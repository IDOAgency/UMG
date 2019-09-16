package WEB-INF.classes.com.universal.milestone;

import com.techempower.DatabaseConnector;
import com.universal.milestone.Company;
import com.universal.milestone.CorporateStructureObject;
import com.universal.milestone.Environment;
import com.universal.milestone.Family;
import java.util.Vector;

public class Company extends CorporateStructureObject implements Cloneable {
  public int distribution = 0;
  
  protected Vector divisions = new Vector();
  
  protected int calendarGroup = 0;
  
  protected Family parentFamily = null;
  
  protected boolean activeFlag;
  
  protected Environment parentEnvironment = null;
  
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
  
  public Vector getDivisions() { return this.divisions; }
  
  public void setDivisions(Vector divisions) { this.divisions = divisions; }
  
  public Vector getChildren() { return getDivisions(); }
  
  public void addChild(CorporateStructureObject cso) {
    if (cso instanceof com.universal.milestone.Division)
      this.divisions.addElement(cso); 
  }
  
  public CorporateStructureObject getParent() { return getParentEnvironment(); }
  
  public void setParent(CorporateStructureObject cso) { setParentEnvironment((Environment)cso); }
  
  public Environment getParentEnvironment() { return this.parentEnvironment; }
  
  public void setParentEnvironment(Environment environment) { this.parentEnvironment = environment; }
  
  public Object clone() throws CloneNotSupportedException { return super.clone(); }
}


/* Location:              D:\Documents\NetBeansProjects\milestone2 Local.war!\WEB-INF\classes\co\\universal\milestone\Company.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */