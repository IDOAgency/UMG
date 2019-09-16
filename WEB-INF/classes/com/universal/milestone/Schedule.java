package WEB-INF.classes.com.universal.milestone;

import com.techempower.DataEntity;
import com.universal.milestone.Schedule;
import java.util.Vector;

public class Schedule extends DataEntity {
  int scheduleID;
  
  int selectionID;
  
  int filter;
  
  Vector ScheduledTasks;
  
  public int getFilter() { return this.filter; }
  
  public void setFilter(int filter) { this.filter = filter; }
  
  public int getScheduleID() { return this.scheduleID; }
  
  public int getSelectionID() { return this.selectionID; }
  
  public void setSelectionID(int selectionID) { this.selectionID = selectionID; }
  
  public Vector getTasks() { return this.ScheduledTasks; }
  
  public void setTasks(Vector tasks) { this.ScheduledTasks = tasks; }
}


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\Schedule.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */