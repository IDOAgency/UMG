package WEB-INF.classes.com.universal.milestone;

import com.techempower.gemini.Context;
import com.techempower.gemini.Dispatcher;
import com.techempower.gemini.Handler;
import com.universal.milestone.MilestoneConstants;
import com.universal.milestone.MilestoneSecurity;

public class SecureHandler implements Handler, MilestoneConstants {
  public String getDescription() { return "Provides user authentication check before accepting"; }
  
  public boolean acceptRequest(Dispatcher dispatcher, Context context, String command) { return MilestoneSecurity.isUserLoggedIn(context, command); }
  
  public boolean handleRequest(Dispatcher dispatcher, Context context, String command) { return true; }
}


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\SecureHandler.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */