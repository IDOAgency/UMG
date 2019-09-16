package WEB-INF.classes.com.universal.milestone;

import com.techempower.gemini.Context;
import com.techempower.gemini.FormCheckBox;
import com.techempower.gemini.FormElement;
import com.universal.milestone.Cache;
import com.universal.milestone.Company;
import com.universal.milestone.Day;
import com.universal.milestone.Division;
import com.universal.milestone.Environment;
import com.universal.milestone.Family;
import com.universal.milestone.Form;
import com.universal.milestone.Genre;
import com.universal.milestone.JdbcConnector;
import com.universal.milestone.Label;
import com.universal.milestone.LookupObject;
import com.universal.milestone.MilestoneFormDropDownMenu;
import com.universal.milestone.MilestoneHelper;
import com.universal.milestone.MilestoneHelper_2;
import com.universal.milestone.PrefixCode;
import com.universal.milestone.ProductCategory;
import com.universal.milestone.ReleaseType;
import com.universal.milestone.Report;
import com.universal.milestone.ReportSelections;
import com.universal.milestone.ReportSelectionsHelper;
import com.universal.milestone.ReportingServices;
import com.universal.milestone.Schedule;
import com.universal.milestone.ScheduleManager;
import com.universal.milestone.ScheduledTask;
import com.universal.milestone.Selection;
import com.universal.milestone.SelectionConfiguration;
import com.universal.milestone.SelectionManager;
import com.universal.milestone.SelectionStatus;
import com.universal.milestone.SelectionSubConfiguration;
import com.universal.milestone.User;
import com.universal.milestone.UserManager;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.StringTokenizer;
import java.util.Vector;
import javax.servlet.http.HttpServletResponse;

public class ReportSelections {
  private Vector finalSelections;
  
  private String reportName;
  
  private int reportProductType;
  
  private Context reportContext;
  
  private Report currentReport;
  
  public ReportSelections(Context context) {
    this.finalSelections = null;
    this.reportName = null;
    this.reportProductType = 0;
    this.reportContext = context;
    this.currentReport = (Report)context.getSessionValue("report");
    this.reportName = this.currentReport.getReportName();
    this.reportProductType = this.currentReport.getProductType();
  }
  
  public int getCurrentUser() {
    User user = (User)this.reportContext.getSessionValue("user");
    int userId = 0;
    if (user != null)
      userId = user.getUserId(); 
    return userId;
  }
  
  public Vector getFinalSelections() {
    Vector finalSelections = new Vector();
    System.out.println("ReportSelections::getFinalSelections()");
    if (this.reportName.equals("UmlAddMov")) {
      Form reportForm = (Form)this.reportContext.getSessionValue("reportForm");
      String addsMovesFlag = reportForm.getStringValue("AddsMovesBoth");
      Vector AddsSelections = new Vector();
      Vector MovesSelections = new Vector();
      if (addsMovesFlag.equalsIgnoreCase("Adds") || addsMovesFlag.equalsIgnoreCase("Both"))
        AddsSelections = getSelectionsForAddsReport(this.reportContext, this.reportProductType); 
      if (addsMovesFlag.equalsIgnoreCase("Moves") || addsMovesFlag.equalsIgnoreCase("Both"))
        MovesSelections = getSelectionsForMovesReport(this.reportContext, this.reportProductType); 
      Vector addsMovesVector = new Vector();
      addsMovesVector.add(AddsSelections);
      addsMovesVector.add(MovesSelections);
      return addsMovesVector;
    } 
    if (this.reportName.equals("PhyProdAct")) {
      Form reportForm = (Form)this.reportContext.getSessionValue("reportForm");
      boolean CancelsActivityFlag = false;
      boolean ChangesActivityFlag = false;
      boolean DeletesActivityFlag = false;
      boolean AddsActivityFlag = false;
      boolean MovesActivityFlag = false;
      boolean AllActivityFlag = false;
      CancelsActivityFlag = ((FormCheckBox)reportForm.getElement("CancelsActivity")).isChecked();
      ChangesActivityFlag = ((FormCheckBox)reportForm.getElement("ChangesActivity")).isChecked();
      DeletesActivityFlag = ((FormCheckBox)reportForm.getElement("DeletesActivity")).isChecked();
      AddsActivityFlag = ((FormCheckBox)reportForm.getElement("AddsActivity")).isChecked();
      MovesActivityFlag = ((FormCheckBox)reportForm.getElement("MovesActivity")).isChecked();
      AllActivityFlag = ((FormCheckBox)reportForm.getElement("AllActivity")).isChecked();
      Vector ChangesSelections = new Vector();
      Vector CancelsSelections = new Vector();
      Vector DeletesSelections = new Vector();
      Vector AddsSelections = new Vector();
      Vector MovesSelections = new Vector();
      if (ChangesActivityFlag || AllActivityFlag)
        ChangesSelections = getSelectionsForPhysicalProductActivityReportMultiples(this.reportContext, this.reportProductType, "changes"); 
      if (CancelsActivityFlag || AllActivityFlag)
        CancelsSelections = getSelectionsForPhysicalProductActivityReportMultiples(this.reportContext, this.reportProductType, "cancels"); 
      if (DeletesActivityFlag || AllActivityFlag)
        DeletesSelections = getSelectionsForPhysicalProductActivityReport(this.reportContext, this.reportProductType, "deletes"); 
      if (AddsActivityFlag || AllActivityFlag)
        AddsSelections = getSelectionsForPhysicalProductActivityReport(this.reportContext, this.reportProductType, "adds"); 
      if (MovesActivityFlag || AllActivityFlag)
        MovesSelections = getSelectionsForPhysicalProductActivityReportMultiples(this.reportContext, this.reportProductType, "moves"); 
      Vector physicalProductActivityVector = new Vector();
      physicalProductActivityVector.add(ChangesSelections);
      physicalProductActivityVector.add(AddsSelections);
      physicalProductActivityVector.add(CancelsSelections);
      physicalProductActivityVector.add(DeletesSelections);
      physicalProductActivityVector.add(MovesSelections);
      return physicalProductActivityVector;
    } 
    if (this.reportName.equals("UmeProdSch")) {
      finalSelections = getFilteredSelectionsForReport(this.reportContext, this.reportProductType);
    } else if (this.reportName.equals("TaskDueDt") || this.reportName.equals("MCARelSchd")) {
      finalSelections = getTaskDueSelectionsForReport(this.reportContext, this.reportProductType);
    } else {
      Vector selections = getAllSelectionsForUser(this.reportContext, this.reportName, this.reportProductType);
      if (this.reportName.equals("eInitRel"))
        return selections; 
      finalSelections = filterSelectionsWithReportCriteria(selections, this.reportContext, this.reportName, this.reportProductType);
    } 
    return finalSelections;
  }
  
  public static Vector getAllSelectionsForUser(Context context, String reportname, int reportProductType) {
    Vector companies = MilestoneHelper.getUserCompanies(context);
    Company company = null;
    Vector precache = new Vector();
    Selection selection = null;
    StringBuffer query = new StringBuffer(200);
    Form reportForm = (Form)context.getSessionValue("reportForm");
    boolean bScheduled = (reportForm.getElement("ScheduledReleases") != null) ? ((FormCheckBox)reportForm.getElement("ScheduledReleases")).isChecked() : 0;
    String strUmlKey = (reportForm.getStringValue("umlKeyTask") != null) ? reportForm.getStringValue("umlKeyTask") : "";
    Report report = (Report)context.getSessionValue("Report");
    if (reportname.equals("eInitRel")) {
      if (bScheduled || (!strUmlKey.equalsIgnoreCase("") && !strUmlKey.trim().equalsIgnoreCase("all"))) {
        query.append("SELECT DISTINCT header.[release_id],header.[impact_date], header.[street_date],  header.[artist],header.[title],header.[upc],header.[packaging],  header.[coordinator_contacts],header.[product_line],header.[parental_indicator],  header.[territory],header[comments],  header.[imprint]  FROM vi_Release_Header header with (nolock), vi_Release_Detail detail with (nolock) WHERE (header.[release_id] = detail.[release_id]) AND (");
      } else {
        query.append("SELECT release_id,impact_date,street_date, artist,title,upc,packaging,  coordinator_contacts,product_line,parental_indicator,  territory,comments,  [imprint]  from vi_Release_Header header with (nolock) WHERE (");
      } 
    } else if (reportname.equals("ToDoList")) {
      query.append("SELECT DISTINCT header.[release_id], header.[artist], header.[title], header.[artist_first_name], header.[artist_last_name], header.[street_date], header.[configuration], header.[sub_configuration], header.[upc], header.[prefix], header.[selection_no], header.[status],  header.[company_id], header.[family_id], header.[release_family_id], header.[environment_id], header.[label_id], detail.[owner], detail.[due_date], task.[department], task.[name], header.[contact_id], detail.[completion_date],  header.[imprint]  FROM vi_Release_Header header with (nolock) INNER JOIN vi_release_detail detail with (nolock) ON header.[release_id] = detail.[release_id]  INNER JOIN vi_Task task with (nolock) ON detail.[task_id] = task.[task_id] WHERE  not (detail.[due_date] is null) and detail.[completion_date] is null and (");
    } else if (bScheduled || (!strUmlKey.equalsIgnoreCase("") && !strUmlKey.trim().equalsIgnoreCase("all") && 
      !report.getReportName().trim().equalsIgnoreCase("New Rel."))) {
      query.append("SELECT DISTINCT header.[release_id], header.[artist],  UPPER(header.artist) as 'UpperArtist', UPPER(header.title) as 'UpperTitle',  header.[title], header.[artist_first_name], header.[artist_last_name],  header.[artist_first_name] + ' ' + header.[artist_last_name] AS fl_artist, header.[street_date], header.[digital_rls_date], header.[configuration], header.[sub_configuration], header.[upc], header.[prefix], header.[selection_no], header.[status],  header.[company_id], header.[environment_id], header.[family_id], header.[release_family_id], header.[label_id],  header.[imprint], header.[comments]  FROM vi_Release_Header header with (nolock), vi_Release_Detail detail with (nolock) WHERE (header.[release_id] = detail.[release_id]) AND (");
    } else {
      query.append("SELECT release_id, title, artist_first_name, artist_last_name, street_date, digital_rls_date, configuration, sub_configuration, artist_first_name + ' ' + artist_last_name AS fl_artist, upc, prefix, selection_no, status,  company_id, environment_id, family_id, release_family_id, label_id,  header.[imprint], header.[comments]  from vi_Release_Header header with (nolock) ");
      String strProdGroupCode = (reportForm.getStringValue("productionGroupCode") != null) ? reportForm.getStringValue("productionGroupCode") : "";
      if (!strProdGroupCode.equalsIgnoreCase("") && !strProdGroupCode.equalsIgnoreCase("All")) {
        query.append(" INNER JOIN ArchimedesLabels atlasLabels with (nolock) ON header.[Archimedes_id] = atlasLabels.[ArchimedesID]  WHERE ( atlasLabels.[ProductionGroupCode] ='" + 
            strProdGroupCode + "' AND ");
      } else {
        query.append(" WHERE ( ");
      } 
    } 
    String[] strFamily = ReportSelectionsHelper.getMultiSelectionListValues("family", reportForm);
    String[] strEnvironment = ReportSelectionsHelper.getMultiSelectionListValues("environment", reportForm);
    String[] strCompany = ReportSelectionsHelper.getMultiSelectionListValues("company", reportForm);
    String[] strLabel = ReportSelectionsHelper.getMultiSelectionListValues("Label", reportForm);
    String strBgnDueDate = (reportForm.getStringValue("beginDueDate") != null) ? reportForm.getStringValue("beginDueDate") : "";
    String strEndDueDate = (reportForm.getStringValue("endDueDate") != null) ? reportForm.getStringValue("endDueDate") : "";
    String strProductType = (reportForm.getStringValue("ProductType") != null) ? reportForm.getStringValue("ProductType") : "";
    if (strProductType.equals("")) {
      strProductType = String.valueOf(reportProductType);
    } else if (strProductType.equals("Physical")) {
      strProductType = String.valueOf(0);
    } else if (strProductType.equals("Digital")) {
      strProductType = String.valueOf(1);
    } else if (strProductType.equals("Both")) {
      strProductType = String.valueOf(2);
    } 
    ReportSelectionsHelper.addMultSelectionToStringBuffer(query, "environment_id", strEnvironment, true, "environment", reportForm, true, false);
    ReportSelectionsHelper.addMultSelectionToStringBuffer(query, "company_id", strCompany, true, "company", reportForm, false, true);
    if (reportname.equals("ToDoList")) {
      if (!strBgnDueDate.equalsIgnoreCase("") && 
        !strBgnDueDate.equalsIgnoreCase("0"))
        query.append(" AND due_date >= '" + strBgnDueDate + "' "); 
      if (!strEndDueDate.equalsIgnoreCase("") && 
        !strEndDueDate.equalsIgnoreCase("0"))
        query.append(" AND due_date <= '" + strEndDueDate + "' "); 
    } 
    ReportSelectionsHelper.addReleasingFamilyLabelFamilyMultSelect("family", context, query, reportForm);
    ReportSelectionsHelper.addMultSelectionToStringBuffer(query, "label_id", strLabel, true, "Label", reportForm, false, true);
    if (strProductType.equals(Integer.toString(1))) {
      query.append(" AND digital_flag = 1 ");
    } else if (strProductType.equals(Integer.toString(0))) {
      query.append(" AND digital_flag = 0 ");
    } 
    if (!strUmlKey.equalsIgnoreCase("") && !strUmlKey.trim().equalsIgnoreCase("all")) {
      int intUml = -1;
      Vector theFamilies = Cache.getFamilies();
      for (int i = 0; i < theFamilies.size(); i++) {
        Family family = (Family)theFamilies.get(i);
        if (family.getName().trim().equalsIgnoreCase("UML")) {
          intUml = family.getStructureID();
          break;
        } 
      } 
      if (intUml > 0)
        if (report.getReportName().trim().equalsIgnoreCase("New Rel.")) {
          query.append(" AND (EXISTS (SELECT owner FROM vi_release_detail detail where owner = " + intUml);
          query.append(" AND detail.release_id = header.release_id)");
          query.append(" OR NOT EXISTS ");
          query.append(" (SELECT detailA.release_id FROM vi_release_detail detailA");
          query.append(" WHERE detailA.release_id = header.release_id)) ");
        } else if (strUmlKey.equalsIgnoreCase("UML")) {
          query.append(" AND detail.owner = " + intUml);
        } else {
          query.append(" AND detail.owner != " + intUml);
        }  
    } 
    String[] strConfiguration = null;
    try {
      MilestoneFormDropDownMenu configList = (MilestoneFormDropDownMenu)reportForm.getElement("configurationList");
      if (configList != null) {
        ArrayList configListAl = configList.getStringValues();
        if (configListAl != null) {
          strConfiguration = new String[configListAl.size()];
          strConfiguration = (String[])configListAl.toArray(strConfiguration);
        } 
      } 
    } catch (Exception e) {
      e.printStackTrace();
      System.out.println("<<< Configuration vector execption " + e.getMessage());
    } 
    String[] strSubconfiguration = null;
    try {
      MilestoneFormDropDownMenu subconfigList = (MilestoneFormDropDownMenu)reportForm.getElement("subconfigurationList");
      if (subconfigList != null) {
        ArrayList subconfigListAl = subconfigList.getStringValues();
        if (subconfigListAl != null) {
          strSubconfiguration = new String[subconfigListAl.size()];
          strSubconfiguration = (String[])subconfigListAl.toArray(strSubconfiguration);
        } 
      } 
    } catch (Exception e) {
      e.printStackTrace();
      System.out.println("<<< Subconfiguration vector execption " + e.getMessage());
    } 
    if (strSubconfiguration != null && 
      !strSubconfiguration[0].trim().equalsIgnoreCase("0") && 
      !strSubconfiguration[0].trim().equalsIgnoreCase("") && 
      !strSubconfiguration[0].trim().equalsIgnoreCase("all")) {
      boolean addOr = false;
      query.append(" AND (");
      for (int x = 0; x < strSubconfiguration.length; x++) {
        if (addOr)
          query.append(" OR "); 
        String txtvalue = strSubconfiguration[x];
        if (txtvalue.indexOf("[") != -1) {
          String txtconfigcode = txtvalue.substring(1, txtvalue.indexOf("]"));
          String txtsubconfigcode = txtvalue.substring(txtvalue.indexOf("]") + 1, txtvalue.length());
          query.append(" (header.[configuration] = '" + txtconfigcode.trim() + "' AND ");
          query.append(" header.[sub_configuration] = '" + txtsubconfigcode.trim() + "') ");
        } else {
          query.append(" header.[sub_configuration] = '" + txtvalue.trim() + "' ");
        } 
        addOr = true;
      } 
      query.append(") ");
    } else if (strConfiguration != null && !strConfiguration[0].equalsIgnoreCase("") && !strConfiguration[0].equalsIgnoreCase("0") && 
      !strConfiguration[0].equalsIgnoreCase("all")) {
      boolean addOr = false;
      query.append(" AND (");
      for (int x = 0; x < strConfiguration.length; x++) {
        if (addOr)
          query.append(" OR "); 
        query.append(" header.[configuration] = '" + strConfiguration[x].trim() + "' ");
        addOr = true;
      } 
      query.append(") ");
    } 
    String beginDate = "";
    beginDate = (reportForm.getStringValue("beginDate") != null && reportForm.getStringValue("beginDate").length() > 0) ? reportForm.getStringValue("beginDate") : "";
    String endDate = "";
    endDate = (reportForm.getStringValue("endDate") != null && reportForm.getStringValue("endDate").length() > 0) ? reportForm.getStringValue("endDate") : "";
    if (!beginDate.equalsIgnoreCase("") || !endDate.equalsIgnoreCase(""))
      query.append(" AND (( header.status ='TBS' OR header.status ='ITW') OR ("); 
    String streetDate = "street_date";
    if (reportname.equals("DigProdSch") || reportname.equals("DigProLSch"))
      streetDate = "digital_rls_date"; 
    String[] reportParms = new String[2];
    if (strProductType.equals(Integer.toString(2))) {
      if (!beginDate.equalsIgnoreCase("")) {
        query.append("street_date >= '" + MilestoneHelper.escapeSingleQuotes(beginDate) + "'");
        reportParms[0] = MilestoneHelper.escapeSingleQuotes(beginDate);
      } 
      if (!endDate.equalsIgnoreCase("")) {
        if (!beginDate.equalsIgnoreCase("")) {
          query.append(" AND street_date <= '" + MilestoneHelper.escapeSingleQuotes(endDate) + "'");
        } else {
          query.append("street_date <= '" + MilestoneHelper.escapeSingleQuotes(endDate) + "'");
        } 
        reportParms[1] = MilestoneHelper.escapeSingleQuotes(endDate);
      } 
      if (!beginDate.equalsIgnoreCase("")) {
        query.append(" ) OR (digital_rls_date >= '" + MilestoneHelper.escapeSingleQuotes(beginDate) + "'");
        reportParms[0] = MilestoneHelper.escapeSingleQuotes(beginDate);
      } 
      if (!endDate.equalsIgnoreCase("")) {
        if (!beginDate.equalsIgnoreCase("")) {
          query.append(" AND digital_rls_date <= '" + MilestoneHelper.escapeSingleQuotes(endDate) + "'");
        } else {
          query.append(" ) OR (digital_rls_date <= '" + MilestoneHelper.escapeSingleQuotes(endDate) + "'");
        } 
        reportParms[1] = MilestoneHelper.escapeSingleQuotes(endDate);
      } 
    } else {
      if (!beginDate.equalsIgnoreCase("")) {
        if (reportname.equals("McaCustImp")) {
          query.append("( " + streetDate + " >= '" + 
              MilestoneHelper.escapeSingleQuotes(beginDate) + "'");
        } else {
          query.append(String.valueOf(streetDate) + " >= '" + 
              MilestoneHelper.escapeSingleQuotes(beginDate) + "'");
        } 
        reportParms[0] = MilestoneHelper.escapeSingleQuotes(beginDate);
      } 
      if (!endDate.equalsIgnoreCase("")) {
        if (!beginDate.equalsIgnoreCase("")) {
          query.append(" AND " + streetDate + " <= '" + 
              MilestoneHelper.escapeSingleQuotes(endDate) + "'");
        } else {
          query.append(String.valueOf(streetDate) + " <= '" + 
              MilestoneHelper.escapeSingleQuotes(endDate) + "'");
        } 
        reportParms[1] = MilestoneHelper.escapeSingleQuotes(endDate);
      } 
    } 
    if (reportname.equals("McaCustImp")) {
      if (!beginDate.equalsIgnoreCase("")) {
        query.append(") OR EXISTS( select impactDate from impactDates where selection_id = release_id AND (");
        query.append(" impactDate >= '" + MilestoneHelper.escapeSingleQuotes(beginDate) + "'");
      } 
      if (!endDate.equalsIgnoreCase(""))
        if (!beginDate.equalsIgnoreCase("")) {
          query.append(" AND impactDate <= '" + MilestoneHelper.escapeSingleQuotes(endDate) + "'");
        } else {
          query.append(") OR EXISTS( select impactDate from impactDates where selection_id = release_id AND (");
          query.append(" impactDate <= '" + MilestoneHelper.escapeSingleQuotes(endDate) + "'");
        }  
      if (!beginDate.equalsIgnoreCase("") || !endDate.equalsIgnoreCase(""))
        query.append("))))"); 
    } else if (!beginDate.equalsIgnoreCase("") || !endDate.equalsIgnoreCase("")) {
      query.append("))");
    } 
    ReportSelectionsHelper.addStatusToSelect(reportForm, query);
    if (reportname.equals("eInitRel")) {
      String eInitRelSortInt = (reportForm.getStringValue("eInitRelList") != null) ? 
        reportForm.getStringValue("eInitRelList") : "0";
      if (eInitRelSortInt.equals("0"))
        query.append(") ORDER BY UPPER(artist), UPPER(title), upc"); 
      if (eInitRelSortInt.equals("1"))
        query.append(") ORDER BY impact_date, packaging, UPPER(artist), UPPER(title), upc"); 
      if (eInitRelSortInt.equals("2"))
        query.append(") ORDER BY impact_date, UPPER(title), upc"); 
    } else if (reportname.equals("ToDoList")) {
      query.append(") ORDER BY due_date, prefix ");
    } else if (bScheduled || (!strUmlKey.equalsIgnoreCase("") && !strUmlKey.trim().equalsIgnoreCase("all") && 
      !report.getReportName().trim().equalsIgnoreCase("New Rel."))) {
      query.append(") ORDER BY UpperArtist, " + streetDate + 
          " , UpperTitle");
    } else if (reportname.equals("IDJProdAlt")) {
      query.append(") ORDER BY " + streetDate + " , UPPER(artist)");
    } else {
      query.append(") ORDER BY UPPER(artist), " + streetDate + 
          " , UPPER(title)");
    } 
    System.out.println("\n\n**************\n\n");
    System.out.println(query.toString());
    System.out.println("\n\n**************\n\n");
    if (ReportingServices.execUsingReportServices(reportname, query, context, reportParms))
      return null; 
    System.out.println("\n\n****************\n\n");
    System.out.println(query.toString());
    System.out.println("\n\n****************\n\n");
    JdbcConnector connector = MilestoneHelper.getConnector(query.toString());
    connector.setForwardOnly(false);
    connector.runQuery();
    int totalCount = 0;
    int tenth = 0;
    totalCount = connector.getRowCount();
    tenth = totalCount / 5;
    if (tenth < 1)
      tenth = 1; 
    try {
      HttpServletResponse sresponse = context.getResponse();
      context.putDelivery("status", new String("start_gathering"));
      context.putDelivery("percent", new String("10"));
      if (!ReportingServices.usingReportServicesByContext(context)) {
        context.includeJSP("status.jsp", "hiddenFrame");
        sresponse.setContentType("text/plain");
        sresponse.flushBuffer();
      } 
    } catch (Exception exception) {}
    int recordCount = 0;
    int count = 0;
    while (connector.more()) {
      try {
        if (count < recordCount / tenth) {
          count = recordCount / tenth;
          HttpServletResponse sresponse = context.getResponse();
          context.putDelivery("status", new String("start_gathering"));
          context.putDelivery("percent", new String(String.valueOf(count * 10)));
          if (!ReportingServices.usingReportServicesByContext(context)) {
            context.includeJSP("status.jsp", "hiddenFrame");
            sresponse.setContentType("text/plain");
            sresponse.flushBuffer();
          } 
        } 
        recordCount++;
        if (reportname.equals("eInitRel")) {
          selection = new Selection();
          selection.setSelectionID(connector.getIntegerField("release_id"));
          String impactDateString = connector.getFieldByName("impact_date");
          if (impactDateString != null)
            selection.setImpactDate(MilestoneHelper.getDatabaseDate(impactDateString)); 
          String streetDateString = connector.getFieldByName("street_date");
          if (streetDateString != null)
            selection.setStreetDate(MilestoneHelper.getDatabaseDate(streetDateString)); 
          selection.setArtist(connector.getField("artist", ""));
          selection.setTitle(connector.getField("title", ""));
          selection.setUpc(connector.getField("upc", ""));
          selection.setSelectionPackaging(connector.getField("packaging", ""));
          selection.setOtherContact(connector.getField("coordinator_contacts", ""));
          selection.setProductCategory((ProductCategory)getLookupObject(connector.getField("product_line"), 
                Cache.getProductCategories()));
          selection.setParentalGuidance(connector.getBoolean("parental_indicator"));
          selection.setSelectionTerritory(connector.getField("territory", ""));
          selection.setComments(connector.getField("comments", ""));
          selection.setImprint(connector.getField("imprint", ""));
          selection.setReleaseFamilyId(connector.getIntegerField("release_family_id"));
        } else if (reportname.equals("ToDoList")) {
          selection = new Selection();
          selection.setSelectionID(connector.getIntegerField("release_id"));
          selection.setTitle(connector.getField("title", ""));
          selection.setTaskName(connector.getField("name", ""));
          selection.setArtist(connector.getField("artist", ""));
          selection.setDepartment(connector.getField("department", ""));
          String completionDateString = connector.getFieldByName("completion_date");
          if (completionDateString != null)
            selection.setCompletionDate(MilestoneHelper.getDatabaseDate(completionDateString)); 
          selection.setArtistFirstName(connector.getField("artist_first_name", ""));
          selection.setArtistLastName(connector.getField("artist_last_name", ""));
          if (UserManager.getInstance().getUser(connector.getIntegerField("contact_id")) != null)
            selection.setLabelContact(UserManager.getInstance().getUser(connector.getIntegerField("contact_id"))); 
          String dueDateString = connector.getFieldByName("due_date");
          if (dueDateString != null)
            selection.setDueDate(MilestoneHelper.getDatabaseDate(dueDateString)); 
          String streetDateString = connector.getFieldByName("street_date");
          if (streetDateString != null)
            selection.setStreetDate(MilestoneHelper.getDatabaseDate(streetDateString)); 
          selection.setUpc(connector.getField("upc", ""));
          selection.setSelectionConfig(
              getSelectionConfigObject(connector.getField("configuration"), 
                Cache.getSelectionConfigs()));
          selection.setImprint(connector.getField("imprint", ""));
          selection.setReleaseFamilyId(connector.getIntegerField("release_family_id"));
          selection.setSelectionSubConfig(getSelectionSubConfigObject(connector.getField("sub_configuration"), selection.getSelectionConfig()));
          selection.setPrefixID((PrefixCode)getLookupObject(connector.getField("prefix"), Cache.getPrefixCodes()));
          selection.setSelectionNo(connector.getField("selection_no"));
          selection.setSelectionStatus((SelectionStatus)getLookupObject(connector.getField("status"), 
                Cache.getSelectionStatusList()));
        } else {
          selection = new Selection();
          selection.setSelectionID(connector.getIntegerField("release_id"));
          selection.setTitle(connector.getField("title", ""));
          selection.setArtistFirstName(connector.getField("artist_first_name", ""));
          selection.setArtistLastName(connector.getField("artist_last_name", ""));
          selection.setFlArtist(connector.getField("fl_artist", ""));
          String streetDateString = connector.getFieldByName("street_date");
          if (streetDateString != null)
            selection.setStreetDate(MilestoneHelper.getDatabaseDate(streetDateString)); 
          if (reportname.equals("DigProdSch") || reportname.equals("DigProLSch")) {
            String digitalStreetDateString = connector.getFieldByName("digital_rls_date");
            if (digitalStreetDateString != null)
              selection.setDigitalRlsDate(MilestoneHelper.getDatabaseDate(digitalStreetDateString)); 
          } 
          selection.setUpc(connector.getField("upc", ""));
          selection.setImprint(connector.getField("imprint", ""));
          selection.setReleaseFamilyId(connector.getIntegerField("release_family_id"));
          selection.setSelectionConfig(
              getSelectionConfigObject(connector.getField("configuration"), 
                Cache.getSelectionConfigs()));
          selection.setSelectionSubConfig(getSelectionSubConfigObject(connector.getField("sub_configuration"), selection.getSelectionConfig()));
          selection.setPrefixID((PrefixCode)getLookupObject(connector.getField("prefix"), Cache.getPrefixCodes()));
          selection.setSelectionNo(connector.getField("selection_no"));
          selection.setSelectionStatus((SelectionStatus)getLookupObject(connector.getField("status"), 
                Cache.getSelectionStatusList()));
        } 
        precache.add(selection);
        selection = null;
        connector.next();
      } catch (Exception exception) {}
    } 
    connector.close();
    company = null;
    return precache;
  }
  
  protected static Vector filterSelectionsWithReportCriteria(Vector selections, Context context, String reportname, int reportProductType) {
    Vector selectionsForReport = new Vector();
    Form reportForm = (Form)context.getSessionValue("reportForm");
    if (selections == null || selections.size() == 0)
      return selectionsForReport; 
    Calendar beginStDate = (reportForm.getStringValue("beginDate") != null && reportForm.getStringValue("beginDate").length() > 0) ? MilestoneHelper.getDate(reportForm.getStringValue("beginDate")) : null;
    if (beginStDate == null) {
      beginStDate = Calendar.getInstance();
      beginStDate.setTime(new Date(0L));
    } 
    beginStDate.set(11, 0);
    beginStDate.set(12, 0);
    beginStDate.set(13, 0);
    beginStDate.set(14, 0);
    Calendar endStDate = (reportForm.getStringValue("endDate") != null && reportForm.getStringValue("endDate").length() > 0) ? MilestoneHelper.getDate(reportForm.getStringValue("endDate")) : null;
    if (endStDate != null) {
      endStDate.set(11, 23);
      endStDate.set(12, 59);
      endStDate.set(13, 59);
      endStDate.set(14, 999);
    } 
    String strDistribution = (reportForm.getStringValue("distribution") != null) ? reportForm.getStringValue("distribution") : "";
    String strLabelContact = (reportForm.getStringValue("contact") != null) ? reportForm.getStringValue("contact") : "";
    String strUmlContact = (reportForm.getStringValue("umlContact") != null) ? reportForm.getStringValue("umlContact") : "";
    String strFuture2 = (reportForm.getStringValue("Future2") != null) ? reportForm.getStringValue("Future2") : "";
    String strReleaseType = (reportForm.getStringValue("releaseType") != null) ? reportForm.getStringValue("releaseType") : "";
    String strArtist = (reportForm.getStringValue("artist") != null) ? reportForm.getStringValue("artist") : "";
    Iterator formIterator = reportForm.getElements();
    Vector distCoIDs = new Vector();
    while (formIterator.hasNext()) {
      FormElement field = (FormElement)formIterator.next();
      String fieldName = field.getName();
      if (fieldName.startsWith("distCo")) {
        FormCheckBox fCheck = (FormCheckBox)field;
        if (fCheck.isChecked())
          distCoIDs.add(fieldName.substring(6, fieldName.length())); 
      } 
    } 
    String[] strFamily = ReportSelectionsHelper.getMultiSelectionListValues("family", reportForm);
    String[] strEnvironment = ReportSelectionsHelper.getMultiSelectionListValues("environment", reportForm);
    String[] strCompany = ReportSelectionsHelper.getMultiSelectionListValues("company", reportForm);
    String[] strLabel = ReportSelectionsHelper.getMultiSelectionListValues("Label", reportForm);
    String strProductType = (reportForm.getStringValue("ProductType") != null) ? reportForm.getStringValue("ProductType") : "";
    if (strProductType.equals("")) {
      strProductType = String.valueOf(reportProductType);
    } else if (strProductType.equals("Physical")) {
      strProductType = String.valueOf(0);
    } else if (strProductType.equals("Digital")) {
      strProductType = String.valueOf(1);
    } else if (strProductType.equals("Both")) {
      strProductType = String.valueOf(2);
    } 
    String[] strConfiguration = null;
    try {
      MilestoneFormDropDownMenu configList = (MilestoneFormDropDownMenu)reportForm.getElement("configurationList");
      if (configList != null) {
        ArrayList configListAl = configList.getStringValues();
        if (configListAl != null) {
          strConfiguration = new String[configListAl.size()];
          strConfiguration = (String[])configListAl.toArray(strConfiguration);
        } 
      } 
    } catch (Exception e) {
      e.printStackTrace();
      System.out.println("<<< Configuration vector execption " + e.getMessage());
    } 
    String[] strSubconfiguration = null;
    try {
      MilestoneFormDropDownMenu subconfigList = (MilestoneFormDropDownMenu)reportForm.getElement("subconfigurationList");
      if (subconfigList != null) {
        ArrayList subconfigListAl = subconfigList.getStringValues();
        if (subconfigListAl != null) {
          strSubconfiguration = new String[subconfigListAl.size()];
          strSubconfiguration = (String[])subconfigListAl.toArray(strSubconfiguration);
        } 
      } 
    } catch (Exception e) {
      e.printStackTrace();
      System.out.println("<<< Subconfiguration vector execption " + e.getMessage());
    } 
    boolean bParent = (reportForm.getElement("ParentsOnly") != null) ? ((FormCheckBox)reportForm.getElement("ParentsOnly")).isChecked() : 0;
    int totalCount = selections.size();
    int tenth = 0;
    tenth = totalCount / 5;
    if (tenth < 1)
      tenth = 1; 
    try {
      HttpServletResponse sresponse = context.getResponse();
      context.putDelivery("status", new String("start_gathering"));
      context.putDelivery("percent", new String("10"));
      if (!ReportingServices.usingReportServicesByContext(context)) {
        context.includeJSP("status.jsp", "hiddenFrame");
        sresponse.setContentType("text/plain");
        sresponse.flushBuffer();
      } 
    } catch (Exception exception) {}
    int recordCount = 0;
    int count = 0;
    for (int i = 0; i < selections.size(); i++) {
      try {
        if (count < recordCount / tenth) {
          count = recordCount / tenth;
          HttpServletResponse sresponse = context.getResponse();
          context.putDelivery("status", new String("start_gathering"));
          context.putDelivery("percent", new String(String.valueOf(count * 10 + 50)));
          if (!ReportingServices.usingReportServicesByContext(context)) {
            context.includeJSP("status.jsp", "hiddenFrame");
            sresponse.setContentType("text/plain");
            sresponse.flushBuffer();
          } 
        } 
        recordCount++;
        Selection temp_sel = (Selection)selections.elementAt(i);
        Selection sel = SelectionManager.getInstance().getSelectionHeader(temp_sel.getSelectionID());
        if (reportname.equals("ToDoList")) {
          sel.setDueDate(temp_sel.getDueDate());
          sel.setDepartment(temp_sel.getDepartment());
          sel.setTaskName(temp_sel.getTaskName());
          sel.setCompletionDate(temp_sel.getCompletionDate());
        } 
        String status = "";
        if (sel.getSelectionStatus() != null)
          status = sel.getSelectionStatus().getName(); 
        if (!status.equalsIgnoreCase("TBS") && !status.equalsIgnoreCase("In the works"))
          if (!reportname.equals("McaCustImp"))
            if (strProductType.equals(String.valueOf(1))) {
              Calendar digitalStreetDate = sel.getDigitalRlsDate();
              if (digitalStreetDate != null && ((beginStDate != null && digitalStreetDate.before(beginStDate)) || (endStDate != null && digitalStreetDate.after(endStDate))))
                continue; 
            } else if (strProductType.equals(String.valueOf(2))) {
              Calendar streetDate;
              if (sel.isDigital) {
                streetDate = sel.getDigitalRlsDate();
              } else {
                streetDate = sel.getStreetDate();
              } 
              if (streetDate != null && ((beginStDate != null && streetDate.before(beginStDate)) || (endStDate != null && streetDate.after(endStDate))))
                continue; 
            } else {
              Calendar physicalStreetDate = sel.getStreetDate();
              if (physicalStreetDate != null && ((beginStDate != null && physicalStreetDate.before(beginStDate)) || (endStDate != null && physicalStreetDate.after(endStDate))))
                continue; 
            }   
        if (!strDistribution.equalsIgnoreCase("") && !strDistribution.equalsIgnoreCase("All")) {
          String selDistribution = "";
          if (sel.getLabel().getDistribution() == 1) {
            selDistribution = "East";
          } else if (sel.getLabel().getDistribution() == 0) {
            selDistribution = "West";
          } 
          if (!selDistribution.equalsIgnoreCase(strDistribution.trim()))
            continue; 
        } 
        Label currentLabel = sel.getLabel();
        int selectionsDistCompany = MilestoneHelper_2.getLabelDistCoId(currentLabel.getStructureID());
        if (selectionsDistCompany == -1)
          selectionsDistCompany = Integer.parseInt("1"); 
        if (distCoIDs.size() > 0 && !distCoIDs.contains(Integer.toString(selectionsDistCompany)))
          continue; 
        if (!strLabelContact.trim().equalsIgnoreCase("0") && !strLabelContact.trim().equalsIgnoreCase("")) {
          if (sel.getLabelContact() == null)
            continue; 
          String selLabelContactName = Integer.toString(sel.getLabelContact().getUserId());
          if (!selLabelContactName.equalsIgnoreCase(strLabelContact))
            continue; 
        } 
        if (!strUmlContact.trim().equalsIgnoreCase("0") && !strUmlContact.trim().equalsIgnoreCase("")) {
          SelectionManager.getInstance().getSelectionManufacturingSubDetail(sel);
          if (sel.getUmlContact() == null)
            continue; 
          String selUmlContactName = Integer.toString(sel.getUmlContact().getUserId());
          if (!selUmlContactName.equalsIgnoreCase(strUmlContact))
            continue; 
        } 
        if (!strReleaseType.equalsIgnoreCase("") && !strReleaseType.equalsIgnoreCase("All")) {
          String releaseType = "";
          if (sel.getReleaseType() != null) {
            releaseType = (sel.getReleaseType().getName() != null) ? sel.getReleaseType().getName() : "";
            if (!releaseType.equalsIgnoreCase(strReleaseType))
              continue; 
          } else {
            continue;
          } 
        } 
        if (!ReportSelectionsHelper.isStatusFound(reportForm, sel))
          continue; 
        if (!strArtist.trim().equals("") && sel.getArtist() != null) {
          String artistUpperCase = sel.getArtist().trim().toUpperCase();
          if (!artistUpperCase.startsWith(strArtist.trim().toUpperCase()))
            continue; 
        } 
        if (!ReportSelectionsHelper.isMultSelectFound(Integer.toString(sel.getReleaseFamilyId()), strFamily))
          continue; 
        if (sel.getEnvironment() == null || 
          !ReportSelectionsHelper.isMultSelectFound(Integer.toString(sel.getEnvironment().getStructureID()), strEnvironment))
          continue; 
        if (sel.getCompany() == null || 
          !ReportSelectionsHelper.isMultSelectFound(Integer.toString(sel.getCompany().getStructureID()), strCompany))
          continue; 
        if (sel.getLabel() == null || 
          !ReportSelectionsHelper.isMultSelectFound(Integer.toString(sel.getLabel().getStructureID()), strLabel))
          continue; 
        if (bParent) {
          String prefixId = "";
          if (sel.getPrefixID() != null)
            prefixId = SelectionManager.getLookupObjectValue(sel.getPrefixID()); 
          if (!sel.getTitleID().equalsIgnoreCase(String.valueOf(prefixId) + sel.getSelectionNo()))
            continue; 
        } 
        if (strSubconfiguration != null && 
          !strSubconfiguration[0].trim().equalsIgnoreCase("0") && 
          !strSubconfiguration[0].trim().equalsIgnoreCase("") && 
          !strSubconfiguration[0].trim().equalsIgnoreCase("all")) {
          if (sel.getSelectionSubConfig() != null && sel.getSelectionConfig() != null) {
            String selConfigurationName = sel.getSelectionConfig().getSelectionConfigurationAbbreviation();
            String selSubconfigurationName = sel.getSelectionSubConfig().getSelectionSubConfigurationAbbreviation();
            boolean subconfigFnd = false;
            for (int x = 0; x < strSubconfiguration.length; x++) {
              String txtvalue = strSubconfiguration[x];
              if (txtvalue.indexOf("[") != -1) {
                String txtconfigcode = txtvalue.substring(1, 
                    txtvalue.indexOf("]"));
                String txtsubconfigcode = txtvalue.substring(txtvalue.indexOf(
                      "]") + 1, txtvalue.length());
                if (selConfigurationName.equalsIgnoreCase(txtconfigcode) && 
                  selSubconfigurationName.equalsIgnoreCase(txtsubconfigcode)) {
                  subconfigFnd = true;
                  break;
                } 
              } else if (selSubconfigurationName.equalsIgnoreCase(txtvalue)) {
                subconfigFnd = true;
                break;
              } 
            } 
            if (!subconfigFnd)
              continue; 
          } 
        } else if (strConfiguration != null && !strConfiguration[0].trim().equalsIgnoreCase("0") && !strConfiguration[0].trim().equalsIgnoreCase("") && 
          !strConfiguration[0].trim().equalsIgnoreCase("all")) {
          if (sel.getSelectionConfig() != null) {
            String selConfigurationName = sel.getSelectionConfig().getSelectionConfigurationAbbreviation();
            boolean configFnd = false;
            for (int x = 0; x < strConfiguration.length; x++) {
              if (selConfigurationName.equalsIgnoreCase(strConfiguration[x])) {
                configFnd = true;
                break;
              } 
            } 
            if (!configFnd)
              continue; 
          } 
        } 
        selectionsForReport.addElement(sel);
        continue;
      } catch (Exception exception) {
        continue;
      } 
    } 
    if (reportname.equals("Prod.Stat.")) {
      Vector precache = new Vector();
      for (int y = 0; y < selectionsForReport.size(); y++) {
        Selection currentSelection = (Selection)selectionsForReport.get(y);
        Schedule temp = ScheduleManager.getInstance().getSchedule(currentSelection.getSelectionID());
        currentSelection.setSchedule(temp);
        if (temp != null && temp.getTasks().size() > 0)
          precache.add(currentSelection); 
      } 
      return precache;
    } 
    if (reportname.equals("Com Rel") || reportname.equals("VerveComm")) {
      Vector commercialSelectionsForReport = new Vector();
      for (int z = 0; z < selectionsForReport.size(); z++) {
        Selection sel = (Selection)selectionsForReport.elementAt(z);
        String releaseType = (sel.getReleaseType().getName() != null) ? sel.getReleaseType().getName() : "";
        if (releaseType.toUpperCase().indexOf("COMMERCIAL") != -1)
          commercialSelectionsForReport.addElement(sel); 
      } 
      return commercialSelectionsForReport;
    } 
    return selectionsForReport;
  }
  
  public static SelectionConfiguration getSelectionConfigObject(String abbreviation, Vector configs) {
    for (int j = 0; j < configs.size(); j++) {
      SelectionConfiguration selectionConfiguration = (SelectionConfiguration)configs.get(j);
      if (selectionConfiguration.getSelectionConfigurationAbbreviation().equalsIgnoreCase(abbreviation))
        return selectionConfiguration; 
    } 
    return null;
  }
  
  public static SelectionSubConfiguration getSelectionSubConfigObject(String abbreviation, SelectionConfiguration config) {
    Vector subConfigs = config.getSubConfigurations();
    for (int j = 0; j < subConfigs.size(); j++) {
      SelectionSubConfiguration subConfig = (SelectionSubConfiguration)subConfigs.get(j);
      if (subConfig.getSelectionSubConfigurationAbbreviation().equalsIgnoreCase(abbreviation))
        return subConfig; 
    } 
    return null;
  }
  
  public static LookupObject getLookupObject(String abbreviation, Vector lookupVector) {
    for (int j = 0; j < lookupVector.size(); j++) {
      LookupObject lookupObject = (LookupObject)lookupVector.get(j);
      if (lookupObject.getAbbreviation().equalsIgnoreCase(abbreviation))
        return lookupObject; 
    } 
    return null;
  }
  
  public static Vector getFilteredSelectionsForReport(Context context, int reportProductType) {
    User user = (User)context.getSessionValue("user");
    int userId = 0;
    if (user != null)
      userId = user.getUserId(); 
    Form reportForm = (Form)context.getSessionValue("reportForm");
    boolean bScheduled = (reportForm.getElement("ScheduledReleases") != null) ? ((FormCheckBox)reportForm.getElement("ScheduledReleases")).isChecked() : 0;
    Vector companies = MilestoneHelper.getUserCompanies(context);
    Company company = null;
    Vector precache = new Vector();
    StringBuffer query = new StringBuffer();
    query.append("SELECT header.release_id, header.title_id, header.selection_no, header.prefix,   pfm.units_per_set, detail.due_date AS taskDue, detail.comments AS taskComments,   detail.owner AS taskOwner, detail.completion_date AS taskComplete,   detail.status AS taskStatus, detail.day_of_week AS taskDayOfWeek,   detail.weeks_to_release AS taskWeeksToRelease, detail.key_task_indicator AS taskKey,    detail.vendor AS taskVendor, detail.authorization_name AS taskAuthName,   detail.authorization_date AS taskAuthDate, detail.last_updated_ck, header.project_no,   header.title, header.artist_first_name, header.artist_last_name,   header.artist, header.artist_first_name + ' ' + header.artist_last_name AS fl_artist ,   header.side_a_title, header.side_b_title, header.product_line, header.release_type,   header.configuration, header.sub_configuration, header.UPC, header.price_code, header.genre,   header.family_id, header.release_family_id, header.environment_id, header.company_id, header.division_id, header.label_id, header.street_date,   header.international_date, header.coordinator_contacts, header.contact_id, header.status,   header.hold_indicator, header.hold_reason, header.comments, header.special_pkg_indicator,   header.pd_indicator, header.packaging, header.impact_date, header.last_updated_on,   header.last_updated_by, header.entered_on, mfg.uml_id, mfg.plant, mfg.distribution,   header.imprint,   mfg.order_qty, mfg.complete_qty, mfg.order_comments, Task.task_id, Task.department,   Task.name, Task.abbrev_id  FROM  Release_Header header  with (nolock)  LEFT JOIN Release_Subdetail mfg  with (nolock)  ON header.release_id = mfg.release_id ");
    if (bScheduled) {
      query.append(" INNER JOIN release_detail detail with (nolock) ON header.[release_id] = detail.[release_id]");
    } else {
      query.append(" LEFT JOIN release_detail detail with (nolock) ON header.[release_id] = detail.[release_id]");
    } 
    String strProdGroupCode = (reportForm.getStringValue("productionGroupCode") != null) ? reportForm.getStringValue("productionGroupCode") : "";
    if (!strProdGroupCode.equalsIgnoreCase("") && !strProdGroupCode.equalsIgnoreCase("All"))
      query.append(" INNER JOIN ArchimedesLabels atlasLabels with (nolock) ON header.[Archimedes_id] = atlasLabels.[ArchimedesID] "); 
    query.append("  LEFT JOIN Task  with (nolock)  ON detail.task_id = Task.task_id   LEFT JOIN Pfm_Selection pfm with (nolock)  ON header.release_id = pfm.release_id    WHERE ");
    String strLabelContact = (reportForm.getStringValue("contact") != null) ? reportForm.getStringValue("contact") : "";
    String strUmlContact = (reportForm.getStringValue("umlContact") != null) ? reportForm.getStringValue("umlContact") : "";
    String strReleaseType = (reportForm.getStringValue("releaseType") != null) ? reportForm.getStringValue("releaseType") : "";
    String strArtist = (reportForm.getStringValue("artist") != null) ? reportForm.getStringValue("artist") : "";
    String[] strFamily = ReportSelectionsHelper.getMultiSelectionListValues("family", reportForm);
    String[] strEnvironment = ReportSelectionsHelper.getMultiSelectionListValues("environment", reportForm);
    String[] strCompany = ReportSelectionsHelper.getMultiSelectionListValues("company", reportForm);
    String[] strLabel = ReportSelectionsHelper.getMultiSelectionListValues("Label", reportForm);
    String strProductType = (reportForm.getStringValue("ProductType") != null) ? reportForm.getStringValue("ProductType") : "";
    if (strProductType.equals("")) {
      strProductType = String.valueOf(reportProductType);
    } else if (strProductType.equals("Physical")) {
      strProductType = String.valueOf(0);
    } else if (strProductType.equals("Digital")) {
      strProductType = String.valueOf(1);
    } else if (strProductType.equals("Both")) {
      strProductType = String.valueOf(2);
    } 
    String[] strConfiguration = null;
    try {
      MilestoneFormDropDownMenu configList = (MilestoneFormDropDownMenu)reportForm.getElement("configurationList");
      if (configList != null) {
        ArrayList configListAl = configList.getStringValues();
        if (configListAl != null) {
          strConfiguration = new String[configListAl.size()];
          strConfiguration = (String[])configListAl.toArray(strConfiguration);
        } 
      } 
    } catch (Exception e) {
      e.printStackTrace();
      System.out.println("<<< Configuration vector execption " + e.getMessage());
    } 
    String[] strSubconfiguration = null;
    try {
      MilestoneFormDropDownMenu subconfigList = (MilestoneFormDropDownMenu)reportForm.getElement("subconfigurationList");
      if (subconfigList != null) {
        ArrayList subconfigListAl = subconfigList.getStringValues();
        if (subconfigListAl != null) {
          strSubconfiguration = new String[subconfigListAl.size()];
          strSubconfiguration = (String[])subconfigListAl.toArray(strSubconfiguration);
        } 
      } 
    } catch (Exception e) {
      e.printStackTrace();
      System.out.println("<<< Subconfiguration vector execption " + e.getMessage());
    } 
    boolean bParent = (reportForm.getElement("ParentsOnly") != null) ? ((FormCheckBox)reportForm.getElement("ParentsOnly")).isChecked() : 0;
    ReportSelectionsHelper.addMultSelectionToStringBuffer(query, "header.environment_id", strEnvironment, true, "environment", reportForm, true, false);
    ReportSelectionsHelper.addMultSelectionToStringBuffer(query, "header.company_id", strCompany, true, "company", reportForm, false, true);
    ReportSelectionsHelper.addReleasingFamilyLabelFamilyMultSelect("family", context, query, reportForm);
    ReportSelectionsHelper.addMultSelectionToStringBuffer(query, "header.[label_id]", strLabel, true, "Label", reportForm, false, true);
    if (strProductType.equals(Integer.toString(1))) {
      query.append(" AND digital_flag = 1 ");
    } else if (strProductType.equals(Integer.toString(0))) {
      query.append(" AND digital_flag = 0 ");
    } 
    if (!strArtist.equalsIgnoreCase(""))
      query.append(" AND header.[artist] LIKE '%" + MilestoneHelper.escapeSingleQuotes(strArtist) + "%'"); 
    ReportSelectionsHelper.addStatusToSelect(reportForm, query);
    if (!strReleaseType.equalsIgnoreCase("") && !strReleaseType.equalsIgnoreCase("all"))
      if (strReleaseType.equalsIgnoreCase("commercial")) {
        query.append(" AND header.[release_type] ='CO'");
      } else {
        query.append(" AND header.[release_type] ='PR'");
      }  
    if (!strProdGroupCode.equalsIgnoreCase("") && !strProdGroupCode.equalsIgnoreCase("All"))
      query.append(" AND atlasLabels.[ProductionGroupCode] ='" + strProdGroupCode + "' "); 
    if (!strLabelContact.equalsIgnoreCase("") && !strLabelContact.equalsIgnoreCase("0"))
      query.append(" AND header.[contact_id] =" + strLabelContact); 
    if (strSubconfiguration != null && 
      !strSubconfiguration[0].trim().equalsIgnoreCase("0") && 
      !strSubconfiguration[0].trim().equalsIgnoreCase("") && 
      !strSubconfiguration[0].trim().equalsIgnoreCase("all")) {
      boolean addOr = false;
      query.append(" AND (");
      for (int x = 0; x < strSubconfiguration.length; x++) {
        if (addOr)
          query.append(" OR "); 
        String txtvalue = strSubconfiguration[x];
        if (txtvalue.indexOf("[") != -1) {
          String txtconfigcode = txtvalue.substring(1, txtvalue.indexOf("]"));
          String txtsubconfigcode = txtvalue.substring(txtvalue.indexOf("]") + 1, txtvalue.length());
          query.append(" (header.[configuration] = '" + txtconfigcode.trim() + "' AND ");
          query.append(" header.[sub_configuration] = '" + txtsubconfigcode.trim() + "') ");
        } else {
          query.append(" header.[sub_configuration] = '" + txtvalue.trim() + "' ");
        } 
        addOr = true;
      } 
      query.append(") ");
    } else if (strConfiguration != null && !strConfiguration[0].equalsIgnoreCase("") && !strConfiguration[0].equalsIgnoreCase("0") && 
      !strConfiguration[0].equalsIgnoreCase("all")) {
      boolean addOr = false;
      query.append(" AND (");
      for (int x = 0; x < strConfiguration.length; x++) {
        if (addOr)
          query.append(" OR "); 
        query.append(" header.[configuration] = '" + strConfiguration[x].trim() + "' ");
        addOr = true;
      } 
      query.append(") ");
    } 
    if (!strUmlContact.equalsIgnoreCase("") && !strUmlContact.equalsIgnoreCase("0"))
      query.append(" AND mfg.[uml_id] =" + strUmlContact); 
    String beginDate = "";
    beginDate = (reportForm.getStringValue("beginDate") != null && reportForm.getStringValue("beginDate").length() > 0) ? reportForm.getStringValue("beginDate") : "";
    String endDate = "";
    endDate = (reportForm.getStringValue("endDate") != null && reportForm.getStringValue("endDate").length() > 0) ? reportForm.getStringValue("endDate") : "";
    if (!beginDate.equalsIgnoreCase("") || !endDate.equalsIgnoreCase(""))
      query.append(" AND ((header.[status] ='TBS' OR header.[status] ='ITW') OR ("); 
    if (!beginDate.equalsIgnoreCase(""))
      query.append(" header.[street_date] >= '" + MilestoneHelper.escapeSingleQuotes(beginDate) + "'"); 
    if (!endDate.equalsIgnoreCase(""))
      if (!beginDate.equalsIgnoreCase("")) {
        query.append(" AND header.[street_date] <= '" + MilestoneHelper.escapeSingleQuotes(endDate) + "'");
      } else {
        query.append(" header.[street_date] <= '" + MilestoneHelper.escapeSingleQuotes(endDate) + "'");
      }  
    if (!beginDate.equalsIgnoreCase("") || !endDate.equalsIgnoreCase(""))
      query.append("))"); 
    query.append(" ORDER BY header.[release_id], UPPER(header.[artist]), header.[street_date], UPPER(header.[title])");
    JdbcConnector connector = MilestoneHelper.getConnector(query.toString());
    connector.runQuery();
    Vector selections = null;
    System.out.println("\n\n************\n\n");
    System.out.println(query.toString());
    System.out.println("\n\n************\n\n");
    int totalCount = 0;
    int tenth = 0;
    JdbcConnector connectorCount = MilestoneHelper.getConnector(query.toString());
    connectorCount.runQuery();
    while (connectorCount.more()) {
      totalCount++;
      connectorCount.next();
    } 
    connectorCount.close();
    tenth = totalCount / 10;
    if (tenth < 1)
      tenth = 1; 
    try {
      HttpServletResponse sresponse = context.getResponse();
      context.putDelivery("status", new String("start_gathering"));
      context.putDelivery("percent", new String("10"));
      if (!ReportingServices.usingReportServicesByContext(context)) {
        context.includeJSP("status.jsp", "hiddenFrame");
        sresponse.setContentType("text/plain");
        sresponse.flushBuffer();
      } 
    } catch (Exception exception) {}
    int recordCount = 0;
    int count = 0;
    selections = new Vector();
    while (connector.more()) {
      try {
        if (count < recordCount / tenth) {
          count = recordCount / tenth;
          HttpServletResponse sresponse = context.getResponse();
          context.putDelivery("status", new String("start_gathering"));
          context.putDelivery("percent", new String(String.valueOf(count * 10)));
          if (!ReportingServices.usingReportServicesByContext(context)) {
            context.includeJSP("status.jsp", "hiddenFrame");
            sresponse.setContentType("text/plain");
            sresponse.flushBuffer();
          } 
        } 
        recordCount++;
        if (bParent) {
          String prefixId = "";
          String tmpTitleId = connector.getField("title_id", "");
          String tmpSelectionNo = connector.getField("selection_no", "");
          prefixId = SelectionManager.getLookupObjectValue((PrefixCode)getLookupObject(connector.getField("prefix", ""), Cache.getPrefixCodes()));
          if (!tmpTitleId.equalsIgnoreCase(String.valueOf(prefixId) + tmpSelectionNo)) {
            connector.next();
            continue;
          } 
        } 
        int numberOfUnits = 0;
        try {
          numberOfUnits = connector.getIntegerField("pfm.[units_per_set]");
        } catch (Exception exception) {}
        Selection selection = null;
        selection = new Selection();
        selection.setSelectionID(connector.getIntegerField("release_id"));
        String selectionNo = "";
        if (connector.getFieldByName("selection_no") != null)
          selectionNo = connector.getFieldByName("selection_no"); 
        selection.setSelectionNo(selectionNo);
        selection.setProjectID(connector.getField("project_no", ""));
        String titleId = "";
        if (connector.getFieldByName("title_id") != null)
          titleId = connector.getFieldByName("title_id"); 
        selection.setTitleID(titleId);
        selection.setTitle(connector.getField("title", ""));
        selection.setArtistFirstName(connector.getField("artist_first_name", ""));
        selection.setArtistLastName(connector.getField("artist_last_name", ""));
        selection.setArtist(connector.getField("artist", ""));
        selection.setFlArtist(connector.getField("fl_artist", ""));
        selection.setASide(connector.getField("side_a_title", ""));
        selection.setBSide(connector.getField("side_b_title", ""));
        selection.setProductCategory((ProductCategory)getLookupObject(connector.getField("product_line"), 
              Cache.getProductCategories()));
        selection.setReleaseType((ReleaseType)getLookupObject(connector.getField("release_type"), 
              Cache.getReleaseTypes()));
        selection.setSelectionConfig(getSelectionConfigObject(connector.getField("configuration"), 
              Cache.getSelectionConfigs()));
        selection.setSelectionSubConfig(getSelectionSubConfigObject(connector.getField("sub_configuration"), 
              selection.getSelectionConfig()));
        selection.setUpc(connector.getField("upc", ""));
        String sellCodeString = connector.getFieldByName("price_code");
        if (sellCodeString != null)
          selection.setPriceCode(SelectionManager.getInstance().getPriceCode(sellCodeString)); 
        selection.setSellCode(sellCodeString);
        selection.setGenre((Genre)getLookupObject(connector.getField("genre"), 
              Cache.getMusicTypes()));
        selection.setFamily((Family)MilestoneHelper.getStructureObject(connector.getIntegerField("family_id")));
        selection.setCompany((Company)MilestoneHelper.getStructureObject(connector.getIntegerField("company_id")));
        selection.setEnvironment((Environment)MilestoneHelper.getStructureObject(connector.getIntegerField("environment_id")));
        selection.setDivision((Division)MilestoneHelper.getStructureObject(connector.getIntegerField("division_id")));
        selection.setLabel((Label)MilestoneHelper.getStructureObject(connector.getIntegerField("label_id")));
        selection.setImprint(connector.getField("imprint", ""));
        selection.setReleaseFamilyId(connector.getIntegerField("release_family_id"));
        String streetDateString = connector.getFieldByName("street_date");
        if (streetDateString != null)
          selection.setStreetDate(MilestoneHelper.getDatabaseDate(streetDateString)); 
        String internationalDateString = connector.getFieldByName("international_date");
        if (internationalDateString != null)
          selection.setInternationalDate(MilestoneHelper.getDatabaseDate(internationalDateString)); 
        selection.setOtherContact(connector.getField("coordinator_contacts", ""));
        if (UserManager.getInstance().getUser(connector.getIntegerField("contact_id")) != null)
          selection.setLabelContact(UserManager.getInstance().getUser(connector.getIntegerField("contact_id"))); 
        selection.setSelectionStatus((SelectionStatus)getLookupObject(connector.getField("status"), 
              Cache.getSelectionStatusList()));
        selection.setHoldSelection(connector.getBoolean("hold_indicator"));
        selection.setHoldReason(connector.getField("hold_reason", ""));
        selection.setComments(connector.getField("comments", ""));
        selection.setSpecialPackaging(connector.getBoolean("special_pkg_indicator"));
        selection.setNumberOfUnits(numberOfUnits);
        selection.setPressAndDistribution(connector.getBoolean("pd_indicator"));
        selection.setPrefixID((PrefixCode)getLookupObject(connector.getField("prefix"), Cache.getPrefixCodes()));
        selection.setSelectionPackaging(connector.getField("packaging", ""));
        String impactDateString = connector.getFieldByName("impact_date");
        if (impactDateString != null)
          selection.setImpactDate(MilestoneHelper.getDatabaseDate(impactDateString)); 
        String lastUpdateDateString = connector.getFieldByName("last_updated_on");
        if (lastUpdateDateString != null)
          selection.setLastUpdateDate(MilestoneHelper.getDatabaseDate(lastUpdateDateString)); 
        selection.setLastUpdatingUser(UserManager.getInstance().getUser(connector.getIntegerField("last_updated_by")));
        String originDateString = connector.getFieldByName("entered_on");
        if (originDateString != null)
          selection.setOriginDate(MilestoneHelper.getDatabaseDate(originDateString)); 
        User umlContact = UserManager.getInstance().getUser(connector.getIntegerField("mfg.[uml_id]"));
        selection.setUmlContact(umlContact);
        selection.setPlant(getLookupObject(connector.getField("mfg.[plant]"), Cache.getVendors()));
        selection.setDistribution(getLookupObject(connector.getField("mfg.[distribution]"), Cache.getDistributionCodes()));
        selection.setPoQuantity(connector.getIntegerField("mfg.[order_qty]"));
        selection.setCompletedQuantity(connector.getIntegerField("mfg.[complete_qty]"));
        selection.setManufacturingComments(connector.getField("mfg.[order_comments]", ""));
        selection.setPrice(connector.getFloat("mfg.[list_price]"));
        selection.setFullSelection(true);
        String strDistribution = (reportForm.getStringValue("distribution") != null) ? reportForm.getStringValue("distribution") : "";
        String strFuture2 = (reportForm.getStringValue("Future2") != null) ? reportForm.getStringValue("Future2") : "";
        if (!strDistribution.equalsIgnoreCase("") && !strDistribution.equalsIgnoreCase("All")) {
          String selDistribution = SelectionManager.getLookupObjectValue(selection.getDistribution());
          if (!selDistribution.equalsIgnoreCase(strDistribution.trim()))
            continue; 
        } 
        int nextReleaseId = connector.getIntegerField("release_id");
        Schedule schedule = new Schedule();
        schedule.setSelectionID(nextReleaseId);
        Vector precacheSchedule = new Vector();
        while (connector.more() && connector.getIntegerField("release_id") == nextReleaseId) {
          ScheduledTask scheduledTask = null;
          scheduledTask = new ScheduledTask();
          scheduledTask.setReleaseID(connector.getIntegerField("release_Id"));
          scheduledTask.setTaskID(connector.getIntegerField("task_id"));
          scheduledTask.setOwner((Family)MilestoneHelper.getStructureObject(connector.getIntegerField("taskOwner")));
          String dueDateString = connector.getField("taskDue", "");
          if (dueDateString.length() > 0)
            scheduledTask.setDueDate(MilestoneHelper.getDatabaseDate(dueDateString)); 
          String completionDateString = connector.getField("taskComplete", "");
          if (completionDateString.length() > 0)
            scheduledTask.setCompletionDate(MilestoneHelper.getDatabaseDate(completionDateString)); 
          String taskStatus = connector.getField("taskStatus", "");
          if (taskStatus.length() > 1)
            scheduledTask.setScheduledTaskStatus(taskStatus); 
          int day = connector.getIntegerField("taskDayOfWeek");
          if (day > 0)
            scheduledTask.setDayOfTheWeek(new Day(connector.getIntegerField("taskDayOfWeek"))); 
          int weeks = connector.getIntegerField("taskWeeksToRelease");
          if (weeks > 0)
            scheduledTask.setWeeksToRelease(connector.getIntegerField("taskWeeksToRelease")); 
          String vendorString = connector.getField("taskVendor", "");
          if (vendorString.length() > 0)
            scheduledTask.setVendor(vendorString); 
          int taskAbbrevID = connector.getIntegerField("abbrev_id");
          scheduledTask.setTaskAbbreviationID(taskAbbrevID);
          int taskID = connector.getIntegerField("task_id");
          scheduledTask.setScheduledTaskID(taskID);
          String taskDept = connector.getField("department", "");
          scheduledTask.setDepartment(taskDept);
          scheduledTask.setKeyTask(connector.getBoolean("taskKey"));
          scheduledTask.setAuthorizationName(connector.getField("taskAuthName", ""));
          String authDateString = connector.getField("taskAuthDate", "");
          if (authDateString.length() > 0)
            scheduledTask.setAuthorizationDate(MilestoneHelper.getDatabaseDate(authDateString)); 
          String comments = connector.getField("taskComments", "");
          scheduledTask.setComments(comments);
          scheduledTask.setName(connector.getField("name", ""));
          precacheSchedule.add(scheduledTask);
          scheduledTask = null;
          if (connector.more()) {
            connector.next();
            recordCount++;
            continue;
          } 
          break;
        } 
        schedule.setTasks(precacheSchedule);
        selection.setSchedule(schedule);
        selections.add(selection);
      } catch (Exception exception) {}
    } 
    connector.close();
    return selections;
  }
  
  public static Vector getTaskDueSelectionsForReport(Context context, int reportProductType) {
    User user = (User)context.getSessionValue("user");
    int userId = 0;
    if (user != null)
      userId = user.getUserId(); 
    Form reportForm = (Form)context.getSessionValue("reportForm");
    String beginDate = "";
    beginDate = (reportForm.getStringValue("beginDate") != null && reportForm.getStringValue("beginDate").length() > 0) ? reportForm.getStringValue("beginDate") : "";
    String endDate = "";
    endDate = (reportForm.getStringValue("endDate") != null && reportForm.getStringValue("endDate").length() > 0) ? reportForm.getStringValue("endDate") : "";
    String beginDueDate = "";
    beginDueDate = (reportForm.getStringValue("beginDueDate") != null && reportForm.getStringValue("beginDueDate").length() > 0) ? reportForm.getStringValue("beginDueDate") : "";
    String endDueDate = "";
    endDueDate = (reportForm.getStringValue("endDueDate") != null && reportForm.getStringValue("endDueDate").length() > 0) ? reportForm.getStringValue("endDueDate") : "";
    StringBuffer dueDateQuery = new StringBuffer(200);
    if (!beginDueDate.equalsIgnoreCase(""))
      dueDateQuery.append("AND due_date >= '" + MilestoneHelper.escapeSingleQuotes(beginDueDate) + "'"); 
    if (!endDueDate.equalsIgnoreCase(""))
      if (!beginDueDate.equalsIgnoreCase("")) {
        dueDateQuery.append(" AND due_date <= '" + MilestoneHelper.escapeSingleQuotes(endDueDate) + "'");
      } else {
        dueDateQuery.append(" due_date <= '" + MilestoneHelper.escapeSingleQuotes(endDueDate) + "'");
      }  
    Vector companies = MilestoneHelper.getUserCompanies(context);
    Company company = null;
    Vector precache = new Vector();
    StringBuffer query = new StringBuffer();
    query.append("SELECT header.release_id, header.title_id, header.selection_no, header.prefix,   pfm.units_per_set, detail.due_date AS taskDue, detail.comments AS taskComments,   detail.owner AS taskOwner, detail.completion_date AS taskComplete,   detail.status AS taskStatus, detail.day_of_week AS taskDayOfWeek,   detail.weeks_to_release AS taskWeeksToRelease, detail.key_task_indicator AS taskKey,    detail.vendor AS taskVendor, detail.authorization_name AS taskAuthName,   detail.authorization_date AS taskAuthDate, detail.last_updated_ck, header.project_no,   header.title, header.artist_first_name, header.artist_last_name, header.artist,   header.side_a_title, header.side_b_title, header.product_line, header.release_type,   header.configuration, header.sub_configuration, header.UPC, header.price_code, header.genre,   header.family_id, header.release_family_id, header_environment_id, header.company_id, header.division_id, header.label_id, header.street_date,   header.international_date, header.coordinator_contacts, header.contact_id, header.status,   header.hold_indicator, header.hold_reason, header.comments, header.special_pkg_indicator,   header.pd_indicator, header.packaging, header.impact_date, header.last_updated_on,   header.last_updated_by, header.entered_on, mfg.uml_id, mfg.plant, mfg.distribution,   header.imprint,   mfg.order_qty, mfg.complete_qty, mfg.order_comments, Task.task_id, Task.department,   Task.name, Task.abbrev_id  FROM vi_release_header header with (nolock) LEFT JOIN vi_release_subdetail mfg with (nolock) ON header.[release_id] = mfg.[release_id] LEFT JOIN vi_release_detail detail with (nolock) ON header.[release_id] = detail.[release_id] " + 
        
        dueDateQuery.toString() + 
        " INNER JOIN vi_Task task with (nolock)" + 
        " ON detail.[task_id] = task.[task_id]" + 
        " LEFT JOIN vi_pfm_selection pfm with (nolock)" + 
        " ON header.[release_id] = pfm.[release_id]" + 
        " WHERE ");
    String strLabelContact = (reportForm.getStringValue("contact") != null) ? reportForm.getStringValue("contact") : "";
    String strUmlContact = (reportForm.getStringValue("umlContact") != null) ? reportForm.getStringValue("umlContact") : "";
    String strReleaseType = (reportForm.getStringValue("releaseType") != null) ? reportForm.getStringValue("releaseType") : "";
    String strArtist = (reportForm.getStringValue("artist") != null) ? reportForm.getStringValue("artist") : "";
    String[] strFamily = ReportSelectionsHelper.getMultiSelectionListValues("family", reportForm);
    String[] strEnvironment = ReportSelectionsHelper.getMultiSelectionListValues("environment", reportForm);
    String[] strCompany = ReportSelectionsHelper.getMultiSelectionListValues("company", reportForm);
    String[] strLabel = ReportSelectionsHelper.getMultiSelectionListValues("Label", reportForm);
    String strProductType = (reportForm.getStringValue("ProductType") != null) ? reportForm.getStringValue("ProductType") : "";
    if (strProductType.equals("")) {
      strProductType = String.valueOf(reportProductType);
    } else if (strProductType.equals("Physical")) {
      strProductType = String.valueOf(0);
    } else if (strProductType.equals("Digital")) {
      strProductType = String.valueOf(1);
    } else if (strProductType.equals("Both")) {
      strProductType = String.valueOf(2);
    } 
    boolean bParent = (reportForm.getElement("ParentsOnly") != null) ? ((FormCheckBox)reportForm.getElement("ParentsOnly")).isChecked() : 0;
    ReportSelectionsHelper.addMultSelectionToStringBuffer(query, "header.environment_id", strEnvironment, true, "environment", reportForm, true, false);
    ReportSelectionsHelper.addMultSelectionToStringBuffer(query, "header.company_id", strCompany, true, "company", reportForm, false, true);
    ReportSelectionsHelper.addReleasingFamilyLabelFamilyMultSelect("family", context, query, reportForm);
    ReportSelectionsHelper.addMultSelectionToStringBuffer(query, "header.[label_id]", strLabel, true, "Label", reportForm, false, true);
    if (strProductType.equals(Integer.toString(1))) {
      query.append(" AND digital_flag = 1 ");
    } else if (strProductType.equals(Integer.toString(0))) {
      query.append(" AND digital_flag = 0 ");
    } 
    if (!strArtist.equalsIgnoreCase(""))
      query.append(" AND header.[artist] LIKE '%" + MilestoneHelper.escapeSingleQuotes(strArtist) + "%'"); 
    ReportSelectionsHelper.addStatusToSelect(reportForm, query);
    if (!strReleaseType.equalsIgnoreCase("") && !strReleaseType.equalsIgnoreCase("all"))
      if (strReleaseType.equalsIgnoreCase("commercial")) {
        query.append(" AND header.[release_type] ='CO'");
      } else {
        query.append(" AND header.[release_type] ='PR'");
      }  
    if (!strLabelContact.equalsIgnoreCase("") && !strLabelContact.equalsIgnoreCase("0"))
      query.append(" AND header.[contact_id] =" + strLabelContact); 
    if (!strUmlContact.equalsIgnoreCase("") && !strUmlContact.equalsIgnoreCase("0"))
      query.append(" AND mfg.[uml_id] =" + strUmlContact); 
    if (!beginDate.equalsIgnoreCase("") || !endDate.equalsIgnoreCase(""))
      query.append(" AND ((header.[status] ='TBS' OR header.[status] ='ITW') OR ("); 
    if (!beginDate.equalsIgnoreCase(""))
      query.append(" street_date >= '" + MilestoneHelper.escapeSingleQuotes(beginDate) + "'"); 
    if (!endDate.equalsIgnoreCase(""))
      if (!beginDate.equalsIgnoreCase("")) {
        query.append(" AND street_date <= '" + MilestoneHelper.escapeSingleQuotes(endDate) + "'");
      } else {
        query.append(" street_date <= '" + MilestoneHelper.escapeSingleQuotes(endDate) + "'");
      }  
    if (!beginDate.equalsIgnoreCase("") || !endDate.equalsIgnoreCase(""))
      query.append("))"); 
    query.append(" ORDER BY header.[release_id], UPPER(header.[artist]), header.[street_date], UPPER(header.[title])");
    JdbcConnector connector = MilestoneHelper.getConnector(query.toString());
    connector.setForwardOnly(false);
    connector.runQuery();
    Vector selections = null;
    int totalCount = 0;
    int tenth = 0;
    totalCount = connector.getRowCount();
    tenth = totalCount / 10;
    if (tenth < 1)
      tenth = 1; 
    try {
      HttpServletResponse sresponse = context.getResponse();
      context.putDelivery("status", new String("start_gathering"));
      context.putDelivery("percent", new String("10"));
      if (!ReportingServices.usingReportServicesByContext(context)) {
        context.includeJSP("status.jsp", "hiddenFrame");
        sresponse.setContentType("text/plain");
        sresponse.flushBuffer();
      } 
    } catch (Exception exception) {}
    int recordCount = 0;
    int count = 0;
    selections = new Vector();
    while (connector.more()) {
      try {
        if (count < recordCount / tenth) {
          count = recordCount / tenth;
          HttpServletResponse sresponse = context.getResponse();
          context.putDelivery("status", new String("start_gathering"));
          context.putDelivery("percent", new String(String.valueOf(count * 10)));
          if (!ReportingServices.usingReportServicesByContext(context)) {
            context.includeJSP("status.jsp", "hiddenFrame");
            sresponse.setContentType("text/plain");
            sresponse.flushBuffer();
          } 
        } 
        recordCount++;
        if (bParent) {
          String prefixId = "";
          String tmpTitleId = connector.getField("title_id", "");
          String tmpSelectionNo = connector.getField("selection_no", "");
          prefixId = SelectionManager.getLookupObjectValue((PrefixCode)getLookupObject(connector.getField("prefix", ""), Cache.getPrefixCodes()));
          if (!tmpTitleId.equalsIgnoreCase(String.valueOf(prefixId) + tmpSelectionNo)) {
            connector.next();
            continue;
          } 
        } 
        int numberOfUnits = 0;
        try {
          numberOfUnits = connector.getIntegerField("pfm.[units_per_set]");
        } catch (Exception exception) {}
        Selection selection = null;
        selection = new Selection();
        selection.setSelectionID(connector.getIntegerField("release_id"));
        String selectionNo = "";
        if (connector.getFieldByName("selection_no") != null)
          selectionNo = connector.getFieldByName("selection_no"); 
        selection.setSelectionNo(selectionNo);
        selection.setProjectID(connector.getField("project_no", ""));
        String titleId = "";
        if (connector.getFieldByName("title_id") != null)
          titleId = connector.getFieldByName("title_id"); 
        selection.setTitleID(titleId);
        selection.setTitle(connector.getField("title", ""));
        selection.setArtistFirstName(connector.getField("artist_first_name", ""));
        selection.setArtistLastName(connector.getField("artist_last_name", ""));
        selection.setArtist(connector.getField("artist", ""));
        selection.setASide(connector.getField("side_a_title", ""));
        selection.setBSide(connector.getField("side_b_title", ""));
        selection.setProductCategory((ProductCategory)getLookupObject(connector.getField("product_line"), 
              Cache.getProductCategories()));
        selection.setReleaseType((ReleaseType)getLookupObject(connector.getField("release_type"), 
              Cache.getReleaseTypes()));
        selection.setSelectionConfig(getSelectionConfigObject(connector.getField("configuration"), 
              Cache.getSelectionConfigs()));
        selection.setSelectionSubConfig(getSelectionSubConfigObject(connector.getField("sub_configuration"), 
              selection.getSelectionConfig()));
        selection.setUpc(connector.getField("upc", ""));
        String sellCodeString = connector.getFieldByName("price_code");
        if (sellCodeString != null)
          selection.setPriceCode(SelectionManager.getInstance().getPriceCode(sellCodeString)); 
        selection.setSellCode(sellCodeString);
        selection.setGenre((Genre)getLookupObject(connector.getField("genre"), 
              Cache.getMusicTypes()));
        selection.setFamily((Family)MilestoneHelper.getStructureObject(connector.getIntegerField("family_id")));
        selection.setCompany((Company)MilestoneHelper.getStructureObject(connector.getIntegerField("company_id")));
        selection.setDivision((Division)MilestoneHelper.getStructureObject(connector.getIntegerField("division_id")));
        selection.setEnvironment((Environment)MilestoneHelper.getStructureObject(connector.getIntegerField("environment_id")));
        selection.setLabel((Label)MilestoneHelper.getStructureObject(connector.getIntegerField("label_id")));
        selection.setImprint(connector.getField("imprint", ""));
        selection.setReleaseFamilyId(connector.getIntegerField("release_family_id"));
        String streetDateString = connector.getFieldByName("street_date");
        if (streetDateString != null)
          selection.setStreetDate(MilestoneHelper.getDatabaseDate(streetDateString)); 
        String internationalDateString = connector.getFieldByName("international_date");
        if (internationalDateString != null)
          selection.setInternationalDate(MilestoneHelper.getDatabaseDate(internationalDateString)); 
        selection.setOtherContact(connector.getField("coordinator_contacts", ""));
        if (UserManager.getInstance().getUser(connector.getIntegerField("contact_id")) != null)
          selection.setLabelContact(UserManager.getInstance().getUser(connector.getIntegerField("contact_id"))); 
        selection.setSelectionStatus((SelectionStatus)getLookupObject(connector.getField("status"), 
              Cache.getSelectionStatusList()));
        selection.setHoldSelection(connector.getBoolean("hold_indicator"));
        selection.setHoldReason(connector.getField("hold_reason", ""));
        selection.setComments(connector.getField("comments", ""));
        selection.setSpecialPackaging(connector.getBoolean("special_pkg_indicator"));
        selection.setNumberOfUnits(numberOfUnits);
        selection.setPressAndDistribution(connector.getBoolean("pd_indicator"));
        selection.setPrefixID((PrefixCode)getLookupObject(connector.getField("prefix"), Cache.getPrefixCodes()));
        selection.setSelectionPackaging(connector.getField("packaging", ""));
        String impactDateString = connector.getFieldByName("impact_date");
        if (impactDateString != null)
          selection.setImpactDate(MilestoneHelper.getDatabaseDate(impactDateString)); 
        String lastUpdateDateString = connector.getFieldByName("last_updated_on");
        if (lastUpdateDateString != null)
          selection.setLastUpdateDate(MilestoneHelper.getDatabaseDate(lastUpdateDateString)); 
        selection.setLastUpdatingUser(UserManager.getInstance().getUser(connector.getIntegerField("last_updated_by")));
        String originDateString = connector.getFieldByName("entered_on");
        if (originDateString != null)
          selection.setOriginDate(MilestoneHelper.getDatabaseDate(originDateString)); 
        User umlContact = UserManager.getInstance().getUser(connector.getIntegerField("mfg.[uml_id]"));
        selection.setUmlContact(umlContact);
        selection.setPlant(getLookupObject(connector.getField("mfg.[plant]"), Cache.getVendors()));
        selection.setDistribution(getLookupObject(connector.getField("mfg.[distribution]"), Cache.getDistributionCodes()));
        selection.setPoQuantity(connector.getIntegerField("mfg.[order_qty]"));
        selection.setCompletedQuantity(connector.getIntegerField("mfg.[complete_qty]"));
        selection.setManufacturingComments(connector.getField("mfg.[order_comments]", ""));
        selection.setPrice(connector.getFloat("mfg.[list_price]"));
        selection.setFullSelection(true);
        String strDistribution = (reportForm.getStringValue("distribution") != null) ? reportForm.getStringValue("distribution") : "";
        String strFuture2 = (reportForm.getStringValue("Future2") != null) ? reportForm.getStringValue("Future2") : "";
        if (!strDistribution.equalsIgnoreCase("") && !strDistribution.equalsIgnoreCase("All")) {
          String selDistribution = SelectionManager.getLookupObjectValue(selection.getDistribution());
          if (!selDistribution.equalsIgnoreCase(strDistribution.trim()))
            continue; 
        } 
        int nextReleaseId = connector.getIntegerField("release_id");
        Schedule schedule = new Schedule();
        schedule.setSelectionID(nextReleaseId);
        Vector precacheSchedule = new Vector();
        while (connector.more() && connector.getIntegerField("release_id") == nextReleaseId) {
          ScheduledTask scheduledTask = null;
          scheduledTask = new ScheduledTask();
          scheduledTask.setReleaseID(connector.getIntegerField("release_Id"));
          scheduledTask.setTaskID(connector.getIntegerField("task_id"));
          scheduledTask.setOwner((Family)MilestoneHelper.getStructureObject(connector.getIntegerField("taskOwner")));
          String dueDateString = connector.getField("taskDue", "");
          if (dueDateString.length() > 0)
            scheduledTask.setDueDate(MilestoneHelper.getDatabaseDate(dueDateString)); 
          String completionDateString = connector.getField("taskComplete", "");
          if (completionDateString.length() > 0)
            scheduledTask.setCompletionDate(MilestoneHelper.getDatabaseDate(completionDateString)); 
          String taskStatus = connector.getField("taskStatus", "");
          if (taskStatus.length() > 1)
            scheduledTask.setScheduledTaskStatus(taskStatus); 
          int day = connector.getIntegerField("taskDayOfWeek");
          if (day > 0)
            scheduledTask.setDayOfTheWeek(new Day(connector.getIntegerField("taskDayOfWeek"))); 
          int weeks = connector.getIntegerField("taskWeeksToRelease");
          if (weeks > 0)
            scheduledTask.setWeeksToRelease(connector.getIntegerField("taskWeeksToRelease")); 
          String vendorString = connector.getField("taskVendor", "");
          if (vendorString.length() > 0)
            scheduledTask.setVendor(vendorString); 
          int taskAbbrevID = connector.getIntegerField("abbrev_id");
          scheduledTask.setTaskAbbreviationID(taskAbbrevID);
          int taskID = connector.getIntegerField("task_id");
          scheduledTask.setScheduledTaskID(taskID);
          String taskDept = connector.getField("department", "");
          scheduledTask.setDepartment(taskDept);
          scheduledTask.setKeyTask(connector.getBoolean("taskKey"));
          scheduledTask.setAuthorizationName(connector.getField("taskAuthName", ""));
          String authDateString = connector.getField("taskAuthDate", "");
          if (authDateString.length() > 0)
            scheduledTask.setAuthorizationDate(MilestoneHelper.getDatabaseDate(authDateString)); 
          String comments = connector.getField("taskComments", "");
          scheduledTask.setComments(comments);
          scheduledTask.setName(connector.getField("name", ""));
          precacheSchedule.add(scheduledTask);
          scheduledTask = null;
          if (connector.more()) {
            connector.next();
            recordCount++;
            continue;
          } 
          break;
        } 
        schedule.setTasks(precacheSchedule);
        selection.setSchedule(schedule);
        selections.add(selection);
      } catch (Exception exception) {}
    } 
    connector.close();
    return selections;
  }
  
  public static Vector getSelectionsForAddsReport(Context context, int reportProductType) {
    System.out.println("ReportSelections::getSelectionsForAddsReport()");
    User user = (User)context.getSessionValue("user");
    int userId = 0;
    if (user != null)
      userId = user.getUserId(); 
    return getAllSelectionsForUserUmlAdds(context, reportProductType);
  }
  
  public static Vector getSelectionsForMovesReport(Context context, int reportProductType) {
    System.out.println("ReportSelections::getSelectionsForMovesReport()");
    User user = (User)context.getSessionValue("user");
    int userId = 0;
    if (user != null)
      userId = user.getUserId(); 
    return getAllSelectionsForUserUmlMoves(context, reportProductType);
  }
  
  public static Vector getAllSelectionsForUserUmlAdds(Context context, int reportProductType) {
    System.out.println("ReportSelections::getAllSelectionsForUserUmlAdds()");
    Vector companies = MilestoneHelper.getUserCompanies(context);
    Company company = null;
    Vector precache = new Vector();
    Selection selection = null;
    StringBuffer query = new StringBuffer(200);
    Form reportForm = (Form)context.getSessionValue("reportForm");
    boolean bScheduled = (reportForm.getElement("ScheduledReleases") != null) ? ((FormCheckBox)reportForm.getElement("ScheduledReleases")).isChecked() : 0;
    String sqlJoin = "LEFT";
    if (bScheduled)
      sqlJoin = "INNER"; 
    query.append("SELECT header.[release_id], header.[artist],  header.[title], header.[artist_first_name], header.[artist_last_name],  header.[artist_first_name] + ' ' + header.[artist_last_name] AS fl_artist, header.[street_date], header.[configuration], header.[sub_configuration], header.[upc], header.[prefix], header.[selection_no],  header.[hold_reason], header.[hold_indicator], header.[pd_indicator],  header.[comments], header.[status], header.[family_id], header.[release_family_id],  header.[imprint], header.[environment_id], header.[company_id], header.[label_id], header.entered_on, header.[units],detail.* FROM Release_Header header with (nolock) " + 
        
        sqlJoin + " JOIN" + 
        " Release_SubDetail detail with (nolock) ON (header.[release_id] = detail.[release_id])" + 
        " WHERE (header.status ='TBS' OR header.status ='Active') AND (");
    String[] strFamily = ReportSelectionsHelper.getMultiSelectionListValues("family", reportForm);
    String[] strEnvironment = ReportSelectionsHelper.getMultiSelectionListValues("environment", reportForm);
    String[] strCompany = ReportSelectionsHelper.getMultiSelectionListValues("company", reportForm);
    String[] strLabel = ReportSelectionsHelper.getMultiSelectionListValues("Label", reportForm);
    String strProductType = (reportForm.getStringValue("ProductType") != null) ? reportForm.getStringValue("ProductType") : "";
    if (strProductType.equals("")) {
      strProductType = String.valueOf(reportProductType);
    } else if (strProductType.equals("Physical")) {
      strProductType = String.valueOf(0);
    } else if (strProductType.equals("Digital")) {
      strProductType = String.valueOf(1);
    } else if (strProductType.equals("Both")) {
      strProductType = String.valueOf(2);
    } 
    ReportSelectionsHelper.addMultSelectionToStringBuffer(query, "environment_id", strEnvironment, true, "environment", reportForm, true, false);
    ReportSelectionsHelper.addMultSelectionToStringBuffer(query, "company_id", strCompany, true, "company", reportForm, false, true);
    ReportSelectionsHelper.addReleasingFamilyLabelFamilyMultSelect("family", context, query, reportForm);
    ReportSelectionsHelper.addMultSelectionToStringBuffer(query, "label_id", strLabel, true, "Label", reportForm, false, true);
    if (strProductType.equals(Integer.toString(1))) {
      query.append(" AND digital_flag = 1 ");
    } else if (strProductType.equals(Integer.toString(0))) {
      query.append(" AND digital_flag = 0 ");
    } 
    String[] strConfiguration = null;
    try {
      MilestoneFormDropDownMenu configList = (MilestoneFormDropDownMenu)reportForm.getElement("configurationList");
      if (configList != null) {
        ArrayList configListAl = configList.getStringValues();
        if (configListAl != null) {
          strConfiguration = new String[configListAl.size()];
          strConfiguration = (String[])configListAl.toArray(strConfiguration);
        } 
      } 
    } catch (Exception e) {
      e.printStackTrace();
      System.out.println("<<< Subconfiguration vector execption " + e.getMessage());
    } 
    String[] strSubconfiguration = null;
    try {
      MilestoneFormDropDownMenu subconfigList = (MilestoneFormDropDownMenu)reportForm.getElement("subconfigurationList");
      if (subconfigList != null) {
        ArrayList subconfigListAl = subconfigList.getStringValues();
        if (subconfigListAl != null) {
          strSubconfiguration = new String[subconfigListAl.size()];
          strSubconfiguration = (String[])subconfigListAl.toArray(strSubconfiguration);
        } 
      } 
    } catch (Exception e) {
      e.printStackTrace();
      System.out.println("<<< Subconfiguration vector execption " + e.getMessage());
    } 
    if (strSubconfiguration != null && 
      !strSubconfiguration[0].trim().equalsIgnoreCase("0") && 
      !strSubconfiguration[0].trim().equalsIgnoreCase("") && 
      !strSubconfiguration[0].trim().equalsIgnoreCase("all")) {
      boolean addOr = false;
      query.append(" AND (");
      for (int x = 0; x < strSubconfiguration.length; x++) {
        if (addOr)
          query.append(" OR "); 
        String txtvalue = strSubconfiguration[x];
        if (txtvalue.indexOf("[") != -1) {
          String txtconfigcode = txtvalue.substring(1, txtvalue.indexOf("]"));
          String txtsubconfigcode = txtvalue.substring(txtvalue.indexOf("]") + 1, txtvalue.length());
          query.append(" (header.[configuration] = '" + txtconfigcode.trim() + "' AND ");
          query.append(" header.[sub_configuration] = '" + txtsubconfigcode.trim() + "') ");
        } else {
          query.append(" header.[sub_configuration] = '" + txtvalue.trim() + "' ");
        } 
        addOr = true;
      } 
      query.append(") ");
    } else if (strConfiguration != null && !strConfiguration[0].equalsIgnoreCase("") && !strConfiguration[0].equalsIgnoreCase("0") && 
      !strConfiguration[0].equalsIgnoreCase("all")) {
      boolean addOr = false;
      query.append(" AND (");
      for (int x = 0; x < strConfiguration.length; x++) {
        if (addOr)
          query.append(" OR "); 
        query.append(" header.[configuration] = '" + strConfiguration[x].trim() + "' ");
        addOr = true;
      } 
      query.append(") ");
    } 
    String beginDate = "";
    beginDate = (reportForm.getStringValue("beginEffectiveDate") != null && reportForm.getStringValue("beginEffectiveDate").length() > 0) ? reportForm.getStringValue("beginEffectiveDate") : "";
    if (beginDate.equalsIgnoreCase(""))
      beginDate = "01/01/1900"; 
    beginDate = String.valueOf(beginDate) + " 00:00:00";
    String endDate = "";
    endDate = (reportForm.getStringValue("endEffectiveDate") != null && reportForm.getStringValue("endEffectiveDate").length() > 0) ? reportForm.getStringValue("endEffectiveDate") : "";
    if (endDate.equalsIgnoreCase(""))
      endDate = "01/01/2200"; 
    endDate = String.valueOf(endDate) + " 23:59:59";
    if (!beginDate.equalsIgnoreCase("") || !endDate.equalsIgnoreCase(""))
      query.append(" AND ("); 
    if (!beginDate.equalsIgnoreCase(""))
      query.append(" entered_on >= '" + MilestoneHelper.escapeSingleQuotes(beginDate) + "'"); 
    if (!endDate.equalsIgnoreCase(""))
      if (!beginDate.equalsIgnoreCase("")) {
        query.append(" AND entered_on <= '" + MilestoneHelper.escapeSingleQuotes(endDate) + "'");
      } else {
        query.append(" entered_on <= '" + MilestoneHelper.escapeSingleQuotes(endDate) + "'");
      }  
    if (!beginDate.equalsIgnoreCase("") || !endDate.equalsIgnoreCase(""))
      query.append(")"); 
    query.append(") ORDER BY street_date, configuration, selection_no");
    Iterator formIterator = reportForm.getElements();
    Vector distCoIDs = new Vector();
    while (formIterator.hasNext()) {
      FormElement field = (FormElement)formIterator.next();
      String fieldName = field.getName();
      if (fieldName.startsWith("distCo")) {
        FormCheckBox fCheck = (FormCheckBox)field;
        if (fCheck.isChecked())
          distCoIDs.add(fieldName.substring(6, fieldName.length())); 
      } 
    } 
    JdbcConnector connector = MilestoneHelper.getConnector(query.toString());
    connector.setForwardOnly(false);
    connector.runQuery();
    int totalCount = 0;
    int tenth = 0;
    totalCount = connector.getRowCount();
    tenth = totalCount / 5;
    if (tenth < 1)
      tenth = 1; 
    try {
      HttpServletResponse sresponse = context.getResponse();
      context.putDelivery("status", new String("start_gathering"));
      context.putDelivery("percent", new String("10"));
      if (!ReportingServices.usingReportServicesByContext(context)) {
        context.includeJSP("status.jsp", "hiddenFrame");
        sresponse.setContentType("text/plain");
        sresponse.flushBuffer();
      } 
    } catch (Exception exception) {}
    int recordCount = 0;
    int count = 0;
    while (connector.more()) {
      try {
        if (count < recordCount / tenth) {
          count = recordCount / tenth;
          HttpServletResponse sresponse = context.getResponse();
          context.putDelivery("status", new String("start_gathering"));
          context.putDelivery("percent", new String(String.valueOf(count * 10)));
          if (!ReportingServices.usingReportServicesByContext(context)) {
            context.includeJSP("status.jsp", "hiddenFrame");
            sresponse.setContentType("text/plain");
            sresponse.flushBuffer();
          } 
        } 
        recordCount++;
        selection = new Selection();
        selection.setSelectionID(connector.getIntegerField("release_id"));
        selection.setTitle(connector.getField("title", ""));
        selection.setArtistFirstName(connector.getField("artist_first_name", ""));
        selection.setArtistLastName(connector.getField("artist_last_name", ""));
        selection.setFlArtist(connector.getField("fl_artist", ""));
        String streetDateString = connector.getFieldByName("street_date");
        if (streetDateString != null)
          selection.setStreetDate(MilestoneHelper.getDatabaseDate(streetDateString)); 
        selection.setUpc(connector.getField("upc", ""));
        selection.setSelectionConfig(
            getSelectionConfigObject(connector.getField("configuration"), 
              Cache.getSelectionConfigs()));
        selection.setSelectionSubConfig(getSelectionSubConfigObject(connector.getField("sub_configuration"), selection.getSelectionConfig()));
        selection.setPrefixID((PrefixCode)getLookupObject(connector.getField("prefix"), Cache.getPrefixCodes()));
        selection.setSelectionNo(connector.getField("selection_no"));
        selection.setSelectionStatus((SelectionStatus)getLookupObject(connector.getField("status"), 
              Cache.getSelectionStatusList()));
        selection.setOriginDate(MilestoneHelper.getDatabaseDate(connector.getField("entered_on")));
        selection.setDistribution(getLookupObject(connector.getField("distribution"), Cache.getDistributionCodes()));
        selection.setPlant(getLookupObject(connector.getField("detail.plant"), 
              Cache.getVendors()));
        selection.setHoldReason(connector.getField("hold_reason"));
        selection.setComments(connector.getField("comments"));
        selection.setPoQuantity(connector.getInt("order_qty"));
        selection.setNumberOfUnits(connector.getInt("units"));
        selection.setCompletedQuantity(connector.getInt("complete_qty"));
        selection.setLabel((Label)MilestoneHelper.getStructureObject(connector.getInt("label_id")));
        selection.setPressAndDistribution(connector.getBoolean("pd_indicator"));
        selection.setImprint(connector.getField("imprint", ""));
        selection.setReleaseFamilyId(connector.getIntegerField("release_family_id"));
        Label currentLabel = selection.getLabel();
        int selectionsDistCompany = MilestoneHelper_2.getLabelDistCoId(currentLabel.getStructureID());
        if (selectionsDistCompany == -1)
          selectionsDistCompany = Integer.parseInt("1"); 
        if (distCoIDs.contains(Integer.toString(selectionsDistCompany)))
          precache.add(selection); 
        selection = null;
        connector.next();
      } catch (Exception e) {
        System.out.println("**** Exception: " + e.toString());
        connector.next();
      } 
    } 
    connector.close();
    company = null;
    return precache;
  }
  
  public static Vector getAllSelectionsForUserUmlMoves(Context context, int reportProductType) {
    System.out.println("ReportSelections::getAllSelectionsForUserUmlMoves()");
    Vector companies = MilestoneHelper.getUserCompanies(context);
    Company company = null;
    Vector precache = new Vector();
    Selection selection = null;
    StringBuffer query = new StringBuffer(200);
    Form reportForm = (Form)context.getSessionValue("reportForm");
    boolean bScheduled = (reportForm.getElement("ScheduledReleases") != null) ? ((FormCheckBox)reportForm.getElement("ScheduledReleases")).isChecked() : 0;
    String sqlJoin = "LEFT";
    if (bScheduled)
      sqlJoin = "INNER"; 
    query.append("SELECT header.[release_id], header.[artist],  header.[title], header.[artist_first_name], header.[artist_last_name],  header.[artist_first_name] + ' ' + header.[artist_last_name] AS fl_artist, header.[street_date], header.[configuration], header.[sub_configuration], header.[upc], header.[prefix], header.[selection_no],  header.[hold_reason], header.[hold_indicator], header.[pd_indicator],  header.[comments], header.[status],header.[family_id], header.[release_family_id], audit.[logged_on],  header.[imprint], header.[environment_id], header.[company_id], header.[label_id], header.entered_on, header.[units], detail.* FROM Release_Header header with (nolock) INNER JOIN Audit_Release_Header audit with (nolock) ON header.release_id = audit.release_id " + 
        
        sqlJoin + " JOIN Release_SubDetail detail with (nolock) ON audit.release_id = detail.release_id " + 
        " WHERE (header.status ='TBS' OR header.status ='Active') AND (");
    String[] strFamily = ReportSelectionsHelper.getMultiSelectionListValues("family", reportForm);
    String[] strEnvironment = ReportSelectionsHelper.getMultiSelectionListValues("environment", reportForm);
    String[] strCompany = ReportSelectionsHelper.getMultiSelectionListValues("company", reportForm);
    String[] strLabel = ReportSelectionsHelper.getMultiSelectionListValues("Label", reportForm);
    String strProductType = (reportForm.getStringValue("ProductType") != null) ? 
      reportForm.getStringValue("ProductType") : "";
    if (strProductType.equals("")) {
      strProductType = String.valueOf(reportProductType);
    } else if (strProductType.equals("Physical")) {
      strProductType = String.valueOf(0);
    } else if (strProductType.equals("Digital")) {
      strProductType = String.valueOf(1);
    } else if (strProductType.equals("Both")) {
      strProductType = String.valueOf(2);
    } 
    ReportSelectionsHelper.addMultSelectionToStringBuffer(query, "header.environment_id", strEnvironment, true, "environment", reportForm, true, false);
    ReportSelectionsHelper.addMultSelectionToStringBuffer(query, "header.company_id", strCompany, true, "company", reportForm, false, true);
    ReportSelectionsHelper.addReleasingFamilyLabelFamilyMultSelect("family", context, query, reportForm);
    ReportSelectionsHelper.addMultSelectionToStringBuffer(query, "header.label_id", strLabel, true, "Label", reportForm, false, true);
    if (strProductType.equals(Integer.toString(1))) {
      query.append(" AND digital_flag = 1 ");
    } else if (strProductType.equals(Integer.toString(0))) {
      query.append(" AND digital_flag = 0 ");
    } 
    String[] strConfiguration = null;
    try {
      MilestoneFormDropDownMenu configList = (MilestoneFormDropDownMenu)reportForm.getElement("configurationList");
      if (configList != null) {
        ArrayList configListAl = configList.getStringValues();
        if (configListAl != null) {
          strConfiguration = new String[configListAl.size()];
          strConfiguration = (String[])configListAl.toArray(strConfiguration);
        } 
      } 
    } catch (Exception e) {
      e.printStackTrace();
      System.out.println("<<< Configuration vector execption " + e.getMessage());
    } 
    String[] strSubconfiguration = null;
    try {
      MilestoneFormDropDownMenu subconfigList = (MilestoneFormDropDownMenu)reportForm.getElement("subconfigurationList");
      if (subconfigList != null) {
        ArrayList subconfigListAl = subconfigList.getStringValues();
        if (subconfigListAl != null) {
          strSubconfiguration = new String[subconfigListAl.size()];
          strSubconfiguration = (String[])subconfigListAl.toArray(strSubconfiguration);
        } 
      } 
    } catch (Exception e) {
      e.printStackTrace();
      System.out.println("<<< Subconfiguration vector execption " + e.getMessage());
    } 
    if (strSubconfiguration != null && 
      !strSubconfiguration[0].trim().equalsIgnoreCase("0") && 
      !strSubconfiguration[0].trim().equalsIgnoreCase("") && 
      !strSubconfiguration[0].trim().equalsIgnoreCase("all")) {
      boolean addOr = false;
      query.append(" AND (");
      for (int x = 0; x < strSubconfiguration.length; x++) {
        if (addOr)
          query.append(" OR "); 
        String txtvalue = strSubconfiguration[x];
        if (txtvalue.indexOf("[") != -1) {
          String txtconfigcode = txtvalue.substring(1, txtvalue.indexOf("]"));
          String txtsubconfigcode = txtvalue.substring(txtvalue.indexOf("]") + 1, txtvalue.length());
          query.append(" (header.[configuration] = '" + txtconfigcode.trim() + "' AND ");
          query.append(" header.[sub_configuration] = '" + txtsubconfigcode.trim() + "') ");
        } else {
          query.append(" header.[sub_configuration] = '" + txtvalue.trim() + "' ");
        } 
        addOr = true;
      } 
      query.append(") ");
    } else if (strConfiguration != null && !strConfiguration[0].equalsIgnoreCase("") && !strConfiguration[0].equalsIgnoreCase("0") && 
      !strConfiguration[0].equalsIgnoreCase("all")) {
      boolean addOr = false;
      query.append(" AND (");
      for (int x = 0; x < strConfiguration.length; x++) {
        if (addOr)
          query.append(" OR "); 
        query.append(" header.[configuration] = '" + strConfiguration[x].trim() + "' ");
        addOr = true;
      } 
      query.append(") ");
    } 
    String beginDate = "";
    beginDate = (reportForm.getStringValue("beginEffectiveDate") != null && reportForm.getStringValue("beginEffectiveDate").length() > 0) ? reportForm.getStringValue("beginEffectiveDate") : "";
    if (beginDate.equalsIgnoreCase(""))
      beginDate = "01/01/1900"; 
    beginDate = String.valueOf(beginDate) + " 00:00:00";
    String endDate = "";
    endDate = (reportForm.getStringValue("endEffectiveDate") != null && reportForm.getStringValue("endEffectiveDate").length() > 0) ? reportForm.getStringValue("endEffectiveDate") : "";
    if (endDate.equalsIgnoreCase(""))
      endDate = "01/01/2200"; 
    endDate = String.valueOf(endDate) + " 23:59:59";
    if (!beginDate.equalsIgnoreCase("") || !endDate.equalsIgnoreCase(""))
      query.append(" AND ("); 
    if (!beginDate.equalsIgnoreCase(""))
      query.append(" audit.logged_on >= '" + MilestoneHelper.escapeSingleQuotes(beginDate) + "'"); 
    if (!endDate.equalsIgnoreCase(""))
      if (!beginDate.equalsIgnoreCase("")) {
        query.append(" AND audit.logged_on <= '" + MilestoneHelper.escapeSingleQuotes(endDate) + "'");
      } else {
        query.append(" audit.logged_on <= '" + MilestoneHelper.escapeSingleQuotes(endDate) + "'");
      }  
    if (!beginDate.equalsIgnoreCase("") || !endDate.equalsIgnoreCase(""))
      query.append(")"); 
    query.append(") ORDER BY UPPER(header.artist), header.street_date, UPPER(header.title)");
    Iterator formIterator = reportForm.getElements();
    Vector distCoIDs = new Vector();
    while (formIterator.hasNext()) {
      FormElement field = (FormElement)formIterator.next();
      String fieldName = field.getName();
      if (fieldName.startsWith("distCo")) {
        FormCheckBox fCheck = (FormCheckBox)field;
        if (fCheck.isChecked())
          distCoIDs.add(fieldName.substring(6, fieldName.length())); 
      } 
    } 
    JdbcConnector connector = MilestoneHelper.getConnector(query.toString());
    connector.setForwardOnly(false);
    connector.runQuery();
    int totalCount = 0;
    int tenth = 0;
    totalCount = connector.getRowCount();
    tenth = totalCount / 5;
    if (tenth < 1)
      tenth = 1; 
    try {
      HttpServletResponse sresponse = context.getResponse();
      context.putDelivery("status", new String("start_gathering"));
      context.putDelivery("percent", new String("10"));
      if (!ReportingServices.usingReportServicesByContext(context)) {
        context.includeJSP("status.jsp", "hiddenFrame");
        sresponse.setContentType("text/plain");
        sresponse.flushBuffer();
      } 
    } catch (Exception exception) {}
    int recordCount = 0;
    int count = 0;
    while (connector.more()) {
      try {
        if (count < recordCount / tenth) {
          count = recordCount / tenth;
          HttpServletResponse sresponse = context.getResponse();
          context.putDelivery("status", new String("start_gathering"));
          context.putDelivery("percent", new String(String.valueOf(count * 10)));
          if (!ReportingServices.usingReportServicesByContext(context)) {
            context.includeJSP("status.jsp", "hiddenFrame");
            sresponse.setContentType("text/plain");
            sresponse.flushBuffer();
          } 
        } 
        recordCount++;
        selection = new Selection();
        selection.setSelectionID(connector.getIntegerField("release_id"));
        selection.setTitle(connector.getField("title", ""));
        selection.setArtistFirstName(connector.getField("artist_first_name", ""));
        selection.setArtistLastName(connector.getField("artist_last_name", ""));
        selection.setFlArtist(connector.getField("fl_artist", ""));
        String streetDateString = connector.getFieldByName("street_date");
        if (streetDateString != null)
          selection.setStreetDate(MilestoneHelper.getDatabaseDate(streetDateString)); 
        selection.setUpc(connector.getField("upc", ""));
        selection.setSelectionConfig(
            getSelectionConfigObject(connector.getField("configuration"), 
              Cache.getSelectionConfigs()));
        selection.setSelectionSubConfig(getSelectionSubConfigObject(connector.getField("sub_configuration"), selection.getSelectionConfig()));
        selection.setPrefixID((PrefixCode)getLookupObject(connector.getField("prefix"), Cache.getPrefixCodes()));
        selection.setSelectionNo(connector.getField("selection_no"));
        selection.setSelectionStatus((SelectionStatus)getLookupObject(connector.getField("status"), 
              Cache.getSelectionStatusList()));
        selection.setLastStreetUpdateDate(MilestoneHelper.getDatabaseDate(connector.getField("logged_on")));
        selection.setOriginDate(MilestoneHelper.getDatabaseDate(connector.getField("entered_on")));
        selection.setPlant(getLookupObject(connector.getField("plant"), 
              Cache.getVendors()));
        selection.setDistribution(getLookupObject(connector.getField("distribution"), Cache.getDistributionCodes()));
        selection.setHoldReason(connector.getField("hold_reason"));
        selection.setComments(connector.getField("comments"));
        selection.setPoQuantity(connector.getInt("order_qty"));
        selection.setNumberOfUnits(connector.getInt("units"));
        selection.setCompletedQuantity(connector.getInt("complete_qty"));
        selection.setLabel((Label)MilestoneHelper.getStructureObject(connector.getInt("label_id")));
        selection.setPressAndDistribution(connector.getBoolean("pd_indicator"));
        selection.setImprint(connector.getField("imprint", ""));
        selection.setReleaseFamilyId(connector.getIntegerField("release_family_id"));
        Label currentLabel = selection.getLabel();
        int selectionsDistCompany = MilestoneHelper_2.getLabelDistCoId(currentLabel.getStructureID());
        if (selectionsDistCompany == -1)
          selectionsDistCompany = Integer.parseInt("1"); 
        if (distCoIDs.contains(Integer.toString(selectionsDistCompany)))
          precache.add(selection); 
        selection = null;
        connector.next();
      } catch (Exception e) {
        System.out.println("**** Exception: " + e.toString());
        connector.next();
      } 
    } 
    connector.close();
    company = null;
    return precache;
  }
  
  public static Vector getSelectionsForPhysicalProductActivityReport(Context context, int reportProductType, String activity) {
    Vector companies = MilestoneHelper.getUserCompanies(context);
    Company company = null;
    Vector precache = new Vector();
    Selection selection = null;
    StringBuffer query = new StringBuffer(300);
    Form reportForm = (Form)context.getSessionValue("reportForm");
    query.append("SELECT DISTINCT headeraudit.[release_id], headeraudit.[artist],  headeraudit.[title], headeraudit.[artist_first_name], headeraudit.[artist_last_name],  headeraudit.[artist_first_name] + ' ' + headeraudit.[artist_last_name] AS fl_artist, headeraudit.[street_date], headeraudit.[configuration], headeraudit.[sub_configuration], headeraudit.[upc], headeraudit.[prefix], headeraudit.[selection_no],  headeraudit.[status], headeraudit.[family_id], headeraudit.[release_family_id],  headeraudit.[imprint],  headeraudit.[company_id], headeraudit.[label_id],  headeraudit.[audit_date], ");
    query.append(" headeraudit.[prefix] as audit_prefix, headeraudit.[selection_no] as audit_selection_no, headeraudit.[upc] as audit_upc, headeraudit.[status] as audit_status, headeraudit.[street_date] as audit_street_date ");
    if (activity.equals("adds")) {
      query.append(" FROM Release_Header_Audit headeraudit with (nolock), Release_Header header with (nolock) WHERE (headeraudit.release_id = header.release_id) AND ");
    } else {
      query.append(" FROM Release_Header_Audit headeraudit with (nolock) WHERE ");
    } 
    String[] strFamily = ReportSelectionsHelper.getMultiSelectionListValues("family", reportForm);
    String[] strEnvironment = ReportSelectionsHelper.getMultiSelectionListValues("environment", reportForm);
    String[] strCompany = ReportSelectionsHelper.getMultiSelectionListValues("company", reportForm);
    String[] strLabel = ReportSelectionsHelper.getMultiSelectionListValues("Label", reportForm);
    ReportSelectionsHelper.addMultSelectionToStringBuffer(query, "headeraudit.environment_id", strEnvironment, true, "environment", reportForm, true, false);
    ReportSelectionsHelper.addMultSelectionToStringBuffer(query, "headeraudit.company_id", strCompany, true, "company", reportForm, false, true);
    ReportSelectionsHelper.addReleasingFamilyLabelFamilySelectForAudit("family", context, query, reportForm, "headeraudit");
    ReportSelectionsHelper.addMultSelectionToStringBuffer(query, "headeraudit.label_id", strLabel, true, "Label", reportForm, false, true);
    query.append(" AND headeraudit.digital_flag = 0 ");
    String strLabelContact = (reportForm.getStringValue("contact") != null) ? reportForm.getStringValue("contact") : "";
    if (!strLabelContact.equals("") && !strLabelContact.equals("0"))
      query.append(" AND (header.contact_id = '" + strLabelContact.trim() + "') "); 
    String strReleaseType = (reportForm.getStringValue("releaseType") != null) ? reportForm.getStringValue("releaseType") : "";
    if (!strReleaseType.equalsIgnoreCase("") && !strReleaseType.equalsIgnoreCase("all"))
      if (strReleaseType.equalsIgnoreCase("commercial")) {
        query.append(" AND (header.[release_type] ='CO') ");
      } else {
        query.append(" AND (header.[release_type] ='PR') ");
      }  
    if (activity.equals("adds"))
      ReportSelectionsHelper.addStatusToSelect(reportForm, query); 
    String[] strConfiguration = null;
    try {
      MilestoneFormDropDownMenu configList = (MilestoneFormDropDownMenu)reportForm.getElement("configurationList");
      if (configList != null) {
        ArrayList configListAl = configList.getStringValues();
        if (configListAl != null) {
          strConfiguration = new String[configListAl.size()];
          strConfiguration = (String[])configListAl.toArray(strConfiguration);
        } 
      } 
    } catch (Exception e) {
      e.printStackTrace();
      System.out.println("<<< Subconfiguration vector execption " + e.getMessage());
    } 
    String[] strSubconfiguration = null;
    try {
      MilestoneFormDropDownMenu subconfigList = (MilestoneFormDropDownMenu)reportForm.getElement("subconfigurationList");
      if (subconfigList != null) {
        ArrayList subconfigListAl = subconfigList.getStringValues();
        if (subconfigListAl != null) {
          strSubconfiguration = new String[subconfigListAl.size()];
          strSubconfiguration = (String[])subconfigListAl.toArray(strSubconfiguration);
        } 
      } 
    } catch (Exception e) {
      e.printStackTrace();
      System.out.println("<<< Subconfiguration vector execption " + e.getMessage());
    } 
    if (strSubconfiguration != null && 
      !strSubconfiguration[0].trim().equalsIgnoreCase("0") && 
      !strSubconfiguration[0].trim().equalsIgnoreCase("") && 
      !strSubconfiguration[0].trim().equalsIgnoreCase("all")) {
      boolean addOr = false;
      query.append(" AND (");
      for (int x = 0; x < strSubconfiguration.length; x++) {
        if (addOr)
          query.append(" OR "); 
        String txtvalue = strSubconfiguration[x];
        if (txtvalue.indexOf("[") != -1) {
          String txtconfigcode = txtvalue.substring(1, txtvalue.indexOf("]"));
          String txtsubconfigcode = txtvalue.substring(txtvalue.indexOf("]") + 1, txtvalue.length());
          query.append(" (headeraudit.[configuration] = '" + txtconfigcode.trim() + "' AND ");
          query.append(" headeraudit.[sub_configuration] = '" + txtsubconfigcode.trim() + "') ");
        } else {
          query.append(" headeraudit.[sub_configuration] = '" + txtvalue.trim() + "' ");
        } 
        addOr = true;
      } 
      query.append(") ");
    } else if (strConfiguration != null && !strConfiguration[0].equalsIgnoreCase("") && !strConfiguration[0].equalsIgnoreCase("0") && 
      !strConfiguration[0].equalsIgnoreCase("all")) {
      boolean addOr = false;
      query.append(" AND (");
      for (int x = 0; x < strConfiguration.length; x++) {
        if (addOr)
          query.append(" OR "); 
        query.append(" headeraudit.[configuration] = '" + strConfiguration[x].trim() + "' ");
        addOr = true;
      } 
      query.append(") ");
    } 
    if (activity.equals("adds")) {
      query.append(" AND (headeraudit.audit_code = 'I') ");
      query.append(" AND (headeraudit.status IN ('ACTIVE', 'TBS')) ");
    } 
    if (activity.equals("deletes"))
      query.append(" AND (headeraudit.audit_code = 'D') "); 
    String beginDate = "";
    beginDate = (reportForm.getStringValue("beginEffectiveDate") != null && reportForm.getStringValue("beginEffectiveDate").length() > 0) ? reportForm.getStringValue("beginEffectiveDate") : "";
    if (beginDate.equalsIgnoreCase(""))
      beginDate = "01/01/1900"; 
    beginDate = String.valueOf(beginDate) + " 00:00:00";
    String endDate = "";
    endDate = (reportForm.getStringValue("endEffectiveDate") != null && reportForm.getStringValue("endEffectiveDate").length() > 0) ? reportForm.getStringValue("endEffectiveDate") : "";
    if (endDate.equalsIgnoreCase(""))
      endDate = "01/01/2200"; 
    endDate = String.valueOf(endDate) + " 23:59:59";
    if (!beginDate.equalsIgnoreCase("") || !endDate.equalsIgnoreCase(""))
      query.append(" AND ("); 
    String tableName = "headeraudit";
    if (!beginDate.equalsIgnoreCase(""))
      query.append(" " + tableName + ".audit_date >= '" + MilestoneHelper.escapeSingleQuotes(beginDate) + "'"); 
    if (!endDate.equalsIgnoreCase(""))
      if (!beginDate.equalsIgnoreCase("")) {
        query.append(" AND " + tableName + ".audit_date <= '" + MilestoneHelper.escapeSingleQuotes(endDate) + "'");
      } else {
        query.append(" " + tableName + ".audit_date <= '" + MilestoneHelper.escapeSingleQuotes(endDate) + "'");
      }  
    if (!beginDate.equalsIgnoreCase("") || !endDate.equalsIgnoreCase(""))
      query.append(")"); 
    String beginDate2 = "";
    beginDate2 = (reportForm.getStringValue("beginDate") != null && reportForm.getStringValue("beginDate").length() > 0) ? reportForm.getStringValue("beginDate") : "";
    if (beginDate2.equalsIgnoreCase(""))
      beginDate2 = "01/01/1900"; 
    beginDate2 = String.valueOf(beginDate2) + " 00:00:00";
    String endDate2 = "";
    endDate2 = (reportForm.getStringValue("endDate") != null && reportForm.getStringValue("endDate").length() > 0) ? reportForm.getStringValue("endDate") : "";
    if (endDate2.equalsIgnoreCase(""))
      endDate2 = "01/01/2200"; 
    endDate2 = String.valueOf(endDate2) + " 23:59:59";
    if (!beginDate2.equalsIgnoreCase("") || !endDate2.equalsIgnoreCase(""))
      query.append(" AND ("); 
    if (!beginDate2.equalsIgnoreCase(""))
      query.append(" headeraudit.street_date >= '" + MilestoneHelper.escapeSingleQuotes(beginDate2) + "'"); 
    if (!endDate2.equalsIgnoreCase(""))
      if (!beginDate2.equalsIgnoreCase("")) {
        query.append(" AND headeraudit.street_date <= '" + MilestoneHelper.escapeSingleQuotes(endDate2) + "'");
      } else {
        query.append(" headeraudit.street_date <= '" + MilestoneHelper.escapeSingleQuotes(endDate2) + "'");
      }  
    if (!beginDate2.equalsIgnoreCase("") || !endDate2.equalsIgnoreCase(""))
      query.append(")"); 
    query.append(" ORDER BY headeraudit.street_date, headeraudit.configuration, headeraudit.selection_no");
    JdbcConnector connector = MilestoneHelper.getConnector(query.toString());
    connector.setForwardOnly(false);
    connector.runQuery();
    int totalCount = 0;
    int tenth = 0;
    totalCount = connector.getRowCount();
    tenth = totalCount / 5;
    if (tenth < 1)
      tenth = 1; 
    try {
      HttpServletResponse sresponse = context.getResponse();
      context.putDelivery("status", new String("start_gathering"));
      context.putDelivery("percent", new String("10"));
      if (!ReportingServices.usingReportServicesByContext(context)) {
        context.includeJSP("status.jsp", "hiddenFrame");
        sresponse.setContentType("text/plain");
        sresponse.flushBuffer();
      } 
    } catch (Exception exception) {}
    int recordCount = 0;
    int count = 0;
    while (connector.more()) {
      try {
        if (count < recordCount / tenth) {
          count = recordCount / tenth;
          HttpServletResponse sresponse = context.getResponse();
          context.putDelivery("status", new String("start_gathering"));
          context.putDelivery("percent", new String(String.valueOf(count * 10)));
          if (!ReportingServices.usingReportServicesByContext(context)) {
            context.includeJSP("status.jsp", "hiddenFrame");
            sresponse.setContentType("text/plain");
            sresponse.flushBuffer();
          } 
        } 
        recordCount++;
        selection = new Selection();
        selection.setSelectionID(connector.getIntegerField("release_id"));
        selection.setTitle(connector.getField("title", ""));
        selection.setArtistFirstName(connector.getField("artist_first_name", ""));
        selection.setArtistLastName(connector.getField("artist_last_name", ""));
        selection.setArtist(connector.getField("artist", ""));
        String streetDateString = connector.getFieldByName("street_date");
        if (streetDateString != null)
          selection.setStreetDate(MilestoneHelper.getDatabaseDate(streetDateString)); 
        selection.setUpc(connector.getField("upc", ""));
        selection.setSelectionConfig(
            getSelectionConfigObject(connector.getField("configuration"), 
              Cache.getSelectionConfigs()));
        selection.setSelectionSubConfig(getSelectionSubConfigObject(connector.getField("sub_configuration"), selection.getSelectionConfig()));
        selection.setPrefixID((PrefixCode)getLookupObject(connector.getField("prefix"), Cache.getPrefixCodes()));
        selection.setSelectionNo(connector.getField("selection_no"));
        selection.setSelectionStatus((SelectionStatus)getLookupObject(connector.getField("status"), Cache.getSelectionStatusList()));
        selection.setLabel((Label)MilestoneHelper.getStructureObject(connector.getInt("label_id")));
        selection.setImprint(connector.getField("imprint", ""));
        selection.setReleaseFamilyId(connector.getInt("release_family_id"));
        String auditDateString = connector.getFieldByName("audit_date");
        if (auditDateString != null) {
          auditDateString = String.valueOf(auditDateString) + ".0";
          selection.setAuditDate(MilestoneHelper.getDatabaseDate(auditDateString));
        } 
        selection.setAuditPrefixID((PrefixCode)getLookupObject(connector.getField("audit_prefix"), Cache.getPrefixCodes()));
        selection.setAuditSelectionNo(connector.getField("audit_selection_no"));
        selection.setAuditUPC(connector.getField("audit_upc"));
        selection.setAuditSelectionStatus((SelectionStatus)getLookupObject(connector.getField("audit_status"), Cache.getSelectionStatusList()));
        String auditStreetDateString = connector.getFieldByName("audit_street_date");
        if (auditStreetDateString != null) {
          auditStreetDateString = String.valueOf(auditStreetDateString) + ".0";
          selection.setAuditStreetDate(MilestoneHelper.getDatabaseDate(auditStreetDateString));
        } 
        boolean addToSelection = true;
        String strDistribution = (reportForm.getStringValue("distribution") != null) ? reportForm.getStringValue("distribution") : "";
        if (!strDistribution.equalsIgnoreCase("") && !strDistribution.equalsIgnoreCase("All")) {
          String selDistribution = "";
          if (selection.getLabel().getDistribution() == 1) {
            selDistribution = "East";
          } else if (selection.getLabel().getDistribution() == 0) {
            selDistribution = "West";
          } 
          if (!selDistribution.equalsIgnoreCase(strDistribution.trim()))
            addToSelection = false; 
        } 
        if (addToSelection)
          precache.add(selection); 
        selection = null;
        connector.next();
      } catch (Exception e) {
        System.out.println("**** Exception: " + e.toString());
        connector.next();
      } 
    } 
    connector.close();
    company = null;
    return precache;
  }
  
  public static Vector getSelectionsForPhysicalProductActivityReportMultiples(Context context, int reportProductType, String activity) {
    Vector companies = MilestoneHelper.getUserCompanies(context);
    Company company = null;
    Vector precache = new Vector();
    Selection selection = null;
    StringBuffer query = new StringBuffer(300);
    Form reportForm = (Form)context.getSessionValue("reportForm");
    query.append("SELECT DISTINCT header.[release_id]");
    query.append(" FROM Release_Header_Audit header with (nolock) WHERE ");
    String[] strFamily = ReportSelectionsHelper.getMultiSelectionListValues("family", reportForm);
    String[] strEnvironment = ReportSelectionsHelper.getMultiSelectionListValues("environment", reportForm);
    String[] strCompany = ReportSelectionsHelper.getMultiSelectionListValues("company", reportForm);
    String[] strLabel = ReportSelectionsHelper.getMultiSelectionListValues("Label", reportForm);
    ReportSelectionsHelper.addMultSelectionToStringBuffer(query, "header.environment_id", strEnvironment, true, "environment", reportForm, true, false);
    ReportSelectionsHelper.addMultSelectionToStringBuffer(query, "header.company_id", strCompany, true, "company", reportForm, false, true);
    ReportSelectionsHelper.addReleasingFamilyLabelFamilyMultSelect("family", context, query, reportForm);
    ReportSelectionsHelper.addMultSelectionToStringBuffer(query, "header.label_id", strLabel, true, "Label", reportForm, false, true);
    query.append(" AND header.digital_flag = 0 ");
    String[] strConfiguration = null;
    try {
      MilestoneFormDropDownMenu configList = (MilestoneFormDropDownMenu)reportForm.getElement("configurationList");
      if (configList != null) {
        ArrayList configListAl = configList.getStringValues();
        if (configListAl != null) {
          strConfiguration = new String[configListAl.size()];
          strConfiguration = (String[])configListAl.toArray(strConfiguration);
        } 
      } 
    } catch (Exception e) {
      e.printStackTrace();
      System.out.println("<<< Subconfiguration vector execption " + e.getMessage());
    } 
    String[] strSubconfiguration = null;
    try {
      MilestoneFormDropDownMenu subconfigList = (MilestoneFormDropDownMenu)reportForm.getElement("subconfigurationList");
      if (subconfigList != null) {
        ArrayList subconfigListAl = subconfigList.getStringValues();
        if (subconfigListAl != null) {
          strSubconfiguration = new String[subconfigListAl.size()];
          strSubconfiguration = (String[])subconfigListAl.toArray(strSubconfiguration);
        } 
      } 
    } catch (Exception e) {
      e.printStackTrace();
      System.out.println("<<< Subconfiguration vector execption " + e.getMessage());
    } 
    if (strSubconfiguration != null && 
      !strSubconfiguration[0].trim().equalsIgnoreCase("0") && 
      !strSubconfiguration[0].trim().equalsIgnoreCase("") && 
      !strSubconfiguration[0].trim().equalsIgnoreCase("all")) {
      boolean addOr = false;
      query.append(" AND (");
      for (int x = 0; x < strSubconfiguration.length; x++) {
        if (addOr)
          query.append(" OR "); 
        String txtvalue = strSubconfiguration[x];
        if (txtvalue.indexOf("[") != -1) {
          String txtconfigcode = txtvalue.substring(1, txtvalue.indexOf("]"));
          String txtsubconfigcode = txtvalue.substring(txtvalue.indexOf("]") + 1, txtvalue.length());
          query.append(" (header.[configuration] = '" + txtconfigcode.trim() + "' AND ");
          query.append(" header.[sub_configuration] = '" + txtsubconfigcode.trim() + "') ");
        } else {
          query.append(" header.[sub_configuration] = '" + txtvalue.trim() + "' ");
        } 
        addOr = true;
      } 
      query.append(") ");
    } else if (strConfiguration != null && !strConfiguration[0].equalsIgnoreCase("") && !strConfiguration[0].equalsIgnoreCase("0") && 
      !strConfiguration[0].equalsIgnoreCase("all")) {
      boolean addOr = false;
      query.append(" AND (");
      for (int x = 0; x < strConfiguration.length; x++) {
        if (addOr)
          query.append(" OR "); 
        query.append(" header.[configuration] = '" + strConfiguration[x].trim() + "' ");
        addOr = true;
      } 
      query.append(") ");
    } 
    if (activity.equals("cancels"))
      query.append(" AND (header.status = 'CANCEL') "); 
    String beginDate = "";
    beginDate = (reportForm.getStringValue("beginEffectiveDate") != null && reportForm.getStringValue("beginEffectiveDate").length() > 0) ? reportForm.getStringValue("beginEffectiveDate") : "";
    if (beginDate.equalsIgnoreCase(""))
      beginDate = "01/01/1900"; 
    beginDate = String.valueOf(beginDate) + " 00:00:00";
    String endDate = "";
    endDate = (reportForm.getStringValue("endEffectiveDate") != null && reportForm.getStringValue("endEffectiveDate").length() > 0) ? reportForm.getStringValue("endEffectiveDate") : "";
    if (endDate.equalsIgnoreCase(""))
      endDate = "01/01/2200"; 
    endDate = String.valueOf(endDate) + " 23:59:59";
    if (!beginDate.equalsIgnoreCase("") || !endDate.equalsIgnoreCase(""))
      query.append(" AND ("); 
    String audittableName = "header";
    String reltableName = "header";
    if (!beginDate.equalsIgnoreCase(""))
      query.append(" " + audittableName + ".audit_date >= '" + MilestoneHelper.escapeSingleQuotes(beginDate) + "'"); 
    if (!endDate.equalsIgnoreCase(""))
      if (!beginDate.equalsIgnoreCase("")) {
        query.append(" AND " + audittableName + ".audit_date <= '" + MilestoneHelper.escapeSingleQuotes(endDate) + "'");
      } else {
        query.append(" " + audittableName + ".audit_date <= '" + MilestoneHelper.escapeSingleQuotes(endDate) + "'");
      }  
    if (!beginDate.equalsIgnoreCase("") || !endDate.equalsIgnoreCase(""))
      query.append(")"); 
    String beginDate2 = "";
    beginDate2 = (reportForm.getStringValue("beginDate") != null && reportForm.getStringValue("beginDate").length() > 0) ? reportForm.getStringValue("beginDate") : "";
    if (beginDate2.equalsIgnoreCase(""))
      beginDate2 = "01/01/1900"; 
    beginDate2 = String.valueOf(beginDate2) + " 00:00:00";
    String endDate2 = "";
    endDate2 = (reportForm.getStringValue("endDate") != null && reportForm.getStringValue("endDate").length() > 0) ? reportForm.getStringValue("endDate") : "";
    if (endDate2.equalsIgnoreCase(""))
      endDate2 = "01/01/2200"; 
    endDate2 = String.valueOf(endDate2) + " 23:59:59";
    if (!beginDate2.equalsIgnoreCase("") || !endDate2.equalsIgnoreCase(""))
      query.append(" AND ("); 
    if (!beginDate2.equalsIgnoreCase(""))
      query.append(String.valueOf(reltableName) + ".street_date >= '" + MilestoneHelper.escapeSingleQuotes(beginDate2) + "'"); 
    if (!endDate2.equalsIgnoreCase(""))
      if (!beginDate2.equalsIgnoreCase("")) {
        query.append(" AND " + reltableName + ".street_date <= '" + MilestoneHelper.escapeSingleQuotes(endDate2) + "'");
      } else {
        query.append(String.valueOf(reltableName) + ".street_date <= '" + MilestoneHelper.escapeSingleQuotes(endDate2) + "'");
      }  
    if (!beginDate2.equalsIgnoreCase("") || !endDate2.equalsIgnoreCase(""))
      query.append(")"); 
    JdbcConnector connector = MilestoneHelper.getConnector(query.toString());
    connector.setForwardOnly(false);
    connector.runQuery();
    Vector validReleaseIDs = new Vector();
    while (connector.more()) {
      try {
        validReleaseIDs.add(connector.getField("release_id"));
        connector.next();
      } catch (Exception e) {
        System.out.println("**** Exception: " + e.toString());
        connector.next();
      } 
    } 
    connector.close();
    company = null;
    if (validReleaseIDs.size() == 0)
      return precache; 
    StringBuffer query2 = new StringBuffer(300);
    query2.append("SELECT header.[release_id], header.[artist],  header.[title], header.[artist_first_name], header.[artist_last_name],  header.[artist_first_name] + ' ' + header.[artist_last_name] AS fl_artist, header.[configuration], header.[street_date],  header.[sub_configuration], header.[upc], header.[prefix], header.[selection_no],  header.[status], header.[family_id], header.[release_family_id],  header.[imprint], header.[company_id], header.[label_id] ");
    query2.append(" FROM Release_Header header with (nolock) ");
    String relIdString = "";
    relIdString = "(";
    for (int i = 0; i < validReleaseIDs.size(); i++) {
      relIdString = String.valueOf(relIdString) + validReleaseIDs.elementAt(i);
      if (i + 1 < validReleaseIDs.size())
        relIdString = String.valueOf(relIdString) + ", "; 
    } 
    relIdString = String.valueOf(relIdString) + ")";
    query2.append(" WHERE header.release_id IN " + relIdString);
    String strLabelContact = (reportForm.getStringValue("contact") != null) ? reportForm.getStringValue("contact") : "";
    if (!strLabelContact.equals("") && !strLabelContact.equals("0"))
      query2.append(" AND (header.contact_id = '" + strLabelContact.trim() + "') "); 
    String strReleaseType = (reportForm.getStringValue("releaseType") != null) ? reportForm.getStringValue("releaseType") : "";
    if (!strReleaseType.equalsIgnoreCase("") && !strReleaseType.equalsIgnoreCase("all"))
      if (strReleaseType.equalsIgnoreCase("commercial")) {
        query2.append(" AND (header.[release_type] ='CO') ");
      } else {
        query2.append(" AND (header.[release_type] ='PR') ");
      }  
    if (activity.equals("cancels")) {
      query.append(" AND (header.status = 'CANCEL') ");
    } else {
      ReportSelectionsHelper.addStatusToSelect(reportForm, query2);
    } 
    connector = MilestoneHelper.getConnector(query2.toString());
    connector.setForwardOnly(false);
    connector.runQuery();
    int totalCount = 0;
    int tenth = 0;
    totalCount = connector.getRowCount();
    tenth = totalCount / 5;
    if (tenth < 1)
      tenth = 1; 
    try {
      HttpServletResponse sresponse = context.getResponse();
      context.putDelivery("status", new String("start_gathering"));
      context.putDelivery("percent", new String("10"));
      if (!ReportingServices.usingReportServicesByContext(context)) {
        context.includeJSP("status.jsp", "hiddenFrame");
        sresponse.setContentType("text/plain");
        sresponse.flushBuffer();
      } 
    } catch (Exception exception) {}
    int recordCount = 0;
    int count = 0;
    while (connector.more()) {
      try {
        if (count < recordCount / tenth) {
          count = recordCount / tenth;
          HttpServletResponse sresponse = context.getResponse();
          context.putDelivery("status", new String("start_gathering"));
          context.putDelivery("percent", new String(String.valueOf(count * 10)));
          if (!ReportingServices.usingReportServicesByContext(context)) {
            context.includeJSP("status.jsp", "hiddenFrame");
            sresponse.setContentType("text/plain");
            sresponse.flushBuffer();
          } 
        } 
        recordCount++;
        selection = new Selection();
        selection.setSelectionID(connector.getIntegerField("release_id"));
        selection.setTitle(connector.getField("title", ""));
        selection.setArtistFirstName(connector.getField("artist_first_name", ""));
        selection.setArtistLastName(connector.getField("artist_last_name", ""));
        selection.setArtist(connector.getField("artist", ""));
        String streetDateString = connector.getFieldByName("street_date");
        if (streetDateString != null)
          selection.setStreetDate(MilestoneHelper.getDatabaseDate(streetDateString)); 
        selection.setUpc(connector.getField("upc", ""));
        selection.setSelectionConfig(
            getSelectionConfigObject(connector.getField("configuration"), 
              Cache.getSelectionConfigs()));
        selection.setSelectionSubConfig(getSelectionSubConfigObject(connector.getField("sub_configuration"), selection.getSelectionConfig()));
        selection.setPrefixID((PrefixCode)getLookupObject(connector.getField("prefix"), Cache.getPrefixCodes()));
        selection.setSelectionNo(connector.getField("selection_no"));
        selection.setSelectionStatus((SelectionStatus)getLookupObject(connector.getField("status"), Cache.getSelectionStatusList()));
        selection.setLabel((Label)MilestoneHelper.getStructureObject(connector.getInt("label_id")));
        selection.setImprint(connector.getField("imprint", ""));
        selection.setReleaseFamilyId(connector.getInt("release_family_id"));
        boolean addToSelection = true;
        String strDistribution = (reportForm.getStringValue("distribution") != null) ? reportForm.getStringValue("distribution") : "";
        if (!strDistribution.equalsIgnoreCase("") && !strDistribution.equalsIgnoreCase("All")) {
          String selDistribution = "";
          if (selection.getLabel().getDistribution() == 1) {
            selDistribution = "East";
          } else if (selection.getLabel().getDistribution() == 0) {
            selDistribution = "West";
          } 
          if (!selDistribution.equalsIgnoreCase(strDistribution.trim()))
            addToSelection = false; 
        } 
        if (addToSelection)
          precache.add(selection); 
        selection = null;
        connector.next();
      } catch (Exception e) {
        System.out.println("**** Exception: " + e.toString());
        connector.next();
      } 
    } 
    connector.close();
    if (activity.equals("changes"))
      return getAuditChangeSelections(precache, beginDate, endDate, "changes"); 
    if (activity.equals("moves"))
      return getAuditChangeSelections(precache, beginDate, endDate, "moves"); 
    return getAuditChangeSelections(precache, beginDate, endDate, "cancels");
  }
  
  public static Vector getAuditChangeSelections(Vector releaseHeaderVector, String beginAuditDate, String endAuditDate, String activity) {
    Vector auditSelections = new Vector();
    Vector releaseIdsChecked = new Vector();
    JdbcConnector connector = new JdbcConnector();
    String auditResults = "";
    for (int i = 0; i < releaseHeaderVector.size(); i++) {
      Selection sel = (Selection)releaseHeaderVector.elementAt(i);
      String selId = Integer.toString(sel.getSelectionID());
      if (!releaseIdsChecked.contains(selId)) {
        releaseIdsChecked.add(selId);
        String query = "";
        if (activity.equals("changes")) {
          query = "sp_get_audit_rows_change " + sel.getSelectionID() + 
            ",'" + beginAuditDate + "'," + 
            "'" + endAuditDate + "'";
        } else if (activity.equals("cancels")) {
          query = "sp_get_audit_rows_cancel " + sel.getSelectionID() + 
            ",'" + beginAuditDate + "'," + 
            "'" + endAuditDate + "'";
        } else {
          query = "sp_get_audit_rows_move " + sel.getSelectionID() + 
            ",'" + beginAuditDate + "'," + 
            "'" + endAuditDate + "'";
        } 
        connector.setQuery(query);
        connector.runQuery();
        auditResults = connector.getField("ReturnString");
        if (!auditResults.equals("")) {
          StringTokenizer st = new StringTokenizer(auditResults, "|");
          Vector auditValues = new Vector();
          while (st.hasMoreTokens())
            auditValues.add(st.nextToken()); 
          if (activity.equals("changes")) {
            buildAuditSelectionsChanges(sel, auditValues, auditSelections);
          } else if (activity.equals("cancels")) {
            buildAuditSelectionsCancels(sel, auditValues, auditSelections);
          } else {
            buildAuditSelectionsMoves(sel, auditValues, auditSelections);
          } 
        } 
      } 
    } 
    connector.close();
    return auditSelections;
  }
  
  public static void buildAuditSelectionsCancels(Selection sel, Vector auditValues, Vector auditSelections) {
    String finalAuditDate = "";
    for (int i = 0; i < auditValues.size(); i++) {
      finalAuditDate = checkForX((String)auditValues.elementAt(i));
      if (i > 0) {
        try {
          Selection copiedSelection = (Selection)sel.clone();
          if (finalAuditDate != null) {
            finalAuditDate = String.valueOf(finalAuditDate) + ".0";
            copiedSelection.setAuditDate(MilestoneHelper.getDatabaseDate(finalAuditDate));
          } 
          auditSelections.add(copiedSelection);
        } catch (CloneNotSupportedException e) {
          System.out.println("clone exception in ReportSelections.buildAuditSelectionsCancels()");
        } 
      } else {
        if (finalAuditDate != null) {
          finalAuditDate = String.valueOf(finalAuditDate) + ".0";
          sel.setAuditDate(MilestoneHelper.getDatabaseDate(finalAuditDate));
        } 
        auditSelections.add(sel);
      } 
    } 
  }
  
  public static void buildAuditSelectionsChanges(Selection sel, Vector auditValues, Vector auditSelections) {
    String initalPrefix = "";
    String finalPrefix = "";
    String initalSelection_no = "";
    String finalSelection_no = "";
    String initalAuditDate = "";
    String finalAuditDate = "";
    for (int i = 0; i < auditValues.size(); i += 6) {
      initalPrefix = checkForX((String)auditValues.elementAt(i));
      initalSelection_no = checkForX((String)auditValues.elementAt(i + 1));
      initalAuditDate = checkForX((String)auditValues.elementAt(i + 2));
      finalPrefix = checkForX((String)auditValues.elementAt(i + 3));
      finalSelection_no = checkForX((String)auditValues.elementAt(i + 4));
      finalAuditDate = checkForX((String)auditValues.elementAt(i + 5));
      if (i > 0) {
        try {
          Selection copiedSelection = (Selection)sel.clone();
          copiedSelection.setPrefixID((PrefixCode)SelectionManager.getLookupObject(finalPrefix, Cache.getPrefixCodes()));
          copiedSelection.setAuditPrefixID((PrefixCode)SelectionManager.getLookupObject(initalPrefix, Cache.getPrefixCodes()));
          copiedSelection.setSelectionNo(finalSelection_no);
          copiedSelection.setAuditSelectionNo(initalSelection_no);
          if (finalAuditDate != null) {
            finalAuditDate = String.valueOf(finalAuditDate) + ".0";
            copiedSelection.setAuditDate(MilestoneHelper.getDatabaseDate(finalAuditDate));
          } 
          auditSelections.add(copiedSelection);
        } catch (CloneNotSupportedException e) {
          System.out.println("clone exception in ReportSelections.buildAuditSelectionsChanges()");
        } 
      } else {
        sel.setPrefixID((PrefixCode)SelectionManager.getLookupObject(finalPrefix, Cache.getPrefixCodes()));
        sel.setAuditPrefixID((PrefixCode)SelectionManager.getLookupObject(initalPrefix, Cache.getPrefixCodes()));
        sel.setSelectionNo(finalSelection_no);
        sel.setAuditSelectionNo(initalSelection_no);
        if (finalAuditDate != null) {
          finalAuditDate = String.valueOf(finalAuditDate) + ".0";
          sel.setAuditDate(MilestoneHelper.getDatabaseDate(finalAuditDate));
        } 
        auditSelections.add(sel);
      } 
    } 
  }
  
  public static void buildAuditSelectionsMoves(Selection sel, Vector auditValues, Vector auditSelections) {
    String initalStreetDate = "";
    String initalAuditDate = "";
    String initialStatus = "";
    String finalSteetDate = "";
    String finalAuditDate = "";
    String finalStatus = "";
    for (int i = 0; i < auditValues.size(); i += 6) {
      initialStatus = (String)auditValues.elementAt(i);
      initalStreetDate = (String)auditValues.elementAt(i + 1);
      initalAuditDate = (String)auditValues.elementAt(i + 2);
      finalStatus = (String)auditValues.elementAt(i + 3);
      finalSteetDate = (String)auditValues.elementAt(i + 4);
      finalAuditDate = (String)auditValues.elementAt(i + 5);
      if (i > 0) {
        try {
          Selection copiedSelection = (Selection)sel.clone();
          copiedSelection.setStreetDate(MilestoneHelper.getDatabaseDate(finalSteetDate));
          if (initalStreetDate != null) {
            initalStreetDate = String.valueOf(initalStreetDate) + ".0";
            copiedSelection.setAuditStreetDate(MilestoneHelper.getDatabaseDate(initalStreetDate));
          } 
          copiedSelection.setSelectionStatus((SelectionStatus)MilestoneHelper.getLookupObject(finalStatus, Cache.getSelectionStatusList()));
          copiedSelection.setAuditSelectionStatus((SelectionStatus)MilestoneHelper.getLookupObject(initialStatus, Cache.getSelectionStatusList()));
          if (finalAuditDate != null) {
            finalAuditDate = String.valueOf(finalAuditDate) + ".0";
            copiedSelection.setAuditDate(MilestoneHelper.getDatabaseDate(finalAuditDate));
          } 
          auditSelections.add(copiedSelection);
        } catch (CloneNotSupportedException e) {
          System.out.println("clone exception in ReportSelections.buildAuditSelectionsChanges()");
        } 
      } else {
        sel.setStreetDate(MilestoneHelper.getDatabaseDate(finalSteetDate));
        if (initalStreetDate != null) {
          initalStreetDate = String.valueOf(initalStreetDate) + ".0";
          sel.setAuditStreetDate(MilestoneHelper.getDatabaseDate(initalStreetDate));
        } 
        sel.setAuditSelectionStatus((SelectionStatus)MilestoneHelper.getLookupObject(initialStatus, Cache.getSelectionStatusList()));
        sel.setSelectionStatus((SelectionStatus)MilestoneHelper.getLookupObject(finalStatus, Cache.getSelectionStatusList()));
        if (finalAuditDate != null) {
          finalAuditDate = String.valueOf(finalAuditDate) + ".0";
          sel.setAuditDate(MilestoneHelper.getDatabaseDate(finalAuditDate));
        } 
        auditSelections.add(sel);
      } 
    } 
  }
  
  public static String checkForX(String toCheck) {
    if (toCheck.equals("XXX"))
      return ""; 
    return toCheck;
  }
}


/* Location:              D:\Documents\NetBeansProjects\milestone2 Local.war!\WEB-INF\classes\co\\universal\milestone\ReportSelections.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */