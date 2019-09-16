package WEB-INF.classes.com.universal.milestone;

import com.techempower.ComponentLog;
import com.techempower.EnhancedProperties;
import com.techempower.gemini.GeminiApplication;
import com.universal.milestone.Cache;
import com.universal.milestone.Company;
import com.universal.milestone.CompanyManager;
import com.universal.milestone.CorporateObjectSearchObj;
import com.universal.milestone.Environment;
import com.universal.milestone.Family;
import com.universal.milestone.JdbcConnector;
import com.universal.milestone.MilestoneConstants;
import com.universal.milestone.MilestoneHelper;
import com.universal.milestone.Notepad;
import java.util.Vector;

public class CompanyManager implements MilestoneConstants {
  public static final String COMPONENT_CODE = "mCmp";
  
  protected static CompanyManager companyManager = null;
  
  protected static ComponentLog log;
  
  public static void configure(EnhancedProperties props, GeminiApplication application) { log = application.getLog("mCmp"); }
  
  public static CompanyManager getInstance() {
    if (companyManager == null)
      companyManager = new CompanyManager(); 
    return companyManager;
  }
  
  public Company getCompany(int id) {
    Company company = null;
    company = (Company)MilestoneHelper.getStructureObject(id);
    String query = "SELECT last_updated_by, last_updated_on, last_updated_ck FROM vi_Structure WHERE type = 2 AND structure_id = " + 
      
      id + 
      " ORDER BY name;";
    JdbcConnector connector = MilestoneHelper.getConnector(query);
    connector.runQuery();
    if (connector.more()) {
      String lastDateString = connector.getFieldByName("last_updated_on");
      if (lastDateString != null)
        company.setLastUpdateDate(MilestoneHelper.getDatabaseDate(lastDateString)); 
      company.setLastUpdatingUser(connector.getIntegerField("last_updated_by"));
      long lastUpdatedLong = Long.parseLong(connector.getField("last_updated_ck"), 16);
      company.setLastUpdatedCk(lastUpdatedLong);
    } 
    connector.close();
    return company;
  }
  
  public Company saveCompany(Company company, int userID) {
    long timestamp = company.getLastUpdatedCk();
    String activeFlag = "0";
    boolean boolVal = company.getActive();
    if (!boolVal)
      activeFlag = "1"; 
    String query = "sp_sav_Structure " + 
      company.getStructureID() + "," + 
      company.getParentEnvironment().getStructureID() + "," + 
      activeFlag + "," + 
      '\002' + "," + 
      "'" + MilestoneHelper.escapeSingleQuotes(company.getName()) + "'," + 
      "'" + MilestoneHelper.escapeSingleQuotes(company.getStructureAbbreviation()) + "'," + 
      company.getDistribution() + "," + 
      company.getCalendarGroup() + "," + 
      userID + "," + 
      timestamp + 
      ";";
    JdbcConnector connector = MilestoneHelper.getConnector(query);
    log.debug("Executing query: " + query);
    connector.runQuery();
    connector.close();
    log.debug("Flushing audits.");
    company.flushAudits(userID);
    String timestampQuery = "SELECT last_updated_ck, last_updated_on FROM vi_structure WHERE structure_id = " + 
      
      company.getStructureID() + 
      ";";
    JdbcConnector connectorTimestamp = MilestoneHelper.getConnector(timestampQuery);
    connectorTimestamp.runQuery();
    if (connectorTimestamp.more()) {
      company.setLastUpdatedCk(Long.parseLong(connectorTimestamp.getField("last_updated_ck"), 16));
      company.setLastUpdateDate(MilestoneHelper.getDatabaseDate(connectorTimestamp.getField("last_updated_on")));
    } 
    connectorTimestamp.close();
    return company;
  }
  
  public Company saveNewCompany(Company company, int userID) {
    long timestamp = company.getLastUpdatedCk();
    company.flushAudits(userID);
    String query = "sp_sav_Structure " + 
      company.getStructureID() + "," + 
      company.getParentEnvironment().getStructureID() + "," + 
      "0" + "," + 
      '\002' + "," + 
      "'" + MilestoneHelper.escapeSingleQuotes(company.getName()) + "'," + 
      "'" + MilestoneHelper.escapeSingleQuotes(company.getStructureAbbreviation()) + "'," + 
      "0" + "," + 
      "0" + "," + 
      userID + "," + 
      timestamp + 
      ";";
    JdbcConnector connector = MilestoneHelper.getConnector(query);
    connector.runQuery();
    if (company.isNew()) {
      company.setStructureID(connector.getIntegerField("ReturnId"));
      log.debug("New company's ID set to " + company.getStructureID());
    } 
    String timestampQuery = "SELECT last_updated_ck, last_updated_on FROM vi_structure WHERE structure_id = " + 
      
      company.getStructureID() + 
      ";";
    JdbcConnector connectorTimestamp = MilestoneHelper.getConnector(timestampQuery);
    connectorTimestamp.runQuery();
    if (connectorTimestamp.more()) {
      company.setLastUpdatedCk(Long.parseLong(connectorTimestamp.getField("last_updated_ck"), 16));
      company.setLastUpdateDate(MilestoneHelper.getDatabaseDate(connectorTimestamp.getField("last_updated_on")));
    } 
    connectorTimestamp.close();
    connector.close();
    String companyUpdateQuery = "sp_structure_insert_maintenance 2," + company.getStructureID() + ";";
    JdbcConnector companyUpdate = MilestoneHelper.getConnector(companyUpdateQuery);
    companyUpdate.runQuery();
    companyUpdate.close();
    return company;
  }
  
  public void deleteCompany(Company company, int userID) {
    String query = "sp_del_Structure " + 
      company.getStructureID();
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
        String companyDescriptionSearch = corpObject.getCompanySearch();
        Vector corporateObjects = Cache.getFamilies();
        contents = MilestoneHelper.filterCorporateByDescription(corporateObjects, descriptionSearch);
        contents = MilestoneHelper.filterCorporateByDescription(Cache.getFamilies(), descriptionSearch);
        contents = getEnvironmentsFromFamilies(contents);
        contents = MilestoneHelper.filterCorporateByDescription(contents, environmentDescriptionSearch);
        contents = getCompaniesFromEnvironments(contents);
        contents = MilestoneHelper.filterCorporateByDescription(contents, companyDescriptionSearch);
        notepad.setAllContents(MilestoneHelper.sortCorporateVectorByName(contents));
      }  
  }
  
  public Vector getCompanyNotepadList(int UserId, Notepad notepad) {
    if (notepad != null && 
      notepad.getCorporateObjectSearchObj() != null) {
      setNotepadQuery(notepad);
      return MilestoneHelper.sortCorporateVectorByName(notepad.getAllContents());
    } 
    return MilestoneHelper.sortCorporateVectorByName(Cache.getCompanies());
  }
  
  public static Vector getCompaniesFromFamilies(Vector families) {
    Vector preCache = new Vector();
    for (int i = 0; i < families.size(); i++) {
      Family family = (Family)families.get(i);
      Vector companies = family.getCompanies();
      for (int j = 0; j < companies.size(); j++)
        preCache.add(companies.get(j)); 
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
  
  public boolean isTimestampValid(Company company) {
    if (company != null) {
      String timestampQuery = "SELECT last_updated_ck, last_updated_on FROM vi_structure WHERE structure_id = " + 
        
        company.getStructureID() + 
        ";";
      JdbcConnector connectorTimestamp = MilestoneHelper.getConnector(timestampQuery);
      connectorTimestamp.runQuery();
      if (connectorTimestamp.more())
        if (company.getLastUpdatedCk() != Long.parseLong(connectorTimestamp.getField("last_updated_ck", ""), 16)) {
          connectorTimestamp.close();
          return false;
        }  
      connectorTimestamp.close();
      return true;
    } 
    return false;
  }
}


/* Location:              D:\Documents\NetBeansProjects\milestone2 Local.war!\WEB-INF\classes\co\\universal\milestone\CompanyManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */