package WEB-INF.classes.com.universal.milestone;

import com.techempower.ComponentLog;
import com.techempower.EnhancedProperties;
import com.techempower.gemini.Context;
import com.techempower.gemini.GeminiApplication;
import com.universal.milestone.EmailDistribution;
import com.universal.milestone.EmailDistributionDetail;
import com.universal.milestone.EmailDistributionManager;
import com.universal.milestone.EmailDistributionReleasingFamily;
import com.universal.milestone.Environment;
import com.universal.milestone.JdbcConnector;
import com.universal.milestone.Label;
import com.universal.milestone.MilestoneConstants;
import com.universal.milestone.MilestoneHelper;
import com.universal.milestone.MilestoneHelper_2;
import com.universal.milestone.Notepad;
import com.universal.milestone.SessionUserEmail;
import com.universal.milestone.SessionUserEmailObj;
import com.universal.milestone.User;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.Vector;

public class EmailDistributionManager implements MilestoneConstants {
  public static final String COMPONENT_CODE = "mEmailDistr";
  
  public static final String DEFAULT_HEADER_QUERY = "SELECT * FROM vi_email_distribution_header ";
  
  public static final String DEFAULT_HEADER_ORDER = " ORDER BY last_name, first_name";
  
  public static final int WestAndEast = 2;
  
  public static final int None = 3;
  
  public static final int UME_CATALOG = 1052;
  
  protected static EmailDistributionManager emailDistributionManager = null;
  
  protected static ComponentLog log;
  
  public static void configure(EnhancedProperties props, GeminiApplication application) { log = application.getLog("mEmailDistr"); }
  
  public static EmailDistributionManager getInstance() {
    if (emailDistributionManager == null)
      emailDistributionManager = new EmailDistributionManager(); 
    return emailDistributionManager;
  }
  
  public EmailDistribution getEmailDistribution(int distributionId, boolean getTimestamp) {
    String userQuery = 
      "SELECT * FROM vi_Email_Distribution_Header WHERE distribution_id = " + distributionId + ";";
    EmailDistribution emailDist = null;
    JdbcConnector connector = MilestoneHelper.getConnector(userQuery);
    connector.runQuery();
    if (connector.more()) {
      emailDist = new EmailDistribution();
      emailDist.setDistributionId(connector.getInt("distribution_id"));
      emailDist.setFirstName(connector.getField("first_name", ""));
      emailDist.setLastName(connector.getField("last_name", ""));
      emailDist.setEmail(connector.getField("email", ""));
      emailDist.setPfm(connector.getBoolean("pfm"));
      emailDist.setBom(connector.getBoolean("bom"));
      emailDist.setPromo(connector.getBoolean("promo"));
      emailDist.setCommercial(connector.getBoolean("commercial"));
      emailDist.setInactive(connector.getBoolean("inactive"));
      emailDist.setLabelDistribution(connector.getInt("label_distribution"));
      emailDist.setProductType(connector.getInt("product_type"));
      long lastUpdatedLong = Long.parseLong(connector.getField("last_updated_ck"), 16);
      int lastUpdatedBy = connector.getIntegerField("last_updated_by");
      String lastDateString = connector.getField("last_updated_on");
      connector.close();
      if (lastDateString != null)
        emailDist.setLastUpdatedOn(MilestoneHelper.getDatabaseDate(lastDateString)); 
      emailDist.setLastUpdatedBy(lastUpdatedBy);
      if (getTimestamp)
        emailDist.setLastUpdatedCk(lastUpdatedLong); 
      emailDist.setEmailDistributionDetail(getEmailDistributionDetail(distributionId));
      emailDist.setAssignedEnvironments(getAssignedEnvironments(emailDist.getDistributionId()));
      emailDist.setReleasingFamily(EmailDistributionReleasingFamily.get(emailDist.getDistributionId()));
    } 
    return emailDist;
  }
  
  public EmailDistribution save(EmailDistribution emailDist, User sessionUser, Vector checkedDetail, Context context) {
    long timestamp = emailDist.getLastUpdatedCk();
    String query = "sp_sav_Email_Distribution_Header " + 
      emailDist.getDistributionId() + 
      ",'" + MilestoneHelper.escapeSingleQuotes(emailDist.getFirstName()) + "'" + 
      ",'" + MilestoneHelper.escapeSingleQuotes(emailDist.getLastName()) + "'" + 
      ",'" + MilestoneHelper.escapeSingleQuotes(emailDist.getEmail()) + "'" + 
      "," + (emailDist.getPfm() ? 1 : 0) + 
      "," + (emailDist.getBom() ? 1 : 0) + 
      "," + (emailDist.getPromo() ? 1 : 0) + 
      "," + (emailDist.getCommercial() ? 1 : 0) + 
      "," + sessionUser.getUserId() + 
      "," + timestamp + 
      "," + (emailDist.getInactive() ? 1 : 0) + 
      "," + emailDist.getLabelDistribution() + 
      "," + emailDist.getProductType();
    JdbcConnector connector = MilestoneHelper.getConnector(query);
    connector.runQuery();
    int savedDistributionId = connector.getIntegerField("ReturnId");
    connector.close();
    if (savedDistributionId > 0)
      emailDist.setDistributionId(savedDistributionId); 
    String timestampQuery = "SELECT last_updated_ck, last_updated_on FROM vi_Email_Distribution_Header WHERE distribution_id = " + 
      
      emailDist.getDistributionId() + 
      ";";
    JdbcConnector connectorTimestamp = MilestoneHelper.getConnector(timestampQuery);
    connectorTimestamp.runQuery();
    if (connectorTimestamp.more()) {
      emailDist.setLastUpdatedCk(Long.parseLong(connectorTimestamp.getField("last_updated_ck"), 16));
      String lastString = connectorTimestamp.getField("last_updated_on");
      if (lastString != null)
        emailDist.setLastUpdatedOn(MilestoneHelper.getDatabaseDate(connectorTimestamp.getField("last_updated_on"))); 
    } 
    connectorTimestamp.close();
    int distributionId = emailDist.getDistributionId();
    Vector emailDistDetails = getEmailDistributionDetail(distributionId);
    boolean isNew = true;
    boolean isChecked = false;
    boolean isUnChecked = false;
    for (int b = 0; b < emailDistDetails.size(); b++) {
      EmailDistributionDetail emailDistDetail = (EmailDistributionDetail)emailDistDetails.elementAt(b);
      if (emailDistDetail != null) {
        isChecked = false;
        for (int j = 0; j < checkedDetail.size(); j++) {
          Environment checkEnvironment = (Environment)checkedDetail.get(j);
          if (checkEnvironment != null && checkEnvironment.getStructureID() == emailDistDetail.getStructureId()) {
            isChecked = true;
            break;
          } 
        } 
        if (!isChecked)
          deleteEmailDistributionDetail(distributionId, emailDistDetail.getStructureId()); 
      } 
    } 
    for (int l = 0; l < checkedDetail.size(); l++) {
      Environment checkEnvironment = (Environment)checkedDetail.get(l);
      if (checkEnvironment != null) {
        isNew = true;
        for (int c = 0; c < emailDistDetails.size(); c++) {
          EmailDistributionDetail emailDistDetail = (EmailDistributionDetail)emailDistDetails.elementAt(c);
          if (emailDistDetail != null && emailDistDetail.getStructureId() == checkEnvironment.getStructureID()) {
            isNew = false;
            break;
          } 
        } 
        if (isNew)
          addEmailDistributionDetail(distributionId, checkEnvironment.getStructureID(), sessionUser.getUserId()); 
      } 
    } 
    context.removeSessionValue("copiedEmailDistObj");
    return emailDist;
  }
  
  public static Vector getEmailDistributionDetail(int distributionId) {
    String strQuery = "SELECT b.* FROM vi_email_distribution_detail b WHERE distribution_id = " + 
      
      distributionId + 
      " ORDER BY b.distribution_id;";
    Vector precache = new Vector();
    JdbcConnector connector = MilestoneHelper.createConnector(strQuery);
    connector.setForwardOnly(false);
    connector.runQuery();
    while (connector.more()) {
      EmailDistributionDetail environment = new EmailDistributionDetail();
      environment.setDistributionId(connector.getIntegerField("distribution_id"));
      environment.setStructureId(connector.getIntegerField("structure_id"));
      precache.add(environment);
      environment = null;
      connector.next();
    } 
    connector.close();
    return precache;
  }
  
  public void deleteEmailDistribution(int distributionId) {
    String query = "sp_del_Email_Distribution_Detail " + 
      distributionId + "," + 
      -1;
    JdbcConnector connector = MilestoneHelper.getConnector(query);
    connector.runQuery();
    connector.close();
    query = "sp_del_Email_Distribution_Header " + 
      distributionId;
    connector = MilestoneHelper.getConnector(query);
    connector.runQuery();
    connector.close();
  }
  
  public void saveCopyEmailDistributionReleasingFamily(int distributionId, int copyDistributionId, int userId) {
    String query = "sp_saveCopy_Email_Distribution_ReleasingFamily " + distributionId + "," + 
      copyDistributionId + "," + userId;
    JdbcConnector connector = MilestoneHelper.getConnector(query);
    connector.runQuery();
    connector.close();
  }
  
  public static Vector getDistributionNotepadList(Notepad notepad) {
    String query = "";
    if (notepad != null && !notepad.getSearchQuery().equals("")) {
      query = notepad.getSearchQuery();
      query = String.valueOf(query) + notepad.getOrderBy();
    } else {
      query = "SELECT * FROM vi_email_distribution_header  ORDER BY last_name, first_name";
    } 
    JdbcConnector connector = MilestoneHelper.getConnector(query);
    connector.runQuery();
    Vector precache = new Vector();
    while (connector.more()) {
      EmailDistribution emailDist = new EmailDistribution();
      emailDist.setDistributionId(connector.getInt("distribution_id"));
      emailDist.setFirstName(connector.getField("first_name", ""));
      emailDist.setLastName(connector.getField("last_name", ""));
      emailDist.setEmail(connector.getField("email", ""));
      emailDist.setPfm(connector.getBoolean("pfm"));
      emailDist.setBom(connector.getBoolean("bom"));
      emailDist.setPromo(connector.getBoolean("promo"));
      emailDist.setCommercial(connector.getBoolean("commercial"));
      emailDist.setInactive(connector.getBoolean("inactive"));
      emailDist.setLabelDistribution(connector.getInt("label_distribution"));
      emailDist.setProductType(connector.getInt("product_type"));
      emailDist.setEmailDistributionDetail(getEmailDistributionDetail(emailDist.getDistributionId()));
      precache.addElement(emailDist);
      emailDist = null;
      connector.next();
    } 
    connector.close();
    return precache;
  }
  
  public static String getDefaultQuery() { return "SELECT * FROM vi_email_distribution_header "; }
  
  public static void deleteEmailDistributionDetail(int distributionId, int structureId) {
    String query = "sp_del_Email_Distribution_Detail " + 
      distributionId + "," + 
      structureId;
    JdbcConnector connector = MilestoneHelper.getConnector(query);
    connector.runQuery();
    connector.close();
  }
  
  public static void addEmailDistributionDetail(int distributionId, int structureId, int updator) {
    String query = "sp_sav_Email_Distribution_Detail " + 
      distributionId + "," + 
      structureId + "," + 
      updator;
    JdbcConnector connector = MilestoneHelper.getConnector(query);
    connector.runQuery();
    connector.close();
  }
  
  public void setEmailDistributionNotepadQuery(Context context, Notepad notepad) {
    if (notepad != null) {
      String firstNameSearch = context.getParameter("firstNameSrch");
      String lastNameSearch = context.getParameter("lastNameSrch");
      String formTypeSearch = context.getParameter("formTypeSrch");
      String releaseTypeSearch = context.getParameter("releaseTypeSrch");
      String productTypeSearch = context.getParameter("productTypeSrch");
      String environmentSearch = context.getParameter("environmentSrch");
      if (environmentSearch == null)
        environmentSearch = "0"; 
      String params = "";
      String query = "SELECT * FROM vi_email_distribution_header ";
      if (!environmentSearch.equals("0")) {
        query = String.valueOf(query) + " JOIN vi_email_distribution_detail   ON vi_email_distribution_header.distribution_id =   vi_email_distribution_detail.distribution_id ";
        query = String.valueOf(query) + MilestoneHelper.addQueryParams(query, " vi_email_distribution_detail.structure_id = " + environmentSearch);
      } 
      if (MilestoneHelper.isStringNotEmpty(firstNameSearch))
        query = String.valueOf(query) + MilestoneHelper.addQueryParams(query, " vi_email_distribution_header.first_Name " + 
            MilestoneHelper.setWildCardsEscapeSingleQuotes(firstNameSearch)); 
      if (MilestoneHelper.isStringNotEmpty(lastNameSearch))
        query = String.valueOf(query) + MilestoneHelper.addQueryParams(query, " vi_email_distribution_header.last_Name " + 
            MilestoneHelper.setWildCardsEscapeSingleQuotes(lastNameSearch)); 
      if (MilestoneHelper.isStringNotEmpty(formTypeSearch) && !formTypeSearch.equals("-1"))
        if (formTypeSearch.equals("pfm")) {
          query = String.valueOf(query) + MilestoneHelper.addQueryParams(query, " vi_email_distribution_header.pfm = 1 ");
          query = String.valueOf(query) + MilestoneHelper.addQueryParams(query, " vi_email_distribution_header.bom = 0 ");
        } else if (formTypeSearch.equals("bom")) {
          query = String.valueOf(query) + MilestoneHelper.addQueryParams(query, " vi_email_distribution_header.bom = 1 ");
          query = String.valueOf(query) + MilestoneHelper.addQueryParams(query, " vi_email_distribution_header.pfm = 0 ");
        }  
      if (MilestoneHelper.isStringNotEmpty(releaseTypeSearch) && !releaseTypeSearch.equals("-1"))
        if (releaseTypeSearch.equals("promo")) {
          query = String.valueOf(query) + MilestoneHelper.addQueryParams(query, " vi_email_distribution_header.promo = 1 ");
          query = String.valueOf(query) + MilestoneHelper.addQueryParams(query, " vi_email_distribution_header.commercial = 0 ");
        } else if (releaseTypeSearch.equals("commercial")) {
          query = String.valueOf(query) + MilestoneHelper.addQueryParams(query, " vi_email_distribution_header.commercial = 1 ");
          query = String.valueOf(query) + MilestoneHelper.addQueryParams(query, " vi_email_distribution_header.promo = 0 ");
        }  
      if (MilestoneHelper.isStringNotEmpty(productTypeSearch) && !productTypeSearch.equals("-1"))
        query = String.valueOf(query) + MilestoneHelper.addQueryParams(query, " vi_email_distribution_header.product_type  = " + productTypeSearch); 
      String order = " ORDER BY last_name, first_name";
      notepad.setSearchQuery(query);
      notepad.setOrderBy(order);
    } 
  }
  
  public boolean isTimestampValid(EmailDistribution emailDistribution) {
    if (emailDistribution != null) {
      String timestampQuery = "SELECT last_updated_ck  FROM vi_email_distribution_header WHERE distribution_id = " + 
        
        emailDistribution.getDistributionId() + 
        ";";
      JdbcConnector connectorTimestamp = MilestoneHelper.getConnector(timestampQuery);
      connectorTimestamp.runQuery();
      if (connectorTimestamp.more())
        if (emailDistribution.getLastUpdatedCk() != Long.parseLong(connectorTimestamp.getField("last_updated_ck", ""), 16)) {
          connectorTimestamp.close();
          return false;
        }  
      connectorTimestamp.close();
      return true;
    } 
    return false;
  }
  
  public boolean isDuplicate(EmailDistribution emailDistribution) {
    boolean isDuplicate = false;
    if (emailDistribution != null) {
      String query = "SELECT * FROM vi_email_distribution_header WHERE  first_name = '" + 
        MilestoneHelper.escapeSingleQuotes(emailDistribution.getFirstName()) + "' " + 
        " and last_name = '" + MilestoneHelper.escapeSingleQuotes(emailDistribution.getLastName()) + "' " + 
        " AND distribution_id <> " + emailDistribution.getDistributionId();
      JdbcConnector connector = MilestoneHelper.getConnector(query);
      connector.runQuery();
      if (connector.more())
        isDuplicate = true; 
      connector.close();
    } 
    return isDuplicate;
  }
  
  public static String getEmailTo(Environment environment, String formType, String releaseType, Label label, boolean IsDigital, int releaseFamilyId, int familyId) {
    StringBuffer emailTo = new StringBuffer();
    emailTo.append("");
    String strQuery = "SELECT d.distribution_id, d.structure_id, h.email, h.inactive, h.pfm, h.bom,h.promo, h.commercial, h.label_distribution, h.product_type  FROM vi_email_distribution_detail d  inner join vi_email_distribution_header h on d.distribution_id = h.distribution_id  WHERE d.structure_id = " + 
      
      environment.getStructureID() + 
      " ORDER BY d.structure_id;";
    JdbcConnector connector = MilestoneHelper.createConnector(strQuery);
    connector.setForwardOnly(false);
    connector.runQuery();
    while (connector.more()) {
      int distributionId = connector.getInt("distribution_id", -1);
      boolean inactive = connector.getBoolean("inactive");
      boolean pfm = connector.getBoolean("pfm");
      boolean bom = connector.getBoolean("bom");
      boolean promo = connector.getBoolean("promo");
      boolean commercial = connector.getBoolean("commercial");
      int label_distribution = connector.getInt("label_distribution", 2);
      int product_type = connector.getInt("product_type", 2);
      if (!inactive)
        if (connector.getInt("structure_id") == environment.getStructureID() && ((
          pfm && formType.equalsIgnoreCase("PFM")) || (
          bom && formType.equalsIgnoreCase("BOM"))))
          if ((promo && releaseType.equalsIgnoreCase("PR")) || (
            commercial && releaseType.equalsIgnoreCase("CO"))) {
            Hashtable releasingFamilyHash = EmailDistributionReleasingFamily.get(distributionId);
            if (releasingFamilyHash == null || (
              releasingFamilyHash != null && releasingFamilyHash.get(Integer.toString(familyId)) == null)) {
              releasingFamilyHash = new Hashtable();
              Vector releasingFamilies = new Vector();
              releasingFamilies.add(new EmailDistributionReleasingFamily(familyId, familyId, true, "", ""));
              releasingFamilyHash.put(Integer.toString(familyId), releasingFamilies);
            } 
            boolean found = false;
            Vector releasingFamilies = (Vector)releasingFamilyHash.get(Integer.toString(familyId));
            if (releasingFamilies != null)
              for (int i = 0; i < releasingFamilies.size(); i++) {
                EmailDistributionReleasingFamily releasingFamily = (EmailDistributionReleasingFamily)releasingFamilies.get(i);
                if (releasingFamily != null && releasingFamily.getReleasingFamilyId() == releaseFamilyId) {
                  found = true;
                  break;
                } 
              }  
            System.out.println("EMailDistributionManager::getEmailTo:found = ");
            System.out.print(found);
            if (found) {
              if (releaseFamilyId != 1052 || 
                label_distribution == 2) {
                if (checkDigital(IsDigital, product_type))
                  emailTo.append(String.valueOf(connector.getField("email", "")) + ";"); 
              } else if (label_distribution != 3 && 
                label.getDistribution() == label_distribution) {
                if (checkDigital(IsDigital, product_type))
                  emailTo.append(String.valueOf(connector.getField("email", "")) + ";"); 
              } 
              System.out.println("EMailDistributionManager::getEmailTo:connector.getField('email', '')= ");
              System.out.print(connector.getField("email", ""));
            } 
          }   
      connector.next();
    } 
    connector.close();
    return emailTo.toString();
  }
  
  public static boolean checkDigital(boolean IsDigital, int product_type) {
    if (product_type == 2)
      return true; 
    if (IsDigital && product_type == 0)
      return true; 
    if (!IsDigital && product_type == 1)
      return true; 
    return false;
  }
  
  public static boolean sendEmailDistribution(String subject, String body, String recipients, String copyRecipients, String attachment) {
    boolean result = false;
    try {
      StringTokenizer valueTokenizer = new StringTokenizer(recipients, ";");
      ArrayList destList = new ArrayList();
      while (valueTokenizer.hasMoreTokens())
        destList.add(new SessionUserEmailObj(valueTokenizer.nextToken().trim(), true)); 
      StringTokenizer ccValueTokenizer = new StringTokenizer(copyRecipients, ";");
      ArrayList ccList = new ArrayList();
      while (ccValueTokenizer.hasMoreTokens())
        ccList.add(new SessionUserEmailObj(ccValueTokenizer.nextToken().trim(), true)); 
      SessionUserEmail sue = new SessionUserEmail();
      body = "<font face='arial'>" + body + "</font>";
      result = sue.sendEmail(destList, body, subject, attachment, ccList);
      if (!result) {
        String emailAuditSQL = "INSERT INTO [dbo].[Email_Distribution_audit] ";
        emailAuditSQL = String.valueOf(emailAuditSQL) + " ([subject], [body], [recipients], [copyRecipients], ";
        emailAuditSQL = String.valueOf(emailAuditSQL) + " [fileName], [serverName], [errorCode]) ";
        emailAuditSQL = String.valueOf(emailAuditSQL) + " VALUES('" + MilestoneHelper.escapeSingleQuotes(subject) + "',";
        emailAuditSQL = String.valueOf(emailAuditSQL) + "'" + MilestoneHelper.escapeSingleQuotes(body) + "',";
        emailAuditSQL = String.valueOf(emailAuditSQL) + "'" + MilestoneHelper.escapeSingleQuotes(recipients) + "',";
        emailAuditSQL = String.valueOf(emailAuditSQL) + "'" + MilestoneHelper.escapeSingleQuotes(copyRecipients) + "',";
        emailAuditSQL = String.valueOf(emailAuditSQL) + "'" + MilestoneHelper.escapeSingleQuotes(attachment) + "',";
        emailAuditSQL = String.valueOf(emailAuditSQL) + "'" + JdbcConnector.PROVIDER_URL + "','1')";
        JdbcConnector connector = MilestoneHelper.getConnector(emailAuditSQL);
        connector.runQuery();
        connector.close();
        SessionUserEmail sueFailed = new SessionUserEmail();
        ArrayList destListMilestone = new ArrayList();
        String userEmail = "";
        Properties fileProps = MilestoneHelper_2.readConfigFile(MilestoneHelper_2.MILESTONE_CONFIG_FILE);
        if (fileProps != null) {
          userEmail = fileProps.getProperty("EmailDistributionSessionUserEmail");
          if (userEmail == null) {
            log.debug("EmailDistributionManager: sendEmailDistribution: no email is sent out because of no property 'EmailDistributionSessionUserEmail' in " + MilestoneHelper_2.MILESTONE_CONFIG_FILE);
            return result;
          } 
        } else {
          log.debug("EmailDistributionManager: sendEmailDistribution: failed to get property 'EmailDistributionSessionUserEmail' from" + MilestoneHelper_2.MILESTONE_CONFIG_FILE);
          return result;
        } 
        StringTokenizer userEmailTokenizer = new StringTokenizer(userEmail, ";");
        while (userEmailTokenizer.hasMoreTokens())
          destListMilestone.add(new SessionUserEmailObj(userEmailTokenizer.nextToken().trim(), true)); 
        String bodyFailed = "<b>The following message failed to send:</b><P><b>Subject:</b>" + 
          subject + "<BR>" + 
          "<b>Body:</b>" + body + "<BR>" + 
          "<b>To:</b>" + recipients + "<BR>" + 
          "<b>CC:</b>" + copyRecipients + "<BR>" + 
          "<b>Attachment:</b>" + attachment;
        ArrayList cc = new ArrayList();
        sueFailed.sendEmail(destListMilestone, bodyFailed, "Email failed to send", "", cc);
      } 
    } catch (Exception e) {
      System.out.println("send email exception...");
    } 
    return result;
  }
  
  public static Vector getAssignedEnvironments(int distributionId) {
    Vector assignedEnvironments = null;
    String userQuery = "SELECT * FROM vi_Email_Distribution_Detail WHERE distribution_id = " + distributionId + ";";
    JdbcConnector connector = MilestoneHelper.getConnector(userQuery);
    connector.runQuery();
    while (connector.more()) {
      if (assignedEnvironments == null)
        assignedEnvironments = new Vector(); 
      int environmentId = connector.getInt("structure_id", -1);
      if (environmentId != -1) {
        Environment environment = MilestoneHelper.getEnvironmentById(environmentId);
        if (environment != null)
          if (!assignedEnvironments.contains(environment))
            assignedEnvironments.add(environment);  
      } 
      connector.next();
    } 
    connector.close();
    return assignedEnvironments;
  }
}


/* Location:              D:\Documents\NetBeansProjects\milestone2 Local.war!\WEB-INF\classes\co\\universal\milestone\EmailDistributionManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */