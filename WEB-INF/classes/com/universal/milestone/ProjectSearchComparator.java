/*     */ package WEB-INF.classes.com.universal.milestone;
/*     */ 
/*     */ import com.universal.milestone.MilestoneHelper;
/*     */ import com.universal.milestone.ProjectSearch;
/*     */ import com.universal.milestone.ProjectSearchComparator;
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
/*     */ public class ProjectSearchComparator
/*     */   implements Comparator
/*     */ {
/*     */   public int sortingColumn;
/*     */   public String sortingdirection;
/*     */   
/*     */   public ProjectSearchComparator(int sortColumn, String direction) {
/*  30 */     this.sortingColumn = sortColumn;
/*  31 */     this.sortingdirection = direction;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int compare(Object o1, Object o2) {
/*  38 */     ProjectSearch proj1 = (ProjectSearch)o1;
/*  39 */     ProjectSearch proj2 = (ProjectSearch)o2;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/*  46 */       switch (this.sortingColumn) {
/*     */ 
/*     */         
/*     */         case 0:
/*  50 */           return sortStringColumn(proj1.getRMSProjectNo().toUpperCase().trim(), proj2.getRMSProjectNo().toUpperCase().trim());
/*  51 */         case 1: return sortStringColumn(proj1.getArtistFirstName().toUpperCase().trim(), proj2.getArtistFirstName().toUpperCase().trim());
/*  52 */         case 2: return sortStringColumn(proj1.getArtistLastName().toUpperCase().trim(), proj2.getArtistLastName().toUpperCase().trim());
/*  53 */         case 3: return sortStringColumn(proj1.getProjectDesc().toUpperCase().trim(), proj2.getProjectDesc().toUpperCase().trim());
/*  54 */         case 4: return sortStringColumn(proj1.getTitle().toUpperCase().trim(), proj2.getTitle().toUpperCase().trim());
/*  55 */         case 5: return sortStringColumn(MilestoneHelper.getStructureName(proj1.getMSLabelId()), MilestoneHelper.getStructureName(proj2.getMSLabelId()));
/*  56 */         case 6: return sortCreateDate(proj1.getCreateDate(), proj2.getCreateDate());
/*  57 */       }  return sortCreateDate(proj1.getCreateDate(), proj2.getCreateDate());
/*     */     
/*     */     }
/*  60 */     catch (Exception e) {
/*     */       
/*  62 */       System.out.println("Exception raised in Project Search Comparator");
/*  63 */       return 0;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int sortIntegerColumn(String proj1String, String proj2String) {
/*     */     try {
/*  72 */       int proj1int = Integer.parseInt(proj1String);
/*  73 */       int proj2int = Integer.parseInt(proj2String);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  78 */       if (this.sortingdirection.equalsIgnoreCase("Ascending")) {
/*  79 */         return (new Integer(proj1int)).compareTo(new Integer(proj2int));
/*     */       }
/*  81 */       return (new Integer(proj2int)).compareTo(new Integer(proj1int));
/*     */     }
/*  83 */     catch (Exception e) {
/*     */       
/*  85 */       System.out.println("Exception raised in Project Search Comparator sortIntegerColumn()");
/*  86 */       return 0;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int sortStringColumn(String proj1String, String proj2String) {
/*     */     try {
/*  93 */       if (this.sortingdirection.equalsIgnoreCase("Ascending")) {
/*  94 */         return proj1String.compareTo(proj2String);
/*     */       }
/*  96 */       return proj2String.compareTo(proj1String);
/*     */     }
/*  98 */     catch (Exception e) {
/*     */       
/* 100 */       System.out.println("Exception raised in Project Search Comparator sortStringColumn()");
/* 101 */       return 0;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int sortCreateDate(Calendar proj1Cal, Calendar proj2Cal) {
/*     */     try {
/* 108 */       if (proj1Cal == null && proj2Cal == null)
/* 109 */         return 0; 
/* 110 */       if (proj1Cal == null)
/* 111 */         return 1; 
/* 112 */       if (proj2Cal == null) {
/* 113 */         return -1;
/*     */       }
/* 115 */       if (this.sortingdirection.equalsIgnoreCase("Ascending")) {
/*     */         
/* 117 */         if (proj1Cal.before(proj2Cal))
/* 118 */           return -1; 
/* 119 */         if (proj1Cal.after(proj2Cal))
/* 120 */           return 1; 
/* 121 */         return 0;
/*     */       } 
/* 123 */       if (proj2Cal.before(proj1Cal))
/* 124 */         return -1; 
/* 125 */       if (proj2Cal.after(proj1Cal))
/* 126 */         return 1; 
/* 127 */       return 0;
/*     */ 
/*     */     
/*     */     }
/* 131 */     catch (Exception e) {
/*     */       
/* 133 */       System.out.println("Exception raised in Project Search Comparator sortCreateDate()");
/* 134 */       return 0;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\ProjectSearchComparator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */