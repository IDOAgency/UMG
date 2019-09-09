/*     */ package WEB-INF.classes.com.universal.milestone;
/*     */ 
/*     */ import com.techempower.ComponentLog;
/*     */ import com.techempower.EnhancedProperties;
/*     */ import com.techempower.gemini.Context;
/*     */ import com.techempower.gemini.GeminiApplication;
/*     */ import com.universal.milestone.JdbcConnector;
/*     */ import com.universal.milestone.MilestoneConstants;
/*     */ import com.universal.milestone.MilestoneHelper;
/*     */ import com.universal.milestone.User;
/*     */ import com.universal.milestone.UserPreferences;
/*     */ import com.universal.milestone.UserPreferencesManager;
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
/*     */ public class UserPreferencesManager
/*     */   implements MilestoneConstants
/*     */ {
/*     */   public static final String COMPONENT_CODE = "mUsr";
/*  39 */   protected static UserPreferencesManager userManager = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static ComponentLog log;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  51 */   public static void configure(EnhancedProperties props, GeminiApplication application) { log = application.getLog("mUsr"); }
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
/*     */   public static UserPreferencesManager getInstance() {
/*  67 */     if (userManager == null)
/*     */     {
/*  69 */       userManager = new UserPreferencesManager();
/*     */     }
/*  71 */     return userManager;
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
/*     */ 
/*     */   
/*     */   public UserPreferences getUserPreferences(int userID) {
/*  86 */     String userQuery = "sp_get_User_preferences " + userID;
/*  87 */     UserPreferences up = new UserPreferences();
/*     */     
/*  89 */     JdbcConnector connector = MilestoneHelper.getConnector(userQuery);
/*  90 */     connector.runQuery();
/*     */     
/*  92 */     if (connector.more()) {
/*     */       
/*  94 */       up.setOpeningScreen(connector.getIntegerField("openingScreen"));
/*  95 */       up.setAutoClose(connector.getIntegerField("autoClose"));
/*  96 */       up.setAutoCloseDays(connector.getIntegerField("autoCloseDays"));
/*     */       
/*  98 */       up.setNotepadSortBy(connector.getIntegerField("generalSortBy"));
/*  99 */       up.setNotepadOrder(connector.getIntegerField("generalOrder"));
/* 100 */       up.setNotepadProductType(connector.getIntegerField("generalProductType"));
/*     */       
/* 102 */       up.setSelectionReleasingFamily(connector.getIntegerField("selectionReleasingFamily"));
/* 103 */       up.setSelectionEnvironment(connector.getIntegerField("selectionEnvironment"));
/* 104 */       up.setSelectionLabelContact(connector.getIntegerField("selectionLabelContact"));
/* 105 */       up.setSelectionProductType(connector.getIntegerField("selectionProductType"));
/* 106 */       up.setSelectionStatus(connector.getIntegerField("selectionStatus"));
/* 107 */       up.setSelectionPriorCriteria(connector.getIntegerField("selectionPriorCriteria"));
/*     */       
/* 109 */       up.setSchedulePhysicalSortBy(connector.getIntegerField("physicalScheduleSortBy"));
/* 110 */       up.setSchedulePhysicalOwner(connector.getIntegerField("physicalScheduleOwner"));
/*     */       
/* 112 */       up.setScheduleDigitalSortBy(connector.getIntegerField("digitalScheduleSortBy"));
/* 113 */       up.setScheduleDigitalOwner(connector.getIntegerField("digitalScheduleOwner"));
/*     */       
/* 115 */       up.setReportsReleaseType(connector.getIntegerField("reportsReleaseType"));
/* 116 */       up.setReportsReleasingFamily(connector.getIntegerField("reportsReleasingFamily"));
/* 117 */       up.setReportsEnvironment(connector.getIntegerField("reportsEnvironment"));
/* 118 */       up.setReportsLabelContact(connector.getIntegerField("reportsLabelContact"));
/* 119 */       up.setReportsUMLContact(connector.getIntegerField("reportsUMLContact"));
/* 120 */       up.setReportsStatusAll(connector.getIntegerField("reportStatusAll"));
/* 121 */       up.setReportsStatusActive(connector.getIntegerField("reportStatusActive"));
/* 122 */       up.setReportsStatusTBS(connector.getIntegerField("reportStatusTBS"));
/* 123 */       up.setReportsStatusClosed(connector.getIntegerField("reportStatusClosed"));
/* 124 */       up.setReportsStatusCancelled(connector.getIntegerField("reportStatusCancelled"));
/* 125 */       up.setActive(connector.getBoolean("active"));
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 132 */     connector.close();
/* 133 */     return up;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void savePreferences(User user, Context context) {
/* 142 */     String query = "sp_sav_UserPreferences " + 
/* 143 */       user.getUserId() + 
/* 144 */       "," + user.getPreferences().getOpeningScreen() + 
/* 145 */       "," + user.getPreferences().getAutoClose() + 
/* 146 */       "," + user.getPreferences().getAutoCloseDays() + 
/* 147 */       "," + user.getPreferences().getNotepadSortBy() + 
/* 148 */       "," + user.getPreferences().getNotepadOrder() + 
/* 149 */       "," + user.getPreferences().getNotepadProductType() + 
/* 150 */       "," + user.getPreferences().getSelectionReleasingFamily() + 
/* 151 */       "," + user.getPreferences().getSelectionEnvironment() + 
/* 152 */       "," + user.getPreferences().getSelectionLabelContact() + 
/* 153 */       "," + user.getPreferences().getSelectionProductType() + 
/* 154 */       "," + user.getPreferences().getSelectionStatus() + 
/* 155 */       "," + user.getPreferences().getSelectionPriorCriteria() + 
/* 156 */       "," + user.getPreferences().getSchedulePhysicalSortBy() + 
/* 157 */       "," + user.getPreferences().getSchedulePhysicalOwner() + 
/* 158 */       "," + user.getPreferences().getScheduleDigitalSortBy() + 
/* 159 */       "," + user.getPreferences().getScheduleDigitalOwner() + 
/* 160 */       "," + user.getPreferences().getReportsReleaseType() + 
/* 161 */       "," + user.getPreferences().getReportsReleasingFamily() + 
/* 162 */       "," + user.getPreferences().getReportsEnvironment() + 
/* 163 */       "," + user.getPreferences().getReportsLabelContact() + 
/* 164 */       "," + user.getPreferences().getReportsUMLContact() + 
/* 165 */       "," + user.getPreferences().getReportsStatusAll() + 
/* 166 */       "," + user.getPreferences().getReportsStatusActive() + 
/* 167 */       "," + user.getPreferences().getReportsStatusTBS() + 
/* 168 */       "," + user.getPreferences().getReportsStatusClosed() + 
/* 169 */       "," + user.getPreferences().getReportsStatusCancelled();
/*     */ 
/*     */ 
/*     */     
/* 173 */     JdbcConnector connector = MilestoneHelper.getConnector(query);
/* 174 */     connector.runQuery();
/* 175 */     connector.close();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void deleteUser(User user, User sessionUser) {
/* 185 */     String query = "sp_del_Users " + 
/* 186 */       user.getUserId();
/*     */ 
/*     */     
/* 189 */     JdbcConnector connector = MilestoneHelper.getConnector(query);
/* 190 */     connector.runQuery();
/* 191 */     connector.close();
/*     */   }
/*     */ }


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\UserPreferencesManager.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */