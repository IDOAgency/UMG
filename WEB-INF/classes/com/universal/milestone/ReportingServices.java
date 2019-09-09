/*     */ package WEB-INF.classes.com.universal.milestone;
/*     */ 
/*     */ import com.techempower.gemini.Context;
/*     */ import com.universal.milestone.JdbcConnector;
/*     */ import com.universal.milestone.MilestoneHelper;
/*     */ import com.universal.milestone.Report;
/*     */ import com.universal.milestone.ReportingServices;
/*     */ import com.universal.milestone.User;
/*     */ import java.util.Calendar;
/*     */ import java.util.Hashtable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ReportingServices
/*     */ {
/*  29 */   public static Hashtable reportServicesHash = new Hashtable();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean execUsingReportServices(String reportName, StringBuffer query, Context context, String[] reportParms) {
/*  39 */     boolean returnFlag = false;
/*     */ 
/*     */     
/*  42 */     String reportURL = getReportServicesURL(reportName);
/*  43 */     if (reportURL != null && !reportURL.equals("")) {
/*     */       
/*  45 */       returnFlag = true;
/*  46 */       execReportServices(query, context, reportParms, reportURL);
/*     */     } 
/*  48 */     return returnFlag;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void getReportServicesByContext(Context context) {
/*  58 */     Report report = (Report)context.getSessionValue("report");
/*  59 */     getReportServices(report.getReportName());
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
/*     */   public static void getReportServices(String reportName) {
/*  71 */     String reportURL = "";
/*     */ 
/*     */     
/*  74 */     String sqlStr = "select reportingServicesReportName from dbo.report_config where reportingServicesActive = 1 and [name] = '" + 
/*  75 */       reportName + "'";
/*  76 */     JdbcConnector connector = MilestoneHelper.getConnector(sqlStr);
/*     */     
/*  78 */     connector.runQuery();
/*     */ 
/*     */     
/*  81 */     if (connector.more()) {
/*  82 */       reportURL = connector.getField("reportingServicesReportName", "");
/*     */     }
/*  84 */     connector.close();
/*     */     
/*  86 */     if (reportURL != null && !reportURL.equals("")) {
/*     */ 
/*     */       
/*  89 */       reportServicesHash.put(reportName, reportURL);
/*     */ 
/*     */ 
/*     */     
/*     */     }
/*  94 */     else if (reportServicesHash.containsKey(reportName)) {
/*  95 */       reportServicesHash.remove(reportName);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean usingReportServicesByContext(Context context) {
/* 106 */     Report report = (Report)context.getSessionValue("report");
/* 107 */     if (report.getReportName() != null && usingReportServices(report.getReportName())) {
/* 108 */       return true;
/*     */     }
/* 110 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean usingReportServices(String reportName) {
/* 120 */     if (reportName != null && reportServicesHash.get(reportName) != null) {
/* 121 */       return true;
/*     */     }
/* 123 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 132 */   public static String getReportServicesURL(String reportName) { return (String)reportServicesHash.get(reportName); }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void execReportServices(StringBuffer query, Context context, String[] reportParms, String reportName) {
/* 138 */     String whereStr = "";
/* 139 */     int wp = query.toString().toUpperCase().indexOf("WHERE ");
/*     */     
/* 141 */     if (wp != -1) {
/*     */       
/* 143 */       int op = query.toString().toUpperCase().indexOf("ORDER BY ");
/*     */       
/* 145 */       if (op != -1) {
/* 146 */         whereStr = query.toString().substring(wp + 5, op);
/*     */       } else {
/* 148 */         whereStr = query.toString().substring(wp);
/*     */       } 
/* 150 */       User user = (User)context.getSessionValue("user");
/* 151 */       String reportId = String.valueOf(user.getLogin()) + "_" + user.getUserId() + "_" + Calendar.getInstance().getTime().getTime();
/*     */       
/* 153 */       String sqlStr = "INSERT INTO [dbo].[ReportServiceParms] ([report_id], [whereString],[dateString1], [dateString2], [report_name], [entered_by]) VALUES('" + 
/*     */         
/* 155 */         MilestoneHelper.escapeSingleQuotes(reportId) + "'," + 
/* 156 */         "'" + MilestoneHelper.escapeSingleQuotes(whereStr) + "'," + 
/* 157 */         "'" + MilestoneHelper.escapeSingleQuotes(reportParms[0]) + "'," + 
/* 158 */         "'" + MilestoneHelper.escapeSingleQuotes(reportParms[1]) + "'," + 
/* 159 */         "'" + MilestoneHelper.escapeSingleQuotes(reportName) + "'," + 
/* 160 */         user.getUserId() + ")";
/* 161 */       JdbcConnector connector = MilestoneHelper.getConnector(sqlStr);
/* 162 */       connector.runQuery();
/* 163 */       connector.close();
/* 164 */       context.putDelivery("ReportServicesReportId", reportId);
/* 165 */       context.putDelivery("ReportServicesReportName", reportName);
/* 166 */       context.includeJSP("ReportServices.jsp");
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\ReportingServices.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */