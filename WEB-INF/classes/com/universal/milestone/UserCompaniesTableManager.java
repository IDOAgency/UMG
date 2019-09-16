package WEB-INF.classes.com.universal.milestone;

import com.techempower.ComponentLog;
import com.techempower.EnhancedProperties;
import com.techempower.gemini.GeminiApplication;
import com.universal.milestone.Cache;
import com.universal.milestone.MilestoneConstants;
import com.universal.milestone.UserCompaniesTableManager;
import java.util.Hashtable;

public class UserCompaniesTableManager implements MilestoneConstants {
  public static final String COMPONENT_CODE = "mUCT";
  
  protected static UserCompaniesTableManager userCompaniesTableManager = null;
  
  protected static ComponentLog log;
  
  public static void configure(EnhancedProperties props, GeminiApplication application) { log = application.getLog("mUCT"); }
  
  public static UserCompaniesTableManager getInstance() {
    if (userCompaniesTableManager == null)
      userCompaniesTableManager = new UserCompaniesTableManager(); 
    return userCompaniesTableManager;
  }
  
  public static void setUpdateFlag(int userId, boolean isUpdated) {
    Hashtable userCompanyTable = Cache.getUserCompaniesTable();
    String userKey = Integer.toString(userId);
    userCompanyTable.put(userKey, new Boolean(isUpdated));
  }
  
  public static boolean isUpdated(int userId) {
    boolean isUpdated = false;
    Hashtable userCompanyTable = Cache.getUserCompaniesTable();
    String userKey = Integer.toString(userId);
    if (userCompanyTable.get(userKey) != null)
      isUpdated = ((Boolean)userCompanyTable.get(userKey)).booleanValue(); 
    return isUpdated;
  }
}


/* Location:              D:\Documents\NetBeansProjects\milestone2 Local.war!\WEB-INF\classes\co\\universal\milestone\UserCompaniesTableManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */