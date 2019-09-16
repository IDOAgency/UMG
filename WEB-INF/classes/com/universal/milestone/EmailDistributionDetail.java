package WEB-INF.classes.com.universal.milestone;

import com.universal.milestone.EmailDistributionDetail;
import java.util.Calendar;

public class EmailDistributionDetail {
  protected int distribtionId = -1;
  
  protected int structureId = -1;
  
  protected Calendar lastUpdatedOn;
  
  protected int lastUpdatedBy;
  
  protected long lastUpdatedCk = -1L;
  
  protected boolean inactive = false;
  
  public int getDistributionId() { return this.distribtionId; }
  
  public void setDistributionId(int distributionId) { this.distribtionId = distributionId; }
  
  public int getStructureId() { return this.structureId; }
  
  public void setStructureId(int structureId) { this.structureId = structureId; }
  
  public Calendar getLastUpdateOn() { return this.lastUpdatedOn; }
  
  public void setLastUpdatedOn(Calendar lastUpdatedOn) { this.lastUpdatedOn = lastUpdatedOn; }
  
  public int getLastUpdateBy() { return this.lastUpdatedBy; }
  
  public void setLastUpdatedBy(int lastUpdatedBy) { this.lastUpdatedBy = lastUpdatedBy; }
  
  public long getLastUpdateCk() { return this.lastUpdatedCk; }
  
  public void setLastUpdatedBy(long lastUpdatedCk) { this.lastUpdatedCk = lastUpdatedCk; }
  
  public boolean getInactive() { return this.inactive; }
  
  public void setInactive(boolean inactive) { this.inactive = inactive; }
}


/* Location:              D:\Documents\NetBeansProjects\milestone2 Local.war!\WEB-INF\classes\co\\universal\milestone\EmailDistributionDetail.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */