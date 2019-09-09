/*    */ package WEB-INF.classes.com.universal.milestone;
/*    */ 
/*    */ import com.universal.milestone.MilestoneVector;
/*    */ import java.util.Iterator;
/*    */ import java.util.Vector;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MilestoneVector
/*    */   extends Vector
/*    */ {
/*    */   public boolean contains(String key) {
/* 17 */     boolean result = false;
/*    */     
/* 19 */     if (key != null)
/*    */     {
/* 21 */       for (Iterator strKeys = iterator(); strKeys.hasNext(); ) {
/*    */         
/* 23 */         String strKey = (String)strKeys.next();
/* 24 */         if (strKey != null && strKey.equalsIgnoreCase(key))
/* 25 */           return true; 
/*    */       } 
/*    */     }
/* 28 */     return result;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int indexOf(String key) {
/* 37 */     int result = -1;
/*    */     
/* 39 */     if (key != null) {
/* 40 */       Iterator strKeys = iterator();
/* 41 */       for (int i = 0; strKeys.hasNext(); i++) {
/* 42 */         String strKey = (String)strKeys.next();
/* 43 */         if (strKey != null && strKey.equalsIgnoreCase(key))
/* 44 */           return i; 
/*    */       } 
/*    */     } 
/* 47 */     return result;
/*    */   }
/*    */ }


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\MilestoneVector.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */