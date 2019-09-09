/*     */ package WEB-INF.classes.com.universal.milestone;
/*     */ 
/*     */ import com.universal.milestone.JdbcConnector;
/*     */ import com.universal.milestone.PnrCommunication;
/*     */ import com.universal.milestone.pnr.PnrHome;
/*     */ import com.universal.milestone.pnr.PnrRemote;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.InputStream;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.Properties;
/*     */ import java.util.StringTokenizer;
/*     */ import javax.mail.Message;
/*     */ import javax.mail.Session;
/*     */ import javax.mail.Transport;
/*     */ import javax.mail.internet.InternetAddress;
/*     */ import javax.mail.internet.MimeMessage;
/*     */ import javax.naming.Context;
/*     */ import javax.naming.InitialContext;
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
/*     */ public class PnrCommunication
/*     */ {
/*     */   public static final String COMPONENT_CODE = "Pnr";
/*     */   protected static final String configFile = "pnrbean.conf";
/*  50 */   protected static String destEmailAddr = "LCSSupport@umusic.com";
/*  51 */   protected static String ccEmailAddr = "milestone@umusic.com";
/*  52 */   protected static String emailHostServer = "usushntws51";
/*  53 */   protected static String adminEmailAddr = "Milestone";
/*  54 */   protected static String adminEmailName = "Milestone";
/*  55 */   protected static String emailSubject = "Milestone Application Error - EJB communication error!";
/*  56 */   protected static String emailMessage = "The Milestone application is receiving an error when communicating with the Enterprise Java Bean";
/*  57 */   protected static String emailContentType = "text/html";
/*  58 */   protected static String MilestoneServer = "";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  67 */   protected static PnrCommunication pnrCommunication = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PnrCommunication() {
/*     */     try {
/*  78 */       InputStream in = ClassLoader.getSystemResourceAsStream("pnrbean.conf");
/*     */ 
/*     */       
/*  81 */       if (in == null) {
/*  82 */         in = new FileInputStream("pnrbean.conf");
/*     */       }
/*  84 */       Properties defaultProps = new Properties();
/*  85 */       defaultProps.load(in);
/*     */ 
/*     */       
/*  88 */       adminEmailAddr = defaultProps.getProperty("AdminEmailAddr", adminEmailAddr);
/*  89 */       adminEmailName = defaultProps.getProperty("AdminEmailName", adminEmailName);
/*  90 */       emailHostServer = defaultProps.getProperty("EmailHostServer", emailHostServer);
/*  91 */       emailContentType = defaultProps.getProperty("EmailContentType", emailContentType);
/*  92 */       MilestoneServer = defaultProps.getProperty("MilestoneServer", "");
/*     */       
/*  94 */       in.close();
/*  95 */     } catch (Exception exception) {}
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
/*     */   public static PnrCommunication getInstance() {
/* 107 */     if (pnrCommunication == null)
/*     */     {
/* 109 */       pnrCommunication = new PnrCommunication();
/*     */     }
/* 111 */     return pnrCommunication;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String GetPNR(String userID, String operatingCompany, String superLabel, String configCode, String titleID, String title, String artistFName, String artistLName, String releaseDate, String labelCode, String projectID, String upc, String soundScan) {
/* 121 */     String strReply = "";
/*     */     
/* 123 */     PnrHome home = null;
/* 124 */     PnrRemote pnr = null;
/*     */     
/*     */     try {
/* 127 */       Properties h = new Properties();
/* 128 */       h.put("java.naming.factory.initial", JdbcConnector.INITIAL_CONTEXT_FACTORY);
/* 129 */       h.put("java.naming.provider.url", JdbcConnector.PROVIDER_URL);
/* 130 */       Context initial = new InitialContext(h);
/*     */       
/* 132 */       home = (PnrHome)initial.lookup("PnrBean");
/*     */       
/* 134 */       if (home == null)
/*     */       {
/*     */         
/* 137 */         sendJavaMailOpt("** Unable to find EJB home object for (PnrBean) **", userID);
/*     */       
/*     */       }
/*     */       else
/*     */       {
/* 142 */         pnr = home.create();
/* 143 */         if (pnr == null) {
/* 144 */           sendJavaMailOpt("** Unable to create EJB remote object for (PnrBean) **", userID);
/*     */         }
/*     */ 
/*     */         
/* 148 */         SimpleDateFormat df = new SimpleDateFormat("m/d/yy");
/*     */         
/* 150 */         strReply = pnr.getPnr(userID, 
/* 151 */             operatingCompany, 
/* 152 */             superLabel, 
/* 153 */             configCode, 
/* 154 */             titleID, 
/* 155 */             title, 
/* 156 */             artistFName, 
/* 157 */             artistLName, 
/* 158 */             df.parse(releaseDate), 
/* 159 */             labelCode, 
/* 160 */             projectID, 
/* 161 */             upc, 
/* 162 */             soundScan);
/*     */       
/*     */       }
/*     */     
/*     */     }
/* 167 */     catch (Exception e) {
/* 168 */       System.out.println(e.getMessage());
/* 169 */       strReply = "BROKER ERROR: " + e.getMessage();
/* 170 */       sendJavaMailOpt(e.getMessage(), userID);
/*     */     } 
/* 172 */     return strReply;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean sendJavaMailOpt(String messageMsg, String userID) {
/* 181 */     boolean sendStatus = false;
/*     */ 
/*     */     
/* 184 */     StringTokenizer Addrtokenizer = new StringTokenizer(destEmailAddr, ";");
/* 185 */     StringTokenizer ccAddrtokenizer = new StringTokenizer(ccEmailAddr, ";");
/*     */     
/* 187 */     InternetAddress[] iAddresses = new InternetAddress[Addrtokenizer.countTokens()];
/* 188 */     InternetAddress[] ccAddresses = new InternetAddress[ccAddrtokenizer.countTokens()];
/*     */     
/* 190 */     int x = 0;
/* 191 */     while (Addrtokenizer.hasMoreTokens()) {
/*     */       try {
/* 193 */         iAddresses[x] = new InternetAddress(Addrtokenizer.nextToken());
/* 194 */         x++;
/* 195 */       } catch (Exception e) {
/* 196 */         System.out.println(e.toString());
/* 197 */         return sendStatus;
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 202 */     x = 0;
/* 203 */     while (ccAddrtokenizer.hasMoreTokens()) {
/*     */       try {
/* 205 */         ccAddresses[x] = new InternetAddress(ccAddrtokenizer.nextToken());
/* 206 */         x++;
/* 207 */       } catch (Exception e) {
/* 208 */         System.out.println(e.toString());
/* 209 */         return sendStatus;
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/*     */     try {
/* 215 */       Properties props = System.getProperties();
/*     */ 
/*     */ 
/*     */       
/* 219 */       props.put("mail.smtp.host", emailHostServer.trim());
/*     */ 
/*     */       
/* 222 */       Session session = Session.getDefaultInstance(props, null);
/*     */ 
/*     */       
/* 225 */       MimeMessage message = new MimeMessage(session);
/*     */ 
/*     */       
/* 228 */       message.setFrom(new InternetAddress(adminEmailAddr, adminEmailName));
/*     */ 
/*     */ 
/*     */       
/* 232 */       String subject = emailSubject;
/* 233 */       if (subject != null && subject.length() > 0) {
/* 234 */         message.setSubject(subject, "utf-8");
/*     */       } else {
/* 236 */         message.setSubject(emailSubject, "utf-8");
/*     */       } 
/*     */       
/* 239 */       String emailBody = "<b>" + emailMessage + "</b><BR><BR>" + "<b>" + messageMsg + "</b>" + 
/* 240 */         "<BR><BR><font color='#0000FF'><B> Server: " + MilestoneServer + "</B>:</font>" + 
/* 241 */         "<BR><BR><font color='#0000FF'><B> User:   " + userID + "</B>:</font>";
/*     */ 
/*     */       
/* 244 */       message.setContent(emailBody, emailContentType);
/*     */ 
/*     */ 
/*     */       
/*     */       try {
/* 249 */         message.addRecipients(Message.RecipientType.CC, ccAddresses);
/*     */ 
/*     */         
/* 252 */         message.addRecipients(Message.RecipientType.TO, iAddresses);
/*     */ 
/*     */         
/* 255 */         Transport.send(message);
/*     */ 
/*     */         
/* 258 */         sendStatus = true;
/*     */       }
/* 260 */       catch (Exception e) {
/* 261 */         e.printStackTrace();
/* 262 */         System.out.println("<<< Session Email Transport exception " + e.getMessage() + ", " + e.toString());
/* 263 */         sendStatus = false;
/*     */       } 
/* 265 */     } catch (Exception e) {
/* 266 */       System.out.println("<<< Session Email Transport exception " + e.getMessage());
/* 267 */       sendStatus = false;
/*     */     } 
/*     */     
/* 270 */     return sendStatus;
/*     */   }
/*     */ }


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\PnrCommunication.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */