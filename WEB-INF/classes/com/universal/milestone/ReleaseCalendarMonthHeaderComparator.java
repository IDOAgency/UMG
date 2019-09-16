package WEB-INF.classes.com.universal.milestone;

import com.universal.milestone.ReleaseCalendarMonthHeaderComparator;
import java.util.Calendar;
import java.util.Comparator;
import java.util.GregorianCalendar;

public class ReleaseCalendarMonthHeaderComparator implements Comparator {
  private int getMonth(String month) {
    int monthIndex = 0;
    if (month.equalsIgnoreCase("January"))
      monthIndex = 0; 
    if (month.equalsIgnoreCase("February"))
      monthIndex = 1; 
    if (month.equalsIgnoreCase("March"))
      monthIndex = 2; 
    if (month.equalsIgnoreCase("April"))
      monthIndex = 3; 
    if (month.equalsIgnoreCase("May"))
      monthIndex = 4; 
    if (month.equalsIgnoreCase("June"))
      monthIndex = 5; 
    if (month.equalsIgnoreCase("July"))
      monthIndex = 6; 
    if (month.equalsIgnoreCase("August"))
      monthIndex = 7; 
    if (month.equalsIgnoreCase("September"))
      monthIndex = 8; 
    if (month.equalsIgnoreCase("October"))
      monthIndex = 9; 
    if (month.equalsIgnoreCase("November"))
      monthIndex = 10; 
    if (month.equalsIgnoreCase("December"))
      monthIndex = 11; 
    return monthIndex;
  }
  
  public int compare(Object o1, Object o2) {
    String cso1 = (String)o1;
    String cso2 = (String)o2;
    Integer cso1Year = Integer.valueOf(cso1.substring(cso1.indexOf("-") + 1));
    Integer cso2Year = Integer.valueOf(cso2.substring(cso2.indexOf("-") + 1));
    String cso1Month = cso1.substring(0, cso1.indexOf("-"));
    String cso2Month = cso2.substring(0, cso2.indexOf("-"));
    Calendar cso1Cal = new GregorianCalendar(cso1Year.intValue(), getMonth(cso1Month), 1);
    Calendar cso2Cal = new GregorianCalendar(cso2Year.intValue(), getMonth(cso2Month), 1);
    return cso1Cal.getTime().compareTo(cso2Cal.getTime());
  }
}


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\ReleaseCalendarMonthHeaderComparator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */