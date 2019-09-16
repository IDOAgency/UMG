package WEB-INF.classes.com.universal.milestone.jms;

import com.universal.milestone.jms.MessageEmailBean;
import com.universal.milestone.jms.MessageObject;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Hashtable;
import java.util.Properties;
import java.util.StringTokenizer;
import javax.ejb.MessageDrivenBean;
import javax.ejb.MessageDrivenContext;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.TextMessage;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class MessageEmailBean implements MessageDrivenBean, MessageListener {
  public static final String JNDI_FACTORY = "weblogic.jndi.WLInitialContextFactory";
  
  public static final StringBuffer buffer = new StringBuffer();
  
  public String configFile = "pnrbean.conf";
  
  protected String adminEmailAddr = "Milestone";
  
  protected String adminEmailName = "Milestone";
  
  protected String destEmailAddr = "";
  
  protected String ccEmailAddr = "";
  
  protected String emailSubject = "";
  
  protected String emailMessage = "";
  
  protected String emailHostServer = "";
  
  protected String emailContentType = "text/html";
  
  protected boolean debug = false;
  
  protected static String MilestoneServer = "";
  
  protected String emailBody = "";
  
  private static final boolean VERBOSE = false;
  
  private MessageDrivenContext m_context;
  
  protected MessageObject messageObject = null;
  
  private void log(String s) {}
  
  public void ejbActivate() { log("ejbActivate called"); }
  
  public void ejbRemove() { log("ejbRemove called"); }
  
  public void ejbPassivate() { log("ejbPassivate called"); }
  
  public void setMessageDrivenContext(MessageDrivenContext ctx) {
    log("setMessageDrivenContext called");
    this.m_context = ctx;
  }
  
  public void ejbCreate() {
    log("ejbCreate called");
    try {
      InputStream in = ClassLoader.getSystemResourceAsStream(this.configFile);
      if (in == null)
        in = new FileInputStream(this.configFile); 
      Properties defaultProps = new Properties();
      defaultProps.load(in);
      this.adminEmailAddr = defaultProps.getProperty("AdminEmailAddr");
      this.adminEmailName = defaultProps.getProperty("AdminEmailName");
      this.emailHostServer = defaultProps.getProperty("EmailHostServer");
      this.emailContentType = defaultProps.getProperty("EmailContentType");
      MilestoneServer = defaultProps.getProperty("MilestoneServer", "");
      in.close();
    } catch (Exception exception) {}
  }
  
  public void onMessage(Message msg) {
    try {
      String msgText;
      if (msg instanceof TextMessage) {
        msgText = ((TextMessage)msg).getText();
      } else if (msg instanceof ObjectMessage) {
        ObjectMessage objMsg = (ObjectMessage)msg;
        MessageObject msgObj = (MessageObject)objMsg.getObject();
        msgText = msgObj.getEmailBody();
      } else {
        msgText = msg.toString();
      } 
      System.out.println("JMS Message Received: " + msgText);
      sendJavaMailOpt(msgText);
    } catch (JMSException jmse) {
      jmse.printStackTrace();
    } 
  }
  
  private static InitialContext getInitialContext() throws NamingException {
    env = new Hashtable();
    env.put("java.naming.factory.initial", 
        "weblogic.jndi.WLInitialContextFactory");
    return new InitialContext(env);
  }
  
  static void p(String s) { System.out.println("*** <MessageEmailBean> " + s); }
  
  public boolean sendJavaMailOpt(String msg) {
    boolean sendStatus = false;
    this.destEmailAddr = "mark.cole@umusic.com";
    this.emailSubject = "This is a test of JMS Email";
    this.emailBody = msg;
    StringTokenizer Addrtokenizer = new StringTokenizer(this.destEmailAddr, ";");
    StringTokenizer ccAddrtokenizer = new StringTokenizer(this.ccEmailAddr, ";");
    InternetAddress[] iAddresses = new InternetAddress[Addrtokenizer
        .countTokens()];
    InternetAddress[] ccAddresses = new InternetAddress[ccAddrtokenizer
        .countTokens()];
    int x = 0;
    while (Addrtokenizer.hasMoreTokens()) {
      try {
        iAddresses[x] = new InternetAddress(Addrtokenizer.nextToken());
        x++;
      } catch (Exception e) {
        System.out.println(e.toString());
        return sendStatus;
      } 
    } 
    x = 0;
    while (ccAddrtokenizer.hasMoreTokens()) {
      try {
        ccAddresses[x] = new InternetAddress(ccAddrtokenizer.nextToken());
        x++;
      } catch (Exception e) {
        System.out.println(e.toString());
        return sendStatus;
      } 
    } 
    try {
      Properties props = System.getProperties();
      props.put("mail.smtp.host", this.emailHostServer.trim());
      Session session = Session.getDefaultInstance(props, null);
      MimeMessage message = new MimeMessage(session);
      message.setFrom(new InternetAddress(this.adminEmailAddr, 
            this.adminEmailName));
      String subject = this.emailSubject;
      if (subject != null && subject.length() > 0) {
        message.setSubject(subject, "utf-8");
      } else {
        message.setSubject(this.emailSubject, "utf-8");
      } 
      message.setContent(this.emailBody, this.emailContentType);
      try {
        message.addRecipients(Message.RecipientType.CC, ccAddresses);
        message.addRecipients(Message.RecipientType.TO, iAddresses);
        Transport.send(message);
        System.out.println(
            "<<< Session Email Address - Java Mail - Message Sent");
        sendStatus = true;
      } catch (Exception e) {
        e.printStackTrace();
        System.out.println("<<< Session Email Transport exception " + 
            e.getMessage() + ", " + e.toString());
        sendStatus = false;
      } 
    } catch (Exception e) {
      System.out.println("<<< Session Email Transport exception " + 
          e.getMessage());
      sendStatus = false;
    } 
    return sendStatus;
  }
}


/* Location:              D:\Documents\NetBeansProjects\milestone2 Local.war!\WEB-INF\classes\co\\universal\milestone\jms\MessageEmailBean.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */