package WEB-INF.classes.com.universal.milestone;

import com.universal.milestone.Day;
import com.universal.milestone.Family;
import com.universal.milestone.MilestoneDataEntity;
import com.universal.milestone.NotepadContentObject;
import com.universal.milestone.Task;
import java.util.Calendar;
import java.util.Vector;

public class Task extends MilestoneDataEntity implements Cloneable, NotepadContentObject {
  protected int taskID = 0;
  
  protected int weekAdjustment = 0;
  
  protected String department = "";
  
  protected int taskAbbreviationIndex = 0;
  
  protected String category = "";
  
  protected String taskName = "";
  
  protected String taskDescription = "";
  
  protected String comments = "";
  
  protected boolean keyTask = false;
  
  protected Family owner = null;
  
  protected int weeksToRelease = 0;
  
  protected Day dayOfTheWeek = null;
  
  protected Vector templates = null;
  
  protected boolean activeFlag;
  
  protected int lastUpdatingUser;
  
  protected Calendar lastUpdateDate;
  
  protected long lastUpdatedCk;
  
  protected String taskAbbrevStr = "";
  
  protected boolean allowMultCompleteDatesFlag = false;
  
  public String getTableName() { return "Task"; }
  
  public int getIdentity() { return getTaskID(); }
  
  public int getTaskID() { return this.taskID; }
  
  public void setTaskID(int taskID) { this.taskID = taskID; }
  
  public String getTaskName() { return this.taskName; }
  
  public void setTaskName(String taskName) {
    auditCheck("name", this.taskName, taskName);
    this.taskName = taskName;
  }
  
  public int getWeeksToRelease() { return this.weeksToRelease; }
  
  public void setWeeksToRelease(int weeksToRelease) {
    auditCheck("weeks_to_release", this.weeksToRelease, weeksToRelease);
    this.weeksToRelease = weeksToRelease;
  }
  
  public Day getDayOfTheWeek() { return this.dayOfTheWeek; }
  
  public void setDayOfTheWeek(Day day) { this.dayOfTheWeek = day; }
  
  public int getWeekAdjustment() { return this.weekAdjustment; }
  
  public void setWeekAdjustment(int week) {
    auditCheck("week_adjustment", this.weekAdjustment, week);
    this.weekAdjustment = week;
  }
  
  public String getDepartment() { return this.department; }
  
  public void setDepartment(String department) {
    auditCheck("department", this.department, department);
    this.department = department;
  }
  
  public String getCategory() { return this.category; }
  
  public void setCategory(String category) {
    auditCheck("category", this.category, category);
    this.category = category;
  }
  
  public Family getOwner() { return this.owner; }
  
  public void setOwner(Family owner) { this.owner = owner; }
  
  public boolean getAllowMultCompleteDatesFlag() { return this.allowMultCompleteDatesFlag; }
  
  public void setAllowMultCompleteDatesFlag(boolean allowed) { this.allowMultCompleteDatesFlag = allowed; }
  
  public int getTaskAbbreviation() { return this.taskAbbreviationIndex; }
  
  public void setTaskAbbreviation(int taskAbbreviationIndex) {
    auditCheck("abbrev_id", this.taskAbbreviationIndex, taskAbbreviationIndex);
    this.taskAbbreviationIndex = taskAbbreviationIndex;
  }
  
  public String getTaskDescription() { return this.taskDescription; }
  
  public void setTaskDescription(String taskDescription) {
    auditCheck("description", this.taskDescription, taskDescription);
    this.taskDescription = taskDescription;
  }
  
  public String getComments() { return this.comments; }
  
  public void setComments(String comments) { this.comments = comments; }
  
  public boolean getIsKeyTask() { return this.keyTask; }
  
  public void setIsKeyTask(boolean isKeyTask) { // Byte code:
    //   0: aload_0
    //   1: ldc 'key_task_indicator'
    //   3: new java/lang/StringBuilder
    //   6: dup
    //   7: invokespecial <init> : ()V
    //   10: aload_0
    //   11: getfield keyTask : Z
    //   14: invokevirtual append : (Z)Ljava/lang/StringBuilder;
    //   17: invokevirtual toString : ()Ljava/lang/String;
    //   20: new java/lang/StringBuilder
    //   23: dup
    //   24: invokespecial <init> : ()V
    //   27: iload_1
    //   28: invokevirtual append : (Z)Ljava/lang/StringBuilder;
    //   31: invokevirtual toString : ()Ljava/lang/String;
    //   34: invokevirtual auditCheck : (Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V
    //   37: aload_0
    //   38: iload_1
    //   39: putfield keyTask : Z
    //   42: return
    // Line number table:
    //   Java source line number -> byte code offset
    //   #355	-> 0
    //   #356	-> 37
    //   #357	-> 42
    // Local variable table:
    //   start	length	slot	name	descriptor
    //   0	43	0	this	Lcom/universal/milestone/Task;
    //   0	43	1	isKeyTask	Z }
  
  public int getLastUpdatingUser() { return this.lastUpdatingUser; }
  
  public void setLastUpdatingUser(int updatingUser) { this.lastUpdatingUser = updatingUser; }
  
  public Calendar getLastUpdateDate() { return this.lastUpdateDate; }
  
  public void setLastUpdateDate(Calendar lastUpdateDate) { this.lastUpdateDate = lastUpdateDate; }
  
  public boolean getActiveFlag() { return this.activeFlag; }
  
  public void setActiveFlag(boolean activeFlag) { this.activeFlag = activeFlag; }
  
  public Vector getTemplates() { return this.templates; }
  
  public void setTemplates(Vector templates) { this.templates = templates; }
  
  public long getLastUpdatedCk() { return this.lastUpdatedCk; }
  
  public void setLastUpdatedCk(long lastUpdatedCk) { this.lastUpdatedCk = lastUpdatedCk; }
  
  public String getNotepadContentObjectId() { return Integer.toString(this.taskID); }
  
  public String getTaskAbbrStr() { return this.taskAbbrevStr; }
  
  public void setTaskAbbrStr(String taskAbbrevStr) { this.taskAbbrevStr = taskAbbrevStr; }
  
  public Object clone() throws CloneNotSupportedException { return super.clone(); }
}


/* Location:              D:\Documents\NetBeansProjects\milestone2 Local.war!\WEB-INF\classes\co\\universal\milestone\Task.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */