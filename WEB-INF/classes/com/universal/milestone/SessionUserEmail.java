/*     */ package WEB-INF.classes.com.universal.milestone;
/*     */ 
/*     */ import com.universal.milestone.JdbcConnector;
/*     */ import com.universal.milestone.MilestoneHelper;
/*     */ import com.universal.milestone.SessionUserEmail;
/*     */ import com.universal.milestone.SessionUserEmailObj;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileWriter;
/*     */ import java.io.InputStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Properties;
/*     */ import java.util.StringTokenizer;
/*     */ import javax.activation.DataHandler;
/*     */ import javax.activation.FileDataSource;
/*     */ import javax.mail.Message;
/*     */ import javax.mail.Session;
/*     */ import javax.mail.Transport;
/*     */ import javax.mail.internet.InternetAddress;
/*     */ import javax.mail.internet.MimeBodyPart;
/*     */ import javax.mail.internet.MimeMessage;
/*     */ import javax.mail.internet.MimeMultipart;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SessionUserEmail
/*     */ {
/*     */   static final int smtpFolderOpt = 1;
/*     */   static final int smtpJavaMailOpt = 2;
/*  36 */   public String configFile = "milestone.conf";
/*     */   
/*     */   static final String CRLF = "\r\n";
/*  39 */   int emailSendOption = 1;
/*  40 */   String adminEmailAddr = null;
/*  41 */   String adminEmailName = null;
/*  42 */   String emailSubject = null;
/*  43 */   String emailMessage = null;
/*  44 */   String emailPickupFolder = null;
/*  45 */   String emailHostServer = null;
/*  46 */   String emailContentType = null;
/*  47 */   String emailBCCAddr = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SessionUserEmail() {
/*     */     try {
/*  56 */       InputStream in = ClassLoader.getSystemResourceAsStream(this.configFile);
/*     */ 
/*     */ 
/*     */       
/*  60 */       if (in == null) {
/*  61 */         in = new FileInputStream(this.configFile);
/*     */       }
/*  63 */       Properties defaultProps = new Properties();
/*  64 */       defaultProps.load(in);
/*     */ 
/*     */       
/*  67 */       this.adminEmailAddr = defaultProps.getProperty("AdminEmailAddr");
/*     */       
/*  69 */       this.adminEmailName = defaultProps.getProperty("AdminEmailName");
/*     */       
/*  71 */       this.emailSubject = defaultProps.getProperty("EmailSubject");
/*  72 */       this.emailMessage = defaultProps.getProperty("EmailMessage");
/*  73 */       this.emailPickupFolder = defaultProps.getProperty("EmailPickupFolder");
/*  74 */       this.emailHostServer = defaultProps.getProperty("EmailHostServer");
/*  75 */       this.emailSendOption = Integer.parseInt(defaultProps.getProperty("EmailSendOption"));
/*     */       
/*  77 */       this.emailContentType = defaultProps.getProperty("EmailContentType");
/*     */       
/*  79 */       this.emailBCCAddr = defaultProps.getProperty("EmailBCCAddr");
/*     */       
/*  81 */       in.close();
/*     */     }
/*  83 */     catch (Exception ex) {
/*  84 */       System.out.println("<<< Session Email Exception " + ex.getMessage());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  92 */   public void setEmailSendOpt(int emailSendOption) { this.emailSendOption = emailSendOption; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  97 */   public int getEmailSendOpt() { return this.emailSendOption; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 102 */   public void setConfigFile(String configFile) { this.configFile = configFile; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 107 */   public String getConfigFile() { return this.configFile; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean sendEmail(ArrayList emailList, String emailMsg, String subject, String attachment, ArrayList ccList) {
/* 117 */     if (this.emailSendOption == 1) {
/* 118 */       return sendFolderOpt(emailList, emailMsg);
/*     */     }
/* 120 */     if (this.emailSendOption == 2) {
/* 121 */       return sendSQLemailOpt(emailList, emailMsg);
/*     */     }
/* 123 */     return sendJavaMailOpt(emailList, emailMsg, subject, attachment, ccList);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean sendFolderOpt(ArrayList eList, String eMsg) {
/* 133 */     boolean sendStatus = false;
/*     */     
/* 135 */     FileWriter outStream = null;
/*     */     
/* 137 */     for (int x = 0; x < eList.size(); x++) {
/*     */ 
/*     */       
/* 140 */       SessionUserEmailObj emailObj = (SessionUserEmailObj)eList.get(x);
/*     */       
/* 142 */       if (emailObj.emailAddr != null && !emailObj.emailAddr.equals("") && emailObj.getSendEmail()) {
/*     */         
/* 144 */         StringBuffer fileStr = new StringBuffer();
/* 145 */         fileStr.append("From: " + this.adminEmailAddr + "\r\n");
/* 146 */         fileStr.append("To: " + emailObj.emailAddr + "\r\n");
/* 147 */         fileStr.append("Subject: " + this.emailSubject + "\r\n" + "\r\n");
/* 148 */         fileStr.append(eMsg);
/* 149 */         File outFile = new File(this.emailPickupFolder, "MilestoneMsg" + x + ".txt");
/*     */         try {
/* 151 */           outStream = new FileWriter(outFile);
/* 152 */           outStream.write(fileStr.toString());
/* 153 */           outStream.close();
/*     */           
/* 155 */           sendStatus = true;
/* 156 */         } catch (Exception e) {
/* 157 */           System.out.println("<<< Session Email FileIO Exception " + e.getMessage());
/* 158 */           sendStatus = false;
/*     */         } 
/*     */         
/* 161 */         fileStr = null;
/* 162 */         outFile = null;
/* 163 */         outStream = null;
/*     */       } 
/*     */     } 
/*     */     
/* 167 */     return sendStatus;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean sendSQLemailOpt(ArrayList eList, String eMsg) {
/* 177 */     boolean sendStatus = false;
/*     */     
/* 179 */     for (int x = 0; x < eList.size(); x++) {
/*     */ 
/*     */       
/* 182 */       SessionUserEmailObj emailObj = (SessionUserEmailObj)eList.get(x);
/*     */       
/* 184 */       String fileName = "";
/* 185 */       String eCC = "";
/* 186 */       if (emailObj.emailAddr != null && !emailObj.emailAddr.equals("") && emailObj.getSendEmail()) {
/*     */ 
/*     */         
/* 189 */         String query = "sp_SendEmailNotification '" + 
/* 190 */           this.emailSubject + "'," + 
/* 191 */           "'" + eMsg + "'," + 
/* 192 */           "'" + emailObj.getEmailAddr() + "'," + 
/* 193 */           "'" + eCC + "'," + 
/* 194 */           "'" + fileName + "'";
/*     */         
/* 196 */         JdbcConnector connector = MilestoneHelper.getConnector(query);
/* 197 */         connector.runQuery();
/*     */         
/* 199 */         int result = connector.getIntegerField("result");
/* 200 */         if (result == 0) {
/* 201 */           sendStatus = true;
/*     */         }
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 207 */     return sendStatus;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean sendJavaMailOpt(ArrayList eList, String eMsg, String subject, String attachment, ArrayList ccList) {
/* 216 */     boolean sendStatus = false;
/*     */     
/* 218 */     InternetAddress[] iAddresses = new InternetAddress[eList.size()];
/* 219 */     InternetAddress[] ccAddresses = new InternetAddress[ccList.size()];
/*     */ 
/*     */     
/* 222 */     for (int x = 0; x < eList.size(); x++) {
/*     */       
/* 224 */       SessionUserEmailObj emailObj = (SessionUserEmailObj)eList.get(x);
/*     */       
/*     */       try {
/* 227 */         iAddresses[x] = new InternetAddress(emailObj.emailAddr);
/*     */       }
/* 229 */       catch (Exception e) {
/* 230 */         System.out.println("<<< Session Email Internet Address Exception " + e.getMessage());
/* 231 */         return sendStatus;
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 236 */     for (y = 0; y < ccList.size(); y++) {
/*     */       
/* 238 */       SessionUserEmailObj ccEmailObj = (SessionUserEmailObj)ccList.get(y);
/*     */       
/*     */       try {
/* 241 */         ccAddresses[y] = new InternetAddress(ccEmailObj.emailAddr);
/*     */       }
/* 243 */       catch (Exception e) {
/* 244 */         System.out.println("<<< Session Email Internet Address Exception " + e.getMessage());
/* 245 */         return sendStatus;
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 253 */       Properties props = System.getProperties();
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 258 */       props.put("mail.smtp.host", this.emailHostServer.trim());
/*     */ 
/*     */       
/* 261 */       Session session = Session.getDefaultInstance(props, null);
/*     */ 
/*     */ 
/*     */       
/* 265 */       MimeMessage message = new MimeMessage(session);
/*     */ 
/*     */ 
/*     */       
/* 269 */       message.setFrom(new InternetAddress(this.adminEmailAddr, this.adminEmailName));
/*     */ 
/*     */ 
/*     */       
/* 273 */       if (subject != null && subject.length() > 0) {
/* 274 */         message.setSubject(subject, "utf-8");
/*     */       } else {
/* 276 */         message.setSubject(this.emailSubject, "utf-8");
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 281 */       if (attachment == null) {
/*     */         
/* 283 */         message.setContent(eMsg, this.emailContentType);
/*     */       }
/*     */       else {
/*     */         
/* 287 */         File file = new File(attachment);
/* 288 */         if (file.exists()) {
/*     */           
/* 290 */           MimeMultipart mp = new MimeMultipart();
/*     */           
/* 292 */           MimeBodyPart text = new MimeBodyPart();
/* 293 */           text.setDisposition("inline");
/* 294 */           text.setContent(eMsg, this.emailContentType);
/* 295 */           mp.addBodyPart(text);
/*     */           
/* 297 */           MimeBodyPart file_part = new MimeBodyPart();
/* 298 */           FileDataSource fds = new FileDataSource(file);
/* 299 */           DataHandler dh = new DataHandler(fds);
/* 300 */           file_part.setFileName(file.getName());
/* 301 */           file_part.setDisposition("attachment");
/* 302 */           file_part.setDescription("Attached file: " + file.getName());
/* 303 */           file_part.setDataHandler(dh);
/* 304 */           mp.addBodyPart(file_part);
/*     */           
/* 306 */           message.setContent(mp);
/*     */         }
/*     */         else {
/*     */           
/* 310 */           message.setContent(eMsg, this.emailContentType);
/*     */         } 
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       try {
/* 322 */         StringTokenizer bccTokenizer = new StringTokenizer(this.emailBCCAddr, ";");
/* 323 */         ArrayList bccArrayList = new ArrayList();
/* 324 */         while (bccTokenizer.hasMoreTokens())
/* 325 */           bccArrayList.add(bccTokenizer.nextToken().trim()); 
/* 326 */         InternetAddress[] bccAddresses = new InternetAddress[bccArrayList.size()];
/*     */         
/* 328 */         for (int bccCounter = 0; bccCounter < bccArrayList.size(); bccCounter++) {
/* 329 */           bccAddresses[bccCounter] = new InternetAddress((String)bccArrayList.get(bccCounter));
/*     */         }
/* 331 */         message.addRecipients(Message.RecipientType.BCC, bccAddresses);
/*     */ 
/*     */         
/* 334 */         message.addRecipients(Message.RecipientType.CC, ccAddresses);
/*     */ 
/*     */         
/* 337 */         message.addRecipients(Message.RecipientType.TO, iAddresses);
/*     */ 
/*     */         
/* 340 */         Transport.send(message);
/* 341 */         sendStatus = true;
/* 342 */       } catch (Exception e) {
/* 343 */         System.out.println("<<< Session Email Transport exception " + e.getMessage());
/* 344 */         sendStatus = false;
/*     */       } 
/* 346 */     } catch (Exception y) {
/* 347 */       Exception e; System.out.println("<<< Session Email Transport exception " + e.getMessage());
/* 348 */       sendStatus = false;
/*     */     } 
/*     */     
/* 351 */     return sendStatus;
/*     */   }
/*     */ }


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\SessionUserEmail.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */