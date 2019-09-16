package WEB-INF.classes.com.universal.milestone;

import com.techempower.BasicHelper;
import com.techempower.ComponentLog;
import com.techempower.EnhancedProperties;
import com.techempower.gemini.GeminiApplication;
import com.universal.milestone.CorporateStructureHelper;
import com.universal.milestone.MilestoneConstants;

public class CorporateStructureHelper extends BasicHelper implements MilestoneConstants {
  public static final String COMPONENT_CODE = "chlp";
  
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
    log = application.getLog("chlp");
  }
}


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\CorporateStructureHelper.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */