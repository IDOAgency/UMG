package WEB-INF.classes.com.universal.milestone;

import com.techempower.ComponentLog;
import com.techempower.gemini.Context;
import com.techempower.gemini.GeminiApplication;
import com.universal.milestone.AddsAndMovesForPrintSubHandler;
import com.universal.milestone.Company;
import com.universal.milestone.Family;
import com.universal.milestone.Form;
import com.universal.milestone.MilestoneHelper;
import com.universal.milestone.MilestoneHelper_2;
import com.universal.milestone.SecureHandler;
import com.universal.milestone.Selection;
import com.universal.milestone.SelectionManager;
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

public class AddsAndMovesForPrintSubHandler extends SecureHandler {
  public static final String COMPONENT_CODE = "hNsl";
  
  public GeminiApplication application;
  
  public ComponentLog log;
  
  public static Context m_context;
  
  public AddsAndMovesForPrintSubHandler(GeminiApplication application) {
    this.application = application;
    this.log = application.getLog("hNsl");
  }
  
  public String getDescription() { return "Sub Report"; }
  
  protected static void fillAddsAndMovesForPrint(XStyleSheet report, Context context) {
    Color SHADED_AREA_COLOR = Color.lightGray;
    int COL_LINE_STYLE = 4097;
    int HEADER_FONT_SIZE = 12;
    int NUM_COLUMNS = 10;
    double ldLineVal = 0.3D;
    SectionBand hbandType = new SectionBand(report);
    SectionBand hbandCategory = new SectionBand(report);
    SectionBand hbandDate = new SectionBand(report);
    SectionBand body = new SectionBand(report);
    SectionBand footer = new SectionBand(report);
    SectionBand spacer = new SectionBand(report);
    SectionBand selectionSpacer = new SectionBand(report);
    DefaultSectionLens group = null;
    m_context = context;
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
      Form reportForm = (Form)context.getSessionValue("reportForm");
      String addsMovesFlag = reportForm.getStringValue("AddsMovesBoth");
      if (addsMovesFlag == null)
        addsMovesFlag = "Both"; 
      Calendar beginStDate = (reportForm.getStringValue("beginEffectiveDate") != null && 
        reportForm.getStringValue("beginEffectiveDate").length() > 0) ? 
        MilestoneHelper.getDate(reportForm.getStringValue("beginEffectiveDate")) : null;
      Calendar endStDate = (reportForm.getStringValue("endEffectiveDate") != null && 
        reportForm.getStringValue("endEffectiveDate").length() > 0) ? 
        MilestoneHelper.getDate(reportForm.getStringValue("endEffectiveDate")) : null;
      report.setElement("crs_startdate", MilestoneHelper.getFormatedDate(beginStDate));
      report.setElement("crs_enddate", MilestoneHelper.getFormatedDate(endStDate));
      SimpleDateFormat formatter = new SimpleDateFormat("MMMM d, yyyy");
      String todayLong = formatter.format(new Date());
      report.setElement("crs_bottomdate", todayLong);
      Hashtable selTable = groupSelectionsByAddsMoves(selections);
      Enumeration AddsMoves = selTable.keys();
      Vector AddMoveVector = new Vector();
      while (AddsMoves.hasMoreElements())
        AddMoveVector.addElement(AddsMoves.nextElement()); 
      int numSubconfigs = 0;
      for (int i = 0; i < AddMoveVector.size(); i++) {
        String modifcation = (AddMoveVector.elementAt(i) != null) ? (String)AddMoveVector.elementAt(i) : "";
        Hashtable subconfigTable = (Hashtable)selTable.get(modifcation);
        if (subconfigTable != null)
          numSubconfigs += subconfigTable.size(); 
      } 
      int numExtraRows = AddMoveVector.size() * 2 - 1 + numSubconfigs * 6;
      int numSelections = selections.size() * 2;
      int numRows = numSelections + numExtraRows;
      int numColumns = 6;
      nextRow = 0;
      Object[] AddMoveArray = AddMoveVector.toArray();
      Arrays.sort(AddMoveArray, new StringComparator());
      for (int n = 0; n < AddMoveArray.length; n++) {
        String company = (String)AddMoveArray[n];
        String companyHeaderText = !company.trim().equals("") ? company : "Other";
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
            hbandType = new SectionBand(report);
            hbandType.setHeight(0.65F);
            hbandType.setShrinkToFit(true);
            hbandType.setVisible(true);
            nextRow = 0;
            nextRow = 0;
            columnHeaderTable = new DefaultTableLens(1, 8);
            columnHeaderTable.setColWidth(0, 100);
            columnHeaderTable.setColWidth(1, 280);
            columnHeaderTable.setColWidth(2, 290);
            columnHeaderTable.setColWidth(3, 110);
            columnHeaderTable.setColWidth(4, 130);
            columnHeaderTable.setColWidth(5, 110);
            columnHeaderTable.setColWidth(6, 110);
            columnHeaderTable.setColBorder(0);
            columnHeaderTable.setRowBorder(nextRow - 1, 0);
            columnHeaderTable.setRowBorder(nextRow, 4097);
            columnHeaderTable.setRowBorderColor(nextRow, Color.black);
            columnHeaderTable.setAlignment(nextRow, 0, 18);
            if (subconfig.equalsIgnoreCase("Moves")) {
              columnHeaderTable.setObject(nextRow, 0, "Date\nChanged");
            } else {
              columnHeaderTable.setObject(nextRow, 0, "Date\nAdded");
            } 
            columnHeaderTable.setAlignment(nextRow, 1, 16);
            columnHeaderTable.setObject(nextRow, 1, "\nArtist");
            columnHeaderTable.setObject(nextRow, 2, "\nTitle");
            columnHeaderTable.setObject(nextRow, 3, "Imprint");
            columnHeaderTable.setAlignment(nextRow, 4, 18);
            columnHeaderTable.setObject(nextRow, 4, "Local Prod #\nUPC");
            columnHeaderTable.setAlignment(nextRow, 5, 18);
            columnHeaderTable.setObject(nextRow, 5, "\nSubconfig");
            columnHeaderTable.setAlignment(nextRow, 6, 18);
            columnHeaderTable.setObject(nextRow, 6, "In House/\nStreet Date");
            columnHeaderTable.setRowFont(nextRow, new Font("Arial", 3, 10));
            columnHeaderTable.setRowBorder(nextRow, 4097);
            columnHeaderTable.setRowBorderColor(nextRow, Color.black);
            Vector selectionsTemp = (Vector)subconfigTable.get(subconfig);
            if (selectionsTemp != null && selectionsTemp.size() > 0 && (
              addsMovesFlag.equalsIgnoreCase("Both") || 
              addsMovesFlag.equalsIgnoreCase(subconfig))) {
              hbandType.addTable(columnHeaderTable, new Rectangle(0, 0, 800, 35));
              hbandType.setBottomBorder(0);
              hbandType.setTopBorder(0);
              configTableLens = new DefaultTableLens(1, 10);
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
              configTableLens.setSpan(nextRow, 0, new Dimension(10, 1));
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
            } 
            selections = (Vector)subconfigTable.get(subconfig);
            if (!addsMovesFlag.equalsIgnoreCase("Both") && !addsMovesFlag.equalsIgnoreCase(subconfig))
              selections = null; 
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
              subTable = new DefaultTableLens(2, 7);
              subTable.setRowBorderColor(Color.lightGray);
              subTable.setColWidth(0, 100);
              subTable.setColWidth(1, 280);
              subTable.setColWidth(2, 290);
              subTable.setColWidth(3, 110);
              subTable.setColWidth(4, 130);
              subTable.setColWidth(5, 110);
              subTable.setColWidth(6, 110);
              String bSide = "";
              String upc = (sel.getUpc() != null) ? sel.getUpc() : "";
              upc = MilestoneHelper_2.getRMSReportFormat(upc, "UPC", sel.getIsDigital());
              String localProductNumber = "";
              if (SelectionManager.getLookupObjectValue(sel.getPrefixID()) == null) {
                localProductNumber = "";
              } else {
                localProductNumber = sel.getSelectionNo();
              } 
              String prefix = "";
              if (sel.getPrefixID() != null && sel.getPrefixID().getAbbreviation() != null)
                prefix = sel.getPrefixID().getAbbreviation(); 
              localProductNumber = String.valueOf(prefix) + localProductNumber;
              String price = "0.00";
              if (sel.getPriceCode() != null && 
                sel.getPriceCode().getTotalCost() > 0.0F)
                price = MilestoneHelper.formatDollarPrice(sel.getPriceCode().getTotalCost()); 
              String streetDate = MilestoneHelper.getCustomFormatedDate(sel.getStreetDate(), "MM/dd/yy");
              String status = (sel.getSelectionStatus() != null && sel.getSelectionStatus().getName() != null) ? 
                sel.getSelectionStatus().getName() : "";
              if (status.equalsIgnoreCase("TBS"))
                streetDate = "TBS " + streetDate; 
              String comment = (sel.getSelectionComments() != null) ? sel.getSelectionComments() : "";
              String description = (sel.getSelectionSubConfig() != null && sel.getSelectionSubConfig().getSelectionSubConfigurationName() != null) ? 
                sel.getSelectionSubConfig().getSelectionSubConfigurationName() : "";
              String imprint = sel.getImprint();
              subTable.setRowInsets(nextRow, new Insets(0, 0, 0, 0));
              if (subconfig.equalsIgnoreCase("Moves")) {
                subTable.setObject(nextRow, 0, MilestoneHelper.getCustomFormatedDate(sel.getLastStreetUpdateDate(), "MM/dd/yy"));
              } else {
                subTable.setObject(nextRow, 0, MilestoneHelper.getCustomFormatedDate(sel.getOriginDate(), "MM/dd/yy"));
              } 
              subTable.setObject(nextRow, 1, sel.getArtist());
              subTable.setObject(nextRow, 2, sel.getTitle());
              subTable.setObject(nextRow, 3, imprint);
              subTable.setObject(nextRow, 4, String.valueOf(localProductNumber) + "\n" + upc);
              subTable.setObject(nextRow, 5, description);
              subTable.setColLineWrap(6, false);
              subTable.setObject(nextRow, 6, streetDate);
              subTable.setColAlignment(0, 9);
              subTable.setSpan(nextRow, 0, new Dimension(1, 2));
              subTable.setColAlignment(1, 33);
              subTable.setColAlignment(2, 9);
              subTable.setSpan(nextRow, 2, new Dimension(1, 2));
              subTable.setColAlignment(3, 9);
              subTable.setSpan(nextRow, 3, new Dimension(1, 2));
              subTable.setColAlignment(4, 9);
              subTable.setSpan(nextRow, 4, new Dimension(1, 2));
              subTable.setColAlignment(5, 10);
              subTable.setSpan(nextRow, 5, new Dimension(1, 2));
              subTable.setColAlignment(6, 10);
              subTable.setSpan(nextRow, 6, new Dimension(1, 2));
              subTable.setRowBorder(nextRow, 1, 0);
              subTable.setRowBorder(nextRow - 1, 0);
              subTable.setRowFont(nextRow, new Font("Arial", 0, 8));
              subTable.setRowAutoSize(true);
              nextRow++;
              subTable.setAlignment(nextRow, 1, 9);
              String[] checkStrings = { sel.getArtist(), sel.getTitle(), imprint };
              int[] checkStringsLength = { 30, 40, 20 };
              int addExtraLines = MilestoneHelper.lineCount(checkStrings, checkStringsLength);
              subTable.setObject(nextRow, 0, "");
              subTable.setObject(nextRow, 1, bSide);
              subTable.setObject(nextRow, 2, "");
              subTable.setObject(nextRow, 3, "");
              subTable.setObject(nextRow, 4, "");
              subTable.setObject(nextRow, 5, "");
              subTable.setObject(nextRow, 6, "");
              subTable.setRowFont(nextRow, new Font("Arial", 2, 8));
              subTable.setColAlignment(1, 9);
              subTable.setRowBorderColor(nextRow, Color.lightGray);
              subTable.setRowBorderColor(nextRow - 1, Color.lightGray);
              subTable.setRowBorderColor(nextRow + 1, Color.lightGray);
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
            if (selections != null && selections.size() > 0) {
              group = new DefaultSectionLens(hbandType, group, null);
              report.addSection(group, rowCountTable);
            } 
            group = null;
          } 
        } 
      } 
    } catch (Exception e) {
      System.out.println(">>>>>>>>ReportHandler.fillAddsAndMovesForPrint(): exception: " + e);
    } 
  }
  
  public static Hashtable groupSelectionsByAddsMoves(Vector selections) {
    Hashtable groupedByAddsMove = new Hashtable();
    if (selections == null)
      return groupedByAddsMove; 
    Form reportForm = (Form)m_context.getSessionValue("reportForm");
    Calendar beginEffectiveDate = (reportForm.getStringValue("beginEffectiveDate") != null && reportForm.getStringValue("beginEffectiveDate").length() > 0) ? MilestoneHelper.getDate(reportForm.getStringValue("beginEffectiveDate")) : null;
    if (beginEffectiveDate != null) {
      beginEffectiveDate.set(11, 0);
      beginEffectiveDate.set(12, 0);
      beginEffectiveDate.set(13, 0);
      beginEffectiveDate.set(14, 0);
    } else {
      beginEffectiveDate = MilestoneHelper.getDate("1/1/1900");
    } 
    Calendar endEffectiveDate = (reportForm.getStringValue("endEffectiveDate") != null && reportForm.getStringValue("endEffectiveDate").length() > 0) ? MilestoneHelper.getDate(reportForm.getStringValue("endEffectiveDate")) : null;
    if (endEffectiveDate != null) {
      endEffectiveDate.set(11, 23);
      endEffectiveDate.set(12, 59);
      endEffectiveDate.set(13, 59);
      endEffectiveDate.set(14, 999);
    } else {
      endEffectiveDate = MilestoneHelper.getDate("12/31/2200");
    } 
    for (int i = 0; i < selections.size(); i++) {
      Selection sel = (Selection)selections.elementAt(i);
      if (sel != null) {
        String AddMoveInd = "";
        String MoveDate = "";
        String AddDate = "";
        String familyName = "", companyName = "";
        Family family = sel.getFamily();
        Company company = sel.getCompany();
        if (family != null)
          familyName = (family.getName() == null) ? "" : family.getName(); 
        if (company != null)
          companyName = (company.getName() == null) ? "" : company.getName(); 
        companyName = "";
        AddDate = MilestoneHelper.getCustomFormatedDate(sel.getOriginDate(), "MM/dd/yyyy");
        MoveDate = MilestoneHelper.getCustomFormatedDate(sel.getLastStreetUpdateDate(), "MM/dd/yyyy");
        if (MoveDate.length() == 0 || MoveDate == null)
          MoveDate = AddDate; 
        if (AddDate.length() == 0 || AddDate == null)
          AddDate = MoveDate; 
        if (sel.getOriginDate() != null && (AddDate.compareTo(MoveDate) != 0 || 
          sel.getOriginDate().before(beginEffectiveDate) || 
          sel.getOriginDate().after(endEffectiveDate))) {
          AddMoveInd = "Moves";
        } else {
          AddMoveInd = "Adds";
        } 
        Hashtable addsMovesSubTable = (Hashtable)groupedByAddsMove.get(companyName);
        if (addsMovesSubTable == null) {
          addsMovesSubTable = new Hashtable();
          groupedByAddsMove.put(companyName, addsMovesSubTable);
        } 
        Vector selectionsForSubconfig = (Vector)addsMovesSubTable.get(AddMoveInd);
        if (selectionsForSubconfig == null) {
          selectionsForSubconfig = new Vector();
          addsMovesSubTable.put(AddMoveInd, selectionsForSubconfig);
        } 
        if (sel.getLastStreetUpdateDate() != null && 
          sel.getLastStreetUpdateDate().before(endEffectiveDate) && (
          sel.getLastStreetUpdateDate().after(beginEffectiveDate) || 
          sel.getLastStreetUpdateDate().equals(beginEffectiveDate)))
          selectionsForSubconfig.addElement(sel); 
        if (AddDate.compareTo(MoveDate) != 0 && 
          sel.getOriginDate().before(endEffectiveDate) && 
          sel.getOriginDate().after(beginEffectiveDate) && 
          sel.getLastStreetUpdateDate().before(endEffectiveDate) && (
          sel.getLastStreetUpdateDate().after(beginEffectiveDate) || 
          sel.getLastStreetUpdateDate().equals(beginEffectiveDate))) {
          AddMoveInd = "Adds";
          addsMovesSubTable = (Hashtable)groupedByAddsMove.get(companyName);
          if (addsMovesSubTable == null) {
            addsMovesSubTable = new Hashtable();
            groupedByAddsMove.put(companyName, addsMovesSubTable);
          } 
          selectionsForSubconfig = (Vector)addsMovesSubTable.get(AddMoveInd);
          if (selectionsForSubconfig == null) {
            selectionsForSubconfig = new Vector();
            addsMovesSubTable.put(AddMoveInd, selectionsForSubconfig);
          } 
          if (sel.getLastStreetUpdateDate() != null && 
            sel.getLastStreetUpdateDate().before(endEffectiveDate) && (
            sel.getLastStreetUpdateDate().after(beginEffectiveDate) || 
            sel.getLastStreetUpdateDate().equals(beginEffectiveDate)))
            selectionsForSubconfig.addElement(sel); 
        } 
      } 
    } 
    return groupedByAddsMove;
  }
}


/* Location:              D:\Documents\NetBeansProjects\milestone2 Local.war!\WEB-INF\classes\co\\universal\milestone\AddsAndMovesForPrintSubHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */