/*     */ package WEB-INF.classes.com.universal.milestone;
/*     */ 
/*     */ import com.techempower.ComponentLog;
/*     */ import com.techempower.gemini.Context;
/*     */ import com.techempower.gemini.GeminiApplication;
/*     */ import com.universal.milestone.Form;
/*     */ import com.universal.milestone.MilestoneHelper;
/*     */ import com.universal.milestone.MilestoneHelper_2;
/*     */ import com.universal.milestone.NashRelScheduleForPrintSubHandler;
/*     */ import com.universal.milestone.SecureHandler;
/*     */ import com.universal.milestone.Selection;
/*     */ import com.universal.milestone.SelectionManager;
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
/*     */ public class NashRelScheduleForPrintSubHandler
/*     */   extends SecureHandler
/*     */ {
/*     */   public static final String COMPONENT_CODE = "hNashProd";
/*     */   public GeminiApplication application;
/*     */   public ComponentLog log;
/*     */   
/*     */   public NashRelScheduleForPrintSubHandler(GeminiApplication application) {
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
/*     */   protected static void fillNashRelScheduleForPrint(XStyleSheet report, Context context) {
/*  88 */     Color SHADED_AREA_COLOR = Color.lightGray;
/*  89 */     int COL_LINE_STYLE = 4097;
/*  90 */     int HEADER_FONT_SIZE = 12;
/*  91 */     int NUM_COLUMNS = 7;
/*     */     
/*  93 */     double ldLineVal = 0.3D;
/*     */     
/*  95 */     SectionBand hbandType = new SectionBand(report);
/*  96 */     SectionBand hbandCategory = new SectionBand(report);
/*  97 */     SectionBand hbandDate = new SectionBand(report);
/*  98 */     SectionBand body = new SectionBand(report);
/*  99 */     SectionBand footer = new SectionBand(report);
/* 100 */     SectionBand spacer = new SectionBand(report);
/* 101 */     SectionBand selectionSpacer = new SectionBand(report);
/* 102 */     DefaultSectionLens group = null;
/*     */     
/* 104 */     footer.setVisible(true);
/* 105 */     footer.setHeight(0.1F);
/* 106 */     footer.setShrinkToFit(false);
/* 107 */     footer.setBottomBorder(0);
/*     */     
/* 109 */     spacer.setVisible(true);
/* 110 */     spacer.setHeight(0.05F);
/* 111 */     spacer.setShrinkToFit(false);
/* 112 */     spacer.setBottomBorder(0);
/*     */     
/* 114 */     selectionSpacer.setVisible(true);
/* 115 */     selectionSpacer.setHeight(0.03F);
/* 116 */     selectionSpacer.setShrinkToFit(false);
/* 117 */     selectionSpacer.setBottomBorder(0);
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
/* 138 */       context.includeJSP("status.jsp", "hiddenFrame");
/* 139 */       sresponse.setContentType("text/plain");
/* 140 */       sresponse.flushBuffer();
/*     */     }
/* 142 */     catch (Exception exception) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 148 */     MilestoneHelper.setSelectionSorting(selections, 12);
/* 149 */     Collections.sort(selections);
/*     */ 
/*     */     
/* 152 */     MilestoneHelper.setSelectionSorting(selections, 14);
/* 153 */     Collections.sort(selections);
/*     */ 
/*     */     
/* 156 */     MilestoneHelper.setSelectionSorting(selections, 4);
/* 157 */     Collections.sort(selections);
/*     */ 
/*     */     
/* 160 */     MilestoneHelper.setSelectionSorting(selections, 3);
/* 161 */     Collections.sort(selections);
/*     */ 
/*     */     
/* 164 */     MilestoneHelper.setSelectionSorting(selections, 1);
/* 165 */     Collections.sort(selections);
/*     */ 
/*     */     
/* 168 */     MilestoneHelper.setSelectionSorting(selections, 10);
/* 169 */     Collections.sort(selections);
/*     */ 
/*     */     
/*     */     try {
/* 173 */       DefaultTableLens table_contents = null;
/* 174 */       DefaultTableLens rowCountTable = null;
/* 175 */       DefaultTableLens columnHeaderTable = null;
/* 176 */       DefaultTableLens subTable = null;
/* 177 */       DefaultTableLens monthTableLens = null;
/* 178 */       DefaultTableLens configTableLens = null;
/* 179 */       DefaultTableLens dateTableLens = null;
/*     */       
/* 181 */       rowCountTable = new DefaultTableLens(2, 10000);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 188 */       Form reportForm = (Form)context.getSessionValue("reportForm");
/*     */       
/* 190 */       Calendar beginStDate = (reportForm.getStringValue("beginDate") != null && 
/* 191 */         reportForm.getStringValue("beginDate").length() > 0) ? 
/* 192 */         MilestoneHelper.getDate(reportForm.getStringValue("beginDate")) : null;
/*     */       
/* 194 */       Calendar endStDate = (reportForm.getStringValue("endDate") != null && 
/* 195 */         reportForm.getStringValue("endDate").length() > 0) ? 
/* 196 */         MilestoneHelper.getDate(reportForm.getStringValue("endDate")) : null;
/*     */       
/* 198 */       report.setElement("crs_startdate", MilestoneHelper.getFormatedDate(beginStDate));
/* 199 */       report.setElement("crs_enddate", MilestoneHelper.getFormatedDate(endStDate));
/*     */       
/* 201 */       SimpleDateFormat formatter = new SimpleDateFormat("MMMM d, yyyy");
/* 202 */       String todayLong = formatter.format(new Date());
/* 203 */       report.setElement("crs_bottomdate", todayLong);
/*     */ 
/*     */       
/* 206 */       Hashtable selTable = MilestoneHelper.groupSelectionsByCompanyAndSubconfig(selections);
/* 207 */       Enumeration companies = selTable.keys();
/* 208 */       Vector companyVector = new Vector();
/* 209 */       while (companies.hasMoreElements()) {
/* 210 */         companyVector.addElement(companies.nextElement());
/*     */       }
/*     */       
/* 213 */       int numSubconfigs = 0;
/* 214 */       for (int i = 0; i < companyVector.size(); i++) {
/*     */         
/* 216 */         String companyName = (companyVector.elementAt(i) != null) ? (String)companyVector.elementAt(i) : "";
/* 217 */         Hashtable subconfigTable = (Hashtable)selTable.get(companyName);
/* 218 */         if (subconfigTable != null)
/*     */         {
/* 220 */           numSubconfigs += subconfigTable.size();
/*     */         }
/*     */       } 
/*     */ 
/*     */       
/* 225 */       int numExtraRows = companyVector.size() * 2 - 1 + numSubconfigs * 6;
/*     */ 
/*     */       
/* 228 */       int numSelections = selections.size() * 2;
/* 229 */       int numRows = numSelections + numExtraRows;
/*     */ 
/*     */       
/* 232 */       int numColumns = 7;
/*     */ 
/*     */       
/* 235 */       int nextRow = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 241 */       Object[] companyArray = companyVector.toArray();
/* 242 */       Arrays.sort(companyArray, new StringComparator());
/*     */       
/* 244 */       for (int n = 0; n < companyArray.length; n++)
/*     */       {
/* 246 */         String company = (String)companyArray[n];
/* 247 */         String companyHeaderText = !company.trim().equals("") ? company : "Other";
/*     */         
/* 249 */         hbandType = new SectionBand(report);
/* 250 */         hbandType.setHeight(0.95F);
/* 251 */         hbandType.setShrinkToFit(true);
/* 252 */         hbandType.setVisible(true);
/*     */         
/* 254 */         nextRow = 0;
/*     */ 
/*     */         
/* 257 */         table_contents = new DefaultTableLens(1, numColumns);
/*     */ 
/*     */         
/* 260 */         table_contents.setColWidth(0, 260);
/* 261 */         table_contents.setColWidth(1, 500);
/* 262 */         table_contents.setColWidth(2, 150);
/* 263 */         table_contents.setColWidth(3, 90);
/* 264 */         table_contents.setColWidth(4, 80);
/* 265 */         table_contents.setColWidth(5, 60);
/* 266 */         table_contents.setColWidth(6, 140);
/*     */ 
/*     */         
/* 269 */         table_contents.setSpan(nextRow, 0, new Dimension(numColumns, 1));
/* 270 */         table_contents.setObject(nextRow, 0, companyHeaderText);
/* 271 */         table_contents.setRowBorder(nextRow, 0);
/* 272 */         table_contents.setRowBackground(nextRow, Color.black);
/* 273 */         table_contents.setRowForeground(nextRow, Color.white);
/* 274 */         table_contents.setRowFont(nextRow, new Font("Arial", 3, 16));
/*     */         
/* 276 */         hbandType.addTable(table_contents, new Rectangle(0, 0, 800, 30));
/*     */         
/* 278 */         nextRow = 0;
/*     */         
/* 280 */         columnHeaderTable = new DefaultTableLens(1, 7);
/*     */ 
/*     */         
/* 283 */         columnHeaderTable.setColWidth(0, 260);
/* 284 */         columnHeaderTable.setColWidth(1, 500);
/* 285 */         columnHeaderTable.setColWidth(2, 150);
/* 286 */         columnHeaderTable.setColWidth(3, 90);
/* 287 */         columnHeaderTable.setColWidth(4, 80);
/* 288 */         columnHeaderTable.setColWidth(5, 60);
/* 289 */         columnHeaderTable.setColWidth(6, 140);
/*     */         
/* 291 */         columnHeaderTable.setAlignment(nextRow, 0, 16);
/* 292 */         columnHeaderTable.setObject(nextRow, 0, "\nArtist");
/*     */         
/* 294 */         columnHeaderTable.setAlignment(nextRow, 1, 16);
/* 295 */         columnHeaderTable.setObject(nextRow, 1, "\nTitle/B Side");
/* 296 */         columnHeaderTable.setObject(nextRow, 2, "\nUPC/Selection");
/* 297 */         columnHeaderTable.setAlignment(nextRow, 3, 20);
/* 298 */         columnHeaderTable.setObject(nextRow, 3, "\nPrice");
/* 299 */         columnHeaderTable.setAlignment(nextRow, 4, 18);
/* 300 */         columnHeaderTable.setObject(nextRow, 4, "Street\nDate");
/* 301 */         columnHeaderTable.setAlignment(nextRow, 5, 18);
/* 302 */         columnHeaderTable.setObject(nextRow, 5, "Impact\nDate");
/* 303 */         columnHeaderTable.setObject(nextRow, 6, "\nComments");
/*     */         
/* 305 */         columnHeaderTable.setRowFont(nextRow, new Font("Arial", 3, 10));
/*     */         
/* 307 */         columnHeaderTable.setRowBorderColor(nextRow - 1, Color.white);
/*     */         
/* 309 */         for (int k = -1; k < 7; k++) {
/* 310 */           columnHeaderTable.setColBorderColor(k, Color.white);
/*     */         }
/* 312 */         columnHeaderTable.setRowBorder(nextRow, 4097);
/* 313 */         columnHeaderTable.setRowBorderColor(nextRow, Color.black);
/*     */         
/* 315 */         hbandType.addTable(columnHeaderTable, new Rectangle(0, 30, 800, 35));
/* 316 */         hbandType.setBottomBorder(0);
/*     */ 
/*     */         
/* 319 */         Hashtable subconfigTable = (Hashtable)selTable.get(company);
/* 320 */         if (subconfigTable != null) {
/*     */           
/* 322 */           Enumeration subconfigs = subconfigTable.keys();
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 327 */           Vector subconfigVector = new Vector();
/*     */           
/* 329 */           while (subconfigs.hasMoreElements()) {
/* 330 */             subconfigVector.add((String)subconfigs.nextElement());
/*     */           }
/* 332 */           Object[] subconfigsArray = subconfigVector.toArray();
/* 333 */           Arrays.sort(subconfigsArray, new StringComparator());
/*     */           
/* 335 */           for (int scIndex = 0; scIndex < subconfigsArray.length; scIndex++) {
/*     */             
/* 337 */             String subconfig = (String)subconfigsArray[scIndex];
/*     */             
/* 339 */             configTableLens = new DefaultTableLens(1, 7);
/* 340 */             hbandCategory = new SectionBand(report);
/* 341 */             hbandCategory.setHeight(0.25F);
/* 342 */             hbandCategory.setShrinkToFit(true);
/* 343 */             hbandCategory.setVisible(true);
/* 344 */             hbandCategory.setBottomBorder(0);
/* 345 */             hbandCategory.setLeftBorder(0);
/* 346 */             hbandCategory.setRightBorder(0);
/* 347 */             hbandCategory.setTopBorder(0);
/*     */             
/* 349 */             nextRow = 0;
/*     */ 
/*     */             
/* 352 */             configTableLens.setAlignment(nextRow, 0, 2);
/* 353 */             configTableLens.setSpan(nextRow, 0, new Dimension(7, 1));
/* 354 */             configTableLens.setObject(nextRow, 0, subconfig);
/* 355 */             configTableLens.setRowFont(nextRow, new Font("Arial", 3, 12));
/*     */             
/* 357 */             hbandCategory.addTable(configTableLens, new Rectangle(800, 800));
/*     */             
/* 359 */             footer.setVisible(true);
/* 360 */             footer.setHeight(0.1F);
/* 361 */             footer.setShrinkToFit(false);
/* 362 */             footer.setBottomBorder(0);
/*     */             
/* 364 */             group = new DefaultSectionLens(null, group, spacer);
/* 365 */             group = new DefaultSectionLens(null, group, hbandCategory);
/* 366 */             group = new DefaultSectionLens(null, group, spacer);
/*     */ 
/*     */             
/* 369 */             selections = (Vector)subconfigTable.get(subconfig);
/* 370 */             if (selections == null) {
/* 371 */               selections = new Vector();
/*     */             }
/*     */             
/* 374 */             for (int i = 0; i < selections.size(); i++) {
/*     */               
/* 376 */               Selection sel = (Selection)selections.elementAt(i);
/*     */               
/* 378 */               nextRow = 0;
/* 379 */               subTable = new DefaultTableLens(2, 7);
/*     */ 
/*     */               
/* 382 */               subTable.setColWidth(0, 260);
/* 383 */               subTable.setColWidth(1, 500);
/* 384 */               subTable.setColWidth(2, 150);
/* 385 */               subTable.setColWidth(3, 90);
/* 386 */               subTable.setColWidth(4, 80);
/* 387 */               subTable.setColWidth(5, 60);
/* 388 */               subTable.setColWidth(6, 140);
/*     */ 
/*     */               
/* 391 */               String bSide = (sel.getBSide() != null && !sel.getBSide().trim().equals("")) ? (
/* 392 */                 "B Side:  " + sel.getBSide()) : "";
/*     */ 
/*     */               
/* 395 */               String upc = sel.getUpc();
/*     */ 
/*     */               
/* 398 */               upc = MilestoneHelper_2.getRMSReportFormat(upc, "UPC", sel.getIsDigital());
/*     */ 
/*     */               
/* 401 */               if (upc == null || upc.trim().equals("")) {
/*     */                 
/* 403 */                 upc = SelectionManager.getLookupObjectValue(sel.getPrefixID());
/* 404 */                 if (upc == null)
/* 405 */                   upc = ""; 
/* 406 */                 if (sel.getSelectionNo() != null) {
/* 407 */                   upc = String.valueOf(upc) + sel.getSelectionNo();
/*     */                 }
/*     */               } 
/*     */               
/* 411 */               String price = "0.00";
/* 412 */               if (sel.getPriceCode() != null && 
/* 413 */                 sel.getPriceCode().getTotalCost() > 0.0F) {
/* 414 */                 price = MilestoneHelper.formatDollarPrice(sel.getPriceCode().getTotalCost());
/*     */               }
/*     */               
/* 417 */               String streetDate = MilestoneHelper.getFormatedDate(sel.getStreetDate());
/*     */               
/* 419 */               String status = (sel.getSelectionStatus() != null && sel.getSelectionStatus().getName() != null) ? 
/* 420 */                 sel.getSelectionStatus().getName() : "";
/*     */               
/* 422 */               if (status.equalsIgnoreCase("TBS")) {
/* 423 */                 streetDate = "TBS " + streetDate;
/*     */               }
/* 425 */               else if (status.equalsIgnoreCase("In The Works")) {
/* 426 */                 streetDate = "ITW " + streetDate;
/*     */               } 
/*     */               
/* 429 */               String comment = (sel.getSelectionComments() != null) ? sel.getSelectionComments() : "";
/*     */ 
/*     */               
/* 432 */               subTable.setRowInsets(nextRow, new Insets(0, 0, 0, 0));
/* 433 */               subTable.setRowHeight(nextRow, 11);
/*     */               
/* 435 */               subTable.setObject(nextRow, 0, sel.getArtist());
/* 436 */               subTable.setObject(nextRow, 1, sel.getTitle());
/* 437 */               subTable.setObject(nextRow, 2, upc);
/* 438 */               subTable.setObject(nextRow, 3, "$" + price);
/* 439 */               subTable.setColLineWrap(4, false);
/* 440 */               subTable.setObject(nextRow, 4, streetDate);
/* 441 */               subTable.setObject(nextRow, 5, MilestoneHelper.getFormatedDate(sel.getImpactDate()));
/* 442 */               subTable.setObject(nextRow, 6, comment);
/*     */               
/* 444 */               subTable.setColAlignment(0, 9);
/* 445 */               subTable.setSpan(nextRow, 0, new Dimension(1, 2));
/* 446 */               subTable.setColAlignment(1, 33);
/* 447 */               subTable.setColAlignment(2, 9);
/* 448 */               subTable.setSpan(nextRow, 2, new Dimension(1, 2));
/* 449 */               subTable.setColAlignment(3, 12);
/* 450 */               subTable.setSpan(nextRow, 3, new Dimension(1, 2));
/* 451 */               subTable.setColAlignment(4, 12);
/* 452 */               subTable.setSpan(nextRow, 4, new Dimension(1, 2));
/* 453 */               subTable.setColAlignment(5, 12);
/* 454 */               subTable.setSpan(nextRow, 5, new Dimension(1, 2));
/* 455 */               subTable.setColAlignment(6, 9);
/* 456 */               subTable.setSpan(nextRow, 6, new Dimension(1, 2));
/*     */               
/* 458 */               subTable.setRowBorder(nextRow, 1, 0);
/*     */               
/* 460 */               subTable.setRowBorder(nextRow - 1, 0);
/*     */               
/* 462 */               subTable.setRowFont(nextRow, new Font("Arial", 0, 8));
/*     */               
/* 464 */               nextRow++;
/* 465 */               subTable.setAlignment(nextRow, 1, 9);
/*     */               
/* 467 */               String[] checkStrings = { comment.trim(), sel.getArtist().trim(), sel.getTitle().trim() };
/* 468 */               int[] checkStringsLength = { 20, 40, 60 };
/*     */               
/* 470 */               if (bSide.equals("") && comment.equals(""))
/*     */               {
/* 472 */                 subTable.setRowHeight(nextRow, 1);
/*     */               }
/*     */               
/* 475 */               int addExtraLines = MilestoneHelper.lineCount(checkStrings, checkStringsLength);
/*     */               
/* 477 */               if (addExtraLines > 0 && addExtraLines < 2)
/*     */               {
/* 479 */                 addExtraLines--;
/*     */               }
/* 481 */               for (int z = 0; z < addExtraLines; z++) {
/* 482 */                 bSide = String.valueOf(bSide) + "\n";
/*     */               }
/* 484 */               for (int k = -1; k < 7; k++) {
/* 485 */                 subTable.setColBorder(k, 0);
/*     */               }
/* 487 */               subTable.setObject(nextRow, 0, "");
/* 488 */               subTable.setObject(nextRow, 1, bSide);
/* 489 */               subTable.setObject(nextRow, 2, "");
/* 490 */               subTable.setObject(nextRow, 3, "");
/* 491 */               subTable.setObject(nextRow, 4, "");
/* 492 */               subTable.setObject(nextRow, 5, "");
/* 493 */               subTable.setObject(nextRow, 6, "");
/*     */               
/* 495 */               subTable.setRowFont(nextRow, new Font("Arial", 2, 8));
/* 496 */               subTable.setColAlignment(1, 9);
/*     */ 
/*     */               
/* 499 */               subTable.setRowBorderColor(nextRow, Color.lightGray);
/*     */               
/* 501 */               body = new SectionBand(report);
/*     */               
/* 503 */               double lfLineCount = 1.5D;
/*     */               
/* 505 */               if (addExtraLines > 0) {
/*     */                 
/* 507 */                 body.setHeight(1.5F);
/*     */               }
/*     */               else {
/*     */                 
/* 511 */                 body.setHeight(0.8F);
/*     */               } 
/*     */               
/* 514 */               if (addExtraLines > 3) {
/*     */                 
/* 516 */                 if (lfLineCount < addExtraLines * 0.3D) {
/* 517 */                   lfLineCount = addExtraLines * 0.3D;
/*     */                 }
/* 519 */                 body.setHeight((float)lfLineCount);
/*     */               }
/*     */               else {
/*     */                 
/* 523 */                 body.setHeight(0.8F);
/*     */               } 
/*     */               
/* 526 */               body.addTable(subTable, new Rectangle(800, 800));
/* 527 */               body.setBottomBorder(0);
/* 528 */               body.setTopBorder(0);
/* 529 */               body.setShrinkToFit(true);
/* 530 */               body.setVisible(true);
/*     */ 
/*     */               
/* 533 */               group = new DefaultSectionLens(null, group, body);
/*     */             } 
/*     */           } 
/*     */         } 
/* 537 */         group = new DefaultSectionLens(hbandType, group, null);
/* 538 */         report.addSection(group, rowCountTable);
/* 539 */         report.addPageBreak();
/* 540 */         group = null;
/*     */       }
/*     */     
/* 543 */     } catch (Exception e) {
/*     */       
/* 545 */       System.out.println(">>>>>>>>ReportHandler.fillEntRelScheduleForPrint(): exception: " + e);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\NashRelScheduleForPrintSubHandler.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */