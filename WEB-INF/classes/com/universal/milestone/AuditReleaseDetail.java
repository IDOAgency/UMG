package WEB-INF.classes.com.universal.milestone;

import com.techempower.DataEntity;
import com.universal.milestone.AuditReleaseDetail;
import java.util.Calendar;

public class AuditReleaseDetail extends DataEntity {
  protected int release_id = -1;
  
  protected int task_id = -1;
  
  protected Calendar completion_date = null;
  
  public int getReleaseID() { return this.release_id; }
  
  public void setReleaseID(int id) { this.release_id = id; }
  
  public int getTaskID() { return this.task_id; }
  
  public void setTaskID(int id) { this.task_id = id; }
  
  public Calendar getCompletionDate() { return this.completion_date; }
  
  public void setCompletionDate(Calendar cd) { this.completion_date = cd; }
}


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\AuditReleaseDetail.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */