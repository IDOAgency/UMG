package WEB-INF.classes.com.universal.milestone;

import com.techempower.ComponentLog;
import com.techempower.gemini.Context;
import com.techempower.gemini.GeminiApplication;
import com.universal.milestone.Form;
import com.universal.milestone.LatinoProductionScheduleUpdateForPrintSubHandler;
import com.universal.milestone.MilestoneHelper;
import com.universal.milestone.MilestoneHelper_2;
import com.universal.milestone.Schedule;
import com.universal.milestone.ScheduledTask;
import com.universal.milestone.SecureHandler;
import com.universal.milestone.Selection;
import com.universal.milestone.SelectionArtistComparator;
import com.universal.milestone.SelectionManager;
import com.universal.milestone.SelectionSelectionNumberComparator;
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
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;
import javax.servlet.http.HttpServletResponse;

public class LatinoProductionScheduleUpdateForPrintSubHandler extends SecureHandler {
  public static final String COMPONENT_CODE = "hLat";
  
  public GeminiApplication application;
  
  public ComponentLog log;
  
  public LatinoProductionScheduleUpdateForPrintSubHandler(GeminiApplication application) {
    this.application = application;
    this.log = application.getLog("hLat");
  }
  
  public String getDescription() { return "Sub Report"; }
  
  protected static void fillLatinoProductionScheduleUpdateForPrint(XStyleSheet report, Context context) {
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
    Color SHADED_AREA_COLOR = Color.lightGray;
    try {
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
      SectionBand hbandType = new SectionBand(report);
      SectionBand hbandCategory = new SectionBand(report);
      SectionBand body = new SectionBand(report);
      SectionBand footer = new SectionBand(report);
      DefaultSectionLens group = null;
      Hashtable selTable = MilestoneHelper.groupSelectionsBySubConfigAndStreetDate(selections);
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
      int numDates = 0;
      for (int i = 0; i < configVector.size(); i++) {
        String configName = (configVector.elementAt(i) != null) ? (String)configVector.elementAt(i) : "";
        Hashtable dateTable = (Hashtable)selTable.get(configName);
        if (dateTable != null)
          numDates += dateTable.size(); 
      } 
      int numExtraRows = configVector.size() * 4 + numDates * 4 - configVector.size() + 4;
      int numSelections = 2 * selections.size();
      int numRows = numSelections + numExtraRows - configVector.size();
      DefaultTableLens table_contents = null;
      DefaultTableLens rowCountTable = null;
      DefaultTableLens columnHeaderTable = null;
      DefaultTableLens subTable = null;
      int totalCount = 0;
      int tenth = 1;
      for (int a = 0; a < configVector.size(); a++) {
        totalCount++;
        String configC = (String)configVector.get(a);
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
      int nextRow = 0;
      for (int n = 0; n < configVector.size(); n++) {
        String config = (String)configVector.elementAt(n);
        String configHeaderText = !config.trim().equals("") ? config : "Other";
        rowCountTable = new DefaultTableLens(2, 10000);
        table_contents = new DefaultTableLens(1, 12);
        nextRow = 0;
        hbandType = new SectionBand(report);
        hbandType.setHeight(0.95F);
        hbandType.setShrinkToFit(true);
        hbandType.setVisible(true);
        table_contents.setHeaderRowCount(0);
        table_contents.setColWidth(0, 95);
        table_contents.setColWidth(1, 83);
        table_contents.setColWidth(2, 150);
        table_contents.setColWidth(3, 270);
        table_contents.setColWidth(4, 180);
        table_contents.setColWidth(5, 80);
        table_contents.setColWidth(6, 140);
        table_contents.setColWidth(7, 85);
        table_contents.setColWidth(8, 85);
        table_contents.setColWidth(9, 80);
        table_contents.setColWidth(10, 80);
        table_contents.setColWidth(11, 140);
        table_contents.setSpan(nextRow, 0, new Dimension(12, 1));
        table_contents.setAlignment(nextRow, 0, 2);
        table_contents.setObject(nextRow, 0, configHeaderText);
        table_contents.setRowHeight(nextRow, 15);
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
        columnHeaderTable = new DefaultTableLens(1, 12);
        nextRow = 0;
        columnHeaderTable.setColWidth(0, 95);
        columnHeaderTable.setColWidth(1, 83);
        columnHeaderTable.setColWidth(2, 150);
        columnHeaderTable.setColWidth(3, 270);
        columnHeaderTable.setColWidth(4, 180);
        columnHeaderTable.setColWidth(5, 80);
        columnHeaderTable.setColWidth(6, 140);
        columnHeaderTable.setColWidth(7, 85);
        columnHeaderTable.setColWidth(8, 85);
        columnHeaderTable.setColWidth(9, 80);
        columnHeaderTable.setColWidth(10, 80);
        columnHeaderTable.setColWidth(11, 140);
        columnHeaderTable.setRowBorderColor(nextRow - 1, SHADED_AREA_COLOR);
        columnHeaderTable.setRowBorderColor(nextRow, SHADED_AREA_COLOR);
        columnHeaderTable.setRowBorderColor(nextRow + 1, SHADED_AREA_COLOR);
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
        columnHeaderTable.setRowFont(nextRow, new Font("Arial", 1, 9));
        columnHeaderTable.setObject(nextRow, 0, "Release\nDate");
        columnHeaderTable.setObject(nextRow, 1, "Retail\nPrice");
        columnHeaderTable.setObject(nextRow, 2, "Local Product #\nUPC");
        columnHeaderTable.setObject(nextRow, 3, "Artist\nTitle");
        columnHeaderTable.setObject(nextRow, 4, "Label");
        columnHeaderTable.setObject(nextRow, 5, "Quota");
        columnHeaderTable.setObject(nextRow, 6, "Vendor");
        columnHeaderTable.setObject(nextRow, 7, "Master\nto plant");
        columnHeaderTable.setObject(nextRow, 8, "Label\nCopy");
        columnHeaderTable.setObject(nextRow, 9, "Package\nFilm");
        columnHeaderTable.setObject(nextRow, 10, "Depot");
        columnHeaderTable.setObject(nextRow, 11, "Comments");
        hbandType.addTable(columnHeaderTable, new Rectangle(0, 24, 800, 39));
        hbandType.setBottomBorder(0);
        Hashtable dateTable = (Hashtable)selTable.get(config);
        if (dateTable != null) {
          Enumeration dates = dateTable.keys();
          Vector streetVector = new Vector();
          while (dates.hasMoreElements())
            streetVector.addElement(dates.nextElement()); 
          Collections.sort(streetVector, new StringDateComparator());
          for (int z = 0; z < streetVector.size(); z++) {
            String date = (String)streetVector.get(z);
            DefaultTableLens dateTableLens = new DefaultTableLens(1, 12);
            hbandCategory = new SectionBand(report);
            hbandCategory.setHeight(0.25F);
            hbandCategory.setShrinkToFit(true);
            hbandCategory.setVisible(true);
            hbandCategory.setBottomBorder(0);
            hbandCategory.setLeftBorder(0);
            hbandCategory.setRightBorder(0);
            hbandCategory.setTopBorder(0);
            nextRow = 0;
            dateTableLens.setObject(nextRow, 0, date);
            dateTableLens.setSpan(nextRow, 0, new Dimension(12, 1));
            dateTableLens.setRowFont(nextRow, new Font("Arial", 1, 12));
            dateTableLens.setRowHeight(nextRow, 15);
            dateTableLens.setColBorderColor(nextRow, -1, Color.white);
            dateTableLens.setColBorderColor(nextRow, 12, Color.white);
            dateTableLens.setColBorderColor(nextRow, 11, Color.white);
            dateTableLens.setRowBorderColor(nextRow, Color.white);
            dateTableLens.setRowBorderColor(nextRow - 1, Color.white);
            dateTableLens.setRowBackground(nextRow, Color.lightGray);
            dateTableLens.setRowForeground(nextRow, Color.black);
            hbandCategory.addTable(dateTableLens, new Rectangle(800, 800));
            hbandCategory.setBottomBorder(0);
            footer.setVisible(true);
            footer.setHeight(0.1F);
            footer.setShrinkToFit(false);
            footer.setBottomBorder(0);
            group = new DefaultSectionLens(null, group, footer);
            group = new DefaultSectionLens(null, group, hbandCategory);
            group = new DefaultSectionLens(null, group, footer);
            selections = (Vector)dateTable.get(date);
            if (selections == null)
              selections = new Vector(); 
            Object[] selectionsArray = selections.toArray();
            Arrays.sort(selectionsArray, new SelectionSelectionNumberComparator());
            Arrays.sort(selectionsArray, new SelectionTitleComparator());
            Arrays.sort(selectionsArray, new SelectionArtistComparator());
            for (int i = 0; i < selectionsArray.length; i++) {
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
              Selection sel = (Selection)selectionsArray[i];
              sel = SelectionManager.getInstance().getSelectionAndSchedule(sel.getSelectionID());
              nextRow = 0;
              subTable = new DefaultTableLens(2, 12);
              subTable.setColWidth(0, 95);
              subTable.setColWidth(1, 83);
              subTable.setColWidth(2, 150);
              subTable.setColWidth(3, 270);
              subTable.setColWidth(4, 180);
              subTable.setColWidth(5, 80);
              subTable.setColWidth(6, 140);
              subTable.setColWidth(7, 85);
              subTable.setColWidth(8, 85);
              subTable.setColWidth(9, 80);
              subTable.setColWidth(10, 80);
              subTable.setColWidth(11, 140);
              subTable.setColBorderColor(nextRow, Color.lightGray);
              subTable.setRowBorderColor(nextRow - 1, Color.lightGray);
              subTable.setRowBorderColor(nextRow, Color.lightGray);
              subTable.setRowBorder(266240);
              subTable.setColBorder(266240);
              subTable.setRowInsets(nextRow, new Insets(0, 0, 0, 0));
              subTable.setRowAlignment(nextRow, 2);
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
              String titleId = "";
              titleId = String.valueOf(sel.getSelectionNo());
              if (sel.getPrefixID() != null)
                titleId = String.valueOf(SelectionManager.getLookupObjectValue(sel.getPrefixID())) + " " + titleId; 
              String artist = "";
              artist = sel.getArtist();
              String comment = (sel.getSelectionComments() != null) ? sel.getSelectionComments() : "";
              String label = sel.getImprint();
              String pack = "";
              pack = sel.getSelectionPackaging();
              String title = sel.getTitle();
              String upc = (sel.getUpc() != null) ? sel.getUpc() : "";
              upc = MilestoneHelper_2.getRMSReportFormat(upc, "UPC", sel.getIsDigital());
              String selConfig = "";
              selConfig = (sel.getSelectionConfig() != null) ? sel.getSelectionConfig().getSelectionConfigurationAbbreviation() : "";
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
              String street = MilestoneHelper.getFormatedDate(sel.getStreetDate());
              String status = (sel.getSelectionStatus() != null && sel.getSelectionStatus().getName() != null) ? 
                sel.getSelectionStatus().getName() : "";
              if (status.equalsIgnoreCase("TBS")) {
                street = "TBS " + street;
              } else if (status.equalsIgnoreCase("In The Works")) {
                street = "ITW " + street;
              } 
              String vendor = (sel.getPlant() != null) ? sel.getPlant().getAbbreviation() : "";
              Schedule schedule = sel.getSchedule();
              Vector tasks = (schedule != null) ? schedule.getTasks() : null;
              ScheduledTask task = null;
              String LABEL = "";
              String MASTERS = "";
              String PACK = "";
              String DEPOT = "";
              String LABELcom = "";
              String MASTERScom = "";
              String PACKcom = "";
              String DEPOTcom = "";
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
                    if (taskAbbrev.equalsIgnoreCase("TPS")) {
                      MASTERS = dueDate;
                      MASTERScom = String.valueOf(completionDate) + taskComment;
                    } else if (taskAbbrev.equalsIgnoreCase("LC")) {
                      LABEL = dueDate;
                      LABELcom = String.valueOf(completionDate) + taskComment;
                    } else if (taskAbbrev.equalsIgnoreCase("FAP")) {
                      PACK = dueDate;
                      PACKcom = String.valueOf(completionDate) + taskComment;
                    } else if (taskAbbrev.equalsIgnoreCase("DEPO")) {
                      DEPOT = dueDate;
                      DEPOTcom = String.valueOf(completionDate) + taskComment;
                    } 
                    task = null;
                  } 
                }  
              String[] bigString = { String.valueOf(artist) + "\n" + title, 
                  comment, 
                  DEPOTcom };
              int[] bigLines = { 36, 
                  15, 
                  10 };
              int maxLines = 0;
              maxLines = MilestoneHelper.lineCountWCR(bigString, bigLines);
              if (sel.getSelectionStatus().getName().equalsIgnoreCase("In The Works") && maxLines == 0)
                maxLines = 1; 
              if (maxLines < 3 && !price.equals("") && !code.equals("") && !retail.equals(""))
                maxLines = 1; 
              String vSpacer = "";
              for (int k = 0; k < maxLines; k++)
                vSpacer = String.valueOf(vSpacer) + "\n"; 
              subTable.setRowBorderColor(nextRow, Color.lightGray);
              subTable.setRowBorderColor(nextRow + 1, Color.lightGray);
              subTable.setRowInsets(nextRow, new Insets(0, 0, 0, 0));
              subTable.setRowFont(nextRow + 1, new Font("Arial", 0, 8));
              subTable.setObject(nextRow, 0, street);
              subTable.setBackground(nextRow, 0, Color.white);
              subTable.setSpan(nextRow, 0, new Dimension(1, 2));
              subTable.setAlignment(nextRow, 0, 10);
              if (!code.equals("") && !retail.equals(""))
                subTable.setObject(nextRow, 1, String.valueOf(price) + "\n" + code + " \\ " + retail); 
              subTable.setBackground(nextRow, 1, Color.white);
              subTable.setSpan(nextRow, 1, new Dimension(1, 2));
              subTable.setAlignment(nextRow, 1, 10);
              subTable.setObject(nextRow, 2, String.valueOf(titleId) + "\n" + upc);
              subTable.setBackground(nextRow, 2, Color.white);
              subTable.setSpan(nextRow, 2, new Dimension(1, 2));
              subTable.setAlignment(nextRow, 2, 10);
              subTable.setObject(nextRow, 3, String.valueOf(artist) + "\n" + title);
              subTable.setBackground(nextRow, 3, Color.white);
              subTable.setSpan(nextRow, 3, new Dimension(1, 2));
              subTable.setAlignment(nextRow, 3, 10);
              subTable.setLineWrap(nextRow, 3, true);
              subTable.setObject(nextRow, 4, "                            Due Dates");
              subTable.setSpan(nextRow, 4, new Dimension(2, 1));
              subTable.setColBorderColor(nextRow, 4, Color.lightGray);
              subTable.setColBorderColor(nextRow, 5, Color.lightGray);
              subTable.setColBorder(nextRow, 4, 266240);
              subTable.setFont(nextRow, 4, new Font("Arial", 1, 9));
              subTable.setAlignment(nextRow, 4, 9);
              subTable.setRowHeight(nextRow, 9);
              subTable.setAlignment(nextRow + 1, 4, 10);
              subTable.setObject(nextRow + 1, 4, String.valueOf(label) + vSpacer);
              String quota = "";
              SelectionManager.getInstance().getSelectionManufacturingSubDetail(sel);
              try {
                if (sel.getPoQuantity() > 0) {
                  NumberFormat nf = NumberFormat.getInstance();
                  quota = nf.format(sel.getPoQuantity());
                } 
              } catch (Exception exception) {}
              subTable.setObject(nextRow + 1, 5, quota);
              subTable.setAlignment(nextRow + 1, 5, 10);
              subTable.setObject(nextRow + 1, 6, vendor);
              subTable.setColBorderColor(nextRow, 6, Color.lightGray);
              subTable.setColBorder(nextRow, 6, 266240);
              subTable.setAlignment(nextRow + 1, 6, 10);
              subTable.setObject(nextRow, 7, MASTERS);
              subTable.setColBorderColor(nextRow, 7, Color.lightGray);
              subTable.setColBorder(nextRow, 7, 266240);
              subTable.setFont(nextRow, 7, new Font("Arial", 1, 8));
              subTable.setAlignment(nextRow, 7, 10);
              subTable.setObject(nextRow, 8, LABEL);
              subTable.setColBorderColor(nextRow, 8, Color.lightGray);
              subTable.setColBorder(nextRow, 8, 266240);
              subTable.setFont(nextRow, 8, new Font("Arial", 1, 8));
              subTable.setAlignment(nextRow, 8, 10);
              subTable.setObject(nextRow, 9, PACK);
              subTable.setColBorderColor(nextRow, 9, Color.lightGray);
              subTable.setColBorder(nextRow, 9, 266240);
              subTable.setFont(nextRow, 9, new Font("Arial", 1, 8));
              subTable.setAlignment(nextRow, 9, 2);
              subTable.setAlignment(nextRow, 9, 10);
              subTable.setObject(nextRow, 10, DEPOT);
              subTable.setColBorderColor(nextRow, 10, Color.lightGray);
              subTable.setColBorder(nextRow, 10, 266240);
              subTable.setFont(nextRow, 10, new Font("Arial", 1, 8));
              subTable.setAlignment(nextRow, 10, 10);
              subTable.setObject(nextRow + 1, 7, MASTERScom);
              subTable.setAlignment(nextRow + 1, 7, 10);
              subTable.setObject(nextRow + 1, 8, LABELcom);
              subTable.setAlignment(nextRow + 1, 8, 10);
              subTable.setObject(nextRow + 1, 9, PACKcom);
              subTable.setAlignment(nextRow + 1, 9, 10);
              subTable.setObject(nextRow + 1, 10, DEPOTcom);
              subTable.setAlignment(nextRow + 1, 10, 10);
              subTable.setRowBackground(nextRow, Color.lightGray);
              subTable.setRowForeground(nextRow, Color.black);
              subTable.setBackground(nextRow, 11, Color.white);
              subTable.setSpan(nextRow, 11, new Dimension(1, 2));
              subTable.setObject(nextRow, 11, comment);
              subTable.setAlignment(nextRow, 11, 10);
              subTable.setRowFont(nextRow, new Font("Arial", 0, 8));
              Font holidayFont = new Font("Arial", 3, 8);
              for (int colIdx = 7; colIdx <= 10; colIdx++) {
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
                MASTERScom.length() > 10 || LABELcom.length() > 10 || 
                PACKcom.length() > 10 || DEPOTcom.length() > 10) {
                if (lfLineCount < maxLines * 0.3D)
                  lfLineCount = maxLines * 0.3D; 
                if (lfLineCount < (MASTERScom.length() / 7) * 0.3D)
                  lfLineCount = (MASTERScom.length() / 7) * 0.3D; 
                if (lfLineCount < (LABELcom.length() / 8) * 0.3D)
                  lfLineCount = (LABELcom.length() / 8) * 0.3D; 
                if (lfLineCount < (PACKcom.length() / 8) * 0.3D)
                  lfLineCount = (PACKcom.length() / 8) * 0.3D; 
                if (lfLineCount < (DEPOTcom.length() / 8) * 0.3D)
                  lfLineCount = (DEPOTcom.length() / 8) * 0.3D; 
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
        group = new DefaultSectionLens(hbandType, group, footer);
        report.addSection(group, rowCountTable);
        report.addPageBreak();
        group = null;
      } 
    } catch (Exception e) {
      System.out.println(">>>>>>>>ReportHandler(): exception: " + e);
    } 
  }
}


/* Location:              D:\Documents\NetBeansProjects\milestone2 Local.war!\WEB-INF\classes\co\\universal\milestone\LatinoProductionScheduleUpdateForPrintSubHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */