package WEB-INF.classes.com.universal.milestone;

import com.universal.milestone.CorporateStructureObject;
import com.universal.milestone.Family;
import java.util.Vector;

public class Family extends CorporateStructureObject implements Cloneable {
  protected Vector companies = new Vector();
  
  protected boolean activeFlag;
  
  protected Vector environments = new Vector();
  
  public Vector getCompanies() { return this.companies; }
  
  public void setCompanies(Vector companies) { this.companies = companies; }
  
  public Vector getEnvironments() { return this.environments; }
  
  public void setEnvironment(Vector environments) { this.environments = environments; }
  
  public Vector getChildren() { return getEnvironments(); }
  
  public void addChild(CorporateStructureObject cso) {
    if (cso instanceof com.universal.milestone.Environment)
      this.environments.addElement(cso); 
  }
  
  public boolean getActive() { return this.activeFlag; }
  
  public void setActive(boolean active) { this.activeFlag = active; }
  
  public Object clone() throws CloneNotSupportedException { return super.clone(); }
}


/* Location:              D:\Documents\NetBeansProjects\milestone2 Local.war!\WEB-INF\classes\co\\universal\milestone\Family.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */