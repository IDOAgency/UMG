/*     */ package WEB-INF.classes.com.universal.milestone;
/*     */ 
/*     */ import com.universal.milestone.IDJExceptionsComparator;
/*     */ import com.universal.milestone.Selection;
/*     */ import com.universal.milestone.SelectionSubConfiguration;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class IDJExceptionsComparator
/*     */   implements Comparator
/*     */ {
/*     */   public int compare(Object o1, Object o2) {
/*     */     Calendar calEntry2, calEntry1;
/*  33 */     Selection sel1 = (Selection)o1;
/*  34 */     Selection sel2 = (Selection)o2;
/*     */     
/*  36 */     String title1 = sel1.getTitle();
/*  37 */     String title2 = sel2.getTitle();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  43 */     if (sel1.getIsDigital()) {
/*  44 */       calEntry1 = sel1.getDigitalRlsDate();
/*     */     } else {
/*     */       
/*  47 */       calEntry1 = sel1.getStreetDate();
/*     */     } 
/*     */     
/*  50 */     if (sel2.getIsDigital()) {
/*  51 */       calEntry2 = sel2.getDigitalRlsDate();
/*     */     } else {
/*     */       
/*  54 */       calEntry2 = sel2.getStreetDate();
/*     */     } 
/*     */     
/*  57 */     boolean activeTBSSel1 = (calEntry1 != null);
/*  58 */     boolean activeTBSSel2 = (calEntry2 != null);
/*     */ 
/*     */     
/*  61 */     if (activeTBSSel1 && !activeTBSSel2) {
/*  62 */       return -1;
/*     */     }
/*     */     
/*  65 */     if (!activeTBSSel1 && activeTBSSel2) {
/*  66 */       return 1;
/*     */     }
/*     */     
/*  69 */     if (calEntry1 == null && calEntry2 == null)
/*     */     {
/*  71 */       return compareTitleAndSubConfig(sel1, sel2);
/*     */     }
/*     */ 
/*     */     
/*  75 */     if (calEntry1.before(calEntry2)) {
/*  76 */       return -1;
/*     */     }
/*  78 */     if (calEntry1.after(calEntry2)) {
/*  79 */       return 1;
/*     */     }
/*     */ 
/*     */     
/*  83 */     return compareTitleAndSubConfig(sel1, sel2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int compareTitleAndSubConfig(Selection sel1, Selection sel2) {
/*  93 */     String titleEntry1 = sel1.getTitle().toUpperCase();
/*  94 */     String titleEntry2 = sel2.getTitle().toUpperCase();
/*     */     
/*  96 */     if (!titleEntry1.equals(titleEntry2))
/*     */     {
/*  98 */       return titleEntry1.compareTo(titleEntry2);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 103 */     SelectionSubConfiguration subconfig1 = sel1.getSelectionSubConfig();
/* 104 */     SelectionSubConfiguration subconfig2 = sel2.getSelectionSubConfig();
/* 105 */     String subconfigAbbreviation1 = "";
/* 106 */     String subconfigAbbreviation2 = "";
/* 107 */     if (subconfig1 != null)
/* 108 */       subconfigAbbreviation1 = (subconfig1
/* 109 */         .getSelectionSubConfigurationAbbreviation() == null) ? 
/* 110 */         "" : subconfig1.getSelectionSubConfigurationAbbreviation(); 
/* 111 */     if (subconfig2 != null) {
/* 112 */       subconfigAbbreviation2 = (subconfig2
/* 113 */         .getSelectionSubConfigurationAbbreviation() == null) ? 
/* 114 */         "" : subconfig2.getSelectionSubConfigurationAbbreviation();
/*     */     }
/* 116 */     return subconfigAbbreviation1.compareTo(subconfigAbbreviation2);
/*     */   }
/*     */ }


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\IDJExceptionsComparator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */