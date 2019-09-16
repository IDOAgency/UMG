package WEB-INF.classes.com.universal.milestone;

import com.universal.milestone.MilestoneHelper;
import java.util.Calendar;
import java.util.Comparator;

public class StringDateComparator implements Comparator {
  public int compare(Object o1, Object o2) {
    try {
      Calendar thisStreetDate = null;
      if (!((String)o1).equalsIgnoreCase("TBS") && !((String)o1).equalsIgnoreCase("ITW"))
        thisStreetDate = MilestoneHelper.getDate((String)o1); 
      if (((String)o1).indexOf("TBS") > 0) {
        thisStreetDate = MilestoneHelper.getDate(((String)o1).substring(((String)o1).indexOf("TBS"), ((String)o1).length()));
      } else if (((String)o1).indexOf("ITW") > 0) {
        thisStreetDate = MilestoneHelper.getDate(((String)o1).substring(((String)o1).indexOf("ITW"), ((String)o1).length()));
      } 
      Calendar thatStreetDate = null;
      if (!((String)o2).equalsIgnoreCase("TBS") && !((String)o2).equalsIgnoreCase("ITW"))
        thatStreetDate = MilestoneHelper.getDate((String)o2); 
      if (((String)o2).indexOf("TBS") > 0) {
        thatStreetDate = MilestoneHelper.getDate(((String)o2).substring(((String)o2).indexOf("TBS"), ((String)o2).length()));
      } else if (((String)o2).indexOf("ITW") > 0) {
        thatStreetDate = MilestoneHelper.getDate(((String)o2).substring(((String)o2).indexOf("ITW"), ((String)o2).length()));
      } 
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
    } catch (Exception e) {
      return 1;
    } 
  }
}


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\StringDateComparator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */