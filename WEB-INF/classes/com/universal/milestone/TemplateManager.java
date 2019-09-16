package WEB-INF.classes.com.universal.milestone;

import com.techempower.ComponentLog;
import com.techempower.EnhancedProperties;
import com.techempower.gemini.Context;
import com.techempower.gemini.GeminiApplication;
import com.universal.milestone.Cache;
import com.universal.milestone.Company;
import com.universal.milestone.CompanyAcl;
import com.universal.milestone.Day;
import com.universal.milestone.Family;
import com.universal.milestone.JdbcConnector;
import com.universal.milestone.MilestoneConstants;
import com.universal.milestone.MilestoneHelper;
import com.universal.milestone.Notepad;
import com.universal.milestone.NotepadSortOrder;
import com.universal.milestone.NotepadSortOrderTemplate;
import com.universal.milestone.ProductCategory;
import com.universal.milestone.ReleaseType;
import com.universal.milestone.Task;
import com.universal.milestone.TaskManager;
import com.universal.milestone.Template;
import com.universal.milestone.TemplateManager;
import com.universal.milestone.User;
import java.util.Vector;

public class TemplateManager implements MilestoneConstants {
  public static final String COMPONENT_CODE = "mTmp";
  
  public static final String DEFAULT_ORDER = " ORDER BY a.[name]";
  
  public static final String DEFAULT_TASK_ORDER = " ORDER BY name";
  
  protected static TemplateManager templateManager = null;
  
  protected static ComponentLog log;
  
  public static void configure(EnhancedProperties props, GeminiApplication application) { log = application.getLog("mTmp"); }
  
  public static TemplateManager getInstance() {
    if (templateManager == null)
      templateManager = new TemplateManager(); 
    return templateManager;
  }
  
  public Template getTemplate(int id, boolean getTimestamp) {
    String templateQuery = "SELECT * FROM vi_Template_Header WHERE template_Id = " + 
      
      id + 
      ";";
    Template template = null;
    JdbcConnector connector = MilestoneHelper.getConnector(templateQuery);
    connector.runQuery();
    if (connector.more()) {
      template = new Template();
      template.setTemplateID(connector.getIntegerField("template_id"));
      template.setTemplateName(connector.getField("name", ""));
      String comments = connector.getField("comments", "");
      if (comments.length() > 0)
        template.setComments(comments); 
      if (connector.getField("product_line", "").length() > 0)
        template.setProductCategory((ProductCategory)MilestoneHelper.getLookupObject(connector.getField("product_line", ""), Cache.getProductCategories())); 
      if (connector.getIntegerField("owner") > 0)
        template.setOwner((Family)MilestoneHelper.getStructureObject(connector.getIntegerField("owner"))); 
      if (connector.getField("release_type", "").length() > 0)
        template.setReleaseType((ReleaseType)MilestoneHelper.getLookupObject(connector.getField("release_type", ""), Cache.getReleaseTypes())); 
      if (connector.getField("configuration", "").length() > 0)
        template.setSelectionConfig(MilestoneHelper.getSelectionConfigObject(connector.getField("configuration", ""), Cache.getSelectionConfigs())); 
      if (connector.getField("sub_configuration", "").length() > 0)
        template.setSelectionSubConfig(MilestoneHelper.getSelectionSubConfigObject(connector.getField("sub_configuration", ""), template.getSelectionConfig())); 
      template.setProdType(connector.getIntegerField("productType"));
      String tasksQuery = "SELECT * FROM vi_Task WHERE task_id IN (SELECT task_id FROM vi_Template_Detail  WHERE template_id = " + 
        
        template.getTemplateID() + ")" + 
        " ORDER BY weeks_to_release DESC, day_of_week ASC, name ASC;";
      Task task = null;
      Vector precache = new Vector();
      JdbcConnector taskConnector = MilestoneHelper.getConnector(tasksQuery);
      taskConnector.runQuery();
      while (taskConnector.more()) {
        if (taskConnector.getIntegerField("task_id") > 0)
          precache.addElement(TaskManager.getInstance().getTask(taskConnector.getIntegerField("task_id"), true)); 
        taskConnector.next();
      } 
      template.setTasks(precache);
      String lastDateString = connector.getFieldByName("last_updated_on");
      if (lastDateString != null)
        template.setLastUpdateDate(MilestoneHelper.getDatabaseDate(lastDateString)); 
      template.setLastUpdatingUser(connector.getIntegerField("last_updated_by"));
      if (getTimestamp) {
        long lastUpdatedLong = Long.parseLong(connector.getField("last_updated_ck"), 16);
        template.setLastUpdatedCk(lastUpdatedLong);
      } 
      taskConnector.close();
    } 
    connector.close();
    return template;
  }
  
  public Template getNotepadTemplate(JdbcConnector connector) {
    Template template = null;
    if (connector != null) {
      template = new Template();
      template.setTemplateID(connector.getIntegerField("template_id"));
      template.setTemplateName(connector.getFieldByName("name"));
      if (connector.getIntegerField("owner") > 0)
        template.setOwner((Family)MilestoneHelper.getStructureObject(connector.getIntegerField("owner"))); 
      if (connector.getField("configuration", "").length() > 0)
        template.setSelectionConfig(MilestoneHelper.getSelectionConfigObject(connector.getField("configuration", ""), Cache.getSelectionConfigs())); 
      if (connector.getField("product_line", "").length() > 0)
        template.setProductCategory((ProductCategory)MilestoneHelper.getLookupObject(connector.getField("product_line", ""), Cache.getProductCategories())); 
      if (connector.getIntegerField("owner") > 0)
        template.setOwner((Family)MilestoneHelper.getStructureObject(connector.getIntegerField("owner"))); 
      if (connector.getField("release_type", "").length() > 0)
        template.setReleaseType((ReleaseType)MilestoneHelper.getLookupObject(connector.getField("release_type", ""), Cache.getReleaseTypes())); 
      if (connector.getField("sub_configuration", "").length() > 0)
        template.setSelectionSubConfig(MilestoneHelper.getSelectionSubConfigObject(connector.getField("sub_configuration", ""), template.getSelectionConfig())); 
      if (connector.getIntegerField("productType") > 0)
        template.setProdType(connector.getIntegerField("productType")); 
    } 
    return template;
  }
  
  public Template saveTemplate(Template template, int userID) {
    long timestamp = template.getLastUpdatedCk();
    int owner = -1;
    if (template.getOwner() != null)
      owner = template.getOwner().getStructureID(); 
    String product = "";
    if (template.getProductCategory() != null)
      product = template.getProductCategory().getAbbreviation(); 
    String release = "";
    if (template.getReleaseType() != null)
      release = template.getReleaseType().getAbbreviation(); 
    String config = "";
    if (template.getSelectionConfig() != null)
      config = template.getSelectionConfig().getSelectionConfigurationAbbreviation(); 
    String subconfig = "";
    if (template.getSelectionSubConfig() != null)
      subconfig = template.getSelectionSubConfig().getSelectionSubConfigurationAbbreviation(); 
    String tempName = "";
    if (template.getTempateName() != null)
      tempName = template.getTempateName(); 
    String comments = "";
    if (template.getComments() != null)
      comments = template.getComments(); 
    String query = "sp_sav_Template_Header " + 
      template.getTemplateID() + "," + 
      "'" + MilestoneHelper.escapeSingleQuotes(tempName) + "'," + 
      owner + "," + 
      "'" + MilestoneHelper.escapeSingleQuotes(product) + "'," + 
      "'" + MilestoneHelper.escapeSingleQuotes(release) + "'," + 
      "'" + MilestoneHelper.escapeSingleQuotes(config) + "'," + 
      "'" + MilestoneHelper.escapeSingleQuotes(subconfig) + "'," + 
      "'" + MilestoneHelper.escapeSingleQuotes(comments) + "'," + 
      userID + "," + 
      timestamp + "," + 
      template.getProdType();
    JdbcConnector connector = MilestoneHelper.getConnector(query);
    connector.runQuery();
    int newTemplateID = -1;
    if (template.getTemplateID() < 0) {
      newTemplateID = connector.getInt("ReturnID");
    } else {
      newTemplateID = template.getTemplateID();
    } 
    connector.close();
    return getInstance().getTemplate(newTemplateID, true);
  }
  
  public Template saveTemplateCopiedTemplate(Template template, int userID) {
    long timestamp = template.getLastUpdatedCk();
    int owner = -1;
    if (template.getOwner() != null)
      owner = template.getOwner().getStructureID(); 
    String product = "";
    if (template.getProductCategory() != null)
      product = template.getProductCategory().getAbbreviation(); 
    String release = "";
    if (template.getReleaseType() != null)
      release = template.getReleaseType().getAbbreviation(); 
    String config = "";
    if (template.getSelectionConfig() != null)
      config = template.getSelectionConfig().getSelectionConfigurationAbbreviation(); 
    String subconfig = "";
    if (template.getSelectionSubConfig() != null)
      subconfig = template.getSelectionSubConfig().getSelectionSubConfigurationAbbreviation(); 
    String tempName = "";
    if (template.getTempateName() != null)
      tempName = template.getTempateName(); 
    String comments = "";
    if (template.getComments() != null)
      comments = template.getComments(); 
    String query = "sp_sav_Template_Header " + 
      template.getTemplateID() + "," + 
      "'" + MilestoneHelper.escapeSingleQuotes(tempName) + "'," + 
      owner + "," + 
      "'" + MilestoneHelper.escapeSingleQuotes(product) + "'," + 
      "'" + MilestoneHelper.escapeSingleQuotes(release) + "'," + 
      "'" + MilestoneHelper.escapeSingleQuotes(config) + "'," + 
      "'" + MilestoneHelper.escapeSingleQuotes(subconfig) + "'," + 
      "'" + MilestoneHelper.escapeSingleQuotes(comments) + "'," + 
      userID + "," + 
      timestamp + "," + 
      template.getProdType();
    JdbcConnector connector = MilestoneHelper.getConnector(query);
    connector.setQuery(query);
    connector.runQuery();
    int newTemplateID = -1;
    if (template.getTemplateID() < 0)
      newTemplateID = connector.getInt("ReturnID"); 
    connector.close();
    if (newTemplateID > 0)
      if (template.getTasks() != null) {
        Vector tasks = template.getTasks();
        for (int i = 0; i < tasks.size(); i++) {
          Task task = (Task)tasks.elementAt(i);
          int sort = 0;
          String queryDetails = "sp_ins_Template_Detail " + 
            newTemplateID + "," + 
            task.getTaskID() + "," + 
            sort + "," + 
            userID;
          JdbcConnector connectorDetail = MilestoneHelper.getConnector(queryDetails);
          connectorDetail.setQuery(queryDetails);
          connectorDetail.runQuery();
          connectorDetail.close();
        } 
      }  
    return getInstance().getTemplate(newTemplateID, true);
  }
  
  public void setNotepadQuery(Context context, int UserId, Notepad notepad) {
    log.debug("TEMPLATE MGR SETTING NOTEPAD QUERY FOR SEARCH");
    if (notepad != null) {
      String templateNameSrch = context.getParameter("templateNameSrch");
      String configurationSrch = context.getParameter("configurationSrch");
      String ownerSrch = context.getParameter("ownerSrch");
      String params = "";
      String productSearch = context.getParameter("ProdTypeSearch");
      String query = getDefaultSearchQuery(context);
      NotepadSortOrderTemplate notepadSortOrder = NotepadSortOrder.getNotepadSortOrderFromSession(context).getNotepadSortOrderTemplate();
      notepadSortOrder.setTemplateOrderBy("");
      notepadSortOrder.setTemplateOrderCol("Template");
      if (MilestoneHelper.isStringNotEmpty(templateNameSrch))
        query = String.valueOf(query) + MilestoneHelper.addQueryParams(query, " a.[name] " + 
            MilestoneHelper.setWildCardsEscapeSingleQuotes(templateNameSrch)); 
      if (MilestoneHelper.isStringNotEmpty(configurationSrch) && !configurationSrch.equals("0"))
        query = String.valueOf(query) + MilestoneHelper.addQueryParams(query, " a.[configuration] = '" + 
            MilestoneHelper.escapeSingleQuotes(configurationSrch) + "'"); 
      if (MilestoneHelper.isStringNotEmpty(ownerSrch) && !ownerSrch.equals("0"))
        query = String.valueOf(query) + MilestoneHelper.addQueryParams(query, " a.[owner] = '" + 
            MilestoneHelper.escapeSingleQuotes(ownerSrch) + "'"); 
      if (MilestoneHelper.isStringNotEmpty(productSearch) && !productSearch.equals("2"))
        query = String.valueOf(query) + MilestoneHelper.addQueryParams(query, " a.[productType] = " + 
            MilestoneHelper.escapeSingleQuotes(productSearch)); 
      String order = " ORDER BY a.[name]";
      log.debug("Notepad TTTTTTTT template query:\n" + query);
      notepad.setSearchQuery(query);
      notepad.setOrderBy(order);
    } 
  }
  
  public Vector getTemplateNotepadList(Context context, Notepad notepad) {
    String query = "";
    Template template = null;
    Vector precache = new Vector();
    if (notepad != null && !notepad.getSearchQuery().equals("")) {
      query = notepad.getSearchQuery();
      query = String.valueOf(query) + notepad.getOrderBy();
      log.debug("notepad TTTTTTTT getting preset query :\n" + query);
    } else {
      log.debug("notepad TTTTTTTT doing default query");
      query = getDefaultSearchQuery(context);
      query = String.valueOf(query) + " ORDER BY a.[name]";
    } 
    log.debug("notepad TTTTTTTT template query being used :\n" + query);
    JdbcConnector connector = MilestoneHelper.getConnector(query);
    connector.runQuery();
    while (connector.more()) {
      template = getNotepadTemplate(connector);
      precache.addElement(template);
      template = null;
      connector.next();
    } 
    connector.close();
    return precache;
  }
  
  public void deleteTemplate(Template template, int userID) {
    String query = "sp_del_Templates " + 
      template.getTemplateID();
    JdbcConnector connector = MilestoneHelper.getConnector(query);
    connector.runQuery();
    connector.close();
  }
  
  public void deleteTemplateDetail(Template template, Task task, int userID) {
    String query = "sp_del_Template_Detail " + 
      template.getTemplateID() + "," + 
      task.getTaskID();
    JdbcConnector connector = MilestoneHelper.getConnector(query);
    connector.runQuery();
    connector.close();
  }
  
  public void addTask(Template template, Task task, int userID) {
    int sortId = 1;
    String query = "sp_ins_Template_Detail " + 
      template.getTemplateID() + "," + 
      task.getTaskID() + "," + 
      sortId + "," + 
      userID;
    JdbcConnector connector = MilestoneHelper.getConnector(query);
    connector.runQuery();
    connector.close();
  }
  
  public static String getDefaultSearchQuery(Context context) {
    query = "SELECT * FROM vi_Template_Header ";
    query = "SELECT * FROM vi_Template_Header a LEFT JOIN vi_structure b \ton a.[owner] = b.[structure_id] ";
    return String.valueOf(query) + MilestoneHelper.getOwnerCompanyWhereClause(context);
  }
  
  public String getDefaultTaskSearchQuery(int templateId) {
    query = "";
    return "SELECT * FROM vi_Task WHERE task_id  NOT IN (SELECT task_id FROM vi_Template_Detail  WHERE template_id = " + 
      
      templateId + ")";
  }
  
  public Vector getTemplateTaskNotepadList(int templateId, int userId, Notepad notepad, Context context) {
    String query = "";
    if (notepad != null && !notepad.getSearchQuery().equals("")) {
      query = notepad.getSearchQuery();
      query = String.valueOf(query) + notepad.getOrderBy();
    } else {
      query = String.valueOf(getDefaultTaskSearchQuery(templateId)) + " ORDER BY name";
    } 
    log.debug("++++++++++++++++++++ template query " + query);
    Task task = null;
    Vector precache = new Vector();
    Vector companies = MilestoneHelper.getUserCompanies(context);
    JdbcConnector connector = MilestoneHelper.getConnector(query);
    connector.runQuery();
    while (connector.more()) {
      task = getTemplateTaskNotepadObject(connector);
      if (task != null) {
        Company company = null;
        if (companies != null)
          for (int j = 0; j < companies.size(); j++) {
            company = (Company)companies.get(j);
            if (company != null && company.getParentEnvironment().getParentFamily() != null && task.getOwner() != null)
              if (company.getParentEnvironment().getParentFamily().getStructureID() == task.getOwner().getStructureID()) {
                if (task.getActiveFlag())
                  precache.addElement(task); 
                break;
              }  
          }  
      } 
      task = null;
      connector.next();
    } 
    connector.close();
    return precache;
  }
  
  public Task getTemplateTaskNotepadObject(JdbcConnector connector) {
    Task task = null;
    if (connector != null) {
      task = new Task();
      task.setTaskID(connector.getIntegerField("task_id"));
      task.setTaskName(connector.getField("name", ""));
      task.setWeeksToRelease(connector.getIntegerField("weeks_to_release"));
      task.setDayOfTheWeek(new Day(connector.getIntegerField("day_of_week")));
      task.setDepartment(connector.getField("department", ""));
      task.setOwner((Family)MilestoneHelper.getStructureObject(connector.getIntegerField("owner")));
      task.setTaskAbbreviation(connector.getIntegerField("abbrev_id"));
      task.setIsKeyTask(connector.getBoolean("key_task_indicator"));
      task.setActiveFlag(connector.getBoolean("active_flag"));
    } 
    return task;
  }
  
  public int getTaskEditAccess(User user, int familyID) {
    Vector companyAcls = user.getAcl().getCompanyAcl();
    int access = 0;
    for (int i = 0; i < companyAcls.size(); i++) {
      CompanyAcl companyAcl = (CompanyAcl)companyAcls.get(i);
      Company company = null;
      if (companyAcl != null) {
        company = (Company)MilestoneHelper.getStructureObject(companyAcl
            .getCompanyId());
        int familyIdCk = -1;
        if (company != null)
          familyIdCk = company.getParentEnvironment().getParentFamily()
            .getStructureID(); 
        if (familyIdCk == familyID)
          if (companyAcl.getAccessTemplate() > access)
            access = companyAcl.getAccessTemplate();  
      } 
    } 
    return access;
  }
  
  public void setTaskNotepadQuery(Context context, Notepad notepad) {
    if (notepad != null) {
      int templateId = ((Template)context.getSessionValue("Template")).getTemplateID();
      String taskNameSearch = context.getParameter("TaskNameSearch");
      String keyTaskSearch = context.getParameter("KeyTaskSearch");
      String taskOwnerSearch = context.getParameter("TaskOwnerSearch");
      String taskDepartmentSearch = context.getParameter("TaskDepartmentSearch");
      int keyTask = -1;
      if (keyTaskSearch != null && keyTaskSearch.equalsIgnoreCase("yes")) {
        keyTask = 1;
      } else if (keyTaskSearch != null && keyTaskSearch.equalsIgnoreCase("no")) {
        keyTask = 0;
      } 
      String query = "SELECT * FROM vi_Task WHERE task_id  not in (select task_id from vi_Template_Detail  where template_id = " + 
        
        templateId + ")";
      if (keyTask > -1)
        query = String.valueOf(query) + " AND key_task_indicator = " + keyTask; 
      if (MilestoneHelper.isStringNotEmpty(taskNameSearch))
        query = String.valueOf(query) + " AND name " + MilestoneHelper.setWildCardsEscapeSingleQuotes(taskNameSearch); 
      if (MilestoneHelper.isStringNotEmpty(taskOwnerSearch) && !taskOwnerSearch.equals("0"))
        query = String.valueOf(query) + " AND owner = '" + taskOwnerSearch + "'"; 
      if (MilestoneHelper.isStringNotEmpty(taskDepartmentSearch) && !taskDepartmentSearch.equals("0") && !taskDepartmentSearch.equals("-1"))
        query = String.valueOf(query) + " AND department like '%" + taskDepartmentSearch + "%'"; 
      String order = " ORDER BY name";
      notepad.setSearchQuery(query);
      notepad.setOrderBy(order);
    } 
  }
  
  public boolean isTimestampValid(Template template) {
    if (template != null) {
      String timestampQuery = "SELECT last_updated_ck  FROM vi_Template_Header WHERE template_Id = " + 
        
        template.getTemplateID() + 
        ";";
      JdbcConnector connectorTimestamp = MilestoneHelper.getConnector(timestampQuery);
      connectorTimestamp.runQuery();
      if (connectorTimestamp.more())
        if (template.getLastUpdatedCk() != Long.parseLong(connectorTimestamp.getField("last_updated_ck", ""), 16)) {
          connectorTimestamp.close();
          return false;
        }  
      connectorTimestamp.close();
      return true;
    } 
    return false;
  }
  
  public boolean isDuplicate(Template template) {
    boolean isDuplicate = false;
    if (template != null) {
      String query = "SELECT * FROM vi_template_header WHERE  name = '" + 
        MilestoneHelper.escapeSingleQuotes(template.getTempateName()) + "'" + 
        " AND template_id <> " + template.getTemplateID();
      log.debug("TTTTTTTTTTTTTTTT duplicate query = " + query);
      JdbcConnector connector = MilestoneHelper.getConnector(query);
      connector.runQuery();
      if (connector.more())
        isDuplicate = true; 
      connector.close();
    } 
    return isDuplicate;
  }
}


/* Location:              D:\Documents\NetBeansProjects\milestone2 Local.war!\WEB-INF\classes\co\\universal\milestone\TemplateManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */