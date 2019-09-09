/*     */ package WEB-INF.classes.com.universal.milestone;
/*     */ 
/*     */ import com.universal.milestone.BomVinylDetail;
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
/*     */ public class BomVinylDetail
/*     */ {
/*     */   public boolean recordStatusIndicator = false;
/*  33 */   public int recordPartId = -1;
/*  34 */   public String recordPart = "";
/*  35 */   public int recordSupplierId = -1;
/*  36 */   public String recordSupplier = "";
/*  37 */   public String recordColor = "";
/*  38 */   public String recordInfo = "";
/*     */   
/*     */   public boolean labelStatusIndicator = false;
/*     */   
/*  42 */   public int labelSupplierId = -1;
/*  43 */   public String labelSupplier = "";
/*  44 */   public int labelPartId = -1;
/*  45 */   public String labelPart = "";
/*  46 */   public String labelInk1 = "";
/*  47 */   public String labelInk2 = "";
/*  48 */   public String labelInfo = "";
/*     */   
/*     */   public boolean sleeveStatusIndicator = false;
/*     */   
/*  52 */   public int sleeveSupplierId = -1;
/*  53 */   public String sleeveSupplier = "";
/*  54 */   public int sleevePartId = -1;
/*  55 */   public String sleevePart = "";
/*  56 */   public String sleeveInk1 = "";
/*  57 */   public String sleeveInk2 = "";
/*  58 */   public String sleeveInfo = "";
/*     */   
/*     */   public boolean jacketStatusIndicator = false;
/*     */   
/*  62 */   public int jacketSupplierId = -1;
/*  63 */   public String jacketSupplier = "";
/*  64 */   public int jacketPartId = -1;
/*  65 */   public String jacketPart = "";
/*  66 */   public String jacketInk1 = "";
/*  67 */   public String jacketInk2 = "";
/*  68 */   public String jacketInfo = "";
/*     */ 
/*     */   
/*     */   public boolean insertStatusIndicator = false;
/*     */ 
/*     */   
/*  74 */   public int insertSupplierId = -1;
/*  75 */   public String insertSupplier = "";
/*  76 */   public int insertPartId = -1;
/*  77 */   public String insertPart = "";
/*  78 */   public String insertInk1 = "";
/*  79 */   public String insertInk2 = "";
/*  80 */   public String insertInfo = "";
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean stickerOneStatusIndicator = false;
/*     */ 
/*     */   
/*  87 */   public int stickerOneSupplierId = -1;
/*  88 */   public String stickerOneSupplier = "";
/*  89 */   public int stickerOnePartId = -1;
/*  90 */   public String stickerOnePart = "";
/*  91 */   public String stickerOneInk1 = "";
/*  92 */   public String stickerOneInk2 = "";
/*  93 */   public String stickerOnePlaces = "";
/*  94 */   public String stickerOneInfo = "";
/*     */   
/*     */   public boolean stickerTwoStatusIndicator = false;
/*     */   
/*  98 */   public int stickerTwoSupplierId = -1;
/*  99 */   public String stickerTwoSupplier = "";
/* 100 */   public int stickerTwoPartId = -1;
/* 101 */   public String stickerTwoPart = "";
/* 102 */   public String stickerTwoInk1 = "";
/* 103 */   public String stickerTwoInk2 = "";
/* 104 */   public String stickerTwoPlaces = "";
/* 105 */   public String stickerTwoInfo = "";
/*     */   
/*     */   public boolean otherStatusIndicator = false;
/*     */   
/* 109 */   public int otherSupplierId = -1;
/* 110 */   public String otherSupplier = "";
/* 111 */   public int otherPartId = -1;
/* 112 */   public String otherPart = "";
/* 113 */   public String otherInk1 = "";
/* 114 */   public String otherInk2 = "";
/* 115 */   public String otherInfo = "";
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
/* 128 */   public String getRecordColor() { return this.recordColor.equalsIgnoreCase("[none]") ? "" : this.recordColor; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 133 */   public String getRecordInfo() { return this.recordInfo.equalsIgnoreCase("[none]") ? "" : this.recordInfo; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 139 */   public String getLabelInk1() { return this.labelInk1.equalsIgnoreCase("[none]") ? "" : this.labelInk1; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 144 */   public String getLabelInk2() { return this.labelInk2.equalsIgnoreCase("[none]") ? "" : this.labelInk2; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 149 */   public String getLabelInfo() { return this.labelInfo.equalsIgnoreCase("[none]") ? "" : this.labelInfo; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 155 */   public String getSleeveInk1() { return this.sleeveInk1.equalsIgnoreCase("[none]") ? "" : this.sleeveInk1; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 160 */   public String getSleeveInk2() { return this.sleeveInk2.equalsIgnoreCase("[none]") ? "" : this.sleeveInk2; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 165 */   public String getSleeveInfo() { return this.sleeveInfo.equalsIgnoreCase("[none]") ? "" : this.sleeveInfo; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 171 */   public String getJacketInk1() { return this.jacketInk1.equalsIgnoreCase("[none]") ? "" : this.jacketInk1; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 176 */   public String getJacketInk2() { return this.jacketInk2.equalsIgnoreCase("[none]") ? "" : this.jacketInk2; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 181 */   public String getJacketInfo() { return this.jacketInfo.equalsIgnoreCase("[none]") ? "" : this.jacketInfo; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 189 */   public String getInsertInk1() { return this.insertInk1.equalsIgnoreCase("[none]") ? "" : this.insertInk1; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 194 */   public String getInsertInk2() { return this.insertInk2.equalsIgnoreCase("[none]") ? "" : this.insertInk2; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 199 */   public String getInsertInfo() { return this.insertInfo.equalsIgnoreCase("[none]") ? "" : this.insertInfo; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 208 */   public String getStickerOneInk1() { return this.stickerOneInk1.equalsIgnoreCase("[none]") ? "" : this.stickerOneInk1; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 213 */   public String getStickerOneInk2() { return this.stickerOneInk2.equalsIgnoreCase("[none]") ? "" : this.stickerOneInk2; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 218 */   public String getStickerOnePlaces() { return this.stickerOnePlaces.equalsIgnoreCase("[none]") ? "" : this.stickerOnePlaces; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 223 */   public String getStickerOneInfo() { return this.stickerOneInfo.equalsIgnoreCase("[none]") ? "" : this.stickerOneInfo; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 229 */   public String getStickerTwoInk1() { return this.stickerTwoInk1.equalsIgnoreCase("[none]") ? "" : this.stickerTwoInk1; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 234 */   public String getStickerTwoInk2() { return this.stickerTwoInk2.equalsIgnoreCase("[none]") ? "" : this.stickerTwoInk2; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 239 */   public String getStickerTwoPlaces() { return this.stickerTwoPlaces.equalsIgnoreCase("[none]") ? "" : this.stickerTwoPlaces; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 244 */   public String getStickerTwoInfo() { return this.stickerTwoInfo.equalsIgnoreCase("[none]") ? "" : this.stickerTwoInfo; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 250 */   public String getOtherInk1() { return this.otherInk1.equalsIgnoreCase("[none]") ? "" : this.otherInk1; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 255 */   public String getOtherInk2() { return this.otherInk2.equalsIgnoreCase("[none]") ? "" : this.otherInk2; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 260 */   public String getOtherInfo() { return this.otherInfo.equalsIgnoreCase("[none]") ? "" : this.otherInfo; }
/*     */ }


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\BomVinylDetail.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */