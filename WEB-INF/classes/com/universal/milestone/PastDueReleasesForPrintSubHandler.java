/*     */ package WEB-INF.classes.com.universal.milestone;
/*     */ 
/*     */ import com.techempower.ComponentLog;
/*     */ import com.techempower.gemini.Context;
/*     */ import com.techempower.gemini.GeminiApplication;
/*     */ import com.universal.milestone.Form;
/*     */ import com.universal.milestone.MilestoneHelper;
/*     */ import com.universal.milestone.MilestoneHelper_2;
/*     */ import com.universal.milestone.PastDueReleasesForPrintSubHandler;
/*     */ import com.universal.milestone.ReleasingFamily;
/*     */ import com.universal.milestone.Schedule;
/*     */ import com.universal.milestone.ScheduledTask;
/*     */ import com.universal.milestone.SecureHandler;
/*     */ import com.universal.milestone.Selection;
/*     */ import com.universal.milestone.SelectionManager;
/*     */ import inetsoft.report.XStyleSheet;
/*     */ import inetsoft.report.lens.DefaultTableLens;
/*     */ import java.awt.Color;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Font;
/*     */ import java.awt.Insets;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PastDueReleasesForPrintSubHandler
/*     */   extends SecureHandler
/*     */ {
/*     */   public static final String COMPONENT_CODE = "hCProd";
/*     */   public GeminiApplication application;
/*     */   public ComponentLog log;
/*     */   
/*     */   public PastDueReleasesForPrintSubHandler(GeminiApplication application) {
/*  70 */     this.application = application;
/*  71 */     this.log = application.getLog("hCProd");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  79 */   public String getDescription() { return "Sub Report"; }
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
/*     */   protected static void fillPastDueReleasesForPrint(XStyleSheet report, Context context) {
/*  91 */     ComponentLog log = context.getApplication().getLog("hCProd");
/*     */ 
/*     */     
/*     */     try {
/*  95 */       HttpServletResponse sresponse = context.getResponse();
/*  96 */       context.putDelivery("status", new String("start_gathering"));
/*  97 */       context.includeJSP("status.jsp", "hiddenFrame");
/*  98 */       sresponse.setContentType("text/plain");
/*  99 */       sresponse.flushBuffer();
/*     */     }
/* 101 */     catch (Exception exception) {}
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 106 */     Vector selections = MilestoneHelper.getSelectionsForReport(context);
/*     */ 
/*     */     
/*     */     try {
/* 110 */       HttpServletResponse sresponse = context.getResponse();
/* 111 */       context.putDelivery("status", new String("start_report"));
/* 112 */       context.putDelivery("percent", new String("10"));
/* 113 */       context.includeJSP("status.jsp", "hiddenFrame");
/* 114 */       sresponse.setContentType("text/plain");
/* 115 */       sresponse.flushBuffer();
/*     */     }
/* 117 */     catch (Exception exception) {}
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
/* 128 */       Form reportForm = (Form)context.getSessionValue("reportForm");
/*     */       
/* 130 */       Calendar beginStDate = (reportForm.getStringValue("beginDate") != null && 
/* 131 */         reportForm.getStringValue("beginDate").length() > 0) ? 
/* 132 */         MilestoneHelper.getDate(reportForm.getStringValue("beginDate")) : null;
/*     */       
/* 134 */       Calendar endStDate = (reportForm.getStringValue("endDate") != null && 
/* 135 */         reportForm.getStringValue("endDate").length() > 0) ? 
/* 136 */         MilestoneHelper.getDate(reportForm.getStringValue("endDate")) : null;
/*     */       
/* 138 */       report.setElement("crs_startdate", MilestoneHelper.getFormatedDate(beginStDate));
/* 139 */       report.setElement("crs_enddate", MilestoneHelper.getFormatedDate(endStDate));
/*     */ 
/*     */       
/* 142 */       SimpleDateFormat formatter = new SimpleDateFormat("MMMM d, yyyy");
/* 143 */       String todayLong = formatter.format(new Date());
/* 144 */       report.setElement("crs_bottomdate", todayLong);
/*     */       
/* 146 */       Calendar beginEffectiveDate = (reportForm.getStringValue("beginEffectiveDate") != null && reportForm.getStringValue("beginEffectiveDate").length() > 0) ? MilestoneHelper.getDate(reportForm.getStringValue("beginEffectiveDate")) : null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 152 */       if (beginEffectiveDate != null) {
/*     */         
/* 154 */         beginEffectiveDate.set(11, 0);
/* 155 */         beginEffectiveDate.set(12, 0);
/* 156 */         beginEffectiveDate.set(13, 0);
/* 157 */         beginEffectiveDate.set(14, 0);
/*     */       } 
/*     */       
/* 160 */       Calendar endEffectiveDate = (reportForm.getStringValue("endEffectiveDate") != null && reportForm.getStringValue("endEffectiveDate").length() > 0) ? MilestoneHelper.getDate(reportForm.getStringValue("endEffectiveDate")) : null;
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 165 */       if (endEffectiveDate != null) {
/*     */         
/* 167 */         endEffectiveDate.set(11, 23);
/* 168 */         endEffectiveDate.set(12, 59);
/* 169 */         endEffectiveDate.set(13, 59);
/* 170 */         endEffectiveDate.set(14, 999);
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 175 */       Hashtable selTable = MilestoneHelper.groupSelectionsByFamilyAndCompany(selections);
/* 176 */       Enumeration families = selTable.keys();
/* 177 */       Vector familyVector = new Vector();
/* 178 */       while (families.hasMoreElements()) {
/* 179 */         familyVector.addElement(families.nextElement());
/*     */       }
/*     */       
/* 182 */       int numCompanies = 0;
/* 183 */       for (int i = 0; i < familyVector.size(); i++) {
/*     */         
/* 185 */         String familyName = (familyVector.elementAt(i) != null) ? (String)familyVector.elementAt(i) : "";
/* 186 */         Hashtable companyTable = (Hashtable)selTable.get(familyName);
/* 187 */         if (companyTable != null)
/*     */         {
/* 189 */           numCompanies += companyTable.size();
/*     */         }
/*     */       } 
/*     */ 
/*     */       
/* 194 */       int numExtraRows = 1 + familyVector.size() * 3 + numCompanies;
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 199 */       int kk = 0;
/* 200 */       int size = selections.size();
/* 201 */       Vector selection1 = (Vector)selections.clone();
/* 202 */       for (int ii = 0; ii < size; ii++) {
/*     */         
/* 204 */         Selection sel1 = (Selection)selection1.elementAt(ii);
/* 205 */         Selection sel = SelectionManager.getInstance().getSelectionAndSchedule(sel1.getSelectionID());
/*     */ 
/*     */         
/* 208 */         if (sel.getStreetDate() != null && 
/* 209 */           sel.getStreetDate().after(beginEffectiveDate)) {
/* 210 */           selections.remove(sel1);
/*     */         }
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
/* 226 */       int numSelections = selections.size();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 232 */       int numRows = numSelections + numExtraRows;
/*     */ 
/*     */ 
/*     */       
/* 236 */       DefaultTableLens table_contents = new DefaultTableLens(numRows, 10);
/*     */       
/* 238 */       table_contents.setColBorder(0);
/* 239 */       table_contents.setRowBorderColor(Color.white);
/*     */       
/* 241 */       table_contents.setColWidth(0, 190);
/* 242 */       table_contents.setColWidth(1, 180);
/* 243 */       table_contents.setColWidth(2, 100);
/* 244 */       table_contents.setColWidth(3, 150);
/* 245 */       table_contents.setColWidth(4, 175);
/* 246 */       table_contents.setColWidth(5, 150);
/* 247 */       table_contents.setColWidth(6, 5);
/* 248 */       table_contents.setColWidth(7, 80);
/* 249 */       table_contents.setColWidth(8, 80);
/* 250 */       table_contents.setColWidth(9, 180);
/*     */       
/* 252 */       table_contents.setAlignment(0, 0, 33);
/* 253 */       table_contents.setAlignment(0, 1, 34);
/* 254 */       table_contents.setAlignment(0, 2, 34);
/* 255 */       table_contents.setAlignment(0, 3, 34);
/* 256 */       table_contents.setAlignment(0, 4, 34);
/* 257 */       table_contents.setAlignment(0, 5, 34);
/* 258 */       table_contents.setAlignment(0, 6, 34);
/* 259 */       table_contents.setAlignment(0, 7, 34);
/* 260 */       table_contents.setAlignment(0, 8, 34);
/* 261 */       table_contents.setAlignment(0, 9, 33);
/*     */ 
/*     */ 
/*     */       
/* 265 */       table_contents.setHeaderRowCount(1);
/*     */       
/* 267 */       table_contents.setRowBorder(-1, 0);
/* 268 */       table_contents.setRowBorder(0, 266240);
/* 269 */       table_contents.setRowBorderColor(0, Color.black);
/*     */ 
/*     */       
/* 272 */       table_contents.setRowAlignment(0, 32);
/*     */       
/* 274 */       table_contents.setObject(0, 0, "Artist");
/* 275 */       table_contents.setObject(0, 1, "Title");
/*     */       
/* 277 */       table_contents.setObject(0, 3, "UPC");
/* 278 */       table_contents.setObject(0, 4, "Local\nProduct #");
/* 279 */       table_contents.setObject(0, 5, "Rel. Family/\nLabel");
/*     */       
/* 281 */       table_contents.setObject(0, 7, "Street/Ship\nDate");
/* 282 */       table_contents.setObject(0, 8, "All Tasks\nComp.");
/* 283 */       table_contents.setObject(0, 9, "Comments");
/* 284 */       table_contents.setRowInsets(0, new Insets(0, 0, 0, 0));
/* 285 */       table_contents.setRowFont(0, new Font("Arial", 3, 11));
/*     */ 
/*     */ 
/*     */       
/* 289 */       int nextRow = 1;
/*     */       
/* 291 */       for (int n = 0; n < familyVector.size(); n++) {
/*     */         
/* 293 */         String family = (String)familyVector.elementAt(n);
/* 294 */         String familyHeaderText = !family.trim().equals("") ? family : "Other";
/*     */ 
/*     */         
/* 297 */         table_contents.setObject(nextRow, 0, "");
/* 298 */         table_contents.setObject(nextRow, 1, "");
/* 299 */         table_contents.setObject(nextRow, 2, "");
/* 300 */         table_contents.setObject(nextRow, 3, "");
/* 301 */         table_contents.setObject(nextRow, 4, "");
/* 302 */         table_contents.setObject(nextRow, 5, "");
/* 303 */         table_contents.setObject(nextRow, 6, "");
/* 304 */         table_contents.setObject(nextRow, 7, "");
/* 305 */         table_contents.setObject(nextRow, 8, "");
/* 306 */         table_contents.setObject(nextRow, 9, "");
/* 307 */         table_contents.setRowInsets(nextRow, new Insets(0, 0, 0, 0));
/* 308 */         table_contents.setRowHeight(nextRow, 10);
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
/* 334 */         table_contents.setRowInsets(nextRow + 2, new Insets(0, 0, 0, 0));
/* 335 */         table_contents.setRowHeight(nextRow + 2, 5);
/*     */         
/* 337 */         nextRow += 3;
/*     */ 
/*     */         
/* 340 */         Hashtable companyTable = (Hashtable)selTable.get(family);
/* 341 */         if (companyTable != null) {
/*     */           
/* 343 */           Enumeration companies = companyTable.keys();
/*     */           
/* 345 */           Vector companiesVector = new Vector();
/*     */           
/* 347 */           while (companies.hasMoreElements())
/*     */           {
/* 349 */             companiesVector.add((String)companies.nextElement());
/*     */           }
/*     */           
/* 352 */           Vector sortedCompaniesVector = MilestoneHelper.sortStrings(companiesVector);
/*     */           
/* 354 */           for (int a = 0; a < sortedCompaniesVector.size(); a++) {
/*     */             
/* 356 */             String company = (String)sortedCompaniesVector.get(a);
/*     */ 
/*     */             
/* 359 */             table_contents.setSpan(nextRow, 0, new Dimension(10, 1));
/* 360 */             table_contents.setObject(nextRow, 0, company);
/*     */ 
/*     */             
/* 363 */             table_contents.setRowBorder(nextRow, 4097);
/* 364 */             table_contents.setRowBorderColor(nextRow, Color.black);
/*     */             
/* 366 */             table_contents.setRowFont(nextRow, new Font("Arial", 3, 14));
/* 367 */             table_contents.setRowBackground(nextRow, Color.black);
/* 368 */             table_contents.setRowForeground(nextRow, Color.white);
/*     */             
/* 370 */             nextRow++;
/*     */ 
/*     */             
/* 373 */             selections = (Vector)companyTable.get(company);
/*     */ 
/*     */             
/* 376 */             kk = 0;
/* 377 */             size = selections.size();
/* 378 */             Vector select1 = (Vector)selections.clone();
/* 379 */             for (int ii = 0; ii < size; ii++) {
/*     */               
/* 381 */               Selection sel1 = (Selection)select1.elementAt(ii);
/* 382 */               Selection sel = SelectionManager.getInstance().getSelectionAndSchedule(sel1.getSelectionID());
/*     */               
/* 384 */               if (sel.getStreetDate() != null && 
/* 385 */                 sel.getStreetDate().after(beginEffectiveDate)) {
/* 386 */                 selections.remove(sel1);
/* 387 */                 kk++;
/*     */               } 
/*     */             } 
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
/* 400 */             if (selections == null) {
/* 401 */               selections = new Vector();
/*     */             }
/*     */             
/* 404 */             MilestoneHelper.setSelectionSorting(selections, 14);
/* 405 */             Collections.sort(selections);
/*     */ 
/*     */             
/* 408 */             MilestoneHelper.setSelectionSorting(selections, 5);
/* 409 */             Collections.sort(selections);
/*     */ 
/*     */             
/* 412 */             MilestoneHelper.setSelectionSorting(selections, 4);
/* 413 */             Collections.sort(selections);
/*     */ 
/*     */ 
/*     */             
/* 417 */             MilestoneHelper.setSelectionSorting(selections, 3);
/* 418 */             Collections.sort(selections);
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 423 */             MilestoneHelper.setSelectionSorting(selections, 1);
/* 424 */             Collections.sort(selections);
/*     */ 
/*     */             
/* 427 */             MilestoneHelper.setSelectionSorting(selections, 8);
/* 428 */             Collections.sort(selections);
/*     */ 
/*     */             
/* 431 */             MilestoneHelper.setSelectionSorting(selections, 6);
/* 432 */             Collections.sort(selections);
/*     */ 
/*     */ 
/*     */             
/* 436 */             MilestoneHelper.setSelectionSorting(selections, 16);
/* 437 */             Collections.sort(selections);
/*     */ 
/*     */             
/* 440 */             int count = 2;
/* 441 */             int numRec = selections.size();
/* 442 */             int chunkSize = numRec / 10;
/*     */ 
/*     */             
/* 445 */             for (int i = 0; i < selections.size(); i++) {
/*     */ 
/*     */               
/*     */               try {
/*     */                 
/* 450 */                 int myPercent = i / chunkSize;
/* 451 */                 if (myPercent > 1 && myPercent < 10)
/* 452 */                   count = myPercent; 
/* 453 */                 HttpServletResponse sresponse = context.getResponse();
/* 454 */                 context.putDelivery("status", new String("start_report"));
/* 455 */                 context.putDelivery("percent", new String(String.valueOf(count * 10)));
/* 456 */                 context.includeJSP("status.jsp", "hiddenFrame");
/* 457 */                 sresponse.setContentType("text/plain");
/* 458 */                 sresponse.flushBuffer();
/*     */               }
/* 460 */               catch (Exception exception) {}
/*     */ 
/*     */ 
/*     */               
/* 464 */               Selection sel = (Selection)selections.elementAt(i);
/*     */               
/* 466 */               sel = SelectionManager.getInstance().getSelectionAndSchedule(sel.getSelectionID());
/*     */ 
/*     */ 
/*     */               
/* 470 */               String selectionNo = SelectionManager.getLookupObjectValue(sel.getPrefixID());
/* 471 */               if (selectionNo == null)
/* 472 */                 selectionNo = ""; 
/* 473 */               selectionNo = String.valueOf(selectionNo) + sel.getSelectionNo();
/*     */ 
/*     */ 
/*     */               
/* 477 */               Schedule schedule = sel.getSchedule();
/*     */               
/* 479 */               Vector tasks = (schedule != null) ? schedule.getTasks() : null;
/* 480 */               ScheduledTask task = null;
/*     */ 
/*     */               
/* 483 */               boolean allTasks = true;
/*     */ 
/*     */               
/* 486 */               if (tasks != null)
/*     */               {
/*     */ 
/*     */                 
/* 490 */                 for (int j = 0; j < tasks.size(); j++) {
/*     */                   
/* 492 */                   task = (ScheduledTask)tasks.get(j);
/*     */                   
/* 494 */                   if (task != null && task.getCompletionDate() != null && !task.getCompletionDate().equals("")) {
/*     */                     
/* 496 */                     String completionDate = MilestoneHelper.getFormatedDate(task.getCompletionDate());
/*     */                     
/* 498 */                     allTasks = true;
/*     */                     
/* 500 */                     task = null;
/*     */                   
/*     */                   }
/*     */                   else {
/*     */                     
/* 505 */                     allTasks = false;
/*     */ 
/*     */ 
/*     */ 
/*     */                     
/*     */                     break;
/*     */                   } 
/*     */                 } 
/*     */               }
/*     */ 
/*     */ 
/*     */ 
/*     */               
/* 518 */               String comment = (sel.getSelectionComments() != null) ? sel.getSelectionComments() : "";
/*     */               
/* 520 */               String streetDate = MilestoneHelper.getFormatedDate(sel.getStreetDate());
/*     */               
/* 522 */               String status = (sel.getSelectionStatus() != null && sel.getSelectionStatus().getName() != null) ? 
/* 523 */                 sel.getSelectionStatus().getName() : "";
/*     */               
/* 525 */               if (status.equalsIgnoreCase("TBS")) {
/* 526 */                 streetDate = "TBS " + streetDate;
/*     */               }
/* 528 */               else if (status.equalsIgnoreCase("In The Works")) {
/* 529 */                 streetDate = "ITW " + streetDate;
/*     */               } 
/* 531 */               String allTasksComp = "";
/* 532 */               if (allTasks) allTasksComp = "X";
/*     */               
/* 534 */               table_contents.setRowInsets(nextRow, new Insets(0, 0, 5, 0));
/*     */               
/* 536 */               table_contents.setObject(nextRow, 0, sel.getArtist());
/* 537 */               table_contents.setObject(nextRow, 1, sel.getTitle());
/*     */ 
/*     */ 
/*     */               
/* 541 */               String upc = sel.getUpc();
/* 542 */               upc = MilestoneHelper_2.getRMSReportFormat(upc, "UPC", sel.getIsDigital());
/*     */               
/* 544 */               table_contents.setObject(nextRow, 3, upc);
/* 545 */               table_contents.setObject(nextRow, 4, selectionNo);
/*     */ 
/*     */               
/* 548 */               table_contents.setObject(nextRow, 5, String.valueOf(ReleasingFamily.getName(sel.getReleaseFamilyId())) + "\n" + sel.getImprint());
/*     */               
/* 550 */               table_contents.setObject(nextRow, 7, streetDate);
/* 551 */               table_contents.setObject(nextRow, 8, allTasksComp);
/* 552 */               table_contents.setObject(nextRow, 9, comment);
/*     */ 
/*     */               
/* 555 */               table_contents.setAlignment(nextRow, 3, 1);
/*     */ 
/*     */               
/* 558 */               table_contents.setRowBorder(nextRow, 4097);
/*     */               
/* 560 */               table_contents.setRowBorderColor(nextRow, Color.lightGray);
/*     */               
/* 562 */               table_contents.setAlignment(nextRow, 0, 9);
/* 563 */               table_contents.setAlignment(nextRow, 1, 9);
/* 564 */               table_contents.setAlignment(nextRow, 2, 10);
/* 565 */               table_contents.setAlignment(nextRow, 3, 9);
/* 566 */               table_contents.setAlignment(nextRow, 4, 10);
/* 567 */               table_contents.setAlignment(nextRow, 5, 9);
/* 568 */               table_contents.setAlignment(nextRow, 6, 9);
/* 569 */               table_contents.setAlignment(nextRow, 7, 12);
/* 570 */               table_contents.setAlignment(nextRow, 8, 10);
/* 571 */               table_contents.setAlignment(nextRow, 9, 9);
/* 572 */               nextRow++;
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 582 */       report.setElement("table_colheaders", table_contents);
/*     */     
/*     */     }
/* 585 */     catch (Exception e) {
/*     */       
/* 587 */       System.out.println(">>>>>>>>ReportHandler.fillPastDueReleasesForPrint(): exception: " + e);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\PastDueReleasesForPrintSubHandler.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */