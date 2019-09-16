package WEB-INF.classes.com.universal.milestone;

import com.universal.milestone.MilestoneDataEntity;
import com.universal.milestone.NotepadContentObject;
import com.universal.milestone.ReleaseWeek;
import java.util.Calendar;

public class ReleaseWeek extends MilestoneDataEntity implements NotepadContentObject {
  protected String name = "";
  
  protected String cycle = "";
  
  protected Calendar startDate = null;
  
  protected Calendar endDate = null;
  
  protected Calendar solDate = null;
  
  protected int releaseWeekID = 0;
  
  protected Calendar lastUpdateDate = null;
  
  protected long lastUpdatedCheck = -1L;
  
  protected String lastUpdateDateString = "";
  
  protected int lastUpdatingUser = 0;
  
  public String getTableName() { return "Date_period"; }
  
  public int getIdentity() { return getReleaseWeekID(); }
  
  public int getReleaseWeekID() { return this.releaseWeekID; }
  
  public void setReleaseWeekID(int releaseWeekID) { this.releaseWeekID = releaseWeekID; }
  
  public String getName() { return this.name; }
  
  public void setName(String name) {
    auditCheck("name", this.name, name);
    this.name = name;
  }
  
  public String getCycle() { return this.cycle; }
  
  public void setCycle(String cycle) {
    auditCheck("cycle", this.cycle, cycle);
    this.cycle = cycle;
  }
  
  public Calendar getStartDate() { return this.startDate; }
  
  public void setStartDate(Calendar startDate) {
    auditCheck("start_date", this.startDate, startDate);
    this.startDate = startDate;
  }
  
  public Calendar getEndDate() { return this.endDate; }
  
  public void setEndDate(Calendar endDate) {
    auditCheck("end_date", this.endDate, endDate);
    this.endDate = endDate;
  }
  
  public Calendar getSolDate() { return this.solDate; }
  
  public void setSolDate(Calendar solDate) { this.solDate = solDate; }
  
  public String getLastUpdateDateString() { return this.lastUpdateDateString; }
  
  public void setLastUpdateDateString(String lastUpdateDateString) { this.lastUpdateDateString = lastUpdateDateString; }
  
  public long getLastUpdatedCheck() { return this.lastUpdatedCheck; }
  
  public void setLastUpdatedCheck(long lastUpdatedCheck) { this.lastUpdatedCheck = lastUpdatedCheck; }
  
  public Calendar getLastUpdateDate() { return this.lastUpdateDate; }
  
  public void setLastUpdateDate(Calendar lastUpdateDate) { this.lastUpdateDate = lastUpdateDate; }
  
  public int getLastUpdatingUser() { return this.lastUpdatingUser; }
  
  public void setLastUpdatingUser(int updatingUser) { this.lastUpdatingUser = updatingUser; }
  
  public String getNotepadContentObjectId() { return this.name; }
}


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\ReleaseWeek.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */