/*     */ package WEB-INF.classes.com.universal.milestone;
/*     */ 
/*     */ import com.techempower.ComponentLog;
/*     */ import com.techempower.gemini.BasicHandler;
/*     */ import com.techempower.gemini.Context;
/*     */ import com.techempower.gemini.Dispatcher;
/*     */ import com.techempower.gemini.GeminiApplication;
/*     */ import com.universal.milestone.Cache;
/*     */ import com.universal.milestone.CacheRefreshHandler;
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
/*     */ public class CacheRefreshHandler
/*     */   extends BasicHandler
/*     */   implements MilestoneConstants
/*     */ {
/*     */   public static final String COMPONENT_CODE = "hcrf";
/*     */   public GeminiApplication application;
/*     */   public ComponentLog log;
/*     */   
/*     */   public CacheRefreshHandler(GeminiApplication application) {
/*  54 */     this.application = application;
/*  55 */     this.log = application.getLog("hcrf");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  63 */   public String getDescription() { return "Cache Refresh Handler"; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean acceptRequest(Dispatcher dispatcher, Context context, String command) {
/*  73 */     if (command.startsWith("cache-refresh"))
/*     */     {
/*  75 */       return handleRequest(dispatcher, context, command);
/*     */     }
/*  77 */     return false;
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
/*     */   public boolean handleRequest(Dispatcher dispatcher, Context context, String command) {
/*  89 */     if (command.equalsIgnoreCase("cache-refresh"))
/*     */     {
/*  91 */       RefreshCache(dispatcher, context, command);
/*     */     }
/*  93 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean RefreshCache(Dispatcher dispatcher, Context context, String command) {
/* 102 */     String strObject = "";
/* 103 */     if (context.getParameter("cache-refresh-param") != null) {
/* 104 */       strObject = context.getParameter("cache-refresh-param");
/*     */     }
/*     */     
/* 107 */     String strType = "";
/* 108 */     if (context.getParameter("cache-refresh-param-type") != null) {
/* 109 */       strType = context.getParameter("cache-refresh-param-type");
/*     */     }
/*     */     
/* 112 */     String strUserId = "";
/* 113 */     if (context.getParameter("cache-refresh-param-userid") != null) {
/* 114 */       strUserId = context.getParameter("cache-refresh-param-userid");
/*     */     }
/* 116 */     System.out.println("<<< CMD_REFRESH_CACHE_PARAM " + strObject + 
/* 117 */         " REFRESH_CACHE_PARAM_TYPE " + strType + 
/* 118 */         " REFRESH_CACHE_PARAM_USERID " + strUserId);
/*     */     
/* 120 */     if (strObject.equalsIgnoreCase("CS")) {
/* 121 */       Cache.getInstance().flushCorporateStructureLocal();
/* 122 */     } else if (strObject.equalsIgnoreCase("PC")) {
/* 123 */       Cache.getInstance().flushSellCodeLocal();
/* 124 */     } else if (strObject.equalsIgnoreCase("TV")) {
/* 125 */       Cache.getInstance().flushTableVariablesLocal(strType);
/* 126 */     } else if (strObject.equalsIgnoreCase("DP")) {
/* 127 */       Cache.getInstance().flushDatePeriodsLocal();
/* 128 */     } else if (strObject.equalsIgnoreCase("DT")) {
/* 129 */       Cache.getInstance().flushDayTypesLocal();
/* 130 */     } else if (strObject.equalsIgnoreCase("AU")) {
/* 131 */       if (strUserId.equals(""))
/* 132 */       { Cache.flushCachedVariableLocal(Cache.getInstance().getAllUsers()); }
/*     */       else
/* 134 */       { Cache.getInstance().flushCachedVariableLocalAllUsers(Integer.parseInt(strUserId)); } 
/* 135 */     } else if (strObject.equalsIgnoreCase("UL")) {
/* 136 */       Cache.getInstance().flushUsedLabelsLocal();
/*     */     } 
/*     */ 
/*     */     
/* 140 */     return true;
/*     */   }
/*     */ }


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\CacheRefreshHandler.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */