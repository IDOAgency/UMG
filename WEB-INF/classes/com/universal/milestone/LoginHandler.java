package WEB-INF.classes.com.universal.milestone;

import com.techempower.ComponentLog;
import com.techempower.gemini.Context;
import com.techempower.gemini.Dispatcher;
import com.techempower.gemini.FormHidden;
import com.techempower.gemini.FormPasswordField;
import com.techempower.gemini.FormTextField;
import com.techempower.gemini.FormValidation;
import com.techempower.gemini.GeminiApplication;
import com.techempower.gemini.Handler;
import com.universal.milestone.Form;
import com.universal.milestone.JdbcConnector;
import com.universal.milestone.LoginHandler;
import com.universal.milestone.MilestoneConstants;
import com.universal.milestone.MilestoneHelper;
import com.universal.milestone.MilestoneSecurity;
import com.universal.milestone.SessionUser;
import com.universal.milestone.User;
import com.universal.milestone.UserManager;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;
import javax.servlet.http.HttpSession;

public class LoginHandler implements Handler, MilestoneConstants {
  public static final String COMPONENT_CODE = "hLog";
  
  public GeminiApplication application;
  
  public ComponentLog log;
  
  public LoginHandler(GeminiApplication application) {
    this.application = application;
    this.log = application.getLog("hLog");
  }
  
  public String getDescription() { return "Login"; }
  
  public boolean acceptRequest(Dispatcher dispatcher, Context context, String command) {
    if (command.equalsIgnoreCase("login"))
      return handleRequest(dispatcher, context, command); 
    if (command.equalsIgnoreCase("logoff")) {
      UserManager.getInstance().logout(context);
      return handleRequest(dispatcher, context, command);
    } 
    return false;
  }
  
  public boolean handleRequest(Dispatcher dispatcher, Context context, String command) {
    boolean isSessionActive = false;
    if (!isSessionActive)
      if (MilestoneSecurity.getUser(context) == null) {
        UserManager.getInstance().logout(context);
      } else {
        UserManager.getInstance().clearSession(context);
      }  
    String environmentName = "Production";
    String environmentColor = "FFFFFF";
    try {
      String configFile = "milestone.conf";
      InputStream in = ClassLoader.getSystemResourceAsStream(configFile);
      if (in == null)
        in = new FileInputStream(configFile); 
      Properties defaultProps = new Properties();
      defaultProps.load(in);
      if (defaultProps.getProperty("ENVIRONMENT_NAME") != null)
        environmentName = defaultProps.getProperty("ENVIRONMENT_NAME"); 
      if (defaultProps.getProperty("ENVIRONMENT_COLOR") != null)
        environmentColor = defaultProps.getProperty("ENVIRONMENT_COLOR"); 
      in.close();
    } catch (Exception e) {
      System.out.println("Error loading ENVIRONMENT_NAME and/or ENVIRONMENT_COLOR from milestone.conf");
    } 
    context.putSessionValue("environment_name", environmentName);
    context.putSessionValue("environment_color", environmentColor);
    Form form = new Form(this.application, "LoginForm", 
        this.application.getInfrastructure().getServletURL(), "POST");
    form.addElement(new FormHidden("cmd", "login"));
    FormTextField username1 = new FormTextField("username", "", true, 12);
    username1.addFormEvent("onkeypress", "checkInput(window.event.keyCode);");
    username1.setTabIndex(1);
    form.addElement(username1);
    FormPasswordField password1 = new FormPasswordField("password", "", true, 12);
    password1.addFormEvent("onkeypress", "checkInput(window.event.keyCode);");
    password1.setTabIndex(2);
    form.addElement(password1);
    form.setValues(context);
    context.putDelivery("Form", form);
    if (!form.isUnchanged()) {
      FormValidation formValidation = form.validate();
      context.putDelivery("FormValidation", formValidation);
      if (formValidation.isGood()) {
        String username = form.getStringValue("username");
        String password = form.getStringValue("password");
        User user = null;
        if (!isSessionActive)
          user = MilestoneSecurity.processLogin(username, password, context); 
        if (user != null && !isSessionActive) {
          this.log.debug(">>>>>>>>>>>>>>>>>>>>login");
          HttpSession sess = context.getSession();
          sess.setAttribute("UserSession", new SessionUser(user.getName(), sess.getId(), 
                user.getUserId(), context.getClientIP(), JdbcConnector.PROVIDER_URL));
          return context.includeJSP("main-milestone-frame.jsp");
        } 
        if (isSessionActive) {
          context.putDelivery("Message", "An active Milestone session already exists! \\n\\nIf you want to have multiple open sessions, close this window and open Milestone in a new browser instance.");
          context.putDelivery("CloseWindow", "true");
          password1.setValue("");
        } else {
          context.putDelivery("Message", "Invalid login.  Please try again.");
          password1.setValue("");
        } 
      } 
    } 
    return context.includeJSP("login.jsp");
  }
  
  public boolean isSessionActive(String sessionId) {
    boolean retBool = true;
    String query = "sp_get_IsActiveSession " + sessionId.substring(0, 52);
    JdbcConnector connector = MilestoneHelper.getConnector(query);
    connector.runQuery();
    if (connector.more())
      retBool = connector.getBoolean("activeSession"); 
    System.out.println("<<< isSessionActive " + 
        sessionId.substring(0, 52) + " = " + retBool);
    connector.close();
    return retBool;
  }
}


/* Location:              D:\Documents\NetBeansProjects\milestone2 Local.war!\WEB-INF\classes\co\\universal\milestone\LoginHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */