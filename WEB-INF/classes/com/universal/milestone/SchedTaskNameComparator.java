package WEB-INF.classes.com.universal.milestone;

import com.universal.milestone.ScheduledTask;
import java.util.Comparator;

public class SchedTaskNameComparator implements Comparator {
  public int compare(Object o1, Object o2) {
    ScheduledTask task1 = (ScheduledTask)o1;
    ScheduledTask task2 = (ScheduledTask)o2;
    try {
      String task1Name = task1.getName().toUpperCase();
      String task2Name = task2.getName().toUpperCase();
      return task1Name.compareTo(task2Name);
    } catch (Exception e) {
      return 0;
    } 
  }
}


/* Location:              D:\Documents\NetBeansProjects\milestone2 Local.war!\WEB-INF\classes\co\\universal\milestone\SchedTaskNameComparator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */