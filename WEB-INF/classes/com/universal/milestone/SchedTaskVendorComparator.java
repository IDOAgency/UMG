/*    */ package WEB-INF.classes.com.universal.milestone;
/*    */ 
/*    */ import com.universal.milestone.ScheduledTask;
/*    */ import java.util.Comparator;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SchedTaskVendorComparator
/*    */   implements Comparator
/*    */ {
/*    */   public int compare(Object o1, Object o2) {
/*    */     try {
/* 15 */       ScheduledTask task1 = (ScheduledTask)o1;
/* 16 */       ScheduledTask task2 = (ScheduledTask)o2;
/*    */       
/* 18 */       String vendor1 = task1.getVendor();
/* 19 */       String vendor2 = task2.getVendor();
/*    */       
/* 21 */       return vendor1.compareTo(vendor2);
/*    */     }
/* 23 */     catch (Exception e) {
/*    */       
/* 25 */       return 0;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\SchedTaskVendorComparator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */