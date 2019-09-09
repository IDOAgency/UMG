/*     */ package WEB-INF.classes.com.universal.milestone;
/*     */ 
/*     */ import com.techempower.ComponentLog;
/*     */ import com.techempower.gemini.Context;
/*     */ import com.techempower.gemini.GeminiApplication;
/*     */ import com.universal.milestone.DatePeriod;
/*     */ import com.universal.milestone.Form;
/*     */ import com.universal.milestone.MCACustomReleaseForPrintSubHandler;
/*     */ import com.universal.milestone.MilestoneHelper;
/*     */ import com.universal.milestone.MilestoneHelper_2;
/*     */ import com.universal.milestone.MonthYearComparator;
/*     */ import com.universal.milestone.SecureHandler;
/*     */ import com.universal.milestone.Selection;
/*     */ import com.universal.milestone.SelectionManager;
/*     */ import com.universal.milestone.SelectionStatus;
/*     */ import inetsoft.report.CompositeSheet;
/*     */ import inetsoft.report.SectionBand;
/*     */ import inetsoft.report.SeparatorElement;
/*     */ import inetsoft.report.StyleSheet;
/*     */ import inetsoft.report.XStyleSheet;
/*     */ import inetsoft.report.io.Builder;
/*     */ import inetsoft.report.lens.DefaultSectionLens;
/*     */ import inetsoft.report.lens.DefaultTableLens;
/*     */ import java.awt.Color;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Font;
/*     */ import java.awt.Rectangle;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.InputStream;
/*     */ import java.text.SimpleDateFormat;
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
/*     */ public class MCACustomReleaseForPrintSubHandler
/*     */   extends SecureHandler
/*     */ {
/*     */   public static final String COMPONENT_CODE = "hCProd";
/*     */   public GeminiApplication application;
/*     */   public ComponentLog log;
/*     */   
/*     */   public void MCACustomReleaseForPrintSubHandler(GeminiApplication application) {
/*  71 */     this.application = application;
/*  72 */     this.log = application.getLog("hCProd");
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
/*     */   protected static StyleSheet fillMCACustomReleaseForPrint(Context context, String reportPath) {
/*  85 */     Color SHADED_AREA_COLOR = Color.lightGray;
/*  86 */     int COL_LINE_STYLE = 4097;
/*  87 */     int HEADER_FONT_SIZE = 12;
/*  88 */     int NUM_COLUMNS = 8;
/*     */     
/*  90 */     double ldLineVal = 0.3D;
/*  91 */     StyleSheet[] sheets = (StyleSheet[])null;
/*  92 */     ComponentLog log = context.getApplication().getLog("hCProd");
/*     */ 
/*     */ 
/*     */     
/*  96 */     int separatorLineStyle = 266240;
/*  97 */     Color separatorLineColor = Color.black;
/*     */ 
/*     */     
/* 100 */     int tableHeaderLineStyle = 266240;
/*     */     
/* 102 */     Color tableHeaderLineColor = Color.black;
/*     */ 
/*     */ 
/*     */     
/* 106 */     int tableRowLineStyle = 4097;
/*     */     
/* 108 */     Color tableRowLineColor = new Color(208, 206, 206, 0);
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 113 */       HttpServletResponse sresponse = context.getResponse();
/* 114 */       context.putDelivery("status", new String("start_gathering"));
/* 115 */       context.includeJSP("status.jsp", "hiddenFrame");
/* 116 */       sresponse.setContentType("text/plain");
/* 117 */       sresponse.flushBuffer();
/*     */     }
/* 119 */     catch (Exception exception) {}
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 124 */     Vector selections = MilestoneHelper.getSelectionsForReport(context);
/*     */ 
/*     */ 
/*     */     
/* 128 */     Hashtable hTable = new Hashtable();
/* 129 */     Vector vSelections = new Vector();
/* 130 */     Selection selFirst = (Selection)selections.elementAt(0);
/* 131 */     String artistString = selFirst.getArtist();
/*     */ 
/*     */     
/* 134 */     hTable = MilestoneHelper.groupSelectionsByArtistAndMonth(selections);
/*     */ 
/*     */     
/*     */     try {
/* 138 */       HttpServletResponse sresponse = context.getResponse();
/* 139 */       context.putDelivery("status", new String("start_report"));
/* 140 */       context.putDelivery("percent", new String("10"));
/* 141 */       context.includeJSP("status.jsp", "hiddenFrame");
/* 142 */       sresponse.setContentType("text/plain");
/* 143 */       sresponse.flushBuffer();
/*     */     }
/* 145 */     catch (Exception exception) {}
/*     */ 
/*     */ 
/*     */     
/* 149 */     int numSelections = selections.size();
/*     */ 
/*     */     
/* 152 */     MilestoneHelper.setSelectionSorting(selections, 12);
/* 153 */     Collections.sort(selections);
/*     */ 
/*     */     
/* 156 */     MilestoneHelper.setSelectionSorting(selections, 13);
/* 157 */     Collections.sort(selections);
/*     */ 
/*     */     
/* 160 */     MilestoneHelper.setSelectionSorting(selections, 4);
/* 161 */     Collections.sort(selections);
/*     */ 
/*     */     
/* 164 */     MilestoneHelper.setSelectionSorting(selections, 3);
/* 165 */     Collections.sort(selections);
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
/* 176 */     if (numSelections == 0) {
/* 177 */       return null;
/*     */     }
/*     */ 
/*     */     
/*     */     try {
/* 182 */       DefaultTableLens table_contents = null;
/* 183 */       DefaultTableLens rowCountTable = null;
/* 184 */       DefaultTableLens columnHeaderTable = null;
/* 185 */       DefaultTableLens subTable = null;
/* 186 */       DefaultTableLens headerTableLens = null;
/*     */       
/* 188 */       rowCountTable = new DefaultTableLens(2, 10000);
/*     */ 
/*     */ 
/*     */       
/* 192 */       int i = 0;
/* 193 */       String artistName = new String();
/*     */       
/* 195 */       Vector artistsVector = new Vector();
/* 196 */       Enumeration hTableEnum = hTable.keys();
/* 197 */       int count = 0;
/*     */       
/* 199 */       Vector tempArtists = new Vector();
/*     */       
/* 201 */       while (hTableEnum.hasMoreElements())
/*     */       {
/* 203 */         tempArtists.addElement(hTableEnum.nextElement());
/*     */       }
/*     */ 
/*     */       
/* 207 */       int arraySize = tempArtists.size();
/* 208 */       sheets = new StyleSheet[arraySize];
/*     */       
/* 210 */       Collections.sort(tempArtists);
/*     */       
/* 212 */       for (int artistsCounter = 0; artistsCounter < tempArtists.size(); artistsCounter++)
/*     */       {
/*     */         
/* 215 */         String currentArtist = (String)tempArtists.get(artistsCounter);
/*     */ 
/*     */ 
/*     */         
/* 219 */         InputStream input = new FileInputStream(String.valueOf(reportPath) + "\\mca_release_schedule.xml");
/* 220 */         XStyleSheet report = (XStyleSheet)Builder.getBuilder(1, input).read(null);
/*     */         
/* 222 */         SectionBand hbandType = new SectionBand(report);
/* 223 */         SectionBand hbandCategory = new SectionBand(report);
/* 224 */         SectionBand hbandDate = new SectionBand(report);
/* 225 */         SectionBand body = new SectionBand(report);
/* 226 */         SectionBand footer = new SectionBand(report);
/* 227 */         SectionBand spacer = new SectionBand(report);
/* 228 */         DefaultSectionLens group = null;
/*     */         
/* 230 */         footer.setVisible(true);
/* 231 */         footer.setHeight(0.1F);
/* 232 */         footer.setShrinkToFit(false);
/* 233 */         footer.setBottomBorder(0);
/*     */         
/* 235 */         spacer.setVisible(true);
/* 236 */         spacer.setHeight(0.05F);
/* 237 */         spacer.setShrinkToFit(false);
/* 238 */         spacer.setBottomBorder(0);
/*     */ 
/*     */         
/* 241 */         SeparatorElement topSeparator = (SeparatorElement)report.getElement("separator_top");
/* 242 */         SeparatorElement bottomHeaderSeparator = (SeparatorElement)report.getElement("separator_bottom_header");
/* 243 */         SeparatorElement bottomSeparator = (SeparatorElement)report.getElement("separator_bottom");
/* 244 */         if (topSeparator != null) {
/*     */           
/* 246 */           topSeparator.setStyle(separatorLineStyle);
/* 247 */           topSeparator.setForeground(separatorLineColor);
/*     */         } 
/* 249 */         if (bottomHeaderSeparator != null) {
/*     */           
/* 251 */           bottomHeaderSeparator.setStyle(separatorLineStyle);
/* 252 */           bottomHeaderSeparator.setForeground(separatorLineColor);
/*     */         } 
/* 254 */         if (bottomSeparator != null) {
/*     */           
/* 256 */           bottomSeparator.setStyle(separatorLineStyle);
/* 257 */           bottomSeparator.setForeground(separatorLineColor);
/*     */         } 
/*     */ 
/*     */         
/* 261 */         Form reportForm = (Form)context.getSessionValue("reportForm");
/*     */         
/* 263 */         Calendar beginDate = (reportForm.getStringValue("beginDate") != null && 
/* 264 */           reportForm.getStringValue("beginDate").length() > 0) ? 
/* 265 */           MilestoneHelper.getDate(reportForm.getStringValue("beginDate")) : null;
/*     */         
/* 267 */         Calendar endDate = (reportForm.getStringValue("endDate") != null && 
/* 268 */           reportForm.getStringValue("endDate").length() > 0) ? 
/* 269 */           MilestoneHelper.getDate(reportForm.getStringValue("endDate")) : null;
/*     */         
/* 271 */         report.setElement("crs_startdate", MilestoneHelper.getFormatedDate(beginDate));
/* 272 */         report.setElement("crs_enddate", MilestoneHelper.getFormatedDate(endDate));
/*     */         
/* 274 */         SimpleDateFormat formatter = new SimpleDateFormat("MMMM d, yyyy");
/* 275 */         String todayLong = formatter.format(new Date());
/* 276 */         report.setElement("crs_bottomdate", todayLong);
/*     */         
/* 278 */         String strUmlKey = (reportForm.getStringValue("umlKeyTask") != null) ? reportForm.getStringValue("umlKeyTask") : "";
/*     */ 
/*     */         
/* 281 */         Hashtable monthTable = (Hashtable)hTable.get(currentArtist);
/*     */         
/* 283 */         Vector monthVector = new Vector();
/* 284 */         Enumeration ee = monthTable.keys();
/* 285 */         while (ee.hasMoreElements()) {
/* 286 */           monthVector.add((String)ee.nextElement());
/*     */         }
/* 288 */         Collections.sort(monthVector, new MonthYearComparator());
/* 289 */         for (int monthCounter = 0; monthCounter < monthVector.size(); monthCounter++) {
/*     */           
/* 291 */           String monthString = (String)monthVector.get(monthCounter);
/*     */ 
/*     */           
/* 294 */           table_contents = new DefaultTableLens(1, 8);
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 299 */           table_contents.setColAlignment(0, 2);
/* 300 */           table_contents.setColAlignment(1, 1);
/* 301 */           table_contents.setColAlignment(2, 1);
/* 302 */           table_contents.setColAlignment(3, 2);
/* 303 */           table_contents.setColAlignment(4, 2);
/* 304 */           table_contents.setColAlignment(5, 2);
/* 305 */           table_contents.setColAlignment(6, 2);
/* 306 */           table_contents.setColAlignment(7, 12);
/*     */           
/* 308 */           table_contents.setColBorderColor(Color.black);
/* 309 */           table_contents.setRowBorderColor(Color.black);
/* 310 */           table_contents.setRowBorder(0);
/* 311 */           table_contents.setColBorder(0);
/*     */           
/* 313 */           table_contents.setRowBorder(1, 4097);
/*     */ 
/*     */           
/* 316 */           table_contents.setColWidth(0, 55);
/* 317 */           table_contents.setColWidth(1, 128);
/* 318 */           table_contents.setColWidth(2, 75);
/* 319 */           table_contents.setColWidth(3, 65);
/* 320 */           table_contents.setColWidth(4, 70);
/* 321 */           table_contents.setColWidth(5, 45);
/* 322 */           table_contents.setColWidth(6, 45);
/* 323 */           table_contents.setColWidth(7, 56);
/*     */           
/* 325 */           table_contents.setLineWrap(0, 0, false);
/* 326 */           table_contents.setLineWrap(0, 1, false);
/*     */ 
/*     */           
/* 329 */           table_contents.setObject(0, 0, "Product\nCategory ");
/* 330 */           table_contents.setFont(0, 0, new Font("Arial", 3, 10));
/*     */           
/* 332 */           table_contents.setObject(0, 1, "Title");
/* 333 */           table_contents.setFont(0, 1, new Font("Arial", 3, 10));
/*     */           
/* 335 */           table_contents.setObject(0, 2, "Configuration");
/* 336 */           table_contents.setFont(0, 2, new Font("Arial", 3, 10));
/*     */           
/* 338 */           table_contents.setObject(0, 3, "Local Product #");
/* 339 */           table_contents.setFont(0, 3, new Font("Arial", 3, 10));
/*     */           
/* 341 */           table_contents.setObject(0, 4, "UPC");
/* 342 */           table_contents.setFont(0, 4, new Font("Arial", 3, 10));
/*     */           
/* 344 */           table_contents.setObject(0, 5, "Price");
/* 345 */           table_contents.setFont(0, 5, new Font("Arial", 3, 10));
/*     */           
/* 347 */           table_contents.setObject(0, 6, "Street\nDate");
/* 348 */           table_contents.setFont(0, 6, new Font("Arial", 3, 10));
/*     */           
/* 350 */           table_contents.setObject(0, 7, "Release\nWeek");
/* 351 */           table_contents.setFont(0, 7, new Font("Arial", 3, 10));
/*     */           
/* 353 */           table_contents.setRowBorder(-1, 0);
/*     */           
/* 355 */           hbandType.addTable(table_contents, new Rectangle(0, 0, 800, 30));
/*     */           
/* 357 */           int nextRow = 0;
/*     */           
/* 359 */           headerTableLens = new DefaultTableLens(1, 8);
/* 360 */           headerTableLens.setColWidth(0, 5);
/* 361 */           headerTableLens.setSpan(0, 1, new Dimension(2, 1));
/* 362 */           headerTableLens.setColWidth(1, 5);
/* 363 */           headerTableLens.setColWidth(2, 5);
/*     */           
/* 365 */           headerTableLens.setColAlignment(0, 1);
/* 366 */           headerTableLens.setColAlignment(3, 4);
/* 367 */           headerTableLens.setColAlignment(4, 1);
/*     */           
/* 369 */           headerTableLens.setColBorder(-1, 0);
/* 370 */           headerTableLens.setColBorder(0, 0);
/* 371 */           headerTableLens.setColBorder(1, 0);
/* 372 */           headerTableLens.setColBorder(2, 0);
/* 373 */           headerTableLens.setColBorder(3, 0);
/* 374 */           headerTableLens.setColBorder(4, 0);
/* 375 */           headerTableLens.setColBorder(5, 0);
/* 376 */           headerTableLens.setColBorder(6, 0);
/* 377 */           headerTableLens.setColBorder(7, 0);
/*     */           
/* 379 */           headerTableLens.setRowBorder(0, 0);
/* 380 */           headerTableLens.setRowBorder(-1, 0);
/*     */           
/* 382 */           headerTableLens.setRowBackground(0, Color.lightGray);
/*     */           
/* 384 */           headerTableLens.setFont(0, 0, new Font("Arial", 1, 10));
/* 385 */           headerTableLens.setFont(0, 1, new Font("Arial", 0, 10));
/* 386 */           headerTableLens.setFont(0, 3, new Font("Arial", 1, 10));
/* 387 */           headerTableLens.setFont(0, 4, new Font("Arial", 0, 10));
/*     */           
/* 389 */           Calendar cal = MilestoneHelper.getMYDate(monthString);
/* 390 */           String myMonth = MilestoneHelper.getCustomFormatedDate(cal, "MMM");
/* 391 */           headerTableLens.setObject(0, 0, myMonth);
/*     */           
/* 393 */           hbandType.addTable(headerTableLens, new Rectangle(0, 30, 800, 30));
/* 394 */           hbandType.setHeight(1.0F);
/*     */           
/* 396 */           Vector vSel = (Vector)monthTable.get(monthString);
/* 397 */           for (int vIndex = 0; vIndex < vSel.size(); vIndex++) {
/* 398 */             Selection sel = (Selection)vSel.elementAt(vIndex);
/*     */             
/* 400 */             if (vIndex == 0) {
/*     */ 
/*     */               
/* 403 */               String selProject = (sel.getProjectID() != null) ? sel.getProjectID() : "";
/*     */ 
/*     */ 
/*     */               
/* 407 */               String selLabel = sel.getImprint();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */               
/* 413 */               String selArtist = (sel.getArtist() != null) ? sel.getArtist() : "";
/*     */               
/* 415 */               report.setElement("artist", selArtist);
/*     */               
/* 417 */               report.setElement("project", selProject);
/*     */ 
/*     */               
/* 420 */               report.setElement("label", selLabel);
/*     */             } 
/*     */ 
/*     */             
/* 424 */             String selPD = sel.getPressAndDistribution() ? "Yes" : "";
/*     */ 
/*     */             
/* 427 */             String selDivision = (sel.getDivision() != null && sel.getDivision().getName() != null) ? 
/* 428 */               sel.getDivision().getName() : "";
/*     */ 
/*     */             
/* 431 */             String selId = "";
/* 432 */             if (SelectionManager.getLookupObjectValue(sel.getPrefixID()).equals("")) {
/* 433 */               selId = sel.getSelectionNo();
/*     */             } else {
/* 435 */               selId = String.valueOf(SelectionManager.getLookupObjectValue(sel.getPrefixID())) + sel.getSelectionNo();
/*     */             } 
/*     */ 
/*     */             
/* 439 */             String selTitle = (sel.getTitle() != null) ? sel.getTitle() : "";
/*     */             
/* 441 */             String selPrice = "$" + String.valueOf(sel.getPrice());
/*     */             
/* 443 */             String selProductCategory = (sel.getProductCategory() != null) ? sel.getProductCategory().getName() : "";
/*     */             
/* 445 */             DatePeriod dp = MilestoneHelper.getReleaseWeek(sel);
/* 446 */             String selReleaseWeek = (dp != null) ? dp.getName() : "";
/*     */             
/* 448 */             String selConfig = (sel.getSelectionSubConfig() != null && sel.getSelectionSubConfig().getSelectionSubConfigurationAbbreviation() != null) ? 
/* 449 */               sel.getSelectionSubConfig().getSelectionSubConfigurationName() : "";
/*     */ 
/*     */             
/* 452 */             String selUpc = (sel.getUpc() != null) ? sel.getUpc() : "";
/*     */ 
/*     */             
/* 455 */             selUpc = MilestoneHelper_2.getRMSReportFormat(selUpc, "UPC", sel.getIsDigital());
/*     */ 
/*     */             
/* 458 */             String selStreetDate = "";
/* 459 */             selStreetDate = MilestoneHelper.getFormatedDate(sel.getStreetDate());
/*     */ 
/*     */ 
/*     */             
/* 463 */             String statusString = "";
/* 464 */             SelectionStatus status = sel.getSelectionStatus();
/*     */             
/* 466 */             if (status != null) {
/* 467 */               statusString = (status.getName() == null) ? "" : status.getName();
/*     */             }
/* 469 */             if (!statusString.equalsIgnoreCase("ACTIVE"))
/*     */             {
/* 471 */               if (!statusString.equalsIgnoreCase("In The Works")) {
/* 472 */                 selStreetDate = String.valueOf(statusString) + " " + selStreetDate;
/*     */               } else {
/* 474 */                 selStreetDate = "ITW " + selStreetDate;
/*     */               } 
/*     */             }
/* 477 */             nextRow = 0;
/*     */             
/* 479 */             subTable = new DefaultTableLens(1, 8);
/* 480 */             subTable.setColAlignment(0, 1);
/* 481 */             subTable.setColAlignment(1, 1);
/* 482 */             subTable.setColAlignment(2, 1);
/* 483 */             subTable.setColAlignment(3, 4);
/* 484 */             subTable.setColAlignment(4, 4);
/* 485 */             subTable.setColAlignment(5, 4);
/* 486 */             subTable.setColAlignment(6, 4);
/* 487 */             subTable.setColAlignment(7, 4);
/*     */             
/* 489 */             subTable.setColWidth(0, 95);
/* 490 */             subTable.setColWidth(1, 200);
/* 491 */             subTable.setColWidth(2, 115);
/* 492 */             subTable.setColWidth(3, 100);
/* 493 */             subTable.setColWidth(4, 125);
/* 494 */             subTable.setColWidth(5, 65);
/* 495 */             subTable.setColWidth(6, 80);
/* 496 */             subTable.setColWidth(7, 90);
/*     */             
/* 498 */             subTable.setColBorderColor(Color.black);
/* 499 */             subTable.setRowBorderColor(Color.black);
/* 500 */             subTable.setRowBorder(0);
/* 501 */             subTable.setColBorder(0);
/* 502 */             subTable.setRowBorder(2, 4097);
/*     */ 
/*     */             
/* 505 */             subTable.setObject(nextRow, 0, selProductCategory);
/* 506 */             subTable.setObject(nextRow, 1, selTitle);
/* 507 */             subTable.setObject(nextRow, 2, selConfig);
/* 508 */             subTable.setObject(nextRow, 3, selId);
/* 509 */             subTable.setObject(nextRow, 4, selUpc);
/* 510 */             subTable.setObject(nextRow, 5, selPrice);
/* 511 */             subTable.setObject(nextRow, 6, selStreetDate);
/* 512 */             subTable.setObject(nextRow, 7, selReleaseWeek);
/*     */ 
/*     */             
/* 515 */             subTable.setRowFont(nextRow, new Font("Arial", 0, 9));
/*     */ 
/*     */ 
/*     */             
/* 519 */             subTable.setRowBorder(nextRow, tableRowLineStyle);
/* 520 */             subTable.setRowBorderColor(nextRow, tableRowLineColor);
/*     */             
/* 522 */             body = new SectionBand(report);
/* 523 */             double lfLineCount = 1.5D;
/* 524 */             body.setHeight(1.5F);
/*     */             
/* 526 */             body.addTable(subTable, new Rectangle(800, 800));
/* 527 */             body.setBottomBorder(0);
/* 528 */             body.setTopBorder(0);
/* 529 */             body.setShrinkToFit(true);
/* 530 */             body.setVisible(true);
/*     */             
/* 532 */             group = new DefaultSectionLens(null, group, body);
/*     */           } 
/*     */         } 
/* 535 */         group = new DefaultSectionLens(hbandType, group, null);
/* 536 */         report.addSection(group, rowCountTable);
/* 537 */         group = null;
/*     */ 
/*     */         
/* 540 */         sheets[i] = report;
/* 541 */         i++;
/*     */       }
/*     */     
/*     */     }
/* 545 */     catch (Exception e) {
/*     */       
/* 547 */       System.out.println(">>>>>>>>ReportHandler.fillMCACustomReleaseForPrint(): exception: " + e);
/*     */     } 
/*     */ 
/*     */     
/* 551 */     return new CompositeSheet(sheets);
/*     */   }
/*     */ }


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\MCACustomReleaseForPrintSubHandler.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */