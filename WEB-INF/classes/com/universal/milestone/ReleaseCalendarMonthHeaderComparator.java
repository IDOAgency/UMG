/*    */ package WEB-INF.classes.com.universal.milestone;
/*    */ 
/*    */ import com.universal.milestone.ReleaseCalendarMonthHeaderComparator;
/*    */ import java.util.Calendar;
/*    */ import java.util.Comparator;
/*    */ import java.util.GregorianCalendar;
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
/*    */ public class ReleaseCalendarMonthHeaderComparator
/*    */   implements Comparator
/*    */ {
/*    */   private int getMonth(String month) {
/* 21 */     int monthIndex = 0;
/* 22 */     if (month.equalsIgnoreCase("January"))
/* 23 */       monthIndex = 0; 
/* 24 */     if (month.equalsIgnoreCase("February"))
/* 25 */       monthIndex = 1; 
/* 26 */     if (month.equalsIgnoreCase("March"))
/* 27 */       monthIndex = 2; 
/* 28 */     if (month.equalsIgnoreCase("April"))
/* 29 */       monthIndex = 3; 
/* 30 */     if (month.equalsIgnoreCase("May"))
/* 31 */       monthIndex = 4; 
/* 32 */     if (month.equalsIgnoreCase("June"))
/* 33 */       monthIndex = 5; 
/* 34 */     if (month.equalsIgnoreCase("July"))
/* 35 */       monthIndex = 6; 
/* 36 */     if (month.equalsIgnoreCase("August"))
/* 37 */       monthIndex = 7; 
/* 38 */     if (month.equalsIgnoreCase("September"))
/* 39 */       monthIndex = 8; 
/* 40 */     if (month.equalsIgnoreCase("October"))
/* 41 */       monthIndex = 9; 
/* 42 */     if (month.equalsIgnoreCase("November"))
/* 43 */       monthIndex = 10; 
/* 44 */     if (month.equalsIgnoreCase("December"))
/* 45 */       monthIndex = 11; 
/* 46 */     return monthIndex;
/*    */   }
/*    */ 
/*    */   
/*    */   public int compare(Object o1, Object o2) {
/* 51 */     String cso1 = (String)o1;
/* 52 */     String cso2 = (String)o2;
/*    */     
/* 54 */     Integer cso1Year = Integer.valueOf(cso1.substring(cso1.indexOf("-") + 1));
/* 55 */     Integer cso2Year = Integer.valueOf(cso2.substring(cso2.indexOf("-") + 1));
/*    */     
/* 57 */     String cso1Month = cso1.substring(0, cso1.indexOf("-"));
/* 58 */     String cso2Month = cso2.substring(0, cso2.indexOf("-"));
/*    */     
/* 60 */     Calendar cso1Cal = new GregorianCalendar(cso1Year.intValue(), getMonth(cso1Month), 1);
/* 61 */     Calendar cso2Cal = new GregorianCalendar(cso2Year.intValue(), getMonth(cso2Month), 1);
/*    */     
/* 63 */     return cso1Cal.getTime().compareTo(cso2Cal.getTime());
/*    */   }
/*    */ }


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\ReleaseCalendarMonthHeaderComparator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */