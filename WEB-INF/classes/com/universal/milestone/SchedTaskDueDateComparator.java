/*    */ package WEB-INF.classes.com.universal.milestone;
/*    */ 
/*    */ import com.universal.milestone.ScheduledTask;
/*    */ import java.util.Comparator;
/*    */ import java.util.Date;
/*    */ 
/*    */ 
/*    */ public class SchedTaskDueDateComparator
/*    */   implements Comparator
/*    */ {
/*    */   public int compare(Object o1, Object o2) {
/* 12 */     int catchFlag = -1;
/*    */ 
/*    */     
/*    */     try {
/* 16 */       ScheduledTask task1 = (ScheduledTask)o1;
/*    */       
/* 18 */       Date task1Date = task1.getDueDate().getTime();
/*    */       
/* 20 */       catchFlag = 1;
/*    */       
/* 22 */       ScheduledTask task2 = (ScheduledTask)o2;
/* 23 */       Date task2Date = task2.getDueDate().getTime();
/*    */       
/* 25 */       return task1Date.compareTo(task2Date);
/*    */     }
/* 27 */     catch (Exception e) {
/*    */       
/* 29 */       return catchFlag;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\SchedTaskDueDateComparator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */