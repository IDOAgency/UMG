package WEB-INF.classes.com.universal.milestone;

import com.techempower.ComponentLog;
import com.techempower.gemini.Context;
import com.techempower.gemini.GeminiApplication;
import com.universal.milestone.Form;
import com.universal.milestone.MilestoneHelper;
import com.universal.milestone.Schedule;
import com.universal.milestone.ScheduledTask;
import com.universal.milestone.SecureHandler;
import com.universal.milestone.Selection;
import com.universal.milestone.SelectionManager;
import com.universal.milestone.ToDoListReportForPrintSubHandler;
import inetsoft.report.XStyleSheet;
import inetsoft.report.lens.DefaultTableLens;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;
import javax.servlet.http.HttpServletResponse;

public class ToDoListReportForPrintSubHandler extends SecureHandler {
  public static final String COMPONENT_CODE = "hIga";
  
  public GeminiApplication application;
  
  public ComponentLog log;
  
  public ToDoListReportForPrintSubHandler(GeminiApplication application) {
    this.application = application;
    this.log = application.getLog("hIga");
  }
  
  public String getDescription() { return "Sub Report"; }
  
  protected static void fillEntToDoListForPrint(XStyleSheet report, Context context) {
    try {
      HttpServletResponse sresponse = context.getResponse();
      context.putDelivery("status", new String("start_gathering"));
      context.includeJSP("status.jsp", "hiddenFrame");
      sresponse.setContentType("text/plain");
      sresponse.flushBuffer();
    } catch (Exception exception) {}
    Vector selections = MilestoneHelper.getSelectionsForReport(context);
    Form reportForm = (Form)context.getSessionValue("reportForm");
    String strUmlKey = (reportForm.getStringValue("umlKeyTask") != null) ? reportForm.getStringValue("umlKeyTask") : "";
    try {
      HttpServletResponse sresponse = context.getResponse();
      context.putDelivery("status", new String("start_report"));
      context.putDelivery("percent", new String("10"));
      context.includeJSP("status.jsp", "hiddenFrame");
      sresponse.setContentType("text/plain");
      sresponse.flushBuffer();
    } catch (Exception exception) {}
    try {
      reportForm = (Form)context.getSessionValue("reportForm");
      Calendar beginStDate = (reportForm.getStringValue("beginDueDate") != null && 
        reportForm.getStringValue("beginDueDate").length() > 0) ? 
        MilestoneHelper.getDate(reportForm.getStringValue("beginDueDate")) : null;
      Calendar endStDate = (reportForm.getStringValue("endDueDate") != null && 
        reportForm.getStringValue("endDueDate").length() > 0) ? 
        MilestoneHelper.getDate(reportForm.getStringValue("endDueDate")) : null;
      report.setElement("crs_startdate", MilestoneHelper.getFormatedDate(beginStDate));
      report.setElement("crs_enddate", MilestoneHelper.getFormatedDate(endStDate));
      SimpleDateFormat formatter = new SimpleDateFormat("MMMM d, yyyy");
      String todayLong = formatter.format(new Date());
      report.setElement("crs_bottomdate", todayLong);
      if (beginStDate == null)
        beginStDate = MilestoneHelper.getDate("9/9/99"); 
      if (endStDate == null)
        endStDate = MilestoneHelper.getDate("9/9/09"); 
      int numSelections = selections.size() + selections.size() / 100 + 1;
      int numExtraRows = 0;
      for (int i = 0; i < selections.size(); i++) {
        Selection sel = (Selection)selections.elementAt(i);
        Schedule schedule = sel.getSchedule();
        Vector tasks = (schedule != null) ? schedule.getTasks() : null;
        ScheduledTask task = null;
        if (tasks != null)
          for (int j = 0; j < tasks.size(); j++) {
            task = (ScheduledTask)tasks.get(j);
            if (strUmlKey.equalsIgnoreCase("") || strUmlKey.trim().equalsIgnoreCase("all") || (
              strUmlKey.equalsIgnoreCase("UML") ? 
              !task.getOwner().getName().trim().equalsIgnoreCase("UML") : 
              
              task.getOwner().getName().trim().equalsIgnoreCase("UML")))
              numExtraRows++; 
          }  
      } 
      int numRows = numSelections;
      DefaultTableLens table_contents = new DefaultTableLens(numRows, 10);
      table_contents.setRowAutoSize(true);
      table_contents.setColBorder(0);
      table_contents.setRowBorder(0);
      table_contents.setRowBorderColor(Color.white);
      table_contents.setColWidth(0, 60);
      table_contents.setColWidth(1, 100);
      table_contents.setColWidth(2, 150);
      table_contents.setColWidth(3, 150);
      table_contents.setColWidth(4, 180);
      table_contents.setColWidth(5, 60);
      table_contents.setColWidth(6, 45);
      table_contents.setColWidth(7, 35);
      table_contents.setColWidth(8, 150);
      table_contents.setAlignment(0, 0, 33);
      table_contents.setAlignment(0, 1, 33);
      table_contents.setAlignment(0, 2, 33);
      table_contents.setAlignment(0, 3, 33);
      table_contents.setAlignment(0, 4, 33);
      table_contents.setAlignment(0, 5, 33);
      table_contents.setAlignment(0, 6, 33);
      table_contents.setAlignment(0, 7, 33);
      table_contents.setAlignment(0, 8, 33);
      table_contents.setHeaderRowCount(1);
      table_contents.setRowBorder(-1, 266240);
      table_contents.setRowBorder(0, 266240);
      table_contents.setRowBorderColor(0, Color.black);
      table_contents.setRowAlignment(0, 32);
      table_contents.setObject(0, 0, "Street \nDate");
      table_contents.setObject(0, 1, "Local \nProd #");
      table_contents.setObject(0, 2, "Artist");
      table_contents.setObject(0, 3, "Title");
      table_contents.setObject(0, 4, "Task Name/Comment");
      table_contents.setObject(0, 5, "Dept");
      table_contents.setObject(0, 6, "Task\nDate Due");
      table_contents.setSpan(0, 6, new Dimension(2, 1));
      table_contents.setObject(0, 8, "Label Contact");
      table_contents.setRowFont(0, new Font("Arial", 3, 11));
      int nextRow = 1;
      int count = 2;
      int numRec = selections.size();
      int chunkSize = numRec / 10;
      Color curColor = Color.white;
      for (int i = 0; i < selections.size(); i++) {
        try {
          int myPercent = i / chunkSize;
          if (myPercent > 1 && myPercent < 10)
            count = myPercent; 
          HttpServletResponse sresponse = context.getResponse();
          context.putDelivery("status", new String("start_report"));
          context.putDelivery("percent", new String(String.valueOf(count * 10)));
          context.includeJSP("status.jsp", "hiddenFrame");
          sresponse.setContentType("text/plain");
          sresponse.flushBuffer();
        } catch (Exception exception) {}
        Selection sel = (Selection)selections.elementAt(i);
        String selectionNo = SelectionManager.getLookupObjectValue(sel.getPrefixID());
        if (selectionNo == null)
          selectionNo = ""; 
        selectionNo = String.valueOf(selectionNo) + sel.getSelectionNo();
        String comment = (sel.getSelectionComments() != null) ? sel.getSelectionComments() : "";
        String streetDate = MilestoneHelper.getFormatedDate(sel.getStreetDate());
        String status = (sel.getSelectionStatus() != null && sel.getSelectionStatus().getName() != null) ? 
          sel.getSelectionStatus().getName() : "";
        if (status.equalsIgnoreCase("TBS")) {
          streetDate = "TBS " + streetDate;
        } else if (status.equalsIgnoreCase("In The Works")) {
          streetDate = "ITW " + streetDate;
        } 
        Schedule schedule = sel.getSchedule();
        if (curColor == Color.white) {
          table_contents.setRowBackground(nextRow, Color.lightGray);
          curColor = Color.gray;
        } else {
          table_contents.setRowBackground(nextRow, Color.white);
          curColor = Color.white;
        } 
        String dueDate = MilestoneHelper.getFormatedDate(sel.getDueDate());
        String dueDateHolidayFlg = MilestoneHelper.getDayType(sel.getCalendarGroup(), sel.getDueDate());
        if (sel.getTaskName().length() > 25 || sel.getArtist().length() > 25 || sel.getTitle().length() > 25)
          table_contents.setRowHeight(nextRow, 25); 
        String stdate_temp = (streetDate != null) ? streetDate : "";
        table_contents.setObject(nextRow, 0, stdate_temp);
        table_contents.setObject(nextRow, 2, sel.getArtist());
        table_contents.setObject(nextRow, 5, sel.getDepartment());
        table_contents.setObject(nextRow, 3, sel.getTitle());
        table_contents.setObject(nextRow, 1, selectionNo);
        table_contents.setObject(nextRow, 4, sel.getTaskName());
        table_contents.setObject(nextRow, 6, dueDate);
        table_contents.setObject(nextRow, 7, dueDateHolidayFlg);
        String name = "";
        name = (sel.getLabelContact() != null) ? sel.getLabelContact().getName() : "";
        table_contents.setObject(nextRow, 8, name);
        if (dueDateHolidayFlg != "") {
          Font holidayFont = new Font("Arial", 3, 8);
          table_contents.setFont(nextRow, 6, holidayFont);
          table_contents.setFont(nextRow, 7, holidayFont);
        } 
        table_contents.setAlignment(nextRow, 3, 1);
        table_contents.setRowBorder(nextRow, 4097);
        table_contents.setRowBorderColor(nextRow, Color.lightGray);
        table_contents.setAlignment(nextRow, 0, 9);
        table_contents.setAlignment(nextRow, 1, 9);
        table_contents.setAlignment(nextRow, 2, 9);
        table_contents.setAlignment(nextRow, 3, 9);
        table_contents.setAlignment(nextRow, 4, 8);
        table_contents.setAlignment(nextRow, 5, 9);
        table_contents.setAlignment(nextRow, 6, 9);
        table_contents.setAlignment(nextRow, 7, 9);
        table_contents.setAlignment(nextRow, 8, 9);
        nextRow++;
      } 
      report.setElement("table_colheaders", table_contents);
    } catch (Exception e) {
      System.out.println(">>>>>>>>ReportHandler.fillEntRelScheduleForPrint(): exception: " + e);
    } 
  }
}


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\ToDoListReportForPrintSubHandler.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */