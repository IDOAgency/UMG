/*     */ package WEB-INF.classes.com.universal.milestone;
/*     */ 
/*     */ import com.techempower.ComponentLog;
/*     */ import com.techempower.gemini.Context;
/*     */ import com.techempower.gemini.Dispatcher;
/*     */ import com.techempower.gemini.GeminiApplication;
/*     */ import com.universal.milestone.SecureHandler;
/*     */ import com.universal.milestone.UtilityHandler;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class UtilityHandler
/*     */   extends SecureHandler
/*     */ {
/*     */   public static final String COMPONENT_CODE = "hUti";
/*     */   public GeminiApplication application;
/*     */   public ComponentLog log;
/*     */   
/*     */   public UtilityHandler(GeminiApplication application) {
/*  59 */     this.application = application;
/*  60 */     this.log = application.getLog("hUti");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  68 */   public String getDescription() { return "Utility"; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean acceptRequest(Dispatcher dispatcher, Context context, String command) {
/*  78 */     if (super.acceptRequest(dispatcher, context, command))
/*     */     {
/*  80 */       if (command.startsWith("tables-header") || 
/*  81 */         command.startsWith("release-week") || 
/*  82 */         command.startsWith("report-config") || 
/*  83 */         command.startsWith("price-code"))
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
/*     */   public boolean handleRequest(Dispatcher dispatcher, Context context, String command) {
/* 101 */     if (command.equalsIgnoreCase("tables-header-search"))
/*     */     {
/* 103 */       return tablesHeaderSearch(context);
/*     */     }
/* 105 */     if (command.equalsIgnoreCase("tables-header-search-results"))
/*     */     {
/* 107 */       return tablesHeaderSearchResults(context);
/*     */     }
/* 109 */     if (command.equalsIgnoreCase("tables-header-editor"))
/*     */     {
/* 111 */       return tablesHeaderEditor(context);
/*     */     }
/* 113 */     if (command.equalsIgnoreCase("release-week-search"))
/*     */     {
/* 115 */       return releaseWeekSearch(context);
/*     */     }
/* 117 */     if (command.equalsIgnoreCase("release-week-search-results"))
/*     */     {
/* 119 */       return releaseWeekSearchResults(context);
/*     */     }
/* 121 */     if (command.equalsIgnoreCase("release-week-editor"))
/*     */     {
/* 123 */       return releaseWeekEditor(context);
/*     */     }
/* 125 */     if (command.equalsIgnoreCase("report-config-search"))
/*     */     {
/* 127 */       return reportConfigSearch(context);
/*     */     }
/* 129 */     if (command.equalsIgnoreCase("report-config-search-results"))
/*     */     {
/* 131 */       return reportConfigSearchResults(context);
/*     */     }
/* 133 */     if (command.equalsIgnoreCase("report-config-editor"))
/*     */     {
/* 135 */       return reportConfigEditor(context);
/*     */     }
/* 137 */     if (command.equalsIgnoreCase("price-code-search"))
/*     */     {
/* 139 */       return priceCodeSearch(context);
/*     */     }
/* 141 */     if (command.equalsIgnoreCase("price-code-search-results"))
/*     */     {
/* 143 */       return priceCodeSearchResults(context);
/*     */     }
/* 145 */     if (command.equalsIgnoreCase("price-code-editor"))
/*     */     {
/* 147 */       return priceCodeEditor(context);
/*     */     }
/*     */     
/* 150 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 155 */   protected boolean tablesHeaderSearch(Context context) { return context.includeJSP("tables-header-search.jsp"); }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 160 */   protected boolean tablesHeaderSearchResults(Context context) { return context.includeJSP("tables-header-search-results.jsp"); }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 165 */   protected boolean tablesHeaderEditor(Context context) { return context.includeJSP("tables-header-editor.jsp"); }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 170 */   protected boolean releaseWeekSearch(Context context) { return context.includeJSP("release-week-search.jsp"); }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 175 */   protected boolean releaseWeekSearchResults(Context context) { return context.includeJSP("release-week-search-results.jsp"); }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 180 */   protected boolean releaseWeekEditor(Context context) { return context.includeJSP("release-week-editor.jsp"); }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 185 */   protected boolean reportConfigSearch(Context context) { return context.includeJSP("report-config-search.jsp"); }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 190 */   protected boolean reportConfigSearchResults(Context context) { return context.includeJSP("report-config-results.jsp"); }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 195 */   protected boolean reportConfigEditor(Context context) { return context.includeJSP("report-config-editor.jsp"); }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 200 */   protected boolean priceCodeSearch(Context context) { return context.includeJSP("price-code-search.jsp"); }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 205 */   protected boolean priceCodeSearchResults(Context context) { return context.includeJSP("price-code-results.jsp"); }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 210 */   protected boolean priceCodeEditor(Context context) { return context.includeJSP("price-code-editor.jsp"); }
/*     */ }


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\UtilityHandler.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */