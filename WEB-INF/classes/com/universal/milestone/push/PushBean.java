/*     */ package WEB-INF.classes.com.universal.milestone.push;
/*     */ 
/*     */ import com.softwareag.entirex.aci.Broker;
/*     */ import com.softwareag.entirex.aci.BrokerException;
/*     */ import com.softwareag.entirex.aci.BrokerMessage;
/*     */ import com.softwareag.entirex.aci.BrokerService;
/*     */ import com.softwareag.entirex.aci.Conversation;
/*     */ import com.universal.milestone.push.PushBean;
/*     */ import com.universal.milestone.push.PushCommunication;
/*     */ import com.universal.milestone.xml.XMLUtil;
/*     */ import java.util.Properties;
/*     */ import java.util.StringTokenizer;
/*     */ import javax.ejb.CreateException;
/*     */ import javax.ejb.SessionBean;
/*     */ import javax.ejb.SessionContext;
/*     */ import javax.mail.Message;
/*     */ import javax.mail.Session;
/*     */ import javax.mail.Transport;
/*     */ import javax.mail.internet.InternetAddress;
/*     */ import javax.mail.internet.MimeMessage;
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
/*     */ public class PushBean
/*     */   implements SessionBean
/*     */ {
/*     */   private SessionContext ctx;
/*  45 */   protected String sServer = "";
/*  46 */   protected String brokerID = "";
/*  47 */   protected String adminEmailAddr = "Milestone";
/*  48 */   protected String adminEmailName = "Milestone";
/*  49 */   protected String destEmailAddr = "mark.cole@umusic.com";
/*  50 */   protected String ccEmailAddr = "marla.hall@umusic.com; tommy.thomas@umusic.com;";
/*  51 */   protected String emailSubject = "";
/*  52 */   protected String emailMessage = "";
/*  53 */   protected String emailHostServer = "";
/*  54 */   protected String emailContentType = "text/html";
/*  55 */   protected String defaultWaitTime = "60s";
/*  56 */   protected String MilestoneServer = "";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  65 */   public void ejbActivate() { PushCommunication.log("ejbActivate called"); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  73 */   public void ejbRemove() { PushCommunication.log("ejbRemove called"); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  81 */   public void ejbPassivate() { PushCommunication.log("ejbPassivate called"); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSessionContext(SessionContext ctx) {
/*  89 */     PushCommunication.log("setSessionContext called");
/*  90 */     this.ctx = ctx;
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
/* 104 */     PushCommunication.log("ejbCreate called");
/*     */     
/* 106 */     XMLUtil xmlUtil = new XMLUtil("pushbean.config");
/*     */     
/* 108 */     if (xmlUtil.getDocument() != null) {
/*     */ 
/*     */ 
/*     */       
/* 112 */       this.sServer = xmlUtil.getElementValueByParentList(PushCommunication.legacyNodeList, "server");
/* 113 */       this.brokerID = xmlUtil.getElementValueByParentList(PushCommunication.legacyNodeList, "broker-id");
/* 114 */       this.defaultWaitTime = xmlUtil.getElementValueByParentList(PushCommunication.legacyNodeList, "default-wait-time", this.defaultWaitTime);
/*     */ 
/*     */       
/* 117 */       this.adminEmailAddr = xmlUtil.getElementValueByParentList(PushCommunication.emailNodeList, "admin-addr", this.adminEmailAddr);
/* 118 */       this.adminEmailName = xmlUtil.getElementValueByParentList(PushCommunication.emailNodeList, "admin-name", this.adminEmailName);
/* 119 */       this.emailSubject = xmlUtil.getElementValueByParentList(PushCommunication.emailNodeList, "subject");
/* 120 */       this.emailMessage = xmlUtil.getElementValueByParentList(PushCommunication.emailNodeList, "message");
/* 121 */       this.emailHostServer = xmlUtil.getElementValueByParentList(PushCommunication.emailNodeList, "host-server");
/* 122 */       this.emailContentType = xmlUtil.getElementValueByParentList(PushCommunication.emailNodeList, "content-type", this.emailContentType);
/* 123 */       this.destEmailAddr = xmlUtil.getElementValueByParentList(PushCommunication.emailNodeList, "to-list");
/* 124 */       this.ccEmailAddr = xmlUtil.getElementValueByParentList(PushCommunication.emailNodeList, "cc-list");
/*     */ 
/*     */       
/* 127 */       this.MilestoneServer = xmlUtil.getElementValueByParentList(PushCommunication.milestoneNodeList, "server", "");
/*     */     } else {
/*     */       
/* 130 */       sendJavaMailOpt("** PushBean unable to initialize **\n\nUnable to find document", "Initialize");
/*     */     } 
/*     */     try {
/* 133 */       InitialContext ic = new InitialContext();
/* 134 */     } catch (NamingException ne) {
/* 135 */       throw new CreateException("PushBean Failed to Initialize Context " + ne);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 142 */   public String TestEJB() { return "This is a test of PushBean's method TestEJB()"; }
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
/*     */   public String SendPFM(String pMessage, String userId) {
/* 157 */     PushCommunication.log("SendPFM called");
/* 158 */     PushCommunication.log("server: " + this.sServer);
/* 159 */     PushCommunication.log("broker Id: " + this.brokerID);
/* 160 */     PushCommunication.log("pMessage: " + pMessage);
/*     */ 
/*     */     
/*     */     try {
/* 164 */       return sendRequest(pMessage, userId);
/*     */     }
/* 166 */     catch (BrokerException bE) {
/* 167 */       PushCommunication.log(bE.toString());
/* 168 */       sendJavaMailOpt(bE.toString(), userId);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 173 */       return "BROKER ERROR: ** PFM PUSH ** " + bE.toString();
/*     */     }
/* 175 */     catch (Exception ae) {
/*     */       
/* 177 */       PushCommunication.log(ae.getMessage());
/* 178 */       sendJavaMailOpt(ae.getMessage(), userId);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 183 */       return "BROKER ERROR: ** PFM PUSH ** " + ae.getMessage();
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
/*     */   private String sendRequest(String pMessage, String userID) {
/* 208 */     Broker broker = new Broker(this.brokerID, "JavaUser");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 214 */     BrokerService bService = new BrokerService(broker, this.sServer);
/*     */ 
/*     */     
/* 217 */     bService.setDefaultWaittime(this.defaultWaitTime);
/*     */ 
/*     */     
/* 220 */     BrokerMessage bRequest = new BrokerMessage();
/*     */     
/* 222 */     bService.setMaxReceiveLen(550);
/*     */ 
/*     */     
/* 225 */     broker.logon();
/*     */ 
/*     */ 
/*     */     
/* 229 */     PushCommunication.log("Communication started: " + broker.getConnInfo());
/*     */     
/* 231 */     Conversation conv = new Conversation(bService);
/*     */     
/* 233 */     bRequest.setMessage(pMessage);
/*     */     
/* 235 */     PushCommunication.log("Sent: " + bRequest);
/*     */     
/* 237 */     BrokerMessage bReply = conv.sendReceive(bRequest);
/*     */     
/* 239 */     PushCommunication.log("Returned: " + bReply);
/*     */ 
/*     */     
/* 242 */     conv.end();
/* 243 */     broker.logoff();
/* 244 */     broker.disconnect();
/*     */     
/* 246 */     return bReply.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean sendJavaMailOpt(String brokerExcMsg, String userID) {
/* 256 */     boolean sendStatus = false;
/*     */ 
/*     */     
/* 259 */     StringTokenizer Addrtokenizer = new StringTokenizer(this.destEmailAddr, ";");
/* 260 */     StringTokenizer ccAddrtokenizer = new StringTokenizer(this.ccEmailAddr, ";");
/*     */     
/* 262 */     InternetAddress[] iAddresses = new InternetAddress[Addrtokenizer.countTokens()];
/* 263 */     InternetAddress[] ccAddresses = new InternetAddress[ccAddrtokenizer.countTokens()];
/*     */     
/* 265 */     int x = 0;
/* 266 */     while (Addrtokenizer.hasMoreTokens()) {
/*     */       try {
/* 268 */         iAddresses[x] = new InternetAddress(Addrtokenizer.nextToken());
/* 269 */         x++;
/* 270 */       } catch (Exception e) {
/* 271 */         PushCommunication.log(e.toString());
/* 272 */         return sendStatus;
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 277 */     x = 0;
/* 278 */     while (ccAddrtokenizer.hasMoreTokens()) {
/*     */       try {
/* 280 */         ccAddresses[x] = new InternetAddress(ccAddrtokenizer.nextToken());
/* 281 */         x++;
/* 282 */       } catch (Exception e) {
/* 283 */         PushCommunication.log(e.toString());
/* 284 */         return sendStatus;
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/*     */     try {
/* 290 */       Properties props = System.getProperties();
/*     */ 
/*     */ 
/*     */       
/* 294 */       props.put("mail.smtp.host", this.emailHostServer.trim());
/*     */ 
/*     */       
/* 297 */       Session session = Session.getDefaultInstance(props, null);
/*     */ 
/*     */       
/* 300 */       MimeMessage message = new MimeMessage(session);
/*     */ 
/*     */       
/* 303 */       message.setFrom(new InternetAddress(this.adminEmailAddr, this.adminEmailName));
/*     */ 
/*     */ 
/*     */       
/* 307 */       String subject = this.emailSubject;
/* 308 */       if (subject != null && subject.length() > 0) {
/* 309 */         message.setSubject(subject, "utf-8");
/*     */       } else {
/* 311 */         message.setSubject(this.emailSubject, "utf-8");
/*     */       } 
/*     */       
/* 314 */       String emailBody = "<b>" + this.emailMessage + "</b><BR><BR>" + 
/* 315 */         "<font color='#0000FF'><B> MainFrame Server: " + this.sServer + "</B>:</font><BR>" + 
/* 316 */         "<BR><BR><font color='#0000FF'><B> Milestone Server: " + this.MilestoneServer + "</B>:</font>" + 
/* 317 */         "<BR><BR><font color='#0000FF'><B> User:   " + userID + "</B>:</font>" + 
/* 318 */         "<BR><BR><b>" + brokerExcMsg + "</b>";
/*     */ 
/*     */ 
/*     */       
/* 322 */       message.setContent(emailBody, this.emailContentType);
/*     */ 
/*     */ 
/*     */       
/*     */       try {
/* 327 */         message.addRecipients(Message.RecipientType.CC, ccAddresses);
/*     */ 
/*     */         
/* 330 */         message.addRecipients(Message.RecipientType.TO, iAddresses);
/*     */ 
/*     */         
/* 333 */         Transport.send(message);
/* 334 */         PushCommunication.log("<<< Session Email Address - Java Mail - Message Sent");
/*     */         
/* 336 */         sendStatus = true;
/*     */       }
/* 338 */       catch (Exception e) {
/* 339 */         e.printStackTrace();
/* 340 */         PushCommunication.log("<<< Session Email Transport exception " + e.getMessage() + ", " + e.toString());
/* 341 */         sendStatus = false;
/*     */       } 
/* 343 */     } catch (Exception e) {
/* 344 */       PushCommunication.log("<<< Session Email Transport exception " + e.getMessage());
/* 345 */       sendStatus = false;
/*     */     } 
/*     */     
/* 348 */     return sendStatus;
/*     */   }
/*     */ }


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\push\PushBean.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */