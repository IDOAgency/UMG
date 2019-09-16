package WEB-INF.classes.com.universal.milestone;

import com.techempower.gemini.Context;
import com.techempower.gemini.GeminiApplication;
import com.universal.milestone.MilestoneServlet;
import com.universal.milestone.PressPlayApplication;
import com.universal.milestone.PressPlayServlet;

public class PressPlayServlet extends MilestoneServlet {
  public GeminiApplication getApplication() { return PressPlayApplication.getInstance(); }
  
  public void handleRequest(Context context, boolean isPost) { this.application.getDispatcher().dispatch(context); }
}


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\PressPlayServlet.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */