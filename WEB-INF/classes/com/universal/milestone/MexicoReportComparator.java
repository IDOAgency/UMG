/*    */ package WEB-INF.classes.com.universal.milestone;
/*    */ 
/*    */ import com.universal.milestone.MilestoneHelper;
/*    */ import java.util.Calendar;
/*    */ import java.util.Comparator;
/*    */ import java.util.Vector;
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
/*    */ public class MexicoReportComparator
/*    */   implements Comparator
/*    */ {
/*    */   public int compare(Object o1, Object o2) {
/*    */     try {
/* 26 */       String string1 = (String)o1;
/* 27 */       String string2 = (String)o2;
/*    */ 
/*    */       
/* 30 */       String config1 = string1.substring(0, string1.indexOf("|"));
/* 31 */       String config2 = string2.substring(0, string2.indexOf("|"));
/* 32 */       String date1 = string1.substring(string1.indexOf("|") + 1, string1.length());
/* 33 */       String date2 = string2.substring(string2.indexOf("|") + 1, string2.length());
/*    */ 
/*    */ 
/*    */       
/* 37 */       String[] configList = { 
/* 38 */           "Promos in Spanish", 
/* 39 */           "Promos in English", 
/* 40 */           "Pending Albums", 
/* 41 */           "Commercial", 
/* 42 */           "National Classical", 
/* 43 */           "Popular Imports", 
/* 44 */           "DVDs", 
/* 45 */           "Special Products", 
/* 46 */           "Distributed Promos", 
/* 47 */           "Distributed Commercial", 
/* 48 */           "" };
/*    */ 
/*    */       
/* 51 */       Vector configListVector = new Vector();
/* 52 */       for (int i = 0; i < configList.length; i++) {
/* 53 */         configListVector.add(configList[i]);
/*    */       }
/*    */       
/* 56 */       if (config1.equals(config2)) {
/*    */ 
/*    */ 
/*    */         
/* 60 */         Calendar thisStreetDate = MilestoneHelper.getDate(date1);
/* 61 */         Calendar thatStreetDate = MilestoneHelper.getDate(date2);
/*    */         
/* 63 */         if (thisStreetDate == null && thatStreetDate == null) {
/* 64 */           return 0;
/*    */         }
/* 66 */         if (thisStreetDate == null) {
/* 67 */           return 1;
/*    */         }
/* 69 */         if (thatStreetDate == null) {
/* 70 */           return -1;
/*    */         }
/* 72 */         if (thisStreetDate.before(thatStreetDate)) {
/* 73 */           return -1;
/*    */         }
/* 75 */         if (thisStreetDate.after(thatStreetDate)) {
/* 76 */           return 1;
/*    */         }
/*    */         
/* 79 */         return 0;
/*    */       } 
/*    */       
/* 82 */       if (configListVector.indexOf(config1) < configListVector.indexOf(config2)) {
/* 83 */         return -1;
/*    */       }
/*    */       
/* 86 */       return 1;
/*    */     }
/* 88 */     catch (Exception e) {
/*    */       
/* 90 */       System.out.println("Exception raised in MexicoReportComparator");
/* 91 */       return 0;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\MexicoReportComparator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */