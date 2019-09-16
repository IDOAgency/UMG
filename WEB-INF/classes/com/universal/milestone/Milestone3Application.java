package WEB-INF.classes.com.universal.milestone;

import com.techempower.Version;
import com.techempower.gemini.BasicInfrastructure;
import com.techempower.gemini.Configurator;
import com.techempower.gemini.Context;
import com.techempower.gemini.Dispatcher;
import com.techempower.gemini.EmailTemplater;
import com.techempower.gemini.GeminiApplication;
import com.universal.milestone.Milestone3Application;
import com.universal.milestone.Milestone3Context;
import com.universal.milestone.Milestone3Version;
import com.universal.milestone.MilestoneApplication;
import com.universal.milestone.MilestoneConfigurator;
import com.universal.milestone.MilestoneDispatcher;
import com.universal.milestone.MilestoneInfrastructure;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Milestone3Application extends MilestoneApplication {
  protected static Milestone3Application instance = new Milestone3Application();
  
  protected Version constructVersion() { return Milestone3Version.getInstance(); }
  
  protected BasicInfrastructure constructInfrastructure() { return new MilestoneInfrastructure(this); }
  
  protected Configurator constructConfigurator() { return new MilestoneConfigurator(this); }
  
  protected Dispatcher constructDispatcher() { return new MilestoneDispatcher(this); }
  
  protected EmailTemplater constructEmailTemplater() { return null; }
  
  public Context getContext(HttpServletRequest request, HttpServletResponse response, ServletContext servletContext) { return new Milestone3Context(request, response, servletContext, this.dispatcher); }
  
  public static GeminiApplication getInstance() { return instance; }
}


/* Location:              D:\Documents\NetBeansProjects\milestone2 Local.war!\WEB-INF\classes\co\\universal\milestone\Milestone3Application.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */