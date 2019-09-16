package WEB-INF.classes.com.universal.milestone;

import com.techempower.ComponentLog;
import com.techempower.EnhancedProperties;
import com.techempower.gemini.GeminiApplication;
import com.universal.milestone.Cache;
import com.universal.milestone.Company;
import com.universal.milestone.CorporateObjectSearchObj;
import com.universal.milestone.Division;
import com.universal.milestone.Environment;
import com.universal.milestone.Family;
import com.universal.milestone.JdbcConnector;
import com.universal.milestone.Label;
import com.universal.milestone.LabelManager;
import com.universal.milestone.MilestoneConstants;
import com.universal.milestone.MilestoneHelper;
import com.universal.milestone.MilestoneHelper_2;
import com.universal.milestone.Notepad;
import com.universal.milestone.projectSearchSvcClient;
import java.rmi.RemoteException;
import java.util.Hashtable;
import java.util.Vector;
import net.umusic.milestone.alps.DcLabelData;

public class LabelManager implements MilestoneConstants {
  public static final String COMPONENT_CODE = "mLab";
  
  protected static LabelManager labelManager = null;
  
  protected static ComponentLog log;
  
  public static void configure(EnhancedProperties props, GeminiApplication application) { log = application.getLog("mLab"); }
  
  public static LabelManager getInstance() {
    if (labelManager == null)
      labelManager = new LabelManager(); 
    return labelManager;
  }
  
  public Label getLabel(int id) {
    Label label = null;
    label = (Label)MilestoneHelper.getStructureObject(id);
    if (label != null)
      label.setParentDivision((Division)MilestoneHelper.getStructureObject(label.getParentID())); 
    String query = "SELECT last_updated_by, last_updated_on, last_updated_ck  FROM vi_Structure  WHERE type = 4 AND structure_id = " + 
      
      id + 
      " ORDER BY name ";
    JdbcConnector connector = MilestoneHelper.getConnector(query);
    connector.runQuery();
    if (connector.more()) {
      String lastDateString = connector.getFieldByName("last_updated_on");
      if (lastDateString != null)
        label.setLastUpdateDate(MilestoneHelper.getDatabaseDate(lastDateString)); 
      label.setLastUpdatingUser(connector.getIntegerField("last_updated_by"));
      long lastUpdatedLong = Long.parseLong(connector.getField("last_updated_ck"), 16);
      label.setLastUpdatedCk(lastUpdatedLong);
    } 
    connector.close();
    getArchimedes(label, Boolean.valueOf(false));
    return label;
  }
  
  public Label saveLabel(Label label, int userID) {
    long timestamp = label.getLastUpdatedCk();
    String activeFlag = "0";
    boolean boolVal = label.getActive();
    if (!boolVal)
      activeFlag = "1"; 
    String query = "sp_sav_Structure " + 
      label.getStructureID() + "," + 
      label.getParentDivision().getStructureID() + "," + 
      activeFlag + "," + 
      '\004' + "," + 
      "'" + MilestoneHelper.escapeSingleQuotes(label.getName()) + "'," + 
      "'" + MilestoneHelper.escapeSingleQuotes(label.getStructureAbbreviation()) + "'," + 
      
      label.getDistribution() + "," + 
      "0" + "," + 
      userID + "," + 
      timestamp;
    JdbcConnector connector = MilestoneHelper.getConnector(query);
    connector.runQuery();
    connector.close();
    label.flushAudits(userID);
    if (label.getArchimedesId() == 0)
      updateDistributionCompany(label); 
    String timestampQuery = "SELECT last_updated_ck, last_updated_on FROM vi_structure WHERE structure_id = " + 
      
      label.getStructureID() + 
      ";";
    JdbcConnector connectorTimestamp = MilestoneHelper.getConnector(timestampQuery);
    connectorTimestamp.runQuery();
    if (connectorTimestamp.more()) {
      label.setLastUpdatedCk(Long.parseLong(connectorTimestamp.getField("last_updated_ck"), 16));
      label.setLastUpdateDate(MilestoneHelper.getDatabaseDate(connectorTimestamp.getField("last_updated_on")));
    } 
    connectorTimestamp.close();
    return label;
  }
  
  public Label saveNewLabel(Label label, int userID) {
    int parentDivision = -1;
    if (label.getParentDivision() != null)
      parentDivision = label.getParentDivision().getStructureID(); 
    String activeFlag = "0";
    boolean boolVal = label.getActive();
    if (!boolVal)
      activeFlag = "1"; 
    String query = "sp_sav_Structure " + 
      label.getStructureID() + "," + 
      parentDivision + "," + 
      activeFlag + "," + 
      '\004' + "," + 
      "'" + MilestoneHelper.escapeSingleQuotes(label.getName()) + "'," + 
      "'" + MilestoneHelper.escapeSingleQuotes(label.getStructureAbbreviation()) + "'," + 
      
      label.getDistribution() + "," + 
      "-1" + "," + 
      userID + "," + 
      "-1";
    label.flushAudits(userID);
    log.debug("Save new label query:\n" + query);
    JdbcConnector connector = MilestoneHelper.getConnector(query);
    connector.runQuery();
    label.setStructureID(connector.getIntegerField("ReturnId"));
    String timestampQuery = "SELECT last_updated_ck, last_updated_on FROM vi_structure WHERE structure_id = " + 
      
      label.getStructureID() + 
      ";";
    JdbcConnector connectorTimestamp = MilestoneHelper.getConnector(timestampQuery);
    connectorTimestamp.runQuery();
    if (connectorTimestamp.more()) {
      label.setLastUpdatedCk(Long.parseLong(connectorTimestamp.getField("last_updated_ck"), 16));
      label.setLastUpdateDate(MilestoneHelper.getDatabaseDate(connectorTimestamp.getField("last_updated_on")));
    } 
    connectorTimestamp.close();
    connector.close();
    if (label.getName().matches("(?i).*unknown.*"))
      updateDistributionCompany(label); 
    return label;
  }
  
  public void deleteLabel(Label label, int userID) {
    String query = "sp_del_Structure " + 
      label.getStructureID();
    JdbcConnector connector = MilestoneHelper.getConnector(query);
    connector.runQuery();
    connector.close();
    Cache.getInstance().flushCorporateStructure();
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
  
  public void setNotepadQuery(Notepad notepad) {
    if (notepad != null)
      if (notepad.getCorporateObjectSearchObj() != null) {
        CorporateObjectSearchObj corpObject = notepad.getCorporateObjectSearchObj();
        Vector contents = new Vector();
        String descriptionSearch = corpObject.getFamilySearch();
        String environmentDescriptionSearch = corpObject.getEnvironmentSearch();
        String companyDescriptionSearch = corpObject.getCompanySearch();
        String divisionDescriptionSearch = corpObject.getDivisionSearch();
        String labelDescriptionSearch = corpObject.getLabelSearch();
        String entityDescriptionSearch = corpObject.getEntitySearch();
        Vector corporateObjects = Cache.getFamilies();
        contents = MilestoneHelper.filterCorporateByDescription(corporateObjects, descriptionSearch);
        contents = getEnvironmentsFromFamilies(contents);
        contents = MilestoneHelper.filterCorporateByDescription(contents, environmentDescriptionSearch);
        contents = getCompaniesFromEnvironments(contents);
        contents = MilestoneHelper.filterCorporateByDescription(contents, companyDescriptionSearch);
        contents = getDivisionsFromCompanies(contents);
        contents = MilestoneHelper.filterCorporateByDescription(contents, divisionDescriptionSearch);
        Vector preCache = new Vector();
        for (int i = 0; i < contents.size(); i++) {
          Division division = (Division)contents.get(i);
          Vector labels = division.getLabels();
          for (int j = 0; j < labels.size(); j++)
            preCache.add(labels.get(j)); 
        } 
        preCache = MilestoneHelper.sortCorporateVectorByName(MilestoneHelper.filterCorporateByDescription(preCache, labelDescriptionSearch));
        preCache = MilestoneHelper.sortCorporateVectorByName(MilestoneHelper_2.filterCorporateByEntityName(preCache, entityDescriptionSearch));
        notepad.setAllContents(preCache);
      }  
  }
  
  public Vector getLabelNotepadList(int UserId, Notepad notepad) {
    sortedVector = new Vector();
    if (notepad != null && 
      notepad.getCorporateObjectSearchObj() != null) {
      setNotepadQuery(notepad);
      return MilestoneHelper.sortCorporateVectorByName(notepad.getAllContents());
    } 
    return MilestoneHelper.sortCorporateVectorByName(Cache.getLabels());
  }
  
  public boolean isTimestampValid(Label label) {
    if (label != null) {
      String timestampQuery = "SELECT last_updated_ck, last_updated_on FROM vi_structure WHERE structure_id = " + 
        
        label.getStructureID() + 
        ";";
      JdbcConnector connectorTimestamp = MilestoneHelper.getConnector(timestampQuery);
      connectorTimestamp.runQuery();
      if (connectorTimestamp.more())
        if (label.getLastUpdatedCk() != Long.parseLong(connectorTimestamp.getField("last_updated_ck", ""), 16)) {
          connectorTimestamp.close();
          return false;
        }  
      connectorTimestamp.close();
      return true;
    } 
    return false;
  }
  
  public boolean isDuplicate(String name, int type, int id, int parentId) { return false; }
  
  public static void getArchimedes(Label label, Boolean IsGetFromCache) {
    if (label.getArchimedesId() == 0) {
      StringBuffer query = new StringBuffer(" SELECT ");
      query.append(" ISNULL(DistCoId,'1') as DistCoId, ");
      query.append(" ISNULL(DistCoName,'UMVD') as DistCoName ");
      query.append(" FROM ArchimedesLabels ");
      query.append(" WHERE MS_LabelId = " + label.getStructureID());
      JdbcConnector connector = MilestoneHelper.getConnector(query.toString());
      connector.runQuery();
      if (connector.more()) {
        label.setDistCoName(connector.getField("DistCoName"));
        label.setDistCoId(connector.getIntegerField("DistCoId"));
      } 
      connector.close();
    } else {
      try {
        Hashtable<Integer, DcLabelData> cachedLD = null;
        if (IsGetFromCache.booleanValue())
          if (projectSearchSvcClient.cachedLabelData == null) {
            log.debug("Get Archimedes Label Data start.");
            try {
              cachedLD = projectSearchSvcClient.LabelDataGet();
              System.out.println("Labels from WCF: " + cachedLD.size());
            } catch (RemoteException re) {
              log.debug(re.getMessage());
              System.out.println(re.getMessage());
            } catch (Exception e) {
              log.debug(e.getMessage());
              System.out.println(e.getMessage());
            } 
            log.debug("Get Archimedes Label Data end.");
          } else {
            cachedLD = projectSearchSvcClient.cachedLabelData;
          }  
        DcLabelData dld = null;
        if (cachedLD != null && cachedLD.containsKey(Integer.valueOf(label.getArchimedesId()))) {
          dld = (DcLabelData)cachedLD.get(Integer.valueOf(label.getArchimedesId()));
        } else {
          dld = projectSearchSvcClient.LabelDataGetById(label.getArchimedesId());
        } 
        if (dld != null && dld.getArchimedesID() != null) {
          log.debug("Loading Label Id " + dld.getArchimedesID());
          label.setArchimedesId(dld.getArchimedesID().intValue());
          label.setEntityName(dld.getEntityName());
          label.setLegacyOpCo(dld.getLegacyOperatingCo());
          label.setLegacyOpUnit(dld.getLegacyOperatingUnit());
          label.setLegacySuperLabel(dld.getLegacySuperlabel());
          label.setLegacySubLabel(dld.getLegacySublabel());
          label.setLevelOfActivity(dld.getLevelOfActivity());
          label.setProductionGroupCode(dld.getProductionGroupCode());
          label.setEntityType(dld.getEntityType());
          label.setAPNGInd(dld.getAPNGInd().booleanValue());
          label.setDistCoName(dld.getDistCoName());
          label.setDistCoId(dld.getDistCoID().intValue());
        } 
      } catch (Exception e) {
        System.out.println("****exception raised in projectSearchSvcClient().LabelDataGet****");
      } 
    } 
  }
  
  public static void updateDistributionCompany(Label label) {
    String query = " sp_sav_distCompanyForLabel " + label.getStructureID() + 
      "," + label.getDistCoId() + ",'" + label.getDistCoName() + "'";
    JdbcConnector connector = MilestoneHelper.getConnector(query);
    connector.runQuery();
    connector.close();
  }
}


/* Location:              D:\Documents\NetBeansProjects\milestone2 Local.war!\WEB-INF\classes\co\\universal\milestone\LabelManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */