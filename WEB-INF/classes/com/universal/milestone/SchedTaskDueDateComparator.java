package WEB-INF.classes.com.universal.milestone;

import com.universal.milestone.ScheduledTask;
import java.util.Comparator;
import java.util.Date;

public class SchedTaskDueDateComparator implements Comparator {
  public int compare(Object o1, Object o2) {
    int catchFlag = -1;
    try {
      ScheduledTask task1 = (ScheduledTask)o1;
      Date task1Date = task1.getDueDate().getTime();
      catchFlag = 1;
      ScheduledTask task2 = (ScheduledTask)o2;
      Date task2Date = task2.getDueDate().getTime();
      return task1Date.compareTo(task2Date);
    } catch (Exception e) {
      return catchFlag;
    } 
  }
}


/* Location:              D:\Documents\NetBeansProjects\milestone2 Local.war!\WEB-INF\classes\co\\universal\milestone\SchedTaskDueDateComparator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */