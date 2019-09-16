package WEB-INF.classes.com.universal.milestone;

import com.universal.milestone.MilestoneHelper;
import java.util.Calendar;
import java.util.Comparator;

public class DateConverterComparator implements Comparator {
  public int compare(Object o1, Object o2) {
    Calendar thisStreetDate = MilestoneHelper.getDate((String)o1);
    Calendar thatStreetDate = MilestoneHelper.getDate((String)o2);
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
    return 0;
  }
}


/* Location:              D:\Documents\NetBeansProjects\milestone2 Local.war!\WEB-INF\classes\co\\universal\milestone\DateConverterComparator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */