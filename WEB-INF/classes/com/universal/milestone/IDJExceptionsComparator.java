package WEB-INF.classes.com.universal.milestone;

import com.universal.milestone.IDJExceptionsComparator;
import com.universal.milestone.Selection;
import com.universal.milestone.SelectionSubConfiguration;
import java.util.Calendar;
import java.util.Comparator;

public class IDJExceptionsComparator implements Comparator {
  public int compare(Object o1, Object o2) {
    Calendar calEntry2, calEntry1;
    Selection sel1 = (Selection)o1;
    Selection sel2 = (Selection)o2;
    String title1 = sel1.getTitle();
    String title2 = sel2.getTitle();
    if (sel1.getIsDigital()) {
      calEntry1 = sel1.getDigitalRlsDate();
    } else {
      calEntry1 = sel1.getStreetDate();
    } 
    if (sel2.getIsDigital()) {
      calEntry2 = sel2.getDigitalRlsDate();
    } else {
      calEntry2 = sel2.getStreetDate();
    } 
    boolean activeTBSSel1 = (calEntry1 != null);
    boolean activeTBSSel2 = (calEntry2 != null);
    if (activeTBSSel1 && !activeTBSSel2)
      return -1; 
    if (!activeTBSSel1 && activeTBSSel2)
      return 1; 
    if (calEntry1 == null && calEntry2 == null)
      return compareTitleAndSubConfig(sel1, sel2); 
    if (calEntry1.before(calEntry2))
      return -1; 
    if (calEntry1.after(calEntry2))
      return 1; 
    return compareTitleAndSubConfig(sel1, sel2);
  }
  
  public int compareTitleAndSubConfig(Selection sel1, Selection sel2) {
    String titleEntry1 = sel1.getTitle().toUpperCase();
    String titleEntry2 = sel2.getTitle().toUpperCase();
    if (!titleEntry1.equals(titleEntry2))
      return titleEntry1.compareTo(titleEntry2); 
    SelectionSubConfiguration subconfig1 = sel1.getSelectionSubConfig();
    SelectionSubConfiguration subconfig2 = sel2.getSelectionSubConfig();
    String subconfigAbbreviation1 = "";
    String subconfigAbbreviation2 = "";
    if (subconfig1 != null)
      subconfigAbbreviation1 = (subconfig1
        .getSelectionSubConfigurationAbbreviation() == null) ? 
        "" : subconfig1.getSelectionSubConfigurationAbbreviation(); 
    if (subconfig2 != null)
      subconfigAbbreviation2 = (subconfig2
        .getSelectionSubConfigurationAbbreviation() == null) ? 
        "" : subconfig2.getSelectionSubConfigurationAbbreviation(); 
    return subconfigAbbreviation1.compareTo(subconfigAbbreviation2);
  }
}


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\IDJExceptionsComparator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */