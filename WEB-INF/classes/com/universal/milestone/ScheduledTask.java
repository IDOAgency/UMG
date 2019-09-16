package WEB-INF.classes.com.universal.milestone;

import com.techempower.DataEntity;
import com.universal.milestone.Day;
import com.universal.milestone.Family;
import com.universal.milestone.ScheduledTask;
import java.util.Calendar;
import java.util.Vector;

public class ScheduledTask extends DataEntity {
  protected int releaseID;
  
  protected Family owner = null;
  
  protected boolean isKeyTask;
  
  protected String authorizationName = "";
  
  protected Calendar authorizationDate = null;
  
  protected String name = "";
  
  protected String department = "";
  
  protected int scheduledTaskID;
  
  protected int scheduleID;
  
  protected int taskID = -1;
  
  protected int taskAbbreviationID = -1;
  
  protected String scheduledTaskStatus = "";
  
  protected String comments = "";
  
  protected String vendor = "";
  
  protected int weeksToRelease = 0;
  
  protected Calendar dueDate = null;
  
  protected Day dayOfTheWeek = null;
  
  protected Calendar completionDate = null;
  
  protected Vector multCompleteDates = null;
  
  protected boolean allowMultCompleteDatesFlag = false;
  
  protected Calendar lastUpdatingDate = null;
  
  protected int lastUpdatingUser;
  
  protected long lastUpdatedCk;
  
  protected String selectionId = "";
  
  protected String upc = "";
  
  protected int taskWeeksToRelease = 0;
  
  protected String selectionNo = "";
  
  protected Calendar streetDate = null;
  
  public int getScheduledTaskID() { return this.scheduledTaskID; }
  
  public void setScheduledTaskID(int scheduledTaskID) { this.scheduledTaskID = scheduledTaskID; }
  
  public int getScheduleID() { return this.scheduleID; }
  
  public void setScheduleID(int scheduleID) { this.scheduleID = scheduleID; }
  
  public int getTaskID() { return this.taskID; }
  
  public void setTaskID(int taskID) { this.taskID = taskID; }
  
  public int getWeeksToRelease() { return this.weeksToRelease; }
  
  public Day getDayOfTheWeek() { return this.dayOfTheWeek; }
  
  public String getComments() { return this.comments; }
  
  public Calendar getDueDate() { return this.dueDate; }
  
  public Calendar getCompletionDate() { return this.completionDate; }
  
  public Vector getMultCompleteDates() { return this.multCompleteDates; }
  
  public boolean getAllowMultCompleteDatesFlag() { return this.allowMultCompleteDatesFlag; }
  
  public String getScheduledTaskStatus() { return this.scheduledTaskStatus; }
  
  public String getVendor() { return this.vendor; }
  
  public int getTaskWeeksToRelease() { return this.taskWeeksToRelease; }
  
  public void setWeeksToRelease(int weeksToRelease) { this.weeksToRelease = weeksToRelease; }
  
  public void setDayOfTheWeek(Day dayOfTheWeek) { this.dayOfTheWeek = dayOfTheWeek; }
  
  public void setComments(String comments) { this.comments = comments; }
  
  public void setDueDate(Calendar dueDate) { this.dueDate = dueDate; }
  
  public void setCompletionDate(Calendar completeDate) { this.completionDate = completeDate; }
  
  public void setMultCompleteDates(Vector multCompleteDates) { this.multCompleteDates = multCompleteDates; }
  
  public void setAllowMultCompleteDatesFlag(boolean allowed) { this.allowMultCompleteDatesFlag = allowed; }
  
  public void setScheduledTaskStatus(String taskStatus) { this.scheduledTaskStatus = taskStatus; }
  
  public void setVendor(String vendor) { this.vendor = vendor; }
  
  public Calendar recalcDueDates() { return this.dueDate; }
  
  public int getLastUpdatingUser() { return this.lastUpdatingUser; }
  
  public void setLastUpdatingUser(int lastUpdatingUser) { this.lastUpdatingUser = lastUpdatingUser; }
  
  public Calendar getLastUpdateDate() { return this.lastUpdatingDate; }
  
  public void setLastUpdateDate(Calendar lastUpdateDate) { this.lastUpdatingDate = lastUpdateDate; }
  
  public int getReleaseID() { return this.releaseID; }
  
  public void setReleaseID(int releaseID) { this.releaseID = releaseID; }
  
  public Family getOwner() { return this.owner; }
  
  public void setOwner(Family owner) { this.owner = owner; }
  
  public void setReleaseID(Family owner) { this.owner = owner; }
  
  public boolean getIsKeytask() { return this.isKeyTask; }
  
  public void setKeyTask(boolean isKeyTask) { this.isKeyTask = isKeyTask; }
  
  public String getAuthorizationName() { return this.authorizationName; }
  
  public void setAuthorizationName(String authorizationName) { this.authorizationName = authorizationName; }
  
  public Calendar setAuthorizationDate() { return this.authorizationDate; }
  
  public void setAuthorizationDate(Calendar authorizationDate) { this.authorizationDate = authorizationDate; }
  
  public long setLastUpdatedCk() { return this.lastUpdatedCk; }
  
  public void setLastUpdatedCk(long lastUpdatedCk) { this.lastUpdatedCk = lastUpdatedCk; }
  
  public String getName() { return this.name; }
  
  public void setName(String name) { this.name = name; }
  
  public int getTaskAbbreviationID() { return this.taskAbbreviationID; }
  
  public void setTaskAbbreviationID(int taskAbbreviationIndex) { this.taskAbbreviationID = taskAbbreviationIndex; }
  
  public String getDepartment() { return this.department; }
  
  public void setDepartment(String department) { this.department = department; }
  
  public String getSelectionId() { return this.selectionId; }
  
  public void setSelectionId(String selectionId) { this.selectionId = selectionId; }
  
  public String getUpc() { return this.upc; }
  
  public void setUpc(String upc) { this.upc = upc; }
  
  public void setTaskWeeksToRelease(int taskWeeksToRelease) { this.taskWeeksToRelease = taskWeeksToRelease; }
  
  public String getSelectionNo() { return this.selectionNo; }
  
  public void setSelectionNo(String selectionNo) { this.selectionNo = selectionNo; }
  
  public void setStreetDate(Calendar streetDate) { this.streetDate = streetDate; }
  
  public Calendar getStreetDate() { return this.streetDate; }
}


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\ScheduledTask.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */