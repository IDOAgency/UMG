package WEB-INF.classes.com.universal.milestone;

import com.techempower.ComponentLog;
import com.techempower.EnhancedProperties;
import com.techempower.gemini.Context;
import com.techempower.gemini.GeminiApplication;
import com.universal.milestone.JdbcConnector;
import com.universal.milestone.MilestoneConstants;
import com.universal.milestone.MilestoneHelper;
import com.universal.milestone.User;
import com.universal.milestone.UserPreferences;
import com.universal.milestone.UserPreferencesManager;

public class UserPreferencesManager implements MilestoneConstants {
  public static final String COMPONENT_CODE = "mUsr";
  
  protected static UserPreferencesManager userManager = null;
  
  protected static ComponentLog log;
  
  public static void configure(EnhancedProperties props, GeminiApplication application) { log = application.getLog("mUsr"); }
  
  public static UserPreferencesManager getInstance() {
    if (userManager == null)
      userManager = new UserPreferencesManager(); 
    return userManager;
  }
  
  public UserPreferences getUserPreferences(int userID) {
    String userQuery = "sp_get_User_preferences " + userID;
    UserPreferences up = new UserPreferences();
    JdbcConnector connector = MilestoneHelper.getConnector(userQuery);
    connector.runQuery();
    if (connector.more()) {
      up.setOpeningScreen(connector.getIntegerField("openingScreen"));
      up.setAutoClose(connector.getIntegerField("autoClose"));
      up.setAutoCloseDays(connector.getIntegerField("autoCloseDays"));
      up.setNotepadSortBy(connector.getIntegerField("generalSortBy"));
      up.setNotepadOrder(connector.getIntegerField("generalOrder"));
      up.setNotepadProductType(connector.getIntegerField("generalProductType"));
      up.setSelectionReleasingFamily(connector.getIntegerField("selectionReleasingFamily"));
      up.setSelectionEnvironment(connector.getIntegerField("selectionEnvironment"));
      up.setSelectionLabelContact(connector.getIntegerField("selectionLabelContact"));
      up.setSelectionProductType(connector.getIntegerField("selectionProductType"));
      up.setSelectionStatus(connector.getIntegerField("selectionStatus"));
      up.setSelectionPriorCriteria(connector.getIntegerField("selectionPriorCriteria"));
      up.setSchedulePhysicalSortBy(connector.getIntegerField("physicalScheduleSortBy"));
      up.setSchedulePhysicalOwner(connector.getIntegerField("physicalScheduleOwner"));
      up.setScheduleDigitalSortBy(connector.getIntegerField("digitalScheduleSortBy"));
      up.setScheduleDigitalOwner(connector.getIntegerField("digitalScheduleOwner"));
      up.setReportsReleaseType(connector.getIntegerField("reportsReleaseType"));
      up.setReportsReleasingFamily(connector.getIntegerField("reportsReleasingFamily"));
      up.setReportsEnvironment(connector.getIntegerField("reportsEnvironment"));
      up.setReportsLabelContact(connector.getIntegerField("reportsLabelContact"));
      up.setReportsUMLContact(connector.getIntegerField("reportsUMLContact"));
      up.setReportsStatusAll(connector.getIntegerField("reportStatusAll"));
      up.setReportsStatusActive(connector.getIntegerField("reportStatusActive"));
      up.setReportsStatusTBS(connector.getIntegerField("reportStatusTBS"));
      up.setReportsStatusClosed(connector.getIntegerField("reportStatusClosed"));
      up.setReportsStatusCancelled(connector.getIntegerField("reportStatusCancelled"));
      up.setActive(connector.getBoolean("active"));
    } 
    connector.close();
    return up;
  }
  
  public void savePreferences(User user, Context context) {
    String query = "sp_sav_UserPreferences " + 
      user.getUserId() + 
      "," + user.getPreferences().getOpeningScreen() + 
      "," + user.getPreferences().getAutoClose() + 
      "," + user.getPreferences().getAutoCloseDays() + 
      "," + user.getPreferences().getNotepadSortBy() + 
      "," + user.getPreferences().getNotepadOrder() + 
      "," + user.getPreferences().getNotepadProductType() + 
      "," + user.getPreferences().getSelectionReleasingFamily() + 
      "," + user.getPreferences().getSelectionEnvironment() + 
      "," + user.getPreferences().getSelectionLabelContact() + 
      "," + user.getPreferences().getSelectionProductType() + 
      "," + user.getPreferences().getSelectionStatus() + 
      "," + user.getPreferences().getSelectionPriorCriteria() + 
      "," + user.getPreferences().getSchedulePhysicalSortBy() + 
      "," + user.getPreferences().getSchedulePhysicalOwner() + 
      "," + user.getPreferences().getScheduleDigitalSortBy() + 
      "," + user.getPreferences().getScheduleDigitalOwner() + 
      "," + user.getPreferences().getReportsReleaseType() + 
      "," + user.getPreferences().getReportsReleasingFamily() + 
      "," + user.getPreferences().getReportsEnvironment() + 
      "," + user.getPreferences().getReportsLabelContact() + 
      "," + user.getPreferences().getReportsUMLContact() + 
      "," + user.getPreferences().getReportsStatusAll() + 
      "," + user.getPreferences().getReportsStatusActive() + 
      "," + user.getPreferences().getReportsStatusTBS() + 
      "," + user.getPreferences().getReportsStatusClosed() + 
      "," + user.getPreferences().getReportsStatusCancelled();
    JdbcConnector connector = MilestoneHelper.getConnector(query);
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
}


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\UserPreferencesManager.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */