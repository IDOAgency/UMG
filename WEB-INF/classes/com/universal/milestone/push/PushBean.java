package WEB-INF.classes.com.universal.milestone.push;

import com.softwareag.entirex.aci.Broker;
import com.softwareag.entirex.aci.BrokerException;
import com.softwareag.entirex.aci.BrokerMessage;
import com.softwareag.entirex.aci.BrokerService;
import com.softwareag.entirex.aci.Conversation;
import com.universal.milestone.push.PushBean;
import com.universal.milestone.push.PushCommunication;
import com.universal.milestone.xml.XMLUtil;
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

public class PushBean implements SessionBean {
  private SessionContext ctx;
  
  protected String sServer = "";
  
  protected String brokerID = "";
  
  protected String adminEmailAddr = "Milestone";
  
  protected String adminEmailName = "Milestone";
  
  protected String destEmailAddr = "LCSSupport@umusic.com";
  
  protected String ccEmailAddr = "milestone@umusic.com";
  
  protected String emailSubject = "";
  
  protected String emailMessage = "";
  
  protected String emailHostServer = "";
  
  protected String emailContentType = "text/html";
  
  protected String defaultWaitTime = "60s";
  
  protected String MilestoneServer = "";
  
  public void ejbActivate() { PushCommunication.log("ejbActivate called"); }
  
  public void ejbRemove() { PushCommunication.log("ejbRemove called"); }
  
  public void ejbPassivate() { PushCommunication.log("ejbPassivate called"); }
  
  public void setSessionContext(SessionContext ctx) {
    PushCommunication.log("setSessionContext called");
    this.ctx = ctx;
  }
  
  public void ejbCreate() {
    PushCommunication.log("ejbCreate called");
    XMLUtil xmlUtil = new XMLUtil("pushbean.config");
    if (xmlUtil.getDocument() != null) {
      this.sServer = xmlUtil.getElementValueByParentList(PushCommunication.legacyNodeList, "server");
      this.brokerID = xmlUtil.getElementValueByParentList(PushCommunication.legacyNodeList, "broker-id");
      this.defaultWaitTime = xmlUtil.getElementValueByParentList(PushCommunication.legacyNodeList, "default-wait-time", this.defaultWaitTime);
      this.adminEmailAddr = xmlUtil.getElementValueByParentList(PushCommunication.emailNodeList, "admin-addr", this.adminEmailAddr);
      this.adminEmailName = xmlUtil.getElementValueByParentList(PushCommunication.emailNodeList, "admin-name", this.adminEmailName);
      this.emailSubject = xmlUtil.getElementValueByParentList(PushCommunication.emailNodeList, "subject");
      this.emailMessage = xmlUtil.getElementValueByParentList(PushCommunication.emailNodeList, "message");
      this.emailHostServer = xmlUtil.getElementValueByParentList(PushCommunication.emailNodeList, "host-server");
      this.emailContentType = xmlUtil.getElementValueByParentList(PushCommunication.emailNodeList, "content-type", this.emailContentType);
      this.destEmailAddr = xmlUtil.getElementValueByParentList(PushCommunication.emailNodeList, "to-list");
      this.ccEmailAddr = xmlUtil.getElementValueByParentList(PushCommunication.emailNodeList, "cc-list");
      this.MilestoneServer = xmlUtil.getElementValueByParentList(PushCommunication.milestoneNodeList, "server", "");
    } else {
      sendJavaMailOpt("** PushBean unable to initialize **\n\nUnable to find document", "Initialize");
    } 
    try {
      InitialContext initialContext = new InitialContext();
    } catch (NamingException ne) {
      throw new CreateException("PushBean Failed to Initialize Context " + ne);
    } 
  }
  
  public String TestEJB() { return "This is a test of PushBean's method TestEJB()"; }
  
  public String SendPFM(String pMessage, String userId) {
    PushCommunication.log("SendPFM called");
    PushCommunication.log("server: " + this.sServer);
    PushCommunication.log("broker Id: " + this.brokerID);
    PushCommunication.log("pMessage: " + pMessage);
    try {
      sendJavaMailOpt("test erro push to legacy", userId);
      return sendRequest(pMessage, userId);
    } catch (BrokerException bE) {
      PushCommunication.log(bE.toString());
      sendJavaMailOpt(bE.toString(), userId);
      return "BROKER ERROR: ** PFM PUSH ** " + bE.toString();
    } catch (Exception ae) {
      PushCommunication.log(ae.getMessage());
      sendJavaMailOpt(ae.getMessage(), userId);
      return "BROKER ERROR: ** PFM PUSH ** " + ae.getMessage();
    } 
  }
  
  private String sendRequest(String pMessage, String userID) {
    Broker broker = new Broker(this.brokerID, "JavaUser");
    BrokerService bService = new BrokerService(broker, this.sServer);
    bService.setDefaultWaittime(this.defaultWaitTime);
    BrokerMessage bRequest = new BrokerMessage();
    bService.setMaxReceiveLen(550);
    broker.logon();
    PushCommunication.log("Communication started: " + broker.getConnInfo());
    Conversation conv = new Conversation(bService);
    bRequest.setMessage(pMessage);
    PushCommunication.log("Sent: " + bRequest);
    BrokerMessage bReply = conv.sendReceive(bRequest);
    PushCommunication.log("Returned: " + bReply);
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
        PushCommunication.log(e.toString());
        return sendStatus;
      } 
    } 
    x = 0;
    while (ccAddrtokenizer.hasMoreTokens()) {
      try {
        ccAddresses[x] = new InternetAddress(ccAddrtokenizer.nextToken());
        x++;
      } catch (Exception e) {
        PushCommunication.log(e.toString());
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
        "<BR><BR><font color='#0000FF'><B> Milestone Server: " + this.MilestoneServer + "</B>:</font>" + 
        "<BR><BR><font color='#0000FF'><B> User:   " + userID + "</B>:</font>" + 
        "<BR><BR><b>" + brokerExcMsg + "</b>";
      message.setContent(emailBody, this.emailContentType);
      try {
        message.addRecipients(Message.RecipientType.CC, ccAddresses);
        message.addRecipients(Message.RecipientType.TO, iAddresses);
        Transport.send(message);
        PushCommunication.log("<<< Session Email Address - Java Mail - Message Sent");
        sendStatus = true;
      } catch (Exception e) {
        e.printStackTrace();
        PushCommunication.log("<<< Session Email Transport exception " + e.getMessage() + ", " + e.toString());
        sendStatus = false;
      } 
    } catch (Exception e) {
      PushCommunication.log("<<< Session Email Transport exception " + e.getMessage());
      sendStatus = false;
    } 
    return sendStatus;
  }
}


/* Location:              D:\Documents\NetBeansProjects\milestone2 Local.war!\WEB-INF\classes\co\\universal\milestone\push\PushBean.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */