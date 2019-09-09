/*     */ package WEB-INF.classes.com.universal.milestone;
/*     */ 
/*     */ import com.techempower.ComponentLog;
/*     */ import com.techempower.gemini.Context;
/*     */ import com.techempower.gemini.GeminiApplication;
/*     */ import com.universal.milestone.AddsAndMovesForPrintSubHandler;
/*     */ import com.universal.milestone.Company;
/*     */ import com.universal.milestone.Family;
/*     */ import com.universal.milestone.Form;
/*     */ import com.universal.milestone.MilestoneHelper;
/*     */ import com.universal.milestone.MilestoneHelper_2;
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
/*     */ 
/*     */ 
/*     */ public class AddsAndMovesForPrintSubHandler
/*     */   extends SecureHandler
/*     */ {
/*     */   public static final String COMPONENT_CODE = "hNsl";
/*     */   public GeminiApplication application;
/*     */   public ComponentLog log;
/*     */   public static Context m_context;
/*     */   
/*     */   public AddsAndMovesForPrintSubHandler(GeminiApplication application) {
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
/*     */   protected static void fillAddsAndMovesForPrint(XStyleSheet report, Context context) {
/*  92 */     Color SHADED_AREA_COLOR = Color.lightGray;
/*  93 */     int COL_LINE_STYLE = 4097;
/*  94 */     int HEADER_FONT_SIZE = 12;
/*  95 */     int NUM_COLUMNS = 10;
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
/* 107 */     m_context = context;
/*     */     
/* 109 */     footer.setVisible(true);
/* 110 */     footer.setHeight(0.1F);
/* 111 */     footer.setShrinkToFit(false);
/* 112 */     footer.setBottomBorder(0);
/*     */     
/* 114 */     spacer.setVisible(true);
/* 115 */     spacer.setHeight(0.05F);
/* 116 */     spacer.setShrinkToFit(false);
/* 117 */     spacer.setBottomBorder(0);
/*     */     
/* 119 */     selectionSpacer.setVisible(true);
/* 120 */     selectionSpacer.setHeight(0.03F);
/* 121 */     selectionSpacer.setShrinkToFit(false);
/* 122 */     selectionSpacer.setBottomBorder(0);
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 127 */       HttpServletResponse sresponse = context.getResponse();
/* 128 */       context.putDelivery("status", new String("start_gathering"));
/* 129 */       context.includeJSP("status.jsp", "hiddenFrame");
/* 130 */       sresponse.setContentType("text/plain");
/* 131 */       sresponse.flushBuffer();
/*     */     }
/* 133 */     catch (Exception exception) {}
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 138 */     Vector selections = MilestoneHelper.getSelectionsForReport(context);
/*     */ 
/*     */     
/*     */     try {
/* 142 */       HttpServletResponse sresponse = context.getResponse();
/* 143 */       context.putDelivery("status", new String("start_report"));
/* 144 */       context.putDelivery("percent", new String("10"));
/* 145 */       context.includeJSP("status.jsp", "hiddenFrame");
/* 146 */       sresponse.setContentType("text/plain");
/* 147 */       sresponse.flushBuffer();
/*     */     }
/* 149 */     catch (Exception exception) {}
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
/*     */     try {
/* 171 */       DefaultTableLens table_contents = null;
/* 172 */       DefaultTableLens rowCountTable = null;
/* 173 */       DefaultTableLens columnHeaderTable = null;
/* 174 */       DefaultTableLens subTable = null;
/* 175 */       DefaultTableLens monthTableLens = null;
/* 176 */       DefaultTableLens configTableLens = null;
/* 177 */       DefaultTableLens dateTableLens = null;
/*     */ 
/*     */ 
/*     */       
/* 181 */       rowCountTable = new DefaultTableLens(2, 10000);
/*     */ 
/*     */       
/* 184 */       int nextRow = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 191 */       Form reportForm = (Form)context.getSessionValue("reportForm");
/*     */ 
/*     */       
/* 194 */       String addsMovesFlag = reportForm.getStringValue("AddsMovesBoth");
/* 195 */       if (addsMovesFlag == null) {
/* 196 */         addsMovesFlag = "Both";
/*     */       }
/* 198 */       Calendar beginStDate = (reportForm.getStringValue("beginEffectiveDate") != null && 
/* 199 */         reportForm.getStringValue("beginEffectiveDate").length() > 0) ? 
/* 200 */         MilestoneHelper.getDate(reportForm.getStringValue("beginEffectiveDate")) : null;
/*     */       
/* 202 */       Calendar endStDate = (reportForm.getStringValue("endEffectiveDate") != null && 
/* 203 */         reportForm.getStringValue("endEffectiveDate").length() > 0) ? 
/* 204 */         MilestoneHelper.getDate(reportForm.getStringValue("endEffectiveDate")) : null;
/*     */       
/* 206 */       report.setElement("crs_startdate", MilestoneHelper.getFormatedDate(beginStDate));
/* 207 */       report.setElement("crs_enddate", MilestoneHelper.getFormatedDate(endStDate));
/*     */       
/* 209 */       SimpleDateFormat formatter = new SimpleDateFormat("MMMM d, yyyy");
/* 210 */       String todayLong = formatter.format(new Date());
/* 211 */       report.setElement("crs_bottomdate", todayLong);
/*     */ 
/*     */       
/* 214 */       Hashtable selTable = groupSelectionsByAddsMoves(selections);
/*     */       
/* 216 */       Enumeration AddsMoves = selTable.keys();
/*     */ 
/*     */       
/* 219 */       Vector AddMoveVector = new Vector();
/* 220 */       while (AddsMoves.hasMoreElements()) {
/* 221 */         AddMoveVector.addElement(AddsMoves.nextElement());
/*     */       }
/*     */       
/* 224 */       int numSubconfigs = 0;
/* 225 */       for (int i = 0; i < AddMoveVector.size(); i++) {
/*     */         
/* 227 */         String modifcation = (AddMoveVector.elementAt(i) != null) ? (String)AddMoveVector.elementAt(i) : "";
/* 228 */         Hashtable subconfigTable = (Hashtable)selTable.get(modifcation);
/* 229 */         if (subconfigTable != null)
/*     */         {
/* 231 */           numSubconfigs += subconfigTable.size();
/*     */         }
/*     */       } 
/*     */ 
/*     */       
/* 236 */       int numExtraRows = AddMoveVector.size() * 2 - 1 + numSubconfigs * 6;
/*     */ 
/*     */ 
/*     */       
/* 240 */       int numSelections = selections.size() * 2;
/* 241 */       int numRows = numSelections + numExtraRows;
/*     */ 
/*     */       
/* 244 */       int numColumns = 6;
/*     */ 
/*     */       
/* 247 */       nextRow = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 253 */       Object[] AddMoveArray = AddMoveVector.toArray();
/* 254 */       Arrays.sort(AddMoveArray, new StringComparator());
/*     */ 
/*     */ 
/*     */       
/* 258 */       for (int n = 0; n < AddMoveArray.length; n++) {
/*     */         
/* 260 */         String company = (String)AddMoveArray[n];
/* 261 */         String companyHeaderText = !company.trim().equals("") ? company : "Other";
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 266 */         Hashtable subconfigTable = (Hashtable)selTable.get(company);
/* 267 */         if (subconfigTable != null)
/*     */         {
/* 269 */           Enumeration subconfigs = subconfigTable.keys();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 276 */           Vector subconfigVector = new Vector();
/*     */           
/* 278 */           while (subconfigs.hasMoreElements()) {
/* 279 */             subconfigVector.add((String)subconfigs.nextElement());
/*     */           }
/* 281 */           Object[] subconfigsArray = subconfigVector.toArray();
/* 282 */           Arrays.sort(subconfigsArray, new StringComparator());
/*     */           
/* 284 */           for (int scIndex = 0; scIndex < subconfigsArray.length; scIndex++)
/*     */           {
/* 286 */             String subconfig = (String)subconfigsArray[scIndex];
/* 287 */             hbandType = new SectionBand(report);
/* 288 */             hbandType.setHeight(0.65F);
/* 289 */             hbandType.setShrinkToFit(true);
/* 290 */             hbandType.setVisible(true);
/*     */             
/* 292 */             nextRow = 0;
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
/* 311 */             nextRow = 0;
/*     */             
/* 313 */             columnHeaderTable = new DefaultTableLens(1, 8);
/*     */ 
/*     */             
/* 316 */             columnHeaderTable.setColWidth(0, 100);
/* 317 */             columnHeaderTable.setColWidth(1, 280);
/* 318 */             columnHeaderTable.setColWidth(2, 290);
/*     */             
/* 320 */             columnHeaderTable.setColWidth(3, 110);
/* 321 */             columnHeaderTable.setColWidth(4, 130);
/* 322 */             columnHeaderTable.setColWidth(5, 110);
/* 323 */             columnHeaderTable.setColWidth(6, 110);
/*     */             
/* 325 */             columnHeaderTable.setColBorder(0);
/* 326 */             columnHeaderTable.setRowBorder(nextRow - 1, 0);
/* 327 */             columnHeaderTable.setRowBorder(nextRow, 4097);
/* 328 */             columnHeaderTable.setRowBorderColor(nextRow, Color.black);
/*     */ 
/*     */ 
/*     */             
/* 332 */             columnHeaderTable.setAlignment(nextRow, 0, 18);
/* 333 */             if (subconfig.equalsIgnoreCase("Moves")) {
/* 334 */               columnHeaderTable.setObject(nextRow, 0, "Date\nChanged");
/*     */             } else {
/* 336 */               columnHeaderTable.setObject(nextRow, 0, "Date\nAdded");
/*     */             } 
/* 338 */             columnHeaderTable.setAlignment(nextRow, 1, 16);
/* 339 */             columnHeaderTable.setObject(nextRow, 1, "\nArtist");
/* 340 */             columnHeaderTable.setObject(nextRow, 2, "\nTitle");
/*     */ 
/*     */ 
/*     */             
/* 344 */             columnHeaderTable.setObject(nextRow, 3, "Imprint");
/*     */             
/* 346 */             columnHeaderTable.setAlignment(nextRow, 4, 18);
/* 347 */             columnHeaderTable.setObject(nextRow, 4, "Local Prod #\nUPC");
/* 348 */             columnHeaderTable.setAlignment(nextRow, 5, 18);
/* 349 */             columnHeaderTable.setObject(nextRow, 5, "\nSubconfig");
/* 350 */             columnHeaderTable.setAlignment(nextRow, 6, 18);
/* 351 */             columnHeaderTable.setObject(nextRow, 6, "In House/\nStreet Date");
/* 352 */             columnHeaderTable.setRowFont(nextRow, new Font("Arial", 3, 10));
/* 353 */             columnHeaderTable.setRowBorder(nextRow, 4097);
/* 354 */             columnHeaderTable.setRowBorderColor(nextRow, Color.black);
/*     */ 
/*     */ 
/*     */             
/* 358 */             Vector selectionsTemp = (Vector)subconfigTable.get(subconfig);
/* 359 */             if (selectionsTemp != null && selectionsTemp.size() > 0 && (
/* 360 */               addsMovesFlag.equalsIgnoreCase("Both") || 
/* 361 */               addsMovesFlag.equalsIgnoreCase(subconfig))) {
/*     */ 
/*     */ 
/*     */               
/* 365 */               hbandType.addTable(columnHeaderTable, new Rectangle(0, 0, 800, 35));
/* 366 */               hbandType.setBottomBorder(0);
/* 367 */               hbandType.setTopBorder(0);
/*     */ 
/*     */ 
/*     */               
/* 371 */               configTableLens = new DefaultTableLens(1, 10);
/* 372 */               hbandCategory = new SectionBand(report);
/* 373 */               hbandCategory.setHeight(0.25F);
/* 374 */               hbandCategory.setShrinkToFit(true);
/* 375 */               hbandCategory.setVisible(true);
/* 376 */               hbandCategory.setBottomBorder(0);
/* 377 */               hbandCategory.setLeftBorder(0);
/* 378 */               hbandCategory.setRightBorder(0);
/* 379 */               hbandCategory.setTopBorder(0);
/*     */               
/* 381 */               nextRow = 0;
/*     */ 
/*     */               
/* 384 */               configTableLens.setAlignment(nextRow, 0, 2);
/* 385 */               configTableLens.setSpan(nextRow, 0, new Dimension(10, 1));
/* 386 */               configTableLens.setObject(nextRow, 0, subconfig);
/* 387 */               configTableLens.setRowFont(nextRow, new Font("Arial", 3, 12));
/*     */               
/* 389 */               hbandCategory.addTable(configTableLens, new Rectangle(800, 800));
/*     */               
/* 391 */               footer.setVisible(true);
/* 392 */               footer.setHeight(0.1F);
/* 393 */               footer.setShrinkToFit(false);
/* 394 */               footer.setBottomBorder(0);
/*     */               
/* 396 */               group = new DefaultSectionLens(null, group, spacer);
/* 397 */               group = new DefaultSectionLens(null, group, hbandCategory);
/* 398 */               group = new DefaultSectionLens(null, group, spacer);
/*     */             } 
/*     */ 
/*     */ 
/*     */             
/* 403 */             selections = (Vector)subconfigTable.get(subconfig);
/*     */ 
/*     */ 
/*     */             
/* 407 */             if (!addsMovesFlag.equalsIgnoreCase("Both") && !addsMovesFlag.equalsIgnoreCase(subconfig)) {
/* 408 */               selections = null;
/*     */             }
/* 410 */             if (selections == null) {
/* 411 */               selections = new Vector();
/*     */             }
/*     */ 
/*     */             
/* 415 */             int count = 2;
/* 416 */             int numRec = selections.size();
/* 417 */             int chunkSize = numRec / 10;
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 422 */             for (int i = 0; i < selections.size(); i++) {
/*     */ 
/*     */               
/*     */               try {
/*     */ 
/*     */                 
/* 428 */                 int myPercent = i / chunkSize;
/* 429 */                 if (myPercent > 1 && myPercent < 10)
/* 430 */                   count = myPercent; 
/* 431 */                 HttpServletResponse sresponse = context.getResponse();
/* 432 */                 context.putDelivery("status", new String("start_report"));
/* 433 */                 context.putDelivery("percent", new String(String.valueOf(count * 10)));
/* 434 */                 context.includeJSP("status.jsp", "hiddenFrame");
/* 435 */                 sresponse.setContentType("text/plain");
/* 436 */                 sresponse.flushBuffer();
/*     */               }
/* 438 */               catch (Exception exception) {}
/*     */ 
/*     */ 
/*     */               
/* 442 */               Selection sel = (Selection)selections.elementAt(i);
/*     */               
/* 444 */               nextRow = 0;
/* 445 */               subTable = new DefaultTableLens(2, 7);
/* 446 */               subTable.setRowBorderColor(Color.lightGray);
/*     */ 
/*     */               
/* 449 */               subTable.setColWidth(0, 100);
/* 450 */               subTable.setColWidth(1, 280);
/* 451 */               subTable.setColWidth(2, 290);
/*     */               
/* 453 */               subTable.setColWidth(3, 110);
/* 454 */               subTable.setColWidth(4, 130);
/* 455 */               subTable.setColWidth(5, 110);
/* 456 */               subTable.setColWidth(6, 110);
/*     */ 
/*     */               
/* 459 */               String bSide = "";
/*     */ 
/*     */               
/* 462 */               String upc = (sel.getUpc() != null) ? sel.getUpc() : "";
/*     */ 
/*     */               
/* 465 */               upc = MilestoneHelper_2.getRMSReportFormat(upc, "UPC", sel.getIsDigital());
/*     */               
/* 467 */               String localProductNumber = "";
/* 468 */               if (SelectionManager.getLookupObjectValue(sel.getPrefixID()) == null) {
/* 469 */                 localProductNumber = "";
/*     */               } else {
/* 471 */                 localProductNumber = sel.getSelectionNo();
/*     */               } 
/*     */ 
/*     */               
/* 475 */               String prefix = "";
/* 476 */               if (sel.getPrefixID() != null && sel.getPrefixID().getAbbreviation() != null)
/* 477 */                 prefix = sel.getPrefixID().getAbbreviation(); 
/* 478 */               localProductNumber = String.valueOf(prefix) + localProductNumber;
/*     */ 
/*     */ 
/*     */               
/* 482 */               String price = "0.00";
/* 483 */               if (sel.getPriceCode() != null && 
/* 484 */                 sel.getPriceCode().getTotalCost() > 0.0F) {
/* 485 */                 price = MilestoneHelper.formatDollarPrice(sel.getPriceCode().getTotalCost());
/*     */               }
/*     */               
/* 488 */               String streetDate = MilestoneHelper.getCustomFormatedDate(sel.getStreetDate(), "MM/dd/yy");
/*     */               
/* 490 */               String status = (sel.getSelectionStatus() != null && sel.getSelectionStatus().getName() != null) ? 
/* 491 */                 sel.getSelectionStatus().getName() : "";
/*     */               
/* 493 */               if (status.equalsIgnoreCase("TBS")) {
/* 494 */                 streetDate = "TBS " + streetDate;
/*     */               }
/*     */               
/* 497 */               String comment = (sel.getSelectionComments() != null) ? sel.getSelectionComments() : "";
/*     */ 
/*     */               
/* 500 */               String description = (sel.getSelectionSubConfig() != null && sel.getSelectionSubConfig().getSelectionSubConfigurationName() != null) ? 
/* 501 */                 sel.getSelectionSubConfig().getSelectionSubConfigurationName() : "";
/*     */ 
/*     */ 
/*     */               
/* 505 */               String imprint = sel.getImprint();
/*     */ 
/*     */               
/* 508 */               subTable.setRowInsets(nextRow, new Insets(0, 0, 0, 0));
/*     */               
/* 510 */               if (subconfig.equalsIgnoreCase("Moves")) {
/* 511 */                 subTable.setObject(nextRow, 0, MilestoneHelper.getCustomFormatedDate(sel.getLastStreetUpdateDate(), "MM/dd/yy"));
/*     */               } else {
/* 513 */                 subTable.setObject(nextRow, 0, MilestoneHelper.getCustomFormatedDate(sel.getOriginDate(), "MM/dd/yy"));
/* 514 */               }  subTable.setObject(nextRow, 1, sel.getArtist());
/* 515 */               subTable.setObject(nextRow, 2, sel.getTitle());
/*     */               
/* 517 */               subTable.setObject(nextRow, 3, imprint);
/* 518 */               subTable.setObject(nextRow, 4, String.valueOf(localProductNumber) + "\n" + upc);
/* 519 */               subTable.setObject(nextRow, 5, description);
/* 520 */               subTable.setColLineWrap(6, false);
/* 521 */               subTable.setObject(nextRow, 6, streetDate);
/*     */               
/* 523 */               subTable.setColAlignment(0, 9);
/* 524 */               subTable.setSpan(nextRow, 0, new Dimension(1, 2));
/* 525 */               subTable.setColAlignment(1, 33);
/* 526 */               subTable.setColAlignment(2, 9);
/* 527 */               subTable.setSpan(nextRow, 2, new Dimension(1, 2));
/* 528 */               subTable.setColAlignment(3, 9);
/* 529 */               subTable.setSpan(nextRow, 3, new Dimension(1, 2));
/* 530 */               subTable.setColAlignment(4, 9);
/* 531 */               subTable.setSpan(nextRow, 4, new Dimension(1, 2));
/* 532 */               subTable.setColAlignment(5, 10);
/* 533 */               subTable.setSpan(nextRow, 5, new Dimension(1, 2));
/* 534 */               subTable.setColAlignment(6, 10);
/* 535 */               subTable.setSpan(nextRow, 6, new Dimension(1, 2));
/*     */               
/* 537 */               subTable.setRowBorder(nextRow, 1, 0);
/* 538 */               subTable.setRowBorder(nextRow - 1, 0);
/* 539 */               subTable.setRowFont(nextRow, new Font("Arial", 0, 8));
/*     */               
/* 541 */               subTable.setRowAutoSize(true);
/*     */               
/* 543 */               nextRow++;
/*     */               
/* 545 */               subTable.setAlignment(nextRow, 1, 9);
/*     */               
/* 547 */               String[] checkStrings = { sel.getArtist(), sel.getTitle(), imprint };
/* 548 */               int[] checkStringsLength = { 30, 40, 20 };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */               
/* 555 */               int addExtraLines = MilestoneHelper.lineCount(checkStrings, checkStringsLength);
/*     */ 
/*     */ 
/*     */ 
/*     */               
/* 560 */               subTable.setObject(nextRow, 0, "");
/* 561 */               subTable.setObject(nextRow, 1, bSide);
/* 562 */               subTable.setObject(nextRow, 2, "");
/* 563 */               subTable.setObject(nextRow, 3, "");
/* 564 */               subTable.setObject(nextRow, 4, "");
/* 565 */               subTable.setObject(nextRow, 5, "");
/* 566 */               subTable.setObject(nextRow, 6, "");
/*     */               
/* 568 */               subTable.setRowFont(nextRow, new Font("Arial", 2, 8));
/* 569 */               subTable.setColAlignment(1, 9);
/* 570 */               subTable.setRowBorderColor(nextRow, Color.lightGray);
/* 571 */               subTable.setRowBorderColor(nextRow - 1, Color.lightGray);
/* 572 */               subTable.setRowBorderColor(nextRow + 1, Color.lightGray);
/* 573 */               subTable.setColBorder(0);
/*     */               
/* 575 */               body = new SectionBand(report);
/*     */               
/* 577 */               double lfLineCount = 1.5D;
/*     */               
/* 579 */               if (addExtraLines > 0) {
/*     */                 
/* 581 */                 body.setHeight(1.5F);
/*     */               }
/*     */               else {
/*     */                 
/* 585 */                 body.setHeight(0.8F);
/*     */               } 
/*     */               
/* 588 */               if (addExtraLines > 3) {
/*     */                 
/* 590 */                 if (lfLineCount < addExtraLines * 0.3D) {
/* 591 */                   lfLineCount = addExtraLines * 0.3D;
/*     */                 }
/* 593 */                 body.setHeight((float)lfLineCount);
/*     */               }
/*     */               else {
/*     */                 
/* 597 */                 body.setHeight(0.8F);
/*     */               } 
/*     */               
/* 600 */               body.addTable(subTable, new Rectangle(800, 800));
/* 601 */               body.setBottomBorder(0);
/* 602 */               body.setTopBorder(0);
/* 603 */               body.setShrinkToFit(true);
/* 604 */               body.setVisible(true);
/*     */ 
/*     */               
/* 607 */               group = new DefaultSectionLens(null, group, body);
/*     */             } 
/*     */             
/* 610 */             if (selections != null && selections.size() > 0) {
/*     */               
/* 612 */               group = new DefaultSectionLens(hbandType, group, null);
/* 613 */               report.addSection(group, rowCountTable);
/*     */             } 
/* 615 */             group = null;
/*     */           }
/*     */         
/*     */         }
/*     */       
/*     */       } 
/* 621 */     } catch (Exception e) {
/*     */       
/* 623 */       System.out.println(">>>>>>>>ReportHandler.fillAddsAndMovesForPrint(): exception: " + e);
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
/*     */   public static Hashtable groupSelectionsByAddsMoves(Vector selections) {
/* 641 */     Hashtable groupedByAddsMove = new Hashtable();
/*     */     
/* 643 */     if (selections == null) {
/* 644 */       return groupedByAddsMove;
/*     */     }
/* 646 */     Form reportForm = (Form)m_context.getSessionValue("reportForm");
/*     */     
/* 648 */     Calendar beginEffectiveDate = (reportForm.getStringValue("beginEffectiveDate") != null && reportForm.getStringValue("beginEffectiveDate").length() > 0) ? MilestoneHelper.getDate(reportForm.getStringValue("beginEffectiveDate")) : null;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 653 */     if (beginEffectiveDate != null) {
/*     */       
/* 655 */       beginEffectiveDate.set(11, 0);
/* 656 */       beginEffectiveDate.set(12, 0);
/* 657 */       beginEffectiveDate.set(13, 0);
/* 658 */       beginEffectiveDate.set(14, 0);
/*     */     } else {
/*     */       
/* 661 */       beginEffectiveDate = MilestoneHelper.getDate("1/1/1900");
/*     */     } 
/* 663 */     Calendar endEffectiveDate = (reportForm.getStringValue("endEffectiveDate") != null && reportForm.getStringValue("endEffectiveDate").length() > 0) ? MilestoneHelper.getDate(reportForm.getStringValue("endEffectiveDate")) : null;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 668 */     if (endEffectiveDate != null) {
/*     */       
/* 670 */       endEffectiveDate.set(11, 23);
/* 671 */       endEffectiveDate.set(12, 59);
/* 672 */       endEffectiveDate.set(13, 59);
/* 673 */       endEffectiveDate.set(14, 999);
/*     */     } else {
/*     */       
/* 676 */       endEffectiveDate = MilestoneHelper.getDate("12/31/2200");
/*     */     } 
/* 678 */     for (int i = 0; i < selections.size(); i++) {
/*     */       
/* 680 */       Selection sel = (Selection)selections.elementAt(i);
/* 681 */       if (sel != null) {
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 686 */         String AddMoveInd = "";
/* 687 */         String MoveDate = "";
/* 688 */         String AddDate = "";
/* 689 */         String familyName = "", companyName = "";
/*     */         
/* 691 */         Family family = sel.getFamily();
/* 692 */         Company company = sel.getCompany();
/*     */         
/* 694 */         if (family != null)
/* 695 */           familyName = (family.getName() == null) ? "" : family.getName(); 
/* 696 */         if (company != null) {
/* 697 */           companyName = (company.getName() == null) ? "" : company.getName();
/*     */         }
/*     */ 
/*     */         
/* 701 */         companyName = "";
/*     */         
/* 703 */         AddDate = MilestoneHelper.getCustomFormatedDate(sel.getOriginDate(), "MM/dd/yyyy");
/* 704 */         MoveDate = MilestoneHelper.getCustomFormatedDate(sel.getLastStreetUpdateDate(), "MM/dd/yyyy");
/* 705 */         if (MoveDate.length() == 0 || MoveDate == null)
/* 706 */           MoveDate = AddDate; 
/* 707 */         if (AddDate.length() == 0 || AddDate == null) {
/* 708 */           AddDate = MoveDate;
/*     */         }
/*     */ 
/*     */         
/* 712 */         if (sel.getOriginDate() != null && (AddDate.compareTo(MoveDate) != 0 || 
/* 713 */           sel.getOriginDate().before(beginEffectiveDate) || 
/* 714 */           sel.getOriginDate().after(endEffectiveDate))) {
/*     */ 
/*     */           
/* 717 */           AddMoveInd = "Moves";
/*     */         
/*     */         }
/*     */         else {
/*     */           
/* 722 */           AddMoveInd = "Adds";
/*     */         } 
/*     */ 
/*     */ 
/*     */         
/* 727 */         Hashtable addsMovesSubTable = (Hashtable)groupedByAddsMove.get(companyName);
/* 728 */         if (addsMovesSubTable == null) {
/*     */ 
/*     */           
/* 731 */           addsMovesSubTable = new Hashtable();
/* 732 */           groupedByAddsMove.put(companyName, addsMovesSubTable);
/*     */         } 
/*     */ 
/*     */         
/* 736 */         Vector selectionsForSubconfig = (Vector)addsMovesSubTable.get(AddMoveInd);
/* 737 */         if (selectionsForSubconfig == null) {
/*     */ 
/*     */           
/* 740 */           selectionsForSubconfig = new Vector();
/* 741 */           addsMovesSubTable.put(AddMoveInd, selectionsForSubconfig);
/*     */         } 
/*     */ 
/*     */         
/* 745 */         if (sel.getLastStreetUpdateDate() != null && 
/* 746 */           sel.getLastStreetUpdateDate().before(endEffectiveDate) && (
/* 747 */           sel.getLastStreetUpdateDate().after(beginEffectiveDate) || 
/* 748 */           sel.getLastStreetUpdateDate().equals(beginEffectiveDate))) {
/* 749 */           selectionsForSubconfig.addElement(sel);
/*     */         }
/*     */         
/* 752 */         if (AddDate.compareTo(MoveDate) != 0 && 
/* 753 */           sel.getOriginDate().before(endEffectiveDate) && 
/* 754 */           sel.getOriginDate().after(beginEffectiveDate) && 
/* 755 */           sel.getLastStreetUpdateDate().before(endEffectiveDate) && (
/* 756 */           sel.getLastStreetUpdateDate().after(beginEffectiveDate) || 
/* 757 */           sel.getLastStreetUpdateDate().equals(beginEffectiveDate))) {
/*     */ 
/*     */           
/* 760 */           AddMoveInd = "Adds";
/* 761 */           addsMovesSubTable = (Hashtable)groupedByAddsMove.get(companyName);
/* 762 */           if (addsMovesSubTable == null) {
/*     */ 
/*     */             
/* 765 */             addsMovesSubTable = new Hashtable();
/* 766 */             groupedByAddsMove.put(companyName, addsMovesSubTable);
/*     */           } 
/*     */ 
/*     */           
/* 770 */           selectionsForSubconfig = (Vector)addsMovesSubTable.get(AddMoveInd);
/* 771 */           if (selectionsForSubconfig == null) {
/*     */ 
/*     */             
/* 774 */             selectionsForSubconfig = new Vector();
/* 775 */             addsMovesSubTable.put(AddMoveInd, selectionsForSubconfig);
/*     */           } 
/*     */ 
/*     */           
/* 779 */           if (sel.getLastStreetUpdateDate() != null && 
/* 780 */             sel.getLastStreetUpdateDate().before(endEffectiveDate) && (
/* 781 */             sel.getLastStreetUpdateDate().after(beginEffectiveDate) || 
/* 782 */             sel.getLastStreetUpdateDate().equals(beginEffectiveDate))) {
/* 783 */             selectionsForSubconfig.addElement(sel);
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 789 */     return groupedByAddsMove;
/*     */   }
/*     */ }


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\AddsAndMovesForPrintSubHandler.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */