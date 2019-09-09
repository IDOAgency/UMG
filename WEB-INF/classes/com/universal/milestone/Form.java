/*     */ package WEB-INF.classes.com.universal.milestone;
/*     */ 
/*     */ import com.techempower.gemini.Form;
/*     */ import com.techempower.gemini.GeminiApplication;
/*     */ import com.universal.milestone.Form;
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
/*     */ public class Form
/*     */   extends Form
/*     */ {
/*  30 */   static final String[] unChangedVariables = { "", "0", "-1", "null", 
/*  31 */       "1/1/00", "&nbsp;", " ", " " };
/*  32 */   static final String[] unChangedElems = { "lastUpdatedDate", 
/*  33 */       "lastUpdatedBy", "enteredBy", "cmd", "new", 
/*  34 */       "APNGInd" };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  42 */   public Form(GeminiApplication application) { super(application); }
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
/*  58 */   public Form(GeminiApplication application, String name, String action, String method) { super(application, name, action, method); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  67 */   public boolean isUnchanged() { return super.isUnchanged(); }
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
/*     */   public boolean isUnchangedVarible(String value, String elemName) {
/* 110 */     for (int i = 0; i < unChangedElems.length; i++) {
/*     */ 
/*     */       
/* 113 */       if (elemName != null && elemName.equals(unChangedElems[i])) {
/* 114 */         return true;
/*     */       }
/*     */     } 
/*     */     
/* 118 */     for (int i = 0; i < unChangedVariables.length; i++) {
/*     */ 
/*     */ 
/*     */       
/* 122 */       if (value != null && value.equals(unChangedVariables[i]))
/* 123 */         return true; 
/*     */     } 
/* 125 */     return false;
/*     */   }
/*     */ }


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\Form.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */