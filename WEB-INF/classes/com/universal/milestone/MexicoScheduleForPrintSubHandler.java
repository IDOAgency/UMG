package WEB-INF.classes.com.universal.milestone;

import com.techempower.ComponentLog;
import com.techempower.gemini.Context;
import com.techempower.gemini.GeminiApplication;
import com.universal.milestone.Form;
import com.universal.milestone.MexicoReportComparator;
import com.universal.milestone.MexicoScheduleForPrintSubHandler;
import com.universal.milestone.MilestoneHelper;
import com.universal.milestone.MilestoneHelper_2;
import com.universal.milestone.Plant;
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

public class MexicoScheduleForPrintSubHandler extends SecureHandler {
  public static final String COMPONENT_CODE = "hMCAProd";
  
  public GeminiApplication application;
  
  public ComponentLog log;
  
  public void MexicoScheduleForPrintSubHandler(GeminiApplication application) {
    this.application = application;
    this.log = application.getLog("hMCAProd");
  }
  
  public String getDescription() { return "Sub Report"; }
  
  protected static void fillMexicoScheduleForPrint(XStyleSheet report, Context context) {
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
    Color SHADED_AREA_COLOR = Color.lightGray;
    int NUM_COLUMNS = 0;
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
    Hashtable selTable = MilestoneHelper_2.groupSelectionsForMexicoProductionByTypeConfigAndStreetDate(selections);
    Enumeration configs = selTable.keys();
    Vector configVector = new Vector();
    while (configs.hasMoreElements())
      configVector.addElement(configs.nextElement()); 
    int numConfigs = configVector.size();
    try {
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
      for (int n = 0; n < configVector.size(); n++) {
        String configC = (configVector.elementAt(n) != null) ? (String)configVector.elementAt(n) : "";
        Vector selectionsVector = (Vector)selTable.get(configC);
        totalCount += selectionsVector.size();
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
      Form reportForm = (Form)context.getSessionValue("reportForm");
      Calendar beginStDate = (reportForm.getStringValue("beginDate") != null && reportForm.getStringValue("beginDate").length() > 0) ? 
        MilestoneHelper.getDate(reportForm.getStringValue("beginDate")) : null;
      Calendar endStDate = (reportForm.getStringValue("endDate") != null && reportForm.getStringValue("endDate").length() > 0) ? 
        MilestoneHelper.getDate(reportForm.getStringValue("endDate")) : null;
      report.setElement("crs_startdate", MilestoneHelper.getCustomFormatedDate(beginStDate, "d-MMM yy"));
      report.setElement("crs_enddate", MilestoneHelper.getCustomFormatedDate(endStDate, "d-MMM yy"));
      SimpleDateFormat formatter = new SimpleDateFormat("d-MMM yyyy");
      String todayLong = formatter.format(new Date());
      report.setElement("crs_bottomdate", "Version: " + todayLong);
      String a4Landscape = "11.75x8.25";
      report.setProperty("PageSize", a4Landscape);
      Object[] configArray = configVector.toArray();
      Arrays.sort(configArray, new MexicoReportComparator());
      Vector sortedConfigs = new Vector();
      for (int i = 0; i < configArray.length; i++)
        sortedConfigs.addElement((String)configArray[i]); 
      Vector displayCatalogNumberVector = new Vector();
      displayCatalogNumberVector.add("Promos in Spanish");
      displayCatalogNumberVector.add("Promos in English");
      displayCatalogNumberVector.add("Distributed Promos");
      for (int n = 0; n < sortedConfigs.size(); n++) {
        String configBefore = (sortedConfigs.elementAt(n) != null) ? (String)sortedConfigs.elementAt(n) : "";
        String config = configBefore.substring(0, configBefore.indexOf("|"));
        String dateName = configBefore.substring(configBefore.indexOf("|") + 1, configBefore.length());
        Vector selectionsForConfig = (Vector)selTable.get(configBefore);
        int numRows = 0;
        numRows += totalCount * 2;
        numRows += totalCount * 2;
        numRows += 5;
        hbandType = new SectionBand(report);
        hbandType.setHeight(0.95F);
        hbandType.setShrinkToFit(true);
        hbandType.setVisible(true);
        if (config.equals("Commercial")) {
          NUM_COLUMNS = 16;
        } else {
          NUM_COLUMNS = 14;
        } 
        boolean configException = false;
        if (config.equals("Distributed Commercial"))
          configException = true; 
        table_contents = new DefaultTableLens(1, NUM_COLUMNS);
        table_contents.setHeaderRowCount(0);
        table_contents.setColBorderColor(Color.lightGray);
        table_contents.setColWidth(0, 80);
        table_contents.setColWidth(1, 200);
        table_contents.setColWidth(2, 200);
        table_contents.setColWidth(3, 80);
        table_contents.setColWidth(4, 80);
        table_contents.setColWidth(5, 80);
        table_contents.setColWidth(6, 80);
        table_contents.setColWidth(7, 80);
        table_contents.setColWidth(8, 80);
        table_contents.setColWidth(9, 80);
        table_contents.setColWidth(10, 80);
        table_contents.setColWidth(11, 80);
        if (NUM_COLUMNS == 14) {
          table_contents.setColWidth(12, 150);
          table_contents.setColWidth(13, 150);
        } else {
          table_contents.setColWidth(12, 80);
          table_contents.setColWidth(13, 80);
          table_contents.setColWidth(14, 150);
          table_contents.setColWidth(15, 150);
        } 
        table_contents.setColBorderColor(Color.black);
        table_contents.setRowBorderColor(Color.black);
        table_contents.setRowBorder(4097);
        table_contents.setColBorder(4097);
        int nextRow = 0;
        if (NUM_COLUMNS == 14) {
          table_contents.setSpan(nextRow, 0, new Dimension(14, 1));
        } else {
          table_contents.setSpan(nextRow, 0, new Dimension(16, 1));
        } 
        table_contents.setAlignment(nextRow, 0, 2);
        if (config.equals(""))
          config = "Other"; 
        table_contents.setObject(nextRow, 0, config);
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
        columnHeaderTable = new DefaultTableLens(1, NUM_COLUMNS);
        columnHeaderTable.setHeaderRowCount(0);
        columnHeaderTable.setColBorderColor(Color.lightGray);
        columnHeaderTable.setColWidth(0, 80);
        columnHeaderTable.setColWidth(1, 200);
        columnHeaderTable.setColWidth(2, 200);
        columnHeaderTable.setColWidth(3, 80);
        columnHeaderTable.setColWidth(4, 80);
        columnHeaderTable.setColWidth(5, 80);
        columnHeaderTable.setColWidth(6, 80);
        columnHeaderTable.setColWidth(7, 80);
        columnHeaderTable.setColWidth(8, 80);
        columnHeaderTable.setColWidth(9, 80);
        columnHeaderTable.setColWidth(10, 80);
        columnHeaderTable.setColWidth(11, 80);
        if (NUM_COLUMNS == 14) {
          columnHeaderTable.setColWidth(12, 150);
          columnHeaderTable.setColWidth(13, 150);
        } else {
          columnHeaderTable.setColWidth(12, 80);
          columnHeaderTable.setColWidth(13, 80);
          columnHeaderTable.setColWidth(14, 150);
          columnHeaderTable.setColWidth(15, 150);
        } 
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
        if (NUM_COLUMNS == 16) {
          columnHeaderTable.setAlignment(nextRow, 14, 34);
          columnHeaderTable.setAlignment(nextRow, 15, 34);
        } 
        columnHeaderTable.setObject(nextRow, 0, "Price\nCode");
        if (displayCatalogNumberVector.contains(config)) {
          columnHeaderTable.setObject(nextRow, 1, "Catalog\nNumber");
        } else {
          columnHeaderTable.setObject(nextRow, 1, "UPC");
        } 
        columnHeaderTable.setObject(nextRow, 2, "Artist\nTitle");
        columnHeaderTable.setObject(nextRow, 3, "Label");
        columnHeaderTable.setObject(nextRow, 4, "Origin\nDate");
        columnHeaderTable.setObject(nextRow, 5, "Config");
        columnHeaderTable.setObject(nextRow, 6, "Initial\nQty");
        columnHeaderTable.setObject(nextRow, 7, "Vendor");
        columnHeaderTable.setObject(nextRow, 8, "Label\nCopy");
        columnHeaderTable.setObject(nextRow, 9, "Dat\nMaster");
        columnHeaderTable.setObject(nextRow, 10, "Artwork");
        columnHeaderTable.setObject(nextRow, 11, "Stock\nArrives");
        if (NUM_COLUMNS == 14) {
          columnHeaderTable.setObject(nextRow, 12, "Comments");
          columnHeaderTable.setObject(nextRow, 13, "Label\nManager");
        } else {
          columnHeaderTable.setObject(nextRow, 12, "Label\nMgr");
          columnHeaderTable.setObject(nextRow, 13, "Press\nMgr");
          columnHeaderTable.setObject(nextRow, 14, "Comments");
          columnHeaderTable.setObject(nextRow, 15, "Label\nManager");
        } 
        columnHeaderTable.setRowBorderColor(nextRow - 1, SHADED_AREA_COLOR);
        columnHeaderTable.setRowBorderColor(nextRow, SHADED_AREA_COLOR);
        columnHeaderTable.setRowFont(nextRow, new Font("Arial", 1, 8));
        columnHeaderTable.setRowBackground(nextRow, Color.white);
        columnHeaderTable.setRowForeground(nextRow, Color.black);
        hbandType.addTable(columnHeaderTable, new Rectangle(0, 24, 800, 45));
        hbandType.setBottomBorder(0);
        Hashtable totals = new Hashtable();
        monthTableLens = new DefaultTableLens(1, NUM_COLUMNS);
        hbandCategory = new SectionBand(report);
        hbandCategory.setHeight(0.25F);
        hbandCategory.setShrinkToFit(true);
        hbandCategory.setVisible(true);
        hbandCategory.setBottomBorder(0);
        hbandCategory.setLeftBorder(0);
        hbandCategory.setRightBorder(0);
        hbandCategory.setTopBorder(0);
        nextRow = 0;
        monthTableLens.setObject(nextRow, 0, reformatMexicoDate(dateName));
        monthTableLens.setFont(nextRow, 0, new Font("Arial", 1, 12));
        String MC = "";
        String TPS = "";
        String FAD = "";
        String STA = "";
        String LMD = "";
        String PMD = "";
        if (NUM_COLUMNS == 14 && !configException) {
          monthTableLens.setSpan(nextRow, 0, new Dimension(14, 1));
          monthTableLens.setColBorderColor(nextRow, 13, Color.white);
        } else {
          monthTableLens.setColWidth(0, 80);
          monthTableLens.setColWidth(1, 200);
          monthTableLens.setColWidth(2, 200);
          monthTableLens.setColWidth(3, 80);
          monthTableLens.setColWidth(4, 80);
          monthTableLens.setColWidth(5, 80);
          monthTableLens.setColWidth(6, 80);
          monthTableLens.setColWidth(7, 80);
          monthTableLens.setColWidth(8, 80);
          monthTableLens.setColWidth(9, 80);
          monthTableLens.setColWidth(10, 80);
          monthTableLens.setColWidth(11, 80);
          if (configException) {
            monthTableLens.setColWidth(12, 150);
            monthTableLens.setColWidth(13, 150);
            monthTableLens.setColBorderColor(nextRow, 13, Color.white);
          } else {
            monthTableLens.setColWidth(12, 80);
            monthTableLens.setColWidth(13, 80);
            monthTableLens.setColWidth(14, 150);
            monthTableLens.setColWidth(15, 150);
            monthTableLens.setColBorderColor(nextRow, 15, Color.white);
          } 
          monthTableLens.setSpan(nextRow, 0, new Dimension(2, 1));
          if (!dateName.equals("TBS")) {
            monthTableLens.setObject(nextRow, 6, "Due Dates");
            monthTableLens.setFont(nextRow, 6, new Font("Arial", 1, 8));
            monthTableLens.setSpan(nextRow, 6, new Dimension(2, 1));
            monthTableLens.setAlignment(nextRow, 6, 2);
            Selection tempSel = new Selection();
            for (int selCounter = 0; selCounter < selectionsForConfig.size(); selCounter++) {
              tempSel = (Selection)selectionsForConfig.elementAt(selCounter);
              Schedule schedule = tempSel.getSchedule();
              if (schedule != null)
                break; 
            } 
            if (tempSel != null) {
              Schedule schedule = tempSel.getSchedule();
              Vector tasks = (schedule != null) ? schedule.getTasks() : null;
              ScheduledTask task = null;
              if (tasks != null)
                for (int j = 0; j < tasks.size(); j++) {
                  task = (ScheduledTask)tasks.get(j);
                  if (task != null) {
                    String dueDate = "";
                    if (task.getDueDate() != null) {
                      SimpleDateFormat dueDateFormatter = new SimpleDateFormat(
                          "d-MMM");
                      dueDate = 
                        String.valueOf(dueDateFormatter.format(task.getDueDate().getTime())) + " " + 
                        MilestoneHelper.getDayType(tempSel.getCalendarGroup(), 
                          task.getDueDate());
                    } 
                    String taskAbbrev = 
                      MilestoneHelper.getTaskAbbreivationNameById(task.getTaskAbbreviationID());
                    if (taskAbbrev.equalsIgnoreCase("M/C"))
                      MC = dueDate; 
                    if (taskAbbrev.equalsIgnoreCase("TPS"))
                      TPS = dueDate; 
                    if (taskAbbrev.equalsIgnoreCase("FAD"))
                      FAD = dueDate; 
                    if (taskAbbrev.equalsIgnoreCase("STA"))
                      STA = dueDate; 
                    if (!configException) {
                      if (taskAbbrev.equalsIgnoreCase("LMD"))
                        LMD = dueDate; 
                      if (taskAbbrev.equalsIgnoreCase("PMD"))
                        PMD = dueDate; 
                    } 
                    task = null;
                  } 
                }  
            } 
            monthTableLens.setObject(nextRow, 8, MC);
            monthTableLens.setObject(nextRow, 9, TPS);
            monthTableLens.setObject(nextRow, 10, FAD);
            monthTableLens.setObject(nextRow, 11, STA);
            monthTableLens.setObject(nextRow, 12, LMD);
            monthTableLens.setObject(nextRow, 13, PMD);
            monthTableLens.setAlignment(nextRow, 8, 2);
            monthTableLens.setAlignment(nextRow, 9, 2);
            monthTableLens.setAlignment(nextRow, 10, 2);
            monthTableLens.setAlignment(nextRow, 11, 2);
            monthTableLens.setAlignment(nextRow, 12, 2);
            monthTableLens.setAlignment(nextRow, 13, 2);
            monthTableLens.setFont(nextRow, 8, new Font("Arial", 0, 8));
            monthTableLens.setFont(nextRow, 9, new Font("Arial", 0, 8));
            monthTableLens.setFont(nextRow, 10, new Font("Arial", 0, 8));
            monthTableLens.setFont(nextRow, 11, new Font("Arial", 0, 8));
            monthTableLens.setFont(nextRow, 12, new Font("Arial", 0, 8));
            monthTableLens.setFont(nextRow, 13, new Font("Arial", 0, 8));
          } 
        } 
        monthTableLens.setColBorderColor(SHADED_AREA_COLOR);
        monthTableLens.setColBorderColor(nextRow, -1, Color.white);
        monthTableLens.setColBorderColor(nextRow, 0, Color.white);
        monthTableLens.setRowHeight(nextRow, 13);
        monthTableLens.setRowBackground(nextRow, SHADED_AREA_COLOR);
        monthTableLens.setRowForeground(nextRow, Color.black);
        monthTableLens.setRowBorderColor(nextRow, SHADED_AREA_COLOR);
        monthTableLens.setRowBorderColor(nextRow - 1, SHADED_AREA_COLOR);
        hbandCategory.addTable(monthTableLens, new Rectangle(800, 800));
        footer.setVisible(true);
        footer.setHeight(0.1F);
        footer.setShrinkToFit(false);
        footer.setBottomBorder(0);
        group = new DefaultSectionLens(null, group, spacer);
        group = new DefaultSectionLens(null, group, hbandCategory);
        group = new DefaultSectionLens(null, group, spacer);
        if (selectionsForConfig == null)
          selectionsForConfig = new Vector(); 
        MilestoneHelper.setSelectionSorting(selectionsForConfig, 4);
        Collections.sort(selectionsForConfig);
        MilestoneHelper.setSelectionSorting(selectionsForConfig, 3);
        Collections.sort(selectionsForConfig);
        if (config.equals("Distributed Promos") || config.equals("Distributed Commercial")) {
          MilestoneHelper.setSelectionSorting(selectionsForConfig, 9);
          Collections.sort(selectionsForConfig);
        } 
        MilestoneHelper.applyManufacturingToSelections(selectionsForConfig);
        for (int i = 0; i < selectionsForConfig.size(); i++) {
          Selection sel = (Selection)selectionsForConfig.elementAt(i);
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
          Vector plants = sel.getManufacturingPlants();
          int plantSize = 1;
          if (plants != null && plants.size() > 0)
            plantSize = plants.size(); 
          Plant p = null;
          for (int plantCount = 0; plantCount < plantSize; plantCount++) {
            if (plants != null && plants.size() > 0)
              p = (Plant)plants.get(plantCount); 
          } 
          String titleId = "";
          titleId = sel.getTitleID();
          String internationalDate = "";
          String filler = "";
          String projectID = (sel.getProjectID() != null) ? 
            sel.getProjectID() : "";
          String artist = "";
          artist = sel.getFlArtist().trim();
          String comment = "";
          String commentStr = (sel.getSelectionComments() != null) ? 
            sel.getSelectionComments() : "";
          int subTableRows = 2;
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
          upc = MilestoneHelper_2.getRMSReportFormat(upc, "UPC", sel.getIsDigital());
          String selConfig = "";
          selConfig = (sel.getSelectionConfig() != null) ? sel.getSelectionConfig().getSelectionConfigurationAbbreviation() : "";
          String selSubConfig = "";
          selSubConfig = (sel.getSelectionSubConfig() != null) ? sel.getSelectionSubConfig().getSelectionSubConfigurationAbbreviation() : "";
          String poQty = (p != null && p.getOrderQty() > 0) ? String.valueOf(p.getOrderQty()) : "0";
          Integer integerPoQty = Integer.valueOf(poQty);
          if (integerPoQty == null)
            integerPoQty = new Integer(0); 
          Integer configTotal = (Integer)totals.get(selConfig);
          if (configTotal == null) {
            totals.put(selConfig, integerPoQty);
          } else {
            Integer newTotal = new Integer(configTotal.intValue() + integerPoQty.intValue());
            totals.put(selConfig, newTotal);
          } 
          String sellCode = (sel.getSellCode() != null) ? sel.getSellCode() : "";
          if (sellCode != null && sellCode.startsWith("-1"))
            sellCode = ""; 
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
          String MCcom = "";
          String TPScom = "";
          String FADcom = "";
          String STAcom = "";
          String LMDcom = "";
          String PMDcom = "";
          Schedule schedule = sel.getSchedule();
          Vector tasks = (schedule != null) ? schedule.getTasks() : null;
          ScheduledTask task = null;
          if (tasks != null)
            for (int j = 0; j < tasks.size(); j++) {
              task = (ScheduledTask)tasks.get(j);
              if (task != null) {
                String completionDate = "";
                if (task.getCompletionDate() != null) {
                  SimpleDateFormat completionDateFormatter = new SimpleDateFormat("d-MMM");
                  completionDate = completionDateFormatter.format(task.getCompletionDate().getTime());
                } 
                String taskAbbrev = MilestoneHelper.getTaskAbbreivationNameById(task.getTaskAbbreviationID());
                if (taskAbbrev.equalsIgnoreCase("M/C"))
                  MCcom = completionDate; 
                if (taskAbbrev.equalsIgnoreCase("TPS"))
                  TPScom = completionDate; 
                if (taskAbbrev.equalsIgnoreCase("FAD"))
                  FADcom = completionDate; 
                if (taskAbbrev.equalsIgnoreCase("STA"))
                  STAcom = completionDate; 
                if (taskAbbrev.equalsIgnoreCase("LMD"))
                  LMDcom = completionDate; 
                if (taskAbbrev.equalsIgnoreCase("PMD"))
                  PMDcom = completionDate; 
                task = null;
              } 
            }  
          nextRow = 0;
          subTable = new DefaultTableLens(subTableRows, NUM_COLUMNS);
          subTable.setRowInsets(nextRow, new Insets(0, 0, 0, 0));
          subTable.setColWidth(0, 80);
          subTable.setColWidth(1, 200);
          subTable.setColWidth(2, 200);
          subTable.setColWidth(3, 80);
          subTable.setColWidth(4, 80);
          subTable.setColWidth(5, 80);
          subTable.setColWidth(6, 80);
          subTable.setColWidth(7, 80);
          subTable.setColWidth(8, 80);
          subTable.setColWidth(9, 80);
          subTable.setColWidth(10, 80);
          subTable.setColWidth(11, 80);
          if (NUM_COLUMNS == 14) {
            subTable.setColWidth(12, 150);
            subTable.setColWidth(13, 150);
          } else {
            subTable.setColWidth(12, 80);
            subTable.setColWidth(13, 80);
            subTable.setColWidth(14, 150);
            subTable.setColWidth(15, 150);
          } 
          subTable.setRowBorderColor(nextRow - 1, Color.lightGray);
          subTable.setBackground(nextRow, 0, Color.white);
          subTable.setAlignment(nextRow, 0, 10);
          subTable.setSpan(nextRow, 0, new Dimension(1, 2));
          subTable.setSpan(nextRow, 1, new Dimension(1, 2));
          subTable.setSpan(nextRow, 2, new Dimension(1, 2));
          subTable.setSpan(nextRow, 3, new Dimension(1, 2));
          subTable.setSpan(nextRow, 4, new Dimension(1, 2));
          subTable.setSpan(nextRow, 5, new Dimension(1, 2));
          subTable.setSpan(nextRow, 6, new Dimension(1, 2));
          subTable.setSpan(nextRow, 7, new Dimension(1, 2));
          subTable.setSpan(nextRow, 8, new Dimension(1, 2));
          subTable.setSpan(nextRow, 9, new Dimension(1, 2));
          subTable.setSpan(nextRow, 10, new Dimension(1, 2));
          subTable.setSpan(nextRow, 11, new Dimension(1, 2));
          subTable.setSpan(nextRow, 12, new Dimension(1, 2));
          subTable.setSpan(nextRow, 13, new Dimension(1, 2));
          if (NUM_COLUMNS == 16) {
            subTable.setSpan(nextRow, 14, new Dimension(1, 2));
            subTable.setSpan(nextRow, 15, new Dimension(1, 2));
          } 
          setColBorderColor(subTable, nextRow, 0, SHADED_AREA_COLOR);
          setColBorderColor(subTable, nextRow, 1, SHADED_AREA_COLOR);
          setColBorderColor(subTable, nextRow, 2, SHADED_AREA_COLOR);
          setColBorderColor(subTable, nextRow, 3, SHADED_AREA_COLOR);
          setColBorderColor(subTable, nextRow, 4, SHADED_AREA_COLOR);
          setColBorderColor(subTable, nextRow, 5, SHADED_AREA_COLOR);
          setColBorderColor(subTable, nextRow, 6, SHADED_AREA_COLOR);
          setColBorderColor(subTable, nextRow, 7, SHADED_AREA_COLOR);
          setColBorderColor(subTable, nextRow, 8, SHADED_AREA_COLOR);
          setColBorderColor(subTable, nextRow, 9, SHADED_AREA_COLOR);
          setColBorderColor(subTable, nextRow, 10, SHADED_AREA_COLOR);
          setColBorderColor(subTable, nextRow, 11, SHADED_AREA_COLOR);
          setColBorderColor(subTable, nextRow, 12, SHADED_AREA_COLOR);
          setColBorderColor(subTable, nextRow, 13, SHADED_AREA_COLOR);
          setColBorderColor(subTable, nextRow, 14, SHADED_AREA_COLOR);
          if (NUM_COLUMNS == 16) {
            setColBorderColor(subTable, nextRow, 14, SHADED_AREA_COLOR);
            setColBorderColor(subTable, nextRow, 15, SHADED_AREA_COLOR);
            setColBorderColor(subTable, nextRow, 16, SHADED_AREA_COLOR);
          } 
          setColBorderColor(subTable, nextRow + 1, 0, SHADED_AREA_COLOR);
          setColBorderColor(subTable, nextRow + 1, 1, SHADED_AREA_COLOR);
          setColBorderColor(subTable, nextRow + 1, 2, SHADED_AREA_COLOR);
          setColBorderColor(subTable, nextRow + 1, 3, SHADED_AREA_COLOR);
          setColBorderColor(subTable, nextRow + 1, 4, SHADED_AREA_COLOR);
          setColBorderColor(subTable, nextRow + 1, 5, SHADED_AREA_COLOR);
          setColBorderColor(subTable, nextRow + 1, 6, SHADED_AREA_COLOR);
          setColBorderColor(subTable, nextRow + 1, 7, SHADED_AREA_COLOR);
          setColBorderColor(subTable, nextRow + 1, 8, SHADED_AREA_COLOR);
          setColBorderColor(subTable, nextRow + 1, 9, SHADED_AREA_COLOR);
          setColBorderColor(subTable, nextRow + 1, 10, SHADED_AREA_COLOR);
          setColBorderColor(subTable, nextRow + 1, 11, SHADED_AREA_COLOR);
          setColBorderColor(subTable, nextRow + 1, 12, SHADED_AREA_COLOR);
          setColBorderColor(subTable, nextRow + 1, 13, SHADED_AREA_COLOR);
          setColBorderColor(subTable, nextRow + 1, 14, SHADED_AREA_COLOR);
          if (NUM_COLUMNS == 16) {
            setColBorderColor(subTable, nextRow + 1, 14, SHADED_AREA_COLOR);
            setColBorderColor(subTable, nextRow + 1, 15, SHADED_AREA_COLOR);
            setColBorderColor(subTable, nextRow + 1, 16, SHADED_AREA_COLOR);
          } 
          subTable.setObject(nextRow, 0, sellCode);
          subTable.setFont(nextRow, 0, new Font("Arial", 0, 7));
          subTable.setAlignment(nextRow, 0, 2);
          String contact = "";
          contact = (sel.getLabelContact() != null) ? sel.getLabelContact().getName() : "";
          String otherContact = "";
          otherContact = (sel.getOtherContact() != null) ? sel.getOtherContact() : "";
          String typeString = (sel.getReleaseType() != null) ? sel.getReleaseType().getName() : "";
          if ((config.equals("Special Products") && typeString.equals("Promotional")) || 
            displayCatalogNumberVector.contains(config)) {
            subTable.setObject(nextRow, 1, String.valueOf(prefix) + sel.getSelectionNo());
          } else {
            subTable.setObject(nextRow, 1, upc);
          } 
          subTable.setBackground(nextRow, 1, Color.white);
          String[] check1a = { comment };
          int[] check1 = { 16 };
          String[] check2a = { artist };
          int[] check2 = { 35 };
          String[] check3a = { title };
          int[] check3 = { 35 };
          int extraLines = MilestoneHelper.lineCountWCR(check1a, check1) + MilestoneHelper.lineCountWCR(check2a, check2) + MilestoneHelper.lineCountWCR(check3a, check3);
          if (extraLines == 10) {
            extraLines = 9;
          } else if (extraLines > 10) {
            extraLines = 7;
          } 
          for (int z = 0; z < extraLines; z++)
            filler = String.valueOf(filler) + "\n"; 
          subTable.setObject(nextRow, 2, String.valueOf(artist) + "\n" + title);
          subTable.setAlignment(nextRow, 2, 10);
          subTable.setObject(nextRow, 3, sel.getSelectionTerritory());
          subTable.setAlignment(nextRow, 4, 10);
          String originDate = MilestoneHelper.getCustomFormatedDate(sel.getOriginDate(), "d-MMM");
          subTable.setObject(nextRow, 4, originDate);
          subTable.setObject(nextRow, 5, selConfig);
          subTable.setObject(nextRow, 6, poQty);
          String vendorString = "";
          if (p != null && p.getPlant() != null) {
            String plantNo = p.getPlant().getName();
            vendorString = p.getPlant().getName();
          } 
          subTable.setObject(nextRow, 7, vendorString);
          subTable.setObject(nextRow, 8, MCcom);
          subTable.setObject(nextRow, 9, TPScom);
          subTable.setObject(nextRow, 10, FADcom);
          subTable.setObject(nextRow, 11, STAcom);
          if (NUM_COLUMNS == 14) {
            subTable.setObject(nextRow, 12, commentStr);
            subTable.setObject(nextRow, 13, sel.getOtherContact());
          } else {
            subTable.setObject(nextRow, 12, LMDcom);
            subTable.setObject(nextRow, 13, PMDcom);
            subTable.setObject(nextRow, 14, commentStr);
            subTable.setObject(nextRow, 15, sel.getOtherContact());
          } 
          subTable.setFont(nextRow, 3, new Font("Arial", 1, 7));
          subTable.setFont(nextRow, 4, new Font("Arial", 1, 7));
          subTable.setFont(nextRow, 5, new Font("Arial", 1, 7));
          subTable.setFont(nextRow, 6, new Font("Arial", 1, 7));
          subTable.setFont(nextRow, 7, new Font("Arial", 1, 7));
          subTable.setFont(nextRow, 8, new Font("Arial", 1, 7));
          subTable.setFont(nextRow, 9, new Font("Arial", 1, 7));
          subTable.setFont(nextRow, 10, new Font("Arial", 1, 7));
          subTable.setFont(nextRow, 11, new Font("Arial", 1, 7));
          subTable.setFont(nextRow, 12, new Font("Arial", 1, 7));
          subTable.setFont(nextRow, 13, new Font("Arial", 1, 7));
          if (NUM_COLUMNS == 16) {
            subTable.setFont(nextRow, 14, new Font("Arial", 1, 7));
            subTable.setFont(nextRow, 15, new Font("Arial", 1, 7));
          } 
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
          if (NUM_COLUMNS == 16) {
            subTable.setAlignment(nextRow, 14, 2);
            subTable.setAlignment(nextRow, 15, 2);
          } 
          subTable.setRowBorderColor(nextRow, SHADED_AREA_COLOR);
          subTable.setRowBorderColor(nextRow + 1, SHADED_AREA_COLOR);
          subTable.setRowFont(nextRow, new Font("Arial", 0, 7));
          body = new SectionBand(report);
          double lfLineCount = 1.5D;
          body.setHeight(1.0F);
          body.addTable(subTable, new Rectangle(800, 800));
          body.setBottomBorder(0);
          body.setTopBorder(0);
          body.setShrinkToFit(true);
          body.setVisible(true);
          group = new DefaultSectionLens(null, group, body);
        } 
        if (NUM_COLUMNS == 16 || configException) {
          nextRow = 0;
          subTable = new DefaultTableLens(1, NUM_COLUMNS);
          subTable.setSpan(nextRow, 0, new Dimension(NUM_COLUMNS, 1));
          subTable.setRowBorder(0);
          printTotals(totals, subTable, nextRow);
          subTable.setRowFont(nextRow, new Font("Arial", 1, 7));
          setColBorders(nextRow, -1, NUM_COLUMNS - 1, subTable, 0);
          setColBorderColors(nextRow, -1, NUM_COLUMNS - 1, subTable, Color.white);
          subTable.setRowBorder(nextRow, 0);
          body = new SectionBand(report);
          body.setHeight(1.0F);
          body.addTable(subTable, new Rectangle(800, 800));
          body.setBottomBorder(0);
          body.setTopBorder(0);
          body.setShrinkToFit(true);
          body.setVisible(true);
          group = new DefaultSectionLens(null, group, body);
          nextRow = 0;
        } 
        group = new DefaultSectionLens(hbandType, group, null);
        report.addSection(group, rowCountTable);
        report.addPageBreak();
        group = null;
      } 
    } catch (Exception e) {
      System.out.println(">>>>>>>>fillMexicoScheduleForPrintSubHander(): exception: " + e);
    } 
  }
  
  public static String removeLF(String theString, int maxChars) { return theString.replace('\n', ' '); }
  
  private static void setColBorderColor(DefaultTableLens table, int row, int columns, Color color) {
    for (int i = -1; i < columns; i++)
      table.setColBorderColor(row, i, color); 
  }
  
  private static void printTotals(Hashtable unitsHashtable, DefaultTableLens subTable, int nextRow) {
    Enumeration keysEnum = unitsHashtable.keys();
    Vector keys = new Vector();
    int columnCount = 0;
    while (keysEnum.hasMoreElements())
      keys.add(keysEnum.nextElement()); 
    String totalsString = "Totals:     ";
    for (int i = 0; i < keys.size(); i++) {
      Integer idString = (Integer)unitsHashtable.get(keys.get(i));
      int total = idString.intValue();
      if (total > 0)
        totalsString = String.valueOf(totalsString) + keys.get(i) + ": " + MilestoneHelper.formatQuantityWithCommas(String.valueOf(total)) + "    "; 
    } 
    if (totalsString.length() > 12)
      subTable.setObject(nextRow, 0, totalsString); 
  }
  
  private static void setColBorderColors(int rowNum, int start, int end, DefaultTableLens table, Color color) {
    end++;
    for (int i = start; i < end; i++)
      table.setColBorderColor(rowNum, i, Color.white); 
  }
  
  private static void setColBorders(int rowNum, int start, int end, DefaultTableLens table, int size) {
    end++;
    for (int i = start; i < end; i++)
      table.setColBorder(rowNum, i, size); 
  }
  
  private static String reformatMexicoDate(String dateString) {
    if (dateString.equals("TBS"))
      return dateString; 
    Calendar dateCal = MilestoneHelper.getDate(dateString);
    return MilestoneHelper.getCustomFormatedDate(dateCal, "d-MMM yy");
  }
}


/* Location:              D:\Documents\NetBeansProjects\milestone2 Local.war!\WEB-INF\classes\co\\universal\milestone\MexicoScheduleForPrintSubHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */