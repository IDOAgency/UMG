/*     */ package WEB-INF.classes.com.universal.milestone;
/*     */ 
/*     */ import com.techempower.ComponentLog;
/*     */ import com.techempower.DataEntity;
/*     */ import com.techempower.DatabaseConnector;
/*     */ import com.techempower.EnhancedProperties;
/*     */ import com.techempower.gemini.GeminiApplication;
/*     */ import com.universal.milestone.Audit;
/*     */ import com.universal.milestone.AuditItem;
/*     */ import com.universal.milestone.AuditTable;
/*     */ import com.universal.milestone.JdbcConnector;
/*     */ import com.universal.milestone.MilestoneDataEntity;
/*     */ import com.universal.milestone.MilestoneHelper;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.Calendar;
/*     */ import java.util.Date;
/*     */ import java.util.Hashtable;
/*     */ import java.util.Iterator;
/*     */ import java.util.Vector;
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
/*     */ public abstract class MilestoneDataEntity
/*     */   extends DataEntity
/*     */ {
/*     */   public static final String COMPONENT_CODE = "msDE";
/*     */   protected static boolean auditingEnabled = true;
/*  51 */   protected static Hashtable classToFields = new Hashtable();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  57 */   protected static Hashtable newEntityAudits = new Hashtable();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static ComponentLog log;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  69 */   protected static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Vector newAudits;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void configure(EnhancedProperties props, GeminiApplication application) {
/*  82 */     log = application.getLog("msDE");
/*     */ 
/*     */     
/*  85 */     auditingEnabled = props.getYesNoProperty("AuditingEnabled", auditingEnabled);
/*     */ 
/*     */     
/*  88 */     loadAuditItems();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static void loadAuditItems() {
/*  96 */     classesAudited = 0;
/*  97 */     int fieldsAudited = 0;
/*  98 */     int newEntities = 0;
/*     */ 
/*     */     
/* 101 */     if (auditingEnabled) {
/*     */ 
/*     */       
/*     */       try {
/*     */ 
/*     */         
/* 107 */         JdbcConnector jdbcConnector = MilestoneHelper.getConnector("SELECT * FROM AuditTable;");
/* 108 */         jdbcConnector.runQuery();
/*     */         
/* 110 */         Vector results = MilestoneHelper.getDataEntities(
/* 111 */             Class.forName("com.universal.milestone.AuditTable"), 
/* 112 */             jdbcConnector);
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 117 */         for (int i = 0; i < results.size(); i++) {
/*     */           
/* 119 */           AuditTable auditTable = (AuditTable)results.elementAt(i);
/*     */ 
/*     */ 
/*     */           
/*     */           try {
/* 124 */             Class dataEntityClass = Class.forName("com.universal.milestone." + 
/* 125 */                 auditTable.className);
/*     */             
/* 127 */             Hashtable auditItems = new Hashtable();
/* 128 */             classToFields.put(dataEntityClass, auditItems);
/* 129 */             classesAudited++;
/*     */ 
/*     */ 
/*     */             
/* 133 */             JdbcConnector jdbcConnector1 = MilestoneHelper.getConnector(
/* 134 */                 "SELECT * FROM AuditItem WHERE AuditEnabled = 'T' AND AuditTableID = " + 
/* 135 */                 auditTable.auditTableID + ";");
/* 136 */             jdbcConnector1.runQuery();
/* 137 */             Vector fields = MilestoneHelper.getDataEntities(
/* 138 */                 Class.forName("com.universal.milestone.AuditItem"), 
/* 139 */                 jdbcConnector1);
/*     */ 
/*     */ 
/*     */             
/* 143 */             for (int field = 0; field < fields.size(); field++) {
/*     */               
/* 145 */               AuditItem auditItem = (AuditItem)fields.elementAt(field);
/*     */ 
/*     */               
/* 148 */               if (auditItem.auditField != null)
/*     */               {
/* 150 */                 auditItems.put(auditItem.auditField, auditItem);
/* 151 */                 fieldsAudited++;
/*     */               
/*     */               }
/*     */               else
/*     */               {
/* 156 */                 newEntityAudits.put(dataEntityClass, auditItem);
/* 157 */                 newEntities++;
/*     */               }
/*     */             
/*     */             } 
/* 161 */           } catch (ClassNotFoundException cnfexc) {
/*     */ 
/*     */ 
/*     */             
/* 165 */             log.debug("Class-to-audit not found: com.universal.milestone." + 
/*     */                 
/* 167 */                 auditTable.className);
/*     */           }
/*     */         
/*     */         } 
/* 171 */       } catch (Exception exc) {
/*     */ 
/*     */ 
/*     */         
/* 175 */         log.log("Exception while building audit lookups: " + exc);
/*     */       } 
/*     */       
/* 178 */       log.debug(String.valueOf(classesAudited) + " classes (" + fieldsAudited + " fields) to be audited.");
/* 179 */       log.debug(String.valueOf(newEntities) + " new entity audits.");
/*     */     
/*     */     }
/*     */     else {
/*     */ 
/*     */       
/* 185 */       log.debug("Auditing is disabled.");
/* 186 */       classToFields.clear();
/* 187 */       newEntityAudits.clear();
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
/*     */ 
/*     */   
/*     */   protected boolean individualAuditing = true;
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
/*     */   public Iterator getAudits() {
/* 218 */     if (this.newAudits != null) {
/* 219 */       return this.newAudits.iterator();
/*     */     }
/* 221 */     return null;
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
/*     */   public abstract String getTableName();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract int getIdentity();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 253 */   public boolean isNew() { return (getIdentity() <= 0); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 263 */   public void setAuditingEnabled(boolean enabled) { this.individualAuditing = enabled; }
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
/*     */   public boolean flushAudits(int userID) {
/* 275 */     if (auditingEnabled) {
/*     */ 
/*     */ 
/*     */       
/* 279 */       if (this.newAudits != null) {
/*     */ 
/*     */ 
/*     */         
/* 283 */         JdbcConnector jdbcConnector = MilestoneHelper.getConnector("");
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 288 */         for (int i = 0; i < this.newAudits.size(); i++) {
/*     */           
/* 290 */           Audit audit = (Audit)this.newAudits.elementAt(i);
/* 291 */           if (audit.userID == 0) {
/* 292 */             audit.userID = userID;
/*     */           }
/* 294 */           audit.updateDatabase(jdbcConnector);
/*     */         } 
/*     */ 
/*     */         
/* 298 */         eraseAudits();
/*     */         
/* 300 */         return true;
/*     */       } 
/*     */ 
/*     */       
/* 304 */       if (isNew() && this.individualAuditing) {
/*     */ 
/*     */         
/* 307 */         AuditItem auditItem = (AuditItem)newEntityAudits.get(getClass());
/*     */ 
/*     */         
/* 310 */         if (auditItem != null) {
/*     */           
/* 312 */           log.debug(auditItem.toString());
/*     */           
/* 314 */           Audit audit = new Audit(auditItem, 0, 1, 
/* 315 */               new Date(), "", "", userID);
/*     */ 
/*     */ 
/*     */           
/* 319 */           JdbcConnector jdbcConnector = MilestoneHelper.getConnector("");
/* 320 */           audit.updateDatabase(jdbcConnector);
/*     */           
/* 322 */           return true;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 334 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 342 */   public void eraseAudits() { this.newAudits = null; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int updateDatabase(DatabaseConnector connector, int userID) {
/* 351 */     flushAudits(userID);
/* 352 */     return updateDatabase(connector);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void addAudit(Audit audit) {
/* 361 */     if (this.newAudits == null) {
/* 362 */       this.newAudits = new Vector();
/*     */     }
/* 364 */     this.newAudits.addElement(audit);
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
/*     */   public void auditCheck(String fieldName, String oldValue, String newValue, int userID) {
/* 376 */     if (newValue == null)
/* 377 */       newValue = ""; 
/* 378 */     if (oldValue == null) {
/* 379 */       oldValue = "";
/*     */     }
/* 381 */     if (!newValue.equals(oldValue)) {
/*     */       
/* 383 */       AuditItem auditItem = getAuditItem(fieldName);
/*     */       
/* 385 */       if (auditItem != null) {
/*     */ 
/*     */ 
/*     */         
/* 389 */         Audit audit = new Audit(auditItem, getIdentity(), 
/* 390 */             2, new Date(), oldValue, newValue, 
/* 391 */             userID);
/* 392 */         addAudit(audit);
/*     */       } 
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
/* 404 */   public void auditCheck(String fieldName, String oldValue, String newValue) { auditCheck(fieldName, oldValue, newValue, 0); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void auditCheck(String fieldName, Date oldValue, Date newValue, int userID) {
/* 413 */     String oldValueString = "";
/* 414 */     String newValueString = "";
/*     */     
/* 416 */     if (oldValue != null)
/* 417 */       oldValueString = simpleDateFormat.format(oldValue); 
/* 418 */     if (newValue != null) {
/* 419 */       newValueString = simpleDateFormat.format(newValue);
/*     */     }
/* 421 */     auditCheck(fieldName, oldValueString, newValueString, userID);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 431 */   public void auditCheck(String fieldName, Date oldValue, Date newValue) { auditCheck(fieldName, oldValue, newValue, 0); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void auditCheck(String fieldName, Calendar oldValue, Calendar newValue, int userID) {
/* 440 */     String oldValueString = "";
/* 441 */     String newValueString = "";
/*     */     
/* 443 */     if (oldValue != null)
/* 444 */       oldValueString = simpleDateFormat.format(oldValue.getTime()); 
/* 445 */     if (newValue != null) {
/* 446 */       newValueString = simpleDateFormat.format(newValue.getTime());
/*     */     }
/* 448 */     auditCheck(fieldName, oldValueString, newValueString, userID);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 458 */   public void auditCheck(String fieldName, Calendar oldValue, Calendar newValue) { auditCheck(fieldName, oldValue, newValue, 0); }
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
/*     */   public void auditCheck(String fieldName, int oldValue, int newValue, int userID) { // Byte code:
/*     */     //   0: iload_2
/*     */     //   1: iload_3
/*     */     //   2: if_icmpeq -> 76
/*     */     //   5: aload_0
/*     */     //   6: aload_1
/*     */     //   7: invokevirtual getAuditItem : (Ljava/lang/String;)Lcom/universal/milestone/AuditItem;
/*     */     //   10: astore #5
/*     */     //   12: aload #5
/*     */     //   14: ifnull -> 76
/*     */     //   17: new com/universal/milestone/Audit
/*     */     //   20: dup
/*     */     //   21: aload #5
/*     */     //   23: aload_0
/*     */     //   24: invokevirtual getIdentity : ()I
/*     */     //   27: iconst_2
/*     */     //   28: new java/util/Date
/*     */     //   31: dup
/*     */     //   32: invokespecial <init> : ()V
/*     */     //   35: new java/lang/StringBuilder
/*     */     //   38: dup
/*     */     //   39: invokespecial <init> : ()V
/*     */     //   42: iload_2
/*     */     //   43: invokevirtual append : (I)Ljava/lang/StringBuilder;
/*     */     //   46: invokevirtual toString : ()Ljava/lang/String;
/*     */     //   49: new java/lang/StringBuilder
/*     */     //   52: dup
/*     */     //   53: invokespecial <init> : ()V
/*     */     //   56: iload_3
/*     */     //   57: invokevirtual append : (I)Ljava/lang/StringBuilder;
/*     */     //   60: invokevirtual toString : ()Ljava/lang/String;
/*     */     //   63: iload #4
/*     */     //   65: invokespecial <init> : (Lcom/universal/milestone/AuditItem;IILjava/util/Date;Ljava/lang/String;Ljava/lang/String;I)V
/*     */     //   68: astore #6
/*     */     //   70: aload_0
/*     */     //   71: aload #6
/*     */     //   73: invokevirtual addAudit : (Lcom/universal/milestone/Audit;)V
/*     */     //   76: return
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #469	-> 0
/*     */     //   #471	-> 5
/*     */     //   #473	-> 12
/*     */     //   #476	-> 17
/*     */     //   #477	-> 27
/*     */     //   #478	-> 63
/*     */     //   #476	-> 65
/*     */     //   #479	-> 70
/*     */     //   #482	-> 76
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   0	77	0	this	Lcom/universal/milestone/MilestoneDataEntity;
/*     */     //   0	77	1	fieldName	Ljava/lang/String;
/*     */     //   0	77	2	oldValue	I
/*     */     //   0	77	3	newValue	I
/*     */     //   0	77	4	userID	I
/*     */     //   12	64	5	auditItem	Lcom/universal/milestone/AuditItem;
/*     */     //   70	6	6	audit	Lcom/universal/milestone/Audit; }
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
/* 491 */   public void auditCheck(String fieldName, int oldValue, int newValue) { auditCheck(fieldName, oldValue, newValue, 0); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected AuditItem getAuditItem(String fieldName) {
/* 500 */     AuditItem auditItem = null;
/*     */ 
/*     */     
/* 503 */     if (auditingEnabled && this.individualAuditing)
/*     */     {
/*     */       
/* 506 */       if (!isNew()) {
/*     */         
/* 508 */         Hashtable classFields = (Hashtable)classToFields.get(getClass());
/*     */ 
/*     */ 
/*     */         
/* 512 */         if (classFields != null)
/*     */         {
/* 514 */           auditItem = (AuditItem)classFields.get(fieldName);
/*     */         }
/*     */       } 
/*     */     }
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
/* 533 */     return auditItem;
/*     */   }
/*     */ }


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\MilestoneDataEntity.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */