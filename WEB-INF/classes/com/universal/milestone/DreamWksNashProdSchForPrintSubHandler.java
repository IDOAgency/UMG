package WEB-INF.classes.com.universal.milestone;

import com.techempower.ComponentLog;
import com.techempower.gemini.Context;
import com.techempower.gemini.GeminiApplication;
import com.universal.milestone.DreamWksNashProdSchForPrintSubHandler;
import com.universal.milestone.Form;
import com.universal.milestone.IntegerComparator;
import com.universal.milestone.MilestoneHelper;
import com.universal.milestone.Schedule;
import com.universal.milestone.ScheduledTask;
import com.universal.milestone.SecureHandler;
import com.universal.milestone.Selection;
import com.universal.milestone.SelectionArtistComparator;
import com.universal.milestone.SelectionManager;
import com.universal.milestone.SelectionSelectionNumberComparator;
import com.universal.milestone.SelectionTitleComparator;
import com.universal.milestone.StringComparator;
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
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;
import javax.servlet.http.HttpServletResponse;

public class DreamWksNashProdSchForPrintSubHandler extends SecureHandler {
  public static final String COMPONENT_CODE = "hDWProd";
  
  public GeminiApplication application;
  
  public ComponentLog log;
  
  public DreamWksNashProdSchForPrintSubHandler(GeminiApplication application) {
    this.application = application;
    this.log = application.getLog("hDWProd");
  }
  
  public String getDescription() { return "Sub Report"; }
  
  protected static void fillDreamWksNashProdSchForPrint2(XStyleSheet report, Context context) {
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
    int SUB_HEADER_FONT_SIZE = 8;
    int ROW_LINE_STYLE = 4097;
    Color COL_ROW_LINE_COLOR = new Color(208, 206, 206, 0);
    Color SHADED_ROW_COLOR = new Color(208, 206, 206, 0);
    Hashtable selTable = MilestoneHelper.groupSelectionsByCompanyAndSubconfigAndStreetDate(selections);
    Enumeration companies = selTable.keys();
    Vector companyVector = new Vector();
    while (companies.hasMoreElements())
      companyVector.addElement(companies.nextElement()); 
    int numCompanies = companyVector.size();
    try {
      DefaultTableLens table_contents = null;
      DefaultTableLens rowCountTable = null;
      DefaultTableLens columnHeaderTable = null;
      DefaultTableLens subTable = null;
      DefaultTableLens monthTableLens = null;
      DefaultTableLens dateTableLens = null;
      DefaultTableLens subconfigTableLens = null;
      rowCountTable = new DefaultTableLens(2, 10000);
      Object[] companyArray = companyVector.toArray();
      Arrays.sort(companyArray, new StringComparator());
      for (int n = 0; n < companyArray.length; n++) {
        String company = (String)companyArray[n];
        Form reportForm = (Form)context.getSessionValue("reportForm");
        Calendar beginStDate = (reportForm.getStringValue("beginDate") != null && 
          reportForm.getStringValue("beginDate").length() > 0) ? 
          MilestoneHelper.getDate(reportForm.getStringValue("beginDate")) : null;
        Calendar endStDate = (reportForm.getStringValue("endDate") != null && 
          reportForm.getStringValue("endDate").length() > 0) ? 
          MilestoneHelper.getDate(reportForm.getStringValue("endDate")) : null;
        String startDate = MilestoneHelper.getFormatedDate(beginStDate);
        String endDate = MilestoneHelper.getFormatedDate(endStDate);
        report.setElement("crs_startdate", startDate);
        report.setElement("crs_enddate", endDate);
        SimpleDateFormat formatter = new SimpleDateFormat("MMMM d, yyyy");
        String todayLong = formatter.format(new Date());
        report.setElement("crs_bottomdate", todayLong);
        int numSubconfigs = 0;
        int numMonths = 0;
        int numDates = 0;
        int numSelections = 0;
        int totalCount = 0;
        int tenth = 1;
        Hashtable subconfigTable = (Hashtable)selTable.get(company);
        if (subconfigTable != null) {
          Enumeration subconfigs = subconfigTable.keys();
          while (subconfigs.hasMoreElements()) {
            totalCount++;
            numSubconfigs++;
            String subconfig = (String)subconfigs.nextElement();
            Hashtable monthTable = (Hashtable)subconfigTable.get(subconfig);
            if (monthTable != null) {
              Enumeration months = monthTable.keys();
              while (months.hasMoreElements()) {
                String monthName = (String)months.nextElement();
                numMonths++;
                totalCount += monthTable.size();
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
        tenth = (totalCount > 10) ? (totalCount / 10) : 1;
        HttpServletResponse sresponse = context.getResponse();
        context.putDelivery("status", new String("start_report"));
        context.putDelivery("percent", new String("20"));
        context.includeJSP("status.jsp", "hiddenFrame");
        sresponse.setContentType("text/plain");
        sresponse.flushBuffer();
        int recordCount = 0;
        int count = 0;
        int numRows = 0;
        numRows += numSubconfigs * 4;
        numRows += numMonths * 2;
        numRows += numDates * 3;
        numRows += numSelections * 2;
        numRows += 3;
        table_contents = new DefaultTableLens(1, 9);
        String companyHeaderText = !company.trim().equals("") ? company : "Other";
        table_contents.setColWidth(0, 146);
        table_contents.setColWidth(1, 58);
        table_contents.setColWidth(2, 45);
        table_contents.setColWidth(3, 48);
        table_contents.setColWidth(4, 45);
        table_contents.setColWidth(5, 45);
        table_contents.setColWidth(6, 50);
        table_contents.setColWidth(7, 38);
        table_contents.setColWidth(8, 105);
        int nextRow = 0;
        table_contents.setObject(nextRow, 0, companyHeaderText);
        table_contents.setSpan(nextRow, 0, new Dimension(9, 1));
        table_contents.setRowBackground(nextRow, Color.black);
        table_contents.setRowForeground(nextRow, Color.white);
        table_contents.setRowFont(nextRow, new Font("Arial", 1, 12));
        table_contents.setRowBorderColor(nextRow, Color.white);
        table_contents.setRowBorder(nextRow, 266240);
        if (subconfigTable != null) {
          Enumeration subconfigs = subconfigTable.keys();
          Vector subconfigVector = new Vector();
          while (subconfigs.hasMoreElements())
            subconfigVector.add((String)subconfigs.nextElement()); 
          Object[] subconfigsArray = subconfigVector.toArray();
          Arrays.sort(subconfigsArray, new StringComparator());
          for (int scIndex = 0; scIndex < subconfigsArray.length; scIndex++) {
            String subconfig = (String)subconfigsArray[scIndex];
            boolean isFullLength = false;
            if (subconfig.equalsIgnoreCase("full length"))
              isFullLength = true; 
            nextRow = 0;
            subconfigTableLens = new DefaultTableLens(1, 9);
            subconfigTableLens.setSpan(nextRow, 0, new Dimension(9, 1));
            subconfigTableLens.setAlignment(nextRow, 0, 2);
            subconfigTableLens.setObject(nextRow, 0, subconfig);
            subconfigTableLens.setRowHeight(nextRow, 15);
            subconfigTableLens.setRowBorderColor(nextRow, Color.black);
            subconfigTableLens.setRowBorder(nextRow, 0, 266240);
            subconfigTableLens.setRowFont(nextRow, new Font("Arial", 3, 12));
            subconfigTableLens.setRowBackground(nextRow, Color.white);
            subconfigTableLens.setRowForeground(nextRow, Color.black);
            subconfigTableLens.setRowBorder(nextRow - 1, 266240);
            subconfigTableLens.setColBorder(nextRow, -1, 266240);
            subconfigTableLens.setColBorder(nextRow, 0, 266240);
            subconfigTableLens.setColBorder(nextRow, 9, 266240);
            subconfigTableLens.setColBorder(nextRow, 8, 266240);
            subconfigTableLens.setRowBorder(nextRow, 266240);
            subconfigTableLens.setRowBorderColor(nextRow - 1, Color.black);
            subconfigTableLens.setColBorderColor(nextRow, -1, Color.black);
            subconfigTableLens.setColBorderColor(nextRow, 0, Color.black);
            subconfigTableLens.setColBorderColor(nextRow, 9, Color.black);
            subconfigTableLens.setColBorderColor(nextRow, 8, Color.black);
            subconfigTableLens.setRowBorderColor(nextRow, Color.black);
            hbandType = new SectionBand(report);
            hbandType.setHeight(0.7F);
            hbandType.setShrinkToFit(false);
            hbandType.setVisible(true);
            hbandType.addTable(table_contents, new Rectangle(0, 0, 800, 25));
            hbandType.addTable(subconfigTableLens, new Rectangle(0, 25, 800, 30));
            Hashtable monthTable = (Hashtable)subconfigTable.get(subconfig);
            if (monthTable != null) {
              Enumeration monthSort = monthTable.keys();
              Vector monthVector = new Vector();
              while (monthSort.hasMoreElements())
                monthVector.add((String)monthSort.nextElement()); 
              Object[] monthArray = null;
              monthArray = monthVector.toArray();
              Arrays.sort(monthArray, new IntegerComparator());
              for (int mIndex = 0; mIndex < monthArray.length; mIndex++) {
                String monthName = (String)monthArray[mIndex];
                String monthNameString = monthName;
                try {
                  monthNameString = MONTHS[Integer.parseInt(monthName) - 1];
                } catch (Exception e) {
                  if (monthName.equals("13")) {
                    monthNameString = "TBS";
                  } else if (monthName.equals("26")) {
                    monthNameString = "ITW";
                  } else {
                    monthNameString = "No street date";
                  } 
                } 
                monthTableLens = new DefaultTableLens(1, 9);
                hbandCategory = new SectionBand(report);
                hbandCategory.setHeight(0.25F);
                hbandCategory.setShrinkToFit(false);
                hbandCategory.setVisible(true);
                hbandCategory.setBottomBorder(0);
                hbandCategory.setLeftBorder(0);
                hbandCategory.setRightBorder(0);
                hbandCategory.setTopBorder(0);
                nextRow = 0;
                monthTableLens.setSpan(nextRow, 0, new Dimension(9, 1));
                monthTableLens.setObject(nextRow, 0, monthNameString);
                monthTableLens.setRowHeight(nextRow, 15);
                monthTableLens.setRowFont(nextRow, new Font("Arial", 1, 12));
                monthTableLens.setRowBackground(nextRow, SHADED_AREA_COLOR);
                monthTableLens.setRowForeground(nextRow, Color.black);
                monthTableLens.setRowBorderColor(nextRow, Color.white);
                monthTableLens.setRowBorderColor(nextRow - 1, Color.white);
                monthTableLens.setColBorderColor(nextRow, -1, Color.white);
                monthTableLens.setColBorderColor(nextRow, 0, Color.white);
                monthTableLens.setColBorderColor(nextRow, 8, Color.white);
                hbandCategory.addTable(monthTableLens, new Rectangle(800, 800));
                footer.setVisible(true);
                footer.setHeight(0.1F);
                footer.setShrinkToFit(false);
                footer.setBottomBorder(0);
                group = new DefaultSectionLens(null, group, hbandCategory);
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
                    hbandDate = new SectionBand(report);
                    hbandDate.setHeight(0.65F);
                    hbandDate.setShrinkToFit(false);
                    hbandDate.setVisible(true);
                    hbandDate.setBottomBorder(0);
                    hbandDate.setLeftBorder(0);
                    hbandDate.setRightBorder(0);
                    hbandDate.setTopBorder(0);
                    dateTableLens = new DefaultTableLens(1, 9);
                    nextRow = 0;
                    dateTableLens.setSpan(nextRow, 0, new Dimension(9, 1));
                    dateTableLens.setObject(nextRow, 0, dateNameText);
                    dateTableLens.setRowHeight(nextRow, 14);
                    dateTableLens.setRowFont(nextRow, new Font("Arial", 1, 12));
                    dateTableLens.setRowForeground(nextRow, Color.black);
                    dateTableLens.setRowBorderColor(nextRow, Color.white);
                    dateTableLens.setRowBorderColor(nextRow - 1, Color.white);
                    dateTableLens.setColBorderColor(nextRow, -1, Color.white);
                    dateTableLens.setColBorderColor(nextRow, 0, Color.white);
                    dateTableLens.setColBorderColor(nextRow, 8, Color.white);
                    dateTableLens.setRowBackground(nextRow, Color.white);
                    hbandDate.addTable(dateTableLens, new Rectangle(0, 0, 800, 20));
                    hbandDate.setBottomBorder(0);
                    group = new DefaultSectionLens(null, group, hbandDate);
                    nextRow = 0;
                    columnHeaderTable = new DefaultTableLens(1, 9);
                    columnHeaderTable.setColWidth(0, 146);
                    columnHeaderTable.setColWidth(1, 58);
                    columnHeaderTable.setColWidth(2, 45);
                    columnHeaderTable.setColWidth(3, 48);
                    columnHeaderTable.setColWidth(4, 45);
                    columnHeaderTable.setColWidth(5, 45);
                    columnHeaderTable.setColWidth(6, 50);
                    columnHeaderTable.setColWidth(7, 38);
                    columnHeaderTable.setColWidth(8, 105);
                    columnHeaderTable.setAlignment(nextRow, 0, 1);
                    columnHeaderTable.setObject(nextRow, 0, "Artist/Title");
                    columnHeaderTable.setAlignment(nextRow, 1, 2);
                    columnHeaderTable.setObject(nextRow, 1, "Selection\nPrice");
                    columnHeaderTable.setAlignment(nextRow, 2, 2);
                    columnHeaderTable.setObject(nextRow, 2, "Package\nCopy");
                    columnHeaderTable.setAlignment(nextRow, 3, 2);
                    columnHeaderTable.setObject(nextRow, 3, "Masters\nApproved");
                    columnHeaderTable.setAlignment(nextRow, 4, 2);
                    columnHeaderTable.setObject(nextRow, 4, "Cover\nArt");
                    columnHeaderTable.setAlignment(nextRow, 5, 2);
                    columnHeaderTable.setObject(nextRow, 5, "Film");
                    columnHeaderTable.setAlignment(nextRow, 6, 2);
                    columnHeaderTable.setColBorderColor(COL_ROW_LINE_COLOR);
                    for (int col = -1; col < 9; col++)
                      columnHeaderTable.setColBorder(nextRow, col, 4097); 
                    columnHeaderTable.setRowBorderColor(nextRow - 1, COL_ROW_LINE_COLOR);
                    columnHeaderTable.setRowBorderColor(nextRow, COL_ROW_LINE_COLOR);
                    columnHeaderTable.setRowBorder(nextRow, 4097);
                    if (isFullLength) {
                      columnHeaderTable.setObject(nextRow, 6, "Solicitation");
                    } else {
                      columnHeaderTable.setObject(nextRow, 6, "Add Date");
                    } 
                    columnHeaderTable.setAlignment(nextRow, 7, 2);
                    columnHeaderTable.setObject(nextRow, 7, "Depot");
                    columnHeaderTable.setAlignment(nextRow, 8, 2);
                    columnHeaderTable.setObject(nextRow, 8, "Comments");
                    columnHeaderTable.setAlignment(nextRow, 1, 2);
                    columnHeaderTable.setRowFont(nextRow, new Font("Arial", 1, 8));
                    hbandDate.addTable(columnHeaderTable, new Rectangle(0, 20, 800, 65));
                    selections = (Vector)dateTable.get(dateName);
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
                        int myPercent = count * 10;
                        if (myPercent > 90)
                          myPercent = 90; 
                        context.putDelivery("percent", new String(String.valueOf(myPercent)));
                        context.includeJSP("status.jsp", "hiddenFrame");
                        sresponse.setContentType("text/plain");
                        sresponse.flushBuffer();
                      } 
                      recordCount++;
                      Selection sel = (Selection)selectionsArray[i];
                      nextRow = 0;
                      subTable = new DefaultTableLens(2, 9);
                      subTable.setColWidth(0, 146);
                      subTable.setColWidth(1, 58);
                      subTable.setColWidth(2, 45);
                      subTable.setColWidth(3, 48);
                      subTable.setColWidth(4, 45);
                      subTable.setColWidth(5, 45);
                      subTable.setColWidth(6, 50);
                      subTable.setColWidth(7, 38);
                      subTable.setColWidth(8, 105);
                      String selectionNo = SelectionManager.getLookupObjectValue(sel.getPrefixID());
                      if (selectionNo == null)
                        selectionNo = ""; 
                      selectionNo = String.valueOf(selectionNo) + sel.getSelectionNo();
                      String price = "$0.00";
                      if (sel.getPriceCode() != null && 
                        sel.getPriceCode().getTotalCost() > 0.0F)
                        price = "$" + MilestoneHelper.formatDollarPrice(sel.getPriceCode().getTotalCost()); 
                      String comment = (sel.getSelectionComments() != null) ? sel.getSelectionComments() : "";
                      Schedule schedule = sel.getSchedule();
                      String packageCopy = "";
                      String packageCopyDue = "";
                      String packageCopyComment = "";
                      String mastersApproved = "";
                      String mastersApprovedDue = "";
                      String mastersApprovedComment = "";
                      String coverArt = "";
                      String coverArtDue = "";
                      String coverArtComment = "";
                      String film = "";
                      String filmDue = "";
                      String filmComment = "";
                      String addDate = "";
                      String addDateDue = "";
                      String addDateComment = "";
                      String depot = "";
                      String depotDue = "";
                      String depotComment = "";
                      String solicitation = "";
                      String solicitationDue = "";
                      String solicitationComment = "";
                      if (schedule != null) {
                        Vector tasks = schedule.getTasks();
                        if (tasks != null && tasks.size() > 0)
                          for (int j = 0; j < schedule.getTasks().size(); j++) {
                            ScheduledTask task = (ScheduledTask)tasks.get(j);
                            if (task != null && task.getDueDate() != null) {
                              SimpleDateFormat dueDateFormatter = new SimpleDateFormat("M/d");
                              String dueDate = dueDateFormatter.format(task.getDueDate().getTime());
                              String completionDate = MilestoneHelper.getFormatedDate(task.getCompletionDate());
                              if (task.getScheduledTaskStatus().equals("N/A"))
                                completionDate = task.getScheduledTaskStatus(); 
                              String taskAbbrev = MilestoneHelper.getTaskAbbreivationNameById(task.getTaskAbbreviationID());
                              String taskComment = "";
                              if (task.getComments() != null)
                                taskComment = task.getComments(); 
                              if (taskAbbrev.equalsIgnoreCase("FPC")) {
                                packageCopy = completionDate;
                                packageCopyDue = dueDate;
                                packageCopyComment = taskComment;
                              } else if (taskAbbrev.equalsIgnoreCase("MA")) {
                                mastersApproved = completionDate;
                                mastersApprovedDue = dueDate;
                                mastersApprovedComment = taskComment;
                              } else if (taskAbbrev.equalsIgnoreCase("CAA")) {
                                coverArt = completionDate;
                                coverArtDue = dueDate;
                                coverArtComment = taskComment;
                              } else if (taskAbbrev.equalsIgnoreCase("PFS")) {
                                film = completionDate;
                                filmDue = dueDate;
                                filmComment = taskComment;
                              } else if (taskAbbrev.equalsIgnoreCase("SOL")) {
                                solicitation = completionDate;
                                solicitationDue = dueDate;
                                solicitationComment = taskComment;
                              } else if (taskAbbrev.equalsIgnoreCase("DEPO")) {
                                depot = completionDate;
                                depotDue = dueDate;
                                depotComment = taskComment;
                              } else if (taskAbbrev.equalsIgnoreCase("ADD")) {
                                addDate = completionDate;
                                addDateDue = dueDate;
                                addDateComment = taskComment;
                              } 
                            } 
                          }  
                      } 
                      String seventhColumn = "";
                      String seventhColumnDue = "";
                      String seventhColumnComment = "";
                      if (isFullLength) {
                        seventhColumn = solicitation;
                        seventhColumnDue = solicitationDue;
                        seventhColumnComment = solicitationComment;
                      } else {
                        seventhColumn = addDate;
                        seventhColumnDue = addDateDue;
                        seventhColumnComment = addDateComment;
                      } 
                      String[] checkStrings = { null, ((new String[2][0] = comment.trim()).valueOf(sel.getFlArtist()) + "\n" + sel.getTitle()).trim() };
                      int[] checkStringsLength = { 20, 40 };
                      int addExtraLines = MilestoneHelper.lineCount(checkStrings, checkStringsLength);
                      String selectionNoPrice = String.valueOf(selectionNo) + "\n" + price;
                      for (int z = 0; z < addExtraLines; z++)
                        selectionNoPrice = String.valueOf(selectionNoPrice) + "\n"; 
                      subTable.setRowInsets(nextRow, new Insets(0, 0, 0, 0));
                      subTable.setObject(nextRow, 0, String.valueOf(sel.getFlArtist()) + "\n" + sel.getTitle());
                      subTable.setSpan(nextRow, 0, new Dimension(1, 2));
                      subTable.setObject(nextRow, 1, "Due Dates");
                      subTable.setFont(nextRow, 1, new Font("Arial", 1, 9));
                      subTable.setObject(nextRow, 2, packageCopyDue);
                      subTable.setObject(nextRow, 3, mastersApprovedDue);
                      subTable.setObject(nextRow, 4, coverArtDue);
                      subTable.setObject(nextRow, 5, filmDue);
                      subTable.setObject(nextRow, 6, seventhColumnDue);
                      subTable.setObject(nextRow, 7, depotDue);
                      subTable.setObject(nextRow, 8, comment);
                      subTable.setRowBorderColor(nextRow, Color.white);
                      subTable.setRowBorder(nextRow, 4097);
                      subTable.setRowHeight(nextRow, 10);
                      subTable.setColAutoSize(true);
                      subTable.setRowFont(nextRow, new Font("Arial", 1, 7));
                      subTable.setColBorderColor(COL_ROW_LINE_COLOR);
                      subTable.setColBorder(nextRow, -1, 4097);
                      subTable.setColBorder(nextRow, 0, 4097);
                      subTable.setColBorder(nextRow, 1, 4097);
                      subTable.setColBorder(nextRow, 2, 4097);
                      subTable.setColBorder(nextRow, 3, 4097);
                      subTable.setColBorder(nextRow, 4, 4097);
                      subTable.setColBorder(nextRow, 5, 4097);
                      subTable.setColBorder(nextRow, 6, 4097);
                      subTable.setColBorder(nextRow, 7, 4097);
                      subTable.setColBorder(nextRow, 8, 4097);
                      subTable.setBackground(nextRow, 1, SHADED_ROW_COLOR);
                      subTable.setBackground(nextRow, 2, SHADED_ROW_COLOR);
                      subTable.setBackground(nextRow, 3, SHADED_ROW_COLOR);
                      subTable.setBackground(nextRow, 4, SHADED_ROW_COLOR);
                      subTable.setBackground(nextRow, 5, SHADED_ROW_COLOR);
                      subTable.setBackground(nextRow, 6, SHADED_ROW_COLOR);
                      subTable.setBackground(nextRow, 7, SHADED_ROW_COLOR);
                      subTable.setAlignment(nextRow, 0, 8);
                      subTable.setAlignment(nextRow, 1, 10);
                      subTable.setAlignment(nextRow, 2, 10);
                      subTable.setAlignment(nextRow, 3, 10);
                      subTable.setAlignment(nextRow, 4, 10);
                      subTable.setAlignment(nextRow, 5, 10);
                      subTable.setAlignment(nextRow, 6, 10);
                      subTable.setAlignment(nextRow, 7, 10);
                      subTable.setAlignment(nextRow, 8, 10);
                      subTable.setObject(nextRow + 1, 1, selectionNoPrice);
                      subTable.setObject(nextRow + 1, 2, String.valueOf(packageCopy) + "\n" + packageCopyComment);
                      subTable.setObject(nextRow + 1, 3, String.valueOf(mastersApproved) + "\n" + mastersApprovedComment);
                      subTable.setObject(nextRow + 1, 4, String.valueOf(coverArt) + "\n" + coverArtComment);
                      subTable.setObject(nextRow + 1, 5, String.valueOf(film) + "\n" + filmComment);
                      subTable.setObject(nextRow + 1, 6, String.valueOf(seventhColumn) + "\n" + seventhColumnComment);
                      subTable.setObject(nextRow + 1, 7, String.valueOf(depot) + "\n" + depotComment);
                      subTable.setRowFont(nextRow + 1, new Font("Arial", 0, 7));
                      subTable.setFont(nextRow, 0, new Font("Arial", 0, 7));
                      subTable.setFont(nextRow, 8, new Font("Arial", 0, 7));
                      subTable.setAlignment(nextRow + 1, 1, 10);
                      subTable.setAlignment(nextRow + 1, 2, 10);
                      subTable.setAlignment(nextRow + 1, 3, 10);
                      subTable.setAlignment(nextRow + 1, 4, 10);
                      subTable.setAlignment(nextRow + 1, 5, 10);
                      subTable.setAlignment(nextRow + 1, 6, 10);
                      subTable.setAlignment(nextRow + 1, 7, 10);
                      subTable.setColBorderColor(COL_ROW_LINE_COLOR);
                      subTable.setColBorder(nextRow + 1, -1, 4097);
                      subTable.setColBorder(nextRow + 1, 0, 4097);
                      subTable.setColBorder(nextRow + 1, 1, 4097);
                      subTable.setColBorder(nextRow + 1, 2, 4097);
                      subTable.setColBorder(nextRow + 1, 3, 4097);
                      subTable.setColBorder(nextRow + 1, 4, 4097);
                      subTable.setColBorder(nextRow + 1, 5, 4097);
                      subTable.setColBorder(nextRow + 1, 6, 4097);
                      subTable.setColBorder(nextRow + 1, 7, 4097);
                      subTable.setColBorder(nextRow + 1, 8, 4097);
                      subTable.setSpan(nextRow, 8, new Dimension(1, 2));
                      subTable.setRowBorderColor(nextRow - 1, COL_ROW_LINE_COLOR);
                      subTable.setRowBorderColor(nextRow, COL_ROW_LINE_COLOR);
                      subTable.setRowBorderColor(nextRow + 1, COL_ROW_LINE_COLOR);
                      subTable.setRowBorder(nextRow + 1, 4097);
                      body = new SectionBand(report);
                      body.addTable(subTable, new Rectangle(800, 800));
                      body.setHeight(2.0F);
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
        } 
      } 
    } catch (Exception e) {
      System.out.println(">>>>>>>>ReportHandler.fillDreamWksNashProdScheduleForPrint(): exception: " + e);
    } 
  }
}


/* Location:              D:\Documents\NetBeansProjects\milestone2 Local.war!\WEB-INF\classes\co\\universal\milestone\DreamWksNashProdSchForPrintSubHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */