package WEB-INF.classes.com.universal.milestone;

import java.util.Comparator;

public class StringReverseComparator implements Comparator {
  public int compare(Object o1, Object o2) {
    String string1 = ((String)o1).toUpperCase();
    String string2 = ((String)o2).toUpperCase();
    return -1 * string1.compareTo(string2);
  }
}


/* Location:              D:\Documents\NetBeansProjects\milestone2 Local.war!\WEB-INF\classes\co\\universal\milestone\StringReverseComparator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */