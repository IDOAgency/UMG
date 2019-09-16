package WEB-INF.classes.com.universal.milestone;

import com.techempower.ComponentLog;
import com.techempower.gemini.Context;
import com.techempower.gemini.Dispatcher;
import com.techempower.gemini.FormCheckBox;
import com.techempower.gemini.FormDropDownMenu;
import com.techempower.gemini.FormHidden;
import com.techempower.gemini.FormRadioButtonGroup;
import com.techempower.gemini.GeminiApplication;
import com.universal.milestone.Cache;
import com.universal.milestone.Company;
import com.universal.milestone.Division;
import com.universal.milestone.Environment;
import com.universal.milestone.Family;
import com.universal.milestone.Form;
import com.universal.milestone.JdbcConnector;
import com.universal.milestone.Label;
import com.universal.milestone.MilestoneFormDropDownMenu;
import com.universal.milestone.MilestoneHelper;
import com.universal.milestone.MilestoneHelper_2;
import com.universal.milestone.MilestoneSecurity;
import com.universal.milestone.PrefixCode;
import com.universal.milestone.ReleaseCalendarHandler;
import com.universal.milestone.ReleasingFamily;
import com.universal.milestone.ReportConfigManager;
import com.universal.milestone.SecureHandler;
import com.universal.milestone.Selection;
import com.universal.milestone.SelectionHandler;
import com.universal.milestone.SelectionManager;
import com.universal.milestone.SelectionStatus;
import com.universal.milestone.User;
import com.universal.milestone.UserManager;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Vector;

public class ReleaseCalendarHandler extends SecureHandler {
  public static final String COMPONENT_CODE = "hRCa";
  
  public GeminiApplication application;
  
  public ComponentLog log;
  
  public HashMap corpHashMap;
  
  public String[] calendarMonths;
  
  public String[] calendarDays;
  
  public ReleaseCalendarHandler(GeminiApplication application) {
    this.corpHashMap = null;
    this.calendarMonths = new String[12];
    this.calendarDays = new String[7];
    this.application = application;
    this.log = application.getLog("hRCa");
    this.calendarMonths[0] = "January";
    this.calendarMonths[1] = "February";
    this.calendarMonths[2] = "March";
    this.calendarMonths[3] = "April";
    this.calendarMonths[4] = "May";
    this.calendarMonths[5] = "June";
    this.calendarMonths[6] = "July";
    this.calendarMonths[7] = "August";
    this.calendarMonths[8] = "September";
    this.calendarMonths[9] = "October";
    this.calendarMonths[10] = "November";
    this.calendarMonths[11] = "December";
    this.calendarDays[0] = "Sunday";
    this.calendarDays[1] = "Monday";
    this.calendarDays[2] = "Tuesday";
    this.calendarDays[3] = "Wednesday";
    this.calendarDays[4] = "Thursday";
    this.calendarDays[5] = "Friday";
    this.calendarDays[6] = "Saturday";
  }
  
  public String getDescription() { return "Release Calendar Handler"; }
  
  public boolean acceptRequest(Dispatcher dispatcher, Context context, String command) {
    if (super.acceptRequest(dispatcher, context, command))
      if (command.startsWith("release-calendar"))
        return handleRequest(dispatcher, context, command);  
    return false;
  }
  
  public boolean handleRequest(Dispatcher dispatcher, Context context, String command) {
    if (command.equalsIgnoreCase("release-calendar-editor"))
      editReleaseCalendar(dispatcher, context, command); 
    return true;
  }
  
  private boolean editReleaseCalendar(Dispatcher dispatcher, Context context, String command) {
    SelectionManager.getInstance().setSelectionNotepadUserDefaults(context);
    Form form = buildForm(context);
    context.putDelivery("Form", form);
    HashMap selections = getReleaseCalendarSelections(form, context);
    context.putDelivery("ReleaseCalendarSelections", selections);
    return context.includeJSP("release-calendar-editor.jsp");
  }
  
  protected Form buildForm(Context context) {
    User user = MilestoneSecurity.getUser(context);
    User userSrch = (User)context.getSessionValue("ResetSearchVariables");
    if (userSrch != null)
      SelectionHandler.resetSearchVariables(user, userSrch, context); 
    if (user != null && !user.SS_searchInitiated) {
      UserManager.getInstance().setUserPreferenceReleaseCalendar(user);
    } else {
      user.RC_environment = user.SS_environmentSearch;
      user.RC_releasingFamily = user.SS_familySearch;
      user.RC_labelContact = user.SS_contactSearch;
      user.RC_productType = MilestoneHelper_2.convertProductTypeToReleaseCalendar(user.SS_productTypeSearch);
    } 
    Calendar calendar = new GregorianCalendar();
    int defaultMonth = calendar.get(2) + 1;
    int defaultYear = calendar.get(1);
    if (!user.RC_month.equals(""))
      defaultMonth = Integer.parseInt(user.RC_month); 
    if (context.getRequestValue("monthList") != null)
      defaultMonth = Integer.parseInt(context.getRequestValue("monthList")); 
    if (!user.RC_year.equals(""))
      defaultYear = Integer.parseInt(user.RC_year); 
    if (context.getRequestValue("yearList") != null)
      defaultYear = Integer.parseInt(context.getRequestValue("yearList")); 
    String paramReleasingFamily = "-1";
    if (!user.RC_releasingFamily.equals(""))
      paramReleasingFamily = user.RC_releasingFamily; 
    if (context.getRequestValue("family") != null) {
      paramReleasingFamily = context.getRequestValue("family");
      user.SS_familySearch = paramReleasingFamily;
    } 
    String paramEnvironment = "-1";
    if (!user.RC_environment.equals(""))
      paramEnvironment = user.RC_environment; 
    if (context.getRequestValue("environment") != null) {
      paramEnvironment = context.getRequestValue("environment");
      user.SS_environmentSearch = paramEnvironment;
    } 
    String paramReleaseType = "All";
    if (!user.RC_releaseType.equals(""))
      paramReleaseType = user.RC_releaseType; 
    if (context.getRequestValue("releaseType") != null)
      paramReleaseType = context.getRequestValue("releaseType"); 
    String paramProductType = "All";
    if (!user.RC_productType.equals(""))
      paramProductType = user.RC_productType; 
    if (context.getRequestValue("productType") != null) {
      paramProductType = context.getRequestValue("productType");
      user.SS_productTypeSearch = MilestoneHelper_2.convertProductTypeToUserPref(paramProductType);
    } 
    String paramContactList = "All";
    if (!user.RC_labelContact.equals(""))
      paramContactList = user.RC_labelContact; 
    if (context.getRequestValue("contact") != null) {
      paramContactList = context.getRequestValue("contact");
      user.SS_contactSearch = paramContactList;
    } 
    String paramConfigList = "All";
    if (!user.RC_formatType.equals(""))
      paramConfigList = user.RC_formatType; 
    if (context.getRequestValue("configurationList") != null)
      paramConfigList = context.getRequestValue("configurationList"); 
    boolean paramAllStatus = false;
    if (!user.RC_status.equals("") && user.RC_status.indexOf("All") > -1 && context.getRequestValue("configurationList") == null)
      paramAllStatus = true; 
    if (context.getRequestValue("AllStatus") != null)
      paramAllStatus = true; 
    boolean paramActiveStatus = false;
    if (!user.RC_status.equals("") && user.RC_status.indexOf("Active") > -1 && context.getRequestValue("configurationList") == null) {
      paramActiveStatus = true;
    } else if (user.RC_status.equals("") && context.getRequestValue("configurationList") == null) {
      paramActiveStatus = true;
    } 
    if (context.getRequestValue("ActiveStatus") != null)
      paramActiveStatus = true; 
    boolean paramClosedStatus = false;
    if (!user.RC_status.equals("") && user.RC_status.indexOf("Closed") > -1 && context.getRequestValue("configurationList") == null)
      paramClosedStatus = true; 
    if (context.getRequestValue("ClosedStatus") != null)
      paramClosedStatus = true; 
    Form calendarForm = new Form(this.application, "reportForm", 
        this.application.getInfrastructure().getServletURL(), "POST");
    calendarForm.addElement(new FormHidden("cmd", "release-calendar-editor", true));
    String monthList = "January,February,March,April,May,June,July,August,September,October,November,December";
    String monthIdList = "1,2,3,4,5,6,7,8,9,10,11,12";
    FormDropDownMenu MonthList = new FormDropDownMenu("monthList", String.valueOf(defaultMonth), monthIdList, monthList, false);
    MonthList.setTabIndex(1);
    calendarForm.addElement(MonthList);
    String yearList = "";
    String yearIdList = "";
    Calendar thisYear = GregorianCalendar.getInstance();
    for (int y = 1998; y < thisYear.get(1) + 2; y++) {
      yearList = String.valueOf(yearList) + "," + String.valueOf(y);
      yearIdList = String.valueOf(yearIdList) + "," + String.valueOf(y);
    } 
    FormDropDownMenu YearList = new FormDropDownMenu("yearList", String.valueOf(defaultYear), yearIdList, yearList, false);
    YearList.setTabIndex(2);
    calendarForm.addElement(YearList);
    Vector families = SelectionHandler.filterCSO(ReleasingFamily.getUserReleasingFamiliesVectorOfFamilies(context));
    FormDropDownMenu Family = MilestoneHelper.getCorporateStructureDropDown("family", families, paramReleasingFamily, false, true);
    Family.addFormEvent("onChange", "return(clickFamily(this))");
    Family.setTabIndex(3);
    calendarForm.addElement(Family);
    String hiddenFamilyIndex = "0";
    if (context.getRequest().getParameter("hiddenFamilyIndex") != null)
      hiddenFamilyIndex = context.getRequest().getParameter("hiddenFamilyIndex"); 
    calendarForm.addElement(new FormHidden("hiddenFamilyIndex", hiddenFamilyIndex, false));
    Vector environments = MilestoneHelper.getUserEnvironments(context);
    Vector companies = MilestoneHelper.getUserCompanies(context);
    environments = SelectionHandler.filterSelectionEnvironments(companies);
    environments = MilestoneHelper.removeUnusedCSO(environments, context, -1);
    companies = MilestoneHelper.removeUnusedCSO(companies, context, -1);
    FormDropDownMenu envMenu = MilestoneHelper.getCorporateStructureDropDown("environment", environments, paramEnvironment, false, true);
    envMenu.addFormEvent("onChange", "return(clickEnvironment(this))");
    envMenu.setTabIndex(4);
    calendarForm.addElement(envMenu);
    String hiddenEnvironmentIndex = "0";
    if (context.getRequest().getParameter("hiddenEnvironmentIndex") != null)
      hiddenEnvironmentIndex = context.getRequest().getParameter("hiddenEnvironmentIndex"); 
    calendarForm.addElement(new FormHidden("hiddenEnvironmentIndex", hiddenEnvironmentIndex, false));
    FormRadioButtonGroup ReleaseType = new FormRadioButtonGroup("releaseType", paramReleaseType, "Commercial, Promotional, All", false);
    ReleaseType.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    ReleaseType.setTabIndex(11);
    calendarForm.addElement(ReleaseType);
    if (user == null || user.getAdministrator() != 1)
      paramProductType = "Physical"; 
    FormRadioButtonGroup ProductType = new FormRadioButtonGroup("productType", paramProductType, "Digital, Physical, All", false);
    ProductType.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    ProductType.setTabIndex(10);
    calendarForm.addElement(ProductType);
    Vector contactVector = ReportConfigManager.getLabelContacts();
    String contactList = "All,";
    String idList = "0,";
    if (contactVector != null) {
      for (int i = 0; i < contactVector.size(); i++) {
        User userContact = (User)contactVector.get(i);
        if (i < contactVector.size() - 1) {
          if (userContact != null) {
            contactList = String.valueOf(contactList) + userContact.getName() + ",";
            idList = String.valueOf(idList) + userContact.getUserId() + ",";
          } 
        } else if (userContact != null) {
          contactList = String.valueOf(contactList) + userContact.getName();
          idList = String.valueOf(idList) + userContact.getUserId();
        } 
      } 
    } else {
      contactList = "All";
      idList = "0";
    } 
    FormDropDownMenu ContactList = new FormDropDownMenu("contact", paramContactList, idList, contactList, false);
    ContactList.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    ContactList.setTabIndex(5);
    calendarForm.addElement(ContactList);
    MilestoneFormDropDownMenu ConfigList = new MilestoneFormDropDownMenu(MilestoneHelper.getSelectionConfigurationDropDown("configurationList", "", false));
    ConfigList.addFormEvent("onClick", "hidePrintButtons('printWindow','');");
    String[] values = ConfigList.getValueList();
    String[] menuText = ConfigList.getMenuTextList();
    values[0] = "All";
    menuText[0] = "All";
    ConfigList.setValueList(values);
    ConfigList.setMenuTextList(menuText);
    ConfigList.setValue(paramConfigList);
    ConfigList.setTabIndex(6);
    calendarForm.addElement(ConfigList);
    FormCheckBox AllStatus = new FormCheckBox("AllStatus", "All", false, false);
    AllStatus.setChecked(paramAllStatus);
    AllStatus.addFormEvent("onClick", "StatusSelected(this);");
    AllStatus.setTabIndex(7);
    AllStatus.setId("AllStatus");
    calendarForm.addElement(AllStatus);
    FormCheckBox ActiveStatus = new FormCheckBox("ActiveStatus", "Active", false, true);
    ActiveStatus.setChecked(paramActiveStatus);
    ActiveStatus.addFormEvent("onClick", "StatusSelected(this);");
    ActiveStatus.setTabIndex(8);
    ActiveStatus.setId("ActiveStatus");
    calendarForm.addElement(ActiveStatus);
    FormCheckBox ClosedStatus = new FormCheckBox("ClosedStatus", "Closed", false, false);
    ClosedStatus.setChecked(paramClosedStatus);
    ClosedStatus.addFormEvent("onClick", "StatusSelected(this);");
    ClosedStatus.setTabIndex(9);
    ClosedStatus.setId("ClosedStatus");
    calendarForm.addElement(ClosedStatus);
    user.RC_environment = paramEnvironment;
    user.RC_formatType = paramConfigList;
    user.RC_labelContact = paramContactList;
    user.RC_month = String.valueOf(defaultMonth);
    user.RC_productType = paramProductType;
    user.RC_releaseType = paramReleaseType;
    user.RC_releasingFamily = paramReleasingFamily;
    user.RC_status = "";
    if (paramAllStatus)
      user.RC_status = String.valueOf(user.RC_status) + "All"; 
    if (paramActiveStatus)
      user.RC_status = String.valueOf(user.RC_status) + "Active"; 
    if (paramClosedStatus)
      user.RC_status = String.valueOf(user.RC_status) + "Closed"; 
    user.RC_year = String.valueOf(defaultYear);
    context.putDelivery("selectionArrays", ReleasingFamily.getJavaScriptCorporateArrayReleasingFamilySearch(context));
    return calendarForm;
  }
  
  private String getJavaScriptCorporateArray(Context context) {
    StringBuffer result = new StringBuffer(100);
    String str = "";
    String value = new String();
    this.corpHashMap = MilestoneHelper.buildActiveCorporateStructureHashMap();
    User user = (User)context.getSessionValue("user");
    Vector vUserCompanies = SelectionHandler.filterSelectionActiveCompanies(user.getUserId(), MilestoneHelper.getUserCompanies(context));
    Vector vUserEnvironments = SelectionHandler.filterSelectionEnvironments(vUserCompanies);
    result.append("\n");
    result.append("var familyArray = new Array();\n");
    result.append("var environmentArray = new Array();\n");
    result.append("var companyArray = new Array();\n");
    int arrayIndex = 0;
    result.append("familyArray[0] = new Array( 0, 'All');\n");
    result.append("environmentArray[0] = new Array( 0, 'All');\n");
    Vector vUserFamilies = SelectionHandler.filterCSO(MilestoneHelper.getNonSecureActiveUserFamilies(context));
    for (int l = 0; l < vUserFamilies.size(); l++) {
      Family family = (Family)vUserFamilies.elementAt(l);
      int structureId = family.getStructureID();
      boolean familyVal = true;
      if (this.corpHashMap.containsKey(new Integer(structureId)))
        familyVal = false; 
      if (family != null && familyVal) {
        result.append("familyArray[");
        result.append(family.getStructureID());
        result.append("] = new Array(");
        boolean foundZeroth = false;
        Vector environmentVector = new Vector();
        Vector environments = getUserEnvironmentsFromFamily(family, context);
        if (environments != null) {
          result.append(" 0, 'All',");
          for (int j = 0; j < environments.size(); j++) {
            Environment environment = (Environment)environments.elementAt(j);
            int structureIdc = environment.getStructureID();
            boolean boolVal = true;
            if (this.corpHashMap.containsKey(new Integer(structureIdc)))
              boolVal = false; 
            if (environment.getParentID() == family.getStructureID() && boolVal) {
              if (foundZeroth)
                result.append(','); 
              result.append(' ');
              result.append(environment.getStructureID());
              result.append(", '");
              result.append(MilestoneHelper.urlEncode(environment.getName()));
              result.append('\'');
              foundZeroth = true;
              environmentVector.addElement(environment);
            } 
          } 
          if (foundZeroth) {
            result.append(");\n");
          } else {
            result.append(" 0, 'All');\n");
          } 
          for (int k = 0; k < environmentVector.size(); k++) {
            Environment environmentNode = (Environment)environmentVector.elementAt(k);
            result.append("environmentArray[");
            result.append(environmentNode.getStructureID());
            result.append("] = new Array(");
            Vector companyVector = new Vector();
            Vector companies = environmentNode.getChildren();
            if (companies != null) {
              result.append(" 0, 'All',");
              boolean foundZeroth2 = false;
              for (int m = 0; m < companies.size(); m++) {
                Company company = (Company)companies.elementAt(m);
                int structureIdc = company.getStructureID();
                boolean boolVal = true;
                if (this.corpHashMap.containsKey(new Integer(structureIdc)))
                  boolVal = false; 
                if (company.getParentID() == environmentNode.getStructureID() && boolVal) {
                  if (foundZeroth2)
                    result.append(','); 
                  result.append(' ');
                  result.append(company.getStructureID());
                  result.append(", '");
                  result.append(MilestoneHelper.urlEncode(company.getName()));
                  result.append('\'');
                  foundZeroth2 = true;
                  companyVector.addElement(company);
                } 
              } 
              if (foundZeroth2) {
                result.append(");\n");
              } else {
                result.append(" 0, 'All');\n");
              } 
              for (int n = 0; n < companyVector.size(); n++) {
                Company companyNode = (Company)companyVector.elementAt(n);
                result.append("companyArray[");
                result.append(companyNode.getStructureID());
                result.append("] = new Array(");
                Vector divisions = companyNode.getChildren();
                boolean foundSecond2 = false;
                result.append(" 0, 'All',");
                for (int x = 0; x < divisions.size(); x++) {
                  Division division = (Division)divisions.elementAt(x);
                  int structureIds = division.getStructureID();
                  boolean boolRes = true;
                  if (this.corpHashMap.containsKey(new Integer(structureIds)))
                    boolRes = false; 
                  if (division != null && boolRes) {
                    Vector labels = division.getChildren();
                    for (int y = 0; y < labels.size(); y++) {
                      Label labelNode = (Label)labels.get(y);
                      int structureIdl = labelNode.getStructureID();
                      boolean boolVal = true;
                      if (this.corpHashMap.containsKey(new Integer(structureIdl)))
                        boolVal = false; 
                      if (labelNode.getParentID() == division.getStructureID() && boolVal) {
                        if (foundSecond2)
                          result.append(','); 
                        result.append(' ');
                        result.append(labelNode.getStructureID());
                        result.append(", '");
                        result.append(MilestoneHelper.urlEncode(labelNode.getName()));
                        result.append('\'');
                        foundSecond2 = true;
                      } 
                    } 
                  } 
                } 
                if (foundSecond2) {
                  result.append(");\n");
                } else {
                  result.append(" 0, 'All');\n");
                } 
              } 
            } 
          } 
        } 
      } 
    } 
    this.corpHashMap = null;
    return result.toString();
  }
  
  private Vector getUserEnvironmentsFromFamily(Family family, Context context) {
    Vector userEnvironments = MilestoneHelper.getUserEnvironments(context);
    Vector result = new Vector();
    if (family != null) {
      Vector familyEnvironments = family.getChildren();
      if (familyEnvironments != null)
        for (int i = 0; i < familyEnvironments.size(); i++) {
          Environment familyEnvironment = (Environment)familyEnvironments.get(i);
          for (int j = 0; j < userEnvironments.size(); j++) {
            Environment userEnvironment = (Environment)userEnvironments.get(j);
            int structureId = userEnvironment.getStructureID();
            boolean resultStructure = true;
            if (this.corpHashMap.containsKey(new Integer(structureId)))
              resultStructure = false; 
            if (userEnvironment.getStructureID() == familyEnvironment.getStructureID() && resultStructure)
              result.add(familyEnvironment); 
          } 
        }  
    } 
    return result;
  }
  
  private void getInitialDates(Calendar currDate, HashMap selectionsPerMonth1) {
    String txtDateHeader = "";
    int originalMonth = currDate.get(2);
    while (originalMonth == currDate.get(2)) {
      if (this.calendarDays[currDate.get(7) - 1].equalsIgnoreCase("Monday") || 
        this.calendarDays[currDate.get(7) - 1].equalsIgnoreCase("Tuesday")) {
        txtDateHeader = String.valueOf(String.valueOf(currDate.get(5))) + "-" + this.calendarDays[currDate.get(7) - 1];
        Vector workingSelections = new Vector();
        selectionsPerMonth1.put(txtDateHeader, workingSelections);
      } 
      currDate.add(5, 1);
    } 
  }
  
  private HashMap getReleaseCalendarSelections(Form form, Context context) {
    int monthpassedin = form.getElement("monthList").getIntegerValue();
    int yearpassedin = form.getElement("yearList").getIntegerValue();
    String strDate = String.valueOf(String.valueOf(monthpassedin)) + "/1/" + String.valueOf(yearpassedin);
    StringBuffer releaseHeaderQuery = new StringBuffer();
    releaseHeaderQuery.append("select release_id, title, artist, selection_no, release_date = case digital_flag when 0 then street_date when 1 then digital_street_date end,digital_flag, street_date, digital_street_date, configuration, sub_configuration, upc, prefix, status from vi_release_header where ((street_date between '" + 
        
        strDate + "'  and dateadd(month,3,'" + strDate + "') and digital_flag = 0) or " + 
        "       (digital_street_date between '" + strDate + "'  and dateadd(month,3,'" + strDate + "') and digital_flag = 1)) ");
    SelectionManager.addReleasingFamilyLabelFamilySelect("family", context, releaseHeaderQuery, form);
    FormDropDownMenu envDD = (FormDropDownMenu)form.getElement("environment");
    if (!envDD.getStringValue().equals("0") && !envDD.getStringValue().equals("-1")) {
      releaseHeaderQuery.append(" and ( environment_id = " + envDD.getStringValue() + " ) ");
    } else {
      releaseHeaderQuery.append(" and ( environment_id in (");
      for (int f = 0; f < envDD.getValueList().length; f++) {
        if (f == 0) {
          releaseHeaderQuery.append(envDD.getValueList()[f]);
        } else {
          releaseHeaderQuery.append("," + envDD.getValueList()[f]);
        } 
      } 
      releaseHeaderQuery.append(" ))");
    } 
    FormRadioButtonGroup releaseTypeRB = (FormRadioButtonGroup)form.getElement("releaseType");
    if (!releaseTypeRB.getStringValue().equalsIgnoreCase("All"))
      if (releaseTypeRB.getStringValue().equalsIgnoreCase("Commercial")) {
        releaseHeaderQuery.append(" and ( release_type = 'CO' ) ");
      } else {
        releaseHeaderQuery.append(" and ( release_type = 'PR' ) ");
      }  
    FormRadioButtonGroup productTypeRB = (FormRadioButtonGroup)form.getElement("productType");
    if (!productTypeRB.getStringValue().equalsIgnoreCase("All"))
      if (productTypeRB.getStringValue().equalsIgnoreCase("Digital")) {
        releaseHeaderQuery.append(" and ( digital_flag = 1 ) ");
      } else {
        releaseHeaderQuery.append(" and ( digital_flag = 0 ) ");
      }  
    FormDropDownMenu labelContactDD = (FormDropDownMenu)form.getElement("contact");
    if (!labelContactDD.getStringValue().equalsIgnoreCase("All") && !labelContactDD.getStringValue().equalsIgnoreCase("0"))
      releaseHeaderQuery.append(" and ( contact_id = " + labelContactDD.getStringValue() + " ) "); 
    FormDropDownMenu formatTypeDD = (FormDropDownMenu)form.getElement("configurationList");
    if (!formatTypeDD.getStringValue().equalsIgnoreCase("All"))
      releaseHeaderQuery.append(" and ( configuration = '" + formatTypeDD.getStringValue() + "' ) "); 
    FormCheckBox allStatusCB = (FormCheckBox)form.getElement("AllStatus");
    if (allStatusCB == null || !allStatusCB.isChecked()) {
      FormCheckBox ActiveStatusCB = (FormCheckBox)form.getElement("ActiveStatus");
      boolean isTrue = false;
      if (ActiveStatusCB.isChecked()) {
        releaseHeaderQuery.append(" and ( status = 'Active' ");
        isTrue = true;
      } 
      FormCheckBox ClosedStatusCB = (FormCheckBox)form.getElement("ClosedStatus");
      if (ClosedStatusCB.isChecked()) {
        if (isTrue) {
          releaseHeaderQuery.append(" or status = 'Closed' ");
        } else {
          releaseHeaderQuery.append(" and ( status = 'Closed' ");
        } 
        isTrue = true;
      } 
      if (isTrue) {
        releaseHeaderQuery.append(" ) ");
      } else {
        releaseHeaderQuery.append(" and (status = 'Active' OR status = 'Closed') ");
      } 
    } else {
      releaseHeaderQuery.append(" and (status = 'Active' OR status = 'Closed') ");
    } 
    releaseHeaderQuery.append("order by release_date ASC,artist,title");
    JdbcConnector connector = MilestoneHelper.getConnector(releaseHeaderQuery.toString());
    connector.runQuery();
    Selection selection = null;
    HashMap AllSelections = new HashMap();
    HashMap selectionsPerMonth1 = new HashMap();
    HashMap selectionsPerMonth2 = new HashMap();
    HashMap selectionsPerMonth3 = new HashMap();
    Calendar currDate = null;
    String txtDateHeader = "";
    currDate = new GregorianCalendar(yearpassedin, monthpassedin - 1, 1);
    getInitialDates(currDate, selectionsPerMonth1);
    getInitialDates(currDate, selectionsPerMonth2);
    getInitialDates(currDate, selectionsPerMonth3);
    while (connector.more()) {
      selection = new Selection();
      selection.setSelectionID(connector.getIntegerField("release_id"));
      String selectionNo = "";
      if (connector.getFieldByName("selection_no") != null)
        selectionNo = connector.getFieldByName("selection_no"); 
      selection.setSelectionNo(selectionNo);
      selection.setPrefixID((PrefixCode)SelectionManager.getLookupObject(connector.getField("prefix"), Cache.getPrefixCodes()));
      selection.setTitle(connector.getField("title", ""));
      selection.setArtist(connector.getField("artist", ""));
      String streetDateString = connector.getFieldByName("street_date");
      if (streetDateString != null)
        selection.setStreetDate(MilestoneHelper.getDatabaseDate(streetDateString)); 
      String digitalRlsString = connector.getFieldByName("digital_street_date");
      if (digitalRlsString != null) {
        selection.setDigitalRlsDateString(digitalRlsString);
        selection.setDigitalRlsDate(MilestoneHelper.getDatabaseDate(digitalRlsString));
      } 
      selection.setIsDigital(connector.getBoolean("digital_flag"));
      selection.setSelectionConfig(
          SelectionManager.getSelectionConfigObject(connector.getField("configuration"), Cache.getSelectionConfigs()));
      selection.setSelectionSubConfig(
          SelectionManager.getSelectionSubConfigObject(connector.getField("sub_configuration"), selection.getSelectionConfig()));
      selection.setUpc(connector.getField("upc", ""));
      selection.setSelectionStatus(
          (SelectionStatus)MilestoneHelper.getLookupObject(connector.getField("status", ""), Cache.getSelectionStatusList()));
      currDate = MilestoneHelper.getDatabaseDate(connector.getField("release_date"));
      txtDateHeader = String.valueOf(String.valueOf(currDate.get(5))) + "-" + this.calendarDays[currDate.get(7) - 1];
      if (currDate.get(2) == monthpassedin - 1) {
        Vector workingSelections;
        if (selectionsPerMonth1.containsKey(txtDateHeader)) {
          workingSelections = (Vector)selectionsPerMonth1.get(txtDateHeader);
        } else {
          workingSelections = new Vector();
        } 
        workingSelections.add(selection);
        selectionsPerMonth1.put(txtDateHeader, workingSelections);
      } else if ((currDate.get(1) == yearpassedin && currDate.get(2) == monthpassedin) || (
        currDate.get(1) == yearpassedin + 1 && currDate.get(2) == 0 && monthpassedin == 12)) {
        Vector workingSelections;
        if (selectionsPerMonth2.containsKey(txtDateHeader)) {
          workingSelections = (Vector)selectionsPerMonth2.get(txtDateHeader);
        } else {
          workingSelections = new Vector();
        } 
        workingSelections.add(selection);
        selectionsPerMonth2.put(txtDateHeader, workingSelections);
      } else if ((currDate.get(1) == yearpassedin && currDate.get(2) == monthpassedin + 1) || (
        currDate.get(1) == yearpassedin + 1 && currDate.get(2) == 0 && monthpassedin == 11) || (
        currDate.get(1) == yearpassedin + 1 && currDate.get(2) == 1 && monthpassedin == 12)) {
        Vector workingSelections;
        if (selectionsPerMonth3.containsKey(txtDateHeader)) {
          workingSelections = (Vector)selectionsPerMonth3.get(txtDateHeader);
        } else {
          workingSelections = new Vector();
        } 
        workingSelections.add(selection);
        selectionsPerMonth3.put(txtDateHeader, workingSelections);
      } 
      connector.next();
    } 
    connector.close();
    int intForArray1 = monthpassedin - 1;
    int intForArray2 = monthpassedin;
    int intForArray3 = monthpassedin + 1;
    int intYear1 = yearpassedin;
    int intYear2 = yearpassedin;
    int intYear3 = yearpassedin;
    if (monthpassedin > 10)
      switch (monthpassedin) {
        case 11:
          intForArray1 = 10;
          intForArray2 = 11;
          intForArray3 = 0;
          intYear3 = yearpassedin + 1;
          break;
        case 12:
          intForArray1 = 11;
          intForArray2 = 0;
          intForArray3 = 1;
          intYear2 = yearpassedin + 1;
          intYear3 = yearpassedin + 1;
          break;
      }  
    String monthHeader = String.valueOf(String.valueOf(this.calendarMonths[intForArray1])) + "-" + String.valueOf(intYear1);
    AllSelections.put(monthHeader, selectionsPerMonth1);
    monthHeader = String.valueOf(String.valueOf(this.calendarMonths[intForArray2])) + "-" + String.valueOf(intYear2);
    AllSelections.put(monthHeader, selectionsPerMonth2);
    monthHeader = String.valueOf(String.valueOf(this.calendarMonths[intForArray3])) + "-" + String.valueOf(intYear3);
    AllSelections.put(monthHeader, selectionsPerMonth3);
    return AllSelections;
  }
}


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\ReleaseCalendarHandler.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */