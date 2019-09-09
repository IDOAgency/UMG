/*     */ package WEB-INF.classes.com.universal.milestone;
/*     */ 
/*     */ import com.universal.milestone.LookupObject;
/*     */ import com.universal.milestone.PrefixCode;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PrefixCode
/*     */   extends LookupObject
/*     */ {
/*     */   int prefixCodeSubValue;
/*     */   String detValue;
/*     */   
/*     */   public PrefixCode(String abbreviation, String name, int subValue) {
/*  50 */     super(abbreviation, name);
/*  51 */     this.prefixCodeSubValue = subValue;
/*     */   }
/*     */ 
/*     */   
/*     */   public PrefixCode(String abbreviation, int subValue) {
/*  56 */     super(abbreviation);
/*  57 */     this.prefixCodeSubValue = subValue;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public PrefixCode(String abbreviation, int subValue, boolean inactive, int prodType) {
/*  63 */     super(abbreviation);
/*  64 */     this.prefixCodeSubValue = subValue;
/*  65 */     this.inactive = inactive;
/*  66 */     this.prodType = prodType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PrefixCode() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  81 */   public int getPrefixCodeSubValue() { return this.prefixCodeSubValue; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  91 */   public void setPrefixCodeSubValue(int subValue) { this.prefixCodeSubValue = subValue; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 101 */   public String getDetValue() { return this.detValue; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 111 */   public void setDetValue(String detValue) { this.detValue = detValue; }
/*     */ }


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\PrefixCode.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */