/*    */ package WEB-INF.classes.com.universal.milestone;
/*    */ 
/*    */ import com.techempower.DataEntity;
/*    */ import com.universal.milestone.AuditItem;
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
/*    */ 
/*    */ public class AuditItem
/*    */   extends DataEntity
/*    */ {
/* 31 */   public int auditItemID = 0;
/* 32 */   public int auditTableID = 0;
/* 33 */   public String auditField = "";
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 40 */   public int getAuditItemID() { return this.auditItemID; }
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
/* 54 */   public int getIdentity() { return this.auditItemID; }
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
/* 69 */   public String getTableName() { return "AuditItem"; }
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
/* 82 */   public String getIdentityColumnName() { return "AuditItemID"; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String toString() {
/* 90 */     return "AuditItem [" + this.auditItemID + 
/* 91 */       "; " + this.auditTableID + 
/* 92 */       "; " + this.auditField + 
/* 93 */       "]";
/*    */   }
/*    */ }


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\AuditItem.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */