/*     */ package WEB-INF.classes.com.universal.milestone;
/*     */ 
/*     */ import com.techempower.ComponentLog;
/*     */ import com.techempower.gemini.Context;
/*     */ import com.techempower.gemini.GeminiApplication;
/*     */ import com.universal.milestone.EntRelScheduleForPrintSubHandler;
/*     */ import com.universal.milestone.Form;
/*     */ import com.universal.milestone.MilestoneHelper;
/*     */ import com.universal.milestone.MilestoneHelper_2;
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
/*     */ 
/*     */ public class EntRelScheduleForPrintSubHandler
/*     */   extends SecureHandler
/*     */ {
/*     */   public static final String COMPONENT_CODE = "hEntRel";
/*     */   public GeminiApplication application;
/*     */   public ComponentLog log;
/*     */   
/*     */   public EntRelScheduleForPrintSubHandler(GeminiApplication application) {
/*  70 */     this.application = application;
/*  71 */     this.log = application.getLog("hEntRel");
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
/*     */   protected static void fillEntRelScheduleForPrint(XStyleSheet report, Context context) {
/*     */     try {
/*  92 */       HttpServletResponse sresponse = context.getResponse();
/*  93 */       context.putDelivery("status", new String("start_gathering"));
/*  94 */       context.includeJSP("status.jsp", "hiddenFrame");
/*  95 */       sresponse.setContentType("text/plain");
/*  96 */       sresponse.flushBuffer();
/*     */     }
/*  98 */     catch (Exception exception) {}
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 103 */     Vector selections = MilestoneHelper.getSelectionsForReport(context);
/*     */ 
/*     */     
/*     */     try {
/* 107 */       HttpServletResponse sresponse = context.getResponse();
/* 108 */       context.putDelivery("status", new String("start_report"));
/* 109 */       context.putDelivery("percent", new String("10"));
/* 110 */       context.includeJSP("status.jsp", "hiddenFrame");
/* 111 */       sresponse.setContentType("text/plain");
/* 112 */       sresponse.flushBuffer();
/*     */     }
/* 114 */     catch (Exception exception) {}
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
/* 125 */       Form reportForm = (Form)context.getSessionValue("reportForm");
/*     */       
/* 127 */       Calendar beginStDate = (reportForm.getStringValue("beginDate") != null && 
/* 128 */         reportForm.getStringValue("beginDate").length() > 0) ? 
/* 129 */         MilestoneHelper.getDate(reportForm.getStringValue("beginDate")) : null;
/*     */       
/* 131 */       Calendar endStDate = (reportForm.getStringValue("endDate") != null && 
/* 132 */         reportForm.getStringValue("endDate").length() > 0) ? 
/* 133 */         MilestoneHelper.getDate(reportForm.getStringValue("endDate")) : null;
/*     */       
/* 135 */       report.setElement("crs_startdate", MilestoneHelper.getFormatedDate(beginStDate));
/* 136 */       report.setElement("crs_enddate", MilestoneHelper.getFormatedDate(endStDate));
/*     */ 
/*     */       
/* 139 */       SimpleDateFormat formatter = new SimpleDateFormat("MMMM d, yyyy");
/* 140 */       String todayLong = formatter.format(new Date());
/* 141 */       report.setElement("crs_bottomdate", todayLong);
/*     */ 
/*     */       
/* 144 */       Hashtable selTable = MilestoneHelper.groupSelectionsByFamilyAndCompany(selections);
/*     */       
/* 146 */       Enumeration families = selTable.keys();
/* 147 */       Vector familyVector = new Vector();
/* 148 */       while (families.hasMoreElements()) {
/* 149 */         familyVector.addElement(families.nextElement());
/*     */       }
/*     */       
/* 152 */       int numCompanies = 0;
/* 153 */       for (int i = 0; i < familyVector.size(); i++) {
/*     */         
/* 155 */         String familyName = (familyVector.elementAt(i) != null) ? (String)familyVector.elementAt(i) : "";
/* 156 */         Hashtable companyTable = (Hashtable)selTable.get(familyName);
/* 157 */         if (companyTable != null)
/*     */         {
/* 159 */           numCompanies += companyTable.size();
/*     */         }
/*     */       } 
/*     */ 
/*     */       
/* 164 */       int numExtraRows = 1 + familyVector.size() * 3 + numCompanies;
/*     */ 
/*     */       
/* 167 */       int numSelections = selections.size();
/*     */       
/* 169 */       int numRows = numSelections + numExtraRows;
/*     */ 
/*     */       
/* 172 */       DefaultTableLens table_contents = new DefaultTableLens(numRows, 10);
/*     */       
/* 174 */       table_contents.setColBorder(0);
/* 175 */       table_contents.setRowBorderColor(Color.lightGray);
/*     */       
/* 177 */       table_contents.setColWidth(0, 190);
/* 178 */       table_contents.setColWidth(1, 180);
/* 179 */       table_contents.setColWidth(2, 50);
/* 180 */       table_contents.setColWidth(3, 130);
/* 181 */       table_contents.setColWidth(4, 125);
/* 182 */       table_contents.setColWidth(5, 60);
/* 183 */       table_contents.setColWidth(6, 70);
/* 184 */       table_contents.setColWidth(7, 80);
/* 185 */       table_contents.setColWidth(8, 60);
/* 186 */       table_contents.setColWidth(9, 180);
/*     */       
/* 188 */       table_contents.setAlignment(0, 0, 33);
/* 189 */       table_contents.setAlignment(0, 1, 34);
/* 190 */       table_contents.setAlignment(0, 2, 34);
/* 191 */       table_contents.setAlignment(0, 3, 34);
/* 192 */       table_contents.setAlignment(0, 4, 34);
/* 193 */       table_contents.setAlignment(0, 5, 34);
/* 194 */       table_contents.setAlignment(0, 6, 34);
/* 195 */       table_contents.setAlignment(0, 7, 34);
/* 196 */       table_contents.setAlignment(0, 8, 34);
/* 197 */       table_contents.setAlignment(0, 9, 33);
/*     */ 
/*     */       
/* 200 */       table_contents.setHeaderRowCount(1);
/*     */       
/* 202 */       table_contents.setRowBorder(-1, 0);
/* 203 */       table_contents.setRowBorder(0, 266240);
/* 204 */       table_contents.setRowBorderColor(0, Color.black);
/*     */ 
/*     */       
/* 207 */       table_contents.setRowAlignment(0, 32);
/*     */       
/* 209 */       table_contents.setObject(0, 0, "Artist");
/* 210 */       table_contents.setObject(0, 1, "Title");
/* 211 */       table_contents.setObject(0, 2, "P&D");
/* 212 */       table_contents.setObject(0, 3, "UPC");
/* 213 */       table_contents.setObject(0, 4, "Local\nProd. #");
/* 214 */       table_contents.setObject(0, 5, "Price");
/* 215 */       table_contents.setObject(0, 6, "Config");
/* 216 */       table_contents.setObject(0, 7, "Street/\nShip\nDate");
/* 217 */       table_contents.setObject(0, 8, "Depot\nDate");
/* 218 */       table_contents.setObject(0, 9, "Comments");
/* 219 */       table_contents.setRowInsets(0, new Insets(0, 0, 0, 0));
/* 220 */       table_contents.setRowFont(0, new Font("Arial", 3, 11));
/*     */ 
/*     */ 
/*     */       
/* 224 */       int nextRow = 1;
/*     */       
/* 226 */       for (int n = 0; n < familyVector.size(); n++) {
/*     */         
/* 228 */         String family = (String)familyVector.elementAt(n);
/* 229 */         String familyHeaderText = !family.trim().equals("") ? family : "Other";
/*     */ 
/*     */         
/* 232 */         table_contents.setObject(nextRow, 0, "");
/* 233 */         table_contents.setObject(nextRow, 1, "");
/* 234 */         table_contents.setObject(nextRow, 2, "");
/* 235 */         table_contents.setObject(nextRow, 3, "");
/* 236 */         table_contents.setObject(nextRow, 4, "");
/* 237 */         table_contents.setObject(nextRow, 5, "");
/* 238 */         table_contents.setObject(nextRow, 6, "");
/* 239 */         table_contents.setObject(nextRow, 7, "");
/* 240 */         table_contents.setObject(nextRow, 8, "");
/* 241 */         table_contents.setObject(nextRow, 9, "");
/* 242 */         table_contents.setRowInsets(nextRow, new Insets(0, 0, 0, 0));
/* 243 */         table_contents.setRowHeight(nextRow, 10);
/*     */ 
/*     */ 
/*     */         
/* 247 */         table_contents.setSpan(nextRow + 1, 0, new Dimension(10, 1));
/*     */         
/* 249 */         table_contents.setRowBorder(nextRow, 4097);
/* 250 */         table_contents.setRowBorderColor(nextRow, Color.black);
/*     */         
/* 252 */         table_contents.setRowBorder(nextRow + 1, 4097);
/* 253 */         table_contents.setRowBorderColor(nextRow + 1, Color.black);
/*     */         
/* 255 */         table_contents.setColBorder(nextRow + 1, -1, 4097);
/* 256 */         table_contents.setColBorder(nextRow + 1, 0, 4097);
/* 257 */         table_contents.setColBorderColor(nextRow + 1, -1, Color.black);
/* 258 */         table_contents.setColBorderColor(nextRow + 1, 0, Color.black);
/*     */         
/* 260 */         table_contents.setInsets(nextRow + 1, 0, new Insets(0, 5, 0, 0));
/* 261 */         table_contents.setObject(nextRow + 1, 0, familyHeaderText);
/*     */         
/* 263 */         table_contents.setColBorder(nextRow + 1, 10, 4097);
/* 264 */         table_contents.setColBorderColor(nextRow + 1, 10, Color.black);
/*     */         
/* 266 */         table_contents.setRowFont(nextRow + 1, new Font("Arial", 1, 16));
/*     */ 
/*     */         
/* 269 */         table_contents.setRowInsets(nextRow + 2, new Insets(0, 0, 0, 0));
/* 270 */         table_contents.setRowHeight(nextRow + 2, 5);
/*     */         
/* 272 */         nextRow += 3;
/*     */ 
/*     */         
/* 275 */         Hashtable companyTable = (Hashtable)selTable.get(family);
/* 276 */         if (companyTable != null) {
/*     */           
/* 278 */           Enumeration companies = companyTable.keys();
/*     */           
/* 280 */           Vector companiesVector = new Vector();
/*     */           
/* 282 */           while (companies.hasMoreElements())
/*     */           {
/* 284 */             companiesVector.add((String)companies.nextElement());
/*     */           }
/*     */           
/* 287 */           Vector sortedCompaniesVector = MilestoneHelper.sortStrings(companiesVector);
/*     */           
/* 289 */           for (int a = 0; a < sortedCompaniesVector.size(); a++) {
/*     */             
/* 291 */             String company = (String)sortedCompaniesVector.get(a);
/*     */ 
/*     */             
/* 294 */             table_contents.setSpan(nextRow, 0, new Dimension(10, 1));
/* 295 */             table_contents.setObject(nextRow, 0, company);
/*     */ 
/*     */             
/* 298 */             table_contents.setRowBorder(nextRow, 4097);
/* 299 */             table_contents.setRowBorderColor(nextRow, Color.black);
/*     */             
/* 301 */             table_contents.setRowFont(nextRow, new Font("Arial", 3, 14));
/* 302 */             table_contents.setRowBackground(nextRow, Color.black);
/* 303 */             table_contents.setRowForeground(nextRow, Color.white);
/*     */             
/* 305 */             nextRow++;
/*     */ 
/*     */             
/* 308 */             selections = (Vector)companyTable.get(company);
/* 309 */             if (selections == null) {
/* 310 */               selections = new Vector();
/*     */             }
/*     */             
/* 313 */             MilestoneHelper.setSelectionSorting(selections, 14);
/* 314 */             Collections.sort(selections);
/*     */ 
/*     */             
/* 317 */             MilestoneHelper.setSelectionSorting(selections, 5);
/* 318 */             Collections.sort(selections);
/*     */ 
/*     */             
/* 321 */             MilestoneHelper.setSelectionSorting(selections, 4);
/* 322 */             Collections.sort(selections);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 335 */             MilestoneHelper.setSelectionSorting(selections, 3);
/* 336 */             Collections.sort(selections);
/*     */ 
/*     */             
/* 339 */             MilestoneHelper.setSelectionSorting(selections, 1);
/* 340 */             Collections.sort(selections);
/*     */ 
/*     */             
/* 343 */             MilestoneHelper.setSelectionSorting(selections, 16);
/* 344 */             Collections.sort(selections);
/*     */ 
/*     */             
/* 347 */             int count = 2;
/* 348 */             int numRec = selections.size();
/* 349 */             int chunkSize = numRec / 10;
/*     */ 
/*     */             
/* 352 */             for (int i = 0; i < selections.size(); i++) {
/*     */ 
/*     */               
/*     */               try {
/*     */                 
/* 357 */                 int myPercent = i / chunkSize;
/* 358 */                 if (myPercent > 1 && myPercent < 10)
/* 359 */                   count = myPercent; 
/* 360 */                 HttpServletResponse sresponse = context.getResponse();
/* 361 */                 context.putDelivery("status", new String("start_report"));
/* 362 */                 context.putDelivery("percent", new String(String.valueOf(count * 10)));
/* 363 */                 context.includeJSP("status.jsp", "hiddenFrame");
/* 364 */                 sresponse.setContentType("text/plain");
/* 365 */                 sresponse.flushBuffer();
/*     */               }
/* 367 */               catch (Exception exception) {}
/*     */ 
/*     */ 
/*     */               
/* 371 */               Selection sel = (Selection)selections.elementAt(i);
/* 372 */               sel = SelectionManager.getInstance().getSelectionAndSchedule(sel.getSelectionID());
/*     */ 
/*     */               
/* 375 */               String pAndD = sel.getPressAndDistribution() ? "X" : "";
/*     */ 
/*     */               
/* 378 */               String selectionNo = SelectionManager.getLookupObjectValue(sel.getPrefixID());
/* 379 */               if (selectionNo == null)
/* 380 */                 selectionNo = ""; 
/* 381 */               selectionNo = String.valueOf(selectionNo) + sel.getSelectionNo();
/*     */ 
/*     */               
/* 384 */               String price = "0.00";
/* 385 */               if (sel.getPriceCode() != null && 
/* 386 */                 sel.getPriceCode().getTotalCost() > 0.0F) {
/* 387 */                 price = MilestoneHelper.formatDollarPrice(sel.getPriceCode().getTotalCost());
/*     */               }
/*     */ 
/*     */ 
/*     */               
/* 392 */               String config = sel.getSelectionSubConfig().getSelectionSubConfigurationAbbreviation();
/*     */ 
/*     */ 
/*     */               
/* 396 */               if (config == null) {
/* 397 */                 config = "";
/*     */               }
/*     */               
/* 400 */               String comment = (sel.getSelectionComments() != null) ? sel.getSelectionComments() : "";
/*     */               
/* 402 */               String streetDate = MilestoneHelper.getFormatedDate(sel.getStreetDate());
/*     */               
/* 404 */               String status = (sel.getSelectionStatus() != null && sel.getSelectionStatus().getName() != null) ? 
/* 405 */                 sel.getSelectionStatus().getName() : "";
/*     */               
/* 407 */               if (status.equalsIgnoreCase("TBS")) {
/* 408 */                 streetDate = "TBS " + streetDate;
/*     */               }
/* 410 */               else if (status.equalsIgnoreCase("In The Works")) {
/* 411 */                 streetDate = "ITW " + streetDate;
/*     */               } 
/*     */               
/* 414 */               Schedule schedule = sel.getSchedule();
/*     */               
/* 416 */               Vector tasks = (schedule != null) ? schedule.getTasks() : null;
/* 417 */               ScheduledTask task = null;
/*     */               
/* 419 */               String DEPOT = "";
/*     */               
/* 421 */               if (tasks != null)
/*     */               {
/* 423 */                 for (int j = 0; j < tasks.size(); j++) {
/*     */                   
/* 425 */                   task = (ScheduledTask)tasks.get(j);
/*     */                   
/* 427 */                   if (task != null && task.getDueDate() != null) {
/*     */                     
/* 429 */                     String dueDate = MilestoneHelper.getFormatedDate(task.getDueDate());
/* 430 */                     String taskAbbrev = MilestoneHelper.getTaskAbbreivationNameById(task.getTaskAbbreviationID());
/*     */                     
/* 432 */                     if (taskAbbrev.equalsIgnoreCase("DEPO")) {
/*     */                       
/* 434 */                       DEPOT = dueDate;
/*     */                       
/*     */                       break;
/*     */                     } 
/* 438 */                     task = null;
/*     */                   } 
/*     */                 } 
/*     */               }
/*     */ 
/*     */ 
/*     */               
/* 445 */               table_contents.setRowInsets(nextRow, new Insets(0, 0, 5, 0));
/*     */               
/* 447 */               table_contents.setObject(nextRow, 0, sel.getArtist());
/* 448 */               table_contents.setObject(nextRow, 1, sel.getTitle());
/* 449 */               table_contents.setObject(nextRow, 2, pAndD);
/*     */ 
/*     */               
/* 452 */               String upc = sel.getUpc();
/* 453 */               upc = MilestoneHelper_2.getRMSReportFormat(upc, "UPC", sel.getIsDigital());
/*     */               
/* 455 */               table_contents.setObject(nextRow, 3, upc);
/* 456 */               table_contents.setObject(nextRow, 4, selectionNo);
/* 457 */               table_contents.setObject(nextRow, 5, "$" + price);
/* 458 */               table_contents.setObject(nextRow, 6, config);
/* 459 */               table_contents.setObject(nextRow, 7, streetDate);
/* 460 */               table_contents.setObject(nextRow, 8, DEPOT);
/* 461 */               table_contents.setObject(nextRow, 9, comment);
/*     */ 
/*     */               
/* 464 */               table_contents.setAlignment(nextRow, 3, 1);
/*     */ 
/*     */ 
/*     */               
/* 468 */               table_contents.setRowBorder(nextRow, 4097);
/*     */               
/* 470 */               table_contents.setRowBorderColor(nextRow, Color.lightGray);
/*     */               
/* 472 */               table_contents.setAlignment(nextRow, 0, 9);
/* 473 */               table_contents.setAlignment(nextRow, 1, 9);
/* 474 */               table_contents.setAlignment(nextRow, 2, 10);
/* 475 */               table_contents.setAlignment(nextRow, 3, 9);
/* 476 */               table_contents.setAlignment(nextRow, 4, 8);
/* 477 */               table_contents.setAlignment(nextRow, 5, 12);
/* 478 */               table_contents.setAlignment(nextRow, 6, 9);
/* 479 */               table_contents.setAlignment(nextRow, 7, 12);
/* 480 */               table_contents.setAlignment(nextRow, 8, 12);
/* 481 */               table_contents.setAlignment(nextRow, 9, 9);
/*     */               
/* 483 */               nextRow++;
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 493 */       report.setElement("table_colheaders", table_contents);
/*     */     
/*     */     }
/* 496 */     catch (Exception e) {
/*     */       
/* 498 */       System.out.println(">>>>>>>>ReportHandler.fillEntRelScheduleForPrint(): exception: " + e);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\EntRelScheduleForPrintSubHandler.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */