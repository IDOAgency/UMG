package WEB-INF.classes.com.universal.milestone;

import com.techempower.gemini.GeminiApplication;
import com.universal.milestone.Milestone3Application;
import com.universal.milestone.MilestoneJSP;

public abstract class Milestone3JSP extends MilestoneJSP {
  public String getServletInfo() { return "Milestone3 JSP"; }
  
  public GeminiApplication getApplication() { return Milestone3Application.getInstance(); }
}


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\Milestone3JSP.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */