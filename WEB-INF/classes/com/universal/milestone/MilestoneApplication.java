/*     */ package WEB-INF.classes.com.universal.milestone;
/*     */ 
/*     */ import com.techempower.Version;
/*     */ import com.techempower.gemini.BasicInfrastructure;
/*     */ import com.techempower.gemini.Configurator;
/*     */ import com.techempower.gemini.Context;
/*     */ import com.techempower.gemini.Dispatcher;
/*     */ import com.techempower.gemini.EmailServicer;
/*     */ import com.techempower.gemini.EmailTemplater;
/*     */ import com.techempower.gemini.EmailTransport;
/*     */ import com.techempower.gemini.GeminiApplication;
/*     */ import com.universal.milestone.MilestoneApplication;
/*     */ import com.universal.milestone.MilestoneConfigurator;
/*     */ import com.universal.milestone.MilestoneContext;
/*     */ import com.universal.milestone.MilestoneDispatcher;
/*     */ import com.universal.milestone.MilestoneInfrastructure;
/*     */ import com.universal.milestone.MilestoneVersion;
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
/*     */ public class MilestoneApplication
/*     */   extends GeminiApplication
/*     */ {
/*  38 */   protected static MilestoneApplication instance = new MilestoneApplication();
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
/*  58 */   protected Version constructVersion() { return MilestoneVersion.getInstance(); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  66 */   protected BasicInfrastructure constructInfrastructure() { return new MilestoneInfrastructure(this); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  74 */   protected Configurator constructConfigurator() { return new MilestoneConfigurator(this); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  82 */   protected Dispatcher constructDispatcher() { return new MilestoneDispatcher(this); }
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
/*  94 */   protected EmailTemplater constructEmailTemplater() { return null; }
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
/* 107 */   protected EmailServicer constructEmailServicer() { return null; }
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
/* 120 */   protected EmailTransport constructEmailTransport() { return null; }
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
/* 133 */   public Context getContext(HttpServletRequest request, HttpServletResponse response, ServletContext servletContext) { return new MilestoneContext(request, response, servletContext, this.dispatcher); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 142 */   public static GeminiApplication getInstance() { return instance; }
/*     */ }


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\MilestoneApplication.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */