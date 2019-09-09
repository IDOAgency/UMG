/*    */ package WEB-INF.classes.com.universal.milestone;
/*    */ 
/*    */ import com.universal.milestone.MilestoneHelper;
/*    */ import java.util.Comparator;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class StringMonthComparator
/*    */   implements Comparator
/*    */ {
/*    */   public int compare(Object o1, Object o2) {
/*    */     try {
/* 14 */       String thisStreetDate = String.valueOf(MilestoneHelper.getMonth((String)o1));
/* 15 */       String thatStreetDate = String.valueOf(MilestoneHelper.getMonth((String)o2));
/*    */       
/* 17 */       return thisStreetDate.compareTo(thatStreetDate);
/*    */     }
/* 19 */     catch (Exception e) {
/*    */       
/* 21 */       return 0;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\StringMonthComparator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */