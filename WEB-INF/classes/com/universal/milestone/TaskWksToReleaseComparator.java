package WEB-INF.classes.com.universal.milestone;

import com.universal.milestone.Task;
import java.util.Comparator;

public class TaskWksToReleaseComparator implements Comparator {
  public int compare(Object o1, Object o2) {
    Task task1 = (Task)o1;
    Task task2 = (Task)o2;
    int weeks1 = task1.getWeeksToRelease();
    int weeks2 = task2.getWeeksToRelease();
    return (new Integer(weeks1)).compareTo(new Integer(weeks2));
  }
}


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\TaskWksToReleaseComparator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */