package WEB-INF.classes.com.universal.milestone;

import com.techempower.ComponentLog;
import com.techempower.gemini.Context;
import com.techempower.gemini.GeminiApplication;
import com.universal.milestone.DatePeriod;
import com.universal.milestone.Form;
import com.universal.milestone.MCAProductionScheduleForPrintSubHandler;
import com.universal.milestone.MilestoneHelper;
import com.universal.milestone.MilestoneHelper_2;
import com.universal.milestone.MonthYearComparator;
import com.universal.milestone.Schedule;
import com.universal.milestone.ScheduledTask;
import com.universal.milestone.SecureHandler;
import com.universal.milestone.Selection;
import com.universal.milestone.SelectionManager;
import com.universal.milestone.StringDateComparator;
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

public class MCAProductionScheduleForPrintSubHandler extends SecureHandler {
  public static final String COMPONENT_CODE = "hMCAProd";
  
  public GeminiApplication application;
  
  public ComponentLog log;
  
  public void MCAProdcutionScheduleForPrintSubHandler(GeminiApplication application) {
    this.application = application;
    this.log = application.getLog("hMCAProd");
  }
  
  public String getDescription() { return "Sub Report"; }
  
  protected static void fillMCAProductionScheduleForPrint(XStyleSheet report, Context context) {
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
    int NUM_COLUMNS = 12;
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
    Hashtable selTable = MilestoneHelper.groupSelectionsForMcaProductionByTypeConfigAndStreetDate(selections);
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
        String configC = (configVector.elementAt(n) != null) ? (String)configVector.elementAt(n) : "";
        Hashtable monthTableC = (Hashtable)selTable.get(configC);
        totalCount++;
        Enumeration monthsC = monthTableC.keys();
        Vector monthVectorC = new Vector();
        while (monthsC.hasMoreElements()) {
          monthVectorC.add((String)monthsC.nextElement());
          Object[] monthArrayC = null;
          monthArrayC = monthVectorC.toArray();
          totalCount += monthArrayC.length;
        } 
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
      for (int n = 0; n < sortedConfigVector.size(); n++) {
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
        String config = (configVector.elementAt(n) != null) ? (String)configVector.elementAt(n) : "";
        Hashtable monthTable = (Hashtable)selTable.get(config);
        String previousReleaseWeek = "";
        boolean newCycle = false;
        int numMonths = 0;
        int numDates = 0;
        int numSelections = 0;
        if (monthTable != null) {
          Enumeration months = monthTable.keys();
          while (months.hasMoreElements()) {
            String monthName = (String)months.nextElement();
            numMonths++;
            Hashtable dateTable = (Hashtable)monthTable.get(monthName);
            if (dateTable != null) {
              Enumeration dates = dateTable.keys();
              while (dates.hasMoreElements()) {
                String dateName = (String)dates.nextElement();
                numDates++;
                selections = (Vector)dateTable.get(dateName);
                if (selections != null)
                  numSelections += selections.size(); 
              } 
            } 
          } 
        } 
        int numRows = 0;
        numRows += numMonths * 3;
        numRows += numDates * 2;
        numRows += numSelections * 2;
        numRows += 5;
        hbandType = new SectionBand(report);
        hbandType.setHeight(0.95F);
        hbandType.setShrinkToFit(true);
        hbandType.setVisible(true);
        table_contents = new DefaultTableLens(1, 12);
        table_contents.setHeaderRowCount(0);
        table_contents.setColBorderColor(Color.lightGray);
        table_contents.setColWidth(0, 70);
        table_contents.setColWidth(1, 259);
        table_contents.setColWidth(2, 157);
        table_contents.setColWidth(3, 80);
        table_contents.setColWidth(4, 80);
        table_contents.setColWidth(5, 80);
        table_contents.setColWidth(6, 80);
        table_contents.setColWidth(7, 60);
        table_contents.setColWidth(8, 60);
        table_contents.setColWidth(9, 60);
        table_contents.setColWidth(10, 60);
        table_contents.setColWidth(11, 60);
        table_contents.setColWidth(12, 60);
        table_contents.setColBorderColor(Color.black);
        table_contents.setRowBorderColor(Color.black);
        table_contents.setRowBorder(4097);
        table_contents.setColBorder(4097);
        int nextRow = 0;
        String configHeaderText = !config.trim().equals("") ? config : "Other";
        if (configHeaderText != null)
          if (configHeaderText.startsWith("Frontline")) {
            configHeaderText = "Frontline";
          } else if (configHeaderText.startsWith("Singles")) {
            configHeaderText = "Singles";
          }  
        table_contents.setSpan(nextRow, 0, new Dimension(12, 1));
        table_contents.setAlignment(nextRow, 0, 2);
        table_contents.setObject(nextRow, 0, configHeaderText);
        table_contents.setRowHeight(nextRow, 16);
        table_contents.setRowBorderColor(nextRow, Color.black);
        table_contents.setRowBorder(nextRow, 0, 266240);
        table_contents.setRowFont(nextRow, new Font("Arial", 3, 12));
        table_contents.setRowBackground(nextRow, Color.white);
        table_contents.setRowForeground(nextRow, Color.black);
        table_contents.setRowBorder(nextRow - 1, 266240);
        table_contents.setColBorder(nextRow, -1, 266240);
        table_contents.setColBorder(nextRow, 0, 266240);
        table_contents.setRowBorder(nextRow, 266240);
        table_contents.setRowBorderColor(nextRow - 1, Color.black);
        table_contents.setColBorderColor(nextRow, -1, Color.black);
        table_contents.setColBorderColor(nextRow, 0, Color.black);
        table_contents.setRowBorderColor(nextRow, Color.black);
        hbandType.addTable(table_contents, new Rectangle(0, 0, 800, 25));
        nextRow = 0;
        columnHeaderTable = new DefaultTableLens(1, 12);
        columnHeaderTable.setHeaderRowCount(0);
        columnHeaderTable.setColBorderColor(Color.lightGray);
        columnHeaderTable.setColWidth(0, 100);
        columnHeaderTable.setColWidth(1, 259);
        columnHeaderTable.setColWidth(2, 157);
        columnHeaderTable.setColWidth(3, 80);
        columnHeaderTable.setColWidth(4, 80);
        columnHeaderTable.setColWidth(5, 80);
        columnHeaderTable.setColWidth(6, 80);
        columnHeaderTable.setColWidth(7, 60);
        columnHeaderTable.setColWidth(8, 60);
        columnHeaderTable.setColWidth(9, 60);
        columnHeaderTable.setColWidth(10, 60);
        columnHeaderTable.setColWidth(11, 60);
        columnHeaderTable.setColWidth(12, 60);
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
        columnHeaderTable.setObject(nextRow, 0, "Street Date\nRelease Week\nProject ID");
        columnHeaderTable.setObject(nextRow, 1, "Artist/Title\nProd./MM");
        columnHeaderTable.setObject(nextRow, 2, "Config\nLocal Prod #\nUPC");
        columnHeaderTable.setObject(nextRow, 3, "Sell\nRetail\nPrice");
        columnHeaderTable.setObject(nextRow, 4, "Credits\n to Art");
        columnHeaderTable.setObject(nextRow, 5, "*\n\nPFM");
        columnHeaderTable.setObject(nextRow, 6, "*\n\nMAP");
        columnHeaderTable.setObject(nextRow, 7, "*\n\nBOM");
        columnHeaderTable.setObject(nextRow, 8, "*\nDJ\nQty\nDue");
        columnHeaderTable.setObject(nextRow, 9, "Film\nShips");
        columnHeaderTable.setObject(nextRow, 10, "*\nPPR");
        columnHeaderTable.setObject(nextRow, 11, "*\nAvail\nto\nShip");
        columnHeaderTable.setRowBorderColor(nextRow - 1, SHADED_AREA_COLOR);
        columnHeaderTable.setRowBorderColor(nextRow, SHADED_AREA_COLOR);
        columnHeaderTable.setRowFont(nextRow, new Font("Arial", 1, 8));
        columnHeaderTable.setRowBackground(nextRow, Color.white);
        columnHeaderTable.setRowForeground(nextRow, Color.black);
        hbandType.addTable(columnHeaderTable, new Rectangle(0, 24, 800, 45));
        hbandType.setBottomBorder(0);
        if (monthTable != null) {
          Enumeration months = monthTable.keys();
          Vector monthVector = new Vector();
          while (months.hasMoreElements())
            monthVector.add((String)months.nextElement()); 
          Object[] monthArray = null;
          monthArray = monthVector.toArray();
          Arrays.sort(monthArray, new MonthYearComparator());
          for (int x = 0; x < monthArray.length; x++) {
            String monthName = (String)monthArray[x];
            String monthNameString = monthName;
            try {
              monthNameString = MONTHS[Integer.parseInt(monthName.substring(0, 2)) - 1];
            } catch (Exception e) {
              if (monthName.equals("13")) {
                monthNameString = "TBS";
              } else if (monthName.equals("26")) {
                monthNameString = "ITW";
              } else {
                monthNameString = "No street date";
              } 
            } 
            monthTableLens = new DefaultTableLens(1, 12);
            hbandCategory = new SectionBand(report);
            hbandCategory.setHeight(0.25F);
            hbandCategory.setShrinkToFit(true);
            hbandCategory.setVisible(true);
            hbandCategory.setBottomBorder(0);
            hbandCategory.setLeftBorder(0);
            hbandCategory.setRightBorder(0);
            hbandCategory.setTopBorder(0);
            nextRow = 0;
            monthTableLens.setSpan(nextRow, 0, new Dimension(12, 1));
            monthTableLens.setColBorderColor(Color.white);
            monthTableLens.setObject(nextRow, 0, monthNameString);
            monthTableLens.setRowHeight(nextRow, 13);
            monthTableLens.setRowFont(nextRow, new Font("Arial", 1, 12));
            monthTableLens.setRowBackground(nextRow, SHADED_AREA_COLOR);
            monthTableLens.setRowForeground(nextRow, Color.black);
            monthTableLens.setRowBorderColor(nextRow, Color.white);
            monthTableLens.setRowBorderColor(nextRow - 1, Color.white);
            monthTableLens.setColBorderColor(nextRow, -1, Color.white);
            monthTableLens.setColBorderColor(nextRow, 0, Color.white);
            monthTableLens.setColBorderColor(nextRow, 12, Color.white);
            hbandCategory.addTable(monthTableLens, new Rectangle(800, 800));
            footer.setVisible(true);
            footer.setHeight(0.1F);
            footer.setShrinkToFit(false);
            footer.setBottomBorder(0);
            group = new DefaultSectionLens(null, group, spacer);
            group = new DefaultSectionLens(null, group, hbandCategory);
            group = new DefaultSectionLens(null, group, spacer);
            Hashtable dateTable = (Hashtable)monthTable.get(monthName);
            if (dateTable != null) {
              Enumeration dateSort = dateTable.keys();
              Vector dateVector = new Vector();
              while (dateSort.hasMoreElements())
                dateVector.add((String)dateSort.nextElement()); 
              Object[] dateArray = null;
              dateArray = dateVector.toArray();
              Arrays.sort(dateArray, new StringDateComparator());
              for (int dIndex = 0; dIndex < dateArray.length; dIndex++) {
                String dateName = (String)dateArray[dIndex];
                String dateNameText = dateName;
                if (monthNameString.equalsIgnoreCase("TBS")) {
                  dateNameText = "TBS " + dateName;
                } else if (monthNameString.equalsIgnoreCase("ITW")) {
                  dateNameText = "ITW " + dateName;
                } 
                String releaseWeek = "";
                try {
                  Calendar calanderDate = MilestoneHelper.getDate(dateNameText);
                  DatePeriod datePeriod = MilestoneHelper.findDatePeriod(calanderDate);
                  releaseWeek = " " + datePeriod.getName();
                } catch (Exception exception) {}
                selections = (Vector)dateTable.get(dateName);
                if (selections == null)
                  selections = new Vector(); 
                MilestoneHelper.setSelectionSorting(selections, 12);
                Collections.sort(selections);
                MilestoneHelper.setSelectionSorting(selections, 14);
                Collections.sort(selections);
                MilestoneHelper.setSelectionSorting(selections, 4);
                Collections.sort(selections);
                MilestoneHelper.setSelectionSorting(selections, 3);
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
                    context.putDelivery("percent", 
                        new String(String.valueOf(myPercent)));
                    context.includeJSP("status.jsp", "hiddenFrame");
                    sresponse.setContentType("text/plain");
                    sresponse.flushBuffer();
                  } 
                  recordCount++;
                  String titleId = "";
                  titleId = sel.getTitleID();
                  String bSide = "";
                  bSide = sel.getBSide();
                  String internationalDate = "";
                  String filler = "";
                  String projectID = (sel.getProjectID() != null) ? 
                    sel.getProjectID() : "";
                  String artist = "";
                  artist = sel.getArtist().trim();
                  String comment = "";
                  String commentStr = (sel.getSelectionComments() != null) ? 
                    sel.getSelectionComments() : "";
                  String newComment = removeLF(commentStr, 800);
                  int subTableRows = 2;
                  if (newComment.length() > 0)
                    subTableRows = 3; 
                  String label = "";
                  if (sel.getLabel() != null)
                    label = sel.getLabel().getName(); 
                  String title = "";
                  if (sel.getTitle() != null)
                    title = sel.getTitle(); 
                  String upc = "";
                  upc = sel.getUpc();
                  if (upc == null || upc.length() == 0)
                    upc = ""; 
                  upc = MilestoneHelper_2.getRMSReportFormat(upc, "UPC", 
                      sel.getIsDigital());
                  String selConfig = "";
                  selConfig = (sel.getSelectionConfig() != null) ? 
                    sel.getSelectionConfig()
                    .getSelectionConfigurationAbbreviation() : "";
                  String selSubConfig = "";
                  selSubConfig = (sel.getSelectionSubConfig() != null) ? 
                    sel.getSelectionSubConfig()
                    .getSelectionSubConfigurationAbbreviation() : "";
                  String units = "";
                  units = (sel.getNumberOfUnits() > 0) ? 
                    String.valueOf(sel.getNumberOfUnits()) : "";
                  String code = (sel.getSellCode() != null) ? sel.getSellCode() : 
                    "";
                  if (code != null && code.startsWith("-1"))
                    code = ""; 
                  String retail = "";
                  if (sel.getPriceCode() != null && 
                    sel.getPriceCode().getRetailCode() != null)
                    retail = sel.getPriceCode().getRetailCode(); 
                  if (code.length() > 0)
                    retail = "\n" + retail; 
                  String price = "";
                  if (sel.getPriceCode() != null && 
                    sel.getPriceCode().getTotalCost() > 0.0F)
                    price = "\n$" + 
                      MilestoneHelper.formatDollarPrice(sel.getPriceCode()
                        .getTotalCost()); 
                  String selectionID = "";
                  selectionID = sel.getSelectionNo();
                  String prefix = "";
                  prefix = SelectionManager.getLookupObjectValue(sel
                      .getPrefixID());
                  if (!prefix.equals(""))
                    prefix = String.valueOf(prefix) + "-"; 
                  Schedule schedule = sel.getSchedule();
                  Vector tasks = (schedule != null) ? schedule.getTasks() : null;
                  ScheduledTask task = null;
                  String CRD = "";
                  String FM = "";
                  String MAP = "";
                  String BOM = "";
                  String DJ = "";
                  String LFS = "";
                  String MC = "";
                  String PSD = "";
                  String CRDcom = "";
                  String FMcom = "";
                  String MAPcom = "";
                  String BOMcom = "";
                  String DJcom = "";
                  String LFScom = "";
                  String MCcom = "";
                  String PSDcom = "";
                  String taskVendor = "";
                  if (tasks != null)
                    for (int j = 0; j < tasks.size(); j++) {
                      task = (ScheduledTask)tasks.get(j);
                      taskVendor = (task.getVendor() != null) ? task.getVendor() : "";
                      taskVendor = taskVendor.equals("\n") ? "" : taskVendor;
                      if (task != null) {
                        String dueDate = "";
                        if (task.getDueDate() != null) {
                          SimpleDateFormat dueDateFormatter = 
                            new SimpleDateFormat("M/d");
                          dueDate = 
                            String.valueOf(dueDateFormatter.format(task.getDueDate().getTime())) + " " + 
                            MilestoneHelper.getDayType(sel.getCalendarGroup(), 
                              task.getDueDate());
                        } 
                        String completionDate = "";
                        if (task.getCompletionDate() != null) {
                          SimpleDateFormat completionDateFormatter = 
                            new SimpleDateFormat("M/d");
                          completionDate = completionDateFormatter.format(task
                              .getCompletionDate().getTime());
                        } 
                        if (task.getScheduledTaskStatus().equals("N/A"))
                          completionDate = task.getScheduledTaskStatus(); 
                        String taskAbbrev = 
                          MilestoneHelper.getTaskAbbreivationNameById(task
                            .getTaskAbbreviationID());
                        if (taskAbbrev.equalsIgnoreCase("FPC")) {
                          CRD = dueDate;
                          CRDcom = String.valueOf(completionDate) + "\n\n" + taskVendor;
                          if (!completionDate.equals("")) {
                            CRD = "Done";
                            if (completionDate.equals("9/9/99"))
                              CRDcom = "\n\n" + taskVendor; 
                          } 
                        } else if (taskAbbrev.equalsIgnoreCase("F/M")) {
                          FM = dueDate;
                          FMcom = String.valueOf(completionDate) + "\n\n" + taskVendor;
                          if (!completionDate.equals("")) {
                            FM = "Done";
                            if (completionDate.equals("9/9/99"))
                              FMcom = "\n\n" + taskVendor; 
                          } 
                        } else if (taskAbbrev.equalsIgnoreCase("PSD")) {
                          PSD = dueDate;
                          PSDcom = String.valueOf(completionDate) + "\n\n" + taskVendor;
                          if (!completionDate.equals("")) {
                            PSD = "Done";
                            if (completionDate.equals("9/9/99"))
                              PSDcom = "\n\n" + taskVendor; 
                          } 
                        } else if (taskAbbrev.equalsIgnoreCase("BOM")) {
                          BOM = dueDate;
                          BOMcom = String.valueOf(completionDate) + "\n\n" + taskVendor;
                          if (!completionDate.equals("")) {
                            BOM = "Done";
                            if (completionDate.equals("9/9/99"))
                              BOMcom = "\n\n" + taskVendor; 
                          } 
                        } else if (taskAbbrev.equalsIgnoreCase("MPOP")) {
                          DJ = dueDate;
                          DJcom = String.valueOf(completionDate) + "\n\n" + taskVendor;
                          if (!completionDate.equals("")) {
                            DJ = "Done";
                            if (completionDate.equals("9/9/99"))
                              DJcom = "\n\n" + taskVendor; 
                          } 
                        } else if (taskAbbrev.equalsIgnoreCase("TPS")) {
                          MAP = dueDate;
                          MAPcom = String.valueOf(completionDate) + "\n\n" + taskVendor;
                          if (!completionDate.equals("")) {
                            MAP = "Done";
                            if (completionDate.equals("9/9/99"))
                              MAPcom = "\n\n" + taskVendor; 
                          } 
                        } else if (taskAbbrev.equalsIgnoreCase("LFS")) {
                          LFS = dueDate;
                          LFScom = String.valueOf(completionDate) + "\n\n" + taskVendor;
                          if (!completionDate.equals("")) {
                            LFS = "Done";
                            if (completionDate.equals("9/9/99"))
                              LFScom = "\n\n" + taskVendor; 
                          } 
                        } else if (taskAbbrev.equalsIgnoreCase("M/C")) {
                          MC = dueDate;
                          MCcom = String.valueOf(completionDate) + "\n\n" + taskVendor;
                          if (!completionDate.equals("")) {
                            MC = "Done";
                            if (completionDate.equals("9/9/99"))
                              MCcom = "\n\n" + taskVendor; 
                          } 
                        } 
                        task = null;
                      } 
                    }  
                  nextRow = 0;
                  subTable = new DefaultTableLens(subTableRows, 12);
                  subTable.setRowInsets(nextRow, new Insets(0, 0, 0, 0));
                  subTable.setHeaderRowCount(0);
                  subTable.setColBorderColor(Color.lightGray);
                  subTable.setColWidth(0, 100);
                  subTable.setColWidth(1, 259);
                  subTable.setColWidth(2, 157);
                  subTable.setColWidth(3, 80);
                  subTable.setColWidth(4, 80);
                  subTable.setColWidth(5, 80);
                  subTable.setColWidth(6, 80);
                  subTable.setColWidth(7, 60);
                  subTable.setColWidth(8, 60);
                  subTable.setColWidth(9, 60);
                  subTable.setColWidth(10, 60);
                  subTable.setColWidth(11, 60);
                  subTable.setColWidth(12, 60);
                  subTable.setRowBorderColor(nextRow - 1, Color.lightGray);
                  if (releaseWeek.equals(previousReleaseWeek)) {
                    newCycle = false;
                  } else {
                    newCycle = true;
                  } 
                  previousReleaseWeek = releaseWeek;
                  subTable.setBackground(nextRow, 0, Color.white);
                  subTable.setAlignment(nextRow, 0, 10);
                  subTable.setSpan(nextRow, 0, new Dimension(1, 2));
                  String releaseWeekString = "";
                  if (releaseWeek.trim().equals("")) {
                    releaseWeekString = projectID;
                  } else {
                    releaseWeekString = String.valueOf(releaseWeek) + "\n" + projectID;
                  } 
                  subTable.setObject(nextRow, 0, String.valueOf(dateNameText) + "\n" + releaseWeekString);
                  subTable.setFont(nextRow, 0, new Font("Arial", 0, 7));
                  String contact = "";
                  contact = (sel.getLabelContact() != null) ? sel.getLabelContact().getName() : "";
                  String otherContact = "";
                  otherContact = (sel.getOtherContact() != null) ? sel.getOtherContact() : "";
                  subTable.setObject(nextRow, 1, String.valueOf(artist) + "\n" + title + "\n" + contact + "\n" + otherContact);
                  subTable.setBackground(nextRow, 1, Color.white);
                  subTable.setSpan(nextRow, 1, new Dimension(1, 2));
                  subTable.setAlignment(nextRow, 1, 9);
                  if (sel.getInternationalDate() != null) {
                    subTable.setFont(nextRow, 1, new Font("Arial", 2, 7));
                  } else {
                    subTable.setFont(nextRow, 1, new Font("Arial", 1, 7));
                  } 
                  String[] check1a = { comment };
                  int[] check1 = { 16 };
                  String[] check2a = { artist };
                  int[] check2 = { 35 };
                  String[] check3a = { title };
                  int[] check3 = { 35 };
                  String[] check4a = { bSide };
                  int[] check4 = { 35 };
                  int extraLines = MilestoneHelper.lineCountWCR(check1a, check1) + MilestoneHelper.lineCountWCR(check2a, check2) + MilestoneHelper.lineCountWCR(check3a, check3) + MilestoneHelper.lineCountWCR(check4a, check4);
                  if (LFScom.length() > 8)
                    extraLines += 2; 
                  if (extraLines == 10) {
                    extraLines = 9;
                  } else if (extraLines > 10) {
                    extraLines = 7;
                  } 
                  for (int z = 0; z < extraLines; z++)
                    filler = String.valueOf(filler) + "\n"; 
                  String explicitInd = "";
                  if (sel.getParentalGuidance())
                    explicitInd = "P.A.Required"; 
                  if (!explicitInd.equals("")) {
                    subTable.setObject(nextRow, 2, String.valueOf(selSubConfig) + "\n" + prefix + selectionID + "\n" + upc + "\n" + explicitInd);
                  } else {
                    subTable.setObject(nextRow, 2, String.valueOf(selSubConfig) + "\n" + prefix + selectionID + "\n" + upc);
                  } 
                  subTable.setAlignment(nextRow, 2, 10);
                  subTable.setBackground(nextRow, 2, Color.white);
                  subTable.setSpan(nextRow, 2, new Dimension(1, 2));
                  subTable.setObject(nextRow, 3, "Due Dates");
                  subTable.setColBorderColor(nextRow, 3, SHADED_AREA_COLOR);
                  subTable.setColBorder(nextRow, 3, 266240);
                  subTable.setFont(nextRow, 3, new Font("Arial", 1, 8));
                  subTable.setRowBorderColor(nextRow, SHADED_AREA_COLOR);
                  subTable.setAlignment(nextRow, 4, 10);
                  subTable.setAlignment(nextRow + 1, 4, 10);
                  subTable.setRowHeight(nextRow, 9);
                  subTable.setRowBorderColor(nextRow + 1, SHADED_AREA_COLOR);
                  subTable.setObject(nextRow, 4, CRD);
                  subTable.setColBorderColor(nextRow, 4, SHADED_AREA_COLOR);
                  subTable.setColBorder(nextRow, 4, 266240);
                  subTable.setObject(nextRow, 5, FM);
                  subTable.setColBorderColor(nextRow, 5, SHADED_AREA_COLOR);
                  subTable.setColBorder(nextRow, 5, 266240);
                  subTable.setObject(nextRow, 6, MAP);
                  subTable.setColBorderColor(nextRow, 6, Color.lightGray);
                  subTable.setColBorder(nextRow, 6, 266240);
                  subTable.setObject(nextRow, 7, BOM);
                  subTable.setColBorderColor(nextRow, 7, SHADED_AREA_COLOR);
                  subTable.setColBorder(nextRow, 7, 266240);
                  subTable.setObject(nextRow, 8, DJ);
                  subTable.setColBorderColor(nextRow, 8, SHADED_AREA_COLOR);
                  subTable.setColBorder(nextRow, 8, 266240);
                  subTable.setObject(nextRow, 9, LFS);
                  subTable.setColBorderColor(nextRow, 9, Color.lightGray);
                  subTable.setColBorder(nextRow, 9, 266240);
                  subTable.setObject(nextRow, 10, MC);
                  subTable.setColBorderColor(nextRow, 10, SHADED_AREA_COLOR);
                  subTable.setColBorder(nextRow, 10, 266240);
                  subTable.setObject(nextRow, 11, PSD);
                  subTable.setColBorderColor(nextRow, 11, SHADED_AREA_COLOR);
                  subTable.setColBorder(nextRow, 11, 266240);
                  subTable.setFont(nextRow, 3, new Font("Arial", 1, 7));
                  subTable.setFont(nextRow, 4, new Font("Arial", 1, 7));
                  subTable.setFont(nextRow, 5, new Font("Arial", 1, 7));
                  subTable.setFont(nextRow, 6, new Font("Arial", 1, 7));
                  subTable.setFont(nextRow, 7, new Font("Arial", 1, 7));
                  subTable.setFont(nextRow, 8, new Font("Arial", 1, 7));
                  subTable.setFont(nextRow, 9, new Font("Arial", 1, 7));
                  subTable.setFont(nextRow, 10, new Font("Arial", 1, 7));
                  subTable.setFont(nextRow, 11, new Font("Arial", 1, 7));
                  Font holidayFont = new Font("Arial", 3, 7);
                  for (int colIdx = 4; colIdx <= 10; colIdx++) {
                    String dueDate = subTable.getObject(nextRow, colIdx).toString();
                    if (dueDate != null && dueDate.length() > 0) {
                      char lastChar = dueDate.charAt(dueDate.length() - 1);
                      if (Character.isLetter(lastChar))
                        subTable.setFont(nextRow, colIdx, holidayFont); 
                    } 
                  } 
                  subTable.setAlignment(nextRow, 4, 2);
                  subTable.setAlignment(nextRow, 5, 2);
                  subTable.setAlignment(nextRow, 6, 2);
                  subTable.setAlignment(nextRow, 7, 2);
                  subTable.setAlignment(nextRow, 8, 2);
                  subTable.setAlignment(nextRow, 9, 2);
                  subTable.setAlignment(nextRow, 10, 2);
                  subTable.setAlignment(nextRow, 11, 2);
                  if (extraLines > 0) {
                    subTable.setObject(nextRow + 1, 3, String.valueOf(code) + retail + price + filler);
                  } else {
                    subTable.setObject(nextRow + 1, 3, String.valueOf(code) + retail + price);
                  } 
                  subTable.setObject(nextRow + 1, 4, CRDcom);
                  subTable.setObject(nextRow + 1, 5, FMcom);
                  subTable.setObject(nextRow + 1, 6, MAPcom);
                  subTable.setObject(nextRow + 1, 7, BOMcom);
                  subTable.setObject(nextRow + 1, 8, DJcom);
                  subTable.setObject(nextRow + 1, 9, LFScom);
                  subTable.setObject(nextRow + 1, 10, MCcom);
                  subTable.setObject(nextRow + 1, 11, PSDcom);
                  subTable.setAlignment(nextRow + 1, 3, 10);
                  subTable.setAlignment(nextRow + 1, 4, 10);
                  subTable.setAlignment(nextRow + 1, 5, 10);
                  subTable.setAlignment(nextRow + 1, 6, 10);
                  subTable.setAlignment(nextRow + 1, 7, 10);
                  subTable.setAlignment(nextRow + 1, 8, 10);
                  subTable.setAlignment(nextRow + 1, 9, 10);
                  subTable.setAlignment(nextRow + 1, 10, 10);
                  subTable.setAlignment(nextRow + 1, 11, 10);
                  subTable.setRowFont(nextRow + 1, new Font("Arial", 0, 7));
                  subTable.setRowBackground(nextRow, SHADED_AREA_COLOR);
                  subTable.setRowForeground(nextRow, Color.black);
                  subTable.setRowFont(nextRow, new Font("Arial", 0, 7));
                  if (newComment.length() > 0) {
                    nextRow++;
                    subTable.setRowAutoSize(true);
                    subTable.setRowBorderColor(nextRow + 1, Color.lightGray);
                    setColBorderColor(subTable, nextRow + 1, 12, SHADED_AREA_COLOR);
                    subTable.setRowFont(nextRow + 1, new Font("Arial", 3, 7));
                    subTable.setSpan(nextRow + 1, 0, new Dimension(1, 1));
                    subTable.setSpan(nextRow + 1, 1, new Dimension(1, 1));
                    subTable.setAlignment(nextRow + 1, 1, 12);
                    subTable.setObject(nextRow + 1, 0, "Comments:   ");
                    subTable.setObject(nextRow + 1, 1, newComment);
                    subTable.setSpan(nextRow + 1, 1, new Dimension(10, 1));
                    subTable.setRowBorderColor(nextRow + 1, Color.lightGray);
                    setColBorderColor(subTable, nextRow + 1, 12, SHADED_AREA_COLOR);
                    subTable.setRowFont(nextRow + 1, new Font("Arial", 3, 7));
                    subTable.setAlignment(nextRow + 1, 1, 9);
                    subTable.setColLineWrap(1, true);
                    subTable.setColBorderColor(nextRow + 1, 0, Color.white);
                    subTable.setColBorderColor(nextRow + 1, 1, Color.white);
                    subTable.setColBorderColor(nextRow + 1, 2, Color.white);
                    subTable.setColBorderColor(nextRow + 1, 3, Color.white);
                    subTable.setColBorderColor(nextRow + 1, 4, Color.white);
                    subTable.setColBorderColor(nextRow + 1, 5, Color.white);
                    subTable.setColBorderColor(nextRow + 1, 6, Color.white);
                    subTable.setColBorderColor(nextRow + 1, 7, Color.white);
                    subTable.setColBorderColor(nextRow + 1, 8, Color.white);
                    subTable.setColBorderColor(nextRow + 1, 9, Color.white);
                    subTable.setColBorderColor(nextRow + 1, 10, Color.white);
                    subTable.setColBorderColor(nextRow + 1, 11, Color.white);
                  } 
                  body = new SectionBand(report);
                  double lfLineCount = 1.5D;
                  if (extraLines > 0) {
                    body.setHeight(1.0F);
                  } else {
                    body.setHeight(1.0F);
                  } 
                  if (extraLines > 3 || 
                    CRDcom.length() > 10 || FMcom.length() > 10 || 
                    MAPcom.length() > 10 || BOMcom.length() > 10 || 
                    DJcom.length() > 10 || LFScom.length() > 10 || 
                    MCcom.length() > 10 || PSDcom.length() > 10) {
                    if (lfLineCount < extraLines * 0.3D)
                      lfLineCount = extraLines * 0.3D; 
                    if (lfLineCount < (CRDcom.length() / 8) * 0.3D)
                      lfLineCount = (CRDcom.length() / 8) * 0.3D; 
                    if (lfLineCount < (BOMcom.length() / 8) * 0.3D)
                      lfLineCount = (BOMcom.length() / 8) * 0.3D; 
                    if (lfLineCount < (FMcom.length() / 8) * 0.3D)
                      lfLineCount = (FMcom.length() / 8) * 0.3D; 
                    if (lfLineCount < (MCcom.length() / 8) * 0.3D)
                      lfLineCount = (MCcom.length() / 8) * 0.3D; 
                    if (lfLineCount < (CRDcom.length() / 8) * 0.3D)
                      lfLineCount = (CRDcom.length() / 8) * 0.3D; 
                    if (lfLineCount < (MAPcom.length() / 8) * 0.3D)
                      lfLineCount = (MAPcom.length() / 8) * 0.3D; 
                    if (lfLineCount < (PSDcom.length() / 8) * 0.3D)
                      lfLineCount = (PSDcom.length() / 8) * 0.3D; 
                    if (lfLineCount < (DJcom.length() / 8) * 0.3D)
                      lfLineCount = (DJcom.length() / 8) * 0.3D; 
                    if (lfLineCount < (LFScom.length() / 8) * 0.3D)
                      lfLineCount = (LFScom.length() / 8) * 0.3D; 
                    body.setHeight((float)lfLineCount);
                  } else {
                    body.setHeight(1.0F);
                  } 
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
            } 
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
  
  public static String removeLF(String theString, int maxChars) { return theString.replace('\n', ' '); }
  
  private static void setColBorderColor(DefaultTableLens table, int row, int columns, Color color) {
    for (int i = -1; i < columns; i++)
      table.setColBorderColor(row, i, color); 
  }
}


/* Location:              D:\Documents\NetBeansProjects\milestone2 Local.war!\WEB-INF\classes\co\\universal\milestone\MCAProductionScheduleForPrintSubHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */