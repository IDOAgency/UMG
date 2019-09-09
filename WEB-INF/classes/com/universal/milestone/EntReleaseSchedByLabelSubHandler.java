/*     */ package WEB-INF.classes.com.universal.milestone;
/*     */ 
/*     */ import com.techempower.ComponentLog;
/*     */ import com.techempower.gemini.Context;
/*     */ import com.techempower.gemini.GeminiApplication;
/*     */ import com.universal.milestone.EntReleaseSchedByLabelSubHandler;
/*     */ import com.universal.milestone.Family;
/*     */ import com.universal.milestone.Form;
/*     */ import com.universal.milestone.MilestoneHelper;
/*     */ import com.universal.milestone.MilestoneHelper_2;
/*     */ import com.universal.milestone.SecureHandler;
/*     */ import com.universal.milestone.Selection;
/*     */ import com.universal.milestone.SelectionConfiguration;
/*     */ import com.universal.milestone.SelectionManager;
/*     */ import com.universal.milestone.SelectionSubConfiguration;
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
/*     */ 
/*     */ 
/*     */ public class EntReleaseSchedByLabelSubHandler
/*     */   extends SecureHandler
/*     */ {
/*     */   public static final String COMPONENT_CODE = "hNsl";
/*     */   public GeminiApplication application;
/*     */   public ComponentLog log;
/*     */   
/*     */   public EntReleaseSchedByLabelSubHandler(GeminiApplication application) {
/*  72 */     this.application = application;
/*  73 */     this.log = application.getLog("hNsl");
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
/*     */   protected static void fillEntReleaseSchedByLabelForPrint(XStyleSheet report, Context context) {
/*  92 */     Color SHADED_AREA_COLOR = Color.lightGray;
/*  93 */     int COL_LINE_STYLE = 4097;
/*  94 */     int HEADER_FONT_SIZE = 12;
/*  95 */     int NUM_COLUMNS = 7;
/*     */     
/*  97 */     double ldLineVal = 0.3D;
/*     */     
/*  99 */     SectionBand hbandType = new SectionBand(report);
/* 100 */     SectionBand hbandCategory = new SectionBand(report);
/* 101 */     SectionBand hbandDate = new SectionBand(report);
/* 102 */     SectionBand body = new SectionBand(report);
/* 103 */     SectionBand footer = new SectionBand(report);
/* 104 */     SectionBand spacer = new SectionBand(report);
/* 105 */     SectionBand selectionSpacer = new SectionBand(report);
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
/* 118 */     selectionSpacer.setVisible(true);
/* 119 */     selectionSpacer.setHeight(0.03F);
/* 120 */     selectionSpacer.setShrinkToFit(false);
/* 121 */     selectionSpacer.setBottomBorder(0);
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 126 */       HttpServletResponse sresponse = context.getResponse();
/* 127 */       context.putDelivery("status", new String("start_gathering"));
/* 128 */       context.includeJSP("status.jsp", "hiddenFrame");
/* 129 */       sresponse.setContentType("text/plain");
/* 130 */       sresponse.flushBuffer();
/*     */     }
/* 132 */     catch (Exception exception) {}
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 137 */     Vector selections = MilestoneHelper.getSelectionsForReport(context);
/*     */ 
/*     */     
/*     */     try {
/* 141 */       HttpServletResponse sresponse = context.getResponse();
/* 142 */       context.putDelivery("status", new String("start_report"));
/* 143 */       context.putDelivery("percent", new String("10"));
/* 144 */       context.includeJSP("status.jsp", "hiddenFrame");
/* 145 */       sresponse.setContentType("text/plain");
/* 146 */       sresponse.flushBuffer();
/*     */     }
/* 148 */     catch (Exception exception) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 154 */     MilestoneHelper.setSelectionSorting(selections, 12);
/* 155 */     Collections.sort(selections);
/*     */ 
/*     */     
/* 158 */     MilestoneHelper.setSelectionSorting(selections, 14);
/* 159 */     Collections.sort(selections);
/*     */ 
/*     */     
/* 162 */     MilestoneHelper.setSelectionSorting(selections, 4);
/* 163 */     Collections.sort(selections);
/*     */ 
/*     */     
/* 166 */     MilestoneHelper.setSelectionSorting(selections, 3);
/* 167 */     Collections.sort(selections);
/*     */ 
/*     */     
/* 170 */     MilestoneHelper.setSelectionSorting(selections, 1);
/* 171 */     Collections.sort(selections);
/*     */ 
/*     */     
/* 174 */     MilestoneHelper.setSelectionSorting(selections, 10);
/* 175 */     Collections.sort(selections);
/*     */ 
/*     */     
/*     */     try {
/* 179 */       DefaultTableLens table_contents = null;
/* 180 */       DefaultTableLens rowCountTable = null;
/* 181 */       DefaultTableLens columnHeaderTable = null;
/* 182 */       DefaultTableLens subTable = null;
/* 183 */       DefaultTableLens monthTableLens = null;
/* 184 */       DefaultTableLens configTableLens = null;
/* 185 */       DefaultTableLens dateTableLens = null;
/*     */       
/* 187 */       rowCountTable = new DefaultTableLens(2, 10000);
/*     */ 
/*     */       
/* 190 */       int nextRow = 0;
/*     */       
/* 192 */       boolean shade = true;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 199 */       Form reportForm = (Form)context.getSessionValue("reportForm");
/*     */       
/* 201 */       Calendar beginStDate = (reportForm.getStringValue("beginDate") != null && 
/* 202 */         reportForm.getStringValue("beginDate").length() > 0) ? 
/* 203 */         MilestoneHelper.getDate(reportForm.getStringValue("beginDate")) : null;
/*     */       
/* 205 */       Calendar endStDate = (reportForm.getStringValue("endDate") != null && 
/* 206 */         reportForm.getStringValue("endDate").length() > 0) ? 
/* 207 */         MilestoneHelper.getDate(reportForm.getStringValue("endDate")) : null;
/*     */       
/* 209 */       report.setElement("crs_startdate", MilestoneHelper.getFormatedDate(beginStDate));
/* 210 */       report.setElement("crs_enddate", MilestoneHelper.getFormatedDate(endStDate));
/*     */       
/* 212 */       SimpleDateFormat formatter = new SimpleDateFormat("MMMM d, yyyy");
/* 213 */       String todayLong = formatter.format(new Date());
/* 214 */       report.setElement("crs_bottomdate", todayLong);
/*     */ 
/*     */       
/* 217 */       Hashtable selTable = groupSelectionsByLabelAndSubconfig(selections);
/* 218 */       Enumeration companies = selTable.keys();
/* 219 */       Vector companyVector = new Vector();
/* 220 */       while (companies.hasMoreElements()) {
/* 221 */         companyVector.addElement(companies.nextElement());
/*     */       }
/*     */       
/* 224 */       int numSubconfigs = 0;
/* 225 */       for (int i = 0; i < companyVector.size(); i++) {
/*     */         
/* 227 */         String companyName = (companyVector.elementAt(i) != null) ? (String)companyVector.elementAt(i) : "";
/* 228 */         Hashtable subconfigTable = (Hashtable)selTable.get(companyName);
/* 229 */         if (subconfigTable != null)
/*     */         {
/* 231 */           numSubconfigs += subconfigTable.size();
/*     */         }
/*     */       } 
/*     */ 
/*     */       
/* 236 */       int numExtraRows = companyVector.size() * 2 - 1 + numSubconfigs * 6;
/*     */ 
/*     */ 
/*     */       
/* 240 */       int numSelections = selections.size() * 2;
/* 241 */       int numRows = numSelections + numExtraRows;
/*     */ 
/*     */ 
/*     */       
/* 245 */       int numColumns = 9;
/*     */ 
/*     */       
/* 248 */       nextRow = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 254 */       Object[] companyArray = companyVector.toArray();
/* 255 */       Arrays.sort(companyArray, new StringComparator());
/*     */       
/* 257 */       for (int n = 0; n < companyArray.length; n++)
/*     */       {
/* 259 */         String company = (String)companyArray[n];
/* 260 */         String companyHeaderText = !company.trim().equals("") ? company : "Other";
/*     */         
/* 262 */         hbandType = new SectionBand(report);
/* 263 */         hbandType.setHeight(0.95F);
/* 264 */         hbandType.setShrinkToFit(true);
/* 265 */         hbandType.setVisible(true);
/*     */         
/* 267 */         nextRow = 0;
/*     */ 
/*     */         
/* 270 */         table_contents = new DefaultTableLens(1, numColumns);
/*     */         
/* 272 */         table_contents.setColBorder(0);
/* 273 */         table_contents.setRowBorder(528384);
/*     */         
/* 275 */         table_contents.setRowBorderColor(Color.white);
/* 276 */         table_contents.setRowBorder(-1, 0);
/*     */ 
/*     */         
/* 279 */         table_contents.setColWidth(0, 260);
/* 280 */         table_contents.setColWidth(1, 320);
/* 281 */         table_contents.setColWidth(2, 140);
/* 282 */         table_contents.setColWidth(3, 150);
/* 283 */         table_contents.setColWidth(4, 120);
/* 284 */         table_contents.setColWidth(5, 65);
/* 285 */         table_contents.setColWidth(6, 80);
/* 286 */         table_contents.setColWidth(7, 60);
/* 287 */         table_contents.setColWidth(8, 140);
/*     */ 
/*     */         
/* 290 */         table_contents.setSpan(nextRow, 0, new Dimension(numColumns, 1));
/* 291 */         table_contents.setObject(nextRow, 0, companyHeaderText);
/*     */         
/* 293 */         table_contents.setRowBorder(nextRow, 0);
/*     */         
/* 295 */         table_contents.setRowBackground(nextRow, Color.black);
/* 296 */         table_contents.setRowForeground(nextRow, Color.white);
/*     */         
/* 298 */         table_contents.setRowFont(nextRow, new Font("Arial", 3, 16));
/*     */         
/* 300 */         hbandType.addTable(table_contents, new Rectangle(0, 0, 800, 30));
/*     */         
/* 302 */         nextRow = 0;
/*     */         
/* 304 */         columnHeaderTable = new DefaultTableLens(1, numColumns);
/*     */ 
/*     */         
/* 307 */         columnHeaderTable.setColWidth(0, 260);
/* 308 */         columnHeaderTable.setColWidth(1, 320);
/* 309 */         columnHeaderTable.setColWidth(2, 140);
/* 310 */         columnHeaderTable.setColWidth(3, 150);
/* 311 */         columnHeaderTable.setColWidth(4, 120);
/* 312 */         columnHeaderTable.setColWidth(5, 65);
/* 313 */         columnHeaderTable.setColWidth(6, 80);
/* 314 */         columnHeaderTable.setColWidth(7, 60);
/* 315 */         columnHeaderTable.setColWidth(8, 140);
/*     */         
/* 317 */         columnHeaderTable.setColBorder(0);
/* 318 */         columnHeaderTable.setRowBorder(nextRow - 1, 0);
/* 319 */         columnHeaderTable.setRowBorder(nextRow, 4097);
/* 320 */         columnHeaderTable.setRowBorderColor(nextRow, Color.black);
/* 321 */         columnHeaderTable.setRowAlignment(nextRow, 32);
/*     */ 
/*     */ 
/*     */         
/* 325 */         columnHeaderTable.setObject(nextRow, 0, "\nArtist");
/*     */         
/* 327 */         columnHeaderTable.setObject(nextRow, 1, "\nTitle/B Side");
/* 328 */         columnHeaderTable.setObject(nextRow, 2, "\nSub-Config");
/* 329 */         columnHeaderTable.setObject(nextRow, 3, "Local\nProduct #");
/* 330 */         columnHeaderTable.setObject(nextRow, 4, "\nUPC");
/* 331 */         columnHeaderTable.setAlignment(nextRow, 4, 33);
/* 332 */         columnHeaderTable.setObject(nextRow, 5, "\nPrice");
/* 333 */         columnHeaderTable.setAlignment(nextRow, 5, 36);
/* 334 */         columnHeaderTable.setObject(nextRow, 6, "Street/\nShip\nDate");
/* 335 */         columnHeaderTable.setAlignment(nextRow, 6, 36);
/* 336 */         columnHeaderTable.setObject(nextRow, 7, "Impact\nDate");
/* 337 */         columnHeaderTable.setAlignment(nextRow, 7, 34);
/* 338 */         columnHeaderTable.setObject(nextRow, 8, "\nComments");
/* 339 */         columnHeaderTable.setRowFont(nextRow, new Font("Arial", 3, 10));
/* 340 */         columnHeaderTable.setRowBorder(nextRow, 4097);
/* 341 */         columnHeaderTable.setRowBorderColor(nextRow, Color.black);
/*     */         
/* 343 */         hbandType.addTable(columnHeaderTable, new Rectangle(0, 30, 800, 60));
/* 344 */         hbandType.setBottomBorder(0);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 350 */         Hashtable subconfigTable = (Hashtable)selTable.get(company);
/* 351 */         if (subconfigTable != null) {
/*     */           
/* 353 */           Enumeration subconfigs = subconfigTable.keys();
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 358 */           Vector subconfigVector = new Vector();
/*     */           
/* 360 */           while (subconfigs.hasMoreElements()) {
/* 361 */             subconfigVector.add((String)subconfigs.nextElement());
/*     */           }
/* 363 */           Object[] subconfigsArray = subconfigVector.toArray();
/* 364 */           Arrays.sort(subconfigsArray, new StringComparator());
/*     */           
/* 366 */           for (int scIndex = 0; scIndex < subconfigsArray.length; scIndex++) {
/*     */             
/* 368 */             String subconfig = (String)subconfigsArray[scIndex];
/*     */             
/* 370 */             configTableLens = new DefaultTableLens(1, numColumns);
/* 371 */             hbandCategory = new SectionBand(report);
/* 372 */             hbandCategory.setHeight(0.25F);
/* 373 */             hbandCategory.setShrinkToFit(true);
/* 374 */             hbandCategory.setVisible(true);
/* 375 */             hbandCategory.setBottomBorder(0);
/* 376 */             hbandCategory.setLeftBorder(0);
/* 377 */             hbandCategory.setRightBorder(0);
/* 378 */             hbandCategory.setTopBorder(0);
/*     */             
/* 380 */             nextRow = 0;
/*     */ 
/*     */             
/* 383 */             configTableLens.setAlignment(nextRow, 0, 2);
/* 384 */             configTableLens.setSpan(nextRow, 0, new Dimension(numColumns, 1));
/* 385 */             configTableLens.setObject(nextRow, 0, subconfig);
/* 386 */             configTableLens.setRowFont(nextRow, new Font("Arial", 3, 12));
/*     */             
/* 388 */             hbandCategory.addTable(configTableLens, new Rectangle(800, 800));
/*     */             
/* 390 */             footer.setVisible(true);
/* 391 */             footer.setHeight(0.1F);
/* 392 */             footer.setShrinkToFit(false);
/* 393 */             footer.setBottomBorder(0);
/*     */             
/* 395 */             group = new DefaultSectionLens(null, group, spacer);
/* 396 */             group = new DefaultSectionLens(null, group, hbandCategory);
/* 397 */             group = new DefaultSectionLens(null, group, spacer);
/*     */ 
/*     */             
/* 400 */             selections = (Vector)subconfigTable.get(subconfig);
/* 401 */             if (selections == null) {
/* 402 */               selections = new Vector();
/*     */             }
/*     */ 
/*     */             
/* 406 */             int count = 2;
/* 407 */             int numRec = selections.size();
/* 408 */             int chunkSize = numRec / 10;
/*     */ 
/*     */             
/* 411 */             for (int i = 0; i < selections.size(); i++) {
/*     */ 
/*     */               
/*     */               try {
/*     */                 
/* 416 */                 int myPercent = i / chunkSize;
/* 417 */                 if (myPercent > 1 && myPercent < 10)
/* 418 */                   count = myPercent; 
/* 419 */                 HttpServletResponse sresponse = context.getResponse();
/* 420 */                 context.putDelivery("status", new String("start_report"));
/* 421 */                 context.putDelivery("percent", new String(String.valueOf(count * 10)));
/* 422 */                 context.includeJSP("status.jsp", "hiddenFrame");
/* 423 */                 sresponse.setContentType("text/plain");
/* 424 */                 sresponse.flushBuffer();
/*     */               }
/* 426 */               catch (Exception exception) {}
/*     */ 
/*     */ 
/*     */               
/* 430 */               Selection sel = (Selection)selections.elementAt(i);
/*     */               
/* 432 */               nextRow = 0;
/* 433 */               subTable = new DefaultTableLens(2, numColumns);
/*     */               
/* 435 */               subTable.setRowBorderColor(Color.white);
/*     */ 
/*     */               
/* 438 */               subTable.setColWidth(0, 260);
/* 439 */               subTable.setColWidth(1, 320);
/* 440 */               subTable.setColWidth(2, 140);
/* 441 */               subTable.setColWidth(3, 150);
/* 442 */               subTable.setColWidth(4, 120);
/* 443 */               subTable.setColWidth(5, 65);
/* 444 */               subTable.setColWidth(6, 80);
/* 445 */               subTable.setColWidth(7, 60);
/* 446 */               subTable.setColWidth(8, 140);
/*     */ 
/*     */               
/* 449 */               String bSide = (sel.getBSide() != null && !sel.getBSide().trim().equals("")) ? (
/* 450 */                 "B Side:  " + sel.getBSide()) : "";
/*     */ 
/*     */ 
/*     */               
/* 454 */               String txtSubconfig = "";
/* 455 */               if (sel.getSelectionSubConfig() != null) {
/* 456 */                 txtSubconfig = sel.getSelectionSubConfig().getSelectionSubConfigurationAbbreviation();
/*     */               }
/*     */               
/* 459 */               String upc = (sel.getUpc() != null) ? sel.getUpc() : "";
/*     */ 
/*     */               
/* 462 */               upc = MilestoneHelper_2.getRMSReportFormat(upc, "UPC", sel.getIsDigital());
/*     */ 
/*     */               
/* 465 */               String localProductNumber = "";
/* 466 */               if (SelectionManager.getLookupObjectValue(sel.getPrefixID()) == null) {
/* 467 */                 localProductNumber = "";
/*     */               } else {
/* 469 */                 localProductNumber = sel.getSelectionNo();
/*     */               } 
/*     */               
/* 472 */               if (sel.getPrefixID() != null && sel.getPrefixID().getAbbreviation() != null) {
/* 473 */                 localProductNumber = String.valueOf(sel.getPrefixID().getAbbreviation()) + localProductNumber;
/*     */               }
/*     */ 
/*     */ 
/*     */               
/* 478 */               String price = "0.00";
/* 479 */               if (sel.getPriceCode() != null && 
/* 480 */                 sel.getPriceCode().getTotalCost() > 0.0F) {
/* 481 */                 price = MilestoneHelper.formatDollarPrice(sel.getPriceCode().getTotalCost());
/*     */               }
/*     */               
/* 484 */               String streetDate = MilestoneHelper.getFormatedDate(sel.getStreetDate());
/*     */               
/* 486 */               String status = (sel.getSelectionStatus() != null && sel.getSelectionStatus().getName() != null) ? 
/* 487 */                 sel.getSelectionStatus().getName() : "";
/*     */               
/* 489 */               if (status.equalsIgnoreCase("TBS")) {
/* 490 */                 streetDate = "TBS " + streetDate;
/*     */               }
/* 492 */               else if (status.equalsIgnoreCase("In The Works")) {
/* 493 */                 streetDate = "ITW " + streetDate;
/*     */               } 
/*     */               
/* 496 */               String comment = (sel.getSelectionComments() != null) ? sel.getSelectionComments() : "";
/*     */ 
/*     */               
/* 499 */               subTable.setRowInsets(nextRow, new Insets(0, 0, 0, 0));
/*     */               
/* 501 */               subTable.setObject(nextRow, 0, sel.getArtist());
/*     */               
/* 503 */               subTable.setObject(nextRow, 1, sel.getTitle());
/* 504 */               subTable.setObject(nextRow, 2, txtSubconfig);
/* 505 */               subTable.setObject(nextRow, 3, localProductNumber);
/* 506 */               subTable.setObject(nextRow, 4, upc);
/* 507 */               subTable.setObject(nextRow, 5, "$" + price);
/* 508 */               subTable.setColLineWrap(5, false);
/* 509 */               subTable.setObject(nextRow, 6, streetDate);
/* 510 */               subTable.setObject(nextRow, 7, MilestoneHelper.getFormatedDate(sel.getImpactDate()));
/* 511 */               subTable.setObject(nextRow, 8, comment);
/*     */               
/* 513 */               subTable.setColAlignment(0, 9);
/* 514 */               subTable.setSpan(nextRow, 0, new Dimension(1, 2));
/* 515 */               subTable.setColAlignment(1, 33);
/* 516 */               subTable.setColAlignment(2, 9);
/* 517 */               subTable.setSpan(nextRow, 2, new Dimension(1, 2));
/* 518 */               subTable.setColAlignment(3, 9);
/* 519 */               subTable.setSpan(nextRow, 3, new Dimension(1, 2));
/* 520 */               subTable.setColAlignment(4, 9);
/* 521 */               subTable.setSpan(nextRow, 4, new Dimension(1, 2));
/* 522 */               subTable.setColAlignment(5, 12);
/* 523 */               subTable.setSpan(nextRow, 5, new Dimension(1, 2));
/* 524 */               subTable.setColAlignment(6, 12);
/* 525 */               subTable.setSpan(nextRow, 6, new Dimension(1, 2));
/* 526 */               subTable.setColAlignment(7, 12);
/* 527 */               subTable.setSpan(nextRow, 7, new Dimension(1, 2));
/* 528 */               subTable.setColAlignment(8, 9);
/* 529 */               subTable.setSpan(nextRow, 8, new Dimension(1, 2));
/*     */               
/* 531 */               subTable.setRowBorder(nextRow, 1, 0);
/* 532 */               subTable.setRowBorder(nextRow - 1, 0);
/* 533 */               subTable.setRowFont(nextRow, new Font("Arial", 0, 8));
/*     */               
/* 535 */               subTable.setRowAutoSize(true);
/*     */ 
/*     */               
/* 538 */               if (!shade) {
/* 539 */                 subTable.setFont(nextRow, 1, new Font("Arial", 0, 8));
/*     */               } else {
/*     */                 
/* 542 */                 subTable.setRowBackground(nextRow, new Color(235, 235, 235, 0));
/*     */               } 
/*     */ 
/*     */               
/* 546 */               nextRow++;
/*     */               
/* 548 */               subTable.setAlignment(nextRow, 1, 9);
/*     */               
/* 550 */               String[] checkStrings = { comment, sel.getArtist(), sel.getTitle() };
/* 551 */               int[] checkStringsLength = { 35, 50, 20 };
/*     */               
/* 553 */               if (bSide.equals("") && comment.equals(""))
/*     */               {
/* 555 */                 subTable.setRowHeight(nextRow, 1);
/*     */               }
/*     */               
/* 558 */               int addExtraLines = MilestoneHelper.lineCount(checkStrings, checkStringsLength);
/*     */               
/* 560 */               for (int z = 0; z < addExtraLines - 1; z++) {
/* 561 */                 bSide = String.valueOf(bSide) + "\n";
/*     */               }
/*     */               
/* 564 */               if (!shade) {
/* 565 */                 subTable.setFont(nextRow, 1, new Font("Arial", 0, 8));
/*     */               } else {
/* 567 */                 subTable.setFont(nextRow, 1, new Font("Arial", 1, 8));
/* 568 */                 subTable.setRowBackground(nextRow, new Color(235, 235, 235, 0));
/*     */               } 
/*     */ 
/*     */               
/* 572 */               shade = !shade;
/*     */               
/* 574 */               subTable.setObject(nextRow, 0, "");
/* 575 */               subTable.setObject(nextRow, 1, bSide);
/* 576 */               subTable.setObject(nextRow, 2, "");
/* 577 */               subTable.setObject(nextRow, 3, "");
/* 578 */               subTable.setObject(nextRow, 4, "");
/* 579 */               subTable.setObject(nextRow, 5, "");
/* 580 */               subTable.setObject(nextRow, 6, "");
/* 581 */               subTable.setObject(nextRow, 7, "");
/* 582 */               subTable.setObject(nextRow, 8, "");
/*     */               
/* 584 */               subTable.setRowFont(nextRow, new Font("Arial", 2, 8));
/* 585 */               subTable.setColAlignment(1, 9);
/*     */               
/* 587 */               subTable.setRowBorderColor(nextRow, Color.white);
/* 588 */               subTable.setRowBorderColor(nextRow - 1, Color.white);
/* 589 */               subTable.setRowBorderColor(nextRow + 1, Color.white);
/*     */               
/* 591 */               subTable.setColBorder(0);
/*     */               
/* 593 */               body = new SectionBand(report);
/*     */               
/* 595 */               double lfLineCount = 1.5D;
/*     */               
/* 597 */               if (addExtraLines > 0) {
/*     */                 
/* 599 */                 body.setHeight(1.5F);
/*     */               }
/*     */               else {
/*     */                 
/* 603 */                 body.setHeight(0.8F);
/*     */               } 
/*     */               
/* 606 */               if (addExtraLines > 3) {
/*     */                 
/* 608 */                 if (lfLineCount < addExtraLines * 0.3D) {
/* 609 */                   lfLineCount = addExtraLines * 0.3D;
/*     */                 }
/* 611 */                 body.setHeight((float)lfLineCount);
/*     */               }
/*     */               else {
/*     */                 
/* 615 */                 body.setHeight(0.8F);
/*     */               } 
/*     */               
/* 618 */               body.addTable(subTable, new Rectangle(800, 800));
/* 619 */               body.setBottomBorder(0);
/* 620 */               body.setTopBorder(0);
/* 621 */               body.setShrinkToFit(true);
/* 622 */               body.setVisible(true);
/*     */ 
/*     */               
/* 625 */               group = new DefaultSectionLens(null, group, body);
/*     */             } 
/*     */           } 
/*     */         } 
/* 629 */         group = new DefaultSectionLens(hbandType, group, null);
/* 630 */         report.addSection(group, rowCountTable);
/* 631 */         report.addPageBreak();
/* 632 */         group = null;
/*     */       }
/*     */     
/*     */     }
/* 636 */     catch (Exception e) {
/*     */       
/* 638 */       System.out.println(">>>>>>>>ReportHandler.fillEntRelScheduleForPrint(): exception: " + e);
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
/*     */   public static Hashtable groupSelectionsByLabelAndSubconfig(Vector selections) {
/* 656 */     Hashtable groupedByCompanyAndSubconfig = new Hashtable();
/* 657 */     if (selections == null) {
/* 658 */       return groupedByCompanyAndSubconfig;
/*     */     }
/* 660 */     for (int i = 0; i < selections.size(); i++) {
/*     */       
/* 662 */       Selection sel = (Selection)selections.elementAt(i);
/* 663 */       if (sel != null) {
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 668 */         String familyName = "";
/* 669 */         String labelName = "";
/* 670 */         String configName = "";
/* 671 */         String subconfigName = "";
/*     */         
/* 673 */         Family family = sel.getFamily();
/*     */ 
/*     */         
/* 676 */         SelectionConfiguration config = sel.getSelectionConfig();
/* 677 */         SelectionSubConfiguration subconfig = sel.getSelectionSubConfig();
/*     */         
/* 679 */         if (family != null) {
/* 680 */           familyName = (family.getName() == null) ? "" : family.getName();
/*     */         }
/*     */ 
/*     */ 
/*     */         
/* 685 */         labelName = sel.getImprint().trim();
/*     */         
/* 687 */         if (config != null)
/* 688 */           configName = (config.getSelectionConfigurationName() == null) ? 
/* 689 */             "" : config.getSelectionConfigurationName(); 
/* 690 */         if (subconfig != null) {
/* 691 */           subconfigName = (subconfig.getSelectionSubConfigurationName() == null) ? 
/* 692 */             "" : subconfig.getSelectionSubConfigurationName();
/*     */         }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 699 */         if (subconfigName.toUpperCase().indexOf("EP/SAMPLER") > -1 || 
/* 700 */           subconfigName.toUpperCase().indexOf("FULL") > -1 || subconfigName.equalsIgnoreCase("DUALDISC")) {
/*     */ 
/*     */           
/* 703 */           subconfigName = "Full Length";
/*     */         }
/* 705 */         else if (!subconfigName.equals("")) {
/*     */           
/* 707 */           subconfigName = "Singles";
/*     */         } 
/*     */ 
/*     */ 
/*     */         
/* 712 */         Hashtable labelSubTable = (Hashtable)groupedByCompanyAndSubconfig.get(labelName);
/*     */ 
/*     */         
/* 715 */         if (labelSubTable == null)
/*     */         {
/* 717 */           for (Enumeration e = groupedByCompanyAndSubconfig.keys(); e.hasMoreElements(); ) {
/*     */             
/* 719 */             String keyStr = (String)e.nextElement();
/*     */             
/* 721 */             if (keyStr.equalsIgnoreCase(labelName)) {
/*     */               
/* 723 */               labelSubTable = (Hashtable)groupedByCompanyAndSubconfig.get(keyStr);
/*     */               
/*     */               break;
/*     */             } 
/*     */           } 
/*     */         }
/* 729 */         if (labelSubTable == null) {
/*     */ 
/*     */           
/* 732 */           labelSubTable = new Hashtable();
/* 733 */           groupedByCompanyAndSubconfig.put(labelName, labelSubTable);
/*     */         } 
/*     */ 
/*     */         
/* 737 */         Vector selectionsForSubconfig = (Vector)labelSubTable.get(subconfigName);
/* 738 */         if (selectionsForSubconfig == null) {
/*     */ 
/*     */           
/* 741 */           selectionsForSubconfig = new Vector();
/* 742 */           labelSubTable.put(subconfigName, selectionsForSubconfig);
/*     */         } 
/*     */ 
/*     */         
/* 746 */         selectionsForSubconfig.addElement(sel);
/*     */       } 
/*     */     } 
/*     */     
/* 750 */     return groupedByCompanyAndSubconfig;
/*     */   }
/*     */ }


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\EntReleaseSchedByLabelSubHandler.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */