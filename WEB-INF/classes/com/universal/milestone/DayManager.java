package WEB-INF.classes.com.universal.milestone;

import com.techempower.ComponentLog;
import com.techempower.EnhancedProperties;
import com.techempower.gemini.Context;
import com.techempower.gemini.GeminiApplication;
import com.universal.milestone.Day;
import com.universal.milestone.DayManager;
import com.universal.milestone.JdbcConnector;
import com.universal.milestone.MilestoneConstants;
import com.universal.milestone.MilestoneHelper;
import com.universal.milestone.Notepad;
import java.util.Vector;

public class DayManager implements MilestoneConstants {
  public static final String COMPONENT_CODE = "mDay";
  
  public static final String DEFAULT_QUERY = "SELECT [grouping], [date], [value], [type_id]  FROM vi_Day_Type";
  
  public static final String DEFAULT_ORDER = " ORDER BY grouping DESC";
  
  protected static DayManager dayManager = null;
  
  protected static ComponentLog log;
  
  public static void configure(EnhancedProperties props, GeminiApplication application) { log = application.getLog("mDay"); }
  
  public static DayManager getInstance() {
    if (dayManager == null)
      dayManager = new DayManager(); 
    return dayManager;
  }
  
  public Day getDay(int id, boolean getTimestamp) {
    String dayQuery = "SELECT [grouping], [date], [value], [type_id], [description], [entered_by], [last_updated_by], [last_updated_on], [last_updated_ck] FROM vi_Day_Type WHERE type_id = " + 
      
      id + 
      ";";
    Day day = null;
    JdbcConnector connector = MilestoneHelper.getConnector(dayQuery);
    connector.runQuery();
    if (connector.more()) {
      day = new Day(connector.getIntegerField("type_id"));
      day.setDescription(connector.getField("description", ""));
      day.setCalendarGroup(connector.getIntegerField("grouping"));
      String dateString = connector.getField("date", "");
      if (dateString.length() > 0)
        day.setSpecificDate(MilestoneHelper.getDatabaseDate(dateString)); 
      day.setDayType(connector.getField("value", ""));
      String lastDateString = connector.getFieldByName("last_updated_on");
      if (lastDateString != null)
        day.setLastUpdateDate(MilestoneHelper.getDatabaseDate(lastDateString)); 
      day.setLastUpdatingUser(connector.getIntegerField("last_updated_by"));
      if (getTimestamp) {
        long lastUpdatedLong = Long.parseLong(connector.getField("last_updated_ck"), 16);
        day.setLastUpdatedCk(lastUpdatedLong);
      } 
    } 
    connector.close();
    return day;
  }
  
  private Day getNotepadDay(JdbcConnector connector) {
    Day day = null;
    if (connector != null) {
      day = new Day(connector.getIntegerField("type_id"));
      day.setCalendarGroup(connector.getIntegerField("grouping"));
      String dateString = connector.getField("date", "");
      if (dateString.length() > 0)
        day.setSpecificDate(MilestoneHelper.getDatabaseDate(dateString)); 
      day.setDayType(connector.getField("value", ""));
    } 
    return day;
  }
  
  public Day saveDay(Day day, int userID) {
    long timestamp = day.getLastUpdatedCk();
    String query = "sp_sav_Day_Type " + 
      day.getDayID() + "," + 
      "'" + MilestoneHelper.escapeSingleQuotes(day.getDayType()) + "'," + 
      "'" + MilestoneHelper.escapeSingleQuotes(day.getDescription()) + "'," + 
      "'" + MilestoneHelper.getFormatedDate(day.getSpecificDate()) + "'," + 
      day.getCalendarGroup() + "," + 
      userID + "," + 
      timestamp + 
      ";";
    log.debug("Day save query:\n" + query);
    JdbcConnector connector = MilestoneHelper.getConnector(query);
    connector.runQuery();
    if (connector.getIntegerField("ReturnId") > 0)
      day.setDayID(connector.getIntegerField("ReturnId")); 
    connector.close();
    String timestampQuery = "SELECT last_updated_ck, last_updated_on FROM vi_Day_Type WHERE type_id = " + 
      
      day.getDayID() + 
      ";";
    day.flushAudits(userID);
    JdbcConnector connectorTimestamp = MilestoneHelper.getConnector(timestampQuery);
    connectorTimestamp.runQuery();
    if (connectorTimestamp.more()) {
      day.setLastUpdatedCk(Long.parseLong(connectorTimestamp.getField("last_updated_ck"), 16));
      String lastUpdatedString = connectorTimestamp.getField("last_updated_on", "");
      if (lastUpdatedString.length() > 0)
        day.setLastUpdateDate(MilestoneHelper.getDatabaseDate(lastUpdatedString)); 
    } 
    connectorTimestamp.close();
    return day;
  }
  
  public Day saveNewDayType(Day day, int userID) {
    day.flushAudits(userID);
    String query = "sp_sav_Day_Type -1,'" + 
      
      MilestoneHelper.escapeSingleQuotes(day.getDayType()) + "'," + 
      "'" + MilestoneHelper.escapeSingleQuotes(day.getDescription()) + "'," + 
      "'" + MilestoneHelper.getFormatedDate(day.getSpecificDate()) + "'," + 
      day.getCalendarGroup() + "," + 
      userID + "," + 
      -1;
    JdbcConnector connector = MilestoneHelper.getConnector(query);
    connector.runQuery();
    day.setDayID(connector.getIntegerField("returnId"));
    connector.close();
    return day;
  }
  
  public void deleteDay(Day day, int userID) {
    String query = "sp_del_Day_Type " + 
      day.getDayID();
    JdbcConnector connector = MilestoneHelper.getConnector(query);
    connector.runQuery();
    connector.close();
  }
  
  public void setNotepadQuery(Context context, int UserId, Notepad notepad) {
    if (notepad != null) {
      String groupSearch = context.getParameter("GroupSearch");
      String specialDateSearch = context.getParameter("SpecialDateSearch");
      String dayTypeSearch = context.getParameter("DayTypeSearch");
      String descriptionSearch = context.getParameter("DescriptionSearch");
      StringBuffer dayQuery = new StringBuffer(
          "SELECT [grouping], [date], [value], [type_id]  FROM vi_Day_Type");
      if (MilestoneHelper.isStringNotEmpty(groupSearch) && !groupSearch.equals("0"))
        dayQuery.append(MilestoneHelper.addQueryParams(dayQuery.toString(), " grouping like '%" + groupSearch + "%'")); 
      if (MilestoneHelper.isStringNotEmpty(specialDateSearch))
        dayQuery.append(MilestoneHelper.addQueryParams(dayQuery.toString(), " date = '" + MilestoneHelper.escapeSingleQuotes(specialDateSearch) + "'")); 
      if (MilestoneHelper.isStringNotEmpty(dayTypeSearch))
        dayQuery.append(MilestoneHelper.addQueryParams(dayQuery.toString(), " value " + MilestoneHelper.setWildCardsEscapeSingleQuotes(dayTypeSearch))); 
      if (MilestoneHelper.isStringNotEmpty(descriptionSearch))
        dayQuery.append(MilestoneHelper.addQueryParams(dayQuery.toString(), " description " + MilestoneHelper.setWildCardsEscapeSingleQuotes(descriptionSearch))); 
      String order = " ORDER BY grouping";
      notepad.setSearchQuery(dayQuery.toString());
      notepad.setOrderBy(order);
    } 
  }
  
  public Vector getDayNotepadList(int UserId, Notepad notepad) {
    String dayQuery = "";
    if (notepad != null && !notepad.getSearchQuery().equals("")) {
      dayQuery = notepad.getSearchQuery();
      dayQuery = String.valueOf(dayQuery) + notepad.getOrderBy();
    } else {
      dayQuery = "SELECT [grouping], [date], [value], [type_id]  FROM vi_Day_Type ORDER BY grouping DESC";
    } 
    Day day = null;
    Vector precache = new Vector();
    JdbcConnector connector = MilestoneHelper.getConnector(dayQuery);
    connector.runQuery();
    while (connector.more()) {
      day = getNotepadDay(connector);
      precache.addElement(day);
      day = null;
      connector.next();
    } 
    connector.close();
    return precache;
  }
  
  public boolean isTimestampValid(Day day) {
    if (day != null) {
      String timestampQuery = "SELECT last_updated_ck, last_updated_on FROM vi_Day_Type WHERE type_id = " + 
        
        day.getDayID() + 
        ";";
      JdbcConnector connectorTimestamp = MilestoneHelper.getConnector(timestampQuery);
      connectorTimestamp.runQuery();
      if (connectorTimestamp.more())
        if (day.getLastUpdatedCk() != Long.parseLong(connectorTimestamp.getField("last_updated_ck", ""), 16)) {
          connectorTimestamp.close();
          return false;
        }  
      connectorTimestamp.close();
      return true;
    } 
    return false;
  }
  
  public boolean isDuplicate(Day day) {
    boolean isDuplicate = false;
    if (day != null) {
      String query = "SELECT * FROM vi_day_type  WHERE grouping = " + 
        day.getCalendarGroup() + 
        " AND date = '" + MilestoneHelper.getFormatedDate(day.getSpecificDate()) + "'" + 
        " AND type_id <> " + day.getDayID();
      JdbcConnector connector = MilestoneHelper.getConnector(query);
      connector.runQuery();
      if (connector.more())
        isDuplicate = true; 
      connector.close();
    } 
    return isDuplicate;
  }
}


/* Location:              D:\Documents\NetBeansProjects\milestone2 Local.war!\WEB-INF\classes\co\\universal\milestone\DayManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */