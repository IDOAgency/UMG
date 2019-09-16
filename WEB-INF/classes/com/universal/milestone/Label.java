package WEB-INF.classes.com.universal.milestone;

import com.techempower.DatabaseConnector;
import com.universal.milestone.CorporateStructureObject;
import com.universal.milestone.Division;
import com.universal.milestone.Label;

public class Label extends CorporateStructureObject implements Cloneable {
  protected Division parentDivision;
  
  protected boolean activeFlag;
  
  public int distribution = 0;
  
  protected String entityName = "";
  
  protected String legacyOpCo = "";
  
  protected String legacyOpUnit = "";
  
  protected String legacySuperLabel = "";
  
  protected String legacySubLabel = "";
  
  protected String levelOfActivity = "";
  
  protected String productionGroupCode = "";
  
  protected String entityType = "";
  
  protected boolean APNGInd = false;
  
  protected String distCoName = "";
  
  protected int distCoId = -1;
  
  public Division getParentDivision() { return this.parentDivision; }
  
  public void setParentDivision(Division division) { this.parentDivision = division; }
  
  public int getDistribution() { return this.distribution; }
  
  public void setDistribution(int distribution) {
    auditCheck("distribution", this.distribution, distribution);
    this.distribution = distribution;
  }
  
  public CorporateStructureObject getParent() { return getParentDivision(); }
  
  public void setParent(CorporateStructureObject cso) { setParentDivision((Division)cso); }
  
  public boolean getActive() { return this.activeFlag; }
  
  public void setActive(boolean active) { this.activeFlag = active; }
  
  public void customInitialization(DatabaseConnector connector) {
    super.customInitialization(connector);
    this.distribution = connector.getInt("region");
  }
  
  public String getEntityName() { return this.entityName; }
  
  public void setEntityName(String entityName) { this.entityName = entityName; }
  
  public String getLegacyOpCo() { return this.legacyOpCo; }
  
  public void setLegacyOpCo(String legacyOpCo) { this.legacyOpCo = legacyOpCo; }
  
  public String getLegacyOpUnit() { return this.legacyOpUnit; }
  
  public void setLegacyOpUnit(String legacyOpUnit) { this.legacyOpUnit = legacyOpUnit; }
  
  public String getLegacySuperLabel() { return this.legacySuperLabel; }
  
  public void setLegacySuperLabel(String legacySuperLabel) { this.legacySuperLabel = legacySuperLabel; }
  
  public String getLegacySubLabel() { return this.legacySubLabel; }
  
  public void setLegacySubLabel(String legacySubLabel) { this.legacySubLabel = legacySubLabel; }
  
  public String getLevelOfActivity() { return this.levelOfActivity; }
  
  public void setLevelOfActivity(String levelOfActivity) { this.levelOfActivity = levelOfActivity; }
  
  public String getProductionGroupCode() { return this.productionGroupCode; }
  
  public void setProductionGroupCode(String productionGroupCode) { this.productionGroupCode = productionGroupCode; }
  
  public String getEntityType() { return this.entityType; }
  
  public void setEntityType(String entityType) { this.entityType = entityType; }
  
  public void setAPNGInd(boolean apngInd) { this.APNGInd = apngInd; }
  
  public boolean getAPGNInd() { return this.APNGInd; }
  
  public void setDistCoName(String distCoName) { this.distCoName = distCoName; }
  
  public String getDistCoName() { return this.distCoName; }
  
  public void setDistCoId(int distCoId) { this.distCoId = distCoId; }
  
  public int getDistCoId() { return this.distCoId; }
  
  public Object clone() throws CloneNotSupportedException { return super.clone(); }
}


/* Location:              D:\Documents\NetBeansProjects\milestone2 Local.war!\WEB-INF\classes\co\\universal\milestone\Label.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */