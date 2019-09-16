package WEB-INF.classes.com.universal.milestone;

import com.universal.milestone.MilestoneVector;
import java.util.Iterator;
import java.util.Vector;

public class MilestoneVector extends Vector {
  public boolean contains(String key) {
    boolean result = false;
    if (key != null)
      for (Iterator strKeys = iterator(); strKeys.hasNext(); ) {
        String strKey = (String)strKeys.next();
        if (strKey != null && strKey.equalsIgnoreCase(key))
          return true; 
      }  
    return result;
  }
  
  public int indexOf(String key) {
    int result = -1;
    if (key != null) {
      Iterator strKeys = iterator();
      for (int i = 0; strKeys.hasNext(); i++) {
        String strKey = (String)strKeys.next();
        if (strKey != null && strKey.equalsIgnoreCase(key))
          return i; 
      } 
    } 
    return result;
  }
}


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\MilestoneVector.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */