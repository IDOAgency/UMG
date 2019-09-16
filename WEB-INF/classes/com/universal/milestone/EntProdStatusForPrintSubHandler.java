package WEB-INF.classes.com.universal.milestone;

import com.techempower.ComponentLog;
import com.techempower.gemini.Context;
import com.techempower.gemini.GeminiApplication;
import com.universal.milestone.EntProdStatusForPrintSubHandler;
import com.universal.milestone.Form;
import com.universal.milestone.MilestoneHelper;
import com.universal.milestone.MilestoneHelper_2;
import com.universal.milestone.ReleasingFamily;
import com.universal.milestone.Schedule;
import com.universal.milestone.ScheduledTask;
import com.universal.milestone.SecureHandler;
import com.universal.milestone.Selection;
import com.universal.milestone.SelectionManager;
import com.universal.milestone.SelectionStatus;
import inetsoft.report.CompositeSheet;
import inetsoft.report.SeparatorElement;
import inetsoft.report.StyleSheet;
import inetsoft.report.XStyleSheet;
import inetsoft.report.io.Builder;
import inetsoft.report.lens.DefaultTableLens;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Vector;
import javax.servlet.http.HttpServletResponse;

public class EntProdStatusForPrintSubHandler extends SecureHandler {
  public static final String COMPONENT_CODE = "hEntProd";
  
  public GeminiApplication application;
  
  public ComponentLog log;
  
  public EntProdStatusForPrintSubHandler(GeminiApplication application) {
    this.application = application;
    this.log = application.getLog("hEntProd");
  }
  
  public String getDescription() { return "Sub Report"; }
  
  protected static StyleSheet fillEntProdStatusForPrint(Context context, String reportPath) {
    int separatorLineStyle = 266240;
    Color separatorLineColor = Color.black;
    int tableHeaderLineStyle = 266240;
    Color tableHeaderLineColor = Color.black;
    int tableRowLineStyle = 4097;
    try {
      HttpServletResponse sresponse = context.getResponse();
      context.putDelivery("status", new String("start_gathering"));
      context.includeJSP("status.jsp", "hiddenFrame");
      sresponse.setContentType("text/plain");
      sresponse.flushBuffer();
    } catch (Exception exception) {}
    Vector selections = MilestoneHelper.getSelectionsForReport(context);
    try {
      HttpServletResponse sresponse = context.getResponse();
      context.putDelivery("status", new String("start_report"));
      context.putDelivery("percent", new String("10"));
      context.includeJSP("status.jsp", "hiddenFrame");
      sresponse.setContentType("text/plain");
      sresponse.flushBuffer();
    } catch (Exception exception) {}
    int numSelections = selections.size();
    MilestoneHelper.setSelectionSorting(selections, 12);
    Collections.sort(selections);
    MilestoneHelper.setSelectionSorting(selections, 4);
    Collections.sort(selections);
    MilestoneHelper.setSelectionSorting(selections, 3);
    Collections.sort(selections);
    MilestoneHelper.setSelectionSorting(selections, 9);
    Collections.sort(selections);
    MilestoneHelper.setSelectionSorting(selections, 7);
    Collections.sort(selections);
    MilestoneHelper.setSelectionSorting(selections, 8);
    Collections.sort(selections);
    MilestoneHelper.setSelectionSorting(selections, 6);
    Collections.sort(selections);
    MilestoneHelper.setSelectionSorting(selections, 1);
    Collections.sort(selections);
    MilestoneHelper.setSelectionSorting(selections, 10);
    Collections.sort(selections);
    if (numSelections == 0)
      return null; 
    StyleSheet[] sheets = new StyleSheet[numSelections];
    try {
      for (int i = 0; i < numSelections; i++) {
        InputStream input = new FileInputStream(String.valueOf(reportPath) + "\\ent_prod_status.xml");
        XStyleSheet report = (XStyleSheet)Builder.getBuilder(1, input).read(null);
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
        Calendar beginStDate = (reportForm.getStringValue("beginDate") != null && 
          reportForm.getStringValue("beginDate").length() > 0) ? 
          MilestoneHelper.getDate(reportForm.getStringValue("beginDate")) : null;
        Calendar endStDate = (reportForm.getStringValue("endDate") != null && 
          reportForm.getStringValue("endDate").length() > 0) ? 
          MilestoneHelper.getDate(reportForm.getStringValue("endDate")) : null;
        report.setElement("startdate", MilestoneHelper.getFormatedDate(beginStDate));
        report.setElement("enddate", MilestoneHelper.getFormatedDate(endStDate));
        SimpleDateFormat formatter = new SimpleDateFormat("MMMM d, yyyy");
        String todayLong = formatter.format(new Date());
        report.setElement("bottomdate", todayLong);
        String strUmlKey = (reportForm.getStringValue("umlKeyTask") != null) ? reportForm.getStringValue("umlKeyTask") : "";
        Selection sel = (Selection)selections.elementAt(i);
        SelectionManager.getInstance().getSelectionManufacturingSubDetail(sel);
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
          String selLabel = sel.getImprint();
          String selReleasingFamily = ReleasingFamily.getName(sel.getReleaseFamilyId());
          String selId = "";
          if (SelectionManager.getLookupObjectValue(sel.getPrefixID()).equals("")) {
            selId = sel.getSelectionNo();
          } else {
            selId = String.valueOf(SelectionManager.getLookupObjectValue(sel.getPrefixID())) + ' ' + sel.getSelectionNo();
          } 
          String selUpc = (sel.getUpc() != null) ? sel.getUpc() : "";
          selUpc = MilestoneHelper_2.getRMSReportFormat(selUpc, "UPC", sel.getIsDigital());
          String selConfig = (sel.getSelectionSubConfig() != null && sel.getSelectionSubConfig().getSelectionSubConfigurationAbbreviation() != null) ? 
            sel.getSelectionSubConfig().getSelectionSubConfigurationAbbreviation() : "";
          String selTerritory = (sel.getSelectionTerritory() != null) ? sel.getSelectionTerritory() : "";
          String selStreetDate = "";
          selStreetDate = MilestoneHelper.getFormatedDate(sel.getStreetDate());
          String statusString = "";
          SelectionStatus status = sel.getSelectionStatus();
          if (status != null)
            statusString = (status.getName() == null) ? "" : status.getName(); 
          if (statusString.equalsIgnoreCase("TBS")) {
            selStreetDate = String.valueOf(statusString) + " " + selStreetDate;
          } else if (statusString.equalsIgnoreCase("In The Works")) {
            selStreetDate = "ITW " + selStreetDate;
          } 
          if (selStreetDate.equals(""))
            selStreetDate = "No Street Date"; 
          String selArtist = (sel.getArtist() != null) ? sel.getArtist() : "";
          String selTitle = (sel.getTitle() != null) ? sel.getTitle() : "";
          report.setElement("company", selCompany);
          report.setElement("project", selProject);
          report.setElement("p_and_d", selPD);
          report.setElement("label", String.valueOf(selReleasingFamily) + "/" + selLabel);
          report.setElement("selection_id", selId);
          report.setElement("upc", selUpc);
          report.setElement("config", selConfig);
          report.setElement("street_date", selStreetDate);
          report.setElement("artist", selArtist);
          report.setElement("title", selTitle);
          report.setElement("territory", selTerritory);
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
          DefaultTableLens table_contents = new DefaultTableLens(scheduledTasks.size() + commentCount + 1, 9);
          table_contents.setColAlignment(0, 1);
          table_contents.setColAlignment(1, 1);
          table_contents.setColAlignment(2, 2);
          table_contents.setColAlignment(3, 2);
          table_contents.setColAlignment(4, 4);
          table_contents.setColAlignment(6, 1);
          table_contents.setColAlignment(7, 12);
          table_contents.setColBorderColor(Color.black);
          table_contents.setRowBorderColor(Color.black);
          table_contents.setRowBorder(4097);
          table_contents.setColBorder(4097);
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
          table_contents.setAlignment(0, 2, 1);
          table_contents.setFont(0, 2, new Font("Arial", 1, 10));
          table_contents.setObject(0, 3, "Wks To Rel");
          table_contents.setFont(0, 3, new Font("Arial", 1, 10));
          table_contents.setObject(0, 4, "Date Due");
          table_contents.setFont(0, 4, new Font("Arial", 1, 10));
          table_contents.setSpan(0, 4, new Dimension(2, 1));
          table_contents.setObject(0, 7, "Complete");
          table_contents.setSpan(0, 7, new Dimension(2, 1));
          table_contents.setFont(0, 7, new Font("Arial", 1, 10));
          table_contents.setRowBorder(-1, 0);
          table_contents.setRowBorder(0, tableHeaderLineStyle);
          table_contents.setRowBorderColor(0, tableHeaderLineColor);
          int nextRow = 1;
          boolean shade = true;
          for (int j = 0; j < scheduledTasks.size(); j++) {
            String dueDateHolidayFlg, taskDueDate;
            ScheduledTask task = (ScheduledTask)scheduledTasks.get(j);
            boolean taskHasComment = false;
            String taskName = (task.getName() != null) ? task.getName().trim() : "";
            String taskComments = (task.getComments() != null) ? task.getComments().trim() : "";
            String taskOwner = (task.getOwner() != null && task.getOwner().getName() != null) ? 
              task.getOwner().getName() : "";
            if (!taskComments.equals("") && !taskComments.equalsIgnoreCase("null"))
              taskHasComment = true; 
            String taskDepartment = (task.getDepartment() != null && !task.getDepartment().equalsIgnoreCase("-1") && !task.getDepartment().equalsIgnoreCase("null")) ? 
              task.getDepartment() : "";
            String wksToReleaseString = "";
            if (task.getWeeksToRelease() > 0) {
              wksToReleaseString = (task.getDayOfTheWeek() != null) ? task.getDayOfTheWeek().getDay() : "";
              wksToReleaseString = String.valueOf(wksToReleaseString) + " " + task.getWeeksToRelease();
            } 
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
            table_contents.setObject(nextRow, 0, taskName);
            table_contents.setSpan(nextRow, 0, new Dimension(2, 1));
            table_contents.setObject(nextRow, 2, taskOwner);
            table_contents.setAlignment(nextRow, 2, 1);
            table_contents.setObject(nextRow, 3, wksToReleaseString);
            table_contents.setObject(nextRow, 4, taskDueDate);
            table_contents.setObject(nextRow, 6, dueDateHolidayFlg);
            table_contents.setObject(nextRow, 7, taskCompletionDate);
            if (!shade) {
              table_contents.setFont(nextRow, 1, new Font("Arial", 0, 8));
            } else {
              table_contents.setFont(nextRow, 1, new Font("Arial", 1, 8));
              table_contents.setRowBackground(nextRow, Color.lightGray);
            } 
            table_contents.setRowFont(nextRow, new Font("Arial", 0, 9));
            if (dueDateHolidayFlg != "") {
              Font holidayFont = new Font("Arial", 3, 9);
              table_contents.setFont(nextRow, 4, holidayFont);
              table_contents.setFont(nextRow, 6, holidayFont);
            } 
            if (taskHasComment) {
              nextRow++;
              table_contents.setSpan(nextRow, 0, new Dimension(2, 1));
              table_contents.setObject(nextRow, 0, taskComments);
              table_contents.setRowFont(nextRow, new Font("Arial", 2, 9));
              table_contents.setRowBorder(nextRow - 1, 0);
              if (!shade) {
                table_contents.setFont(nextRow, 1, new Font("Arial", 0, 8));
              } else {
                table_contents.setFont(nextRow, 1, new Font("Arial", 1, 8));
                table_contents.setRowBackground(nextRow, Color.lightGray);
              } 
            } 
            table_contents.setRowBorder(nextRow, tableRowLineStyle);
            table_contents.setRowBorderColor(nextRow, Color.white);
            nextRow++;
            shade = !shade;
          } 
          report.setElement("table", table_contents);
        } 
        sheets[i] = report;
      } 
    } catch (Exception e) {
      System.out.println(">>>>>>>>ReportHandler.fillEntProdStatusForPrint(): exception: " + e);
    } 
    return new CompositeSheet(sheets);
  }
}


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\EntProdStatusForPrintSubHandler.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */