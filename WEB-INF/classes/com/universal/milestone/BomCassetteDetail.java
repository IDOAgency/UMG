/*     */ package WEB-INF.classes.com.universal.milestone;
/*     */ 
/*     */ import com.universal.milestone.BomCassetteDetail;
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
/*     */ public class BomCassetteDetail
/*     */ {
/*     */   public boolean coStatusIndicator = false;
/*  34 */   public int coPartId = -1;
/*  35 */   public int coParSupplierId = -1;
/*  36 */   public String coPart = "";
/*  37 */   public String coSupplier = "";
/*  38 */   public String coInk1 = "";
/*  39 */   public String coInk2 = "";
/*  40 */   public String coColor = "";
/*  41 */   public String coInfo = "";
/*     */   
/*     */   public boolean norelcoStatusIndicator = false;
/*     */   
/*  45 */   public int norelcoPartId = -1;
/*  46 */   public int norelcoSupplierId = -1;
/*  47 */   public String norelcoPart = "";
/*  48 */   public String norelcoSuplier = "";
/*  49 */   public String norelcoColor = "";
/*  50 */   public String norelcoInfo = "";
/*     */   
/*     */   public boolean jCardStatusIndicator = false;
/*     */   
/*  54 */   public int jCardPartId = -1;
/*  55 */   public String jCardPart = "";
/*  56 */   public int jCardSupplierId = -1;
/*  57 */   public String jCardSupplier = "";
/*  58 */   public String jCardInk1 = "";
/*  59 */   public String jCardInk2 = "";
/*  60 */   public String jCardPanels = "";
/*  61 */   public String jCardInfo = "";
/*     */   
/*     */   public boolean uCardStatusIndicator = false;
/*     */   
/*  65 */   public String uCardPart = "";
/*  66 */   public int uCardPartId = -1;
/*  67 */   public int uCardSupplierId = -1;
/*  68 */   public String uCardSupplier = "";
/*  69 */   public String uCardInk1 = "";
/*  70 */   public String uCardInk2 = "";
/*  71 */   public String uCardPanels = "";
/*  72 */   public String uCardInfo = "";
/*     */   
/*     */   public boolean oCardStatusIndicator = false;
/*     */   
/*  76 */   public int oCardPartId = -1;
/*  77 */   public String oCardPart = "";
/*  78 */   public int oCardSupplierId = -1;
/*  79 */   public String oCardSupplier = "";
/*  80 */   public String oCardInk1 = "";
/*  81 */   public String oCardInk2 = "";
/*  82 */   public String oCardInfo = "";
/*     */   
/*     */   public boolean stickerOneCardStatusIndicator = false;
/*     */   
/*  86 */   public int stickerOneCardPartId = -1;
/*  87 */   public String stickerOneCardPart = "";
/*  88 */   public int stickerOneCardSupplierId = -1;
/*  89 */   public String stickerOneCardSupplier = "";
/*  90 */   public String stickerOneCardInk1 = "";
/*  91 */   public String stickerOneCardInk2 = "";
/*  92 */   public String stickerOneCardPlaces = "";
/*  93 */   public String stickerOneCardInfo = "";
/*     */   
/*     */   public boolean stickerTwoCardStatusIndicator = false;
/*     */   
/*  97 */   public int stickerTwoCardPartId = -1;
/*  98 */   public String stickerTwoCardPart = "";
/*  99 */   public int stickerTwoCardSupplierId = -1;
/* 100 */   public String stickerTwoCardSupplier = "";
/* 101 */   public String stickerTwoCardInk1 = "";
/* 102 */   public String stickerTwoCardInk2 = "";
/* 103 */   public String stickerTwoCardPlaces = "";
/* 104 */   public String stickerTwoCardInfo = "";
/*     */   
/*     */   public boolean otherStatusIndicator = false;
/*     */   
/* 108 */   public int otherPartId = -1;
/* 109 */   public String otherPart = "";
/* 110 */   public int otherSupplierId = -1;
/* 111 */   public String otherSupplier = "";
/* 112 */   public String otherInk1 = "";
/* 113 */   public String otherInk2 = "";
/* 114 */   public String otherInfo = "";
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
/* 128 */   public String getCoInk1() { return this.coInk1.equalsIgnoreCase("[none]") ? "" : this.coInk1; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 133 */   public String getCoInk2() { return this.coInk2.equalsIgnoreCase("[none]") ? "" : this.coInk2; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 138 */   public String getCoColor() { return this.coColor.equalsIgnoreCase("[none]") ? "" : this.coColor; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 143 */   public String getCoInfo() { return this.coInfo.equalsIgnoreCase("[none]") ? "" : this.coInfo; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 149 */   public String getNorelcoColor() { return this.norelcoColor.equalsIgnoreCase("[none]") ? "" : this.norelcoColor; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 154 */   public String getNorelcoInfo() { return this.norelcoInfo.equalsIgnoreCase("[none]") ? "" : this.norelcoInfo; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 160 */   public String getJCardInk1() { return this.jCardInk1.equalsIgnoreCase("[none]") ? "" : this.jCardInk1; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 165 */   public String getJCardInk2() { return this.jCardInk2.equalsIgnoreCase("[none]") ? "" : this.jCardInk2; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 170 */   public String getJCardPanels() { return this.jCardPanels.equalsIgnoreCase("[none]") ? "" : this.jCardPanels; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 175 */   public String getJCardInfo() { return this.jCardInfo.equalsIgnoreCase("[none]") ? "" : this.jCardInfo; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 181 */   public String getUCardInk1() { return this.uCardInk1.equalsIgnoreCase("[none]") ? "" : this.uCardInk1; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 186 */   public String getUCardInk2() { return this.uCardInk2.equalsIgnoreCase("[none]") ? "" : this.uCardInk2; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 191 */   public String getUCardPanels() { return this.uCardPanels.equalsIgnoreCase("[none]") ? "" : this.uCardPanels; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 196 */   public String getUCardInfo() { return this.uCardInfo.equalsIgnoreCase("[none]") ? "" : this.uCardInfo; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 202 */   public String getOCardInk1() { return this.oCardInk1.equalsIgnoreCase("[none]") ? "" : this.oCardInk1; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 207 */   public String getOCardInk2() { return this.oCardInk2.equalsIgnoreCase("[none]") ? "" : this.oCardInk2; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 212 */   public String getOCardInfo() { return this.oCardInfo.equalsIgnoreCase("[none]") ? "" : this.oCardInfo; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 218 */   public String getStickerOneCardInk1() { return this.stickerOneCardInk1.equalsIgnoreCase("[none]") ? "" : this.stickerOneCardInk1; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 223 */   public String getStickerOneCardInk2() { return this.stickerOneCardInk2.equalsIgnoreCase("[none]") ? "" : this.stickerOneCardInk2; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 228 */   public String getStickerOneCardPlaces() { return this.stickerOneCardPlaces.equalsIgnoreCase("[none]") ? "" : this.stickerOneCardPlaces; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 233 */   public String getStickerOneCardInfo() { return this.stickerOneCardInfo.equalsIgnoreCase("[none]") ? "" : this.stickerOneCardInfo; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 239 */   public String getStickerTwoCardInk1() { return this.stickerTwoCardInk1.equalsIgnoreCase("[none]") ? "" : this.stickerTwoCardInk1; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 244 */   public String getStickerTwoCardInk2() { return this.stickerTwoCardInk2.equalsIgnoreCase("[none]") ? "" : this.stickerTwoCardInk2; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 249 */   public String getStickerTwoCardPlaces() { return this.stickerTwoCardPlaces.equalsIgnoreCase("[none]") ? "" : this.stickerTwoCardPlaces; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 254 */   public String getStickerTwoCardInfo() { return this.stickerTwoCardInfo.equalsIgnoreCase("[none]") ? "" : this.stickerTwoCardInfo; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 260 */   public String getOtherInk1() { return this.otherInk1.equalsIgnoreCase("[none]") ? "" : this.otherInk1; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 265 */   public String getOtherInk2() { return this.otherInk2.equalsIgnoreCase("[none]") ? "" : this.otherInk2; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 270 */   public String getOtherInfo() { return this.otherInfo.equalsIgnoreCase("[none]") ? "" : this.otherInfo; }
/*     */ }


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\BomCassetteDetail.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */