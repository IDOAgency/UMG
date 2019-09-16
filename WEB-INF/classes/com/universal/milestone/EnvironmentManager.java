package WEB-INF.classes.com.universal.milestone;

import com.techempower.ComponentLog;
import com.techempower.EnhancedProperties;
import com.techempower.gemini.GeminiApplication;
import com.universal.milestone.Cache;
import com.universal.milestone.CorporateObjectSearchObj;
import com.universal.milestone.Environment;
import com.universal.milestone.EnvironmentManager;
import com.universal.milestone.Family;
import com.universal.milestone.JdbcConnector;
import com.universal.milestone.MilestoneConstants;
import com.universal.milestone.MilestoneHelper;
import com.universal.milestone.Notepad;
import java.util.Vector;

public class EnvironmentManager implements MilestoneConstants {
  public static final String COMPONENT_CODE = "mEnv";
  
  protected static EnvironmentManager environmentManager = null;
  
  protected static ComponentLog log;
  
  public static void configure(EnhancedProperties props, GeminiApplication application) { log = application.getLog("mEnv"); }
  
  public static EnvironmentManager getInstance() {
    if (environmentManager == null)
      environmentManager = new EnvironmentManager(); 
    return environmentManager;
  }
  
  public Environment getEnvironment(int id) {
    Environment environment = null;
    environment = (Environment)MilestoneHelper.getStructureObject(id);
    String query = "SELECT last_updated_by, last_updated_on, last_updated_ck FROM vi_Structure WHERE type = 5 AND structure_id = " + 
      
      id + 
      " ORDER BY name;";
    JdbcConnector connector = MilestoneHelper.getConnector(query);
    connector.runQuery();
    if (connector.more()) {
      String lastDateString = connector.getFieldByName("last_updated_on");
      if (lastDateString != null)
        environment.setLastUpdateDate(MilestoneHelper.getDatabaseDate(lastDateString)); 
      environment.setLastUpdatingUser(connector.getIntegerField("last_updated_by"));
      long lastUpdatedLong = Long.parseLong(connector.getField("last_updated_ck"), 16);
      environment.setLastUpdatedCk(lastUpdatedLong);
    } 
    connector.close();
    return environment;
  }
  
  public Environment saveEnvironment(Environment environment, int userID) {
    long timestamp = environment.getLastUpdatedCk();
    String activeFlag = "0";
    boolean boolVal = environment.getActive();
    if (!boolVal)
      activeFlag = "1"; 
    String query = "sp_sav_Structure " + 
      environment.getStructureID() + "," + 
      environment.getParentFamily().getStructureID() + "," + 
      activeFlag + "," + 
      '\005' + "," + 
      "'" + MilestoneHelper.escapeSingleQuotes(environment.getName()) + "'," + 
      "'" + MilestoneHelper.escapeSingleQuotes(environment.getStructureAbbreviation()) + "'," + 
      environment.getDistribution() + "," + 
      environment.getCalendarGroup() + "," + 
      userID + "," + 
      timestamp + 
      ";";
    JdbcConnector connector = MilestoneHelper.getConnector(query);
    connector.runQuery();
    connector.close();
    environment.flushAudits(userID);
    String timestampQuery = "SELECT last_updated_ck, last_updated_on FROM vi_structure WHERE structure_id = " + 
      
      environment.getStructureID() + 
      ";";
    JdbcConnector connectorTimestamp = MilestoneHelper.getConnector(timestampQuery);
    connectorTimestamp.runQuery();
    if (connectorTimestamp.more()) {
      environment.setLastUpdatedCk(Long.parseLong(connectorTimestamp.getField("last_updated_ck"), 16));
      environment.setLastUpdateDate(MilestoneHelper.getDatabaseDate(connectorTimestamp.getField("last_updated_on")));
    } 
    connectorTimestamp.close();
    return environment;
  }
  
  public Environment saveNewEnvironment(Environment environment, int userID) {
    long timestamp = environment.getLastUpdatedCk();
    environment.flushAudits(userID);
    String query = "sp_sav_Structure " + 
      environment.getStructureID() + "," + 
      environment.getParentFamily().getStructureID() + "," + 
      "0" + "," + 
      '\005' + "," + 
      "'" + MilestoneHelper.escapeSingleQuotes(environment.getName()) + "'," + 
      "'" + MilestoneHelper.escapeSingleQuotes(environment.getStructureAbbreviation()) + "'," + 
      "0" + "," + 
      "0" + "," + 
      userID + "," + 
      timestamp + 
      ";";
    JdbcConnector connector = MilestoneHelper.getConnector(query);
    connector.runQuery();
    if (environment.isNew())
      environment.setStructureID(connector.getIntegerField("ReturnId")); 
    String timestampQuery = "SELECT last_updated_ck, last_updated_on FROM vi_structure WHERE structure_id = " + 
      
      environment.getStructureID() + 
      ";";
    JdbcConnector connectorTimestamp = MilestoneHelper.getConnector(timestampQuery);
    connectorTimestamp.runQuery();
    if (connectorTimestamp.more()) {
      environment.setLastUpdatedCk(Long.parseLong(connectorTimestamp.getField("last_updated_ck"), 16));
      environment.setLastUpdateDate(MilestoneHelper.getDatabaseDate(connectorTimestamp.getField("last_updated_on")));
    } 
    connectorTimestamp.close();
    connector.close();
    return environment;
  }
  
  public void deleteEnvironment(Environment environment, int userID) {
    String query = "sp_del_Structure " + 
      environment.getStructureID();
    JdbcConnector connector = MilestoneHelper.getConnector(query);
    connector.runQuery();
    connector.close();
  }
  
  public void setNotepadQuery(Notepad notepad) {
    if (notepad != null)
      if (notepad.getCorporateObjectSearchObj() != null) {
        CorporateObjectSearchObj corpObject = notepad.getCorporateObjectSearchObj();
        Vector contents = new Vector();
        String descriptionSearch = corpObject.getFamilySearch();
        String environmentDescriptionSearch = corpObject.getEnvironmentSearch();
        Vector corporateObjects = Cache.getFamilies();
        contents = MilestoneHelper.filterCorporateByDescription(corporateObjects, descriptionSearch);
        contents = MilestoneHelper.filterCorporateByDescription(Cache.getFamilies(), descriptionSearch);
        contents = getEnvironmentsFromFamilies(contents);
        contents = MilestoneHelper.filterCorporateByDescription(contents, environmentDescriptionSearch);
        notepad.setAllContents(MilestoneHelper.sortCorporateVectorByName(contents));
      }  
  }
  
  public Vector getEnvironmentNotepadList(int UserId, Notepad notepad) {
    if (notepad != null && 
      notepad.getCorporateObjectSearchObj() != null) {
      setNotepadQuery(notepad);
      return MilestoneHelper.sortCorporateVectorByName(notepad.getAllContents());
    } 
    return MilestoneHelper.sortCorporateVectorByName(Cache.getEnvironments());
  }
  
  public static Vector getEnvironmentsFromFamilies(Vector families) {
    Vector preCache = new Vector();
    for (int i = 0; i < families.size(); i++) {
      Family family = (Family)families.get(i);
      Vector environments = family.getEnvironments();
      for (int j = 0; j < environments.size(); j++)
        preCache.add(environments.get(j)); 
    } 
    return preCache;
  }
  
  public boolean isTimestampValid(Environment environment) {
    if (environment != null) {
      String timestampQuery = "SELECT last_updated_ck, last_updated_on FROM vi_structure WHERE structure_id = " + 
        
        environment.getStructureID() + 
        ";";
      JdbcConnector connectorTimestamp = MilestoneHelper.getConnector(timestampQuery);
      connectorTimestamp.runQuery();
      if (connectorTimestamp.more())
        if (environment.getLastUpdatedCk() != Long.parseLong(connectorTimestamp.getField("last_updated_ck", ""), 16)) {
          connectorTimestamp.close();
          return false;
        }  
      connectorTimestamp.close();
      return true;
    } 
    return false;
  }
}


/* Location:              D:\Documents\NetBeansProjects\milestone2 Local.war!\WEB-INF\classes\co\\universal\milestone\EnvironmentManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */