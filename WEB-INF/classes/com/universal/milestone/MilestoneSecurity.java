/*     */ package WEB-INF.classes.com.universal.milestone;
/*     */ 
/*     */ import com.techempower.ComponentLog;
/*     */ import com.techempower.EnhancedProperties;
/*     */ import com.techempower.gemini.Context;
/*     */ import com.techempower.gemini.GeminiApplication;
/*     */ import com.universal.milestone.MilestoneConstants;
/*     */ import com.universal.milestone.MilestoneHelper;
/*     */ import com.universal.milestone.MilestoneSecurity;
/*     */ import com.universal.milestone.User;
/*     */ import com.universal.milestone.UserCompaniesTableManager;
/*     */ import com.universal.milestone.UserManager;
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
/*     */ public class MilestoneSecurity
/*     */   implements MilestoneConstants
/*     */ {
/*     */   public static final String COMPONENT_CODE = "secu";
/*     */   public static final String deptFilterFormat = "department.filter.";
/*     */   protected static ComponentLog log;
/*     */   
/*  51 */   public static void configure(EnhancedProperties props, GeminiApplication application) { log = application.getLog("secu"); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static User processLogin(String username, String password, Context context) {
/*  62 */     User user = UserManager.getInstance().getUser(username, password);
/*  63 */     if (user != null) {
/*     */       
/*  65 */       context.putSessionValue("user", user);
/*  66 */       String[] filter = MilestoneHelper.parseFilter(user.getFilter());
/*  67 */       context.putSessionValue("filterFlag", filter[0]);
/*  68 */       context.putSessionValue("filter", filter[1]);
/*     */ 
/*     */       
/*  71 */       String deptFilter = user.getDeptFilter();
/*  72 */       context.putSessionValue("deptFilterFlag", "Yes");
/*     */       try {
/*  74 */         if (deptFilter.substring(0, 4).equalsIgnoreCase("true"))
/*  75 */         { context.putSessionValue("deptFilterFlag", "Yes"); }
/*     */         else
/*  77 */         { context.putSessionValue("deptFilterFlag", "No"); } 
/*  78 */       } catch (Exception exception) {}
/*     */ 
/*     */       
/*  81 */       String selDepartment = "All";
/*  82 */       if (deptFilter != null) {
/*  83 */         int sel = deptFilter.indexOf("department.filter.");
/*  84 */         if (sel != -1) {
/*     */           try {
/*  86 */             selDepartment = deptFilter.substring(sel + "department.filter.".length());
/*  87 */           } catch (Exception exception) {}
/*     */         }
/*     */       } 
/*  90 */       context.putSessionValue("deptFilter", selDepartment);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/*  95 */     return user;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 103 */   public static void processLogout(Context context) { context.removeAllSessionValues(); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 111 */   public static User getUser(Context context) { return (User)context.getSessionValue("user"); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isUserLoggedIn(Context context, String command) {
/* 119 */     if (context.getSessionValue("user") != null) {
/*     */       
/* 121 */       if (!command.equalsIgnoreCase("login") && 
/* 122 */         !command.equalsIgnoreCase("logoff") && 
/* 123 */         !command.equalsIgnoreCase("top-navigation") && 
/* 124 */         !command.equalsIgnoreCase("main-top-menu") && 
/* 125 */         !command.equalsIgnoreCase("home") && 
/* 126 */         command.length() > 0)
/*     */       {
/* 128 */         context.putSessionValue("lastLink", command);
/*     */       }
/*     */       
/* 131 */       User user = (User)context.getSessionValue("user");
/* 132 */       int userId = user.getUserId();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 139 */       if (UserCompaniesTableManager.getInstance().isUpdated(userId)) {
/*     */         
/* 141 */         Vector companies = (Vector)context.getSessionValue("user-companies");
/* 142 */         if (companies != null) {
/* 143 */           companies.removeAllElements();
/*     */         }
/*     */ 
/*     */ 
/*     */         
/* 148 */         user = UserManager.getInstance().getUser(userId, true);
/* 149 */         context.putSessionValue("user", user);
/*     */         
/* 151 */         UserCompaniesTableManager.getInstance().setUpdateFlag(userId, false);
/*     */       } 
/*     */     } 
/*     */     
/* 155 */     return (context.getSessionValue("user") != null);
/*     */   }
/*     */ }


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\MilestoneSecurity.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */