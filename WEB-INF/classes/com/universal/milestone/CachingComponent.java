/*    */ package WEB-INF.classes.com.universal.milestone;
/*    */ 
/*    */ import com.universal.milestone.JdbcConnector;
/*    */ import com.universal.milestone.MilestoneHelper;
/*    */ import java.util.Vector;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ abstract class CachingComponent
/*    */ {
/*    */   protected static Vector getCachedVectorOfStringsSp(Vector cache, String storedProcedure, String fieldName) {
/* 42 */     if (cache != null)
/*    */     {
/*    */       
/* 45 */       return cache;
/*    */     }
/*    */ 
/*    */ 
/*    */     
/* 50 */     Vector precache = new Vector();
/*    */     
/* 52 */     JdbcConnector connector = MilestoneHelper.createConnector(storedProcedure);
/* 53 */     connector.runQuery();
/*    */     
/* 55 */     while (connector.more()) {
/*    */       
/* 57 */       precache.addElement(connector.getField(fieldName));
/* 58 */       connector.next();
/*    */     } 
/* 60 */     connector.close();
/*    */     
/* 62 */     return precache;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected static Vector getCachedVectorOfStrings(Vector cache, String query, String fieldName) {
/* 71 */     if (cache != null)
/*    */     {
/*    */       
/* 74 */       return cache;
/*    */     }
/*    */ 
/*    */ 
/*    */     
/* 79 */     Vector precache = new Vector();
/*    */     
/* 81 */     JdbcConnector connector = MilestoneHelper.createConnector(query);
/* 82 */     connector.runQuery();
/*    */     
/* 84 */     while (connector.more()) {
/*    */       
/* 86 */       precache.addElement(connector.getField(fieldName));
/* 87 */       connector.next();
/*    */     } 
/* 89 */     connector.close();
/*    */     
/* 91 */     return precache;
/*    */   }
/*    */ }


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\CachingComponent.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */