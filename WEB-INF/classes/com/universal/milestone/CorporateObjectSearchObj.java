/*     */ package WEB-INF.classes.com.universal.milestone;
/*     */ 
/*     */ import com.techempower.ComponentLog;
/*     */ import com.techempower.EnhancedProperties;
/*     */ import com.techempower.gemini.GeminiApplication;
/*     */ import com.universal.milestone.CorporateObjectSearchObj;
/*     */ import com.universal.milestone.MilestoneConstants;
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
/*     */ public class CorporateObjectSearchObj
/*     */   implements MilestoneConstants
/*     */ {
/*     */   public static final String COMPONENT_CODE = "note";
/*     */   protected static ComponentLog log;
/*  51 */   protected String familySearch = "";
/*     */   
/*  53 */   protected String environmentSearch = "";
/*  54 */   protected String companySearch = "";
/*  55 */   protected String labelSearch = "";
/*  56 */   protected String divisionSearch = "";
/*  57 */   protected String entitySearch = "";
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
/*     */   public static void configure(EnhancedProperties props, GeminiApplication application) {}
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
/*  85 */   public String getFamilySearch() { return this.familySearch; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  95 */   public void setFamilySearch(String searchString) { this.familySearch = searchString; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 105 */   public String getCompanySearch() { return this.companySearch; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 115 */   public void setCompanySearch(String searchString) { this.companySearch = searchString; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 125 */   public String getDivisionSearch() { return this.divisionSearch; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 135 */   public void setDivisionSearch(String searchString) { this.divisionSearch = searchString; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 145 */   public String getLabelSearch() { return this.labelSearch; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 155 */   public void setLabelSearch(String searchString) { this.labelSearch = searchString; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 165 */   public String getEntitySearch() { return this.entitySearch; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 175 */   public void setEntitySearch(String searchString) { this.entitySearch = searchString; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clearAllSearches() {
/* 186 */     this.familySearch = "";
/* 187 */     this.environmentSearch = "";
/* 188 */     this.companySearch = "";
/* 189 */     this.divisionSearch = "";
/* 190 */     this.labelSearch = "";
/* 191 */     this.entitySearch = "";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isSearchEmpty() {
/* 202 */     boolean result = false;
/*     */     
/* 204 */     if (this.familySearch.equals("") && 
/* 205 */       this.environmentSearch.equals("") && 
/* 206 */       this.companySearch.equals("") && 
/* 207 */       this.divisionSearch.equals("") && 
/* 208 */       this.labelSearch.equals("") && 
/* 209 */       this.entitySearch.equals("")) {
/* 210 */       result = true;
/*     */     }
/* 212 */     return result;
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
/* 223 */   public String getEnvironmentSearch() { return this.environmentSearch; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 233 */   public void setEnvironmentSearch(String searchString) { this.environmentSearch = searchString; }
/*     */ }


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\CorporateObjectSearchObj.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */