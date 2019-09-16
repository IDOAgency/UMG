package WEB-INF.classes.com.universal.milestone;

import com.techempower.gemini.Context;
import com.techempower.gemini.Dispatcher;
import com.techempower.gemini.GeminiApplication;
import com.universal.milestone.MilestoneApplication;
import com.universal.milestone.MilestoneContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MilestoneContext extends Context {
  public MilestoneContext(HttpServletRequest request, HttpServletResponse response, ServletContext servletContext, Dispatcher dispatcher) { super(request, response, servletContext, dispatcher); }
  
  public String getCustomRequestMark(String pageName) { return ""; }
  
  public GeminiApplication getApplication() { return MilestoneApplication.getInstance(); }
  
  public boolean includeJSP(String name) {
    this.response.setHeader("Cache-Control", "no-cache");
    return super.includeJSP(name);
  }
  
  public void putSessionValue(String object, Object value) {
    if (value != null)
      super.putSessionValue(object, value); 
  }
}


/* Location:              D:\Documents\NetBeansProjects\milestone2 Local.war!\WEB-INF\classes\co\\universal\milestone\MilestoneContext.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */