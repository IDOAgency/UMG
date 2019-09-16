package WEB-INF.classes.com.universal.milestone;

import com.techempower.ComponentLog;
import com.techempower.EnhancedProperties;
import com.techempower.gemini.Context;
import com.techempower.gemini.GeminiApplication;
import com.universal.milestone.DigitalProductionScheduleForPrintSubHandler;
import com.universal.milestone.Form;
import com.universal.milestone.ImpactDate;
import com.universal.milestone.MilestoneHelper;
import com.universal.milestone.MilestoneHelper_2;
import com.universal.milestone.MonthYearComparator;
import com.universal.milestone.Pfm;
import com.universal.milestone.ReleasingFamily;
import com.universal.milestone.Schedule;
import com.universal.milestone.ScheduledTask;
import com.universal.milestone.SecureHandler;
import com.universal.milestone.Selection;
import com.universal.milestone.SelectionManager;
import inetsoft.report.SectionBand;
import inetsoft.report.XStyleSheet;
import inetsoft.report.lens.DefaultSectionLens;
import inetsoft.report.lens.DefaultTableLens;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Rectangle;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;
import javax.servlet.http.HttpServletResponse;

public class DigitalProductionScheduleForPrintSubHandler extends SecureHandler {
  public static final String COMPONENT_CODE = "hUMEsu";
  
  public static ComponentLog log;
  
  public DigitalProductionScheduleForPrintSubHandler(GeminiApplication application) {}
  
  public static void configure(EnhancedProperties props, GeminiApplication application) { log = application.getLog("hUMEsu"); }
  
  public String getDescription() { return "Digital Production Schedule Report"; }
  
  protected static void fillDigitalProductionScheduleUpdateForPrint(XStyleSheet report, Context context) {
    int COL_LINE_STYLE = 4097;
    int HEADER_FONT_SIZE = 12;
    double ldLineVal = 0.25D;
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
    } catch (Exception e) {
      System.out.println("exception caught in DigitalProductionScheduleForPrintSubhandler");
    } 
    Hashtable selTable = MilestoneHelper.groupSelectionsByDigitalTypeAndStreetDate(selections);
    try {
      Form reportForm = (Form)context.getSessionValue("reportForm");
      Calendar beginStDate = (reportForm.getStringValue("beginDate") != null && 
        reportForm.getStringValue("beginDate").length() > 0) ? 
        MilestoneHelper.getDate(reportForm.getStringValue("beginDate")) : null;
      Calendar endStDate = (reportForm.getStringValue("endDate") != null && 
        reportForm.getStringValue("endDate").length() > 0) ? 
        MilestoneHelper.getDate(reportForm.getStringValue("endDate")) : null;
      report.setElement("startdate", MilestoneHelper.getFormatedDate(beginStDate));
      report.setElement("enddate", MilestoneHelper.getFormatedDate(endStDate));
      int numMonths = 0;
      int numConfigs = 0;
      int numSelections = 0;
      Color SHADED_AREA_COLOR = Color.lightGray;
      int DATA_FONT_SIZE = 7;
      int SMALL_HEADER_FONT_SIZE = 8;
      int numColumns = 14;
      DefaultTableLens table_contents = null;
      DefaultTableLens columnHeaderTable = null;
      DefaultTableLens rowCountTable = null;
      DefaultTableLens subTable = null;
      SectionBand hbandType = new SectionBand(report);
      SectionBand hbandMonth = new SectionBand(report);
      SectionBand body = new SectionBand(report);
      SectionBand footer = new SectionBand(report);
      DefaultSectionLens group = null;
      Enumeration configs = selTable.keys();
      Vector configVector = new Vector();
      while (configs.hasMoreElements())
        configVector.addElement(configs.nextElement()); 
      Object[] sortedArray = null;
      sortedArray = MilestoneHelper.sortStringVector(configVector);
      Vector sortedConfigVector = new Vector();
      for (int aCount = 0; aCount < sortedArray.length; aCount++)
        sortedConfigVector.addElement(sortedArray[aCount]); 
      int nextRow = 0;
      int totalCount = 0;
      int tenth = 1;
      String saveConfig = "";
      for (int a = 0; a < sortedConfigVector.size(); a++) {
        totalCount++;
        String configC = (String)sortedConfigVector.get(a);
        Hashtable monthTableC = (Hashtable)selTable.get(configC);
        if (monthTableC != null) {
          Enumeration monthsC = monthTableC.keys();
          Vector monthVectorC = new Vector();
          while (monthsC.hasMoreElements())
            monthVectorC.add((String)monthsC.nextElement()); 
          Object[] monthArrayC = null;
          monthArrayC = monthVectorC.toArray();
          for (int b = 0; b < monthArrayC.length; b++) {
            totalCount++;
            String monthNameC = (String)monthArrayC[b];
            Vector selectionsC = (Vector)monthTableC.get(monthNameC);
            if (selectionsC == null)
              selectionsC = new Vector(); 
            totalCount += selectionsC.size();
          } 
        } 
      } 
      tenth = (totalCount > 10) ? (totalCount / 10) : 1;
      HttpServletResponse sresponse = context.getResponse();
      context.putDelivery("status", new String("start_report"));
      context.putDelivery("percent", new String("10"));
      context.includeJSP("status.jsp", "hiddenFrame");
      sresponse.setContentType("text/plain");
      sresponse.flushBuffer();
      int recordCount = 0;
      int count = 0;
      for (int n = 0; n < sortedConfigVector.size(); n++) {
        if (count < recordCount / tenth) {
          count = recordCount / tenth;
          sresponse = context.getResponse();
          context.putDelivery("status", new String("start_report"));
          context.putDelivery("percent", new String(String.valueOf(count * 10)));
          context.includeJSP("status.jsp", "hiddenFrame");
          sresponse.setContentType("text/plain");
          sresponse.flushBuffer();
        } 
        recordCount++;
        long start_config = System.currentTimeMillis();
        String config = (String)sortedConfigVector.get(n);
        Hashtable monthTable = (Hashtable)selTable.get(config);
        rowCountTable = new DefaultTableLens(2, 10000);
        table_contents = new DefaultTableLens(1, 14);
        table_contents.setColWidth(0, 70);
        table_contents.setColWidth(1, 150);
        table_contents.setColWidth(2, 65);
        table_contents.setColWidth(3, 150);
        table_contents.setColWidth(4, 65);
        table_contents.setColWidth(5, 65);
        table_contents.setColWidth(6, 105);
        table_contents.setColWidth(7, 80);
        table_contents.setColWidth(8, 80);
        table_contents.setColWidth(9, 80);
        table_contents.setColWidth(10, 70);
        table_contents.setColWidth(11, 70);
        table_contents.setColWidth(12, 85);
        table_contents.setColWidth(13, 70);
        nextRow = 0;
        hbandType = new SectionBand(report);
        hbandType.setHeight(1.0F);
        hbandType.setShrinkToFit(false);
        hbandType.setVisible(true);
        int cols = 14;
        table_contents.setObject(nextRow, 0, "");
        table_contents.setSpan(nextRow, 0, new Dimension(cols, 1));
        table_contents.setRowHeight(nextRow, 14);
        table_contents.setRowBackground(nextRow, Color.white);
        table_contents.setRowBorderColor(nextRow, Color.black);
        table_contents.setRowBorder(nextRow, 266240);
        table_contents.setAlignment(nextRow, 0, 2);
        table_contents.setObject(nextRow, 0, config);
        table_contents.setRowFont(nextRow, new Font("Arial", 3, 12));
        nextRow++;
        hbandType.addTable(table_contents, new Rectangle(0, 0, 800, 25));
        if (monthTable != null) {
          Enumeration months = monthTable.keys();
          Vector monthVector = new Vector();
          while (months.hasMoreElements())
            monthVector.add((String)months.nextElement()); 
          Object[] monthArray = null;
          monthArray = monthVector.toArray();
          Arrays.sort(monthArray, new MonthYearComparator());
          for (int x = 0; x < monthArray.length; x++) {
            if (count < recordCount / tenth) {
              count = recordCount / tenth;
              sresponse = context.getResponse();
              context.putDelivery("status", new String("start_report"));
              context.putDelivery("percent", new String(String.valueOf(count * 10)));
              context.includeJSP("status.jsp", "hiddenFrame");
              sresponse.setContentType("text/plain");
              sresponse.flushBuffer();
            } 
            recordCount++;
            long start_month = System.currentTimeMillis();
            String monthName = (String)monthArray[x];
            String monthNameString = monthName;
            hbandMonth = new SectionBand(report);
            hbandMonth.setHeight(0.25F);
            hbandMonth.setShrinkToFit(true);
            hbandMonth.setVisible(true);
            hbandMonth.setBottomBorder(0);
            hbandMonth.setLeftBorder(0);
            hbandMonth.setRightBorder(0);
            hbandMonth.setTopBorder(0);
            try {
              Calendar currentDate = MilestoneHelper.getMYDate(monthName);
              SimpleDateFormat monthFormatter = new SimpleDateFormat("MMMMM");
              monthNameString = monthFormatter.format(currentDate.getTime());
            } catch (Exception e) {
              if (monthName.equals("13")) {
                monthNameString = "TBS";
              } else if (monthName.equals("26")) {
                monthNameString = "ITW";
              } else {
                monthNameString = "No street date";
              } 
            } 
            selections = (Vector)monthTable.get(monthName);
            if (selections == null)
              selections = new Vector(); 
            MilestoneHelper.setSelectionSorting(selections, 22);
            Collections.sort(selections);
            MilestoneHelper.setSelectionSorting(selections, 25);
            Collections.sort(selections);
            MilestoneHelper.setSelectionSorting(selections, 26);
            Collections.sort(selections);
            columnHeaderTable = new DefaultTableLens(1, 14);
            nextRow = 0;
            table_contents.setColWidth(0, 70);
            columnHeaderTable.setColWidth(0, 70);
            columnHeaderTable.setColWidth(1, 150);
            columnHeaderTable.setColWidth(2, 65);
            columnHeaderTable.setColWidth(3, 150);
            columnHeaderTable.setColWidth(4, 65);
            columnHeaderTable.setColWidth(5, 65);
            columnHeaderTable.setColWidth(6, 105);
            columnHeaderTable.setColWidth(7, 80);
            columnHeaderTable.setColWidth(8, 80);
            columnHeaderTable.setColWidth(9, 80);
            columnHeaderTable.setColWidth(10, 70);
            columnHeaderTable.setColWidth(11, 70);
            columnHeaderTable.setColWidth(12, 85);
            columnHeaderTable.setColWidth(13, 70);
            columnHeaderTable.setRowAlignment(nextRow, 2);
            columnHeaderTable.setObject(nextRow, 0, "Digital\nRelease\nDate");
            columnHeaderTable.setObject(nextRow, 1, "Artist /\nTitle");
            columnHeaderTable.setObject(nextRow, 2, "Explicit\n(Y/N)");
            columnHeaderTable.setObject(nextRow, 3, "Releasing Family\nLabel");
            columnHeaderTable.setObject(nextRow, 4, "Priority");
            if (config.equals("eAlbum")) {
              columnHeaderTable.setObject(nextRow, 5, "Phys.\nStreet\nDate");
              columnHeaderTable.setObject(nextRow, 6, "Corresponding\nPhysical\nLocal Prod # /\nDigital UPC");
            } else {
              columnHeaderTable.setObject(nextRow, 5, "Radio\nImpact\nDate");
              columnHeaderTable.setObject(nextRow, 6, "Corresponding\nPhysical\nLocal Prod # /\nDigital UPC");
            } 
            columnHeaderTable.setObject(nextRow, 7, "eCommerce\nNotified");
            columnHeaderTable.setObject(nextRow, 8, "PFM\nCompleted");
            columnHeaderTable.setObject(nextRow, 9, "Graphics\nReceived");
            columnHeaderTable.setObject(nextRow, 10, "Audio\nRecieved");
            columnHeaderTable.setObject(nextRow, 11, "Digital Parts\nOrder Placed");
            columnHeaderTable.setObject(nextRow, 12, "Legal\nClearance");
            columnHeaderTable.setObject(nextRow, 13, "Parts\nreceived\nby\nClient");
            columnHeaderTable.setRowAlignment(nextRow, 34);
            columnHeaderTable.setRowFont(nextRow, new Font("Arial", 1, 8));
            columnHeaderTable.setRowBorderColor(nextRow, Color.lightGray);
            columnHeaderTable.setRowBorderColor(nextRow - 1, Color.lightGray);
            columnHeaderTable.setColBorderColor(nextRow, -1, Color.lightGray);
            columnHeaderTable.setColBorderColor(nextRow, 0, Color.lightGray);
            columnHeaderTable.setColBorderColor(nextRow, 1, Color.lightGray);
            columnHeaderTable.setColBorderColor(nextRow, 2, Color.lightGray);
            columnHeaderTable.setColBorderColor(nextRow, 3, Color.lightGray);
            columnHeaderTable.setColBorderColor(nextRow, 4, Color.lightGray);
            columnHeaderTable.setColBorderColor(nextRow, 5, Color.lightGray);
            columnHeaderTable.setColBorderColor(nextRow, 6, Color.lightGray);
            columnHeaderTable.setColBorderColor(nextRow, 7, Color.lightGray);
            columnHeaderTable.setColBorderColor(nextRow, 8, Color.lightGray);
            columnHeaderTable.setColBorderColor(nextRow, 9, Color.lightGray);
            columnHeaderTable.setColBorderColor(nextRow, 10, Color.lightGray);
            columnHeaderTable.setColBorderColor(nextRow, 11, Color.lightGray);
            columnHeaderTable.setColBorderColor(nextRow, 12, Color.lightGray);
            columnHeaderTable.setColBorderColor(nextRow, 13, Color.lightGray);
            columnHeaderTable.setRowBorderColor(nextRow, Color.lightGray);
            hbandType.addTable(columnHeaderTable, new Rectangle(0, 25, 800, 50));
            nextRow = 0;
            DefaultTableLens monthTableLens = new DefaultTableLens(1, 14);
            nextRow = 0;
            monthTableLens.setObject(nextRow, 0, monthNameString);
            monthTableLens.setSpan(nextRow, 0, new Dimension(14, 1));
            monthTableLens.setRowAlignment(nextRow, 1);
            monthTableLens.setRowFont(nextRow, new Font("Arial", 1, 12));
            monthTableLens.setRowHeight(nextRow, 14);
            monthTableLens.setColBorderColor(nextRow, -1, Color.white);
            monthTableLens.setColBorderColor(nextRow, 13, Color.white);
            monthTableLens.setColBorderColor(nextRow, 14, Color.white);
            monthTableLens.setRowBorderColor(nextRow, Color.white);
            monthTableLens.setRowBorderColor(nextRow - 1, Color.white);
            monthTableLens.setRowBackground(nextRow, Color.lightGray);
            hbandMonth.addTable(monthTableLens, new Rectangle(800, 800));
            hbandMonth.setBottomBorder(0);
            footer.setVisible(true);
            footer.setHeight(0.05F);
            footer.setShrinkToFit(true);
            footer.setBottomBorder(0);
            group = new DefaultSectionLens(null, group, footer);
            group = new DefaultSectionLens(null, group, hbandMonth);
            group = new DefaultSectionLens(null, group, footer);
            for (int j = 0; j < selections.size(); j++) {
              if (count < recordCount / tenth) {
                count = recordCount / tenth;
                sresponse = context.getResponse();
                context.putDelivery("status", new String("start_report"));
                context.putDelivery("percent", new String(String.valueOf(count * 10)));
                context.includeJSP("status.jsp", "hiddenFrame");
                sresponse.setContentType("text/plain");
                sresponse.flushBuffer();
              } 
              recordCount++;
              long start_selection = System.currentTimeMillis();
              Selection sel = (Selection)selections.elementAt(j);
              sel = SelectionManager.getInstance().getSelectionAndSchedule(sel.getSelectionID());
              Schedule schedule = sel.getSchedule();
              Vector tasks = null;
              if (schedule != null)
                tasks = schedule.getTasks(); 
              String digitalReleaseDate = MilestoneHelper.getFormatedDate(sel.getDigitalRlsDate());
              String artist = sel.getFlArtist();
              String title = sel.getTitle();
              boolean parental = sel.getParentalGuidance();
              String explicit = "No";
              if (parental)
                explicit = "Yes"; 
              String releasingFamilyString = ReleasingFamily.getName(sel.getReleaseFamilyId());
              String label = (sel.getImprint() != null) ? sel.getImprint() : "";
              boolean priorityBool = sel.getPriority();
              String priority = "No";
              if (priorityBool)
                priority = "Yes"; 
              String physicalReleaseDate = "";
              if (config.equals("eAlbum") && sel.getStreetDate() != null)
                physicalReleaseDate = MilestoneHelper.getFormatedDate(sel.getStreetDate()); 
              String radioImpactDate = "";
              ImpactDate earliestImpactDate = new ImpactDate();
              radioImpactDate = MilestoneHelper.getFormatedDate(sel.getImpactDate());
              if (radioImpactDate.equals("")) {
                if (sel.getImpactDates().size() != 0)
                  for (int i = 0; i < sel.getImpactDates().size(); i++) {
                    ImpactDate iDate = (ImpactDate)sel.getImpactDates().elementAt(i);
                    Calendar cals = iDate.getImpactDate();
                    if (i == 0)
                      earliestImpactDate = iDate; 
                    if (cals.before(earliestImpactDate.getImpactDate()))
                      earliestImpactDate = iDate; 
                  }  
                radioImpactDate = MilestoneHelper.getFormatedDate(earliestImpactDate.getImpactDate());
              } 
              String prefix = "";
              prefix = SelectionManager.getLookupObjectValue(sel.getPrefixID());
              if (!prefix.equals(""))
                prefix = String.valueOf(prefix) + "-"; 
              String selectionID = "";
              selectionID = sel.getSelectionNo();
              String localProductNumber = String.valueOf(prefix) + selectionID;
              String upc = (sel.getUpc() != null) ? sel.getUpc() : "";
              upc = MilestoneHelper_2.getRMSReportFormat(upc, "UPC", sel.getIsDigital());
              String pfmComments = "";
              Pfm pfm = new Pfm();
              pfm = SelectionManager.getInstance().getPfm(sel
                  .getSelectionID());
              if (pfm != null && 
                pfm.getComments() != null)
                pfmComments = pfm.getComments(); 
              String selComments2 = "";
              selComments2 = (sel.getSelectionComments() != null) ? sel.getSelectionComments() : "";
              String selectionScreenComments = selComments2.replace('\n', ' ');
              int subTableRows = 2;
              if (pfmComments.trim().length() > 0)
                subTableRows++; 
              if (selectionScreenComments.trim().length() > 0)
                subTableRows++; 
              int extraLines = 0;
              nextRow = 0;
              subTable = new DefaultTableLens(subTableRows, 14);
              columnHeaderTable.setColWidth(0, 70);
              subTable.setColWidth(0, 70);
              subTable.setColWidth(1, 150);
              subTable.setColWidth(2, 65);
              subTable.setColWidth(3, 150);
              subTable.setColWidth(4, 65);
              subTable.setColWidth(5, 65);
              subTable.setColWidth(6, 105);
              subTable.setColWidth(7, 80);
              subTable.setColWidth(8, 80);
              subTable.setColWidth(9, 80);
              subTable.setColWidth(10, 70);
              subTable.setColWidth(11, 70);
              subTable.setColWidth(12, 85);
              subTable.setColWidth(13, 70);
              boolean extendedLabelString = false;
              boolean extendedTitleString = false;
              boolean extendedArtistString = false;
              if (label.length() > 17)
                extendedLabelString = true; 
              if (title.length() > 20)
                extendedTitleString = true; 
              if (artist.length() > 20)
                extendedArtistString = true; 
              if (extendedLabelString || extendedTitleString || extendedArtistString) {
                subTable.setRowHeight(nextRow, 25);
              } else {
                subTable.setRowHeight(nextRow, 9);
              } 
              subTable.setSpan(nextRow, 0, new Dimension(1, 2));
              subTable.setRowFont(nextRow, new Font("Arial", 0, 7));
              subTable.setRowFont(nextRow + 1, new Font("Arial", 0, 7));
              subTable.setRowBorderColor(nextRow - 1, Color.lightGray);
              subTable.setRowBorderColor(nextRow, 0, Color.white);
              subTable.setRowBorderColor(nextRow, 1, Color.white);
              subTable.setRowBorderColor(nextRow, 2, Color.white);
              subTable.setRowBorderColor(nextRow, 3, Color.white);
              subTable.setRowBorderColor(nextRow, 4, Color.white);
              subTable.setRowBorderColor(nextRow, 5, Color.white);
              subTable.setRowBorderColor(nextRow, 6, Color.white);
              subTable.setRowBorderColor(nextRow, 7, Color.lightGray);
              subTable.setRowBorderColor(nextRow, 8, Color.lightGray);
              subTable.setRowBorderColor(nextRow, 9, Color.lightGray);
              subTable.setRowBorderColor(nextRow, 10, Color.lightGray);
              subTable.setRowBorderColor(nextRow, 11, Color.lightGray);
              subTable.setRowBorderColor(nextRow, 12, Color.lightGray);
              subTable.setRowBorderColor(nextRow, 13, Color.lightGray);
              subTable.setAlignment(nextRow, 0, 2);
              subTable.setAlignment(nextRow, 1, 2);
              subTable.setAlignment(nextRow, 2, 2);
              subTable.setAlignment(nextRow, 3, 2);
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
              subTable.setObject(nextRow, 0, digitalReleaseDate);
              subTable.setObject(nextRow, 1, artist);
              subTable.setFont(nextRow, 1, new Font("Arial", 1, 7));
              subTable.setObject(nextRow, 2, explicit);
              subTable.setObject(nextRow, 3, releasingFamilyString);
              subTable.setObject(nextRow, 4, priority);
              if (config.equals("eAlbum")) {
                subTable.setObject(nextRow, 5, physicalReleaseDate);
              } else {
                subTable.setObject(nextRow, 5, radioImpactDate);
              } 
              subTable.setObject(nextRow, 6, localProductNumber);
              subTable.setObject(nextRow + 1, 1, title);
              subTable.setFont(nextRow + 1, 1, new Font("Arial", 1, 7));
              subTable.setObject(nextRow + 1, 3, label);
              subTable.setObject(nextRow + 1, 6, upc);
              subTable.setAlignment(nextRow + 1, 1, 2);
              subTable.setAlignment(nextRow + 1, 3, 2);
              subTable.setAlignment(nextRow + 1, 6, 2);
              subTable.setAlignment(nextRow + 1, 7, 2);
              subTable.setAlignment(nextRow + 1, 8, 2);
              subTable.setAlignment(nextRow + 1, 9, 2);
              subTable.setAlignment(nextRow + 1, 10, 2);
              subTable.setAlignment(nextRow + 1, 11, 2);
              subTable.setAlignment(nextRow + 1, 12, 2);
              subTable.setAlignment(nextRow + 1, 13, 2);
              subTable.setRowBorderColor(nextRow + 1, Color.lightGray);
              for (int z = -1; z <= numColumns; z++) {
                subTable.setColBorder(nextRow, z, 4097);
                subTable.setColBorderColor(nextRow, z, Color.lightGray);
              } 
              subTable.setObject(nextRow + 1, 1, sel.getTitle());
              String NOC = "";
              String RMS = "";
              String DFM = "";
              String GRE = "";
              String DPO = "";
              String WAE = "";
              String PFM = "";
              String BAR = "";
              String PRC = "";
              String NOCcom = "";
              String RMScom = "";
              String DFMcom = "";
              String GREcom = "";
              String DPOcom = "";
              String WAEcom = "";
              String PFMcom = "";
              String BARcom = "";
              String PRCcom = "";
              String taskDueDate = "", taskCompletionDate = "", taskVendor = "";
              ScheduledTask task = null;
              if (tasks != null)
                for (int z = 0; z < tasks.size(); z++) {
                  task = (ScheduledTask)tasks.get(z);
                  if (task != null) {
                    String taskStatus = task.getScheduledTaskStatus();
                    SimpleDateFormat dueDateFormatter = new SimpleDateFormat("M/dd");
                    String dueDate = "";
                    if (task.getDueDate() != null)
                      dueDate = String.valueOf(dueDateFormatter.format(task.getDueDate().getTime())) + 
                        " " + MilestoneHelper.getDayType(sel.getCalendarGroup(), task.getDueDate()); 
                    String completionDate = MilestoneHelper.getFormatedDate(task.getCompletionDate());
                    taskVendor = (task.getVendor() != null) ? (String.valueOf(task.getVendor()) + "\n") : "\n";
                    if (!completionDate.equals(""))
                      completionDate = String.valueOf(completionDate) + "\n"; 
                    if (taskStatus.equalsIgnoreCase("N/A"))
                      completionDate = "N/A\n"; 
                    String taskAbbrev = MilestoneHelper.getTaskAbbreivationNameById(task.getTaskAbbreviationID());
                    String taskComment = "";
                    if (task.getComments() != null && !task.getComments().equals(""))
                      taskComment = task.getComments(); 
                    if (taskAbbrev.equalsIgnoreCase("NOC")) {
                      NOC = dueDate;
                      NOCcom = String.valueOf(completionDate) + taskVendor;
                    } else if (taskAbbrev.equalsIgnoreCase("RMS")) {
                      RMS = dueDate;
                      RMScom = String.valueOf(completionDate) + taskVendor;
                    } else if (taskAbbrev.equalsIgnoreCase("DFM")) {
                      DFM = dueDate;
                      DFMcom = String.valueOf(completionDate) + taskVendor;
                    } else if (taskAbbrev.equalsIgnoreCase("PFM")) {
                      PFM = dueDate;
                      PFMcom = String.valueOf(completionDate) + taskVendor;
                    } else if (taskAbbrev.equalsIgnoreCase("GRE")) {
                      GRE = dueDate;
                      GREcom = String.valueOf(completionDate) + taskVendor;
                    } else if (taskAbbrev.equalsIgnoreCase("WAE")) {
                      WAE = dueDate;
                      WAEcom = String.valueOf(completionDate) + taskVendor;
                    } else if (taskAbbrev.equalsIgnoreCase("DPO")) {
                      DPO = dueDate;
                      DPOcom = String.valueOf(completionDate) + taskVendor;
                    } else if (taskAbbrev.equalsIgnoreCase("BAR")) {
                      BAR = dueDate;
                      BARcom = String.valueOf(completionDate) + taskVendor;
                    } else if (taskAbbrev.equalsIgnoreCase("PRC")) {
                      PRC = dueDate;
                      PRCcom = String.valueOf(completionDate) + taskVendor;
                    } 
                    task = null;
                  } 
                }  
              subTable.setRowBorderColor(nextRow + 1, Color.lightGray);
              for (int a = -1; a <= numColumns; a++) {
                subTable.setColBorder(nextRow, a, 4097);
                subTable.setColBorderColor(nextRow, a, Color.lightGray);
                subTable.setColBorder(nextRow + 1, a, 4097);
                subTable.setColBorderColor(nextRow + 1, a, Color.lightGray);
              } 
              subTable.setObject(nextRow, 7, NOC);
              subTable.setBackground(nextRow, 7, Color.lightGray);
              subTable.setObject(nextRow + 1, 7, NOCcom);
              subTable.setObject(nextRow, 8, DFM);
              subTable.setBackground(nextRow, 8, Color.lightGray);
              subTable.setObject(nextRow + 1, 8, DFMcom);
              subTable.setObject(nextRow, 9, GRE);
              subTable.setBackground(nextRow, 9, Color.lightGray);
              subTable.setObject(nextRow + 1, 9, GREcom);
              subTable.setBackground(nextRow, 10, Color.lightGray);
              subTable.setObject(nextRow, 10, WAE);
              subTable.setObject(nextRow + 1, 10, WAEcom);
              subTable.setBackground(nextRow, 11, Color.lightGray);
              subTable.setObject(nextRow, 11, DPO);
              subTable.setObject(nextRow + 1, 11, DPOcom);
              subTable.setObject(nextRow, 12, BAR);
              subTable.setBackground(nextRow, 12, Color.lightGray);
              subTable.setObject(nextRow + 1, 12, BARcom);
              subTable.setObject(nextRow, 13, PRC);
              subTable.setBackground(nextRow, 13, Color.lightGray);
              subTable.setObject(nextRow + 1, 13, PRCcom);
              Font holidayFont = new Font("Arial", 3, 8);
              Font nonHolidayFont = new Font("Arial", 1, 8);
              for (int colIdx = 7; colIdx <= 13; colIdx++) {
                String dueDate = subTable.getObject(nextRow, colIdx).toString();
                if (dueDate != null && dueDate.length() > 0) {
                  char lastChar = dueDate.charAt(dueDate.length() - 1);
                  if (Character.isLetter(lastChar)) {
                    subTable.setFont(nextRow, colIdx, holidayFont);
                  } else {
                    subTable.setFont(nextRow, colIdx, nonHolidayFont);
                  } 
                } 
              } 
              if (selectionScreenComments.trim().length() > 0) {
                nextRow++;
                subTable.setRowAutoSize(true);
                subTable.setRowBorderColor(nextRow + 1, Color.lightGray);
                subTable.setRowFont(nextRow + 1, new Font("Arial", 2, 7));
                subTable.setSpan(nextRow + 1, 0, new Dimension(14, 1));
                subTable.setAlignment(nextRow + 1, 0, 9);
                subTable.setObject(nextRow + 1, 0, "Selection Comments: " + selectionScreenComments);
                subTable.setRowBorderColor(nextRow + 1, Color.lightGray);
                subTable.setColLineWrap(2, true);
                subTable.setColBorderColor(nextRow + 1, -1, Color.lightGray);
                subTable.setColBorderColor(nextRow + 1, 1, Color.white);
                subTable.setColBorderColor(nextRow + 1, 13, Color.lightGray);
              } 
              if (pfmComments.trim().length() > 0) {
                if (selectionScreenComments.trim().length() > 0)
                  subTable.setRowBorderColor(nextRow + 1, Color.white); 
                nextRow++;
                subTable.setRowAutoSize(true);
                subTable.setRowBorderColor(nextRow + 1, Color.lightGray);
                subTable.setRowFont(nextRow + 1, new Font("Arial", 2, 7));
                subTable.setSpan(nextRow + 1, 0, new Dimension(14, 1));
                subTable.setAlignment(nextRow + 1, 0, 9);
                subTable.setObject(nextRow + 1, 0, "PFM Comments: " + pfmComments);
                subTable.setRowBorderColor(nextRow + 1, Color.lightGray);
                subTable.setColLineWrap(2, true);
                subTable.setColBorderColor(nextRow + 1, -1, Color.lightGray);
                subTable.setColBorderColor(nextRow + 1, 1, Color.white);
                subTable.setColBorderColor(nextRow + 1, 13, Color.lightGray);
              } 
              body = new SectionBand(report);
              double lfLineCount = 1.5D;
              if (label.length() > 17 || sel.getTitle().length() > 20 || sel.getArtist().length() > 20) {
                body.setHeight(1.5F);
              } else {
                body.setHeight(1.0F);
              } 
              if (pfmComments.length() > 0 || selectionScreenComments.length() > 0) {
                lfLineCount = 2.0D;
                if (pfmComments.length() > 500)
                  lfLineCount = 3.0D; 
                body.setHeight((float)lfLineCount);
              } 
              body.addTable(subTable, new Rectangle(800, 800));
              body.setBottomBorder(0);
              body.setTopBorder(0);
              body.setShrinkToFit(true);
              body.setVisible(true);
              group = new DefaultSectionLens(null, group, body);
            } 
            long l = System.currentTimeMillis();
          } 
        } 
        group = new DefaultSectionLens(hbandType, group, null);
        report.addSection(group, rowCountTable);
        if (!config.equals(saveConfig) || saveConfig.equals(""))
          report.addPageBreak(); 
        saveConfig = config;
        group = null;
      } 
    } catch (Exception e) {
      System.out.println(">>>>>>>>DigitalProductionScheduleForPrintSubHandler.fillUmeProdScheduleForPrint(): exception: " + e);
    } 
  }
  
  private static void setColBorderColor(DefaultTableLens table, int row, int columns, Color color) {
    for (int i = -1; i < columns; i++)
      table.setColBorderColor(row, i, color); 
  }
}


/* Location:              D:\Documents\NetBeansProjects\milestone2 Local.war!\WEB-INF\classes\co\\universal\milestone\DigitalProductionScheduleForPrintSubHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */