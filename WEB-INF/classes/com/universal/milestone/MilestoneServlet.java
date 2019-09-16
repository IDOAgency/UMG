package WEB-INF.classes.com.universal.milestone;

import com.techempower.gemini.Context;
import com.techempower.gemini.GeminiApplication;
import com.techempower.gemini.InfrastructureServlet;
import com.universal.milestone.MilestoneApplication;
import com.universal.milestone.MilestoneServlet;

public class MilestoneServlet extends InfrastructureServlet {
  public GeminiApplication getApplication() { return MilestoneApplication.getInstance(); }
  
  public void handleRequest(Context context, boolean isPost) { this.application.getDispatcher().dispatch(context); }
}


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\MilestoneServlet.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */