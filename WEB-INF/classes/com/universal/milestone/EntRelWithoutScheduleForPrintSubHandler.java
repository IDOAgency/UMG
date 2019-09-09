/*     */ package WEB-INF.classes.com.universal.milestone;
/*     */ 
/*     */ import com.techempower.ComponentLog;
/*     */ import com.techempower.gemini.Context;
/*     */ import com.techempower.gemini.GeminiApplication;
/*     */ import com.universal.milestone.EntRelWithoutScheduleForPrintSubHandler;
/*     */ import com.universal.milestone.Form;
/*     */ import com.universal.milestone.MilestoneHelper;
/*     */ import com.universal.milestone.MilestoneHelper_2;
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
/*     */ 
/*     */ public class EntRelWithoutScheduleForPrintSubHandler
/*     */   extends SecureHandler
/*     */ {
/*     */   public static final String COMPONENT_CODE = "hCProd";
/*     */   public GeminiApplication application;
/*     */   public ComponentLog log;
/*     */   
/*     */   public EntRelWithoutScheduleForPrintSubHandler(GeminiApplication application) {
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static void fillEntRelWithoutScheduleForPrint(XStyleSheet report, Context context) {
/*     */     try {
/*  98 */       HttpServletResponse sresponse = context.getResponse();
/*  99 */       context.putDelivery("status", new String("start_gathering"));
/* 100 */       context.includeJSP("status.jsp", "hiddenFrame");
/* 101 */       sresponse.setContentType("text/plain");
/* 102 */       sresponse.flushBuffer();
/*     */     }
/* 104 */     catch (Exception exception) {}
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 109 */     Vector selections = MilestoneHelper.getSelectionsForReport(context);
/*     */     
/* 111 */     int kount = 0;
/* 112 */     int size = selections.size();
/* 113 */     Vector temp_selections = MilestoneHelper.getSelectionsForReport(context);
/*     */     
/* 115 */     for (i = 0; i < selections.size(); i++) {
/* 116 */       Selection sel = (Selection)selections.elementAt(i);
/* 117 */       sel = SelectionManager.getInstance().getSelectionAndSchedule(sel.getSelectionID());
/*     */       
/* 119 */       if (sel.getSchedule() != null) {
/* 120 */         int temp_i = i - kount;
/* 121 */         temp_selections.remove(temp_i);
/* 122 */         kount++;
/*     */       } 
/*     */     } 
/* 125 */     selections = temp_selections;
/*     */ 
/*     */     
/*     */     try {
/* 129 */       HttpServletResponse sresponse = context.getResponse();
/* 130 */       context.putDelivery("status", new String("start_report"));
/* 131 */       context.putDelivery("percent", new String("10"));
/* 132 */       context.includeJSP("status.jsp", "hiddenFrame");
/* 133 */       sresponse.setContentType("text/plain");
/* 134 */       sresponse.flushBuffer();
/*     */     }
/* 136 */     catch (Exception i) {
/*     */       Exception exception;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 147 */       Form reportForm = (Form)context.getSessionValue("reportForm");
/*     */       
/* 149 */       Calendar beginStDate = (reportForm.getStringValue("beginDate") != null && 
/* 150 */         reportForm.getStringValue("beginDate").length() > 0) ? 
/* 151 */         MilestoneHelper.getDate(reportForm.getStringValue("beginDate")) : null;
/*     */       
/* 153 */       Calendar endStDate = (reportForm.getStringValue("endDate") != null && 
/* 154 */         reportForm.getStringValue("endDate").length() > 0) ? 
/* 155 */         MilestoneHelper.getDate(reportForm.getStringValue("endDate")) : null;
/*     */       
/* 157 */       report.setElement("crs_startdate", MilestoneHelper.getFormatedDate(beginStDate));
/* 158 */       report.setElement("crs_enddate", MilestoneHelper.getFormatedDate(endStDate));
/*     */ 
/*     */       
/* 161 */       SimpleDateFormat formatter = new SimpleDateFormat("MMMM d, yyyy");
/* 162 */       String todayLong = formatter.format(new Date());
/* 163 */       report.setElement("crs_bottomdate", todayLong);
/*     */ 
/*     */       
/* 166 */       Hashtable selTable = MilestoneHelper.groupSelectionsByFamilyAndCompany(selections);
/*     */       
/* 168 */       Enumeration families = selTable.keys();
/* 169 */       Vector familyVector = new Vector();
/* 170 */       while (families.hasMoreElements()) {
/* 171 */         familyVector.addElement(families.nextElement());
/*     */       }
/*     */       
/* 174 */       int numCompanies = 0;
/* 175 */       for (int i = 0; i < familyVector.size(); i++) {
/*     */         
/* 177 */         String familyName = (familyVector.elementAt(i) != null) ? (String)familyVector.elementAt(i) : "";
/* 178 */         Hashtable companyTable = (Hashtable)selTable.get(familyName);
/* 179 */         if (companyTable != null)
/*     */         {
/* 181 */           numCompanies += companyTable.size();
/*     */         }
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 188 */       int numExtraRows = 1 + familyVector.size() * 3 + numCompanies;
/*     */ 
/*     */       
/* 191 */       int numSelections = selections.size();
/*     */       
/* 193 */       int numRows = size + numExtraRows - kount;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 203 */       DefaultTableLens table_contents = new DefaultTableLens(numRows, 10);
/*     */ 
/*     */       
/* 206 */       table_contents.setColBorder(0);
/* 207 */       table_contents.setRowBorderColor(Color.white);
/*     */       
/* 209 */       table_contents.setColWidth(0, 190);
/* 210 */       table_contents.setColWidth(1, 180);
/* 211 */       table_contents.setColWidth(2, 120);
/* 212 */       table_contents.setColWidth(3, 100);
/* 213 */       table_contents.setColWidth(4, 80);
/* 214 */       table_contents.setColWidth(5, 60);
/* 215 */       table_contents.setColWidth(6, 180);
/*     */       
/* 217 */       table_contents.setAlignment(0, 0, 33);
/* 218 */       table_contents.setAlignment(0, 1, 34);
/* 219 */       table_contents.setAlignment(0, 2, 34);
/* 220 */       table_contents.setAlignment(0, 3, 34);
/* 221 */       table_contents.setAlignment(0, 4, 34);
/* 222 */       table_contents.setAlignment(0, 5, 34);
/* 223 */       table_contents.setAlignment(0, 6, 34);
/*     */ 
/*     */       
/* 226 */       table_contents.setHeaderRowCount(1);
/*     */       
/* 228 */       table_contents.setRowBorder(-1, 0);
/* 229 */       table_contents.setRowBorder(0, 266240);
/* 230 */       table_contents.setRowBorderColor(0, Color.black);
/*     */ 
/*     */       
/* 233 */       table_contents.setRowAlignment(0, 32);
/*     */       
/* 235 */       table_contents.setObject(0, 0, "Artist");
/* 236 */       table_contents.setObject(0, 1, "Title");
/*     */       
/* 238 */       table_contents.setObject(0, 2, "UPC");
/* 239 */       table_contents.setObject(0, 3, "Local\nProduct #");
/*     */       
/* 241 */       table_contents.setObject(0, 4, "Rel. Family/\nLabel");
/* 242 */       table_contents.setObject(0, 5, "Street/Ship\nDate");
/*     */       
/* 244 */       table_contents.setObject(0, 6, "Comments");
/* 245 */       table_contents.setRowInsets(0, new Insets(0, 0, 0, 0));
/* 246 */       table_contents.setRowFont(0, new Font("Arial", 3, 11));
/*     */ 
/*     */ 
/*     */       
/* 250 */       int nextRow = 1;
/*     */       
/* 252 */       for (int n = 0; n < familyVector.size(); n++) {
/*     */         
/* 254 */         String family = (String)familyVector.elementAt(n);
/* 255 */         String familyHeaderText = !family.trim().equals("") ? family : "Other";
/*     */ 
/*     */         
/* 258 */         table_contents.setObject(nextRow, 0, "");
/* 259 */         table_contents.setObject(nextRow, 1, "");
/* 260 */         table_contents.setObject(nextRow, 2, "");
/* 261 */         table_contents.setObject(nextRow, 3, "");
/* 262 */         table_contents.setObject(nextRow, 4, "");
/* 263 */         table_contents.setObject(nextRow, 5, "");
/* 264 */         table_contents.setObject(nextRow, 6, "");
/*     */         
/* 266 */         table_contents.setRowInsets(nextRow, new Insets(0, 0, 0, 0));
/* 267 */         table_contents.setRowHeight(nextRow, 15);
/*     */ 
/*     */         
/* 270 */         Hashtable companyTable = (Hashtable)selTable.get(family);
/* 271 */         if (companyTable != null) {
/*     */           
/* 273 */           Enumeration companies = companyTable.keys();
/*     */           
/* 275 */           Vector companiesVector = new Vector();
/*     */           
/* 277 */           while (companies.hasMoreElements())
/*     */           {
/* 279 */             companiesVector.add((String)companies.nextElement());
/*     */           }
/*     */           
/* 282 */           Vector sortedCompaniesVector = MilestoneHelper.sortStrings(companiesVector);
/*     */           
/* 284 */           for (int a = 0; a < sortedCompaniesVector.size(); a++) {
/*     */             
/* 286 */             String company = (String)sortedCompaniesVector.get(a);
/*     */ 
/*     */             
/* 289 */             table_contents.setSpan(nextRow, 0, new Dimension(10, 1));
/* 290 */             table_contents.setObject(nextRow, 0, company);
/*     */ 
/*     */             
/* 293 */             table_contents.setRowBorder(nextRow, 4097);
/* 294 */             table_contents.setRowBorderColor(nextRow, Color.black);
/*     */             
/* 296 */             table_contents.setRowFont(nextRow, new Font("Arial", 3, 14));
/* 297 */             table_contents.setRowBackground(nextRow, Color.black);
/* 298 */             table_contents.setRowForeground(nextRow, Color.white);
/*     */             
/* 300 */             nextRow++;
/*     */ 
/*     */             
/* 303 */             selections = (Vector)companyTable.get(company);
/* 304 */             if (selections == null) {
/* 305 */               selections = new Vector();
/*     */             }
/*     */             
/* 308 */             MilestoneHelper.setSelectionSorting(selections, 14);
/* 309 */             Collections.sort(selections);
/*     */ 
/*     */ 
/*     */             
/* 313 */             MilestoneHelper.setSelectionSorting(selections, 4);
/* 314 */             Collections.sort(selections);
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 319 */             MilestoneHelper.setSelectionSorting(selections, 3);
/* 320 */             Collections.sort(selections);
/*     */ 
/*     */             
/* 323 */             MilestoneHelper.setSelectionSorting(selections, 1);
/* 324 */             Collections.sort(selections);
/*     */ 
/*     */             
/* 327 */             MilestoneHelper.setSelectionSorting(selections, 8);
/* 328 */             Collections.sort(selections);
/*     */ 
/*     */             
/* 331 */             MilestoneHelper.setSelectionSorting(selections, 6);
/* 332 */             Collections.sort(selections);
/*     */ 
/*     */             
/* 335 */             int count = 2;
/* 336 */             int numRec = selections.size();
/* 337 */             int chunkSize = numRec / 10;
/*     */ 
/*     */             
/* 340 */             for (int i = 0; i < selections.size(); i++) {
/*     */ 
/*     */               
/*     */               try {
/*     */                 
/* 345 */                 int myPercent = i / chunkSize;
/* 346 */                 if (myPercent > 1 && myPercent < 10)
/* 347 */                   count = myPercent; 
/* 348 */                 HttpServletResponse sresponse = context.getResponse();
/* 349 */                 context.putDelivery("status", new String("start_report"));
/* 350 */                 context.putDelivery("percent", new String(String.valueOf(count * 10)));
/* 351 */                 context.includeJSP("status.jsp", "hiddenFrame");
/* 352 */                 sresponse.setContentType("text/plain");
/* 353 */                 sresponse.flushBuffer();
/*     */               }
/* 355 */               catch (Exception exception) {}
/*     */ 
/*     */ 
/*     */               
/* 359 */               Selection sel = (Selection)selections.elementAt(i);
/* 360 */               sel = SelectionManager.getInstance().getSelectionAndSchedule(sel.getSelectionID());
/* 361 */               String status = (sel.getSelectionStatus() != null && sel.getSelectionStatus().getName() != null) ? 
/* 362 */                 sel.getSelectionStatus().getName() : "";
/* 363 */               if (sel.getSchedule() == null) {
/*     */ 
/*     */ 
/*     */ 
/*     */                 
/* 368 */                 String selectionNo = SelectionManager.getLookupObjectValue(sel.getPrefixID());
/* 369 */                 if (selectionNo == null)
/* 370 */                   selectionNo = ""; 
/* 371 */                 selectionNo = String.valueOf(selectionNo) + sel.getSelectionNo();
/*     */ 
/*     */ 
/*     */                 
/* 375 */                 String label = sel.getImprint();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */                 
/* 384 */                 String RelFamily = ReleasingFamily.getName(sel.getReleaseFamilyId());
/*     */ 
/*     */                 
/* 387 */                 String comment = (sel.getSelectionComments() != null) ? sel.getSelectionComments() : "";
/*     */                 
/* 389 */                 String streetDate = MilestoneHelper.getFormatedDate(sel.getStreetDate());
/*     */                 
/* 391 */                 status = (sel.getSelectionStatus() != null && sel.getSelectionStatus().getName() != null) ? 
/* 392 */                   sel.getSelectionStatus().getName() : "";
/*     */                 
/* 394 */                 if (status.equalsIgnoreCase("TBS")) {
/* 395 */                   streetDate = "TBS " + streetDate;
/*     */                 }
/* 397 */                 else if (status.equalsIgnoreCase("In The Works")) {
/* 398 */                   streetDate = "ITW " + streetDate;
/*     */                 } 
/*     */                 
/* 401 */                 Schedule schedule = sel.getSchedule();
/*     */                 
/* 403 */                 Vector tasks = (schedule != null) ? schedule.getTasks() : null;
/* 404 */                 ScheduledTask task = null;
/*     */ 
/*     */                 
/* 407 */                 table_contents.setRowInsets(nextRow, new Insets(0, 0, 5, 0));
/*     */                 
/* 409 */                 table_contents.setObject(nextRow, 0, sel.getArtist());
/* 410 */                 table_contents.setObject(nextRow, 1, sel.getTitle());
/*     */ 
/*     */                 
/* 413 */                 String upc = sel.getUpc();
/* 414 */                 upc = MilestoneHelper_2.getRMSReportFormat(upc, "UPC", sel.getIsDigital());
/*     */                 
/* 416 */                 table_contents.setObject(nextRow, 2, upc);
/* 417 */                 table_contents.setObject(nextRow, 3, selectionNo);
/*     */                 
/* 419 */                 table_contents.setObject(nextRow, 4, String.valueOf(RelFamily) + "\n" + label);
/* 420 */                 table_contents.setObject(nextRow, 5, streetDate);
/*     */                 
/* 422 */                 table_contents.setObject(nextRow, 6, comment);
/*     */ 
/*     */                 
/* 425 */                 table_contents.setAlignment(nextRow, 3, 1);
/*     */ 
/*     */                 
/* 428 */                 table_contents.setRowBorder(nextRow, 4097);
/*     */                 
/* 430 */                 table_contents.setRowBorderColor(nextRow, Color.lightGray);
/*     */                 
/* 432 */                 table_contents.setAlignment(nextRow, 0, 9);
/* 433 */                 table_contents.setAlignment(nextRow, 1, 9);
/* 434 */                 table_contents.setAlignment(nextRow, 2, 10);
/* 435 */                 table_contents.setAlignment(nextRow, 3, 9);
/* 436 */                 table_contents.setAlignment(nextRow, 4, 8);
/* 437 */                 table_contents.setAlignment(nextRow, 5, 12);
/* 438 */                 table_contents.setAlignment(nextRow, 6, 9);
/* 439 */                 nextRow++;
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 450 */       report.setElement("table_colheaders", table_contents);
/*     */     
/*     */     }
/* 453 */     catch (Exception i) {
/*     */       Exception e;
/* 455 */       System.out.println(">>>>>>>>ReportHandler.fillEntRelWithoutScheduleForPrint(): exception: " + e);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\EntRelWithoutScheduleForPrintSubHandler.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */