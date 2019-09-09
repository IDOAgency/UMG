/*     */ package WEB-INF.classes.com.universal.milestone;
/*     */ 
/*     */ import com.techempower.DataEntity;
/*     */ import com.universal.milestone.Audit;
/*     */ import com.universal.milestone.AuditItem;
/*     */ import java.util.Date;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Audit
/*     */   extends DataEntity
/*     */ {
/*     */   public static final int VALUE_LENGTH = 20;
/*     */   public static final int AUDIT_NEW = 1;
/*     */   public static final int AUDIT_UPDATE = 2;
/*  40 */   public int auditID = 0;
/*  41 */   public int auditEntityID = 0;
/*  42 */   public int auditItemID = 0;
/*  43 */   public int auditType = 2;
/*  44 */   public AuditItem auditItem = null;
/*  45 */   public Date auditTimestamp = new Date();
/*  46 */   public String oldValue = "";
/*  47 */   public String newValue = "";
/*  48 */   public int userID = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Audit() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Audit(AuditItem auditItem, int auditEntityID, int auditType, Date timestamp, String oldValue, String newValue, int userID) {
/*  64 */     this.auditItemID = auditItem.getAuditItemID();
/*  65 */     this.auditEntityID = auditEntityID;
/*  66 */     this.auditTimestamp = timestamp;
/*  67 */     this.userID = userID;
/*  68 */     this.auditType = auditType;
/*  69 */     setOldValue(oldValue);
/*  70 */     setNewValue(newValue);
/*     */   }
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
/*  83 */   public Audit(AuditItem auditItem, int auditEntityID, int auditType, Date timestamp, String oldValue, String newValue) { this(auditItem, auditEntityID, auditType, timestamp, oldValue, newValue, 0); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setOldValue(String oldValue) {
/*  91 */     if (oldValue != null && oldValue.length() > 20) {
/*  92 */       this.oldValue = oldValue.substring(0, 20);
/*     */     } else {
/*  94 */       this.oldValue = oldValue;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setNewValue(String newValue) {
/* 102 */     if (newValue != null && newValue.length() > 20) {
/* 103 */       this.newValue = newValue.substring(0, 20);
/*     */     } else {
/* 105 */       this.newValue = newValue;
/*     */     } 
/*     */   }
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
/* 119 */   public int getIdentity() { return this.auditID; }
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
/* 134 */   public String getTableName() { return "Audit"; }
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
/* 147 */   public String getIdentityColumnName() { return "AuditID"; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 155 */     return "Audit [a" + this.auditID + 
/* 156 */       "; i" + this.auditItemID + 
/* 157 */       "; t" + this.auditType + 
/* 158 */       "; " + this.newValue + 
/* 159 */       "; u" + this.userID + 
/* 160 */       "]";
/*     */   }
/*     */ }


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\Audit.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */