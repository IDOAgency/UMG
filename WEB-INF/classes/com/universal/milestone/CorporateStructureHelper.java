/*    */ package WEB-INF.classes.com.universal.milestone;
/*    */ 
/*    */ import com.techempower.BasicHelper;
/*    */ import com.techempower.ComponentLog;
/*    */ import com.techempower.EnhancedProperties;
/*    */ import com.techempower.gemini.GeminiApplication;
/*    */ import com.universal.milestone.CorporateStructureHelper;
/*    */ import com.universal.milestone.MilestoneConstants;
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
/*    */ public class CorporateStructureHelper
/*    */   extends BasicHelper
/*    */   implements MilestoneConstants
/*    */ {
/*    */   public static final String COMPONENT_CODE = "chlp";
/* 43 */   protected static String databaseName = "Milestone2";
/* 44 */   protected static String dbLoginName = "";
/* 45 */   protected static String dbLoginPass = "";
/* 46 */   protected static String urlPrefix = "jdbc:odbc:";
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
/*    */   public static void configure(EnhancedProperties props, GeminiApplication application) {
/* 59 */     databaseName = props.getProperty("DatabaseName", databaseName);
/* 60 */     dbLoginName = props.getProperty("DatabaseLogin", dbLoginName);
/* 61 */     dbLoginPass = props.getProperty("DatabasePassword", dbLoginPass);
/* 62 */     urlPrefix = props.getProperty("JDBCURLPrefix", urlPrefix);
/*    */ 
/*    */     
/* 65 */     log = application.getLog("chlp");
/*    */   }
/*    */ }


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\CorporateStructureHelper.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */