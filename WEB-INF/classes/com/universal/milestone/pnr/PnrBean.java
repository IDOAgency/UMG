package WEB-INF.classes.com.universal.milestone.pnr;

import com.softwareag.entirex.aci.Broker;
import com.softwareag.entirex.aci.BrokerException;
import com.softwareag.entirex.aci.BrokerMessage;
import com.softwareag.entirex.aci.BrokerService;
import com.softwareag.entirex.aci.Conversation;
import com.universal.milestone.pnr.PnrBean;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.StringTokenizer;
import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class PnrBean implements SessionBean {
  private static final boolean VERBOSE = false;
  
  private SessionContext ctx;
  
  protected String sServer = "";
  
  protected String brokerID = "";
  
  protected int maxReceiveLen = 550;
  
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
  
  protected String defaultWaitTime = "60s";
  
  protected static String MilestoneServer = "";
  
  private void log(String s) {}
  
  public void ejbActivate() { log("ejbActivate called"); }
  
  public void ejbRemove() { log("ejbRemove called"); }
  
  public void ejbPassivate() { log("ejbPassivate called"); }
  
  public void setSessionContext(SessionContext ctx) {
    log("setSessionContext called");
    this.ctx = ctx;
  }
  
  public void ejbCreate() {
    log("ejbCreate called");
    try {
      InputStream in = ClassLoader.getSystemResourceAsStream(this.configFile);
      if (in == null)
        in = new FileInputStream(this.configFile); 
      Properties defaultProps = new Properties();
      defaultProps.load(in);
      this.sServer = defaultProps.getProperty("LegacyServer");
      this.brokerID = defaultProps.getProperty("LegacyBrokerID");
      this.adminEmailAddr = defaultProps.getProperty("AdminEmailAddr");
      this.adminEmailName = defaultProps.getProperty("AdminEmailName");
      this.emailSubject = defaultProps.getProperty("EmailSubject");
      this.emailMessage = defaultProps.getProperty("EmailMessage");
      this.emailHostServer = defaultProps.getProperty("EmailHostServer");
      this.emailContentType = defaultProps.getProperty("EmailContentType");
      this.destEmailAddr = defaultProps.getProperty("EmailDestList");
      this.ccEmailAddr = defaultProps.getProperty("EmailCCList");
      this.defaultWaitTime = defaultProps.getProperty("LegacyDefaultWaitTime", "25s");
      MilestoneServer = defaultProps.getProperty("MilestoneServer", "");
      in.close();
    } catch (Exception exception) {}
    try {
      InitialContext ic = new InitialContext();
    } catch (NamingException ne) {
      throw new CreateException("Failed to find environment value " + ne);
    } 
  }
  
  public String getPnr(String userID, String operatingCompany, String superLabel, String configCode, String titleID, String title, String artistFName, String artistLName, Date releaseDate, String labelCode, String projectID, String upc, String soundScan) {
    try {
      log("getPnr called");
      log("server: " + this.sServer);
      String pMessage = "";
      pMessage = String.valueOf(pMessage) + formatData("FM510N", 8, " ");
      pMessage = String.valueOf(pMessage) + formatData(userID, 8, " ");
      pMessage = String.valueOf(pMessage) + formatData(operatingCompany, 2, " ");
      pMessage = String.valueOf(pMessage) + formatData(superLabel, 3, " ");
      pMessage = String.valueOf(pMessage) + formatData(configCode, 2, " ");
      pMessage = String.valueOf(pMessage) + formatData(titleID, 10, " ");
      pMessage = String.valueOf(pMessage) + formatData("", 10, " ");
      pMessage = String.valueOf(pMessage) + formatData(title, 70, " ");
      pMessage = String.valueOf(pMessage) + formatData(artistFName, 20, " ");
      pMessage = String.valueOf(pMessage) + formatData(artistLName, 50, " ");
      SimpleDateFormat adf = new SimpleDateFormat("mmddyy");
      pMessage = String.valueOf(pMessage) + formatData(adf.format(releaseDate), 6, " ");
      pMessage = String.valueOf(pMessage) + formatData(labelCode, 3, " ");
      pMessage = String.valueOf(pMessage) + formatData(formatData(projectID, 9, "0"), 12, " ");
      pMessage = String.valueOf(pMessage) + formatData(upc, 14, "0");
      pMessage = String.valueOf(pMessage) + formatData(soundScan, 14, "0");
      pMessage = String.valueOf(pMessage) + formatData("", 20, " ");
      pMessage = String.valueOf(pMessage) + formatData("", 20, " ");
      pMessage = String.valueOf(pMessage) + formatData("", 20, " ");
      pMessage = String.valueOf(pMessage) + formatData("", 20, " ");
      pMessage = String.valueOf(pMessage) + formatData("", 200, " ");
      log("pMessage: " + pMessage);
      return sendRequest(pMessage, userID);
    } catch (BrokerException bE) {
      System.out.println(bE.toString());
      sendJavaMailOpt(bE.toString(), userID);
      return "BROKER ERROR: " + bE.toString();
    } catch (Exception ae) {
      System.out.println(ae.getMessage());
      sendJavaMailOpt(ae.getMessage(), userID);
      return "BROKER ERROR: " + ae.getMessage();
    } 
  }
  
  private String formatData(String p1, int length, String filler) {
    String r1 = "";
    if (p1 == null || p1.equalsIgnoreCase("")) {
      for (int i = 0; i < length; i++)
        r1 = String.valueOf(r1) + " "; 
      return r1;
    } 
    if (p1.length() > length)
      return p1.substring(0, length); 
    for (int i = 0; i < length - p1.length(); i++)
      r1 = String.valueOf(r1) + filler; 
    if (filler.equalsIgnoreCase("0"))
      return String.valueOf(r1) + p1; 
    return String.valueOf(p1) + r1;
  }
  
  private String sendRequest(String pMessage, String userID) throws BrokerException {
    Broker broker = new Broker(this.brokerID, "JavaUser");
    BrokerService bService = new BrokerService(broker, this.sServer);
    bService.setDefaultWaittime(this.defaultWaitTime);
    BrokerMessage bRequest = new BrokerMessage();
    bService.setMaxReceiveLen(this.maxReceiveLen);
    broker.logon();
    Conversation conv = new Conversation(bService);
    bRequest.setMessage(pMessage);
    BrokerMessage bReply = conv.sendReceive(bRequest);
    conv.end();
    broker.logoff();
    broker.disconnect();
    return bReply.toString();
  }
  
  public boolean sendJavaMailOpt(String brokerExcMsg, String userID) {
    boolean sendStatus = false;
    StringTokenizer Addrtokenizer = new StringTokenizer(this.destEmailAddr, ";");
    StringTokenizer ccAddrtokenizer = new StringTokenizer(this.ccEmailAddr, ";");
    InternetAddress[] iAddresses = new InternetAddress[Addrtokenizer.countTokens()];
    InternetAddress[] ccAddresses = new InternetAddress[ccAddrtokenizer.countTokens()];
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
      message.setFrom(new InternetAddress(this.adminEmailAddr, this.adminEmailName));
      String subject = this.emailSubject;
      if (subject != null && subject.length() > 0) {
        message.setSubject(subject, "utf-8");
      } else {
        message.setSubject(this.emailSubject, "utf-8");
      } 
      String emailBody = "<b>" + this.emailMessage + "</b><BR><BR>" + 
        "<font color='#0000FF'><B> MainFrame Server: " + this.sServer + "</B>:</font><BR>" + 
        "<BR><BR><font color='#0000FF'><B> Milestone Server: " + MilestoneServer + "</B>:</font>" + 
        "<BR><BR><font color='#0000FF'><B> User:   " + userID + "</B>:</font>" + 
        "<BR><BR><b>" + brokerExcMsg + "</b>";
      message.setContent(emailBody, this.emailContentType);
      try {
        message.addRecipients(Message.RecipientType.CC, ccAddresses);
        message.addRecipients(Message.RecipientType.TO, iAddresses);
        Transport.send(message);
        sendStatus = true;
      } catch (Exception e) {
        e.printStackTrace();
        System.out.println("<<< Session Email Transport exception " + e.getMessage() + ", " + e.toString());
        sendStatus = false;
      } 
    } catch (Exception e) {
      System.out.println("<<< Session Email Transport exception " + e.getMessage());
      sendStatus = false;
    } 
    return sendStatus;
  }
}


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\pnr\PnrBean.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */