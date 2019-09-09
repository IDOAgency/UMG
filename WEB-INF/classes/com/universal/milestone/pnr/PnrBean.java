/*     */ package WEB-INF.classes.com.universal.milestone.pnr;
/*     */ 
/*     */ import com.softwareag.entirex.aci.Broker;
/*     */ import com.softwareag.entirex.aci.BrokerException;
/*     */ import com.softwareag.entirex.aci.BrokerMessage;
/*     */ import com.softwareag.entirex.aci.BrokerService;
/*     */ import com.softwareag.entirex.aci.Conversation;
/*     */ import com.universal.milestone.pnr.PnrBean;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.InputStream;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.Date;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PnrBean
/*     */   implements SessionBean
/*     */ {
/*     */   private static final boolean VERBOSE = false;
/*     */   private SessionContext ctx;
/*  51 */   protected String sServer = "";
/*  52 */   protected String brokerID = "";
/*  53 */   protected int maxReceiveLen = 550;
/*  54 */   public String configFile = "pnrbean.conf";
/*  55 */   protected String adminEmailAddr = "Milestone";
/*  56 */   protected String adminEmailName = "Milestone";
/*  57 */   protected String destEmailAddr = "";
/*  58 */   protected String ccEmailAddr = "";
/*  59 */   protected String emailSubject = "";
/*  60 */   protected String emailMessage = "";
/*  61 */   protected String emailHostServer = "";
/*  62 */   protected String emailContentType = "text/html";
/*     */   protected boolean debug = false;
/*  64 */   protected String defaultWaitTime = "60s";
/*  65 */   protected static String MilestoneServer = "";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void log(String s) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  76 */   public void ejbActivate() { log("ejbActivate called"); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  84 */   public void ejbRemove() { log("ejbRemove called"); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  92 */   public void ejbPassivate() { log("ejbPassivate called"); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSessionContext(SessionContext ctx) {
/* 100 */     log("setSessionContext called");
/* 101 */     this.ctx = ctx;
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
/* 115 */     log("ejbCreate called");
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 120 */       InputStream in = ClassLoader.getSystemResourceAsStream(this.configFile);
/*     */ 
/*     */       
/* 123 */       if (in == null) {
/* 124 */         in = new FileInputStream(this.configFile);
/*     */       }
/* 126 */       Properties defaultProps = new Properties();
/* 127 */       defaultProps.load(in);
/*     */ 
/*     */       
/* 130 */       this.sServer = defaultProps.getProperty("LegacyServer");
/* 131 */       this.brokerID = defaultProps.getProperty("LegacyBrokerID");
/*     */       
/* 133 */       this.adminEmailAddr = defaultProps.getProperty("AdminEmailAddr");
/* 134 */       this.adminEmailName = defaultProps.getProperty("AdminEmailName");
/* 135 */       this.emailSubject = defaultProps.getProperty("EmailSubject");
/* 136 */       this.emailMessage = defaultProps.getProperty("EmailMessage");
/* 137 */       this.emailHostServer = defaultProps.getProperty("EmailHostServer");
/* 138 */       this.emailContentType = defaultProps.getProperty("EmailContentType");
/* 139 */       this.destEmailAddr = defaultProps.getProperty("EmailDestList");
/* 140 */       this.ccEmailAddr = defaultProps.getProperty("EmailCCList");
/* 141 */       this.defaultWaitTime = defaultProps.getProperty("LegacyDefaultWaitTime", "25s");
/* 142 */       MilestoneServer = defaultProps.getProperty("MilestoneServer", "");
/*     */       
/* 144 */       in.close();
/* 145 */     } catch (Exception exception) {}
/*     */ 
/*     */     
/*     */     try {
/* 149 */       InitialContext ic = new InitialContext();
/* 150 */     } catch (NamingException ne) {
/* 151 */       throw new CreateException("Failed to find environment value " + ne);
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
/*     */   public String getPnr(String userID, String operatingCompany, String superLabel, String configCode, String titleID, String title, String artistFName, String artistLName, Date releaseDate, String labelCode, String projectID, String upc, String soundScan) {
/*     */     try {
/* 172 */       log("getPnr called");
/* 173 */       log("server: " + this.sServer);
/* 174 */       String pMessage = "";
/*     */       
/* 176 */       pMessage = String.valueOf(pMessage) + formatData("FM510N", 8, " ");
/* 177 */       pMessage = String.valueOf(pMessage) + formatData(userID, 8, " ");
/* 178 */       pMessage = String.valueOf(pMessage) + formatData(operatingCompany, 2, " ");
/*     */       
/* 180 */       pMessage = String.valueOf(pMessage) + formatData(superLabel, 3, " ");
/*     */       
/* 182 */       pMessage = String.valueOf(pMessage) + formatData(configCode, 2, " ");
/* 183 */       pMessage = String.valueOf(pMessage) + formatData(titleID, 10, " ");
/* 184 */       pMessage = String.valueOf(pMessage) + formatData("", 10, " ");
/* 185 */       pMessage = String.valueOf(pMessage) + formatData(title, 70, " ");
/* 186 */       pMessage = String.valueOf(pMessage) + formatData(artistFName, 20, " ");
/* 187 */       pMessage = String.valueOf(pMessage) + formatData(artistLName, 50, " ");
/*     */       
/* 189 */       SimpleDateFormat adf = new SimpleDateFormat("mmddyy");
/* 190 */       pMessage = String.valueOf(pMessage) + formatData(adf.format(releaseDate), 6, " ");
/*     */       
/* 192 */       pMessage = String.valueOf(pMessage) + formatData(labelCode, 3, " ");
/* 193 */       pMessage = String.valueOf(pMessage) + formatData(formatData(projectID, 9, "0"), 12, " ");
/* 194 */       pMessage = String.valueOf(pMessage) + formatData(upc, 14, "0");
/* 195 */       pMessage = String.valueOf(pMessage) + formatData(soundScan, 14, "0");
/* 196 */       pMessage = String.valueOf(pMessage) + formatData("", 20, " ");
/* 197 */       pMessage = String.valueOf(pMessage) + formatData("", 20, " ");
/* 198 */       pMessage = String.valueOf(pMessage) + formatData("", 20, " ");
/* 199 */       pMessage = String.valueOf(pMessage) + formatData("", 20, " ");
/* 200 */       pMessage = String.valueOf(pMessage) + formatData("", 200, " ");
/*     */ 
/*     */       
/* 203 */       log("pMessage: " + pMessage);
/*     */       
/* 205 */       return sendRequest(pMessage, userID);
/*     */     }
/* 207 */     catch (BrokerException bE) {
/* 208 */       System.out.println(bE.toString());
/* 209 */       sendJavaMailOpt(bE.toString(), userID);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 214 */       return "BROKER ERROR: " + bE.toString();
/*     */     }
/* 216 */     catch (Exception ae) {
/*     */       
/* 218 */       System.out.println(ae.getMessage());
/* 219 */       sendJavaMailOpt(ae.getMessage(), userID);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 224 */       return "BROKER ERROR: " + ae.getMessage();
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
/*     */   private String formatData(String p1, int length, String filler) {
/* 239 */     String r1 = "";
/*     */ 
/*     */ 
/*     */     
/* 243 */     if (p1 == null || p1.equalsIgnoreCase("")) {
/*     */       
/* 245 */       for (int i = 0; i < length; i++)
/* 246 */         r1 = String.valueOf(r1) + " "; 
/* 247 */       return r1;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 252 */     if (p1.length() > length)
/*     */     {
/* 254 */       return p1.substring(0, length);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 262 */     for (int i = 0; i < length - p1.length(); i++) {
/* 263 */       r1 = String.valueOf(r1) + filler;
/*     */     }
/* 265 */     if (filler.equalsIgnoreCase("0")) {
/* 266 */       return String.valueOf(r1) + p1;
/*     */     }
/* 268 */     return String.valueOf(p1) + r1;
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
/*     */   private String sendRequest(String pMessage, String userID) throws BrokerException {
/* 292 */     Broker broker = new Broker(this.brokerID, "JavaUser");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 298 */     BrokerService bService = new BrokerService(broker, this.sServer);
/*     */ 
/*     */     
/* 301 */     bService.setDefaultWaittime(this.defaultWaitTime);
/*     */ 
/*     */     
/* 304 */     BrokerMessage bRequest = new BrokerMessage();
/*     */     
/* 306 */     bService.setMaxReceiveLen(this.maxReceiveLen);
/*     */ 
/*     */     
/* 309 */     broker.logon();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 315 */     Conversation conv = new Conversation(bService);
/*     */     
/* 317 */     bRequest.setMessage(pMessage);
/*     */ 
/*     */ 
/*     */     
/* 321 */     BrokerMessage bReply = conv.sendReceive(bRequest);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 326 */     conv.end();
/* 327 */     broker.logoff();
/* 328 */     broker.disconnect();
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
/* 343 */     return bReply.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean sendJavaMailOpt(String brokerExcMsg, String userID) {
/* 353 */     boolean sendStatus = false;
/*     */ 
/*     */     
/* 356 */     StringTokenizer Addrtokenizer = new StringTokenizer(this.destEmailAddr, ";");
/* 357 */     StringTokenizer ccAddrtokenizer = new StringTokenizer(this.ccEmailAddr, ";");
/*     */     
/* 359 */     InternetAddress[] iAddresses = new InternetAddress[Addrtokenizer.countTokens()];
/* 360 */     InternetAddress[] ccAddresses = new InternetAddress[ccAddrtokenizer.countTokens()];
/*     */     
/* 362 */     int x = 0;
/* 363 */     while (Addrtokenizer.hasMoreTokens()) {
/*     */       try {
/* 365 */         iAddresses[x] = new InternetAddress(Addrtokenizer.nextToken());
/* 366 */         x++;
/* 367 */       } catch (Exception e) {
/* 368 */         System.out.println(e.toString());
/* 369 */         return sendStatus;
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 374 */     x = 0;
/* 375 */     while (ccAddrtokenizer.hasMoreTokens()) {
/*     */       try {
/* 377 */         ccAddresses[x] = new InternetAddress(ccAddrtokenizer.nextToken());
/* 378 */         x++;
/* 379 */       } catch (Exception e) {
/* 380 */         System.out.println(e.toString());
/* 381 */         return sendStatus;
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/*     */     try {
/* 387 */       Properties props = System.getProperties();
/*     */ 
/*     */ 
/*     */       
/* 391 */       props.put("mail.smtp.host", this.emailHostServer.trim());
/*     */ 
/*     */       
/* 394 */       Session session = Session.getDefaultInstance(props, null);
/*     */ 
/*     */       
/* 397 */       MimeMessage message = new MimeMessage(session);
/*     */ 
/*     */       
/* 400 */       message.setFrom(new InternetAddress(this.adminEmailAddr, this.adminEmailName));
/*     */ 
/*     */ 
/*     */       
/* 404 */       String subject = this.emailSubject;
/* 405 */       if (subject != null && subject.length() > 0) {
/* 406 */         message.setSubject(subject, "utf-8");
/*     */       } else {
/* 408 */         message.setSubject(this.emailSubject, "utf-8");
/*     */       } 
/*     */       
/* 411 */       String emailBody = "<b>" + this.emailMessage + "</b><BR><BR>" + 
/* 412 */         "<font color='#0000FF'><B> MainFrame Server: " + this.sServer + "</B>:</font><BR>" + 
/* 413 */         "<BR><BR><font color='#0000FF'><B> Milestone Server: " + MilestoneServer + "</B>:</font>" + 
/* 414 */         "<BR><BR><font color='#0000FF'><B> User:   " + userID + "</B>:</font>" + 
/* 415 */         "<BR><BR><b>" + brokerExcMsg + "</b>";
/*     */ 
/*     */ 
/*     */       
/* 419 */       message.setContent(emailBody, this.emailContentType);
/*     */ 
/*     */ 
/*     */       
/*     */       try {
/* 424 */         message.addRecipients(Message.RecipientType.CC, ccAddresses);
/*     */ 
/*     */         
/* 427 */         message.addRecipients(Message.RecipientType.TO, iAddresses);
/*     */ 
/*     */         
/* 430 */         Transport.send(message);
/*     */ 
/*     */         
/* 433 */         sendStatus = true;
/*     */       }
/* 435 */       catch (Exception e) {
/* 436 */         e.printStackTrace();
/* 437 */         System.out.println("<<< Session Email Transport exception " + e.getMessage() + ", " + e.toString());
/* 438 */         sendStatus = false;
/*     */       } 
/* 440 */     } catch (Exception e) {
/* 441 */       System.out.println("<<< Session Email Transport exception " + e.getMessage());
/* 442 */       sendStatus = false;
/*     */     } 
/*     */     
/* 445 */     return sendStatus;
/*     */   }
/*     */ }


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\pnr\PnrBean.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */