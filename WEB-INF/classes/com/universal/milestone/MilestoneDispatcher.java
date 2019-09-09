/*     */ package WEB-INF.classes.com.universal.milestone;
/*     */ 
/*     */ import com.techempower.gemini.Context;
/*     */ import com.techempower.gemini.Dispatcher;
/*     */ import com.techempower.gemini.GeminiApplication;
/*     */ import com.universal.milestone.BomHandler;
/*     */ import com.universal.milestone.CacheRefreshHandler;
/*     */ import com.universal.milestone.CompanyHandler;
/*     */ import com.universal.milestone.DayHandler;
/*     */ import com.universal.milestone.DivisionHandler;
/*     */ import com.universal.milestone.EmailDistributionHandler;
/*     */ import com.universal.milestone.EnvironmentHandler;
/*     */ import com.universal.milestone.FamilyHandler;
/*     */ import com.universal.milestone.LabelHandler;
/*     */ import com.universal.milestone.LoginHandler;
/*     */ import com.universal.milestone.MainTopMenuHandler;
/*     */ import com.universal.milestone.MilestoneDispatcher;
/*     */ import com.universal.milestone.NotepadHandler;
/*     */ import com.universal.milestone.PfmHandler;
/*     */ import com.universal.milestone.PriceCodeHandler;
/*     */ import com.universal.milestone.ProjectSearchHandler;
/*     */ import com.universal.milestone.ReleaseCalendarHandler;
/*     */ import com.universal.milestone.ReleaseWeekHandler;
/*     */ import com.universal.milestone.ReportConfigHandler;
/*     */ import com.universal.milestone.ReportHandler;
/*     */ import com.universal.milestone.ScheduleHandler;
/*     */ import com.universal.milestone.SecurityHandler;
/*     */ import com.universal.milestone.SelectionHandler;
/*     */ import com.universal.milestone.SessionUser;
/*     */ import com.universal.milestone.TablesHeaderHandler;
/*     */ import com.universal.milestone.TaskHandler;
/*     */ import com.universal.milestone.TemplateHandler;
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
/*     */ public class MilestoneDispatcher
/*     */   extends Dispatcher
/*     */ {
/*     */   protected LoginHandler loginHandler;
/*     */   
/*  72 */   public MilestoneDispatcher(GeminiApplication application) { super(application); }
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
/*     */   protected void installHandlers() {
/*  84 */     this.loginHandler = new LoginHandler(this.application);
/*     */     
/*  86 */     this.dispatchHandlers.addElement(this.loginHandler);
/*  87 */     this.dispatchHandlers.addElement(new SelectionHandler(this.application));
/*  88 */     this.dispatchHandlers.addElement(new ScheduleHandler(this.application));
/*  89 */     this.dispatchHandlers.addElement(new PriceCodeHandler(this.application));
/*  90 */     this.dispatchHandlers.addElement(new ReportConfigHandler(this.application));
/*  91 */     this.dispatchHandlers.addElement(new ReleaseWeekHandler(this.application));
/*  92 */     this.dispatchHandlers.addElement(new TablesHeaderHandler(this.application));
/*  93 */     this.dispatchHandlers.addElement(new FamilyHandler(this.application));
/*  94 */     this.dispatchHandlers.addElement(new EnvironmentHandler(this.application));
/*  95 */     this.dispatchHandlers.addElement(new CompanyHandler(this.application));
/*  96 */     this.dispatchHandlers.addElement(new DivisionHandler(this.application));
/*  97 */     this.dispatchHandlers.addElement(new LabelHandler(this.application));
/*  98 */     this.dispatchHandlers.addElement(new TaskHandler(this.application));
/*  99 */     this.dispatchHandlers.addElement(new SecurityHandler(this.application));
/* 100 */     this.dispatchHandlers.addElement(new TemplateHandler(this.application));
/* 101 */     this.dispatchHandlers.addElement(new MainTopMenuHandler(this.application));
/* 102 */     this.dispatchHandlers.addElement(new PfmHandler(this.application));
/* 103 */     this.dispatchHandlers.addElement(new BomHandler(this.application));
/* 104 */     this.dispatchHandlers.addElement(new ReportHandler(this.application));
/* 105 */     this.dispatchHandlers.addElement(new NotepadHandler(this.application));
/* 106 */     this.dispatchHandlers.addElement(new DayHandler(this.application));
/* 107 */     this.dispatchHandlers.addElement(new CacheRefreshHandler(this.application));
/* 108 */     this.dispatchHandlers.addElement(new EmailDistributionHandler(this.application));
/* 109 */     this.dispatchHandlers.addElement(new ProjectSearchHandler(this.application));
/* 110 */     this.dispatchHandlers.addElement(new ReleaseCalendarHandler(this.application));
/*     */ 
/*     */ 
/*     */     
/* 114 */     this.defaultHandler = this.loginHandler;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean dispatch(Context context) {
/* 133 */     boolean retValue = super.dispatch(context);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 139 */     SessionUser sessionUser = (SessionUser)context.getSessionValue("UserSession");
/* 140 */     if (sessionUser != null && sessionUser.recordPK != -1)
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 147 */       if (context.getRequest().getQueryString() == null || context.getRequest().getQueryString().equals("")) {
/* 148 */         sessionUser.setLastActiveDate(context.getCommand());
/*     */       } else {
/* 150 */         sessionUser.setLastActiveDate(context.getRequest().getQueryString());
/*     */       }  } 
/* 152 */     return retValue;
/*     */   }
/*     */ }


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\MilestoneDispatcher.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */