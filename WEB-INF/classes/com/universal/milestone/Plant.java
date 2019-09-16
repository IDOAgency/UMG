package WEB-INF.classes.com.universal.milestone;

import com.universal.milestone.LookupObject;
import com.universal.milestone.MilestoneDataEntity;
import com.universal.milestone.NotepadContentObject;
import com.universal.milestone.Plant;
import java.util.Calendar;

public class Plant extends MilestoneDataEntity implements NotepadContentObject {
  protected int plantID = -1;
  
  protected String selectionID = null;
  
  protected int releaseID = -1;
  
  protected LookupObject plant = null;
  
  protected int orderQty = -1;
  
  protected int completedQty = -1;
  
  protected int lastUpdatingUser;
  
  protected Calendar lastUpdateDate;
  
  protected long lastUpdatedCk;
  
  public String getTableName() { return "Manufacturing_Plants"; }
  
  public int getIdentity() { return getPlantID(); }
  
  public int getPlantID() { return this.plantID; }
  
  public void setPlantID(int id) { this.plantID = id; }
  
  public String getSelectionID() { return this.selectionID; }
  
  public int getReleaseID() { return this.releaseID; }
  
  public void setSelectionID(String id) { this.selectionID = id; }
  
  public void setReleaseID(int id) { this.releaseID = id; }
  
  public LookupObject getPlant() { return this.plant; }
  
  public void setPlant(LookupObject plant) { this.plant = plant; }
  
  public int getOrderQty() { return this.orderQty; }
  
  public void setOrderQty(int orderQty) { this.orderQty = orderQty; }
  
  public int getCompletedQty() { return this.completedQty; }
  
  public void setCompletedQty(int completedQty) { this.completedQty = completedQty; }
  
  public long getLastUpdatedCk() { return this.lastUpdatedCk; }
  
  public void setLastUpdatedCk(long lastUpdatedCk) { this.lastUpdatedCk = lastUpdatedCk; }
  
  public int getLastUpdatingUser() { return this.lastUpdatingUser; }
  
  public void setLastUpdatingUser(int updatingUser) { this.lastUpdatingUser = updatingUser; }
  
  public Calendar getLastUpdateDate() { return this.lastUpdateDate; }
  
  public void setLastUpdateDate(Calendar lastUpdateDate) { this.lastUpdateDate = lastUpdateDate; }
  
  public String getNotepadContentObjectId() { return Integer.toString(this.plantID); }
}


/* Location:              D:\Documents\NetBeansProjects\milestone2 Local.war!\WEB-INF\classes\co\\universal\milestone\Plant.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */