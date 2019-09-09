/*     */ package WEB-INF.classes.com.universal.milestone;
/*     */ 
/*     */ import com.techempower.ComponentLog;
/*     */ import com.techempower.gemini.Context;
/*     */ import com.techempower.gemini.GeminiApplication;
/*     */ import com.universal.milestone.Form;
/*     */ import com.universal.milestone.LatinoProductionScheduleUpdateForPrintSubHandler;
/*     */ import com.universal.milestone.MilestoneHelper;
/*     */ import com.universal.milestone.MilestoneHelper_2;
/*     */ import com.universal.milestone.Schedule;
/*     */ import com.universal.milestone.ScheduledTask;
/*     */ import com.universal.milestone.SecureHandler;
/*     */ import com.universal.milestone.Selection;
/*     */ import com.universal.milestone.SelectionArtistComparator;
/*     */ import com.universal.milestone.SelectionManager;
/*     */ import com.universal.milestone.SelectionSelectionNumberComparator;
/*     */ import com.universal.milestone.SelectionTitleComparator;
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
/*     */ import java.text.NumberFormat;
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
/*     */ public class LatinoProductionScheduleUpdateForPrintSubHandler
/*     */   extends SecureHandler
/*     */ {
/*     */   public static final String COMPONENT_CODE = "hLat";
/*     */   public GeminiApplication application;
/*     */   public ComponentLog log;
/*     */   
/*     */   public LatinoProductionScheduleUpdateForPrintSubHandler(GeminiApplication application) {
/*  71 */     this.application = application;
/*  72 */     this.log = application.getLog("hLat");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  80 */   public String getDescription() { return "Sub Report"; }
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
/*     */   protected static void fillLatinoProductionScheduleUpdateForPrint(XStyleSheet report, Context context) {
/*  93 */     int COL_LINE_STYLE = 4097;
/*  94 */     int HEADER_FONT_SIZE = 12;
/*     */     
/*  96 */     double ldLineVal = 0.3D;
/*     */ 
/*     */     
/*     */     try {
/* 100 */       HttpServletResponse sresponse = context.getResponse();
/* 101 */       context.putDelivery("status", new String("start_gathering"));
/* 102 */       context.includeJSP("status.jsp", "hiddenFrame");
/* 103 */       sresponse.setContentType("text/plain");
/* 104 */       sresponse.flushBuffer();
/*     */     }
/* 106 */     catch (Exception exception) {}
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 111 */     Vector selections = MilestoneHelper.getSelectionsForReport(context);
/*     */ 
/*     */     
/*     */     try {
/* 115 */       HttpServletResponse sresponse = context.getResponse();
/* 116 */       context.putDelivery("status", new String("start_report"));
/* 117 */       context.putDelivery("percent", new String("10"));
/* 118 */       context.includeJSP("status.jsp", "hiddenFrame");
/* 119 */       sresponse.setContentType("text/plain");
/* 120 */       sresponse.flushBuffer();
/*     */     }
/* 122 */     catch (Exception exception) {}
/*     */ 
/*     */ 
/*     */     
/* 126 */     Color SHADED_AREA_COLOR = Color.lightGray;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 133 */       Form reportForm = (Form)context.getSessionValue("reportForm");
/*     */       
/* 135 */       Calendar beginStDate = (reportForm.getStringValue("beginDate") != null && 
/* 136 */         reportForm.getStringValue("beginDate").length() > 0) ? 
/* 137 */         MilestoneHelper.getDate(reportForm.getStringValue("beginDate")) : null;
/*     */       
/* 139 */       Calendar endStDate = (reportForm.getStringValue("endDate") != null && 
/* 140 */         reportForm.getStringValue("endDate").length() > 0) ? 
/* 141 */         MilestoneHelper.getDate(reportForm.getStringValue("endDate")) : null;
/*     */       
/* 143 */       report.setElement("crs_startdate", MilestoneHelper.getFormatedDate(beginStDate));
/* 144 */       report.setElement("crs_enddate", MilestoneHelper.getFormatedDate(endStDate));
/*     */       
/* 146 */       SimpleDateFormat formatter = new SimpleDateFormat("MMMM d, yyyy");
/* 147 */       String todayLong = formatter.format(new Date());
/* 148 */       report.setElement("crs_bottomdate", todayLong);
/*     */       
/* 150 */       SectionBand hbandType = new SectionBand(report);
/* 151 */       SectionBand hbandCategory = new SectionBand(report);
/* 152 */       SectionBand body = new SectionBand(report);
/* 153 */       SectionBand footer = new SectionBand(report);
/* 154 */       DefaultSectionLens group = null;
/*     */ 
/*     */       
/* 157 */       Hashtable selTable = MilestoneHelper.groupSelectionsBySubConfigAndStreetDate(selections);
/* 158 */       Enumeration configs = selTable.keys();
/* 159 */       Vector configVector = new Vector();
/*     */       
/* 161 */       while (configs.hasMoreElements()) {
/* 162 */         configVector.addElement(configs.nextElement());
/*     */       }
/*     */       
/* 165 */       Collections.sort(configVector);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 170 */       if (configVector.size() > 1) {
/*     */         
/* 172 */         String subConfig1 = (String)configVector.elementAt(0);
/* 173 */         String subConfig2 = (String)configVector.elementAt(1);
/*     */         
/* 175 */         if (subConfig1 != null && subConfig2 != null)
/*     */         {
/* 177 */           if (subConfig1.startsWith("1") && subConfig2.startsWith("7")) {
/*     */             
/* 179 */             configVector.set(0, subConfig2);
/* 180 */             configVector.set(1, subConfig1);
/*     */           } 
/*     */         }
/*     */       } 
/*     */ 
/*     */       
/* 186 */       int numDates = 0;
/* 187 */       for (int i = 0; i < configVector.size(); i++) {
/*     */         
/* 189 */         String configName = (configVector.elementAt(i) != null) ? (String)configVector.elementAt(i) : "";
/* 190 */         Hashtable dateTable = (Hashtable)selTable.get(configName);
/* 191 */         if (dateTable != null)
/*     */         {
/* 193 */           numDates += dateTable.size();
/*     */         }
/*     */       } 
/*     */ 
/*     */       
/* 198 */       int numExtraRows = configVector.size() * 4 + numDates * 4 - configVector.size() + 4;
/*     */ 
/*     */       
/* 201 */       int numSelections = 2 * selections.size();
/* 202 */       int numRows = numSelections + numExtraRows - configVector.size();
/*     */       
/* 204 */       DefaultTableLens table_contents = null;
/* 205 */       DefaultTableLens rowCountTable = null;
/* 206 */       DefaultTableLens columnHeaderTable = null;
/* 207 */       DefaultTableLens subTable = null;
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 212 */       int totalCount = 0;
/* 213 */       int tenth = 1;
/*     */       
/* 215 */       for (int a = 0; a < configVector.size(); a++) {
/*     */         
/* 217 */         totalCount++;
/* 218 */         String configC = (String)configVector.get(a);
/* 219 */         Hashtable monthTableC = (Hashtable)selTable.get(configC);
/*     */         
/* 221 */         if (monthTableC != null) {
/*     */           
/* 223 */           Enumeration monthsC = monthTableC.keys();
/* 224 */           Vector monthVectorC = new Vector();
/*     */           
/* 226 */           while (monthsC.hasMoreElements())
/*     */           {
/* 228 */             monthVectorC.add((String)monthsC.nextElement());
/*     */           }
/*     */           
/* 231 */           Object[] monthArrayC = (Object[])null;
/* 232 */           monthArrayC = monthVectorC.toArray();
/*     */           
/* 234 */           for (int b = 0; b < monthArrayC.length; b++) {
/*     */             
/* 236 */             totalCount++;
/* 237 */             String monthNameC = (String)monthArrayC[b];
/*     */             
/* 239 */             Vector selectionsC = (Vector)monthTableC.get(monthNameC);
/* 240 */             if (selectionsC == null) {
/* 241 */               selectionsC = new Vector();
/*     */             }
/* 243 */             totalCount += selectionsC.size();
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 248 */       tenth = (totalCount > 10) ? (totalCount / 10) : 1;
/*     */       
/* 250 */       HttpServletResponse sresponse = context.getResponse();
/* 251 */       context.putDelivery("status", new String("start_report"));
/* 252 */       context.putDelivery("percent", new String("10"));
/* 253 */       context.includeJSP("status.jsp", "hiddenFrame");
/* 254 */       sresponse.setContentType("text/plain");
/* 255 */       sresponse.flushBuffer();
/*     */       
/* 257 */       int recordCount = 0;
/* 258 */       int count = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 264 */       int nextRow = 0;
/*     */       
/* 266 */       for (int n = 0; n < configVector.size(); n++)
/*     */       {
/* 268 */         String config = (String)configVector.elementAt(n);
/* 269 */         String configHeaderText = !config.trim().equals("") ? config : "Other";
/*     */ 
/*     */         
/* 272 */         rowCountTable = new DefaultTableLens(2, 10000);
/* 273 */         table_contents = new DefaultTableLens(1, 12);
/*     */ 
/*     */         
/* 276 */         nextRow = 0;
/* 277 */         hbandType = new SectionBand(report);
/* 278 */         hbandType.setHeight(0.95F);
/* 279 */         hbandType.setShrinkToFit(true);
/* 280 */         hbandType.setVisible(true);
/*     */ 
/*     */         
/* 283 */         table_contents.setHeaderRowCount(0);
/* 284 */         table_contents.setColWidth(0, 95);
/* 285 */         table_contents.setColWidth(1, 83);
/* 286 */         table_contents.setColWidth(2, 150);
/* 287 */         table_contents.setColWidth(3, 270);
/* 288 */         table_contents.setColWidth(4, 180);
/* 289 */         table_contents.setColWidth(5, 80);
/* 290 */         table_contents.setColWidth(6, 140);
/* 291 */         table_contents.setColWidth(7, 85);
/* 292 */         table_contents.setColWidth(8, 85);
/* 293 */         table_contents.setColWidth(9, 80);
/* 294 */         table_contents.setColWidth(10, 80);
/* 295 */         table_contents.setColWidth(11, 140);
/*     */ 
/*     */         
/* 298 */         table_contents.setSpan(nextRow, 0, new Dimension(12, 1));
/* 299 */         table_contents.setAlignment(nextRow, 0, 2);
/* 300 */         table_contents.setObject(nextRow, 0, configHeaderText);
/* 301 */         table_contents.setRowHeight(nextRow, 15);
/*     */         
/* 303 */         table_contents.setRowBorderColor(nextRow, Color.black);
/* 304 */         table_contents.setRowBorder(nextRow, 0, 266240);
/*     */         
/* 306 */         table_contents.setRowFont(nextRow, new Font("Arial", 3, 12));
/* 307 */         table_contents.setRowBackground(nextRow, Color.white);
/* 308 */         table_contents.setRowForeground(nextRow, Color.black);
/*     */         
/* 310 */         table_contents.setRowBorder(nextRow - 1, 266240);
/* 311 */         table_contents.setColBorder(nextRow, -1, 266240);
/* 312 */         table_contents.setColBorder(nextRow, 0, 266240);
/* 313 */         table_contents.setColBorder(nextRow, 12, 266240);
/* 314 */         table_contents.setColBorder(nextRow, 11, 266240);
/* 315 */         table_contents.setRowBorder(nextRow, 266240);
/*     */         
/* 317 */         table_contents.setRowBorderColor(nextRow - 1, Color.black);
/* 318 */         table_contents.setColBorderColor(nextRow, -1, Color.black);
/* 319 */         table_contents.setColBorderColor(nextRow, 0, Color.black);
/* 320 */         table_contents.setColBorderColor(nextRow, 12, Color.black);
/* 321 */         table_contents.setColBorderColor(nextRow, 11, Color.black);
/* 322 */         table_contents.setRowBorderColor(nextRow, Color.black);
/*     */         
/* 324 */         hbandType.addTable(table_contents, new Rectangle(0, 0, 800, 25));
/*     */         
/* 326 */         columnHeaderTable = new DefaultTableLens(1, 12);
/*     */         
/* 328 */         nextRow = 0;
/*     */         
/* 330 */         columnHeaderTable.setColWidth(0, 95);
/* 331 */         columnHeaderTable.setColWidth(1, 83);
/* 332 */         columnHeaderTable.setColWidth(2, 150);
/* 333 */         columnHeaderTable.setColWidth(3, 270);
/* 334 */         columnHeaderTable.setColWidth(4, 180);
/* 335 */         columnHeaderTable.setColWidth(5, 80);
/* 336 */         columnHeaderTable.setColWidth(6, 140);
/* 337 */         columnHeaderTable.setColWidth(7, 85);
/* 338 */         columnHeaderTable.setColWidth(8, 85);
/* 339 */         columnHeaderTable.setColWidth(9, 80);
/* 340 */         columnHeaderTable.setColWidth(10, 80);
/* 341 */         columnHeaderTable.setColWidth(11, 140);
/*     */         
/* 343 */         columnHeaderTable.setRowBorderColor(nextRow - 1, SHADED_AREA_COLOR);
/* 344 */         columnHeaderTable.setRowBorderColor(nextRow, SHADED_AREA_COLOR);
/* 345 */         columnHeaderTable.setRowBorderColor(nextRow + 1, SHADED_AREA_COLOR);
/*     */         
/* 347 */         columnHeaderTable.setColBorderColor(-1, Color.lightGray);
/* 348 */         columnHeaderTable.setColBorderColor(0, Color.lightGray);
/* 349 */         columnHeaderTable.setColBorderColor(1, Color.lightGray);
/* 350 */         columnHeaderTable.setColBorderColor(2, Color.lightGray);
/* 351 */         columnHeaderTable.setColBorderColor(3, Color.lightGray);
/* 352 */         columnHeaderTable.setColBorderColor(4, Color.lightGray);
/* 353 */         columnHeaderTable.setColBorderColor(5, Color.lightGray);
/* 354 */         columnHeaderTable.setColBorderColor(6, Color.lightGray);
/* 355 */         columnHeaderTable.setColBorderColor(7, Color.lightGray);
/* 356 */         columnHeaderTable.setColBorderColor(8, Color.lightGray);
/* 357 */         columnHeaderTable.setColBorderColor(9, Color.lightGray);
/* 358 */         columnHeaderTable.setColBorderColor(10, Color.lightGray);
/* 359 */         columnHeaderTable.setColBorderColor(11, Color.lightGray);
/*     */ 
/*     */         
/* 362 */         columnHeaderTable.setAlignment(nextRow, 0, 34);
/* 363 */         columnHeaderTable.setAlignment(nextRow, 1, 34);
/* 364 */         columnHeaderTable.setAlignment(nextRow, 2, 34);
/* 365 */         columnHeaderTable.setAlignment(nextRow, 3, 34);
/* 366 */         columnHeaderTable.setAlignment(nextRow, 4, 34);
/* 367 */         columnHeaderTable.setAlignment(nextRow, 5, 34);
/* 368 */         columnHeaderTable.setAlignment(nextRow, 6, 34);
/* 369 */         columnHeaderTable.setAlignment(nextRow, 7, 34);
/* 370 */         columnHeaderTable.setAlignment(nextRow, 8, 34);
/* 371 */         columnHeaderTable.setAlignment(nextRow, 9, 34);
/* 372 */         columnHeaderTable.setAlignment(nextRow, 10, 34);
/* 373 */         columnHeaderTable.setAlignment(nextRow, 11, 34);
/*     */         
/* 375 */         columnHeaderTable.setRowFont(nextRow, new Font("Arial", 1, 9));
/*     */         
/* 377 */         columnHeaderTable.setObject(nextRow, 0, "Release\nDate");
/* 378 */         columnHeaderTable.setObject(nextRow, 1, "Retail\nPrice");
/* 379 */         columnHeaderTable.setObject(nextRow, 2, "Local Product #\nUPC");
/* 380 */         columnHeaderTable.setObject(nextRow, 3, "Artist\nTitle");
/* 381 */         columnHeaderTable.setObject(nextRow, 4, "Label");
/* 382 */         columnHeaderTable.setObject(nextRow, 5, "Quota");
/* 383 */         columnHeaderTable.setObject(nextRow, 6, "Vendor");
/* 384 */         columnHeaderTable.setObject(nextRow, 7, "Master\nto plant");
/* 385 */         columnHeaderTable.setObject(nextRow, 8, "Label\nCopy");
/* 386 */         columnHeaderTable.setObject(nextRow, 9, "Package\nFilm");
/* 387 */         columnHeaderTable.setObject(nextRow, 10, "Depot");
/* 388 */         columnHeaderTable.setObject(nextRow, 11, "Comments");
/*     */         
/* 390 */         hbandType.addTable(columnHeaderTable, new Rectangle(0, 24, 800, 39));
/* 391 */         hbandType.setBottomBorder(0);
/*     */ 
/*     */         
/* 394 */         Hashtable dateTable = (Hashtable)selTable.get(config);
/* 395 */         if (dateTable != null) {
/*     */           
/* 397 */           Enumeration dates = dateTable.keys();
/*     */           
/* 399 */           Vector streetVector = new Vector();
/*     */           
/* 401 */           while (dates.hasMoreElements()) {
/* 402 */             streetVector.addElement(dates.nextElement());
/*     */           }
/*     */           
/* 405 */           Collections.sort(streetVector, new StringDateComparator());
/*     */           
/* 407 */           for (int z = 0; z < streetVector.size(); z++) {
/*     */             
/* 409 */             String date = (String)streetVector.get(z);
/*     */             
/* 411 */             DefaultTableLens dateTableLens = new DefaultTableLens(1, 12);
/*     */             
/* 413 */             hbandCategory = new SectionBand(report);
/* 414 */             hbandCategory.setHeight(0.25F);
/* 415 */             hbandCategory.setShrinkToFit(true);
/* 416 */             hbandCategory.setVisible(true);
/* 417 */             hbandCategory.setBottomBorder(0);
/* 418 */             hbandCategory.setLeftBorder(0);
/* 419 */             hbandCategory.setRightBorder(0);
/* 420 */             hbandCategory.setTopBorder(0);
/*     */             
/* 422 */             nextRow = 0;
/*     */ 
/*     */             
/* 425 */             dateTableLens.setObject(nextRow, 0, date);
/* 426 */             dateTableLens.setSpan(nextRow, 0, new Dimension(12, 1));
/* 427 */             dateTableLens.setRowFont(nextRow, new Font("Arial", 1, 12));
/* 428 */             dateTableLens.setRowHeight(nextRow, 15);
/* 429 */             dateTableLens.setColBorderColor(nextRow, -1, Color.white);
/* 430 */             dateTableLens.setColBorderColor(nextRow, 12, Color.white);
/* 431 */             dateTableLens.setColBorderColor(nextRow, 11, Color.white);
/* 432 */             dateTableLens.setRowBorderColor(nextRow, Color.white);
/* 433 */             dateTableLens.setRowBorderColor(nextRow - 1, Color.white);
/* 434 */             dateTableLens.setRowBackground(nextRow, Color.lightGray);
/* 435 */             dateTableLens.setRowForeground(nextRow, Color.black);
/*     */             
/* 437 */             hbandCategory.addTable(dateTableLens, new Rectangle(800, 800));
/* 438 */             hbandCategory.setBottomBorder(0);
/*     */             
/* 440 */             footer.setVisible(true);
/* 441 */             footer.setHeight(0.1F);
/* 442 */             footer.setShrinkToFit(false);
/* 443 */             footer.setBottomBorder(0);
/*     */             
/* 445 */             group = new DefaultSectionLens(null, group, footer);
/* 446 */             group = new DefaultSectionLens(null, group, hbandCategory);
/* 447 */             group = new DefaultSectionLens(null, group, footer);
/*     */ 
/*     */             
/* 450 */             selections = (Vector)dateTable.get(date);
/* 451 */             if (selections == null) {
/* 452 */               selections = new Vector();
/*     */             }
/* 454 */             Object[] selectionsArray = selections.toArray();
/*     */             
/* 456 */             Arrays.sort(selectionsArray, new SelectionSelectionNumberComparator());
/*     */             
/* 458 */             Arrays.sort(selectionsArray, new SelectionTitleComparator());
/*     */             
/* 460 */             Arrays.sort(selectionsArray, new SelectionArtistComparator());
/*     */ 
/*     */             
/* 463 */             for (int i = 0; i < selectionsArray.length; i++) {
/*     */ 
/*     */ 
/*     */               
/* 467 */               if (count < recordCount / tenth) {
/*     */                 
/* 469 */                 count = recordCount / tenth;
/* 470 */                 sresponse = context.getResponse();
/* 471 */                 context.putDelivery("status", new String("start_report"));
/* 472 */                 context.putDelivery("percent", new String(String.valueOf(count * 10)));
/* 473 */                 context.includeJSP("status.jsp", "hiddenFrame");
/* 474 */                 sresponse.setContentType("text/plain");
/* 475 */                 sresponse.flushBuffer();
/*     */               } 
/*     */               
/* 478 */               recordCount++;
/*     */ 
/*     */               
/* 481 */               Selection sel = (Selection)selectionsArray[i];
/* 482 */               sel = SelectionManager.getInstance().getSelectionAndSchedule(sel.getSelectionID());
/*     */               
/* 484 */               nextRow = 0;
/*     */               
/* 486 */               subTable = new DefaultTableLens(2, 12);
/*     */               
/* 488 */               subTable.setColWidth(0, 95);
/* 489 */               subTable.setColWidth(1, 83);
/* 490 */               subTable.setColWidth(2, 150);
/* 491 */               subTable.setColWidth(3, 270);
/* 492 */               subTable.setColWidth(4, 180);
/* 493 */               subTable.setColWidth(5, 80);
/* 494 */               subTable.setColWidth(6, 140);
/* 495 */               subTable.setColWidth(7, 85);
/* 496 */               subTable.setColWidth(8, 85);
/* 497 */               subTable.setColWidth(9, 80);
/* 498 */               subTable.setColWidth(10, 80);
/* 499 */               subTable.setColWidth(11, 140);
/*     */               
/* 501 */               subTable.setColBorderColor(nextRow, Color.lightGray);
/* 502 */               subTable.setRowBorderColor(nextRow - 1, Color.lightGray);
/* 503 */               subTable.setRowBorderColor(nextRow, Color.lightGray);
/* 504 */               subTable.setRowBorder(266240);
/* 505 */               subTable.setColBorder(266240);
/*     */ 
/*     */               
/* 508 */               subTable.setRowInsets(nextRow, new Insets(0, 0, 0, 0));
/*     */               
/* 510 */               subTable.setRowAlignment(nextRow, 2);
/*     */               
/* 512 */               subTable.setColBorderColor(-1, Color.lightGray);
/* 513 */               subTable.setColBorderColor(0, Color.lightGray);
/* 514 */               subTable.setColBorderColor(1, Color.lightGray);
/* 515 */               subTable.setColBorderColor(2, Color.lightGray);
/* 516 */               subTable.setColBorderColor(3, Color.lightGray);
/* 517 */               subTable.setColBorderColor(4, Color.lightGray);
/* 518 */               subTable.setColBorderColor(5, Color.lightGray);
/* 519 */               subTable.setColBorderColor(6, Color.lightGray);
/* 520 */               subTable.setColBorderColor(7, Color.lightGray);
/* 521 */               subTable.setColBorderColor(8, Color.lightGray);
/* 522 */               subTable.setColBorderColor(9, Color.lightGray);
/* 523 */               subTable.setColBorderColor(10, Color.lightGray);
/* 524 */               subTable.setColBorderColor(11, Color.lightGray);
/*     */ 
/*     */               
/* 527 */               String titleId = "";
/* 528 */               titleId = String.valueOf(sel.getSelectionNo());
/*     */               
/* 530 */               if (sel.getPrefixID() != null) {
/* 531 */                 titleId = String.valueOf(SelectionManager.getLookupObjectValue(sel.getPrefixID())) + " " + titleId;
/*     */               }
/*     */               
/* 534 */               String artist = "";
/* 535 */               artist = sel.getArtist();
/*     */ 
/*     */               
/* 538 */               String comment = (sel.getSelectionComments() != null) ? sel.getSelectionComments() : "";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */               
/* 544 */               String label = sel.getImprint();
/*     */ 
/*     */ 
/*     */ 
/*     */               
/* 549 */               String pack = "";
/* 550 */               pack = sel.getSelectionPackaging();
/*     */ 
/*     */               
/* 553 */               String title = sel.getTitle();
/*     */ 
/*     */               
/* 556 */               String upc = (sel.getUpc() != null) ? sel.getUpc() : "";
/*     */ 
/*     */               
/* 559 */               upc = MilestoneHelper_2.getRMSReportFormat(upc, "UPC", sel.getIsDigital());
/*     */ 
/*     */               
/* 562 */               String selConfig = "";
/* 563 */               selConfig = (sel.getSelectionConfig() != null) ? sel.getSelectionConfig().getSelectionConfigurationAbbreviation() : "";
/*     */ 
/*     */               
/* 566 */               String units = "";
/* 567 */               units = (sel.getNumberOfUnits() > 0) ? String.valueOf(sel.getNumberOfUnits()) : "";
/*     */ 
/*     */               
/* 570 */               String code = (sel.getSellCode() != null) ? sel.getSellCode() : "";
/* 571 */               if (code != null && code.startsWith("-1")) {
/* 572 */                 code = "";
/*     */               }
/*     */               
/* 575 */               String retail = "";
/* 576 */               if (sel.getPriceCode() != null && sel.getPriceCode().getRetailCode() != null) {
/* 577 */                 retail = sel.getPriceCode().getRetailCode();
/*     */               }
/*     */               
/* 580 */               String price = "";
/* 581 */               if (sel.getPriceCode() != null && sel.getPriceCode().getTotalCost() > 0.0F) {
/* 582 */                 price = "$" + MilestoneHelper.formatDollarPrice(sel.getPriceCode().getTotalCost());
/*     */               }
/*     */               
/* 585 */               String street = MilestoneHelper.getFormatedDate(sel.getStreetDate());
/*     */               
/* 587 */               String status = (sel.getSelectionStatus() != null && sel.getSelectionStatus().getName() != null) ? 
/* 588 */                 sel.getSelectionStatus().getName() : "";
/*     */               
/* 590 */               if (status.equalsIgnoreCase("TBS")) {
/* 591 */                 street = "TBS " + street;
/*     */               }
/* 593 */               else if (status.equalsIgnoreCase("In The Works")) {
/* 594 */                 street = "ITW " + street;
/*     */               } 
/*     */               
/* 597 */               String vendor = (sel.getPlant() != null) ? sel.getPlant().getAbbreviation() : "";
/*     */ 
/*     */ 
/*     */               
/* 601 */               Schedule schedule = sel.getSchedule();
/*     */               
/* 603 */               Vector tasks = (schedule != null) ? schedule.getTasks() : null;
/* 604 */               ScheduledTask task = null;
/*     */               
/* 606 */               String LABEL = "";
/* 607 */               String MASTERS = "";
/* 608 */               String PACK = "";
/* 609 */               String DEPOT = "";
/*     */               
/* 611 */               String LABELcom = "";
/* 612 */               String MASTERScom = "";
/* 613 */               String PACKcom = "";
/* 614 */               String DEPOTcom = "";
/*     */               
/* 616 */               if (tasks != null)
/*     */               {
/* 618 */                 for (int j = 0; j < tasks.size(); j++) {
/*     */                   
/* 620 */                   task = (ScheduledTask)tasks.get(j);
/*     */                   
/* 622 */                   if (task != null && task.getDueDate() != null) {
/*     */                     
/* 624 */                     String taskStatus = task.getScheduledTaskStatus();
/* 625 */                     SimpleDateFormat dueDateFormatter = new SimpleDateFormat("M/dd");
/*     */ 
/*     */                     
/* 628 */                     String dueDate = String.valueOf(dueDateFormatter.format(task.getDueDate().getTime())) + 
/* 629 */                       " " + MilestoneHelper.getDayType(sel.getCalendarGroup(), task.getDueDate());
/* 630 */                     String completionDate = MilestoneHelper.getFormatedDate(task.getCompletionDate());
/*     */                     
/* 632 */                     if (!completionDate.equals("")) {
/* 633 */                       completionDate = String.valueOf(completionDate) + "\n";
/*     */                     }
/* 635 */                     if (taskStatus.equalsIgnoreCase("N/A")) {
/* 636 */                       completionDate = "N/A\n";
/*     */                     }
/* 638 */                     String taskAbbrev = MilestoneHelper.getTaskAbbreivationNameById(task.getTaskAbbreviationID());
/* 639 */                     String taskComment = "";
/*     */                     
/* 641 */                     if (task.getComments() != null && !task.getComments().equals("")) {
/* 642 */                       taskComment = task.getComments();
/*     */                     }
/* 644 */                     if (taskAbbrev.equalsIgnoreCase("TPS")) {
/*     */                       
/* 646 */                       MASTERS = dueDate;
/* 647 */                       MASTERScom = String.valueOf(completionDate) + taskComment;
/*     */                     }
/* 649 */                     else if (taskAbbrev.equalsIgnoreCase("LC")) {
/*     */                       
/* 651 */                       LABEL = dueDate;
/* 652 */                       LABELcom = String.valueOf(completionDate) + taskComment;
/*     */                     }
/* 654 */                     else if (taskAbbrev.equalsIgnoreCase("FAP")) {
/*     */                       
/* 656 */                       PACK = dueDate;
/* 657 */                       PACKcom = String.valueOf(completionDate) + taskComment;
/*     */                     }
/* 659 */                     else if (taskAbbrev.equalsIgnoreCase("DEPO")) {
/*     */                       
/* 661 */                       DEPOT = dueDate;
/* 662 */                       DEPOTcom = String.valueOf(completionDate) + taskComment;
/*     */                     } 
/*     */ 
/*     */ 
/*     */ 
/*     */                     
/* 668 */                     task = null;
/*     */                   } 
/*     */                 } 
/*     */               }
/*     */ 
/*     */               
/* 674 */               String[] bigString = {
/* 675 */                   String.valueOf(artist) + "\n" + title, 
/* 676 */                   comment, 
/* 677 */                   DEPOTcom
/*     */                 };
/*     */ 
/*     */ 
/*     */               
/* 682 */               int[] bigLines = {
/* 683 */                   36, 
/* 684 */                   15, 
/* 685 */                   10
/*     */                 };
/*     */               
/* 688 */               int maxLines = 0;
/* 689 */               maxLines = MilestoneHelper.lineCountWCR(bigString, bigLines);
/*     */ 
/*     */ 
/*     */               
/* 693 */               if (sel.getSelectionStatus().getName().equalsIgnoreCase("In The Works") && maxLines == 0)
/*     */               {
/* 695 */                 maxLines = 1;
/*     */               }
/*     */ 
/*     */ 
/*     */ 
/*     */               
/* 701 */               if (maxLines < 3 && !price.equals("") && !code.equals("") && !retail.equals("")) {
/* 702 */                 maxLines = 1;
/*     */               }
/*     */ 
/*     */ 
/*     */               
/* 707 */               String vSpacer = "";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */               
/* 714 */               for (int k = 0; k < maxLines; k++) {
/* 715 */                 vSpacer = String.valueOf(vSpacer) + "\n";
/*     */               }
/*     */               
/* 718 */               subTable.setRowBorderColor(nextRow, Color.lightGray);
/* 719 */               subTable.setRowBorderColor(nextRow + 1, Color.lightGray);
/*     */               
/* 721 */               subTable.setRowInsets(nextRow, new Insets(0, 0, 0, 0));
/* 722 */               subTable.setRowFont(nextRow + 1, new Font("Arial", 0, 8));
/*     */               
/* 724 */               subTable.setObject(nextRow, 0, street);
/* 725 */               subTable.setBackground(nextRow, 0, Color.white);
/* 726 */               subTable.setSpan(nextRow, 0, new Dimension(1, 2));
/* 727 */               subTable.setAlignment(nextRow, 0, 10);
/* 728 */               if (!code.equals("") && !retail.equals(""))
/* 729 */                 subTable.setObject(nextRow, 1, String.valueOf(price) + "\n" + code + " \\ " + retail); 
/* 730 */               subTable.setBackground(nextRow, 1, Color.white);
/* 731 */               subTable.setSpan(nextRow, 1, new Dimension(1, 2));
/* 732 */               subTable.setAlignment(nextRow, 1, 10);
/*     */               
/* 734 */               subTable.setObject(nextRow, 2, String.valueOf(titleId) + "\n" + upc);
/* 735 */               subTable.setBackground(nextRow, 2, Color.white);
/* 736 */               subTable.setSpan(nextRow, 2, new Dimension(1, 2));
/* 737 */               subTable.setAlignment(nextRow, 2, 10);
/*     */               
/* 739 */               subTable.setObject(nextRow, 3, String.valueOf(artist) + "\n" + title);
/* 740 */               subTable.setBackground(nextRow, 3, Color.white);
/* 741 */               subTable.setSpan(nextRow, 3, new Dimension(1, 2));
/* 742 */               subTable.setAlignment(nextRow, 3, 10);
/* 743 */               subTable.setLineWrap(nextRow, 3, true);
/*     */               
/* 745 */               subTable.setObject(nextRow, 4, "                            Due Dates");
/* 746 */               subTable.setSpan(nextRow, 4, new Dimension(2, 1));
/* 747 */               subTable.setColBorderColor(nextRow, 4, Color.lightGray);
/* 748 */               subTable.setColBorderColor(nextRow, 5, Color.lightGray);
/* 749 */               subTable.setColBorder(nextRow, 4, 266240);
/* 750 */               subTable.setFont(nextRow, 4, new Font("Arial", 1, 9));
/* 751 */               subTable.setAlignment(nextRow, 4, 9);
/* 752 */               subTable.setRowHeight(nextRow, 9);
/*     */               
/* 754 */               subTable.setAlignment(nextRow + 1, 4, 10);
/* 755 */               subTable.setObject(nextRow + 1, 4, String.valueOf(label) + vSpacer);
/*     */               
/* 757 */               String quota = "";
/*     */               
/* 759 */               SelectionManager.getInstance().getSelectionManufacturingSubDetail(sel);
/*     */ 
/*     */               
/*     */               try {
/* 763 */                 if (sel.getPoQuantity() > 0)
/*     */                 {
/* 765 */                   NumberFormat nf = NumberFormat.getInstance();
/* 766 */                   quota = nf.format(sel.getPoQuantity());
/*     */                 }
/*     */               
/* 769 */               } catch (Exception exception) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */               
/* 775 */               subTable.setObject(nextRow + 1, 5, quota);
/*     */ 
/*     */               
/* 778 */               subTable.setAlignment(nextRow + 1, 5, 10);
/*     */               
/* 780 */               subTable.setObject(nextRow + 1, 6, vendor);
/* 781 */               subTable.setColBorderColor(nextRow, 6, Color.lightGray);
/* 782 */               subTable.setColBorder(nextRow, 6, 266240);
/* 783 */               subTable.setAlignment(nextRow + 1, 6, 10);
/*     */               
/* 785 */               subTable.setObject(nextRow, 7, MASTERS);
/* 786 */               subTable.setColBorderColor(nextRow, 7, Color.lightGray);
/* 787 */               subTable.setColBorder(nextRow, 7, 266240);
/*     */               
/* 789 */               subTable.setFont(nextRow, 7, new Font("Arial", 1, 8));
/* 790 */               subTable.setAlignment(nextRow, 7, 10);
/*     */               
/* 792 */               subTable.setObject(nextRow, 8, LABEL);
/* 793 */               subTable.setColBorderColor(nextRow, 8, Color.lightGray);
/* 794 */               subTable.setColBorder(nextRow, 8, 266240);
/* 795 */               subTable.setFont(nextRow, 8, new Font("Arial", 1, 8));
/* 796 */               subTable.setAlignment(nextRow, 8, 10);
/* 797 */               subTable.setObject(nextRow, 9, PACK);
/* 798 */               subTable.setColBorderColor(nextRow, 9, Color.lightGray);
/* 799 */               subTable.setColBorder(nextRow, 9, 266240);
/*     */               
/* 801 */               subTable.setFont(nextRow, 9, new Font("Arial", 1, 8));
/* 802 */               subTable.setAlignment(nextRow, 9, 2);
/* 803 */               subTable.setAlignment(nextRow, 9, 10);
/*     */               
/* 805 */               subTable.setObject(nextRow, 10, DEPOT);
/* 806 */               subTable.setColBorderColor(nextRow, 10, Color.lightGray);
/* 807 */               subTable.setColBorder(nextRow, 10, 266240);
/*     */               
/* 809 */               subTable.setFont(nextRow, 10, new Font("Arial", 1, 8));
/* 810 */               subTable.setAlignment(nextRow, 10, 10);
/*     */               
/* 812 */               subTable.setObject(nextRow + 1, 7, MASTERScom);
/* 813 */               subTable.setAlignment(nextRow + 1, 7, 10);
/* 814 */               subTable.setObject(nextRow + 1, 8, LABELcom);
/* 815 */               subTable.setAlignment(nextRow + 1, 8, 10);
/* 816 */               subTable.setObject(nextRow + 1, 9, PACKcom);
/* 817 */               subTable.setAlignment(nextRow + 1, 9, 10);
/* 818 */               subTable.setObject(nextRow + 1, 10, DEPOTcom);
/* 819 */               subTable.setAlignment(nextRow + 1, 10, 10);
/*     */               
/* 821 */               subTable.setRowBackground(nextRow, Color.lightGray);
/* 822 */               subTable.setRowForeground(nextRow, Color.black);
/*     */               
/* 824 */               subTable.setBackground(nextRow, 11, Color.white);
/* 825 */               subTable.setSpan(nextRow, 11, new Dimension(1, 2));
/* 826 */               subTable.setObject(nextRow, 11, comment);
/* 827 */               subTable.setAlignment(nextRow, 11, 10);
/* 828 */               subTable.setRowFont(nextRow, new Font("Arial", 0, 8));
/*     */ 
/*     */ 
/*     */               
/* 832 */               Font holidayFont = new Font("Arial", 3, 8);
/* 833 */               for (int colIdx = 7; colIdx <= 10; colIdx++) {
/* 834 */                 String dueDate = subTable.getObject(nextRow, colIdx).toString();
/* 835 */                 if (dueDate != null && dueDate.length() > 0) {
/* 836 */                   char lastChar = dueDate.charAt(dueDate.length() - 1);
/* 837 */                   if (Character.isLetter(lastChar)) {
/* 838 */                     subTable.setFont(nextRow, colIdx, holidayFont);
/*     */                   }
/*     */                 } 
/*     */               } 
/*     */               
/* 843 */               body = new SectionBand(report);
/* 844 */               double lfLineCount = 1.5D;
/*     */               
/* 846 */               if (maxLines > 0) {
/*     */                 
/* 848 */                 body.setHeight(1.5F);
/*     */               }
/*     */               else {
/*     */                 
/* 852 */                 body.setHeight(1.0F);
/*     */               } 
/*     */ 
/*     */               
/* 856 */               if (maxLines > 3 || 
/* 857 */                 MASTERScom.length() > 10 || LABELcom.length() > 10 || 
/* 858 */                 PACKcom.length() > 10 || DEPOTcom.length() > 10) {
/*     */                 
/* 860 */                 if (lfLineCount < maxLines * 0.3D) {
/* 861 */                   lfLineCount = maxLines * 0.3D;
/*     */                 }
/* 863 */                 if (lfLineCount < (MASTERScom.length() / 7) * 0.3D) {
/* 864 */                   lfLineCount = (MASTERScom.length() / 7) * 0.3D;
/*     */                 }
/* 866 */                 if (lfLineCount < (LABELcom.length() / 8) * 0.3D) {
/* 867 */                   lfLineCount = (LABELcom.length() / 8) * 0.3D;
/*     */                 }
/* 869 */                 if (lfLineCount < (PACKcom.length() / 8) * 0.3D) {
/* 870 */                   lfLineCount = (PACKcom.length() / 8) * 0.3D;
/*     */                 }
/* 872 */                 if (lfLineCount < (DEPOTcom.length() / 8) * 0.3D) {
/* 873 */                   lfLineCount = (DEPOTcom.length() / 8) * 0.3D;
/*     */                 }
/* 875 */                 body.setHeight((float)lfLineCount);
/*     */               }
/*     */               else {
/*     */                 
/* 879 */                 body.setHeight(1.0F);
/*     */               } 
/*     */               
/* 882 */               body.addTable(subTable, new Rectangle(800, 800));
/* 883 */               body.setBottomBorder(0);
/* 884 */               body.setTopBorder(0);
/* 885 */               body.setShrinkToFit(true);
/* 886 */               body.setVisible(true);
/*     */               
/* 888 */               group = new DefaultSectionLens(null, group, body);
/*     */             } 
/*     */           } 
/*     */         } 
/* 892 */         group = new DefaultSectionLens(hbandType, group, footer);
/* 893 */         report.addSection(group, rowCountTable);
/* 894 */         report.addPageBreak();
/* 895 */         group = null;
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     }
/* 906 */     catch (Exception e) {
/*     */       
/* 908 */       System.out.println(">>>>>>>>ReportHandler(): exception: " + e);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\LatinoProductionScheduleUpdateForPrintSubHandler.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */