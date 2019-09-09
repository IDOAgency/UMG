/*    */ package WEB-INF.classes.com.universal.milestone;
/*    */ 
/*    */ import com.universal.milestone.MilestoneHashtable;
/*    */ import java.util.Enumeration;
/*    */ import java.util.Hashtable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MilestoneHashtable
/*    */   extends Hashtable
/*    */ {
/*    */   public boolean containsKey(String key) {
/* 16 */     boolean result = false;
/*    */     
/* 18 */     if (key != null)
/*    */     {
/* 20 */       for (Enumeration strKeys = keys(); strKeys.hasMoreElements(); ) {
/*    */         
/* 22 */         String strKey = (String)strKeys.nextElement();
/* 23 */         if (strKey != null && strKey.equalsIgnoreCase(key))
/* 24 */           return true; 
/*    */       } 
/*    */     }
/* 27 */     return result;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Object get(String key) {
/* 36 */     Object result = null;
/*    */     
/* 38 */     if (key != null)
/*    */     {
/* 40 */       for (Enumeration strKeys = keys(); strKeys.hasMoreElements(); ) {
/*    */         
/* 42 */         String strKey = (String)strKeys.nextElement();
/* 43 */         if (strKey != null && strKey.equalsIgnoreCase(key))
/* 44 */           return get(strKey); 
/*    */       } 
/*    */     }
/* 47 */     return result;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Object put(String key, String value) {
/* 56 */     for (Enumeration strKeys = keys(); strKeys.hasMoreElements(); ) {
/*    */       
/* 58 */       String strKey = (String)strKeys.nextElement();
/* 59 */       if (strKey.equalsIgnoreCase(key)) {
/* 60 */         remove(strKey);
/*    */       }
/*    */     } 
/* 63 */     return put(key, value);
/*    */   }
/*    */ }


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\MilestoneHashtable.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */