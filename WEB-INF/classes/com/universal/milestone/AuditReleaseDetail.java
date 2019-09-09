/*    */ package WEB-INF.classes.com.universal.milestone;
/*    */ 
/*    */ import com.techempower.DataEntity;
/*    */ import com.universal.milestone.AuditReleaseDetail;
/*    */ import java.util.Calendar;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class AuditReleaseDetail
/*    */   extends DataEntity
/*    */ {
/* 31 */   protected int release_id = -1;
/* 32 */   protected int task_id = -1;
/* 33 */   protected Calendar completion_date = null;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 41 */   public int getReleaseID() { return this.release_id; }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 46 */   public void setReleaseID(int id) { this.release_id = id; }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 51 */   public int getTaskID() { return this.task_id; }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 56 */   public void setTaskID(int id) { this.task_id = id; }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 61 */   public Calendar getCompletionDate() { return this.completion_date; }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 66 */   public void setCompletionDate(Calendar cd) { this.completion_date = cd; }
/*    */ }


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\AuditReleaseDetail.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */