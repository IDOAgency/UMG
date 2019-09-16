package WEB-INF.classes.com.universal.milestone;

import com.universal.milestone.JdbcConnector;
import com.universal.milestone.PnrCommunication;
import com.universal.milestone.pnr.PnrHome;
import com.universal.milestone.pnr.PnrRemote;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Properties;
import java.util.StringTokenizer;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.naming.Context;
import javax.naming.InitialContext;

public class PnrCommunication {
  public static final String COMPONENT_CODE = "Pnr";
  
  protected static final String configFile = "pnrbean.conf";
  
  protected static String destEmailAddr = "mark.cole@umusic.com";
  
  protected static String ccEmailAddr = "marla.hall@umusic.com; tommy.thomas@umusic.com";
  
  protected static String emailHostServer = "usushntws51";
  
  protected static String adminEmailAddr = "Milestone";
  
  protected static String adminEmailName = "Milestone";
  
  protected static String emailSubject = "Milestone Application Error - EJB communication error!";
  
  protected static String emailMessage = "The Milestone application is receiving an error when communicating with the Enterprise Java Bean";
  
  protected static String emailContentType = "text/html";
  
  protected static String MilestoneServer = "";
  
  protected static PnrCommunication pnrCommunication = null;
  
  public PnrCommunication() {
    try {
      InputStream in = ClassLoader.getSystemResourceAsStream("pnrbean.conf");
      if (in == null)
        in = new FileInputStream("pnrbean.conf"); 
      Properties defaultProps = new Properties();
      defaultProps.load(in);
      adminEmailAddr = defaultProps.getProperty("AdminEmailAddr", adminEmailAddr);
      adminEmailName = defaultProps.getProperty("AdminEmailName", adminEmailName);
      emailHostServer = defaultProps.getProperty("EmailHostServer", emailHostServer);
      emailContentType = defaultProps.getProperty("EmailContentType", emailContentType);
      MilestoneServer = defaultProps.getProperty("MilestoneServer", "");
      in.close();
    } catch (Exception exception) {}
  }
  
  public static PnrCommunication getInstance() {
    if (pnrCommunication == null)
      pnrCommunication = new PnrCommunication(); 
    return pnrCommunication;
  }
  
  public static String GetPNR(String userID, String operatingCompany, String superLabel, String configCode, String titleID, String title, String artistFName, String artistLName, String releaseDate, String labelCode, String projectID, String upc, String soundScan) {
    String strReply = "";
    PnrHome home = null;
    PnrRemote pnr = null;
    try {
      Properties h = new Properties();
      h.put("java.naming.factory.initial", JdbcConnector.INITIAL_CONTEXT_FACTORY);
      h.put("java.naming.provider.url", JdbcConnector.PROVIDER_URL);
      Context initial = new InitialContext(h);
      home = (PnrHome)initial.lookup("PnrBean");
      if (home == null) {
        sendJavaMailOpt("** Unable to find EJB home object for (PnrBean) **", userID);
      } else {
        pnr = home.create();
        if (pnr == null)
          sendJavaMailOpt("** Unable to create EJB remote object for (PnrBean) **", userID); 
        SimpleDateFormat df = new SimpleDateFormat("m/d/yy");
        strReply = pnr.getPnr(userID, 
            operatingCompany, 
            superLabel, 
            configCode, 
            titleID, 
            title, 
            artistFName, 
            artistLName, 
            df.parse(releaseDate), 
            labelCode, 
            projectID, 
            upc, 
            soundScan);
      } 
    } catch (Exception e) {
      System.out.println(e.getMessage());
      strReply = "BROKER ERROR: " + e.getMessage();
      sendJavaMailOpt(e.getMessage(), userID);
    } 
    return strReply;
  }
  
  public static boolean sendJavaMailOpt(String messageMsg, String userID) {
    boolean sendStatus = false;
    StringTokenizer Addrtokenizer = new StringTokenizer(destEmailAddr, ";");
    StringTokenizer ccAddrtokenizer = new StringTokenizer(ccEmailAddr, ";");
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
      props.put("mail.smtp.host", emailHostServer.trim());
      Session session = Session.getDefaultInstance(props, null);
      MimeMessage message = new MimeMessage(session);
      message.setFrom(new InternetAddress(adminEmailAddr, adminEmailName));
      String subject = emailSubject;
      if (subject != null && subject.length() > 0) {
        message.setSubject(subject, "utf-8");
      } else {
        message.setSubject(emailSubject, "utf-8");
      } 
      String emailBody = "<b>" + emailMessage + "</b><BR><BR>" + "<b>" + messageMsg + "</b>" + 
        "<BR><BR><font color='#0000FF'><B> Server: " + MilestoneServer + "</B>:</font>" + 
        "<BR><BR><font color='#0000FF'><B> User:   " + userID + "</B>:</font>";
      message.setContent(emailBody, emailContentType);
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


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\PnrCommunication.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */