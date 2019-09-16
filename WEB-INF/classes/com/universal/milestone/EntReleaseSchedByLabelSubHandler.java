package WEB-INF.classes.com.universal.milestone;

import com.techempower.ComponentLog;
import com.techempower.gemini.Context;
import com.techempower.gemini.GeminiApplication;
import com.universal.milestone.EntReleaseSchedByLabelSubHandler;
import com.universal.milestone.Family;
import com.universal.milestone.Form;
import com.universal.milestone.MilestoneHelper;
import com.universal.milestone.MilestoneHelper_2;
import com.universal.milestone.SecureHandler;
import com.universal.milestone.Selection;
import com.universal.milestone.SelectionConfiguration;
import com.universal.milestone.SelectionManager;
import com.universal.milestone.SelectionSubConfiguration;
import com.universal.milestone.StringComparator;
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

public class EntReleaseSchedByLabelSubHandler extends SecureHandler {
  public static final String COMPONENT_CODE = "hNsl";
  
  public GeminiApplication application;
  
  public ComponentLog log;
  
  public EntReleaseSchedByLabelSubHandler(GeminiApplication application) {
    this.application = application;
    this.log = application.getLog("hNsl");
  }
  
  public String getDescription() { return "Sub Report"; }
  
  protected static void fillEntReleaseSchedByLabelForPrint(XStyleSheet report, Context context) {
    Color SHADED_AREA_COLOR = Color.lightGray;
    int COL_LINE_STYLE = 4097;
    int HEADER_FONT_SIZE = 12;
    int NUM_COLUMNS = 7;
    double ldLineVal = 0.3D;
    SectionBand hbandType = new SectionBand(report);
    SectionBand hbandCategory = new SectionBand(report);
    SectionBand hbandDate = new SectionBand(report);
    SectionBand body = new SectionBand(report);
    SectionBand footer = new SectionBand(report);
    SectionBand spacer = new SectionBand(report);
    SectionBand selectionSpacer = new SectionBand(report);
    DefaultSectionLens group = null;
    footer.setVisible(true);
    footer.setHeight(0.1F);
    footer.setShrinkToFit(false);
    footer.setBottomBorder(0);
    spacer.setVisible(true);
    spacer.setHeight(0.05F);
    spacer.setShrinkToFit(false);
    spacer.setBottomBorder(0);
    selectionSpacer.setVisible(true);
    selectionSpacer.setHeight(0.03F);
    selectionSpacer.setShrinkToFit(false);
    selectionSpacer.setBottomBorder(0);
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
    MilestoneHelper.setSelectionSorting(selections, 12);
    Collections.sort(selections);
    MilestoneHelper.setSelectionSorting(selections, 14);
    Collections.sort(selections);
    MilestoneHelper.setSelectionSorting(selections, 4);
    Collections.sort(selections);
    MilestoneHelper.setSelectionSorting(selections, 3);
    Collections.sort(selections);
    MilestoneHelper.setSelectionSorting(selections, 1);
    Collections.sort(selections);
    MilestoneHelper.setSelectionSorting(selections, 10);
    Collections.sort(selections);
    try {
      DefaultTableLens table_contents = null;
      DefaultTableLens rowCountTable = null;
      DefaultTableLens columnHeaderTable = null;
      DefaultTableLens subTable = null;
      DefaultTableLens monthTableLens = null;
      DefaultTableLens configTableLens = null;
      DefaultTableLens dateTableLens = null;
      rowCountTable = new DefaultTableLens(2, 10000);
      int nextRow = 0;
      boolean shade = true;
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
      Hashtable selTable = groupSelectionsByLabelAndSubconfig(selections);
      Enumeration companies = selTable.keys();
      Vector companyVector = new Vector();
      while (companies.hasMoreElements())
        companyVector.addElement(companies.nextElement()); 
      int numSubconfigs = 0;
      for (int i = 0; i < companyVector.size(); i++) {
        String companyName = (companyVector.elementAt(i) != null) ? (String)companyVector.elementAt(i) : "";
        Hashtable subconfigTable = (Hashtable)selTable.get(companyName);
        if (subconfigTable != null)
          numSubconfigs += subconfigTable.size(); 
      } 
      int numExtraRows = companyVector.size() * 2 - 1 + numSubconfigs * 6;
      int numSelections = selections.size() * 2;
      int numRows = numSelections + numExtraRows;
      int numColumns = 9;
      nextRow = 0;
      Object[] companyArray = companyVector.toArray();
      Arrays.sort(companyArray, new StringComparator());
      for (int n = 0; n < companyArray.length; n++) {
        String company = (String)companyArray[n];
        String companyHeaderText = !company.trim().equals("") ? company : "Other";
        hbandType = new SectionBand(report);
        hbandType.setHeight(0.95F);
        hbandType.setShrinkToFit(true);
        hbandType.setVisible(true);
        nextRow = 0;
        table_contents = new DefaultTableLens(1, numColumns);
        table_contents.setColBorder(0);
        table_contents.setRowBorder(528384);
        table_contents.setRowBorderColor(Color.white);
        table_contents.setRowBorder(-1, 0);
        table_contents.setColWidth(0, 260);
        table_contents.setColWidth(1, 320);
        table_contents.setColWidth(2, 140);
        table_contents.setColWidth(3, 150);
        table_contents.setColWidth(4, 120);
        table_contents.setColWidth(5, 65);
        table_contents.setColWidth(6, 80);
        table_contents.setColWidth(7, 60);
        table_contents.setColWidth(8, 140);
        table_contents.setSpan(nextRow, 0, new Dimension(numColumns, 1));
        table_contents.setObject(nextRow, 0, companyHeaderText);
        table_contents.setRowBorder(nextRow, 0);
        table_contents.setRowBackground(nextRow, Color.black);
        table_contents.setRowForeground(nextRow, Color.white);
        table_contents.setRowFont(nextRow, new Font("Arial", 3, 16));
        hbandType.addTable(table_contents, new Rectangle(0, 0, 800, 30));
        nextRow = 0;
        columnHeaderTable = new DefaultTableLens(1, numColumns);
        columnHeaderTable.setColWidth(0, 260);
        columnHeaderTable.setColWidth(1, 320);
        columnHeaderTable.setColWidth(2, 140);
        columnHeaderTable.setColWidth(3, 150);
        columnHeaderTable.setColWidth(4, 120);
        columnHeaderTable.setColWidth(5, 65);
        columnHeaderTable.setColWidth(6, 80);
        columnHeaderTable.setColWidth(7, 60);
        columnHeaderTable.setColWidth(8, 140);
        columnHeaderTable.setColBorder(0);
        columnHeaderTable.setRowBorder(nextRow - 1, 0);
        columnHeaderTable.setRowBorder(nextRow, 4097);
        columnHeaderTable.setRowBorderColor(nextRow, Color.black);
        columnHeaderTable.setRowAlignment(nextRow, 32);
        columnHeaderTable.setObject(nextRow, 0, "\nArtist");
        columnHeaderTable.setObject(nextRow, 1, "\nTitle/B Side");
        columnHeaderTable.setObject(nextRow, 2, "\nSub-Config");
        columnHeaderTable.setObject(nextRow, 3, "Local\nProduct #");
        columnHeaderTable.setObject(nextRow, 4, "\nUPC");
        columnHeaderTable.setAlignment(nextRow, 4, 33);
        columnHeaderTable.setObject(nextRow, 5, "\nPrice");
        columnHeaderTable.setAlignment(nextRow, 5, 36);
        columnHeaderTable.setObject(nextRow, 6, "Street/\nShip\nDate");
        columnHeaderTable.setAlignment(nextRow, 6, 36);
        columnHeaderTable.setObject(nextRow, 7, "Impact\nDate");
        columnHeaderTable.setAlignment(nextRow, 7, 34);
        columnHeaderTable.setObject(nextRow, 8, "\nComments");
        columnHeaderTable.setRowFont(nextRow, new Font("Arial", 3, 10));
        columnHeaderTable.setRowBorder(nextRow, 4097);
        columnHeaderTable.setRowBorderColor(nextRow, Color.black);
        hbandType.addTable(columnHeaderTable, new Rectangle(0, 30, 800, 60));
        hbandType.setBottomBorder(0);
        Hashtable subconfigTable = (Hashtable)selTable.get(company);
        if (subconfigTable != null) {
          Enumeration subconfigs = subconfigTable.keys();
          Vector subconfigVector = new Vector();
          while (subconfigs.hasMoreElements())
            subconfigVector.add((String)subconfigs.nextElement()); 
          Object[] subconfigsArray = subconfigVector.toArray();
          Arrays.sort(subconfigsArray, new StringComparator());
          for (int scIndex = 0; scIndex < subconfigsArray.length; scIndex++) {
            String subconfig = (String)subconfigsArray[scIndex];
            configTableLens = new DefaultTableLens(1, numColumns);
            hbandCategory = new SectionBand(report);
            hbandCategory.setHeight(0.25F);
            hbandCategory.setShrinkToFit(true);
            hbandCategory.setVisible(true);
            hbandCategory.setBottomBorder(0);
            hbandCategory.setLeftBorder(0);
            hbandCategory.setRightBorder(0);
            hbandCategory.setTopBorder(0);
            nextRow = 0;
            configTableLens.setAlignment(nextRow, 0, 2);
            configTableLens.setSpan(nextRow, 0, new Dimension(numColumns, 1));
            configTableLens.setObject(nextRow, 0, subconfig);
            configTableLens.setRowFont(nextRow, new Font("Arial", 3, 12));
            hbandCategory.addTable(configTableLens, new Rectangle(800, 800));
            footer.setVisible(true);
            footer.setHeight(0.1F);
            footer.setShrinkToFit(false);
            footer.setBottomBorder(0);
            group = new DefaultSectionLens(null, group, spacer);
            group = new DefaultSectionLens(null, group, hbandCategory);
            group = new DefaultSectionLens(null, group, spacer);
            selections = (Vector)subconfigTable.get(subconfig);
            if (selections == null)
              selections = new Vector(); 
            int count = 2;
            int numRec = selections.size();
            int chunkSize = numRec / 10;
            for (int i = 0; i < selections.size(); i++) {
              try {
                int myPercent = i / chunkSize;
                if (myPercent > 1 && myPercent < 10)
                  count = myPercent; 
                HttpServletResponse sresponse = context.getResponse();
                context.putDelivery("status", new String("start_report"));
                context.putDelivery("percent", new String(String.valueOf(count * 10)));
                context.includeJSP("status.jsp", "hiddenFrame");
                sresponse.setContentType("text/plain");
                sresponse.flushBuffer();
              } catch (Exception exception) {}
              Selection sel = (Selection)selections.elementAt(i);
              nextRow = 0;
              subTable = new DefaultTableLens(2, numColumns);
              subTable.setRowBorderColor(Color.white);
              subTable.setColWidth(0, 260);
              subTable.setColWidth(1, 320);
              subTable.setColWidth(2, 140);
              subTable.setColWidth(3, 150);
              subTable.setColWidth(4, 120);
              subTable.setColWidth(5, 65);
              subTable.setColWidth(6, 80);
              subTable.setColWidth(7, 60);
              subTable.setColWidth(8, 140);
              String bSide = (sel.getBSide() != null && !sel.getBSide().trim().equals("")) ? (
                "B Side:  " + sel.getBSide()) : "";
              String txtSubconfig = "";
              if (sel.getSelectionSubConfig() != null)
                txtSubconfig = sel.getSelectionSubConfig().getSelectionSubConfigurationAbbreviation(); 
              String upc = (sel.getUpc() != null) ? sel.getUpc() : "";
              upc = MilestoneHelper_2.getRMSReportFormat(upc, "UPC", sel.getIsDigital());
              String localProductNumber = "";
              if (SelectionManager.getLookupObjectValue(sel.getPrefixID()) == null) {
                localProductNumber = "";
              } else {
                localProductNumber = sel.getSelectionNo();
              } 
              if (sel.getPrefixID() != null && sel.getPrefixID().getAbbreviation() != null)
                localProductNumber = String.valueOf(sel.getPrefixID().getAbbreviation()) + localProductNumber; 
              String price = "0.00";
              if (sel.getPriceCode() != null && 
                sel.getPriceCode().getTotalCost() > 0.0F)
                price = MilestoneHelper.formatDollarPrice(sel.getPriceCode().getTotalCost()); 
              String streetDate = MilestoneHelper.getFormatedDate(sel.getStreetDate());
              String status = (sel.getSelectionStatus() != null && sel.getSelectionStatus().getName() != null) ? 
                sel.getSelectionStatus().getName() : "";
              if (status.equalsIgnoreCase("TBS")) {
                streetDate = "TBS " + streetDate;
              } else if (status.equalsIgnoreCase("In The Works")) {
                streetDate = "ITW " + streetDate;
              } 
              String comment = (sel.getSelectionComments() != null) ? sel.getSelectionComments() : "";
              subTable.setRowInsets(nextRow, new Insets(0, 0, 0, 0));
              subTable.setObject(nextRow, 0, sel.getArtist());
              subTable.setObject(nextRow, 1, sel.getTitle());
              subTable.setObject(nextRow, 2, txtSubconfig);
              subTable.setObject(nextRow, 3, localProductNumber);
              subTable.setObject(nextRow, 4, upc);
              subTable.setObject(nextRow, 5, "$" + price);
              subTable.setColLineWrap(5, false);
              subTable.setObject(nextRow, 6, streetDate);
              subTable.setObject(nextRow, 7, MilestoneHelper.getFormatedDate(sel.getImpactDate()));
              subTable.setObject(nextRow, 8, comment);
              subTable.setColAlignment(0, 9);
              subTable.setSpan(nextRow, 0, new Dimension(1, 2));
              subTable.setColAlignment(1, 33);
              subTable.setColAlignment(2, 9);
              subTable.setSpan(nextRow, 2, new Dimension(1, 2));
              subTable.setColAlignment(3, 9);
              subTable.setSpan(nextRow, 3, new Dimension(1, 2));
              subTable.setColAlignment(4, 9);
              subTable.setSpan(nextRow, 4, new Dimension(1, 2));
              subTable.setColAlignment(5, 12);
              subTable.setSpan(nextRow, 5, new Dimension(1, 2));
              subTable.setColAlignment(6, 12);
              subTable.setSpan(nextRow, 6, new Dimension(1, 2));
              subTable.setColAlignment(7, 12);
              subTable.setSpan(nextRow, 7, new Dimension(1, 2));
              subTable.setColAlignment(8, 9);
              subTable.setSpan(nextRow, 8, new Dimension(1, 2));
              subTable.setRowBorder(nextRow, 1, 0);
              subTable.setRowBorder(nextRow - 1, 0);
              subTable.setRowFont(nextRow, new Font("Arial", 0, 8));
              subTable.setRowAutoSize(true);
              if (!shade) {
                subTable.setFont(nextRow, 1, new Font("Arial", 0, 8));
              } else {
                subTable.setRowBackground(nextRow, new Color(235, 235, 235, 0));
              } 
              nextRow++;
              subTable.setAlignment(nextRow, 1, 9);
              String[] checkStrings = { comment, sel.getArtist(), sel.getTitle() };
              int[] checkStringsLength = { 35, 50, 20 };
              if (bSide.equals("") && comment.equals(""))
                subTable.setRowHeight(nextRow, 1); 
              int addExtraLines = MilestoneHelper.lineCount(checkStrings, checkStringsLength);
              for (int z = 0; z < addExtraLines - 1; z++)
                bSide = String.valueOf(bSide) + "\n"; 
              if (!shade) {
                subTable.setFont(nextRow, 1, new Font("Arial", 0, 8));
              } else {
                subTable.setFont(nextRow, 1, new Font("Arial", 1, 8));
                subTable.setRowBackground(nextRow, new Color(235, 235, 235, 0));
              } 
              shade = !shade;
              subTable.setObject(nextRow, 0, "");
              subTable.setObject(nextRow, 1, bSide);
              subTable.setObject(nextRow, 2, "");
              subTable.setObject(nextRow, 3, "");
              subTable.setObject(nextRow, 4, "");
              subTable.setObject(nextRow, 5, "");
              subTable.setObject(nextRow, 6, "");
              subTable.setObject(nextRow, 7, "");
              subTable.setObject(nextRow, 8, "");
              subTable.setRowFont(nextRow, new Font("Arial", 2, 8));
              subTable.setColAlignment(1, 9);
              subTable.setRowBorderColor(nextRow, Color.white);
              subTable.setRowBorderColor(nextRow - 1, Color.white);
              subTable.setRowBorderColor(nextRow + 1, Color.white);
              subTable.setColBorder(0);
              body = new SectionBand(report);
              double lfLineCount = 1.5D;
              if (addExtraLines > 0) {
                body.setHeight(1.5F);
              } else {
                body.setHeight(0.8F);
              } 
              if (addExtraLines > 3) {
                if (lfLineCount < addExtraLines * 0.3D)
                  lfLineCount = addExtraLines * 0.3D; 
                body.setHeight((float)lfLineCount);
              } else {
                body.setHeight(0.8F);
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
        group = new DefaultSectionLens(hbandType, group, null);
        report.addSection(group, rowCountTable);
        report.addPageBreak();
        group = null;
      } 
    } catch (Exception e) {
      System.out.println(">>>>>>>>ReportHandler.fillEntRelScheduleForPrint(): exception: " + e);
    } 
  }
  
  public static Hashtable groupSelectionsByLabelAndSubconfig(Vector selections) {
    Hashtable groupedByCompanyAndSubconfig = new Hashtable();
    if (selections == null)
      return groupedByCompanyAndSubconfig; 
    for (int i = 0; i < selections.size(); i++) {
      Selection sel = (Selection)selections.elementAt(i);
      if (sel != null) {
        String familyName = "";
        String labelName = "";
        String configName = "";
        String subconfigName = "";
        Family family = sel.getFamily();
        SelectionConfiguration config = sel.getSelectionConfig();
        SelectionSubConfiguration subconfig = sel.getSelectionSubConfig();
        if (family != null)
          familyName = (family.getName() == null) ? "" : family.getName(); 
        labelName = sel.getImprint().trim();
        if (config != null)
          configName = (config.getSelectionConfigurationName() == null) ? 
            "" : config.getSelectionConfigurationName(); 
        if (subconfig != null)
          subconfigName = (subconfig.getSelectionSubConfigurationName() == null) ? 
            "" : subconfig.getSelectionSubConfigurationName(); 
        if (subconfigName.toUpperCase().indexOf("EP/SAMPLER") > -1 || 
          subconfigName.toUpperCase().indexOf("FULL") > -1 || subconfigName.equalsIgnoreCase("DUALDISC")) {
          subconfigName = "Full Length";
        } else if (!subconfigName.equals("")) {
          subconfigName = "Singles";
        } 
        Hashtable labelSubTable = (Hashtable)groupedByCompanyAndSubconfig.get(labelName);
        if (labelSubTable == null)
          for (Enumeration e = groupedByCompanyAndSubconfig.keys(); e.hasMoreElements(); ) {
            String keyStr = (String)e.nextElement();
            if (keyStr.equalsIgnoreCase(labelName)) {
              labelSubTable = (Hashtable)groupedByCompanyAndSubconfig.get(keyStr);
              break;
            } 
          }  
        if (labelSubTable == null) {
          labelSubTable = new Hashtable();
          groupedByCompanyAndSubconfig.put(labelName, labelSubTable);
        } 
        Vector selectionsForSubconfig = (Vector)labelSubTable.get(subconfigName);
        if (selectionsForSubconfig == null) {
          selectionsForSubconfig = new Vector();
          labelSubTable.put(subconfigName, selectionsForSubconfig);
        } 
        selectionsForSubconfig.addElement(sel);
      } 
    } 
    return groupedByCompanyAndSubconfig;
  }
}


/* Location:              D:\Documents\NetBeansProjects\milestone2 Local.war!\WEB-INF\classes\co\\universal\milestone\EntReleaseSchedByLabelSubHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */