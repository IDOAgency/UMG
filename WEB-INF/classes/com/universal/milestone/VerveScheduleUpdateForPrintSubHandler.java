package WEB-INF.classes.com.universal.milestone;

import com.techempower.ComponentLog;
import com.techempower.gemini.Context;
import com.techempower.gemini.GeminiApplication;
import com.universal.milestone.Form;
import com.universal.milestone.MilestoneHelper;
import com.universal.milestone.MilestoneHelper_2;
import com.universal.milestone.Schedule;
import com.universal.milestone.ScheduledTask;
import com.universal.milestone.SecureHandler;
import com.universal.milestone.Selection;
import com.universal.milestone.SelectionManager;
import com.universal.milestone.StringDescComparator;
import com.universal.milestone.VerveScheduleUpdateForPrintSubHandler;
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
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Vector;
import javax.servlet.http.HttpServletResponse;

public class VerveScheduleUpdateForPrintSubHandler extends SecureHandler {
  public static final String COMPONENT_CODE = "hCProd";
  
  public GeminiApplication application;
  
  public ComponentLog log;
  
  public VerveScheduleUpdateForPrintSubHandler(GeminiApplication application) {
    this.application = application;
    this.log = application.getLog("hCProd");
  }
  
  public String getDescription() { return "Sub Report"; }
  
  protected static void fillVerveScheduleUpdateForPrint(XStyleSheet report, Context context) {
    int COL_LINE_STYLE = 4097;
    int HEADER_FONT_SIZE = 12;
    double ldLineVal = 0.3D;
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
      Hashtable selTable = MilestoneHelper.groupSelectionsByTypeAndCategory(selections);
      Enumeration types = selTable.keys();
      Vector typeVector = new Vector();
      int numOfSelectionsInAdvanceCategory = 0;
      Vector toIgnore = new Vector();
      while (types.hasMoreElements())
        typeVector.addElement(types.nextElement()); 
      Collections.sort(typeVector);
      DefaultTableLens table_contents = null;
      DefaultTableLens rowCountTable = null;
      DefaultTableLens columnHeaderTable = null;
      for (int n = 0; n < typeVector.size(); n++) {
        String type = (String)typeVector.elementAt(n);
        String typeHeaderText = !type.trim().equals("") ? type : "Other";
        Hashtable categoryTable = (Hashtable)selTable.get(type);
        DefaultTableLens subTable = null;
        if (categoryTable != null) {
          Enumeration categories = categoryTable.keys();
          Vector currentTypeCategories = new Vector();
          while (categories.hasMoreElements())
            currentTypeCategories.add((String)categories.nextElement()); 
          Collections.sort(currentTypeCategories, new StringDescComparator());
          Iterator theCategories = currentTypeCategories.iterator();
          int categoryCount = 0;
          while (theCategories.hasNext()) {
            String category = (String)theCategories.next();
            selections = (Vector)categoryTable.get(category);
            if (category.equalsIgnoreCase("Advances"))
              continue; 
            int numSelections = selections.size() * 2;
            int numRows = numSelections + 5;
            rowCountTable = new DefaultTableLens(2, 10000);
            table_contents = new DefaultTableLens(1, 17);
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
            table_contents.setColWidth(3, 70);
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
            table_contents.setColWidth(14, 70);
            table_contents.setColWidth(15, 70);
            table_contents.setColWidth(16, 180);
            table_contents.setRowBorderColor(nextRow, Color.lightGray);
            table_contents.setRowBorder(4097);
            table_contents.setColBorder(4097);
            if (typeHeaderText.equals("Promotional"))
              typeHeaderText = "Promos"; 
            int cols = 17;
            table_contents.setObject(nextRow, 0, "");
            table_contents.setSpan(nextRow, 0, new Dimension(cols, 1));
            table_contents.setRowHeight(nextRow, 15);
            table_contents.setRowBackground(nextRow, Color.white);
            for (int col = -1; col < cols; col++) {
              table_contents.setColBorderColor(nextRow, col, Color.black);
              table_contents.setColBorder(nextRow, col, 4097);
            } 
            table_contents.setRowBorderColor(nextRow, Color.black);
            table_contents.setRowBorder(nextRow, 266240);
            table_contents.setAlignment(nextRow, 0, 2);
            table_contents.setObject(nextRow, 0, typeHeaderText);
            table_contents.setRowFont(nextRow, new Font("Arial", 3, 12));
            nextRow++;
            hbandType.addTable(table_contents, new Rectangle(0, 0, 800, 25));
            columnHeaderTable = new DefaultTableLens(1, 17);
            nextRow = 0;
            columnHeaderTable.setColWidth(0, 95);
            columnHeaderTable.setColWidth(1, 225);
            columnHeaderTable.setColWidth(2, 140);
            columnHeaderTable.setColWidth(3, 70);
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
            columnHeaderTable.setColWidth(14, 70);
            columnHeaderTable.setColWidth(15, 70);
            columnHeaderTable.setColWidth(16, 180);
            columnHeaderTable.setRowAlignment(nextRow, 34);
            columnHeaderTable.setObject(nextRow, 0, "U.S. &\nInt'l\nRelease");
            columnHeaderTable.setObject(nextRow, 1, "UPC\nUS #/Int'l\nArtist/Title");
            columnHeaderTable.setObject(nextRow, 2, "Label &\nPackage");
            columnHeaderTable.setObject(nextRow, 3, "Photo\nShoot");
            columnHeaderTable.setObject(nextRow, 4, "Master\nto plant");
            columnHeaderTable.setObject(nextRow, 5, "Manf.\nCopy\nShips");
            columnHeaderTable.setObject(nextRow, 6, "Package\nCopy");
            columnHeaderTable.setObject(nextRow, 7, "Sticker\nCopy");
            columnHeaderTable.setObject(nextRow, 8, "Adv.\nCDs");
            columnHeaderTable.setObject(nextRow, 9, "Cover\nArt");
            columnHeaderTable.setObject(nextRow, 10, "Seps");
            columnHeaderTable.setObject(nextRow, 11, "Japan");
            columnHeaderTable.setObject(nextRow, 12, "Package\nFilm\nShip");
            columnHeaderTable.setObject(nextRow, 13, "Package\nFilm");
            columnHeaderTable.setObject(nextRow, 14, "Print\nat Plant");
            columnHeaderTable.setObject(nextRow, 15, "Depot");
            columnHeaderTable.setObject(nextRow, 16, "Comments");
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
            columnHeaderTable.setColBorderColor(nextRow, 15, Color.lightGray);
            columnHeaderTable.setColBorderColor(nextRow, 16, Color.lightGray);
            columnHeaderTable.setRowBorderColor(nextRow, Color.lightGray);
            nextRow++;
            hbandType.addTable(columnHeaderTable, new Rectangle(0, 24, 800, 39));
            hbandType.setBottomBorder(0);
            DefaultTableLens categoryTableLens = new DefaultTableLens(1, 17);
            nextRow = 0;
            categoryTableLens.setObject(nextRow, 0, category);
            categoryTableLens.setSpan(nextRow, 0, new Dimension(17, 1));
            categoryTableLens.setRowFont(nextRow, new Font("Arial", 1, 12));
            categoryTableLens.setRowHeight(nextRow, 20);
            categoryTableLens.setColBorderColor(nextRow, -1, Color.white);
            categoryTableLens.setColBorderColor(nextRow, 16, Color.white);
            categoryTableLens.setRowBorderColor(nextRow, Color.lightGray);
            categoryTableLens.setRowBorderColor(nextRow - 1, Color.lightGray);
            nextRow++;
            hbandCategory.addTable(categoryTableLens, new Rectangle(800, 800));
            hbandCategory.setBottomBorder(0);
            footer.setVisible(false);
            footer.setHeight(0.1F);
            footer.setShrinkToFit(true);
            footer.setBottomBorder(0);
            group = new DefaultSectionLens(null, group, hbandCategory);
            nextRow = 0;
            Hashtable sortedByStatus = MilestoneHelper.groupSelectionsByStatus(selections);
            if (sortedByStatus != null) {
              Vector statuses = new Vector();
              Enumeration selectionsSoredByStatus = sortedByStatus.keys();
              while (selectionsSoredByStatus.hasMoreElements()) {
                String status = (String)selectionsSoredByStatus.nextElement();
                statuses.add(status);
              } 
              Collections.sort(statuses);
              Iterator theStatuses = statuses.iterator();
              int statusCount = 0;
              while (theStatuses != null && theStatuses.hasNext()) {
                String status = (String)theStatuses.next();
                selections = (Vector)sortedByStatus.get(status);
                if (selections != null) {
                  Vector allSelections = MilestoneHelper.groupSelectionsByReleaseMonth(selections);
                  if (allSelections == null)
                    allSelections = new Vector(); 
                  for (int i = 0; i < allSelections.size(); i++) {
                    selections = (Vector)allSelections.elementAt(i);
                    if (selections != null && !selections.isEmpty()) {
                      int commentLines = 0;
                      for (int j = 0; j < selections.size(); j++) {
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
                        if (sel.getPrefixID() != null && SelectionManager.getLookupObjectValue(sel.getPrefixID()) != null)
                          localProductNumber = SelectionManager.getLookupObjectValue(sel.getPrefixID()); 
                        localProductNumber = String.valueOf(localProductNumber) + sel.getSelectionNo();
                        String ArtistTile = "";
                        ArtistTile = String.valueOf(ArtistTile) + localProductNumber + "\n";
                        ArtistTile = String.valueOf(ArtistTile) + sel.getArtist() + "\n";
                        ArtistTile = String.valueOf(ArtistTile) + sel.getTitle();
                        String comment = (sel.getSelectionComments() != null) ? sel.getSelectionComments() : " ";
                        String label = (sel.getLabel() != null) ? sel.getLabel().getName() : " ";
                        String packaging = (sel.getSelectionPackaging() != null) ? sel.getSelectionPackaging() : " ";
                        commentLines = MilestoneHelper.lineCount(comment, "");
                        String labelAndPackage = String.valueOf(label) + "\n" + packaging;
                        for (int y = 0; y < commentLines; y++)
                          labelAndPackage = String.valueOf(labelAndPackage) + "\n"; 
                        Schedule schedule = sel.getSchedule();
                        String photoShootDate = "", masterToPlantDate = "", manfCopyShipsDate = "";
                        String packageCopyDate = "", stickerCopyDate = "", advCDsDate = "", coverArtDate = "";
                        String sepsDate = "", japanDate = "", packageFilmShipDate = "", packageFilmDate = "";
                        String printAtPlantDate = "", depotDate = "";
                        String photoShoot = "", masterToPlant = "", manfCopyShips = "";
                        String packageCopy = "", stickerCopy = "", advCDs = "", coverArt = "";
                        String seps = "", japan = "", packageFilmShip = "", packageFilm = "";
                        String printAtPlant = "", depot = "";
                        Vector tasks = null;
                        if (schedule != null)
                          tasks = schedule.getTasks(); 
                        if (tasks != null) {
                          Iterator it = tasks.iterator();
                          int dayTypeCalendarGroup = sel.getCalendarGroup();
                          while (it != null && it.hasNext()) {
                            ScheduledTask task = (ScheduledTask)it.next();
                            String name = task.getName();
                            try {
                              if (name != null) {
                                name = name.trim();
                                if (name.equalsIgnoreCase("Photo shoot complete")) {
                                  photoShootDate = String.valueOf(MilestoneHelper.getShortDate(task.getDueDate())) + 
                                    " " + MilestoneHelper.getDayType(dayTypeCalendarGroup, task.getDueDate());
                                  if (task.getScheduledTaskStatus() != null && 
                                    task.getScheduledTaskStatus().equals("N/A")) {
                                    photoShoot = "N/A";
                                  } else {
                                    photoShoot = MilestoneHelper.getFormatedDate(task.getCompletionDate());
                                  } 
                                  photoShoot = String.valueOf(photoShoot) + "\n";
                                  photoShoot = String.valueOf(photoShoot) + ((task.getComments() != null) ? task.getComments() : "");
                                  continue;
                                } 
                                if (name.equalsIgnoreCase("Master tapes ship to plant")) {
                                  masterToPlantDate = String.valueOf(MilestoneHelper.getShortDate(task.getDueDate())) + 
                                    " " + MilestoneHelper.getDayType(dayTypeCalendarGroup, task.getDueDate());
                                  if (task.getScheduledTaskStatus() != null && 
                                    task.getScheduledTaskStatus().equals("N/A")) {
                                    masterToPlant = "N/A";
                                  } else {
                                    masterToPlant = MilestoneHelper.getFormatedDate(task.getCompletionDate());
                                  } 
                                  masterToPlant = String.valueOf(masterToPlant) + "\n";
                                  masterToPlant = String.valueOf(masterToPlant) + ((task.getComments() != null) ? task.getComments() : " ");
                                  continue;
                                } 
                                if (name.equalsIgnoreCase("Manufacturing copy ships")) {
                                  manfCopyShipsDate = String.valueOf(MilestoneHelper.getShortDate(task.getDueDate())) + 
                                    " " + MilestoneHelper.getDayType(dayTypeCalendarGroup, task.getDueDate());
                                  if (task.getScheduledTaskStatus() != null && 
                                    task.getScheduledTaskStatus().equals("N/A")) {
                                    manfCopyShips = "N/A";
                                  } else {
                                    manfCopyShips = MilestoneHelper.getFormatedDate(task.getCompletionDate());
                                  } 
                                  manfCopyShips = String.valueOf(manfCopyShips) + "\n";
                                  manfCopyShips = String.valueOf(manfCopyShips) + ((task.getComments() != null) ? task.getComments() : " ");
                                  continue;
                                } 
                                if (name.equalsIgnoreCase("Final packaging copy due")) {
                                  packageCopyDate = String.valueOf(MilestoneHelper.getShortDate(task.getDueDate())) + 
                                    " " + MilestoneHelper.getDayType(dayTypeCalendarGroup, task.getDueDate());
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
                                if (name.equalsIgnoreCase("Final sticker copy due")) {
                                  stickerCopyDate = String.valueOf(MilestoneHelper.getShortDate(task.getDueDate())) + 
                                    " " + MilestoneHelper.getDayType(dayTypeCalendarGroup, task.getDueDate());
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
                                if (name.equalsIgnoreCase("Cover art due")) {
                                  coverArtDate = String.valueOf(MilestoneHelper.getShortDate(task.getDueDate())) + 
                                    " " + MilestoneHelper.getDayType(dayTypeCalendarGroup, task.getDueDate());
                                  if (task.getScheduledTaskStatus() != null && 
                                    task.getScheduledTaskStatus().equals("N/A")) {
                                    coverArt = "N/A";
                                  } else {
                                    coverArt = MilestoneHelper.getFormatedDate(task.getCompletionDate());
                                  } 
                                  coverArt = String.valueOf(coverArt) + "\n";
                                  coverArt = String.valueOf(coverArt) + ((task.getComments() != null) ? task.getComments() : " ");
                                  continue;
                                } 
                                if (name.equalsIgnoreCase("Package film ships to printer")) {
                                  packageFilmShipDate = String.valueOf(MilestoneHelper.getShortDate(task.getDueDate())) + 
                                    " " + MilestoneHelper.getDayType(dayTypeCalendarGroup, task.getDueDate());
                                  if (task.getScheduledTaskStatus() != null && 
                                    task.getScheduledTaskStatus().equals("N/A")) {
                                    packageFilmShip = "N/A";
                                  } else {
                                    packageFilmShip = MilestoneHelper.getFormatedDate(task.getCompletionDate());
                                  } 
                                  packageFilmShip = String.valueOf(packageFilmShip) + "\n";
                                  packageFilmShip = String.valueOf(packageFilmShip) + ((task.getComments() != null) ? task.getComments() : " ");
                                  continue;
                                } 
                                if (name.equalsIgnoreCase("Print received at Plant")) {
                                  printAtPlantDate = String.valueOf(MilestoneHelper.getShortDate(task.getDueDate())) + 
                                    " " + MilestoneHelper.getDayType(dayTypeCalendarGroup, task.getDueDate());
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
                                if (name.equalsIgnoreCase("Depot date")) {
                                  depotDate = String.valueOf(MilestoneHelper.getShortDate(task.getDueDate())) + 
                                    " " + MilestoneHelper.getDayType(dayTypeCalendarGroup, task.getDueDate());
                                  if (task.getScheduledTaskStatus() != null && 
                                    task.getScheduledTaskStatus().equals("N/A")) {
                                    depot = "N/A";
                                  } else {
                                    depot = MilestoneHelper.getFormatedDate(task.getCompletionDate());
                                  } 
                                  depot = String.valueOf(depot) + "\n";
                                  depot = String.valueOf(depot) + ((task.getComments() != null) ? task.getComments() : " ");
                                  continue;
                                } 
                                if (name.equalsIgnoreCase("Mechanical to separations")) {
                                  sepsDate = String.valueOf(MilestoneHelper.getShortDate(task.getDueDate())) + 
                                    " " + MilestoneHelper.getDayType(dayTypeCalendarGroup, task.getDueDate());
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
                                if (name.equalsIgnoreCase("Japan due")) {
                                  japanDate = String.valueOf(MilestoneHelper.getShortDate(task.getDueDate())) + 
                                    " " + MilestoneHelper.getDayType(dayTypeCalendarGroup, task.getDueDate());
                                  if (task.getScheduledTaskStatus() != null && 
                                    task.getScheduledTaskStatus().equals("N/A")) {
                                    japan = "N/A";
                                  } else {
                                    japan = MilestoneHelper.getFormatedDate(task.getCompletionDate());
                                  } 
                                  japan = String.valueOf(japan) + "\n";
                                  japan = String.valueOf(japan) + ((task.getComments() != null) ? task.getComments() : " ");
                                  continue;
                                } 
                                if (name.equalsIgnoreCase("Advanced CDs ordered")) {
                                  advCDsDate = String.valueOf(MilestoneHelper.getShortDate(task.getDueDate())) + 
                                    " " + MilestoneHelper.getDayType(dayTypeCalendarGroup, task.getDueDate());
                                  if (task.getScheduledTaskStatus() != null && 
                                    task.getScheduledTaskStatus().equals("N/A")) {
                                    advCDs = "N/A";
                                  } else {
                                    advCDs = MilestoneHelper.getFormatedDate(task.getCompletionDate());
                                  } 
                                  advCDs = String.valueOf(advCDs) + "\n";
                                  advCDs = String.valueOf(advCDs) + ((task.getComments() != null) ? task.getComments() : " ");
                                  continue;
                                } 
                                if (name.equalsIgnoreCase("Package Film received by Printer")) {
                                  packageFilmDate = String.valueOf(MilestoneHelper.getShortDate(task.getDueDate())) + 
                                    " " + MilestoneHelper.getDayType(dayTypeCalendarGroup, task.getDueDate());
                                  if (task.getScheduledTaskStatus() != null && 
                                    task.getScheduledTaskStatus().equals("N/A")) {
                                    packageFilm = "N/A";
                                  } else {
                                    packageFilm = MilestoneHelper.getFormatedDate(task.getCompletionDate());
                                  } 
                                  packageFilm = String.valueOf(packageFilm) + "\n";
                                  packageFilm = String.valueOf(packageFilm) + ((task.getComments() != null) ? task.getComments() : " ");
                                } 
                              } 
                            } catch (Exception e) {
                              e.printStackTrace();
                            } 
                          } 
                        } 
                        nextRow = 0;
                        subTable = new DefaultTableLens(2, 17);
                        subTable.setColWidth(0, 95);
                        subTable.setColWidth(1, 225);
                        subTable.setColWidth(2, 140);
                        subTable.setColWidth(3, 70);
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
                        subTable.setColWidth(14, 70);
                        subTable.setColWidth(15, 70);
                        subTable.setColWidth(16, 180);
                        subTable.setColBorderColor(nextRow, Color.lightGray);
                        subTable.setRowBorderColor(nextRow, Color.lightGray);
                        subTable.setRowBorder(4097);
                        subTable.setColBorder(4097);
                        subTable.setRowInsets(nextRow, new Insets(0, 0, 0, 0));
                        subTable.setRowAlignment(nextRow, 2);
                        subTable.setSpan(nextRow, 0, new Dimension(1, 2));
                        subTable.setObject(nextRow, 0, USIntRelease);
                        subTable.setObject(nextRow, 1, upc);
                        subTable.setObject(nextRow, 2, "Due Dates");
                        subTable.setObject(nextRow, 3, photoShootDate);
                        subTable.setObject(nextRow, 4, masterToPlantDate);
                        subTable.setObject(nextRow, 5, manfCopyShipsDate);
                        subTable.setObject(nextRow, 6, packageCopyDate);
                        subTable.setObject(nextRow, 7, stickerCopyDate);
                        subTable.setObject(nextRow, 8, advCDsDate);
                        subTable.setObject(nextRow, 9, coverArtDate);
                        subTable.setObject(nextRow, 10, sepsDate);
                        subTable.setObject(nextRow, 11, japanDate);
                        subTable.setObject(nextRow, 12, packageFilmShipDate);
                        subTable.setObject(nextRow, 13, packageFilmDate);
                        subTable.setObject(nextRow, 14, printAtPlantDate);
                        subTable.setObject(nextRow, 15, depotDate);
                        subTable.setObject(nextRow, 16, sel.getSelectionComments());
                        subTable.setSpan(nextRow, 16, new Dimension(1, 2));
                        subTable.setRowFont(nextRow, new Font("Arial", 1, 7));
                        subTable.setFont(nextRow, 0, new Font("Arial", 0, 7));
                        Font holidayFont = new Font("Arial", 3, 7);
                        Font nonHolidayFont = new Font("Arial", 1, 7);
                        for (int colIdx = 3; colIdx <= 15; colIdx++) {
                          String dueDate = subTable.getObject(nextRow, colIdx).toString();
                          if (dueDate != null && dueDate.length() > 0) {
                            char lastChar = dueDate.charAt(dueDate.length() - 1);
                            if (Character.isLetter(lastChar)) {
                              subTable.setFont(nextRow, colIdx, holidayFont);
                            } else {
                              subTable.setFont(nextRow, colIdx, nonHolidayFont);
                            } 
                          } 
                        } 
                        subTable.setFont(nextRow, 16, new Font("Arial", 0, 7));
                        subTable.setAlignment(nextRow, 0, 10);
                        subTable.setAlignment(nextRow, 16, 9);
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
                        subTable.setBackground(nextRow, 14, Color.lightGray);
                        subTable.setBackground(nextRow, 15, Color.lightGray);
                        subTable.setBackground(nextRow, 16, Color.white);
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
                        subTable.setColBorderColor(nextRow, 15, Color.lightGray);
                        subTable.setColBorderColor(nextRow, 16, Color.lightGray);
                        subTable.setRowBorderColor(nextRow, Color.lightGray);
                        subTable.setRowBorderColor(nextRow - 1, Color.lightGray);
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
                        subTable.setColBorder(nextRow, 14, 266240);
                        subTable.setColBorder(nextRow, 15, 266240);
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
                        subTable.setAlignment(nextRow, 14, 10);
                        subTable.setAlignment(nextRow, 15, 10);
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
                        subTable.setColBorderColor(nextRow, 15, Color.lightGray);
                        subTable.setColBorderColor(nextRow, 16, Color.lightGray);
                        subTable.setRowInsets(nextRow, new Insets(0, 0, 0, 0));
                        subTable.setRowFont(nextRow, new Font("Arial", 0, 7));
                        subTable.setRowAlignment(nextRow, 10);
                        subTable.setObject(nextRow, 0, "");
                        subTable.setObject(nextRow, 1, ArtistTile);
                        subTable.setObject(nextRow, 2, labelAndPackage);
                        subTable.setObject(nextRow, 3, photoShoot);
                        subTable.setObject(nextRow, 4, masterToPlant);
                        subTable.setObject(nextRow, 5, manfCopyShips);
                        subTable.setObject(nextRow, 6, packageCopy);
                        subTable.setObject(nextRow, 7, stickerCopy);
                        subTable.setObject(nextRow, 8, advCDs);
                        subTable.setObject(nextRow, 9, coverArt);
                        subTable.setObject(nextRow, 10, seps);
                        subTable.setObject(nextRow, 11, japan);
                        subTable.setObject(nextRow, 12, packageFilmShip);
                        subTable.setObject(nextRow, 13, packageFilm);
                        subTable.setObject(nextRow, 14, printAtPlant);
                        subTable.setObject(nextRow, 15, depot);
                        body = new SectionBand(report);
                        body.addTable(subTable, new Rectangle(800, 800));
                        double lfLineCount = 1.5D;
                        if (commentLines > 0 || labelAndPackage.length() > 17 || sel.getTitle().length() > 20) {
                          body.setHeight(1.5F);
                        } else {
                          body.setHeight(1.0F);
                        } 
                        if (commentLines > 0 || labelAndPackage.length() > 17 || sel.getTitle().length() > 20 || 
                          photoShoot.length() > 25 || masterToPlant.length() > 25 || manfCopyShips.length() > 25 || 
                          packageCopy.length() > 25 || stickerCopy.length() > 25 || 
                          advCDs.length() > 25 || coverArt.length() > 25 || 
                          seps.length() > 25 || japan.length() > 25 || 
                          packageFilmShip.length() > 25 || 
                          packageFilm.length() > 25 || 
                          printAtPlant.length() > 25 || depot.length() > 25) {
                          if (lfLineCount < commentLines * 0.3D)
                            lfLineCount = commentLines * 0.3D; 
                          if (lfLineCount < (packageFilmShip.length() / 7) * 0.3D)
                            lfLineCount = (packageFilmShip.length() / 7) * 0.3D; 
                          if (lfLineCount < (labelAndPackage.length() / 8) * 0.3D)
                            lfLineCount = (labelAndPackage.length() / 8) * 0.3D; 
                          if (lfLineCount < (packageFilm.length() / 8) * 0.3D)
                            lfLineCount = (packageFilm.length() / 8) * 0.3D; 
                          if (lfLineCount < (printAtPlant.length() / 8) * 0.3D)
                            lfLineCount = (printAtPlant.length() / 8) * 0.3D; 
                          if (lfLineCount < (sel.getTitle().length() / 8) * 0.3D)
                            lfLineCount = (sel.getTitle().length() / 8) * 0.3D; 
                          if (lfLineCount < (photoShoot.length() / 7) * 0.3D)
                            lfLineCount = (photoShoot.length() / 7) * 0.3D; 
                          if (lfLineCount < (masterToPlant.length() / 7) * 0.3D)
                            lfLineCount = (masterToPlant.length() / 7) * 0.3D; 
                          if (lfLineCount < (manfCopyShips.length() / 7) * 0.3D)
                            lfLineCount = (manfCopyShips.length() / 7) * 0.3D; 
                          if (lfLineCount < (packageCopy.length() / 7) * 0.3D)
                            lfLineCount = (packageCopy.length() / 7) * 0.3D; 
                          if (lfLineCount < (stickerCopy.length() / 7) * 0.3D)
                            lfLineCount = (stickerCopy.length() / 7) * 0.3D; 
                          if (lfLineCount < (advCDs.length() / 7) * 0.3D)
                            lfLineCount = (advCDs.length() / 7) * 0.3D; 
                          if (lfLineCount < (coverArt.length() / 7) * 0.3D)
                            lfLineCount = (coverArt.length() / 7) * 0.3D; 
                          if (lfLineCount < (seps.length() / 7) * 0.3D)
                            lfLineCount = (seps.length() / 7) * 0.3D; 
                          if (lfLineCount < (japan.length() / 7) * 0.3D)
                            lfLineCount = (japan.length() / 7) * 0.3D; 
                          body.setHeight((float)lfLineCount);
                        } else if (commentLines > 0 || labelAndPackage.length() > 17 || sel.getTitle().length() > 20 || 
                          photoShoot.length() > 5 || masterToPlant.length() > 5 || manfCopyShips.length() > 5 || 
                          packageCopy.length() > 5 || stickerCopy.length() > 5 || 
                          advCDs.length() > 5 || coverArt.length() > 5 || 
                          seps.length() > 5 || japan.length() > 5 || 
                          packageFilmShip.length() > 5 || 
                          packageFilm.length() > 5 || 
                          printAtPlant.length() > 5 || depot.length() > 5) {
                          if (lfLineCount < commentLines * 0.3D)
                            lfLineCount = commentLines * 0.3D; 
                          if (lfLineCount < (packageFilmShip.length() / 5) * 0.3D)
                            lfLineCount = (packageFilmShip.length() / 5) * 0.3D; 
                          if (lfLineCount < (labelAndPackage.length() / 5) * 0.3D)
                            lfLineCount = (labelAndPackage.length() / 5) * 0.3D; 
                          if (lfLineCount < (packageFilm.length() / 5) * 0.3D)
                            lfLineCount = (packageFilm.length() / 5) * 0.3D; 
                          if (lfLineCount < (printAtPlant.length() / 5) * 0.3D)
                            lfLineCount = (printAtPlant.length() / 5) * 0.3D; 
                          if (lfLineCount < (sel.getTitle().length() / 5) * 0.3D)
                            lfLineCount = (sel.getTitle().length() / 5) * 0.3D; 
                          if (lfLineCount < (photoShoot.length() / 5) * 0.3D)
                            lfLineCount = (photoShoot.length() / 5) * 0.3D; 
                          if (lfLineCount < (masterToPlant.length() / 5) * 0.3D)
                            lfLineCount = (masterToPlant.length() / 5) * 0.3D; 
                          if (lfLineCount < (manfCopyShips.length() / 5) * 0.3D)
                            lfLineCount = (manfCopyShips.length() / 5) * 0.3D; 
                          if (lfLineCount < (packageCopy.length() / 5) * 0.3D)
                            lfLineCount = (packageCopy.length() / 5) * 0.3D; 
                          if (lfLineCount < (stickerCopy.length() / 5) * 0.3D)
                            lfLineCount = (stickerCopy.length() / 5) * 0.3D; 
                          if (lfLineCount < (advCDs.length() / 5) * 0.3D)
                            lfLineCount = (advCDs.length() / 5) * 0.3D; 
                          if (lfLineCount < (coverArt.length() / 5) * 0.3D)
                            lfLineCount = (coverArt.length() / 5) * 0.3D; 
                          if (lfLineCount < (seps.length() / 5) * 0.3D)
                            lfLineCount = (seps.length() / 5) * 0.3D; 
                          if (lfLineCount < (japan.length() / 5) * 0.3D)
                            lfLineCount = (japan.length() / 5) * 0.3D; 
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
                    } 
                  } 
                } 
              } 
            } 
          } 
        } 
        group = new DefaultSectionLens(hbandType, group, footer);
        report.addSection(group, rowCountTable);
        report.addPageBreak();
        group = null;
      } 
    } catch (Exception e) {
      System.out.println(">>>>>>>>ReportHandler.fillVerveScheduleUpdateForPrint(): exception: " + e);
      e.printStackTrace();
    } 
  }
}


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\VerveScheduleUpdateForPrintSubHandler.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */