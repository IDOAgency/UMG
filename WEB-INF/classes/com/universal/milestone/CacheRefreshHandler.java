package WEB-INF.classes.com.universal.milestone;

import com.techempower.ComponentLog;
import com.techempower.gemini.BasicHandler;
import com.techempower.gemini.Context;
import com.techempower.gemini.Dispatcher;
import com.techempower.gemini.GeminiApplication;
import com.universal.milestone.Cache;
import com.universal.milestone.CacheRefreshHandler;
import com.universal.milestone.MilestoneConstants;

public class CacheRefreshHandler extends BasicHandler implements MilestoneConstants {
  public static final String COMPONENT_CODE = "hcrf";
  
  public GeminiApplication application;
  
  public ComponentLog log;
  
  public CacheRefreshHandler(GeminiApplication application) {
    this.application = application;
    this.log = application.getLog("hcrf");
  }
  
  public String getDescription() { return "Cache Refresh Handler"; }
  
  public boolean acceptRequest(Dispatcher dispatcher, Context context, String command) {
    if (command.startsWith("cache-refresh"))
      return handleRequest(dispatcher, context, command); 
    return false;
  }
  
  public boolean handleRequest(Dispatcher dispatcher, Context context, String command) {
    if (command.equalsIgnoreCase("cache-refresh"))
      RefreshCache(dispatcher, context, command); 
    return true;
  }
  
  private boolean RefreshCache(Dispatcher dispatcher, Context context, String command) {
    String strObject = "";
    if (context.getParameter("cache-refresh-param") != null)
      strObject = context.getParameter("cache-refresh-param"); 
    String strType = "";
    if (context.getParameter("cache-refresh-param-type") != null)
      strType = context.getParameter("cache-refresh-param-type"); 
    String strUserId = "";
    if (context.getParameter("cache-refresh-param-userid") != null)
      strUserId = context.getParameter("cache-refresh-param-userid"); 
    System.out.println("<<< CMD_REFRESH_CACHE_PARAM " + strObject + 
        " REFRESH_CACHE_PARAM_TYPE " + strType + 
        " REFRESH_CACHE_PARAM_USERID " + strUserId);
    if (strObject.equalsIgnoreCase("CS")) {
      Cache.getInstance().flushCorporateStructureLocal();
    } else if (strObject.equalsIgnoreCase("PC")) {
      Cache.getInstance().flushSellCodeLocal();
    } else if (strObject.equalsIgnoreCase("TV")) {
      Cache.getInstance().flushTableVariablesLocal(strType);
    } else if (strObject.equalsIgnoreCase("DP")) {
      Cache.getInstance().flushDatePeriodsLocal();
    } else if (strObject.equalsIgnoreCase("DT")) {
      Cache.getInstance().flushDayTypesLocal();
    } else if (strObject.equalsIgnoreCase("AU")) {
      if (strUserId.equals("")) {
        Cache.flushCachedVariableLocal(Cache.getInstance().getAllUsers());
      } else {
        Cache.getInstance().flushCachedVariableLocalAllUsers(Integer.parseInt(strUserId));
      } 
    } else if (strObject.equalsIgnoreCase("UL")) {
      Cache.getInstance().flushUsedLabelsLocal();
    } 
    return true;
  }
}


/* Location:              D:\Documents\NetBeansProjects\milestone2 Local.war!\WEB-INF\classes\co\\universal\milestone\CacheRefreshHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */