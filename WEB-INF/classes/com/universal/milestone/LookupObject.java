package WEB-INF.classes.com.universal.milestone;

import com.universal.milestone.LookupObject;
import com.universal.milestone.MilestoneDataEntity;
import com.universal.milestone.NotepadContentObject;
import java.util.Calendar;

public class LookupObject extends MilestoneDataEntity implements NotepadContentObject {
  public int lookupObjectID;
  
  public String abbreviation;
  
  public String name;
  
  public String subValue;
  
  public int id;
  
  protected int lastUpdatingUser;
  
  protected Calendar lastUpdateDate;
  
  protected long lastUpdatedCk;
  
  protected boolean inactive;
  
  protected int prodType;
  
  protected boolean isDigitalEquivalent;
  
  public String getTableName() { return "Lookup_Header"; }
  
  public int getIdentity() { return getId(); }
  
  public LookupObject(String abbreviation, String name) {
    this.lookupObjectID = 0;
    this.abbreviation = null;
    this.name = null;
    this.subValue = null;
    this.id = 0;
    this.inactive = false;
    this.isDigitalEquivalent = false;
    this.abbreviation = abbreviation;
    this.name = name;
  }
  
  public LookupObject(String abbreviation) {
    this.lookupObjectID = 0;
    this.abbreviation = null;
    this.name = null;
    this.subValue = null;
    this.id = 0;
    this.inactive = false;
    this.isDigitalEquivalent = false;
    this.abbreviation = abbreviation;
  }
  
  public LookupObject() {
    this.lookupObjectID = 0;
    this.abbreviation = null;
    this.name = null;
    this.subValue = null;
    this.id = 0;
    this.inactive = false;
    this.isDigitalEquivalent = false;
  }
  
  public int getId() { return this.id; }
  
  public void setId(int id) { this.id = id; }
  
  public String getName() { return this.name; }
  
  public void setName(String name) {
    auditCheck("name", this.name, name);
    this.name = name;
  }
  
  public String getSubValue() { return this.subValue; }
  
  public void setSubValue(String subValue) { this.subValue = subValue; }
  
  public String getAbbreviation() { return this.abbreviation; }
  
  public void setAbbreviation(String abbreviation) {
    auditCheck("abbreviation", this.abbreviation, abbreviation);
    this.abbreviation = abbreviation;
  }
  
  public long getLastUpdatedCk() { return this.lastUpdatedCk; }
  
  public void setLastUpdatedCk(long lastUpdatedCk) { this.lastUpdatedCk = lastUpdatedCk; }
  
  public int getLastUpdatingUser() { return this.lastUpdatingUser; }
  
  public void setLastUpdatingUser(int updatingUser) { this.lastUpdatingUser = updatingUser; }
  
  public Calendar getLastUpdateDate() { return this.lastUpdateDate; }
  
  public void setLastUpdateDate(Calendar lastUpdateDate) { this.lastUpdateDate = lastUpdateDate; }
  
  public String getNotepadContentObjectId() { return this.abbreviation; }
  
  public boolean getInactive() { return this.inactive; }
  
  public void setInactive(boolean inactive) { this.inactive = inactive; }
  
  public int getProdType() { return this.prodType; }
  
  public void setProdType(int prodType) { this.prodType = prodType; }
  
  public boolean getIsDigitalEquivalent() { return this.isDigitalEquivalent; }
  
  public void setIsDigitalEquivalent(boolean isDigitalEquivalent) { this.isDigitalEquivalent = isDigitalEquivalent; }
}


/* Location:              D:\Documents\NetBeansProjects\milestone2 Local.war!\WEB-INF\classes\co\\universal\milestone\LookupObject.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */