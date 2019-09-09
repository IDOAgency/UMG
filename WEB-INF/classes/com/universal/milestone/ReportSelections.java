/*      */ package WEB-INF.classes.com.universal.milestone;
/*      */ 
/*      */ import com.techempower.gemini.Context;
/*      */ import com.techempower.gemini.FormCheckBox;
/*      */ import com.techempower.gemini.FormElement;
/*      */ import com.universal.milestone.Cache;
/*      */ import com.universal.milestone.Company;
/*      */ import com.universal.milestone.Day;
/*      */ import com.universal.milestone.Division;
/*      */ import com.universal.milestone.Environment;
/*      */ import com.universal.milestone.Family;
/*      */ import com.universal.milestone.Form;
/*      */ import com.universal.milestone.Genre;
/*      */ import com.universal.milestone.JdbcConnector;
/*      */ import com.universal.milestone.Label;
/*      */ import com.universal.milestone.LookupObject;
/*      */ import com.universal.milestone.MilestoneFormDropDownMenu;
/*      */ import com.universal.milestone.MilestoneHelper;
/*      */ import com.universal.milestone.MilestoneHelper_2;
/*      */ import com.universal.milestone.PrefixCode;
/*      */ import com.universal.milestone.ProductCategory;
/*      */ import com.universal.milestone.ReleaseType;
/*      */ import com.universal.milestone.Report;
/*      */ import com.universal.milestone.ReportSelections;
/*      */ import com.universal.milestone.ReportSelectionsHelper;
/*      */ import com.universal.milestone.ReportingServices;
/*      */ import com.universal.milestone.Schedule;
/*      */ import com.universal.milestone.ScheduleManager;
/*      */ import com.universal.milestone.ScheduledTask;
/*      */ import com.universal.milestone.Selection;
/*      */ import com.universal.milestone.SelectionConfiguration;
/*      */ import com.universal.milestone.SelectionManager;
/*      */ import com.universal.milestone.SelectionStatus;
/*      */ import com.universal.milestone.SelectionSubConfiguration;
/*      */ import com.universal.milestone.User;
/*      */ import com.universal.milestone.UserManager;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Calendar;
/*      */ import java.util.Date;
/*      */ import java.util.Iterator;
/*      */ import java.util.StringTokenizer;
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
/*      */ public class ReportSelections
/*      */ {
/*      */   private Vector finalSelections;
/*      */   private String reportName;
/*      */   private int reportProductType;
/*      */   private Context reportContext;
/*      */   private Report currentReport;
/*      */   
/*      */   public ReportSelections(Context context) {
/*   62 */     this.finalSelections = null;
/*   63 */     this.reportName = null;
/*   64 */     this.reportProductType = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*   73 */     this.reportContext = context;
/*      */     
/*   75 */     this.currentReport = (Report)context.getSessionValue("report");
/*   76 */     this.reportName = this.currentReport.getReportName();
/*   77 */     this.reportProductType = this.currentReport.getProductType();
/*      */   }
/*      */ 
/*      */   
/*      */   public int getCurrentUser() {
/*   82 */     User user = (User)this.reportContext.getSessionValue("user");
/*   83 */     int userId = 0;
/*   84 */     if (user != null)
/*   85 */       userId = user.getUserId(); 
/*   86 */     return userId;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Vector getFinalSelections() {
/*   92 */     Vector finalSelections = new Vector();
/*   93 */     System.out.println("ReportSelections::getFinalSelections()");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*   99 */     if (this.reportName.equals("UmlAddMov")) {
/*      */       
/*  101 */       Form reportForm = (Form)this.reportContext.getSessionValue("reportForm");
/*  102 */       String addsMovesFlag = reportForm.getStringValue("AddsMovesBoth");
/*  103 */       Vector AddsSelections = new Vector();
/*  104 */       Vector MovesSelections = new Vector();
/*      */       
/*  106 */       if (addsMovesFlag.equalsIgnoreCase("Adds") || addsMovesFlag.equalsIgnoreCase("Both"))
/*      */       {
/*  108 */         AddsSelections = getSelectionsForAddsReport(this.reportContext, this.reportProductType);
/*      */       }
/*      */       
/*  111 */       if (addsMovesFlag.equalsIgnoreCase("Moves") || addsMovesFlag.equalsIgnoreCase("Both"))
/*      */       {
/*  113 */         MovesSelections = getSelectionsForMovesReport(this.reportContext, this.reportProductType);
/*      */       }
/*      */       
/*  116 */       Vector addsMovesVector = new Vector();
/*      */       
/*  118 */       addsMovesVector.add(AddsSelections);
/*  119 */       addsMovesVector.add(MovesSelections);
/*      */       
/*  121 */       return addsMovesVector;
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  128 */     if (this.reportName.equals("PhyProdAct")) {
/*      */       
/*  130 */       Form reportForm = (Form)this.reportContext.getSessionValue("reportForm");
/*      */ 
/*      */       
/*  133 */       boolean CancelsActivityFlag = false;
/*  134 */       boolean ChangesActivityFlag = false;
/*  135 */       boolean DeletesActivityFlag = false;
/*  136 */       boolean AddsActivityFlag = false;
/*  137 */       boolean MovesActivityFlag = false;
/*  138 */       boolean AllActivityFlag = false;
/*      */       
/*  140 */       CancelsActivityFlag = ((FormCheckBox)reportForm.getElement("CancelsActivity")).isChecked();
/*  141 */       ChangesActivityFlag = ((FormCheckBox)reportForm.getElement("ChangesActivity")).isChecked();
/*  142 */       DeletesActivityFlag = ((FormCheckBox)reportForm.getElement("DeletesActivity")).isChecked();
/*  143 */       AddsActivityFlag = ((FormCheckBox)reportForm.getElement("AddsActivity")).isChecked();
/*  144 */       MovesActivityFlag = ((FormCheckBox)reportForm.getElement("MovesActivity")).isChecked();
/*  145 */       AllActivityFlag = ((FormCheckBox)reportForm.getElement("AllActivity")).isChecked();
/*      */       
/*  147 */       Vector ChangesSelections = new Vector();
/*  148 */       Vector CancelsSelections = new Vector();
/*  149 */       Vector DeletesSelections = new Vector();
/*  150 */       Vector AddsSelections = new Vector();
/*  151 */       Vector MovesSelections = new Vector();
/*      */       
/*  153 */       if (ChangesActivityFlag || AllActivityFlag) {
/*  154 */         ChangesSelections = getSelectionsForPhysicalProductActivityReportMultiples(this.reportContext, this.reportProductType, "changes");
/*      */       }
/*      */       
/*  157 */       if (CancelsActivityFlag || AllActivityFlag) {
/*  158 */         CancelsSelections = getSelectionsForPhysicalProductActivityReportMultiples(this.reportContext, this.reportProductType, "cancels");
/*      */       }
/*      */       
/*  161 */       if (DeletesActivityFlag || AllActivityFlag) {
/*  162 */         DeletesSelections = getSelectionsForPhysicalProductActivityReport(this.reportContext, this.reportProductType, "deletes");
/*      */       }
/*      */       
/*  165 */       if (AddsActivityFlag || AllActivityFlag) {
/*  166 */         AddsSelections = getSelectionsForPhysicalProductActivityReport(this.reportContext, this.reportProductType, "adds");
/*      */       }
/*      */       
/*  169 */       if (MovesActivityFlag || AllActivityFlag) {
/*  170 */         MovesSelections = getSelectionsForPhysicalProductActivityReportMultiples(this.reportContext, this.reportProductType, "moves");
/*      */       }
/*      */       
/*  173 */       Vector physicalProductActivityVector = new Vector();
/*      */       
/*  175 */       physicalProductActivityVector.add(ChangesSelections);
/*  176 */       physicalProductActivityVector.add(AddsSelections);
/*  177 */       physicalProductActivityVector.add(CancelsSelections);
/*  178 */       physicalProductActivityVector.add(DeletesSelections);
/*  179 */       physicalProductActivityVector.add(MovesSelections);
/*      */       
/*  181 */       return physicalProductActivityVector;
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  186 */     if (this.reportName.equals("UmeProdSch")) {
/*  187 */       finalSelections = getFilteredSelectionsForReport(this.reportContext, this.reportProductType);
/*      */     
/*      */     }
/*  190 */     else if (this.reportName.equals("TaskDueDt") || this.reportName.equals("MCARelSchd")) {
/*  191 */       finalSelections = getTaskDueSelectionsForReport(this.reportContext, this.reportProductType);
/*      */     }
/*      */     else {
/*      */       
/*  195 */       Vector selections = getAllSelectionsForUser(this.reportContext, this.reportName, this.reportProductType);
/*      */       
/*  197 */       if (this.reportName.equals("eInitRel")) {
/*  198 */         return selections;
/*      */       }
/*  200 */       finalSelections = filterSelectionsWithReportCriteria(selections, this.reportContext, this.reportName, this.reportProductType);
/*      */     } 
/*      */     
/*  203 */     return finalSelections;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Vector getAllSelectionsForUser(Context context, String reportname, int reportProductType) {
/*  233 */     Vector companies = MilestoneHelper.getUserCompanies(context);
/*  234 */     Company company = null;
/*  235 */     Vector precache = new Vector();
/*  236 */     Selection selection = null;
/*  237 */     StringBuffer query = new StringBuffer(200);
/*      */ 
/*      */     
/*  240 */     Form reportForm = (Form)context.getSessionValue("reportForm");
/*  241 */     boolean bScheduled = (reportForm.getElement("ScheduledReleases") != null) ? ((FormCheckBox)reportForm.getElement("ScheduledReleases")).isChecked() : 0;
/*      */ 
/*      */ 
/*      */     
/*  245 */     String strUmlKey = (reportForm.getStringValue("umlKeyTask") != null) ? reportForm.getStringValue("umlKeyTask") : "";
/*  246 */     Report report = (Report)context.getSessionValue("Report");
/*      */ 
/*      */ 
/*      */     
/*  250 */     if (reportname.equals("eInitRel")) {
/*      */       
/*  252 */       if (bScheduled || (!strUmlKey.equalsIgnoreCase("") && !strUmlKey.trim().equalsIgnoreCase("all")))
/*      */       {
/*  254 */         query.append("SELECT DISTINCT header.[release_id],header.[impact_date], header.[street_date],  header.[artist],header.[title],header.[upc],header.[packaging],  header.[coordinator_contacts],header.[product_line],header.[parental_indicator],  header.[territory],header[comments],  header.[imprint]  FROM vi_Release_Header header with (nolock), vi_Release_Detail detail with (nolock) WHERE (header.[release_id] = detail.[release_id]) AND (");
/*      */ 
/*      */       
/*      */       }
/*      */       else
/*      */       {
/*      */ 
/*      */         
/*  262 */         query.append("SELECT release_id,impact_date,street_date, artist,title,upc,packaging,  coordinator_contacts,product_line,parental_indicator,  territory,comments,  [imprint]  from vi_Release_Header header with (nolock) WHERE (");
/*      */ 
/*      */       
/*      */       }
/*      */ 
/*      */ 
/*      */     
/*      */     }
/*  270 */     else if (reportname.equals("ToDoList")) {
/*      */ 
/*      */       
/*  273 */       query.append("SELECT DISTINCT header.[release_id], header.[artist], header.[title], header.[artist_first_name], header.[artist_last_name], header.[street_date], header.[configuration], header.[sub_configuration], header.[upc], header.[prefix], header.[selection_no], header.[status],  header.[company_id], header.[family_id], header.[release_family_id], header.[environment_id], header.[label_id], detail.[owner], detail.[due_date], task.[department], task.[name], header.[contact_id], detail.[completion_date],  header.[imprint]  FROM vi_Release_Header header with (nolock) INNER JOIN vi_release_detail detail with (nolock) ON header.[release_id] = detail.[release_id]  INNER JOIN vi_Task task with (nolock) ON detail.[task_id] = task.[task_id] WHERE  not (detail.[due_date] is null) and detail.[completion_date] is null and (");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     }
/*  289 */     else if (bScheduled || (!strUmlKey.equalsIgnoreCase("") && !strUmlKey.trim().equalsIgnoreCase("all") && 
/*  290 */       !report.getReportName().trim().equalsIgnoreCase("New Rel."))) {
/*      */       
/*  292 */       query.append("SELECT DISTINCT header.[release_id], header.[artist],  UPPER(header.artist) as 'UpperArtist', UPPER(header.title) as 'UpperTitle',  header.[title], header.[artist_first_name], header.[artist_last_name],  header.[artist_first_name] + ' ' + header.[artist_last_name] AS fl_artist, header.[street_date], header.[digital_rls_date], header.[configuration], header.[sub_configuration], header.[upc], header.[prefix], header.[selection_no], header.[status],  header.[company_id], header.[environment_id], header.[family_id], header.[release_family_id], header.[label_id],  header.[imprint], header.[comments]  FROM vi_Release_Header header with (nolock), vi_Release_Detail detail with (nolock) WHERE (header.[release_id] = detail.[release_id]) AND (");
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     }
/*      */     else {
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  303 */       query.append("SELECT release_id, title, artist_first_name, artist_last_name, street_date, digital_rls_date, configuration, sub_configuration, artist_first_name + ' ' + artist_last_name AS fl_artist, upc, prefix, selection_no, status,  company_id, environment_id, family_id, release_family_id, label_id,  header.[imprint], header.[comments]  from vi_Release_Header header with (nolock) ");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  312 */       String strProdGroupCode = (reportForm.getStringValue("productionGroupCode") != null) ? reportForm.getStringValue("productionGroupCode") : "";
/*  313 */       if (!strProdGroupCode.equalsIgnoreCase("") && !strProdGroupCode.equalsIgnoreCase("All")) {
/*  314 */         query.append(" INNER JOIN ArchimedesLabels atlasLabels with (nolock) ON header.[Archimedes_id] = atlasLabels.[ArchimedesID]  WHERE ( atlasLabels.[ProductionGroupCode] ='" + 
/*  315 */             strProdGroupCode + "' AND ");
/*      */       } else {
/*  317 */         query.append(" WHERE ( ");
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  324 */     String[] strFamily = ReportSelectionsHelper.getMultiSelectionListValues("family", reportForm);
/*  325 */     String[] strEnvironment = ReportSelectionsHelper.getMultiSelectionListValues("environment", reportForm);
/*  326 */     String[] strCompany = ReportSelectionsHelper.getMultiSelectionListValues("company", reportForm);
/*  327 */     String[] strLabel = ReportSelectionsHelper.getMultiSelectionListValues("Label", reportForm);
/*  328 */     String strBgnDueDate = (reportForm.getStringValue("beginDueDate") != null) ? reportForm.getStringValue("beginDueDate") : "";
/*  329 */     String strEndDueDate = (reportForm.getStringValue("endDueDate") != null) ? reportForm.getStringValue("endDueDate") : "";
/*      */     
/*  331 */     String strProductType = (reportForm.getStringValue("ProductType") != null) ? reportForm.getStringValue("ProductType") : "";
/*  332 */     if (strProductType.equals("")) {
/*      */       
/*  334 */       strProductType = String.valueOf(reportProductType);
/*      */     
/*      */     }
/*  337 */     else if (strProductType.equals("Physical")) {
/*  338 */       strProductType = String.valueOf(0);
/*  339 */     } else if (strProductType.equals("Digital")) {
/*  340 */       strProductType = String.valueOf(1);
/*  341 */     } else if (strProductType.equals("Both")) {
/*  342 */       strProductType = String.valueOf(2);
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  356 */     ReportSelectionsHelper.addMultSelectionToStringBuffer(query, "environment_id", strEnvironment, true, "environment", reportForm, true, false);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  363 */     ReportSelectionsHelper.addMultSelectionToStringBuffer(query, "company_id", strCompany, true, "company", reportForm, false, true);
/*      */     
/*  365 */     if (reportname.equals("ToDoList")) {
/*      */ 
/*      */ 
/*      */       
/*  369 */       if (!strBgnDueDate.equalsIgnoreCase("") && 
/*  370 */         !strBgnDueDate.equalsIgnoreCase("0")) {
/*  371 */         query.append(" AND due_date >= '" + strBgnDueDate + "' ");
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*  376 */       if (!strEndDueDate.equalsIgnoreCase("") && 
/*  377 */         !strEndDueDate.equalsIgnoreCase("0")) {
/*  378 */         query.append(" AND due_date <= '" + strEndDueDate + "' ");
/*      */       }
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  384 */     ReportSelectionsHelper.addReleasingFamilyLabelFamilyMultSelect("family", context, query, reportForm);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  391 */     ReportSelectionsHelper.addMultSelectionToStringBuffer(query, "label_id", strLabel, true, "Label", reportForm, false, true);
/*      */ 
/*      */     
/*  394 */     if (strProductType.equals(Integer.toString(1))) {
/*  395 */       query.append(" AND digital_flag = 1 ");
/*  396 */     } else if (strProductType.equals(Integer.toString(0))) {
/*  397 */       query.append(" AND digital_flag = 0 ");
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  404 */     if (!strUmlKey.equalsIgnoreCase("") && !strUmlKey.trim().equalsIgnoreCase("all")) {
/*      */       
/*  406 */       int intUml = -1;
/*      */ 
/*      */       
/*  409 */       Vector theFamilies = Cache.getFamilies();
/*  410 */       for (int i = 0; i < theFamilies.size(); i++) {
/*  411 */         Family family = (Family)theFamilies.get(i);
/*      */         
/*  413 */         if (family.getName().trim().equalsIgnoreCase("UML")) {
/*  414 */           intUml = family.getStructureID();
/*      */           
/*      */           break;
/*      */         } 
/*      */       } 
/*      */       
/*  420 */       if (intUml > 0)
/*      */       {
/*      */         
/*  423 */         if (report.getReportName().trim().equalsIgnoreCase("New Rel.")) {
/*      */           
/*  425 */           query.append(" AND (EXISTS (SELECT owner FROM vi_release_detail detail where owner = " + intUml);
/*  426 */           query.append(" AND detail.release_id = header.release_id)");
/*      */           
/*  428 */           query.append(" OR NOT EXISTS ");
/*  429 */           query.append(" (SELECT detailA.release_id FROM vi_release_detail detailA");
/*  430 */           query.append(" WHERE detailA.release_id = header.release_id)) ");
/*      */ 
/*      */         
/*      */         }
/*  434 */         else if (strUmlKey.equalsIgnoreCase("UML")) {
/*  435 */           query.append(" AND detail.owner = " + intUml);
/*      */         } else {
/*  437 */           query.append(" AND detail.owner != " + intUml);
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
/*      */ 
/*      */     
/*  451 */     String[] strConfiguration = null;
/*      */     try {
/*  453 */       MilestoneFormDropDownMenu configList = (MilestoneFormDropDownMenu)reportForm.getElement("configurationList");
/*  454 */       if (configList != null) {
/*  455 */         ArrayList configListAl = configList.getStringValues();
/*  456 */         if (configListAl != null) {
/*      */           
/*  458 */           strConfiguration = new String[configListAl.size()];
/*  459 */           strConfiguration = (String[])configListAl.toArray(strConfiguration);
/*      */         } 
/*      */       } 
/*  462 */     } catch (Exception e) {
/*  463 */       e.printStackTrace();
/*  464 */       System.out.println("<<< Configuration vector execption " + e.getMessage());
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  471 */     String[] strSubconfiguration = null;
/*      */     try {
/*  473 */       MilestoneFormDropDownMenu subconfigList = (MilestoneFormDropDownMenu)reportForm.getElement("subconfigurationList");
/*  474 */       if (subconfigList != null) {
/*  475 */         ArrayList subconfigListAl = subconfigList.getStringValues();
/*  476 */         if (subconfigListAl != null) {
/*      */           
/*  478 */           strSubconfiguration = new String[subconfigListAl.size()];
/*  479 */           strSubconfiguration = (String[])subconfigListAl.toArray(strSubconfiguration);
/*      */         } 
/*      */       } 
/*  482 */     } catch (Exception e) {
/*  483 */       e.printStackTrace();
/*  484 */       System.out.println("<<< Subconfiguration vector execption " + e.getMessage());
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  490 */     if (strSubconfiguration != null && 
/*  491 */       !strSubconfiguration[0].trim().equalsIgnoreCase("0") && 
/*  492 */       !strSubconfiguration[0].trim().equalsIgnoreCase("") && 
/*  493 */       !strSubconfiguration[0].trim().equalsIgnoreCase("all")) {
/*      */       
/*  495 */       boolean addOr = false;
/*  496 */       query.append(" AND (");
/*  497 */       for (int x = 0; x < strSubconfiguration.length; x++) {
/*  498 */         if (addOr)
/*  499 */           query.append(" OR "); 
/*  500 */         String txtvalue = strSubconfiguration[x];
/*  501 */         if (txtvalue.indexOf("[") != -1) {
/*      */           
/*  503 */           String txtconfigcode = txtvalue.substring(1, txtvalue.indexOf("]"));
/*  504 */           String txtsubconfigcode = txtvalue.substring(txtvalue.indexOf("]") + 1, txtvalue.length());
/*  505 */           query.append(" (header.[configuration] = '" + txtconfigcode.trim() + "' AND ");
/*  506 */           query.append(" header.[sub_configuration] = '" + txtsubconfigcode.trim() + "') ");
/*      */         } else {
/*      */           
/*  509 */           query.append(" header.[sub_configuration] = '" + txtvalue.trim() + "' ");
/*      */         } 
/*  511 */         addOr = true;
/*      */       } 
/*  513 */       query.append(") ");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     }
/*  521 */     else if (strConfiguration != null && !strConfiguration[0].equalsIgnoreCase("") && !strConfiguration[0].equalsIgnoreCase("0") && 
/*  522 */       !strConfiguration[0].equalsIgnoreCase("all")) {
/*      */       
/*  524 */       boolean addOr = false;
/*  525 */       query.append(" AND (");
/*  526 */       for (int x = 0; x < strConfiguration.length; x++) {
/*  527 */         if (addOr) {
/*  528 */           query.append(" OR ");
/*      */         }
/*  530 */         query.append(" header.[configuration] = '" + strConfiguration[x].trim() + "' ");
/*  531 */         addOr = true;
/*      */       } 
/*  533 */       query.append(") ");
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  540 */     String beginDate = "";
/*  541 */     beginDate = (reportForm.getStringValue("beginDate") != null && reportForm.getStringValue("beginDate").length() > 0) ? reportForm.getStringValue("beginDate") : "";
/*      */     
/*  543 */     String endDate = "";
/*  544 */     endDate = (reportForm.getStringValue("endDate") != null && reportForm.getStringValue("endDate").length() > 0) ? reportForm.getStringValue("endDate") : "";
/*      */     
/*  546 */     if (!beginDate.equalsIgnoreCase("") || !endDate.equalsIgnoreCase("")) {
/*  547 */       query.append(" AND (( header.status ='TBS' OR header.status ='ITW') OR (");
/*      */     }
/*      */     
/*  550 */     String streetDate = "street_date";
/*  551 */     if (reportname.equals("DigProdSch") || reportname.equals("DigProLSch")) {
/*  552 */       streetDate = "digital_rls_date";
/*      */     }
/*      */     
/*  555 */     String[] reportParms = new String[2];
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  560 */     if (strProductType.equals(Integer.toString(2))) {
/*      */       
/*  562 */       if (!beginDate.equalsIgnoreCase("")) {
/*  563 */         query.append("street_date >= '" + MilestoneHelper.escapeSingleQuotes(beginDate) + "'");
/*  564 */         reportParms[0] = MilestoneHelper.escapeSingleQuotes(beginDate);
/*      */       } 
/*  566 */       if (!endDate.equalsIgnoreCase("")) {
/*      */         
/*  568 */         if (!beginDate.equalsIgnoreCase("")) {
/*  569 */           query.append(" AND street_date <= '" + MilestoneHelper.escapeSingleQuotes(endDate) + "'");
/*      */         } else {
/*  571 */           query.append("street_date <= '" + MilestoneHelper.escapeSingleQuotes(endDate) + "'");
/*  572 */         }  reportParms[1] = MilestoneHelper.escapeSingleQuotes(endDate);
/*      */       } 
/*      */       
/*  575 */       if (!beginDate.equalsIgnoreCase("")) {
/*  576 */         query.append(" ) OR (digital_rls_date >= '" + MilestoneHelper.escapeSingleQuotes(beginDate) + "'");
/*  577 */         reportParms[0] = MilestoneHelper.escapeSingleQuotes(beginDate);
/*      */       } 
/*  579 */       if (!endDate.equalsIgnoreCase("")) {
/*  580 */         if (!beginDate.equalsIgnoreCase("")) {
/*  581 */           query.append(" AND digital_rls_date <= '" + MilestoneHelper.escapeSingleQuotes(endDate) + "'");
/*      */         } else {
/*  583 */           query.append(" ) OR (digital_rls_date <= '" + MilestoneHelper.escapeSingleQuotes(endDate) + "'");
/*  584 */         }  reportParms[1] = MilestoneHelper.escapeSingleQuotes(endDate);
/*      */       }
/*      */     
/*      */     } else {
/*      */       
/*  589 */       if (!beginDate.equalsIgnoreCase("")) {
/*  590 */         if (reportname.equals("McaCustImp")) {
/*  591 */           query.append("( " + streetDate + " >= '" + 
/*  592 */               MilestoneHelper.escapeSingleQuotes(beginDate) + "'");
/*      */         } else {
/*      */           
/*  595 */           query.append(String.valueOf(streetDate) + " >= '" + 
/*  596 */               MilestoneHelper.escapeSingleQuotes(beginDate) + "'");
/*      */         } 
/*  598 */         reportParms[0] = MilestoneHelper.escapeSingleQuotes(beginDate);
/*      */       } 
/*  600 */       if (!endDate.equalsIgnoreCase("")) {
/*  601 */         if (!beginDate.equalsIgnoreCase("")) {
/*  602 */           query.append(" AND " + streetDate + " <= '" + 
/*  603 */               MilestoneHelper.escapeSingleQuotes(endDate) + "'");
/*      */         } else {
/*  605 */           query.append(String.valueOf(streetDate) + " <= '" + 
/*  606 */               MilestoneHelper.escapeSingleQuotes(endDate) + "'");
/*  607 */         }  reportParms[1] = MilestoneHelper.escapeSingleQuotes(endDate);
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  612 */     if (reportname.equals("McaCustImp"))
/*  613 */     { if (!beginDate.equalsIgnoreCase("")) {
/*  614 */         query.append(") OR EXISTS( select impactDate from impactDates where selection_id = release_id AND (");
/*  615 */         query.append(" impactDate >= '" + MilestoneHelper.escapeSingleQuotes(beginDate) + "'");
/*      */       } 
/*  617 */       if (!endDate.equalsIgnoreCase(""))
/*      */       {
/*  619 */         if (!beginDate.equalsIgnoreCase("")) {
/*  620 */           query.append(" AND impactDate <= '" + MilestoneHelper.escapeSingleQuotes(endDate) + "'");
/*      */         } else {
/*  622 */           query.append(") OR EXISTS( select impactDate from impactDates where selection_id = release_id AND (");
/*  623 */           query.append(" impactDate <= '" + MilestoneHelper.escapeSingleQuotes(endDate) + "'");
/*      */         } 
/*      */       }
/*  626 */       if (!beginDate.equalsIgnoreCase("") || !endDate.equalsIgnoreCase("")) query.append("))))");
/*      */       
/*      */        }
/*      */     
/*  630 */     else if (!beginDate.equalsIgnoreCase("") || !endDate.equalsIgnoreCase("")) { query.append("))"); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  636 */     ReportSelectionsHelper.addStatusToSelect(reportForm, query);
/*      */ 
/*      */ 
/*      */     
/*  640 */     if (reportname.equals("eInitRel")) {
/*  641 */       String eInitRelSortInt = (reportForm.getStringValue("eInitRelList") != null) ? 
/*  642 */         reportForm.getStringValue("eInitRelList") : "0";
/*      */       
/*  644 */       if (eInitRelSortInt.equals("0"))
/*  645 */         query.append(") ORDER BY UPPER(artist), UPPER(title), upc"); 
/*  646 */       if (eInitRelSortInt.equals("1"))
/*  647 */         query.append(") ORDER BY impact_date, packaging, UPPER(artist), UPPER(title), upc"); 
/*  648 */       if (eInitRelSortInt.equals("2")) {
/*  649 */         query.append(") ORDER BY impact_date, UPPER(title), upc");
/*      */       }
/*      */     }
/*  652 */     else if (reportname.equals("ToDoList")) {
/*  653 */       query.append(") ORDER BY due_date, prefix ");
/*      */ 
/*      */     
/*      */     }
/*  657 */     else if (bScheduled || (!strUmlKey.equalsIgnoreCase("") && !strUmlKey.trim().equalsIgnoreCase("all") && 
/*  658 */       !report.getReportName().trim().equalsIgnoreCase("New Rel."))) {
/*      */       
/*  660 */       query.append(") ORDER BY UpperArtist, " + streetDate + 
/*  661 */           " , UpperTitle");
/*      */     }
/*  663 */     else if (reportname.equals("IDJProdAlt")) {
/*  664 */       query.append(") ORDER BY " + streetDate + " , UPPER(artist)");
/*      */     }
/*      */     else {
/*      */       
/*  668 */       query.append(") ORDER BY UPPER(artist), " + streetDate + 
/*  669 */           " , UPPER(title)");
/*      */     } 
/*      */ 
/*      */     
/*  673 */     System.out.println("\n\n**************\n\n");
/*  674 */     System.out.println(query.toString());
/*  675 */     System.out.println("\n\n**************\n\n");
/*      */     
/*  677 */     if (ReportingServices.execUsingReportServices(reportname, query, context, reportParms)) {
/*  678 */       return null;
/*      */     }
/*  680 */     System.out.println("\n\n****************\n\n");
/*  681 */     System.out.println(query.toString());
/*  682 */     System.out.println("\n\n****************\n\n");
/*  683 */     JdbcConnector connector = MilestoneHelper.getConnector(query.toString());
/*  684 */     connector.setForwardOnly(false);
/*  685 */     connector.runQuery();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  691 */     int totalCount = 0;
/*  692 */     int tenth = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  700 */     totalCount = connector.getRowCount();
/*      */ 
/*      */     
/*  703 */     tenth = totalCount / 5;
/*      */     
/*  705 */     if (tenth < 1) {
/*  706 */       tenth = 1;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/*  713 */       HttpServletResponse sresponse = context.getResponse();
/*  714 */       context.putDelivery("status", new String("start_gathering"));
/*  715 */       context.putDelivery("percent", new String("10"));
/*  716 */       if (!ReportingServices.usingReportServicesByContext(context))
/*      */       {
/*  718 */         context.includeJSP("status.jsp", "hiddenFrame");
/*  719 */         sresponse.setContentType("text/plain");
/*  720 */         sresponse.flushBuffer();
/*      */       }
/*      */     
/*  723 */     } catch (Exception exception) {}
/*      */ 
/*      */     
/*  726 */     int recordCount = 0;
/*  727 */     int count = 0;
/*      */ 
/*      */     
/*  730 */     while (connector.more()) {
/*      */ 
/*      */       
/*      */       try {
/*  734 */         if (count < recordCount / tenth) {
/*      */           
/*  736 */           count = recordCount / tenth;
/*  737 */           HttpServletResponse sresponse = context.getResponse();
/*  738 */           context.putDelivery("status", new String("start_gathering"));
/*  739 */           context.putDelivery("percent", new String(String.valueOf(count * 10)));
/*  740 */           if (!ReportingServices.usingReportServicesByContext(context)) {
/*      */             
/*  742 */             context.includeJSP("status.jsp", "hiddenFrame");
/*  743 */             sresponse.setContentType("text/plain");
/*  744 */             sresponse.flushBuffer();
/*      */           } 
/*      */         } 
/*      */         
/*  748 */         recordCount++;
/*      */         
/*  750 */         if (reportname.equals("eInitRel")) {
/*      */           
/*  752 */           selection = new Selection();
/*      */ 
/*      */           
/*  755 */           selection.setSelectionID(connector.getIntegerField("release_id"));
/*      */ 
/*      */           
/*  758 */           String impactDateString = connector.getFieldByName("impact_date");
/*  759 */           if (impactDateString != null) {
/*  760 */             selection.setImpactDate(MilestoneHelper.getDatabaseDate(impactDateString));
/*      */           }
/*      */           
/*  763 */           String streetDateString = connector.getFieldByName("street_date");
/*  764 */           if (streetDateString != null) {
/*  765 */             selection.setStreetDate(MilestoneHelper.getDatabaseDate(streetDateString));
/*      */           }
/*      */           
/*  768 */           selection.setArtist(connector.getField("artist", ""));
/*      */ 
/*      */           
/*  771 */           selection.setTitle(connector.getField("title", ""));
/*      */ 
/*      */           
/*  774 */           selection.setUpc(connector.getField("upc", ""));
/*      */ 
/*      */           
/*  777 */           selection.setSelectionPackaging(connector.getField("packaging", ""));
/*      */ 
/*      */           
/*  780 */           selection.setOtherContact(connector.getField("coordinator_contacts", ""));
/*      */ 
/*      */           
/*  783 */           selection.setProductCategory((ProductCategory)getLookupObject(connector.getField("product_line"), 
/*  784 */                 Cache.getProductCategories()));
/*      */           
/*  786 */           selection.setParentalGuidance(connector.getBoolean("parental_indicator"));
/*      */           
/*  788 */           selection.setSelectionTerritory(connector.getField("territory", ""));
/*      */           
/*  790 */           selection.setComments(connector.getField("comments", ""));
/*      */ 
/*      */           
/*  793 */           selection.setImprint(connector.getField("imprint", ""));
/*  794 */           selection.setReleaseFamilyId(connector.getIntegerField("release_family_id"));
/*      */         }
/*  796 */         else if (reportname.equals("ToDoList")) {
/*      */           
/*  798 */           selection = new Selection();
/*      */ 
/*      */           
/*  801 */           selection.setSelectionID(connector.getIntegerField("release_id"));
/*      */           
/*  803 */           selection.setTitle(connector.getField("title", ""));
/*      */ 
/*      */           
/*  806 */           selection.setTaskName(connector.getField("name", ""));
/*      */ 
/*      */           
/*  809 */           selection.setArtist(connector.getField("artist", ""));
/*      */ 
/*      */           
/*  812 */           selection.setDepartment(connector.getField("department", ""));
/*      */ 
/*      */           
/*  815 */           String completionDateString = connector.getFieldByName("completion_date");
/*  816 */           if (completionDateString != null) {
/*  817 */             selection.setCompletionDate(MilestoneHelper.getDatabaseDate(completionDateString));
/*      */           }
/*      */           
/*  820 */           selection.setArtistFirstName(connector.getField("artist_first_name", ""));
/*      */ 
/*      */           
/*  823 */           selection.setArtistLastName(connector.getField("artist_last_name", ""));
/*      */ 
/*      */           
/*  826 */           if (UserManager.getInstance().getUser(connector.getIntegerField("contact_id")) != null) {
/*  827 */             selection.setLabelContact(UserManager.getInstance().getUser(connector.getIntegerField("contact_id")));
/*      */           }
/*      */           
/*  830 */           String dueDateString = connector.getFieldByName("due_date");
/*  831 */           if (dueDateString != null) {
/*  832 */             selection.setDueDate(MilestoneHelper.getDatabaseDate(dueDateString));
/*      */           }
/*      */ 
/*      */           
/*  836 */           String streetDateString = connector.getFieldByName("street_date");
/*  837 */           if (streetDateString != null) {
/*  838 */             selection.setStreetDate(MilestoneHelper.getDatabaseDate(streetDateString));
/*      */           }
/*  840 */           selection.setUpc(connector.getField("upc", ""));
/*      */           
/*  842 */           selection.setSelectionConfig(
/*  843 */               getSelectionConfigObject(connector.getField("configuration"), 
/*  844 */                 Cache.getSelectionConfigs()));
/*      */ 
/*      */ 
/*      */           
/*  848 */           selection.setImprint(connector.getField("imprint", ""));
/*  849 */           selection.setReleaseFamilyId(connector.getIntegerField("release_family_id"));
/*      */ 
/*      */           
/*  852 */           selection.setSelectionSubConfig(getSelectionSubConfigObject(connector.getField("sub_configuration"), selection.getSelectionConfig()));
/*  853 */           selection.setPrefixID((PrefixCode)getLookupObject(connector.getField("prefix"), Cache.getPrefixCodes()));
/*  854 */           selection.setSelectionNo(connector.getField("selection_no"));
/*  855 */           selection.setSelectionStatus((SelectionStatus)getLookupObject(connector.getField("status"), 
/*  856 */                 Cache.getSelectionStatusList()));
/*      */         } else {
/*      */           
/*  859 */           selection = new Selection();
/*      */ 
/*      */           
/*  862 */           selection.setSelectionID(connector.getIntegerField("release_id"));
/*      */           
/*  864 */           selection.setTitle(connector.getField("title", ""));
/*      */ 
/*      */           
/*  867 */           selection.setArtistFirstName(connector.getField("artist_first_name", ""));
/*      */ 
/*      */           
/*  870 */           selection.setArtistLastName(connector.getField("artist_last_name", ""));
/*      */ 
/*      */           
/*  873 */           selection.setFlArtist(connector.getField("fl_artist", ""));
/*      */ 
/*      */           
/*  876 */           String streetDateString = connector.getFieldByName("street_date");
/*  877 */           if (streetDateString != null) {
/*  878 */             selection.setStreetDate(MilestoneHelper.getDatabaseDate(streetDateString));
/*      */           }
/*      */           
/*  881 */           if (reportname.equals("DigProdSch") || reportname.equals("DigProLSch")) {
/*  882 */             String digitalStreetDateString = connector.getFieldByName("digital_rls_date");
/*  883 */             if (digitalStreetDateString != null) {
/*  884 */               selection.setDigitalRlsDate(MilestoneHelper.getDatabaseDate(digitalStreetDateString));
/*      */             }
/*      */           } 
/*  887 */           selection.setUpc(connector.getField("upc", ""));
/*      */           
/*  889 */           selection.setImprint(connector.getField("imprint", ""));
/*  890 */           selection.setReleaseFamilyId(connector.getIntegerField("release_family_id"));
/*      */           
/*  892 */           selection.setSelectionConfig(
/*  893 */               getSelectionConfigObject(connector.getField("configuration"), 
/*  894 */                 Cache.getSelectionConfigs()));
/*      */ 
/*      */ 
/*      */           
/*  898 */           selection.setSelectionSubConfig(getSelectionSubConfigObject(connector.getField("sub_configuration"), selection.getSelectionConfig()));
/*  899 */           selection.setPrefixID((PrefixCode)getLookupObject(connector.getField("prefix"), Cache.getPrefixCodes()));
/*  900 */           selection.setSelectionNo(connector.getField("selection_no"));
/*  901 */           selection.setSelectionStatus((SelectionStatus)getLookupObject(connector.getField("status"), 
/*  902 */                 Cache.getSelectionStatusList()));
/*      */         } 
/*      */ 
/*      */         
/*  906 */         precache.add(selection);
/*  907 */         selection = null;
/*  908 */         connector.next();
/*      */       
/*      */       }
/*  911 */       catch (Exception exception) {}
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  916 */     connector.close();
/*  917 */     company = null;
/*      */     
/*  919 */     return precache;
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
/*      */   protected static Vector filterSelectionsWithReportCriteria(Vector selections, Context context, String reportname, int reportProductType) {
/*  935 */     Vector selectionsForReport = new Vector();
/*  936 */     Form reportForm = (Form)context.getSessionValue("reportForm");
/*      */     
/*  938 */     if (selections == null || selections.size() == 0) {
/*  939 */       return selectionsForReport;
/*      */     }
/*      */     
/*  942 */     Calendar beginStDate = (reportForm.getStringValue("beginDate") != null && reportForm.getStringValue("beginDate").length() > 0) ? MilestoneHelper.getDate(reportForm.getStringValue("beginDate")) : null;
/*  943 */     if (beginStDate == null) {
/*      */       
/*  945 */       beginStDate = Calendar.getInstance();
/*  946 */       beginStDate.setTime(new Date(0L));
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  952 */     beginStDate.set(11, 0);
/*  953 */     beginStDate.set(12, 0);
/*  954 */     beginStDate.set(13, 0);
/*  955 */     beginStDate.set(14, 0);
/*      */     
/*  957 */     Calendar endStDate = (reportForm.getStringValue("endDate") != null && reportForm.getStringValue("endDate").length() > 0) ? MilestoneHelper.getDate(reportForm.getStringValue("endDate")) : null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  964 */     if (endStDate != null) {
/*      */       
/*  966 */       endStDate.set(11, 23);
/*  967 */       endStDate.set(12, 59);
/*  968 */       endStDate.set(13, 59);
/*  969 */       endStDate.set(14, 999);
/*      */     } 
/*      */     
/*  972 */     String strDistribution = (reportForm.getStringValue("distribution") != null) ? reportForm.getStringValue("distribution") : "";
/*  973 */     String strLabelContact = (reportForm.getStringValue("contact") != null) ? reportForm.getStringValue("contact") : "";
/*  974 */     String strUmlContact = (reportForm.getStringValue("umlContact") != null) ? reportForm.getStringValue("umlContact") : "";
/*  975 */     String strFuture2 = (reportForm.getStringValue("Future2") != null) ? reportForm.getStringValue("Future2") : "";
/*      */     
/*  977 */     String strReleaseType = (reportForm.getStringValue("releaseType") != null) ? reportForm.getStringValue("releaseType") : "";
/*  978 */     String strArtist = (reportForm.getStringValue("artist") != null) ? reportForm.getStringValue("artist") : "";
/*      */ 
/*      */ 
/*      */     
/*  982 */     Iterator formIterator = reportForm.getElements();
/*  983 */     Vector distCoIDs = new Vector();
/*  984 */     while (formIterator.hasNext()) {
/*      */ 
/*      */       
/*  987 */       FormElement field = (FormElement)formIterator.next();
/*  988 */       String fieldName = field.getName();
/*  989 */       if (fieldName.startsWith("distCo")) {
/*      */         
/*  991 */         FormCheckBox fCheck = (FormCheckBox)field;
/*  992 */         if (fCheck.isChecked()) {
/*  993 */           distCoIDs.add(fieldName.substring(6, fieldName.length()));
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/*  998 */     String[] strFamily = ReportSelectionsHelper.getMultiSelectionListValues("family", reportForm);
/*      */ 
/*      */     
/* 1001 */     String[] strEnvironment = ReportSelectionsHelper.getMultiSelectionListValues("environment", reportForm);
/* 1002 */     String[] strCompany = ReportSelectionsHelper.getMultiSelectionListValues("company", reportForm);
/* 1003 */     String[] strLabel = ReportSelectionsHelper.getMultiSelectionListValues("Label", reportForm);
/*      */ 
/*      */     
/* 1006 */     String strProductType = (reportForm.getStringValue("ProductType") != null) ? reportForm.getStringValue("ProductType") : "";
/* 1007 */     if (strProductType.equals("")) {
/*      */       
/* 1009 */       strProductType = String.valueOf(reportProductType);
/*      */     }
/* 1011 */     else if (strProductType.equals("Physical")) {
/* 1012 */       strProductType = String.valueOf(0);
/* 1013 */     } else if (strProductType.equals("Digital")) {
/* 1014 */       strProductType = String.valueOf(1);
/* 1015 */     } else if (strProductType.equals("Both")) {
/* 1016 */       strProductType = String.valueOf(2);
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1028 */     String[] strConfiguration = null;
/*      */     try {
/* 1030 */       MilestoneFormDropDownMenu configList = (MilestoneFormDropDownMenu)reportForm.getElement("configurationList");
/* 1031 */       if (configList != null) {
/* 1032 */         ArrayList configListAl = configList.getStringValues();
/* 1033 */         if (configListAl != null) {
/*      */           
/* 1035 */           strConfiguration = new String[configListAl.size()];
/* 1036 */           strConfiguration = (String[])configListAl.toArray(strConfiguration);
/*      */         } 
/*      */       } 
/* 1039 */     } catch (Exception e) {
/* 1040 */       e.printStackTrace();
/* 1041 */       System.out.println("<<< Configuration vector execption " + e.getMessage());
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1048 */     String[] strSubconfiguration = null;
/*      */     try {
/* 1050 */       MilestoneFormDropDownMenu subconfigList = (MilestoneFormDropDownMenu)reportForm.getElement("subconfigurationList");
/* 1051 */       if (subconfigList != null) {
/* 1052 */         ArrayList subconfigListAl = subconfigList.getStringValues();
/* 1053 */         if (subconfigListAl != null) {
/*      */           
/* 1055 */           strSubconfiguration = new String[subconfigListAl.size()];
/* 1056 */           strSubconfiguration = (String[])subconfigListAl.toArray(strSubconfiguration);
/*      */         } 
/*      */       } 
/* 1059 */     } catch (Exception e) {
/* 1060 */       e.printStackTrace();
/* 1061 */       System.out.println("<<< Subconfiguration vector execption " + e.getMessage());
/*      */     } 
/*      */ 
/*      */     
/* 1065 */     boolean bParent = (reportForm.getElement("ParentsOnly") != null) ? ((FormCheckBox)reportForm.getElement("ParentsOnly")).isChecked() : 0;
/*      */     
/* 1067 */     int totalCount = selections.size();
/*      */     
/* 1069 */     int tenth = 0;
/*      */     
/* 1071 */     tenth = totalCount / 5;
/*      */     
/* 1073 */     if (tenth < 1) {
/* 1074 */       tenth = 1;
/*      */     }
/*      */     
/*      */     try {
/* 1078 */       HttpServletResponse sresponse = context.getResponse();
/* 1079 */       context.putDelivery("status", new String("start_gathering"));
/* 1080 */       context.putDelivery("percent", new String("10"));
/* 1081 */       if (!ReportingServices.usingReportServicesByContext(context))
/*      */       {
/* 1083 */         context.includeJSP("status.jsp", "hiddenFrame");
/* 1084 */         sresponse.setContentType("text/plain");
/* 1085 */         sresponse.flushBuffer();
/*      */       }
/*      */     
/* 1088 */     } catch (Exception exception) {}
/*      */ 
/*      */ 
/*      */     
/* 1092 */     int recordCount = 0;
/* 1093 */     int count = 0;
/*      */ 
/*      */ 
/*      */     
/* 1097 */     for (int i = 0; i < selections.size(); i++) {
/*      */ 
/*      */       
/*      */       try {
/* 1101 */         if (count < recordCount / tenth) {
/*      */           
/* 1103 */           count = recordCount / tenth;
/* 1104 */           HttpServletResponse sresponse = context.getResponse();
/* 1105 */           context.putDelivery("status", new String("start_gathering"));
/* 1106 */           context.putDelivery("percent", new String(String.valueOf(count * 10 + 50)));
/* 1107 */           if (!ReportingServices.usingReportServicesByContext(context)) {
/*      */             
/* 1109 */             context.includeJSP("status.jsp", "hiddenFrame");
/* 1110 */             sresponse.setContentType("text/plain");
/* 1111 */             sresponse.flushBuffer();
/*      */           } 
/*      */         } 
/*      */         
/* 1115 */         recordCount++;
/*      */ 
/*      */         
/* 1118 */         Selection temp_sel = (Selection)selections.elementAt(i);
/* 1119 */         Selection sel = SelectionManager.getInstance().getSelectionHeader(temp_sel.getSelectionID());
/*      */ 
/*      */         
/* 1122 */         if (reportname.equals("ToDoList")) {
/* 1123 */           sel.setDueDate(temp_sel.getDueDate());
/* 1124 */           sel.setDepartment(temp_sel.getDepartment());
/* 1125 */           sel.setTaskName(temp_sel.getTaskName());
/* 1126 */           sel.setCompletionDate(temp_sel.getCompletionDate());
/*      */         } 
/*      */ 
/*      */         
/* 1130 */         String status = "";
/* 1131 */         if (sel.getSelectionStatus() != null) {
/* 1132 */           status = sel.getSelectionStatus().getName();
/*      */         }
/* 1134 */         if (!status.equalsIgnoreCase("TBS") && !status.equalsIgnoreCase("In the works"))
/*      */         {
/*      */           
/* 1137 */           if (!reportname.equals("McaCustImp"))
/*      */           {
/*      */             
/* 1140 */             if (strProductType.equals(String.valueOf(1))) {
/* 1141 */               Calendar digitalStreetDate = sel.getDigitalRlsDate();
/* 1142 */               if (digitalStreetDate != null && ((beginStDate != null && digitalStreetDate.before(beginStDate)) || (endStDate != null && digitalStreetDate.after(endStDate))))
/*      */                 continue; 
/* 1144 */             } else if (strProductType.equals(String.valueOf(2))) {
/*      */               Calendar streetDate;
/*      */               
/* 1147 */               if (sel.isDigital) {
/* 1148 */                 streetDate = sel.getDigitalRlsDate();
/*      */               } else {
/* 1150 */                 streetDate = sel.getStreetDate();
/*      */               } 
/* 1152 */               if (streetDate != null && ((beginStDate != null && streetDate.before(beginStDate)) || (endStDate != null && streetDate.after(endStDate))))
/*      */                 continue; 
/*      */             } else {
/* 1155 */               Calendar physicalStreetDate = sel.getStreetDate();
/* 1156 */               if (physicalStreetDate != null && ((beginStDate != null && physicalStreetDate.before(beginStDate)) || (endStDate != null && physicalStreetDate.after(endStDate)))) {
/*      */                 continue;
/*      */               }
/*      */             } 
/*      */           }
/*      */         }
/*      */ 
/*      */         
/* 1164 */         if (!strDistribution.equalsIgnoreCase("") && !strDistribution.equalsIgnoreCase("All")) {
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 1169 */           String selDistribution = "";
/*      */           
/* 1171 */           if (sel.getLabel().getDistribution() == 1) {
/*      */             
/* 1173 */             selDistribution = "East";
/*      */           }
/* 1175 */           else if (sel.getLabel().getDistribution() == 0) {
/*      */             
/* 1177 */             selDistribution = "West";
/*      */           } 
/*      */           
/* 1180 */           if (!selDistribution.equalsIgnoreCase(strDistribution.trim())) {
/*      */             continue;
/*      */           }
/*      */         } 
/*      */ 
/*      */         
/* 1186 */         Label currentLabel = sel.getLabel();
/* 1187 */         int selectionsDistCompany = MilestoneHelper_2.getLabelDistCoId(currentLabel.getStructureID());
/*      */         
/* 1189 */         if (selectionsDistCompany == -1) {
/* 1190 */           selectionsDistCompany = Integer.parseInt("1");
/*      */         }
/*      */ 
/*      */         
/* 1194 */         if (distCoIDs.size() > 0 && !distCoIDs.contains(Integer.toString(selectionsDistCompany))) {
/*      */           continue;
/*      */         }
/*      */ 
/*      */ 
/*      */         
/* 1200 */         if (!strLabelContact.trim().equalsIgnoreCase("0") && !strLabelContact.trim().equalsIgnoreCase("")) {
/*      */           
/* 1202 */           if (sel.getLabelContact() == null) {
/*      */             continue;
/*      */           }
/* 1205 */           String selLabelContactName = Integer.toString(sel.getLabelContact().getUserId());
/*      */           
/* 1207 */           if (!selLabelContactName.equalsIgnoreCase(strLabelContact)) {
/*      */             continue;
/*      */           }
/*      */         } 
/*      */         
/* 1212 */         if (!strUmlContact.trim().equalsIgnoreCase("0") && !strUmlContact.trim().equalsIgnoreCase("")) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 1218 */           SelectionManager.getInstance().getSelectionManufacturingSubDetail(sel);
/*      */           
/* 1220 */           if (sel.getUmlContact() == null) {
/*      */             continue;
/*      */           }
/*      */ 
/*      */           
/* 1225 */           String selUmlContactName = Integer.toString(sel.getUmlContact().getUserId());
/*      */           
/* 1227 */           if (!selUmlContactName.equalsIgnoreCase(strUmlContact)) {
/*      */             continue;
/*      */           }
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1236 */         if (!strReleaseType.equalsIgnoreCase("") && !strReleaseType.equalsIgnoreCase("All")) {
/*      */           
/* 1238 */           String releaseType = "";
/* 1239 */           if (sel.getReleaseType() != null) {
/*      */             
/* 1241 */             releaseType = (sel.getReleaseType().getName() != null) ? sel.getReleaseType().getName() : "";
/* 1242 */             if (!releaseType.equalsIgnoreCase(strReleaseType)) {
/*      */               continue;
/*      */             }
/*      */           } else {
/*      */             continue;
/*      */           } 
/*      */         } 
/*      */ 
/*      */ 
/*      */         
/* 1252 */         if (!ReportSelectionsHelper.isStatusFound(reportForm, sel)) {
/*      */           continue;
/*      */         }
/*      */ 
/*      */         
/* 1257 */         if (!strArtist.trim().equals("") && sel.getArtist() != null) {
/*      */           
/* 1259 */           String artistUpperCase = sel.getArtist().trim().toUpperCase();
/*      */           
/* 1261 */           if (!artistUpperCase.startsWith(strArtist.trim().toUpperCase())) {
/*      */             continue;
/*      */           }
/*      */         } 
/*      */ 
/*      */         
/* 1267 */         if (!ReportSelectionsHelper.isMultSelectFound(Integer.toString(sel.getReleaseFamilyId()), strFamily)) {
/*      */           continue;
/*      */         }
/*      */         
/* 1271 */         if (sel.getEnvironment() == null || 
/* 1272 */           !ReportSelectionsHelper.isMultSelectFound(Integer.toString(sel.getEnvironment().getStructureID()), strEnvironment)) {
/*      */           continue;
/*      */         }
/*      */         
/* 1276 */         if (sel.getCompany() == null || 
/* 1277 */           !ReportSelectionsHelper.isMultSelectFound(Integer.toString(sel.getCompany().getStructureID()), strCompany)) {
/*      */           continue;
/*      */         }
/*      */ 
/*      */         
/* 1282 */         if (sel.getLabel() == null || 
/* 1283 */           !ReportSelectionsHelper.isMultSelectFound(Integer.toString(sel.getLabel().getStructureID()), strLabel)) {
/*      */           continue;
/*      */         }
/*      */ 
/*      */ 
/*      */         
/* 1289 */         if (bParent) {
/*      */           
/* 1291 */           String prefixId = "";
/*      */           
/* 1293 */           if (sel.getPrefixID() != null) {
/* 1294 */             prefixId = SelectionManager.getLookupObjectValue(sel.getPrefixID());
/*      */           }
/* 1296 */           if (!sel.getTitleID().equalsIgnoreCase(String.valueOf(prefixId) + sel.getSelectionNo())) {
/*      */             continue;
/*      */           }
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1304 */         if (strSubconfiguration != null && 
/* 1305 */           !strSubconfiguration[0].trim().equalsIgnoreCase("0") && 
/* 1306 */           !strSubconfiguration[0].trim().equalsIgnoreCase("") && 
/* 1307 */           !strSubconfiguration[0].trim().equalsIgnoreCase("all")) {
/*      */ 
/*      */           
/* 1310 */           if (sel.getSelectionSubConfig() != null && sel.getSelectionConfig() != null) {
/* 1311 */             String selConfigurationName = sel.getSelectionConfig().getSelectionConfigurationAbbreviation();
/* 1312 */             String selSubconfigurationName = sel.getSelectionSubConfig().getSelectionSubConfigurationAbbreviation();
/*      */             
/* 1314 */             boolean subconfigFnd = false;
/* 1315 */             for (int x = 0; x < strSubconfiguration.length; x++) {
/* 1316 */               String txtvalue = strSubconfiguration[x];
/* 1317 */               if (txtvalue.indexOf("[") != -1) {
/*      */                 
/* 1319 */                 String txtconfigcode = txtvalue.substring(1, 
/* 1320 */                     txtvalue.indexOf("]"));
/* 1321 */                 String txtsubconfigcode = txtvalue.substring(txtvalue.indexOf(
/* 1322 */                       "]") + 1, txtvalue.length());
/* 1323 */                 if (selConfigurationName.equalsIgnoreCase(txtconfigcode) && 
/* 1324 */                   selSubconfigurationName.equalsIgnoreCase(txtsubconfigcode)) {
/* 1325 */                   subconfigFnd = true;
/*      */ 
/*      */ 
/*      */                   
/*      */                   break;
/*      */                 } 
/* 1331 */               } else if (selSubconfigurationName.equalsIgnoreCase(txtvalue)) {
/* 1332 */                 subconfigFnd = true;
/*      */                 
/*      */                 break;
/*      */               } 
/*      */             } 
/* 1337 */             if (!subconfigFnd)
/*      */             {
/*      */               continue;
/*      */ 
/*      */ 
/*      */             
/*      */             }
/*      */ 
/*      */           
/*      */           }
/*      */ 
/*      */ 
/*      */         
/*      */         }
/* 1351 */         else if (strConfiguration != null && !strConfiguration[0].trim().equalsIgnoreCase("0") && !strConfiguration[0].trim().equalsIgnoreCase("") && 
/* 1352 */           !strConfiguration[0].trim().equalsIgnoreCase("all")) {
/*      */           
/* 1354 */           if (sel.getSelectionConfig() != null) {
/* 1355 */             String selConfigurationName = sel.getSelectionConfig().getSelectionConfigurationAbbreviation();
/*      */             
/* 1357 */             boolean configFnd = false;
/* 1358 */             for (int x = 0; x < strConfiguration.length; x++) {
/* 1359 */               if (selConfigurationName.equalsIgnoreCase(strConfiguration[x])) {
/* 1360 */                 configFnd = true;
/*      */                 break;
/*      */               } 
/*      */             } 
/* 1364 */             if (!configFnd) {
/*      */               continue;
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
/* 1378 */         selectionsForReport.addElement(sel);
/*      */         
/*      */         continue;
/* 1381 */       } catch (Exception exception) {
/*      */         continue;
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 1387 */     if (reportname.equals("Prod.Stat.")) {
/* 1388 */       Vector precache = new Vector();
/*      */       
/* 1390 */       for (int y = 0; y < selectionsForReport.size(); y++) {
/*      */         
/* 1392 */         Selection currentSelection = (Selection)selectionsForReport.get(y);
/*      */         
/* 1394 */         Schedule temp = ScheduleManager.getInstance().getSchedule(currentSelection.getSelectionID());
/* 1395 */         currentSelection.setSchedule(temp);
/*      */         
/* 1397 */         if (temp != null && temp.getTasks().size() > 0)
/*      */         {
/*      */           
/* 1400 */           precache.add(currentSelection);
/*      */         }
/*      */       } 
/* 1403 */       return precache;
/* 1404 */     }  if (reportname.equals("Com Rel") || reportname.equals("VerveComm")) {
/*      */       
/* 1406 */       Vector commercialSelectionsForReport = new Vector();
/* 1407 */       for (int z = 0; z < selectionsForReport.size(); z++) {
/*      */         
/* 1409 */         Selection sel = (Selection)selectionsForReport.elementAt(z);
/*      */ 
/*      */         
/* 1412 */         String releaseType = (sel.getReleaseType().getName() != null) ? sel.getReleaseType().getName() : "";
/*      */         
/* 1414 */         if (releaseType.toUpperCase().indexOf("COMMERCIAL") != -1)
/* 1415 */           commercialSelectionsForReport.addElement(sel); 
/*      */       } 
/* 1417 */       return commercialSelectionsForReport;
/*      */     } 
/* 1419 */     return selectionsForReport;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static SelectionConfiguration getSelectionConfigObject(String abbreviation, Vector configs) {
/* 1426 */     for (int j = 0; j < configs.size(); j++) {
/*      */       
/* 1428 */       SelectionConfiguration selectionConfiguration = (SelectionConfiguration)configs.get(j);
/*      */       
/* 1430 */       if (selectionConfiguration.getSelectionConfigurationAbbreviation().equalsIgnoreCase(abbreviation))
/*      */       {
/* 1432 */         return selectionConfiguration;
/*      */       }
/*      */     } 
/*      */     
/* 1436 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static SelectionSubConfiguration getSelectionSubConfigObject(String abbreviation, SelectionConfiguration config) {
/* 1444 */     Vector subConfigs = config.getSubConfigurations();
/*      */     
/* 1446 */     for (int j = 0; j < subConfigs.size(); j++) {
/*      */       
/* 1448 */       SelectionSubConfiguration subConfig = (SelectionSubConfiguration)subConfigs.get(j);
/*      */       
/* 1450 */       if (subConfig.getSelectionSubConfigurationAbbreviation().equalsIgnoreCase(abbreviation))
/*      */       {
/* 1452 */         return subConfig;
/*      */       }
/*      */     } 
/*      */     
/* 1456 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static LookupObject getLookupObject(String abbreviation, Vector lookupVector) {
/* 1464 */     for (int j = 0; j < lookupVector.size(); j++) {
/*      */       
/* 1466 */       LookupObject lookupObject = (LookupObject)lookupVector.get(j);
/*      */       
/* 1468 */       if (lookupObject.getAbbreviation().equalsIgnoreCase(abbreviation))
/*      */       {
/* 1470 */         return lookupObject;
/*      */       }
/*      */     } 
/*      */     
/* 1474 */     return null;
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
/*      */   public static Vector getFilteredSelectionsForReport(Context context, int reportProductType) {
/* 1489 */     User user = (User)context.getSessionValue("user");
/* 1490 */     int userId = 0;
/* 1491 */     if (user != null) {
/* 1492 */       userId = user.getUserId();
/*      */     }
/*      */     
/* 1495 */     Form reportForm = (Form)context.getSessionValue("reportForm");
/* 1496 */     boolean bScheduled = (reportForm.getElement("ScheduledReleases") != null) ? ((FormCheckBox)reportForm.getElement("ScheduledReleases")).isChecked() : 0;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1502 */     Vector companies = MilestoneHelper.getUserCompanies(context);
/* 1503 */     Company company = null;
/* 1504 */     Vector precache = new Vector();
/*      */     
/* 1506 */     StringBuffer query = new StringBuffer();
/*      */     
/* 1508 */     query.append("SELECT header.release_id, header.title_id, header.selection_no, header.prefix,   pfm.units_per_set, detail.due_date AS taskDue, detail.comments AS taskComments,   detail.owner AS taskOwner, detail.completion_date AS taskComplete,   detail.status AS taskStatus, detail.day_of_week AS taskDayOfWeek,   detail.weeks_to_release AS taskWeeksToRelease, detail.key_task_indicator AS taskKey,    detail.vendor AS taskVendor, detail.authorization_name AS taskAuthName,   detail.authorization_date AS taskAuthDate, detail.last_updated_ck, header.project_no,   header.title, header.artist_first_name, header.artist_last_name,   header.artist, header.artist_first_name + ' ' + header.artist_last_name AS fl_artist ,   header.side_a_title, header.side_b_title, header.product_line, header.release_type,   header.configuration, header.sub_configuration, header.UPC, header.price_code, header.genre,   header.family_id, header.release_family_id, header.environment_id, header.company_id, header.division_id, header.label_id, header.street_date,   header.international_date, header.coordinator_contacts, header.contact_id, header.status,   header.hold_indicator, header.hold_reason, header.comments, header.special_pkg_indicator,   header.pd_indicator, header.packaging, header.impact_date, header.last_updated_on,   header.last_updated_by, header.entered_on, mfg.uml_id, mfg.plant, mfg.distribution,   header.imprint,   mfg.order_qty, mfg.complete_qty, mfg.order_comments, Task.task_id, Task.department,   Task.name, Task.abbrev_id  FROM  Release_Header header  with (nolock)  LEFT JOIN Release_Subdetail mfg  with (nolock)  ON header.release_id = mfg.release_id ");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1531 */     if (bScheduled) {
/*      */       
/* 1533 */       query.append(" INNER JOIN release_detail detail with (nolock) ON header.[release_id] = detail.[release_id]");
/*      */     } else {
/*      */       
/* 1536 */       query.append(" LEFT JOIN release_detail detail with (nolock) ON header.[release_id] = detail.[release_id]");
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1543 */     String strProdGroupCode = (reportForm.getStringValue("productionGroupCode") != null) ? reportForm.getStringValue("productionGroupCode") : "";
/* 1544 */     if (!strProdGroupCode.equalsIgnoreCase("") && !strProdGroupCode.equalsIgnoreCase("All")) {
/* 1545 */       query.append(" INNER JOIN ArchimedesLabels atlasLabels with (nolock) ON header.[Archimedes_id] = atlasLabels.[ArchimedesID] ");
/*      */     }
/* 1547 */     query.append("  LEFT JOIN Task  with (nolock)  ON detail.task_id = Task.task_id   LEFT JOIN Pfm_Selection pfm with (nolock)  ON header.release_id = pfm.release_id    WHERE ");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1558 */     String strLabelContact = (reportForm.getStringValue("contact") != null) ? reportForm.getStringValue("contact") : "";
/* 1559 */     String strUmlContact = (reportForm.getStringValue("umlContact") != null) ? reportForm.getStringValue("umlContact") : "";
/* 1560 */     String strReleaseType = (reportForm.getStringValue("releaseType") != null) ? reportForm.getStringValue("releaseType") : "";
/* 1561 */     String strArtist = (reportForm.getStringValue("artist") != null) ? reportForm.getStringValue("artist") : "";
/* 1562 */     String[] strFamily = ReportSelectionsHelper.getMultiSelectionListValues("family", reportForm);
/*      */     
/* 1564 */     String[] strEnvironment = ReportSelectionsHelper.getMultiSelectionListValues("environment", reportForm);
/* 1565 */     String[] strCompany = ReportSelectionsHelper.getMultiSelectionListValues("company", reportForm);
/* 1566 */     String[] strLabel = ReportSelectionsHelper.getMultiSelectionListValues("Label", reportForm);
/*      */ 
/*      */ 
/*      */     
/* 1570 */     String strProductType = (reportForm.getStringValue("ProductType") != null) ? reportForm.getStringValue("ProductType") : "";
/* 1571 */     if (strProductType.equals("")) {
/*      */       
/* 1573 */       strProductType = String.valueOf(reportProductType);
/*      */     
/*      */     }
/* 1576 */     else if (strProductType.equals("Physical")) {
/* 1577 */       strProductType = String.valueOf(0);
/* 1578 */     } else if (strProductType.equals("Digital")) {
/* 1579 */       strProductType = String.valueOf(1);
/* 1580 */     } else if (strProductType.equals("Both")) {
/* 1581 */       strProductType = String.valueOf(2);
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1591 */     String[] strConfiguration = null;
/*      */     try {
/* 1593 */       MilestoneFormDropDownMenu configList = (MilestoneFormDropDownMenu)reportForm.getElement("configurationList");
/* 1594 */       if (configList != null) {
/* 1595 */         ArrayList configListAl = configList.getStringValues();
/* 1596 */         if (configListAl != null) {
/*      */           
/* 1598 */           strConfiguration = new String[configListAl.size()];
/* 1599 */           strConfiguration = (String[])configListAl.toArray(strConfiguration);
/*      */         } 
/*      */       } 
/* 1602 */     } catch (Exception e) {
/* 1603 */       e.printStackTrace();
/* 1604 */       System.out.println("<<< Configuration vector execption " + e.getMessage());
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1611 */     String[] strSubconfiguration = null;
/*      */     try {
/* 1613 */       MilestoneFormDropDownMenu subconfigList = (MilestoneFormDropDownMenu)reportForm.getElement("subconfigurationList");
/* 1614 */       if (subconfigList != null) {
/* 1615 */         ArrayList subconfigListAl = subconfigList.getStringValues();
/* 1616 */         if (subconfigListAl != null) {
/*      */           
/* 1618 */           strSubconfiguration = new String[subconfigListAl.size()];
/* 1619 */           strSubconfiguration = (String[])subconfigListAl.toArray(strSubconfiguration);
/*      */         } 
/*      */       } 
/* 1622 */     } catch (Exception e) {
/* 1623 */       e.printStackTrace();
/* 1624 */       System.out.println("<<< Subconfiguration vector execption " + e.getMessage());
/*      */     } 
/*      */ 
/*      */     
/* 1628 */     boolean bParent = (reportForm.getElement("ParentsOnly") != null) ? ((FormCheckBox)reportForm.getElement("ParentsOnly")).isChecked() : 0;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1638 */     ReportSelectionsHelper.addMultSelectionToStringBuffer(query, "header.environment_id", strEnvironment, true, "environment", reportForm, true, false);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1645 */     ReportSelectionsHelper.addMultSelectionToStringBuffer(query, "header.company_id", strCompany, true, "company", reportForm, false, true);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1651 */     ReportSelectionsHelper.addReleasingFamilyLabelFamilyMultSelect("family", context, query, reportForm);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1657 */     ReportSelectionsHelper.addMultSelectionToStringBuffer(query, "header.[label_id]", strLabel, true, "Label", reportForm, false, true);
/*      */ 
/*      */ 
/*      */     
/* 1661 */     if (strProductType.equals(Integer.toString(1))) {
/* 1662 */       query.append(" AND digital_flag = 1 ");
/* 1663 */     } else if (strProductType.equals(Integer.toString(0))) {
/* 1664 */       query.append(" AND digital_flag = 0 ");
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1671 */     if (!strArtist.equalsIgnoreCase("")) {
/* 1672 */       query.append(" AND header.[artist] LIKE '%" + MilestoneHelper.escapeSingleQuotes(strArtist) + "%'");
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 1677 */     ReportSelectionsHelper.addStatusToSelect(reportForm, query);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1682 */     if (!strReleaseType.equalsIgnoreCase("") && !strReleaseType.equalsIgnoreCase("all"))
/*      */     {
/* 1684 */       if (strReleaseType.equalsIgnoreCase("commercial")) {
/* 1685 */         query.append(" AND header.[release_type] ='CO'");
/*      */       } else {
/* 1687 */         query.append(" AND header.[release_type] ='PR'");
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 1693 */     if (!strProdGroupCode.equalsIgnoreCase("") && !strProdGroupCode.equalsIgnoreCase("All")) {
/* 1694 */       query.append(" AND atlasLabels.[ProductionGroupCode] ='" + strProdGroupCode + "' ");
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 1699 */     if (!strLabelContact.equalsIgnoreCase("") && !strLabelContact.equalsIgnoreCase("0")) {
/* 1700 */       query.append(" AND header.[contact_id] =" + strLabelContact);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1725 */     if (strSubconfiguration != null && 
/* 1726 */       !strSubconfiguration[0].trim().equalsIgnoreCase("0") && 
/* 1727 */       !strSubconfiguration[0].trim().equalsIgnoreCase("") && 
/* 1728 */       !strSubconfiguration[0].trim().equalsIgnoreCase("all")) {
/*      */       
/* 1730 */       boolean addOr = false;
/* 1731 */       query.append(" AND (");
/* 1732 */       for (int x = 0; x < strSubconfiguration.length; x++) {
/* 1733 */         if (addOr)
/* 1734 */           query.append(" OR "); 
/* 1735 */         String txtvalue = strSubconfiguration[x];
/* 1736 */         if (txtvalue.indexOf("[") != -1) {
/*      */           
/* 1738 */           String txtconfigcode = txtvalue.substring(1, txtvalue.indexOf("]"));
/* 1739 */           String txtsubconfigcode = txtvalue.substring(txtvalue.indexOf("]") + 1, txtvalue.length());
/* 1740 */           query.append(" (header.[configuration] = '" + txtconfigcode.trim() + "' AND ");
/* 1741 */           query.append(" header.[sub_configuration] = '" + txtsubconfigcode.trim() + "') ");
/*      */         } else {
/*      */           
/* 1744 */           query.append(" header.[sub_configuration] = '" + txtvalue.trim() + "' ");
/* 1745 */         }  addOr = true;
/*      */       } 
/* 1747 */       query.append(") ");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     }
/* 1755 */     else if (strConfiguration != null && !strConfiguration[0].equalsIgnoreCase("") && !strConfiguration[0].equalsIgnoreCase("0") && 
/* 1756 */       !strConfiguration[0].equalsIgnoreCase("all")) {
/*      */       
/* 1758 */       boolean addOr = false;
/* 1759 */       query.append(" AND (");
/* 1760 */       for (int x = 0; x < strConfiguration.length; x++) {
/* 1761 */         if (addOr) {
/* 1762 */           query.append(" OR ");
/*      */         }
/* 1764 */         query.append(" header.[configuration] = '" + strConfiguration[x].trim() + "' ");
/* 1765 */         addOr = true;
/*      */       } 
/* 1767 */       query.append(") ");
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1776 */     if (!strUmlContact.equalsIgnoreCase("") && !strUmlContact.equalsIgnoreCase("0")) {
/* 1777 */       query.append(" AND mfg.[uml_id] =" + strUmlContact);
/*      */     }
/* 1779 */     String beginDate = "";
/* 1780 */     beginDate = (reportForm.getStringValue("beginDate") != null && reportForm.getStringValue("beginDate").length() > 0) ? reportForm.getStringValue("beginDate") : "";
/*      */     
/* 1782 */     String endDate = "";
/* 1783 */     endDate = (reportForm.getStringValue("endDate") != null && reportForm.getStringValue("endDate").length() > 0) ? reportForm.getStringValue("endDate") : "";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1790 */     if (!beginDate.equalsIgnoreCase("") || !endDate.equalsIgnoreCase("")) {
/* 1791 */       query.append(" AND ((header.[status] ='TBS' OR header.[status] ='ITW') OR (");
/*      */     }
/* 1793 */     if (!beginDate.equalsIgnoreCase("")) {
/* 1794 */       query.append(" header.[street_date] >= '" + MilestoneHelper.escapeSingleQuotes(beginDate) + "'");
/*      */     }
/* 1796 */     if (!endDate.equalsIgnoreCase(""))
/*      */     {
/* 1798 */       if (!beginDate.equalsIgnoreCase("")) {
/* 1799 */         query.append(" AND header.[street_date] <= '" + MilestoneHelper.escapeSingleQuotes(endDate) + "'");
/*      */       } else {
/* 1801 */         query.append(" header.[street_date] <= '" + MilestoneHelper.escapeSingleQuotes(endDate) + "'");
/*      */       } 
/*      */     }
/* 1804 */     if (!beginDate.equalsIgnoreCase("") || !endDate.equalsIgnoreCase("")) query.append("))");
/*      */     
/* 1806 */     query.append(" ORDER BY header.[release_id], UPPER(header.[artist]), header.[street_date], UPPER(header.[title])");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1812 */     JdbcConnector connector = MilestoneHelper.getConnector(query.toString());
/*      */ 
/*      */     
/* 1815 */     connector.runQuery();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1825 */     Vector selections = null;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1830 */     System.out.println("\n\n************\n\n");
/* 1831 */     System.out.println(query.toString());
/* 1832 */     System.out.println("\n\n************\n\n");
/*      */     
/* 1834 */     int totalCount = 0;
/* 1835 */     int tenth = 0;
/*      */ 
/*      */     
/* 1838 */     JdbcConnector connectorCount = MilestoneHelper.getConnector(query.toString());
/* 1839 */     connectorCount.runQuery();
/*      */     
/* 1841 */     while (connectorCount.more()) {
/*      */       
/* 1843 */       totalCount++;
/* 1844 */       connectorCount.next();
/*      */     } 
/* 1846 */     connectorCount.close();
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1851 */     tenth = totalCount / 10;
/*      */     
/* 1853 */     if (tenth < 1) {
/* 1854 */       tenth = 1;
/*      */     }
/*      */     
/*      */     try {
/* 1858 */       HttpServletResponse sresponse = context.getResponse();
/* 1859 */       context.putDelivery("status", new String("start_gathering"));
/* 1860 */       context.putDelivery("percent", new String("10"));
/* 1861 */       if (!ReportingServices.usingReportServicesByContext(context))
/*      */       {
/* 1863 */         context.includeJSP("status.jsp", "hiddenFrame");
/* 1864 */         sresponse.setContentType("text/plain");
/* 1865 */         sresponse.flushBuffer();
/*      */       }
/*      */     
/* 1868 */     } catch (Exception exception) {}
/*      */ 
/*      */ 
/*      */     
/* 1872 */     int recordCount = 0;
/* 1873 */     int count = 0;
/*      */ 
/*      */ 
/*      */     
/* 1877 */     selections = new Vector();
/*      */     
/* 1879 */     while (connector.more()) {
/*      */ 
/*      */       
/*      */       try {
/*      */ 
/*      */ 
/*      */         
/* 1886 */         if (count < recordCount / tenth) {
/*      */           
/* 1888 */           count = recordCount / tenth;
/* 1889 */           HttpServletResponse sresponse = context.getResponse();
/* 1890 */           context.putDelivery("status", new String("start_gathering"));
/* 1891 */           context.putDelivery("percent", new String(String.valueOf(count * 10)));
/* 1892 */           if (!ReportingServices.usingReportServicesByContext(context)) {
/*      */             
/* 1894 */             context.includeJSP("status.jsp", "hiddenFrame");
/* 1895 */             sresponse.setContentType("text/plain");
/* 1896 */             sresponse.flushBuffer();
/*      */           } 
/*      */         } 
/*      */         
/* 1900 */         recordCount++;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1911 */         if (bParent) {
/*      */           
/* 1913 */           String prefixId = "";
/* 1914 */           String tmpTitleId = connector.getField("title_id", "");
/* 1915 */           String tmpSelectionNo = connector.getField("selection_no", "");
/*      */           
/* 1917 */           prefixId = SelectionManager.getLookupObjectValue((PrefixCode)getLookupObject(connector.getField("prefix", ""), Cache.getPrefixCodes()));
/*      */           
/* 1919 */           if (!tmpTitleId.equalsIgnoreCase(String.valueOf(prefixId) + tmpSelectionNo)) {
/*      */             
/* 1921 */             connector.next();
/*      */             
/*      */             continue;
/*      */           } 
/*      */         } 
/*      */         
/* 1927 */         int numberOfUnits = 0;
/*      */         
/*      */         try {
/* 1930 */           numberOfUnits = connector.getIntegerField("pfm.[units_per_set]");
/*      */         }
/* 1932 */         catch (Exception exception) {}
/*      */ 
/*      */         
/* 1935 */         Selection selection = null;
/*      */         
/* 1937 */         selection = new Selection();
/* 1938 */         selection.setSelectionID(connector.getIntegerField("release_id"));
/*      */         
/* 1940 */         String selectionNo = "";
/* 1941 */         if (connector.getFieldByName("selection_no") != null)
/* 1942 */           selectionNo = connector.getFieldByName("selection_no"); 
/* 1943 */         selection.setSelectionNo(selectionNo);
/*      */         
/* 1945 */         selection.setProjectID(connector.getField("project_no", ""));
/*      */         
/* 1947 */         String titleId = "";
/* 1948 */         if (connector.getFieldByName("title_id") != null)
/* 1949 */           titleId = connector.getFieldByName("title_id"); 
/* 1950 */         selection.setTitleID(titleId);
/*      */         
/* 1952 */         selection.setTitle(connector.getField("title", ""));
/* 1953 */         selection.setArtistFirstName(connector.getField("artist_first_name", ""));
/* 1954 */         selection.setArtistLastName(connector.getField("artist_last_name", ""));
/* 1955 */         selection.setArtist(connector.getField("artist", ""));
/* 1956 */         selection.setFlArtist(connector.getField("fl_artist", ""));
/*      */         
/* 1958 */         selection.setASide(connector.getField("side_a_title", ""));
/* 1959 */         selection.setBSide(connector.getField("side_b_title", ""));
/*      */         
/* 1961 */         selection.setProductCategory((ProductCategory)getLookupObject(connector.getField("product_line"), 
/* 1962 */               Cache.getProductCategories()));
/* 1963 */         selection.setReleaseType((ReleaseType)getLookupObject(connector.getField("release_type"), 
/* 1964 */               Cache.getReleaseTypes()));
/*      */         
/* 1966 */         selection.setSelectionConfig(getSelectionConfigObject(connector.getField("configuration"), 
/* 1967 */               Cache.getSelectionConfigs()));
/* 1968 */         selection.setSelectionSubConfig(getSelectionSubConfigObject(connector.getField("sub_configuration"), 
/* 1969 */               selection.getSelectionConfig()));
/*      */         
/* 1971 */         selection.setUpc(connector.getField("upc", ""));
/*      */         
/* 1973 */         String sellCodeString = connector.getFieldByName("price_code");
/* 1974 */         if (sellCodeString != null)
/*      */         {
/* 1976 */           selection.setPriceCode(SelectionManager.getInstance().getPriceCode(sellCodeString));
/*      */         }
/*      */         
/* 1979 */         selection.setSellCode(sellCodeString);
/*      */         
/* 1981 */         selection.setGenre((Genre)getLookupObject(connector.getField("genre"), 
/* 1982 */               Cache.getMusicTypes()));
/* 1983 */         selection.setFamily((Family)MilestoneHelper.getStructureObject(connector.getIntegerField("family_id")));
/* 1984 */         selection.setCompany((Company)MilestoneHelper.getStructureObject(connector.getIntegerField("company_id")));
/* 1985 */         selection.setEnvironment((Environment)MilestoneHelper.getStructureObject(connector.getIntegerField("environment_id")));
/* 1986 */         selection.setDivision((Division)MilestoneHelper.getStructureObject(connector.getIntegerField("division_id")));
/* 1987 */         selection.setLabel((Label)MilestoneHelper.getStructureObject(connector.getIntegerField("label_id")));
/*      */ 
/*      */         
/* 1990 */         selection.setImprint(connector.getField("imprint", ""));
/*      */ 
/*      */         
/* 1993 */         selection.setReleaseFamilyId(connector.getIntegerField("release_family_id"));
/*      */         
/* 1995 */         String streetDateString = connector.getFieldByName("street_date");
/* 1996 */         if (streetDateString != null) {
/* 1997 */           selection.setStreetDate(MilestoneHelper.getDatabaseDate(streetDateString));
/*      */         }
/* 1999 */         String internationalDateString = connector.getFieldByName("international_date");
/* 2000 */         if (internationalDateString != null) {
/* 2001 */           selection.setInternationalDate(MilestoneHelper.getDatabaseDate(internationalDateString));
/*      */         }
/* 2003 */         selection.setOtherContact(connector.getField("coordinator_contacts", ""));
/*      */         
/* 2005 */         if (UserManager.getInstance().getUser(connector.getIntegerField("contact_id")) != null) {
/* 2006 */           selection.setLabelContact(UserManager.getInstance().getUser(connector.getIntegerField("contact_id")));
/*      */         }
/* 2008 */         selection.setSelectionStatus((SelectionStatus)getLookupObject(connector.getField("status"), 
/* 2009 */               Cache.getSelectionStatusList()));
/* 2010 */         selection.setHoldSelection(connector.getBoolean("hold_indicator"));
/*      */         
/* 2012 */         selection.setHoldReason(connector.getField("hold_reason", ""));
/* 2013 */         selection.setComments(connector.getField("comments", ""));
/* 2014 */         selection.setSpecialPackaging(connector.getBoolean("special_pkg_indicator"));
/* 2015 */         selection.setNumberOfUnits(numberOfUnits);
/* 2016 */         selection.setPressAndDistribution(connector.getBoolean("pd_indicator"));
/*      */         
/* 2018 */         selection.setPrefixID((PrefixCode)getLookupObject(connector.getField("prefix"), Cache.getPrefixCodes()));
/* 2019 */         selection.setSelectionPackaging(connector.getField("packaging", ""));
/*      */         
/* 2021 */         String impactDateString = connector.getFieldByName("impact_date");
/* 2022 */         if (impactDateString != null) {
/* 2023 */           selection.setImpactDate(MilestoneHelper.getDatabaseDate(impactDateString));
/*      */         }
/* 2025 */         String lastUpdateDateString = connector.getFieldByName("last_updated_on");
/* 2026 */         if (lastUpdateDateString != null) {
/* 2027 */           selection.setLastUpdateDate(MilestoneHelper.getDatabaseDate(lastUpdateDateString));
/*      */         }
/* 2029 */         selection.setLastUpdatingUser(UserManager.getInstance().getUser(connector.getIntegerField("last_updated_by")));
/*      */ 
/*      */         
/* 2032 */         String originDateString = connector.getFieldByName("entered_on");
/* 2033 */         if (originDateString != null) {
/* 2034 */           selection.setOriginDate(MilestoneHelper.getDatabaseDate(originDateString));
/*      */         }
/*      */ 
/*      */ 
/*      */         
/* 2039 */         User umlContact = UserManager.getInstance().getUser(connector.getIntegerField("mfg.[uml_id]"));
/* 2040 */         selection.setUmlContact(umlContact);
/* 2041 */         selection.setPlant(getLookupObject(connector.getField("mfg.[plant]"), Cache.getVendors()));
/* 2042 */         selection.setDistribution(getLookupObject(connector.getField("mfg.[distribution]"), Cache.getDistributionCodes()));
/* 2043 */         selection.setPoQuantity(connector.getIntegerField("mfg.[order_qty]"));
/* 2044 */         selection.setCompletedQuantity(connector.getIntegerField("mfg.[complete_qty]"));
/* 2045 */         selection.setManufacturingComments(connector.getField("mfg.[order_comments]", ""));
/* 2046 */         selection.setPrice(connector.getFloat("mfg.[list_price]"));
/*      */         
/* 2048 */         selection.setFullSelection(true);
/*      */         
/* 2050 */         String strDistribution = (reportForm.getStringValue("distribution") != null) ? reportForm.getStringValue("distribution") : "";
/* 2051 */         String strFuture2 = (reportForm.getStringValue("Future2") != null) ? reportForm.getStringValue("Future2") : "";
/*      */ 
/*      */         
/* 2054 */         if (!strDistribution.equalsIgnoreCase("") && !strDistribution.equalsIgnoreCase("All")) {
/*      */           
/* 2056 */           String selDistribution = SelectionManager.getLookupObjectValue(selection.getDistribution());
/*      */           
/* 2058 */           if (!selDistribution.equalsIgnoreCase(strDistribution.trim())) {
/*      */             continue;
/*      */           }
/*      */         } 
/* 2062 */         int nextReleaseId = connector.getIntegerField("release_id");
/* 2063 */         Schedule schedule = new Schedule();
/* 2064 */         schedule.setSelectionID(nextReleaseId);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 2071 */         Vector precacheSchedule = new Vector();
/* 2072 */         while (connector.more() && connector.getIntegerField("release_id") == nextReleaseId) {
/*      */           
/* 2074 */           ScheduledTask scheduledTask = null;
/*      */           
/* 2076 */           scheduledTask = new ScheduledTask();
/*      */ 
/*      */           
/* 2079 */           scheduledTask.setReleaseID(connector.getIntegerField("release_Id"));
/*      */ 
/*      */           
/* 2082 */           scheduledTask.setTaskID(connector.getIntegerField("task_id"));
/*      */ 
/*      */           
/* 2085 */           scheduledTask.setOwner((Family)MilestoneHelper.getStructureObject(connector.getIntegerField("taskOwner")));
/*      */ 
/*      */           
/* 2088 */           String dueDateString = connector.getField("taskDue", "");
/* 2089 */           if (dueDateString.length() > 0) {
/* 2090 */             scheduledTask.setDueDate(MilestoneHelper.getDatabaseDate(dueDateString));
/*      */           }
/*      */           
/* 2093 */           String completionDateString = connector.getField("taskComplete", "");
/* 2094 */           if (completionDateString.length() > 0) {
/* 2095 */             scheduledTask.setCompletionDate(MilestoneHelper.getDatabaseDate(completionDateString));
/*      */           }
/*      */           
/* 2098 */           String taskStatus = connector.getField("taskStatus", "");
/* 2099 */           if (taskStatus.length() > 1) {
/* 2100 */             scheduledTask.setScheduledTaskStatus(taskStatus);
/*      */           }
/*      */           
/* 2103 */           int day = connector.getIntegerField("taskDayOfWeek");
/* 2104 */           if (day > 0) {
/* 2105 */             scheduledTask.setDayOfTheWeek(new Day(connector.getIntegerField("taskDayOfWeek")));
/*      */           }
/*      */           
/* 2108 */           int weeks = connector.getIntegerField("taskWeeksToRelease");
/* 2109 */           if (weeks > 0) {
/* 2110 */             scheduledTask.setWeeksToRelease(connector.getIntegerField("taskWeeksToRelease"));
/*      */           }
/*      */           
/* 2113 */           String vendorString = connector.getField("taskVendor", "");
/* 2114 */           if (vendorString.length() > 0) {
/* 2115 */             scheduledTask.setVendor(vendorString);
/*      */           }
/*      */           
/* 2118 */           int taskAbbrevID = connector.getIntegerField("abbrev_id");
/* 2119 */           scheduledTask.setTaskAbbreviationID(taskAbbrevID);
/*      */ 
/*      */           
/* 2122 */           int taskID = connector.getIntegerField("task_id");
/* 2123 */           scheduledTask.setScheduledTaskID(taskID);
/*      */ 
/*      */           
/* 2126 */           String taskDept = connector.getField("department", "");
/* 2127 */           scheduledTask.setDepartment(taskDept);
/*      */ 
/*      */           
/* 2130 */           scheduledTask.setKeyTask(connector.getBoolean("taskKey"));
/*      */ 
/*      */           
/* 2133 */           scheduledTask.setAuthorizationName(connector.getField("taskAuthName", ""));
/*      */ 
/*      */           
/* 2136 */           String authDateString = connector.getField("taskAuthDate", "");
/* 2137 */           if (authDateString.length() > 0) {
/* 2138 */             scheduledTask.setAuthorizationDate(MilestoneHelper.getDatabaseDate(authDateString));
/*      */           }
/*      */           
/* 2141 */           String comments = connector.getField("taskComments", "");
/* 2142 */           scheduledTask.setComments(comments);
/*      */ 
/*      */           
/* 2145 */           scheduledTask.setName(connector.getField("name", ""));
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 2150 */           precacheSchedule.add(scheduledTask);
/*      */           
/* 2152 */           scheduledTask = null;
/*      */           
/* 2154 */           if (connector.more()) {
/*      */             
/* 2156 */             connector.next();
/* 2157 */             recordCount++;
/*      */             
/*      */             continue;
/*      */           } 
/*      */           
/*      */           break;
/*      */         } 
/* 2164 */         schedule.setTasks(precacheSchedule);
/*      */         
/* 2166 */         selection.setSchedule(schedule);
/*      */         
/* 2168 */         selections.add(selection);
/*      */       
/*      */       }
/* 2171 */       catch (Exception exception) {}
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2178 */     connector.close();
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2183 */     return selections;
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
/*      */   public static Vector getTaskDueSelectionsForReport(Context context, int reportProductType) {
/* 2199 */     User user = (User)context.getSessionValue("user");
/* 2200 */     int userId = 0;
/* 2201 */     if (user != null)
/* 2202 */       userId = user.getUserId(); 
/* 2203 */     Form reportForm = (Form)context.getSessionValue("reportForm");
/*      */     
/* 2205 */     String beginDate = "";
/* 2206 */     beginDate = (reportForm.getStringValue("beginDate") != null && reportForm.getStringValue("beginDate").length() > 0) ? reportForm.getStringValue("beginDate") : "";
/*      */     
/* 2208 */     String endDate = "";
/* 2209 */     endDate = (reportForm.getStringValue("endDate") != null && reportForm.getStringValue("endDate").length() > 0) ? reportForm.getStringValue("endDate") : "";
/*      */     
/* 2211 */     String beginDueDate = "";
/* 2212 */     beginDueDate = (reportForm.getStringValue("beginDueDate") != null && reportForm.getStringValue("beginDueDate").length() > 0) ? reportForm.getStringValue("beginDueDate") : "";
/*      */     
/* 2214 */     String endDueDate = "";
/* 2215 */     endDueDate = (reportForm.getStringValue("endDueDate") != null && reportForm.getStringValue("endDueDate").length() > 0) ? reportForm.getStringValue("endDueDate") : "";
/*      */     
/* 2217 */     StringBuffer dueDateQuery = new StringBuffer(200);
/*      */     
/* 2219 */     if (!beginDueDate.equalsIgnoreCase("")) {
/* 2220 */       dueDateQuery.append("AND due_date >= '" + MilestoneHelper.escapeSingleQuotes(beginDueDate) + "'");
/*      */     }
/* 2222 */     if (!endDueDate.equalsIgnoreCase(""))
/*      */     {
/* 2224 */       if (!beginDueDate.equalsIgnoreCase("")) {
/* 2225 */         dueDateQuery.append(" AND due_date <= '" + MilestoneHelper.escapeSingleQuotes(endDueDate) + "'");
/*      */       } else {
/* 2227 */         dueDateQuery.append(" due_date <= '" + MilestoneHelper.escapeSingleQuotes(endDueDate) + "'");
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2234 */     Vector companies = MilestoneHelper.getUserCompanies(context);
/* 2235 */     Company company = null;
/* 2236 */     Vector precache = new Vector();
/* 2237 */     StringBuffer query = new StringBuffer();
/*      */ 
/*      */     
/* 2240 */     query.append("SELECT header.release_id, header.title_id, header.selection_no, header.prefix,   pfm.units_per_set, detail.due_date AS taskDue, detail.comments AS taskComments,   detail.owner AS taskOwner, detail.completion_date AS taskComplete,   detail.status AS taskStatus, detail.day_of_week AS taskDayOfWeek,   detail.weeks_to_release AS taskWeeksToRelease, detail.key_task_indicator AS taskKey,    detail.vendor AS taskVendor, detail.authorization_name AS taskAuthName,   detail.authorization_date AS taskAuthDate, detail.last_updated_ck, header.project_no,   header.title, header.artist_first_name, header.artist_last_name, header.artist,   header.side_a_title, header.side_b_title, header.product_line, header.release_type,   header.configuration, header.sub_configuration, header.UPC, header.price_code, header.genre,   header.family_id, header.release_family_id, header_environment_id, header.company_id, header.division_id, header.label_id, header.street_date,   header.international_date, header.coordinator_contacts, header.contact_id, header.status,   header.hold_indicator, header.hold_reason, header.comments, header.special_pkg_indicator,   header.pd_indicator, header.packaging, header.impact_date, header.last_updated_on,   header.last_updated_by, header.entered_on, mfg.uml_id, mfg.plant, mfg.distribution,   header.imprint,   mfg.order_qty, mfg.complete_qty, mfg.order_comments, Task.task_id, Task.department,   Task.name, Task.abbrev_id  FROM vi_release_header header with (nolock) LEFT JOIN vi_release_subdetail mfg with (nolock) ON header.[release_id] = mfg.[release_id] LEFT JOIN vi_release_detail detail with (nolock) ON header.[release_id] = detail.[release_id] " + 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 2263 */         dueDateQuery.toString() + 
/* 2264 */         " INNER JOIN vi_Task task with (nolock)" + 
/* 2265 */         " ON detail.[task_id] = task.[task_id]" + 
/* 2266 */         " LEFT JOIN vi_pfm_selection pfm with (nolock)" + 
/* 2267 */         " ON header.[release_id] = pfm.[release_id]" + 
/* 2268 */         " WHERE ");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2275 */     String strLabelContact = (reportForm.getStringValue("contact") != null) ? reportForm.getStringValue("contact") : "";
/* 2276 */     String strUmlContact = (reportForm.getStringValue("umlContact") != null) ? reportForm.getStringValue("umlContact") : "";
/* 2277 */     String strReleaseType = (reportForm.getStringValue("releaseType") != null) ? reportForm.getStringValue("releaseType") : "";
/* 2278 */     String strArtist = (reportForm.getStringValue("artist") != null) ? reportForm.getStringValue("artist") : "";
/* 2279 */     String[] strFamily = ReportSelectionsHelper.getMultiSelectionListValues("family", reportForm);
/* 2280 */     String[] strEnvironment = ReportSelectionsHelper.getMultiSelectionListValues("environment", reportForm);
/* 2281 */     String[] strCompany = ReportSelectionsHelper.getMultiSelectionListValues("company", reportForm);
/* 2282 */     String[] strLabel = ReportSelectionsHelper.getMultiSelectionListValues("Label", reportForm);
/*      */ 
/*      */     
/* 2285 */     String strProductType = (reportForm.getStringValue("ProductType") != null) ? reportForm.getStringValue("ProductType") : "";
/* 2286 */     if (strProductType.equals("")) {
/*      */       
/* 2288 */       strProductType = String.valueOf(reportProductType);
/*      */     
/*      */     }
/* 2291 */     else if (strProductType.equals("Physical")) {
/* 2292 */       strProductType = String.valueOf(0);
/* 2293 */     } else if (strProductType.equals("Digital")) {
/* 2294 */       strProductType = String.valueOf(1);
/* 2295 */     } else if (strProductType.equals("Both")) {
/* 2296 */       strProductType = String.valueOf(2);
/*      */     } 
/*      */ 
/*      */     
/* 2300 */     boolean bParent = (reportForm.getElement("ParentsOnly") != null) ? ((FormCheckBox)reportForm.getElement("ParentsOnly")).isChecked() : 0;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2310 */     ReportSelectionsHelper.addMultSelectionToStringBuffer(query, "header.environment_id", strEnvironment, true, "environment", reportForm, true, false);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2317 */     ReportSelectionsHelper.addMultSelectionToStringBuffer(query, "header.company_id", strCompany, true, "company", reportForm, false, true);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2324 */     ReportSelectionsHelper.addReleasingFamilyLabelFamilyMultSelect("family", context, query, reportForm);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2330 */     ReportSelectionsHelper.addMultSelectionToStringBuffer(query, "header.[label_id]", strLabel, true, "Label", reportForm, false, true);
/*      */ 
/*      */     
/* 2333 */     if (strProductType.equals(Integer.toString(1))) {
/* 2334 */       query.append(" AND digital_flag = 1 ");
/* 2335 */     } else if (strProductType.equals(Integer.toString(0))) {
/* 2336 */       query.append(" AND digital_flag = 0 ");
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2343 */     if (!strArtist.equalsIgnoreCase("")) {
/* 2344 */       query.append(" AND header.[artist] LIKE '%" + MilestoneHelper.escapeSingleQuotes(strArtist) + "%'");
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 2349 */     ReportSelectionsHelper.addStatusToSelect(reportForm, query);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2355 */     if (!strReleaseType.equalsIgnoreCase("") && !strReleaseType.equalsIgnoreCase("all"))
/*      */     {
/* 2357 */       if (strReleaseType.equalsIgnoreCase("commercial")) {
/* 2358 */         query.append(" AND header.[release_type] ='CO'");
/*      */       } else {
/* 2360 */         query.append(" AND header.[release_type] ='PR'");
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 2366 */     if (!strLabelContact.equalsIgnoreCase("") && !strLabelContact.equalsIgnoreCase("0")) {
/* 2367 */       query.append(" AND header.[contact_id] =" + strLabelContact);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 2372 */     if (!strUmlContact.equalsIgnoreCase("") && !strUmlContact.equalsIgnoreCase("0")) {
/* 2373 */       query.append(" AND mfg.[uml_id] =" + strUmlContact);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2381 */     if (!beginDate.equalsIgnoreCase("") || !endDate.equalsIgnoreCase("")) {
/* 2382 */       query.append(" AND ((header.[status] ='TBS' OR header.[status] ='ITW') OR (");
/*      */     }
/* 2384 */     if (!beginDate.equalsIgnoreCase("")) {
/* 2385 */       query.append(" street_date >= '" + MilestoneHelper.escapeSingleQuotes(beginDate) + "'");
/*      */     }
/* 2387 */     if (!endDate.equalsIgnoreCase(""))
/*      */     {
/* 2389 */       if (!beginDate.equalsIgnoreCase("")) {
/* 2390 */         query.append(" AND street_date <= '" + MilestoneHelper.escapeSingleQuotes(endDate) + "'");
/*      */       } else {
/* 2392 */         query.append(" street_date <= '" + MilestoneHelper.escapeSingleQuotes(endDate) + "'");
/*      */       } 
/*      */     }
/* 2395 */     if (!beginDate.equalsIgnoreCase("") || !endDate.equalsIgnoreCase("")) query.append("))");
/*      */     
/* 2397 */     query.append(" ORDER BY header.[release_id], UPPER(header.[artist]), header.[street_date], UPPER(header.[title])");
/*      */ 
/*      */     
/* 2400 */     JdbcConnector connector = MilestoneHelper.getConnector(query.toString());
/*      */     
/* 2402 */     connector.setForwardOnly(false);
/*      */ 
/*      */     
/* 2405 */     connector.runQuery();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2415 */     Vector selections = null;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2420 */     int totalCount = 0;
/* 2421 */     int tenth = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2436 */     totalCount = connector.getRowCount();
/*      */     
/* 2438 */     tenth = totalCount / 10;
/*      */     
/* 2440 */     if (tenth < 1) {
/* 2441 */       tenth = 1;
/*      */     }
/*      */     
/*      */     try {
/* 2445 */       HttpServletResponse sresponse = context.getResponse();
/* 2446 */       context.putDelivery("status", new String("start_gathering"));
/* 2447 */       context.putDelivery("percent", new String("10"));
/* 2448 */       if (!ReportingServices.usingReportServicesByContext(context))
/*      */       {
/* 2450 */         context.includeJSP("status.jsp", "hiddenFrame");
/* 2451 */         sresponse.setContentType("text/plain");
/* 2452 */         sresponse.flushBuffer();
/*      */       }
/*      */     
/* 2455 */     } catch (Exception exception) {}
/*      */ 
/*      */ 
/*      */     
/* 2459 */     int recordCount = 0;
/* 2460 */     int count = 0;
/*      */ 
/*      */ 
/*      */     
/* 2464 */     selections = new Vector();
/*      */     
/* 2466 */     while (connector.more()) {
/*      */ 
/*      */       
/*      */       try {
/*      */ 
/*      */ 
/*      */         
/* 2473 */         if (count < recordCount / tenth) {
/*      */           
/* 2475 */           count = recordCount / tenth;
/* 2476 */           HttpServletResponse sresponse = context.getResponse();
/* 2477 */           context.putDelivery("status", new String("start_gathering"));
/* 2478 */           context.putDelivery("percent", new String(String.valueOf(count * 10)));
/* 2479 */           if (!ReportingServices.usingReportServicesByContext(context)) {
/*      */             
/* 2481 */             context.includeJSP("status.jsp", "hiddenFrame");
/* 2482 */             sresponse.setContentType("text/plain");
/* 2483 */             sresponse.flushBuffer();
/*      */           } 
/*      */         } 
/*      */         
/* 2487 */         recordCount++;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 2498 */         if (bParent) {
/*      */           
/* 2500 */           String prefixId = "";
/* 2501 */           String tmpTitleId = connector.getField("title_id", "");
/* 2502 */           String tmpSelectionNo = connector.getField("selection_no", "");
/*      */           
/* 2504 */           prefixId = SelectionManager.getLookupObjectValue((PrefixCode)getLookupObject(connector.getField("prefix", ""), Cache.getPrefixCodes()));
/*      */           
/* 2506 */           if (!tmpTitleId.equalsIgnoreCase(String.valueOf(prefixId) + tmpSelectionNo)) {
/*      */             
/* 2508 */             connector.next();
/*      */             
/*      */             continue;
/*      */           } 
/*      */         } 
/*      */         
/* 2514 */         int numberOfUnits = 0;
/*      */         
/*      */         try {
/* 2517 */           numberOfUnits = connector.getIntegerField("pfm.[units_per_set]");
/*      */         }
/* 2519 */         catch (Exception exception) {}
/*      */ 
/*      */         
/* 2522 */         Selection selection = null;
/*      */         
/* 2524 */         selection = new Selection();
/* 2525 */         selection.setSelectionID(connector.getIntegerField("release_id"));
/*      */         
/* 2527 */         String selectionNo = "";
/* 2528 */         if (connector.getFieldByName("selection_no") != null)
/* 2529 */           selectionNo = connector.getFieldByName("selection_no"); 
/* 2530 */         selection.setSelectionNo(selectionNo);
/*      */         
/* 2532 */         selection.setProjectID(connector.getField("project_no", ""));
/*      */         
/* 2534 */         String titleId = "";
/* 2535 */         if (connector.getFieldByName("title_id") != null)
/* 2536 */           titleId = connector.getFieldByName("title_id"); 
/* 2537 */         selection.setTitleID(titleId);
/*      */         
/* 2539 */         selection.setTitle(connector.getField("title", ""));
/* 2540 */         selection.setArtistFirstName(connector.getField("artist_first_name", ""));
/* 2541 */         selection.setArtistLastName(connector.getField("artist_last_name", ""));
/* 2542 */         selection.setArtist(connector.getField("artist", ""));
/* 2543 */         selection.setASide(connector.getField("side_a_title", ""));
/* 2544 */         selection.setBSide(connector.getField("side_b_title", ""));
/*      */         
/* 2546 */         selection.setProductCategory((ProductCategory)getLookupObject(connector.getField("product_line"), 
/* 2547 */               Cache.getProductCategories()));
/* 2548 */         selection.setReleaseType((ReleaseType)getLookupObject(connector.getField("release_type"), 
/* 2549 */               Cache.getReleaseTypes()));
/*      */         
/* 2551 */         selection.setSelectionConfig(getSelectionConfigObject(connector.getField("configuration"), 
/* 2552 */               Cache.getSelectionConfigs()));
/* 2553 */         selection.setSelectionSubConfig(getSelectionSubConfigObject(connector.getField("sub_configuration"), 
/* 2554 */               selection.getSelectionConfig()));
/*      */         
/* 2556 */         selection.setUpc(connector.getField("upc", ""));
/*      */         
/* 2558 */         String sellCodeString = connector.getFieldByName("price_code");
/* 2559 */         if (sellCodeString != null)
/*      */         {
/* 2561 */           selection.setPriceCode(SelectionManager.getInstance().getPriceCode(sellCodeString));
/*      */         }
/*      */         
/* 2564 */         selection.setSellCode(sellCodeString);
/*      */         
/* 2566 */         selection.setGenre((Genre)getLookupObject(connector.getField("genre"), 
/* 2567 */               Cache.getMusicTypes()));
/* 2568 */         selection.setFamily((Family)MilestoneHelper.getStructureObject(connector.getIntegerField("family_id")));
/* 2569 */         selection.setCompany((Company)MilestoneHelper.getStructureObject(connector.getIntegerField("company_id")));
/* 2570 */         selection.setDivision((Division)MilestoneHelper.getStructureObject(connector.getIntegerField("division_id")));
/* 2571 */         selection.setEnvironment((Environment)MilestoneHelper.getStructureObject(connector.getIntegerField("environment_id")));
/* 2572 */         selection.setLabel((Label)MilestoneHelper.getStructureObject(connector.getIntegerField("label_id")));
/*      */ 
/*      */         
/* 2575 */         selection.setImprint(connector.getField("imprint", ""));
/*      */ 
/*      */         
/* 2578 */         selection.setReleaseFamilyId(connector.getIntegerField("release_family_id"));
/*      */         
/* 2580 */         String streetDateString = connector.getFieldByName("street_date");
/* 2581 */         if (streetDateString != null) {
/* 2582 */           selection.setStreetDate(MilestoneHelper.getDatabaseDate(streetDateString));
/*      */         }
/* 2584 */         String internationalDateString = connector.getFieldByName("international_date");
/* 2585 */         if (internationalDateString != null) {
/* 2586 */           selection.setInternationalDate(MilestoneHelper.getDatabaseDate(internationalDateString));
/*      */         }
/* 2588 */         selection.setOtherContact(connector.getField("coordinator_contacts", ""));
/*      */         
/* 2590 */         if (UserManager.getInstance().getUser(connector.getIntegerField("contact_id")) != null) {
/* 2591 */           selection.setLabelContact(UserManager.getInstance().getUser(connector.getIntegerField("contact_id")));
/*      */         }
/* 2593 */         selection.setSelectionStatus((SelectionStatus)getLookupObject(connector.getField("status"), 
/* 2594 */               Cache.getSelectionStatusList()));
/* 2595 */         selection.setHoldSelection(connector.getBoolean("hold_indicator"));
/*      */         
/* 2597 */         selection.setHoldReason(connector.getField("hold_reason", ""));
/* 2598 */         selection.setComments(connector.getField("comments", ""));
/* 2599 */         selection.setSpecialPackaging(connector.getBoolean("special_pkg_indicator"));
/* 2600 */         selection.setNumberOfUnits(numberOfUnits);
/* 2601 */         selection.setPressAndDistribution(connector.getBoolean("pd_indicator"));
/*      */         
/* 2603 */         selection.setPrefixID((PrefixCode)getLookupObject(connector.getField("prefix"), Cache.getPrefixCodes()));
/* 2604 */         selection.setSelectionPackaging(connector.getField("packaging", ""));
/*      */         
/* 2606 */         String impactDateString = connector.getFieldByName("impact_date");
/* 2607 */         if (impactDateString != null) {
/* 2608 */           selection.setImpactDate(MilestoneHelper.getDatabaseDate(impactDateString));
/*      */         }
/* 2610 */         String lastUpdateDateString = connector.getFieldByName("last_updated_on");
/* 2611 */         if (lastUpdateDateString != null) {
/* 2612 */           selection.setLastUpdateDate(MilestoneHelper.getDatabaseDate(lastUpdateDateString));
/*      */         }
/* 2614 */         selection.setLastUpdatingUser(UserManager.getInstance().getUser(connector.getIntegerField("last_updated_by")));
/*      */ 
/*      */         
/* 2617 */         String originDateString = connector.getFieldByName("entered_on");
/* 2618 */         if (originDateString != null) {
/* 2619 */           selection.setOriginDate(MilestoneHelper.getDatabaseDate(originDateString));
/*      */         }
/*      */ 
/*      */ 
/*      */         
/* 2624 */         User umlContact = UserManager.getInstance().getUser(connector.getIntegerField("mfg.[uml_id]"));
/* 2625 */         selection.setUmlContact(umlContact);
/* 2626 */         selection.setPlant(getLookupObject(connector.getField("mfg.[plant]"), Cache.getVendors()));
/* 2627 */         selection.setDistribution(getLookupObject(connector.getField("mfg.[distribution]"), Cache.getDistributionCodes()));
/* 2628 */         selection.setPoQuantity(connector.getIntegerField("mfg.[order_qty]"));
/* 2629 */         selection.setCompletedQuantity(connector.getIntegerField("mfg.[complete_qty]"));
/* 2630 */         selection.setManufacturingComments(connector.getField("mfg.[order_comments]", ""));
/* 2631 */         selection.setPrice(connector.getFloat("mfg.[list_price]"));
/*      */         
/* 2633 */         selection.setFullSelection(true);
/*      */         
/* 2635 */         String strDistribution = (reportForm.getStringValue("distribution") != null) ? reportForm.getStringValue("distribution") : "";
/* 2636 */         String strFuture2 = (reportForm.getStringValue("Future2") != null) ? reportForm.getStringValue("Future2") : "";
/*      */ 
/*      */         
/* 2639 */         if (!strDistribution.equalsIgnoreCase("") && !strDistribution.equalsIgnoreCase("All")) {
/*      */           
/* 2641 */           String selDistribution = SelectionManager.getLookupObjectValue(selection.getDistribution());
/*      */           
/* 2643 */           if (!selDistribution.equalsIgnoreCase(strDistribution.trim())) {
/*      */             continue;
/*      */           }
/*      */         } 
/* 2647 */         int nextReleaseId = connector.getIntegerField("release_id");
/*      */         
/* 2649 */         Schedule schedule = new Schedule();
/* 2650 */         schedule.setSelectionID(nextReleaseId);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 2659 */         Vector precacheSchedule = new Vector();
/* 2660 */         while (connector.more() && connector.getIntegerField("release_id") == nextReleaseId) {
/*      */           
/* 2662 */           ScheduledTask scheduledTask = null;
/*      */           
/* 2664 */           scheduledTask = new ScheduledTask();
/*      */ 
/*      */ 
/*      */           
/* 2668 */           scheduledTask.setReleaseID(connector.getIntegerField("release_Id"));
/*      */ 
/*      */           
/* 2671 */           scheduledTask.setTaskID(connector.getIntegerField("task_id"));
/*      */ 
/*      */           
/* 2674 */           scheduledTask.setOwner((Family)MilestoneHelper.getStructureObject(connector.getIntegerField("taskOwner")));
/*      */ 
/*      */           
/* 2677 */           String dueDateString = connector.getField("taskDue", "");
/* 2678 */           if (dueDateString.length() > 0) {
/* 2679 */             scheduledTask.setDueDate(MilestoneHelper.getDatabaseDate(dueDateString));
/*      */           }
/*      */           
/* 2682 */           String completionDateString = connector.getField("taskComplete", "");
/* 2683 */           if (completionDateString.length() > 0) {
/* 2684 */             scheduledTask.setCompletionDate(MilestoneHelper.getDatabaseDate(completionDateString));
/*      */           }
/*      */           
/* 2687 */           String taskStatus = connector.getField("taskStatus", "");
/* 2688 */           if (taskStatus.length() > 1) {
/* 2689 */             scheduledTask.setScheduledTaskStatus(taskStatus);
/*      */           }
/*      */           
/* 2692 */           int day = connector.getIntegerField("taskDayOfWeek");
/* 2693 */           if (day > 0) {
/* 2694 */             scheduledTask.setDayOfTheWeek(new Day(connector.getIntegerField("taskDayOfWeek")));
/*      */           }
/*      */           
/* 2697 */           int weeks = connector.getIntegerField("taskWeeksToRelease");
/* 2698 */           if (weeks > 0) {
/* 2699 */             scheduledTask.setWeeksToRelease(connector.getIntegerField("taskWeeksToRelease"));
/*      */           }
/*      */           
/* 2702 */           String vendorString = connector.getField("taskVendor", "");
/* 2703 */           if (vendorString.length() > 0) {
/* 2704 */             scheduledTask.setVendor(vendorString);
/*      */           }
/*      */           
/* 2707 */           int taskAbbrevID = connector.getIntegerField("abbrev_id");
/* 2708 */           scheduledTask.setTaskAbbreviationID(taskAbbrevID);
/*      */ 
/*      */           
/* 2711 */           int taskID = connector.getIntegerField("task_id");
/* 2712 */           scheduledTask.setScheduledTaskID(taskID);
/*      */ 
/*      */ 
/*      */           
/* 2716 */           String taskDept = connector.getField("department", "");
/* 2717 */           scheduledTask.setDepartment(taskDept);
/*      */ 
/*      */           
/* 2720 */           scheduledTask.setKeyTask(connector.getBoolean("taskKey"));
/*      */ 
/*      */           
/* 2723 */           scheduledTask.setAuthorizationName(connector.getField("taskAuthName", ""));
/*      */ 
/*      */           
/* 2726 */           String authDateString = connector.getField("taskAuthDate", "");
/* 2727 */           if (authDateString.length() > 0) {
/* 2728 */             scheduledTask.setAuthorizationDate(MilestoneHelper.getDatabaseDate(authDateString));
/*      */           }
/*      */           
/* 2731 */           String comments = connector.getField("taskComments", "");
/* 2732 */           scheduledTask.setComments(comments);
/*      */ 
/*      */           
/* 2735 */           scheduledTask.setName(connector.getField("name", ""));
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 2740 */           precacheSchedule.add(scheduledTask);
/*      */           
/* 2742 */           scheduledTask = null;
/*      */           
/* 2744 */           if (connector.more()) {
/*      */             
/* 2746 */             connector.next();
/* 2747 */             recordCount++;
/*      */             
/*      */             continue;
/*      */           } 
/*      */           
/*      */           break;
/*      */         } 
/* 2754 */         schedule.setTasks(precacheSchedule);
/*      */         
/* 2756 */         selection.setSchedule(schedule);
/*      */         
/* 2758 */         selections.add(selection);
/*      */       
/*      */       }
/* 2761 */       catch (Exception exception) {}
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2768 */     connector.close();
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2773 */     return selections;
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
/*      */   public static Vector getSelectionsForAddsReport(Context context, int reportProductType) {
/* 2787 */     System.out.println("ReportSelections::getSelectionsForAddsReport()");
/*      */     
/* 2789 */     User user = (User)context.getSessionValue("user");
/* 2790 */     int userId = 0;
/* 2791 */     if (user != null) {
/* 2792 */       userId = user.getUserId();
/*      */     }
/*      */     
/* 2795 */     return getAllSelectionsForUserUmlAdds(context, reportProductType);
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
/*      */   public static Vector getSelectionsForMovesReport(Context context, int reportProductType) {
/* 2811 */     System.out.println("ReportSelections::getSelectionsForMovesReport()");
/*      */     
/* 2813 */     User user = (User)context.getSessionValue("user");
/* 2814 */     int userId = 0;
/* 2815 */     if (user != null) {
/* 2816 */       userId = user.getUserId();
/*      */     }
/*      */     
/* 2819 */     return getAllSelectionsForUserUmlMoves(context, reportProductType);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Vector getAllSelectionsForUserUmlAdds(Context context, int reportProductType) {
/* 2829 */     System.out.println("ReportSelections::getAllSelectionsForUserUmlAdds()");
/* 2830 */     Vector companies = MilestoneHelper.getUserCompanies(context);
/* 2831 */     Company company = null;
/* 2832 */     Vector precache = new Vector();
/* 2833 */     Selection selection = null;
/* 2834 */     StringBuffer query = new StringBuffer(200);
/*      */ 
/*      */     
/* 2837 */     Form reportForm = (Form)context.getSessionValue("reportForm");
/* 2838 */     boolean bScheduled = (reportForm.getElement("ScheduledReleases") != null) ? ((FormCheckBox)reportForm.getElement("ScheduledReleases")).isChecked() : 0;
/*      */     
/* 2840 */     String sqlJoin = "LEFT";
/* 2841 */     if (bScheduled) {
/* 2842 */       sqlJoin = "INNER";
/*      */     }
/* 2844 */     query.append("SELECT header.[release_id], header.[artist],  header.[title], header.[artist_first_name], header.[artist_last_name],  header.[artist_first_name] + ' ' + header.[artist_last_name] AS fl_artist, header.[street_date], header.[configuration], header.[sub_configuration], header.[upc], header.[prefix], header.[selection_no],  header.[hold_reason], header.[hold_indicator], header.[pd_indicator],  header.[comments], header.[status], header.[family_id], header.[release_family_id],  header.[imprint], header.[environment_id], header.[company_id], header.[label_id], header.entered_on, header.[units],detail.* FROM Release_Header header with (nolock) " + 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 2853 */         sqlJoin + " JOIN" + 
/* 2854 */         " Release_SubDetail detail with (nolock) ON (header.[release_id] = detail.[release_id])" + 
/* 2855 */         " WHERE (header.status ='TBS' OR header.status ='Active') AND (");
/*      */     
/* 2857 */     String[] strFamily = ReportSelectionsHelper.getMultiSelectionListValues("family", reportForm);
/* 2858 */     String[] strEnvironment = ReportSelectionsHelper.getMultiSelectionListValues("environment", reportForm);
/* 2859 */     String[] strCompany = ReportSelectionsHelper.getMultiSelectionListValues("company", reportForm);
/* 2860 */     String[] strLabel = ReportSelectionsHelper.getMultiSelectionListValues("Label", reportForm);
/*      */ 
/*      */ 
/*      */     
/* 2864 */     String strProductType = (reportForm.getStringValue("ProductType") != null) ? reportForm.getStringValue("ProductType") : "";
/* 2865 */     if (strProductType.equals("")) {
/*      */       
/* 2867 */       strProductType = String.valueOf(reportProductType);
/*      */     
/*      */     }
/* 2870 */     else if (strProductType.equals("Physical")) {
/* 2871 */       strProductType = String.valueOf(0);
/* 2872 */     } else if (strProductType.equals("Digital")) {
/* 2873 */       strProductType = String.valueOf(1);
/* 2874 */     } else if (strProductType.equals("Both")) {
/* 2875 */       strProductType = String.valueOf(2);
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2883 */     ReportSelectionsHelper.addMultSelectionToStringBuffer(query, "environment_id", strEnvironment, true, "environment", reportForm, true, false);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2890 */     ReportSelectionsHelper.addMultSelectionToStringBuffer(query, "company_id", strCompany, true, "company", reportForm, false, true);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2896 */     ReportSelectionsHelper.addReleasingFamilyLabelFamilyMultSelect("family", context, query, reportForm);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2902 */     ReportSelectionsHelper.addMultSelectionToStringBuffer(query, "label_id", strLabel, true, "Label", reportForm, false, true);
/*      */ 
/*      */     
/* 2905 */     if (strProductType.equals(Integer.toString(1))) {
/* 2906 */       query.append(" AND digital_flag = 1 ");
/* 2907 */     } else if (strProductType.equals(Integer.toString(0))) {
/* 2908 */       query.append(" AND digital_flag = 0 ");
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2916 */     String[] strConfiguration = null;
/*      */     try {
/* 2918 */       MilestoneFormDropDownMenu configList = (MilestoneFormDropDownMenu)reportForm.getElement("configurationList");
/* 2919 */       if (configList != null) {
/* 2920 */         ArrayList configListAl = configList.getStringValues();
/* 2921 */         if (configListAl != null) {
/*      */           
/* 2923 */           strConfiguration = new String[configListAl.size()];
/* 2924 */           strConfiguration = (String[])configListAl.toArray(strConfiguration);
/*      */         } 
/*      */       } 
/* 2927 */     } catch (Exception e) {
/* 2928 */       e.printStackTrace();
/* 2929 */       System.out.println("<<< Subconfiguration vector execption " + e.getMessage());
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2937 */     String[] strSubconfiguration = null;
/*      */     try {
/* 2939 */       MilestoneFormDropDownMenu subconfigList = (MilestoneFormDropDownMenu)reportForm.getElement("subconfigurationList");
/* 2940 */       if (subconfigList != null) {
/* 2941 */         ArrayList subconfigListAl = subconfigList.getStringValues();
/* 2942 */         if (subconfigListAl != null) {
/*      */           
/* 2944 */           strSubconfiguration = new String[subconfigListAl.size()];
/* 2945 */           strSubconfiguration = (String[])subconfigListAl.toArray(strSubconfiguration);
/*      */         } 
/*      */       } 
/* 2948 */     } catch (Exception e) {
/* 2949 */       e.printStackTrace();
/* 2950 */       System.out.println("<<< Subconfiguration vector execption " + e.getMessage());
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2957 */     if (strSubconfiguration != null && 
/* 2958 */       !strSubconfiguration[0].trim().equalsIgnoreCase("0") && 
/* 2959 */       !strSubconfiguration[0].trim().equalsIgnoreCase("") && 
/* 2960 */       !strSubconfiguration[0].trim().equalsIgnoreCase("all")) {
/*      */ 
/*      */       
/* 2963 */       boolean addOr = false;
/* 2964 */       query.append(" AND (");
/* 2965 */       for (int x = 0; x < strSubconfiguration.length; x++) {
/* 2966 */         if (addOr)
/* 2967 */           query.append(" OR "); 
/* 2968 */         String txtvalue = strSubconfiguration[x];
/* 2969 */         if (txtvalue.indexOf("[") != -1) {
/*      */           
/* 2971 */           String txtconfigcode = txtvalue.substring(1, txtvalue.indexOf("]"));
/* 2972 */           String txtsubconfigcode = txtvalue.substring(txtvalue.indexOf("]") + 1, txtvalue.length());
/* 2973 */           query.append(" (header.[configuration] = '" + txtconfigcode.trim() + "' AND ");
/* 2974 */           query.append(" header.[sub_configuration] = '" + txtsubconfigcode.trim() + "') ");
/*      */         } else {
/*      */           
/* 2977 */           query.append(" header.[sub_configuration] = '" + txtvalue.trim() + "' ");
/*      */         } 
/* 2979 */         addOr = true;
/*      */       } 
/* 2981 */       query.append(") ");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     }
/* 2990 */     else if (strConfiguration != null && !strConfiguration[0].equalsIgnoreCase("") && !strConfiguration[0].equalsIgnoreCase("0") && 
/* 2991 */       !strConfiguration[0].equalsIgnoreCase("all")) {
/*      */       
/* 2993 */       boolean addOr = false;
/* 2994 */       query.append(" AND (");
/* 2995 */       for (int x = 0; x < strConfiguration.length; x++) {
/* 2996 */         if (addOr) {
/* 2997 */           query.append(" OR ");
/*      */         }
/* 2999 */         query.append(" header.[configuration] = '" + strConfiguration[x].trim() + "' ");
/* 3000 */         addOr = true;
/*      */       } 
/* 3002 */       query.append(") ");
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3034 */     String beginDate = "";
/* 3035 */     beginDate = (reportForm.getStringValue("beginEffectiveDate") != null && reportForm.getStringValue("beginEffectiveDate").length() > 0) ? reportForm.getStringValue("beginEffectiveDate") : "";
/* 3036 */     if (beginDate.equalsIgnoreCase(""))
/* 3037 */       beginDate = "01/01/1900"; 
/* 3038 */     beginDate = String.valueOf(beginDate) + " 00:00:00";
/*      */     
/* 3040 */     String endDate = "";
/* 3041 */     endDate = (reportForm.getStringValue("endEffectiveDate") != null && reportForm.getStringValue("endEffectiveDate").length() > 0) ? reportForm.getStringValue("endEffectiveDate") : "";
/* 3042 */     if (endDate.equalsIgnoreCase(""))
/* 3043 */       endDate = "01/01/2200"; 
/* 3044 */     endDate = String.valueOf(endDate) + " 23:59:59";
/*      */     
/* 3046 */     if (!beginDate.equalsIgnoreCase("") || !endDate.equalsIgnoreCase("")) {
/* 3047 */       query.append(" AND (");
/*      */     }
/*      */     
/* 3050 */     if (!beginDate.equalsIgnoreCase("")) {
/* 3051 */       query.append(" entered_on >= '" + MilestoneHelper.escapeSingleQuotes(beginDate) + "'");
/*      */     }
/* 3053 */     if (!endDate.equalsIgnoreCase(""))
/*      */     {
/* 3055 */       if (!beginDate.equalsIgnoreCase("")) {
/* 3056 */         query.append(" AND entered_on <= '" + MilestoneHelper.escapeSingleQuotes(endDate) + "'");
/*      */       } else {
/* 3058 */         query.append(" entered_on <= '" + MilestoneHelper.escapeSingleQuotes(endDate) + "'");
/*      */       } 
/*      */     }
/* 3061 */     if (!beginDate.equalsIgnoreCase("") || !endDate.equalsIgnoreCase("")) query.append(")");
/*      */ 
/*      */     
/* 3064 */     query.append(") ORDER BY street_date, configuration, selection_no");
/*      */ 
/*      */ 
/*      */     
/* 3068 */     Iterator formIterator = reportForm.getElements();
/* 3069 */     Vector distCoIDs = new Vector();
/* 3070 */     while (formIterator.hasNext()) {
/*      */ 
/*      */       
/* 3073 */       FormElement field = (FormElement)formIterator.next();
/* 3074 */       String fieldName = field.getName();
/* 3075 */       if (fieldName.startsWith("distCo")) {
/*      */         
/* 3077 */         FormCheckBox fCheck = (FormCheckBox)field;
/* 3078 */         if (fCheck.isChecked()) {
/* 3079 */           distCoIDs.add(fieldName.substring(6, fieldName.length()));
/*      */         }
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3087 */     JdbcConnector connector = MilestoneHelper.getConnector(query.toString());
/* 3088 */     connector.setForwardOnly(false);
/* 3089 */     connector.runQuery();
/* 3090 */     int totalCount = 0;
/* 3091 */     int tenth = 0;
/* 3092 */     totalCount = connector.getRowCount();
/*      */ 
/*      */     
/* 3095 */     tenth = totalCount / 5;
/*      */     
/* 3097 */     if (tenth < 1) {
/* 3098 */       tenth = 1;
/*      */     }
/*      */     
/*      */     try {
/* 3102 */       HttpServletResponse sresponse = context.getResponse();
/* 3103 */       context.putDelivery("status", new String("start_gathering"));
/* 3104 */       context.putDelivery("percent", new String("10"));
/* 3105 */       if (!ReportingServices.usingReportServicesByContext(context))
/*      */       {
/* 3107 */         context.includeJSP("status.jsp", "hiddenFrame");
/* 3108 */         sresponse.setContentType("text/plain");
/* 3109 */         sresponse.flushBuffer();
/*      */       }
/*      */     
/* 3112 */     } catch (Exception exception) {}
/*      */ 
/*      */ 
/*      */     
/* 3116 */     int recordCount = 0;
/* 3117 */     int count = 0;
/*      */ 
/*      */     
/* 3120 */     while (connector.more()) {
/*      */ 
/*      */       
/*      */       try {
/* 3124 */         if (count < recordCount / tenth) {
/*      */           
/* 3126 */           count = recordCount / tenth;
/* 3127 */           HttpServletResponse sresponse = context.getResponse();
/* 3128 */           context.putDelivery("status", new String("start_gathering"));
/* 3129 */           context.putDelivery("percent", new String(String.valueOf(count * 10)));
/* 3130 */           if (!ReportingServices.usingReportServicesByContext(context)) {
/*      */             
/* 3132 */             context.includeJSP("status.jsp", "hiddenFrame");
/* 3133 */             sresponse.setContentType("text/plain");
/* 3134 */             sresponse.flushBuffer();
/*      */           } 
/*      */         } 
/*      */         
/* 3138 */         recordCount++;
/*      */ 
/*      */         
/* 3141 */         selection = new Selection();
/*      */ 
/*      */         
/* 3144 */         selection.setSelectionID(connector.getIntegerField("release_id"));
/*      */         
/* 3146 */         selection.setTitle(connector.getField("title", ""));
/*      */ 
/*      */         
/* 3149 */         selection.setArtistFirstName(connector.getField("artist_first_name", ""));
/*      */ 
/*      */         
/* 3152 */         selection.setArtistLastName(connector.getField("artist_last_name", ""));
/*      */ 
/*      */         
/* 3155 */         selection.setFlArtist(connector.getField("fl_artist", ""));
/*      */ 
/*      */ 
/*      */         
/* 3159 */         String streetDateString = connector.getFieldByName("street_date");
/* 3160 */         if (streetDateString != null) {
/* 3161 */           selection.setStreetDate(MilestoneHelper.getDatabaseDate(streetDateString));
/*      */         }
/* 3163 */         selection.setUpc(connector.getField("upc", ""));
/*      */         
/* 3165 */         selection.setSelectionConfig(
/* 3166 */             getSelectionConfigObject(connector.getField("configuration"), 
/* 3167 */               Cache.getSelectionConfigs()));
/*      */ 
/*      */ 
/*      */         
/* 3171 */         selection.setSelectionSubConfig(getSelectionSubConfigObject(connector.getField("sub_configuration"), selection.getSelectionConfig()));
/* 3172 */         selection.setPrefixID((PrefixCode)getLookupObject(connector.getField("prefix"), Cache.getPrefixCodes()));
/* 3173 */         selection.setSelectionNo(connector.getField("selection_no"));
/* 3174 */         selection.setSelectionStatus((SelectionStatus)getLookupObject(connector.getField("status"), 
/* 3175 */               Cache.getSelectionStatusList()));
/*      */         
/* 3177 */         selection.setOriginDate(MilestoneHelper.getDatabaseDate(connector.getField("entered_on")));
/* 3178 */         selection.setDistribution(getLookupObject(connector.getField("distribution"), Cache.getDistributionCodes()));
/* 3179 */         selection.setPlant(getLookupObject(connector.getField("detail.plant"), 
/* 3180 */               Cache.getVendors()));
/* 3181 */         selection.setHoldReason(connector.getField("hold_reason"));
/* 3182 */         selection.setComments(connector.getField("comments"));
/* 3183 */         selection.setPoQuantity(connector.getInt("order_qty"));
/*      */         
/* 3185 */         selection.setNumberOfUnits(connector.getInt("units"));
/* 3186 */         selection.setCompletedQuantity(connector.getInt("complete_qty"));
/* 3187 */         selection.setLabel((Label)MilestoneHelper.getStructureObject(connector.getInt("label_id")));
/* 3188 */         selection.setPressAndDistribution(connector.getBoolean("pd_indicator"));
/*      */ 
/*      */         
/* 3191 */         selection.setImprint(connector.getField("imprint", ""));
/* 3192 */         selection.setReleaseFamilyId(connector.getIntegerField("release_family_id"));
/*      */ 
/*      */         
/* 3195 */         Label currentLabel = selection.getLabel();
/* 3196 */         int selectionsDistCompany = MilestoneHelper_2.getLabelDistCoId(currentLabel.getStructureID());
/*      */         
/* 3198 */         if (selectionsDistCompany == -1) {
/* 3199 */           selectionsDistCompany = Integer.parseInt("1");
/*      */         }
/*      */ 
/*      */         
/* 3203 */         if (distCoIDs.contains(Integer.toString(selectionsDistCompany)))
/*      */         {
/* 3205 */           precache.add(selection);
/*      */         }
/*      */         
/* 3208 */         selection = null;
/* 3209 */         connector.next();
/*      */       }
/* 3211 */       catch (Exception e) {
/*      */         
/* 3213 */         System.out.println("**** Exception: " + e.toString());
/* 3214 */         connector.next();
/*      */       } 
/*      */     } 
/*      */     
/* 3218 */     connector.close();
/* 3219 */     company = null;
/*      */     
/* 3221 */     return precache;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Vector getAllSelectionsForUserUmlMoves(Context context, int reportProductType) {
/* 3228 */     System.out.println("ReportSelections::getAllSelectionsForUserUmlMoves()");
/* 3229 */     Vector companies = MilestoneHelper.getUserCompanies(context);
/* 3230 */     Company company = null;
/* 3231 */     Vector precache = new Vector();
/* 3232 */     Selection selection = null;
/* 3233 */     StringBuffer query = new StringBuffer(200);
/*      */ 
/*      */     
/* 3236 */     Form reportForm = (Form)context.getSessionValue("reportForm");
/*      */     
/* 3238 */     boolean bScheduled = (reportForm.getElement("ScheduledReleases") != null) ? ((FormCheckBox)reportForm.getElement("ScheduledReleases")).isChecked() : 0;
/* 3239 */     String sqlJoin = "LEFT";
/* 3240 */     if (bScheduled) {
/* 3241 */       sqlJoin = "INNER";
/*      */     }
/* 3243 */     query.append("SELECT header.[release_id], header.[artist],  header.[title], header.[artist_first_name], header.[artist_last_name],  header.[artist_first_name] + ' ' + header.[artist_last_name] AS fl_artist, header.[street_date], header.[configuration], header.[sub_configuration], header.[upc], header.[prefix], header.[selection_no],  header.[hold_reason], header.[hold_indicator], header.[pd_indicator],  header.[comments], header.[status],header.[family_id], header.[release_family_id], audit.[logged_on],  header.[imprint], header.[environment_id], header.[company_id], header.[label_id], header.entered_on, header.[units], detail.* FROM Release_Header header with (nolock) INNER JOIN Audit_Release_Header audit with (nolock) ON header.release_id = audit.release_id " + 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 3254 */         sqlJoin + " JOIN Release_SubDetail detail with (nolock) ON audit.release_id = detail.release_id " + 
/* 3255 */         " WHERE (header.status ='TBS' OR header.status ='Active') AND (");
/*      */ 
/*      */ 
/*      */     
/* 3259 */     String[] strFamily = ReportSelectionsHelper.getMultiSelectionListValues("family", reportForm);
/* 3260 */     String[] strEnvironment = ReportSelectionsHelper.getMultiSelectionListValues("environment", reportForm);
/* 3261 */     String[] strCompany = ReportSelectionsHelper.getMultiSelectionListValues("company", reportForm);
/* 3262 */     String[] strLabel = ReportSelectionsHelper.getMultiSelectionListValues("Label", reportForm);
/*      */ 
/*      */     
/* 3265 */     String strProductType = (reportForm.getStringValue("ProductType") != null) ? 
/* 3266 */       reportForm.getStringValue("ProductType") : "";
/* 3267 */     if (strProductType.equals("")) {
/*      */       
/* 3269 */       strProductType = String.valueOf(reportProductType);
/*      */ 
/*      */     
/*      */     }
/* 3273 */     else if (strProductType.equals("Physical")) {
/* 3274 */       strProductType = String.valueOf(0);
/*      */     }
/* 3276 */     else if (strProductType.equals("Digital")) {
/* 3277 */       strProductType = String.valueOf(1);
/*      */     }
/* 3279 */     else if (strProductType.equals("Both")) {
/* 3280 */       strProductType = String.valueOf(2);
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3288 */     ReportSelectionsHelper.addMultSelectionToStringBuffer(query, "header.environment_id", strEnvironment, true, "environment", reportForm, true, false);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3296 */     ReportSelectionsHelper.addMultSelectionToStringBuffer(query, "header.company_id", strCompany, true, "company", reportForm, false, true);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3302 */     ReportSelectionsHelper.addReleasingFamilyLabelFamilyMultSelect("family", context, query, reportForm);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3307 */     ReportSelectionsHelper.addMultSelectionToStringBuffer(query, "header.label_id", strLabel, true, "Label", reportForm, false, true);
/*      */ 
/*      */ 
/*      */     
/* 3311 */     if (strProductType.equals(Integer.toString(1))) {
/* 3312 */       query.append(" AND digital_flag = 1 ");
/* 3313 */     } else if (strProductType.equals(Integer.toString(0))) {
/* 3314 */       query.append(" AND digital_flag = 0 ");
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3324 */     String[] strConfiguration = null;
/*      */     try {
/* 3326 */       MilestoneFormDropDownMenu configList = (MilestoneFormDropDownMenu)reportForm.getElement("configurationList");
/* 3327 */       if (configList != null) {
/* 3328 */         ArrayList configListAl = configList.getStringValues();
/* 3329 */         if (configListAl != null) {
/*      */           
/* 3331 */           strConfiguration = new String[configListAl.size()];
/* 3332 */           strConfiguration = (String[])configListAl.toArray(strConfiguration);
/*      */         } 
/*      */       } 
/* 3335 */     } catch (Exception e) {
/* 3336 */       e.printStackTrace();
/* 3337 */       System.out.println("<<< Configuration vector execption " + e.getMessage());
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3343 */     String[] strSubconfiguration = null;
/*      */     try {
/* 3345 */       MilestoneFormDropDownMenu subconfigList = (MilestoneFormDropDownMenu)reportForm.getElement("subconfigurationList");
/* 3346 */       if (subconfigList != null) {
/* 3347 */         ArrayList subconfigListAl = subconfigList.getStringValues();
/* 3348 */         if (subconfigListAl != null) {
/*      */           
/* 3350 */           strSubconfiguration = new String[subconfigListAl.size()];
/* 3351 */           strSubconfiguration = (String[])subconfigListAl.toArray(strSubconfiguration);
/*      */         } 
/*      */       } 
/* 3354 */     } catch (Exception e) {
/* 3355 */       e.printStackTrace();
/* 3356 */       System.out.println("<<< Subconfiguration vector execption " + e.getMessage());
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3362 */     if (strSubconfiguration != null && 
/* 3363 */       !strSubconfiguration[0].trim().equalsIgnoreCase("0") && 
/* 3364 */       !strSubconfiguration[0].trim().equalsIgnoreCase("") && 
/* 3365 */       !strSubconfiguration[0].trim().equalsIgnoreCase("all")) {
/*      */       
/* 3367 */       boolean addOr = false;
/* 3368 */       query.append(" AND (");
/* 3369 */       for (int x = 0; x < strSubconfiguration.length; x++) {
/* 3370 */         if (addOr)
/* 3371 */           query.append(" OR "); 
/* 3372 */         String txtvalue = strSubconfiguration[x];
/* 3373 */         if (txtvalue.indexOf("[") != -1) {
/*      */           
/* 3375 */           String txtconfigcode = txtvalue.substring(1, txtvalue.indexOf("]"));
/* 3376 */           String txtsubconfigcode = txtvalue.substring(txtvalue.indexOf("]") + 1, txtvalue.length());
/* 3377 */           query.append(" (header.[configuration] = '" + txtconfigcode.trim() + "' AND ");
/* 3378 */           query.append(" header.[sub_configuration] = '" + txtsubconfigcode.trim() + "') ");
/*      */         } else {
/*      */           
/* 3381 */           query.append(" header.[sub_configuration] = '" + txtvalue.trim() + "' ");
/*      */         } 
/* 3383 */         addOr = true;
/*      */       } 
/* 3385 */       query.append(") ");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     }
/* 3393 */     else if (strConfiguration != null && !strConfiguration[0].equalsIgnoreCase("") && !strConfiguration[0].equalsIgnoreCase("0") && 
/* 3394 */       !strConfiguration[0].equalsIgnoreCase("all")) {
/*      */       
/* 3396 */       boolean addOr = false;
/* 3397 */       query.append(" AND (");
/* 3398 */       for (int x = 0; x < strConfiguration.length; x++) {
/* 3399 */         if (addOr) {
/* 3400 */           query.append(" OR ");
/*      */         }
/* 3402 */         query.append(" header.[configuration] = '" + strConfiguration[x].trim() + "' ");
/* 3403 */         addOr = true;
/*      */       } 
/* 3405 */       query.append(") ");
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3433 */     String beginDate = "";
/* 3434 */     beginDate = (reportForm.getStringValue("beginEffectiveDate") != null && reportForm.getStringValue("beginEffectiveDate").length() > 0) ? reportForm.getStringValue("beginEffectiveDate") : "";
/* 3435 */     if (beginDate.equalsIgnoreCase(""))
/* 3436 */       beginDate = "01/01/1900"; 
/* 3437 */     beginDate = String.valueOf(beginDate) + " 00:00:00";
/*      */     
/* 3439 */     String endDate = "";
/* 3440 */     endDate = (reportForm.getStringValue("endEffectiveDate") != null && reportForm.getStringValue("endEffectiveDate").length() > 0) ? reportForm.getStringValue("endEffectiveDate") : "";
/* 3441 */     if (endDate.equalsIgnoreCase(""))
/* 3442 */       endDate = "01/01/2200"; 
/* 3443 */     endDate = String.valueOf(endDate) + " 23:59:59";
/*      */     
/* 3445 */     if (!beginDate.equalsIgnoreCase("") || !endDate.equalsIgnoreCase("")) {
/* 3446 */       query.append(" AND (");
/*      */     }
/* 3448 */     if (!beginDate.equalsIgnoreCase("")) {
/* 3449 */       query.append(" audit.logged_on >= '" + MilestoneHelper.escapeSingleQuotes(beginDate) + "'");
/*      */     }
/* 3451 */     if (!endDate.equalsIgnoreCase(""))
/*      */     {
/* 3453 */       if (!beginDate.equalsIgnoreCase("")) {
/* 3454 */         query.append(" AND audit.logged_on <= '" + MilestoneHelper.escapeSingleQuotes(endDate) + "'");
/*      */       } else {
/* 3456 */         query.append(" audit.logged_on <= '" + MilestoneHelper.escapeSingleQuotes(endDate) + "'");
/*      */       } 
/*      */     }
/* 3459 */     if (!beginDate.equalsIgnoreCase("") || !endDate.equalsIgnoreCase("")) query.append(")");
/*      */ 
/*      */     
/* 3462 */     query.append(") ORDER BY UPPER(header.artist), header.street_date, UPPER(header.title)");
/*      */ 
/*      */ 
/*      */     
/* 3466 */     Iterator formIterator = reportForm.getElements();
/* 3467 */     Vector distCoIDs = new Vector();
/* 3468 */     while (formIterator.hasNext()) {
/*      */ 
/*      */       
/* 3471 */       FormElement field = (FormElement)formIterator.next();
/* 3472 */       String fieldName = field.getName();
/* 3473 */       if (fieldName.startsWith("distCo")) {
/*      */         
/* 3475 */         FormCheckBox fCheck = (FormCheckBox)field;
/* 3476 */         if (fCheck.isChecked()) {
/* 3477 */           distCoIDs.add(fieldName.substring(6, fieldName.length()));
/*      */         }
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 3484 */     JdbcConnector connector = MilestoneHelper.getConnector(query.toString());
/* 3485 */     connector.setForwardOnly(false);
/* 3486 */     connector.runQuery();
/* 3487 */     int totalCount = 0;
/* 3488 */     int tenth = 0;
/* 3489 */     totalCount = connector.getRowCount();
/*      */ 
/*      */     
/* 3492 */     tenth = totalCount / 5;
/*      */     
/* 3494 */     if (tenth < 1) {
/* 3495 */       tenth = 1;
/*      */     }
/*      */     
/*      */     try {
/* 3499 */       HttpServletResponse sresponse = context.getResponse();
/* 3500 */       context.putDelivery("status", new String("start_gathering"));
/* 3501 */       context.putDelivery("percent", new String("10"));
/* 3502 */       if (!ReportingServices.usingReportServicesByContext(context))
/*      */       {
/* 3504 */         context.includeJSP("status.jsp", "hiddenFrame");
/* 3505 */         sresponse.setContentType("text/plain");
/* 3506 */         sresponse.flushBuffer();
/*      */       }
/*      */     
/* 3509 */     } catch (Exception exception) {}
/*      */ 
/*      */ 
/*      */     
/* 3513 */     int recordCount = 0;
/* 3514 */     int count = 0;
/*      */ 
/*      */     
/* 3517 */     while (connector.more()) {
/*      */ 
/*      */       
/*      */       try {
/* 3521 */         if (count < recordCount / tenth) {
/*      */           
/* 3523 */           count = recordCount / tenth;
/* 3524 */           HttpServletResponse sresponse = context.getResponse();
/* 3525 */           context.putDelivery("status", new String("start_gathering"));
/* 3526 */           context.putDelivery("percent", new String(String.valueOf(count * 10)));
/* 3527 */           if (!ReportingServices.usingReportServicesByContext(context)) {
/*      */             
/* 3529 */             context.includeJSP("status.jsp", "hiddenFrame");
/* 3530 */             sresponse.setContentType("text/plain");
/* 3531 */             sresponse.flushBuffer();
/*      */           } 
/*      */         } 
/*      */         
/* 3535 */         recordCount++;
/*      */ 
/*      */         
/* 3538 */         selection = new Selection();
/*      */ 
/*      */         
/* 3541 */         selection.setSelectionID(connector.getIntegerField("release_id"));
/*      */         
/* 3543 */         selection.setTitle(connector.getField("title", ""));
/*      */ 
/*      */         
/* 3546 */         selection.setArtistFirstName(connector.getField("artist_first_name", ""));
/*      */ 
/*      */         
/* 3549 */         selection.setArtistLastName(connector.getField("artist_last_name", ""));
/*      */ 
/*      */         
/* 3552 */         selection.setFlArtist(connector.getField("fl_artist", ""));
/*      */ 
/*      */         
/* 3555 */         String streetDateString = connector.getFieldByName("street_date");
/* 3556 */         if (streetDateString != null) {
/* 3557 */           selection.setStreetDate(MilestoneHelper.getDatabaseDate(streetDateString));
/*      */         }
/* 3559 */         selection.setUpc(connector.getField("upc", ""));
/*      */         
/* 3561 */         selection.setSelectionConfig(
/* 3562 */             getSelectionConfigObject(connector.getField("configuration"), 
/* 3563 */               Cache.getSelectionConfigs()));
/*      */ 
/*      */ 
/*      */         
/* 3567 */         selection.setSelectionSubConfig(getSelectionSubConfigObject(connector.getField("sub_configuration"), selection.getSelectionConfig()));
/* 3568 */         selection.setPrefixID((PrefixCode)getLookupObject(connector.getField("prefix"), Cache.getPrefixCodes()));
/* 3569 */         selection.setSelectionNo(connector.getField("selection_no"));
/* 3570 */         selection.setSelectionStatus((SelectionStatus)getLookupObject(connector.getField("status"), 
/* 3571 */               Cache.getSelectionStatusList()));
/*      */ 
/*      */         
/* 3574 */         selection.setLastStreetUpdateDate(MilestoneHelper.getDatabaseDate(connector.getField("logged_on")));
/* 3575 */         selection.setOriginDate(MilestoneHelper.getDatabaseDate(connector.getField("entered_on")));
/* 3576 */         selection.setPlant(getLookupObject(connector.getField("plant"), 
/* 3577 */               Cache.getVendors()));
/* 3578 */         selection.setDistribution(getLookupObject(connector.getField("distribution"), Cache.getDistributionCodes()));
/* 3579 */         selection.setHoldReason(connector.getField("hold_reason"));
/* 3580 */         selection.setComments(connector.getField("comments"));
/* 3581 */         selection.setPoQuantity(connector.getInt("order_qty"));
/*      */         
/* 3583 */         selection.setNumberOfUnits(connector.getInt("units"));
/* 3584 */         selection.setCompletedQuantity(connector.getInt("complete_qty"));
/* 3585 */         selection.setLabel((Label)MilestoneHelper.getStructureObject(connector.getInt("label_id")));
/* 3586 */         selection.setPressAndDistribution(connector.getBoolean("pd_indicator"));
/*      */ 
/*      */         
/* 3589 */         selection.setImprint(connector.getField("imprint", ""));
/* 3590 */         selection.setReleaseFamilyId(connector.getIntegerField("release_family_id"));
/*      */ 
/*      */ 
/*      */         
/* 3594 */         Label currentLabel = selection.getLabel();
/* 3595 */         int selectionsDistCompany = MilestoneHelper_2.getLabelDistCoId(currentLabel.getStructureID());
/*      */         
/* 3597 */         if (selectionsDistCompany == -1) {
/* 3598 */           selectionsDistCompany = Integer.parseInt("1");
/*      */         }
/*      */ 
/*      */         
/* 3602 */         if (distCoIDs.contains(Integer.toString(selectionsDistCompany)))
/*      */         {
/* 3604 */           precache.add(selection);
/*      */         }
/*      */         
/* 3607 */         selection = null;
/* 3608 */         connector.next();
/*      */       
/*      */       }
/* 3611 */       catch (Exception e) {
/*      */         
/* 3613 */         System.out.println("**** Exception: " + e.toString());
/* 3614 */         connector.next();
/*      */       } 
/*      */     } 
/*      */     
/* 3618 */     connector.close();
/* 3619 */     company = null;
/*      */     
/* 3621 */     return precache;
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
/*      */   public static Vector getSelectionsForPhysicalProductActivityReport(Context context, int reportProductType, String activity) {
/* 3637 */     Vector companies = MilestoneHelper.getUserCompanies(context);
/* 3638 */     Company company = null;
/* 3639 */     Vector precache = new Vector();
/* 3640 */     Selection selection = null;
/* 3641 */     StringBuffer query = new StringBuffer(300);
/*      */ 
/*      */     
/* 3644 */     Form reportForm = (Form)context.getSessionValue("reportForm");
/*      */     
/* 3646 */     query.append("SELECT DISTINCT headeraudit.[release_id], headeraudit.[artist],  headeraudit.[title], headeraudit.[artist_first_name], headeraudit.[artist_last_name],  headeraudit.[artist_first_name] + ' ' + headeraudit.[artist_last_name] AS fl_artist, headeraudit.[street_date], headeraudit.[configuration], headeraudit.[sub_configuration], headeraudit.[upc], headeraudit.[prefix], headeraudit.[selection_no],  headeraudit.[status], headeraudit.[family_id], headeraudit.[release_family_id],  headeraudit.[imprint],  headeraudit.[company_id], headeraudit.[label_id],  headeraudit.[audit_date], ");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3656 */     query.append(" headeraudit.[prefix] as audit_prefix, headeraudit.[selection_no] as audit_selection_no, headeraudit.[upc] as audit_upc, headeraudit.[status] as audit_status, headeraudit.[street_date] as audit_street_date ");
/*      */     
/* 3658 */     if (activity.equals("adds")) {
/* 3659 */       query.append(" FROM Release_Header_Audit headeraudit with (nolock), Release_Header header with (nolock) WHERE (headeraudit.release_id = header.release_id) AND ");
/*      */     } else {
/* 3661 */       query.append(" FROM Release_Header_Audit headeraudit with (nolock) WHERE ");
/*      */     } 
/*      */     
/* 3664 */     String[] strFamily = ReportSelectionsHelper.getMultiSelectionListValues("family", reportForm);
/* 3665 */     String[] strEnvironment = ReportSelectionsHelper.getMultiSelectionListValues("environment", reportForm);
/* 3666 */     String[] strCompany = ReportSelectionsHelper.getMultiSelectionListValues("company", reportForm);
/* 3667 */     String[] strLabel = ReportSelectionsHelper.getMultiSelectionListValues("Label", reportForm);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3679 */     ReportSelectionsHelper.addMultSelectionToStringBuffer(query, "headeraudit.environment_id", strEnvironment, true, "environment", reportForm, true, false);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3686 */     ReportSelectionsHelper.addMultSelectionToStringBuffer(query, "headeraudit.company_id", strCompany, true, "company", reportForm, false, true);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3691 */     ReportSelectionsHelper.addReleasingFamilyLabelFamilySelectForAudit("family", context, query, reportForm, "headeraudit");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3697 */     ReportSelectionsHelper.addMultSelectionToStringBuffer(query, "headeraudit.label_id", strLabel, true, "Label", reportForm, false, true);
/*      */     
/* 3699 */     query.append(" AND headeraudit.digital_flag = 0 ");
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3704 */     String strLabelContact = (reportForm.getStringValue("contact") != null) ? reportForm.getStringValue("contact") : "";
/* 3705 */     if (!strLabelContact.equals("") && !strLabelContact.equals("0")) {
/* 3706 */       query.append(" AND (header.contact_id = '" + strLabelContact.trim() + "') ");
/*      */     }
/*      */ 
/*      */     
/* 3710 */     String strReleaseType = (reportForm.getStringValue("releaseType") != null) ? reportForm.getStringValue("releaseType") : "";
/* 3711 */     if (!strReleaseType.equalsIgnoreCase("") && !strReleaseType.equalsIgnoreCase("all"))
/*      */     {
/* 3713 */       if (strReleaseType.equalsIgnoreCase("commercial")) {
/* 3714 */         query.append(" AND (header.[release_type] ='CO') ");
/*      */       } else {
/* 3716 */         query.append(" AND (header.[release_type] ='PR') ");
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/* 3721 */     if (activity.equals("adds")) {
/* 3722 */       ReportSelectionsHelper.addStatusToSelect(reportForm, query);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3731 */     String[] strConfiguration = null;
/*      */     try {
/* 3733 */       MilestoneFormDropDownMenu configList = (MilestoneFormDropDownMenu)reportForm.getElement("configurationList");
/* 3734 */       if (configList != null) {
/* 3735 */         ArrayList configListAl = configList.getStringValues();
/* 3736 */         if (configListAl != null) {
/*      */           
/* 3738 */           strConfiguration = new String[configListAl.size()];
/* 3739 */           strConfiguration = (String[])configListAl.toArray(strConfiguration);
/*      */         } 
/*      */       } 
/* 3742 */     } catch (Exception e) {
/* 3743 */       e.printStackTrace();
/* 3744 */       System.out.println("<<< Subconfiguration vector execption " + e.getMessage());
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3752 */     String[] strSubconfiguration = null;
/*      */     try {
/* 3754 */       MilestoneFormDropDownMenu subconfigList = (MilestoneFormDropDownMenu)reportForm.getElement("subconfigurationList");
/* 3755 */       if (subconfigList != null) {
/* 3756 */         ArrayList subconfigListAl = subconfigList.getStringValues();
/* 3757 */         if (subconfigListAl != null) {
/*      */           
/* 3759 */           strSubconfiguration = new String[subconfigListAl.size()];
/* 3760 */           strSubconfiguration = (String[])subconfigListAl.toArray(strSubconfiguration);
/*      */         } 
/*      */       } 
/* 3763 */     } catch (Exception e) {
/* 3764 */       e.printStackTrace();
/* 3765 */       System.out.println("<<< Subconfiguration vector execption " + e.getMessage());
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3772 */     if (strSubconfiguration != null && 
/* 3773 */       !strSubconfiguration[0].trim().equalsIgnoreCase("0") && 
/* 3774 */       !strSubconfiguration[0].trim().equalsIgnoreCase("") && 
/* 3775 */       !strSubconfiguration[0].trim().equalsIgnoreCase("all")) {
/*      */ 
/*      */       
/* 3778 */       boolean addOr = false;
/* 3779 */       query.append(" AND (");
/* 3780 */       for (int x = 0; x < strSubconfiguration.length; x++) {
/* 3781 */         if (addOr)
/* 3782 */           query.append(" OR "); 
/* 3783 */         String txtvalue = strSubconfiguration[x];
/* 3784 */         if (txtvalue.indexOf("[") != -1) {
/*      */           
/* 3786 */           String txtconfigcode = txtvalue.substring(1, txtvalue.indexOf("]"));
/* 3787 */           String txtsubconfigcode = txtvalue.substring(txtvalue.indexOf("]") + 1, txtvalue.length());
/* 3788 */           query.append(" (headeraudit.[configuration] = '" + txtconfigcode.trim() + "' AND ");
/* 3789 */           query.append(" headeraudit.[sub_configuration] = '" + txtsubconfigcode.trim() + "') ");
/*      */         } else {
/*      */           
/* 3792 */           query.append(" headeraudit.[sub_configuration] = '" + txtvalue.trim() + "' ");
/*      */         } 
/* 3794 */         addOr = true;
/*      */       } 
/* 3796 */       query.append(") ");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     }
/* 3805 */     else if (strConfiguration != null && !strConfiguration[0].equalsIgnoreCase("") && !strConfiguration[0].equalsIgnoreCase("0") && 
/* 3806 */       !strConfiguration[0].equalsIgnoreCase("all")) {
/*      */       
/* 3808 */       boolean addOr = false;
/* 3809 */       query.append(" AND (");
/* 3810 */       for (int x = 0; x < strConfiguration.length; x++) {
/* 3811 */         if (addOr) {
/* 3812 */           query.append(" OR ");
/*      */         }
/* 3814 */         query.append(" headeraudit.[configuration] = '" + strConfiguration[x].trim() + "' ");
/* 3815 */         addOr = true;
/*      */       } 
/* 3817 */       query.append(") ");
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3823 */     if (activity.equals("adds")) {
/* 3824 */       query.append(" AND (headeraudit.audit_code = 'I') ");
/* 3825 */       query.append(" AND (headeraudit.status IN ('ACTIVE', 'TBS')) ");
/*      */     } 
/*      */     
/* 3828 */     if (activity.equals("deletes")) {
/* 3829 */       query.append(" AND (headeraudit.audit_code = 'D') ");
/*      */     }
/*      */     
/* 3832 */     String beginDate = "";
/* 3833 */     beginDate = (reportForm.getStringValue("beginEffectiveDate") != null && reportForm.getStringValue("beginEffectiveDate").length() > 0) ? reportForm.getStringValue("beginEffectiveDate") : "";
/* 3834 */     if (beginDate.equalsIgnoreCase(""))
/* 3835 */       beginDate = "01/01/1900"; 
/* 3836 */     beginDate = String.valueOf(beginDate) + " 00:00:00";
/*      */     
/* 3838 */     String endDate = "";
/* 3839 */     endDate = (reportForm.getStringValue("endEffectiveDate") != null && reportForm.getStringValue("endEffectiveDate").length() > 0) ? reportForm.getStringValue("endEffectiveDate") : "";
/* 3840 */     if (endDate.equalsIgnoreCase(""))
/* 3841 */       endDate = "01/01/2200"; 
/* 3842 */     endDate = String.valueOf(endDate) + " 23:59:59";
/*      */     
/* 3844 */     if (!beginDate.equalsIgnoreCase("") || !endDate.equalsIgnoreCase("")) {
/* 3845 */       query.append(" AND (");
/*      */     }
/* 3847 */     String tableName = "headeraudit";
/*      */     
/* 3849 */     if (!beginDate.equalsIgnoreCase("")) {
/* 3850 */       query.append(" " + tableName + ".audit_date >= '" + MilestoneHelper.escapeSingleQuotes(beginDate) + "'");
/*      */     }
/* 3852 */     if (!endDate.equalsIgnoreCase("")) {
/* 3853 */       if (!beginDate.equalsIgnoreCase("")) {
/* 3854 */         query.append(" AND " + tableName + ".audit_date <= '" + MilestoneHelper.escapeSingleQuotes(endDate) + "'");
/*      */       } else {
/* 3856 */         query.append(" " + tableName + ".audit_date <= '" + MilestoneHelper.escapeSingleQuotes(endDate) + "'");
/*      */       } 
/*      */     }
/* 3859 */     if (!beginDate.equalsIgnoreCase("") || !endDate.equalsIgnoreCase("")) query.append(")");
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3864 */     String beginDate2 = "";
/* 3865 */     beginDate2 = (reportForm.getStringValue("beginDate") != null && reportForm.getStringValue("beginDate").length() > 0) ? reportForm.getStringValue("beginDate") : "";
/* 3866 */     if (beginDate2.equalsIgnoreCase(""))
/* 3867 */       beginDate2 = "01/01/1900"; 
/* 3868 */     beginDate2 = String.valueOf(beginDate2) + " 00:00:00";
/*      */     
/* 3870 */     String endDate2 = "";
/* 3871 */     endDate2 = (reportForm.getStringValue("endDate") != null && reportForm.getStringValue("endDate").length() > 0) ? reportForm.getStringValue("endDate") : "";
/* 3872 */     if (endDate2.equalsIgnoreCase(""))
/* 3873 */       endDate2 = "01/01/2200"; 
/* 3874 */     endDate2 = String.valueOf(endDate2) + " 23:59:59";
/*      */     
/* 3876 */     if (!beginDate2.equalsIgnoreCase("") || !endDate2.equalsIgnoreCase("")) {
/* 3877 */       query.append(" AND (");
/*      */     }
/* 3879 */     if (!beginDate2.equalsIgnoreCase("")) {
/* 3880 */       query.append(" headeraudit.street_date >= '" + MilestoneHelper.escapeSingleQuotes(beginDate2) + "'");
/*      */     }
/* 3882 */     if (!endDate2.equalsIgnoreCase(""))
/*      */     {
/* 3884 */       if (!beginDate2.equalsIgnoreCase("")) {
/* 3885 */         query.append(" AND headeraudit.street_date <= '" + MilestoneHelper.escapeSingleQuotes(endDate2) + "'");
/*      */       } else {
/* 3887 */         query.append(" headeraudit.street_date <= '" + MilestoneHelper.escapeSingleQuotes(endDate2) + "'");
/*      */       } 
/*      */     }
/* 3890 */     if (!beginDate2.equalsIgnoreCase("") || !endDate2.equalsIgnoreCase("")) query.append(")");
/*      */ 
/*      */     
/* 3893 */     query.append(" ORDER BY headeraudit.street_date, headeraudit.configuration, headeraudit.selection_no");
/*      */     
/* 3895 */     JdbcConnector connector = MilestoneHelper.getConnector(query.toString());
/* 3896 */     connector.setForwardOnly(false);
/* 3897 */     connector.runQuery();
/* 3898 */     int totalCount = 0;
/* 3899 */     int tenth = 0;
/* 3900 */     totalCount = connector.getRowCount();
/*      */ 
/*      */     
/* 3903 */     tenth = totalCount / 5;
/*      */     
/* 3905 */     if (tenth < 1) {
/* 3906 */       tenth = 1;
/*      */     }
/*      */     
/*      */     try {
/* 3910 */       HttpServletResponse sresponse = context.getResponse();
/* 3911 */       context.putDelivery("status", new String("start_gathering"));
/* 3912 */       context.putDelivery("percent", new String("10"));
/* 3913 */       if (!ReportingServices.usingReportServicesByContext(context))
/*      */       {
/* 3915 */         context.includeJSP("status.jsp", "hiddenFrame");
/* 3916 */         sresponse.setContentType("text/plain");
/* 3917 */         sresponse.flushBuffer();
/*      */       }
/*      */     
/* 3920 */     } catch (Exception exception) {}
/*      */ 
/*      */ 
/*      */     
/* 3924 */     int recordCount = 0;
/* 3925 */     int count = 0;
/*      */ 
/*      */     
/* 3928 */     while (connector.more()) {
/*      */ 
/*      */       
/*      */       try {
/* 3932 */         if (count < recordCount / tenth) {
/*      */           
/* 3934 */           count = recordCount / tenth;
/* 3935 */           HttpServletResponse sresponse = context.getResponse();
/* 3936 */           context.putDelivery("status", new String("start_gathering"));
/* 3937 */           context.putDelivery("percent", new String(String.valueOf(count * 10)));
/* 3938 */           if (!ReportingServices.usingReportServicesByContext(context)) {
/*      */             
/* 3940 */             context.includeJSP("status.jsp", "hiddenFrame");
/* 3941 */             sresponse.setContentType("text/plain");
/* 3942 */             sresponse.flushBuffer();
/*      */           } 
/*      */         } 
/*      */         
/* 3946 */         recordCount++;
/*      */ 
/*      */         
/* 3949 */         selection = new Selection();
/*      */ 
/*      */         
/* 3952 */         selection.setSelectionID(connector.getIntegerField("release_id"));
/*      */         
/* 3954 */         selection.setTitle(connector.getField("title", ""));
/*      */ 
/*      */         
/* 3957 */         selection.setArtistFirstName(connector.getField("artist_first_name", ""));
/*      */ 
/*      */         
/* 3960 */         selection.setArtistLastName(connector.getField("artist_last_name", ""));
/*      */ 
/*      */         
/* 3963 */         selection.setArtist(connector.getField("artist", ""));
/*      */ 
/*      */         
/* 3966 */         String streetDateString = connector.getFieldByName("street_date");
/* 3967 */         if (streetDateString != null) {
/* 3968 */           selection.setStreetDate(MilestoneHelper.getDatabaseDate(streetDateString));
/*      */         }
/* 3970 */         selection.setUpc(connector.getField("upc", ""));
/*      */         
/* 3972 */         selection.setSelectionConfig(
/* 3973 */             getSelectionConfigObject(connector.getField("configuration"), 
/* 3974 */               Cache.getSelectionConfigs()));
/*      */ 
/*      */ 
/*      */         
/* 3978 */         selection.setSelectionSubConfig(getSelectionSubConfigObject(connector.getField("sub_configuration"), selection.getSelectionConfig()));
/* 3979 */         selection.setPrefixID((PrefixCode)getLookupObject(connector.getField("prefix"), Cache.getPrefixCodes()));
/* 3980 */         selection.setSelectionNo(connector.getField("selection_no"));
/* 3981 */         selection.setSelectionStatus((SelectionStatus)getLookupObject(connector.getField("status"), Cache.getSelectionStatusList()));
/* 3982 */         selection.setLabel((Label)MilestoneHelper.getStructureObject(connector.getInt("label_id")));
/*      */ 
/*      */         
/* 3985 */         selection.setImprint(connector.getField("imprint", ""));
/* 3986 */         selection.setReleaseFamilyId(connector.getInt("release_family_id"));
/*      */ 
/*      */         
/* 3989 */         String auditDateString = connector.getFieldByName("audit_date");
/* 3990 */         if (auditDateString != null) {
/* 3991 */           auditDateString = String.valueOf(auditDateString) + ".0";
/* 3992 */           selection.setAuditDate(MilestoneHelper.getDatabaseDate(auditDateString));
/*      */         } 
/* 3994 */         selection.setAuditPrefixID((PrefixCode)getLookupObject(connector.getField("audit_prefix"), Cache.getPrefixCodes()));
/* 3995 */         selection.setAuditSelectionNo(connector.getField("audit_selection_no"));
/* 3996 */         selection.setAuditUPC(connector.getField("audit_upc"));
/* 3997 */         selection.setAuditSelectionStatus((SelectionStatus)getLookupObject(connector.getField("audit_status"), Cache.getSelectionStatusList()));
/*      */         
/* 3999 */         String auditStreetDateString = connector.getFieldByName("audit_street_date");
/* 4000 */         if (auditStreetDateString != null) {
/* 4001 */           auditStreetDateString = String.valueOf(auditStreetDateString) + ".0";
/* 4002 */           selection.setAuditStreetDate(MilestoneHelper.getDatabaseDate(auditStreetDateString));
/*      */         } 
/*      */ 
/*      */         
/* 4006 */         boolean addToSelection = true;
/* 4007 */         String strDistribution = (reportForm.getStringValue("distribution") != null) ? reportForm.getStringValue("distribution") : "";
/* 4008 */         if (!strDistribution.equalsIgnoreCase("") && !strDistribution.equalsIgnoreCase("All")) {
/* 4009 */           String selDistribution = "";
/* 4010 */           if (selection.getLabel().getDistribution() == 1) {
/* 4011 */             selDistribution = "East";
/*      */           }
/* 4013 */           else if (selection.getLabel().getDistribution() == 0) {
/* 4014 */             selDistribution = "West";
/*      */           } 
/* 4016 */           if (!selDistribution.equalsIgnoreCase(strDistribution.trim())) {
/* 4017 */             addToSelection = false;
/*      */           }
/*      */         } 
/* 4020 */         if (addToSelection) {
/* 4021 */           precache.add(selection);
/*      */         }
/* 4023 */         selection = null;
/*      */         
/* 4025 */         connector.next();
/*      */       
/*      */       }
/* 4028 */       catch (Exception e) {
/*      */         
/* 4030 */         System.out.println("**** Exception: " + e.toString());
/* 4031 */         connector.next();
/*      */       } 
/*      */     } 
/*      */     
/* 4035 */     connector.close();
/* 4036 */     company = null;
/*      */     
/* 4038 */     return precache;
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
/*      */   
/*      */   public static Vector getSelectionsForPhysicalProductActivityReportMultiples(Context context, int reportProductType, String activity) {
/* 4061 */     Vector companies = MilestoneHelper.getUserCompanies(context);
/* 4062 */     Company company = null;
/* 4063 */     Vector precache = new Vector();
/* 4064 */     Selection selection = null;
/* 4065 */     StringBuffer query = new StringBuffer(300);
/*      */ 
/*      */ 
/*      */     
/* 4069 */     Form reportForm = (Form)context.getSessionValue("reportForm");
/* 4070 */     query.append("SELECT DISTINCT header.[release_id]");
/* 4071 */     query.append(" FROM Release_Header_Audit header with (nolock) WHERE ");
/*      */     
/* 4073 */     String[] strFamily = ReportSelectionsHelper.getMultiSelectionListValues("family", reportForm);
/* 4074 */     String[] strEnvironment = ReportSelectionsHelper.getMultiSelectionListValues("environment", reportForm);
/* 4075 */     String[] strCompany = ReportSelectionsHelper.getMultiSelectionListValues("company", reportForm);
/* 4076 */     String[] strLabel = ReportSelectionsHelper.getMultiSelectionListValues("Label", reportForm);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 4088 */     ReportSelectionsHelper.addMultSelectionToStringBuffer(query, "header.environment_id", strEnvironment, true, "environment", reportForm, true, false);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 4095 */     ReportSelectionsHelper.addMultSelectionToStringBuffer(query, "header.company_id", strCompany, true, "company", reportForm, false, true);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 4101 */     ReportSelectionsHelper.addReleasingFamilyLabelFamilyMultSelect("family", context, query, reportForm);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 4108 */     ReportSelectionsHelper.addMultSelectionToStringBuffer(query, "header.label_id", strLabel, true, "Label", reportForm, false, true);
/*      */     
/* 4110 */     query.append(" AND header.digital_flag = 0 ");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 4117 */     String[] strConfiguration = null;
/*      */     try {
/* 4119 */       MilestoneFormDropDownMenu configList = (MilestoneFormDropDownMenu)reportForm.getElement("configurationList");
/* 4120 */       if (configList != null) {
/* 4121 */         ArrayList configListAl = configList.getStringValues();
/* 4122 */         if (configListAl != null) {
/*      */           
/* 4124 */           strConfiguration = new String[configListAl.size()];
/* 4125 */           strConfiguration = (String[])configListAl.toArray(strConfiguration);
/*      */         } 
/*      */       } 
/* 4128 */     } catch (Exception e) {
/* 4129 */       e.printStackTrace();
/* 4130 */       System.out.println("<<< Subconfiguration vector execption " + e.getMessage());
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 4138 */     String[] strSubconfiguration = null;
/*      */     try {
/* 4140 */       MilestoneFormDropDownMenu subconfigList = (MilestoneFormDropDownMenu)reportForm.getElement("subconfigurationList");
/* 4141 */       if (subconfigList != null) {
/* 4142 */         ArrayList subconfigListAl = subconfigList.getStringValues();
/* 4143 */         if (subconfigListAl != null) {
/*      */           
/* 4145 */           strSubconfiguration = new String[subconfigListAl.size()];
/* 4146 */           strSubconfiguration = (String[])subconfigListAl.toArray(strSubconfiguration);
/*      */         } 
/*      */       } 
/* 4149 */     } catch (Exception e) {
/* 4150 */       e.printStackTrace();
/* 4151 */       System.out.println("<<< Subconfiguration vector execption " + e.getMessage());
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 4158 */     if (strSubconfiguration != null && 
/* 4159 */       !strSubconfiguration[0].trim().equalsIgnoreCase("0") && 
/* 4160 */       !strSubconfiguration[0].trim().equalsIgnoreCase("") && 
/* 4161 */       !strSubconfiguration[0].trim().equalsIgnoreCase("all")) {
/*      */ 
/*      */       
/* 4164 */       boolean addOr = false;
/* 4165 */       query.append(" AND (");
/* 4166 */       for (int x = 0; x < strSubconfiguration.length; x++) {
/* 4167 */         if (addOr)
/* 4168 */           query.append(" OR "); 
/* 4169 */         String txtvalue = strSubconfiguration[x];
/* 4170 */         if (txtvalue.indexOf("[") != -1) {
/*      */           
/* 4172 */           String txtconfigcode = txtvalue.substring(1, txtvalue.indexOf("]"));
/* 4173 */           String txtsubconfigcode = txtvalue.substring(txtvalue.indexOf("]") + 1, txtvalue.length());
/* 4174 */           query.append(" (header.[configuration] = '" + txtconfigcode.trim() + "' AND ");
/* 4175 */           query.append(" header.[sub_configuration] = '" + txtsubconfigcode.trim() + "') ");
/*      */         } else {
/*      */           
/* 4178 */           query.append(" header.[sub_configuration] = '" + txtvalue.trim() + "' ");
/*      */         } 
/* 4180 */         addOr = true;
/*      */       } 
/* 4182 */       query.append(") ");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     }
/* 4191 */     else if (strConfiguration != null && !strConfiguration[0].equalsIgnoreCase("") && !strConfiguration[0].equalsIgnoreCase("0") && 
/* 4192 */       !strConfiguration[0].equalsIgnoreCase("all")) {
/*      */       
/* 4194 */       boolean addOr = false;
/* 4195 */       query.append(" AND (");
/* 4196 */       for (int x = 0; x < strConfiguration.length; x++) {
/* 4197 */         if (addOr) {
/* 4198 */           query.append(" OR ");
/*      */         }
/* 4200 */         query.append(" header.[configuration] = '" + strConfiguration[x].trim() + "' ");
/* 4201 */         addOr = true;
/*      */       } 
/* 4203 */       query.append(") ");
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 4213 */     if (activity.equals("cancels")) {
/* 4214 */       query.append(" AND (header.status = 'CANCEL') ");
/*      */     }
/*      */     
/* 4217 */     String beginDate = "";
/* 4218 */     beginDate = (reportForm.getStringValue("beginEffectiveDate") != null && reportForm.getStringValue("beginEffectiveDate").length() > 0) ? reportForm.getStringValue("beginEffectiveDate") : "";
/* 4219 */     if (beginDate.equalsIgnoreCase(""))
/* 4220 */       beginDate = "01/01/1900"; 
/* 4221 */     beginDate = String.valueOf(beginDate) + " 00:00:00";
/*      */     
/* 4223 */     String endDate = "";
/* 4224 */     endDate = (reportForm.getStringValue("endEffectiveDate") != null && reportForm.getStringValue("endEffectiveDate").length() > 0) ? reportForm.getStringValue("endEffectiveDate") : "";
/* 4225 */     if (endDate.equalsIgnoreCase(""))
/* 4226 */       endDate = "01/01/2200"; 
/* 4227 */     endDate = String.valueOf(endDate) + " 23:59:59";
/*      */     
/* 4229 */     if (!beginDate.equalsIgnoreCase("") || !endDate.equalsIgnoreCase("")) {
/* 4230 */       query.append(" AND (");
/*      */     }
/* 4232 */     String audittableName = "header";
/* 4233 */     String reltableName = "header";
/*      */     
/* 4235 */     if (!beginDate.equalsIgnoreCase("")) {
/* 4236 */       query.append(" " + audittableName + ".audit_date >= '" + MilestoneHelper.escapeSingleQuotes(beginDate) + "'");
/*      */     }
/* 4238 */     if (!endDate.equalsIgnoreCase("")) {
/* 4239 */       if (!beginDate.equalsIgnoreCase("")) {
/* 4240 */         query.append(" AND " + audittableName + ".audit_date <= '" + MilestoneHelper.escapeSingleQuotes(endDate) + "'");
/*      */       } else {
/* 4242 */         query.append(" " + audittableName + ".audit_date <= '" + MilestoneHelper.escapeSingleQuotes(endDate) + "'");
/*      */       } 
/*      */     }
/* 4245 */     if (!beginDate.equalsIgnoreCase("") || !endDate.equalsIgnoreCase("")) query.append(")");
/*      */ 
/*      */ 
/*      */     
/* 4249 */     String beginDate2 = "";
/* 4250 */     beginDate2 = (reportForm.getStringValue("beginDate") != null && reportForm.getStringValue("beginDate").length() > 0) ? reportForm.getStringValue("beginDate") : "";
/* 4251 */     if (beginDate2.equalsIgnoreCase(""))
/* 4252 */       beginDate2 = "01/01/1900"; 
/* 4253 */     beginDate2 = String.valueOf(beginDate2) + " 00:00:00";
/*      */     
/* 4255 */     String endDate2 = "";
/* 4256 */     endDate2 = (reportForm.getStringValue("endDate") != null && reportForm.getStringValue("endDate").length() > 0) ? reportForm.getStringValue("endDate") : "";
/* 4257 */     if (endDate2.equalsIgnoreCase(""))
/* 4258 */       endDate2 = "01/01/2200"; 
/* 4259 */     endDate2 = String.valueOf(endDate2) + " 23:59:59";
/*      */     
/* 4261 */     if (!beginDate2.equalsIgnoreCase("") || !endDate2.equalsIgnoreCase("")) {
/* 4262 */       query.append(" AND (");
/*      */     }
/* 4264 */     if (!beginDate2.equalsIgnoreCase("")) {
/* 4265 */       query.append(String.valueOf(reltableName) + ".street_date >= '" + MilestoneHelper.escapeSingleQuotes(beginDate2) + "'");
/*      */     }
/* 4267 */     if (!endDate2.equalsIgnoreCase(""))
/*      */     {
/* 4269 */       if (!beginDate2.equalsIgnoreCase("")) {
/* 4270 */         query.append(" AND " + reltableName + ".street_date <= '" + MilestoneHelper.escapeSingleQuotes(endDate2) + "'");
/*      */       } else {
/* 4272 */         query.append(String.valueOf(reltableName) + ".street_date <= '" + MilestoneHelper.escapeSingleQuotes(endDate2) + "'");
/*      */       } 
/*      */     }
/* 4275 */     if (!beginDate2.equalsIgnoreCase("") || !endDate2.equalsIgnoreCase("")) query.append(")");
/*      */ 
/*      */     
/* 4278 */     JdbcConnector connector = MilestoneHelper.getConnector(query.toString());
/* 4279 */     connector.setForwardOnly(false);
/* 4280 */     connector.runQuery();
/*      */     
/* 4282 */     Vector validReleaseIDs = new Vector();
/*      */ 
/*      */     
/* 4285 */     while (connector.more()) {
/*      */ 
/*      */       
/*      */       try {
/* 4289 */         validReleaseIDs.add(connector.getField("release_id"));
/* 4290 */         connector.next();
/*      */       }
/* 4292 */       catch (Exception e) {
/*      */         
/* 4294 */         System.out.println("**** Exception: " + e.toString());
/* 4295 */         connector.next();
/*      */       } 
/*      */     } 
/*      */     
/* 4299 */     connector.close();
/* 4300 */     company = null;
/*      */ 
/*      */ 
/*      */     
/* 4304 */     if (validReleaseIDs.size() == 0) {
/* 4305 */       return precache;
/*      */     }
/*      */ 
/*      */     
/* 4309 */     StringBuffer query2 = new StringBuffer(300);
/* 4310 */     query2.append("SELECT header.[release_id], header.[artist],  header.[title], header.[artist_first_name], header.[artist_last_name],  header.[artist_first_name] + ' ' + header.[artist_last_name] AS fl_artist, header.[configuration], header.[street_date],  header.[sub_configuration], header.[upc], header.[prefix], header.[selection_no],  header.[status], header.[family_id], header.[release_family_id],  header.[imprint], header.[company_id], header.[label_id] ");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 4317 */     query2.append(" FROM Release_Header header with (nolock) ");
/*      */     
/* 4319 */     String relIdString = "";
/* 4320 */     relIdString = "(";
/* 4321 */     for (int i = 0; i < validReleaseIDs.size(); i++) {
/* 4322 */       relIdString = String.valueOf(relIdString) + validReleaseIDs.elementAt(i);
/* 4323 */       if (i + 1 < validReleaseIDs.size())
/* 4324 */         relIdString = String.valueOf(relIdString) + ", "; 
/*      */     } 
/* 4326 */     relIdString = String.valueOf(relIdString) + ")";
/*      */     
/* 4328 */     query2.append(" WHERE header.release_id IN " + relIdString);
/*      */ 
/*      */ 
/*      */     
/* 4332 */     String strLabelContact = (reportForm.getStringValue("contact") != null) ? reportForm.getStringValue("contact") : "";
/* 4333 */     if (!strLabelContact.equals("") && !strLabelContact.equals("0")) {
/* 4334 */       query2.append(" AND (header.contact_id = '" + strLabelContact.trim() + "') ");
/*      */     }
/*      */ 
/*      */     
/* 4338 */     String strReleaseType = (reportForm.getStringValue("releaseType") != null) ? reportForm.getStringValue("releaseType") : "";
/* 4339 */     if (!strReleaseType.equalsIgnoreCase("") && !strReleaseType.equalsIgnoreCase("all"))
/*      */     {
/* 4341 */       if (strReleaseType.equalsIgnoreCase("commercial")) {
/* 4342 */         query2.append(" AND (header.[release_type] ='CO') ");
/*      */       } else {
/* 4344 */         query2.append(" AND (header.[release_type] ='PR') ");
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 4351 */     if (activity.equals("cancels")) {
/* 4352 */       query.append(" AND (header.status = 'CANCEL') ");
/*      */     } else {
/* 4354 */       ReportSelectionsHelper.addStatusToSelect(reportForm, query2);
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 4359 */     connector = MilestoneHelper.getConnector(query2.toString());
/* 4360 */     connector.setForwardOnly(false);
/* 4361 */     connector.runQuery();
/* 4362 */     int totalCount = 0;
/* 4363 */     int tenth = 0;
/* 4364 */     totalCount = connector.getRowCount();
/*      */ 
/*      */     
/* 4367 */     tenth = totalCount / 5;
/*      */     
/* 4369 */     if (tenth < 1) {
/* 4370 */       tenth = 1;
/*      */     }
/*      */     
/*      */     try {
/* 4374 */       HttpServletResponse sresponse = context.getResponse();
/* 4375 */       context.putDelivery("status", new String("start_gathering"));
/* 4376 */       context.putDelivery("percent", new String("10"));
/* 4377 */       if (!ReportingServices.usingReportServicesByContext(context))
/*      */       {
/* 4379 */         context.includeJSP("status.jsp", "hiddenFrame");
/* 4380 */         sresponse.setContentType("text/plain");
/* 4381 */         sresponse.flushBuffer();
/*      */       }
/*      */     
/* 4384 */     } catch (Exception exception) {}
/*      */ 
/*      */ 
/*      */     
/* 4388 */     int recordCount = 0;
/* 4389 */     int count = 0;
/*      */ 
/*      */     
/* 4392 */     while (connector.more()) {
/*      */ 
/*      */       
/*      */       try {
/* 4396 */         if (count < recordCount / tenth) {
/*      */           
/* 4398 */           count = recordCount / tenth;
/* 4399 */           HttpServletResponse sresponse = context.getResponse();
/* 4400 */           context.putDelivery("status", new String("start_gathering"));
/* 4401 */           context.putDelivery("percent", new String(String.valueOf(count * 10)));
/* 4402 */           if (!ReportingServices.usingReportServicesByContext(context)) {
/*      */             
/* 4404 */             context.includeJSP("status.jsp", "hiddenFrame");
/* 4405 */             sresponse.setContentType("text/plain");
/* 4406 */             sresponse.flushBuffer();
/*      */           } 
/*      */         } 
/*      */         
/* 4410 */         recordCount++;
/*      */         
/* 4412 */         selection = new Selection();
/*      */         
/* 4414 */         selection.setSelectionID(connector.getIntegerField("release_id"));
/* 4415 */         selection.setTitle(connector.getField("title", ""));
/* 4416 */         selection.setArtistFirstName(connector.getField("artist_first_name", ""));
/* 4417 */         selection.setArtistLastName(connector.getField("artist_last_name", ""));
/* 4418 */         selection.setArtist(connector.getField("artist", ""));
/*      */         
/* 4420 */         String streetDateString = connector.getFieldByName("street_date");
/* 4421 */         if (streetDateString != null)
/* 4422 */           selection.setStreetDate(MilestoneHelper.getDatabaseDate(streetDateString)); 
/* 4423 */         selection.setUpc(connector.getField("upc", ""));
/* 4424 */         selection.setSelectionConfig(
/* 4425 */             getSelectionConfigObject(connector.getField("configuration"), 
/* 4426 */               Cache.getSelectionConfigs()));
/*      */         
/* 4428 */         selection.setSelectionSubConfig(getSelectionSubConfigObject(connector.getField("sub_configuration"), selection.getSelectionConfig()));
/* 4429 */         selection.setPrefixID((PrefixCode)getLookupObject(connector.getField("prefix"), Cache.getPrefixCodes()));
/* 4430 */         selection.setSelectionNo(connector.getField("selection_no"));
/* 4431 */         selection.setSelectionStatus((SelectionStatus)getLookupObject(connector.getField("status"), Cache.getSelectionStatusList()));
/* 4432 */         selection.setLabel((Label)MilestoneHelper.getStructureObject(connector.getInt("label_id")));
/* 4433 */         selection.setImprint(connector.getField("imprint", ""));
/* 4434 */         selection.setReleaseFamilyId(connector.getInt("release_family_id"));
/*      */ 
/*      */ 
/*      */         
/* 4438 */         boolean addToSelection = true;
/* 4439 */         String strDistribution = (reportForm.getStringValue("distribution") != null) ? reportForm.getStringValue("distribution") : "";
/* 4440 */         if (!strDistribution.equalsIgnoreCase("") && !strDistribution.equalsIgnoreCase("All")) {
/* 4441 */           String selDistribution = "";
/* 4442 */           if (selection.getLabel().getDistribution() == 1) {
/* 4443 */             selDistribution = "East";
/*      */           }
/* 4445 */           else if (selection.getLabel().getDistribution() == 0) {
/* 4446 */             selDistribution = "West";
/*      */           } 
/* 4448 */           if (!selDistribution.equalsIgnoreCase(strDistribution.trim())) {
/* 4449 */             addToSelection = false;
/*      */           }
/*      */         } 
/* 4452 */         if (addToSelection) {
/* 4453 */           precache.add(selection);
/*      */         }
/* 4455 */         selection = null;
/* 4456 */         connector.next();
/*      */       
/*      */       }
/* 4459 */       catch (Exception e) {
/*      */         
/* 4461 */         System.out.println("**** Exception: " + e.toString());
/* 4462 */         connector.next();
/*      */       } 
/*      */     } 
/*      */     
/* 4466 */     connector.close();
/*      */     
/* 4468 */     if (activity.equals("changes"))
/* 4469 */       return getAuditChangeSelections(precache, beginDate, endDate, "changes"); 
/* 4470 */     if (activity.equals("moves")) {
/* 4471 */       return getAuditChangeSelections(precache, beginDate, endDate, "moves");
/*      */     }
/* 4473 */     return getAuditChangeSelections(precache, beginDate, endDate, "cancels");
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
/*      */   public static Vector getAuditChangeSelections(Vector releaseHeaderVector, String beginAuditDate, String endAuditDate, String activity) {
/* 4488 */     Vector auditSelections = new Vector();
/* 4489 */     Vector releaseIdsChecked = new Vector();
/* 4490 */     JdbcConnector connector = new JdbcConnector();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 4497 */     String auditResults = "";
/*      */     
/* 4499 */     for (int i = 0; i < releaseHeaderVector.size(); i++) {
/*      */       
/* 4501 */       Selection sel = (Selection)releaseHeaderVector.elementAt(i);
/*      */ 
/*      */       
/* 4504 */       String selId = Integer.toString(sel.getSelectionID());
/* 4505 */       if (!releaseIdsChecked.contains(selId)) {
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 4510 */         releaseIdsChecked.add(selId);
/*      */ 
/*      */         
/* 4513 */         String query = "";
/* 4514 */         if (activity.equals("changes")) {
/*      */ 
/*      */           
/* 4517 */           query = "sp_get_audit_rows_change " + sel.getSelectionID() + 
/* 4518 */             ",'" + beginAuditDate + "'," + 
/* 4519 */             "'" + endAuditDate + "'";
/*      */         }
/* 4521 */         else if (activity.equals("cancels")) {
/*      */ 
/*      */           
/* 4524 */           query = "sp_get_audit_rows_cancel " + sel.getSelectionID() + 
/* 4525 */             ",'" + beginAuditDate + "'," + 
/* 4526 */             "'" + endAuditDate + "'";
/*      */         }
/*      */         else {
/*      */           
/* 4530 */           query = "sp_get_audit_rows_move " + sel.getSelectionID() + 
/* 4531 */             ",'" + beginAuditDate + "'," + 
/* 4532 */             "'" + endAuditDate + "'";
/*      */         } 
/*      */         
/* 4535 */         connector.setQuery(query);
/* 4536 */         connector.runQuery();
/* 4537 */         auditResults = connector.getField("ReturnString");
/*      */ 
/*      */         
/* 4540 */         if (!auditResults.equals("")) {
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 4545 */           StringTokenizer st = new StringTokenizer(auditResults, "|");
/* 4546 */           Vector auditValues = new Vector();
/* 4547 */           while (st.hasMoreTokens()) {
/* 4548 */             auditValues.add(st.nextToken());
/*      */           }
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 4554 */           if (activity.equals("changes")) {
/* 4555 */             buildAuditSelectionsChanges(sel, auditValues, auditSelections);
/*      */           }
/* 4557 */           else if (activity.equals("cancels")) {
/* 4558 */             buildAuditSelectionsCancels(sel, auditValues, auditSelections);
/*      */           } else {
/* 4560 */             buildAuditSelectionsMoves(sel, auditValues, auditSelections);
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/* 4565 */     connector.close();
/* 4566 */     return auditSelections;
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
/*      */   public static void buildAuditSelectionsCancels(Selection sel, Vector auditValues, Vector auditSelections) {
/* 4583 */     String finalAuditDate = "";
/*      */     
/* 4585 */     for (int i = 0; i < auditValues.size(); i++) {
/*      */       
/* 4587 */       finalAuditDate = checkForX((String)auditValues.elementAt(i));
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 4592 */       if (i > 0) {
/*      */         try {
/* 4594 */           Selection copiedSelection = (Selection)sel.clone();
/*      */           
/* 4596 */           if (finalAuditDate != null) {
/*      */             
/* 4598 */             finalAuditDate = String.valueOf(finalAuditDate) + ".0";
/* 4599 */             copiedSelection.setAuditDate(MilestoneHelper.getDatabaseDate(finalAuditDate));
/*      */           } 
/*      */ 
/*      */           
/* 4603 */           auditSelections.add(copiedSelection);
/*      */         }
/* 4605 */         catch (CloneNotSupportedException e) {
/* 4606 */           System.out.println("clone exception in ReportSelections.buildAuditSelectionsCancels()");
/*      */         } 
/*      */       } else {
/*      */         
/* 4610 */         if (finalAuditDate != null) {
/*      */           
/* 4612 */           finalAuditDate = String.valueOf(finalAuditDate) + ".0";
/* 4613 */           sel.setAuditDate(MilestoneHelper.getDatabaseDate(finalAuditDate));
/*      */         } 
/*      */ 
/*      */         
/* 4617 */         auditSelections.add(sel);
/*      */       } 
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
/*      */ 
/*      */ 
/*      */   
/*      */   public static void buildAuditSelectionsChanges(Selection sel, Vector auditValues, Vector auditSelections) {
/* 4638 */     String initalPrefix = "";
/* 4639 */     String finalPrefix = "";
/* 4640 */     String initalSelection_no = "";
/* 4641 */     String finalSelection_no = "";
/* 4642 */     String initalAuditDate = "";
/* 4643 */     String finalAuditDate = "";
/*      */     
/* 4645 */     for (int i = 0; i < auditValues.size(); i += 6) {
/*      */       
/* 4647 */       initalPrefix = checkForX((String)auditValues.elementAt(i));
/* 4648 */       initalSelection_no = checkForX((String)auditValues.elementAt(i + 1));
/* 4649 */       initalAuditDate = checkForX((String)auditValues.elementAt(i + 2));
/* 4650 */       finalPrefix = checkForX((String)auditValues.elementAt(i + 3));
/* 4651 */       finalSelection_no = checkForX((String)auditValues.elementAt(i + 4));
/* 4652 */       finalAuditDate = checkForX((String)auditValues.elementAt(i + 5));
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 4657 */       if (i > 0) {
/*      */         try {
/* 4659 */           Selection copiedSelection = (Selection)sel.clone();
/*      */           
/* 4661 */           copiedSelection.setPrefixID((PrefixCode)SelectionManager.getLookupObject(finalPrefix, Cache.getPrefixCodes()));
/* 4662 */           copiedSelection.setAuditPrefixID((PrefixCode)SelectionManager.getLookupObject(initalPrefix, Cache.getPrefixCodes()));
/* 4663 */           copiedSelection.setSelectionNo(finalSelection_no);
/* 4664 */           copiedSelection.setAuditSelectionNo(initalSelection_no);
/* 4665 */           if (finalAuditDate != null) {
/*      */             
/* 4667 */             finalAuditDate = String.valueOf(finalAuditDate) + ".0";
/* 4668 */             copiedSelection.setAuditDate(MilestoneHelper.getDatabaseDate(finalAuditDate));
/*      */           } 
/*      */ 
/*      */           
/* 4672 */           auditSelections.add(copiedSelection);
/*      */         }
/* 4674 */         catch (CloneNotSupportedException e) {
/* 4675 */           System.out.println("clone exception in ReportSelections.buildAuditSelectionsChanges()");
/*      */         } 
/*      */       } else {
/*      */         
/* 4679 */         sel.setPrefixID((PrefixCode)SelectionManager.getLookupObject(finalPrefix, Cache.getPrefixCodes()));
/* 4680 */         sel.setAuditPrefixID((PrefixCode)SelectionManager.getLookupObject(initalPrefix, Cache.getPrefixCodes()));
/* 4681 */         sel.setSelectionNo(finalSelection_no);
/* 4682 */         sel.setAuditSelectionNo(initalSelection_no);
/* 4683 */         if (finalAuditDate != null) {
/*      */           
/* 4685 */           finalAuditDate = String.valueOf(finalAuditDate) + ".0";
/* 4686 */           sel.setAuditDate(MilestoneHelper.getDatabaseDate(finalAuditDate));
/*      */         } 
/*      */ 
/*      */         
/* 4690 */         auditSelections.add(sel);
/*      */       } 
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
/*      */ 
/*      */   
/*      */   public static void buildAuditSelectionsMoves(Selection sel, Vector auditValues, Vector auditSelections) {
/* 4710 */     String initalStreetDate = "";
/* 4711 */     String initalAuditDate = "";
/* 4712 */     String initialStatus = "";
/*      */     
/* 4714 */     String finalSteetDate = "";
/* 4715 */     String finalAuditDate = "";
/* 4716 */     String finalStatus = "";
/*      */     
/* 4718 */     for (int i = 0; i < auditValues.size(); i += 6) {
/*      */       
/* 4720 */       initialStatus = (String)auditValues.elementAt(i);
/* 4721 */       initalStreetDate = (String)auditValues.elementAt(i + 1);
/* 4722 */       initalAuditDate = (String)auditValues.elementAt(i + 2);
/* 4723 */       finalStatus = (String)auditValues.elementAt(i + 3);
/* 4724 */       finalSteetDate = (String)auditValues.elementAt(i + 4);
/* 4725 */       finalAuditDate = (String)auditValues.elementAt(i + 5);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 4730 */       if (i > 0) {
/*      */         try {
/* 4732 */           Selection copiedSelection = (Selection)sel.clone();
/*      */           
/* 4734 */           copiedSelection.setStreetDate(MilestoneHelper.getDatabaseDate(finalSteetDate));
/* 4735 */           if (initalStreetDate != null) {
/* 4736 */             initalStreetDate = String.valueOf(initalStreetDate) + ".0";
/* 4737 */             copiedSelection.setAuditStreetDate(MilestoneHelper.getDatabaseDate(initalStreetDate));
/*      */           } 
/* 4739 */           copiedSelection.setSelectionStatus((SelectionStatus)MilestoneHelper.getLookupObject(finalStatus, Cache.getSelectionStatusList()));
/* 4740 */           copiedSelection.setAuditSelectionStatus((SelectionStatus)MilestoneHelper.getLookupObject(initialStatus, Cache.getSelectionStatusList()));
/* 4741 */           if (finalAuditDate != null) {
/* 4742 */             finalAuditDate = String.valueOf(finalAuditDate) + ".0";
/* 4743 */             copiedSelection.setAuditDate(MilestoneHelper.getDatabaseDate(finalAuditDate));
/*      */           } 
/*      */           
/* 4746 */           auditSelections.add(copiedSelection);
/*      */         }
/* 4748 */         catch (CloneNotSupportedException e) {
/* 4749 */           System.out.println("clone exception in ReportSelections.buildAuditSelectionsChanges()");
/*      */         } 
/*      */       } else {
/*      */         
/* 4753 */         sel.setStreetDate(MilestoneHelper.getDatabaseDate(finalSteetDate));
/* 4754 */         if (initalStreetDate != null) {
/* 4755 */           initalStreetDate = String.valueOf(initalStreetDate) + ".0";
/* 4756 */           sel.setAuditStreetDate(MilestoneHelper.getDatabaseDate(initalStreetDate));
/*      */         } 
/* 4758 */         sel.setAuditSelectionStatus((SelectionStatus)MilestoneHelper.getLookupObject(initialStatus, Cache.getSelectionStatusList()));
/* 4759 */         sel.setSelectionStatus((SelectionStatus)MilestoneHelper.getLookupObject(finalStatus, Cache.getSelectionStatusList()));
/* 4760 */         if (finalAuditDate != null) {
/* 4761 */           finalAuditDate = String.valueOf(finalAuditDate) + ".0";
/* 4762 */           sel.setAuditDate(MilestoneHelper.getDatabaseDate(finalAuditDate));
/*      */         } 
/*      */         
/* 4765 */         auditSelections.add(sel);
/*      */       } 
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
/*      */   public static String checkForX(String toCheck) {
/* 4780 */     if (toCheck.equals("XXX")) {
/* 4781 */       return "";
/*      */     }
/* 4783 */     return toCheck;
/*      */   }
/*      */ }


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\ReportSelections.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */