/*     */ package WEB-INF.classes.com.universal.milestone;
/*     */ 
/*     */ import com.universal.milestone.LookupObject;
/*     */ import com.universal.milestone.MilestoneDataEntity;
/*     */ import com.universal.milestone.NotepadContentObject;
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
/*     */ public class LookupObject
/*     */   extends MilestoneDataEntity
/*     */   implements NotepadContentObject
/*     */ {
/*     */   public int lookupObjectID;
/*     */   public String abbreviation;
/*     */   public String name;
/*     */   public String subValue;
/*     */   public int id;
/*     */   protected int lastUpdatingUser;
/*     */   protected Calendar lastUpdateDate;
/*     */   protected long lastUpdatedCk;
/*     */   protected boolean inactive;
/*     */   protected int prodType;
/*     */   protected boolean isDigitalEquivalent;
/*     */   
/*  64 */   public String getTableName() { return "Lookup_Header"; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  74 */   public int getIdentity() { return getId(); }
/*     */ 
/*     */ 
/*     */   
/*     */   public LookupObject(String abbreviation, String name) {
/*     */     this.lookupObjectID = 0;
/*     */     this.abbreviation = null;
/*     */     this.name = null;
/*     */     this.subValue = null;
/*     */     this.id = 0;
/*     */     this.inactive = false;
/*     */     this.isDigitalEquivalent = false;
/*  86 */     this.abbreviation = abbreviation;
/*  87 */     this.name = name; } public LookupObject(String abbreviation) {
/*     */     this.lookupObjectID = 0;
/*     */     this.abbreviation = null;
/*     */     this.name = null;
/*     */     this.subValue = null;
/*     */     this.id = 0;
/*     */     this.inactive = false;
/*     */     this.isDigitalEquivalent = false;
/*  95 */     this.abbreviation = abbreviation;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LookupObject() {
/*     */     this.lookupObjectID = 0;
/*     */     this.abbreviation = null;
/*     */     this.name = null;
/*     */     this.subValue = null;
/*     */     this.id = 0;
/*     */     this.inactive = false;
/*     */     this.isDigitalEquivalent = false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 113 */   public int getId() { return this.id; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 123 */   public void setId(int id) { this.id = id; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 133 */   public String getName() { return this.name; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setName(String name) {
/* 143 */     auditCheck("name", this.name, name);
/* 144 */     this.name = name;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 154 */   public String getSubValue() { return this.subValue; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 164 */   public void setSubValue(String subValue) { this.subValue = subValue; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 174 */   public String getAbbreviation() { return this.abbreviation; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setAbbreviation(String abbreviation) {
/* 184 */     auditCheck("abbreviation", this.abbreviation, abbreviation);
/* 185 */     this.abbreviation = abbreviation;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 195 */   public long getLastUpdatedCk() { return this.lastUpdatedCk; }
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
/* 206 */   public void setLastUpdatedCk(long lastUpdatedCk) { this.lastUpdatedCk = lastUpdatedCk; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 216 */   public int getLastUpdatingUser() { return this.lastUpdatingUser; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 226 */   public void setLastUpdatingUser(int updatingUser) { this.lastUpdatingUser = updatingUser; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 236 */   public Calendar getLastUpdateDate() { return this.lastUpdateDate; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 246 */   public void setLastUpdateDate(Calendar lastUpdateDate) { this.lastUpdateDate = lastUpdateDate; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 251 */   public String getNotepadContentObjectId() { return this.abbreviation; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 261 */   public boolean getInactive() { return this.inactive; }
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
/* 272 */   public void setInactive(boolean inactive) { this.inactive = inactive; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 282 */   public int getProdType() { return this.prodType; }
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
/* 293 */   public void setProdType(int prodType) { this.prodType = prodType; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 303 */   public boolean getIsDigitalEquivalent() { return this.isDigitalEquivalent; }
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
/* 314 */   public void setIsDigitalEquivalent(boolean isDigitalEquivalent) { this.isDigitalEquivalent = isDigitalEquivalent; }
/*     */ }


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\LookupObject.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */