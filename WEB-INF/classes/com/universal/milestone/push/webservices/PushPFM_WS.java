/*     */ package WEB-INF.classes.com.universal.milestone.push.webservices;
/*     */ 
/*     */ import com.techempower.EnhancedProperties;
/*     */ import com.universal.milestone.JdbcConnector;
/*     */ import com.universal.milestone.push.PushCommunication;
/*     */ import com.universal.milestone.push.PushPFM;
/*     */ import com.universal.milestone.push.webservices.PushPFM_WS;
/*     */ import com.universal.milestone.xml.XMLUtil;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import weblogic.webservice.context.ContextNotFoundException;
/*     */ import weblogic.webservice.context.WebServiceContext;
/*     */ import weblogic.webservice.context.WebServiceSession;
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
/*     */ public final class PushPFM_WS
/*     */ {
/*  28 */   private String CONFIGURATION_FILENAME = "milestone.conf";
/*  29 */   private JdbcConnector connector = null;
/*     */   private boolean IsDBConfigured = false;
/*     */   private boolean IsWebConfigured = false;
/*  32 */   private String MilestoneServer = "";
/*     */   public static final String configFile = "pushbean.config";
/*  34 */   public static final String[] milestoneNodeList = { "configuration", "milestone" };
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
/*     */ 
/*     */   
/*     */   public static final String COMPONENT_CODE = "pushPFM_WS";
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
/*     */ 
/*     */ 
/*     */   
/*     */   private void configure() {
/* 126 */     if (JdbcConnector.DATASOURCE == null) {
/*     */       
/*     */       try
/*     */       {
/* 130 */         InputStream is = ClassLoader.getSystemResourceAsStream(this.CONFIGURATION_FILENAME);
/*     */ 
/*     */         
/* 133 */         if (is == null) {
/* 134 */           is = new FileInputStream(this.CONFIGURATION_FILENAME);
/*     */         }
/*     */         
/* 137 */         EnhancedProperties props = new EnhancedProperties();
/* 138 */         props.load(is);
/*     */ 
/*     */         
/* 141 */         is.close();
/*     */ 
/*     */         
/* 144 */         JdbcConnector.getDataSourceProperties(props);
/*     */         
/* 146 */         this.IsDBConfigured = true;
/*     */         
/* 148 */         PushCommunication.log("PushPFM configure() Configuration successfull");
/*     */       
/*     */       }
/* 151 */       catch (IOException ioexc)
/*     */       {
/* 153 */         PushCommunication.log("PushPFM configure() Cannot read configuration file: " + ioexc);
/*     */         
/* 155 */         PushCommunication.WriteCrossRoadsAuditLog("ErrorEmail", "pushPFM_WS", "PushPFM configure() end abnormally: " + this.MilestoneServer, ioexc.toString(), PushPFM.emailSubject);
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/* 160 */       this.IsDBConfigured = true;
/*     */     } 
/*     */   }
/*     */   
/*     */   private void getSessionInfo() {
/*     */     try {
/* 166 */       WebServiceContext wsContext = WebServiceContext.currentContext();
/* 167 */       WebServiceSession webServiceSession = wsContext.getSession();
/*     */ 
/*     */     
/*     */     }
/* 171 */     catch (ContextNotFoundException e) {
/*     */       
/* 173 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void getWegConfig() {
/* 180 */     XMLUtil xmlUtil = new XMLUtil("pushbean.config");
/*     */     
/* 182 */     if (xmlUtil.getDocument() != null)
/*     */     {
/*     */       
/* 185 */       this.MilestoneServer = xmlUtil.getElementValueByParentList(milestoneNodeList, "server", "");
/*     */     }
/*     */     
/* 188 */     this.IsWebConfigured = true;
/*     */   }
/*     */ }


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\push\webservices\PushPFM_WS.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */