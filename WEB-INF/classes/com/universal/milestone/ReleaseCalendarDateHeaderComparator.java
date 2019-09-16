package WEB-INF.classes.com.universal.milestone;

import java.util.Comparator;

public class ReleaseCalendarDateHeaderComparator implements Comparator {
  public int compare(Object o1, Object o2) {
    String cso1 = (String)o1;
    String cso2 = (String)o2;
    Integer cso1Date = Integer.valueOf(cso1.substring(0, cso1.indexOf("-")));
    Integer cso2Date = Integer.valueOf(cso2.substring(0, cso2.indexOf("-")));
    return cso1Date.compareTo(cso2Date);
  }
}


/* Location:              D:\Documents\NetBeansProjects\milestone2 Local.war!\WEB-INF\classes\co\\universal\milestone\ReleaseCalendarDateHeaderComparator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */