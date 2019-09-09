/*      */ package WEB-INF.classes.com.universal.milestone;
/*      */ 
/*      */ import com.techempower.DataEntity;
/*      */ import com.techempower.gemini.Context;
/*      */ import com.techempower.gemini.Dispatcher;
/*      */ import com.techempower.gemini.FormCheckBox;
/*      */ import com.techempower.gemini.FormDropDownMenu;
/*      */ import com.techempower.gemini.FormElement;
/*      */ import com.universal.milestone.Cache;
/*      */ import com.universal.milestone.EmailDistribution;
/*      */ import com.universal.milestone.EmailDistributionManager;
/*      */ import com.universal.milestone.Form;
/*      */ import com.universal.milestone.InkChange;
/*      */ import com.universal.milestone.LookupObject;
/*      */ import com.universal.milestone.MilestoneHelper_2;
/*      */ import com.universal.milestone.NotepadContentObject;
/*      */ import com.universal.milestone.Selection;
/*      */ import com.universal.milestone.User;
/*      */ import com.universal.milestone.jms.MessageObject;
/*      */ import com.universal.milestone.push.PushPFM;
/*      */ import inetsoft.report.XStyleSheet;
/*      */ import inetsoft.report.pdf.PDF4Generator;
/*      */ import java.io.FileInputStream;
/*      */ import java.io.FileOutputStream;
/*      */ import java.io.InputStream;
/*      */ import java.net.URLDecoder;
/*      */ import java.net.URLEncoder;
/*      */ import java.text.SimpleDateFormat;
/*      */ import java.util.Calendar;
/*      */ import java.util.Hashtable;
/*      */ import java.util.Iterator;
/*      */ import java.util.Properties;
/*      */ import java.util.Vector;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class EmailDistribution
/*      */   extends DataEntity
/*      */   implements NotepadContentObject, Cloneable
/*      */ {
/*   59 */   protected int distributionId = -1;
/*      */   
/*   61 */   protected int copyDistributionId = -1;
/*   62 */   protected String firstName = null;
/*   63 */   protected String lastName = null;
/*   64 */   protected String email = null;
/*      */   protected boolean pfm = false;
/*      */   protected boolean bom = false;
/*      */   protected boolean promo = false;
/*      */   protected boolean commercial = false;
/*      */   protected Calendar lastUpdatedOn;
/*      */   protected int lastUpdatedBy;
/*   71 */   protected long lastUpdatedCk = -1L;
/*      */   protected boolean inactive = false;
/*   73 */   protected Vector emailDistributionDetail = null;
/*   74 */   protected int labelDistribution = 0;
/*      */   
/*   76 */   protected int productType = 1;
/*      */ 
/*      */   
/*   79 */   public static String emailOutDir = null;
/*   80 */   public static String emailAttachDir = null;
/*   81 */   protected static Object lockObject = new Object();
/*      */   
/*      */   public static final int DIGITAL = 0;
/*      */   
/*      */   public static final int PHYSICAL = 1;
/*      */   
/*      */   public static final int BOTH = 2;
/*      */   
/*   89 */   protected Hashtable releasingFamilyHash = null;
/*      */   
/*   91 */   protected Vector assignedEnvironments = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public EmailDistribution() {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  108 */   public EmailDistribution(int distribution_id) { this.distributionId = distribution_id; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  118 */   public int getDistributionId() { return this.distributionId; }
/*      */ 
/*      */ 
/*      */   
/*  122 */   public void setDistributionId(int distributionId) { this.distributionId = distributionId; }
/*      */ 
/*      */ 
/*      */   
/*  126 */   public int getCopyDistributionId() { return this.copyDistributionId; }
/*      */ 
/*      */ 
/*      */   
/*  130 */   public void setCopyDistributionId(int copyDistributionId) { this.copyDistributionId = copyDistributionId; }
/*      */ 
/*      */ 
/*      */   
/*  134 */   public String getFirstName() { return this.firstName; }
/*      */ 
/*      */ 
/*      */   
/*  138 */   public void setFirstName(String firstName) { this.firstName = firstName; }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  143 */   public String getLastName() { return this.lastName; }
/*      */ 
/*      */ 
/*      */   
/*  147 */   public void setLastName(String lastName) { this.lastName = lastName; }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  152 */   public String getEmail() { return this.email; }
/*      */ 
/*      */ 
/*      */   
/*  156 */   public void setEmail(String email) { this.email = email; }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  161 */   public Calendar getLastUpdateOn() { return this.lastUpdatedOn; }
/*      */ 
/*      */ 
/*      */   
/*  165 */   public void setLastUpdatedOn(Calendar lastUpdatedOn) { this.lastUpdatedOn = lastUpdatedOn; }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  170 */   public int getLastUpdateBy() { return this.lastUpdatedBy; }
/*      */ 
/*      */ 
/*      */   
/*  174 */   public void setLastUpdatedBy(int lastUpdatedBy) { this.lastUpdatedBy = lastUpdatedBy; }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  179 */   public long getLastUpdatedCk() { return this.lastUpdatedCk; }
/*      */ 
/*      */ 
/*      */   
/*  183 */   public void setLastUpdatedCk(long lastUpdatedCk) { this.lastUpdatedCk = lastUpdatedCk; }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  188 */   public boolean getInactive() { return this.inactive; }
/*      */ 
/*      */ 
/*      */   
/*  192 */   public void setInactive(boolean inactive) { this.inactive = inactive; }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  197 */   public boolean getPfm() { return this.pfm; }
/*      */ 
/*      */ 
/*      */   
/*  201 */   public void setPfm(boolean pfm) { this.pfm = pfm; }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  206 */   public boolean getBom() { return this.bom; }
/*      */ 
/*      */ 
/*      */   
/*  210 */   public void setBom(boolean bom) { this.bom = bom; }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  215 */   public boolean getPromo() { return this.promo; }
/*      */ 
/*      */ 
/*      */   
/*  219 */   public void setPromo(boolean promo) { this.promo = promo; }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  224 */   public boolean getCommercial() { return this.commercial; }
/*      */ 
/*      */ 
/*      */   
/*  228 */   public void setCommercial(boolean commercial) { this.commercial = commercial; }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  233 */   public Vector getEmailDistributionDetail() { return this.emailDistributionDetail; }
/*      */ 
/*      */ 
/*      */   
/*  237 */   public void setEmailDistributionDetail(Vector emailDistributionDetail) { this.emailDistributionDetail = emailDistributionDetail; }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  242 */   public int getLabelDistribution() { return this.labelDistribution; }
/*      */ 
/*      */ 
/*      */   
/*  246 */   public void setLabelDistribution(int labelDistribution) { this.labelDistribution = labelDistribution; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  254 */   public int getProductType() { return this.productType; }
/*      */ 
/*      */ 
/*      */   
/*  258 */   public void setProductType(int productType) { this.productType = productType; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean putEmailBody(Form form, Context context, Selection selection, String lastChgDate, String lastChgBy, String formType, MessageObject messageObject) {
/*  280 */     if (messageObject != null && messageObject.selectionObj != null) {
/*  281 */       selection = messageObject.selectionObj;
/*      */     }
/*      */     
/*  284 */     User user = (User)context.getSessionValue("user");
/*      */     
/*  286 */     StringBuffer changedFields = new StringBuffer();
/*      */ 
/*      */     
/*  289 */     boolean isNew = false;
/*  290 */     String changeNumber = form.getStringValue("ChangeNumber");
/*  291 */     if (formType.equalsIgnoreCase("PFM")) {
/*      */       
/*  293 */       String mode = form.getStringValue("mode");
/*  294 */       String prodType = selection.getIsDigital() ? "Digital" : "Physical";
/*  295 */       if (mode.equalsIgnoreCase("Add"))
/*      */       {
/*  297 */         isNew = true;
/*  298 */         changedFields.append("<BR>The following " + prodType + " " + formType + " has been created:<P>");
/*      */       }
/*      */       else
/*      */       {
/*  302 */         isNew = false;
/*  303 */         changedFields.append("<BR>The following " + prodType + " " + formType + " has been modified:<P>");
/*      */       }
/*      */     
/*      */     } else {
/*      */       
/*  308 */       String typeString = form.getStringValue("IsAdded");
/*  309 */       if (typeString.indexOf("Add") > -1) {
/*      */         
/*  311 */         isNew = true;
/*  312 */         changedFields.append("<BR>The following " + formType + " has been created:<P>");
/*      */       }
/*      */       else {
/*      */         
/*  316 */         isNew = false;
/*  317 */         changedFields.append("<BR>The following " + formType + " has been modified:<P>");
/*      */       } 
/*      */     } 
/*      */     
/*  321 */     Vector formElements = form.getChangedElements();
/*      */     
/*  323 */     boolean skipChgNumber = false;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  329 */     if (!isFormChanged(formElements, changedFields, skipChgNumber, isNew, form, messageObject)) {
/*      */ 
/*      */       
/*  332 */       context.putSessionValue("emailConfirm", "false");
/*  333 */       return false;
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  340 */     changedFields.append("Last Updated Date: " + lastChgDate + "<BR>");
/*  341 */     changedFields.append("Last Updated By: " + lastChgBy + "<P>");
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  346 */     StringBuffer subject = new StringBuffer();
/*      */     
/*  348 */     String prefix = "";
/*  349 */     String productNo = "";
/*  350 */     String artist = "";
/*  351 */     String subconfig = "";
/*  352 */     String releaseDate = "";
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  357 */     String title = "";
/*  358 */     String upc = "";
/*      */     
/*  360 */     if (selection.getPrefixID() != null && selection.getPrefixID().getAbbreviation() != null)
/*  361 */       prefix = selection.getPrefixID().getAbbreviation(); 
/*  362 */     if (selection.getSelectionNo() != null)
/*  363 */       productNo = selection.getSelectionNo(); 
/*  364 */     if (selection.getArtist() != null) {
/*  365 */       artist = selection.getArtist();
/*      */     }
/*  367 */     if (selection.getSelectionSubConfig() != null) {
/*  368 */       subconfig = selection.getSelectionSubConfig().getSelectionSubConfigurationName();
/*      */     }
/*  370 */     if (selection.getTitle() != null) {
/*  371 */       title = selection.getTitle();
/*      */     }
/*  373 */     if (selection.getUpc() != null) {
/*  374 */       upc = selection.getUpc();
/*      */     }
/*      */     
/*  377 */     upc = MilestoneHelper_2.getRMSReportFormat(upc, "UPC", selection.getIsDigital());
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  383 */     if (isNew) {
/*  384 */       subject.append("New " + formType);
/*      */     } else {
/*  386 */       subject.append(String.valueOf(formType) + " Change ");
/*      */     } 
/*  388 */     if (selection.getIsDigital()) {
/*  389 */       subject.append(" " + upc);
/*      */     
/*      */     }
/*  392 */     else if (!prefix.equals("")) {
/*  393 */       subject.append(" " + prefix + "-" + productNo);
/*      */     } else {
/*  395 */       subject.append(" " + productNo);
/*      */     } 
/*      */     
/*  398 */     if (selection.getStreetDate() != null) {
/*  399 */       releaseDate = (new SimpleDateFormat("MM/dd/yyyy")).format(selection.streetDate.getTime()).toString();
/*      */     }
/*      */ 
/*      */     
/*  403 */     subject.append(" / " + artist);
/*  404 */     subject.append(" / " + title);
/*  405 */     subject.append(" / " + subconfig);
/*  406 */     subject.append(" / " + releaseDate);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  413 */     String envName = "";
/*  414 */     if (context.getSessionValue("environment_name") != null) {
/*  415 */       envName = (String)context.getSessionValue("environment_name");
/*  416 */       if (!envName.equals("Production")) {
/*  417 */         subject.insert(0, String.valueOf(envName.toUpperCase()) + ": ");
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/*  422 */     context.putSessionValue("emailSubject", URLDecoder.decode(URLEncoder.encode(subject.toString())));
/*      */     
/*  424 */     messageObject.emailSubject = 
/*  425 */       URLDecoder.decode(URLEncoder.encode(new String(subject)));
/*      */ 
/*      */     
/*  428 */     String body = "";
/*      */ 
/*      */     
/*  431 */     body = "<B>";
/*  432 */     if (!selection.getIsDigital())
/*      */     {
/*  434 */       if (!prefix.equals("")) {
/*  435 */         body = String.valueOf(body) + "Local Product No: " + prefix + "-" + productNo;
/*      */       } else {
/*  437 */         body = String.valueOf(body) + "Local Product No: " + productNo;
/*      */       } 
/*      */     }
/*  440 */     body = String.valueOf(body) + "<P>Artist: " + artist + 
/*  441 */       "<BR>Title: " + selection.getTitle() + 
/*  442 */       "<BR>Release Date: " + releaseDate + 
/*      */       
/*  444 */       "<BR>UPC: " + MilestoneHelper_2.getRMSReportFormat(selection.getUpc(), "UPC", selection.getIsDigital());
/*      */ 
/*      */     
/*  447 */     if (selection.getIsDigital()) {
/*  448 */       body = String.valueOf(body) + "<BR>Schedule Type: " + selection.getSelectionConfig().getSelectionConfigurationName();
/*      */     } else {
/*  450 */       body = String.valueOf(body) + "<BR>Format: " + selection.getSelectionConfig().getSelectionConfigurationName();
/*      */     } 
/*  452 */     body = String.valueOf(body) + "<BR>Sub-Format: " + selection.getSelectionSubConfig().getSelectionSubConfigurationName();
/*      */     
/*  454 */     body = String.valueOf(body) + "<BR>Status: " + selection.getSelectionStatus().getName();
/*  455 */     body = String.valueOf(body) + "</b>" + "<BR>" + changedFields.toString();
/*      */ 
/*      */ 
/*      */     
/*  459 */     if (!envName.equals("Production")) {
/*  460 */       String preBody = "";
/*  461 */       preBody = "<H1 STYLE='font-size: 12pt; color: #ff0000'>***NOTE: THIS EMAIL WAS SENT FROM THE " + 
/*      */         
/*  463 */         envName.toUpperCase() + 
/*  464 */         " SERVER NOT FROM PRODUCTION***</H1><BR><BR>";
/*  465 */       body = String.valueOf(preBody) + body;
/*      */     } 
/*      */     
/*  468 */     context.putSessionValue("emailBody", body);
/*      */     
/*  470 */     messageObject.emailBody = new String(body);
/*      */ 
/*      */     
/*  473 */     String emailTo = EmailDistributionManager.getEmailTo(
/*  474 */         selection.getEnvironment(), 
/*  475 */         formType, 
/*  476 */         selection.getReleaseType().getAbbreviation(), 
/*  477 */         selection.getLabel(), 
/*  478 */         selection.getIsDigital(), 
/*  479 */         selection.getReleaseFamilyId(), 
/*  480 */         selection.getFamily().getStructureID());
/*      */ 
/*      */     
/*  483 */     String emailCC = "";
/*  484 */     if (user != null && user.getEmail() != null) {
/*  485 */       emailCC = String.valueOf(user.getEmail()) + ";";
/*      */     }
/*  487 */     context.putSessionValue("emailCC", emailCC);
/*  488 */     messageObject.emailCC = new String(emailCC);
/*      */ 
/*      */     
/*  491 */     context.putSessionValue("emailTo", emailTo);
/*  492 */     messageObject.emailTo = new String(emailTo);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  499 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isFormChanged(Vector formElements, StringBuffer changedFields, boolean skipChgNo, boolean isNew, Form form, MessageObject messageObject) {
/*  506 */     boolean changed = false;
/*      */ 
/*      */ 
/*      */     
/*  510 */     Vector inkChanges = new Vector();
/*  511 */     for (int i = 0; i < formElements.size(); i++) {
/*      */       
/*  513 */       FormElement element = (FormElement)formElements.elementAt(i);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  520 */       if (isNew)
/*      */       {
/*  522 */         if (!element.getDisplayName().equalsIgnoreCase("printOption")) {
/*      */           continue;
/*      */         }
/*      */       }
/*      */ 
/*      */       
/*  528 */       if (skipChgNo)
/*      */       {
/*  530 */         if (element.getDisplayName().equalsIgnoreCase("ChangeNumber")) {
/*      */           continue;
/*      */         }
/*      */       }
/*  534 */       if (!element.getDisplayName().equalsIgnoreCase("lastUpdatedBy"))
/*      */       {
/*  536 */         if (!element.getDisplayName().equalsIgnoreCase("lastUpdatedDate"))
/*      */         {
/*  538 */           if (!element.getDisplayName().equalsIgnoreCase("enteredBy"))
/*      */           {
/*  540 */             if (!element.getDisplayName().equalsIgnoreCase("LabelSearch"))
/*      */             {
/*  542 */               if (!element.getDisplayName().equalsIgnoreCase("CompanySearch"))
/*      */               {
/*  544 */                 if (!element.getDisplayName().equalsIgnoreCase("ContactSearch"))
/*      */                 {
/*  546 */                   if (!element.getDisplayName().equalsIgnoreCase("ConfigSearch"))
/*      */                   {
/*  548 */                     if (!element.getDisplayName().equalsIgnoreCase("SubconfigSearch"))
/*      */                     {
/*  550 */                       if (!element.getDisplayName().equalsIgnoreCase("verifiedBy"))
/*      */                       {
/*      */                         
/*  553 */                         if (element.getDisplayName().toUpperCase().indexOf("SEARCH") == -1)
/*      */                         {
/*  555 */                           if (!element.getDisplayName().equalsIgnoreCase("sendOption"))
/*      */                           {
/*      */                             
/*  558 */                             if (!element.getDisplayName().equalsIgnoreCase("prodType"))
/*      */                             {
/*      */                               
/*  561 */                               if (!element.getDisplayName().equalsIgnoreCase("lastLegacyUpdateDate")) {
/*      */ 
/*      */ 
/*      */ 
/*      */                                 
/*  566 */                                 String startValue = "";
/*      */ 
/*      */                                 
/*  569 */                                 if (element.getStartingValue() != null) {
/*      */                                   
/*  571 */                                   String quoteString = removeQuote(element.getStartingValue());
/*  572 */                                   startValue = removeNbsp(quoteString);
/*      */                                 } 
/*      */                                 
/*  575 */                                 String newValue = element.getStringValue();
/*      */                                 
/*  577 */                                 newValue = removeNbsp(removeQuote(element.getStringValue()));
/*      */ 
/*      */                                 
/*  580 */                                 if (!element.getDisplayName().equalsIgnoreCase("status") || !newValue.equalsIgnoreCase("CLOSED")) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                                   
/*  588 */                                   if (element.getDisplayName().equalsIgnoreCase("releaseDate") && startValue.equalsIgnoreCase("1/1/00")) {
/*  589 */                                     startValue = "";
/*      */                                   }
/*      */                                   
/*  592 */                                   if (element.getClass().getName().indexOf("FormCheckBox") >= 0) {
/*      */                                     
/*  594 */                                     FormCheckBox tempCB = (FormCheckBox)element;
/*  595 */                                     startValue = tempCB.getStartingChecked() ? "Yes" : "No";
/*  596 */                                     newValue = tempCB.isChecked() ? "Yes" : "No";
/*      */                                   } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                                   
/*  606 */                                   if (element.getClass().getName().indexOf("FormDropDownMenu") >= 0) {
/*      */                                     
/*  608 */                                     String[] valueList = ((FormDropDownMenu)element).getValueList();
/*  609 */                                     String[] menuList = ((FormDropDownMenu)element).getMenuTextList();
/*      */                                     
/*  611 */                                     if (element.getStartingValue() != null)
/*      */                                     {
/*  613 */                                       for (int index = 0; index < valueList.length; index++) {
/*      */                                         
/*  615 */                                         if (valueList[index].equalsIgnoreCase(startValue)) {
/*  616 */                                           startValue = menuList[index];
/*      */                                         }
/*      */                                       } 
/*      */                                     }
/*  620 */                                     for (int index = 0; index < valueList.length; index++) {
/*      */                                       
/*  622 */                                       if (valueList[index].equalsIgnoreCase(element.getStringValue())) {
/*  623 */                                         newValue = menuList[index];
/*      */                                       }
/*      */                                     } 
/*      */                                   } 
/*      */ 
/*      */ 
/*      */ 
/*      */                                   
/*  631 */                                   newValue = removeNbsp(removeQuote(newValue));
/*  632 */                                   newValue = newValue.trim();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                                   
/*  639 */                                   if (!startValue.trim().equalsIgnoreCase(newValue)) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                                     
/*  646 */                                     if (!messageObject.IsPushPFM && IsPFMPushElement(element.getDisplayName())) {
/*  647 */                                       messageObject.IsPushPFM = true;
/*      */                                     }
/*  649 */                                     String displayName = new String();
/*      */                                     
/*  651 */                                     displayName = element.getId();
/*  652 */                                     if (displayName.length() > 0) {
/*      */                                       
/*  654 */                                       displayName = displayName.substring(5, displayName.length() - 1);
/*      */                                     }
/*      */                                     else {
/*      */                                       
/*  658 */                                       displayName = convertFormCodes(element.getDisplayName(), "");
/*  659 */                                       displayName = displayName.replace('_', ' ');
/*      */                                     } 
/*  661 */                                     displayName = displayName.trim().toUpperCase();
/*      */ 
/*      */ 
/*      */ 
/*      */                                     
/*  666 */                                     if (element.getDisplayName().toUpperCase().indexOf("INK") >= 0 && 
/*  667 */                                       element.getDisplayName().toUpperCase().indexOf("SHRINK") == -1) {
/*      */ 
/*      */ 
/*      */                                       
/*  671 */                                       boolean isLeft = false;
/*  672 */                                       boolean isNewIC = true;
/*      */                                       
/*  674 */                                       if (element.getDisplayName().toUpperCase().indexOf("INK1") >= 0) {
/*  675 */                                         isLeft = true;
/*      */                                       }
/*      */ 
/*      */ 
/*      */ 
/*      */                                       
/*  681 */                                       for (int icIndex = 0; icIndex < inkChanges.size(); icIndex++) {
/*      */                                         
/*  683 */                                         InkChange currentIC = (InkChange)inkChanges.get(icIndex);
/*      */ 
/*      */ 
/*      */ 
/*      */                                         
/*  688 */                                         if (currentIC.getId().equalsIgnoreCase(displayName)) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                                           
/*  694 */                                           if (isLeft) {
/*      */                                             
/*  696 */                                             currentIC.setStartingValueLeft(startValue);
/*  697 */                                             currentIC.setEndValueLeft(newValue);
/*  698 */                                             currentIC.setIsLeftSet(true);
/*      */                                           }
/*      */                                           else {
/*      */                                             
/*  702 */                                             currentIC.setStartingValueRight(startValue);
/*  703 */                                             currentIC.setEndValueRight(newValue);
/*  704 */                                             currentIC.setIsRightSet(true);
/*      */                                           } 
/*  706 */                                           isNewIC = false;
/*      */                                         } 
/*      */                                       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                                       
/*  715 */                                       if (isNewIC) {
/*      */                                         
/*  717 */                                         InkChange ic = new InkChange();
/*  718 */                                         ic.setName(element.getDisplayName());
/*  719 */                                         ic.setId(displayName);
/*  720 */                                         if (isLeft) {
/*      */                                           
/*  722 */                                           ic.setStartingValueLeft(startValue);
/*  723 */                                           ic.setEndValueLeft(newValue);
/*  724 */                                           ic.setIsLeftSet(true);
/*      */                                         }
/*      */                                         else {
/*      */                                           
/*  728 */                                           ic.setStartingValueRight(startValue);
/*  729 */                                           ic.setEndValueRight(newValue);
/*  730 */                                           ic.setIsRightSet(true);
/*      */                                         } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                                         
/*  742 */                                         inkChanges.add(ic);
/*      */                                       } 
/*  744 */                                       changed = true;
/*      */                                     
/*      */                                     }
/*      */                                     else {
/*      */                                       
/*  749 */                                       if (!element.getDisplayName().equalsIgnoreCase("SID12") || !element.getDisplayName().equalsIgnoreCase("SID23")) {
/*  750 */                                         changedFields.append("<font color='#0000FF'><B><u>" + displayName + 
/*  751 */                                             "</u></B>:</font><BR>");
/*      */                                       }
/*      */                                       
/*  754 */                                       if (element.getDisplayName().indexOf("lastUpdated") < 0) {
/*      */                                         
/*  756 */                                         if (element.getClass().getName().indexOf("FormCheckBox") >= 0) {
/*      */                                           
/*  758 */                                           String start = convertCheckBoxOld((FormCheckBox)element);
/*  759 */                                           String to = convertCheckBoxNew((FormCheckBox)element);
/*      */ 
/*      */ 
/*      */ 
/*      */                                           
/*  764 */                                           start = start.equalsIgnoreCase("on") ? "Yes" : "No";
/*  765 */                                           to = to.equalsIgnoreCase("on") ? "Yes" : "No";
/*      */ 
/*      */                                           
/*  768 */                                           changedFields.append("&nbsp;&nbsp;From:&nbsp;");
/*  769 */                                           changedFields.append(String.valueOf(start) + "<BR>");
/*  770 */                                           changedFields.append(
/*  771 */                                               "&nbsp;&nbsp;<font color='#000000'><b>To:&nbsp;</b></font>");
/*  772 */                                           changedFields.append("<b>" + to + "</b><BR>");
/*      */ 
/*      */ 
/*      */                                         
/*      */                                         }
/*  777 */                                         else if (!element.getDisplayName().equalsIgnoreCase("SID12") || !element.getDisplayName().equalsIgnoreCase("SID23")) {
/*      */                                           
/*  779 */                                           if (element.getDisplayName().equalsIgnoreCase("Mode") && 
/*  780 */                                             element.getStringValue().equalsIgnoreCase("Modify")) {
/*  781 */                                             newValue = "Change";
/*      */                                           }
/*      */                                           
/*  784 */                                           changedFields.append("&nbsp;&nbsp;From:&nbsp;");
/*  785 */                                           changedFields.append(String.valueOf(startValue) + "<BR>");
/*  786 */                                           changedFields.append(
/*  787 */                                               "&nbsp;&nbsp;<font color='#000000'><b>To:</b></font>&nbsp;<b>");
/*  788 */                                           changedFields.append(String.valueOf(newValue) + "</b><BR>");
/*      */                                         } 
/*      */ 
/*      */ 
/*      */                                         
/*  793 */                                         changedFields.append("<P>");
/*  794 */                                         changed = true;
/*      */ 
/*      */                                         
/*  797 */                                         if (element.getDisplayName().equalsIgnoreCase("REPERTOIRE_OWNER")) {
/*      */ 
/*      */ 
/*      */                                           
/*  801 */                                           String subDescBefore = "";
/*  802 */                                           String subDescAfter = "";
/*  803 */                                           Vector repVector = Cache.getRepertoireClasses();
/*  804 */                                           for (int repCount = 0; repCount < repVector.size(); repCount++) {
/*      */                                             
/*  806 */                                             LookupObject lookupObject = (LookupObject)repVector.elementAt(repCount);
/*      */ 
/*      */ 
/*      */                                             
/*  810 */                                             int positOpen = lookupObject.getName().indexOf("(");
/*  811 */                                             int positClose = lookupObject.getName().indexOf(")");
/*  812 */                                             String lookupName = "";
/*  813 */                                             if (positOpen != -1 && positClose != -1) {
/*      */ 
/*      */                                               
/*  816 */                                               lookupName = lookupObject.getName().substring(0, lookupObject.getName().length() - positClose - positOpen + 1).trim();
/*      */ 
/*      */ 
/*      */                                               
/*  820 */                                               int positStart = startValue.indexOf(":");
/*  821 */                                               int positNew = newValue.indexOf(":");
/*  822 */                                               if (positStart != -1 && positNew != -1) {
/*      */ 
/*      */                                                 
/*  825 */                                                 String adjustedStartValue = "";
/*  826 */                                                 String adjustedNewValue = "";
/*  827 */                                                 adjustedStartValue = startValue.substring(positStart + 1, startValue.length());
/*  828 */                                                 adjustedNewValue = newValue.substring(positNew + 1, newValue.length());
/*      */                                                 
/*  830 */                                                 if (lookupName.equals(adjustedStartValue)) {
/*  831 */                                                   subDescBefore = lookupObject.getSubValue();
/*      */                                                 }
/*  833 */                                                 if (lookupName.equals(adjustedNewValue))
/*  834 */                                                   subDescAfter = lookupObject.getSubValue(); 
/*      */                                               } 
/*      */                                             } 
/*      */                                           } 
/*  838 */                                           changedFields.append("<font color='#0000FF'><B><u>REPERTOIRE CLASS</u></B>:</font><BR>");
/*  839 */                                           changedFields.append("&nbsp;&nbsp;From:&nbsp;");
/*  840 */                                           changedFields.append(String.valueOf(startValue) + " (" + subDescBefore + ")<BR>");
/*  841 */                                           changedFields.append("&nbsp;&nbsp;<font color='#000000'><b>To:&nbsp;</b></font>");
/*  842 */                                           changedFields.append("<b>" + newValue + " (" + subDescAfter + ")</b><BR>");
/*  843 */                                           changedFields.append("<P>");
/*      */                                         } 
/*      */                                       } 
/*      */                                     } 
/*      */                                   } 
/*      */                                 } 
/*      */                               }  }  }  }  }  }  }  }  }  } 
/*      */           }
/*      */         }
/*      */       }
/*      */       continue;
/*      */     } 
/*  855 */     for (int x = 0; x < inkChanges.size(); x++) {
/*      */       
/*  857 */       InkChange currentInk = (InkChange)inkChanges.elementAt(x);
/*      */ 
/*      */       
/*  860 */       if (!currentInk.getIsLeftSet()) {
/*      */         
/*  862 */         String leftInk = currentInk.getName();
/*  863 */         leftInk = "INK1" + leftInk.substring(4, leftInk.length());
/*      */         
/*  865 */         FormElement inkElement = form.getElement(leftInk);
/*  866 */         String inkDName = "";
/*      */         
/*  868 */         if (inkElement.getId().length() > 0) {
/*  869 */           inkDName = inkElement.getId().substring(5, inkElement.getId().length() - 1);
/*      */         }
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  875 */         currentInk.setStartingValueLeft(inkElement.getStartingValue());
/*  876 */         currentInk.setEndValueLeft(inkElement.getStringValue());
/*      */       } 
/*      */ 
/*      */       
/*  880 */       if (!currentInk.getIsRightSet()) {
/*      */         
/*  882 */         String rightInk = currentInk.getName();
/*  883 */         rightInk = "INK2" + rightInk.substring(4, rightInk.length());
/*      */         
/*  885 */         FormElement inkElement = form.getElement(rightInk);
/*      */         
/*  887 */         String inkDName = "";
/*      */         
/*  889 */         if (inkElement.getId().length() > 0) {
/*  890 */           inkDName = inkElement.getId().substring(5, inkElement.getId().length() - 1);
/*      */         }
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  896 */         currentInk.setStartingValueRight(inkElement.getStartingValue());
/*  897 */         currentInk.setEndValueRight(inkElement.getStringValue());
/*  898 */         changedFields.append("<font color='#0000FF'><B><u>" + currentInk.getId() + 
/*  899 */             "</u></B>:</font><BR>");
/*      */         
/*  901 */         changedFields.append("&nbsp;&nbsp;From:&nbsp;");
/*  902 */         changedFields.append(String.valueOf(currentInk.getStartingValueLeft()) + "/" + 
/*  903 */             currentInk.getStartingValueRight() + "<BR>");
/*  904 */         changedFields.append("&nbsp;&nbsp;<b>To:&nbsp;");
/*  905 */         changedFields.append(String.valueOf(currentInk.getEndValueLeft()) + "/" + 
/*  906 */             currentInk.getEndValueRight());
/*  907 */         changedFields.append("</b><P>");
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  917 */     return changed;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean IsPFMPushElement(String elementName) {
/*  924 */     if (PushPFM.getPushElements().containsKey(elementName)) {
/*  925 */       return true;
/*      */     }
/*  927 */     return false;
/*      */   }
/*      */   
/*      */   private static void ListFormElements(Form form) {
/*  931 */     for (Iterator i = form.getElements(); i.hasNext();)
/*      */     {
/*  933 */       System.err.println("<element>" + ((FormElement)i.next()).getDisplayName() + "</element>");
/*      */     }
/*      */   }
/*      */   
/*      */   private static String convertCheckBoxOld(FormCheckBox ckBox) {
/*  938 */     if (ckBox.isChecked()) {
/*  939 */       return "off";
/*      */     }
/*  941 */     return "on";
/*      */   }
/*      */ 
/*      */   
/*      */   private static String convertCheckBoxNew(FormCheckBox ckBox) {
/*  946 */     if (ckBox.isChecked()) {
/*  947 */       return "on";
/*      */     }
/*  949 */     return "off";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static String removeQuote(String str) {
/*  958 */     int s = 0;
/*  959 */     int e = 0;
/*  960 */     StringBuffer result = new StringBuffer();
/*  961 */     while ((e = str.indexOf("&quot;", s)) >= 0) {
/*  962 */       result.append(str.substring(s, e));
/*  963 */       result.append("\"");
/*  964 */       s = e + "&quot;".length();
/*      */     } 
/*  966 */     result.append(str.substring(s));
/*  967 */     return result.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static String removeNbsp(String str) {
/*  975 */     int s = 0;
/*  976 */     int e = 0;
/*  977 */     StringBuffer result = new StringBuffer();
/*  978 */     while ((e = str.indexOf("&nbsp;", s)) >= 0) {
/*  979 */       result.append(str.substring(s, e));
/*  980 */       s = e + "&nbsp;".length();
/*      */     } 
/*  982 */     result.append(str.substring(s));
/*  983 */     return result.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void putEmailAttach(Context context, String fileName, MessageObject messageObject) {
/*  990 */     context.putSessionValue("emailAttach", fileName);
/*  991 */     if (messageObject != null) {
/*  992 */       messageObject.emailAttach = new String(fileName);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public static void removeSessionValues(Context context) {
/*  998 */     context.removeSessionValue("emailBody");
/*  999 */     context.removeSessionValue("emailSubject");
/* 1000 */     context.removeSessionValue("emailTo");
/* 1001 */     context.removeSessionValue("emailCC");
/* 1002 */     context.removeSessionValue("emailAttach");
/* 1003 */     context.removeSessionValue("emailConfirm");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1009 */   public static void resetConfirmValue(Context context) { context.putSessionValue("emailConfirm", "false"); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean sendEmail(Dispatcher dispatcher, Context context, String command, MessageObject messageObject) {
/* 1042 */     String emailCC = messageObject.emailCC;
/* 1043 */     String emailBody = messageObject.emailBody;
/* 1044 */     String emailSubject = messageObject.emailSubject;
/* 1045 */     String emailTo = messageObject.emailTo;
/* 1046 */     String emailFrom = messageObject.emailFrom;
/* 1047 */     String emailAttach = messageObject.emailAttach;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1052 */     if (emailTo == null || (emailTo != null && emailTo.equals(""))) {
/*      */       
/* 1054 */       context.putDelivery("AlertMessage", "Email Notification Send Failed..<BR>(Missing Send To Address)..<BR>Please notfiy Milestone Support Staff");
/* 1055 */       context.putDelivery("SendMailStatus", "0");
/* 1056 */       return true;
/*      */     } 
/*      */ 
/*      */     
/* 1060 */     if (EmailDistributionManager.sendEmailDistribution(emailSubject, emailBody, emailTo, emailCC, emailAttach)) {
/*      */       
/* 1062 */       context.putDelivery("AlertMessage", "Email Notfication Sent Sucessfully.....<BR> Sent To: " + addWrapToEmailToStr(emailTo));
/* 1063 */       context.putDelivery("SendMailStatus", "1");
/*      */     }
/*      */     else {
/*      */       
/* 1067 */       context.putDelivery("AlertMessage", "EMAIL NOTIFICATION SEND FAILED! <BR>Please notfiy Milestone Support Staff.<BR>.....Failed To Send To: " + addWrapToEmailToStr(emailTo));
/* 1068 */       context.putDelivery("SendMailStatus", "0");
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1073 */     if (!command.equals(""))
/* 1074 */       dispatcher.redispatch(context, command); 
/* 1075 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public static void setStaticValues() {
/* 1080 */     synchronized (lockObject) {
/*      */ 
/*      */       
/*      */       try {
/* 1084 */         String filename = "milestone.conf";
/*      */ 
/*      */         
/* 1087 */         InputStream in = ClassLoader.getSystemResourceAsStream(filename);
/*      */ 
/*      */         
/* 1090 */         if (in == null) {
/* 1091 */           in = new FileInputStream(filename);
/*      */         }
/* 1093 */         Properties defaultProps = new Properties();
/* 1094 */         defaultProps.load(in);
/*      */         
/* 1096 */         emailOutDir = defaultProps.getProperty("EmailOutDir");
/* 1097 */         emailAttachDir = defaultProps.getProperty("EmailAttachDir");
/*      */         
/* 1099 */         in.close();
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       }
/* 1105 */       catch (Exception ex) {
/* 1106 */         System.out.println("<<< EmailDistribution Exception " + ex.getMessage());
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static String getEmailOutDir() {
/* 1113 */     if (emailOutDir == null)
/* 1114 */       setStaticValues(); 
/* 1115 */     return emailOutDir;
/*      */   }
/*      */ 
/*      */   
/*      */   public static String getEmailAttachDir() {
/* 1120 */     if (emailAttachDir == null)
/* 1121 */       setStaticValues(); 
/* 1122 */     return emailAttachDir;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static String getAttachFileSuffix(Context context, String formType, String productNo, String prefix, String upc, boolean isDigital) {
/*      */     String fileSuffix;
/* 1129 */     User user = (User)context.getSessionValue("user");
/* 1130 */     if (user != null) {
/*      */       
/* 1132 */       if (isDigital) {
/* 1133 */         fileSuffix = "\\" + formType + "_" + upc + "_" + user.getUserId() + 
/* 1134 */           "_" + Calendar.getInstance().getTime().getTime() + ".pdf";
/*      */       } else {
/* 1136 */         fileSuffix = "\\" + formType + "_" + prefix + " " + productNo + "_" + user.getUserId() + 
/* 1137 */           "_" + Calendar.getInstance().getTime().getTime() + ".pdf";
/*      */       }
/*      */     
/*      */     }
/* 1141 */     else if (isDigital) {
/* 1142 */       fileSuffix = "\\" + formType + "_" + upc + "_" + Calendar.getInstance().getTime().getTime() + ".pdf";
/*      */     } else {
/* 1144 */       fileSuffix = "\\" + formType + "_" + prefix + " " + productNo + "_" + Calendar.getInstance().getTime().getTime() + ".pdf";
/*      */     } 
/*      */     
/* 1147 */     return fileSuffix;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void generateFormReport(Context context, String formType, XStyleSheet report, String productNo, String prefix, String upc, boolean isDigital, MessageObject messageObject) {
/* 1156 */     String attachFileName = new String();
/* 1157 */     String outFileName = new String();
/* 1158 */     User user = (User)context.getSessionValue("user");
/* 1159 */     String fileSuffix = getAttachFileSuffix(context, formType, productNo, prefix, upc, isDigital);
/* 1160 */     outFileName = String.valueOf(getEmailOutDir()) + fileSuffix;
/* 1161 */     attachFileName = String.valueOf(getEmailAttachDir()) + fileSuffix;
/*      */ 
/*      */     
/*      */     try {
/* 1165 */       FileOutputStream fileOutstream = new FileOutputStream(outFileName);
/* 1166 */       PDF4Generator pdfGenerator = new PDF4Generator(fileOutstream);
/* 1167 */       pdfGenerator.generate(report);
/* 1168 */       fileOutstream.close();
/* 1169 */     } catch (Exception e) {
/*      */ 
/*      */       
/* 1172 */       attachFileName = "** Unable to create document, contact IT support staff ** \n outDoc: " + 
/* 1173 */         outFileName + 
/* 1174 */         "\n attachDoc: " + attachFileName + 
/* 1175 */         "\n exception: " + e.getMessage();
/*      */     } 
/*      */     
/* 1178 */     putEmailAttach(context, attachFileName, messageObject);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String convertFormCodes(String code, String codeNo) {
/* 1186 */     if (code.equalsIgnoreCase("PID7") || codeNo.equals("7"))
/* 1187 */       return "Disc"; 
/* 1188 */     if (code.equalsIgnoreCase("PID12") || codeNo.equals("12"))
/* 1189 */       return "Jewel Box"; 
/* 1190 */     if (code.equalsIgnoreCase("PID23") || codeNo.equals("23"))
/* 1191 */       return "Tray"; 
/* 1192 */     if (code.equalsIgnoreCase("PID10") || codeNo.equals("10"))
/* 1193 */       return "Inlay"; 
/* 1194 */     if (code.equalsIgnoreCase("PID9") || codeNo.equals("9"))
/* 1195 */       return "Front Insert"; 
/* 1196 */     if (code.equalsIgnoreCase("PID8") || codeNo.equals("8"))
/* 1197 */       return "Folder"; 
/* 1198 */     if (code.equalsIgnoreCase("PID1") || codeNo.equals("1"))
/* 1199 */       return "Booklet"; 
/* 1200 */     if (code.equalsIgnoreCase("PID4") || codeNo.equals("4"))
/* 1201 */       return "BRC Insert"; 
/* 1202 */     if (code.equalsIgnoreCase("PID15") || codeNo.equals("15"))
/* 1203 */       return "Mini Jacket"; 
/* 1204 */     if (code.equalsIgnoreCase("PID6") || codeNo.equals("6"))
/* 1205 */       return "DigiPak"; 
/* 1206 */     if (code.equalsIgnoreCase("PID21") || codeNo.equals("21"))
/* 1207 */       return "Sticker1"; 
/* 1208 */     if (code.equalsIgnoreCase("PID22") || codeNo.equals("22"))
/* 1209 */       return "Sticker 2"; 
/* 1210 */     if (code.equalsIgnoreCase("PID2") || codeNo.equals("2"))
/* 1211 */       return "Book (Other/Set)"; 
/* 1212 */     if (code.equalsIgnoreCase("PID3") || codeNo.equals("3"))
/* 1213 */       return "Box (Set)"; 
/* 1214 */     if (code.equalsIgnoreCase("PID18") || codeNo.equals("18")) {
/* 1215 */       return "Other";
/*      */     }
/*      */     
/* 1218 */     if (code.equalsIgnoreCase("PID5") || codeNo.equals("5"))
/* 1219 */       return "C-0"; 
/* 1220 */     if (code.equalsIgnoreCase("PID16") || codeNo.equals("16"))
/* 1221 */       return "Norelco"; 
/* 1222 */     if (code.equalsIgnoreCase("PID13") || codeNo.equals("5"))
/* 1223 */       return "J-Card"; 
/* 1224 */     if (code.equalsIgnoreCase("PID24") || codeNo.equals("24"))
/* 1225 */       return "U-Card"; 
/* 1226 */     if (code.equalsIgnoreCase("PID17"))
/* 1227 */       return "O-Card"; 
/* 1228 */     if (code.equalsIgnoreCase("PID5") || codeNo.equals("5")) {
/* 1229 */       return "C-0";
/*      */     }
/*      */     
/* 1232 */     if (code.equalsIgnoreCase("PID19") || codeNo.equals("19"))
/* 1233 */       return "Record"; 
/* 1234 */     if (code.equalsIgnoreCase("PID14") || codeNo.equals("14"))
/* 1235 */       return "Label"; 
/* 1236 */     if (code.equalsIgnoreCase("PID20") || codeNo.equals("20"))
/* 1237 */       return "Sleeve"; 
/* 1238 */     if (code.equalsIgnoreCase("PID11") || codeNo.equals("11")) {
/* 1239 */       return "Jacket";
/*      */     }
/*      */     
/* 1242 */     if (code.indexOf("guarantee_code") >= 0) {
/* 1243 */       return "IMI Exempt";
/*      */     }
/*      */ 
/*      */     
/* 1247 */     if (code.indexOf("SID") >= 0)
/* 1248 */       return String.valueOf(convertFormCodes("", code.substring(3))) + " / Supplier "; 
/* 1249 */     if (code.indexOf("INK") >= 0)
/* 1250 */       return String.valueOf(convertFormCodes("", code.substring(3))) + " / Ink "; 
/* 1251 */     if (code.indexOf("INF") >= 0)
/* 1252 */       return String.valueOf(convertFormCodes("", code.substring(3))) + " / Additional Information "; 
/* 1253 */     if (code.indexOf("SEL") >= 0) {
/* 1254 */       return String.valueOf(convertFormCodes("", code.substring(3))) + " / Additional Information ";
/*      */     }
/*      */     
/* 1257 */     return code;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1267 */   public String getNotepadContentObjectId() { return Integer.toString(this.distributionId); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1273 */   public Object clone() throws CloneNotSupportedException { return super.clone(); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1281 */   public Hashtable getReleasingFamily() { return this.releasingFamilyHash; }
/*      */ 
/*      */ 
/*      */   
/* 1285 */   public void setReleasingFamily(Hashtable releasingFamilyHash) { this.releasingFamilyHash = releasingFamilyHash; }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1290 */   public void setAssignedEnvironments(Vector assignedEnvironments) { this.assignedEnvironments = assignedEnvironments; }
/*      */ 
/*      */ 
/*      */   
/* 1294 */   public Vector getAssignedEnvironments() { return this.assignedEnvironments; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String addWrapToEmailToStr(String emailTo) {
/* 1304 */     StringBuffer emailToBuf = new StringBuffer(emailTo);
/*      */     
/* 1306 */     return emailToBuf.toString().replaceAll(";", ";<br>");
/*      */   }
/*      */ }


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\EmailDistribution.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */