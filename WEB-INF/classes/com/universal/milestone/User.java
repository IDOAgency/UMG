/*     */ package WEB-INF.classes.com.universal.milestone;
/*     */ 
/*     */ import com.techempower.DataEntity;
/*     */ import com.universal.milestone.Acl;
/*     */ import com.universal.milestone.NotepadContentObject;
/*     */ import com.universal.milestone.User;
/*     */ import com.universal.milestone.UserPreferences;
/*     */ import java.util.Calendar;
/*     */ import java.util.Hashtable;
/*     */ import java.util.Vector;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class User
/*     */   extends DataEntity
/*     */   implements NotepadContentObject, Cloneable
/*     */ {
/*     */   protected Acl acl;
/*     */   protected String aclString;
/*     */   protected int userId;
/*     */   protected String login;
/*     */   protected String fullName;
/*     */   protected String password;
/*     */   protected String reportsTo;
/*     */   protected int owner;
/*     */   protected String location;
/*     */   protected int employedBy;
/*     */   protected String filter;
/*     */   protected String employedByString;
/*     */   protected int lastUpdatingUser;
/*     */   protected Calendar lastUpdateDate;
/*     */   protected long lastUpdatedCk;
/*     */   protected String email;
/*     */   protected String phone;
/*     */   protected String fax;
/*     */   protected String deptFilter;
/*     */   protected int inactive;
/*     */   public String RC_month;
/*     */   public String RC_year;
/*     */   public String RC_releasingFamily;
/*     */   public String RC_environment;
/*     */   public String RC_labelContact;
/*     */   public String RC_formatType;
/*     */   public String RC_status;
/*     */   public String RC_productType;
/*     */   public String RC_releaseType;
/*     */   public String SS_artistSearch;
/*     */   public String SS_titleSearch;
/*     */   public String SS_selectionNoSearch;
/*     */   public String SS_prefixIDSearch;
/*     */   public String SS_upcSearch;
/*     */   public String SS_streetDateSearch;
/*     */   public String SS_streetEndDateSearch;
/*     */   public String SS_configSearch;
/*     */   public String SS_subconfigSearch;
/*     */   public String SS_labelSearch;
/*     */   public String SS_companySearch;
/*     */   public String SS_contactSearch;
/*     */   public String SS_familySearch;
/*     */   public String SS_environmentSearch;
/*     */   public String SS_projectIDSearch;
/*     */   public String SS_productTypeSearch;
/*     */   public String SS_showAllSearch;
/*     */   public boolean SS_searchInitiated;
/*     */   public boolean newSelectionEditAccess;
/*     */   protected Hashtable releasingFamily;
/*     */   protected Vector assignedEnvironments;
/*     */   protected int administrator;
/*     */   protected Hashtable releasingFamilyLabelFamily;
/*     */   protected UserPreferences preferences;
/*     */   
/*     */   public Hashtable getCustomMethodBindings() {
/* 117 */     Hashtable userMethods = new Hashtable();
/* 118 */     userMethods.put("user_id", "setUserId");
/* 119 */     userMethods.put("name", "setLogin");
/* 120 */     userMethods.put("password", "setPassword");
/* 121 */     userMethods.put("full_name", "setName");
/* 122 */     userMethods.put("employed_by", "setEmployedBy");
/* 123 */     userMethods.put("access_tbl1", "setAclString");
/* 124 */     userMethods.put("report_to", "setReportsTo");
/* 125 */     userMethods.put("location", "setLocation");
/* 126 */     userMethods.put("access_tbl4", "setFilter");
/* 127 */     userMethods.put("access_tbl3", "setDeptFilter");
/* 128 */     userMethods.put("email", "setEmail");
/* 129 */     userMethods.put("phone", "setPhone");
/* 130 */     userMethods.put("fax", "setFax");
/* 131 */     userMethods.put("inactive", "setInactive");
/* 132 */     userMethods.put("administrator", "setAdministrator");
/*     */ 
/*     */     
/* 135 */     return userMethods; } public User() { this.acl = null; this.aclString = null; this.userId = -1; this.login = null; this.fullName = null; this.password = null; this.reportsTo = null; this.owner = -1; this.location = null; this.employedBy = -1; this.filter = null; this.employedByString = null; this.lastUpdatedCk = -1L; this.email = null; this.phone = null; this.fax = null; this.deptFilter = null; this.inactive = -1; this.RC_month = ""; this.RC_year = ""; this.RC_releasingFamily = ""; this.RC_environment = ""; this.RC_labelContact = ""; this.RC_formatType = ""; this.RC_status = ""; this.RC_productType = ""; this.RC_releaseType = ""; this.SS_artistSearch = ""; this.SS_titleSearch = ""; this.SS_selectionNoSearch = ""; this.SS_prefixIDSearch = ""; this.SS_upcSearch = ""; this.SS_streetDateSearch = ""; this.SS_streetEndDateSearch = ""; this.SS_configSearch = ""; this.SS_subconfigSearch = ""; this.SS_labelSearch = ""; this.SS_companySearch = ""; this.SS_contactSearch = ""; this.SS_familySearch = ""; this.SS_environmentSearch = ""; this.SS_projectIDSearch = ""; this.SS_productTypeSearch = ""; this.SS_showAllSearch = "";
/*     */     this.SS_searchInitiated = false;
/*     */     this.newSelectionEditAccess = false;
/*     */     this.releasingFamily = null;
/*     */     this.assignedEnvironments = null;
/*     */     this.administrator = 0;
/*     */     this.releasingFamilyLabelFamily = null;
/*     */     this.preferences = null;
/* 143 */     this.acl = new Acl(); } public User(int userID) { this.acl = null; this.aclString = null; this.userId = -1; this.login = null; this.fullName = null; this.password = null; this.reportsTo = null; this.owner = -1; this.location = null; this.employedBy = -1; this.filter = null; this.employedByString = null; this.lastUpdatedCk = -1L; this.email = null; this.phone = null; this.fax = null; this.deptFilter = null; this.inactive = -1; this.RC_month = ""; this.RC_year = ""; this.RC_releasingFamily = ""; this.RC_environment = ""; this.RC_labelContact = ""; this.RC_formatType = ""; this.RC_status = ""; this.RC_productType = ""; this.RC_releaseType = ""; this.SS_artistSearch = ""; this.SS_titleSearch = ""; this.SS_selectionNoSearch = ""; this.SS_prefixIDSearch = ""; this.SS_upcSearch = ""; this.SS_streetDateSearch = ""; this.SS_streetEndDateSearch = ""; this.SS_configSearch = ""; this.SS_subconfigSearch = ""; this.SS_labelSearch = ""; this.SS_companySearch = ""; this.SS_contactSearch = ""; this.SS_familySearch = ""; this.SS_environmentSearch = ""; this.SS_projectIDSearch = ""; this.SS_productTypeSearch = ""; this.SS_showAllSearch = ""; this.SS_searchInitiated = false; this.newSelectionEditAccess = false;
/*     */     this.releasingFamily = null;
/*     */     this.assignedEnvironments = null;
/*     */     this.administrator = 0;
/*     */     this.releasingFamilyLabelFamily = null;
/*     */     this.preferences = null;
/* 149 */     this.userId = userID; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 157 */   public Acl getAcl() { return this.acl; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 165 */   public void setAcl(Acl acl) { this.acl = acl; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 173 */   public int getUserId() { return this.userId; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 181 */   public void setUserId(int userId) { this.userId = userId; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 189 */   public String getName() { return this.fullName; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 198 */   public void setName(String fullName) { this.fullName = fullName; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 206 */   public String getLogin() { return this.login; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 215 */   public void setLogin(String login) { this.login = login; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 223 */   public String getPassword() { return this.password; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 231 */   public void setPassword(String password) { this.password = password; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 239 */   public String getReportsTo() { return this.reportsTo; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 247 */   public void setReportsTo(String reportsTo) { this.reportsTo = reportsTo; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 255 */   public void setInactive(int inactive) { this.inactive = inactive; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 264 */   public int getInactive() { return this.inactive; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 272 */   public int getOwner() { return this.owner; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 280 */   public void setOwner(int owner) { this.owner = owner; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 288 */   public String getLocation() { return this.location; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 296 */   public void setLocation(String location) { this.location = location; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 304 */   public int getEmployedBy() { return this.employedBy; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 312 */   public void setEmployedBy(int employedBy) { this.employedBy = employedBy; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 320 */   public String getEmployedByString() { return this.employedByString; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 328 */   public void setEmployedByString(String employedByString) { this.employedByString = employedByString; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 337 */   public String getAclString() { return this.aclString; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 345 */   public void setAclString(String aclString) { this.aclString = aclString; }
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 350 */     return "--User Info--\n\nUserId:  " + 
/* 351 */       getUserId() + 
/* 352 */       "\nName:    " + getName() + 
/* 353 */       "\nLogin:   " + getLogin() + 
/* 354 */       "\nPassword:" + getPassword() + 
/* 355 */       "\nAcl string:" + getAclString() + 
/* 356 */       "\n--end user info--";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 368 */   public long getLastUpdatedCk() { return this.lastUpdatedCk; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 379 */   public void setLastUpdatedCk(long lastUpdatedCk) { this.lastUpdatedCk = lastUpdatedCk; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 389 */   public int getLastUpdatingUser() { return this.lastUpdatingUser; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 399 */   public void setLastUpdatingUser(int updatingUser) { this.lastUpdatingUser = updatingUser; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 409 */   public Calendar getLastUpdateDate() { return this.lastUpdateDate; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 419 */   public void setLastUpdateDate(Calendar lastUpdateDate) { this.lastUpdateDate = lastUpdateDate; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 428 */   public String getNotepadContentObjectId() { return Integer.toString(this.userId); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 438 */   public String getFilter() { return this.filter; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 448 */   public void setFilter(String filter) { this.filter = filter; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 455 */   public Object clone() throws CloneNotSupportedException { return super.clone(); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 466 */   public String getEmail() { return this.email; }
/*     */ 
/*     */ 
/*     */   
/* 470 */   public void setEmail(String email) { this.email = email; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 479 */   public String getPhone() { return this.phone; }
/*     */ 
/*     */ 
/*     */   
/* 483 */   public void setPhone(String phone) { this.phone = phone; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 492 */   public String getFax() { return this.fax; }
/*     */ 
/*     */ 
/*     */   
/* 496 */   public void setFax(String fax) { this.fax = fax; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 506 */   public String getDeptFilter() { return this.deptFilter; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 511 */   public void setDeptFilter(String deptFilter) { this.deptFilter = deptFilter; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 523 */   public void setReleasingFamily(Hashtable releasingFamily) { this.releasingFamily = releasingFamily; }
/*     */ 
/*     */ 
/*     */   
/* 527 */   public Hashtable getReleasingFamily() { return this.releasingFamily; }
/*     */ 
/*     */ 
/*     */   
/* 531 */   public void setAssignedEnvironments(Vector assignedEnvironments) { this.assignedEnvironments = assignedEnvironments; }
/*     */ 
/*     */ 
/*     */   
/* 535 */   public Vector getAssignedEnvironments() { return this.assignedEnvironments; }
/*     */ 
/*     */ 
/*     */   
/* 539 */   public Hashtable getReleasingFamilyLabelFamily() { return this.releasingFamilyLabelFamily; }
/*     */ 
/*     */ 
/*     */   
/* 543 */   public void setReleasingFamilyLabelFamily(Hashtable releasingFamilyLabelFamily) { this.releasingFamilyLabelFamily = releasingFamilyLabelFamily; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 555 */   public int getAdministrator() { return this.administrator; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 560 */   public void setAdministrator(int administrator) { this.administrator = administrator; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 565 */   public UserPreferences getPreferences() { return this.preferences; }
/*     */ 
/*     */ 
/*     */   
/* 569 */   public void setPreferences(UserPreferences preferences) { this.preferences = preferences; }
/*     */ }


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\User.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */