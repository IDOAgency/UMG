/*     */ package WEB-INF.classes.com.universal.milestone;
/*     */ 
/*     */ import com.techempower.gemini.Dispatcher;
/*     */ import com.techempower.gemini.GeminiApplication;
/*     */ import com.universal.milestone.Milestone3Application;
/*     */ import com.universal.milestone.Milestone3Context;
/*     */ import com.universal.milestone.MilestoneContext;
/*     */ import javax.servlet.ServletContext;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
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
/*     */ public class Milestone3Context
/*     */   extends MilestoneContext
/*     */ {
/*  52 */   public Milestone3Context(HttpServletRequest request, HttpServletResponse response, ServletContext servletContext, Dispatcher dispatcher) { super(request, response, servletContext, dispatcher); }
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
/*  82 */   public String getCustomRequestMark(String pageName) { return ""; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  91 */   public GeminiApplication getApplication() { return Milestone3Application.getInstance(); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean includeJSP(String name) {
/*  99 */     this.response.setHeader("Cache-Control", "no-cache");
/* 100 */     return super.includeJSP(name);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void putSessionValue(String object, Object value) {
/* 106 */     if (value != null)
/* 107 */       super.putSessionValue(object, value); 
/*     */   }
/*     */ }


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\Milestone3Context.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */