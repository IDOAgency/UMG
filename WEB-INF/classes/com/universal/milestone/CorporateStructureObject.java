package WEB-INF.classes.com.universal.milestone;

import com.techempower.DatabaseConnector;
import com.universal.milestone.CorporateStructureObject;
import com.universal.milestone.MilestoneDataEntity;
import com.universal.milestone.NotepadContentObject;
import java.util.Calendar;
import java.util.Hashtable;
import java.util.Vector;

public abstract class CorporateStructureObject extends MilestoneDataEntity implements NotepadContentObject {
  public int structureID = 0;
  
  public int structureType = 0;
  
  public int parentID = 0;
  
  public String name = "";
  
  public String structureAbbreviation = "";
  
  public int lastUpdatingUser = 0;
  
  public Calendar lastUpdateDate = null;
  
  public long lastUpdatedCk = 0L;
  
  protected static Hashtable bindings = null;
  
  public int archimedesId = -1;
  
  public boolean isUsed = false;
  
  public Hashtable getCustomVariableBindings() {
    if (bindings == null) {
      bindings = new Hashtable();
      bindings.put("structure_id", "structureID");
      bindings.put("parent_id", "parentID");
      bindings.put("type", "structureType");
      bindings.put("name", "name");
      bindings.put("abbreviation", "structureAbbreviation");
      bindings.put("last_updated_on", "lastUpdateDate");
      bindings.put("last_updated_by", "lastUpdatingUser");
      bindings.put("archimedes_id", "archimedes_id");
    } 
    return bindings;
  }
  
  public void customInitialization(DatabaseConnector connector) {
    this.lastUpdatedCk = Long.parseLong(
        connector.getField("last_updated_ck"), 16);
  }
  
  public String getTableName() { return "Structure"; }
  
  public int getIdentity() { return getStructureID(); }
  
  public String getIdentityColumnName() { return "structure_id"; }
  
  public int getStructureID() { return this.structureID; }
  
  public void setStructureID(int structureID) { this.structureID = structureID; }
  
  public String getName() { return this.name; }
  
  public void setName(String name) {
    auditCheck("name", this.name, name);
    this.name = name;
  }
  
  public int getStructureType() { return this.structureType; }
  
  public void setStructureType(int structureType) { this.structureType = structureType; }
  
  public String getStructureAbbreviation() { return this.structureAbbreviation; }
  
  public void setStructureAbbreviation(String structureAbbreviation) {
    auditCheck("abbreviation", this.structureAbbreviation, structureAbbreviation);
    this.structureAbbreviation = structureAbbreviation;
  }
  
  public int getParentID() { return this.parentID; }
  
  public void setParentID(int parentID) { this.parentID = parentID; }
  
  public String getNotepadContentObjectId() { return Integer.toString(this.structureID); }
  
  public CorporateStructureObject getParent() { return null; }
  
  public void setParent(CorporateStructureObject cso) {}
  
  public Vector getChildren() { return null; }
  
  public void addChild(CorporateStructureObject cso) {}
  
  public long getLastUpdatedCk() { return this.lastUpdatedCk; }
  
  public void setLastUpdatedCk(long lastUpdatedCk) { this.lastUpdatedCk = lastUpdatedCk; }
  
  public int getLastUpdatingUser() { return this.lastUpdatingUser; }
  
  public void setLastUpdatingUser(int updatingUser) { this.lastUpdatingUser = updatingUser; }
  
  public Calendar getLastUpdateDate() { return this.lastUpdateDate; }
  
  public void setLastUpdateDate(Calendar lastUpdateDate) { this.lastUpdateDate = lastUpdateDate; }
  
  public int getArchimedesId() { return this.archimedesId; }
  
  public void setArchimedesId(int archimedesId) { this.archimedesId = archimedesId; }
  
  public boolean getIsUsed() { return this.isUsed; }
  
  public void setIsUsed(boolean isUsed) { this.isUsed = isUsed; }
  
  public String toString() {
    return "CSO [i" + this.structureID + 
      "; t" + this.structureType + 
      "; p" + this.parentID + 
      "; n:" + this.name + 
      "; a:" + this.structureAbbreviation + 
      "]";
  }
}


/* Location:              D:\Documents\NetBeansProjects\milestone2 Local.war!\WEB-INF\classes\co\\universal\milestone\CorporateStructureObject.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */