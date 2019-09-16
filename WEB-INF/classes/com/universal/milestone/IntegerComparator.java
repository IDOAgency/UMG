package WEB-INF.classes.com.universal.milestone;

import java.util.Comparator;

public class IntegerComparator implements Comparator {
  public int compare(Object o1, Object o2) {
    Integer integer1 = new Integer((String)o1);
    Integer integer2 = new Integer((String)o2);
    return integer1.compareTo(integer2);
  }
}


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\IntegerComparator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */