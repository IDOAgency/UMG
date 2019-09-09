/*    */ package WEB-INF.classes.com.universal.milestone;
/*    */ 
/*    */ import com.universal.milestone.Selection;
/*    */ import java.util.Comparator;
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
/*    */ public class SelectionSelectionNumberComparator
/*    */   implements Comparator
/*    */ {
/*    */   public int compare(Object o1, Object o2) {
/*    */     try {
/* 33 */       Selection sel1 = (Selection)o1;
/* 34 */       Selection sel2 = (Selection)o2;
/*    */       
/* 36 */       String prefix1 = "";
/* 37 */       String prefix2 = "";
/*    */       
/* 39 */       if (sel1.getPrefixID() != null) {
/* 40 */         prefix1 = sel1.getPrefixID().getName();
/*    */       }
/* 42 */       if (sel2.getPrefixID() != null) {
/* 43 */         prefix2 = sel1.getPrefixID().getName();
/*    */       }
/* 45 */       String selNo1 = String.valueOf(prefix1.trim()) + sel1.getSelectionNo().trim();
/* 46 */       String selNo2 = String.valueOf(prefix2.trim()) + sel2.getSelectionNo().trim();
/*    */       
/* 48 */       return selNo1.compareTo(selNo2);
/*    */     }
/* 50 */     catch (Exception e) {
/*    */       
/* 52 */       return 0;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\SelectionSelectionNumberComparator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */