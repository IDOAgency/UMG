package WEB-INF.classes.com.universal.milestone;

import com.techempower.ComponentLog;
import com.techempower.gemini.Context;
import com.techempower.gemini.GeminiApplication;
import com.universal.milestone.ClassicsRelSchdForPrintSubHandler;
import com.universal.milestone.Form;
import com.universal.milestone.MilestoneHelper;
import com.universal.milestone.MilestoneHelper_2;
import com.universal.milestone.MonthYearComparator;
import com.universal.milestone.SecureHandler;
import com.universal.milestone.Selection;
import com.universal.milestone.SelectionManager;
import com.universal.milestone.StatusJSPupdate;
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
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

public class ClassicsRelSchdForPrintSubHandler extends SecureHandler {
  public static final String COMPONENT_CODE = "hCRsch";
  
  public GeminiApplication application;
  
  public ComponentLog log;
  
  public ClassicsRelSchdForPrintSubHandler(GeminiApplication application) {
    this.application = application;
    this.log = application.getLog("hCRsch");
  }
  
  public String getDescription() { return "Sub Report"; }
  
  protected static void fillClassicsRelSchdForPrint(XStyleSheet report, Context context) {
    int COL_LINE_STYLE = 4097;
    int HEADER_FONT_SIZE = 12;
    double ldLineVal = 0.3D;
    StatusJSPupdate aStatusUpdate = new StatusJSPupdate(context);
    aStatusUpdate.updateStatus(0, 0, "start_gathering", 0);
    Vector selections1 = MilestoneHelper.getSelectionsForReport(context);
    aStatusUpdate.updateStatus(0, 0, "start_report", 10);
    aStatusUpdate.setInternalCounter(true);
    int DATA_FONT_SIZE = 7;
    int SMALL_HEADER_FONT_SIZE = 10;
    int NUM_COLUMNS = 15;
    Color SHADED_AREA_COLOR = Color.lightGray;
    boolean productCatagoryFlag = false;
    SectionBand hbandType = new SectionBand(report);
    SectionBand hbandCategory = new SectionBand(report);
    SectionBand hbandDate = new SectionBand(report);
    SectionBand hbandDiv = new SectionBand(report);
    SectionBand hbandProd = new SectionBand(report);
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
    Hashtable selTable = MilestoneHelper.groupSelectionsByMonthAndDayAndDivision(selections1);
    try {
      DefaultTableLens table_contents = null;
      DefaultTableLens rowCountTable = null;
      DefaultTableLens columnHeaderTable = null;
      DefaultTableLens subTable = null;
      DefaultTableLens monthTableLens = null;
      DefaultTableLens dateTableLens = null;
      DefaultTableLens divTableLens = null;
      DefaultTableLens prodTableLens = null;
      rowCountTable = new DefaultTableLens(2, 10000);
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
      Calendar beginEffectiveDate = (reportForm.getStringValue("beginEffectiveDate") != null && reportForm.getStringValue("beginEffectiveDate").length() > 0) ? MilestoneHelper.getDate(reportForm.getStringValue("beginEffectiveDate")) : null;
      if (beginEffectiveDate != null) {
        beginEffectiveDate.set(11, 0);
        beginEffectiveDate.set(12, 0);
        beginEffectiveDate.set(13, 0);
        beginEffectiveDate.set(14, 0);
      } 
      Calendar endEffectiveDate = (reportForm.getStringValue("endEffectiveDate") != null && reportForm.getStringValue("endEffectiveDate").length() > 0) ? MilestoneHelper.getDate(reportForm.getStringValue("endEffectiveDate")) : null;
      if (endEffectiveDate != null) {
        endEffectiveDate.set(11, 23);
        endEffectiveDate.set(12, 59);
        endEffectiveDate.set(13, 59);
        endEffectiveDate.set(14, 999);
      } 
      int numColumns = 10;
      hbandType = new SectionBand(report);
      hbandType.setHeight(0.95F);
      hbandType.setShrinkToFit(true);
      hbandType.setVisible(true);
      table_contents = new DefaultTableLens(1, numColumns);
      table_contents.setHeaderRowCount(0);
      table_contents.setColWidth(0, 50);
      table_contents.setColWidth(1, 130);
      table_contents.setColWidth(2, 130);
      table_contents.setColWidth(3, 75);
      table_contents.setColWidth(4, 75);
      table_contents.setColWidth(5, 70);
      table_contents.setColWidth(6, 30);
      table_contents.setColWidth(7, 70);
      table_contents.setColWidth(8, 30);
      table_contents.setColWidth(9, 110);
      int nextRow = 0;
      nextRow = 0;
      columnHeaderTable = new DefaultTableLens(1, numColumns);
      columnHeaderTable.setHeaderRowCount(0);
      columnHeaderTable.setColWidth(0, 50);
      columnHeaderTable.setColWidth(1, 130);
      columnHeaderTable.setColWidth(2, 130);
      columnHeaderTable.setColWidth(3, 75);
      columnHeaderTable.setColWidth(4, 75);
      columnHeaderTable.setColWidth(5, 70);
      columnHeaderTable.setColWidth(6, 30);
      columnHeaderTable.setColWidth(7, 70);
      columnHeaderTable.setColWidth(8, 30);
      columnHeaderTable.setColWidth(9, 110);
      columnHeaderTable.setColBorder(0);
      columnHeaderTable.setRowBorder(nextRow - 1, 0);
      columnHeaderTable.setRowBorder(nextRow, 4097);
      columnHeaderTable.setRowBorderColor(nextRow, Color.black);
      columnHeaderTable.setAlignment(nextRow, 0, 33);
      columnHeaderTable.setAlignment(nextRow, 1, 33);
      columnHeaderTable.setAlignment(nextRow, 2, 33);
      columnHeaderTable.setAlignment(nextRow, 3, 33);
      columnHeaderTable.setAlignment(nextRow, 4, 33);
      columnHeaderTable.setAlignment(nextRow, 5, 33);
      columnHeaderTable.setAlignment(nextRow, 6, 33);
      columnHeaderTable.setAlignment(nextRow, 7, 34);
      columnHeaderTable.setAlignment(nextRow, 8, 33);
      columnHeaderTable.setAlignment(nextRow, 9, 34);
      columnHeaderTable.setObject(nextRow, 0, "Release\nDate");
      columnHeaderTable.setObject(nextRow, 1, "Artist");
      columnHeaderTable.setObject(nextRow, 2, "Title");
      columnHeaderTable.setObject(nextRow, 3, "LPN");
      columnHeaderTable.setObject(nextRow, 4, "UPC");
      columnHeaderTable.setObject(nextRow, 5, "Label");
      columnHeaderTable.setObject(nextRow, 6, "Retail\nCode");
      columnHeaderTable.setObject(nextRow, 7, "Other\nContact");
      columnHeaderTable.setObject(nextRow, 8, "Int'l\nDate");
      columnHeaderTable.setObject(nextRow, 9, "Comments");
      columnHeaderTable.setRowFont(nextRow, new Font("Arial", 3, 10));
      columnHeaderTable.setRowBackground(nextRow, Color.white);
      columnHeaderTable.setRowForeground(nextRow, Color.black);
      hbandType.addTable(columnHeaderTable, new Rectangle(0, 10, 800, 35));
      hbandType.setBottomBorder(0);
      if (selTable != null) {
        Enumeration months = selTable.keys();
        Vector monthVector = new Vector();
        while (months.hasMoreElements())
          monthVector.add((String)months.nextElement()); 
        Object[] monthArray = (Object[])null;
        monthArray = monthVector.toArray();
        Arrays.sort(monthArray, new MonthYearComparator());
        int statusBarCount = 0;
        for (int n = 0; n < monthArray.length; n++) {
          String monthName = (String)monthArray[n];
          String monthNameString = monthName;
          try {
            monthNameString = MONTHS[Integer.parseInt(monthName.substring(0, 2)) - 1];
          } catch (Exception e) {
            if (monthName.equals("13")) {
              monthNameString = "TBS";
            } else if (monthName.equals("26")) {
              monthNameString = "ITW";
            } else {
              monthNameString = "No street date";
            } 
          } 
          Hashtable dateTable = (Hashtable)selTable.get(monthName);
          if (dateTable != null) {
            Enumeration dateSort = dateTable.keys();
            Vector dateVector = new Vector();
            while (dateSort.hasMoreElements())
              dateVector.add((String)dateSort.nextElement()); 
            Object[] dateArray = (Object[])null;
            dateArray = dateVector.toArray();
            Arrays.sort(dateArray, new StringDateComparator());
            for (int dIndex = 0; dIndex < dateArray.length; dIndex++) {
              String dateName = (String)dateArray[dIndex];
              Hashtable divTable = (Hashtable)dateTable.get(dateName);
              if (divTable != null) {
                Enumeration divSort = divTable.keys();
                Vector divVector = new Vector();
                while (divSort.hasMoreElements())
                  divVector.add((String)divSort.nextElement()); 
                Object[] divArray = (Object[])null;
                divArray = divVector.toArray();
                Arrays.sort(divArray, new StringDateComparator());
                for (int divIndex = 0; divIndex < divArray.length; divIndex++) {
                  String divName = (String)divArray[divIndex];
                  selections1 = (Vector)divTable.get(divName);
                  statusBarCount += selections1.size();
                } 
              } 
            } 
          } 
        } 
        for (int x = 0; x < monthArray.length; x++) {
          String monthName = (String)monthArray[x];
          String monthNameString = monthName;
          try {
            monthNameString = MONTHS[Integer.parseInt(monthName.substring(0, 2)) - 1];
          } catch (Exception e) {
            if (monthName.equals("13")) {
              monthNameString = "TBS";
            } else if (monthName.equals("26")) {
              monthNameString = "ITW";
            } else {
              monthNameString = "No street date";
            } 
          } 
          monthTableLens = new DefaultTableLens(1, numColumns);
          hbandCategory = new SectionBand(report);
          hbandCategory.setHeight(0.25F);
          hbandCategory.setShrinkToFit(true);
          hbandCategory.setVisible(true);
          hbandCategory.setBottomBorder(0);
          hbandCategory.setLeftBorder(0);
          hbandCategory.setRightBorder(0);
          hbandCategory.setTopBorder(0);
          nextRow = 0;
          monthTableLens.setSpan(nextRow, 0, new Dimension(numColumns, 1));
          monthTableLens.setObject(nextRow, 0, monthNameString);
          monthTableLens.setRowHeight(nextRow, 14);
          monthTableLens.setRowFont(nextRow, new Font("Arial", 1, 12));
          monthTableLens.setRowBackground(nextRow, Color.black);
          monthTableLens.setRowForeground(nextRow, Color.white);
          monthTableLens.setRowBorderColor(nextRow, Color.white);
          monthTableLens.setRowBorderColor(nextRow - 1, Color.white);
          monthTableLens.setColBorderColor(nextRow, -1, Color.white);
          monthTableLens.setColBorderColor(nextRow, 0, Color.white);
          monthTableLens.setColBorderColor(nextRow, 14, Color.white);
          hbandCategory.addTable(monthTableLens, new Rectangle(800, 800));
          footer.setVisible(true);
          footer.setHeight(0.1F);
          footer.setShrinkToFit(false);
          footer.setBottomBorder(0);
          group = new DefaultSectionLens(null, group, hbandCategory);
          group = new DefaultSectionLens(null, group, spacer);
          Hashtable dateTable = (Hashtable)selTable.get(monthName);
          if (dateTable != null) {
            Enumeration dateSort = dateTable.keys();
            Vector dateVector = new Vector();
            while (dateSort.hasMoreElements())
              dateVector.add((String)dateSort.nextElement()); 
            Object[] dateArray = (Object[])null;
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
              hbandDate.setHeight(0.25F);
              hbandDate.setShrinkToFit(true);
              hbandDate.setVisible(true);
              hbandDate.setBottomBorder(0);
              hbandDate.setLeftBorder(0);
              hbandDate.setRightBorder(0);
              hbandDate.setTopBorder(0);
              dateTableLens = new DefaultTableLens(1, 15);
              nextRow = 0;
              dateTableLens.setSpan(nextRow, 0, new Dimension(15, 1));
              dateTableLens.setObject(nextRow, 0, dateNameText);
              dateTableLens.setRowHeight(nextRow, 14);
              dateTableLens.setRowFont(nextRow, new Font("Arial", 1, 12));
              dateTableLens.setRowForeground(nextRow, Color.black);
              dateTableLens.setRowBorderColor(nextRow, Color.white);
              dateTableLens.setRowBorderColor(nextRow - 1, Color.white);
              dateTableLens.setColBorderColor(nextRow, -1, Color.white);
              dateTableLens.setColBorderColor(nextRow, 0, Color.white);
              dateTableLens.setColBorderColor(nextRow, 14, Color.white);
              dateTableLens.setRowBackground(nextRow, Color.lightGray);
              hbandDate.addTable(dateTableLens, new Rectangle(800, 200));
              hbandDate.setBottomBorder(0);
              group = new DefaultSectionLens(null, group, hbandDate);
              Hashtable divTable = (Hashtable)dateTable.get(dateName);
              if (divTable != null) {
                Enumeration divSort = divTable.keys();
                Vector divVector = new Vector();
                while (divSort.hasMoreElements())
                  divVector.add((String)divSort.nextElement()); 
                Object[] divArray = (Object[])null;
                divArray = divVector.toArray();
                Arrays.sort(divArray, new StringDateComparator());
                for (int divIndex = 0; divIndex < divArray.length; divIndex++) {
                  String divName = (String)divArray[divIndex];
                  hbandDiv = new SectionBand(report);
                  hbandDiv.setHeight(0.25F);
                  hbandDiv.setShrinkToFit(true);
                  hbandDiv.setVisible(true);
                  hbandDiv.setBottomBorder(0);
                  hbandDiv.setLeftBorder(0);
                  hbandDiv.setRightBorder(0);
                  hbandDiv.setTopBorder(0);
                  divTableLens = new DefaultTableLens(1, 15);
                  selections1 = (Vector)divTable.get(divName);
                  nextRow = 0;
                  divTableLens.setSpan(nextRow, 0, new Dimension(15, 1));
                  if (selections1.size() > 0) {
                    divTableLens.setRowHeight(nextRow, 14);
                    divTableLens.setRowFont(nextRow, new Font("Arial", 1, 12));
                    divTableLens.setObject(nextRow, 0, divArray[divIndex]);
                    divTableLens.setRowForeground(nextRow, Color.black);
                    divTableLens.setRowBorderColor(nextRow, Color.white);
                    divTableLens.setRowBorderColor(nextRow - 1, Color.white);
                    divTableLens.setColBorderColor(nextRow, -1, Color.white);
                    divTableLens.setColBorderColor(nextRow, 0, Color.white);
                    divTableLens.setColBorderColor(nextRow, 14, Color.white);
                    divTableLens.setRowBackground(nextRow, Color.white);
                    hbandDiv.addTable(divTableLens, new Rectangle(800, 200));
                    hbandDiv.setBottomBorder(0);
                    group = new DefaultSectionLens(null, group, hbandDiv);
                    group = new DefaultSectionLens(null, group, spacer);
                  } else {
                    divTableLens.setObject(nextRow, 0, "");
                  } 
                  if (selections1 == null)
                    selections1 = new Vector(); 
                  MilestoneHelper.setSelectionSorting(selections1, 12);
                  Collections.sort(selections1);
                  MilestoneHelper.setSelectionSorting(selections1, 4);
                  Collections.sort(selections1);
                  MilestoneHelper.setSelectionSorting(selections1, 22);
                  Collections.sort(selections1);
                  MilestoneHelper.setSelectionSorting(selections1, 9);
                  Collections.sort(selections1);
                  MilestoneHelper.setSelectionSorting(selections1, 7);
                  Collections.sort(selections1);
                  MilestoneHelper.setSelectionSorting(selections1, 1);
                  Collections.sort(selections1);
                  MilestoneHelper.setSelectionSorting(selections1, 8);
                  Collections.sort(selections1);
                  MilestoneHelper.setSelectionSorting(selections1, 6);
                  Collections.sort(selections1);
                  selections1 = reorderByProductCategory(selections1);
                  productCatagoryFlag = true;
                  for (int i = 0; i < selections1.size(); i++) {
                    aStatusUpdate.updateStatus(statusBarCount, i, "start_report", 0);
                    Selection sel = (Selection)selections1.elementAt(i);
                    String categoryString = (sel.getProductCategory() != null) ? sel.getProductCategory().getName() : "";
                    if (categoryString.equals("Catalog/Reissue") && productCatagoryFlag) {
                      productCatagoryFlag = false;
                      hbandProd = new SectionBand(report);
                      hbandProd.setHeight(0.25F);
                      hbandProd.setShrinkToFit(true);
                      hbandProd.setVisible(true);
                      hbandProd.setBottomBorder(0);
                      hbandProd.setLeftBorder(0);
                      hbandProd.setRightBorder(0);
                      hbandProd.setTopBorder(0);
                      prodTableLens = new DefaultTableLens(1, 15);
                      nextRow = 0;
                      prodTableLens.setSpan(nextRow, 0, new Dimension(15, 1));
                      prodTableLens.setRowHeight(nextRow, 14);
                      prodTableLens.setRowFont(nextRow, new Font("Arial", 1, 12));
                      prodTableLens.setObject(nextRow, 0, sel.getProductCategory().getName());
                      prodTableLens.setRowForeground(nextRow, Color.black);
                      prodTableLens.setRowBorderColor(nextRow, Color.white);
                      prodTableLens.setRowBorderColor(nextRow - 1, Color.white);
                      prodTableLens.setColBorderColor(nextRow, -1, Color.white);
                      prodTableLens.setColBorderColor(nextRow, 0, Color.white);
                      prodTableLens.setColBorderColor(nextRow, 14, Color.white);
                      prodTableLens.setRowBackground(nextRow, Color.white);
                      hbandProd.addTable(prodTableLens, new Rectangle(800, 200));
                      hbandProd.setBottomBorder(0);
                      group = new DefaultSectionLens(null, group, spacer);
                      group = new DefaultSectionLens(null, group, hbandProd);
                      group = new DefaultSectionLens(null, group, spacer);
                    } 
                    nextRow = 0;
                    subTable = new DefaultTableLens(1, numColumns);
                    subTable.setColWidth(0, 50);
                    subTable.setColWidth(1, 130);
                    subTable.setColWidth(2, 130);
                    subTable.setColWidth(3, 75);
                    subTable.setColWidth(4, 75);
                    subTable.setColWidth(5, 70);
                    subTable.setColWidth(6, 30);
                    subTable.setColWidth(7, 70);
                    subTable.setColWidth(8, 30);
                    subTable.setColWidth(9, 110);
                    String labelName = "";
                    if (sel.getImprint() != null)
                      labelName = sel.getImprint(); 
                    String retail = "";
                    if (sel.getPriceCode() != null && sel.getPriceCode().getRetailCode() != null)
                      retail = sel.getPriceCode().getRetailCode(); 
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
                    String artist = "";
                    if (sel.getFlArtist() != null)
                      artist = sel.getFlArtist(); 
                    String title = "";
                    if (sel.getTitle() != null)
                      title = sel.getTitle(); 
                    String otherContact = "";
                    if (sel.getOtherContact() != null)
                      otherContact = sel.getOtherContact(); 
                    String[] checkStrings = { artist, title, otherContact };
                    int[] checkStringLengths = { 25, 25, 20 };
                    int extraLines = MilestoneHelper.lineCount(checkStrings, checkStringLengths);
                    if (extraLines > 3)
                      extraLines -= 4; 
                    subTable.setObject(nextRow, 8, intlDate);
                    subTable.setAlignment(nextRow, 8, 9);
                    Calendar streetUpdate = sel.getLastStreetUpdateDate();
                    Calendar enteredOn = sel.getOriginDate();
                    boolean asteric = false;
                    if (streetUpdate != null) {
                      if (beginEffectiveDate != null) {
                        if (streetUpdate.after(beginEffectiveDate) || streetUpdate.equals(beginEffectiveDate))
                          asteric = true; 
                      } else if (beginStDate != null) {
                        if (streetUpdate.after(beginStDate) || streetUpdate.equals(beginStDate))
                          asteric = true; 
                      } 
                      if (endEffectiveDate != null) {
                        if ((beginEffectiveDate != null || beginStDate != null) && asteric && (
                          streetUpdate.before(endEffectiveDate) || streetUpdate.equals(endEffectiveDate))) {
                          asteric = true;
                        } else if (beginEffectiveDate == null && beginStDate == null && (streetUpdate.before(endEffectiveDate) || streetUpdate.equals(endEffectiveDate))) {
                          asteric = true;
                        } else {
                          asteric = false;
                        } 
                      } else if (endStDate != null) {
                        if ((beginEffectiveDate != null || beginStDate != null) && asteric && (
                          streetUpdate.before(endStDate) || streetUpdate.equals(endStDate))) {
                          asteric = true;
                        } else if (beginEffectiveDate == null && beginStDate == null && (streetUpdate.before(endStDate) || streetUpdate.equals(endStDate))) {
                          asteric = true;
                        } else {
                          asteric = false;
                        } 
                      } 
                    } 
                    if (enteredOn != null && streetUpdate != null && enteredOn.equals(streetUpdate))
                      asteric = false; 
                    if (asteric)
                      streetDate = String.valueOf(streetDate) + " *"; 
                    boolean lightGrayBG = false;
                    if (enteredOn != null) {
                      if (beginEffectiveDate != null) {
                        if (enteredOn.after(beginEffectiveDate) || enteredOn.equals(beginEffectiveDate))
                          lightGrayBG = true; 
                      } else if (beginStDate != null) {
                        if (enteredOn.after(beginStDate) || enteredOn.equals(beginStDate))
                          lightGrayBG = true; 
                      } 
                      if (endEffectiveDate != null) {
                        if ((beginEffectiveDate != null || beginStDate != null) && lightGrayBG && (
                          enteredOn.before(endEffectiveDate) || enteredOn.equals(endEffectiveDate))) {
                          lightGrayBG = true;
                        } else if (beginEffectiveDate == null && beginStDate == null && (enteredOn.before(endEffectiveDate) || enteredOn.equals(endEffectiveDate))) {
                          lightGrayBG = true;
                        } else {
                          lightGrayBG = false;
                        } 
                      } else if (endStDate != null) {
                        if ((beginEffectiveDate != null || beginStDate != null) && lightGrayBG && (
                          enteredOn.before(endStDate) || enteredOn.equals(endStDate))) {
                          lightGrayBG = true;
                        } else if (beginEffectiveDate == null && beginStDate == null && (enteredOn.before(endStDate) || enteredOn.equals(endStDate))) {
                          lightGrayBG = true;
                        } else {
                          lightGrayBG = false;
                        } 
                      } 
                    } 
                    if (lightGrayBG)
                      subTable.setBackground(nextRow, 0, Color.lightGray); 
                    subTable.setObject(nextRow, 0, streetDate);
                    subTable.setAlignment(nextRow, 0, 9);
                    subTable.setObject(nextRow, 1, sel.getFlArtist().trim());
                    subTable.setAlignment(nextRow, 1, 9);
                    subTable.setObject(nextRow, 2, sel.getTitle());
                    subTable.setAlignment(nextRow, 2, 9);
                    subTable.setObject(nextRow, 5, sel.getImprint());
                    subTable.setAlignment(nextRow, 5, 9);
                    subTable.setObject(nextRow, 6, retail);
                    subTable.setAlignment(nextRow, 6, 10);
                    subTable.setObject(nextRow, 7, sel.getOtherContact());
                    subTable.setAlignment(nextRow, 7, 9);
                    String selectionNumber = "";
                    if (SelectionManager.getLookupObjectValue(sel.getPrefixID()).equals("")) {
                      selectionNumber = sel.getSelectionNo();
                    } else {
                      selectionNumber = String.valueOf(SelectionManager.getLookupObjectValue(sel.getPrefixID())) + sel.getSelectionNo();
                    } 
                    subTable.setObject(nextRow, 3, selectionNumber);
                    subTable.setAlignment(nextRow, 3, 9);
                    String upc = sel.getUpc();
                    upc = MilestoneHelper_2.getRMSReportFormat(upc, "UPC", sel.getIsDigital());
                    subTable.setObject(nextRow, 4, !upc.equals("") ? upc : "");
                    subTable.setAlignment(nextRow, 4, 9);
                    subTable.setObject(nextRow, 9, sel.getSelectionComments());
                    subTable.setAlignment(nextRow, 9, 9);
                    subTable.setColBorder(0);
                    subTable.setRowBorderColor(nextRow, Color.white);
                    subTable.setRowBorder(nextRow - 1, 4097);
                    subTable.setRowBorder(nextRow, 4097);
                    subTable.setRowBorderColor(nextRow, Color.lightGray);
                    subTable.setRowBorderColor(nextRow - 1, Color.lightGray);
                    subTable.setColBorder(4097);
                    subTable.setColBorder(nextRow - 1, 0);
                    subTable.setColBorder(nextRow, -1, 4097);
                    subTable.setColBorder(nextRow, 4097);
                    subTable.setColBorderColor(nextRow, Color.lightGray);
                    subTable.setColBorderColor(Color.lightGray);
                    subTable.setRowFont(nextRow, new Font("Arial", 0, 8));
                    body = new SectionBand(report);
                    double lfLineCount = 1.5D;
                    if (extraLines > 0 || sel.getTitle().length() > 20) {
                      body.setHeight(1.5F);
                    } else {
                      body.setHeight(1.0F);
                    } 
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
            } 
          } 
        } 
      } 
      group = new DefaultSectionLens(hbandType, group, null);
      report.addSection(group, rowCountTable);
      group = null;
    } catch (Exception e) {
      System.out.println(">>>>>>>>ClassicsRelSchdForPrintSubHandler(): exception: " + e);
    } 
  }
  
  private static Vector reorderByProductCategory(Vector initialSelections) {
    Vector selectionsThatAreNotCatalogReissue = new Vector();
    Vector selectionsThatAreCatalogReissue = new Vector();
    for (int counter = 0; counter < initialSelections.size(); counter++) {
      Selection sel = (Selection)initialSelections.elementAt(counter);
      String categoryString = (sel.getProductCategory() != null) ? sel.getProductCategory().getName() : "";
      if (categoryString.equals("Catalog/Reissue")) {
        selectionsThatAreCatalogReissue.add(sel);
      } else {
        selectionsThatAreNotCatalogReissue.add(sel);
      } 
    } 
    Vector finalSelections = new Vector();
    for (int count = 0; count < selectionsThatAreNotCatalogReissue.size(); count++)
      finalSelections.addElement(selectionsThatAreNotCatalogReissue.elementAt(count)); 
    for (int count2 = 0; count2 < selectionsThatAreCatalogReissue.size(); count2++)
      finalSelections.addElement(selectionsThatAreCatalogReissue.elementAt(count2)); 
    return finalSelections;
  }
}


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\ClassicsRelSchdForPrintSubHandler.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */