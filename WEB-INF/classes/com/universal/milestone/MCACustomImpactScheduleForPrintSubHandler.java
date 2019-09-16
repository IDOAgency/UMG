package WEB-INF.classes.com.universal.milestone;

import com.techempower.ComponentLog;
import com.techempower.gemini.Context;
import com.techempower.gemini.GeminiApplication;
import com.universal.milestone.Company;
import com.universal.milestone.DatePeriod;
import com.universal.milestone.Family;
import com.universal.milestone.Form;
import com.universal.milestone.ImpactDate;
import com.universal.milestone.IntegerComparator;
import com.universal.milestone.MCACustomImpactScheduleForPrintSubHandler;
import com.universal.milestone.MilestoneHelper;
import com.universal.milestone.MilestoneHelper_2;
import com.universal.milestone.ProductCategory;
import com.universal.milestone.ReleaseType;
import com.universal.milestone.SecureHandler;
import com.universal.milestone.Selection;
import com.universal.milestone.SelectionArtistComparator;
import com.universal.milestone.SelectionImpactDateComparator;
import com.universal.milestone.SelectionManager;
import com.universal.milestone.SelectionSelectionNumberComparator;
import com.universal.milestone.SelectionStreetDateComparator;
import com.universal.milestone.SelectionSubConfiguration;
import com.universal.milestone.SelectionTitleComparator;
import com.universal.milestone.StringComparator;
import com.universal.milestone.StringReverseComparator;
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

public class MCACustomImpactScheduleForPrintSubHandler extends SecureHandler {
  public static final String COMPONENT_CODE = "hCProd";
  
  public GeminiApplication application;
  
  public ComponentLog log;
  
  public static Context m_context;
  
  public static Calendar beginStDate;
  
  public static Calendar endStDate;
  
  public void MCACustomImpactScheduleForPrintSubHandler(GeminiApplication application) {
    this.application = application;
    this.log = application.getLog("hCProd");
  }
  
  public String getDescription() { return "Sub Report"; }
  
  protected static void fillMCACustomImpactScheduleForPrint(XStyleSheet report, Context context) {
    Color SHADED_AREA_COLOR = Color.lightGray;
    int COL_LINE_STYLE = 4097;
    int HEADER_FONT_SIZE = 12;
    int NUM_COLUMNS = 8;
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
    Vector initialSelections = MilestoneHelper.getSelectionsForReport(context);
    try {
      HttpServletResponse sresponse = context.getResponse();
      context.putDelivery("status", new String("start_report"));
      context.putDelivery("percent", new String("10"));
      context.includeJSP("status.jsp", "hiddenFrame");
      sresponse.setContentType("text/plain");
      sresponse.flushBuffer();
    } catch (Exception exception) {}
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
      beginStDate = (reportForm.getStringValue("beginDate") != null && 
        reportForm.getStringValue("beginDate").length() > 0) ? 
        MilestoneHelper.getDate(reportForm.getStringValue("beginDate")) : null;
      endStDate = (reportForm.getStringValue("endDate") != null && 
        reportForm.getStringValue("endDate").length() > 0) ? 
        MilestoneHelper.getDate(reportForm.getStringValue("endDate")) : null;
      report.setElement("crs_startdate", MilestoneHelper.getFormatedDate(beginStDate));
      report.setElement("crs_enddate", MilestoneHelper.getFormatedDate(endStDate));
      SimpleDateFormat formatter = new SimpleDateFormat("MMMM d, yyyy");
      String todayLong = formatter.format(new Date());
      report.setElement("crs_bottomdate", todayLong);
      Vector selections = constructSelectionsWithImpactDates(initialSelections);
      Hashtable selTable = groupSelectionsByHeaderType(selections);
      Enumeration CompanyEnum = selTable.keys();
      Vector CompanyVector = new Vector();
      while (CompanyEnum.hasMoreElements())
        CompanyVector.addElement(CompanyEnum.nextElement()); 
      int numSelections = 0;
      for (int i = 0; i < CompanyVector.size(); i++) {
        String aCompany = (CompanyVector.elementAt(i) != null) ? (String)CompanyVector.elementAt(i) : "";
        Hashtable subconfigTable = (Hashtable)selTable.get(aCompany);
        Enumeration releasesEnum = subconfigTable.keys();
        Vector releasesVector = new Vector();
        while (releasesEnum.hasMoreElements())
          releasesVector.addElement(releasesEnum.nextElement()); 
        for (int k = 0; k < releasesVector.size(); k++) {
          String aRelease = (String)releasesVector.elementAt(k);
          Hashtable months = (Hashtable)subconfigTable.get(aRelease);
          Enumeration monthEnum = months.keys();
          Vector monthVector = new Vector();
          while (monthEnum.hasMoreElements())
            monthVector.addElement(monthEnum.nextElement()); 
          for (int j = 0; j < monthVector.size(); j++) {
            String aMonth = (String)monthVector.elementAt(j);
            Vector selectVector = (Vector)months.get(aMonth);
            numSelections += selectVector.size();
          } 
        } 
      } 
      int numColumns = 8;
      nextRow = 0;
      Object[] CompanyArray = CompanyVector.toArray();
      Arrays.sort(CompanyArray, new StringComparator());
      for (int n = 0; n < CompanyArray.length; n++) {
        String company = (String)CompanyArray[n];
        String companyHeaderText = !company.trim().equals("") ? company : "Other";
        Hashtable subconfigTable = (Hashtable)selTable.get(company);
        if (subconfigTable != null) {
          Enumeration subconfigs = subconfigTable.keys();
          Vector subconfigVector = new Vector();
          while (subconfigs.hasMoreElements())
            subconfigVector.add((String)subconfigs.nextElement()); 
          Object[] subconfigsArray = subconfigVector.toArray();
          Arrays.sort(subconfigsArray, new StringReverseComparator());
          for (int scIndex = 0; scIndex < subconfigsArray.length; scIndex++) {
            String subconfig = (String)subconfigsArray[scIndex];
            nextRow = 0;
            columnHeaderTable = new DefaultTableLens(1, 8);
            if (!subconfig.equalsIgnoreCase("Discard")) {
              if (subconfig.equalsIgnoreCase("Release with Impact Dates")) {
                columnHeaderTable.setColWidth(0, 50);
                columnHeaderTable.setColWidth(1, 130);
                columnHeaderTable.setColWidth(2, 50);
                columnHeaderTable.setColWidth(3, 120);
                columnHeaderTable.setColWidth(4, 70);
                columnHeaderTable.setColWidth(5, 50);
                columnHeaderTable.setColWidth(6, 50);
                columnHeaderTable.setColWidth(7, 50);
              } else {
                columnHeaderTable.setColWidth(0, 60);
                columnHeaderTable.setColWidth(1, 160);
                columnHeaderTable.setColWidth(2, 60);
                columnHeaderTable.setColWidth(3, 80);
                columnHeaderTable.setColWidth(4, 10);
                columnHeaderTable.setColWidth(5, 30);
                columnHeaderTable.setColWidth(6, 60);
                columnHeaderTable.setColWidth(7, 50);
              } 
              columnHeaderTable.setColBorder(0);
              columnHeaderTable.setRowBorder(nextRow - 1, 0);
              columnHeaderTable.setRowBorder(nextRow, 4097);
              columnHeaderTable.setRowBorderColor(nextRow, Color.black);
              columnHeaderTable.setAlignment(nextRow, 0, 16);
              columnHeaderTable.setAlignment(nextRow, 1, 16);
              columnHeaderTable.setAlignment(nextRow, 3, 18);
              if (subconfig.equalsIgnoreCase("Release with Impact Dates"))
                columnHeaderTable.setAlignment(nextRow, 4, 18); 
              columnHeaderTable.setObject(nextRow, 0, "\nArtist");
              columnHeaderTable.setObject(nextRow, 1, "\nTitle");
              columnHeaderTable.setObject(nextRow, 2, "Configuration");
              columnHeaderTable.setObject(nextRow, 3, "Local Prod #\nUPC");
              if (subconfig.equalsIgnoreCase("Release with Impact Dates")) {
                columnHeaderTable.setObject(nextRow, 4, "Impact\nDate");
                columnHeaderTable.setObject(nextRow, 5, "Format");
              } else {
                columnHeaderTable.setObject(nextRow, 4, "");
                columnHeaderTable.setObject(nextRow, 5, "");
              } 
              if (subconfig.equalsIgnoreCase("Release with Impact Dates")) {
                columnHeaderTable.setObject(nextRow, 6, "In House/\nStreet Date");
              } else if (subconfig.equalsIgnoreCase("Promo without Impact Dates")) {
                columnHeaderTable.setObject(nextRow, 6, "In House");
              } else if (subconfig.equalsIgnoreCase("Commercial Singles")) {
                columnHeaderTable.setObject(nextRow, 6, "Street Date");
              } 
              columnHeaderTable.setObject(nextRow, 7, "Release\nWeek");
              columnHeaderTable.setRowFont(nextRow, new Font("Arial", 3, 10));
              columnHeaderTable.setRowBorder(nextRow, 4097);
              columnHeaderTable.setRowBorderColor(nextRow, Color.black);
              footer.setVisible(true);
              footer.setHeight(0.1F);
              footer.setShrinkToFit(false);
              footer.setBottomBorder(0);
              configTableLens = new DefaultTableLens(1, 8);
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
                    monthNameString = "No street date";
                  } 
                  nextRow = 0;
                  configTableLens.setAlignment(nextRow, 0, 2);
                  configTableLens.setSpan(nextRow, 0, new Dimension(8, 1));
                  configTableLens.setObject(nextRow, 0, subconfig);
                  configTableLens.setRowFont(nextRow, new Font("Arial", 3, 12));
                  monthTableLens = new DefaultTableLens(1, 8);
                  monthTableLens.setSpan(nextRow, 0, new Dimension(8, 1));
                  monthTableLens.setObject(nextRow, 0, monthNameString);
                  monthTableLens.setRowHeight(nextRow, 14);
                  monthTableLens.setRowFont(nextRow, new Font("Arial", 1, 12));
                  monthTableLens.setRowBackground(nextRow, SHADED_AREA_COLOR);
                  monthTableLens.setRowForeground(nextRow, Color.black);
                  monthTableLens.setRowBorderColor(nextRow, Color.white);
                  monthTableLens.setRowBorderColor(nextRow - 1, Color.white);
                  monthTableLens.setColBorderColor(nextRow, -1, Color.white);
                  monthTableLens.setColBorderColor(nextRow, 0, Color.white);
                  monthTableLens.setColBorderColor(nextRow, 14, Color.white);
                  hbandType = new SectionBand(report);
                  hbandType.setHeight(1.23F);
                  hbandType.setShrinkToFit(true);
                  hbandType.setVisible(true);
                  hbandType.addTable(columnHeaderTable, new Rectangle(0, 20, 800, 25));
                  hbandType.addTable(configTableLens, new Rectangle(0, 50, 800, 25));
                  hbandType.addTable(monthTableLens, new Rectangle(0, 70, 800, 25));
                  selections = (Vector)monthTable.get(monthName);
                  if (selections == null)
                    selections = new Vector(); 
                  Object[] selectionsArray = selections.toArray();
                  if (subconfig.equalsIgnoreCase("Promo without Impact Dates") || subconfig.equalsIgnoreCase("Commercial Singles")) {
                    Arrays.sort(selectionsArray, new SelectionArtistComparator());
                    Arrays.sort(selectionsArray, new SelectionStreetDateComparator());
                  } else {
                    Arrays.sort(selectionsArray, new SelectionSelectionNumberComparator());
                    Arrays.sort(selectionsArray, new SelectionTitleComparator());
                    Arrays.sort(selectionsArray, new SelectionArtistComparator());
                    Arrays.sort(selectionsArray, new SelectionImpactDateComparator());
                  } 
                  int count = 2;
                  int numRec = selections.size();
                  int chunkSize = numRec / 10;
                  for (int i = 0; i < selectionsArray.length; i++) {
                    String selectionNumber;
                    try {
                      int myPercent = i / chunkSize;
                      if (myPercent > 1 && myPercent < 10)
                        count = myPercent; 
                      selectionNumber = context.getResponse();
                      context.putDelivery("status", new String("start_report"));
                      context.putDelivery("percent", new String(String.valueOf(count * 10)));
                      context.includeJSP("status.jsp", "hiddenFrame");
                      selectionNumber.setContentType("text/plain");
                      selectionNumber.flushBuffer();
                    } catch (Exception exception) {}
                    Selection sel = (Selection)selectionsArray[i];
                    nextRow = 0;
                    subTable = new DefaultTableLens(2, 8);
                    subTable.setRowBorderColor(Color.lightGray);
                    subTable.setColWidth(0, 50);
                    subTable.setColWidth(1, 150);
                    subTable.setColWidth(2, 75);
                    subTable.setColWidth(3, 75);
                    if (subconfig.equalsIgnoreCase("Release with Impact Dates")) {
                      subTable.setColWidth(4, 50);
                      subTable.setColWidth(5, 50);
                    } else {
                      subTable.setColWidth(4, 1);
                      subTable.setColWidth(5, 1);
                    } 
                    subTable.setColWidth(6, 50);
                    subTable.setColWidth(7, 50);
                    if (SelectionManager.getLookupObjectValue(sel.getPrefixID()).equals("")) {
                      selectionNumber = sel.getSelectionNo();
                    } else {
                      selectionNumber = String.valueOf(SelectionManager.getLookupObjectValue(sel.getPrefixID())) + "-" + sel.getSelectionNo();
                    } 
                    String upc = (sel.getUpc() != null) ? sel.getUpc() : " ";
                    upc = MilestoneHelper_2.getRMSReportFormat(upc, "UPC", sel.getIsDigital());
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
                    DatePeriod dp = MilestoneHelper.getReleaseWeek(sel);
                    String selReleaseWeek = (dp != null) ? dp.getName() : "";
                    subTable.setRowInsets(nextRow, new Insets(0, 0, 0, 0));
                    subTable.setFont(nextRow, 0, new Font("Arial", 1, 8));
                    subTable.setObject(nextRow, 0, sel.getArtist());
                    subTable.setObject(nextRow, 1, sel.getTitle());
                    subTable.setObject(nextRow, 2, sel.getSelectionConfig().getSelectionConfigurationAbbreviation());
                    subTable.setObject(nextRow, 3, String.valueOf(selectionNumber) + "\n" + upc);
                    if (sel.getImpactDates().size() != 0) {
                      ImpactDate theImpactDate = sel.getImpactDateObject();
                      Calendar cals = theImpactDate.getImpactDate();
                      if (!theImpactDate.getTbi()) {
                        subTable.setObject(nextRow, 4, MilestoneHelper.getCustomFormatedDate(cals, "MM/dd"));
                      } else {
                        subTable.setObject(nextRow, 4, "TBI");
                      } 
                      subTable.setObject(nextRow, 5, theImpactDate.getFormatDescription());
                    } else {
                      subTable.setObject(nextRow, 4, "");
                      subTable.setObject(nextRow, 5, "");
                    } 
                    subTable.setObject(nextRow, 6, MilestoneHelper.getCustomFormatedDate(sel.getStreetDate(), "MM/dd"));
                    subTable.setObject(nextRow, 7, selReleaseWeek);
                    subTable.setColAlignment(0, 9);
                    subTable.setSpan(nextRow, 0, new Dimension(1, 2));
                    subTable.setColAlignment(1, 33);
                    subTable.setColAlignment(2, 9);
                    subTable.setSpan(nextRow, 2, new Dimension(1, 2));
                    subTable.setColAlignment(3, 9);
                    subTable.setSpan(nextRow, 3, new Dimension(1, 2));
                    subTable.setColAlignment(4, 10);
                    subTable.setSpan(nextRow, 4, new Dimension(1, 2));
                    subTable.setColAlignment(5, 10);
                    subTable.setSpan(nextRow, 5, new Dimension(1, 2));
                    subTable.setColAlignment(6, 10);
                    subTable.setSpan(nextRow, 6, new Dimension(1, 2));
                    subTable.setColAlignment(7, 10);
                    subTable.setSpan(nextRow, 7, new Dimension(1, 2));
                    subTable.setRowBorder(nextRow, 1, 0);
                    subTable.setRowBorder(nextRow - 1, 0);
                    subTable.setRowFont(nextRow, new Font("Arial", 0, 8));
                    subTable.setRowAutoSize(true);
                    nextRow++;
                    subTable.setAlignment(nextRow, 1, 9);
                    String[] checkStrings = { sel.getArtist(), sel.getTitle() };
                    int[] checkStringsLength = { 20, 40 };
                    int addExtraLines = MilestoneHelper.lineCount(checkStrings, checkStringsLength);
                    String adder = "";
                    for (int z = 0; z < addExtraLines - 1; z++)
                      adder = String.valueOf(adder) + "\n"; 
                    subTable.setObject(nextRow, 0, "");
                    subTable.setObject(nextRow, 1, adder);
                    subTable.setObject(nextRow, 2, "");
                    subTable.setObject(nextRow, 3, "");
                    subTable.setObject(nextRow, 4, "");
                    subTable.setObject(nextRow, 5, "");
                    subTable.setObject(nextRow, 6, "");
                    subTable.setObject(nextRow, 7, "");
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
                  group = new DefaultSectionLens(hbandType, group, null);
                  report.addSection(group, rowCountTable);
                  report.addPageBreak();
                  group = null;
                } 
              } 
            } 
          } 
        } 
      } 
    } catch (Exception e) {
      System.out.println(">>>>>>>>ReportHandler.fillMCACustomImpactScheduleForPrint(): exception: " + e);
    } 
  }
  
  public static Hashtable groupSelectionsByHeaderType(Vector selections) {
    Hashtable groupedByHeaderTypes = new Hashtable();
    if (selections == null)
      return groupedByHeaderTypes; 
    Form reportForm = (Form)m_context.getSessionValue("reportForm");
    for (int i = 0; i < selections.size(); i++) {
      Selection sel = (Selection)selections.elementAt(i);
      if (sel != null) {
        String ReleaseTypeInd = "";
        String familyName = "";
        String companyName = "";
        String productCatString = "";
        String subConfigString = "";
        String releaseTypeString = "";
        String configString = "";
        Family family = sel.getFamily();
        Company company = sel.getCompany();
        ProductCategory productCat = sel.getProductCategory();
        SelectionSubConfiguration subConfig = sel.getSelectionSubConfig();
        ReleaseType releaseType = sel.getReleaseType();
        if (family != null)
          familyName = (family.getName() == null) ? "" : family.getName(); 
        if (company != null)
          companyName = (company.getName() == null) ? "" : company.getName(); 
        productCatString = productCat.getName();
        releaseTypeString = releaseType.getName();
        subConfigString = subConfig.getSelectionSubConfigurationName();
        configString = (sel.getSelectionSubConfig().getSelectionSubConfigurationAbbreviation() != null) ? sel.getSelectionSubConfig().getSelectionSubConfigurationAbbreviation() : "";
        if (productCatString.equals("New Release") && sel.getImpactDates().size() != 0) {
          ReleaseTypeInd = "Release with Impact Dates";
        } else if (releaseTypeString.equals("Promotional") && sel.getImpactDates().size() == 0) {
          ReleaseTypeInd = "Promo without Impact Dates";
        } else if (releaseTypeString.equals("Commercial") && sel.getImpactDates().size() == 0) {
          ReleaseTypeInd = "Commercial Singles";
        } 
        if (productCatString.equals("Advances") && sel.getImpactDates().size() != 0)
          ReleaseTypeInd = "Release with Impact Dates"; 
        if ((subConfigString.equals("Full Length") || subConfigString.equalsIgnoreCase("DUALDISC")) && sel.getImpactDates().size() == 0)
          ReleaseTypeInd = "Discard"; 
        if (configString.equals("DVDVID") || configString.equals("DVDAUD") || 
          configString.equals("VIDEO") || configString.equals("SACD"))
          ReleaseTypeInd = "Discard"; 
        if (sel != null && sel.getSelectionConfig() != null && 
          sel.getSelectionConfig().getSelectionConfigurationName().equals("SACD"))
          ReleaseTypeInd = "Discard"; 
        if (sel != null && sel.getSelectionConfig() != null && 
          sel.getSelectionConfig().getSelectionConfigurationAbbreviation().equals("DP"))
          ReleaseTypeInd = "Discard"; 
        String dateString = "";
        String monthString = "";
        if (sel.getImpactDates().size() == 0) {
          Calendar calendarDate = sel.getStreetDate();
          dateString = MilestoneHelper.getFormatedDate(calendarDate);
          if (calendarDate != null) {
            SimpleDateFormat formatter = new SimpleDateFormat("MM");
            monthString = formatter.format(calendarDate.getTime());
          } 
        } else {
          ImpactDate theImpactDate = sel.getImpactDateObject();
          Calendar calendarDate = theImpactDate.getImpactDate();
          dateString = MilestoneHelper.getFormatedDate(calendarDate);
          if (calendarDate != null) {
            SimpleDateFormat formatter = new SimpleDateFormat("MM");
            monthString = formatter.format(calendarDate.getTime());
          } 
        } 
        Hashtable releaseTypeSubTable = (Hashtable)groupedByHeaderTypes.get(companyName);
        if (releaseTypeSubTable == null) {
          releaseTypeSubTable = new Hashtable();
          groupedByHeaderTypes.put(companyName, releaseTypeSubTable);
        } 
        Hashtable selectionsForSubconfig = (Hashtable)releaseTypeSubTable.get(ReleaseTypeInd);
        if (selectionsForSubconfig == null) {
          selectionsForSubconfig = new Hashtable();
          releaseTypeSubTable.put(ReleaseTypeInd, selectionsForSubconfig);
        } 
        try {
          Integer.parseInt(monthString);
        } catch (Exception e) {
          monthString = "52";
        } 
        Vector monthsTable = (Vector)selectionsForSubconfig.get(monthString);
        if (monthsTable == null) {
          monthsTable = new Vector();
          selectionsForSubconfig.put(monthString, monthsTable);
        } 
        monthsTable.addElement(sel);
      } 
    } 
    return groupedByHeaderTypes;
  }
  
  private static Vector constructSelectionsWithImpactDates(Vector vectorToOrder) {
    Vector finalVector = new Vector();
    for (int count = 0; count < vectorToOrder.size(); count++) {
      Selection temp = (Selection)vectorToOrder.elementAt(count);
      if (temp.getImpactDates().size() == 0) {
        finalVector.add(temp);
      } else {
        for (int i = 0; i < temp.getImpactDates().size(); i++) {
          ImpactDate iDate = (ImpactDate)temp.getImpactDates().elementAt(i);
          Selection tempSelection = new Selection();
          try {
            tempSelection = (Selection)temp.clone();
          } catch (Exception e) {
            System.out.println("Cloning Error in groupSelectionsByHeaderType");
          } 
          Calendar selectionStartDate = tempSelection.getStreetDate();
          Calendar selectionImpactCal = ((ImpactDate)temp.getImpactDates().elementAt(i)).getImpactDate();
          boolean discardSelectionImpact = false;
          if (selectionStartDate != null && ((beginStDate != null && selectionStartDate.before(beginStDate)) || (
            endStDate != null && selectionStartDate.after(endStDate))))
            if (selectionImpactCal != null && ((beginStDate != null && selectionImpactCal.before(beginStDate)) || (
              endStDate != null && selectionImpactCal.after(endStDate))))
              discardSelectionImpact = true;  
          if (!discardSelectionImpact) {
            tempSelection.setImpactDateObject(iDate);
            finalVector.add(tempSelection);
          } 
        } 
      } 
    } 
    return finalVector;
  }
}


/* Location:              D:\Documents\NetBeansProjects\milestone2 Local.war!\WEB-INF\classes\co\\universal\milestone\MCACustomImpactScheduleForPrintSubHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */