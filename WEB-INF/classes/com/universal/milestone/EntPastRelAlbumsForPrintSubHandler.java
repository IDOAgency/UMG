/*      */ package WEB-INF.classes.com.universal.milestone;
/*      */ 
/*      */ import com.techempower.ComponentLog;
/*      */ import com.techempower.gemini.Context;
/*      */ import com.techempower.gemini.FormCheckBox;
/*      */ import com.techempower.gemini.GeminiApplication;
/*      */ import com.universal.milestone.Cache;
/*      */ import com.universal.milestone.Company;
/*      */ import com.universal.milestone.EntPastRelAlbumsForPrintSubHandler;
/*      */ import com.universal.milestone.Environment;
/*      */ import com.universal.milestone.Family;
/*      */ import com.universal.milestone.Form;
/*      */ import com.universal.milestone.JdbcConnector;
/*      */ import com.universal.milestone.LookupObject;
/*      */ import com.universal.milestone.MilestoneHelper;
/*      */ import com.universal.milestone.PrefixCode;
/*      */ import com.universal.milestone.ReportSelectionsHelper;
/*      */ import com.universal.milestone.SecureHandler;
/*      */ import com.universal.milestone.Selection;
/*      */ import com.universal.milestone.SelectionManager;
/*      */ import com.universal.milestone.SelectionStatus;
/*      */ import com.universal.milestone.SelectionSubConfiguration;
/*      */ import inetsoft.report.XStyleSheet;
/*      */ import inetsoft.report.lens.DefaultTableLens;
/*      */ import java.awt.Color;
/*      */ import java.awt.Font;
/*      */ import java.awt.Insets;
/*      */ import java.text.SimpleDateFormat;
/*      */ import java.util.Calendar;
/*      */ import java.util.Collections;
/*      */ import java.util.Date;
/*      */ import java.util.Enumeration;
/*      */ import java.util.Hashtable;
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
/*      */ public class EntPastRelAlbumsForPrintSubHandler
/*      */   extends SecureHandler
/*      */ {
/*      */   public static final String COMPONENT_CODE = "hEntPastRelDates";
/*      */   public GeminiApplication application;
/*      */   public ComponentLog log;
/*      */   
/*      */   public EntPastRelAlbumsForPrintSubHandler(GeminiApplication application) {
/*   68 */     this.application = application;
/*   69 */     this.log = application.getLog("hEntPastRelDates");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   77 */   public String getDescription() { return "Sub Report"; }
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
/*      */   protected static void fillEntPastRelDatesForPrint(XStyleSheet report, Context context, String reportType) {
/*      */     try {
/*   91 */       HttpServletResponse sresponse = context.getResponse();
/*   92 */       context.putDelivery("status", new String("start_gathering"));
/*   93 */       context.includeJSP("status.jsp", "hiddenFrame");
/*   94 */       sresponse.setContentType("text/plain");
/*   95 */       sresponse.flushBuffer();
/*      */     }
/*   97 */     catch (Exception exception) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  103 */     Vector initialSelections = getSingleSelectionsForReport(context, reportType);
/*      */ 
/*      */     
/*  106 */     Vector selections = getValidSelections(initialSelections);
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/*  111 */       HttpServletResponse sresponse = context.getResponse();
/*  112 */       context.putDelivery("status", new String("start_report"));
/*  113 */       context.putDelivery("percent", new String("10"));
/*  114 */       context.includeJSP("status.jsp", "hiddenFrame");
/*  115 */       sresponse.setContentType("text/plain");
/*  116 */       sresponse.flushBuffer();
/*      */     }
/*  118 */     catch (Exception exception) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/*  129 */       Form reportForm = (Form)context.getSessionValue("reportForm");
/*      */       
/*  131 */       Calendar beginStDate = (reportForm.getStringValue("beginDate") != null && 
/*  132 */         reportForm.getStringValue("beginDate").length() > 0) ? 
/*  133 */         MilestoneHelper.getDate(reportForm.getStringValue("beginDate")) : null;
/*      */       
/*  135 */       Calendar endStDate = (reportForm.getStringValue("endDate") != null && 
/*  136 */         reportForm.getStringValue("endDate").length() > 0) ? 
/*  137 */         MilestoneHelper.getDate(reportForm.getStringValue("endDate")) : null;
/*      */       
/*  139 */       report.setElement("crs_startdate", MilestoneHelper.getFormatedDate(beginStDate));
/*  140 */       report.setElement("crs_enddate", MilestoneHelper.getFormatedDate(endStDate));
/*      */       
/*  142 */       SimpleDateFormat formatter = new SimpleDateFormat("MMMM d, yyyy");
/*  143 */       String todayLong = formatter.format(new Date());
/*  144 */       report.setElement("crs_bottomdate", todayLong);
/*      */ 
/*      */ 
/*      */       
/*  148 */       Hashtable selTable = groupSelectionsByFamilyAndArtist(selections);
/*      */       
/*  150 */       Enumeration families = selTable.keys();
/*  151 */       Vector familyVector = new Vector();
/*  152 */       while (families.hasMoreElements()) {
/*  153 */         familyVector.addElement(families.nextElement());
/*      */       }
/*      */       
/*  156 */       familyVector = MilestoneHelper.sortStrings(familyVector);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  163 */       int numArtist = 0;
/*  164 */       int numSelectsIDs = 0;
/*  165 */       int rowCounter = 0;
/*  166 */       for (int i = 0; i < familyVector.size(); i++) {
/*      */         
/*  168 */         String familyName = (familyVector.elementAt(i) != null) ? (String)familyVector.elementAt(i) : "";
/*  169 */         Hashtable artistTable = (Hashtable)selTable.get(familyName);
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  174 */         Vector allFamilySelections = new Vector();
/*      */         
/*  176 */         if (artistTable != null) {
/*      */ 
/*      */           
/*  179 */           Enumeration projects = artistTable.keys();
/*  180 */           Vector artistVector = new Vector();
/*      */           
/*  182 */           while (projects.hasMoreElements()) {
/*  183 */             artistVector.addElement((String)projects.nextElement());
/*      */           }
/*      */           
/*  186 */           artistVector = MilestoneHelper.sortStrings(artistVector);
/*      */           
/*  188 */           for (int jj = 0; jj < artistVector.size(); jj++) {
/*      */             
/*  190 */             String selectsName = (artistVector.elementAt(jj) != null) ? (String)artistVector.elementAt(jj) : "";
/*  191 */             Vector selectVector = (Vector)artistTable.get(selectsName);
/*      */             
/*  193 */             if (selectVector != null) {
/*      */               
/*  195 */               selections = selectVector;
/*      */               
/*  197 */               for (int selc = 0; selc < selections.size(); selc++) {
/*  198 */                 allFamilySelections.add(selections.elementAt(selc));
/*      */               }
/*      */             } 
/*      */           } 
/*      */         } 
/*      */         
/*  204 */         if (artistTable != null) {
/*      */ 
/*      */           
/*  207 */           Enumeration projects = artistTable.keys();
/*  208 */           Vector artistVector = new Vector();
/*      */           
/*  210 */           while (projects.hasMoreElements()) {
/*  211 */             artistVector.addElement((String)projects.nextElement());
/*      */           }
/*      */           
/*  214 */           artistVector = MilestoneHelper.sortStrings(artistVector);
/*      */           
/*  216 */           for (int rs = 0; rs < artistVector.size(); rs++) {
/*      */ 
/*      */             
/*  219 */             String selectsName = (artistVector.elementAt(rs) != null) ? (String)artistVector.elementAt(rs) : "";
/*  220 */             Vector selectVector = (Vector)artistTable.get(selectsName);
/*      */             
/*  222 */             if (selectVector != null) {
/*      */ 
/*      */               
/*  225 */               selections = selectVector;
/*      */ 
/*      */               
/*  228 */               MilestoneHelper.setSelectionSorting(selections, 1);
/*  229 */               Collections.sort(selections);
/*      */               
/*  231 */               Vector fullLengthStrings = getConfigType(0);
/*  232 */               Vector singleStrings = getConfigType(1);
/*      */               
/*  234 */               Vector titlesVector = getTitlesVector(selections, allFamilySelections);
/*      */ 
/*      */               
/*  237 */               for (int tu = 0; tu < titlesVector.size(); tu++) {
/*      */ 
/*      */ 
/*      */ 
/*      */                 
/*  242 */                 String selTitle = (String)titlesVector.elementAt(tu);
/*      */                 
/*  244 */                 Vector allProjectIDVector = new Vector();
/*      */                 
/*  246 */                 for (int jt = 0; jt < selections.size(); jt++) {
/*      */ 
/*      */ 
/*      */ 
/*      */                   
/*  251 */                   String selectionTitle = ((Selection)selections.elementAt(jt)).getTitle();
/*  252 */                   if (selectionTitle.equals(selTitle)) {
/*  253 */                     String projectID = ((Selection)selections.elementAt(jt)).getProjectID();
/*      */ 
/*      */                     
/*  256 */                     if (!allProjectIDVector.contains(projectID))
/*      */                     {
/*  258 */                       allProjectIDVector.add(projectID);
/*      */                     }
/*      */                   } 
/*      */                 } 
/*      */                 
/*  263 */                 for (int p = 0; p < allProjectIDVector.size(); p++) {
/*      */                   
/*  265 */                   String currentProjectID = (String)allProjectIDVector.elementAt(p);
/*      */ 
/*      */                   
/*  268 */                   for (int iq = 0; iq < selections.size(); iq++) {
/*      */ 
/*      */ 
/*      */                     
/*  272 */                     if (((Selection)selections.elementAt(iq)).getProjectID().equals(currentProjectID) && (
/*  273 */                       (Selection)selections.elementAt(iq)).getTitle().equals(selTitle)) {
/*      */                       
/*  275 */                       Selection selectionEntry = (Selection)selections.elementAt(iq);
/*      */ 
/*      */                       
/*  278 */                       String sin = "";
/*  279 */                       String singlesSubconfig = selectionEntry.getSelectionSubConfig().getSelectionSubConfigurationAbbreviation();
/*      */ 
/*      */ 
/*      */ 
/*      */                       
/*  284 */                       if (fullLengthStrings.contains(singlesSubconfig))
/*      */                       {
/*  286 */                         if (selectionHasValidSingles(allFamilySelections, currentProjectID, ((Selection)selections.elementAt(iq)).getStreetDate(), ((Selection)selections.elementAt(iq)).getSelectionStatus())) {
/*      */                           
/*  288 */                           Vector albumAndSingles = new Vector();
/*  289 */                           albumAndSingles.add((Selection)selections.elementAt(iq));
/*      */                           
/*  291 */                           for (int y = 0; y < allFamilySelections.size(); y++) {
/*      */ 
/*      */                             
/*  294 */                             Selection currentSelection = (Selection)allFamilySelections.elementAt(y);
/*  295 */                             String singleSubConfig = currentSelection.getSelectionSubConfig().getSelectionSubConfigurationAbbreviation();
/*  296 */                             String singleProjectID = currentSelection.getProjectID();
/*  297 */                             if (singleProjectID.equals(currentProjectID) && singleStrings.contains(singleSubConfig)) {
/*  298 */                               albumAndSingles.add(currentSelection);
/*      */                             }
/*      */                           } 
/*      */                           
/*  302 */                           for (int counter = 0; counter < albumAndSingles.size(); counter++) {
/*  303 */                             rowCounter++;
/*      */                           }
/*      */                         } 
/*      */                       }
/*      */                     } 
/*      */                   } 
/*      */                 } 
/*      */               } 
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
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
/*  328 */       rowCounter++;
/*      */ 
/*      */       
/*  331 */       int numRows = numSelectsIDs + 1;
/*      */ 
/*      */ 
/*      */       
/*  335 */       DefaultTableLens table_contents = new DefaultTableLens(rowCounter, 7);
/*      */       
/*  337 */       table_contents.setColBorder(0);
/*  338 */       table_contents.setRowBorderColor(Color.lightGray);
/*      */       
/*  340 */       table_contents.setColWidth(0, 100);
/*  341 */       table_contents.setColWidth(1, 180);
/*  342 */       table_contents.setColWidth(2, 300);
/*  343 */       table_contents.setColWidth(3, 130);
/*  344 */       table_contents.setColWidth(4, 100);
/*  345 */       table_contents.setColWidth(5, 80);
/*  346 */       table_contents.setColWidth(6, 150);
/*      */       
/*  348 */       table_contents.setAlignment(0, 0, 33);
/*  349 */       table_contents.setAlignment(0, 1, 33);
/*  350 */       table_contents.setAlignment(0, 2, 33);
/*  351 */       table_contents.setAlignment(0, 3, 33);
/*  352 */       table_contents.setAlignment(0, 4, 33);
/*  353 */       table_contents.setAlignment(0, 5, 33);
/*  354 */       table_contents.setAlignment(0, 6, 36);
/*      */ 
/*      */       
/*  357 */       table_contents.setHeaderRowCount(1);
/*      */       
/*  359 */       table_contents.setRowBorder(-1, 0);
/*  360 */       table_contents.setRowBorder(0, 266240);
/*  361 */       table_contents.setRowBorderColor(0, Color.black);
/*      */ 
/*      */       
/*  364 */       table_contents.setRowAlignment(0, 32);
/*      */       
/*  366 */       table_contents.setObject(0, 0, "Family");
/*  367 */       table_contents.setObject(0, 1, "Artist");
/*  368 */       table_contents.setObject(0, 2, "Title");
/*  369 */       table_contents.setObject(0, 3, "Catalog Number");
/*  370 */       table_contents.setObject(0, 4, "Project Number");
/*  371 */       table_contents.setObject(0, 5, "Sub Config");
/*  372 */       table_contents.setObject(0, 6, "Release Date");
/*  373 */       table_contents.setRowInsets(0, new Insets(0, 0, 0, 0));
/*  374 */       table_contents.setRowFont(0, new Font("Arial", 3, 11));
/*      */ 
/*      */       
/*  377 */       int nextRow = 1;
/*      */       
/*  379 */       for (int n = 0; n < familyVector.size(); n++) {
/*      */ 
/*      */         
/*  382 */         String family = (String)familyVector.elementAt(n);
/*      */         
/*  384 */         String familyHeaderText = !family.trim().equals("") ? family : "Other";
/*      */ 
/*      */         
/*  387 */         Hashtable artistTable = (Hashtable)selTable.get(family);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  393 */         Vector allFamilySelections = new Vector();
/*      */         
/*  395 */         if (artistTable != null) {
/*      */ 
/*      */           
/*  398 */           Enumeration projects = artistTable.keys();
/*  399 */           Vector artistVector = new Vector();
/*      */           
/*  401 */           while (projects.hasMoreElements()) {
/*  402 */             artistVector.addElement((String)projects.nextElement());
/*      */           }
/*      */           
/*  405 */           artistVector = MilestoneHelper.sortStrings(artistVector);
/*      */           
/*  407 */           for (int jj = 0; jj < artistVector.size(); jj++) {
/*      */             
/*  409 */             String selectsName = (artistVector.elementAt(jj) != null) ? (String)artistVector.elementAt(jj) : "";
/*  410 */             Vector selectVector = (Vector)artistTable.get(selectsName);
/*      */             
/*  412 */             if (selectVector != null) {
/*      */               
/*  414 */               selections = selectVector;
/*      */               
/*  416 */               for (int selc = 0; selc < selections.size(); selc++) {
/*  417 */                 allFamilySelections.add(selections.elementAt(selc));
/*      */               }
/*      */             } 
/*      */           } 
/*      */         } 
/*      */ 
/*      */         
/*  424 */         if (artistTable != null) {
/*      */ 
/*      */           
/*  427 */           Enumeration projects = artistTable.keys();
/*  428 */           Vector artistVector = new Vector();
/*      */           
/*  430 */           while (projects.hasMoreElements()) {
/*  431 */             artistVector.addElement((String)projects.nextElement());
/*      */           }
/*      */           
/*  434 */           artistVector = MilestoneHelper.sortStrings(artistVector);
/*      */           
/*  436 */           for (int rs = 0; rs < artistVector.size(); rs++) {
/*      */ 
/*      */             
/*  439 */             String selectsName = (artistVector.elementAt(rs) != null) ? (String)artistVector.elementAt(rs) : "";
/*  440 */             Vector selectVector = (Vector)artistTable.get(selectsName);
/*      */             
/*  442 */             if (selectVector != null) {
/*      */ 
/*      */               
/*  445 */               selections = selectVector;
/*      */ 
/*      */               
/*  448 */               MilestoneHelper.setSelectionSorting(selections, 1);
/*  449 */               Collections.sort(selections);
/*      */ 
/*      */               
/*  452 */               int count = 2;
/*  453 */               int numRec = selections.size();
/*  454 */               int chunkSize = numRec / 10;
/*      */               
/*  456 */               Vector fullLengthStrings = getConfigType(0);
/*  457 */               Vector singleStrings = getConfigType(1);
/*      */               
/*  459 */               Vector titlesVector = getTitlesVector(selections, allFamilySelections);
/*      */ 
/*      */               
/*  462 */               boolean hasAlbum = false;
/*  463 */               String albumArtist = "";
/*      */ 
/*      */               
/*      */               try {
/*  467 */                 int myPercent = rs / chunkSize;
/*  468 */                 if (myPercent > 1 && myPercent < 10)
/*  469 */                   count = myPercent; 
/*  470 */                 HttpServletResponse sresponse = context.getResponse();
/*  471 */                 context.putDelivery("status", new String("start_report"));
/*  472 */                 context.putDelivery("percent", new String(String.valueOf(count * 10)));
/*  473 */                 context.includeJSP("status.jsp", "hiddenFrame");
/*  474 */                 sresponse.setContentType("text/plain");
/*  475 */                 sresponse.flushBuffer();
/*      */               }
/*  477 */               catch (Exception exception) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */               
/*  483 */               for (int tu = 0; tu < titlesVector.size(); tu++) {
/*      */ 
/*      */                 
/*  486 */                 String selTitle = (String)titlesVector.elementAt(tu);
/*      */                 
/*  488 */                 Vector allProjectIDVector = new Vector();
/*      */                 
/*  490 */                 for (int jt = 0; jt < selections.size(); jt++) {
/*      */ 
/*      */ 
/*      */ 
/*      */                   
/*  495 */                   String selectionTitle = ((Selection)selections.elementAt(jt)).getTitle();
/*  496 */                   if (selectionTitle.equals(selTitle)) {
/*  497 */                     String projectID = ((Selection)selections.elementAt(jt)).getProjectID();
/*      */ 
/*      */                     
/*  500 */                     if (!allProjectIDVector.contains(projectID))
/*      */                     {
/*  502 */                       allProjectIDVector.add(projectID);
/*      */                     }
/*      */                   } 
/*      */                 } 
/*      */ 
/*      */                 
/*  508 */                 for (int p = 0; p < allProjectIDVector.size(); p++) {
/*      */                   
/*  510 */                   String currentProjectID = (String)allProjectIDVector.elementAt(p);
/*      */ 
/*      */                   
/*  513 */                   for (int i = 0; i < selections.size(); i++) {
/*      */ 
/*      */ 
/*      */                     
/*  517 */                     if (((Selection)selections.elementAt(i)).getProjectID().equals(currentProjectID) && (
/*  518 */                       (Selection)selections.elementAt(i)).getTitle().equals(selTitle)) {
/*      */                       
/*  520 */                       Selection selectionEntry = (Selection)selections.elementAt(i);
/*      */ 
/*      */                       
/*  523 */                       String sin = "";
/*  524 */                       String singlesSubconfig = selectionEntry.getSelectionSubConfig().getSelectionSubConfigurationAbbreviation();
/*      */ 
/*      */ 
/*      */ 
/*      */                       
/*  529 */                       if (fullLengthStrings.contains(singlesSubconfig))
/*      */                       {
/*  531 */                         if (selectionHasValidSingles(allFamilySelections, currentProjectID, ((Selection)selections.elementAt(i)).getStreetDate(), ((Selection)selections.elementAt(i)).getSelectionStatus())) {
/*      */                           
/*  533 */                           Vector albumAndSingles = new Vector();
/*  534 */                           albumAndSingles.add((Selection)selections.elementAt(i));
/*      */                           
/*  536 */                           for (int y = 0; y < allFamilySelections.size(); y++) {
/*      */ 
/*      */                             
/*  539 */                             Selection currentSelection = (Selection)allFamilySelections.elementAt(y);
/*  540 */                             String singleSubConfig = currentSelection.getSelectionSubConfig().getSelectionSubConfigurationAbbreviation();
/*  541 */                             String singleProjectID = currentSelection.getProjectID();
/*  542 */                             if (singleProjectID.equals(currentProjectID) && singleStrings.contains(singleSubConfig)) {
/*  543 */                               albumAndSingles.add(currentSelection);
/*      */                             }
/*      */                           } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                           
/*  551 */                           for (int counter = 0; counter < albumAndSingles.size(); counter++) {
/*      */                             String catalogNumber, singlesStreetDate;
/*  553 */                             selectionEntry = (Selection)albumAndSingles.elementAt(counter);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                             
/*  563 */                             singlesSubconfig = selectionEntry.getSelectionSubConfig().getSelectionSubConfigurationAbbreviation();
/*      */                             
/*  565 */                             SelectionStatus status = selectionEntry.getSelectionStatus();
/*  566 */                             String selectionStatus = (status != null && status.getName() != null) ? 
/*  567 */                               status.getName() : "";
/*      */ 
/*      */                             
/*  570 */                             if (selectionStatus.equals("TBS")) {
/*  571 */                               singlesStreetDate = "TBS";
/*      */                             
/*      */                             }
/*      */                             else {
/*      */                               
/*  576 */                               singlesStreetDate = MilestoneHelper.getFormatedDate(selectionEntry.getStreetDate());
/*      */                             } 
/*      */                             
/*  579 */                             String singlesTitle = selectionEntry.getTitle();
/*  580 */                             String singlesArtist = selectionEntry.getArtist();
/*  581 */                             String singlesFamily = selectionEntry.getFamily().getName();
/*  582 */                             String projectID = selectionEntry.getProjectID();
/*      */                             
/*  584 */                             if (SelectionManager.getLookupObjectValue(selectionEntry.getPrefixID()).equals("")) {
/*  585 */                               catalogNumber = selectionEntry.getSelectionNo();
/*      */                             } else {
/*  587 */                               catalogNumber = String.valueOf(SelectionManager.getLookupObjectValue(selectionEntry.getPrefixID())) + selectionEntry.getSelectionNo();
/*      */                             } 
/*      */                             
/*  590 */                             table_contents.setColWidth(0, 100);
/*  591 */                             table_contents.setColWidth(1, 180);
/*  592 */                             table_contents.setColWidth(2, 300);
/*  593 */                             table_contents.setColWidth(3, 130);
/*  594 */                             table_contents.setColWidth(4, 100);
/*  595 */                             table_contents.setColWidth(5, 80);
/*  596 */                             table_contents.setColWidth(6, 150);
/*      */                             
/*  598 */                             if (fullLengthStrings.contains(singlesSubconfig)) {
/*  599 */                               hasAlbum = true;
/*  600 */                               albumArtist = singlesArtist;
/*      */                             } 
/*      */ 
/*      */ 
/*      */ 
/*      */                             
/*  606 */                             if (fullLengthStrings.contains(singlesSubconfig) || (
/*  607 */                               singleStrings.contains(singlesSubconfig) && !hasAlbum)) {
/*  608 */                               table_contents.setObject(nextRow, 0, singlesFamily);
/*  609 */                               table_contents.setObject(nextRow, 1, singlesArtist);
/*  610 */                               table_contents.setObject(nextRow, 2, singlesTitle);
/*  611 */                             } else if (!singlesArtist.equals(albumArtist)) {
/*      */ 
/*      */                               
/*  614 */                               table_contents.setObject(nextRow, 0, singlesFamily);
/*  615 */                               table_contents.setObject(nextRow, 1, singlesArtist);
/*  616 */                               table_contents.setObject(nextRow, 2, "    " + singlesTitle);
/*      */                             } else {
/*  618 */                               table_contents.setObject(nextRow, 0, "");
/*  619 */                               table_contents.setObject(nextRow, 1, "");
/*  620 */                               table_contents.setObject(nextRow, 2, "    " + singlesTitle);
/*      */                             } 
/*  622 */                             table_contents.setObject(nextRow, 3, catalogNumber);
/*  623 */                             table_contents.setObject(nextRow, 4, projectID);
/*  624 */                             table_contents.setObject(nextRow, 5, singlesSubconfig);
/*  625 */                             table_contents.setObject(nextRow, 6, singlesStreetDate);
/*      */ 
/*      */                             
/*  628 */                             if (fullLengthStrings.contains(singlesSubconfig)) {
/*  629 */                               table_contents.setRowFont(nextRow, new Font("Arial", 0, 11));
/*      */                             } else {
/*  631 */                               table_contents.setRowFont(nextRow, new Font("Arial", 0, 9));
/*      */                             } 
/*      */                             
/*  634 */                             table_contents.setRowInsets(nextRow, new Insets(0, 0, 2, 0));
/*      */ 
/*      */                             
/*  637 */                             if (fullLengthStrings.contains(singlesSubconfig)) {
/*  638 */                               table_contents.setRowBorder(0);
/*      */                             
/*      */                             }
/*  641 */                             else if (counter + 1 == albumAndSingles.size()) {
/*  642 */                               table_contents.setRowBorder(nextRow, 4097);
/*  643 */                               table_contents.setRowBorderColor(nextRow, Color.lightGray);
/*      */                             } else {
/*  645 */                               table_contents.setRowBorder(0);
/*      */                             } 
/*      */                             
/*  648 */                             table_contents.setAlignment(nextRow, 0, 9);
/*  649 */                             table_contents.setAlignment(nextRow, 1, 9);
/*  650 */                             table_contents.setAlignment(nextRow, 2, 9);
/*  651 */                             table_contents.setAlignment(nextRow, 3, 9);
/*  652 */                             table_contents.setAlignment(nextRow, 4, 9);
/*  653 */                             table_contents.setAlignment(nextRow, 5, 9);
/*  654 */                             table_contents.setAlignment(nextRow, 6, 12);
/*      */                             
/*  656 */                             nextRow++;
/*      */                           } 
/*      */                         } 
/*      */                       }
/*      */                     } 
/*      */                   } 
/*      */                 } 
/*      */               } 
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
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
/*  748 */       report.setElement("table_colheaders", table_contents);
/*      */     
/*      */     }
/*  751 */     catch (Exception e) {
/*      */       
/*  753 */       System.out.println(">>>>>>>>ReportHandler.fillEntRelScheduleForPrint(): exception: " + e);
/*      */     } 
/*      */   }
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
/*      */   public static Vector getSingleSelectionsForReport(Context context, String releaseType) {
/*  770 */     Vector companies = MilestoneHelper.getUserCompanies(context);
/*  771 */     Company company = null;
/*  772 */     Vector precache = new Vector();
/*  773 */     Selection selection = null;
/*  774 */     StringBuffer query = new StringBuffer();
/*      */ 
/*      */     
/*  777 */     Form reportForm = (Form)context.getSessionValue("reportForm");
/*  778 */     boolean bScheduled = (reportForm.getElement("ScheduledReleases") != null) ? ((FormCheckBox)reportForm.getElement("ScheduledReleases")).isChecked() : 0;
/*      */     
/*  780 */     query.append("SELECT release_id, title, artist, street_date, configuration, sub_configuration, selection_no, status, prefix,  company_id, family_id, environment_id, label_id, project_no   from vi_Release_Header header WHERE ");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  790 */     query.append(" header.pd_indicator != 1 ");
/*      */     
/*  792 */     if (releaseType.equals("commercial")) {
/*  793 */       query.append(" AND header.release_type = 'CO' ");
/*      */     } else {
/*  795 */       query.append(" AND ( (header.sub_configuration IN ('ECD','CD','CASS','ALBUM') AND (header.release_type = 'CO')) OR ");
/*  796 */       query.append(" ( header.sub_configuration NOT IN ('ECD','CD','CASS','ALBUM') AND (header.release_type = 'PR') )) ");
/*      */     } 
/*      */     
/*  799 */     query.append(" AND (header.configuration = 'CD' OR header.configuration = 'ECD') ");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  807 */     String[] strFamily = ReportSelectionsHelper.getMultiSelectionListValues("family", reportForm);
/*  808 */     String[] strEnvironment = ReportSelectionsHelper.getMultiSelectionListValues("environment", reportForm);
/*  809 */     String[] strCompany = ReportSelectionsHelper.getMultiSelectionListValues("company", reportForm);
/*  810 */     String[] strLabel = ReportSelectionsHelper.getMultiSelectionListValues("Label", reportForm);
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
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  844 */     ReportSelectionsHelper.addMultSelectionToStringBuffer(query, "company_id", strCompany, true, "company", reportForm, false, true);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  853 */     ReportSelectionsHelper.addReleasingFamilyLabelFamilyMultSelect("family", context, query, reportForm);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  861 */     ReportSelectionsHelper.addMultSelectionToStringBuffer(query, "environment_id", strEnvironment, true, "environment", reportForm, true, true);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  871 */     ReportSelectionsHelper.addMultSelectionToStringBuffer(query, "label_id", strLabel, true, "Label", reportForm, false, true);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  876 */     int intUmvd = -1;
/*  877 */     int intPressPlay = -1;
/*      */ 
/*      */     
/*  880 */     Vector theFamilies = Cache.getFamilies();
/*  881 */     for (int i = 0; i < theFamilies.size(); i++) {
/*  882 */       Family family = (Family)theFamilies.get(i);
/*      */       
/*  884 */       if (family.getName().trim().equalsIgnoreCase("UMVD")) {
/*  885 */         intUmvd = family.getStructureID();
/*      */       }
/*  887 */       if (family.getName().trim().equalsIgnoreCase("Press Play")) {
/*  888 */         intPressPlay = family.getStructureID();
/*      */       }
/*      */     } 
/*      */     
/*  892 */     if (intUmvd > 0)
/*  893 */       query.append(" AND family_id  != " + intUmvd); 
/*  894 */     if (intPressPlay > 0) {
/*  895 */       query.append(" AND family_id != " + intPressPlay);
/*      */     }
/*      */ 
/*      */     
/*  899 */     String beginDate = "";
/*  900 */     beginDate = (reportForm.getStringValue("beginDate") != null && reportForm.getStringValue("beginDate").length() > 0) ? reportForm.getStringValue("beginDate") : "";
/*      */     
/*  902 */     String endDate = "";
/*  903 */     endDate = (reportForm.getStringValue("endDate") != null && reportForm.getStringValue("endDate").length() > 0) ? reportForm.getStringValue("endDate") : "";
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
/*  918 */     if (!beginDate.equalsIgnoreCase("")) {
/*  919 */       query.append(" AND ( ( ( street_date >= '" + MilestoneHelper.escapeSingleQuotes(beginDate) + "'");
/*      */     }
/*  921 */     if (!endDate.equalsIgnoreCase("")) {
/*      */       
/*  923 */       if (!beginDate.equalsIgnoreCase("")) {
/*  924 */         query.append(" AND street_date <= '" + MilestoneHelper.escapeSingleQuotes(endDate) + "')");
/*      */       } else {
/*  926 */         query.append(" AND (( street_date <= '" + MilestoneHelper.escapeSingleQuotes(endDate) + "'");
/*      */       } 
/*  928 */     } else if (!beginDate.equalsIgnoreCase("")) {
/*  929 */       query.append(" ) ");
/*      */     } 
/*      */ 
/*      */     
/*  933 */     if (beginDate.equalsIgnoreCase("") && endDate.equalsIgnoreCase("")) {
/*  934 */       query.append(" AND (((header.status = 'ACTIVE' OR  header.status = 'CLOSED' OR header.status = 'TBS'))");
/*      */     } else {
/*  936 */       query.append(" AND (header.status = 'ACTIVE' OR  header.status = 'CLOSED' OR header.status = 'TBS'))");
/*      */     } 
/*  938 */     query.append(" OR (header.status = 'TBS' AND street_date IS NULL))");
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  943 */     query.append(" ORDER BY artist, street_date");
/*      */ 
/*      */ 
/*      */     
/*  947 */     JdbcConnector connector = MilestoneHelper.getConnector(query.toString());
/*  948 */     connector.setForwardOnly(false);
/*  949 */     connector.runQuery();
/*      */     
/*  951 */     int totalCount = 0;
/*  952 */     int tenth = 0;
/*  953 */     totalCount = connector.getRowCount();
/*      */     
/*  955 */     tenth = totalCount / 5;
/*      */     
/*  957 */     if (tenth < 1) {
/*  958 */       tenth = 1;
/*      */     }
/*      */     
/*      */     try {
/*  962 */       HttpServletResponse sresponse = context.getResponse();
/*  963 */       context.putDelivery("status", new String("start_gathering"));
/*  964 */       context.putDelivery("percent", new String("10"));
/*  965 */       context.includeJSP("status.jsp", "hiddenFrame");
/*  966 */       sresponse.setContentType("text/plain");
/*  967 */       sresponse.flushBuffer();
/*      */     }
/*  969 */     catch (Exception exception) {}
/*      */ 
/*      */ 
/*      */     
/*  973 */     int recordCount = 0;
/*  974 */     int count = 0;
/*      */ 
/*      */     
/*  977 */     while (connector.more()) {
/*      */ 
/*      */       
/*      */       try {
/*  981 */         if (count < recordCount / tenth) {
/*      */           
/*  983 */           count = recordCount / tenth;
/*  984 */           HttpServletResponse sresponse = context.getResponse();
/*  985 */           context.putDelivery("status", new String("start_gathering"));
/*  986 */           context.putDelivery("percent", new String(String.valueOf(count * 10)));
/*  987 */           context.includeJSP("status.jsp", "hiddenFrame");
/*  988 */           sresponse.setContentType("text/plain");
/*  989 */           sresponse.flushBuffer();
/*      */         } 
/*      */         
/*  992 */         recordCount++;
/*      */ 
/*      */         
/*  995 */         selection = new Selection();
/*      */ 
/*      */         
/*  998 */         selection.setFamily((Family)MilestoneHelper.getStructureObject(connector.getIntegerField("family_id")));
/*      */ 
/*      */         
/* 1001 */         selection.setEnvironment((Environment)MilestoneHelper.getStructureObject(connector.getIntegerField("environment_id")));
/*      */ 
/*      */         
/* 1004 */         selection.setCompany((Company)MilestoneHelper.getStructureObject(connector.getIntegerField("company_id")));
/*      */ 
/*      */         
/* 1007 */         selection.setSelectionID(connector.getIntegerField("release_id"));
/*      */ 
/*      */         
/* 1010 */         selection.setPrefixID((PrefixCode)getLookupObject(connector.getField("prefix"), Cache.getPrefixCodes()));
/*      */ 
/*      */         
/* 1013 */         selection.setTitle(connector.getField("title", ""));
/*      */ 
/*      */         
/* 1016 */         selection.setArtistFirstName(connector.getField("artist", ""));
/* 1017 */         selection.setArtist(connector.getField("artist", ""));
/*      */ 
/*      */         
/* 1020 */         selection.setProjectID(connector.getField("project_no", ""));
/*      */ 
/*      */         
/* 1023 */         selection.setSelectionStatus(
/* 1024 */             (SelectionStatus)MilestoneHelper.getLookupObject(connector.getField("status"), Cache.getSelectionStatusList()));
/*      */ 
/*      */         
/* 1027 */         String streetDateString = connector.getFieldByName("street_date");
/* 1028 */         if (streetDateString != null) {
/* 1029 */           selection.setStreetDate(MilestoneHelper.getDatabaseDate(streetDateString));
/*      */         }
/* 1031 */         selection.setSelectionConfig(
/* 1032 */             MilestoneHelper.getSelectionConfigObject(connector.getField("configuration"), 
/* 1033 */               Cache.getSelectionConfigs()));
/*      */ 
/*      */         
/* 1036 */         selection.setSelectionSubConfig(MilestoneHelper.getSelectionSubConfigObject(connector.getField("sub_configuration"), selection.getSelectionConfig()));
/* 1037 */         selection.setSelectionNo(connector.getField("selection_no"));
/*      */         
/* 1039 */         precache.add(selection);
/* 1040 */         selection = null;
/* 1041 */         connector.next();
/*      */       
/*      */       }
/* 1044 */       catch (Exception exception) {}
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1049 */     connector.close();
/* 1050 */     company = null;
/*      */     
/* 1052 */     return precache;
/*      */   }
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
/*      */   public static Hashtable groupSelectionsByFamilyAndArtist(Vector selections) {
/* 1069 */     Hashtable groupedByFamilyAndArtist = new Hashtable();
/* 1070 */     if (selections == null) {
/* 1071 */       return groupedByFamilyAndArtist;
/*      */     }
/* 1073 */     for (int i = 0; i < selections.size(); i++) {
/*      */       
/* 1075 */       Selection sel = (Selection)selections.elementAt(i);
/* 1076 */       if (sel != null) {
/*      */ 
/*      */         
/* 1079 */         String familyName = "", companyName = "";
/* 1080 */         Family family = sel.getFamily();
/* 1081 */         Company company = sel.getCompany();
/* 1082 */         String artist = "";
/*      */         
/* 1084 */         if (family != null)
/* 1085 */           familyName = (family.getName() == null) ? "" : family.getName(); 
/* 1086 */         if (company != null) {
/* 1087 */           companyName = (company.getName() == null) ? "" : company.getName();
/*      */         }
/*      */ 
/*      */         
/* 1091 */         Hashtable familySubTable = (Hashtable)groupedByFamilyAndArtist.get(familyName);
/* 1092 */         if (familySubTable == null) {
/*      */ 
/*      */           
/* 1095 */           familySubTable = new Hashtable();
/* 1096 */           groupedByFamilyAndArtist.put(familyName, familySubTable);
/*      */         } 
/*      */ 
/*      */         
/* 1100 */         artist = sel.getArtist();
/* 1101 */         Vector artistsForFamily = (Vector)familySubTable.get(artist);
/* 1102 */         if (artistsForFamily == null) {
/*      */ 
/*      */           
/* 1105 */           artistsForFamily = new Vector();
/* 1106 */           familySubTable.put(artist, artistsForFamily);
/*      */         } 
/*      */ 
/*      */         
/* 1110 */         artistsForFamily.addElement(sel);
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 1115 */     return groupedByFamilyAndArtist;
/*      */   }
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
/*      */   public static Vector getValidSelections(Vector allSelections) {
/* 1132 */     Vector fullLengthStrings = getConfigType(0);
/* 1133 */     Vector singleStrings = getConfigType(1);
/*      */     
/* 1135 */     Vector finalSelections = new Vector();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1141 */     for (int selectCounter = 0; selectCounter < allSelections.size(); selectCounter++) {
/*      */ 
/*      */       
/* 1144 */       String projectID = ((Selection)allSelections.elementAt(selectCounter)).getProjectID();
/*      */       
/* 1146 */       SelectionStatus status = ((Selection)allSelections.elementAt(selectCounter)).getSelectionStatus();
/* 1147 */       Calendar releaseDateCalendar = ((Selection)allSelections.elementAt(selectCounter)).getStreetDate();
/*      */       
/* 1149 */       String title = ((Selection)allSelections.elementAt(selectCounter)).getTitle();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1155 */       Selection t = (Selection)allSelections.elementAt(selectCounter);
/* 1156 */       SelectionSubConfiguration ssc = t.getSelectionSubConfig();
/* 1157 */       String subConfig = ssc.getSelectionSubConfigurationAbbreviation();
/*      */       
/* 1159 */       if (fullLengthStrings.contains(subConfig)) {
/* 1160 */         if (selectionHasValidSingles(allSelections, projectID, releaseDateCalendar, status)) {
/* 1161 */           finalSelections.add((Selection)allSelections.elementAt(selectCounter));
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         }
/*      */ 
/*      */ 
/*      */       
/*      */       }
/* 1171 */       else if (singleStrings.contains(subConfig) && 
/* 1172 */         selectionHasAnAlbum(allSelections, projectID, releaseDateCalendar, fullLengthStrings, true, status)) {
/* 1173 */         finalSelections.add((Selection)allSelections.elementAt(selectCounter));
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 1178 */     return finalSelections;
/*      */   }
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
/*      */   public static boolean selectionHasValidSingles(Vector allSelections, String projectID, Calendar releaseDateCalendar, SelectionStatus status) {
/* 1198 */     Vector singleStrings = getConfigType(1);
/*      */ 
/*      */     
/* 1201 */     if (selectionHasAValidProjectID(projectID))
/*      */     {
/* 1203 */       for (int i = 0; i < allSelections.size(); i++) {
/*      */         
/* 1205 */         Selection t = (Selection)allSelections.elementAt(i);
/* 1206 */         SelectionSubConfiguration ssc = t.getSelectionSubConfig();
/* 1207 */         String subConfig = ssc.getSelectionSubConfigurationAbbreviation();
/*      */         
/* 1209 */         SelectionStatus singleStatus = t.getSelectionStatus();
/* 1210 */         String selectionStatus2 = (singleStatus != null && singleStatus.getName() != null) ? 
/* 1211 */           singleStatus.getName() : "";
/*      */ 
/*      */         
/* 1214 */         if (((Selection)allSelections.elementAt(i)).getProjectID().equals(projectID) && singleStrings.contains(subConfig) && !selectionStatus2.equals("TBS")) {
/*      */           
/* 1216 */           String selectionStatus = (status != null && status.getName() != null) ? 
/* 1217 */             status.getName() : "";
/*      */           
/* 1219 */           if (selectionStatus.equals("TBS")) {
/* 1220 */             return true;
/*      */           }
/*      */           
/* 1223 */           Calendar singleReleaseDate = ((Selection)allSelections.elementAt(i)).getStreetDate();
/*      */ 
/*      */           
/* 1226 */           if (singleReleaseDate.before(releaseDateCalendar)) {
/* 1227 */             return true;
/*      */           }
/*      */         } 
/*      */       } 
/*      */     }
/* 1232 */     return false;
/*      */   }
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
/*      */   public static boolean selectionHasAnAlbum(Vector allSelections, String projectID, Calendar releaseDateCalendar, Vector fullLengths, boolean ifNotInAnAlbum, SelectionStatus status) {
/* 1254 */     Vector selectionIdVector = new Vector();
/* 1255 */     boolean returnValue = false;
/* 1256 */     boolean datesCompared = false;
/*      */ 
/*      */ 
/*      */     
/* 1260 */     if (status.getName().equals("TBS")) {
/* 1261 */       return false;
/*      */     }
/*      */     
/* 1264 */     if (selectionHasAValidProjectID(projectID)) {
/*      */       
/* 1266 */       for (int i = 0; i < allSelections.size(); i++) {
/*      */         
/* 1268 */         Selection t = (Selection)allSelections.elementAt(i);
/* 1269 */         SelectionSubConfiguration ssc = t.getSelectionSubConfig();
/* 1270 */         String subConfig = ssc.getSelectionSubConfigurationAbbreviation();
/*      */ 
/*      */         
/* 1273 */         if (t.getProjectID().equals(projectID) && 
/* 1274 */           fullLengths.contains(subConfig)) {
/*      */           
/* 1276 */           selectionIdVector.add(String.valueOf(t.getSelectionID()));
/*      */ 
/*      */           
/* 1279 */           if (status.getName().equals("TBS")) {
/* 1280 */             return true;
/*      */           }
/*      */         } 
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1288 */       for (int j = 0; j < selectionIdVector.size(); j++) {
/*      */         
/* 1290 */         String currentSelectionID = (String)selectionIdVector.elementAt(j);
/*      */         
/* 1292 */         for (int k = 0; k < allSelections.size(); k++) {
/* 1293 */           Selection ts = (Selection)allSelections.elementAt(k);
/*      */           
/* 1295 */           SelectionStatus status2 = ts.getSelectionStatus();
/* 1296 */           String selectionStatus2 = (status2 != null && status2.getName() != null) ? 
/* 1297 */             status2.getName() : "";
/*      */ 
/*      */           
/* 1300 */           if (String.valueOf(ts.getSelectionID()).equals(currentSelectionID)) {
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 1305 */             if (selectionStatus2.equals("TBS")) {
/* 1306 */               return true;
/*      */             }
/*      */             
/* 1309 */             Calendar albumDateCalendar = ts.getStreetDate();
/*      */             
/* 1311 */             if (releaseDateCalendar.before(albumDateCalendar)) {
/* 1312 */               return true;
/*      */             }
/* 1314 */             datesCompared = true;
/*      */           } 
/*      */         } 
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1322 */       if (datesCompared) {
/* 1323 */         return false;
/*      */       }
/*      */       
/* 1326 */       return ifNotInAnAlbum;
/*      */     } 
/*      */     
/* 1329 */     return true;
/*      */   }
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
/*      */   public static Vector getConfigType(int typeFlag) {
/* 1345 */     Vector allTheFullLengths = new Vector();
/* 1346 */     StringBuffer query = new StringBuffer();
/* 1347 */     int total = 0;
/* 1348 */     query.append(" SELECT distinct header.sub_configuration as fullLength FROM ");
/* 1349 */     query.append(" vi_release_header header, lookup_detail ");
/* 1350 */     query.append(" WHERE ");
/* 1351 */     query.append(" lookup_detail.value = header.sub_configuration AND ");
/*      */     
/* 1353 */     if (typeFlag == 0) {
/* 1354 */       query.append(" lookup_detail.description = 'Full Length'");
/*      */     } else {
/* 1356 */       query.append(" lookup_detail.description = 'Single'");
/*      */     } 
/*      */     
/* 1359 */     JdbcConnector connector = MilestoneHelper.getConnector(query.toString());
/* 1360 */     connector.setForwardOnly(false);
/* 1361 */     connector.runQuery();
/*      */ 
/*      */     
/* 1364 */     while (connector.more()) {
/*      */       
/*      */       try {
/* 1367 */         allTheFullLengths.add(connector.getField("fullLength", ""));
/*      */       }
/* 1369 */       catch (Exception exception) {}
/*      */ 
/*      */       
/* 1372 */       connector.next();
/*      */     } 
/*      */     
/* 1375 */     connector.close();
/*      */ 
/*      */     
/* 1378 */     if (typeFlag == 1) {
/* 1379 */       allTheFullLengths.add("CDMX");
/* 1380 */       allTheFullLengths.add("ECDMX");
/*      */     } 
/*      */     
/* 1383 */     return allTheFullLengths;
/*      */   }
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
/*      */   public static Vector getTitlesVector(Vector selections, Vector allFamilySelections) {
/* 1397 */     Vector fullLengthStrings = getConfigType(0);
/* 1398 */     Vector singleStrings = getConfigType(1);
/*      */     
/* 1400 */     Vector titleVector = new Vector();
/*      */ 
/*      */     
/* 1403 */     for (int i = 0; i < selections.size(); i++) {
/* 1404 */       Selection t = (Selection)selections.elementAt(i);
/* 1405 */       SelectionSubConfiguration ssc = t.getSelectionSubConfig();
/* 1406 */       String subConfig = ssc.getSelectionSubConfigurationAbbreviation();
/* 1407 */       String projectID = t.getProjectID();
/* 1408 */       SelectionStatus status = t.getSelectionStatus();
/* 1409 */       Calendar releaseDateCalendar = t.getStreetDate();
/*      */ 
/*      */       
/* 1412 */       if (fullLengthStrings.contains(subConfig)) {
/*      */         
/* 1414 */         titleVector.add(((Selection)selections.elementAt(i)).getTitle());
/*      */       }
/* 1416 */       else if (singleStrings.contains(subConfig) && 
/* 1417 */         !selectionHasAnAlbum2(selections, projectID, releaseDateCalendar, fullLengthStrings, false, allFamilySelections, status)) {
/*      */ 
/*      */ 
/*      */         
/* 1421 */         titleVector.add(((Selection)selections.elementAt(i)).getTitle());
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 1426 */     return titleVector;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean selectionHasAnAlbum2(Vector allSelections, String projectID, Calendar releaseDateCalendar, Vector fullLengths, boolean ifNotInAnAlbum, Vector allFamilySelections, SelectionStatus status) {
/* 1436 */     Vector selectionIdVector = new Vector();
/* 1437 */     boolean returnValue = false;
/* 1438 */     boolean datesCompared = false;
/*      */ 
/*      */ 
/*      */     
/* 1442 */     if (status.getName().equals("TBS")) {
/* 1443 */       return false;
/*      */     }
/*      */ 
/*      */     
/* 1447 */     if (selectionHasAValidProjectID(projectID)) {
/*      */ 
/*      */       
/* 1450 */       for (int i = 0; i < allFamilySelections.size(); i++) {
/*      */         
/* 1452 */         Selection t = (Selection)allFamilySelections.elementAt(i);
/* 1453 */         SelectionSubConfiguration ssc = t.getSelectionSubConfig();
/* 1454 */         String subConfig = ssc.getSelectionSubConfigurationAbbreviation();
/*      */ 
/*      */         
/* 1457 */         if (t.getProjectID().equals(projectID))
/*      */         {
/* 1459 */           if (fullLengths.contains(subConfig)) {
/*      */ 
/*      */             
/* 1462 */             selectionIdVector.add(String.valueOf(t.getSelectionID()));
/*      */ 
/*      */ 
/*      */             
/* 1466 */             if (status.getName().equals("TBS")) {
/* 1467 */               return true;
/*      */             }
/*      */           } 
/*      */         }
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 1475 */       for (int j = 0; j < selectionIdVector.size(); j++) {
/*      */         
/* 1477 */         String currentSelectionID = (String)selectionIdVector.elementAt(j);
/*      */         
/* 1479 */         for (int k = 0; k < allFamilySelections.size(); k++) {
/* 1480 */           Selection ts = (Selection)allFamilySelections.elementAt(k);
/*      */           
/* 1482 */           if (String.valueOf(ts.getSelectionID()).equals(currentSelectionID)) {
/*      */             
/* 1484 */             SelectionStatus status2 = ts.getSelectionStatus();
/* 1485 */             String selectionStatus2 = (status2 != null && status2.getName() != null) ? 
/* 1486 */               status2.getName() : "";
/*      */             
/* 1488 */             if (selectionStatus2.equals("TBS")) {
/* 1489 */               return true;
/*      */             }
/*      */             
/* 1492 */             Calendar albumDateCalendar = ts.getStreetDate();
/*      */             
/* 1494 */             if (releaseDateCalendar.before(albumDateCalendar)) {
/* 1495 */               return true;
/*      */             }
/* 1497 */             datesCompared = true;
/*      */           } 
/*      */         } 
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 1504 */       if (datesCompared) {
/* 1505 */         return false;
/*      */       }
/*      */       
/* 1508 */       return ifNotInAnAlbum;
/*      */     } 
/* 1510 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean selectionHasAValidProjectID(String projectID) {
/* 1521 */     if (projectID == null) {
/* 1522 */       return false;
/*      */     }
/*      */ 
/*      */     
/* 1526 */     if (projectID.indexOf('x') != -1) {
/* 1527 */       return false;
/*      */     }
/*      */     
/* 1530 */     if (projectID.indexOf('X') != -1) {
/* 1531 */       return false;
/*      */     }
/*      */     
/* 1534 */     if (projectID.equals("0000-0000") || projectID.equals("0000-00000")) {
/* 1535 */       return false;
/*      */     }
/*      */     
/* 1538 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static LookupObject getLookupObject(String abbreviation, Vector lookupVector) {
/* 1547 */     for (int j = 0; j < lookupVector.size(); j++) {
/*      */       
/* 1549 */       LookupObject lookupObject = (LookupObject)lookupVector.get(j);
/*      */       
/* 1551 */       if (lookupObject.getAbbreviation().equalsIgnoreCase(abbreviation))
/*      */       {
/* 1553 */         return lookupObject;
/*      */       }
/*      */     } 
/*      */     
/* 1557 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean singleHasNoAlbum(Vector allSelections, Selection selectionEntry, Vector fullLengthStrings) {
/* 1564 */     SelectionStatus status2 = selectionEntry.getSelectionStatus();
/* 1565 */     String selectionStatus2 = (status2 != null && status2.getName() != null) ? 
/* 1566 */       status2.getName() : "";
/* 1567 */     if (selectionStatus2.equals("TBS")) {
/* 1568 */       return false;
/*      */     }
/*      */     
/* 1571 */     for (int i = 0; i < allSelections.size(); i++) {
/* 1572 */       Selection currentSelection = (Selection)allSelections.elementAt(i);
/*      */       
/* 1574 */       if (currentSelection.getProjectID().equals(selectionEntry.getProjectID()))
/*      */       {
/*      */         
/* 1577 */         if (fullLengthStrings.contains(currentSelection.getSelectionSubConfig().getSelectionSubConfigurationAbbreviation())) {
/*      */ 
/*      */           
/* 1580 */           SelectionStatus status = currentSelection.getSelectionStatus();
/* 1581 */           String selectionStatus = (status != null && status.getName() != null) ? 
/* 1582 */             status.getName() : "";
/* 1583 */           if (selectionStatus.equals("TBS")) {
/* 1584 */             return false;
/*      */           }
/*      */           
/* 1587 */           if (selectionEntry.getStreetDate().before(currentSelection.getStreetDate()))
/*      */           {
/* 1589 */             return false;
/*      */           }
/*      */         } 
/*      */       }
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1601 */     String projectID = selectionEntry.getProjectID();
/*      */ 
/*      */ 
/*      */     
/* 1605 */     if (selectionHasAValidProjectID(projectID)) {
/*      */       
/* 1607 */       String streetDate = MilestoneHelper.getFormatedDate(selectionEntry.getStreetDate());
/* 1608 */       String numberOfAlbumsFound = "0";
/*      */       
/* 1610 */       StringBuffer query = new StringBuffer();
/* 1611 */       int total = 0;
/* 1612 */       query.append(" SELECT count(*) as numberOfAlbums FROM ");
/* 1613 */       query.append(" vi_release_header header");
/* 1614 */       query.append(" WHERE ");
/* 1615 */       query.append(" project_no = '" + projectID + "' AND ");
/* 1616 */       query.append(" release_type = 'CO' AND ");
/* 1617 */       query.append(" (sub_configuration = 'CD' OR sub_configuration = 'ECD') AND ");
/* 1618 */       query.append(" street_date <= '" + MilestoneHelper.escapeSingleQuotes(streetDate) + "'");
/*      */       
/* 1620 */       JdbcConnector connector = MilestoneHelper.getConnector(query.toString());
/* 1621 */       connector.setForwardOnly(false);
/* 1622 */       connector.runQuery();
/*      */ 
/*      */       
/* 1625 */       while (connector.more()) {
/*      */ 
/*      */         
/*      */         try {
/*      */           
/* 1630 */           numberOfAlbumsFound = connector.getField("numberOfAlbums", "");
/*      */         }
/* 1632 */         catch (Exception exception) {}
/*      */ 
/*      */         
/* 1635 */         connector.next();
/*      */       } 
/*      */       
/* 1638 */       connector.close();
/*      */       
/* 1640 */       if (!numberOfAlbumsFound.equals("0")) {
/* 1641 */         return false;
/*      */       }
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1647 */     return true;
/*      */   }
/*      */ }


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\EntPastRelAlbumsForPrintSubHandler.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */