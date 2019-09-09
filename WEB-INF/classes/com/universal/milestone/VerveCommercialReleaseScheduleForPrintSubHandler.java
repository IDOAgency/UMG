/*     */ package WEB-INF.classes.com.universal.milestone;
/*     */ 
/*     */ import com.techempower.ComponentLog;
/*     */ import com.techempower.gemini.Context;
/*     */ import com.techempower.gemini.GeminiApplication;
/*     */ import com.universal.milestone.Cache;
/*     */ import com.universal.milestone.Form;
/*     */ import com.universal.milestone.LookupObject;
/*     */ import com.universal.milestone.MilestoneHelper;
/*     */ import com.universal.milestone.MilestoneHelper_2;
/*     */ import com.universal.milestone.SecureHandler;
/*     */ import com.universal.milestone.Selection;
/*     */ import com.universal.milestone.SelectionManager;
/*     */ import com.universal.milestone.StatusJSPupdate;
/*     */ import com.universal.milestone.VerveCommercialReleaseScheduleForPrintSubHandler;
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
/*     */ public class VerveCommercialReleaseScheduleForPrintSubHandler
/*     */   extends SecureHandler
/*     */ {
/*     */   public static final String COMPONENT_CODE = "hCRel";
/*     */   public GeminiApplication application;
/*     */   public ComponentLog log;
/*     */   
/*     */   public VerveCommercialReleaseScheduleForPrintSubHandler(GeminiApplication application) {
/*  67 */     this.application = application;
/*  68 */     this.log = application.getLog("hCRel");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  76 */   public String getDescription() { return "Verve Commercial Release Schedule Report"; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static void fillVerveScheduleUpdateForPrint(XStyleSheet report, Context context) {
/*  87 */     Color SHADED_AREA_COLOR = Color.lightGray;
/*  88 */     int COL_LINE_STYLE = 4097;
/*  89 */     int HEADER_FONT_SIZE = 12;
/*  90 */     int NUM_COLUMNS = 12;
/*     */     
/*  92 */     double ldLineVal = 0.3D;
/*     */     
/*  94 */     SectionBand hbandType = new SectionBand(report);
/*  95 */     SectionBand hbandCategory = new SectionBand(report);
/*  96 */     SectionBand hbandDate = new SectionBand(report);
/*  97 */     SectionBand body = new SectionBand(report);
/*  98 */     SectionBand footer = new SectionBand(report);
/*  99 */     SectionBand spacer = new SectionBand(report);
/* 100 */     DefaultSectionLens group = null;
/*     */     
/* 102 */     footer.setVisible(true);
/* 103 */     footer.setHeight(0.1F);
/* 104 */     footer.setShrinkToFit(false);
/* 105 */     footer.setBottomBorder(0);
/*     */     
/* 107 */     spacer.setVisible(true);
/* 108 */     spacer.setHeight(0.05F);
/* 109 */     spacer.setShrinkToFit(false);
/* 110 */     spacer.setBottomBorder(0);
/*     */ 
/*     */     
/* 113 */     int separatorLineStyle = 266240;
/* 114 */     Color separatorLineColor = Color.black;
/*     */ 
/*     */     
/* 117 */     int monthLineStyle = 4097;
/* 118 */     Color monthLineColor = Color.black;
/*     */     
/* 120 */     StatusJSPupdate aStatusUpdate = new StatusJSPupdate(context);
/* 121 */     aStatusUpdate.setInternalCounter(true);
/*     */ 
/*     */ 
/*     */     
/* 125 */     aStatusUpdate.updateStatus(0, 0, "start_gathering", 0);
/*     */ 
/*     */     
/* 128 */     Vector selections = MilestoneHelper.getSelectionsForReport(context);
/*     */ 
/*     */     
/* 131 */     aStatusUpdate.updateStatus(0, 0, "start_report", 10);
/*     */ 
/*     */     
/* 134 */     MilestoneHelper.setSelectionSorting(selections, 5);
/* 135 */     Collections.sort(selections);
/*     */ 
/*     */     
/* 138 */     MilestoneHelper.setSelectionSorting(selections, 4);
/* 139 */     Collections.sort(selections);
/*     */ 
/*     */     
/* 142 */     MilestoneHelper.setSelectionSorting(selections, 3);
/* 143 */     Collections.sort(selections);
/*     */ 
/*     */     
/* 146 */     MilestoneHelper.setSelectionSorting(selections, 2);
/* 147 */     Collections.sort(selections);
/*     */ 
/*     */     
/* 150 */     MilestoneHelper.setSelectionSorting(selections, 1);
/* 151 */     Collections.sort(selections);
/*     */ 
/*     */     
/* 154 */     MilestoneHelper.setSelectionSorting(selections, 16);
/* 155 */     Collections.sort(selections);
/*     */ 
/*     */     
/*     */     try {
/* 159 */       DefaultTableLens table_contents = null;
/* 160 */       DefaultTableLens rowCountTable = null;
/* 161 */       DefaultTableLens columnHeaderTable = null;
/* 162 */       DefaultTableLens subTable = null;
/* 163 */       DefaultTableLens monthTableLens = null;
/* 164 */       DefaultTableLens dateTableLens = null;
/*     */       
/* 166 */       rowCountTable = new DefaultTableLens(2, 10000);
/*     */ 
/*     */       
/* 169 */       int nextRow = 0;
/*     */ 
/*     */       
/* 172 */       SeparatorElement topSeparator = (SeparatorElement)report.getElement("separator_top");
/* 173 */       SeparatorElement bottomHeaderSeparator = (SeparatorElement)report.getElement("separator_bottom_header");
/* 174 */       SeparatorElement bottomSeparator = (SeparatorElement)report.getElement("separator_bottom");
/* 175 */       if (topSeparator != null) {
/*     */         
/* 177 */         topSeparator.setStyle(separatorLineStyle);
/* 178 */         topSeparator.setForeground(separatorLineColor);
/*     */       } 
/* 180 */       if (bottomHeaderSeparator != null) {
/*     */         
/* 182 */         bottomHeaderSeparator.setStyle(separatorLineStyle);
/* 183 */         bottomHeaderSeparator.setForeground(separatorLineColor);
/*     */       } 
/* 185 */       if (bottomSeparator != null) {
/*     */         
/* 187 */         bottomSeparator.setStyle(separatorLineStyle);
/* 188 */         bottomSeparator.setForeground(separatorLineColor);
/*     */       } 
/*     */ 
/*     */       
/* 192 */       Form reportForm = (Form)context.getSessionValue("reportForm");
/*     */       
/* 194 */       Calendar beginStDate = (reportForm.getStringValue("beginDate") != null && 
/* 195 */         reportForm.getStringValue("beginDate").length() > 0) ? 
/* 196 */         MilestoneHelper.getDate(reportForm.getStringValue("beginDate")) : null;
/*     */       
/* 198 */       Calendar endStDate = (reportForm.getStringValue("endDate") != null && 
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
/*     */       
/* 210 */       Vector groupedByMonth = MilestoneHelper.groupSelectionsByReleaseMonth(selections);
/*     */ 
/*     */       
/* 213 */       int numExtraRows = groupedByMonth.size() * 4;
/*     */ 
/*     */       
/* 216 */       int numSelections = selections.size();
/* 217 */       int numRows = numSelections + numExtraRows;
/*     */       
/* 219 */       int numColumns = 11;
/*     */       
/* 221 */       nextRow = 0;
/*     */       
/* 223 */       hbandType = new SectionBand(report);
/* 224 */       hbandType.setHeight(0.5F);
/* 225 */       hbandType.setShrinkToFit(false);
/* 226 */       hbandType.setVisible(true);
/*     */ 
/*     */       
/* 229 */       table_contents = new DefaultTableLens(1, numColumns);
/*     */       
/* 231 */       table_contents.setRowBorder(0);
/*     */       
/* 233 */       table_contents.setColWidth(0, 50);
/* 234 */       table_contents.setColWidth(1, 50);
/* 235 */       table_contents.setColWidth(2, 50);
/* 236 */       table_contents.setColWidth(3, 130);
/* 237 */       table_contents.setColWidth(4, 210);
/* 238 */       table_contents.setColWidth(5, 80);
/* 239 */       table_contents.setColWidth(6, 60);
/* 240 */       table_contents.setColWidth(7, 100);
/* 241 */       table_contents.setColWidth(8, 70);
/* 242 */       table_contents.setColWidth(9, 85);
/* 243 */       table_contents.setColWidth(10, 70);
/*     */       
/* 245 */       table_contents.setColBorder(0);
/* 246 */       table_contents.setRowBorder(0);
/*     */ 
/*     */       
/* 249 */       table_contents.setRowAlignment(nextRow, 32);
/* 250 */       table_contents.setRowHeight(nextRow, 55);
/*     */       
/* 252 */       table_contents.setObject(nextRow, 0, "Japan");
/* 253 */       table_contents.setAlignment(nextRow, 0, 1);
/* 254 */       table_contents.setObject(nextRow, 1, "Europe");
/* 255 */       table_contents.setAlignment(nextRow, 1, 1);
/* 256 */       table_contents.setObject(nextRow, 2, "US");
/* 257 */       table_contents.setAlignment(nextRow, 2, 1);
/* 258 */       table_contents.setObject(nextRow, 3, "Artist");
/* 259 */       table_contents.setAlignment(nextRow, 3, 1);
/* 260 */       table_contents.setObject(nextRow, 4, "Title");
/* 261 */       table_contents.setAlignment(nextRow, 4, 1);
/* 262 */       table_contents.setObject(nextRow, 5, "Product Category");
/* 263 */       table_contents.setAlignment(nextRow, 5, 1);
/* 264 */       table_contents.setObject(nextRow, 6, "Price");
/* 265 */       table_contents.setAlignment(nextRow, 6, 4);
/* 266 */       table_contents.setObject(nextRow, 7, "Label");
/* 267 */       table_contents.setAlignment(nextRow, 7, 2);
/* 268 */       table_contents.setObject(nextRow, 8, "Local Product #");
/* 269 */       table_contents.setAlignment(nextRow, 8, 1);
/* 270 */       table_contents.setObject(nextRow, 9, "UPC");
/* 271 */       table_contents.setAlignment(nextRow, 9, 2);
/* 272 */       table_contents.setObject(nextRow, 10, "Project #");
/* 273 */       table_contents.setAlignment(nextRow, 10, 1);
/*     */       
/* 275 */       table_contents.setRowFont(nextRow, new Font("Arial", 3, 10));
/*     */       
/* 277 */       hbandType.addTable(table_contents, new Rectangle(0, 0, 800, 30));
/*     */       
/* 279 */       table_contents = new DefaultTableLens(1, numColumns);
/*     */       
/* 281 */       nextRow = 0;
/* 282 */       table_contents.setRowBackground(nextRow, Color.white);
/* 283 */       table_contents.setRowBorderColor(nextRow - 1, Color.black);
/* 284 */       table_contents.setRowBorder(nextRow, 0);
/* 285 */       table_contents.setColBorder(0);
/* 286 */       table_contents.setRowHeight(nextRow, 1);
/*     */       
/* 288 */       hbandType.addTable(table_contents, new Rectangle(0, 32, 800, 5));
/*     */ 
/*     */ 
/*     */       
/* 292 */       int statusBarCounter = 0;
/* 293 */       for (int n = 0; n < groupedByMonth.size(); n++) {
/*     */         
/* 295 */         Vector selectionsInMonth = (Vector)groupedByMonth.elementAt(n);
/* 296 */         statusBarCounter += selectionsInMonth.size();
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 302 */       for (int n = 0; n < groupedByMonth.size(); n++) {
/*     */ 
/*     */         
/* 305 */         Vector selectionsInMonth = (Vector)groupedByMonth.elementAt(n);
/* 306 */         if (selectionsInMonth == null) {
/* 307 */           selectionsInMonth = new Vector();
/*     */         }
/* 309 */         monthTableLens = new DefaultTableLens(2, numColumns);
/* 310 */         hbandCategory = new SectionBand(report);
/* 311 */         hbandCategory.setHeight(0.23F);
/* 312 */         hbandCategory.setShrinkToFit(false);
/* 313 */         hbandCategory.setVisible(true);
/* 314 */         hbandCategory.setBottomBorder(0);
/* 315 */         hbandCategory.setLeftBorder(0);
/* 316 */         hbandCategory.setRightBorder(0);
/* 317 */         hbandCategory.setTopBorder(0);
/*     */         
/* 319 */         nextRow = 0;
/*     */         
/* 321 */         monthTableLens.setRowBorderColor(nextRow - 1, Color.white);
/* 322 */         monthTableLens.setRowBorderColor(nextRow, Color.white);
/*     */         
/* 324 */         monthTableLens.setColWidth(0, 50);
/* 325 */         monthTableLens.setColWidth(1, 50);
/* 326 */         monthTableLens.setColWidth(2, 50);
/* 327 */         monthTableLens.setColWidth(3, 130);
/* 328 */         monthTableLens.setColWidth(4, 210);
/* 329 */         monthTableLens.setColWidth(5, 80);
/* 330 */         monthTableLens.setColWidth(6, 60);
/* 331 */         monthTableLens.setColWidth(7, 100);
/* 332 */         monthTableLens.setColWidth(8, 70);
/* 333 */         monthTableLens.setColWidth(9, 85);
/* 334 */         monthTableLens.setColWidth(10, 70);
/*     */         
/* 336 */         monthTableLens.setColBorder(0);
/*     */ 
/*     */         
/* 339 */         String monthName = MilestoneHelper.getMonthNameForUmeReport(selectionsInMonth);
/*     */         
/* 341 */         monthTableLens.setRowAlignment(nextRow, 1);
/* 342 */         monthTableLens.setSpan(nextRow, 0, new Dimension(numColumns, 1));
/* 343 */         monthTableLens.setObject(nextRow, 0, monthName);
/*     */         
/* 345 */         monthTableLens.setRowFont(nextRow, new Font("Arial", 1, 10));
/* 346 */         monthTableLens.setRowBackground(nextRow, Color.lightGray);
/*     */         
/* 348 */         hbandCategory.addTable(monthTableLens, new Rectangle(0, 0, 800, 20));
/*     */         
/* 350 */         footer.setVisible(true);
/* 351 */         footer.setHeight(0.1F);
/* 352 */         footer.setShrinkToFit(false);
/* 353 */         footer.setBottomBorder(0);
/*     */         
/* 355 */         group = new DefaultSectionLens(null, group, spacer);
/* 356 */         group = new DefaultSectionLens(null, group, hbandCategory);
/* 357 */         group = new DefaultSectionLens(null, group, spacer);
/*     */ 
/*     */         
/* 360 */         MilestoneHelper.setSelectionSorting(selectionsInMonth, 5);
/* 361 */         Collections.sort(selectionsInMonth);
/*     */ 
/*     */         
/* 364 */         MilestoneHelper.setSelectionSorting(selectionsInMonth, 4);
/* 365 */         Collections.sort(selectionsInMonth);
/*     */ 
/*     */         
/* 368 */         MilestoneHelper.setSelectionSorting(selectionsInMonth, 3);
/* 369 */         Collections.sort(selectionsInMonth);
/*     */ 
/*     */         
/* 372 */         MilestoneHelper.setSelectionSorting(selectionsInMonth, 2);
/* 373 */         Collections.sort(selectionsInMonth);
/*     */ 
/*     */         
/* 376 */         MilestoneHelper.setSelectionSorting(selectionsInMonth, 1);
/* 377 */         Collections.sort(selectionsInMonth);
/*     */ 
/*     */         
/* 380 */         MilestoneHelper.setSelectionSorting(selections, 16);
/* 381 */         Collections.sort(selections);
/*     */ 
/*     */         
/* 384 */         for (int i = 0; i < selectionsInMonth.size(); i++) {
/*     */ 
/*     */           
/* 387 */           aStatusUpdate.updateStatus(statusBarCounter, i, "start_report", 0);
/*     */ 
/*     */           
/* 390 */           Selection sel = (Selection)selectionsInMonth.elementAt(i);
/*     */           
/* 392 */           nextRow = 0;
/* 393 */           subTable = new DefaultTableLens(1, numColumns);
/*     */           
/* 395 */           subTable.setColWidth(0, 50);
/* 396 */           subTable.setColWidth(1, 50);
/* 397 */           subTable.setColWidth(2, 50);
/* 398 */           subTable.setColWidth(3, 130);
/* 399 */           subTable.setColWidth(4, 210);
/* 400 */           subTable.setColWidth(5, 80);
/* 401 */           subTable.setColWidth(6, 60);
/* 402 */           subTable.setColWidth(7, 100);
/* 403 */           subTable.setColWidth(8, 70);
/* 404 */           subTable.setColWidth(9, 85);
/* 405 */           subTable.setColWidth(10, 70);
/*     */ 
/*     */           
/* 408 */           String labelName = "";
/* 409 */           if (sel.getLabel() != null)
/*     */           {
/* 411 */             labelName = sel.getLabel().getName();
/*     */           }
/*     */ 
/*     */           
/* 415 */           String price = "0.00";
/* 416 */           if (sel.getPriceCode() != null && 
/* 417 */             sel.getPriceCode().getTotalCost() > 0.0F) {
/* 418 */             price = MilestoneHelper.formatDollarPrice(sel.getPriceCode().getTotalCost());
/*     */           }
/*     */           
/* 421 */           LookupObject prodCategory = SelectionManager.getLookupObject(sel.getProductCategory().getAbbreviation(), 
/* 422 */               Cache.getProductCategories());
/* 423 */           String prodCategoryName = "";
/* 424 */           if (prodCategory != null) {
/* 425 */             prodCategoryName = prodCategory.getName();
/*     */           }
/*     */           
/* 428 */           subTable.setRowInsets(nextRow, new Insets(0, 0, 5, 0));
/*     */           
/* 430 */           String intlDate = "  " + MilestoneHelper.getFormatedDate(sel.getInternationalDate());
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 435 */           if (intlDate.length() < 8) {
/*     */             
/* 437 */             intlDate = String.valueOf(intlDate) + " ";
/*     */             
/* 439 */             if (intlDate.length() < 7) {
/* 440 */               intlDate = String.valueOf(intlDate) + " ";
/*     */             }
/*     */           } 
/* 443 */           String streetDate = "  " + MilestoneHelper.getFormatedDate(sel.getStreetDate());
/* 444 */           String japanDate = " " + sel.getOtherContact();
/*     */           
/* 446 */           String status = (sel.getSelectionStatus() != null && sel.getSelectionStatus().getName() != null) ? 
/* 447 */             sel.getSelectionStatus().getName() : "";
/*     */ 
/*     */           
/* 450 */           if (status.equalsIgnoreCase("TBS")) {
/* 451 */             streetDate = "TBS " + streetDate;
/*     */           }
/* 453 */           else if (status.equalsIgnoreCase("In The Works")) {
/* 454 */             streetDate = "ITW " + streetDate;
/*     */           } 
/*     */ 
/*     */ 
/*     */           
/* 459 */           if (streetDate.length() < 8) {
/*     */             
/* 461 */             streetDate = String.valueOf(streetDate) + " ";
/*     */             
/* 463 */             if (streetDate.length() < 7) {
/* 464 */               streetDate = String.valueOf(streetDate) + " ";
/*     */             }
/*     */           } 
/* 467 */           subTable.setObject(nextRow, 0, japanDate);
/* 468 */           subTable.setAlignment(nextRow, 0, 9);
/* 469 */           subTable.setObject(nextRow, 1, intlDate);
/* 470 */           subTable.setAlignment(nextRow, 1, 9);
/* 471 */           subTable.setObject(nextRow, 2, streetDate);
/* 472 */           subTable.setAlignment(nextRow, 2, 9);
/* 473 */           subTable.setObject(nextRow, 3, sel.getArtist().trim());
/* 474 */           subTable.setAlignment(nextRow, 3, 9);
/* 475 */           subTable.setObject(nextRow, 4, sel.getTitle());
/* 476 */           subTable.setAlignment(nextRow, 4, 9);
/* 477 */           subTable.setObject(nextRow, 5, prodCategoryName);
/* 478 */           subTable.setAlignment(nextRow, 5, 9);
/* 479 */           subTable.setObject(nextRow, 6, "$" + price);
/* 480 */           subTable.setAlignment(nextRow, 6, 12);
/* 481 */           subTable.setObject(nextRow, 7, labelName);
/* 482 */           subTable.setAlignment(nextRow, 7, 10);
/*     */           
/* 484 */           String selId = "";
/* 485 */           if (SelectionManager.getLookupObjectValue(sel.getPrefixID()).equals("")) {
/* 486 */             selId = sel.getSelectionNo();
/*     */           } else {
/* 488 */             selId = String.valueOf(SelectionManager.getLookupObjectValue(sel.getPrefixID())) + sel.getSelectionNo();
/*     */           } 
/* 490 */           subTable.setObject(nextRow, 8, selId);
/* 491 */           subTable.setAlignment(nextRow, 8, 9);
/*     */ 
/*     */           
/* 494 */           String upc = sel.getUpc();
/* 495 */           upc = MilestoneHelper_2.getRMSReportFormat(upc, "UPC", sel.getIsDigital());
/*     */           
/* 497 */           subTable.setObject(nextRow, 9, upc);
/* 498 */           subTable.setAlignment(nextRow, 9, 10);
/* 499 */           subTable.setObject(nextRow, 10, sel.getProjectID());
/* 500 */           subTable.setAlignment(nextRow, 10, 9);
/*     */ 
/*     */           
/* 503 */           subTable.setColBorder(0);
/* 504 */           subTable.setRowBorderColor(nextRow, Color.white);
/* 505 */           subTable.setRowBorder(nextRow - 1, 0);
/* 506 */           subTable.setRowBorder(nextRow, 4097);
/* 507 */           subTable.setRowBorderColor(nextRow, Color.lightGray);
/* 508 */           subTable.setRowFont(nextRow, new Font("Arial", 0, 8));
/*     */           
/* 510 */           body = new SectionBand(report);
/*     */           
/* 512 */           double lfLineCount = 1.5D;
/*     */           
/* 514 */           body.setHeight(1.0F);
/*     */           
/* 516 */           body.addTable(subTable, new Rectangle(800, 800));
/* 517 */           body.setBottomBorder(0);
/* 518 */           body.setTopBorder(0);
/* 519 */           body.setShrinkToFit(true);
/* 520 */           body.setVisible(true);
/*     */           
/* 522 */           group = new DefaultSectionLens(null, group, body);
/*     */         } 
/*     */       } 
/* 525 */       group = new DefaultSectionLens(hbandType, group, null);
/* 526 */       report.addSection(group, rowCountTable);
/* 527 */       group = null;
/*     */ 
/*     */       
/* 530 */       HttpServletResponse sresponse = context.getResponse();
/* 531 */       context.putDelivery("percent", new String("100"));
/* 532 */       context.includeJSP("status.jsp", "hiddenFrame");
/* 533 */       sresponse.setContentType("text/plain");
/* 534 */       sresponse.flushBuffer();
/*     */     }
/* 536 */     catch (Exception e) {
/*     */       
/* 538 */       System.out.println(">>>>>>>>ReportHandler.fillEntCommRelScheduleForPrint(): exception: " + e);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\VerveCommercialReleaseScheduleForPrintSubHandler.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */