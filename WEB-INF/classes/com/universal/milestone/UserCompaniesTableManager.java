/*     */ package WEB-INF.classes.com.universal.milestone;
/*     */ 
/*     */ import com.techempower.ComponentLog;
/*     */ import com.techempower.EnhancedProperties;
/*     */ import com.techempower.gemini.GeminiApplication;
/*     */ import com.universal.milestone.Cache;
/*     */ import com.universal.milestone.MilestoneConstants;
/*     */ import com.universal.milestone.UserCompaniesTableManager;
/*     */ import java.util.Hashtable;
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
/*     */ public class UserCompaniesTableManager
/*     */   implements MilestoneConstants
/*     */ {
/*     */   public static final String COMPONENT_CODE = "mUCT";
/*  45 */   protected static UserCompaniesTableManager userCompaniesTableManager = null;
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
/*  57 */   public static void configure(EnhancedProperties props, GeminiApplication application) { log = application.getLog("mUCT"); }
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
/*     */   public static UserCompaniesTableManager getInstance() {
/*  75 */     if (userCompaniesTableManager == null)
/*     */     {
/*  77 */       userCompaniesTableManager = new UserCompaniesTableManager();
/*     */     }
/*  79 */     return userCompaniesTableManager;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void setUpdateFlag(int userId, boolean isUpdated) {
/*  90 */     Hashtable userCompanyTable = Cache.getUserCompaniesTable();
/*  91 */     String userKey = Integer.toString(userId);
/*  92 */     userCompanyTable.put(userKey, new Boolean(isUpdated));
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
/*     */   public static boolean isUpdated(int userId) {
/* 106 */     boolean isUpdated = false;
/* 107 */     Hashtable userCompanyTable = Cache.getUserCompaniesTable();
/* 108 */     String userKey = Integer.toString(userId);
/*     */     
/* 110 */     if (userCompanyTable.get(userKey) != null) {
/* 111 */       isUpdated = ((Boolean)userCompanyTable.get(userKey)).booleanValue();
/*     */     }
/* 113 */     return isUpdated;
/*     */   }
/*     */ }


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\UserCompaniesTableManager.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */