package WEB-INF.classes.com.universal.milestone;

import com.techempower.ComponentLog;
import com.techempower.gemini.Context;
import com.techempower.gemini.GeminiApplication;
import com.universal.milestone.Form;
import com.universal.milestone.MilestoneHelper;
import com.universal.milestone.MilestoneHelper_2;
import com.universal.milestone.Schedule;
import com.universal.milestone.ScheduledTask;
import com.universal.milestone.SecureHandler;
import com.universal.milestone.Selection;
import com.universal.milestone.SelectionStatus;
import com.universal.milestone.TaskDueDateForPrintSubHandler;
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

public class TaskDueDateForPrintSubHandler extends SecureHandler {
  public static final String COMPONENT_CODE = "hCProd";
  
  public GeminiApplication application;
  
  public ComponentLog log;
  
  public TaskDueDateForPrintSubHandler(GeminiApplication application) {
    this.application = application;
    this.log = application.getLog("hCProd");
  }
  
  public String getDescription() { return "Sub Report"; }
  
  protected static StyleSheet fillTaskDueDateForPrint(Context context, String reportPath) {
    Color SHADED_AREA_COLOR = Color.lightGray;
    int COL_LINE_STYLE = 4097;
    int HEADER_FONT_SIZE = 12;
    int NUM_COLUMNS = 8;
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
    MilestoneHelper.setSelectionSorting(selections, 13);
    Collections.sort(selections);
    MilestoneHelper.setSelectionSorting(selections, 4);
    Collections.sort(selections);
    MilestoneHelper.setSelectionSorting(selections, 3);
    Collections.sort(selections);
    MilestoneHelper.setSelectionSorting(selections, 6);
    Collections.sort(selections);
    MilestoneHelper.setSelectionSorting(selections, 10);
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
        InputStream input = new FileInputStream(String.valueOf(reportPath) + "\\taskDueDate.xml");
        XStyleSheet report = (XStyleSheet)Builder.getBuilder(1, input).read(null);
        SectionBand hbandType = new SectionBand(report);
        SectionBand hbandCategory = new SectionBand(report);
        SectionBand hbandDate = new SectionBand(report);
        SectionBand body = new SectionBand(report);
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
          String selId = (sel.getTitleID() != null) ? sel.getTitleID() : "";
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
          report.setElement("artist", selArtist);
          report.setElement("title", selTitle);
          report.setElement("project", selProject);
          report.setElement("street_date", selStreetDate);
          report.setElement("company", selCompany);
          report.setElement("label", selLabel);
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
          table_contents = new DefaultTableLens(1, 8);
          table_contents.setColAlignment(0, 1);
          table_contents.setColAlignment(1, 1);
          table_contents.setColAlignment(2, 1);
          table_contents.setColAlignment(3, 1);
          table_contents.setColAlignment(4, 4);
          table_contents.setColAlignment(6, 12);
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
          table_contents.setColWidth(6, 56);
          table_contents.setColWidth(7, 1);
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
          table_contents.setObject(0, 6, "Complete");
          table_contents.setSpan(0, 6, new Dimension(2, 1));
          table_contents.setFont(0, 6, new Font("Arial", 1, 10));
          table_contents.setRowBorder(-1, 0);
          hbandType.addTable(table_contents, new Rectangle(0, 0, 800, 30));
          int nextRow = 0;
          headerTableLens = new DefaultTableLens(1, 8);
          headerTableLens.setColWidth(0, 5);
          headerTableLens.setSpan(0, 1, new Dimension(2, 1));
          headerTableLens.setColAlignment(0, 4);
          headerTableLens.setColWidth(1, 5);
          headerTableLens.setColWidth(2, 5);
          headerTableLens.setColWidth(3, 1);
          headerTableLens.setColAlignment(3, 4);
          headerTableLens.setSpan(0, 4, new Dimension(4, 1));
          headerTableLens.setColAlignment(4, 1);
          headerTableLens.setColWidth(4, 5);
          headerTableLens.setColWidth(5, 5);
          headerTableLens.setColWidth(6, 5);
          headerTableLens.setColWidth(7, 5);
          headerTableLens.setColBorder(-1, 4097);
          headerTableLens.setColBorder(0, 0);
          headerTableLens.setColBorder(1, 0);
          headerTableLens.setColBorder(2, 0);
          headerTableLens.setColBorder(3, 0);
          headerTableLens.setColBorder(4, 0);
          headerTableLens.setColBorder(5, 0);
          headerTableLens.setColBorderColor(Color.black);
          headerTableLens.setRowBorderColor(Color.black);
          headerTableLens.setRowBackground(0, Color.lightGray);
          headerTableLens.setFont(0, 0, new Font("Arial", 3, 10));
          headerTableLens.setFont(0, 1, new Font("Arial", 0, 10));
          headerTableLens.setFont(0, 3, new Font("Arial", 3, 10));
          headerTableLens.setFont(0, 4, new Font("Arial", 0, 10));
          headerTableLens.setObject(0, 0, "Selection ID:");
          headerTableLens.setObject(0, 1, selId);
          headerTableLens.setObject(0, 3, "UPC");
          headerTableLens.setObject(0, 4, selUpc);
          hbandType.addTable(headerTableLens, new Rectangle(0, 30, 800, 30));
          hbandType.setHeight(1.0F);
          for (int j = 0; j < scheduledTasks.size(); j++) {
            nextRow = 0;
            ScheduledTask task = (ScheduledTask)scheduledTasks.get(j);
            boolean taskHasComment = false;
            String taskComments = (task.getComments() != null) ? task.getComments().trim() : "";
            if (!taskComments.equals("") && !taskComments.equalsIgnoreCase("null"))
              taskHasComment = true; 
            if (taskHasComment) {
              subTable = new DefaultTableLens(2, 8);
            } else {
              subTable = new DefaultTableLens(1, 8);
            } 
            subTable.setColAlignment(0, 1);
            subTable.setColAlignment(1, 1);
            subTable.setColAlignment(2, 1);
            subTable.setColAlignment(3, 1);
            subTable.setColAlignment(4, 4);
            subTable.setColAlignment(6, 12);
            subTable.setColWidth(0, 35);
            subTable.setLineWrap(0, 0, false);
            subTable.setLineWrap(0, 1, false);
            subTable.setColWidth(1, 82);
            subTable.setColWidth(2, 55);
            subTable.setColWidth(3, 45);
            subTable.setColWidth(4, 45);
            subTable.setColWidth(5, 1);
            subTable.setColWidth(6, 56);
            subTable.setColWidth(7, 1);
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
            String taskDueDate = (task.getDueDate() != null) ? 
              MilestoneHelper.getFormatedDate(task.getDueDate()) : "";
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
            subTable.setObject(nextRow, 6, taskCompletionDate);
            subTable.setRowFont(nextRow, new Font("Arial", 0, 9));
            if (taskHasComment) {
              nextRow++;
              subTable.setSpan(nextRow, 0, new Dimension(2, 1));
              subTable.setObject(nextRow, 0, taskComments);
              subTable.setRowFont(nextRow, new Font("Arial", 2, 9));
              subTable.setRowBorder(nextRow - 1, 0);
            } 
            subTable.setRowBorder(nextRow, tableRowLineStyle);
            subTable.setRowBorderColor(nextRow, tableRowLineColor);
            body = new SectionBand(report);
            double lfLineCount = 1.5D;
            body.setHeight(1.5F);
            body.addTable(subTable, new Rectangle(800, 800));
            body.setBottomBorder(0);
            body.setTopBorder(0);
            body.setShrinkToFit(true);
            body.setVisible(true);
            group = new DefaultSectionLens(null, group, body);
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
}


/* Location:              D:\Documents\NetBeansProjects\milestone2 Local.war!\WEB-INF\classes\co\\universal\milestone\TaskDueDateForPrintSubHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */