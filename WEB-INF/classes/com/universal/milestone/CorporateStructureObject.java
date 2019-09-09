/*     */ package WEB-INF.classes.com.universal.milestone;
/*     */ 
/*     */ import com.techempower.DatabaseConnector;
/*     */ import com.universal.milestone.CorporateStructureObject;
/*     */ import com.universal.milestone.MilestoneDataEntity;
/*     */ import com.universal.milestone.NotepadContentObject;
/*     */ import java.util.Calendar;
/*     */ import java.util.Hashtable;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class CorporateStructureObject
/*     */   extends MilestoneDataEntity
/*     */   implements NotepadContentObject
/*     */ {
/*  48 */   public int structureID = 0;
/*  49 */   public int structureType = 0;
/*  50 */   public int parentID = 0;
/*     */   
/*  52 */   public String name = "";
/*  53 */   public String structureAbbreviation = "";
/*     */ 
/*     */   
/*  56 */   public int lastUpdatingUser = 0;
/*  57 */   public Calendar lastUpdateDate = null;
/*  58 */   public long lastUpdatedCk = 0L;
/*     */   
/*  60 */   protected static Hashtable bindings = null;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  65 */   public int archimedesId = -1;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isUsed = false;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Hashtable getCustomVariableBindings() {
/*  81 */     if (bindings == null) {
/*     */       
/*  83 */       bindings = new Hashtable();
/*  84 */       bindings.put("structure_id", "structureID");
/*  85 */       bindings.put("parent_id", "parentID");
/*  86 */       bindings.put("type", "structureType");
/*  87 */       bindings.put("name", "name");
/*  88 */       bindings.put("abbreviation", "structureAbbreviation");
/*  89 */       bindings.put("last_updated_on", "lastUpdateDate");
/*  90 */       bindings.put("last_updated_by", "lastUpdatingUser");
/*  91 */       bindings.put("archimedes_id", "archimedes_id");
/*     */     } 
/*  93 */     return bindings;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void customInitialization(DatabaseConnector connector) {
/* 101 */     this.lastUpdatedCk = Long.parseLong(
/* 102 */         connector.getField("last_updated_ck"), 16);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 111 */   public String getTableName() { return "Structure"; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 121 */   public int getIdentity() { return getStructureID(); }
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
/* 135 */   public String getIdentityColumnName() { return "structure_id"; }
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
/* 149 */   public int getStructureID() { return this.structureID; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 159 */   public void setStructureID(int structureID) { this.structureID = structureID; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 169 */   public String getName() { return this.name; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setName(String name) {
/* 179 */     auditCheck("name", this.name, name);
/* 180 */     this.name = name;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 190 */   public int getStructureType() { return this.structureType; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 200 */   public void setStructureType(int structureType) { this.structureType = structureType; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 210 */   public String getStructureAbbreviation() { return this.structureAbbreviation; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setStructureAbbreviation(String structureAbbreviation) {
/* 220 */     auditCheck("abbreviation", this.structureAbbreviation, structureAbbreviation);
/* 221 */     this.structureAbbreviation = structureAbbreviation;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 231 */   public int getParentID() { return this.parentID; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 241 */   public void setParentID(int parentID) { this.parentID = parentID; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 249 */   public String getNotepadContentObjectId() { return Integer.toString(this.structureID); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 257 */   public CorporateStructureObject getParent() { return null; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setParent(CorporateStructureObject cso) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 274 */   public Vector getChildren() { return null; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addChild(CorporateStructureObject cso) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 292 */   public long getLastUpdatedCk() { return this.lastUpdatedCk; }
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
/* 303 */   public void setLastUpdatedCk(long lastUpdatedCk) { this.lastUpdatedCk = lastUpdatedCk; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 313 */   public int getLastUpdatingUser() { return this.lastUpdatingUser; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 323 */   public void setLastUpdatingUser(int updatingUser) { this.lastUpdatingUser = updatingUser; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 333 */   public Calendar getLastUpdateDate() { return this.lastUpdateDate; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 343 */   public void setLastUpdateDate(Calendar lastUpdateDate) { this.lastUpdateDate = lastUpdateDate; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 352 */   public int getArchimedesId() { return this.archimedesId; }
/*     */ 
/*     */ 
/*     */   
/* 356 */   public void setArchimedesId(int archimedesId) { this.archimedesId = archimedesId; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 365 */   public boolean getIsUsed() { return this.isUsed; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 370 */   public void setIsUsed(boolean isUsed) { this.isUsed = isUsed; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 378 */     return "CSO [i" + this.structureID + 
/* 379 */       "; t" + this.structureType + 
/* 380 */       "; p" + this.parentID + 
/* 381 */       "; n:" + this.name + 
/* 382 */       "; a:" + this.structureAbbreviation + 
/* 383 */       "]";
/*     */   }
/*     */ }


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\CorporateStructureObject.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */