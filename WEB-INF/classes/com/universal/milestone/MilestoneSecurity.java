package WEB-INF.classes.com.universal.milestone;

import com.techempower.ComponentLog;
import com.techempower.EnhancedProperties;
import com.techempower.gemini.Context;
import com.techempower.gemini.GeminiApplication;
import com.universal.milestone.MilestoneConstants;
import com.universal.milestone.MilestoneHelper;
import com.universal.milestone.MilestoneSecurity;
import com.universal.milestone.User;
import com.universal.milestone.UserCompaniesTableManager;
import com.universal.milestone.UserManager;
import java.util.Vector;

public class MilestoneSecurity implements MilestoneConstants {
  public static final String COMPONENT_CODE = "secu";
  
  public static final String deptFilterFormat = "department.filter.";
  
  protected static ComponentLog log;
  
  public static void configure(EnhancedProperties props, GeminiApplication application) { log = application.getLog("secu"); }
  
  public static User processLogin(String username, String password, Context context) {
    User user = UserManager.getInstance().getUser(username, password);
    if (user != null) {
      context.putSessionValue("user", user);
      String[] filter = MilestoneHelper.parseFilter(user.getFilter());
      context.putSessionValue("filterFlag", filter[0]);
      context.putSessionValue("filter", filter[1]);
      String deptFilter = user.getDeptFilter();
      context.putSessionValue("deptFilterFlag", "Yes");
      try {
        if (deptFilter.substring(0, 4).equalsIgnoreCase("true")) {
          context.putSessionValue("deptFilterFlag", "Yes");
        } else {
          context.putSessionValue("deptFilterFlag", "No");
        } 
      } catch (Exception exception) {}
      String selDepartment = "All";
      if (deptFilter != null) {
        int sel = deptFilter.indexOf("department.filter.");
        if (sel != -1)
          try {
            selDepartment = deptFilter.substring(sel + "department.filter.".length());
          } catch (Exception exception) {} 
      } 
      context.putSessionValue("deptFilter", selDepartment);
    } 
    return user;
  }
  
  public static void processLogout(Context context) { context.removeAllSessionValues(); }
  
  public static User getUser(Context context) { return (User)context.getSessionValue("user"); }
  
  public static boolean isUserLoggedIn(Context context, String command) {
    if (context.getSessionValue("user") != null) {
      if (!command.equalsIgnoreCase("login") && 
        !command.equalsIgnoreCase("logoff") && 
        !command.equalsIgnoreCase("top-navigation") && 
        !command.equalsIgnoreCase("main-top-menu") && 
        !command.equalsIgnoreCase("home") && 
        command.length() > 0)
        context.putSessionValue("lastLink", command); 
      User user = (User)context.getSessionValue("user");
      int userId = user.getUserId();
      if (UserCompaniesTableManager.getInstance().isUpdated(userId)) {
        Vector companies = (Vector)context.getSessionValue("user-companies");
        if (companies != null)
          companies.removeAllElements(); 
        user = UserManager.getInstance().getUser(userId, true);
        context.putSessionValue("user", user);
        UserCompaniesTableManager.getInstance().setUpdateFlag(userId, false);
      } 
    } 
    return (context.getSessionValue("user") != null);
  }
}


/* Location:              D:\Documents\NetBeansProjects\milestone2 Local.war!\WEB-INF\classes\co\\universal\milestone\MilestoneSecurity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */