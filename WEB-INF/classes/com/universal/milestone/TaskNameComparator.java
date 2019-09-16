package WEB-INF.classes.com.universal.milestone;

import com.universal.milestone.Task;
import java.util.Comparator;

public class TaskNameComparator implements Comparator {
  public int compare(Object o1, Object o2) {
    Task task1 = (Task)o1;
    Task task2 = (Task)o2;
    String task1Name = task1.getTaskName().toUpperCase();
    String task2Name = task2.getTaskName().toUpperCase();
    return task1Name.compareTo(task2Name);
  }
}


/* Location:              D:\Documents\NetBeansProjects\milestone2 Local.war!\WEB-INF\classes\co\\universal\milestone\TaskNameComparator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */