package WEB-INF.classes.com.universal.milestone;

import com.techempower.DataEntity;
import com.universal.milestone.AuditItem;

public class AuditItem extends DataEntity {
  public int auditItemID = 0;
  
  public int auditTableID = 0;
  
  public String auditField = "";
  
  public int getAuditItemID() { return this.auditItemID; }
  
  public int getIdentity() { return this.auditItemID; }
  
  public String getTableName() { return "AuditItem"; }
  
  public String getIdentityColumnName() { return "AuditItemID"; }
  
  public String toString() {
    return "AuditItem [" + this.auditItemID + 
      "; " + this.auditTableID + 
      "; " + this.auditField + 
      "]";
  }
}


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\AuditItem.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */