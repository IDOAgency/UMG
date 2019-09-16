package WEB-INF.classes.com.universal.milestone;

import com.techempower.DataEntity;
import com.universal.milestone.Supplier;

public class Supplier extends DataEntity {
  int supplierID;
  
  String supplierDescription;
  
  public Supplier() {}
  
  public Supplier(int id, String description) {
    this.supplierID = id;
    this.supplierDescription = description;
  }
  
  public int getSupplierID() { return this.supplierID; }
  
  public void setSupplierID(int supplierID) { this.supplierID = supplierID; }
  
  public String getSupplierDescription() { return this.supplierDescription; }
  
  public void setSupplierDescription(String description) { this.supplierDescription = description; }
}


/* Location:              D:\Documents\NetBeansProjects\milestone2 Local.war!\WEB-INF\classes\co\\universal\milestone\Supplier.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */