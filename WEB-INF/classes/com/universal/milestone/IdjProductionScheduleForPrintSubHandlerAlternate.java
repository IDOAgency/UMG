package WEB-INF.classes.com.universal.milestone;

import com.techempower.ComponentLog;
import com.techempower.gemini.Context;
import com.techempower.gemini.GeminiApplication;
import com.universal.milestone.Form;
import com.universal.milestone.IDJExceptionsComparator;
import com.universal.milestone.IDJReportDateArtistComparator;
import com.universal.milestone.IDJSelectionSortComparator;
import com.universal.milestone.IdjProductionScheduleForPrintSubHandlerAlternate;
import com.universal.milestone.JdbcConnector;
import com.universal.milestone.MilestoneHelper;
import com.universal.milestone.MilestoneHelper_2;
import com.universal.milestone.MultCompleteDate;
import com.universal.milestone.Schedule;
import com.universal.milestone.ScheduledTask;
import com.universal.milestone.SecureHandler;
import com.universal.milestone.Selection;
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
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Vector;
import javax.servlet.http.HttpServletResponse;

public class IdjProductionScheduleForPrintSubHandlerAlternate extends SecureHandler {
  public static final String COMPONENT_CODE = "hPsp";
  
  public GeminiApplication application;
  
  public ComponentLog log;
  
  public IdjProductionScheduleForPrintSubHandlerAlternate(GeminiApplication application) {
    this.application = application;
    this.log = application.getLog("hPsp");
  }
  
  public String getDescription() { return "IdjProductionScheduleForPrintSubHandlerAlternate"; }
  
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
    Vector multCompleteDates = getRptMultCompleteDates(selections);
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
    Hashtable selTable = MilestoneHelper.buildFinalIDJAlternateSelections(selections);
    Enumeration types = selTable.keys();
    Vector typeVector = new Vector();
    while (types.hasMoreElements())
      typeVector.addElement(types.nextElement()); 
    int numConfigs = typeVector.size();
    try {
      Vector sortedTypeVector = typeVector;
      DefaultTableLens table_contents = null;
      DefaultTableLens rowCountTable = null;
      DefaultTableLens columnHeaderTable = null;
      DefaultTableLens subTable = null;
      DefaultTableLens dateArtistTableLens = null;
      rowCountTable = new DefaultTableLens(2, 10000);
      int totalCount = 0;
      int tenth = 1;
      Vector updatedTypeVector = new Vector();
      updatedTypeVector.add(0, "Product");
      updatedTypeVector.add(1, "TBS");
      for (int n = 0; n < updatedTypeVector.size(); n++) {
        String typeC = (updatedTypeVector.elementAt(n) != null) ? (String)updatedTypeVector.elementAt(n) : "";
        Hashtable typeTableC = (Hashtable)selTable.get(typeC);
        totalCount++;
        Enumeration typeTableCEnum = typeTableC.keys();
        Vector SelectionsVectorC = new Vector();
        while (typeTableCEnum.hasMoreElements()) {
          SelectionsVectorC.add((String)typeTableCEnum.nextElement());
          Object[] SelectionsArrayC = null;
          SelectionsArrayC = SelectionsVectorC.toArray();
          totalCount += SelectionsArrayC.length;
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
      for (int n = 0; n < updatedTypeVector.size(); n++) {
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
        String type = (updatedTypeVector.elementAt(n) != null) ? (String)updatedTypeVector.elementAt(n) : "";
        Hashtable typeTable = (Hashtable)selTable.get(type);
        int numTypes = 0;
        int numDates = 0;
        int numSelections = 0;
        if (typeTable != null) {
          Enumeration typeEnum = typeTable.keys();
          while (typeEnum.hasMoreElements()) {
            String typeName = (String)typeEnum.nextElement();
            numTypes++;
            Vector selectionSet = (Vector)typeTable.get(typeName);
            if (selectionSet != null)
              numSelections += selectionSet.size(); 
          } 
        } 
        int numRows = 0;
        numRows += numTypes * 3;
        numRows += numDates * 2;
        numRows += numSelections * 2;
        numRows += 5;
        hbandType = new SectionBand(report);
        hbandType.setHeight(1.06F);
        hbandType.setShrinkToFit(true);
        hbandType.setVisible(true);
        table_contents = new DefaultTableLens(1, 16);
        table_contents.setHeaderRowCount(0);
        table_contents.setColWidth(0, 70);
        table_contents.setColWidth(1, 365);
        table_contents.setColWidth(2, 200);
        table_contents.setColWidth(3, 160);
        table_contents.setColWidth(4, 95);
        table_contents.setColWidth(5, 80);
        table_contents.setColWidth(6, 80);
        table_contents.setColWidth(7, 80);
        table_contents.setColWidth(8, 80);
        table_contents.setColWidth(9, 80);
        table_contents.setColWidth(10, 80);
        table_contents.setColWidth(11, 80);
        table_contents.setColWidth(12, 2);
        table_contents.setColWidth(13, 80);
        table_contents.setColWidth(14, 80);
        table_contents.setColWidth(15, 80);
        table_contents.setColBorderColor(Color.black);
        table_contents.setRowBorderColor(Color.black);
        table_contents.setRowBorder(4097);
        table_contents.setColBorder(4097);
        int nextRow = 0;
        String typeHeaderText = !type.trim().equals("") ? type : "Other";
        table_contents.setSpan(nextRow, 0, new Dimension(16, 1));
        table_contents.setAlignment(nextRow, 0, 2);
        table_contents.setObject(nextRow, 0, typeHeaderText);
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
        table_contents.setRowBorder(nextRow, 266240);
        table_contents.setRowBorderColor(nextRow - 1, Color.black);
        table_contents.setColBorderColor(nextRow, -1, Color.black);
        table_contents.setColBorderColor(nextRow, 0, Color.black);
        table_contents.setColBorderColor(nextRow, 15, Color.black);
        table_contents.setRowBorderColor(nextRow, Color.black);
        nextRow = 0;
        columnHeaderTable = new DefaultTableLens(3, 16);
        columnHeaderTable.setHeaderRowCount(0);
        columnHeaderTable.setColWidth(0, 70);
        columnHeaderTable.setColWidth(1, 365);
        columnHeaderTable.setColWidth(2, 200);
        columnHeaderTable.setColWidth(3, 160);
        columnHeaderTable.setColWidth(4, 95);
        columnHeaderTable.setColWidth(5, 80);
        columnHeaderTable.setColWidth(6, 80);
        columnHeaderTable.setColWidth(7, 80);
        columnHeaderTable.setColWidth(8, 80);
        columnHeaderTable.setColWidth(9, 80);
        columnHeaderTable.setColWidth(10, 80);
        columnHeaderTable.setColWidth(11, 80);
        columnHeaderTable.setColWidth(12, 2);
        columnHeaderTable.setColWidth(13, 80);
        columnHeaderTable.setColWidth(14, 80);
        columnHeaderTable.setColWidth(15, 80);
        columnHeaderTable.setAlignment(nextRow, 0, 33);
        columnHeaderTable.setAlignment(nextRow, 1, 33);
        columnHeaderTable.setAlignment(nextRow, 2, 34);
        columnHeaderTable.setAlignment(nextRow, 3, 34);
        columnHeaderTable.setAlignment(nextRow, 4, 34);
        columnHeaderTable.setAlignment(nextRow, 5, 33);
        columnHeaderTable.setAlignment(nextRow, 6, 33);
        columnHeaderTable.setAlignment(nextRow, 7, 33);
        columnHeaderTable.setAlignment(nextRow, 8, 33);
        columnHeaderTable.setAlignment(nextRow, 9, 33);
        columnHeaderTable.setAlignment(nextRow, 10, 33);
        columnHeaderTable.setAlignment(nextRow, 11, 33);
        columnHeaderTable.setAlignment(nextRow, 12, 33);
        columnHeaderTable.setAlignment(nextRow, 13, 33);
        columnHeaderTable.setAlignment(nextRow, 14, 33);
        columnHeaderTable.setAlignment(nextRow, 15, 33);
        columnHeaderTable.setSpan(nextRow, 0, new Dimension(1, 2));
        columnHeaderTable.setObject(nextRow, 0, "Release\nDate");
        columnHeaderTable.setSpan(nextRow, 1, new Dimension(1, 2));
        columnHeaderTable.setObject(nextRow, 1, "Artist\nTitle\nLabel\nContacts");
        columnHeaderTable.setSpan(nextRow, 2, new Dimension(1, 2));
        columnHeaderTable.setObject(nextRow, 2, "Packaging\nSpecs");
        columnHeaderTable.setSpan(nextRow, 3, new Dimension(1, 2));
        columnHeaderTable.setObject(nextRow, 3, "UPC\nLocal Prod #\nConfig\nImpact Date");
        columnHeaderTable.setSpan(nextRow, 4, new Dimension(1, 2));
        columnHeaderTable.setObject(nextRow, 4, "Price\nCode\nUnits");
        columnHeaderTable.setRowHeight(nextRow, 10);
        columnHeaderTable.setRowHeight(nextRow + 1, 27);
        columnHeaderTable.setSpan(nextRow, 5, new Dimension(7, 1));
        columnHeaderTable.setObject(nextRow, 5, "PHYSICAL PRODUCTS");
        columnHeaderTable.setAlignment(nextRow, 5, 2);
        columnHeaderTable.setSpan(nextRow, 13, new Dimension(3, 1));
        columnHeaderTable.setObject(nextRow, 13, "DIGITAL PRODUCTS");
        columnHeaderTable.setAlignment(nextRow, 13, 2);
        columnHeaderTable.setObject(nextRow + 1, 5, "Prod\nReq\nDue");
        columnHeaderTable.setObject(nextRow + 1, 6, "Sol\nFilm\nDue");
        columnHeaderTable.setObject(nextRow + 1, 7, "To Seps");
        columnHeaderTable.setObject(nextRow + 1, 8, "Film\nShips");
        columnHeaderTable.setObject(nextRow + 1, 9, "Signed\nProd\nReqs");
        columnHeaderTable.setObject(nextRow + 1, 10, "Master\nShips");
        columnHeaderTable.setObject(nextRow + 1, 11, "Tests\nApprv");
        columnHeaderTable.setObject(nextRow + 1, 13, "BA\nApprv");
        columnHeaderTable.setObject(nextRow + 1, 14, "Graphics\nDue");
        columnHeaderTable.setObject(nextRow + 1, 15, "Audio\nDue");
        columnHeaderTable.setAlignment(nextRow + 1, 5, 34);
        columnHeaderTable.setAlignment(nextRow + 1, 6, 34);
        columnHeaderTable.setAlignment(nextRow + 1, 7, 34);
        columnHeaderTable.setAlignment(nextRow + 1, 8, 34);
        columnHeaderTable.setAlignment(nextRow + 1, 9, 34);
        columnHeaderTable.setAlignment(nextRow + 1, 10, 34);
        columnHeaderTable.setAlignment(nextRow + 1, 11, 34);
        columnHeaderTable.setAlignment(nextRow + 1, 12, 34);
        columnHeaderTable.setAlignment(nextRow + 1, 13, 34);
        columnHeaderTable.setAlignment(nextRow + 1, 14, 34);
        columnHeaderTable.setAlignment(nextRow + 1, 15, 34);
        setColBorderColor(columnHeaderTable, nextRow, 16, SHADED_AREA_COLOR);
        setColBorderColor(columnHeaderTable, nextRow + 1, 16, SHADED_AREA_COLOR);
        columnHeaderTable.setRowBorderColor(nextRow - 1, SHADED_AREA_COLOR);
        columnHeaderTable.setRowBorderColor(nextRow, SHADED_AREA_COLOR);
        columnHeaderTable.setRowBorderColor(nextRow + 1, SHADED_AREA_COLOR);
        columnHeaderTable.setRowFont(nextRow, new Font("Arial", 1, 7));
        columnHeaderTable.setRowFont(nextRow + 1, new Font("Arial", 1, 7));
        columnHeaderTable.setRowBackground(nextRow, Color.white);
        columnHeaderTable.setRowForeground(nextRow, Color.black);
        hbandType.addTable(columnHeaderTable, new Rectangle(0, 24, 800, 50));
        DefaultTableLens blank_table_contents = null;
        blank_table_contents = new DefaultTableLens(1, 16);
        blank_table_contents.setHeaderRowCount(0);
        blank_table_contents.setColBorderColor(Color.white);
        blank_table_contents.setRowBorderColor(Color.white);
        nextRow = 0;
        blank_table_contents.setSpan(nextRow, 0, new Dimension(16, 1));
        blank_table_contents.setAlignment(nextRow, 0, 2);
        blank_table_contents.setRowHeight(nextRow, 1);
        blank_table_contents.setRowBorderColor(nextRow, Color.white);
        blank_table_contents.setRowBorder(nextRow, 0, 266240);
        blank_table_contents.setRowFont(nextRow, new Font("Arial", 3, 12));
        blank_table_contents.setRowBackground(nextRow, Color.white);
        blank_table_contents.setRowForeground(nextRow, Color.white);
        blank_table_contents.setRowBorder(nextRow - 1, 266240);
        blank_table_contents.setColBorder(nextRow, -1, 266240);
        blank_table_contents.setColBorder(nextRow, 0, 266240);
        blank_table_contents.setColBorder(nextRow, 15, 266240);
        blank_table_contents.setRowBorder(nextRow, 266240);
        blank_table_contents.setRowBorderColor(nextRow - 1, Color.white);
        blank_table_contents.setColBorderColor(nextRow, -1, Color.white);
        blank_table_contents.setColBorderColor(nextRow, 0, Color.white);
        blank_table_contents.setColBorderColor(nextRow, 15, Color.white);
        blank_table_contents.setRowBorderColor(nextRow, Color.white);
        hbandType.addTable(blank_table_contents, new Rectangle(0, 75, 800, 15));
        hbandType.setBottomBorder(0);
        if (typeTable != null) {
          Enumeration typesEnum = typeTable.keys();
          Vector currentTypeVector = new Vector();
          while (typesEnum.hasMoreElements())
            currentTypeVector.add((String)typesEnum.nextElement()); 
          Collections.sort(currentTypeVector, new IDJReportDateArtistComparator());
          for (int x = 0; x < currentTypeVector.size(); x++) {
            String typeName = (String)currentTypeVector.elementAt(x);
            dateArtistTableLens = new DefaultTableLens(1, 16);
            nextRow = 0;
            selections = (Vector)typeTable.get(typeName);
            if (typeName.startsWith("TBS")) {
              Collections.sort(selections, new IDJExceptionsComparator());
            } else {
              Collections.sort(selections, new IDJSelectionSortComparator());
            } 
            nextRow = 0;
            if (selections == null)
              selections = new Vector(); 
            for (int i = 0; i < selections.size(); i++) {
              Calendar streetCal;
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
              int subTableRows = 3;
              if (newComment.length() > 0)
                subTableRows = 4; 
              String labelCell = "";
              labelCell = sel.getImprint();
              String pack = "";
              pack = sel.getSelectionPackaging();
              String title = "";
              if (sel.getTitle() != null)
                title = sel.getTitle(); 
              String upc = "";
              upc = (sel.getUpc() != null) ? sel.getUpc() : "";
              upc = MilestoneHelper_2.getRMSReportFormat(upc, "UPC", sel.getIsDigital());
              String localProductNumber = "";
              if (sel.getPrefixID() != null && sel.getPrefixID().getAbbreviation() != null)
                localProductNumber = sel.getPrefixID().getAbbreviation(); 
              localProductNumber = String.valueOf(localProductNumber) + sel.getSelectionNo();
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
              String PRQD = "N/A";
              String SFD = "N/A";
              String PTS = "N/A";
              String TPS = "N/A";
              String TA = "N/A";
              String PRQDcom = "";
              String SFDcom = "";
              String PTScom = "";
              String TPScom = "";
              String TAcom = "";
              String SEPS = "N/A";
              String SPR = "N/A";
              String BAS = "N/A";
              String GRA = "N/A";
              String WAV = "N/A";
              String PFM = "N/A";
              String BAScom = "";
              String GRAcom = "";
              String WAVcom = "";
              String PFMcom = "";
              String SEPScom = "";
              String SPRcom = "";
              boolean hasSFD = false;
              if (tasks != null)
                for (int j = 0; j < tasks.size(); j++) {
                  task = (ScheduledTask)tasks.get(j);
                  if (task != null) {
                    streetCal = new SimpleDateFormat("M/d");
                    String dueDate = "";
                    if (task.getDueDate() != null)
                      dueDate = String.valueOf(streetCal.format(task.getDueDate().getTime())) + 
                        " " + MilestoneHelper.getDayType(sel.getCalendarGroup(), task.getDueDate()); 
                    String completionDate = MilestoneHelper.getFormatedDate(task.getCompletionDate());
                    if (task.getScheduledTaskStatus().equals("N/A"))
                      completionDate = task.getScheduledTaskStatus(); 
                    completionDate = String.valueOf(completionDate) + "\n";
                    String taskAbbrev = MilestoneHelper.getTaskAbbreivationNameById(task.getTaskAbbreviationID());
                    String taskComment = "";
                    if (taskAbbrev.equalsIgnoreCase("PRQD")) {
                      PRQD = dueDate;
                      completionDate = String.valueOf(completionDate) + getTaskMultCompleteDates(task.getScheduledTaskID(), sel.getSelectionID(), multCompleteDates);
                      PRQDcom = String.valueOf(completionDate) + taskComment;
                    } else if (taskAbbrev.equalsIgnoreCase("SFD")) {
                      SFD = dueDate;
                      SFDcom = String.valueOf(completionDate) + taskComment;
                      hasSFD = true;
                    } else if (taskAbbrev.equalsIgnoreCase("PFS")) {
                      PTS = dueDate;
                      completionDate = String.valueOf(completionDate) + getTaskMultCompleteDates(task.getScheduledTaskID(), sel.getSelectionID(), multCompleteDates);
                      PTScom = String.valueOf(completionDate) + taskComment;
                    } else if (taskAbbrev.equalsIgnoreCase("TPS")) {
                      TPS = dueDate;
                      completionDate = String.valueOf(completionDate) + getTaskMultCompleteDates(task.getScheduledTaskID(), sel.getSelectionID(), multCompleteDates);
                      TPScom = String.valueOf(completionDate) + taskComment;
                    } else if (taskAbbrev.equalsIgnoreCase("TA")) {
                      TA = dueDate;
                      TAcom = String.valueOf(completionDate) + taskComment;
                    } else if (taskAbbrev.equalsIgnoreCase("BAS")) {
                      BAS = dueDate;
                      BAScom = String.valueOf(completionDate) + taskComment;
                    } else if (taskAbbrev.equalsIgnoreCase("GRA")) {
                      GRA = dueDate;
                      completionDate = String.valueOf(completionDate) + getTaskMultCompleteDates(task.getScheduledTaskID(), sel.getSelectionID(), multCompleteDates);
                      GRAcom = String.valueOf(completionDate) + taskComment;
                    } else if (taskAbbrev.equalsIgnoreCase("WAV")) {
                      WAV = dueDate;
                      completionDate = String.valueOf(completionDate) + getTaskMultCompleteDates(task.getScheduledTaskID(), sel.getSelectionID(), multCompleteDates);
                      WAVcom = String.valueOf(completionDate) + taskComment;
                    } else if (taskAbbrev.equalsIgnoreCase("PFM")) {
                      PFM = dueDate;
                      completionDate = String.valueOf(completionDate) + 
                        getTaskMultCompleteDates(task.getScheduledTaskID(), 
                          sel.getSelectionID(), multCompleteDates);
                      PFMcom = String.valueOf(completionDate) + taskComment;
                    } else if (taskAbbrev.equalsIgnoreCase("SEPS")) {
                      SEPS = dueDate;
                      completionDate = String.valueOf(completionDate) + 
                        getTaskMultCompleteDates(task.getScheduledTaskID(), 
                          sel.getSelectionID(), multCompleteDates);
                      SEPScom = String.valueOf(completionDate) + taskComment;
                    } else if (taskAbbrev.equalsIgnoreCase("SPR")) {
                      SPR = dueDate;
                      completionDate = String.valueOf(completionDate) + 
                        getTaskMultCompleteDates(task.getScheduledTaskID(), 
                          sel.getSelectionID(), multCompleteDates);
                      SPRcom = String.valueOf(completionDate) + taskComment;
                    } 
                    task = null;
                  } 
                }  
              nextRow = 0;
              if (i == 0) {
                subTable = new DefaultTableLens(subTableRows + 3, 16);
              } else {
                subTable = new DefaultTableLens(subTableRows, 16);
              } 
              subTable.setRowInsets(nextRow, new Insets(0, 0, 0, 0));
              setColBorderColor(subTable, nextRow, 16, SHADED_AREA_COLOR);
              setColBorderColor(subTable, nextRow + 1, 16, SHADED_AREA_COLOR);
              setColBorderColor(subTable, nextRow + 2, 16, SHADED_AREA_COLOR);
              setColBorderColor(subTable, nextRow + 3, 16, SHADED_AREA_COLOR);
              if (i == 0) {
                setColBorderColor(subTable, nextRow + 4, 16, SHADED_AREA_COLOR);
                setColBorderColor(subTable, nextRow + 5, 16, SHADED_AREA_COLOR);
              } 
              subTable.setHeaderRowCount(0);
              subTable.setColWidth(0, 70);
              subTable.setColWidth(1, 365);
              subTable.setColWidth(2, 200);
              subTable.setColWidth(3, 160);
              subTable.setColWidth(4, 95);
              subTable.setColWidth(5, 80);
              subTable.setColWidth(6, 80);
              subTable.setColWidth(7, 80);
              subTable.setColWidth(8, 80);
              subTable.setColWidth(9, 80);
              subTable.setColWidth(10, 80);
              subTable.setColWidth(11, 80);
              subTable.setColWidth(12, 2);
              subTable.setColWidth(13, 80);
              subTable.setColWidth(14, 80);
              subTable.setColWidth(15, 80);
              subTable.setRowBorderColor(nextRow - 1, Color.lightGray);
              if (i == 0) {
                subTable.setHeaderRowCount(0);
                subTable.setSpan(nextRow, 0, new Dimension(16, 1));
                subTable.setRowHeight(nextRow, 2);
                subTable.setRowBackground(nextRow, Color.white);
                subTable.setRowForeground(nextRow, Color.black);
                subTable.setColBorderColor(nextRow, -1, Color.white);
                subTable.setColBorderColor(nextRow, 15, Color.white);
                subTable.setRowBorderColor(nextRow - 1, Color.white);
                subTable.setRowBorderColor(nextRow, SHADED_AREA_COLOR);
                nextRow++;
                if (typeName.startsWith("TBS")) {
                  String tbsDate = "";
                  tbsDate = typeName.substring(6, typeName.lastIndexOf("*") - 1);
                  if (tbsDate.equals("NoDate")) {
                    streetCal = typeName.substring(typeName.lastIndexOf("*") + 2, typeName.length());
                    typeName = "TBS - " + streetCal;
                  } 
                } 
                typeName = typeName.replace('*', '-');
                subTable.setHeaderRowCount(0);
                subTable.setSpan(nextRow, 0, new Dimension(16, 1));
                subTable.setAlignment(nextRow, 0, 17);
                subTable.setObject(nextRow, 0, typeName);
                subTable.setRowHeight(nextRow, 15);
                subTable.setRowFont(nextRow, new Font("Arial", 1, 12));
                subTable.setRowBackground(nextRow, SHADED_AREA_COLOR);
                subTable.setRowForeground(nextRow, Color.black);
                subTable.setColBorderColor(nextRow, -1, SHADED_AREA_COLOR);
                subTable.setColBorderColor(nextRow, 15, SHADED_AREA_COLOR);
                subTable.setColBorderColor(nextRow - 1, 15, Color.white);
                subTable.setRowBorderColor(nextRow - 1, SHADED_AREA_COLOR);
                subTable.setRowBorderColor(nextRow, SHADED_AREA_COLOR);
                nextRow++;
                subTable.setHeaderRowCount(0);
                subTable.setSpan(nextRow, 0, new Dimension(16, 1));
                subTable.setRowHeight(nextRow, 2);
                subTable.setRowBackground(nextRow, Color.white);
                subTable.setRowForeground(nextRow, Color.black);
                subTable.setColBorderColor(nextRow, -1, Color.white);
                subTable.setColBorderColor(nextRow, 15, Color.white);
                subTable.setRowBorderColor(nextRow - 1, Color.white);
                subTable.setRowBorderColor(nextRow, SHADED_AREA_COLOR);
                nextRow++;
              } 
              String streetDate = "";
              if (sel.getIsDigital()) {
                streetCal = sel.getDigitalRlsDate();
              } else {
                streetCal = sel.getStreetDate();
              } 
              streetDate = MilestoneHelper.getFormatedDate(streetCal);
              String status = (sel.getSelectionStatus() != null && sel.getSelectionStatus().getName() != null) ? 
                sel.getSelectionStatus().getName() : "";
              if (status.equalsIgnoreCase("TBS"))
                streetDate = "TBS\n" + streetDate; 
              String radioImpactDate = "";
              radioImpactDate = MilestoneHelper.getFormatedDate(sel.getImpactDate());
              subTable.setObject(nextRow, 0, streetDate);
              subTable.setBackground(nextRow, 0, Color.white);
              subTable.setSpan(nextRow, 0, new Dimension(1, 3));
              subTable.setAlignment(nextRow, 0, 9);
              subTable.setFont(nextRow, 0, new Font("Arial", 1, 7));
              boolean otherContactExists = false;
              if (!otherContact.equals(""))
                otherContactExists = true; 
              if (contact != null && !contact.equals(""))
                labelCell = String.valueOf(labelCell) + "\n" + contact; 
              if (otherContactExists)
                labelCell = String.valueOf(labelCell) + "\n" + otherContact; 
              String[] checkStrings = { comment, artist, title, (new String[5][3] = pack).valueOf(contact) + "/n" + otherContact };
              int[] checkStringsLength = { 20, 30, 30, 20, 25 };
              int extraLines = MilestoneHelper.lineCountWCR(checkStrings, checkStringsLength);
              subTable.setAlignment(nextRow, 1, 9);
              subTable.setAlignment(nextRow + 2, 1, 9);
              subTable.setFont(nextRow, 1, new Font("Arial", 1, 7));
              subTable.setFont(nextRow + 2, 1, new Font("Arial", 0, 7));
              subTable.setSpan(nextRow, 1, new Dimension(1, 2));
              subTable.setObject(nextRow, 1, String.valueOf(artist) + "\n" + title);
              subTable.setObject(nextRow + 2, 1, labelCell);
              subTable.setRowBorderColor(nextRow, 1, Color.white);
              subTable.setBackground(nextRow, 1, Color.white);
              subTable.setBackground(nextRow + 2, 1, Color.white);
              if (contact.equals("") && !otherContactExists && (radioImpactDate == null || radioImpactDate.equals("")))
                subTable.setRowHeight(nextRow + 2, 8); 
              subTable.setRowBorderColor(nextRow, -1, SHADED_AREA_COLOR);
              subTable.setObject(nextRow, 2, pack);
              subTable.setBackground(nextRow, 2, Color.white);
              subTable.setSpan(nextRow, 2, new Dimension(1, 3));
              subTable.setAlignment(nextRow, 2, 9);
              subTable.setFont(nextRow, 2, new Font("Arial", 0, 7));
              subTable.setAlignment(nextRow, 3, 10);
              subTable.setAlignment(nextRow + 2, 3, 10);
              subTable.setFont(nextRow, 3, new Font("Arial", 0, 7));
              subTable.setFont(nextRow + 1, 3, new Font("Arial", 0, 7));
              subTable.setFont(nextRow + 2, 3, new Font("Arial", 1, 7));
              subTable.setSpan(nextRow, 3, new Dimension(1, 2));
              subTable.setObject(nextRow, 3, String.valueOf(upc) + "\n" + localProductNumber);
              subTable.setObject(nextRow + 2, 3, String.valueOf(selSubConfig) + "\n" + radioImpactDate);
              subTable.setRowBorderColor(nextRow, 3, Color.white);
              subTable.setBackground(nextRow, 3, Color.white);
              subTable.setBackground(nextRow + 2, 3, Color.white);
              subTable.setObject(nextRow, 4, "Due Dates");
              subTable.setColBorderColor(nextRow, 4, SHADED_AREA_COLOR);
              subTable.setColBorder(nextRow, 4, 266240);
              subTable.setFont(nextRow, 4, new Font("Arial", 1, 7));
              subTable.setFont(nextRow + 1, 4, new Font("Arial", 0, 7));
              subTable.setRowBorderColor(nextRow, SHADED_AREA_COLOR);
              subTable.setSpan(nextRow + 1, 4, new Dimension(1, 2));
              subTable.setObject(nextRow + 1, 4, String.valueOf(price) + "\n" + code + "\n" + units);
              subTable.setAlignment(nextRow + 1, 4, 10);
              subTable.setAlignment(nextRow, 6, 10);
              subTable.setAlignment(nextRow + 1, 6, 10);
              subTable.setRowHeight(nextRow, 8);
              subTable.setRowBorderColor(nextRow + 1, Color.white);
              subTable.setObject(nextRow, 5, PRQD);
              subTable.setColBorderColor(nextRow, 5, SHADED_AREA_COLOR);
              subTable.setColBorder(nextRow, 5, 266240);
              subTable.setObject(nextRow, 6, SFD);
              subTable.setColBorderColor(nextRow, 6, SHADED_AREA_COLOR);
              subTable.setColBorder(nextRow, 6, 266240);
              subTable.setObject(nextRow, 7, SEPS);
              subTable.setColBorderColor(nextRow, 7, SHADED_AREA_COLOR);
              subTable.setColBorder(nextRow, 7, 266240);
              subTable.setObject(nextRow, 8, PTS);
              subTable.setColBorderColor(nextRow, 8, SHADED_AREA_COLOR);
              subTable.setColBorder(nextRow, 8, 266240);
              subTable.setObject(nextRow, 9, SPR);
              subTable.setColBorderColor(nextRow, 9, SHADED_AREA_COLOR);
              subTable.setColBorder(nextRow, 9, 266240);
              subTable.setObject(nextRow, 10, TPS);
              subTable.setColBorderColor(nextRow, 10, SHADED_AREA_COLOR);
              subTable.setColBorder(nextRow, 10, 266240);
              subTable.setObject(nextRow, 11, TA);
              subTable.setColBorderColor(nextRow, 11, Color.lightGray);
              subTable.setColBorder(nextRow, 11, 266240);
              subTable.setObject(nextRow, 13, BAS);
              subTable.setColBorderColor(nextRow, 13, Color.lightGray);
              subTable.setColBorder(nextRow, 13, 266240);
              subTable.setObject(nextRow, 14, GRA);
              subTable.setColBorderColor(nextRow, 14, Color.lightGray);
              subTable.setColBorder(nextRow, 14, 266240);
              subTable.setObject(nextRow, 15, WAV);
              subTable.setColBorderColor(nextRow, 15, Color.lightGray);
              subTable.setColBorder(nextRow, 15, 266240);
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
              Font holidayFont = new Font("Arial", 3, 7);
              for (int colIdx = 5; colIdx <= 15; colIdx++) {
                if (colIdx != 12) {
                  String dueDate = subTable.getObject(nextRow, colIdx).toString();
                  if (dueDate != null && dueDate.length() > 0) {
                    char lastChar = dueDate.charAt(dueDate.length() - 1);
                    if (Character.isLetter(lastChar))
                      subTable.setFont(nextRow, colIdx, holidayFont); 
                  } 
                } 
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
              subTable.setAlignment(nextRow, 14, 2);
              subTable.setAlignment(nextRow, 15, 2);
              subTable.setObject(nextRow + 1, 5, PRQDcom);
              subTable.setObject(nextRow + 1, 6, SFDcom);
              subTable.setObject(nextRow + 1, 7, SEPScom);
              subTable.setObject(nextRow + 1, 8, PTScom);
              subTable.setObject(nextRow + 1, 9, SPRcom);
              subTable.setObject(nextRow + 1, 10, TPScom);
              subTable.setObject(nextRow + 1, 11, TAcom);
              subTable.setObject(nextRow + 1, 13, BAScom);
              subTable.setObject(nextRow + 1, 14, GRAcom);
              subTable.setObject(nextRow + 1, 15, WAVcom);
              subTable.setSpan(nextRow + 1, 5, new Dimension(1, 2));
              subTable.setSpan(nextRow + 1, 6, new Dimension(1, 2));
              subTable.setSpan(nextRow + 1, 7, new Dimension(1, 2));
              subTable.setSpan(nextRow + 1, 8, new Dimension(1, 2));
              subTable.setSpan(nextRow + 1, 9, new Dimension(1, 2));
              subTable.setSpan(nextRow + 1, 10, new Dimension(1, 2));
              subTable.setSpan(nextRow + 1, 11, new Dimension(1, 2));
              subTable.setSpan(nextRow + 1, 12, new Dimension(1, 2));
              subTable.setSpan(nextRow + 1, 13, new Dimension(1, 2));
              subTable.setSpan(nextRow + 1, 14, new Dimension(1, 2));
              subTable.setSpan(nextRow + 1, 15, new Dimension(1, 2));
              subTable.setAlignment(nextRow + 1, 4, 10);
              subTable.setAlignment(nextRow + 1, 5, 10);
              subTable.setAlignment(nextRow + 1, 6, 10);
              subTable.setAlignment(nextRow + 1, 7, 10);
              subTable.setAlignment(nextRow + 1, 8, 10);
              subTable.setAlignment(nextRow + 1, 9, 10);
              subTable.setAlignment(nextRow + 1, 10, 10);
              subTable.setAlignment(nextRow + 1, 11, 10);
              subTable.setAlignment(nextRow + 1, 13, 10);
              subTable.setAlignment(nextRow + 1, 14, 10);
              subTable.setAlignment(nextRow + 1, 15, 10);
              subTable.setRowFont(nextRow + 1, new Font("Arial", 0, 7));
              subTable.setRowBackground(nextRow, SHADED_AREA_COLOR);
              subTable.setRowForeground(nextRow, Color.black);
              subTable.setRowBorderColor(nextRow + 2, Color.lightGray);
              subTable.setRowFont(nextRow, new Font("Arial", 0, 7));
              if (newComment.length() > 0) {
                nextRow++;
                subTable.setRowBorderColor(nextRow + 2, Color.lightGray);
                subTable.setRowFont(nextRow + 2, new Font("Arial", 3, 7));
                subTable.setSpan(nextRow + 2, 0, new Dimension(16, 1));
                subTable.setAlignment(nextRow + 2, 0, 9);
                subTable.setObject(nextRow + 2, 0, "Comments:   " + newComment);
                subTable.setColBorderColor(nextRow + 2, -1, Color.lightGray);
                subTable.setColBorderColor(nextRow + 2, 15, Color.lightGray);
              } 
              body = new SectionBand(report);
              double lfLineCount = 1.0D;
              if (i == 0 && typeName.startsWith("TBS"))
                lfLineCount = 1.3D; 
              if (newComment.length() > 0 && ((!contact.equals("") && contact != null) || otherContactExists)) {
                lfLineCount = 2.0D;
              } else if ((!contact.equals("") && contact != null) || otherContactExists) {
                lfLineCount = 1.5D;
              } else if (!otherContactExists && newComment.length() > 0) {
                lfLineCount = 1.3D;
              } 
              body.setHeight((float)lfLineCount);
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
      System.out.println(">>>>>>>>IdjProductionScheduleForPrintSubHandlerAlternate(): exception: " + e);
    } 
  }
  
  private static void setColBorderColor(DefaultTableLens table, int row, int columns, Color color) {
    for (int i = -1; i < columns; i++)
      table.setColBorderColor(row, i, color); 
  }
  
  public static String removeLF(String theString, int maxChars) { return theString.replace('\n', ' '); }
  
  private static String getTaskMultCompleteDates(int taskID, int selectionID, Vector multCompleteDates) {
    String completionDateList = "";
    if (multCompleteDates != null) {
      int mcdCt = (multCompleteDates == null) ? 0 : multCompleteDates.size();
      boolean relTaskFound = false;
      int i = 0;
      while (!relTaskFound && i < mcdCt) {
        MultCompleteDate mcd = (MultCompleteDate)multCompleteDates.get(i);
        if (mcd.getReleaseID() == selectionID && mcd.getTaskID() == taskID) {
          relTaskFound = true;
          continue;
        } 
        i++;
      } 
      if (relTaskFound) {
        boolean relTaskDone = false;
        while (!relTaskDone && i < mcdCt) {
          MultCompleteDate mcd = (MultCompleteDate)multCompleteDates.get(i);
          if (mcd.getReleaseID() == selectionID && mcd.getTaskID() == taskID) {
            completionDateList = String.valueOf(completionDateList) + MilestoneHelper.getFormatedDate(mcd.getCompletionDate()) + "\n";
          } else {
            relTaskDone = true;
          } 
          i++;
        } 
      } 
    } 
    return completionDateList;
  }
  
  private static Vector getRptMultCompleteDates(Vector selections) {
    Vector multCompleteDates = null;
    if (selections != null) {
      multCompleteDates = new Vector();
      StringBuffer sql = new StringBuffer();
      Iterator it = selections.iterator();
      sql.append("select * from MultCompleteDates with (nolock) where release_id in (");
      while (it.hasNext()) {
        sql.append(((Selection)it.next()).getSelectionID());
        sql.append(", ");
      } 
      String query = String.valueOf(sql.substring(0, sql.length() - 2)) + ") order by release_id asc, task_id asc, order_no desc";
      JdbcConnector connector = MilestoneHelper.getConnector(query);
      if (connector != null) {
        connector.setForwardOnly(false);
        connector.runQuery();
        SimpleDateFormat adf = new SimpleDateFormat("M/d/yy");
        while (connector.more()) {
          MultCompleteDate mcd = new MultCompleteDate();
          mcd.setReleaseID(connector.getInt("release_id"));
          mcd.setTaskID(connector.getInt("task_id"));
          mcd.setOrderNo(connector.getInt("order_no"));
          mcd.setCompletionDate(MilestoneHelper.getDate(adf.format(connector.getDate("completion_date"))));
          multCompleteDates.addElement(mcd);
          connector.next();
        } 
        connector.close();
      } 
    } 
    return multCompleteDates;
  }
}


/* Location:              D:\Documents\NetBeansProjects\milestone2 Local.war!\WEB-INF\classes\co\\universal\milestone\IdjProductionScheduleForPrintSubHandlerAlternate.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */