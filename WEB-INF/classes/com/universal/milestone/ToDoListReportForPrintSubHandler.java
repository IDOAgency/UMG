/*     */ package WEB-INF.classes.com.universal.milestone;
/*     */ 
/*     */ import com.techempower.ComponentLog;
/*     */ import com.techempower.gemini.Context;
/*     */ import com.techempower.gemini.GeminiApplication;
/*     */ import com.universal.milestone.Form;
/*     */ import com.universal.milestone.MilestoneHelper;
/*     */ import com.universal.milestone.Schedule;
/*     */ import com.universal.milestone.ScheduledTask;
/*     */ import com.universal.milestone.SecureHandler;
/*     */ import com.universal.milestone.Selection;
/*     */ import com.universal.milestone.SelectionManager;
/*     */ import com.universal.milestone.ToDoListReportForPrintSubHandler;
/*     */ import inetsoft.report.XStyleSheet;
/*     */ import inetsoft.report.lens.DefaultTableLens;
/*     */ import java.awt.Color;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Font;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.Calendar;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ToDoListReportForPrintSubHandler
/*     */   extends SecureHandler
/*     */ {
/*     */   public static final String COMPONENT_CODE = "hIga";
/*     */   public GeminiApplication application;
/*     */   public ComponentLog log;
/*     */   
/*     */   public ToDoListReportForPrintSubHandler(GeminiApplication application) {
/*  72 */     this.application = application;
/*  73 */     this.log = application.getLog("hIga");
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
/*     */ 
/*     */ 
/*     */   
/*     */   protected static void fillEntToDoListForPrint(XStyleSheet report, Context context) {
/*     */     try {
/*  96 */       HttpServletResponse sresponse = context.getResponse();
/*  97 */       context.putDelivery("status", new String("start_gathering"));
/*  98 */       context.includeJSP("status.jsp", "hiddenFrame");
/*  99 */       sresponse.setContentType("text/plain");
/* 100 */       sresponse.flushBuffer();
/*     */     }
/* 102 */     catch (Exception exception) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 108 */     Vector selections = MilestoneHelper.getSelectionsForReport(context);
/*     */ 
/*     */     
/* 111 */     Form reportForm = (Form)context.getSessionValue("reportForm");
/* 112 */     String strUmlKey = (reportForm.getStringValue("umlKeyTask") != null) ? reportForm.getStringValue("umlKeyTask") : "";
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 118 */       HttpServletResponse sresponse = context.getResponse();
/* 119 */       context.putDelivery("status", new String("start_report"));
/* 120 */       context.putDelivery("percent", new String("10"));
/* 121 */       context.includeJSP("status.jsp", "hiddenFrame");
/* 122 */       sresponse.setContentType("text/plain");
/* 123 */       sresponse.flushBuffer();
/*     */     }
/* 125 */     catch (Exception exception) {}
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
/* 136 */       reportForm = (Form)context.getSessionValue("reportForm");
/*     */       
/* 138 */       Calendar beginStDate = (reportForm.getStringValue("beginDueDate") != null && 
/* 139 */         reportForm.getStringValue("beginDueDate").length() > 0) ? 
/* 140 */         MilestoneHelper.getDate(reportForm.getStringValue("beginDueDate")) : null;
/*     */       
/* 142 */       Calendar endStDate = (reportForm.getStringValue("endDueDate") != null && 
/* 143 */         reportForm.getStringValue("endDueDate").length() > 0) ? 
/* 144 */         MilestoneHelper.getDate(reportForm.getStringValue("endDueDate")) : null;
/*     */       
/* 146 */       report.setElement("crs_startdate", MilestoneHelper.getFormatedDate(beginStDate));
/* 147 */       report.setElement("crs_enddate", MilestoneHelper.getFormatedDate(endStDate));
/*     */ 
/*     */       
/* 150 */       SimpleDateFormat formatter = new SimpleDateFormat("MMMM d, yyyy");
/* 151 */       String todayLong = formatter.format(new Date());
/* 152 */       report.setElement("crs_bottomdate", todayLong);
/*     */       
/* 154 */       if (beginStDate == null) {
/* 155 */         beginStDate = MilestoneHelper.getDate("9/9/99");
/*     */       }
/*     */       
/* 158 */       if (endStDate == null) {
/* 159 */         endStDate = MilestoneHelper.getDate("9/9/09");
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 167 */       int numSelections = selections.size() + selections.size() / 100 + 1;
/*     */       
/* 169 */       int numExtraRows = 0;
/*     */ 
/*     */       
/* 172 */       for (int i = 0; i < selections.size(); i++) {
/*     */         
/* 174 */         Selection sel = (Selection)selections.elementAt(i);
/*     */         
/* 176 */         Schedule schedule = sel.getSchedule();
/* 177 */         Vector tasks = (schedule != null) ? schedule.getTasks() : null;
/* 178 */         ScheduledTask task = null;
/* 179 */         if (tasks != null)
/*     */         {
/* 181 */           for (int j = 0; j < tasks.size(); j++) {
/*     */             
/* 183 */             task = (ScheduledTask)tasks.get(j);
/*     */ 
/*     */ 
/*     */             
/* 187 */             if (strUmlKey.equalsIgnoreCase("") || strUmlKey.trim().equalsIgnoreCase("all") || (
/* 188 */               strUmlKey.equalsIgnoreCase("UML") ? 
/* 189 */               !task.getOwner().getName().trim().equalsIgnoreCase("UML") : 
/*     */ 
/*     */               
/* 192 */               task.getOwner().getName().trim().equalsIgnoreCase("UML")))
/*     */             {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */               
/* 201 */               numExtraRows++;
/*     */             }
/*     */           } 
/*     */         }
/*     */       } 
/*     */ 
/*     */       
/* 208 */       int numRows = numSelections;
/*     */ 
/*     */ 
/*     */       
/* 212 */       DefaultTableLens table_contents = new DefaultTableLens(numRows, 10);
/* 213 */       table_contents.setRowAutoSize(true);
/* 214 */       table_contents.setColBorder(0);
/* 215 */       table_contents.setRowBorder(0);
/* 216 */       table_contents.setRowBorderColor(Color.white);
/*     */       
/* 218 */       table_contents.setColWidth(0, 60);
/* 219 */       table_contents.setColWidth(1, 100);
/* 220 */       table_contents.setColWidth(2, 150);
/* 221 */       table_contents.setColWidth(3, 150);
/* 222 */       table_contents.setColWidth(4, 180);
/* 223 */       table_contents.setColWidth(5, 60);
/* 224 */       table_contents.setColWidth(6, 45);
/* 225 */       table_contents.setColWidth(7, 35);
/* 226 */       table_contents.setColWidth(8, 150);
/*     */ 
/*     */       
/* 229 */       table_contents.setAlignment(0, 0, 33);
/* 230 */       table_contents.setAlignment(0, 1, 33);
/* 231 */       table_contents.setAlignment(0, 2, 33);
/* 232 */       table_contents.setAlignment(0, 3, 33);
/* 233 */       table_contents.setAlignment(0, 4, 33);
/* 234 */       table_contents.setAlignment(0, 5, 33);
/* 235 */       table_contents.setAlignment(0, 6, 33);
/* 236 */       table_contents.setAlignment(0, 7, 33);
/* 237 */       table_contents.setAlignment(0, 8, 33);
/*     */ 
/*     */       
/* 240 */       table_contents.setHeaderRowCount(1);
/*     */       
/* 242 */       table_contents.setRowBorder(-1, 266240);
/* 243 */       table_contents.setRowBorder(0, 266240);
/* 244 */       table_contents.setRowBorderColor(0, Color.black);
/*     */ 
/*     */       
/* 247 */       table_contents.setRowAlignment(0, 32);
/*     */ 
/*     */       
/* 250 */       table_contents.setObject(0, 0, "Street \nDate");
/* 251 */       table_contents.setObject(0, 1, "Local \nProd #");
/* 252 */       table_contents.setObject(0, 2, "Artist");
/* 253 */       table_contents.setObject(0, 3, "Title");
/* 254 */       table_contents.setObject(0, 4, "Task Name/Comment");
/* 255 */       table_contents.setObject(0, 5, "Dept");
/* 256 */       table_contents.setObject(0, 6, "Task\nDate Due");
/* 257 */       table_contents.setSpan(0, 6, new Dimension(2, 1));
/* 258 */       table_contents.setObject(0, 8, "Label Contact");
/*     */ 
/*     */       
/* 261 */       table_contents.setRowFont(0, new Font("Arial", 3, 11));
/*     */ 
/*     */ 
/*     */       
/* 265 */       int nextRow = 1;
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
/* 277 */       int count = 2;
/* 278 */       int numRec = selections.size();
/* 279 */       int chunkSize = numRec / 10;
/*     */ 
/*     */       
/* 282 */       Color curColor = Color.white;
/* 283 */       for (int i = 0; i < selections.size(); i++) {
/*     */ 
/*     */         
/*     */         try {
/*     */           
/* 288 */           int myPercent = i / chunkSize;
/* 289 */           if (myPercent > 1 && myPercent < 10)
/* 290 */             count = myPercent; 
/* 291 */           HttpServletResponse sresponse = context.getResponse();
/* 292 */           context.putDelivery("status", new String("start_report"));
/* 293 */           context.putDelivery("percent", new String(String.valueOf(count * 10)));
/* 294 */           context.includeJSP("status.jsp", "hiddenFrame");
/* 295 */           sresponse.setContentType("text/plain");
/* 296 */           sresponse.flushBuffer();
/*     */         }
/* 298 */         catch (Exception exception) {}
/*     */ 
/*     */ 
/*     */         
/* 302 */         Selection sel = (Selection)selections.elementAt(i);
/*     */ 
/*     */ 
/*     */         
/* 306 */         String selectionNo = SelectionManager.getLookupObjectValue(sel.getPrefixID());
/* 307 */         if (selectionNo == null)
/* 308 */           selectionNo = ""; 
/* 309 */         selectionNo = String.valueOf(selectionNo) + sel.getSelectionNo();
/*     */ 
/*     */ 
/*     */         
/* 313 */         String comment = (sel.getSelectionComments() != null) ? sel.getSelectionComments() : "";
/*     */         
/* 315 */         String streetDate = MilestoneHelper.getFormatedDate(sel.getStreetDate());
/*     */         
/* 317 */         String status = (sel.getSelectionStatus() != null && sel.getSelectionStatus().getName() != null) ? 
/* 318 */           sel.getSelectionStatus().getName() : "";
/*     */         
/* 320 */         if (status.equalsIgnoreCase("TBS")) {
/* 321 */           streetDate = "TBS " + streetDate;
/*     */         }
/* 323 */         else if (status.equalsIgnoreCase("In The Works")) {
/* 324 */           streetDate = "ITW " + streetDate;
/*     */         } 
/*     */         
/* 327 */         Schedule schedule = sel.getSchedule();
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
/*     */ 
/*     */         
/* 365 */         if (curColor == Color.white) {
/* 366 */           table_contents.setRowBackground(nextRow, Color.lightGray);
/* 367 */           curColor = Color.gray;
/*     */         } else {
/* 369 */           table_contents.setRowBackground(nextRow, Color.white);
/* 370 */           curColor = Color.white;
/*     */         } 
/*     */         
/* 373 */         String dueDate = MilestoneHelper.getFormatedDate(sel.getDueDate());
/*     */ 
/*     */         
/* 376 */         String dueDateHolidayFlg = MilestoneHelper.getDayType(sel.getCalendarGroup(), sel.getDueDate());
/*     */ 
/*     */         
/* 379 */         if (sel.getTaskName().length() > 25 || sel.getArtist().length() > 25 || sel.getTitle().length() > 25) {
/* 380 */           table_contents.setRowHeight(nextRow, 25);
/*     */         }
/* 382 */         String stdate_temp = (streetDate != null) ? streetDate : "";
/* 383 */         table_contents.setObject(nextRow, 0, stdate_temp);
/* 384 */         table_contents.setObject(nextRow, 2, sel.getArtist());
/* 385 */         table_contents.setObject(nextRow, 5, sel.getDepartment());
/* 386 */         table_contents.setObject(nextRow, 3, sel.getTitle());
/* 387 */         table_contents.setObject(nextRow, 1, selectionNo);
/* 388 */         table_contents.setObject(nextRow, 4, sel.getTaskName());
/* 389 */         table_contents.setObject(nextRow, 6, dueDate);
/* 390 */         table_contents.setObject(nextRow, 7, dueDateHolidayFlg);
/*     */         
/* 392 */         String name = "";
/* 393 */         name = (sel.getLabelContact() != null) ? sel.getLabelContact().getName() : "";
/* 394 */         table_contents.setObject(nextRow, 8, name);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 403 */         if (dueDateHolidayFlg != "") {
/*     */           
/* 405 */           Font holidayFont = new Font("Arial", 3, 8);
/* 406 */           table_contents.setFont(nextRow, 6, holidayFont);
/* 407 */           table_contents.setFont(nextRow, 7, holidayFont);
/*     */         } 
/*     */         
/* 410 */         table_contents.setAlignment(nextRow, 3, 1);
/*     */         
/* 412 */         table_contents.setRowBorder(nextRow, 4097);
/*     */ 
/*     */ 
/*     */         
/* 416 */         table_contents.setRowBorderColor(nextRow, Color.lightGray);
/*     */         
/* 418 */         table_contents.setAlignment(nextRow, 0, 9);
/* 419 */         table_contents.setAlignment(nextRow, 1, 9);
/* 420 */         table_contents.setAlignment(nextRow, 2, 9);
/* 421 */         table_contents.setAlignment(nextRow, 3, 9);
/*     */         
/* 423 */         table_contents.setAlignment(nextRow, 4, 8);
/* 424 */         table_contents.setAlignment(nextRow, 5, 9);
/* 425 */         table_contents.setAlignment(nextRow, 6, 9);
/* 426 */         table_contents.setAlignment(nextRow, 7, 9);
/* 427 */         table_contents.setAlignment(nextRow, 8, 9);
/* 428 */         nextRow++;
/*     */       } 
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
/* 443 */       report.setElement("table_colheaders", table_contents);
/*     */     
/*     */     }
/* 446 */     catch (Exception e) {
/*     */       
/* 448 */       System.out.println(">>>>>>>>ReportHandler.fillEntRelScheduleForPrint(): exception: " + e);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\ToDoListReportForPrintSubHandler.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */