package WEB-INF.classes.com.universal.milestone;

import com.techempower.ComponentLog;
import com.techempower.gemini.Context;
import com.techempower.gemini.GeminiApplication;
import com.universal.milestone.Form;
import com.universal.milestone.JdbcConnector;
import com.universal.milestone.MilestoneHelper;
import com.universal.milestone.Plant;
import com.universal.milestone.ReleasingFamily;
import com.universal.milestone.ReportHandler;
import com.universal.milestone.Schedule;
import com.universal.milestone.ScheduledTask;
import com.universal.milestone.SecureHandler;
import com.universal.milestone.Selection;
import com.universal.milestone.SelectionManager;
import com.universal.milestone.StringComparator;
import com.universal.milestone.UmlNewReleaseMasterScheduleSubHandler;
import inetsoft.report.Margin;
import inetsoft.report.SectionBand;
import inetsoft.report.XStyleSheet;
import inetsoft.report.lens.DefaultSectionLens;
import inetsoft.report.lens.DefaultTableLens;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.Rectangle;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;
import javax.servlet.http.HttpServletResponse;

public class UmlNewReleaseMasterScheduleSubHandler extends SecureHandler {
  public static final String COMPONENT_CODE = "hUsu";
  
  public GeminiApplication application;
  
  public ComponentLog log;
  
  public static int NUM_COLS = 22;
  
  public UmlNewReleaseMasterScheduleSubHandler(GeminiApplication application) {
    this.application = application;
    this.log = application.getLog("hUsu");
  }
  
  public String getDescription() { return "Sub Report"; }
  
  static boolean tryParseInt(String value) {
    try {
      Integer.parseInt(value);
      return true;
    } catch (NumberFormatException nfe) {
      return false;
    } 
  }
  
  protected static void fillUmlNewReleaseMasterScheduleForPrint(XStyleSheet report, Context context) {
    report.setMargin(new Margin(1.0D, 0.1D, 0.1D, 0.1D));
    report.setFooterFromEdge(0.5D);
    report.setHeaderFromEdge(0.1D);
    SectionBand hbandHeader = new SectionBand(report);
    SectionBand body = new SectionBand(report);
    SectionBand footer = new SectionBand(report);
    DefaultSectionLens group = null;
    DefaultTableLens table_contents = null;
    DefaultTableLens rowCountTable = null;
    DefaultTableLens subTable = null;
    rowCountTable = new DefaultTableLens(2, 10000);
    Color SHADED_AREA_COLOR = Color.lightGray;
    String theConfig = "";
    try {
      HttpServletResponse sresponse = context.getResponse();
      context.putDelivery("status", new String("start_gathering"));
      context.includeJSP("status.jsp", "hiddenFrame");
      sresponse.setContentType("text/plain");
      sresponse.flushBuffer();
    } catch (Exception exception) {}
    Vector selections = MilestoneHelper.getSelectionsForReport(context);
    Hashtable plantIdandName = buildPlantHash();
    try {
      HttpServletResponse sresponse = context.getResponse();
      context.putDelivery("status", new String("start_report"));
      context.putDelivery("percent", new String("10"));
      context.includeJSP("status.jsp", "hiddenFrame");
      sresponse.setContentType("text/plain");
      sresponse.flushBuffer();
    } catch (Exception exception) {}
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
      Hashtable selTable = MilestoneHelper.groupSelectionsByConfigAndStreetDate(selections);
      Enumeration streetDates = selTable.keys();
      Vector streetDatesVector = new Vector();
      while (streetDates.hasMoreElements())
        streetDatesVector.addElement(streetDates.nextElement()); 
      int numConfigs = 0;
      for (int i = 0; i < streetDatesVector.size(); i++) {
        String streetDateName = (streetDatesVector.elementAt(i) != null) ? (String)streetDatesVector.elementAt(i) : "";
        Hashtable streetDateTable = (Hashtable)selTable.get(streetDateName);
        if (streetDateTable != null)
          numConfigs += streetDateTable.size(); 
      } 
      int numExtraRows = 2 + streetDatesVector.size() * 10 + numConfigs;
      int numSelections = selections.size() * 2;
      int numRows = numSelections + numExtraRows;
      numRows -= streetDatesVector.size() * 2 - 1;
      table_contents = new DefaultTableLens(2, NUM_COLS);
      table_contents.setHeaderRowCount(1);
      table_contents.setColWidth(0, 140);
      table_contents.setColWidth(1, 150);
      table_contents.setColWidth(2, 10);
      table_contents.setColWidth(3, 110);
      table_contents.setColWidth(4, 25);
      table_contents.setColWidth(5, 246);
      table_contents.setColWidth(6, 140);
      table_contents.setColWidth(7, 140);
      table_contents.setColWidth(8, 100);
      table_contents.setColWidth(9, 45);
      table_contents.setColWidth(10, 110);
      table_contents.setColWidth(11, 80);
      table_contents.setColWidth(12, 85);
      table_contents.setColWidth(13, 85);
      table_contents.setColWidth(14, 85);
      table_contents.setColWidth(15, 85);
      table_contents.setColWidth(16, 85);
      table_contents.setColWidth(17, 85);
      table_contents.setColWidth(18, 85);
      table_contents.setColWidth(19, 85);
      table_contents.setColWidth(20, 85);
      table_contents.setColWidth(21, 89);
      table_contents.setRowBorder(0, 4097);
      table_contents.setRowBorderColor(-1, SHADED_AREA_COLOR);
      table_contents.setRowBorderColor(0, SHADED_AREA_COLOR);
      table_contents.setAlignment(0, 0, 34);
      table_contents.setAlignment(0, 1, 34);
      table_contents.setAlignment(0, 2, 34);
      table_contents.setAlignment(0, 3, 34);
      table_contents.setAlignment(0, 4, 34);
      table_contents.setAlignment(0, 5, 34);
      table_contents.setAlignment(0, 6, 36);
      table_contents.setAlignment(0, 7, 33);
      table_contents.setAlignment(0, 8, 34);
      table_contents.setAlignment(0, 9, 34);
      table_contents.setAlignment(0, 10, 34);
      table_contents.setAlignment(0, 11, 34);
      table_contents.setAlignment(0, 12, 34);
      table_contents.setAlignment(0, 13, 34);
      table_contents.setAlignment(0, 14, 34);
      table_contents.setAlignment(0, 15, 34);
      table_contents.setAlignment(0, 16, 34);
      table_contents.setAlignment(0, 17, 34);
      table_contents.setAlignment(0, 18, 34);
      table_contents.setAlignment(0, 19, 34);
      table_contents.setAlignment(0, 20, 34);
      table_contents.setAlignment(0, 21, 34);
      table_contents.setInsets(new Insets(-1, 0, 0, 0));
      table_contents.setObject(0, 0, "P&D(*)\nLocal Prod #");
      table_contents.setObject(0, 1, "Rls Family - Label");
      table_contents.setSpan(0, 1, new Dimension(2, 1));
      table_contents.setObject(0, 3, "Plant");
      table_contents.setObject(0, 4, "H\nD");
      table_contents.setObject(0, 5, "Artist");
      table_contents.setObject(0, 6, "Title  /");
      table_contents.setObject(0, 7, "Comments");
      table_contents.setObject(0, 8, "P.O.\nQty");
      table_contents.setObject(0, 9, "Unit");
      table_contents.setObject(0, 10, "Exploded\nTotal");
      table_contents.setObject(0, 11, "Qty\nDone");
      table_contents.setObject(0, 12, "F/M");
      table_contents.setObject(0, 13, "BOM");
      table_contents.setObject(0, 15, "PRQ");
      table_contents.setObject(0, 16, "TAPE");
      table_contents.setObject(0, 17, "FILM");
      table_contents.setObject(0, 20, "PAP");
      table_contents.setObject(0, 18, "STIC");
      table_contents.setObject(0, 19, "PPR");
      table_contents.setObject(0, 14, "FAP");
      table_contents.setObject(0, 21, "PSD\nComp\nDue");
      table_contents.setRowFont(0, new Font("Arial", 1, 7));
      table_contents.setFont(0, 6, new Font("Arial", 3, 7));
      table_contents.setColBorderColor(0, -1, new Color(208, 206, 206, 0));
      table_contents.setColBorderColor(0, 0, new Color(208, 206, 206, 0));
      table_contents.setColBorderColor(0, 1, Color.white);
      table_contents.setColBorderColor(0, 2, new Color(208, 206, 206, 0));
      table_contents.setColBorderColor(0, 3, new Color(208, 206, 206, 0));
      table_contents.setColBorderColor(0, 4, new Color(208, 206, 206, 0));
      table_contents.setColBorderColor(0, 5, new Color(208, 206, 206, 0));
      table_contents.setColBorderColor(0, 6, Color.white);
      table_contents.setColBorderColor(0, 7, new Color(208, 206, 206, 0));
      table_contents.setColBorderColor(0, 8, new Color(208, 206, 206, 0));
      table_contents.setColBorderColor(0, 9, new Color(208, 206, 206, 0));
      table_contents.setColBorderColor(0, 10, new Color(208, 206, 206, 0));
      table_contents.setColBorderColor(0, 11, new Color(208, 206, 206, 0));
      table_contents.setColBorderColor(0, 12, new Color(208, 206, 206, 0));
      table_contents.setColBorderColor(0, 13, new Color(208, 206, 206, 0));
      table_contents.setColBorderColor(0, 14, new Color(208, 206, 206, 0));
      table_contents.setColBorderColor(0, 15, new Color(208, 206, 206, 0));
      table_contents.setColBorderColor(0, 16, new Color(208, 206, 206, 0));
      table_contents.setColBorderColor(0, 17, new Color(208, 206, 206, 0));
      table_contents.setColBorderColor(0, 18, new Color(208, 206, 206, 0));
      table_contents.setColBorderColor(0, 19, new Color(208, 206, 206, 0));
      table_contents.setColBorderColor(0, 20, new Color(208, 206, 206, 0));
      table_contents.setColBorderColor(0, 21, new Color(208, 206, 206, 0));
      table_contents.setSpan(1, 0, new Dimension(22, 1));
      table_contents.setRowHeight(1, 1);
      table_contents.setRowBackground(1, Color.white);
      table_contents.setRowForeground(1, Color.black);
      table_contents.setRowBorderColor(1, SHADED_AREA_COLOR);
      setColBorderColors(1, -1, NUM_COLS - 1, table_contents, new Color(208, 206, 206, 0));
      setColBorders(1, -1, NUM_COLS - 1, table_contents, 0);
      int nextRow = 0;
      Vector sortedStreetDatesVector = MilestoneHelper.sortDates(streetDatesVector);
      hbandHeader.setHeight(1.0F);
      hbandHeader.setShrinkToFit(true);
      hbandHeader.setVisible(true);
      hbandHeader.setBottomBorder(0);
      hbandHeader.addTable(table_contents, new Rectangle(800, 800));
      int totalCount = 0;
      int tenth = 1;
      for (int a = 0; a < sortedStreetDatesVector.size(); a++) {
        totalCount++;
        String datesC = (String)sortedStreetDatesVector.get(a);
        Hashtable configTableC = (Hashtable)selTable.get(datesC);
        if (configTableC != null) {
          Enumeration monthsC = configTableC.keys();
          Vector monthVectorC = new Vector();
          while (monthsC.hasMoreElements())
            monthVectorC.add((String)monthsC.nextElement()); 
          Object[] configsArrayC = null;
          configsArrayC = monthVectorC.toArray();
          for (int b = 0; b < configsArrayC.length; b++) {
            totalCount++;
            String monthNameC = (String)configsArrayC[b];
            Vector selectionsC = (Vector)configTableC.get(monthNameC);
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
      try {
        sresponse.flushBuffer();
      } catch (IOException iOException) {}
      int recordCount = 0;
      int count = 0;
      for (int n = 0; n < sortedStreetDatesVector.size(); n++) {
        if (count < recordCount / tenth) {
          count = recordCount / tenth;
          sresponse = context.getResponse();
          context.putDelivery("status", new String("start_report"));
          context.putDelivery("percent", new String(String.valueOf(count * 10)));
          context.includeJSP("status.jsp", "hiddenFrame");
          sresponse.setContentType("text/plain");
          try {
            sresponse.flushBuffer();
          } catch (IOException iOException) {}
        } 
        recordCount++;
        int piTotal = 0;
        int kmTotal = 0;
        int haTotal = 0;
        int glTotal = 0;
        int ciTotal = 0;
        int paTotal = 0;
        int civTotal = 0;
        Hashtable plantTotals = buildTotals(plantIdandName);
        String theStreetDate = (String)sortedStreetDatesVector.elementAt(n);
        String theStreetDateText = !theStreetDate.trim().equals("") ? theStreetDate : "Other";
        footer.setVisible(true);
        footer.setHeight(0.05F);
        footer.setShrinkToFit(true);
        footer.setBottomBorder(0);
        nextRow = 0;
        Hashtable configTable = (Hashtable)selTable.get(theStreetDate);
        if (configTable != null) {
          Enumeration configsEnum = configTable.keys();
          Vector configsVector = new Vector();
          while (configsEnum.hasMoreElements())
            configsVector.add((String)configsEnum.nextElement()); 
          Object[] configsList = configsVector.toArray();
          Arrays.sort(configsList, new StringComparator());
          for (int x = 0; x < configsList.length; x++) {
            nextRow = 0;
            theConfig = (String)configsList[x];
            if (count < recordCount / tenth) {
              count = recordCount / tenth;
              sresponse = context.getResponse();
              context.putDelivery("status", new String("start_report"));
              context.putDelivery("percent", new String(String.valueOf(count * 10)));
              context.includeJSP("status.jsp", "hiddenFrame");
              sresponse.setContentType("text/plain");
              try {
                sresponse.flushBuffer();
              } catch (IOException iOException) {}
            } 
            recordCount++;
            footer.setVisible(true);
            footer.setHeight(0.05F);
            footer.setShrinkToFit(true);
            footer.setBottomBorder(0);
            selections = (Vector)configTable.get(theConfig);
            if (selections == null)
              selections = new Vector(); 
            MilestoneHelper.setSelectionSorting(selections, 15);
            Collections.sort(selections);
            MilestoneHelper.setSelectionSorting(selections, 14);
            Collections.sort(selections);
            MilestoneHelper.setSelectionSorting(selections, 24);
            Collections.sort(selections);
            MilestoneHelper.applyManufacturingToSelections(selections);
            for (int i = 0; i < selections.size(); i++) {
              Selection sel = (Selection)selections.elementAt(i);
              Vector plants = sel.getManufacturingPlants();
              int plantSize = 1;
              if (plants != null && plants.size() > 0)
                plantSize = plants.size(); 
              for (int plantCount = 0; plantCount < plantSize; plantCount++) {
                if (i == 0 && plantCount == 0) {
                  subTable = new DefaultTableLens(8, NUM_COLS);
                } else {
                  subTable = new DefaultTableLens(2, NUM_COLS);
                } 
                Plant p = null;
                if (plants != null && plants.size() > 0)
                  p = (Plant)plants.get(plantCount); 
                nextRow = 0;
                subTable.setColWidth(0, 140);
                subTable.setColWidth(1, 150);
                subTable.setColWidth(2, 10);
                subTable.setColWidth(3, 110);
                subTable.setColWidth(4, 25);
                subTable.setColWidth(5, 247);
                subTable.setColWidth(6, 140);
                subTable.setColWidth(7, 140);
                subTable.setColWidth(8, 100);
                subTable.setColWidth(9, 45);
                subTable.setColWidth(10, 110);
                subTable.setColWidth(11, 80);
                subTable.setColWidth(12, 85);
                subTable.setColWidth(13, 85);
                subTable.setColWidth(14, 85);
                subTable.setColWidth(15, 85);
                subTable.setColWidth(16, 85);
                subTable.setColWidth(17, 85);
                subTable.setColWidth(18, 85);
                subTable.setColWidth(19, 85);
                subTable.setColWidth(20, 85);
                subTable.setColWidth(21, 88);
                subTable.setRowBorder(0, 4097);
                subTable.setLineWrap(0, 0, false);
                subTable.setLineWrap(0, 1, false);
                subTable.setLineWrap(0, 2, false);
                subTable.setLineWrap(0, 3, false);
                subTable.setLineWrap(0, 4, false);
                subTable.setLineWrap(0, 5, false);
                subTable.setLineWrap(0, 6, false);
                subTable.setLineWrap(0, 7, false);
                subTable.setLineWrap(0, 8, false);
                subTable.setLineWrap(0, 9, false);
                subTable.setLineWrap(0, 10, false);
                subTable.setLineWrap(0, 11, false);
                subTable.setLineWrap(0, 12, false);
                subTable.setLineWrap(0, 13, false);
                subTable.setLineWrap(0, 14, false);
                subTable.setLineWrap(0, 15, false);
                subTable.setLineWrap(0, 16, false);
                subTable.setLineWrap(0, 17, false);
                subTable.setLineWrap(0, 18, false);
                subTable.setLineWrap(0, 19, false);
                subTable.setLineWrap(0, 20, false);
                subTable.setLineWrap(0, 21, false);
                subTable.setAlignment(0, 0, 34);
                subTable.setAlignment(0, 1, 34);
                subTable.setAlignment(0, 2, 34);
                subTable.setAlignment(0, 3, 34);
                subTable.setAlignment(0, 4, 34);
                subTable.setAlignment(0, 5, 36);
                subTable.setAlignment(0, 6, 33);
                subTable.setAlignment(0, 7, 34);
                subTable.setAlignment(0, 8, 34);
                subTable.setAlignment(0, 9, 34);
                subTable.setAlignment(0, 10, 34);
                subTable.setAlignment(0, 11, 34);
                subTable.setAlignment(0, 12, 34);
                subTable.setAlignment(0, 13, 34);
                subTable.setAlignment(0, 14, 34);
                subTable.setAlignment(0, 15, 34);
                subTable.setAlignment(0, 16, 34);
                subTable.setAlignment(0, 17, 34);
                subTable.setAlignment(0, 18, 34);
                subTable.setAlignment(0, 19, 34);
                subTable.setAlignment(0, 20, 34);
                subTable.setAlignment(0, 21, 34);
                int artistLength = 0, labelLength = 0, titleLength = 0, idLength = 0;
                if (count < recordCount / tenth) {
                  count = recordCount / tenth;
                  sresponse = context.getResponse();
                  context.putDelivery("status", new String("start_report"));
                  context.putDelivery("percent", new String(String.valueOf(count * 10)));
                  context.includeJSP("status.jsp", "hiddenFrame");
                  sresponse.setContentType("text/plain");
                  try {
                    sresponse.flushBuffer();
                  } catch (IOException iOException) {}
                } 
                recordCount++;
                String selectionNo = SelectionManager.getLookupObjectValue(sel.getPrefixID());
                if (selectionNo == null)
                  selectionNo = ""; 
                if (sel != null) {
                  selectionNo = String.valueOf(selectionNo) + sel.getSelectionNo().trim();
                  idLength = selectionNo.length();
                } 
                String pd = "";
                if (sel != null && sel.getPressAndDistribution())
                  pd = "* "; 
                String selDistribution = SelectionManager.getLookupObjectValue(sel.getDistribution());
                String artist = "";
                if (sel != null) {
                  artist = sel.getArtist();
                } else {
                  artist = null;
                } 
                if (artist != null)
                  artistLength = artist.length(); 
                String comment = "";
                try {
                  comment = (sel.getSelectionComments() != null) ? sel.getSelectionComments().trim() : "";
                } catch (Exception ex) {
                  System.out.print("comment:" + ex);
                } 
                String mfgComment = "";
                try {
                  mfgComment = (sel.getManufacturingComments() != null) ? sel.getManufacturingComments().trim() : "";
                } catch (Exception ed) {
                  System.out.print("mfgcoment:" + ed);
                } 
                String releasingFamily = "";
                if (sel != null && sel.getReleaseFamilyId() > 0)
                  releasingFamily = ReleasingFamily.getName(sel.getReleaseFamilyId()); 
                String label = "";
                if (sel != null && sel.getImprint() != null)
                  label = sel.getImprint(); 
                try {
                  labelLength = label.length() + releasingFamily.length();
                } catch (Exception ef) {
                  System.out.print("labellengh:" + ef);
                } 
                String titleComments = "";
                try {
                  titleComments = sel.getTitle();
                } catch (Exception rg) {
                  System.out.print("title coments:" + rg);
                } 
                if (titleComments != null)
                  titleLength = titleComments.length(); 
                String poQty = "0";
                try {
                  poQty = (p != null && p.getOrderQty() > 0) ? String.valueOf(p.getOrderQty()) : "0";
                } catch (Exception ex) {
                  System.out.print("poQty:" + ex);
                } 
                String units = "";
                try {
                  units = (sel.getNumberOfUnits() > 0) ? String.valueOf(sel.getNumberOfUnits()) : "";
                } catch (Exception ex) {
                  System.out.print("Units:" + ex);
                } 
                int poQtyNum = 0;
                try {
                  poQtyNum = Integer.parseInt(poQty);
                } catch (Exception e) {
                  System.out.print("poQtyNum:" + e);
                } 
                int explodedTotal = 0;
                try {
                  if (poQtyNum > 0 && sel != null && sel.getNumberOfUnits() > 0)
                    explodedTotal = poQtyNum * sel.getNumberOfUnits(); 
                } catch (Exception g) {
                  System.out.print("poQtyNum:" + g);
                } 
                String plant = "";
                String plantText = "";
                if (p != null && p.getPlant() != null) {
                  String plantNo = (p.getPlant().getName() != null) ? p.getPlant().getName() : "";
                  plant = (p.getPlant().getAbbreviation() != null) ? p.getPlant().getAbbreviation() : "";
                  plantText = (p.getPlant().getName() != null) ? p.getPlant().getName() : "";
                  String plantId = p.getPlant().getAbbreviation();
                  if (plantIdandName != null && plantIdandName.get(plantId) != null) {
                    plant = (String)plantIdandName.get(plantId);
                  } else {
                    plant = "";
                  } 
                  int currentTotal = 0;
                  if (tryParseInt((String)plantTotals.get(plantId)))
                    currentTotal = Integer.parseInt((String)plantTotals.get(plantId)); 
                  int newTotal = currentTotal + explodedTotal;
                  plantTotals.put(plantId, String.valueOf(newTotal));
                } 
                String compQty = "0";
                if (p != null && p.getCompletedQty() > 0)
                  try {
                    compQty = MilestoneHelper.formatQuantityWithCommas(String.valueOf(p.getCompletedQty()));
                  } catch (Exception f) {
                    System.out.print("compQty:" + f);
                  }  
                Schedule schedule = (sel.getSchedule() != null) ? sel.getSchedule() : null;
                Vector tasks = (schedule != null) ? schedule.getTasks() : null;
                ScheduledTask task = null;
                String FM = "";
                String BOM = "";
                String PRQ = "";
                String TAPE = "";
                String FILM = "";
                String PAP = "";
                String STIC = "";
                String MC = "";
                String FAP = "";
                String PSD = "";
                String dueDateHolidayFlg = "";
                String MCvend = "";
                boolean hasPPRtask = false;
                if (tasks != null) {
                  PSD = "N/A";
                  for (int j = 0; j < tasks.size(); j++) {
                    task = (ScheduledTask)tasks.get(j);
                    String taskAbbrev = MilestoneHelper.getTaskAbbreivationNameById(task.getTaskAbbreviationID());
                    String taskVendor = (task.getVendor() != null) ? task.getVendor() : "";
                    taskVendor = taskVendor.equals("\n") ? "" : taskVendor;
                    if (taskAbbrev.equalsIgnoreCase("F/M")) {
                      FM = MilestoneHelper.getFormatedDate(task.getCompletionDate());
                      if (task.getScheduledTaskStatus() != null && 
                        task.getScheduledTaskStatus().equals("N/A"))
                        FM = "N/A"; 
                    } else if (taskAbbrev.equalsIgnoreCase("BOM")) {
                      BOM = MilestoneHelper.getFormatedDate(task.getCompletionDate());
                      if (task.getScheduledTaskStatus() != null && 
                        task.getScheduledTaskStatus().equals("N/A"))
                        BOM = "N/A"; 
                    } else if (taskAbbrev.equalsIgnoreCase("PRQ")) {
                      PRQ = MilestoneHelper.getFormatedDate(task.getCompletionDate());
                      if (task.getScheduledTaskStatus() != null && 
                        task.getScheduledTaskStatus().equals("N/A"))
                        PRQ = "N/A"; 
                    } else if (taskAbbrev.equalsIgnoreCase("TAPE")) {
                      TAPE = MilestoneHelper.getFormatedDate(task.getCompletionDate());
                      if (task.getScheduledTaskStatus() != null && 
                        task.getScheduledTaskStatus().equals("N/A"))
                        TAPE = "N/A"; 
                    } else if (taskAbbrev.equalsIgnoreCase("FILM")) {
                      FILM = MilestoneHelper.getFormatedDate(task.getCompletionDate());
                      if (task.getScheduledTaskStatus() != null && 
                        task.getScheduledTaskStatus().equals("N/A"))
                        FILM = "N/A"; 
                    } else if (taskAbbrev.equalsIgnoreCase("PAP")) {
                      PAP = MilestoneHelper.getFormatedDate(task.getCompletionDate());
                      if (task.getScheduledTaskStatus() != null && 
                        task.getScheduledTaskStatus().equals("N/A"))
                        PAP = "N/A"; 
                    } else if (taskAbbrev.equalsIgnoreCase("STIC")) {
                      STIC = MilestoneHelper.getFormatedDate(task.getCompletionDate());
                      if (task.getScheduledTaskStatus() != null && 
                        task.getScheduledTaskStatus().equals("N/A"))
                        STIC = "N/A"; 
                    } else if (taskAbbrev.equalsIgnoreCase("M/C")) {
                      MC = MilestoneHelper.getFormatedDate(task.getCompletionDate());
                      if (task.getScheduledTaskStatus() != null && 
                        task.getScheduledTaskStatus().equals("N/A"))
                        MC = "N/A"; 
                      MCvend = taskVendor;
                      hasPPRtask = true;
                    } else if (taskAbbrev.equalsIgnoreCase("FAP")) {
                      FAP = MilestoneHelper.getFormatedDate(task.getCompletionDate());
                      if (task.getScheduledTaskStatus() != null && 
                        task.getScheduledTaskStatus().equals("N/A"))
                        FAP = "N/A"; 
                    } else if (taskAbbrev.equalsIgnoreCase("PSD")) {
                      if (task.getScheduledTaskStatus() != null && 
                        task.getScheduledTaskStatus().equals("N/A")) {
                        PSD = "N/A";
                      } else {
                        dueDateHolidayFlg = MilestoneHelper.getDayType(sel.getCalendarGroup(), task.getDueDate());
                        String PsdCompDt = (task.getCompletionDate() == null) ? "" : MilestoneHelper.getFormatedDate(task.getCompletionDate());
                        String PsdDueDt = (task.getDueDate() == null) ? "" : MilestoneHelper.getFormatedDate(task.getDueDate());
                        PSD = String.valueOf(PsdCompDt) + "\n" + PsdDueDt + " " + dueDateHolidayFlg;
                      } 
                    } 
                    task = null;
                  } 
                } 
                nextRow = 0;
                if (i == 0 && plantCount == 0) {
                  nextRow = ReportHandler.insertLightGrayHeader(subTable, theStreetDateText, nextRow, 22);
                  subTable.setRowBorder(nextRow, 4097);
                  subTable.setRowBorder(nextRow + 1, 4097);
                  setColBorders(nextRow, -1, NUM_COLS - 1, subTable, 0);
                  subTable.setColBorderColor(nextRow, Color.white);
                  setColBorderColors(nextRow, -1, NUM_COLS - 1, subTable, Color.white);
                  subTable.setSpan(nextRow, 0, new Dimension(22, 1));
                  subTable.setObject(nextRow, 0, theConfig);
                  subTable.setAlignment(0, 0, 33);
                  subTable.setRowFont(nextRow, new Font("Arial", 1, 9));
                  subTable.setRowBorderColor(-1, Color.lightGray);
                  subTable.setRowBorderColor(Color.lightGray);
                  nextRow++;
                  subTable.setHeaderRowCount(0);
                  subTable.setSpan(nextRow, 0, new Dimension(22, 1));
                  subTable.setRowHeight(nextRow, 2);
                  subTable.setRowBackground(nextRow, Color.white);
                  subTable.setRowForeground(nextRow, Color.black);
                  setColBorderColors(nextRow, -1, NUM_COLS - 1, subTable, new Color(208, 206, 206, 0));
                  setColBorders(nextRow, -1, NUM_COLS - 1, subTable, 0);
                  nextRow++;
                } 
                subTable.setRowInsets(nextRow, new Insets(0, 0, 5, 0));
                if (sel.getSpecialPackaging()) {
                  if (BOM.length() > 0) {
                    BOM = String.valueOf(BOM) + "\n";
                    subTable.setRowHeight(nextRow, 15);
                  } 
                  BOM = String.valueOf(BOM) + "sp. pkg";
                } 
                if (plantText.length() > 13 || labelLength > 17 || artistLength > 27) {
                  subTable.setRowHeight(nextRow, 15);
                  subTable.setRowHeight(nextRow + 1, 1);
                } 
                subTable.setRowInsets(nextRow, new Insets(0, 0, 5, 0));
                subTable.setSpan(nextRow, 0, new Dimension(1, 2));
                subTable.setSpan(nextRow, 1, new Dimension(2, 2));
                subTable.setSpan(nextRow, 2, new Dimension(1, 2));
                subTable.setSpan(nextRow, 3, new Dimension(1, 2));
                subTable.setSpan(nextRow, 4, new Dimension(1, 2));
                subTable.setSpan(nextRow, 5, new Dimension(1, 2));
                subTable.setSpan(nextRow, 6, new Dimension(2, 2));
                subTable.setSpan(nextRow, 7, new Dimension(1, 2));
                subTable.setSpan(nextRow, 8, new Dimension(1, 2));
                subTable.setSpan(nextRow, 9, new Dimension(1, 2));
                subTable.setSpan(nextRow, 10, new Dimension(1, 2));
                subTable.setSpan(nextRow, 11, new Dimension(1, 2));
                subTable.setSpan(nextRow, 12, new Dimension(1, 2));
                subTable.setSpan(nextRow, 13, new Dimension(1, 2));
                subTable.setSpan(nextRow, 14, new Dimension(1, 2));
                subTable.setSpan(nextRow, 15, new Dimension(1, 2));
                subTable.setSpan(nextRow, 16, new Dimension(1, 2));
                subTable.setSpan(nextRow, 17, new Dimension(1, 2));
                subTable.setSpan(nextRow, 18, new Dimension(1, 2));
                subTable.setSpan(nextRow, 19, new Dimension(1, 2));
                subTable.setSpan(nextRow, 20, new Dimension(1, 2));
                subTable.setSpan(nextRow, 21, new Dimension(1, 2));
                if (idLength == 13 && pd.trim().equals("*"))
                  pd = "*"; 
                subTable.setObject(nextRow, 0, String.valueOf(pd) + selectionNo);
                subTable.setObject(nextRow, 1, String.valueOf(releasingFamily) + " - " + label);
                subTable.setObject(nextRow, 3, plantText);
                subTable.setObject(nextRow, 4, selDistribution);
                subTable.setObject(nextRow, 5, artist);
                subTable.setSpan(nextRow, 6, new Dimension(2, 1));
                subTable.setSpan(nextRow + 1, 6, new Dimension(2, 1));
                subTable.setObject(nextRow, 6, titleComments);
                subTable.setObject(nextRow, 8, MilestoneHelper.formatQuantityWithCommas(poQty));
                subTable.setObject(nextRow, 9, units);
                subTable.setObject(nextRow, 10, MilestoneHelper.formatQuantityWithCommas(String.valueOf(explodedTotal)));
                subTable.setObject(nextRow, 11, MilestoneHelper.formatQuantityWithCommas(compQty));
                subTable.setObject(nextRow, 12, FM);
                subTable.setObject(nextRow, 13, BOM);
                subTable.setObject(nextRow, 15, PRQ);
                subTable.setObject(nextRow, 16, TAPE);
                subTable.setObject(nextRow, 17, FILM);
                subTable.setObject(nextRow, 20, PAP);
                subTable.setObject(nextRow, 18, STIC);
                if (tasks == null) {
                  subTable.setObject(nextRow, 19, "");
                } else if (!hasPPRtask) {
                  subTable.setObject(nextRow, 19, "N/A");
                } else if (MCvend != null && !MCvend.equals("")) {
                  subTable.setObject(nextRow, 19, String.valueOf(MC) + "\n" + MCvend);
                } else {
                  subTable.setObject(nextRow, 19, MC);
                } 
                subTable.setObject(nextRow, 14, FAP);
                subTable.setObject(nextRow, 21, PSD);
                subTable.setAlignment(nextRow, 0, 4);
                subTable.setLineWrap(nextRow, 1, true);
                subTable.setLineWrap(nextRow, 2, true);
                subTable.setLineWrap(nextRow, 3, true);
                subTable.setLineWrap(nextRow, 4, true);
                subTable.setLineWrap(nextRow, 5, true);
                subTable.setLineWrap(nextRow, 6, true);
                subTable.setLineWrap(nextRow, 7, true);
                subTable.setLineWrap(nextRow, 8, true);
                subTable.setLineWrap(nextRow, 9, true);
                subTable.setLineWrap(nextRow, 10, true);
                subTable.setLineWrap(nextRow, 11, true);
                subTable.setLineWrap(nextRow, 12, true);
                subTable.setLineWrap(nextRow, 13, true);
                subTable.setLineWrap(nextRow, 14, true);
                subTable.setLineWrap(nextRow, 15, true);
                subTable.setLineWrap(nextRow, 16, true);
                subTable.setLineWrap(nextRow, 17, true);
                subTable.setLineWrap(nextRow, 18, true);
                subTable.setLineWrap(nextRow, 19, true);
                subTable.setLineWrap(nextRow, 20, true);
                subTable.setLineWrap(nextRow, 21, true);
                subTable.setAlignment(nextRow, 0, 8);
                subTable.setAlignment(nextRow, 1, 8);
                subTable.setAlignment(nextRow, 2, 8);
                subTable.setAlignment(nextRow, 3, 8);
                subTable.setAlignment(nextRow, 4, 8);
                subTable.setAlignment(nextRow, 5, 8);
                subTable.setAlignment(nextRow, 6, 8);
                subTable.setAlignment(nextRow, 7, 12);
                subTable.setAlignment(nextRow, 8, 12);
                subTable.setAlignment(nextRow, 9, 12);
                subTable.setAlignment(nextRow, 10, 12);
                subTable.setAlignment(nextRow, 11, 12);
                subTable.setAlignment(nextRow, 12, 12);
                subTable.setAlignment(nextRow, 13, 12);
                subTable.setAlignment(nextRow, 14, 12);
                subTable.setAlignment(nextRow, 15, 12);
                subTable.setAlignment(nextRow, 16, 12);
                subTable.setAlignment(nextRow, 17, 12);
                subTable.setAlignment(nextRow, 18, 12);
                subTable.setAlignment(nextRow, 19, 12);
                subTable.setAlignment(nextRow, 20, 12);
                subTable.setAlignment(nextRow, 21, 12);
                subTable.setRowInsets(nextRow, new Insets(0, 0, 0, 0));
                subTable.setRowFont(nextRow, new Font("Arial", 0, 6));
                if (dueDateHolidayFlg != "")
                  subTable.setFont(nextRow, 21, new Font("Arial", 3, 6)); 
                subTable.setColBorderColor(nextRow, -1, new Color(208, 206, 206, 0));
                subTable.setColBorderColor(nextRow, 0, new Color(208, 206, 206, 0));
                subTable.setColBorderColor(nextRow, 1, new Color(208, 206, 206, 0));
                subTable.setColBorderColor(nextRow, 2, new Color(208, 206, 206, 0));
                subTable.setColBorderColor(nextRow, 3, new Color(208, 206, 206, 0));
                subTable.setColBorderColor(nextRow, 4, new Color(208, 206, 206, 0));
                subTable.setColBorderColor(nextRow, 5, new Color(208, 206, 206, 0));
                subTable.setColBorderColor(nextRow, 6, new Color(208, 206, 206, 0));
                subTable.setColBorderColor(nextRow, 7, new Color(208, 206, 206, 0));
                subTable.setColBorderColor(nextRow, 8, new Color(208, 206, 206, 0));
                subTable.setColBorderColor(nextRow, 9, new Color(208, 206, 206, 0));
                subTable.setColBorderColor(nextRow, 10, new Color(208, 206, 206, 0));
                subTable.setColBorderColor(nextRow, 11, new Color(208, 206, 206, 0));
                subTable.setColBorderColor(nextRow, 12, new Color(208, 206, 206, 0));
                subTable.setColBorderColor(nextRow, 13, new Color(208, 206, 206, 0));
                subTable.setColBorderColor(nextRow, 14, new Color(208, 206, 206, 0));
                subTable.setColBorderColor(nextRow, 15, new Color(208, 206, 206, 0));
                subTable.setColBorderColor(nextRow, 16, new Color(208, 206, 206, 0));
                subTable.setColBorderColor(nextRow, 17, new Color(208, 206, 206, 0));
                subTable.setColBorderColor(nextRow, 18, new Color(208, 206, 206, 0));
                subTable.setColBorderColor(nextRow, 19, new Color(208, 206, 206, 0));
                subTable.setColBorderColor(nextRow, 20, new Color(208, 206, 206, 0));
                subTable.setColBorderColor(nextRow, 21, new Color(208, 206, 206, 0));
                subTable.setRowBorder(nextRow, 4097);
                nextRow++;
                subTable.setRowBorderColor(nextRow - 2, Color.lightGray);
                subTable.setRowBorderColor(nextRow - 1, Color.white);
                subTable.setRowBorderColor(nextRow, Color.lightGray);
                subTable.setRowBorderColor(nextRow, 0, Color.lightGray);
                subTable.setRowBorderColor(nextRow, 6, Color.lightGray);
                subTable.setColBorderColor(nextRow, -1, new Color(208, 206, 206, 0));
                subTable.setColBorderColor(nextRow, 0, new Color(208, 206, 206, 0));
                subTable.setColBorderColor(nextRow, 1, new Color(208, 206, 206, 0));
                subTable.setColBorderColor(nextRow, 2, new Color(208, 206, 206, 0));
                subTable.setColBorderColor(nextRow, 3, new Color(208, 206, 206, 0));
                subTable.setColBorderColor(nextRow, 4, new Color(208, 206, 206, 0));
                subTable.setColBorderColor(nextRow, 5, new Color(208, 206, 206, 0));
                subTable.setColBorderColor(nextRow, 6, new Color(208, 206, 206, 0));
                subTable.setColBorderColor(nextRow, 7, new Color(208, 206, 206, 0));
                subTable.setColBorderColor(nextRow, 8, new Color(208, 206, 206, 0));
                subTable.setColBorderColor(nextRow, 9, new Color(208, 206, 206, 0));
                subTable.setColBorderColor(nextRow, 10, new Color(208, 206, 206, 0));
                subTable.setColBorderColor(nextRow, 11, new Color(208, 206, 206, 0));
                subTable.setColBorderColor(nextRow, 12, new Color(208, 206, 206, 0));
                subTable.setColBorderColor(nextRow, 13, new Color(208, 206, 206, 0));
                subTable.setColBorderColor(nextRow, 14, new Color(208, 206, 206, 0));
                subTable.setColBorderColor(nextRow, 15, new Color(208, 206, 206, 0));
                subTable.setColBorderColor(nextRow, 16, new Color(208, 206, 206, 0));
                subTable.setColBorderColor(nextRow, 17, new Color(208, 206, 206, 0));
                subTable.setColBorderColor(nextRow, 18, new Color(208, 206, 206, 0));
                subTable.setColBorderColor(nextRow, 19, new Color(208, 206, 206, 0));
                subTable.setColBorderColor(nextRow, 20, new Color(208, 206, 206, 0));
                subTable.setColBorderColor(nextRow, 21, new Color(208, 206, 206, 0));
                subTable.setRowInsets(nextRow, new Insets(0, 0, 0, 0));
                if (!MCvend.equals(""))
                  subTable.setRowHeight(nextRow, 15); 
                if (mfgComment != null && mfgComment.trim().length() > 0) {
                  subTable.setRowAutoSize(true);
                  subTable.setObject(nextRow, 6, mfgComment);
                  subTable.setFont(nextRow, 6, new Font("Arial", 2, 6));
                  if (mfgComment.length() > 40) {
                    subTable.setRowHeight(nextRow, 60);
                  } else if (mfgComment.length() > 10) {
                    subTable.setRowHeight(nextRow, 15);
                  } else {
                    subTable.setRowHeight(nextRow, 15);
                  } 
                } else if (artistLength < 26 && labelLength < 21 && idLength + pd.length() < 15) {
                  subTable.setRowHeight(nextRow, 3);
                } else if (labelLength > 30) {
                  subTable.setRowHeight(nextRow, 8);
                } 
                body = new SectionBand(report);
                if (mfgComment != null && mfgComment.trim().length() > 0) {
                  double lfLineCount = 1.5D;
                  if (mfgComment.trim().length() > 100)
                    lfLineCount = 8.0D; 
                  if (mfgComment.trim().length() > 40)
                    lfLineCount = 4.0D; 
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
                nextRow = 0;
              } 
            } 
            if (theConfig.equals("Compact Disc") && totalsExist(plantTotals)) {
              nextRow = 0;
              subTable = new DefaultTableLens(1, 14);
              subTable.setColWidth(0, 90);
              subTable.setColWidth(1, 90);
              subTable.setColWidth(2, 90);
              subTable.setColWidth(3, 90);
              subTable.setColWidth(4, 90);
              subTable.setColWidth(5, 90);
              subTable.setColWidth(6, 90);
              subTable.setColWidth(7, 90);
              subTable.setColWidth(8, 90);
              subTable.setColWidth(9, 90);
              subTable.setColWidth(10, 90);
              subTable.setColWidth(11, 90);
              subTable.setColWidth(12, 90);
              subTable.setColWidth(13, 90);
              subTable.setRowBorder(0);
              printTotals(plantTotals, plantIdandName, subTable, nextRow);
              subTable.setFont(nextRow, 0, new Font("Arial", 1, 7));
              subTable.setFont(nextRow, 1, new Font("Arial", 0, 7));
              subTable.setFont(nextRow, 2, new Font("Arial", 1, 7));
              subTable.setFont(nextRow, 3, new Font("Arial", 0, 7));
              subTable.setFont(nextRow, 4, new Font("Arial", 1, 7));
              subTable.setFont(nextRow, 5, new Font("Arial", 0, 7));
              subTable.setFont(nextRow, 6, new Font("Arial", 1, 7));
              subTable.setFont(nextRow, 7, new Font("Arial", 0, 7));
              subTable.setFont(nextRow, 8, new Font("Arial", 1, 7));
              subTable.setFont(nextRow, 9, new Font("Arial", 0, 7));
              subTable.setFont(nextRow, 10, new Font("Arial", 1, 7));
              subTable.setFont(nextRow, 11, new Font("Arial", 0, 7));
              subTable.setFont(nextRow, 12, new Font("Arial", 1, 7));
              subTable.setFont(nextRow, 13, new Font("Arial", 0, 7));
              setColBorders(nextRow, -1, NUM_COLS - 1, subTable, 0);
              setColBorderColors(nextRow, -1, NUM_COLS - 1, subTable, Color.white);
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
          } 
        } 
      } 
      group = new DefaultSectionLens(hbandHeader, group, null);
      report.addSection(group, rowCountTable);
      group = null;
    } catch (Exception e) {
      System.out.println(">>>>>>>>errors");
      System.out.println(">>>>>>>>ReportHandler.fillUmlNewReleaseMasterScheduleSubHandler(): exception: " + e.getMessage());
      System.out.println(">>>>>>>>ReportHandler.fillUmlNewReleaseMasterScheduleSubHandler(): exception: " + e);
    } 
  }
  
  private static void setColBorderColors(int rowNum, int start, int end, DefaultTableLens table, Color color) {
    end++;
    for (int i = start; i < end; i++)
      table.setColBorderColor(rowNum, i, Color.white); 
  }
  
  private static void setRowBorderColors(int rowNum, int start, int end, DefaultTableLens table, Color color) {
    end++;
    for (int i = start; i < end; i++)
      table.setRowBorderColor(rowNum, i, Color.white); 
  }
  
  private static void setColBorders(int rowNum, int start, int end, DefaultTableLens table, int size) {
    end++;
    for (int i = start; i < end; i++)
      table.setColBorder(rowNum, i, size); 
  }
  
  private static Hashtable buildPlantHash() {
    plantNames = new Hashtable();
    String plantQuery = " SELECT det_value, description FROM vi_Lookup_SubDetail WHERE field_id = 22";
    JdbcConnector connector = MilestoneHelper.getConnector(plantQuery);
    connector.setForwardOnly(false);
    connector.runQuery();
    while (connector.more()) {
      String plantId = connector.getField("det_value");
      String plantDescription = connector.getField("description");
      if (plantId != null && !plantId.equals("") && plantDescription != null && !plantDescription.equals(""))
        plantNames.put(plantId, plantDescription); 
      connector.next();
    } 
    connector.close();
    return plantNames;
  }
  
  private static Hashtable buildTotals(Hashtable namesHash) {
    Enumeration keysEnum = namesHash.keys();
    Hashtable plantTotals = new Hashtable();
    while (keysEnum.hasMoreElements())
      plantTotals.put((String)keysEnum.nextElement(), "0"); 
    return plantTotals;
  }
  
  private static boolean totalsExist(Hashtable plantTotals) {
    Enumeration keysEnum = plantTotals.keys();
    while (keysEnum.hasMoreElements()) {
      int total = Integer.parseInt((String)plantTotals.get(keysEnum.nextElement()));
      if (total > 0)
        return true; 
    } 
    return false;
  }
  
  private static void printTotals(Hashtable plantTotals, Hashtable plantNames, DefaultTableLens subTable, int nextRow) {
    Enumeration keysEnum = plantTotals.keys();
    Vector keys = new Vector();
    int columnCount = 0;
    while (keysEnum.hasMoreElements())
      keys.add(keysEnum.nextElement()); 
    int maxCol = 14;
    for (int i = 0; i < keys.size(); i++) {
      String idString = (plantTotals.get(keys.get(i)) != null) ? (String)plantTotals.get(keys.get(i)) : "0";
      int total = Integer.parseInt(idString);
      String plantName = (plantNames.get(keys.get(i)) != null) ? (String)plantNames.get(keys.get(i)) : "";
      if (total > 0 && columnCount < 14) {
        subTable.setObject(nextRow, columnCount, String.valueOf(plantName) + ":\n" + MilestoneHelper.formatQuantityWithCommas(String.valueOf(total)));
        columnCount++;
      } 
    } 
  }
}


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\UmlNewReleaseMasterScheduleSubHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */