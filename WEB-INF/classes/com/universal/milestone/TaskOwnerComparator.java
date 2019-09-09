/*    */ package WEB-INF.classes.com.universal.milestone;
/*    */ 
/*    */ import com.universal.milestone.Family;
/*    */ import com.universal.milestone.Task;
/*    */ import java.util.Comparator;
/*    */ 
/*    */ 
/*    */ public class TaskOwnerComparator
/*    */   implements Comparator
/*    */ {
/*    */   public int compare(Object o1, Object o2) {
/* 12 */     Task task1 = (Task)o1;
/* 13 */     Task task2 = (Task)o2;
/*    */     
/* 15 */     Family family1 = task1.getOwner();
/* 16 */     Family family2 = task2.getOwner();
/*    */     
/* 18 */     String cso1Name = family1.getName().toUpperCase();
/* 19 */     String cso2Name = family2.getName().toUpperCase();
/*    */     
/* 21 */     return cso1Name.compareTo(cso2Name);
/*    */   }
/*    */ }


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\TaskOwnerComparator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */