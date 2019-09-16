package WEB-INF.classes.com.universal.milestone;

import com.techempower.DataEntity;
import com.universal.milestone.Acl;
import com.universal.milestone.NotepadContentObject;
import com.universal.milestone.User;
import com.universal.milestone.UserPreferences;
import java.util.Calendar;
import java.util.Hashtable;
import java.util.Vector;

public class User extends DataEntity implements NotepadContentObject, Cloneable {
  protected Acl acl;
  
  protected String aclString;
  
  protected int userId;
  
  protected String login;
  
  protected String fullName;
  
  protected String password;
  
  protected String reportsTo;
  
  protected int owner;
  
  protected String location;
  
  protected int employedBy;
  
  protected String filter;
  
  protected String employedByString;
  
  protected int lastUpdatingUser;
  
  protected Calendar lastUpdateDate;
  
  protected long lastUpdatedCk;
  
  protected String email;
  
  protected String phone;
  
  protected String fax;
  
  protected String deptFilter;
  
  protected int inactive;
  
  public String RC_month;
  
  public String RC_year;
  
  public String RC_releasingFamily;
  
  public String RC_environment;
  
  public String RC_labelContact;
  
  public String RC_formatType;
  
  public String RC_status;
  
  public String RC_productType;
  
  public String RC_releaseType;
  
  public String SS_artistSearch;
  
  public String SS_titleSearch;
  
  public String SS_selectionNoSearch;
  
  public String SS_prefixIDSearch;
  
  public String SS_upcSearch;
  
  public String SS_streetDateSearch;
  
  public String SS_streetEndDateSearch;
  
  public String SS_configSearch;
  
  public String SS_subconfigSearch;
  
  public String SS_labelSearch;
  
  public String SS_companySearch;
  
  public String SS_contactSearch;
  
  public String SS_familySearch;
  
  public String SS_environmentSearch;
  
  public String SS_projectIDSearch;
  
  public String SS_productTypeSearch;
  
  public String SS_showAllSearch;
  
  public boolean SS_searchInitiated;
  
  public boolean newSelectionEditAccess;
  
  protected Hashtable releasingFamily;
  
  protected Vector assignedEnvironments;
  
  protected int administrator;
  
  protected Hashtable releasingFamilyLabelFamily;
  
  protected UserPreferences preferences;
  
  public Hashtable getCustomMethodBindings() {
    Hashtable userMethods = new Hashtable();
    userMethods.put("user_id", "setUserId");
    userMethods.put("name", "setLogin");
    userMethods.put("password", "setPassword");
    userMethods.put("full_name", "setName");
    userMethods.put("employed_by", "setEmployedBy");
    userMethods.put("access_tbl1", "setAclString");
    userMethods.put("report_to", "setReportsTo");
    userMethods.put("location", "setLocation");
    userMethods.put("access_tbl4", "setFilter");
    userMethods.put("access_tbl3", "setDeptFilter");
    userMethods.put("email", "setEmail");
    userMethods.put("phone", "setPhone");
    userMethods.put("fax", "setFax");
    userMethods.put("inactive", "setInactive");
    userMethods.put("administrator", "setAdministrator");
    return userMethods;
  }
  
  public User() {
    this.acl = null;
    this.aclString = null;
    this.userId = -1;
    this.login = null;
    this.fullName = null;
    this.password = null;
    this.reportsTo = null;
    this.owner = -1;
    this.location = null;
    this.employedBy = -1;
    this.filter = null;
    this.employedByString = null;
    this.lastUpdatedCk = -1L;
    this.email = null;
    this.phone = null;
    this.fax = null;
    this.deptFilter = null;
    this.inactive = -1;
    this.RC_month = "";
    this.RC_year = "";
    this.RC_releasingFamily = "";
    this.RC_environment = "";
    this.RC_labelContact = "";
    this.RC_formatType = "";
    this.RC_status = "";
    this.RC_productType = "";
    this.RC_releaseType = "";
    this.SS_artistSearch = "";
    this.SS_titleSearch = "";
    this.SS_selectionNoSearch = "";
    this.SS_prefixIDSearch = "";
    this.SS_upcSearch = "";
    this.SS_streetDateSearch = "";
    this.SS_streetEndDateSearch = "";
    this.SS_configSearch = "";
    this.SS_subconfigSearch = "";
    this.SS_labelSearch = "";
    this.SS_companySearch = "";
    this.SS_contactSearch = "";
    this.SS_familySearch = "";
    this.SS_environmentSearch = "";
    this.SS_projectIDSearch = "";
    this.SS_productTypeSearch = "";
    this.SS_showAllSearch = "";
    this.SS_searchInitiated = false;
    this.newSelectionEditAccess = false;
    this.releasingFamily = null;
    this.assignedEnvironments = null;
    this.administrator = 0;
    this.releasingFamilyLabelFamily = null;
    this.preferences = null;
    this.acl = new Acl();
  }
  
  public User(int userID) {
    this.acl = null;
    this.aclString = null;
    this.userId = -1;
    this.login = null;
    this.fullName = null;
    this.password = null;
    this.reportsTo = null;
    this.owner = -1;
    this.location = null;
    this.employedBy = -1;
    this.filter = null;
    this.employedByString = null;
    this.lastUpdatedCk = -1L;
    this.email = null;
    this.phone = null;
    this.fax = null;
    this.deptFilter = null;
    this.inactive = -1;
    this.RC_month = "";
    this.RC_year = "";
    this.RC_releasingFamily = "";
    this.RC_environment = "";
    this.RC_labelContact = "";
    this.RC_formatType = "";
    this.RC_status = "";
    this.RC_productType = "";
    this.RC_releaseType = "";
    this.SS_artistSearch = "";
    this.SS_titleSearch = "";
    this.SS_selectionNoSearch = "";
    this.SS_prefixIDSearch = "";
    this.SS_upcSearch = "";
    this.SS_streetDateSearch = "";
    this.SS_streetEndDateSearch = "";
    this.SS_configSearch = "";
    this.SS_subconfigSearch = "";
    this.SS_labelSearch = "";
    this.SS_companySearch = "";
    this.SS_contactSearch = "";
    this.SS_familySearch = "";
    this.SS_environmentSearch = "";
    this.SS_projectIDSearch = "";
    this.SS_productTypeSearch = "";
    this.SS_showAllSearch = "";
    this.SS_searchInitiated = false;
    this.newSelectionEditAccess = false;
    this.releasingFamily = null;
    this.assignedEnvironments = null;
    this.administrator = 0;
    this.releasingFamilyLabelFamily = null;
    this.preferences = null;
    this.userId = userID;
  }
  
  public Acl getAcl() { return this.acl; }
  
  public void setAcl(Acl acl) { this.acl = acl; }
  
  public int getUserId() { return this.userId; }
  
  public void setUserId(int userId) { this.userId = userId; }
  
  public String getName() { return this.fullName; }
  
  public void setName(String fullName) { this.fullName = fullName; }
  
  public String getLogin() { return this.login; }
  
  public void setLogin(String login) { this.login = login; }
  
  public String getPassword() { return this.password; }
  
  public void setPassword(String password) { this.password = password; }
  
  public String getReportsTo() { return this.reportsTo; }
  
  public void setReportsTo(String reportsTo) { this.reportsTo = reportsTo; }
  
  public void setInactive(int inactive) { this.inactive = inactive; }
  
  public int getInactive() { return this.inactive; }
  
  public int getOwner() { return this.owner; }
  
  public void setOwner(int owner) { this.owner = owner; }
  
  public String getLocation() { return this.location; }
  
  public void setLocation(String location) { this.location = location; }
  
  public int getEmployedBy() { return this.employedBy; }
  
  public void setEmployedBy(int employedBy) { this.employedBy = employedBy; }
  
  public String getEmployedByString() { return this.employedByString; }
  
  public void setEmployedByString(String employedByString) { this.employedByString = employedByString; }
  
  public String getAclString() { return this.aclString; }
  
  public void setAclString(String aclString) { this.aclString = aclString; }
  
  public String toString() {
    return "--User Info--\n\nUserId:  " + 
      getUserId() + 
      "\nName:    " + getName() + 
      "\nLogin:   " + getLogin() + 
      "\nPassword:" + getPassword() + 
      "\nAcl string:" + getAclString() + 
      "\n--end user info--";
  }
  
  public long getLastUpdatedCk() { return this.lastUpdatedCk; }
  
  public void setLastUpdatedCk(long lastUpdatedCk) { this.lastUpdatedCk = lastUpdatedCk; }
  
  public int getLastUpdatingUser() { return this.lastUpdatingUser; }
  
  public void setLastUpdatingUser(int updatingUser) { this.lastUpdatingUser = updatingUser; }
  
  public Calendar getLastUpdateDate() { return this.lastUpdateDate; }
  
  public void setLastUpdateDate(Calendar lastUpdateDate) { this.lastUpdateDate = lastUpdateDate; }
  
  public String getNotepadContentObjectId() { return Integer.toString(this.userId); }
  
  public String getFilter() { return this.filter; }
  
  public void setFilter(String filter) { this.filter = filter; }
  
  public Object clone() throws CloneNotSupportedException { return super.clone(); }
  
  public String getEmail() { return this.email; }
  
  public void setEmail(String email) { this.email = email; }
  
  public String getPhone() { return this.phone; }
  
  public void setPhone(String phone) { this.phone = phone; }
  
  public String getFax() { return this.fax; }
  
  public void setFax(String fax) { this.fax = fax; }
  
  public String getDeptFilter() { return this.deptFilter; }
  
  public void setDeptFilter(String deptFilter) { this.deptFilter = deptFilter; }
  
  public void setReleasingFamily(Hashtable releasingFamily) { this.releasingFamily = releasingFamily; }
  
  public Hashtable getReleasingFamily() { return this.releasingFamily; }
  
  public void setAssignedEnvironments(Vector assignedEnvironments) { this.assignedEnvironments = assignedEnvironments; }
  
  public Vector getAssignedEnvironments() { return this.assignedEnvironments; }
  
  public Hashtable getReleasingFamilyLabelFamily() { return this.releasingFamilyLabelFamily; }
  
  public void setReleasingFamilyLabelFamily(Hashtable releasingFamilyLabelFamily) { this.releasingFamilyLabelFamily = releasingFamilyLabelFamily; }
  
  public int getAdministrator() { return this.administrator; }
  
  public void setAdministrator(int administrator) { this.administrator = administrator; }
  
  public UserPreferences getPreferences() { return this.preferences; }
  
  public void setPreferences(UserPreferences preferences) { this.preferences = preferences; }
}


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\User.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */