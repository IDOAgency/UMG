/*     */ package WEB-INF.classes.com.universal.milestone;
/*     */ 
/*     */ import com.universal.milestone.BomDiskDetail;
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
/*     */ public class BomDiskDetail
/*     */ {
/*     */   public boolean discStatusIndicator = false;
/*  33 */   public int discPartId = -1;
/*  34 */   public int diskSupplierId = -1;
/*  35 */   public String discPart = "";
/*  36 */   public String discSupplier = "";
/*  37 */   public String discInk1 = "";
/*  38 */   public String discInk2 = "";
/*  39 */   public String discInfo = "";
/*     */   
/*     */   public boolean jewelStatusIndicator = false;
/*     */   
/*  43 */   public int jewelPartId = -1;
/*  44 */   public String jewelPart = "";
/*  45 */   public String jewelColor = "";
/*  46 */   public String jewelInfo = "";
/*     */   
/*     */   public boolean trayStatusIndicator = false;
/*     */   
/*  50 */   public int trayPartId = -1;
/*  51 */   public String trayPart = "";
/*  52 */   public String trayColor = "";
/*  53 */   public String trayInfo = "";
/*     */   
/*     */   public boolean inlayStatusIndicator = false;
/*     */   
/*  57 */   public int inlayPartId = -1;
/*  58 */   public String inlayPart = "";
/*  59 */   public int inlaySupplierId = -1;
/*  60 */   public String inlaySupplier = "";
/*  61 */   public String inlayInk1 = "";
/*  62 */   public String inlayInk2 = "";
/*  63 */   public String inlayInfo = "";
/*     */   
/*     */   public boolean frontStatusIndicator = false;
/*     */   
/*  67 */   public int frontPartId = -1;
/*  68 */   public String frontPart = "";
/*  69 */   public int frontSupplierId = -1;
/*  70 */   public String frontSupplier = "";
/*  71 */   public String frontInk1 = "";
/*  72 */   public String frontInk2 = "";
/*  73 */   public String frontInfo = "";
/*     */   
/*     */   public boolean folderStatusIndicator = false;
/*     */   
/*  77 */   public int folderPartId = -1;
/*  78 */   public String folderPart = "";
/*  79 */   public int folderSupplierId = -1;
/*  80 */   public String folderSupplier = "";
/*  81 */   public String folderInk1 = "";
/*  82 */   public String folderInk2 = "";
/*  83 */   public String folderPages = "";
/*  84 */   public String folderInfo = "";
/*     */   
/*     */   public boolean bookletStatusIndicator = false;
/*     */   
/*  88 */   public int bookletPartId = -1;
/*  89 */   public String bookletPart = "";
/*  90 */   public int bookletSupplierId = -1;
/*  91 */   public String bookletSupplier = "";
/*  92 */   public String bookletInk1 = "";
/*  93 */   public String bookletInk2 = "";
/*  94 */   public String bookletPages = "";
/*  95 */   public String bookletInfo = "";
/*     */   
/*     */   public boolean brcStatusIndicator = false;
/*     */   
/*  99 */   public int brcPartId = -1;
/* 100 */   public String brcPart = "";
/* 101 */   public int brcSupplierId = -1;
/* 102 */   public String brcSupplier = "";
/* 103 */   public String brcInk1 = "";
/* 104 */   public String brcInk2 = "";
/* 105 */   public String brcSize = "";
/* 106 */   public String brcInfo = "";
/*     */   
/*     */   public boolean miniStatusIndicator = false;
/*     */   
/* 110 */   public int miniPartId = -1;
/* 111 */   public String miniPart = "";
/* 112 */   public int miniSupplierId = -1;
/* 113 */   public String miniSupplier = "";
/* 114 */   public String miniInk1 = "";
/* 115 */   public String miniInk2 = "";
/* 116 */   public String miniInfo = "";
/*     */   
/*     */   public boolean digiPakStatusIndicator = false;
/*     */   
/* 120 */   public int digiPakPartId = -1;
/* 121 */   public String digiPakPart = "";
/* 122 */   public int digiPakSupplierId = -1;
/* 123 */   public String digiPakSupplier = "";
/* 124 */   public String digiPakInk1 = "";
/* 125 */   public String digiPakInk2 = "";
/* 126 */   public String digiPakTray = "";
/* 127 */   public String digiPakInfo = "";
/*     */ 
/*     */   
/*     */   public boolean softPakStatusIndicator = false;
/*     */   
/* 132 */   public int softPakPartId = -1;
/* 133 */   public String softPakPart = "";
/* 134 */   public int softPakSupplierId = -1;
/* 135 */   public String softPakSupplier = "";
/* 136 */   public String softPakInk1 = "";
/* 137 */   public String softPakInk2 = "";
/* 138 */   public String softPakTray = "";
/* 139 */   public String softPakInfo = "";
/*     */   
/*     */   public boolean stickerOneStatusIndicator = false;
/*     */   
/* 143 */   public int stickerOnePartId = -1;
/* 144 */   public String stickerOnePart = "";
/* 145 */   public int stickerOneSupplierId = -1;
/* 146 */   public String stickerOneSupplier = "";
/* 147 */   public String stickerOneInk1 = "";
/* 148 */   public String stickerOneInk2 = "";
/* 149 */   public String stickerOnePlaces = "";
/* 150 */   public String stickerOneInfo = "";
/*     */   
/*     */   public boolean stickerTwoStatusIndicator = false;
/*     */   
/* 154 */   public int stickerTwoPartId = -1;
/* 155 */   public String stickerTwoPart = "";
/* 156 */   public int stickerTwoSupplierId = -1;
/* 157 */   public String stickerTwoSupplier = "";
/* 158 */   public String stickerTwoInk1 = "";
/* 159 */   public String stickerTwoInk2 = "";
/* 160 */   public String stickerTwoPlaces = "";
/* 161 */   public String stickerTwoInfo = "";
/*     */   
/*     */   public boolean bookStatusIndicator = false;
/*     */   
/* 165 */   public int bookPartId = -1;
/* 166 */   public String bookPart = "";
/* 167 */   public int bookSupplierId = -1;
/* 168 */   public String bookSupplier = "";
/* 169 */   public String bookInk1 = "";
/* 170 */   public String bookInk2 = "";
/* 171 */   public String bookPages = "";
/* 172 */   public String bookInfo = "";
/*     */   
/*     */   public boolean boxStatusIndicator = false;
/*     */   
/* 176 */   public int boxPartId = -1;
/* 177 */   public String boxPart = "";
/* 178 */   public int boxSupplierId = -1;
/* 179 */   public String boxSupplier = "";
/* 180 */   public String boxInk1 = "";
/* 181 */   public String boxInk2 = "";
/* 182 */   public String boxSizes = "";
/* 183 */   public String boxInfo = "";
/*     */   
/*     */   public boolean otherStatusIndicator = false;
/*     */   
/* 187 */   public int otherPartId = -1;
/* 188 */   public String otherPart = "";
/* 189 */   public int otherSupplierId = -1;
/* 190 */   public String otherSupplier = "";
/* 191 */   public String otherInk1 = "";
/* 192 */   public String otherInk2 = "";
/* 193 */   public String otherInfo = "";
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
/* 207 */   public String getDiscPart() { return this.discPart.equalsIgnoreCase("[none]") ? "" : this.discPart; }
/*     */ 
/*     */ 
/*     */   
/* 211 */   public String getDiscPartSuplier() { return this.discSupplier.equalsIgnoreCase("[none]") ? "" : this.discSupplier; }
/*     */ 
/*     */ 
/*     */   
/* 215 */   public String getDiscInk1() { return this.discInk1.equalsIgnoreCase("[none]") ? "" : this.discInk1; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 220 */   public String getDiscInk2() { return this.discInk2.equalsIgnoreCase("[none]") ? "" : this.discInk2; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 225 */   public String getDiscInfo() { return this.discInfo.equalsIgnoreCase("[none]") ? "" : this.discInfo; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 231 */   public String getJewelPart() { return this.jewelPart.equalsIgnoreCase("[none]") ? "" : this.jewelPart; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 236 */   public String getJewelColor() { return this.jewelColor.equalsIgnoreCase("[none]") ? "" : this.jewelColor; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 241 */   public String getJewelInfo() { return this.jewelInfo.equalsIgnoreCase("[none]") ? "" : this.jewelInfo; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 247 */   public String getTrayPart() { return this.trayPart.equalsIgnoreCase("[none]") ? "" : this.trayPart; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 252 */   public String getTrayColor() { return this.trayColor.equalsIgnoreCase("[none]") ? "" : this.trayColor; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 257 */   public String getTrayInfo() { return this.trayInfo.equalsIgnoreCase("[none]") ? "" : this.trayInfo; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 263 */   public String getInlayPart() { return this.inlayPart.equalsIgnoreCase("[none]") ? "" : this.inlayPart; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 268 */   public String getInlaySupplier() { return this.inlaySupplier.equalsIgnoreCase("[none]") ? "" : this.inlaySupplier; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 273 */   public String getInlayInk1() { return this.inlayInk1.equalsIgnoreCase("[none]") ? "" : this.inlayInk1; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 278 */   public String getInlayInk2() { return this.inlayInk2.equalsIgnoreCase("[none]") ? "" : this.inlayInk2; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 283 */   public String getInlayInfo() { return this.inlayInfo.equalsIgnoreCase("[none]") ? "" : this.inlayInfo; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 289 */   public String getFrontPart() { return this.frontPart.equalsIgnoreCase("[none]") ? "" : this.frontPart; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 294 */   public String getFrontInk1() { return this.frontInk1.equalsIgnoreCase("[none]") ? "" : this.frontInk1; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 299 */   public String getFrontInk2() { return this.frontInk2.equalsIgnoreCase("[none]") ? "" : this.frontInk2; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 304 */   public String getFrontInfo() { return this.frontInfo.equalsIgnoreCase("[none]") ? "" : this.frontInfo; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 310 */   public String getFolderInk1() { return this.folderInk1.equalsIgnoreCase("[none]") ? "" : this.folderInk1; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 315 */   public String getFolderInk2() { return this.folderInk2.equalsIgnoreCase("[none]") ? "" : this.folderInk2; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 320 */   public String getFolderPages() { return this.folderPages.equalsIgnoreCase("[none]") ? "" : this.folderPages; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 325 */   public String getFolderInfo() { return this.folderInfo.equalsIgnoreCase("[none]") ? "" : this.folderInfo; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 331 */   public String getBookletInk1() { return this.bookletInk1.equalsIgnoreCase("[none]") ? "" : this.bookletInk1; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 336 */   public String getBookletInk2() { return this.bookletInk2.equalsIgnoreCase("[none]") ? "" : this.bookletInk2; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 341 */   public String getBookletPages() { return this.bookletPages.equalsIgnoreCase("[none]") ? "" : this.bookletPages; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 346 */   public String getBookletInfo() { return this.bookletInfo.equalsIgnoreCase("[none]") ? "" : this.bookletInfo; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 352 */   public String getBrcInk1() { return this.brcInk1.equalsIgnoreCase("[none]") ? "" : this.brcInk1; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 357 */   public String getBrcInk2() { return this.brcInk2.equalsIgnoreCase("[none]") ? "" : this.brcInk2; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 362 */   public String getBrcSize() { return this.brcSize.equalsIgnoreCase("[none]") ? "" : this.brcSize; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 367 */   public String getBrcInfo() { return this.brcInfo.equalsIgnoreCase("[none]") ? "" : this.brcInfo; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 373 */   public String getMiniInk1() { return this.miniInk1.equalsIgnoreCase("[none]") ? "" : this.miniInk1; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 378 */   public String getMiniInk2() { return this.miniInk2.equalsIgnoreCase("[none]") ? "" : this.miniInk2; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 383 */   public String getMiniInfo() { return this.miniInfo.equalsIgnoreCase("[none]") ? "" : this.miniInfo; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 389 */   public String getDigiPakInk1() { return this.digiPakInk1.equalsIgnoreCase("[none]") ? "" : this.digiPakInk1; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 394 */   public String getDigiPakInk2() { return this.digiPakInk2.equalsIgnoreCase("[none]") ? "" : this.digiPakInk2; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 399 */   public String getDigiPakTray() { return this.digiPakTray.equalsIgnoreCase("[none]") ? "" : this.digiPakTray; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 404 */   public String getDigiPakInfo() { return this.digiPakInfo.equalsIgnoreCase("[none]") ? "" : this.digiPakInfo; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 410 */   public String getSoftPakInk1() { return this.softPakInk1.equalsIgnoreCase("[none]") ? "" : this.softPakInk1; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 415 */   public String getSoftPakInk2() { return this.softPakInk2.equalsIgnoreCase("[none]") ? "" : this.softPakInk2; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 420 */   public String getSoftPakTray() { return this.softPakTray.equalsIgnoreCase("[none]") ? "" : this.softPakTray; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 425 */   public String getSoftPakInfo() { return this.softPakInfo.equalsIgnoreCase("[none]") ? "" : this.softPakInfo; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 433 */   public String getStickerOneInk1() { return this.stickerOneInk1.equalsIgnoreCase("[none]") ? "" : this.stickerOneInk1; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 438 */   public String getStickerOneInk2() { return this.stickerOneInk2.equalsIgnoreCase("[none]") ? "" : this.stickerOneInk2; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 443 */   public String getStickerOnePlaces() { return this.stickerOnePlaces.equalsIgnoreCase("[none]") ? "" : this.stickerOnePlaces; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 448 */   public String getStickerOneInfo() { return this.stickerOneInfo.equalsIgnoreCase("[none]") ? "" : this.stickerOneInfo; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 454 */   public String getStickerTwoInk1() { return this.stickerTwoInk1.equalsIgnoreCase("[none]") ? "" : this.stickerTwoInk1; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 459 */   public String getStickerTwoInk2() { return this.stickerTwoInk2.equalsIgnoreCase("[none]") ? "" : this.stickerTwoInk2; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 464 */   public String getStickerTwoPlaces() { return this.stickerTwoPlaces.equalsIgnoreCase("[none]") ? "" : this.stickerTwoPlaces; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 469 */   public String getStickerTwoInfo() { return this.stickerTwoInfo.equalsIgnoreCase("[none]") ? "" : this.stickerTwoInfo; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 475 */   public String getBookInk1() { return this.bookInk1.equalsIgnoreCase("[none]") ? "" : this.bookInk1; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 480 */   public String getBookInk2() { return this.bookInk2.equalsIgnoreCase("[none]") ? "" : this.bookInk2; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 485 */   public String getBookPages() { return this.bookPages.equalsIgnoreCase("[none]") ? "" : this.bookPages; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 490 */   public String getBookInfo() { return this.bookInfo.equalsIgnoreCase("[none]") ? "" : this.bookInfo; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 496 */   public String getBoxInk1() { return this.boxInk1.equalsIgnoreCase("[none]") ? "" : this.boxInk1; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 501 */   public String getBoxInk2() { return this.boxInk2.equalsIgnoreCase("[none]") ? "" : this.boxInk2; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 506 */   public String getBoxSizes() { return this.boxSizes.equalsIgnoreCase("[none]") ? "" : this.boxSizes; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 511 */   public String getBoxInfo() { return this.boxInfo.equalsIgnoreCase("[none]") ? "" : this.boxInfo; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 517 */   public String getOtherInk1() { return this.otherInk1.equalsIgnoreCase("[none]") ? "" : this.otherInk1; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 522 */   public String getOtherInk2() { return this.otherInk2.equalsIgnoreCase("[none]") ? "" : this.otherInk2; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 527 */   public String getOtherInfo() { return this.otherInfo.equalsIgnoreCase("[none]") ? "" : this.otherInfo; }
/*     */ }


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\BomDiskDetail.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */