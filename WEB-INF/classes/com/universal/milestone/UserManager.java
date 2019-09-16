package WEB-INF.classes.com.universal.milestone;

import com.techempower.ComponentLog;
import com.techempower.EnhancedProperties;
import com.techempower.gemini.Context;
import com.techempower.gemini.GeminiApplication;
import com.universal.milestone.Acl;
import com.universal.milestone.Cache;
import com.universal.milestone.Company;
import com.universal.milestone.CompanyAcl;
import com.universal.milestone.CorpStructNameComparator;
import com.universal.milestone.Environment;
import com.universal.milestone.JdbcConnector;
import com.universal.milestone.MilestoneConstants;
import com.universal.milestone.MilestoneHelper;
import com.universal.milestone.MilestoneHelper_2;
import com.universal.milestone.MilestoneSecurity;
import com.universal.milestone.Notepad;
import com.universal.milestone.ReleasingFamily;
import com.universal.milestone.ScheduleManager;
import com.universal.milestone.User;
import com.universal.milestone.UserManager;
import com.universal.milestone.UserPreferences;
import com.universal.milestone.UserPreferencesManager;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;

public class UserManager implements MilestoneConstants {
  public static final String COMPONENT_CODE = "mUsr";
  
  public static final String DEFAULT_USER_QUERY = "SELECT DISTINCT vi_user.*, vi_structure.name as employedByStr FROM vi_all_user as vi_user  LEFT JOIN vi_structure  ON vi_user.[employed_by] = vi_structure.[structure_id]";
  
  public static final String DEFAULT_USER_ORDER = " ORDER BY vi_user.[name]";
  
  public static final String DEFAULT_USER_COMPANY_QUERY = "SELECT a.*, b.*, c.name FROM vi_User_Company a, vi_Structure b, vi_Structure c  WHERE a.company_id = b.structure_id  AND b.parent_id = c.structure_id ";
  
  public static final String DEFAULT_USER_COMPANY_ORDER = " ORDER BY b.name";
  
  public static final String DEFAULT_USER_ENVIRONMENT_QUERY = "SELECT a.*, b.*, c.name FROM vi_User_Environment a, vi_Structure b, vi_Structure c  WHERE a.environment_id = b.structure_id  AND b.parent_id = c.structure_id ";
  
  public static final String DEFAULT_USER_ENVIRONMENT_ORDER = " ORDER BY b.name";
  
  protected static UserManager userManager = null;
  
  protected static ComponentLog log;
  
  public static void configure(EnhancedProperties props, GeminiApplication application) { log = application.getLog("mUsr"); }
  
  public static UserManager getInstance() {
    if (userManager == null)
      userManager = new UserManager(); 
    return userManager;
  }
  
  public User getUser(String userName, String password) {
    String userQuery = "SELECT vi_user.*, vi_structure.name as employedByStr FROM [vi_user] LEFT JOIN vi_structure ON vi_user.[employed_by] = vi_structure.[structure_id] WHERE vi_user.name = '" + 
      
      MilestoneHelper.escapeSingleQuotes(userName) + "'" + 
      " AND vi_user.password = '" + MilestoneHelper.escapeSingleQuotes(password) + "';";
    User user = null;
    Acl acl = null;
    JdbcConnector connector = MilestoneHelper.getConnector(userQuery);
    connector.runQuery();
    if (connector.more()) {
      user = new User();
      initializeFields(user, connector);
      long lastUpdatedLong = Long.parseLong(connector.getField("last_updated_ck"), 16);
      int lastUpdatedBy = connector.getIntegerField("last_updated_by");
      String lastDateString = connector.getFieldByName("last_updated_on");
      connector.close();
      if (lastDateString != null)
        user.setLastUpdateDate(MilestoneHelper.getDatabaseDate(lastDateString)); 
      user.setLastUpdatingUser(lastUpdatedBy);
      user.setLastUpdatedCk(lastUpdatedLong);
      if (user.getAclString() != null) {
        String aclString = user.getAclString();
        acl = new Acl();
        if (aclString.length() > 0) {
          acl.setAccessSelection(aclString.substring(0, 1).equals("1"));
          acl.setAccessSchedule(aclString.substring(1, 2).equals("1"));
          acl.setAccessManufacturing(aclString.substring(2, 3).equals("1"));
          acl.setAccessPfmForm(aclString.substring(3, 4).equals("1"));
          acl.setAccessBomForm(aclString.substring(4, 5).equals("1"));
          acl.setAccessReport(aclString.substring(5, 6).equals("1"));
          acl.setAccessTemplate(aclString.substring(6, 7).equals("1"));
          acl.setAccessTask(aclString.substring(7, 8).equals("1"));
          acl.setAccessDayType(aclString.substring(8, 9).equals("1"));
          acl.setAccessUser(aclString.substring(9, 10).equals("1"));
          acl.setAccessFamily(aclString.substring(10, 11).equals("1"));
          if (aclString.length() > 19) {
            acl.setAccessEnvironment(aclString.substring(11, 12).equals("1"));
            acl.setAccessCompany(aclString.substring(12, 13).equals("1"));
            acl.setAccessDivision(aclString.substring(13, 14).equals("1"));
            acl.setAccessLabel(aclString.substring(14, 15).equals("1"));
            acl.setAccessTable(aclString.substring(15, 16).equals("1"));
            acl.setAccessParameter(aclString.substring(16, 17).equals("1"));
            acl.setAccessAuditTrail(aclString.substring(17, 18).equals("1"));
            acl.setAccessReportConfig(aclString.substring(18, 19).equals("1"));
            acl.setAccessPriceCode(aclString.substring(19, 20).equals("1"));
          } else {
            acl.setAccessCompany(aclString.substring(11, 12).equals("1"));
            acl.setAccessDivision(aclString.substring(12, 13).equals("1"));
            acl.setAccessLabel(aclString.substring(13, 14).equals("1"));
            acl.setAccessTable(aclString.substring(14, 15).equals("1"));
            acl.setAccessParameter(aclString.substring(15, 16).equals("1"));
            acl.setAccessAuditTrail(aclString.substring(16, 17).equals("1"));
            acl.setAccessReportConfig(aclString.substring(17, 18).equals("1"));
            acl.setAccessPriceCode(aclString.substring(18, 19).equals("1"));
          } 
        } 
      } 
      Vector companies = new Vector();
      String companyQuery = "sp_get_User_company_access " + String.valueOf(user.getUserId());
      connector.setQuery(companyQuery);
      connector.runQuery();
      while (connector.more()) {
        CompanyAcl newCompanyAcl = new CompanyAcl();
        newCompanyAcl.setCompanyName(connector.getField("name"));
        newCompanyAcl.setCompanyId(connector.getIntegerField("company_id"));
        if (connector.getField("menu_access") != null) {
          String companyAclString = connector.getField("menu_access");
          if (companyAclString.length() > 0) {
            newCompanyAcl.setAccessSelection(Integer.parseInt(companyAclString.substring(0, 1)));
            newCompanyAcl.setAccessSchedule(Integer.parseInt(companyAclString.substring(1, 2)));
            newCompanyAcl.setAccessManufacturing(Integer.parseInt(companyAclString.substring(2, 3)));
            newCompanyAcl.setAccessPfmForm(Integer.parseInt(companyAclString.substring(3, 4)));
            newCompanyAcl.setAccessBomForm(Integer.parseInt(companyAclString.substring(4, 5)));
            newCompanyAcl.setAccessReport(Integer.parseInt(companyAclString.substring(5, 6)));
            newCompanyAcl.setAccessTemplate(Integer.parseInt(companyAclString.substring(6, 7)));
            newCompanyAcl.setAccessTask(Integer.parseInt(companyAclString.substring(7, 8)));
            newCompanyAcl.setAccessDayType(Integer.parseInt(companyAclString.substring(8, 9)));
            newCompanyAcl.setAccessUser(Integer.parseInt(companyAclString.substring(9, 10)));
            newCompanyAcl.setAccessFamily(Integer.parseInt(companyAclString.substring(10, 11)));
            if (companyAclString.length() > 19) {
              newCompanyAcl.setAccessEnvironment(Integer.parseInt(companyAclString.substring(11, 12)));
              newCompanyAcl.setAccessCompany(Integer.parseInt(companyAclString.substring(12, 13)));
              newCompanyAcl.setAccessDivision(Integer.parseInt(companyAclString.substring(13, 14)));
              newCompanyAcl.setAccessLabel(Integer.parseInt(companyAclString.substring(14, 15)));
              newCompanyAcl.setAccessTable(Integer.parseInt(companyAclString.substring(15, 16)));
              newCompanyAcl.setAccessParameter(Integer.parseInt(companyAclString.substring(16, 17)));
              newCompanyAcl.setAccessAuditTrail(Integer.parseInt(companyAclString.substring(17, 18)));
              newCompanyAcl.setAccessReportConfig(Integer.parseInt(companyAclString.substring(18, 19)));
              newCompanyAcl.setAccessPriceCode(Integer.parseInt(companyAclString.substring(19, 20)));
            } else {
              newCompanyAcl.setAccessCompany(Integer.parseInt(companyAclString.substring(11, 12)));
              newCompanyAcl.setAccessDivision(Integer.parseInt(companyAclString.substring(12, 13)));
              newCompanyAcl.setAccessLabel(Integer.parseInt(companyAclString.substring(13, 14)));
              newCompanyAcl.setAccessTable(Integer.parseInt(companyAclString.substring(14, 15)));
              newCompanyAcl.setAccessParameter(Integer.parseInt(companyAclString.substring(15, 16)));
              newCompanyAcl.setAccessAuditTrail(Integer.parseInt(companyAclString.substring(16, 17)));
              newCompanyAcl.setAccessReportConfig(Integer.parseInt(companyAclString.substring(17, 18)));
              newCompanyAcl.setAccessPriceCode(Integer.parseInt(companyAclString.substring(18, 19)));
            } 
          } 
          companies.addElement(newCompanyAcl);
          newCompanyAcl = null;
        } 
        connector.next();
      } 
      if (acl != null) {
        acl.setCompanyAcl(companies);
        acl.setFamilyAccessHash(ScheduleManager.getInstance().buildTaskEditAccess(companies, user));
        acl.setScreenPermissionsHash();
        user.setAcl(acl);
      } else {
        user.newSelectionEditAccess = MilestoneHelper_2.userHasSelectionEditPermission(user.getAcl());
      } 
      user.setReleasingFamily(ReleasingFamily.get(user.getUserId()));
      user.setReleasingFamilyLabelFamily(ReleasingFamily.getReleasingFamilyLabelFamily(user.getUserId()));
      Vector userEnvironments = MilestoneHelper.getUserEnvironments(user.getUserId());
      user.setAssignedEnvironments(userEnvironments);
      UserPreferences up = UserPreferencesManager.getInstance().getUserPreferences(user.getUserId());
      user.setPreferences(up);
    } 
    connector.close();
    return user;
  }
  
  public User getUser(int userId, boolean getTimestamp) {
    String userQuery = "sp_get_User_All " + userId;
    User user = null;
    Acl acl = null;
    JdbcConnector connector = MilestoneHelper.getConnector(userQuery);
    connector.runQuery();
    if (connector.more()) {
      user = new User();
      initializeFields(user, connector);
      long lastUpdatedLong = Long.parseLong(connector.getField("last_updated_ck"), 16);
      int lastUpdatedBy = connector.getIntegerField("last_updated_by");
      String lastDateString = connector.getField("last_updated_on");
      connector.close();
      if (!lastDateString.equalsIgnoreCase("[none]"))
        user.setLastUpdateDate(MilestoneHelper.getDatabaseDate(lastDateString)); 
      user.setLastUpdatingUser(lastUpdatedBy);
      if (getTimestamp)
        user.setLastUpdatedCk(lastUpdatedLong); 
      if (user.getAclString() != null) {
        String aclString = user.getAclString();
        acl = new Acl();
        if (aclString.length() > 0) {
          acl.setAccessSelection(aclString.substring(0, 1).equals("1"));
          acl.setAccessSchedule(aclString.substring(1, 2).equals("1"));
          acl.setAccessManufacturing(aclString.substring(2, 3).equals("1"));
          acl.setAccessPfmForm(aclString.substring(3, 4).equals("1"));
          acl.setAccessBomForm(aclString.substring(4, 5).equals("1"));
          acl.setAccessReport(aclString.substring(5, 6).equals("1"));
          acl.setAccessTemplate(aclString.substring(6, 7).equals("1"));
          acl.setAccessTask(aclString.substring(7, 8).equals("1"));
          acl.setAccessDayType(aclString.substring(8, 9).equals("1"));
          acl.setAccessUser(aclString.substring(9, 10).equals("1"));
          acl.setAccessFamily(aclString.substring(10, 11).equals("1"));
          if (aclString.length() > 19) {
            acl.setAccessEnvironment(aclString.substring(11, 12).equals("1"));
            acl.setAccessCompany(aclString.substring(12, 13).equals("1"));
            acl.setAccessDivision(aclString.substring(13, 14).equals("1"));
            acl.setAccessLabel(aclString.substring(14, 15).equals("1"));
            acl.setAccessTable(aclString.substring(15, 16).equals("1"));
            acl.setAccessParameter(aclString.substring(16, 17).equals("1"));
            acl.setAccessAuditTrail(aclString.substring(17, 18).equals("1"));
            acl.setAccessReportConfig(aclString.substring(18, 19).equals("1"));
            acl.setAccessPriceCode(aclString.substring(19, 20).equals("1"));
          } else {
            acl.setAccessCompany(aclString.substring(11, 12).equals("1"));
            acl.setAccessDivision(aclString.substring(12, 13).equals("1"));
            acl.setAccessLabel(aclString.substring(13, 14).equals("1"));
            acl.setAccessTable(aclString.substring(14, 15).equals("1"));
            acl.setAccessParameter(aclString.substring(15, 16).equals("1"));
            acl.setAccessAuditTrail(aclString.substring(16, 17).equals("1"));
            acl.setAccessReportConfig(aclString.substring(17, 18).equals("1"));
            acl.setAccessPriceCode(aclString.substring(18, 19).equals("1"));
          } 
        } 
      } 
      Vector companies = new Vector();
      String companyQuery = "sp_get_User_company_access " + String.valueOf(user.getUserId());
      connector.setQuery(companyQuery);
      connector.runQuery();
      while (connector.more()) {
        CompanyAcl newCompanyAcl = new CompanyAcl();
        newCompanyAcl.setCompanyName(connector.getField("name"));
        newCompanyAcl.setCompanyId(connector.getIntegerField("company_id"));
        if (connector.getField("menu_access") != null) {
          String companyAclString = connector.getField("menu_access");
          if (companyAclString.length() > 0) {
            newCompanyAcl.setAccessSelection(Integer.parseInt(companyAclString.substring(0, 1)));
            newCompanyAcl.setAccessSchedule(Integer.parseInt(companyAclString.substring(1, 2)));
            newCompanyAcl.setAccessManufacturing(Integer.parseInt(companyAclString.substring(2, 3)));
            newCompanyAcl.setAccessPfmForm(Integer.parseInt(companyAclString.substring(3, 4)));
            newCompanyAcl.setAccessBomForm(Integer.parseInt(companyAclString.substring(4, 5)));
            newCompanyAcl.setAccessReport(Integer.parseInt(companyAclString.substring(5, 6)));
            newCompanyAcl.setAccessTemplate(Integer.parseInt(companyAclString.substring(6, 7)));
            newCompanyAcl.setAccessTask(Integer.parseInt(companyAclString.substring(7, 8)));
            newCompanyAcl.setAccessDayType(Integer.parseInt(companyAclString.substring(8, 9)));
            newCompanyAcl.setAccessUser(Integer.parseInt(companyAclString.substring(9, 10)));
            newCompanyAcl.setAccessFamily(Integer.parseInt(companyAclString.substring(10, 11)));
            if (companyAclString.length() > 19) {
              newCompanyAcl.setAccessEnvironment(Integer.parseInt(companyAclString.substring(11, 12)));
              newCompanyAcl.setAccessCompany(Integer.parseInt(companyAclString.substring(12, 13)));
              newCompanyAcl.setAccessDivision(Integer.parseInt(companyAclString.substring(13, 14)));
              newCompanyAcl.setAccessLabel(Integer.parseInt(companyAclString.substring(14, 15)));
              newCompanyAcl.setAccessTable(Integer.parseInt(companyAclString.substring(15, 16)));
              newCompanyAcl.setAccessParameter(Integer.parseInt(companyAclString.substring(16, 17)));
              newCompanyAcl.setAccessAuditTrail(Integer.parseInt(companyAclString.substring(17, 18)));
              newCompanyAcl.setAccessReportConfig(Integer.parseInt(companyAclString.substring(18, 19)));
              newCompanyAcl.setAccessPriceCode(Integer.parseInt(companyAclString.substring(19, 20)));
            } else {
              newCompanyAcl.setAccessCompany(Integer.parseInt(companyAclString.substring(11, 12)));
              newCompanyAcl.setAccessDivision(Integer.parseInt(companyAclString.substring(12, 13)));
              newCompanyAcl.setAccessLabel(Integer.parseInt(companyAclString.substring(13, 14)));
              newCompanyAcl.setAccessTable(Integer.parseInt(companyAclString.substring(14, 15)));
              newCompanyAcl.setAccessParameter(Integer.parseInt(companyAclString.substring(15, 16)));
              newCompanyAcl.setAccessAuditTrail(Integer.parseInt(companyAclString.substring(16, 17)));
              newCompanyAcl.setAccessReportConfig(Integer.parseInt(companyAclString.substring(17, 18)));
              newCompanyAcl.setAccessPriceCode(Integer.parseInt(companyAclString.substring(18, 19)));
            } 
          } 
          companies.addElement(newCompanyAcl);
          newCompanyAcl = null;
        } 
        connector.next();
      } 
      if (acl != null) {
        acl.setCompanyAcl(companies);
        acl.setFamilyAccessHash(ScheduleManager.getInstance().buildTaskEditAccess(companies, user));
        acl.setScreenPermissionsHash();
        user.setAcl(acl);
      } else {
        user.newSelectionEditAccess = MilestoneHelper_2.userHasSelectionEditPermission(user.getAcl());
      } 
      user.setReleasingFamily(ReleasingFamily.get(user.getUserId()));
      user.setReleasingFamilyLabelFamily(ReleasingFamily.getReleasingFamilyLabelFamily(user.getUserId()));
      Vector userEnvironments = MilestoneHelper.getUserEnvironments(user.getUserId());
      user.setAssignedEnvironments(userEnvironments);
      UserPreferences up = UserPreferencesManager.getInstance().getUserPreferences(user.getUserId());
      user.setPreferences(up);
    } 
    connector.close();
    return user;
  }
  
  public User getUser(int userId) {
    User user = null;
    if (userId > 0) {
      Hashtable users = Cache.getAllUsersHash();
      if (users != null)
        user = (User)users.get(String.valueOf(userId)); 
    } 
    return user;
  }
  
  public User getUserObject(JdbcConnector connector) {
    User user = null;
    Acl acl = null;
    if (connector != null && connector.more()) {
      user = new User();
      initializeFields(user, connector);
      long lastUpdatedLong = Long.parseLong(connector.getField("last_updated_ck"), 16);
      int lastUpdatedBy = connector.getIntegerField("last_updated_by");
      String lastDateString = connector.getField("last_updated_on");
      if (!lastDateString.equalsIgnoreCase("[none]"))
        user.setLastUpdateDate(MilestoneHelper.getDatabaseDate(lastDateString)); 
      user.setLastUpdatingUser(lastUpdatedBy);
      if (user.getAclString() != null) {
        String aclString = user.getAclString();
        acl = new Acl();
        if (aclString.length() > 0) {
          acl.setAccessSelection(aclString.substring(0, 1).equals("1"));
          acl.setAccessSchedule(aclString.substring(1, 2).equals("1"));
          acl.setAccessManufacturing(aclString.substring(2, 3).equals("1"));
          acl.setAccessPfmForm(aclString.substring(3, 4).equals("1"));
          acl.setAccessBomForm(aclString.substring(4, 5).equals("1"));
          acl.setAccessReport(aclString.substring(5, 6).equals("1"));
          acl.setAccessTemplate(aclString.substring(6, 7).equals("1"));
          acl.setAccessTask(aclString.substring(7, 8).equals("1"));
          acl.setAccessDayType(aclString.substring(8, 9).equals("1"));
          acl.setAccessUser(aclString.substring(9, 10).equals("1"));
          acl.setAccessFamily(aclString.substring(10, 11).equals("1"));
          if (aclString.length() > 19) {
            acl.setAccessEnvironment(aclString.substring(11, 12).equals("1"));
            acl.setAccessCompany(aclString.substring(12, 13).equals("1"));
            acl.setAccessDivision(aclString.substring(13, 14).equals("1"));
            acl.setAccessLabel(aclString.substring(14, 15).equals("1"));
            acl.setAccessTable(aclString.substring(15, 16).equals("1"));
            acl.setAccessParameter(aclString.substring(16, 17).equals("1"));
            acl.setAccessAuditTrail(aclString.substring(17, 18).equals("1"));
            acl.setAccessReportConfig(aclString.substring(18, 19).equals("1"));
            acl.setAccessPriceCode(aclString.substring(19, 20).equals("1"));
          } else {
            acl.setAccessCompany(aclString.substring(11, 12).equals("1"));
            acl.setAccessDivision(aclString.substring(12, 13).equals("1"));
            acl.setAccessLabel(aclString.substring(13, 14).equals("1"));
            acl.setAccessTable(aclString.substring(14, 15).equals("1"));
            acl.setAccessParameter(aclString.substring(15, 16).equals("1"));
            acl.setAccessAuditTrail(aclString.substring(16, 17).equals("1"));
            acl.setAccessReportConfig(aclString.substring(17, 18).equals("1"));
            acl.setAccessPriceCode(aclString.substring(18, 19).equals("1"));
          } 
        } 
      } 
      Vector companies = new Vector();
      String companyQuery = "sp_get_User_company_access " + String.valueOf(user.getUserId());
      JdbcConnector companyConnector = MilestoneHelper.getConnector(companyQuery);
      companyConnector.runQuery();
      while (companyConnector.more()) {
        CompanyAcl newCompanyAcl = new CompanyAcl();
        newCompanyAcl.setCompanyName(companyConnector.getField("name"));
        newCompanyAcl.setCompanyId(companyConnector.getIntegerField("company_id"));
        if (companyConnector.getField("menu_access") != null) {
          String companyAclString = companyConnector.getField("menu_access");
          if (companyAclString.length() > 0) {
            newCompanyAcl.setAccessSelection(Integer.parseInt(companyAclString.substring(0, 1)));
            newCompanyAcl.setAccessSchedule(Integer.parseInt(companyAclString.substring(1, 2)));
            newCompanyAcl.setAccessManufacturing(Integer.parseInt(companyAclString.substring(2, 3)));
            newCompanyAcl.setAccessPfmForm(Integer.parseInt(companyAclString.substring(3, 4)));
            newCompanyAcl.setAccessBomForm(Integer.parseInt(companyAclString.substring(4, 5)));
            newCompanyAcl.setAccessReport(Integer.parseInt(companyAclString.substring(5, 6)));
            newCompanyAcl.setAccessTemplate(Integer.parseInt(companyAclString.substring(6, 7)));
            newCompanyAcl.setAccessTask(Integer.parseInt(companyAclString.substring(7, 8)));
            newCompanyAcl.setAccessDayType(Integer.parseInt(companyAclString.substring(8, 9)));
            newCompanyAcl.setAccessUser(Integer.parseInt(companyAclString.substring(9, 10)));
            newCompanyAcl.setAccessFamily(Integer.parseInt(companyAclString.substring(10, 11)));
            if (companyAclString.length() > 19) {
              newCompanyAcl.setAccessEnvironment(Integer.parseInt(companyAclString.substring(11, 12)));
              newCompanyAcl.setAccessCompany(Integer.parseInt(companyAclString.substring(12, 13)));
              newCompanyAcl.setAccessDivision(Integer.parseInt(companyAclString.substring(13, 14)));
              newCompanyAcl.setAccessLabel(Integer.parseInt(companyAclString.substring(14, 15)));
              newCompanyAcl.setAccessTable(Integer.parseInt(companyAclString.substring(15, 16)));
              newCompanyAcl.setAccessParameter(Integer.parseInt(companyAclString.substring(16, 17)));
              newCompanyAcl.setAccessAuditTrail(Integer.parseInt(companyAclString.substring(17, 18)));
              newCompanyAcl.setAccessReportConfig(Integer.parseInt(companyAclString.substring(18, 19)));
              newCompanyAcl.setAccessPriceCode(Integer.parseInt(companyAclString.substring(19, 20)));
            } else {
              newCompanyAcl.setAccessCompany(Integer.parseInt(companyAclString.substring(11, 12)));
              newCompanyAcl.setAccessDivision(Integer.parseInt(companyAclString.substring(12, 13)));
              newCompanyAcl.setAccessLabel(Integer.parseInt(companyAclString.substring(13, 14)));
              newCompanyAcl.setAccessTable(Integer.parseInt(companyAclString.substring(14, 15)));
              newCompanyAcl.setAccessParameter(Integer.parseInt(companyAclString.substring(15, 16)));
              newCompanyAcl.setAccessAuditTrail(Integer.parseInt(companyAclString.substring(16, 17)));
              newCompanyAcl.setAccessReportConfig(Integer.parseInt(companyAclString.substring(17, 18)));
              newCompanyAcl.setAccessPriceCode(Integer.parseInt(companyAclString.substring(18, 19)));
            } 
          } 
          companies.addElement(newCompanyAcl);
          newCompanyAcl = null;
        } 
        companyConnector.next();
      } 
      if (acl != null) {
        acl.setCompanyAcl(companies);
        acl.setFamilyAccessHash(ScheduleManager.getInstance().buildTaskEditAccess(companies, user));
        acl.setScreenPermissionsHash();
        user.setAcl(acl);
      } else {
        user.newSelectionEditAccess = MilestoneHelper_2.userHasSelectionEditPermission(user.getAcl());
      } 
      companyConnector.close();
      UserPreferences up = UserPreferencesManager.getInstance().getUserPreferences(user.getUserId());
      user.setPreferences(up);
    } 
    return user;
  }
  
  public boolean login(String userName, String password) {
    User user = getUser(userName, password);
    if (user != null)
      return user.getPassword().equalsIgnoreCase(password); 
    return false;
  }
  
  public void logout(Context context) { MilestoneSecurity.processLogout(context); }
  
  public void clearSession(Context context) {
    String lastLink = (context.getSessionValue("lastLink") != null) ? (String)context.getSessionValue("lastLink") : "";
    context.removeAllSessionValues();
    if (lastLink.length() > 0)
      context.putSessionValue("lastLink", lastLink); 
  }
  
  public boolean register(User pUser) { return true; }
  
  public User save(User user, User sessionUser, Vector checkedCompanies, Context context) {
    StringBuffer aclString = new StringBuffer(25);
    Acl acl = user.getAcl();
    if (acl.getAccessSelection()) {
      aclString.append('1');
    } else {
      aclString.append('0');
    } 
    if (acl.getAccessSchedule()) {
      aclString.append('1');
    } else {
      aclString.append('0');
    } 
    if (acl.getAccessManufacturing()) {
      aclString.append('1');
    } else {
      aclString.append('0');
    } 
    if (acl.getAccessPfmForm()) {
      aclString.append('1');
    } else {
      aclString.append('0');
    } 
    if (acl.getAccessBomForm()) {
      aclString.append('1');
    } else {
      aclString.append('0');
    } 
    if (acl.getAccessReport()) {
      aclString.append('1');
    } else {
      aclString.append('0');
    } 
    if (acl.getAccessTemplate()) {
      aclString.append('1');
    } else {
      aclString.append('0');
    } 
    if (acl.getAccessTask()) {
      aclString.append('1');
    } else {
      aclString.append('0');
    } 
    if (acl.getAccessDayType()) {
      aclString.append('1');
    } else {
      aclString.append('0');
    } 
    if (acl.getAccessUser()) {
      aclString.append('1');
    } else {
      aclString.append('0');
    } 
    if (acl.getAccessFamily()) {
      aclString.append('1');
    } else {
      aclString.append('0');
    } 
    if (acl.getAccessEnvironment()) {
      aclString.append('1');
    } else {
      aclString.append('0');
    } 
    if (acl.getAccessCompany()) {
      aclString.append('1');
    } else {
      aclString.append('0');
    } 
    if (acl.getAccessDivision()) {
      aclString.append('1');
    } else {
      aclString.append('0');
    } 
    if (acl.getAccessLabel()) {
      aclString.append('1');
    } else {
      aclString.append('0');
    } 
    if (acl.getAccessTable()) {
      aclString.append('1');
    } else {
      aclString.append('0');
    } 
    if (acl.getAccessParameter()) {
      aclString.append('1');
    } else {
      aclString.append('0');
    } 
    if (acl.getAccessAuditTrail()) {
      aclString.append('1');
    } else {
      aclString.append('0');
    } 
    if (acl.getAccessReportConfig()) {
      aclString.append('1');
    } else {
      aclString.append('0');
    } 
    if (acl.getAccessPriceCode()) {
      aclString.append('1');
    } else {
      aclString.append('0');
    } 
    long timestamp = user.getLastUpdatedCk();
    String query = "sp_sav_User " + 
      user.getUserId() + 
      ",'" + MilestoneHelper.escapeSingleQuotes(user.getLogin()) + "'" + 
      ",'" + MilestoneHelper.escapeSingleQuotes(user.getName()) + "'" + 
      ",'" + MilestoneHelper.escapeSingleQuotes(user.getPassword()) + "'" + 
      ",'" + MilestoneHelper.escapeSingleQuotes(user.getReportsTo()) + "'" + 
      "," + user.getEmployedBy() + 
      ",'" + MilestoneHelper.escapeSingleQuotes(user.getLocation()) + "'" + 
      ",'" + aclString.toString() + "'" + 
      ",''" + 
      ",'" + user.getDeptFilter() + "'" + 
      ",'" + user.getFilter() + "'" + 
      ",''" + 
      "," + sessionUser.getUserId() + 
      "," + timestamp + 
      ",'" + user.getEmail() + "'" + 
      ",'" + user.getPhone() + "'" + 
      ",'" + user.getFax() + "'" + 
      "," + user.getInactive() + 
      "," + user.getAdministrator();
    boolean IsRelFamNewUser = false;
    if (user.getUserId() == -1)
      IsRelFamNewUser = true; 
    JdbcConnector connector = MilestoneHelper.getConnector(query);
    connector.runQuery();
    int savedUserId = connector.getIntegerField("ReturnId");
    connector.close();
    if (savedUserId > 0)
      user.setUserId(savedUserId); 
    user.setAcl(acl);
    user.newSelectionEditAccess = MilestoneHelper_2.userHasSelectionEditPermission(user.getAcl());
    String timestampQuery = "SELECT last_updated_ck, last_updated_on FROM vi_user WHERE user_id = " + 
      
      user.getUserId() + 
      ";";
    JdbcConnector connectorTimestamp = MilestoneHelper.getConnector(timestampQuery);
    connectorTimestamp.runQuery();
    if (connectorTimestamp.more()) {
      user.setLastUpdatedCk(Long.parseLong(connectorTimestamp.getField("last_updated_ck"), 16));
      String lastString = connectorTimestamp.getField("last_updated_on");
      if (!lastString.equalsIgnoreCase("[none]"))
        user.setLastUpdateDate(MilestoneHelper.getDatabaseDate(connectorTimestamp.getField("last_updated_on"))); 
    } 
    connectorTimestamp.close();
    Vector companies = Cache.getInstance().getCompanies();
    int userid = user.getUserId();
    Vector userCompanies = MilestoneHelper.getUserCompanies(userid);
    if (userCompanies == null)
      userCompanies = new Vector(); 
    boolean isNew = true;
    boolean isChecked = false;
    boolean isUnChecked = false;
    for (int b = 0; b < userCompanies.size(); b++) {
      Company userCompany = (Company)userCompanies.elementAt(b);
      if (userCompany != null) {
        isChecked = false;
        for (int j = 0; j < checkedCompanies.size(); j++) {
          Company checkCompany = (Company)checkedCompanies.get(j);
          if (checkCompany != null && checkCompany.getStructureID() == userCompany.getStructureID()) {
            isChecked = true;
            break;
          } 
        } 
        if (!isChecked)
          deleteUserCompany(userid, userCompany.getStructureID()); 
      } 
    } 
    Hashtable copiedUserHash = null;
    if (checkedCompanies.size() > 0 && context.getSessionValue("copiedUserObj") != null) {
      copiedUserHash = new Hashtable();
      User copiedUserObj = (User)context.getSessionValue("copiedUserObj");
      Acl currentAcl = copiedUserObj.getAcl();
      Vector companyAcl = currentAcl.getCompanyAcl();
      for (int i = 0; i < companyAcl.size(); i++) {
        CompanyAcl tempAcl = (CompanyAcl)companyAcl.get(i);
        if (tempAcl != null) {
          StringBuffer aclBuffer = buildCompanyAcl(tempAcl);
          String companyId = String.valueOf(tempAcl.getCompanyId());
          if (!copiedUserHash.containsKey(companyId))
            copiedUserHash.put(companyId, aclBuffer.toString()); 
        } 
      } 
    } 
    for (int l = 0; l < checkedCompanies.size(); l++) {
      Company checkCompany = (Company)checkedCompanies.get(l);
      if (checkCompany != null) {
        isNew = true;
        for (int c = 0; c < userCompanies.size(); c++) {
          Company userCompany = (Company)userCompanies.elementAt(c);
          if (userCompany != null && userCompany.getStructureID() == checkCompany.getStructureID()) {
            isNew = false;
            break;
          } 
        } 
        if (isNew)
          addUserCompany(context, userid, checkCompany.getStructureID(), 
              sessionUser.getUserId(), copiedUserHash); 
      } 
    } 
    context.removeSessionValue("copiedUserObj");
    copiedUserHash = null;
    if (IsRelFamNewUser) {
      User copiedUser = (User)context.getSessionValue("User");
      if (copiedUser != null)
        ReleasingFamily.save(context, user, copiedUser); 
    } 
    return user;
  }
  
  public void saveCompany(User user, User sessionUser, CompanyAcl acl, Company company) {
    String timestampQuery = "SELECT a.last_updated_ck, b.* FROM vi_User_Company a, vi_Structure b WHERE a.user_id = " + 
      
      user.getUserId() + 
      " AND a.company_id = b.structure_id " + 
      " AND a.company_id = " + company.getStructureID() + 
      " ORDER BY b.name";
    long timestamp = -1L;
    JdbcConnector connectorTimestamp = MilestoneHelper.getConnector(timestampQuery);
    connectorTimestamp.runQuery();
    if (connectorTimestamp.more())
      timestamp = Long.parseLong(connectorTimestamp.getField("last_updated_ck"), 16); 
    connectorTimestamp.close();
    StringBuffer aclString = new StringBuffer(25);
    aclString.append(acl.getAccessSelection());
    aclString.append(acl.getAccessSchedule());
    aclString.append(acl.getAccessManufacturing());
    aclString.append(acl.getAccessPfmForm());
    aclString.append(acl.getAccessBomForm());
    aclString.append(acl.getAccessReport());
    aclString.append(acl.getAccessTemplate());
    aclString.append(acl.getAccessTask());
    aclString.append(acl.getAccessDayType());
    aclString.append(acl.getAccessUser());
    aclString.append(acl.getAccessFamily());
    aclString.append(acl.getAccessEnvironment());
    aclString.append(acl.getAccessCompany());
    aclString.append(acl.getAccessDivision());
    aclString.append(acl.getAccessLabel());
    aclString.append(acl.getAccessTable());
    aclString.append(acl.getAccessParameter());
    aclString.append(acl.getAccessAuditTrail());
    aclString.append(acl.getAccessReportConfig());
    aclString.append(acl.getAccessPriceCode());
    String query = "sp_upd_User_Company " + 
      user.getUserId() + "," + 
      company.getStructureID() + "," + 
      "'" + aclString.toString() + "'," + 
      sessionUser.getUserId() + 
      "," + timestamp;
    JdbcConnector connector = MilestoneHelper.getConnector(query);
    connector.runQuery();
    connector.close();
  }
  
  public void copyCompany(User user, User sessionUser, long copiedUserId) {
    String query = "delete from User_company Where user_id =  " + 
      user.getUserId();
    JdbcConnector connector = MilestoneHelper.getConnector(query);
    connector.runQuery();
    connector.close();
    query = "insert into User_Company ( [user_id],company_id,menu_access,last_updated_on,last_updated_by  ) Select  " + 
      
      user.getUserId() + ", company_id, " + 
      "menu_access," + 
      "getdate()," + 
      sessionUser.getUserId() + 
      " from User_company where user_id = " + 
      copiedUserId;
    connector = MilestoneHelper.getConnector(query);
    connector.runQuery();
    connector.close();
  }
  
  public void deleteUser(User user, User sessionUser) {
    String query = "sp_del_Users " + 
      user.getUserId();
    JdbcConnector connector = MilestoneHelper.getConnector(query);
    connector.runQuery();
    connector.close();
  }
  
  public boolean isAdmin(int pUserID) { return true; }
  
  public boolean isAdmin(User pUser) { return true; }
  
  public static Vector getUserNotepadList(Notepad notepad) {
    String query = "";
    if (notepad != null && !notepad.getSearchQuery().equals("")) {
      query = notepad.getSearchQuery();
      query = String.valueOf(query) + notepad.getOrderBy();
    } else {
      query = "SELECT DISTINCT vi_user.*, vi_structure.name as employedByStr FROM vi_all_user as vi_user  LEFT JOIN vi_structure  ON vi_user.[employed_by] = vi_structure.[structure_id] ORDER BY vi_user.[name]";
    } 
    JdbcConnector connector = MilestoneHelper.getConnector(query);
    connector.runQuery();
    Vector precache = new Vector();
    User user = null;
    while (connector.more()) {
      user = new User();
      user.setUserId(connector.getIntegerField("user_id"));
      user.setLogin(connector.getField("name", ""));
      user.setName(connector.getField("full_name", ""));
      user.setEmployedBy(connector.getInt("employed_by", -1));
      user.setEmployedByString(connector.getField("employedByStr", ""));
      precache.addElement(user);
      user = null;
      connector.next();
    } 
    connector.close();
    return precache;
  }
  
  public static String getDefaultQuery() { return "SELECT DISTINCT vi_user.*, vi_structure.name as employedByStr FROM vi_all_user as vi_user  LEFT JOIN vi_structure  ON vi_user.[employed_by] = vi_structure.[structure_id]"; }
  
  public static String getDefaultUserCompanyQuery(int id) { return "SELECT a.*, b.*, c.name FROM vi_User_Company a, vi_Structure b, vi_Structure c  WHERE a.company_id = b.structure_id  AND b.parent_id = c.structure_id  AND a.user_id = " + id; }
  
  public static String getDefaultUserEnvironmentQuery(int id) { return "SELECT a.*, b.*, c.name FROM vi_User_Environment a, vi_Structure b, vi_Structure c  WHERE a.environment_id = b.structure_id  AND b.parent_id = c.structure_id  AND a.user_id = " + id; }
  
  public static Vector getUserCompanyNotepadList(int id, Notepad notepad) {
    String query = "SELECT a.*, b.*, c.name FROM vi_User_Company a, vi_Structure b, vi_Structure c WHERE a.user_id = " + 
      
      id + 
      " AND a.company_id = b.structure_id " + 
      " AND b.parent_id = c.structure_id " + 
      "ORDER BY b.name";
    if (notepad != null && !notepad.getSearchQuery().equals("")) {
      query = notepad.getSearchQuery();
      query = String.valueOf(query) + notepad.getOrderBy();
    } 
    Vector precache = new Vector();
    Company company = null;
    JdbcConnector companyConnector = MilestoneHelper.createConnector(query);
    companyConnector.runQuery();
    while (companyConnector.more()) {
      company = new Company();
      company.setName(companyConnector.getField("name"));
      company.setStructureID(companyConnector.getIntegerField("structure_id"));
      company.setParentID(companyConnector.getIntegerField("parent_id"));
      precache.addElement(company);
      company = null;
      companyConnector.next();
    } 
    return precache;
  }
  
  public static Vector getUserEnvironmentNotepadList(int id, Notepad notepad) {
    String query = "SELECT a.*, b.*, c.name FROM vi_User_Company a, vi_Structure b, vi_Structure c WHERE a.user_id = " + 
      
      id + 
      " AND a.company_id = b.structure_id " + 
      " AND b.parent_id = c.structure_id " + 
      "ORDER BY b.name";
    if (notepad != null && !notepad.getSearchQuery().equals("")) {
      query = notepad.getSearchQuery();
      query = String.valueOf(query) + notepad.getOrderBy();
    } 
    Vector precache = new Vector();
    Company company = null;
    JdbcConnector companyConnector = MilestoneHelper.createConnector(query);
    companyConnector.runQuery();
    while (companyConnector.more()) {
      company = new Company();
      company.setName(companyConnector.getField("name"));
      company.setStructureID(companyConnector.getIntegerField("structure_id"));
      company.setParentID(companyConnector.getIntegerField("parent_id"));
      precache.addElement(company);
      company = null;
      companyConnector.next();
    } 
    companyConnector.close();
    Vector environmentList = new Vector();
    for (int j = 0; j < precache.size(); j++) {
      Company aCompany = (Company)precache.elementAt(j);
      Environment environment = (Environment)MilestoneHelper.getStructureObject(aCompany.getParentID());
      if (!environmentList.contains(environment))
        environmentList.add(environment); 
    } 
    Object[] EnvironmentArray = environmentList.toArray();
    Arrays.sort(EnvironmentArray, new CorpStructNameComparator());
    Vector sortedEnvironmentVector = new Vector();
    for (int j = 0; j < EnvironmentArray.length; j++)
      sortedEnvironmentVector.add(EnvironmentArray[j]); 
    return sortedEnvironmentVector;
  }
  
  public static void deleteUserCompany(int userId, int companyId) {
    String query = "sp_del_User_Company " + 
      userId + "," + 
      companyId;
    JdbcConnector connector = MilestoneHelper.getConnector(query);
    connector.runQuery();
    connector.close();
  }
  
  public static void addUserCompany(Context context, int userId, int companyId, int updator, Hashtable copiedUserHash) {
    String query = "";
    String menu_access = "11111111111111111111";
    String companyIdStr = String.valueOf(companyId);
    if (copiedUserHash != null && copiedUserHash.containsKey(companyIdStr))
      menu_access = (String)copiedUserHash.get(companyIdStr); 
    query = "sp_ins_User_Company " + 
      userId + "," + 
      companyId + "," + 
      "'" + menu_access + "'," + 
      updator;
    JdbcConnector connector = MilestoneHelper.getConnector(query);
    connector.runQuery();
    connector.close();
  }
  
  public void setUserNotepadQuery(Context context, Notepad notepad) {
    if (notepad != null) {
      String loginSearch = context.getParameter("nameSrch");
      String userSearch = context.getParameter("userNameSrch");
      String employedBySrc = context.getParameter("employedBySrc");
      String environmentSearch = context.getParameter("EnvironmentDescriptionSearch");
      String params = "";
      String query = "SELECT DISTINCT vi_user.*, vi_structure.name as employedByStr FROM vi_all_user as vi_user  LEFT JOIN vi_structure  ON vi_user.[employed_by] = vi_structure.[structure_id]";
      if (environmentSearch != null && (new Integer(environmentSearch)).intValue() > 0) {
        Environment environmentSelected = (Environment)MilestoneHelper.getStructureObject((new Integer(environmentSearch)).intValue());
        Vector userCompanies = environmentSelected.getCompanies();
        String companyStringList = "";
        for (int i = 0; i < userCompanies.size(); i++) {
          Company currentCompany = (Company)userCompanies.elementAt(i);
          String structureID = Integer.toString(currentCompany.getStructureID());
          if (!companyStringList.equals("")) {
            companyStringList = String.valueOf(companyStringList) + "," + structureID;
          } else {
            companyStringList = structureID;
          } 
        } 
        String companyQuery = " JOIN user_company ON vi_user.[user_id] = user_company.[user_id]";
        companyQuery = String.valueOf(companyQuery) + " AND user_company.company_id in (" + companyStringList + ") ";
        query = String.valueOf(query) + companyQuery;
      } 
      if (MilestoneHelper.isStringNotEmpty(loginSearch))
        query = String.valueOf(query) + MilestoneHelper.addQueryParams(query, " vi_user.name " + MilestoneHelper.setWildCardsEscapeSingleQuotes(loginSearch)); 
      if (MilestoneHelper.isStringNotEmpty(userSearch))
        query = String.valueOf(query) + MilestoneHelper.addQueryParams(query, " vi_user.full_name " + MilestoneHelper.setWildCardsEscapeSingleQuotes(userSearch)); 
      if (employedBySrc != null && (new Integer(employedBySrc)).intValue() > 0)
        query = String.valueOf(query) + MilestoneHelper.addQueryParams(query, " vi_user.employed_by = " + employedBySrc); 
      String order = " ORDER BY vi_user.[name]";
      notepad.setSearchQuery(query);
      notepad.setOrderBy(order);
    } 
  }
  
  public void setUserCompanyNotepadQuery(Context context, Notepad notepad) {
    if (notepad != null) {
      String companySearch = context.getParameter("nameSrch");
      User securityUser = (User)context.getSessionValue("securityUser");
      int id = -1;
      if (securityUser != null)
        id = securityUser.getUserId(); 
      String query = "SELECT a.*, b.*, c.name FROM vi_User_Company a, vi_Structure b, vi_Structure c ";
      query = String.valueOf(query) + MilestoneHelper.addQueryParams(query, " a.user_id = " + id);
      query = String.valueOf(query) + MilestoneHelper.addQueryParams(query, " a.company_id = b.structure_id ");
      query = String.valueOf(query) + MilestoneHelper.addQueryParams(query, " b.parent_id = c.structure_id ");
      if (MilestoneHelper.isStringNotEmpty(companySearch))
        query = String.valueOf(query) + MilestoneHelper.addQueryParams(query, " c.name " + MilestoneHelper.setWildCardsEscapeSingleQuotes(companySearch)); 
      String order = " ORDER BY c.name";
      notepad.setSearchQuery(query);
      notepad.setOrderBy(order);
    } 
  }
  
  public boolean isTimestampValid(User user) {
    if (user != null) {
      String timestampQuery = "SELECT last_updated_ck  FROM vi_user WHERE user_id = " + 
        
        user.getUserId() + 
        ";";
      JdbcConnector connectorTimestamp = MilestoneHelper.getConnector(timestampQuery);
      connectorTimestamp.runQuery();
      if (connectorTimestamp.more())
        if (user.getLastUpdatedCk() != Long.parseLong(connectorTimestamp.getField("last_updated_ck", ""), 16)) {
          connectorTimestamp.close();
          return false;
        }  
      connectorTimestamp.close();
      return true;
    } 
    return false;
  }
  
  public boolean isDuplicate(User user) {
    boolean isDuplicate = false;
    if (user != null) {
      String query = "SELECT * FROM vi_user WHERE  name = '" + 
        MilestoneHelper.escapeSingleQuotes(user.getLogin()) + "' " + 
        " AND user_id <> " + user.getUserId();
      JdbcConnector connector = MilestoneHelper.getConnector(query);
      connector.runQuery();
      if (connector.more())
        isDuplicate = true; 
      connector.close();
    } 
    return isDuplicate;
  }
  
  public void initializeFields(User user, JdbcConnector connector) {
    user.setUserId(connector.getIntegerField("user_id"));
    user.setLogin(connector.getField("name", ""));
    user.setName(connector.getField("full_name", ""));
    user.setPassword(connector.getField("password", ""));
    user.setEmployedBy(connector.getInt("employed_by", -1));
    user.setEmployedByString(connector.getField("employedByStr", ""));
    user.setReportsTo(connector.getField("report_to", ""));
    user.setLocation(connector.getField("location", ""));
    user.setEmail(connector.getField("email", ""));
    user.setPhone(connector.getField("phone", ""));
    user.setFax(connector.getField("fax", ""));
    user.setInactive(connector.getInt("inactive"));
    user.setAdministrator(connector.getInt("administrator"));
    user.setAclString(connector.getField("access_tbl1", ""));
    user.setDeptFilter(connector.getField("access_tbl3", ""));
    user.setFilter(connector.getField("access_tbl4", ""));
    user.setNew(false);
  }
  
  public static void setUserPreferenceReleaseCalendar(User user) {
    if (user.getPreferences().getSelectionReleasingFamily() > 0)
      user.RC_releasingFamily = String.valueOf(user.getPreferences().getSelectionReleasingFamily()); 
    if (user.getPreferences().getSelectionEnvironment() > 0)
      user.RC_environment = String.valueOf(user.getPreferences().getSelectionEnvironment()); 
    if (user.getPreferences().getSelectionLabelContact() > 0)
      user.RC_labelContact = String.valueOf(user.getPreferences().getSelectionLabelContact()); 
    if (user.getPreferences().getSelectionProductType() > -1) {
      if (user.getPreferences().getSelectionProductType() == 0)
        user.RC_productType = "Physical"; 
      if (user.getPreferences().getSelectionProductType() == 1)
        user.RC_productType = "Digital"; 
      if (user.getPreferences().getSelectionProductType() == 2)
        user.RC_productType = "All"; 
    } 
  }
  
  public static StringBuffer buildCompanyAcl(CompanyAcl acl) {
    StringBuffer aclString = new StringBuffer(25);
    aclString.append(acl.getAccessSelection());
    aclString.append(acl.getAccessSchedule());
    aclString.append(acl.getAccessManufacturing());
    aclString.append(acl.getAccessPfmForm());
    aclString.append(acl.getAccessBomForm());
    aclString.append(acl.getAccessReport());
    aclString.append(acl.getAccessTemplate());
    aclString.append(acl.getAccessTask());
    aclString.append(acl.getAccessDayType());
    aclString.append(acl.getAccessUser());
    aclString.append(acl.getAccessFamily());
    aclString.append(acl.getAccessEnvironment());
    aclString.append(acl.getAccessCompany());
    aclString.append(acl.getAccessDivision());
    aclString.append(acl.getAccessLabel());
    aclString.append(acl.getAccessTable());
    aclString.append(acl.getAccessParameter());
    aclString.append(acl.getAccessAuditTrail());
    aclString.append(acl.getAccessReportConfig());
    aclString.append(acl.getAccessPriceCode());
    return aclString;
  }
}


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\UserManager.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */