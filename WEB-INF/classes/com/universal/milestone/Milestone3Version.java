package WEB-INF.classes.com.universal.milestone;

import com.techempower.Version;
import com.universal.milestone.Milestone3Version;
import com.universal.milestone.MilestoneVersion;

public class Milestone3Version extends MilestoneVersion {
  protected static Milestone3Version instance = new Milestone3Version();
  
  public int getMajorVersion() { return 2; }
  
  public int getMinorVersion() { return 1; }
  
  public int getMicroVersion() { return 2; }
  
  public String getProductCode() { return "MS"; }
  
  public String getProductName() { return "Milestone3"; }
  
  public String getClientName() { return "Universal"; }
  
  public String getDeveloperName() { return "TechEmpower, Inc."; }
  
  public String getCopyrightYears() { return "2000"; }
  
  public static Version getInstance() { return instance; }
}


/* Location:              D:\Documents\NetBeansProjects\milestone2 Local.war!\WEB-INF\classes\co\\universal\milestone\Milestone3Version.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */