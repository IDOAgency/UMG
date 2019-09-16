package WEB-INF.classes.com.universal.milestone;

import com.techempower.ComponentLog;
import com.techempower.gemini.Context;
import com.techempower.gemini.Dispatcher;
import com.techempower.gemini.FormCheckBox;
import com.techempower.gemini.FormDropDownMenu;
import com.techempower.gemini.FormElement;
import com.techempower.gemini.FormHidden;
import com.techempower.gemini.FormRadioButtonGroup;
import com.techempower.gemini.FormTextField;
import com.techempower.gemini.FormValidation;
import com.techempower.gemini.GeminiApplication;
import com.universal.milestone.Cache;
import com.universal.milestone.EmailDistribution;
import com.universal.milestone.EmailDistributionDetail;
import com.universal.milestone.EmailDistributionHandler;
import com.universal.milestone.EmailDistributionManager;
import com.universal.milestone.EmailDistributionReleasingFamily;
import com.universal.milestone.Environment;
import com.universal.milestone.Form;
import com.universal.milestone.MilestoneConstants;
import com.universal.milestone.MilestoneHelper;
import com.universal.milestone.MilestoneSecurity;
import com.universal.milestone.Notepad;
import com.universal.milestone.SecureHandler;
import com.universal.milestone.User;
import com.universal.milestone.UserManager;
import java.util.Vector;

public class EmailDistributionHandler extends SecureHandler {
  public static final String COMPONENT_CODE = "hEmailDist";
  
  public GeminiApplication application;
  
  public ComponentLog log;
  
  public EmailDistributionHandler(GeminiApplication application) {
    this.application = application;
    this.log = application.getLog("hEmailDist");
  }
  
  public String getDescription() { return "emailDistribution"; }
  
  public boolean acceptRequest(Dispatcher dispatcher, Context context, String command) {
    if (super.acceptRequest(dispatcher, context, command) && 
      command.startsWith("email-distribution"))
      return handleRequest(dispatcher, context, command); 
    return false;
  }
  
  public boolean handleRequest(Dispatcher dispatcher, Context context, String command) {
    if (command.equalsIgnoreCase("email-distribution-search")) {
      emailDistributionSearch(context);
    } else if (command.equalsIgnoreCase("email-distribution-save")) {
      emailDistributionSave(context);
    } else if (command.equalsIgnoreCase("email-distribution-delete")) {
      emailDistributionDelete(context);
    } else if (command.equalsIgnoreCase("email-distribution-search-results")) {
      emailDistributionSearchResults(context);
    } else if (command.equalsIgnoreCase("email-distribution-editor")) {
      emailDistributionEditor(context);
    } else if (command.equalsIgnoreCase("email-distribution-new")) {
      emailDistributionNew(context);
    } else if (command.equalsIgnoreCase("email-distribution-sort")) {
      emailDistributionSort(context);
    } else if (command.equalsIgnoreCase("email-distribution-save-releasing-family")) {
      emailDistributionReleasingFamilySave(dispatcher, context);
    } else if (command.equalsIgnoreCase("email-distribution-copy")) {
      emailDistributionCopy(context);
    } 
    return true;
  }
  
  private boolean emailDistributionSearch(Context context) {
    Notepad notepad = MilestoneHelper.getNotepadFromSession(
        20, context);
    EmailDistributionManager.getInstance().setEmailDistributionNotepadQuery(
        context, notepad);
    notepad.setAllContents(null);
    notepad.setSelected(null);
    emailDistributionEditor(context);
    return true;
  }
  
  private boolean emailDistributionSearchResults(Context context) { return context.includeJSP("email-distribution-search-results.jsp"); }
  
  private boolean emailDistributionEditor(Context context) {
    context.removeSessionValue("copiedEmailDistObj");
    Notepad notepad = getEmailDistributionNotepad(context);
    MilestoneHelper.putNotepadIntoSession(notepad, context);
    EmailDistribution emailDist = getEmailDistribution(context);
    context.putSessionValue("emailDistribution", emailDist);
    if (emailDist != null) {
      Form form = null;
      if (emailDist != null) {
        form = buildForm(context, emailDist);
      } else {
        form = buildNewForm(context);
      } 
      return context.includeJSP("email-distribution-editor.jsp");
    } 
    return emailDistributionGoToBlank(context);
  }
  
  private boolean emailDistributionSave(Context context) {
    User sessionUser = MilestoneSecurity.getUser(context);
    EmailDistribution emailDist = (EmailDistribution)context.getSessionValue(
        "emailDistribution");
    boolean isNewUser = false;
    if (emailDist == null) {
      emailDist = new EmailDistribution();
      isNewUser = true;
    } 
    boolean isRelFamilyNewUser = false;
    if (isNewUser || emailDist.getDistributionId() == -1)
      isRelFamilyNewUser = true; 
    Form form = buildForm(context, emailDist);
    if (EmailDistributionManager.getInstance().isTimestampValid(emailDist)) {
      form.setValues(context);
      Vector emailDistDetail = emailDist.getEmailDistributionDetail();
      String firstName = form.getStringValue("firstName");
      emailDist.setFirstName(firstName);
      String lastName = form.getStringValue("lastName");
      emailDist.setLastName(lastName);
      String email = form.getStringValue("email");
      emailDist.setEmail(email);
      emailDist.setPfm(((FormCheckBox)form.getElement("pfm")).isChecked());
      emailDist.setBom(((FormCheckBox)form.getElement("bom")).isChecked());
      emailDist.setPromo(((FormCheckBox)form.getElement("promo")).isChecked());
      emailDist.setCommercial(
          ((FormCheckBox)form.getElement("commercial")).isChecked());
      emailDist.setInactive(
          ((FormCheckBox)form.getElement("inactive")).isChecked());
      String westString = form.getStringValue("Distribution");
      if (westString.equalsIgnoreCase("West")) {
        emailDist.setLabelDistribution(0);
      } else if (westString.equalsIgnoreCase("East")) {
        emailDist.setLabelDistribution(1);
      } else if (westString.equalsIgnoreCase("Both")) {
        emailDist.setLabelDistribution(2);
      } else {
        emailDist.setLabelDistribution(3);
      } 
      String productType = form.getStringValue("ProductType");
      if (productType.equalsIgnoreCase("Digital")) {
        emailDist.setProductType(0);
      } else if (productType.equalsIgnoreCase("Physical")) {
        emailDist.setProductType(1);
      } else {
        emailDist.setProductType(2);
      } 
      Vector checkedDetails = new Vector();
      Vector environments = Cache.getInstance().getEnvironments();
      if (environments != null)
        for (int i = 0; i < environments.size(); i++) {
          Environment environment = (Environment)environments.get(i);
          if (environment != null && 
            form.getElement("uc" + environment.getStructureID()) != null)
            if (((FormCheckBox)form.getElement("uc" + 
                environment.getStructureID()))
              .isChecked())
              checkedDetails.add(environment);  
        }  
      if (!EmailDistributionManager.getInstance().isDuplicate(emailDist)) {
        if (!form.isUnchanged()) {
          FormValidation formValidation = form.validate();
          if (formValidation.isGood()) {
            EmailDistribution savedEmailDist = EmailDistributionManager.getInstance().save(emailDist, sessionUser, checkedDetails, 
                context);
            if (emailDist.getCopyDistributionId() != -1)
              EmailDistributionManager.getInstance().saveCopyEmailDistributionReleasingFamily(
                  savedEmailDist.getDistributionId(), emailDist.getCopyDistributionId(), sessionUser.getUserId()); 
            FormElement lastUpdated = form.getElement("lastupdatedon");
            if (savedEmailDist.getLastUpdateOn() != null)
              lastUpdated.setValue(MilestoneHelper.getLongDate(
                    savedEmailDist.getLastUpdateOn())); 
            savedEmailDist = EmailDistributionManager.getInstance()
              .getEmailDistribution(savedEmailDist.getDistributionId(), true);
            form = buildForm(context, savedEmailDist);
            context.putSessionValue("emailDistribution", savedEmailDist);
            Notepad notepad = getEmailDistributionNotepad(context);
            notepad.setAllContents(null);
            if (isNewUser) {
              notepad.newSelectedReset();
              notepad = getEmailDistributionNotepad(context);
              notepad.setSelected(savedEmailDist);
              notepad = getEmailDistributionNotepad(context);
            } else {
              notepad = getEmailDistributionNotepad(context);
              notepad.setSelected(savedEmailDist);
              emailDist = (EmailDistribution)notepad.validateSelected();
              if (emailDist == null)
                return emailDistributionGoToBlank(context); 
              form = buildForm(context, emailDist);
            } 
          } else {
            context.putDelivery("FormValidation", formValidation);
          } 
        } 
        if (isRelFamilyNewUser)
          context.putSessionValue("showAssignedEmail", "ASSIGNED"); 
      } else {
        Notepad notepad = getEmailDistributionNotepad(context);
        context.putDelivery("AlertMessage", "Duplicate.");
        emailDist = (EmailDistribution)notepad.validateSelected();
        form = buildForm(context, emailDist);
      } 
    } else {
      context.putDelivery("AlertMessage", "The record is unavailable for editing at this time.  Please refresh your screen and edit it at a later time.");
    } 
    context.putDelivery("Form", form);
    return context.includeJSP("email-distribution-editor.jsp");
  }
  
  private boolean emailDistributionNew(Context context) {
    Notepad notepad = getEmailDistributionNotepad(context);
    notepad.setSelected(null);
    MilestoneHelper.putNotepadIntoSession(notepad, context);
    context.removeSessionValue("emailDistribution");
    Form form = buildNewForm(context);
    return context.includeJSP("email-distribution-editor.jsp");
  }
  
  private boolean emailDistributionDelete(Context context) {
    Notepad notepad = getEmailDistributionNotepad(context);
    MilestoneHelper.putNotepadIntoSession(notepad, context);
    EmailDistribution emailDist = getEmailDistribution(context);
    if (emailDist != null) {
      EmailDistributionManager.getInstance().deleteEmailDistribution(
          emailDist.getDistributionId());
      notepad.setAllContents(null);
      notepad.setSelected(null);
    } 
    return emailDistributionEditor(context);
  }
  
  private Form buildForm(Context context, EmailDistribution emailDist) {
    String showAssigned = context.getRequest().getParameter("showAssignedEmail");
    if (showAssigned == null && context.getSessionValue("showAssignedEmail") != null)
      showAssigned = (String)context.getSessionValue("showAssignedEmail"); 
    if (showAssigned != null) {
      if (emailDist.getDistributionId() == -1) {
        context.putSessionValue("showAssignedEmail", "ALL");
      } else {
        context.putSessionValue("showAssignedEmail", showAssigned);
      } 
    } else {
      context.putSessionValue("showAssignedEmail", "ASSIGNED");
    } 
    Form emailDistForm = new Form(this.application, "emailDistributionForm", 
        this.application.getInfrastructure().getServletURL(), 
        "POST");
    FormTextField firstName = new FormTextField("firstName", 
        emailDist.getFirstName(), true, 
        30, 30);
    firstName.setTabIndex(1);
    emailDistForm.addElement(firstName);
    FormTextField lastName = new FormTextField("lastName", 
        emailDist.getLastName(), true, 
        30, 30);
    lastName.setTabIndex(2);
    emailDistForm.addElement(lastName);
    FormCheckBox inactive = new FormCheckBox("inactive", "Inactive", false, 
        !!emailDist.getInactive());
    inactive.setId("inactive");
    inactive.setTabIndex(3);
    emailDistForm.addElement(inactive);
    FormTextField email = new FormTextField("email", emailDist.getEmail(), true, 
        50, 50);
    email.addFormEvent("onfocus", "JavaScript:buildEmail()");
    email.addFormEvent("onblur", "JavaScript:isValidEmailAddr(this)");
    email.setTabIndex(4);
    emailDistForm.addElement(email);
    String westEast = "None";
    if (emailDist.getLabelDistribution() == 
      1)
      westEast = "East"; 
    if (emailDist.getLabelDistribution() == 0)
      westEast = "West"; 
    if (emailDist.getLabelDistribution() == 
      2)
      westEast = "Both"; 
    FormRadioButtonGroup distribution = new FormRadioButtonGroup("Distribution", 
        westEast, "West, East, Both, None", false);
    distribution.setTabIndex(5);
    emailDistForm.addElement(distribution);
    String productTypeStr = "Both";
    if (emailDist.getProductType() == 0)
      productTypeStr = "Digital"; 
    if (emailDist.getProductType() == 1)
      productTypeStr = "Physical"; 
    FormRadioButtonGroup productType = new FormRadioButtonGroup("ProductType", 
        productTypeStr, "Digital, Physical, Both", false);
    distribution.setTabIndex(6);
    emailDistForm.addElement(productType);
    FormCheckBox pfm = new FormCheckBox("PFM", "PFM", false, !!emailDist.getPfm());
    pfm.setId("pfm");
    pfm.setTabIndex(6);
    emailDistForm.addElement(pfm);
    FormCheckBox bom = new FormCheckBox("BOM", "BOM", false, !!emailDist.getBom());
    bom.setId("bom");
    bom.setTabIndex(7);
    emailDistForm.addElement(bom);
    FormCheckBox promo = new FormCheckBox("Promo", "Promo", false, 
        !!emailDist.getPromo());
    promo.setId("promo");
    promo.setTabIndex(8);
    emailDistForm.addElement(promo);
    FormCheckBox commercial = new FormCheckBox("Commercial", "Commercial", false, 
        !!emailDist.getCommercial());
    commercial.setId("commercial");
    commercial.setTabIndex(9);
    emailDistForm.addElement(commercial);
    Vector environments = Cache.getInstance().getEnvironments();
    int distributionId = emailDist.getDistributionId();
    Vector userEnvironments = null;
    userEnvironments = emailDist.getEmailDistributionDetail();
    for (int i = 0; i < environments.size(); i++) {
      Environment environment = (Environment)environments.elementAt(i);
      if (environment != null) {
        if (userEnvironments != null)
          for (int a = 0; a < userEnvironments.size(); a++) {
            EmailDistributionDetail emailDistDetail = 
              (EmailDistributionDetail)userEnvironments.elementAt(a);
            if (emailDistDetail != null && 
              emailDistDetail.getStructureId() == environment.getStructureID()) {
              FormCheckBox environmentCheckbox = new FormCheckBox("uc" + 
                  emailDistDetail.getStructureId(), 
                  "uc" + emailDistDetail.getStructureId(), environment.getName(), false, true);
              environmentCheckbox.setId("uc" + emailDistDetail.getStructureId());
              emailDistForm.addElement(environmentCheckbox);
            } 
          }  
        FormCheckBox environmentCheckbox = new FormCheckBox("uc" + 
            environment.getStructureID(), "uc" + environment.getStructureID(), 
            environment.getName(), false, false);
        environmentCheckbox.setId("uc" + environment.getStructureID());
        emailDistForm.addElement(environmentCheckbox);
      } 
    } 
    FormTextField lastUpdated = new FormTextField("lastUpdatedOn", false, 50);
    if (emailDist.getLastUpdateOn() != null)
      lastUpdated.setValue(MilestoneHelper.getLongDate(
            emailDist.getLastUpdateOn())); 
    emailDistForm.addElement(lastUpdated);
    FormTextField lastUpdatedBy = new FormTextField("lastUpdatedBy", false, 50);
    if (UserManager.getInstance().getUser(emailDist.getLastUpdateBy(), true) != null)
      lastUpdatedBy.setValue(
          UserManager.getInstance().getUser(emailDist.getLastUpdateBy(), true).getLogin()); 
    emailDistForm.addElement(lastUpdatedBy);
    FormTextField firstNameSrch = new FormTextField("firstNameSrch", "", false, 
        20);
    firstNameSrch.setId("firstNameSrch");
    emailDistForm.addElement(firstNameSrch);
    FormTextField lastNameSrch = new FormTextField("lastNameSrch", "", false, 
        20);
    lastNameSrch.setId("lastNameSrch");
    emailDistForm.addElement(lastNameSrch);
    FormDropDownMenu environmentSrch = 
      MilestoneHelper.getCorporateStructureDropDown("environmentSrch", environments, "", false, true);
    environmentSrch.setId("environmentSrch");
    emailDistForm.addElement(environmentSrch);
    FormDropDownMenu formTypeSrch = new FormDropDownMenu("formTypeSrch", "", 
        "-1,pfm,bom", 
        "&nbsp;,PFM, BOM", false);
    formTypeSrch.setId("formTypeSrch");
    emailDistForm.addElement(formTypeSrch);
    FormDropDownMenu releaseTypeSrch = new FormDropDownMenu("releaseTypeSrch", 
        "", 
        "-1,promo,commercial", 
        "&nbsp;,Promo, Commercial", false);
    releaseTypeSrch.setId("releaseTypeSrch");
    emailDistForm.addElement(releaseTypeSrch);
    FormDropDownMenu productTypeSrch = new FormDropDownMenu("productTypeSrch", 
        "", 
        "-1,2,1,0", 
        "&nbsp;,Both ,Physical, Digital", false);
    productTypeSrch.setId("productTypeSrch");
    emailDistForm.addElement(productTypeSrch);
    emailDistForm.addElement(new FormHidden("cmd", "email-distribution-editor"));
    context.putDelivery("Form", emailDistForm);
    if (context.getSessionValue("NOTEPAD_EMAIL_DISTRIBUTION_VISIBLE") != null)
      context.putDelivery("isNotePadVisible", 
          (Boolean)context.getSessionValue("NOTEPAD_EMAIL_DISTRIBUTION_VISIBLE")); 
    return emailDistForm;
  }
  
  public Notepad getEmailDistributionNotepad(Context context) {
    Vector contents = new Vector();
    if (MilestoneHelper.getNotepadFromSession(20, 
        context) != null) {
      Notepad notepad = MilestoneHelper.getNotepadFromSession(
          20, context);
      if (notepad.getAllContents() == null) {
        this.log.debug("---------Reseting note pad contents------------");
        EmailDistributionManager.getInstance();
        contents = 
          EmailDistributionManager.getDistributionNotepadList(notepad);
        notepad.setAllContents(contents);
      } 
      return notepad;
    } 
    String[] columnNames = { "First Name", "Last Name" };
    EmailDistributionManager.getInstance();
    contents = 
      EmailDistributionManager.getDistributionNotepadList(null);
    Notepad notepad = new Notepad(contents, 0, 7, 
        "Email Distribution", 
        20, columnNames);
    EmailDistributionManager.getInstance().setEmailDistributionNotepadQuery(
        context, notepad);
    return notepad;
  }
  
  private Form buildNewForm(Context context) {
    context.putSessionValue("showAssignedEmail", "NEW");
    Form emailDistForm = new Form(this.application, "emailDistributionForm", 
        this.application.getInfrastructure().getServletURL(), 
        "POST");
    FormTextField firstName = new FormTextField("firstName", "", true, 30, 100);
    firstName.setTabIndex(1);
    emailDistForm.addElement(firstName);
    FormTextField lastName = new FormTextField("lastName", "", true, 30, 30);
    lastName.setTabIndex(2);
    emailDistForm.addElement(lastName);
    FormCheckBox inactive = new FormCheckBox("inactive", "Inactive", false, false);
    inactive.setId("inactive");
    inactive.setTabIndex(3);
    emailDistForm.addElement(inactive);
    FormTextField email = new FormTextField("email", "", false, 50, 50);
    email.addFormEvent("onfocus", "JavaScript:buildEmail()");
    email.addFormEvent("onblur", "JavaScript:isValidEmailAddr(this)");
    email.setTabIndex(4);
    emailDistForm.addElement(email);
    String westEast = "None";
    FormRadioButtonGroup distribution = new FormRadioButtonGroup("Distribution", 
        westEast, "West, East, Both, None", false);
    distribution.setTabIndex(5);
    emailDistForm.addElement(distribution);
    String productTypeStr = "Both";
    FormRadioButtonGroup productType = new FormRadioButtonGroup("ProductType", 
        productTypeStr, "Digital, Physical, Both", false);
    distribution.setTabIndex(6);
    emailDistForm.addElement(productType);
    FormCheckBox pfm = new FormCheckBox("PFM", "PFM", false, false);
    pfm.setId("pfm");
    pfm.setTabIndex(6);
    emailDistForm.addElement(pfm);
    FormCheckBox bom = new FormCheckBox("BOM", "BOM", false, false);
    bom.setId("bom");
    bom.setTabIndex(7);
    emailDistForm.addElement(bom);
    FormCheckBox promo = new FormCheckBox("Promo", "Promo", false, false);
    promo.setId("promo");
    promo.setTabIndex(8);
    emailDistForm.addElement(promo);
    FormCheckBox commercial = new FormCheckBox("Commercial", "Commercial", false, false);
    commercial.setId("commercial");
    commercial.setTabIndex(9);
    emailDistForm.addElement(commercial);
    Vector environments = Cache.getInstance().getEnvironments();
    for (int i = 0; i < environments.size(); i++) {
      Environment environment = (Environment)environments.elementAt(i);
      if (environment != null) {
        FormCheckBox environmentCheckbox = new FormCheckBox("uc" + 
            environment.getStructureID(), "uc" + environment.getStructureID(), 
            environment.getName(), false, false);
        environmentCheckbox.setId("uc" + environment.getStructureID());
        emailDistForm.addElement(environmentCheckbox);
      } 
    } 
    FormTextField lastUpdated = new FormTextField("lastUpdatedOn", false, 50);
    emailDistForm.addElement(lastUpdated);
    FormTextField lastUpdatedBy = new FormTextField("lastUpdatedBy", false, 50);
    emailDistForm.addElement(lastUpdatedBy);
    FormTextField firstNameSrch = new FormTextField("firstNameSrch", "", false, 
        20);
    firstNameSrch.setId("firstNameSrch");
    emailDistForm.addElement(firstNameSrch);
    FormTextField lastNameSrch = new FormTextField("lastNameSrch", "", false, 
        20);
    lastNameSrch.setId("lastNameSrch");
    emailDistForm.addElement(lastNameSrch);
    FormDropDownMenu environmentSrch = 
      MilestoneHelper.getCorporateStructureDropDown("environmentSrch", environments, "", false, true);
    environmentSrch.setId("environmentSrch");
    emailDistForm.addElement(environmentSrch);
    FormDropDownMenu formTypeSrch = new FormDropDownMenu("formTypeSrch", "", 
        "-1,pfm,bom", 
        "&nbsp;,PFM, BOM", false);
    formTypeSrch.setId("formTypeSrch");
    emailDistForm.addElement(formTypeSrch);
    FormDropDownMenu releaseTypeSrch = new FormDropDownMenu("releaseTypeSrch", 
        "", 
        "-1,promo,commercial", 
        "&nbsp;,Promo, Commercial", false);
    releaseTypeSrch.setId("releaseTypeSrch");
    emailDistForm.addElement(releaseTypeSrch);
    FormDropDownMenu productTypeSrch = new FormDropDownMenu("productTypeSrch", 
        "", 
        "-1,2,1,0", 
        "&nbsp;,Both, Physical, Digital", false);
    productTypeSrch.setId("productTypeSrch");
    emailDistForm.addElement(productTypeSrch);
    emailDistForm.addElement(new FormHidden("cmd", "email-distribution-new"));
    context.putDelivery("Form", emailDistForm);
    if (context.getSessionValue("NOTEPAD_EMAIL_DISTRIBUTION_VISIBLE") != null)
      context.putDelivery("isNotePadVisible", 
          (Boolean)context.getSessionValue("NOTEPAD_EMAIL_DISTRIBUTION_VISIBLE")); 
    return emailDistForm;
  }
  
  private boolean emailDistributionSort(Context context) {
    int sort = Integer.parseInt(context.getParameter("OrderBy"));
    Notepad notepad = MilestoneHelper.getNotepadFromSession(
        20, context);
    if (notepad.getSearchQuery() == null || notepad.getSearchQuery().equals(""))
      notepad.setSearchQuery(
          EmailDistributionManager.getInstance().getDefaultQuery()); 
    notepad.setOrderBy(" ORDER BY [" + 
        MilestoneConstants.SORT_EMAIL_DISTRIBUTION[sort] + "]");
    notepad.setAllContents(null);
    notepad = getEmailDistributionNotepad(context);
    notepad.goToSelectedPage();
    emailDistributionEditor(context);
    return true;
  }
  
  private boolean emailDistributionGoToBlank(Context context) {
    context.putDelivery("isNotePadVisible", 
        new Boolean(
          MilestoneHelper.getNotePadVisiblitiy(20, 
            context)));
    Form form = new Form(this.application, "form", 
        this.application.getInfrastructure().getServletURL(), 
        "Post");
    form.addElement(new FormHidden("cmd", "email-distribution-editor", true));
    form.addElement(new FormHidden("OrderBy", "", true));
    FormTextField firstNameSrch = new FormTextField("firstNameSrch", "", false, 
        20);
    firstNameSrch.setId("firstNameSrch");
    form.addElement(firstNameSrch);
    FormTextField lastNameSrch = new FormTextField("lastNameSrch", "", false, 
        20);
    lastNameSrch.setId("lastNameSrch");
    form.addElement(lastNameSrch);
    Vector environments = Cache.getInstance().getEnvironments();
    FormDropDownMenu environmentSrch = 
      MilestoneHelper.getCorporateStructureDropDown("environmentSrch", environments, "", false, true);
    environmentSrch.setId("environmentSrch");
    form.addElement(environmentSrch);
    FormDropDownMenu formTypeSrch = new FormDropDownMenu("formTypeSrch", "", 
        "-1,pfm,bom", 
        "&nbsp;,PFM, BOM", false);
    formTypeSrch.setId("formTypeSrch");
    form.addElement(formTypeSrch);
    FormDropDownMenu releaseTypeSrch = new FormDropDownMenu("releaseTypeSrch", 
        "", 
        "-1,promo,commercial", 
        "&nbsp;,Promo, Commercial", false);
    releaseTypeSrch.setId("releaseTypeSrch");
    form.addElement(releaseTypeSrch);
    FormDropDownMenu productTypeSrch = new FormDropDownMenu("productTypeSrch", 
        "", 
        "-1,2,1,0", 
        "&nbsp;,Both, Physical, Digital", false);
    productTypeSrch.setId("productTypeSrch");
    form.addElement(productTypeSrch);
    context.putDelivery("Form", form);
    return context.includeJSP("blank-email-distribution-editor.jsp");
  }
  
  public static EmailDistribution getEmailDistribution(Context context) {
    EmailDistribution emailDist = null;
    int distributionId = -1;
    Notepad notepad = MilestoneHelper.getNotepadFromSession(
        20, context);
    if (context.getRequestValue("distribution-id") != null) {
      distributionId = Integer.parseInt(context.getRequestValue(
            "distribution-id"));
      emailDist = EmailDistributionManager.getInstance().getEmailDistribution(
          distributionId, true);
      notepad.setSelected(emailDist);
    } else if ((EmailDistribution)notepad.getSelected() != null) {
      EmailDistribution notepadEmailDist = (EmailDistribution)notepad
        .getSelected();
      distributionId = notepadEmailDist.getDistributionId();
      emailDist = EmailDistributionManager.getInstance().getEmailDistribution(
          distributionId, true);
      if (emailDist.getEmailDistributionDetail() == null)
        System.out.println(
            "<<< emailDist.getEmailDistributionDetail() == null "); 
    } else if (notepad.getAllContents() != null && 
      notepad.getAllContents().size() > 0) {
      emailDist = (EmailDistribution)notepad.getAllContents().get(0);
      notepad.setSelected(emailDist);
    } 
    return emailDist;
  }
  
  private boolean emailDistributionReleasingFamilySave(Dispatcher dispatcher, Context context) {
    EmailDistributionReleasingFamily.save(context);
    return emailDistributionEditor(context);
  }
  
  private boolean emailDistributionCopy(Context context) {
    EmailDistribution emailDist = getEmailDistribution(context);
    EmailDistribution copiedEmailDistObj = null;
    context.removeSessionValue("copiedEmailDistObj");
    try {
      copiedEmailDistObj = (EmailDistribution)emailDist.clone();
      context.putSessionValue("copiedEmailDistObj", copiedEmailDistObj);
      copiedEmailDistObj.setCopyDistributionId(copiedEmailDistObj.getDistributionId());
      copiedEmailDistObj.setDistributionId(-1);
      context.putSessionValue("emailDistribution", copiedEmailDistObj);
    } catch (CloneNotSupportedException cloneNotSupportedException) {}
    copiedEmailDistObj.setFirstName("");
    copiedEmailDistObj.setLastName("");
    copiedEmailDistObj.setEmail("");
    copiedEmailDistObj.setDistributionId(-1);
    Notepad notepad = getEmailDistributionNotepad(context);
    MilestoneHelper.putNotepadIntoSession(notepad, context);
    Form form = null;
    form = buildCopyForm(context, copiedEmailDistObj);
    form.addElement(new FormHidden("cmd", "email-distribution-copy"));
    context.putDelivery("Form", form);
    return context.includeJSP("email-distribution-editor.jsp");
  }
  
  private Form buildCopyForm(Context context, EmailDistribution emailDist) {
    context.putSessionValue("showAssigned", "NEW");
    Form emailDistForm = new Form(this.application, "emailDistributionForm", 
        this.application.getInfrastructure().getServletURL(), 
        "POST");
    FormTextField firstName = new FormTextField("firstName", 
        emailDist.getFirstName(), true, 
        30, 30);
    firstName.setTabIndex(1);
    emailDistForm.addElement(firstName);
    FormTextField lastName = new FormTextField("lastName", 
        emailDist.getLastName(), true, 
        30, 30);
    lastName.setTabIndex(2);
    emailDistForm.addElement(lastName);
    FormCheckBox inactive = new FormCheckBox("inactive", "Inactive", false, 
        !!emailDist.getInactive());
    inactive.setId("inactive");
    inactive.setTabIndex(3);
    emailDistForm.addElement(inactive);
    FormTextField email = new FormTextField("email", emailDist.getEmail(), true, 
        50, 50);
    email.addFormEvent("onfocus", "JavaScript:buildEmail()");
    email.addFormEvent("onblur", "JavaScript:isValidEmailAddr(this)");
    email.setTabIndex(4);
    emailDistForm.addElement(email);
    String westEast = "None";
    if (emailDist.getLabelDistribution() == 
      1)
      westEast = "East"; 
    if (emailDist.getLabelDistribution() == 0)
      westEast = "West"; 
    if (emailDist.getLabelDistribution() == 
      2)
      westEast = "Both"; 
    FormRadioButtonGroup distribution = new FormRadioButtonGroup("Distribution", 
        westEast, "West, East, Both, None", false);
    distribution.setTabIndex(5);
    emailDistForm.addElement(distribution);
    String productTypeStr = "Both";
    if (emailDist.getProductType() == 0)
      productTypeStr = "Digital"; 
    if (emailDist.getProductType() == 1)
      productTypeStr = "Physical"; 
    FormRadioButtonGroup productType = new FormRadioButtonGroup("ProductType", 
        productTypeStr, "Digital, Physical, Both", false);
    distribution.setTabIndex(6);
    emailDistForm.addElement(productType);
    FormCheckBox pfm = new FormCheckBox("PFM", "PFM", false, !!emailDist.getPfm());
    pfm.setId("pfm");
    pfm.setTabIndex(6);
    emailDistForm.addElement(pfm);
    FormCheckBox bom = new FormCheckBox("BOM", "BOM", false, !!emailDist.getBom());
    bom.setId("bom");
    bom.setTabIndex(7);
    emailDistForm.addElement(bom);
    FormCheckBox promo = new FormCheckBox("Promo", "Promo", false, 
        !!emailDist.getPromo());
    promo.setId("promo");
    promo.setTabIndex(8);
    emailDistForm.addElement(promo);
    FormCheckBox commercial = new FormCheckBox("Commercial", "Commercial", false, 
        !!emailDist.getCommercial());
    commercial.setId("commercial");
    commercial.setTabIndex(9);
    emailDistForm.addElement(commercial);
    Vector environments = Cache.getInstance().getEnvironments();
    int distributionId = emailDist.getDistributionId();
    Vector userEnvironments = null;
    userEnvironments = emailDist.getEmailDistributionDetail();
    for (int i = 0; i < environments.size(); i++) {
      Environment environment = (Environment)environments.elementAt(i);
      if (environment != null) {
        if (userEnvironments != null)
          for (int a = 0; a < userEnvironments.size(); a++) {
            EmailDistributionDetail emailDistDetail = 
              (EmailDistributionDetail)userEnvironments.elementAt(a);
            if (emailDistDetail != null && 
              emailDistDetail.getStructureId() == environment.getStructureID()) {
              FormCheckBox environmentCheckbox = new FormCheckBox("uc" + 
                  emailDistDetail.getStructureId(), 
                  "uc" + emailDistDetail.getStructureId(), environment.getName(), false, true);
              environmentCheckbox.setId("uc" + emailDistDetail.getStructureId());
              emailDistForm.addElement(environmentCheckbox);
            } 
          }  
        FormCheckBox environmentCheckbox = new FormCheckBox("uc" + 
            environment.getStructureID(), "uc" + environment.getStructureID(), 
            environment.getName(), false, false);
        environmentCheckbox.setId("uc" + environment.getStructureID());
        emailDistForm.addElement(environmentCheckbox);
      } 
    } 
    FormTextField lastUpdated = new FormTextField("lastUpdatedOn", false, 50);
    if (emailDist.getLastUpdateOn() != null)
      lastUpdated.setValue(MilestoneHelper.getLongDate(
            emailDist.getLastUpdateOn())); 
    emailDistForm.addElement(lastUpdated);
    FormTextField lastUpdatedBy = new FormTextField("lastUpdatedBy", false, 50);
    if (UserManager.getInstance().getUser(emailDist.getLastUpdateBy(), true) != null)
      lastUpdatedBy.setValue(
          UserManager.getInstance().getUser(emailDist.getLastUpdateBy(), true).getLogin()); 
    emailDistForm.addElement(lastUpdatedBy);
    FormTextField firstNameSrch = new FormTextField("firstNameSrch", "", false, 
        20);
    firstNameSrch.setId("firstNameSrch");
    emailDistForm.addElement(firstNameSrch);
    FormTextField lastNameSrch = new FormTextField("lastNameSrch", "", false, 
        20);
    lastNameSrch.setId("lastNameSrch");
    emailDistForm.addElement(lastNameSrch);
    FormDropDownMenu environmentSrch = 
      MilestoneHelper.getCorporateStructureDropDown("environmentSrch", environments, "", false, true);
    environmentSrch.setId("environmentSrch");
    emailDistForm.addElement(environmentSrch);
    FormDropDownMenu formTypeSrch = new FormDropDownMenu("formTypeSrch", "", 
        "-1,pfm,bom", 
        "&nbsp;,PFM, BOM", false);
    formTypeSrch.setId("formTypeSrch");
    emailDistForm.addElement(formTypeSrch);
    FormDropDownMenu releaseTypeSrch = new FormDropDownMenu("releaseTypeSrch", 
        "", 
        "-1,promo,commercial", 
        "&nbsp;,Promo, Commercial", false);
    releaseTypeSrch.setId("releaseTypeSrch");
    emailDistForm.addElement(releaseTypeSrch);
    FormDropDownMenu productTypeSrch = new FormDropDownMenu("productTypeSrch", 
        "", 
        "-1,2,1,0", 
        "&nbsp;,Both ,Physical, Digital", false);
    productTypeSrch.setId("productTypeSrch");
    emailDistForm.addElement(productTypeSrch);
    emailDistForm.addElement(new FormHidden("cmd", "email-distribution-copy"));
    context.putDelivery("Form", emailDistForm);
    if (context.getSessionValue("NOTEPAD_EMAIL_DISTRIBUTION_VISIBLE") != null)
      context.putDelivery("isNotePadVisible", (Boolean)context.getSessionValue("NOTEPAD_EMAIL_DISTRIBUTION_VISIBLE")); 
    return emailDistForm;
  }
}


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\EmailDistributionHandler.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */