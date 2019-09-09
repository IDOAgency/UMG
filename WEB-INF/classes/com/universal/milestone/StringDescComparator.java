/*    */ package WEB-INF.classes.com.universal.milestone;
/*    */ 
/*    */ import java.util.Comparator;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class StringDescComparator
/*    */   implements Comparator
/*    */ {
/*    */   public int compare(Object o1, Object o2) {
/* 12 */     String string1 = ((String)o1).toUpperCase();
/* 13 */     String string2 = ((String)o2).toUpperCase();
/*    */     
/* 15 */     return string2.compareTo(string1);
/*    */   }
/*    */ }


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\StringDescComparator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */