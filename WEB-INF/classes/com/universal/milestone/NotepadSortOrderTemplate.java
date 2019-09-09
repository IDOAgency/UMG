/*     */ package WEB-INF.classes.com.universal.milestone;
/*     */ 
/*     */ import com.techempower.gemini.Context;
/*     */ import com.techempower.gemini.Dispatcher;
/*     */ import com.universal.milestone.MilestoneHelper;
/*     */ import com.universal.milestone.Notepad;
/*     */ import com.universal.milestone.NotepadSortOrderInterface;
/*     */ import com.universal.milestone.NotepadSortOrderTemplate;
/*     */ import com.universal.milestone.User;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class NotepadSortOrderTemplate
/*     */   implements NotepadSortOrderInterface
/*     */ {
/*  42 */   protected String templateOrderCol = "Template";
/*  43 */   protected String templateOrderBy = "";
/*  44 */   protected int templateOrderColNo = 0;
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
/*  59 */   public void setTemplateOrderCol(String orderCol) { this.templateOrderCol = orderCol; }
/*     */ 
/*     */ 
/*     */   
/*  63 */   public String getTemplateOrderCol() { return this.templateOrderCol; }
/*     */ 
/*     */ 
/*     */   
/*  67 */   public void setTemplateOrderBy(String orderBy) { this.templateOrderBy = orderBy; }
/*     */ 
/*     */ 
/*     */   
/*  71 */   public String getTemplateOrderBy() { return this.templateOrderBy; }
/*     */ 
/*     */ 
/*     */   
/*  75 */   public void setTemplateOrderColNo(int colNo) { this.templateOrderColNo = colNo; }
/*     */ 
/*     */ 
/*     */   
/*  79 */   public int getTemplateOrderColNo() { return this.templateOrderColNo; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  84 */   public String getNotepadOrderCol() { return this.templateOrderCol; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isSortAscending() {
/*  93 */     if (this.templateOrderBy.indexOf(" DESC ") == -1) {
/*  94 */       return true;
/*     */     }
/*  96 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean sortHelper(Dispatcher dispatcher, Context context, Notepad notepad) {
/* 105 */     int userId = ((User)context.getSessionValue("user")).getUserId();
/*     */     
/* 107 */     String sort = "";
/* 108 */     int sortInd = 0;
/*     */ 
/*     */     
/* 111 */     String orderColLast = getTemplateOrderCol();
/*     */ 
/*     */     
/* 114 */     String orderCol = context.getParameter("OrderBy");
/*     */ 
/*     */     
/* 117 */     String orderBy = getTemplateOrderBy();
/*     */ 
/*     */     
/* 120 */     if (orderCol.equalsIgnoreCase("Template"))
/* 121 */       sortInd = 0; 
/* 122 */     if (orderCol.equalsIgnoreCase("Format"))
/* 123 */       sortInd = 1; 
/* 124 */     if (orderCol.equalsIgnoreCase("Owner")) {
/* 125 */       sortInd = 2;
/*     */     }
/* 127 */     if (orderCol.equalsIgnoreCase(orderColLast)) {
/*     */       
/* 129 */       if (orderBy.indexOf(" DESC ") == -1) {
/* 130 */         sort = String.valueOf(MilestoneHelper.SORT_TEMPLATE[sortInd]) + " DESC ";
/*     */       } else {
/* 132 */         sort = MilestoneHelper.SORT_TEMPLATE[sortInd];
/*     */       }
/*     */     
/* 135 */     } else if (orderBy.indexOf(" DESC ") == -1) {
/* 136 */       sort = MilestoneHelper.SORT_TEMPLATE[sortInd];
/*     */     } else {
/* 138 */       sort = String.valueOf(MilestoneHelper.SORT_TEMPLATE[sortInd]) + " DESC ";
/*     */     } 
/*     */ 
/*     */     
/* 142 */     if (sortInd != 0) {
/* 143 */       sort = String.valueOf(sort) + "," + MilestoneHelper.SORT_TEMPLATE[0];
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 149 */     setTemplateOrderBy(sort);
/* 150 */     setTemplateOrderCol(orderCol);
/* 151 */     setTemplateOrderColNo(sortInd);
/*     */ 
/*     */     
/* 154 */     notepad.setOrderBy(" ORDER BY " + sort);
/*     */     
/* 156 */     return true;
/*     */   }
/*     */ }


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\NotepadSortOrderTemplate.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */