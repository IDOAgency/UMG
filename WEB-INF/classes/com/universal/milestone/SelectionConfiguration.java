/*     */ package WEB-INF.classes.com.universal.milestone;
/*     */ 
/*     */ import com.techempower.DataEntity;
/*     */ import com.universal.milestone.SelectionConfiguration;
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
/*     */ public class SelectionConfiguration
/*     */   extends DataEntity
/*     */ {
/*     */   int selectionConfigurationID;
/*     */   String selectionConfigurationAbbreviation;
/*     */   String selectionConfigurationName;
/*     */   Vector subConfigurations;
/*     */   boolean inactive;
/*     */   int prodType;
/*     */   int newBundle;
/*     */   
/*     */   public SelectionConfiguration(String abbreviation, String name, Vector subConfigs) {
/*  41 */     this.inactive = false;
/*     */     
/*  43 */     this.prodType = 2;
/*     */     
/*  45 */     this.newBundle = 1;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  52 */     this.selectionConfigurationAbbreviation = abbreviation;
/*  53 */     this.selectionConfigurationName = name;
/*  54 */     this.subConfigurations = subConfigs;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*  59 */   public SelectionConfiguration(String abbreviation, String name) { this(abbreviation, name, null); }
/*     */ 
/*     */   
/*     */   public SelectionConfiguration(String name, Vector subConfigs) {
/*     */     this.inactive = false;
/*     */     this.prodType = 2;
/*     */     this.newBundle = 1;
/*  66 */     this.selectionConfigurationName = name;
/*  67 */     this.subConfigurations = subConfigs;
/*     */   }
/*     */   public SelectionConfiguration(String abbreviation, String name, Vector subConfigs, boolean inactive) {
/*     */     this.inactive = false;
/*     */     this.prodType = 2;
/*     */     this.newBundle = 1;
/*  73 */     this.selectionConfigurationAbbreviation = abbreviation;
/*  74 */     this.selectionConfigurationName = name;
/*  75 */     this.subConfigurations = subConfigs;
/*  76 */     this.inactive = inactive;
/*     */   }
/*     */   public SelectionConfiguration(String abbreviation, String name, Vector subConfigs, boolean inactive, int prodType) {
/*     */     this.inactive = false;
/*     */     this.prodType = 2;
/*     */     this.newBundle = 1;
/*  82 */     this.selectionConfigurationAbbreviation = abbreviation;
/*  83 */     this.selectionConfigurationName = name;
/*  84 */     this.subConfigurations = subConfigs;
/*  85 */     this.inactive = inactive;
/*  86 */     this.prodType = prodType;
/*     */   }
/*     */   public SelectionConfiguration(String abbreviation, String name, Vector subConfigs, boolean inactive, int prodType, int newBundle) {
/*     */     this.inactive = false;
/*     */     this.prodType = 2;
/*     */     this.newBundle = 1;
/*  92 */     this.selectionConfigurationAbbreviation = abbreviation;
/*  93 */     this.selectionConfigurationName = name;
/*  94 */     this.subConfigurations = subConfigs;
/*  95 */     this.inactive = inactive;
/*  96 */     this.prodType = prodType;
/*  97 */     this.newBundle = newBundle;
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
/* 111 */   public int getSelectionConfigurationID() { return this.selectionConfigurationID; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 121 */   public void setSelectionConfigurationID(int selectionConfigurationID) { this.selectionConfigurationID = selectionConfigurationID; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 131 */   public String getSelectionConfigurationName() { return this.selectionConfigurationName; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 141 */   public void setSelectionConfigurationName(String selectionConfigurationName) { this.selectionConfigurationName = selectionConfigurationName; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 151 */   public String getSelectionConfigurationAbbreviation() { return this.selectionConfigurationAbbreviation; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 160 */   public Vector getSubConfigurations() { return this.subConfigurations; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 170 */   public void setSubConfigurations(Vector subConfig) { this.subConfigurations = subConfig; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 179 */   public boolean getInactive() { return this.inactive; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 189 */   public void setInactive(boolean inactive) { this.inactive = inactive; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 198 */   public int getProdType() { return this.prodType; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 208 */   public void setProdType(int prodType) { this.prodType = prodType; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 217 */   public int getNewBundle() { return this.newBundle; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 227 */   public void setNewBundle(int newBundle) { this.newBundle = newBundle; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 235 */     return "SelectionConfiguration [" + this.selectionConfigurationID + 
/* 236 */       "; " + this.selectionConfigurationAbbreviation + 
/* 237 */       "; " + this.subConfigurations + 
/* 238 */       "]";
/*     */   }
/*     */ }


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\SelectionConfiguration.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */