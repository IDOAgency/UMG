package WEB-INF.classes.com.universal.milestone;

import com.techempower.EnhancedProperties;
import com.techempower.Version;
import com.techempower.gemini.BasicInfrastructure;
import com.techempower.gemini.GeminiApplication;
import com.universal.milestone.MilestoneInfrastructure;

public class MilestoneInfrastructure extends BasicInfrastructure {
  public MilestoneInfrastructure(GeminiApplication application) { super(application); }
  
  public void customConfigure(EnhancedProperties props, Version version) { this.log.debug("Milestone Infrastructure configured."); }
}


/* Location:              D:\Documents\NetBeansProjects\milestone2 Local.war!\WEB-INF\classes\co\\universal\milestone\MilestoneInfrastructure.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */