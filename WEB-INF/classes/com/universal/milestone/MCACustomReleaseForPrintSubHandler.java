package WEB-INF.classes.com.universal.milestone;

import com.techempower.ComponentLog;
import com.techempower.gemini.Context;
import com.techempower.gemini.GeminiApplication;
import com.universal.milestone.DatePeriod;
import com.universal.milestone.Form;
import com.universal.milestone.MCACustomReleaseForPrintSubHandler;
import com.universal.milestone.MilestoneHelper;
import com.universal.milestone.MilestoneHelper_2;
import com.universal.milestone.MonthYearComparator;
import com.universal.milestone.SecureHandler;
import com.universal.milestone.Selection;
import com.universal.milestone.SelectionManager;
import com.universal.milestone.SelectionStatus;
import inetsoft.report.CompositeSheet;
import inetsoft.report.SectionBand;
import inetsoft.report.SeparatorElement;
import inetsoft.report.StyleSheet;
import inetsoft.report.XStyleSheet;
import inetsoft.report.io.Builder;
import inetsoft.report.lens.DefaultSectionLens;
import inetsoft.report.lens.DefaultTableLens;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Rectangle;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;
import javax.servlet.http.HttpServletResponse;

public class MCACustomReleaseForPrintSubHandler extends SecureHandler {
  public static final String COMPONENT_CODE = "hCProd";
  
  public GeminiApplication application;
  
  public ComponentLog log;
  
  public void MCACustomReleaseForPrintSubHandler(GeminiApplication application) {
    this.application = application;
    this.log = application.getLog("hCProd");
  }
  
  public String getDescription() { return "Sub Report"; }
  
  protected static StyleSheet fillMCACustomReleaseForPrint(Context context, String reportPath) {
    Color SHADED_AREA_COLOR = Color.lightGray;
    int COL_LINE_STYLE = 4097;
    int HEADER_FONT_SIZE = 12;
    int NUM_COLUMNS = 8;
    double ldLineVal = 0.3D;
    StyleSheet[] sheets = null;
    ComponentLog log = context.getApplication().getLog("hCProd");
    int separatorLineStyle = 266240;
    Color separatorLineColor = Color.black;
    int tableHeaderLineStyle = 266240;
    Color tableHeaderLineColor = Color.black;
    int tableRowLineStyle = 4097;
    Color tableRowLineColor = new Color(208, 206, 206, 0);
    try {
      HttpServletResponse sresponse = context.getResponse();
      context.putDelivery("status", new String("start_gathering"));
      context.includeJSP("status.jsp", "hiddenFrame");
      sresponse.setContentType("text/plain");
      sresponse.flushBuffer();
    } catch (Exception exception) {}
    Vector selections = MilestoneHelper.getSelectionsForReport(context);
    Hashtable hTable = new Hashtable();
    Vector vSelections = new Vector();
    Selection selFirst = (Selection)selections.elementAt(0);
    String artistString = selFirst.getArtist();
    hTable = MilestoneHelper.groupSelectionsByArtistAndMonth(selections);
    try {
      HttpServletResponse sresponse = context.getResponse();
      context.putDelivery("status", new String("start_report"));
      context.putDelivery("percent", new String("10"));
      context.includeJSP("status.jsp", "hiddenFrame");
      sresponse.setContentType("text/plain");
      sresponse.flushBuffer();
    } catch (Exception exception) {}
    int numSelections = selections.size();
    MilestoneHelper.setSelectionSorting(selections, 12);
    Collections.sort(selections);
    MilestoneHelper.setSelectionSorting(selections, 13);
    Collections.sort(selections);
    MilestoneHelper.setSelectionSorting(selections, 4);
    Collections.sort(selections);
    MilestoneHelper.setSelectionSorting(selections, 3);
    Collections.sort(selections);
    if (numSelections == 0)
      return null; 
    try {
      DefaultTableLens table_contents = null;
      DefaultTableLens rowCountTable = null;
      DefaultTableLens columnHeaderTable = null;
      DefaultTableLens subTable = null;
      DefaultTableLens headerTableLens = null;
      rowCountTable = new DefaultTableLens(2, 10000);
      int i = 0;
      String artistName = new String();
      Vector artistsVector = new Vector();
      Enumeration hTableEnum = hTable.keys();
      int count = 0;
      Vector tempArtists = new Vector();
      while (hTableEnum.hasMoreElements())
        tempArtists.addElement(hTableEnum.nextElement()); 
      int arraySize = tempArtists.size();
      sheets = new StyleSheet[arraySize];
      Collections.sort(tempArtists);
      for (int artistsCounter = 0; artistsCounter < tempArtists.size(); artistsCounter++) {
        String currentArtist = (String)tempArtists.get(artistsCounter);
        InputStream input = new FileInputStream(String.valueOf(reportPath) + "\\mca_release_schedule.xml");
        XStyleSheet report = (XStyleSheet)Builder.getBuilder(1, input).read(null);
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
        SeparatorElement topSeparator = (SeparatorElement)report.getElement("separator_top");
        SeparatorElement bottomHeaderSeparator = (SeparatorElement)report.getElement("separator_bottom_header");
        SeparatorElement bottomSeparator = (SeparatorElement)report.getElement("separator_bottom");
        if (topSeparator != null) {
          topSeparator.setStyle(separatorLineStyle);
          topSeparator.setForeground(separatorLineColor);
        } 
        if (bottomHeaderSeparator != null) {
          bottomHeaderSeparator.setStyle(separatorLineStyle);
          bottomHeaderSeparator.setForeground(separatorLineColor);
        } 
        if (bottomSeparator != null) {
          bottomSeparator.setStyle(separatorLineStyle);
          bottomSeparator.setForeground(separatorLineColor);
        } 
        Form reportForm = (Form)context.getSessionValue("reportForm");
        Calendar beginDate = (reportForm.getStringValue("beginDate") != null && 
          reportForm.getStringValue("beginDate").length() > 0) ? 
          MilestoneHelper.getDate(reportForm.getStringValue("beginDate")) : null;
        Calendar endDate = (reportForm.getStringValue("endDate") != null && 
          reportForm.getStringValue("endDate").length() > 0) ? 
          MilestoneHelper.getDate(reportForm.getStringValue("endDate")) : null;
        report.setElement("crs_startdate", MilestoneHelper.getFormatedDate(beginDate));
        report.setElement("crs_enddate", MilestoneHelper.getFormatedDate(endDate));
        SimpleDateFormat formatter = new SimpleDateFormat("MMMM d, yyyy");
        String todayLong = formatter.format(new Date());
        report.setElement("crs_bottomdate", todayLong);
        String strUmlKey = (reportForm.getStringValue("umlKeyTask") != null) ? reportForm.getStringValue("umlKeyTask") : "";
        Hashtable monthTable = (Hashtable)hTable.get(currentArtist);
        Vector monthVector = new Vector();
        Enumeration ee = monthTable.keys();
        while (ee.hasMoreElements())
          monthVector.add((String)ee.nextElement()); 
        Collections.sort(monthVector, new MonthYearComparator());
        for (int monthCounter = 0; monthCounter < monthVector.size(); monthCounter++) {
          String monthString = (String)monthVector.get(monthCounter);
          table_contents = new DefaultTableLens(1, 8);
          table_contents.setColAlignment(0, 2);
          table_contents.setColAlignment(1, 1);
          table_contents.setColAlignment(2, 1);
          table_contents.setColAlignment(3, 2);
          table_contents.setColAlignment(4, 2);
          table_contents.setColAlignment(5, 2);
          table_contents.setColAlignment(6, 2);
          table_contents.setColAlignment(7, 12);
          table_contents.setColBorderColor(Color.black);
          table_contents.setRowBorderColor(Color.black);
          table_contents.setRowBorder(0);
          table_contents.setColBorder(0);
          table_contents.setRowBorder(1, 4097);
          table_contents.setColWidth(0, 55);
          table_contents.setColWidth(1, 128);
          table_contents.setColWidth(2, 75);
          table_contents.setColWidth(3, 65);
          table_contents.setColWidth(4, 70);
          table_contents.setColWidth(5, 45);
          table_contents.setColWidth(6, 45);
          table_contents.setColWidth(7, 56);
          table_contents.setLineWrap(0, 0, false);
          table_contents.setLineWrap(0, 1, false);
          table_contents.setObject(0, 0, "Product\nCategory ");
          table_contents.setFont(0, 0, new Font("Arial", 3, 10));
          table_contents.setObject(0, 1, "Title");
          table_contents.setFont(0, 1, new Font("Arial", 3, 10));
          table_contents.setObject(0, 2, "Configuration");
          table_contents.setFont(0, 2, new Font("Arial", 3, 10));
          table_contents.setObject(0, 3, "Local Product #");
          table_contents.setFont(0, 3, new Font("Arial", 3, 10));
          table_contents.setObject(0, 4, "UPC");
          table_contents.setFont(0, 4, new Font("Arial", 3, 10));
          table_contents.setObject(0, 5, "Price");
          table_contents.setFont(0, 5, new Font("Arial", 3, 10));
          table_contents.setObject(0, 6, "Street\nDate");
          table_contents.setFont(0, 6, new Font("Arial", 3, 10));
          table_contents.setObject(0, 7, "Release\nWeek");
          table_contents.setFont(0, 7, new Font("Arial", 3, 10));
          table_contents.setRowBorder(-1, 0);
          hbandType.addTable(table_contents, new Rectangle(0, 0, 800, 30));
          int nextRow = 0;
          headerTableLens = new DefaultTableLens(1, 8);
          headerTableLens.setColWidth(0, 5);
          headerTableLens.setSpan(0, 1, new Dimension(2, 1));
          headerTableLens.setColWidth(1, 5);
          headerTableLens.setColWidth(2, 5);
          headerTableLens.setColAlignment(0, 1);
          headerTableLens.setColAlignment(3, 4);
          headerTableLens.setColAlignment(4, 1);
          headerTableLens.setColBorder(-1, 0);
          headerTableLens.setColBorder(0, 0);
          headerTableLens.setColBorder(1, 0);
          headerTableLens.setColBorder(2, 0);
          headerTableLens.setColBorder(3, 0);
          headerTableLens.setColBorder(4, 0);
          headerTableLens.setColBorder(5, 0);
          headerTableLens.setColBorder(6, 0);
          headerTableLens.setColBorder(7, 0);
          headerTableLens.setRowBorder(0, 0);
          headerTableLens.setRowBorder(-1, 0);
          headerTableLens.setRowBackground(0, Color.lightGray);
          headerTableLens.setFont(0, 0, new Font("Arial", 1, 10));
          headerTableLens.setFont(0, 1, new Font("Arial", 0, 10));
          headerTableLens.setFont(0, 3, new Font("Arial", 1, 10));
          headerTableLens.setFont(0, 4, new Font("Arial", 0, 10));
          Calendar cal = MilestoneHelper.getMYDate(monthString);
          String myMonth = MilestoneHelper.getCustomFormatedDate(cal, "MMM");
          headerTableLens.setObject(0, 0, myMonth);
          hbandType.addTable(headerTableLens, new Rectangle(0, 30, 800, 30));
          hbandType.setHeight(1.0F);
          Vector vSel = (Vector)monthTable.get(monthString);
          for (int vIndex = 0; vIndex < vSel.size(); vIndex++) {
            Selection sel = (Selection)vSel.elementAt(vIndex);
            if (vIndex == 0) {
              String selProject = (sel.getProjectID() != null) ? sel.getProjectID() : "";
              String selLabel = sel.getImprint();
              String selArtist = (sel.getArtist() != null) ? sel.getArtist() : "";
              report.setElement("artist", selArtist);
              report.setElement("project", selProject);
              report.setElement("label", selLabel);
            } 
            String selPD = sel.getPressAndDistribution() ? "Yes" : "";
            String selDivision = (sel.getDivision() != null && sel.getDivision().getName() != null) ? 
              sel.getDivision().getName() : "";
            String selId = "";
            if (SelectionManager.getLookupObjectValue(sel.getPrefixID()).equals("")) {
              selId = sel.getSelectionNo();
            } else {
              selId = String.valueOf(SelectionManager.getLookupObjectValue(sel.getPrefixID())) + sel.getSelectionNo();
            } 
            String selTitle = (sel.getTitle() != null) ? sel.getTitle() : "";
            String selPrice = "$" + String.valueOf(sel.getPrice());
            String selProductCategory = (sel.getProductCategory() != null) ? sel.getProductCategory().getName() : "";
            DatePeriod dp = MilestoneHelper.getReleaseWeek(sel);
            String selReleaseWeek = (dp != null) ? dp.getName() : "";
            String selConfig = (sel.getSelectionSubConfig() != null && sel.getSelectionSubConfig().getSelectionSubConfigurationAbbreviation() != null) ? 
              sel.getSelectionSubConfig().getSelectionSubConfigurationName() : "";
            String selUpc = (sel.getUpc() != null) ? sel.getUpc() : "";
            selUpc = MilestoneHelper_2.getRMSReportFormat(selUpc, "UPC", sel.getIsDigital());
            String selStreetDate = "";
            selStreetDate = MilestoneHelper.getFormatedDate(sel.getStreetDate());
            String statusString = "";
            SelectionStatus status = sel.getSelectionStatus();
            if (status != null)
              statusString = (status.getName() == null) ? "" : status.getName(); 
            if (!statusString.equalsIgnoreCase("ACTIVE"))
              if (!statusString.equalsIgnoreCase("In The Works")) {
                selStreetDate = String.valueOf(statusString) + " " + selStreetDate;
              } else {
                selStreetDate = "ITW " + selStreetDate;
              }  
            nextRow = 0;
            subTable = new DefaultTableLens(1, 8);
            subTable.setColAlignment(0, 1);
            subTable.setColAlignment(1, 1);
            subTable.setColAlignment(2, 1);
            subTable.setColAlignment(3, 4);
            subTable.setColAlignment(4, 4);
            subTable.setColAlignment(5, 4);
            subTable.setColAlignment(6, 4);
            subTable.setColAlignment(7, 4);
            subTable.setColWidth(0, 95);
            subTable.setColWidth(1, 200);
            subTable.setColWidth(2, 115);
            subTable.setColWidth(3, 100);
            subTable.setColWidth(4, 125);
            subTable.setColWidth(5, 65);
            subTable.setColWidth(6, 80);
            subTable.setColWidth(7, 90);
            subTable.setColBorderColor(Color.black);
            subTable.setRowBorderColor(Color.black);
            subTable.setRowBorder(0);
            subTable.setColBorder(0);
            subTable.setRowBorder(2, 4097);
            subTable.setObject(nextRow, 0, selProductCategory);
            subTable.setObject(nextRow, 1, selTitle);
            subTable.setObject(nextRow, 2, selConfig);
            subTable.setObject(nextRow, 3, selId);
            subTable.setObject(nextRow, 4, selUpc);
            subTable.setObject(nextRow, 5, selPrice);
            subTable.setObject(nextRow, 6, selStreetDate);
            subTable.setObject(nextRow, 7, selReleaseWeek);
            subTable.setRowFont(nextRow, new Font("Arial", 0, 9));
            subTable.setRowBorder(nextRow, tableRowLineStyle);
            subTable.setRowBorderColor(nextRow, tableRowLineColor);
            body = new SectionBand(report);
            double lfLineCount = 1.5D;
            body.setHeight(1.5F);
            body.addTable(subTable, new Rectangle(800, 800));
            body.setBottomBorder(0);
            body.setTopBorder(0);
            body.setShrinkToFit(true);
            body.setVisible(true);
            group = new DefaultSectionLens(null, group, body);
          } 
        } 
        group = new DefaultSectionLens(hbandType, group, null);
        report.addSection(group, rowCountTable);
        group = null;
        sheets[i] = report;
        i++;
      } 
    } catch (Exception e) {
      System.out.println(">>>>>>>>ReportHandler.fillMCACustomReleaseForPrint(): exception: " + e);
    } 
    return new CompositeSheet(sheets);
  }
}


/* Location:              D:\Documents\NetBeansProjects\milestone2 Local.war!\WEB-INF\classes\co\\universal\milestone\MCACustomReleaseForPrintSubHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */