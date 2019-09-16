package WEB-INF.classes.com.universal.milestone;

import com.universal.milestone.JdbcConnector;
import com.universal.milestone.MilestoneHelper;
import com.universal.milestone.SessionUserEmail;
import com.universal.milestone.SessionUserEmailObj;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Properties;
import java.util.StringTokenizer;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class SessionUserEmail {
  static final int smtpFolderOpt = 1;
  
  static final int smtpJavaMailOpt = 2;
  
  public String configFile = "milestone.conf";
  
  static final String CRLF = "\r\n";
  
  int emailSendOption = 1;
  
  String adminEmailAddr = null;
  
  String adminEmailName = null;
  
  String emailSubject = null;
  
  String emailMessage = null;
  
  String emailPickupFolder = null;
  
  String emailHostServer = null;
  
  String emailContentType = null;
  
  String emailBCCAddr = null;
  
  public SessionUserEmail() {
    try {
      InputStream in = ClassLoader.getSystemResourceAsStream(this.configFile);
      if (in == null)
        in = new FileInputStream(this.configFile); 
      Properties defaultProps = new Properties();
      defaultProps.load(in);
      this.adminEmailAddr = defaultProps.getProperty("AdminEmailAddr");
      this.adminEmailName = defaultProps.getProperty("AdminEmailName");
      this.emailSubject = defaultProps.getProperty("EmailSubject");
      this.emailMessage = defaultProps.getProperty("EmailMessage");
      this.emailPickupFolder = defaultProps.getProperty("EmailPickupFolder");
      this.emailHostServer = defaultProps.getProperty("EmailHostServer");
      this.emailSendOption = Integer.parseInt(defaultProps.getProperty("EmailSendOption"));
      this.emailContentType = defaultProps.getProperty("EmailContentType");
      this.emailBCCAddr = defaultProps.getProperty("EmailBCCAddr");
      in.close();
    } catch (Exception ex) {
      System.out.println("<<< Session Email Exception " + ex.getMessage());
    } 
  }
  
  public void setEmailSendOpt(int emailSendOption) { this.emailSendOption = emailSendOption; }
  
  public int getEmailSendOpt() { return this.emailSendOption; }
  
  public void setConfigFile(String configFile) { this.configFile = configFile; }
  
  public String getConfigFile() { return this.configFile; }
  
  public boolean sendEmail(ArrayList emailList, String emailMsg, String subject, String attachment, ArrayList ccList) {
    if (this.emailSendOption == 1)
      return sendFolderOpt(emailList, emailMsg); 
    if (this.emailSendOption == 2)
      return sendSQLemailOpt(emailList, emailMsg); 
    return sendJavaMailOpt(emailList, emailMsg, subject, attachment, ccList);
  }
  
  public boolean sendFolderOpt(ArrayList eList, String eMsg) {
    boolean sendStatus = false;
    FileWriter outStream = null;
    for (int x = 0; x < eList.size(); x++) {
      SessionUserEmailObj emailObj = (SessionUserEmailObj)eList.get(x);
      if (emailObj.emailAddr != null && !emailObj.emailAddr.equals("") && emailObj.getSendEmail()) {
        StringBuffer fileStr = new StringBuffer();
        fileStr.append("From: " + this.adminEmailAddr + "\r\n");
        fileStr.append("To: " + emailObj.emailAddr + "\r\n");
        fileStr.append("Subject: " + this.emailSubject + "\r\n" + "\r\n");
        fileStr.append(eMsg);
        File outFile = new File(this.emailPickupFolder, "MilestoneMsg" + x + ".txt");
        try {
          outStream = new FileWriter(outFile);
          outStream.write(fileStr.toString());
          outStream.close();
          sendStatus = true;
        } catch (Exception e) {
          System.out.println("<<< Session Email FileIO Exception " + e.getMessage());
          sendStatus = false;
        } 
        fileStr = null;
        outFile = null;
        outStream = null;
      } 
    } 
    return sendStatus;
  }
  
  public boolean sendSQLemailOpt(ArrayList eList, String eMsg) {
    boolean sendStatus = false;
    for (int x = 0; x < eList.size(); x++) {
      SessionUserEmailObj emailObj = (SessionUserEmailObj)eList.get(x);
      String fileName = "";
      String eCC = "";
      if (emailObj.emailAddr != null && !emailObj.emailAddr.equals("") && emailObj.getSendEmail()) {
        String query = "sp_SendEmailNotification '" + 
          this.emailSubject + "'," + 
          "'" + eMsg + "'," + 
          "'" + emailObj.getEmailAddr() + "'," + 
          "'" + eCC + "'," + 
          "'" + fileName + "'";
        JdbcConnector connector = MilestoneHelper.getConnector(query);
        connector.runQuery();
        int result = connector.getIntegerField("result");
        if (result == 0)
          sendStatus = true; 
      } 
    } 
    return sendStatus;
  }
  
  public boolean sendJavaMailOpt(ArrayList eList, String eMsg, String subject, String attachment, ArrayList ccList) {
    boolean sendStatus = false;
    InternetAddress[] iAddresses = new InternetAddress[eList.size()];
    InternetAddress[] ccAddresses = new InternetAddress[ccList.size()];
    for (int x = 0; x < eList.size(); x++) {
      SessionUserEmailObj emailObj = (SessionUserEmailObj)eList.get(x);
      try {
        iAddresses[x] = new InternetAddress(emailObj.emailAddr);
      } catch (Exception e) {
        System.out.println("<<< Session Email Internet Address Exception " + e.getMessage());
        return sendStatus;
      } 
    } 
    for (y = 0; y < ccList.size(); y++) {
      SessionUserEmailObj ccEmailObj = (SessionUserEmailObj)ccList.get(y);
      try {
        ccAddresses[y] = new InternetAddress(ccEmailObj.emailAddr);
      } catch (Exception e) {
        System.out.println("<<< Session Email Internet Address Exception " + e.getMessage());
        return sendStatus;
      } 
    } 
    try {
      Properties props = System.getProperties();
      props.put("mail.smtp.host", this.emailHostServer.trim());
      Session session = Session.getDefaultInstance(props, null);
      MimeMessage message = new MimeMessage(session);
      message.setFrom(new InternetAddress(this.adminEmailAddr, this.adminEmailName));
      if (subject != null && subject.length() > 0) {
        message.setSubject(subject, "utf-8");
      } else {
        message.setSubject(this.emailSubject, "utf-8");
      } 
      if (attachment == null) {
        message.setContent(eMsg, this.emailContentType);
      } else {
        File file = new File(attachment);
        if (file.exists()) {
          MimeMultipart mp = new MimeMultipart();
          MimeBodyPart text = new MimeBodyPart();
          text.setDisposition("inline");
          text.setContent(eMsg, this.emailContentType);
          mp.addBodyPart(text);
          MimeBodyPart file_part = new MimeBodyPart();
          FileDataSource fds = new FileDataSource(file);
          DataHandler dh = new DataHandler(fds);
          file_part.setFileName(file.getName());
          file_part.setDisposition("attachment");
          file_part.setDescription("Attached file: " + file.getName());
          file_part.setDataHandler(dh);
          mp.addBodyPart(file_part);
          message.setContent(mp);
        } else {
          message.setContent(eMsg, this.emailContentType);
        } 
      } 
      try {
        StringTokenizer bccTokenizer = new StringTokenizer(this.emailBCCAddr, ";");
        ArrayList bccArrayList = new ArrayList();
        while (bccTokenizer.hasMoreTokens())
          bccArrayList.add(bccTokenizer.nextToken().trim()); 
        InternetAddress[] bccAddresses = new InternetAddress[bccArrayList.size()];
        for (int bccCounter = 0; bccCounter < bccArrayList.size(); bccCounter++)
          bccAddresses[bccCounter] = new InternetAddress((String)bccArrayList.get(bccCounter)); 
        message.addRecipients(Message.RecipientType.BCC, bccAddresses);
        message.addRecipients(Message.RecipientType.CC, ccAddresses);
        message.addRecipients(Message.RecipientType.TO, iAddresses);
        Transport.send(message);
        sendStatus = true;
      } catch (Exception e) {
        System.out.println("<<< Session Email Transport exception " + e.getMessage());
        sendStatus = false;
      } 
    } catch (Exception y) {
      Exception e;
      System.out.println("<<< Session Email Transport exception " + e.getMessage());
      sendStatus = false;
    } 
    return sendStatus;
  }
}


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\SessionUserEmail.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */