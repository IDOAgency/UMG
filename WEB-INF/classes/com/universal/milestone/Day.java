/*     */ package WEB-INF.classes.com.universal.milestone;
/*     */ 
/*     */ import com.universal.milestone.Day;
/*     */ import com.universal.milestone.MilestoneDataEntity;
/*     */ import com.universal.milestone.NotepadContentObject;
/*     */ import java.util.Calendar;
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
/*     */ public class Day
/*     */   extends MilestoneDataEntity
/*     */   implements NotepadContentObject
/*     */ {
/*     */   protected int dayID;
/*     */   protected String dayType;
/*     */   protected String dayDescription;
/*     */   protected Calendar specificDate;
/*     */   protected int calendarGroup;
/*     */   protected int lastUpdatingUser;
/*     */   protected Calendar lastUpdateDate;
/*     */   protected long lastUpdatedCk;
/*     */   
/*  64 */   public String getTableName() { return "Day_Type"; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  74 */   public int getIdentity() { return getDayID(); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Day(int day) {
/*     */     this.dayID = 0;
/*     */     this.dayType = "";
/*     */     this.dayDescription = "";
/*     */     this.specificDate = null;
/*     */     this.calendarGroup = 0;
/*  86 */     this.dayID = day;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Day(String day) {
/*     */     this.dayID = 0;
/*     */     this.dayType = "";
/*     */     this.dayDescription = "";
/*     */     this.specificDate = null;
/*     */     this.calendarGroup = 0;
/* 102 */     int dayID = -1;
/*     */     
/* 104 */     if (day.toUpperCase().equalsIgnoreCase("M")) {
/* 105 */       dayID = 1;
/* 106 */     } else if (day.toUpperCase().equalsIgnoreCase("T")) {
/* 107 */       dayID = 2;
/* 108 */     } else if (day.toUpperCase().equalsIgnoreCase("W")) {
/* 109 */       dayID = 3;
/* 110 */     } else if (day.toUpperCase().equalsIgnoreCase("TH")) {
/* 111 */       dayID = 4;
/* 112 */     } else if (day.toUpperCase().equalsIgnoreCase("F")) {
/* 113 */       dayID = 5;
/* 114 */     } else if (day.toUpperCase().equalsIgnoreCase("S")) {
/* 115 */       dayID = 6;
/* 116 */     } else if (day.toUpperCase().equalsIgnoreCase("SU")) {
/* 117 */       dayID = 7;
/* 118 */     } else if (day.toUpperCase().equalsIgnoreCase("D")) {
/* 119 */       dayID = 8;
/* 120 */     } else if (day.toUpperCase().equalsIgnoreCase("SOL")) {
/* 121 */       dayID = -10;
/*     */     } 
/*     */     
/*     */     try {
/* 125 */       dayID = Integer.parseInt(day);
/*     */     }
/* 127 */     catch (Exception exception) {}
/*     */ 
/*     */ 
/*     */     
/* 131 */     this.dayID = dayID;
/*     */   }
/*     */   public Day() {
/*     */     this.dayID = 0;
/*     */     this.dayType = "";
/*     */     this.dayDescription = "";
/*     */     this.specificDate = null;
/*     */     this.calendarGroup = 0;
/*     */   }
/*     */   
/* 141 */   public int getDayID() { return this.dayID; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 151 */   public void setDayID(int dayID) { this.dayID = dayID; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDay() {
/* 161 */     switch (this.dayID) {
/*     */       
/*     */       case 1:
/* 164 */         return "M";
/*     */       case 2:
/* 166 */         return "T";
/*     */       case 3:
/* 168 */         return "W";
/*     */       case 4:
/* 170 */         return "TH";
/*     */       case 5:
/* 172 */         return "F";
/*     */       case 6:
/* 174 */         return "S";
/*     */       case 7:
/* 176 */         return "SU";
/*     */       case 8:
/* 178 */         return "D";
/*     */       case 9:
/* 180 */         return "SOL";
/*     */     } 
/*     */     
/* 183 */     return String.valueOf(this.dayID);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 193 */   public String getTaskDay() { return String.valueOf(this.dayID); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 203 */   public int getCalendarGroup() { return this.calendarGroup; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setCalendarGroup(int calendarGroup) {
/* 213 */     auditCheck("grouping", this.calendarGroup, calendarGroup);
/* 214 */     this.calendarGroup = calendarGroup;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 224 */   public String getDescription() { return this.dayDescription; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDescription(String description) {
/* 234 */     auditCheck("description", this.dayDescription, this.dayDescription);
/* 235 */     this.dayDescription = description;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 245 */   public Calendar getSpecificDate() { return this.specificDate; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSpecificDate(Calendar specificDate) {
/* 255 */     auditCheck("date", this.specificDate, specificDate);
/* 256 */     this.specificDate = specificDate;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 266 */   public String getDayType() { return this.dayType; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDayType(String dayType) {
/* 276 */     auditCheck("value", this.dayType, dayType);
/* 277 */     this.dayType = dayType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 287 */   public long getLastUpdatedCk() { return this.lastUpdatedCk; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 297 */   public void setLastUpdatedCk(long lastUpdatedCk) { this.lastUpdatedCk = lastUpdatedCk; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 307 */   public int getLastUpdatingUser() { return this.lastUpdatingUser; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 317 */   public void setLastUpdatingUser(int updatingUser) { this.lastUpdatingUser = updatingUser; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 327 */   public Calendar getLastUpdateDate() { return this.lastUpdateDate; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 337 */   public void setLastUpdateDate(Calendar lastUpdateDate) { this.lastUpdateDate = lastUpdateDate; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 346 */   public String getNotepadContentObjectId() { return Integer.toString(this.dayID); }
/*     */ }


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\Day.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */