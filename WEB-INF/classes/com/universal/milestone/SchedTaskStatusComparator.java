/*    */ package WEB-INF.classes.com.universal.milestone;
/*    */ 
/*    */ import com.universal.milestone.ScheduledTask;
/*    */ import java.util.Comparator;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SchedTaskStatusComparator
/*    */   implements Comparator
/*    */ {
/*    */   public int compare(Object o1, Object o2) {
/*    */     try {
/* 14 */       ScheduledTask task1 = (ScheduledTask)o1;
/* 15 */       ScheduledTask task2 = (ScheduledTask)o2;
/*    */       
/* 17 */       String status1 = task1.getScheduledTaskStatus();
/* 18 */       String status2 = task2.getScheduledTaskStatus();
/*    */       
/* 20 */       return status1.compareTo(status2);
/*    */     }
/* 22 */     catch (Exception e) {
/*    */       
/* 24 */       return 0;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\SchedTaskStatusComparator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */