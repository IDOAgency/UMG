package WEB-INF.classes.com.universal.milestone;

import com.techempower.ComponentLog;
import com.techempower.gemini.Context;
import com.techempower.gemini.GeminiApplication;
import com.universal.milestone.Cache;
import com.universal.milestone.EntCommRelScheduleForPrintSubHandler;
import com.universal.milestone.Form;
import com.universal.milestone.LookupObject;
import com.universal.milestone.MilestoneHelper;
import com.universal.milestone.MilestoneHelper_2;
import com.universal.milestone.SecureHandler;
import com.universal.milestone.Selection;
import com.universal.milestone.SelectionManager;
import com.universal.milestone.StatusJSPupdate;
import inetsoft.report.SectionBand;
import inetsoft.report.SeparatorElement;
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
import java.util.Vector;
import javax.servlet.http.HttpServletResponse;

public class EntCommRelScheduleForPrintSubHandler extends SecureHandler {
  public static final String COMPONENT_CODE = "hCRel";
  
  public GeminiApplication application;
  
  public ComponentLog log;
  
  public EntCommRelScheduleForPrintSubHandler(GeminiApplication application) {
    this.application = application;
    this.log = application.getLog("hCRel");
  }
  
  public String getDescription() { return "Sub Report"; }
  
  protected static void fillEntCommRelScheduleForPrint(XStyleSheet report, Context context) {
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
    int separatorLineStyle = 266240;
    Color separatorLineColor = Color.black;
    int monthLineStyle = 4097;
    Color monthLineColor = Color.black;
    StatusJSPupdate aStatusUpdate = new StatusJSPupdate(context);
    aStatusUpdate.setInternalCounter(true);
    aStatusUpdate.updateStatus(0, 0, "start_gathering", 0);
    Vector selections = MilestoneHelper.getSelectionsForReport(context);
    aStatusUpdate.updateStatus(0, 0, "start_report", 10);
    MilestoneHelper.setSelectionSorting(selections, 5);
    Collections.sort(selections);
    MilestoneHelper.setSelectionSorting(selections, 4);
    Collections.sort(selections);
    MilestoneHelper.setSelectionSorting(selections, 3);
    Collections.sort(selections);
    MilestoneHelper.setSelectionSorting(selections, 2);
    Collections.sort(selections);
    MilestoneHelper.setSelectionSorting(selections, 1);
    Collections.sort(selections);
    MilestoneHelper.setSelectionSorting(selections, 16);
    Collections.sort(selections);
    try {
      DefaultTableLens table_contents = null;
      DefaultTableLens rowCountTable = null;
      DefaultTableLens columnHeaderTable = null;
      DefaultTableLens subTable = null;
      DefaultTableLens monthTableLens = null;
      DefaultTableLens dateTableLens = null;
      rowCountTable = new DefaultTableLens(2, 10000);
      int nextRow = 0;
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
      Vector groupedByMonth = MilestoneHelper.groupSelectionsByReleaseMonth(selections);
      int numExtraRows = groupedByMonth.size() * 4;
      int numSelections = selections.size();
      int numRows = numSelections + numExtraRows;
      int numColumns = 10;
      nextRow = 0;
      hbandType = new SectionBand(report);
      hbandType.setHeight(0.5F);
      hbandType.setShrinkToFit(false);
      hbandType.setVisible(true);
      table_contents = new DefaultTableLens(1, numColumns);
      table_contents.setRowBorder(0);
      table_contents.setColWidth(0, 20);
      table_contents.setColWidth(1, 41);
      table_contents.setColWidth(2, 100);
      table_contents.setColWidth(3, 100);
      table_contents.setColWidth(4, 60);
      table_contents.setColWidth(5, 30);
      table_contents.setColWidth(6, 60);
      table_contents.setColWidth(7, 50);
      table_contents.setColWidth(8, 50);
      table_contents.setColWidth(9, 50);
      table_contents.setColBorder(0);
      table_contents.setRowBorder(0);
      table_contents.setColAlignment(0, 2);
      table_contents.setColAlignment(1, 2);
      table_contents.setColAlignment(5, 2);
      table_contents.setRowAlignment(nextRow, 32);
      table_contents.setRowHeight(nextRow, 55);
      table_contents.setObject(nextRow, 0, "Int'l\nDate");
      table_contents.setAlignment(nextRow, 0, 2);
      table_contents.setObject(nextRow, 1, "Street\nDate");
      table_contents.setAlignment(nextRow, 1, 2);
      table_contents.setObject(nextRow, 2, "Artist");
      table_contents.setObject(nextRow, 3, "Title");
      table_contents.setObject(nextRow, 4, "Product\nCategory");
      table_contents.setAlignment(nextRow, 5, 36);
      table_contents.setObject(nextRow, 5, "Price");
      table_contents.setObject(nextRow, 6, "Label");
      table_contents.setObject(nextRow, 7, "Local Product #");
      table_contents.setObject(nextRow, 8, "UPC");
      table_contents.setObject(nextRow, 9, "Project #");
      table_contents.setRowFont(nextRow, new Font("Arial", 3, 10));
      hbandType.addTable(table_contents, new Rectangle(0, 0, 800, 30));
      table_contents = new DefaultTableLens(1, numColumns);
      nextRow = 0;
      table_contents.setRowBackground(nextRow, Color.white);
      table_contents.setRowBorderColor(nextRow - 1, Color.black);
      table_contents.setRowBorder(nextRow, 0);
      table_contents.setColBorder(0);
      table_contents.setRowHeight(nextRow, 1);
      hbandType.addTable(table_contents, new Rectangle(0, 32, 800, 5));
      int statusBarCounter = 0;
      for (int n = 0; n < groupedByMonth.size(); n++) {
        Vector selectionsInMonth = (Vector)groupedByMonth.elementAt(n);
        statusBarCounter += selectionsInMonth.size();
      } 
      for (int n = 0; n < groupedByMonth.size(); n++) {
        Vector selectionsInMonth = (Vector)groupedByMonth.elementAt(n);
        if (selectionsInMonth == null)
          selectionsInMonth = new Vector(); 
        monthTableLens = new DefaultTableLens(2, numColumns);
        hbandCategory = new SectionBand(report);
        hbandCategory.setHeight(0.23F);
        hbandCategory.setShrinkToFit(false);
        hbandCategory.setVisible(true);
        hbandCategory.setBottomBorder(0);
        hbandCategory.setLeftBorder(0);
        hbandCategory.setRightBorder(0);
        hbandCategory.setTopBorder(0);
        nextRow = 0;
        monthTableLens.setRowBorderColor(nextRow - 1, Color.white);
        monthTableLens.setRowBorderColor(nextRow, Color.white);
        monthTableLens.setColWidth(0, 20);
        monthTableLens.setColWidth(1, 41);
        monthTableLens.setColWidth(2, 100);
        monthTableLens.setColWidth(3, 100);
        monthTableLens.setColWidth(4, 60);
        monthTableLens.setColWidth(5, 30);
        monthTableLens.setColWidth(6, 60);
        monthTableLens.setColBorder(0);
        monthTableLens.setColAlignment(0, 2);
        monthTableLens.setColAlignment(1, 2);
        monthTableLens.setColAlignment(5, 2);
        String monthName = MilestoneHelper.getMonthNameForUmeReport(selectionsInMonth);
        monthTableLens.setRowAlignment(nextRow, 1);
        monthTableLens.setSpan(nextRow, 0, new Dimension(numColumns, 1));
        monthTableLens.setObject(nextRow, 0, monthName);
        monthTableLens.setRowFont(nextRow, new Font("Arial", 1, 10));
        monthTableLens.setRowBackground(nextRow, Color.lightGray);
        hbandCategory.addTable(monthTableLens, new Rectangle(0, 0, 800, 20));
        footer.setVisible(true);
        footer.setHeight(0.1F);
        footer.setShrinkToFit(false);
        footer.setBottomBorder(0);
        group = new DefaultSectionLens(null, group, spacer);
        group = new DefaultSectionLens(null, group, hbandCategory);
        group = new DefaultSectionLens(null, group, spacer);
        MilestoneHelper.setSelectionSorting(selectionsInMonth, 5);
        Collections.sort(selectionsInMonth);
        MilestoneHelper.setSelectionSorting(selectionsInMonth, 4);
        Collections.sort(selectionsInMonth);
        MilestoneHelper.setSelectionSorting(selectionsInMonth, 3);
        Collections.sort(selectionsInMonth);
        MilestoneHelper.setSelectionSorting(selectionsInMonth, 2);
        Collections.sort(selectionsInMonth);
        MilestoneHelper.setSelectionSorting(selectionsInMonth, 1);
        Collections.sort(selectionsInMonth);
        MilestoneHelper.setSelectionSorting(selections, 16);
        Collections.sort(selections);
        for (int i = 0; i < selectionsInMonth.size(); i++) {
          aStatusUpdate.updateStatus(statusBarCounter, i, "start_report", 0);
          Selection sel = (Selection)selectionsInMonth.elementAt(i);
          nextRow = 0;
          subTable = new DefaultTableLens(1, numColumns);
          subTable.setColWidth(0, 20);
          subTable.setColWidth(1, 41);
          subTable.setColWidth(2, 100);
          subTable.setColWidth(3, 100);
          subTable.setColWidth(4, 60);
          subTable.setColWidth(5, 30);
          subTable.setColWidth(6, 60);
          subTable.setColWidth(7, 50);
          subTable.setColWidth(8, 50);
          subTable.setColWidth(9, 50);
          String labelName = sel.getImprint();
          String price = "0.00";
          if (sel.getPriceCode() != null && 
            sel.getPriceCode().getTotalCost() > 0.0F)
            price = MilestoneHelper.formatDollarPrice(sel.getPriceCode().getTotalCost()); 
          LookupObject prodCategory = SelectionManager.getLookupObject(sel.getProductCategory().getAbbreviation(), 
              Cache.getProductCategories());
          String prodCategoryName = "";
          if (prodCategory != null)
            prodCategoryName = prodCategory.getName(); 
          subTable.setRowInsets(nextRow, new Insets(0, 0, 5, 0));
          String intlDate = "  " + MilestoneHelper.getFormatedDate(sel.getInternationalDate());
          if (intlDate.length() < 8) {
            intlDate = String.valueOf(intlDate) + " ";
            if (intlDate.length() < 7)
              intlDate = String.valueOf(intlDate) + " "; 
          } 
          String streetDate = "  " + MilestoneHelper.getFormatedDate(sel.getStreetDate());
          String status = (sel.getSelectionStatus() != null && sel.getSelectionStatus().getName() != null) ? 
            sel.getSelectionStatus().getName() : "";
          if (status.equalsIgnoreCase("TBS")) {
            streetDate = "TBS " + streetDate;
          } else if (status.equalsIgnoreCase("In The Works")) {
            streetDate = "ITW " + streetDate;
          } 
          if (streetDate.length() < 8) {
            streetDate = String.valueOf(streetDate) + " ";
            if (streetDate.length() < 7)
              streetDate = String.valueOf(streetDate) + " "; 
          } 
          subTable.setObject(nextRow, 0, intlDate);
          subTable.setAlignment(nextRow, 0, 12);
          subTable.setObject(nextRow, 1, streetDate);
          subTable.setAlignment(nextRow, 1, 12);
          subTable.setObject(nextRow, 2, sel.getArtist().trim());
          subTable.setAlignment(nextRow, 2, 9);
          subTable.setObject(nextRow, 3, sel.getTitle());
          subTable.setAlignment(nextRow, 3, 9);
          subTable.setObject(nextRow, 4, prodCategoryName);
          subTable.setAlignment(nextRow, 4, 9);
          subTable.setObject(nextRow, 5, "$" + price);
          subTable.setAlignment(nextRow, 5, 12);
          subTable.setObject(nextRow, 6, labelName);
          subTable.setAlignment(nextRow, 6, 9);
          String selId = "";
          if (SelectionManager.getLookupObjectValue(sel.getPrefixID()).equals("")) {
            selId = sel.getSelectionNo();
          } else {
            selId = String.valueOf(SelectionManager.getLookupObjectValue(sel.getPrefixID())) + sel.getSelectionNo();
          } 
          subTable.setObject(nextRow, 7, selId);
          subTable.setAlignment(nextRow, 7, 9);
          subTable.setObject(nextRow, 9, sel.getProjectID());
          subTable.setAlignment(nextRow, 9, 9);
          String upc = sel.getUpc();
          upc = MilestoneHelper_2.getRMSReportFormat(upc, "UPC", sel.getIsDigital());
          subTable.setObject(nextRow, 8, upc);
          subTable.setAlignment(nextRow, 8, 9);
          subTable.setColBorder(0);
          subTable.setRowBorderColor(nextRow, Color.white);
          subTable.setRowBorder(nextRow - 1, 0);
          subTable.setRowBorder(nextRow, 4097);
          subTable.setRowBorderColor(nextRow, Color.lightGray);
          subTable.setRowFont(nextRow, new Font("Arial", 0, 8));
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
      } 
      group = new DefaultSectionLens(hbandType, group, null);
      report.addSection(group, rowCountTable);
      group = null;
      HttpServletResponse sresponse = context.getResponse();
      context.putDelivery("percent", new String("100"));
      context.includeJSP("status.jsp", "hiddenFrame");
      sresponse.setContentType("text/plain");
      sresponse.flushBuffer();
    } catch (Exception e) {
      System.out.println(">>>>>>>>ReportHandler.fillEntCommRelScheduleForPrint(): exception: " + e);
    } 
  }
}


/* Location:              D:\Documents\NetBeansProjects\milestone2 Local.war!\WEB-INF\classes\co\\universal\milestone\EntCommRelScheduleForPrintSubHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */