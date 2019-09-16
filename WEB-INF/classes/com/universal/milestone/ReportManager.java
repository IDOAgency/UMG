package WEB-INF.classes.com.universal.milestone;

import com.techempower.gemini.Context;
import com.universal.milestone.Environment;
import com.universal.milestone.JdbcConnector;
import com.universal.milestone.MilestoneHelper;
import com.universal.milestone.Notepad;
import com.universal.milestone.Report;
import com.universal.milestone.ReportManager;
import inetsoft.report.PreviewView;
import inetsoft.report.Previewer;
import inetsoft.report.XStyleSheet;
import inetsoft.report.io.Builder;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.Vector;

public class ReportManager {
  protected static final ReportManager reportManager = new ReportManager();
  
  public static final String COMPONENT_CODE = "repm";
  
  public static final String DEFAULT_QUERY = "SELECT DISTINCT report_id, report_name, file_name, description, report_owner FROM vi_Report_Config where report_status = 'ACTIVE' ";
  
  public static final String DEFAULT_ORDER = " ORDER BY description";
  
  public static ReportManager getInstance() { return reportManager; }
  
  public boolean printReport() {
    String billing = "John Glennon\n1234 Corporate Drive\nComptown, NJ 07796";
    String shipping = "Shipping/Receiving\n1234 Corporate Drive\nComptown, NJ 07796";
    Object[][] orderinfo = { {}, { new Date(), new Date(), "Net 30 days", "11039893" } };
    Object[][] iteminfo = { {}, { "Style Report/Pro", "SR11P", new Double(495.0D), new Integer(2), 
          new Double(990.0D) }, {}, {}, {}, {}, { null, null, null, "Total:", new Double(990.0D) } };
    try {
      InputStream input = new FileInputStream("D:\\Projects\\Universal\\Milestone v2\\Reports\\invoice1.xml");
      XStyleSheet report = 
        (XStyleSheet)Builder.getBuilder(1, input).read(null);
      report.setElement("billTo", billing);
      report.setElement("shipTo", shipping);
      report.setElement("ordertbl", orderinfo);
      report.setElement("itemtbl", iteminfo);
      report.getTableStyle("ordertbl")
        .setRowAlignment(0, 2);
      report.getTableStyle("itemtbl")
        .setRowAlignment(0, 2);
      report.addFormat("ordertbl", Date.class, 
          new SimpleDateFormat("MMM d, yyyy"));
      report.addFormat("itemtbl", Double.class, 
          NumberFormat.getCurrencyInstance());
      PreviewView previewer = Previewer.createPreviewer();
      previewer.pack();
      previewer.print(report);
      previewer.setVisible(true);
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return true;
  }
  
  public void setNotepadQuery(Context context, int UserId, Notepad notepad) {
    if (notepad != null) {
      String reportDescriptionSearch = context.getParameter("ReportDescriptionSearch");
      String reportQuery = "SELECT DISTINCT report_id, report_name, file_name, description, report_owner FROM vi_Report_Config where report_status = 'ACTIVE' ";
      if (MilestoneHelper.isStringNotEmpty(reportDescriptionSearch))
        reportQuery = String.valueOf(reportQuery) + MilestoneHelper.addQueryParams(reportQuery, " description " + MilestoneHelper.setWildCardsEscapeSingleQuotes(reportDescriptionSearch)); 
      String order = " ORDER BY description";
      notepad.setSearchQuery(reportQuery);
      notepad.setOrderBy(order);
    } 
  }
  
  public Vector getReportNotepadList(Notepad notepad) {
    String reportQuery = "";
    if (notepad != null && !notepad.getSearchQuery().equals("")) {
      reportQuery = notepad.getSearchQuery();
      reportQuery = String.valueOf(reportQuery) + notepad.getOrderBy();
    } else {
      reportQuery = "SELECT DISTINCT report_id, report_name, file_name, description, report_owner FROM vi_Report_Config where report_status = 'ACTIVE'  ORDER BY description";
    } 
    Report reportConfig = null;
    Vector precache = new Vector();
    JdbcConnector connector = MilestoneHelper.getConnector(reportQuery);
    connector.runQuery();
    while (connector.more()) {
      reportConfig = getNotepadReport(connector);
      precache.addElement(reportConfig);
      reportConfig = null;
      connector.next();
    } 
    connector.close();
    return precache;
  }
  
  private Report getNotepadReport(JdbcConnector connector) {
    Report report = null;
    if (connector != null) {
      report = new Report(connector.getIntegerField("report_id"));
      report.setReportName(connector.getFieldByName("report_name"));
      report.setDescription(connector.getFieldByName("description"));
      report.setFileName(connector.getFieldByName("file_name"));
    } 
    return report;
  }
  
  public Vector getReportNotepadList(Notepad notepad, Context context) {
    String reportQuery = "";
    Vector environments = MilestoneHelper.getUserEnvironments(context);
    String familyWhere = "";
    familyWhere = String.valueOf(familyWhere) + " and ( report_owner IS NULL OR  report_owner = 0 OR report_owner = -1 ";
    for (int i = 0; i < environments.size(); i++) {
      Environment environment = (Environment)environments.get(i);
      familyWhere = String.valueOf(familyWhere) + " OR report_owner = " + environment.getParent().getStructureID();
    } 
    familyWhere = String.valueOf(familyWhere) + " )";
    if (notepad != null && !notepad.getSearchQuery().equals("")) {
      reportQuery = String.valueOf(notepad.getSearchQuery()) + familyWhere;
      reportQuery = String.valueOf(reportQuery) + notepad.getOrderBy();
    } else {
      reportQuery = "SELECT DISTINCT report_id, report_name, file_name, description, report_owner FROM vi_Report_Config where report_status = 'ACTIVE' " + familyWhere + " ORDER By Description";
    } 
    Report reportConfig = null;
    Vector precache = new Vector();
    JdbcConnector connector = MilestoneHelper.getConnector(reportQuery);
    connector.runQuery();
    while (connector.more()) {
      reportConfig = getNotepadReport(connector);
      int owner = connector.getInt("report_owner", 0);
      if (owner == 12)
        reportConfig.order = owner; 
      precache.addElement(reportConfig);
      reportConfig = null;
      connector.next();
    } 
    connector.close();
    Collections.sort(precache);
    return precache;
  }
}


/* Location:              D:\Documents\NetBeansProjects\milestone2 Local.war!\WEB-INF\classes\co\\universal\milestone\ReportManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */