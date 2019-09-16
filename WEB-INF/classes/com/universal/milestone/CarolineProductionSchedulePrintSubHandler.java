package WEB-INF.classes.com.universal.milestone;

import com.techempower.ComponentLog;
import com.techempower.gemini.Context;
import com.techempower.gemini.GeminiApplication;
import com.universal.milestone.CarolineProductionSchedulePrintSubHandler;
import com.universal.milestone.Form;
import com.universal.milestone.MilestoneHelper;
import com.universal.milestone.MilestoneHelper_2;
import com.universal.milestone.Plant;
import com.universal.milestone.Schedule;
import com.universal.milestone.ScheduledTask;
import com.universal.milestone.SecureHandler;
import com.universal.milestone.Selection;
import com.universal.milestone.SelectionManager;
import com.universal.milestone.StatusJSPupdate;
import com.universal.milestone.TemplateManager;
import inetsoft.report.SectionBand;
import inetsoft.report.XStyleSheet;
import inetsoft.report.lens.DefaultSectionLens;
import inetsoft.report.lens.DefaultTableLens;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.Rectangle;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;
import java.util.Vector;

public class CarolineProductionSchedulePrintSubHandler extends SecureHandler {
  public static final String COMPONENT_CODE = "hCapProd";
  
  public GeminiApplication application;
  
  public ComponentLog log;
  
  public CarolineProductionSchedulePrintSubHandler(GeminiApplication application) {
    this.application = application;
    this.log = application.getLog("hCapProd");
  }
  
  public String getDescription() { return "Sub Report"; }
  
  protected static void fillCarolineProductionUpdateForPrint(XStyleSheet report, Context context) {
    int COL_LINE_STYLE = 4097;
    int HEADER_FONT_SIZE = 12;
    double ldLineVal = 0.3D;
    ComponentLog log = context.getApplication().getLog("hCapProd");
    StatusJSPupdate statusJSPupdate = new StatusJSPupdate(context);
    statusJSPupdate.updateStatus(0, 0, "start_gathering", 0);
    Vector selections = MilestoneHelper.getSelectionsForReport(context);
    System.out.println("--- update status bar for building report --- ");
    statusJSPupdate.updateStatus(0, 0, "start_report", 0);
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
      SectionBand hbandType = new SectionBand(report);
      SectionBand hbandCategory = new SectionBand(report);
      SectionBand body = new SectionBand(report);
      SectionBand footer = new SectionBand(report);
      DefaultSectionLens group = null;
      DefaultTableLens table_contents = null;
      DefaultTableLens rowCountTable = null;
      DefaultTableLens columnHeaderTable = null;
      DefaultTableLens subTable = null;
      int numSelections = selections.size() * 2;
      int numRows = numSelections + 5;
      rowCountTable = new DefaultTableLens(2, 10000);
      table_contents = new DefaultTableLens(1, 14);
      int nextRow = 0;
      hbandType = new SectionBand(report);
      hbandType.setHeight(0.95F);
      hbandType.setShrinkToFit(true);
      hbandType.setVisible(true);
      hbandCategory = new SectionBand(report);
      hbandCategory.setHeight(2.0F);
      hbandCategory.setShrinkToFit(true);
      hbandCategory.setVisible(true);
      hbandCategory.setBottomBorder(0);
      hbandCategory.setLeftBorder(0);
      hbandCategory.setRightBorder(0);
      hbandCategory.setTopBorder(0);
      table_contents.setHeaderRowCount(0);
      table_contents.setColWidth(0, 95);
      table_contents.setColWidth(1, 225);
      table_contents.setColWidth(2, 140);
      table_contents.setColWidth(3, 130);
      table_contents.setColWidth(4, 70);
      table_contents.setColWidth(5, 70);
      table_contents.setColWidth(6, 70);
      table_contents.setColWidth(7, 70);
      table_contents.setColWidth(8, 70);
      table_contents.setColWidth(9, 70);
      table_contents.setColWidth(10, 70);
      table_contents.setColWidth(11, 70);
      table_contents.setColWidth(12, 80);
      table_contents.setColWidth(13, 180);
      table_contents.setRowBorderColor(nextRow, Color.lightGray);
      table_contents.setRowBorder(4097);
      table_contents.setColBorder(4097);
      columnHeaderTable = new DefaultTableLens(1, 14);
      nextRow = 0;
      columnHeaderTable.setColWidth(0, 95);
      columnHeaderTable.setColWidth(1, 225);
      columnHeaderTable.setColWidth(2, 140);
      columnHeaderTable.setColWidth(3, 120);
      columnHeaderTable.setColWidth(4, 80);
      columnHeaderTable.setColWidth(5, 70);
      columnHeaderTable.setColWidth(6, 70);
      columnHeaderTable.setColWidth(7, 70);
      columnHeaderTable.setColWidth(8, 70);
      columnHeaderTable.setColWidth(9, 70);
      columnHeaderTable.setColWidth(10, 70);
      columnHeaderTable.setColWidth(11, 70);
      columnHeaderTable.setColWidth(12, 90);
      columnHeaderTable.setColWidth(13, 180);
      columnHeaderTable.setRowAlignment(nextRow, 34);
      columnHeaderTable.setObject(nextRow, 0, "Release\nDate");
      columnHeaderTable.setObject(nextRow, 1, "UPC\nLocal Prod #\nArtist\nTitle");
      columnHeaderTable.setObject(nextRow, 2, "Label");
      columnHeaderTable.setObject(nextRow, 3, "Format");
      columnHeaderTable.setObject(nextRow, 4, "Pricing");
      columnHeaderTable.setObject(nextRow, 5, "Label Copy\nDue");
      columnHeaderTable.setObject(nextRow, 6, "FGs Due");
      columnHeaderTable.setObject(nextRow, 7, "Audio to\nManuf.");
      columnHeaderTable.setObject(nextRow, 8, "Art to\nPrinter");
      columnHeaderTable.setObject(nextRow, 9, "BOM");
      columnHeaderTable.setObject(nextRow, 11, "Qty\nDone");
      columnHeaderTable.setObject(nextRow, 10, "IO");
      columnHeaderTable.setObject(nextRow, 12, "Production\nStatus");
      columnHeaderTable.setObject(nextRow, 13, "Comments/Pkg Info");
      columnHeaderTable.setRowFont(nextRow, new Font("Arial", 1, 8));
      columnHeaderTable.setRowBorderColor(nextRow, Color.lightGray);
      columnHeaderTable.setRowBorderColor(nextRow - 1, Color.lightGray);
      columnHeaderTable.setColBorderColor(nextRow, -1, Color.lightGray);
      columnHeaderTable.setColBorderColor(nextRow, 0, Color.lightGray);
      columnHeaderTable.setColBorderColor(nextRow, 1, Color.lightGray);
      columnHeaderTable.setColBorderColor(nextRow, 2, Color.lightGray);
      columnHeaderTable.setColBorderColor(nextRow, 3, Color.lightGray);
      columnHeaderTable.setColBorderColor(nextRow, 4, Color.lightGray);
      columnHeaderTable.setColBorderColor(nextRow, 5, Color.lightGray);
      columnHeaderTable.setColBorderColor(nextRow, 6, Color.lightGray);
      columnHeaderTable.setColBorderColor(nextRow, 7, Color.lightGray);
      columnHeaderTable.setColBorderColor(nextRow, 8, Color.lightGray);
      columnHeaderTable.setColBorderColor(nextRow, 9, Color.lightGray);
      columnHeaderTable.setColBorderColor(nextRow, 10, Color.lightGray);
      columnHeaderTable.setColBorderColor(nextRow, 11, Color.lightGray);
      columnHeaderTable.setColBorderColor(nextRow, 12, Color.lightGray);
      columnHeaderTable.setColBorderColor(nextRow, 13, Color.lightGray);
      columnHeaderTable.setRowBorderColor(nextRow, Color.lightGray);
      nextRow++;
      hbandType.addTable(columnHeaderTable, new Rectangle(0, 0, 800, 50));
      hbandType.setBottomBorder(0);
      footer.setVisible(false);
      footer.setHeight(0.1F);
      footer.setShrinkToFit(true);
      footer.setBottomBorder(0);
      MilestoneHelper.setSelectionSorting(selections, 12);
      Collections.sort(selections);
      MilestoneHelper.setSelectionSorting(selections, 4);
      Collections.sort(selections);
      MilestoneHelper.setSelectionSorting(selections, 22);
      Collections.sort(selections);
      MilestoneHelper.setSelectionSorting(selections, 9);
      Collections.sort(selections);
      MilestoneHelper.setSelectionSorting(selections, 1);
      Collections.sort(selections);
      int commentLines = 0;
      for (int j = 0; j < selections.size(); j++) {
        statusJSPupdate.updateStatus(selections.size(), j, "start_report", 0);
        Selection sel = (Selection)selections.elementAt(j);
        sel = SelectionManager.getInstance().getSelectionAndSchedule(sel.getSelectionID());
        int templeateId = sel.getTemplateId();
        TemplateManager temmanage = new TemplateManager();
        temmanage.getTemplate(templeateId, false);
        String USIntRelease = "";
        if (SelectionManager.getLookupObjectValue(sel.getSelectionStatus()).equalsIgnoreCase("TBS")) {
          USIntRelease = "TBS ";
        } else if (SelectionManager.getLookupObjectValue(sel.getSelectionStatus()).equalsIgnoreCase("ITW")) {
          USIntRelease = "ITW ";
        } 
        if (USIntRelease != null && !USIntRelease.equals("")) {
          USIntRelease = String.valueOf(USIntRelease) + MilestoneHelper.getFormatedDate(sel.getStreetDate());
        } else {
          USIntRelease = String.valueOf(USIntRelease) + MilestoneHelper.getFormatedDate(sel.getStreetDate()) + "\n" + MilestoneHelper.getFormatedDate(sel.getInternationalDate());
        } 
        String upc = (sel.getUpc() != null) ? sel.getUpc() : " ";
        upc = MilestoneHelper_2.getRMSReportFormat(upc, "UPC", sel.getIsDigital());
        String localProductNumber = "";
        if (sel.getPrefixID() != null && sel.getPrefixID().getAbbreviation() != null)
          localProductNumber = sel.getPrefixID().getAbbreviation(); 
        localProductNumber = String.valueOf(localProductNumber) + sel.getSelectionNo();
        String artistName = (sel.getFlArtist() != null) ? sel.getFlArtist() : " ";
        String title = (sel.getTitle() != null) ? sel.getTitle() : " ";
        String comment = (sel.getSelectionComments() != null) ? sel.getSelectionComments() : " ";
        commentLines = MilestoneHelper.lineCount(comment, "");
        String code = (sel.getSellCode() != null) ? sel.getSellCode() : "";
        if (code != null && code.startsWith("-1"))
          code = ""; 
        String retail = "";
        if (sel.getPriceCode() != null && sel.getPriceCode().getRetailCode() != null)
          retail = sel.getPriceCode().getRetailCode(); 
        if (code.length() > 0)
          retail = "\n" + retail; 
        String price = "";
        if (sel.getPriceCode() != null && sel.getPriceCode().getTotalCost() > 0.0F)
          price = "\n$" + MilestoneHelper.formatDollarPrice(sel.getPriceCode().getTotalCost()); 
        String label = (sel.getImprint() != null) ? sel.getImprint() : " ";
        String packaging = (sel.getSelectionPackaging() != null) ? sel.getSelectionPackaging() : " ";
        String labelAndPackage = String.valueOf(label) + "\n" + packaging;
        for (int y = 0; y < commentLines; y++)
          labelAndPackage = String.valueOf(labelAndPackage) + "\n"; 
        String selFormat = String.valueOf(sel.getReleaseType().getName()) + "\n" + 
          sel.getSelectionConfig().getSelectionConfigurationName() + "\n" + 
          sel.getSelectionSubConfig().getSelectionSubConfigurationName();
        Vector manuPlant = null;
        int poQty = 0;
        String vendorDetail = "";
        if (sel != null) {
          SelectionManager.getInstance().getSelectionManufacturingSubDetail(sel);
          manuPlant = sel.getManufacturingPlants();
        } 
        String qtyOrders = "";
        if (manuPlant != null) {
          Iterator itmanu = manuPlant.iterator();
          while (itmanu != null && itmanu.hasNext()) {
            Plant plant = (Plant)itmanu.next();
            poQty += plant.orderQty;
            if (plant.getPlant() != null) {
              vendorDetail = String.valueOf(vendorDetail) + (plant.getPlant()).name + " - " + plant.orderQty + "\n";
              qtyOrders = MilestoneHelper.removeCommas(MilestoneHelper.formatQuantityWithCommas(String.valueOf(plant.getCompletedQty())));
            } 
          } 
        } 
        String dueDateTaks = "";
        String completationDateTaks = "";
        Schedule schedule = sel.getSchedule();
        String dueDateHolidayFlg = "";
        String schedulingFormDate = "", labelCopyDate = "", copyToArtDate = "", audioToUMSDate = "", artToPrinterDate = "", bomDate = "", productionStatusDate = "";
        String schedulingForm = "", labelCopy = "", copyToArt = "", audioToUMS = "", artToPrinter = "", bom = "", productionStatus = "";
        Number f = NumberFormat.getNumberInstance(Locale.US).parse(Integer.toString(poQty));
        String fS = f.toString();
        Vector tasks = null;
        if (schedule != null)
          tasks = schedule.getTasks(); 
        if (tasks != null) {
          Iterator it = tasks.iterator();
          while (it != null && it.hasNext()) {
            ScheduledTask task = (ScheduledTask)it.next();
            String taskAbbrev = MilestoneHelper.getTaskAbbreivationNameById(task.getTaskAbbreviationID());
            String name = task.getName();
            if (name.equalsIgnoreCase("Finished Goods Due")) {
              dueDateTaks = MilestoneHelper.getShortDate(task.getDueDate());
              completationDateTaks = MilestoneHelper.getFormatedDate(task.getCompletionDate());
            } 
            try {
              if (name != null) {
                name = name.trim();
                dueDateHolidayFlg = MilestoneHelper.getDayType(sel.getCalendarGroup(), task.getDueDate());
                if (taskAbbrev.equalsIgnoreCase("SCHF")) {
                  schedulingFormDate = String.valueOf(MilestoneHelper.getShortDate(task.getDueDate())) + " " + dueDateHolidayFlg;
                  if (task.getScheduledTaskStatus() != null && 
                    task.getScheduledTaskStatus().equals("N/A")) {
                    schedulingForm = "N/A";
                  } else {
                    schedulingForm = MilestoneHelper.getFormatedDate(task.getCompletionDate());
                  } 
                  schedulingForm = String.valueOf(schedulingForm) + "\n";
                  schedulingForm = String.valueOf(schedulingForm) + ((task.getComments() != null) ? task.getComments() : "");
                  continue;
                } 
                if (taskAbbrev.equalsIgnoreCase("LCD")) {
                  labelCopyDate = String.valueOf(MilestoneHelper.getShortDate(task.getDueDate())) + 
                    " " + dueDateHolidayFlg;
                  if (task.getScheduledTaskStatus() != null && 
                    task.getScheduledTaskStatus().equals("N/A")) {
                    labelCopy = "N/A";
                  } else {
                    labelCopy = MilestoneHelper.getFormatedDate(task.getCompletionDate());
                  } 
                  labelCopy = String.valueOf(labelCopy) + "\n";
                  labelCopy = String.valueOf(labelCopy) + ((task.getComments() != null) ? task.getComments() : " ");
                  continue;
                } 
                if (taskAbbrev.equalsIgnoreCase("CTAD")) {
                  copyToArtDate = String.valueOf(MilestoneHelper.getShortDate(task.getDueDate())) + 
                    " " + dueDateHolidayFlg;
                  if (task.getScheduledTaskStatus() != null && 
                    task.getScheduledTaskStatus().equals("N/A")) {
                    copyToArt = "N/A";
                  } else {
                    copyToArt = MilestoneHelper.getFormatedDate(task.getCompletionDate());
                  } 
                  copyToArt = String.valueOf(copyToArt) + "\n";
                  copyToArt = String.valueOf(copyToArt) + ((task.getComments() != null) ? task.getComments() : " ");
                  continue;
                } 
                if (taskAbbrev.equalsIgnoreCase("TPS")) {
                  audioToUMSDate = String.valueOf(MilestoneHelper.getShortDate(task.getDueDate())) + 
                    " " + dueDateHolidayFlg;
                  if (task.getScheduledTaskStatus() != null && 
                    task.getScheduledTaskStatus().equals("N/A")) {
                    audioToUMS = "N/A";
                  } else {
                    audioToUMS = MilestoneHelper.getFormatedDate(task.getCompletionDate());
                  } 
                  audioToUMS = String.valueOf(audioToUMS) + "\n";
                  audioToUMS = String.valueOf(audioToUMS) + ((task.getComments() != null) ? task.getComments() : " ");
                  continue;
                } 
                if (taskAbbrev.equalsIgnoreCase("ARTP")) {
                  artToPrinterDate = String.valueOf(MilestoneHelper.getShortDate(task.getDueDate())) + 
                    " " + dueDateHolidayFlg;
                  if (task.getScheduledTaskStatus() != null && 
                    task.getScheduledTaskStatus().equals("N/A")) {
                    artToPrinter = "N/A";
                  } else {
                    artToPrinter = MilestoneHelper.getFormatedDate(task.getCompletionDate());
                  } 
                  artToPrinter = String.valueOf(artToPrinter) + "\n";
                  artToPrinter = String.valueOf(artToPrinter) + ((task.getComments() != null) ? task.getComments() : " ");
                  continue;
                } 
                if (taskAbbrev.equalsIgnoreCase("CPS")) {
                  productionStatusDate = String.valueOf(MilestoneHelper.getShortDate(task.getDueDate())) + 
                    " " + dueDateHolidayFlg;
                  if (task.getScheduledTaskStatus() != null && 
                    task.getScheduledTaskStatus().equals("N/A")) {
                    productionStatus = "N/A";
                  } else if (task.getScheduledTaskStatus() != null) {
                    productionStatus = task.getScheduledTaskStatus();
                  } else {
                    productionStatus = "";
                  } 
                  productionStatus = String.valueOf(productionStatus) + "\n";
                  productionStatus = String.valueOf(productionStatus) + ((task.getComments() != null) ? task.getComments() : " ");
                  continue;
                } 
                if (taskAbbrev.equalsIgnoreCase("BILL")) {
                  bomDate = String.valueOf(MilestoneHelper.getShortDate(task.getDueDate())) + 
                    " " + dueDateHolidayFlg;
                  if (task.getScheduledTaskStatus() != null && 
                    task.getScheduledTaskStatus().equals("N/A")) {
                    bom = "N/A";
                  } else {
                    bom = MilestoneHelper.getFormatedDate(task.getCompletionDate());
                  } 
                  bom = String.valueOf(bom) + "\n";
                  bom = String.valueOf(bom) + ((task.getComments() != null) ? task.getComments() : " ");
                } 
              } 
            } catch (Exception e) {
              e.printStackTrace();
            } 
          } 
        } 
        nextRow = 0;
        subTable = new DefaultTableLens(2, 14);
        subTable.setColWidth(0, 95);
        subTable.setColWidth(1, 225);
        subTable.setColWidth(2, 140);
        subTable.setColWidth(3, 120);
        subTable.setColWidth(4, 80);
        subTable.setColWidth(5, 70);
        subTable.setColWidth(6, 70);
        subTable.setColWidth(7, 70);
        subTable.setColWidth(8, 70);
        subTable.setColWidth(9, 70);
        subTable.setColWidth(10, 70);
        subTable.setColWidth(11, 70);
        subTable.setColWidth(12, 90);
        subTable.setColWidth(13, 180);
        subTable.setColBorderColor(nextRow, Color.lightGray);
        subTable.setRowBorderColor(nextRow, Color.lightGray);
        subTable.setRowBorder(4097);
        subTable.setColBorder(4097);
        subTable.setRowInsets(nextRow, new Insets(0, 0, 0, 0));
        subTable.setRowAlignment(nextRow, 2);
        subTable.setSpan(nextRow, 0, new Dimension(1, 2));
        subTable.setObject(nextRow, 0, USIntRelease);
        subTable.setObject(nextRow, 1, "");
        subTable.setObject(nextRow, 2, "");
        subTable.setObject(nextRow, 3, "");
        subTable.setObject(nextRow, 4, schedulingFormDate);
        subTable.setObject(nextRow, 5, labelCopyDate);
        subTable.setObject(nextRow, 6, dueDateTaks);
        subTable.setObject(nextRow, 7, audioToUMSDate);
        subTable.setObject(nextRow, 8, artToPrinterDate);
        subTable.setObject(nextRow, 9, bomDate);
        subTable.setObject(nextRow, 10, "");
        subTable.setObject(nextRow, 11, "");
        subTable.setObject(nextRow, 12, "");
        subTable.setObject(nextRow, 13, sel.getSelectionComments());
        subTable.setSpan(nextRow, 13, new Dimension(1, 2));
        subTable.setRowFont(nextRow, new Font("Arial", 1, 7));
        Font holidayFont = new Font("Arial", 3, 7);
        for (int colIdx = 4; colIdx <= 10; colIdx++) {
          String dueDate = subTable.getObject(nextRow, colIdx).toString();
          if (dueDate != null && dueDate.length() > 0) {
            char lastChar = dueDate.charAt(dueDate.length() - 1);
            if (Character.isLetter(lastChar))
              subTable.setFont(nextRow, colIdx, holidayFont); 
          } 
        } 
        subTable.setFont(nextRow, 0, new Font("Arial", 0, 7));
        subTable.setFont(nextRow, 1, new Font("Arial", 0, 7));
        subTable.setAlignment(nextRow, 0, 10);
        subTable.setRowHeight(nextRow, 10);
        subTable.setColFont(0, new Font("Arial", 0, 8));
        subTable.setRowBackground(nextRow, Color.white);
        subTable.setBackground(nextRow, 0, Color.white);
        subTable.setBackground(nextRow, 1, Color.lightGray);
        subTable.setBackground(nextRow, 2, Color.lightGray);
        subTable.setBackground(nextRow, 3, Color.lightGray);
        subTable.setBackground(nextRow, 4, Color.lightGray);
        subTable.setBackground(nextRow, 5, Color.lightGray);
        subTable.setBackground(nextRow, 6, Color.lightGray);
        subTable.setBackground(nextRow, 7, Color.lightGray);
        subTable.setBackground(nextRow, 8, Color.lightGray);
        subTable.setBackground(nextRow, 9, Color.lightGray);
        subTable.setBackground(nextRow, 10, Color.lightGray);
        subTable.setBackground(nextRow, 11, Color.lightGray);
        subTable.setBackground(nextRow, 12, Color.lightGray);
        subTable.setBackground(nextRow, 13, Color.white);
        subTable.setColBorderColor(nextRow, -1, Color.lightGray);
        subTable.setColBorderColor(nextRow, 0, Color.lightGray);
        subTable.setColBorderColor(nextRow, 1, Color.lightGray);
        subTable.setColBorderColor(nextRow, 2, Color.lightGray);
        subTable.setColBorderColor(nextRow, 3, Color.lightGray);
        subTable.setColBorderColor(nextRow, 4, Color.lightGray);
        subTable.setColBorderColor(nextRow, 5, Color.lightGray);
        subTable.setColBorderColor(nextRow, 6, Color.lightGray);
        subTable.setColBorderColor(nextRow, 7, Color.lightGray);
        subTable.setColBorderColor(nextRow, 8, Color.lightGray);
        subTable.setColBorderColor(nextRow, 9, Color.lightGray);
        subTable.setColBorderColor(nextRow, 10, Color.lightGray);
        subTable.setColBorderColor(nextRow, 11, Color.lightGray);
        subTable.setColBorderColor(nextRow, 12, Color.lightGray);
        subTable.setColBorderColor(nextRow, 13, Color.lightGray);
        subTable.setRowBorderColor(nextRow, Color.lightGray);
        subTable.setRowBorderColor(nextRow - 1, Color.lightGray);
        subTable.setRowBorderColor(nextRow, 1, Color.white);
        subTable.setColBorder(nextRow, 1, 266240);
        subTable.setColBorder(nextRow, 2, 266240);
        subTable.setColBorder(nextRow, 3, 266240);
        subTable.setColBorder(nextRow, 4, 266240);
        subTable.setColBorder(nextRow, 5, 266240);
        subTable.setColBorder(nextRow, 6, 266240);
        subTable.setColBorder(nextRow, 7, 266240);
        subTable.setColBorder(nextRow, 8, 266240);
        subTable.setColBorder(nextRow, 9, 266240);
        subTable.setColBorder(nextRow, 10, 266240);
        subTable.setColBorder(nextRow, 11, 266240);
        subTable.setAlignment(nextRow, 0, 10);
        subTable.setAlignment(nextRow, 1, 10);
        subTable.setAlignment(nextRow, 2, 10);
        subTable.setAlignment(nextRow, 3, 10);
        subTable.setAlignment(nextRow, 4, 10);
        subTable.setAlignment(nextRow, 5, 10);
        subTable.setAlignment(nextRow, 6, 10);
        subTable.setAlignment(nextRow, 7, 10);
        subTable.setAlignment(nextRow, 8, 10);
        subTable.setAlignment(nextRow, 9, 10);
        subTable.setAlignment(nextRow, 10, 10);
        subTable.setAlignment(nextRow, 11, 10);
        subTable.setAlignment(nextRow, 12, 10);
        subTable.setAlignment(nextRow, 13, 9);
        nextRow++;
        subTable.setRowBorderColor(nextRow, Color.lightGray);
        subTable.setColBorderColor(nextRow, -1, Color.lightGray);
        subTable.setColBorderColor(nextRow, 0, Color.lightGray);
        subTable.setColBorderColor(nextRow, 1, Color.lightGray);
        subTable.setColBorderColor(nextRow, 2, Color.lightGray);
        subTable.setColBorderColor(nextRow, 3, Color.lightGray);
        subTable.setColBorderColor(nextRow, 4, Color.lightGray);
        subTable.setColBorderColor(nextRow, 5, Color.lightGray);
        subTable.setColBorderColor(nextRow, 6, Color.lightGray);
        subTable.setColBorderColor(nextRow, 7, Color.lightGray);
        subTable.setColBorderColor(nextRow, 8, Color.lightGray);
        subTable.setColBorderColor(nextRow, 9, Color.lightGray);
        subTable.setColBorderColor(nextRow, 10, Color.lightGray);
        subTable.setColBorderColor(nextRow, 11, Color.lightGray);
        subTable.setColBorderColor(nextRow, 12, Color.lightGray);
        subTable.setColBorderColor(nextRow, 13, Color.lightGray);
        subTable.setRowInsets(nextRow, new Insets(0, 0, 0, 0));
        subTable.setRowFont(nextRow, new Font("Arial", 0, 7));
        subTable.setRowAlignment(nextRow, 10);
        subTable.setObject(nextRow, 0, "");
        subTable.setObject(nextRow, 1, String.valueOf(upc) + "\n" + localProductNumber + "\n" + artistName + "\n" + title);
        subTable.setObject(nextRow, 2, labelAndPackage);
        subTable.setObject(nextRow, 3, selFormat);
        subTable.setObject(nextRow, 4, String.valueOf(code) + " " + retail + " " + price);
        subTable.setObject(nextRow, 5, labelCopy);
        subTable.setObject(nextRow, 6, completationDateTaks);
        subTable.setObject(nextRow, 7, audioToUMS);
        subTable.setObject(nextRow, 8, artToPrinter);
        subTable.setObject(nextRow, 9, bom);
        subTable.setObject(nextRow, 11, qtyOrders);
        subTable.setObject(nextRow, 10, (poQty == 0) ? "" : fS);
        subTable.setObject(nextRow, 12, productionStatus);
        subTable.setObject(nextRow, 13, sel.getSelectionComments());
        body = new SectionBand(report);
        body.addTable(subTable, new Rectangle(800, 800));
        double lfLineCount = 1.5D;
        if (commentLines > 0 || labelAndPackage.length() > 17 || sel.getTitle().length() > 20) {
          body.setHeight(1.5F);
        } else {
          body.setHeight(1.0F);
        } 
        if (commentLines > 0 || labelAndPackage.length() > 17 || sel.getTitle().length() > 20 || 
          schedulingForm.length() > 25 || labelCopy.length() > 25 || artToPrinter.length() > 25 || 
          audioToUMS.length() > 25 || productionStatus.length() > 25 || 
          bom.length() > 25) {
          if (lfLineCount < commentLines * 0.3D)
            lfLineCount = commentLines * 0.3D; 
          if (lfLineCount < (labelAndPackage.length() / 8) * 0.3D)
            lfLineCount = (labelAndPackage.length() / 8) * 0.3D; 
          if (lfLineCount < (sel.getTitle().length() / 8) * 0.3D)
            lfLineCount = (sel.getTitle().length() / 8) * 0.3D; 
          if (lfLineCount < (schedulingForm.length() / 7) * 0.3D)
            lfLineCount = (schedulingForm.length() / 7) * 0.3D; 
          if (lfLineCount < (labelCopy.length() / 7) * 0.3D)
            lfLineCount = (labelCopy.length() / 7) * 0.3D; 
          if (lfLineCount < (artToPrinter.length() / 7) * 0.3D)
            lfLineCount = (artToPrinter.length() / 7) * 0.3D; 
          if (lfLineCount < (audioToUMS.length() / 7) * 0.3D)
            lfLineCount = (audioToUMS.length() / 7) * 0.3D; 
          if (lfLineCount < (productionStatus.length() / 7) * 0.3D)
            lfLineCount = (productionStatus.length() / 7) * 0.3D; 
          if (lfLineCount < (bom.length() / 7) * 0.3D)
            lfLineCount = (bom.length() / 7) * 0.3D; 
          body.setHeight((float)lfLineCount);
        } else if (commentLines > 0 || labelAndPackage.length() > 17 || sel.getTitle().length() > 20 || 
          schedulingForm.length() > 5 || labelCopy.length() > 5 || artToPrinter.length() > 5 || 
          audioToUMS.length() > 5 || productionStatus.length() > 5 || 
          bom.length() > 5) {
          if (lfLineCount < commentLines * 0.3D)
            lfLineCount = commentLines * 0.3D; 
          if (lfLineCount < (labelAndPackage.length() / 5) * 0.3D)
            lfLineCount = (labelAndPackage.length() / 5) * 0.3D; 
          if (lfLineCount < (sel.getTitle().length() / 5) * 0.3D)
            lfLineCount = (sel.getTitle().length() / 5) * 0.3D; 
          if (lfLineCount < (schedulingForm.length() / 5) * 0.3D)
            lfLineCount = (schedulingForm.length() / 5) * 0.3D; 
          if (lfLineCount < (labelCopy.length() / 5) * 0.3D)
            lfLineCount = (labelCopy.length() / 5) * 0.3D; 
          if (lfLineCount < (artToPrinter.length() / 5) * 0.3D)
            lfLineCount = (artToPrinter.length() / 5) * 0.3D; 
          if (lfLineCount < (audioToUMS.length() / 5) * 0.3D)
            lfLineCount = (audioToUMS.length() / 5) * 0.3D; 
          if (lfLineCount < (productionStatus.length() / 5) * 0.3D)
            lfLineCount = (productionStatus.length() / 5) * 0.3D; 
          if (lfLineCount < (bom.length() / 5) * 0.3D)
            lfLineCount = (bom.length() / 5) * 0.3D; 
          body.setHeight((float)lfLineCount);
        } else {
          body.setHeight(1.0F);
        } 
        body.setBottomBorder(0);
        body.setTopBorder(0);
        body.setShrinkToFit(true);
        body.setVisible(true);
        group = new DefaultSectionLens(null, group, body);
      } 
      group = new DefaultSectionLens(hbandType, group, footer);
      report.addSection(group, rowCountTable);
      group = null;
    } catch (Exception e) {
      System.out.println(">>>>>>>>ReportHandler.fillCarolineProductionUpdateForPrint(): exception: " + e);
      e.printStackTrace();
    } 
    statusJSPupdate = null;
  }
}


/* Location:              D:\Documents\NetBeansProjects\milestone2 Local.war!\WEB-INF\classes\co\\universal\milestone\CarolineProductionSchedulePrintSubHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */