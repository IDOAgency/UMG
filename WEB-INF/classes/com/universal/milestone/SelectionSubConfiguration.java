/*     */ package WEB-INF.classes.com.universal.milestone;
/*     */ 
/*     */ import com.techempower.DataEntity;
/*     */ import com.universal.milestone.SelectionSubConfiguration;
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
/*     */ public class SelectionSubConfiguration
/*     */   extends DataEntity
/*     */ {
/*     */   int selectionSubConfigurationID;
/*     */   boolean parent;
/*     */   String selectionSubConfigurationAbbreviation;
/*     */   String selectionSubConfigurationName;
/*     */   boolean inactive;
/*     */   int prodType;
/*     */   
/*     */   public SelectionSubConfiguration(String abbreviation, String name) {
/*  40 */     this.inactive = false;
/*     */     
/*  42 */     this.prodType = 2;
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
/*  54 */     this.selectionSubConfigurationAbbreviation = abbreviation;
/*  55 */     this.selectionSubConfigurationName = name;
/*     */     
/*  57 */     this.parent = false;
/*     */   }
/*     */   public SelectionSubConfiguration(String abbreviation, String name, int prodType) {
/*     */     this.inactive = false;
/*     */     this.prodType = 2;
/*  62 */     this.selectionSubConfigurationAbbreviation = abbreviation;
/*  63 */     this.selectionSubConfigurationName = name;
/*  64 */     this.prodType = prodType;
/*     */     
/*  66 */     this.parent = false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  76 */   public int getSelectionSubConfigurationID() { return this.selectionSubConfigurationID; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  86 */   public void setSelectionSubConfigurationID(int selectionSubConfigurationID) { this.selectionSubConfigurationID = selectionSubConfigurationID; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  96 */   public String getSelectionSubConfigurationName() { return this.selectionSubConfigurationName; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 106 */   public void setSelectionSubConfigurationName(String selectionSubConfigurationName) { this.selectionSubConfigurationName = selectionSubConfigurationName; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 116 */   public String getSelectionSubConfigurationAbbreviation() { return this.selectionSubConfigurationAbbreviation; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 126 */   public void setSelectionSubConfigurationAbbreviation(String abbreviation) { this.selectionSubConfigurationAbbreviation = abbreviation; }
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
/* 137 */   public boolean isParent() { return this.parent; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 147 */   public void setParent(boolean parent) { this.parent = parent; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 157 */   public boolean getInactive() { return this.inactive; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 167 */   public void setInactive(boolean inactive) { this.inactive = inactive; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 176 */   public int getProdType() { return this.prodType; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 186 */   public void setProdType(int prodType) { this.prodType = prodType; }
/*     */ }


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\SelectionSubConfiguration.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */