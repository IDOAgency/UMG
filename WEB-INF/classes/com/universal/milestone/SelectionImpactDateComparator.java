/*    */ package WEB-INF.classes.com.universal.milestone;
/*    */ 
/*    */ import com.universal.milestone.Selection;
/*    */ import java.util.Calendar;
/*    */ import java.util.Comparator;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SelectionImpactDateComparator
/*    */   implements Comparator
/*    */ {
/*    */   public int compare(Object o1, Object o2) {
/* 16 */     Selection sel1 = (Selection)o1;
/* 17 */     Selection sel2 = (Selection)o2;
/*    */ 
/*    */ 
/*    */     
/* 21 */     if (sel1.getImpactDates().size() != 0 && sel1.getImpactDates().size() != 0) {
/*    */       
/*    */       try {
/*    */         
/* 25 */         Calendar impactCal1 = sel1.getImpactDateObject().getImpactDate();
/* 26 */         Calendar impactCal2 = sel2.getImpactDateObject().getImpactDate();
/*    */         
/* 28 */         if (impactCal1.before(impactCal2))
/* 29 */           return -1; 
/* 30 */         if (impactCal1.after(impactCal2))
/* 31 */           return 1; 
/* 32 */         return 0;
/*    */       
/*    */       }
/* 35 */       catch (Exception e) {
/*    */         
/* 37 */         System.out.println(">>>>>>>>exception in SelectionImpactDateComparator");
/* 38 */         return 0;
/*    */       } 
/*    */     }
/*    */     
/* 42 */     return 0;
/*    */   }
/*    */ }


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\SelectionImpactDateComparator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */