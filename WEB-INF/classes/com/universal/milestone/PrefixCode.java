package WEB-INF.classes.com.universal.milestone;

import com.universal.milestone.LookupObject;
import com.universal.milestone.PrefixCode;

public class PrefixCode extends LookupObject {
  int prefixCodeSubValue;
  
  String detValue;
  
  public PrefixCode(String abbreviation, String name, int subValue) {
    super(abbreviation, name);
    this.prefixCodeSubValue = subValue;
  }
  
  public PrefixCode(String abbreviation, int subValue) {
    super(abbreviation);
    this.prefixCodeSubValue = subValue;
  }
  
  public PrefixCode(String abbreviation, int subValue, boolean inactive, int prodType) {
    super(abbreviation);
    this.prefixCodeSubValue = subValue;
    this.inactive = inactive;
    this.prodType = prodType;
  }
  
  public PrefixCode() {}
  
  public int getPrefixCodeSubValue() { return this.prefixCodeSubValue; }
  
  public void setPrefixCodeSubValue(int subValue) { this.prefixCodeSubValue = subValue; }
  
  public String getDetValue() { return this.detValue; }
  
  public void setDetValue(String detValue) { this.detValue = detValue; }
}


/* Location:              D:\Documents\NetBeansProjects\milestone2 Local.war!\WEB-INF\classes\co\\universal\milestone\PrefixCode.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */