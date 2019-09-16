package WEB-INF.classes.com.universal.milestone;

import com.universal.milestone.Selection;
import java.util.Comparator;

public class SelectionArtistComparator implements Comparator {
  public int compare(Object o1, Object o2) {
    try {
      Selection sel1 = (Selection)o1;
      Selection sel2 = (Selection)o2;
      String artist1 = sel1.getArtist().trim();
      String artist2 = sel2.getArtist().trim();
      return artist1.compareTo(artist2);
    } catch (Exception e) {
      return 0;
    } 
  }
}


/* Location:              D:\Documents\NetBeansProjects\milestone2 Local.war!\WEB-INF\classes\co\\universal\milestone\SelectionArtistComparator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */