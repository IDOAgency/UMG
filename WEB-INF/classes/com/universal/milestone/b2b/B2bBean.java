/*     */ package WEB-INF.classes.com.universal.milestone.b2b;
/*     */ 
/*     */ import com.universal.milestone.b2b.B2bBean;
/*     */ import com.universal.milestone.b2b.SqlXML;
/*     */ import java.io.File;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.PrintStream;
/*     */ import javax.ejb.CreateException;
/*     */ import javax.ejb.SessionBean;
/*     */ import javax.ejb.SessionContext;
/*     */ import javax.naming.InitialContext;
/*     */ import javax.naming.NamingException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class B2bBean
/*     */   implements SessionBean
/*     */ {
/*     */   private static final boolean VERBOSE = true;
/*     */   private SessionContext ctx;
/*     */   
/*  27 */   private void log(String s) { System.out.println(s); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  35 */   public void ejbActivate() { log("ejbActivate called"); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  43 */   public void ejbRemove() { log("ejbRemove called"); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  51 */   public void ejbPassivate() { log("ejbPassivate called"); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSessionContext(SessionContext ctx) {
/*  59 */     log("setSessionContext called");
/*  60 */     this.ctx = ctx;
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
/*     */   public void ejbCreate() {
/*  74 */     log("ejbCreate called");
/*     */     try {
/*  76 */       InitialContext ic = new InitialContext();
/*  77 */     } catch (NamingException ne) {
/*  78 */       throw new CreateException("Failed to find environment value " + ne);
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
/*     */   public String getRelease(String full) {
/*  91 */     log("getRelease called");
/*  92 */     SqlXML sqlxml = new SqlXML();
/*     */     
/*  94 */     String sqlstr = "select street_date as 'StreetDate', release_id, international_date as 'InternationalDate', impact_date as 'ImpactDate', status as 'Status', hold_indicator as 'Hold', project_no as 'ProjectID', upc as 'UPC', selection_no as 'SelectionNumber', artist_first_name as 'ArtistFirstName', artist_last_name as 'ArtistLastName', title as 'Title', configuration as 'Configuration', release_type as 'ReleaseType', sub_configuration as 'SubConfiguration', a.[name] as 'Company', b.[name] as 'Division', c.[name] as 'Label', Release_header.last_updated_on as 'LastUpdated', entered_on as 'OriginDate', last_changed_on as 'LastStreetDateUpdated',  territory as 'Territory' from Release_header, structure a, structure b, structure c where street_date is not null and (a.structure_id = company_id and a.type=2) and (b.structure_id = division_id and b.type=3) and (c.structure_id = label_id and c.type=4) and status = 'ACTIVE' order by street_date ";
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
/* 105 */     String providerURL = sqlxml.PROVIDER_URL;
/* 106 */     String serverPath = sqlxml.B2BSERVERPATH;
/* 107 */     String filePath = sqlxml.B2BPATH;
/*     */     
/* 109 */     long milli = System.currentTimeMillis();
/* 110 */     String milliString = Long.toString(milli);
/*     */     
/* 112 */     String serverFileName = "\\\\" + sqlxml.B2BHOSTSERVER + serverPath + milliString + ".xml";
/* 113 */     System.out.println("serverFileName:[" + serverFileName + "]");
/*     */     
/* 115 */     String fileName = String.valueOf(filePath) + milliString + ".xml";
/*     */ 
/*     */     
/* 118 */     System.out.println("****sqlstr:[" + sqlstr);
/* 119 */     String xmlString = sqlxml.getSQLXML(sqlstr);
/*     */ 
/*     */     
/* 122 */     System.out.println("file name:[" + fileName + "]");
/* 123 */     File inputFile = new File(fileName);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 129 */       FileOutputStream out = new FileOutputStream(inputFile);
/*     */       
/* 131 */       PrintStream p = new PrintStream(out);
/* 132 */       p.print(xmlString);
/* 133 */       p.close();
/*     */     }
/* 135 */     catch (Exception e) {
/*     */       
/* 137 */       System.err.println("Error writing to file");
/* 138 */       serverFileName = "error";
/*     */     } 
/*     */     
/* 141 */     return serverFileName;
/*     */   }
/*     */ }


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\b2b\B2bBean.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */