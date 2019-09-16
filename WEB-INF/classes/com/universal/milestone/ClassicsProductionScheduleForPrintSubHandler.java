package WEB-INF.classes.com.universal.milestone;

import com.techempower.ComponentLog;
import com.techempower.gemini.Context;
import com.techempower.gemini.GeminiApplication;
import com.universal.milestone.ClassicsProductionScheduleForPrintSubHandler;
import com.universal.milestone.Form;
import com.universal.milestone.MilestoneHelper;
import com.universal.milestone.MilestoneHelper_2;
import com.universal.milestone.Schedule;
import com.universal.milestone.ScheduledTask;
import com.universal.milestone.SecureHandler;
import com.universal.milestone.Selection;
import com.universal.milestone.SelectionManager;
import com.universal.milestone.StatusJSPupdate;
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
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.Vector;

public class ClassicsProductionScheduleForPrintSubHandler extends SecureHandler {
  public static final String COMPONENT_CODE = "hCProd";
  
  public GeminiApplication application;
  
  public ComponentLog log;
  
  public ClassicsProductionScheduleForPrintSubHandler(GeminiApplication application) {
    this.application = application;
    this.log = application.getLog("hCProd");
  }
  
  public String getDescription() { return "Sub Report"; }
  
  protected static void fillClassicProductionUpdateForPrint(XStyleSheet report, Context context) {
    int COL_LINE_STYLE = 4097;
    int HEADER_FONT_SIZE = 12;
    double ldLineVal = 0.3D;
    ComponentLog log = context.getApplication().getLog("hCProd");
    StatusJSPupdate statusJSPupdate = new StatusJSPupdate(context);
    statusJSPupdate.updateStatus(0, 0, "start_gathering", 0);
    Vector selections = MilestoneHelper.getSelectionsForReport(context);
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
      table_contents = new DefaultTableLens(1, 15);
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
      table_contents.setColWidth(3, 80);
      table_contents.setColWidth(4, 70);
      table_contents.setColWidth(5, 70);
      table_contents.setColWidth(6, 70);
      table_contents.setColWidth(7, 70);
      table_contents.setColWidth(8, 70);
      table_contents.setColWidth(9, 70);
      table_contents.setColWidth(10, 70);
      table_contents.setColWidth(11, 70);
      table_contents.setColWidth(12, 70);
      table_contents.setColWidth(13, 70);
      table_contents.setColWidth(14, 180);
      table_contents.setRowBorderColor(nextRow, Color.lightGray);
      table_contents.setRowBorder(4097);
      table_contents.setColBorder(4097);
      columnHeaderTable = new DefaultTableLens(1, 15);
      nextRow = 0;
      columnHeaderTable.setColWidth(0, 95);
      columnHeaderTable.setColWidth(1, 225);
      columnHeaderTable.setColWidth(2, 140);
      columnHeaderTable.setColWidth(3, 80);
      columnHeaderTable.setColWidth(4, 70);
      columnHeaderTable.setColWidth(5, 70);
      columnHeaderTable.setColWidth(6, 70);
      columnHeaderTable.setColWidth(7, 70);
      columnHeaderTable.setColWidth(8, 70);
      columnHeaderTable.setColWidth(9, 70);
      columnHeaderTable.setColWidth(10, 70);
      columnHeaderTable.setColWidth(11, 70);
      columnHeaderTable.setColWidth(12, 70);
      columnHeaderTable.setColWidth(13, 70);
      columnHeaderTable.setColWidth(14, 180);
      columnHeaderTable.setRowAlignment(nextRow, 34);
      columnHeaderTable.setObject(nextRow, 0, "Release\nDate");
      columnHeaderTable.setObject(nextRow, 1, "UPC\nLocal Prod #\nArtist\nTitle");
      columnHeaderTable.setObject(nextRow, 2, "Label &\nPackage Info");
      columnHeaderTable.setObject(nextRow, 3, "Cover\nApproved");
      columnHeaderTable.setObject(nextRow, 4, "Package\nCopy");
      columnHeaderTable.setObject(nextRow, 5, "Sticker\nCopy");
      columnHeaderTable.setObject(nextRow, 6, "DJ\nQty");
      columnHeaderTable.setObject(nextRow, 7, "F/M");
      columnHeaderTable.setObject(nextRow, 8, "Manf.\nImport\nQty");
      columnHeaderTable.setObject(nextRow, 9, "Seps");
      columnHeaderTable.setObject(nextRow, 10, "BOM");
      columnHeaderTable.setObject(nextRow, 11, "Masters\nat Plant");
      columnHeaderTable.setObject(nextRow, 12, "Film at\nPrinter");
      columnHeaderTable.setObject(nextRow, 13, "Print\nat Plant");
      columnHeaderTable.setObject(nextRow, 14, "Comments");
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
      columnHeaderTable.setColBorderColor(nextRow, 14, Color.lightGray);
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
        String label = (sel.getImprint() != null) ? sel.getImprint() : " ";
        String packaging = (sel.getSelectionPackaging() != null) ? sel.getSelectionPackaging() : " ";
        String code = (sel.getSellCode() != null) ? sel.getSellCode() : "";
        if (code != null && code.startsWith("-1"))
          code = ""; 
        String retail = "";
        if (sel.getPriceCode() != null && sel.getPriceCode().getRetailCode() != null)
          retail = sel.getPriceCode().getRetailCode(); 
        if (code.length() > 0)
          retail = "    " + retail; 
        String price = "";
        if (sel.getPriceCode() != null && sel.getPriceCode().getTotalCost() > 0.0F)
          price = "     $" + MilestoneHelper.formatDollarPrice(sel.getPriceCode().getTotalCost()); 
        commentLines = MilestoneHelper.lineCount(comment, "");
        String labelAndPackage = String.valueOf(label) + "\n" + packaging;
        for (int y = 0; y < commentLines; y++)
          labelAndPackage = String.valueOf(labelAndPackage) + "\n"; 
        Schedule schedule = sel.getSchedule();
        String coverApprovedDate = "", packageCopyDate = "", stickerCopyDate = "";
        String djQtyDate = "", fmDate = "", manuImportDate = "", sepsDate = "";
        String bomDate = "", masterAtPlantDate = "", filmAtPrinterDate = "";
        String printAtPlantDate = "", packageFilmDate = "";
        String dueDateHolidayFlg = "";
        String coverApproved = "", packageCopy = "", stickerCopy = "";
        String djQty = "", fm = "", manuImportQty = "", seps = "";
        String bom = "", masterAtPlant = "", filmAtPrinter = "";
        String printAtPlant = "", packageFilm = "";
        Vector tasks = null;
        if (schedule != null)
          tasks = schedule.getTasks(); 
        if (tasks != null) {
          Iterator it = tasks.iterator();
          while (it != null && it.hasNext()) {
            ScheduledTask task = (ScheduledTask)it.next();
            String taskAbbrev = MilestoneHelper.getTaskAbbreivationNameById(task.getTaskAbbreviationID());
            String name = task.getName();
            try {
              if (name != null) {
                name = name.trim();
                dueDateHolidayFlg = MilestoneHelper.getDayType(sel.getCalendarGroup(), task.getDueDate());
                if (taskAbbrev.equalsIgnoreCase("CAA")) {
                  coverApprovedDate = String.valueOf(MilestoneHelper.getShortDate(task.getDueDate())) + 
                    " " + dueDateHolidayFlg;
                  if (task.getScheduledTaskStatus() != null && 
                    task.getScheduledTaskStatus().equals("N/A")) {
                    coverApproved = "N/A";
                  } else {
                    coverApproved = MilestoneHelper.getFormatedDate(task.getCompletionDate());
                  } 
                  coverApproved = String.valueOf(coverApproved) + "\n";
                  coverApproved = String.valueOf(coverApproved) + ((task.getComments() != null) ? task.getComments() : "");
                  continue;
                } 
                if (taskAbbrev.equalsIgnoreCase("FPC")) {
                  packageCopyDate = String.valueOf(MilestoneHelper.getShortDate(task.getDueDate())) + 
                    " " + dueDateHolidayFlg;
                  if (task.getScheduledTaskStatus() != null && 
                    task.getScheduledTaskStatus().equals("N/A")) {
                    packageCopy = "N/A";
                  } else {
                    packageCopy = MilestoneHelper.getFormatedDate(task.getCompletionDate());
                  } 
                  packageCopy = String.valueOf(packageCopy) + "\n";
                  packageCopy = String.valueOf(packageCopy) + ((task.getComments() != null) ? task.getComments() : " ");
                  continue;
                } 
                if (taskAbbrev.equalsIgnoreCase("STD")) {
                  stickerCopyDate = String.valueOf(MilestoneHelper.getShortDate(task.getDueDate())) + 
                    " " + dueDateHolidayFlg;
                  if (task.getScheduledTaskStatus() != null && 
                    task.getScheduledTaskStatus().equals("N/A")) {
                    stickerCopy = "N/A";
                  } else {
                    stickerCopy = MilestoneHelper.getFormatedDate(task.getCompletionDate());
                  } 
                  stickerCopy = String.valueOf(stickerCopy) + "\n";
                  stickerCopy = String.valueOf(stickerCopy) + ((task.getComments() != null) ? task.getComments() : " ");
                  continue;
                } 
                if (taskAbbrev.equalsIgnoreCase("DSD")) {
                  djQtyDate = String.valueOf(MilestoneHelper.getShortDate(task.getDueDate())) + 
                    " " + dueDateHolidayFlg;
                  if (task.getScheduledTaskStatus() != null && 
                    task.getScheduledTaskStatus().equals("N/A")) {
                    djQty = "N/A";
                  } else {
                    djQty = MilestoneHelper.getFormatedDate(task.getCompletionDate());
                  } 
                  djQty = String.valueOf(djQty) + "\n";
                  djQty = String.valueOf(djQty) + ((task.getComments() != null) ? task.getComments() : " ");
                  continue;
                } 
                if (taskAbbrev.equalsIgnoreCase("MQD")) {
                  manuImportDate = String.valueOf(MilestoneHelper.getShortDate(task.getDueDate())) + 
                    " " + dueDateHolidayFlg;
                  if (task.getScheduledTaskStatus() != null && 
                    task.getScheduledTaskStatus().equals("N/A")) {
                    manuImportQty = "N/A";
                  } else {
                    manuImportQty = MilestoneHelper.getFormatedDate(task.getCompletionDate());
                  } 
                  manuImportQty = String.valueOf(manuImportQty) + "\n";
                  manuImportQty = String.valueOf(manuImportQty) + ((task.getComments() != null) ? task.getComments() : " ");
                  continue;
                } 
                if (taskAbbrev.equalsIgnoreCase("F/M")) {
                  fmDate = String.valueOf(MilestoneHelper.getShortDate(task.getDueDate())) + 
                    " " + dueDateHolidayFlg;
                  if (task.getScheduledTaskStatus() != null && 
                    task.getScheduledTaskStatus().equals("N/A")) {
                    fm = "N/A";
                  } else {
                    fm = MilestoneHelper.getFormatedDate(task.getCompletionDate());
                  } 
                  fm = String.valueOf(fm) + "\n";
                  fm = String.valueOf(fm) + ((task.getComments() != null) ? task.getComments() : " ");
                  continue;
                } 
                if (taskAbbrev.equalsIgnoreCase("PAP")) {
                  printAtPlantDate = String.valueOf(MilestoneHelper.getShortDate(task.getDueDate())) + 
                    " " + dueDateHolidayFlg;
                  if (task.getScheduledTaskStatus() != null && 
                    task.getScheduledTaskStatus().equals("N/A")) {
                    printAtPlant = "N/A";
                  } else {
                    printAtPlant = MilestoneHelper.getFormatedDate(task.getCompletionDate());
                  } 
                  printAtPlant = String.valueOf(printAtPlant) + "\n";
                  printAtPlant = String.valueOf(printAtPlant) + ((task.getComments() != null) ? task.getComments() : " ");
                  continue;
                } 
                if (taskAbbrev.equalsIgnoreCase("TAPE")) {
                  masterAtPlantDate = String.valueOf(MilestoneHelper.getShortDate(task.getDueDate())) + 
                    " " + dueDateHolidayFlg;
                  if (task.getScheduledTaskStatus() != null && 
                    task.getScheduledTaskStatus().equals("N/A")) {
                    masterAtPlant = "N/A";
                  } else {
                    masterAtPlant = MilestoneHelper.getFormatedDate(task.getCompletionDate());
                  } 
                  masterAtPlant = String.valueOf(masterAtPlant) + "\n";
                  masterAtPlant = String.valueOf(masterAtPlant) + ((task.getComments() != null) ? task.getComments() : " ");
                  continue;
                } 
                if (taskAbbrev.equalsIgnoreCase("SEPS")) {
                  sepsDate = String.valueOf(MilestoneHelper.getShortDate(task.getDueDate())) + 
                    " " + dueDateHolidayFlg;
                  if (task.getScheduledTaskStatus() != null && 
                    task.getScheduledTaskStatus().equals("N/A")) {
                    seps = "N/A";
                  } else {
                    seps = MilestoneHelper.getFormatedDate(task.getCompletionDate());
                  } 
                  seps = String.valueOf(seps) + "\n";
                  seps = String.valueOf(seps) + ((task.getComments() != null) ? task.getComments() : " ");
                  continue;
                } 
                if (taskAbbrev.equalsIgnoreCase("BOM")) {
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
                  continue;
                } 
                if (taskAbbrev.equalsIgnoreCase("FAP")) {
                  filmAtPrinterDate = String.valueOf(MilestoneHelper.getShortDate(task.getDueDate())) + 
                    " " + dueDateHolidayFlg;
                  if (task.getScheduledTaskStatus() != null && 
                    task.getScheduledTaskStatus().equals("N/A")) {
                    filmAtPrinter = "N/A";
                  } else {
                    filmAtPrinter = MilestoneHelper.getFormatedDate(task.getCompletionDate());
                  } 
                  filmAtPrinter = String.valueOf(filmAtPrinter) + "\n";
                  filmAtPrinter = String.valueOf(filmAtPrinter) + ((task.getComments() != null) ? task.getComments() : " ");
                } 
              } 
            } catch (Exception e) {
              e.printStackTrace();
            } 
          } 
        } 
        nextRow = 0;
        subTable = new DefaultTableLens(2, 15);
        subTable.setColWidth(0, 95);
        subTable.setColWidth(1, 225);
        subTable.setColWidth(2, 140);
        subTable.setColWidth(3, 80);
        subTable.setColWidth(4, 70);
        subTable.setColWidth(5, 70);
        subTable.setColWidth(6, 70);
        subTable.setColWidth(7, 70);
        subTable.setColWidth(8, 70);
        subTable.setColWidth(9, 70);
        subTable.setColWidth(10, 70);
        subTable.setColWidth(11, 70);
        subTable.setColWidth(12, 70);
        subTable.setColWidth(13, 70);
        subTable.setColWidth(14, 180);
        subTable.setColBorderColor(nextRow, Color.lightGray);
        subTable.setRowBorderColor(nextRow, Color.lightGray);
        subTable.setRowBorder(4097);
        subTable.setColBorder(4097);
        subTable.setRowInsets(nextRow, new Insets(0, 0, 0, 0));
        subTable.setRowAlignment(nextRow, 2);
        subTable.setSpan(nextRow, 0, new Dimension(1, 2));
        subTable.setObject(nextRow, 0, USIntRelease);
        subTable.setObject(nextRow, 1, String.valueOf(code) + " " + retail + " " + price);
        subTable.setObject(nextRow, 2, "Due Dates");
        subTable.setObject(nextRow, 3, coverApprovedDate);
        subTable.setObject(nextRow, 4, packageCopyDate);
        subTable.setObject(nextRow, 5, stickerCopyDate);
        subTable.setObject(nextRow, 6, djQtyDate);
        subTable.setObject(nextRow, 7, fmDate);
        subTable.setObject(nextRow, 8, manuImportDate);
        subTable.setObject(nextRow, 9, sepsDate);
        subTable.setObject(nextRow, 10, bomDate);
        subTable.setObject(nextRow, 11, masterAtPlantDate);
        subTable.setObject(nextRow, 12, filmAtPrinterDate);
        subTable.setObject(nextRow, 13, printAtPlantDate);
        subTable.setObject(nextRow, 14, sel.getSelectionComments());
        subTable.setSpan(nextRow, 14, new Dimension(1, 2));
        subTable.setRowFont(nextRow, new Font("Arial", 1, 7));
        Font holidayFont = new Font("Arial", 3, 7);
        for (int colIdx = 3; colIdx <= 13; colIdx++) {
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
        subTable.setBackground(nextRow, 13, Color.lightGray);
        subTable.setBackground(nextRow, 14, Color.white);
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
        subTable.setColBorderColor(nextRow, 14, Color.lightGray);
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
        subTable.setColBorder(nextRow, 12, 266240);
        subTable.setColBorder(nextRow, 13, 266240);
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
        subTable.setAlignment(nextRow, 13, 10);
        subTable.setAlignment(nextRow, 14, 9);
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
        subTable.setColBorderColor(nextRow, 14, Color.lightGray);
        subTable.setRowInsets(nextRow, new Insets(0, 0, 0, 0));
        subTable.setRowFont(nextRow, new Font("Arial", 0, 7));
        subTable.setRowAlignment(nextRow, 10);
        subTable.setObject(nextRow, 0, "");
        subTable.setObject(nextRow, 1, String.valueOf(upc) + "\n" + localProductNumber + "\n" + artistName + "\n" + title);
        subTable.setObject(nextRow, 2, labelAndPackage);
        subTable.setObject(nextRow, 3, coverApproved);
        subTable.setObject(nextRow, 4, packageCopy);
        subTable.setObject(nextRow, 5, stickerCopy);
        subTable.setObject(nextRow, 6, djQty);
        subTable.setObject(nextRow, 7, fm);
        subTable.setObject(nextRow, 8, manuImportQty);
        subTable.setObject(nextRow, 9, seps);
        subTable.setObject(nextRow, 10, bom);
        subTable.setObject(nextRow, 11, masterAtPlant);
        subTable.setObject(nextRow, 12, filmAtPrinter);
        subTable.setObject(nextRow, 13, printAtPlant);
        subTable.setObject(nextRow, 14, sel.getSelectionComments());
        body = new SectionBand(report);
        body.addTable(subTable, new Rectangle(800, 800));
        double lfLineCount = 1.5D;
        if (commentLines > 0 || labelAndPackage.length() > 17 || sel.getTitle().length() > 20) {
          body.setHeight(1.5F);
        } else {
          body.setHeight(1.0F);
        } 
        if (commentLines > 0 || labelAndPackage.length() > 17 || sel.getTitle().length() > 20 || 
          coverApproved.length() > 25 || masterAtPlant.length() > 25 || manuImportQty.length() > 25 || 
          packageCopy.length() > 25 || stickerCopy.length() > 25 || 
          seps.length() > 25 || 
          packageFilm.length() > 25 || 
          printAtPlant.length() > 25) {
          if (lfLineCount < commentLines * 0.3D)
            lfLineCount = commentLines * 0.3D; 
          if (lfLineCount < (labelAndPackage.length() / 8) * 0.3D)
            lfLineCount = (labelAndPackage.length() / 8) * 0.3D; 
          if (lfLineCount < (packageFilm.length() / 8) * 0.3D)
            lfLineCount = (packageFilm.length() / 8) * 0.3D; 
          if (lfLineCount < (printAtPlant.length() / 8) * 0.3D)
            lfLineCount = (printAtPlant.length() / 8) * 0.3D; 
          if (lfLineCount < (sel.getTitle().length() / 8) * 0.3D)
            lfLineCount = (sel.getTitle().length() / 8) * 0.3D; 
          if (lfLineCount < (coverApproved.length() / 7) * 0.3D)
            lfLineCount = (coverApproved.length() / 7) * 0.3D; 
          if (lfLineCount < (masterAtPlant.length() / 7) * 0.3D)
            lfLineCount = (masterAtPlant.length() / 7) * 0.3D; 
          if (lfLineCount < (manuImportQty.length() / 7) * 0.3D)
            lfLineCount = (manuImportQty.length() / 7) * 0.3D; 
          if (lfLineCount < (packageCopy.length() / 7) * 0.3D)
            lfLineCount = (packageCopy.length() / 7) * 0.3D; 
          if (lfLineCount < (stickerCopy.length() / 7) * 0.3D)
            lfLineCount = (stickerCopy.length() / 7) * 0.3D; 
          if (lfLineCount < (seps.length() / 7) * 0.3D)
            lfLineCount = (seps.length() / 7) * 0.3D; 
          body.setHeight((float)lfLineCount);
        } else if (commentLines > 0 || labelAndPackage.length() > 17 || sel.getTitle().length() > 20 || 
          coverApproved.length() > 5 || masterAtPlant.length() > 5 || manuImportQty.length() > 5 || 
          packageCopy.length() > 5 || stickerCopy.length() > 5 || 
          seps.length() > 5 || 
          packageFilm.length() > 5 || 
          printAtPlant.length() > 5) {
          if (lfLineCount < commentLines * 0.3D)
            lfLineCount = commentLines * 0.3D; 
          if (lfLineCount < (labelAndPackage.length() / 5) * 0.3D)
            lfLineCount = (labelAndPackage.length() / 5) * 0.3D; 
          if (lfLineCount < (packageFilm.length() / 5) * 0.3D)
            lfLineCount = (packageFilm.length() / 5) * 0.3D; 
          if (lfLineCount < (printAtPlant.length() / 5) * 0.3D)
            lfLineCount = (printAtPlant.length() / 5) * 0.3D; 
          if (lfLineCount < (sel.getTitle().length() / 5) * 0.3D)
            lfLineCount = (sel.getTitle().length() / 5) * 0.3D; 
          if (lfLineCount < (coverApproved.length() / 5) * 0.3D)
            lfLineCount = (coverApproved.length() / 5) * 0.3D; 
          if (lfLineCount < (masterAtPlant.length() / 5) * 0.3D)
            lfLineCount = (masterAtPlant.length() / 5) * 0.3D; 
          if (lfLineCount < (manuImportQty.length() / 5) * 0.3D)
            lfLineCount = (manuImportQty.length() / 5) * 0.3D; 
          if (lfLineCount < (packageCopy.length() / 5) * 0.3D)
            lfLineCount = (packageCopy.length() / 5) * 0.3D; 
          if (lfLineCount < (stickerCopy.length() / 5) * 0.3D)
            lfLineCount = (stickerCopy.length() / 5) * 0.3D; 
          if (lfLineCount < (seps.length() / 5) * 0.3D)
            lfLineCount = (seps.length() / 5) * 0.3D; 
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
      System.out.println(">>>>>>>>ReportHandler.fillClassicProductionScheduleForPrint(): exception: " + e);
      e.printStackTrace();
    } 
    statusJSPupdate = null;
  }
}


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\ClassicsProductionScheduleForPrintSubHandler.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */