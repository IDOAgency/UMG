package WEB-INF.classes.com.universal.milestone;

import com.universal.milestone.MilestoneHelper;
import com.universal.milestone.ProjectSearch;
import com.universal.milestone.ProjectSearchComparator;
import java.util.Calendar;
import java.util.Comparator;

public class ProjectSearchComparator implements Comparator {
  public int sortingColumn;
  
  public String sortingdirection;
  
  public ProjectSearchComparator(int sortColumn, String direction) {
    this.sortingColumn = sortColumn;
    this.sortingdirection = direction;
  }
  
  public int compare(Object o1, Object o2) {
    ProjectSearch proj1 = (ProjectSearch)o1;
    ProjectSearch proj2 = (ProjectSearch)o2;
    try {
      switch (this.sortingColumn) {
        case 0:
          return sortStringColumn(proj1.getRMSProjectNo().toUpperCase().trim(), proj2.getRMSProjectNo().toUpperCase().trim());
        case 1:
          return sortStringColumn(proj1.getArtistFirstName().toUpperCase().trim(), proj2.getArtistFirstName().toUpperCase().trim());
        case 2:
          return sortStringColumn(proj1.getArtistLastName().toUpperCase().trim(), proj2.getArtistLastName().toUpperCase().trim());
        case 3:
          return sortStringColumn(proj1.getProjectDesc().toUpperCase().trim(), proj2.getProjectDesc().toUpperCase().trim());
        case 4:
          return sortStringColumn(proj1.getTitle().toUpperCase().trim(), proj2.getTitle().toUpperCase().trim());
        case 5:
          return sortStringColumn(MilestoneHelper.getStructureName(proj1.getMSLabelId()), MilestoneHelper.getStructureName(proj2.getMSLabelId()));
        case 6:
          return sortCreateDate(proj1.getCreateDate(), proj2.getCreateDate());
      } 
      return sortCreateDate(proj1.getCreateDate(), proj2.getCreateDate());
    } catch (Exception e) {
      System.out.println("Exception raised in Project Search Comparator");
      return 0;
    } 
  }
  
  public int sortIntegerColumn(String proj1String, String proj2String) {
    try {
      int proj1int = Integer.parseInt(proj1String);
      int proj2int = Integer.parseInt(proj2String);
      if (this.sortingdirection.equalsIgnoreCase("Ascending"))
        return (new Integer(proj1int)).compareTo(new Integer(proj2int)); 
      return (new Integer(proj2int)).compareTo(new Integer(proj1int));
    } catch (Exception e) {
      System.out.println("Exception raised in Project Search Comparator sortIntegerColumn()");
      return 0;
    } 
  }
  
  public int sortStringColumn(String proj1String, String proj2String) {
    try {
      if (this.sortingdirection.equalsIgnoreCase("Ascending"))
        return proj1String.compareTo(proj2String); 
      return proj2String.compareTo(proj1String);
    } catch (Exception e) {
      System.out.println("Exception raised in Project Search Comparator sortStringColumn()");
      return 0;
    } 
  }
  
  public int sortCreateDate(Calendar proj1Cal, Calendar proj2Cal) {
    try {
      if (proj1Cal == null && proj2Cal == null)
        return 0; 
      if (proj1Cal == null)
        return 1; 
      if (proj2Cal == null)
        return -1; 
      if (this.sortingdirection.equalsIgnoreCase("Ascending")) {
        if (proj1Cal.before(proj2Cal))
          return -1; 
        if (proj1Cal.after(proj2Cal))
          return 1; 
        return 0;
      } 
      if (proj2Cal.before(proj1Cal))
        return -1; 
      if (proj2Cal.after(proj1Cal))
        return 1; 
      return 0;
    } catch (Exception e) {
      System.out.println("Exception raised in Project Search Comparator sortCreateDate()");
      return 0;
    } 
  }
}


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\ProjectSearchComparator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */