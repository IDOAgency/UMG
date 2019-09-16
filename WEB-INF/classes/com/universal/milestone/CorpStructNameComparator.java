package WEB-INF.classes.com.universal.milestone;

import com.universal.milestone.CorporateStructureObject;
import java.util.Comparator;

public class CorpStructNameComparator implements Comparator {
  public int compare(Object o1, Object o2) {
    CorporateStructureObject cso1 = (CorporateStructureObject)o1;
    CorporateStructureObject cso2 = (CorporateStructureObject)o2;
    String cso1Name = cso1.getName().toUpperCase();
    String cso2Name = cso2.getName().toUpperCase();
    return cso1Name.compareTo(cso2Name);
  }
}


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\CorpStructNameComparator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */