package WEB-INF.classes.com.universal.milestone;

import com.techempower.DataEntity;
import com.universal.milestone.Audit;
import com.universal.milestone.AuditItem;
import java.util.Date;

public class Audit extends DataEntity {
  public static final int VALUE_LENGTH = 20;
  
  public static final int AUDIT_NEW = 1;
  
  public static final int AUDIT_UPDATE = 2;
  
  public int auditID = 0;
  
  public int auditEntityID = 0;
  
  public int auditItemID = 0;
  
  public int auditType = 2;
  
  public AuditItem auditItem = null;
  
  public Date auditTimestamp = new Date();
  
  public String oldValue = "";
  
  public String newValue = "";
  
  public int userID = 0;
  
  public Audit() {}
  
  public Audit(AuditItem auditItem, int auditEntityID, int auditType, Date timestamp, String oldValue, String newValue, int userID) {
    this.auditItemID = auditItem.getAuditItemID();
    this.auditEntityID = auditEntityID;
    this.auditTimestamp = timestamp;
    this.userID = userID;
    this.auditType = auditType;
    setOldValue(oldValue);
    setNewValue(newValue);
  }
  
  public Audit(AuditItem auditItem, int auditEntityID, int auditType, Date timestamp, String oldValue, String newValue) { this(auditItem, auditEntityID, auditType, timestamp, oldValue, newValue, 0); }
  
  public void setOldValue(String oldValue) {
    if (oldValue != null && oldValue.length() > 20) {
      this.oldValue = oldValue.substring(0, 20);
    } else {
      this.oldValue = oldValue;
    } 
  }
  
  public void setNewValue(String newValue) {
    if (newValue != null && newValue.length() > 20) {
      this.newValue = newValue.substring(0, 20);
    } else {
      this.newValue = newValue;
    } 
  }
  
  public int getIdentity() { return this.auditID; }
  
  public String getTableName() { return "Audit"; }
  
  public String getIdentityColumnName() { return "AuditID"; }
  
  public String toString() {
    return "Audit [a" + this.auditID + 
      "; i" + this.auditItemID + 
      "; t" + this.auditType + 
      "; " + this.newValue + 
      "; u" + this.userID + 
      "]";
  }
}


/* Location:              D:\Documents\NetBeansProjects\milestone2 Local.war!\WEB-INF\classes\co\\universal\milestone\Audit.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */