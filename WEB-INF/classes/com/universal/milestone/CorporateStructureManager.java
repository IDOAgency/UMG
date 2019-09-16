package WEB-INF.classes.com.universal.milestone;

import com.techempower.ComponentLog;
import com.techempower.EnhancedProperties;
import com.techempower.gemini.GeminiApplication;
import com.universal.milestone.CorporateStructureManager;
import com.universal.milestone.CorporateStructureObject;
import com.universal.milestone.JdbcConnector;
import com.universal.milestone.MilestoneConstants;
import com.universal.milestone.MilestoneHelper;

public class CorporateStructureManager implements MilestoneConstants {
  public static final String COMPONENT_CODE = "mCso";
  
  protected static CorporateStructureManager corporateStructureManager = null;
  
  protected static ComponentLog log;
  
  public static void configure(EnhancedProperties props, GeminiApplication application) { log = application.getLog("mCso"); }
  
  public static CorporateStructureManager getInstance() {
    if (corporateStructureManager == null)
      corporateStructureManager = new CorporateStructureManager(); 
    return corporateStructureManager;
  }
  
  public String delete(CorporateStructureObject corporateStructureObject, int userid) {
    String errorMsg = null;
    int structureId = corporateStructureObject.getStructureID();
    if (corporateStructureObject.getChildren() == null || (
      corporateStructureObject.getChildren() != null && 
      corporateStructureObject.getChildren().size() < 1)) {
      String query, query, query;
      boolean error = false;
      JdbcConnector connector = null;
      switch (corporateStructureObject.getStructureType()) {
        case 1:
          query = "Select * FROM vi_Template_Header WHERE Owner = " + structureId;
          connector = MilestoneHelper.getConnector(query);
          connector.setForwardOnly(false);
          connector.runQuery();
          if (connector.getRowCount() > 0)
            error = true; 
          query = "Select * FROM vi_Task WHERE Owner = " + structureId;
          connector.runQuery();
          if (connector.getRowCount() > 0)
            error = true; 
          connector.close();
          if (error)
            errorMsg = "This family cannot be deleted because it is used in a Template or Task."; 
          break;
        case 4:
          query = "Select * FROM vi_Template_Header WHERE Owner = " + structureId;
          connector = MilestoneHelper.getConnector(query);
          connector.setForwardOnly(false);
          connector.runQuery();
          if (connector.getRowCount() > 0) {
            error = true;
            errorMsg = "This label cannot be deleted because it is being used in a Release.";
          } 
          connector.close();
          query = "Select * FROM vi_Release_Header WHERE label_id = " + structureId;
          connector = MilestoneHelper.getConnector(query);
          connector.setForwardOnly(false);
          connector.runQuery();
          if (connector.getRowCount() > 0) {
            error = true;
            errorMsg = "This label cannot be deleted because it is being used in a Release.";
          } 
          connector.close();
          break;
        case 2:
          query = "Select * FROM vi_user_company WHERE company_id = " + structureId;
          connector = MilestoneHelper.getConnector(query);
          connector.setForwardOnly(false);
          connector.runQuery();
          if (connector.getRowCount() > 0) {
            error = true;
            errorMsg = "This company cannot be deleted because there are users assigned to it.";
          } 
          connector.close();
          break;
      } 
      if (!error)
        if (!deleteFromDatabase(structureId))
          errorMsg = "DB Exception, Could not delete the record. \nPlease try it again later";  
    } else {
      errorMsg = "This cannot be deleted because it has children.";
    } 
    return errorMsg;
  }
  
  private boolean deleteFromDatabase(int structureId) {
    try {
      String query = "sp_del_Structure " + 
        structureId;
      JdbcConnector connector = MilestoneHelper.getConnector(query);
      connector.runQuery();
      connector.close();
      return true;
    } catch (Exception e) {
      return false;
    } 
  }
  
  public boolean isDuplicate(String name, int type, int id) {
    boolean isDuplicate = false;
    if (type == 1) {
      String query = "SELECT * from vi_structure where  name = '" + 
        MilestoneHelper.escapeSingleQuotes(name) + "' " + 
        " and type = " + type + 
        " and structure_id <> " + id;
      JdbcConnector connector = MilestoneHelper.getConnector(query);
      connector.runQuery();
      if (connector.more())
        isDuplicate = true; 
      connector.close();
    } 
    return isDuplicate;
  }
  
  public boolean isArchiStructure(String name, int type, int id) {
    boolean isArchiStructure = false;
    String query = "SELECT 'x' from dbo.ArchimedesLabels where  ArchimedesId is not null and (ms_familyId = " + 
      
      id + 
      " or ms_environmentId = " + id + 
      " or ms_companyId = " + id + 
      " or ms_divisionId = " + id + 
      " or ms_labelId = " + id + ")";
    JdbcConnector connector = MilestoneHelper.getConnector(query);
    connector.runQuery();
    if (connector.more())
      isArchiStructure = true; 
    connector.close();
    return isArchiStructure;
  }
}


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\CorporateStructureManager.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */