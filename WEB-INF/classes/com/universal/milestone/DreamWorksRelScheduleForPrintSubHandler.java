package WEB-INF.classes.com.universal.milestone;

import com.techempower.ComponentLog;
import com.techempower.gemini.Context;
import com.techempower.gemini.GeminiApplication;
import com.universal.milestone.DreamWorksRelScheduleForPrintSubHandler;
import com.universal.milestone.Form;
import com.universal.milestone.MilestoneHelper;
import com.universal.milestone.MilestoneHelper_2;
import com.universal.milestone.Schedule;
import com.universal.milestone.ScheduledTask;
import com.universal.milestone.SecureHandler;
import com.universal.milestone.Selection;
import com.universal.milestone.SelectionManager;
import com.universal.milestone.StatusJSPupdate;
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

public class DreamWorksRelScheduleForPrintSubHandler extends SecureHandler {
  public static final String COMPONENT_CODE = "hDWProd";
  
  public GeminiApplication application;
  
  public ComponentLog log;
  
  public DreamWorksRelScheduleForPrintSubHandler(GeminiApplication application) {
    this.application = application;
    this.log = application.getLog("hDWProd");
  }
  
  public String getDescription() { return "Sub Report"; }
  
  protected static void fillDreamWorksRelScheduleForPrint(XStyleSheet report, Context context) {
    Color SHADED_AREA_COLOR = Color.lightGray;
    int COL_LINE_STYLE = 4097;
    int HEADER_FONT_SIZE = 12;
    int NUM_COLUMNS = 8;
    double ldLineVal = 0.3D;
    ComponentLog log = context.getApplication().getLog("hDWProd");
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
    StatusJSPupdate statusJSPupdate = new StatusJSPupdate(context);
    statusJSPupdate.setInternalCounter(true);
    statusJSPupdate.updateStatus(0, 0, "start_gathering", 0);
    Vector selections = MilestoneHelper.getSelectionsForReport(context);
    statusJSPupdate.updateStatus(0, 0, "start_report", 0);
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
      Hashtable selTable = MilestoneHelper.groupSelectionsByCompanyAndSubconfig(selections);
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
      int numColumns = 8;
      int nextRow = 0;
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
        table_contents.setColWidth(0, 260);
        table_contents.setColWidth(1, 200);
        table_contents.setColWidth(2, 110);
        table_contents.setColWidth(3, 90);
        table_contents.setColWidth(4, 80);
        table_contents.setColWidth(5, 60);
        table_contents.setColWidth(6, 60);
        table_contents.setColWidth(7, 140);
        table_contents.setSpan(nextRow, 0, new Dimension(numColumns, 1));
        table_contents.setObject(nextRow, 0, companyHeaderText);
        table_contents.setRowBorder(nextRow, 0);
        table_contents.setRowBackground(nextRow, Color.black);
        table_contents.setRowForeground(nextRow, Color.white);
        table_contents.setRowFont(nextRow, new Font("Arial", 3, 16));
        hbandType.addTable(table_contents, new Rectangle(0, 0, 800, 30));
        nextRow = 0;
        columnHeaderTable = new DefaultTableLens(1, 8);
        columnHeaderTable.setColWidth(0, 260);
        columnHeaderTable.setColWidth(1, 200);
        columnHeaderTable.setColWidth(2, 95);
        columnHeaderTable.setColWidth(3, 100);
        columnHeaderTable.setColWidth(4, 80);
        columnHeaderTable.setColWidth(5, 60);
        columnHeaderTable.setColWidth(6, 60);
        columnHeaderTable.setColWidth(7, 140);
        columnHeaderTable.setAlignment(nextRow, 0, 16);
        columnHeaderTable.setObject(nextRow, 0, "\nArtist");
        columnHeaderTable.setAlignment(nextRow, 1, 16);
        columnHeaderTable.setObject(nextRow, 1, "\nTitle/B Side");
        columnHeaderTable.setObject(nextRow, 2, "\nUPC/Selection");
        columnHeaderTable.setAlignment(nextRow, 3, 18);
        columnHeaderTable.setObject(nextRow, 3, "\nPrice");
        columnHeaderTable.setAlignment(nextRow, 4, 18);
        columnHeaderTable.setObject(nextRow, 4, "Street\nDate");
        columnHeaderTable.setAlignment(nextRow, 5, 18);
        columnHeaderTable.setObject(nextRow, 5, "Ship\nDate");
        columnHeaderTable.setObject(nextRow, 6, "Impact\nDate");
        columnHeaderTable.setAlignment(nextRow, 6, 18);
        columnHeaderTable.setObject(nextRow, 7, "\nComments");
        columnHeaderTable.setRowFont(nextRow, new Font("Arial", 3, 10));
        columnHeaderTable.setRowBorderColor(nextRow - 1, Color.white);
        for (int k = -1; k < 7; k++)
          columnHeaderTable.setColBorderColor(k, Color.white); 
        columnHeaderTable.setRowBorder(nextRow, 4097);
        columnHeaderTable.setRowBorderColor(nextRow, Color.black);
        hbandType.addTable(columnHeaderTable, new Rectangle(0, 30, 800, 35));
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
            configTableLens = new DefaultTableLens(1, 7);
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
            configTableLens.setSpan(nextRow, 0, new Dimension(7, 1));
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
            for (int i = 0; i < selections.size(); i++) {
              statusJSPupdate.updateStatus(numSelections, i, "start_report", 0);
              Selection sel = (Selection)selections.elementAt(i);
              sel = SelectionManager.getInstance().getSelectionAndSchedule(sel.getSelectionID());
              nextRow = 0;
              subTable = new DefaultTableLens(2, 8);
              subTable.setColWidth(0, 270);
              subTable.setColWidth(1, 240);
              subTable.setColWidth(2, 90);
              subTable.setColWidth(3, 90);
              subTable.setColWidth(4, 140);
              subTable.setColWidth(5, 60);
              subTable.setColWidth(6, 60);
              subTable.setColWidth(7, 140);
              String bSide = (sel.getBSide() != null && !sel.getBSide().trim().equals("")) ? (
                "B Side:  " + sel.getBSide()) : "";
              String upc = sel.getUpc();
              upc = MilestoneHelper_2.getRMSReportFormat(upc, "UPC", sel.getIsDigital());
              if (upc == null || upc.trim().equals("")) {
                upc = SelectionManager.getLookupObjectValue(sel.getPrefixID());
                if (upc == null)
                  upc = ""; 
                if (sel.getSelectionNo() != null)
                  upc = String.valueOf(upc) + sel.getSelectionNo(); 
              } 
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
              Schedule schedule = sel.getSchedule();
              Vector tasks = (schedule != null) ? schedule.getTasks() : null;
              ScheduledTask task = null;
              String SHIP = "";
              if (tasks != null)
                for (int j = 0; j < tasks.size(); j++) {
                  task = (ScheduledTask)tasks.get(j);
                  if (task != null && task.getDueDate() != null) {
                    String dueDate = MilestoneHelper.getFormatedDate(task.getDueDate());
                    String taskAbbrev = MilestoneHelper.getTaskAbbreivationNameById(task.getTaskAbbreviationID());
                    if (taskAbbrev.equalsIgnoreCase("PSD")) {
                      SHIP = dueDate;
                      break;
                    } 
                    task = null;
                  } 
                }  
              subTable.setRowInsets(nextRow, new Insets(0, 0, 0, 0));
              subTable.setRowHeight(nextRow, 11);
              subTable.setObject(nextRow, 0, sel.getFlArtist());
              subTable.setObject(nextRow, 1, sel.getTitle());
              subTable.setObject(nextRow, 2, upc);
              subTable.setObject(nextRow, 3, "$" + price);
              subTable.setColLineWrap(4, false);
              subTable.setObject(nextRow, 4, streetDate);
              subTable.setAlignment(1);
              subTable.setObject(nextRow, 6, MilestoneHelper.getFormatedDate(sel.getImpactDate()));
              subTable.setObject(nextRow, 5, SHIP);
              subTable.setObject(nextRow, 7, comment);
              subTable.setColAlignment(0, 9);
              subTable.setSpan(nextRow, 0, new Dimension(1, 2));
              subTable.setColAlignment(1, 33);
              subTable.setColAlignment(2, 9);
              subTable.setSpan(nextRow, 2, new Dimension(1, 2));
              subTable.setColAlignment(3, 12);
              subTable.setSpan(nextRow, 3, new Dimension(1, 2));
              subTable.setColAlignment(4, 10);
              subTable.setSpan(nextRow, 4, new Dimension(1, 2));
              subTable.setColAlignment(6, 10);
              subTable.setSpan(nextRow, 6, new Dimension(1, 2));
              subTable.setColAlignment(5, 9);
              subTable.setSpan(nextRow, 5, new Dimension(1, 2));
              subTable.setColAlignment(7, 9);
              subTable.setSpan(nextRow, 7, new Dimension(1, 2));
              subTable.setRowBorder(nextRow, 1, 0);
              subTable.setRowBorder(nextRow - 1, 0);
              subTable.setRowFont(nextRow, new Font("Arial", 0, 8));
              nextRow++;
              subTable.setAlignment(nextRow, 1, 9);
              String[] checkStrings = { comment.trim(), sel.getFlArtist().trim(), sel.getTitle().trim() };
              int[] checkStringsLength = { 20, 40, 60 };
              if (bSide.equals("") && comment.equals(""))
                subTable.setRowHeight(nextRow, 1); 
              int addExtraLines = MilestoneHelper.lineCount(checkStrings, checkStringsLength);
              if (addExtraLines > 0 && addExtraLines < 2)
                addExtraLines--; 
              for (int z = 0; z < addExtraLines; z++)
                bSide = String.valueOf(bSide) + "\n"; 
              for (int k = -1; k < 7; k++)
                subTable.setColBorder(k, 0); 
              subTable.setObject(nextRow, 0, "");
              subTable.setObject(nextRow, 1, bSide);
              subTable.setObject(nextRow, 2, "");
              subTable.setObject(nextRow, 3, "");
              subTable.setObject(nextRow, 4, "");
              subTable.setObject(nextRow, 5, "");
              subTable.setObject(nextRow, 6, "");
              subTable.setObject(nextRow, 7, "");
              subTable.setRowFont(nextRow, new Font("Arial", 2, 8));
              subTable.setColAlignment(1, 9);
              subTable.setRowBorderColor(nextRow, Color.lightGray);
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
      System.out.println(">>>>>>>>fillDreamWorksRelScheduleForPrint(): exception: " + e);
    } 
    statusJSPupdate = null;
  }
}


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\DreamWorksRelScheduleForPrintSubHandler.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */