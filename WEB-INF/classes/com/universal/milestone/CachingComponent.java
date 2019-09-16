package WEB-INF.classes.com.universal.milestone;

import com.universal.milestone.JdbcConnector;
import com.universal.milestone.MilestoneHelper;
import java.util.Vector;

abstract class CachingComponent {
  protected static Vector getCachedVectorOfStringsSp(Vector cache, String storedProcedure, String fieldName) {
    if (cache != null)
      return cache; 
    Vector precache = new Vector();
    JdbcConnector connector = MilestoneHelper.createConnector(storedProcedure);
    connector.runQuery();
    while (connector.more()) {
      precache.addElement(connector.getField(fieldName));
      connector.next();
    } 
    connector.close();
    return precache;
  }
  
  protected static Vector getCachedVectorOfStrings(Vector cache, String query, String fieldName) {
    if (cache != null)
      return cache; 
    Vector precache = new Vector();
    JdbcConnector connector = MilestoneHelper.createConnector(query);
    connector.runQuery();
    while (connector.more()) {
      precache.addElement(connector.getField(fieldName));
      connector.next();
    } 
    connector.close();
    return precache;
  }
}


/* Location:              D:\Documents\NetBeansProjects\milestone2 Local.war!\WEB-INF\classes\co\\universal\milestone\CachingComponent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */