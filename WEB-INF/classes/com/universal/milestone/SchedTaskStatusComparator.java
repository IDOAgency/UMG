package WEB-INF.classes.com.universal.milestone;

import com.universal.milestone.ScheduledTask;
import java.util.Comparator;

public class SchedTaskStatusComparator implements Comparator {
  public int compare(Object o1, Object o2) {
    try {
      ScheduledTask task1 = (ScheduledTask)o1;
      ScheduledTask task2 = (ScheduledTask)o2;
      String status1 = task1.getScheduledTaskStatus();
      String status2 = task2.getScheduledTaskStatus();
      return status1.compareTo(status2);
    } catch (Exception e) {
      return 0;
    } 
  }
}


/* Location:              D:\Documents\NetBeansProjects\milestone2 Local.war!\WEB-INF\classes\co\\universal\milestone\SchedTaskStatusComparator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */