package WEB-INF.classes.com.universal.milestone;

import com.techempower.ComponentLog;
import com.techempower.gemini.Context;
import com.techempower.gemini.GeminiApplication;
import com.universal.milestone.Form;
import com.universal.milestone.MCANewReleasePlanningSchedulePrintSubHandler;
import com.universal.milestone.MilestoneHelper;
import com.universal.milestone.MonthYearComparator;
import com.universal.milestone.MultOtherContact;
import com.universal.milestone.Schedule;
import com.universal.milestone.ScheduledTask;
import com.universal.milestone.SecureHandler;
import com.universal.milestone.Selection;
import inetsoft.report.SectionBand;
import inetsoft.report.XStyleSheet;
import inetsoft.report.lens.DefaultSectionLens;
import inetsoft.report.lens.DefaultTableLens;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.Rectangle;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;
import javax.servlet.http.HttpServletResponse;

public class MCANewReleasePlanningSchedulePrintSubHandler extends SecureHandler {
  public static final String COMPONENT_CODE = "hMCAProd";
  
  public GeminiApplication application;
  
  public ComponentLog log;
  
  public void MCANewReleasePlanningSchedulePrintSubHandler(GeminiApplication application) {
    this.application = application;
    this.log = application.getLog("hMCAProd");
  }
  
  public String getDescription() { return "Sub Report"; }
  
  protected static void fillMCANewReleasePlanningScheduleForPrint(XStyleSheet report, Context context) {
    int COL_LINE_STYLE = 4097;
    int HEADER_FONT_SIZE = 12;
    double ldLineVal = 0.3D;
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
    int DATA_FONT_SIZE = 7;
    int SMALL_HEADER_FONT_SIZE = 8;
    int NUM_COLUMNS = 24;
    Color SHADED_AREA_COLOR = Color.lightGray;
    SectionBand hbandType = new SectionBand(report);
    SectionBand hbandCategory = new SectionBand(report);
    SectionBand hbandDate = new SectionBand(report);
    SectionBand hbandRelWeek = new SectionBand(report);
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
    Hashtable selTable = MilestoneHelper.groupSelectionsForMcaByTypeConfigAndStreetDate(selections);
    Enumeration configs = selTable.keys();
    Vector configVector = new Vector();
    while (configs.hasMoreElements())
      configVector.addElement(configs.nextElement()); 
    int numConfigs = configVector.size();
    try {
      Collections.sort(configVector);
      Vector sortedConfigVector = MilestoneHelper.sortStrings(configVector);
      DefaultTableLens table_contents = null;
      DefaultTableLens rowCountTable = null;
      DefaultTableLens columnHeaderTable = null;
      DefaultTableLens subTable = null;
      DefaultTableLens monthTableLens = null;
      DefaultTableLens dateTableLens = null;
      DefaultTableLens relWeekLens = null;
      rowCountTable = new DefaultTableLens(2, 10000);
      int totalCount = 0;
      int tenth = 1;
      for (int n = 0; n < sortedConfigVector.size(); n++) {
        String monthName = (sortedConfigVector.elementAt(n) != null) ? (String)sortedConfigVector.elementAt(n) : "";
        Vector selectionsForMonth = (Vector)selTable.get(monthName);
        totalCount += selectionsForMonth.size();
      } 
      tenth = (totalCount > 10) ? (totalCount / 10) : 1;
      HttpServletResponse sresponse = context.getResponse();
      context.putDelivery("status", new String("start_report"));
      context.putDelivery("percent", new String("20"));
      context.includeJSP("status.jsp", "hiddenFrame");
      sresponse.setContentType("text/plain");
      sresponse.flushBuffer();
      int recordCount = 0;
      int count = 0;
      Object[] monthArray = (Object[])null;
      monthArray = sortedConfigVector.toArray();
      Arrays.sort(monthArray, new MonthYearComparator());
      for (int x = 0; x < monthArray.length; x++) {
        Form reportForm = (Form)context.getSessionValue("reportForm");
        Calendar beginStDate = (reportForm.getStringValue("beginDate") != null && 
          reportForm.getStringValue("beginDate").length() > 0) ? 
          MilestoneHelper.getDate(reportForm.getStringValue("beginDate")) : null;
        Calendar endStDate = (reportForm.getStringValue("endDate") != null && 
          reportForm.getStringValue("endDate").length() > 0) ? 
          MilestoneHelper.getDate(reportForm.getStringValue("endDate")) : null;
        report.setElement("crs_startdate", MilestoneHelper.getFormatedDate(beginStDate));
        report.setElement("crs_enddate", MilestoneHelper.getFormatedDate(endStDate));
        SimpleDateFormat formatter = new SimpleDateFormat("MMMM d, yyyy, h:mm a");
        String todayLong = formatter.format(new Date());
        report.setElement("crs_bottomdate", todayLong);
        String monthName = (String)monthArray[x];
        Vector selectionsVector = (Vector)selTable.get(monthName);
        String previousReleaseWeek = "";
        boolean newCycle = false;
        int numMonths = 0;
        int numDates = 0;
        int numSelections = 0;
        if (selectionsVector != null) {
          numMonths++;
          numSelections += selectionsVector.size();
        } 
        int numRows = 0;
        numRows += numMonths * 3;
        numRows += numDates * 2;
        numRows += numSelections * 2;
        numRows += 5;
        hbandType = new SectionBand(report);
        hbandType.setHeight(2.5F);
        hbandType.setShrinkToFit(true);
        hbandType.setVisible(true);
        int nextRow = 0;
        columnHeaderTable = new DefaultTableLens(2, 24);
        columnHeaderTable.setHeaderRowCount(1);
        columnHeaderTable.setColBorderColor(Color.lightGray);
        columnHeaderTable.setColWidth(0, 100);
        columnHeaderTable.setColWidth(1, 100);
        columnHeaderTable.setColWidth(2, 130);
        columnHeaderTable.setColWidth(3, 130);
        columnHeaderTable.setColWidth(4, 130);
        columnHeaderTable.setColWidth(5, 80);
        columnHeaderTable.setColWidth(6, 90);
        columnHeaderTable.setColWidth(7, 80);
        columnHeaderTable.setColWidth(8, 100);
        columnHeaderTable.setColWidth(9, 80);
        columnHeaderTable.setColWidth(10, 100);
        columnHeaderTable.setColWidth(11, 60);
        columnHeaderTable.setColWidth(12, 65);
        columnHeaderTable.setColWidth(13, 80);
        columnHeaderTable.setColWidth(14, 80);
        columnHeaderTable.setColWidth(15, 60);
        columnHeaderTable.setColWidth(16, 70);
        columnHeaderTable.setColWidth(17, 80);
        columnHeaderTable.setColWidth(18, 70);
        columnHeaderTable.setColWidth(19, 70);
        columnHeaderTable.setColWidth(20, 75);
        columnHeaderTable.setColWidth(21, 60);
        columnHeaderTable.setColWidth(22, 100);
        columnHeaderTable.setColWidth(23, 60);
        columnHeaderTable.setAlignment(nextRow, 0, 33);
        columnHeaderTable.setAlignment(nextRow, 1, 34);
        columnHeaderTable.setAlignment(nextRow, 2, 34);
        columnHeaderTable.setAlignment(nextRow, 3, 34);
        columnHeaderTable.setAlignment(nextRow, 4, 34);
        columnHeaderTable.setAlignment(nextRow, 5, 34);
        columnHeaderTable.setAlignment(nextRow, 6, 34);
        columnHeaderTable.setAlignment(nextRow, 7, 34);
        columnHeaderTable.setAlignment(nextRow, 8, 34);
        columnHeaderTable.setAlignment(nextRow, 9, 34);
        columnHeaderTable.setAlignment(nextRow, 10, 34);
        columnHeaderTable.setAlignment(nextRow, 11, 34);
        columnHeaderTable.setAlignment(nextRow, 12, 34);
        columnHeaderTable.setAlignment(nextRow, 13, 34);
        columnHeaderTable.setAlignment(nextRow, 14, 34);
        columnHeaderTable.setAlignment(nextRow, 15, 34);
        columnHeaderTable.setAlignment(nextRow, 16, 34);
        columnHeaderTable.setAlignment(nextRow, 17, 34);
        columnHeaderTable.setAlignment(nextRow, 18, 34);
        columnHeaderTable.setAlignment(nextRow, 19, 34);
        columnHeaderTable.setAlignment(nextRow, 20, 34);
        columnHeaderTable.setAlignment(nextRow, 21, 34);
        columnHeaderTable.setAlignment(nextRow, 22, 34);
        columnHeaderTable.setAlignment(nextRow, 23, 34);
        columnHeaderTable.setRowFont(nextRow, new Font("Arial", 0, 6));
        columnHeaderTable.setObject(nextRow, 0, "Project/\nAlbum Title");
        columnHeaderTable.setObject(nextRow, 1, "Street Date");
        columnHeaderTable.setObject(nextRow, 2, "Marketing\nDirector");
        columnHeaderTable.setObject(nextRow, 3, "Art Director");
        columnHeaderTable.setObject(nextRow, 4, "A&R Contact");
        columnHeaderTable.setObject(nextRow, 5, "Image meeting");
        columnHeaderTable.setObject(nextRow, 6, "photoshoot");
        columnHeaderTable.setObject(nextRow, 7, "pick slot");
        columnHeaderTable.setObject(nextRow, 8, "cd pro\n cover approved");
        columnHeaderTable.setObject(nextRow, 9, "planning mtg");
        columnHeaderTable.setObject(nextRow, 10, "album cover approved");
        columnHeaderTable.setObject(nextRow, 11, "cd pro in house");
        columnHeaderTable.setObject(nextRow, 12, "cdr's to publicity");
        columnHeaderTable.setObject(nextRow, 13, "solicitation content due");
        columnHeaderTable.setObject(nextRow, 14, "send Marketing Brief");
        columnHeaderTable.setObject(nextRow, 15, "completed credits\nto Creative");
        columnHeaderTable.setObject(nextRow, 16, "impact date");
        columnHeaderTable.setObject(nextRow, 17, "UMVD advances in house");
        columnHeaderTable.setObject(nextRow, 18, "Mechanical\nbegins\nrouting");
        columnHeaderTable.setObject(nextRow, 19, "Comm. Single in stores");
        columnHeaderTable.setObject(nextRow, 20, "send Marketing Plan");
        columnHeaderTable.setObject(nextRow, 21, "Sticker copy due\nto Production");
        columnHeaderTable.setObject(nextRow, 22, "Mechanical\nto\nSeparations");
        columnHeaderTable.setObject(nextRow, 23, "Package film\nships to\nPrinter");
        columnHeaderTable.setRowBorderColor(nextRow - 1, SHADED_AREA_COLOR);
        columnHeaderTable.setRowBorderColor(nextRow, SHADED_AREA_COLOR);
        columnHeaderTable.setRowBackground(nextRow, Color.white);
        columnHeaderTable.setRowForeground(nextRow, Color.black);
        nextRow = 1;
        columnHeaderTable.setRowBorderColor(nextRow - 1, SHADED_AREA_COLOR);
        columnHeaderTable.setRowBorderColor(nextRow, SHADED_AREA_COLOR);
        columnHeaderTable.setRowBackground(nextRow, Color.white);
        columnHeaderTable.setRowForeground(nextRow, Color.black);
        columnHeaderTable.setRowFont(nextRow, new Font("Arial", 0, 6));
        columnHeaderTable.setSpan(nextRow, 0, new Dimension(5, 1));
        columnHeaderTable.setObject(nextRow, 0, "Weeks Prior to Release:\nDay of Week:");
        columnHeaderTable.setObject(nextRow, 5, "30\nMonday");
        columnHeaderTable.setObject(nextRow, 6, "26\nFriday");
        columnHeaderTable.setObject(nextRow, 7, "24\nTuesday");
        columnHeaderTable.setObject(nextRow, 8, "19\nFriday");
        columnHeaderTable.setObject(nextRow, 9, "18\nThursday");
        columnHeaderTable.setObject(nextRow, 10, "16\nFriday");
        columnHeaderTable.setObject(nextRow, 11, "14\nMonday");
        columnHeaderTable.setObject(nextRow, 12, "13\nMonday");
        columnHeaderTable.setObject(nextRow, 13, "12\nMonday");
        columnHeaderTable.setObject(nextRow, 14, "12\nMonday");
        columnHeaderTable.setObject(nextRow, 15, "11\nFriday");
        columnHeaderTable.setObject(nextRow, 16, "10\nTuesday");
        columnHeaderTable.setObject(nextRow, 17, "9\nMonday");
        columnHeaderTable.setObject(nextRow, 18, "8\nFriday");
        columnHeaderTable.setObject(nextRow, 19, "8\nTuesday");
        columnHeaderTable.setObject(nextRow, 20, "8\nTuesday");
        columnHeaderTable.setObject(nextRow, 21, "8\nFriday");
        columnHeaderTable.setObject(nextRow, 22, "6\nTuesday");
        columnHeaderTable.setObject(nextRow, 23, "6\nFriday");
        columnHeaderTable.setRowInsets(nextRow, new Insets(0, 0, 0, 0));
        columnHeaderTable.setRowHeight(nextRow, 20);
        columnHeaderTable.setAlignment(nextRow, 0, 36);
        columnHeaderTable.setAlignment(nextRow, 1, 34);
        columnHeaderTable.setAlignment(nextRow, 2, 34);
        columnHeaderTable.setAlignment(nextRow, 3, 34);
        columnHeaderTable.setAlignment(nextRow, 4, 34);
        columnHeaderTable.setAlignment(nextRow, 5, 34);
        columnHeaderTable.setAlignment(nextRow, 6, 34);
        columnHeaderTable.setAlignment(nextRow, 7, 34);
        columnHeaderTable.setAlignment(nextRow, 8, 34);
        columnHeaderTable.setAlignment(nextRow, 9, 34);
        columnHeaderTable.setAlignment(nextRow, 10, 34);
        columnHeaderTable.setAlignment(nextRow, 11, 34);
        columnHeaderTable.setAlignment(nextRow, 12, 34);
        columnHeaderTable.setAlignment(nextRow, 13, 34);
        columnHeaderTable.setAlignment(nextRow, 14, 34);
        columnHeaderTable.setAlignment(nextRow, 15, 34);
        columnHeaderTable.setAlignment(nextRow, 16, 34);
        columnHeaderTable.setAlignment(nextRow, 17, 34);
        columnHeaderTable.setAlignment(nextRow, 18, 34);
        columnHeaderTable.setAlignment(nextRow, 19, 34);
        columnHeaderTable.setAlignment(nextRow, 20, 34);
        columnHeaderTable.setAlignment(nextRow, 21, 34);
        columnHeaderTable.setAlignment(nextRow, 22, 34);
        columnHeaderTable.setAlignment(nextRow, 23, 34);
        hbandType.addTable(columnHeaderTable, new Rectangle(0, 24, 800, 150));
        hbandType.setBottomBorder(0);
        if (selTable != null) {
          monthName = (String)monthArray[x];
          String monthNameString = monthName;
          try {
            monthNameString = String.valueOf(MONTHS[Integer.parseInt(monthName.substring(0, 2)) - 1]) + " " + Integer.parseInt(monthName.substring(3, 7));
          } catch (Exception e) {
            monthNameString = "No street date";
          } 
          monthTableLens = new DefaultTableLens(1, 24);
          hbandCategory = new SectionBand(report);
          hbandCategory.setHeight(0.25F);
          hbandCategory.setShrinkToFit(true);
          hbandCategory.setVisible(true);
          hbandCategory.setBottomBorder(0);
          hbandCategory.setLeftBorder(0);
          hbandCategory.setRightBorder(0);
          hbandCategory.setTopBorder(0);
          nextRow = 0;
          monthTableLens.setRowFont(nextRow, new Font("Arial", 1, 9));
          monthTableLens.setColBorderColor(SHADED_AREA_COLOR);
          monthTableLens.setRowHeight(nextRow, 14);
          monthTableLens.setBackground(0, 0, SHADED_AREA_COLOR);
          monthTableLens.setBackground(0, 1, SHADED_AREA_COLOR);
          monthTableLens.setSpan(nextRow, 0, new Dimension(2, 1));
          monthTableLens.setObject(nextRow, 0, monthNameString);
          monthTableLens.setRowForeground(nextRow, Color.black);
          monthTableLens.setColWidth(0, 100);
          monthTableLens.setColWidth(1, 100);
          monthTableLens.setColWidth(2, 130);
          monthTableLens.setColWidth(3, 130);
          monthTableLens.setColWidth(4, 130);
          monthTableLens.setColWidth(5, 80);
          monthTableLens.setColWidth(6, 90);
          monthTableLens.setColWidth(7, 80);
          monthTableLens.setColWidth(8, 100);
          monthTableLens.setColWidth(9, 80);
          monthTableLens.setColWidth(10, 100);
          monthTableLens.setColWidth(11, 60);
          monthTableLens.setColWidth(12, 65);
          monthTableLens.setColWidth(13, 80);
          monthTableLens.setColWidth(14, 80);
          monthTableLens.setColWidth(15, 60);
          monthTableLens.setColWidth(16, 70);
          monthTableLens.setColWidth(17, 80);
          monthTableLens.setColWidth(18, 70);
          monthTableLens.setColWidth(19, 70);
          monthTableLens.setColWidth(20, 75);
          monthTableLens.setColWidth(21, 60);
          monthTableLens.setColWidth(22, 100);
          monthTableLens.setColWidth(23, 60);
          monthTableLens.setSpan(nextRow, 2, new Dimension(22, 1));
          monthTableLens.setBackground(0, 2, SHADED_AREA_COLOR);
          monthTableLens.setRowBorderColor(nextRow, SHADED_AREA_COLOR);
          monthTableLens.setRowBorderColor(nextRow - 1, SHADED_AREA_COLOR);
          monthTableLens.setColBorderColor(nextRow, -1, SHADED_AREA_COLOR);
          monthTableLens.setColBorderColor(nextRow, 0, SHADED_AREA_COLOR);
          monthTableLens.setColBorderColor(nextRow, 14, SHADED_AREA_COLOR);
          monthTableLens.setRowBorderColor(nextRow + 1, SHADED_AREA_COLOR);
          hbandCategory.addTable(monthTableLens, new Rectangle(800, 800));
          footer.setVisible(true);
          footer.setHeight(0.1F);
          footer.setShrinkToFit(false);
          footer.setBottomBorder(0);
          group = new DefaultSectionLens(null, group, spacer);
          group = new DefaultSectionLens(null, group, hbandCategory);
          group = new DefaultSectionLens(null, group, spacer);
          selections = (Vector)selTable.get(monthName);
          MilestoneHelper.setSelectionSorting(selections, 12);
          Collections.sort(selections);
          MilestoneHelper.setSelectionSorting(selections, 14);
          Collections.sort(selections);
          MilestoneHelper.setSelectionSorting(selections, 4);
          Collections.sort(selections);
          MilestoneHelper.setSelectionSorting(selections, 3);
          Collections.sort(selections);
          MilestoneHelper.setSelectionSorting(selections, 1);
          Collections.sort(selections);
          for (int i = 0; i < selections.size(); i++) {
            Selection sel = (Selection)selections.elementAt(i);
            if (count < recordCount / tenth) {
              count = recordCount / tenth;
              sresponse = context.getResponse();
              context.putDelivery("status", new String("start_report"));
              int myPercent = count * 10;
              if (myPercent > 90)
                myPercent = 90; 
              context.putDelivery("percent", new String(String.valueOf(myPercent)));
              context.includeJSP("status.jsp", "hiddenFrame");
              sresponse.setContentType("text/plain");
              sresponse.flushBuffer();
            } 
            recordCount++;
            String dateNameText = MilestoneHelper.getFormatedDate(sel.getStreetDate());
            String titleId = "";
            titleId = sel.getTitleID();
            String filler = "";
            String artist = "";
            artist = sel.getArtist().trim();
            String title = "";
            if (sel.getTitle() != null)
              title = sel.getTitle(); 
            Schedule schedule = sel.getSchedule();
            Vector tasks = (schedule != null) ? schedule.getTasks() : null;
            ScheduledTask task = null;
            String IMG = "";
            String PHO = "";
            String SLT = "";
            String PRO = "";
            String PLN = "";
            String ALB = "";
            String PIH = "";
            String CRDA = "";
            String CDRP = "";
            String CNT = "";
            String MKB = "";
            String IMP = "";
            String ADIH = "";
            String MBR = "";
            String SGL = "";
            String MKP = "";
            String STD = "";
            String PFS = "";
            String SEPS = "";
            String IMGcom = "";
            String PHOcom = "";
            String SLTcom = "";
            String PROcom = "";
            String PLNcom = "";
            String ALBcom = "";
            String PIHcom = "";
            String CRDAcom = "";
            String CDRPcom = "";
            String CNTcom = "";
            String MKBcom = "";
            String IMPcom = "";
            String ADIHcom = "";
            String MBRcom = "";
            String SGLcom = "";
            String MKPcom = "";
            String STDcom = "";
            String PFScom = "";
            String SEPScom = "";
            String taskVendor = "";
            if (tasks != null)
              for (int j = 0; j < tasks.size(); j++) {
                task = (ScheduledTask)tasks.get(j);
                taskVendor = (task.getVendor() != null) ? task.getVendor() : "";
                taskVendor = taskVendor.equals("\n") ? "" : taskVendor;
                taskVendor = "";
                if (task != null) {
                  String dueDate = "";
                  if (task.getDueDate() != null) {
                    SimpleDateFormat dueDateFormatter = new SimpleDateFormat("M/d");
                    dueDate = String.valueOf(dueDateFormatter.format(task.getDueDate().getTime())) + " " + MilestoneHelper.getDayType(task.getDueDate());
                  } 
                  String completionDate = "";
                  if (task.getCompletionDate() != null) {
                    SimpleDateFormat completionDateFormatter = new SimpleDateFormat("M/d");
                    completionDate = completionDateFormatter.format(task.getCompletionDate().getTime());
                  } 
                  if (task.getScheduledTaskStatus().equals("N/A"))
                    completionDate = task.getScheduledTaskStatus(); 
                  String taskAbbrev = MilestoneHelper.getTaskAbbreivationNameById(task.getTaskAbbreviationID());
                  if (taskAbbrev.equalsIgnoreCase("IMG")) {
                    IMG = dueDate;
                    if (task.getScheduledTaskStatus().equals("N/A")) {
                      IMGcom = "n/a";
                    } else if (task.getScheduledTaskStatus().equals("Done")) {
                      IMGcom = "Done";
                    } else {
                      IMGcom = completionDate;
                    } 
                  } else if (taskAbbrev.equalsIgnoreCase("PHO")) {
                    PHO = dueDate;
                    if (task.getScheduledTaskStatus().equals("N/A")) {
                      PHOcom = "n/a";
                    } else if (task.getScheduledTaskStatus().equals("Done")) {
                      PHOcom = "Done";
                    } else {
                      PHOcom = completionDate;
                    } 
                  } else if (taskAbbrev.equalsIgnoreCase("SLT")) {
                    SLT = dueDate;
                    if (task.getScheduledTaskStatus().equals("N/A")) {
                      SLTcom = "n/a";
                    } else if (task.getScheduledTaskStatus().equals("Done")) {
                      SLTcom = "Done";
                    } else {
                      SLTcom = completionDate;
                    } 
                  } else if (taskAbbrev.equalsIgnoreCase("PRO")) {
                    PRO = dueDate;
                    if (task.getScheduledTaskStatus().equals("N/A")) {
                      PROcom = "n/a";
                    } else if (task.getScheduledTaskStatus().equals("Done")) {
                      PROcom = "Done";
                    } else {
                      PROcom = completionDate;
                    } 
                  } else if (taskAbbrev.equalsIgnoreCase("PLN")) {
                    PLN = dueDate;
                    if (task.getScheduledTaskStatus().equals("N/A")) {
                      PLNcom = "n/a";
                    } else if (task.getScheduledTaskStatus().equals("Done")) {
                      PLNcom = "Done";
                    } else {
                      PLNcom = completionDate;
                    } 
                  } else if (taskAbbrev.equalsIgnoreCase("ALB")) {
                    ALB = dueDate;
                    if (task.getScheduledTaskStatus().equals("N/A")) {
                      ALBcom = "n/a";
                    } else if (task.getScheduledTaskStatus().equals("Done")) {
                      ALBcom = "Done";
                    } else {
                      ALBcom = completionDate;
                    } 
                  } else if (taskAbbrev.equalsIgnoreCase("PIH")) {
                    PIH = dueDate;
                    if (task.getScheduledTaskStatus().equals("N/A")) {
                      PIHcom = "n/a";
                    } else if (task.getScheduledTaskStatus().equals("Done")) {
                      PIHcom = "Done";
                    } else {
                      PIHcom = completionDate;
                    } 
                  } else if (taskAbbrev.equalsIgnoreCase("CRDA")) {
                    CRDA = dueDate;
                    if (task.getScheduledTaskStatus().equals("N/A")) {
                      CRDAcom = "n/a";
                    } else if (task.getScheduledTaskStatus().equals("Done")) {
                      CRDAcom = "Done";
                    } else {
                      CRDAcom = completionDate;
                    } 
                  } else if (taskAbbrev.equalsIgnoreCase("CDRP")) {
                    CDRP = dueDate;
                    if (task.getScheduledTaskStatus().equals("N/A")) {
                      CDRPcom = "n/a";
                    } else if (task.getScheduledTaskStatus().equals("Done")) {
                      CDRPcom = "Done";
                    } else {
                      CDRPcom = completionDate;
                    } 
                  } else if (taskAbbrev.equalsIgnoreCase("CNT")) {
                    CNT = dueDate;
                    if (task.getScheduledTaskStatus().equals("N/A")) {
                      CNTcom = "n/a";
                    } else if (task.getScheduledTaskStatus().equals("Done")) {
                      CNTcom = "Done";
                    } else {
                      CNTcom = completionDate;
                    } 
                  } else if (taskAbbrev.equalsIgnoreCase("MKB")) {
                    MKB = dueDate;
                    if (task.getScheduledTaskStatus().equals("N/A")) {
                      MKBcom = "n/a";
                    } else if (task.getScheduledTaskStatus().equals("Done")) {
                      MKBcom = "Done";
                    } else {
                      MKBcom = completionDate;
                    } 
                  } else if (taskAbbrev.equalsIgnoreCase("IMP")) {
                    IMP = dueDate;
                    if (task.getScheduledTaskStatus().equals("N/A")) {
                      IMPcom = "n/a";
                    } else if (task.getScheduledTaskStatus().equals("Done")) {
                      IMPcom = "Done";
                    } else {
                      IMPcom = completionDate;
                    } 
                  } else if (taskAbbrev.equalsIgnoreCase("ADIH")) {
                    ADIH = dueDate;
                    if (task.getScheduledTaskStatus().equals("N/A")) {
                      ADIHcom = "n/a";
                    } else if (task.getScheduledTaskStatus().equals("Done")) {
                      ADIHcom = "Done";
                    } else {
                      ADIHcom = completionDate;
                    } 
                  } else if (taskAbbrev.equalsIgnoreCase("MBR")) {
                    MBR = dueDate;
                    if (task.getScheduledTaskStatus().equals("N/A")) {
                      MBRcom = "n/a";
                    } else if (task.getScheduledTaskStatus().equals("Done")) {
                      MBRcom = "Done";
                    } else {
                      MBRcom = completionDate;
                    } 
                  } else if (taskAbbrev.equalsIgnoreCase("SGL")) {
                    SGL = dueDate;
                    if (task.getScheduledTaskStatus().equals("N/A")) {
                      SGLcom = "n/a";
                    } else if (task.getScheduledTaskStatus().equals("Done")) {
                      SGLcom = "Done";
                    } else {
                      SGLcom = completionDate;
                    } 
                  } else if (taskAbbrev.equalsIgnoreCase("MKP")) {
                    MKP = dueDate;
                    if (task.getScheduledTaskStatus().equals("N/A")) {
                      MKPcom = "n/a";
                    } else if (task.getScheduledTaskStatus().equals("Done")) {
                      MKPcom = "Done";
                    } else {
                      MKPcom = completionDate;
                    } 
                  } else if (taskAbbrev.equalsIgnoreCase("STD")) {
                    STD = dueDate;
                    if (task.getScheduledTaskStatus().equals("N/A")) {
                      STDcom = "n/a";
                    } else if (task.getScheduledTaskStatus().equals("Done")) {
                      STDcom = "Done";
                    } else {
                      STDcom = completionDate;
                    } 
                  } else if (taskAbbrev.equalsIgnoreCase("SEPS")) {
                    SEPS = dueDate;
                    if (task.getScheduledTaskStatus().equals("N/A")) {
                      SEPScom = "n/a";
                    } else if (task.getScheduledTaskStatus().equals("Done")) {
                      SEPScom = "Done";
                    } else {
                      SEPScom = completionDate;
                    } 
                  } else if (taskAbbrev.equalsIgnoreCase("PFS")) {
                    PFS = dueDate;
                    if (task.getScheduledTaskStatus().equals("N/A")) {
                      PFScom = "n/a";
                    } else if (task.getScheduledTaskStatus().equals("Done")) {
                      PFScom = "Done";
                    } else {
                      PFScom = completionDate;
                    } 
                  } 
                  task = null;
                } 
              }  
            nextRow = 0;
            subTable = new DefaultTableLens(2, 24);
            subTable.setRowInsets(nextRow, new Insets(0, 0, 0, 0));
            subTable.setHeaderRowCount(0);
            subTable.setColBorderColor(Color.lightGray);
            subTable.setColWidth(0, 100);
            subTable.setColWidth(1, 100);
            subTable.setColWidth(2, 130);
            subTable.setColWidth(3, 130);
            subTable.setColWidth(4, 130);
            subTable.setColWidth(5, 80);
            subTable.setColWidth(6, 90);
            subTable.setColWidth(7, 80);
            subTable.setColWidth(8, 100);
            subTable.setColWidth(9, 70);
            subTable.setColWidth(10, 100);
            subTable.setColWidth(11, 60);
            subTable.setColWidth(12, 65);
            subTable.setColWidth(13, 80);
            subTable.setColWidth(14, 80);
            subTable.setColWidth(15, 60);
            subTable.setColWidth(16, 70);
            subTable.setColWidth(17, 80);
            subTable.setColWidth(18, 70);
            subTable.setColWidth(19, 70);
            subTable.setColWidth(20, 75);
            subTable.setColWidth(21, 60);
            subTable.setColWidth(22, 100);
            subTable.setColWidth(23, 60);
            subTable.setRowBorderColor(nextRow - 1, Color.lightGray);
            subTable.setBackground(nextRow, 0, Color.white);
            subTable.setAlignment(nextRow, 0, 9);
            subTable.setFont(nextRow, 0, new Font("Arial", 1, 6));
            subTable.setObject(nextRow, 0, artist);
            subTable.setFont(nextRow + 1, 0, new Font("Arial", 0, 6));
            subTable.setAlignment(nextRow + 1, 0, 9);
            subTable.setObject(nextRow + 1, 0, title);
            subTable.setFont(nextRow, 1, new Font("Arial", 1, 7));
            subTable.setFont(nextRow, 2, new Font("Arial", 0, 7));
            subTable.setFont(nextRow, 3, new Font("Arial", 0, 7));
            subTable.setFont(nextRow, 4, new Font("Arial", 0, 7));
            subTable.setBackground(nextRow, 1, Color.white);
            subTable.setObject(nextRow, 1, dateNameText);
            subTable.setAlignment(nextRow, 2, 10);
            String[] check2a = { artist };
            int[] check2 = { 35 };
            String[] check3a = { title };
            int[] check3 = { 35 };
            int extraLines = MilestoneHelper.lineCountWCR(check2a, check2) + MilestoneHelper.lineCountWCR(check3a, check3);
            if (extraLines == 10) {
              extraLines = 9;
            } else if (extraLines > 10) {
              extraLines = 7;
            } 
            for (int z = 0; z < extraLines; z++)
              filler = String.valueOf(filler) + "\n"; 
            Vector multContacts = sel.getMultOtherContacts();
            int numberOfContacts = multContacts.size();
            String marketingDirector = "";
            String artDirector = "";
            String AandRContact = "";
            switch (numberOfContacts) {
              case 1:
                marketingDirector = ((MultOtherContact)multContacts.get(0)).getName();
                break;
              case 2:
                marketingDirector = ((MultOtherContact)multContacts.get(0)).getName();
                artDirector = ((MultOtherContact)multContacts.get(1)).getName();
                break;
              case 3:
                marketingDirector = ((MultOtherContact)multContacts.get(0)).getName();
                artDirector = ((MultOtherContact)multContacts.get(1)).getName();
                AandRContact = ((MultOtherContact)multContacts.get(2)).getName();
                break;
            } 
            subTable.setObject(nextRow, 2, marketingDirector);
            subTable.setObject(nextRow, 3, artDirector);
            subTable.setObject(nextRow, 4, AandRContact);
            subTable.setAlignment(nextRow, 1, 10);
            subTable.setAlignment(nextRow, 2, 9);
            subTable.setBackground(nextRow, 2, Color.white);
            subTable.setAlignment(nextRow, 3, 9);
            subTable.setBackground(nextRow, 3, Color.white);
            subTable.setAlignment(nextRow, 4, 9);
            subTable.setBackground(nextRow, 4, Color.white);
            subTable.setRowBorderColor(nextRow, SHADED_AREA_COLOR);
            subTable.setRowBorderColor(nextRow, 0, Color.white);
            subTable.setRowBorderColor(nextRow, 1, Color.white);
            subTable.setRowBorderColor(nextRow, 2, Color.white);
            subTable.setRowBorderColor(nextRow, 3, Color.white);
            subTable.setRowBorderColor(nextRow, 4, Color.white);
            subTable.setRowBorderColor(nextRow + 1, SHADED_AREA_COLOR);
            subTable.setColBorderColor(nextRow, 3, SHADED_AREA_COLOR);
            subTable.setColBorderColor(nextRow, 4, SHADED_AREA_COLOR);
            subTable.setObject(nextRow, 5, IMGcom);
            subTable.setColBorderColor(nextRow, 5, SHADED_AREA_COLOR);
            subTable.setColBorder(nextRow, 5, 266240);
            subTable.setObject(nextRow, 6, PHOcom);
            subTable.setColBorderColor(nextRow, 6, SHADED_AREA_COLOR);
            subTable.setColBorder(nextRow, 6, 266240);
            subTable.setObject(nextRow, 7, SLTcom);
            subTable.setColBorderColor(nextRow, 7, SHADED_AREA_COLOR);
            subTable.setColBorder(nextRow, 7, 266240);
            subTable.setObject(nextRow, 8, PROcom);
            subTable.setColBorderColor(nextRow, 8, SHADED_AREA_COLOR);
            subTable.setColBorder(nextRow, 8, 266240);
            subTable.setObject(nextRow, 9, PLNcom);
            subTable.setColBorderColor(nextRow, 9, SHADED_AREA_COLOR);
            subTable.setColBorder(nextRow, 9, 266240);
            subTable.setObject(nextRow, 10, ALBcom);
            subTable.setColBorderColor(nextRow, 10, SHADED_AREA_COLOR);
            subTable.setColBorder(nextRow, 10, 266240);
            subTable.setObject(nextRow, 11, PIHcom);
            subTable.setColBorderColor(nextRow, 11, SHADED_AREA_COLOR);
            subTable.setColBorder(nextRow, 11, 266240);
            subTable.setObject(nextRow, 12, CDRPcom);
            subTable.setColBorderColor(nextRow, 12, SHADED_AREA_COLOR);
            subTable.setColBorder(nextRow, 12, 266240);
            subTable.setObject(nextRow, 13, CNTcom);
            subTable.setColBorderColor(nextRow, 13, SHADED_AREA_COLOR);
            subTable.setColBorder(nextRow, 13, 266240);
            subTable.setObject(nextRow, 14, MKBcom);
            subTable.setColBorderColor(nextRow, 14, SHADED_AREA_COLOR);
            subTable.setColBorder(nextRow, 14, 266240);
            subTable.setObject(nextRow, 15, CRDAcom);
            subTable.setColBorderColor(nextRow, 15, SHADED_AREA_COLOR);
            subTable.setColBorder(nextRow, 15, 266240);
            subTable.setObject(nextRow, 16, IMPcom);
            subTable.setColBorderColor(nextRow, 16, SHADED_AREA_COLOR);
            subTable.setColBorder(nextRow, 16, 266240);
            subTable.setObject(nextRow, 17, ADIHcom);
            subTable.setColBorderColor(nextRow, 17, SHADED_AREA_COLOR);
            subTable.setColBorder(nextRow, 17, 266240);
            subTable.setObject(nextRow, 18, MBRcom);
            subTable.setColBorderColor(nextRow, 18, SHADED_AREA_COLOR);
            subTable.setColBorder(nextRow, 18, 266240);
            subTable.setObject(nextRow, 19, SGLcom);
            subTable.setColBorderColor(nextRow, 19, SHADED_AREA_COLOR);
            subTable.setColBorder(nextRow, 19, 266240);
            subTable.setObject(nextRow, 20, MKPcom);
            subTable.setColBorderColor(nextRow, 20, SHADED_AREA_COLOR);
            subTable.setColBorder(nextRow, 20, 266240);
            subTable.setObject(nextRow, 21, STDcom);
            subTable.setColBorderColor(nextRow, 21, SHADED_AREA_COLOR);
            subTable.setColBorder(nextRow, 21, 266240);
            subTable.setObject(nextRow, 22, SEPScom);
            subTable.setColBorderColor(nextRow, 22, SHADED_AREA_COLOR);
            subTable.setColBorder(nextRow, 22, 266240);
            subTable.setObject(nextRow, 23, PFScom);
            subTable.setColBorderColor(nextRow, 23, SHADED_AREA_COLOR);
            subTable.setColBorder(nextRow, 23, 266240);
            subTable.setFont(nextRow, 3, new Font("Arial", 0, 7));
            subTable.setFont(nextRow, 4, new Font("Arial", 0, 7));
            subTable.setFont(nextRow, 5, new Font("Arial", 1, 7));
            subTable.setFont(nextRow, 6, new Font("Arial", 1, 7));
            subTable.setFont(nextRow, 7, new Font("Arial", 1, 7));
            subTable.setFont(nextRow, 8, new Font("Arial", 1, 7));
            subTable.setFont(nextRow, 9, new Font("Arial", 1, 7));
            subTable.setFont(nextRow, 10, new Font("Arial", 1, 7));
            subTable.setFont(nextRow, 11, new Font("Arial", 1, 7));
            subTable.setFont(nextRow, 12, new Font("Arial", 1, 7));
            subTable.setFont(nextRow, 13, new Font("Arial", 1, 7));
            subTable.setFont(nextRow, 14, new Font("Arial", 1, 7));
            subTable.setFont(nextRow, 15, new Font("Arial", 1, 7));
            subTable.setFont(nextRow, 16, new Font("Arial", 1, 7));
            subTable.setFont(nextRow, 17, new Font("Arial", 1, 7));
            subTable.setFont(nextRow, 18, new Font("Arial", 1, 7));
            subTable.setFont(nextRow, 19, new Font("Arial", 1, 7));
            subTable.setFont(nextRow, 20, new Font("Arial", 1, 7));
            subTable.setFont(nextRow, 21, new Font("Arial", 1, 7));
            subTable.setFont(nextRow, 22, new Font("Arial", 1, 7));
            subTable.setFont(nextRow, 23, new Font("Arial", 1, 7));
            subTable.setAlignment(nextRow, 4, 2);
            subTable.setAlignment(nextRow, 5, 2);
            subTable.setAlignment(nextRow, 6, 2);
            subTable.setAlignment(nextRow, 7, 2);
            subTable.setAlignment(nextRow, 8, 2);
            subTable.setAlignment(nextRow, 9, 2);
            subTable.setAlignment(nextRow, 10, 2);
            subTable.setAlignment(nextRow, 11, 2);
            subTable.setAlignment(nextRow, 12, 2);
            subTable.setAlignment(nextRow, 13, 2);
            subTable.setAlignment(nextRow, 14, 2);
            subTable.setAlignment(nextRow, 15, 2);
            subTable.setAlignment(nextRow, 16, 2);
            subTable.setAlignment(nextRow, 17, 2);
            subTable.setAlignment(nextRow, 18, 2);
            subTable.setAlignment(nextRow, 19, 2);
            subTable.setAlignment(nextRow, 20, 2);
            subTable.setAlignment(nextRow, 21, 2);
            subTable.setAlignment(nextRow, 22, 2);
            subTable.setAlignment(nextRow, 23, 2);
            for (int k = 5; k < 24; k++)
              subTable.setBackground(nextRow, k, SHADED_AREA_COLOR); 
            subTable.setColBorderColor(nextRow + 1, 4, SHADED_AREA_COLOR);
            subTable.setObject(nextRow + 1, 5, IMG);
            subTable.setBackground(nextRow + 1, 5, Color.white);
            subTable.setColBorderColor(nextRow + 1, 5, SHADED_AREA_COLOR);
            subTable.setObject(nextRow + 1, 6, PHO);
            subTable.setBackground(nextRow + 1, 6, Color.white);
            subTable.setColBorderColor(nextRow + 1, 6, SHADED_AREA_COLOR);
            subTable.setObject(nextRow + 1, 7, SLT);
            subTable.setBackground(nextRow + 1, 7, Color.white);
            subTable.setColBorderColor(nextRow + 1, 7, SHADED_AREA_COLOR);
            subTable.setObject(nextRow + 1, 8, PRO);
            subTable.setBackground(nextRow + 1, 8, Color.white);
            subTable.setColBorderColor(nextRow + 1, 8, SHADED_AREA_COLOR);
            subTable.setObject(nextRow + 1, 9, PLN);
            subTable.setBackground(nextRow + 1, 9, Color.white);
            subTable.setColBorderColor(nextRow + 1, 9, SHADED_AREA_COLOR);
            subTable.setObject(nextRow + 1, 10, ALB);
            subTable.setBackground(nextRow + 1, 10, Color.white);
            subTable.setColBorderColor(nextRow + 1, 10, SHADED_AREA_COLOR);
            subTable.setObject(nextRow + 1, 11, PIH);
            subTable.setBackground(nextRow + 1, 11, Color.white);
            subTable.setColBorderColor(nextRow + 1, 11, SHADED_AREA_COLOR);
            subTable.setObject(nextRow + 1, 12, CDRP);
            subTable.setBackground(nextRow + 1, 12, Color.white);
            subTable.setColBorderColor(nextRow + 1, 12, SHADED_AREA_COLOR);
            subTable.setObject(nextRow + 1, 13, CNT);
            subTable.setBackground(nextRow + 1, 13, Color.white);
            subTable.setColBorderColor(nextRow + 1, 13, SHADED_AREA_COLOR);
            subTable.setObject(nextRow + 1, 14, MKB);
            subTable.setBackground(nextRow + 1, 14, Color.white);
            subTable.setColBorderColor(nextRow + 1, 14, SHADED_AREA_COLOR);
            subTable.setObject(nextRow + 1, 15, CRDA);
            subTable.setBackground(nextRow + 1, 15, Color.white);
            subTable.setColBorderColor(nextRow + 1, 15, SHADED_AREA_COLOR);
            subTable.setObject(nextRow + 1, 16, IMP);
            subTable.setBackground(nextRow + 1, 16, Color.white);
            subTable.setColBorderColor(nextRow + 1, 16, SHADED_AREA_COLOR);
            subTable.setObject(nextRow + 1, 17, ADIH);
            subTable.setBackground(nextRow + 1, 17, Color.white);
            subTable.setColBorderColor(nextRow + 1, 17, SHADED_AREA_COLOR);
            subTable.setObject(nextRow + 1, 18, MBR);
            subTable.setBackground(nextRow + 1, 18, Color.white);
            subTable.setColBorderColor(nextRow + 1, 18, SHADED_AREA_COLOR);
            subTable.setObject(nextRow + 1, 19, SGL);
            subTable.setBackground(nextRow + 1, 19, Color.white);
            subTable.setColBorderColor(nextRow + 1, 19, SHADED_AREA_COLOR);
            subTable.setObject(nextRow + 1, 20, MKP);
            subTable.setBackground(nextRow + 1, 20, Color.white);
            subTable.setColBorderColor(nextRow + 1, 20, SHADED_AREA_COLOR);
            subTable.setObject(nextRow + 1, 21, STD);
            subTable.setBackground(nextRow + 1, 21, Color.white);
            subTable.setColBorderColor(nextRow + 1, 21, SHADED_AREA_COLOR);
            subTable.setObject(nextRow + 1, 22, SEPS);
            subTable.setBackground(nextRow + 1, 22, Color.white);
            subTable.setColBorderColor(nextRow + 1, 22, SHADED_AREA_COLOR);
            subTable.setObject(nextRow + 1, 23, PFS);
            subTable.setBackground(nextRow + 1, 23, Color.white);
            subTable.setColBorderColor(nextRow + 1, 23, SHADED_AREA_COLOR);
            subTable.setAlignment(nextRow + 1, 3, 10);
            subTable.setAlignment(nextRow + 1, 4, 10);
            subTable.setAlignment(nextRow + 1, 5, 10);
            subTable.setAlignment(nextRow + 1, 6, 10);
            subTable.setAlignment(nextRow + 1, 7, 10);
            subTable.setAlignment(nextRow + 1, 8, 10);
            subTable.setAlignment(nextRow + 1, 9, 10);
            subTable.setAlignment(nextRow + 1, 10, 10);
            subTable.setAlignment(nextRow + 1, 11, 10);
            subTable.setAlignment(nextRow + 1, 12, 10);
            subTable.setAlignment(nextRow + 1, 13, 10);
            subTable.setAlignment(nextRow + 1, 14, 10);
            subTable.setAlignment(nextRow + 1, 15, 10);
            subTable.setAlignment(nextRow + 1, 16, 10);
            subTable.setAlignment(nextRow + 1, 17, 10);
            subTable.setAlignment(nextRow + 1, 18, 10);
            subTable.setAlignment(nextRow + 1, 19, 10);
            subTable.setAlignment(nextRow + 1, 20, 10);
            subTable.setAlignment(nextRow + 1, 21, 10);
            subTable.setAlignment(nextRow + 1, 22, 10);
            subTable.setAlignment(nextRow + 1, 23, 10);
            subTable.setRowFont(nextRow + 1, new Font("Arial", 0, 7));
            subTable.setRowForeground(nextRow, Color.black);
            subTable.setRowFont(nextRow, new Font("Arial", 0, 7));
            body = new SectionBand(report);
            double lfLineCount = 1.5D;
            body.setHeight(3.0F);
            body.addTable(subTable, new Rectangle(800, 800));
            body.setBottomBorder(0);
            body.setTopBorder(0);
            body.setShrinkToFit(true);
            body.setVisible(true);
            if (newCycle)
              group = new DefaultSectionLens(null, group, spacer); 
            group = new DefaultSectionLens(null, group, body);
          } 
        } 
        group = new DefaultSectionLens(hbandType, group, null);
        report.addSection(group, rowCountTable);
        report.addPageBreak();
        group = null;
      } 
    } catch (Exception e) {
      System.out.println(">>>>>>>>McaProductionScheduleForPrintSubHandler(): exception: " + e);
    } 
    System.out.println("done");
  }
}


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\MCANewReleasePlanningSchedulePrintSubHandler.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */