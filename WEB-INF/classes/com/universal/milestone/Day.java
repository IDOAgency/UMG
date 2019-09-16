package WEB-INF.classes.com.universal.milestone;

import com.universal.milestone.Day;
import com.universal.milestone.MilestoneDataEntity;
import com.universal.milestone.NotepadContentObject;
import java.util.Calendar;

public class Day extends MilestoneDataEntity implements NotepadContentObject {
  protected int dayID;
  
  protected String dayType;
  
  protected String dayDescription;
  
  protected Calendar specificDate;
  
  protected int calendarGroup;
  
  protected int lastUpdatingUser;
  
  protected Calendar lastUpdateDate;
  
  protected long lastUpdatedCk;
  
  public String getTableName() { return "Day_Type"; }
  
  public int getIdentity() { return getDayID(); }
  
  public Day(int day) {
    this.dayID = 0;
    this.dayType = "";
    this.dayDescription = "";
    this.specificDate = null;
    this.calendarGroup = 0;
    this.dayID = day;
  }
  
  public Day() {
    this.dayID = 0;
    this.dayType = "";
    this.dayDescription = "";
    this.specificDate = null;
    this.calendarGroup = 0;
  }
  
  public Day(String day) {
    this.dayID = 0;
    this.dayType = "";
    this.dayDescription = "";
    this.specificDate = null;
    this.calendarGroup = 0;
    int dayID = -1;
    if (day.toUpperCase().equalsIgnoreCase("M")) {
      dayID = 1;
    } else if (day.toUpperCase().equalsIgnoreCase("T")) {
      dayID = 2;
    } else if (day.toUpperCase().equalsIgnoreCase("W")) {
      dayID = 3;
    } else if (day.toUpperCase().equalsIgnoreCase("TH")) {
      dayID = 4;
    } else if (day.toUpperCase().equalsIgnoreCase("F")) {
      dayID = 5;
    } else if (day.toUpperCase().equalsIgnoreCase("S")) {
      dayID = 6;
    } else if (day.toUpperCase().equalsIgnoreCase("SU")) {
      dayID = 7;
    } else if (day.toUpperCase().equalsIgnoreCase("D")) {
      dayID = 8;
    } else if (day.toUpperCase().equalsIgnoreCase("SOL")) {
      dayID = -10;
    } 
    try {
      dayID = Integer.parseInt(day);
    } catch (Exception exception) {}
    this.dayID = dayID;
  }
  
  public int getDayID() { return this.dayID; }
  
  public void setDayID(int dayID) { this.dayID = dayID; }
  
  public String getDay() {
    switch (this.dayID) {
      case 1:
        return "M";
      case 2:
        return "T";
      case 3:
        return "W";
      case 4:
        return "TH";
      case 5:
        return "F";
      case 6:
        return "S";
      case 7:
        return "SU";
      case 8:
        return "D";
      case 9:
        return "SOL";
    } 
    return String.valueOf(this.dayID);
  }
  
  public String getTaskDay() { return String.valueOf(this.dayID); }
  
  public int getCalendarGroup() { return this.calendarGroup; }
  
  public void setCalendarGroup(int calendarGroup) {
    auditCheck("grouping", this.calendarGroup, calendarGroup);
    this.calendarGroup = calendarGroup;
  }
  
  public String getDescription() { return this.dayDescription; }
  
  public void setDescription(String description) {
    auditCheck("description", this.dayDescription, this.dayDescription);
    this.dayDescription = description;
  }
  
  public Calendar getSpecificDate() { return this.specificDate; }
  
  public void setSpecificDate(Calendar specificDate) {
    auditCheck("date", this.specificDate, specificDate);
    this.specificDate = specificDate;
  }
  
  public String getDayType() { return this.dayType; }
  
  public void setDayType(String dayType) {
    auditCheck("value", this.dayType, dayType);
    this.dayType = dayType;
  }
  
  public long getLastUpdatedCk() { return this.lastUpdatedCk; }
  
  public void setLastUpdatedCk(long lastUpdatedCk) { this.lastUpdatedCk = lastUpdatedCk; }
  
  public int getLastUpdatingUser() { return this.lastUpdatingUser; }
  
  public void setLastUpdatingUser(int updatingUser) { this.lastUpdatingUser = updatingUser; }
  
  public Calendar getLastUpdateDate() { return this.lastUpdateDate; }
  
  public void setLastUpdateDate(Calendar lastUpdateDate) { this.lastUpdateDate = lastUpdateDate; }
  
  public String getNotepadContentObjectId() { return Integer.toString(this.dayID); }
}


/* Location:              D:\Documents\NetBeansProjects\milestone2 Local.war!\WEB-INF\classes\co\\universal\milestone\Day.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */