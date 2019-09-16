package WEB-INF.classes.com.universal.milestone;

import com.techempower.ComponentLog;
import com.techempower.gemini.Context;
import com.techempower.gemini.GeminiApplication;
import com.universal.milestone.Cache;
import com.universal.milestone.Form;
import com.universal.milestone.LookupObject;
import com.universal.milestone.MilestoneHelper;
import com.universal.milestone.MilestoneHelper_2;
import com.universal.milestone.SecureHandler;
import com.universal.milestone.Selection;
import com.universal.milestone.SelectionManager;
import com.universal.milestone.StatusJSPupdate;
import com.universal.milestone.eInitiativeRelScheduleForPrintSubHandler;
import inetsoft.report.SectionBand;
import inetsoft.report.SeparatorElement;
import inetsoft.report.XStyleSheet;
import inetsoft.report.lens.DefaultSectionLens;
import inetsoft.report.lens.DefaultTableLens;
import java.awt.Color;
import java.awt.Font;
import java.awt.Insets;
import java.awt.Rectangle;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;

public class eInitiativeRelScheduleForPrintSubHandler extends SecureHandler {
  public static final String COMPONENT_CODE = "heInit";
  
  public GeminiApplication application;
  
  public ComponentLog log;
  
  public eInitiativeRelScheduleForPrintSubHandler(GeminiApplication application) {
    this.application = application;
    this.log = application.getLog("heInit");
  }
  
  public String getDescription() { return "Sub Report"; }
  
  protected static void filleInitiativeRelScheduleForPrint(XStyleSheet report, Context context, GeminiApplication application) {
    Color SHADED_AREA_COLOR = Color.lightGray;
    int COL_LINE_STYLE = 4097;
    int HEADER_FONT_SIZE = 12;
    double ldLineVal = 0.3D;
    ComponentLog log = application.getLog("heInit");
    SectionBand hbandType = new SectionBand(report);
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
    try {
      DefaultTableLens table_contents = null;
      DefaultTableLens rowCountTable = null;
      DefaultTableLens columnHeaderTable = null;
      DefaultTableLens subTable = null;
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
      int numSelections = selections.size();
      int numRows = numSelections;
      int numColumns = 11;
      nextRow = 0;
      hbandType = new SectionBand(report);
      hbandType.setHeight(0.95F);
      hbandType.setShrinkToFit(false);
      hbandType.setVisible(true);
      table_contents = new DefaultTableLens(1, numColumns);
      table_contents.setRowBorder(0);
      table_contents.setColWidth(0, 40);
      table_contents.setColWidth(1, 40);
      table_contents.setColWidth(2, 80);
      table_contents.setColWidth(3, 100);
      table_contents.setColWidth(4, 60);
      table_contents.setColWidth(5, 40);
      table_contents.setColWidth(6, 50);
      table_contents.setColWidth(7, 60);
      table_contents.setColWidth(8, 40);
      table_contents.setColWidth(9, 60);
      table_contents.setColWidth(10, 100);
      table_contents.setColBorder(0);
      table_contents.setRowBorder(0);
      table_contents.setColAlignment(0, 2);
      table_contents.setColAlignment(1, 2);
      table_contents.setRowAlignment(nextRow, 8);
      table_contents.setRowHeight(nextRow, 55);
      table_contents.setObject(nextRow, 0, "ORG\nRelease\nDate");
      table_contents.setAlignment(nextRow, 0, 34);
      table_contents.setColFont(0, new Font("Arial", 3, 8));
      table_contents.setObject(nextRow, 1, "PP\nStreet\nDate");
      table_contents.setColFont(1, new Font("Arial", 3, 8));
      table_contents.setAlignment(nextRow, 1, 34);
      table_contents.setObject(nextRow, 2, "Artist");
      table_contents.setAlignment(nextRow, 2, 32);
      table_contents.setObject(nextRow, 3, "Title");
      table_contents.setAlignment(nextRow, 3, 32);
      table_contents.setObject(nextRow, 4, "UPC");
      table_contents.setAlignment(nextRow, 4, 32);
      table_contents.setObject(nextRow, 5, "Vol");
      table_contents.setAlignment(nextRow, 5, 32);
      table_contents.setObject(nextRow, 6, "Label");
      table_contents.setAlignment(nextRow, 6, 32);
      table_contents.setObject(nextRow, 7, "Product\nCategory");
      table_contents.setAlignment(nextRow, 7, 33);
      table_contents.setObject(nextRow, 8, "Parental\nAdv");
      table_contents.setAlignment(nextRow, 8, 34);
      table_contents.setObject(nextRow, 9, "Territory");
      table_contents.setAlignment(nextRow, 9, 32);
      table_contents.setObject(nextRow, 10, "Comments");
      table_contents.setAlignment(nextRow, 10, 32);
      table_contents.setRowFont(nextRow, new Font("Arial", 3, 10));
      hbandType.addTable(table_contents, new Rectangle(0, 0, 800, 39));
      hbandType.setBottomBorder(0);
      table_contents = new DefaultTableLens(1, numColumns);
      nextRow = 0;
      table_contents.setRowBackground(nextRow, Color.white);
      table_contents.setRowBorderColor(nextRow - 1, Color.black);
      table_contents.setRowBorder(nextRow, 0);
      table_contents.setColBorder(0);
      hbandType.addTable(table_contents, new Rectangle(0, 45, 800, 3));
      for (int n = 0; n < selections.size(); n++) {
        nextRow = 0;
        footer.setVisible(true);
        footer.setHeight(0.1F);
        footer.setShrinkToFit(false);
        footer.setBottomBorder(0);
        group = new DefaultSectionLens(null, group, spacer);
        aStatusUpdate.updateStatus(selections.size(), n, "start_report", 0);
        Selection sel = (Selection)selections.elementAt(n);
        nextRow = 0;
        subTable = new DefaultTableLens(1, numColumns);
        subTable.setColWidth(0, 40);
        subTable.setColWidth(1, 40);
        subTable.setColWidth(2, 80);
        subTable.setColWidth(3, 100);
        subTable.setColWidth(4, 60);
        subTable.setColWidth(5, 40);
        subTable.setColWidth(6, 50);
        subTable.setColWidth(7, 60);
        subTable.setColWidth(8, 40);
        subTable.setColWidth(9, 60);
        subTable.setColWidth(10, 100);
        String labelName = "";
        if (sel.getLabel() != null)
          labelName = sel.getLabel().getName(); 
        subTable.setRowInsets(nextRow, new Insets(0, 0, 5, 0));
        String impactDate = "  " + MilestoneHelper.getFormatedDate(sel.getImpactDate());
        String streetDate = "  " + MilestoneHelper.getFormatedDate(sel.getStreetDate());
        String status = (sel.getSelectionStatus() != null && sel.getSelectionStatus().getName() != null) ? 
          sel.getSelectionStatus().getName() : "";
        if (status.equalsIgnoreCase("TBS")) {
          streetDate = "TBS " + streetDate;
        } else if (status.equalsIgnoreCase("In The Works")) {
          streetDate = "ITW " + streetDate;
        } 
        subTable.setObject(nextRow, 0, impactDate);
        subTable.setAlignment(nextRow, 0, 12);
        subTable.setObject(nextRow, 1, streetDate);
        subTable.setAlignment(nextRow, 1, 12);
        subTable.setObject(nextRow, 2, sel.getArtist().trim());
        subTable.setAlignment(nextRow, 2, 9);
        subTable.setObject(nextRow, 3, sel.getTitle());
        subTable.setAlignment(nextRow, 3, 9);
        String upc = sel.getUpc();
        upc = MilestoneHelper_2.getRMSReportFormat(upc, "UPC", sel.getIsDigital());
        subTable.setObject(nextRow, 4, upc);
        subTable.setAlignment(nextRow, 4, 9);
        subTable.setObject(nextRow, 5, sel.getSelectionPackaging());
        subTable.setAlignment(nextRow, 5, 9);
        subTable.setObject(nextRow, 6, sel.getOtherContact());
        subTable.setAlignment(nextRow, 6, 9);
        LookupObject prodCategory = SelectionManager.getLookupObject(sel.getProductCategory().getAbbreviation(), 
            Cache.getProductCategories());
        String prodCategoryName = "";
        if (prodCategory != null)
          prodCategoryName = prodCategory.getName(); 
        subTable.setObject(nextRow, 7, prodCategoryName);
        subTable.setAlignment(nextRow, 7, 9);
        String parAdv = "";
        if (sel.getParentalGuidance())
          parAdv = "Yes"; 
        subTable.setObject(nextRow, 8, parAdv);
        subTable.setAlignment(nextRow, 8, 10);
        subTable.setObject(nextRow, 9, sel.getSelectionTerritory());
        subTable.setAlignment(nextRow, 9, 9);
        subTable.setObject(nextRow, 10, sel.getSelectionComments());
        subTable.setAlignment(nextRow, 10, 9);
        subTable.setColBorder(0);
        subTable.setRowBorderColor(nextRow, Color.white);
        subTable.setRowBorder(nextRow - 1, 0);
        subTable.setRowBorder(nextRow, 4097);
        subTable.setRowBorderColor(nextRow, Color.lightGray);
        subTable.setRowFont(nextRow, new Font("Arial", 0, 8));
        body = new SectionBand(report);
        double lfLineCount = 1.0D;
        body.setHeight(1.0F);
        if (sel.getSelectionComments().length() > 20) {
          if (lfLineCount < (sel.getSelectionComments().length() / 24) * 0.3D)
            lfLineCount = (sel.getSelectionComments().length() / 24) * 0.3D; 
          body.setHeight((float)lfLineCount);
        } 
        body.addTable(subTable, new Rectangle(800, 800));
        body.setBottomBorder(0);
        body.setTopBorder(0);
        body.setShrinkToFit(true);
        body.setVisible(true);
        group = new DefaultSectionLens(null, group, body);
      } 
      group = new DefaultSectionLens(hbandType, group, null);
      report.addSection(group, rowCountTable);
      group = null;
    } catch (Exception e) {
      System.out.println(">>>>>>>>ReportHandler.filleInitiativeRelScheduleForPrint(): exception: " + e);
      System.out.println("<<< error " + e.getMessage());
      e.printStackTrace();
    } 
  }
}


/* Location:              D:\Documents\NetBeansProjects\milestone2 Local.war!\WEB-INF\classes\co\\universal\milestone\eInitiativeRelScheduleForPrintSubHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */