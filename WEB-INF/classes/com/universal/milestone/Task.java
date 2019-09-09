/*     */ package WEB-INF.classes.com.universal.milestone;
/*     */ 
/*     */ import com.universal.milestone.Day;
/*     */ import com.universal.milestone.Family;
/*     */ import com.universal.milestone.MilestoneDataEntity;
/*     */ import com.universal.milestone.NotepadContentObject;
/*     */ import com.universal.milestone.Task;
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
/*     */ public class Task
/*     */   extends MilestoneDataEntity
/*     */   implements Cloneable, NotepadContentObject
/*     */ {
/*  38 */   protected int taskID = 0;
/*  39 */   protected int weekAdjustment = 0;
/*  40 */   protected String department = "";
/*  41 */   protected int taskAbbreviationIndex = 0;
/*  42 */   protected String category = "";
/*     */   
/*  44 */   protected String taskName = "";
/*  45 */   protected String taskDescription = "";
/*  46 */   protected String comments = "";
/*     */ 
/*     */   
/*     */   protected boolean keyTask = false;
/*     */   
/*  51 */   protected Family owner = null;
/*  52 */   protected int weeksToRelease = 0;
/*  53 */   protected Day dayOfTheWeek = null;
/*     */   
/*  55 */   protected Vector templates = null;
/*     */   
/*     */   protected boolean activeFlag;
/*     */   protected int lastUpdatingUser;
/*     */   protected Calendar lastUpdateDate;
/*     */   protected long lastUpdatedCk;
/*  61 */   protected String taskAbbrevStr = "";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean allowMultCompleteDatesFlag = false;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  75 */   public String getTableName() { return "Task"; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  85 */   public int getIdentity() { return getTaskID(); }
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
/*  99 */   public int getTaskID() { return this.taskID; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 109 */   public void setTaskID(int taskID) { this.taskID = taskID; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 119 */   public String getTaskName() { return this.taskName; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTaskName(String taskName) {
/* 129 */     auditCheck("name", this.taskName, taskName);
/* 130 */     this.taskName = taskName;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 140 */   public int getWeeksToRelease() { return this.weeksToRelease; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setWeeksToRelease(int weeksToRelease) {
/* 150 */     auditCheck("weeks_to_release", this.weeksToRelease, weeksToRelease);
/* 151 */     this.weeksToRelease = weeksToRelease;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 159 */   public Day getDayOfTheWeek() { return this.dayOfTheWeek; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 169 */   public void setDayOfTheWeek(Day day) { this.dayOfTheWeek = day; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 179 */   public int getWeekAdjustment() { return this.weekAdjustment; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setWeekAdjustment(int week) {
/* 189 */     auditCheck("week_adjustment", this.weekAdjustment, week);
/* 190 */     this.weekAdjustment = week;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 200 */   public String getDepartment() { return this.department; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDepartment(String department) {
/* 210 */     auditCheck("department", this.department, department);
/* 211 */     this.department = department;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 221 */   public String getCategory() { return this.category; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setCategory(String category) {
/* 231 */     auditCheck("category", this.category, category);
/* 232 */     this.category = category;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 242 */   public Family getOwner() { return this.owner; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 252 */   public void setOwner(Family owner) { this.owner = owner; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 262 */   public boolean getAllowMultCompleteDatesFlag() { return this.allowMultCompleteDatesFlag; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 272 */   public void setAllowMultCompleteDatesFlag(boolean allowed) { this.allowMultCompleteDatesFlag = allowed; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 282 */   public int getTaskAbbreviation() { return this.taskAbbreviationIndex; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTaskAbbreviation(int taskAbbreviationIndex) {
/* 292 */     auditCheck("abbrev_id", this.taskAbbreviationIndex, taskAbbreviationIndex);
/* 293 */     this.taskAbbreviationIndex = taskAbbreviationIndex;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 303 */   public String getTaskDescription() { return this.taskDescription; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTaskDescription(String taskDescription) {
/* 313 */     auditCheck("description", this.taskDescription, taskDescription);
/* 314 */     this.taskDescription = taskDescription;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 325 */   public String getComments() { return this.comments; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 335 */   public void setComments(String comments) { this.comments = comments; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 345 */   public boolean getIsKeyTask() { return this.keyTask; }
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
/*     */   public void setIsKeyTask(boolean isKeyTask) { // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: ldc 'key_task_indicator'
/*     */     //   3: new java/lang/StringBuilder
/*     */     //   6: dup
/*     */     //   7: invokespecial <init> : ()V
/*     */     //   10: aload_0
/*     */     //   11: getfield keyTask : Z
/*     */     //   14: invokevirtual append : (Z)Ljava/lang/StringBuilder;
/*     */     //   17: invokevirtual toString : ()Ljava/lang/String;
/*     */     //   20: new java/lang/StringBuilder
/*     */     //   23: dup
/*     */     //   24: invokespecial <init> : ()V
/*     */     //   27: iload_1
/*     */     //   28: invokevirtual append : (Z)Ljava/lang/StringBuilder;
/*     */     //   31: invokevirtual toString : ()Ljava/lang/String;
/*     */     //   34: invokevirtual auditCheck : (Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V
/*     */     //   37: aload_0
/*     */     //   38: iload_1
/*     */     //   39: putfield keyTask : Z
/*     */     //   42: return
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #355	-> 0
/*     */     //   #356	-> 37
/*     */     //   #357	-> 42
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   0	43	0	this	Lcom/universal/milestone/Task;
/*     */     //   0	43	1	isKeyTask	Z }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 366 */   public int getLastUpdatingUser() { return this.lastUpdatingUser; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 376 */   public void setLastUpdatingUser(int updatingUser) { this.lastUpdatingUser = updatingUser; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 386 */   public Calendar getLastUpdateDate() { return this.lastUpdateDate; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 396 */   public void setLastUpdateDate(Calendar lastUpdateDate) { this.lastUpdateDate = lastUpdateDate; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 406 */   public boolean getActiveFlag() { return this.activeFlag; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 416 */   public void setActiveFlag(boolean activeFlag) { this.activeFlag = activeFlag; }
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
/* 430 */   public Vector getTemplates() { return this.templates; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 440 */   public void setTemplates(Vector templates) { this.templates = templates; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 450 */   public long getLastUpdatedCk() { return this.lastUpdatedCk; }
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
/* 461 */   public void setLastUpdatedCk(long lastUpdatedCk) { this.lastUpdatedCk = lastUpdatedCk; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 470 */   public String getNotepadContentObjectId() { return Integer.toString(this.taskID); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 479 */   public String getTaskAbbrStr() { return this.taskAbbrevStr; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 489 */   public void setTaskAbbrStr(String taskAbbrevStr) { this.taskAbbrevStr = taskAbbrevStr; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 495 */   public Object clone() throws CloneNotSupportedException { return super.clone(); }
/*     */ }


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\Task.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */