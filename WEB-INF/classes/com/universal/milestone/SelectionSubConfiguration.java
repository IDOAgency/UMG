package WEB-INF.classes.com.universal.milestone;

import com.techempower.DataEntity;
import com.universal.milestone.SelectionSubConfiguration;

public class SelectionSubConfiguration extends DataEntity {
  int selectionSubConfigurationID;
  
  boolean parent;
  
  String selectionSubConfigurationAbbreviation;
  
  String selectionSubConfigurationName;
  
  boolean inactive;
  
  int prodType;
  
  public SelectionSubConfiguration(String abbreviation, String name) {
    this.inactive = false;
    this.prodType = 2;
    this.selectionSubConfigurationAbbreviation = abbreviation;
    this.selectionSubConfigurationName = name;
    this.parent = false;
  }
  
  public SelectionSubConfiguration(String abbreviation, String name, int prodType) {
    this.inactive = false;
    this.prodType = 2;
    this.selectionSubConfigurationAbbreviation = abbreviation;
    this.selectionSubConfigurationName = name;
    this.prodType = prodType;
    this.parent = false;
  }
  
  public int getSelectionSubConfigurationID() { return this.selectionSubConfigurationID; }
  
  public void setSelectionSubConfigurationID(int selectionSubConfigurationID) { this.selectionSubConfigurationID = selectionSubConfigurationID; }
  
  public String getSelectionSubConfigurationName() { return this.selectionSubConfigurationName; }
  
  public void setSelectionSubConfigurationName(String selectionSubConfigurationName) { this.selectionSubConfigurationName = selectionSubConfigurationName; }
  
  public String getSelectionSubConfigurationAbbreviation() { return this.selectionSubConfigurationAbbreviation; }
  
  public void setSelectionSubConfigurationAbbreviation(String abbreviation) { this.selectionSubConfigurationAbbreviation = abbreviation; }
  
  public boolean isParent() { return this.parent; }
  
  public void setParent(boolean parent) { this.parent = parent; }
  
  public boolean getInactive() { return this.inactive; }
  
  public void setInactive(boolean inactive) { this.inactive = inactive; }
  
  public int getProdType() { return this.prodType; }
  
  public void setProdType(int prodType) { this.prodType = prodType; }
}


/* Location:              D:\Documents\NetBeansProjects\milestone2 Local.war!\WEB-INF\classes\co\\universal\milestone\SelectionSubConfiguration.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */