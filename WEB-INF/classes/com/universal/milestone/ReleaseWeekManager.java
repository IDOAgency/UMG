package WEB-INF.classes.com.universal.milestone;

import com.techempower.ComponentLog;
import com.techempower.EnhancedProperties;
import com.techempower.gemini.Context;
import com.techempower.gemini.GeminiApplication;
import com.universal.milestone.Cache;
import com.universal.milestone.JdbcConnector;
import com.universal.milestone.MilestoneConstants;
import com.universal.milestone.MilestoneHelper;
import com.universal.milestone.Notepad;
import com.universal.milestone.ReleaseWeek;
import com.universal.milestone.ReleaseWeekManager;
import java.util.Vector;

public class ReleaseWeekManager implements MilestoneConstants {
  public static final String COMPONENT_CODE = "mFam";
  
  public static final String DEFAULT_QUERY = "SELECT [name], [start_date], [end_date]FROM vi_Date_Period";
  
  public static final String DEFAULT_ORDER = "  ORDER BY name";
  
  protected static ReleaseWeekManager releaseWeekManager = null;
  
  protected static ComponentLog log;
  
  public static void configure(EnhancedProperties props, GeminiApplication application) { log = application.getLog("mFam"); }
  
  public static ReleaseWeekManager getInstance() {
    if (releaseWeekManager == null)
      releaseWeekManager = new ReleaseWeekManager(); 
    return releaseWeekManager;
  }
  
  public ReleaseWeek getReleaseWeek(String id, boolean getTimestamp) {
    String releaseWeekQuery = "SELECT [per_id], [name], [cycle], [start_date], [end_date], [sol_date], [last_updated_on], [last_updated_by], [last_updated_ck] FROM vi_Date_Period WHERE name = '" + 
      
      MilestoneHelper.escapeSingleQuotes(id) + "'";
    ReleaseWeek releaseWeek = null;
    JdbcConnector connector = MilestoneHelper.getConnector(releaseWeekQuery);
    connector.runQuery();
    if (connector.more()) {
      releaseWeek = new ReleaseWeek();
      releaseWeek.setReleaseWeekID(connector.getIntegerField("per_id"));
      releaseWeek.setName(connector.getField("name"));
      releaseWeek.setCycle(connector.getField("cycle"));
      String startDateString = connector.getFieldByName("start_date");
      if (startDateString != null)
        releaseWeek.setStartDate(MilestoneHelper.getDatabaseDate(startDateString)); 
      String endDateString = connector.getFieldByName("end_date");
      if (endDateString != null)
        releaseWeek.setEndDate(MilestoneHelper.getDatabaseDate(endDateString)); 
      String solDateString = connector.getFieldByName("sol_date");
      if (solDateString != null)
        releaseWeek.setSolDate(MilestoneHelper.getDatabaseDate(solDateString)); 
      String lastDateString = connector.getFieldByName("last_updated_on");
      if (lastDateString != null)
        releaseWeek.setLastUpdateDate(MilestoneHelper.getDatabaseDate(lastDateString)); 
      releaseWeek.setLastUpdatingUser(connector.getIntegerField("last_updated_by"));
      if (getTimestamp) {
        long lastUpdatedLong = Long.parseLong(connector.getField("last_updated_ck"), 16);
        releaseWeek.setLastUpdatedCheck(lastUpdatedLong);
      } 
    } 
    connector.close();
    return releaseWeek;
  }
  
  private ReleaseWeek getNotepadReleaseWeek(JdbcConnector connector) {
    ReleaseWeek releaseWeek = null;
    if (connector != null) {
      releaseWeek = new ReleaseWeek();
      releaseWeek.setName(connector.getFieldByName("name"));
      String startDateString = connector.getFieldByName("start_date");
      if (startDateString != null)
        releaseWeek.setStartDate(MilestoneHelper.getDatabaseDate(startDateString)); 
      String endDateString = connector.getFieldByName("end_date");
      if (endDateString != null)
        releaseWeek.setEndDate(MilestoneHelper.getDatabaseDate(endDateString)); 
    } 
    return releaseWeek;
  }
  
  public ReleaseWeek saveReleaseWeek(ReleaseWeek releaseWeek, int userID) {
    long timestamp = releaseWeek.getLastUpdatedCheck();
    String query = "sp_sav_Date_Period " + 
      releaseWeek.getReleaseWeekID() + "," + 
      "'" + MilestoneHelper.escapeSingleQuotes(releaseWeek.getName()) + "'," + 
      "'" + MilestoneHelper.escapeSingleQuotes(releaseWeek.getCycle()) + "'," + 
      "'" + MilestoneHelper.getFormatedDate(releaseWeek.getStartDate()) + "'," + 
      "'" + MilestoneHelper.getFormatedDate(releaseWeek.getEndDate()) + "'," + 
      "'" + MilestoneHelper.getFormatedDate(releaseWeek.getSolDate()) + "'," + 
      userID + "," + 
      timestamp;
    JdbcConnector connector = MilestoneHelper.getConnector(query);
    connector.runQuery();
    connector.close();
    releaseWeek.flushAudits(userID);
    String timestampQuery = "Select last_updated_ck, last_updated_on from vi_Date_Period where per_id = " + releaseWeek.getReleaseWeekID();
    JdbcConnector connectorTimestamp = MilestoneHelper.getConnector(timestampQuery);
    connectorTimestamp.runQuery();
    if (connectorTimestamp.more()) {
      releaseWeek.setLastUpdatedCheck(Long.parseLong(connectorTimestamp.getField("last_updated_ck"), 16));
      releaseWeek.setLastUpdateDate(MilestoneHelper.getDatabaseDate(connectorTimestamp.getField("last_updated_on")));
    } 
    connectorTimestamp.close();
    Cache.flushDatePeriods();
    return releaseWeek;
  }
  
  public ReleaseWeek saveNewReleaseWeek(ReleaseWeek releaseWeek, int userID) {
    String query = "sp_sav_Date_Period -1,'" + 
      
      MilestoneHelper.escapeSingleQuotes(releaseWeek.getName()) + "'," + 
      "'" + MilestoneHelper.escapeSingleQuotes(releaseWeek.getCycle()) + "'," + 
      "'" + MilestoneHelper.getFormatedDate(releaseWeek.getStartDate()) + "'," + 
      "'" + MilestoneHelper.getFormatedDate(releaseWeek.getEndDate()) + "'," + 
      "'" + MilestoneHelper.getFormatedDate(releaseWeek.getSolDate()) + "'," + 
      userID + "," + 
      -1;
    releaseWeek.flushAudits(userID);
    JdbcConnector connector = MilestoneHelper.getConnector(query);
    connector.runQuery();
    if (connector.getIntegerField("ReturnId") > 0)
      releaseWeek.setReleaseWeekID(connector.getIntegerField("ReturnId")); 
    connector.close();
    Cache.flushDatePeriods();
    return releaseWeek;
  }
  
  public void deleteReleaseWeek(ReleaseWeek releaseWeek, int userID) {
    String query = "sp_del_Date_Period " + 
      releaseWeek.getReleaseWeekID();
    JdbcConnector connector = MilestoneHelper.getConnector(query);
    connector.runQuery();
    connector.close();
    Cache.flushDatePeriods();
  }
  
  public void setNotepadQuery(Context context, int UserId, Notepad notepad) {
    if (notepad != null) {
      String nameSearch = context.getParameter("NameSearch");
      String cycleSearch = context.getParameter("CycleSearch");
      String startDateSearch = context.getParameter("StartDateSearch");
      String endDateSearch = context.getParameter("EndDateSearch");
      String releaseWeekQuery = "SELECT * FROM vi_Date_Period ";
      if (MilestoneHelper.isStringNotEmpty(nameSearch))
        releaseWeekQuery = String.valueOf(releaseWeekQuery) + MilestoneHelper.addQueryParams(releaseWeekQuery, " name " + MilestoneHelper.setWildCardsEscapeSingleQuotes(nameSearch)); 
      if (MilestoneHelper.isStringNotEmpty(cycleSearch))
        releaseWeekQuery = String.valueOf(releaseWeekQuery) + MilestoneHelper.addQueryParams(releaseWeekQuery, " cycle " + MilestoneHelper.setWildCardsEscapeSingleQuotes(cycleSearch)); 
      if (MilestoneHelper.isStringNotEmpty(startDateSearch))
        releaseWeekQuery = String.valueOf(releaseWeekQuery) + MilestoneHelper.addQueryParams(releaseWeekQuery, " start_date = '" + MilestoneHelper.escapeSingleQuotes(startDateSearch) + "'"); 
      if (MilestoneHelper.isStringNotEmpty(endDateSearch))
        releaseWeekQuery = String.valueOf(releaseWeekQuery) + MilestoneHelper.addQueryParams(releaseWeekQuery, " end_date = '" + MilestoneHelper.escapeSingleQuotes(endDateSearch) + "'"); 
      String order = " ORDER BY name";
      log.debug("Notepad rwrwrwrw releaseWeekQuery:\n" + releaseWeekQuery);
      notepad.setSearchQuery(releaseWeekQuery);
      notepad.setOrderBy(order);
    } 
  }
  
  public Vector getReleaseWeekNotepadList(int UserId, Notepad notepad) {
    String releaseWeekQuery = "";
    if (notepad != null && !notepad.getSearchQuery().equals("")) {
      releaseWeekQuery = notepad.getSearchQuery();
      releaseWeekQuery = String.valueOf(releaseWeekQuery) + notepad.getOrderBy();
    } else {
      releaseWeekQuery = "SELECT [name], [start_date], [end_date]FROM vi_Date_Period  ORDER BY name";
    } 
    ReleaseWeek releaseWeek = null;
    Vector precache = new Vector();
    JdbcConnector connector = MilestoneHelper.getConnector(releaseWeekQuery);
    connector.runQuery();
    while (connector.more()) {
      releaseWeek = getNotepadReleaseWeek(connector);
      precache.addElement(releaseWeek);
      releaseWeek = null;
      connector.next();
    } 
    connector.close();
    return precache;
  }
  
  public boolean isTimestampValid(ReleaseWeek releaseWeek) {
    if (releaseWeek != null) {
      String timestampQuery = "Select last_updated_ck, last_updated_on from vi_Date_Period where per_id = " + releaseWeek.getReleaseWeekID();
      JdbcConnector connectorTimestamp = MilestoneHelper.getConnector(timestampQuery);
      connectorTimestamp.runQuery();
      if (connectorTimestamp.more())
        if (releaseWeek.getLastUpdatedCheck() != Long.parseLong(connectorTimestamp.getField("last_updated_ck", ""), 16)) {
          connectorTimestamp.close();
          return false;
        }  
      connectorTimestamp.close();
      return true;
    } 
    return false;
  }
  
  public boolean isDuplicate(ReleaseWeek releaseWeek) {
    boolean isDuplicate = false;
    if (releaseWeek != null) {
      String query = "SELECT * FROM vi_date_period  WHERE name = '" + 
        MilestoneHelper.escapeSingleQuotes(releaseWeek.getName()) + "' " + 
        " AND per_id <> " + releaseWeek.getReleaseWeekID();
      JdbcConnector connector = MilestoneHelper.getConnector(query);
      connector.runQuery();
      if (connector.more())
        isDuplicate = true; 
      connector.close();
    } 
    return isDuplicate;
  }
}


/* Location:              D:\Documents\NetBeansProjects\milestone2 Local.war!\WEB-INF\classes\co\\universal\milestone\ReleaseWeekManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */