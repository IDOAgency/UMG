/*    */ package WEB-INF.classes.com.universal.milestone;
/*    */ 
/*    */ import com.universal.milestone.Selection;
/*    */ import java.util.Calendar;
/*    */ import java.util.Comparator;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SelectionStreetDateComparator
/*    */   implements Comparator
/*    */ {
/*    */   public int compare(Object o1, Object o2) {
/* 13 */     Selection sel1 = (Selection)o1;
/* 14 */     Selection sel2 = (Selection)o2;
/*    */ 
/*    */     
/*    */     try {
/* 18 */       Calendar streetDateCal1 = sel1.getStreetDate();
/* 19 */       Calendar streetDateCal2 = sel2.getStreetDate();
/*    */       
/* 21 */       if (streetDateCal1 == null && streetDateCal2 == null)
/* 22 */         return 0; 
/* 23 */       if (streetDateCal1 == null)
/* 24 */         return 1; 
/* 25 */       if (streetDateCal2 == null) {
/* 26 */         return -1;
/*    */       }
/* 28 */       if (streetDateCal1.before(streetDateCal2))
/* 29 */         return -1; 
/* 30 */       if (streetDateCal1.after(streetDateCal2))
/* 31 */         return 1; 
/* 32 */       return 0;
/*    */     
/*    */     }
/* 35 */     catch (Exception e) {
/*    */       
/* 37 */       System.out.println(">>>>>>>>exception in SelectionStreetDateComparator");
/* 38 */       return 0;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\SelectionStreetDateComparator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */