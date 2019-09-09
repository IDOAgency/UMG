/*     */ package WEB-INF.classes.com.universal.milestone.jms;
/*     */ 
/*     */ import com.universal.milestone.jms.MessageEmailBean;
/*     */ import com.universal.milestone.jms.MessageObject;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.InputStream;
/*     */ import java.util.Hashtable;
/*     */ import java.util.Properties;
/*     */ import java.util.StringTokenizer;
/*     */ import javax.ejb.MessageDrivenBean;
/*     */ import javax.ejb.MessageDrivenContext;
/*     */ import javax.jms.JMSException;
/*     */ import javax.jms.Message;
/*     */ import javax.jms.MessageListener;
/*     */ import javax.jms.ObjectMessage;
/*     */ import javax.jms.TextMessage;
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
/*     */ public class MessageEmailBean
/*     */   implements MessageDrivenBean, MessageListener
/*     */ {
/*     */   public static final String JNDI_FACTORY = "weblogic.jndi.WLInitialContextFactory";
/*  34 */   public static final StringBuffer buffer = new StringBuffer();
/*     */   
/*  36 */   public String configFile = "pnrbean.conf";
/*  37 */   protected String adminEmailAddr = "Milestone";
/*  38 */   protected String adminEmailName = "Milestone";
/*  39 */   protected String destEmailAddr = "";
/*  40 */   protected String ccEmailAddr = "";
/*  41 */   protected String emailSubject = "";
/*  42 */   protected String emailMessage = "";
/*  43 */   protected String emailHostServer = "";
/*  44 */   protected String emailContentType = "text/html";
/*     */   protected boolean debug = false;
/*  46 */   protected static String MilestoneServer = "";
/*  47 */   protected String emailBody = "";
/*     */   
/*     */   private static final boolean VERBOSE = false;
/*     */   
/*     */   private MessageDrivenContext m_context;
/*  52 */   protected MessageObject messageObject = null;
/*     */ 
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
/*     */ 
/*     */   
/*  66 */   public void ejbActivate() { log("ejbActivate called"); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  74 */   public void ejbRemove() { log("ejbRemove called"); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  82 */   public void ejbPassivate() { log("ejbPassivate called"); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMessageDrivenContext(MessageDrivenContext ctx) {
/*  91 */     log("setMessageDrivenContext called");
/*  92 */     this.m_context = ctx;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void ejbCreate() {
/*  99 */     log("ejbCreate called");
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 104 */       InputStream in = ClassLoader.getSystemResourceAsStream(this.configFile);
/*     */ 
/*     */       
/* 107 */       if (in == null) {
/* 108 */         in = new FileInputStream(this.configFile);
/*     */       }
/*     */       
/* 111 */       Properties defaultProps = new Properties();
/* 112 */       defaultProps.load(in);
/*     */ 
/*     */       
/* 115 */       this.adminEmailAddr = defaultProps.getProperty("AdminEmailAddr");
/* 116 */       this.adminEmailName = defaultProps.getProperty("AdminEmailName");
/* 117 */       this.emailHostServer = defaultProps.getProperty("EmailHostServer");
/* 118 */       this.emailContentType = defaultProps.getProperty("EmailContentType");
/* 119 */       MilestoneServer = defaultProps.getProperty("MilestoneServer", "");
/*     */       
/* 121 */       in.close();
/*     */     
/*     */     }
/* 124 */     catch (Exception exception) {}
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
/*     */   public void onMessage(Message msg) {
/*     */     try {
/*     */       String msgText;
/* 146 */       if (msg instanceof TextMessage) {
/* 147 */         msgText = ((TextMessage)msg).getText();
/*     */ 
/*     */ 
/*     */       
/*     */       }
/* 152 */       else if (msg instanceof ObjectMessage) {
/*     */         
/* 154 */         ObjectMessage objMsg = (ObjectMessage)msg;
/* 155 */         MessageObject msgObj = (MessageObject)objMsg.getObject();
/* 156 */         msgText = msgObj.getEmailBody();
/*     */       } else {
/*     */         
/* 159 */         msgText = msg.toString();
/*     */       } 
/*     */ 
/*     */       
/* 163 */       System.out.println("JMS Message Received: " + msgText);
/* 164 */       sendJavaMailOpt(msgText);
/*     */     }
/* 166 */     catch (JMSException jmse) {
/* 167 */       jmse.printStackTrace();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static InitialContext getInitialContext() throws NamingException {
/* 175 */     env = new Hashtable();
/* 176 */     env.put("java.naming.factory.initial", 
/* 177 */         "weblogic.jndi.WLInitialContextFactory");
/* 178 */     return new InitialContext(env);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 186 */   static void p(String s) { System.out.println("*** <MessageEmailBean> " + s); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean sendJavaMailOpt(String msg) {
/* 194 */     boolean sendStatus = false;
/*     */ 
/*     */     
/* 197 */     this.destEmailAddr = "mark.cole@umusic.com";
/*     */     
/* 199 */     this.emailSubject = "This is a test of JMS Email";
/* 200 */     this.emailBody = msg;
/*     */ 
/*     */     
/* 203 */     StringTokenizer Addrtokenizer = new StringTokenizer(this.destEmailAddr, ";");
/* 204 */     StringTokenizer ccAddrtokenizer = new StringTokenizer(this.ccEmailAddr, ";");
/*     */ 
/*     */     
/* 207 */     InternetAddress[] iAddresses = new InternetAddress[Addrtokenizer
/* 208 */         .countTokens()];
/* 209 */     InternetAddress[] ccAddresses = new InternetAddress[ccAddrtokenizer
/* 210 */         .countTokens()];
/*     */     
/* 212 */     int x = 0;
/* 213 */     while (Addrtokenizer.hasMoreTokens()) {
/*     */       try {
/* 215 */         iAddresses[x] = new InternetAddress(Addrtokenizer.nextToken());
/* 216 */         x++;
/*     */       }
/* 218 */       catch (Exception e) {
/* 219 */         System.out.println(e.toString());
/* 220 */         return sendStatus;
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 225 */     x = 0;
/* 226 */     while (ccAddrtokenizer.hasMoreTokens()) {
/*     */       try {
/* 228 */         ccAddresses[x] = new InternetAddress(ccAddrtokenizer.nextToken());
/* 229 */         x++;
/*     */       }
/* 231 */       catch (Exception e) {
/* 232 */         System.out.println(e.toString());
/* 233 */         return sendStatus;
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/*     */     try {
/* 239 */       Properties props = System.getProperties();
/*     */ 
/*     */ 
/*     */       
/* 243 */       props.put("mail.smtp.host", this.emailHostServer.trim());
/*     */ 
/*     */       
/* 246 */       Session session = Session.getDefaultInstance(props, null);
/*     */ 
/*     */       
/* 249 */       MimeMessage message = new MimeMessage(session);
/*     */ 
/*     */       
/* 252 */       message.setFrom(new InternetAddress(this.adminEmailAddr, 
/* 253 */             this.adminEmailName));
/*     */ 
/*     */ 
/*     */       
/* 257 */       String subject = this.emailSubject;
/* 258 */       if (subject != null && subject.length() > 0) {
/* 259 */         message.setSubject(subject, "utf-8");
/*     */       } else {
/*     */         
/* 262 */         message.setSubject(this.emailSubject, "utf-8");
/*     */       } 
/*     */ 
/*     */       
/* 266 */       message.setContent(this.emailBody, this.emailContentType);
/*     */ 
/*     */ 
/*     */       
/*     */       try {
/* 271 */         message.addRecipients(Message.RecipientType.CC, ccAddresses);
/*     */ 
/*     */         
/* 274 */         message.addRecipients(Message.RecipientType.TO, iAddresses);
/*     */ 
/*     */         
/* 277 */         Transport.send(message);
/* 278 */         System.out.println(
/* 279 */             "<<< Session Email Address - Java Mail - Message Sent");
/*     */         
/* 281 */         sendStatus = true;
/*     */       
/*     */       }
/* 284 */       catch (Exception e) {
/* 285 */         e.printStackTrace();
/* 286 */         System.out.println("<<< Session Email Transport exception " + 
/* 287 */             e.getMessage() + ", " + e.toString());
/* 288 */         sendStatus = false;
/*     */       }
/*     */     
/* 291 */     } catch (Exception e) {
/* 292 */       System.out.println("<<< Session Email Transport exception " + 
/* 293 */           e.getMessage());
/* 294 */       sendStatus = false;
/*     */     } 
/*     */     
/* 297 */     return sendStatus;
/*     */   }
/*     */ }


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\jms\MessageEmailBean.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */