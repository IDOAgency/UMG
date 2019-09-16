package WEB-INF.classes.com.universal.milestone;

import com.universal.milestone.MilestoneHelper;
import java.util.Calendar;
import java.util.Comparator;

public class IDJReportDateArtistComparator implements Comparator {
  public int compare(Object o1, Object o2) {
    Calendar calEntry2, calEntry1;
    String entry1 = (String)o1;
    String entry2 = (String)o2;
    String dateEntry1 = "";
    String dateEntry2 = "";
    if (entry1.startsWith("TBS")) {
      dateEntry1 = entry1.substring(6, entry1.lastIndexOf("*") - 1);
      dateEntry2 = entry2.substring(6, entry2.lastIndexOf("*") - 1);
    } else {
      dateEntry1 = entry1.substring(0, entry1.indexOf("*") - 1);
      dateEntry2 = entry2.substring(0, entry2.indexOf("*") - 1);
    } 
    try {
      calEntry1 = MilestoneHelper.getDate(dateEntry1);
      calEntry2 = MilestoneHelper.getDate(dateEntry2);
    } catch (Exception e) {
      System.out.println(">>>>>>>>exception in IDJReportDateArtistComparator");
      return 0;
    } 
    String artistEntry1 = "";
    String artistEntry2 = "";
    if (entry1.startsWith("TBS")) {
      artistEntry1 = entry1.substring(entry1.lastIndexOf("*") + 2, entry1.length()).toUpperCase().trim();
      artistEntry2 = entry2.substring(entry2.lastIndexOf("*") + 2, entry2.length()).toUpperCase().trim();
    } else {
      artistEntry1 = entry1.substring(entry1.indexOf("*") + 2, entry1.length()).toUpperCase().trim();
      artistEntry2 = entry2.substring(entry2.indexOf("*") + 2, entry2.length()).toUpperCase().trim();
    } 
    if (calEntry1 == null && calEntry2 == null)
      return artistEntry1.compareTo(artistEntry2); 
    if (calEntry1 == null || calEntry2 == null) {
      if (calEntry1 == null && calEntry2 == null)
        return 0; 
      if (calEntry1 == null)
        return 1; 
      if (calEntry2 == null)
        return -1; 
      if (calEntry1.before(calEntry2))
        return -1; 
      if (calEntry1.after(calEntry2))
        return 1; 
      return 0;
    } 
    if (calEntry1.equals(calEntry2))
      return artistEntry1.compareTo(artistEntry2); 
    if (calEntry1 == null && calEntry2 == null)
      return 0; 
    if (calEntry1 == null)
      return 1; 
    if (calEntry2 == null)
      return -1; 
    if (calEntry1.before(calEntry2))
      return -1; 
    if (calEntry1.after(calEntry2))
      return 1; 
    return 0;
  }
}


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\IDJReportDateArtistComparator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */