package WEB-INF.classes.com.universal.milestone;

import com.techempower.ComponentLog;
import com.techempower.gemini.Context;
import com.techempower.gemini.GeminiApplication;
import com.universal.milestone.DatePeriod;
import com.universal.milestone.Form;
import com.universal.milestone.IdjProductionScheduleForPrintSubHandler;
import com.universal.milestone.MilestoneHelper;
import com.universal.milestone.MonthYearComparator;
import com.universal.milestone.Schedule;
import com.universal.milestone.ScheduledTask;
import com.universal.milestone.SecureHandler;
import com.universal.milestone.Selection;
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

public class IdjProductionScheduleForPrintSubHandler extends SecureHandler {
  public static final String COMPONENT_CODE = "hPsp";
  
  public GeminiApplication application;
  
  public ComponentLog log;
  
  public IdjProductionScheduleForPrintSubHandler(GeminiApplication application) {
    this.application = application;
    this.log = application.getLog("hPsp");
  }
  
  public String getDescription() { return "Sub Report"; }
  
  protected static void fillIdjProductionScheduleForPrint(XStyleSheet report, Context context) {
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
    int NUM_COLUMNS = 15;
    Color SHADED_AREA_COLOR = Color.lightGray;
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
    Hashtable selTable = MilestoneHelper.groupSelectionsByTypeConfigAndStreetDate(selections);
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
          Object[] monthArrayC = (Object[])null;
          monthArrayC = monthVectorC.toArray();
          totalCount += monthArrayC.length;
        } 
        System.out.println(">>>>>>>>>>>>>>>>>>>>   Total Count = " + totalCount);
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
        SimpleDateFormat formatter = new SimpleDateFormat("MMMM d, yyyy");
        String todayLong = formatter.format(new Date());
        report.setElement("crs_bottomdate", todayLong);
        String config = (configVector.elementAt(n) != null) ? (String)configVector.elementAt(n) : "";
        Hashtable monthTable = (Hashtable)selTable.get(config);
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
        table_contents = new DefaultTableLens(1, 15);
        table_contents.setHeaderRowCount(0);
        table_contents.setColWidth(0, 259);
        table_contents.setColWidth(1, 157);
        table_contents.setColWidth(2, 150);
        table_contents.setColWidth(3, 80);
        table_contents.setColWidth(4, 168);
        table_contents.setColWidth(5, 87);
        table_contents.setColWidth(6, 84);
        table_contents.setColWidth(7, 70);
        table_contents.setColWidth(8, 80);
        table_contents.setColWidth(9, 72);
        table_contents.setColWidth(10, 90);
        table_contents.setColWidth(11, 70);
        table_contents.setColWidth(12, 90);
        table_contents.setColWidth(13, 77);
        table_contents.setColWidth(14, 180);
        table_contents.setColBorderColor(Color.black);
        table_contents.setRowBorderColor(Color.black);
        table_contents.setRowBorder(4097);
        table_contents.setColBorder(4097);
        int nextRow = 0;
        String configHeaderText = !config.trim().equals("") ? config : "Other";
        if (configHeaderText != null)
          if (configHeaderText.startsWith("Commercial CD Single")) {
            configHeaderText = "Commercial Singles";
          } else if (configHeaderText.startsWith("Promotional CD Single")) {
            configHeaderText = "Promos Singles";
          }  
        table_contents.setSpan(nextRow, 0, new Dimension(15, 1));
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
        table_contents.setColBorder(nextRow, 14, 266240);
        table_contents.setColBorder(nextRow, 15, 266240);
        table_contents.setRowBorder(nextRow, 266240);
        table_contents.setRowBorderColor(nextRow - 1, Color.black);
        table_contents.setColBorderColor(nextRow, -1, Color.black);
        table_contents.setColBorderColor(nextRow, 0, Color.black);
        table_contents.setColBorderColor(nextRow, 14, Color.black);
        table_contents.setColBorderColor(nextRow, 15, Color.black);
        table_contents.setRowBorderColor(nextRow, Color.black);
        hbandType.addTable(table_contents, new Rectangle(0, 0, 800, 25));
        nextRow = 0;
        columnHeaderTable = new DefaultTableLens(1, 15);
        columnHeaderTable.setHeaderRowCount(0);
        columnHeaderTable.setColWidth(0, 259);
        columnHeaderTable.setColWidth(1, 157);
        columnHeaderTable.setColWidth(2, 150);
        columnHeaderTable.setColWidth(3, 80);
        columnHeaderTable.setColWidth(4, 168);
        columnHeaderTable.setColWidth(5, 87);
        columnHeaderTable.setColWidth(6, 84);
        columnHeaderTable.setColWidth(7, 70);
        columnHeaderTable.setColWidth(8, 80);
        columnHeaderTable.setColWidth(9, 72);
        columnHeaderTable.setColWidth(10, 90);
        columnHeaderTable.setColWidth(11, 70);
        columnHeaderTable.setColWidth(12, 90);
        columnHeaderTable.setColWidth(13, 77);
        columnHeaderTable.setColWidth(14, 180);
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
        columnHeaderTable.setObject(nextRow, 0, "Artist/Title");
        columnHeaderTable.setObject(nextRow, 1, "Packaging\nSpecs");
        columnHeaderTable.setObject(nextRow, 2, "Selection\nUPC\nConfig");
        columnHeaderTable.setObject(nextRow, 3, "Price\nCode\nUnits");
        columnHeaderTable.setObject(nextRow, 4, "Label & Contacts");
        columnHeaderTable.setObject(nextRow, 5, "Prod\nReq\nDue");
        columnHeaderTable.setObject(nextRow, 6, "Readers\nCirc");
        columnHeaderTable.setObject(nextRow, 7, "BOM");
        columnHeaderTable.setObject(nextRow, 8, "Film\nShips");
        columnHeaderTable.setObject(nextRow, 9, "Parts\nOrder");
        columnHeaderTable.setObject(nextRow, 10, "Masters\nShip");
        columnHeaderTable.setObject(nextRow, 11, "Manf\nCopy\nDue");
        columnHeaderTable.setObject(nextRow, 12, "Tests\nApproved");
        columnHeaderTable.setObject(nextRow, 13, "Finish\nGoods\nDepot");
        columnHeaderTable.setObject(nextRow, 14, "Comments");
        setColBorderColor(columnHeaderTable, nextRow, 15, SHADED_AREA_COLOR);
        columnHeaderTable.setRowBorderColor(nextRow - 1, SHADED_AREA_COLOR);
        columnHeaderTable.setRowBorderColor(nextRow, SHADED_AREA_COLOR);
        columnHeaderTable.setRowFont(nextRow, new Font("Arial", 1, 8));
        columnHeaderTable.setRowBackground(nextRow, Color.white);
        columnHeaderTable.setRowForeground(nextRow, Color.black);
        hbandType.addTable(columnHeaderTable, new Rectangle(0, 24, 800, 39));
        hbandType.setBottomBorder(0);
        if (monthTable != null) {
          Enumeration months = monthTable.keys();
          Vector monthVector = new Vector();
          while (months.hasMoreElements())
            monthVector.add((String)months.nextElement()); 
          Object[] monthArray = (Object[])null;
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
            monthTableLens = new DefaultTableLens(1, 15);
            hbandCategory = new SectionBand(report);
            hbandCategory.setHeight(0.25F);
            hbandCategory.setShrinkToFit(true);
            hbandCategory.setVisible(true);
            hbandCategory.setBottomBorder(0);
            hbandCategory.setLeftBorder(0);
            hbandCategory.setRightBorder(0);
            hbandCategory.setTopBorder(0);
            nextRow = 0;
            monthTableLens.setSpan(nextRow, 0, new Dimension(15, 1));
            monthTableLens.setObject(nextRow, 0, monthNameString);
            monthTableLens.setRowHeight(nextRow, 14);
            monthTableLens.setRowFont(nextRow, new Font("Arial", 1, 12));
            monthTableLens.setRowBackground(nextRow, SHADED_AREA_COLOR);
            monthTableLens.setRowForeground(nextRow, Color.black);
            monthTableLens.setRowBorderColor(nextRow, Color.white);
            monthTableLens.setRowBorderColor(nextRow - 1, Color.white);
            monthTableLens.setColBorderColor(nextRow, -1, Color.white);
            monthTableLens.setColBorderColor(nextRow, 0, Color.white);
            monthTableLens.setColBorderColor(nextRow, 14, Color.white);
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
              Object[] dateArray = (Object[])null;
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
                String cycle = "";
                try {
                  Calendar calanderDate = MilestoneHelper.getDate(dateNameText);
                  DatePeriod datePeriod = MilestoneHelper.findDatePeriod(calanderDate);
                  cycle = " " + datePeriod.getCycle();
                } catch (Exception exception) {}
                hbandDate = new SectionBand(report);
                hbandDate.setHeight(0.25F);
                hbandDate.setShrinkToFit(true);
                hbandDate.setVisible(true);
                hbandDate.setBottomBorder(0);
                hbandDate.setLeftBorder(0);
                hbandDate.setRightBorder(0);
                hbandDate.setTopBorder(0);
                dateTableLens = new DefaultTableLens(1, 15);
                nextRow = 0;
                dateTableLens.setSpan(nextRow, 0, new Dimension(15, 1));
                dateTableLens.setObject(nextRow, 0, String.valueOf(dateNameText) + cycle);
                dateTableLens.setRowHeight(nextRow, 14);
                dateTableLens.setRowFont(nextRow, new Font("Arial", 1, 12));
                dateTableLens.setRowForeground(nextRow, Color.black);
                dateTableLens.setRowBorderColor(nextRow, Color.white);
                dateTableLens.setRowBorderColor(nextRow - 1, Color.white);
                dateTableLens.setColBorderColor(nextRow, -1, Color.white);
                dateTableLens.setColBorderColor(nextRow, 0, Color.white);
                dateTableLens.setColBorderColor(nextRow, 14, Color.white);
                dateTableLens.setRowBackground(nextRow, Color.white);
                hbandDate.addTable(dateTableLens, new Rectangle(800, 200));
                hbandDate.setBottomBorder(0);
                group = new DefaultSectionLens(null, group, hbandDate);
                selections = (Vector)dateTable.get(dateName);
                if (selections == null)
                  selections = new Vector(); 
                MilestoneHelper.setSelectionSorting(selections, 14);
                Collections.sort(selections);
                MilestoneHelper.setSelectionSorting(selections, 4);
                Collections.sort(selections);
                MilestoneHelper.setSelectionSorting(selections, 3);
                Collections.sort(selections);
                MilestoneHelper.setSelectionSorting(selections, 9);
                Collections.sort(selections);
                MilestoneHelper.setSelectionSorting(selections, 8);
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
                  System.out.println(">>>>>>>>>>>>>>>>>>>>   tenth = " + tenth);
                  System.out.println(">>>>>>>>>>>>>>>>>>>>   count = " + count);
                  System.out.println(">>>>>>>>>>>>>>>>>>>>   recordCount = " + recordCount);
                  String titleId = "";
                  titleId = sel.getTitleID();
                  String artist = "";
                  artist = sel.getArtist().trim();
                  String comment = (sel.getSelectionComments() != null) ? sel.getSelectionComments() : "";
                  String label = "";
                  if (sel.getLabel() != null)
                    label = sel.getLabel().getName(); 
                  String pack = "";
                  pack = sel.getSelectionPackaging();
                  String title = "";
                  if (sel.getTitle() != null)
                    title = sel.getTitle(); 
                  String upc = "";
                  upc = sel.getUpc();
                  if (upc != null && upc.length() == 0)
                    upc = titleId; 
                  String selConfig = "";
                  selConfig = (sel.getSelectionConfig() != null) ? sel.getSelectionConfig().getSelectionConfigurationAbbreviation() : "";
                  String selSubConfig = "";
                  selSubConfig = (sel.getSelectionSubConfig() != null) ? sel.getSelectionSubConfig().getSelectionSubConfigurationAbbreviation() : "";
                  String units = "";
                  units = (sel.getNumberOfUnits() > 0) ? String.valueOf(sel.getNumberOfUnits()) : "";
                  String code = (sel.getSellCode() != null) ? sel.getSellCode() : "";
                  if (code != null && code.startsWith("-1"))
                    code = ""; 
                  String retail = "";
                  if (sel.getPriceCode() != null && sel.getPriceCode().getRetailCode() != null)
                    retail = sel.getPriceCode().getRetailCode(); 
                  if (code.length() > 0)
                    retail = "/" + retail; 
                  String price = "";
                  if (sel.getPriceCode() != null && sel.getPriceCode().getTotalCost() > 0.0F)
                    price = "$" + MilestoneHelper.formatDollarPrice(sel.getPriceCode().getTotalCost()); 
                  String contact = "";
                  contact = (sel.getLabelContact() != null) ? sel.getLabelContact().getName() : "";
                  String otherContact = "";
                  otherContact = (sel.getOtherContact() != null) ? sel.getOtherContact() : "";
                  Schedule schedule = sel.getSchedule();
                  Vector tasks = (schedule != null) ? schedule.getTasks() : null;
                  ScheduledTask task = null;
                  String FP = "";
                  String BOM = "";
                  String PRQ = "";
                  String MASTERS = "";
                  String FILM = "";
                  String DEPOT = "";
                  String STIC = "";
                  String MC = "";
                  String DJ = "";
                  String FPcom = "";
                  String BOMcom = "";
                  String PRQcom = "";
                  String MASTERScom = "";
                  String FILMcom = "";
                  String DEPOTcom = "";
                  String STICcom = "";
                  String MCcom = "";
                  String DJcom = "";
                  if (tasks != null)
                    for (int j = 0; j < tasks.size(); j++) {
                      task = (ScheduledTask)tasks.get(j);
                      if (task != null && task.getDueDate() != null) {
                        SimpleDateFormat dueDateFormatter = new SimpleDateFormat("M/d");
                        String dueDate = dueDateFormatter.format(task.getDueDate().getTime());
                        String completionDate = MilestoneHelper.getFormatedDate(task.getCompletionDate());
                        if (task.getScheduledTaskStatus().equals("N/A"))
                          completionDate = task.getScheduledTaskStatus(); 
                        completionDate = String.valueOf(completionDate) + "\n";
                        String taskAbbrev = MilestoneHelper.getTaskAbbreivationNameById(task.getTaskAbbreviationID());
                        String taskComment = "";
                        if (taskAbbrev.equalsIgnoreCase("PRQD")) {
                          PRQ = dueDate;
                          PRQcom = String.valueOf(completionDate) + taskComment;
                        } else if (taskAbbrev.equalsIgnoreCase("READ")) {
                          STIC = dueDate;
                          STICcom = String.valueOf(completionDate) + taskComment;
                        } else if (taskAbbrev.equalsIgnoreCase("BMS")) {
                          BOM = dueDate;
                          BOMcom = String.valueOf(completionDate) + taskComment;
                        } else if (taskAbbrev.equalsIgnoreCase("PFS")) {
                          FP = dueDate;
                          FPcom = String.valueOf(completionDate) + taskComment;
                        } else if (taskAbbrev.equalsIgnoreCase("PO")) {
                          MC = dueDate;
                          MCcom = String.valueOf(completionDate) + taskComment;
                        } else if (taskAbbrev.equalsIgnoreCase("TPS")) {
                          MASTERS = dueDate;
                          MASTERScom = String.valueOf(completionDate) + taskComment;
                        } else if (taskAbbrev.equalsIgnoreCase("TA")) {
                          FILM = dueDate;
                          FILMcom = String.valueOf(completionDate) + taskComment;
                        } else if (taskAbbrev.equalsIgnoreCase("PSD")) {
                          DEPOT = dueDate;
                          DEPOTcom = String.valueOf(completionDate) + taskComment;
                        } else if (taskAbbrev.equalsIgnoreCase("MCS")) {
                          DJ = dueDate;
                          DJcom = String.valueOf(completionDate) + taskComment;
                        } 
                        task = null;
                      } 
                    }  
                  nextRow = 0;
                  subTable = new DefaultTableLens(2, 15);
                  subTable.setRowInsets(nextRow, new Insets(0, 0, 0, 0));
                  setColBorderColor(subTable, nextRow, 15, SHADED_AREA_COLOR);
                  subTable.setHeaderRowCount(0);
                  subTable.setColWidth(0, 259);
                  subTable.setColWidth(1, 157);
                  subTable.setColWidth(2, 150);
                  subTable.setColWidth(3, 80);
                  subTable.setColWidth(4, 168);
                  subTable.setColWidth(5, 87);
                  subTable.setColWidth(6, 84);
                  subTable.setColWidth(7, 70);
                  subTable.setColWidth(8, 80);
                  subTable.setColWidth(9, 72);
                  subTable.setColWidth(10, 90);
                  subTable.setColWidth(11, 70);
                  subTable.setColWidth(12, 77);
                  subTable.setColWidth(13, 77);
                  subTable.setColWidth(14, 180);
                  subTable.setRowBorderColor(nextRow - 1, Color.lightGray);
                  subTable.setObject(nextRow, 0, String.valueOf(artist) + "\n" + title);
                  subTable.setBackground(nextRow, 0, Color.white);
                  subTable.setSpan(nextRow, 0, new Dimension(1, 2));
                  subTable.setRowAutoSize(true);
                  subTable.setAlignment(nextRow, 0, 9);
                  subTable.setFont(nextRow, 0, new Font("Arial", 1, 7));
                  subTable.setSpan(nextRow, 1, new Dimension(1, 2));
                  subTable.setObject(nextRow, 1, pack);
                  subTable.setAlignment(nextRow, 1, 10);
                  subTable.setBackground(nextRow, 1, Color.white);
                  String[] checkStrings = { comment, artist, title, pack, (new String[7][4] = label).valueOf(contact) + "/n" + otherContact, price };
                  int[] checkStringsLength = { 20, 30, 30, 20, 25, 25, 15 };
                  int extraLines = MilestoneHelper.lineCountWCR(checkStrings, checkStringsLength);
                  String[] commentString = { comment };
                  int[] checkCommentLength = { 30 };
                  int commentCounter = MilestoneHelper.lineCountWCR(commentString, checkCommentLength);
                  boolean otherContactExists = false;
                  System.out.println("***commentCounter:[" + commentCounter + "]");
                  if (!otherContact.equals("") || commentCounter > 1)
                    otherContactExists = true; 
                  extraLines = (extraLines <= 2) ? 0 : (extraLines - 2);
                  for (int z = 0; z < extraLines; z++)
                    otherContact = String.valueOf(otherContact) + "\n"; 
                  subTable.setObject(nextRow, 2, String.valueOf(upc) + "\n" + selSubConfig);
                  subTable.setAlignment(nextRow, 2, 10);
                  subTable.setBackground(nextRow, 2, Color.white);
                  subTable.setSpan(nextRow, 2, new Dimension(1, 2));
                  subTable.setObject(nextRow, 3, String.valueOf(price) + "\n" + code + retail + "\n" + units);
                  subTable.setAlignment(nextRow, 3, 10);
                  subTable.setBackground(nextRow, 3, Color.white);
                  subTable.setSpan(nextRow, 3, new Dimension(1, 2));
                  subTable.setAlignment(nextRow + 1, 3, 10);
                  subTable.setObject(nextRow, 4, "Due Dates");
                  subTable.setColBorderColor(nextRow, 4, SHADED_AREA_COLOR);
                  subTable.setColBorder(nextRow, 4, 266240);
                  subTable.setFont(nextRow, 4, new Font("Arial", 1, 8));
                  subTable.setRowBorderColor(nextRow, SHADED_AREA_COLOR);
                  subTable.setAlignment(nextRow, 4, 10);
                  subTable.setAlignment(nextRow + 1, 4, 10);
                  if (otherContactExists) {
                    subTable.setObject(nextRow + 1, 4, String.valueOf(label) + "\n" + contact + "\n" + otherContact);
                  } else {
                    subTable.setObject(nextRow + 1, 4, String.valueOf(label) + "\n" + contact);
                  } 
                  subTable.setRowHeight(nextRow, 9);
                  subTable.setRowBorderColor(nextRow + 1, SHADED_AREA_COLOR);
                  subTable.setObject(nextRow, 5, PRQ);
                  subTable.setColBorderColor(nextRow, 5, SHADED_AREA_COLOR);
                  subTable.setColBorder(nextRow, 5, 266240);
                  subTable.setObject(nextRow, 6, STIC);
                  subTable.setColBorderColor(nextRow, 6, SHADED_AREA_COLOR);
                  subTable.setColBorder(nextRow, 6, 266240);
                  subTable.setObject(nextRow, 7, BOM);
                  subTable.setColBorderColor(nextRow, 7, SHADED_AREA_COLOR);
                  subTable.setColBorder(nextRow, 7, 266240);
                  subTable.setObject(nextRow, 8, FP);
                  subTable.setColBorderColor(nextRow, 8, SHADED_AREA_COLOR);
                  subTable.setColBorder(nextRow, 8, 266240);
                  subTable.setObject(nextRow, 9, MC);
                  subTable.setColBorderColor(nextRow, 9, SHADED_AREA_COLOR);
                  subTable.setColBorder(nextRow, 9, 266240);
                  subTable.setObject(nextRow, 10, MASTERS);
                  subTable.setColBorderColor(nextRow, 10, Color.lightGray);
                  subTable.setColBorder(nextRow, 10, 266240);
                  subTable.setObject(nextRow, 11, DJ);
                  subTable.setColBorderColor(nextRow, 11, Color.lightGray);
                  subTable.setColBorder(nextRow, 11, 266240);
                  subTable.setObject(nextRow, 12, FILM);
                  subTable.setColBorderColor(nextRow, 12, SHADED_AREA_COLOR);
                  subTable.setColBorder(nextRow, 12, 266240);
                  subTable.setObject(nextRow, 13, DEPOT);
                  subTable.setColBorderColor(nextRow, 13, SHADED_AREA_COLOR);
                  subTable.setColBorder(nextRow, 13, 266240);
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
                  subTable.setObject(nextRow + 1, 5, PRQcom);
                  subTable.setObject(nextRow + 1, 6, STICcom);
                  subTable.setObject(nextRow + 1, 7, BOMcom);
                  subTable.setObject(nextRow + 1, 8, FPcom);
                  subTable.setObject(nextRow + 1, 9, MCcom);
                  subTable.setObject(nextRow + 1, 10, MASTERScom);
                  subTable.setObject(nextRow + 1, 11, DJcom);
                  subTable.setObject(nextRow + 1, 12, FILMcom);
                  subTable.setObject(nextRow + 1, 13, DEPOTcom);
                  setColBorderColor(subTable, nextRow + 1, 15, SHADED_AREA_COLOR);
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
                  subTable.setRowFont(nextRow + 1, new Font("Arial", 0, 7));
                  subTable.setRowBackground(nextRow, SHADED_AREA_COLOR);
                  subTable.setRowForeground(nextRow, Color.black);
                  subTable.setBackground(nextRow, 14, Color.white);
                  subTable.setAlignment(nextRow, 14, 9);
                  subTable.setSpan(nextRow, 14, new Dimension(1, 2));
                  subTable.setObject(nextRow, 14, comment);
                  subTable.setFont(nextRow, 14, new Font("Arial", 0, 7));
                  subTable.setRowFont(nextRow, new Font("Arial", 0, 7));
                  body = new SectionBand(report);
                  double lfLineCount = 1.5D;
                  if (extraLines > 0) {
                    body.setHeight(1.5F);
                  } else {
                    body.setHeight(1.5F);
                  } 
                  if (extraLines > 3 || 
                    PRQcom.length() > 10 || STICcom.length() > 10 || 
                    BOMcom.length() > 10 || FPcom.length() > 10 || 
                    MCcom.length() > 10 || MASTERScom.length() > 10 || 
                    DJcom.length() > 10 || FILMcom.length() > 10 || 
                    DEPOTcom.length() > 10) {
                    if (lfLineCount < extraLines * 0.3D)
                      lfLineCount = extraLines * 0.3D; 
                    if (lfLineCount < (PRQcom.length() / 7) * 0.3D)
                      lfLineCount = (PRQcom.length() / 7) * 0.3D; 
                    if (lfLineCount < (STICcom.length() / 8) * 0.3D)
                      lfLineCount = (STICcom.length() / 8) * 0.3D; 
                    if (lfLineCount < (BOMcom.length() / 8) * 0.3D)
                      lfLineCount = (BOMcom.length() / 8) * 0.3D; 
                    if (lfLineCount < (FPcom.length() / 8) * 0.3D)
                      lfLineCount = (FPcom.length() / 8) * 0.3D; 
                    if (lfLineCount < (MCcom.length() / 8) * 0.3D)
                      lfLineCount = (MCcom.length() / 8) * 0.3D; 
                    if (lfLineCount < (MASTERScom.length() / 8) * 0.3D)
                      lfLineCount = (MASTERScom.length() / 8) * 0.3D; 
                    if (lfLineCount < (DJcom.length() / 8) * 0.3D)
                      lfLineCount = (DJcom.length() / 8) * 0.3D; 
                    if (lfLineCount < (FILMcom.length() / 8) * 0.3D)
                      lfLineCount = (FILMcom.length() / 8) * 0.3D; 
                    if (lfLineCount < (DEPOTcom.length() / 8) * 0.3D)
                      lfLineCount = (DEPOTcom.length() / 8) * 0.3D; 
                    body.setHeight((float)lfLineCount);
                  } else {
                    body.setHeight(1.5F);
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
      System.out.println(">>>>>>>>IdjProductionScheduleForPrintSubHandler(): exception: " + e);
    } 
    System.out.println("done");
  }
  
  private static void setColBorderColor(DefaultTableLens table, int row, int columns, Color color) {
    for (int i = -1; i < columns; i++)
      table.setColBorderColor(row, i, color); 
  }
  
  public static int insertConfigHeader(DefaultTableLens table_contents, String title, int nextRow, int cols) {
    int COL_LINE_STYLE = 4097;
    int HEADER_FONT_SIZE = 12;
    table_contents.setObject(nextRow, 0, "");
    table_contents.setSpan(nextRow, 0, new Dimension(cols, 1));
    table_contents.setRowHeight(nextRow, 1);
    table_contents.setRowBackground(nextRow, Color.white);
    table_contents.setColBorderColor(nextRow, -1, Color.black);
    table_contents.setColBorder(nextRow, -1, 4097);
    table_contents.setColBorderColor(nextRow, cols - 1, Color.black);
    table_contents.setColBorder(nextRow, cols - 1, 4097);
    table_contents.setRowBorderColor(nextRow, Color.white);
    table_contents.setRowBorder(nextRow, 266240);
    table_contents.setColBorderColor(nextRow + 1, -1, Color.black);
    table_contents.setColBorder(nextRow + 1, -1, 4097);
    table_contents.setColBorderColor(nextRow + 1, cols - 1, Color.black);
    table_contents.setColBorder(nextRow + 1, cols - 1, 4097);
    table_contents.setAlignment(nextRow + 1, 0, 2);
    table_contents.setSpan(nextRow + 1, 0, new Dimension(cols, 1));
    table_contents.setObject(nextRow + 1, 0, title);
    table_contents.setRowFont(nextRow + 1, new Font("Arial", 3, 12));
    table_contents.setColBorderColor(nextRow + 1, -1, Color.black);
    table_contents.setColBorder(nextRow + 1, -1, 4097);
    nextRow += 2;
    table_contents.setObject(nextRow, 0, "");
    table_contents.setSpan(nextRow, 0, new Dimension(cols, 1));
    table_contents.setRowHeight(nextRow, 1);
    table_contents.setRowBackground(nextRow, Color.white);
    table_contents.setColBorderColor(nextRow, -1, Color.black);
    table_contents.setColBorder(nextRow, -1, 4097);
    table_contents.setColBorderColor(nextRow, cols, Color.black);
    table_contents.setColBorder(nextRow, cols, 4097);
    table_contents.setRowBorderColor(nextRow, Color.white);
    table_contents.setRowBorder(nextRow, 266240);
    nextRow++;
    table_contents.setObject(nextRow, 0, "");
    table_contents.setSpan(nextRow, 0, new Dimension(cols, 1));
    table_contents.setRowHeight(nextRow, 1);
    table_contents.setRowBackground(nextRow, Color.white);
    table_contents.setColBorderColor(nextRow, -1, Color.white);
    table_contents.setColBorder(nextRow, -1, 4097);
    table_contents.setColBorderColor(nextRow, cols - 1, Color.white);
    table_contents.setColBorder(nextRow, cols, 4097);
    table_contents.setRowBorderColor(nextRow, Color.white);
    table_contents.setRowBorder(nextRow, 266240);
    nextRow++;
    return nextRow;
  }
}


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\IdjProductionScheduleForPrintSubHandler.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */