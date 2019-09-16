package WEB-INF.classes.com.universal.milestone;

import com.techempower.gemini.GeminiApplication;
import com.techempower.gemini.InfrastructureJSP;
import com.universal.milestone.MilestoneApplication;

public abstract class MilestoneJSP extends InfrastructureJSP {
  public String getServletInfo() { return "Milestone JSP"; }
  
  public GeminiApplication getApplication() { return MilestoneApplication.getInstance(); }
}


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\MilestoneJSP.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */