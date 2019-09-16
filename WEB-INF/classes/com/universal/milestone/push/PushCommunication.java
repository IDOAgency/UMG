package WEB-INF.classes.com.universal.milestone.push;

import com.techempower.gemini.Context;
import com.universal.milestone.JdbcConnector;
import com.universal.milestone.MilestoneHelper;
import com.universal.milestone.Pfm;
import com.universal.milestone.ReleasingFamily;
import com.universal.milestone.Selection;
import com.universal.milestone.User;
import com.universal.milestone.jms.MessageObject;
import com.universal.milestone.push.PushCommunication;
import com.universal.milestone.push.PushHome;
import com.universal.milestone.push.PushPFM;
import com.universal.milestone.push.PushRemote;
import com.universal.milestone.xml.XMLUtil;
import java.util.Properties;
import java.util.StringTokenizer;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.naming.Context;
import javax.naming.InitialContext;

public class PushCommunication {
  public static final boolean VERBOSE = true;
  
  public static final String[] legacyNodeList = { "configuration", "legacy" };
  
  public static final String[] emailNodeList = { "configuration", "email" };
  
  public static final String[] milestoneNodeList = { "configuration", "milestone" };
  
  public static final int PFMPushReceiveLen = 542;
  
  public static final int PFMPushMaxReceiveLen = 550;
  
  public static final int PFMPushErrorStrPosition = 342;
  
  public static final String PFMPushOK = "PFM Push to Legacy Completed Successfully";
  
  public static final String PFMPushProductAdded = "PRODUCT ADDED";
  
  public static final String PFMPushProductChanged = "PRODUCT CHANGED";
  
  public static final String PFMPushNonFilter = "PFMNONPUSH";
  
  public static final String BrokerError = "BROKER ERROR: ** PFM PUSH ** ";
  
  public static final String ServiceError = "SERVICE ERROR: ** PFM RECEIVE ** ";
  
  public static final String LegacyProgramError = "LEGACY/MILESTONE INTERFACE PROGRAM";
  
  public static final String LegacyProgramErrorSubject = "PFM Push - Legacy Program Error!";
  
  public static final String LegacyProgramErrorBody = "The Milestone PFM Push application is receiving an error when communicating with the Legacy Service Program";
  
  public static final String MODIFIED = "has been modified:";
  
  public static final String CREATED = "has been created:";
  
  public static final String COMPONENT_CODE = "Push PFM";
  
  public static final String configFile = "pushbean.config";
  
  public static String destEmailAddr = "LCSSupport@umusic.com";
  
  public static String ccEmailAddr = "milestone@umusic.com";
  
  public static String emailHostServer = "usushntws51";
  
  public static String adminEmailAddr = "Milestone";
  
  public static String adminEmailName = "Milestone";
  
  public static String emailSubject = "Milestone Application Error - EJB communication error!";
  
  public static String emailMessage = "The Milestone application is receiving an error when communicating with the Enterprise Java Bean";
  
  public static String emailContentType = "text/html";
  
  public static String MilestoneServer = "";
  
  public static String emailLegacySupport = "FishersLegacySupport@umusic.com";
  
  public static final String[] appSettingsNodeList = { "configuration", "appSettings" };
  
  public static boolean auditLogActive = true;
  
  public static final boolean debug = false;
  
  protected static PushCommunication pushCommunication = null;
  
  public PushCommunication() {
    XMLUtil xmlUtil = new XMLUtil("pushbean.config");
    if (xmlUtil.getDocument() != null) {
      adminEmailAddr = xmlUtil.getElementValueByParentList(emailNodeList, "admin-addr", adminEmailAddr);
      adminEmailName = xmlUtil.getElementValueByParentList(emailNodeList, "admin-name", adminEmailName);
      emailHostServer = xmlUtil.getElementValueByParentList(emailNodeList, "host-server", emailHostServer);
      emailContentType = xmlUtil.getElementValueByParentList(emailNodeList, "content-type", emailContentType);
      emailLegacySupport = xmlUtil.getElementValueByParentList(emailNodeList, "legacy-support", emailLegacySupport);
      ccEmailAddr = xmlUtil.getElementValueByParentList(emailNodeList, "cc-list", ccEmailAddr);
      MilestoneServer = xmlUtil.getElementValueByParentList(milestoneNodeList, "server", "");
      auditLogActive = xmlUtil.getBooleanValueByParentList(appSettingsNodeList, "auditLogActive", auditLogActive);
    } else {
      sendJavaMailOpt("", "", "** Push Communication unable to initialize **\n\nUnable to find document", "Initialize", "");
    } 
  }
  
  public static void log(String s) { System.out.println("Push PFM: " + s); }
  
  public static PushCommunication getInstance() {
    log("<<<< PushCommunication get instance call");
    if (pushCommunication == null)
      pushCommunication = new PushCommunication(); 
    return pushCommunication;
  }
  
  public String SendPFM(PushPFM pushPFM) {
    String strReply = "";
    PushHome pushHome = null;
    PushRemote pushRemote = null;
    try {
      Properties h = new Properties();
      h.put("java.naming.factory.initial", JdbcConnector.INITIAL_CONTEXT_FACTORY);
      h.put("java.naming.provider.url", JdbcConnector.PROVIDER_URL);
      Context initial = new InitialContext(h);
      pushHome = (PushHome)initial.lookup("PushBean");
      if (pushHome == null) {
        log("NULL PushHome.");
        sendJavaMailOpt("", "", "** Unable to find EJB home object for (PushBean) **", pushPFM.getUserId(), "");
        strReply = "** Unable to find EJB home object for (PushBean) **";
      } else {
        log("PushHome not null.");
        pushRemote = pushHome.create();
        if (pushRemote == null) {
          sendJavaMailOpt("", "", "** Unable to create EJB remote object for (PushBean) **", pushPFM.getUserId(), "");
          strReply = "** Unable to create EJB remote object for (PushBean) **";
        } else {
          strReply = pushRemote.SendPFM(pushPFM.toString(), pushPFM.getUserId());
        } 
      } 
    } catch (Exception e) {
      log(e.getMessage());
      strReply = "BROKER ERROR: ** PFM PUSH ** " + e.getMessage();
      sendJavaMailOpt("", "", e.getMessage(), pushPFM.getUserId(), "");
    } 
    return strReply;
  }
  
  public static String pushPFMLegacy(Pfm pfm, Selection selection, Context context) {
    boolean IsAPNG = selection.getLabel().getAPGNInd();
    boolean IsExclude = false;
    String familyName = ReleasingFamily.getName(selection.getReleaseFamilyId());
    if (PushPFM.getPushExcludeFamilies() != null && PushPFM.getPushExcludeFamilies().containsKey(familyName.trim().toUpperCase()))
      IsExclude = true; 
    if (IsExclude)
      return "PFMNONPUSH"; 
    String userIdStr = ((User)context.getSessionValue("user")).getLogin();
    if (userIdStr == null || userIdStr.equals(""))
      userIdStr = "Mileston"; 
    PushPFM pushPFM = new PushPFM(selection.getIsDigital(), 
        selection.getReleaseType().getAbbreviation(), 
        userIdStr, IsAPNG);
    pushPFM.setPFM(pfm, selection);
    PushCommunication push = getInstance();
    String result = push.SendPFM(pushPFM);
    if (result.indexOf("BROKER ERROR: ** PFM PUSH ** ") != -1) {
      pushPFM.setError(result.trim());
    } else {
      pushPFM.setError(result.substring(342, result.length()).trim());
    } 
    sendJavaMailOpt("", "", "test erro push to legacy", "839", "");
    String reply = pushPFM.getError().trim();
    if (reply.equals("") || reply.equals("PRODUCT ADDED") || reply.equals("PRODUCT CHANGED")) {
      if (reply.equals(""))
        pushPFM.setError("PFM Push to Legacy Completed Successfully"); 
      pushAudit(pushPFM, true);
      return pushPFM.getError();
    } 
    pushAudit(pushPFM, false);
    return pushPFM.getError().replace('\'', ' ');
  }
  
  public static void pushAudit(PushPFM pushPFM, boolean result) {
    try {
      String resultStr = result ? "1" : "0";
      StringBuffer query = new StringBuffer();
      query.append("SET QUOTED_IDENTIFIER OFF INSERT INTO [dbo].[Push_Audit]([push_user_id], [push_type], [push_id], [push_message], [push_reply], [push_ok], [push_release_id])");
      query.append(" VALUES(");
      query.append("'" + pushPFM.getUserId() + "',");
      query.append("'" + pushPFM.getPushType() + "',");
      query.append("'" + pushPFM.getProductNo() + "',");
      query.append("'" + MilestoneHelper.escapeSingleQuotes(pushPFM.getMessage()) + "',");
      query.append("\"" + pushPFM.getError() + "\",");
      query.append(String.valueOf(resultStr) + ",");
      query.append(String.valueOf(String.valueOf(pushPFM.getReleaseId())) + ")");
      JdbcConnector connector = MilestoneHelper.getConnector(query.toString());
      connector.runQuery();
      connector.close();
      log("<<< PFM Push Audit added " + pushPFM.getError());
    } catch (Exception e) {
      log("<<< PFM Push Audit write error:\n" + e.getMessage());
      WriteCrossRoadsAuditLog("ErrorEmail", "Push PFM", "Push PFM: " + MilestoneServer, "PFM Push audit write error:<br>" + e.getMessage(), "PFM Push audit write error:");
    } 
    if (pushPFM.getError().indexOf("LEGACY/MILESTONE INTERFACE PROGRAM") != -1)
      sendJavaMailOpt("The Milestone PFM Push application is receiving an error when communicating with the Legacy Service Program", "PFM Push - Legacy Program Error!", pushPFM.getError(), pushPFM.getUserId(), emailLegacySupport); 
  }
  
  public static void pushPFMLegacyAppendMessge(MessageObject messageObject, String pushMessage) {
    if (pushMessage.equals("PFMNONPUSH")) {
      log("<<< PFM Push NON-PUSH FILTER");
      return;
    } 
    log("<<< PFM Push Message added to Email " + pushMessage);
    boolean IsPushOk = false;
    if (pushMessage.equals("PRODUCT ADDED") || 
      pushMessage.equals("PRODUCT CHANGED") || 
      pushMessage.equals("PFM Push to Legacy Completed Successfully"))
      IsPushOk = true; 
    StringBuffer newBody = new StringBuffer();
    String emailBody = messageObject.getEmailBody();
    int pos = -1;
    if (IsPushOk) {
      newBody.append(emailBody);
      pos = IndexOfInsert(newBody.toString());
      if (pos > -1) {
        newBody.deleteCharAt(pos);
        newBody.insert(pos, " and updated in Legacy<BR>");
      } 
    } else {
      messageObject.setEmailSubject("FAILED - " + messageObject.getEmailSubject());
      newBody.append("<span STYLE='font-size: 12pt; color: #ff0000'>");
      newBody.append("<b>FAILED - Legacy Not Updated</b>");
      newBody.append("</span><BR>");
      newBody.append(emailBody);
      pos = IndexOfInsert(newBody.toString());
      if (pos > -1)
        if (pushMessage.indexOf("BROKER ERROR: ** PFM PUSH ** ") != -1) {
          newBody.insert(pos, " and NOT updated in Legacy <br>");
        } else {
          newBody.deleteCharAt(pos);
          newBody.insert(pos, " and NOT updated in Legacy <br><span STYLE='font-size: 12pt; color: #ff0000'><b>" + 
              
              pushMessage + "</b></span><BR>");
        }  
    } 
    messageObject.setEmailBody(newBody.toString());
  }
  
  public static int IndexOfInsert(String inStr) {
    int pos = inStr.indexOf("has been modified:");
    if (pos != -1) {
      pos += "has been modified:".length() - 1;
    } else {
      pos = inStr.indexOf("has been created:");
      if (pos != -1)
        pos += "has been created:".length() - 1; 
    } 
    return pos;
  }
  
  public static boolean sendJavaMailOpt(String ovrEmailBody, String ovrEmailSubject, String errorMsg, String userID, String ovrEmailToList) {
    boolean sendStatus = false;
    String body = "<b>" + emailMessage + "</b>";
    String subject = emailSubject;
    String toList = destEmailAddr;
    if (!ovrEmailToList.equals(""))
      toList = ovrEmailToList; 
    if (!ovrEmailBody.equals(""))
      body = "<b>" + ovrEmailBody + "</b>"; 
    if (!ovrEmailSubject.equals(""))
      subject = ovrEmailSubject; 
    StringTokenizer Addrtokenizer = new StringTokenizer(toList, ";");
    StringTokenizer ccAddrtokenizer = new StringTokenizer(ccEmailAddr, ";");
    InternetAddress[] iAddresses = new InternetAddress[Addrtokenizer.countTokens()];
    InternetAddress[] ccAddresses = new InternetAddress[ccAddrtokenizer.countTokens()];
    int x = 0;
    while (Addrtokenizer.hasMoreTokens()) {
      try {
        iAddresses[x] = new InternetAddress(Addrtokenizer.nextToken());
        x++;
      } catch (Exception e) {
        log(e.toString());
        return sendStatus;
      } 
    } 
    x = 0;
    while (ccAddrtokenizer.hasMoreTokens()) {
      try {
        ccAddresses[x] = new InternetAddress(ccAddrtokenizer.nextToken());
        x++;
      } catch (Exception e) {
        log(e.toString());
        return sendStatus;
      } 
    } 
    try {
      Properties props = System.getProperties();
      props.put("mail.smtp.host", emailHostServer.trim());
      Session session = Session.getDefaultInstance(props, null);
      MimeMessage message = new MimeMessage(session);
      message.setFrom(new InternetAddress(adminEmailAddr, adminEmailName));
      message.setSubject(subject, "utf-8");
      body = String.valueOf(body) + "<BR><BR><b>" + errorMsg + "</b>" + 
        "<BR><BR><font color='#0000FF'><B> Server: " + MilestoneServer + "</B>:</font>" + 
        "<BR><BR><font color='#0000FF'><B> User:   " + userID + "</B>:</font>";
      message.setContent(body, emailContentType);
      try {
        message.addRecipients(Message.RecipientType.CC, ccAddresses);
        message.addRecipients(Message.RecipientType.TO, iAddresses);
        Transport.send(message);
        log("<<< Session Email Address - Java Mail - Message Sent");
        sendStatus = true;
      } catch (Exception e) {
        e.printStackTrace();
        log("<<< Session Email Transport exception " + e.getMessage() + ", " + e.toString());
        sendStatus = false;
      } 
    } catch (Exception e) {
      log("<<< Session Email Transport exception " + e.getMessage());
      sendStatus = false;
    } 
    return sendStatus;
  }
  
  public static boolean WriteCrossRoadsAuditLog(String type, String method, String description, String errMsg, String subject) { return true; }
}


/* Location:              D:\Documents\NetBeansProjects\milestone2 Local.war!\WEB-INF\classes\co\\universal\milestone\push\PushCommunication.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */