package WEB-INF.classes.com.universal.milestone;

import com.universal.milestone.MilestoneHelper;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Vector;

public class MexicoReportComparator implements Comparator {
  public int compare(Object o1, Object o2) {
    try {
      String string1 = (String)o1;
      String string2 = (String)o2;
      String config1 = string1.substring(0, string1.indexOf("|"));
      String config2 = string2.substring(0, string2.indexOf("|"));
      String date1 = string1.substring(string1.indexOf("|") + 1, string1.length());
      String date2 = string2.substring(string2.indexOf("|") + 1, string2.length());
      String[] configList = { 
          "Promos in Spanish", 
          "Promos in English", 
          "Pending Albums", 
          "Commercial", 
          "National Classical", 
          "Popular Imports", 
          "DVDs", 
          "Special Products", 
          "Distributed Promos", 
          "Distributed Commercial", 
          "" };
      Vector configListVector = new Vector();
      for (int i = 0; i < configList.length; i++)
        configListVector.add(configList[i]); 
      if (config1.equals(config2)) {
        Calendar thisStreetDate = MilestoneHelper.getDate(date1);
        Calendar thatStreetDate = MilestoneHelper.getDate(date2);
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
      if (configListVector.indexOf(config1) < configListVector.indexOf(config2))
        return -1; 
      return 1;
    } catch (Exception e) {
      System.out.println("Exception raised in MexicoReportComparator");
      return 0;
    } 
  }
}


/* Location:              D:\Documents\NetBeansProjects\milestone2 Local.war!\WEB-INF\classes\co\\universal\milestone\MexicoReportComparator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */