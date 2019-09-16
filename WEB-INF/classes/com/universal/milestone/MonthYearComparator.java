package WEB-INF.classes.com.universal.milestone;

import com.universal.milestone.MilestoneHelper;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;

public class MonthYearComparator implements Comparator {
  public int compare(Object o1, Object o2) {
    int catchFlag = 1;
    try {
      String dateString1 = (String)o1;
      Calendar cal1 = MilestoneHelper.getMYDate(dateString1);
      Date date1 = cal1.getTime();
      catchFlag = -1;
      String dateString2 = (String)o2;
      Calendar cal2 = MilestoneHelper.getMYDate(dateString2);
      Date date2 = cal2.getTime();
      return date1.compareTo(date2);
    } catch (Exception e) {
      return catchFlag;
    } 
  }
}


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\MonthYearComparator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */