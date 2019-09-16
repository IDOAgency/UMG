package WEB-INF.classes.com.universal.milestone;

import com.techempower.gemini.Context;
import com.techempower.gemini.GeminiApplication;
import com.universal.milestone.Milestone3Application;
import com.universal.milestone.Milestone3Servlet;
import com.universal.milestone.MilestoneServlet;

public class Milestone3Servlet extends MilestoneServlet {
  public GeminiApplication getApplication() { return Milestone3Application.getInstance(); }
  
  public void handleRequest(Context context, boolean isPost) { this.application.getDispatcher().dispatch(context); }
}


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\Milestone3Servlet.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */