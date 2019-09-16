package WEB-INF.classes.com.universal.milestone;

import com.universal.milestone.Company;
import com.universal.milestone.CorporateStructureObject;
import com.universal.milestone.Division;
import java.util.Vector;

public class Division extends CorporateStructureObject implements Cloneable {
  public Company parentCompany;
  
  public Vector labels = new Vector();
  
  protected boolean activeFlag;
  
  public Company getParentCompany() { return this.parentCompany; }
  
  public void setParentCompany(Company company) { this.parentCompany = company; }
  
  public Vector getLabels() { return this.labels; }
  
  public void setLabels(Vector labels) { this.labels = labels; }
  
  public Vector getChildren() { return getLabels(); }
  
  public void setActive(boolean active) { this.activeFlag = active; }
  
  public boolean getActive() { return this.activeFlag; }
  
  public void addChild(CorporateStructureObject cso) {
    if (cso instanceof com.universal.milestone.Label)
      this.labels.addElement(cso); 
  }
  
  public CorporateStructureObject getParent() { return getParentCompany(); }
  
  public void setParent(CorporateStructureObject cso) { setParentCompany((Company)cso); }
  
  public Object clone() throws CloneNotSupportedException { return super.clone(); }
}


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\Division.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */