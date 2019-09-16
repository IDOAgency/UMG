package WEB-INF.classes.com.universal.milestone;

import com.techempower.ComponentLog;
import com.techempower.gemini.Context;
import com.techempower.gemini.FormCheckBox;
import com.techempower.gemini.GeminiApplication;
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
import com.universal.milestone.MilestoneHelper;
import com.universal.milestone.MilestoneHelper_2;
import com.universal.milestone.PrefixCode;
import com.universal.milestone.ProductCategory;
import com.universal.milestone.ReleaseType;
import com.universal.milestone.ReportSelectionsHelper;
import com.universal.milestone.Schedule;
import com.universal.milestone.ScheduledTask;
import com.universal.milestone.SecureHandler;
import com.universal.milestone.Selection;
import com.universal.milestone.SelectionManager;
import com.universal.milestone.SelectionStatus;
import com.universal.milestone.TaskDueDateByTitleForPrintHandler;
import com.universal.milestone.User;
import com.universal.milestone.UserManager;
import inetsoft.report.CompositeSheet;
import inetsoft.report.SectionBand;
import inetsoft.report.SeparatorElement;
import inetsoft.report.StyleSheet;
import inetsoft.report.XStyleSheet;
import inetsoft.report.io.Builder;
import inetsoft.report.lens.DefaultSectionLens;
import inetsoft.report.lens.DefaultTableLens;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Rectangle;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Vector;
import javax.servlet.http.HttpServletResponse;

public class TaskDueDateByTitleForPrintHandler extends SecureHandler {
  public static final String COMPONENT_CODE = "hTaskDueBySel";
  
  public GeminiApplication application;
  
  public ComponentLog log;
  
  public TaskDueDateByTitleForPrintHandler(GeminiApplication application) {
    this.application = application;
    this.log = application.getLog("hTaskDueBySel");
  }
  
  public String getDescription() { return "Sub Report"; }
  
  protected static StyleSheet fillTaskDueDateByTitleForPrint(Context context, String reportPath, ComponentLog log) {
    Color SHADED_AREA_COLOR = Color.lightGray;
    int COL_LINE_STYLE = 4097;
    int HEADER_FONT_SIZE = 12;
    int NUM_COLUMNS = 9;
    double ldLineVal = 0.3D;
    int separatorLineStyle = 266240;
    Color separatorLineColor = Color.black;
    int tableHeaderLineStyle = 266240;
    Color tableHeaderLineColor = Color.black;
    int tableRowLineStyle = 4097;
    Color tableRowLineColor = new Color(208, 206, 206, 0);
    try {
      HttpServletResponse sresponse = context.getResponse();
      context.putDelivery("status", new String("start_gathering"));
      context.includeJSP("status.jsp", "hiddenFrame");
      sresponse.setContentType("text/plain");
      sresponse.flushBuffer();
    } catch (Exception exception) {}
    Vector selections = getTaskDueSelectionsForReport(context, log);
    try {
      HttpServletResponse sresponse = context.getResponse();
      context.putDelivery("status", new String("start_report"));
      context.putDelivery("percent", new String("10"));
      context.includeJSP("status.jsp", "hiddenFrame");
      sresponse.setContentType("text/plain");
      sresponse.flushBuffer();
    } catch (Exception exception) {}
    int numSelections = selections.size();
    MilestoneHelper.setSelectionSorting(selections, 10);
    Collections.sort(selections);
    MilestoneHelper.setSelectionSorting(selections, 6);
    Collections.sort(selections);
    if (numSelections == 0)
      return null; 
    StyleSheet[] sheets = new StyleSheet[numSelections];
    try {
      DefaultTableLens table_contents = null;
      DefaultTableLens rowCountTable = null;
      DefaultTableLens columnHeaderTable = null;
      DefaultTableLens subTable = null;
      DefaultTableLens headerTableLens = null;
      rowCountTable = new DefaultTableLens(2, 10000);
      for (int i = 0; i < numSelections; i++) {
        InputStream input = new FileInputStream(String.valueOf(reportPath) + "\\taskDueDateByTitleId.xml");
        XStyleSheet report = (XStyleSheet)Builder.getBuilder(1, input).read(null);
        SectionBand hbandType = new SectionBand(report);
        SectionBand hbandCategory = new SectionBand(report);
        SectionBand hbandDate = new SectionBand(report);
        SectionBand body = new SectionBand(report);
        SectionBand tbody = new SectionBand(report);
        SectionBand footer = new SectionBand(report);
        SectionBand spacer = new SectionBand(report);
        DefaultSectionLens group = null;
        footer.setVisible(true);
        footer.setHeight(0.1F);
        footer.setShrinkToFit(false);
        footer.setBottomBorder(0);
        spacer.setVisible(true);
        spacer.setHeight(0.05F);
        spacer.setShrinkToFit(false);
        spacer.setBottomBorder(0);
        SeparatorElement topSeparator = (SeparatorElement)report.getElement("separator_top");
        SeparatorElement bottomHeaderSeparator = (SeparatorElement)report.getElement("separator_bottom_header");
        SeparatorElement bottomSeparator = (SeparatorElement)report.getElement("separator_bottom");
        if (topSeparator != null) {
          topSeparator.setStyle(separatorLineStyle);
          topSeparator.setForeground(separatorLineColor);
        } 
        if (bottomHeaderSeparator != null) {
          bottomHeaderSeparator.setStyle(separatorLineStyle);
          bottomHeaderSeparator.setForeground(separatorLineColor);
        } 
        if (bottomSeparator != null) {
          bottomSeparator.setStyle(separatorLineStyle);
          bottomSeparator.setForeground(separatorLineColor);
        } 
        Form reportForm = (Form)context.getSessionValue("reportForm");
        Calendar beginDueDate = (reportForm.getStringValue("beginDueDate") != null && 
          reportForm.getStringValue("beginDueDate").length() > 0) ? 
          MilestoneHelper.getDate(reportForm.getStringValue("beginDueDate")) : null;
        Calendar endDueDate = (reportForm.getStringValue("endDueDate") != null && 
          reportForm.getStringValue("endDueDate").length() > 0) ? 
          MilestoneHelper.getDate(reportForm.getStringValue("endDueDate")) : null;
        report.setElement("startdate", MilestoneHelper.getFormatedDate(beginDueDate));
        report.setElement("enddate", MilestoneHelper.getFormatedDate(endDueDate));
        SimpleDateFormat formatter = new SimpleDateFormat("MMMM d, yyyy");
        String todayLong = formatter.format(new Date());
        report.setElement("bottomdate", todayLong);
        String strUmlKey = (reportForm.getStringValue("umlKeyTask") != null) ? reportForm.getStringValue("umlKeyTask") : "";
        Selection sel = (Selection)selections.elementAt(i);
        if (sel != null) {
          String selFamily = (sel.getFamily() != null && sel.getFamily().getName() != null) ? 
            sel.getFamily().getName() : "";
          String selCompany = (sel.getCompany() != null && sel.getCompany().getName() != null) ? 
            sel.getCompany().getName() : "";
          int region = (sel.getEnvironment() != null && sel.getEnvironment().getDistribution() >= 0) ? sel.getEnvironment().getDistribution() : -1;
          String selRegion = "";
          if (region == 0) {
            selRegion = "West";
          } else if (region == 1) {
            selRegion = "East";
          } 
          String selProject = (sel.getProjectID() != null) ? sel.getProjectID() : "";
          String selPD = sel.getPressAndDistribution() ? "Yes" : "";
          String selDivision = (sel.getDivision() != null && sel.getDivision().getName() != null) ? 
            sel.getDivision().getName() : "";
          String selLabel = (sel.getLabel() != null && sel.getLabel().getName() != null) ? 
            sel.getLabel().getName() : "";
          String selId = (sel.getSelectionNo() != null) ? sel.getSelectionNo() : "";
          String selUpc = (sel.getUpc() != null) ? sel.getUpc() : "";
          selUpc = MilestoneHelper_2.getRMSReportFormat(selUpc, "UPC", sel.getIsDigital());
          String selConfig = (sel.getSelectionSubConfig() != null && sel.getSelectionSubConfig().getSelectionSubConfigurationAbbreviation() != null) ? 
            sel.getSelectionSubConfig().getSelectionSubConfigurationAbbreviation() : "";
          String selStreetDate = "";
          selStreetDate = MilestoneHelper.getFormatedDate(sel.getStreetDate());
          String statusString = "";
          SelectionStatus status = sel.getSelectionStatus();
          if (status != null)
            statusString = (status.getName() == null) ? "" : status.getName(); 
          if (!statusString.equalsIgnoreCase("ACTIVE"))
            if (!statusString.equalsIgnoreCase("In The Works")) {
              selStreetDate = String.valueOf(statusString) + " " + selStreetDate;
            } else {
              selStreetDate = "ITW " + selStreetDate;
            }  
          String selArtist = (sel.getArtist() != null) ? sel.getArtist() : "";
          String selTitle = (sel.getTitle() != null) ? sel.getTitle() : "";
          String selTitleId = "";
          selTitleId = (sel.getTitleID() != null) ? sel.getTitleID() : "";
          String selFamilyLabel = String.valueOf(selFamily) + "/" + selLabel;
          report.setElement("artist", selArtist);
          report.setElement("title", selTitle);
          report.setElement("project", selProject);
          report.setElement("titleId", selTitleId);
          report.setElement("company", selCompany);
          report.setElement("familylabel", selFamilyLabel);
          Schedule schedule = sel.getSchedule();
          Vector scheduledTasks = new Vector();
          if (schedule != null) {
            scheduledTasks = schedule.getTasks();
            if (scheduledTasks != null && strUmlKey.equalsIgnoreCase("label"))
              scheduledTasks = MilestoneHelper.filterUmlTasks(scheduledTasks, false); 
            if (scheduledTasks != null && strUmlKey.equalsIgnoreCase("uml"))
              scheduledTasks = MilestoneHelper.filterUmlTasks(scheduledTasks, true); 
            if (scheduledTasks == null)
              scheduledTasks = new Vector(); 
          } 
          int commentCount = MilestoneHelper.countTasksWithComments(scheduledTasks);
          table_contents = new DefaultTableLens(1, 9);
          table_contents.setColAlignment(0, 1);
          table_contents.setColAlignment(1, 1);
          table_contents.setColAlignment(2, 1);
          table_contents.setColAlignment(3, 1);
          table_contents.setColAlignment(4, 4);
          table_contents.setColAlignment(6, 1);
          table_contents.setColAlignment(7, 12);
          table_contents.setColBorderColor(Color.black);
          table_contents.setRowBorderColor(Color.black);
          table_contents.setRowBorder(0);
          table_contents.setColBorder(0);
          table_contents.setRowBorder(1, 4097);
          table_contents.setColWidth(0, 35);
          table_contents.setLineWrap(0, 0, false);
          table_contents.setLineWrap(0, 1, false);
          table_contents.setColWidth(1, 82);
          table_contents.setColWidth(2, 55);
          table_contents.setColWidth(3, 45);
          table_contents.setColWidth(4, 45);
          table_contents.setColWidth(5, 1);
          table_contents.setColWidth(6, 4);
          table_contents.setColWidth(7, 52);
          table_contents.setColWidth(8, 1);
          table_contents.setHeaderRowCount(1);
          table_contents.setObject(0, 0, "Description /");
          table_contents.setFont(0, 0, new Font("Arial", 1, 10));
          table_contents.setObject(0, 1, "Comments");
          table_contents.setFont(0, 1, new Font("Arial", 3, 10));
          table_contents.setObject(0, 2, "Task Owner");
          table_contents.setFont(0, 2, new Font("Arial", 1, 10));
          table_contents.setObject(0, 3, "Department");
          table_contents.setFont(0, 3, new Font("Arial", 1, 10));
          table_contents.setObject(0, 4, "Date Due");
          table_contents.setFont(0, 4, new Font("Arial", 1, 10));
          table_contents.setSpan(0, 4, new Dimension(2, 1));
          table_contents.setObject(0, 7, "Complete");
          table_contents.setSpan(0, 7, new Dimension(2, 1));
          table_contents.setFont(0, 7, new Font("Arial", 1, 10));
          table_contents.setRowBorder(-1, 0);
          hbandType.addTable(table_contents, new Rectangle(0, 0, 800, 30));
          int nextRow = 0;
          String saveSelectionNo = ";;;;";
          boolean printTitleId = false;
          for (int j = 0; j < scheduledTasks.size(); j++) {
            String dueDateHolidayFlg, taskDueDate;
            ScheduledTask task = (ScheduledTask)scheduledTasks.get(j);
            if (!task.getSelectionNo().equalsIgnoreCase(saveSelectionNo)) {
              printTitleId = true;
              nextRow = 0;
              headerTableLens = new DefaultTableLens(2, 9);
              headerTableLens.setColWidth(nextRow, 3);
              headerTableLens.setColAlignment(nextRow, 1);
              headerTableLens.setColWidth(1, 3);
              headerTableLens.setColWidth(3, 4);
              headerTableLens.setColWidth(3, 1);
              headerTableLens.setColAlignment(3, 1);
              headerTableLens.setColAlignment(4, 1);
              headerTableLens.setColWidth(4, 5);
              headerTableLens.setColWidth(5, 1);
              headerTableLens.setColWidth(6, 3);
              headerTableLens.setColAlignment(6, 4);
              headerTableLens.setColWidth(8, 2);
              headerTableLens.setColBorder(-1, 4097);
              headerTableLens.setColBorder(nextRow, 0);
              headerTableLens.setColBorder(1, 0);
              headerTableLens.setColBorder(2, 0);
              headerTableLens.setColBorder(3, 0);
              headerTableLens.setColBorder(4, 0);
              headerTableLens.setColBorder(5, 0);
              headerTableLens.setColBorder(6, 0);
              headerTableLens.setColBorder(7, 0);
              headerTableLens.setColBorderColor(Color.black);
              headerTableLens.setRowBorderColor(Color.black);
              headerTableLens.setRowBackground(nextRow, Color.lightGray);
              headerTableLens.setFont(nextRow, 0, new Font("Arial", 3, 9));
              headerTableLens.setFont(nextRow, 1, new Font("Arial", 0, 9));
              headerTableLens.setFont(nextRow, 3, new Font("Arial", 3, 9));
              headerTableLens.setFont(nextRow, 4, new Font("Arial", 0, 9));
              headerTableLens.setFont(nextRow, 6, new Font("Arial", 3, 9));
              headerTableLens.setFont(nextRow, 8, new Font("Arial", 0, 9));
              headerTableLens.setObject(nextRow, 0, "Local Prod #:");
              headerTableLens.setObject(nextRow, 1, task.getSelectionNo().trim());
              headerTableLens.setObject(nextRow, 3, "UPC");
              String upc = task.getUpc().trim();
              upc = MilestoneHelper_2.getRMSReportFormat(upc, "UPC", sel.getIsDigital());
              headerTableLens.setObject(nextRow, 4, upc);
              headerTableLens.setObject(nextRow, 6, "Street Date");
              String streetDate = (task.getStreetDate() != null) ? 
                MilestoneHelper.getFormatedDate(task.getStreetDate()) : "";
              headerTableLens.setObject(nextRow, 8, streetDate);
              saveSelectionNo = task.getSelectionNo();
            } 
            nextRow = 0;
            boolean taskHasComment = false;
            String taskComments = (task.getComments() != null) ? task.getComments().trim() : "";
            if (!taskComments.equals("") && !taskComments.equalsIgnoreCase("null"))
              taskHasComment = true; 
            if (taskHasComment) {
              subTable = new DefaultTableLens(2, 9);
            } else {
              subTable = new DefaultTableLens(1, 9);
            } 
            subTable.setColAlignment(0, 1);
            subTable.setColAlignment(1, 1);
            subTable.setColAlignment(2, 1);
            subTable.setColAlignment(3, 1);
            subTable.setColAlignment(4, 4);
            subTable.setColAlignment(6, 1);
            subTable.setColAlignment(7, 12);
            subTable.setColWidth(0, 35);
            subTable.setLineWrap(0, 0, false);
            subTable.setLineWrap(0, 1, false);
            subTable.setColWidth(1, 82);
            subTable.setColWidth(2, 55);
            subTable.setColWidth(3, 45);
            subTable.setColWidth(4, 45);
            subTable.setColWidth(5, 1);
            subTable.setColWidth(6, 4);
            subTable.setColWidth(7, 52);
            subTable.setColWidth(8, 1);
            subTable.setColBorderColor(Color.black);
            subTable.setRowBorderColor(Color.black);
            subTable.setRowBorder(0);
            subTable.setColBorder(0);
            subTable.setRowBorder(2, 4097);
            String taskName = (task.getName() != null) ? task.getName().trim() : "";
            String taskOwner = (task.getOwner() != null && task.getOwner().getName() != null) ? 
              task.getOwner().getName() : "";
            String taskDepartment = (task.getDepartment() != null && !task.getDepartment().equalsIgnoreCase("-1") && !task.getDepartment().equalsIgnoreCase("null")) ? 
              task.getDepartment() : "";
            if (task.getDueDate() != null) {
              taskDueDate = MilestoneHelper.getFormatedDate(task.getDueDate());
              dueDateHolidayFlg = MilestoneHelper.getDayType(sel.getCalendarGroup(), task.getDueDate());
            } else {
              taskDueDate = "";
              dueDateHolidayFlg = "";
            } 
            String taskStatus = task.getScheduledTaskStatus();
            String taskCompletionDate = (task.getCompletionDate() != null) ? 
              MilestoneHelper.getFormatedDate(task.getCompletionDate()) : "";
            if (taskStatus.equalsIgnoreCase("N/A"))
              taskCompletionDate = "N/A"; 
            subTable.setObject(nextRow, 0, taskName);
            subTable.setSpan(nextRow, 0, new Dimension(2, 1));
            subTable.setObject(nextRow, 2, taskOwner);
            subTable.setObject(nextRow, 3, taskDepartment);
            subTable.setObject(nextRow, 4, taskDueDate);
            subTable.setObject(nextRow, 6, dueDateHolidayFlg);
            subTable.setObject(nextRow, 7, taskCompletionDate);
            subTable.setRowFont(nextRow, new Font("Arial", 0, 9));
            if (dueDateHolidayFlg != "") {
              subTable.setFont(nextRow, 4, new Font("Arial", 3, 9));
              subTable.setFont(nextRow, 6, new Font("Arial", 3, 9));
            } 
            if (taskHasComment) {
              nextRow++;
              subTable.setSpan(nextRow, 0, new Dimension(2, 1));
              subTable.setObject(nextRow, 0, taskComments);
              subTable.setRowFont(nextRow, new Font("Arial", 2, 9));
              subTable.setRowBorder(nextRow - 1, 0);
            } 
            subTable.setRowBorder(nextRow, tableRowLineStyle);
            subTable.setRowBorderColor(nextRow, tableRowLineColor);
            if (printTitleId) {
              tbody = new SectionBand(report);
              tbody.setHeight(0.3F);
              tbody.addTable(headerTableLens, new Rectangle(800, 30));
              tbody.setVisible(true);
              group = new DefaultSectionLens(null, group, tbody);
            } 
            body = new SectionBand(report);
            double lfLineCount = 1.5D;
            body.setHeight(1.3F);
            body.addTable(subTable, new Rectangle(800, 800));
            body.setBottomBorder(0);
            body.setTopBorder(0);
            body.setShrinkToFit(true);
            body.setVisible(true);
            group = new DefaultSectionLens(null, group, body);
            printTitleId = false;
          } 
          group = new DefaultSectionLens(hbandType, group, null);
          report.addSection(group, rowCountTable);
          group = null;
        } 
        sheets[i] = report;
      } 
    } catch (Exception e) {
      System.out.println(">>>>>>>>ReportHandler.fillEntProdStatusForPrint(): exception: " + e);
    } 
    return new CompositeSheet(sheets);
  }
  
  public static Vector getTaskDueSelectionsForReport(Context context, ComponentLog log) {
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
    query.append("SELECT header.release_id, header.title_id, header.selection_no, header.prefix,   pfm.units_per_set, detail.due_date AS taskDue, detail.comments AS taskComments,   detail.owner AS taskOwner, detail.completion_date AS taskComplete,   detail.status AS taskStatus, detail.day_of_week AS taskDayOfWeek,   detail.weeks_to_release AS taskWeeksToRelease, detail.key_task_indicator AS taskKey,    detail.vendor AS taskVendor, detail.authorization_name AS taskAuthName,   detail.authorization_date AS taskAuthDate, detail.last_updated_ck, header.project_no,   header.title, header.artist_first_name, header.artist_last_name, header.artist,   header.side_a_title, header.side_b_title, header.product_line, header.release_type,   header.configuration, header.sub_configuration, header.UPC, header.price_code, header.genre,   header.family_id, header.company_id, header.environment_id, header.division_id, header.label_id, header.street_date,   header.international_date, header.coordinator_contacts, header.contact_id, header.status,   header.hold_indicator, header.hold_reason, header.comments, header.special_pkg_indicator,   header.pd_indicator, header.packaging, header.impact_date, header.last_updated_on,   header.last_updated_by, header.entered_on, mfg.uml_id, mfg.plant, mfg.distribution,   mfg.order_qty, mfg.complete_qty, mfg.order_comments, Task.task_id, Task.department,   Task.name, Task.abbrev_id  FROM vi_release_header header with (nolock) LEFT JOIN vi_release_subdetail mfg with (nolock) ON header.[release_id] = mfg.[release_id] LEFT JOIN vi_release_detail detail with (nolock) ON header.[release_id] = detail.[release_id] " + 
        
        dueDateQuery.toString() + 
        " INNER JOIN vi_Task task with (nolock)" + 
        " ON detail.[task_id] = task.[task_id]" + 
        " LEFT JOIN vi_pfm_selection pfm with (nolock)" + 
        " ON header.[release_id] = pfm.[release_id]" + 
        " WHERE ");
    String strLabelContact = (reportForm.getStringValue("contact") != null) ? reportForm.getStringValue("contact") : "";
    String strUmlContact = (reportForm.getStringValue("umlContact") != null) ? reportForm.getStringValue("umlContact") : "";
    String strReleaseType = (reportForm.getStringValue("releaseType") != null) ? reportForm.getStringValue("releaseType") : "";
    String strStatus = (reportForm.getStringValue("status") != null) ? reportForm.getStringValue("status") : "";
    String strArtist = (reportForm.getStringValue("artist") != null) ? reportForm.getStringValue("artist") : "";
    String[] strFamily = ReportSelectionsHelper.getMultiSelectionListValues("family", reportForm);
    String[] strEnvironment = ReportSelectionsHelper.getMultiSelectionListValues("environment", reportForm);
    String[] strCompany = ReportSelectionsHelper.getMultiSelectionListValues("company", reportForm);
    String[] strLabel = ReportSelectionsHelper.getMultiSelectionListValues("Label", reportForm);
    boolean bParent = (reportForm.getElement("ParentsOnly") != null) ? ((FormCheckBox)reportForm.getElement("ParentsOnly")).isChecked() : 0;
    ReportSelectionsHelper.addMultSelectionToStringBuffer(query, "header.environment_id", strEnvironment, true, "environment", reportForm, true, false);
    ReportSelectionsHelper.addMultSelectionToStringBuffer(query, "header.company_id", strCompany, true, "company", reportForm, false, true);
    ReportSelectionsHelper.addReleasingFamilyLabelFamilyMultSelect("family", context, query, reportForm);
    ReportSelectionsHelper.addMultSelectionToStringBuffer(query, "header.label_id", strLabel, true, "Label", reportForm, false, true);
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
    query.append(" ORDER BY header.[title_id], header.[release_id], taskDue, taskComplete ");
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
      context.includeJSP("status.jsp", "hiddenFrame");
      sresponse.setContentType("text/plain");
      sresponse.flushBuffer();
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
          context.includeJSP("status.jsp", "hiddenFrame");
          sresponse.setContentType("text/plain");
          sresponse.flushBuffer();
        } 
        recordCount++;
        if (bParent) {
          String prefixId = "";
          String tmpTitleId = connector.getField("title_id", "");
          String tmpSelectionNo = connector.getField("selection_no", "");
          prefixId = SelectionManager.getLookupObjectValue((PrefixCode)MilestoneHelper.getLookupObject(connector.getField("prefix", ""), Cache.getPrefixCodes()));
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
        selection.setProductCategory(
            (ProductCategory)MilestoneHelper.getLookupObject(connector.getField("product_line"), Cache.getProductCategories()));
        selection.setReleaseType(
            (ReleaseType)MilestoneHelper.getLookupObject(connector.getField("release_type"), 
              Cache.getReleaseTypes()));
        selection.setSelectionConfig(
            MilestoneHelper.getSelectionConfigObject(connector.getField("configuration"), Cache.getSelectionConfigs()));
        selection.setSelectionSubConfig(
            MilestoneHelper.getSelectionSubConfigObject(connector.getField("sub_configuration"), selection.getSelectionConfig()));
        selection.setUpc(connector.getField("upc", ""));
        String sellCodeString = connector.getFieldByName("price_code");
        if (sellCodeString != null)
          selection.setPriceCode(SelectionManager.getInstance().getPriceCode(sellCodeString)); 
        selection.setSellCode(sellCodeString);
        selection.setGenre(
            (Genre)MilestoneHelper.getLookupObject(connector.getField("genre"), Cache.getMusicTypes()));
        selection.setFamily((Family)MilestoneHelper.getStructureObject(connector.getIntegerField("family_id")));
        selection.setEnvironment((Environment)MilestoneHelper.getStructureObject(connector.getIntegerField("environment_id")));
        selection.setCompany((Company)MilestoneHelper.getStructureObject(connector.getIntegerField("company_id")));
        selection.setDivision((Division)MilestoneHelper.getStructureObject(connector.getIntegerField("division_id")));
        selection.setLabel((Label)MilestoneHelper.getStructureObject(connector.getIntegerField("label_id")));
        String streetDateString = connector.getFieldByName("street_date");
        if (streetDateString != null)
          selection.setStreetDate(MilestoneHelper.getDatabaseDate(streetDateString)); 
        String internationalDateString = connector.getFieldByName("international_date");
        if (internationalDateString != null)
          selection.setInternationalDate(MilestoneHelper.getDatabaseDate(internationalDateString)); 
        selection.setOtherContact(connector.getField("coordinator_contacts", ""));
        if (UserManager.getInstance().getUser(connector.getIntegerField("contact_id")) != null)
          selection.setLabelContact(UserManager.getInstance().getUser(connector.getIntegerField("contact_id"))); 
        selection.setSelectionStatus(
            (SelectionStatus)MilestoneHelper.getLookupObject(connector.getField("status"), Cache.getSelectionStatusList()));
        selection.setHoldSelection(connector.getBoolean("hold_indicator"));
        selection.setHoldReason(connector.getField("hold_reason", ""));
        selection.setComments(connector.getField("comments", ""));
        selection.setSpecialPackaging(connector.getBoolean("special_pkg_indicator"));
        selection.setNumberOfUnits(numberOfUnits);
        selection.setPressAndDistribution(connector.getBoolean("pd_indicator"));
        selection.setPrefixID((PrefixCode)MilestoneHelper.getLookupObject(connector.getField("prefix"), Cache.getPrefixCodes()));
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
        selection.setPlant(MilestoneHelper.getLookupObject(connector.getField("mfg.[plant]"), Cache.getVendors()));
        selection.setDistribution(MilestoneHelper.getLookupObject(connector.getField("mfg.[distribution]"), Cache.getDistributionCodes()));
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
        Vector precacheSchedule = new Vector();
        while (connector.more() && connector.getFieldByName("title_id").equalsIgnoreCase(titleId)) {
          ScheduledTask scheduledTask = null;
          scheduledTask = new ScheduledTask();
          scheduledTask.setReleaseID(connector.getIntegerField("release_Id"));
          scheduledTask.setSelectionNo(connector.getField("selection_no", ""));
          scheduledTask.setUpc(connector.getField("upc", ""));
          streetDateString = connector.getFieldByName("street_date");
          if (streetDateString != null)
            scheduledTask.setStreetDate(MilestoneHelper.getDatabaseDate(streetDateString)); 
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
}


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\TaskDueDateByTitleForPrintHandler.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */