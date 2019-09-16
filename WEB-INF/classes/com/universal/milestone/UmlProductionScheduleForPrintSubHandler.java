package WEB-INF.classes.com.universal.milestone;

import com.techempower.ComponentLog;
import com.techempower.gemini.Context;
import com.techempower.gemini.GeminiApplication;
import com.universal.milestone.Company;
import com.universal.milestone.Form;
import com.universal.milestone.MilestoneHelper;
import com.universal.milestone.Plant;
import com.universal.milestone.ReleasingFamily;
import com.universal.milestone.Schedule;
import com.universal.milestone.ScheduledTask;
import com.universal.milestone.SecureHandler;
import com.universal.milestone.Selection;
import com.universal.milestone.SelectionManager;
import com.universal.milestone.StringDateComparator;
import com.universal.milestone.UmlProductionScheduleForPrintSubHandler;
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

public class UmlProductionScheduleForPrintSubHandler extends SecureHandler {
  public static final String COMPONENT_CODE = "hPsp";
  
  public GeminiApplication application;
  
  public ComponentLog log;
  
  public UmlProductionScheduleForPrintSubHandler(GeminiApplication application) {
    this.application = application;
    this.log = application.getLog("hPsp");
  }
  
  public String getDescription() { return "Sub Report"; }
  
  protected static void fillUmlProductionScheduleForPrint(XStyleSheet report, Context context) {
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
    int NUM_COLUMNS = 17;
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
    Hashtable selTable = groupSelectionsByCompanyAndStreetDate(selections);
    Enumeration configs = selTable.keys();
    Vector configVector = new Vector();
    while (configs.hasMoreElements())
      configVector.addElement(configs.nextElement()); 
    int numConfigs = configVector.size();
    try {
      Collections.sort(configVector);
      Vector sortedConfigVector = MilestoneHelper.sortStrings(configVector);
      DefaultTableLens rowCountTable = null;
      DefaultTableLens columnHeaderTable = null;
      DefaultTableLens subTable = null;
      DefaultTableLens companyTableLens = null;
      DefaultTableLens dateTableLens = null;
      rowCountTable = new DefaultTableLens(2, 10000);
      int totalCount = 0;
      int tenth = 1;
      Vector selectionsC = new Vector();
      int counterC = 0;
      for (int n = 0; n < sortedConfigVector.size(); n++) {
        String configC = (configVector.elementAt(n) != null) ? (String)configVector.elementAt(n) : "";
        Hashtable monthTableC = (Hashtable)selTable.get(configC);
        Enumeration monthsC = monthTableC.keys();
        Vector monthVectorC = new Vector();
        while (monthsC.hasMoreElements())
          monthVectorC.add((String)monthsC.nextElement()); 
        Object[] monthArrayC = null;
        monthArrayC = monthVectorC.toArray();
        for (int x = 0; x < monthArrayC.length; x++) {
          String monthNameC = (String)monthArrayC[x];
          selectionsC = (Vector)monthTableC.get(monthNameC);
          counterC += selectionsC.size();
        } 
      } 
      totalCount = counterC;
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
        int nextRow = 0;
        companyTableLens = new DefaultTableLens(1, 17);
        hbandCategory = new SectionBand(report);
        hbandCategory.setHeight(0.25F);
        hbandCategory.setShrinkToFit(true);
        hbandCategory.setVisible(true);
        hbandCategory.setBottomBorder(0);
        hbandCategory.setLeftBorder(0);
        hbandCategory.setRightBorder(0);
        hbandCategory.setTopBorder(0);
        companyTableLens.setSpan(nextRow, 0, new Dimension(17, 1));
        companyTableLens.setObject(nextRow, 0, config);
        companyTableLens.setRowHeight(nextRow, 15);
        companyTableLens.setRowFont(nextRow, new Font("Arial", 1, 12));
        companyTableLens.setRowBackground(nextRow, SHADED_AREA_COLOR);
        companyTableLens.setRowForeground(nextRow, Color.black);
        companyTableLens.setRowBorderColor(nextRow, Color.white);
        companyTableLens.setRowBorderColor(nextRow - 1, Color.white);
        companyTableLens.setColBorderColor(nextRow, -1, Color.white);
        companyTableLens.setColBorderColor(nextRow, 0, Color.white);
        companyTableLens.setColBorderColor(nextRow, 16, Color.white);
        hbandCategory.addTable(companyTableLens, new Rectangle(800, 800));
        group = new DefaultSectionLens(null, group, spacer);
        group = new DefaultSectionLens(null, group, hbandCategory);
        group = new DefaultSectionLens(null, group, spacer);
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
        nextRow = 0;
        String configHeaderText = !config.trim().equals("") ? config : "Other";
        columnHeaderTable = new DefaultTableLens(1, 17);
        columnHeaderTable.setHeaderRowCount(0);
        columnHeaderTable.setColWidth(0, 120);
        columnHeaderTable.setColWidth(1, 150);
        columnHeaderTable.setColWidth(2, 300);
        columnHeaderTable.setColWidth(3, 120);
        columnHeaderTable.setColWidth(4, 40);
        columnHeaderTable.setColWidth(5, 100);
        columnHeaderTable.setColWidth(6, 60);
        columnHeaderTable.setColWidth(7, 60);
        columnHeaderTable.setColWidth(8, 60);
        columnHeaderTable.setColWidth(9, 60);
        columnHeaderTable.setColWidth(10, 60);
        columnHeaderTable.setColWidth(11, 60);
        columnHeaderTable.setColWidth(12, 60);
        columnHeaderTable.setColWidth(13, 60);
        columnHeaderTable.setColWidth(14, 60);
        columnHeaderTable.setColWidth(15, 60);
        columnHeaderTable.setColWidth(16, 60);
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
        columnHeaderTable.setObject(nextRow, 0, "*P&D\n Local Prod#");
        columnHeaderTable.setObject(nextRow, 1, "Releasing Family/\nLabel");
        columnHeaderTable.setObject(nextRow, 2, "Artist/Title\nComments");
        columnHeaderTable.setObject(nextRow, 3, "P.O.\nQty");
        columnHeaderTable.setObject(nextRow, 4, "Unit");
        columnHeaderTable.setObject(nextRow, 5, "Exploded Total");
        columnHeaderTable.setObject(nextRow, 6, "Qty\nDone");
        columnHeaderTable.setObject(nextRow, 7, "F/M");
        columnHeaderTable.setObject(nextRow, 8, "BOM");
        columnHeaderTable.setObject(nextRow, 9, "FAP");
        columnHeaderTable.setObject(nextRow, 10, "PRQ");
        columnHeaderTable.setObject(nextRow, 11, "TAPE");
        columnHeaderTable.setObject(nextRow, 12, "FILM");
        columnHeaderTable.setObject(nextRow, 13, "STIC");
        columnHeaderTable.setObject(nextRow, 14, "MC");
        columnHeaderTable.setObject(nextRow, 15, "PAP");
        columnHeaderTable.setObject(nextRow, 16, "PSD");
        setColBorderColor(columnHeaderTable, nextRow, 17, SHADED_AREA_COLOR);
        columnHeaderTable.setRowBorderColor(nextRow - 1, SHADED_AREA_COLOR);
        columnHeaderTable.setRowBorderColor(nextRow, SHADED_AREA_COLOR);
        columnHeaderTable.setRowFont(nextRow, new Font("Arial", 1, 8));
        columnHeaderTable.setRowBackground(nextRow, Color.white);
        columnHeaderTable.setRowForeground(nextRow, Color.black);
        hbandType.addTable(columnHeaderTable, new Rectangle(0, 10, 800, 39));
        hbandType.setBottomBorder(0);
        if (monthTable != null) {
          Enumeration months = monthTable.keys();
          Vector monthVector = new Vector();
          while (months.hasMoreElements())
            monthVector.add((String)months.nextElement()); 
          Object[] monthArray = null;
          monthArray = monthVector.toArray();
          Arrays.sort(monthArray, new StringDateComparator());
          for (int x = 0; x < monthArray.length; x++) {
            String monthName = (String)monthArray[x];
            String monthNameString = monthName;
            selections = (Vector)monthTable.get(monthName);
            if (selections != null) {
              MilestoneHelper.setSelectionSorting(selections, 12);
              Collections.sort(selections);
              MilestoneHelper.setSelectionSorting(selections, 4);
              Collections.sort(selections);
              MilestoneHelper.setSelectionSorting(selections, 3);
              Collections.sort(selections);
              MilestoneHelper.setSelectionSorting(selections, 24);
              Collections.sort(selections);
              MilestoneHelper.setSelectionSorting(selections, 1);
              Collections.sort(selections);
              MilestoneHelper.setSelectionSorting(selections, 16);
              Collections.sort(selections);
              MilestoneHelper.setSelectionSorting(selections, 8);
              Collections.sort(selections);
              MilestoneHelper.setSelectionSorting(selections, 6);
              Collections.sort(selections);
              footer.setVisible(true);
              footer.setHeight(0.1F);
              footer.setShrinkToFit(false);
              footer.setBottomBorder(0);
              hbandDate = new SectionBand(report);
              hbandDate.setHeight(0.25F);
              hbandDate.setShrinkToFit(true);
              hbandDate.setVisible(true);
              hbandDate.setBottomBorder(0);
              hbandDate.setLeftBorder(0);
              hbandDate.setRightBorder(0);
              hbandDate.setTopBorder(0);
              dateTableLens = new DefaultTableLens(1, 17);
              nextRow = 0;
              dateTableLens.setSpan(nextRow, 0, new Dimension(17, 1));
              dateTableLens.setObject(nextRow, 0, monthName);
              dateTableLens.setRowHeight(nextRow, 16);
              dateTableLens.setRowFont(nextRow, new Font("Arial", 1, 12));
              dateTableLens.setRowForeground(nextRow, Color.black);
              dateTableLens.setRowBorderColor(nextRow, Color.white);
              dateTableLens.setRowBorderColor(nextRow - 1, Color.white);
              dateTableLens.setColBorderColor(nextRow, -1, Color.white);
              dateTableLens.setColBorderColor(nextRow, 0, Color.white);
              dateTableLens.setColBorderColor(nextRow, 16, Color.white);
              dateTableLens.setRowBackground(nextRow, Color.white);
              hbandDate.addTable(dateTableLens, new Rectangle(800, 200));
              hbandDate.setBottomBorder(0);
              group = new DefaultSectionLens(null, group, hbandDate);
              MilestoneHelper.applyManufacturingToSelections(selections);
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
                artist = sel.getArtist().trim();
                String comment = (sel.getSelectionComments() != null) ? sel.getSelectionComments() : "";
                boolean bpad = false;
                bpad = sel.getPressAndDistribution();
                String pAndD = "";
                String prefix = "";
                if (sel.getPrefixID() != null)
                  prefix = String.valueOf(sel.getPrefixID().getAbbreviation()) + " "; 
                if (sel.getSelectionNo() != null)
                  if (bpad) {
                    pAndD = "*" + prefix + sel.getSelectionNo();
                  } else {
                    pAndD = String.valueOf(prefix) + sel.getSelectionNo();
                  }  
                String releasingFamily = "";
                if (sel.getReleaseFamilyId() > 0)
                  releasingFamily = ReleasingFamily.getName(sel.getReleaseFamilyId()); 
                String label = "";
                if (sel.getImprint() != null)
                  label = sel.getImprint(); 
                String title = "";
                if (sel.getTitle() != null)
                  title = sel.getTitle(); 
                Schedule schedule = sel.getSchedule();
                Vector tasks = (schedule != null) ? schedule.getTasks() : null;
                ScheduledTask task = null;
                String FM = "";
                String BOM = "";
                String FAP = "";
                String PRQ = "";
                String TAPE = "";
                String FILM = "";
                String STIC = "";
                String MC = "";
                String PAP = "";
                String PSD = "";
                Vector plants = sel.getManufacturingPlants();
                int plantSize = 1;
                int explodedTotal = 0;
                int poQtyInt = 0;
                String compQty = "0";
                int complqtyInt = 0;
                if (plants != null && plants.size() > 0)
                  plantSize = plants.size(); 
                for (int plantCount = 0; plantCount < plantSize; plantCount++) {
                  Plant p = null;
                  if (plants != null && plants.size() > 0)
                    p = (Plant)plants.get(plantCount); 
                  int currentPoQty = 0;
                  if (p != null && p.getOrderQty() > 0) {
                    currentPoQty = p.getOrderQty();
                    poQtyInt += currentPoQty;
                  } 
                  if (poQtyInt > 0 && sel.getNumberOfUnits() > 0)
                    explodedTotal += currentPoQty * sel.getNumberOfUnits(); 
                  if (p != null && p.getCompletedQty() > 0)
                    complqtyInt += p.getCompletedQty(); 
                } 
                String units = (sel.getNumberOfUnits() > 0) ? String.valueOf(sel.getNumberOfUnits()) : "";
                compQty = MilestoneHelper.formatQuantityWithCommas(String.valueOf(complqtyInt));
                String FMcom = "";
                String BOMcom = "";
                String FAPcom = "";
                String PRQcom = "";
                String TAPEcom = "";
                String FILMcom = "";
                String STICcom = "";
                String MCcom = "";
                String PAPcom = "";
                String PSDcom = "";
                String FMDaysLate = "";
                String BOMDaysLate = "";
                String FAPDaysLate = "";
                String PRQDaysLate = "";
                String TAPEDaysLate = "";
                String FILMDaysLate = "";
                String STICDaysLate = "";
                String MCDaysLate = "";
                String PAPDaysLate = "";
                String PSDDaysLate = "";
                if (tasks != null)
                  for (int j = 0; j < tasks.size(); j++) {
                    task = (ScheduledTask)tasks.get(j);
                    if (task != null && task.getDueDate() != null) {
                      SimpleDateFormat dueDateFormatter = new SimpleDateFormat("M/d");
                      String dueDate = String.valueOf(dueDateFormatter.format(task.getDueDate().getTime())) + 
                        " " + MilestoneHelper.getDayType(sel.getCalendarGroup(), task.getDueDate());
                      String completionDate = MilestoneHelper.getFormatedDate(task.getCompletionDate());
                      if (task.getScheduledTaskStatus().equals("N/A"))
                        completionDate = task.getScheduledTaskStatus(); 
                      String taskAbbrev = MilestoneHelper.getTaskAbbreivationNameById(task.getTaskAbbreviationID());
                      String taskComment = "";
                      if (taskAbbrev.equalsIgnoreCase("F/M")) {
                        FM = dueDate;
                        FMcom = String.valueOf(completionDate) + taskComment;
                        if (dueDate != null && completionDate != "")
                          FMDaysLate = getDaysLate(task); 
                      } else if (taskAbbrev.equalsIgnoreCase("BOM")) {
                        BOM = dueDate;
                        BOMcom = String.valueOf(completionDate) + taskComment;
                        if (dueDate != null && completionDate != "")
                          BOMDaysLate = getDaysLate(task); 
                      } else if (taskAbbrev.equalsIgnoreCase("FAP")) {
                        FAP = dueDate;
                        FAPcom = String.valueOf(completionDate) + taskComment;
                        if (dueDate != null && completionDate != "")
                          FAPDaysLate = getDaysLate(task); 
                      } else if (taskAbbrev.equalsIgnoreCase("PRQ")) {
                        PRQ = dueDate;
                        PRQcom = String.valueOf(completionDate) + taskComment;
                        if (dueDate != null && completionDate != "")
                          PRQDaysLate = getDaysLate(task); 
                      } else if (taskAbbrev.equalsIgnoreCase("TAPE")) {
                        TAPE = dueDate;
                        TAPEcom = String.valueOf(completionDate) + taskComment;
                        if (dueDate != null && completionDate != "")
                          TAPEDaysLate = getDaysLate(task); 
                      } else if (taskAbbrev.equalsIgnoreCase("FILM")) {
                        FILM = dueDate;
                        FILMcom = String.valueOf(completionDate) + taskComment;
                        if (dueDate != null && completionDate != "")
                          FILMDaysLate = getDaysLate(task); 
                      } else if (taskAbbrev.equalsIgnoreCase("STIC")) {
                        STIC = dueDate;
                        STICcom = String.valueOf(completionDate) + taskComment;
                        if (dueDate != null && completionDate != "")
                          STICDaysLate = getDaysLate(task); 
                      } else if (taskAbbrev.equalsIgnoreCase("M/C")) {
                        MC = dueDate;
                        MCcom = String.valueOf(completionDate) + taskComment;
                        if (dueDate != null && completionDate != "")
                          MCDaysLate = getDaysLate(task); 
                      } else if (taskAbbrev.equalsIgnoreCase("PAP")) {
                        PAP = dueDate;
                        PAPcom = String.valueOf(completionDate) + taskComment;
                        if (dueDate != null && completionDate != "")
                          PAPDaysLate = getDaysLate(task); 
                      } else if (taskAbbrev.equalsIgnoreCase("PSD")) {
                        PSD = dueDate;
                        PSDcom = String.valueOf(completionDate) + taskComment;
                        if (dueDate != null && completionDate != "")
                          PSDDaysLate = getDaysLate(task); 
                      } 
                      task = null;
                    } 
                  }  
                nextRow = 0;
                subTable = new DefaultTableLens(3, 17);
                subTable.setRowInsets(nextRow, new Insets(0, 0, 0, 0));
                setColBorderColor(subTable, nextRow, 17, SHADED_AREA_COLOR);
                subTable.setHeaderRowCount(0);
                subTable.setColWidth(0, 120);
                subTable.setColWidth(1, 150);
                subTable.setColWidth(2, 300);
                subTable.setColWidth(3, 120);
                subTable.setColWidth(4, 40);
                subTable.setColWidth(5, 100);
                subTable.setColWidth(6, 60);
                subTable.setColWidth(7, 60);
                subTable.setColWidth(8, 60);
                subTable.setColWidth(9, 60);
                subTable.setColWidth(10, 60);
                subTable.setColWidth(11, 60);
                subTable.setColWidth(12, 60);
                subTable.setColWidth(13, 60);
                subTable.setColWidth(14, 60);
                subTable.setColWidth(15, 60);
                subTable.setColWidth(16, 60);
                subTable.setRowBorderColor(nextRow - 1, Color.lightGray);
                subTable.setObject(nextRow, 0, String.valueOf(pAndD) + "\n" + sel.getReleaseType().getName());
                subTable.setBackground(nextRow, 0, Color.white);
                subTable.setSpan(nextRow, 0, new Dimension(1, 3));
                subTable.setRowAutoSize(true);
                subTable.setAlignment(nextRow, 0, 9);
                subTable.setFont(nextRow, 0, new Font("Arial", 1, 7));
                subTable.setSpan(nextRow, 1, new Dimension(1, 3));
                subTable.setObject(nextRow, 1, String.valueOf(releasingFamily) + " /\n " + label);
                subTable.setAlignment(nextRow, 1, 10);
                subTable.setBackground(nextRow, 1, Color.white);
                String[] checkStrings = { pAndD };
                int[] checkStringsLength = { 15 };
                int extraLines = MilestoneHelper.lineCountWCR(checkStrings, checkStringsLength);
                String[] commentString = { comment };
                int[] checkCommentLength = { 30 };
                int commentCounter = MilestoneHelper.lineCountWCR(commentString, checkCommentLength);
                extraLines = (extraLines <= 2) ? 0 : (extraLines - 2);
                for (int z = 0; z < extraLines; z++);
                subTable.setObject(nextRow, 2, String.valueOf(artist) + "\n" + title);
                subTable.setAlignment(nextRow, 2, 10);
                subTable.setBackground(nextRow, 2, Color.white);
                subTable.setSpan(nextRow, 2, new Dimension(1, 3));
                subTable.setAlignment(nextRow + 1, 4, 10);
                subTable.setRowHeight(nextRow, 9);
                subTable.setRowBorderColor(nextRow + 1, SHADED_AREA_COLOR);
                subTable.setRowBorderColor(nextRow + 2, SHADED_AREA_COLOR);
                subTable.setObject(nextRow, 3, "Due Date");
                subTable.setAlignment(nextRow, 3, 2);
                subTable.setFont(nextRow, 3, new Font("Arial", 1, 7));
                subTable.setSpan(nextRow, 3, new Dimension(4, 1));
                subTable.setColBorder(nextRow, 3, 266240);
                subTable.setRowBorderColor(nextRow, SHADED_AREA_COLOR);
                subTable.setObject(nextRow, 7, FM);
                subTable.setColBorderColor(nextRow, 7, SHADED_AREA_COLOR);
                subTable.setColBorder(nextRow, 7, 266240);
                subTable.setObject(nextRow, 8, BOM);
                subTable.setColBorderColor(nextRow, 8, SHADED_AREA_COLOR);
                subTable.setColBorder(nextRow, 8, 266240);
                subTable.setObject(nextRow, 9, FAP);
                subTable.setColBorderColor(nextRow, 9, SHADED_AREA_COLOR);
                subTable.setColBorder(nextRow, 9, 266240);
                subTable.setObject(nextRow, 10, PRQ);
                subTable.setColBorderColor(nextRow, 10, Color.lightGray);
                subTable.setColBorder(nextRow, 10, 266240);
                subTable.setObject(nextRow, 11, TAPE);
                subTable.setColBorderColor(nextRow, 11, Color.lightGray);
                subTable.setColBorder(nextRow, 11, 266240);
                subTable.setObject(nextRow, 12, FILM);
                subTable.setColBorderColor(nextRow, 12, SHADED_AREA_COLOR);
                subTable.setColBorder(nextRow, 12, 266240);
                subTable.setObject(nextRow, 13, STIC);
                subTable.setColBorderColor(nextRow, 13, SHADED_AREA_COLOR);
                subTable.setColBorder(nextRow, 13, 266240);
                subTable.setObject(nextRow, 14, MC);
                subTable.setColBorderColor(nextRow, 14, SHADED_AREA_COLOR);
                subTable.setColBorder(nextRow, 14, 266240);
                subTable.setObject(nextRow, 15, PAP);
                subTable.setColBorderColor(nextRow, 15, SHADED_AREA_COLOR);
                subTable.setColBorder(nextRow, 15, 266240);
                subTable.setObject(nextRow, 16, PSD);
                subTable.setColBorderColor(nextRow, 16, SHADED_AREA_COLOR);
                subTable.setColBorder(nextRow, 16, 266240);
                subTable.setFont(nextRow, 5, new Font("Arial", 1, 7));
                subTable.setFont(nextRow, 6, new Font("Arial", 1, 7));
                Font holidayFont = new Font("Arial", 3, 7);
                Font nonHolidayFont = new Font("Arial", 1, 7);
                for (int colIdx = 7; colIdx <= 16; colIdx++) {
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
                subTable.setObject(nextRow + 1, 3, MilestoneHelper.formatQuantityWithCommas(String.valueOf(poQtyInt)));
                subTable.setObject(nextRow + 1, 4, units);
                subTable.setObject(nextRow + 1, 5, MilestoneHelper.formatQuantityWithCommas(String.valueOf(explodedTotal)));
                subTable.setObject(nextRow + 1, 6, MilestoneHelper.formatQuantityWithCommas(compQty));
                subTable.setObject(nextRow + 1, 7, FMcom);
                subTable.setObject(nextRow + 1, 8, BOMcom);
                subTable.setObject(nextRow + 1, 9, FAPcom);
                subTable.setObject(nextRow + 1, 10, PRQcom);
                subTable.setObject(nextRow + 1, 11, TAPEcom);
                subTable.setObject(nextRow + 1, 12, FILMcom);
                subTable.setObject(nextRow + 1, 13, STICcom);
                subTable.setObject(nextRow + 1, 14, MCcom);
                subTable.setObject(nextRow + 1, 15, PAPcom);
                subTable.setObject(nextRow + 1, 16, PSDcom);
                subTable.setObject(nextRow + 2, 3, "Days Late");
                subTable.setAlignment(nextRow + 2, 3, 2);
                subTable.setFont(nextRow + 2, 3, new Font("Arial", 1, 7));
                subTable.setSpan(nextRow + 2, 3, new Dimension(4, 1));
                subTable.setObject(nextRow + 2, 7, FMDaysLate);
                subTable.setObject(nextRow + 2, 8, BOMDaysLate);
                subTable.setObject(nextRow + 2, 9, FAPDaysLate);
                subTable.setObject(nextRow + 2, 10, PRQDaysLate);
                subTable.setObject(nextRow + 2, 11, TAPEDaysLate);
                subTable.setObject(nextRow + 2, 12, FILMDaysLate);
                subTable.setObject(nextRow + 2, 13, STICDaysLate);
                subTable.setObject(nextRow + 2, 14, MCDaysLate);
                subTable.setObject(nextRow + 2, 15, PAPDaysLate);
                subTable.setObject(nextRow + 2, 16, PSDDaysLate);
                subTable.setAlignment(nextRow + 2, 7, 10);
                subTable.setAlignment(nextRow + 2, 8, 10);
                subTable.setAlignment(nextRow + 2, 9, 10);
                subTable.setAlignment(nextRow + 2, 10, 10);
                subTable.setAlignment(nextRow + 2, 11, 10);
                subTable.setAlignment(nextRow + 2, 12, 10);
                subTable.setAlignment(nextRow + 2, 13, 10);
                subTable.setAlignment(nextRow + 2, 14, 10);
                subTable.setAlignment(nextRow + 2, 15, 10);
                subTable.setAlignment(nextRow + 2, 16, 10);
                setColBorderColor(subTable, nextRow + 1, 17, SHADED_AREA_COLOR);
                setColBorderColor(subTable, nextRow + 2, 17, SHADED_AREA_COLOR);
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
                subTable.setRowFont(nextRow + 1, new Font("Arial", 0, 7));
                subTable.setRowFont(nextRow + 2, new Font("Arial", 0, 7));
                subTable.setRowBackground(nextRow, SHADED_AREA_COLOR);
                subTable.setRowForeground(nextRow, Color.black);
                subTable.setRowFont(nextRow, new Font("Arial", 0, 7));
                body = new SectionBand(report);
                double lfLineCount = 1.5D;
                if (extraLines > 0) {
                  body.setHeight(1.5F);
                } else {
                  body.setHeight(1.5F);
                } 
                body.addTable(subTable, new Rectangle(800, 800));
                body.setBottomBorder(0);
                body.setTopBorder(0);
                body.setShrinkToFit(true);
                body.setVisible(true);
                group = new DefaultSectionLens(null, group, body);
                group = new DefaultSectionLens(null, group, spacer);
              } 
            } 
          } 
        } 
      } 
      group = new DefaultSectionLens(hbandType, group, null);
      report.addSection(group, rowCountTable);
      report.addPageBreak();
      group = null;
    } catch (Exception e) {
      System.out.println(">>>>>>>>IdjProductionScheduleForPrintSubHandler(): exception: " + e);
    } 
  }
  
  private static void setColBorderColor(DefaultTableLens table, int row, int columns, Color color) {
    for (int i = -1; i < columns; i++)
      table.setColBorderColor(row, i, color); 
  }
  
  public static Hashtable groupSelectionsByCompanyAndStreetDate(Vector selections) {
    Hashtable companyTable = new Hashtable();
    if (selections == null)
      return companyTable; 
    for (int i = 0; i < selections.size(); i++) {
      Selection sel = (Selection)selections.elementAt(i);
      if (sel != null) {
        sel = SelectionManager.getInstance().getSelectionAndSchedule(sel.getSelectionID());
        String companyName = "";
        String dateString = "";
        Company company = sel.getCompany();
        if (company != null)
          companyName = (company.getName() == null) ? "" : company.getName(); 
        dateString = MilestoneHelper.getFormatedDate(sel.getStreetDate());
        Hashtable datesTable = (Hashtable)companyTable.get(companyName);
        if (datesTable == null) {
          datesTable = new Hashtable();
          companyTable.put(companyName, datesTable);
        } 
        Vector selectionsForDates = (Vector)datesTable.get(dateString);
        if (selectionsForDates == null) {
          selectionsForDates = new Vector();
          datesTable.put(dateString, selectionsForDates);
        } 
        selectionsForDates.add(sel);
      } 
    } 
    return companyTable;
  }
  
  private static String getDaysLate(ScheduledTask task) {
    Calendar d1 = task.getCompletionDate();
    Calendar d2 = task.getDueDate();
    if (d1.after(d2)) {
      long timer = d1.getTime().getTime() - d2.getTime().getTime();
      int days = (int)timer / 86400000 - 1;
      Integer daysInt = new Integer(days);
      return daysInt.toString();
    } 
    return "";
  }
}


/* Location:              D:\Documents\NetBeansProjects\milestone2 Local.war!\WEB-INF\classes\co\\universal\milestone\UmlProductionScheduleForPrintSubHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */