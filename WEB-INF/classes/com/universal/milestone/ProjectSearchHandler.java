package WEB-INF.classes.com.universal.milestone;

import com.techempower.ComponentLog;
import com.techempower.gemini.Context;
import com.techempower.gemini.Dispatcher;
import com.techempower.gemini.FormDropDownMenu;
import com.techempower.gemini.FormHidden;
import com.techempower.gemini.FormRadioButtonGroup;
import com.techempower.gemini.FormTextField;
import com.techempower.gemini.GeminiApplication;
import com.universal.milestone.Form;
import com.universal.milestone.MilestoneHelper;
import com.universal.milestone.Notepad;
import com.universal.milestone.ProjectSearchHandler;
import com.universal.milestone.ProjectSearchManager;
import com.universal.milestone.SecureHandler;
import com.universal.milestone.Selection;
import com.universal.milestone.SelectionManager;
import com.universal.milestone.User;
import java.util.Vector;

public class ProjectSearchHandler extends SecureHandler {
  public static final String COMPONENT_CODE = "hSel";
  
  public GeminiApplication application;
  
  public ComponentLog log;
  
  public ProjectSearchHandler(GeminiApplication application) {
    this.application = application;
    this.log = application.getLog("hSel");
  }
  
  public String getDescription() { return "ProjectSearch"; }
  
  public boolean acceptRequest(Dispatcher dispatcher, Context context, String command) {
    if (super.acceptRequest(dispatcher, context, command))
      if (command.startsWith("project-search"))
        return handleRequest(dispatcher, context, command);  
    return false;
  }
  
  public boolean handleRequest(Dispatcher dispatcher, Context context, String command) {
    if (command.equalsIgnoreCase("project-search")) {
      context.putSessionValue("selectionScreenType", "new");
      projectSearchEdit(dispatcher, context, command);
    } else if (command.equalsIgnoreCase("project-search-revise")) {
      context.putSessionValue("selectionScreenType", "revise");
      projectSearchEdit(dispatcher, context, command);
    } else if (command.equalsIgnoreCase("project-search-results")) {
      projectSearchResults(dispatcher, context, command);
    } else if (command.equalsIgnoreCase("project-search-results-order")) {
      sortProjectSearchResults(dispatcher, context, command);
    } else if (command.equalsIgnoreCase("project-search-cancel")) {
      projectCancel(dispatcher, context, command);
    } else if (command.equalsIgnoreCase("project-search-clear")) {
      projectClear(dispatcher, context, command);
    } else if (command.equalsIgnoreCase("project-search-goto-selection")) {
      projectGotoSelection(dispatcher, context, command);
    } 
    return true;
  }
  
  private boolean search(Dispatcher dispatcher, Context context, String command) {
    Notepad notepad = MilestoneHelper.getNotepadFromSession(0, context);
    notepad.setAllContents(null);
    notepad.setSelected(null);
    SelectionManager.getInstance().setSelectionNotepadQuery(context, ((User)context.getSessionValue("user")).getUserId(), notepad, null);
    if (command.equals("selection-search")) {
      dispatcher.redispatch(context, "selection-editor");
    } else {
      dispatcher.redispatch(context, "selection-manufacturing-editor");
    } 
    return true;
  }
  
  private boolean projectSearchEdit(Dispatcher dispatcher, Context context, String command) {
    User user = (User)context.getSessionValue("user");
    context.removeSessionValue("searchResults");
    Form form = null;
    form = buildNewForm(context, command);
    context.putDelivery("Form", form);
    String sortColumn = "";
    String sortDirection = "";
    if (context.getParameter("sortColumn") != null)
      sortColumn = context.getParameter("sortColumn"); 
    if (context.getParameter("sortDirection") != null)
      sortDirection = context.getParameter("sortDirection"); 
    context.putSessionValue("sortingColumn", sortColumn);
    context.putSessionValue("sortingDirection", sortDirection);
    return context.includeJSP("project-search.jsp");
  }
  
  private boolean projectGotoSelection(Dispatcher dispatcher, Context context, String command) {
    Selection selection = new Selection();
    User user = (User)context.getSessionValue("user");
    String counter = context.getParameter("selectedCounter");
    context.putSessionValue("selectionScreenTypeIndex", counter);
    if (context.getParameter("isPhysical").equals("Digital"))
      return dispatcher.redispatch(context, "selection-edit-new-digital-archie-project"); 
    return dispatcher.redispatch(context, "selection-edit-new-physical-archie-project");
  }
  
  private boolean projectCancel(Dispatcher dispatcher, Context context, String command) {
    context.removeSessionValue("searchResults");
    context.removeSessionValue("selectionScreenType");
    dispatcher.redispatch(context, "selection-editor");
    return true;
  }
  
  private boolean projectClear(Dispatcher dispatcher, Context context, String command) { return projectSearchEdit(dispatcher, context, command); }
  
  private boolean sortProjectSearchResults(Dispatcher dispatcher, Context context, String command) {
    User user = (User)context.getSessionValue("user");
    Vector searchResults = new Vector();
    if (context.getSessionValue("searchResults") != null)
      searchResults = (Vector)context.getSessionValue("searchResults"); 
    Vector archieProjects = ProjectSearchManager.getInstance().sortResults(context, user, searchResults, false);
    Form form = null;
    form = buildForm(context, command);
    context.putDelivery("Form", form);
    String sortColumn = "";
    String sortDirection = "";
    if (context.getParameter("sortColumn") != null)
      sortColumn = context.getParameter("sortColumn"); 
    if (context.getParameter("sortDirection") != null)
      sortDirection = context.getParameter("sortDirection"); 
    context.putSessionValue("sortingColumn", sortColumn);
    context.putSessionValue("sortingDirection", sortDirection);
    context.putSessionValue("searchResults", archieProjects);
    return context.includeJSP("project-search.jsp");
  }
  
  private boolean projectSearchResults(Dispatcher dispatcher, Context context, String command) {
    User user = (User)context.getSessionValue("user");
    try {
      Vector archieProjects = ProjectSearchManager.getInstance().getArchieProjects(context, user);
      Form form = null;
      form = buildForm(context, command);
      context.putDelivery("Form", form);
      String sortColumn = "";
      String sortDirection = "";
      if (context.getParameter("sortColumn") != null)
        sortColumn = context.getParameter("sortColumn"); 
      if (context.getParameter("sortDirection") != null)
        sortDirection = context.getParameter("sortDirection"); 
      context.putSessionValue("sortingColumn", sortColumn);
      context.putSessionValue("sortingDirection", sortDirection);
      context.putSessionValue("searchResults", archieProjects);
      return context.includeJSP("project-search.jsp");
    } catch (Exception exception) {
      System.out.println("ATLAS WEBSERVICE ERROR: " + exception);
      context.putSessionValue("atlasFailureException", exception);
      return context.includeJSP("project-search-results.jsp");
    } 
  }
  
  protected Form buildForm(Context context, String command) {
    Form projectSearchForm = new Form(this.application, "projectSearchForm", 
        this.application.getInfrastructure().getServletURL(), "POST");
    User user = (User)context.getSession().getAttribute("user");
    int userId = user.getUserId();
    projectSearchForm.addElement(new FormHidden("cmd", command, true));
    Vector companies = null;
    companies = MilestoneHelper.getUserCompanies(context);
    String radioSelected = "Physical";
    if (context.getParameter("isPhysical").equals("Digital"))
      radioSelected = "Digital"; 
    FormRadioButtonGroup isPhysical = new FormRadioButtonGroup("isPhysical", radioSelected, "Physical, Digital", false);
    isPhysical.setTabIndex(1);
    projectSearchForm.addElement(isPhysical);
    FormTextField artistFirstName = new FormTextField("artistFirstName2", context.getParameter("artistFirstName2"), true, 20, 50);
    artistFirstName.setTabIndex(2);
    artistFirstName.setClassName("ctrlMedium");
    projectSearchForm.addElement(artistFirstName);
    FormTextField artistLastName = new FormTextField("artistLastName", context.getParameter("artistLastName"), true, 20, 50);
    artistLastName.setTabIndex(3);
    artistLastName.setClassName("ctrlMedium");
    projectSearchForm.addElement(artistLastName);
    FormTextField projectTitle = new FormTextField("title", context.getParameter("title"), true, 73, 125);
    projectTitle.setTabIndex(4);
    projectTitle.setClassName("ctrlMedium");
    projectSearchForm.addElement(projectTitle);
    FormTextField projectDesc = new FormTextField("projectDesc", context.getParameter("projectDesc"), true, 73, 125);
    projectDesc.setTabIndex(5);
    projectDesc.setClassName("ctrlMedium");
    projectSearchForm.addElement(projectDesc);
    FormTextField projectID = new FormTextField("projectID", context.getParameter("projectID"), true, 73, 125);
    projectID.setTabIndex(7);
    projectID.setClassName("ctrlMedium");
    projectSearchForm.addElement(projectID);
    Vector userCompanies = null;
    userCompanies = MilestoneHelper.getUserCompanies(context);
    Vector userEditableCompanies = ProjectSearchManager.getInstance().filterSelectionCompaniesWithEditRights(userCompanies, user);
    Vector userEditableLabels = MilestoneHelper.getUserLabels(userEditableCompanies);
    FormDropDownMenu labels = MilestoneHelper.getCorporateStructureDropDownDuplicates("labels", userEditableLabels, context.getParameter("labels"), false, true, true);
    labels.addFormEvent("onKeyPress", "scanKey(window.event.keyCode, 'document.all.labels.options', getMaxLength(document.all.labels.options))");
    labels.setTabIndex(6);
    projectSearchForm.addElement(labels);
    return projectSearchForm;
  }
  
  protected Form buildNewForm(Context context, String command) {
    Form projectSearchForm = new Form(this.application, "projectSearchForm", 
        this.application.getInfrastructure().getServletURL(), "POST");
    User user = (User)context.getSession().getAttribute("user");
    int userId = user.getUserId();
    projectSearchForm.addElement(new FormHidden("cmd", command, true));
    Vector companies = null;
    companies = MilestoneHelper.getUserCompanies(context);
    for (int c = 0; c < companies.size(); c++)
      System.out.println("companies:[" + companies.elementAt(c) + "]"); 
    FormRadioButtonGroup isPhysical = new FormRadioButtonGroup("isPhysical", "", "Physical, Digital", false);
    isPhysical.setTabIndex(1);
    projectSearchForm.addElement(isPhysical);
    FormTextField artistFirstName = new FormTextField("artistFirstName2", "", true, 20, 50);
    artistFirstName.setTabIndex(2);
    artistFirstName.setClassName("ctrlMedium");
    projectSearchForm.addElement(artistFirstName);
    FormTextField artistLastName = new FormTextField("artistLastName", "", true, 20, 50);
    artistLastName.setTabIndex(3);
    artistLastName.setClassName("ctrlMedium");
    projectSearchForm.addElement(artistLastName);
    FormTextField projectTitle = new FormTextField("title", "", true, 73, 125);
    projectTitle.setTabIndex(4);
    projectTitle.setClassName("ctrlMedium");
    projectSearchForm.addElement(projectTitle);
    FormTextField projectDesc = new FormTextField("projectDesc", "", true, 73, 125);
    projectDesc.setTabIndex(5);
    projectDesc.setClassName("ctrlMedium");
    projectSearchForm.addElement(projectDesc);
    FormTextField projectID = new FormTextField("projectID", "", true, 73, 125);
    projectID.setTabIndex(7);
    projectID.setClassName("ctrlMedium");
    projectSearchForm.addElement(projectID);
    Vector userCompanies = null;
    userCompanies = MilestoneHelper.getUserCompanies(context);
    Vector userEditableCompanies = ProjectSearchManager.getInstance().filterSelectionCompaniesWithEditRights(userCompanies, user);
    Vector userEditableLabels = MilestoneHelper.getUserLabels(userEditableCompanies);
    FormDropDownMenu labels = MilestoneHelper.getCorporateStructureDropDownDuplicates("labels", userEditableLabels, "", false, true, true);
    labels.addFormEvent("onKeyPress", "scanKey(window.event.keyCode, 'document.all.labels.options', getMaxLength(document.all.labels.options))");
    labels.setTabIndex(6);
    projectSearchForm.addElement(labels);
    return projectSearchForm;
  }
}


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\ProjectSearchHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */