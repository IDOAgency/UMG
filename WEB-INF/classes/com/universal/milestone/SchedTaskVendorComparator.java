package WEB-INF.classes.com.universal.milestone;

import com.universal.milestone.ScheduledTask;
import java.util.Comparator;

public class SchedTaskVendorComparator implements Comparator {
  public int compare(Object o1, Object o2) {
    try {
      ScheduledTask task1 = (ScheduledTask)o1;
      ScheduledTask task2 = (ScheduledTask)o2;
      String vendor1 = task1.getVendor();
      String vendor2 = task2.getVendor();
      return vendor1.compareTo(vendor2);
    } catch (Exception e) {
      return 0;
    } 
  }
}


/* Location:              D:\Documents\NetBeansProjects\milestone2 Local.war!\WEB-INF\classes\co\\universal\milestone\SchedTaskVendorComparator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */