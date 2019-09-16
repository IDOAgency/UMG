package WEB-INF.classes.com.universal.milestone;

import com.universal.milestone.Selection;
import java.util.Comparator;

public class SelectionSelectionNumberComparator implements Comparator {
  public int compare(Object o1, Object o2) {
    try {
      Selection sel1 = (Selection)o1;
      Selection sel2 = (Selection)o2;
      String prefix1 = "";
      String prefix2 = "";
      if (sel1.getPrefixID() != null)
        prefix1 = sel1.getPrefixID().getName(); 
      if (sel2.getPrefixID() != null)
        prefix2 = sel1.getPrefixID().getName(); 
      String selNo1 = String.valueOf(prefix1.trim()) + sel1.getSelectionNo().trim();
      String selNo2 = String.valueOf(prefix2.trim()) + sel2.getSelectionNo().trim();
      return selNo1.compareTo(selNo2);
    } catch (Exception e) {
      return 0;
    } 
  }
}


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\SelectionSelectionNumberComparator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */