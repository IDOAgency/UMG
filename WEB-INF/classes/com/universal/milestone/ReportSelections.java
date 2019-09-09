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
/*  673 */     if (ReportingServices.execUsingReportServices(reportname, query, context, reportParms)) {
/*  674 */       return null;
/*      */     }
/*  676 */     System.out.println("\n\n****************\n\n");
/*  677 */     System.out.println(query.toString());
/*  678 */     System.out.println("\n\n****************\n\n");
/*  679 */     JdbcConnector connector = MilestoneHelper.getConnector(query.toString());
/*  680 */     connector.setForwardOnly(false);
/*  681 */     connector.runQuery();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  687 */     int totalCount = 0;
/*  688 */     int tenth = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  696 */     totalCount = connector.getRowCount();
/*      */ 
/*      */     
/*  699 */     tenth = totalCount / 5;
/*      */     
/*  701 */     if (tenth < 1) {
/*  702 */       tenth = 1;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/*  709 */       HttpServletResponse sresponse = context.getResponse();
/*  710 */       context.putDelivery("status", new String("start_gathering"));
/*  711 */       context.putDelivery("percent", new String("10"));
/*  712 */       if (!ReportingServices.usingReportServicesByContext(context))
/*      */       {
/*  714 */         context.includeJSP("status.jsp", "hiddenFrame");
/*  715 */         sresponse.setContentType("text/plain");
/*  716 */         sresponse.flushBuffer();
/*      */       }
/*      */     
/*  719 */     } catch (Exception exception) {}
/*      */ 
/*      */     
/*  722 */     int recordCount = 0;
/*  723 */     int count = 0;
/*      */ 
/*      */     
/*  726 */     while (connector.more()) {
/*      */ 
/*      */       
/*      */       try {
/*  730 */         if (count < recordCount / tenth) {
/*      */           
/*  732 */           count = recordCount / tenth;
/*  733 */           HttpServletResponse sresponse = context.getResponse();
/*  734 */           context.putDelivery("status", new String("start_gathering"));
/*  735 */           context.putDelivery("percent", new String(String.valueOf(count * 10)));
/*  736 */           if (!ReportingServices.usingReportServicesByContext(context)) {
/*      */             
/*  738 */             context.includeJSP("status.jsp", "hiddenFrame");
/*  739 */             sresponse.setContentType("text/plain");
/*  740 */             sresponse.flushBuffer();
/*      */           } 
/*      */         } 
/*      */         
/*  744 */         recordCount++;
/*      */         
/*  746 */         if (reportname.equals("eInitRel")) {
/*      */           
/*  748 */           selection = new Selection();
/*      */ 
/*      */           
/*  751 */           selection.setSelectionID(connector.getIntegerField("release_id"));
/*      */ 
/*      */           
/*  754 */           String impactDateString = connector.getFieldByName("impact_date");
/*  755 */           if (impactDateString != null) {
/*  756 */             selection.setImpactDate(MilestoneHelper.getDatabaseDate(impactDateString));
/*      */           }
/*      */           
/*  759 */           String streetDateString = connector.getFieldByName("street_date");
/*  760 */           if (streetDateString != null) {
/*  761 */             selection.setStreetDate(MilestoneHelper.getDatabaseDate(streetDateString));
/*      */           }
/*      */           
/*  764 */           selection.setArtist(connector.getField("artist", ""));
/*      */ 
/*      */           
/*  767 */           selection.setTitle(connector.getField("title", ""));
/*      */ 
/*      */           
/*  770 */           selection.setUpc(connector.getField("upc", ""));
/*      */ 
/*      */           
/*  773 */           selection.setSelectionPackaging(connector.getField("packaging", ""));
/*      */ 
/*      */           
/*  776 */           selection.setOtherContact(connector.getField("coordinator_contacts", ""));
/*      */ 
/*      */           
/*  779 */           selection.setProductCategory((ProductCategory)getLookupObject(connector.getField("product_line"), 
/*  780 */                 Cache.getProductCategories()));
/*      */           
/*  782 */           selection.setParentalGuidance(connector.getBoolean("parental_indicator"));
/*      */           
/*  784 */           selection.setSelectionTerritory(connector.getField("territory", ""));
/*      */           
/*  786 */           selection.setComments(connector.getField("comments", ""));
/*      */ 
/*      */           
/*  789 */           selection.setImprint(connector.getField("imprint", ""));
/*  790 */           selection.setReleaseFamilyId(connector.getIntegerField("release_family_id"));
/*      */         }
/*  792 */         else if (reportname.equals("ToDoList")) {
/*      */           
/*  794 */           selection = new Selection();
/*      */ 
/*      */           
/*  797 */           selection.setSelectionID(connector.getIntegerField("release_id"));
/*      */           
/*  799 */           selection.setTitle(connector.getField("title", ""));
/*      */ 
/*      */           
/*  802 */           selection.setTaskName(connector.getField("name", ""));
/*      */ 
/*      */           
/*  805 */           selection.setArtist(connector.getField("artist", ""));
/*      */ 
/*      */           
/*  808 */           selection.setDepartment(connector.getField("department", ""));
/*      */ 
/*      */           
/*  811 */           String completionDateString = connector.getFieldByName("completion_date");
/*  812 */           if (completionDateString != null) {
/*  813 */             selection.setCompletionDate(MilestoneHelper.getDatabaseDate(completionDateString));
/*      */           }
/*      */           
/*  816 */           selection.setArtistFirstName(connector.getField("artist_first_name", ""));
/*      */ 
/*      */           
/*  819 */           selection.setArtistLastName(connector.getField("artist_last_name", ""));
/*      */ 
/*      */           
/*  822 */           if (UserManager.getInstance().getUser(connector.getIntegerField("contact_id")) != null) {
/*  823 */             selection.setLabelContact(UserManager.getInstance().getUser(connector.getIntegerField("contact_id")));
/*      */           }
/*      */           
/*  826 */           String dueDateString = connector.getFieldByName("due_date");
/*  827 */           if (dueDateString != null) {
/*  828 */             selection.setDueDate(MilestoneHelper.getDatabaseDate(dueDateString));
/*      */           }
/*      */ 
/*      */           
/*  832 */           String streetDateString = connector.getFieldByName("street_date");
/*  833 */           if (streetDateString != null) {
/*  834 */             selection.setStreetDate(MilestoneHelper.getDatabaseDate(streetDateString));
/*      */           }
/*  836 */           selection.setUpc(connector.getField("upc", ""));
/*      */           
/*  838 */           selection.setSelectionConfig(
/*  839 */               getSelectionConfigObject(connector.getField("configuration"), 
/*  840 */                 Cache.getSelectionConfigs()));
/*      */ 
/*      */ 
/*      */           
/*  844 */           selection.setImprint(connector.getField("imprint", ""));
/*  845 */           selection.setReleaseFamilyId(connector.getIntegerField("release_family_id"));
/*      */ 
/*      */           
/*  848 */           selection.setSelectionSubConfig(getSelectionSubConfigObject(connector.getField("sub_configuration"), selection.getSelectionConfig()));
/*  849 */           selection.setPrefixID((PrefixCode)getLookupObject(connector.getField("prefix"), Cache.getPrefixCodes()));
/*  850 */           selection.setSelectionNo(connector.getField("selection_no"));
/*  851 */           selection.setSelectionStatus((SelectionStatus)getLookupObject(connector.getField("status"), 
/*  852 */                 Cache.getSelectionStatusList()));
/*      */         } else {
/*      */           
/*  855 */           selection = new Selection();
/*      */ 
/*      */           
/*  858 */           selection.setSelectionID(connector.getIntegerField("release_id"));
/*      */           
/*  860 */           selection.setTitle(connector.getField("title", ""));
/*      */ 
/*      */           
/*  863 */           selection.setArtistFirstName(connector.getField("artist_first_name", ""));
/*      */ 
/*      */           
/*  866 */           selection.setArtistLastName(connector.getField("artist_last_name", ""));
/*      */ 
/*      */           
/*  869 */           selection.setFlArtist(connector.getField("fl_artist", ""));
/*      */ 
/*      */           
/*  872 */           String streetDateString = connector.getFieldByName("street_date");
/*  873 */           if (streetDateString != null) {
/*  874 */             selection.setStreetDate(MilestoneHelper.getDatabaseDate(streetDateString));
/*      */           }
/*      */           
/*  877 */           if (reportname.equals("DigProdSch") || reportname.equals("DigProLSch")) {
/*  878 */             String digitalStreetDateString = connector.getFieldByName("digital_rls_date");
/*  879 */             if (digitalStreetDateString != null) {
/*  880 */               selection.setDigitalRlsDate(MilestoneHelper.getDatabaseDate(digitalStreetDateString));
/*      */             }
/*      */           } 
/*  883 */           selection.setUpc(connector.getField("upc", ""));
/*      */           
/*  885 */           selection.setImprint(connector.getField("imprint", ""));
/*  886 */           selection.setReleaseFamilyId(connector.getIntegerField("release_family_id"));
/*      */           
/*  888 */           selection.setSelectionConfig(
/*  889 */               getSelectionConfigObject(connector.getField("configuration"), 
/*  890 */                 Cache.getSelectionConfigs()));
/*      */ 
/*      */ 
/*      */           
/*  894 */           selection.setSelectionSubConfig(getSelectionSubConfigObject(connector.getField("sub_configuration"), selection.getSelectionConfig()));
/*  895 */           selection.setPrefixID((PrefixCode)getLookupObject(connector.getField("prefix"), Cache.getPrefixCodes()));
/*  896 */           selection.setSelectionNo(connector.getField("selection_no"));
/*  897 */           selection.setSelectionStatus((SelectionStatus)getLookupObject(connector.getField("status"), 
/*  898 */                 Cache.getSelectionStatusList()));
/*      */         } 
/*      */ 
/*      */         
/*  902 */         precache.add(selection);
/*  903 */         selection = null;
/*  904 */         connector.next();
/*      */       
/*      */       }
/*  907 */       catch (Exception exception) {}
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  912 */     connector.close();
/*  913 */     company = null;
/*      */     
/*  915 */     return precache;
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
/*  931 */     Vector selectionsForReport = new Vector();
/*  932 */     Form reportForm = (Form)context.getSessionValue("reportForm");
/*      */     
/*  934 */     if (selections == null || selections.size() == 0) {
/*  935 */       return selectionsForReport;
/*      */     }
/*      */     
/*  938 */     Calendar beginStDate = (reportForm.getStringValue("beginDate") != null && reportForm.getStringValue("beginDate").length() > 0) ? MilestoneHelper.getDate(reportForm.getStringValue("beginDate")) : null;
/*  939 */     if (beginStDate == null) {
/*      */       
/*  941 */       beginStDate = Calendar.getInstance();
/*  942 */       beginStDate.setTime(new Date(0L));
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  948 */     beginStDate.set(11, 0);
/*  949 */     beginStDate.set(12, 0);
/*  950 */     beginStDate.set(13, 0);
/*  951 */     beginStDate.set(14, 0);
/*      */     
/*  953 */     Calendar endStDate = (reportForm.getStringValue("endDate") != null && reportForm.getStringValue("endDate").length() > 0) ? MilestoneHelper.getDate(reportForm.getStringValue("endDate")) : null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  960 */     if (endStDate != null) {
/*      */       
/*  962 */       endStDate.set(11, 23);
/*  963 */       endStDate.set(12, 59);
/*  964 */       endStDate.set(13, 59);
/*  965 */       endStDate.set(14, 999);
/*      */     } 
/*      */     
/*  968 */     String strDistribution = (reportForm.getStringValue("distribution") != null) ? reportForm.getStringValue("distribution") : "";
/*  969 */     String strLabelContact = (reportForm.getStringValue("contact") != null) ? reportForm.getStringValue("contact") : "";
/*  970 */     String strUmlContact = (reportForm.getStringValue("umlContact") != null) ? reportForm.getStringValue("umlContact") : "";
/*  971 */     String strFuture2 = (reportForm.getStringValue("Future2") != null) ? reportForm.getStringValue("Future2") : "";
/*      */     
/*  973 */     String strReleaseType = (reportForm.getStringValue("releaseType") != null) ? reportForm.getStringValue("releaseType") : "";
/*  974 */     String strArtist = (reportForm.getStringValue("artist") != null) ? reportForm.getStringValue("artist") : "";
/*      */ 
/*      */ 
/*      */     
/*  978 */     Iterator formIterator = reportForm.getElements();
/*  979 */     Vector distCoIDs = new Vector();
/*  980 */     while (formIterator.hasNext()) {
/*      */ 
/*      */       
/*  983 */       FormElement field = (FormElement)formIterator.next();
/*  984 */       String fieldName = field.getName();
/*  985 */       if (fieldName.startsWith("distCo")) {
/*      */         
/*  987 */         FormCheckBox fCheck = (FormCheckBox)field;
/*  988 */         if (fCheck.isChecked()) {
/*  989 */           distCoIDs.add(fieldName.substring(6, fieldName.length()));
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/*  994 */     String[] strFamily = ReportSelectionsHelper.getMultiSelectionListValues("family", reportForm);
/*      */ 
/*      */     
/*  997 */     String[] strEnvironment = ReportSelectionsHelper.getMultiSelectionListValues("environment", reportForm);
/*  998 */     String[] strCompany = ReportSelectionsHelper.getMultiSelectionListValues("company", reportForm);
/*  999 */     String[] strLabel = ReportSelectionsHelper.getMultiSelectionListValues("Label", reportForm);
/*      */ 
/*      */     
/* 1002 */     String strProductType = (reportForm.getStringValue("ProductType") != null) ? reportForm.getStringValue("ProductType") : "";
/* 1003 */     if (strProductType.equals("")) {
/*      */       
/* 1005 */       strProductType = String.valueOf(reportProductType);
/*      */     }
/* 1007 */     else if (strProductType.equals("Physical")) {
/* 1008 */       strProductType = String.valueOf(0);
/* 1009 */     } else if (strProductType.equals("Digital")) {
/* 1010 */       strProductType = String.valueOf(1);
/* 1011 */     } else if (strProductType.equals("Both")) {
/* 1012 */       strProductType = String.valueOf(2);
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
/* 1024 */     String[] strConfiguration = null;
/*      */     try {
/* 1026 */       MilestoneFormDropDownMenu configList = (MilestoneFormDropDownMenu)reportForm.getElement("configurationList");
/* 1027 */       if (configList != null) {
/* 1028 */         ArrayList configListAl = configList.getStringValues();
/* 1029 */         if (configListAl != null) {
/*      */           
/* 1031 */           strConfiguration = new String[configListAl.size()];
/* 1032 */           strConfiguration = (String[])configListAl.toArray(strConfiguration);
/*      */         } 
/*      */       } 
/* 1035 */     } catch (Exception e) {
/* 1036 */       e.printStackTrace();
/* 1037 */       System.out.println("<<< Configuration vector execption " + e.getMessage());
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1044 */     String[] strSubconfiguration = null;
/*      */     try {
/* 1046 */       MilestoneFormDropDownMenu subconfigList = (MilestoneFormDropDownMenu)reportForm.getElement("subconfigurationList");
/* 1047 */       if (subconfigList != null) {
/* 1048 */         ArrayList subconfigListAl = subconfigList.getStringValues();
/* 1049 */         if (subconfigListAl != null) {
/*      */           
/* 1051 */           strSubconfiguration = new String[subconfigListAl.size()];
/* 1052 */           strSubconfiguration = (String[])subconfigListAl.toArray(strSubconfiguration);
/*      */         } 
/*      */       } 
/* 1055 */     } catch (Exception e) {
/* 1056 */       e.printStackTrace();
/* 1057 */       System.out.println("<<< Subconfiguration vector execption " + e.getMessage());
/*      */     } 
/*      */ 
/*      */     
/* 1061 */     boolean bParent = (reportForm.getElement("ParentsOnly") != null) ? ((FormCheckBox)reportForm.getElement("ParentsOnly")).isChecked() : 0;
/*      */     
/* 1063 */     int totalCount = selections.size();
/*      */     
/* 1065 */     int tenth = 0;
/*      */     
/* 1067 */     tenth = totalCount / 5;
/*      */     
/* 1069 */     if (tenth < 1) {
/* 1070 */       tenth = 1;
/*      */     }
/*      */     
/*      */     try {
/* 1074 */       HttpServletResponse sresponse = context.getResponse();
/* 1075 */       context.putDelivery("status", new String("start_gathering"));
/* 1076 */       context.putDelivery("percent", new String("10"));
/* 1077 */       if (!ReportingServices.usingReportServicesByContext(context))
/*      */       {
/* 1079 */         context.includeJSP("status.jsp", "hiddenFrame");
/* 1080 */         sresponse.setContentType("text/plain");
/* 1081 */         sresponse.flushBuffer();
/*      */       }
/*      */     
/* 1084 */     } catch (Exception exception) {}
/*      */ 
/*      */ 
/*      */     
/* 1088 */     int recordCount = 0;
/* 1089 */     int count = 0;
/*      */ 
/*      */ 
/*      */     
/* 1093 */     for (int i = 0; i < selections.size(); i++) {
/*      */ 
/*      */       
/*      */       try {
/* 1097 */         if (count < recordCount / tenth) {
/*      */           
/* 1099 */           count = recordCount / tenth;
/* 1100 */           HttpServletResponse sresponse = context.getResponse();
/* 1101 */           context.putDelivery("status", new String("start_gathering"));
/* 1102 */           context.putDelivery("percent", new String(String.valueOf(count * 10 + 50)));
/* 1103 */           if (!ReportingServices.usingReportServicesByContext(context)) {
/*      */             
/* 1105 */             context.includeJSP("status.jsp", "hiddenFrame");
/* 1106 */             sresponse.setContentType("text/plain");
/* 1107 */             sresponse.flushBuffer();
/*      */           } 
/*      */         } 
/*      */         
/* 1111 */         recordCount++;
/*      */ 
/*      */         
/* 1114 */         Selection temp_sel = (Selection)selections.elementAt(i);
/* 1115 */         Selection sel = SelectionManager.getInstance().getSelectionHeader(temp_sel.getSelectionID());
/*      */ 
/*      */         
/* 1118 */         if (reportname.equals("ToDoList")) {
/* 1119 */           sel.setDueDate(temp_sel.getDueDate());
/* 1120 */           sel.setDepartment(temp_sel.getDepartment());
/* 1121 */           sel.setTaskName(temp_sel.getTaskName());
/* 1122 */           sel.setCompletionDate(temp_sel.getCompletionDate());
/*      */         } 
/*      */ 
/*      */         
/* 1126 */         String status = "";
/* 1127 */         if (sel.getSelectionStatus() != null) {
/* 1128 */           status = sel.getSelectionStatus().getName();
/*      */         }
/* 1130 */         if (!status.equalsIgnoreCase("TBS") && !status.equalsIgnoreCase("In the works"))
/*      */         {
/*      */           
/* 1133 */           if (!reportname.equals("McaCustImp"))
/*      */           {
/*      */             
/* 1136 */             if (strProductType.equals(String.valueOf(1))) {
/* 1137 */               Calendar digitalStreetDate = sel.getDigitalRlsDate();
/* 1138 */               if (digitalStreetDate != null && ((beginStDate != null && digitalStreetDate.before(beginStDate)) || (endStDate != null && digitalStreetDate.after(endStDate))))
/*      */                 continue; 
/* 1140 */             } else if (strProductType.equals(String.valueOf(2))) {
/*      */               Calendar streetDate;
/*      */               
/* 1143 */               if (sel.isDigital) {
/* 1144 */                 streetDate = sel.getDigitalRlsDate();
/*      */               } else {
/* 1146 */                 streetDate = sel.getStreetDate();
/*      */               } 
/* 1148 */               if (streetDate != null && ((beginStDate != null && streetDate.before(beginStDate)) || (endStDate != null && streetDate.after(endStDate))))
/*      */                 continue; 
/*      */             } else {
/* 1151 */               Calendar physicalStreetDate = sel.getStreetDate();
/* 1152 */               if (physicalStreetDate != null && ((beginStDate != null && physicalStreetDate.before(beginStDate)) || (endStDate != null && physicalStreetDate.after(endStDate)))) {
/*      */                 continue;
/*      */               }
/*      */             } 
/*      */           }
/*      */         }
/*      */ 
/*      */         
/* 1160 */         if (!strDistribution.equalsIgnoreCase("") && !strDistribution.equalsIgnoreCase("All")) {
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 1165 */           String selDistribution = "";
/*      */           
/* 1167 */           if (sel.getLabel().getDistribution() == 1) {
/*      */             
/* 1169 */             selDistribution = "East";
/*      */           }
/* 1171 */           else if (sel.getLabel().getDistribution() == 0) {
/*      */             
/* 1173 */             selDistribution = "West";
/*      */           } 
/*      */           
/* 1176 */           if (!selDistribution.equalsIgnoreCase(strDistribution.trim())) {
/*      */             continue;
/*      */           }
/*      */         } 
/*      */ 
/*      */         
/* 1182 */         Label currentLabel = sel.getLabel();
/* 1183 */         int selectionsDistCompany = MilestoneHelper_2.getLabelDistCoId(currentLabel.getStructureID());
/*      */         
/* 1185 */         if (selectionsDistCompany == -1) {
/* 1186 */           selectionsDistCompany = Integer.parseInt("1");
/*      */         }
/*      */ 
/*      */         
/* 1190 */         if (distCoIDs.size() > 0 && !distCoIDs.contains(Integer.toString(selectionsDistCompany))) {
/*      */           continue;
/*      */         }
/*      */ 
/*      */ 
/*      */         
/* 1196 */         if (!strLabelContact.trim().equalsIgnoreCase("0") && !strLabelContact.trim().equalsIgnoreCase("")) {
/*      */           
/* 1198 */           if (sel.getLabelContact() == null) {
/*      */             continue;
/*      */           }
/* 1201 */           String selLabelContactName = Integer.toString(sel.getLabelContact().getUserId());
/*      */           
/* 1203 */           if (!selLabelContactName.equalsIgnoreCase(strLabelContact)) {
/*      */             continue;
/*      */           }
/*      */         } 
/*      */         
/* 1208 */         if (!strUmlContact.trim().equalsIgnoreCase("0") && !strUmlContact.trim().equalsIgnoreCase("")) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 1214 */           SelectionManager.getInstance().getSelectionManufacturingSubDetail(sel);
/*      */           
/* 1216 */           if (sel.getUmlContact() == null) {
/*      */             continue;
/*      */           }
/*      */ 
/*      */           
/* 1221 */           String selUmlContactName = Integer.toString(sel.getUmlContact().getUserId());
/*      */           
/* 1223 */           if (!selUmlContactName.equalsIgnoreCase(strUmlContact)) {
/*      */             continue;
/*      */           }
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1232 */         if (!strReleaseType.equalsIgnoreCase("") && !strReleaseType.equalsIgnoreCase("All")) {
/*      */           
/* 1234 */           String releaseType = "";
/* 1235 */           if (sel.getReleaseType() != null) {
/*      */             
/* 1237 */             releaseType = (sel.getReleaseType().getName() != null) ? sel.getReleaseType().getName() : "";
/* 1238 */             if (!releaseType.equalsIgnoreCase(strReleaseType)) {
/*      */               continue;
/*      */             }
/*      */           } else {
/*      */             continue;
/*      */           } 
/*      */         } 
/*      */ 
/*      */ 
/*      */         
/* 1248 */         if (!ReportSelectionsHelper.isStatusFound(reportForm, sel)) {
/*      */           continue;
/*      */         }
/*      */ 
/*      */         
/* 1253 */         if (!strArtist.trim().equals("") && sel.getArtist() != null) {
/*      */           
/* 1255 */           String artistUpperCase = sel.getArtist().trim().toUpperCase();
/*      */           
/* 1257 */           if (!artistUpperCase.startsWith(strArtist.trim().toUpperCase())) {
/*      */             continue;
/*      */           }
/*      */         } 
/*      */ 
/*      */         
/* 1263 */         if (!ReportSelectionsHelper.isMultSelectFound(Integer.toString(sel.getReleaseFamilyId()), strFamily)) {
/*      */           continue;
/*      */         }
/*      */         
/* 1267 */         if (sel.getEnvironment() == null || 
/* 1268 */           !ReportSelectionsHelper.isMultSelectFound(Integer.toString(sel.getEnvironment().getStructureID()), strEnvironment)) {
/*      */           continue;
/*      */         }
/*      */         
/* 1272 */         if (sel.getCompany() == null || 
/* 1273 */           !ReportSelectionsHelper.isMultSelectFound(Integer.toString(sel.getCompany().getStructureID()), strCompany)) {
/*      */           continue;
/*      */         }
/*      */ 
/*      */         
/* 1278 */         if (sel.getLabel() == null || 
/* 1279 */           !ReportSelectionsHelper.isMultSelectFound(Integer.toString(sel.getLabel().getStructureID()), strLabel)) {
/*      */           continue;
/*      */         }
/*      */ 
/*      */ 
/*      */         
/* 1285 */         if (bParent) {
/*      */           
/* 1287 */           String prefixId = "";
/*      */           
/* 1289 */           if (sel.getPrefixID() != null) {
/* 1290 */             prefixId = SelectionManager.getLookupObjectValue(sel.getPrefixID());
/*      */           }
/* 1292 */           if (!sel.getTitleID().equalsIgnoreCase(String.valueOf(prefixId) + sel.getSelectionNo())) {
/*      */             continue;
/*      */           }
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1300 */         if (strSubconfiguration != null && 
/* 1301 */           !strSubconfiguration[0].trim().equalsIgnoreCase("0") && 
/* 1302 */           !strSubconfiguration[0].trim().equalsIgnoreCase("") && 
/* 1303 */           !strSubconfiguration[0].trim().equalsIgnoreCase("all")) {
/*      */ 
/*      */           
/* 1306 */           if (sel.getSelectionSubConfig() != null && sel.getSelectionConfig() != null) {
/* 1307 */             String selConfigurationName = sel.getSelectionConfig().getSelectionConfigurationAbbreviation();
/* 1308 */             String selSubconfigurationName = sel.getSelectionSubConfig().getSelectionSubConfigurationAbbreviation();
/*      */             
/* 1310 */             boolean subconfigFnd = false;
/* 1311 */             for (int x = 0; x < strSubconfiguration.length; x++) {
/* 1312 */               String txtvalue = strSubconfiguration[x];
/* 1313 */               if (txtvalue.indexOf("[") != -1) {
/*      */                 
/* 1315 */                 String txtconfigcode = txtvalue.substring(1, 
/* 1316 */                     txtvalue.indexOf("]"));
/* 1317 */                 String txtsubconfigcode = txtvalue.substring(txtvalue.indexOf(
/* 1318 */                       "]") + 1, txtvalue.length());
/* 1319 */                 if (selConfigurationName.equalsIgnoreCase(txtconfigcode) && 
/* 1320 */                   selSubconfigurationName.equalsIgnoreCase(txtsubconfigcode)) {
/* 1321 */                   subconfigFnd = true;
/*      */ 
/*      */ 
/*      */                   
/*      */                   break;
/*      */                 } 
/* 1327 */               } else if (selSubconfigurationName.equalsIgnoreCase(txtvalue)) {
/* 1328 */                 subconfigFnd = true;
/*      */                 
/*      */                 break;
/*      */               } 
/*      */             } 
/* 1333 */             if (!subconfigFnd)
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
/* 1347 */         else if (strConfiguration != null && !strConfiguration[0].trim().equalsIgnoreCase("0") && !strConfiguration[0].trim().equalsIgnoreCase("") && 
/* 1348 */           !strConfiguration[0].trim().equalsIgnoreCase("all")) {
/*      */           
/* 1350 */           if (sel.getSelectionConfig() != null) {
/* 1351 */             String selConfigurationName = sel.getSelectionConfig().getSelectionConfigurationAbbreviation();
/*      */             
/* 1353 */             boolean configFnd = false;
/* 1354 */             for (int x = 0; x < strConfiguration.length; x++) {
/* 1355 */               if (selConfigurationName.equalsIgnoreCase(strConfiguration[x])) {
/* 1356 */                 configFnd = true;
/*      */                 break;
/*      */               } 
/*      */             } 
/* 1360 */             if (!configFnd) {
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
/* 1374 */         selectionsForReport.addElement(sel);
/*      */         
/*      */         continue;
/* 1377 */       } catch (Exception exception) {
/*      */         continue;
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 1383 */     if (reportname.equals("Prod.Stat.")) {
/* 1384 */       Vector precache = new Vector();
/*      */       
/* 1386 */       for (int y = 0; y < selectionsForReport.size(); y++) {
/*      */         
/* 1388 */         Selection currentSelection = (Selection)selectionsForReport.get(y);
/*      */         
/* 1390 */         Schedule temp = ScheduleManager.getInstance().getSchedule(currentSelection.getSelectionID());
/* 1391 */         currentSelection.setSchedule(temp);
/*      */         
/* 1393 */         if (temp != null && temp.getTasks().size() > 0)
/*      */         {
/*      */           
/* 1396 */           precache.add(currentSelection);
/*      */         }
/*      */       } 
/* 1399 */       return precache;
/* 1400 */     }  if (reportname.equals("Com Rel") || reportname.equals("VerveComm")) {
/*      */       
/* 1402 */       Vector commercialSelectionsForReport = new Vector();
/* 1403 */       for (int z = 0; z < selectionsForReport.size(); z++) {
/*      */         
/* 1405 */         Selection sel = (Selection)selectionsForReport.elementAt(z);
/*      */ 
/*      */         
/* 1408 */         String releaseType = (sel.getReleaseType().getName() != null) ? sel.getReleaseType().getName() : "";
/*      */         
/* 1410 */         if (releaseType.toUpperCase().indexOf("COMMERCIAL") != -1)
/* 1411 */           commercialSelectionsForReport.addElement(sel); 
/*      */       } 
/* 1413 */       return commercialSelectionsForReport;
/*      */     } 
/* 1415 */     return selectionsForReport;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static SelectionConfiguration getSelectionConfigObject(String abbreviation, Vector configs) {
/* 1422 */     for (int j = 0; j < configs.size(); j++) {
/*      */       
/* 1424 */       SelectionConfiguration selectionConfiguration = (SelectionConfiguration)configs.get(j);
/*      */       
/* 1426 */       if (selectionConfiguration.getSelectionConfigurationAbbreviation().equalsIgnoreCase(abbreviation))
/*      */       {
/* 1428 */         return selectionConfiguration;
/*      */       }
/*      */     } 
/*      */     
/* 1432 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static SelectionSubConfiguration getSelectionSubConfigObject(String abbreviation, SelectionConfiguration config) {
/* 1440 */     Vector subConfigs = config.getSubConfigurations();
/*      */     
/* 1442 */     for (int j = 0; j < subConfigs.size(); j++) {
/*      */       
/* 1444 */       SelectionSubConfiguration subConfig = (SelectionSubConfiguration)subConfigs.get(j);
/*      */       
/* 1446 */       if (subConfig.getSelectionSubConfigurationAbbreviation().equalsIgnoreCase(abbreviation))
/*      */       {
/* 1448 */         return subConfig;
/*      */       }
/*      */     } 
/*      */     
/* 1452 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static LookupObject getLookupObject(String abbreviation, Vector lookupVector) {
/* 1460 */     for (int j = 0; j < lookupVector.size(); j++) {
/*      */       
/* 1462 */       LookupObject lookupObject = (LookupObject)lookupVector.get(j);
/*      */       
/* 1464 */       if (lookupObject.getAbbreviation().equalsIgnoreCase(abbreviation))
/*      */       {
/* 1466 */         return lookupObject;
/*      */       }
/*      */     } 
/*      */     
/* 1470 */     return null;
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
/* 1485 */     User user = (User)context.getSessionValue("user");
/* 1486 */     int userId = 0;
/* 1487 */     if (user != null) {
/* 1488 */       userId = user.getUserId();
/*      */     }
/*      */     
/* 1491 */     Form reportForm = (Form)context.getSessionValue("reportForm");
/* 1492 */     boolean bScheduled = (reportForm.getElement("ScheduledReleases") != null) ? ((FormCheckBox)reportForm.getElement("ScheduledReleases")).isChecked() : 0;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1498 */     Vector companies = MilestoneHelper.getUserCompanies(context);
/* 1499 */     Company company = null;
/* 1500 */     Vector precache = new Vector();
/*      */     
/* 1502 */     StringBuffer query = new StringBuffer();
/*      */     
/* 1504 */     query.append("SELECT header.release_id, header.title_id, header.selection_no, header.prefix,   pfm.units_per_set, detail.due_date AS taskDue, detail.comments AS taskComments,   detail.owner AS taskOwner, detail.completion_date AS taskComplete,   detail.status AS taskStatus, detail.day_of_week AS taskDayOfWeek,   detail.weeks_to_release AS taskWeeksToRelease, detail.key_task_indicator AS taskKey,    detail.vendor AS taskVendor, detail.authorization_name AS taskAuthName,   detail.authorization_date AS taskAuthDate, detail.last_updated_ck, header.project_no,   header.title, header.artist_first_name, header.artist_last_name,   header.artist, header.artist_first_name + ' ' + header.artist_last_name AS fl_artist ,   header.side_a_title, header.side_b_title, header.product_line, header.release_type,   header.configuration, header.sub_configuration, header.UPC, header.price_code, header.genre,   header.family_id, header.release_family_id, header.environment_id, header.company_id, header.division_id, header.label_id, header.street_date,   header.international_date, header.coordinator_contacts, header.contact_id, header.status,   header.hold_indicator, header.hold_reason, header.comments, header.special_pkg_indicator,   header.pd_indicator, header.packaging, header.impact_date, header.last_updated_on,   header.last_updated_by, header.entered_on, mfg.uml_id, mfg.plant, mfg.distribution,   header.imprint,   mfg.order_qty, mfg.complete_qty, mfg.order_comments, Task.task_id, Task.department,   Task.name, Task.abbrev_id  FROM  Release_Header header  with (nolock)  LEFT JOIN Release_Subdetail mfg  with (nolock)  ON header.release_id = mfg.release_id ");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1527 */     if (bScheduled) {
/*      */       
/* 1529 */       query.append(" INNER JOIN release_detail detail with (nolock) ON header.[release_id] = detail.[release_id]");
/*      */     } else {
/*      */       
/* 1532 */       query.append(" LEFT JOIN release_detail detail with (nolock) ON header.[release_id] = detail.[release_id]");
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1539 */     String strProdGroupCode = (reportForm.getStringValue("productionGroupCode") != null) ? reportForm.getStringValue("productionGroupCode") : "";
/* 1540 */     if (!strProdGroupCode.equalsIgnoreCase("") && !strProdGroupCode.equalsIgnoreCase("All")) {
/* 1541 */       query.append(" INNER JOIN ArchimedesLabels atlasLabels with (nolock) ON header.[Archimedes_id] = atlasLabels.[ArchimedesID] ");
/*      */     }
/* 1543 */     query.append("  LEFT JOIN Task  with (nolock)  ON detail.task_id = Task.task_id   LEFT JOIN Pfm_Selection pfm with (nolock)  ON header.release_id = pfm.release_id    WHERE ");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1554 */     String strLabelContact = (reportForm.getStringValue("contact") != null) ? reportForm.getStringValue("contact") : "";
/* 1555 */     String strUmlContact = (reportForm.getStringValue("umlContact") != null) ? reportForm.getStringValue("umlContact") : "";
/* 1556 */     String strReleaseType = (reportForm.getStringValue("releaseType") != null) ? reportForm.getStringValue("releaseType") : "";
/* 1557 */     String strArtist = (reportForm.getStringValue("artist") != null) ? reportForm.getStringValue("artist") : "";
/* 1558 */     String[] strFamily = ReportSelectionsHelper.getMultiSelectionListValues("family", reportForm);
/*      */     
/* 1560 */     String[] strEnvironment = ReportSelectionsHelper.getMultiSelectionListValues("environment", reportForm);
/* 1561 */     String[] strCompany = ReportSelectionsHelper.getMultiSelectionListValues("company", reportForm);
/* 1562 */     String[] strLabel = ReportSelectionsHelper.getMultiSelectionListValues("Label", reportForm);
/*      */ 
/*      */ 
/*      */     
/* 1566 */     String strProductType = (reportForm.getStringValue("ProductType") != null) ? reportForm.getStringValue("ProductType") : "";
/* 1567 */     if (strProductType.equals("")) {
/*      */       
/* 1569 */       strProductType = String.valueOf(reportProductType);
/*      */     
/*      */     }
/* 1572 */     else if (strProductType.equals("Physical")) {
/* 1573 */       strProductType = String.valueOf(0);
/* 1574 */     } else if (strProductType.equals("Digital")) {
/* 1575 */       strProductType = String.valueOf(1);
/* 1576 */     } else if (strProductType.equals("Both")) {
/* 1577 */       strProductType = String.valueOf(2);
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1587 */     String[] strConfiguration = null;
/*      */     try {
/* 1589 */       MilestoneFormDropDownMenu configList = (MilestoneFormDropDownMenu)reportForm.getElement("configurationList");
/* 1590 */       if (configList != null) {
/* 1591 */         ArrayList configListAl = configList.getStringValues();
/* 1592 */         if (configListAl != null) {
/*      */           
/* 1594 */           strConfiguration = new String[configListAl.size()];
/* 1595 */           strConfiguration = (String[])configListAl.toArray(strConfiguration);
/*      */         } 
/*      */       } 
/* 1598 */     } catch (Exception e) {
/* 1599 */       e.printStackTrace();
/* 1600 */       System.out.println("<<< Configuration vector execption " + e.getMessage());
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1607 */     String[] strSubconfiguration = null;
/*      */     try {
/* 1609 */       MilestoneFormDropDownMenu subconfigList = (MilestoneFormDropDownMenu)reportForm.getElement("subconfigurationList");
/* 1610 */       if (subconfigList != null) {
/* 1611 */         ArrayList subconfigListAl = subconfigList.getStringValues();
/* 1612 */         if (subconfigListAl != null) {
/*      */           
/* 1614 */           strSubconfiguration = new String[subconfigListAl.size()];
/* 1615 */           strSubconfiguration = (String[])subconfigListAl.toArray(strSubconfiguration);
/*      */         } 
/*      */       } 
/* 1618 */     } catch (Exception e) {
/* 1619 */       e.printStackTrace();
/* 1620 */       System.out.println("<<< Subconfiguration vector execption " + e.getMessage());
/*      */     } 
/*      */ 
/*      */     
/* 1624 */     boolean bParent = (reportForm.getElement("ParentsOnly") != null) ? ((FormCheckBox)reportForm.getElement("ParentsOnly")).isChecked() : 0;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1634 */     ReportSelectionsHelper.addMultSelectionToStringBuffer(query, "header.environment_id", strEnvironment, true, "environment", reportForm, true, false);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1641 */     ReportSelectionsHelper.addMultSelectionToStringBuffer(query, "header.company_id", strCompany, true, "company", reportForm, false, true);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1647 */     ReportSelectionsHelper.addReleasingFamilyLabelFamilyMultSelect("family", context, query, reportForm);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1653 */     ReportSelectionsHelper.addMultSelectionToStringBuffer(query, "header.[label_id]", strLabel, true, "Label", reportForm, false, true);
/*      */ 
/*      */ 
/*      */     
/* 1657 */     if (strProductType.equals(Integer.toString(1))) {
/* 1658 */       query.append(" AND digital_flag = 1 ");
/* 1659 */     } else if (strProductType.equals(Integer.toString(0))) {
/* 1660 */       query.append(" AND digital_flag = 0 ");
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1667 */     if (!strArtist.equalsIgnoreCase("")) {
/* 1668 */       query.append(" AND header.[artist] LIKE '%" + MilestoneHelper.escapeSingleQuotes(strArtist) + "%'");
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 1673 */     ReportSelectionsHelper.addStatusToSelect(reportForm, query);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1678 */     if (!strReleaseType.equalsIgnoreCase("") && !strReleaseType.equalsIgnoreCase("all"))
/*      */     {
/* 1680 */       if (strReleaseType.equalsIgnoreCase("commercial")) {
/* 1681 */         query.append(" AND header.[release_type] ='CO'");
/*      */       } else {
/* 1683 */         query.append(" AND header.[release_type] ='PR'");
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 1689 */     if (!strProdGroupCode.equalsIgnoreCase("") && !strProdGroupCode.equalsIgnoreCase("All")) {
/* 1690 */       query.append(" AND atlasLabels.[ProductionGroupCode] ='" + strProdGroupCode + "' ");
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 1695 */     if (!strLabelContact.equalsIgnoreCase("") && !strLabelContact.equalsIgnoreCase("0")) {
/* 1696 */       query.append(" AND header.[contact_id] =" + strLabelContact);
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
/* 1721 */     if (strSubconfiguration != null && 
/* 1722 */       !strSubconfiguration[0].trim().equalsIgnoreCase("0") && 
/* 1723 */       !strSubconfiguration[0].trim().equalsIgnoreCase("") && 
/* 1724 */       !strSubconfiguration[0].trim().equalsIgnoreCase("all")) {
/*      */       
/* 1726 */       boolean addOr = false;
/* 1727 */       query.append(" AND (");
/* 1728 */       for (int x = 0; x < strSubconfiguration.length; x++) {
/* 1729 */         if (addOr)
/* 1730 */           query.append(" OR "); 
/* 1731 */         String txtvalue = strSubconfiguration[x];
/* 1732 */         if (txtvalue.indexOf("[") != -1) {
/*      */           
/* 1734 */           String txtconfigcode = txtvalue.substring(1, txtvalue.indexOf("]"));
/* 1735 */           String txtsubconfigcode = txtvalue.substring(txtvalue.indexOf("]") + 1, txtvalue.length());
/* 1736 */           query.append(" (header.[configuration] = '" + txtconfigcode.trim() + "' AND ");
/* 1737 */           query.append(" header.[sub_configuration] = '" + txtsubconfigcode.trim() + "') ");
/*      */         } else {
/*      */           
/* 1740 */           query.append(" header.[sub_configuration] = '" + txtvalue.trim() + "' ");
/* 1741 */         }  addOr = true;
/*      */       } 
/* 1743 */       query.append(") ");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     }
/* 1751 */     else if (strConfiguration != null && !strConfiguration[0].equalsIgnoreCase("") && !strConfiguration[0].equalsIgnoreCase("0") && 
/* 1752 */       !strConfiguration[0].equalsIgnoreCase("all")) {
/*      */       
/* 1754 */       boolean addOr = false;
/* 1755 */       query.append(" AND (");
/* 1756 */       for (int x = 0; x < strConfiguration.length; x++) {
/* 1757 */         if (addOr) {
/* 1758 */           query.append(" OR ");
/*      */         }
/* 1760 */         query.append(" header.[configuration] = '" + strConfiguration[x].trim() + "' ");
/* 1761 */         addOr = true;
/*      */       } 
/* 1763 */       query.append(") ");
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1772 */     if (!strUmlContact.equalsIgnoreCase("") && !strUmlContact.equalsIgnoreCase("0")) {
/* 1773 */       query.append(" AND mfg.[uml_id] =" + strUmlContact);
/*      */     }
/* 1775 */     String beginDate = "";
/* 1776 */     beginDate = (reportForm.getStringValue("beginDate") != null && reportForm.getStringValue("beginDate").length() > 0) ? reportForm.getStringValue("beginDate") : "";
/*      */     
/* 1778 */     String endDate = "";
/* 1779 */     endDate = (reportForm.getStringValue("endDate") != null && reportForm.getStringValue("endDate").length() > 0) ? reportForm.getStringValue("endDate") : "";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1786 */     if (!beginDate.equalsIgnoreCase("") || !endDate.equalsIgnoreCase("")) {
/* 1787 */       query.append(" AND ((header.[status] ='TBS' OR header.[status] ='ITW') OR (");
/*      */     }
/* 1789 */     if (!beginDate.equalsIgnoreCase("")) {
/* 1790 */       query.append(" header.[street_date] >= '" + MilestoneHelper.escapeSingleQuotes(beginDate) + "'");
/*      */     }
/* 1792 */     if (!endDate.equalsIgnoreCase(""))
/*      */     {
/* 1794 */       if (!beginDate.equalsIgnoreCase("")) {
/* 1795 */         query.append(" AND header.[street_date] <= '" + MilestoneHelper.escapeSingleQuotes(endDate) + "'");
/*      */       } else {
/* 1797 */         query.append(" header.[street_date] <= '" + MilestoneHelper.escapeSingleQuotes(endDate) + "'");
/*      */       } 
/*      */     }
/* 1800 */     if (!beginDate.equalsIgnoreCase("") || !endDate.equalsIgnoreCase("")) query.append("))");
/*      */     
/* 1802 */     query.append(" ORDER BY header.[release_id], UPPER(header.[artist]), header.[street_date], UPPER(header.[title])");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1808 */     JdbcConnector connector = MilestoneHelper.getConnector(query.toString());
/*      */ 
/*      */     
/* 1811 */     connector.runQuery();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1821 */     Vector selections = null;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1826 */     System.out.println("\n\n************\n\n");
/* 1827 */     System.out.println(query.toString());
/* 1828 */     System.out.println("\n\n************\n\n");
/*      */     
/* 1830 */     int totalCount = 0;
/* 1831 */     int tenth = 0;
/*      */ 
/*      */     
/* 1834 */     JdbcConnector connectorCount = MilestoneHelper.getConnector(query.toString());
/* 1835 */     connectorCount.runQuery();
/*      */     
/* 1837 */     while (connectorCount.more()) {
/*      */       
/* 1839 */       totalCount++;
/* 1840 */       connectorCount.next();
/*      */     } 
/* 1842 */     connectorCount.close();
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1847 */     tenth = totalCount / 10;
/*      */     
/* 1849 */     if (tenth < 1) {
/* 1850 */       tenth = 1;
/*      */     }
/*      */     
/*      */     try {
/* 1854 */       HttpServletResponse sresponse = context.getResponse();
/* 1855 */       context.putDelivery("status", new String("start_gathering"));
/* 1856 */       context.putDelivery("percent", new String("10"));
/* 1857 */       if (!ReportingServices.usingReportServicesByContext(context))
/*      */       {
/* 1859 */         context.includeJSP("status.jsp", "hiddenFrame");
/* 1860 */         sresponse.setContentType("text/plain");
/* 1861 */         sresponse.flushBuffer();
/*      */       }
/*      */     
/* 1864 */     } catch (Exception exception) {}
/*      */ 
/*      */ 
/*      */     
/* 1868 */     int recordCount = 0;
/* 1869 */     int count = 0;
/*      */ 
/*      */ 
/*      */     
/* 1873 */     selections = new Vector();
/*      */     
/* 1875 */     while (connector.more()) {
/*      */ 
/*      */       
/*      */       try {
/*      */ 
/*      */ 
/*      */         
/* 1882 */         if (count < recordCount / tenth) {
/*      */           
/* 1884 */           count = recordCount / tenth;
/* 1885 */           HttpServletResponse sresponse = context.getResponse();
/* 1886 */           context.putDelivery("status", new String("start_gathering"));
/* 1887 */           context.putDelivery("percent", new String(String.valueOf(count * 10)));
/* 1888 */           if (!ReportingServices.usingReportServicesByContext(context)) {
/*      */             
/* 1890 */             context.includeJSP("status.jsp", "hiddenFrame");
/* 1891 */             sresponse.setContentType("text/plain");
/* 1892 */             sresponse.flushBuffer();
/*      */           } 
/*      */         } 
/*      */         
/* 1896 */         recordCount++;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1907 */         if (bParent) {
/*      */           
/* 1909 */           String prefixId = "";
/* 1910 */           String tmpTitleId = connector.getField("title_id", "");
/* 1911 */           String tmpSelectionNo = connector.getField("selection_no", "");
/*      */           
/* 1913 */           prefixId = SelectionManager.getLookupObjectValue((PrefixCode)getLookupObject(connector.getField("prefix", ""), Cache.getPrefixCodes()));
/*      */           
/* 1915 */           if (!tmpTitleId.equalsIgnoreCase(String.valueOf(prefixId) + tmpSelectionNo)) {
/*      */             
/* 1917 */             connector.next();
/*      */             
/*      */             continue;
/*      */           } 
/*      */         } 
/*      */         
/* 1923 */         int numberOfUnits = 0;
/*      */         
/*      */         try {
/* 1926 */           numberOfUnits = connector.getIntegerField("pfm.[units_per_set]");
/*      */         }
/* 1928 */         catch (Exception exception) {}
/*      */ 
/*      */         
/* 1931 */         Selection selection = null;
/*      */         
/* 1933 */         selection = new Selection();
/* 1934 */         selection.setSelectionID(connector.getIntegerField("release_id"));
/*      */         
/* 1936 */         String selectionNo = "";
/* 1937 */         if (connector.getFieldByName("selection_no") != null)
/* 1938 */           selectionNo = connector.getFieldByName("selection_no"); 
/* 1939 */         selection.setSelectionNo(selectionNo);
/*      */         
/* 1941 */         selection.setProjectID(connector.getField("project_no", ""));
/*      */         
/* 1943 */         String titleId = "";
/* 1944 */         if (connector.getFieldByName("title_id") != null)
/* 1945 */           titleId = connector.getFieldByName("title_id"); 
/* 1946 */         selection.setTitleID(titleId);
/*      */         
/* 1948 */         selection.setTitle(connector.getField("title", ""));
/* 1949 */         selection.setArtistFirstName(connector.getField("artist_first_name", ""));
/* 1950 */         selection.setArtistLastName(connector.getField("artist_last_name", ""));
/* 1951 */         selection.setArtist(connector.getField("artist", ""));
/* 1952 */         selection.setFlArtist(connector.getField("fl_artist", ""));
/*      */         
/* 1954 */         selection.setASide(connector.getField("side_a_title", ""));
/* 1955 */         selection.setBSide(connector.getField("side_b_title", ""));
/*      */         
/* 1957 */         selection.setProductCategory((ProductCategory)getLookupObject(connector.getField("product_line"), 
/* 1958 */               Cache.getProductCategories()));
/* 1959 */         selection.setReleaseType((ReleaseType)getLookupObject(connector.getField("release_type"), 
/* 1960 */               Cache.getReleaseTypes()));
/*      */         
/* 1962 */         selection.setSelectionConfig(getSelectionConfigObject(connector.getField("configuration"), 
/* 1963 */               Cache.getSelectionConfigs()));
/* 1964 */         selection.setSelectionSubConfig(getSelectionSubConfigObject(connector.getField("sub_configuration"), 
/* 1965 */               selection.getSelectionConfig()));
/*      */         
/* 1967 */         selection.setUpc(connector.getField("upc", ""));
/*      */         
/* 1969 */         String sellCodeString = connector.getFieldByName("price_code");
/* 1970 */         if (sellCodeString != null)
/*      */         {
/* 1972 */           selection.setPriceCode(SelectionManager.getInstance().getPriceCode(sellCodeString));
/*      */         }
/*      */         
/* 1975 */         selection.setSellCode(sellCodeString);
/*      */         
/* 1977 */         selection.setGenre((Genre)getLookupObject(connector.getField("genre"), 
/* 1978 */               Cache.getMusicTypes()));
/* 1979 */         selection.setFamily((Family)MilestoneHelper.getStructureObject(connector.getIntegerField("family_id")));
/* 1980 */         selection.setCompany((Company)MilestoneHelper.getStructureObject(connector.getIntegerField("company_id")));
/* 1981 */         selection.setEnvironment((Environment)MilestoneHelper.getStructureObject(connector.getIntegerField("environment_id")));
/* 1982 */         selection.setDivision((Division)MilestoneHelper.getStructureObject(connector.getIntegerField("division_id")));
/* 1983 */         selection.setLabel((Label)MilestoneHelper.getStructureObject(connector.getIntegerField("label_id")));
/*      */ 
/*      */         
/* 1986 */         selection.setImprint(connector.getField("imprint", ""));
/*      */ 
/*      */         
/* 1989 */         selection.setReleaseFamilyId(connector.getIntegerField("release_family_id"));
/*      */         
/* 1991 */         String streetDateString = connector.getFieldByName("street_date");
/* 1992 */         if (streetDateString != null) {
/* 1993 */           selection.setStreetDate(MilestoneHelper.getDatabaseDate(streetDateString));
/*      */         }
/* 1995 */         String internationalDateString = connector.getFieldByName("international_date");
/* 1996 */         if (internationalDateString != null) {
/* 1997 */           selection.setInternationalDate(MilestoneHelper.getDatabaseDate(internationalDateString));
/*      */         }
/* 1999 */         selection.setOtherContact(connector.getField("coordinator_contacts", ""));
/*      */         
/* 2001 */         if (UserManager.getInstance().getUser(connector.getIntegerField("contact_id")) != null) {
/* 2002 */           selection.setLabelContact(UserManager.getInstance().getUser(connector.getIntegerField("contact_id")));
/*      */         }
/* 2004 */         selection.setSelectionStatus((SelectionStatus)getLookupObject(connector.getField("status"), 
/* 2005 */               Cache.getSelectionStatusList()));
/* 2006 */         selection.setHoldSelection(connector.getBoolean("hold_indicator"));
/*      */         
/* 2008 */         selection.setHoldReason(connector.getField("hold_reason", ""));
/* 2009 */         selection.setComments(connector.getField("comments", ""));
/* 2010 */         selection.setSpecialPackaging(connector.getBoolean("special_pkg_indicator"));
/* 2011 */         selection.setNumberOfUnits(numberOfUnits);
/* 2012 */         selection.setPressAndDistribution(connector.getBoolean("pd_indicator"));
/*      */         
/* 2014 */         selection.setPrefixID((PrefixCode)getLookupObject(connector.getField("prefix"), Cache.getPrefixCodes()));
/* 2015 */         selection.setSelectionPackaging(connector.getField("packaging", ""));
/*      */         
/* 2017 */         String impactDateString = connector.getFieldByName("impact_date");
/* 2018 */         if (impactDateString != null) {
/* 2019 */           selection.setImpactDate(MilestoneHelper.getDatabaseDate(impactDateString));
/*      */         }
/* 2021 */         String lastUpdateDateString = connector.getFieldByName("last_updated_on");
/* 2022 */         if (lastUpdateDateString != null) {
/* 2023 */           selection.setLastUpdateDate(MilestoneHelper.getDatabaseDate(lastUpdateDateString));
/*      */         }
/* 2025 */         selection.setLastUpdatingUser(UserManager.getInstance().getUser(connector.getIntegerField("last_updated_by")));
/*      */ 
/*      */         
/* 2028 */         String originDateString = connector.getFieldByName("entered_on");
/* 2029 */         if (originDateString != null) {
/* 2030 */           selection.setOriginDate(MilestoneHelper.getDatabaseDate(originDateString));
/*      */         }
/*      */ 
/*      */ 
/*      */         
/* 2035 */         User umlContact = UserManager.getInstance().getUser(connector.getIntegerField("mfg.[uml_id]"));
/* 2036 */         selection.setUmlContact(umlContact);
/* 2037 */         selection.setPlant(getLookupObject(connector.getField("mfg.[plant]"), Cache.getVendors()));
/* 2038 */         selection.setDistribution(getLookupObject(connector.getField("mfg.[distribution]"), Cache.getDistributionCodes()));
/* 2039 */         selection.setPoQuantity(connector.getIntegerField("mfg.[order_qty]"));
/* 2040 */         selection.setCompletedQuantity(connector.getIntegerField("mfg.[complete_qty]"));
/* 2041 */         selection.setManufacturingComments(connector.getField("mfg.[order_comments]", ""));
/* 2042 */         selection.setPrice(connector.getFloat("mfg.[list_price]"));
/*      */         
/* 2044 */         selection.setFullSelection(true);
/*      */         
/* 2046 */         String strDistribution = (reportForm.getStringValue("distribution") != null) ? reportForm.getStringValue("distribution") : "";
/* 2047 */         String strFuture2 = (reportForm.getStringValue("Future2") != null) ? reportForm.getStringValue("Future2") : "";
/*      */ 
/*      */         
/* 2050 */         if (!strDistribution.equalsIgnoreCase("") && !strDistribution.equalsIgnoreCase("All")) {
/*      */           
/* 2052 */           String selDistribution = SelectionManager.getLookupObjectValue(selection.getDistribution());
/*      */           
/* 2054 */           if (!selDistribution.equalsIgnoreCase(strDistribution.trim())) {
/*      */             continue;
/*      */           }
/*      */         } 
/* 2058 */         int nextReleaseId = connector.getIntegerField("release_id");
/* 2059 */         Schedule schedule = new Schedule();
/* 2060 */         schedule.setSelectionID(nextReleaseId);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 2067 */         Vector precacheSchedule = new Vector();
/* 2068 */         while (connector.more() && connector.getIntegerField("release_id") == nextReleaseId) {
/*      */           
/* 2070 */           ScheduledTask scheduledTask = null;
/*      */           
/* 2072 */           scheduledTask = new ScheduledTask();
/*      */ 
/*      */           
/* 2075 */           scheduledTask.setReleaseID(connector.getIntegerField("release_Id"));
/*      */ 
/*      */           
/* 2078 */           scheduledTask.setTaskID(connector.getIntegerField("task_id"));
/*      */ 
/*      */           
/* 2081 */           scheduledTask.setOwner((Family)MilestoneHelper.getStructureObject(connector.getIntegerField("taskOwner")));
/*      */ 
/*      */           
/* 2084 */           String dueDateString = connector.getField("taskDue", "");
/* 2085 */           if (dueDateString.length() > 0) {
/* 2086 */             scheduledTask.setDueDate(MilestoneHelper.getDatabaseDate(dueDateString));
/*      */           }
/*      */           
/* 2089 */           String completionDateString = connector.getField("taskComplete", "");
/* 2090 */           if (completionDateString.length() > 0) {
/* 2091 */             scheduledTask.setCompletionDate(MilestoneHelper.getDatabaseDate(completionDateString));
/*      */           }
/*      */           
/* 2094 */           String taskStatus = connector.getField("taskStatus", "");
/* 2095 */           if (taskStatus.length() > 1) {
/* 2096 */             scheduledTask.setScheduledTaskStatus(taskStatus);
/*      */           }
/*      */           
/* 2099 */           int day = connector.getIntegerField("taskDayOfWeek");
/* 2100 */           if (day > 0) {
/* 2101 */             scheduledTask.setDayOfTheWeek(new Day(connector.getIntegerField("taskDayOfWeek")));
/*      */           }
/*      */           
/* 2104 */           int weeks = connector.getIntegerField("taskWeeksToRelease");
/* 2105 */           if (weeks > 0) {
/* 2106 */             scheduledTask.setWeeksToRelease(connector.getIntegerField("taskWeeksToRelease"));
/*      */           }
/*      */           
/* 2109 */           String vendorString = connector.getField("taskVendor", "");
/* 2110 */           if (vendorString.length() > 0) {
/* 2111 */             scheduledTask.setVendor(vendorString);
/*      */           }
/*      */           
/* 2114 */           int taskAbbrevID = connector.getIntegerField("abbrev_id");
/* 2115 */           scheduledTask.setTaskAbbreviationID(taskAbbrevID);
/*      */ 
/*      */           
/* 2118 */           int taskID = connector.getIntegerField("task_id");
/* 2119 */           scheduledTask.setScheduledTaskID(taskID);
/*      */ 
/*      */           
/* 2122 */           String taskDept = connector.getField("department", "");
/* 2123 */           scheduledTask.setDepartment(taskDept);
/*      */ 
/*      */           
/* 2126 */           scheduledTask.setKeyTask(connector.getBoolean("taskKey"));
/*      */ 
/*      */           
/* 2129 */           scheduledTask.setAuthorizationName(connector.getField("taskAuthName", ""));
/*      */ 
/*      */           
/* 2132 */           String authDateString = connector.getField("taskAuthDate", "");
/* 2133 */           if (authDateString.length() > 0) {
/* 2134 */             scheduledTask.setAuthorizationDate(MilestoneHelper.getDatabaseDate(authDateString));
/*      */           }
/*      */           
/* 2137 */           String comments = connector.getField("taskComments", "");
/* 2138 */           scheduledTask.setComments(comments);
/*      */ 
/*      */           
/* 2141 */           scheduledTask.setName(connector.getField("name", ""));
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 2146 */           precacheSchedule.add(scheduledTask);
/*      */           
/* 2148 */           scheduledTask = null;
/*      */           
/* 2150 */           if (connector.more()) {
/*      */             
/* 2152 */             connector.next();
/* 2153 */             recordCount++;
/*      */             
/*      */             continue;
/*      */           } 
/*      */           
/*      */           break;
/*      */         } 
/* 2160 */         schedule.setTasks(precacheSchedule);
/*      */         
/* 2162 */         selection.setSchedule(schedule);
/*      */         
/* 2164 */         selections.add(selection);
/*      */       
/*      */       }
/* 2167 */       catch (Exception exception) {}
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2174 */     connector.close();
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2179 */     return selections;
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
/* 2195 */     User user = (User)context.getSessionValue("user");
/* 2196 */     int userId = 0;
/* 2197 */     if (user != null)
/* 2198 */       userId = user.getUserId(); 
/* 2199 */     Form reportForm = (Form)context.getSessionValue("reportForm");
/*      */     
/* 2201 */     String beginDate = "";
/* 2202 */     beginDate = (reportForm.getStringValue("beginDate") != null && reportForm.getStringValue("beginDate").length() > 0) ? reportForm.getStringValue("beginDate") : "";
/*      */     
/* 2204 */     String endDate = "";
/* 2205 */     endDate = (reportForm.getStringValue("endDate") != null && reportForm.getStringValue("endDate").length() > 0) ? reportForm.getStringValue("endDate") : "";
/*      */     
/* 2207 */     String beginDueDate = "";
/* 2208 */     beginDueDate = (reportForm.getStringValue("beginDueDate") != null && reportForm.getStringValue("beginDueDate").length() > 0) ? reportForm.getStringValue("beginDueDate") : "";
/*      */     
/* 2210 */     String endDueDate = "";
/* 2211 */     endDueDate = (reportForm.getStringValue("endDueDate") != null && reportForm.getStringValue("endDueDate").length() > 0) ? reportForm.getStringValue("endDueDate") : "";
/*      */     
/* 2213 */     StringBuffer dueDateQuery = new StringBuffer(200);
/*      */     
/* 2215 */     if (!beginDueDate.equalsIgnoreCase("")) {
/* 2216 */       dueDateQuery.append("AND due_date >= '" + MilestoneHelper.escapeSingleQuotes(beginDueDate) + "'");
/*      */     }
/* 2218 */     if (!endDueDate.equalsIgnoreCase(""))
/*      */     {
/* 2220 */       if (!beginDueDate.equalsIgnoreCase("")) {
/* 2221 */         dueDateQuery.append(" AND due_date <= '" + MilestoneHelper.escapeSingleQuotes(endDueDate) + "'");
/*      */       } else {
/* 2223 */         dueDateQuery.append(" due_date <= '" + MilestoneHelper.escapeSingleQuotes(endDueDate) + "'");
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2230 */     Vector companies = MilestoneHelper.getUserCompanies(context);
/* 2231 */     Company company = null;
/* 2232 */     Vector precache = new Vector();
/* 2233 */     StringBuffer query = new StringBuffer();
/*      */ 
/*      */     
/* 2236 */     query.append("SELECT header.release_id, header.title_id, header.selection_no, header.prefix,   pfm.units_per_set, detail.due_date AS taskDue, detail.comments AS taskComments,   detail.owner AS taskOwner, detail.completion_date AS taskComplete,   detail.status AS taskStatus, detail.day_of_week AS taskDayOfWeek,   detail.weeks_to_release AS taskWeeksToRelease, detail.key_task_indicator AS taskKey,    detail.vendor AS taskVendor, detail.authorization_name AS taskAuthName,   detail.authorization_date AS taskAuthDate, detail.last_updated_ck, header.project_no,   header.title, header.artist_first_name, header.artist_last_name, header.artist,   header.side_a_title, header.side_b_title, header.product_line, header.release_type,   header.configuration, header.sub_configuration, header.UPC, header.price_code, header.genre,   header.family_id, header.release_family_id, header_environment_id, header.company_id, header.division_id, header.label_id, header.street_date,   header.international_date, header.coordinator_contacts, header.contact_id, header.status,   header.hold_indicator, header.hold_reason, header.comments, header.special_pkg_indicator,   header.pd_indicator, header.packaging, header.impact_date, header.last_updated_on,   header.last_updated_by, header.entered_on, mfg.uml_id, mfg.plant, mfg.distribution,   header.imprint,   mfg.order_qty, mfg.complete_qty, mfg.order_comments, Task.task_id, Task.department,   Task.name, Task.abbrev_id  FROM vi_release_header header with (nolock) LEFT JOIN vi_release_subdetail mfg with (nolock) ON header.[release_id] = mfg.[release_id] LEFT JOIN vi_release_detail detail with (nolock) ON header.[release_id] = detail.[release_id] " + 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 2259 */         dueDateQuery.toString() + 
/* 2260 */         " INNER JOIN vi_Task task with (nolock)" + 
/* 2261 */         " ON detail.[task_id] = task.[task_id]" + 
/* 2262 */         " LEFT JOIN vi_pfm_selection pfm with (nolock)" + 
/* 2263 */         " ON header.[release_id] = pfm.[release_id]" + 
/* 2264 */         " WHERE ");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2271 */     String strLabelContact = (reportForm.getStringValue("contact") != null) ? reportForm.getStringValue("contact") : "";
/* 2272 */     String strUmlContact = (reportForm.getStringValue("umlContact") != null) ? reportForm.getStringValue("umlContact") : "";
/* 2273 */     String strReleaseType = (reportForm.getStringValue("releaseType") != null) ? reportForm.getStringValue("releaseType") : "";
/* 2274 */     String strArtist = (reportForm.getStringValue("artist") != null) ? reportForm.getStringValue("artist") : "";
/* 2275 */     String[] strFamily = ReportSelectionsHelper.getMultiSelectionListValues("family", reportForm);
/* 2276 */     String[] strEnvironment = ReportSelectionsHelper.getMultiSelectionListValues("environment", reportForm);
/* 2277 */     String[] strCompany = ReportSelectionsHelper.getMultiSelectionListValues("company", reportForm);
/* 2278 */     String[] strLabel = ReportSelectionsHelper.getMultiSelectionListValues("Label", reportForm);
/*      */ 
/*      */     
/* 2281 */     String strProductType = (reportForm.getStringValue("ProductType") != null) ? reportForm.getStringValue("ProductType") : "";
/* 2282 */     if (strProductType.equals("")) {
/*      */       
/* 2284 */       strProductType = String.valueOf(reportProductType);
/*      */     
/*      */     }
/* 2287 */     else if (strProductType.equals("Physical")) {
/* 2288 */       strProductType = String.valueOf(0);
/* 2289 */     } else if (strProductType.equals("Digital")) {
/* 2290 */       strProductType = String.valueOf(1);
/* 2291 */     } else if (strProductType.equals("Both")) {
/* 2292 */       strProductType = String.valueOf(2);
/*      */     } 
/*      */ 
/*      */     
/* 2296 */     boolean bParent = (reportForm.getElement("ParentsOnly") != null) ? ((FormCheckBox)reportForm.getElement("ParentsOnly")).isChecked() : 0;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2306 */     ReportSelectionsHelper.addMultSelectionToStringBuffer(query, "header.environment_id", strEnvironment, true, "environment", reportForm, true, false);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2313 */     ReportSelectionsHelper.addMultSelectionToStringBuffer(query, "header.company_id", strCompany, true, "company", reportForm, false, true);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2320 */     ReportSelectionsHelper.addReleasingFamilyLabelFamilyMultSelect("family", context, query, reportForm);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2326 */     ReportSelectionsHelper.addMultSelectionToStringBuffer(query, "header.[label_id]", strLabel, true, "Label", reportForm, false, true);
/*      */ 
/*      */     
/* 2329 */     if (strProductType.equals(Integer.toString(1))) {
/* 2330 */       query.append(" AND digital_flag = 1 ");
/* 2331 */     } else if (strProductType.equals(Integer.toString(0))) {
/* 2332 */       query.append(" AND digital_flag = 0 ");
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2339 */     if (!strArtist.equalsIgnoreCase("")) {
/* 2340 */       query.append(" AND header.[artist] LIKE '%" + MilestoneHelper.escapeSingleQuotes(strArtist) + "%'");
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 2345 */     ReportSelectionsHelper.addStatusToSelect(reportForm, query);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2351 */     if (!strReleaseType.equalsIgnoreCase("") && !strReleaseType.equalsIgnoreCase("all"))
/*      */     {
/* 2353 */       if (strReleaseType.equalsIgnoreCase("commercial")) {
/* 2354 */         query.append(" AND header.[release_type] ='CO'");
/*      */       } else {
/* 2356 */         query.append(" AND header.[release_type] ='PR'");
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 2362 */     if (!strLabelContact.equalsIgnoreCase("") && !strLabelContact.equalsIgnoreCase("0")) {
/* 2363 */       query.append(" AND header.[contact_id] =" + strLabelContact);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 2368 */     if (!strUmlContact.equalsIgnoreCase("") && !strUmlContact.equalsIgnoreCase("0")) {
/* 2369 */       query.append(" AND mfg.[uml_id] =" + strUmlContact);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2377 */     if (!beginDate.equalsIgnoreCase("") || !endDate.equalsIgnoreCase("")) {
/* 2378 */       query.append(" AND ((header.[status] ='TBS' OR header.[status] ='ITW') OR (");
/*      */     }
/* 2380 */     if (!beginDate.equalsIgnoreCase("")) {
/* 2381 */       query.append(" street_date >= '" + MilestoneHelper.escapeSingleQuotes(beginDate) + "'");
/*      */     }
/* 2383 */     if (!endDate.equalsIgnoreCase(""))
/*      */     {
/* 2385 */       if (!beginDate.equalsIgnoreCase("")) {
/* 2386 */         query.append(" AND street_date <= '" + MilestoneHelper.escapeSingleQuotes(endDate) + "'");
/*      */       } else {
/* 2388 */         query.append(" street_date <= '" + MilestoneHelper.escapeSingleQuotes(endDate) + "'");
/*      */       } 
/*      */     }
/* 2391 */     if (!beginDate.equalsIgnoreCase("") || !endDate.equalsIgnoreCase("")) query.append("))");
/*      */     
/* 2393 */     query.append(" ORDER BY header.[release_id], UPPER(header.[artist]), header.[street_date], UPPER(header.[title])");
/*      */ 
/*      */     
/* 2396 */     JdbcConnector connector = MilestoneHelper.getConnector(query.toString());
/*      */     
/* 2398 */     connector.setForwardOnly(false);
/*      */ 
/*      */     
/* 2401 */     connector.runQuery();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2411 */     Vector selections = null;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2416 */     int totalCount = 0;
/* 2417 */     int tenth = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2432 */     totalCount = connector.getRowCount();
/*      */     
/* 2434 */     tenth = totalCount / 10;
/*      */     
/* 2436 */     if (tenth < 1) {
/* 2437 */       tenth = 1;
/*      */     }
/*      */     
/*      */     try {
/* 2441 */       HttpServletResponse sresponse = context.getResponse();
/* 2442 */       context.putDelivery("status", new String("start_gathering"));
/* 2443 */       context.putDelivery("percent", new String("10"));
/* 2444 */       if (!ReportingServices.usingReportServicesByContext(context))
/*      */       {
/* 2446 */         context.includeJSP("status.jsp", "hiddenFrame");
/* 2447 */         sresponse.setContentType("text/plain");
/* 2448 */         sresponse.flushBuffer();
/*      */       }
/*      */     
/* 2451 */     } catch (Exception exception) {}
/*      */ 
/*      */ 
/*      */     
/* 2455 */     int recordCount = 0;
/* 2456 */     int count = 0;
/*      */ 
/*      */ 
/*      */     
/* 2460 */     selections = new Vector();
/*      */     
/* 2462 */     while (connector.more()) {
/*      */ 
/*      */       
/*      */       try {
/*      */ 
/*      */ 
/*      */         
/* 2469 */         if (count < recordCount / tenth) {
/*      */           
/* 2471 */           count = recordCount / tenth;
/* 2472 */           HttpServletResponse sresponse = context.getResponse();
/* 2473 */           context.putDelivery("status", new String("start_gathering"));
/* 2474 */           context.putDelivery("percent", new String(String.valueOf(count * 10)));
/* 2475 */           if (!ReportingServices.usingReportServicesByContext(context)) {
/*      */             
/* 2477 */             context.includeJSP("status.jsp", "hiddenFrame");
/* 2478 */             sresponse.setContentType("text/plain");
/* 2479 */             sresponse.flushBuffer();
/*      */           } 
/*      */         } 
/*      */         
/* 2483 */         recordCount++;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 2494 */         if (bParent) {
/*      */           
/* 2496 */           String prefixId = "";
/* 2497 */           String tmpTitleId = connector.getField("title_id", "");
/* 2498 */           String tmpSelectionNo = connector.getField("selection_no", "");
/*      */           
/* 2500 */           prefixId = SelectionManager.getLookupObjectValue((PrefixCode)getLookupObject(connector.getField("prefix", ""), Cache.getPrefixCodes()));
/*      */           
/* 2502 */           if (!tmpTitleId.equalsIgnoreCase(String.valueOf(prefixId) + tmpSelectionNo)) {
/*      */             
/* 2504 */             connector.next();
/*      */             
/*      */             continue;
/*      */           } 
/*      */         } 
/*      */         
/* 2510 */         int numberOfUnits = 0;
/*      */         
/*      */         try {
/* 2513 */           numberOfUnits = connector.getIntegerField("pfm.[units_per_set]");
/*      */         }
/* 2515 */         catch (Exception exception) {}
/*      */ 
/*      */         
/* 2518 */         Selection selection = null;
/*      */         
/* 2520 */         selection = new Selection();
/* 2521 */         selection.setSelectionID(connector.getIntegerField("release_id"));
/*      */         
/* 2523 */         String selectionNo = "";
/* 2524 */         if (connector.getFieldByName("selection_no") != null)
/* 2525 */           selectionNo = connector.getFieldByName("selection_no"); 
/* 2526 */         selection.setSelectionNo(selectionNo);
/*      */         
/* 2528 */         selection.setProjectID(connector.getField("project_no", ""));
/*      */         
/* 2530 */         String titleId = "";
/* 2531 */         if (connector.getFieldByName("title_id") != null)
/* 2532 */           titleId = connector.getFieldByName("title_id"); 
/* 2533 */         selection.setTitleID(titleId);
/*      */         
/* 2535 */         selection.setTitle(connector.getField("title", ""));
/* 2536 */         selection.setArtistFirstName(connector.getField("artist_first_name", ""));
/* 2537 */         selection.setArtistLastName(connector.getField("artist_last_name", ""));
/* 2538 */         selection.setArtist(connector.getField("artist", ""));
/* 2539 */         selection.setASide(connector.getField("side_a_title", ""));
/* 2540 */         selection.setBSide(connector.getField("side_b_title", ""));
/*      */         
/* 2542 */         selection.setProductCategory((ProductCategory)getLookupObject(connector.getField("product_line"), 
/* 2543 */               Cache.getProductCategories()));
/* 2544 */         selection.setReleaseType((ReleaseType)getLookupObject(connector.getField("release_type"), 
/* 2545 */               Cache.getReleaseTypes()));
/*      */         
/* 2547 */         selection.setSelectionConfig(getSelectionConfigObject(connector.getField("configuration"), 
/* 2548 */               Cache.getSelectionConfigs()));
/* 2549 */         selection.setSelectionSubConfig(getSelectionSubConfigObject(connector.getField("sub_configuration"), 
/* 2550 */               selection.getSelectionConfig()));
/*      */         
/* 2552 */         selection.setUpc(connector.getField("upc", ""));
/*      */         
/* 2554 */         String sellCodeString = connector.getFieldByName("price_code");
/* 2555 */         if (sellCodeString != null)
/*      */         {
/* 2557 */           selection.setPriceCode(SelectionManager.getInstance().getPriceCode(sellCodeString));
/*      */         }
/*      */         
/* 2560 */         selection.setSellCode(sellCodeString);
/*      */         
/* 2562 */         selection.setGenre((Genre)getLookupObject(connector.getField("genre"), 
/* 2563 */               Cache.getMusicTypes()));
/* 2564 */         selection.setFamily((Family)MilestoneHelper.getStructureObject(connector.getIntegerField("family_id")));
/* 2565 */         selection.setCompany((Company)MilestoneHelper.getStructureObject(connector.getIntegerField("company_id")));
/* 2566 */         selection.setDivision((Division)MilestoneHelper.getStructureObject(connector.getIntegerField("division_id")));
/* 2567 */         selection.setEnvironment((Environment)MilestoneHelper.getStructureObject(connector.getIntegerField("environment_id")));
/* 2568 */         selection.setLabel((Label)MilestoneHelper.getStructureObject(connector.getIntegerField("label_id")));
/*      */ 
/*      */         
/* 2571 */         selection.setImprint(connector.getField("imprint", ""));
/*      */ 
/*      */         
/* 2574 */         selection.setReleaseFamilyId(connector.getIntegerField("release_family_id"));
/*      */         
/* 2576 */         String streetDateString = connector.getFieldByName("street_date");
/* 2577 */         if (streetDateString != null) {
/* 2578 */           selection.setStreetDate(MilestoneHelper.getDatabaseDate(streetDateString));
/*      */         }
/* 2580 */         String internationalDateString = connector.getFieldByName("international_date");
/* 2581 */         if (internationalDateString != null) {
/* 2582 */           selection.setInternationalDate(MilestoneHelper.getDatabaseDate(internationalDateString));
/*      */         }
/* 2584 */         selection.setOtherContact(connector.getField("coordinator_contacts", ""));
/*      */         
/* 2586 */         if (UserManager.getInstance().getUser(connector.getIntegerField("contact_id")) != null) {
/* 2587 */           selection.setLabelContact(UserManager.getInstance().getUser(connector.getIntegerField("contact_id")));
/*      */         }
/* 2589 */         selection.setSelectionStatus((SelectionStatus)getLookupObject(connector.getField("status"), 
/* 2590 */               Cache.getSelectionStatusList()));
/* 2591 */         selection.setHoldSelection(connector.getBoolean("hold_indicator"));
/*      */         
/* 2593 */         selection.setHoldReason(connector.getField("hold_reason", ""));
/* 2594 */         selection.setComments(connector.getField("comments", ""));
/* 2595 */         selection.setSpecialPackaging(connector.getBoolean("special_pkg_indicator"));
/* 2596 */         selection.setNumberOfUnits(numberOfUnits);
/* 2597 */         selection.setPressAndDistribution(connector.getBoolean("pd_indicator"));
/*      */         
/* 2599 */         selection.setPrefixID((PrefixCode)getLookupObject(connector.getField("prefix"), Cache.getPrefixCodes()));
/* 2600 */         selection.setSelectionPackaging(connector.getField("packaging", ""));
/*      */         
/* 2602 */         String impactDateString = connector.getFieldByName("impact_date");
/* 2603 */         if (impactDateString != null) {
/* 2604 */           selection.setImpactDate(MilestoneHelper.getDatabaseDate(impactDateString));
/*      */         }
/* 2606 */         String lastUpdateDateString = connector.getFieldByName("last_updated_on");
/* 2607 */         if (lastUpdateDateString != null) {
/* 2608 */           selection.setLastUpdateDate(MilestoneHelper.getDatabaseDate(lastUpdateDateString));
/*      */         }
/* 2610 */         selection.setLastUpdatingUser(UserManager.getInstance().getUser(connector.getIntegerField("last_updated_by")));
/*      */ 
/*      */         
/* 2613 */         String originDateString = connector.getFieldByName("entered_on");
/* 2614 */         if (originDateString != null) {
/* 2615 */           selection.setOriginDate(MilestoneHelper.getDatabaseDate(originDateString));
/*      */         }
/*      */ 
/*      */ 
/*      */         
/* 2620 */         User umlContact = UserManager.getInstance().getUser(connector.getIntegerField("mfg.[uml_id]"));
/* 2621 */         selection.setUmlContact(umlContact);
/* 2622 */         selection.setPlant(getLookupObject(connector.getField("mfg.[plant]"), Cache.getVendors()));
/* 2623 */         selection.setDistribution(getLookupObject(connector.getField("mfg.[distribution]"), Cache.getDistributionCodes()));
/* 2624 */         selection.setPoQuantity(connector.getIntegerField("mfg.[order_qty]"));
/* 2625 */         selection.setCompletedQuantity(connector.getIntegerField("mfg.[complete_qty]"));
/* 2626 */         selection.setManufacturingComments(connector.getField("mfg.[order_comments]", ""));
/* 2627 */         selection.setPrice(connector.getFloat("mfg.[list_price]"));
/*      */         
/* 2629 */         selection.setFullSelection(true);
/*      */         
/* 2631 */         String strDistribution = (reportForm.getStringValue("distribution") != null) ? reportForm.getStringValue("distribution") : "";
/* 2632 */         String strFuture2 = (reportForm.getStringValue("Future2") != null) ? reportForm.getStringValue("Future2") : "";
/*      */ 
/*      */         
/* 2635 */         if (!strDistribution.equalsIgnoreCase("") && !strDistribution.equalsIgnoreCase("All")) {
/*      */           
/* 2637 */           String selDistribution = SelectionManager.getLookupObjectValue(selection.getDistribution());
/*      */           
/* 2639 */           if (!selDistribution.equalsIgnoreCase(strDistribution.trim())) {
/*      */             continue;
/*      */           }
/*      */         } 
/* 2643 */         int nextReleaseId = connector.getIntegerField("release_id");
/*      */         
/* 2645 */         Schedule schedule = new Schedule();
/* 2646 */         schedule.setSelectionID(nextReleaseId);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 2655 */         Vector precacheSchedule = new Vector();
/* 2656 */         while (connector.more() && connector.getIntegerField("release_id") == nextReleaseId) {
/*      */           
/* 2658 */           ScheduledTask scheduledTask = null;
/*      */           
/* 2660 */           scheduledTask = new ScheduledTask();
/*      */ 
/*      */ 
/*      */           
/* 2664 */           scheduledTask.setReleaseID(connector.getIntegerField("release_Id"));
/*      */ 
/*      */           
/* 2667 */           scheduledTask.setTaskID(connector.getIntegerField("task_id"));
/*      */ 
/*      */           
/* 2670 */           scheduledTask.setOwner((Family)MilestoneHelper.getStructureObject(connector.getIntegerField("taskOwner")));
/*      */ 
/*      */           
/* 2673 */           String dueDateString = connector.getField("taskDue", "");
/* 2674 */           if (dueDateString.length() > 0) {
/* 2675 */             scheduledTask.setDueDate(MilestoneHelper.getDatabaseDate(dueDateString));
/*      */           }
/*      */           
/* 2678 */           String completionDateString = connector.getField("taskComplete", "");
/* 2679 */           if (completionDateString.length() > 0) {
/* 2680 */             scheduledTask.setCompletionDate(MilestoneHelper.getDatabaseDate(completionDateString));
/*      */           }
/*      */           
/* 2683 */           String taskStatus = connector.getField("taskStatus", "");
/* 2684 */           if (taskStatus.length() > 1) {
/* 2685 */             scheduledTask.setScheduledTaskStatus(taskStatus);
/*      */           }
/*      */           
/* 2688 */           int day = connector.getIntegerField("taskDayOfWeek");
/* 2689 */           if (day > 0) {
/* 2690 */             scheduledTask.setDayOfTheWeek(new Day(connector.getIntegerField("taskDayOfWeek")));
/*      */           }
/*      */           
/* 2693 */           int weeks = connector.getIntegerField("taskWeeksToRelease");
/* 2694 */           if (weeks > 0) {
/* 2695 */             scheduledTask.setWeeksToRelease(connector.getIntegerField("taskWeeksToRelease"));
/*      */           }
/*      */           
/* 2698 */           String vendorString = connector.getField("taskVendor", "");
/* 2699 */           if (vendorString.length() > 0) {
/* 2700 */             scheduledTask.setVendor(vendorString);
/*      */           }
/*      */           
/* 2703 */           int taskAbbrevID = connector.getIntegerField("abbrev_id");
/* 2704 */           scheduledTask.setTaskAbbreviationID(taskAbbrevID);
/*      */ 
/*      */           
/* 2707 */           int taskID = connector.getIntegerField("task_id");
/* 2708 */           scheduledTask.setScheduledTaskID(taskID);
/*      */ 
/*      */ 
/*      */           
/* 2712 */           String taskDept = connector.getField("department", "");
/* 2713 */           scheduledTask.setDepartment(taskDept);
/*      */ 
/*      */           
/* 2716 */           scheduledTask.setKeyTask(connector.getBoolean("taskKey"));
/*      */ 
/*      */           
/* 2719 */           scheduledTask.setAuthorizationName(connector.getField("taskAuthName", ""));
/*      */ 
/*      */           
/* 2722 */           String authDateString = connector.getField("taskAuthDate", "");
/* 2723 */           if (authDateString.length() > 0) {
/* 2724 */             scheduledTask.setAuthorizationDate(MilestoneHelper.getDatabaseDate(authDateString));
/*      */           }
/*      */           
/* 2727 */           String comments = connector.getField("taskComments", "");
/* 2728 */           scheduledTask.setComments(comments);
/*      */ 
/*      */           
/* 2731 */           scheduledTask.setName(connector.getField("name", ""));
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 2736 */           precacheSchedule.add(scheduledTask);
/*      */           
/* 2738 */           scheduledTask = null;
/*      */           
/* 2740 */           if (connector.more()) {
/*      */             
/* 2742 */             connector.next();
/* 2743 */             recordCount++;
/*      */             
/*      */             continue;
/*      */           } 
/*      */           
/*      */           break;
/*      */         } 
/* 2750 */         schedule.setTasks(precacheSchedule);
/*      */         
/* 2752 */         selection.setSchedule(schedule);
/*      */         
/* 2754 */         selections.add(selection);
/*      */       
/*      */       }
/* 2757 */       catch (Exception exception) {}
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2764 */     connector.close();
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2769 */     return selections;
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
/* 2783 */     System.out.println("ReportSelections::getSelectionsForAddsReport()");
/*      */     
/* 2785 */     User user = (User)context.getSessionValue("user");
/* 2786 */     int userId = 0;
/* 2787 */     if (user != null) {
/* 2788 */       userId = user.getUserId();
/*      */     }
/*      */     
/* 2791 */     return getAllSelectionsForUserUmlAdds(context, reportProductType);
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
/* 2807 */     System.out.println("ReportSelections::getSelectionsForMovesReport()");
/*      */     
/* 2809 */     User user = (User)context.getSessionValue("user");
/* 2810 */     int userId = 0;
/* 2811 */     if (user != null) {
/* 2812 */       userId = user.getUserId();
/*      */     }
/*      */     
/* 2815 */     return getAllSelectionsForUserUmlMoves(context, reportProductType);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Vector getAllSelectionsForUserUmlAdds(Context context, int reportProductType) {
/* 2825 */     System.out.println("ReportSelections::getAllSelectionsForUserUmlAdds()");
/* 2826 */     Vector companies = MilestoneHelper.getUserCompanies(context);
/* 2827 */     Company company = null;
/* 2828 */     Vector precache = new Vector();
/* 2829 */     Selection selection = null;
/* 2830 */     StringBuffer query = new StringBuffer(200);
/*      */ 
/*      */     
/* 2833 */     Form reportForm = (Form)context.getSessionValue("reportForm");
/* 2834 */     boolean bScheduled = (reportForm.getElement("ScheduledReleases") != null) ? ((FormCheckBox)reportForm.getElement("ScheduledReleases")).isChecked() : 0;
/*      */     
/* 2836 */     String sqlJoin = "LEFT";
/* 2837 */     if (bScheduled) {
/* 2838 */       sqlJoin = "INNER";
/*      */     }
/* 2840 */     query.append("SELECT header.[release_id], header.[artist],  header.[title], header.[artist_first_name], header.[artist_last_name],  header.[artist_first_name] + ' ' + header.[artist_last_name] AS fl_artist, header.[street_date], header.[configuration], header.[sub_configuration], header.[upc], header.[prefix], header.[selection_no],  header.[hold_reason], header.[hold_indicator], header.[pd_indicator],  header.[comments], header.[status], header.[family_id], header.[release_family_id],  header.[imprint], header.[environment_id], header.[company_id], header.[label_id], header.entered_on, header.[units],detail.* FROM Release_Header header with (nolock) " + 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 2849 */         sqlJoin + " JOIN" + 
/* 2850 */         " Release_SubDetail detail with (nolock) ON (header.[release_id] = detail.[release_id])" + 
/* 2851 */         " WHERE (header.status ='TBS' OR header.status ='Active') AND (");
/*      */     
/* 2853 */     String[] strFamily = ReportSelectionsHelper.getMultiSelectionListValues("family", reportForm);
/* 2854 */     String[] strEnvironment = ReportSelectionsHelper.getMultiSelectionListValues("environment", reportForm);
/* 2855 */     String[] strCompany = ReportSelectionsHelper.getMultiSelectionListValues("company", reportForm);
/* 2856 */     String[] strLabel = ReportSelectionsHelper.getMultiSelectionListValues("Label", reportForm);
/*      */ 
/*      */ 
/*      */     
/* 2860 */     String strProductType = (reportForm.getStringValue("ProductType") != null) ? reportForm.getStringValue("ProductType") : "";
/* 2861 */     if (strProductType.equals("")) {
/*      */       
/* 2863 */       strProductType = String.valueOf(reportProductType);
/*      */     
/*      */     }
/* 2866 */     else if (strProductType.equals("Physical")) {
/* 2867 */       strProductType = String.valueOf(0);
/* 2868 */     } else if (strProductType.equals("Digital")) {
/* 2869 */       strProductType = String.valueOf(1);
/* 2870 */     } else if (strProductType.equals("Both")) {
/* 2871 */       strProductType = String.valueOf(2);
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2879 */     ReportSelectionsHelper.addMultSelectionToStringBuffer(query, "environment_id", strEnvironment, true, "environment", reportForm, true, false);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2886 */     ReportSelectionsHelper.addMultSelectionToStringBuffer(query, "company_id", strCompany, true, "company", reportForm, false, true);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2892 */     ReportSelectionsHelper.addReleasingFamilyLabelFamilyMultSelect("family", context, query, reportForm);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2898 */     ReportSelectionsHelper.addMultSelectionToStringBuffer(query, "label_id", strLabel, true, "Label", reportForm, false, true);
/*      */ 
/*      */     
/* 2901 */     if (strProductType.equals(Integer.toString(1))) {
/* 2902 */       query.append(" AND digital_flag = 1 ");
/* 2903 */     } else if (strProductType.equals(Integer.toString(0))) {
/* 2904 */       query.append(" AND digital_flag = 0 ");
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2912 */     String[] strConfiguration = null;
/*      */     try {
/* 2914 */       MilestoneFormDropDownMenu configList = (MilestoneFormDropDownMenu)reportForm.getElement("configurationList");
/* 2915 */       if (configList != null) {
/* 2916 */         ArrayList configListAl = configList.getStringValues();
/* 2917 */         if (configListAl != null) {
/*      */           
/* 2919 */           strConfiguration = new String[configListAl.size()];
/* 2920 */           strConfiguration = (String[])configListAl.toArray(strConfiguration);
/*      */         } 
/*      */       } 
/* 2923 */     } catch (Exception e) {
/* 2924 */       e.printStackTrace();
/* 2925 */       System.out.println("<<< Subconfiguration vector execption " + e.getMessage());
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2933 */     String[] strSubconfiguration = null;
/*      */     try {
/* 2935 */       MilestoneFormDropDownMenu subconfigList = (MilestoneFormDropDownMenu)reportForm.getElement("subconfigurationList");
/* 2936 */       if (subconfigList != null) {
/* 2937 */         ArrayList subconfigListAl = subconfigList.getStringValues();
/* 2938 */         if (subconfigListAl != null) {
/*      */           
/* 2940 */           strSubconfiguration = new String[subconfigListAl.size()];
/* 2941 */           strSubconfiguration = (String[])subconfigListAl.toArray(strSubconfiguration);
/*      */         } 
/*      */       } 
/* 2944 */     } catch (Exception e) {
/* 2945 */       e.printStackTrace();
/* 2946 */       System.out.println("<<< Subconfiguration vector execption " + e.getMessage());
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2953 */     if (strSubconfiguration != null && 
/* 2954 */       !strSubconfiguration[0].trim().equalsIgnoreCase("0") && 
/* 2955 */       !strSubconfiguration[0].trim().equalsIgnoreCase("") && 
/* 2956 */       !strSubconfiguration[0].trim().equalsIgnoreCase("all")) {
/*      */ 
/*      */       
/* 2959 */       boolean addOr = false;
/* 2960 */       query.append(" AND (");
/* 2961 */       for (int x = 0; x < strSubconfiguration.length; x++) {
/* 2962 */         if (addOr)
/* 2963 */           query.append(" OR "); 
/* 2964 */         String txtvalue = strSubconfiguration[x];
/* 2965 */         if (txtvalue.indexOf("[") != -1) {
/*      */           
/* 2967 */           String txtconfigcode = txtvalue.substring(1, txtvalue.indexOf("]"));
/* 2968 */           String txtsubconfigcode = txtvalue.substring(txtvalue.indexOf("]") + 1, txtvalue.length());
/* 2969 */           query.append(" (header.[configuration] = '" + txtconfigcode.trim() + "' AND ");
/* 2970 */           query.append(" header.[sub_configuration] = '" + txtsubconfigcode.trim() + "') ");
/*      */         } else {
/*      */           
/* 2973 */           query.append(" header.[sub_configuration] = '" + txtvalue.trim() + "' ");
/*      */         } 
/* 2975 */         addOr = true;
/*      */       } 
/* 2977 */       query.append(") ");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     }
/* 2986 */     else if (strConfiguration != null && !strConfiguration[0].equalsIgnoreCase("") && !strConfiguration[0].equalsIgnoreCase("0") && 
/* 2987 */       !strConfiguration[0].equalsIgnoreCase("all")) {
/*      */       
/* 2989 */       boolean addOr = false;
/* 2990 */       query.append(" AND (");
/* 2991 */       for (int x = 0; x < strConfiguration.length; x++) {
/* 2992 */         if (addOr) {
/* 2993 */           query.append(" OR ");
/*      */         }
/* 2995 */         query.append(" header.[configuration] = '" + strConfiguration[x].trim() + "' ");
/* 2996 */         addOr = true;
/*      */       } 
/* 2998 */       query.append(") ");
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
/* 3030 */     String beginDate = "";
/* 3031 */     beginDate = (reportForm.getStringValue("beginEffectiveDate") != null && reportForm.getStringValue("beginEffectiveDate").length() > 0) ? reportForm.getStringValue("beginEffectiveDate") : "";
/* 3032 */     if (beginDate.equalsIgnoreCase(""))
/* 3033 */       beginDate = "01/01/1900"; 
/* 3034 */     beginDate = String.valueOf(beginDate) + " 00:00:00";
/*      */     
/* 3036 */     String endDate = "";
/* 3037 */     endDate = (reportForm.getStringValue("endEffectiveDate") != null && reportForm.getStringValue("endEffectiveDate").length() > 0) ? reportForm.getStringValue("endEffectiveDate") : "";
/* 3038 */     if (endDate.equalsIgnoreCase(""))
/* 3039 */       endDate = "01/01/2200"; 
/* 3040 */     endDate = String.valueOf(endDate) + " 23:59:59";
/*      */     
/* 3042 */     if (!beginDate.equalsIgnoreCase("") || !endDate.equalsIgnoreCase("")) {
/* 3043 */       query.append(" AND (");
/*      */     }
/*      */     
/* 3046 */     if (!beginDate.equalsIgnoreCase("")) {
/* 3047 */       query.append(" entered_on >= '" + MilestoneHelper.escapeSingleQuotes(beginDate) + "'");
/*      */     }
/* 3049 */     if (!endDate.equalsIgnoreCase(""))
/*      */     {
/* 3051 */       if (!beginDate.equalsIgnoreCase("")) {
/* 3052 */         query.append(" AND entered_on <= '" + MilestoneHelper.escapeSingleQuotes(endDate) + "'");
/*      */       } else {
/* 3054 */         query.append(" entered_on <= '" + MilestoneHelper.escapeSingleQuotes(endDate) + "'");
/*      */       } 
/*      */     }
/* 3057 */     if (!beginDate.equalsIgnoreCase("") || !endDate.equalsIgnoreCase("")) query.append(")");
/*      */ 
/*      */     
/* 3060 */     query.append(") ORDER BY street_date, configuration, selection_no");
/*      */ 
/*      */ 
/*      */     
/* 3064 */     Iterator formIterator = reportForm.getElements();
/* 3065 */     Vector distCoIDs = new Vector();
/* 3066 */     while (formIterator.hasNext()) {
/*      */ 
/*      */       
/* 3069 */       FormElement field = (FormElement)formIterator.next();
/* 3070 */       String fieldName = field.getName();
/* 3071 */       if (fieldName.startsWith("distCo")) {
/*      */         
/* 3073 */         FormCheckBox fCheck = (FormCheckBox)field;
/* 3074 */         if (fCheck.isChecked()) {
/* 3075 */           distCoIDs.add(fieldName.substring(6, fieldName.length()));
/*      */         }
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3083 */     JdbcConnector connector = MilestoneHelper.getConnector(query.toString());
/* 3084 */     connector.setForwardOnly(false);
/* 3085 */     connector.runQuery();
/* 3086 */     int totalCount = 0;
/* 3087 */     int tenth = 0;
/* 3088 */     totalCount = connector.getRowCount();
/*      */ 
/*      */     
/* 3091 */     tenth = totalCount / 5;
/*      */     
/* 3093 */     if (tenth < 1) {
/* 3094 */       tenth = 1;
/*      */     }
/*      */     
/*      */     try {
/* 3098 */       HttpServletResponse sresponse = context.getResponse();
/* 3099 */       context.putDelivery("status", new String("start_gathering"));
/* 3100 */       context.putDelivery("percent", new String("10"));
/* 3101 */       if (!ReportingServices.usingReportServicesByContext(context))
/*      */       {
/* 3103 */         context.includeJSP("status.jsp", "hiddenFrame");
/* 3104 */         sresponse.setContentType("text/plain");
/* 3105 */         sresponse.flushBuffer();
/*      */       }
/*      */     
/* 3108 */     } catch (Exception exception) {}
/*      */ 
/*      */ 
/*      */     
/* 3112 */     int recordCount = 0;
/* 3113 */     int count = 0;
/*      */ 
/*      */     
/* 3116 */     while (connector.more()) {
/*      */ 
/*      */       
/*      */       try {
/* 3120 */         if (count < recordCount / tenth) {
/*      */           
/* 3122 */           count = recordCount / tenth;
/* 3123 */           HttpServletResponse sresponse = context.getResponse();
/* 3124 */           context.putDelivery("status", new String("start_gathering"));
/* 3125 */           context.putDelivery("percent", new String(String.valueOf(count * 10)));
/* 3126 */           if (!ReportingServices.usingReportServicesByContext(context)) {
/*      */             
/* 3128 */             context.includeJSP("status.jsp", "hiddenFrame");
/* 3129 */             sresponse.setContentType("text/plain");
/* 3130 */             sresponse.flushBuffer();
/*      */           } 
/*      */         } 
/*      */         
/* 3134 */         recordCount++;
/*      */ 
/*      */         
/* 3137 */         selection = new Selection();
/*      */ 
/*      */         
/* 3140 */         selection.setSelectionID(connector.getIntegerField("release_id"));
/*      */         
/* 3142 */         selection.setTitle(connector.getField("title", ""));
/*      */ 
/*      */         
/* 3145 */         selection.setArtistFirstName(connector.getField("artist_first_name", ""));
/*      */ 
/*      */         
/* 3148 */         selection.setArtistLastName(connector.getField("artist_last_name", ""));
/*      */ 
/*      */         
/* 3151 */         selection.setFlArtist(connector.getField("fl_artist", ""));
/*      */ 
/*      */ 
/*      */         
/* 3155 */         String streetDateString = connector.getFieldByName("street_date");
/* 3156 */         if (streetDateString != null) {
/* 3157 */           selection.setStreetDate(MilestoneHelper.getDatabaseDate(streetDateString));
/*      */         }
/* 3159 */         selection.setUpc(connector.getField("upc", ""));
/*      */         
/* 3161 */         selection.setSelectionConfig(
/* 3162 */             getSelectionConfigObject(connector.getField("configuration"), 
/* 3163 */               Cache.getSelectionConfigs()));
/*      */ 
/*      */ 
/*      */         
/* 3167 */         selection.setSelectionSubConfig(getSelectionSubConfigObject(connector.getField("sub_configuration"), selection.getSelectionConfig()));
/* 3168 */         selection.setPrefixID((PrefixCode)getLookupObject(connector.getField("prefix"), Cache.getPrefixCodes()));
/* 3169 */         selection.setSelectionNo(connector.getField("selection_no"));
/* 3170 */         selection.setSelectionStatus((SelectionStatus)getLookupObject(connector.getField("status"), 
/* 3171 */               Cache.getSelectionStatusList()));
/*      */         
/* 3173 */         selection.setOriginDate(MilestoneHelper.getDatabaseDate(connector.getField("entered_on")));
/* 3174 */         selection.setDistribution(getLookupObject(connector.getField("distribution"), Cache.getDistributionCodes()));
/* 3175 */         selection.setPlant(getLookupObject(connector.getField("detail.plant"), 
/* 3176 */               Cache.getVendors()));
/* 3177 */         selection.setHoldReason(connector.getField("hold_reason"));
/* 3178 */         selection.setComments(connector.getField("comments"));
/* 3179 */         selection.setPoQuantity(connector.getInt("order_qty"));
/*      */         
/* 3181 */         selection.setNumberOfUnits(connector.getInt("units"));
/* 3182 */         selection.setCompletedQuantity(connector.getInt("complete_qty"));
/* 3183 */         selection.setLabel((Label)MilestoneHelper.getStructureObject(connector.getInt("label_id")));
/* 3184 */         selection.setPressAndDistribution(connector.getBoolean("pd_indicator"));
/*      */ 
/*      */         
/* 3187 */         selection.setImprint(connector.getField("imprint", ""));
/* 3188 */         selection.setReleaseFamilyId(connector.getIntegerField("release_family_id"));
/*      */ 
/*      */         
/* 3191 */         Label currentLabel = selection.getLabel();
/* 3192 */         int selectionsDistCompany = MilestoneHelper_2.getLabelDistCoId(currentLabel.getStructureID());
/*      */         
/* 3194 */         if (selectionsDistCompany == -1) {
/* 3195 */           selectionsDistCompany = Integer.parseInt("1");
/*      */         }
/*      */ 
/*      */         
/* 3199 */         if (distCoIDs.contains(Integer.toString(selectionsDistCompany)))
/*      */         {
/* 3201 */           precache.add(selection);
/*      */         }
/*      */         
/* 3204 */         selection = null;
/* 3205 */         connector.next();
/*      */       }
/* 3207 */       catch (Exception e) {
/*      */         
/* 3209 */         System.out.println("**** Exception: " + e.toString());
/* 3210 */         connector.next();
/*      */       } 
/*      */     } 
/*      */     
/* 3214 */     connector.close();
/* 3215 */     company = null;
/*      */     
/* 3217 */     return precache;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Vector getAllSelectionsForUserUmlMoves(Context context, int reportProductType) {
/* 3224 */     System.out.println("ReportSelections::getAllSelectionsForUserUmlMoves()");
/* 3225 */     Vector companies = MilestoneHelper.getUserCompanies(context);
/* 3226 */     Company company = null;
/* 3227 */     Vector precache = new Vector();
/* 3228 */     Selection selection = null;
/* 3229 */     StringBuffer query = new StringBuffer(200);
/*      */ 
/*      */     
/* 3232 */     Form reportForm = (Form)context.getSessionValue("reportForm");
/*      */     
/* 3234 */     boolean bScheduled = (reportForm.getElement("ScheduledReleases") != null) ? ((FormCheckBox)reportForm.getElement("ScheduledReleases")).isChecked() : 0;
/* 3235 */     String sqlJoin = "LEFT";
/* 3236 */     if (bScheduled) {
/* 3237 */       sqlJoin = "INNER";
/*      */     }
/* 3239 */     query.append("SELECT header.[release_id], header.[artist],  header.[title], header.[artist_first_name], header.[artist_last_name],  header.[artist_first_name] + ' ' + header.[artist_last_name] AS fl_artist, header.[street_date], header.[configuration], header.[sub_configuration], header.[upc], header.[prefix], header.[selection_no],  header.[hold_reason], header.[hold_indicator], header.[pd_indicator],  header.[comments], header.[status],header.[family_id], header.[release_family_id], audit.[logged_on],  header.[imprint], header.[environment_id], header.[company_id], header.[label_id], header.entered_on, header.[units], detail.* FROM Release_Header header with (nolock) INNER JOIN Audit_Release_Header audit with (nolock) ON header.release_id = audit.release_id " + 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 3250 */         sqlJoin + " JOIN Release_SubDetail detail with (nolock) ON audit.release_id = detail.release_id " + 
/* 3251 */         " WHERE (header.status ='TBS' OR header.status ='Active') AND (");
/*      */ 
/*      */ 
/*      */     
/* 3255 */     String[] strFamily = ReportSelectionsHelper.getMultiSelectionListValues("family", reportForm);
/* 3256 */     String[] strEnvironment = ReportSelectionsHelper.getMultiSelectionListValues("environment", reportForm);
/* 3257 */     String[] strCompany = ReportSelectionsHelper.getMultiSelectionListValues("company", reportForm);
/* 3258 */     String[] strLabel = ReportSelectionsHelper.getMultiSelectionListValues("Label", reportForm);
/*      */ 
/*      */     
/* 3261 */     String strProductType = (reportForm.getStringValue("ProductType") != null) ? 
/* 3262 */       reportForm.getStringValue("ProductType") : "";
/* 3263 */     if (strProductType.equals("")) {
/*      */       
/* 3265 */       strProductType = String.valueOf(reportProductType);
/*      */ 
/*      */     
/*      */     }
/* 3269 */     else if (strProductType.equals("Physical")) {
/* 3270 */       strProductType = String.valueOf(0);
/*      */     }
/* 3272 */     else if (strProductType.equals("Digital")) {
/* 3273 */       strProductType = String.valueOf(1);
/*      */     }
/* 3275 */     else if (strProductType.equals("Both")) {
/* 3276 */       strProductType = String.valueOf(2);
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3284 */     ReportSelectionsHelper.addMultSelectionToStringBuffer(query, "header.environment_id", strEnvironment, true, "environment", reportForm, true, false);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3292 */     ReportSelectionsHelper.addMultSelectionToStringBuffer(query, "header.company_id", strCompany, true, "company", reportForm, false, true);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3298 */     ReportSelectionsHelper.addReleasingFamilyLabelFamilyMultSelect("family", context, query, reportForm);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3303 */     ReportSelectionsHelper.addMultSelectionToStringBuffer(query, "header.label_id", strLabel, true, "Label", reportForm, false, true);
/*      */ 
/*      */ 
/*      */     
/* 3307 */     if (strProductType.equals(Integer.toString(1))) {
/* 3308 */       query.append(" AND digital_flag = 1 ");
/* 3309 */     } else if (strProductType.equals(Integer.toString(0))) {
/* 3310 */       query.append(" AND digital_flag = 0 ");
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3320 */     String[] strConfiguration = null;
/*      */     try {
/* 3322 */       MilestoneFormDropDownMenu configList = (MilestoneFormDropDownMenu)reportForm.getElement("configurationList");
/* 3323 */       if (configList != null) {
/* 3324 */         ArrayList configListAl = configList.getStringValues();
/* 3325 */         if (configListAl != null) {
/*      */           
/* 3327 */           strConfiguration = new String[configListAl.size()];
/* 3328 */           strConfiguration = (String[])configListAl.toArray(strConfiguration);
/*      */         } 
/*      */       } 
/* 3331 */     } catch (Exception e) {
/* 3332 */       e.printStackTrace();
/* 3333 */       System.out.println("<<< Configuration vector execption " + e.getMessage());
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3339 */     String[] strSubconfiguration = null;
/*      */     try {
/* 3341 */       MilestoneFormDropDownMenu subconfigList = (MilestoneFormDropDownMenu)reportForm.getElement("subconfigurationList");
/* 3342 */       if (subconfigList != null) {
/* 3343 */         ArrayList subconfigListAl = subconfigList.getStringValues();
/* 3344 */         if (subconfigListAl != null) {
/*      */           
/* 3346 */           strSubconfiguration = new String[subconfigListAl.size()];
/* 3347 */           strSubconfiguration = (String[])subconfigListAl.toArray(strSubconfiguration);
/*      */         } 
/*      */       } 
/* 3350 */     } catch (Exception e) {
/* 3351 */       e.printStackTrace();
/* 3352 */       System.out.println("<<< Subconfiguration vector execption " + e.getMessage());
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3358 */     if (strSubconfiguration != null && 
/* 3359 */       !strSubconfiguration[0].trim().equalsIgnoreCase("0") && 
/* 3360 */       !strSubconfiguration[0].trim().equalsIgnoreCase("") && 
/* 3361 */       !strSubconfiguration[0].trim().equalsIgnoreCase("all")) {
/*      */       
/* 3363 */       boolean addOr = false;
/* 3364 */       query.append(" AND (");
/* 3365 */       for (int x = 0; x < strSubconfiguration.length; x++) {
/* 3366 */         if (addOr)
/* 3367 */           query.append(" OR "); 
/* 3368 */         String txtvalue = strSubconfiguration[x];
/* 3369 */         if (txtvalue.indexOf("[") != -1) {
/*      */           
/* 3371 */           String txtconfigcode = txtvalue.substring(1, txtvalue.indexOf("]"));
/* 3372 */           String txtsubconfigcode = txtvalue.substring(txtvalue.indexOf("]") + 1, txtvalue.length());
/* 3373 */           query.append(" (header.[configuration] = '" + txtconfigcode.trim() + "' AND ");
/* 3374 */           query.append(" header.[sub_configuration] = '" + txtsubconfigcode.trim() + "') ");
/*      */         } else {
/*      */           
/* 3377 */           query.append(" header.[sub_configuration] = '" + txtvalue.trim() + "' ");
/*      */         } 
/* 3379 */         addOr = true;
/*      */       } 
/* 3381 */       query.append(") ");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     }
/* 3389 */     else if (strConfiguration != null && !strConfiguration[0].equalsIgnoreCase("") && !strConfiguration[0].equalsIgnoreCase("0") && 
/* 3390 */       !strConfiguration[0].equalsIgnoreCase("all")) {
/*      */       
/* 3392 */       boolean addOr = false;
/* 3393 */       query.append(" AND (");
/* 3394 */       for (int x = 0; x < strConfiguration.length; x++) {
/* 3395 */         if (addOr) {
/* 3396 */           query.append(" OR ");
/*      */         }
/* 3398 */         query.append(" header.[configuration] = '" + strConfiguration[x].trim() + "' ");
/* 3399 */         addOr = true;
/*      */       } 
/* 3401 */       query.append(") ");
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
/* 3429 */     String beginDate = "";
/* 3430 */     beginDate = (reportForm.getStringValue("beginEffectiveDate") != null && reportForm.getStringValue("beginEffectiveDate").length() > 0) ? reportForm.getStringValue("beginEffectiveDate") : "";
/* 3431 */     if (beginDate.equalsIgnoreCase(""))
/* 3432 */       beginDate = "01/01/1900"; 
/* 3433 */     beginDate = String.valueOf(beginDate) + " 00:00:00";
/*      */     
/* 3435 */     String endDate = "";
/* 3436 */     endDate = (reportForm.getStringValue("endEffectiveDate") != null && reportForm.getStringValue("endEffectiveDate").length() > 0) ? reportForm.getStringValue("endEffectiveDate") : "";
/* 3437 */     if (endDate.equalsIgnoreCase(""))
/* 3438 */       endDate = "01/01/2200"; 
/* 3439 */     endDate = String.valueOf(endDate) + " 23:59:59";
/*      */     
/* 3441 */     if (!beginDate.equalsIgnoreCase("") || !endDate.equalsIgnoreCase("")) {
/* 3442 */       query.append(" AND (");
/*      */     }
/* 3444 */     if (!beginDate.equalsIgnoreCase("")) {
/* 3445 */       query.append(" audit.logged_on >= '" + MilestoneHelper.escapeSingleQuotes(beginDate) + "'");
/*      */     }
/* 3447 */     if (!endDate.equalsIgnoreCase(""))
/*      */     {
/* 3449 */       if (!beginDate.equalsIgnoreCase("")) {
/* 3450 */         query.append(" AND audit.logged_on <= '" + MilestoneHelper.escapeSingleQuotes(endDate) + "'");
/*      */       } else {
/* 3452 */         query.append(" audit.logged_on <= '" + MilestoneHelper.escapeSingleQuotes(endDate) + "'");
/*      */       } 
/*      */     }
/* 3455 */     if (!beginDate.equalsIgnoreCase("") || !endDate.equalsIgnoreCase("")) query.append(")");
/*      */ 
/*      */     
/* 3458 */     query.append(") ORDER BY UPPER(header.artist), header.street_date, UPPER(header.title)");
/*      */ 
/*      */ 
/*      */     
/* 3462 */     Iterator formIterator = reportForm.getElements();
/* 3463 */     Vector distCoIDs = new Vector();
/* 3464 */     while (formIterator.hasNext()) {
/*      */ 
/*      */       
/* 3467 */       FormElement field = (FormElement)formIterator.next();
/* 3468 */       String fieldName = field.getName();
/* 3469 */       if (fieldName.startsWith("distCo")) {
/*      */         
/* 3471 */         FormCheckBox fCheck = (FormCheckBox)field;
/* 3472 */         if (fCheck.isChecked()) {
/* 3473 */           distCoIDs.add(fieldName.substring(6, fieldName.length()));
/*      */         }
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 3480 */     JdbcConnector connector = MilestoneHelper.getConnector(query.toString());
/* 3481 */     connector.setForwardOnly(false);
/* 3482 */     connector.runQuery();
/* 3483 */     int totalCount = 0;
/* 3484 */     int tenth = 0;
/* 3485 */     totalCount = connector.getRowCount();
/*      */ 
/*      */     
/* 3488 */     tenth = totalCount / 5;
/*      */     
/* 3490 */     if (tenth < 1) {
/* 3491 */       tenth = 1;
/*      */     }
/*      */     
/*      */     try {
/* 3495 */       HttpServletResponse sresponse = context.getResponse();
/* 3496 */       context.putDelivery("status", new String("start_gathering"));
/* 3497 */       context.putDelivery("percent", new String("10"));
/* 3498 */       if (!ReportingServices.usingReportServicesByContext(context))
/*      */       {
/* 3500 */         context.includeJSP("status.jsp", "hiddenFrame");
/* 3501 */         sresponse.setContentType("text/plain");
/* 3502 */         sresponse.flushBuffer();
/*      */       }
/*      */     
/* 3505 */     } catch (Exception exception) {}
/*      */ 
/*      */ 
/*      */     
/* 3509 */     int recordCount = 0;
/* 3510 */     int count = 0;
/*      */ 
/*      */     
/* 3513 */     while (connector.more()) {
/*      */ 
/*      */       
/*      */       try {
/* 3517 */         if (count < recordCount / tenth) {
/*      */           
/* 3519 */           count = recordCount / tenth;
/* 3520 */           HttpServletResponse sresponse = context.getResponse();
/* 3521 */           context.putDelivery("status", new String("start_gathering"));
/* 3522 */           context.putDelivery("percent", new String(String.valueOf(count * 10)));
/* 3523 */           if (!ReportingServices.usingReportServicesByContext(context)) {
/*      */             
/* 3525 */             context.includeJSP("status.jsp", "hiddenFrame");
/* 3526 */             sresponse.setContentType("text/plain");
/* 3527 */             sresponse.flushBuffer();
/*      */           } 
/*      */         } 
/*      */         
/* 3531 */         recordCount++;
/*      */ 
/*      */         
/* 3534 */         selection = new Selection();
/*      */ 
/*      */         
/* 3537 */         selection.setSelectionID(connector.getIntegerField("release_id"));
/*      */         
/* 3539 */         selection.setTitle(connector.getField("title", ""));
/*      */ 
/*      */         
/* 3542 */         selection.setArtistFirstName(connector.getField("artist_first_name", ""));
/*      */ 
/*      */         
/* 3545 */         selection.setArtistLastName(connector.getField("artist_last_name", ""));
/*      */ 
/*      */         
/* 3548 */         selection.setFlArtist(connector.getField("fl_artist", ""));
/*      */ 
/*      */         
/* 3551 */         String streetDateString = connector.getFieldByName("street_date");
/* 3552 */         if (streetDateString != null) {
/* 3553 */           selection.setStreetDate(MilestoneHelper.getDatabaseDate(streetDateString));
/*      */         }
/* 3555 */         selection.setUpc(connector.getField("upc", ""));
/*      */         
/* 3557 */         selection.setSelectionConfig(
/* 3558 */             getSelectionConfigObject(connector.getField("configuration"), 
/* 3559 */               Cache.getSelectionConfigs()));
/*      */ 
/*      */ 
/*      */         
/* 3563 */         selection.setSelectionSubConfig(getSelectionSubConfigObject(connector.getField("sub_configuration"), selection.getSelectionConfig()));
/* 3564 */         selection.setPrefixID((PrefixCode)getLookupObject(connector.getField("prefix"), Cache.getPrefixCodes()));
/* 3565 */         selection.setSelectionNo(connector.getField("selection_no"));
/* 3566 */         selection.setSelectionStatus((SelectionStatus)getLookupObject(connector.getField("status"), 
/* 3567 */               Cache.getSelectionStatusList()));
/*      */ 
/*      */         
/* 3570 */         selection.setLastStreetUpdateDate(MilestoneHelper.getDatabaseDate(connector.getField("logged_on")));
/* 3571 */         selection.setOriginDate(MilestoneHelper.getDatabaseDate(connector.getField("entered_on")));
/* 3572 */         selection.setPlant(getLookupObject(connector.getField("plant"), 
/* 3573 */               Cache.getVendors()));
/* 3574 */         selection.setDistribution(getLookupObject(connector.getField("distribution"), Cache.getDistributionCodes()));
/* 3575 */         selection.setHoldReason(connector.getField("hold_reason"));
/* 3576 */         selection.setComments(connector.getField("comments"));
/* 3577 */         selection.setPoQuantity(connector.getInt("order_qty"));
/*      */         
/* 3579 */         selection.setNumberOfUnits(connector.getInt("units"));
/* 3580 */         selection.setCompletedQuantity(connector.getInt("complete_qty"));
/* 3581 */         selection.setLabel((Label)MilestoneHelper.getStructureObject(connector.getInt("label_id")));
/* 3582 */         selection.setPressAndDistribution(connector.getBoolean("pd_indicator"));
/*      */ 
/*      */         
/* 3585 */         selection.setImprint(connector.getField("imprint", ""));
/* 3586 */         selection.setReleaseFamilyId(connector.getIntegerField("release_family_id"));
/*      */ 
/*      */ 
/*      */         
/* 3590 */         Label currentLabel = selection.getLabel();
/* 3591 */         int selectionsDistCompany = MilestoneHelper_2.getLabelDistCoId(currentLabel.getStructureID());
/*      */         
/* 3593 */         if (selectionsDistCompany == -1) {
/* 3594 */           selectionsDistCompany = Integer.parseInt("1");
/*      */         }
/*      */ 
/*      */         
/* 3598 */         if (distCoIDs.contains(Integer.toString(selectionsDistCompany)))
/*      */         {
/* 3600 */           precache.add(selection);
/*      */         }
/*      */         
/* 3603 */         selection = null;
/* 3604 */         connector.next();
/*      */       
/*      */       }
/* 3607 */       catch (Exception e) {
/*      */         
/* 3609 */         System.out.println("**** Exception: " + e.toString());
/* 3610 */         connector.next();
/*      */       } 
/*      */     } 
/*      */     
/* 3614 */     connector.close();
/* 3615 */     company = null;
/*      */     
/* 3617 */     return precache;
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
/* 3633 */     Vector companies = MilestoneHelper.getUserCompanies(context);
/* 3634 */     Company company = null;
/* 3635 */     Vector precache = new Vector();
/* 3636 */     Selection selection = null;
/* 3637 */     StringBuffer query = new StringBuffer(300);
/*      */ 
/*      */     
/* 3640 */     Form reportForm = (Form)context.getSessionValue("reportForm");
/*      */     
/* 3642 */     query.append("SELECT DISTINCT headeraudit.[release_id], headeraudit.[artist],  headeraudit.[title], headeraudit.[artist_first_name], headeraudit.[artist_last_name],  headeraudit.[artist_first_name] + ' ' + headeraudit.[artist_last_name] AS fl_artist, headeraudit.[street_date], headeraudit.[configuration], headeraudit.[sub_configuration], headeraudit.[upc], headeraudit.[prefix], headeraudit.[selection_no],  headeraudit.[status], headeraudit.[family_id], headeraudit.[release_family_id],  headeraudit.[imprint],  headeraudit.[company_id], headeraudit.[label_id],  headeraudit.[audit_date], ");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3652 */     query.append(" headeraudit.[prefix] as audit_prefix, headeraudit.[selection_no] as audit_selection_no, headeraudit.[upc] as audit_upc, headeraudit.[status] as audit_status, headeraudit.[street_date] as audit_street_date ");
/*      */     
/* 3654 */     if (activity.equals("adds")) {
/* 3655 */       query.append(" FROM Release_Header_Audit headeraudit with (nolock), Release_Header header with (nolock) WHERE (headeraudit.release_id = header.release_id) AND ");
/*      */     } else {
/* 3657 */       query.append(" FROM Release_Header_Audit headeraudit with (nolock) WHERE ");
/*      */     } 
/*      */     
/* 3660 */     String[] strFamily = ReportSelectionsHelper.getMultiSelectionListValues("family", reportForm);
/* 3661 */     String[] strEnvironment = ReportSelectionsHelper.getMultiSelectionListValues("environment", reportForm);
/* 3662 */     String[] strCompany = ReportSelectionsHelper.getMultiSelectionListValues("company", reportForm);
/* 3663 */     String[] strLabel = ReportSelectionsHelper.getMultiSelectionListValues("Label", reportForm);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3675 */     ReportSelectionsHelper.addMultSelectionToStringBuffer(query, "headeraudit.environment_id", strEnvironment, true, "environment", reportForm, true, false);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3682 */     ReportSelectionsHelper.addMultSelectionToStringBuffer(query, "headeraudit.company_id", strCompany, true, "company", reportForm, false, true);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3687 */     ReportSelectionsHelper.addReleasingFamilyLabelFamilySelectForAudit("family", context, query, reportForm, "headeraudit");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3693 */     ReportSelectionsHelper.addMultSelectionToStringBuffer(query, "headeraudit.label_id", strLabel, true, "Label", reportForm, false, true);
/*      */     
/* 3695 */     query.append(" AND headeraudit.digital_flag = 0 ");
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3700 */     String strLabelContact = (reportForm.getStringValue("contact") != null) ? reportForm.getStringValue("contact") : "";
/* 3701 */     if (!strLabelContact.equals("") && !strLabelContact.equals("0")) {
/* 3702 */       query.append(" AND (header.contact_id = '" + strLabelContact.trim() + "') ");
/*      */     }
/*      */ 
/*      */     
/* 3706 */     String strReleaseType = (reportForm.getStringValue("releaseType") != null) ? reportForm.getStringValue("releaseType") : "";
/* 3707 */     if (!strReleaseType.equalsIgnoreCase("") && !strReleaseType.equalsIgnoreCase("all"))
/*      */     {
/* 3709 */       if (strReleaseType.equalsIgnoreCase("commercial")) {
/* 3710 */         query.append(" AND (header.[release_type] ='CO') ");
/*      */       } else {
/* 3712 */         query.append(" AND (header.[release_type] ='PR') ");
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/* 3717 */     if (activity.equals("adds")) {
/* 3718 */       ReportSelectionsHelper.addStatusToSelect(reportForm, query);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3727 */     String[] strConfiguration = null;
/*      */     try {
/* 3729 */       MilestoneFormDropDownMenu configList = (MilestoneFormDropDownMenu)reportForm.getElement("configurationList");
/* 3730 */       if (configList != null) {
/* 3731 */         ArrayList configListAl = configList.getStringValues();
/* 3732 */         if (configListAl != null) {
/*      */           
/* 3734 */           strConfiguration = new String[configListAl.size()];
/* 3735 */           strConfiguration = (String[])configListAl.toArray(strConfiguration);
/*      */         } 
/*      */       } 
/* 3738 */     } catch (Exception e) {
/* 3739 */       e.printStackTrace();
/* 3740 */       System.out.println("<<< Subconfiguration vector execption " + e.getMessage());
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3748 */     String[] strSubconfiguration = null;
/*      */     try {
/* 3750 */       MilestoneFormDropDownMenu subconfigList = (MilestoneFormDropDownMenu)reportForm.getElement("subconfigurationList");
/* 3751 */       if (subconfigList != null) {
/* 3752 */         ArrayList subconfigListAl = subconfigList.getStringValues();
/* 3753 */         if (subconfigListAl != null) {
/*      */           
/* 3755 */           strSubconfiguration = new String[subconfigListAl.size()];
/* 3756 */           strSubconfiguration = (String[])subconfigListAl.toArray(strSubconfiguration);
/*      */         } 
/*      */       } 
/* 3759 */     } catch (Exception e) {
/* 3760 */       e.printStackTrace();
/* 3761 */       System.out.println("<<< Subconfiguration vector execption " + e.getMessage());
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3768 */     if (strSubconfiguration != null && 
/* 3769 */       !strSubconfiguration[0].trim().equalsIgnoreCase("0") && 
/* 3770 */       !strSubconfiguration[0].trim().equalsIgnoreCase("") && 
/* 3771 */       !strSubconfiguration[0].trim().equalsIgnoreCase("all")) {
/*      */ 
/*      */       
/* 3774 */       boolean addOr = false;
/* 3775 */       query.append(" AND (");
/* 3776 */       for (int x = 0; x < strSubconfiguration.length; x++) {
/* 3777 */         if (addOr)
/* 3778 */           query.append(" OR "); 
/* 3779 */         String txtvalue = strSubconfiguration[x];
/* 3780 */         if (txtvalue.indexOf("[") != -1) {
/*      */           
/* 3782 */           String txtconfigcode = txtvalue.substring(1, txtvalue.indexOf("]"));
/* 3783 */           String txtsubconfigcode = txtvalue.substring(txtvalue.indexOf("]") + 1, txtvalue.length());
/* 3784 */           query.append(" (headeraudit.[configuration] = '" + txtconfigcode.trim() + "' AND ");
/* 3785 */           query.append(" headeraudit.[sub_configuration] = '" + txtsubconfigcode.trim() + "') ");
/*      */         } else {
/*      */           
/* 3788 */           query.append(" headeraudit.[sub_configuration] = '" + txtvalue.trim() + "' ");
/*      */         } 
/* 3790 */         addOr = true;
/*      */       } 
/* 3792 */       query.append(") ");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     }
/* 3801 */     else if (strConfiguration != null && !strConfiguration[0].equalsIgnoreCase("") && !strConfiguration[0].equalsIgnoreCase("0") && 
/* 3802 */       !strConfiguration[0].equalsIgnoreCase("all")) {
/*      */       
/* 3804 */       boolean addOr = false;
/* 3805 */       query.append(" AND (");
/* 3806 */       for (int x = 0; x < strConfiguration.length; x++) {
/* 3807 */         if (addOr) {
/* 3808 */           query.append(" OR ");
/*      */         }
/* 3810 */         query.append(" headeraudit.[configuration] = '" + strConfiguration[x].trim() + "' ");
/* 3811 */         addOr = true;
/*      */       } 
/* 3813 */       query.append(") ");
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3819 */     if (activity.equals("adds")) {
/* 3820 */       query.append(" AND (headeraudit.audit_code = 'I') ");
/* 3821 */       query.append(" AND (headeraudit.status IN ('ACTIVE', 'TBS')) ");
/*      */     } 
/*      */     
/* 3824 */     if (activity.equals("deletes")) {
/* 3825 */       query.append(" AND (headeraudit.audit_code = 'D') ");
/*      */     }
/*      */     
/* 3828 */     String beginDate = "";
/* 3829 */     beginDate = (reportForm.getStringValue("beginEffectiveDate") != null && reportForm.getStringValue("beginEffectiveDate").length() > 0) ? reportForm.getStringValue("beginEffectiveDate") : "";
/* 3830 */     if (beginDate.equalsIgnoreCase(""))
/* 3831 */       beginDate = "01/01/1900"; 
/* 3832 */     beginDate = String.valueOf(beginDate) + " 00:00:00";
/*      */     
/* 3834 */     String endDate = "";
/* 3835 */     endDate = (reportForm.getStringValue("endEffectiveDate") != null && reportForm.getStringValue("endEffectiveDate").length() > 0) ? reportForm.getStringValue("endEffectiveDate") : "";
/* 3836 */     if (endDate.equalsIgnoreCase(""))
/* 3837 */       endDate = "01/01/2200"; 
/* 3838 */     endDate = String.valueOf(endDate) + " 23:59:59";
/*      */     
/* 3840 */     if (!beginDate.equalsIgnoreCase("") || !endDate.equalsIgnoreCase("")) {
/* 3841 */       query.append(" AND (");
/*      */     }
/* 3843 */     String tableName = "headeraudit";
/*      */     
/* 3845 */     if (!beginDate.equalsIgnoreCase("")) {
/* 3846 */       query.append(" " + tableName + ".audit_date >= '" + MilestoneHelper.escapeSingleQuotes(beginDate) + "'");
/*      */     }
/* 3848 */     if (!endDate.equalsIgnoreCase("")) {
/* 3849 */       if (!beginDate.equalsIgnoreCase("")) {
/* 3850 */         query.append(" AND " + tableName + ".audit_date <= '" + MilestoneHelper.escapeSingleQuotes(endDate) + "'");
/*      */       } else {
/* 3852 */         query.append(" " + tableName + ".audit_date <= '" + MilestoneHelper.escapeSingleQuotes(endDate) + "'");
/*      */       } 
/*      */     }
/* 3855 */     if (!beginDate.equalsIgnoreCase("") || !endDate.equalsIgnoreCase("")) query.append(")");
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3860 */     String beginDate2 = "";
/* 3861 */     beginDate2 = (reportForm.getStringValue("beginDate") != null && reportForm.getStringValue("beginDate").length() > 0) ? reportForm.getStringValue("beginDate") : "";
/* 3862 */     if (beginDate2.equalsIgnoreCase(""))
/* 3863 */       beginDate2 = "01/01/1900"; 
/* 3864 */     beginDate2 = String.valueOf(beginDate2) + " 00:00:00";
/*      */     
/* 3866 */     String endDate2 = "";
/* 3867 */     endDate2 = (reportForm.getStringValue("endDate") != null && reportForm.getStringValue("endDate").length() > 0) ? reportForm.getStringValue("endDate") : "";
/* 3868 */     if (endDate2.equalsIgnoreCase(""))
/* 3869 */       endDate2 = "01/01/2200"; 
/* 3870 */     endDate2 = String.valueOf(endDate2) + " 23:59:59";
/*      */     
/* 3872 */     if (!beginDate2.equalsIgnoreCase("") || !endDate2.equalsIgnoreCase("")) {
/* 3873 */       query.append(" AND (");
/*      */     }
/* 3875 */     if (!beginDate2.equalsIgnoreCase("")) {
/* 3876 */       query.append(" headeraudit.street_date >= '" + MilestoneHelper.escapeSingleQuotes(beginDate2) + "'");
/*      */     }
/* 3878 */     if (!endDate2.equalsIgnoreCase(""))
/*      */     {
/* 3880 */       if (!beginDate2.equalsIgnoreCase("")) {
/* 3881 */         query.append(" AND headeraudit.street_date <= '" + MilestoneHelper.escapeSingleQuotes(endDate2) + "'");
/*      */       } else {
/* 3883 */         query.append(" headeraudit.street_date <= '" + MilestoneHelper.escapeSingleQuotes(endDate2) + "'");
/*      */       } 
/*      */     }
/* 3886 */     if (!beginDate2.equalsIgnoreCase("") || !endDate2.equalsIgnoreCase("")) query.append(")");
/*      */ 
/*      */     
/* 3889 */     query.append(" ORDER BY headeraudit.street_date, headeraudit.configuration, headeraudit.selection_no");
/*      */     
/* 3891 */     JdbcConnector connector = MilestoneHelper.getConnector(query.toString());
/* 3892 */     connector.setForwardOnly(false);
/* 3893 */     connector.runQuery();
/* 3894 */     int totalCount = 0;
/* 3895 */     int tenth = 0;
/* 3896 */     totalCount = connector.getRowCount();
/*      */ 
/*      */     
/* 3899 */     tenth = totalCount / 5;
/*      */     
/* 3901 */     if (tenth < 1) {
/* 3902 */       tenth = 1;
/*      */     }
/*      */     
/*      */     try {
/* 3906 */       HttpServletResponse sresponse = context.getResponse();
/* 3907 */       context.putDelivery("status", new String("start_gathering"));
/* 3908 */       context.putDelivery("percent", new String("10"));
/* 3909 */       if (!ReportingServices.usingReportServicesByContext(context))
/*      */       {
/* 3911 */         context.includeJSP("status.jsp", "hiddenFrame");
/* 3912 */         sresponse.setContentType("text/plain");
/* 3913 */         sresponse.flushBuffer();
/*      */       }
/*      */     
/* 3916 */     } catch (Exception exception) {}
/*      */ 
/*      */ 
/*      */     
/* 3920 */     int recordCount = 0;
/* 3921 */     int count = 0;
/*      */ 
/*      */     
/* 3924 */     while (connector.more()) {
/*      */ 
/*      */       
/*      */       try {
/* 3928 */         if (count < recordCount / tenth) {
/*      */           
/* 3930 */           count = recordCount / tenth;
/* 3931 */           HttpServletResponse sresponse = context.getResponse();
/* 3932 */           context.putDelivery("status", new String("start_gathering"));
/* 3933 */           context.putDelivery("percent", new String(String.valueOf(count * 10)));
/* 3934 */           if (!ReportingServices.usingReportServicesByContext(context)) {
/*      */             
/* 3936 */             context.includeJSP("status.jsp", "hiddenFrame");
/* 3937 */             sresponse.setContentType("text/plain");
/* 3938 */             sresponse.flushBuffer();
/*      */           } 
/*      */         } 
/*      */         
/* 3942 */         recordCount++;
/*      */ 
/*      */         
/* 3945 */         selection = new Selection();
/*      */ 
/*      */         
/* 3948 */         selection.setSelectionID(connector.getIntegerField("release_id"));
/*      */         
/* 3950 */         selection.setTitle(connector.getField("title", ""));
/*      */ 
/*      */         
/* 3953 */         selection.setArtistFirstName(connector.getField("artist_first_name", ""));
/*      */ 
/*      */         
/* 3956 */         selection.setArtistLastName(connector.getField("artist_last_name", ""));
/*      */ 
/*      */         
/* 3959 */         selection.setArtist(connector.getField("artist", ""));
/*      */ 
/*      */         
/* 3962 */         String streetDateString = connector.getFieldByName("street_date");
/* 3963 */         if (streetDateString != null) {
/* 3964 */           selection.setStreetDate(MilestoneHelper.getDatabaseDate(streetDateString));
/*      */         }
/* 3966 */         selection.setUpc(connector.getField("upc", ""));
/*      */         
/* 3968 */         selection.setSelectionConfig(
/* 3969 */             getSelectionConfigObject(connector.getField("configuration"), 
/* 3970 */               Cache.getSelectionConfigs()));
/*      */ 
/*      */ 
/*      */         
/* 3974 */         selection.setSelectionSubConfig(getSelectionSubConfigObject(connector.getField("sub_configuration"), selection.getSelectionConfig()));
/* 3975 */         selection.setPrefixID((PrefixCode)getLookupObject(connector.getField("prefix"), Cache.getPrefixCodes()));
/* 3976 */         selection.setSelectionNo(connector.getField("selection_no"));
/* 3977 */         selection.setSelectionStatus((SelectionStatus)getLookupObject(connector.getField("status"), Cache.getSelectionStatusList()));
/* 3978 */         selection.setLabel((Label)MilestoneHelper.getStructureObject(connector.getInt("label_id")));
/*      */ 
/*      */         
/* 3981 */         selection.setImprint(connector.getField("imprint", ""));
/* 3982 */         selection.setReleaseFamilyId(connector.getInt("release_family_id"));
/*      */ 
/*      */         
/* 3985 */         String auditDateString = connector.getFieldByName("audit_date");
/* 3986 */         if (auditDateString != null) {
/* 3987 */           auditDateString = String.valueOf(auditDateString) + ".0";
/* 3988 */           selection.setAuditDate(MilestoneHelper.getDatabaseDate(auditDateString));
/*      */         } 
/* 3990 */         selection.setAuditPrefixID((PrefixCode)getLookupObject(connector.getField("audit_prefix"), Cache.getPrefixCodes()));
/* 3991 */         selection.setAuditSelectionNo(connector.getField("audit_selection_no"));
/* 3992 */         selection.setAuditUPC(connector.getField("audit_upc"));
/* 3993 */         selection.setAuditSelectionStatus((SelectionStatus)getLookupObject(connector.getField("audit_status"), Cache.getSelectionStatusList()));
/*      */         
/* 3995 */         String auditStreetDateString = connector.getFieldByName("audit_street_date");
/* 3996 */         if (auditStreetDateString != null) {
/* 3997 */           auditStreetDateString = String.valueOf(auditStreetDateString) + ".0";
/* 3998 */           selection.setAuditStreetDate(MilestoneHelper.getDatabaseDate(auditStreetDateString));
/*      */         } 
/*      */ 
/*      */         
/* 4002 */         boolean addToSelection = true;
/* 4003 */         String strDistribution = (reportForm.getStringValue("distribution") != null) ? reportForm.getStringValue("distribution") : "";
/* 4004 */         if (!strDistribution.equalsIgnoreCase("") && !strDistribution.equalsIgnoreCase("All")) {
/* 4005 */           String selDistribution = "";
/* 4006 */           if (selection.getLabel().getDistribution() == 1) {
/* 4007 */             selDistribution = "East";
/*      */           }
/* 4009 */           else if (selection.getLabel().getDistribution() == 0) {
/* 4010 */             selDistribution = "West";
/*      */           } 
/* 4012 */           if (!selDistribution.equalsIgnoreCase(strDistribution.trim())) {
/* 4013 */             addToSelection = false;
/*      */           }
/*      */         } 
/* 4016 */         if (addToSelection) {
/* 4017 */           precache.add(selection);
/*      */         }
/* 4019 */         selection = null;
/*      */         
/* 4021 */         connector.next();
/*      */       
/*      */       }
/* 4024 */       catch (Exception e) {
/*      */         
/* 4026 */         System.out.println("**** Exception: " + e.toString());
/* 4027 */         connector.next();
/*      */       } 
/*      */     } 
/*      */     
/* 4031 */     connector.close();
/* 4032 */     company = null;
/*      */     
/* 4034 */     return precache;
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
/* 4057 */     Vector companies = MilestoneHelper.getUserCompanies(context);
/* 4058 */     Company company = null;
/* 4059 */     Vector precache = new Vector();
/* 4060 */     Selection selection = null;
/* 4061 */     StringBuffer query = new StringBuffer(300);
/*      */ 
/*      */ 
/*      */     
/* 4065 */     Form reportForm = (Form)context.getSessionValue("reportForm");
/* 4066 */     query.append("SELECT DISTINCT header.[release_id]");
/* 4067 */     query.append(" FROM Release_Header_Audit header with (nolock) WHERE ");
/*      */     
/* 4069 */     String[] strFamily = ReportSelectionsHelper.getMultiSelectionListValues("family", reportForm);
/* 4070 */     String[] strEnvironment = ReportSelectionsHelper.getMultiSelectionListValues("environment", reportForm);
/* 4071 */     String[] strCompany = ReportSelectionsHelper.getMultiSelectionListValues("company", reportForm);
/* 4072 */     String[] strLabel = ReportSelectionsHelper.getMultiSelectionListValues("Label", reportForm);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 4084 */     ReportSelectionsHelper.addMultSelectionToStringBuffer(query, "header.environment_id", strEnvironment, true, "environment", reportForm, true, false);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 4091 */     ReportSelectionsHelper.addMultSelectionToStringBuffer(query, "header.company_id", strCompany, true, "company", reportForm, false, true);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 4097 */     ReportSelectionsHelper.addReleasingFamilyLabelFamilyMultSelect("family", context, query, reportForm);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 4104 */     ReportSelectionsHelper.addMultSelectionToStringBuffer(query, "header.label_id", strLabel, true, "Label", reportForm, false, true);
/*      */     
/* 4106 */     query.append(" AND header.digital_flag = 0 ");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 4113 */     String[] strConfiguration = null;
/*      */     try {
/* 4115 */       MilestoneFormDropDownMenu configList = (MilestoneFormDropDownMenu)reportForm.getElement("configurationList");
/* 4116 */       if (configList != null) {
/* 4117 */         ArrayList configListAl = configList.getStringValues();
/* 4118 */         if (configListAl != null) {
/*      */           
/* 4120 */           strConfiguration = new String[configListAl.size()];
/* 4121 */           strConfiguration = (String[])configListAl.toArray(strConfiguration);
/*      */         } 
/*      */       } 
/* 4124 */     } catch (Exception e) {
/* 4125 */       e.printStackTrace();
/* 4126 */       System.out.println("<<< Subconfiguration vector execption " + e.getMessage());
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 4134 */     String[] strSubconfiguration = null;
/*      */     try {
/* 4136 */       MilestoneFormDropDownMenu subconfigList = (MilestoneFormDropDownMenu)reportForm.getElement("subconfigurationList");
/* 4137 */       if (subconfigList != null) {
/* 4138 */         ArrayList subconfigListAl = subconfigList.getStringValues();
/* 4139 */         if (subconfigListAl != null) {
/*      */           
/* 4141 */           strSubconfiguration = new String[subconfigListAl.size()];
/* 4142 */           strSubconfiguration = (String[])subconfigListAl.toArray(strSubconfiguration);
/*      */         } 
/*      */       } 
/* 4145 */     } catch (Exception e) {
/* 4146 */       e.printStackTrace();
/* 4147 */       System.out.println("<<< Subconfiguration vector execption " + e.getMessage());
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 4154 */     if (strSubconfiguration != null && 
/* 4155 */       !strSubconfiguration[0].trim().equalsIgnoreCase("0") && 
/* 4156 */       !strSubconfiguration[0].trim().equalsIgnoreCase("") && 
/* 4157 */       !strSubconfiguration[0].trim().equalsIgnoreCase("all")) {
/*      */ 
/*      */       
/* 4160 */       boolean addOr = false;
/* 4161 */       query.append(" AND (");
/* 4162 */       for (int x = 0; x < strSubconfiguration.length; x++) {
/* 4163 */         if (addOr)
/* 4164 */           query.append(" OR "); 
/* 4165 */         String txtvalue = strSubconfiguration[x];
/* 4166 */         if (txtvalue.indexOf("[") != -1) {
/*      */           
/* 4168 */           String txtconfigcode = txtvalue.substring(1, txtvalue.indexOf("]"));
/* 4169 */           String txtsubconfigcode = txtvalue.substring(txtvalue.indexOf("]") + 1, txtvalue.length());
/* 4170 */           query.append(" (header.[configuration] = '" + txtconfigcode.trim() + "' AND ");
/* 4171 */           query.append(" header.[sub_configuration] = '" + txtsubconfigcode.trim() + "') ");
/*      */         } else {
/*      */           
/* 4174 */           query.append(" header.[sub_configuration] = '" + txtvalue.trim() + "' ");
/*      */         } 
/* 4176 */         addOr = true;
/*      */       } 
/* 4178 */       query.append(") ");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     }
/* 4187 */     else if (strConfiguration != null && !strConfiguration[0].equalsIgnoreCase("") && !strConfiguration[0].equalsIgnoreCase("0") && 
/* 4188 */       !strConfiguration[0].equalsIgnoreCase("all")) {
/*      */       
/* 4190 */       boolean addOr = false;
/* 4191 */       query.append(" AND (");
/* 4192 */       for (int x = 0; x < strConfiguration.length; x++) {
/* 4193 */         if (addOr) {
/* 4194 */           query.append(" OR ");
/*      */         }
/* 4196 */         query.append(" header.[configuration] = '" + strConfiguration[x].trim() + "' ");
/* 4197 */         addOr = true;
/*      */       } 
/* 4199 */       query.append(") ");
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 4209 */     if (activity.equals("cancels")) {
/* 4210 */       query.append(" AND (header.status = 'CANCEL') ");
/*      */     }
/*      */     
/* 4213 */     String beginDate = "";
/* 4214 */     beginDate = (reportForm.getStringValue("beginEffectiveDate") != null && reportForm.getStringValue("beginEffectiveDate").length() > 0) ? reportForm.getStringValue("beginEffectiveDate") : "";
/* 4215 */     if (beginDate.equalsIgnoreCase(""))
/* 4216 */       beginDate = "01/01/1900"; 
/* 4217 */     beginDate = String.valueOf(beginDate) + " 00:00:00";
/*      */     
/* 4219 */     String endDate = "";
/* 4220 */     endDate = (reportForm.getStringValue("endEffectiveDate") != null && reportForm.getStringValue("endEffectiveDate").length() > 0) ? reportForm.getStringValue("endEffectiveDate") : "";
/* 4221 */     if (endDate.equalsIgnoreCase(""))
/* 4222 */       endDate = "01/01/2200"; 
/* 4223 */     endDate = String.valueOf(endDate) + " 23:59:59";
/*      */     
/* 4225 */     if (!beginDate.equalsIgnoreCase("") || !endDate.equalsIgnoreCase("")) {
/* 4226 */       query.append(" AND (");
/*      */     }
/* 4228 */     String audittableName = "header";
/* 4229 */     String reltableName = "header";
/*      */     
/* 4231 */     if (!beginDate.equalsIgnoreCase("")) {
/* 4232 */       query.append(" " + audittableName + ".audit_date >= '" + MilestoneHelper.escapeSingleQuotes(beginDate) + "'");
/*      */     }
/* 4234 */     if (!endDate.equalsIgnoreCase("")) {
/* 4235 */       if (!beginDate.equalsIgnoreCase("")) {
/* 4236 */         query.append(" AND " + audittableName + ".audit_date <= '" + MilestoneHelper.escapeSingleQuotes(endDate) + "'");
/*      */       } else {
/* 4238 */         query.append(" " + audittableName + ".audit_date <= '" + MilestoneHelper.escapeSingleQuotes(endDate) + "'");
/*      */       } 
/*      */     }
/* 4241 */     if (!beginDate.equalsIgnoreCase("") || !endDate.equalsIgnoreCase("")) query.append(")");
/*      */ 
/*      */ 
/*      */     
/* 4245 */     String beginDate2 = "";
/* 4246 */     beginDate2 = (reportForm.getStringValue("beginDate") != null && reportForm.getStringValue("beginDate").length() > 0) ? reportForm.getStringValue("beginDate") : "";
/* 4247 */     if (beginDate2.equalsIgnoreCase(""))
/* 4248 */       beginDate2 = "01/01/1900"; 
/* 4249 */     beginDate2 = String.valueOf(beginDate2) + " 00:00:00";
/*      */     
/* 4251 */     String endDate2 = "";
/* 4252 */     endDate2 = (reportForm.getStringValue("endDate") != null && reportForm.getStringValue("endDate").length() > 0) ? reportForm.getStringValue("endDate") : "";
/* 4253 */     if (endDate2.equalsIgnoreCase(""))
/* 4254 */       endDate2 = "01/01/2200"; 
/* 4255 */     endDate2 = String.valueOf(endDate2) + " 23:59:59";
/*      */     
/* 4257 */     if (!beginDate2.equalsIgnoreCase("") || !endDate2.equalsIgnoreCase("")) {
/* 4258 */       query.append(" AND (");
/*      */     }
/* 4260 */     if (!beginDate2.equalsIgnoreCase("")) {
/* 4261 */       query.append(String.valueOf(reltableName) + ".street_date >= '" + MilestoneHelper.escapeSingleQuotes(beginDate2) + "'");
/*      */     }
/* 4263 */     if (!endDate2.equalsIgnoreCase(""))
/*      */     {
/* 4265 */       if (!beginDate2.equalsIgnoreCase("")) {
/* 4266 */         query.append(" AND " + reltableName + ".street_date <= '" + MilestoneHelper.escapeSingleQuotes(endDate2) + "'");
/*      */       } else {
/* 4268 */         query.append(String.valueOf(reltableName) + ".street_date <= '" + MilestoneHelper.escapeSingleQuotes(endDate2) + "'");
/*      */       } 
/*      */     }
/* 4271 */     if (!beginDate2.equalsIgnoreCase("") || !endDate2.equalsIgnoreCase("")) query.append(")");
/*      */ 
/*      */     
/* 4274 */     JdbcConnector connector = MilestoneHelper.getConnector(query.toString());
/* 4275 */     connector.setForwardOnly(false);
/* 4276 */     connector.runQuery();
/*      */     
/* 4278 */     Vector validReleaseIDs = new Vector();
/*      */ 
/*      */     
/* 4281 */     while (connector.more()) {
/*      */ 
/*      */       
/*      */       try {
/* 4285 */         validReleaseIDs.add(connector.getField("release_id"));
/* 4286 */         connector.next();
/*      */       }
/* 4288 */       catch (Exception e) {
/*      */         
/* 4290 */         System.out.println("**** Exception: " + e.toString());
/* 4291 */         connector.next();
/*      */       } 
/*      */     } 
/*      */     
/* 4295 */     connector.close();
/* 4296 */     company = null;
/*      */ 
/*      */ 
/*      */     
/* 4300 */     if (validReleaseIDs.size() == 0) {
/* 4301 */       return precache;
/*      */     }
/*      */ 
/*      */     
/* 4305 */     StringBuffer query2 = new StringBuffer(300);
/* 4306 */     query2.append("SELECT header.[release_id], header.[artist],  header.[title], header.[artist_first_name], header.[artist_last_name],  header.[artist_first_name] + ' ' + header.[artist_last_name] AS fl_artist, header.[configuration], header.[street_date],  header.[sub_configuration], header.[upc], header.[prefix], header.[selection_no],  header.[status], header.[family_id], header.[release_family_id],  header.[imprint], header.[company_id], header.[label_id] ");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 4313 */     query2.append(" FROM Release_Header header with (nolock) ");
/*      */     
/* 4315 */     String relIdString = "";
/* 4316 */     relIdString = "(";
/* 4317 */     for (int i = 0; i < validReleaseIDs.size(); i++) {
/* 4318 */       relIdString = String.valueOf(relIdString) + validReleaseIDs.elementAt(i);
/* 4319 */       if (i + 1 < validReleaseIDs.size())
/* 4320 */         relIdString = String.valueOf(relIdString) + ", "; 
/*      */     } 
/* 4322 */     relIdString = String.valueOf(relIdString) + ")";
/*      */     
/* 4324 */     query2.append(" WHERE header.release_id IN " + relIdString);
/*      */ 
/*      */ 
/*      */     
/* 4328 */     String strLabelContact = (reportForm.getStringValue("contact") != null) ? reportForm.getStringValue("contact") : "";
/* 4329 */     if (!strLabelContact.equals("") && !strLabelContact.equals("0")) {
/* 4330 */       query2.append(" AND (header.contact_id = '" + strLabelContact.trim() + "') ");
/*      */     }
/*      */ 
/*      */     
/* 4334 */     String strReleaseType = (reportForm.getStringValue("releaseType") != null) ? reportForm.getStringValue("releaseType") : "";
/* 4335 */     if (!strReleaseType.equalsIgnoreCase("") && !strReleaseType.equalsIgnoreCase("all"))
/*      */     {
/* 4337 */       if (strReleaseType.equalsIgnoreCase("commercial")) {
/* 4338 */         query2.append(" AND (header.[release_type] ='CO') ");
/*      */       } else {
/* 4340 */         query2.append(" AND (header.[release_type] ='PR') ");
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 4347 */     if (activity.equals("cancels")) {
/* 4348 */       query.append(" AND (header.status = 'CANCEL') ");
/*      */     } else {
/* 4350 */       ReportSelectionsHelper.addStatusToSelect(reportForm, query2);
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 4355 */     connector = MilestoneHelper.getConnector(query2.toString());
/* 4356 */     connector.setForwardOnly(false);
/* 4357 */     connector.runQuery();
/* 4358 */     int totalCount = 0;
/* 4359 */     int tenth = 0;
/* 4360 */     totalCount = connector.getRowCount();
/*      */ 
/*      */     
/* 4363 */     tenth = totalCount / 5;
/*      */     
/* 4365 */     if (tenth < 1) {
/* 4366 */       tenth = 1;
/*      */     }
/*      */     
/*      */     try {
/* 4370 */       HttpServletResponse sresponse = context.getResponse();
/* 4371 */       context.putDelivery("status", new String("start_gathering"));
/* 4372 */       context.putDelivery("percent", new String("10"));
/* 4373 */       if (!ReportingServices.usingReportServicesByContext(context))
/*      */       {
/* 4375 */         context.includeJSP("status.jsp", "hiddenFrame");
/* 4376 */         sresponse.setContentType("text/plain");
/* 4377 */         sresponse.flushBuffer();
/*      */       }
/*      */     
/* 4380 */     } catch (Exception exception) {}
/*      */ 
/*      */ 
/*      */     
/* 4384 */     int recordCount = 0;
/* 4385 */     int count = 0;
/*      */ 
/*      */     
/* 4388 */     while (connector.more()) {
/*      */ 
/*      */       
/*      */       try {
/* 4392 */         if (count < recordCount / tenth) {
/*      */           
/* 4394 */           count = recordCount / tenth;
/* 4395 */           HttpServletResponse sresponse = context.getResponse();
/* 4396 */           context.putDelivery("status", new String("start_gathering"));
/* 4397 */           context.putDelivery("percent", new String(String.valueOf(count * 10)));
/* 4398 */           if (!ReportingServices.usingReportServicesByContext(context)) {
/*      */             
/* 4400 */             context.includeJSP("status.jsp", "hiddenFrame");
/* 4401 */             sresponse.setContentType("text/plain");
/* 4402 */             sresponse.flushBuffer();
/*      */           } 
/*      */         } 
/*      */         
/* 4406 */         recordCount++;
/*      */         
/* 4408 */         selection = new Selection();
/*      */         
/* 4410 */         selection.setSelectionID(connector.getIntegerField("release_id"));
/* 4411 */         selection.setTitle(connector.getField("title", ""));
/* 4412 */         selection.setArtistFirstName(connector.getField("artist_first_name", ""));
/* 4413 */         selection.setArtistLastName(connector.getField("artist_last_name", ""));
/* 4414 */         selection.setArtist(connector.getField("artist", ""));
/*      */         
/* 4416 */         String streetDateString = connector.getFieldByName("street_date");
/* 4417 */         if (streetDateString != null)
/* 4418 */           selection.setStreetDate(MilestoneHelper.getDatabaseDate(streetDateString)); 
/* 4419 */         selection.setUpc(connector.getField("upc", ""));
/* 4420 */         selection.setSelectionConfig(
/* 4421 */             getSelectionConfigObject(connector.getField("configuration"), 
/* 4422 */               Cache.getSelectionConfigs()));
/*      */         
/* 4424 */         selection.setSelectionSubConfig(getSelectionSubConfigObject(connector.getField("sub_configuration"), selection.getSelectionConfig()));
/* 4425 */         selection.setPrefixID((PrefixCode)getLookupObject(connector.getField("prefix"), Cache.getPrefixCodes()));
/* 4426 */         selection.setSelectionNo(connector.getField("selection_no"));
/* 4427 */         selection.setSelectionStatus((SelectionStatus)getLookupObject(connector.getField("status"), Cache.getSelectionStatusList()));
/* 4428 */         selection.setLabel((Label)MilestoneHelper.getStructureObject(connector.getInt("label_id")));
/* 4429 */         selection.setImprint(connector.getField("imprint", ""));
/* 4430 */         selection.setReleaseFamilyId(connector.getInt("release_family_id"));
/*      */ 
/*      */ 
/*      */         
/* 4434 */         boolean addToSelection = true;
/* 4435 */         String strDistribution = (reportForm.getStringValue("distribution") != null) ? reportForm.getStringValue("distribution") : "";
/* 4436 */         if (!strDistribution.equalsIgnoreCase("") && !strDistribution.equalsIgnoreCase("All")) {
/* 4437 */           String selDistribution = "";
/* 4438 */           if (selection.getLabel().getDistribution() == 1) {
/* 4439 */             selDistribution = "East";
/*      */           }
/* 4441 */           else if (selection.getLabel().getDistribution() == 0) {
/* 4442 */             selDistribution = "West";
/*      */           } 
/* 4444 */           if (!selDistribution.equalsIgnoreCase(strDistribution.trim())) {
/* 4445 */             addToSelection = false;
/*      */           }
/*      */         } 
/* 4448 */         if (addToSelection) {
/* 4449 */           precache.add(selection);
/*      */         }
/* 4451 */         selection = null;
/* 4452 */         connector.next();
/*      */       
/*      */       }
/* 4455 */       catch (Exception e) {
/*      */         
/* 4457 */         System.out.println("**** Exception: " + e.toString());
/* 4458 */         connector.next();
/*      */       } 
/*      */     } 
/*      */     
/* 4462 */     connector.close();
/*      */     
/* 4464 */     if (activity.equals("changes"))
/* 4465 */       return getAuditChangeSelections(precache, beginDate, endDate, "changes"); 
/* 4466 */     if (activity.equals("moves")) {
/* 4467 */       return getAuditChangeSelections(precache, beginDate, endDate, "moves");
/*      */     }
/* 4469 */     return getAuditChangeSelections(precache, beginDate, endDate, "cancels");
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
/* 4484 */     Vector auditSelections = new Vector();
/* 4485 */     Vector releaseIdsChecked = new Vector();
/* 4486 */     JdbcConnector connector = new JdbcConnector();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 4493 */     String auditResults = "";
/*      */     
/* 4495 */     for (int i = 0; i < releaseHeaderVector.size(); i++) {
/*      */       
/* 4497 */       Selection sel = (Selection)releaseHeaderVector.elementAt(i);
/*      */ 
/*      */       
/* 4500 */       String selId = Integer.toString(sel.getSelectionID());
/* 4501 */       if (!releaseIdsChecked.contains(selId)) {
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 4506 */         releaseIdsChecked.add(selId);
/*      */ 
/*      */         
/* 4509 */         String query = "";
/* 4510 */         if (activity.equals("changes")) {
/*      */ 
/*      */           
/* 4513 */           query = "sp_get_audit_rows_change " + sel.getSelectionID() + 
/* 4514 */             ",'" + beginAuditDate + "'," + 
/* 4515 */             "'" + endAuditDate + "'";
/*      */         }
/* 4517 */         else if (activity.equals("cancels")) {
/*      */ 
/*      */           
/* 4520 */           query = "sp_get_audit_rows_cancel " + sel.getSelectionID() + 
/* 4521 */             ",'" + beginAuditDate + "'," + 
/* 4522 */             "'" + endAuditDate + "'";
/*      */         }
/*      */         else {
/*      */           
/* 4526 */           query = "sp_get_audit_rows_move " + sel.getSelectionID() + 
/* 4527 */             ",'" + beginAuditDate + "'," + 
/* 4528 */             "'" + endAuditDate + "'";
/*      */         } 
/*      */         
/* 4531 */         connector.setQuery(query);
/* 4532 */         connector.runQuery();
/* 4533 */         auditResults = connector.getField("ReturnString");
/*      */ 
/*      */         
/* 4536 */         if (!auditResults.equals("")) {
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 4541 */           StringTokenizer st = new StringTokenizer(auditResults, "|");
/* 4542 */           Vector auditValues = new Vector();
/* 4543 */           while (st.hasMoreTokens()) {
/* 4544 */             auditValues.add(st.nextToken());
/*      */           }
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 4550 */           if (activity.equals("changes")) {
/* 4551 */             buildAuditSelectionsChanges(sel, auditValues, auditSelections);
/*      */           }
/* 4553 */           else if (activity.equals("cancels")) {
/* 4554 */             buildAuditSelectionsCancels(sel, auditValues, auditSelections);
/*      */           } else {
/* 4556 */             buildAuditSelectionsMoves(sel, auditValues, auditSelections);
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/* 4561 */     connector.close();
/* 4562 */     return auditSelections;
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
/* 4579 */     String finalAuditDate = "";
/*      */     
/* 4581 */     for (int i = 0; i < auditValues.size(); i++) {
/*      */       
/* 4583 */       finalAuditDate = checkForX((String)auditValues.elementAt(i));
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 4588 */       if (i > 0) {
/*      */         try {
/* 4590 */           Selection copiedSelection = (Selection)sel.clone();
/*      */           
/* 4592 */           if (finalAuditDate != null) {
/*      */             
/* 4594 */             finalAuditDate = String.valueOf(finalAuditDate) + ".0";
/* 4595 */             copiedSelection.setAuditDate(MilestoneHelper.getDatabaseDate(finalAuditDate));
/*      */           } 
/*      */ 
/*      */           
/* 4599 */           auditSelections.add(copiedSelection);
/*      */         }
/* 4601 */         catch (CloneNotSupportedException e) {
/* 4602 */           System.out.println("clone exception in ReportSelections.buildAuditSelectionsCancels()");
/*      */         } 
/*      */       } else {
/*      */         
/* 4606 */         if (finalAuditDate != null) {
/*      */           
/* 4608 */           finalAuditDate = String.valueOf(finalAuditDate) + ".0";
/* 4609 */           sel.setAuditDate(MilestoneHelper.getDatabaseDate(finalAuditDate));
/*      */         } 
/*      */ 
/*      */         
/* 4613 */         auditSelections.add(sel);
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
/* 4634 */     String initalPrefix = "";
/* 4635 */     String finalPrefix = "";
/* 4636 */     String initalSelection_no = "";
/* 4637 */     String finalSelection_no = "";
/* 4638 */     String initalAuditDate = "";
/* 4639 */     String finalAuditDate = "";
/*      */     
/* 4641 */     for (int i = 0; i < auditValues.size(); i += 6) {
/*      */       
/* 4643 */       initalPrefix = checkForX((String)auditValues.elementAt(i));
/* 4644 */       initalSelection_no = checkForX((String)auditValues.elementAt(i + 1));
/* 4645 */       initalAuditDate = checkForX((String)auditValues.elementAt(i + 2));
/* 4646 */       finalPrefix = checkForX((String)auditValues.elementAt(i + 3));
/* 4647 */       finalSelection_no = checkForX((String)auditValues.elementAt(i + 4));
/* 4648 */       finalAuditDate = checkForX((String)auditValues.elementAt(i + 5));
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 4653 */       if (i > 0) {
/*      */         try {
/* 4655 */           Selection copiedSelection = (Selection)sel.clone();
/*      */           
/* 4657 */           copiedSelection.setPrefixID((PrefixCode)SelectionManager.getLookupObject(finalPrefix, Cache.getPrefixCodes()));
/* 4658 */           copiedSelection.setAuditPrefixID((PrefixCode)SelectionManager.getLookupObject(initalPrefix, Cache.getPrefixCodes()));
/* 4659 */           copiedSelection.setSelectionNo(finalSelection_no);
/* 4660 */           copiedSelection.setAuditSelectionNo(initalSelection_no);
/* 4661 */           if (finalAuditDate != null) {
/*      */             
/* 4663 */             finalAuditDate = String.valueOf(finalAuditDate) + ".0";
/* 4664 */             copiedSelection.setAuditDate(MilestoneHelper.getDatabaseDate(finalAuditDate));
/*      */           } 
/*      */ 
/*      */           
/* 4668 */           auditSelections.add(copiedSelection);
/*      */         }
/* 4670 */         catch (CloneNotSupportedException e) {
/* 4671 */           System.out.println("clone exception in ReportSelections.buildAuditSelectionsChanges()");
/*      */         } 
/*      */       } else {
/*      */         
/* 4675 */         sel.setPrefixID((PrefixCode)SelectionManager.getLookupObject(finalPrefix, Cache.getPrefixCodes()));
/* 4676 */         sel.setAuditPrefixID((PrefixCode)SelectionManager.getLookupObject(initalPrefix, Cache.getPrefixCodes()));
/* 4677 */         sel.setSelectionNo(finalSelection_no);
/* 4678 */         sel.setAuditSelectionNo(initalSelection_no);
/* 4679 */         if (finalAuditDate != null) {
/*      */           
/* 4681 */           finalAuditDate = String.valueOf(finalAuditDate) + ".0";
/* 4682 */           sel.setAuditDate(MilestoneHelper.getDatabaseDate(finalAuditDate));
/*      */         } 
/*      */ 
/*      */         
/* 4686 */         auditSelections.add(sel);
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
/* 4706 */     String initalStreetDate = "";
/* 4707 */     String initalAuditDate = "";
/* 4708 */     String initialStatus = "";
/*      */     
/* 4710 */     String finalSteetDate = "";
/* 4711 */     String finalAuditDate = "";
/* 4712 */     String finalStatus = "";
/*      */     
/* 4714 */     for (int i = 0; i < auditValues.size(); i += 6) {
/*      */       
/* 4716 */       initialStatus = (String)auditValues.elementAt(i);
/* 4717 */       initalStreetDate = (String)auditValues.elementAt(i + 1);
/* 4718 */       initalAuditDate = (String)auditValues.elementAt(i + 2);
/* 4719 */       finalStatus = (String)auditValues.elementAt(i + 3);
/* 4720 */       finalSteetDate = (String)auditValues.elementAt(i + 4);
/* 4721 */       finalAuditDate = (String)auditValues.elementAt(i + 5);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 4726 */       if (i > 0) {
/*      */         try {
/* 4728 */           Selection copiedSelection = (Selection)sel.clone();
/*      */           
/* 4730 */           copiedSelection.setStreetDate(MilestoneHelper.getDatabaseDate(finalSteetDate));
/* 4731 */           if (initalStreetDate != null) {
/* 4732 */             initalStreetDate = String.valueOf(initalStreetDate) + ".0";
/* 4733 */             copiedSelection.setAuditStreetDate(MilestoneHelper.getDatabaseDate(initalStreetDate));
/*      */           } 
/* 4735 */           copiedSelection.setSelectionStatus((SelectionStatus)MilestoneHelper.getLookupObject(finalStatus, Cache.getSelectionStatusList()));
/* 4736 */           copiedSelection.setAuditSelectionStatus((SelectionStatus)MilestoneHelper.getLookupObject(initialStatus, Cache.getSelectionStatusList()));
/* 4737 */           if (finalAuditDate != null) {
/* 4738 */             finalAuditDate = String.valueOf(finalAuditDate) + ".0";
/* 4739 */             copiedSelection.setAuditDate(MilestoneHelper.getDatabaseDate(finalAuditDate));
/*      */           } 
/*      */           
/* 4742 */           auditSelections.add(copiedSelection);
/*      */         }
/* 4744 */         catch (CloneNotSupportedException e) {
/* 4745 */           System.out.println("clone exception in ReportSelections.buildAuditSelectionsChanges()");
/*      */         } 
/*      */       } else {
/*      */         
/* 4749 */         sel.setStreetDate(MilestoneHelper.getDatabaseDate(finalSteetDate));
/* 4750 */         if (initalStreetDate != null) {
/* 4751 */           initalStreetDate = String.valueOf(initalStreetDate) + ".0";
/* 4752 */           sel.setAuditStreetDate(MilestoneHelper.getDatabaseDate(initalStreetDate));
/*      */         } 
/* 4754 */         sel.setAuditSelectionStatus((SelectionStatus)MilestoneHelper.getLookupObject(initialStatus, Cache.getSelectionStatusList()));
/* 4755 */         sel.setSelectionStatus((SelectionStatus)MilestoneHelper.getLookupObject(finalStatus, Cache.getSelectionStatusList()));
/* 4756 */         if (finalAuditDate != null) {
/* 4757 */           finalAuditDate = String.valueOf(finalAuditDate) + ".0";
/* 4758 */           sel.setAuditDate(MilestoneHelper.getDatabaseDate(finalAuditDate));
/*      */         } 
/*      */         
/* 4761 */         auditSelections.add(sel);
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
/* 4776 */     if (toCheck.equals("XXX")) {
/* 4777 */       return "";
/*      */     }
/* 4779 */     return toCheck;
/*      */   }
/*      */ }


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\ReportSelections.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */