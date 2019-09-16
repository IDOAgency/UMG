package WEB-INF.classes.com.universal.milestone;

import com.universal.milestone.ScheduledTask;
import java.util.Comparator;

public class SchedTaskWksToReleaseComparator implements Comparator {
  public int compare(Object o1, Object o2) {
    try {
      ScheduledTask task1 = (ScheduledTask)o1;
      ScheduledTask task2 = (ScheduledTask)o2;
      int weeks1 = task1.getWeeksToRelease();
      int weeks2 = task2.getWeeksToRelease();
      return (new Integer(weeks2)).compareTo(new Integer(weeks1));
    } catch (Exception e) {
      return 0;
    } 
  }
}


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\SchedTaskWksToReleaseComparator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */