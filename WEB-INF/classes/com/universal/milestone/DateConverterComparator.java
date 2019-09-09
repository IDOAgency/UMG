/*    */ package WEB-INF.classes.com.universal.milestone;
/*    */ 
/*    */ import com.universal.milestone.MilestoneHelper;
/*    */ import java.util.Calendar;
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
/*    */ public class DateConverterComparator
/*    */   implements Comparator
/*    */ {
/*    */   public int compare(Object o1, Object o2) {
/* 22 */     Calendar thisStreetDate = MilestoneHelper.getDate((String)o1);
/* 23 */     Calendar thatStreetDate = MilestoneHelper.getDate((String)o2);
/*    */     
/* 25 */     if (thisStreetDate == null && thatStreetDate == null) {
/* 26 */       return 0;
/*    */     }
/* 28 */     if (thisStreetDate == null) {
/* 29 */       return 1;
/*    */     }
/* 31 */     if (thatStreetDate == null) {
/* 32 */       return -1;
/*    */     }
/* 34 */     if (thisStreetDate.before(thatStreetDate)) {
/* 35 */       return -1;
/*    */     }
/* 37 */     if (thisStreetDate.after(thatStreetDate)) {
/* 38 */       return 1;
/*    */     }
/*    */     
/* 41 */     return 0;
/*    */   }
/*    */ }


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\DateConverterComparator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */