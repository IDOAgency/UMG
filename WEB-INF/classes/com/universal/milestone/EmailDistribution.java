package WEB-INF.classes.com.universal.milestone;

import com.techempower.DataEntity;
import com.techempower.gemini.Context;
import com.techempower.gemini.Dispatcher;
import com.techempower.gemini.FormCheckBox;
import com.techempower.gemini.FormDropDownMenu;
import com.techempower.gemini.FormElement;
import com.universal.milestone.Cache;
import com.universal.milestone.EmailDistribution;
import com.universal.milestone.EmailDistributionManager;
import com.universal.milestone.Form;
import com.universal.milestone.InkChange;
import com.universal.milestone.LookupObject;
import com.universal.milestone.MilestoneHelper_2;
import com.universal.milestone.NotepadContentObject;
import com.universal.milestone.Selection;
import com.universal.milestone.User;
import com.universal.milestone.jms.MessageObject;
import com.universal.milestone.push.PushPFM;
import inetsoft.report.XStyleSheet;
import inetsoft.report.pdf.PDF4Generator;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Properties;
import java.util.Vector;

public class EmailDistribution extends DataEntity implements NotepadContentObject, Cloneable {
  protected int distributionId = -1;
  
  protected int copyDistributionId = -1;
  
  protected String firstName = null;
  
  protected String lastName = null;
  
  protected String email = null;
  
  protected boolean pfm = false;
  
  protected boolean bom = false;
  
  protected boolean promo = false;
  
  protected boolean commercial = false;
  
  protected Calendar lastUpdatedOn;
  
  protected int lastUpdatedBy;
  
  protected long lastUpdatedCk = -1L;
  
  protected boolean inactive = false;
  
  protected Vector emailDistributionDetail = null;
  
  protected int labelDistribution = 0;
  
  protected int productType = 1;
  
  public static String emailOutDir = null;
  
  public static String emailAttachDir = null;
  
  protected static Object lockObject = new Object();
  
  public static final int DIGITAL = 0;
  
  public static final int PHYSICAL = 1;
  
  public static final int BOTH = 2;
  
  protected Hashtable releasingFamilyHash = null;
  
  protected Vector assignedEnvironments = null;
  
  public EmailDistribution() {}
  
  public EmailDistribution(int distribution_id) { this.distributionId = distribution_id; }
  
  public int getDistributionId() { return this.distributionId; }
  
  public void setDistributionId(int distributionId) { this.distributionId = distributionId; }
  
  public int getCopyDistributionId() { return this.copyDistributionId; }
  
  public void setCopyDistributionId(int copyDistributionId) { this.copyDistributionId = copyDistributionId; }
  
  public String getFirstName() { return this.firstName; }
  
  public void setFirstName(String firstName) { this.firstName = firstName; }
  
  public String getLastName() { return this.lastName; }
  
  public void setLastName(String lastName) { this.lastName = lastName; }
  
  public String getEmail() { return this.email; }
  
  public void setEmail(String email) { this.email = email; }
  
  public Calendar getLastUpdateOn() { return this.lastUpdatedOn; }
  
  public void setLastUpdatedOn(Calendar lastUpdatedOn) { this.lastUpdatedOn = lastUpdatedOn; }
  
  public int getLastUpdateBy() { return this.lastUpdatedBy; }
  
  public void setLastUpdatedBy(int lastUpdatedBy) { this.lastUpdatedBy = lastUpdatedBy; }
  
  public long getLastUpdatedCk() { return this.lastUpdatedCk; }
  
  public void setLastUpdatedCk(long lastUpdatedCk) { this.lastUpdatedCk = lastUpdatedCk; }
  
  public boolean getInactive() { return this.inactive; }
  
  public void setInactive(boolean inactive) { this.inactive = inactive; }
  
  public boolean getPfm() { return this.pfm; }
  
  public void setPfm(boolean pfm) { this.pfm = pfm; }
  
  public boolean getBom() { return this.bom; }
  
  public void setBom(boolean bom) { this.bom = bom; }
  
  public boolean getPromo() { return this.promo; }
  
  public void setPromo(boolean promo) { this.promo = promo; }
  
  public boolean getCommercial() { return this.commercial; }
  
  public void setCommercial(boolean commercial) { this.commercial = commercial; }
  
  public Vector getEmailDistributionDetail() { return this.emailDistributionDetail; }
  
  public void setEmailDistributionDetail(Vector emailDistributionDetail) { this.emailDistributionDetail = emailDistributionDetail; }
  
  public int getLabelDistribution() { return this.labelDistribution; }
  
  public void setLabelDistribution(int labelDistribution) { this.labelDistribution = labelDistribution; }
  
  public int getProductType() { return this.productType; }
  
  public void setProductType(int productType) { this.productType = productType; }
  
  public static boolean putEmailBody(Form form, Context context, Selection selection, String lastChgDate, String lastChgBy, String formType, MessageObject messageObject) {
    if (messageObject != null && messageObject.selectionObj != null)
      selection = messageObject.selectionObj; 
    User user = (User)context.getSessionValue("user");
    StringBuffer changedFields = new StringBuffer();
    boolean isNew = false;
    String changeNumber = form.getStringValue("ChangeNumber");
    if (formType.equalsIgnoreCase("PFM")) {
      String mode = form.getStringValue("mode");
      String prodType = selection.getIsDigital() ? "Digital" : "Physical";
      if (mode.equalsIgnoreCase("Add")) {
        isNew = true;
        changedFields.append("<BR>The following " + prodType + " " + formType + " has been created:<P>");
      } else {
        isNew = false;
        changedFields.append("<BR>The following " + prodType + " " + formType + " has been modified:<P>");
      } 
    } else {
      String typeString = form.getStringValue("IsAdded");
      if (typeString.indexOf("Add") > -1) {
        isNew = true;
        changedFields.append("<BR>The following " + formType + " has been created:<P>");
      } else {
        isNew = false;
        changedFields.append("<BR>The following " + formType + " has been modified:<P>");
      } 
    } 
    Vector formElements = form.getChangedElements();
    boolean skipChgNumber = false;
    if (!isFormChanged(formElements, changedFields, skipChgNumber, isNew, form, messageObject)) {
      context.putSessionValue("emailConfirm", "false");
      return false;
    } 
    changedFields.append("Last Updated Date: " + lastChgDate + "<BR>");
    changedFields.append("Last Updated By: " + lastChgBy + "<P>");
    StringBuffer subject = new StringBuffer();
    String prefix = "";
    String productNo = "";
    String artist = "";
    String subconfig = "";
    String releaseDate = "";
    String title = "";
    String upc = "";
    if (selection.getPrefixID() != null && selection.getPrefixID().getAbbreviation() != null)
      prefix = selection.getPrefixID().getAbbreviation(); 
    if (selection.getSelectionNo() != null)
      productNo = selection.getSelectionNo(); 
    if (selection.getArtist() != null)
      artist = selection.getArtist(); 
    if (selection.getSelectionSubConfig() != null)
      subconfig = selection.getSelectionSubConfig().getSelectionSubConfigurationName(); 
    if (selection.getTitle() != null)
      title = selection.getTitle(); 
    if (selection.getUpc() != null)
      upc = selection.getUpc(); 
    upc = MilestoneHelper_2.getRMSReportFormat(upc, "UPC", selection.getIsDigital());
    if (isNew) {
      subject.append("New " + formType);
    } else {
      subject.append(String.valueOf(formType) + " Change ");
    } 
    if (selection.getIsDigital()) {
      subject.append(" " + upc);
    } else if (!prefix.equals("")) {
      subject.append(" " + prefix + "-" + productNo);
    } else {
      subject.append(" " + productNo);
    } 
    if (selection.getStreetDate() != null)
      releaseDate = (new SimpleDateFormat("MM/dd/yyyy")).format(selection.streetDate.getTime()).toString(); 
    subject.append(" / " + artist);
    subject.append(" / " + title);
    subject.append(" / " + subconfig);
    subject.append(" / " + releaseDate);
    String envName = "";
    if (context.getSessionValue("environment_name") != null) {
      envName = (String)context.getSessionValue("environment_name");
      if (!envName.equals("Production"))
        subject.insert(0, String.valueOf(envName.toUpperCase()) + ": "); 
    } 
    context.putSessionValue("emailSubject", URLDecoder.decode(URLEncoder.encode(subject.toString())));
    messageObject.emailSubject = 
      URLDecoder.decode(URLEncoder.encode(new String(subject)));
    String body = "";
    body = "<B>";
    if (!selection.getIsDigital())
      if (!prefix.equals("")) {
        body = String.valueOf(body) + "Local Product No: " + prefix + "-" + productNo;
      } else {
        body = String.valueOf(body) + "Local Product No: " + productNo;
      }  
    body = String.valueOf(body) + "<P>Artist: " + artist + 
      "<BR>Title: " + selection.getTitle() + 
      "<BR>Release Date: " + releaseDate + 
      
      "<BR>UPC: " + MilestoneHelper_2.getRMSReportFormat(selection.getUpc(), "UPC", selection.getIsDigital());
    if (selection.getIsDigital()) {
      body = String.valueOf(body) + "<BR>Schedule Type: " + selection.getSelectionConfig().getSelectionConfigurationName();
    } else {
      body = String.valueOf(body) + "<BR>Format: " + selection.getSelectionConfig().getSelectionConfigurationName();
    } 
    body = String.valueOf(body) + "<BR>Sub-Format: " + selection.getSelectionSubConfig().getSelectionSubConfigurationName();
    body = String.valueOf(body) + "<BR>Status: " + selection.getSelectionStatus().getName();
    body = String.valueOf(body) + "</b>" + "<BR>" + changedFields.toString();
    if (!envName.equals("Production")) {
      String preBody = "";
      preBody = "<H1 STYLE='font-size: 12pt; color: #ff0000'>***NOTE: THIS EMAIL WAS SENT FROM THE " + 
        
        envName.toUpperCase() + 
        " SERVER NOT FROM PRODUCTION***</H1><BR><BR>";
      body = String.valueOf(preBody) + body;
    } 
    context.putSessionValue("emailBody", body);
    messageObject.emailBody = new String(body);
    String emailTo = EmailDistributionManager.getEmailTo(
        selection.getEnvironment(), 
        formType, 
        selection.getReleaseType().getAbbreviation(), 
        selection.getLabel(), 
        selection.getIsDigital(), 
        selection.getReleaseFamilyId(), 
        selection.getFamily().getStructureID());
    String emailCC = "";
    if (user != null && user.getEmail() != null)
      emailCC = String.valueOf(user.getEmail()) + ";"; 
    context.putSessionValue("emailCC", emailCC);
    messageObject.emailCC = new String(emailCC);
    context.putSessionValue("emailTo", emailTo);
    messageObject.emailTo = new String(emailTo);
    return true;
  }
  
  public static boolean isFormChanged(Vector formElements, StringBuffer changedFields, boolean skipChgNo, boolean isNew, Form form, MessageObject messageObject) {
    boolean changed = false;
    Vector inkChanges = new Vector();
    for (int i = 0; i < formElements.size(); i++) {
      FormElement element = (FormElement)formElements.elementAt(i);
      if (isNew)
        if (!element.getDisplayName().equalsIgnoreCase("printOption"))
          continue;  
      if (skipChgNo)
        if (element.getDisplayName().equalsIgnoreCase("ChangeNumber"))
          continue;  
      if (!element.getDisplayName().equalsIgnoreCase("lastUpdatedBy"))
        if (!element.getDisplayName().equalsIgnoreCase("lastUpdatedDate"))
          if (!element.getDisplayName().equalsIgnoreCase("enteredBy"))
            if (!element.getDisplayName().equalsIgnoreCase("LabelSearch"))
              if (!element.getDisplayName().equalsIgnoreCase("CompanySearch"))
                if (!element.getDisplayName().equalsIgnoreCase("ContactSearch"))
                  if (!element.getDisplayName().equalsIgnoreCase("ConfigSearch"))
                    if (!element.getDisplayName().equalsIgnoreCase("SubconfigSearch"))
                      if (!element.getDisplayName().equalsIgnoreCase("verifiedBy"))
                        if (element.getDisplayName().toUpperCase().indexOf("SEARCH") == -1)
                          if (!element.getDisplayName().equalsIgnoreCase("sendOption"))
                            if (!element.getDisplayName().equalsIgnoreCase("prodType"))
                              if (!element.getDisplayName().equalsIgnoreCase("lastLegacyUpdateDate")) {
                                String startValue = "";
                                if (element.getStartingValue() != null) {
                                  String quoteString = removeQuote(element.getStartingValue());
                                  startValue = removeNbsp(quoteString);
                                } 
                                String newValue = element.getStringValue();
                                newValue = removeNbsp(removeQuote(element.getStringValue()));
                                if (!element.getDisplayName().equalsIgnoreCase("status") || !newValue.equalsIgnoreCase("CLOSED")) {
                                  if (element.getDisplayName().equalsIgnoreCase("releaseDate") && startValue.equalsIgnoreCase("1/1/00"))
                                    startValue = ""; 
                                  if (element.getClass().getName().indexOf("FormCheckBox") >= 0) {
                                    FormCheckBox tempCB = (FormCheckBox)element;
                                    startValue = tempCB.getStartingChecked() ? "Yes" : "No";
                                    newValue = tempCB.isChecked() ? "Yes" : "No";
                                  } 
                                  if (element.getClass().getName().indexOf("FormDropDownMenu") >= 0) {
                                    String[] valueList = ((FormDropDownMenu)element).getValueList();
                                    String[] menuList = ((FormDropDownMenu)element).getMenuTextList();
                                    if (element.getStartingValue() != null)
                                      for (int index = 0; index < valueList.length; index++) {
                                        if (valueList[index].equalsIgnoreCase(startValue))
                                          startValue = menuList[index]; 
                                      }  
                                    for (int index = 0; index < valueList.length; index++) {
                                      if (valueList[index].equalsIgnoreCase(element.getStringValue()))
                                        newValue = menuList[index]; 
                                    } 
                                  } 
                                  newValue = removeNbsp(removeQuote(newValue));
                                  newValue = newValue.trim();
                                  if (!startValue.trim().equalsIgnoreCase(newValue)) {
                                    if (!messageObject.IsPushPFM && IsPFMPushElement(element.getDisplayName()))
                                      messageObject.IsPushPFM = true; 
                                    String displayName = new String();
                                    displayName = element.getId();
                                    if (displayName.length() > 0) {
                                      displayName = displayName.substring(5, displayName.length() - 1);
                                    } else {
                                      displayName = convertFormCodes(element.getDisplayName(), "");
                                      displayName = displayName.replace('_', ' ');
                                    } 
                                    displayName = displayName.trim().toUpperCase();
                                    if (element.getDisplayName().toUpperCase().indexOf("INK") >= 0 && 
                                      element.getDisplayName().toUpperCase().indexOf("SHRINK") == -1) {
                                      boolean isLeft = false;
                                      boolean isNewIC = true;
                                      if (element.getDisplayName().toUpperCase().indexOf("INK1") >= 0)
                                        isLeft = true; 
                                      for (int icIndex = 0; icIndex < inkChanges.size(); icIndex++) {
                                        InkChange currentIC = (InkChange)inkChanges.get(icIndex);
                                        if (currentIC.getId().equalsIgnoreCase(displayName)) {
                                          if (isLeft) {
                                            currentIC.setStartingValueLeft(startValue);
                                            currentIC.setEndValueLeft(newValue);
                                            currentIC.setIsLeftSet(true);
                                          } else {
                                            currentIC.setStartingValueRight(startValue);
                                            currentIC.setEndValueRight(newValue);
                                            currentIC.setIsRightSet(true);
                                          } 
                                          isNewIC = false;
                                        } 
                                      } 
                                      if (isNewIC) {
                                        InkChange ic = new InkChange();
                                        ic.setName(element.getDisplayName());
                                        ic.setId(displayName);
                                        if (isLeft) {
                                          ic.setStartingValueLeft(startValue);
                                          ic.setEndValueLeft(newValue);
                                          ic.setIsLeftSet(true);
                                        } else {
                                          ic.setStartingValueRight(startValue);
                                          ic.setEndValueRight(newValue);
                                          ic.setIsRightSet(true);
                                        } 
                                        inkChanges.add(ic);
                                      } 
                                      changed = true;
                                    } else {
                                      if (!element.getDisplayName().equalsIgnoreCase("SID12") || !element.getDisplayName().equalsIgnoreCase("SID23"))
                                        changedFields.append("<font color='#0000FF'><B><u>" + displayName + 
                                            "</u></B>:</font><BR>"); 
                                      if (element.getDisplayName().indexOf("lastUpdated") < 0) {
                                        if (element.getClass().getName().indexOf("FormCheckBox") >= 0) {
                                          String start = convertCheckBoxOld((FormCheckBox)element);
                                          String to = convertCheckBoxNew((FormCheckBox)element);
                                          start = start.equalsIgnoreCase("on") ? "Yes" : "No";
                                          to = to.equalsIgnoreCase("on") ? "Yes" : "No";
                                          changedFields.append("&nbsp;&nbsp;From:&nbsp;");
                                          changedFields.append(String.valueOf(start) + "<BR>");
                                          changedFields.append(
                                              "&nbsp;&nbsp;<font color='#000000'><b>To:&nbsp;</b></font>");
                                          changedFields.append("<b>" + to + "</b><BR>");
                                        } else if (!element.getDisplayName().equalsIgnoreCase("SID12") || !element.getDisplayName().equalsIgnoreCase("SID23")) {
                                          if (element.getDisplayName().equalsIgnoreCase("Mode") && 
                                            element.getStringValue().equalsIgnoreCase("Modify"))
                                            newValue = "Change"; 
                                          changedFields.append("&nbsp;&nbsp;From:&nbsp;");
                                          changedFields.append(String.valueOf(startValue) + "<BR>");
                                          changedFields.append(
                                              "&nbsp;&nbsp;<font color='#000000'><b>To:</b></font>&nbsp;<b>");
                                          changedFields.append(String.valueOf(newValue) + "</b><BR>");
                                        } 
                                        changedFields.append("<P>");
                                        changed = true;
                                        if (element.getDisplayName().equalsIgnoreCase("REPERTOIRE_OWNER")) {
                                          String subDescBefore = "";
                                          String subDescAfter = "";
                                          Vector repVector = Cache.getRepertoireClasses();
                                          for (int repCount = 0; repCount < repVector.size(); repCount++) {
                                            LookupObject lookupObject = (LookupObject)repVector.elementAt(repCount);
                                            int positOpen = lookupObject.getName().indexOf("(");
                                            int positClose = lookupObject.getName().indexOf(")");
                                            String lookupName = "";
                                            if (positOpen != -1 && positClose != -1) {
                                              lookupName = lookupObject.getName().substring(0, lookupObject.getName().length() - positClose - positOpen + 1).trim();
                                              int positStart = startValue.indexOf(":");
                                              int positNew = newValue.indexOf(":");
                                              if (positStart != -1 && positNew != -1) {
                                                String adjustedStartValue = "";
                                                String adjustedNewValue = "";
                                                adjustedStartValue = startValue.substring(positStart + 1, startValue.length());
                                                adjustedNewValue = newValue.substring(positNew + 1, newValue.length());
                                                if (lookupName.equals(adjustedStartValue))
                                                  subDescBefore = lookupObject.getSubValue(); 
                                                if (lookupName.equals(adjustedNewValue))
                                                  subDescAfter = lookupObject.getSubValue(); 
                                              } 
                                            } 
                                          } 
                                          changedFields.append("<font color='#0000FF'><B><u>REPERTOIRE CLASS</u></B>:</font><BR>");
                                          changedFields.append("&nbsp;&nbsp;From:&nbsp;");
                                          changedFields.append(String.valueOf(startValue) + " (" + subDescBefore + ")<BR>");
                                          changedFields.append("&nbsp;&nbsp;<font color='#000000'><b>To:&nbsp;</b></font>");
                                          changedFields.append("<b>" + newValue + " (" + subDescAfter + ")</b><BR>");
                                          changedFields.append("<P>");
                                        } 
                                      } 
                                    } 
                                  } 
                                } 
                              }             
      continue;
    } 
    for (int x = 0; x < inkChanges.size(); x++) {
      InkChange currentInk = (InkChange)inkChanges.elementAt(x);
      if (!currentInk.getIsLeftSet()) {
        String leftInk = currentInk.getName();
        leftInk = "INK1" + leftInk.substring(4, leftInk.length());
        FormElement inkElement = form.getElement(leftInk);
        String inkDName = "";
        if (inkElement.getId().length() > 0)
          inkDName = inkElement.getId().substring(5, inkElement.getId().length() - 1); 
        currentInk.setStartingValueLeft(inkElement.getStartingValue());
        currentInk.setEndValueLeft(inkElement.getStringValue());
      } 
      if (!currentInk.getIsRightSet()) {
        String rightInk = currentInk.getName();
        rightInk = "INK2" + rightInk.substring(4, rightInk.length());
        FormElement inkElement = form.getElement(rightInk);
        String inkDName = "";
        if (inkElement.getId().length() > 0)
          inkDName = inkElement.getId().substring(5, inkElement.getId().length() - 1); 
        currentInk.setStartingValueRight(inkElement.getStartingValue());
        currentInk.setEndValueRight(inkElement.getStringValue());
        changedFields.append("<font color='#0000FF'><B><u>" + currentInk.getId() + 
            "</u></B>:</font><BR>");
        changedFields.append("&nbsp;&nbsp;From:&nbsp;");
        changedFields.append(String.valueOf(currentInk.getStartingValueLeft()) + "/" + 
            currentInk.getStartingValueRight() + "<BR>");
        changedFields.append("&nbsp;&nbsp;<b>To:&nbsp;");
        changedFields.append(String.valueOf(currentInk.getEndValueLeft()) + "/" + 
            currentInk.getEndValueRight());
        changedFields.append("</b><P>");
      } 
    } 
    return changed;
  }
  
  public static boolean IsPFMPushElement(String elementName) {
    if (PushPFM.getPushElements().containsKey(elementName))
      return true; 
    return false;
  }
  
  private static void ListFormElements(Form form) {
    for (Iterator i = form.getElements(); i.hasNext();)
      System.err.println("<element>" + ((FormElement)i.next()).getDisplayName() + "</element>"); 
  }
  
  private static String convertCheckBoxOld(FormCheckBox ckBox) {
    if (ckBox.isChecked())
      return "off"; 
    return "on";
  }
  
  private static String convertCheckBoxNew(FormCheckBox ckBox) {
    if (ckBox.isChecked())
      return "on"; 
    return "off";
  }
  
  private static String removeQuote(String str) {
    int s = 0;
    int e = 0;
    StringBuffer result = new StringBuffer();
    while ((e = str.indexOf("&quot;", s)) >= 0) {
      result.append(str.substring(s, e));
      result.append("\"");
      s = e + "&quot;".length();
    } 
    result.append(str.substring(s));
    return result.toString();
  }
  
  private static String removeNbsp(String str) {
    int s = 0;
    int e = 0;
    StringBuffer result = new StringBuffer();
    while ((e = str.indexOf("&nbsp;", s)) >= 0) {
      result.append(str.substring(s, e));
      s = e + "&nbsp;".length();
    } 
    result.append(str.substring(s));
    return result.toString();
  }
  
  public static void putEmailAttach(Context context, String fileName, MessageObject messageObject) {
    context.putSessionValue("emailAttach", fileName);
    if (messageObject != null)
      messageObject.emailAttach = new String(fileName); 
  }
  
  public static void removeSessionValues(Context context) {
    context.removeSessionValue("emailBody");
    context.removeSessionValue("emailSubject");
    context.removeSessionValue("emailTo");
    context.removeSessionValue("emailCC");
    context.removeSessionValue("emailAttach");
    context.removeSessionValue("emailConfirm");
  }
  
  public static void resetConfirmValue(Context context) { context.putSessionValue("emailConfirm", "false"); }
  
  public static boolean sendEmail(Dispatcher dispatcher, Context context, String command, MessageObject messageObject) {
    String emailCC = messageObject.emailCC;
    String emailBody = messageObject.emailBody;
    String emailSubject = messageObject.emailSubject;
    String emailTo = messageObject.emailTo;
    String emailFrom = messageObject.emailFrom;
    String emailAttach = messageObject.emailAttach;
    if (emailTo == null || (emailTo != null && emailTo.equals(""))) {
      context.putDelivery("AlertMessage", "Email Notification Send Failed..<BR>(Missing Send To Address)..<BR>Please notfiy Milestone Support Staff");
      context.putDelivery("SendMailStatus", "0");
      return true;
    } 
    if (EmailDistributionManager.sendEmailDistribution(emailSubject, emailBody, emailTo, emailCC, emailAttach)) {
      context.putDelivery("AlertMessage", "Email Notfication Sent Sucessfully.....<BR> Sent To: " + addWrapToEmailToStr(emailTo));
      context.putDelivery("SendMailStatus", "1");
    } else {
      context.putDelivery("AlertMessage", "EMAIL NOTIFICATION SEND FAILED! <BR>Please notfiy Milestone Support Staff.<BR>.....Failed To Send To: " + addWrapToEmailToStr(emailTo));
      context.putDelivery("SendMailStatus", "0");
    } 
    if (!command.equals(""))
      dispatcher.redispatch(context, command); 
    return true;
  }
  
  public static void setStaticValues() {
    synchronized (lockObject) {
      try {
        String filename = "milestone.conf";
        InputStream in = ClassLoader.getSystemResourceAsStream(filename);
        if (in == null)
          in = new FileInputStream(filename); 
        Properties defaultProps = new Properties();
        defaultProps.load(in);
        emailOutDir = defaultProps.getProperty("EmailOutDir");
        emailAttachDir = defaultProps.getProperty("EmailAttachDir");
        in.close();
      } catch (Exception ex) {
        System.out.println("<<< EmailDistribution Exception " + ex.getMessage());
      } 
    } 
  }
  
  public static String getEmailOutDir() {
    if (emailOutDir == null)
      setStaticValues(); 
    return emailOutDir;
  }
  
  public static String getEmailAttachDir() {
    if (emailAttachDir == null)
      setStaticValues(); 
    return emailAttachDir;
  }
  
  public static String getAttachFileSuffix(Context context, String formType, String productNo, String prefix, String upc, boolean isDigital) {
    String fileSuffix;
    User user = (User)context.getSessionValue("user");
    if (user != null) {
      if (isDigital) {
        fileSuffix = "\\" + formType + "_" + upc + "_" + user.getUserId() + 
          "_" + Calendar.getInstance().getTime().getTime() + ".pdf";
      } else {
        fileSuffix = "\\" + formType + "_" + prefix + " " + productNo + "_" + user.getUserId() + 
          "_" + Calendar.getInstance().getTime().getTime() + ".pdf";
      } 
    } else if (isDigital) {
      fileSuffix = "\\" + formType + "_" + upc + "_" + Calendar.getInstance().getTime().getTime() + ".pdf";
    } else {
      fileSuffix = "\\" + formType + "_" + prefix + " " + productNo + "_" + Calendar.getInstance().getTime().getTime() + ".pdf";
    } 
    return fileSuffix;
  }
  
  public static void generateFormReport(Context context, String formType, XStyleSheet report, String productNo, String prefix, String upc, boolean isDigital, MessageObject messageObject) {
    String attachFileName = new String();
    String outFileName = new String();
    User user = (User)context.getSessionValue("user");
    String fileSuffix = getAttachFileSuffix(context, formType, productNo, prefix, upc, isDigital);
    outFileName = String.valueOf(getEmailOutDir()) + fileSuffix;
    attachFileName = String.valueOf(getEmailAttachDir()) + fileSuffix;
    try {
      FileOutputStream fileOutstream = new FileOutputStream(outFileName);
      PDF4Generator pdfGenerator = new PDF4Generator(fileOutstream);
      pdfGenerator.generate(report);
      fileOutstream.close();
    } catch (Exception e) {
      attachFileName = "** Unable to create document, contact IT support staff ** \n outDoc: " + 
        outFileName + 
        "\n attachDoc: " + attachFileName + 
        "\n exception: " + e.getMessage();
    } 
    putEmailAttach(context, attachFileName, messageObject);
  }
  
  public static String convertFormCodes(String code, String codeNo) {
    if (code.equalsIgnoreCase("PID7") || codeNo.equals("7"))
      return "Disc"; 
    if (code.equalsIgnoreCase("PID12") || codeNo.equals("12"))
      return "Jewel Box"; 
    if (code.equalsIgnoreCase("PID23") || codeNo.equals("23"))
      return "Tray"; 
    if (code.equalsIgnoreCase("PID10") || codeNo.equals("10"))
      return "Inlay"; 
    if (code.equalsIgnoreCase("PID9") || codeNo.equals("9"))
      return "Front Insert"; 
    if (code.equalsIgnoreCase("PID8") || codeNo.equals("8"))
      return "Folder"; 
    if (code.equalsIgnoreCase("PID1") || codeNo.equals("1"))
      return "Booklet"; 
    if (code.equalsIgnoreCase("PID4") || codeNo.equals("4"))
      return "BRC Insert"; 
    if (code.equalsIgnoreCase("PID15") || codeNo.equals("15"))
      return "Mini Jacket"; 
    if (code.equalsIgnoreCase("PID6") || codeNo.equals("6"))
      return "DigiPak"; 
    if (code.equalsIgnoreCase("PID21") || codeNo.equals("21"))
      return "Sticker1"; 
    if (code.equalsIgnoreCase("PID22") || codeNo.equals("22"))
      return "Sticker 2"; 
    if (code.equalsIgnoreCase("PID2") || codeNo.equals("2"))
      return "Book (Other/Set)"; 
    if (code.equalsIgnoreCase("PID3") || codeNo.equals("3"))
      return "Box (Set)"; 
    if (code.equalsIgnoreCase("PID18") || codeNo.equals("18"))
      return "Other"; 
    if (code.equalsIgnoreCase("PID5") || codeNo.equals("5"))
      return "C-0"; 
    if (code.equalsIgnoreCase("PID16") || codeNo.equals("16"))
      return "Norelco"; 
    if (code.equalsIgnoreCase("PID13") || codeNo.equals("5"))
      return "J-Card"; 
    if (code.equalsIgnoreCase("PID24") || codeNo.equals("24"))
      return "U-Card"; 
    if (code.equalsIgnoreCase("PID17"))
      return "O-Card"; 
    if (code.equalsIgnoreCase("PID5") || codeNo.equals("5"))
      return "C-0"; 
    if (code.equalsIgnoreCase("PID19") || codeNo.equals("19"))
      return "Record"; 
    if (code.equalsIgnoreCase("PID14") || codeNo.equals("14"))
      return "Label"; 
    if (code.equalsIgnoreCase("PID20") || codeNo.equals("20"))
      return "Sleeve"; 
    if (code.equalsIgnoreCase("PID11") || codeNo.equals("11"))
      return "Jacket"; 
    if (code.indexOf("guarantee_code") >= 0)
      return "IMI Exempt"; 
    if (code.indexOf("SID") >= 0)
      return String.valueOf(convertFormCodes("", code.substring(3))) + " / Supplier "; 
    if (code.indexOf("INK") >= 0)
      return String.valueOf(convertFormCodes("", code.substring(3))) + " / Ink "; 
    if (code.indexOf("INF") >= 0)
      return String.valueOf(convertFormCodes("", code.substring(3))) + " / Additional Information "; 
    if (code.indexOf("SEL") >= 0)
      return String.valueOf(convertFormCodes("", code.substring(3))) + " / Additional Information "; 
    return code;
  }
  
  public String getNotepadContentObjectId() { return Integer.toString(this.distributionId); }
  
  public Object clone() throws CloneNotSupportedException { return super.clone(); }
  
  public Hashtable getReleasingFamily() { return this.releasingFamilyHash; }
  
  public void setReleasingFamily(Hashtable releasingFamilyHash) { this.releasingFamilyHash = releasingFamilyHash; }
  
  public void setAssignedEnvironments(Vector assignedEnvironments) { this.assignedEnvironments = assignedEnvironments; }
  
  public Vector getAssignedEnvironments() { return this.assignedEnvironments; }
  
  public static String addWrapToEmailToStr(String emailTo) {
    StringBuffer emailToBuf = new StringBuffer(emailTo);
    return emailToBuf.toString().replaceAll(";", ";<br>");
  }
}


/* Location:              D:\Documents\NetBeansProjects\milestone2 Local.war!\WEB-INF\classes\co\\universal\milestone\EmailDistribution.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */