/*     */ package WEB-INF.classes.com.universal.milestone;
/*     */ 
/*     */ import com.techempower.ComponentLog;
/*     */ import com.techempower.gemini.Context;
/*     */ import com.techempower.gemini.GeminiApplication;
/*     */ import com.universal.milestone.EntProdStatusForPrintSubHandler;
/*     */ import com.universal.milestone.Form;
/*     */ import com.universal.milestone.MilestoneHelper;
/*     */ import com.universal.milestone.MilestoneHelper_2;
/*     */ import com.universal.milestone.ReleasingFamily;
/*     */ import com.universal.milestone.Schedule;
/*     */ import com.universal.milestone.ScheduledTask;
/*     */ import com.universal.milestone.SecureHandler;
/*     */ import com.universal.milestone.Selection;
/*     */ import com.universal.milestone.SelectionManager;
/*     */ import com.universal.milestone.SelectionStatus;
/*     */ import inetsoft.report.CompositeSheet;
/*     */ import inetsoft.report.SeparatorElement;
/*     */ import inetsoft.report.StyleSheet;
/*     */ import inetsoft.report.XStyleSheet;
/*     */ import inetsoft.report.io.Builder;
/*     */ import inetsoft.report.lens.DefaultTableLens;
/*     */ import java.awt.Color;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Font;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class EntProdStatusForPrintSubHandler
/*     */   extends SecureHandler
/*     */ {
/*     */   public static final String COMPONENT_CODE = "hEntProd";
/*     */   public GeminiApplication application;
/*     */   public ComponentLog log;
/*     */   
/*     */   public EntProdStatusForPrintSubHandler(GeminiApplication application) {
/*  77 */     this.application = application;
/*  78 */     this.log = application.getLog("hEntProd");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  86 */   public String getDescription() { return "Sub Report"; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static StyleSheet fillEntProdStatusForPrint(Context context, String reportPath) {
/*  94 */     int separatorLineStyle = 266240;
/*  95 */     Color separatorLineColor = Color.black;
/*     */ 
/*     */     
/*  98 */     int tableHeaderLineStyle = 266240;
/*     */     
/* 100 */     Color tableHeaderLineColor = Color.black;
/*     */ 
/*     */ 
/*     */     
/* 104 */     int tableRowLineStyle = 4097;
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
/*     */     try {
/* 116 */       HttpServletResponse sresponse = context.getResponse();
/* 117 */       context.putDelivery("status", new String("start_gathering"));
/* 118 */       context.includeJSP("status.jsp", "hiddenFrame");
/* 119 */       sresponse.setContentType("text/plain");
/* 120 */       sresponse.flushBuffer();
/*     */     }
/* 122 */     catch (Exception exception) {}
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 127 */     Vector selections = MilestoneHelper.getSelectionsForReport(context);
/*     */ 
/*     */     
/*     */     try {
/* 131 */       HttpServletResponse sresponse = context.getResponse();
/* 132 */       context.putDelivery("status", new String("start_report"));
/* 133 */       context.putDelivery("percent", new String("10"));
/* 134 */       context.includeJSP("status.jsp", "hiddenFrame");
/* 135 */       sresponse.setContentType("text/plain");
/* 136 */       sresponse.flushBuffer();
/*     */     }
/* 138 */     catch (Exception exception) {}
/*     */ 
/*     */ 
/*     */     
/* 142 */     int numSelections = selections.size();
/*     */ 
/*     */     
/* 145 */     MilestoneHelper.setSelectionSorting(selections, 12);
/* 146 */     Collections.sort(selections);
/*     */ 
/*     */     
/* 149 */     MilestoneHelper.setSelectionSorting(selections, 4);
/* 150 */     Collections.sort(selections);
/*     */ 
/*     */     
/* 153 */     MilestoneHelper.setSelectionSorting(selections, 3);
/* 154 */     Collections.sort(selections);
/*     */ 
/*     */     
/* 157 */     MilestoneHelper.setSelectionSorting(selections, 9);
/* 158 */     Collections.sort(selections);
/*     */ 
/*     */     
/* 161 */     MilestoneHelper.setSelectionSorting(selections, 7);
/* 162 */     Collections.sort(selections);
/*     */ 
/*     */     
/* 165 */     MilestoneHelper.setSelectionSorting(selections, 8);
/* 166 */     Collections.sort(selections);
/*     */ 
/*     */     
/* 169 */     MilestoneHelper.setSelectionSorting(selections, 6);
/* 170 */     Collections.sort(selections);
/*     */ 
/*     */     
/* 173 */     MilestoneHelper.setSelectionSorting(selections, 1);
/* 174 */     Collections.sort(selections);
/*     */ 
/*     */     
/* 177 */     MilestoneHelper.setSelectionSorting(selections, 10);
/* 178 */     Collections.sort(selections);
/*     */ 
/*     */     
/* 181 */     if (numSelections == 0) {
/* 182 */       return null;
/*     */     }
/*     */     
/* 185 */     StyleSheet[] sheets = new StyleSheet[numSelections];
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 190 */       for (int i = 0; i < numSelections; i++)
/*     */       {
/* 192 */         InputStream input = new FileInputStream(String.valueOf(reportPath) + "\\ent_prod_status.xml");
/* 193 */         XStyleSheet report = (XStyleSheet)Builder.getBuilder(1, input).read(null);
/*     */ 
/*     */         
/* 196 */         SeparatorElement topSeparator = (SeparatorElement)report.getElement("separator_top");
/* 197 */         SeparatorElement bottomHeaderSeparator = (SeparatorElement)report.getElement("separator_bottom_header");
/* 198 */         SeparatorElement bottomSeparator = (SeparatorElement)report.getElement("separator_bottom");
/* 199 */         if (topSeparator != null) {
/*     */           
/* 201 */           topSeparator.setStyle(separatorLineStyle);
/* 202 */           topSeparator.setForeground(separatorLineColor);
/*     */         } 
/* 204 */         if (bottomHeaderSeparator != null) {
/*     */           
/* 206 */           bottomHeaderSeparator.setStyle(separatorLineStyle);
/* 207 */           bottomHeaderSeparator.setForeground(separatorLineColor);
/*     */         } 
/* 209 */         if (bottomSeparator != null) {
/*     */           
/* 211 */           bottomSeparator.setStyle(separatorLineStyle);
/* 212 */           bottomSeparator.setForeground(separatorLineColor);
/*     */         } 
/*     */ 
/*     */         
/* 216 */         Form reportForm = (Form)context.getSessionValue("reportForm");
/*     */         
/* 218 */         Calendar beginStDate = (reportForm.getStringValue("beginDate") != null && 
/* 219 */           reportForm.getStringValue("beginDate").length() > 0) ? 
/* 220 */           MilestoneHelper.getDate(reportForm.getStringValue("beginDate")) : null;
/*     */         
/* 222 */         Calendar endStDate = (reportForm.getStringValue("endDate") != null && 
/* 223 */           reportForm.getStringValue("endDate").length() > 0) ? 
/* 224 */           MilestoneHelper.getDate(reportForm.getStringValue("endDate")) : null;
/*     */         
/* 226 */         report.setElement("startdate", MilestoneHelper.getFormatedDate(beginStDate));
/* 227 */         report.setElement("enddate", MilestoneHelper.getFormatedDate(endStDate));
/*     */ 
/*     */         
/* 230 */         SimpleDateFormat formatter = new SimpleDateFormat("MMMM d, yyyy");
/* 231 */         String todayLong = formatter.format(new Date());
/* 232 */         report.setElement("bottomdate", todayLong);
/*     */ 
/*     */         
/* 235 */         String strUmlKey = (reportForm.getStringValue("umlKeyTask") != null) ? reportForm.getStringValue("umlKeyTask") : "";
/*     */ 
/*     */         
/* 238 */         Selection sel = (Selection)selections.elementAt(i);
/* 239 */         SelectionManager.getInstance().getSelectionManufacturingSubDetail(sel);
/*     */         
/* 241 */         if (sel != null) {
/*     */ 
/*     */           
/* 244 */           String selFamily = (sel.getFamily() != null && sel.getFamily().getName() != null) ? 
/* 245 */             sel.getFamily().getName() : "";
/*     */ 
/*     */           
/* 248 */           String selCompany = (sel.getCompany() != null && sel.getCompany().getName() != null) ? 
/* 249 */             sel.getCompany().getName() : "";
/*     */ 
/*     */           
/* 252 */           int region = (sel.getEnvironment() != null && sel.getEnvironment().getDistribution() >= 0) ? sel.getEnvironment().getDistribution() : -1;
/* 253 */           String selRegion = "";
/*     */           
/* 255 */           if (region == 0) {
/* 256 */             selRegion = "West";
/* 257 */           } else if (region == 1) {
/* 258 */             selRegion = "East";
/*     */           } 
/*     */           
/* 261 */           String selProject = (sel.getProjectID() != null) ? sel.getProjectID() : "";
/*     */ 
/*     */           
/* 264 */           String selPD = sel.getPressAndDistribution() ? "Yes" : "";
/*     */ 
/*     */           
/* 267 */           String selDivision = (sel.getDivision() != null && sel.getDivision().getName() != null) ? 
/* 268 */             sel.getDivision().getName() : "";
/*     */ 
/*     */ 
/*     */           
/* 272 */           String selLabel = sel.getImprint();
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 277 */           String selReleasingFamily = ReleasingFamily.getName(sel.getReleaseFamilyId());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 283 */           String selId = "";
/* 284 */           if (SelectionManager.getLookupObjectValue(sel.getPrefixID()).equals("")) {
/* 285 */             selId = sel.getSelectionNo();
/*     */           } else {
/* 287 */             selId = String.valueOf(SelectionManager.getLookupObjectValue(sel.getPrefixID())) + ' ' + sel.getSelectionNo();
/*     */           } 
/*     */ 
/*     */           
/* 291 */           String selUpc = (sel.getUpc() != null) ? sel.getUpc() : "";
/*     */ 
/*     */           
/* 294 */           selUpc = MilestoneHelper_2.getRMSReportFormat(selUpc, "UPC", sel.getIsDigital());
/*     */ 
/*     */           
/* 297 */           String selConfig = (sel.getSelectionSubConfig() != null && sel.getSelectionSubConfig().getSelectionSubConfigurationAbbreviation() != null) ? 
/* 298 */             sel.getSelectionSubConfig().getSelectionSubConfigurationAbbreviation() : "";
/*     */ 
/*     */ 
/*     */           
/* 302 */           String selTerritory = (sel.getSelectionTerritory() != null) ? sel.getSelectionTerritory() : "";
/*     */ 
/*     */           
/* 305 */           String selStreetDate = "";
/* 306 */           selStreetDate = MilestoneHelper.getFormatedDate(sel.getStreetDate());
/*     */ 
/*     */ 
/*     */           
/* 310 */           String statusString = "";
/* 311 */           SelectionStatus status = sel.getSelectionStatus();
/*     */           
/* 313 */           if (status != null) {
/* 314 */             statusString = (status.getName() == null) ? "" : status.getName();
/*     */           }
/* 316 */           if (statusString.equalsIgnoreCase("TBS")) {
/*     */             
/* 318 */             selStreetDate = String.valueOf(statusString) + " " + selStreetDate;
/*     */           }
/* 320 */           else if (statusString.equalsIgnoreCase("In The Works")) {
/*     */             
/* 322 */             selStreetDate = "ITW " + selStreetDate;
/*     */           } 
/*     */           
/* 325 */           if (selStreetDate.equals("")) {
/* 326 */             selStreetDate = "No Street Date";
/*     */           }
/*     */ 
/*     */           
/* 330 */           String selArtist = (sel.getArtist() != null) ? sel.getArtist() : "";
/*     */ 
/*     */           
/* 333 */           String selTitle = (sel.getTitle() != null) ? sel.getTitle() : "";
/*     */           
/* 335 */           report.setElement("company", selCompany);
/* 336 */           report.setElement("project", selProject);
/* 337 */           report.setElement("p_and_d", selPD);
/*     */           
/* 339 */           report.setElement("label", String.valueOf(selReleasingFamily) + "/" + selLabel);
/* 340 */           report.setElement("selection_id", selId);
/* 341 */           report.setElement("upc", selUpc);
/* 342 */           report.setElement("config", selConfig);
/* 343 */           report.setElement("street_date", selStreetDate);
/* 344 */           report.setElement("artist", selArtist);
/* 345 */           report.setElement("title", selTitle);
/* 346 */           report.setElement("territory", selTerritory);
/*     */ 
/*     */ 
/*     */           
/* 350 */           Schedule schedule = sel.getSchedule();
/*     */           
/* 352 */           Vector scheduledTasks = new Vector();
/* 353 */           if (schedule != null) {
/*     */             
/* 355 */             scheduledTasks = schedule.getTasks();
/*     */ 
/*     */             
/* 358 */             if (scheduledTasks != null && strUmlKey.equalsIgnoreCase("label"))
/*     */             {
/* 360 */               scheduledTasks = MilestoneHelper.filterUmlTasks(scheduledTasks, false);
/*     */             }
/*     */             
/* 363 */             if (scheduledTasks != null && strUmlKey.equalsIgnoreCase("uml"))
/*     */             {
/* 365 */               scheduledTasks = MilestoneHelper.filterUmlTasks(scheduledTasks, true);
/*     */             }
/*     */             
/* 368 */             if (scheduledTasks == null) {
/* 369 */               scheduledTasks = new Vector();
/*     */             }
/*     */           } 
/*     */           
/* 373 */           int commentCount = MilestoneHelper.countTasksWithComments(scheduledTasks);
/*     */ 
/*     */ 
/*     */           
/* 377 */           DefaultTableLens table_contents = new DefaultTableLens(scheduledTasks.size() + commentCount + 1, 9);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 385 */           table_contents.setColAlignment(0, 1);
/* 386 */           table_contents.setColAlignment(1, 1);
/* 387 */           table_contents.setColAlignment(2, 2);
/* 388 */           table_contents.setColAlignment(3, 2);
/* 389 */           table_contents.setColAlignment(4, 4);
/* 390 */           table_contents.setColAlignment(6, 1);
/* 391 */           table_contents.setColAlignment(7, 12);
/*     */           
/* 393 */           table_contents.setColBorderColor(Color.black);
/* 394 */           table_contents.setRowBorderColor(Color.black);
/* 395 */           table_contents.setRowBorder(4097);
/* 396 */           table_contents.setColBorder(4097);
/*     */ 
/*     */ 
/*     */           
/* 400 */           table_contents.setColWidth(0, 35);
/* 401 */           table_contents.setLineWrap(0, 0, false);
/* 402 */           table_contents.setLineWrap(0, 1, false);
/* 403 */           table_contents.setColWidth(1, 82);
/* 404 */           table_contents.setColWidth(2, 55);
/* 405 */           table_contents.setColWidth(3, 45);
/*     */           
/* 407 */           table_contents.setColWidth(4, 45);
/* 408 */           table_contents.setColWidth(5, 1);
/*     */           
/* 410 */           table_contents.setColWidth(6, 4);
/*     */           
/* 412 */           table_contents.setColWidth(7, 52);
/* 413 */           table_contents.setColWidth(8, 1);
/*     */ 
/*     */ 
/*     */           
/* 417 */           table_contents.setHeaderRowCount(1);
/* 418 */           table_contents.setObject(0, 0, "Description /");
/* 419 */           table_contents.setFont(0, 0, new Font("Arial", 1, 10));
/*     */           
/* 421 */           table_contents.setObject(0, 1, "Comments");
/* 422 */           table_contents.setFont(0, 1, new Font("Arial", 3, 10));
/*     */           
/* 424 */           table_contents.setObject(0, 2, "Task Owner");
/* 425 */           table_contents.setAlignment(0, 2, 1);
/*     */           
/* 427 */           table_contents.setFont(0, 2, new Font("Arial", 1, 10));
/* 428 */           table_contents.setObject(0, 3, "Wks To Rel");
/* 429 */           table_contents.setFont(0, 3, new Font("Arial", 1, 10));
/*     */           
/* 431 */           table_contents.setObject(0, 4, "Date Due");
/* 432 */           table_contents.setFont(0, 4, new Font("Arial", 1, 10));
/* 433 */           table_contents.setSpan(0, 4, new Dimension(2, 1));
/*     */           
/* 435 */           table_contents.setObject(0, 7, "Complete");
/* 436 */           table_contents.setSpan(0, 7, new Dimension(2, 1));
/* 437 */           table_contents.setFont(0, 7, new Font("Arial", 1, 10));
/*     */ 
/*     */           
/* 440 */           table_contents.setRowBorder(-1, 0);
/*     */           
/* 442 */           table_contents.setRowBorder(0, tableHeaderLineStyle);
/* 443 */           table_contents.setRowBorderColor(0, tableHeaderLineColor);
/*     */           
/* 445 */           int nextRow = 1;
/*     */           
/* 447 */           boolean shade = true;
/*     */ 
/*     */           
/* 450 */           for (int j = 0; j < scheduledTasks.size(); j++) {
/*     */             String dueDateHolidayFlg, taskDueDate;
/* 452 */             ScheduledTask task = (ScheduledTask)scheduledTasks.get(j);
/*     */             
/* 454 */             boolean taskHasComment = false;
/*     */ 
/*     */             
/* 457 */             String taskName = (task.getName() != null) ? task.getName().trim() : "";
/*     */ 
/*     */             
/* 460 */             String taskComments = (task.getComments() != null) ? task.getComments().trim() : "";
/*     */ 
/*     */             
/* 463 */             String taskOwner = (task.getOwner() != null && task.getOwner().getName() != null) ? 
/* 464 */               task.getOwner().getName() : "";
/*     */ 
/*     */             
/* 467 */             if (!taskComments.equals("") && !taskComments.equalsIgnoreCase("null")) {
/* 468 */               taskHasComment = true;
/*     */             }
/*     */             
/* 471 */             String taskDepartment = (task.getDepartment() != null && !task.getDepartment().equalsIgnoreCase("-1") && !task.getDepartment().equalsIgnoreCase("null")) ? 
/* 472 */               task.getDepartment() : "";
/*     */ 
/*     */             
/* 475 */             String wksToReleaseString = "";
/* 476 */             if (task.getWeeksToRelease() > 0) {
/*     */               
/* 478 */               wksToReleaseString = (task.getDayOfTheWeek() != null) ? task.getDayOfTheWeek().getDay() : "";
/* 479 */               wksToReleaseString = String.valueOf(wksToReleaseString) + " " + task.getWeeksToRelease();
/*     */             } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 486 */             if (task.getDueDate() != null) {
/*     */               
/* 488 */               taskDueDate = MilestoneHelper.getFormatedDate(task.getDueDate());
/* 489 */               dueDateHolidayFlg = MilestoneHelper.getDayType(sel.getCalendarGroup(), task.getDueDate());
/*     */             }
/*     */             else {
/*     */               
/* 493 */               taskDueDate = "";
/* 494 */               dueDateHolidayFlg = "";
/*     */             } 
/*     */ 
/*     */             
/* 498 */             String taskStatus = task.getScheduledTaskStatus();
/*     */ 
/*     */             
/* 501 */             String taskCompletionDate = (task.getCompletionDate() != null) ? 
/* 502 */               MilestoneHelper.getFormatedDate(task.getCompletionDate()) : "";
/*     */             
/* 504 */             if (taskStatus.equalsIgnoreCase("N/A")) {
/* 505 */               taskCompletionDate = "N/A";
/*     */             }
/*     */ 
/*     */             
/* 509 */             table_contents.setObject(nextRow, 0, taskName);
/* 510 */             table_contents.setSpan(nextRow, 0, new Dimension(2, 1));
/* 511 */             table_contents.setObject(nextRow, 2, taskOwner);
/* 512 */             table_contents.setAlignment(nextRow, 2, 1);
/* 513 */             table_contents.setObject(nextRow, 3, wksToReleaseString);
/* 514 */             table_contents.setObject(nextRow, 4, taskDueDate);
/* 515 */             table_contents.setObject(nextRow, 6, dueDateHolidayFlg);
/* 516 */             table_contents.setObject(nextRow, 7, taskCompletionDate);
/*     */ 
/*     */             
/* 519 */             if (!shade) {
/* 520 */               table_contents.setFont(nextRow, 1, new Font("Arial", 0, 8));
/*     */             } else {
/* 522 */               table_contents.setFont(nextRow, 1, new Font("Arial", 1, 8));
/*     */ 
/*     */ 
/*     */ 
/*     */               
/* 527 */               table_contents.setRowBackground(nextRow, Color.lightGray);
/*     */             } 
/*     */ 
/*     */ 
/*     */             
/* 532 */             table_contents.setRowFont(nextRow, new Font("Arial", 0, 9));
/*     */ 
/*     */             
/* 535 */             if (dueDateHolidayFlg != "") {
/*     */               
/* 537 */               Font holidayFont = new Font("Arial", 3, 9);
/* 538 */               table_contents.setFont(nextRow, 4, holidayFont);
/* 539 */               table_contents.setFont(nextRow, 6, holidayFont);
/*     */             } 
/*     */ 
/*     */             
/* 543 */             if (taskHasComment) {
/*     */               
/* 545 */               nextRow++;
/* 546 */               table_contents.setSpan(nextRow, 0, new Dimension(2, 1));
/* 547 */               table_contents.setObject(nextRow, 0, taskComments);
/* 548 */               table_contents.setRowFont(nextRow, new Font("Arial", 2, 9));
/* 549 */               table_contents.setRowBorder(nextRow - 1, 0);
/* 550 */               if (!shade) {
/* 551 */                 table_contents.setFont(nextRow, 1, new Font("Arial", 0, 8));
/*     */               } else {
/* 553 */                 table_contents.setFont(nextRow, 1, new Font("Arial", 1, 8));
/*     */ 
/*     */                 
/* 556 */                 table_contents.setRowBackground(nextRow, Color.lightGray);
/*     */               } 
/*     */             } 
/*     */ 
/*     */ 
/*     */             
/* 562 */             table_contents.setRowBorder(nextRow, tableRowLineStyle);
/*     */             
/* 564 */             table_contents.setRowBorderColor(nextRow, Color.white);
/*     */             
/* 566 */             nextRow++;
/*     */             
/* 568 */             shade = !shade;
/*     */           } 
/*     */ 
/*     */ 
/*     */           
/* 573 */           report.setElement("table", table_contents);
/*     */         } 
/*     */ 
/*     */         
/* 577 */         sheets[i] = report;
/*     */       }
/*     */     
/*     */     }
/* 581 */     catch (Exception e) {
/*     */       
/* 583 */       System.out.println(">>>>>>>>ReportHandler.fillEntProdStatusForPrint(): exception: " + e);
/*     */     } 
/*     */ 
/*     */     
/* 587 */     return new CompositeSheet(sheets);
/*     */   }
/*     */ }


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\EntProdStatusForPrintSubHandler.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */