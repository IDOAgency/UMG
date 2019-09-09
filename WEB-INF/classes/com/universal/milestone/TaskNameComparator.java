/*    */ package WEB-INF.classes.com.universal.milestone;
/*    */ 
/*    */ import com.universal.milestone.Task;
/*    */ import java.util.Comparator;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TaskNameComparator
/*    */   implements Comparator
/*    */ {
/*    */   public int compare(Object o1, Object o2) {
/* 12 */     Task task1 = (Task)o1;
/* 13 */     Task task2 = (Task)o2;
/*    */     
/* 15 */     String task1Name = task1.getTaskName().toUpperCase();
/* 16 */     String task2Name = task2.getTaskName().toUpperCase();
/*    */     
/* 18 */     return task1Name.compareTo(task2Name);
/*    */   }
/*    */ }


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\TaskNameComparator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */