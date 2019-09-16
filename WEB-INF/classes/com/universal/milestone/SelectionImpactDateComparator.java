package WEB-INF.classes.com.universal.milestone;

import com.universal.milestone.Selection;
import java.util.Calendar;
import java.util.Comparator;

public class SelectionImpactDateComparator implements Comparator {
  public int compare(Object o1, Object o2) {
    Selection sel1 = (Selection)o1;
    Selection sel2 = (Selection)o2;
    if (sel1.getImpactDates().size() != 0 && sel1.getImpactDates().size() != 0)
      try {
        Calendar impactCal1 = sel1.getImpactDateObject().getImpactDate();
        Calendar impactCal2 = sel2.getImpactDateObject().getImpactDate();
        if (impactCal1.before(impactCal2))
          return -1; 
        if (impactCal1.after(impactCal2))
          return 1; 
        return 0;
      } catch (Exception e) {
        System.out.println(">>>>>>>>exception in SelectionImpactDateComparator");
        return 0;
      }  
    return 0;
  }
}


/* Location:              D:\Documents\NetBeansProjects\milestone2 Local.war!\WEB-INF\classes\co\\universal\milestone\SelectionImpactDateComparator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */