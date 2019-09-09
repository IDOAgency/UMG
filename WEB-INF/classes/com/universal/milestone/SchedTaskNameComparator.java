/*    */ package WEB-INF.classes.com.universal.milestone;
/*    */ 
/*    */ import com.universal.milestone.ScheduledTask;
/*    */ import java.util.Comparator;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SchedTaskNameComparator
/*    */   implements Comparator
/*    */ {
/*    */   public int compare(Object o1, Object o2) {
/* 12 */     ScheduledTask task1 = (ScheduledTask)o1;
/* 13 */     ScheduledTask task2 = (ScheduledTask)o2;
/*    */ 
/*    */     
/*    */     try {
/* 17 */       String task1Name = task1.getName().toUpperCase();
/* 18 */       String task2Name = task2.getName().toUpperCase();
/*    */       
/* 20 */       return task1Name.compareTo(task2Name);
/*    */     }
/* 22 */     catch (Exception e) {
/*    */       
/* 24 */       return 0;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\SchedTaskNameComparator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */