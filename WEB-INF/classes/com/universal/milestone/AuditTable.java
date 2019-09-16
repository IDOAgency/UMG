package WEB-INF.classes.com.universal.milestone;

import com.techempower.DataEntity;
import com.universal.milestone.AuditTable;

public class AuditTable extends DataEntity {
  public int auditTableID = 0;
  
  public String tableName = "";
  
  public String className = "";
  
  public int getIdentity() { return this.auditTableID; }
  
  public String getTableName() { return "AuditTable"; }
  
  public String getIdentityColumnName() { return "AuditTableID"; }
  
  public String toString() {
    return "AuditTable [" + this.auditTableID + 
      "; " + this.tableName + 
      "; " + this.className + 
      "]";
  }
}


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\AuditTable.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */