package WEB-INF.classes.com.universal.milestone;

import com.techempower.DataEntity;
import com.universal.milestone.SelectionConfiguration;
import java.util.Vector;

public class SelectionConfiguration extends DataEntity {
  int selectionConfigurationID;
  
  String selectionConfigurationAbbreviation;
  
  String selectionConfigurationName;
  
  Vector subConfigurations;
  
  boolean inactive;
  
  int prodType;
  
  int newBundle;
  
  public SelectionConfiguration(String abbreviation, String name, Vector subConfigs) {
    this.inactive = false;
    this.prodType = 2;
    this.newBundle = 1;
    this.selectionConfigurationAbbreviation = abbreviation;
    this.selectionConfigurationName = name;
    this.subConfigurations = subConfigs;
  }
  
  public SelectionConfiguration(String abbreviation, String name) { this(abbreviation, name, null); }
  
  public SelectionConfiguration(String name, Vector subConfigs) {
    this.inactive = false;
    this.prodType = 2;
    this.newBundle = 1;
    this.selectionConfigurationName = name;
    this.subConfigurations = subConfigs;
  }
  
  public SelectionConfiguration(String abbreviation, String name, Vector subConfigs, boolean inactive) {
    this.inactive = false;
    this.prodType = 2;
    this.newBundle = 1;
    this.selectionConfigurationAbbreviation = abbreviation;
    this.selectionConfigurationName = name;
    this.subConfigurations = subConfigs;
    this.inactive = inactive;
  }
  
  public SelectionConfiguration(String abbreviation, String name, Vector subConfigs, boolean inactive, int prodType) {
    this.inactive = false;
    this.prodType = 2;
    this.newBundle = 1;
    this.selectionConfigurationAbbreviation = abbreviation;
    this.selectionConfigurationName = name;
    this.subConfigurations = subConfigs;
    this.inactive = inactive;
    this.prodType = prodType;
  }
  
  public SelectionConfiguration(String abbreviation, String name, Vector subConfigs, boolean inactive, int prodType, int newBundle) {
    this.inactive = false;
    this.prodType = 2;
    this.newBundle = 1;
    this.selectionConfigurationAbbreviation = abbreviation;
    this.selectionConfigurationName = name;
    this.subConfigurations = subConfigs;
    this.inactive = inactive;
    this.prodType = prodType;
    this.newBundle = newBundle;
  }
  
  public int getSelectionConfigurationID() { return this.selectionConfigurationID; }
  
  public void setSelectionConfigurationID(int selectionConfigurationID) { this.selectionConfigurationID = selectionConfigurationID; }
  
  public String getSelectionConfigurationName() { return this.selectionConfigurationName; }
  
  public void setSelectionConfigurationName(String selectionConfigurationName) { this.selectionConfigurationName = selectionConfigurationName; }
  
  public String getSelectionConfigurationAbbreviation() { return this.selectionConfigurationAbbreviation; }
  
  public Vector getSubConfigurations() { return this.subConfigurations; }
  
  public void setSubConfigurations(Vector subConfig) { this.subConfigurations = subConfig; }
  
  public boolean getInactive() { return this.inactive; }
  
  public void setInactive(boolean inactive) { this.inactive = inactive; }
  
  public int getProdType() { return this.prodType; }
  
  public void setProdType(int prodType) { this.prodType = prodType; }
  
  public int getNewBundle() { return this.newBundle; }
  
  public void setNewBundle(int newBundle) { this.newBundle = newBundle; }
  
  public String toString() {
    return "SelectionConfiguration [" + this.selectionConfigurationID + 
      "; " + this.selectionConfigurationAbbreviation + 
      "; " + this.subConfigurations + 
      "]";
  }
}


/* Location:              D:\Documents\NetBeansProjects\milestone2 Local.war!\WEB-INF\classes\co\\universal\milestone\SelectionConfiguration.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */