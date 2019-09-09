/*    */ package WEB-INF.classes.com.universal.milestone;
/*    */ 
/*    */ import com.techempower.ComponentLog;
/*    */ import com.techempower.EnhancedProperties;
/*    */ import com.techempower.gemini.GeminiApplication;
/*    */ import com.universal.milestone.MilestoneConstants;
/*    */ import com.universal.milestone.PfmManager;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PfmManager
/*    */   implements MilestoneConstants
/*    */ {
/*    */   public static final String COMPONENT_CODE = "mPfm";
/* 41 */   protected static PfmManager pfmManager = null;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected static ComponentLog log;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 53 */   public static void configure(EnhancedProperties props, GeminiApplication application) { log = application.getLog("mPfm"); }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static PfmManager getInstance() {
/* 71 */     if (pfmManager == null)
/*    */     {
/* 73 */       pfmManager = new PfmManager();
/*    */     }
/* 75 */     return pfmManager;
/*    */   }
/*    */ }


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\PfmManager.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */