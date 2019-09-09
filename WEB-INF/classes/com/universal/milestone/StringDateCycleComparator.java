/*    */ package WEB-INF.classes.com.universal.milestone;
/*    */ 
/*    */ import com.universal.milestone.MilestoneHelper;
/*    */ import java.util.Calendar;
/*    */ import java.util.Comparator;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class StringDateCycleComparator
/*    */   implements Comparator
/*    */ {
/*    */   public int compare(Object o1, Object o2) {
/*    */     try {
/* 14 */       String o1Date = ((String)o1).substring(0, ((String)o1).indexOf(" "));
/* 15 */       String o2Date = ((String)o2).substring(0, ((String)o2).indexOf(" "));
/*    */       
/* 17 */       String o1Cycle = ((String)o1).substring(((String)o1).indexOf(" "), ((String)o1).length());
/* 18 */       String o2Cycle = ((String)o2).substring(((String)o2).indexOf(" "), ((String)o2).length());
/*    */       
/* 20 */       Calendar thisStreetDate = MilestoneHelper.getDate(o1Date);
/* 21 */       Calendar thatStreetDate = MilestoneHelper.getDate(o2Date);
/*    */       
/* 23 */       if (thisStreetDate == null && thatStreetDate == null) {
/* 24 */         return 0;
/*    */       }
/* 26 */       if (thisStreetDate == null) {
/* 27 */         return 1;
/*    */       }
/* 29 */       if (thatStreetDate == null) {
/* 30 */         return -1;
/*    */       }
/* 32 */       if (thisStreetDate.before(thatStreetDate)) {
/* 33 */         return -1;
/*    */       }
/* 35 */       if (thisStreetDate.after(thatStreetDate)) {
/* 36 */         return 1;
/*    */       }
/* 38 */       if (thisStreetDate.equals(thatStreetDate)) {
/* 39 */         return o1Cycle.compareTo(o2Cycle);
/*    */       }
/*    */       
/* 42 */       return 0;
/*    */     }
/* 44 */     catch (Exception e) {
/*    */       
/* 46 */       return 0;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\StringDateCycleComparator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */