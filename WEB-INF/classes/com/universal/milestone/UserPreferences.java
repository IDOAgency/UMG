package WEB-INF.classes.com.universal.milestone;

import com.techempower.DataEntity;
import com.universal.milestone.UserPreferences;

public class UserPreferences extends DataEntity implements Cloneable {
  protected int openingScreen = 2;
  
  protected int autoClose = 1;
  
  protected int autoCloseDays = 0;
  
  protected int notepadSortBy = 1;
  
  protected int notepadOrder = 1;
  
  protected int notepadProductType = 2;
  
  protected int selectionReleasingFamily = 0;
  
  protected int selectionEnvironment = 0;
  
  protected int selectionLabelContact = 0;
  
  protected int selectionProductType = 2;
  
  protected int selectionStatus = 0;
  
  protected int selectionPriorCriteria = 0;
  
  protected int schedulePhysicalSortBy = 3;
  
  protected int schedulePhysicalOwner = 1;
  
  protected int scheduleDigitalSortBy = 3;
  
  protected int scheduleDigitalOwner = 1;
  
  protected int reportsReleaseType = 3;
  
  protected int reportsReleasingFamily = 0;
  
  protected int reportsEnvironment = 0;
  
  protected int reportsLabelContact = 0;
  
  protected int reportsUMLContact = 0;
  
  protected int reportsStatusAll = 1;
  
  protected int reportsStatusActive = 0;
  
  protected int reportsStatusTBS = 0;
  
  protected int reportsStatusClosed = 0;
  
  protected int reportsStatusCancelled = 0;
  
  protected boolean active = false;
  
  public int getOpeningScreen() { return this.openingScreen; }
  
  public void setOpeningScreen(int openingScreen) { this.openingScreen = openingScreen; }
  
  public int getAutoClose() { return this.autoClose; }
  
  public void setAutoClose(int autoClose) { this.autoClose = autoClose; }
  
  public int getAutoCloseDays() { return this.autoCloseDays; }
  
  public void setAutoCloseDays(int autoCloseDays) { this.autoCloseDays = autoCloseDays; }
  
  public int getNotepadSortBy() { return this.notepadSortBy; }
  
  public void setNotepadSortBy(int notepadSortBy) { this.notepadSortBy = notepadSortBy; }
  
  public int getNotepadOrder() { return this.notepadOrder; }
  
  public void setNotepadOrder(int notepadOrder) { this.notepadOrder = notepadOrder; }
  
  public int getNotepadProductType() { return this.notepadProductType; }
  
  public void setNotepadProductType(int notepadProductType) { this.notepadProductType = notepadProductType; }
  
  public int getSelectionReleasingFamily() { return this.selectionReleasingFamily; }
  
  public void setSelectionReleasingFamily(int selectionReleasingFamily) { this.selectionReleasingFamily = selectionReleasingFamily; }
  
  public int getSelectionEnvironment() { return this.selectionEnvironment; }
  
  public void setSelectionEnvironment(int selectionEnvironment) { this.selectionEnvironment = selectionEnvironment; }
  
  public int getSelectionLabelContact() { return this.selectionLabelContact; }
  
  public void setSelectionLabelContact(int selectionLabelContact) { this.selectionLabelContact = selectionLabelContact; }
  
  public int getSelectionProductType() { return this.selectionProductType; }
  
  public void setSelectionProductType(int selectionProductType) { this.selectionProductType = selectionProductType; }
  
  public int getSelectionStatus() { return this.selectionStatus; }
  
  public void setSelectionStatus(int selectionStatus) { this.selectionStatus = selectionStatus; }
  
  public int getSelectionPriorCriteria() { return this.selectionPriorCriteria; }
  
  public void setSelectionPriorCriteria(int selectionPriorCriteria) { this.selectionPriorCriteria = selectionPriorCriteria; }
  
  public int getSchedulePhysicalSortBy() { return this.schedulePhysicalSortBy; }
  
  public void setSchedulePhysicalSortBy(int schedulePhysicalSortBy) { this.schedulePhysicalSortBy = schedulePhysicalSortBy; }
  
  public int getSchedulePhysicalOwner() { return this.schedulePhysicalOwner; }
  
  public void setSchedulePhysicalOwner(int schedulePhysicalOwner) { this.schedulePhysicalOwner = schedulePhysicalOwner; }
  
  public int getScheduleDigitalSortBy() { return this.scheduleDigitalSortBy; }
  
  public void setScheduleDigitalSortBy(int scheduleDigitalSortBy) { this.scheduleDigitalSortBy = scheduleDigitalSortBy; }
  
  public int getScheduleDigitalOwner() { return this.scheduleDigitalOwner; }
  
  public void setScheduleDigitalOwner(int scheduleDigitalOwner) { this.scheduleDigitalOwner = scheduleDigitalOwner; }
  
  public int getReportsReleaseType() { return this.reportsReleaseType; }
  
  public void setReportsReleaseType(int reportsReleaseType) { this.reportsReleaseType = reportsReleaseType; }
  
  public int getReportsReleasingFamily() { return this.reportsReleasingFamily; }
  
  public void setReportsReleasingFamily(int reportsReleasingFamily) { this.reportsReleasingFamily = reportsReleasingFamily; }
  
  public int getReportsEnvironment() { return this.reportsEnvironment; }
  
  public void setReportsEnvironment(int reportsEnvironment) { this.reportsEnvironment = reportsEnvironment; }
  
  public int getReportsLabelContact() { return this.reportsLabelContact; }
  
  public void setReportsLabelContact(int reportsLabelContact) { this.reportsLabelContact = reportsLabelContact; }
  
  public int getReportsUMLContact() { return this.reportsUMLContact; }
  
  public void setReportsUMLContact(int reportsUMLContact) { this.reportsUMLContact = reportsUMLContact; }
  
  public int getReportsStatusAll() { return this.reportsStatusAll; }
  
  public void setReportsStatusAll(int reportsStatusAll) { this.reportsStatusAll = reportsStatusAll; }
  
  public int getReportsStatusActive() { return this.reportsStatusActive; }
  
  public void setReportsStatusActive(int reportsStatusActive) { this.reportsStatusActive = reportsStatusActive; }
  
  public int getReportsStatusTBS() { return this.reportsStatusTBS; }
  
  public void setReportsStatusTBS(int reportsStatusTBS) { this.reportsStatusTBS = reportsStatusTBS; }
  
  public int getReportsStatusClosed() { return this.reportsStatusClosed; }
  
  public void setReportsStatusClosed(int reportsStatusClosed) { this.reportsStatusClosed = reportsStatusClosed; }
  
  public int getReportsStatusCancelled() { return this.reportsStatusCancelled; }
  
  public void setReportsStatusCancelled(int reportsStatusCancelled) { this.reportsStatusCancelled = reportsStatusCancelled; }
  
  public boolean getActive() { return this.active; }
  
  public void setActive(boolean active) { this.active = active; }
}


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\UserPreferences.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */