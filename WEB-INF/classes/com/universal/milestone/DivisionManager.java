package WEB-INF.classes.com.universal.milestone;

import com.techempower.ComponentLog;
import com.techempower.EnhancedProperties;
import com.techempower.gemini.GeminiApplication;
import com.universal.milestone.Cache;
import com.universal.milestone.Company;
import com.universal.milestone.CorporateObjectSearchObj;
import com.universal.milestone.Division;
import com.universal.milestone.DivisionManager;
import com.universal.milestone.Environment;
import com.universal.milestone.Family;
import com.universal.milestone.JdbcConnector;
import com.universal.milestone.MilestoneConstants;
import com.universal.milestone.MilestoneHelper;
import com.universal.milestone.Notepad;
import java.util.Vector;

public class DivisionManager implements MilestoneConstants {
  public static final String COMPONENT_CODE = "mDiv";
  
  protected static DivisionManager divisionManager = null;
  
  protected static ComponentLog log;
  
  public static void configure(EnhancedProperties props, GeminiApplication application) { log = application.getLog("mDiv"); }
  
  public static DivisionManager getInstance() {
    if (divisionManager == null)
      divisionManager = new DivisionManager(); 
    return divisionManager;
  }
  
  public Division getDivision(int id) {
    Division division = null;
    division = (Division)MilestoneHelper.getStructureObject(id);
    String query = "SELECT last_updated_by, last_updated_on, last_updated_ck FROM vi_Structure WHERE type = 3 AND structure_id = " + 
      
      id + 
      " ORDER BY name;";
    JdbcConnector connector = MilestoneHelper.getConnector(query);
    connector.runQuery();
    if (connector.more()) {
      String lastDateString = connector.getFieldByName("last_updated_on");
      if (lastDateString != null)
        division.setLastUpdateDate(MilestoneHelper.getDatabaseDate(lastDateString)); 
      division.setLastUpdatingUser(connector.getIntegerField("last_updated_by"));
      long lastUpdatedLong = Long.parseLong(connector.getField("last_updated_ck"), 16);
      division.setLastUpdatedCk(lastUpdatedLong);
    } 
    connector.close();
    return division;
  }
  
  public Division saveDivision(Division division, int userID) {
    long timestamp = division.getLastUpdatedCk();
    int structId = division.getStructureID();
    String activeFlag = "0";
    boolean boolVal = division.getActive();
    if (!boolVal)
      activeFlag = "1"; 
    String query = "sp_sav_Structure " + 
      division.getStructureID() + "," + 
      division.getParentCompany().getStructureID() + "," + 
      activeFlag + "," + 
      '\003' + "," + 
      "'" + MilestoneHelper.escapeSingleQuotes(division.getName()) + "'," + 
      "'" + MilestoneHelper.escapeSingleQuotes(division.getStructureAbbreviation()) + "'," + 
      "0" + "," + 
      "0" + "," + 
      userID + "," + 
      timestamp + ";";
    log.debug("Division update query:\n" + query);
    JdbcConnector connector = MilestoneHelper.getConnector(query);
    connector.runQuery();
    connector.close();
    division.flushAudits(userID);
    String timestampQuery = "SELECT last_updated_ck, last_updated_on FROM vi_structure WHERE structure_id = " + 
      
      division.getStructureID() + 
      ";";
    JdbcConnector connectorTimestamp = MilestoneHelper.getConnector(timestampQuery);
    connectorTimestamp.runQuery();
    if (connectorTimestamp.more()) {
      division.setLastUpdatedCk(Long.parseLong(connectorTimestamp.getField("last_updated_ck"), 16));
      division.setLastUpdateDate(MilestoneHelper.getDatabaseDate(connectorTimestamp.getField("last_updated_on")));
    } 
    connectorTimestamp.close();
    return division;
  }
  
  public Division saveNewDivision(Division division, int userID) {
    int parentCompany = -1;
    if (division.getParentCompany() != null)
      parentCompany = division.getParentCompany().getStructureID(); 
    division.flushAudits(userID);
    String activeFlag = "0";
    boolean boolVal = division.getActive();
    if (!boolVal)
      activeFlag = "1"; 
    String query = "sp_sav_Structure " + 
      division.getStructureID() + "," + 
      parentCompany + "," + 
      activeFlag + "," + 
      '\003' + "," + 
      "'" + MilestoneHelper.escapeSingleQuotes(division.getName()) + "'," + 
      "'" + MilestoneHelper.escapeSingleQuotes(division.getStructureAbbreviation()) + "'," + 
      "-1" + "," + 
      "-1" + "," + 
      userID + "," + 
      "-1" + ";";
    log.debug("Division save query:\n" + query);
    JdbcConnector connector = MilestoneHelper.getConnector(query);
    connector.runQuery();
    division.setStructureID(connector.getIntegerField("ReturnId"));
    String timestampQuery = "SELECT last_updated_ck, last_updated_on FROM vi_structure WHERE structure_id = " + 
      
      division.getStructureID() + 
      ";";
    JdbcConnector connectorTimestamp = MilestoneHelper.getConnector(timestampQuery);
    connectorTimestamp.runQuery();
    if (connectorTimestamp.more()) {
      division.setLastUpdatedCk(Long.parseLong(connectorTimestamp.getField("last_updated_ck"), 16));
      division.setLastUpdateDate(MilestoneHelper.getDatabaseDate(connectorTimestamp.getField("last_updated_on")));
    } 
    connectorTimestamp.close();
    connector.close();
    return division;
  }
  
  public void deleteDivision(Division division, int userID) {
    String query = "sp_del_Structure " + 
      division.getStructureID();
    log.debug("Division delete query:\n" + query);
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
        String environmentDescriptionSearch = corpObject.getEnvironmentSearch();
        String companyDescriptionSearch = corpObject.getCompanySearch();
        String divisionDescriptionSearch = corpObject.getDivisionSearch();
        Vector corporateObjects = Cache.getFamilies();
        contents = MilestoneHelper.filterCorporateByDescription(corporateObjects, descriptionSearch);
        contents = MilestoneHelper.filterCorporateByDescription(Cache.getFamilies(), descriptionSearch);
        contents = getEnvironmentsFromFamilies(contents);
        contents = MilestoneHelper.filterCorporateByDescription(contents, environmentDescriptionSearch);
        contents = getCompaniesFromEnvironments(contents);
        contents = MilestoneHelper.filterCorporateByDescription(contents, companyDescriptionSearch);
        contents = getDivisionsFromCompanies(contents);
        contents = MilestoneHelper.filterCorporateByDescription(contents, divisionDescriptionSearch);
        notepad.setAllContents(MilestoneHelper.sortCorporateVectorByName(contents));
      }  
  }
  
  public Vector getDivisionNotepadList(int UserId, Notepad notepad) {
    if (notepad != null && 
      notepad.getCorporateObjectSearchObj() != null) {
      setNotepadQuery(notepad);
      return MilestoneHelper.sortCorporateVectorByName(notepad.getAllContents());
    } 
    return MilestoneHelper.sortCorporateVectorByName(Cache.getDivisions());
  }
  
  public static Vector getDivisionsFromCompanies(Vector companies) {
    Vector preCache = new Vector();
    for (int i = 0; i < companies.size(); i++) {
      Company company = (Company)companies.get(i);
      Vector divisions = company.getDivisions();
      for (int j = 0; j < divisions.size(); j++)
        preCache.add(divisions.get(j)); 
    } 
    return preCache;
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
  
  public static Vector getCompaniesFromEnvironments(Vector environments) {
    Vector preCache = new Vector();
    for (int i = 0; i < environments.size(); i++) {
      Environment environment = (Environment)environments.get(i);
      Vector companies = environment.getCompanies();
      for (int j = 0; j < companies.size(); j++)
        preCache.add(companies.get(j)); 
    } 
    return preCache;
  }
  
  public boolean isTimestampValid(Division division) {
    if (division != null) {
      String timestampQuery = "SELECT last_updated_ck, last_updated_on FROM vi_structure WHERE structure_id = " + 
        
        division.getStructureID() + 
        ";";
      JdbcConnector connectorTimestamp = MilestoneHelper.getConnector(timestampQuery);
      connectorTimestamp.runQuery();
      if (connectorTimestamp.more())
        if (division.getLastUpdatedCk() != Long.parseLong(connectorTimestamp.getField("last_updated_ck", ""), 16)) {
          connectorTimestamp.close();
          return false;
        }  
      connectorTimestamp.close();
      return true;
    } 
    return false;
  }
}


/* Location:              D:\Documents\NetBeansProjects\milestone2 Local.war!\WEB-INF\classes\co\\universal\milestone\DivisionManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */