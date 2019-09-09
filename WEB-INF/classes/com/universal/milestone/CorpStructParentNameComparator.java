/*    */ package WEB-INF.classes.com.universal.milestone;
/*    */ 
/*    */ import com.universal.milestone.CorporateStructureObject;
/*    */ import java.util.Comparator;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CorpStructParentNameComparator
/*    */   implements Comparator
/*    */ {
/*    */   public int compare(Object o1, Object o2) {
/* 12 */     int result = -1;
/*    */     
/* 14 */     CorporateStructureObject cso1 = (CorporateStructureObject)o1;
/* 15 */     CorporateStructureObject cso2 = (CorporateStructureObject)o2;
/*    */ 
/*    */     
/*    */     try {
/* 19 */       CorporateStructureObject parent1 = cso1.getParent();
/* 20 */       CorporateStructureObject parent2 = cso2.getParent();
/*    */       
/* 22 */       String cso1Name = parent1.getName().toUpperCase();
/* 23 */       String cso2Name = parent2.getName().toUpperCase();
/*    */       
/* 25 */       result = cso1Name.compareTo(cso2Name);
/*    */     }
/* 27 */     catch (NullPointerException nullPointerException) {}
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 32 */     return result;
/*    */   }
/*    */ }


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\CorpStructParentNameComparator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */