/*    */ package WEB-INF.classes.com.universal.milestone;
/*    */ 
/*    */ import com.universal.milestone.MilestoneHelper;
/*    */ import java.util.Calendar;
/*    */ import java.util.Comparator;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class StringDateComparator
/*    */   implements Comparator
/*    */ {
/*    */   public int compare(Object o1, Object o2) {
/*    */     try {
/* 14 */       Calendar thisStreetDate = null;
/*    */       
/* 16 */       if (!((String)o1).equalsIgnoreCase("TBS") && !((String)o1).equalsIgnoreCase("ITW")) {
/* 17 */         thisStreetDate = MilestoneHelper.getDate((String)o1);
/*    */       }
/* 19 */       if (((String)o1).indexOf("TBS") > 0) {
/* 20 */         thisStreetDate = MilestoneHelper.getDate(((String)o1).substring(((String)o1).indexOf("TBS"), ((String)o1).length()));
/* 21 */       } else if (((String)o1).indexOf("ITW") > 0) {
/* 22 */         thisStreetDate = MilestoneHelper.getDate(((String)o1).substring(((String)o1).indexOf("ITW"), ((String)o1).length()));
/*    */       } 
/* 24 */       Calendar thatStreetDate = null;
/* 25 */       if (!((String)o2).equalsIgnoreCase("TBS") && !((String)o2).equalsIgnoreCase("ITW")) {
/* 26 */         thatStreetDate = MilestoneHelper.getDate((String)o2);
/*    */       }
/* 28 */       if (((String)o2).indexOf("TBS") > 0) {
/* 29 */         thatStreetDate = MilestoneHelper.getDate(((String)o2).substring(((String)o2).indexOf("TBS"), ((String)o2).length()));
/* 30 */       } else if (((String)o2).indexOf("ITW") > 0) {
/* 31 */         thatStreetDate = MilestoneHelper.getDate(((String)o2).substring(((String)o2).indexOf("ITW"), ((String)o2).length()));
/*    */       } 
/*    */       
/* 34 */       if (thisStreetDate == null && thatStreetDate == null) {
/* 35 */         return 0;
/*    */       }
/* 37 */       if (thisStreetDate == null) {
/* 38 */         return 1;
/*    */       }
/* 40 */       if (thatStreetDate == null) {
/* 41 */         return -1;
/*    */       }
/* 43 */       if (thisStreetDate.before(thatStreetDate)) {
/* 44 */         return -1;
/*    */       }
/* 46 */       if (thisStreetDate.after(thatStreetDate)) {
/* 47 */         return 1;
/*    */       }
/*    */       
/* 50 */       return 0;
/*    */     }
/* 52 */     catch (Exception e) {
/*    */       
/* 54 */       return 1;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\StringDateComparator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */