/*     */ package WEB-INF.classes.com.universal.milestone;
/*     */ 
/*     */ import com.techempower.ComponentLog;
/*     */ import com.techempower.gemini.Context;
/*     */ import com.techempower.gemini.GeminiApplication;
/*     */ import com.universal.milestone.Cache;
/*     */ import com.universal.milestone.Form;
/*     */ import com.universal.milestone.LookupObject;
/*     */ import com.universal.milestone.MilestoneHelper;
/*     */ import com.universal.milestone.MilestoneHelper_2;
/*     */ import com.universal.milestone.SecureHandler;
/*     */ import com.universal.milestone.Selection;
/*     */ import com.universal.milestone.SelectionManager;
/*     */ import com.universal.milestone.StatusJSPupdate;
/*     */ import com.universal.milestone.eInitiativeRelScheduleForPrintSubHandler;
/*     */ import inetsoft.report.SectionBand;
/*     */ import inetsoft.report.SeparatorElement;
/*     */ import inetsoft.report.XStyleSheet;
/*     */ import inetsoft.report.lens.DefaultSectionLens;
/*     */ import inetsoft.report.lens.DefaultTableLens;
/*     */ import java.awt.Color;
/*     */ import java.awt.Font;
/*     */ import java.awt.Insets;
/*     */ import java.awt.Rectangle;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.Calendar;
/*     */ import java.util.Date;
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
/*     */ public class eInitiativeRelScheduleForPrintSubHandler
/*     */   extends SecureHandler
/*     */ {
/*     */   public static final String COMPONENT_CODE = "heInit";
/*     */   public GeminiApplication application;
/*     */   public ComponentLog log;
/*     */   
/*     */   public eInitiativeRelScheduleForPrintSubHandler(GeminiApplication application) {
/*  67 */     this.application = application;
/*  68 */     this.log = application.getLog("heInit");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  76 */   public String getDescription() { return "Sub Report"; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static void filleInitiativeRelScheduleForPrint(XStyleSheet report, Context context, GeminiApplication application) {
/*  88 */     Color SHADED_AREA_COLOR = Color.lightGray;
/*  89 */     int COL_LINE_STYLE = 4097;
/*  90 */     int HEADER_FONT_SIZE = 12;
/*     */     
/*  92 */     double ldLineVal = 0.3D;
/*     */     
/*  94 */     ComponentLog log = application.getLog("heInit");
/*     */     
/*  96 */     SectionBand hbandType = new SectionBand(report);
/*  97 */     SectionBand hbandDate = new SectionBand(report);
/*  98 */     SectionBand body = new SectionBand(report);
/*  99 */     SectionBand footer = new SectionBand(report);
/* 100 */     SectionBand spacer = new SectionBand(report);
/* 101 */     DefaultSectionLens group = null;
/*     */     
/* 103 */     footer.setVisible(true);
/* 104 */     footer.setHeight(0.1F);
/* 105 */     footer.setShrinkToFit(false);
/* 106 */     footer.setBottomBorder(0);
/*     */     
/* 108 */     spacer.setVisible(true);
/* 109 */     spacer.setHeight(0.05F);
/* 110 */     spacer.setShrinkToFit(false);
/* 111 */     spacer.setBottomBorder(0);
/*     */ 
/*     */     
/* 114 */     int separatorLineStyle = 266240;
/* 115 */     Color separatorLineColor = Color.black;
/*     */ 
/*     */     
/* 118 */     int monthLineStyle = 4097;
/* 119 */     Color monthLineColor = Color.black;
/*     */     
/* 121 */     StatusJSPupdate aStatusUpdate = new StatusJSPupdate(context);
/* 122 */     aStatusUpdate.setInternalCounter(true);
/*     */ 
/*     */ 
/*     */     
/* 126 */     aStatusUpdate.updateStatus(0, 0, "start_gathering", 0);
/*     */ 
/*     */ 
/*     */     
/* 130 */     Vector selections = MilestoneHelper.getSelectionsForReport(context);
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 135 */       DefaultTableLens table_contents = null;
/* 136 */       DefaultTableLens rowCountTable = null;
/* 137 */       DefaultTableLens columnHeaderTable = null;
/* 138 */       DefaultTableLens subTable = null;
/* 139 */       DefaultTableLens dateTableLens = null;
/*     */       
/* 141 */       rowCountTable = new DefaultTableLens(2, 10000);
/*     */ 
/*     */ 
/*     */       
/* 145 */       int nextRow = 0;
/*     */ 
/*     */       
/* 148 */       SeparatorElement topSeparator = (SeparatorElement)report.getElement("separator_top");
/* 149 */       SeparatorElement bottomHeaderSeparator = (SeparatorElement)report.getElement("separator_bottom_header");
/* 150 */       SeparatorElement bottomSeparator = (SeparatorElement)report.getElement("separator_bottom");
/* 151 */       if (topSeparator != null) {
/*     */         
/* 153 */         topSeparator.setStyle(separatorLineStyle);
/* 154 */         topSeparator.setForeground(separatorLineColor);
/*     */       } 
/* 156 */       if (bottomHeaderSeparator != null) {
/*     */         
/* 158 */         bottomHeaderSeparator.setStyle(separatorLineStyle);
/* 159 */         bottomHeaderSeparator.setForeground(separatorLineColor);
/*     */       } 
/* 161 */       if (bottomSeparator != null) {
/*     */         
/* 163 */         bottomSeparator.setStyle(separatorLineStyle);
/* 164 */         bottomSeparator.setForeground(separatorLineColor);
/*     */       } 
/*     */ 
/*     */       
/* 168 */       Form reportForm = (Form)context.getSessionValue("reportForm");
/*     */       
/* 170 */       Calendar beginStDate = (reportForm.getStringValue("beginDate") != null && 
/* 171 */         reportForm.getStringValue("beginDate").length() > 0) ? 
/* 172 */         MilestoneHelper.getDate(reportForm.getStringValue("beginDate")) : null;
/*     */       
/* 174 */       Calendar endStDate = (reportForm.getStringValue("endDate") != null && 
/* 175 */         reportForm.getStringValue("endDate").length() > 0) ? 
/* 176 */         MilestoneHelper.getDate(reportForm.getStringValue("endDate")) : null;
/*     */       
/* 178 */       report.setElement("crs_startdate", MilestoneHelper.getFormatedDate(beginStDate));
/* 179 */       report.setElement("crs_enddate", MilestoneHelper.getFormatedDate(endStDate));
/*     */       
/* 181 */       SimpleDateFormat formatter = new SimpleDateFormat("MMMM d, yyyy");
/* 182 */       String todayLong = formatter.format(new Date());
/* 183 */       report.setElement("crs_bottomdate", todayLong);
/*     */ 
/*     */ 
/*     */       
/* 187 */       int numSelections = selections.size();
/* 188 */       int numRows = numSelections;
/*     */       
/* 190 */       int numColumns = 11;
/*     */       
/* 192 */       nextRow = 0;
/*     */       
/* 194 */       hbandType = new SectionBand(report);
/* 195 */       hbandType.setHeight(0.95F);
/* 196 */       hbandType.setShrinkToFit(false);
/* 197 */       hbandType.setVisible(true);
/*     */ 
/*     */       
/* 200 */       table_contents = new DefaultTableLens(1, numColumns);
/*     */       
/* 202 */       table_contents.setRowBorder(0);
/*     */       
/* 204 */       table_contents.setColWidth(0, 40);
/* 205 */       table_contents.setColWidth(1, 40);
/* 206 */       table_contents.setColWidth(2, 80);
/* 207 */       table_contents.setColWidth(3, 100);
/* 208 */       table_contents.setColWidth(4, 60);
/* 209 */       table_contents.setColWidth(5, 40);
/* 210 */       table_contents.setColWidth(6, 50);
/* 211 */       table_contents.setColWidth(7, 60);
/* 212 */       table_contents.setColWidth(8, 40);
/* 213 */       table_contents.setColWidth(9, 60);
/* 214 */       table_contents.setColWidth(10, 100);
/*     */       
/* 216 */       table_contents.setColBorder(0);
/* 217 */       table_contents.setRowBorder(0);
/*     */       
/* 219 */       table_contents.setColAlignment(0, 2);
/* 220 */       table_contents.setColAlignment(1, 2);
/*     */ 
/*     */       
/* 223 */       table_contents.setRowAlignment(nextRow, 8);
/* 224 */       table_contents.setRowHeight(nextRow, 55);
/*     */       
/* 226 */       table_contents.setObject(nextRow, 0, "ORG\nRelease\nDate");
/* 227 */       table_contents.setAlignment(nextRow, 0, 34);
/* 228 */       table_contents.setColFont(0, new Font("Arial", 3, 8));
/* 229 */       table_contents.setObject(nextRow, 1, "PP\nStreet\nDate");
/* 230 */       table_contents.setColFont(1, new Font("Arial", 3, 8));
/* 231 */       table_contents.setAlignment(nextRow, 1, 34);
/*     */       
/* 233 */       table_contents.setObject(nextRow, 2, "Artist");
/* 234 */       table_contents.setAlignment(nextRow, 2, 32);
/* 235 */       table_contents.setObject(nextRow, 3, "Title");
/* 236 */       table_contents.setAlignment(nextRow, 3, 32);
/* 237 */       table_contents.setObject(nextRow, 4, "UPC");
/* 238 */       table_contents.setAlignment(nextRow, 4, 32);
/* 239 */       table_contents.setObject(nextRow, 5, "Vol");
/* 240 */       table_contents.setAlignment(nextRow, 5, 32);
/* 241 */       table_contents.setObject(nextRow, 6, "Label");
/* 242 */       table_contents.setAlignment(nextRow, 6, 32);
/* 243 */       table_contents.setObject(nextRow, 7, "Product\nCategory");
/* 244 */       table_contents.setAlignment(nextRow, 7, 33);
/* 245 */       table_contents.setObject(nextRow, 8, "Parental\nAdv");
/* 246 */       table_contents.setAlignment(nextRow, 8, 34);
/* 247 */       table_contents.setObject(nextRow, 9, "Territory");
/* 248 */       table_contents.setAlignment(nextRow, 9, 32);
/* 249 */       table_contents.setObject(nextRow, 10, "Comments");
/* 250 */       table_contents.setAlignment(nextRow, 10, 32);
/*     */       
/* 252 */       table_contents.setRowFont(nextRow, new Font("Arial", 3, 10));
/*     */       
/* 254 */       hbandType.addTable(table_contents, new Rectangle(0, 0, 800, 39));
/* 255 */       hbandType.setBottomBorder(0);
/*     */       
/* 257 */       table_contents = new DefaultTableLens(1, numColumns);
/*     */       
/* 259 */       nextRow = 0;
/* 260 */       table_contents.setRowBackground(nextRow, Color.white);
/* 261 */       table_contents.setRowBorderColor(nextRow - 1, Color.black);
/* 262 */       table_contents.setRowBorder(nextRow, 0);
/* 263 */       table_contents.setColBorder(0);
/*     */ 
/*     */       
/* 266 */       hbandType.addTable(table_contents, new Rectangle(0, 45, 800, 3));
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 271 */       for (int n = 0; n < selections.size(); n++) {
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 276 */         nextRow = 0;
/*     */ 
/*     */ 
/*     */         
/* 280 */         footer.setVisible(true);
/* 281 */         footer.setHeight(0.1F);
/* 282 */         footer.setShrinkToFit(false);
/* 283 */         footer.setBottomBorder(0);
/*     */         
/* 285 */         group = new DefaultSectionLens(null, group, spacer);
/*     */ 
/*     */ 
/*     */         
/* 289 */         aStatusUpdate.updateStatus(selections.size(), n, "start_report", 0);
/*     */ 
/*     */         
/* 292 */         Selection sel = (Selection)selections.elementAt(n);
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 297 */         nextRow = 0;
/* 298 */         subTable = new DefaultTableLens(1, numColumns);
/*     */         
/* 300 */         subTable.setColWidth(0, 40);
/* 301 */         subTable.setColWidth(1, 40);
/* 302 */         subTable.setColWidth(2, 80);
/* 303 */         subTable.setColWidth(3, 100);
/* 304 */         subTable.setColWidth(4, 60);
/* 305 */         subTable.setColWidth(5, 40);
/* 306 */         subTable.setColWidth(6, 50);
/* 307 */         subTable.setColWidth(7, 60);
/* 308 */         subTable.setColWidth(8, 40);
/* 309 */         subTable.setColWidth(9, 60);
/* 310 */         subTable.setColWidth(10, 100);
/*     */ 
/*     */         
/* 313 */         String labelName = "";
/* 314 */         if (sel.getLabel() != null)
/*     */         {
/* 316 */           labelName = sel.getLabel().getName();
/*     */         }
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 322 */         subTable.setRowInsets(nextRow, new Insets(0, 0, 5, 0));
/*     */         
/* 324 */         String impactDate = "  " + MilestoneHelper.getFormatedDate(sel.getImpactDate());
/*     */         
/* 326 */         String streetDate = "  " + MilestoneHelper.getFormatedDate(sel.getStreetDate());
/*     */         
/* 328 */         String status = (sel.getSelectionStatus() != null && sel.getSelectionStatus().getName() != null) ? 
/* 329 */           sel.getSelectionStatus().getName() : "";
/*     */         
/* 331 */         if (status.equalsIgnoreCase("TBS")) {
/* 332 */           streetDate = "TBS " + streetDate;
/*     */         }
/* 334 */         else if (status.equalsIgnoreCase("In The Works")) {
/* 335 */           streetDate = "ITW " + streetDate;
/*     */         } 
/* 337 */         subTable.setObject(nextRow, 0, impactDate);
/* 338 */         subTable.setAlignment(nextRow, 0, 12);
/*     */         
/* 340 */         subTable.setObject(nextRow, 1, streetDate);
/* 341 */         subTable.setAlignment(nextRow, 1, 12);
/*     */         
/* 343 */         subTable.setObject(nextRow, 2, sel.getArtist().trim());
/* 344 */         subTable.setAlignment(nextRow, 2, 9);
/*     */         
/* 346 */         subTable.setObject(nextRow, 3, sel.getTitle());
/* 347 */         subTable.setAlignment(nextRow, 3, 9);
/*     */ 
/*     */         
/* 350 */         String upc = sel.getUpc();
/* 351 */         upc = MilestoneHelper_2.getRMSReportFormat(upc, "UPC", sel.getIsDigital());
/*     */         
/* 353 */         subTable.setObject(nextRow, 4, upc);
/* 354 */         subTable.setAlignment(nextRow, 4, 9);
/*     */         
/* 356 */         subTable.setObject(nextRow, 5, sel.getSelectionPackaging());
/* 357 */         subTable.setAlignment(nextRow, 5, 9);
/*     */         
/* 359 */         subTable.setObject(nextRow, 6, sel.getOtherContact());
/* 360 */         subTable.setAlignment(nextRow, 6, 9);
/*     */ 
/*     */         
/* 363 */         LookupObject prodCategory = SelectionManager.getLookupObject(sel.getProductCategory().getAbbreviation(), 
/* 364 */             Cache.getProductCategories());
/* 365 */         String prodCategoryName = "";
/* 366 */         if (prodCategory != null) {
/* 367 */           prodCategoryName = prodCategory.getName();
/*     */         }
/*     */         
/* 370 */         subTable.setObject(nextRow, 7, prodCategoryName);
/* 371 */         subTable.setAlignment(nextRow, 7, 9);
/*     */         
/* 373 */         String parAdv = "";
/* 374 */         if (sel.getParentalGuidance())
/* 375 */           parAdv = "Yes"; 
/* 376 */         subTable.setObject(nextRow, 8, parAdv);
/* 377 */         subTable.setAlignment(nextRow, 8, 10);
/*     */         
/* 379 */         subTable.setObject(nextRow, 9, sel.getSelectionTerritory());
/* 380 */         subTable.setAlignment(nextRow, 9, 9);
/*     */         
/* 382 */         subTable.setObject(nextRow, 10, sel.getSelectionComments());
/* 383 */         subTable.setAlignment(nextRow, 10, 9);
/*     */ 
/*     */         
/* 386 */         subTable.setColBorder(0);
/* 387 */         subTable.setRowBorderColor(nextRow, Color.white);
/* 388 */         subTable.setRowBorder(nextRow - 1, 0);
/* 389 */         subTable.setRowBorder(nextRow, 4097);
/* 390 */         subTable.setRowBorderColor(nextRow, Color.lightGray);
/* 391 */         subTable.setRowFont(nextRow, new Font("Arial", 0, 8));
/*     */         
/* 393 */         body = new SectionBand(report);
/*     */         
/* 395 */         double lfLineCount = 1.0D;
/*     */         
/* 397 */         body.setHeight(1.0F);
/*     */ 
/*     */         
/* 400 */         if (sel.getSelectionComments().length() > 20) {
/*     */           
/* 402 */           if (lfLineCount < (sel.getSelectionComments().length() / 24) * 0.3D) {
/* 403 */             lfLineCount = (sel.getSelectionComments().length() / 24) * 0.3D;
/*     */           }
/* 405 */           body.setHeight((float)lfLineCount);
/*     */         } 
/*     */         
/* 408 */         body.addTable(subTable, new Rectangle(800, 800));
/* 409 */         body.setBottomBorder(0);
/* 410 */         body.setTopBorder(0);
/* 411 */         body.setShrinkToFit(true);
/* 412 */         body.setVisible(true);
/*     */         
/* 414 */         group = new DefaultSectionLens(null, group, body);
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 420 */       group = new DefaultSectionLens(hbandType, group, null);
/*     */ 
/*     */ 
/*     */       
/* 424 */       report.addSection(group, rowCountTable);
/* 425 */       group = null;
/*     */ 
/*     */ 
/*     */     
/*     */     }
/* 430 */     catch (Exception e) {
/* 431 */       System.out.println(">>>>>>>>ReportHandler.filleInitiativeRelScheduleForPrint(): exception: " + e);
/* 432 */       System.out.println("<<< error " + e.getMessage());
/* 433 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\eInitiativeRelScheduleForPrintSubHandler.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */