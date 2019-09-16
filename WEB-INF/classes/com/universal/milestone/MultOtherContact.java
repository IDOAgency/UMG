package WEB-INF.classes.com.universal.milestone;

import com.universal.milestone.MilestoneDataEntity;
import com.universal.milestone.MultOtherContact;
import java.util.Calendar;

public class MultOtherContact extends MilestoneDataEntity {
  protected int multOtherContactsPK = -1;
  
  protected int release_id = -1;
  
  protected String name = "";
  
  protected String description = "";
  
  protected int lastUpdatingUser;
  
  protected Calendar lastUpdateDate;
  
  protected long lastUpdatedCk;
  
  public String getTableName() { return "MultOtherContacts"; }
  
  public int getIdentity() { return getMultOtherContactsPK(); }
  
  public int getMultOtherContactsPK() { return this.multOtherContactsPK; }
  
  public void setMultOtherContactsPK(int Pk) { this.multOtherContactsPK = Pk; }
  
  public int getRealease_id() { return this.release_id; }
  
  public void setRelease_id(int release_id) { this.release_id = release_id; }
  
  public String getName() { return this.name; }
  
  public void setName(String name) { this.name = name; }
  
  public String getDescription() { return this.description; }
  
  public void setDescription(String description) { this.description = description; }
  
  public long getLastUpdatedCk() { return this.lastUpdatedCk; }
  
  public void setLastUpdatedCk(long lastUpdatedCk) { this.lastUpdatedCk = lastUpdatedCk; }
  
  public int getLastUpdatingUser() { return this.lastUpdatingUser; }
  
  public void setLastUpdatingUser(int updatingUser) { this.lastUpdatingUser = updatingUser; }
  
  public Calendar getLastUpdateDate() { return this.lastUpdateDate; }
  
  public void setLastUpdateDate(Calendar lastUpdateDate) { this.lastUpdateDate = lastUpdateDate; }
}


/* Location:              D:\Documents\NetBeansProjects\milestone2 Local.war!\WEB-INF\classes\co\\universal\milestone\MultOtherContact.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */