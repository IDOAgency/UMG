package WEB-INF.classes.com.universal.milestone;

import com.techempower.ComponentLog;
import com.techempower.gemini.Context;
import com.techempower.gemini.FormCheckBox;
import com.techempower.gemini.GeminiApplication;
import com.universal.milestone.Cache;
import com.universal.milestone.Company;
import com.universal.milestone.EntPastRelDatesPrintSubHandler;
import com.universal.milestone.Environment;
import com.universal.milestone.Family;
import com.universal.milestone.Form;
import com.universal.milestone.JdbcConnector;
import com.universal.milestone.LookupObject;
import com.universal.milestone.MilestoneHelper;
import com.universal.milestone.PrefixCode;
import com.universal.milestone.ReportSelectionsHelper;
import com.universal.milestone.SecureHandler;
import com.universal.milestone.Selection;
import com.universal.milestone.SelectionManager;
import com.universal.milestone.SelectionStatus;
import com.universal.milestone.SelectionSubConfiguration;
import inetsoft.report.XStyleSheet;
import inetsoft.report.lens.DefaultTableLens;
import java.awt.Color;
import java.awt.Font;
import java.awt.Insets;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;
import javax.servlet.http.HttpServletResponse;

public class EntPastRelDatesPrintSubHandler extends SecureHandler {
  public static final String COMPONENT_CODE = "hEntPastRelDates";
  
  public GeminiApplication application;
  
  public ComponentLog log;
  
  public EntPastRelDatesPrintSubHandler(GeminiApplication application) {
    this.application = application;
    this.log = application.getLog("hEntPastRelDates");
  }
  
  public String getDescription() { return "Sub Report"; }
  
  protected static void fillEntPastRelDatesForPrint(XStyleSheet report, Context context, String reportType) {
    try {
      HttpServletResponse sresponse = context.getResponse();
      context.putDelivery("status", new String("start_gathering"));
      context.includeJSP("status.jsp", "hiddenFrame");
      sresponse.setContentType("text/plain");
      sresponse.flushBuffer();
    } catch (Exception exception) {}
    Vector initialSelections = getSingleSelectionsForReport(context, reportType);
    Vector selections = getValidSelections(initialSelections);
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
      Hashtable selTable = groupSelectionsByFamilyAndArtist(selections);
      Enumeration families = selTable.keys();
      Vector familyVector = new Vector();
      while (families.hasMoreElements())
        familyVector.addElement(families.nextElement()); 
      familyVector = MilestoneHelper.sortStrings(familyVector);
      int numArtist = 0;
      int numSelectsIDs = 0;
      int rowCounter = 0;
      for (int i = 0; i < familyVector.size(); i++) {
        String familyName = (familyVector.elementAt(i) != null) ? (String)familyVector.elementAt(i) : "";
        Hashtable artistTable = (Hashtable)selTable.get(familyName);
        Vector allFamilySelections = new Vector();
        if (artistTable != null) {
          Enumeration projects = artistTable.keys();
          Vector artistVector = new Vector();
          while (projects.hasMoreElements())
            artistVector.addElement((String)projects.nextElement()); 
          artistVector = MilestoneHelper.sortStrings(artistVector);
          for (int jj = 0; jj < artistVector.size(); jj++) {
            String selectsName = (artistVector.elementAt(jj) != null) ? (String)artistVector.elementAt(jj) : "";
            Vector selectVector = (Vector)artistTable.get(selectsName);
            if (selectVector != null) {
              selections = selectVector;
              for (int selc = 0; selc < selections.size(); selc++)
                allFamilySelections.add(selections.elementAt(selc)); 
            } 
          } 
        } 
        if (artistTable != null) {
          Enumeration projects = artistTable.keys();
          Vector artistVector = new Vector();
          while (projects.hasMoreElements())
            artistVector.addElement((String)projects.nextElement()); 
          artistVector = MilestoneHelper.sortStrings(artistVector);
          for (int rs = 0; rs < artistVector.size(); rs++) {
            String selectsName = (artistVector.elementAt(rs) != null) ? (String)artistVector.elementAt(rs) : "";
            Vector selectVector = (Vector)artistTable.get(selectsName);
            if (selectVector != null) {
              selections = selectVector;
              MilestoneHelper.setSelectionSorting(selections, 1);
              Collections.sort(selections);
              Vector fullLengthStrings = getConfigType(0);
              Vector singleStrings = getConfigType(1);
              Vector titlesVector = getTitlesVector(selections, allFamilySelections);
              for (int tu = 0; tu < titlesVector.size(); tu++) {
                String selTitle = (String)titlesVector.elementAt(tu);
                Vector allProjectIDVector = new Vector();
                for (int jt = 0; jt < selections.size(); jt++) {
                  String selectionTitle = ((Selection)selections.elementAt(jt)).getTitle();
                  if (selectionTitle.equals(selTitle)) {
                    String projectID = ((Selection)selections.elementAt(jt)).getProjectID();
                    if (!allProjectIDVector.contains(projectID))
                      allProjectIDVector.add(projectID); 
                  } 
                } 
                for (int p = 0; p < allProjectIDVector.size(); p++) {
                  String currentProjectID = (String)allProjectIDVector.elementAt(p);
                  for (int iq = 0; iq < selections.size(); iq++) {
                    if (((Selection)selections.elementAt(iq)).getProjectID().equals(currentProjectID) && (
                      (Selection)selections.elementAt(iq)).getTitle().equals(selTitle)) {
                      Selection selectionEntry = (Selection)selections.elementAt(iq);
                      String sin = "";
                      String singlesSubconfig = selectionEntry.getSelectionSubConfig().getSelectionSubConfigurationAbbreviation();
                      if (fullLengthStrings.contains(singlesSubconfig))
                        if (selectionHasValidSingles(allFamilySelections, currentProjectID, ((Selection)selections.elementAt(iq)).getStreetDate(), ((Selection)selections.elementAt(iq)).getSelectionStatus())) {
                          Vector albumAndSingles = new Vector();
                          albumAndSingles.add((Selection)selections.elementAt(iq));
                          for (int y = 0; y < allFamilySelections.size(); y++) {
                            Selection currentSelection = (Selection)allFamilySelections.elementAt(y);
                            String singleSubConfig = currentSelection.getSelectionSubConfig().getSelectionSubConfigurationAbbreviation();
                            String singleProjectID = currentSelection.getProjectID();
                            if (singleProjectID.equals(currentProjectID) && singleStrings.contains(singleSubConfig))
                              albumAndSingles.add(currentSelection); 
                          } 
                          for (int counter = 0; counter < albumAndSingles.size(); counter++)
                            rowCounter++; 
                        }  
                    } 
                  } 
                } 
              } 
            } 
          } 
        } 
      } 
      rowCounter++;
      int numRows = numSelectsIDs + 1;
      DefaultTableLens table_contents = new DefaultTableLens(rowCounter, 7);
      table_contents.setColBorder(0);
      table_contents.setRowBorderColor(Color.lightGray);
      table_contents.setColWidth(0, 100);
      table_contents.setColWidth(1, 180);
      table_contents.setColWidth(2, 300);
      table_contents.setColWidth(3, 130);
      table_contents.setColWidth(4, 100);
      table_contents.setColWidth(5, 80);
      table_contents.setColWidth(6, 150);
      table_contents.setAlignment(0, 0, 33);
      table_contents.setAlignment(0, 1, 33);
      table_contents.setAlignment(0, 2, 33);
      table_contents.setAlignment(0, 3, 33);
      table_contents.setAlignment(0, 4, 33);
      table_contents.setAlignment(0, 5, 33);
      table_contents.setAlignment(0, 6, 36);
      table_contents.setHeaderRowCount(1);
      table_contents.setRowBorder(-1, 0);
      table_contents.setRowBorder(0, 266240);
      table_contents.setRowBorderColor(0, Color.black);
      table_contents.setRowAlignment(0, 32);
      table_contents.setObject(0, 0, "Family");
      table_contents.setObject(0, 1, "Artist");
      table_contents.setObject(0, 2, "Title");
      table_contents.setObject(0, 3, "Catalog Number");
      table_contents.setObject(0, 4, "Project Number");
      table_contents.setObject(0, 5, "Sub Config");
      table_contents.setObject(0, 6, "Release Date");
      table_contents.setRowInsets(0, new Insets(0, 0, 0, 0));
      table_contents.setRowFont(0, new Font("Arial", 3, 11));
      int nextRow = 1;
      for (int n = 0; n < familyVector.size(); n++) {
        String family = (String)familyVector.elementAt(n);
        String familyHeaderText = !family.trim().equals("") ? family : "Other";
        Hashtable artistTable = (Hashtable)selTable.get(family);
        Vector allFamilySelections = new Vector();
        if (artistTable != null) {
          Enumeration projects = artistTable.keys();
          Vector artistVector = new Vector();
          while (projects.hasMoreElements())
            artistVector.addElement((String)projects.nextElement()); 
          artistVector = MilestoneHelper.sortStrings(artistVector);
          for (int jj = 0; jj < artistVector.size(); jj++) {
            String selectsName = (artistVector.elementAt(jj) != null) ? (String)artistVector.elementAt(jj) : "";
            Vector selectVector = (Vector)artistTable.get(selectsName);
            if (selectVector != null) {
              selections = selectVector;
              for (int selc = 0; selc < selections.size(); selc++)
                allFamilySelections.add(selections.elementAt(selc)); 
            } 
          } 
        } 
        if (artistTable != null) {
          Enumeration projects = artistTable.keys();
          Vector artistVector = new Vector();
          while (projects.hasMoreElements())
            artistVector.addElement((String)projects.nextElement()); 
          artistVector = MilestoneHelper.sortStrings(artistVector);
          for (int rs = 0; rs < artistVector.size(); rs++) {
            String selectsName = (artistVector.elementAt(rs) != null) ? (String)artistVector.elementAt(rs) : "";
            Vector selectVector = (Vector)artistTable.get(selectsName);
            if (selectVector != null) {
              selections = selectVector;
              MilestoneHelper.setSelectionSorting(selections, 1);
              Collections.sort(selections);
              Vector fullLengthStrings = getConfigType(0);
              Vector singleStrings = getConfigType(1);
              Vector titlesVector = getTitlesVector(selections, allFamilySelections);
              boolean hasAlbum = false;
              String albumArtist = "";
              for (int tu = 0; tu < titlesVector.size(); tu++) {
                String selTitle = (String)titlesVector.elementAt(tu);
                Vector allProjectIDVector = new Vector();
                for (int jt = 0; jt < selections.size(); jt++) {
                  String selectionTitle = ((Selection)selections.elementAt(jt)).getTitle();
                  if (selectionTitle.equals(selTitle)) {
                    String projectID = ((Selection)selections.elementAt(jt)).getProjectID();
                    if (!allProjectIDVector.contains(projectID))
                      allProjectIDVector.add(projectID); 
                  } 
                } 
                for (int p = 0; p < allProjectIDVector.size(); p++) {
                  String currentProjectID = (String)allProjectIDVector.elementAt(p);
                  for (int i = 0; i < selections.size(); i++) {
                    if (((Selection)selections.elementAt(i)).getProjectID().equals(currentProjectID) && (
                      (Selection)selections.elementAt(i)).getTitle().equals(selTitle)) {
                      Selection selectionEntry = (Selection)selections.elementAt(i);
                      String sin = "";
                      String singlesSubconfig = selectionEntry.getSelectionSubConfig().getSelectionSubConfigurationAbbreviation();
                      if (fullLengthStrings.contains(singlesSubconfig))
                        if (selectionHasValidSingles(allFamilySelections, currentProjectID, ((Selection)selections.elementAt(i)).getStreetDate(), ((Selection)selections.elementAt(i)).getSelectionStatus())) {
                          Vector albumAndSingles = new Vector();
                          albumAndSingles.add((Selection)selections.elementAt(i));
                          for (int y = 0; y < allFamilySelections.size(); y++) {
                            Selection currentSelection = (Selection)allFamilySelections.elementAt(y);
                            String singleSubConfig = currentSelection.getSelectionSubConfig().getSelectionSubConfigurationAbbreviation();
                            String singleProjectID = currentSelection.getProjectID();
                            if (singleProjectID.equals(currentProjectID) && singleStrings.contains(singleSubConfig))
                              albumAndSingles.add(currentSelection); 
                          } 
                          for (int counter = 0; counter < albumAndSingles.size(); counter++) {
                            String catalogNumber, singlesStreetDate;
                            selectionEntry = (Selection)albumAndSingles.elementAt(counter);
                            singlesSubconfig = selectionEntry.getSelectionSubConfig().getSelectionSubConfigurationAbbreviation();
                            SelectionStatus status = selectionEntry.getSelectionStatus();
                            String selectionStatus = (status != null && status.getName() != null) ? 
                              status.getName() : "";
                            if (selectionStatus.equals("TBS")) {
                              singlesStreetDate = "TBS";
                            } else {
                              singlesStreetDate = MilestoneHelper.getFormatedDate(selectionEntry.getStreetDate());
                            } 
                            String singlesTitle = selectionEntry.getTitle();
                            String singlesArtist = selectionEntry.getArtist();
                            String singlesFamily = selectionEntry.getFamily().getName();
                            String projectID = selectionEntry.getProjectID();
                            if (SelectionManager.getLookupObjectValue(selectionEntry.getPrefixID()).equals("")) {
                              catalogNumber = selectionEntry.getSelectionNo();
                            } else {
                              catalogNumber = String.valueOf(SelectionManager.getLookupObjectValue(selectionEntry.getPrefixID())) + selectionEntry.getSelectionNo();
                            } 
                            table_contents.setColWidth(0, 100);
                            table_contents.setColWidth(1, 180);
                            table_contents.setColWidth(2, 300);
                            table_contents.setColWidth(3, 130);
                            table_contents.setColWidth(4, 100);
                            table_contents.setColWidth(5, 80);
                            table_contents.setColWidth(6, 150);
                            if (fullLengthStrings.contains(singlesSubconfig)) {
                              hasAlbum = true;
                              albumArtist = singlesArtist;
                            } 
                            if (fullLengthStrings.contains(singlesSubconfig) || (
                              singleStrings.contains(singlesSubconfig) && !hasAlbum)) {
                              table_contents.setObject(nextRow, 0, singlesFamily);
                              table_contents.setObject(nextRow, 1, singlesArtist);
                              table_contents.setObject(nextRow, 2, singlesTitle);
                            } else if (!singlesArtist.equals(albumArtist)) {
                              table_contents.setObject(nextRow, 0, singlesFamily);
                              table_contents.setObject(nextRow, 1, singlesArtist);
                              table_contents.setObject(nextRow, 2, "    " + singlesTitle);
                            } else {
                              table_contents.setObject(nextRow, 0, "");
                              table_contents.setObject(nextRow, 1, "");
                              table_contents.setObject(nextRow, 2, "    " + singlesTitle);
                            } 
                            table_contents.setObject(nextRow, 3, catalogNumber);
                            table_contents.setObject(nextRow, 4, projectID);
                            table_contents.setObject(nextRow, 5, singlesSubconfig);
                            table_contents.setObject(nextRow, 6, singlesStreetDate);
                            if (fullLengthStrings.contains(singlesSubconfig)) {
                              table_contents.setRowFont(nextRow, new Font("Arial", 0, 11));
                            } else {
                              table_contents.setRowFont(nextRow, new Font("Arial", 0, 9));
                            } 
                            table_contents.setRowInsets(nextRow, new Insets(0, 0, 2, 0));
                            if (fullLengthStrings.contains(singlesSubconfig)) {
                              table_contents.setRowBorder(0);
                            } else if (counter + 1 == albumAndSingles.size()) {
                              table_contents.setRowBorder(nextRow, 4097);
                              table_contents.setRowBorderColor(nextRow, Color.lightGray);
                            } else {
                              table_contents.setRowBorder(0);
                            } 
                            table_contents.setAlignment(nextRow, 0, 9);
                            table_contents.setAlignment(nextRow, 1, 9);
                            table_contents.setAlignment(nextRow, 2, 9);
                            table_contents.setAlignment(nextRow, 3, 9);
                            table_contents.setAlignment(nextRow, 4, 9);
                            table_contents.setAlignment(nextRow, 5, 9);
                            table_contents.setAlignment(nextRow, 6, 12);
                            nextRow++;
                          } 
                        }  
                    } 
                  } 
                } 
              } 
            } 
          } 
        } 
      } 
      report.setElement("table_colheaders", table_contents);
    } catch (Exception e) {
      System.out.println(">>>>>>>>ReportHandler.fillEntRelScheduleForPrint(): exception: " + e);
    } 
  }
  
  public static Vector getSingleSelectionsForReport(Context context, String releaseType) {
    Vector companies = MilestoneHelper.getUserCompanies(context);
    Company company = null;
    Vector precache = new Vector();
    Selection selection = null;
    StringBuffer query = new StringBuffer();
    Form reportForm = (Form)context.getSessionValue("reportForm");
    boolean bScheduled = (reportForm.getElement("ScheduledReleases") != null) ? ((FormCheckBox)reportForm.getElement("ScheduledReleases")).isChecked() : 0;
    query.append("SELECT release_id, title, artist, street_date, configuration, sub_configuration, selection_no, status, prefix,  company_id, family_id, environment_id, label_id, project_no   from vi_Release_Header header WHERE ");
    query.append(" header.pd_indicator != 1 ");
    if (releaseType.equals("commercial")) {
      query.append(" AND header.release_type = 'CO' ");
    } else {
      query.append(" AND ( (header.sub_configuration IN ('ECD','CD','CASS','ALBUM') AND (header.release_type = 'CO')) OR ");
      query.append(" ( header.sub_configuration NOT IN ('ECD','CD','CASS','ALBUM') AND (header.release_type = 'PR') )) ");
    } 
    query.append(" AND (header.configuration = 'CD' OR header.configuration = 'ECD') ");
    String[] strFamily = ReportSelectionsHelper.getMultiSelectionListValues("family", reportForm);
    String[] strEnvironment = ReportSelectionsHelper.getMultiSelectionListValues("environment", reportForm);
    String[] strCompany = ReportSelectionsHelper.getMultiSelectionListValues("company", reportForm);
    String[] strLabel = ReportSelectionsHelper.getMultiSelectionListValues("Label", reportForm);
    ReportSelectionsHelper.addMultSelectionToStringBuffer(query, "company_id", strCompany, true, "company", reportForm, false, true);
    ReportSelectionsHelper.addReleasingFamilyLabelFamilyMultSelect("family", context, query, reportForm);
    ReportSelectionsHelper.addMultSelectionToStringBuffer(query, "environment_id", strEnvironment, true, "environment", reportForm, true, true);
    ReportSelectionsHelper.addMultSelectionToStringBuffer(query, "label_id", strLabel, true, "Label", reportForm, false, true);
    int intUmvd = -1;
    int intPressPlay = -1;
    Vector theFamilies = Cache.getFamilies();
    for (int i = 0; i < theFamilies.size(); i++) {
      Family family = (Family)theFamilies.get(i);
      if (family.getName().trim().equalsIgnoreCase("UMVD"))
        intUmvd = family.getStructureID(); 
      if (family.getName().trim().equalsIgnoreCase("Press Play"))
        intPressPlay = family.getStructureID(); 
    } 
    if (intUmvd > 0)
      query.append(" AND family_id  != " + intUmvd); 
    if (intPressPlay > 0)
      query.append(" AND family_id != " + intPressPlay); 
    String beginDate = "";
    beginDate = (reportForm.getStringValue("beginDate") != null && reportForm.getStringValue("beginDate").length() > 0) ? reportForm.getStringValue("beginDate") : "";
    String endDate = "";
    endDate = (reportForm.getStringValue("endDate") != null && reportForm.getStringValue("endDate").length() > 0) ? reportForm.getStringValue("endDate") : "";
    if (!beginDate.equalsIgnoreCase(""))
      query.append(" AND ( ( ( street_date >= '" + MilestoneHelper.escapeSingleQuotes(beginDate) + "'"); 
    if (!endDate.equalsIgnoreCase("")) {
      if (!beginDate.equalsIgnoreCase("")) {
        query.append(" AND street_date <= '" + MilestoneHelper.escapeSingleQuotes(endDate) + "')");
      } else {
        query.append(" AND (( street_date <= '" + MilestoneHelper.escapeSingleQuotes(endDate) + "'");
      } 
    } else if (!beginDate.equalsIgnoreCase("")) {
      query.append(" ) ");
    } 
    if (beginDate.equalsIgnoreCase("") && endDate.equalsIgnoreCase("")) {
      query.append(" AND (((header.status = 'ACTIVE' OR  header.status = 'CLOSED' OR header.status = 'TBS'))");
    } else {
      query.append(" AND (header.status = 'ACTIVE' OR  header.status = 'CLOSED' OR header.status = 'TBS'))");
    } 
    query.append(" OR (header.status = 'TBS' AND street_date IS NULL))");
    query.append(" ORDER BY artist, street_date");
    JdbcConnector connector = MilestoneHelper.getConnector(query.toString());
    connector.setForwardOnly(false);
    connector.runQuery();
    int totalCount = 0;
    int tenth = 0;
    totalCount = connector.getRowCount();
    tenth = totalCount / 5;
    if (tenth < 1)
      tenth = 1; 
    try {
      HttpServletResponse sresponse = context.getResponse();
      context.putDelivery("status", new String("start_gathering"));
      context.putDelivery("percent", new String("10"));
      context.includeJSP("status.jsp", "hiddenFrame");
      sresponse.setContentType("text/plain");
      sresponse.flushBuffer();
    } catch (Exception exception) {}
    int recordCount = 0;
    int count = 0;
    while (connector.more()) {
      try {
        if (count < recordCount / tenth) {
          count = recordCount / tenth;
          HttpServletResponse sresponse = context.getResponse();
          context.putDelivery("status", new String("start_gathering"));
          context.putDelivery("percent", new String(String.valueOf(count * 10)));
          context.includeJSP("status.jsp", "hiddenFrame");
          sresponse.setContentType("text/plain");
          sresponse.flushBuffer();
        } 
        recordCount++;
        selection = new Selection();
        selection.setFamily((Family)MilestoneHelper.getStructureObject(connector.getIntegerField("family_id")));
        selection.setEnvironment((Environment)MilestoneHelper.getStructureObject(connector.getIntegerField("environment_id")));
        selection.setCompany((Company)MilestoneHelper.getStructureObject(connector.getIntegerField("company_id")));
        selection.setSelectionID(connector.getIntegerField("release_id"));
        selection.setPrefixID((PrefixCode)getLookupObject(connector.getField("prefix"), Cache.getPrefixCodes()));
        selection.setTitle(connector.getField("title", ""));
        selection.setArtistFirstName(connector.getField("artist", ""));
        selection.setArtist(connector.getField("artist", ""));
        selection.setProjectID(connector.getField("project_no", ""));
        selection.setSelectionStatus(
            (SelectionStatus)MilestoneHelper.getLookupObject(connector.getField("status"), Cache.getSelectionStatusList()));
        String streetDateString = connector.getFieldByName("street_date");
        if (streetDateString != null)
          selection.setStreetDate(MilestoneHelper.getDatabaseDate(streetDateString)); 
        selection.setSelectionConfig(
            MilestoneHelper.getSelectionConfigObject(connector.getField("configuration"), 
              Cache.getSelectionConfigs()));
        selection.setSelectionSubConfig(MilestoneHelper.getSelectionSubConfigObject(connector.getField("sub_configuration"), selection.getSelectionConfig()));
        selection.setSelectionNo(connector.getField("selection_no"));
        precache.add(selection);
        selection = null;
        connector.next();
      } catch (Exception exception) {}
    } 
    connector.close();
    company = null;
    return precache;
  }
  
  public static Hashtable groupSelectionsByFamilyAndArtist(Vector selections) {
    Hashtable groupedByFamilyAndArtist = new Hashtable();
    if (selections == null)
      return groupedByFamilyAndArtist; 
    for (int i = 0; i < selections.size(); i++) {
      Selection sel = (Selection)selections.elementAt(i);
      if (sel != null) {
        String familyName = "", companyName = "";
        Family family = sel.getFamily();
        Company company = sel.getCompany();
        String artist = "";
        if (family != null)
          familyName = (family.getName() == null) ? "" : family.getName(); 
        if (company != null)
          companyName = (company.getName() == null) ? "" : company.getName(); 
        Hashtable familySubTable = (Hashtable)groupedByFamilyAndArtist.get(familyName);
        if (familySubTable == null) {
          familySubTable = new Hashtable();
          groupedByFamilyAndArtist.put(familyName, familySubTable);
        } 
        artist = sel.getArtist();
        Vector artistsForFamily = (Vector)familySubTable.get(artist);
        if (artistsForFamily == null) {
          artistsForFamily = new Vector();
          familySubTable.put(artist, artistsForFamily);
        } 
        artistsForFamily.addElement(sel);
      } 
    } 
    return groupedByFamilyAndArtist;
  }
  
  public static Vector getValidSelections(Vector allSelections) {
    Vector fullLengthStrings = getConfigType(0);
    Vector singleStrings = getConfigType(1);
    Vector finalSelections = new Vector();
    for (int selectCounter = 0; selectCounter < allSelections.size(); selectCounter++) {
      String projectID = ((Selection)allSelections.elementAt(selectCounter)).getProjectID();
      SelectionStatus status = ((Selection)allSelections.elementAt(selectCounter)).getSelectionStatus();
      Calendar releaseDateCalendar = ((Selection)allSelections.elementAt(selectCounter)).getStreetDate();
      String title = ((Selection)allSelections.elementAt(selectCounter)).getTitle();
      Selection t = (Selection)allSelections.elementAt(selectCounter);
      SelectionSubConfiguration ssc = t.getSelectionSubConfig();
      String subConfig = ssc.getSelectionSubConfigurationAbbreviation();
      if (fullLengthStrings.contains(subConfig)) {
        if (selectionHasValidSingles(allSelections, projectID, releaseDateCalendar, status))
          finalSelections.add((Selection)allSelections.elementAt(selectCounter)); 
      } else if (singleStrings.contains(subConfig)) {
        if (selectionHasAnAlbum(allSelections, projectID, releaseDateCalendar, fullLengthStrings, true, status))
          finalSelections.add((Selection)allSelections.elementAt(selectCounter)); 
      } 
    } 
    return finalSelections;
  }
  
  public static boolean selectionHasValidSingles(Vector allSelections, String projectID, Calendar releaseDateCalendar, SelectionStatus status) {
    Vector singleStrings = getConfigType(1);
    if (selectionHasAValidProjectID(projectID))
      for (int i = 0; i < allSelections.size(); i++) {
        Selection t = (Selection)allSelections.elementAt(i);
        SelectionSubConfiguration ssc = t.getSelectionSubConfig();
        String subConfig = ssc.getSelectionSubConfigurationAbbreviation();
        SelectionStatus singleStatus = t.getSelectionStatus();
        String selectionStatus2 = (singleStatus != null && singleStatus.getName() != null) ? 
          singleStatus.getName() : "";
        if (((Selection)allSelections.elementAt(i)).getProjectID().equals(projectID) && singleStrings.contains(subConfig) && !selectionStatus2.equals("TBS")) {
          String selectionStatus = (status != null && status.getName() != null) ? 
            status.getName() : "";
          if (selectionStatus.equals("TBS"))
            return true; 
          Calendar singleReleaseDate = ((Selection)allSelections.elementAt(i)).getStreetDate();
          if (singleReleaseDate.before(releaseDateCalendar))
            return true; 
        } 
      }  
    return false;
  }
  
  public static boolean selectionHasAnAlbum(Vector allSelections, String projectID, Calendar releaseDateCalendar, Vector fullLengths, boolean ifNotInAnAlbum, SelectionStatus status) {
    Vector selectionIdVector = new Vector();
    boolean returnValue = false;
    boolean datesCompared = false;
    if (status.getName().equals("TBS"))
      return false; 
    if (selectionHasAValidProjectID(projectID)) {
      for (int i = 0; i < allSelections.size(); i++) {
        Selection t = (Selection)allSelections.elementAt(i);
        SelectionSubConfiguration ssc = t.getSelectionSubConfig();
        String subConfig = ssc.getSelectionSubConfigurationAbbreviation();
        if (t.getProjectID().equals(projectID) && 
          fullLengths.contains(subConfig)) {
          selectionIdVector.add(String.valueOf(t.getSelectionID()));
          if (status.getName().equals("TBS"))
            return true; 
        } 
      } 
      for (int j = 0; j < selectionIdVector.size(); j++) {
        String currentSelectionID = (String)selectionIdVector.elementAt(j);
        for (int k = 0; k < allSelections.size(); k++) {
          Selection ts = (Selection)allSelections.elementAt(k);
          SelectionStatus status2 = ts.getSelectionStatus();
          String selectionStatus2 = (status2 != null && status2.getName() != null) ? 
            status2.getName() : "";
          if (String.valueOf(ts.getSelectionID()).equals(currentSelectionID)) {
            if (selectionStatus2.equals("TBS"))
              return true; 
            Calendar albumDateCalendar = ts.getStreetDate();
            if (releaseDateCalendar.before(albumDateCalendar))
              return true; 
            datesCompared = true;
          } 
        } 
      } 
      if (datesCompared)
        return false; 
      return ifNotInAnAlbum;
    } 
    return true;
  }
  
  public static Vector getConfigType(int typeFlag) {
    Vector allTheFullLengths = new Vector();
    StringBuffer query = new StringBuffer();
    int total = 0;
    query.append(" SELECT distinct header.sub_configuration as fullLength FROM ");
    query.append(" vi_release_header header, lookup_detail ");
    query.append(" WHERE ");
    query.append(" lookup_detail.value = header.sub_configuration AND ");
    if (typeFlag == 0) {
      query.append(" lookup_detail.description = 'Full Length'");
    } else {
      query.append(" lookup_detail.description = 'Single'");
    } 
    JdbcConnector connector = MilestoneHelper.getConnector(query.toString());
    connector.setForwardOnly(false);
    connector.runQuery();
    while (connector.more()) {
      try {
        allTheFullLengths.add(connector.getField("fullLength", ""));
      } catch (Exception exception) {}
      connector.next();
    } 
    connector.close();
    if (typeFlag == 1) {
      allTheFullLengths.add("CDMX");
      allTheFullLengths.add("ECDMX");
    } 
    return allTheFullLengths;
  }
  
  public static Vector getTitlesVector(Vector selections, Vector allFamilySelections) {
    Vector fullLengthStrings = getConfigType(0);
    Vector singleStrings = getConfigType(1);
    Vector titleVector = new Vector();
    for (int i = 0; i < selections.size(); i++) {
      Selection t = (Selection)selections.elementAt(i);
      SelectionSubConfiguration ssc = t.getSelectionSubConfig();
      String subConfig = ssc.getSelectionSubConfigurationAbbreviation();
      String projectID = t.getProjectID();
      SelectionStatus status = t.getSelectionStatus();
      Calendar releaseDateCalendar = t.getStreetDate();
      if (fullLengthStrings.contains(subConfig)) {
        titleVector.add(((Selection)selections.elementAt(i)).getTitle());
      } else if (singleStrings.contains(subConfig)) {
        if (!selectionHasAnAlbum2(selections, projectID, releaseDateCalendar, fullLengthStrings, false, allFamilySelections, status))
          titleVector.add(((Selection)selections.elementAt(i)).getTitle()); 
      } 
    } 
    return titleVector;
  }
  
  public static boolean selectionHasAnAlbum2(Vector allSelections, String projectID, Calendar releaseDateCalendar, Vector fullLengths, boolean ifNotInAnAlbum, Vector allFamilySelections, SelectionStatus status) {
    Vector selectionIdVector = new Vector();
    boolean returnValue = false;
    boolean datesCompared = false;
    if (status.getName().equals("TBS"))
      return false; 
    if (selectionHasAValidProjectID(projectID)) {
      for (int i = 0; i < allFamilySelections.size(); i++) {
        Selection t = (Selection)allFamilySelections.elementAt(i);
        SelectionSubConfiguration ssc = t.getSelectionSubConfig();
        String subConfig = ssc.getSelectionSubConfigurationAbbreviation();
        if (t.getProjectID().equals(projectID))
          if (fullLengths.contains(subConfig)) {
            selectionIdVector.add(String.valueOf(t.getSelectionID()));
            if (status.getName().equals("TBS"))
              return true; 
          }  
      } 
      for (int j = 0; j < selectionIdVector.size(); j++) {
        String currentSelectionID = (String)selectionIdVector.elementAt(j);
        for (int k = 0; k < allFamilySelections.size(); k++) {
          Selection ts = (Selection)allFamilySelections.elementAt(k);
          if (String.valueOf(ts.getSelectionID()).equals(currentSelectionID)) {
            SelectionStatus status2 = ts.getSelectionStatus();
            String selectionStatus2 = (status2 != null && status2.getName() != null) ? 
              status2.getName() : "";
            if (selectionStatus2.equals("TBS"))
              return true; 
            Calendar albumDateCalendar = ts.getStreetDate();
            if (releaseDateCalendar.before(albumDateCalendar))
              return true; 
            datesCompared = true;
          } 
        } 
      } 
      if (datesCompared)
        return false; 
      return ifNotInAnAlbum;
    } 
    return false;
  }
  
  public static boolean selectionHasAValidProjectID(String projectID) {
    if (projectID == null)
      return false; 
    if (projectID.indexOf('x') != -1)
      return false; 
    if (projectID.indexOf('X') != -1)
      return false; 
    if (projectID.equals("0000-0000") || projectID.equals("0000-00000"))
      return false; 
    return true;
  }
  
  public static LookupObject getLookupObject(String abbreviation, Vector lookupVector) {
    for (int j = 0; j < lookupVector.size(); j++) {
      LookupObject lookupObject = (LookupObject)lookupVector.get(j);
      if (lookupObject.getAbbreviation().equalsIgnoreCase(abbreviation))
        return lookupObject; 
    } 
    return null;
  }
  
  public static boolean singleHasNoAlbum(Vector allSelections, Selection selectionEntry, Vector fullLengthStrings) {
    if (selectionEntry.getProjectID().equals("0070-05162"))
      System.out.println("****0070-05162 found****"); 
    SelectionStatus status2 = selectionEntry.getSelectionStatus();
    String selectionStatus2 = (status2 != null && status2.getName() != null) ? 
      status2.getName() : "";
    if (selectionStatus2.equals("TBS"))
      return false; 
    for (int i = 0; i < allSelections.size(); i++) {
      Selection currentSelection = (Selection)allSelections.elementAt(i);
      if (currentSelection.getProjectID().equals(selectionEntry.getProjectID()))
        if (fullLengthStrings.contains(currentSelection.getSelectionSubConfig().getSelectionSubConfigurationAbbreviation())) {
          SelectionStatus status = currentSelection.getSelectionStatus();
          String selectionStatus = (status != null && status.getName() != null) ? 
            status.getName() : "";
          if (selectionStatus.equals("TBS"))
            return false; 
          if (selectionEntry.getStreetDate().before(currentSelection.getStreetDate()))
            return false; 
        }  
    } 
    String projectID = selectionEntry.getProjectID();
    if (selectionHasAValidProjectID(projectID)) {
      String streetDate = MilestoneHelper.getFormatedDate(selectionEntry.getStreetDate());
      String numberOfAlbumsFound = "0";
      StringBuffer query = new StringBuffer();
      int total = 0;
      query.append(" SELECT count(*) as numberOfAlbums FROM ");
      query.append(" vi_release_header header");
      query.append(" WHERE ");
      query.append(" project_no = '" + projectID + "' AND ");
      query.append(" release_type = 'CO' AND ");
      query.append(" (sub_configuration = 'CD' OR sub_configuration = 'ECD') AND ");
      query.append(" street_date <= '" + MilestoneHelper.escapeSingleQuotes(streetDate) + "'");
      JdbcConnector connector = MilestoneHelper.getConnector(query.toString());
      connector.setForwardOnly(false);
      connector.runQuery();
      while (connector.more()) {
        try {
          numberOfAlbumsFound = connector.getField("numberOfAlbums", "");
        } catch (Exception exception) {}
        connector.next();
      } 
      connector.close();
      if (!numberOfAlbumsFound.equals("0"))
        return false; 
    } 
    return true;
  }
}


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\EntPastRelDatesPrintSubHandler.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */