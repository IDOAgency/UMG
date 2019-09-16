package WEB-INF.classes.com.universal.milestone;

import com.techempower.ComponentLog;
import com.techempower.EnhancedProperties;
import com.techempower.gemini.GeminiApplication;
import com.universal.milestone.Cache;
import com.universal.milestone.MilestoneConstants;
import com.universal.milestone.UserEnvironmentsTableManager;
import java.util.Hashtable;

public class UserEnvironmentsTableManager implements MilestoneConstants {
  public static final String COMPONENT_CODE = "mUET";
  
  protected static UserEnvironmentsTableManager userEnvironmentsTableManager = null;
  
  protected static ComponentLog log;
  
  public static void configure(EnhancedProperties props, GeminiApplication application) { log = application.getLog("mUET"); }
  
  public static UserEnvironmentsTableManager getInstance() {
    if (userEnvironmentsTableManager == null)
      userEnvironmentsTableManager = new UserEnvironmentsTableManager(); 
    return userEnvironmentsTableManager;
  }
  
  public static void setUpdateFlag(int userId, boolean isUpdated) {
    Hashtable userEnvironmentTable = Cache.getUserEnvironmentsTable();
    String userKey = Integer.toString(userId);
    userEnvironmentTable.put(userKey, new Boolean(isUpdated));
  }
  
  public static boolean isUpdated(int userId) {
    boolean isUpdated = false;
    Hashtable userEnvironmentTable = Cache.getUserEnvironmentsTable();
    String userKey = Integer.toString(userId);
    if (userEnvironmentTable.get(userKey) != null)
      isUpdated = ((Boolean)userEnvironmentTable.get(userKey)).booleanValue(); 
    return isUpdated;
  }
}


/* Location:              D:\Documents\NetBeansProjects\milestone2 Local.war!\WEB-INF\classes\co\\universal\milestone\UserEnvironmentsTableManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */