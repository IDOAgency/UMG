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
/*  39 */   public String recordInfo2 = "";
/*     */   
/*     */   public boolean labelStatusIndicator = false;
/*     */   
/*  43 */   public int labelSupplierId = -1;
/*  44 */   public String labelSupplier = "";
/*  45 */   public int labelPartId = -1;
/*  46 */   public String labelPart = "";
/*  47 */   public String labelInk1 = "";
/*  48 */   public String labelInk2 = "";
/*  49 */   public String labelInfo = "";
/*     */   
/*     */   public boolean sleeveStatusIndicator = false;
/*     */   
/*  53 */   public int sleeveSupplierId = -1;
/*  54 */   public String sleeveSupplier = "";
/*  55 */   public int sleevePartId = -1;
/*  56 */   public String sleevePart = "";
/*  57 */   public String sleeveInk1 = "";
/*  58 */   public String sleeveInk2 = "";
/*  59 */   public String sleeveInfo = "";
/*     */   
/*     */   public boolean jacketStatusIndicator = false;
/*     */   
/*  63 */   public int jacketSupplierId = -1;
/*  64 */   public String jacketSupplier = "";
/*  65 */   public int jacketPartId = -1;
/*  66 */   public String jacketPart = "";
/*  67 */   public String jacketInk1 = "";
/*  68 */   public String jacketInk2 = "";
/*  69 */   public String jacketInfo = "";
/*     */ 
/*     */   
/*     */   public boolean insertStatusIndicator = false;
/*     */ 
/*     */   
/*  75 */   public int insertSupplierId = -1;
/*  76 */   public String insertSupplier = "";
/*  77 */   public int insertPartId = -1;
/*  78 */   public String insertPart = "";
/*  79 */   public String insertInk1 = "";
/*  80 */   public String insertInk2 = "";
/*  81 */   public String insertInfo = "";
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean stickerOneStatusIndicator = false;
/*     */ 
/*     */   
/*  88 */   public int stickerOneSupplierId = -1;
/*  89 */   public String stickerOneSupplier = "";
/*  90 */   public int stickerOnePartId = -1;
/*  91 */   public String stickerOnePart = "";
/*  92 */   public String stickerOneInk1 = "";
/*  93 */   public String stickerOneInk2 = "";
/*  94 */   public String stickerOnePlaces = "";
/*  95 */   public String stickerOneInfo = "";
/*     */   
/*     */   public boolean stickerTwoStatusIndicator = false;
/*     */   
/*  99 */   public int stickerTwoSupplierId = -1;
/* 100 */   public String stickerTwoSupplier = "";
/* 101 */   public int stickerTwoPartId = -1;
/* 102 */   public String stickerTwoPart = "";
/* 103 */   public String stickerTwoInk1 = "";
/* 104 */   public String stickerTwoInk2 = "";
/* 105 */   public String stickerTwoPlaces = "";
/* 106 */   public String stickerTwoInfo = "";
/*     */   
/*     */   public boolean otherStatusIndicator = false;
/*     */   
/* 110 */   public int otherSupplierId = -1;
/* 111 */   public String otherSupplier = "";
/* 112 */   public int otherPartId = -1;
/* 113 */   public String otherPart = "";
/* 114 */   public String otherInk1 = "";
/* 115 */   public String otherInk2 = "";
/* 116 */   public String otherInfo = "";
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
/* 129 */   public String getRecordColor() { return this.recordColor.equalsIgnoreCase("[none]") ? "" : this.recordColor; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 134 */   public String getRecordInfo() { return this.recordInfo.equalsIgnoreCase("[none]") ? "" : this.recordInfo; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 140 */   public String getLabelInk1() { return this.labelInk1.equalsIgnoreCase("[none]") ? "" : this.labelInk1; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 145 */   public String getLabelInk2() { return this.labelInk2.equalsIgnoreCase("[none]") ? "" : this.labelInk2; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 150 */   public String getLabelInfo() { return this.labelInfo.equalsIgnoreCase("[none]") ? "" : this.labelInfo; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 156 */   public String getSleeveInk1() { return this.sleeveInk1.equalsIgnoreCase("[none]") ? "" : this.sleeveInk1; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 161 */   public String getSleeveInk2() { return this.sleeveInk2.equalsIgnoreCase("[none]") ? "" : this.sleeveInk2; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 166 */   public String getSleeveInfo() { return this.sleeveInfo.equalsIgnoreCase("[none]") ? "" : this.sleeveInfo; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 172 */   public String getJacketInk1() { return this.jacketInk1.equalsIgnoreCase("[none]") ? "" : this.jacketInk1; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 177 */   public String getJacketInk2() { return this.jacketInk2.equalsIgnoreCase("[none]") ? "" : this.jacketInk2; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 182 */   public String getJacketInfo() { return this.jacketInfo.equalsIgnoreCase("[none]") ? "" : this.jacketInfo; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 190 */   public String getInsertInk1() { return this.insertInk1.equalsIgnoreCase("[none]") ? "" : this.insertInk1; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 195 */   public String getInsertInk2() { return this.insertInk2.equalsIgnoreCase("[none]") ? "" : this.insertInk2; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 200 */   public String getInsertInfo() { return this.insertInfo.equalsIgnoreCase("[none]") ? "" : this.insertInfo; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 209 */   public String getStickerOneInk1() { return this.stickerOneInk1.equalsIgnoreCase("[none]") ? "" : this.stickerOneInk1; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 214 */   public String getStickerOneInk2() { return this.stickerOneInk2.equalsIgnoreCase("[none]") ? "" : this.stickerOneInk2; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 219 */   public String getStickerOnePlaces() { return this.stickerOnePlaces.equalsIgnoreCase("[none]") ? "" : this.stickerOnePlaces; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 224 */   public String getStickerOneInfo() { return this.stickerOneInfo.equalsIgnoreCase("[none]") ? "" : this.stickerOneInfo; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 230 */   public String getStickerTwoInk1() { return this.stickerTwoInk1.equalsIgnoreCase("[none]") ? "" : this.stickerTwoInk1; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 235 */   public String getStickerTwoInk2() { return this.stickerTwoInk2.equalsIgnoreCase("[none]") ? "" : this.stickerTwoInk2; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 240 */   public String getStickerTwoPlaces() { return this.stickerTwoPlaces.equalsIgnoreCase("[none]") ? "" : this.stickerTwoPlaces; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 245 */   public String getStickerTwoInfo() { return this.stickerTwoInfo.equalsIgnoreCase("[none]") ? "" : this.stickerTwoInfo; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 251 */   public String getOtherInk1() { return this.otherInk1.equalsIgnoreCase("[none]") ? "" : this.otherInk1; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 256 */   public String getOtherInk2() { return this.otherInk2.equalsIgnoreCase("[none]") ? "" : this.otherInk2; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 261 */   public String getOtherInfo() { return this.otherInfo.equalsIgnoreCase("[none]") ? "" : this.otherInfo; }
/*     */ }


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\BomVinylDetail.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */