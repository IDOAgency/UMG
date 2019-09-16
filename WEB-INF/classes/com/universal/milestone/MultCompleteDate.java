package WEB-INF.classes.com.universal.milestone;

import com.techempower.DataEntity;
import com.universal.milestone.MultCompleteDate;
import java.util.Calendar;

public class MultCompleteDate extends DataEntity {
  protected int release_id = -1;
  
  protected int task_id = -1;
  
  protected int order_no = -1;
  
  protected Calendar completion_date = null;
  
  protected Calendar last_updated_on = null;
  
  protected int last_updated_by = -1;
  
  public int getReleaseID() { return this.release_id; }
  
  public void setReleaseID(int pReleaseId) { this.release_id = pReleaseId; }
  
  public int getTaskID() { return this.task_id; }
  
  public void setTaskID(int pTaskId) { this.task_id = pTaskId; }
  
  public int getOrderNo() { return this.order_no; }
  
  public void setOrderNo(int pOrderNo) { this.order_no = pOrderNo; }
  
  public Calendar getCompletionDate() { return this.completion_date; }
  
  public void setCompletionDate(Calendar pCompletionDate) { this.completion_date = pCompletionDate; }
  
  public Calendar getLastUpdatedOn() { return this.last_updated_on; }
  
  public void setLastUpdatedOn(Calendar pLastUpdatedOn) { this.last_updated_on = pLastUpdatedOn; }
  
  public int getLastUpdatedBy() { return this.last_updated_by; }
  
  public void setLastUpdatedBy(int pLastUpdatedBy) { this.last_updated_by = pLastUpdatedBy; }
}


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\MultCompleteDate.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */