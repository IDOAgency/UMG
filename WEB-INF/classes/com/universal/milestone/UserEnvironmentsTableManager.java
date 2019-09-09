/*     */ package WEB-INF.classes.com.universal.milestone;
/*     */ 
/*     */ import com.techempower.ComponentLog;
/*     */ import com.techempower.EnhancedProperties;
/*     */ import com.techempower.gemini.GeminiApplication;
/*     */ import com.universal.milestone.Cache;
/*     */ import com.universal.milestone.MilestoneConstants;
/*     */ import com.universal.milestone.UserEnvironmentsTableManager;
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
/*     */ public class UserEnvironmentsTableManager
/*     */   implements MilestoneConstants
/*     */ {
/*     */   public static final String COMPONENT_CODE = "mUET";
/*  45 */   protected static UserEnvironmentsTableManager userEnvironmentsTableManager = null;
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
/*  57 */   public static void configure(EnhancedProperties props, GeminiApplication application) { log = application.getLog("mUET"); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static UserEnvironmentsTableManager getInstance() {
/*  75 */     if (userEnvironmentsTableManager == null)
/*     */     {
/*  77 */       userEnvironmentsTableManager = new UserEnvironmentsTableManager();
/*     */     }
/*  79 */     return userEnvironmentsTableManager;
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
/*  90 */     Hashtable userEnvironmentTable = Cache.getUserEnvironmentsTable();
/*  91 */     String userKey = Integer.toString(userId);
/*  92 */     userEnvironmentTable.put(userKey, new Boolean(isUpdated));
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
/* 107 */     Hashtable userEnvironmentTable = Cache.getUserEnvironmentsTable();
/* 108 */     String userKey = Integer.toString(userId);
/*     */     
/* 110 */     if (userEnvironmentTable.get(userKey) != null) {
/* 111 */       isUpdated = ((Boolean)userEnvironmentTable.get(userKey)).booleanValue();
/*     */     }
/* 113 */     return isUpdated;
/*     */   }
/*     */ }


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\UserEnvironmentsTableManager.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */