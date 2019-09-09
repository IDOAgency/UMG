/*     */ package WEB-INF.classes.com.universal.milestone;
/*     */ 
/*     */ import com.techempower.ComponentLog;
/*     */ import com.techempower.gemini.Context;
/*     */ import com.techempower.gemini.GeminiApplication;
/*     */ import com.universal.milestone.CapitolProductionSchedulePrintSubHandler;
/*     */ import com.universal.milestone.Form;
/*     */ import com.universal.milestone.MilestoneHelper;
/*     */ import com.universal.milestone.MilestoneHelper_2;
/*     */ import com.universal.milestone.Plant;
/*     */ import com.universal.milestone.Schedule;
/*     */ import com.universal.milestone.ScheduledTask;
/*     */ import com.universal.milestone.SecureHandler;
/*     */ import com.universal.milestone.Selection;
/*     */ import com.universal.milestone.SelectionManager;
/*     */ import com.universal.milestone.StatusJSPupdate;
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
/*     */ import java.util.Calendar;
/*     */ import java.util.Collections;
/*     */ import java.util.Date;
/*     */ import java.util.Iterator;
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
/*     */ public class CapitolProductionSchedulePrintSubHandler
/*     */   extends SecureHandler
/*     */ {
/*     */   public static final String COMPONENT_CODE = "hCapProd";
/*     */   public GeminiApplication application;
/*     */   public ComponentLog log;
/*     */   
/*     */   public CapitolProductionSchedulePrintSubHandler(GeminiApplication application) {
/*  68 */     this.application = application;
/*  69 */     this.log = application.getLog("hCapProd");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  77 */   public String getDescription() { return "Sub Report"; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static void fillCapitolProductionUpdateForPrint(XStyleSheet report, Context context) {
/*  88 */     int COL_LINE_STYLE = 4097;
/*  89 */     int HEADER_FONT_SIZE = 12;
/*     */     
/*  91 */     double ldLineVal = 0.3D;
/*  92 */     ComponentLog log = context.getApplication().getLog("hCapProd");
/*     */ 
/*     */     
/*  95 */     StatusJSPupdate statusJSPupdate = new StatusJSPupdate(context);
/*     */ 
/*     */     
/*  98 */     statusJSPupdate.updateStatus(0, 0, "start_gathering", 0);
/*     */ 
/*     */     
/* 101 */     Vector selections = MilestoneHelper.getSelectionsForReport(context);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 107 */     statusJSPupdate.updateStatus(0, 0, "start_report", 0);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 114 */       Form reportForm = (Form)context.getSessionValue("reportForm");
/*     */       
/* 116 */       Calendar beginStDate = (reportForm.getStringValue("beginDate") != null && 
/* 117 */         reportForm.getStringValue("beginDate").length() > 0) ? 
/* 118 */         MilestoneHelper.getDate(reportForm.getStringValue("beginDate")) : null;
/*     */       
/* 120 */       Calendar endStDate = (reportForm.getStringValue("endDate") != null && 
/* 121 */         reportForm.getStringValue("endDate").length() > 0) ? 
/* 122 */         MilestoneHelper.getDate(reportForm.getStringValue("endDate")) : null;
/*     */       
/* 124 */       report.setElement("crs_startdate", MilestoneHelper.getFormatedDate(beginStDate));
/* 125 */       report.setElement("crs_enddate", MilestoneHelper.getFormatedDate(endStDate));
/*     */       
/* 127 */       SimpleDateFormat formatter = new SimpleDateFormat("MMMM d, yyyy");
/* 128 */       String todayLong = formatter.format(new Date());
/* 129 */       report.setElement("crs_bottomdate", todayLong);
/*     */       
/* 131 */       SectionBand hbandType = new SectionBand(report);
/* 132 */       SectionBand hbandCategory = new SectionBand(report);
/* 133 */       SectionBand body = new SectionBand(report);
/* 134 */       SectionBand footer = new SectionBand(report);
/* 135 */       DefaultSectionLens group = null;
/*     */       
/* 137 */       DefaultTableLens table_contents = null;
/* 138 */       DefaultTableLens rowCountTable = null;
/* 139 */       DefaultTableLens columnHeaderTable = null;
/*     */ 
/*     */       
/* 142 */       DefaultTableLens subTable = null;
/*     */ 
/*     */       
/* 145 */       int numSelections = selections.size() * 2;
/* 146 */       int numRows = numSelections + 5;
/*     */ 
/*     */       
/* 149 */       rowCountTable = new DefaultTableLens(2, 10000);
/* 150 */       table_contents = new DefaultTableLens(1, 13);
/*     */ 
/*     */       
/* 153 */       int nextRow = 0;
/* 154 */       hbandType = new SectionBand(report);
/* 155 */       hbandType.setHeight(0.95F);
/* 156 */       hbandType.setShrinkToFit(true);
/* 157 */       hbandType.setVisible(true);
/*     */       
/* 159 */       hbandCategory = new SectionBand(report);
/* 160 */       hbandCategory.setHeight(2.0F);
/* 161 */       hbandCategory.setShrinkToFit(true);
/* 162 */       hbandCategory.setVisible(true);
/* 163 */       hbandCategory.setBottomBorder(0);
/* 164 */       hbandCategory.setLeftBorder(0);
/* 165 */       hbandCategory.setRightBorder(0);
/* 166 */       hbandCategory.setTopBorder(0);
/*     */ 
/*     */       
/* 169 */       table_contents.setHeaderRowCount(0);
/*     */ 
/*     */       
/* 172 */       table_contents.setColWidth(0, 95);
/* 173 */       table_contents.setColWidth(1, 225);
/* 174 */       table_contents.setColWidth(2, 140);
/* 175 */       table_contents.setColWidth(3, 120);
/* 176 */       table_contents.setColWidth(4, 70);
/*     */       
/* 178 */       table_contents.setColWidth(5, 70);
/* 179 */       table_contents.setColWidth(6, 70);
/* 180 */       table_contents.setColWidth(7, 70);
/* 181 */       table_contents.setColWidth(8, 70);
/* 182 */       table_contents.setColWidth(9, 70);
/* 183 */       table_contents.setColWidth(10, 80);
/* 184 */       table_contents.setColWidth(11, 250);
/*     */ 
/*     */       
/* 187 */       table_contents.setRowBorderColor(nextRow, Color.lightGray);
/* 188 */       table_contents.setRowBorder(4097);
/* 189 */       table_contents.setColBorder(4097);
/*     */ 
/*     */       
/* 192 */       columnHeaderTable = new DefaultTableLens(1, 13);
/*     */       
/* 194 */       nextRow = 0;
/*     */       
/* 196 */       columnHeaderTable.setColWidth(0, 95);
/* 197 */       columnHeaderTable.setColWidth(1, 225);
/* 198 */       columnHeaderTable.setColWidth(2, 140);
/* 199 */       columnHeaderTable.setColWidth(3, 120);
/* 200 */       columnHeaderTable.setColWidth(4, 70);
/*     */       
/* 202 */       columnHeaderTable.setColWidth(5, 70);
/* 203 */       columnHeaderTable.setColWidth(6, 70);
/* 204 */       columnHeaderTable.setColWidth(7, 70);
/* 205 */       columnHeaderTable.setColWidth(8, 70);
/* 206 */       columnHeaderTable.setColWidth(9, 70);
/* 207 */       columnHeaderTable.setColWidth(10, 80);
/* 208 */       columnHeaderTable.setColWidth(11, 250);
/*     */       
/* 210 */       System.out.println("--- Defining columns headings --- ");
/* 211 */       columnHeaderTable.setRowAlignment(nextRow, 34);
/* 212 */       columnHeaderTable.setObject(nextRow, 0, "Release\nDate");
/* 213 */       columnHeaderTable.setObject(nextRow, 1, "UPC\nLocal Prod #\nArtist\nTitle");
/* 214 */       columnHeaderTable.setObject(nextRow, 2, "Label");
/* 215 */       columnHeaderTable.setObject(nextRow, 3, "Format");
/* 216 */       columnHeaderTable.setObject(nextRow, 4, "Pricing");
/*     */       
/* 218 */       columnHeaderTable.setObject(nextRow, 5, "Label Copy\nDue");
/* 219 */       columnHeaderTable.setObject(nextRow, 6, "Copy to Art");
/* 220 */       columnHeaderTable.setObject(nextRow, 7, "Audio to\nManuf.");
/* 221 */       columnHeaderTable.setObject(nextRow, 8, "Art to\nPrinter");
/* 222 */       columnHeaderTable.setObject(nextRow, 9, "IO");
/* 223 */       columnHeaderTable.setObject(nextRow, 10, "Production Status");
/* 224 */       columnHeaderTable.setObject(nextRow, 11, "Comments/Pkg Info");
/*     */       
/* 226 */       columnHeaderTable.setRowFont(nextRow, new Font("Arial", 1, 8));
/* 227 */       columnHeaderTable.setRowBorderColor(nextRow, Color.lightGray);
/* 228 */       columnHeaderTable.setRowBorderColor(nextRow - 1, Color.lightGray);
/*     */       
/* 230 */       columnHeaderTable.setColBorderColor(nextRow, -1, Color.lightGray);
/* 231 */       columnHeaderTable.setColBorderColor(nextRow, 0, Color.lightGray);
/* 232 */       columnHeaderTable.setColBorderColor(nextRow, 1, Color.lightGray);
/* 233 */       columnHeaderTable.setColBorderColor(nextRow, 2, Color.lightGray);
/* 234 */       columnHeaderTable.setColBorderColor(nextRow, 3, Color.lightGray);
/* 235 */       columnHeaderTable.setColBorderColor(nextRow, 4, Color.lightGray);
/*     */       
/* 237 */       columnHeaderTable.setColBorderColor(nextRow, 5, Color.lightGray);
/* 238 */       columnHeaderTable.setColBorderColor(nextRow, 6, Color.lightGray);
/* 239 */       columnHeaderTable.setColBorderColor(nextRow, 7, Color.lightGray);
/* 240 */       columnHeaderTable.setColBorderColor(nextRow, 8, Color.lightGray);
/* 241 */       columnHeaderTable.setColBorderColor(nextRow, 9, Color.lightGray);
/* 242 */       columnHeaderTable.setColBorderColor(nextRow, 10, Color.lightGray);
/* 243 */       columnHeaderTable.setColBorderColor(nextRow, 11, Color.lightGray);
/*     */       
/* 245 */       columnHeaderTable.setRowBorderColor(nextRow, Color.lightGray);
/* 246 */       nextRow++;
/*     */       
/* 248 */       hbandType.addTable(columnHeaderTable, new Rectangle(0, 0, 800, 50));
/*     */       
/* 250 */       hbandType.setBottomBorder(0);
/*     */ 
/*     */ 
/*     */       
/* 254 */       footer.setVisible(false);
/* 255 */       footer.setHeight(0.1F);
/* 256 */       footer.setShrinkToFit(true);
/* 257 */       footer.setBottomBorder(0);
/*     */ 
/*     */ 
/*     */       
/* 261 */       MilestoneHelper.setSelectionSorting(selections, 12);
/* 262 */       Collections.sort(selections);
/*     */ 
/*     */       
/* 265 */       MilestoneHelper.setSelectionSorting(selections, 4);
/* 266 */       Collections.sort(selections);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 273 */       MilestoneHelper.setSelectionSorting(selections, 22);
/* 274 */       Collections.sort(selections);
/*     */ 
/*     */       
/* 277 */       MilestoneHelper.setSelectionSorting(selections, 9);
/* 278 */       Collections.sort(selections);
/*     */ 
/*     */       
/* 281 */       MilestoneHelper.setSelectionSorting(selections, 1);
/* 282 */       Collections.sort(selections);
/*     */ 
/*     */       
/* 285 */       int commentLines = 0;
/*     */ 
/*     */ 
/*     */       
/* 289 */       for (int j = 0; j < selections.size(); j++) {
/*     */ 
/*     */         
/* 292 */         statusJSPupdate.updateStatus(selections.size(), j, "start_report", 0);
/* 293 */         Selection sel = (Selection)selections.elementAt(j);
/* 294 */         sel = SelectionManager.getInstance().getSelectionAndSchedule(sel.getSelectionID());
/*     */ 
/*     */         
/* 297 */         String USIntRelease = "";
/* 298 */         if (SelectionManager.getLookupObjectValue(sel.getSelectionStatus()).equalsIgnoreCase("TBS")) {
/* 299 */           USIntRelease = "TBS ";
/* 300 */         } else if (SelectionManager.getLookupObjectValue(sel.getSelectionStatus()).equalsIgnoreCase("ITW")) {
/* 301 */           USIntRelease = "ITW ";
/*     */         } 
/*     */         
/* 304 */         if (USIntRelease != null && !USIntRelease.equals("")) {
/* 305 */           USIntRelease = String.valueOf(USIntRelease) + MilestoneHelper.getFormatedDate(sel.getStreetDate());
/*     */         } else {
/*     */           
/* 308 */           USIntRelease = String.valueOf(USIntRelease) + MilestoneHelper.getFormatedDate(sel.getStreetDate()) + "\n" + MilestoneHelper.getFormatedDate(sel.getInternationalDate());
/*     */         } 
/*     */ 
/*     */ 
/*     */         
/* 313 */         String upc = (sel.getUpc() != null) ? sel.getUpc() : " ";
/*     */ 
/*     */         
/* 316 */         upc = MilestoneHelper_2.getRMSReportFormat(upc, "UPC", sel.getIsDigital());
/*     */ 
/*     */         
/* 319 */         String localProductNumber = "";
/* 320 */         if (sel.getPrefixID() != null && sel.getPrefixID().getAbbreviation() != null) {
/* 321 */           localProductNumber = sel.getPrefixID().getAbbreviation();
/*     */         }
/* 323 */         localProductNumber = String.valueOf(localProductNumber) + sel.getSelectionNo();
/*     */         
/* 325 */         String artistName = (sel.getFlArtist() != null) ? sel.getFlArtist() : " ";
/*     */         
/* 327 */         String title = (sel.getTitle() != null) ? sel.getTitle() : " ";
/* 328 */         String comment = (sel.getSelectionComments() != null) ? sel.getSelectionComments() : " ";
/*     */ 
/*     */         
/* 331 */         commentLines = MilestoneHelper.lineCount(comment, "");
/*     */         
/* 333 */         String code = (sel.getSellCode() != null) ? sel.getSellCode() : "";
/* 334 */         if (code != null && code.startsWith("-1")) {
/* 335 */           code = "";
/*     */         }
/* 337 */         String retail = "";
/* 338 */         if (sel.getPriceCode() != null && sel.getPriceCode().getRetailCode() != null) {
/* 339 */           retail = sel.getPriceCode().getRetailCode();
/*     */         }
/* 341 */         if (code.length() > 0) {
/* 342 */           retail = "\n" + retail;
/*     */         }
/* 344 */         String price = "";
/* 345 */         if (sel.getPriceCode() != null && sel.getPriceCode().getTotalCost() > 0.0F) {
/* 346 */           price = "\n$" + MilestoneHelper.formatDollarPrice(sel.getPriceCode().getTotalCost());
/*     */         }
/*     */ 
/*     */         
/* 350 */         String label = (sel.getImprint() != null) ? sel.getImprint() : " ";
/* 351 */         String packaging = (sel.getSelectionPackaging() != null) ? sel.getSelectionPackaging() : " ";
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 356 */         String labelAndPackage = String.valueOf(label) + "\n" + packaging;
/*     */ 
/*     */         
/* 359 */         for (int y = 0; y < commentLines; y++)
/*     */         {
/* 361 */           labelAndPackage = String.valueOf(labelAndPackage) + "\n";
/*     */         }
/*     */ 
/*     */         
/* 365 */         String selFormat = String.valueOf(sel.getReleaseType().getName()) + "\n" + 
/* 366 */           sel.getSelectionConfig().getSelectionConfigurationName() + "\n" + 
/* 367 */           sel.getSelectionSubConfig().getSelectionSubConfigurationName();
/*     */ 
/*     */         
/* 370 */         Vector manuPlant = null;
/* 371 */         int poQty = 0;
/* 372 */         String vendorDetail = "";
/* 373 */         if (sel != null) {
/* 374 */           SelectionManager.getInstance().getSelectionManufacturingSubDetail(sel);
/* 375 */           manuPlant = sel.getManufacturingPlants();
/*     */         } 
/*     */         
/* 378 */         if (manuPlant != null) {
/*     */           
/* 380 */           Iterator itmanu = manuPlant.iterator();
/* 381 */           while (itmanu != null && itmanu.hasNext()) {
/*     */             
/* 383 */             Plant plant = (Plant)itmanu.next();
/* 384 */             poQty += plant.orderQty;
/* 385 */             if (plant.getPlant() != null)
/*     */             {
/* 387 */               vendorDetail = String.valueOf(vendorDetail) + (plant.getPlant()).name + " - " + plant.orderQty + "\n";
/*     */             }
/*     */           } 
/*     */         } 
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
/* 402 */         Schedule schedule = sel.getSchedule();
/*     */ 
/*     */         
/* 405 */         String dueDateHolidayFlg = "";
/*     */ 
/*     */         
/* 408 */         String schedulingFormDate = "", labelCopyDate = "", copyToArtDate = "", audioToUMSDate = "", artToPrinterDate = "", productionStatusDate = "";
/*     */ 
/*     */         
/* 411 */         String schedulingForm = "", labelCopy = "", copyToArt = "", audioToUMS = "", artToPrinter = "", productionStatus = "";
/*     */ 
/*     */ 
/*     */         
/* 415 */         Vector tasks = null;
/* 416 */         if (schedule != null)
/*     */         {
/* 418 */           tasks = schedule.getTasks();
/*     */         }
/*     */         
/* 421 */         if (tasks != null) {
/*     */           
/* 423 */           Iterator it = tasks.iterator();
/*     */           
/* 425 */           while (it != null && it.hasNext()) {
/*     */             
/* 427 */             ScheduledTask task = (ScheduledTask)it.next();
/* 428 */             String taskAbbrev = MilestoneHelper.getTaskAbbreivationNameById(task.getTaskAbbreviationID());
/* 429 */             String name = task.getName();
/*     */             
/*     */             try {
/* 432 */               if (name != null) {
/*     */                 
/* 434 */                 name = name.trim();
/*     */ 
/*     */                 
/* 437 */                 dueDateHolidayFlg = MilestoneHelper.getDayType(sel.getCalendarGroup(), task.getDueDate());
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
/* 453 */                 if (taskAbbrev.equalsIgnoreCase("LCD")) {
/*     */ 
/*     */                   
/* 456 */                   labelCopyDate = String.valueOf(MilestoneHelper.getShortDate(task.getDueDate())) + 
/* 457 */                     " " + dueDateHolidayFlg;
/* 458 */                   if (task.getScheduledTaskStatus() != null && 
/* 459 */                     task.getScheduledTaskStatus().equals("N/A")) {
/* 460 */                     labelCopy = "N/A";
/*     */                   } else {
/*     */                     
/* 463 */                     labelCopy = MilestoneHelper.getFormatedDate(task.getCompletionDate());
/*     */                   } 
/* 465 */                   labelCopy = String.valueOf(labelCopy) + "\n";
/* 466 */                   labelCopy = String.valueOf(labelCopy) + ((task.getComments() != null) ? task.getComments() : " "); continue;
/*     */                 } 
/* 468 */                 if (taskAbbrev.equalsIgnoreCase("CTAD")) {
/*     */ 
/*     */                   
/* 471 */                   copyToArtDate = String.valueOf(MilestoneHelper.getShortDate(task.getDueDate())) + 
/* 472 */                     " " + dueDateHolidayFlg;
/* 473 */                   if (task.getScheduledTaskStatus() != null && 
/* 474 */                     task.getScheduledTaskStatus().equals("N/A")) {
/* 475 */                     copyToArt = "N/A";
/*     */                   } else {
/*     */                     
/* 478 */                     copyToArt = MilestoneHelper.getFormatedDate(task.getCompletionDate());
/*     */                   } 
/* 480 */                   copyToArt = String.valueOf(copyToArt) + "\n";
/* 481 */                   copyToArt = String.valueOf(copyToArt) + ((task.getComments() != null) ? task.getComments() : " "); continue;
/*     */                 } 
/* 483 */                 if (taskAbbrev.equalsIgnoreCase("TPS")) {
/*     */ 
/*     */                   
/* 486 */                   audioToUMSDate = String.valueOf(MilestoneHelper.getShortDate(task.getDueDate())) + 
/* 487 */                     " " + dueDateHolidayFlg;
/* 488 */                   if (task.getScheduledTaskStatus() != null && 
/* 489 */                     task.getScheduledTaskStatus().equals("N/A")) {
/*     */                     
/* 491 */                     audioToUMS = "N/A";
/*     */                   } else {
/*     */                     
/* 494 */                     audioToUMS = MilestoneHelper.getFormatedDate(task.getCompletionDate());
/*     */                   } 
/* 496 */                   audioToUMS = String.valueOf(audioToUMS) + "\n";
/* 497 */                   audioToUMS = String.valueOf(audioToUMS) + ((task.getComments() != null) ? task.getComments() : " "); continue;
/*     */                 } 
/* 499 */                 if (taskAbbrev.equalsIgnoreCase("ARTP")) {
/*     */ 
/*     */                   
/* 502 */                   artToPrinterDate = String.valueOf(MilestoneHelper.getShortDate(task.getDueDate())) + 
/* 503 */                     " " + dueDateHolidayFlg;
/* 504 */                   if (task.getScheduledTaskStatus() != null && 
/* 505 */                     task.getScheduledTaskStatus().equals("N/A")) {
/*     */                     
/* 507 */                     artToPrinter = "N/A";
/*     */                   } else {
/*     */                     
/* 510 */                     artToPrinter = MilestoneHelper.getFormatedDate(task.getCompletionDate());
/*     */                   } 
/* 512 */                   artToPrinter = String.valueOf(artToPrinter) + "\n";
/* 513 */                   artToPrinter = String.valueOf(artToPrinter) + ((task.getComments() != null) ? task.getComments() : " "); continue;
/*     */                 } 
/* 515 */                 if (taskAbbrev.equalsIgnoreCase("CPS"))
/*     */                 {
/*     */                   
/* 518 */                   productionStatusDate = String.valueOf(MilestoneHelper.getShortDate(task.getDueDate())) + 
/* 519 */                     " " + dueDateHolidayFlg;
/* 520 */                   if (task.getScheduledTaskStatus() != null && 
/* 521 */                     task.getScheduledTaskStatus().equals("N/A")) {
/*     */                     
/* 523 */                     productionStatus = "N/A";
/*     */                   }
/* 525 */                   else if (task.getScheduledTaskStatus() != null) {
/* 526 */                     productionStatus = task.getScheduledTaskStatus();
/*     */                   }
/*     */                   else {
/*     */                     
/* 530 */                     productionStatus = "";
/*     */                   } 
/* 532 */                   productionStatus = String.valueOf(productionStatus) + "\n";
/* 533 */                   productionStatus = String.valueOf(productionStatus) + ((task.getComments() != null) ? task.getComments() : " ");
/*     */                 }
/*     */               
/*     */               } 
/* 537 */             } catch (Exception e) {
/*     */               
/* 539 */               e.printStackTrace();
/*     */             } 
/*     */           } 
/*     */         } 
/*     */         
/* 544 */         nextRow = 0;
/* 545 */         subTable = new DefaultTableLens(2, 13);
/*     */         
/* 547 */         subTable.setColWidth(0, 95);
/* 548 */         subTable.setColWidth(1, 225);
/* 549 */         subTable.setColWidth(2, 140);
/* 550 */         subTable.setColWidth(3, 120);
/* 551 */         subTable.setColWidth(4, 70);
/*     */         
/* 553 */         subTable.setColWidth(5, 70);
/* 554 */         subTable.setColWidth(6, 70);
/* 555 */         subTable.setColWidth(7, 70);
/* 556 */         subTable.setColWidth(8, 70);
/* 557 */         subTable.setColWidth(9, 70);
/* 558 */         subTable.setColWidth(10, 80);
/* 559 */         subTable.setColWidth(11, 250);
/*     */ 
/*     */         
/* 562 */         subTable.setColBorderColor(nextRow, Color.lightGray);
/* 563 */         subTable.setRowBorderColor(nextRow, Color.lightGray);
/* 564 */         subTable.setRowBorder(4097);
/* 565 */         subTable.setColBorder(4097);
/*     */ 
/*     */ 
/*     */         
/* 569 */         subTable.setRowInsets(nextRow, new Insets(0, 0, 0, 0));
/*     */         
/* 571 */         subTable.setRowAlignment(nextRow, 2);
/*     */ 
/*     */         
/* 574 */         subTable.setSpan(nextRow, 0, new Dimension(1, 2));
/* 575 */         subTable.setObject(nextRow, 0, USIntRelease);
/*     */         
/* 577 */         subTable.setObject(nextRow, 1, "");
/*     */         
/* 579 */         subTable.setObject(nextRow, 2, "");
/* 580 */         subTable.setObject(nextRow, 3, "");
/* 581 */         subTable.setObject(nextRow, 4, "");
/*     */         
/* 583 */         subTable.setObject(nextRow, 5, labelCopyDate);
/* 584 */         subTable.setObject(nextRow, 6, copyToArtDate);
/* 585 */         subTable.setObject(nextRow, 7, audioToUMSDate);
/* 586 */         subTable.setObject(nextRow, 8, artToPrinterDate);
/* 587 */         subTable.setObject(nextRow, 9, "");
/* 588 */         subTable.setObject(nextRow, 10, productionStatusDate);
/* 589 */         subTable.setObject(nextRow, 11, sel.getSelectionComments());
/*     */         
/* 591 */         subTable.setSpan(nextRow, 11, new Dimension(1, 2));
/*     */         
/* 593 */         subTable.setRowFont(nextRow, new Font("Arial", 1, 7));
/*     */ 
/*     */ 
/*     */         
/* 597 */         Font holidayFont = new Font("Arial", 3, 7);
/* 598 */         for (int colIdx = 4; colIdx <= 10; colIdx++) {
/*     */           
/* 600 */           String dueDate = subTable.getObject(nextRow, colIdx).toString();
/* 601 */           if (dueDate != null && dueDate.length() > 0) {
/*     */             
/* 603 */             char lastChar = dueDate.charAt(dueDate.length() - 1);
/* 604 */             if (Character.isLetter(lastChar)) {
/* 605 */               subTable.setFont(nextRow, colIdx, holidayFont);
/*     */             }
/*     */           } 
/*     */         } 
/*     */         
/* 610 */         subTable.setFont(nextRow, 0, new Font("Arial", 0, 7));
/* 611 */         subTable.setFont(nextRow, 1, new Font("Arial", 0, 7));
/*     */         
/* 613 */         subTable.setAlignment(nextRow, 0, 10);
/*     */         
/* 615 */         subTable.setRowHeight(nextRow, 10);
/* 616 */         subTable.setColFont(0, new Font("Arial", 0, 8));
/*     */         
/* 618 */         subTable.setRowBackground(nextRow, Color.white);
/* 619 */         subTable.setBackground(nextRow, 0, Color.white);
/* 620 */         subTable.setBackground(nextRow, 1, Color.lightGray);
/* 621 */         subTable.setBackground(nextRow, 2, Color.lightGray);
/* 622 */         subTable.setBackground(nextRow, 3, Color.lightGray);
/* 623 */         subTable.setBackground(nextRow, 4, Color.lightGray);
/*     */         
/* 625 */         subTable.setBackground(nextRow, 5, Color.lightGray);
/* 626 */         subTable.setBackground(nextRow, 6, Color.lightGray);
/* 627 */         subTable.setBackground(nextRow, 7, Color.lightGray);
/* 628 */         subTable.setBackground(nextRow, 8, Color.lightGray);
/* 629 */         subTable.setBackground(nextRow, 9, Color.lightGray);
/* 630 */         subTable.setBackground(nextRow, 10, Color.lightGray);
/* 631 */         subTable.setBackground(nextRow, 11, Color.white);
/*     */         
/* 633 */         subTable.setColBorderColor(nextRow, -1, Color.lightGray);
/* 634 */         subTable.setColBorderColor(nextRow, 0, Color.lightGray);
/* 635 */         subTable.setColBorderColor(nextRow, 1, Color.lightGray);
/* 636 */         subTable.setColBorderColor(nextRow, 2, Color.lightGray);
/* 637 */         subTable.setColBorderColor(nextRow, 3, Color.lightGray);
/* 638 */         subTable.setColBorderColor(nextRow, 4, Color.lightGray);
/*     */         
/* 640 */         subTable.setColBorderColor(nextRow, 5, Color.lightGray);
/* 641 */         subTable.setColBorderColor(nextRow, 6, Color.lightGray);
/* 642 */         subTable.setColBorderColor(nextRow, 7, Color.lightGray);
/* 643 */         subTable.setColBorderColor(nextRow, 8, Color.lightGray);
/* 644 */         subTable.setColBorderColor(nextRow, 9, Color.lightGray);
/* 645 */         subTable.setColBorderColor(nextRow, 10, Color.lightGray);
/* 646 */         subTable.setColBorderColor(nextRow, 11, Color.lightGray);
/*     */         
/* 648 */         subTable.setRowBorderColor(nextRow, Color.lightGray);
/*     */         
/* 650 */         subTable.setRowBorderColor(nextRow - 1, Color.lightGray);
/* 651 */         subTable.setRowBorderColor(nextRow, 1, Color.white);
/*     */         
/* 653 */         subTable.setColBorder(nextRow, 1, 266240);
/* 654 */         subTable.setColBorder(nextRow, 2, 266240);
/* 655 */         subTable.setColBorder(nextRow, 3, 266240);
/* 656 */         subTable.setColBorder(nextRow, 4, 266240);
/*     */         
/* 658 */         subTable.setColBorder(nextRow, 5, 266240);
/* 659 */         subTable.setColBorder(nextRow, 6, 266240);
/* 660 */         subTable.setColBorder(nextRow, 7, 266240);
/* 661 */         subTable.setColBorder(nextRow, 8, 266240);
/* 662 */         subTable.setColBorder(nextRow, 9, 266240);
/* 663 */         subTable.setColBorder(nextRow, 10, 266240);
/*     */ 
/*     */         
/* 666 */         subTable.setAlignment(nextRow, 0, 10);
/* 667 */         subTable.setAlignment(nextRow, 1, 10);
/* 668 */         subTable.setAlignment(nextRow, 2, 10);
/* 669 */         subTable.setAlignment(nextRow, 3, 10);
/* 670 */         subTable.setAlignment(nextRow, 4, 10);
/*     */         
/* 672 */         subTable.setAlignment(nextRow, 5, 10);
/* 673 */         subTable.setAlignment(nextRow, 6, 10);
/* 674 */         subTable.setAlignment(nextRow, 7, 10);
/* 675 */         subTable.setAlignment(nextRow, 8, 10);
/* 676 */         subTable.setAlignment(nextRow, 9, 10);
/* 677 */         subTable.setAlignment(nextRow, 10, 10);
/* 678 */         subTable.setAlignment(nextRow, 11, 9);
/*     */         
/* 680 */         nextRow++;
/*     */         
/* 682 */         subTable.setRowBorderColor(nextRow, Color.lightGray);
/*     */         
/* 684 */         subTable.setColBorderColor(nextRow, -1, Color.lightGray);
/* 685 */         subTable.setColBorderColor(nextRow, 0, Color.lightGray);
/* 686 */         subTable.setColBorderColor(nextRow, 1, Color.lightGray);
/* 687 */         subTable.setColBorderColor(nextRow, 2, Color.lightGray);
/* 688 */         subTable.setColBorderColor(nextRow, 3, Color.lightGray);
/* 689 */         subTable.setColBorderColor(nextRow, 4, Color.lightGray);
/*     */         
/* 691 */         subTable.setColBorderColor(nextRow, 5, Color.lightGray);
/* 692 */         subTable.setColBorderColor(nextRow, 6, Color.lightGray);
/* 693 */         subTable.setColBorderColor(nextRow, 7, Color.lightGray);
/* 694 */         subTable.setColBorderColor(nextRow, 8, Color.lightGray);
/* 695 */         subTable.setColBorderColor(nextRow, 9, Color.lightGray);
/* 696 */         subTable.setColBorderColor(nextRow, 10, Color.lightGray);
/* 697 */         subTable.setColBorderColor(nextRow, 11, Color.lightGray);
/*     */ 
/*     */         
/* 700 */         subTable.setRowInsets(nextRow, new Insets(0, 0, 0, 0));
/*     */         
/* 702 */         subTable.setRowFont(nextRow, new Font("Arial", 0, 7));
/* 703 */         subTable.setRowAlignment(nextRow, 10);
/*     */ 
/*     */         
/* 706 */         subTable.setObject(nextRow, 0, "");
/* 707 */         subTable.setObject(nextRow, 1, String.valueOf(upc) + "\n" + localProductNumber + "\n" + artistName + "\n" + title);
/* 708 */         subTable.setObject(nextRow, 2, labelAndPackage);
/* 709 */         subTable.setObject(nextRow, 3, selFormat);
/* 710 */         subTable.setObject(nextRow, 4, String.valueOf(code) + " " + retail + " " + price);
/*     */         
/* 712 */         subTable.setObject(nextRow, 5, labelCopy);
/* 713 */         subTable.setObject(nextRow, 6, copyToArt);
/* 714 */         subTable.setObject(nextRow, 7, audioToUMS);
/* 715 */         subTable.setObject(nextRow, 8, artToPrinter);
/* 716 */         subTable.setObject(nextRow, 9, (poQty == 0) ? "" : Integer.valueOf(poQty));
/* 717 */         subTable.setObject(nextRow, 10, productionStatus);
/* 718 */         subTable.setObject(nextRow, 11, sel.getSelectionComments());
/*     */ 
/*     */ 
/*     */         
/* 722 */         body = new SectionBand(report);
/* 723 */         body.addTable(subTable, new Rectangle(800, 800));
/*     */         
/* 725 */         double lfLineCount = 1.5D;
/*     */         
/* 727 */         if (commentLines > 0 || labelAndPackage.length() > 17 || sel.getTitle().length() > 20) {
/*     */           
/* 729 */           body.setHeight(1.5F);
/*     */         }
/*     */         else {
/*     */           
/* 733 */           body.setHeight(1.0F);
/*     */         } 
/*     */         
/* 736 */         if (commentLines > 0 || labelAndPackage.length() > 17 || sel.getTitle().length() > 20 || 
/* 737 */           schedulingForm.length() > 25 || labelCopy.length() > 25 || artToPrinter.length() > 25 || 
/* 738 */           audioToUMS.length() > 25 || productionStatus.length() > 25) {
/*     */           
/* 740 */           if (lfLineCount < commentLines * 0.3D) {
/* 741 */             lfLineCount = commentLines * 0.3D;
/*     */           }
/* 743 */           if (lfLineCount < (labelAndPackage.length() / 8) * 0.3D) {
/* 744 */             lfLineCount = (labelAndPackage.length() / 8) * 0.3D;
/*     */           }
/* 746 */           if (lfLineCount < (sel.getTitle().length() / 8) * 0.3D) {
/* 747 */             lfLineCount = (sel.getTitle().length() / 8) * 0.3D;
/*     */           }
/* 749 */           if (lfLineCount < (schedulingForm.length() / 7) * 0.3D) {
/* 750 */             lfLineCount = (schedulingForm.length() / 7) * 0.3D;
/*     */           }
/* 752 */           if (lfLineCount < (labelCopy.length() / 7) * 0.3D) {
/* 753 */             lfLineCount = (labelCopy.length() / 7) * 0.3D;
/*     */           }
/* 755 */           if (lfLineCount < (artToPrinter.length() / 7) * 0.3D) {
/* 756 */             lfLineCount = (artToPrinter.length() / 7) * 0.3D;
/*     */           }
/* 758 */           if (lfLineCount < (audioToUMS.length() / 7) * 0.3D) {
/* 759 */             lfLineCount = (audioToUMS.length() / 7) * 0.3D;
/*     */           }
/* 761 */           if (lfLineCount < (productionStatus.length() / 7) * 0.3D) {
/* 762 */             lfLineCount = (productionStatus.length() / 7) * 0.3D;
/*     */           }
/* 764 */           body.setHeight((float)lfLineCount);
/*     */         }
/* 766 */         else if (commentLines > 0 || labelAndPackage.length() > 17 || sel.getTitle().length() > 20 || 
/* 767 */           schedulingForm.length() > 5 || labelCopy.length() > 5 || artToPrinter.length() > 5 || 
/* 768 */           audioToUMS.length() > 5 || productionStatus.length() > 5) {
/*     */ 
/*     */ 
/*     */           
/* 772 */           if (lfLineCount < commentLines * 0.3D) {
/* 773 */             lfLineCount = commentLines * 0.3D;
/*     */           }
/* 775 */           if (lfLineCount < (labelAndPackage.length() / 5) * 0.3D) {
/* 776 */             lfLineCount = (labelAndPackage.length() / 5) * 0.3D;
/*     */           }
/* 778 */           if (lfLineCount < (sel.getTitle().length() / 5) * 0.3D) {
/* 779 */             lfLineCount = (sel.getTitle().length() / 5) * 0.3D;
/*     */           }
/* 781 */           if (lfLineCount < (schedulingForm.length() / 5) * 0.3D) {
/* 782 */             lfLineCount = (schedulingForm.length() / 5) * 0.3D;
/*     */           }
/* 784 */           if (lfLineCount < (labelCopy.length() / 5) * 0.3D) {
/* 785 */             lfLineCount = (labelCopy.length() / 5) * 0.3D;
/*     */           }
/* 787 */           if (lfLineCount < (artToPrinter.length() / 5) * 0.3D) {
/* 788 */             lfLineCount = (artToPrinter.length() / 5) * 0.3D;
/*     */           }
/* 790 */           if (lfLineCount < (audioToUMS.length() / 5) * 0.3D) {
/* 791 */             lfLineCount = (audioToUMS.length() / 5) * 0.3D;
/*     */           }
/* 793 */           if (lfLineCount < (productionStatus.length() / 5) * 0.3D) {
/* 794 */             lfLineCount = (productionStatus.length() / 5) * 0.3D;
/*     */           }
/* 796 */           body.setHeight((float)lfLineCount);
/*     */         
/*     */         }
/*     */         else {
/*     */           
/* 801 */           body.setHeight(1.0F);
/*     */         } 
/*     */         
/* 804 */         body.setBottomBorder(0);
/* 805 */         body.setTopBorder(0);
/* 806 */         body.setShrinkToFit(true);
/* 807 */         body.setVisible(true);
/*     */         
/* 809 */         group = new DefaultSectionLens(null, group, body);
/*     */       } 
/*     */       
/* 812 */       group = new DefaultSectionLens(hbandType, group, footer);
/* 813 */       report.addSection(group, rowCountTable);
/* 814 */       group = null;
/*     */ 
/*     */ 
/*     */     
/*     */     }
/* 819 */     catch (Exception e) {
/*     */       
/* 821 */       System.out.println(">>>>>>>>ReportHandler.fillCapitolProductionUpdateForPrint(): exception: " + e);
/* 822 */       e.printStackTrace();
/*     */     } 
/*     */ 
/*     */     
/* 826 */     statusJSPupdate = null;
/*     */   }
/*     */ }


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\CapitolProductionSchedulePrintSubHandler.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */