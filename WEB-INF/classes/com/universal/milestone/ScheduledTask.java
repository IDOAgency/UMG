/*     */ package WEB-INF.classes.com.universal.milestone;
/*     */ 
/*     */ import com.techempower.DataEntity;
/*     */ import com.universal.milestone.Day;
/*     */ import com.universal.milestone.Family;
/*     */ import com.universal.milestone.ScheduledTask;
/*     */ import java.util.Calendar;
/*     */ import java.util.Vector;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ScheduledTask
/*     */   extends DataEntity
/*     */ {
/*     */   protected int releaseID;
/*  37 */   protected Family owner = null;
/*     */   protected boolean isKeyTask;
/*  39 */   protected String authorizationName = "";
/*  40 */   protected Calendar authorizationDate = null;
/*  41 */   protected String name = "";
/*  42 */   protected String department = "";
/*     */   
/*     */   protected int scheduledTaskID;
/*     */   
/*     */   protected int scheduleID;
/*  47 */   protected int taskID = -1;
/*  48 */   protected int taskAbbreviationID = -1;
/*  49 */   protected String scheduledTaskStatus = "";
/*     */   
/*  51 */   protected String comments = "";
/*  52 */   protected String vendor = "";
/*     */   
/*  54 */   protected int weeksToRelease = 0;
/*  55 */   protected Calendar dueDate = null;
/*  56 */   protected Day dayOfTheWeek = null;
/*     */   
/*  58 */   protected Calendar completionDate = null;
/*     */ 
/*     */   
/*  61 */   protected Vector multCompleteDates = null;
/*     */   
/*     */   protected boolean allowMultCompleteDatesFlag = false;
/*  64 */   protected Calendar lastUpdatingDate = null;
/*     */   
/*     */   protected int lastUpdatingUser;
/*     */   protected long lastUpdatedCk;
/*  68 */   protected String selectionId = "";
/*  69 */   protected String upc = "";
/*  70 */   protected int taskWeeksToRelease = 0;
/*     */ 
/*     */   
/*  73 */   protected String selectionNo = "";
/*  74 */   protected Calendar streetDate = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  88 */   public int getScheduledTaskID() { return this.scheduledTaskID; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  93 */   public void setScheduledTaskID(int scheduledTaskID) { this.scheduledTaskID = scheduledTaskID; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 103 */   public int getScheduleID() { return this.scheduleID; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 108 */   public void setScheduleID(int scheduleID) { this.scheduleID = scheduleID; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 118 */   public int getTaskID() { return this.taskID; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 123 */   public void setTaskID(int taskID) { this.taskID = taskID; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 133 */   public int getWeeksToRelease() { return this.weeksToRelease; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 143 */   public Day getDayOfTheWeek() { return this.dayOfTheWeek; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 153 */   public String getComments() { return this.comments; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 163 */   public Calendar getDueDate() { return this.dueDate; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 173 */   public Calendar getCompletionDate() { return this.completionDate; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 183 */   public Vector getMultCompleteDates() { return this.multCompleteDates; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 193 */   public boolean getAllowMultCompleteDatesFlag() { return this.allowMultCompleteDatesFlag; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 203 */   public String getScheduledTaskStatus() { return this.scheduledTaskStatus; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 213 */   public String getVendor() { return this.vendor; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 224 */   public int getTaskWeeksToRelease() { return this.taskWeeksToRelease; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 237 */   public void setWeeksToRelease(int weeksToRelease) { this.weeksToRelease = weeksToRelease; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 247 */   public void setDayOfTheWeek(Day dayOfTheWeek) { this.dayOfTheWeek = dayOfTheWeek; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 257 */   public void setComments(String comments) { this.comments = comments; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 267 */   public void setDueDate(Calendar dueDate) { this.dueDate = dueDate; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 277 */   public void setCompletionDate(Calendar completeDate) { this.completionDate = completeDate; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 287 */   public void setMultCompleteDates(Vector multCompleteDates) { this.multCompleteDates = multCompleteDates; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 297 */   public void setAllowMultCompleteDatesFlag(boolean allowed) { this.allowMultCompleteDatesFlag = allowed; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 307 */   public void setScheduledTaskStatus(String taskStatus) { this.scheduledTaskStatus = taskStatus; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 317 */   public void setVendor(String vendor) { this.vendor = vendor; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 326 */   public Calendar recalcDueDates() { return this.dueDate; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 336 */   public int getLastUpdatingUser() { return this.lastUpdatingUser; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 346 */   public void setLastUpdatingUser(int lastUpdatingUser) { this.lastUpdatingUser = lastUpdatingUser; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 356 */   public Calendar getLastUpdateDate() { return this.lastUpdatingDate; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 367 */   public void setLastUpdateDate(Calendar lastUpdateDate) { this.lastUpdatingDate = lastUpdateDate; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 377 */   public int getReleaseID() { return this.releaseID; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 388 */   public void setReleaseID(int releaseID) { this.releaseID = releaseID; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 398 */   public Family getOwner() { return this.owner; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 403 */   public void setOwner(Family owner) { this.owner = owner; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 414 */   public void setReleaseID(Family owner) { this.owner = owner; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 424 */   public boolean getIsKeytask() { return this.isKeyTask; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 435 */   public void setKeyTask(boolean isKeyTask) { this.isKeyTask = isKeyTask; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 445 */   public String getAuthorizationName() { return this.authorizationName; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 456 */   public void setAuthorizationName(String authorizationName) { this.authorizationName = authorizationName; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 466 */   public Calendar setAuthorizationDate() { return this.authorizationDate; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 477 */   public void setAuthorizationDate(Calendar authorizationDate) { this.authorizationDate = authorizationDate; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 488 */   public long setLastUpdatedCk() { return this.lastUpdatedCk; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 499 */   public void setLastUpdatedCk(long lastUpdatedCk) { this.lastUpdatedCk = lastUpdatedCk; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 509 */   public String getName() { return this.name; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 520 */   public void setName(String name) { this.name = name; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 531 */   public int getTaskAbbreviationID() { return this.taskAbbreviationID; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 541 */   public void setTaskAbbreviationID(int taskAbbreviationIndex) { this.taskAbbreviationID = taskAbbreviationIndex; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 551 */   public String getDepartment() { return this.department; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 561 */   public void setDepartment(String department) { this.department = department; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 571 */   public String getSelectionId() { return this.selectionId; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 581 */   public void setSelectionId(String selectionId) { this.selectionId = selectionId; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 591 */   public String getUpc() { return this.upc; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 601 */   public void setUpc(String upc) { this.upc = upc; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 613 */   public void setTaskWeeksToRelease(int taskWeeksToRelease) { this.taskWeeksToRelease = taskWeeksToRelease; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 624 */   public String getSelectionNo() { return this.selectionNo; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 634 */   public void setSelectionNo(String selectionNo) { this.selectionNo = selectionNo; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 643 */   public void setStreetDate(Calendar streetDate) { this.streetDate = streetDate; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 651 */   public Calendar getStreetDate() { return this.streetDate; }
/*     */ }


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\ScheduledTask.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */