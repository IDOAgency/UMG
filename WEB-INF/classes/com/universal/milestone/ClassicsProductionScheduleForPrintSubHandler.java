/*     */ package WEB-INF.classes.com.universal.milestone;
/*     */ 
/*     */ import com.techempower.ComponentLog;
/*     */ import com.techempower.gemini.Context;
/*     */ import com.techempower.gemini.GeminiApplication;
/*     */ import com.universal.milestone.ClassicsProductionScheduleForPrintSubHandler;
/*     */ import com.universal.milestone.Form;
/*     */ import com.universal.milestone.MilestoneHelper;
/*     */ import com.universal.milestone.MilestoneHelper_2;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ClassicsProductionScheduleForPrintSubHandler
/*     */   extends SecureHandler
/*     */ {
/*     */   public static final String COMPONENT_CODE = "hCProd";
/*     */   public GeminiApplication application;
/*     */   public ComponentLog log;
/*     */   
/*     */   public ClassicsProductionScheduleForPrintSubHandler(GeminiApplication application) {
/*  73 */     this.application = application;
/*  74 */     this.log = application.getLog("hCProd");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  82 */   public String getDescription() { return "Sub Report"; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static void fillClassicProductionUpdateForPrint(XStyleSheet report, Context context) {
/*  93 */     int COL_LINE_STYLE = 4097;
/*  94 */     int HEADER_FONT_SIZE = 12;
/*     */     
/*  96 */     double ldLineVal = 0.3D;
/*  97 */     ComponentLog log = context.getApplication().getLog("hCProd");
/*     */ 
/*     */ 
/*     */     
/* 101 */     StatusJSPupdate statusJSPupdate = new StatusJSPupdate(context);
/*     */ 
/*     */     
/* 104 */     statusJSPupdate.updateStatus(0, 0, "start_gathering", 0);
/*     */ 
/*     */     
/* 107 */     Vector selections = MilestoneHelper.getSelectionsForReport(context);
/*     */ 
/*     */     
/* 110 */     statusJSPupdate.updateStatus(0, 0, "start_report", 0);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 118 */       Form reportForm = (Form)context.getSessionValue("reportForm");
/*     */       
/* 120 */       Calendar beginStDate = (reportForm.getStringValue("beginDate") != null && 
/* 121 */         reportForm.getStringValue("beginDate").length() > 0) ? 
/* 122 */         MilestoneHelper.getDate(reportForm.getStringValue("beginDate")) : null;
/*     */       
/* 124 */       Calendar endStDate = (reportForm.getStringValue("endDate") != null && 
/* 125 */         reportForm.getStringValue("endDate").length() > 0) ? 
/* 126 */         MilestoneHelper.getDate(reportForm.getStringValue("endDate")) : null;
/*     */       
/* 128 */       report.setElement("crs_startdate", MilestoneHelper.getFormatedDate(beginStDate));
/* 129 */       report.setElement("crs_enddate", MilestoneHelper.getFormatedDate(endStDate));
/*     */       
/* 131 */       SimpleDateFormat formatter = new SimpleDateFormat("MMMM d, yyyy");
/* 132 */       String todayLong = formatter.format(new Date());
/* 133 */       report.setElement("crs_bottomdate", todayLong);
/*     */       
/* 135 */       SectionBand hbandType = new SectionBand(report);
/* 136 */       SectionBand hbandCategory = new SectionBand(report);
/* 137 */       SectionBand body = new SectionBand(report);
/* 138 */       SectionBand footer = new SectionBand(report);
/* 139 */       DefaultSectionLens group = null;
/*     */       
/* 141 */       DefaultTableLens table_contents = null;
/* 142 */       DefaultTableLens rowCountTable = null;
/* 143 */       DefaultTableLens columnHeaderTable = null;
/*     */ 
/*     */       
/* 146 */       DefaultTableLens subTable = null;
/*     */ 
/*     */       
/* 149 */       int numSelections = selections.size() * 2;
/* 150 */       int numRows = numSelections + 5;
/*     */ 
/*     */       
/* 153 */       rowCountTable = new DefaultTableLens(2, 10000);
/* 154 */       table_contents = new DefaultTableLens(1, 15);
/*     */ 
/*     */       
/* 157 */       int nextRow = 0;
/* 158 */       hbandType = new SectionBand(report);
/* 159 */       hbandType.setHeight(0.95F);
/* 160 */       hbandType.setShrinkToFit(true);
/* 161 */       hbandType.setVisible(true);
/*     */       
/* 163 */       hbandCategory = new SectionBand(report);
/* 164 */       hbandCategory.setHeight(2.0F);
/* 165 */       hbandCategory.setShrinkToFit(true);
/* 166 */       hbandCategory.setVisible(true);
/* 167 */       hbandCategory.setBottomBorder(0);
/* 168 */       hbandCategory.setLeftBorder(0);
/* 169 */       hbandCategory.setRightBorder(0);
/* 170 */       hbandCategory.setTopBorder(0);
/*     */ 
/*     */       
/* 173 */       table_contents.setHeaderRowCount(0);
/*     */ 
/*     */ 
/*     */       
/* 177 */       table_contents.setColWidth(0, 95);
/* 178 */       table_contents.setColWidth(1, 225);
/* 179 */       table_contents.setColWidth(2, 140);
/* 180 */       table_contents.setColWidth(3, 80);
/* 181 */       table_contents.setColWidth(4, 70);
/* 182 */       table_contents.setColWidth(5, 70);
/* 183 */       table_contents.setColWidth(6, 70);
/* 184 */       table_contents.setColWidth(7, 70);
/* 185 */       table_contents.setColWidth(8, 70);
/* 186 */       table_contents.setColWidth(9, 70);
/* 187 */       table_contents.setColWidth(10, 70);
/* 188 */       table_contents.setColWidth(11, 70);
/* 189 */       table_contents.setColWidth(12, 70);
/* 190 */       table_contents.setColWidth(13, 70);
/* 191 */       table_contents.setColWidth(14, 180);
/*     */       
/* 193 */       table_contents.setRowBorderColor(nextRow, Color.lightGray);
/* 194 */       table_contents.setRowBorder(4097);
/* 195 */       table_contents.setColBorder(4097);
/*     */ 
/*     */       
/* 198 */       columnHeaderTable = new DefaultTableLens(1, 15);
/*     */       
/* 200 */       nextRow = 0;
/*     */       
/* 202 */       columnHeaderTable.setColWidth(0, 95);
/* 203 */       columnHeaderTable.setColWidth(1, 225);
/* 204 */       columnHeaderTable.setColWidth(2, 140);
/* 205 */       columnHeaderTable.setColWidth(3, 80);
/* 206 */       columnHeaderTable.setColWidth(4, 70);
/* 207 */       columnHeaderTable.setColWidth(5, 70);
/* 208 */       columnHeaderTable.setColWidth(6, 70);
/* 209 */       columnHeaderTable.setColWidth(7, 70);
/* 210 */       columnHeaderTable.setColWidth(8, 70);
/* 211 */       columnHeaderTable.setColWidth(9, 70);
/* 212 */       columnHeaderTable.setColWidth(10, 70);
/* 213 */       columnHeaderTable.setColWidth(11, 70);
/* 214 */       columnHeaderTable.setColWidth(12, 70);
/* 215 */       columnHeaderTable.setColWidth(13, 70);
/* 216 */       columnHeaderTable.setColWidth(14, 180);
/*     */ 
/*     */       
/* 219 */       columnHeaderTable.setRowAlignment(nextRow, 34);
/* 220 */       columnHeaderTable.setObject(nextRow, 0, "Release\nDate");
/* 221 */       columnHeaderTable.setObject(nextRow, 1, "UPC\nLocal Prod #\nArtist\nTitle");
/* 222 */       columnHeaderTable.setObject(nextRow, 2, "Label &\nPackage Info");
/* 223 */       columnHeaderTable.setObject(nextRow, 3, "Cover\nApproved");
/* 224 */       columnHeaderTable.setObject(nextRow, 4, "Package\nCopy");
/* 225 */       columnHeaderTable.setObject(nextRow, 5, "Sticker\nCopy");
/* 226 */       columnHeaderTable.setObject(nextRow, 6, "DJ\nQty");
/* 227 */       columnHeaderTable.setObject(nextRow, 7, "F/M");
/* 228 */       columnHeaderTable.setObject(nextRow, 8, "Manf.\nImport\nQty");
/* 229 */       columnHeaderTable.setObject(nextRow, 9, "Seps");
/* 230 */       columnHeaderTable.setObject(nextRow, 10, "BOM");
/* 231 */       columnHeaderTable.setObject(nextRow, 11, "Masters\nat Plant");
/* 232 */       columnHeaderTable.setObject(nextRow, 12, "Film at\nPrinter");
/* 233 */       columnHeaderTable.setObject(nextRow, 13, "Print\nat Plant");
/* 234 */       columnHeaderTable.setObject(nextRow, 14, "Comments");
/*     */       
/* 236 */       columnHeaderTable.setRowFont(nextRow, new Font("Arial", 1, 8));
/* 237 */       columnHeaderTable.setRowBorderColor(nextRow, Color.lightGray);
/* 238 */       columnHeaderTable.setRowBorderColor(nextRow - 1, Color.lightGray);
/*     */       
/* 240 */       columnHeaderTable.setColBorderColor(nextRow, -1, Color.lightGray);
/* 241 */       columnHeaderTable.setColBorderColor(nextRow, 0, Color.lightGray);
/* 242 */       columnHeaderTable.setColBorderColor(nextRow, 1, Color.lightGray);
/* 243 */       columnHeaderTable.setColBorderColor(nextRow, 2, Color.lightGray);
/* 244 */       columnHeaderTable.setColBorderColor(nextRow, 3, Color.lightGray);
/* 245 */       columnHeaderTable.setColBorderColor(nextRow, 4, Color.lightGray);
/* 246 */       columnHeaderTable.setColBorderColor(nextRow, 5, Color.lightGray);
/* 247 */       columnHeaderTable.setColBorderColor(nextRow, 6, Color.lightGray);
/* 248 */       columnHeaderTable.setColBorderColor(nextRow, 7, Color.lightGray);
/* 249 */       columnHeaderTable.setColBorderColor(nextRow, 8, Color.lightGray);
/* 250 */       columnHeaderTable.setColBorderColor(nextRow, 9, Color.lightGray);
/* 251 */       columnHeaderTable.setColBorderColor(nextRow, 10, Color.lightGray);
/* 252 */       columnHeaderTable.setColBorderColor(nextRow, 11, Color.lightGray);
/* 253 */       columnHeaderTable.setColBorderColor(nextRow, 12, Color.lightGray);
/* 254 */       columnHeaderTable.setColBorderColor(nextRow, 13, Color.lightGray);
/* 255 */       columnHeaderTable.setColBorderColor(nextRow, 14, Color.lightGray);
/*     */       
/* 257 */       columnHeaderTable.setRowBorderColor(nextRow, Color.lightGray);
/* 258 */       nextRow++;
/*     */       
/* 260 */       hbandType.addTable(columnHeaderTable, new Rectangle(0, 0, 800, 50));
/*     */       
/* 262 */       hbandType.setBottomBorder(0);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 268 */       footer.setVisible(false);
/* 269 */       footer.setHeight(0.1F);
/* 270 */       footer.setShrinkToFit(true);
/* 271 */       footer.setBottomBorder(0);
/*     */ 
/*     */ 
/*     */       
/* 275 */       MilestoneHelper.setSelectionSorting(selections, 12);
/* 276 */       Collections.sort(selections);
/*     */ 
/*     */       
/* 279 */       MilestoneHelper.setSelectionSorting(selections, 4);
/* 280 */       Collections.sort(selections);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 287 */       MilestoneHelper.setSelectionSorting(selections, 22);
/* 288 */       Collections.sort(selections);
/*     */ 
/*     */       
/* 291 */       MilestoneHelper.setSelectionSorting(selections, 9);
/* 292 */       Collections.sort(selections);
/*     */ 
/*     */       
/* 295 */       MilestoneHelper.setSelectionSorting(selections, 1);
/* 296 */       Collections.sort(selections);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 303 */       int commentLines = 0;
/*     */ 
/*     */       
/* 306 */       for (int j = 0; j < selections.size(); j++) {
/*     */ 
/*     */ 
/*     */         
/* 310 */         statusJSPupdate.updateStatus(selections.size(), j, "start_report", 0);
/*     */         
/* 312 */         Selection sel = (Selection)selections.elementAt(j);
/* 313 */         sel = SelectionManager.getInstance().getSelectionAndSchedule(sel.getSelectionID());
/*     */ 
/*     */         
/* 316 */         String USIntRelease = "";
/* 317 */         if (SelectionManager.getLookupObjectValue(sel.getSelectionStatus()).equalsIgnoreCase("TBS")) {
/* 318 */           USIntRelease = "TBS ";
/* 319 */         } else if (SelectionManager.getLookupObjectValue(sel.getSelectionStatus()).equalsIgnoreCase("ITW")) {
/* 320 */           USIntRelease = "ITW ";
/*     */         } 
/* 322 */         if (USIntRelease != null && !USIntRelease.equals("")) {
/*     */           
/* 324 */           USIntRelease = String.valueOf(USIntRelease) + MilestoneHelper.getFormatedDate(sel.getStreetDate());
/*     */         }
/*     */         else {
/*     */           
/* 328 */           USIntRelease = String.valueOf(USIntRelease) + MilestoneHelper.getFormatedDate(sel.getStreetDate()) + "\n" + MilestoneHelper.getFormatedDate(sel.getInternationalDate());
/*     */         } 
/*     */ 
/*     */ 
/*     */         
/* 333 */         String upc = (sel.getUpc() != null) ? sel.getUpc() : " ";
/*     */ 
/*     */         
/* 336 */         upc = MilestoneHelper_2.getRMSReportFormat(upc, "UPC", sel.getIsDigital());
/*     */ 
/*     */         
/* 339 */         String localProductNumber = "";
/* 340 */         if (sel.getPrefixID() != null && sel.getPrefixID().getAbbreviation() != null)
/* 341 */           localProductNumber = sel.getPrefixID().getAbbreviation(); 
/* 342 */         localProductNumber = String.valueOf(localProductNumber) + sel.getSelectionNo();
/* 343 */         String artistName = (sel.getFlArtist() != null) ? sel.getFlArtist() : " ";
/* 344 */         String title = (sel.getTitle() != null) ? sel.getTitle() : " ";
/*     */ 
/*     */ 
/*     */         
/* 348 */         String comment = (sel.getSelectionComments() != null) ? sel.getSelectionComments() : " ";
/*     */ 
/*     */         
/* 351 */         String label = (sel.getImprint() != null) ? sel.getImprint() : " ";
/* 352 */         String packaging = (sel.getSelectionPackaging() != null) ? sel.getSelectionPackaging() : " ";
/*     */         
/* 354 */         String code = (sel.getSellCode() != null) ? sel.getSellCode() : "";
/* 355 */         if (code != null && code.startsWith("-1")) {
/* 356 */           code = "";
/*     */         }
/* 358 */         String retail = "";
/* 359 */         if (sel.getPriceCode() != null && sel.getPriceCode().getRetailCode() != null) {
/* 360 */           retail = sel.getPriceCode().getRetailCode();
/*     */         }
/* 362 */         if (code.length() > 0) {
/* 363 */           retail = "    " + retail;
/*     */         }
/* 365 */         String price = "";
/* 366 */         if (sel.getPriceCode() != null && sel.getPriceCode().getTotalCost() > 0.0F) {
/* 367 */           price = "     $" + MilestoneHelper.formatDollarPrice(sel.getPriceCode().getTotalCost());
/*     */         }
/*     */         
/* 370 */         commentLines = MilestoneHelper.lineCount(comment, "");
/*     */         
/* 372 */         String labelAndPackage = String.valueOf(label) + "\n" + packaging;
/*     */         
/* 374 */         for (int y = 0; y < commentLines; y++)
/*     */         {
/* 376 */           labelAndPackage = String.valueOf(labelAndPackage) + "\n";
/*     */         }
/*     */         
/* 379 */         Schedule schedule = sel.getSchedule();
/*     */ 
/*     */         
/* 382 */         String coverApprovedDate = "", packageCopyDate = "", stickerCopyDate = "";
/* 383 */         String djQtyDate = "", fmDate = "", manuImportDate = "", sepsDate = "";
/* 384 */         String bomDate = "", masterAtPlantDate = "", filmAtPrinterDate = "";
/* 385 */         String printAtPlantDate = "", packageFilmDate = "";
/*     */ 
/*     */         
/* 388 */         String dueDateHolidayFlg = "";
/*     */ 
/*     */         
/* 391 */         String coverApproved = "", packageCopy = "", stickerCopy = "";
/* 392 */         String djQty = "", fm = "", manuImportQty = "", seps = "";
/* 393 */         String bom = "", masterAtPlant = "", filmAtPrinter = "";
/* 394 */         String printAtPlant = "", packageFilm = "";
/*     */         
/* 396 */         Vector tasks = null;
/* 397 */         if (schedule != null)
/*     */         {
/* 399 */           tasks = schedule.getTasks();
/*     */         }
/*     */         
/* 402 */         if (tasks != null) {
/*     */           
/* 404 */           Iterator it = tasks.iterator();
/*     */           
/* 406 */           while (it != null && it.hasNext()) {
/*     */             
/* 408 */             ScheduledTask task = (ScheduledTask)it.next();
/* 409 */             String taskAbbrev = MilestoneHelper.getTaskAbbreivationNameById(task.getTaskAbbreviationID());
/* 410 */             String name = task.getName();
/*     */             
/*     */             try {
/* 413 */               if (name != null) {
/*     */                 
/* 415 */                 name = name.trim();
/*     */ 
/*     */                 
/* 418 */                 dueDateHolidayFlg = MilestoneHelper.getDayType(sel.getCalendarGroup(), task.getDueDate());
/*     */                 
/* 420 */                 if (taskAbbrev.equalsIgnoreCase("CAA")) {
/*     */ 
/*     */                   
/* 423 */                   coverApprovedDate = String.valueOf(MilestoneHelper.getShortDate(task.getDueDate())) + 
/* 424 */                     " " + dueDateHolidayFlg;
/* 425 */                   if (task.getScheduledTaskStatus() != null && 
/* 426 */                     task.getScheduledTaskStatus().equals("N/A")) {
/*     */                     
/* 428 */                     coverApproved = "N/A";
/*     */                   }
/*     */                   else {
/*     */                     
/* 432 */                     coverApproved = MilestoneHelper.getFormatedDate(task.getCompletionDate());
/*     */                   } 
/* 434 */                   coverApproved = String.valueOf(coverApproved) + "\n";
/* 435 */                   coverApproved = String.valueOf(coverApproved) + ((task.getComments() != null) ? task.getComments() : ""); continue;
/*     */                 } 
/* 437 */                 if (taskAbbrev.equalsIgnoreCase("FPC")) {
/*     */ 
/*     */                   
/* 440 */                   packageCopyDate = String.valueOf(MilestoneHelper.getShortDate(task.getDueDate())) + 
/* 441 */                     " " + dueDateHolidayFlg;
/* 442 */                   if (task.getScheduledTaskStatus() != null && 
/* 443 */                     task.getScheduledTaskStatus().equals("N/A")) {
/*     */                     
/* 445 */                     packageCopy = "N/A";
/*     */                   } else {
/*     */                     
/* 448 */                     packageCopy = MilestoneHelper.getFormatedDate(task.getCompletionDate());
/*     */                   } 
/* 450 */                   packageCopy = String.valueOf(packageCopy) + "\n";
/* 451 */                   packageCopy = String.valueOf(packageCopy) + ((task.getComments() != null) ? task.getComments() : " "); continue;
/*     */                 } 
/* 453 */                 if (taskAbbrev.equalsIgnoreCase("STD")) {
/*     */ 
/*     */                   
/* 456 */                   stickerCopyDate = String.valueOf(MilestoneHelper.getShortDate(task.getDueDate())) + 
/* 457 */                     " " + dueDateHolidayFlg;
/* 458 */                   if (task.getScheduledTaskStatus() != null && 
/* 459 */                     task.getScheduledTaskStatus().equals("N/A")) {
/*     */                     
/* 461 */                     stickerCopy = "N/A";
/*     */                   } else {
/*     */                     
/* 464 */                     stickerCopy = MilestoneHelper.getFormatedDate(task.getCompletionDate());
/*     */                   } 
/* 466 */                   stickerCopy = String.valueOf(stickerCopy) + "\n";
/* 467 */                   stickerCopy = String.valueOf(stickerCopy) + ((task.getComments() != null) ? task.getComments() : " "); continue;
/*     */                 } 
/* 469 */                 if (taskAbbrev.equalsIgnoreCase("DSD")) {
/*     */ 
/*     */                   
/* 472 */                   djQtyDate = String.valueOf(MilestoneHelper.getShortDate(task.getDueDate())) + 
/* 473 */                     " " + dueDateHolidayFlg;
/* 474 */                   if (task.getScheduledTaskStatus() != null && 
/* 475 */                     task.getScheduledTaskStatus().equals("N/A")) {
/*     */                     
/* 477 */                     djQty = "N/A";
/*     */                   } else {
/*     */                     
/* 480 */                     djQty = MilestoneHelper.getFormatedDate(task.getCompletionDate());
/*     */                   } 
/* 482 */                   djQty = String.valueOf(djQty) + "\n";
/* 483 */                   djQty = String.valueOf(djQty) + ((task.getComments() != null) ? task.getComments() : " "); continue;
/*     */                 } 
/* 485 */                 if (taskAbbrev.equalsIgnoreCase("MQD")) {
/*     */ 
/*     */                   
/* 488 */                   manuImportDate = String.valueOf(MilestoneHelper.getShortDate(task.getDueDate())) + 
/* 489 */                     " " + dueDateHolidayFlg;
/* 490 */                   if (task.getScheduledTaskStatus() != null && 
/* 491 */                     task.getScheduledTaskStatus().equals("N/A")) {
/*     */                     
/* 493 */                     manuImportQty = "N/A";
/*     */                   } else {
/*     */                     
/* 496 */                     manuImportQty = MilestoneHelper.getFormatedDate(task.getCompletionDate());
/*     */                   } 
/* 498 */                   manuImportQty = String.valueOf(manuImportQty) + "\n";
/* 499 */                   manuImportQty = String.valueOf(manuImportQty) + ((task.getComments() != null) ? task.getComments() : " ");
/*     */                   continue;
/*     */                 } 
/* 502 */                 if (taskAbbrev.equalsIgnoreCase("F/M")) {
/*     */ 
/*     */ 
/*     */                   
/* 506 */                   fmDate = String.valueOf(MilestoneHelper.getShortDate(task.getDueDate())) + 
/* 507 */                     " " + dueDateHolidayFlg;
/* 508 */                   if (task.getScheduledTaskStatus() != null && 
/* 509 */                     task.getScheduledTaskStatus().equals("N/A")) {
/*     */                     
/* 511 */                     fm = "N/A";
/*     */                   }
/*     */                   else {
/*     */                     
/* 515 */                     fm = MilestoneHelper.getFormatedDate(task.getCompletionDate());
/*     */                   } 
/* 517 */                   fm = String.valueOf(fm) + "\n";
/* 518 */                   fm = String.valueOf(fm) + ((task.getComments() != null) ? task.getComments() : " ");
/*     */                   continue;
/*     */                 } 
/* 521 */                 if (taskAbbrev.equalsIgnoreCase("PAP")) {
/*     */ 
/*     */                   
/* 524 */                   printAtPlantDate = String.valueOf(MilestoneHelper.getShortDate(task.getDueDate())) + 
/* 525 */                     " " + dueDateHolidayFlg;
/* 526 */                   if (task.getScheduledTaskStatus() != null && 
/* 527 */                     task.getScheduledTaskStatus().equals("N/A")) {
/*     */                     
/* 529 */                     printAtPlant = "N/A";
/*     */                   } else {
/*     */                     
/* 532 */                     printAtPlant = MilestoneHelper.getFormatedDate(task.getCompletionDate());
/*     */                   } 
/* 534 */                   printAtPlant = String.valueOf(printAtPlant) + "\n";
/* 535 */                   printAtPlant = String.valueOf(printAtPlant) + ((task.getComments() != null) ? task.getComments() : " "); continue;
/*     */                 } 
/* 537 */                 if (taskAbbrev.equalsIgnoreCase("TAPE")) {
/*     */ 
/*     */                   
/* 540 */                   masterAtPlantDate = String.valueOf(MilestoneHelper.getShortDate(task.getDueDate())) + 
/* 541 */                     " " + dueDateHolidayFlg;
/* 542 */                   if (task.getScheduledTaskStatus() != null && 
/* 543 */                     task.getScheduledTaskStatus().equals("N/A")) {
/*     */                     
/* 545 */                     masterAtPlant = "N/A";
/*     */                   } else {
/*     */                     
/* 548 */                     masterAtPlant = MilestoneHelper.getFormatedDate(task.getCompletionDate());
/*     */                   } 
/* 550 */                   masterAtPlant = String.valueOf(masterAtPlant) + "\n";
/* 551 */                   masterAtPlant = String.valueOf(masterAtPlant) + ((task.getComments() != null) ? task.getComments() : " "); continue;
/*     */                 } 
/* 553 */                 if (taskAbbrev.equalsIgnoreCase("SEPS")) {
/*     */ 
/*     */                   
/* 556 */                   sepsDate = String.valueOf(MilestoneHelper.getShortDate(task.getDueDate())) + 
/* 557 */                     " " + dueDateHolidayFlg;
/* 558 */                   if (task.getScheduledTaskStatus() != null && 
/* 559 */                     task.getScheduledTaskStatus().equals("N/A")) {
/*     */                     
/* 561 */                     seps = "N/A";
/*     */                   } else {
/*     */                     
/* 564 */                     seps = MilestoneHelper.getFormatedDate(task.getCompletionDate());
/*     */                   } 
/* 566 */                   seps = String.valueOf(seps) + "\n";
/* 567 */                   seps = String.valueOf(seps) + ((task.getComments() != null) ? task.getComments() : " "); continue;
/*     */                 } 
/* 569 */                 if (taskAbbrev.equalsIgnoreCase("BOM")) {
/*     */ 
/*     */                   
/* 572 */                   bomDate = String.valueOf(MilestoneHelper.getShortDate(task.getDueDate())) + 
/* 573 */                     " " + dueDateHolidayFlg;
/* 574 */                   if (task.getScheduledTaskStatus() != null && 
/* 575 */                     task.getScheduledTaskStatus().equals("N/A")) {
/*     */                     
/* 577 */                     bom = "N/A";
/*     */                   } else {
/*     */                     
/* 580 */                     bom = MilestoneHelper.getFormatedDate(task.getCompletionDate());
/*     */                   } 
/* 582 */                   bom = String.valueOf(bom) + "\n";
/* 583 */                   bom = String.valueOf(bom) + ((task.getComments() != null) ? task.getComments() : " ");
/*     */                   continue;
/*     */                 } 
/* 586 */                 if (taskAbbrev.equalsIgnoreCase("FAP"))
/*     */                 {
/*     */ 
/*     */                   
/* 590 */                   filmAtPrinterDate = String.valueOf(MilestoneHelper.getShortDate(task.getDueDate())) + 
/* 591 */                     " " + dueDateHolidayFlg;
/* 592 */                   if (task.getScheduledTaskStatus() != null && 
/* 593 */                     task.getScheduledTaskStatus().equals("N/A")) {
/*     */                     
/* 595 */                     filmAtPrinter = "N/A";
/*     */                   } else {
/*     */                     
/* 598 */                     filmAtPrinter = MilestoneHelper.getFormatedDate(task.getCompletionDate());
/*     */                   } 
/* 600 */                   filmAtPrinter = String.valueOf(filmAtPrinter) + "\n";
/* 601 */                   filmAtPrinter = String.valueOf(filmAtPrinter) + ((task.getComments() != null) ? task.getComments() : " ");
/*     */                 }
/*     */               
/*     */               } 
/* 605 */             } catch (Exception e) {
/*     */               
/* 607 */               e.printStackTrace();
/*     */             } 
/*     */           } 
/*     */         } 
/*     */         
/* 612 */         nextRow = 0;
/*     */         
/* 614 */         subTable = new DefaultTableLens(2, 15);
/*     */         
/* 616 */         subTable.setColWidth(0, 95);
/* 617 */         subTable.setColWidth(1, 225);
/* 618 */         subTable.setColWidth(2, 140);
/* 619 */         subTable.setColWidth(3, 80);
/* 620 */         subTable.setColWidth(4, 70);
/* 621 */         subTable.setColWidth(5, 70);
/* 622 */         subTable.setColWidth(6, 70);
/* 623 */         subTable.setColWidth(7, 70);
/* 624 */         subTable.setColWidth(8, 70);
/* 625 */         subTable.setColWidth(9, 70);
/* 626 */         subTable.setColWidth(10, 70);
/* 627 */         subTable.setColWidth(11, 70);
/* 628 */         subTable.setColWidth(12, 70);
/* 629 */         subTable.setColWidth(13, 70);
/* 630 */         subTable.setColWidth(14, 180);
/*     */ 
/*     */         
/* 633 */         subTable.setColBorderColor(nextRow, Color.lightGray);
/* 634 */         subTable.setRowBorderColor(nextRow, Color.lightGray);
/* 635 */         subTable.setRowBorder(4097);
/* 636 */         subTable.setColBorder(4097);
/*     */ 
/*     */ 
/*     */         
/* 640 */         subTable.setRowInsets(nextRow, new Insets(0, 0, 0, 0));
/*     */         
/* 642 */         subTable.setRowAlignment(nextRow, 2);
/*     */ 
/*     */         
/* 645 */         subTable.setSpan(nextRow, 0, new Dimension(1, 2));
/* 646 */         subTable.setObject(nextRow, 0, USIntRelease);
/*     */         
/* 648 */         subTable.setObject(nextRow, 1, String.valueOf(code) + " " + retail + " " + price);
/*     */         
/* 650 */         subTable.setObject(nextRow, 2, "Due Dates");
/* 651 */         subTable.setObject(nextRow, 3, coverApprovedDate);
/* 652 */         subTable.setObject(nextRow, 4, packageCopyDate);
/* 653 */         subTable.setObject(nextRow, 5, stickerCopyDate);
/* 654 */         subTable.setObject(nextRow, 6, djQtyDate);
/* 655 */         subTable.setObject(nextRow, 7, fmDate);
/* 656 */         subTable.setObject(nextRow, 8, manuImportDate);
/* 657 */         subTable.setObject(nextRow, 9, sepsDate);
/* 658 */         subTable.setObject(nextRow, 10, bomDate);
/* 659 */         subTable.setObject(nextRow, 11, masterAtPlantDate);
/* 660 */         subTable.setObject(nextRow, 12, filmAtPrinterDate);
/* 661 */         subTable.setObject(nextRow, 13, printAtPlantDate);
/* 662 */         subTable.setObject(nextRow, 14, sel.getSelectionComments());
/*     */         
/* 664 */         subTable.setSpan(nextRow, 14, new Dimension(1, 2));
/*     */         
/* 666 */         subTable.setRowFont(nextRow, new Font("Arial", 1, 7));
/*     */ 
/*     */ 
/*     */         
/* 670 */         Font holidayFont = new Font("Arial", 3, 7);
/* 671 */         for (int colIdx = 3; colIdx <= 13; colIdx++) {
/*     */           
/* 673 */           String dueDate = subTable.getObject(nextRow, colIdx).toString();
/* 674 */           if (dueDate != null && dueDate.length() > 0) {
/*     */             
/* 676 */             char lastChar = dueDate.charAt(dueDate.length() - 1);
/* 677 */             if (Character.isLetter(lastChar)) {
/* 678 */               subTable.setFont(nextRow, colIdx, holidayFont);
/*     */             }
/*     */           } 
/*     */         } 
/*     */         
/* 683 */         subTable.setFont(nextRow, 0, new Font("Arial", 0, 7));
/* 684 */         subTable.setFont(nextRow, 1, new Font("Arial", 0, 7));
/*     */         
/* 686 */         subTable.setAlignment(nextRow, 0, 10);
/*     */         
/* 688 */         subTable.setRowHeight(nextRow, 10);
/* 689 */         subTable.setColFont(0, new Font("Arial", 0, 8));
/*     */         
/* 691 */         subTable.setRowBackground(nextRow, Color.white);
/* 692 */         subTable.setBackground(nextRow, 0, Color.white);
/* 693 */         subTable.setBackground(nextRow, 1, Color.lightGray);
/* 694 */         subTable.setBackground(nextRow, 2, Color.lightGray);
/* 695 */         subTable.setBackground(nextRow, 3, Color.lightGray);
/* 696 */         subTable.setBackground(nextRow, 4, Color.lightGray);
/* 697 */         subTable.setBackground(nextRow, 5, Color.lightGray);
/* 698 */         subTable.setBackground(nextRow, 6, Color.lightGray);
/* 699 */         subTable.setBackground(nextRow, 7, Color.lightGray);
/* 700 */         subTable.setBackground(nextRow, 8, Color.lightGray);
/* 701 */         subTable.setBackground(nextRow, 9, Color.lightGray);
/* 702 */         subTable.setBackground(nextRow, 10, Color.lightGray);
/* 703 */         subTable.setBackground(nextRow, 11, Color.lightGray);
/* 704 */         subTable.setBackground(nextRow, 12, Color.lightGray);
/* 705 */         subTable.setBackground(nextRow, 13, Color.lightGray);
/*     */         
/* 707 */         subTable.setBackground(nextRow, 14, Color.white);
/*     */         
/* 709 */         subTable.setColBorderColor(nextRow, -1, Color.lightGray);
/* 710 */         subTable.setColBorderColor(nextRow, 0, Color.lightGray);
/* 711 */         subTable.setColBorderColor(nextRow, 1, Color.lightGray);
/* 712 */         subTable.setColBorderColor(nextRow, 2, Color.lightGray);
/* 713 */         subTable.setColBorderColor(nextRow, 3, Color.lightGray);
/* 714 */         subTable.setColBorderColor(nextRow, 4, Color.lightGray);
/* 715 */         subTable.setColBorderColor(nextRow, 5, Color.lightGray);
/* 716 */         subTable.setColBorderColor(nextRow, 6, Color.lightGray);
/* 717 */         subTable.setColBorderColor(nextRow, 7, Color.lightGray);
/* 718 */         subTable.setColBorderColor(nextRow, 8, Color.lightGray);
/* 719 */         subTable.setColBorderColor(nextRow, 9, Color.lightGray);
/* 720 */         subTable.setColBorderColor(nextRow, 10, Color.lightGray);
/* 721 */         subTable.setColBorderColor(nextRow, 11, Color.lightGray);
/* 722 */         subTable.setColBorderColor(nextRow, 12, Color.lightGray);
/* 723 */         subTable.setColBorderColor(nextRow, 13, Color.lightGray);
/* 724 */         subTable.setColBorderColor(nextRow, 14, Color.lightGray);
/*     */ 
/*     */         
/* 727 */         subTable.setRowBorderColor(nextRow, Color.lightGray);
/*     */         
/* 729 */         subTable.setRowBorderColor(nextRow - 1, Color.lightGray);
/* 730 */         subTable.setRowBorderColor(nextRow, 1, Color.white);
/*     */         
/* 732 */         subTable.setColBorder(nextRow, 1, 266240);
/* 733 */         subTable.setColBorder(nextRow, 2, 266240);
/* 734 */         subTable.setColBorder(nextRow, 3, 266240);
/* 735 */         subTable.setColBorder(nextRow, 4, 266240);
/* 736 */         subTable.setColBorder(nextRow, 5, 266240);
/* 737 */         subTable.setColBorder(nextRow, 6, 266240);
/* 738 */         subTable.setColBorder(nextRow, 7, 266240);
/* 739 */         subTable.setColBorder(nextRow, 8, 266240);
/* 740 */         subTable.setColBorder(nextRow, 9, 266240);
/* 741 */         subTable.setColBorder(nextRow, 10, 266240);
/* 742 */         subTable.setColBorder(nextRow, 11, 266240);
/* 743 */         subTable.setColBorder(nextRow, 12, 266240);
/* 744 */         subTable.setColBorder(nextRow, 13, 266240);
/*     */ 
/*     */         
/* 747 */         subTable.setAlignment(nextRow, 0, 10);
/* 748 */         subTable.setAlignment(nextRow, 1, 10);
/* 749 */         subTable.setAlignment(nextRow, 2, 10);
/* 750 */         subTable.setAlignment(nextRow, 3, 10);
/* 751 */         subTable.setAlignment(nextRow, 4, 10);
/* 752 */         subTable.setAlignment(nextRow, 5, 10);
/* 753 */         subTable.setAlignment(nextRow, 6, 10);
/* 754 */         subTable.setAlignment(nextRow, 7, 10);
/* 755 */         subTable.setAlignment(nextRow, 8, 10);
/* 756 */         subTable.setAlignment(nextRow, 9, 10);
/* 757 */         subTable.setAlignment(nextRow, 10, 10);
/* 758 */         subTable.setAlignment(nextRow, 11, 10);
/* 759 */         subTable.setAlignment(nextRow, 12, 10);
/* 760 */         subTable.setAlignment(nextRow, 13, 10);
/* 761 */         subTable.setAlignment(nextRow, 14, 9);
/*     */         
/* 763 */         nextRow++;
/*     */         
/* 765 */         subTable.setRowBorderColor(nextRow, Color.lightGray);
/*     */         
/* 767 */         subTable.setColBorderColor(nextRow, -1, Color.lightGray);
/* 768 */         subTable.setColBorderColor(nextRow, 0, Color.lightGray);
/* 769 */         subTable.setColBorderColor(nextRow, 1, Color.lightGray);
/* 770 */         subTable.setColBorderColor(nextRow, 2, Color.lightGray);
/* 771 */         subTable.setColBorderColor(nextRow, 3, Color.lightGray);
/* 772 */         subTable.setColBorderColor(nextRow, 4, Color.lightGray);
/* 773 */         subTable.setColBorderColor(nextRow, 5, Color.lightGray);
/* 774 */         subTable.setColBorderColor(nextRow, 6, Color.lightGray);
/* 775 */         subTable.setColBorderColor(nextRow, 7, Color.lightGray);
/* 776 */         subTable.setColBorderColor(nextRow, 8, Color.lightGray);
/* 777 */         subTable.setColBorderColor(nextRow, 9, Color.lightGray);
/* 778 */         subTable.setColBorderColor(nextRow, 10, Color.lightGray);
/* 779 */         subTable.setColBorderColor(nextRow, 11, Color.lightGray);
/* 780 */         subTable.setColBorderColor(nextRow, 12, Color.lightGray);
/* 781 */         subTable.setColBorderColor(nextRow, 13, Color.lightGray);
/* 782 */         subTable.setColBorderColor(nextRow, 14, Color.lightGray);
/*     */ 
/*     */         
/* 785 */         subTable.setRowInsets(nextRow, new Insets(0, 0, 0, 0));
/*     */         
/* 787 */         subTable.setRowFont(nextRow, new Font("Arial", 0, 7));
/* 788 */         subTable.setRowAlignment(nextRow, 10);
/*     */ 
/*     */         
/* 791 */         subTable.setObject(nextRow, 0, "");
/* 792 */         subTable.setObject(nextRow, 1, String.valueOf(upc) + "\n" + localProductNumber + "\n" + artistName + "\n" + title);
/* 793 */         subTable.setObject(nextRow, 2, labelAndPackage);
/* 794 */         subTable.setObject(nextRow, 3, coverApproved);
/* 795 */         subTable.setObject(nextRow, 4, packageCopy);
/* 796 */         subTable.setObject(nextRow, 5, stickerCopy);
/* 797 */         subTable.setObject(nextRow, 6, djQty);
/* 798 */         subTable.setObject(nextRow, 7, fm);
/* 799 */         subTable.setObject(nextRow, 8, manuImportQty);
/* 800 */         subTable.setObject(nextRow, 9, seps);
/* 801 */         subTable.setObject(nextRow, 10, bom);
/* 802 */         subTable.setObject(nextRow, 11, masterAtPlant);
/* 803 */         subTable.setObject(nextRow, 12, filmAtPrinter);
/* 804 */         subTable.setObject(nextRow, 13, printAtPlant);
/* 805 */         subTable.setObject(nextRow, 14, sel.getSelectionComments());
/*     */ 
/*     */         
/* 808 */         body = new SectionBand(report);
/* 809 */         body.addTable(subTable, new Rectangle(800, 800));
/*     */         
/* 811 */         double lfLineCount = 1.5D;
/*     */         
/* 813 */         if (commentLines > 0 || labelAndPackage.length() > 17 || sel.getTitle().length() > 20) {
/*     */           
/* 815 */           body.setHeight(1.5F);
/*     */         }
/*     */         else {
/*     */           
/* 819 */           body.setHeight(1.0F);
/*     */         } 
/*     */         
/* 822 */         if (commentLines > 0 || labelAndPackage.length() > 17 || sel.getTitle().length() > 20 || 
/* 823 */           coverApproved.length() > 25 || masterAtPlant.length() > 25 || manuImportQty.length() > 25 || 
/* 824 */           packageCopy.length() > 25 || stickerCopy.length() > 25 || 
/* 825 */           seps.length() > 25 || 
/* 826 */           packageFilm.length() > 25 || 
/* 827 */           printAtPlant.length() > 25) {
/*     */           
/* 829 */           if (lfLineCount < commentLines * 0.3D) {
/* 830 */             lfLineCount = commentLines * 0.3D;
/*     */           }
/*     */ 
/*     */           
/* 834 */           if (lfLineCount < (labelAndPackage.length() / 8) * 0.3D) {
/* 835 */             lfLineCount = (labelAndPackage.length() / 8) * 0.3D;
/*     */           }
/* 837 */           if (lfLineCount < (packageFilm.length() / 8) * 0.3D) {
/* 838 */             lfLineCount = (packageFilm.length() / 8) * 0.3D;
/*     */           }
/* 840 */           if (lfLineCount < (printAtPlant.length() / 8) * 0.3D) {
/* 841 */             lfLineCount = (printAtPlant.length() / 8) * 0.3D;
/*     */           }
/* 843 */           if (lfLineCount < (sel.getTitle().length() / 8) * 0.3D) {
/* 844 */             lfLineCount = (sel.getTitle().length() / 8) * 0.3D;
/*     */           }
/* 846 */           if (lfLineCount < (coverApproved.length() / 7) * 0.3D) {
/* 847 */             lfLineCount = (coverApproved.length() / 7) * 0.3D;
/*     */           }
/* 849 */           if (lfLineCount < (masterAtPlant.length() / 7) * 0.3D) {
/* 850 */             lfLineCount = (masterAtPlant.length() / 7) * 0.3D;
/*     */           }
/* 852 */           if (lfLineCount < (manuImportQty.length() / 7) * 0.3D) {
/* 853 */             lfLineCount = (manuImportQty.length() / 7) * 0.3D;
/*     */           }
/* 855 */           if (lfLineCount < (packageCopy.length() / 7) * 0.3D) {
/* 856 */             lfLineCount = (packageCopy.length() / 7) * 0.3D;
/*     */           }
/* 858 */           if (lfLineCount < (stickerCopy.length() / 7) * 0.3D) {
/* 859 */             lfLineCount = (stickerCopy.length() / 7) * 0.3D;
/*     */           }
/*     */ 
/*     */           
/* 863 */           if (lfLineCount < (seps.length() / 7) * 0.3D) {
/* 864 */             lfLineCount = (seps.length() / 7) * 0.3D;
/*     */           }
/*     */ 
/*     */           
/* 868 */           body.setHeight((float)lfLineCount);
/*     */         }
/* 870 */         else if (commentLines > 0 || labelAndPackage.length() > 17 || sel.getTitle().length() > 20 || 
/* 871 */           coverApproved.length() > 5 || masterAtPlant.length() > 5 || manuImportQty.length() > 5 || 
/* 872 */           packageCopy.length() > 5 || stickerCopy.length() > 5 || 
/* 873 */           seps.length() > 5 || 
/* 874 */           packageFilm.length() > 5 || 
/* 875 */           printAtPlant.length() > 5) {
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 880 */           if (lfLineCount < commentLines * 0.3D) {
/* 881 */             lfLineCount = commentLines * 0.3D;
/*     */           }
/*     */ 
/*     */           
/* 885 */           if (lfLineCount < (labelAndPackage.length() / 5) * 0.3D) {
/* 886 */             lfLineCount = (labelAndPackage.length() / 5) * 0.3D;
/*     */           }
/* 888 */           if (lfLineCount < (packageFilm.length() / 5) * 0.3D) {
/* 889 */             lfLineCount = (packageFilm.length() / 5) * 0.3D;
/*     */           }
/* 891 */           if (lfLineCount < (printAtPlant.length() / 5) * 0.3D) {
/* 892 */             lfLineCount = (printAtPlant.length() / 5) * 0.3D;
/*     */           }
/* 894 */           if (lfLineCount < (sel.getTitle().length() / 5) * 0.3D) {
/* 895 */             lfLineCount = (sel.getTitle().length() / 5) * 0.3D;
/*     */           }
/* 897 */           if (lfLineCount < (coverApproved.length() / 5) * 0.3D) {
/* 898 */             lfLineCount = (coverApproved.length() / 5) * 0.3D;
/*     */           }
/* 900 */           if (lfLineCount < (masterAtPlant.length() / 5) * 0.3D) {
/* 901 */             lfLineCount = (masterAtPlant.length() / 5) * 0.3D;
/*     */           }
/* 903 */           if (lfLineCount < (manuImportQty.length() / 5) * 0.3D) {
/* 904 */             lfLineCount = (manuImportQty.length() / 5) * 0.3D;
/*     */           }
/* 906 */           if (lfLineCount < (packageCopy.length() / 5) * 0.3D) {
/* 907 */             lfLineCount = (packageCopy.length() / 5) * 0.3D;
/*     */           }
/* 909 */           if (lfLineCount < (stickerCopy.length() / 5) * 0.3D) {
/* 910 */             lfLineCount = (stickerCopy.length() / 5) * 0.3D;
/*     */           }
/*     */ 
/*     */           
/* 914 */           if (lfLineCount < (seps.length() / 5) * 0.3D) {
/* 915 */             lfLineCount = (seps.length() / 5) * 0.3D;
/*     */           }
/*     */ 
/*     */           
/* 919 */           body.setHeight((float)lfLineCount);
/*     */         
/*     */         }
/*     */         else {
/*     */           
/* 924 */           body.setHeight(1.0F);
/*     */         } 
/*     */         
/* 927 */         body.setBottomBorder(0);
/* 928 */         body.setTopBorder(0);
/* 929 */         body.setShrinkToFit(true);
/* 930 */         body.setVisible(true);
/*     */         
/* 932 */         group = new DefaultSectionLens(null, group, body);
/*     */       } 
/*     */ 
/*     */       
/* 936 */       group = new DefaultSectionLens(hbandType, group, footer);
/*     */       
/* 938 */       report.addSection(group, rowCountTable);
/*     */ 
/*     */       
/* 941 */       group = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     }
/* 949 */     catch (Exception e) {
/*     */       
/* 951 */       System.out.println(">>>>>>>>ReportHandler.fillClassicProductionScheduleForPrint(): exception: " + e);
/* 952 */       e.printStackTrace();
/*     */     } 
/*     */ 
/*     */     
/* 956 */     statusJSPupdate = null;
/*     */   }
/*     */ }


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\ClassicsProductionScheduleForPrintSubHandler.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */