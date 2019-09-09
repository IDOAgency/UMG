/*    */ package WEB-INF.classes.com.universal.milestone;
/*    */ 
/*    */ import com.universal.milestone.ScheduledTask;
/*    */ import java.util.Comparator;
/*    */ import java.util.Date;
/*    */ 
/*    */ 
/*    */ public class SchedTaskCompleteComparator
/*    */   implements Comparator
/*    */ {
/*    */   public int compare(Object o1, Object o2) {
/* 12 */     int catchFlag = -1;
/*    */ 
/*    */     
/*    */     try {
/* 16 */       ScheduledTask task1 = (ScheduledTask)o1;
/* 17 */       Date task1Date = task1.getCompletionDate().getTime();
/*    */       
/* 19 */       catchFlag = 1;
/*    */       
/* 21 */       ScheduledTask task2 = (ScheduledTask)o2;
/* 22 */       Date task2Date = task2.getCompletionDate().getTime();
/*    */       
/* 24 */       return task1Date.compareTo(task2Date);
/*    */     }
/* 26 */     catch (Exception e) {
/*    */       
/* 28 */       return catchFlag;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\SchedTaskCompleteComparator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */