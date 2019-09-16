package WEB-INF.classes.com.universal.milestone;

import com.techempower.DataEntity;
import com.universal.milestone.Family;
import com.universal.milestone.NotepadContentObject;
import com.universal.milestone.ProductCategory;
import com.universal.milestone.ReleaseType;
import com.universal.milestone.SelectionConfiguration;
import com.universal.milestone.SelectionSubConfiguration;
import com.universal.milestone.Template;
import com.universal.milestone.WeeksToRelease;
import java.util.Calendar;
import java.util.Vector;

public class Template extends DataEntity implements Cloneable, NotepadContentObject, Comparable {
  protected int templateID;
  
  protected ProductCategory productCategory;
  
  protected SelectionConfiguration selectionConfig;
  
  protected SelectionSubConfiguration selectionSubConfig;
  
  protected int lastUpdatingUser;
  
  protected ReleaseType releaseType;
  
  protected String comments;
  
  protected String templateName;
  
  protected WeeksToRelease weeksToRelease;
  
  protected Family owner;
  
  protected Calendar lastUpdateDate;
  
  protected long lastUpdatedCk;
  
  protected Vector tasks = null;
  
  protected int order = 0;
  
  protected int prodType;
  
  public int getTemplateID() { return this.templateID; }
  
  public String getTempateName() { return this.templateName; }
  
  public void setTemplateName(String templateName) { this.templateName = templateName; }
  
  public void setTemplateID(int templateID) { this.templateID = templateID; }
  
  public ProductCategory getProductCategory() { return this.productCategory; }
  
  public void setProductCategory(ProductCategory productCategory) { this.productCategory = productCategory; }
  
  public SelectionConfiguration getSelectionConfig() { return this.selectionConfig; }
  
  public void setSelectionConfig(SelectionConfiguration selectionConfig) { this.selectionConfig = selectionConfig; }
  
  public SelectionSubConfiguration getSelectionSubConfig() { return this.selectionSubConfig; }
  
  public void setSelectionSubConfig(SelectionSubConfiguration selectionSubConfig) { this.selectionSubConfig = selectionSubConfig; }
  
  public int getLastUpdatingUser() { return this.lastUpdatingUser; }
  
  public void setLastUpdatingUser(int lastUpdatingUser) { this.lastUpdatingUser = lastUpdatingUser; }
  
  public Calendar getLastUpdateDate() { return this.lastUpdateDate; }
  
  public void setLastUpdateDate(Calendar lastUpdateDate) { this.lastUpdateDate = lastUpdateDate; }
  
  public WeeksToRelease getWeeksToRelease() { return this.weeksToRelease; }
  
  public void setWeeksToRelease(WeeksToRelease weeksToRelease) { this.weeksToRelease = weeksToRelease; }
  
  public Family getOwner() { return this.owner; }
  
  public void setOwner(Family owner) { this.owner = owner; }
  
  public ReleaseType getReleaseType() { return this.releaseType; }
  
  public void setReleaseType(ReleaseType releaseType) { this.releaseType = releaseType; }
  
  public String getComments() { return this.comments; }
  
  public void setComments(String comments) { this.comments = comments; }
  
  public long getLastUpdatedCk() { return this.lastUpdatedCk; }
  
  public void setLastUpdatedCk(long lastUpdatedCk) { this.lastUpdatedCk = lastUpdatedCk; }
  
  public Vector getTasks() { return this.tasks; }
  
  public void setTasks(Vector tasks) { this.tasks = tasks; }
  
  public String getNotepadContentObjectId() { return Integer.toString(this.templateID); }
  
  public Object clone() throws CloneNotSupportedException { return super.clone(); }
  
  public int compareTo(Object template) throws ClassCastException { return compareByOrder(template); }
  
  protected int compareByOrder(Object template) throws ClassCastException {
    Integer thisTemplate = new Integer(this.order);
    Integer thatTemplate = new Integer(((Template)template).order);
    return thisTemplate.compareTo(thatTemplate);
  }
  
  public int getProdType() { return this.prodType; }
  
  public void setProdType(int prodType) { this.prodType = prodType; }
}


/* Location:              D:\Documents\NetBeansProjects\milestone2 Local.war!\WEB-INF\classes\co\\universal\milestone\Template.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */