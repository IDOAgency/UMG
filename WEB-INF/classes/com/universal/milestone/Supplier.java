/*    */ package WEB-INF.classes.com.universal.milestone;
/*    */ 
/*    */ import com.techempower.DataEntity;
/*    */ import com.universal.milestone.Supplier;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Supplier
/*    */   extends DataEntity
/*    */ {
/*    */   int supplierID;
/*    */   String supplierDescription;
/*    */   
/*    */   public Supplier() {}
/*    */   
/*    */   public Supplier(int id, String description) {
/* 37 */     this.supplierID = id;
/* 38 */     this.supplierDescription = description;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 52 */   public int getSupplierID() { return this.supplierID; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 65 */   public void setSupplierID(int supplierID) { this.supplierID = supplierID; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 75 */   public String getSupplierDescription() { return this.supplierDescription; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 86 */   public void setSupplierDescription(String description) { this.supplierDescription = description; }
/*    */ }


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\Supplier.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */