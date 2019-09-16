package WEB-INF.classes.com.universal.milestone;

import com.techempower.gemini.GeminiApplication;
import com.universal.milestone.MilestoneJSP;
import com.universal.milestone.PressPlayApplication;

public abstract class PressPlayJSP extends MilestoneJSP {
  public String getServletInfo() { return "PressPlay JSP"; }
  
  public GeminiApplication getApplication() { return PressPlayApplication.getInstance(); }
}


/* Location:              D:\Documents\NetBeansProjects\milestone2 Local.war!\WEB-INF\classes\co\\universal\milestone\PressPlayJSP.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */