package WEB-INF.classes.com.universal.milestone;

import com.universal.milestone.MilestoneDataEntity;
import com.universal.milestone.MultSelection;
import java.util.Calendar;

public class MultSelection extends MilestoneDataEntity {
  protected int multSelectionsPK = -1;
  
  protected int release_id = -1;
  
  protected String selectionNo = "";
  
  protected String upc = "";
  
  protected String description = "";
  
  protected int lastUpdatingUser;
  
  protected Calendar lastUpdateDate;
  
  protected long lastUpdatedCk;
  
  public String getTableName() { return "MultSelections"; }
  
  public int getIdentity() { return getMultSelectionsPK(); }
  
  public int getMultSelectionsPK() { return this.multSelectionsPK; }
  
  public void setMultSelectionPK(int Pk) { this.multSelectionsPK = Pk; }
  
  public int getRealease_id() { return this.release_id; }
  
  public void setRelease_id(int release_id) { this.release_id = release_id; }
  
  public String getSelectionNo() { return this.selectionNo; }
  
  public void setSelectionNo(String selectionNo) { this.selectionNo = selectionNo; }
  
  public String getUpc() { return this.upc; }
  
  public void setUpc(String upc) { this.upc = upc; }
  
  public String getDescription() { return this.description; }
  
  public void setDescription(String description) { this.description = description; }
  
  public long getLastUpdatedCk() { return this.lastUpdatedCk; }
  
  public void setLastUpdatedCk(long lastUpdatedCk) { this.lastUpdatedCk = lastUpdatedCk; }
  
  public int getLastUpdatingUser() { return this.lastUpdatingUser; }
  
  public void setLastUpdatingUser(int updatingUser) { this.lastUpdatingUser = updatingUser; }
  
  public Calendar getLastUpdateDate() { return this.lastUpdateDate; }
  
  public void setLastUpdateDate(Calendar lastUpdateDate) { this.lastUpdateDate = lastUpdateDate; }
}


/* Location:              D:\Documents\NetBeansProjects\milestone2 Local.war!\WEB-INF\classes\co\\universal\milestone\MultSelection.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */