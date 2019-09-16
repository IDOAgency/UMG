package WEB-INF.classes.com.universal.milestone;

import com.universal.milestone.MilestoneHashtable;
import java.util.Enumeration;
import java.util.Hashtable;

public class MilestoneHashtable extends Hashtable {
  public boolean containsKey(String key) {
    boolean result = false;
    if (key != null)
      for (Enumeration strKeys = keys(); strKeys.hasMoreElements(); ) {
        String strKey = (String)strKeys.nextElement();
        if (strKey != null && strKey.equalsIgnoreCase(key))
          return true; 
      }  
    return result;
  }
  
  public Object get(String key) {
    Object result = null;
    if (key != null)
      for (Enumeration strKeys = keys(); strKeys.hasMoreElements(); ) {
        String strKey = (String)strKeys.nextElement();
        if (strKey != null && strKey.equalsIgnoreCase(key))
          return get(strKey); 
      }  
    return result;
  }
  
  public Object put(String key, String value) {
    for (Enumeration strKeys = keys(); strKeys.hasMoreElements(); ) {
      String strKey = (String)strKeys.nextElement();
      if (strKey.equalsIgnoreCase(key))
        remove(strKey); 
    } 
    return put(key, value);
  }
}


/* Location:              D:\Documents\NetBeansProjects\milestone2 Local.war!\WEB-INF\classes\co\\universal\milestone\MilestoneHashtable.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */