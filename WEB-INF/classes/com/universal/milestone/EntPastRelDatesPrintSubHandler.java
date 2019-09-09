/*      */ package WEB-INF.classes.com.universal.milestone;
/*      */ 
/*      */ import com.techempower.ComponentLog;
/*      */ import com.techempower.gemini.Context;
/*      */ import com.techempower.gemini.FormCheckBox;
/*      */ import com.techempower.gemini.GeminiApplication;
/*      */ import com.universal.milestone.Cache;
/*      */ import com.universal.milestone.Company;
/*      */ import com.universal.milestone.EntPastRelDatesPrintSubHandler;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class EntPastRelDatesPrintSubHandler
/*      */   extends SecureHandler
/*      */ {
/*      */   public static final String COMPONENT_CODE = "hEntPastRelDates";
/*      */   public GeminiApplication application;
/*      */   public ComponentLog log;
/*      */   
/*      */   public EntPastRelDatesPrintSubHandler(GeminiApplication application) {
/*   74 */     this.application = application;
/*   75 */     this.log = application.getLog("hEntPastRelDates");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   83 */   public String getDescription() { return "Sub Report"; }
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
/*   97 */       HttpServletResponse sresponse = context.getResponse();
/*   98 */       context.putDelivery("status", new String("start_gathering"));
/*   99 */       context.includeJSP("status.jsp", "hiddenFrame");
/*  100 */       sresponse.setContentType("text/plain");
/*  101 */       sresponse.flushBuffer();
/*      */     }
/*  103 */     catch (Exception exception) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  109 */     Vector initialSelections = getSingleSelectionsForReport(context, reportType);
/*      */ 
/*      */     
/*  112 */     Vector selections = getValidSelections(initialSelections);
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/*  117 */       HttpServletResponse sresponse = context.getResponse();
/*  118 */       context.putDelivery("status", new String("start_report"));
/*  119 */       context.putDelivery("percent", new String("10"));
/*  120 */       context.includeJSP("status.jsp", "hiddenFrame");
/*  121 */       sresponse.setContentType("text/plain");
/*  122 */       sresponse.flushBuffer();
/*      */     }
/*  124 */     catch (Exception exception) {}
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
/*  135 */       Form reportForm = (Form)context.getSessionValue("reportForm");
/*      */       
/*  137 */       Calendar beginStDate = (reportForm.getStringValue("beginDate") != null && 
/*  138 */         reportForm.getStringValue("beginDate").length() > 0) ? 
/*  139 */         MilestoneHelper.getDate(reportForm.getStringValue("beginDate")) : null;
/*      */       
/*  141 */       Calendar endStDate = (reportForm.getStringValue("endDate") != null && 
/*  142 */         reportForm.getStringValue("endDate").length() > 0) ? 
/*  143 */         MilestoneHelper.getDate(reportForm.getStringValue("endDate")) : null;
/*      */       
/*  145 */       report.setElement("crs_startdate", MilestoneHelper.getFormatedDate(beginStDate));
/*  146 */       report.setElement("crs_enddate", MilestoneHelper.getFormatedDate(endStDate));
/*      */       
/*  148 */       SimpleDateFormat formatter = new SimpleDateFormat("MMMM d, yyyy");
/*  149 */       String todayLong = formatter.format(new Date());
/*  150 */       report.setElement("crs_bottomdate", todayLong);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  159 */       Hashtable selTable = groupSelectionsByFamilyAndArtist(selections);
/*      */       
/*  161 */       Enumeration families = selTable.keys();
/*  162 */       Vector familyVector = new Vector();
/*  163 */       while (families.hasMoreElements()) {
/*  164 */         familyVector.addElement(families.nextElement());
/*      */       }
/*      */       
/*  167 */       familyVector = MilestoneHelper.sortStrings(familyVector);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  175 */       int numArtist = 0;
/*  176 */       int numSelectsIDs = 0;
/*  177 */       int rowCounter = 0;
/*  178 */       for (int i = 0; i < familyVector.size(); i++) {
/*      */         
/*  180 */         String familyName = (familyVector.elementAt(i) != null) ? (String)familyVector.elementAt(i) : "";
/*  181 */         Hashtable artistTable = (Hashtable)selTable.get(familyName);
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  186 */         Vector allFamilySelections = new Vector();
/*      */         
/*  188 */         if (artistTable != null) {
/*      */ 
/*      */           
/*  191 */           Enumeration projects = artistTable.keys();
/*  192 */           Vector artistVector = new Vector();
/*      */           
/*  194 */           while (projects.hasMoreElements()) {
/*  195 */             artistVector.addElement((String)projects.nextElement());
/*      */           }
/*      */           
/*  198 */           artistVector = MilestoneHelper.sortStrings(artistVector);
/*      */           
/*  200 */           for (int jj = 0; jj < artistVector.size(); jj++) {
/*      */             
/*  202 */             String selectsName = (artistVector.elementAt(jj) != null) ? (String)artistVector.elementAt(jj) : "";
/*  203 */             Vector selectVector = (Vector)artistTable.get(selectsName);
/*      */             
/*  205 */             if (selectVector != null) {
/*      */               
/*  207 */               selections = selectVector;
/*      */               
/*  209 */               for (int selc = 0; selc < selections.size(); selc++) {
/*  210 */                 allFamilySelections.add(selections.elementAt(selc));
/*      */               }
/*      */             } 
/*      */           } 
/*      */         } 
/*      */         
/*  216 */         if (artistTable != null) {
/*      */ 
/*      */           
/*  219 */           Enumeration projects = artistTable.keys();
/*  220 */           Vector artistVector = new Vector();
/*      */           
/*  222 */           while (projects.hasMoreElements()) {
/*  223 */             artistVector.addElement((String)projects.nextElement());
/*      */           }
/*      */           
/*  226 */           artistVector = MilestoneHelper.sortStrings(artistVector);
/*      */           
/*  228 */           for (int rs = 0; rs < artistVector.size(); rs++) {
/*      */ 
/*      */             
/*  231 */             String selectsName = (artistVector.elementAt(rs) != null) ? (String)artistVector.elementAt(rs) : "";
/*  232 */             Vector selectVector = (Vector)artistTable.get(selectsName);
/*      */             
/*  234 */             if (selectVector != null) {
/*      */ 
/*      */               
/*  237 */               selections = selectVector;
/*      */ 
/*      */               
/*  240 */               MilestoneHelper.setSelectionSorting(selections, 1);
/*  241 */               Collections.sort(selections);
/*      */               
/*  243 */               Vector fullLengthStrings = getConfigType(0);
/*  244 */               Vector singleStrings = getConfigType(1);
/*      */               
/*  246 */               Vector titlesVector = getTitlesVector(selections, allFamilySelections);
/*      */ 
/*      */               
/*  249 */               for (int tu = 0; tu < titlesVector.size(); tu++) {
/*      */ 
/*      */ 
/*      */ 
/*      */                 
/*  254 */                 String selTitle = (String)titlesVector.elementAt(tu);
/*      */                 
/*  256 */                 Vector allProjectIDVector = new Vector();
/*      */                 
/*  258 */                 for (int jt = 0; jt < selections.size(); jt++) {
/*      */ 
/*      */ 
/*      */ 
/*      */                   
/*  263 */                   String selectionTitle = ((Selection)selections.elementAt(jt)).getTitle();
/*  264 */                   if (selectionTitle.equals(selTitle)) {
/*  265 */                     String projectID = ((Selection)selections.elementAt(jt)).getProjectID();
/*      */ 
/*      */                     
/*  268 */                     if (!allProjectIDVector.contains(projectID))
/*      */                     {
/*  270 */                       allProjectIDVector.add(projectID);
/*      */                     }
/*      */                   } 
/*      */                 } 
/*      */                 
/*  275 */                 for (int p = 0; p < allProjectIDVector.size(); p++) {
/*      */                   
/*  277 */                   String currentProjectID = (String)allProjectIDVector.elementAt(p);
/*      */ 
/*      */                   
/*  280 */                   for (int iq = 0; iq < selections.size(); iq++) {
/*      */ 
/*      */ 
/*      */                     
/*  284 */                     if (((Selection)selections.elementAt(iq)).getProjectID().equals(currentProjectID) && (
/*  285 */                       (Selection)selections.elementAt(iq)).getTitle().equals(selTitle)) {
/*      */                       
/*  287 */                       Selection selectionEntry = (Selection)selections.elementAt(iq);
/*      */ 
/*      */                       
/*  290 */                       String sin = "";
/*  291 */                       String singlesSubconfig = selectionEntry.getSelectionSubConfig().getSelectionSubConfigurationAbbreviation();
/*      */ 
/*      */ 
/*      */ 
/*      */                       
/*  296 */                       if (fullLengthStrings.contains(singlesSubconfig))
/*      */                       {
/*  298 */                         if (selectionHasValidSingles(allFamilySelections, currentProjectID, ((Selection)selections.elementAt(iq)).getStreetDate(), ((Selection)selections.elementAt(iq)).getSelectionStatus())) {
/*      */                           
/*  300 */                           Vector albumAndSingles = new Vector();
/*  301 */                           albumAndSingles.add((Selection)selections.elementAt(iq));
/*      */                           
/*  303 */                           for (int y = 0; y < allFamilySelections.size(); y++) {
/*      */ 
/*      */                             
/*  306 */                             Selection currentSelection = (Selection)allFamilySelections.elementAt(y);
/*  307 */                             String singleSubConfig = currentSelection.getSelectionSubConfig().getSelectionSubConfigurationAbbreviation();
/*  308 */                             String singleProjectID = currentSelection.getProjectID();
/*  309 */                             if (singleProjectID.equals(currentProjectID) && singleStrings.contains(singleSubConfig)) {
/*  310 */                               albumAndSingles.add(currentSelection);
/*      */                             }
/*      */                           } 
/*      */                           
/*  314 */                           for (int counter = 0; counter < albumAndSingles.size(); counter++) {
/*  315 */                             rowCounter++;
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
/*  340 */       rowCounter++;
/*      */ 
/*      */       
/*  343 */       int numRows = numSelectsIDs + 1;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  348 */       DefaultTableLens table_contents = new DefaultTableLens(rowCounter, 7);
/*      */       
/*  350 */       table_contents.setColBorder(0);
/*  351 */       table_contents.setRowBorderColor(Color.lightGray);
/*      */       
/*  353 */       table_contents.setColWidth(0, 100);
/*  354 */       table_contents.setColWidth(1, 180);
/*  355 */       table_contents.setColWidth(2, 300);
/*  356 */       table_contents.setColWidth(3, 130);
/*  357 */       table_contents.setColWidth(4, 100);
/*  358 */       table_contents.setColWidth(5, 80);
/*  359 */       table_contents.setColWidth(6, 150);
/*      */       
/*  361 */       table_contents.setAlignment(0, 0, 33);
/*  362 */       table_contents.setAlignment(0, 1, 33);
/*  363 */       table_contents.setAlignment(0, 2, 33);
/*  364 */       table_contents.setAlignment(0, 3, 33);
/*  365 */       table_contents.setAlignment(0, 4, 33);
/*  366 */       table_contents.setAlignment(0, 5, 33);
/*  367 */       table_contents.setAlignment(0, 6, 36);
/*      */ 
/*      */       
/*  370 */       table_contents.setHeaderRowCount(1);
/*      */       
/*  372 */       table_contents.setRowBorder(-1, 0);
/*  373 */       table_contents.setRowBorder(0, 266240);
/*  374 */       table_contents.setRowBorderColor(0, Color.black);
/*      */ 
/*      */       
/*  377 */       table_contents.setRowAlignment(0, 32);
/*      */       
/*  379 */       table_contents.setObject(0, 0, "Family");
/*  380 */       table_contents.setObject(0, 1, "Artist");
/*  381 */       table_contents.setObject(0, 2, "Title");
/*  382 */       table_contents.setObject(0, 3, "Catalog Number");
/*  383 */       table_contents.setObject(0, 4, "Project Number");
/*  384 */       table_contents.setObject(0, 5, "Sub Config");
/*  385 */       table_contents.setObject(0, 6, "Release Date");
/*  386 */       table_contents.setRowInsets(0, new Insets(0, 0, 0, 0));
/*  387 */       table_contents.setRowFont(0, new Font("Arial", 3, 11));
/*      */ 
/*      */       
/*  390 */       int nextRow = 1;
/*      */       
/*  392 */       for (int n = 0; n < familyVector.size(); n++) {
/*      */ 
/*      */         
/*  395 */         String family = (String)familyVector.elementAt(n);
/*      */         
/*  397 */         String familyHeaderText = !family.trim().equals("") ? family : "Other";
/*      */ 
/*      */         
/*  400 */         Hashtable artistTable = (Hashtable)selTable.get(family);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  406 */         Vector allFamilySelections = new Vector();
/*      */         
/*  408 */         if (artistTable != null) {
/*      */ 
/*      */           
/*  411 */           Enumeration projects = artistTable.keys();
/*  412 */           Vector artistVector = new Vector();
/*      */           
/*  414 */           while (projects.hasMoreElements()) {
/*  415 */             artistVector.addElement((String)projects.nextElement());
/*      */           }
/*      */           
/*  418 */           artistVector = MilestoneHelper.sortStrings(artistVector);
/*      */           
/*  420 */           for (int jj = 0; jj < artistVector.size(); jj++) {
/*      */             
/*  422 */             String selectsName = (artistVector.elementAt(jj) != null) ? (String)artistVector.elementAt(jj) : "";
/*  423 */             Vector selectVector = (Vector)artistTable.get(selectsName);
/*      */             
/*  425 */             if (selectVector != null) {
/*      */               
/*  427 */               selections = selectVector;
/*      */               
/*  429 */               for (int selc = 0; selc < selections.size(); selc++) {
/*  430 */                 allFamilySelections.add(selections.elementAt(selc));
/*      */               }
/*      */             } 
/*      */           } 
/*      */         } 
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
/*  446 */         if (artistTable != null) {
/*      */ 
/*      */           
/*  449 */           Enumeration projects = artistTable.keys();
/*  450 */           Vector artistVector = new Vector();
/*      */           
/*  452 */           while (projects.hasMoreElements()) {
/*  453 */             artistVector.addElement((String)projects.nextElement());
/*      */           }
/*      */           
/*  456 */           artistVector = MilestoneHelper.sortStrings(artistVector);
/*      */           
/*  458 */           for (int rs = 0; rs < artistVector.size(); rs++) {
/*      */ 
/*      */             
/*  461 */             String selectsName = (artistVector.elementAt(rs) != null) ? (String)artistVector.elementAt(rs) : "";
/*  462 */             Vector selectVector = (Vector)artistTable.get(selectsName);
/*      */             
/*  464 */             if (selectVector != null) {
/*      */ 
/*      */               
/*  467 */               selections = selectVector;
/*      */ 
/*      */               
/*  470 */               MilestoneHelper.setSelectionSorting(selections, 1);
/*  471 */               Collections.sort(selections);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */               
/*  478 */               Vector fullLengthStrings = getConfigType(0);
/*  479 */               Vector singleStrings = getConfigType(1);
/*      */               
/*  481 */               Vector titlesVector = getTitlesVector(selections, allFamilySelections);
/*      */ 
/*      */               
/*  484 */               boolean hasAlbum = false;
/*  485 */               String albumArtist = "";
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
/*  508 */               for (int tu = 0; tu < titlesVector.size(); tu++) {
/*      */ 
/*      */                 
/*  511 */                 String selTitle = (String)titlesVector.elementAt(tu);
/*      */                 
/*  513 */                 Vector allProjectIDVector = new Vector();
/*      */                 
/*  515 */                 for (int jt = 0; jt < selections.size(); jt++) {
/*      */ 
/*      */ 
/*      */ 
/*      */                   
/*  520 */                   String selectionTitle = ((Selection)selections.elementAt(jt)).getTitle();
/*  521 */                   if (selectionTitle.equals(selTitle)) {
/*  522 */                     String projectID = ((Selection)selections.elementAt(jt)).getProjectID();
/*      */ 
/*      */                     
/*  525 */                     if (!allProjectIDVector.contains(projectID))
/*      */                     {
/*  527 */                       allProjectIDVector.add(projectID);
/*      */                     }
/*      */                   } 
/*      */                 } 
/*      */ 
/*      */                 
/*  533 */                 for (int p = 0; p < allProjectIDVector.size(); p++) {
/*      */                   
/*  535 */                   String currentProjectID = (String)allProjectIDVector.elementAt(p);
/*      */ 
/*      */                   
/*  538 */                   for (int i = 0; i < selections.size(); i++) {
/*      */ 
/*      */ 
/*      */                     
/*  542 */                     if (((Selection)selections.elementAt(i)).getProjectID().equals(currentProjectID) && (
/*  543 */                       (Selection)selections.elementAt(i)).getTitle().equals(selTitle)) {
/*      */                       
/*  545 */                       Selection selectionEntry = (Selection)selections.elementAt(i);
/*      */ 
/*      */                       
/*  548 */                       String sin = "";
/*  549 */                       String singlesSubconfig = selectionEntry.getSelectionSubConfig().getSelectionSubConfigurationAbbreviation();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                       
/*  555 */                       if (fullLengthStrings.contains(singlesSubconfig))
/*      */                       {
/*  557 */                         if (selectionHasValidSingles(allFamilySelections, currentProjectID, ((Selection)selections.elementAt(i)).getStreetDate(), ((Selection)selections.elementAt(i)).getSelectionStatus())) {
/*      */                           
/*  559 */                           Vector albumAndSingles = new Vector();
/*  560 */                           albumAndSingles.add((Selection)selections.elementAt(i));
/*      */                           
/*  562 */                           for (int y = 0; y < allFamilySelections.size(); y++) {
/*      */ 
/*      */                             
/*  565 */                             Selection currentSelection = (Selection)allFamilySelections.elementAt(y);
/*  566 */                             String singleSubConfig = currentSelection.getSelectionSubConfig().getSelectionSubConfigurationAbbreviation();
/*  567 */                             String singleProjectID = currentSelection.getProjectID();
/*  568 */                             if (singleProjectID.equals(currentProjectID) && singleStrings.contains(singleSubConfig)) {
/*  569 */                               albumAndSingles.add(currentSelection);
/*      */                             }
/*      */                           } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                           
/*  577 */                           for (int counter = 0; counter < albumAndSingles.size(); counter++) {
/*      */                             String catalogNumber, singlesStreetDate;
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
/*  602 */                             selectionEntry = (Selection)albumAndSingles.elementAt(counter);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                             
/*  612 */                             singlesSubconfig = selectionEntry.getSelectionSubConfig().getSelectionSubConfigurationAbbreviation();
/*      */                             
/*  614 */                             SelectionStatus status = selectionEntry.getSelectionStatus();
/*  615 */                             String selectionStatus = (status != null && status.getName() != null) ? 
/*  616 */                               status.getName() : "";
/*      */ 
/*      */                             
/*  619 */                             if (selectionStatus.equals("TBS")) {
/*  620 */                               singlesStreetDate = "TBS";
/*      */                             
/*      */                             }
/*      */                             else {
/*      */                               
/*  625 */                               singlesStreetDate = MilestoneHelper.getFormatedDate(selectionEntry.getStreetDate());
/*      */                             } 
/*      */                             
/*  628 */                             String singlesTitle = selectionEntry.getTitle();
/*  629 */                             String singlesArtist = selectionEntry.getArtist();
/*  630 */                             String singlesFamily = selectionEntry.getFamily().getName();
/*  631 */                             String projectID = selectionEntry.getProjectID();
/*      */                             
/*  633 */                             if (SelectionManager.getLookupObjectValue(selectionEntry.getPrefixID()).equals("")) {
/*  634 */                               catalogNumber = selectionEntry.getSelectionNo();
/*      */                             } else {
/*  636 */                               catalogNumber = String.valueOf(SelectionManager.getLookupObjectValue(selectionEntry.getPrefixID())) + selectionEntry.getSelectionNo();
/*      */                             } 
/*      */                             
/*  639 */                             table_contents.setColWidth(0, 100);
/*  640 */                             table_contents.setColWidth(1, 180);
/*  641 */                             table_contents.setColWidth(2, 300);
/*  642 */                             table_contents.setColWidth(3, 130);
/*  643 */                             table_contents.setColWidth(4, 100);
/*  644 */                             table_contents.setColWidth(5, 80);
/*  645 */                             table_contents.setColWidth(6, 150);
/*      */                             
/*  647 */                             if (fullLengthStrings.contains(singlesSubconfig)) {
/*  648 */                               hasAlbum = true;
/*  649 */                               albumArtist = singlesArtist;
/*      */                             } 
/*      */ 
/*      */ 
/*      */ 
/*      */                             
/*  655 */                             if (fullLengthStrings.contains(singlesSubconfig) || (
/*  656 */                               singleStrings.contains(singlesSubconfig) && !hasAlbum)) {
/*  657 */                               table_contents.setObject(nextRow, 0, singlesFamily);
/*  658 */                               table_contents.setObject(nextRow, 1, singlesArtist);
/*  659 */                               table_contents.setObject(nextRow, 2, singlesTitle);
/*  660 */                             } else if (!singlesArtist.equals(albumArtist)) {
/*      */ 
/*      */                               
/*  663 */                               table_contents.setObject(nextRow, 0, singlesFamily);
/*  664 */                               table_contents.setObject(nextRow, 1, singlesArtist);
/*  665 */                               table_contents.setObject(nextRow, 2, "    " + singlesTitle);
/*      */                             } else {
/*  667 */                               table_contents.setObject(nextRow, 0, "");
/*  668 */                               table_contents.setObject(nextRow, 1, "");
/*  669 */                               table_contents.setObject(nextRow, 2, "    " + singlesTitle);
/*      */                             } 
/*  671 */                             table_contents.setObject(nextRow, 3, catalogNumber);
/*  672 */                             table_contents.setObject(nextRow, 4, projectID);
/*  673 */                             table_contents.setObject(nextRow, 5, singlesSubconfig);
/*  674 */                             table_contents.setObject(nextRow, 6, singlesStreetDate);
/*      */ 
/*      */                             
/*  677 */                             if (fullLengthStrings.contains(singlesSubconfig)) {
/*  678 */                               table_contents.setRowFont(nextRow, new Font("Arial", 0, 11));
/*      */                             } else {
/*  680 */                               table_contents.setRowFont(nextRow, new Font("Arial", 0, 9));
/*      */                             } 
/*      */                             
/*  683 */                             table_contents.setRowInsets(nextRow, new Insets(0, 0, 2, 0));
/*      */ 
/*      */                             
/*  686 */                             if (fullLengthStrings.contains(singlesSubconfig)) {
/*  687 */                               table_contents.setRowBorder(0);
/*      */                             
/*      */                             }
/*  690 */                             else if (counter + 1 == albumAndSingles.size()) {
/*  691 */                               table_contents.setRowBorder(nextRow, 4097);
/*  692 */                               table_contents.setRowBorderColor(nextRow, Color.lightGray);
/*      */                             } else {
/*  694 */                               table_contents.setRowBorder(0);
/*      */                             } 
/*      */                             
/*  697 */                             table_contents.setAlignment(nextRow, 0, 9);
/*  698 */                             table_contents.setAlignment(nextRow, 1, 9);
/*  699 */                             table_contents.setAlignment(nextRow, 2, 9);
/*  700 */                             table_contents.setAlignment(nextRow, 3, 9);
/*  701 */                             table_contents.setAlignment(nextRow, 4, 9);
/*  702 */                             table_contents.setAlignment(nextRow, 5, 9);
/*  703 */                             table_contents.setAlignment(nextRow, 6, 12);
/*      */                             
/*  705 */                             nextRow++;
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
/*  797 */       report.setElement("table_colheaders", table_contents);
/*      */     
/*      */     }
/*  800 */     catch (Exception e) {
/*      */       
/*  802 */       System.out.println(">>>>>>>>ReportHandler.fillEntRelScheduleForPrint(): exception: " + e);
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
/*  819 */     Vector companies = MilestoneHelper.getUserCompanies(context);
/*  820 */     Company company = null;
/*  821 */     Vector precache = new Vector();
/*  822 */     Selection selection = null;
/*  823 */     StringBuffer query = new StringBuffer();
/*      */ 
/*      */     
/*  826 */     Form reportForm = (Form)context.getSessionValue("reportForm");
/*  827 */     boolean bScheduled = (reportForm.getElement("ScheduledReleases") != null) ? ((FormCheckBox)reportForm.getElement("ScheduledReleases")).isChecked() : 0;
/*      */     
/*  829 */     query.append("SELECT release_id, title, artist, street_date, configuration, sub_configuration, selection_no, status, prefix,  company_id, family_id, environment_id, label_id, project_no   from vi_Release_Header header WHERE ");
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
/*  842 */     query.append(" header.pd_indicator != 1 ");
/*      */     
/*  844 */     if (releaseType.equals("commercial")) {
/*  845 */       query.append(" AND header.release_type = 'CO' ");
/*      */     } else {
/*  847 */       query.append(" AND ( (header.sub_configuration IN ('ECD','CD','CASS','ALBUM') AND (header.release_type = 'CO')) OR ");
/*  848 */       query.append(" ( header.sub_configuration NOT IN ('ECD','CD','CASS','ALBUM') AND (header.release_type = 'PR') )) ");
/*      */     } 
/*      */ 
/*      */     
/*  852 */     query.append(" AND (header.configuration = 'CD' OR header.configuration = 'ECD') ");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  860 */     String[] strFamily = ReportSelectionsHelper.getMultiSelectionListValues("family", reportForm);
/*  861 */     String[] strEnvironment = ReportSelectionsHelper.getMultiSelectionListValues("environment", reportForm);
/*  862 */     String[] strCompany = ReportSelectionsHelper.getMultiSelectionListValues("company", reportForm);
/*  863 */     String[] strLabel = ReportSelectionsHelper.getMultiSelectionListValues("Label", reportForm);
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
/*  898 */     ReportSelectionsHelper.addMultSelectionToStringBuffer(query, "company_id", strCompany, true, "company", reportForm, false, true);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  907 */     ReportSelectionsHelper.addReleasingFamilyLabelFamilyMultSelect("family", context, query, reportForm);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  915 */     ReportSelectionsHelper.addMultSelectionToStringBuffer(query, "environment_id", strEnvironment, true, "environment", reportForm, true, true);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  925 */     ReportSelectionsHelper.addMultSelectionToStringBuffer(query, "label_id", strLabel, true, "Label", reportForm, false, true);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  931 */     int intUmvd = -1;
/*  932 */     int intPressPlay = -1;
/*      */ 
/*      */     
/*  935 */     Vector theFamilies = Cache.getFamilies();
/*  936 */     for (int i = 0; i < theFamilies.size(); i++) {
/*  937 */       Family family = (Family)theFamilies.get(i);
/*      */       
/*  939 */       if (family.getName().trim().equalsIgnoreCase("UMVD")) {
/*  940 */         intUmvd = family.getStructureID();
/*      */       }
/*  942 */       if (family.getName().trim().equalsIgnoreCase("Press Play")) {
/*  943 */         intPressPlay = family.getStructureID();
/*      */       }
/*      */     } 
/*      */     
/*  947 */     if (intUmvd > 0)
/*  948 */       query.append(" AND family_id  != " + intUmvd); 
/*  949 */     if (intPressPlay > 0) {
/*  950 */       query.append(" AND family_id != " + intPressPlay);
/*      */     }
/*      */ 
/*      */     
/*  954 */     String beginDate = "";
/*  955 */     beginDate = (reportForm.getStringValue("beginDate") != null && reportForm.getStringValue("beginDate").length() > 0) ? reportForm.getStringValue("beginDate") : "";
/*      */     
/*  957 */     String endDate = "";
/*  958 */     endDate = (reportForm.getStringValue("endDate") != null && reportForm.getStringValue("endDate").length() > 0) ? reportForm.getStringValue("endDate") : "";
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
/*  973 */     if (!beginDate.equalsIgnoreCase("")) {
/*  974 */       query.append(" AND ( ( ( street_date >= '" + MilestoneHelper.escapeSingleQuotes(beginDate) + "'");
/*      */     }
/*  976 */     if (!endDate.equalsIgnoreCase("")) {
/*      */       
/*  978 */       if (!beginDate.equalsIgnoreCase("")) {
/*  979 */         query.append(" AND street_date <= '" + MilestoneHelper.escapeSingleQuotes(endDate) + "')");
/*      */       } else {
/*  981 */         query.append(" AND (( street_date <= '" + MilestoneHelper.escapeSingleQuotes(endDate) + "'");
/*      */       } 
/*  983 */     } else if (!beginDate.equalsIgnoreCase("")) {
/*  984 */       query.append(" ) ");
/*      */     } 
/*      */ 
/*      */     
/*  988 */     if (beginDate.equalsIgnoreCase("") && endDate.equalsIgnoreCase("")) {
/*  989 */       query.append(" AND (((header.status = 'ACTIVE' OR  header.status = 'CLOSED' OR header.status = 'TBS'))");
/*      */     } else {
/*  991 */       query.append(" AND (header.status = 'ACTIVE' OR  header.status = 'CLOSED' OR header.status = 'TBS'))");
/*      */     } 
/*  993 */     query.append(" OR (header.status = 'TBS' AND street_date IS NULL))");
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  998 */     query.append(" ORDER BY artist, street_date");
/*      */ 
/*      */ 
/*      */     
/* 1002 */     JdbcConnector connector = MilestoneHelper.getConnector(query.toString());
/* 1003 */     connector.setForwardOnly(false);
/* 1004 */     connector.runQuery();
/*      */     
/* 1006 */     int totalCount = 0;
/* 1007 */     int tenth = 0;
/* 1008 */     totalCount = connector.getRowCount();
/*      */     
/* 1010 */     tenth = totalCount / 5;
/*      */     
/* 1012 */     if (tenth < 1) {
/* 1013 */       tenth = 1;
/*      */     }
/*      */     
/*      */     try {
/* 1017 */       HttpServletResponse sresponse = context.getResponse();
/* 1018 */       context.putDelivery("status", new String("start_gathering"));
/* 1019 */       context.putDelivery("percent", new String("10"));
/* 1020 */       context.includeJSP("status.jsp", "hiddenFrame");
/* 1021 */       sresponse.setContentType("text/plain");
/* 1022 */       sresponse.flushBuffer();
/*      */     }
/* 1024 */     catch (Exception exception) {}
/*      */ 
/*      */ 
/*      */     
/* 1028 */     int recordCount = 0;
/* 1029 */     int count = 0;
/*      */ 
/*      */     
/* 1032 */     while (connector.more()) {
/*      */ 
/*      */       
/*      */       try {
/* 1036 */         if (count < recordCount / tenth) {
/*      */           
/* 1038 */           count = recordCount / tenth;
/* 1039 */           HttpServletResponse sresponse = context.getResponse();
/* 1040 */           context.putDelivery("status", new String("start_gathering"));
/* 1041 */           context.putDelivery("percent", new String(String.valueOf(count * 10)));
/* 1042 */           context.includeJSP("status.jsp", "hiddenFrame");
/* 1043 */           sresponse.setContentType("text/plain");
/* 1044 */           sresponse.flushBuffer();
/*      */         } 
/*      */         
/* 1047 */         recordCount++;
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1052 */         selection = new Selection();
/*      */ 
/*      */         
/* 1055 */         selection.setFamily((Family)MilestoneHelper.getStructureObject(connector.getIntegerField("family_id")));
/*      */ 
/*      */         
/* 1058 */         selection.setEnvironment((Environment)MilestoneHelper.getStructureObject(connector.getIntegerField("environment_id")));
/*      */ 
/*      */         
/* 1061 */         selection.setCompany((Company)MilestoneHelper.getStructureObject(connector.getIntegerField("company_id")));
/*      */ 
/*      */         
/* 1064 */         selection.setSelectionID(connector.getIntegerField("release_id"));
/*      */ 
/*      */         
/* 1067 */         selection.setPrefixID((PrefixCode)getLookupObject(connector.getField("prefix"), Cache.getPrefixCodes()));
/*      */ 
/*      */         
/* 1070 */         selection.setTitle(connector.getField("title", ""));
/*      */ 
/*      */         
/* 1073 */         selection.setArtistFirstName(connector.getField("artist", ""));
/* 1074 */         selection.setArtist(connector.getField("artist", ""));
/*      */ 
/*      */         
/* 1077 */         selection.setProjectID(connector.getField("project_no", ""));
/*      */ 
/*      */         
/* 1080 */         selection.setSelectionStatus(
/* 1081 */             (SelectionStatus)MilestoneHelper.getLookupObject(connector.getField("status"), Cache.getSelectionStatusList()));
/*      */ 
/*      */         
/* 1084 */         String streetDateString = connector.getFieldByName("street_date");
/* 1085 */         if (streetDateString != null) {
/* 1086 */           selection.setStreetDate(MilestoneHelper.getDatabaseDate(streetDateString));
/*      */         }
/* 1088 */         selection.setSelectionConfig(
/* 1089 */             MilestoneHelper.getSelectionConfigObject(connector.getField("configuration"), 
/* 1090 */               Cache.getSelectionConfigs()));
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1095 */         selection.setSelectionSubConfig(MilestoneHelper.getSelectionSubConfigObject(connector.getField("sub_configuration"), selection.getSelectionConfig()));
/* 1096 */         selection.setSelectionNo(connector.getField("selection_no"));
/*      */         
/* 1098 */         precache.add(selection);
/* 1099 */         selection = null;
/* 1100 */         connector.next();
/*      */       
/*      */       }
/* 1103 */       catch (Exception exception) {}
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1108 */     connector.close();
/* 1109 */     company = null;
/*      */ 
/*      */ 
/*      */     
/* 1113 */     return precache;
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
/* 1130 */     Hashtable groupedByFamilyAndArtist = new Hashtable();
/* 1131 */     if (selections == null) {
/* 1132 */       return groupedByFamilyAndArtist;
/*      */     }
/* 1134 */     for (int i = 0; i < selections.size(); i++) {
/*      */       
/* 1136 */       Selection sel = (Selection)selections.elementAt(i);
/* 1137 */       if (sel != null) {
/*      */ 
/*      */         
/* 1140 */         String familyName = "", companyName = "";
/* 1141 */         Family family = sel.getFamily();
/* 1142 */         Company company = sel.getCompany();
/* 1143 */         String artist = "";
/*      */         
/* 1145 */         if (family != null)
/* 1146 */           familyName = (family.getName() == null) ? "" : family.getName(); 
/* 1147 */         if (company != null) {
/* 1148 */           companyName = (company.getName() == null) ? "" : company.getName();
/*      */         }
/*      */ 
/*      */         
/* 1152 */         Hashtable familySubTable = (Hashtable)groupedByFamilyAndArtist.get(familyName);
/* 1153 */         if (familySubTable == null) {
/*      */ 
/*      */           
/* 1156 */           familySubTable = new Hashtable();
/* 1157 */           groupedByFamilyAndArtist.put(familyName, familySubTable);
/*      */         } 
/*      */ 
/*      */         
/* 1161 */         artist = sel.getArtist();
/* 1162 */         Vector artistsForFamily = (Vector)familySubTable.get(artist);
/* 1163 */         if (artistsForFamily == null) {
/*      */ 
/*      */           
/* 1166 */           artistsForFamily = new Vector();
/* 1167 */           familySubTable.put(artist, artistsForFamily);
/*      */         } 
/*      */ 
/*      */         
/* 1171 */         artistsForFamily.addElement(sel);
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 1176 */     return groupedByFamilyAndArtist;
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
/* 1193 */     Vector fullLengthStrings = getConfigType(0);
/* 1194 */     Vector singleStrings = getConfigType(1);
/*      */     
/* 1196 */     Vector finalSelections = new Vector();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1202 */     for (int selectCounter = 0; selectCounter < allSelections.size(); selectCounter++) {
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1207 */       String projectID = ((Selection)allSelections.elementAt(selectCounter)).getProjectID();
/*      */       
/* 1209 */       SelectionStatus status = ((Selection)allSelections.elementAt(selectCounter)).getSelectionStatus();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1215 */       Calendar releaseDateCalendar = ((Selection)allSelections.elementAt(selectCounter)).getStreetDate();
/*      */ 
/*      */ 
/*      */       
/* 1219 */       String title = ((Selection)allSelections.elementAt(selectCounter)).getTitle();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1225 */       Selection t = (Selection)allSelections.elementAt(selectCounter);
/* 1226 */       SelectionSubConfiguration ssc = t.getSelectionSubConfig();
/* 1227 */       String subConfig = ssc.getSelectionSubConfigurationAbbreviation();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1237 */       if (fullLengthStrings.contains(subConfig)) {
/*      */ 
/*      */ 
/*      */         
/* 1241 */         if (selectionHasValidSingles(allSelections, projectID, releaseDateCalendar, status))
/*      */         {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 1248 */           finalSelections.add((Selection)allSelections.elementAt(selectCounter));
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         }
/*      */ 
/*      */ 
/*      */       
/*      */       }
/* 1258 */       else if (singleStrings.contains(subConfig)) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1268 */         if (selectionHasAnAlbum(allSelections, projectID, releaseDateCalendar, fullLengthStrings, true, status))
/*      */         {
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
/* 1280 */           finalSelections.add((Selection)allSelections.elementAt(selectCounter));
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/* 1285 */     return finalSelections;
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
/*      */   public static boolean selectionHasValidSingles(Vector allSelections, String projectID, Calendar releaseDateCalendar, SelectionStatus status) {
/* 1307 */     Vector singleStrings = getConfigType(1);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1312 */     if (selectionHasAValidProjectID(projectID))
/*      */     {
/* 1314 */       for (int i = 0; i < allSelections.size(); i++) {
/*      */         
/* 1316 */         Selection t = (Selection)allSelections.elementAt(i);
/* 1317 */         SelectionSubConfiguration ssc = t.getSelectionSubConfig();
/* 1318 */         String subConfig = ssc.getSelectionSubConfigurationAbbreviation();
/*      */         
/* 1320 */         SelectionStatus singleStatus = t.getSelectionStatus();
/* 1321 */         String selectionStatus2 = (singleStatus != null && singleStatus.getName() != null) ? 
/* 1322 */           singleStatus.getName() : "";
/*      */ 
/*      */         
/* 1325 */         if (((Selection)allSelections.elementAt(i)).getProjectID().equals(projectID) && singleStrings.contains(subConfig) && !selectionStatus2.equals("TBS")) {
/*      */           
/* 1327 */           String selectionStatus = (status != null && status.getName() != null) ? 
/* 1328 */             status.getName() : "";
/*      */           
/* 1330 */           if (selectionStatus.equals("TBS")) {
/* 1331 */             return true;
/*      */           }
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
/* 1344 */           Calendar singleReleaseDate = ((Selection)allSelections.elementAt(i)).getStreetDate();
/*      */ 
/*      */ 
/*      */           
/* 1348 */           if (singleReleaseDate.before(releaseDateCalendar))
/*      */           {
/*      */ 
/*      */             
/* 1352 */             return true;
/*      */           }
/*      */         } 
/*      */       } 
/*      */     }
/* 1357 */     return false;
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
/* 1379 */     Vector selectionIdVector = new Vector();
/* 1380 */     boolean returnValue = false;
/* 1381 */     boolean datesCompared = false;
/*      */ 
/*      */ 
/*      */     
/* 1385 */     if (status.getName().equals("TBS")) {
/* 1386 */       return false;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1396 */     if (selectionHasAValidProjectID(projectID)) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1406 */       for (int i = 0; i < allSelections.size(); i++) {
/*      */         
/* 1408 */         Selection t = (Selection)allSelections.elementAt(i);
/* 1409 */         SelectionSubConfiguration ssc = t.getSelectionSubConfig();
/* 1410 */         String subConfig = ssc.getSelectionSubConfigurationAbbreviation();
/*      */ 
/*      */         
/* 1413 */         if (t.getProjectID().equals(projectID) && 
/* 1414 */           fullLengths.contains(subConfig)) {
/*      */           
/* 1416 */           selectionIdVector.add(String.valueOf(t.getSelectionID()));
/*      */ 
/*      */           
/* 1419 */           if (status.getName().equals("TBS")) {
/* 1420 */             return true;
/*      */           }
/*      */         } 
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1430 */       for (int j = 0; j < selectionIdVector.size(); j++) {
/*      */         
/* 1432 */         String currentSelectionID = (String)selectionIdVector.elementAt(j);
/*      */         
/* 1434 */         for (int k = 0; k < allSelections.size(); k++) {
/* 1435 */           Selection ts = (Selection)allSelections.elementAt(k);
/*      */           
/* 1437 */           SelectionStatus status2 = ts.getSelectionStatus();
/* 1438 */           String selectionStatus2 = (status2 != null && status2.getName() != null) ? 
/* 1439 */             status2.getName() : "";
/*      */ 
/*      */           
/* 1442 */           if (String.valueOf(ts.getSelectionID()).equals(currentSelectionID)) {
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 1447 */             if (selectionStatus2.equals("TBS")) {
/* 1448 */               return true;
/*      */             }
/*      */ 
/*      */ 
/*      */             
/* 1453 */             Calendar albumDateCalendar = ts.getStreetDate();
/*      */ 
/*      */ 
/*      */             
/* 1457 */             if (releaseDateCalendar.before(albumDateCalendar)) {
/* 1458 */               return true;
/*      */             }
/* 1460 */             datesCompared = true;
/*      */           } 
/*      */         } 
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1468 */       if (datesCompared) {
/* 1469 */         return false;
/*      */       }
/*      */       
/* 1472 */       return ifNotInAnAlbum;
/*      */     } 
/*      */     
/* 1475 */     return true;
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
/* 1491 */     Vector allTheFullLengths = new Vector();
/* 1492 */     StringBuffer query = new StringBuffer();
/* 1493 */     int total = 0;
/* 1494 */     query.append(" SELECT distinct header.sub_configuration as fullLength FROM ");
/* 1495 */     query.append(" vi_release_header header, lookup_detail ");
/* 1496 */     query.append(" WHERE ");
/* 1497 */     query.append(" lookup_detail.value = header.sub_configuration AND ");
/*      */     
/* 1499 */     if (typeFlag == 0) {
/* 1500 */       query.append(" lookup_detail.description = 'Full Length'");
/*      */     } else {
/* 1502 */       query.append(" lookup_detail.description = 'Single'");
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1507 */     JdbcConnector connector = MilestoneHelper.getConnector(query.toString());
/* 1508 */     connector.setForwardOnly(false);
/* 1509 */     connector.runQuery();
/*      */ 
/*      */     
/* 1512 */     while (connector.more()) {
/*      */ 
/*      */       
/*      */       try {
/* 1516 */         allTheFullLengths.add(connector.getField("fullLength", ""));
/*      */       }
/* 1518 */       catch (Exception exception) {}
/*      */ 
/*      */       
/* 1521 */       connector.next();
/*      */     } 
/*      */     
/* 1524 */     connector.close();
/*      */ 
/*      */     
/* 1527 */     if (typeFlag == 1) {
/* 1528 */       allTheFullLengths.add("CDMX");
/* 1529 */       allTheFullLengths.add("ECDMX");
/*      */     } 
/*      */     
/* 1532 */     return allTheFullLengths;
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
/* 1546 */     Vector fullLengthStrings = getConfigType(0);
/* 1547 */     Vector singleStrings = getConfigType(1);
/*      */     
/* 1549 */     Vector titleVector = new Vector();
/*      */ 
/*      */     
/* 1552 */     for (int i = 0; i < selections.size(); i++) {
/* 1553 */       Selection t = (Selection)selections.elementAt(i);
/* 1554 */       SelectionSubConfiguration ssc = t.getSelectionSubConfig();
/* 1555 */       String subConfig = ssc.getSelectionSubConfigurationAbbreviation();
/* 1556 */       String projectID = t.getProjectID();
/* 1557 */       SelectionStatus status = t.getSelectionStatus();
/* 1558 */       Calendar releaseDateCalendar = t.getStreetDate();
/*      */ 
/*      */ 
/*      */       
/* 1562 */       if (fullLengthStrings.contains(subConfig)) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1568 */         titleVector.add(((Selection)selections.elementAt(i)).getTitle());
/*      */       }
/* 1570 */       else if (singleStrings.contains(subConfig)) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1577 */         if (!selectionHasAnAlbum2(selections, projectID, releaseDateCalendar, fullLengthStrings, false, allFamilySelections, status))
/*      */         {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 1585 */           titleVector.add(((Selection)selections.elementAt(i)).getTitle());
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
/*      */     
/* 1597 */     return titleVector;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean selectionHasAnAlbum2(Vector allSelections, String projectID, Calendar releaseDateCalendar, Vector fullLengths, boolean ifNotInAnAlbum, Vector allFamilySelections, SelectionStatus status) {
/* 1607 */     Vector selectionIdVector = new Vector();
/* 1608 */     boolean returnValue = false;
/* 1609 */     boolean datesCompared = false;
/*      */ 
/*      */ 
/*      */     
/* 1613 */     if (status.getName().equals("TBS")) {
/* 1614 */       return false;
/*      */     }
/*      */ 
/*      */     
/* 1618 */     if (selectionHasAValidProjectID(projectID)) {
/*      */ 
/*      */       
/* 1621 */       for (int i = 0; i < allFamilySelections.size(); i++) {
/*      */         
/* 1623 */         Selection t = (Selection)allFamilySelections.elementAt(i);
/* 1624 */         SelectionSubConfiguration ssc = t.getSelectionSubConfig();
/* 1625 */         String subConfig = ssc.getSelectionSubConfigurationAbbreviation();
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1630 */         if (t.getProjectID().equals(projectID))
/*      */         {
/*      */ 
/*      */           
/* 1634 */           if (fullLengths.contains(subConfig)) {
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 1639 */             selectionIdVector.add(String.valueOf(t.getSelectionID()));
/*      */ 
/*      */ 
/*      */             
/* 1643 */             if (status.getName().equals("TBS")) {
/* 1644 */               return true;
/*      */             }
/*      */           } 
/*      */         }
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1654 */       for (int j = 0; j < selectionIdVector.size(); j++) {
/*      */         
/* 1656 */         String currentSelectionID = (String)selectionIdVector.elementAt(j);
/*      */         
/* 1658 */         for (int k = 0; k < allFamilySelections.size(); k++) {
/* 1659 */           Selection ts = (Selection)allFamilySelections.elementAt(k);
/*      */           
/* 1661 */           if (String.valueOf(ts.getSelectionID()).equals(currentSelectionID)) {
/*      */             
/* 1663 */             SelectionStatus status2 = ts.getSelectionStatus();
/* 1664 */             String selectionStatus2 = (status2 != null && status2.getName() != null) ? 
/* 1665 */               status2.getName() : "";
/*      */             
/* 1667 */             if (selectionStatus2.equals("TBS")) {
/* 1668 */               return true;
/*      */             }
/*      */             
/* 1671 */             Calendar albumDateCalendar = ts.getStreetDate();
/*      */             
/* 1673 */             if (releaseDateCalendar.before(albumDateCalendar)) {
/* 1674 */               return true;
/*      */             }
/* 1676 */             datesCompared = true;
/*      */           } 
/*      */         } 
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 1683 */       if (datesCompared) {
/* 1684 */         return false;
/*      */       }
/*      */       
/* 1687 */       return ifNotInAnAlbum;
/*      */     } 
/* 1689 */     return false;
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
/* 1700 */     if (projectID == null) {
/* 1701 */       return false;
/*      */     }
/*      */ 
/*      */     
/* 1705 */     if (projectID.indexOf('x') != -1) {
/* 1706 */       return false;
/*      */     }
/*      */     
/* 1709 */     if (projectID.indexOf('X') != -1) {
/* 1710 */       return false;
/*      */     }
/*      */     
/* 1713 */     if (projectID.equals("0000-0000") || projectID.equals("0000-00000")) {
/* 1714 */       return false;
/*      */     }
/*      */     
/* 1717 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static LookupObject getLookupObject(String abbreviation, Vector lookupVector) {
/* 1726 */     for (int j = 0; j < lookupVector.size(); j++) {
/*      */       
/* 1728 */       LookupObject lookupObject = (LookupObject)lookupVector.get(j);
/*      */       
/* 1730 */       if (lookupObject.getAbbreviation().equalsIgnoreCase(abbreviation))
/*      */       {
/* 1732 */         return lookupObject;
/*      */       }
/*      */     } 
/*      */     
/* 1736 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean singleHasNoAlbum(Vector allSelections, Selection selectionEntry, Vector fullLengthStrings) {
/* 1742 */     if (selectionEntry.getProjectID().equals("0070-05162")) {
/* 1743 */       System.out.println("****0070-05162 found****");
/*      */     }
/*      */ 
/*      */     
/* 1747 */     SelectionStatus status2 = selectionEntry.getSelectionStatus();
/* 1748 */     String selectionStatus2 = (status2 != null && status2.getName() != null) ? 
/* 1749 */       status2.getName() : "";
/* 1750 */     if (selectionStatus2.equals("TBS")) {
/* 1751 */       return false;
/*      */     }
/*      */     
/* 1754 */     for (int i = 0; i < allSelections.size(); i++) {
/* 1755 */       Selection currentSelection = (Selection)allSelections.elementAt(i);
/*      */       
/* 1757 */       if (currentSelection.getProjectID().equals(selectionEntry.getProjectID()))
/*      */       {
/*      */         
/* 1760 */         if (fullLengthStrings.contains(currentSelection.getSelectionSubConfig().getSelectionSubConfigurationAbbreviation())) {
/*      */ 
/*      */           
/* 1763 */           SelectionStatus status = currentSelection.getSelectionStatus();
/* 1764 */           String selectionStatus = (status != null && status.getName() != null) ? 
/* 1765 */             status.getName() : "";
/* 1766 */           if (selectionStatus.equals("TBS")) {
/* 1767 */             return false;
/*      */           }
/*      */           
/* 1770 */           if (selectionEntry.getStreetDate().before(currentSelection.getStreetDate()))
/*      */           {
/* 1772 */             return false;
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
/* 1784 */     String projectID = selectionEntry.getProjectID();
/*      */ 
/*      */ 
/*      */     
/* 1788 */     if (selectionHasAValidProjectID(projectID)) {
/*      */       
/* 1790 */       String streetDate = MilestoneHelper.getFormatedDate(selectionEntry.getStreetDate());
/* 1791 */       String numberOfAlbumsFound = "0";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1798 */       StringBuffer query = new StringBuffer();
/* 1799 */       int total = 0;
/* 1800 */       query.append(" SELECT count(*) as numberOfAlbums FROM ");
/* 1801 */       query.append(" vi_release_header header");
/* 1802 */       query.append(" WHERE ");
/* 1803 */       query.append(" project_no = '" + projectID + "' AND ");
/* 1804 */       query.append(" release_type = 'CO' AND ");
/* 1805 */       query.append(" (sub_configuration = 'CD' OR sub_configuration = 'ECD') AND ");
/* 1806 */       query.append(" street_date <= '" + MilestoneHelper.escapeSingleQuotes(streetDate) + "'");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1812 */       JdbcConnector connector = MilestoneHelper.getConnector(query.toString());
/* 1813 */       connector.setForwardOnly(false);
/* 1814 */       connector.runQuery();
/*      */ 
/*      */       
/* 1817 */       while (connector.more()) {
/*      */ 
/*      */         
/*      */         try {
/*      */           
/* 1822 */           numberOfAlbumsFound = connector.getField("numberOfAlbums", "");
/*      */         }
/* 1824 */         catch (Exception exception) {}
/*      */ 
/*      */         
/* 1827 */         connector.next();
/*      */       } 
/*      */       
/* 1830 */       connector.close();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1836 */       if (!numberOfAlbumsFound.equals("0")) {
/* 1837 */         return false;
/*      */       }
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1843 */     return true;
/*      */   }
/*      */ }


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\EntPastRelDatesPrintSubHandler.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */