package WEB-INF.classes.com.universal.milestone;

import com.techempower.ComponentLog;
import com.techempower.EnhancedProperties;
import com.techempower.gemini.GeminiApplication;
import com.universal.milestone.CorporateObjectSearchObj;
import com.universal.milestone.MilestoneConstants;

public class CorporateObjectSearchObj implements MilestoneConstants {
  public static final String COMPONENT_CODE = "note";
  
  protected static ComponentLog log;
  
  protected String familySearch = "";
  
  protected String environmentSearch = "";
  
  protected String companySearch = "";
  
  protected String labelSearch = "";
  
  protected String divisionSearch = "";
  
  protected String entitySearch = "";
  
  public static void configure(EnhancedProperties props, GeminiApplication application) {}
  
  public String getFamilySearch() { return this.familySearch; }
  
  public void setFamilySearch(String searchString) { this.familySearch = searchString; }
  
  public String getCompanySearch() { return this.companySearch; }
  
  public void setCompanySearch(String searchString) { this.companySearch = searchString; }
  
  public String getDivisionSearch() { return this.divisionSearch; }
  
  public void setDivisionSearch(String searchString) { this.divisionSearch = searchString; }
  
  public String getLabelSearch() { return this.labelSearch; }
  
  public void setLabelSearch(String searchString) { this.labelSearch = searchString; }
  
  public String getEntitySearch() { return this.entitySearch; }
  
  public void setEntitySearch(String searchString) { this.entitySearch = searchString; }
  
  public void clearAllSearches() {
    this.familySearch = "";
    this.environmentSearch = "";
    this.companySearch = "";
    this.divisionSearch = "";
    this.labelSearch = "";
    this.entitySearch = "";
  }
  
  public boolean isSearchEmpty() {
    boolean result = false;
    if (this.familySearch.equals("") && 
      this.environmentSearch.equals("") && 
      this.companySearch.equals("") && 
      this.divisionSearch.equals("") && 
      this.labelSearch.equals("") && 
      this.entitySearch.equals(""))
      result = true; 
    return result;
  }
  
  public String getEnvironmentSearch() { return this.environmentSearch; }
  
  public void setEnvironmentSearch(String searchString) { this.environmentSearch = searchString; }
}


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\CorporateObjectSearchObj.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */