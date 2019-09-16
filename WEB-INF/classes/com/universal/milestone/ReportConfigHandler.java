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
import com.universal.milestone.Form;
import com.universal.milestone.MilestoneConstants;
import com.universal.milestone.MilestoneHelper;
import com.universal.milestone.MilestoneHelper_2;
import com.universal.milestone.MilestoneMessage;
import com.universal.milestone.MilestoneSecurity;
import com.universal.milestone.Notepad;
import com.universal.milestone.Report;
import com.universal.milestone.ReportConfigHandler;
import com.universal.milestone.ReportConfigManager;
import com.universal.milestone.SecureHandler;
import com.universal.milestone.User;
import com.universal.milestone.UserManager;
import java.util.Vector;

public class ReportConfigHandler extends SecureHandler {
  public static final String COMPONENT_CODE = "hRpC";
  
  public GeminiApplication application;
  
  public ComponentLog log;
  
  public ReportConfigHandler(GeminiApplication application) {
    this.application = application;
    this.log = application.getLog("hRpC");
  }
  
  public String getDescription() { return "Report Config Handler"; }
  
  public boolean acceptRequest(Dispatcher dispatcher, Context context, String command) {
    if (super.acceptRequest(dispatcher, context, command))
      if (command.startsWith("report-config"))
        return handleRequest(dispatcher, context, command);  
    return false;
  }
  
  public boolean handleRequest(Dispatcher dispatcher, Context context, String command) {
    if (command.equalsIgnoreCase("report-config-search"))
      search(dispatcher, context, command); 
    if (command.equalsIgnoreCase("report-config-sort")) {
      sort(dispatcher, context);
    } else if (command.equalsIgnoreCase("report-config-editor")) {
      edit(context);
    } else if (command.equalsIgnoreCase("report-config-edit-save")) {
      saveEdit(context);
    } else if (command.equalsIgnoreCase("report-config-edit-save-new")) {
      saveNew(context);
    } else if (command.equalsIgnoreCase("report-config-edit-delete")) {
      delete(context);
    } else if (command.equalsIgnoreCase("report-config-edit-new")) {
      editNew(context);
    } 
    return true;
  }
  
  private boolean search(Dispatcher dispatcher, Context context, String command) {
    Notepad notepad = MilestoneHelper.getNotepadFromSession(17, context);
    notepad.setAllContents(null);
    notepad.setSelected(null);
    ReportConfigManager.getInstance().setNotepadQuery(context, MilestoneSecurity.getUser(context).getUserId(), notepad);
    dispatcher.redispatch(context, "report-config-editor");
    return true;
  }
  
  private boolean sort(Dispatcher dispatcher, Context context) {
    int sort = Integer.parseInt(context.getParameter("OrderBy"));
    Notepad notepad = MilestoneHelper.getNotepadFromSession(17, context);
    if (notepad.getSearchQuery() == null || notepad.getSearchQuery().equals("")) {
      ReportConfigManager.getInstance();
      notepad.setSearchQuery("SELECT DISTINCT report_id, report_name, file_name, description, report_status, report_owner FROM vi_Report_Config");
    } 
    notepad.setOrderBy(" ORDER BY " + MilestoneConstants.SORT_REPORT_CONFIG[sort]);
    notepad.setAllContents(null);
    notepad = getReportConfigNotepad(context, MilestoneSecurity.getUser(context).getUserId());
    notepad.setSelected(notepad.getSelected());
    dispatcher.redispatch(context, "report-config-editor");
    return true;
  }
  
  public Notepad getReportConfigNotepad(Context context, int userId) {
    Vector contents = new Vector();
    if (MilestoneHelper.getNotepadFromSession(17, context) != null) {
      Notepad notepad = MilestoneHelper.getNotepadFromSession(17, context);
      if (notepad.getAllContents() == null) {
        contents = ReportConfigManager.getInstance().getReportConfigNotepadList(notepad);
        notepad.setAllContents(contents);
      } 
      return notepad;
    } 
    String[] columnNames = { "Name", "File Name" };
    contents = ReportConfigManager.getInstance().getReportConfigNotepadList(null);
    return new Notepad(contents, 0, 7, "Reports", 17, columnNames);
  }
  
  private boolean edit(Context context) {
    User user = MilestoneSecurity.getUser(context);
    Notepad notepad = getReportConfigNotepad(context, user.getUserId());
    MilestoneHelper.putNotepadIntoSession(notepad, context);
    Report reportConfig = MilestoneHelper.getScreenReportConfig(context);
    if (reportConfig != null) {
      Form reportConfigForm = buildForm(context, reportConfig);
      context.putDelivery("Form", reportConfigForm);
      return context.includeJSP("report-config-editor.jsp");
    } 
    return goToBlank(context);
  }
  
  private boolean saveEdit(Context context) {
    User user = MilestoneSecurity.getUser(context);
    Notepad notepad = getReportConfigNotepad(context, user.getUserId());
    MilestoneHelper.putNotepadIntoSession(notepad, context);
    Report reportConfig = MilestoneHelper.getScreenReportConfig(context);
    Form form = buildForm(context, reportConfig);
    if (ReportConfigManager.getInstance().isTimestampValid(reportConfig)) {
      form.setValues(context);
      String description = form.getStringValue("Description");
      String reportName = form.getStringValue("ReportName");
      String subName = form.getStringValue("SubName");
      String reportType = form.getStringValue("ReportType");
      String reportFormat = form.getStringValue("ReportFormat").trim();
      String reportStatus = form.getStringValue("ReportStatus").trim();
      String path = form.getStringValue("Path");
      String fileName = form.getStringValue("FileName");
      String subReportName = form.getStringValue("SubRepName");
      String uml = form.getStringValue("UML");
      String uml2 = form.getStringValue("UML2");
      String uml3 = form.getStringValue("UML3");
      String pub = form.getStringValue("PUB");
      reportConfig.setDescription(description);
      reportConfig.setReportName(reportName);
      int type = -1;
      try {
        type = Integer.parseInt(reportType);
      } catch (NumberFormatException e) {
        System.out.println("Error converting the Report Type to integer.");
        type = 0;
      } 
      reportConfig.setType(type);
      reportConfig.setPath(path);
      reportConfig.setFileName(fileName);
      reportConfig.setSubName(subReportName);
      reportConfig.setReportStatus(reportStatus);
      int format = -1;
      try {
        format = Integer.parseInt(reportFormat);
      } catch (NumberFormatException e) {
        format = 0;
      } 
      reportConfig.setFormat(format);
      String reportOwner = form.getStringValue("ReportOwner");
      int owner = -1;
      try {
        owner = Integer.parseInt(reportOwner);
      } catch (NumberFormatException e) {
        System.out.println("Error converting the Report Owner to integer.");
        owner = 0;
      } 
      reportConfig.setReportOwner(owner);
      reportConfig.setBeginDateFlag(((FormCheckBox)form.getElement("BeginDate")).isChecked());
      reportConfig.setEndDateFlag(((FormCheckBox)form.getElement("EndDate")).isChecked());
      reportConfig.setRegionFlag(((FormCheckBox)form.getElement("Region")).isChecked());
      reportConfig.setFamilyFlag(((FormCheckBox)form.getElement("Family")).isChecked());
      reportConfig.setEnvironmentFlag(((FormCheckBox)form.getElement("Environment")).isChecked());
      reportConfig.setCompanyFlag(((FormCheckBox)form.getElement("Company")).isChecked());
      reportConfig.setLabelFlag(((FormCheckBox)form.getElement("Label")).isChecked());
      reportConfig.setContactFlag(((FormCheckBox)form.getElement("LabelContact")).isChecked());
      reportConfig.setCompleteKeyTaskFlag(((FormCheckBox)form.getElement("CompleteKeyTask")).isChecked());
      reportConfig.setParentsOnlyFlag(((FormCheckBox)form.getElement("ParentsOnly")).isChecked());
      reportConfig.setKeyTaskFlag(((FormCheckBox)form.getElement("UMLKeyTask")).isChecked());
      reportConfig.setReleaseTypeFlag(((FormCheckBox)form.getElement("ReleaseType")).isChecked());
      reportConfig.setArtistFlag(((FormCheckBox)form.getElement("Artist")).isChecked());
      reportConfig.setStatusFlag(((FormCheckBox)form.getElement("Status")).isChecked());
      reportConfig.setUmlKeyTaskFlag(((FormCheckBox)form.getElement("TaskOwner")).isChecked());
      reportConfig.setFuture1Flag(((FormCheckBox)form.getElement("UmlContact")).isChecked());
      reportConfig.setFuture2Flag(((FormCheckBox)form.getElement("Future2")).isChecked());
      reportConfig.setBeginDueDateFlag(((FormCheckBox)form.getElement("BeginDueDate")).isChecked());
      reportConfig.setEndDueDateFlag(((FormCheckBox)form.getElement("EndDueDate")).isChecked());
      reportConfig.setBeginEffectiveDateFlag(((FormCheckBox)form.getElement("BeginEffectiveDate")).isChecked());
      reportConfig.setEndEffectiveDateFlag(((FormCheckBox)form.getElement("EndEffectiveDate")).isChecked());
      reportConfig.setConfiguration(((FormCheckBox)form.getElement("Configuration")).isChecked());
      reportConfig.setScheduledReleasesFlag(((FormCheckBox)form.getElement("ScheduledReleases")).isChecked());
      reportConfig.setAddsMovesBoth(((FormCheckBox)form.getElement("AddsMovesBoth")).isChecked());
      reportConfig.setSubconfigFlag(((FormCheckBox)form.getElement("SubConfiguration")).isChecked());
      reportConfig.setPhysicalProductActivity(((FormCheckBox)form.getElement("PhysicalProductActivity")).isChecked());
      reportConfig.setDistCo(((FormCheckBox)form.getElement("DistCo")).isChecked());
      String productTypeStr = ((FormRadioButtonGroup)form.getElement("ProductType")).getStringValue();
      int productTypeInt = 0;
      if (productTypeStr.equals("Physical")) {
        productTypeInt = 0;
      } else if (productTypeStr.equals("Digital")) {
        productTypeInt = 1;
      } else if (productTypeStr.equals("Both")) {
        productTypeInt = 2;
      } 
      reportConfig.setProductType(productTypeInt);
      if (!form.isUnchanged()) {
        FormValidation formValidation = form.validate();
        if (formValidation.isGood()) {
          Report savedReportConfig = ReportConfigManager.getInstance().saveReportConfig(reportConfig, user.getUserId());
          FormElement lastUpdated = form.getElement("lastupdateddate");
          lastUpdated.setValue(MilestoneHelper.getLongDate(savedReportConfig.getLastUpdatedDate()));
          notepad.setAllContents(null);
          notepad = getReportConfigNotepad(context, MilestoneSecurity.getUser(context).getUserId());
          notepad.goToSelectedPage();
          notepad.setSelected(savedReportConfig);
          reportConfig = (Report)notepad.validateSelected();
          context.putSessionValue("ReportConfig", reportConfig);
          if (reportConfig == null)
            return goToBlank(context); 
          form = buildForm(context, reportConfig);
        } else {
          context.putDelivery("FormValidation", formValidation);
        } 
      } 
    } else {
      context.putDelivery("AlertMessage", "The record is unavailable for editing at this time.  Please refresh your screen and edit it at a later time.");
    } 
    Notepad reportNotepad = MilestoneHelper.getNotepadFromSession(3, context);
    if (reportNotepad != null) {
      reportNotepad.setAllContents(null);
      reportNotepad.setSelected(null);
    } 
    context.putDelivery("Form", form);
    return context.includeJSP("report-config-editor.jsp");
  }
  
  private boolean delete(Context context) {
    User user = MilestoneSecurity.getUser(context);
    Notepad notepad = getReportConfigNotepad(context, user.getUserId());
    MilestoneHelper.putNotepadIntoSession(notepad, context);
    Report reportConfig = MilestoneHelper.getScreenReportConfig(context);
    if (reportConfig != null) {
      ReportConfigManager.getInstance().deleteReportConfig(reportConfig, user.getUserId());
      notepad.setAllContents(null);
      notepad = getReportConfigNotepad(context, MilestoneSecurity.getUser(context).getUserId());
      notepad.setSelected(null);
      Notepad reportNotepad = MilestoneHelper.getNotepadFromSession(3, context);
      if (reportNotepad != null) {
        reportNotepad.setAllContents(null);
        reportNotepad.setSelected(null);
      } 
    } 
    return edit(context);
  }
  
  protected Form buildForm(Context context, Report reportConfig) {
    Form reportConfigForm = new Form(this.application, "reportConfigForm", 
        this.application.getInfrastructure().getServletURL(), "POST");
    if (reportConfig != null) {
      String reportName = "";
      if (reportConfig.getReportName() != null)
        reportName = reportConfig.getReportName(); 
      FormTextField ReportName = new FormTextField("ReportName", reportName, true, 50, 50);
      reportConfigForm.addElement(ReportName);
      String subName = "";
      if (reportConfig.getReportName() != null)
        subName = reportConfig.getSubName(); 
      FormTextField SubName = new FormTextField("SubName", subName, false, 50, 50);
      reportConfigForm.addElement(SubName);
      String descriptionValue = "";
      if (reportConfig.getDescription() != null)
        descriptionValue = reportConfig.getDescription(); 
      FormTextField Description = new FormTextField("Description", descriptionValue, false, 50, 50);
      Description.setTabIndex(1);
      reportConfigForm.addElement(Description);
      int reportType = 0;
      reportType = reportConfig.getType();
      FormDropDownMenu ReportType = MilestoneHelper.getLookupDropDown("ReportType", Cache.getReportConfigTypes(), 
          "0" + String.valueOf(reportType), true, false);
      ReportType.setTabIndex(2);
      reportConfigForm.addElement(ReportType);
      int reportFormat = 0;
      reportFormat = reportConfig.getFormat();
      FormDropDownMenu ReportFormat = MilestoneHelper.getLookupDropDown("ReportFormat", Cache.getReportConfigFormats(), 
          "0" + String.valueOf(reportFormat), true, false);
      ReportFormat.setTabIndex(3);
      reportConfigForm.addElement(ReportFormat);
      String reportStatus = "";
      if (reportConfig != null && reportConfig.getReportStatus() != null)
        reportStatus = reportConfig.getReportStatus(); 
      FormDropDownMenu ReportStatus = MilestoneHelper.getLookupDropDown("ReportStatus", Cache.getReportConfigStatuses(), 
          String.valueOf(reportStatus), true, false);
      ReportStatus.setTabIndex(4);
      reportConfigForm.addElement(ReportStatus);
      String reportPath = "";
      if (reportConfig.getPath() != null)
        reportPath = reportConfig.getPath(); 
      FormTextField Path = new FormTextField("Path", reportPath, false, 80, 255);
      Path.setTabIndex(5);
      reportConfigForm.addElement(Path);
      String fileName = "";
      if (reportConfig.getFileName() != null)
        fileName = reportConfig.getFileName(); 
      FormTextField FileName = new FormTextField("FileName", fileName, false, 50, 50);
      FileName.setTabIndex(6);
      reportConfigForm.addElement(FileName);
      String subRepName = "";
      if (reportConfig.getSubName() != null && !reportConfig.getSubName().equals("[none]"))
        subRepName = reportConfig.getSubName(); 
      FormTextField SubRepName = new FormTextField("SubRepName", subRepName, false, 50, 50);
      SubRepName.setTabIndex(7);
      reportConfigForm.addElement(SubRepName);
      boolean beginDate = false;
      beginDate = reportConfig.getBeginDateFlag();
      FormCheckBox BeginDate = new FormCheckBox("BeginDate", "BeginDate", "Begin Date", false);
      BeginDate.setChecked(beginDate);
      BeginDate.setId("BeginDate");
      reportConfigForm.addElement(BeginDate);
      boolean endDate = false;
      endDate = reportConfig.getEndDateFlag();
      FormCheckBox EndDate = new FormCheckBox("EndDate", "EndDate", "End Date", false);
      EndDate.setChecked(endDate);
      EndDate.setId("EndDate");
      reportConfigForm.addElement(EndDate);
      boolean region = false;
      region = reportConfig.getRegionFlag();
      FormCheckBox Region = new FormCheckBox("Region", "Region", "Region", false);
      Region.setChecked(region);
      Region.setId("Region");
      reportConfigForm.addElement(Region);
      boolean family = false;
      family = reportConfig.getFamilyFlag();
      FormCheckBox Family = new FormCheckBox("Family", "Family", "Family", false);
      Family.setChecked(family);
      Family.setId("Family");
      reportConfigForm.addElement(Family);
      Vector families = Cache.getInstance().getFamilies();
      FormDropDownMenu UML = MilestoneHelper.getCorporateStructureDropDown("UML", families, "0", true, true);
      reportConfigForm.addElement(UML);
      boolean environment = false;
      environment = reportConfig.getEnvironmentFlag();
      FormCheckBox Environment = new FormCheckBox("Environment", "Environment", "Environment", false);
      Environment.setChecked(environment);
      Environment.setId("Environment");
      reportConfigForm.addElement(Environment);
      Vector environments = Cache.getInstance().getEnvironments();
      FormDropDownMenu UML3 = MilestoneHelper.getCorporateStructureDropDown("UML3", environments, "0", true, true);
      reportConfigForm.addElement(UML3);
      boolean company = false;
      company = reportConfig.getCompanyFlag();
      FormCheckBox Company = new FormCheckBox("Company", "Company", "Company", false);
      Company.setChecked(company);
      Company.setId("Company");
      reportConfigForm.addElement(Company);
      Vector companies = Cache.getCompanies();
      FormDropDownMenu UML2 = MilestoneHelper.getCorporateStructureDropDown("UML2", companies, "0", true, true);
      reportConfigForm.addElement(UML2);
      boolean label = false;
      label = reportConfig.getLabelFlag();
      FormCheckBox Label = new FormCheckBox("Label", "Label", "Label", false);
      Label.setChecked(label);
      Label.setId("Label");
      reportConfigForm.addElement(Label);
      Vector labels = Cache.getLabels();
      FormDropDownMenu PUB = MilestoneHelper.getCorporateStructureDropDown("PUB", labels, "0", true, true);
      reportConfigForm.addElement(PUB);
      boolean contact = false;
      contact = reportConfig.getContactFlag();
      FormCheckBox Contact = new FormCheckBox("LabelContact", "LabelContact", "Label Contact", false);
      Contact.setChecked(contact);
      Contact.setId("LabelContact");
      reportConfigForm.addElement(Contact);
      Vector contactVector = ReportConfigManager.getLabelContacts();
      String contactList = "&nbsp;,";
      for (int i = 0; i < contactVector.size(); i++) {
        User userContact = (User)contactVector.get(i);
        if (i < contactVector.size() - 1) {
          if (userContact != null)
            contactList = String.valueOf(contactList) + userContact.getName() + ","; 
        } else if (userContact != null) {
          contactList = String.valueOf(contactList) + userContact.getName();
        } 
      } 
      FormDropDownMenu ContactList = new FormDropDownMenu("LabelContactDropDown", "All", contactList, false);
      reportConfigForm.addElement(ContactList);
      boolean completeKeyTask = false;
      completeKeyTask = reportConfig.getCompleteKeyTaskFlag();
      FormCheckBox CompleteKeyTask = new FormCheckBox("CompleteKeyTask", "CompleteKeyTask", "Completed Task", false);
      CompleteKeyTask.setChecked(completeKeyTask);
      CompleteKeyTask.setId("CompleteKeyTask");
      reportConfigForm.addElement(CompleteKeyTask);
      boolean parentsOnly = false;
      parentsOnly = reportConfig.getParentsOnlyFlag();
      FormCheckBox ParentsOnly = new FormCheckBox("ParentsOnly", "ParentsOnly", "Parents Only", false);
      ParentsOnly.setChecked(parentsOnly);
      ParentsOnly.setId("ParentsOnly");
      reportConfigForm.addElement(ParentsOnly);
      boolean umlKeyTask = false;
      umlKeyTask = reportConfig.getKeyTaskFlag();
      FormCheckBox UMLKeyTask = new FormCheckBox("UMLKeyTask", "UMLKeyTask", "UMLKeyTask", false);
      UMLKeyTask.setChecked(umlKeyTask);
      UMLKeyTask.setId("UMLKeyTask");
      reportConfigForm.addElement(UMLKeyTask);
      boolean releaseType = false;
      releaseType = reportConfig.getReleaseTypeFlag();
      FormCheckBox ReleaseType = new FormCheckBox("ReleaseType", "ReleaseType", "ReleaseType", false);
      ReleaseType.setChecked(releaseType);
      ReleaseType.setId("ReleaseType");
      reportConfigForm.addElement(ReleaseType);
      boolean status = false;
      status = reportConfig.getStatusFlag();
      FormCheckBox Status = new FormCheckBox("Status", "Status", "Status", false);
      Status.setChecked(status);
      Status.setId("Status");
      reportConfigForm.addElement(Status);
      boolean artist = false;
      artist = reportConfig.getArtistFlag();
      FormCheckBox Artist = new FormCheckBox("Artist", "Artist", "Artist", false);
      Artist.setChecked(artist);
      Artist.setId("Artist");
      reportConfigForm.addElement(Artist);
      boolean taskOwner = false;
      taskOwner = reportConfig.getUmlKeyTaskFlag();
      FormCheckBox TaskOwner = new FormCheckBox("TaskOwner", "TaskOwner", "Task Owner", false);
      TaskOwner.setChecked(taskOwner);
      TaskOwner.setId("TaskOwner");
      reportConfigForm.addElement(TaskOwner);
      boolean future1 = false;
      future1 = reportConfig.getFuture1Flag();
      FormCheckBox Fut1 = new FormCheckBox("UmlContact", "UmlContact", "Uml Contact", false);
      Fut1.setChecked(future1);
      Fut1.setId("UmlContact");
      reportConfigForm.addElement(Fut1);
      Vector contactVector1 = ReportConfigManager.getUmlContacts();
      String contactList1 = "&nbsp;,";
      for (int i = 0; i < contactVector1.size(); i++) {
        User userContact1 = (User)contactVector1.get(i);
        if (i < contactVector1.size() - 1) {
          if (userContact1 != null)
            contactList1 = String.valueOf(contactList1) + userContact1.getName() + ","; 
        } else if (userContact1 != null) {
          contactList1 = String.valueOf(contactList1) + userContact1.getName();
        } 
      } 
      FormDropDownMenu ContactList1 = new FormDropDownMenu("UmlContactDropDown", "All", contactList1, false);
      reportConfigForm.addElement(ContactList1);
      boolean future2 = false;
      future2 = reportConfig.getFuture2Flag();
      FormCheckBox Future2 = new FormCheckBox("Future2", "Future2", "Future2", false);
      Future2.setChecked(future2);
      Future2.setId("Future2");
      reportConfigForm.addElement(Future2);
      boolean beginDueDate = false;
      beginDueDate = reportConfig.getBeginDueDateFlag();
      FormCheckBox BeginDueDate = new FormCheckBox("BeginDueDate", "BeginDueDate", "Begin Due Date", false);
      BeginDueDate.setChecked(beginDueDate);
      BeginDueDate.setId("BeginDueDate");
      reportConfigForm.addElement(BeginDueDate);
      boolean endDueDate = false;
      endDueDate = reportConfig.getEndDueDateFlag();
      FormCheckBox EndDueDate = new FormCheckBox("EndDueDate", "EndDueDate", "End Due Date", false);
      EndDueDate.setChecked(endDueDate);
      EndDueDate.setId("EndDueDate");
      reportConfigForm.addElement(EndDueDate);
      FormTextField lastUpdated = new FormTextField("lastupdateddate", false, 50);
      if (reportConfig.getLastUpdatedDate() != null)
        lastUpdated.setValue(MilestoneHelper.getCustomFormatedDate(reportConfig.getLastUpdatedDate(), "M/d/yyyy hh:mm:ss a 'ET'")); 
      reportConfigForm.addElement(lastUpdated);
      FormTextField lastUpdatedBy = new FormTextField("lastUpdatedBy", false, 50);
      if (UserManager.getInstance().getUser(reportConfig.getLastUpdatingUser()) != null)
        lastUpdatedBy.setValue(UserManager.getInstance().getUser(reportConfig.getLastUpdatingUser()).getName()); 
      reportConfigForm.addElement(lastUpdatedBy);
      boolean beginEffectiveDate = false;
      beginEffectiveDate = reportConfig.getBeginEffectiveDateFlag();
      FormCheckBox BeginEffectiveDate = new FormCheckBox("BeginEffectiveDate", "BeginEffectiveDate", "Begin Effective Date", false);
      BeginEffectiveDate.setChecked(beginEffectiveDate);
      BeginEffectiveDate.setId("BeginEffectiveDate");
      reportConfigForm.addElement(BeginEffectiveDate);
      boolean endEffectiveDate = false;
      endEffectiveDate = reportConfig.getEndEffectiveDateFlag();
      FormCheckBox EndEffectiveDate = new FormCheckBox("EndEffectiveDate", "EndEffectiveDate", "Ending Effective Date", false);
      EndEffectiveDate.setChecked(endEffectiveDate);
      EndEffectiveDate.setId("EndEffectiveDate");
      reportConfigForm.addElement(EndEffectiveDate);
      Vector ownerFamilies = Cache.getInstance().getFamilies();
      String ownerId = Integer.toString(reportConfig.getReportOwner(), 0);
      FormDropDownMenu ReportOwner = MilestoneHelper.getCorporateStructureDropDown("ReportOwner", ownerFamilies, ownerId, true, true);
      reportConfigForm.addElement(ReportOwner);
      boolean configuration = false;
      configuration = reportConfig.getConfiguration();
      FormCheckBox Config = new FormCheckBox("Configuration", "Configuration", "Format/Schedule Type", false);
      Config.setChecked(configuration);
      Config.addFormEvent("onClick", "isSubconfigSelected();");
      Config.setId("Configuration");
      reportConfigForm.addElement(Config);
      FormDropDownMenu configList = MilestoneHelper.getSelectionConfigurationDropDown("configurationList", "", false);
      reportConfigForm.addElement(configList);
      boolean subconfiguration = false;
      subconfiguration = reportConfig.getSubconfigFlag();
      FormCheckBox SubConfig = new FormCheckBox("Subconfiguration", "Subconfiguration", "Sub-Format", false);
      SubConfig.addFormEvent("onClick", "isConfigSelected();");
      SubConfig.setChecked(subconfiguration);
      SubConfig.setId("Subconfiguration");
      reportConfigForm.addElement(SubConfig);
      boolean scheduledReleases = false;
      scheduledReleases = reportConfig.getScheduledReleasesFlag();
      FormCheckBox ScheduledReleases = new FormCheckBox("ScheduledReleases", "ScheduledReleases", "Scheduled Releases", false);
      ScheduledReleases.setChecked(scheduledReleases);
      ScheduledReleases.setId("ScheduledReleases");
      reportConfigForm.addElement(ScheduledReleases);
      boolean addsMovesBoth = false;
      addsMovesBoth = reportConfig.getAddsMovesBoth();
      FormCheckBox AddsMovesBoth = new FormCheckBox("AddsMovesBoth", "AddsMovesBoth", "Adds Moves Both", false);
      AddsMovesBoth.setChecked(addsMovesBoth);
      AddsMovesBoth.setId("AddsMovesBoth");
      reportConfigForm.addElement(AddsMovesBoth);
      boolean physicalActivity = false;
      physicalActivity = reportConfig.getPhysicalProductActivity();
      FormCheckBox PhysicalProductActivity = new FormCheckBox("PhysicalProductActivity", 
          "PhysicalProductActivity", "Physical Product Activity", false);
      PhysicalProductActivity.setChecked(physicalActivity);
      PhysicalProductActivity.setId("PhysicalProductActivity");
      reportConfigForm.addElement(PhysicalProductActivity);
      boolean distCo = false;
      distCo = reportConfig.getDistCo();
      FormCheckBox DistCo = new FormCheckBox("DistCo", "DistCo", "Distribution Company", false);
      DistCo.setChecked(distCo);
      DistCo.setId("DistCo");
      reportConfigForm.addElement(DistCo);
      reportConfigForm.addElement(MilestoneHelper_2.BuildDistCoDropDown());
      int productType = reportConfig.getProductType();
      String productTypeStr = "";
      if (productType == 0) {
        productTypeStr = "Physical";
      } else if (productType == 1) {
        productTypeStr = "Digital";
      } else if (productType == 2) {
        productTypeStr = "Both";
      } 
      FormRadioButtonGroup ProductType = new FormRadioButtonGroup("ProductType", productTypeStr, "Physical,Digital,Both", false);
      ProductType.setId("ProductType");
      reportConfigForm.addElement(ProductType);
    } else {
      FormTextField ReportName = new FormTextField("ReportName", "", true, 50, 50);
      reportConfigForm.addElement(ReportName);
      FormTextField SubName = new FormTextField("SubName", "", true, 50, 50);
      reportConfigForm.addElement(SubName);
      FormTextField Description = new FormTextField("Description", "", true, 50, 50);
      Description.setTabIndex(1);
      reportConfigForm.addElement(Description);
      FormDropDownMenu ReportType = MilestoneHelper.getLookupDropDown("ReportType", Cache.getReportConfigTypes(), "", true, true);
      ReportType.setTabIndex(2);
      reportConfigForm.addElement(ReportType);
      FormDropDownMenu ReportFormat = MilestoneHelper.getLookupDropDown("ReportFormat", Cache.getReportConfigFormats(), "", true, true);
      ReportFormat.setTabIndex(3);
      reportConfigForm.addElement(ReportFormat);
      FormDropDownMenu ReportStatus = MilestoneHelper.getLookupDropDown("ReportStatus", Cache.getReportConfigStatuses(), "", true, true);
      ReportStatus.setTabIndex(4);
      reportConfigForm.addElement(ReportStatus);
      FormTextField Path = new FormTextField("Path", "", true, 80, 255);
      Path.setTabIndex(5);
      reportConfigForm.addElement(Path);
      FormTextField FileName = new FormTextField("FileName", "", true, 50, 50);
      FileName.setTabIndex(6);
      reportConfigForm.addElement(FileName);
      FormCheckBox BeginDate = new FormCheckBox("BeginDate", "BeginDate", "BeginDate", false);
      BeginDate.setId("BeginDate");
      reportConfigForm.addElement(BeginDate);
      FormCheckBox EndDate = new FormCheckBox("EndDate", "EndDate", "EndDate", false);
      EndDate.setId("EndDate");
      reportConfigForm.addElement(EndDate);
      FormCheckBox Region = new FormCheckBox("Region", "Region", "Region", false);
      Region.setId("Region");
      reportConfigForm.addElement(Region);
      FormCheckBox Family = new FormCheckBox("Family", "Family", "Family", false);
      Family.setId("Family");
      reportConfigForm.addElement(Family);
      Vector families = Cache.getInstance().getFamilies();
      FormDropDownMenu UML = MilestoneHelper.getCorporateStructureDropDown("UML", families, "0", true, true);
      reportConfigForm.addElement(UML);
      FormCheckBox Environment = new FormCheckBox("Environment", "Environment", "Environment", false);
      Environment.setId("Environment");
      reportConfigForm.addElement(Environment);
      Vector environments = Cache.getInstance().getEnvironments();
      FormDropDownMenu UML3 = MilestoneHelper.getCorporateStructureDropDown("UML3", environments, "0", true, true);
      reportConfigForm.addElement(UML3);
      FormCheckBox Company = new FormCheckBox("Company", "Company", "Company", false);
      Company.setId("Company");
      reportConfigForm.addElement(Company);
      Vector companies = Cache.getCompanies();
      FormDropDownMenu UML2 = MilestoneHelper.getCorporateStructureDropDown("UML2", companies, "0", true, true);
      reportConfigForm.addElement(UML2);
      FormCheckBox Label = new FormCheckBox("Label", "Label", "Label", false);
      Label.setId("Label");
      reportConfigForm.addElement(Label);
      Vector labels = Cache.getLabels();
      FormDropDownMenu PUB = MilestoneHelper.getCorporateStructureDropDown("PUB", labels, "0", true, true);
      reportConfigForm.addElement(PUB);
      FormCheckBox Contact = new FormCheckBox("LabelContact", "LabelContact", "Label Contact", false);
      Contact.setId("LabelContact");
      reportConfigForm.addElement(Contact);
      Vector contactVector = ReportConfigManager.getLabelContacts();
      String contactList = "&nbsp;,";
      for (int i = 0; i < contactVector.size(); i++) {
        User userContact = (User)contactVector.get(i);
        if (i < contactVector.size() - 1) {
          if (userContact != null)
            contactList = String.valueOf(contactList) + userContact.getName() + ","; 
        } else if (userContact != null) {
          contactList = String.valueOf(contactList) + userContact.getName();
        } 
      } 
      FormDropDownMenu ContactList = new FormDropDownMenu("LabelContactDropDown", "All", contactList, false);
      reportConfigForm.addElement(ContactList);
      FormCheckBox CompleteKeyTask = new FormCheckBox("CompleteKeyTask", "CompleteKeyTask", "Completed Task", false);
      CompleteKeyTask.setId("CompleteKeyTask");
      reportConfigForm.addElement(CompleteKeyTask);
      FormCheckBox ParentsOnly = new FormCheckBox("ParentsOnly", "ParentsOnly", "Parents Only", false);
      ParentsOnly.setId("ParentsOnly");
      reportConfigForm.addElement(ParentsOnly);
      FormCheckBox UMLKeyTask = new FormCheckBox("UMLKeyTask", "UMLKeyTask", "UML Key Task", false);
      UMLKeyTask.setId("UMLKeyTask");
      reportConfigForm.addElement(UMLKeyTask);
      FormCheckBox ReleaseType = new FormCheckBox("ReleaseType", "ReleaseType", "ReleaseType", false);
      ReleaseType.setId("ReleaseType");
      reportConfigForm.addElement(ReleaseType);
      FormCheckBox Status = new FormCheckBox("Status", "Status", "Status", false);
      Status.setId("Status");
      reportConfigForm.addElement(Status);
      FormCheckBox Artist = new FormCheckBox("Artist", "Artist", "Artist", false);
      Artist.setId("Artist");
      reportConfigForm.addElement(Artist);
      FormCheckBox UMLDates = new FormCheckBox("TaskOwner", "TaskOwner", "Task Owner", false);
      UMLDates.setId("UMLDates");
      reportConfigForm.addElement(UMLDates);
      FormCheckBox Fut1 = new FormCheckBox("UmlContact", "UmlContact", "Uml Contact", false);
      Fut1.setId("UmlContact");
      reportConfigForm.addElement(Fut1);
      Vector contactVector1 = ReportConfigManager.getUmlContacts();
      String contactList1 = "&nbsp;,";
      for (int i = 0; i < contactVector1.size(); i++) {
        User userContact1 = (User)contactVector1.get(i);
        if (i < contactVector1.size() - 1) {
          if (userContact1 != null)
            contactList1 = String.valueOf(contactList1) + userContact1.getName() + ","; 
        } else if (userContact1 != null) {
          contactList1 = String.valueOf(contactList1) + userContact1.getName();
        } 
      } 
      FormDropDownMenu ContactList1 = new FormDropDownMenu("UmlContactDropDown", "All", contactList1, false);
      reportConfigForm.addElement(ContactList1);
      FormCheckBox Future2 = new FormCheckBox("Future2", "Future2", "Future2", false);
      Future2.setId("Future2");
      reportConfigForm.addElement(Future2);
      FormCheckBox BeginDueDate = new FormCheckBox("BeginDueDate", "BeginDueDate", "Begin Due Date", false);
      BeginDueDate.setId("BeginDueDate");
      reportConfigForm.addElement(BeginDueDate);
      FormCheckBox EndDueDate = new FormCheckBox("EndDueDate", "EndDueDate", "End Due Date", false);
      EndDueDate.setId("EndDueDate");
      reportConfigForm.addElement(EndDueDate);
      FormTextField lastUpdated = new FormTextField("lastupdateddate", false, 50);
      lastUpdated.setValue("");
      reportConfigForm.addElement(lastUpdated);
      FormTextField lastUpdatedBy = new FormTextField("lastUpdatedBy", false, 50);
      lastUpdatedBy.setValue("");
      reportConfigForm.addElement(lastUpdatedBy);
      FormCheckBox BeginEffectiveDate = new FormCheckBox("BeginEffectiveDate", "BeginEffectiveDate", "Begin Effective Date", false);
      BeginEffectiveDate.setId("BeginEffectiveDate");
      reportConfigForm.addElement(BeginEffectiveDate);
      FormCheckBox EndEffectiveDate = new FormCheckBox("EndEffectiveDate", "EndEffectiveDate", "Ending Effective Date", false);
      EndEffectiveDate.setId("EndEffectiveDate");
      reportConfigForm.addElement(EndEffectiveDate);
      Vector ownerFamilies = Cache.getInstance().getFamilies();
      FormDropDownMenu ReportOwner = MilestoneHelper.getCorporateStructureDropDown("ReportOwner", ownerFamilies, "", true, true);
      reportConfigForm.addElement(ReportOwner);
    } 
    FormTextField ReportNameSearch = new FormTextField("ReportNameSearch", "", false, 10, 10);
    ReportNameSearch.setId("ReportNameSearch");
    reportConfigForm.addElement(ReportNameSearch);
    FormTextField FileNameSearch = new FormTextField("FileNameSearch", "", false, 10, 10);
    FileNameSearch.setId("FileNameSearch");
    reportConfigForm.addElement(FileNameSearch);
    FormCheckBox Config = new FormCheckBox("Configuration", "Configuration", "Format", false);
    Config.addFormEvent("onClick", "isSubconfigSelected();");
    reportConfigForm.addElement(Config);
    FormDropDownMenu configList = MilestoneHelper.getSelectionConfigurationDropDown("configurationList", "", false);
    reportConfigForm.addElement(configList);
    boolean subconfiguration = false;
    subconfiguration = reportConfig.getSubconfigFlag();
    FormCheckBox SubConfig = new FormCheckBox("Subconfiguration", "Subconfiguration", "Sub-Format", false);
    SubConfig.setChecked(subconfiguration);
    SubConfig.addFormEvent("onClick", "isConfigSelected();");
    SubConfig.setId("Subconfiguration");
    reportConfigForm.addElement(SubConfig);
    FormCheckBox ScheduledReleases = new FormCheckBox("ScheduledReleases", "ScheduledReleases", "Scheduled Releases", false);
    ScheduledReleases.setId("SchduledReleases");
    reportConfigForm.addElement(ScheduledReleases);
    FormCheckBox AddsMovesBoth = new FormCheckBox("AddsMovesBoth", "AddsMovesBoth", "Adds Moves Both", false);
    AddsMovesBoth.setId("AddsMovesBoth");
    reportConfigForm.addElement(AddsMovesBoth);
    FormRadioButtonGroup ProductType = new FormRadioButtonGroup("ProductType", "Physical", "Physical,Digital,Both", false);
    ProductType.setId("ProductType");
    reportConfigForm.addElement(ProductType);
    if (reportConfig != null)
      context.putSessionValue("ReportConfig", reportConfig); 
    reportConfigForm.addElement(new FormHidden("cmd", "report-config-editor", true));
    reportConfigForm.addElement(new FormHidden("OrderBy", "", true));
    if (context.getSessionValue("NOTEPAD_REPORT_CONFIG_VISIBLE") != null)
      context.putDelivery("isNotePadVisible", (Boolean)context.getSessionValue("NOTEPAD_REPORT_CONFIG_VISIBLE")); 
    return reportConfigForm;
  }
  
  private boolean editNew(Context context) {
    User user = MilestoneSecurity.getUser(context);
    Notepad notepad = getReportConfigNotepad(context, user.getUserId());
    MilestoneHelper.putNotepadIntoSession(notepad, context);
    Form form = buildNewForm(context);
    form.addElement(new FormHidden("cmd", "report-config-edit-new", true));
    context.putDelivery("Form", form);
    return context.includeJSP("report-config-editor.jsp");
  }
  
  protected Form buildNewForm(Context context) {
    Form reportConfigForm = new Form(this.application, "reportConfigForm", 
        this.application.getInfrastructure().getServletURL(), "POST");
    User user = MilestoneSecurity.getUser(context);
    FormTextField ReportName = new FormTextField("ReportName", "", true, 10, 10);
    ReportName.setTabIndex(1);
    reportConfigForm.addElement(ReportName);
    FormTextField Description = new FormTextField("Description", "", false, 50, 50);
    Description.setTabIndex(2);
    reportConfigForm.addElement(Description);
    FormDropDownMenu ReportType = MilestoneHelper.getLookupDropDown("ReportType", Cache.getReportConfigTypes(), "", true, false);
    ReportType.setTabIndex(3);
    reportConfigForm.addElement(ReportType);
    FormDropDownMenu ReportFormat = MilestoneHelper.getLookupDropDown("ReportFormat", Cache.getReportConfigFormats(), "", true, false);
    ReportFormat.setTabIndex(4);
    reportConfigForm.addElement(ReportFormat);
    FormTextField Path = new FormTextField("Path", "", false, 80, 255);
    Path.setTabIndex(5);
    reportConfigForm.addElement(Path);
    FormTextField FileName = new FormTextField("FileName", "", false, 50, 50);
    FileName.setTabIndex(6);
    reportConfigForm.addElement(FileName);
    FormRadioButtonGroup ProductType = new FormRadioButtonGroup("ProductType", "Physical", "Physical,Digital,Both", false);
    ProductType.setId("ProductType");
    reportConfigForm.addElement(ProductType);
    FormCheckBox BeginDate = new FormCheckBox("BeginDate", "", "Begin Date", false);
    BeginDate.setTabIndex(7);
    reportConfigForm.addElement(BeginDate);
    FormCheckBox EndDate = new FormCheckBox("EndDate", "", "End Date", false);
    EndDate.setTabIndex(8);
    reportConfigForm.addElement(EndDate);
    FormCheckBox Region = new FormCheckBox("Region", "", "Region", false);
    Region.setTabIndex(9);
    reportConfigForm.addElement(Region);
    FormCheckBox Family = new FormCheckBox("Family", "", "Family", false);
    Family.setTabIndex(10);
    reportConfigForm.addElement(Family);
    FormCheckBox Environment = new FormCheckBox("Environment", "", "Environment", false);
    Environment.setTabIndex(11);
    reportConfigForm.addElement(Environment);
    FormCheckBox Company = new FormCheckBox("Company", "", "Company", false);
    Company.setTabIndex(12);
    reportConfigForm.addElement(Company);
    FormCheckBox Label = new FormCheckBox("Label", "", "Label", false);
    Label.setTabIndex(13);
    reportConfigForm.addElement(Label);
    FormCheckBox Contact = new FormCheckBox("LabelContact", "", "LabelContact", false);
    Contact.setTabIndex(14);
    reportConfigForm.addElement(Contact);
    FormCheckBox CompleteKeyTask = new FormCheckBox("CompleteKeyTask", "", "Completed Task", false);
    CompleteKeyTask.setTabIndex(15);
    reportConfigForm.addElement(CompleteKeyTask);
    FormCheckBox ParentsOnly = new FormCheckBox("ParentsOnly", "", "Parents Only", false);
    ParentsOnly.setTabIndex(16);
    reportConfigForm.addElement(ParentsOnly);
    FormCheckBox UMLKeyTask = new FormCheckBox("UMLKeyTask", "", "Uml Key Task", false);
    UMLKeyTask.setTabIndex(17);
    reportConfigForm.addElement(UMLKeyTask);
    FormCheckBox ReleaseType = new FormCheckBox("ReleaseType", "", "Release Type", false);
    ReleaseType.setTabIndex(18);
    reportConfigForm.addElement(ReleaseType);
    FormCheckBox Status = new FormCheckBox("Status", "", "Status", false);
    Status.setTabIndex(19);
    reportConfigForm.addElement(Status);
    FormCheckBox Artist = new FormCheckBox("Artist", "", "Artist", false);
    Artist.setTabIndex(20);
    reportConfigForm.addElement(Artist);
    FormCheckBox UMLDates = new FormCheckBox("TaskOwner", "", "Task Owner", false);
    UMLDates.setTabIndex(21);
    reportConfigForm.addElement(UMLDates);
    FormCheckBox umlContact = new FormCheckBox("UmlContact", "", "Uml Contact", false);
    umlContact.setTabIndex(22);
    reportConfigForm.addElement(umlContact);
    FormCheckBox Future2 = new FormCheckBox("Future2", "", "Future2", false);
    Future2.setTabIndex(23);
    reportConfigForm.addElement(Future2);
    FormCheckBox BeginDueDate = new FormCheckBox("BeginDueDate", "", "Begin Due Date", false);
    BeginDueDate.setTabIndex(24);
    reportConfigForm.addElement(BeginDueDate);
    FormCheckBox EndDueDate = new FormCheckBox("EndDueDate", "", "End Due Date", false);
    EndDueDate.setTabIndex(25);
    reportConfigForm.addElement(EndDueDate);
    FormTextField ReportNameSearch = new FormTextField("ReportNameSearch", "", false, 10, 10);
    ReportNameSearch.setId("ReportNameSearch");
    reportConfigForm.addElement(ReportNameSearch);
    FormTextField FileNameSearch = new FormTextField("FileNameSearch", "", false, 10, 10);
    FileNameSearch.setId("FileNameSearch");
    reportConfigForm.addElement(FileNameSearch);
    FormCheckBox Config = new FormCheckBox("Configuration", "", "Format", false);
    Config.addFormEvent("onClick", "isSubconfigSelected();");
    Config.setTabIndex(26);
    reportConfigForm.addElement(Config);
    FormCheckBox ScheduledReleases = new FormCheckBox("ScheduledReleases", "", "Scheduled Releases", false);
    ScheduledReleases.setTabIndex(27);
    reportConfigForm.addElement(ScheduledReleases);
    FormCheckBox SubConfig = new FormCheckBox("Subconfiguration", "", "Sub-Format", false);
    SubConfig.addFormEvent("onClick", "isConfigSelected();");
    SubConfig.setTabIndex(28);
    reportConfigForm.addElement(SubConfig);
    FormCheckBox AddsMovesBoth = new FormCheckBox("AddsMovesBoth", "", "Adds Moves Both", false);
    AddsMovesBoth.setTabIndex(29);
    reportConfigForm.addElement(AddsMovesBoth);
    FormCheckBox PhysicalProductActivity = new FormCheckBox("PhysicalProductActivity", "", "Physical Product Activity", false);
    PhysicalProductActivity.setTabIndex(30);
    reportConfigForm.addElement(PhysicalProductActivity);
    FormCheckBox DistCo = new FormCheckBox("DistCo", "", "Distribution Company", false);
    DistCo.setTabIndex(31);
    reportConfigForm.addElement(DistCo);
    if (context.getSessionValue("NOTEPAD_REPORT_CONFIG_VISIBLE") != null)
      context.putDelivery("isNotePadVisible", (Boolean)context.getSessionValue("NOTEPAD_REPORT_CONFIG_VISIBLE")); 
    return reportConfigForm;
  }
  
  private boolean saveNew(Context context) {
    User user = MilestoneSecurity.getUser(context);
    Notepad notepad = getReportConfigNotepad(context, user.getUserId());
    MilestoneHelper.putNotepadIntoSession(notepad, context);
    Report reportConfig = new Report();
    Form form = buildNewForm(context);
    form.addElement(new FormHidden("cmd", "report-config-edit-save-new"));
    form.setValues(context);
    reportConfig.setReportID(-1);
    reportConfig.setSubName(null);
    String reportString = form.getStringValue("ReportName");
    reportConfig.setReportName(reportString);
    String descriptionString = form.getStringValue("Description");
    reportConfig.setDescription(descriptionString);
    String reportType = form.getStringValue("ReportType");
    int type = -1;
    try {
      type = Integer.parseInt(reportType);
    } catch (NumberFormatException e) {
      System.out.println("Error converting the Report Type to integer.");
      type = 0;
    } 
    reportConfig.setType(type);
    String reportFormat = form.getStringValue("ReportFormat");
    int format = -1;
    try {
      format = Integer.parseInt(reportFormat);
    } catch (NumberFormatException e) {
      format = 0;
    } 
    reportConfig.setFormat(format);
    reportConfig.setReportStatus("ACTIVE");
    String path = form.getStringValue("Path");
    reportConfig.setPath(path);
    String fileName = form.getStringValue("FileName");
    reportConfig.setFileName(fileName);
    String subReportName = "";
    reportConfig.setSubName(subReportName);
    reportConfig.setBeginDateFlag(((FormCheckBox)form.getElement("BeginDate")).isChecked());
    reportConfig.setEndDateFlag(((FormCheckBox)form.getElement("EndDate")).isChecked());
    reportConfig.setRegionFlag(((FormCheckBox)form.getElement("Region")).isChecked());
    reportConfig.setFamilyFlag(((FormCheckBox)form.getElement("Family")).isChecked());
    reportConfig.setEnvironmentFlag(((FormCheckBox)form.getElement("Environment")).isChecked());
    reportConfig.setCompanyFlag(((FormCheckBox)form.getElement("Company")).isChecked());
    reportConfig.setLabelFlag(((FormCheckBox)form.getElement("Label")).isChecked());
    reportConfig.setContactFlag(((FormCheckBox)form.getElement("LabelContact")).isChecked());
    reportConfig.setCompleteKeyTaskFlag(((FormCheckBox)form.getElement("CompleteKeyTask")).isChecked());
    reportConfig.setParentsOnlyFlag(((FormCheckBox)form.getElement("ParentsOnly")).isChecked());
    reportConfig.setKeyTaskFlag(((FormCheckBox)form.getElement("UMLKeyTask")).isChecked());
    reportConfig.setReleaseTypeFlag(((FormCheckBox)form.getElement("ReleaseType")).isChecked());
    reportConfig.setArtistFlag(((FormCheckBox)form.getElement("Artist")).isChecked());
    reportConfig.setStatusFlag(((FormCheckBox)form.getElement("Status")).isChecked());
    reportConfig.setUmlDatesFlag(((FormCheckBox)form.getElement("TaskOwner")).isChecked());
    reportConfig.setFuture1Flag(((FormCheckBox)form.getElement("UmlContact")).isChecked());
    reportConfig.setFuture2Flag(((FormCheckBox)form.getElement("Future2")).isChecked());
    reportConfig.setBeginDueDateFlag(((FormCheckBox)form.getElement("BeginDueDate")).isChecked());
    reportConfig.setEndDueDateFlag(((FormCheckBox)form.getElement("EndDueDate")).isChecked());
    reportConfig.setConfiguration(((FormCheckBox)form.getElement("Configuration")).isChecked());
    reportConfig.setScheduledReleasesFlag(((FormCheckBox)form.getElement("ScheduledReleases")).isChecked());
    reportConfig.setAddsMovesBoth(((FormCheckBox)form.getElement("AddsMovesBoth")).isChecked());
    reportConfig.setPhysicalProductActivity(((FormCheckBox)form.getElement("PhysicalProductActivity")).isChecked());
    reportConfig.setDistCo(((FormCheckBox)form.getElement("DistCo")).isChecked());
    String productTypeStr = ((FormRadioButtonGroup)form.getElement("ProductType")).getStringValue();
    int productTypeInt = 0;
    if (productTypeStr.equals("Physical")) {
      productTypeInt = 0;
    } else if (productTypeStr.equals("Digital")) {
      productTypeInt = 1;
    } else if (productTypeStr.equals("Both")) {
      productTypeInt = 2;
    } 
    reportConfig.setProductType(productTypeInt);
    if (!ReportConfigManager.getInstance().isDuplicate(reportConfig)) {
      if (!form.isUnchanged()) {
        FormValidation formValidation = form.validate();
        if (formValidation.isGood()) {
          Report savedReportConfig = ReportConfigManager.getInstance().saveReportConfig(reportConfig, user.getUserId());
          context.putSessionValue("ReportConfig", savedReportConfig);
          context.putDelivery("Form", form);
          if (notepad.getAllContents() == null || notepad.getAllContents().size() < 1)
            notepad.setSearchQuery(""); 
          notepad.setAllContents(null);
          notepad.newSelectedReset();
          notepad = getReportConfigNotepad(context, MilestoneSecurity.getUser(context).getUserId());
          notepad.setSelected(reportConfig);
        } else {
          context.putDelivery("FormValidation", formValidation);
          form.addElement(new FormHidden("OrderBy", "", true));
          context.putDelivery("Form", form);
          return context.includeJSP("report-config-editor.jsp");
        } 
      } 
    } else {
      context.putDelivery("AlertMessage", 
          MilestoneMessage.getMessage(5, 
            
            new String[] { "Report Configuration", reportConfig.getReportName() }));
    } 
    Notepad reportNotepad = MilestoneHelper.getNotepadFromSession(3, context);
    if (reportNotepad != null) {
      reportNotepad.setAllContents(null);
      reportNotepad.setSelected(null);
    } 
    return edit(context);
  }
  
  private boolean goToBlank(Context context) {
    context.putDelivery("isNotePadVisible", new Boolean(MilestoneHelper.getNotePadVisiblitiy(17, context)));
    Form form = new Form(this.application, "form", this.application.getInfrastructure().getServletURL(), "Post");
    form.addElement(new FormHidden("cmd", "report-config-editor"));
    form.addElement(new FormHidden("OrderBy", "", true));
    FormTextField ReportNameSearch = new FormTextField("ReportNameSearch", "", false, 10, 10);
    ReportNameSearch.setId("ReportNameSearch");
    form.addElement(ReportNameSearch);
    FormTextField FileNameSearch = new FormTextField("FileNameSearch", "", false, 10, 10);
    FileNameSearch.setId("FileNameSearch");
    form.addElement(FileNameSearch);
    context.putDelivery("Form", form);
    return context.includeJSP("blank-report-config-editor.jsp");
  }
}


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\ReportConfigHandler.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */