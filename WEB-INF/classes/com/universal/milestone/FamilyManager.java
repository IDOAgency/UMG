package WEB-INF.classes.com.universal.milestone;

import com.techempower.ComponentLog;
import com.techempower.EnhancedProperties;
import com.techempower.gemini.GeminiApplication;
import com.universal.milestone.Cache;
import com.universal.milestone.CorporateObjectSearchObj;
import com.universal.milestone.Family;
import com.universal.milestone.FamilyManager;
import com.universal.milestone.JdbcConnector;
import com.universal.milestone.MilestoneConstants;
import com.universal.milestone.MilestoneHelper;
import com.universal.milestone.Notepad;
import java.util.Vector;

public class FamilyManager implements MilestoneConstants {
  public static final String COMPONENT_CODE = "mFam";
  
  protected static FamilyManager familyManager = null;
  
  protected static ComponentLog log;
  
  public static void configure(EnhancedProperties props, GeminiApplication application) { log = application.getLog("mFam"); }
  
  public static FamilyManager getInstance() {
    if (familyManager == null)
      familyManager = new FamilyManager(); 
    return familyManager;
  }
  
  public Family getFamily(int id) {
    Family family = null;
    family = (Family)MilestoneHelper.getStructureObject(id);
    String query = "SELECT last_updated_by, last_updated_on, last_updated_ck FROM vi_Structure  WHERE type = 1 AND structure_id = " + 
      
      id + 
      " ORDER BY name;";
    JdbcConnector connector = MilestoneHelper.getConnector(query);
    connector.runQuery();
    if (connector.more()) {
      String lastDateString = connector.getFieldByName("last_updated_on");
      if (lastDateString != null)
        family.setLastUpdateDate(MilestoneHelper.getDatabaseDate(lastDateString)); 
      family.setLastUpdatingUser(connector.getIntegerField("last_updated_by"));
      long lastUpdatedLong = Long.parseLong(connector.getField("last_updated_ck"), 16);
      family.setLastUpdatedCk(lastUpdatedLong);
    } 
    connector.close();
    return family;
  }
  
  public Family saveFamily(Family family, int userID) {
    long timestamp = family.getLastUpdatedCk();
    String activeFlag = "0";
    boolean boolVal = family.getActive();
    if (!boolVal)
      activeFlag = "1"; 
    String query = "sp_sav_Structure " + 
      family.getStructureID() + "," + 
      "0" + "," + 
      activeFlag + "," + 
      '\001' + "," + 
      "'" + MilestoneHelper.escapeSingleQuotes(family.getName()) + "'," + 
      "'" + MilestoneHelper.escapeSingleQuotes(family.getStructureAbbreviation()) + "'," + 
      "0" + "," + 
      "0" + "," + 
      userID + "," + 
      timestamp;
    log.debug("Update family query:\n" + query);
    JdbcConnector connector = MilestoneHelper.getConnector(query);
    connector.runQuery();
    family.flushAudits(userID);
    if (family.getStructureID() < 1)
      family.setStructureID(connector.getIntegerField("ReturnId")); 
    connector.close();
    String timestampQuery = "SELECT last_updated_ck, last_updated_on FROM vi_structure WHERE structure_id = " + 
      
      family.getStructureID() + 
      ";";
    JdbcConnector connectorTimestamp = MilestoneHelper.getConnector(timestampQuery);
    connectorTimestamp.runQuery();
    if (connectorTimestamp.more()) {
      family.setLastUpdatedCk(Long.parseLong(connectorTimestamp.getField("last_updated_ck"), 16));
      family.setLastUpdateDate(MilestoneHelper.getDatabaseDate(connectorTimestamp.getField("last_updated_on")));
    } 
    connectorTimestamp.close();
    return family;
  }
  
  public void deleteFamily(Family family, int userID) {
    String query = "sp_del_Structure " + 
      family.getStructureID();
    log.debug("Delete query:\n" + query);
    JdbcConnector connector = MilestoneHelper.getConnector(query);
    connector.runQuery();
    connector.close();
    Cache.getInstance().flushCorporateStructure();
  }
  
  public void setNotepadQuery(Notepad notepad) {
    if (notepad != null)
      if (notepad.getCorporateObjectSearchObj() != null) {
        CorporateObjectSearchObj corpObject = notepad.getCorporateObjectSearchObj();
        Vector contents = new Vector();
        String descriptionSearch = corpObject.getFamilySearch();
        Vector corporateObjects = Cache.getFamilies();
        contents = MilestoneHelper.filterCorporateByDescription(corporateObjects, descriptionSearch);
        contents = MilestoneHelper.sortCorporateVectorByName(contents);
        notepad.setAllContents(contents);
      }  
  }
  
  public Vector getFamilyNotepadList(int UserId, Notepad notepad) {
    if (notepad != null && 
      notepad.getCorporateObjectSearchObj() != null) {
      setNotepadQuery(notepad);
      return MilestoneHelper.sortCorporateVectorByName(notepad.getAllContents());
    } 
    return MilestoneHelper.sortCorporateVectorByName(Cache.getFamilies());
  }
  
  public boolean isTimestampValid(Family family) {
    if (family != null) {
      String timestampQuery = "SELECT last_updated_ck, last_updated_on FROM vi_structure WHERE structure_id = " + 
        
        family.getStructureID() + 
        ";";
      JdbcConnector connectorTimestamp = MilestoneHelper.getConnector(timestampQuery);
      connectorTimestamp.runQuery();
      if (connectorTimestamp.more())
        if (family.getLastUpdatedCk() != Long.parseLong(connectorTimestamp.getField("last_updated_ck", ""), 16)) {
          connectorTimestamp.close();
          return false;
        }  
      connectorTimestamp.close();
      return true;
    } 
    return false;
  }
}


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\FamilyManager.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */