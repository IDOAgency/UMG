package WEB-INF.classes.com.universal.milestone;

import com.techempower.ComponentLog;
import com.techempower.gemini.Context;
import com.techempower.gemini.GeminiApplication;
import com.universal.milestone.Form;
import com.universal.milestone.MilestoneHelper;
import com.universal.milestone.MilestoneHelper_2;
import com.universal.milestone.MonthYearComparator;
import com.universal.milestone.ScheduledTask;
import com.universal.milestone.SecureHandler;
import com.universal.milestone.Selection;
import com.universal.milestone.StatusJSPupdate;
import com.universal.milestone.UmeCustomProdScheduleForPrintSubHandler;
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
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

public class UmeCustomProdScheduleForPrintSubHandler extends SecureHandler {
  public static final String COMPONENT_CODE = "hUsu";
  
  public GeminiApplication application;
  
  public ComponentLog log;
  
  public UmeCustomProdScheduleForPrintSubHandler(GeminiApplication application) {
    this.application = application;
    this.log = application.getLog("hUsu");
  }
  
  public String getDescription() { return "Sub Report"; }
  
  protected static void fillUmeCustomProdScheduleForPrint(XStyleSheet report, Context context) {
    int COL_LINE_STYLE = 4097;
    int HEADER_FONT_SIZE = 12;
    double ldLineVal = 0.2D;
    StatusJSPupdate statusJSPupdate = new StatusJSPupdate(context);
    statusJSPupdate.updateStatus(0, 0, "start_gathering", 0);
    statusJSPupdate.setInternalCounter(true);
    Vector selections = MilestoneHelper.getSelectionsForReport(context);
    statusJSPupdate.updateStatus(0, 0, "start_report", 0);
    MilestoneHelper.setSelectionSorting(selections, 12);
    Collections.sort(selections);
    MilestoneHelper.setSelectionSorting(selections, 4);
    Collections.sort(selections);
    MilestoneHelper.setSelectionSorting(selections, 22);
    Collections.sort(selections);
    MilestoneHelper.setSelectionSorting(selections, 10);
    Collections.sort(selections);
    MilestoneHelper.setSelectionSorting(selections, 1);
    Collections.sort(selections);
    Hashtable selTable = MilestoneHelper.groupSelectionsByTypeAndStreetDate(selections);
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
      SimpleDateFormat formatter = new SimpleDateFormat("MMMM d, yyyy");
      String todayLong = formatter.format(new Date());
      report.setElement("bottomdate", todayLong);
      int numMonths = 0;
      int numConfigs = 0;
      int numSelections = 0;
      Enumeration configEnums = selTable.keys();
      while (configEnums.hasMoreElements()) {
        String config = (String)configEnums.nextElement();
        numConfigs++;
        Hashtable monthTable = (Hashtable)selTable.get(config);
        if (monthTable != null) {
          Enumeration months = monthTable.keys();
          while (months.hasMoreElements()) {
            String monthName = (String)months.nextElement();
            numMonths++;
            Vector selectionCount = (Vector)monthTable.get(monthName);
            if (selectionCount != null)
              numSelections += selectionCount.size(); 
          } 
        } 
      } 
      int numRows = 0;
      numRows += numMonths * 4;
      numRows += numConfigs * 3;
      numRows += numSelections * 2;
      numRows--;
      int numColumns = 15;
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
      Collections.sort(configVector);
      Vector sortedConfigVector = MilestoneHelper.sortStrings(configVector);
      int nextRow = 0;
      for (int n = 0; n < sortedConfigVector.size(); n++) {
        String config = (String)sortedConfigVector.get(n);
        Hashtable monthTable = (Hashtable)selTable.get(config);
        rowCountTable = new DefaultTableLens(2, 10000);
        table_contents = new DefaultTableLens(1, 15);
        table_contents.setColWidth(0, 110);
        table_contents.setColWidth(1, 80);
        table_contents.setColWidth(2, 80);
        table_contents.setColWidth(3, 80);
        table_contents.setColWidth(4, 190);
        table_contents.setColWidth(5, 65);
        table_contents.setColWidth(6, 80);
        table_contents.setColWidth(7, 85);
        table_contents.setColWidth(8, 85);
        table_contents.setColWidth(9, 80);
        table_contents.setColWidth(10, 80);
        table_contents.setColWidth(11, 75);
        table_contents.setColWidth(12, 80);
        table_contents.setColWidth(13, 80);
        table_contents.setColWidth(14, 220);
        nextRow = 0;
        hbandType = new SectionBand(report);
        hbandType.setHeight(0.9F);
        hbandType.setShrinkToFit(false);
        hbandType.setVisible(true);
        int cols = 15;
        table_contents.setObject(nextRow, 0, "");
        table_contents.setSpan(nextRow, 0, new Dimension(cols, 1));
        table_contents.setRowHeight(nextRow, 15);
        table_contents.setRowBackground(nextRow, Color.white);
        for (int col = -1; col < cols; col++) {
          table_contents.setColBorderColor(nextRow, col, Color.black);
          table_contents.setColBorder(nextRow, col, 4097);
        } 
        table_contents.setRowBorderColor(nextRow, Color.black);
        table_contents.setRowBorder(nextRow, 266240);
        table_contents.setAlignment(nextRow, 0, 2);
        table_contents.setObject(nextRow, 0, config);
        table_contents.setRowFont(nextRow, new Font("Arial", 3, 12));
        nextRow++;
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
            hbandMonth = new SectionBand(report);
            hbandMonth.setHeight(0.35F);
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
            MilestoneHelper.setSelectionSorting(selections, 5);
            Collections.sort(selections);
            MilestoneHelper.setSelectionSorting(selections, 2);
            Collections.sort(selections);
            MilestoneHelper.setSelectionSorting(selections, 13);
            Collections.sort(selections);
            MilestoneHelper.setSelectionSorting(selections, 4);
            Collections.sort(selections);
            MilestoneHelper.setSelectionSorting(selections, 22);
            Collections.sort(selections);
            MilestoneHelper.setSelectionSorting(selections, 1);
            Collections.sort(selections);
            columnHeaderTable = new DefaultTableLens(1, 15);
            nextRow = 0;
            columnHeaderTable.setColWidth(0, 110);
            columnHeaderTable.setColWidth(1, 80);
            columnHeaderTable.setColWidth(2, 80);
            columnHeaderTable.setColWidth(3, 80);
            columnHeaderTable.setColWidth(4, 190);
            columnHeaderTable.setColWidth(5, 65);
            columnHeaderTable.setColWidth(6, 80);
            columnHeaderTable.setColWidth(7, 85);
            columnHeaderTable.setColWidth(8, 85);
            columnHeaderTable.setColWidth(9, 80);
            columnHeaderTable.setColWidth(10, 80);
            columnHeaderTable.setColWidth(11, 75);
            columnHeaderTable.setColWidth(12, 80);
            columnHeaderTable.setColWidth(13, 80);
            columnHeaderTable.setColWidth(14, 220);
            columnHeaderTable.setRowAlignment(nextRow, 2);
            columnHeaderTable.setObject(nextRow, 0, "Rls Date\nProj No.");
            columnHeaderTable.setObject(nextRow, 1, "\nArtist/Title");
            columnHeaderTable.setSpan(nextRow, 1, new Dimension(3, 1));
            columnHeaderTable.setObject(nextRow, 4, "Selection\nUPC");
            columnHeaderTable.setObject(nextRow, 5, "\nClear");
            columnHeaderTable.setObject(nextRow, 6, "Art\nSpecs\nClients");
            columnHeaderTable.setObject(nextRow, 7, "Label Copy to\nClient");
            columnHeaderTable.setObject(nextRow, 8, "\nAcct #");
            columnHeaderTable.setObject(nextRow, 9, "\nMaster\nrecvd");
            columnHeaderTable.setObject(nextRow, 10, "P.O.\nFrom Client");
            columnHeaderTable.setObject(nextRow, 11, "Graphic\nFilm");
            columnHeaderTable.setObject(nextRow, 12, "Place\nInitial\nOrder");
            columnHeaderTable.setObject(nextRow, 13, "Print\nat\nPlant");
            columnHeaderTable.setObject(nextRow, 14, "\n\nComments");
            columnHeaderTable.setRowFont(nextRow, new Font("Arial", 1, 9));
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
            columnHeaderTable.setColBorderColor(nextRow, 14, Color.lightGray);
            columnHeaderTable.setRowBorderColor(nextRow, Color.lightGray);
            hbandType.addTable(columnHeaderTable, new Rectangle(0, 25, 800, 40));
            nextRow = 0;
            DefaultTableLens monthTableLens = new DefaultTableLens(1, 15);
            nextRow = 0;
            monthTableLens.setObject(nextRow, 0, monthNameString);
            monthTableLens.setSpan(nextRow, 0, new Dimension(15, 1));
            monthTableLens.setRowAlignment(nextRow, 1);
            monthTableLens.setRowFont(nextRow, new Font("Arial", 1, 12));
            monthTableLens.setRowHeight(nextRow, 14);
            monthTableLens.setColBorderColor(nextRow, -1, Color.white);
            monthTableLens.setColBorderColor(Color.white);
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
              statusJSPupdate.updateStatus(numSelections, j, "start_report", 0);
              Selection sel = (Selection)selections.elementAt(j);
              String releaseDate = MilestoneHelper.getFormatedDate(sel.getStreetDate());
              String status = (sel.getSelectionStatus() != null && sel.getSelectionStatus().getName() != null) ? 
                sel.getSelectionStatus().getName() : "";
              if (status.equalsIgnoreCase("TBS")) {
                releaseDate = "TBS " + releaseDate;
              } else if (status.equalsIgnoreCase("In The Works")) {
                releaseDate = "ITW " + releaseDate;
              } 
              String releaseWeek = MilestoneHelper.getReleaseWeekString(sel);
              if (releaseWeek != null) {
                int slashIndex = releaseWeek.indexOf("/");
                if (slashIndex != -1)
                  releaseWeek = releaseWeek.substring(0, slashIndex).trim(); 
              } 
              String projectNo = sel.getProjectID();
              String labelContact = "";
              if (sel.getLabelContact() != null && sel.getLabelContact().getName() != null)
                labelContact = sel.getLabelContact().getName(); 
              String sellCode = (sel.getSellCode() != null) ? sel.getSellCode() : "   ";
              if (sellCode != null && sellCode.startsWith("-1"))
                sellCode = ""; 
              String retailCode = "";
              if (sel.getPriceCode() != null && sel.getPriceCode().getRetailCode() != null)
                retailCode = sel.getPriceCode().getRetailCode(); 
              String price = "";
              if (sel.getPriceCode() != null && sel.getPriceCode().getTotalCost() > 0.0F)
                price = "$" + MilestoneHelper.formatDollarPrice(sel.getPriceCode().getTotalCost()); 
              String label = (sel.getLabel() != null && sel.getLabel().getName() != null) ? 
                sel.getLabel().getName() : "";
              String selection = (sel.getSelectionNo() != null) ? sel.getSelectionNo() : "";
              String upc = (sel.getUpc() != null) ? sel.getUpc() : "";
              upc = MilestoneHelper_2.getRMSReportFormat(upc, "UPC", sel.getIsDigital());
              String comments = (sel.getSelectionComments() != null) ? sel.getSelectionComments() : "";
              String[] checkStrings = { comments, labelContact };
              int[] checkStringLengths = { 20, 10 };
              int extraLines = MilestoneHelper.lineCount(checkStrings, checkStringLengths);
              if (extraLines > 3)
                extraLines -= 4; 
              Vector tasks = MilestoneHelper.getTasksForUmeCustomProdScheduleReport(sel);
              nextRow = 0;
              subTable = new DefaultTableLens(2, 15);
              subTable.setColWidth(0, 110);
              subTable.setColWidth(1, 80);
              subTable.setColWidth(2, 80);
              subTable.setColWidth(3, 80);
              subTable.setColWidth(4, 190);
              subTable.setColWidth(5, 75);
              subTable.setColWidth(6, 80);
              subTable.setColWidth(7, 85);
              subTable.setColWidth(8, 85);
              subTable.setColWidth(9, 80);
              subTable.setColWidth(10, 80);
              subTable.setColWidth(11, 75);
              subTable.setColWidth(12, 80);
              subTable.setColWidth(13, 80);
              subTable.setColWidth(14, 210);
              subTable.setRowHeight(nextRow, 9);
              subTable.setRowAlignment(nextRow, 10);
              subTable.setRowFont(nextRow, new Font("Arial", 0, 8));
              subTable.setRowFont(nextRow + 1, new Font("Arial", 0, 8));
              subTable.setRowBorderColor(nextRow, Color.lightGray);
              subTable.setRowBorderColor(nextRow - 1, Color.lightGray);
              subTable.setSpan(nextRow, 0, new Dimension(1, 2));
              subTable.setObject(nextRow, 0, 
                  String.valueOf(releaseDate) + "\n" + projectNo + "\n" + labelContact);
              subTable.setBackground(nextRow, 1, Color.lightGray);
              subTable.setBackground(nextRow, 2, Color.lightGray);
              subTable.setBackground(nextRow, 3, Color.lightGray);
              subTable.setRowAlignment(nextRow + 1, 10);
              subTable.setRowBorderColor(nextRow + 1, Color.lightGray);
              for (int z = -1; z <= numColumns; z++) {
                subTable.setColBorder(nextRow, z, 4097);
                subTable.setColBorderColor(nextRow, z, Color.lightGray);
              } 
              subTable.setSpan(nextRow + 1, 1, new Dimension(3, 1));
              subTable.setObject(nextRow + 1, 1, String.valueOf(sel.getFlArtist()) + "\n" + sel.getTitle());
              subTable.setFont(nextRow, 4, new Font("Arial", 1, 9));
              subTable.setObject(nextRow, 4, "Due Dates");
              subTable.setBackground(nextRow, 4, Color.lightGray);
              for (int y = 0; y < extraLines; y++)
                upc = String.valueOf(upc) + "\n"; 
              subTable.setObject(nextRow + 1, 4, String.valueOf(selection) + "\n" + upc);
              String CLR = "";
              String ART = "";
              String LCC = "";
              String ACC = "";
              String RCM = "";
              String PO = "";
              String GFR = "";
              String PIO = "";
              String PAP = "";
              String CLRcom = "";
              String ARTcom = "";
              String LCCcom = "";
              String ACCcom = "";
              String RCMcom = "";
              String POcom = "";
              String GFRcom = "";
              String PIOcom = "";
              String PAPcom = "";
              String taskDueDate = "", taskCompletionDate = "", taskVendor = "";
              ScheduledTask task = null;
              if (tasks != null)
                for (int z = 0; z < tasks.size(); z++) {
                  task = (ScheduledTask)tasks.get(z);
                  if (task != null && task.getDueDate() != null) {
                    String taskStatus = task.getScheduledTaskStatus();
                    SimpleDateFormat dueDateFormatter = new SimpleDateFormat("M/dd");
                    String dueDate = String.valueOf(dueDateFormatter.format(task.getDueDate().getTime())) + 
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
                    if (taskAbbrev.equalsIgnoreCase("CLR")) {
                      CLR = dueDate;
                      CLRcom = String.valueOf(completionDate) + taskVendor;
                    } else if (taskAbbrev.equalsIgnoreCase("ART")) {
                      ART = dueDate;
                      ARTcom = String.valueOf(completionDate) + taskVendor;
                    } else if (taskAbbrev.equalsIgnoreCase("LCC")) {
                      LCC = dueDate;
                      LCCcom = String.valueOf(completionDate) + taskVendor;
                    } else if (taskAbbrev.equalsIgnoreCase("ACC")) {
                      ACC = dueDate;
                      ACCcom = String.valueOf(completionDate) + taskVendor;
                    } else if (taskAbbrev.equalsIgnoreCase("RCM")) {
                      RCM = dueDate;
                      RCMcom = String.valueOf(completionDate) + taskVendor;
                    } else if (taskAbbrev.equalsIgnoreCase("PO")) {
                      PO = dueDate;
                      POcom = String.valueOf(completionDate) + taskVendor;
                    } else if (taskAbbrev.equalsIgnoreCase("GFR")) {
                      GFR = dueDate;
                      GFRcom = String.valueOf(completionDate) + taskVendor;
                    } else if (taskAbbrev.equalsIgnoreCase("PIO")) {
                      PIO = dueDate;
                      PIOcom = String.valueOf(completionDate) + taskVendor;
                    } else if (taskAbbrev.equalsIgnoreCase("PAP")) {
                      PAP = dueDate;
                      PAPcom = String.valueOf(completionDate) + taskVendor;
                    } 
                    task = null;
                  } 
                }  
              subTable.setRowBorderColor(nextRow, Color.lightGray);
              subTable.setRowBorderColor(nextRow + 1, Color.lightGray);
              for (int a = -1; a <= numColumns; a++) {
                subTable.setColBorder(nextRow, a, 4097);
                subTable.setColBorderColor(nextRow, a, Color.lightGray);
                subTable.setColBorder(nextRow + 1, a, 4097);
                subTable.setColBorderColor(nextRow + 1, a, Color.lightGray);
              } 
              subTable.setObject(nextRow, 5, CLR);
              subTable.setBackground(nextRow, 5, Color.lightGray);
              subTable.setObject(nextRow + 1, 5, CLRcom);
              subTable.setObject(nextRow, 6, ART);
              subTable.setBackground(nextRow, 6, Color.lightGray);
              subTable.setObject(nextRow + 1, 6, ARTcom);
              subTable.setObject(nextRow, 7, LCC);
              subTable.setBackground(nextRow, 7, Color.lightGray);
              subTable.setObject(nextRow + 1, 7, LCCcom);
              subTable.setObject(nextRow, 8, ACC);
              subTable.setBackground(nextRow, 8, Color.lightGray);
              subTable.setObject(nextRow + 1, 8, ACCcom);
              subTable.setObject(nextRow, 9, RCM);
              subTable.setBackground(nextRow, 9, Color.lightGray);
              subTable.setObject(nextRow + 1, 9, RCMcom);
              subTable.setObject(nextRow, 10, PO);
              subTable.setBackground(nextRow, 10, Color.lightGray);
              subTable.setObject(nextRow + 1, 10, POcom);
              subTable.setObject(nextRow, 11, GFR);
              subTable.setBackground(nextRow, 11, Color.lightGray);
              subTable.setObject(nextRow + 1, 11, GFRcom);
              subTable.setObject(nextRow, 12, PIO);
              subTable.setBackground(nextRow, 12, Color.lightGray);
              subTable.setObject(nextRow + 1, 12, PIOcom);
              subTable.setObject(nextRow, 13, PAP);
              subTable.setBackground(nextRow, 13, Color.lightGray);
              subTable.setObject(nextRow + 1, 13, PAPcom);
              subTable.setAlignment(nextRow, numColumns - 1, 1);
              subTable.setAlignment(nextRow, numColumns - 1, 8);
              subTable.setSpan(nextRow, numColumns - 1, new Dimension(1, 2));
              subTable.setObject(nextRow, numColumns - 1, comments);
              Font holidayFont = new Font("Arial", 3, 9);
              Font nonHolidayFont = new Font("Arial", 1, 9);
              for (int colIdx = 5; colIdx <= 13; colIdx++) {
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
              nextRow += 2;
              body = new SectionBand(report);
              double lfLineCount = 1.5D;
              if (extraLines > 0 || label.length() > 17 || sel.getTitle().length() > 20) {
                body.setHeight(1.5F);
              } else {
                body.setHeight(1.0F);
              } 
              if (extraLines > 0 || label.length() > 17 || sel.getTitle().length() > 20 || 
                CLRcom.length() > 25 || ARTcom.length() > 25 || LCCcom.length() > 25 || 
                ACCcom.length() > 25 || 
                GFRcom.length() > 25 || RCMcom.length() > 25 || 
                POcom.length() > 25 || 
                PAPcom.length() > 25 || 
                PIOcom.length() > 25) {
                if (lfLineCount < extraLines * 0.2D)
                  lfLineCount = extraLines * 0.2D; 
                if (lfLineCount < (CLRcom.length() / 7) * 0.2D)
                  lfLineCount = (CLRcom.length() / 7) * 0.2D; 
                if (lfLineCount < (ARTcom.length() / 8) * 0.2D)
                  lfLineCount = (ARTcom.length() / 8) * 0.2D; 
                if (lfLineCount < (LCCcom.length() / 8) * 0.2D)
                  lfLineCount = (LCCcom.length() / 8) * 0.2D; 
                if (lfLineCount < (ACCcom.length() / 8) * 0.2D)
                  lfLineCount = (ACCcom.length() / 8) * 0.2D; 
                if (lfLineCount < (sel.getTitle().length() / 8) * 0.2D)
                  lfLineCount = (sel.getTitle().length() / 8) * 0.2D; 
                if (lfLineCount < (RCMcom.length() / 7) * 0.2D)
                  lfLineCount = (RCMcom.length() / 7) * 0.2D; 
                if (lfLineCount < (POcom.length() / 7) * 0.2D)
                  lfLineCount = (POcom.length() / 7) * 0.2D; 
                if (lfLineCount < (GFRcom.length() / 7) * 0.2D)
                  lfLineCount = (GFRcom.length() / 7) * 0.2D; 
                if (lfLineCount < (PAPcom.length() / 7) * 0.2D)
                  lfLineCount = (PAPcom.length() / 7) * 0.2D; 
                if (lfLineCount < (PIOcom.length() / 7) * 0.2D)
                  lfLineCount = (PIOcom.length() / 7) * 0.2D; 
                body.setHeight((float)lfLineCount);
              } else if (extraLines > 0 || label.length() > 17 || sel.getTitle().length() > 20 || 
                CLRcom.length() > 5 || ARTcom.length() > 5 || LCCcom.length() > 5 || 
                ACCcom.length() > 5 || POcom.length() > 5 || 
                GFRcom.length() > 5 || RCMcom.length() > 5 || 
                PIOcom.length() > 5 || 
                PAPcom.length() > 5) {
                if (lfLineCount < extraLines * 0.2D)
                  lfLineCount = extraLines * 0.2D; 
                if (lfLineCount < (CLRcom.length() / 7) * 0.2D)
                  lfLineCount = (CLRcom.length() / 7) * 0.2D; 
                if (lfLineCount < (ARTcom.length() / 8) * 0.2D)
                  lfLineCount = (ARTcom.length() / 8) * 0.2D; 
                if (lfLineCount < (LCCcom.length() / 8) * 0.2D)
                  lfLineCount = (LCCcom.length() / 8) * 0.2D; 
                if (lfLineCount < (ACCcom.length() / 8) * 0.2D)
                  lfLineCount = (ACCcom.length() / 8) * 0.2D; 
                if (lfLineCount < (sel.getTitle().length() / 8) * 0.2D)
                  lfLineCount = (sel.getTitle().length() / 8) * 0.2D; 
                if (lfLineCount < (RCMcom.length() / 7) * 0.2D)
                  lfLineCount = (RCMcom.length() / 7) * 0.2D; 
                if (lfLineCount < (POcom.length() / 7) * 0.2D)
                  lfLineCount = (POcom.length() / 7) * 0.2D; 
                if (lfLineCount < (GFRcom.length() / 7) * 0.2D)
                  lfLineCount = (GFRcom.length() / 7) * 0.2D; 
                if (lfLineCount < (PIOcom.length() / 7) * 0.2D)
                  lfLineCount = (PIOcom.length() / 7) * 0.2D; 
                if (lfLineCount < (PAPcom.length() / 7) * 0.2D)
                  lfLineCount = (PAPcom.length() / 7) * 0.2D; 
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
          } 
        } 
        group = new DefaultSectionLens(hbandType, group, null);
        report.addSection(group, rowCountTable);
        report.addPageBreak();
        group = null;
      } 
    } catch (Exception exception) {}
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
    nextRow += 2;
    table_contents.setObject(nextRow, 0, "");
    table_contents.setSpan(nextRow, 0, new Dimension(cols, 1));
    table_contents.setRowHeight(nextRow, 1);
    table_contents.setRowBackground(nextRow, Color.white);
    table_contents.setColBorderColor(nextRow, -1, Color.white);
    table_contents.setColBorder(nextRow, -1, 4097);
    table_contents.setColBorderColor(nextRow, cols, Color.black);
    table_contents.setColBorder(nextRow, cols, 4097);
    table_contents.setRowBorderColor(nextRow, Color.white);
    table_contents.setRowBorder(nextRow, 266240);
    return nextRow;
  }
}


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\UmeCustomProdScheduleForPrintSubHandler.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */