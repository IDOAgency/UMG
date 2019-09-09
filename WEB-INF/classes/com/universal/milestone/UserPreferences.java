/*     */ package WEB-INF.classes.com.universal.milestone;
/*     */ 
/*     */ import com.techempower.DataEntity;
/*     */ import com.universal.milestone.UserPreferences;
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
/*     */ public class UserPreferences
/*     */   extends DataEntity
/*     */   implements Cloneable
/*     */ {
/*  29 */   protected int openingScreen = 2;
/*  30 */   protected int autoClose = 1;
/*  31 */   protected int autoCloseDays = 0;
/*  32 */   protected int notepadSortBy = 1;
/*  33 */   protected int notepadOrder = 1;
/*  34 */   protected int notepadProductType = 2;
/*  35 */   protected int selectionReleasingFamily = 0;
/*  36 */   protected int selectionEnvironment = 0;
/*  37 */   protected int selectionLabelContact = 0;
/*  38 */   protected int selectionProductType = 2;
/*  39 */   protected int selectionStatus = 0;
/*  40 */   protected int selectionPriorCriteria = 0;
/*  41 */   protected int schedulePhysicalSortBy = 3;
/*  42 */   protected int schedulePhysicalOwner = 1;
/*  43 */   protected int scheduleDigitalSortBy = 3;
/*  44 */   protected int scheduleDigitalOwner = 1;
/*  45 */   protected int reportsReleaseType = 3;
/*  46 */   protected int reportsReleasingFamily = 0;
/*  47 */   protected int reportsEnvironment = 0;
/*  48 */   protected int reportsLabelContact = 0;
/*  49 */   protected int reportsUMLContact = 0;
/*  50 */   protected int reportsStatusAll = 1;
/*  51 */   protected int reportsStatusActive = 0;
/*  52 */   protected int reportsStatusTBS = 0;
/*  53 */   protected int reportsStatusClosed = 0;
/*  54 */   protected int reportsStatusCancelled = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean active = false;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  70 */   public int getOpeningScreen() { return this.openingScreen; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  78 */   public void setOpeningScreen(int openingScreen) { this.openingScreen = openingScreen; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  86 */   public int getAutoClose() { return this.autoClose; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  94 */   public void setAutoClose(int autoClose) { this.autoClose = autoClose; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 103 */   public int getAutoCloseDays() { return this.autoCloseDays; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 111 */   public void setAutoCloseDays(int autoCloseDays) { this.autoCloseDays = autoCloseDays; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 120 */   public int getNotepadSortBy() { return this.notepadSortBy; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 128 */   public void setNotepadSortBy(int notepadSortBy) { this.notepadSortBy = notepadSortBy; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 137 */   public int getNotepadOrder() { return this.notepadOrder; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 145 */   public void setNotepadOrder(int notepadOrder) { this.notepadOrder = notepadOrder; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 154 */   public int getNotepadProductType() { return this.notepadProductType; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 162 */   public void setNotepadProductType(int notepadProductType) { this.notepadProductType = notepadProductType; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 170 */   public int getSelectionReleasingFamily() { return this.selectionReleasingFamily; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 178 */   public void setSelectionReleasingFamily(int selectionReleasingFamily) { this.selectionReleasingFamily = selectionReleasingFamily; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 187 */   public int getSelectionEnvironment() { return this.selectionEnvironment; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 195 */   public void setSelectionEnvironment(int selectionEnvironment) { this.selectionEnvironment = selectionEnvironment; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 203 */   public int getSelectionLabelContact() { return this.selectionLabelContact; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 211 */   public void setSelectionLabelContact(int selectionLabelContact) { this.selectionLabelContact = selectionLabelContact; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 220 */   public int getSelectionProductType() { return this.selectionProductType; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 228 */   public void setSelectionProductType(int selectionProductType) { this.selectionProductType = selectionProductType; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 236 */   public int getSelectionStatus() { return this.selectionStatus; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 244 */   public void setSelectionStatus(int selectionStatus) { this.selectionStatus = selectionStatus; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 253 */   public int getSelectionPriorCriteria() { return this.selectionPriorCriteria; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 261 */   public void setSelectionPriorCriteria(int selectionPriorCriteria) { this.selectionPriorCriteria = selectionPriorCriteria; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 270 */   public int getSchedulePhysicalSortBy() { return this.schedulePhysicalSortBy; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 278 */   public void setSchedulePhysicalSortBy(int schedulePhysicalSortBy) { this.schedulePhysicalSortBy = schedulePhysicalSortBy; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 286 */   public int getSchedulePhysicalOwner() { return this.schedulePhysicalOwner; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 294 */   public void setSchedulePhysicalOwner(int schedulePhysicalOwner) { this.schedulePhysicalOwner = schedulePhysicalOwner; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 303 */   public int getScheduleDigitalSortBy() { return this.scheduleDigitalSortBy; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 311 */   public void setScheduleDigitalSortBy(int scheduleDigitalSortBy) { this.scheduleDigitalSortBy = scheduleDigitalSortBy; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 320 */   public int getScheduleDigitalOwner() { return this.scheduleDigitalOwner; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 328 */   public void setScheduleDigitalOwner(int scheduleDigitalOwner) { this.scheduleDigitalOwner = scheduleDigitalOwner; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 336 */   public int getReportsReleaseType() { return this.reportsReleaseType; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 344 */   public void setReportsReleaseType(int reportsReleaseType) { this.reportsReleaseType = reportsReleaseType; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 353 */   public int getReportsReleasingFamily() { return this.reportsReleasingFamily; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 361 */   public void setReportsReleasingFamily(int reportsReleasingFamily) { this.reportsReleasingFamily = reportsReleasingFamily; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 370 */   public int getReportsEnvironment() { return this.reportsEnvironment; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 378 */   public void setReportsEnvironment(int reportsEnvironment) { this.reportsEnvironment = reportsEnvironment; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 387 */   public int getReportsLabelContact() { return this.reportsLabelContact; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 395 */   public void setReportsLabelContact(int reportsLabelContact) { this.reportsLabelContact = reportsLabelContact; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 404 */   public int getReportsUMLContact() { return this.reportsUMLContact; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 412 */   public void setReportsUMLContact(int reportsUMLContact) { this.reportsUMLContact = reportsUMLContact; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 421 */   public int getReportsStatusAll() { return this.reportsStatusAll; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 429 */   public void setReportsStatusAll(int reportsStatusAll) { this.reportsStatusAll = reportsStatusAll; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 437 */   public int getReportsStatusActive() { return this.reportsStatusActive; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 445 */   public void setReportsStatusActive(int reportsStatusActive) { this.reportsStatusActive = reportsStatusActive; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 453 */   public int getReportsStatusTBS() { return this.reportsStatusTBS; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 461 */   public void setReportsStatusTBS(int reportsStatusTBS) { this.reportsStatusTBS = reportsStatusTBS; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 469 */   public int getReportsStatusClosed() { return this.reportsStatusClosed; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 477 */   public void setReportsStatusClosed(int reportsStatusClosed) { this.reportsStatusClosed = reportsStatusClosed; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 485 */   public int getReportsStatusCancelled() { return this.reportsStatusCancelled; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 493 */   public void setReportsStatusCancelled(int reportsStatusCancelled) { this.reportsStatusCancelled = reportsStatusCancelled; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 501 */   public boolean getActive() { return this.active; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 509 */   public void setActive(boolean active) { this.active = active; }
/*     */ }


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\UserPreferences.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */