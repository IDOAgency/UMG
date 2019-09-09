/*     */ package WEB-INF.classes.com.universal.milestone;
/*     */ 
/*     */ import com.techempower.EnhancedProperties;
/*     */ import com.techempower.Version;
/*     */ import com.techempower.gemini.Configurator;
/*     */ import com.techempower.gemini.GeminiApplication;
/*     */ import com.universal.milestone.Audit;
/*     */ import com.universal.milestone.Cache;
/*     */ import com.universal.milestone.CompanyManager;
/*     */ import com.universal.milestone.DayManager;
/*     */ import com.universal.milestone.DivisionManager;
/*     */ import com.universal.milestone.FamilyManager;
/*     */ import com.universal.milestone.JdbcConnector;
/*     */ import com.universal.milestone.JspDebug;
/*     */ import com.universal.milestone.LabelManager;
/*     */ import com.universal.milestone.MilestoneConfigurator;
/*     */ import com.universal.milestone.MilestoneDataEntity;
/*     */ import com.universal.milestone.MilestoneHelper;
/*     */ import com.universal.milestone.MilestoneSecurity;
/*     */ import com.universal.milestone.Notepad;
/*     */ import com.universal.milestone.PriceCodeManager;
/*     */ import com.universal.milestone.ReleaseWeekManager;
/*     */ import com.universal.milestone.ReportHandler;
/*     */ import com.universal.milestone.ScheduleManager;
/*     */ import com.universal.milestone.SelectionManager;
/*     */ import com.universal.milestone.TableManager;
/*     */ import com.universal.milestone.TaskManager;
/*     */ import com.universal.milestone.TemplateManager;
/*     */ import com.universal.milestone.UmeProdScheduleForPrintSubHandler;
/*     */ import com.universal.milestone.UserManager;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MilestoneConfigurator
/*     */   extends Configurator
/*     */ {
/*     */   public static final int DEFAULT_DB_POOLING = 5;
/*     */   
/*  49 */   protected MilestoneConfigurator(GeminiApplication application) { super(application); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void customConfiguration(EnhancedProperties props, Version version) {
/*  69 */     boolean supportsAbsolute = props.getYesNoProperty("JDBCDriverSupportsAbsolute", false);
/*  70 */     boolean supportsGetRow = props.getYesNoProperty("JDBCDriverSupportsGetRow", false);
/*  71 */     boolean jdbc1 = props.getYesNoProperty("JDBC1.x", false);
/*  72 */     String urlPrefix = props.getProperty("JDBCURLPrefix", "jdbc:odbc:");
/*  73 */     String driverName = props.getProperty("JDBCDriver", "sun.jdbc.odbc.JdbcOdbcDriver");
/*  74 */     int dbPooling = props.getIntegerProperty("DatabaseConnections", 5);
/*  75 */     JdbcConnector.loadDriver(driverName, urlPrefix, supportsAbsolute, supportsGetRow, jdbc1, dbPooling);
/*  76 */     JdbcConnector.setDefaultJdbcURLPrefix(urlPrefix);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  85 */     JdbcConnector.getDataSourceProperties(props);
/*     */ 
/*     */ 
/*     */     
/*  89 */     this.application.getInfrastructure().configure(props, version);
/*     */ 
/*     */     
/*  92 */     MilestoneHelper.configure(props, this.application);
/*     */ 
/*     */     
/*  95 */     Cache.configure(props, this.application);
/*     */ 
/*     */     
/*  98 */     Notepad.configure(props, this.application);
/*     */ 
/*     */     
/* 101 */     MilestoneSecurity.configure(props, this.application);
/*     */ 
/*     */     
/* 104 */     MilestoneDataEntity.configure(props, this.application);
/*     */ 
/*     */     
/* 107 */     JspDebug.configure(props, this.application);
/*     */ 
/*     */     
/* 110 */     CompanyManager.configure(props, this.application);
/* 111 */     DayManager.configure(props, this.application);
/* 112 */     DivisionManager.configure(props, this.application);
/* 113 */     FamilyManager.configure(props, this.application);
/* 114 */     LabelManager.configure(props, this.application);
/* 115 */     PriceCodeManager.configure(props, this.application);
/* 116 */     ReleaseWeekManager.configure(props, this.application);
/* 117 */     ScheduleManager.configure(props, this.application);
/* 118 */     SelectionManager.configure(props, this.application);
/* 119 */     TableManager.configure(props, this.application);
/* 120 */     TaskManager.configure(props, this.application);
/* 121 */     TemplateManager.configure(props, this.application);
/* 122 */     UserManager.configure(props, this.application);
/* 123 */     UmeProdScheduleForPrintSubHandler.configure(props, this.application);
/*     */ 
/*     */     
/* 126 */     ReportHandler.configure(props, this.application);
/*     */ 
/*     */ 
/*     */     
/* 130 */     setupBindings();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void setupBindings() {
/* 140 */     JdbcConnector jdbcConnector = MilestoneHelper.getConnector("SELECT * FROM Audit;");
/*     */     
/* 142 */     jdbcConnector.runQuery();
/* 143 */     if (jdbcConnector.more()) {
/*     */       
/* 145 */       Audit audit = new Audit();
/* 146 */       audit.initializeByVariables(jdbcConnector);
/*     */     } 
/*     */     
/* 149 */     jdbcConnector.close();
/*     */   }
/*     */ }


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\MilestoneConfigurator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */