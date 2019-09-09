/*     */ package WEB-INF.classes.com.universal.milestone;
/*     */ 
/*     */ import com.techempower.ComponentLog;
/*     */ import com.techempower.gemini.Context;
/*     */ import com.techempower.gemini.GeminiApplication;
/*     */ import com.universal.milestone.CarolineProductionSchedulePrintSubHandler;
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
/*     */ import com.universal.milestone.TemplateManager;
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
/*     */ import java.util.Calendar;
/*     */ import java.util.Collections;
/*     */ import java.util.Date;
/*     */ import java.util.Iterator;
/*     */ import java.util.Locale;
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
/*     */ public class CarolineProductionSchedulePrintSubHandler
/*     */   extends SecureHandler
/*     */ {
/*     */   public static final String COMPONENT_CODE = "hCapProd";
/*     */   public GeminiApplication application;
/*     */   public ComponentLog log;
/*     */   
/*     */   public CarolineProductionSchedulePrintSubHandler(GeminiApplication application) {
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
/*     */   protected static void fillCarolineProductionUpdateForPrint(XStyleSheet report, Context context) {
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
/* 105 */     System.out.println("--- update status bar for building report --- ");
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
/* 150 */       table_contents = new DefaultTableLens(1, 14);
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
/* 175 */       table_contents.setColWidth(3, 130);
/* 176 */       table_contents.setColWidth(4, 70);
/* 177 */       table_contents.setColWidth(5, 70);
/* 178 */       table_contents.setColWidth(6, 70);
/* 179 */       table_contents.setColWidth(7, 70);
/* 180 */       table_contents.setColWidth(8, 70);
/* 181 */       table_contents.setColWidth(9, 70);
/* 182 */       table_contents.setColWidth(10, 70);
/* 183 */       table_contents.setColWidth(11, 70);
/* 184 */       table_contents.setColWidth(12, 80);
/* 185 */       table_contents.setColWidth(13, 180);
/*     */ 
/*     */       
/* 188 */       table_contents.setRowBorderColor(nextRow, Color.lightGray);
/* 189 */       table_contents.setRowBorder(4097);
/* 190 */       table_contents.setColBorder(4097);
/*     */ 
/*     */       
/* 193 */       columnHeaderTable = new DefaultTableLens(1, 14);
/*     */       
/* 195 */       nextRow = 0;
/*     */       
/* 197 */       columnHeaderTable.setColWidth(0, 95);
/* 198 */       columnHeaderTable.setColWidth(1, 225);
/* 199 */       columnHeaderTable.setColWidth(2, 140);
/* 200 */       columnHeaderTable.setColWidth(3, 120);
/* 201 */       columnHeaderTable.setColWidth(4, 80);
/* 202 */       columnHeaderTable.setColWidth(5, 70);
/* 203 */       columnHeaderTable.setColWidth(6, 70);
/* 204 */       columnHeaderTable.setColWidth(7, 70);
/* 205 */       columnHeaderTable.setColWidth(8, 70);
/* 206 */       columnHeaderTable.setColWidth(9, 70);
/* 207 */       columnHeaderTable.setColWidth(10, 70);
/* 208 */       columnHeaderTable.setColWidth(11, 70);
/* 209 */       columnHeaderTable.setColWidth(12, 90);
/* 210 */       columnHeaderTable.setColWidth(13, 180);
/*     */ 
/*     */       
/* 213 */       columnHeaderTable.setRowAlignment(nextRow, 34);
/* 214 */       columnHeaderTable.setObject(nextRow, 0, "Release\nDate");
/* 215 */       columnHeaderTable.setObject(nextRow, 1, "UPC\nLocal Prod #\nArtist\nTitle");
/* 216 */       columnHeaderTable.setObject(nextRow, 2, "Label");
/* 217 */       columnHeaderTable.setObject(nextRow, 3, "Format");
/* 218 */       columnHeaderTable.setObject(nextRow, 4, "Pricing");
/* 219 */       columnHeaderTable.setObject(nextRow, 5, "Label Copy\nDue");
/* 220 */       columnHeaderTable.setObject(nextRow, 6, "FGs Due");
/* 221 */       columnHeaderTable.setObject(nextRow, 7, "Audio to\nManuf.");
/* 222 */       columnHeaderTable.setObject(nextRow, 8, "Art to\nPrinter");
/* 223 */       columnHeaderTable.setObject(nextRow, 9, "BOM");
/* 224 */       columnHeaderTable.setObject(nextRow, 11, "Qty\nDone");
/* 225 */       columnHeaderTable.setObject(nextRow, 10, "IO");
/* 226 */       columnHeaderTable.setObject(nextRow, 12, "Production\nStatus");
/* 227 */       columnHeaderTable.setObject(nextRow, 13, "Comments/Pkg Info");
/*     */ 
/*     */       
/* 230 */       columnHeaderTable.setRowFont(nextRow, new Font("Arial", 1, 8));
/* 231 */       columnHeaderTable.setRowBorderColor(nextRow, Color.lightGray);
/* 232 */       columnHeaderTable.setRowBorderColor(nextRow - 1, Color.lightGray);
/*     */       
/* 234 */       columnHeaderTable.setColBorderColor(nextRow, -1, Color.lightGray);
/* 235 */       columnHeaderTable.setColBorderColor(nextRow, 0, Color.lightGray);
/* 236 */       columnHeaderTable.setColBorderColor(nextRow, 1, Color.lightGray);
/* 237 */       columnHeaderTable.setColBorderColor(nextRow, 2, Color.lightGray);
/* 238 */       columnHeaderTable.setColBorderColor(nextRow, 3, Color.lightGray);
/* 239 */       columnHeaderTable.setColBorderColor(nextRow, 4, Color.lightGray);
/* 240 */       columnHeaderTable.setColBorderColor(nextRow, 5, Color.lightGray);
/* 241 */       columnHeaderTable.setColBorderColor(nextRow, 6, Color.lightGray);
/* 242 */       columnHeaderTable.setColBorderColor(nextRow, 7, Color.lightGray);
/* 243 */       columnHeaderTable.setColBorderColor(nextRow, 8, Color.lightGray);
/* 244 */       columnHeaderTable.setColBorderColor(nextRow, 9, Color.lightGray);
/* 245 */       columnHeaderTable.setColBorderColor(nextRow, 10, Color.lightGray);
/* 246 */       columnHeaderTable.setColBorderColor(nextRow, 11, Color.lightGray);
/* 247 */       columnHeaderTable.setColBorderColor(nextRow, 12, Color.lightGray);
/* 248 */       columnHeaderTable.setColBorderColor(nextRow, 13, Color.lightGray);
/*     */       
/* 250 */       columnHeaderTable.setRowBorderColor(nextRow, Color.lightGray);
/* 251 */       nextRow++;
/*     */       
/* 253 */       hbandType.addTable(columnHeaderTable, new Rectangle(0, 0, 800, 50));
/*     */       
/* 255 */       hbandType.setBottomBorder(0);
/*     */ 
/*     */ 
/*     */       
/* 259 */       footer.setVisible(false);
/* 260 */       footer.setHeight(0.1F);
/* 261 */       footer.setShrinkToFit(true);
/* 262 */       footer.setBottomBorder(0);
/*     */ 
/*     */ 
/*     */       
/* 266 */       MilestoneHelper.setSelectionSorting(selections, 12);
/* 267 */       Collections.sort(selections);
/*     */ 
/*     */       
/* 270 */       MilestoneHelper.setSelectionSorting(selections, 4);
/* 271 */       Collections.sort(selections);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 278 */       MilestoneHelper.setSelectionSorting(selections, 22);
/* 279 */       Collections.sort(selections);
/*     */ 
/*     */       
/* 282 */       MilestoneHelper.setSelectionSorting(selections, 9);
/* 283 */       Collections.sort(selections);
/*     */ 
/*     */       
/* 286 */       MilestoneHelper.setSelectionSorting(selections, 1);
/* 287 */       Collections.sort(selections);
/*     */ 
/*     */       
/* 290 */       int commentLines = 0;
/*     */ 
/*     */ 
/*     */       
/* 294 */       for (int j = 0; j < selections.size(); j++) {
/*     */ 
/*     */         
/* 297 */         statusJSPupdate.updateStatus(selections.size(), j, "start_report", 0);
/* 298 */         Selection sel = (Selection)selections.elementAt(j);
/* 299 */         sel = SelectionManager.getInstance().getSelectionAndSchedule(sel.getSelectionID());
/* 300 */         int templeateId = sel.getTemplateId();
/* 301 */         TemplateManager temmanage = new TemplateManager();
/* 302 */         temmanage.getTemplate(templeateId, false);
/*     */ 
/*     */         
/* 305 */         String USIntRelease = "";
/* 306 */         if (SelectionManager.getLookupObjectValue(sel.getSelectionStatus()).equalsIgnoreCase("TBS")) {
/* 307 */           USIntRelease = "TBS ";
/* 308 */         } else if (SelectionManager.getLookupObjectValue(sel.getSelectionStatus()).equalsIgnoreCase("ITW")) {
/* 309 */           USIntRelease = "ITW ";
/*     */         } 
/*     */         
/* 312 */         if (USIntRelease != null && !USIntRelease.equals("")) {
/* 313 */           USIntRelease = String.valueOf(USIntRelease) + MilestoneHelper.getFormatedDate(sel.getStreetDate());
/*     */         } else {
/*     */           
/* 316 */           USIntRelease = String.valueOf(USIntRelease) + MilestoneHelper.getFormatedDate(sel.getStreetDate()) + "\n" + MilestoneHelper.getFormatedDate(sel.getInternationalDate());
/*     */         } 
/*     */ 
/*     */ 
/*     */         
/* 321 */         String upc = (sel.getUpc() != null) ? sel.getUpc() : " ";
/*     */ 
/*     */         
/* 324 */         upc = MilestoneHelper_2.getRMSReportFormat(upc, "UPC", sel.getIsDigital());
/*     */ 
/*     */         
/* 327 */         String localProductNumber = "";
/* 328 */         if (sel.getPrefixID() != null && sel.getPrefixID().getAbbreviation() != null) {
/* 329 */           localProductNumber = sel.getPrefixID().getAbbreviation();
/*     */         }
/* 331 */         localProductNumber = String.valueOf(localProductNumber) + sel.getSelectionNo();
/*     */         
/* 333 */         String artistName = (sel.getFlArtist() != null) ? sel.getFlArtist() : " ";
/*     */         
/* 335 */         String title = (sel.getTitle() != null) ? sel.getTitle() : " ";
/* 336 */         String comment = (sel.getSelectionComments() != null) ? sel.getSelectionComments() : " ";
/*     */         
/* 338 */         commentLines = MilestoneHelper.lineCount(comment, "");
/*     */         
/* 340 */         String code = (sel.getSellCode() != null) ? sel.getSellCode() : "";
/* 341 */         if (code != null && code.startsWith("-1")) {
/* 342 */           code = "";
/*     */         }
/* 344 */         String retail = "";
/* 345 */         if (sel.getPriceCode() != null && sel.getPriceCode().getRetailCode() != null) {
/* 346 */           retail = sel.getPriceCode().getRetailCode();
/*     */         }
/* 348 */         if (code.length() > 0) {
/* 349 */           retail = "\n" + retail;
/*     */         }
/* 351 */         String price = "";
/* 352 */         if (sel.getPriceCode() != null && sel.getPriceCode().getTotalCost() > 0.0F) {
/* 353 */           price = "\n$" + MilestoneHelper.formatDollarPrice(sel.getPriceCode().getTotalCost());
/*     */         }
/*     */ 
/*     */         
/* 357 */         String label = (sel.getImprint() != null) ? sel.getImprint() : " ";
/* 358 */         String packaging = (sel.getSelectionPackaging() != null) ? sel.getSelectionPackaging() : " ";
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 363 */         String labelAndPackage = String.valueOf(label) + "\n" + packaging;
/*     */ 
/*     */         
/* 366 */         for (int y = 0; y < commentLines; y++)
/*     */         {
/* 368 */           labelAndPackage = String.valueOf(labelAndPackage) + "\n";
/*     */         }
/*     */ 
/*     */         
/* 372 */         String selFormat = String.valueOf(sel.getReleaseType().getName()) + "\n" + 
/* 373 */           sel.getSelectionConfig().getSelectionConfigurationName() + "\n" + 
/* 374 */           sel.getSelectionSubConfig().getSelectionSubConfigurationName();
/*     */ 
/*     */         
/* 377 */         Vector manuPlant = null;
/* 378 */         int poQty = 0;
/* 379 */         String vendorDetail = "";
/* 380 */         if (sel != null) {
/* 381 */           SelectionManager.getInstance().getSelectionManufacturingSubDetail(sel);
/* 382 */           manuPlant = sel.getManufacturingPlants();
/*     */         } 
/* 384 */         String qtyOrders = "";
/* 385 */         if (manuPlant != null) {
/*     */           
/* 387 */           Iterator itmanu = manuPlant.iterator();
/* 388 */           while (itmanu != null && itmanu.hasNext()) {
/*     */             
/* 390 */             Plant plant = (Plant)itmanu.next();
/* 391 */             poQty += plant.orderQty;
/* 392 */             if (plant.getPlant() != null) {
/*     */               
/* 394 */               vendorDetail = String.valueOf(vendorDetail) + (plant.getPlant()).name + " - " + plant.orderQty + "\n";
/*     */               
/* 396 */               qtyOrders = MilestoneHelper.removeCommas(MilestoneHelper.formatQuantityWithCommas(String.valueOf(plant.getCompletedQty())));
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
/* 407 */         String dueDateTaks = "";
/* 408 */         String completationDateTaks = "";
/*     */         
/* 410 */         Schedule schedule = sel.getSchedule();
/*     */ 
/*     */         
/* 413 */         String dueDateHolidayFlg = "";
/*     */ 
/*     */         
/* 416 */         String schedulingFormDate = "", labelCopyDate = "", copyToArtDate = "", audioToUMSDate = "", artToPrinterDate = "", bomDate = "", productionStatusDate = "";
/*     */ 
/*     */         
/* 419 */         String schedulingForm = "", labelCopy = "", copyToArt = "", audioToUMS = "", artToPrinter = "", bom = "", productionStatus = "";
/*     */ 
/*     */         
/* 422 */         Number f = NumberFormat.getNumberInstance(Locale.US).parse(Integer.toString(poQty));
/* 423 */         String fS = f.toString();
/* 424 */         Vector tasks = null;
/* 425 */         if (schedule != null)
/*     */         {
/* 427 */           tasks = schedule.getTasks();
/*     */         }
/*     */         
/* 430 */         if (tasks != null) {
/*     */           
/* 432 */           Iterator it = tasks.iterator();
/*     */           
/* 434 */           while (it != null && it.hasNext()) {
/*     */             
/* 436 */             ScheduledTask task = (ScheduledTask)it.next();
/* 437 */             String taskAbbrev = MilestoneHelper.getTaskAbbreivationNameById(task.getTaskAbbreviationID());
/* 438 */             String name = task.getName();
/* 439 */             if (name.equalsIgnoreCase("Finished Goods Due")) {
/*     */               
/* 441 */               dueDateTaks = MilestoneHelper.getShortDate(task.getDueDate());
/* 442 */               completationDateTaks = MilestoneHelper.getFormatedDate(task.getCompletionDate());
/*     */             } 
/*     */ 
/*     */             
/*     */             try {
/* 447 */               if (name != null) {
/*     */                 
/* 449 */                 name = name.trim();
/*     */ 
/*     */                 
/* 452 */                 dueDateHolidayFlg = MilestoneHelper.getDayType(sel.getCalendarGroup(), task.getDueDate());
/*     */                 
/* 454 */                 if (taskAbbrev.equalsIgnoreCase("SCHF")) {
/*     */ 
/*     */                   
/* 457 */                   schedulingFormDate = String.valueOf(MilestoneHelper.getShortDate(task.getDueDate())) + " " + dueDateHolidayFlg;
/* 458 */                   if (task.getScheduledTaskStatus() != null && 
/* 459 */                     task.getScheduledTaskStatus().equals("N/A")) {
/* 460 */                     schedulingForm = "N/A";
/*     */                   } else {
/*     */                     
/* 463 */                     schedulingForm = MilestoneHelper.getFormatedDate(task.getCompletionDate());
/*     */                   } 
/* 465 */                   schedulingForm = String.valueOf(schedulingForm) + "\n";
/* 466 */                   schedulingForm = String.valueOf(schedulingForm) + ((task.getComments() != null) ? task.getComments() : ""); continue;
/*     */                 } 
/* 468 */                 if (taskAbbrev.equalsIgnoreCase("LCD")) {
/*     */ 
/*     */                   
/* 471 */                   labelCopyDate = String.valueOf(MilestoneHelper.getShortDate(task.getDueDate())) + 
/* 472 */                     " " + dueDateHolidayFlg;
/* 473 */                   if (task.getScheduledTaskStatus() != null && 
/* 474 */                     task.getScheduledTaskStatus().equals("N/A")) {
/* 475 */                     labelCopy = "N/A";
/*     */                   } else {
/*     */                     
/* 478 */                     labelCopy = MilestoneHelper.getFormatedDate(task.getCompletionDate());
/*     */                   } 
/* 480 */                   labelCopy = String.valueOf(labelCopy) + "\n";
/* 481 */                   labelCopy = String.valueOf(labelCopy) + ((task.getComments() != null) ? task.getComments() : " "); continue;
/*     */                 } 
/* 483 */                 if (taskAbbrev.equalsIgnoreCase("CTAD")) {
/*     */ 
/*     */                   
/* 486 */                   copyToArtDate = String.valueOf(MilestoneHelper.getShortDate(task.getDueDate())) + 
/* 487 */                     " " + dueDateHolidayFlg;
/* 488 */                   if (task.getScheduledTaskStatus() != null && 
/* 489 */                     task.getScheduledTaskStatus().equals("N/A")) {
/* 490 */                     copyToArt = "N/A";
/*     */                   } else {
/*     */                     
/* 493 */                     copyToArt = MilestoneHelper.getFormatedDate(task.getCompletionDate());
/*     */                   } 
/* 495 */                   copyToArt = String.valueOf(copyToArt) + "\n";
/* 496 */                   copyToArt = String.valueOf(copyToArt) + ((task.getComments() != null) ? task.getComments() : " "); continue;
/*     */                 } 
/* 498 */                 if (taskAbbrev.equalsIgnoreCase("TPS")) {
/*     */ 
/*     */                   
/* 501 */                   audioToUMSDate = String.valueOf(MilestoneHelper.getShortDate(task.getDueDate())) + 
/* 502 */                     " " + dueDateHolidayFlg;
/* 503 */                   if (task.getScheduledTaskStatus() != null && 
/* 504 */                     task.getScheduledTaskStatus().equals("N/A")) {
/*     */                     
/* 506 */                     audioToUMS = "N/A";
/*     */                   } else {
/*     */                     
/* 509 */                     audioToUMS = MilestoneHelper.getFormatedDate(task.getCompletionDate());
/*     */                   } 
/* 511 */                   audioToUMS = String.valueOf(audioToUMS) + "\n";
/* 512 */                   audioToUMS = String.valueOf(audioToUMS) + ((task.getComments() != null) ? task.getComments() : " "); continue;
/*     */                 } 
/* 514 */                 if (taskAbbrev.equalsIgnoreCase("ARTP")) {
/*     */ 
/*     */                   
/* 517 */                   artToPrinterDate = String.valueOf(MilestoneHelper.getShortDate(task.getDueDate())) + 
/* 518 */                     " " + dueDateHolidayFlg;
/* 519 */                   if (task.getScheduledTaskStatus() != null && 
/* 520 */                     task.getScheduledTaskStatus().equals("N/A")) {
/*     */                     
/* 522 */                     artToPrinter = "N/A";
/*     */                   } else {
/*     */                     
/* 525 */                     artToPrinter = MilestoneHelper.getFormatedDate(task.getCompletionDate());
/*     */                   } 
/* 527 */                   artToPrinter = String.valueOf(artToPrinter) + "\n";
/* 528 */                   artToPrinter = String.valueOf(artToPrinter) + ((task.getComments() != null) ? task.getComments() : " "); continue;
/*     */                 } 
/* 530 */                 if (taskAbbrev.equalsIgnoreCase("CPS")) {
/*     */ 
/*     */                   
/* 533 */                   productionStatusDate = String.valueOf(MilestoneHelper.getShortDate(task.getDueDate())) + 
/* 534 */                     " " + dueDateHolidayFlg;
/* 535 */                   if (task.getScheduledTaskStatus() != null && 
/* 536 */                     task.getScheduledTaskStatus().equals("N/A")) {
/*     */                     
/* 538 */                     productionStatus = "N/A";
/*     */                   }
/* 540 */                   else if (task.getScheduledTaskStatus() != null) {
/* 541 */                     productionStatus = task.getScheduledTaskStatus();
/*     */                   }
/*     */                   else {
/*     */                     
/* 545 */                     productionStatus = "";
/*     */                   } 
/* 547 */                   productionStatus = String.valueOf(productionStatus) + "\n";
/* 548 */                   productionStatus = String.valueOf(productionStatus) + ((task.getComments() != null) ? task.getComments() : " "); continue;
/*     */                 } 
/* 550 */                 if (taskAbbrev.equalsIgnoreCase("BILL"))
/*     */                 {
/*     */                   
/* 553 */                   bomDate = String.valueOf(MilestoneHelper.getShortDate(task.getDueDate())) + 
/* 554 */                     " " + dueDateHolidayFlg;
/* 555 */                   if (task.getScheduledTaskStatus() != null && 
/* 556 */                     task.getScheduledTaskStatus().equals("N/A")) {
/*     */                     
/* 558 */                     bom = "N/A";
/*     */                   } else {
/*     */                     
/* 561 */                     bom = MilestoneHelper.getFormatedDate(task.getCompletionDate());
/*     */                   } 
/* 563 */                   bom = String.valueOf(bom) + "\n";
/* 564 */                   bom = String.valueOf(bom) + ((task.getComments() != null) ? task.getComments() : " ");
/*     */                 }
/*     */               
/*     */               } 
/* 568 */             } catch (Exception e) {
/*     */               
/* 570 */               e.printStackTrace();
/*     */             } 
/*     */           } 
/*     */         } 
/*     */         
/* 575 */         nextRow = 0;
/* 576 */         subTable = new DefaultTableLens(2, 14);
/*     */         
/* 578 */         subTable.setColWidth(0, 95);
/* 579 */         subTable.setColWidth(1, 225);
/* 580 */         subTable.setColWidth(2, 140);
/* 581 */         subTable.setColWidth(3, 120);
/* 582 */         subTable.setColWidth(4, 80);
/* 583 */         subTable.setColWidth(5, 70);
/* 584 */         subTable.setColWidth(6, 70);
/* 585 */         subTable.setColWidth(7, 70);
/* 586 */         subTable.setColWidth(8, 70);
/* 587 */         subTable.setColWidth(9, 70);
/* 588 */         subTable.setColWidth(10, 70);
/* 589 */         subTable.setColWidth(11, 70);
/* 590 */         subTable.setColWidth(12, 90);
/* 591 */         subTable.setColWidth(13, 180);
/*     */ 
/*     */ 
/*     */         
/* 595 */         subTable.setColBorderColor(nextRow, Color.lightGray);
/* 596 */         subTable.setRowBorderColor(nextRow, Color.lightGray);
/* 597 */         subTable.setRowBorder(4097);
/* 598 */         subTable.setColBorder(4097);
/*     */ 
/*     */ 
/*     */         
/* 602 */         subTable.setRowInsets(nextRow, new Insets(0, 0, 0, 0));
/*     */         
/* 604 */         subTable.setRowAlignment(nextRow, 2);
/*     */ 
/*     */         
/* 607 */         subTable.setSpan(nextRow, 0, new Dimension(1, 2));
/* 608 */         subTable.setObject(nextRow, 0, USIntRelease);
/*     */         
/* 610 */         subTable.setObject(nextRow, 1, "");
/*     */         
/* 612 */         subTable.setObject(nextRow, 2, "");
/* 613 */         subTable.setObject(nextRow, 3, "");
/* 614 */         subTable.setObject(nextRow, 4, schedulingFormDate);
/* 615 */         subTable.setObject(nextRow, 5, labelCopyDate);
/* 616 */         subTable.setObject(nextRow, 6, dueDateTaks);
/* 617 */         subTable.setObject(nextRow, 7, audioToUMSDate);
/* 618 */         subTable.setObject(nextRow, 8, artToPrinterDate);
/* 619 */         subTable.setObject(nextRow, 9, bomDate);
/* 620 */         subTable.setObject(nextRow, 10, "");
/* 621 */         subTable.setObject(nextRow, 11, "");
/* 622 */         subTable.setObject(nextRow, 12, "");
/* 623 */         subTable.setObject(nextRow, 13, sel.getSelectionComments());
/*     */ 
/*     */         
/* 626 */         subTable.setSpan(nextRow, 13, new Dimension(1, 2));
/*     */         
/* 628 */         subTable.setRowFont(nextRow, new Font("Arial", 1, 7));
/*     */ 
/*     */ 
/*     */         
/* 632 */         Font holidayFont = new Font("Arial", 3, 7);
/* 633 */         for (int colIdx = 4; colIdx <= 10; colIdx++) {
/*     */           
/* 635 */           String dueDate = subTable.getObject(nextRow, colIdx).toString();
/* 636 */           if (dueDate != null && dueDate.length() > 0) {
/*     */             
/* 638 */             char lastChar = dueDate.charAt(dueDate.length() - 1);
/* 639 */             if (Character.isLetter(lastChar)) {
/* 640 */               subTable.setFont(nextRow, colIdx, holidayFont);
/*     */             }
/*     */           } 
/*     */         } 
/*     */         
/* 645 */         subTable.setFont(nextRow, 0, new Font("Arial", 0, 7));
/* 646 */         subTable.setFont(nextRow, 1, new Font("Arial", 0, 7));
/*     */         
/* 648 */         subTable.setAlignment(nextRow, 0, 10);
/*     */         
/* 650 */         subTable.setRowHeight(nextRow, 10);
/* 651 */         subTable.setColFont(0, new Font("Arial", 0, 8));
/*     */         
/* 653 */         subTable.setRowBackground(nextRow, Color.white);
/* 654 */         subTable.setBackground(nextRow, 0, Color.white);
/* 655 */         subTable.setBackground(nextRow, 1, Color.lightGray);
/* 656 */         subTable.setBackground(nextRow, 2, Color.lightGray);
/* 657 */         subTable.setBackground(nextRow, 3, Color.lightGray);
/* 658 */         subTable.setBackground(nextRow, 4, Color.lightGray);
/* 659 */         subTable.setBackground(nextRow, 5, Color.lightGray);
/* 660 */         subTable.setBackground(nextRow, 6, Color.lightGray);
/* 661 */         subTable.setBackground(nextRow, 7, Color.lightGray);
/* 662 */         subTable.setBackground(nextRow, 8, Color.lightGray);
/* 663 */         subTable.setBackground(nextRow, 9, Color.lightGray);
/* 664 */         subTable.setBackground(nextRow, 10, Color.lightGray);
/* 665 */         subTable.setBackground(nextRow, 11, Color.lightGray);
/* 666 */         subTable.setBackground(nextRow, 12, Color.lightGray);
/* 667 */         subTable.setBackground(nextRow, 13, Color.white);
/*     */         
/* 669 */         subTable.setColBorderColor(nextRow, -1, Color.lightGray);
/* 670 */         subTable.setColBorderColor(nextRow, 0, Color.lightGray);
/* 671 */         subTable.setColBorderColor(nextRow, 1, Color.lightGray);
/* 672 */         subTable.setColBorderColor(nextRow, 2, Color.lightGray);
/* 673 */         subTable.setColBorderColor(nextRow, 3, Color.lightGray);
/* 674 */         subTable.setColBorderColor(nextRow, 4, Color.lightGray);
/* 675 */         subTable.setColBorderColor(nextRow, 5, Color.lightGray);
/* 676 */         subTable.setColBorderColor(nextRow, 6, Color.lightGray);
/* 677 */         subTable.setColBorderColor(nextRow, 7, Color.lightGray);
/* 678 */         subTable.setColBorderColor(nextRow, 8, Color.lightGray);
/* 679 */         subTable.setColBorderColor(nextRow, 9, Color.lightGray);
/* 680 */         subTable.setColBorderColor(nextRow, 10, Color.lightGray);
/* 681 */         subTable.setColBorderColor(nextRow, 11, Color.lightGray);
/* 682 */         subTable.setColBorderColor(nextRow, 12, Color.lightGray);
/* 683 */         subTable.setColBorderColor(nextRow, 13, Color.lightGray);
/*     */         
/* 685 */         subTable.setRowBorderColor(nextRow, Color.lightGray);
/*     */         
/* 687 */         subTable.setRowBorderColor(nextRow - 1, Color.lightGray);
/* 688 */         subTable.setRowBorderColor(nextRow, 1, Color.white);
/*     */         
/* 690 */         subTable.setColBorder(nextRow, 1, 266240);
/* 691 */         subTable.setColBorder(nextRow, 2, 266240);
/* 692 */         subTable.setColBorder(nextRow, 3, 266240);
/* 693 */         subTable.setColBorder(nextRow, 4, 266240);
/* 694 */         subTable.setColBorder(nextRow, 5, 266240);
/* 695 */         subTable.setColBorder(nextRow, 6, 266240);
/* 696 */         subTable.setColBorder(nextRow, 7, 266240);
/* 697 */         subTable.setColBorder(nextRow, 8, 266240);
/* 698 */         subTable.setColBorder(nextRow, 9, 266240);
/* 699 */         subTable.setColBorder(nextRow, 10, 266240);
/* 700 */         subTable.setColBorder(nextRow, 11, 266240);
/*     */ 
/*     */         
/* 703 */         subTable.setAlignment(nextRow, 0, 10);
/* 704 */         subTable.setAlignment(nextRow, 1, 10);
/* 705 */         subTable.setAlignment(nextRow, 2, 10);
/* 706 */         subTable.setAlignment(nextRow, 3, 10);
/* 707 */         subTable.setAlignment(nextRow, 4, 10);
/* 708 */         subTable.setAlignment(nextRow, 5, 10);
/* 709 */         subTable.setAlignment(nextRow, 6, 10);
/* 710 */         subTable.setAlignment(nextRow, 7, 10);
/* 711 */         subTable.setAlignment(nextRow, 8, 10);
/* 712 */         subTable.setAlignment(nextRow, 9, 10);
/* 713 */         subTable.setAlignment(nextRow, 10, 10);
/* 714 */         subTable.setAlignment(nextRow, 11, 10);
/* 715 */         subTable.setAlignment(nextRow, 12, 10);
/* 716 */         subTable.setAlignment(nextRow, 13, 9);
/*     */ 
/*     */         
/* 719 */         nextRow++;
/*     */         
/* 721 */         subTable.setRowBorderColor(nextRow, Color.lightGray);
/*     */         
/* 723 */         subTable.setColBorderColor(nextRow, -1, Color.lightGray);
/* 724 */         subTable.setColBorderColor(nextRow, 0, Color.lightGray);
/* 725 */         subTable.setColBorderColor(nextRow, 1, Color.lightGray);
/* 726 */         subTable.setColBorderColor(nextRow, 2, Color.lightGray);
/* 727 */         subTable.setColBorderColor(nextRow, 3, Color.lightGray);
/* 728 */         subTable.setColBorderColor(nextRow, 4, Color.lightGray);
/* 729 */         subTable.setColBorderColor(nextRow, 5, Color.lightGray);
/* 730 */         subTable.setColBorderColor(nextRow, 6, Color.lightGray);
/* 731 */         subTable.setColBorderColor(nextRow, 7, Color.lightGray);
/* 732 */         subTable.setColBorderColor(nextRow, 8, Color.lightGray);
/* 733 */         subTable.setColBorderColor(nextRow, 9, Color.lightGray);
/* 734 */         subTable.setColBorderColor(nextRow, 10, Color.lightGray);
/* 735 */         subTable.setColBorderColor(nextRow, 11, Color.lightGray);
/* 736 */         subTable.setColBorderColor(nextRow, 12, Color.lightGray);
/* 737 */         subTable.setColBorderColor(nextRow, 13, Color.lightGray);
/*     */ 
/*     */         
/* 740 */         subTable.setRowInsets(nextRow, new Insets(0, 0, 0, 0));
/*     */         
/* 742 */         subTable.setRowFont(nextRow, new Font("Arial", 0, 7));
/* 743 */         subTable.setRowAlignment(nextRow, 10);
/*     */ 
/*     */         
/* 746 */         subTable.setObject(nextRow, 0, "");
/* 747 */         subTable.setObject(nextRow, 1, String.valueOf(upc) + "\n" + localProductNumber + "\n" + artistName + "\n" + title);
/* 748 */         subTable.setObject(nextRow, 2, labelAndPackage);
/* 749 */         subTable.setObject(nextRow, 3, selFormat);
/* 750 */         subTable.setObject(nextRow, 4, String.valueOf(code) + " " + retail + " " + price);
/* 751 */         subTable.setObject(nextRow, 5, labelCopy);
/* 752 */         subTable.setObject(nextRow, 6, completationDateTaks);
/* 753 */         subTable.setObject(nextRow, 7, audioToUMS);
/* 754 */         subTable.setObject(nextRow, 8, artToPrinter);
/* 755 */         subTable.setObject(nextRow, 9, bom);
/* 756 */         subTable.setObject(nextRow, 11, qtyOrders);
/* 757 */         subTable.setObject(nextRow, 10, (poQty == 0) ? "" : fS);
/* 758 */         subTable.setObject(nextRow, 12, productionStatus);
/* 759 */         subTable.setObject(nextRow, 13, sel.getSelectionComments());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 765 */         body = new SectionBand(report);
/* 766 */         body.addTable(subTable, new Rectangle(800, 800));
/*     */         
/* 768 */         double lfLineCount = 1.5D;
/*     */         
/* 770 */         if (commentLines > 0 || labelAndPackage.length() > 17 || sel.getTitle().length() > 20) {
/*     */           
/* 772 */           body.setHeight(1.5F);
/*     */         }
/*     */         else {
/*     */           
/* 776 */           body.setHeight(1.0F);
/*     */         } 
/*     */         
/* 779 */         if (commentLines > 0 || labelAndPackage.length() > 17 || sel.getTitle().length() > 20 || 
/* 780 */           schedulingForm.length() > 25 || labelCopy.length() > 25 || artToPrinter.length() > 25 || 
/* 781 */           audioToUMS.length() > 25 || productionStatus.length() > 25 || 
/* 782 */           bom.length() > 25) {
/*     */           
/* 784 */           if (lfLineCount < commentLines * 0.3D) {
/* 785 */             lfLineCount = commentLines * 0.3D;
/*     */           }
/* 787 */           if (lfLineCount < (labelAndPackage.length() / 8) * 0.3D) {
/* 788 */             lfLineCount = (labelAndPackage.length() / 8) * 0.3D;
/*     */           }
/* 790 */           if (lfLineCount < (sel.getTitle().length() / 8) * 0.3D) {
/* 791 */             lfLineCount = (sel.getTitle().length() / 8) * 0.3D;
/*     */           }
/* 793 */           if (lfLineCount < (schedulingForm.length() / 7) * 0.3D) {
/* 794 */             lfLineCount = (schedulingForm.length() / 7) * 0.3D;
/*     */           }
/* 796 */           if (lfLineCount < (labelCopy.length() / 7) * 0.3D) {
/* 797 */             lfLineCount = (labelCopy.length() / 7) * 0.3D;
/*     */           }
/* 799 */           if (lfLineCount < (artToPrinter.length() / 7) * 0.3D) {
/* 800 */             lfLineCount = (artToPrinter.length() / 7) * 0.3D;
/*     */           }
/* 802 */           if (lfLineCount < (audioToUMS.length() / 7) * 0.3D) {
/* 803 */             lfLineCount = (audioToUMS.length() / 7) * 0.3D;
/*     */           }
/* 805 */           if (lfLineCount < (productionStatus.length() / 7) * 0.3D) {
/* 806 */             lfLineCount = (productionStatus.length() / 7) * 0.3D;
/*     */           }
/* 808 */           if (lfLineCount < (bom.length() / 7) * 0.3D) {
/* 809 */             lfLineCount = (bom.length() / 7) * 0.3D;
/*     */           }
/* 811 */           body.setHeight((float)lfLineCount);
/*     */         }
/* 813 */         else if (commentLines > 0 || labelAndPackage.length() > 17 || sel.getTitle().length() > 20 || 
/* 814 */           schedulingForm.length() > 5 || labelCopy.length() > 5 || artToPrinter.length() > 5 || 
/* 815 */           audioToUMS.length() > 5 || productionStatus.length() > 5 || 
/* 816 */           bom.length() > 5) {
/*     */ 
/*     */ 
/*     */           
/* 820 */           if (lfLineCount < commentLines * 0.3D) {
/* 821 */             lfLineCount = commentLines * 0.3D;
/*     */           }
/* 823 */           if (lfLineCount < (labelAndPackage.length() / 5) * 0.3D) {
/* 824 */             lfLineCount = (labelAndPackage.length() / 5) * 0.3D;
/*     */           }
/* 826 */           if (lfLineCount < (sel.getTitle().length() / 5) * 0.3D) {
/* 827 */             lfLineCount = (sel.getTitle().length() / 5) * 0.3D;
/*     */           }
/* 829 */           if (lfLineCount < (schedulingForm.length() / 5) * 0.3D) {
/* 830 */             lfLineCount = (schedulingForm.length() / 5) * 0.3D;
/*     */           }
/* 832 */           if (lfLineCount < (labelCopy.length() / 5) * 0.3D) {
/* 833 */             lfLineCount = (labelCopy.length() / 5) * 0.3D;
/*     */           }
/* 835 */           if (lfLineCount < (artToPrinter.length() / 5) * 0.3D) {
/* 836 */             lfLineCount = (artToPrinter.length() / 5) * 0.3D;
/*     */           }
/* 838 */           if (lfLineCount < (audioToUMS.length() / 5) * 0.3D) {
/* 839 */             lfLineCount = (audioToUMS.length() / 5) * 0.3D;
/*     */           }
/* 841 */           if (lfLineCount < (productionStatus.length() / 5) * 0.3D) {
/* 842 */             lfLineCount = (productionStatus.length() / 5) * 0.3D;
/*     */           }
/* 844 */           if (lfLineCount < (bom.length() / 5) * 0.3D) {
/* 845 */             lfLineCount = (bom.length() / 5) * 0.3D;
/*     */           }
/* 847 */           body.setHeight((float)lfLineCount);
/*     */         
/*     */         }
/*     */         else {
/*     */           
/* 852 */           body.setHeight(1.0F);
/*     */         } 
/*     */         
/* 855 */         body.setBottomBorder(0);
/* 856 */         body.setTopBorder(0);
/* 857 */         body.setShrinkToFit(true);
/* 858 */         body.setVisible(true);
/*     */         
/* 860 */         group = new DefaultSectionLens(null, group, body);
/*     */       } 
/*     */       
/* 863 */       group = new DefaultSectionLens(hbandType, group, footer);
/* 864 */       report.addSection(group, rowCountTable);
/* 865 */       group = null;
/*     */ 
/*     */ 
/*     */     
/*     */     }
/* 870 */     catch (Exception e) {
/*     */       
/* 872 */       System.out.println(">>>>>>>>ReportHandler.fillCarolineProductionUpdateForPrint(): exception: " + e);
/* 873 */       e.printStackTrace();
/*     */     } 
/*     */ 
/*     */     
/* 877 */     statusJSPupdate = null;
/*     */   }
/*     */ }


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\CarolineProductionSchedulePrintSubHandler.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */