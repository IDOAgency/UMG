package WEB-INF.classes.com.universal.milestone;

import com.universal.milestone.Task;
import java.util.Comparator;

public class TaskAbbrComparator implements Comparator {
  public int compare(Object o1, Object o2) {
    Task task1 = (Task)o1;
    Task task2 = (Task)o2;
    String task1Abbr = task1.getTaskAbbrStr().toUpperCase();
    String task2Abbr = task2.getTaskAbbrStr().toUpperCase();
    return task1Abbr.compareTo(task2Abbr);
  }
}


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\TaskAbbrComparator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */