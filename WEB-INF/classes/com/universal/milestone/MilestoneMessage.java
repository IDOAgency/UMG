package WEB-INF.classes.com.universal.milestone;

import com.techempower.BasicHelper;
import com.techempower.ComponentLog;
import com.techempower.EnhancedProperties;
import com.techempower.gemini.GeminiApplication;
import com.universal.milestone.MilestoneConstants;
import com.universal.milestone.MilestoneMessage;
import java.util.Vector;

public class MilestoneMessage extends BasicHelper implements MilestoneConstants {
  public static final String COMPONENT_CODE = "mmsg";
  
  protected static Vector bomSuppliers = null;
  
  protected static String databaseName = "Milestone2";
  
  protected static String dbLoginName = "";
  
  protected static String dbLoginPass = "";
  
  protected static String urlPrefix = "jdbc:odbc:";
  
  protected static ComponentLog log;
  
  public static void configure(EnhancedProperties props, GeminiApplication application) {
    databaseName = props.getProperty("DatabaseName", databaseName);
    dbLoginName = props.getProperty("DatabaseLogin", dbLoginName);
    dbLoginPass = props.getProperty("DatabasePassword", dbLoginPass);
    urlPrefix = props.getProperty("JDBCURLPrefix", urlPrefix);
    log = application.getLog("mmsg");
  }
  
  public static String getMessage(int pId) { return getMessage(pId, new String[0]); }
  
  public static String getMessage(int pId, String pParameter) { return getMessage(pId, new String[] { pParameter }); }
  
  public static String getMessage(int pId, String[] pParameters) {
    if (pId < 0 || pId >= ssMessages.length)
      return getMessage(0, new String[] { pId }); 
    String lsMessage = ssMessages[pId];
    for (int i = 0; i < pParameters.length; i++) {
      if (pParameters[i] == null)
        pParameters[i] = ""; 
      String lsPlaceholder = "~%" + i + "~";
      int liIndex = -1;
      while ((liIndex = lsMessage.indexOf(lsPlaceholder)) >= 0)
        lsMessage = String.valueOf(lsMessage.substring(0, liIndex)) + pParameters[i] + ((lsMessage.length() > liIndex + lsPlaceholder.length() + 1) ? 
          lsMessage.substring(liIndex + lsPlaceholder.length()) : 
          ""); 
    } 
    int liIndex = lsMessage.indexOf("~%");
    while (liIndex >= 0) {
      int liEndIndex = lsMessage.indexOf('~', liIndex + 1);
      if (liEndIndex >= 0 && liEndIndex + 1 < lsMessage.length()) {
        lsMessage = String.valueOf(lsMessage.substring(0, liIndex)) + "''" + 
          lsMessage.substring(liEndIndex + 1);
      } else {
        lsMessage = String.valueOf(lsMessage.substring(0, liIndex)) + "''";
      } 
      liIndex = lsMessage.indexOf("~%");
    } 
    return lsMessage;
  }
}


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\MilestoneMessage.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */