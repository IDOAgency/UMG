package WEB-INF.classes.com.universal.milestone;

import com.universal.milestone.InkChange;

public class InkChange {
  protected String id = "";
  
  protected String name = "";
  
  protected String startingValueLeft = "";
  
  protected String endValueLeft = "";
  
  protected String startingValueRight = "";
  
  protected String endValueRight = "";
  
  protected boolean isRightSet = false;
  
  protected boolean isLeftSet = false;
  
  public void setName(String name) { this.name = name; }
  
  public String getName() { return this.name; }
  
  public void setIsRightSet(boolean isRightSet) { this.isRightSet = this.isRightSet; }
  
  public boolean getIsRightSet() { return this.isRightSet; }
  
  public void setIsLeftSet(boolean isLeftSet) { this.isLeftSet = this.isLeftSet; }
  
  public boolean getIsLeftSet() { return this.isLeftSet; }
  
  public void setId(String id) { this.id = id; }
  
  public String getId() { return this.id; }
  
  public void setStartingValueLeft(String startingValueLeft) { this.startingValueLeft = startingValueLeft; }
  
  public String getStartingValueLeft() { return this.startingValueLeft; }
  
  public void setEndValueLeft(String endValueLeft) { this.endValueLeft = endValueLeft; }
  
  public String getEndValueLeft() { return this.endValueLeft; }
  
  public void setStartingValueRight(String startingValueRight) { this.startingValueRight = startingValueRight; }
  
  public String getStartingValueRight() { return this.startingValueRight; }
  
  public void setEndValueRight(String endValueRight) { this.endValueRight = endValueRight; }
  
  public String getEndValueRight() { return this.endValueRight; }
}


/* Location:              D:\Documents\NetBeansProjects\milestone2 Local.war!\WEB-INF\classes\co\\universal\milestone\InkChange.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */