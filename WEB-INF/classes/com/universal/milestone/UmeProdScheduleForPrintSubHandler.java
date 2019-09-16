package WEB-INF.classes.com.universal.milestone;

import com.techempower.ComponentLog;
import com.techempower.EnhancedProperties;
import com.techempower.gemini.Context;
import com.techempower.gemini.GeminiApplication;
import com.universal.milestone.Form;
import com.universal.milestone.MilestoneHelper;
import com.universal.milestone.MilestoneHelper_2;
import com.universal.milestone.MonthYearComparator;
import com.universal.milestone.ScheduledTask;
import com.universal.milestone.SecureHandler;
import com.universal.milestone.Selection;
import com.universal.milestone.SelectionManager;
import com.universal.milestone.UmeProdScheduleForPrintSubHandler;
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
import javax.servlet.http.HttpServletResponse;

public class UmeProdScheduleForPrintSubHandler extends SecureHandler {
  public static final String COMPONENT_CODE = "hUMEsu";
  
  public static ComponentLog log;
  
  public UmeProdScheduleForPrintSubHandler(GeminiApplication application) {}
  
  public static void configure(EnhancedProperties props, GeminiApplication application) { log = application.getLog("hUMEsu"); }
  
  public String getDescription() { return "Sub Report"; }
  
  protected static void fillUmeProdScheduleForPrint(XStyleSheet report, Context context) {
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
    } catch (Exception exception) {}
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
      Color SHADED_AREA_COLOR = Color.lightGray;
      int DATA_FONT_SIZE = 7;
      int SMALL_HEADER_FONT_SIZE = 8;
      int numColumns = 19;
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
      int totalCount = 0;
      int tenth = 1;
      for (int a = 0; a < sortedConfigVector.size(); a++) {
        totalCount++;
        String configC = (String)sortedConfigVector.get(a);
        Hashtable monthTableC = (Hashtable)selTable.get(configC);
        if (monthTableC != null) {
          Enumeration monthsC = monthTableC.keys();
          Vector monthVectorC = new Vector();
          while (monthsC.hasMoreElements())
            monthVectorC.add((String)monthsC.nextElement()); 
          Object[] monthArrayC = (Object[])null;
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
        table_contents = new DefaultTableLens(1, 19);
        table_contents.setColWidth(0, 110);
        table_contents.setColWidth(1, 70);
        table_contents.setColWidth(2, 70);
        table_contents.setColWidth(3, 70);
        table_contents.setColWidth(4, 150);
        table_contents.setColWidth(5, 65);
        table_contents.setColWidth(6, 60);
        table_contents.setColWidth(7, 80);
        table_contents.setColWidth(8, 85);
        table_contents.setColWidth(9, 85);
        table_contents.setColWidth(10, 70);
        table_contents.setColWidth(11, 65);
        table_contents.setColWidth(12, 70);
        table_contents.setColWidth(13, 70);
        table_contents.setColWidth(14, 80);
        table_contents.setColWidth(15, 70);
        table_contents.setColWidth(16, 65);
        table_contents.setColWidth(17, 70);
        table_contents.setColWidth(18, 75);
        nextRow = 0;
        hbandType = new SectionBand(report);
        hbandType.setHeight(0.9F);
        hbandType.setShrinkToFit(false);
        hbandType.setVisible(true);
        int cols = 19;
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
        hbandType.addTable(table_contents, new Rectangle(0, 0, 800, 25));
        if (monthTable != null) {
          Enumeration months = monthTable.keys();
          Vector monthVector = new Vector();
          while (months.hasMoreElements())
            monthVector.add((String)months.nextElement()); 
          Object[] monthArray = (Object[])null;
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
            columnHeaderTable = new DefaultTableLens(1, 19);
            nextRow = 0;
            columnHeaderTable.setColWidth(0, 110);
            columnHeaderTable.setColWidth(1, 70);
            columnHeaderTable.setColWidth(2, 70);
            columnHeaderTable.setColWidth(3, 70);
            columnHeaderTable.setColWidth(4, 150);
            columnHeaderTable.setColWidth(5, 65);
            columnHeaderTable.setColWidth(6, 60);
            columnHeaderTable.setColWidth(7, 80);
            columnHeaderTable.setColWidth(8, 85);
            columnHeaderTable.setColWidth(9, 85);
            columnHeaderTable.setColWidth(10, 70);
            columnHeaderTable.setColWidth(11, 65);
            columnHeaderTable.setColWidth(12, 70);
            columnHeaderTable.setColWidth(13, 70);
            columnHeaderTable.setColWidth(14, 80);
            columnHeaderTable.setColWidth(15, 70);
            columnHeaderTable.setColWidth(16, 65);
            columnHeaderTable.setColWidth(17, 70);
            columnHeaderTable.setColWidth(18, 75);
            columnHeaderTable.setRowAlignment(nextRow, 2);
            columnHeaderTable.setObject(nextRow, 0, "Rls Date\nWeek\nProj No.");
            columnHeaderTable.setObject(nextRow, 1, "\nPrice\nArtist/Title");
            columnHeaderTable.setSpan(nextRow, 1, new Dimension(3, 1));
            columnHeaderTable.setObject(nextRow, 4, "Label\nProduct #\nUPC");
            columnHeaderTable.setObject(nextRow, 5, "Start\nMemo\nRec'd");
            columnHeaderTable.setObject(nextRow, 6, "\nP&L\nAprv'd");
            columnHeaderTable.setObject(nextRow, 7, "\nLegal\nClear.");
            columnHeaderTable.setObject(nextRow, 8, "Labelc./\nCredits\nto Prod.");
            columnHeaderTable.setObject(nextRow, 9, "Final\nPckg. Cpy.\nto Design");
            columnHeaderTable.setObject(nextRow, 10, "\nMech.\nRouting");
            columnHeaderTable.setObject(nextRow, 11, "\nManf.\nQnty");
            columnHeaderTable.setObject(nextRow, 12, "\nSolic.\nFilm");
            columnHeaderTable.setObject(nextRow, 13, "\n\nSeps");
            columnHeaderTable.setObject(nextRow, 14, "Mastering\ncomplt'd/\nPts. Ord.");
            columnHeaderTable.setObject(nextRow, 15, "Film\nto\nPrinter");
            columnHeaderTable.setObject(nextRow, 16, "Master\nrecvd/\nplant");
            columnHeaderTable.setObject(nextRow, 17, "Print\nat\nPlant");
            columnHeaderTable.setObject(nextRow, 18, "\nPlant\nShip");
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
            columnHeaderTable.setColBorderColor(nextRow, 14, Color.lightGray);
            columnHeaderTable.setColBorderColor(nextRow, 15, Color.lightGray);
            columnHeaderTable.setColBorderColor(nextRow, 16, Color.lightGray);
            columnHeaderTable.setColBorderColor(nextRow, 17, Color.lightGray);
            columnHeaderTable.setColBorderColor(nextRow, 18, Color.lightGray);
            columnHeaderTable.setRowBorderColor(nextRow, Color.lightGray);
            hbandType.addTable(columnHeaderTable, new Rectangle(0, 25, 800, 40));
            nextRow = 0;
            DefaultTableLens monthTableLens = new DefaultTableLens(1, 19);
            nextRow = 0;
            monthTableLens.setObject(nextRow, 0, monthNameString);
            monthTableLens.setSpan(nextRow, 0, new Dimension(19, 1));
            monthTableLens.setRowAlignment(nextRow, 1);
            monthTableLens.setRowFont(nextRow, new Font("Arial", 1, 12));
            monthTableLens.setRowHeight(nextRow, 14);
            monthTableLens.setColBorderColor(nextRow, -1, Color.white);
            monthTableLens.setColBorderColor(nextRow, 18, Color.white);
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
              String label = sel.getImprint();
              String localProductNumber = "";
              if (sel.getPrefixID() != null && SelectionManager.getLookupObjectValue(sel.getPrefixID()) != null)
                localProductNumber = SelectionManager.getLookupObjectValue(sel.getPrefixID()); 
              localProductNumber = String.valueOf(localProductNumber) + sel.getSelectionNo();
              String upc = (sel.getUpc() != null) ? sel.getUpc() : "";
              upc = MilestoneHelper_2.getRMSReportFormat(upc, "UPC", sel.getIsDigital());
              String selComments = "";
              selComments = (sel.getSelectionComments() != null) ? sel.getSelectionComments() : "";
              String newComment = selComments.replace('\n', ' ');
              int subTableRows = 2;
              if (newComment.length() > 0)
                subTableRows = 3; 
              String comments = "";
              String[] checkStrings = { comments, labelContact };
              int[] checkStringLengths = { 50, 10 };
              int extraLines = MilestoneHelper.lineCountWCR(checkStrings, checkStringLengths);
              if (extraLines <= 2)
                extraLines += ((extraLines == 1) ? 2 : 1); 
              Vector tasks = MilestoneHelper.getTasksForUmeProdScheduleReportAbbrev(sel);
              nextRow = 0;
              subTable = new DefaultTableLens(subTableRows, 19);
              subTable.setColWidth(0, 110);
              subTable.setColWidth(1, 70);
              subTable.setColWidth(2, 70);
              subTable.setColWidth(3, 70);
              subTable.setColWidth(4, 150);
              subTable.setColWidth(5, 65);
              subTable.setColWidth(6, 65);
              subTable.setColWidth(7, 80);
              subTable.setColWidth(8, 85);
              subTable.setColWidth(9, 85);
              subTable.setColWidth(10, 70);
              subTable.setColWidth(11, 70);
              subTable.setColWidth(12, 70);
              subTable.setColWidth(13, 70);
              subTable.setColWidth(14, 80);
              subTable.setColWidth(15, 70);
              subTable.setColWidth(16, 65);
              subTable.setColWidth(17, 70);
              subTable.setColWidth(18, 70);
              subTable.setRowHeight(nextRow, 9);
              subTable.setRowAlignment(nextRow, 10);
              subTable.setRowFont(nextRow, new Font("Arial", 0, 7));
              subTable.setRowFont(nextRow + 1, new Font("Arial", 0, 7));
              subTable.setRowBorderColor(nextRow, Color.lightGray);
              subTable.setRowBorderColor(nextRow - 1, Color.lightGray);
              subTable.setSpan(nextRow, 0, new Dimension(1, 2));
              subTable.setObject(nextRow, 0, 
                  String.valueOf(releaseDate) + "\n" + releaseWeek + "\n" + projectNo + "\n" + labelContact);
              subTable.setObject(nextRow, 1, sellCode);
              subTable.setObject(nextRow, 2, retailCode);
              subTable.setObject(nextRow, 3, price);
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
              subTable.setFont(nextRow, 4, new Font("Arial", 1, 8));
              subTable.setObject(nextRow, 4, "Due Dates");
              subTable.setBackground(nextRow, 4, Color.lightGray);
              for (int y = 0; y < extraLines - 2; y++)
                upc = String.valueOf(upc) + "\n"; 
              subTable.setObject(nextRow + 1, 4, String.valueOf(label) + "\n" + localProductNumber + "\n" + upc);
              String CLR = "";
              String CRD = "";
              String FPC = "";
              String MBR = "";
              String SEPS = "";
              String SFD = "";
              String TAPE = "";
              String PFS = "";
              String MQD = "";
              String PAP = "";
              String PSD = "";
              String SMR = "";
              String PL = "";
              String PO = "";
              String CLRcom = "";
              String CRDcom = "";
              String FPCcom = "";
              String MBRcom = "";
              String SEPScom = "";
              String SFDcom = "";
              String TAPEcom = "";
              String PFScom = "";
              String MQDcom = "";
              String PAPcom = "";
              String PSDcom = "";
              String SMRcom = "";
              String PLcom = "";
              String POcom = "";
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
                    if (taskAbbrev.equalsIgnoreCase("CLR")) {
                      CLR = dueDate;
                      CLRcom = String.valueOf(completionDate) + taskVendor;
                    } else if (taskAbbrev.equalsIgnoreCase("CRD")) {
                      CRD = dueDate;
                      CRDcom = String.valueOf(completionDate) + taskVendor;
                    } else if (taskAbbrev.equalsIgnoreCase("FPC")) {
                      FPC = dueDate;
                      FPCcom = String.valueOf(completionDate) + taskVendor;
                    } else if (taskAbbrev.equalsIgnoreCase("MBR")) {
                      MBR = dueDate;
                      MBRcom = String.valueOf(completionDate) + taskVendor;
                    } else if (taskAbbrev.equalsIgnoreCase("SEPS")) {
                      SEPS = dueDate;
                      SEPScom = String.valueOf(completionDate) + taskVendor;
                    } else if (taskAbbrev.equalsIgnoreCase("SFD")) {
                      SFD = dueDate;
                      SFDcom = String.valueOf(completionDate) + taskVendor;
                    } else if (taskAbbrev.equalsIgnoreCase("TAPE")) {
                      TAPE = dueDate;
                      TAPEcom = String.valueOf(completionDate) + taskVendor;
                    } else if (taskAbbrev.equalsIgnoreCase("PFS")) {
                      PFS = dueDate;
                      PFScom = String.valueOf(completionDate) + taskVendor;
                    } else if (taskAbbrev.equalsIgnoreCase("MQD")) {
                      MQD = dueDate;
                      MQDcom = String.valueOf(completionDate) + taskVendor;
                    } else if (taskAbbrev.equalsIgnoreCase("PAP")) {
                      PAP = dueDate;
                      PAPcom = String.valueOf(completionDate) + taskVendor;
                    } else if (taskAbbrev.equalsIgnoreCase("PSD")) {
                      PSD = dueDate;
                      PSDcom = String.valueOf(completionDate) + taskVendor;
                    } else if (taskAbbrev.equalsIgnoreCase("SMR")) {
                      SMR = dueDate;
                      SMRcom = String.valueOf(completionDate) + taskVendor;
                    } else if (taskAbbrev.equalsIgnoreCase("P&L")) {
                      PL = dueDate;
                      PLcom = String.valueOf(completionDate) + taskVendor;
                    } else if (taskAbbrev.equalsIgnoreCase("PO")) {
                      PO = dueDate;
                      POcom = String.valueOf(completionDate) + taskVendor;
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
              subTable.setObject(nextRow, 5, SMR);
              subTable.setBackground(nextRow, 5, Color.lightGray);
              subTable.setObject(nextRow + 1, 5, SMRcom);
              subTable.setObject(nextRow, 6, PL);
              subTable.setBackground(nextRow, 6, Color.lightGray);
              subTable.setObject(nextRow + 1, 6, PLcom);
              subTable.setObject(nextRow, 7, CLR);
              subTable.setBackground(nextRow, 7, Color.lightGray);
              subTable.setObject(nextRow + 1, 7, CLRcom);
              subTable.setObject(nextRow, 8, CRD);
              subTable.setBackground(nextRow, 8, Color.lightGray);
              subTable.setObject(nextRow + 1, 8, CRDcom);
              subTable.setObject(nextRow, 9, FPC);
              subTable.setBackground(nextRow, 9, Color.lightGray);
              subTable.setObject(nextRow + 1, 9, FPCcom);
              subTable.setObject(nextRow, 11, SFD);
              subTable.setBackground(nextRow, 11, Color.lightGray);
              subTable.setObject(nextRow + 1, 11, MQDcom);
              subTable.setObject(nextRow, 12, SFD);
              subTable.setBackground(nextRow, 12, Color.lightGray);
              subTable.setObject(nextRow + 1, 12, SFDcom);
              subTable.setObject(nextRow, 10, MBR);
              subTable.setBackground(nextRow, 10, Color.lightGray);
              subTable.setObject(nextRow + 1, 10, MBRcom);
              subTable.setObject(nextRow, 13, SEPS);
              subTable.setBackground(nextRow, 13, Color.lightGray);
              subTable.setObject(nextRow + 1, 13, SEPScom);
              subTable.setObject(nextRow, 14, PO);
              subTable.setBackground(nextRow, 14, Color.lightGray);
              subTable.setObject(nextRow + 1, 14, POcom);
              subTable.setObject(nextRow, 15, PFS);
              subTable.setBackground(nextRow, 15, Color.lightGray);
              subTable.setObject(nextRow + 1, 15, PFScom);
              subTable.setObject(nextRow, 16, TAPE);
              subTable.setBackground(nextRow, 16, Color.lightGray);
              subTable.setObject(nextRow + 1, 16, TAPEcom);
              subTable.setObject(nextRow, 17, PAP);
              subTable.setBackground(nextRow, 17, Color.lightGray);
              subTable.setObject(nextRow + 1, 17, PAPcom);
              subTable.setObject(nextRow, 18, PSD);
              subTable.setBackground(nextRow, 18, Color.lightGray);
              subTable.setObject(nextRow + 1, 18, PSDcom);
              Font holidayFont = new Font("Arial", 3, 8);
              Font nonHolidayFont = new Font("Arial", 1, 8);
              for (int colIdx = 5; colIdx <= 18; colIdx++) {
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
              if (newComment.length() > 0) {
                nextRow++;
                subTable.setRowAutoSize(true);
                subTable.setRowBorderColor(nextRow + 1, Color.lightGray);
                setColBorderColor(subTable, nextRow + 1, numColumns, SHADED_AREA_COLOR);
                subTable.setRowFont(nextRow + 1, new Font("Arial", 2, 7));
                subTable.setSpan(nextRow + 1, 0, new Dimension(1, 1));
                subTable.setAlignment(nextRow + 1, 0, 9);
                subTable.setObject(nextRow + 1, 0, "Comments:   ");
                subTable.setObject(nextRow + 1, 1, newComment);
                subTable.setSpan(nextRow + 1, 1, new Dimension(18, 1));
                subTable.setRowBorderColor(nextRow + 1, Color.lightGray);
                setColBorderColor(subTable, nextRow + 1, numColumns, SHADED_AREA_COLOR);
                subTable.setRowFont(nextRow + 1, new Font("Arial", 2, 7));
                subTable.setAlignment(nextRow + 1, 2, 9);
                subTable.setColLineWrap(2, true);
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
              } 
              nextRow += 2;
              body = new SectionBand(report);
              double lfLineCount = 1.5D;
              if (extraLines > 0 || label.length() > 17 || sel.getTitle().length() > 20) {
                body.setHeight(1.5F);
              } else {
                body.setHeight(1.0F);
              } 
              if (extraLines > 0 || label.length() > 13 || sel.getTitle().length() > 14 || 
                CLRcom.length() > 18 || CRDcom.length() > 18 || FPCcom.length() > 18 || 
                MBRcom.length() > 18 || SEPScom.length() > 18 || 
                SFDcom.length() > 18 || TAPEcom.length() > 18 || 
                PFScom.length() > 18 || MQDcom.length() > 18 || 
                PAPcom.length() > 18 || 
                PSDcom.length() > 18 || SMRcom.length() > 18) {
                if (lfLineCount < extraLines * 0.25D)
                  lfLineCount = extraLines * 0.25D; 
                if (lfLineCount < (CLRcom.length() / 7) * 0.25D)
                  lfLineCount = (CLRcom.length() / 7) * 0.25D; 
                if (lfLineCount < (CRDcom.length() / 8) * 0.25D)
                  lfLineCount = (CRDcom.length() / 8) * 0.25D; 
                if (lfLineCount < (FPCcom.length() / 8) * 0.25D)
                  lfLineCount = (FPCcom.length() / 8) * 0.25D; 
                if (lfLineCount < (MBRcom.length() / 8) * 0.25D)
                  lfLineCount = (MBRcom.length() / 8) * 0.25D; 
                if (lfLineCount < (sel.getTitle().length() / 8) * 0.25D)
                  lfLineCount = (sel.getTitle().length() / 8) * 0.25D; 
                if (lfLineCount < (SEPScom.length() / 7) * 0.25D)
                  lfLineCount = (SEPScom.length() / 7) * 0.25D; 
                if (lfLineCount < (SFDcom.length() / 7) * 0.25D)
                  lfLineCount = (SFDcom.length() / 7) * 0.25D; 
                if (lfLineCount < (TAPEcom.length() / 7) * 0.25D)
                  lfLineCount = (TAPEcom.length() / 7) * 0.25D; 
                if (lfLineCount < (PFScom.length() / 7) * 0.25D)
                  lfLineCount = (PFScom.length() / 7) * 0.25D; 
                if (lfLineCount < (MQDcom.length() / 7) * 0.25D)
                  lfLineCount = (MQDcom.length() / 7) * 0.25D; 
                if (lfLineCount < (PAPcom.length() / 7) * 0.25D)
                  lfLineCount = (PAPcom.length() / 7) * 0.25D; 
                if (lfLineCount < (PSDcom.length() / 7) * 0.25D)
                  lfLineCount = (PSDcom.length() / 7) * 0.25D; 
                body.setHeight((float)lfLineCount);
              } else if (extraLines > 0 || label.length() > 17 || sel.getTitle().length() > 20 || 
                CLRcom.length() > 5 || CRDcom.length() > 5 || FPCcom.length() > 5 || 
                MBRcom.length() > 5 || SEPScom.length() > 5 || 
                SFDcom.length() > 5 || TAPEcom.length() > 5 || 
                PFScom.length() > 5 || MQDcom.length() > 5 || 
                PAPcom.length() > 5 || 
                PSDcom.length() > 5 || SMRcom.length() > 5) {
                if (lfLineCount < extraLines * 0.25D)
                  lfLineCount = extraLines * 0.25D; 
                if (lfLineCount < (CLRcom.length() / 7) * 0.25D)
                  lfLineCount = (CLRcom.length() / 7) * 0.25D; 
                if (lfLineCount < (CRDcom.length() / 8) * 0.25D)
                  lfLineCount = (CRDcom.length() / 8) * 0.25D; 
                if (lfLineCount < (FPCcom.length() / 8) * 0.25D)
                  lfLineCount = (FPCcom.length() / 8) * 0.25D; 
                if (lfLineCount < (MBRcom.length() / 8) * 0.25D)
                  lfLineCount = (MBRcom.length() / 8) * 0.25D; 
                if (lfLineCount < (sel.getTitle().length() / 8) * 0.25D)
                  lfLineCount = (sel.getTitle().length() / 8) * 0.25D; 
                if (lfLineCount < (SEPScom.length() / 7) * 0.25D)
                  lfLineCount = (SEPScom.length() / 7) * 0.25D; 
                if (lfLineCount < (SFDcom.length() / 7) * 0.25D)
                  lfLineCount = (SFDcom.length() / 7) * 0.25D; 
                if (lfLineCount < (TAPEcom.length() / 7) * 0.25D)
                  lfLineCount = (TAPEcom.length() / 7) * 0.25D; 
                if (lfLineCount < (PFScom.length() / 7) * 0.25D)
                  lfLineCount = (PFScom.length() / 7) * 0.25D; 
                if (lfLineCount < (MQDcom.length() / 7) * 0.25D)
                  lfLineCount = (MQDcom.length() / 7) * 0.25D; 
                if (lfLineCount < (PAPcom.length() / 7) * 0.25D)
                  lfLineCount = (PAPcom.length() / 7) * 0.25D; 
                if (lfLineCount < (PSDcom.length() / 7) * 0.25D)
                  lfLineCount = (PSDcom.length() / 7) * 0.25D; 
                if (lfLineCount < (SMRcom.length() / 7) * 0.25D)
                  lfLineCount = (SMRcom.length() / 7) * 0.25D; 
                body.setHeight((float)lfLineCount);
              } else {
                body.setHeight(1.0F);
              } 
              if (newComment.length() > 0) {
                lfLineCount = 2.0D;
                if (newComment.length() > 500)
                  lfLineCount = 3.0D; 
                if (newComment.length() > 1000)
                  lfLineCount = 4.0D; 
                if (newComment.length() > 3000)
                  lfLineCount = 5.0D; 
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
        report.addPageBreak();
        group = null;
      } 
    } catch (Exception e) {
      System.out.println(">>>>>>>>UmeProdScheduleForPrintSubHandler.fillUmeProdScheduleForPrint(): exception: " + e);
    } 
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
    table_contents.setRowBorder(nextRow, 0);
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
    table_contents.setColBorder(nextRow, -1, 0);
    table_contents.setColBorderColor(nextRow, cols, Color.black);
    table_contents.setColBorder(nextRow, cols, 4097);
    table_contents.setRowBorderColor(nextRow, Color.white);
    table_contents.setRowBorder(nextRow, 0);
    return nextRow;
  }
  
  public static void removeLF(String theString) { theString.replace('\n', ' '); }
  
  private static void setColBorderColor(DefaultTableLens table, int row, int columns, Color color) {
    for (int i = -1; i < columns; i++)
      table.setColBorderColor(row, i, color); 
  }
}


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\UmeProdScheduleForPrintSubHandler.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */