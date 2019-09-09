/*     */ package WEB-INF.classes.com.universal.milestone;
/*     */ 
/*     */ import com.techempower.ComponentLog;
/*     */ import com.techempower.gemini.Context;
/*     */ import com.techempower.gemini.Dispatcher;
/*     */ import com.techempower.gemini.FormHidden;
/*     */ import com.techempower.gemini.FormPasswordField;
/*     */ import com.techempower.gemini.FormTextField;
/*     */ import com.techempower.gemini.FormValidation;
/*     */ import com.techempower.gemini.GeminiApplication;
/*     */ import com.techempower.gemini.Handler;
/*     */ import com.universal.milestone.Form;
/*     */ import com.universal.milestone.JdbcConnector;
/*     */ import com.universal.milestone.LoginHandler;
/*     */ import com.universal.milestone.MilestoneConstants;
/*     */ import com.universal.milestone.MilestoneHelper;
/*     */ import com.universal.milestone.MilestoneSecurity;
/*     */ import com.universal.milestone.SessionUser;
/*     */ import com.universal.milestone.User;
/*     */ import com.universal.milestone.UserManager;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.InputStream;
/*     */ import java.util.Properties;
/*     */ import javax.servlet.http.HttpSession;
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
/*     */ public class LoginHandler
/*     */   implements Handler, MilestoneConstants
/*     */ {
/*     */   public static final String COMPONENT_CODE = "hLog";
/*     */   public GeminiApplication application;
/*     */   public ComponentLog log;
/*     */   
/*     */   public LoginHandler(GeminiApplication application) {
/*  68 */     this.application = application;
/*  69 */     this.log = application.getLog("hLog");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  77 */   public String getDescription() { return "Login"; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean acceptRequest(Dispatcher dispatcher, Context context, String command) {
/*  87 */     if (command.equalsIgnoreCase("login"))
/*     */     {
/*  89 */       return handleRequest(dispatcher, context, command);
/*     */     }
/*  91 */     if (command.equalsIgnoreCase("logoff")) {
/*     */       
/*  93 */       UserManager.getInstance().logout(context);
/*  94 */       return handleRequest(dispatcher, context, command);
/*     */     } 
/*     */     
/*  97 */     return false;
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
/*     */   
/*     */   public boolean handleRequest(Dispatcher dispatcher, Context context, String command) {
/* 113 */     boolean isSessionActive = false;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 121 */     if (!isSessionActive)
/*     */     {
/*     */       
/* 124 */       if (MilestoneSecurity.getUser(context) == null) {
/* 125 */         UserManager.getInstance().logout(context);
/*     */       } else {
/* 127 */         UserManager.getInstance().clearSession(context);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 133 */     String environmentName = "Production";
/* 134 */     String environmentColor = "FFFFFF";
/*     */ 
/*     */     
/*     */     try {
/* 138 */       String configFile = "milestone.conf";
/* 139 */       InputStream in = ClassLoader.getSystemResourceAsStream(configFile);
/*     */ 
/*     */       
/* 142 */       if (in == null) in = new FileInputStream(configFile); 
/* 143 */       Properties defaultProps = new Properties();
/* 144 */       defaultProps.load(in);
/*     */ 
/*     */       
/* 147 */       if (defaultProps.getProperty("ENVIRONMENT_NAME") != null) {
/* 148 */         environmentName = defaultProps.getProperty("ENVIRONMENT_NAME");
/*     */       }
/* 150 */       if (defaultProps.getProperty("ENVIRONMENT_COLOR") != null) {
/* 151 */         environmentColor = defaultProps.getProperty("ENVIRONMENT_COLOR");
/*     */       }
/* 153 */       in.close();
/* 154 */     } catch (Exception e) {
/* 155 */       System.out.println("Error loading ENVIRONMENT_NAME and/or ENVIRONMENT_COLOR from milestone.conf");
/*     */     } 
/* 157 */     context.putSessionValue("environment_name", environmentName);
/* 158 */     context.putSessionValue("environment_color", environmentColor);
/*     */ 
/*     */     
/* 161 */     Form form = new Form(this.application, "LoginForm", 
/* 162 */         this.application.getInfrastructure().getServletURL(), "POST");
/* 163 */     form.addElement(new FormHidden("cmd", "login"));
/*     */ 
/*     */     
/* 166 */     FormTextField username1 = new FormTextField("username", "", true, 12);
/* 167 */     username1.addFormEvent("onkeypress", "checkInput(window.event.keyCode);");
/* 168 */     username1.setTabIndex(1);
/* 169 */     form.addElement(username1);
/*     */ 
/*     */     
/* 172 */     FormPasswordField password1 = new FormPasswordField("password", "", true, 12);
/* 173 */     password1.addFormEvent("onkeypress", "checkInput(window.event.keyCode);");
/* 174 */     password1.setTabIndex(2);
/* 175 */     form.addElement(password1);
/*     */     
/* 177 */     form.setValues(context);
/* 178 */     context.putDelivery("Form", form);
/*     */     
/* 180 */     if (!form.isUnchanged()) {
/*     */       
/* 182 */       FormValidation formValidation = form.validate();
/* 183 */       context.putDelivery("FormValidation", formValidation);
/*     */       
/* 185 */       if (formValidation.isGood()) {
/*     */         
/* 187 */         String username = form.getStringValue("username");
/* 188 */         String password = form.getStringValue("password");
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 193 */         User user = null;
/* 194 */         if (!isSessionActive) {
/* 195 */           user = MilestoneSecurity.processLogin(username, password, context);
/*     */         }
/*     */         
/* 198 */         if (user != null && !isSessionActive) {
/*     */           
/* 200 */           this.log.debug(">>>>>>>>>>>>>>>>>>>>login");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 209 */           HttpSession sess = context.getSession();
/*     */           
/* 211 */           sess.setAttribute("UserSession", new SessionUser(user.getName(), sess.getId(), 
/* 212 */                 user.getUserId(), context.getClientIP(), JdbcConnector.PROVIDER_URL));
/*     */ 
/*     */           
/* 215 */           return context.includeJSP("main-milestone-frame.jsp");
/*     */         } 
/*     */ 
/*     */         
/* 219 */         if (isSessionActive) {
/*     */ 
/*     */           
/* 222 */           context.putDelivery("Message", "An active Milestone session already exists! \\n\\nIf you want to have multiple open sessions, close this window and open Milestone in a new browser instance.");
/*     */           
/* 224 */           context.putDelivery("CloseWindow", "true");
/* 225 */           password1.setValue("");
/*     */         }
/*     */         else {
/*     */           
/* 229 */           context.putDelivery("Message", "Invalid login.  Please try again.");
/* 230 */           password1.setValue("");
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 235 */     return context.includeJSP("login.jsp");
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
/*     */   public boolean isSessionActive(String sessionId) {
/* 248 */     boolean retBool = true;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 254 */     String query = "sp_get_IsActiveSession " + sessionId.substring(0, 52);
/*     */     
/* 256 */     JdbcConnector connector = MilestoneHelper.getConnector(query);
/* 257 */     connector.runQuery();
/* 258 */     if (connector.more()) {
/* 259 */       retBool = connector.getBoolean("activeSession");
/*     */     }
/* 261 */     System.out.println("<<< isSessionActive " + 
/* 262 */         sessionId.substring(0, 52) + " = " + retBool);
/*     */ 
/*     */     
/* 265 */     connector.close();
/*     */     
/* 267 */     return retBool;
/*     */   }
/*     */ }


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\LoginHandler.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */