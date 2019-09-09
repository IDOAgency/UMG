/*      */ package WEB-INF.classes.com.universal.milestone;
/*      */ 
/*      */ import com.techempower.ComponentLog;
/*      */ import com.techempower.EnhancedProperties;
/*      */ import com.techempower.gemini.Context;
/*      */ import com.techempower.gemini.GeminiApplication;
/*      */ import com.universal.milestone.Acl;
/*      */ import com.universal.milestone.Cache;
/*      */ import com.universal.milestone.Company;
/*      */ import com.universal.milestone.CompanyAcl;
/*      */ import com.universal.milestone.CorpStructNameComparator;
/*      */ import com.universal.milestone.Environment;
/*      */ import com.universal.milestone.JdbcConnector;
/*      */ import com.universal.milestone.MilestoneConstants;
/*      */ import com.universal.milestone.MilestoneHelper;
/*      */ import com.universal.milestone.MilestoneHelper_2;
/*      */ import com.universal.milestone.MilestoneSecurity;
/*      */ import com.universal.milestone.Notepad;
/*      */ import com.universal.milestone.ReleasingFamily;
/*      */ import com.universal.milestone.ScheduleManager;
/*      */ import com.universal.milestone.User;
/*      */ import com.universal.milestone.UserManager;
/*      */ import com.universal.milestone.UserPreferences;
/*      */ import com.universal.milestone.UserPreferencesManager;
/*      */ import java.util.Arrays;
/*      */ import java.util.Hashtable;
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
/*      */ public class UserManager
/*      */   implements MilestoneConstants
/*      */ {
/*      */   public static final String COMPONENT_CODE = "mUsr";
/*      */   public static final String DEFAULT_USER_QUERY = "SELECT DISTINCT vi_user.*, vi_structure.name as employedByStr FROM vi_all_user as vi_user  LEFT JOIN vi_structure  ON vi_user.[employed_by] = vi_structure.[structure_id]";
/*      */   public static final String DEFAULT_USER_ORDER = " ORDER BY vi_user.[name]";
/*      */   public static final String DEFAULT_USER_COMPANY_QUERY = "SELECT a.*, b.*, c.name FROM vi_User_Company a, vi_Structure b, vi_Structure c  WHERE a.company_id = b.structure_id  AND b.parent_id = c.structure_id ";
/*      */   public static final String DEFAULT_USER_COMPANY_ORDER = " ORDER BY b.name";
/*      */   public static final String DEFAULT_USER_ENVIRONMENT_QUERY = "SELECT a.*, b.*, c.name FROM vi_User_Environment a, vi_Structure b, vi_Structure c  WHERE a.environment_id = b.structure_id  AND b.parent_id = c.structure_id ";
/*      */   public static final String DEFAULT_USER_ENVIRONMENT_ORDER = " ORDER BY b.name";
/*   72 */   protected static UserManager userManager = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected static ComponentLog log;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   84 */   public static void configure(EnhancedProperties props, GeminiApplication application) { log = application.getLog("mUsr"); }
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
/*      */   public static UserManager getInstance() {
/*  100 */     if (userManager == null)
/*      */     {
/*  102 */       userManager = new UserManager();
/*      */     }
/*  104 */     return userManager;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public User getUser(String userName, String password) {
/*  116 */     String userQuery = "SELECT vi_user.*, vi_structure.name as employedByStr FROM [vi_user] LEFT JOIN vi_structure ON vi_user.[employed_by] = vi_structure.[structure_id] WHERE vi_user.name = '" + 
/*      */ 
/*      */       
/*  119 */       MilestoneHelper.escapeSingleQuotes(userName) + "'" + 
/*  120 */       " AND vi_user.password = '" + MilestoneHelper.escapeSingleQuotes(password) + "';";
/*      */     
/*  122 */     User user = null;
/*  123 */     Acl acl = null;
/*      */     
/*  125 */     JdbcConnector connector = MilestoneHelper.getConnector(userQuery);
/*  126 */     connector.runQuery();
/*      */     
/*  128 */     if (connector.more()) {
/*      */ 
/*      */       
/*  131 */       user = new User();
/*      */ 
/*      */       
/*  134 */       initializeFields(user, connector);
/*      */ 
/*      */       
/*  137 */       long lastUpdatedLong = Long.parseLong(connector.getField("last_updated_ck"), 16);
/*  138 */       int lastUpdatedBy = connector.getIntegerField("last_updated_by");
/*  139 */       String lastDateString = connector.getFieldByName("last_updated_on");
/*      */ 
/*      */       
/*  142 */       connector.close();
/*      */ 
/*      */       
/*  145 */       if (lastDateString != null) {
/*  146 */         user.setLastUpdateDate(MilestoneHelper.getDatabaseDate(lastDateString));
/*      */       }
/*      */       
/*  149 */       user.setLastUpdatingUser(lastUpdatedBy);
/*      */ 
/*      */       
/*  152 */       user.setLastUpdatedCk(lastUpdatedLong);
/*      */       
/*  154 */       if (user.getAclString() != null) {
/*      */         
/*  156 */         String aclString = user.getAclString();
/*      */ 
/*      */         
/*  159 */         acl = new Acl();
/*      */         
/*  161 */         if (aclString.length() > 0) {
/*      */           
/*  163 */           acl.setAccessSelection(aclString.substring(0, 1).equals("1"));
/*  164 */           acl.setAccessSchedule(aclString.substring(1, 2).equals("1"));
/*  165 */           acl.setAccessManufacturing(aclString.substring(2, 3).equals("1"));
/*  166 */           acl.setAccessPfmForm(aclString.substring(3, 4).equals("1"));
/*  167 */           acl.setAccessBomForm(aclString.substring(4, 5).equals("1"));
/*  168 */           acl.setAccessReport(aclString.substring(5, 6).equals("1"));
/*  169 */           acl.setAccessTemplate(aclString.substring(6, 7).equals("1"));
/*  170 */           acl.setAccessTask(aclString.substring(7, 8).equals("1"));
/*  171 */           acl.setAccessDayType(aclString.substring(8, 9).equals("1"));
/*  172 */           acl.setAccessUser(aclString.substring(9, 10).equals("1"));
/*  173 */           acl.setAccessFamily(aclString.substring(10, 11).equals("1"));
/*  174 */           if (aclString.length() > 19) {
/*  175 */             acl.setAccessEnvironment(aclString.substring(11, 12).equals("1"));
/*  176 */             acl.setAccessCompany(aclString.substring(12, 13).equals("1"));
/*  177 */             acl.setAccessDivision(aclString.substring(13, 14).equals("1"));
/*  178 */             acl.setAccessLabel(aclString.substring(14, 15).equals("1"));
/*  179 */             acl.setAccessTable(aclString.substring(15, 16).equals("1"));
/*  180 */             acl.setAccessParameter(aclString.substring(16, 17).equals("1"));
/*  181 */             acl.setAccessAuditTrail(aclString.substring(17, 18).equals("1"));
/*  182 */             acl.setAccessReportConfig(aclString.substring(18, 19).equals("1"));
/*  183 */             acl.setAccessPriceCode(aclString.substring(19, 20).equals("1"));
/*      */           } else {
/*  185 */             acl.setAccessCompany(aclString.substring(11, 12).equals("1"));
/*  186 */             acl.setAccessDivision(aclString.substring(12, 13).equals("1"));
/*  187 */             acl.setAccessLabel(aclString.substring(13, 14).equals("1"));
/*  188 */             acl.setAccessTable(aclString.substring(14, 15).equals("1"));
/*  189 */             acl.setAccessParameter(aclString.substring(15, 16).equals("1"));
/*  190 */             acl.setAccessAuditTrail(aclString.substring(16, 17).equals("1"));
/*  191 */             acl.setAccessReportConfig(aclString.substring(17, 18).equals("1"));
/*  192 */             acl.setAccessPriceCode(aclString.substring(18, 19).equals("1"));
/*      */           } 
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/*  198 */       Vector companies = new Vector();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  206 */       String companyQuery = "sp_get_User_company_access " + String.valueOf(user.getUserId());
/*  207 */       connector.setQuery(companyQuery);
/*  208 */       connector.runQuery();
/*      */       
/*  210 */       while (connector.more()) {
/*      */         
/*  212 */         CompanyAcl newCompanyAcl = new CompanyAcl();
/*      */         
/*  214 */         newCompanyAcl.setCompanyName(connector.getField("name"));
/*  215 */         newCompanyAcl.setCompanyId(connector.getIntegerField("company_id"));
/*      */         
/*  217 */         if (connector.getField("menu_access") != null) {
/*      */           
/*  219 */           String companyAclString = connector.getField("menu_access");
/*      */           
/*  221 */           if (companyAclString.length() > 0) {
/*      */             
/*  223 */             newCompanyAcl.setAccessSelection(Integer.parseInt(companyAclString.substring(0, 1)));
/*  224 */             newCompanyAcl.setAccessSchedule(Integer.parseInt(companyAclString.substring(1, 2)));
/*  225 */             newCompanyAcl.setAccessManufacturing(Integer.parseInt(companyAclString.substring(2, 3)));
/*  226 */             newCompanyAcl.setAccessPfmForm(Integer.parseInt(companyAclString.substring(3, 4)));
/*  227 */             newCompanyAcl.setAccessBomForm(Integer.parseInt(companyAclString.substring(4, 5)));
/*  228 */             newCompanyAcl.setAccessReport(Integer.parseInt(companyAclString.substring(5, 6)));
/*  229 */             newCompanyAcl.setAccessTemplate(Integer.parseInt(companyAclString.substring(6, 7)));
/*  230 */             newCompanyAcl.setAccessTask(Integer.parseInt(companyAclString.substring(7, 8)));
/*  231 */             newCompanyAcl.setAccessDayType(Integer.parseInt(companyAclString.substring(8, 9)));
/*  232 */             newCompanyAcl.setAccessUser(Integer.parseInt(companyAclString.substring(9, 10)));
/*  233 */             newCompanyAcl.setAccessFamily(Integer.parseInt(companyAclString.substring(10, 11)));
/*  234 */             if (companyAclString.length() > 19) {
/*  235 */               newCompanyAcl.setAccessEnvironment(Integer.parseInt(companyAclString.substring(11, 12)));
/*  236 */               newCompanyAcl.setAccessCompany(Integer.parseInt(companyAclString.substring(12, 13)));
/*  237 */               newCompanyAcl.setAccessDivision(Integer.parseInt(companyAclString.substring(13, 14)));
/*  238 */               newCompanyAcl.setAccessLabel(Integer.parseInt(companyAclString.substring(14, 15)));
/*  239 */               newCompanyAcl.setAccessTable(Integer.parseInt(companyAclString.substring(15, 16)));
/*  240 */               newCompanyAcl.setAccessParameter(Integer.parseInt(companyAclString.substring(16, 17)));
/*  241 */               newCompanyAcl.setAccessAuditTrail(Integer.parseInt(companyAclString.substring(17, 18)));
/*  242 */               newCompanyAcl.setAccessReportConfig(Integer.parseInt(companyAclString.substring(18, 19)));
/*  243 */               newCompanyAcl.setAccessPriceCode(Integer.parseInt(companyAclString.substring(19, 20)));
/*      */             } else {
/*  245 */               newCompanyAcl.setAccessCompany(Integer.parseInt(companyAclString.substring(11, 12)));
/*  246 */               newCompanyAcl.setAccessDivision(Integer.parseInt(companyAclString.substring(12, 13)));
/*  247 */               newCompanyAcl.setAccessLabel(Integer.parseInt(companyAclString.substring(13, 14)));
/*  248 */               newCompanyAcl.setAccessTable(Integer.parseInt(companyAclString.substring(14, 15)));
/*  249 */               newCompanyAcl.setAccessParameter(Integer.parseInt(companyAclString.substring(15, 16)));
/*  250 */               newCompanyAcl.setAccessAuditTrail(Integer.parseInt(companyAclString.substring(16, 17)));
/*  251 */               newCompanyAcl.setAccessReportConfig(Integer.parseInt(companyAclString.substring(17, 18)));
/*  252 */               newCompanyAcl.setAccessPriceCode(Integer.parseInt(companyAclString.substring(18, 19)));
/*      */             } 
/*      */           } 
/*      */           
/*  256 */           companies.addElement(newCompanyAcl);
/*  257 */           newCompanyAcl = null;
/*      */         } 
/*  259 */         connector.next();
/*      */       } 
/*      */       
/*  262 */       if (acl != null) {
/*      */         
/*  264 */         acl.setCompanyAcl(companies);
/*      */         
/*  266 */         acl.setFamilyAccessHash(ScheduleManager.getInstance().buildTaskEditAccess(companies, user));
/*  267 */         acl.setScreenPermissionsHash();
/*  268 */         user.setAcl(acl);
/*      */       
/*      */       }
/*      */       else {
/*      */         
/*  273 */         user.newSelectionEditAccess = MilestoneHelper_2.userHasSelectionEditPermission(user.getAcl());
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  282 */       user.setReleasingFamily(ReleasingFamily.get(user.getUserId()));
/*  283 */       user.setReleasingFamilyLabelFamily(ReleasingFamily.getReleasingFamilyLabelFamily(user.getUserId()));
/*      */       
/*  285 */       Vector userEnvironments = MilestoneHelper.getUserEnvironments(user.getUserId());
/*      */       
/*  287 */       user.setAssignedEnvironments(userEnvironments);
/*      */ 
/*      */       
/*  290 */       UserPreferences up = UserPreferencesManager.getInstance().getUserPreferences(user.getUserId());
/*  291 */       user.setPreferences(up);
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  298 */     connector.close();
/*  299 */     return user;
/*      */   }
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
/*      */   public User getUser(int userId, boolean getTimestamp) {
/*  314 */     String userQuery = "sp_get_User_All " + userId;
/*  315 */     User user = null;
/*  316 */     Acl acl = null;
/*      */     
/*  318 */     JdbcConnector connector = MilestoneHelper.getConnector(userQuery);
/*  319 */     connector.runQuery();
/*      */     
/*  321 */     if (connector.more()) {
/*      */ 
/*      */       
/*  324 */       user = new User();
/*      */ 
/*      */       
/*  327 */       initializeFields(user, connector);
/*      */ 
/*      */       
/*  330 */       long lastUpdatedLong = Long.parseLong(connector.getField("last_updated_ck"), 16);
/*  331 */       int lastUpdatedBy = connector.getIntegerField("last_updated_by");
/*  332 */       String lastDateString = connector.getField("last_updated_on");
/*      */ 
/*      */       
/*  335 */       connector.close();
/*      */ 
/*      */       
/*  338 */       if (!lastDateString.equalsIgnoreCase("[none]")) {
/*  339 */         user.setLastUpdateDate(MilestoneHelper.getDatabaseDate(lastDateString));
/*      */       }
/*      */       
/*  342 */       user.setLastUpdatingUser(lastUpdatedBy);
/*      */ 
/*      */       
/*  345 */       if (getTimestamp) {
/*  346 */         user.setLastUpdatedCk(lastUpdatedLong);
/*      */       }
/*  348 */       if (user.getAclString() != null) {
/*      */         
/*  350 */         String aclString = user.getAclString();
/*      */ 
/*      */         
/*  353 */         acl = new Acl();
/*      */         
/*  355 */         if (aclString.length() > 0) {
/*      */           
/*  357 */           acl.setAccessSelection(aclString.substring(0, 1).equals("1"));
/*  358 */           acl.setAccessSchedule(aclString.substring(1, 2).equals("1"));
/*  359 */           acl.setAccessManufacturing(aclString.substring(2, 3).equals("1"));
/*  360 */           acl.setAccessPfmForm(aclString.substring(3, 4).equals("1"));
/*  361 */           acl.setAccessBomForm(aclString.substring(4, 5).equals("1"));
/*  362 */           acl.setAccessReport(aclString.substring(5, 6).equals("1"));
/*  363 */           acl.setAccessTemplate(aclString.substring(6, 7).equals("1"));
/*  364 */           acl.setAccessTask(aclString.substring(7, 8).equals("1"));
/*  365 */           acl.setAccessDayType(aclString.substring(8, 9).equals("1"));
/*  366 */           acl.setAccessUser(aclString.substring(9, 10).equals("1"));
/*  367 */           acl.setAccessFamily(aclString.substring(10, 11).equals("1"));
/*  368 */           if (aclString.length() > 19) {
/*  369 */             acl.setAccessEnvironment(aclString.substring(11, 12).equals("1"));
/*  370 */             acl.setAccessCompany(aclString.substring(12, 13).equals("1"));
/*  371 */             acl.setAccessDivision(aclString.substring(13, 14).equals("1"));
/*  372 */             acl.setAccessLabel(aclString.substring(14, 15).equals("1"));
/*  373 */             acl.setAccessTable(aclString.substring(15, 16).equals("1"));
/*  374 */             acl.setAccessParameter(aclString.substring(16, 17).equals("1"));
/*  375 */             acl.setAccessAuditTrail(aclString.substring(17, 18).equals("1"));
/*  376 */             acl.setAccessReportConfig(aclString.substring(18, 19).equals("1"));
/*  377 */             acl.setAccessPriceCode(aclString.substring(19, 20).equals("1"));
/*      */           } else {
/*  379 */             acl.setAccessCompany(aclString.substring(11, 12).equals("1"));
/*  380 */             acl.setAccessDivision(aclString.substring(12, 13).equals("1"));
/*  381 */             acl.setAccessLabel(aclString.substring(13, 14).equals("1"));
/*  382 */             acl.setAccessTable(aclString.substring(14, 15).equals("1"));
/*  383 */             acl.setAccessParameter(aclString.substring(15, 16).equals("1"));
/*  384 */             acl.setAccessAuditTrail(aclString.substring(16, 17).equals("1"));
/*  385 */             acl.setAccessReportConfig(aclString.substring(17, 18).equals("1"));
/*  386 */             acl.setAccessPriceCode(aclString.substring(18, 19).equals("1"));
/*      */           } 
/*      */         } 
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/*  393 */       Vector companies = new Vector();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  401 */       String companyQuery = "sp_get_User_company_access " + String.valueOf(user.getUserId());
/*  402 */       connector.setQuery(companyQuery);
/*  403 */       connector.runQuery();
/*      */       
/*  405 */       while (connector.more()) {
/*      */         
/*  407 */         CompanyAcl newCompanyAcl = new CompanyAcl();
/*      */         
/*  409 */         newCompanyAcl.setCompanyName(connector.getField("name"));
/*  410 */         newCompanyAcl.setCompanyId(connector.getIntegerField("company_id"));
/*      */         
/*  412 */         if (connector.getField("menu_access") != null) {
/*      */           
/*  414 */           String companyAclString = connector.getField("menu_access");
/*      */           
/*  416 */           if (companyAclString.length() > 0) {
/*      */             
/*  418 */             newCompanyAcl.setAccessSelection(Integer.parseInt(companyAclString.substring(0, 1)));
/*  419 */             newCompanyAcl.setAccessSchedule(Integer.parseInt(companyAclString.substring(1, 2)));
/*  420 */             newCompanyAcl.setAccessManufacturing(Integer.parseInt(companyAclString.substring(2, 3)));
/*  421 */             newCompanyAcl.setAccessPfmForm(Integer.parseInt(companyAclString.substring(3, 4)));
/*  422 */             newCompanyAcl.setAccessBomForm(Integer.parseInt(companyAclString.substring(4, 5)));
/*  423 */             newCompanyAcl.setAccessReport(Integer.parseInt(companyAclString.substring(5, 6)));
/*  424 */             newCompanyAcl.setAccessTemplate(Integer.parseInt(companyAclString.substring(6, 7)));
/*  425 */             newCompanyAcl.setAccessTask(Integer.parseInt(companyAclString.substring(7, 8)));
/*  426 */             newCompanyAcl.setAccessDayType(Integer.parseInt(companyAclString.substring(8, 9)));
/*  427 */             newCompanyAcl.setAccessUser(Integer.parseInt(companyAclString.substring(9, 10)));
/*  428 */             newCompanyAcl.setAccessFamily(Integer.parseInt(companyAclString.substring(10, 11)));
/*  429 */             if (companyAclString.length() > 19) {
/*  430 */               newCompanyAcl.setAccessEnvironment(Integer.parseInt(companyAclString.substring(11, 12)));
/*  431 */               newCompanyAcl.setAccessCompany(Integer.parseInt(companyAclString.substring(12, 13)));
/*  432 */               newCompanyAcl.setAccessDivision(Integer.parseInt(companyAclString.substring(13, 14)));
/*  433 */               newCompanyAcl.setAccessLabel(Integer.parseInt(companyAclString.substring(14, 15)));
/*  434 */               newCompanyAcl.setAccessTable(Integer.parseInt(companyAclString.substring(15, 16)));
/*  435 */               newCompanyAcl.setAccessParameter(Integer.parseInt(companyAclString.substring(16, 17)));
/*  436 */               newCompanyAcl.setAccessAuditTrail(Integer.parseInt(companyAclString.substring(17, 18)));
/*  437 */               newCompanyAcl.setAccessReportConfig(Integer.parseInt(companyAclString.substring(18, 19)));
/*  438 */               newCompanyAcl.setAccessPriceCode(Integer.parseInt(companyAclString.substring(19, 20)));
/*      */             } else {
/*  440 */               newCompanyAcl.setAccessCompany(Integer.parseInt(companyAclString.substring(11, 12)));
/*  441 */               newCompanyAcl.setAccessDivision(Integer.parseInt(companyAclString.substring(12, 13)));
/*  442 */               newCompanyAcl.setAccessLabel(Integer.parseInt(companyAclString.substring(13, 14)));
/*  443 */               newCompanyAcl.setAccessTable(Integer.parseInt(companyAclString.substring(14, 15)));
/*  444 */               newCompanyAcl.setAccessParameter(Integer.parseInt(companyAclString.substring(15, 16)));
/*  445 */               newCompanyAcl.setAccessAuditTrail(Integer.parseInt(companyAclString.substring(16, 17)));
/*  446 */               newCompanyAcl.setAccessReportConfig(Integer.parseInt(companyAclString.substring(17, 18)));
/*  447 */               newCompanyAcl.setAccessPriceCode(Integer.parseInt(companyAclString.substring(18, 19)));
/*      */             } 
/*      */           } 
/*      */           
/*  451 */           companies.addElement(newCompanyAcl);
/*  452 */           newCompanyAcl = null;
/*      */         } 
/*  454 */         connector.next();
/*      */       } 
/*      */       
/*  457 */       if (acl != null) {
/*      */         
/*  459 */         acl.setCompanyAcl(companies);
/*      */         
/*  461 */         acl.setFamilyAccessHash(ScheduleManager.getInstance().buildTaskEditAccess(companies, user));
/*  462 */         acl.setScreenPermissionsHash();
/*  463 */         user.setAcl(acl);
/*      */       }
/*      */       else {
/*      */         
/*  467 */         user.newSelectionEditAccess = MilestoneHelper_2.userHasSelectionEditPermission(user.getAcl());
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  477 */       user.setReleasingFamily(ReleasingFamily.get(user.getUserId()));
/*  478 */       user.setReleasingFamilyLabelFamily(ReleasingFamily.getReleasingFamilyLabelFamily(user.getUserId()));
/*      */       
/*  480 */       Vector userEnvironments = MilestoneHelper.getUserEnvironments(user.getUserId());
/*      */       
/*  482 */       user.setAssignedEnvironments(userEnvironments);
/*      */ 
/*      */       
/*  485 */       UserPreferences up = UserPreferencesManager.getInstance().getUserPreferences(user.getUserId());
/*  486 */       user.setPreferences(up);
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  493 */     connector.close();
/*  494 */     return user;
/*      */   }
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
/*      */   public User getUser(int userId) {
/*  508 */     User user = null;
/*      */     
/*  510 */     if (userId > 0) {
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
/*  532 */       Hashtable users = Cache.getAllUsersHash();
/*  533 */       if (users != null) {
/*  534 */         user = (User)users.get(String.valueOf(userId));
/*      */       }
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  543 */     return user;
/*      */   }
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
/*      */   public User getUserObject(JdbcConnector connector) {
/*  557 */     User user = null;
/*  558 */     Acl acl = null;
/*      */     
/*  560 */     if (connector != null && connector.more()) {
/*      */       
/*  562 */       user = new User();
/*      */ 
/*      */       
/*  565 */       initializeFields(user, connector);
/*      */ 
/*      */       
/*  568 */       long lastUpdatedLong = Long.parseLong(connector.getField("last_updated_ck"), 16);
/*  569 */       int lastUpdatedBy = connector.getIntegerField("last_updated_by");
/*  570 */       String lastDateString = connector.getField("last_updated_on");
/*      */ 
/*      */       
/*  573 */       if (!lastDateString.equalsIgnoreCase("[none]")) {
/*  574 */         user.setLastUpdateDate(MilestoneHelper.getDatabaseDate(lastDateString));
/*      */       }
/*      */       
/*  577 */       user.setLastUpdatingUser(lastUpdatedBy);
/*      */       
/*  579 */       if (user.getAclString() != null) {
/*      */         
/*  581 */         String aclString = user.getAclString();
/*      */ 
/*      */         
/*  584 */         acl = new Acl();
/*      */         
/*  586 */         if (aclString.length() > 0) {
/*      */           
/*  588 */           acl.setAccessSelection(aclString.substring(0, 1).equals("1"));
/*  589 */           acl.setAccessSchedule(aclString.substring(1, 2).equals("1"));
/*  590 */           acl.setAccessManufacturing(aclString.substring(2, 3).equals("1"));
/*  591 */           acl.setAccessPfmForm(aclString.substring(3, 4).equals("1"));
/*  592 */           acl.setAccessBomForm(aclString.substring(4, 5).equals("1"));
/*  593 */           acl.setAccessReport(aclString.substring(5, 6).equals("1"));
/*  594 */           acl.setAccessTemplate(aclString.substring(6, 7).equals("1"));
/*  595 */           acl.setAccessTask(aclString.substring(7, 8).equals("1"));
/*  596 */           acl.setAccessDayType(aclString.substring(8, 9).equals("1"));
/*  597 */           acl.setAccessUser(aclString.substring(9, 10).equals("1"));
/*  598 */           acl.setAccessFamily(aclString.substring(10, 11).equals("1"));
/*  599 */           if (aclString.length() > 19) {
/*  600 */             acl.setAccessEnvironment(aclString.substring(11, 12).equals("1"));
/*  601 */             acl.setAccessCompany(aclString.substring(12, 13).equals("1"));
/*  602 */             acl.setAccessDivision(aclString.substring(13, 14).equals("1"));
/*  603 */             acl.setAccessLabel(aclString.substring(14, 15).equals("1"));
/*  604 */             acl.setAccessTable(aclString.substring(15, 16).equals("1"));
/*  605 */             acl.setAccessParameter(aclString.substring(16, 17).equals("1"));
/*  606 */             acl.setAccessAuditTrail(aclString.substring(17, 18).equals("1"));
/*  607 */             acl.setAccessReportConfig(aclString.substring(18, 19).equals("1"));
/*  608 */             acl.setAccessPriceCode(aclString.substring(19, 20).equals("1"));
/*      */           } else {
/*  610 */             acl.setAccessCompany(aclString.substring(11, 12).equals("1"));
/*  611 */             acl.setAccessDivision(aclString.substring(12, 13).equals("1"));
/*  612 */             acl.setAccessLabel(aclString.substring(13, 14).equals("1"));
/*  613 */             acl.setAccessTable(aclString.substring(14, 15).equals("1"));
/*  614 */             acl.setAccessParameter(aclString.substring(15, 16).equals("1"));
/*  615 */             acl.setAccessAuditTrail(aclString.substring(16, 17).equals("1"));
/*  616 */             acl.setAccessReportConfig(aclString.substring(17, 18).equals("1"));
/*  617 */             acl.setAccessPriceCode(aclString.substring(18, 19).equals("1"));
/*      */           } 
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/*  623 */       Vector companies = new Vector();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  631 */       String companyQuery = "sp_get_User_company_access " + String.valueOf(user.getUserId());
/*  632 */       JdbcConnector companyConnector = MilestoneHelper.getConnector(companyQuery);
/*  633 */       companyConnector.runQuery();
/*      */       
/*  635 */       while (companyConnector.more()) {
/*      */         
/*  637 */         CompanyAcl newCompanyAcl = new CompanyAcl();
/*      */         
/*  639 */         newCompanyAcl.setCompanyName(companyConnector.getField("name"));
/*  640 */         newCompanyAcl.setCompanyId(companyConnector.getIntegerField("company_id"));
/*      */ 
/*      */         
/*  643 */         if (companyConnector.getField("menu_access") != null) {
/*      */           
/*  645 */           String companyAclString = companyConnector.getField("menu_access");
/*      */           
/*  647 */           if (companyAclString.length() > 0) {
/*      */             
/*  649 */             newCompanyAcl.setAccessSelection(Integer.parseInt(companyAclString.substring(0, 1)));
/*  650 */             newCompanyAcl.setAccessSchedule(Integer.parseInt(companyAclString.substring(1, 2)));
/*  651 */             newCompanyAcl.setAccessManufacturing(Integer.parseInt(companyAclString.substring(2, 3)));
/*  652 */             newCompanyAcl.setAccessPfmForm(Integer.parseInt(companyAclString.substring(3, 4)));
/*  653 */             newCompanyAcl.setAccessBomForm(Integer.parseInt(companyAclString.substring(4, 5)));
/*  654 */             newCompanyAcl.setAccessReport(Integer.parseInt(companyAclString.substring(5, 6)));
/*  655 */             newCompanyAcl.setAccessTemplate(Integer.parseInt(companyAclString.substring(6, 7)));
/*  656 */             newCompanyAcl.setAccessTask(Integer.parseInt(companyAclString.substring(7, 8)));
/*  657 */             newCompanyAcl.setAccessDayType(Integer.parseInt(companyAclString.substring(8, 9)));
/*  658 */             newCompanyAcl.setAccessUser(Integer.parseInt(companyAclString.substring(9, 10)));
/*  659 */             newCompanyAcl.setAccessFamily(Integer.parseInt(companyAclString.substring(10, 11)));
/*  660 */             if (companyAclString.length() > 19) {
/*  661 */               newCompanyAcl.setAccessEnvironment(Integer.parseInt(companyAclString.substring(11, 12)));
/*  662 */               newCompanyAcl.setAccessCompany(Integer.parseInt(companyAclString.substring(12, 13)));
/*  663 */               newCompanyAcl.setAccessDivision(Integer.parseInt(companyAclString.substring(13, 14)));
/*  664 */               newCompanyAcl.setAccessLabel(Integer.parseInt(companyAclString.substring(14, 15)));
/*  665 */               newCompanyAcl.setAccessTable(Integer.parseInt(companyAclString.substring(15, 16)));
/*  666 */               newCompanyAcl.setAccessParameter(Integer.parseInt(companyAclString.substring(16, 17)));
/*  667 */               newCompanyAcl.setAccessAuditTrail(Integer.parseInt(companyAclString.substring(17, 18)));
/*  668 */               newCompanyAcl.setAccessReportConfig(Integer.parseInt(companyAclString.substring(18, 19)));
/*  669 */               newCompanyAcl.setAccessPriceCode(Integer.parseInt(companyAclString.substring(19, 20)));
/*      */             } else {
/*  671 */               newCompanyAcl.setAccessCompany(Integer.parseInt(companyAclString.substring(11, 12)));
/*  672 */               newCompanyAcl.setAccessDivision(Integer.parseInt(companyAclString.substring(12, 13)));
/*  673 */               newCompanyAcl.setAccessLabel(Integer.parseInt(companyAclString.substring(13, 14)));
/*  674 */               newCompanyAcl.setAccessTable(Integer.parseInt(companyAclString.substring(14, 15)));
/*  675 */               newCompanyAcl.setAccessParameter(Integer.parseInt(companyAclString.substring(15, 16)));
/*  676 */               newCompanyAcl.setAccessAuditTrail(Integer.parseInt(companyAclString.substring(16, 17)));
/*  677 */               newCompanyAcl.setAccessReportConfig(Integer.parseInt(companyAclString.substring(17, 18)));
/*  678 */               newCompanyAcl.setAccessPriceCode(Integer.parseInt(companyAclString.substring(18, 19)));
/*      */             } 
/*      */           } 
/*      */           
/*  682 */           companies.addElement(newCompanyAcl);
/*  683 */           newCompanyAcl = null;
/*      */         } 
/*  685 */         companyConnector.next();
/*      */       } 
/*      */       
/*  688 */       if (acl != null) {
/*      */         
/*  690 */         acl.setCompanyAcl(companies);
/*      */         
/*  692 */         acl.setFamilyAccessHash(ScheduleManager.getInstance().buildTaskEditAccess(companies, user));
/*  693 */         acl.setScreenPermissionsHash();
/*  694 */         user.setAcl(acl);
/*      */       
/*      */       }
/*      */       else {
/*      */         
/*  699 */         user.newSelectionEditAccess = MilestoneHelper_2.userHasSelectionEditPermission(user.getAcl());
/*      */       } 
/*      */       
/*  702 */       companyConnector.close();
/*      */ 
/*      */       
/*  705 */       UserPreferences up = UserPreferencesManager.getInstance().getUserPreferences(user.getUserId());
/*  706 */       user.setPreferences(up);
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  713 */     return user;
/*      */   }
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
/*      */   public boolean login(String userName, String password) {
/*  726 */     User user = getUser(userName, password);
/*      */     
/*  728 */     if (user != null)
/*      */     {
/*  730 */       return user.getPassword().equalsIgnoreCase(password);
/*      */     }
/*      */ 
/*      */     
/*  734 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  743 */   public void logout(Context context) { MilestoneSecurity.processLogout(context); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void clearSession(Context context) {
/*  750 */     String lastLink = (context.getSessionValue("lastLink") != null) ? (String)context.getSessionValue("lastLink") : "";
/*  751 */     context.removeAllSessionValues();
/*  752 */     if (lastLink.length() > 0) {
/*  753 */       context.putSessionValue("lastLink", lastLink);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  762 */   public boolean register(User pUser) { return true; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public User save(User user, User sessionUser, Vector checkedCompanies, Context context) {
/*  772 */     StringBuffer aclString = new StringBuffer(25);
/*  773 */     Acl acl = user.getAcl();
/*      */ 
/*      */     
/*  776 */     if (acl.getAccessSelection()) {
/*  777 */       aclString.append('1');
/*      */     } else {
/*  779 */       aclString.append('0');
/*      */     } 
/*      */     
/*  782 */     if (acl.getAccessSchedule()) {
/*  783 */       aclString.append('1');
/*      */     } else {
/*  785 */       aclString.append('0');
/*      */     } 
/*      */     
/*  788 */     if (acl.getAccessManufacturing()) {
/*  789 */       aclString.append('1');
/*      */     } else {
/*  791 */       aclString.append('0');
/*      */     } 
/*      */     
/*  794 */     if (acl.getAccessPfmForm()) {
/*  795 */       aclString.append('1');
/*      */     } else {
/*  797 */       aclString.append('0');
/*      */     } 
/*      */     
/*  800 */     if (acl.getAccessBomForm()) {
/*  801 */       aclString.append('1');
/*      */     } else {
/*  803 */       aclString.append('0');
/*      */     } 
/*      */     
/*  806 */     if (acl.getAccessReport()) {
/*  807 */       aclString.append('1');
/*      */     } else {
/*  809 */       aclString.append('0');
/*      */     } 
/*      */     
/*  812 */     if (acl.getAccessTemplate()) {
/*  813 */       aclString.append('1');
/*      */     } else {
/*  815 */       aclString.append('0');
/*      */     } 
/*      */     
/*  818 */     if (acl.getAccessTask()) {
/*  819 */       aclString.append('1');
/*      */     } else {
/*  821 */       aclString.append('0');
/*      */     } 
/*      */     
/*  824 */     if (acl.getAccessDayType()) {
/*  825 */       aclString.append('1');
/*      */     } else {
/*  827 */       aclString.append('0');
/*      */     } 
/*      */     
/*  830 */     if (acl.getAccessUser()) {
/*  831 */       aclString.append('1');
/*      */     } else {
/*  833 */       aclString.append('0');
/*      */     } 
/*      */     
/*  836 */     if (acl.getAccessFamily()) {
/*  837 */       aclString.append('1');
/*      */     } else {
/*  839 */       aclString.append('0');
/*      */     } 
/*      */     
/*  842 */     if (acl.getAccessEnvironment()) {
/*  843 */       aclString.append('1');
/*      */     } else {
/*  845 */       aclString.append('0');
/*      */     } 
/*      */     
/*  848 */     if (acl.getAccessCompany()) {
/*  849 */       aclString.append('1');
/*      */     } else {
/*  851 */       aclString.append('0');
/*      */     } 
/*      */     
/*  854 */     if (acl.getAccessDivision()) {
/*  855 */       aclString.append('1');
/*      */     } else {
/*  857 */       aclString.append('0');
/*      */     } 
/*      */     
/*  860 */     if (acl.getAccessLabel()) {
/*  861 */       aclString.append('1');
/*      */     } else {
/*  863 */       aclString.append('0');
/*      */     } 
/*      */     
/*  866 */     if (acl.getAccessTable()) {
/*  867 */       aclString.append('1');
/*      */     } else {
/*  869 */       aclString.append('0');
/*      */     } 
/*      */     
/*  872 */     if (acl.getAccessParameter()) {
/*  873 */       aclString.append('1');
/*      */     } else {
/*  875 */       aclString.append('0');
/*      */     } 
/*      */     
/*  878 */     if (acl.getAccessAuditTrail()) {
/*  879 */       aclString.append('1');
/*      */     } else {
/*  881 */       aclString.append('0');
/*      */     } 
/*      */     
/*  884 */     if (acl.getAccessReportConfig()) {
/*  885 */       aclString.append('1');
/*      */     } else {
/*  887 */       aclString.append('0');
/*      */     } 
/*      */     
/*  890 */     if (acl.getAccessPriceCode()) {
/*  891 */       aclString.append('1');
/*      */     } else {
/*  893 */       aclString.append('0');
/*      */     } 
/*  895 */     long timestamp = user.getLastUpdatedCk();
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
/*  914 */     String query = "sp_sav_User " + 
/*  915 */       user.getUserId() + 
/*  916 */       ",'" + MilestoneHelper.escapeSingleQuotes(user.getLogin()) + "'" + 
/*  917 */       ",'" + MilestoneHelper.escapeSingleQuotes(user.getName()) + "'" + 
/*  918 */       ",'" + MilestoneHelper.escapeSingleQuotes(user.getPassword()) + "'" + 
/*  919 */       ",'" + MilestoneHelper.escapeSingleQuotes(user.getReportsTo()) + "'" + 
/*  920 */       "," + user.getEmployedBy() + 
/*  921 */       ",'" + MilestoneHelper.escapeSingleQuotes(user.getLocation()) + "'" + 
/*  922 */       ",'" + aclString.toString() + "'" + 
/*  923 */       ",''" + 
/*  924 */       ",'" + user.getDeptFilter() + "'" + 
/*  925 */       ",'" + user.getFilter() + "'" + 
/*  926 */       ",''" + 
/*  927 */       "," + sessionUser.getUserId() + 
/*  928 */       "," + timestamp + 
/*  929 */       ",'" + user.getEmail() + "'" + 
/*  930 */       ",'" + user.getPhone() + "'" + 
/*  931 */       ",'" + user.getFax() + "'" + 
/*  932 */       "," + user.getInactive() + 
/*  933 */       "," + user.getAdministrator();
/*      */ 
/*      */ 
/*      */     
/*  937 */     boolean IsRelFamNewUser = false;
/*  938 */     if (user.getUserId() == -1) {
/*  939 */       IsRelFamNewUser = true;
/*      */     }
/*  941 */     JdbcConnector connector = MilestoneHelper.getConnector(query);
/*  942 */     connector.runQuery();
/*  943 */     int savedUserId = connector.getIntegerField("ReturnId");
/*  944 */     connector.close();
/*      */ 
/*      */     
/*  947 */     if (savedUserId > 0) {
/*  948 */       user.setUserId(savedUserId);
/*      */     }
/*      */     
/*  951 */     user.setAcl(acl);
/*      */ 
/*      */     
/*  954 */     user.newSelectionEditAccess = MilestoneHelper_2.userHasSelectionEditPermission(user.getAcl());
/*      */     
/*  956 */     String timestampQuery = "SELECT last_updated_ck, last_updated_on FROM vi_user WHERE user_id = " + 
/*      */       
/*  958 */       user.getUserId() + 
/*  959 */       ";";
/*      */     
/*  961 */     JdbcConnector connectorTimestamp = MilestoneHelper.getConnector(timestampQuery);
/*  962 */     connectorTimestamp.runQuery();
/*  963 */     if (connectorTimestamp.more()) {
/*      */       
/*  965 */       user.setLastUpdatedCk(Long.parseLong(connectorTimestamp.getField("last_updated_ck"), 16));
/*  966 */       String lastString = connectorTimestamp.getField("last_updated_on");
/*  967 */       if (!lastString.equalsIgnoreCase("[none]"))
/*  968 */         user.setLastUpdateDate(MilestoneHelper.getDatabaseDate(connectorTimestamp.getField("last_updated_on"))); 
/*      */     } 
/*  970 */     connectorTimestamp.close();
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  975 */     Vector companies = Cache.getInstance().getCompanies();
/*      */ 
/*      */     
/*  978 */     int userid = user.getUserId();
/*      */ 
/*      */ 
/*      */     
/*  982 */     Vector userCompanies = MilestoneHelper.getUserCompanies(userid);
/*  983 */     if (userCompanies == null) {
/*  984 */       userCompanies = new Vector();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  991 */     boolean isNew = true;
/*  992 */     boolean isChecked = false;
/*  993 */     boolean isUnChecked = false;
/*      */ 
/*      */     
/*  996 */     for (int b = 0; b < userCompanies.size(); b++) {
/*      */       
/*  998 */       Company userCompany = (Company)userCompanies.elementAt(b);
/*  999 */       if (userCompany != null) {
/*      */         
/* 1001 */         isChecked = false;
/* 1002 */         for (int j = 0; j < checkedCompanies.size(); j++) {
/*      */           
/* 1004 */           Company checkCompany = (Company)checkedCompanies.get(j);
/* 1005 */           if (checkCompany != null && checkCompany.getStructureID() == userCompany.getStructureID()) {
/*      */             
/* 1007 */             isChecked = true;
/*      */             
/*      */             break;
/*      */           } 
/*      */         } 
/* 1012 */         if (!isChecked)
/*      */         {
/*      */           
/* 1015 */           deleteUserCompany(userid, userCompany.getStructureID());
/*      */         }
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1022 */     Hashtable copiedUserHash = null;
/* 1023 */     if (checkedCompanies.size() > 0 && context.getSessionValue("copiedUserObj") != null) {
/*      */       
/* 1025 */       copiedUserHash = new Hashtable();
/* 1026 */       User copiedUserObj = (User)context.getSessionValue("copiedUserObj");
/*      */       
/* 1028 */       Acl currentAcl = copiedUserObj.getAcl();
/*      */       
/* 1030 */       Vector companyAcl = currentAcl.getCompanyAcl();
/*      */       
/* 1032 */       for (int i = 0; i < companyAcl.size(); i++) {
/*      */         
/* 1034 */         CompanyAcl tempAcl = (CompanyAcl)companyAcl.get(i);
/* 1035 */         if (tempAcl != null) {
/*      */           
/* 1037 */           StringBuffer aclBuffer = buildCompanyAcl(tempAcl);
/* 1038 */           String companyId = String.valueOf(tempAcl.getCompanyId());
/*      */           
/* 1040 */           if (!copiedUserHash.containsKey(companyId)) {
/* 1041 */             copiedUserHash.put(companyId, aclBuffer.toString());
/*      */           }
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 1048 */     for (int l = 0; l < checkedCompanies.size(); l++) {
/*      */       
/* 1050 */       Company checkCompany = (Company)checkedCompanies.get(l);
/* 1051 */       if (checkCompany != null) {
/*      */         
/* 1053 */         isNew = true;
/* 1054 */         for (int c = 0; c < userCompanies.size(); c++) {
/*      */           
/* 1056 */           Company userCompany = (Company)userCompanies.elementAt(c);
/* 1057 */           if (userCompany != null && userCompany.getStructureID() == checkCompany.getStructureID()) {
/*      */             
/* 1059 */             isNew = false;
/*      */             break;
/*      */           } 
/*      */         } 
/* 1063 */         if (isNew)
/*      */         {
/*      */           
/* 1066 */           addUserCompany(context, userid, checkCompany.getStructureID(), 
/* 1067 */               sessionUser.getUserId(), copiedUserHash);
/*      */         }
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 1073 */     context.removeSessionValue("copiedUserObj");
/* 1074 */     copiedUserHash = null;
/*      */ 
/*      */     
/* 1077 */     if (IsRelFamNewUser) {
/*      */ 
/*      */       
/* 1080 */       User copiedUser = (User)context.getSessionValue("User");
/* 1081 */       if (copiedUser != null)
/*      */       {
/*      */         
/* 1084 */         ReleasingFamily.save(context, user, copiedUser);
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/* 1089 */     return user;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void saveCompany(User user, User sessionUser, CompanyAcl acl, Company company) {
/* 1100 */     String timestampQuery = "SELECT a.last_updated_ck, b.* FROM vi_User_Company a, vi_Structure b WHERE a.user_id = " + 
/*      */       
/* 1102 */       user.getUserId() + 
/* 1103 */       " AND a.company_id = b.structure_id " + 
/* 1104 */       " AND a.company_id = " + company.getStructureID() + 
/* 1105 */       " ORDER BY b.name";
/*      */     
/* 1107 */     long timestamp = -1L;
/*      */     
/* 1109 */     JdbcConnector connectorTimestamp = MilestoneHelper.getConnector(timestampQuery);
/* 1110 */     connectorTimestamp.runQuery();
/* 1111 */     if (connectorTimestamp.more())
/*      */     {
/* 1113 */       timestamp = Long.parseLong(connectorTimestamp.getField("last_updated_ck"), 16);
/*      */     }
/* 1115 */     connectorTimestamp.close();
/*      */     
/* 1117 */     StringBuffer aclString = new StringBuffer(25);
/*      */ 
/*      */     
/* 1120 */     aclString.append(acl.getAccessSelection());
/*      */ 
/*      */     
/* 1123 */     aclString.append(acl.getAccessSchedule());
/*      */ 
/*      */     
/* 1126 */     aclString.append(acl.getAccessManufacturing());
/*      */ 
/*      */     
/* 1129 */     aclString.append(acl.getAccessPfmForm());
/*      */ 
/*      */     
/* 1132 */     aclString.append(acl.getAccessBomForm());
/*      */ 
/*      */     
/* 1135 */     aclString.append(acl.getAccessReport());
/*      */ 
/*      */     
/* 1138 */     aclString.append(acl.getAccessTemplate());
/*      */ 
/*      */     
/* 1141 */     aclString.append(acl.getAccessTask());
/*      */ 
/*      */     
/* 1144 */     aclString.append(acl.getAccessDayType());
/*      */ 
/*      */     
/* 1147 */     aclString.append(acl.getAccessUser());
/*      */ 
/*      */     
/* 1150 */     aclString.append(acl.getAccessFamily());
/*      */ 
/*      */     
/* 1153 */     aclString.append(acl.getAccessEnvironment());
/*      */ 
/*      */     
/* 1156 */     aclString.append(acl.getAccessCompany());
/*      */ 
/*      */     
/* 1159 */     aclString.append(acl.getAccessDivision());
/*      */ 
/*      */     
/* 1162 */     aclString.append(acl.getAccessLabel());
/*      */ 
/*      */     
/* 1165 */     aclString.append(acl.getAccessTable());
/*      */ 
/*      */     
/* 1168 */     aclString.append(acl.getAccessParameter());
/*      */ 
/*      */     
/* 1171 */     aclString.append(acl.getAccessAuditTrail());
/*      */ 
/*      */     
/* 1174 */     aclString.append(acl.getAccessReportConfig());
/*      */ 
/*      */     
/* 1177 */     aclString.append(acl.getAccessPriceCode());
/*      */ 
/*      */     
/* 1180 */     String query = "sp_upd_User_Company " + 
/* 1181 */       user.getUserId() + "," + 
/* 1182 */       company.getStructureID() + "," + 
/* 1183 */       "'" + aclString.toString() + "'," + 
/* 1184 */       sessionUser.getUserId() + 
/* 1185 */       "," + timestamp;
/*      */ 
/*      */ 
/*      */     
/* 1189 */     JdbcConnector connector = MilestoneHelper.getConnector(query);
/* 1190 */     connector.runQuery();
/* 1191 */     connector.close();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void copyCompany(User user, User sessionUser, long copiedUserId) {
/* 1203 */     String query = "delete from User_company Where user_id =  " + 
/* 1204 */       user.getUserId();
/*      */ 
/*      */ 
/*      */     
/* 1208 */     JdbcConnector connector = MilestoneHelper.getConnector(query);
/* 1209 */     connector.runQuery();
/* 1210 */     connector.close();
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1215 */     query = "insert into User_Company ( [user_id],company_id,menu_access,last_updated_on,last_updated_by  ) Select  " + 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1222 */       user.getUserId() + ", company_id, " + 
/* 1223 */       "menu_access," + 
/* 1224 */       "getdate()," + 
/* 1225 */       sessionUser.getUserId() + 
/* 1226 */       " from User_company where user_id = " + 
/* 1227 */       copiedUserId;
/*      */ 
/*      */ 
/*      */     
/* 1231 */     connector = MilestoneHelper.getConnector(query);
/* 1232 */     connector.runQuery();
/* 1233 */     connector.close();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void deleteUser(User user, User sessionUser) {
/* 1242 */     String query = "sp_del_Users " + 
/* 1243 */       user.getUserId();
/*      */ 
/*      */     
/* 1246 */     JdbcConnector connector = MilestoneHelper.getConnector(query);
/* 1247 */     connector.runQuery();
/* 1248 */     connector.close();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1256 */   public boolean isAdmin(int pUserID) { return true; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1264 */   public boolean isAdmin(User pUser) { return true; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Vector getUserNotepadList(Notepad notepad) {
/* 1274 */     String query = "";
/* 1275 */     if (notepad != null && !notepad.getSearchQuery().equals("")) {
/*      */       
/* 1277 */       query = notepad.getSearchQuery();
/* 1278 */       query = String.valueOf(query) + notepad.getOrderBy();
/*      */     
/*      */     }
/*      */     else {
/*      */       
/* 1283 */       query = "SELECT DISTINCT vi_user.*, vi_structure.name as employedByStr FROM vi_all_user as vi_user  LEFT JOIN vi_structure  ON vi_user.[employed_by] = vi_structure.[structure_id] ORDER BY vi_user.[name]";
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1288 */     JdbcConnector connector = MilestoneHelper.getConnector(query);
/* 1289 */     connector.runQuery();
/*      */     
/* 1291 */     Vector precache = new Vector();
/* 1292 */     User user = null;
/*      */ 
/*      */     
/* 1295 */     while (connector.more()) {
/*      */ 
/*      */       
/* 1298 */       user = new User();
/* 1299 */       user.setUserId(connector.getIntegerField("user_id"));
/* 1300 */       user.setLogin(connector.getField("name", ""));
/* 1301 */       user.setName(connector.getField("full_name", ""));
/* 1302 */       user.setEmployedBy(connector.getInt("employed_by", -1));
/* 1303 */       user.setEmployedByString(connector.getField("employedByStr", ""));
/* 1304 */       precache.addElement(user);
/* 1305 */       user = null;
/* 1306 */       connector.next();
/*      */     } 
/*      */     
/* 1309 */     connector.close();
/*      */ 
/*      */     
/* 1312 */     return precache;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/* 1317 */   public static String getDefaultQuery() { return "SELECT DISTINCT vi_user.*, vi_structure.name as employedByStr FROM vi_all_user as vi_user  LEFT JOIN vi_structure  ON vi_user.[employed_by] = vi_structure.[structure_id]"; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1325 */   public static String getDefaultUserCompanyQuery(int id) { return "SELECT a.*, b.*, c.name FROM vi_User_Company a, vi_Structure b, vi_Structure c  WHERE a.company_id = b.structure_id  AND b.parent_id = c.structure_id  AND a.user_id = " + id; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1332 */   public static String getDefaultUserEnvironmentQuery(int id) { return "SELECT a.*, b.*, c.name FROM vi_User_Environment a, vi_Structure b, vi_Structure c  WHERE a.environment_id = b.structure_id  AND b.parent_id = c.structure_id  AND a.user_id = " + id; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Vector getUserCompanyNotepadList(int id, Notepad notepad) {
/* 1342 */     String query = "SELECT a.*, b.*, c.name FROM vi_User_Company a, vi_Structure b, vi_Structure c WHERE a.user_id = " + 
/*      */       
/* 1344 */       id + 
/* 1345 */       " AND a.company_id = b.structure_id " + 
/* 1346 */       " AND b.parent_id = c.structure_id " + 
/* 1347 */       "ORDER BY b.name";
/*      */     
/* 1349 */     if (notepad != null && !notepad.getSearchQuery().equals("")) {
/*      */       
/* 1351 */       query = notepad.getSearchQuery();
/* 1352 */       query = String.valueOf(query) + notepad.getOrderBy();
/*      */     } 
/*      */     
/* 1355 */     Vector precache = new Vector();
/* 1356 */     Company company = null;
/*      */ 
/*      */ 
/*      */     
/* 1360 */     JdbcConnector companyConnector = MilestoneHelper.createConnector(query);
/* 1361 */     companyConnector.runQuery();
/*      */     
/* 1363 */     while (companyConnector.more()) {
/*      */       
/* 1365 */       company = new Company();
/* 1366 */       company.setName(companyConnector.getField("name"));
/* 1367 */       company.setStructureID(companyConnector.getIntegerField("structure_id"));
/* 1368 */       company.setParentID(companyConnector.getIntegerField("parent_id"));
/* 1369 */       precache.addElement(company);
/* 1370 */       company = null;
/* 1371 */       companyConnector.next();
/*      */     } 
/*      */ 
/*      */     
/* 1375 */     return precache;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Vector getUserEnvironmentNotepadList(int id, Notepad notepad) {
/* 1385 */     String query = "SELECT a.*, b.*, c.name FROM vi_User_Company a, vi_Structure b, vi_Structure c WHERE a.user_id = " + 
/*      */       
/* 1387 */       id + 
/* 1388 */       " AND a.company_id = b.structure_id " + 
/* 1389 */       " AND b.parent_id = c.structure_id " + 
/* 1390 */       "ORDER BY b.name";
/*      */     
/* 1392 */     if (notepad != null && !notepad.getSearchQuery().equals("")) {
/*      */       
/* 1394 */       query = notepad.getSearchQuery();
/* 1395 */       query = String.valueOf(query) + notepad.getOrderBy();
/*      */     } 
/*      */     
/* 1398 */     Vector precache = new Vector();
/* 1399 */     Company company = null;
/*      */ 
/*      */ 
/*      */     
/* 1403 */     JdbcConnector companyConnector = MilestoneHelper.createConnector(query);
/* 1404 */     companyConnector.runQuery();
/*      */     
/* 1406 */     while (companyConnector.more()) {
/*      */       
/* 1408 */       company = new Company();
/* 1409 */       company.setName(companyConnector.getField("name"));
/* 1410 */       company.setStructureID(companyConnector.getIntegerField("structure_id"));
/* 1411 */       company.setParentID(companyConnector.getIntegerField("parent_id"));
/* 1412 */       precache.addElement(company);
/* 1413 */       company = null;
/* 1414 */       companyConnector.next();
/*      */     } 
/*      */     
/* 1417 */     companyConnector.close();
/*      */ 
/*      */     
/* 1420 */     Vector environmentList = new Vector();
/*      */ 
/*      */     
/* 1423 */     for (int j = 0; j < precache.size(); j++) {
/* 1424 */       Company aCompany = (Company)precache.elementAt(j);
/* 1425 */       Environment environment = (Environment)MilestoneHelper.getStructureObject(aCompany.getParentID());
/* 1426 */       if (!environmentList.contains(environment)) {
/* 1427 */         environmentList.add(environment);
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/* 1432 */     Object[] EnvironmentArray = environmentList.toArray();
/* 1433 */     Arrays.sort(EnvironmentArray, new CorpStructNameComparator());
/*      */     
/* 1435 */     Vector sortedEnvironmentVector = new Vector();
/* 1436 */     for (int j = 0; j < EnvironmentArray.length; j++) {
/* 1437 */       sortedEnvironmentVector.add(EnvironmentArray[j]);
/*      */     }
/*      */ 
/*      */     
/* 1441 */     return sortedEnvironmentVector;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void deleteUserCompany(int userId, int companyId) {
/* 1451 */     String query = "sp_del_User_Company " + 
/* 1452 */       userId + "," + 
/* 1453 */       companyId;
/*      */ 
/*      */ 
/*      */     
/* 1457 */     JdbcConnector connector = MilestoneHelper.getConnector(query);
/* 1458 */     connector.runQuery();
/* 1459 */     connector.close();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void addUserCompany(Context context, int userId, int companyId, int updator, Hashtable copiedUserHash) {
/* 1471 */     String query = "";
/* 1472 */     String menu_access = "11111111111111111111";
/*      */ 
/*      */     
/* 1475 */     String companyIdStr = String.valueOf(companyId);
/*      */     
/* 1477 */     if (copiedUserHash != null && copiedUserHash.containsKey(companyIdStr)) {
/* 1478 */       menu_access = (String)copiedUserHash.get(companyIdStr);
/*      */     }
/* 1480 */     query = "sp_ins_User_Company " + 
/* 1481 */       userId + "," + 
/* 1482 */       companyId + "," + 
/* 1483 */       "'" + menu_access + "'," + 
/* 1484 */       updator;
/*      */ 
/*      */     
/* 1487 */     JdbcConnector connector = MilestoneHelper.getConnector(query);
/* 1488 */     connector.runQuery();
/* 1489 */     connector.close();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setUserNotepadQuery(Context context, Notepad notepad) {
/* 1501 */     if (notepad != null) {
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1506 */       String loginSearch = context.getParameter("nameSrch");
/* 1507 */       String userSearch = context.getParameter("userNameSrch");
/* 1508 */       String employedBySrc = context.getParameter("employedBySrc");
/* 1509 */       String environmentSearch = context.getParameter("EnvironmentDescriptionSearch");
/* 1510 */       String params = "";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1516 */       String query = "SELECT DISTINCT vi_user.*, vi_structure.name as employedByStr FROM vi_all_user as vi_user  LEFT JOIN vi_structure  ON vi_user.[employed_by] = vi_structure.[structure_id]";
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
/* 1538 */       if (environmentSearch != null && (new Integer(environmentSearch)).intValue() > 0) {
/*      */         
/* 1540 */         Environment environmentSelected = (Environment)MilestoneHelper.getStructureObject((new Integer(environmentSearch)).intValue());
/* 1541 */         Vector userCompanies = environmentSelected.getCompanies();
/* 1542 */         String companyStringList = "";
/*      */         
/* 1544 */         for (int i = 0; i < userCompanies.size(); i++) {
/* 1545 */           Company currentCompany = (Company)userCompanies.elementAt(i);
/* 1546 */           String structureID = Integer.toString(currentCompany.getStructureID());
/* 1547 */           if (!companyStringList.equals("")) {
/* 1548 */             companyStringList = String.valueOf(companyStringList) + "," + structureID;
/*      */           } else {
/* 1550 */             companyStringList = structureID;
/*      */           } 
/*      */         } 
/*      */ 
/*      */         
/* 1555 */         String companyQuery = " JOIN user_company ON vi_user.[user_id] = user_company.[user_id]";
/* 1556 */         companyQuery = String.valueOf(companyQuery) + " AND user_company.company_id in (" + companyStringList + ") ";
/*      */         
/* 1558 */         query = String.valueOf(query) + companyQuery;
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 1563 */       if (MilestoneHelper.isStringNotEmpty(loginSearch)) {
/* 1564 */         query = String.valueOf(query) + MilestoneHelper.addQueryParams(query, " vi_user.name " + MilestoneHelper.setWildCardsEscapeSingleQuotes(loginSearch));
/*      */       }
/* 1566 */       if (MilestoneHelper.isStringNotEmpty(userSearch)) {
/* 1567 */         query = String.valueOf(query) + MilestoneHelper.addQueryParams(query, " vi_user.full_name " + MilestoneHelper.setWildCardsEscapeSingleQuotes(userSearch));
/*      */       }
/*      */       
/* 1570 */       if (employedBySrc != null && (new Integer(employedBySrc)).intValue() > 0) {
/* 1571 */         query = String.valueOf(query) + MilestoneHelper.addQueryParams(query, " vi_user.employed_by = " + employedBySrc);
/*      */       }
/*      */       
/* 1574 */       String order = " ORDER BY vi_user.[name]";
/*      */ 
/*      */ 
/*      */       
/* 1578 */       notepad.setSearchQuery(query);
/* 1579 */       notepad.setOrderBy(order);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setUserCompanyNotepadQuery(Context context, Notepad notepad) {
/* 1592 */     if (notepad != null) {
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1597 */       String companySearch = context.getParameter("nameSrch");
/*      */ 
/*      */       
/* 1600 */       User securityUser = (User)context.getSessionValue("securityUser");
/* 1601 */       int id = -1;
/* 1602 */       if (securityUser != null) {
/* 1603 */         id = securityUser.getUserId();
/*      */       }
/*      */       
/* 1606 */       String query = "SELECT a.*, b.*, c.name FROM vi_User_Company a, vi_Structure b, vi_Structure c ";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1612 */       query = String.valueOf(query) + MilestoneHelper.addQueryParams(query, " a.user_id = " + id);
/* 1613 */       query = String.valueOf(query) + MilestoneHelper.addQueryParams(query, " a.company_id = b.structure_id ");
/* 1614 */       query = String.valueOf(query) + MilestoneHelper.addQueryParams(query, " b.parent_id = c.structure_id ");
/*      */ 
/*      */       
/* 1617 */       if (MilestoneHelper.isStringNotEmpty(companySearch)) {
/* 1618 */         query = String.valueOf(query) + MilestoneHelper.addQueryParams(query, " c.name " + MilestoneHelper.setWildCardsEscapeSingleQuotes(companySearch));
/*      */       }
/*      */       
/* 1621 */       String order = " ORDER BY c.name";
/*      */       
/* 1623 */       notepad.setSearchQuery(query);
/* 1624 */       notepad.setOrderBy(order);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isTimestampValid(User user) {
/* 1634 */     if (user != null) {
/*      */       
/* 1636 */       String timestampQuery = "SELECT last_updated_ck  FROM vi_user WHERE user_id = " + 
/*      */         
/* 1638 */         user.getUserId() + 
/* 1639 */         ";";
/* 1640 */       JdbcConnector connectorTimestamp = MilestoneHelper.getConnector(timestampQuery);
/* 1641 */       connectorTimestamp.runQuery();
/* 1642 */       if (connectorTimestamp.more())
/*      */       {
/* 1644 */         if (user.getLastUpdatedCk() != Long.parseLong(connectorTimestamp.getField("last_updated_ck", ""), 16)) {
/*      */           
/* 1646 */           connectorTimestamp.close();
/* 1647 */           return false;
/*      */         } 
/*      */       }
/* 1650 */       connectorTimestamp.close();
/* 1651 */       return true;
/*      */     } 
/* 1653 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isDuplicate(User user) {
/* 1663 */     boolean isDuplicate = false;
/*      */     
/* 1665 */     if (user != null) {
/*      */       
/* 1667 */       String query = "SELECT * FROM vi_user WHERE  name = '" + 
/* 1668 */         MilestoneHelper.escapeSingleQuotes(user.getLogin()) + "' " + 
/* 1669 */         " AND user_id <> " + user.getUserId();
/*      */ 
/*      */ 
/*      */       
/* 1673 */       JdbcConnector connector = MilestoneHelper.getConnector(query);
/* 1674 */       connector.runQuery();
/*      */       
/* 1676 */       if (connector.more()) {
/* 1677 */         isDuplicate = true;
/*      */       }
/* 1679 */       connector.close();
/*      */     } 
/*      */     
/* 1682 */     return isDuplicate;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void initializeFields(User user, JdbcConnector connector) {
/* 1693 */     user.setUserId(connector.getIntegerField("user_id"));
/* 1694 */     user.setLogin(connector.getField("name", ""));
/* 1695 */     user.setName(connector.getField("full_name", ""));
/* 1696 */     user.setPassword(connector.getField("password", ""));
/* 1697 */     user.setEmployedBy(connector.getInt("employed_by", -1));
/* 1698 */     user.setEmployedByString(connector.getField("employedByStr", ""));
/*      */     
/* 1700 */     user.setReportsTo(connector.getField("report_to", ""));
/* 1701 */     user.setLocation(connector.getField("location", ""));
/* 1702 */     user.setEmail(connector.getField("email", ""));
/* 1703 */     user.setPhone(connector.getField("phone", ""));
/* 1704 */     user.setFax(connector.getField("fax", ""));
/* 1705 */     user.setInactive(connector.getInt("inactive"));
/* 1706 */     user.setAdministrator(connector.getInt("administrator"));
/* 1707 */     user.setAclString(connector.getField("access_tbl1", ""));
/* 1708 */     user.setDeptFilter(connector.getField("access_tbl3", ""));
/* 1709 */     user.setFilter(connector.getField("access_tbl4", ""));
/*      */     
/* 1711 */     user.setNew(false);
/*      */   }
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
/*      */   public static void setUserPreferenceReleaseCalendar(User user) {
/* 1725 */     if (user.getPreferences().getSelectionReleasingFamily() > 0)
/* 1726 */       user.RC_releasingFamily = String.valueOf(user.getPreferences().getSelectionReleasingFamily()); 
/* 1727 */     if (user.getPreferences().getSelectionEnvironment() > 0)
/* 1728 */       user.RC_environment = String.valueOf(user.getPreferences().getSelectionEnvironment()); 
/* 1729 */     if (user.getPreferences().getSelectionLabelContact() > 0)
/* 1730 */       user.RC_labelContact = String.valueOf(user.getPreferences().getSelectionLabelContact()); 
/* 1731 */     if (user.getPreferences().getSelectionProductType() > -1) {
/*      */ 
/*      */       
/* 1734 */       if (user.getPreferences().getSelectionProductType() == 0)
/* 1735 */         user.RC_productType = "Physical"; 
/* 1736 */       if (user.getPreferences().getSelectionProductType() == 1)
/* 1737 */         user.RC_productType = "Digital"; 
/* 1738 */       if (user.getPreferences().getSelectionProductType() == 2) {
/* 1739 */         user.RC_productType = "All";
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static StringBuffer buildCompanyAcl(CompanyAcl acl) {
/* 1746 */     StringBuffer aclString = new StringBuffer(25);
/*      */ 
/*      */     
/* 1749 */     aclString.append(acl.getAccessSelection());
/*      */ 
/*      */     
/* 1752 */     aclString.append(acl.getAccessSchedule());
/*      */ 
/*      */     
/* 1755 */     aclString.append(acl.getAccessManufacturing());
/*      */ 
/*      */     
/* 1758 */     aclString.append(acl.getAccessPfmForm());
/*      */ 
/*      */     
/* 1761 */     aclString.append(acl.getAccessBomForm());
/*      */ 
/*      */     
/* 1764 */     aclString.append(acl.getAccessReport());
/*      */ 
/*      */     
/* 1767 */     aclString.append(acl.getAccessTemplate());
/*      */ 
/*      */     
/* 1770 */     aclString.append(acl.getAccessTask());
/*      */ 
/*      */     
/* 1773 */     aclString.append(acl.getAccessDayType());
/*      */ 
/*      */     
/* 1776 */     aclString.append(acl.getAccessUser());
/*      */ 
/*      */     
/* 1779 */     aclString.append(acl.getAccessFamily());
/*      */ 
/*      */     
/* 1782 */     aclString.append(acl.getAccessEnvironment());
/*      */ 
/*      */     
/* 1785 */     aclString.append(acl.getAccessCompany());
/*      */ 
/*      */     
/* 1788 */     aclString.append(acl.getAccessDivision());
/*      */ 
/*      */     
/* 1791 */     aclString.append(acl.getAccessLabel());
/*      */ 
/*      */     
/* 1794 */     aclString.append(acl.getAccessTable());
/*      */ 
/*      */     
/* 1797 */     aclString.append(acl.getAccessParameter());
/*      */ 
/*      */     
/* 1800 */     aclString.append(acl.getAccessAuditTrail());
/*      */ 
/*      */     
/* 1803 */     aclString.append(acl.getAccessReportConfig());
/*      */ 
/*      */     
/* 1806 */     aclString.append(acl.getAccessPriceCode());
/*      */     
/* 1808 */     return aclString;
/*      */   }
/*      */ }


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\UserManager.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */