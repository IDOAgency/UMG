/*     */ package WEB-INF.classes.com.universal.milestone;
/*     */ 
/*     */ import com.techempower.ComponentLog;
/*     */ import com.techempower.gemini.Context;
/*     */ import com.techempower.gemini.GeminiApplication;
/*     */ import com.universal.milestone.Form;
/*     */ import com.universal.milestone.MilestoneHelper;
/*     */ import com.universal.milestone.MilestoneHelper_2;
/*     */ import com.universal.milestone.Schedule;
/*     */ import com.universal.milestone.ScheduledTask;
/*     */ import com.universal.milestone.SecureHandler;
/*     */ import com.universal.milestone.Selection;
/*     */ import com.universal.milestone.SelectionStatus;
/*     */ import com.universal.milestone.TaskDueDateForPrintSubHandler;
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
/*     */ public class TaskDueDateForPrintSubHandler
/*     */   extends SecureHandler
/*     */ {
/*     */   public static final String COMPONENT_CODE = "hCProd";
/*     */   public GeminiApplication application;
/*     */   public ComponentLog log;
/*     */   
/*     */   public TaskDueDateForPrintSubHandler(GeminiApplication application) {
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
/*     */   protected static StyleSheet fillTaskDueDateForPrint(Context context, String reportPath) {
/*  85 */     Color SHADED_AREA_COLOR = Color.lightGray;
/*  86 */     int COL_LINE_STYLE = 4097;
/*  87 */     int HEADER_FONT_SIZE = 12;
/*  88 */     int NUM_COLUMNS = 8;
/*     */     
/*  90 */     double ldLineVal = 0.3D;
/*     */ 
/*     */     
/*  93 */     int separatorLineStyle = 266240;
/*  94 */     Color separatorLineColor = Color.black;
/*     */ 
/*     */     
/*  97 */     int tableHeaderLineStyle = 266240;
/*     */     
/*  99 */     Color tableHeaderLineColor = Color.black;
/*     */ 
/*     */ 
/*     */     
/* 103 */     int tableRowLineStyle = 4097;
/*     */     
/* 105 */     Color tableRowLineColor = new Color(208, 206, 206, 0);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 115 */       HttpServletResponse sresponse = context.getResponse();
/* 116 */       context.putDelivery("status", new String("start_gathering"));
/* 117 */       context.includeJSP("status.jsp", "hiddenFrame");
/* 118 */       sresponse.setContentType("text/plain");
/* 119 */       sresponse.flushBuffer();
/*     */     }
/* 121 */     catch (Exception exception) {}
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 126 */     Vector selections = MilestoneHelper.getSelectionsForReport(context);
/*     */ 
/*     */     
/*     */     try {
/* 130 */       HttpServletResponse sresponse = context.getResponse();
/* 131 */       context.putDelivery("status", new String("start_report"));
/* 132 */       context.putDelivery("percent", new String("10"));
/* 133 */       context.includeJSP("status.jsp", "hiddenFrame");
/* 134 */       sresponse.setContentType("text/plain");
/* 135 */       sresponse.flushBuffer();
/*     */     }
/* 137 */     catch (Exception exception) {}
/*     */ 
/*     */ 
/*     */     
/* 141 */     int numSelections = selections.size();
/*     */ 
/*     */     
/* 144 */     MilestoneHelper.setSelectionSorting(selections, 12);
/* 145 */     Collections.sort(selections);
/*     */ 
/*     */     
/* 148 */     MilestoneHelper.setSelectionSorting(selections, 13);
/* 149 */     Collections.sort(selections);
/*     */ 
/*     */     
/* 152 */     MilestoneHelper.setSelectionSorting(selections, 4);
/* 153 */     Collections.sort(selections);
/*     */ 
/*     */     
/* 156 */     MilestoneHelper.setSelectionSorting(selections, 3);
/* 157 */     Collections.sort(selections);
/*     */ 
/*     */     
/* 160 */     MilestoneHelper.setSelectionSorting(selections, 6);
/* 161 */     Collections.sort(selections);
/*     */ 
/*     */     
/* 164 */     MilestoneHelper.setSelectionSorting(selections, 10);
/* 165 */     Collections.sort(selections);
/*     */ 
/*     */     
/* 168 */     if (numSelections == 0) {
/* 169 */       return null;
/*     */     }
/*     */     
/* 172 */     StyleSheet[] sheets = new StyleSheet[numSelections];
/*     */ 
/*     */     
/*     */     try {
/* 176 */       DefaultTableLens table_contents = null;
/* 177 */       DefaultTableLens rowCountTable = null;
/* 178 */       DefaultTableLens columnHeaderTable = null;
/* 179 */       DefaultTableLens subTable = null;
/* 180 */       DefaultTableLens headerTableLens = null;
/*     */       
/* 182 */       rowCountTable = new DefaultTableLens(2, 10000);
/*     */ 
/*     */ 
/*     */       
/* 186 */       for (int i = 0; i < numSelections; i++)
/*     */       {
/* 188 */         InputStream input = new FileInputStream(String.valueOf(reportPath) + "\\taskDueDate.xml");
/* 189 */         XStyleSheet report = (XStyleSheet)Builder.getBuilder(1, input).read(null);
/*     */         
/* 191 */         SectionBand hbandType = new SectionBand(report);
/* 192 */         SectionBand hbandCategory = new SectionBand(report);
/* 193 */         SectionBand hbandDate = new SectionBand(report);
/* 194 */         SectionBand body = new SectionBand(report);
/* 195 */         SectionBand footer = new SectionBand(report);
/* 196 */         SectionBand spacer = new SectionBand(report);
/* 197 */         DefaultSectionLens group = null;
/*     */         
/* 199 */         footer.setVisible(true);
/* 200 */         footer.setHeight(0.1F);
/* 201 */         footer.setShrinkToFit(false);
/* 202 */         footer.setBottomBorder(0);
/*     */         
/* 204 */         spacer.setVisible(true);
/* 205 */         spacer.setHeight(0.05F);
/* 206 */         spacer.setShrinkToFit(false);
/* 207 */         spacer.setBottomBorder(0);
/*     */ 
/*     */         
/* 210 */         SeparatorElement topSeparator = (SeparatorElement)report.getElement("separator_top");
/* 211 */         SeparatorElement bottomHeaderSeparator = (SeparatorElement)report.getElement("separator_bottom_header");
/* 212 */         SeparatorElement bottomSeparator = (SeparatorElement)report.getElement("separator_bottom");
/* 213 */         if (topSeparator != null) {
/*     */           
/* 215 */           topSeparator.setStyle(separatorLineStyle);
/* 216 */           topSeparator.setForeground(separatorLineColor);
/*     */         } 
/* 218 */         if (bottomHeaderSeparator != null) {
/*     */           
/* 220 */           bottomHeaderSeparator.setStyle(separatorLineStyle);
/* 221 */           bottomHeaderSeparator.setForeground(separatorLineColor);
/*     */         } 
/* 223 */         if (bottomSeparator != null) {
/*     */           
/* 225 */           bottomSeparator.setStyle(separatorLineStyle);
/* 226 */           bottomSeparator.setForeground(separatorLineColor);
/*     */         } 
/*     */ 
/*     */         
/* 230 */         Form reportForm = (Form)context.getSessionValue("reportForm");
/*     */         
/* 232 */         Calendar beginDueDate = (reportForm.getStringValue("beginDueDate") != null && 
/* 233 */           reportForm.getStringValue("beginDueDate").length() > 0) ? 
/* 234 */           MilestoneHelper.getDate(reportForm.getStringValue("beginDueDate")) : null;
/*     */         
/* 236 */         Calendar endDueDate = (reportForm.getStringValue("endDueDate") != null && 
/* 237 */           reportForm.getStringValue("endDueDate").length() > 0) ? 
/* 238 */           MilestoneHelper.getDate(reportForm.getStringValue("endDueDate")) : null;
/*     */         
/* 240 */         report.setElement("startdate", MilestoneHelper.getFormatedDate(beginDueDate));
/* 241 */         report.setElement("enddate", MilestoneHelper.getFormatedDate(endDueDate));
/*     */ 
/*     */         
/* 244 */         SimpleDateFormat formatter = new SimpleDateFormat("MMMM d, yyyy");
/* 245 */         String todayLong = formatter.format(new Date());
/* 246 */         report.setElement("bottomdate", todayLong);
/*     */ 
/*     */         
/* 249 */         String strUmlKey = (reportForm.getStringValue("umlKeyTask") != null) ? reportForm.getStringValue("umlKeyTask") : "";
/*     */ 
/*     */         
/* 252 */         Selection sel = (Selection)selections.elementAt(i);
/*     */ 
/*     */         
/* 255 */         if (sel != null) {
/*     */ 
/*     */           
/* 258 */           String selFamily = (sel.getFamily() != null && sel.getFamily().getName() != null) ? 
/* 259 */             sel.getFamily().getName() : "";
/*     */ 
/*     */           
/* 262 */           String selCompany = (sel.getCompany() != null && sel.getCompany().getName() != null) ? 
/* 263 */             sel.getCompany().getName() : "";
/*     */ 
/*     */           
/* 266 */           int region = (sel.getEnvironment() != null && sel.getEnvironment().getDistribution() >= 0) ? sel.getEnvironment().getDistribution() : -1;
/* 267 */           String selRegion = "";
/*     */           
/* 269 */           if (region == 0) {
/* 270 */             selRegion = "West";
/* 271 */           } else if (region == 1) {
/* 272 */             selRegion = "East";
/*     */           } 
/*     */           
/* 275 */           String selProject = (sel.getProjectID() != null) ? sel.getProjectID() : "";
/*     */ 
/*     */           
/* 278 */           String selPD = sel.getPressAndDistribution() ? "Yes" : "";
/*     */ 
/*     */           
/* 281 */           String selDivision = (sel.getDivision() != null && sel.getDivision().getName() != null) ? 
/* 282 */             sel.getDivision().getName() : "";
/*     */ 
/*     */           
/* 285 */           String selLabel = (sel.getLabel() != null && sel.getLabel().getName() != null) ? 
/* 286 */             sel.getLabel().getName() : "";
/*     */ 
/*     */ 
/*     */           
/* 290 */           String selId = (sel.getTitleID() != null) ? sel.getTitleID() : "";
/*     */ 
/*     */           
/* 293 */           String selUpc = (sel.getUpc() != null) ? sel.getUpc() : "";
/*     */ 
/*     */           
/* 296 */           selUpc = MilestoneHelper_2.getRMSReportFormat(selUpc, "UPC", sel.getIsDigital());
/*     */ 
/*     */           
/* 299 */           String selConfig = (sel.getSelectionSubConfig() != null && sel.getSelectionSubConfig().getSelectionSubConfigurationAbbreviation() != null) ? 
/* 300 */             sel.getSelectionSubConfig().getSelectionSubConfigurationAbbreviation() : "";
/*     */ 
/*     */           
/* 303 */           String selStreetDate = "";
/* 304 */           selStreetDate = MilestoneHelper.getFormatedDate(sel.getStreetDate());
/*     */ 
/*     */ 
/*     */           
/* 308 */           String statusString = "";
/* 309 */           SelectionStatus status = sel.getSelectionStatus();
/*     */           
/* 311 */           if (status != null) {
/* 312 */             statusString = (status.getName() == null) ? "" : status.getName();
/*     */           }
/* 314 */           if (!statusString.equalsIgnoreCase("ACTIVE"))
/*     */           {
/* 316 */             if (!statusString.equalsIgnoreCase("In The Works")) {
/* 317 */               selStreetDate = String.valueOf(statusString) + " " + selStreetDate;
/*     */             } else {
/* 319 */               selStreetDate = "ITW " + selStreetDate;
/*     */             } 
/*     */           }
/*     */           
/* 323 */           String selArtist = (sel.getArtist() != null) ? sel.getArtist() : "";
/*     */ 
/*     */           
/* 326 */           String selTitle = (sel.getTitle() != null) ? sel.getTitle() : "";
/*     */           
/* 328 */           report.setElement("artist", selArtist);
/* 329 */           report.setElement("title", selTitle);
/* 330 */           report.setElement("project", selProject);
/* 331 */           report.setElement("street_date", selStreetDate);
/* 332 */           report.setElement("company", selCompany);
/* 333 */           report.setElement("label", selLabel);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 339 */           Schedule schedule = sel.getSchedule();
/*     */           
/* 341 */           Vector scheduledTasks = new Vector();
/* 342 */           if (schedule != null) {
/*     */             
/* 344 */             scheduledTasks = schedule.getTasks();
/*     */ 
/*     */             
/* 347 */             if (scheduledTasks != null && strUmlKey.equalsIgnoreCase("label"))
/*     */             {
/* 349 */               scheduledTasks = MilestoneHelper.filterUmlTasks(scheduledTasks, false);
/*     */             }
/*     */             
/* 352 */             if (scheduledTasks != null && strUmlKey.equalsIgnoreCase("uml"))
/*     */             {
/* 354 */               scheduledTasks = MilestoneHelper.filterUmlTasks(scheduledTasks, true);
/*     */             }
/*     */             
/* 357 */             if (scheduledTasks == null) {
/* 358 */               scheduledTasks = new Vector();
/*     */             }
/*     */           } 
/*     */           
/* 362 */           int commentCount = MilestoneHelper.countTasksWithComments(scheduledTasks);
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 367 */           table_contents = new DefaultTableLens(1, 8);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 373 */           table_contents.setColAlignment(0, 1);
/* 374 */           table_contents.setColAlignment(1, 1);
/* 375 */           table_contents.setColAlignment(2, 1);
/* 376 */           table_contents.setColAlignment(3, 1);
/* 377 */           table_contents.setColAlignment(4, 4);
/* 378 */           table_contents.setColAlignment(6, 12);
/*     */           
/* 380 */           table_contents.setColBorderColor(Color.black);
/* 381 */           table_contents.setRowBorderColor(Color.black);
/* 382 */           table_contents.setRowBorder(0);
/* 383 */           table_contents.setColBorder(0);
/*     */           
/* 385 */           table_contents.setRowBorder(1, 4097);
/*     */ 
/*     */           
/* 388 */           table_contents.setColWidth(0, 35);
/* 389 */           table_contents.setLineWrap(0, 0, false);
/* 390 */           table_contents.setLineWrap(0, 1, false);
/* 391 */           table_contents.setColWidth(1, 82);
/* 392 */           table_contents.setColWidth(2, 55);
/* 393 */           table_contents.setColWidth(3, 45);
/*     */           
/* 395 */           table_contents.setColWidth(4, 45);
/* 396 */           table_contents.setColWidth(5, 1);
/*     */           
/* 398 */           table_contents.setColWidth(6, 56);
/* 399 */           table_contents.setColWidth(7, 1);
/*     */ 
/*     */           
/* 402 */           table_contents.setHeaderRowCount(1);
/* 403 */           table_contents.setObject(0, 0, "Description /");
/* 404 */           table_contents.setFont(0, 0, new Font("Arial", 1, 10));
/*     */           
/* 406 */           table_contents.setObject(0, 1, "Comments");
/* 407 */           table_contents.setFont(0, 1, new Font("Arial", 3, 10));
/*     */           
/* 409 */           table_contents.setObject(0, 2, "Task Owner");
/* 410 */           table_contents.setFont(0, 2, new Font("Arial", 1, 10));
/* 411 */           table_contents.setObject(0, 3, "Department");
/* 412 */           table_contents.setFont(0, 3, new Font("Arial", 1, 10));
/*     */           
/* 414 */           table_contents.setObject(0, 4, "Date Due");
/* 415 */           table_contents.setFont(0, 4, new Font("Arial", 1, 10));
/* 416 */           table_contents.setSpan(0, 4, new Dimension(2, 1));
/*     */           
/* 418 */           table_contents.setObject(0, 6, "Complete");
/* 419 */           table_contents.setSpan(0, 6, new Dimension(2, 1));
/* 420 */           table_contents.setFont(0, 6, new Font("Arial", 1, 10));
/*     */ 
/*     */           
/* 423 */           table_contents.setRowBorder(-1, 0);
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 428 */           hbandType.addTable(table_contents, new Rectangle(0, 0, 800, 30));
/*     */           
/* 430 */           int nextRow = 0;
/*     */           
/* 432 */           headerTableLens = new DefaultTableLens(1, 8);
/* 433 */           headerTableLens.setColWidth(0, 5);
/*     */           
/* 435 */           headerTableLens.setSpan(0, 1, new Dimension(2, 1));
/* 436 */           headerTableLens.setColAlignment(0, 4);
/* 437 */           headerTableLens.setColWidth(1, 5);
/* 438 */           headerTableLens.setColWidth(2, 5);
/*     */           
/* 440 */           headerTableLens.setColWidth(3, 1);
/* 441 */           headerTableLens.setColAlignment(3, 4);
/*     */           
/* 443 */           headerTableLens.setSpan(0, 4, new Dimension(4, 1));
/* 444 */           headerTableLens.setColAlignment(4, 1);
/* 445 */           headerTableLens.setColWidth(4, 5);
/* 446 */           headerTableLens.setColWidth(5, 5);
/* 447 */           headerTableLens.setColWidth(6, 5);
/* 448 */           headerTableLens.setColWidth(7, 5);
/*     */           
/* 450 */           headerTableLens.setColBorder(-1, 4097);
/* 451 */           headerTableLens.setColBorder(0, 0);
/* 452 */           headerTableLens.setColBorder(1, 0);
/* 453 */           headerTableLens.setColBorder(2, 0);
/* 454 */           headerTableLens.setColBorder(3, 0);
/* 455 */           headerTableLens.setColBorder(4, 0);
/* 456 */           headerTableLens.setColBorder(5, 0);
/*     */           
/* 458 */           headerTableLens.setColBorderColor(Color.black);
/* 459 */           headerTableLens.setRowBorderColor(Color.black);
/* 460 */           headerTableLens.setRowBackground(0, Color.lightGray);
/*     */           
/* 462 */           headerTableLens.setFont(0, 0, new Font("Arial", 3, 10));
/* 463 */           headerTableLens.setFont(0, 1, new Font("Arial", 0, 10));
/* 464 */           headerTableLens.setFont(0, 3, new Font("Arial", 3, 10));
/* 465 */           headerTableLens.setFont(0, 4, new Font("Arial", 0, 10));
/* 466 */           headerTableLens.setObject(0, 0, "Selection ID:");
/* 467 */           headerTableLens.setObject(0, 1, selId);
/* 468 */           headerTableLens.setObject(0, 3, "UPC");
/* 469 */           headerTableLens.setObject(0, 4, selUpc);
/*     */           
/* 471 */           hbandType.addTable(headerTableLens, new Rectangle(0, 30, 800, 30));
/* 472 */           hbandType.setHeight(1.0F);
/*     */ 
/*     */           
/* 475 */           for (int j = 0; j < scheduledTasks.size(); j++) {
/*     */             
/* 477 */             nextRow = 0;
/*     */             
/* 479 */             ScheduledTask task = (ScheduledTask)scheduledTasks.get(j);
/*     */             
/* 481 */             boolean taskHasComment = false;
/*     */             
/* 483 */             String taskComments = (task.getComments() != null) ? task.getComments().trim() : "";
/*     */ 
/*     */             
/* 486 */             if (!taskComments.equals("") && !taskComments.equalsIgnoreCase("null")) {
/* 487 */               taskHasComment = true;
/*     */             }
/* 489 */             if (taskHasComment) {
/*     */               
/* 491 */               subTable = new DefaultTableLens(2, 8);
/*     */             
/*     */             }
/*     */             else {
/*     */ 
/*     */               
/* 497 */               subTable = new DefaultTableLens(1, 8);
/*     */             } 
/*     */ 
/*     */             
/* 501 */             subTable.setColAlignment(0, 1);
/* 502 */             subTable.setColAlignment(1, 1);
/* 503 */             subTable.setColAlignment(2, 1);
/* 504 */             subTable.setColAlignment(3, 1);
/* 505 */             subTable.setColAlignment(4, 4);
/* 506 */             subTable.setColAlignment(6, 12);
/*     */             
/* 508 */             subTable.setColWidth(0, 35);
/* 509 */             subTable.setLineWrap(0, 0, false);
/* 510 */             subTable.setLineWrap(0, 1, false);
/* 511 */             subTable.setColWidth(1, 82);
/* 512 */             subTable.setColWidth(2, 55);
/* 513 */             subTable.setColWidth(3, 45);
/*     */             
/* 515 */             subTable.setColWidth(4, 45);
/* 516 */             subTable.setColWidth(5, 1);
/*     */             
/* 518 */             subTable.setColWidth(6, 56);
/* 519 */             subTable.setColWidth(7, 1);
/*     */             
/* 521 */             subTable.setColBorderColor(Color.black);
/* 522 */             subTable.setRowBorderColor(Color.black);
/* 523 */             subTable.setRowBorder(0);
/* 524 */             subTable.setColBorder(0);
/*     */             
/* 526 */             subTable.setRowBorder(2, 4097);
/*     */ 
/*     */             
/* 529 */             String taskName = (task.getName() != null) ? task.getName().trim() : "";
/*     */ 
/*     */             
/* 532 */             String taskOwner = (task.getOwner() != null && task.getOwner().getName() != null) ? 
/* 533 */               task.getOwner().getName() : "";
/*     */ 
/*     */ 
/*     */             
/* 537 */             String taskDepartment = (task.getDepartment() != null && !task.getDepartment().equalsIgnoreCase("-1") && !task.getDepartment().equalsIgnoreCase("null")) ? 
/* 538 */               task.getDepartment() : "";
/*     */ 
/*     */             
/* 541 */             String taskDueDate = (task.getDueDate() != null) ? 
/* 542 */               MilestoneHelper.getFormatedDate(task.getDueDate()) : "";
/*     */ 
/*     */             
/* 545 */             String taskStatus = task.getScheduledTaskStatus();
/*     */ 
/*     */             
/* 548 */             String taskCompletionDate = (task.getCompletionDate() != null) ? 
/* 549 */               MilestoneHelper.getFormatedDate(task.getCompletionDate()) : "";
/*     */             
/* 551 */             if (taskStatus.equalsIgnoreCase("N/A")) {
/* 552 */               taskCompletionDate = "N/A";
/*     */             }
/*     */             
/* 555 */             subTable.setObject(nextRow, 0, taskName);
/* 556 */             subTable.setSpan(nextRow, 0, new Dimension(2, 1));
/* 557 */             subTable.setObject(nextRow, 2, taskOwner);
/* 558 */             subTable.setObject(nextRow, 3, taskDepartment);
/* 559 */             subTable.setObject(nextRow, 4, taskDueDate);
/* 560 */             subTable.setObject(nextRow, 6, taskCompletionDate);
/*     */ 
/*     */             
/* 563 */             subTable.setRowFont(nextRow, new Font("Arial", 0, 9));
/*     */ 
/*     */             
/* 566 */             if (taskHasComment) {
/*     */               
/* 568 */               nextRow++;
/* 569 */               subTable.setSpan(nextRow, 0, new Dimension(2, 1));
/* 570 */               subTable.setObject(nextRow, 0, taskComments);
/* 571 */               subTable.setRowFont(nextRow, new Font("Arial", 2, 9));
/* 572 */               subTable.setRowBorder(nextRow - 1, 0);
/*     */             } 
/*     */ 
/*     */ 
/*     */             
/* 577 */             subTable.setRowBorder(nextRow, tableRowLineStyle);
/* 578 */             subTable.setRowBorderColor(nextRow, tableRowLineColor);
/*     */             
/* 580 */             body = new SectionBand(report);
/* 581 */             double lfLineCount = 1.5D;
/* 582 */             body.setHeight(1.5F);
/*     */             
/* 584 */             body.addTable(subTable, new Rectangle(800, 800));
/* 585 */             body.setBottomBorder(0);
/* 586 */             body.setTopBorder(0);
/* 587 */             body.setShrinkToFit(true);
/* 588 */             body.setVisible(true);
/*     */             
/* 590 */             group = new DefaultSectionLens(null, group, body);
/*     */           } 
/*     */           
/* 593 */           group = new DefaultSectionLens(hbandType, group, null);
/* 594 */           report.addSection(group, rowCountTable);
/* 595 */           group = null;
/*     */         } 
/*     */ 
/*     */         
/* 599 */         sheets[i] = report;
/*     */       }
/*     */     
/*     */     }
/* 603 */     catch (Exception e) {
/*     */       
/* 605 */       System.out.println(">>>>>>>>ReportHandler.fillEntProdStatusForPrint(): exception: " + e);
/*     */     } 
/*     */ 
/*     */     
/* 609 */     return new CompositeSheet(sheets);
/*     */   }
/*     */ }


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\TaskDueDateForPrintSubHandler.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */