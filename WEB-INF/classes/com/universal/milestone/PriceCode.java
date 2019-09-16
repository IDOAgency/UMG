package WEB-INF.classes.com.universal.milestone;

import com.techempower.DataEntity;
import com.universal.milestone.NotepadContentObject;
import com.universal.milestone.PriceCode;
import java.util.Calendar;

public class PriceCode extends DataEntity implements NotepadContentObject {
  protected String priceCodeID;
  
  protected String sellCode;
  
  protected String retailCode;
  
  protected String pricePoint;
  
  protected String description;
  
  protected float unitCost;
  
  protected float totalCost;
  
  protected int units;
  
  protected boolean isDigital;
  
  protected Calendar lastUpdateDate = null;
  
  protected long lastUpdatedCheck = -1L;
  
  protected String lastUpdateDateString = "";
  
  protected int lastUpdatingUser;
  
  public String getPriceCodeID() { return this.priceCodeID; }
  
  public void setPriceCodeID(String priceCodeID) { this.priceCodeID = priceCodeID; }
  
  public String getSellCode() { return this.sellCode; }
  
  public void setSellCode(String sellCode) { this.sellCode = sellCode; }
  
  public int getUnits() { return this.units; }
  
  public void setUnits(int units) { this.units = units; }
  
  public String getPricePoint() { return this.pricePoint; }
  
  public void setPricePoint(String pricePoint) { this.pricePoint = pricePoint; }
  
  public String getRetailCode() { return this.retailCode; }
  
  public void setRetailCode(String retailCode) { this.retailCode = retailCode; }
  
  public String getDescription() { return this.description; }
  
  public void setDescription(String description) { this.description = description; }
  
  public float getUnitCost() { return this.unitCost; }
  
  public void setUnitCost(float unitCost) { this.unitCost = unitCost; }
  
  public float getTotalCost() { return this.totalCost; }
  
  public void setTotalCost(float totalCost) { this.totalCost = totalCost; }
  
  public String getLastUpdateDateString() { return this.lastUpdateDateString; }
  
  public void setLastUpdateDateString(String lastUpdateDateString) { this.lastUpdateDateString = lastUpdateDateString; }
  
  public long getLastUpdatedCheck() { return this.lastUpdatedCheck; }
  
  public void setLastUpdatedCheck(long lastUpdatedCheck) { this.lastUpdatedCheck = lastUpdatedCheck; }
  
  public Calendar getLastUpdateDate() { return this.lastUpdateDate; }
  
  public void setLastUpdateDate(Calendar lastUpdateDate) { this.lastUpdateDate = lastUpdateDate; }
  
  public int getLastUpdatingUser() { return this.lastUpdatingUser; }
  
  public void setLastUpdatingUser(int updatingUser) { this.lastUpdatingUser = updatingUser; }
  
  public String getNotepadContentObjectId() { return this.sellCode; }
  
  public boolean getIsDigital() { return this.isDigital; }
  
  public void setIsDigital(boolean isDigital) { this.isDigital = isDigital; }
}


/* Location:              D:\Documents\NetBeansProjects\milestone2 Local.war!\WEB-INF\classes\co\\universal\milestone\PriceCode.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */