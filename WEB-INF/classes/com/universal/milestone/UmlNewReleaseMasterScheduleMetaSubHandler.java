/*     */ package WEB-INF.classes.com.universal.milestone;
/*     */ import com.techempower.gemini.Context;
/*     */ import com.techempower.gemini.GeminiApplication;
/*     */ import com.universal.milestone.Form;
/*     */ import com.universal.milestone.JdbcConnector;
/*     */ import com.universal.milestone.MilestoneHelper;
/*     */ import com.universal.milestone.Plant;
/*     */ import com.universal.milestone.ReleasingFamily;
/*     */ import com.universal.milestone.ReportHandler;
/*     */ import com.universal.milestone.Schedule;
/*     */ import com.universal.milestone.ScheduledTask;
/*     */ import com.universal.milestone.Selection;
/*     */ import com.universal.milestone.SelectionManager;
/*     */ import com.universal.milestone.StringComparator;
/*     */ import com.universal.milestone.UmlNewReleaseMasterScheduleMetaSubHandler;
/*     */ import inetsoft.report.SectionBand;
/*     */ import inetsoft.report.XStyleSheet;
/*     */ import inetsoft.report.lens.DefaultSectionLens;
/*     */ import inetsoft.report.lens.DefaultTableLens;
/*     */ import java.awt.Color;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Font;
/*     */ import java.awt.Insets;
/*     */ import java.awt.Rectangle;
/*     */ import java.io.IOException;
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
/*     */ public class UmlNewReleaseMasterScheduleMetaSubHandler extends SecureHandler {
/*  37 */   public static int NUM_COLS = 12; public static final String COMPONENT_CODE = "hUsu"; public GeminiApplication application;
/*     */   public ComponentLog log;
/*     */   
/*     */   public UmlNewReleaseMasterScheduleMetaSubHandler(GeminiApplication application) {
/*  41 */     this.application = application;
/*  42 */     this.log = application.getLog("hUsu");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*  47 */   public String getDescription() { return "Sub Report"; }
/*     */ 
/*     */ 
/*     */   
/*     */   protected static void fillUmlNewReleaseMasterScheduleForPrint(XStyleSheet report, Context context) {
/*  52 */     report.setMargin(new Margin(1.0D, 0.1D, 0.1D, 0.1D));
/*  53 */     report.setFooterFromEdge(0.5D);
/*  54 */     report.setHeaderFromEdge(0.1D);
/*     */     
/*  56 */     SectionBand hbandHeader = new SectionBand(report);
/*  57 */     SectionBand body = new SectionBand(report);
/*  58 */     SectionBand footer = new SectionBand(report);
/*     */     
/*  60 */     DefaultSectionLens group = null;
/*     */     
/*  62 */     DefaultTableLens table_contents = null;
/*  63 */     DefaultTableLens rowCountTable = null;
/*  64 */     DefaultTableLens subTable = null;
/*     */     
/*  66 */     rowCountTable = new DefaultTableLens(2, 10000);
/*     */     
/*  68 */     Color SHADED_AREA_COLOR = Color.lightGray;
/*  69 */     String theConfig = "";
/*     */     
/*     */     try {
/*  72 */       HttpServletResponse sresponse = context.getResponse();
/*  73 */       context.putDelivery("status", new String("start_gathering"));
/*  74 */       context.includeJSP("status.jsp", "hiddenFrame");
/*  75 */       sresponse.setContentType("text/plain");
/*  76 */       sresponse.flushBuffer();
/*     */     }
/*  78 */     catch (Exception exception) {}
/*  79 */     Vector selections = MilestoneHelper.getSelectionsForReport(context);
/*     */     
/*  81 */     Hashtable plantIdandName = buildPlantHash();
/*     */     
/*     */     try {
/*  84 */       HttpServletResponse sresponse = context.getResponse();
/*  85 */       context.putDelivery("status", new String("start_report"));
/*  86 */       context.putDelivery("percent", new String("10"));
/*  87 */       context.includeJSP("status.jsp", "hiddenFrame");
/*  88 */       sresponse.setContentType("text/plain");
/*  89 */       sresponse.flushBuffer();
/*     */     }
/*  91 */     catch (Exception exception) {}
/*     */     
/*     */     try {
/*  94 */       Form reportForm = (Form)context.getSessionValue("reportForm");
/*     */       
/*  96 */       Calendar beginStDate = (reportForm.getStringValue("beginDate") != null && 
/*  97 */         reportForm.getStringValue("beginDate").length() > 0) ? 
/*  98 */         MilestoneHelper.getDate(reportForm.getStringValue("beginDate")) : null;
/*     */       
/* 100 */       Calendar endStDate = (reportForm.getStringValue("endDate") != null && 
/* 101 */         reportForm.getStringValue("endDate").length() > 0) ? 
/* 102 */         MilestoneHelper.getDate(reportForm.getStringValue("endDate")) : null;
/*     */       
/* 104 */       report.setElement("crs_startdate", MilestoneHelper.getFormatedDate(beginStDate));
/* 105 */       report.setElement("crs_enddate", MilestoneHelper.getFormatedDate(endStDate));
/*     */       
/* 107 */       SimpleDateFormat formatter = new SimpleDateFormat("MMMM d, yyyy");
/* 108 */       String todayLong = formatter.format(new Date());
/* 109 */       report.setElement("crs_bottomdate", todayLong);
/*     */       
/* 111 */       Hashtable selTable = MilestoneHelper.groupSelectionsByConfigAndStreetDate(selections);
/*     */       
/* 113 */       Enumeration streetDates = selTable.keys();
/* 114 */       Vector streetDatesVector = new Vector();
/* 115 */       while (streetDates.hasMoreElements()) {
/* 116 */         streetDatesVector.addElement(streetDates.nextElement());
/*     */       }
/* 118 */       int numConfigs = 0;
/* 119 */       for (int i = 0; i < streetDatesVector.size(); i++) {
/*     */         
/* 121 */         String streetDateName = (streetDatesVector.elementAt(i) != null) ? (String)streetDatesVector.elementAt(i) : "";
/* 122 */         Hashtable streetDateTable = (Hashtable)selTable.get(streetDateName);
/* 123 */         if (streetDateTable != null) {
/* 124 */           numConfigs += streetDateTable.size();
/*     */         }
/*     */       } 
/* 127 */       int numExtraRows = 2 + streetDatesVector.size() * 10 + numConfigs;
/*     */       
/* 129 */       int numSelections = selections.size() * 2;
/* 130 */       int numRows = numSelections + numExtraRows;
/*     */       
/* 132 */       numRows -= streetDatesVector.size() * 2 - 1;
/*     */       
/* 134 */       table_contents = new DefaultTableLens(2, NUM_COLS);
/*     */       
/* 136 */       table_contents.setHeaderRowCount(1);
/* 137 */       table_contents.setColWidth(0, 160);
/* 138 */       table_contents.setColWidth(1, 130);
/* 139 */       table_contents.setColWidth(2, 160);
/* 140 */       table_contents.setColWidth(3, 160);
/* 141 */       table_contents.setColWidth(4, 240);
/* 142 */       table_contents.setColWidth(5, 140);
/* 143 */       table_contents.setColWidth(6, 140);
/* 144 */       table_contents.setColWidth(7, 110);
/* 145 */       table_contents.setColWidth(8, 80);
/* 146 */       table_contents.setColWidth(9, 80);
/* 147 */       table_contents.setColWidth(10, 80);
/* 148 */       table_contents.setColWidth(11, 80);
/*     */       
/* 150 */       table_contents.setRowBorder(0, 4097);
/* 151 */       table_contents.setRowBorderColor(-1, SHADED_AREA_COLOR);
/* 152 */       table_contents.setRowBorderColor(0, SHADED_AREA_COLOR);
/*     */       
/* 154 */       table_contents.setAlignment(0, 0, 34);
/* 155 */       table_contents.setAlignment(0, 1, 34);
/* 156 */       table_contents.setAlignment(0, 2, 34);
/* 157 */       table_contents.setAlignment(0, 3, 34);
/* 158 */       table_contents.setAlignment(0, 4, 34);
/* 159 */       table_contents.setAlignment(0, 5, 36);
/* 160 */       table_contents.setAlignment(0, 6, 33);
/* 161 */       table_contents.setAlignment(0, 7, 34);
/* 162 */       table_contents.setAlignment(0, 8, 34);
/* 163 */       table_contents.setAlignment(0, 9, 34);
/* 164 */       table_contents.setAlignment(0, 10, 34);
/* 165 */       table_contents.setAlignment(0, 11, 34);
/*     */       
/* 167 */       table_contents.setInsets(new Insets(-1, 0, 0, 0));
/* 168 */       table_contents.setObject(0, 0, "UPC");
/* 169 */       table_contents.setObject(0, 1, "P&D(*)\nLocal Prod #");
/* 170 */       table_contents.setObject(0, 2, "Rls Family - Label");
/* 171 */       table_contents.setSpan(0, 2, new Dimension(2, 1));
/*     */       
/* 173 */       table_contents.setObject(0, 4, "Artist");
/* 174 */       table_contents.setObject(0, 5, "Title  /");
/* 175 */       table_contents.setObject(0, 6, "Comments");
/* 176 */       table_contents.setObject(0, 7, "Plant");
/* 177 */       table_contents.setObject(0, 8, "Qty\nDone");
/* 178 */       table_contents.setObject(0, 9, "F/M");
/* 179 */       table_contents.setObject(0, 10, "BOM");
/* 180 */       table_contents.setObject(0, 11, "PRR");
/*     */       
/* 182 */       table_contents.setRowFont(0, new Font("Arial", 1, 7));
/* 183 */       table_contents.setFont(0, 6, new Font("Arial", 3, 7));
/*     */       
/* 185 */       table_contents.setColBorderColor(0, -1, new Color(208, 206, 206, 0));
/* 186 */       table_contents.setColBorderColor(0, 0, new Color(208, 206, 206, 0));
/* 187 */       table_contents.setColBorderColor(0, 2, Color.white);
/* 188 */       table_contents.setColBorderColor(0, 1, new Color(208, 206, 206, 0));
/* 189 */       table_contents.setColBorderColor(0, 3, new Color(208, 206, 206, 0));
/* 190 */       table_contents.setColBorderColor(0, 4, new Color(208, 206, 206, 0));
/* 191 */       table_contents.setColBorderColor(0, 6, new Color(208, 206, 206, 0));
/* 192 */       table_contents.setColBorderColor(0, 5, Color.white);
/* 193 */       table_contents.setColBorderColor(0, 7, new Color(208, 206, 206, 0));
/* 194 */       table_contents.setColBorderColor(0, 8, new Color(208, 206, 206, 0));
/* 195 */       table_contents.setColBorderColor(0, 9, new Color(208, 206, 206, 0));
/* 196 */       table_contents.setColBorderColor(0, 10, new Color(208, 206, 206, 0));
/* 197 */       table_contents.setColBorderColor(0, 11, new Color(208, 206, 206, 0));
/*     */       
/* 199 */       table_contents.setSpan(1, 0, new Dimension(NUM_COLS, 1));
/* 200 */       table_contents.setRowHeight(1, 1);
/* 201 */       table_contents.setRowBackground(1, Color.white);
/* 202 */       table_contents.setRowForeground(1, Color.black);
/* 203 */       table_contents.setRowBorderColor(1, SHADED_AREA_COLOR);
/* 204 */       setColBorderColors(1, -1, NUM_COLS - 1, table_contents, new Color(208, 206, 206, 0));
/* 205 */       setColBorders(1, -1, NUM_COLS - 1, table_contents, 0);
/*     */       
/* 207 */       int nextRow = 0;
/* 208 */       String upc = "";
/* 209 */       Vector sortedStreetDatesVector = MilestoneHelper.sortDates(streetDatesVector);
/*     */       
/* 211 */       hbandHeader.setHeight(1.0F);
/* 212 */       hbandHeader.setShrinkToFit(true);
/* 213 */       hbandHeader.setVisible(true);
/* 214 */       hbandHeader.setBottomBorder(0);
/*     */       
/* 216 */       hbandHeader.addTable(table_contents, new Rectangle(800, 800));
/*     */       
/* 218 */       int totalCount = 0;
/* 219 */       int tenth = 1;
/* 220 */       for (int a = 0; a < sortedStreetDatesVector.size(); a++) {
/*     */         
/* 222 */         totalCount++;
/* 223 */         String datesC = (String)sortedStreetDatesVector.get(a);
/* 224 */         Hashtable configTableC = (Hashtable)selTable.get(datesC);
/* 225 */         if (configTableC != null) {
/*     */           
/* 227 */           Enumeration monthsC = configTableC.keys();
/* 228 */           Vector monthVectorC = new Vector();
/* 229 */           while (monthsC.hasMoreElements()) {
/* 230 */             monthVectorC.add((String)monthsC.nextElement());
/*     */           }
/* 232 */           Object[] configsArrayC = null;
/* 233 */           configsArrayC = monthVectorC.toArray();
/* 234 */           for (int b = 0; b < configsArrayC.length; b++) {
/*     */             
/* 236 */             totalCount++;
/* 237 */             String monthNameC = (String)configsArrayC[b];
/*     */             
/* 239 */             Vector selectionsC = (Vector)configTableC.get(monthNameC);
/* 240 */             if (selectionsC == null) {
/* 241 */               selectionsC = new Vector();
/*     */             }
/* 243 */             totalCount += selectionsC.size();
/*     */           } 
/*     */         } 
/*     */       } 
/* 247 */       tenth = (totalCount > 10) ? (totalCount / 10) : 1;
/*     */       
/* 249 */       HttpServletResponse sresponse = context.getResponse();
/* 250 */       context.putDelivery("status", new String("start_report"));
/* 251 */       context.putDelivery("percent", new String("10"));
/* 252 */       context.includeJSP("status.jsp", "hiddenFrame");
/* 253 */       sresponse.setContentType("text/plain");
/*     */       
/*     */       try {
/* 256 */         sresponse.flushBuffer();
/*     */       }
/* 258 */       catch (IOException iOException) {}
/* 259 */       int recordCount = 0;
/* 260 */       int count = 0;
/* 261 */       for (int n = 0; n < sortedStreetDatesVector.size(); n++) {
/*     */         
/* 263 */         if (count < recordCount / tenth) {
/*     */           
/* 265 */           count = recordCount / tenth;
/* 266 */           sresponse = context.getResponse();
/* 267 */           context.putDelivery("status", new String("start_report"));
/* 268 */           context.putDelivery("percent", new String(String.valueOf(count * 10)));
/* 269 */           context.includeJSP("status.jsp", "hiddenFrame");
/* 270 */           sresponse.setContentType("text/plain");
/*     */           
/*     */           try {
/* 273 */             sresponse.flushBuffer();
/*     */           }
/* 275 */           catch (IOException iOException) {}
/*     */         } 
/* 277 */         recordCount++;
/*     */         
/* 279 */         int piTotal = 0;
/* 280 */         int kmTotal = 0;
/* 281 */         int haTotal = 0;
/* 282 */         int glTotal = 0;
/* 283 */         int ciTotal = 0;
/* 284 */         int paTotal = 0;
/* 285 */         int civTotal = 0;
/*     */         
/* 287 */         Hashtable plantTotals = buildTotals(plantIdandName);
/*     */         
/* 289 */         String theStreetDate = (String)sortedStreetDatesVector.elementAt(n);
/* 290 */         String theStreetDateText = !theStreetDate.trim().equals("") ? theStreetDate : "Other";
/*     */         
/* 292 */         footer.setVisible(true);
/* 293 */         footer.setHeight(0.05F);
/* 294 */         footer.setShrinkToFit(true);
/* 295 */         footer.setBottomBorder(0);
/* 296 */         nextRow = 0;
/*     */         
/* 298 */         Hashtable configTable = (Hashtable)selTable.get(theStreetDate);
/* 299 */         if (configTable != null) {
/*     */           
/* 301 */           Enumeration configsEnum = configTable.keys();
/*     */           
/* 303 */           Vector configsVector = new Vector();
/* 304 */           while (configsEnum.hasMoreElements()) {
/* 305 */             configsVector.add((String)configsEnum.nextElement());
/*     */           }
/* 307 */           Object[] configsList = configsVector.toArray();
/*     */           
/* 309 */           Arrays.sort(configsList, new StringComparator());
/* 310 */           for (int x = 0; x < configsList.length; x++) {
/*     */             
/* 312 */             nextRow = 0;
/*     */             
/* 314 */             theConfig = (String)configsList[x];
/* 315 */             if (count < recordCount / tenth) {
/*     */               
/* 317 */               count = recordCount / tenth;
/* 318 */               sresponse = context.getResponse();
/* 319 */               context.putDelivery("status", new String("start_report"));
/* 320 */               context.putDelivery("percent", new String(String.valueOf(count * 10)));
/* 321 */               context.includeJSP("status.jsp", "hiddenFrame");
/* 322 */               sresponse.setContentType("text/plain");
/*     */               
/*     */               try {
/* 325 */                 sresponse.flushBuffer();
/*     */               }
/* 327 */               catch (IOException iOException) {}
/*     */             } 
/* 329 */             recordCount++;
/*     */             
/* 331 */             footer.setVisible(true);
/* 332 */             footer.setHeight(0.05F);
/* 333 */             footer.setShrinkToFit(true);
/* 334 */             footer.setBottomBorder(0);
/*     */             
/* 336 */             selections = (Vector)configTable.get(theConfig);
/* 337 */             if (selections == null) {
/* 338 */               selections = new Vector();
/*     */             }
/* 340 */             MilestoneHelper.setSelectionSorting(selections, 15);
/* 341 */             Collections.sort(selections);
/*     */             
/* 343 */             MilestoneHelper.setSelectionSorting(selections, 14);
/* 344 */             Collections.sort(selections);
/*     */             
/* 346 */             MilestoneHelper.setSelectionSorting(selections, 24);
/* 347 */             Collections.sort(selections);
/*     */             
/* 349 */             MilestoneHelper.applyManufacturingToSelections(selections);
/* 350 */             for (int i = 0; i < selections.size(); i++) {
/*     */               
/* 352 */               Selection sel = (Selection)selections.elementAt(i);
/* 353 */               upc = sel.getUpc();
/* 354 */               Vector plants = sel.getManufacturingPlants();
/*     */               
/* 356 */               int plantSize = 1;
/* 357 */               if (plants != null && plants.size() > 0) {
/* 358 */                 plantSize = plants.size();
/*     */               }
/* 360 */               for (int plantCount = 0; plantCount < plantSize; plantCount++) {
/*     */                 
/* 362 */                 if (i == 0 && plantCount == 0) {
/* 363 */                   subTable = new DefaultTableLens(8, NUM_COLS);
/*     */                 } else {
/* 365 */                   subTable = new DefaultTableLens(2, NUM_COLS);
/*     */                 } 
/* 367 */                 Plant p = null;
/* 368 */                 if (plants != null && plants.size() > 0) {
/* 369 */                   p = (Plant)plants.get(plantCount);
/*     */                 }
/* 371 */                 nextRow = 0;
/*     */                 
/* 373 */                 subTable.setColWidth(0, 160);
/* 374 */                 subTable.setColWidth(1, 130);
/* 375 */                 subTable.setColWidth(2, 160);
/* 376 */                 subTable.setColWidth(3, 160);
/* 377 */                 subTable.setColWidth(4, 240);
/* 378 */                 subTable.setColWidth(5, 140);
/* 379 */                 subTable.setColWidth(6, 140);
/* 380 */                 subTable.setColWidth(7, 110);
/* 381 */                 subTable.setColWidth(8, 80);
/* 382 */                 subTable.setColWidth(9, 80);
/* 383 */                 subTable.setColWidth(10, 80);
/* 384 */                 subTable.setColWidth(11, 80);
/*     */                 
/* 386 */                 subTable.setRowBorder(0, 4097);
/*     */                 
/* 388 */                 subTable.setLineWrap(0, 0, false);
/* 389 */                 subTable.setLineWrap(0, 1, false);
/* 390 */                 subTable.setLineWrap(0, 2, false);
/* 391 */                 subTable.setLineWrap(0, 3, false);
/* 392 */                 subTable.setLineWrap(0, 4, false);
/* 393 */                 subTable.setLineWrap(0, 5, false);
/* 394 */                 subTable.setLineWrap(0, 6, false);
/* 395 */                 subTable.setLineWrap(0, 7, false);
/* 396 */                 subTable.setLineWrap(0, 8, false);
/* 397 */                 subTable.setLineWrap(0, 9, false);
/* 398 */                 subTable.setLineWrap(0, 10, false);
/* 399 */                 subTable.setLineWrap(0, 11, false);
/*     */                 
/* 401 */                 subTable.setAlignment(0, 0, 34);
/* 402 */                 subTable.setAlignment(0, 1, 34);
/* 403 */                 subTable.setAlignment(0, 2, 36);
/* 404 */                 subTable.setAlignment(0, 3, 33);
/* 405 */                 subTable.setAlignment(0, 4, 34);
/* 406 */                 subTable.setAlignment(0, 5, 36);
/* 407 */                 subTable.setAlignment(0, 6, 33);
/* 408 */                 subTable.setAlignment(0, 7, 34);
/* 409 */                 subTable.setAlignment(0, 8, 34);
/* 410 */                 subTable.setAlignment(0, 9, 34);
/* 411 */                 subTable.setAlignment(0, 10, 34);
/* 412 */                 subTable.setAlignment(0, 11, 34);
/*     */                 
/* 414 */                 int artistLength = 0, labelLength = 0, titleLength = 0, idLength = 0;
/* 415 */                 if (count < recordCount / tenth) {
/*     */                   
/* 417 */                   count = recordCount / tenth;
/* 418 */                   sresponse = context.getResponse();
/* 419 */                   context.putDelivery("status", new String("start_report"));
/* 420 */                   context.putDelivery("percent", new String(String.valueOf(count * 10)));
/* 421 */                   context.includeJSP("status.jsp", "hiddenFrame");
/* 422 */                   sresponse.setContentType("text/plain");
/*     */                   
/*     */                   try {
/* 425 */                     sresponse.flushBuffer();
/*     */                   }
/* 427 */                   catch (IOException iOException) {}
/*     */                 } 
/* 429 */                 recordCount++;
/*     */                 
/* 431 */                 String selectionNo = SelectionManager.getLookupObjectValue(sel.getPrefixID());
/* 432 */                 if (selectionNo == null) {
/* 433 */                   selectionNo = "";
/*     */                 }
/* 435 */                 selectionNo = String.valueOf(selectionNo) + sel.getSelectionNo().trim();
/* 436 */                 idLength = selectionNo.length();
/*     */                 
/* 438 */                 String pd = "";
/* 439 */                 if (sel.getPressAndDistribution()) {
/* 440 */                   pd = "* ";
/*     */                 }
/* 442 */                 String selDistribution = SelectionManager.getLookupObjectValue(sel.getDistribution());
/*     */                 
/* 444 */                 String artist = "";
/*     */                 
/* 446 */                 artist = sel.getArtist();
/* 447 */                 if (artist != null) {
/* 448 */                   artistLength = artist.length();
/*     */                 }
/* 450 */                 String comment = (sel.getSelectionComments() != null) ? sel.getSelectionComments().trim() : "";
/*     */                 
/* 452 */                 String mfgComment = (sel.getManufacturingComments() != null) ? sel.getManufacturingComments().trim() : "";
/*     */                 
/* 454 */                 String releasingFamily = "";
/* 455 */                 if (sel.getReleaseFamilyId() > 0) {
/* 456 */                   releasingFamily = ReleasingFamily.getName(sel.getReleaseFamilyId());
/*     */                 }
/* 458 */                 String label = "";
/* 459 */                 if (sel.getImprint() != null) {
/* 460 */                   label = sel.getImprint();
/*     */                 }
/* 462 */                 labelLength = label.length() + releasingFamily.length();
/*     */                 
/* 464 */                 String titleComments = sel.getTitle();
/* 465 */                 if (titleComments != null) {
/* 466 */                   titleLength = titleComments.length();
/*     */                 }
/*     */                 
/* 469 */                 String poQty = "0";
/* 470 */                 if (p != null && p.getOrderQty() > 0) {
/* 471 */                   poQty = (p != null && p.getOrderQty() > 0) ? Integer.toString(p.getOrderQty()) : "0";
/*     */                 }
/*     */ 
/*     */                 
/* 475 */                 int poQtyNum = 0;
/*     */                 
/*     */                 try {
/* 478 */                   poQtyNum = Integer.parseInt(poQty);
/*     */                 }
/* 480 */                 catch (Exception exception) {}
/* 481 */                 int explodedTotal = 0;
/* 482 */                 if (poQtyNum > 0 && sel.getNumberOfUnits() > 0) {
/* 483 */                   explodedTotal = poQtyNum * sel.getNumberOfUnits();
/*     */                 }
/* 485 */                 String plant = "";
/* 486 */                 String plantText = "";
/* 487 */                 if (p != null && p.getPlant() != null) {
/*     */                   
/* 489 */                   String plantNo = p.getPlant().getName();
/* 490 */                   plant = p.getPlant().getAbbreviation();
/*     */                   
/* 492 */                   plantText = p.getPlant().getName();
/*     */                   
/* 494 */                   String plantId = p.getPlant().getAbbreviation();
/* 495 */                   if (plantIdandName.get(plantId) != null) {
/* 496 */                     plant = (String)plantIdandName.get(plantId);
/*     */                   } else {
/* 498 */                     plant = "";
/*     */                   } 
/* 500 */                   int currentTotal = 0;
/* 501 */                   if (plantTotals.get(plantId) != null) {
/* 502 */                     currentTotal = Integer.parseInt((String)plantTotals.get(plantId));
/*     */                   }
/* 504 */                   int newTotal = currentTotal + explodedTotal;
/* 505 */                   plantTotals.put(plantId, String.valueOf(newTotal));
/*     */                 } 
/* 507 */                 String compQty = "0";
/* 508 */                 if (p != null && p.getCompletedQty() > 0) {
/* 509 */                   compQty = MilestoneHelper.formatQuantityWithCommas(String.valueOf(p.getCompletedQty()));
/*     */                 }
/* 511 */                 Schedule schedule = sel.getSchedule();
/*     */                 
/* 513 */                 Vector tasks = (schedule != null) ? schedule.getTasks() : null;
/* 514 */                 ScheduledTask task = null;
/*     */                 
/* 516 */                 String FM = "";
/* 517 */                 String BOM = "";
/* 518 */                 String PRQ = "";
/* 519 */                 String TAPE = "";
/* 520 */                 String FILM = "";
/* 521 */                 String PAP = "";
/* 522 */                 String STIC = "";
/* 523 */                 String MC = "";
/* 524 */                 String FAP = "";
/* 525 */                 String PSD = "";
/* 526 */                 String dueDateHolidayFlg = "";
/*     */                 
/* 528 */                 String MCvend = "";
/*     */                 
/* 530 */                 boolean hasPPRtask = false;
/* 531 */                 if (tasks != null) {
/*     */                   
/* 533 */                   PSD = "N/A";
/* 534 */                   for (int j = 0; j < tasks.size(); j++) {
/*     */                     
/* 536 */                     task = (ScheduledTask)tasks.get(j);
/*     */                     
/* 538 */                     String taskAbbrev = MilestoneHelper.getTaskAbbreivationNameById(task.getTaskAbbreviationID());
/*     */                     
/* 540 */                     String taskVendor = (task.getVendor() != null) ? task.getVendor() : "";
/* 541 */                     taskVendor = taskVendor.equals("\n") ? "" : taskVendor;
/* 542 */                     if (taskAbbrev.equalsIgnoreCase("F/M")) {
/*     */                       
/* 544 */                       FM = MilestoneHelper.getFormatedDate(task.getCompletionDate());
/* 545 */                       if (task.getScheduledTaskStatus() != null && 
/* 546 */                         task.getScheduledTaskStatus().equals("N/A")) {
/* 547 */                         FM = "N/A";
/*     */                       }
/*     */                     }
/* 550 */                     else if (taskAbbrev.equalsIgnoreCase("BOM")) {
/*     */                       
/* 552 */                       BOM = MilestoneHelper.getFormatedDate(task.getCompletionDate());
/* 553 */                       if (task.getScheduledTaskStatus() != null && 
/* 554 */                         task.getScheduledTaskStatus().equals("N/A")) {
/* 555 */                         BOM = "N/A";
/*     */                       }
/*     */                     }
/* 558 */                     else if (taskAbbrev.equalsIgnoreCase("PRQ")) {
/*     */                       
/* 560 */                       PRQ = MilestoneHelper.getFormatedDate(task.getCompletionDate());
/* 561 */                       if (task.getScheduledTaskStatus() != null && 
/* 562 */                         task.getScheduledTaskStatus().equals("N/A")) {
/* 563 */                         PRQ = "N/A";
/*     */                       }
/*     */                     }
/* 566 */                     else if (taskAbbrev.equalsIgnoreCase("TAPE")) {
/*     */                       
/* 568 */                       TAPE = MilestoneHelper.getFormatedDate(task.getCompletionDate());
/* 569 */                       if (task.getScheduledTaskStatus() != null && 
/* 570 */                         task.getScheduledTaskStatus().equals("N/A")) {
/* 571 */                         TAPE = "N/A";
/*     */                       }
/*     */                     }
/* 574 */                     else if (taskAbbrev.equalsIgnoreCase("FILM")) {
/*     */                       
/* 576 */                       FILM = MilestoneHelper.getFormatedDate(task.getCompletionDate());
/* 577 */                       if (task.getScheduledTaskStatus() != null && 
/* 578 */                         task.getScheduledTaskStatus().equals("N/A")) {
/* 579 */                         FILM = "N/A";
/*     */                       }
/*     */                     }
/* 582 */                     else if (taskAbbrev.equalsIgnoreCase("PAP")) {
/*     */                       
/* 584 */                       PAP = MilestoneHelper.getFormatedDate(task.getCompletionDate());
/* 585 */                       if (task.getScheduledTaskStatus() != null && 
/* 586 */                         task.getScheduledTaskStatus().equals("N/A")) {
/* 587 */                         PAP = "N/A";
/*     */                       }
/*     */                     }
/* 590 */                     else if (taskAbbrev.equalsIgnoreCase("STIC")) {
/*     */                       
/* 592 */                       STIC = MilestoneHelper.getFormatedDate(task.getCompletionDate());
/* 593 */                       if (task.getScheduledTaskStatus() != null && 
/* 594 */                         task.getScheduledTaskStatus().equals("N/A")) {
/* 595 */                         STIC = "N/A";
/*     */                       }
/*     */                     }
/* 598 */                     else if (taskAbbrev.equalsIgnoreCase("M/C")) {
/*     */                       
/* 600 */                       MC = MilestoneHelper.getFormatedDate(task.getCompletionDate());
/* 601 */                       if (task.getScheduledTaskStatus() != null && 
/* 602 */                         task.getScheduledTaskStatus().equals("N/A")) {
/* 603 */                         MC = "N/A";
/*     */                       }
/* 605 */                       MCvend = taskVendor;
/* 606 */                       hasPPRtask = true;
/*     */                     }
/* 608 */                     else if (taskAbbrev.equalsIgnoreCase("FAP")) {
/*     */                       
/* 610 */                       FAP = MilestoneHelper.getFormatedDate(task.getCompletionDate());
/* 611 */                       if (task.getScheduledTaskStatus() != null && 
/* 612 */                         task.getScheduledTaskStatus().equals("N/A")) {
/* 613 */                         FAP = "N/A";
/*     */                       }
/*     */                     }
/* 616 */                     else if (taskAbbrev.equalsIgnoreCase("PSD")) {
/*     */                       
/* 618 */                       if (task.getScheduledTaskStatus() != null && 
/* 619 */                         task.getScheduledTaskStatus().equals("N/A")) {
/*     */                         
/* 621 */                         PSD = "N/A";
/*     */                       }
/*     */                       else {
/*     */                         
/* 625 */                         dueDateHolidayFlg = MilestoneHelper.getDayType(sel.getCalendarGroup(), task.getDueDate());
/* 626 */                         String PsdCompDt = (task.getCompletionDate() == null) ? "" : MilestoneHelper.getFormatedDate(task.getCompletionDate());
/* 627 */                         String PsdDueDt = (task.getDueDate() == null) ? "" : MilestoneHelper.getFormatedDate(task.getDueDate());
/* 628 */                         PSD = String.valueOf(PsdCompDt) + "\n" + PsdDueDt + " " + dueDateHolidayFlg;
/*     */                       } 
/*     */                     } 
/* 631 */                     task = null;
/*     */                   } 
/*     */                 } 
/* 634 */                 nextRow = 0;
/* 635 */                 if (i == 0 && plantCount == 0) {
/*     */                   
/* 637 */                   nextRow = ReportHandler.insertLightGrayHeader(subTable, theStreetDateText, nextRow, 12);
/*     */                   
/* 639 */                   subTable.setRowBorder(nextRow, 4097);
/* 640 */                   subTable.setRowBorder(nextRow + 1, 4097);
/* 641 */                   setColBorders(nextRow, -1, NUM_COLS - 1, subTable, 0);
/* 642 */                   subTable.setColBorderColor(nextRow, Color.white);
/* 643 */                   setColBorderColors(nextRow, -1, NUM_COLS - 1, subTable, Color.white);
/* 644 */                   subTable.setSpan(nextRow, 0, new Dimension(NUM_COLS, 1));
/* 645 */                   subTable.setObject(nextRow, 0, theConfig);
/* 646 */                   subTable.setAlignment(0, 0, 33);
/* 647 */                   subTable.setRowFont(nextRow, new Font("Arial", 1, 9));
/*     */                   
/* 649 */                   subTable.setRowBorderColor(-1, Color.lightGray);
/* 650 */                   subTable.setRowBorderColor(Color.lightGray);
/*     */                   
/* 652 */                   nextRow++;
/*     */                   
/* 654 */                   subTable.setHeaderRowCount(0);
/* 655 */                   subTable.setSpan(nextRow, 0, new Dimension(NUM_COLS, 1));
/* 656 */                   subTable.setRowHeight(nextRow, 2);
/* 657 */                   subTable.setRowBackground(nextRow, Color.white);
/* 658 */                   subTable.setRowForeground(nextRow, Color.black);
/* 659 */                   setColBorderColors(nextRow, -1, NUM_COLS - 1, subTable, new Color(208, 206, 206, 0));
/* 660 */                   setColBorders(nextRow, -1, NUM_COLS - 1, subTable, 0);
/*     */                   
/* 662 */                   nextRow++;
/*     */                 } 
/* 664 */                 subTable.setRowInsets(nextRow, new Insets(0, 0, 5, 0));
/* 665 */                 if (sel.getSpecialPackaging()) {
/*     */                   
/* 667 */                   if (BOM.length() > 0) {
/*     */                     
/* 669 */                     BOM = String.valueOf(BOM) + "\n";
/* 670 */                     subTable.setRowHeight(nextRow, 7);
/*     */                   } 
/* 672 */                   BOM = String.valueOf(BOM) + "sp. pkg";
/*     */                 } 
/* 674 */                 if (plantText.length() > 13 || labelLength > 17 || artistLength > 27) {
/*     */                   
/* 676 */                   subTable.setRowHeight(nextRow, 10);
/* 677 */                   subTable.setRowAutoSize(true);
/* 678 */                   subTable.setRowHeight(nextRow + 1, 1);
/*     */                 } 
/* 680 */                 subTable.setRowInsets(nextRow, new Insets(0, 0, 5, 0));
/*     */                 
/* 682 */                 subTable.setSpan(nextRow, 0, new Dimension(1, 2));
/* 683 */                 subTable.setSpan(nextRow, 1, new Dimension(1, 2));
/* 684 */                 subTable.setSpan(nextRow, 2, new Dimension(2, 2));
/* 685 */                 subTable.setSpan(nextRow, 3, new Dimension(1, 2));
/* 686 */                 subTable.setSpan(nextRow, 4, new Dimension(1, 2));
/* 687 */                 subTable.setSpan(nextRow, 5, new Dimension(2, 2));
/* 688 */                 subTable.setSpan(nextRow, 6, new Dimension(1, 2));
/* 689 */                 subTable.setSpan(nextRow, 7, new Dimension(1, 2));
/* 690 */                 subTable.setSpan(nextRow, 8, new Dimension(1, 2));
/* 691 */                 subTable.setSpan(nextRow, 9, new Dimension(1, 2));
/* 692 */                 subTable.setSpan(nextRow, 10, new Dimension(1, 2));
/* 693 */                 subTable.setSpan(nextRow, 11, new Dimension(1, 2));
/* 694 */                 if (idLength == 13 && pd.trim().equals("*")) {
/* 695 */                   pd = "*";
/*     */                 }
/* 697 */                 subTable.setObject(nextRow, 0, upc);
/* 698 */                 subTable.setObject(nextRow, 1, String.valueOf(pd) + selectionNo);
/* 699 */                 subTable.setObject(nextRow, 2, String.valueOf(releasingFamily) + " - " + label);
/* 700 */                 subTable.setObject(nextRow, 4, artist);
/*     */                 
/* 702 */                 subTable.setSpan(nextRow, 5, new Dimension(2, 1));
/* 703 */                 subTable.setSpan(nextRow + 1, 5, new Dimension(2, 1));
/*     */                 
/* 705 */                 subTable.setObject(nextRow, 5, titleComments);
/* 706 */                 subTable.setObject(nextRow, 7, plant);
/* 707 */                 subTable.setObject(nextRow, 8, MilestoneHelper.formatQuantityWithCommas(compQty));
/*     */                 
/* 709 */                 subTable.setObject(nextRow, 9, FM);
/* 710 */                 subTable.setObject(nextRow, 10, BOM);
/* 711 */                 if (tasks != null) {
/* 712 */                   if (!hasPPRtask) {
/* 713 */                     subTable.setObject(nextRow, 11, "N/A");
/* 714 */                   } else if (MCvend != null && !MCvend.equals("")) {
/* 715 */                     subTable.setObject(nextRow, 11, String.valueOf(MC) + "\n" + MCvend);
/*     */                   } else {
/* 717 */                     subTable.setObject(nextRow, 11, MC);
/*     */                   } 
/*     */                 }
/* 720 */                 subTable.setRowHeight(nextRow, 10);
/*     */                 
/* 722 */                 subTable.setAlignment(nextRow, 0, 4);
/*     */                 
/* 724 */                 subTable.setLineWrap(nextRow, 1, true);
/* 725 */                 subTable.setLineWrap(nextRow, 2, true);
/* 726 */                 subTable.setLineWrap(nextRow, 3, true);
/* 727 */                 subTable.setLineWrap(nextRow, 4, true);
/* 728 */                 subTable.setLineWrap(nextRow, 5, true);
/* 729 */                 subTable.setLineWrap(nextRow, 6, true);
/* 730 */                 subTable.setLineWrap(nextRow, 7, true);
/* 731 */                 subTable.setLineWrap(nextRow, 8, true);
/* 732 */                 subTable.setLineWrap(nextRow, 9, true);
/* 733 */                 subTable.setLineWrap(nextRow, 10, true);
/* 734 */                 subTable.setLineWrap(nextRow, 11, true);
/*     */                 
/* 736 */                 subTable.setAlignment(nextRow, 0, 8);
/* 737 */                 subTable.setAlignment(nextRow, 1, 8);
/* 738 */                 subTable.setAlignment(nextRow, 2, 8);
/* 739 */                 subTable.setAlignment(nextRow, 3, 8);
/* 740 */                 subTable.setAlignment(nextRow, 4, 8);
/* 741 */                 subTable.setAlignment(nextRow, 5, 8);
/* 742 */                 subTable.setAlignment(nextRow, 6, 8);
/* 743 */                 subTable.setAlignment(nextRow, 7, 12);
/* 744 */                 subTable.setAlignment(nextRow, 8, 12);
/* 745 */                 subTable.setAlignment(nextRow, 9, 12);
/* 746 */                 subTable.setAlignment(nextRow, 10, 12);
/* 747 */                 subTable.setAlignment(nextRow, 11, 12);
/*     */                 
/* 749 */                 subTable.setRowInsets(nextRow, new Insets(0, 0, 0, 0));
/* 750 */                 subTable.setRowFont(nextRow, new Font("Arial", 0, 6));
/*     */                 
/* 752 */                 subTable.setColBorderColor(nextRow, -1, new Color(208, 206, 206, 0));
/* 753 */                 subTable.setColBorderColor(nextRow, 0, new Color(208, 206, 206, 0));
/* 754 */                 subTable.setColBorderColor(nextRow, 1, new Color(208, 206, 206, 0));
/* 755 */                 subTable.setColBorderColor(nextRow, 2, new Color(208, 206, 206, 0));
/* 756 */                 subTable.setColBorderColor(nextRow, 3, new Color(208, 206, 206, 0));
/* 757 */                 subTable.setColBorderColor(nextRow, 4, new Color(208, 206, 206, 0));
/* 758 */                 subTable.setColBorderColor(nextRow, 5, new Color(208, 206, 206, 0));
/* 759 */                 subTable.setColBorderColor(nextRow, 6, new Color(208, 206, 206, 0));
/* 760 */                 subTable.setColBorderColor(nextRow, 7, new Color(208, 206, 206, 0));
/* 761 */                 subTable.setColBorderColor(nextRow, 8, new Color(208, 206, 206, 0));
/* 762 */                 subTable.setColBorderColor(nextRow, 9, new Color(208, 206, 206, 0));
/* 763 */                 subTable.setColBorderColor(nextRow, 10, new Color(208, 206, 206, 0));
/* 764 */                 subTable.setColBorderColor(nextRow, 11, new Color(208, 206, 206, 0));
/*     */                 
/* 766 */                 subTable.setRowBorder(nextRow, 4097);
/*     */                 
/* 768 */                 nextRow++;
/*     */                 
/* 770 */                 subTable.setRowBorderColor(nextRow - 2, Color.lightGray);
/* 771 */                 subTable.setRowBorderColor(nextRow - 1, Color.white);
/* 772 */                 subTable.setRowBorderColor(nextRow, Color.lightGray);
/*     */                 
/* 774 */                 subTable.setRowBorderColor(nextRow, 0, Color.lightGray);
/* 775 */                 subTable.setRowBorderColor(nextRow, 6, Color.lightGray);
/*     */                 
/* 777 */                 subTable.setColBorderColor(nextRow, -1, new Color(208, 206, 206, 0));
/* 778 */                 subTable.setColBorderColor(nextRow, 0, new Color(208, 206, 206, 0));
/* 779 */                 subTable.setColBorderColor(nextRow, 1, new Color(208, 206, 206, 0));
/* 780 */                 subTable.setColBorderColor(nextRow, 2, new Color(208, 206, 206, 0));
/* 781 */                 subTable.setColBorderColor(nextRow, 3, new Color(208, 206, 206, 0));
/* 782 */                 subTable.setColBorderColor(nextRow, 4, new Color(208, 206, 206, 0));
/* 783 */                 subTable.setColBorderColor(nextRow, 5, new Color(208, 206, 206, 0));
/* 784 */                 subTable.setColBorderColor(nextRow, 6, new Color(208, 206, 206, 0));
/* 785 */                 subTable.setColBorderColor(nextRow, 7, new Color(208, 206, 206, 0));
/* 786 */                 subTable.setColBorderColor(nextRow, 8, new Color(208, 206, 206, 0));
/* 787 */                 subTable.setColBorderColor(nextRow, 9, new Color(208, 206, 206, 0));
/* 788 */                 subTable.setColBorderColor(nextRow, 10, new Color(208, 206, 206, 0));
/* 789 */                 subTable.setColBorderColor(nextRow, 11, new Color(208, 206, 206, 0));
/*     */                 
/* 791 */                 subTable.setRowInsets(nextRow, new Insets(0, 0, 0, 0));
/* 792 */                 if (mfgComment != null && mfgComment.trim().length() > 0) {
/*     */                   
/* 794 */                   subTable.setRowAutoSize(true);
/* 795 */                   subTable.setObject(nextRow, 5, mfgComment);
/* 796 */                   subTable.setFont(nextRow, 5, new Font("Arial", 2, 6));
/*     */                 }
/* 798 */                 else if (artistLength < 26 && labelLength < 21 && idLength + pd.length() < 15) {
/*     */                   
/* 800 */                   subTable.setRowHeight(nextRow, 3);
/*     */                 }
/* 802 */                 else if (labelLength > 30) {
/*     */                   
/* 804 */                   subTable.setRowHeight(nextRow, 8);
/*     */                 } 
/* 806 */                 body = new SectionBand(report);
/* 807 */                 if (mfgComment != null && mfgComment.trim().length() > 0) {
/*     */                   
/* 809 */                   double lfLineCount = 1.5D;
/* 810 */                   if (mfgComment.trim().length() > 100) {
/* 811 */                     lfLineCount = 8.0D;
/*     */                   }
/* 813 */                   if (mfgComment.trim().length() > 40) {
/* 814 */                     lfLineCount = 4.0D;
/*     */                   }
/* 816 */                   body.setHeight((float)lfLineCount);
/*     */                 }
/*     */                 else {
/*     */                   
/* 820 */                   body.setHeight(1.5F);
/*     */                 } 
/* 822 */                 body.addTable(subTable, new Rectangle(800, 800));
/* 823 */                 body.setBottomBorder(0);
/* 824 */                 body.setTopBorder(0);
/* 825 */                 body.setShrinkToFit(true);
/* 826 */                 body.setVisible(true);
/* 827 */                 group = new DefaultSectionLens(null, group, body);
/*     */                 
/* 829 */                 nextRow = 0;
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/* 835 */       group = new DefaultSectionLens(hbandHeader, group, null);
/* 836 */       report.addSection(group, rowCountTable);
/* 837 */       group = null;
/*     */     }
/* 839 */     catch (Exception e) {
/*     */       
/* 841 */       System.out.println(">>>>>>>>ReportHandler.fillUmlNewReleaseMasterScheduleMetaSubHandler(): exception: " + e + " \n ******\n " + e.getMessage());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static void setColBorderColors(int rowNum, int start, int end, DefaultTableLens table, Color color) {
/* 848 */     for (int i = start; i < end; i++) {
/* 849 */       table.setColBorderColor(rowNum, i, Color.white);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static void setRowBorderColors(int rowNum, int start, int end, DefaultTableLens table, Color color) {
/* 856 */     for (int i = start; i < end; i++) {
/* 857 */       table.setRowBorderColor(rowNum, i, Color.white);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static void setColBorders(int rowNum, int start, int end, DefaultTableLens table, int size) {
/* 864 */     for (int i = start; i < end; i++) {
/* 865 */       table.setColBorder(rowNum, i, size);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private static Hashtable buildPlantHash() {
/* 871 */     plantNames = new Hashtable();
/* 872 */     String plantQuery = " SELECT det_value, description FROM vi_Lookup_SubDetail WHERE field_id = 22";
/* 873 */     JdbcConnector connector = MilestoneHelper.getConnector(plantQuery);
/* 874 */     connector.setForwardOnly(false);
/* 875 */     connector.runQuery();
/* 876 */     while (connector.more()) {
/*     */       
/* 878 */       String plantId = connector.getField("det_value");
/* 879 */       String plantDescription = connector.getField("description");
/* 880 */       if (plantId != null && !plantId.equals("") && plantDescription != null && !plantDescription.equals("")) {
/* 881 */         plantNames.put(plantId, plantDescription);
/*     */       }
/* 883 */       connector.next();
/*     */     } 
/* 885 */     connector.close();
/* 886 */     return plantNames;
/*     */   }
/*     */ 
/*     */   
/*     */   private static Hashtable buildTotals(Hashtable namesHash) {
/* 891 */     Enumeration keysEnum = namesHash.keys();
/* 892 */     Hashtable plantTotals = new Hashtable();
/* 893 */     while (keysEnum.hasMoreElements()) {
/* 894 */       plantTotals.put((String)keysEnum.nextElement(), "0");
/*     */     }
/* 896 */     return plantTotals;
/*     */   }
/*     */ 
/*     */   
/*     */   private static boolean totalsExist(Hashtable plantTotals) {
/* 901 */     Enumeration keysEnum = plantTotals.keys();
/* 902 */     while (keysEnum.hasMoreElements()) {
/*     */       
/* 904 */       int total = Integer.parseInt((String)plantTotals.get(keysEnum.nextElement()));
/* 905 */       if (total > 0) {
/* 906 */         return true;
/*     */       }
/*     */     } 
/* 909 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   private static void printTotals(Hashtable plantTotals, Hashtable plantNames, DefaultTableLens subTable, int nextRow) {
/* 914 */     Enumeration keysEnum = plantTotals.keys();
/* 915 */     Vector keys = new Vector();
/* 916 */     int columnCount = 0;
/* 917 */     while (keysEnum.hasMoreElements()) {
/* 918 */       keys.add(keysEnum.nextElement());
/*     */     }
/* 920 */     int maxCol = 13;
/* 921 */     for (int i = 0; i < keys.size(); i++) {
/*     */       
/* 923 */       String idString = (plantTotals.get(keys.get(i)) != null) ? (String)plantTotals.get(keys.get(i)) : "0";
/* 924 */       int total = Integer.parseInt(idString);
/* 925 */       String plantName = (plantNames.get(keys.get(i)) != null) ? (String)plantNames.get(keys.get(i)) : "";
/* 926 */       if (total > 0 && columnCount < 14) {
/*     */         
/* 928 */         subTable.setObject(nextRow, columnCount, String.valueOf(plantName) + ":\n" + MilestoneHelper.formatQuantityWithCommas(String.valueOf(total)));
/* 929 */         columnCount++;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\UmlNewReleaseMasterScheduleMetaSubHandler.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */