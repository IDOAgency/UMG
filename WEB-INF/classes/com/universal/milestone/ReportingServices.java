package WEB-INF.classes.com.universal.milestone;

import com.techempower.gemini.Context;
import com.universal.milestone.JdbcConnector;
import com.universal.milestone.MilestoneHelper;
import com.universal.milestone.Report;
import com.universal.milestone.ReportingServices;
import com.universal.milestone.User;
import java.util.Calendar;
import java.util.Hashtable;

public class ReportingServices {
  public static Hashtable reportServicesHash = new Hashtable();
  
  public static boolean execUsingReportServices(String reportName, StringBuffer query, Context context, String[] reportParms) {
    boolean returnFlag = false;
    String reportURL = getReportServicesURL(reportName);
    if (reportURL != null && !reportURL.equals("")) {
      returnFlag = true;
      execReportServices(query, context, reportParms, reportURL);
    } 
    return returnFlag;
  }
  
  public static void getReportServicesByContext(Context context) {
    Report report = (Report)context.getSessionValue("report");
    getReportServices(report.getReportName());
  }
  
  public static void getReportServices(String reportName) {
    String reportURL = "";
    String sqlStr = "select reportingServicesReportName from dbo.report_config where reportingServicesActive = 1 and [name] = '" + 
      reportName + "'";
    JdbcConnector connector = MilestoneHelper.getConnector(sqlStr);
    connector.runQuery();
    if (connector.more())
      reportURL = connector.getField("reportingServicesReportName", ""); 
    connector.close();
    if (reportURL != null && !reportURL.equals("")) {
      reportServicesHash.put(reportName, reportURL);
    } else if (reportServicesHash.containsKey(reportName)) {
      reportServicesHash.remove(reportName);
    } 
  }
  
  public static boolean usingReportServicesByContext(Context context) {
    Report report = (Report)context.getSessionValue("report");
    if (report.getReportName() != null && usingReportServices(report.getReportName()))
      return true; 
    return false;
  }
  
  public static boolean usingReportServices(String reportName) {
    if (reportName != null && reportServicesHash.get(reportName) != null)
      return true; 
    return false;
  }
  
  public static String getReportServicesURL(String reportName) { return (String)reportServicesHash.get(reportName); }
  
  public static void execReportServices(StringBuffer query, Context context, String[] reportParms, String reportName) {
    String whereStr = "";
    int wp = query.toString().toUpperCase().indexOf("WHERE ");
    if (wp != -1) {
      int op = query.toString().toUpperCase().indexOf("ORDER BY ");
      if (op != -1) {
        whereStr = query.toString().substring(wp + 5, op);
      } else {
        whereStr = query.toString().substring(wp);
      } 
      User user = (User)context.getSessionValue("user");
      String reportId = String.valueOf(user.getLogin()) + "_" + user.getUserId() + "_" + Calendar.getInstance().getTime().getTime();
      String sqlStr = "INSERT INTO [dbo].[ReportServiceParms] ([report_id], [whereString],[dateString1], [dateString2], [report_name], [entered_by]) VALUES('" + 
        
        MilestoneHelper.escapeSingleQuotes(reportId) + "'," + 
        "'" + MilestoneHelper.escapeSingleQuotes(whereStr) + "'," + 
        "'" + MilestoneHelper.escapeSingleQuotes(reportParms[0]) + "'," + 
        "'" + MilestoneHelper.escapeSingleQuotes(reportParms[1]) + "'," + 
        "'" + MilestoneHelper.escapeSingleQuotes(reportName) + "'," + 
        user.getUserId() + ")";
      JdbcConnector connector = MilestoneHelper.getConnector(sqlStr);
      connector.runQuery();
      connector.close();
      context.putDelivery("ReportServicesReportId", reportId);
      context.putDelivery("ReportServicesReportName", reportName);
      context.includeJSP("ReportServices.jsp");
    } 
  }
}


/* Location:              D:\Documents\NetBeansProjects\milestone2 Local.war!\WEB-INF\classes\co\\universal\milestone\ReportingServices.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */