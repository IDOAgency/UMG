package WEB-INF.classes.com.universal.milestone;

import com.techempower.ComponentLog;
import com.techempower.gemini.Context;
import com.techempower.gemini.GeminiApplication;
import com.universal.milestone.Form;
import com.universal.milestone.IgaProductionScheduleUpdateForPrintSubHandler;
import com.universal.milestone.MilestoneHelper;
import com.universal.milestone.MilestoneHelper_2;
import com.universal.milestone.MonthYearComparator;
import com.universal.milestone.Schedule;
import com.universal.milestone.ScheduledTask;
import com.universal.milestone.SecureHandler;
import com.universal.milestone.Selection;
import com.universal.milestone.SelectionFlArtistComparator;
import com.universal.milestone.SelectionManager;
import com.universal.milestone.SelectionSelectionNumberComparator;
import com.universal.milestone.SelectionStatus;
import com.universal.milestone.SelectionTitleComparator;
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

public class IgaProductionScheduleUpdateForPrintSubHandler extends SecureHandler {
  public static final String COMPONENT_CODE = "hIga";
  
  public GeminiApplication application;
  
  public ComponentLog log;
  
  public IgaProductionScheduleUpdateForPrintSubHandler(GeminiApplication application) {
    this.application = application;
    this.log = application.getLog("hIga");
  }
  
  public String getDescription() { return "Sub Report"; }
  
  protected static void fillIgaProductionScheduleForPrint(XStyleSheet report, Context context) {
    try {
      HttpServletResponse sresponse = context.getResponse();
      context.putDelivery("status", new String("start_gathering"));
      context.putDelivery("percent", new String("10"));
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
    Color SHADED_AREA_COLOR = Color.lightGray;
    int COL_LINE_STYLE = 4097;
    int HEADER_FONT_SIZE = 12;
    int NUM_COLUMNS = 12;
    double ldLineVal = 0.3D;
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
    try {
      DefaultTableLens table_contents = null;
      DefaultTableLens rowCountTable = null;
      DefaultTableLens columnHeaderTable = null;
      DefaultTableLens subTable = null;
      DefaultTableLens monthTableLens = null;
      DefaultTableLens dateTableLens = null;
      rowCountTable = new DefaultTableLens(2, 10000);
      Form reportForm = (Form)context.getSessionValue("reportForm");
      Calendar beginStDate = (reportForm.getStringValue("beginDate") != null && 
        reportForm.getStringValue("beginDate").length() > 0) ? 
        MilestoneHelper.getDate(reportForm.getStringValue("beginDate")) : null;
      Calendar endStDate = (reportForm.getStringValue("endDate") != null && 
        reportForm.getStringValue("endDate").length() > 0) ? 
        MilestoneHelper.getDate(reportForm.getStringValue("endDate")) : null;
      report.setElement("crs_startdate", MilestoneHelper.getFormatedDate(beginStDate));
      report.setElement("crs_enddate", MilestoneHelper.getFormatedDate(endStDate));
      SimpleDateFormat formatter = new SimpleDateFormat("MMMM d, yyyy");
      String todayLong = formatter.format(new Date());
      report.setElement("crs_bottomdate", todayLong);
      Hashtable selTable = groupSelections(selections);
      Enumeration configs = selTable.keys();
      Vector configVector = new Vector();
      while (configs.hasMoreElements())
        configVector.addElement(configs.nextElement()); 
      Collections.sort(configVector);
      if (configVector.size() > 1) {
        String subConfig1 = (String)configVector.elementAt(0);
        String subConfig2 = (String)configVector.elementAt(1);
        if (subConfig1 != null && subConfig2 != null)
          if (subConfig1.startsWith("1") && subConfig2.startsWith("7")) {
            configVector.set(0, subConfig2);
            configVector.set(1, subConfig1);
          }  
      } 
      int numSubconfigs = 0;
      int numMonths = 0;
      int numDates = 0;
      int numSelections = 0;
      if (selTable != null) {
        numSubconfigs = selTable.size();
        Enumeration subconfigs = selTable.keys();
        while (subconfigs.hasMoreElements()) {
          String subconfig = (String)subconfigs.nextElement();
          Hashtable monthTable = (Hashtable)selTable.get(subconfig);
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
        } 
      } 
      int numRows = 0;
      numRows += numSubconfigs * 2;
      numRows += numMonths * 2;
      numRows += numDates * 4;
      numRows += numSelections * 2;
      int nextRow = 0;
      for (int n = 0; n < configVector.size(); n++) {
        String config = (String)configVector.elementAt(n);
        String configHeaderText = !config.trim().equals("") ? config : "Other";
        hbandType = new SectionBand(report);
        hbandType.setHeight(0.95F);
        hbandType.setShrinkToFit(true);
        hbandType.setVisible(true);
        table_contents = new DefaultTableLens(1, 12);
        table_contents.setHeaderRowCount(0);
        table_contents.setColWidth(0, 270);
        table_contents.setColWidth(1, 135);
        table_contents.setColWidth(2, 127);
        table_contents.setColWidth(3, 65);
        table_contents.setColWidth(4, 118);
        table_contents.setColWidth(5, 90);
        table_contents.setColWidth(6, 85);
        table_contents.setColWidth(7, 85);
        table_contents.setColWidth(8, 85);
        table_contents.setColWidth(9, 85);
        table_contents.setColWidth(10, 85);
        table_contents.setColWidth(11, 220);
        table_contents.setRowFont(0, new Font("Arial", 1, 8));
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
        table_contents.setColBorder(nextRow, 12, 266240);
        table_contents.setColBorder(nextRow, 11, 266240);
        table_contents.setRowBorder(nextRow, 266240);
        table_contents.setRowBorderColor(nextRow - 1, Color.black);
        table_contents.setColBorderColor(nextRow, -1, Color.black);
        table_contents.setColBorderColor(nextRow, 0, Color.black);
        table_contents.setColBorderColor(nextRow, 12, Color.black);
        table_contents.setColBorderColor(nextRow, 11, Color.black);
        table_contents.setRowBorderColor(nextRow, Color.black);
        hbandType.addTable(table_contents, new Rectangle(0, 0, 800, 25));
        nextRow = 0;
        columnHeaderTable = new DefaultTableLens(1, 12);
        columnHeaderTable.setHeaderRowCount(0);
        columnHeaderTable.setColWidth(0, 270);
        columnHeaderTable.setColWidth(1, 135);
        columnHeaderTable.setColWidth(2, 127);
        columnHeaderTable.setColWidth(3, 65);
        columnHeaderTable.setColWidth(4, 118);
        columnHeaderTable.setColWidth(5, 90);
        columnHeaderTable.setColWidth(6, 85);
        columnHeaderTable.setColWidth(7, 85);
        columnHeaderTable.setColWidth(8, 85);
        columnHeaderTable.setColWidth(9, 85);
        columnHeaderTable.setColWidth(10, 85);
        columnHeaderTable.setColWidth(11, 220);
        columnHeaderTable.setRowBorderColor(nextRow - 1, SHADED_AREA_COLOR);
        columnHeaderTable.setRowBorderColor(nextRow, SHADED_AREA_COLOR);
        columnHeaderTable.setRowBorderColor(nextRow + 1, SHADED_AREA_COLOR);
        columnHeaderTable.setRowHeight(nextRow, 100);
        columnHeaderTable.setColBorderColor(-1, Color.lightGray);
        columnHeaderTable.setColBorderColor(0, Color.lightGray);
        columnHeaderTable.setColBorderColor(1, Color.lightGray);
        columnHeaderTable.setColBorderColor(2, Color.lightGray);
        columnHeaderTable.setColBorderColor(3, Color.lightGray);
        columnHeaderTable.setColBorderColor(4, Color.lightGray);
        columnHeaderTable.setColBorderColor(5, Color.lightGray);
        columnHeaderTable.setColBorderColor(6, Color.lightGray);
        columnHeaderTable.setColBorderColor(7, Color.lightGray);
        columnHeaderTable.setColBorderColor(8, Color.lightGray);
        columnHeaderTable.setColBorderColor(9, Color.lightGray);
        columnHeaderTable.setColBorderColor(10, Color.lightGray);
        columnHeaderTable.setColBorderColor(11, Color.lightGray);
        columnHeaderTable.setColBorderColor(12, Color.lightGray);
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
        columnHeaderTable.setAlignment(nextRow, 11, 33);
        columnHeaderTable.setRowFont(nextRow, new Font("Arial", 1, 9));
        columnHeaderTable.setObject(nextRow, 0, "Artist/Title/UPC #");
        columnHeaderTable.setObject(nextRow, 1, "MM/Prod.");
        columnHeaderTable.setObject(nextRow, 2, "Catalog #/\nConfig/Label/\nImprint");
        columnHeaderTable.setObject(nextRow, 3, "List\nPrice");
        columnHeaderTable.setObject(nextRow, 4, "Packaging\nSpecs");
        columnHeaderTable.setObject(nextRow, 5, "Credits\nDue");
        columnHeaderTable.setObject(nextRow, 6, "Art\nDue");
        columnHeaderTable.setObject(nextRow, 7, "Pkging\nDue");
        columnHeaderTable.setObject(nextRow, 8, "Master\nShips");
        columnHeaderTable.setObject(nextRow, 9, "Film\nShips");
        columnHeaderTable.setObject(nextRow, 10, "Plant\nShip\nDate");
        columnHeaderTable.setObject(nextRow, 11, "Comments");
        hbandType.addTable(columnHeaderTable, new Rectangle(0, 24, 800, 37));
        hbandType.setBottomBorder(0);
        Hashtable monthTable = (Hashtable)selTable.get(config);
        if (monthTable != null) {
          Enumeration monthSort = monthTable.keys();
          Vector monthVector = new Vector();
          while (monthSort.hasMoreElements()) {
            String elem = (String)monthSort.nextElement();
            monthVector.add(elem);
          } 
          Object[] monthArray = (Object[])null;
          monthArray = monthVector.toArray();
          Arrays.sort(monthArray, new MonthYearComparator());
          for (int mIndex = 0; mIndex < monthArray.length; mIndex++) {
            String monthName = (String)monthArray[mIndex];
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
            monthTableLens.setObject(nextRow, 0, monthNameString);
            monthTableLens.setRowHeight(nextRow, 14);
            monthTableLens.setRowFont(nextRow, new Font("Arial", 1, 12));
            monthTableLens.setRowBackground(nextRow, SHADED_AREA_COLOR);
            monthTableLens.setRowForeground(nextRow, Color.black);
            monthTableLens.setRowBorderColor(nextRow, Color.white);
            monthTableLens.setRowBorderColor(nextRow - 1, Color.white);
            monthTableLens.setColBorderColor(nextRow, -1, Color.white);
            monthTableLens.setColBorderColor(nextRow, 0, Color.white);
            monthTableLens.setColBorderColor(nextRow, 11, Color.white);
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
              Enumeration dates = dateTable.keys();
              Vector streetVector = new Vector();
              while (dates.hasMoreElements())
                streetVector.addElement(dates.nextElement()); 
              Collections.sort(streetVector, new StringDateComparator());
              for (int z = 0; z < streetVector.size(); z++) {
                String date = (String)streetVector.get(z);
                hbandDate = new SectionBand(report);
                hbandDate.setHeight(0.25F);
                hbandDate.setShrinkToFit(true);
                hbandDate.setVisible(true);
                hbandDate.setBottomBorder(0);
                hbandDate.setLeftBorder(0);
                hbandDate.setRightBorder(0);
                hbandDate.setTopBorder(0);
                dateTableLens = new DefaultTableLens(1, 12);
                nextRow = 0;
                dateTableLens.setSpan(nextRow, 0, new Dimension(12, 1));
                dateTableLens.setObject(nextRow, 0, date);
                dateTableLens.setRowHeight(nextRow, 14);
                dateTableLens.setRowFont(nextRow, new Font("Arial", 1, 12));
                dateTableLens.setRowForeground(nextRow, Color.black);
                dateTableLens.setRowBorderColor(nextRow, Color.white);
                dateTableLens.setRowBorderColor(nextRow - 1, Color.white);
                dateTableLens.setColBorderColor(nextRow, -1, Color.white);
                dateTableLens.setColBorderColor(nextRow, 0, Color.white);
                dateTableLens.setColBorderColor(nextRow, 11, Color.white);
                dateTableLens.setRowBackground(nextRow, Color.white);
                hbandDate.addTable(dateTableLens, new Rectangle(800, 200));
                hbandDate.setBottomBorder(0);
                group = new DefaultSectionLens(null, group, hbandDate);
                selections = (Vector)dateTable.get(date);
                if (selections == null)
                  selections = new Vector(); 
                Object[] selectionsArray = selections.toArray();
                Arrays.sort(selectionsArray, new SelectionSelectionNumberComparator());
                Arrays.sort(selectionsArray, new SelectionTitleComparator());
                Arrays.sort(selectionsArray, new SelectionFlArtistComparator());
                int totalCount = selectionsArray.length;
                int tenth = 0;
                int recordCount = 0;
                int count = 0;
                tenth = totalCount / 10;
                try {
                  HttpServletResponse sresponse = context.getResponse();
                  context.putDelivery("status", new String("start_report"));
                  context.putDelivery("percent", new String("10"));
                  context.includeJSP("status.jsp", "hiddenFrame");
                  sresponse.setContentType("text/plain");
                  sresponse.flushBuffer();
                } catch (Exception exception) {}
                for (int i = 0; i < selectionsArray.length; i++) {
                  try {
                    if (count < recordCount / tenth) {
                      count = recordCount / tenth;
                      HttpServletResponse sresponse = context.getResponse();
                      context.putDelivery("status", new String("start_report"));
                      int myPercent = count * 10;
                      if (myPercent > 90)
                        myPercent = 90; 
                      context.putDelivery("percent", new String(String.valueOf(myPercent)));
                      context.includeJSP("status.jsp", "hiddenFrame");
                      sresponse.setContentType("text/plain");
                      sresponse.flushBuffer();
                    } 
                  } catch (Exception exception) {}
                  recordCount++;
                  Selection sel = (Selection)selectionsArray[i];
                  sel = SelectionManager.getInstance().getSelectionAndSchedule(sel.getSelectionID());
                  nextRow = 0;
                  subTable = new DefaultTableLens(2, 12);
                  subTable.setColWidth(0, 270);
                  subTable.setColWidth(1, 135);
                  subTable.setColWidth(2, 127);
                  subTable.setColWidth(3, 65);
                  subTable.setColWidth(4, 118);
                  subTable.setColWidth(5, 90);
                  subTable.setColWidth(6, 85);
                  subTable.setColWidth(7, 85);
                  subTable.setColWidth(8, 85);
                  subTable.setColWidth(9, 85);
                  subTable.setColWidth(10, 85);
                  subTable.setColWidth(11, 220);
                  subTable.setColBorderColor(-1, Color.lightGray);
                  subTable.setColBorderColor(0, Color.lightGray);
                  subTable.setColBorderColor(1, Color.lightGray);
                  subTable.setColBorderColor(2, Color.lightGray);
                  subTable.setColBorderColor(3, Color.lightGray);
                  subTable.setColBorderColor(4, Color.lightGray);
                  subTable.setColBorderColor(5, Color.lightGray);
                  subTable.setColBorderColor(6, Color.lightGray);
                  subTable.setColBorderColor(7, Color.lightGray);
                  subTable.setColBorderColor(8, Color.lightGray);
                  subTable.setColBorderColor(9, Color.lightGray);
                  subTable.setColBorderColor(10, Color.lightGray);
                  subTable.setColBorderColor(11, Color.lightGray);
                  subTable.setColBorderColor(12, Color.lightGray);
                  String titleId = "";
                  titleId = String.valueOf(sel.getSelectionNo());
                  if (sel.getPrefixID() != null)
                    titleId = String.valueOf(SelectionManager.getLookupObjectValue(sel.getPrefixID())) + " " + titleId; 
                  String artist = "";
                  artist = sel.getFlArtist();
                  String comment = (sel.getSelectionComments() != null) ? sel.getSelectionComments() : "";
                  String pack = "";
                  pack = sel.getSelectionPackaging();
                  String title = sel.getTitle();
                  String upc = "";
                  if (sel.getUpc() != null)
                    upc = sel.getUpc(); 
                  upc = MilestoneHelper_2.getRMSReportFormat(upc, "UPC", sel.getIsDigital());
                  String price = "";
                  if (sel.getPriceCode() != null && sel.getPriceCode().getTotalCost() > 0.0F)
                    price = "$" + MilestoneHelper.formatDollarPrice(sel.getPriceCode().getTotalCost()); 
                  String status = (sel.getSelectionStatus() != null && sel.getSelectionStatus().getName() != null) ? 
                    sel.getSelectionStatus().getName() : "";
                  String mm = "";
                  if (sel.getOtherContact() != null)
                    mm = sel.getOtherContact(); 
                  SelectionManager.getInstance().getSelectionManufacturingSubDetail(sel);
                  String prod = "";
                  if (sel.getLabelContact() != null)
                    prod = sel.getLabelContact().getName(); 
                  String format = "";
                  if (sel.getSelectionConfig() != null)
                    format = sel.getSelectionConfig().getSelectionConfigurationName(); 
                  String label = "";
                  if (sel.getLabel() != null)
                    label = sel.getLabel().getName(); 
                  String imprint = sel.getImprint();
                  Schedule schedule = sel.getSchedule();
                  Vector tasks = (schedule != null) ? schedule.getTasks() : null;
                  ScheduledTask task = null;
                  String credits = "";
                  String art = "";
                  String packing = "";
                  String master = "";
                  String film = "";
                  String plant = "";
                  String creditsCom = "";
                  String artCom = "";
                  String packingCom = "";
                  String masterCom = "";
                  String filmCom = "";
                  String plantCom = "";
                  if (tasks != null)
                    for (int j = 0; j < tasks.size(); j++) {
                      task = (ScheduledTask)tasks.get(j);
                      if (task != null && task.getDueDate() != null) {
                        String taskStatus = task.getScheduledTaskStatus();
                        SimpleDateFormat dueDateFormatter = new SimpleDateFormat("M/dd");
                        String dueDate = String.valueOf(dueDateFormatter.format(task.getDueDate().getTime())) + 
                          " " + MilestoneHelper.getDayType(sel.getCalendarGroup(), task.getDueDate());
                        String completionDate = MilestoneHelper.getFormatedDate(task.getCompletionDate());
                        if (!completionDate.equals(""))
                          completionDate = String.valueOf(completionDate) + "\n"; 
                        if (taskStatus.equalsIgnoreCase("N/A"))
                          completionDate = "N/A\n"; 
                        String taskAbbrev = MilestoneHelper.getTaskAbbreivationNameById(task.getTaskAbbreviationID());
                        String taskComment = "";
                        if (task.getComments() != null && !task.getComments().equals(""))
                          taskComment = task.getComments(); 
                        if (taskAbbrev.equalsIgnoreCase("CRD")) {
                          credits = dueDate;
                          creditsCom = String.valueOf(completionDate) + taskComment;
                        } else if (taskAbbrev.equalsIgnoreCase("CAD")) {
                          art = dueDate;
                          artCom = String.valueOf(completionDate) + taskComment;
                        } else if (taskAbbrev.equalsIgnoreCase("FPC")) {
                          packing = dueDate;
                          packingCom = String.valueOf(completionDate) + taskComment;
                        } else if (taskAbbrev.equalsIgnoreCase("TPS")) {
                          master = dueDate;
                          masterCom = String.valueOf(completionDate) + taskComment;
                        } else if (taskAbbrev.equalsIgnoreCase("LFS")) {
                          film = dueDate;
                          filmCom = String.valueOf(completionDate) + taskComment;
                        } else if (taskAbbrev.equalsIgnoreCase("PSD")) {
                          plant = dueDate;
                          plantCom = String.valueOf(completionDate) + taskComment;
                        } 
                        task = null;
                      } 
                    }  
                  String vSpacer = "";
                  String[] bigString = { String.valueOf(artist) + "\n" + title, 
                      comment, 
                      String.valueOf(titleId) + "\n" + format + "\n" + label + "\n" + imprint + "\n\n\n" };
                  int[] bigLines = { 36, 
                      25, 
                      25 };
                  int maxLines = 0;
                  maxLines = MilestoneHelper.lineCountWCR(bigString, bigLines);
                  maxLines = (maxLines > 2) ? --maxLines : maxLines;
                  if (pack.length() < 12) {
                    for (int k = (comment.length() == 0) ? 2 : 1; k < maxLines; k++)
                      vSpacer = String.valueOf(vSpacer) + "\n"; 
                  } else {
                    int k = pack.length() / 15;
                    if (k >= maxLines)
                      maxLines = k + 1; 
                    if (creditsCom.length() == 0 && artCom.length() == 0 && 
                      packingCom.length() == 0 && masterCom.length() == 0 && 
                      filmCom.length() == 0 && plantCom.length() == 0 && 
                      comment.length() == 0)
                      k += 2; 
                    for (; k < maxLines; k++)
                      vSpacer = String.valueOf(vSpacer) + "\n"; 
                  } 
                  subTable.setRowBorderColor(nextRow - 1, Color.lightGray);
                  subTable.setRowBorderColor(nextRow, Color.lightGray);
                  subTable.setRowBorderColor(nextRow + 1, Color.lightGray);
                  subTable.setRowInsets(nextRow, new Insets(0, 0, 0, 0));
                  subTable.setRowFont(nextRow + 1, new Font("Arial", 0, 8));
                  subTable.setObject(nextRow, 0, String.valueOf(artist) + "\n" + title + "\n" + upc);
                  subTable.setBackground(nextRow, 0, Color.white);
                  subTable.setSpan(nextRow, 0, new Dimension(1, 2));
                  subTable.setAlignment(nextRow, 0, 9);
                  mm = String.valueOf(mm) + "\n";
                  subTable.setObject(nextRow, 1, String.valueOf(mm) + prod);
                  subTable.setBackground(nextRow, 1, Color.white);
                  subTable.setSpan(nextRow, 1, new Dimension(1, 2));
                  subTable.setAlignment(nextRow, 1, 9);
                  subTable.setObject(nextRow, 2, String.valueOf(titleId) + "\n" + format + "\n" + label + "\n" + imprint);
                  subTable.setBackground(nextRow, 2, Color.white);
                  subTable.setSpan(nextRow, 2, new Dimension(1, 2));
                  subTable.setAlignment(nextRow, 2, 10);
                  subTable.setObject(nextRow, 3, price);
                  subTable.setBackground(nextRow, 3, Color.white);
                  subTable.setSpan(nextRow, 3, new Dimension(1, 2));
                  subTable.setAlignment(nextRow, 3, 12);
                  subTable.setObject(nextRow, 4, "Due Dates");
                  subTable.setColBorderColor(nextRow, 4, Color.lightGray);
                  subTable.setColBorder(nextRow, 4, 266240);
                  subTable.setFont(nextRow, 4, new Font("Arial", 1, 9));
                  subTable.setAlignment(nextRow, 4, 34);
                  subTable.setAlignment(nextRow + 1, 4, 9);
                  subTable.setObject(nextRow + 1, 4, String.valueOf(pack) + vSpacer);
                  subTable.setRowHeight(nextRow, 10);
                  subTable.setRowHeight(nextRow, 10);
                  subTable.setObject(nextRow, 5, credits);
                  subTable.setColBorderColor(nextRow, 5, Color.lightGray);
                  subTable.setColBorder(nextRow, 5, 266240);
                  subTable.setFont(nextRow, 5, new Font("Arial", 1, 8));
                  subTable.setAlignment(nextRow, 5, 10);
                  subTable.setObject(nextRow + 1, 5, creditsCom);
                  subTable.setAlignment(nextRow + 1, 5, 10);
                  subTable.setObject(nextRow, 6, art);
                  subTable.setColBorderColor(nextRow, 6, Color.lightGray);
                  subTable.setColBorder(nextRow, 6, 266240);
                  subTable.setAlignment(nextRow, 6, 10);
                  subTable.setAlignment(nextRow + 1, 6, 10);
                  subTable.setObject(nextRow + 1, 6, artCom);
                  subTable.setFont(nextRow, 6, new Font("Arial", 1, 8));
                  subTable.setObject(nextRow, 7, packing);
                  subTable.setColBorderColor(nextRow, 7, Color.lightGray);
                  subTable.setColBorder(nextRow, 7, 266240);
                  subTable.setFont(nextRow, 7, new Font("Arial", 1, 8));
                  subTable.setAlignment(nextRow, 7, 10);
                  subTable.setObject(nextRow + 1, 7, packingCom);
                  subTable.setAlignment(nextRow + 1, 7, 10);
                  subTable.setObject(nextRow, 8, master);
                  subTable.setColBorderColor(nextRow, 8, Color.lightGray);
                  subTable.setColBorder(nextRow, 8, 266240);
                  subTable.setFont(nextRow, 8, new Font("Arial", 1, 8));
                  subTable.setAlignment(nextRow, 8, 10);
                  subTable.setObject(nextRow + 1, 8, masterCom);
                  subTable.setAlignment(nextRow + 1, 8, 10);
                  subTable.setObject(nextRow, 9, film);
                  subTable.setColBorderColor(nextRow, 9, Color.lightGray);
                  subTable.setColBorder(nextRow, 9, 266240);
                  subTable.setFont(nextRow, 9, new Font("Arial", 1, 8));
                  subTable.setAlignment(nextRow, 9, 2);
                  subTable.setAlignment(nextRow, 9, 10);
                  subTable.setObject(nextRow + 1, 9, filmCom);
                  subTable.setAlignment(nextRow + 1, 9, 10);
                  subTable.setObject(nextRow, 10, plant);
                  subTable.setColBorderColor(nextRow, 10, Color.lightGray);
                  subTable.setColBorder(nextRow, 10, 266240);
                  subTable.setFont(nextRow, 10, new Font("Arial", 1, 8));
                  subTable.setAlignment(nextRow, 10, 10);
                  subTable.setObject(nextRow + 1, 10, plantCom);
                  subTable.setAlignment(nextRow + 1, 10, 10);
                  subTable.setRowBackground(nextRow, Color.lightGray);
                  subTable.setRowForeground(nextRow, Color.black);
                  subTable.setBackground(nextRow, 11, Color.white);
                  subTable.setSpan(nextRow, 11, new Dimension(1, 2));
                  subTable.setObject(nextRow, 11, comment);
                  subTable.setAlignment(nextRow, 11, 9);
                  subTable.setRowFont(nextRow, new Font("Arial", 0, 8));
                  Font holidayFont = new Font("Arial", 3, 8);
                  for (int colIdx = 5; colIdx <= 10; colIdx++) {
                    String dueDate = subTable.getObject(nextRow, colIdx).toString();
                    if (dueDate != null && dueDate.length() > 0) {
                      char lastChar = dueDate.charAt(dueDate.length() - 1);
                      if (Character.isLetter(lastChar))
                        subTable.setFont(nextRow, colIdx, holidayFont); 
                    } 
                  } 
                  body = new SectionBand(report);
                  double lfLineCount = 1.5D;
                  if (maxLines > 0) {
                    body.setHeight(1.5F);
                  } else {
                    body.setHeight(1.0F);
                  } 
                  if (maxLines > 3 || 
                    creditsCom.length() > 10 || artCom.length() > 10 || 
                    packingCom.length() > 10 || masterCom.length() > 10 || 
                    filmCom.length() > 10 || plantCom.length() > 10) {
                    if (lfLineCount < maxLines * 0.3D)
                      lfLineCount = maxLines * 0.3D; 
                    if (lfLineCount < (creditsCom.length() / 7) * 0.3D)
                      lfLineCount = (creditsCom.length() / 7) * 0.3D; 
                    if (lfLineCount < (artCom.length() / 8) * 0.3D)
                      lfLineCount = (artCom.length() / 8) * 0.3D; 
                    if (lfLineCount < (packingCom.length() / 8) * 0.3D)
                      lfLineCount = (packingCom.length() / 8) * 0.3D; 
                    if (lfLineCount < (masterCom.length() / 8) * 0.3D)
                      lfLineCount = (masterCom.length() / 8) * 0.3D; 
                    if (lfLineCount < (filmCom.length() / 8) * 0.3D)
                      lfLineCount = (filmCom.length() / 8) * 0.3D; 
                    if (lfLineCount < (plantCom.length() / 8) * 0.3D)
                      lfLineCount = (plantCom.length() / 8) * 0.3D; 
                    body.setHeight((float)lfLineCount);
                  } else {
                    body.setHeight(1.0F);
                  } 
                  body.addTable(subTable, new Rectangle(800, 800));
                  body.setBottomBorder(0);
                  body.setTopBorder(0);
                  body.setShrinkToFit(true);
                  body.setVisible(true);
                  group = new DefaultSectionLens(null, group, body);
                } 
                group = new DefaultSectionLens(null, group, spacer);
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
      System.out.println(">>>>>>>>ReportHandler(): exception: " + e);
    } 
  }
  
  public static Hashtable groupSelections(Vector selections) {
    Hashtable allSelections = new Hashtable();
    if (selections == null)
      return allSelections; 
    for (int i = 0; i < selections.size(); i++) {
      Selection sel = (Selection)selections.elementAt(i);
      if (sel != null) {
        sel = SelectionManager.getInstance().getSelectionAndSchedule(sel.getSelectionID());
        String configIDString = "", configString = "", dateString = "";
        configIDString = (sel.getSelectionSubConfig().getSelectionSubConfigurationAbbreviation() != null) ? sel.getSelectionSubConfig().getSelectionSubConfigurationAbbreviation() : "";
        SelectionStatus status = sel.getSelectionStatus();
        String statusString = "";
        if (status != null)
          statusString = (status.getName() == null) ? "" : status.getName(); 
        dateString = (sel.getStreetDate() != null) ? MilestoneHelper.getFormatedDate(sel.getStreetDate()) : "No street date";
        String typeString = (sel.getReleaseType() != null) ? sel.getReleaseType().getName() : "";
        if (typeString.trim().toUpperCase().startsWith("PROMO")) {
          configIDString = "Promos";
        } else if (configIDString != null) {
          configIDString = configIDString.trim();
          configIDString = configIDString.toUpperCase();
          if (configIDString.equals("ALBUM") || configIDString.equals("CASS") || 
            configIDString.equals("CD") || configIDString.equals("ECD") || 
            configIDString.equals("CASSEP") || configIDString.equals("CDEP") || 
            configIDString.equals("ECDEP") || configIDString.equals("DVDAUD") || configIDString.startsWith("CDADVD") || configIDString.startsWith("MIXED") || 
            configIDString.equals("DVDVID") || configIDString.equals("VIDEO") || configIDString.startsWith("SACD") || configIDString.startsWith("DUALDISC") || 
            configIDString.equals("UMD") || configIDString.equals("UMDFL")) {
            configIDString = "Commercial Full Length";
          } else {
            configIDString = "Commercial Singles";
          } 
        } 
        String monthString = "";
        if (sel.getStreetDate() != null && !statusString.equalsIgnoreCase("TBS") && !statusString.equalsIgnoreCase("In the Works")) {
          SimpleDateFormat dueDateFormatter = new SimpleDateFormat("MM/yyyy");
          monthString = dueDateFormatter.format(sel.getStreetDate().getTime());
        } 
        if (statusString.equalsIgnoreCase("TBS")) {
          monthString = "13";
        } else if (statusString.equalsIgnoreCase("In The Works")) {
          monthString = "26";
        } else if (monthString.length() < 1) {
          monthString = "52";
        } 
        Hashtable configSubTable = (Hashtable)allSelections.get(configIDString);
        if (configSubTable == null) {
          configSubTable = new Hashtable();
          allSelections.put(configIDString, configSubTable);
        } 
        Hashtable monthsTable = (Hashtable)configSubTable.get(monthString);
        if (monthsTable == null) {
          monthsTable = new Hashtable();
          configSubTable.put(monthString, monthsTable);
        } 
        Vector selectionsForDate = (Vector)monthsTable.get(dateString);
        if (selectionsForDate == null) {
          selectionsForDate = new Vector();
          monthsTable.put(dateString, selectionsForDate);
        } 
        selectionsForDate.addElement(sel);
      } 
    } 
    return allSelections;
  }
}


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\IgaProductionScheduleUpdateForPrintSubHandler.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */