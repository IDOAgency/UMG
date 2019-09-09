/*     */ package WEB-INF.classes.com.universal.milestone;
/*     */ 
/*     */ import com.techempower.gemini.Context;
/*     */ import com.universal.milestone.Environment;
/*     */ import com.universal.milestone.JdbcConnector;
/*     */ import com.universal.milestone.MilestoneHelper;
/*     */ import com.universal.milestone.Notepad;
/*     */ import com.universal.milestone.Report;
/*     */ import com.universal.milestone.ReportManager;
/*     */ import inetsoft.report.PreviewView;
/*     */ import inetsoft.report.Previewer;
/*     */ import inetsoft.report.XStyleSheet;
/*     */ import inetsoft.report.io.Builder;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.InputStream;
/*     */ import java.text.NumberFormat;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.Collections;
/*     */ import java.util.Date;
/*     */ import java.util.Vector;
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
/*     */ public class ReportManager
/*     */ {
/*  43 */   protected static final ReportManager reportManager = new ReportManager();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final String COMPONENT_CODE = "repm";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final String DEFAULT_QUERY = "SELECT DISTINCT report_id, report_name, file_name, description, report_owner FROM vi_Report_Config where report_status = 'ACTIVE' ";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final String DEFAULT_ORDER = " ORDER BY description";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  73 */   public static ReportManager getInstance() { return reportManager; }
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
/*     */   public boolean printReport() {
/*  95 */     String billing = "John Glennon\n1234 Corporate Drive\nComptown, NJ 07796";
/*     */     
/*  97 */     String shipping = "Shipping/Receiving\n1234 Corporate Drive\nComptown, NJ 07796";
/*     */     
/*  99 */     Object[][] orderinfo = {
/*     */         {}, {
/* 101 */           new Date(), new Date(), "Net 30 days", "11039893"
/*     */         }
/*     */       };
/* 104 */     Object[][] iteminfo = {
/*     */         {}, {
/* 106 */           "Style Report/Pro", "SR11P", new Double(495.0D), new Integer(2), 
/* 107 */           new Double(990.0D)
/*     */ 
/*     */         
/*     */         },
/*     */         {}, {}, {}, {}, {
/* 112 */           null, null, null, "Total:", new Double(990.0D)
/*     */         }
/*     */       };
/*     */     try {
/* 116 */       InputStream input = new FileInputStream("D:\\Projects\\Universal\\Milestone v2\\Reports\\invoice1.xml");
/* 117 */       XStyleSheet report = 
/* 118 */         (XStyleSheet)Builder.getBuilder(1, input).read(null);
/*     */ 
/*     */ 
/*     */       
/* 122 */       report.setElement("billTo", billing);
/* 123 */       report.setElement("shipTo", shipping);
/* 124 */       report.setElement("ordertbl", orderinfo);
/* 125 */       report.setElement("itemtbl", iteminfo);
/*     */       
/* 127 */       report.getTableStyle("ordertbl")
/* 128 */         .setRowAlignment(0, 2);
/* 129 */       report.getTableStyle("itemtbl")
/* 130 */         .setRowAlignment(0, 2);
/*     */       
/* 132 */       report.addFormat("ordertbl", Date.class, 
/* 133 */           new SimpleDateFormat("MMM d, yyyy"));
/* 134 */       report.addFormat("itemtbl", Double.class, 
/* 135 */           NumberFormat.getCurrencyInstance());
/*     */       
/* 137 */       PreviewView previewer = Previewer.createPreviewer();
/* 138 */       previewer.pack();
/* 139 */       previewer.print(report);
/* 140 */       previewer.setVisible(true);
/* 141 */     } catch (Exception e) {
/* 142 */       e.printStackTrace();
/*     */     } 
/*     */ 
/*     */     
/* 146 */     return true;
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
/*     */   public void setNotepadQuery(Context context, int UserId, Notepad notepad) {
/* 158 */     if (notepad != null) {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 163 */       String reportDescriptionSearch = context.getParameter("ReportDescriptionSearch");
/*     */ 
/*     */       
/* 166 */       String reportQuery = "SELECT DISTINCT report_id, report_name, file_name, description, report_owner FROM vi_Report_Config where report_status = 'ACTIVE' ";
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 171 */       if (MilestoneHelper.isStringNotEmpty(reportDescriptionSearch)) {
/* 172 */         reportQuery = String.valueOf(reportQuery) + MilestoneHelper.addQueryParams(reportQuery, " description " + MilestoneHelper.setWildCardsEscapeSingleQuotes(reportDescriptionSearch));
/*     */       }
/* 174 */       String order = " ORDER BY description";
/*     */       
/* 176 */       notepad.setSearchQuery(reportQuery);
/* 177 */       notepad.setOrderBy(order);
/*     */     } 
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
/*     */   public Vector getReportNotepadList(Notepad notepad) {
/* 191 */     String reportQuery = "";
/*     */     
/* 193 */     if (notepad != null && !notepad.getSearchQuery().equals("")) {
/*     */       
/* 195 */       reportQuery = notepad.getSearchQuery();
/* 196 */       reportQuery = String.valueOf(reportQuery) + notepad.getOrderBy();
/*     */     
/*     */     }
/*     */     else {
/*     */       
/* 201 */       reportQuery = "SELECT DISTINCT report_id, report_name, file_name, description, report_owner FROM vi_Report_Config where report_status = 'ACTIVE'  ORDER BY description";
/*     */     } 
/*     */     
/* 204 */     Report reportConfig = null;
/* 205 */     Vector precache = new Vector();
/* 206 */     JdbcConnector connector = MilestoneHelper.getConnector(reportQuery);
/* 207 */     connector.runQuery();
/*     */     
/* 209 */     while (connector.more()) {
/*     */       
/* 211 */       reportConfig = getNotepadReport(connector);
/*     */       
/* 213 */       precache.addElement(reportConfig);
/* 214 */       reportConfig = null;
/* 215 */       connector.next();
/*     */     } 
/*     */     
/* 218 */     connector.close();
/*     */     
/* 220 */     return precache;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Report getNotepadReport(JdbcConnector connector) {
/* 231 */     Report report = null;
/*     */     
/* 233 */     if (connector != null) {
/*     */       
/* 235 */       report = new Report(connector.getIntegerField("report_id"));
/*     */ 
/*     */       
/* 238 */       report.setReportName(connector.getFieldByName("report_name"));
/*     */ 
/*     */       
/* 241 */       report.setDescription(connector.getFieldByName("description"));
/*     */       
/* 243 */       report.setFileName(connector.getFieldByName("file_name"));
/*     */     } 
/*     */     
/* 246 */     return report;
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
/*     */   public Vector getReportNotepadList(Notepad notepad, Context context) {
/* 261 */     String reportQuery = "";
/*     */ 
/*     */     
/* 264 */     Vector environments = MilestoneHelper.getUserEnvironments(context);
/* 265 */     String familyWhere = "";
/*     */     
/* 267 */     familyWhere = String.valueOf(familyWhere) + " and ( report_owner IS NULL OR  report_owner = 0 OR report_owner = -1 ";
/*     */     
/* 269 */     for (int i = 0; i < environments.size(); i++) {
/*     */       
/* 271 */       Environment environment = (Environment)environments.get(i);
/* 272 */       familyWhere = String.valueOf(familyWhere) + " OR report_owner = " + environment.getParent().getStructureID();
/*     */     } 
/*     */     
/* 275 */     familyWhere = String.valueOf(familyWhere) + " )";
/*     */ 
/*     */ 
/*     */     
/* 279 */     if (notepad != null && !notepad.getSearchQuery().equals("")) {
/*     */ 
/*     */       
/* 282 */       reportQuery = String.valueOf(notepad.getSearchQuery()) + familyWhere;
/*     */ 
/*     */ 
/*     */       
/* 286 */       reportQuery = String.valueOf(reportQuery) + notepad.getOrderBy();
/*     */     
/*     */     }
/*     */     else {
/*     */ 
/*     */       
/* 292 */       reportQuery = "SELECT DISTINCT report_id, report_name, file_name, description, report_owner FROM vi_Report_Config where report_status = 'ACTIVE' " + familyWhere + " ORDER By Description";
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 297 */     Report reportConfig = null;
/* 298 */     Vector precache = new Vector();
/*     */     
/* 300 */     JdbcConnector connector = MilestoneHelper.getConnector(reportQuery);
/* 301 */     connector.runQuery();
/*     */     
/* 303 */     while (connector.more()) {
/*     */ 
/*     */       
/* 306 */       reportConfig = getNotepadReport(connector);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 312 */       int owner = connector.getInt("report_owner", 0);
/*     */       
/* 314 */       if (owner == 12) {
/* 315 */         reportConfig.order = owner;
/*     */       }
/*     */ 
/*     */       
/* 319 */       precache.addElement(reportConfig);
/* 320 */       reportConfig = null;
/* 321 */       connector.next();
/*     */     } 
/*     */     
/* 324 */     connector.close();
/*     */ 
/*     */     
/* 327 */     Collections.sort(precache);
/*     */ 
/*     */     
/* 330 */     return precache;
/*     */   }
/*     */ }


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\ReportManager.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */