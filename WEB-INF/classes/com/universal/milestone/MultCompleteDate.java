/*     */ package WEB-INF.classes.com.universal.milestone;
/*     */ 
/*     */ import com.techempower.DataEntity;
/*     */ import com.universal.milestone.MultCompleteDate;
/*     */ import java.util.Calendar;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MultCompleteDate
/*     */   extends DataEntity
/*     */ {
/*  29 */   protected int release_id = -1;
/*  30 */   protected int task_id = -1;
/*  31 */   protected int order_no = -1;
/*  32 */   protected Calendar completion_date = null;
/*  33 */   protected Calendar last_updated_on = null;
/*  34 */   protected int last_updated_by = -1;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  45 */   public int getReleaseID() { return this.release_id; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  50 */   public void setReleaseID(int pReleaseId) { this.release_id = pReleaseId; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  55 */   public int getTaskID() { return this.task_id; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  60 */   public void setTaskID(int pTaskId) { this.task_id = pTaskId; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  65 */   public int getOrderNo() { return this.order_no; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  70 */   public void setOrderNo(int pOrderNo) { this.order_no = pOrderNo; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  75 */   public Calendar getCompletionDate() { return this.completion_date; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  80 */   public void setCompletionDate(Calendar pCompletionDate) { this.completion_date = pCompletionDate; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  85 */   public Calendar getLastUpdatedOn() { return this.last_updated_on; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  90 */   public void setLastUpdatedOn(Calendar pLastUpdatedOn) { this.last_updated_on = pLastUpdatedOn; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  95 */   public int getLastUpdatedBy() { return this.last_updated_by; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 100 */   public void setLastUpdatedBy(int pLastUpdatedBy) { this.last_updated_by = pLastUpdatedBy; }
/*     */ }


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\MultCompleteDate.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */