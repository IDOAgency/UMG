/*    */ package WEB-INF.classes.com.universal.milestone;
/*    */ 
/*    */ import com.universal.milestone.ScheduledTask;
/*    */ import java.util.Comparator;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SchedTaskWksToReleaseComparator
/*    */   implements Comparator
/*    */ {
/*    */   public int compare(Object o1, Object o2) {
/*    */     try {
/* 14 */       ScheduledTask task1 = (ScheduledTask)o1;
/* 15 */       ScheduledTask task2 = (ScheduledTask)o2;
/*    */       
/* 17 */       int weeks1 = task1.getWeeksToRelease();
/* 18 */       int weeks2 = task2.getWeeksToRelease();
/*    */       
/* 20 */       return (new Integer(weeks2)).compareTo(new Integer(weeks1));
/*    */     }
/* 22 */     catch (Exception e) {
/*    */       
/* 24 */       return 0;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\SchedTaskWksToReleaseComparator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */