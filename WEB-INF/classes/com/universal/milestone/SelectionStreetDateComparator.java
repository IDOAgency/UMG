package WEB-INF.classes.com.universal.milestone;

import com.universal.milestone.Selection;
import java.util.Calendar;
import java.util.Comparator;

public class SelectionStreetDateComparator implements Comparator {
  public int compare(Object o1, Object o2) {
    Selection sel1 = (Selection)o1;
    Selection sel2 = (Selection)o2;
    try {
      Calendar streetDateCal1 = sel1.getStreetDate();
      Calendar streetDateCal2 = sel2.getStreetDate();
      if (streetDateCal1 == null && streetDateCal2 == null)
        return 0; 
      if (streetDateCal1 == null)
        return 1; 
      if (streetDateCal2 == null)
        return -1; 
      if (streetDateCal1.before(streetDateCal2))
        return -1; 
      if (streetDateCal1.after(streetDateCal2))
        return 1; 
      return 0;
    } catch (Exception e) {
      System.out.println(">>>>>>>>exception in SelectionStreetDateComparator");
      return 0;
    } 
  }
}


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\SelectionStreetDateComparator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */