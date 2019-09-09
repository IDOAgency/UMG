/*     */ package WEB-INF.classes.com.universal.milestone;
/*     */ 
/*     */ import com.techempower.ComponentLog;
/*     */ import com.techempower.gemini.Context;
/*     */ import com.techempower.gemini.Dispatcher;
/*     */ import com.techempower.gemini.GeminiApplication;
/*     */ import com.universal.milestone.MainTopMenuHandler;
/*     */ import com.universal.milestone.SecureHandler;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MainTopMenuHandler
/*     */   extends SecureHandler
/*     */ {
/*     */   public static final String COMPONENT_CODE = "hMTM";
/*     */   public GeminiApplication application;
/*     */   public ComponentLog log;
/*     */   
/*     */   public MainTopMenuHandler(GeminiApplication application) {
/*  62 */     this.application = application;
/*  63 */     this.log = application.getLog("hMTM");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  71 */   public String getDescription() { return "MainTopMenu"; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean acceptRequest(Dispatcher dispatcher, Context context, String command) {
/*  81 */     if (super.acceptRequest(dispatcher, context, command))
/*     */     {
/*  83 */       if (command.equalsIgnoreCase("main-top-menu"))
/*     */       {
/*  85 */         return handleRequest(dispatcher, context, command);
/*     */       }
/*     */     }
/*     */     
/*  89 */     return false;
/*     */   }
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
/* 101 */   public boolean handleRequest(Dispatcher dispatcher, Context context, String command) { return context.includeJSP("main-top-menu.jsp"); }
/*     */ }


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\MainTopMenuHandler.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */