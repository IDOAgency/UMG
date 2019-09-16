package WEB-INF.classes.com.universal.milestone;

import com.universal.milestone.MilestoneHelper;
import java.util.Calendar;
import java.util.Comparator;

public class StringDateCycleComparator implements Comparator {
  public int compare(Object o1, Object o2) {
    try {
      String o1Date = ((String)o1).substring(0, ((String)o1).indexOf(" "));
      String o2Date = ((String)o2).substring(0, ((String)o2).indexOf(" "));
      String o1Cycle = ((String)o1).substring(((String)o1).indexOf(" "), ((String)o1).length());
      String o2Cycle = ((String)o2).substring(((String)o2).indexOf(" "), ((String)o2).length());
      Calendar thisStreetDate = MilestoneHelper.getDate(o1Date);
      Calendar thatStreetDate = MilestoneHelper.getDate(o2Date);
      if (thisStreetDate == null && thatStreetDate == null)
        return 0; 
      if (thisStreetDate == null)
        return 1; 
      if (thatStreetDate == null)
        return -1; 
      if (thisStreetDate.before(thatStreetDate))
        return -1; 
      if (thisStreetDate.after(thatStreetDate))
        return 1; 
      if (thisStreetDate.equals(thatStreetDate))
        return o1Cycle.compareTo(o2Cycle); 
      return 0;
    } catch (Exception e) {
      return 0;
    } 
  }
}


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\StringDateCycleComparator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */