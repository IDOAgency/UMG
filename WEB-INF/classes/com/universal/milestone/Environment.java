package WEB-INF.classes.com.universal.milestone;

import com.techempower.DatabaseConnector;
import com.universal.milestone.CorporateStructureObject;
import com.universal.milestone.Environment;
import com.universal.milestone.Family;
import java.util.Vector;

public class Environment extends CorporateStructureObject implements Cloneable {
  public int distribution = 0;
  
  protected Vector companies = new Vector();
  
  protected int calendarGroup = 0;
  
  protected Family parentFamily = null;
  
  protected boolean activeFlag;
  
  public void customInitialization(DatabaseConnector connector) {
    super.customInitialization(connector);
    this.distribution = connector.getInt("region");
    this.calendarGroup = connector.getInt("grouping");
  }
  
  public int getDistribution() { return this.distribution; }
  
  public void setDistribution(int distribution) {
    auditCheck("distribution", this.distribution, distribution);
    this.distribution = distribution;
  }
  
  public int getCalendarGroup() { return this.calendarGroup; }
  
  public void getCalendarGroup(int calendarGroup) { this.calendarGroup = calendarGroup; }
  
  public Family getParentFamily() { return this.parentFamily; }
  
  public boolean getActive() { return this.activeFlag; }
  
  public void setParentFamily(Family family) { this.parentFamily = family; }
  
  public void setActive(boolean active) { this.activeFlag = active; }
  
  public Vector getCompanies() { return this.companies; }
  
  public void setCompanies(Vector companies) { this.companies = companies; }
  
  public Vector getChildren() { return getCompanies(); }
  
  public void addChild(CorporateStructureObject cso) {
    if (cso instanceof com.universal.milestone.Company)
      this.companies.addElement(cso); 
  }
  
  public CorporateStructureObject getParent() { return getParentFamily(); }
  
  public void setParent(CorporateStructureObject cso) { setParentFamily((Family)cso); }
  
  public Object clone() throws CloneNotSupportedException { return super.clone(); }
}


/* Location:              D:\Documents\NetBeansProjects\milestone2 Local.war!\WEB-INF\classes\co\\universal\milestone\Environment.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */