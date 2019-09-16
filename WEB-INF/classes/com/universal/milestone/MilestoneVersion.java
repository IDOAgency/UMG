package WEB-INF.classes.com.universal.milestone;

import com.techempower.Version;
import com.universal.milestone.MilestoneVersion;

public class MilestoneVersion extends Version {
  protected static MilestoneVersion instance = new MilestoneVersion();
  
  public int getMajorVersion() { return 2; }
  
  public int getMinorVersion() { return 1; }
  
  public int getMicroVersion() { return 2; }
  
  public String getProductCode() { return "MS"; }
  
  public String getProductName() { return "Milestone"; }
  
  public String getClientName() { return "Universal"; }
  
  public String getDeveloperName() { return "TechEmpower, Inc."; }
  
  public String getCopyrightYears() { return "2000"; }
  
  public static Version getInstance() { return instance; }
}


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\MilestoneVersion.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */