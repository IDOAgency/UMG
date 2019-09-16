package WEB-INF.classes.com.universal.milestone;

import com.techempower.ComponentLog;
import com.techempower.gemini.Context;
import com.techempower.gemini.Dispatcher;
import com.techempower.gemini.GeminiApplication;
import com.universal.milestone.MainTopMenuHandler;
import com.universal.milestone.SecureHandler;

public class MainTopMenuHandler extends SecureHandler {
  public static final String COMPONENT_CODE = "hMTM";
  
  public GeminiApplication application;
  
  public ComponentLog log;
  
  public MainTopMenuHandler(GeminiApplication application) {
    this.application = application;
    this.log = application.getLog("hMTM");
  }
  
  public String getDescription() { return "MainTopMenu"; }
  
  public boolean acceptRequest(Dispatcher dispatcher, Context context, String command) {
    if (super.acceptRequest(dispatcher, context, command))
      if (command.equalsIgnoreCase("main-top-menu"))
        return handleRequest(dispatcher, context, command);  
    return false;
  }
  
  public boolean handleRequest(Dispatcher dispatcher, Context context, String command) { return context.includeJSP("main-top-menu.jsp"); }
}


/* Location:              D:\Documents\NetBeansProjects\milestone2 Local.war!\WEB-INF\classes\co\\universal\milestone\MainTopMenuHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */