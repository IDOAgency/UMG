/*     */ package WEB-INF.classes.com.universal.milestone;
/*     */ 
/*     */ import com.techempower.ComponentLog;
/*     */ import com.techempower.gemini.Context;
/*     */ import com.universal.milestone.StatusJSPupdate;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ 
/*     */ public class StatusJSPupdate {
/*     */   protected Context context;
/*     */   protected int count;
/*     */   protected HttpServletResponse response;
/*     */   protected int totalCount;
/*     */   protected int currentCount;
/*     */   protected int tenth;
/*     */   protected int percent;
/*     */   private boolean calculatedTenth;
/*     */   protected String jspFileName;
/*     */   protected String contextType;
/*     */   protected ComponentLog log;
/*     */   private static final int ten = 10;
/*     */   private static final int ninty = 90;
/*     */   private static final String COMPONENT_CODE = "hRep";
/*     */   private boolean logflag;
/*     */   private int internalCounter;
/*     */   private boolean useInternalCounter;
/*     */   private String lastStatusReport;
/*     */   StringBuffer blog;
/*     */   
/*     */   public StatusJSPupdate(Context context) {
/*  30 */     this.count = -1;
/*  31 */     this.response = null;
/*  32 */     this.totalCount = 0;
/*  33 */     this.currentCount = 0;
/*  34 */     this.tenth = 0;
/*  35 */     this.percent = 0;
/*  36 */     this.calculatedTenth = false;
/*  37 */     this.jspFileName = "status.jsp";
/*  38 */     this.contextType = "text/plain";
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  43 */     this.logflag = false;
/*  44 */     this.internalCounter = 0;
/*  45 */     this.useInternalCounter = false;
/*  46 */     this.lastStatusReport = "";
/*  47 */     this.blog = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  53 */     this.context = context;
/*  54 */     this.log = context.getApplication().getLog("hRep");
/*     */     
/*  56 */     this.response = context.getResponse();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public StatusJSPupdate(Context context, String jspFileName) {
/*  63 */     this(context);
/*  64 */     this.jspFileName = jspFileName;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateStatus(int totalCount, int currentCount, String report, int defaultPercent) {
/*  73 */     if (!report.equals(this.lastStatusReport)) {
/*  74 */       setDefaults(report);
/*     */     }
/*  76 */     int currStatusCount = 0;
/*     */ 
/*     */     
/*  79 */     if (this.useInternalCounter) {
/*  80 */       currStatusCount = this.internalCounter;
/*     */     } else {
/*  82 */       currStatusCount = currentCount;
/*     */     } 
/*     */ 
/*     */     
/*     */     try {
/*  87 */       if (!this.calculatedTenth) {
/*  88 */         this.tenth = calculateTenth(totalCount);
/*     */       }
/*     */ 
/*     */       
/*  92 */       if (this.count < currStatusCount / this.tenth || defaultPercent != 0) {
/*     */ 
/*     */         
/*  95 */         this.count = currStatusCount / this.tenth;
/*     */ 
/*     */         
/*  98 */         if (defaultPercent == 0) {
/*     */           
/* 100 */           this.percent = this.count * 10;
/*     */         
/*     */         }
/*     */         else {
/*     */           
/* 105 */           this.percent = defaultPercent;
/*     */         } 
/*     */ 
/*     */         
/* 109 */         if (this.logflag) this.log.log("<<<<<<  updating status: ");
/*     */ 
/*     */         
/* 112 */         this.context.putDelivery("status", new String(report));
/* 113 */         this.context.putDelivery("percent", new String(String.valueOf(this.percent)));
/* 114 */         this.context.includeJSP(this.jspFileName, "hiddenFrame");
/* 115 */         this.response.setContentType(this.contextType);
/* 116 */         this.response.flushBuffer();
/*     */       } 
/*     */ 
/*     */       
/* 120 */       if (this.useInternalCounter && this.calculatedTenth) {
/* 121 */         this.internalCounter++;
/*     */       }
/* 123 */     } catch (Exception e) {
/* 124 */       this.log.log("<<<<<<<<<<<<<< Unable to udpate status: " + e.getMessage());
/*     */     } 
/*     */ 
/*     */     
/* 128 */     if (this.logflag) {
/* 129 */       this.blog = new StringBuffer("%(");
/* 130 */       this.blog.append(this.percent);
/* 131 */       this.blog.append(") tenth(");
/* 132 */       this.blog.append(this.tenth);
/* 133 */       this.blog.append(") count(");
/* 134 */       this.blog.append(this.count);
/* 135 */       this.blog.append(") intCount(");
/* 136 */       this.blog.append(this.internalCounter);
/* 137 */       this.blog.append(") recCount(");
/* 138 */       this.blog.append(currentCount);
/* 139 */       this.blog.append(") totCount(");
/* 140 */       this.blog.append(totalCount);
/* 141 */       this.blog.append(")");
/* 142 */       this.log.log(this.blog.toString());
/* 143 */       this.blog = null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int calculateTenth(int totalCount) {
/* 152 */     if (totalCount == 0) {
/* 153 */       return 10;
/*     */     }
/* 155 */     this.calculatedTenth = true;
/* 156 */     return (totalCount > 10) ? (totalCount / 10) : 1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 164 */   public void setInternalCounter(boolean useInternalCounter) { this.useInternalCounter = useInternalCounter; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 170 */   public void setLogging(boolean logFlag) { this.logflag = logFlag; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void setDefaults(String report) {
/* 177 */     this.count = -1;
/* 178 */     this.lastStatusReport = report;
/* 179 */     this.calculatedTenth = false;
/*     */   }
/*     */ }


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\StatusJSPupdate.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */