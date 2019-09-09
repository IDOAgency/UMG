/*     */ package WEB-INF.classes.com.universal.milestone;
/*     */ 
/*     */ import com.universal.milestone.MilestoneHelper;
/*     */ import java.util.Calendar;
/*     */ import java.util.Comparator;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class IDJReportDateArtistComparator
/*     */   implements Comparator
/*     */ {
/*     */   public int compare(Object o1, Object o2) {
/*     */     Calendar calEntry2, calEntry1;
/*  24 */     String entry1 = (String)o1;
/*  25 */     String entry2 = (String)o2;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  31 */     String dateEntry1 = "";
/*  32 */     String dateEntry2 = "";
/*  33 */     if (entry1.startsWith("TBS")) {
/*  34 */       dateEntry1 = entry1.substring(6, entry1.lastIndexOf("*") - 1);
/*  35 */       dateEntry2 = entry2.substring(6, entry2.lastIndexOf("*") - 1);
/*     */     } else {
/*  37 */       dateEntry1 = entry1.substring(0, entry1.indexOf("*") - 1);
/*  38 */       dateEntry2 = entry2.substring(0, entry2.indexOf("*") - 1);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/*  45 */       calEntry1 = MilestoneHelper.getDate(dateEntry1);
/*  46 */       calEntry2 = MilestoneHelper.getDate(dateEntry2);
/*     */     }
/*  48 */     catch (Exception e) {
/*     */       
/*  50 */       System.out.println(">>>>>>>>exception in IDJReportDateArtistComparator");
/*  51 */       return 0;
/*     */     } 
/*     */ 
/*     */     
/*  55 */     String artistEntry1 = "";
/*  56 */     String artistEntry2 = "";
/*  57 */     if (entry1.startsWith("TBS")) {
/*  58 */       artistEntry1 = entry1.substring(entry1.lastIndexOf("*") + 2, entry1.length()).toUpperCase().trim();
/*  59 */       artistEntry2 = entry2.substring(entry2.lastIndexOf("*") + 2, entry2.length()).toUpperCase().trim();
/*     */     } else {
/*  61 */       artistEntry1 = entry1.substring(entry1.indexOf("*") + 2, entry1.length()).toUpperCase().trim();
/*  62 */       artistEntry2 = entry2.substring(entry2.indexOf("*") + 2, entry2.length()).toUpperCase().trim();
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/*  67 */     if (calEntry1 == null && calEntry2 == null) {
/*  68 */       return artistEntry1.compareTo(artistEntry2);
/*     */     }
/*     */     
/*  71 */     if (calEntry1 == null || calEntry2 == null) {
/*     */ 
/*     */       
/*  74 */       if (calEntry1 == null && calEntry2 == null)
/*  75 */         return 0; 
/*  76 */       if (calEntry1 == null)
/*  77 */         return 1; 
/*  78 */       if (calEntry2 == null) {
/*  79 */         return -1;
/*     */       }
/*  81 */       if (calEntry1.before(calEntry2)) {
/*  82 */         return -1;
/*     */       }
/*  84 */       if (calEntry1.after(calEntry2)) {
/*  85 */         return 1;
/*     */       }
/*     */       
/*  88 */       return 0;
/*     */     } 
/*     */ 
/*     */     
/*  92 */     if (calEntry1.equals(calEntry2)) {
/*  93 */       return artistEntry1.compareTo(artistEntry2);
/*     */     }
/*     */     
/*  96 */     if (calEntry1 == null && calEntry2 == null)
/*  97 */       return 0; 
/*  98 */     if (calEntry1 == null)
/*  99 */       return 1; 
/* 100 */     if (calEntry2 == null) {
/* 101 */       return -1;
/*     */     }
/* 103 */     if (calEntry1.before(calEntry2)) {
/* 104 */       return -1;
/*     */     }
/* 106 */     if (calEntry1.after(calEntry2)) {
/* 107 */       return 1;
/*     */     }
/*     */     
/* 110 */     return 0;
/*     */   }
/*     */ }


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\IDJReportDateArtistComparator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */