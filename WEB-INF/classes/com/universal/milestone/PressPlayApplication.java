/*     */ package WEB-INF.classes.com.universal.milestone;
/*     */ 
/*     */ import com.techempower.Version;
/*     */ import com.techempower.gemini.BasicInfrastructure;
/*     */ import com.techempower.gemini.Configurator;
/*     */ import com.techempower.gemini.Context;
/*     */ import com.techempower.gemini.Dispatcher;
/*     */ import com.techempower.gemini.EmailTemplater;
/*     */ import com.techempower.gemini.GeminiApplication;
/*     */ import com.universal.milestone.MilestoneApplication;
/*     */ import com.universal.milestone.MilestoneConfigurator;
/*     */ import com.universal.milestone.MilestoneDispatcher;
/*     */ import com.universal.milestone.MilestoneInfrastructure;
/*     */ import com.universal.milestone.PressPlayApplication;
/*     */ import com.universal.milestone.PressPlayContext;
/*     */ import com.universal.milestone.PressPlayVersion;
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
/*     */ public class PressPlayApplication
/*     */   extends MilestoneApplication
/*     */ {
/*  39 */   protected static PressPlayApplication instance = new PressPlayApplication();
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
/*  59 */   protected Version constructVersion() { return PressPlayVersion.getInstance(); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  67 */   protected BasicInfrastructure constructInfrastructure() { return new MilestoneInfrastructure(this); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  75 */   protected Configurator constructConfigurator() { return new MilestoneConfigurator(this); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  83 */   protected Dispatcher constructDispatcher() { return new MilestoneDispatcher(this); }
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
/*  95 */   protected EmailTemplater constructEmailTemplater() { return null; }
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
/* 108 */   public Context getContext(HttpServletRequest request, HttpServletResponse response, ServletContext servletContext) { return new PressPlayContext(request, response, servletContext, this.dispatcher); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 117 */   public static GeminiApplication getInstance() { return instance; }
/*     */ }


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\PressPlayApplication.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */