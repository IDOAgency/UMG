package WEB-INF.classes.com.universal.milestone;

import com.techempower.ComponentLog;
import com.techempower.gemini.Context;
import com.techempower.gemini.GeminiApplication;
import com.universal.milestone.Form;
import com.universal.milestone.JdbcConnector;
import com.universal.milestone.MilestoneHelper;
import com.universal.milestone.Plant;
import com.universal.milestone.ReportHandler;
import com.universal.milestone.SecureHandler;
import com.universal.milestone.Selection;
import com.universal.milestone.SelectionManager;
import com.universal.milestone.StringComparator;
import com.universal.milestone.UmlAddsMovesReportSubHandler;
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
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;
import javax.servlet.http.HttpServletResponse;

public class UmlAddsMovesReportSubHandler extends SecureHandler {
  public static final String COMPONENT_CODE = "hUam";
  
  public GeminiApplication application;
  
  public ComponentLog log;
  
  public static int NUM_COLS = 15;
  
  public UmlAddsMovesReportSubHandler(GeminiApplication application) {
    this.application = application;
    this.log = application.getLog("hUam");
  }
  
  public String getDescription() { return "UML Adds and Moves Report"; }
  
  protected static void fillUmlAddsMovesReportForPrint(XStyleSheet report, Context context) {
    Color SHADED_AREA_COLOR = Color.lightGray;
    int COL_LINE_STYLE = 4097;
    int HEADER_FONT_SIZE = 12;
    int NUM_COLUMNS = 10;
    double ldLineVal = 0.3D;
    report.setMargin(new Margin(1.0D, 0.1D, 0.1D, 0.1D));
    report.setFooterFromEdge(0.3D);
    report.setHeaderFromEdge(0.1D);
    SectionBand hbandHeader = new SectionBand(report);
    SectionBand hbandMonth = new SectionBand(report);
    SectionBand hbandCategory = new SectionBand(report);
    SectionBand body = new SectionBand(report);
    SectionBand footer = new SectionBand(report);
    SectionBand spacer = new SectionBand(report);
    SectionBand hbandConfig = new SectionBand(report);
    DefaultSectionLens group = null;
    DefaultTableLens table_contents = null;
    DefaultTableLens columnHeaderTable = null;
    DefaultTableLens rowCountTable = null;
    DefaultTableLens subTable = null;
    DefaultTableLens theConfigTable = null;
    DefaultTableLens configTableLens = null;
    rowCountTable = new DefaultTableLens(2, 10000);
    String theConfig = "";
    try {
      HttpServletResponse sresponse = context.getResponse();
      context.putDelivery("status", new String("start_gathering"));
      context.includeJSP("status.jsp", "hiddenFrame");
      sresponse.setContentType("text/plain");
      sresponse.flushBuffer();
    } catch (Exception exception) {}
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
      String addsMovesFlag = reportForm.getStringValue("AddsMovesBoth");
      Vector reportSelections = MilestoneHelper.getSelectionsForReport(context);
      Vector AddsSelections = new Vector();
      Vector MovesSelections = new Vector();
      if (addsMovesFlag.equalsIgnoreCase("Adds") || addsMovesFlag.equalsIgnoreCase("Both"))
        AddsSelections = (Vector)reportSelections.elementAt(0); 
      if (addsMovesFlag.equalsIgnoreCase("Moves") || addsMovesFlag.equalsIgnoreCase("Both"))
        MovesSelections = (Vector)reportSelections.elementAt(1); 
      Vector selections = new Vector();
      if (AddsSelections != null)
        selections.addAll(AddsSelections); 
      if (MovesSelections != null)
        selections.addAll(MovesSelections); 
      try {
        HttpServletResponse sresponse = context.getResponse();
        context.putDelivery("status", new String("start_report"));
        context.putDelivery("percent", new String("10"));
        context.includeJSP("status.jsp", "hiddenFrame");
        sresponse.setContentType("text/plain");
        sresponse.flushBuffer();
      } catch (Exception exception) {}
      Hashtable selTable = groupSelectionsByAddsMovesConfigAndStreetDate(AddsSelections, MovesSelections);
      Enumeration AddsMovesEnum = selTable.keys();
      Vector AddsMovesVector = new Vector();
      while (AddsMovesEnum.hasMoreElements())
        AddsMovesVector.addElement(AddsMovesEnum.nextElement()); 
      int numConfigs = 0;
      int numExtraRows = 0;
      int numSelections = 0;
      int numRows = 0;
      for (int k = 0; k < AddsMovesVector.size(); k++) {
        Hashtable streetHash = (Hashtable)selTable.get(AddsMovesVector.get(k));
        Enumeration streetDates = streetHash.keys();
        Vector streetDatesVector = new Vector();
        while (streetDates.hasMoreElements())
          streetDatesVector.addElement(streetDates.nextElement()); 
        for (int i = 0; i < streetDatesVector.size(); i++) {
          String streetDateName = (streetDatesVector.elementAt(i) != null) ? (String)streetDatesVector.elementAt(i) : "";
          Hashtable streetDateTable = (Hashtable)streetHash.get(streetDateName);
          if (streetDateTable != null)
            numConfigs += streetDateTable.size(); 
        } 
      } 
      Object[] sortedAddsMovesArray = AddsMovesVector.toArray();
      Arrays.sort(sortedAddsMovesArray);
      int nextRow = 0;
      for (int xp = 0; xp < sortedAddsMovesArray.length; xp++) {
        String topGroup = (String)sortedAddsMovesArray[xp];
        Hashtable streetHash = (Hashtable)selTable.get(sortedAddsMovesArray[xp]);
        Enumeration streetDates = streetHash.keys();
        Vector streetDatesVector = new Vector();
        while (streetDates.hasMoreElements())
          streetDatesVector.addElement(streetDates.nextElement()); 
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
        configTableLens.setObject(nextRow, 0, sortedAddsMovesArray[xp]);
        configTableLens.setRowFont(nextRow, new Font("Arial", 3, 12));
        hbandCategory.addTable(configTableLens, new Rectangle(800, 30));
        table_contents = new DefaultTableLens(1, NUM_COLS);
        table_contents.setHeaderRowCount(1);
        table_contents.setColWidth(0, 140);
        table_contents.setColWidth(1, 150);
        table_contents.setColWidth(2, 10);
        table_contents.setColWidth(3, 110);
        table_contents.setColWidth(4, 25);
        table_contents.setColWidth(5, 250);
        table_contents.setColWidth(6, 200);
        table_contents.setColWidth(7, 200);
        table_contents.setColWidth(8, 100);
        table_contents.setColWidth(9, 45);
        table_contents.setColWidth(10, 85);
        table_contents.setColWidth(11, 85);
        table_contents.setColWidth(12, 85);
        table_contents.setColWidth(13, 85);
        table_contents.setColWidth(14, 85);
        table_contents.setRowBorder(0, 4097);
        table_contents.setRowBorderColor(-1, SHADED_AREA_COLOR);
        table_contents.setRowBorderColor(0, SHADED_AREA_COLOR);
        for (int i = 0; i < 15; i++)
          table_contents.setAlignment(0, i, 34); 
        table_contents.setInsets(new Insets(-1, 0, 0, 0));
        table_contents.setObject(0, 0, "P&D(*)\nSelection #");
        table_contents.setObject(0, 1, "Label");
        table_contents.setSpan(0, 1, new Dimension(2, 1));
        table_contents.setObject(0, 3, "Plant");
        table_contents.setObject(0, 4, "H\nD");
        table_contents.setObject(0, 5, "Artist");
        table_contents.setObject(0, 6, "Title");
        table_contents.setObject(0, 7, "Comments");
        table_contents.setObject(0, 8, "Subconfig");
        table_contents.setObject(0, 9, "P.O. Qty");
        table_contents.setObject(0, 10, "Unit");
        table_contents.setObject(0, 11, "Exploded\nTotal");
        table_contents.setObject(0, 12, "Qty\nDone");
        table_contents.setObject(0, 13, "Effective Date");
        table_contents.setObject(0, 14, "Old In House/\nStreetDate");
        table_contents.setRowFont(0, new Font("Arial", 1, 7));
        table_contents.setFont(0, 6, new Font("Arial", 3, 7));
        for (int i = -1; i < 15; i++)
          table_contents.setColBorderColor(0, i, new Color(208, 206, 206, 0)); 
        nextRow = 0;
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
          Hashtable configTableC = (Hashtable)streetHash.get(datesC);
          if (configTableC != null) {
            Enumeration monthsC = configTableC.keys();
            Vector monthVectorC = new Vector();
            while (monthsC.hasMoreElements())
              monthVectorC.add((String)monthsC.nextElement()); 
            Object[] configsArrayC = (Object[])null;
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
        sresponse.flushBuffer();
        int recordCount = 0;
        int count = 0;
        for (int n = 0; n < sortedStreetDatesVector.size(); n++) {
          columnHeaderTable = new DefaultTableLens(5, NUM_COLS);
          if (count < recordCount / tenth) {
            count = recordCount / tenth;
            sresponse = context.getResponse();
            context.putDelivery("status", new String("start_report"));
            context.putDelivery("percent", new String(String.valueOf(count * 10)));
            context.includeJSP("status.jsp", "hiddenFrame");
            sresponse.setContentType("text/plain");
            sresponse.flushBuffer();
          } 
          recordCount++;
          String theStreetDate = (String)sortedStreetDatesVector.elementAt(n);
          String theStreetDateText = !theStreetDate.trim().equals("") ? theStreetDate : "Other";
          nextRow = ReportHandler.insertLightGrayHeader(columnHeaderTable, theStreetDateText, nextRow, NUM_COLS);
          columnHeaderTable.setRowBorder(266240);
          columnHeaderTable.setRowBorderColor(-1, Color.lightGray);
          hbandConfig = new SectionBand(report);
          hbandConfig.setHeight(0.5F);
          hbandConfig.setShrinkToFit(true);
          hbandConfig.setVisible(true);
          hbandConfig.setBottomBorder(0);
          hbandConfig.setTopBorder(0);
          hbandConfig.addTable(columnHeaderTable, new Rectangle(0, 0, 800, 30));
          footer.setVisible(true);
          footer.setHeight(0.05F);
          footer.setShrinkToFit(true);
          footer.setBottomBorder(0);
          group = new DefaultSectionLens(null, group, hbandConfig);
          nextRow = 0;
          Hashtable configTable = (Hashtable)streetHash.get(theStreetDate);
          if (configTable != null) {
            Enumeration configsEnum = configTable.keys();
            Vector configsVector = new Vector();
            while (configsEnum.hasMoreElements())
              configsVector.add((String)configsEnum.nextElement()); 
            Object[] configsList = configsVector.toArray();
            Arrays.sort(configsList, new StringComparator());
            for (int x = 0; x < configsList.length; x++) {
              theConfigTable = new DefaultTableLens(2, NUM_COLS);
              nextRow = 0;
              theConfig = (String)configsList[x];
              if (count < recordCount / tenth) {
                count = recordCount / tenth;
                sresponse = context.getResponse();
                context.putDelivery("status", new String("start_report"));
                context.putDelivery("percent", new String(String.valueOf(count * 10)));
                context.includeJSP("status.jsp", "hiddenFrame");
                sresponse.setContentType("text/plain");
                sresponse.flushBuffer();
              } 
              recordCount++;
              setColBorderColors(nextRow, -1, NUM_COLS - 1, theConfigTable, new Color(208, 206, 206, 0));
              theConfigTable.setRowBorderColor(nextRow, new Color(208, 206, 206, 0));
              theConfigTable.setRowBorder(nextRow, 4097);
              setColBorderColors(nextRow + 1, -1, NUM_COLS - 1, theConfigTable, new Color(208, 206, 206, 0));
              theConfigTable.setRowBorderColor(nextRow + 1, new Color(208, 206, 206, 0));
              theConfigTable.setRowBorder(nextRow + 1, 4097);
              setColBorders(nextRow, -1, NUM_COLS - 1, theConfigTable, 0);
              theConfigTable.setColBorderColor(nextRow, -1, Color.white);
              theConfigTable.setColBorderColor(nextRow, 1, Color.white);
              theConfigTable.setColBorderColor(nextRow, 2, Color.white);
              theConfigTable.setColBorderColor(nextRow, 3, Color.white);
              theConfigTable.setSpan(nextRow, 0, new Dimension(NUM_COLS, 1));
              theConfigTable.setObject(nextRow, 0, theConfig);
              theConfigTable.setRowFont(nextRow, new Font("Arial", 1, 9));
              theConfigTable.setRowBackground(nextRow, Color.white);
              theConfigTable.setRowForeground(nextRow, Color.black);
              theConfigTable.setRowBorderColor(-1, Color.lightGray);
              theConfigTable.setRowBorderColor(Color.white);
              theConfigTable.setRowBorderColor(nextRow, Color.white);
              theConfigTable.setRowBorderColor(nextRow + 1, Color.white);
              hbandMonth = new SectionBand(report);
              hbandMonth.setHeight(0.5F);
              hbandMonth.setShrinkToFit(true);
              hbandMonth.setVisible(true);
              hbandMonth.setBottomBorder(0);
              hbandMonth.setLeftBorder(0);
              hbandMonth.setRightBorder(0);
              hbandMonth.setTopBorder(0);
              hbandMonth.addTable(theConfigTable, new Rectangle(0, 0, 800, 30));
              footer.setVisible(true);
              footer.setHeight(0.05F);
              footer.setShrinkToFit(true);
              footer.setBottomBorder(0);
              group = new DefaultSectionLens(null, group, hbandMonth);
              selections = (Vector)configTable.get(theConfig);
              if (selections == null)
                selections = new Vector(); 
              MilestoneHelper.setSelectionSorting(selections, 14);
              Collections.sort(selections);
              MilestoneHelper.applyManufacturingToSelections(selections);
              for (int ix = 0; ix < selections.size(); ix++) {
                Selection sel = (Selection)selections.elementAt(ix);
                Vector plants = sel.getManufacturingPlants();
                int plantSize = 1;
                if (plants != null && plants.size() > 0)
                  plantSize = plants.size(); 
                for (int plantCount = 0; plantCount < plantSize; plantCount++) {
                  Plant p = null;
                  if (plants != null && plants.size() > 0)
                    p = (Plant)plants.get(plantCount); 
                  nextRow = 0;
                  subTable = new DefaultTableLens(2, NUM_COLS);
                  subTable.setColWidth(0, 140);
                  subTable.setColWidth(1, 150);
                  subTable.setColWidth(2, 10);
                  subTable.setColWidth(3, 110);
                  subTable.setColWidth(4, 25);
                  subTable.setColWidth(5, 250);
                  subTable.setColWidth(6, 200);
                  subTable.setColWidth(7, 200);
                  subTable.setColWidth(8, 100);
                  subTable.setColWidth(9, 45);
                  subTable.setColWidth(10, 85);
                  subTable.setColWidth(11, 85);
                  subTable.setColWidth(12, 85);
                  subTable.setColWidth(13, 85);
                  subTable.setColWidth(14, 85);
                  subTable.setRowBorderColor(-1, new Color(208, 206, 206, 0));
                  subTable.setRowBorderColor(1, new Color(208, 206, 206, 0));
                  subTable.setRowBorderColor(0, Color.white);
                  subTable.setRowBorder(0, 4097);
                  int i;
                  for (i = 0; i < 15; i++)
                    subTable.setLineWrap(0, i, false); 
                  int artistLength = 0, labelLength = 0, titleLength = 0, idLength = 0, commentLength = 0;
                  int subconfigLength = 0;
                  if (count < recordCount / tenth) {
                    count = recordCount / tenth;
                    sresponse = context.getResponse();
                    context.putDelivery("status", new String("start_report"));
                    context.putDelivery("percent", new String(String.valueOf(count * 10)));
                    context.includeJSP("status.jsp", "hiddenFrame");
                    sresponse.setContentType("text/plain");
                    sresponse.flushBuffer();
                  } 
                  recordCount++;
                  String selectionNo = SelectionManager.getLookupObjectValue(sel.getPrefixID());
                  if (selectionNo == null)
                    selectionNo = ""; 
                  selectionNo = String.valueOf(selectionNo) + sel.getSelectionNo().trim();
                  idLength = selectionNo.length();
                  String pd = "";
                  if (sel.getPressAndDistribution())
                    pd = "* "; 
                  String selDistribution = SelectionManager.getLookupObjectValue(sel.getDistribution());
                  String artist = "";
                  artist = sel.getFlArtist();
                  if (artist != null)
                    artistLength = artist.length(); 
                  String comment = (sel.getSelectionComments() != null) ? sel.getSelectionComments().trim() : "";
                  commentLength = comment.length();
                  String titleComments = sel.getTitle();
                  if (titleComments != null)
                    titleLength = titleComments.length(); 
                  String poQty = (p != null && p.getOrderQty() > 0) ? String.valueOf(p.getOrderQty()) : "0";
                  String units = (sel.getNumberOfUnits() > 0) ? String.valueOf(sel.getNumberOfUnits()) : "";
                  int poQtyNum = 0;
                  try {
                    poQtyNum = Integer.parseInt(poQty);
                  } catch (Exception exception) {}
                  int explodedTotal = 0;
                  if (poQtyNum > 0 && sel.getNumberOfUnits() > 0)
                    explodedTotal = poQtyNum * sel.getNumberOfUnits(); 
                  String plant = "";
                  String plantText = "";
                  if (p != null && p.getPlant() != null) {
                    String plantNo = p.getPlant().getName();
                    plant = p.getPlant().getAbbreviation();
                    plantText = p.getPlant().getSubValue();
                    if (plant != null)
                      if (plant.equalsIgnoreCase("20")) {
                        plant = "KM";
                      } else if (plant.equalsIgnoreCase("175")) {
                        plant = "PI";
                      } else if (plant.equalsIgnoreCase("009")) {
                        plant = "HA";
                      } else if (plant.equalsIgnoreCase("171")) {
                        plant = "GL";
                      } else if (plant.equalsIgnoreCase("72")) {
                        plant = "CI";
                      } else if (plant.equalsIgnoreCase("371")) {
                        plant = "PA";
                      } else if (plant.equalsIgnoreCase("1772")) {
                        plant = "CIV";
                      } else {
                        plant = "";
                      }  
                  } 
                  String compQty = "0";
                  if (p != null && p.getCompletedQty() > 0)
                    compQty = MilestoneHelper.formatQuantityWithCommas(String.valueOf(p.getCompletedQty())); 
                  String label = "";
                  if (sel.getLabel() != null)
                    label = (sel.getLabel().getName().length() > 25) ? sel.getLabel().getName().substring(0, 24) : sel.getLabel().getName().trim(); 
                  labelLength = label.length();
                  String effectiveDatetxt = "";
                  if (sel.getLastStreetUpdateDate() != null) {
                    effectiveDatetxt = MilestoneHelper.getCustomFormatedDate(sel.getLastStreetUpdateDate(), "MM/dd/yy");
                  } else if (sel.getOriginDate() != null) {
                    effectiveDatetxt = MilestoneHelper.getCustomFormatedDate(sel.getOriginDate(), "MM/dd/yy");
                  } 
                  String subconfigtxt = "";
                  if (sel.getSelectionSubConfig() != null)
                    subconfigtxt = sel.getSelectionSubConfig().getSelectionSubConfigurationName(); 
                  if (subconfigtxt != null)
                    subconfigLength = subconfigtxt.length(); 
                  subTable.setRowInsets(nextRow, new Insets(0, 0, 5, 0));
                  subTable.setSpan(nextRow, 0, new Dimension(1, 2));
                  subTable.setSpan(nextRow, 1, new Dimension(2, 2));
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
                  subTable.setSpan(nextRow, 14, new Dimension(1, 2));
                  if (idLength == 13 && pd.trim().equals("*"))
                    pd = "*"; 
                  subTable.setObject(nextRow, 0, String.valueOf(pd) + selectionNo);
                  subTable.setObject(nextRow, 1, label);
                  subTable.setObject(nextRow, 3, plantText);
                  subTable.setObject(nextRow, 4, selDistribution);
                  subTable.setObject(nextRow, 5, artist);
                  subTable.setObject(nextRow, 6, titleComments);
                  subTable.setObject(nextRow, 7, comment);
                  subTable.setObject(nextRow, 8, subconfigtxt);
                  subTable.setObject(nextRow, 9, MilestoneHelper.formatQuantityWithCommas(poQty));
                  subTable.setObject(nextRow, 10, units);
                  subTable.setObject(nextRow, 11, MilestoneHelper.formatQuantityWithCommas(String.valueOf(explodedTotal)));
                  subTable.setObject(nextRow, 12, MilestoneHelper.formatQuantityWithCommas(compQty));
                  subTable.setObject(nextRow, 13, effectiveDatetxt);
                  if (topGroup.equalsIgnoreCase("Moves")) {
                    String strLastStDate = "";
                    StringBuffer StQuery = new StringBuffer(200);
                    StQuery.append("select top 1 street_date, logged_on from audit_release_header with (nolock) ");
                    StQuery.append("where release_id = " + sel.getIdentity());
                    StQuery.append(" order by logged_on DESC");
                    JdbcConnector connector = MilestoneHelper.getConnector(StQuery.toString());
                    if (connector != null) {
                      connector.setForwardOnly(false);
                      connector.runQuery();
                      SimpleDateFormat adf = new SimpleDateFormat("MM/dd/yy");
                      if (connector.more())
                        if (connector.getDate("street_date") != null)
                          strLastStDate = adf.format(connector.getDate("street_date"));  
                      connector.close();
                    } 
                    subTable.setObject(nextRow, 14, strLastStDate);
                  } else {
                    subTable.setObject(nextRow, 14, "N/A");
                  } 
                  subTable.setLineWrap(nextRow, 0, false);
                  subTable.setAlignment(nextRow, 0, 4);
                  for (i = 0; i < 15; i++)
                    subTable.setLineWrap(nextRow, i, true); 
                  for (i = 0; i < 9; i++)
                    subTable.setAlignment(nextRow, i, 8); 
                  for (i = 9; i < 15; i++)
                    subTable.setAlignment(nextRow, i, 12); 
                  subTable.setRowInsets(nextRow, new Insets(0, 0, 0, 0));
                  subTable.setRowFont(nextRow, new Font("Arial", 0, 6));
                  for (i = -1; i < 15; i++)
                    subTable.setColBorderColor(nextRow, i, new Color(208, 206, 206, 0)); 
                  subTable.setRowBorderColor(nextRow, new Color(208, 206, 206, 0));
                  subTable.setRowBorder(nextRow, 4097);
                  nextRow++;
                  for (i = -1; i < 15; i++)
                    subTable.setColBorderColor(nextRow, i, new Color(208, 206, 206, 0)); 
                  subTable.setRowBorderColor(nextRow, new Color(208, 206, 206, 0));
                  subTable.setRowBorderColor(nextRow + 1, new Color(208, 206, 206, 0));
                  subTable.setRowBorderColor(nextRow + 3, new Color(208, 206, 206, 0));
                  subTable.setRowInsets(nextRow, new Insets(0, 0, 0, 0));
                  int factor1 = titleLength / 16;
                  int factor2 = commentLength / 16;
                  if (titleLength > 16 || commentLength > 16) {
                    if (factor1 > factor2) {
                      subTable.setRowHeight(nextRow, factor1 + 1);
                    } else {
                      subTable.setRowHeight(nextRow, factor2 + 1);
                    } 
                  } else if (subconfigLength > 16) {
                    factor1 = subconfigLength / 16;
                    subTable.setRowHeight(nextRow, factor1 + 1);
                  } else {
                    subTable.setRowHeight(nextRow, 1);
                  } 
                  body = new SectionBand(report);
                  body.setHeight(0.5F);
                  body.addTable(subTable, new Rectangle(800, 800));
                  body.setTopBorder(2);
                  body.setShrinkToFit(true);
                  body.setVisible(true);
                  group = new DefaultSectionLens(null, group, body);
                  nextRow = 0;
                } 
              } 
            } 
          } 
        } 
        spacer.setHeight(0.1F);
        group = new DefaultSectionLens(spacer, group, null);
        group = new DefaultSectionLens(hbandCategory, group, null);
        group = new DefaultSectionLens(spacer, group, null);
        group = new DefaultSectionLens(hbandHeader, group, null);
        report.addSection(group, rowCountTable);
        report.addPageBreak();
        group = null;
      } 
    } catch (Exception e) {
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
  
  public static Hashtable groupSelectionsByAddsMovesConfigAndStreetDate(Vector addSelections, Vector movesSelections) {
    Hashtable groupSelectionsByAddsMovesConfigAndStreetDate = new Hashtable();
    if (addSelections == null && movesSelections == null)
      return groupSelectionsByAddsMovesConfigAndStreetDate; 
    Vector selections = addSelections;
    String AddMoveInd = "Adds";
    for (int j = 0; j < 2; j++) {
      if (selections != null)
        for (int i = 0; i < selections.size(); i++) {
          Selection sel = (Selection)selections.elementAt(i);
          if (sel != null) {
            String configString = "", dateString = "";
            configString = (sel.getSelectionConfig() != null) ? sel.getSelectionConfig().getSelectionConfigurationName() : "";
            dateString = (sel.getStreetDate() != null) ? MilestoneHelper.getFormatedDate(sel.getStreetDate()) : "";
            Hashtable addsMovesSubTable = (Hashtable)groupSelectionsByAddsMovesConfigAndStreetDate.get(AddMoveInd);
            if (addsMovesSubTable == null) {
              addsMovesSubTable = new Hashtable();
              groupSelectionsByAddsMovesConfigAndStreetDate.put(AddMoveInd, addsMovesSubTable);
            } 
            Hashtable streetTable = (Hashtable)addsMovesSubTable.get(dateString);
            if (streetTable == null) {
              streetTable = new Hashtable();
              addsMovesSubTable.put(dateString, streetTable);
            } 
            Vector configsForDate = (Vector)streetTable.get(configString);
            if (configsForDate == null) {
              configsForDate = new Vector();
              streetTable.put(configString, configsForDate);
            } 
            configsForDate.addElement(sel);
          } 
        }  
      selections = movesSelections;
      AddMoveInd = "Moves";
    } 
    return groupSelectionsByAddsMovesConfigAndStreetDate;
  }
}


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\UmlAddsMovesReportSubHandler.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */