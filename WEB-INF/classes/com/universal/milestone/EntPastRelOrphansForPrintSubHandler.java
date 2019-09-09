/*      */ package WEB-INF.classes.com.universal.milestone;
/*      */ 
/*      */ import com.techempower.ComponentLog;
/*      */ import com.techempower.gemini.Context;
/*      */ import com.techempower.gemini.FormCheckBox;
/*      */ import com.techempower.gemini.GeminiApplication;
/*      */ import com.universal.milestone.Cache;
/*      */ import com.universal.milestone.Company;
/*      */ import com.universal.milestone.EntPastRelOrphansForPrintSubHandler;
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
/*      */ public class EntPastRelOrphansForPrintSubHandler
/*      */   extends SecureHandler
/*      */ {
/*      */   public static final String COMPONENT_CODE = "hEntPastRelDates";
/*      */   public GeminiApplication application;
/*      */   public ComponentLog log;
/*      */   
/*      */   public EntPastRelOrphansForPrintSubHandler(GeminiApplication application) {
/*   69 */     this.application = application;
/*   70 */     this.log = application.getLog("hEntPastRelDates");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   78 */   public String getDescription() { return "Sub Report"; }
/*      */ 
/*      */ 
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
/*   92 */       HttpServletResponse sresponse = context.getResponse();
/*   93 */       context.putDelivery("status", new String("start_gathering"));
/*   94 */       context.includeJSP("status.jsp", "hiddenFrame");
/*   95 */       sresponse.setContentType("text/plain");
/*   96 */       sresponse.flushBuffer();
/*      */     }
/*   98 */     catch (Exception exception) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  104 */     Vector initialSelections = getSingleSelectionsForReport(context, reportType);
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
/*  284 */                       if (fullLengthStrings.contains(singlesSubconfig)) {
/*      */                         
/*  286 */                         if (selectionHasValidSingles(allFamilySelections, currentProjectID, ((Selection)selections.elementAt(iq)).getStreetDate(), ((Selection)selections.elementAt(iq)).getSelectionStatus()))
/*      */                         {
/*  288 */                           Vector albumAndSingles = new Vector();
/*  289 */                           albumAndSingles.add((Selection)selections.elementAt(iq));
/*      */                           
/*  291 */                           for (int y = 0; y < allFamilySelections.size(); y++)
/*      */                           {
/*      */                             
/*  294 */                             Selection currentSelection = (Selection)allFamilySelections.elementAt(y);
/*  295 */                             String singleSubConfig = currentSelection.getSelectionSubConfig().getSelectionSubConfigurationAbbreviation();
/*  296 */                             String singleProjectID = currentSelection.getProjectID();
/*  297 */                             if (singleProjectID.equals(currentProjectID) && singleStrings.contains(singleSubConfig)) {
/*  298 */                               albumAndSingles.add(currentSelection);
/*      */                             
/*      */                             }
/*      */                           
/*      */                           }
/*      */ 
/*      */                         
/*      */                         }
/*      */                       
/*      */                       }
/*  308 */                       else if (singleHasNoAlbum(allFamilySelections, selectionEntry, fullLengthStrings)) {
/*  309 */                         rowCounter++;
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
/*  324 */       rowCounter++;
/*      */ 
/*      */       
/*  327 */       DefaultTableLens table_contents = new DefaultTableLens(rowCounter, 7);
/*      */       
/*  329 */       table_contents.setColBorder(0);
/*  330 */       table_contents.setRowBorderColor(Color.lightGray);
/*      */       
/*  332 */       table_contents.setColWidth(0, 100);
/*  333 */       table_contents.setColWidth(1, 180);
/*  334 */       table_contents.setColWidth(2, 300);
/*  335 */       table_contents.setColWidth(3, 130);
/*  336 */       table_contents.setColWidth(4, 100);
/*  337 */       table_contents.setColWidth(5, 80);
/*  338 */       table_contents.setColWidth(6, 150);
/*      */       
/*  340 */       table_contents.setAlignment(0, 0, 33);
/*  341 */       table_contents.setAlignment(0, 1, 33);
/*  342 */       table_contents.setAlignment(0, 2, 33);
/*  343 */       table_contents.setAlignment(0, 3, 33);
/*  344 */       table_contents.setAlignment(0, 4, 33);
/*  345 */       table_contents.setAlignment(0, 5, 33);
/*  346 */       table_contents.setAlignment(0, 6, 36);
/*      */ 
/*      */       
/*  349 */       table_contents.setHeaderRowCount(1);
/*      */       
/*  351 */       table_contents.setRowBorder(-1, 0);
/*  352 */       table_contents.setRowBorder(0, 266240);
/*  353 */       table_contents.setRowBorderColor(0, Color.black);
/*      */ 
/*      */       
/*  356 */       table_contents.setRowAlignment(0, 32);
/*      */       
/*  358 */       table_contents.setObject(0, 0, "Family");
/*  359 */       table_contents.setObject(0, 1, "Artist");
/*  360 */       table_contents.setObject(0, 2, "Title");
/*  361 */       table_contents.setObject(0, 3, "Catalog Number");
/*  362 */       table_contents.setObject(0, 4, "Project Number");
/*  363 */       table_contents.setObject(0, 5, "Sub Config");
/*  364 */       table_contents.setObject(0, 6, "Release Date");
/*  365 */       table_contents.setRowInsets(0, new Insets(0, 0, 0, 0));
/*  366 */       table_contents.setRowFont(0, new Font("Arial", 3, 11));
/*      */ 
/*      */       
/*  369 */       int nextRow = 1;
/*      */       
/*  371 */       for (int n = 0; n < familyVector.size(); n++) {
/*      */ 
/*      */         
/*  374 */         String family = (String)familyVector.elementAt(n);
/*      */         
/*  376 */         String familyHeaderText = !family.trim().equals("") ? family : "Other";
/*      */         
/*  378 */         Hashtable artistTable = (Hashtable)selTable.get(family);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  384 */         Vector allFamilySelections = new Vector();
/*      */         
/*  386 */         if (artistTable != null) {
/*      */ 
/*      */           
/*  389 */           Enumeration projects = artistTable.keys();
/*  390 */           Vector artistVector = new Vector();
/*      */           
/*  392 */           while (projects.hasMoreElements()) {
/*  393 */             artistVector.addElement((String)projects.nextElement());
/*      */           }
/*      */           
/*  396 */           artistVector = MilestoneHelper.sortStrings(artistVector);
/*      */           
/*  398 */           for (int jj = 0; jj < artistVector.size(); jj++) {
/*      */             
/*  400 */             String selectsName = (artistVector.elementAt(jj) != null) ? (String)artistVector.elementAt(jj) : "";
/*  401 */             Vector selectVector = (Vector)artistTable.get(selectsName);
/*      */             
/*  403 */             if (selectVector != null) {
/*      */               
/*  405 */               selections = selectVector;
/*      */               
/*  407 */               for (int selc = 0; selc < selections.size(); selc++) {
/*  408 */                 allFamilySelections.add(selections.elementAt(selc));
/*      */               }
/*      */             } 
/*      */           } 
/*      */         } 
/*      */ 
/*      */         
/*  415 */         if (artistTable != null) {
/*      */ 
/*      */           
/*  418 */           Enumeration projects = artistTable.keys();
/*  419 */           Vector artistVector = new Vector();
/*      */           
/*  421 */           while (projects.hasMoreElements()) {
/*  422 */             artistVector.addElement((String)projects.nextElement());
/*      */           }
/*      */           
/*  425 */           artistVector = MilestoneHelper.sortStrings(artistVector);
/*      */           
/*  427 */           for (int rs = 0; rs < artistVector.size(); rs++) {
/*      */ 
/*      */             
/*  430 */             String selectsName = (artistVector.elementAt(rs) != null) ? (String)artistVector.elementAt(rs) : "";
/*  431 */             Vector selectVector = (Vector)artistTable.get(selectsName);
/*      */             
/*  433 */             if (selectVector != null) {
/*      */ 
/*      */               
/*  436 */               selections = selectVector;
/*      */ 
/*      */               
/*  439 */               MilestoneHelper.setSelectionSorting(selections, 1);
/*  440 */               Collections.sort(selections);
/*      */ 
/*      */               
/*  443 */               int count = 2;
/*  444 */               int numRec = selections.size();
/*  445 */               int chunkSize = numRec / 10;
/*      */               
/*  447 */               Vector fullLengthStrings = getConfigType(0);
/*  448 */               Vector singleStrings = getConfigType(1);
/*      */               
/*  450 */               Vector titlesVector = getTitlesVector(selections, allFamilySelections);
/*      */ 
/*      */               
/*  453 */               boolean hasAlbum = false;
/*  454 */               String albumArtist = "";
/*      */ 
/*      */               
/*      */               try {
/*  458 */                 int myPercent = rs / chunkSize;
/*  459 */                 if (myPercent > 1 && myPercent < 10)
/*  460 */                   count = myPercent; 
/*  461 */                 HttpServletResponse sresponse = context.getResponse();
/*  462 */                 context.putDelivery("status", new String("start_report"));
/*  463 */                 context.putDelivery("percent", new String(String.valueOf(count * 10)));
/*  464 */                 context.includeJSP("status.jsp", "hiddenFrame");
/*  465 */                 sresponse.setContentType("text/plain");
/*  466 */                 sresponse.flushBuffer();
/*      */               }
/*  468 */               catch (Exception exception) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */               
/*  474 */               for (int tu = 0; tu < titlesVector.size(); tu++) {
/*      */ 
/*      */                 
/*  477 */                 String selTitle = (String)titlesVector.elementAt(tu);
/*      */                 
/*  479 */                 Vector allProjectIDVector = new Vector();
/*      */                 
/*  481 */                 for (int jt = 0; jt < selections.size(); jt++) {
/*      */ 
/*      */ 
/*      */ 
/*      */                   
/*  486 */                   String selectionTitle = ((Selection)selections.elementAt(jt)).getTitle();
/*  487 */                   if (selectionTitle.equals(selTitle)) {
/*  488 */                     String projectID = ((Selection)selections.elementAt(jt)).getProjectID();
/*      */ 
/*      */                     
/*  491 */                     if (!allProjectIDVector.contains(projectID))
/*      */                     {
/*  493 */                       allProjectIDVector.add(projectID);
/*      */                     }
/*      */                   } 
/*      */                 } 
/*      */ 
/*      */                 
/*  499 */                 for (int p = 0; p < allProjectIDVector.size(); p++) {
/*      */                   
/*  501 */                   String currentProjectID = (String)allProjectIDVector.elementAt(p);
/*      */ 
/*      */                   
/*  504 */                   for (int i = 0; i < selections.size(); i++) {
/*      */ 
/*      */ 
/*      */                     
/*  508 */                     if (((Selection)selections.elementAt(i)).getProjectID().equals(currentProjectID) && (
/*  509 */                       (Selection)selections.elementAt(i)).getTitle().equals(selTitle)) {
/*      */                       
/*  511 */                       Selection selectionEntry = (Selection)selections.elementAt(i);
/*      */ 
/*      */                       
/*  514 */                       String sin = "";
/*  515 */                       String singlesSubconfig = selectionEntry.getSelectionSubConfig().getSelectionSubConfigurationAbbreviation();
/*      */ 
/*      */ 
/*      */ 
/*      */                       
/*  520 */                       if (fullLengthStrings.contains(singlesSubconfig)) {
/*      */                         
/*  522 */                         if (selectionHasValidSingles(allFamilySelections, currentProjectID, ((Selection)selections.elementAt(i)).getStreetDate(), ((Selection)selections.elementAt(i)).getSelectionStatus()))
/*      */                         {
/*  524 */                           Vector albumAndSingles = new Vector();
/*  525 */                           albumAndSingles.add((Selection)selections.elementAt(i));
/*      */                           
/*  527 */                           for (int y = 0; y < allFamilySelections.size(); y++)
/*      */                           {
/*      */                             
/*  530 */                             Selection currentSelection = (Selection)allFamilySelections.elementAt(y);
/*  531 */                             String singleSubConfig = currentSelection.getSelectionSubConfig().getSelectionSubConfigurationAbbreviation();
/*  532 */                             String singleProjectID = currentSelection.getProjectID();
/*  533 */                             if (singleProjectID.equals(currentProjectID) && singleStrings.contains(singleSubConfig)) {
/*  534 */                               albumAndSingles.add(currentSelection);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                             
/*      */                             }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                           
/*      */                           }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                         
/*      */                         }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                       
/*      */                       }
/*  660 */                       else if (singleHasNoAlbum(allFamilySelections, selectionEntry, fullLengthStrings)) {
/*      */                         String catalogNumber, singlesStreetDate;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                         
/*  670 */                         singlesSubconfig = selectionEntry.getSelectionSubConfig().getSelectionSubConfigurationAbbreviation();
/*  671 */                         String singlesTitle = selectionEntry.getTitle();
/*  672 */                         String singlesArtist = selectionEntry.getArtist();
/*  673 */                         String singlesFamily = selectionEntry.getFamily().getName();
/*  674 */                         String projectID = selectionEntry.getProjectID();
/*      */                         
/*  676 */                         SelectionStatus status = selectionEntry.getSelectionStatus();
/*  677 */                         String selectionStatus = (status != null && status.getName() != null) ? 
/*  678 */                           status.getName() : "";
/*      */ 
/*      */                         
/*  681 */                         if (selectionStatus.equals("TBS")) {
/*      */                           
/*  683 */                           singlesStreetDate = "TBS";
/*      */                         } else {
/*  685 */                           singlesStreetDate = MilestoneHelper.getFormatedDate(selectionEntry.getStreetDate());
/*      */                         } 
/*      */                         
/*  688 */                         if (SelectionManager.getLookupObjectValue(selectionEntry.getPrefixID()).equals("")) {
/*  689 */                           catalogNumber = selectionEntry.getSelectionNo();
/*      */                         } else {
/*  691 */                           catalogNumber = String.valueOf(SelectionManager.getLookupObjectValue(selectionEntry.getPrefixID())) + selectionEntry.getSelectionNo();
/*      */                         } 
/*      */ 
/*      */                         
/*  695 */                         table_contents.setColWidth(0, 100);
/*  696 */                         table_contents.setColWidth(1, 180);
/*  697 */                         table_contents.setColWidth(2, 300);
/*  698 */                         table_contents.setColWidth(3, 100);
/*  699 */                         table_contents.setColWidth(4, 100);
/*  700 */                         table_contents.setColWidth(5, 80);
/*  701 */                         table_contents.setColWidth(6, 150);
/*      */                         
/*  703 */                         table_contents.setObject(nextRow, 0, singlesFamily);
/*  704 */                         table_contents.setObject(nextRow, 1, singlesArtist);
/*  705 */                         table_contents.setObject(nextRow, 2, singlesTitle);
/*  706 */                         table_contents.setObject(nextRow, 3, catalogNumber);
/*  707 */                         table_contents.setObject(nextRow, 4, projectID);
/*  708 */                         table_contents.setObject(nextRow, 5, singlesSubconfig);
/*  709 */                         table_contents.setObject(nextRow, 6, singlesStreetDate);
/*      */                         
/*  711 */                         table_contents.setRowFont(nextRow, new Font("Arial", 0, 9));
/*  712 */                         table_contents.setRowInsets(nextRow, new Insets(0, 0, 2, 0));
/*      */                         
/*  714 */                         table_contents.setRowBorder(nextRow, 4097);
/*  715 */                         table_contents.setRowBorderColor(nextRow, Color.lightGray);
/*      */                         
/*  717 */                         table_contents.setAlignment(nextRow, 0, 9);
/*  718 */                         table_contents.setAlignment(nextRow, 1, 9);
/*  719 */                         table_contents.setAlignment(nextRow, 2, 9);
/*  720 */                         table_contents.setAlignment(nextRow, 3, 9);
/*  721 */                         table_contents.setAlignment(nextRow, 4, 9);
/*  722 */                         table_contents.setAlignment(nextRow, 5, 9);
/*  723 */                         table_contents.setAlignment(nextRow, 6, 12);
/*      */                         
/*  725 */                         nextRow++;
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
/*  739 */       report.setElement("table_colheaders", table_contents);
/*      */     
/*      */     }
/*  742 */     catch (Exception e) {
/*      */       
/*  744 */       System.out.println(">>>>>>>>ReportHandler.fillEntRelScheduleForPrint(): exception: " + e);
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
/*  761 */     Vector companies = MilestoneHelper.getUserCompanies(context);
/*  762 */     Company company = null;
/*  763 */     Vector precache = new Vector();
/*  764 */     Selection selection = null;
/*  765 */     StringBuffer query = new StringBuffer();
/*      */ 
/*      */     
/*  768 */     Form reportForm = (Form)context.getSessionValue("reportForm");
/*  769 */     boolean bScheduled = (reportForm.getElement("ScheduledReleases") != null) ? ((FormCheckBox)reportForm.getElement("ScheduledReleases")).isChecked() : 0;
/*      */     
/*  771 */     query.append("SELECT release_id, title, artist, street_date, configuration, sub_configuration, selection_no, status, prefix,  company_id, family_id, environment_id, label_id, project_no   from vi_Release_Header header WHERE ");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  781 */     query.append(" header.pd_indicator != 1 ");
/*      */     
/*  783 */     if (releaseType.equals("commercial")) {
/*  784 */       query.append(" AND header.release_type = 'CO' ");
/*      */     } else {
/*  786 */       query.append(" AND ( (header.sub_configuration IN ('ECD','CD','CASS','ALBUM') AND (header.release_type = 'CO')) OR ");
/*  787 */       query.append(" ( header.sub_configuration NOT IN ('ECD','CD','CASS','ALBUM') AND (header.release_type = 'PR') )) ");
/*      */     } 
/*      */ 
/*      */     
/*  791 */     query.append(" AND (header.configuration = 'CD' OR header.configuration = 'ECD') ");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  799 */     String[] strFamily = ReportSelectionsHelper.getMultiSelectionListValues("family", reportForm);
/*  800 */     String[] strEnvironment = ReportSelectionsHelper.getMultiSelectionListValues("environment", reportForm);
/*  801 */     String[] strCompany = ReportSelectionsHelper.getMultiSelectionListValues("company", reportForm);
/*  802 */     String[] strLabel = ReportSelectionsHelper.getMultiSelectionListValues("Label", reportForm);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  837 */     ReportSelectionsHelper.addMultSelectionToStringBuffer(query, "company_id", strCompany, true, "company", reportForm, false, true);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  845 */     ReportSelectionsHelper.addReleasingFamilyLabelFamilyMultSelect("family", context, query, reportForm);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  854 */     ReportSelectionsHelper.addMultSelectionToStringBuffer(query, "environment_id", strEnvironment, true, "environment", reportForm, true, true);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  864 */     ReportSelectionsHelper.addMultSelectionToStringBuffer(query, "label_id", strLabel, true, "Label", reportForm, false, true);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  869 */     int intUmvd = -1;
/*  870 */     int intPressPlay = -1;
/*      */ 
/*      */     
/*  873 */     Vector theFamilies = Cache.getFamilies();
/*  874 */     for (int i = 0; i < theFamilies.size(); i++) {
/*  875 */       Family family = (Family)theFamilies.get(i);
/*      */       
/*  877 */       if (family.getName().trim().equalsIgnoreCase("UMVD")) {
/*  878 */         intUmvd = family.getStructureID();
/*      */       }
/*  880 */       if (family.getName().trim().equalsIgnoreCase("Press Play")) {
/*  881 */         intPressPlay = family.getStructureID();
/*      */       }
/*      */     } 
/*      */     
/*  885 */     if (intUmvd > 0)
/*  886 */       query.append(" AND family_id  != " + intUmvd); 
/*  887 */     if (intPressPlay > 0) {
/*  888 */       query.append(" AND family_id != " + intPressPlay);
/*      */     }
/*      */ 
/*      */     
/*  892 */     String beginDate = "";
/*  893 */     beginDate = (reportForm.getStringValue("beginDate") != null && reportForm.getStringValue("beginDate").length() > 0) ? reportForm.getStringValue("beginDate") : "";
/*      */     
/*  895 */     String endDate = "";
/*  896 */     endDate = (reportForm.getStringValue("endDate") != null && reportForm.getStringValue("endDate").length() > 0) ? reportForm.getStringValue("endDate") : "";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  911 */     if (!beginDate.equalsIgnoreCase("")) {
/*  912 */       query.append(" AND ( ( ( street_date >= '" + MilestoneHelper.escapeSingleQuotes(beginDate) + "'");
/*      */     }
/*  914 */     if (!endDate.equalsIgnoreCase("")) {
/*      */       
/*  916 */       if (!beginDate.equalsIgnoreCase("")) {
/*  917 */         query.append(" AND street_date <= '" + MilestoneHelper.escapeSingleQuotes(endDate) + "')");
/*      */       } else {
/*  919 */         query.append(" AND (( street_date <= '" + MilestoneHelper.escapeSingleQuotes(endDate) + "'");
/*      */       } 
/*  921 */     } else if (!beginDate.equalsIgnoreCase("")) {
/*  922 */       query.append(" ) ");
/*      */     } 
/*      */ 
/*      */     
/*  926 */     if (beginDate.equalsIgnoreCase("") && endDate.equalsIgnoreCase("")) {
/*  927 */       query.append(" AND (((header.status = 'ACTIVE' OR  header.status = 'CLOSED' OR header.status = 'TBS'))");
/*      */     } else {
/*  929 */       query.append(" AND (header.status = 'ACTIVE' OR  header.status = 'CLOSED' OR header.status = 'TBS'))");
/*      */     } 
/*  931 */     query.append(" OR (header.status = 'TBS' AND street_date IS NULL))");
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  936 */     query.append(" ORDER BY artist, street_date");
/*      */ 
/*      */ 
/*      */     
/*  940 */     JdbcConnector connector = MilestoneHelper.getConnector(query.toString());
/*  941 */     connector.setForwardOnly(false);
/*  942 */     connector.runQuery();
/*      */     
/*  944 */     int totalCount = 0;
/*  945 */     int tenth = 0;
/*  946 */     totalCount = connector.getRowCount();
/*      */     
/*  948 */     tenth = totalCount / 5;
/*      */     
/*  950 */     if (tenth < 1) {
/*  951 */       tenth = 1;
/*      */     }
/*      */     
/*      */     try {
/*  955 */       HttpServletResponse sresponse = context.getResponse();
/*  956 */       context.putDelivery("status", new String("start_gathering"));
/*  957 */       context.putDelivery("percent", new String("10"));
/*  958 */       context.includeJSP("status.jsp", "hiddenFrame");
/*  959 */       sresponse.setContentType("text/plain");
/*  960 */       sresponse.flushBuffer();
/*      */     }
/*  962 */     catch (Exception exception) {}
/*      */ 
/*      */ 
/*      */     
/*  966 */     int recordCount = 0;
/*  967 */     int count = 0;
/*      */ 
/*      */     
/*  970 */     while (connector.more()) {
/*      */ 
/*      */       
/*      */       try {
/*  974 */         if (count < recordCount / tenth) {
/*      */           
/*  976 */           count = recordCount / tenth;
/*  977 */           HttpServletResponse sresponse = context.getResponse();
/*  978 */           context.putDelivery("status", new String("start_gathering"));
/*  979 */           context.putDelivery("percent", new String(String.valueOf(count * 10)));
/*  980 */           context.includeJSP("status.jsp", "hiddenFrame");
/*  981 */           sresponse.setContentType("text/plain");
/*  982 */           sresponse.flushBuffer();
/*      */         } 
/*      */         
/*  985 */         recordCount++;
/*      */ 
/*      */         
/*  988 */         selection = new Selection();
/*      */ 
/*      */         
/*  991 */         selection.setFamily((Family)MilestoneHelper.getStructureObject(connector.getIntegerField("family_id")));
/*      */ 
/*      */         
/*  994 */         selection.setEnvironment((Environment)MilestoneHelper.getStructureObject(connector.getIntegerField("environment_id")));
/*      */ 
/*      */         
/*  997 */         selection.setCompany((Company)MilestoneHelper.getStructureObject(connector.getIntegerField("company_id")));
/*      */ 
/*      */         
/* 1000 */         selection.setSelectionID(connector.getIntegerField("release_id"));
/*      */ 
/*      */         
/* 1003 */         selection.setPrefixID((PrefixCode)getLookupObject(connector.getField("prefix"), Cache.getPrefixCodes()));
/*      */ 
/*      */         
/* 1006 */         selection.setTitle(connector.getField("title", ""));
/*      */ 
/*      */         
/* 1009 */         selection.setArtistFirstName(connector.getField("artist", ""));
/* 1010 */         selection.setArtist(connector.getField("artist", ""));
/*      */ 
/*      */         
/* 1013 */         selection.setProjectID(connector.getField("project_no", ""));
/*      */ 
/*      */         
/* 1016 */         selection.setSelectionStatus((SelectionStatus)MilestoneHelper.getLookupObject(connector.getField("status"), 
/* 1017 */               Cache.getSelectionStatusList()));
/*      */ 
/*      */         
/* 1020 */         String streetDateString = connector.getFieldByName("street_date");
/* 1021 */         if (streetDateString != null) {
/* 1022 */           selection.setStreetDate(MilestoneHelper.getDatabaseDate(streetDateString));
/*      */         }
/* 1024 */         selection.setSelectionConfig(
/* 1025 */             MilestoneHelper.getSelectionConfigObject(connector.getField("configuration"), 
/* 1026 */               Cache.getSelectionConfigs()));
/*      */ 
/*      */         
/* 1029 */         selection.setSelectionSubConfig(MilestoneHelper.getSelectionSubConfigObject(connector.getField("sub_configuration"), selection.getSelectionConfig()));
/* 1030 */         selection.setSelectionNo(connector.getField("selection_no"));
/*      */         
/* 1032 */         precache.add(selection);
/* 1033 */         selection = null;
/* 1034 */         connector.next();
/*      */       
/*      */       }
/* 1037 */       catch (Exception exception) {}
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1042 */     connector.close();
/* 1043 */     company = null;
/*      */     
/* 1045 */     return precache;
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
/* 1062 */     Hashtable groupedByFamilyAndArtist = new Hashtable();
/* 1063 */     if (selections == null) {
/* 1064 */       return groupedByFamilyAndArtist;
/*      */     }
/* 1066 */     for (int i = 0; i < selections.size(); i++) {
/*      */       
/* 1068 */       Selection sel = (Selection)selections.elementAt(i);
/* 1069 */       if (sel != null) {
/*      */ 
/*      */         
/* 1072 */         String familyName = "";
/* 1073 */         Family family = sel.getFamily();
/* 1074 */         String artist = "";
/*      */         
/* 1076 */         if (family != null) {
/* 1077 */           familyName = (family.getName() == null) ? "" : family.getName();
/*      */         }
/*      */         
/* 1080 */         Hashtable familySubTable = (Hashtable)groupedByFamilyAndArtist.get(familyName);
/* 1081 */         if (familySubTable == null) {
/*      */ 
/*      */           
/* 1084 */           familySubTable = new Hashtable();
/* 1085 */           groupedByFamilyAndArtist.put(familyName, familySubTable);
/*      */         } 
/*      */ 
/*      */         
/* 1089 */         artist = sel.getArtist();
/* 1090 */         Vector artistsForFamily = (Vector)familySubTable.get(artist);
/* 1091 */         if (artistsForFamily == null) {
/*      */ 
/*      */           
/* 1094 */           artistsForFamily = new Vector();
/* 1095 */           familySubTable.put(artist, artistsForFamily);
/*      */         } 
/*      */ 
/*      */         
/* 1099 */         artistsForFamily.addElement(sel);
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 1104 */     return groupedByFamilyAndArtist;
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
/* 1121 */     Vector fullLengthStrings = getConfigType(0);
/* 1122 */     Vector singleStrings = getConfigType(1);
/*      */     
/* 1124 */     Vector finalSelections = new Vector();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1130 */     for (int selectCounter = 0; selectCounter < allSelections.size(); selectCounter++) {
/*      */ 
/*      */       
/* 1133 */       String projectID = ((Selection)allSelections.elementAt(selectCounter)).getProjectID();
/*      */       
/* 1135 */       SelectionStatus status = ((Selection)allSelections.elementAt(selectCounter)).getSelectionStatus();
/* 1136 */       Calendar releaseDateCalendar = ((Selection)allSelections.elementAt(selectCounter)).getStreetDate();
/* 1137 */       String title = ((Selection)allSelections.elementAt(selectCounter)).getTitle();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1143 */       Selection t = (Selection)allSelections.elementAt(selectCounter);
/* 1144 */       SelectionSubConfiguration ssc = t.getSelectionSubConfig();
/* 1145 */       String subConfig = ssc.getSelectionSubConfigurationAbbreviation();
/*      */       
/* 1147 */       if (fullLengthStrings.contains(subConfig)) {
/* 1148 */         if (selectionHasValidSingles(allSelections, projectID, releaseDateCalendar, status)) {
/* 1149 */           finalSelections.add((Selection)allSelections.elementAt(selectCounter));
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         }
/*      */ 
/*      */ 
/*      */       
/*      */       }
/* 1159 */       else if (singleStrings.contains(subConfig) && 
/* 1160 */         selectionHasAnAlbum(allSelections, projectID, releaseDateCalendar, fullLengthStrings, true, status)) {
/* 1161 */         finalSelections.add((Selection)allSelections.elementAt(selectCounter));
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 1166 */     return finalSelections;
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
/* 1186 */     Vector singleStrings = getConfigType(1);
/*      */ 
/*      */     
/* 1189 */     if (selectionHasAValidProjectID(projectID))
/*      */     {
/* 1191 */       for (int i = 0; i < allSelections.size(); i++) {
/*      */         
/* 1193 */         Selection t = (Selection)allSelections.elementAt(i);
/* 1194 */         SelectionSubConfiguration ssc = t.getSelectionSubConfig();
/* 1195 */         String subConfig = ssc.getSelectionSubConfigurationAbbreviation();
/*      */         
/* 1197 */         SelectionStatus singleStatus = t.getSelectionStatus();
/* 1198 */         String selectionStatus2 = (singleStatus != null && singleStatus.getName() != null) ? 
/* 1199 */           singleStatus.getName() : "";
/*      */ 
/*      */         
/* 1202 */         if (((Selection)allSelections.elementAt(i)).getProjectID().equals(projectID) && singleStrings.contains(subConfig) && !selectionStatus2.equals("TBS")) {
/*      */           
/* 1204 */           String selectionStatus = (status != null && status.getName() != null) ? 
/* 1205 */             status.getName() : "";
/*      */           
/* 1207 */           if (selectionStatus.equals("TBS")) {
/* 1208 */             return true;
/*      */           }
/*      */           
/* 1211 */           Calendar singleReleaseDate = ((Selection)allSelections.elementAt(i)).getStreetDate();
/*      */           
/* 1213 */           if (singleReleaseDate.before(releaseDateCalendar)) {
/* 1214 */             return true;
/*      */           }
/*      */         } 
/*      */       } 
/*      */     }
/* 1219 */     return false;
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
/* 1241 */     Vector selectionIdVector = new Vector();
/* 1242 */     boolean returnValue = false;
/* 1243 */     boolean datesCompared = false;
/*      */ 
/*      */ 
/*      */     
/* 1247 */     if (status.getName().equals("TBS")) {
/* 1248 */       return false;
/*      */     }
/*      */     
/* 1251 */     if (selectionHasAValidProjectID(projectID)) {
/*      */       
/* 1253 */       for (int i = 0; i < allSelections.size(); i++) {
/*      */         
/* 1255 */         Selection t = (Selection)allSelections.elementAt(i);
/* 1256 */         SelectionSubConfiguration ssc = t.getSelectionSubConfig();
/* 1257 */         String subConfig = ssc.getSelectionSubConfigurationAbbreviation();
/*      */ 
/*      */         
/* 1260 */         if (t.getProjectID().equals(projectID) && 
/* 1261 */           fullLengths.contains(subConfig)) {
/*      */           
/* 1263 */           selectionIdVector.add(String.valueOf(t.getSelectionID()));
/*      */ 
/*      */           
/* 1266 */           if (status.getName().equals("TBS")) {
/* 1267 */             return true;
/*      */           }
/*      */         } 
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1275 */       for (int j = 0; j < selectionIdVector.size(); j++) {
/*      */         
/* 1277 */         String currentSelectionID = (String)selectionIdVector.elementAt(j);
/*      */         
/* 1279 */         for (int k = 0; k < allSelections.size(); k++) {
/* 1280 */           Selection ts = (Selection)allSelections.elementAt(k);
/*      */           
/* 1282 */           SelectionStatus status2 = ts.getSelectionStatus();
/* 1283 */           String selectionStatus2 = (status2 != null && status2.getName() != null) ? 
/* 1284 */             status2.getName() : "";
/*      */ 
/*      */           
/* 1287 */           if (String.valueOf(ts.getSelectionID()).equals(currentSelectionID)) {
/*      */ 
/*      */ 
/*      */             
/* 1291 */             if (selectionStatus2.equals("TBS")) {
/* 1292 */               return true;
/*      */             }
/*      */             
/* 1295 */             Calendar albumDateCalendar = ts.getStreetDate();
/*      */             
/* 1297 */             if (releaseDateCalendar.before(albumDateCalendar)) {
/* 1298 */               return true;
/*      */             }
/* 1300 */             datesCompared = true;
/*      */           } 
/*      */         } 
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1308 */       if (datesCompared) {
/* 1309 */         return false;
/*      */       }
/*      */       
/* 1312 */       return ifNotInAnAlbum;
/*      */     } 
/*      */     
/* 1315 */     return true;
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
/* 1331 */     Vector allTheFullLengths = new Vector();
/* 1332 */     StringBuffer query = new StringBuffer();
/* 1333 */     int total = 0;
/* 1334 */     query.append(" SELECT distinct header.sub_configuration as fullLength FROM ");
/* 1335 */     query.append(" vi_release_header header, lookup_detail ");
/* 1336 */     query.append(" WHERE ");
/* 1337 */     query.append(" lookup_detail.value = header.sub_configuration AND ");
/*      */     
/* 1339 */     if (typeFlag == 0) {
/* 1340 */       query.append(" lookup_detail.description = 'Full Length'");
/*      */     } else {
/* 1342 */       query.append(" lookup_detail.description = 'Single'");
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1347 */     JdbcConnector connector = MilestoneHelper.getConnector(query.toString());
/* 1348 */     connector.setForwardOnly(false);
/* 1349 */     connector.runQuery();
/*      */ 
/*      */     
/* 1352 */     while (connector.more()) {
/*      */ 
/*      */       
/*      */       try {
/* 1356 */         allTheFullLengths.add(connector.getField("fullLength", ""));
/*      */       }
/* 1358 */       catch (Exception exception) {}
/*      */ 
/*      */       
/* 1361 */       connector.next();
/*      */     } 
/*      */     
/* 1364 */     connector.close();
/*      */ 
/*      */     
/* 1367 */     if (typeFlag == 1) {
/* 1368 */       allTheFullLengths.add("CDMX");
/* 1369 */       allTheFullLengths.add("ECDMX");
/*      */     } 
/*      */     
/* 1372 */     return allTheFullLengths;
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
/* 1386 */     Vector fullLengthStrings = getConfigType(0);
/* 1387 */     Vector singleStrings = getConfigType(1);
/*      */     
/* 1389 */     Vector titleVector = new Vector();
/*      */ 
/*      */     
/* 1392 */     for (int i = 0; i < selections.size(); i++) {
/* 1393 */       Selection t = (Selection)selections.elementAt(i);
/* 1394 */       SelectionSubConfiguration ssc = t.getSelectionSubConfig();
/* 1395 */       String subConfig = ssc.getSelectionSubConfigurationAbbreviation();
/* 1396 */       String projectID = t.getProjectID();
/* 1397 */       SelectionStatus status = t.getSelectionStatus();
/* 1398 */       Calendar releaseDateCalendar = t.getStreetDate();
/*      */ 
/*      */ 
/*      */       
/* 1402 */       if (fullLengthStrings.contains(subConfig)) {
/* 1403 */         titleVector.add(((Selection)selections.elementAt(i)).getTitle());
/* 1404 */       } else if (singleStrings.contains(subConfig)) {
/*      */         
/* 1406 */         if (!selectionHasAnAlbum2(selections, projectID, releaseDateCalendar, fullLengthStrings, false, allFamilySelections, status))
/*      */         {
/*      */           
/* 1409 */           titleVector.add(((Selection)selections.elementAt(i)).getTitle());
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/* 1414 */     return titleVector;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean selectionHasAnAlbum2(Vector allSelections, String projectID, Calendar releaseDateCalendar, Vector fullLengths, boolean ifNotInAnAlbum, Vector allFamilySelections, SelectionStatus status) {
/* 1424 */     Vector selectionIdVector = new Vector();
/* 1425 */     boolean returnValue = false;
/* 1426 */     boolean datesCompared = false;
/*      */ 
/*      */ 
/*      */     
/* 1430 */     if (status.getName().equals("TBS")) {
/* 1431 */       return false;
/*      */     }
/*      */ 
/*      */     
/* 1435 */     if (selectionHasAValidProjectID(projectID)) {
/*      */ 
/*      */       
/* 1438 */       for (int i = 0; i < allFamilySelections.size(); i++) {
/*      */         
/* 1440 */         Selection t = (Selection)allFamilySelections.elementAt(i);
/* 1441 */         SelectionSubConfiguration ssc = t.getSelectionSubConfig();
/* 1442 */         String subConfig = ssc.getSelectionSubConfigurationAbbreviation();
/*      */ 
/*      */         
/* 1445 */         if (t.getProjectID().equals(projectID))
/*      */         {
/* 1447 */           if (fullLengths.contains(subConfig)) {
/*      */ 
/*      */             
/* 1450 */             selectionIdVector.add(String.valueOf(t.getSelectionID()));
/*      */ 
/*      */ 
/*      */             
/* 1454 */             if (status.getName().equals("TBS")) {
/* 1455 */               return true;
/*      */             }
/*      */           } 
/*      */         }
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 1463 */       for (int j = 0; j < selectionIdVector.size(); j++) {
/*      */         
/* 1465 */         String currentSelectionID = (String)selectionIdVector.elementAt(j);
/*      */         
/* 1467 */         for (int k = 0; k < allFamilySelections.size(); k++) {
/* 1468 */           Selection ts = (Selection)allFamilySelections.elementAt(k);
/*      */           
/* 1470 */           if (String.valueOf(ts.getSelectionID()).equals(currentSelectionID)) {
/*      */             
/* 1472 */             SelectionStatus status2 = ts.getSelectionStatus();
/* 1473 */             String selectionStatus2 = (status2 != null && status2.getName() != null) ? 
/* 1474 */               status2.getName() : "";
/*      */             
/* 1476 */             if (selectionStatus2.equals("TBS")) {
/* 1477 */               return true;
/*      */             }
/*      */             
/* 1480 */             Calendar albumDateCalendar = ts.getStreetDate();
/*      */             
/* 1482 */             if (releaseDateCalendar.before(albumDateCalendar)) {
/* 1483 */               return true;
/*      */             }
/* 1485 */             datesCompared = true;
/*      */           } 
/*      */         } 
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 1492 */       if (datesCompared) {
/* 1493 */         return false;
/*      */       }
/*      */       
/* 1496 */       return ifNotInAnAlbum;
/*      */     } 
/* 1498 */     return false;
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
/* 1509 */     if (projectID == null) {
/* 1510 */       return false;
/*      */     }
/*      */ 
/*      */     
/* 1514 */     if (projectID.indexOf('x') != -1) {
/* 1515 */       return false;
/*      */     }
/*      */     
/* 1518 */     if (projectID.indexOf('X') != -1) {
/* 1519 */       return false;
/*      */     }
/*      */     
/* 1522 */     if (projectID.equals("0000-0000") || projectID.equals("0000-00000")) {
/* 1523 */       return false;
/*      */     }
/*      */     
/* 1526 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static LookupObject getLookupObject(String abbreviation, Vector lookupVector) {
/* 1535 */     for (int j = 0; j < lookupVector.size(); j++) {
/*      */       
/* 1537 */       LookupObject lookupObject = (LookupObject)lookupVector.get(j);
/*      */       
/* 1539 */       if (lookupObject.getAbbreviation().equalsIgnoreCase(abbreviation))
/*      */       {
/* 1541 */         return lookupObject;
/*      */       }
/*      */     } 
/*      */     
/* 1545 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean singleHasNoAlbum(Vector allSelections, Selection selectionEntry, Vector fullLengthStrings) {
/* 1552 */     SelectionStatus status2 = selectionEntry.getSelectionStatus();
/* 1553 */     String selectionStatus2 = (status2 != null && status2.getName() != null) ? 
/* 1554 */       status2.getName() : "";
/* 1555 */     if (selectionStatus2.equals("TBS")) {
/* 1556 */       return false;
/*      */     }
/*      */     
/* 1559 */     for (int i = 0; i < allSelections.size(); i++) {
/* 1560 */       Selection currentSelection = (Selection)allSelections.elementAt(i);
/*      */       
/* 1562 */       if (currentSelection.getProjectID().equals(selectionEntry.getProjectID()))
/*      */       {
/*      */         
/* 1565 */         if (fullLengthStrings.contains(currentSelection.getSelectionSubConfig().getSelectionSubConfigurationAbbreviation())) {
/*      */ 
/*      */           
/* 1568 */           SelectionStatus status = currentSelection.getSelectionStatus();
/* 1569 */           String selectionStatus = (status != null && status.getName() != null) ? 
/* 1570 */             status.getName() : "";
/* 1571 */           if (selectionStatus.equals("TBS")) {
/* 1572 */             return false;
/*      */           }
/*      */           
/* 1575 */           if (selectionEntry.getStreetDate().before(currentSelection.getStreetDate()))
/*      */           {
/* 1577 */             return false;
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
/* 1589 */     String projectID = selectionEntry.getProjectID();
/*      */ 
/*      */ 
/*      */     
/* 1593 */     if (selectionHasAValidProjectID(projectID)) {
/*      */       
/* 1595 */       String streetDate = MilestoneHelper.getFormatedDate(selectionEntry.getStreetDate());
/* 1596 */       String numberOfAlbumsFound = "0";
/*      */       
/* 1598 */       StringBuffer query = new StringBuffer();
/* 1599 */       int total = 0;
/* 1600 */       query.append(" SELECT count(*) as numberOfAlbums FROM ");
/* 1601 */       query.append(" vi_release_header header");
/* 1602 */       query.append(" WHERE ");
/* 1603 */       query.append(" project_no = '" + projectID + "' AND ");
/* 1604 */       query.append(" release_type = 'CO' AND ");
/* 1605 */       query.append(" (sub_configuration = 'CD' OR sub_configuration = 'ECD') AND ");
/* 1606 */       query.append(" street_date <= '" + MilestoneHelper.escapeSingleQuotes(streetDate) + "'");
/*      */       
/* 1608 */       JdbcConnector connector = MilestoneHelper.getConnector(query.toString());
/* 1609 */       connector.setForwardOnly(false);
/* 1610 */       connector.runQuery();
/*      */ 
/*      */       
/* 1613 */       while (connector.more()) {
/*      */ 
/*      */         
/*      */         try {
/*      */           
/* 1618 */           numberOfAlbumsFound = connector.getField("numberOfAlbums", "");
/*      */         }
/* 1620 */         catch (Exception exception) {}
/*      */ 
/*      */         
/* 1623 */         connector.next();
/*      */       } 
/*      */       
/* 1626 */       connector.close();
/*      */       
/* 1628 */       if (!numberOfAlbumsFound.equals("0")) {
/* 1629 */         return false;
/*      */       }
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1635 */     return true;
/*      */   }
/*      */ }


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\EntPastRelOrphansForPrintSubHandler.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */