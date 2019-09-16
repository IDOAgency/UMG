package WEB-INF.classes.com.universal.milestone;

import com.universal.milestone.CorporateStructureObject;
import java.util.Comparator;

public class CorpStructParentNameComparator implements Comparator {
  public int compare(Object o1, Object o2) {
    int result = -1;
    CorporateStructureObject cso1 = (CorporateStructureObject)o1;
    CorporateStructureObject cso2 = (CorporateStructureObject)o2;
    try {
      CorporateStructureObject parent1 = cso1.getParent();
      CorporateStructureObject parent2 = cso2.getParent();
      String cso1Name = parent1.getName().toUpperCase();
      String cso2Name = parent2.getName().toUpperCase();
      result = cso1Name.compareTo(cso2Name);
    } catch (NullPointerException nullPointerException) {}
    return result;
  }
}


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\CorpStructParentNameComparator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */