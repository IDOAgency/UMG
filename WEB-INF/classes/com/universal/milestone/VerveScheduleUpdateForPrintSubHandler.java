/*      */ package WEB-INF.classes.com.universal.milestone;
/*      */ 
/*      */ import com.techempower.ComponentLog;
/*      */ import com.techempower.gemini.Context;
/*      */ import com.techempower.gemini.GeminiApplication;
/*      */ import com.universal.milestone.Form;
/*      */ import com.universal.milestone.MilestoneHelper;
/*      */ import com.universal.milestone.MilestoneHelper_2;
/*      */ import com.universal.milestone.Schedule;
/*      */ import com.universal.milestone.ScheduledTask;
/*      */ import com.universal.milestone.SecureHandler;
/*      */ import com.universal.milestone.Selection;
/*      */ import com.universal.milestone.SelectionManager;
/*      */ import com.universal.milestone.StringDescComparator;
/*      */ import com.universal.milestone.VerveScheduleUpdateForPrintSubHandler;
/*      */ import inetsoft.report.SectionBand;
/*      */ import inetsoft.report.XStyleSheet;
/*      */ import inetsoft.report.lens.DefaultSectionLens;
/*      */ import inetsoft.report.lens.DefaultTableLens;
/*      */ import java.awt.Color;
/*      */ import java.awt.Dimension;
/*      */ import java.awt.Font;
/*      */ import java.awt.Insets;
/*      */ import java.awt.Rectangle;
/*      */ import java.text.SimpleDateFormat;
/*      */ import java.util.Calendar;
/*      */ import java.util.Collections;
/*      */ import java.util.Date;
/*      */ import java.util.Enumeration;
/*      */ import java.util.Hashtable;
/*      */ import java.util.Iterator;
/*      */ import java.util.Vector;
/*      */ import javax.servlet.http.HttpServletResponse;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class VerveScheduleUpdateForPrintSubHandler
/*      */   extends SecureHandler
/*      */ {
/*      */   public static final String COMPONENT_CODE = "hCProd";
/*      */   public GeminiApplication application;
/*      */   public ComponentLog log;
/*      */   
/*      */   public VerveScheduleUpdateForPrintSubHandler(GeminiApplication application) {
/*   71 */     this.application = application;
/*   72 */     this.log = application.getLog("hCProd");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   80 */   public String getDescription() { return "Sub Report"; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected static void fillVerveScheduleUpdateForPrint(XStyleSheet report, Context context) {
/*   92 */     int COL_LINE_STYLE = 4097;
/*   93 */     int HEADER_FONT_SIZE = 12;
/*      */     
/*   95 */     double ldLineVal = 0.3D;
/*      */ 
/*      */     
/*      */     try {
/*   99 */       HttpServletResponse sresponse = context.getResponse();
/*  100 */       context.putDelivery("status", new String("start_gathering"));
/*  101 */       context.includeJSP("status.jsp", "hiddenFrame");
/*  102 */       sresponse.setContentType("text/plain");
/*  103 */       sresponse.flushBuffer();
/*      */     }
/*  105 */     catch (Exception exception) {}
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  110 */     Vector selections = MilestoneHelper.getSelectionsForReport(context);
/*      */ 
/*      */     
/*      */     try {
/*  114 */       HttpServletResponse sresponse = context.getResponse();
/*  115 */       context.putDelivery("status", new String("start_report"));
/*  116 */       context.putDelivery("percent", new String("10"));
/*  117 */       context.includeJSP("status.jsp", "hiddenFrame");
/*  118 */       sresponse.setContentType("text/plain");
/*  119 */       sresponse.flushBuffer();
/*      */     }
/*  121 */     catch (Exception exception) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/*  131 */       Form reportForm = (Form)context.getSessionValue("reportForm");
/*      */       
/*  133 */       Calendar beginStDate = (reportForm.getStringValue("beginDate") != null && 
/*  134 */         reportForm.getStringValue("beginDate").length() > 0) ? 
/*  135 */         MilestoneHelper.getDate(reportForm.getStringValue("beginDate")) : null;
/*      */       
/*  137 */       Calendar endStDate = (reportForm.getStringValue("endDate") != null && 
/*  138 */         reportForm.getStringValue("endDate").length() > 0) ? 
/*  139 */         MilestoneHelper.getDate(reportForm.getStringValue("endDate")) : null;
/*      */       
/*  141 */       report.setElement("crs_startdate", MilestoneHelper.getFormatedDate(beginStDate));
/*  142 */       report.setElement("crs_enddate", MilestoneHelper.getFormatedDate(endStDate));
/*      */       
/*  144 */       SimpleDateFormat formatter = new SimpleDateFormat("MMMM d, yyyy");
/*  145 */       String todayLong = formatter.format(new Date());
/*  146 */       report.setElement("crs_bottomdate", todayLong);
/*      */       
/*  148 */       SectionBand hbandType = new SectionBand(report);
/*  149 */       SectionBand hbandCategory = new SectionBand(report);
/*  150 */       SectionBand body = new SectionBand(report);
/*  151 */       SectionBand footer = new SectionBand(report);
/*  152 */       DefaultSectionLens group = null;
/*      */ 
/*      */       
/*  155 */       Hashtable selTable = MilestoneHelper.groupSelectionsByTypeAndCategory(selections);
/*  156 */       Enumeration types = selTable.keys();
/*  157 */       Vector typeVector = new Vector();
/*      */ 
/*      */       
/*  160 */       int numOfSelectionsInAdvanceCategory = 0;
/*  161 */       Vector toIgnore = new Vector();
/*      */       
/*  163 */       while (types.hasMoreElements()) {
/*  164 */         typeVector.addElement(types.nextElement());
/*      */       }
/*      */       
/*  167 */       Collections.sort(typeVector);
/*      */       
/*  169 */       DefaultTableLens table_contents = null;
/*  170 */       DefaultTableLens rowCountTable = null;
/*  171 */       DefaultTableLens columnHeaderTable = null;
/*      */ 
/*      */ 
/*      */       
/*  175 */       for (int n = 0; n < typeVector.size(); n++)
/*      */       {
/*  177 */         String type = (String)typeVector.elementAt(n);
/*  178 */         String typeHeaderText = !type.trim().equals("") ? type : "Other";
/*      */ 
/*      */         
/*  181 */         Hashtable categoryTable = (Hashtable)selTable.get(type);
/*      */         
/*  183 */         DefaultTableLens subTable = null;
/*      */         
/*  185 */         if (categoryTable != null) {
/*      */           
/*  187 */           Enumeration categories = categoryTable.keys();
/*  188 */           Vector currentTypeCategories = new Vector();
/*  189 */           while (categories.hasMoreElements()) {
/*  190 */             currentTypeCategories.add((String)categories.nextElement());
/*      */           }
/*  192 */           Collections.sort(currentTypeCategories, new StringDescComparator());
/*  193 */           Iterator theCategories = currentTypeCategories.iterator();
/*      */           
/*  195 */           int categoryCount = 0;
/*      */           
/*  197 */           while (theCategories.hasNext()) {
/*      */             
/*  199 */             String category = (String)theCategories.next();
/*  200 */             selections = (Vector)categoryTable.get(category);
/*      */ 
/*      */             
/*  203 */             if (category.equalsIgnoreCase("Advances")) {
/*      */               continue;
/*      */             }
/*      */             
/*  207 */             int numSelections = selections.size() * 2;
/*  208 */             int numRows = numSelections + 5;
/*      */ 
/*      */             
/*  211 */             rowCountTable = new DefaultTableLens(2, 10000);
/*  212 */             table_contents = new DefaultTableLens(1, 17);
/*      */ 
/*      */             
/*  215 */             int nextRow = 0;
/*  216 */             hbandType = new SectionBand(report);
/*  217 */             hbandType.setHeight(0.95F);
/*  218 */             hbandType.setShrinkToFit(true);
/*  219 */             hbandType.setVisible(true);
/*      */             
/*  221 */             hbandCategory = new SectionBand(report);
/*  222 */             hbandCategory.setHeight(2.0F);
/*  223 */             hbandCategory.setShrinkToFit(true);
/*  224 */             hbandCategory.setVisible(true);
/*  225 */             hbandCategory.setBottomBorder(0);
/*  226 */             hbandCategory.setLeftBorder(0);
/*  227 */             hbandCategory.setRightBorder(0);
/*  228 */             hbandCategory.setTopBorder(0);
/*      */ 
/*      */             
/*  231 */             table_contents.setHeaderRowCount(0);
/*      */ 
/*      */ 
/*      */             
/*  235 */             table_contents.setColWidth(0, 95);
/*  236 */             table_contents.setColWidth(1, 225);
/*  237 */             table_contents.setColWidth(2, 140);
/*  238 */             table_contents.setColWidth(3, 70);
/*  239 */             table_contents.setColWidth(4, 70);
/*  240 */             table_contents.setColWidth(5, 70);
/*  241 */             table_contents.setColWidth(6, 70);
/*  242 */             table_contents.setColWidth(7, 70);
/*  243 */             table_contents.setColWidth(8, 70);
/*  244 */             table_contents.setColWidth(9, 70);
/*  245 */             table_contents.setColWidth(10, 70);
/*  246 */             table_contents.setColWidth(11, 70);
/*  247 */             table_contents.setColWidth(12, 70);
/*  248 */             table_contents.setColWidth(13, 70);
/*  249 */             table_contents.setColWidth(14, 70);
/*  250 */             table_contents.setColWidth(15, 70);
/*  251 */             table_contents.setColWidth(16, 180);
/*      */ 
/*      */             
/*  254 */             table_contents.setRowBorderColor(nextRow, Color.lightGray);
/*  255 */             table_contents.setRowBorder(4097);
/*  256 */             table_contents.setColBorder(4097);
/*      */ 
/*      */             
/*  259 */             if (typeHeaderText.equals("Promotional"))
/*      */             {
/*  261 */               typeHeaderText = "Promos";
/*      */             }
/*      */             
/*  264 */             int cols = 17;
/*      */             
/*  266 */             table_contents.setObject(nextRow, 0, "");
/*  267 */             table_contents.setSpan(nextRow, 0, new Dimension(cols, 1));
/*  268 */             table_contents.setRowHeight(nextRow, 15);
/*  269 */             table_contents.setRowBackground(nextRow, Color.white);
/*      */             
/*  271 */             for (int col = -1; col < cols; col++) {
/*      */               
/*  273 */               table_contents.setColBorderColor(nextRow, col, Color.black);
/*  274 */               table_contents.setColBorder(nextRow, col, 4097);
/*      */             } 
/*      */             
/*  277 */             table_contents.setRowBorderColor(nextRow, Color.black);
/*  278 */             table_contents.setRowBorder(nextRow, 266240);
/*      */ 
/*      */             
/*  281 */             table_contents.setAlignment(nextRow, 0, 2);
/*  282 */             table_contents.setObject(nextRow, 0, typeHeaderText);
/*  283 */             table_contents.setRowFont(nextRow, new Font("Arial", 3, 12));
/*      */             
/*  285 */             nextRow++;
/*  286 */             hbandType.addTable(table_contents, new Rectangle(0, 0, 800, 25));
/*      */             
/*  288 */             columnHeaderTable = new DefaultTableLens(1, 17);
/*      */             
/*  290 */             nextRow = 0;
/*      */             
/*  292 */             columnHeaderTable.setColWidth(0, 95);
/*  293 */             columnHeaderTable.setColWidth(1, 225);
/*  294 */             columnHeaderTable.setColWidth(2, 140);
/*  295 */             columnHeaderTable.setColWidth(3, 70);
/*  296 */             columnHeaderTable.setColWidth(4, 70);
/*  297 */             columnHeaderTable.setColWidth(5, 70);
/*  298 */             columnHeaderTable.setColWidth(6, 70);
/*  299 */             columnHeaderTable.setColWidth(7, 70);
/*  300 */             columnHeaderTable.setColWidth(8, 70);
/*  301 */             columnHeaderTable.setColWidth(9, 70);
/*  302 */             columnHeaderTable.setColWidth(10, 70);
/*  303 */             columnHeaderTable.setColWidth(11, 70);
/*  304 */             columnHeaderTable.setColWidth(12, 70);
/*  305 */             columnHeaderTable.setColWidth(13, 70);
/*  306 */             columnHeaderTable.setColWidth(14, 70);
/*  307 */             columnHeaderTable.setColWidth(15, 70);
/*  308 */             columnHeaderTable.setColWidth(16, 180);
/*      */             
/*  310 */             columnHeaderTable.setRowAlignment(nextRow, 34);
/*  311 */             columnHeaderTable.setObject(nextRow, 0, "U.S. &\nInt'l\nRelease");
/*  312 */             columnHeaderTable.setObject(nextRow, 1, "UPC\nUS #/Int'l\nArtist/Title");
/*  313 */             columnHeaderTable.setObject(nextRow, 2, "Label &\nPackage");
/*  314 */             columnHeaderTable.setObject(nextRow, 3, "Photo\nShoot");
/*  315 */             columnHeaderTable.setObject(nextRow, 4, "Master\nto plant");
/*  316 */             columnHeaderTable.setObject(nextRow, 5, "Manf.\nCopy\nShips");
/*  317 */             columnHeaderTable.setObject(nextRow, 6, "Package\nCopy");
/*  318 */             columnHeaderTable.setObject(nextRow, 7, "Sticker\nCopy");
/*  319 */             columnHeaderTable.setObject(nextRow, 8, "Adv.\nCDs");
/*  320 */             columnHeaderTable.setObject(nextRow, 9, "Cover\nArt");
/*  321 */             columnHeaderTable.setObject(nextRow, 10, "Seps");
/*  322 */             columnHeaderTable.setObject(nextRow, 11, "Japan");
/*  323 */             columnHeaderTable.setObject(nextRow, 12, "Package\nFilm\nShip");
/*  324 */             columnHeaderTable.setObject(nextRow, 13, "Package\nFilm");
/*  325 */             columnHeaderTable.setObject(nextRow, 14, "Print\nat Plant");
/*  326 */             columnHeaderTable.setObject(nextRow, 15, "Depot");
/*  327 */             columnHeaderTable.setObject(nextRow, 16, "Comments");
/*  328 */             columnHeaderTable.setRowFont(nextRow, new Font("Arial", 1, 8));
/*  329 */             columnHeaderTable.setRowBorderColor(nextRow, Color.lightGray);
/*  330 */             columnHeaderTable.setRowBorderColor(nextRow - 1, Color.lightGray);
/*      */             
/*  332 */             columnHeaderTable.setColBorderColor(nextRow, -1, Color.lightGray);
/*  333 */             columnHeaderTable.setColBorderColor(nextRow, 0, Color.lightGray);
/*  334 */             columnHeaderTable.setColBorderColor(nextRow, 1, Color.lightGray);
/*  335 */             columnHeaderTable.setColBorderColor(nextRow, 2, Color.lightGray);
/*  336 */             columnHeaderTable.setColBorderColor(nextRow, 3, Color.lightGray);
/*  337 */             columnHeaderTable.setColBorderColor(nextRow, 4, Color.lightGray);
/*  338 */             columnHeaderTable.setColBorderColor(nextRow, 5, Color.lightGray);
/*  339 */             columnHeaderTable.setColBorderColor(nextRow, 6, Color.lightGray);
/*  340 */             columnHeaderTable.setColBorderColor(nextRow, 7, Color.lightGray);
/*  341 */             columnHeaderTable.setColBorderColor(nextRow, 8, Color.lightGray);
/*  342 */             columnHeaderTable.setColBorderColor(nextRow, 9, Color.lightGray);
/*  343 */             columnHeaderTable.setColBorderColor(nextRow, 10, Color.lightGray);
/*  344 */             columnHeaderTable.setColBorderColor(nextRow, 11, Color.lightGray);
/*  345 */             columnHeaderTable.setColBorderColor(nextRow, 12, Color.lightGray);
/*  346 */             columnHeaderTable.setColBorderColor(nextRow, 13, Color.lightGray);
/*  347 */             columnHeaderTable.setColBorderColor(nextRow, 14, Color.lightGray);
/*  348 */             columnHeaderTable.setColBorderColor(nextRow, 15, Color.lightGray);
/*  349 */             columnHeaderTable.setColBorderColor(nextRow, 16, Color.lightGray);
/*      */             
/*  351 */             columnHeaderTable.setRowBorderColor(nextRow, Color.lightGray);
/*  352 */             nextRow++;
/*      */             
/*  354 */             hbandType.addTable(columnHeaderTable, new Rectangle(0, 24, 800, 39));
/*      */             
/*  356 */             hbandType.setBottomBorder(0);
/*      */ 
/*      */ 
/*      */             
/*  360 */             DefaultTableLens categoryTableLens = new DefaultTableLens(1, 17);
/*      */             
/*  362 */             nextRow = 0;
/*      */ 
/*      */             
/*  365 */             categoryTableLens.setObject(nextRow, 0, category);
/*  366 */             categoryTableLens.setSpan(nextRow, 0, new Dimension(17, 1));
/*  367 */             categoryTableLens.setRowFont(nextRow, new Font("Arial", 1, 12));
/*  368 */             categoryTableLens.setRowHeight(nextRow, 20);
/*  369 */             categoryTableLens.setColBorderColor(nextRow, -1, Color.white);
/*  370 */             categoryTableLens.setColBorderColor(nextRow, 16, Color.white);
/*  371 */             categoryTableLens.setRowBorderColor(nextRow, Color.lightGray);
/*  372 */             categoryTableLens.setRowBorderColor(nextRow - 1, Color.lightGray);
/*      */             
/*  374 */             nextRow++;
/*      */             
/*  376 */             hbandCategory.addTable(categoryTableLens, new Rectangle(800, 800));
/*  377 */             hbandCategory.setBottomBorder(0);
/*      */             
/*  379 */             footer.setVisible(false);
/*  380 */             footer.setHeight(0.1F);
/*  381 */             footer.setShrinkToFit(true);
/*  382 */             footer.setBottomBorder(0);
/*      */             
/*  384 */             group = new DefaultSectionLens(null, group, hbandCategory);
/*      */             
/*  386 */             nextRow = 0;
/*      */ 
/*      */             
/*  389 */             Hashtable sortedByStatus = MilestoneHelper.groupSelectionsByStatus(selections);
/*      */             
/*  391 */             if (sortedByStatus != null) {
/*      */               
/*  393 */               Vector statuses = new Vector();
/*  394 */               Enumeration selectionsSoredByStatus = sortedByStatus.keys();
/*  395 */               while (selectionsSoredByStatus.hasMoreElements()) {
/*  396 */                 String status = (String)selectionsSoredByStatus.nextElement();
/*  397 */                 statuses.add(status);
/*      */               } 
/*  399 */               Collections.sort(statuses);
/*  400 */               Iterator theStatuses = statuses.iterator();
/*      */               
/*  402 */               int statusCount = 0;
/*      */               
/*  404 */               while (theStatuses != null && theStatuses.hasNext()) {
/*      */                 
/*  406 */                 String status = (String)theStatuses.next();
/*      */                 
/*  408 */                 selections = (Vector)sortedByStatus.get(status);
/*      */                 
/*  410 */                 if (selections != null) {
/*      */                   
/*  412 */                   Vector allSelections = MilestoneHelper.groupSelectionsByReleaseMonth(selections);
/*      */                   
/*  414 */                   if (allSelections == null) {
/*  415 */                     allSelections = new Vector();
/*      */                   }
/*  417 */                   for (int i = 0; i < allSelections.size(); i++) {
/*      */                     
/*  419 */                     selections = (Vector)allSelections.elementAt(i);
/*      */                     
/*  421 */                     if (selections != null && !selections.isEmpty()) {
/*      */ 
/*      */                       
/*  424 */                       int commentLines = 0;
/*      */ 
/*      */                       
/*  427 */                       for (int j = 0; j < selections.size(); j++) {
/*      */                         
/*  429 */                         Selection sel = (Selection)selections.elementAt(j);
/*  430 */                         sel = SelectionManager.getInstance().getSelectionAndSchedule(sel.getSelectionID());
/*      */ 
/*      */                         
/*  433 */                         String USIntRelease = "";
/*  434 */                         if (SelectionManager.getLookupObjectValue(sel.getSelectionStatus()).equalsIgnoreCase("TBS")) {
/*  435 */                           USIntRelease = "TBS ";
/*  436 */                         } else if (SelectionManager.getLookupObjectValue(sel.getSelectionStatus()).equalsIgnoreCase("ITW")) {
/*  437 */                           USIntRelease = "ITW ";
/*      */                         } 
/*  439 */                         if (USIntRelease != null && !USIntRelease.equals("")) {
/*      */                           
/*  441 */                           USIntRelease = String.valueOf(USIntRelease) + MilestoneHelper.getFormatedDate(sel.getStreetDate());
/*      */                         }
/*      */                         else {
/*      */                           
/*  445 */                           USIntRelease = String.valueOf(USIntRelease) + MilestoneHelper.getFormatedDate(sel.getStreetDate()) + "\n" + MilestoneHelper.getFormatedDate(sel.getInternationalDate());
/*      */                         } 
/*      */ 
/*      */                         
/*  449 */                         String upc = (sel.getUpc() != null) ? sel.getUpc() : " ";
/*      */ 
/*      */                         
/*  452 */                         upc = MilestoneHelper_2.getRMSReportFormat(upc, "UPC", sel.getIsDigital());
/*      */ 
/*      */                         
/*  455 */                         String localProductNumber = "";
/*  456 */                         if (sel.getPrefixID() != null && SelectionManager.getLookupObjectValue(sel.getPrefixID()) != null) {
/*  457 */                           localProductNumber = SelectionManager.getLookupObjectValue(sel.getPrefixID());
/*      */                         }
/*      */                         
/*  460 */                         localProductNumber = String.valueOf(localProductNumber) + sel.getSelectionNo();
/*      */ 
/*      */                         
/*  463 */                         String ArtistTile = "";
/*      */ 
/*      */                         
/*  466 */                         ArtistTile = String.valueOf(ArtistTile) + localProductNumber + "\n";
/*  467 */                         ArtistTile = String.valueOf(ArtistTile) + sel.getArtist() + "\n";
/*  468 */                         ArtistTile = String.valueOf(ArtistTile) + sel.getTitle();
/*      */ 
/*      */                         
/*  471 */                         String comment = (sel.getSelectionComments() != null) ? sel.getSelectionComments() : " ";
/*  472 */                         String label = (sel.getLabel() != null) ? sel.getLabel().getName() : " ";
/*  473 */                         String packaging = (sel.getSelectionPackaging() != null) ? sel.getSelectionPackaging() : " ";
/*      */                         
/*  475 */                         commentLines = MilestoneHelper.lineCount(comment, "");
/*      */                         
/*  477 */                         String labelAndPackage = String.valueOf(label) + "\n" + packaging;
/*      */                         
/*  479 */                         for (int y = 0; y < commentLines; y++)
/*      */                         {
/*  481 */                           labelAndPackage = String.valueOf(labelAndPackage) + "\n";
/*      */                         }
/*      */                         
/*  484 */                         Schedule schedule = sel.getSchedule();
/*      */ 
/*      */                         
/*  487 */                         String photoShootDate = "", masterToPlantDate = "", manfCopyShipsDate = "";
/*  488 */                         String packageCopyDate = "", stickerCopyDate = "", advCDsDate = "", coverArtDate = "";
/*  489 */                         String sepsDate = "", japanDate = "", packageFilmShipDate = "", packageFilmDate = "";
/*  490 */                         String printAtPlantDate = "", depotDate = "";
/*      */ 
/*      */                         
/*  493 */                         String photoShoot = "", masterToPlant = "", manfCopyShips = "";
/*  494 */                         String packageCopy = "", stickerCopy = "", advCDs = "", coverArt = "";
/*  495 */                         String seps = "", japan = "", packageFilmShip = "", packageFilm = "";
/*  496 */                         String printAtPlant = "", depot = "";
/*      */                         
/*  498 */                         Vector tasks = null;
/*  499 */                         if (schedule != null)
/*      */                         {
/*  501 */                           tasks = schedule.getTasks();
/*      */                         }
/*      */                         
/*  504 */                         if (tasks != null) {
/*      */                           
/*  506 */                           Iterator it = tasks.iterator();
/*      */                           
/*  508 */                           int dayTypeCalendarGroup = sel.getCalendarGroup();
/*      */                           
/*  510 */                           while (it != null && it.hasNext()) {
/*      */                             
/*  512 */                             ScheduledTask task = (ScheduledTask)it.next();
/*  513 */                             String name = task.getName();
/*      */                             
/*      */                             try {
/*  516 */                               if (name != null) {
/*      */                                 
/*  518 */                                 name = name.trim();
/*  519 */                                 if (name.equalsIgnoreCase("Photo shoot complete")) {
/*      */ 
/*      */                                   
/*  522 */                                   photoShootDate = String.valueOf(MilestoneHelper.getShortDate(task.getDueDate())) + 
/*  523 */                                     " " + MilestoneHelper.getDayType(dayTypeCalendarGroup, task.getDueDate());
/*  524 */                                   if (task.getScheduledTaskStatus() != null && 
/*  525 */                                     task.getScheduledTaskStatus().equals("N/A")) {
/*      */                                     
/*  527 */                                     photoShoot = "N/A";
/*      */                                   }
/*      */                                   else {
/*      */                                     
/*  531 */                                     photoShoot = MilestoneHelper.getFormatedDate(task.getCompletionDate());
/*      */                                   } 
/*  533 */                                   photoShoot = String.valueOf(photoShoot) + "\n";
/*  534 */                                   photoShoot = String.valueOf(photoShoot) + ((task.getComments() != null) ? task.getComments() : ""); continue;
/*      */                                 } 
/*  536 */                                 if (name.equalsIgnoreCase("Master tapes ship to plant")) {
/*      */ 
/*      */                                   
/*  539 */                                   masterToPlantDate = String.valueOf(MilestoneHelper.getShortDate(task.getDueDate())) + 
/*  540 */                                     " " + MilestoneHelper.getDayType(dayTypeCalendarGroup, task.getDueDate());
/*  541 */                                   if (task.getScheduledTaskStatus() != null && 
/*  542 */                                     task.getScheduledTaskStatus().equals("N/A")) {
/*      */                                     
/*  544 */                                     masterToPlant = "N/A";
/*      */                                   } else {
/*      */                                     
/*  547 */                                     masterToPlant = MilestoneHelper.getFormatedDate(task.getCompletionDate());
/*      */                                   } 
/*  549 */                                   masterToPlant = String.valueOf(masterToPlant) + "\n";
/*  550 */                                   masterToPlant = String.valueOf(masterToPlant) + ((task.getComments() != null) ? task.getComments() : " "); continue;
/*      */                                 } 
/*  552 */                                 if (name.equalsIgnoreCase("Manufacturing copy ships")) {
/*      */ 
/*      */                                   
/*  555 */                                   manfCopyShipsDate = String.valueOf(MilestoneHelper.getShortDate(task.getDueDate())) + 
/*  556 */                                     " " + MilestoneHelper.getDayType(dayTypeCalendarGroup, task.getDueDate());
/*  557 */                                   if (task.getScheduledTaskStatus() != null && 
/*  558 */                                     task.getScheduledTaskStatus().equals("N/A")) {
/*      */                                     
/*  560 */                                     manfCopyShips = "N/A";
/*      */                                   } else {
/*      */                                     
/*  563 */                                     manfCopyShips = MilestoneHelper.getFormatedDate(task.getCompletionDate());
/*      */                                   } 
/*  565 */                                   manfCopyShips = String.valueOf(manfCopyShips) + "\n";
/*  566 */                                   manfCopyShips = String.valueOf(manfCopyShips) + ((task.getComments() != null) ? task.getComments() : " "); continue;
/*      */                                 } 
/*  568 */                                 if (name.equalsIgnoreCase("Final packaging copy due")) {
/*      */ 
/*      */                                   
/*  571 */                                   packageCopyDate = String.valueOf(MilestoneHelper.getShortDate(task.getDueDate())) + 
/*  572 */                                     " " + MilestoneHelper.getDayType(dayTypeCalendarGroup, task.getDueDate());
/*  573 */                                   if (task.getScheduledTaskStatus() != null && 
/*  574 */                                     task.getScheduledTaskStatus().equals("N/A")) {
/*      */                                     
/*  576 */                                     packageCopy = "N/A";
/*      */                                   } else {
/*      */                                     
/*  579 */                                     packageCopy = MilestoneHelper.getFormatedDate(task.getCompletionDate());
/*      */                                   } 
/*  581 */                                   packageCopy = String.valueOf(packageCopy) + "\n";
/*  582 */                                   packageCopy = String.valueOf(packageCopy) + ((task.getComments() != null) ? task.getComments() : " "); continue;
/*      */                                 } 
/*  584 */                                 if (name.equalsIgnoreCase("Final sticker copy due")) {
/*      */ 
/*      */                                   
/*  587 */                                   stickerCopyDate = String.valueOf(MilestoneHelper.getShortDate(task.getDueDate())) + 
/*  588 */                                     " " + MilestoneHelper.getDayType(dayTypeCalendarGroup, task.getDueDate());
/*  589 */                                   if (task.getScheduledTaskStatus() != null && 
/*  590 */                                     task.getScheduledTaskStatus().equals("N/A")) {
/*      */                                     
/*  592 */                                     stickerCopy = "N/A";
/*      */                                   }
/*      */                                   else {
/*      */                                     
/*  596 */                                     stickerCopy = MilestoneHelper.getFormatedDate(task.getCompletionDate());
/*      */                                   } 
/*  598 */                                   stickerCopy = String.valueOf(stickerCopy) + "\n";
/*  599 */                                   stickerCopy = String.valueOf(stickerCopy) + ((task.getComments() != null) ? task.getComments() : " "); continue;
/*      */                                 } 
/*  601 */                                 if (name.equalsIgnoreCase("Cover art due")) {
/*      */ 
/*      */                                   
/*  604 */                                   coverArtDate = String.valueOf(MilestoneHelper.getShortDate(task.getDueDate())) + 
/*  605 */                                     " " + MilestoneHelper.getDayType(dayTypeCalendarGroup, task.getDueDate());
/*  606 */                                   if (task.getScheduledTaskStatus() != null && 
/*  607 */                                     task.getScheduledTaskStatus().equals("N/A")) {
/*      */                                     
/*  609 */                                     coverArt = "N/A";
/*      */                                   } else {
/*      */                                     
/*  612 */                                     coverArt = MilestoneHelper.getFormatedDate(task.getCompletionDate());
/*      */                                   } 
/*  614 */                                   coverArt = String.valueOf(coverArt) + "\n";
/*  615 */                                   coverArt = String.valueOf(coverArt) + ((task.getComments() != null) ? task.getComments() : " "); continue;
/*      */                                 } 
/*  617 */                                 if (name.equalsIgnoreCase("Package film ships to printer")) {
/*      */ 
/*      */                                   
/*  620 */                                   packageFilmShipDate = String.valueOf(MilestoneHelper.getShortDate(task.getDueDate())) + 
/*  621 */                                     " " + MilestoneHelper.getDayType(dayTypeCalendarGroup, task.getDueDate());
/*  622 */                                   if (task.getScheduledTaskStatus() != null && 
/*  623 */                                     task.getScheduledTaskStatus().equals("N/A")) {
/*      */                                     
/*  625 */                                     packageFilmShip = "N/A";
/*      */                                   } else {
/*      */                                     
/*  628 */                                     packageFilmShip = MilestoneHelper.getFormatedDate(task.getCompletionDate());
/*      */                                   } 
/*  630 */                                   packageFilmShip = String.valueOf(packageFilmShip) + "\n";
/*  631 */                                   packageFilmShip = String.valueOf(packageFilmShip) + ((task.getComments() != null) ? task.getComments() : " "); continue;
/*      */                                 } 
/*  633 */                                 if (name.equalsIgnoreCase("Print received at Plant")) {
/*      */ 
/*      */                                   
/*  636 */                                   printAtPlantDate = String.valueOf(MilestoneHelper.getShortDate(task.getDueDate())) + 
/*  637 */                                     " " + MilestoneHelper.getDayType(dayTypeCalendarGroup, task.getDueDate());
/*  638 */                                   if (task.getScheduledTaskStatus() != null && 
/*  639 */                                     task.getScheduledTaskStatus().equals("N/A")) {
/*      */                                     
/*  641 */                                     printAtPlant = "N/A";
/*      */                                   } else {
/*      */                                     
/*  644 */                                     printAtPlant = MilestoneHelper.getFormatedDate(task.getCompletionDate());
/*      */                                   } 
/*  646 */                                   printAtPlant = String.valueOf(printAtPlant) + "\n";
/*  647 */                                   printAtPlant = String.valueOf(printAtPlant) + ((task.getComments() != null) ? task.getComments() : " "); continue;
/*      */                                 } 
/*  649 */                                 if (name.equalsIgnoreCase("Depot date")) {
/*      */ 
/*      */                                   
/*  652 */                                   depotDate = String.valueOf(MilestoneHelper.getShortDate(task.getDueDate())) + 
/*  653 */                                     " " + MilestoneHelper.getDayType(dayTypeCalendarGroup, task.getDueDate());
/*  654 */                                   if (task.getScheduledTaskStatus() != null && 
/*  655 */                                     task.getScheduledTaskStatus().equals("N/A")) {
/*      */                                     
/*  657 */                                     depot = "N/A";
/*      */                                   } else {
/*      */                                     
/*  660 */                                     depot = MilestoneHelper.getFormatedDate(task.getCompletionDate());
/*      */                                   } 
/*  662 */                                   depot = String.valueOf(depot) + "\n";
/*  663 */                                   depot = String.valueOf(depot) + ((task.getComments() != null) ? task.getComments() : " "); continue;
/*      */                                 } 
/*  665 */                                 if (name.equalsIgnoreCase("Mechanical to separations")) {
/*      */ 
/*      */                                   
/*  668 */                                   sepsDate = String.valueOf(MilestoneHelper.getShortDate(task.getDueDate())) + 
/*  669 */                                     " " + MilestoneHelper.getDayType(dayTypeCalendarGroup, task.getDueDate());
/*  670 */                                   if (task.getScheduledTaskStatus() != null && 
/*  671 */                                     task.getScheduledTaskStatus().equals("N/A")) {
/*      */                                     
/*  673 */                                     seps = "N/A";
/*      */                                   } else {
/*      */                                     
/*  676 */                                     seps = MilestoneHelper.getFormatedDate(task.getCompletionDate());
/*      */                                   } 
/*  678 */                                   seps = String.valueOf(seps) + "\n";
/*  679 */                                   seps = String.valueOf(seps) + ((task.getComments() != null) ? task.getComments() : " "); continue;
/*      */                                 } 
/*  681 */                                 if (name.equalsIgnoreCase("Japan due")) {
/*      */ 
/*      */                                   
/*  684 */                                   japanDate = String.valueOf(MilestoneHelper.getShortDate(task.getDueDate())) + 
/*  685 */                                     " " + MilestoneHelper.getDayType(dayTypeCalendarGroup, task.getDueDate());
/*  686 */                                   if (task.getScheduledTaskStatus() != null && 
/*  687 */                                     task.getScheduledTaskStatus().equals("N/A")) {
/*      */                                     
/*  689 */                                     japan = "N/A";
/*      */                                   } else {
/*      */                                     
/*  692 */                                     japan = MilestoneHelper.getFormatedDate(task.getCompletionDate());
/*      */                                   } 
/*  694 */                                   japan = String.valueOf(japan) + "\n";
/*  695 */                                   japan = String.valueOf(japan) + ((task.getComments() != null) ? task.getComments() : " "); continue;
/*      */                                 } 
/*  697 */                                 if (name.equalsIgnoreCase("Advanced CDs ordered")) {
/*      */ 
/*      */                                   
/*  700 */                                   advCDsDate = String.valueOf(MilestoneHelper.getShortDate(task.getDueDate())) + 
/*  701 */                                     " " + MilestoneHelper.getDayType(dayTypeCalendarGroup, task.getDueDate());
/*  702 */                                   if (task.getScheduledTaskStatus() != null && 
/*  703 */                                     task.getScheduledTaskStatus().equals("N/A")) {
/*      */                                     
/*  705 */                                     advCDs = "N/A";
/*      */                                   } else {
/*      */                                     
/*  708 */                                     advCDs = MilestoneHelper.getFormatedDate(task.getCompletionDate());
/*      */                                   } 
/*  710 */                                   advCDs = String.valueOf(advCDs) + "\n";
/*  711 */                                   advCDs = String.valueOf(advCDs) + ((task.getComments() != null) ? task.getComments() : " "); continue;
/*      */                                 } 
/*  713 */                                 if (name.equalsIgnoreCase("Package Film received by Printer"))
/*      */                                 {
/*      */                                   
/*  716 */                                   packageFilmDate = String.valueOf(MilestoneHelper.getShortDate(task.getDueDate())) + 
/*  717 */                                     " " + MilestoneHelper.getDayType(dayTypeCalendarGroup, task.getDueDate());
/*  718 */                                   if (task.getScheduledTaskStatus() != null && 
/*  719 */                                     task.getScheduledTaskStatus().equals("N/A")) {
/*      */                                     
/*  721 */                                     packageFilm = "N/A";
/*      */                                   } else {
/*      */                                     
/*  724 */                                     packageFilm = MilestoneHelper.getFormatedDate(task.getCompletionDate());
/*      */                                   } 
/*  726 */                                   packageFilm = String.valueOf(packageFilm) + "\n";
/*  727 */                                   packageFilm = String.valueOf(packageFilm) + ((task.getComments() != null) ? task.getComments() : " ");
/*      */                                 }
/*      */                               
/*      */                               } 
/*  731 */                             } catch (Exception e) {
/*      */                               
/*  733 */                               e.printStackTrace();
/*      */                             } 
/*      */                           } 
/*      */                         } 
/*      */                         
/*  738 */                         nextRow = 0;
/*      */                         
/*  740 */                         subTable = new DefaultTableLens(2, 17);
/*      */                         
/*  742 */                         subTable.setColWidth(0, 95);
/*  743 */                         subTable.setColWidth(1, 225);
/*  744 */                         subTable.setColWidth(2, 140);
/*  745 */                         subTable.setColWidth(3, 70);
/*  746 */                         subTable.setColWidth(4, 70);
/*  747 */                         subTable.setColWidth(5, 70);
/*  748 */                         subTable.setColWidth(6, 70);
/*  749 */                         subTable.setColWidth(7, 70);
/*  750 */                         subTable.setColWidth(8, 70);
/*  751 */                         subTable.setColWidth(9, 70);
/*  752 */                         subTable.setColWidth(10, 70);
/*  753 */                         subTable.setColWidth(11, 70);
/*  754 */                         subTable.setColWidth(12, 70);
/*  755 */                         subTable.setColWidth(13, 70);
/*  756 */                         subTable.setColWidth(14, 70);
/*  757 */                         subTable.setColWidth(15, 70);
/*  758 */                         subTable.setColWidth(16, 180);
/*      */                         
/*  760 */                         subTable.setColBorderColor(nextRow, Color.lightGray);
/*  761 */                         subTable.setRowBorderColor(nextRow, Color.lightGray);
/*  762 */                         subTable.setRowBorder(4097);
/*  763 */                         subTable.setColBorder(4097);
/*      */ 
/*      */                         
/*  766 */                         subTable.setRowInsets(nextRow, new Insets(0, 0, 0, 0));
/*      */                         
/*  768 */                         subTable.setRowAlignment(nextRow, 2);
/*      */ 
/*      */                         
/*  771 */                         subTable.setSpan(nextRow, 0, new Dimension(1, 2));
/*  772 */                         subTable.setObject(nextRow, 0, USIntRelease);
/*  773 */                         subTable.setObject(nextRow, 1, upc);
/*  774 */                         subTable.setObject(nextRow, 2, "Due Dates");
/*  775 */                         subTable.setObject(nextRow, 3, photoShootDate);
/*  776 */                         subTable.setObject(nextRow, 4, masterToPlantDate);
/*  777 */                         subTable.setObject(nextRow, 5, manfCopyShipsDate);
/*  778 */                         subTable.setObject(nextRow, 6, packageCopyDate);
/*  779 */                         subTable.setObject(nextRow, 7, stickerCopyDate);
/*  780 */                         subTable.setObject(nextRow, 8, advCDsDate);
/*  781 */                         subTable.setObject(nextRow, 9, coverArtDate);
/*  782 */                         subTable.setObject(nextRow, 10, sepsDate);
/*  783 */                         subTable.setObject(nextRow, 11, japanDate);
/*  784 */                         subTable.setObject(nextRow, 12, packageFilmShipDate);
/*  785 */                         subTable.setObject(nextRow, 13, packageFilmDate);
/*  786 */                         subTable.setObject(nextRow, 14, printAtPlantDate);
/*  787 */                         subTable.setObject(nextRow, 15, depotDate);
/*  788 */                         subTable.setObject(nextRow, 16, sel.getSelectionComments());
/*  789 */                         subTable.setSpan(nextRow, 16, new Dimension(1, 2));
/*      */                         
/*  791 */                         subTable.setRowFont(nextRow, new Font("Arial", 1, 7));
/*  792 */                         subTable.setFont(nextRow, 0, new Font("Arial", 0, 7));
/*      */ 
/*      */ 
/*      */                         
/*  796 */                         Font holidayFont = new Font("Arial", 3, 7);
/*  797 */                         Font nonHolidayFont = new Font("Arial", 1, 7);
/*  798 */                         for (int colIdx = 3; colIdx <= 15; colIdx++) {
/*  799 */                           String dueDate = subTable.getObject(nextRow, colIdx).toString();
/*  800 */                           if (dueDate != null && dueDate.length() > 0) {
/*  801 */                             char lastChar = dueDate.charAt(dueDate.length() - 1);
/*  802 */                             if (Character.isLetter(lastChar)) {
/*  803 */                               subTable.setFont(nextRow, colIdx, holidayFont);
/*      */                             } else {
/*      */                               
/*  806 */                               subTable.setFont(nextRow, colIdx, nonHolidayFont);
/*      */                             } 
/*      */                           } 
/*      */                         } 
/*      */                         
/*  811 */                         subTable.setFont(nextRow, 16, new Font("Arial", 0, 7));
/*  812 */                         subTable.setAlignment(nextRow, 0, 10);
/*  813 */                         subTable.setAlignment(nextRow, 16, 9);
/*  814 */                         subTable.setRowHeight(nextRow, 10);
/*  815 */                         subTable.setColFont(0, new Font("Arial", 0, 8));
/*      */                         
/*  817 */                         subTable.setRowBackground(nextRow, Color.white);
/*  818 */                         subTable.setBackground(nextRow, 0, Color.white);
/*  819 */                         subTable.setBackground(nextRow, 1, Color.lightGray);
/*  820 */                         subTable.setBackground(nextRow, 2, Color.lightGray);
/*  821 */                         subTable.setBackground(nextRow, 3, Color.lightGray);
/*  822 */                         subTable.setBackground(nextRow, 4, Color.lightGray);
/*  823 */                         subTable.setBackground(nextRow, 5, Color.lightGray);
/*  824 */                         subTable.setBackground(nextRow, 6, Color.lightGray);
/*  825 */                         subTable.setBackground(nextRow, 7, Color.lightGray);
/*  826 */                         subTable.setBackground(nextRow, 8, Color.lightGray);
/*  827 */                         subTable.setBackground(nextRow, 9, Color.lightGray);
/*  828 */                         subTable.setBackground(nextRow, 10, Color.lightGray);
/*  829 */                         subTable.setBackground(nextRow, 11, Color.lightGray);
/*  830 */                         subTable.setBackground(nextRow, 12, Color.lightGray);
/*  831 */                         subTable.setBackground(nextRow, 13, Color.lightGray);
/*  832 */                         subTable.setBackground(nextRow, 14, Color.lightGray);
/*  833 */                         subTable.setBackground(nextRow, 15, Color.lightGray);
/*  834 */                         subTable.setBackground(nextRow, 16, Color.white);
/*      */                         
/*  836 */                         subTable.setColBorderColor(nextRow, -1, Color.lightGray);
/*  837 */                         subTable.setColBorderColor(nextRow, 0, Color.lightGray);
/*  838 */                         subTable.setColBorderColor(nextRow, 1, Color.lightGray);
/*  839 */                         subTable.setColBorderColor(nextRow, 2, Color.lightGray);
/*  840 */                         subTable.setColBorderColor(nextRow, 3, Color.lightGray);
/*  841 */                         subTable.setColBorderColor(nextRow, 4, Color.lightGray);
/*  842 */                         subTable.setColBorderColor(nextRow, 5, Color.lightGray);
/*  843 */                         subTable.setColBorderColor(nextRow, 6, Color.lightGray);
/*  844 */                         subTable.setColBorderColor(nextRow, 7, Color.lightGray);
/*  845 */                         subTable.setColBorderColor(nextRow, 8, Color.lightGray);
/*  846 */                         subTable.setColBorderColor(nextRow, 9, Color.lightGray);
/*  847 */                         subTable.setColBorderColor(nextRow, 10, Color.lightGray);
/*  848 */                         subTable.setColBorderColor(nextRow, 11, Color.lightGray);
/*  849 */                         subTable.setColBorderColor(nextRow, 12, Color.lightGray);
/*  850 */                         subTable.setColBorderColor(nextRow, 13, Color.lightGray);
/*  851 */                         subTable.setColBorderColor(nextRow, 14, Color.lightGray);
/*  852 */                         subTable.setColBorderColor(nextRow, 15, Color.lightGray);
/*  853 */                         subTable.setColBorderColor(nextRow, 16, Color.lightGray);
/*      */                         
/*  855 */                         subTable.setRowBorderColor(nextRow, Color.lightGray);
/*  856 */                         subTable.setRowBorderColor(nextRow - 1, Color.lightGray);
/*      */                         
/*  858 */                         subTable.setColBorder(nextRow, 1, 266240);
/*  859 */                         subTable.setColBorder(nextRow, 2, 266240);
/*  860 */                         subTable.setColBorder(nextRow, 3, 266240);
/*  861 */                         subTable.setColBorder(nextRow, 4, 266240);
/*  862 */                         subTable.setColBorder(nextRow, 5, 266240);
/*  863 */                         subTable.setColBorder(nextRow, 6, 266240);
/*  864 */                         subTable.setColBorder(nextRow, 7, 266240);
/*  865 */                         subTable.setColBorder(nextRow, 8, 266240);
/*  866 */                         subTable.setColBorder(nextRow, 9, 266240);
/*  867 */                         subTable.setColBorder(nextRow, 10, 266240);
/*  868 */                         subTable.setColBorder(nextRow, 11, 266240);
/*  869 */                         subTable.setColBorder(nextRow, 12, 266240);
/*  870 */                         subTable.setColBorder(nextRow, 13, 266240);
/*  871 */                         subTable.setColBorder(nextRow, 14, 266240);
/*  872 */                         subTable.setColBorder(nextRow, 15, 266240);
/*      */                         
/*  874 */                         subTable.setAlignment(nextRow, 0, 10);
/*  875 */                         subTable.setAlignment(nextRow, 1, 10);
/*  876 */                         subTable.setAlignment(nextRow, 2, 10);
/*  877 */                         subTable.setAlignment(nextRow, 3, 10);
/*  878 */                         subTable.setAlignment(nextRow, 4, 10);
/*  879 */                         subTable.setAlignment(nextRow, 5, 10);
/*  880 */                         subTable.setAlignment(nextRow, 6, 10);
/*  881 */                         subTable.setAlignment(nextRow, 7, 10);
/*  882 */                         subTable.setAlignment(nextRow, 8, 10);
/*  883 */                         subTable.setAlignment(nextRow, 9, 10);
/*  884 */                         subTable.setAlignment(nextRow, 10, 10);
/*  885 */                         subTable.setAlignment(nextRow, 11, 10);
/*  886 */                         subTable.setAlignment(nextRow, 12, 10);
/*  887 */                         subTable.setAlignment(nextRow, 13, 10);
/*  888 */                         subTable.setAlignment(nextRow, 14, 10);
/*  889 */                         subTable.setAlignment(nextRow, 15, 10);
/*      */                         
/*  891 */                         nextRow++;
/*      */                         
/*  893 */                         subTable.setRowBorderColor(nextRow, Color.lightGray);
/*      */                         
/*  895 */                         subTable.setColBorderColor(nextRow, -1, Color.lightGray);
/*  896 */                         subTable.setColBorderColor(nextRow, 0, Color.lightGray);
/*  897 */                         subTable.setColBorderColor(nextRow, 1, Color.lightGray);
/*  898 */                         subTable.setColBorderColor(nextRow, 2, Color.lightGray);
/*  899 */                         subTable.setColBorderColor(nextRow, 3, Color.lightGray);
/*  900 */                         subTable.setColBorderColor(nextRow, 4, Color.lightGray);
/*  901 */                         subTable.setColBorderColor(nextRow, 5, Color.lightGray);
/*  902 */                         subTable.setColBorderColor(nextRow, 6, Color.lightGray);
/*  903 */                         subTable.setColBorderColor(nextRow, 7, Color.lightGray);
/*  904 */                         subTable.setColBorderColor(nextRow, 8, Color.lightGray);
/*  905 */                         subTable.setColBorderColor(nextRow, 9, Color.lightGray);
/*  906 */                         subTable.setColBorderColor(nextRow, 10, Color.lightGray);
/*  907 */                         subTable.setColBorderColor(nextRow, 11, Color.lightGray);
/*  908 */                         subTable.setColBorderColor(nextRow, 12, Color.lightGray);
/*  909 */                         subTable.setColBorderColor(nextRow, 13, Color.lightGray);
/*  910 */                         subTable.setColBorderColor(nextRow, 14, Color.lightGray);
/*  911 */                         subTable.setColBorderColor(nextRow, 15, Color.lightGray);
/*  912 */                         subTable.setColBorderColor(nextRow, 16, Color.lightGray);
/*      */ 
/*      */                         
/*  915 */                         subTable.setRowInsets(nextRow, new Insets(0, 0, 0, 0));
/*      */                         
/*  917 */                         subTable.setRowFont(nextRow, new Font("Arial", 0, 7));
/*  918 */                         subTable.setRowAlignment(nextRow, 10);
/*      */ 
/*      */                         
/*  921 */                         subTable.setObject(nextRow, 0, "");
/*  922 */                         subTable.setObject(nextRow, 1, ArtistTile);
/*  923 */                         subTable.setObject(nextRow, 2, labelAndPackage);
/*  924 */                         subTable.setObject(nextRow, 3, photoShoot);
/*  925 */                         subTable.setObject(nextRow, 4, masterToPlant);
/*  926 */                         subTable.setObject(nextRow, 5, manfCopyShips);
/*  927 */                         subTable.setObject(nextRow, 6, packageCopy);
/*  928 */                         subTable.setObject(nextRow, 7, stickerCopy);
/*  929 */                         subTable.setObject(nextRow, 8, advCDs);
/*  930 */                         subTable.setObject(nextRow, 9, coverArt);
/*  931 */                         subTable.setObject(nextRow, 10, seps);
/*  932 */                         subTable.setObject(nextRow, 11, japan);
/*  933 */                         subTable.setObject(nextRow, 12, packageFilmShip);
/*  934 */                         subTable.setObject(nextRow, 13, packageFilm);
/*  935 */                         subTable.setObject(nextRow, 14, printAtPlant);
/*  936 */                         subTable.setObject(nextRow, 15, depot);
/*      */                         
/*  938 */                         body = new SectionBand(report);
/*  939 */                         body.addTable(subTable, new Rectangle(800, 800));
/*      */                         
/*  941 */                         double lfLineCount = 1.5D;
/*      */                         
/*  943 */                         if (commentLines > 0 || labelAndPackage.length() > 17 || sel.getTitle().length() > 20) {
/*      */                           
/*  945 */                           body.setHeight(1.5F);
/*      */                         }
/*      */                         else {
/*      */                           
/*  949 */                           body.setHeight(1.0F);
/*      */                         } 
/*      */                         
/*  952 */                         if (commentLines > 0 || labelAndPackage.length() > 17 || sel.getTitle().length() > 20 || 
/*  953 */                           photoShoot.length() > 25 || masterToPlant.length() > 25 || manfCopyShips.length() > 25 || 
/*  954 */                           packageCopy.length() > 25 || stickerCopy.length() > 25 || 
/*  955 */                           advCDs.length() > 25 || coverArt.length() > 25 || 
/*  956 */                           seps.length() > 25 || japan.length() > 25 || 
/*  957 */                           packageFilmShip.length() > 25 || 
/*  958 */                           packageFilm.length() > 25 || 
/*  959 */                           printAtPlant.length() > 25 || depot.length() > 25) {
/*      */                           
/*  961 */                           if (lfLineCount < commentLines * 0.3D) {
/*  962 */                             lfLineCount = commentLines * 0.3D;
/*      */                           }
/*  964 */                           if (lfLineCount < (packageFilmShip.length() / 7) * 0.3D) {
/*  965 */                             lfLineCount = (packageFilmShip.length() / 7) * 0.3D;
/*      */                           }
/*  967 */                           if (lfLineCount < (labelAndPackage.length() / 8) * 0.3D) {
/*  968 */                             lfLineCount = (labelAndPackage.length() / 8) * 0.3D;
/*      */                           }
/*  970 */                           if (lfLineCount < (packageFilm.length() / 8) * 0.3D) {
/*  971 */                             lfLineCount = (packageFilm.length() / 8) * 0.3D;
/*      */                           }
/*  973 */                           if (lfLineCount < (printAtPlant.length() / 8) * 0.3D) {
/*  974 */                             lfLineCount = (printAtPlant.length() / 8) * 0.3D;
/*      */                           }
/*  976 */                           if (lfLineCount < (sel.getTitle().length() / 8) * 0.3D) {
/*  977 */                             lfLineCount = (sel.getTitle().length() / 8) * 0.3D;
/*      */                           }
/*  979 */                           if (lfLineCount < (photoShoot.length() / 7) * 0.3D) {
/*  980 */                             lfLineCount = (photoShoot.length() / 7) * 0.3D;
/*      */                           }
/*  982 */                           if (lfLineCount < (masterToPlant.length() / 7) * 0.3D) {
/*  983 */                             lfLineCount = (masterToPlant.length() / 7) * 0.3D;
/*      */                           }
/*  985 */                           if (lfLineCount < (manfCopyShips.length() / 7) * 0.3D) {
/*  986 */                             lfLineCount = (manfCopyShips.length() / 7) * 0.3D;
/*      */                           }
/*  988 */                           if (lfLineCount < (packageCopy.length() / 7) * 0.3D) {
/*  989 */                             lfLineCount = (packageCopy.length() / 7) * 0.3D;
/*      */                           }
/*  991 */                           if (lfLineCount < (stickerCopy.length() / 7) * 0.3D) {
/*  992 */                             lfLineCount = (stickerCopy.length() / 7) * 0.3D;
/*      */                           }
/*  994 */                           if (lfLineCount < (advCDs.length() / 7) * 0.3D) {
/*  995 */                             lfLineCount = (advCDs.length() / 7) * 0.3D;
/*      */                           }
/*  997 */                           if (lfLineCount < (coverArt.length() / 7) * 0.3D) {
/*  998 */                             lfLineCount = (coverArt.length() / 7) * 0.3D;
/*      */                           }
/* 1000 */                           if (lfLineCount < (seps.length() / 7) * 0.3D) {
/* 1001 */                             lfLineCount = (seps.length() / 7) * 0.3D;
/*      */                           }
/* 1003 */                           if (lfLineCount < (japan.length() / 7) * 0.3D) {
/* 1004 */                             lfLineCount = (japan.length() / 7) * 0.3D;
/*      */                           }
/* 1006 */                           body.setHeight((float)lfLineCount);
/*      */                         }
/* 1008 */                         else if (commentLines > 0 || labelAndPackage.length() > 17 || sel.getTitle().length() > 20 || 
/* 1009 */                           photoShoot.length() > 5 || masterToPlant.length() > 5 || manfCopyShips.length() > 5 || 
/* 1010 */                           packageCopy.length() > 5 || stickerCopy.length() > 5 || 
/* 1011 */                           advCDs.length() > 5 || coverArt.length() > 5 || 
/* 1012 */                           seps.length() > 5 || japan.length() > 5 || 
/* 1013 */                           packageFilmShip.length() > 5 || 
/* 1014 */                           packageFilm.length() > 5 || 
/* 1015 */                           printAtPlant.length() > 5 || depot.length() > 5) {
/*      */ 
/*      */ 
/*      */ 
/*      */                           
/* 1020 */                           if (lfLineCount < commentLines * 0.3D) {
/* 1021 */                             lfLineCount = commentLines * 0.3D;
/*      */                           }
/* 1023 */                           if (lfLineCount < (packageFilmShip.length() / 5) * 0.3D) {
/* 1024 */                             lfLineCount = (packageFilmShip.length() / 5) * 0.3D;
/*      */                           }
/* 1026 */                           if (lfLineCount < (labelAndPackage.length() / 5) * 0.3D) {
/* 1027 */                             lfLineCount = (labelAndPackage.length() / 5) * 0.3D;
/*      */                           }
/* 1029 */                           if (lfLineCount < (packageFilm.length() / 5) * 0.3D) {
/* 1030 */                             lfLineCount = (packageFilm.length() / 5) * 0.3D;
/*      */                           }
/* 1032 */                           if (lfLineCount < (printAtPlant.length() / 5) * 0.3D) {
/* 1033 */                             lfLineCount = (printAtPlant.length() / 5) * 0.3D;
/*      */                           }
/* 1035 */                           if (lfLineCount < (sel.getTitle().length() / 5) * 0.3D) {
/* 1036 */                             lfLineCount = (sel.getTitle().length() / 5) * 0.3D;
/*      */                           }
/* 1038 */                           if (lfLineCount < (photoShoot.length() / 5) * 0.3D) {
/* 1039 */                             lfLineCount = (photoShoot.length() / 5) * 0.3D;
/*      */                           }
/* 1041 */                           if (lfLineCount < (masterToPlant.length() / 5) * 0.3D) {
/* 1042 */                             lfLineCount = (masterToPlant.length() / 5) * 0.3D;
/*      */                           }
/* 1044 */                           if (lfLineCount < (manfCopyShips.length() / 5) * 0.3D) {
/* 1045 */                             lfLineCount = (manfCopyShips.length() / 5) * 0.3D;
/*      */                           }
/* 1047 */                           if (lfLineCount < (packageCopy.length() / 5) * 0.3D) {
/* 1048 */                             lfLineCount = (packageCopy.length() / 5) * 0.3D;
/*      */                           }
/* 1050 */                           if (lfLineCount < (stickerCopy.length() / 5) * 0.3D) {
/* 1051 */                             lfLineCount = (stickerCopy.length() / 5) * 0.3D;
/*      */                           }
/* 1053 */                           if (lfLineCount < (advCDs.length() / 5) * 0.3D) {
/* 1054 */                             lfLineCount = (advCDs.length() / 5) * 0.3D;
/*      */                           }
/* 1056 */                           if (lfLineCount < (coverArt.length() / 5) * 0.3D) {
/* 1057 */                             lfLineCount = (coverArt.length() / 5) * 0.3D;
/*      */                           }
/* 1059 */                           if (lfLineCount < (seps.length() / 5) * 0.3D) {
/* 1060 */                             lfLineCount = (seps.length() / 5) * 0.3D;
/*      */                           }
/* 1062 */                           if (lfLineCount < (japan.length() / 5) * 0.3D) {
/* 1063 */                             lfLineCount = (japan.length() / 5) * 0.3D;
/*      */                           }
/* 1065 */                           body.setHeight((float)lfLineCount);
/*      */                         
/*      */                         }
/*      */                         else {
/*      */                           
/* 1070 */                           body.setHeight(1.0F);
/*      */                         } 
/*      */                         
/* 1073 */                         body.setBottomBorder(0);
/* 1074 */                         body.setTopBorder(0);
/* 1075 */                         body.setShrinkToFit(true);
/* 1076 */                         body.setVisible(true);
/*      */                         
/* 1078 */                         group = new DefaultSectionLens(null, group, body);
/*      */                       } 
/*      */                     } 
/*      */                   } 
/*      */                 } 
/*      */               } 
/*      */             } 
/*      */           } 
/*      */         } 
/*      */         
/* 1088 */         group = new DefaultSectionLens(hbandType, group, footer);
/*      */         
/* 1090 */         report.addSection(group, rowCountTable);
/*      */         
/* 1092 */         report.addPageBreak();
/* 1093 */         group = null;
/*      */ 
/*      */       
/*      */       }
/*      */ 
/*      */ 
/*      */     
/*      */     }
/* 1101 */     catch (Exception e) {
/*      */       
/* 1103 */       System.out.println(">>>>>>>>ReportHandler.fillVerveScheduleUpdateForPrint(): exception: " + e);
/* 1104 */       e.printStackTrace();
/*      */     } 
/*      */   }
/*      */ }


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\VerveScheduleUpdateForPrintSubHandler.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */