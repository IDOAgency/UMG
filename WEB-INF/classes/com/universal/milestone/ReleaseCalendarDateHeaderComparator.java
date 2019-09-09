/*    */ package WEB-INF.classes.com.universal.milestone;
/*    */ 
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
/*    */ 
/*    */ 
/*    */ public class ReleaseCalendarDateHeaderComparator
/*    */   implements Comparator
/*    */ {
/*    */   public int compare(Object o1, Object o2) {
/* 22 */     String cso1 = (String)o1;
/* 23 */     String cso2 = (String)o2;
/*    */     
/* 25 */     Integer cso1Date = Integer.valueOf(cso1.substring(0, cso1.indexOf("-")));
/* 26 */     Integer cso2Date = Integer.valueOf(cso2.substring(0, cso2.indexOf("-")));
/*    */ 
/*    */     
/* 29 */     return cso1Date.compareTo(cso2Date);
/*    */   }
/*    */ }


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\ReleaseCalendarDateHeaderComparator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */