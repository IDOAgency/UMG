/*    */ package WEB-INF.classes.com.universal.milestone;
/*    */ 
/*    */ import com.universal.milestone.EmailDistributionDetail;
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
/*    */ public class EmailDistributionDetail
/*    */ {
/* 29 */   protected int distribtionId = -1;
/* 30 */   protected int structureId = -1;
/*    */   protected Calendar lastUpdatedOn;
/*    */   protected int lastUpdatedBy;
/* 33 */   protected long lastUpdatedCk = -1L;
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected boolean inactive = false;
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 43 */   public int getDistributionId() { return this.distribtionId; }
/*    */ 
/*    */ 
/*    */   
/* 47 */   public void setDistributionId(int distributionId) { this.distribtionId = distributionId; }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 52 */   public int getStructureId() { return this.structureId; }
/*    */ 
/*    */ 
/*    */   
/* 56 */   public void setStructureId(int structureId) { this.structureId = structureId; }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 61 */   public Calendar getLastUpdateOn() { return this.lastUpdatedOn; }
/*    */ 
/*    */ 
/*    */   
/* 65 */   public void setLastUpdatedOn(Calendar lastUpdatedOn) { this.lastUpdatedOn = lastUpdatedOn; }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 70 */   public int getLastUpdateBy() { return this.lastUpdatedBy; }
/*    */ 
/*    */ 
/*    */   
/* 74 */   public void setLastUpdatedBy(int lastUpdatedBy) { this.lastUpdatedBy = lastUpdatedBy; }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 79 */   public long getLastUpdateCk() { return this.lastUpdatedCk; }
/*    */ 
/*    */ 
/*    */   
/* 83 */   public void setLastUpdatedBy(long lastUpdatedCk) { this.lastUpdatedCk = lastUpdatedCk; }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 88 */   public boolean getInactive() { return this.inactive; }
/*    */ 
/*    */ 
/*    */   
/* 92 */   public void setInactive(boolean inactive) { this.inactive = inactive; }
/*    */ }


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\EmailDistributionDetail.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */