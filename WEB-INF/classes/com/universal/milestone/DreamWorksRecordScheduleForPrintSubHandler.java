package WEB-INF.classes.com.universal.milestone;

import com.techempower.ComponentLog;
import com.techempower.gemini.Context;
import com.techempower.gemini.GeminiApplication;
import com.universal.milestone.DatePeriod;
import com.universal.milestone.DreamWorksRecordScheduleForPrintSubHandler;
import com.universal.milestone.Form;
import com.universal.milestone.MilestoneHelper;
import com.universal.milestone.MilestoneHelper_2;
import com.universal.milestone.MultOtherContact;
import com.universal.milestone.Schedule;
import com.universal.milestone.ScheduledTask;
import com.universal.milestone.SecureHandler;
import com.universal.milestone.Selection;
import com.universal.milestone.SelectionManager;
import com.universal.milestone.SelectionStatus;
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

public class DreamWorksRecordScheduleForPrintSubHandler extends SecureHandler {
  public static final String COMPONENT_CODE = "hPsp";
  
  public GeminiApplication application;
  
  public ComponentLog log;
  
  public DreamWorksRecordScheduleForPrintSubHandler(GeminiApplication application) {
    this.application = application;
    this.log = application.getLog("hPsp");
  }
  
  public String getDescription() { return "Sub Report"; }
  
  protected static void fillDreamWorksRecordScheduleForPrint(XStyleSheet report, Context context) {
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
    int NUM_COLUMNS = 16;
    Color SHADED_AREA_COLOR = Color.lightGray;
    SectionBand hbandType = new SectionBand(report);
    SectionBand hbandCategory = new SectionBand(report);
    SectionBand hbandHeadings = new SectionBand(report);
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
    Hashtable selTable = groupSelectionsByTypeConfigAndStreetDate2(selections);
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
      rowCountTable = new DefaultTableLens(2, 10000);
      int totalCount = 0;
      int tenth = 1;
      for (int n = 0; n < sortedConfigVector.size(); n++) {
        String configC = (configVector.elementAt(n) != null) ? (String)configVector.elementAt(n) : "";
        Hashtable monthTableC = (Hashtable)selTable.get(configC);
        totalCount++;
        Enumeration monthsC = monthTableC.keys();
        Vector monthVectorC = new Vector();
        while (monthsC.hasMoreElements())
          monthVectorC.add((String)monthsC.nextElement()); 
        Object[] monthArrayC = (Object[])null;
        monthArrayC = monthVectorC.toArray();
        totalCount += monthArrayC.length;
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
            selections = (Vector)monthTable.get(monthName);
            if (selections != null)
              numSelections += selections.size(); 
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
        hbandHeadings = new SectionBand(report);
        hbandHeadings.setHeight(1.0F);
        hbandHeadings.setShrinkToFit(true);
        hbandHeadings.setVisible(true);
        table_contents = new DefaultTableLens(1, 16);
        table_contents.setHeaderRowCount(0);
        table_contents.setColWidth(0, 110);
        table_contents.setColWidth(1, 80);
        table_contents.setColWidth(2, 80);
        table_contents.setColWidth(3, 80);
        table_contents.setColWidth(4, 157);
        table_contents.setColWidth(5, 75);
        table_contents.setColWidth(6, 80);
        table_contents.setColWidth(7, 80);
        table_contents.setColWidth(8, 80);
        table_contents.setColWidth(9, 80);
        table_contents.setColWidth(10, 80);
        table_contents.setColWidth(11, 80);
        table_contents.setColWidth(12, 80);
        table_contents.setColWidth(13, 80);
        table_contents.setColWidth(14, 80);
        table_contents.setColWidth(15, 100);
        table_contents.setColBorderColor(Color.black);
        table_contents.setRowBorderColor(Color.black);
        table_contents.setRowBorder(4097);
        table_contents.setColBorder(4097);
        int nextRow = 0;
        String configHeaderText = !config.trim().equals("") ? config : "Other";
        table_contents.setSpan(nextRow, 0, new Dimension(16, 1));
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
        table_contents.setColBorder(nextRow, 15, 266240);
        table_contents.setColBorder(nextRow, 16, 266240);
        table_contents.setRowBorder(nextRow, 266240);
        table_contents.setRowBorderColor(nextRow - 1, Color.black);
        table_contents.setColBorderColor(nextRow, -1, Color.black);
        table_contents.setColBorderColor(nextRow, 0, Color.black);
        table_contents.setColBorderColor(nextRow, 15, Color.black);
        table_contents.setColBorderColor(nextRow, 16, Color.black);
        table_contents.setRowBorderColor(nextRow, Color.black);
        hbandType.addTable(table_contents, new Rectangle(0, 0, 800, 25));
        nextRow = 0;
        columnHeaderTable = new DefaultTableLens(1, 16);
        columnHeaderTable.setHeaderRowCount(0);
        columnHeaderTable.setColWidth(0, 150);
        columnHeaderTable.setColWidth(1, 80);
        columnHeaderTable.setColWidth(2, 80);
        columnHeaderTable.setColWidth(3, 80);
        columnHeaderTable.setColWidth(4, 130);
        columnHeaderTable.setColWidth(5, 75);
        columnHeaderTable.setColWidth(6, 75);
        columnHeaderTable.setColWidth(7, 75);
        columnHeaderTable.setColWidth(8, 75);
        columnHeaderTable.setColWidth(9, 75);
        columnHeaderTable.setColWidth(10, 75);
        columnHeaderTable.setColWidth(11, 75);
        columnHeaderTable.setColWidth(12, 75);
        columnHeaderTable.setColWidth(13, 75);
        columnHeaderTable.setColWidth(14, 75);
        columnHeaderTable.setColWidth(15, 75);
        columnHeaderTable.setAlignment(nextRow, 0, 34);
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
        columnHeaderTable.setObject(nextRow, 0, "Selection #\nUPC Code\nDivision");
        columnHeaderTable.setObject(nextRow, 2, "Price\nArtist\nTitle\nTerritory");
        columnHeaderTable.setObject(nextRow, 4, "Producer/\nProduct Mgr/\nPublicist/A&R");
        columnHeaderTable.setObject(nextRow, 5, "Sol\nSheet");
        columnHeaderTable.setObject(nextRow, 6, "File Maint\n(PFM)");
        columnHeaderTable.setObject(nextRow, 7, "CD-CS\nCopy to\nEd");
        columnHeaderTable.setObject(nextRow, 8, "Copy to\nArt");
        columnHeaderTable.setObject(nextRow, 9, "Final Art\nDue");
        columnHeaderTable.setObject(nextRow, 10, "Ref\nApp/Enter\nRMS");
        columnHeaderTable.setObject(nextRow, 11, "Order\nParts");
        columnHeaderTable.setObject(nextRow, 12, "GAP/\nStker Copy\nDue");
        columnHeaderTable.setObject(nextRow, 13, "Stkr Film\nPkg Film\nParts Ship");
        columnHeaderTable.setObject(nextRow, 14, "BOM");
        columnHeaderTable.setObject(nextRow, 15, "Test\nAppvd");
        columnHeaderTable.setColBorderColor(nextRow - 1, SHADED_AREA_COLOR);
        columnHeaderTable.setColBorderColor(nextRow, 0, SHADED_AREA_COLOR);
        columnHeaderTable.setColBorderColor(nextRow, 1, Color.white);
        columnHeaderTable.setColBorderColor(nextRow, 2, Color.white);
        columnHeaderTable.setColBorderColor(nextRow, 3, SHADED_AREA_COLOR);
        columnHeaderTable.setColBorderColor(nextRow, 4, SHADED_AREA_COLOR);
        columnHeaderTable.setColBorderColor(nextRow, 5, SHADED_AREA_COLOR);
        columnHeaderTable.setColBorderColor(nextRow, 6, SHADED_AREA_COLOR);
        columnHeaderTable.setColBorderColor(nextRow, 7, SHADED_AREA_COLOR);
        columnHeaderTable.setColBorderColor(nextRow, 8, SHADED_AREA_COLOR);
        columnHeaderTable.setColBorderColor(nextRow, 9, SHADED_AREA_COLOR);
        columnHeaderTable.setColBorderColor(nextRow, 10, SHADED_AREA_COLOR);
        columnHeaderTable.setColBorderColor(nextRow, 11, SHADED_AREA_COLOR);
        columnHeaderTable.setColBorderColor(nextRow, 12, SHADED_AREA_COLOR);
        columnHeaderTable.setColBorderColor(nextRow, 13, SHADED_AREA_COLOR);
        columnHeaderTable.setColBorderColor(nextRow, 14, SHADED_AREA_COLOR);
        columnHeaderTable.setColBorderColor(nextRow, 15, SHADED_AREA_COLOR);
        columnHeaderTable.setRowBorderColor(nextRow - 1, SHADED_AREA_COLOR);
        columnHeaderTable.setRowBorderColor(nextRow, SHADED_AREA_COLOR);
        columnHeaderTable.setRowFont(nextRow, new Font("Arial", 1, 8));
        columnHeaderTable.setRowBackground(nextRow, Color.white);
        columnHeaderTable.setRowForeground(nextRow, Color.black);
        columnHeaderTable.setRowHeight(nextRow, 50);
        hbandType.addTable(columnHeaderTable, new Rectangle(0, 24, 800, 80));
        hbandType.setBottomBorder(0);
        if (monthTable != null) {
          Enumeration months = monthTable.keys();
          Vector monthVector = new Vector();
          while (months.hasMoreElements())
            monthVector.add((String)months.nextElement()); 
          Object[] monthArray = (Object[])null;
          monthArray = monthVector.toArray();
          Arrays.sort(monthArray, new StringDateComparator());
          for (int x = 0; x < monthArray.length; x++) {
            String monthName = (String)monthArray[x];
            String monthNameString = monthName;
            monthTableLens = new DefaultTableLens(1, 16);
            hbandCategory = new SectionBand(report);
            hbandCategory.setHeight(0.25F);
            hbandCategory.setShrinkToFit(true);
            hbandCategory.setVisible(true);
            hbandCategory.setBottomBorder(0);
            hbandCategory.setLeftBorder(0);
            hbandCategory.setRightBorder(0);
            hbandCategory.setTopBorder(0);
            nextRow = 0;
            String cycle = "";
            try {
              Calendar calenderDate = MilestoneHelper.getDate(monthNameString);
              DatePeriod datePeriod = MilestoneHelper.findDatePeriod(calenderDate);
              cycle = " " + datePeriod.getCycle();
            } catch (Exception exception) {}
            Vector impactSelections = (Vector)monthTable.get(monthNameString);
            String selectedImpactDate = findLowestImpactDate(impactSelections);
            monthTableLens.setSpan(nextRow, 0, new Dimension(14, 1));
            monthTableLens.setObject(nextRow, 0, String.valueOf(monthNameString) + cycle);
            monthTableLens.setSpan(nextRow, 15, new Dimension(1, 1));
            monthTableLens.setObject(nextRow, 15, "Radio: " + selectedImpactDate);
            monthTableLens.setColBorderColor(nextRow, 1, SHADED_AREA_COLOR);
            monthTableLens.setColBorderColor(nextRow, 2, SHADED_AREA_COLOR);
            monthTableLens.setColBorderColor(nextRow, 3, SHADED_AREA_COLOR);
            monthTableLens.setColBorderColor(nextRow, 4, SHADED_AREA_COLOR);
            monthTableLens.setColBorderColor(nextRow, 5, SHADED_AREA_COLOR);
            monthTableLens.setColBorderColor(nextRow, 6, SHADED_AREA_COLOR);
            monthTableLens.setColBorderColor(nextRow, 7, SHADED_AREA_COLOR);
            monthTableLens.setColBorderColor(nextRow, 8, SHADED_AREA_COLOR);
            monthTableLens.setColBorderColor(nextRow, 9, SHADED_AREA_COLOR);
            monthTableLens.setColBorderColor(nextRow, 10, SHADED_AREA_COLOR);
            monthTableLens.setColBorderColor(nextRow, 11, SHADED_AREA_COLOR);
            monthTableLens.setColBorderColor(nextRow, 12, SHADED_AREA_COLOR);
            monthTableLens.setColBorderColor(nextRow, 13, SHADED_AREA_COLOR);
            monthTableLens.setColBorderColor(nextRow, 14, SHADED_AREA_COLOR);
            monthTableLens.setColBorderColor(nextRow, 15, SHADED_AREA_COLOR);
            monthTableLens.setAlignment(nextRow, 15, 4);
            monthTableLens.setRowHeight(nextRow, 14);
            monthTableLens.setRowFont(nextRow, new Font("Arial", 1, 12));
            monthTableLens.setRowBackground(nextRow, SHADED_AREA_COLOR);
            monthTableLens.setRowForeground(nextRow, Color.black);
            monthTableLens.setRowBorderColor(nextRow, Color.white);
            monthTableLens.setRowBorderColor(nextRow - 1, Color.white);
            monthTableLens.setColBorderColor(nextRow, -1, Color.white);
            monthTableLens.setColBorderColor(nextRow, 0, Color.white);
            monthTableLens.setColBorderColor(nextRow, 15, Color.white);
            hbandCategory.addTable(monthTableLens, new Rectangle(800, 800));
            footer.setVisible(true);
            footer.setHeight(0.1F);
            footer.setShrinkToFit(false);
            footer.setBottomBorder(0);
            group = new DefaultSectionLens(null, group, spacer);
            group = new DefaultSectionLens(null, group, hbandCategory);
            group = new DefaultSectionLens(null, group, spacer);
            nextRow = 0;
            selections = (Vector)monthTable.get(monthNameString);
            if (selections == null)
              selections = new Vector(); 
            MilestoneHelper.setSelectionSorting(selections, 14);
            Collections.sort(selections);
            MilestoneHelper.setSelectionSorting(selections, 4);
            Collections.sort(selections);
            MilestoneHelper.setSelectionSorting(selections, 22);
            Collections.sort(selections);
            MilestoneHelper.setSelectionSorting(selections, 9);
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
              String titleId = "";
              titleId = sel.getTitleID();
              String artist = "";
              artist = sel.getFlArtist().trim();
              String comment = "";
              String commentStr = (sel.getSelectionComments() != null) ? sel.getSelectionComments() : "";
              String newComment = removeLF(commentStr, 800);
              int subTableRows = 9;
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
              upc = MilestoneHelper_2.getRMSReportFormat(upc, "UPC", sel.getIsDigital());
              if (upc != null && upc.length() == 0)
                upc = titleId; 
              String selectionNo = "";
              selectionNo = (sel.getSelectionNo() != null) ? sel.getSelectionNo() : "";
              String selDivision = (sel.getDivision() != null && sel.getDivision().getName() != null) ? 
                sel.getDivision().getName() : "";
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
              String price = "";
              if (sel.getPriceCode() != null && sel.getPriceCode().getTotalCost() > 0.0F)
                price = "$" + MilestoneHelper.formatDollarPrice(sel.getPriceCode().getTotalCost()); 
              Vector multOtherContacts = new Vector();
              String aContact = "";
              for (int co = 0; co < sel.getMultOtherContacts().size(); co++) {
                aContact = ((MultOtherContact)sel.getMultOtherContacts().elementAt(co)).getName();
                multOtherContacts.add(aContact);
              } 
              Schedule schedule = sel.getSchedule();
              Vector tasks = (schedule != null) ? schedule.getTasks() : null;
              ScheduledTask task = null;
              String SFD = "";
              String SFM = "";
              String PCF = "";
              String FPC = "";
              String FAD = "";
              String MA = "";
              String LC = "";
              String PO = "";
              String SEPS = "";
              String STD = "";
              String SFS = "";
              String PFS = "";
              String TPS = "";
              String BMS = "";
              String TA = "";
              String SFDcom = "";
              String SFMcom = "";
              String PCFcom = "";
              String FPCcom = "";
              String FADcom = "";
              String MAcom = "";
              String LCcom = "";
              String POcom = "";
              String SEPScom = "";
              String STDcom = "";
              String SFScom = "";
              String PFScom = "";
              String TPScom = "";
              String BMScom = "";
              String TAcom = "";
              String SFDvend = "";
              String SFMvend = "";
              String PCFvend = "";
              String FPCvend = "";
              String FADvend = "";
              String MAvend = "";
              String LCvend = "";
              String POvend = "";
              String SEPSvend = "";
              String STDvend = "";
              String SFSvend = "";
              String PFSvend = "";
              String TPSvend = "";
              String BMSvend = "";
              String TAvend = "";
              if (tasks != null)
                for (int j = 0; j < tasks.size(); j++) {
                  task = (ScheduledTask)tasks.get(j);
                  if (task != null && task.getDueDate() != null) {
                    SimpleDateFormat dueDateFormatter = new SimpleDateFormat("M/d");
                    String dueDate = dueDateFormatter.format(task.getDueDate().getTime());
                    String completionDate = "";
                    if (task.getCompletionDate() == null) {
                      completionDate = "";
                    } else {
                      completionDate = dueDateFormatter.format(task.getCompletionDate().getTime());
                    } 
                    if (task.getScheduledTaskStatus().equals("N/A"))
                      completionDate = task.getScheduledTaskStatus(); 
                    String taskAbbrev = MilestoneHelper.getTaskAbbreivationNameById(task.getTaskAbbreviationID());
                    String taskComment = "";
                    String taskVendor = "";
                    taskVendor = (task.getVendor() != null) ? task.getVendor() : "";
                    taskVendor = taskVendor.equals("\n") ? "" : taskVendor;
                    if (taskAbbrev.equalsIgnoreCase("SFD")) {
                      SFD = dueDate;
                      SFDcom = completionDate;
                      SFDvend = taskVendor;
                    } else if (taskAbbrev.equalsIgnoreCase("SF/M")) {
                      SFM = dueDate;
                      SFMcom = completionDate;
                      SFMvend = taskVendor;
                    } else if (taskAbbrev.equalsIgnoreCase("PCE")) {
                      PCF = dueDate;
                      PCFcom = completionDate;
                      PCFvend = taskVendor;
                    } else if (taskAbbrev.equalsIgnoreCase("FPC")) {
                      FPC = dueDate;
                      FPCcom = completionDate;
                      FPCvend = taskVendor;
                    } else if (taskAbbrev.equalsIgnoreCase("FAD")) {
                      FAD = dueDate;
                      FADcom = completionDate;
                      FADvend = taskVendor;
                    } else if (taskAbbrev.equalsIgnoreCase("MA")) {
                      MA = dueDate;
                      MAcom = completionDate;
                      MAvend = taskVendor;
                    } else if (taskAbbrev.equalsIgnoreCase("LC")) {
                      LC = dueDate;
                      LCcom = completionDate;
                      LCvend = taskVendor;
                    } else if (taskAbbrev.equalsIgnoreCase("PO")) {
                      PO = dueDate;
                      POcom = completionDate;
                      POvend = taskVendor;
                    } else if (taskAbbrev.equalsIgnoreCase("SEPS")) {
                      SEPS = dueDate;
                      SEPScom = completionDate;
                      SEPSvend = taskVendor;
                    } else if (taskAbbrev.equalsIgnoreCase("STD")) {
                      STD = dueDate;
                      STDcom = completionDate;
                      STDvend = taskVendor;
                    } else if (taskAbbrev.equalsIgnoreCase("SFS")) {
                      SFS = dueDate;
                      SFScom = completionDate;
                      SFSvend = taskVendor;
                    } else if (taskAbbrev.equalsIgnoreCase("PFS")) {
                      PFS = dueDate;
                      PFScom = completionDate;
                      PFSvend = taskVendor;
                    } else if (taskAbbrev.equalsIgnoreCase("TPS")) {
                      TPS = dueDate;
                      TPScom = completionDate;
                      TPSvend = taskVendor;
                    } else if (taskAbbrev.equalsIgnoreCase("BMS")) {
                      BMS = dueDate;
                      BMScom = completionDate;
                      BMSvend = taskVendor;
                    } else if (taskAbbrev.equalsIgnoreCase("TA")) {
                      TA = dueDate;
                      TAcom = completionDate;
                      TAvend = taskVendor;
                    } 
                    task = null;
                  } 
                }  
              nextRow = 0;
              subTable = new DefaultTableLens(subTableRows, 16);
              subTable.setRowInsets(nextRow, new Insets(0, 0, 0, 0));
              setColBorderColor(subTable, nextRow, 16, SHADED_AREA_COLOR);
              subTable.setHeaderRowCount(0);
              subTable.setColWidth(0, 150);
              subTable.setColWidth(1, 80);
              subTable.setColWidth(2, 80);
              subTable.setColWidth(3, 80);
              subTable.setColWidth(4, 130);
              subTable.setColWidth(5, 75);
              subTable.setColWidth(6, 75);
              subTable.setColWidth(7, 75);
              subTable.setColWidth(8, 75);
              subTable.setColWidth(9, 75);
              subTable.setColWidth(10, 75);
              subTable.setColWidth(11, 75);
              subTable.setColWidth(12, 75);
              subTable.setColWidth(13, 75);
              subTable.setColWidth(14, 75);
              subTable.setColWidth(15, 75);
              subTable.setRowBorderColor(nextRow - 1, Color.lightGray);
              subTable.setRowBorderColor(nextRow, 0, Color.white);
              subTable.setRowBorderColor(nextRow, 1, SHADED_AREA_COLOR);
              subTable.setRowBorderColor(nextRow, 2, SHADED_AREA_COLOR);
              subTable.setRowBorderColor(nextRow, 3, SHADED_AREA_COLOR);
              subTable.setRowBorderColor(nextRow, 4, SHADED_AREA_COLOR);
              subTable.setRowBorderColor(nextRow, 5, SHADED_AREA_COLOR);
              subTable.setRowBorderColor(nextRow, 6, SHADED_AREA_COLOR);
              subTable.setRowBorderColor(nextRow, 7, SHADED_AREA_COLOR);
              subTable.setRowBorderColor(nextRow, 8, SHADED_AREA_COLOR);
              subTable.setRowBorderColor(nextRow, 9, SHADED_AREA_COLOR);
              subTable.setRowBorderColor(nextRow, 10, SHADED_AREA_COLOR);
              subTable.setRowBorderColor(nextRow, 11, SHADED_AREA_COLOR);
              subTable.setRowBorderColor(nextRow, 12, SHADED_AREA_COLOR);
              subTable.setRowBorderColor(nextRow, 13, SHADED_AREA_COLOR);
              subTable.setRowBorderColor(nextRow, 14, SHADED_AREA_COLOR);
              subTable.setRowBorderColor(nextRow, 15, SHADED_AREA_COLOR);
              subTable.setObject(nextRow, 0, selectionNo);
              subTable.setBackground(nextRow, 0, Color.white);
              subTable.setAlignment(nextRow, 0, 9);
              subTable.setFont(nextRow, 0, new Font("Arial", 0, 7));
              subTable.setObject(nextRow, 1, code);
              subTable.setSpan(nextRow, 1, new Dimension(1, 1));
              subTable.setBackground(nextRow, 1, SHADED_AREA_COLOR);
              subTable.setAlignment(nextRow, 1, 9);
              subTable.setFont(nextRow, 1, new Font("Arial", 1, 7));
              subTable.setObject(nextRow, 2, retail);
              subTable.setSpan(nextRow, 2, new Dimension(1, 1));
              subTable.setBackground(nextRow, 2, SHADED_AREA_COLOR);
              subTable.setAlignment(nextRow, 2, 10);
              subTable.setFont(nextRow, 2, new Font("Arial", 1, 7));
              subTable.setObject(nextRow, 3, price);
              subTable.setSpan(nextRow, 3, new Dimension(1, 1));
              subTable.setBackground(nextRow, 3, SHADED_AREA_COLOR);
              subTable.setAlignment(nextRow, 3, 12);
              subTable.setFont(nextRow, 3, new Font("Arial", 1, 7));
              subTable.setObject(nextRow, 4, "Due Dates");
              subTable.setSpan(nextRow, 4, new Dimension(1, 1));
              subTable.setAlignment(nextRow, 4, 10);
              subTable.setBackground(nextRow, 4, SHADED_AREA_COLOR);
              subTable.setFont(nextRow, 4, new Font("Arial", 1, 7));
              String[] checkStrings = { comment, artist, title, pack, label, price };
              int[] checkStringsLength = { 20, 30, 30, 20, 25, 15 };
              int extraLines = MilestoneHelper.lineCountWCR(checkStrings, checkStringsLength);
              String[] commentString = { comment };
              int[] checkCommentLength = { 30 };
              int commentCounter = MilestoneHelper.lineCountWCR(commentString, checkCommentLength);
              if (extraLines <= 2) {
                extraLines--;
              } else {
                extraLines--;
              } 
              extraLines = (extraLines <= 2) ? 0 : (extraLines - 2);
              subTable.setObject(nextRow, 5, SFD);
              subTable.setAlignment(nextRow, 5, 10);
              subTable.setBackground(nextRow, 5, SHADED_AREA_COLOR);
              subTable.setObject(nextRow, 6, SFM);
              subTable.setAlignment(nextRow, 6, 10);
              subTable.setBackground(nextRow, 6, SHADED_AREA_COLOR);
              subTable.setObject(nextRow, 7, PCF);
              subTable.setAlignment(nextRow, 7, 10);
              subTable.setColBorderColor(nextRow, 7, SHADED_AREA_COLOR);
              subTable.setColBorder(nextRow, 7, 266240);
              subTable.setFont(nextRow, 7, new Font("Arial", 1, 8));
              subTable.setObject(nextRow, 8, FPC);
              subTable.setAlignment(nextRow, 8, 10);
              subTable.setBackground(nextRow, 8, SHADED_AREA_COLOR);
              subTable.setColBorder(nextRow, 8, 266240);
              subTable.setObject(nextRow, 9, FAD);
              subTable.setAlignment(nextRow, 9, 10);
              subTable.setBackground(nextRow, 9, SHADED_AREA_COLOR);
              subTable.setColBorder(nextRow, 9, 266240);
              subTable.setObject(nextRow, 10, MA);
              subTable.setAlignment(nextRow, 10, 10);
              subTable.setBackground(nextRow, 10, SHADED_AREA_COLOR);
              subTable.setColBorder(nextRow, 10, 266240);
              subTable.setObject(nextRow, 11, PO);
              subTable.setAlignment(nextRow, 11, 10);
              subTable.setBackground(nextRow, 11, SHADED_AREA_COLOR);
              subTable.setColBorder(nextRow, 11, 266240);
              subTable.setObject(nextRow, 12, SEPS);
              subTable.setAlignment(nextRow, 12, 10);
              subTable.setBackground(nextRow, 12, SHADED_AREA_COLOR);
              subTable.setColBorder(nextRow, 12, 266240);
              subTable.setObject(nextRow, 13, SFS);
              subTable.setAlignment(nextRow, 13, 10);
              subTable.setBackground(nextRow, 13, SHADED_AREA_COLOR);
              subTable.setColBorder(nextRow, 13, 266240);
              subTable.setObject(nextRow, 14, BMS);
              subTable.setAlignment(nextRow, 14, 10);
              subTable.setBackground(nextRow, 14, SHADED_AREA_COLOR);
              subTable.setColBorder(nextRow, 14, 266240);
              subTable.setObject(nextRow, 15, TA);
              subTable.setAlignment(nextRow, 15, 10);
              subTable.setBackground(nextRow, 15, SHADED_AREA_COLOR);
              subTable.setColBorder(nextRow, 15, 266240);
              subTable.setAlignment(nextRow + 1, 6, 10);
              subTable.setAlignment(nextRow + 1, 8, 10);
              subTable.setFont(nextRow, 1, new Font("Arial", 1, 7));
              subTable.setFont(nextRow, 2, new Font("Arial", 0, 7));
              subTable.setFont(nextRow, 3, new Font("Arial", 0, 7));
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
              subTable.setRowFont(nextRow + 1, new Font("Arial", 0, 7));
              subTable.setRowBackground(nextRow, SHADED_AREA_COLOR);
              subTable.setRowForeground(nextRow, Color.black);
              subTable.setRowBorderColor(nextRow + 1, Color.white);
              subTable.setObject(nextRow + 1, 0, upc);
              subTable.setObject(nextRow + 1, 1, artist);
              subTable.setSpan(nextRow + 1, 1, new Dimension(3, 1));
              if (multOtherContacts.size() == 0) {
                subTable.setObject(nextRow + 1, 4, "");
              } else {
                subTable.setObject(nextRow + 1, 4, multOtherContacts.elementAt(0));
              } 
              subTable.setObject(nextRow + 1, 5, SFDcom);
              subTable.setObject(nextRow + 1, 6, SFMcom);
              subTable.setObject(nextRow + 1, 7, PCFcom);
              subTable.setObject(nextRow + 1, 8, FPCcom);
              subTable.setObject(nextRow + 1, 9, FADcom);
              subTable.setObject(nextRow + 1, 10, MAcom);
              subTable.setObject(nextRow + 1, 11, POcom);
              subTable.setObject(nextRow + 1, 12, SEPScom);
              subTable.setObject(nextRow + 1, 13, SFScom);
              subTable.setObject(nextRow + 1, 14, BMScom);
              subTable.setObject(nextRow + 1, 15, TAcom);
              setColBorderColor(subTable, nextRow + 1, 16, SHADED_AREA_COLOR);
              subTable.setFont(nextRow + 1, 0, new Font("Arial", 0, 7));
              subTable.setFont(nextRow + 1, 1, new Font("Arial", 1, 7));
              subTable.setFont(nextRow + 1, 2, new Font("Arial", 0, 7));
              subTable.setFont(nextRow + 1, 3, new Font("Arial", 0, 7));
              subTable.setFont(nextRow + 1, 4, new Font("Arial", 0, 7));
              subTable.setFont(nextRow + 1, 5, new Font("Arial", 0, 7));
              subTable.setFont(nextRow + 1, 6, new Font("Arial", 0, 7));
              subTable.setFont(nextRow + 1, 7, new Font("Arial", 0, 7));
              subTable.setFont(nextRow + 1, 8, new Font("Arial", 0, 7));
              subTable.setFont(nextRow + 1, 9, new Font("Arial", 0, 7));
              subTable.setFont(nextRow + 1, 10, new Font("Arial", 0, 7));
              subTable.setFont(nextRow + 1, 11, new Font("Arial", 0, 7));
              subTable.setFont(nextRow + 1, 12, new Font("Arial", 0, 7));
              subTable.setFont(nextRow + 1, 13, new Font("Arial", 0, 7));
              subTable.setFont(nextRow + 1, 14, new Font("Arial", 0, 7));
              subTable.setFont(nextRow + 1, 15, new Font("Arial", 0, 7));
              subTable.setAlignment(nextRow + 1, 4, 9);
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
              nextRow++;
              subTable.setFont(nextRow + 1, 0, new Font("Arial", 0, 7));
              subTable.setFont(nextRow + 1, 1, new Font("Arial", 1, 7));
              subTable.setFont(nextRow + 1, 4, new Font("Arial", 0, 7));
              subTable.setFont(nextRow + 1, 5, new Font("Arial", 0, 7));
              subTable.setFont(nextRow + 1, 6, new Font("Arial", 0, 7));
              subTable.setFont(nextRow + 1, 7, new Font("Arial", 0, 7));
              subTable.setFont(nextRow + 1, 8, new Font("Arial", 0, 7));
              subTable.setFont(nextRow + 1, 9, new Font("Arial", 0, 7));
              subTable.setFont(nextRow + 1, 10, new Font("Arial", 0, 7));
              subTable.setFont(nextRow + 1, 11, new Font("Arial", 0, 7));
              subTable.setFont(nextRow + 1, 12, new Font("Arial", 0, 7));
              subTable.setFont(nextRow + 1, 13, new Font("Arial", 0, 7));
              subTable.setFont(nextRow + 1, 14, new Font("Arial", 0, 7));
              subTable.setFont(nextRow + 1, 15, new Font("Arial", 0, 7));
              setColBorderColor(subTable, nextRow + 1, 16, SHADED_AREA_COLOR);
              subTable.setRowBorderColor(nextRow + 1, Color.white);
              subTable.setRowHeight(nextRow + 1, 9);
              subTable.setObject(nextRow + 1, 0, selDivision);
              subTable.setObject(nextRow + 1, 1, title);
              subTable.setSpan(nextRow + 1, 1, new Dimension(3, 1));
              if (multOtherContacts.size() < 2) {
                subTable.setObject(nextRow + 1, 4, "");
              } else {
                subTable.setObject(nextRow + 1, 4, multOtherContacts.elementAt(1));
              } 
              subTable.setObject(nextRow + 1, 5, SFDvend);
              subTable.setObject(nextRow + 1, 6, SFMvend);
              subTable.setObject(nextRow + 1, 7, PCFvend);
              subTable.setObject(nextRow + 1, 8, FPCvend);
              subTable.setObject(nextRow + 1, 9, FADvend);
              subTable.setObject(nextRow + 1, 10, LCcom);
              subTable.setObject(nextRow + 1, 11, POvend);
              subTable.setObject(nextRow + 1, 12, STDcom);
              subTable.setObject(nextRow + 1, 13, PFScom);
              subTable.setObject(nextRow + 1, 14, BMSvend);
              subTable.setObject(nextRow + 1, 15, TAvend);
              subTable.setAlignment(nextRow + 1, 4, 9);
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
              nextRow++;
              subTable.setFont(nextRow + 1, 0, new Font("Arial", 0, 7));
              subTable.setFont(nextRow + 1, 1, new Font("Arial", 1, 7));
              subTable.setFont(nextRow + 1, 2, new Font("Arial", 0, 7));
              subTable.setFont(nextRow + 1, 3, new Font("Arial", 0, 7));
              subTable.setFont(nextRow + 1, 4, new Font("Arial", 0, 7));
              subTable.setFont(nextRow + 1, 5, new Font("Arial", 0, 7));
              subTable.setFont(nextRow + 1, 6, new Font("Arial", 0, 7));
              subTable.setFont(nextRow + 1, 7, new Font("Arial", 0, 7));
              subTable.setFont(nextRow + 1, 8, new Font("Arial", 0, 7));
              subTable.setFont(nextRow + 1, 9, new Font("Arial", 0, 7));
              subTable.setFont(nextRow + 1, 10, new Font("Arial", 0, 7));
              subTable.setFont(nextRow + 1, 11, new Font("Arial", 0, 7));
              subTable.setFont(nextRow + 1, 12, new Font("Arial", 0, 7));
              subTable.setFont(nextRow + 1, 13, new Font("Arial", 0, 7));
              setColBorderColor(subTable, nextRow + 1, 16, SHADED_AREA_COLOR);
              subTable.setRowBorderColor(nextRow + 1, Color.white);
              subTable.setRowHeight(nextRow + 1, 9);
              subTable.setObject(nextRow + 1, 10, MAvend);
              subTable.setSpan(nextRow + 1, 1, new Dimension(3, 1));
              if (multOtherContacts.size() < 3) {
                subTable.setObject(nextRow + 1, 4, "");
              } else {
                subTable.setObject(nextRow + 1, 4, multOtherContacts.elementAt(2));
              } 
              subTable.setObject(nextRow + 1, 12, SEPSvend);
              subTable.setObject(nextRow + 1, 13, TPScom);
              subTable.setAlignment(nextRow + 1, 4, 9);
              subTable.setAlignment(nextRow + 1, 5, 10);
              subTable.setAlignment(nextRow + 1, 6, 10);
              subTable.setAlignment(nextRow + 1, 7, 10);
              subTable.setAlignment(nextRow + 1, 8, 10);
              subTable.setAlignment(nextRow + 1, 9, 10);
              subTable.setAlignment(nextRow + 1, 10, 10);
              subTable.setAlignment(nextRow + 1, 11, 10);
              subTable.setAlignment(nextRow + 1, 12, 10);
              subTable.setAlignment(nextRow + 1, 13, 10);
              nextRow++;
              subTable.setFont(nextRow + 1, 0, new Font("Arial", 0, 7));
              subTable.setFont(nextRow + 1, 1, new Font("Arial", 1, 7));
              subTable.setFont(nextRow + 1, 2, new Font("Arial", 0, 7));
              subTable.setFont(nextRow + 1, 3, new Font("Arial", 0, 7));
              subTable.setFont(nextRow + 1, 4, new Font("Arial", 0, 7));
              subTable.setFont(nextRow + 1, 5, new Font("Arial", 0, 7));
              subTable.setFont(nextRow + 1, 6, new Font("Arial", 0, 7));
              subTable.setFont(nextRow + 1, 7, new Font("Arial", 0, 7));
              subTable.setFont(nextRow + 1, 8, new Font("Arial", 0, 7));
              subTable.setFont(nextRow + 1, 9, new Font("Arial", 0, 7));
              subTable.setFont(nextRow + 1, 10, new Font("Arial", 0, 7));
              subTable.setFont(nextRow + 1, 11, new Font("Arial", 0, 7));
              subTable.setFont(nextRow + 1, 12, new Font("Arial", 0, 7));
              subTable.setFont(nextRow + 1, 13, new Font("Arial", 0, 7));
              setColBorderColor(subTable, nextRow + 1, 16, SHADED_AREA_COLOR);
              subTable.setRowBorderColor(nextRow + 1, Color.white);
              subTable.setRowHeight(nextRow + 1, 9);
              subTable.setObject(nextRow + 1, 10, LCvend);
              subTable.setSpan(nextRow + 1, 1, new Dimension(3, 1));
              if (multOtherContacts.size() < 4) {
                subTable.setObject(nextRow + 1, 4, "");
              } else {
                subTable.setObject(nextRow + 1, 4, multOtherContacts.elementAt(3));
              } 
              subTable.setObject(nextRow + 1, 12, STDvend);
              subTable.setObject(nextRow + 1, 13, SFSvend);
              subTable.setAlignment(nextRow + 1, 4, 9);
              subTable.setAlignment(nextRow + 1, 5, 10);
              subTable.setAlignment(nextRow + 1, 6, 10);
              subTable.setAlignment(nextRow + 1, 7, 10);
              subTable.setAlignment(nextRow + 1, 8, 10);
              subTable.setAlignment(nextRow + 1, 9, 10);
              subTable.setAlignment(nextRow + 1, 10, 10);
              subTable.setAlignment(nextRow + 1, 11, 10);
              subTable.setAlignment(nextRow + 1, 12, 10);
              subTable.setAlignment(nextRow + 1, 13, 10);
              nextRow++;
              subTable.setFont(nextRow + 1, 0, new Font("Arial", 0, 7));
              subTable.setFont(nextRow + 1, 1, new Font("Arial", 1, 7));
              subTable.setFont(nextRow + 1, 2, new Font("Arial", 0, 7));
              subTable.setFont(nextRow + 1, 3, new Font("Arial", 0, 7));
              subTable.setFont(nextRow + 1, 4, new Font("Arial", 0, 7));
              subTable.setFont(nextRow + 1, 5, new Font("Arial", 0, 7));
              subTable.setFont(nextRow + 1, 6, new Font("Arial", 0, 7));
              subTable.setFont(nextRow + 1, 7, new Font("Arial", 0, 7));
              subTable.setFont(nextRow + 1, 8, new Font("Arial", 0, 7));
              subTable.setFont(nextRow + 1, 9, new Font("Arial", 0, 7));
              subTable.setFont(nextRow + 1, 10, new Font("Arial", 0, 7));
              subTable.setFont(nextRow + 1, 11, new Font("Arial", 0, 7));
              subTable.setFont(nextRow + 1, 12, new Font("Arial", 0, 7));
              subTable.setFont(nextRow + 1, 13, new Font("Arial", 0, 7));
              setColBorderColor(subTable, nextRow + 1, 16, SHADED_AREA_COLOR);
              subTable.setRowBorderColor(nextRow + 1, Color.white);
              subTable.setSpan(nextRow + 1, 1, new Dimension(3, 1));
              if (multOtherContacts.size() < 5) {
                subTable.setObject(nextRow + 1, 4, "");
              } else {
                subTable.setObject(nextRow + 1, 4, multOtherContacts.elementAt(4));
              } 
              subTable.setRowHeight(nextRow + 1, 9);
              subTable.setObject(nextRow + 1, 13, PFSvend);
              subTable.setAlignment(nextRow + 1, 4, 9);
              subTable.setAlignment(nextRow + 1, 5, 10);
              subTable.setAlignment(nextRow + 1, 6, 10);
              subTable.setAlignment(nextRow + 1, 7, 10);
              subTable.setAlignment(nextRow + 1, 8, 10);
              subTable.setAlignment(nextRow + 1, 9, 10);
              subTable.setAlignment(nextRow + 1, 10, 10);
              subTable.setAlignment(nextRow + 1, 11, 10);
              subTable.setAlignment(nextRow + 1, 12, 10);
              subTable.setAlignment(nextRow + 1, 13, 10);
              nextRow++;
              subTable.setFont(nextRow + 1, 0, new Font("Arial", 0, 7));
              subTable.setFont(nextRow + 1, 1, new Font("Arial", 1, 7));
              subTable.setFont(nextRow + 1, 2, new Font("Arial", 0, 7));
              subTable.setFont(nextRow + 1, 3, new Font("Arial", 0, 7));
              subTable.setFont(nextRow + 1, 4, new Font("Arial", 0, 7));
              subTable.setFont(nextRow + 1, 5, new Font("Arial", 0, 7));
              subTable.setFont(nextRow + 1, 6, new Font("Arial", 0, 7));
              subTable.setFont(nextRow + 1, 7, new Font("Arial", 0, 7));
              subTable.setFont(nextRow + 1, 8, new Font("Arial", 0, 7));
              subTable.setFont(nextRow + 1, 9, new Font("Arial", 0, 7));
              subTable.setFont(nextRow + 1, 10, new Font("Arial", 0, 7));
              subTable.setFont(nextRow + 1, 11, new Font("Arial", 0, 7));
              subTable.setFont(nextRow + 1, 12, new Font("Arial", 0, 7));
              subTable.setFont(nextRow + 1, 13, new Font("Arial", 0, 7));
              setColBorderColor(subTable, nextRow + 1, 16, SHADED_AREA_COLOR);
              subTable.setRowBorderColor(nextRow + 1, Color.white);
              subTable.setSpan(nextRow + 1, 1, new Dimension(3, 1));
              if (multOtherContacts.size() < 6) {
                subTable.setObject(nextRow + 1, 4, "");
              } else {
                subTable.setObject(nextRow + 1, 4, multOtherContacts.elementAt(5));
              } 
              subTable.setRowHeight(nextRow + 1, 9);
              subTable.setObject(nextRow + 1, 13, TPSvend);
              subTable.setAlignment(nextRow + 1, 4, 9);
              subTable.setAlignment(nextRow + 1, 5, 10);
              subTable.setAlignment(nextRow + 1, 6, 10);
              subTable.setAlignment(nextRow + 1, 7, 10);
              subTable.setAlignment(nextRow + 1, 8, 10);
              subTable.setAlignment(nextRow + 1, 9, 10);
              subTable.setAlignment(nextRow + 1, 10, 10);
              subTable.setAlignment(nextRow + 1, 11, 10);
              subTable.setAlignment(nextRow + 1, 12, 10);
              subTable.setAlignment(nextRow + 1, 13, 10);
              nextRow++;
              subTable.setRowBackground(nextRow + 1, SHADED_AREA_COLOR);
              subTable.setColBorderColor(nextRow + 1, -1, SHADED_AREA_COLOR);
              subTable.setColBorderColor(nextRow + 1, 0, SHADED_AREA_COLOR);
              subTable.setColBorderColor(nextRow + 1, 1, SHADED_AREA_COLOR);
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
              subTable.setColBorderColor(nextRow + 1, 12, Color.white);
              subTable.setColBorderColor(nextRow + 1, 13, Color.white);
              subTable.setColBorderColor(nextRow + 1, 14, Color.white);
              subTable.setColBorderColor(nextRow + 1, 15, SHADED_AREA_COLOR);
              subTable.setRowBorderColor(nextRow + 1, SHADED_AREA_COLOR);
              subTable.setSpan(nextRow + 1, 0, new Dimension(1, 1));
              subTable.setSpan(nextRow + 1, 1, new Dimension(15, 1));
              subTable.setAlignment(nextRow + 1, 1, 9);
              subTable.setFont(nextRow + 1, 0, new Font("Arial", 1, 7));
              subTable.setFont(nextRow + 1, 1, new Font("Arial", 0, 7));
              subTable.setObject(nextRow + 1, 0, "Packaging Specs:");
              subTable.setObject(nextRow + 1, 1, pack);
              nextRow++;
              subTable.setColBorderColor(nextRow + 1, -1, SHADED_AREA_COLOR);
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
              subTable.setColBorderColor(nextRow + 1, 12, Color.white);
              subTable.setColBorderColor(nextRow + 1, 13, Color.white);
              subTable.setColBorderColor(nextRow + 1, 14, Color.white);
              subTable.setColBorderColor(nextRow + 1, 15, SHADED_AREA_COLOR);
              subTable.setRowBorderColor(nextRow + 1, SHADED_AREA_COLOR);
              subTable.setSpan(nextRow + 1, 0, new Dimension(1, 1));
              subTable.setSpan(nextRow + 1, 1, new Dimension(15, 1));
              subTable.setFont(nextRow + 1, 0, new Font("Arial", 1, 7));
              subTable.setFont(nextRow + 1, 1, new Font("Arial", 0, 7));
              subTable.setObject(nextRow + 1, 0, "Comments:");
              subTable.setObject(nextRow + 1, 1, newComment);
              body = new SectionBand(report);
              double lfLineCount = 1.5D;
              body.setHeight(1.8F);
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
        group = new DefaultSectionLens(hbandType, group, null);
        report.addSection(group, rowCountTable);
        report.addPageBreak();
        group = null;
      } 
    } catch (Exception e) {
      System.out.println(">>>>>>>>fillDreamWorksRecordScheduleForPrint(): exception: " + e);
    } 
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
  
  public static String removeLF(String theString, int maxChars) { return theString.replace('\n', ' '); }
  
  public static Hashtable groupSelectionsByTypeConfigAndStreetDate2(Vector selections) {
    Hashtable groupSelectionsByTypeConfigAndStreetDate = new Hashtable();
    if (selections == null)
      return groupSelectionsByTypeConfigAndStreetDate; 
    for (int i = 0; i < selections.size(); i++) {
      Selection sel = (Selection)selections.elementAt(i);
      if (sel != null) {
        sel = SelectionManager.getInstance().getSelectionAndSchedule(sel.getSelectionID());
        String typeConfigString = "";
        String dayString = "";
        String typeString = (sel.getReleaseType() != null) ? sel.getReleaseType().getName() : "";
        String configString = (sel.getSelectionSubConfig().getSelectionSubConfigurationAbbreviation() != null) ? sel.getSelectionSubConfig().getSelectionSubConfigurationAbbreviation() : "";
        if (!typeString.startsWith("Promo")) {
          if (typeString.startsWith("Commercial"))
            if (configString.equals("8TRK") || 
              configString.equals("CDROM") || 
              configString.equals("CDVID") || 
              configString.equals("DCCASS") || 
              configString.equals("CASSEP") || 
              configString.equals("CDEP") || 
              configString.equals("ECDEP") || 
              configString.equals("ALBUM") || 
              configString.equals("CASS") || 
              configString.equals("CD") || 
              configString.equals("ECD") || 
              configString.equals("LASER") || 
              configString.equals("MIXED") || 
              configString.equals("DVDVID") || 
              configString.equals("VIDEO") || 
              configString.equals("DVDAUD") || 
              configString.equalsIgnoreCase("DUALDISC")) {
              typeConfigString = "Commercial Full Length";
            } else {
              typeConfigString = "Commercial Single";
            }  
          if (sel.getStreetDate() != null)
            dayString = MilestoneHelper.getFormatedDate(sel.getStreetDate()); 
          String statusString = "";
          SelectionStatus status = sel.getSelectionStatus();
          if (status != null)
            statusString = (status.getName() == null) ? "" : status.getName(); 
          Hashtable typeConfigSubTable = (Hashtable)groupSelectionsByTypeConfigAndStreetDate.get(typeConfigString);
          if (typeConfigSubTable == null) {
            typeConfigSubTable = new Hashtable();
            groupSelectionsByTypeConfigAndStreetDate.put(typeConfigString, typeConfigSubTable);
          } 
          Vector selectionsForDates = (Vector)typeConfigSubTable.get(dayString);
          if (selectionsForDates == null) {
            selectionsForDates = new Vector();
            typeConfigSubTable.put(dayString, selectionsForDates);
          } 
          selectionsForDates.addElement(sel);
        } 
      } 
    } 
    return groupSelectionsByTypeConfigAndStreetDate;
  }
  
  public static String findLowestImpactDate(Vector selections) {
    String impactDate = "None";
    SimpleDateFormat dueDateFormatter = new SimpleDateFormat("MM/dd/yy");
    MilestoneHelper.setSelectionSorting(selections, 19);
    Collections.sort(selections);
    if (((Selection)selections.elementAt(false)).getImpactDate() == null) {
      impactDate = "None";
    } else {
      impactDate = dueDateFormatter.format(((Selection)selections.elementAt(0)).getImpactDate().getTime());
    } 
    return impactDate;
  }
}


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\DreamWorksRecordScheduleForPrintSubHandler.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */