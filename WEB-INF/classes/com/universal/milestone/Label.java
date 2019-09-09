/*     */ package WEB-INF.classes.com.universal.milestone;
/*     */ 
/*     */ import com.techempower.DatabaseConnector;
/*     */ import com.universal.milestone.CorporateStructureObject;
/*     */ import com.universal.milestone.Division;
/*     */ import com.universal.milestone.Label;
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
/*     */ 
/*     */ public class Label
/*     */   extends CorporateStructureObject
/*     */   implements Cloneable
/*     */ {
/*     */   protected Division parentDivision;
/*     */   protected boolean activeFlag;
/*  55 */   public int distribution = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  60 */   protected String entityName = "";
/*  61 */   protected String legacyOpCo = "";
/*  62 */   protected String legacyOpUnit = "";
/*  63 */   protected String legacySuperLabel = "";
/*  64 */   protected String legacySubLabel = "";
/*  65 */   protected String levelOfActivity = "";
/*  66 */   protected String productionGroupCode = "";
/*  67 */   protected String entityType = "";
/*     */   
/*     */   protected boolean APNGInd = false;
/*     */   
/*  71 */   protected String distCoName = "";
/*  72 */   protected int distCoId = -1;
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
/*  86 */   public Division getParentDivision() { return this.parentDivision; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  96 */   public void setParentDivision(Division division) { this.parentDivision = division; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 106 */   public int getDistribution() { return this.distribution; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDistribution(int distribution) {
/* 116 */     auditCheck("distribution", this.distribution, distribution);
/* 117 */     this.distribution = distribution;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 126 */   public CorporateStructureObject getParent() { return getParentDivision(); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 134 */   public void setParent(CorporateStructureObject cso) { setParentDivision((Division)cso); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 142 */   public boolean getActive() { return this.activeFlag; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 150 */   public void setActive(boolean active) { this.activeFlag = active; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void customInitialization(DatabaseConnector connector) {
/* 158 */     super.customInitialization(connector);
/* 159 */     this.distribution = connector.getInt("region");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 165 */   public String getEntityName() { return this.entityName; }
/*     */ 
/*     */ 
/*     */   
/* 169 */   public void setEntityName(String entityName) { this.entityName = entityName; }
/*     */ 
/*     */ 
/*     */   
/* 173 */   public String getLegacyOpCo() { return this.legacyOpCo; }
/*     */ 
/*     */ 
/*     */   
/* 177 */   public void setLegacyOpCo(String legacyOpCo) { this.legacyOpCo = legacyOpCo; }
/*     */ 
/*     */ 
/*     */   
/* 181 */   public String getLegacyOpUnit() { return this.legacyOpUnit; }
/*     */ 
/*     */ 
/*     */   
/* 185 */   public void setLegacyOpUnit(String legacyOpUnit) { this.legacyOpUnit = legacyOpUnit; }
/*     */ 
/*     */ 
/*     */   
/* 189 */   public String getLegacySuperLabel() { return this.legacySuperLabel; }
/*     */ 
/*     */ 
/*     */   
/* 193 */   public void setLegacySuperLabel(String legacySuperLabel) { this.legacySuperLabel = legacySuperLabel; }
/*     */ 
/*     */ 
/*     */   
/* 197 */   public String getLegacySubLabel() { return this.legacySubLabel; }
/*     */ 
/*     */ 
/*     */   
/* 201 */   public void setLegacySubLabel(String legacySubLabel) { this.legacySubLabel = legacySubLabel; }
/*     */ 
/*     */ 
/*     */   
/* 205 */   public String getLevelOfActivity() { return this.levelOfActivity; }
/*     */ 
/*     */ 
/*     */   
/* 209 */   public void setLevelOfActivity(String levelOfActivity) { this.levelOfActivity = levelOfActivity; }
/*     */ 
/*     */ 
/*     */   
/* 213 */   public String getProductionGroupCode() { return this.productionGroupCode; }
/*     */ 
/*     */ 
/*     */   
/* 217 */   public void setProductionGroupCode(String productionGroupCode) { this.productionGroupCode = productionGroupCode; }
/*     */ 
/*     */ 
/*     */   
/* 221 */   public String getEntityType() { return this.entityType; }
/*     */ 
/*     */ 
/*     */   
/* 225 */   public void setEntityType(String entityType) { this.entityType = entityType; }
/*     */ 
/*     */ 
/*     */   
/* 229 */   public void setAPNGInd(boolean apngInd) { this.APNGInd = apngInd; }
/*     */ 
/*     */ 
/*     */   
/* 233 */   public boolean getAPGNInd() { return this.APNGInd; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 238 */   public void setDistCoName(String distCoName) { this.distCoName = distCoName; }
/*     */ 
/*     */ 
/*     */   
/* 242 */   public String getDistCoName() { return this.distCoName; }
/*     */ 
/*     */ 
/*     */   
/* 246 */   public void setDistCoId(int distCoId) { this.distCoId = distCoId; }
/*     */ 
/*     */ 
/*     */   
/* 250 */   public int getDistCoId() { return this.distCoId; }
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
/* 261 */   public Object clone() throws CloneNotSupportedException { return super.clone(); }
/*     */ }


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\Label.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */