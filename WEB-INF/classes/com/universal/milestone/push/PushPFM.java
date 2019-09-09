/*      */ package WEB-INF.classes.com.universal.milestone.push;
/*      */ 
/*      */ import com.universal.milestone.Cache;
/*      */ import com.universal.milestone.LookupObject;
/*      */ import com.universal.milestone.MilestoneHelper;
/*      */ import com.universal.milestone.MilestoneHelper_2;
/*      */ import com.universal.milestone.Pfm;
/*      */ import com.universal.milestone.Selection;
/*      */ import com.universal.milestone.SelectionManager;
/*      */ import com.universal.milestone.push.PushCommunication;
/*      */ import com.universal.milestone.push.PushPFM;
/*      */ import com.universal.milestone.xml.XMLUtil;
/*      */ import java.text.SimpleDateFormat;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Date;
/*      */ import java.util.Hashtable;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class PushPFM
/*      */ {
/*      */   public int releaseId;
/*      */   public int matchReleaseId;
/*      */   public static final String programID = "FM511N";
/*      */   public String pushType;
/*      */   public String digitalPhysicalInd;
/*      */   public String promoCommercialInd;
/*      */   public String userId;
/*      */   public String userName;
/*      */   public String opCompany;
/*      */   public String productNo;
/*      */   public String configCode;
/*      */   public String configMod;
/*      */   public String title;
/*      */   public String artist;
/*      */   public String artistFirstName;
/*      */   public String artistLastName;
/*      */   public String titleId;
/*      */   public String releaseDate;
/*      */   public String digitalReleaseDate;
/*      */   public String superLabel;
/*      */   public String labelCode;
/*      */   public String companyCode;
/*      */   public String projectId;
/*      */   public String poMergeCode;
/*      */   public int unitsPerSet;
/*      */   public int setsPerCarton;
/*      */   public String supplierNo;
/*      */   public String importInd;
/*      */   public String upc;
/*      */   public String soundScanGrpCode;
/*      */   public String musicLine;
/*      */   public String repertoireOwner;
/*      */   public String repertoireClass;
/*      */   public String scrapCode;
/*      */   public String exportInd;
/*      */   public String spineTitle;
/*      */   public String spineArtist;
/*      */   public String loosePickExemptInd;
/*      */   public String guaranteeInd;
/*      */   public String musicType;
/*      */   public String impRateCode;
/*      */   public String pricePoint;
/*      */   public String naarmExtractInd;
/*      */   public String sndtrkCode;
/*      */   public String parentalAdvisoryInd;
/*      */   public String boxSetInd;
/*      */   public String valueAddedInd;
/*      */   public String encryptionInd;
/*      */   public String priceCode;
/*      */   public String status;
/*      */   public String error;
/*      */   public String message;
/*      */   public String appendToEmail;
/*      */   public boolean exception;
/*      */   boolean IsDigital;
/*      */   String IsNone;
/*      */   boolean IsAPNG;
/*      */   public static final String IsBlank = "-1";
/*      */   public static final String PROMO = "P";
/*      */   public static final String COMMERCIAL = "C";
/*      */   public static final String DIGITAL = "D";
/*      */   public static final String PHYSICAL = "P";
/*      */   public String prefix;
/*      */   public static final String TBS_DATE = "123139";
/*  105 */   public static Hashtable pushElems = null;
/*  106 */   public static final String[] PFMPushElements = { "PFMPushElems" };
/*  107 */   public static Hashtable PFMPushExclFamilies = null;
/*      */   
/*  109 */   public static String promoZeroUPCStr = "00000000000000";
/*  110 */   public static String TBS = "TBS";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int[] elementPositions = { 
/*  119 */       0, 8, 16, 17, 18, 20, 23, 25, 35, 45, 95, 145, 151, 
/*  120 */       157, 160, 172, 186, 200, 204, 206, 208, 211, 214, 234, 235, 239, 241, 242, 
/*  121 */       243, 244, 281, 318, 319, 320, 322, 324, 326, 327, 328, 329, 330, 331, 332, 
/*  122 */       335, 336, 342, 542 };
/*      */   
/*  124 */   public static String emailSubject = "PFM Push Error Message ";
/*  125 */   public static String emailBodyPrefix = "<br><b>PFM Push Processing Error</b><br><br>";
/*      */ 
/*      */   
/*  128 */   public String getPrefix() { return this.prefix; }
/*      */ 
/*      */ 
/*      */   
/*  132 */   public void setPrefix(String prefix) { this.prefix = prefix; } public PushPFM(boolean isDigital, String releaseType, String userID, boolean isAPNG) { this.releaseId = -1; this.matchReleaseId = -1; this.pushType = "PFM Push"; this.digitalPhysicalInd = ""; this.promoCommercialInd = ""; this.userId = ""; this.userName = ""; this.opCompany = ""; this.productNo = ""; this.configCode = ""; this.configMod = ""; this.title = ""; this.artist = ""; this.artistFirstName = ""; this.artistLastName = ""; this.titleId = ""; this.releaseDate = ""; this.digitalReleaseDate = ""; this.superLabel = ""; this.labelCode = ""; this.companyCode = ""; this.projectId = ""; this.poMergeCode = ""; this.unitsPerSet = 0; this.setsPerCarton = 0; this.supplierNo = ""; this.importInd = ""; this.upc = ""; this.soundScanGrpCode = ""; this.musicLine = ""; this.repertoireOwner = ""; this.repertoireClass = ""; this.scrapCode = ""; this.exportInd = ""; this.spineTitle = ""; this.spineArtist = ""; this.loosePickExemptInd = ""; this.guaranteeInd = ""; this.musicType = ""; this.impRateCode = ""; this.pricePoint = ""; this.naarmExtractInd = ""; this.sndtrkCode = ""; this.parentalAdvisoryInd = ""; this.boxSetInd = ""; this.valueAddedInd = ""; this.encryptionInd = ""; this.priceCode = ""; this.status = ""; this.error = "";
/*      */     this.message = "";
/*      */     this.appendToEmail = "";
/*      */     this.exception = false;
/*      */     this.IsDigital = false;
/*      */     this.IsNone = "[none]";
/*      */     this.IsAPNG = false;
/*      */     this.prefix = "";
/*  140 */     setIsDigital(isDigital);
/*      */ 
/*      */     
/*  143 */     setDigitalPhysicalInd();
/*  144 */     setPromoCommercialInd(releaseType);
/*  145 */     setUserId(userID);
/*      */ 
/*      */     
/*  148 */     setIsAPNG(isAPNG); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  154 */   public String getProgramId() { return "FM511N"; }
/*      */   public PushPFM() { this.releaseId = -1; this.matchReleaseId = -1; this.pushType = "PFM Push"; this.digitalPhysicalInd = ""; this.promoCommercialInd = ""; this.userId = ""; this.userName = ""; this.opCompany = ""; this.productNo = ""; this.configCode = ""; this.configMod = ""; this.title = ""; this.artist = ""; this.artistFirstName = ""; this.artistLastName = ""; this.titleId = ""; this.releaseDate = ""; this.digitalReleaseDate = ""; this.superLabel = ""; this.labelCode = ""; this.companyCode = ""; this.projectId = ""; this.poMergeCode = ""; this.unitsPerSet = 0; this.setsPerCarton = 0; this.supplierNo = ""; this.importInd = ""; this.upc = ""; this.soundScanGrpCode = ""; this.musicLine = ""; this.repertoireOwner = ""; this.repertoireClass = ""; this.scrapCode = ""; this.exportInd = ""; this.spineTitle = ""; this.spineArtist = ""; this.loosePickExemptInd = ""; this.guaranteeInd = ""; this.musicType = ""; this.impRateCode = ""; this.pricePoint = ""; this.naarmExtractInd = ""; this.sndtrkCode = ""; this.parentalAdvisoryInd = ""; this.boxSetInd = ""; this.valueAddedInd = ""; this.encryptionInd = ""; this.priceCode = ""; this.status = ""; this.error = ""; this.message = ""; this.appendToEmail = ""; this.exception = false; this.IsDigital = false; this.IsNone = "[none]";
/*      */     this.IsAPNG = false;
/*  157 */     this.prefix = ""; } public String getArtist() { return this.artist; }
/*      */ 
/*      */   
/*  160 */   public void setArtist(String artist) { this.artist = removeSpace(artist); }
/*      */ 
/*      */   
/*  163 */   public String getBoxSetInd() { return this.boxSetInd; }
/*      */ 
/*      */   
/*      */   public void setBoxSetInd(boolean boxSetInd) {
/*  167 */     if (this.IsDigital) {
/*  168 */       this.boxSetInd = "";
/*      */     } else {
/*  170 */       this.boxSetInd = boxSetInd ? "Y" : "";
/*      */     } 
/*      */   }
/*  173 */   public String getCompanyCode() { return this.companyCode; }
/*      */ 
/*      */   
/*  176 */   public void setCompanyCode(String companyCode) { this.companyCode = companyCode.equals("-1") ? "" : companyCode; }
/*      */ 
/*      */   
/*  179 */   public String getConfigMod() { return this.configMod; }
/*      */ 
/*      */   
/*  182 */   public String getConfigCode() { return this.configCode; }
/*      */ 
/*      */   
/*  185 */   public void setConfigCode(String configCode) { this.configCode = configCode.equals("-1") ? "" : configCode; }
/*      */ 
/*      */   
/*      */   public void setConfigMod(String configMod) {
/*  189 */     if (this.IsDigital) {
/*  190 */       this.configMod = "";
/*      */     
/*      */     }
/*      */     else {
/*      */       
/*  195 */       this.configMod = "";
/*      */     } 
/*      */   }
/*      */   
/*      */   public void setDigitalPhysicalInd() {
/*  200 */     if (this.IsDigital) {
/*  201 */       this.digitalPhysicalInd = "D";
/*      */     } else {
/*  203 */       this.digitalPhysicalInd = "P";
/*      */     } 
/*      */   }
/*      */   public void setDigitalReleaseDate(Date digitalReleaseDate) {
/*  207 */     if (digitalReleaseDate == null) {
/*  208 */       this.digitalReleaseDate = "123139";
/*      */     } else {
/*      */       
/*  211 */       SimpleDateFormat adf = new SimpleDateFormat("MMddyy");
/*  212 */       this.digitalReleaseDate = adf.format(digitalReleaseDate);
/*      */ 
/*      */       
/*  215 */       if (this.digitalReleaseDate.trim().equals("")) {
/*  216 */         this.digitalReleaseDate = "123139";
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public void setExportInd(String exportInd) {
/*  222 */     if (this.IsDigital) {
/*  223 */       this.exportInd = "N";
/*      */     } else {
/*  225 */       this.exportInd = exportInd.equals("-1") ? "" : exportInd;
/*      */     } 
/*      */   }
/*      */   public void setEncryptionInd(String encryptionInd) {
/*  229 */     if (this.IsDigital) {
/*  230 */       this.encryptionInd = "";
/*      */     } else {
/*  232 */       this.encryptionInd = encryptionInd;
/*      */     } 
/*      */   }
/*  235 */   public String getDigitalReleaseDate() { return this.digitalReleaseDate; }
/*      */ 
/*      */   
/*  238 */   public String getDigitalPhysicalInd() { return this.digitalPhysicalInd; }
/*      */ 
/*      */   
/*  241 */   public String getEncryptionInd() { return this.encryptionInd; }
/*      */ 
/*      */   
/*  244 */   public String getGuaranteeInd() { return this.guaranteeInd; }
/*      */ 
/*      */   
/*      */   public void setGuaranteeInd(String guaranteeInd) {
/*  248 */     if (this.IsDigital) {
/*  249 */       this.guaranteeInd = "N";
/*      */     } else {
/*  251 */       this.guaranteeInd = guaranteeInd;
/*      */     } 
/*      */   }
/*  254 */   public String getImportInd() { return this.importInd; }
/*      */ 
/*      */   
/*      */   public void setImportInd(String importInd) {
/*  258 */     if (this.IsDigital) {
/*  259 */       this.importInd = "D";
/*      */     } else {
/*  261 */       this.importInd = importInd;
/*      */     } 
/*      */   }
/*  264 */   public String getImpRateCode() { return this.impRateCode; }
/*      */ 
/*      */   
/*  267 */   public String getLabelCode() { return this.labelCode; }
/*      */ 
/*      */   
/*  270 */   public String getLoosePickExemptInd() { return this.loosePickExemptInd; }
/*      */ 
/*      */   
/*  273 */   public String getMessage() { return this.message; }
/*      */ 
/*      */   
/*  276 */   public String getMusicLine() { return this.musicLine; }
/*      */ 
/*      */   
/*  279 */   public String getMusicType() { return this.musicType; }
/*      */ 
/*      */   
/*  282 */   public String getNaarmExtractInd() { return this.naarmExtractInd; }
/*      */ 
/*      */   
/*  285 */   public String getOpCompany() { return this.opCompany; }
/*      */ 
/*      */   
/*  288 */   public String getParentalAdvisoryInd() { return this.parentalAdvisoryInd; }
/*      */ 
/*      */   
/*  291 */   public String getPromoCommercialInd() { return this.promoCommercialInd; }
/*      */ 
/*      */   
/*  294 */   public String getPoMergeCode() { return this.poMergeCode; }
/*      */ 
/*      */   
/*  297 */   public String getPriceCode() { return this.priceCode; }
/*      */ 
/*      */   
/*  300 */   public String getPricePoint() { return this.pricePoint; }
/*      */ 
/*      */   
/*  303 */   public String getProductNo() { return this.productNo; }
/*      */ 
/*      */   
/*  306 */   public String getProjectId() { return this.projectId; }
/*      */ 
/*      */   
/*  309 */   public String getReleaseDate() { return this.releaseDate; }
/*      */ 
/*      */   
/*  312 */   public String getRepertoireClass() { return this.repertoireClass; }
/*      */ 
/*      */   
/*  315 */   public String getRepertoireOwner() { return this.repertoireOwner; }
/*      */ 
/*      */   
/*  318 */   public String getTitle() { return this.title; }
/*      */ 
/*      */   
/*  321 */   public String getScrapCode() { return this.scrapCode; }
/*      */ 
/*      */   
/*  324 */   public int getSetsPerCarton() { return this.setsPerCarton; }
/*      */ 
/*      */   
/*  327 */   public String getSndtrkCode() { return this.sndtrkCode; }
/*      */ 
/*      */   
/*  330 */   public String getSoundScanGrpCode() { return this.soundScanGrpCode; }
/*      */ 
/*      */   
/*  333 */   public String getSpineArtist() { return this.spineArtist; }
/*      */ 
/*      */   
/*  336 */   public String getSpineTitle() { return this.spineTitle; }
/*      */ 
/*      */   
/*  339 */   public String getStatus() { return this.status; }
/*      */ 
/*      */   
/*  342 */   public String getSuperLabel() { return this.superLabel; }
/*      */ 
/*      */   
/*  345 */   public String getSupplierNo() { return this.supplierNo; }
/*      */ 
/*      */   
/*  348 */   public String getTitleId() { return this.titleId; }
/*      */ 
/*      */   
/*  351 */   public int getUnitsPerSet() { return this.unitsPerSet; }
/*      */ 
/*      */   
/*  354 */   public String getUpc() { return this.upc; }
/*      */ 
/*      */   
/*  357 */   public String getValueAddedInd() { return this.valueAddedInd; }
/*      */ 
/*      */   
/*      */   public void setValueAddedInd(boolean valueAddedInd) {
/*  361 */     if (this.IsDigital) {
/*  362 */       this.valueAddedInd = "";
/*      */     } else {
/*  364 */       this.valueAddedInd = valueAddedInd ? "Y" : "";
/*      */     } 
/*      */   }
/*      */   public void setUpc(String upc) {
/*  368 */     if (getPromoCommercialInd().equals("P") && upc.trim().equals("")) {
/*  369 */       this.upc = promoZeroUPCStr;
/*      */     } else {
/*  371 */       this.upc = MilestoneHelper_2.reformat_UPC_SSG_SGC_forSave(upc.trim(), "UPC", this.IsDigital, true);
/*      */     } 
/*      */   }
/*  374 */   public String getUserId() { return this.userId; }
/*      */ 
/*      */   
/*      */   public void setUnitsPerSet(int unitsPerSet) {
/*  378 */     if (this.IsDigital) {
/*  379 */       this.unitsPerSet = 1;
/*      */     } else {
/*  381 */       this.unitsPerSet = unitsPerSet;
/*      */     } 
/*      */   }
/*      */   public void setTitleId(String value) {
/*  385 */     String wrkStr = value.trim().replaceAll(" ", "");
/*  386 */     this.titleId = wrkStr.replaceAll("-", "");
/*      */   }
/*      */   
/*      */   public void setSupplierNo(String supplierNo) {
/*  390 */     if (this.IsDigital) {
/*  391 */       this.supplierNo = "";
/*      */     } else {
/*  393 */       this.supplierNo = supplierNo.trim().equals("-1") ? "" : supplierNo.trim();
/*      */     } 
/*      */   }
/*  396 */   public void setSuperLabel(String superLabel) { this.superLabel = superLabel; }
/*      */ 
/*      */   
/*      */   public void setStatus() {
/*  400 */     if (this.IsDigital) {
/*  401 */       this.status = "N";
/*      */     } else {
/*  403 */       this.status = "F";
/*      */     } 
/*      */   }
/*  406 */   public void setSpineTitle(String spineTitle) { this.spineTitle = spineTitle; }
/*      */ 
/*      */   
/*  409 */   public void setSpineArtist(String spineArtist) { this.spineArtist = spineArtist; }
/*      */ 
/*      */ 
/*      */   
/*  413 */   public void setSoundScanGrpCode(String soundScanGrpCode) { this.soundScanGrpCode = MilestoneHelper_2.reformat_UPC_SSG_SGC_forSave(soundScanGrpCode.trim(), "SSG", this.IsDigital, true); }
/*      */   
/*      */   public void setSndtrkCode(String sndtrkCode) {
/*  416 */     this.sndtrkCode = sndtrkCode.equals("-1") ? "" : sndtrkCode.trim();
/*      */ 
/*      */     
/*  419 */     if (this.sndtrkCode.length() == 2)
/*  420 */       this.sndtrkCode = this.sndtrkCode.substring(1); 
/*      */   }
/*      */   
/*      */   public void setSetsPerCarton(int setsPerCarton) {
/*  424 */     if (this.IsDigital) {
/*  425 */       this.setsPerCarton = 1;
/*      */     } else {
/*  427 */       this.setsPerCarton = setsPerCarton;
/*      */     } 
/*      */   }
/*      */   public void setScrapCode(String scrapCode) {
/*  431 */     if (this.IsDigital) {
/*  432 */       this.scrapCode = "T";
/*      */     } else {
/*  434 */       this.scrapCode = scrapCode.equals("-1") ? "" : scrapCode;
/*      */     } 
/*      */   }
/*  437 */   public void setTitle(String title) { this.title = title; }
/*      */ 
/*      */   
/*  440 */   public void setRepertoireOwner(String repertoireOwner) { this.repertoireOwner = repertoireOwner.equals("-1") ? "" : repertoireOwner; }
/*      */ 
/*      */   
/*  443 */   public void setRepertoireClass(String repertoireClass) { this.repertoireClass = repertoireClass.equals("-1") ? "" : repertoireClass; }
/*      */ 
/*      */   
/*      */   public void setReleaseDate(Date releaseDate) {
/*  447 */     if (releaseDate == null) {
/*  448 */       this.releaseDate = "123139";
/*      */     } else {
/*      */       
/*  451 */       SimpleDateFormat adf = new SimpleDateFormat("MMddyy");
/*  452 */       this.releaseDate = adf.format(releaseDate);
/*      */ 
/*      */       
/*  455 */       if (this.releaseDate.trim().equals("")) {
/*  456 */         this.releaseDate = "123139";
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*  461 */   public void setProjectId(String projectId) { this.projectId = projectId.replaceAll("-", ""); }
/*      */ 
/*      */   
/*      */   public void setProductNo(String productNo) {
/*  465 */     if (this.IsDigital) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  482 */       this.productNo = convertUPCtoProduct(getUpc());
/*      */ 
/*      */ 
/*      */     
/*      */     }
/*      */     else {
/*      */ 
/*      */ 
/*      */       
/*  491 */       String wrkStr = productNo.trim().replaceAll(" ", "");
/*  492 */       this.productNo = wrkStr.replaceAll("-", "");
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public String convertUPCtoProduct(String upc) {
/*  499 */     String product = upc.trim().replaceAll("-", "");
/*      */ 
/*      */     
/*  502 */     int len = product.length();
/*  503 */     if (len == 14) {
/*  504 */       product = upc.substring(3, 13);
/*  505 */     } else if (len == 13) {
/*  506 */       product = upc.substring(2, 12);
/*  507 */     } else if (len == 12) {
/*  508 */       product = upc.substring(1, 11);
/*      */     } 
/*  510 */     return product;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void setPricePoint(String pricePoint) {
/*  516 */     if (this.IsDigital) {
/*  517 */       this.pricePoint = "PS";
/*      */     } else {
/*  519 */       this.pricePoint = pricePoint.equals("-1") ? "" : pricePoint;
/*      */     } 
/*      */   }
/*      */   public void setPriceCode(String priceCode) {
/*  523 */     if (this.IsDigital || getPromoCommercialInd().equals("P")) {
/*  524 */       this.priceCode = "AAA";
/*      */     } else {
/*  526 */       this.priceCode = priceCode;
/*      */     } 
/*      */   }
/*      */   public void setPoMergeCode(String poMergeCode) {
/*  530 */     if (this.IsDigital) {
/*  531 */       this.poMergeCode = "99";
/*      */     } else {
/*  533 */       this.poMergeCode = poMergeCode.equals("-1") ? "" : poMergeCode;
/*      */     } 
/*      */   } public void setPromoCommercialInd(String releaseType) {
/*  536 */     if (releaseType.equals("CO")) {
/*  537 */       this.promoCommercialInd = "C";
/*      */     } else {
/*  539 */       this.promoCommercialInd = "P";
/*      */     } 
/*      */   }
/*  542 */   public void setParentalAdvisoryInd(boolean parentalAdvisoryInd) { this.parentalAdvisoryInd = parentalAdvisoryInd ? "Y" : ""; }
/*      */ 
/*      */   
/*  545 */   public void setOpCompany(String opCompany) { this.opCompany = opCompany; }
/*      */ 
/*      */   
/*      */   public void setNaarmExtractInd(String naarmExtractInd) {
/*  549 */     if (this.IsDigital) {
/*  550 */       this.naarmExtractInd = "N";
/*      */     } else {
/*  552 */       this.naarmExtractInd = naarmExtractInd;
/*      */     } 
/*      */   }
/*  555 */   public void setMusicType(String musicType) { this.musicType = musicType; }
/*      */ 
/*      */   
/*  558 */   public void setMusicLine(String musicLine) { this.musicLine = musicLine.equals("-1") ? "" : musicLine; }
/*      */ 
/*      */   
/*  561 */   public void setMessage(String message) { this.message = message; }
/*      */ 
/*      */   
/*      */   public void setLoosePickExemptInd(String loosePickExemptInd) {
/*  565 */     if (this.IsDigital) {
/*  566 */       this.loosePickExemptInd = "N";
/*      */     } else {
/*  568 */       this.loosePickExemptInd = loosePickExemptInd;
/*      */     } 
/*      */   }
/*  571 */   public void setLabelCode(String labelCode) { this.labelCode = labelCode; }
/*      */ 
/*      */   
/*      */   public void setImpRateCode(String impRateCode) {
/*  575 */     if (this.IsDigital) {
/*  576 */       this.impRateCode = "SI";
/*      */     } else {
/*  578 */       this.impRateCode = impRateCode.equals("-1") ? "" : impRateCode;
/*      */     } 
/*      */   }
/*  581 */   public String getExportInd() { return this.exportInd; }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean setPFM(Pfm pfm, Selection selection) {
/*  587 */     setReleaseId(pfm.getReleaseId());
/*      */ 
/*      */ 
/*      */     
/*  591 */     setUpc(pfm.getUpc());
/*  592 */     setPrefix(SelectionManager.getLookupObjectValue(selection.getPrefixID()));
/*      */ 
/*      */     
/*  595 */     setOpCompany(pfm.getOperatingCompany());
/*  596 */     setProductNo(pfm.getProductNumber());
/*  597 */     setConfigCode(pfm.getConfigCode());
/*  598 */     setConfigMod(pfm.getModifier());
/*  599 */     setTitle(pfm.getTitle());
/*  600 */     setArtist(pfm.getArtist());
/*  601 */     setArtistFirstName(selection.getArtistFirstName());
/*  602 */     setArtistLastName(selection.getArtistLastName());
/*      */ 
/*      */     
/*  605 */     if (this.IsDigital) {
/*  606 */       setTitleId("");
/*      */     } else {
/*  608 */       setTitleId(pfm.getTitleId());
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  614 */     String prodStatus = "";
/*  615 */     if (selection.getSelectionStatus() != null && selection.getSelectionStatus().getAbbreviation() != null) {
/*  616 */       prodStatus = selection.getSelectionStatus().getAbbreviation().toUpperCase();
/*      */     }
/*  618 */     if (this.IsDigital) {
/*      */ 
/*      */       
/*  621 */       if (pfm.getStreetDate() == null || prodStatus.equals(TBS)) {
/*  622 */         setDigitalReleaseDate(null);
/*      */       } else {
/*  624 */         setDigitalReleaseDate(pfm.getStreetDate().getTime());
/*      */       
/*      */       }
/*      */     
/*      */     }
/*  629 */     else if (pfm.getStreetDate() == null || prodStatus.equals(TBS)) {
/*  630 */       setReleaseDate(null);
/*      */     } else {
/*  632 */       setReleaseDate(pfm.getStreetDate().getTime());
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  638 */     setSuperLabel(pfm.getSuperLabel());
/*  639 */     setLabelCode(pfm.getLabelCode());
/*  640 */     setCompanyCode(pfm.getCompanyCode());
/*  641 */     setProjectId(pfm.getProjectID());
/*  642 */     setPoMergeCode(pfm.getPoMergeCode());
/*  643 */     setUnitsPerSet(pfm.getUnitsPerSet());
/*  644 */     setSetsPerCarton(pfm.getSetsPerCarton());
/*  645 */     setSupplierNo(pfm.getSupplier());
/*  646 */     setImportInd(pfm.getImportIndicator());
/*  647 */     setSoundScanGrpCode(pfm.getSoundScanGrp());
/*  648 */     setMusicLine(pfm.getMusicLine());
/*  649 */     setRepertoireOwner(pfm.getRepertoireOwner());
/*  650 */     setRepertoireClass(pfm.getRepertoireClass());
/*  651 */     setScrapCode(pfm.getReturnCode());
/*  652 */     setExportInd(pfm.getExportFlag());
/*  653 */     setSpineTitle(pfm.getSpineTitle());
/*  654 */     setSpineArtist(pfm.getSpineArtist());
/*  655 */     setLoosePickExemptInd(pfm.getLoosePickExemptCode());
/*  656 */     setGuaranteeInd(pfm.getGuaranteeCode());
/*      */     
/*  658 */     String musicType = (pfm.getMusicType() != null && !pfm.getMusicType().equals("")) ? pfm.getMusicType().getAbbreviation() : "";
/*  659 */     setMusicType(musicType);
/*  660 */     setImpRateCode(pfm.getImpRateCode());
/*  661 */     setPricePoint(pfm.getPricePoint());
/*  662 */     setNaarmExtractInd(pfm.getNarmFlag());
/*  663 */     setSndtrkCode(pfm.getCompilationCode());
/*  664 */     setParentalAdvisoryInd(pfm.getParentalGuidance());
/*  665 */     setBoxSetInd(pfm.getBoxSet());
/*  666 */     setValueAddedInd(pfm.getValueAdded());
/*  667 */     setEncryptionInd(pfm.getEncryptionFlag());
/*  668 */     setPriceCode(pfm.getPriceCode());
/*  669 */     setStatus();
/*      */     
/*  671 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String toString() {
/*  683 */     StringBuffer pMessage = new StringBuffer();
/*      */     
/*  685 */     pMessage.append(formatData(getProgramId(), 8, " "));
/*  686 */     pMessage.append(formatData(getUserId(), 8, " "));
/*  687 */     pMessage.append(formatData(getDigitalPhysicalInd(), 1, " "));
/*  688 */     pMessage.append(formatData(getPromoCommercialInd(), 1, " "));
/*  689 */     pMessage.append(formatData(getOpCompany(), 2, " "));
/*      */     
/*  691 */     pMessage.append(formatData(getSuperLabel(), 3, " "));
/*      */     
/*  693 */     pMessage.append(formatData(getConfigCode(), 2, " "));
/*  694 */     pMessage.append(formatData(getTitleId(), 10, " "));
/*  695 */     pMessage.append(formatData(getProductNo(), 10, " "));
/*  696 */     pMessage.append(formatData(getTitle(), 50, " "));
/*  697 */     pMessage.append(formatData(getArtist(), 50, " "));
/*  698 */     pMessage.append(formatData(getReleaseDate(), 6, " "));
/*  699 */     pMessage.append(formatData(getDigitalReleaseDate(), 6, " "));
/*  700 */     pMessage.append(formatData(getLabelCode(), 3, " "));
/*  701 */     pMessage.append(formatData(formatData(getProjectId(), 9, "0"), 12, " "));
/*  702 */     pMessage.append(formatData(getUpc(), 14, " "));
/*  703 */     pMessage.append(formatData(getSoundScanGrpCode(), 14, " "));
/*  704 */     pMessage.append(formatData(getCompanyCode(), 4, " "));
/*  705 */     pMessage.append(formatData(getConfigMod(), 2, " "));
/*  706 */     pMessage.append(formatData(getPoMergeCode(), 2, " "));
/*  707 */     pMessage.append(formatData((new Integer(getUnitsPerSet())).toString(), 3, "0"));
/*  708 */     pMessage.append(formatData((new Integer(getSetsPerCarton())).toString(), 3, "0"));
/*      */ 
/*      */ 
/*      */     
/*  712 */     pMessage.append(formatData(getSupplierNo(), 4, "0"));
/*  713 */     pMessage.append(formatData(new String(), 16, "0"));
/*      */     
/*  715 */     pMessage.append(formatData(getImportInd(), 1, " "));
/*  716 */     pMessage.append(formatData(getMusicLine(), 4, " "));
/*  717 */     pMessage.append(formatData(getRepertoireOwner(), 2, " "));
/*      */     
/*  719 */     pMessage.append(formatData(getRepertoireClassSubDesc(getRepertoireClass()), 1, " "));
/*  720 */     pMessage.append(formatData(getScrapCode(), 1, " "));
/*  721 */     pMessage.append(formatData(getExportInd(), 1, " "));
/*  722 */     pMessage.append(formatData(getSpineTitle(), 37, " "));
/*  723 */     pMessage.append(formatData(getSpineArtist(), 37, " "));
/*  724 */     pMessage.append(formatData(getLoosePickExemptInd(), 1, " "));
/*  725 */     pMessage.append(formatData(getGuaranteeInd(), 1, " "));
/*  726 */     pMessage.append(formatData(getMusicType(), 2, " "));
/*  727 */     pMessage.append(formatData(getImpRateCode(), 2, " "));
/*  728 */     pMessage.append(formatData(getPricePoint(), 2, " "));
/*  729 */     pMessage.append(formatData(getNaarmExtractInd(), 1, " "));
/*  730 */     pMessage.append(formatData(getSndtrkCode(), 1, " "));
/*  731 */     pMessage.append(formatData(getParentalAdvisoryInd(), 1, " "));
/*  732 */     pMessage.append(formatData(getBoxSetInd(), 1, " "));
/*  733 */     pMessage.append(formatData(getValueAddedInd(), 1, " "));
/*  734 */     pMessage.append(formatData(getEncryptionInd(), 1, " "));
/*  735 */     pMessage.append(formatData(getPriceCode(), 3, " "));
/*  736 */     pMessage.append(formatData(getStatus(), 1, " "));
/*      */     
/*  738 */     pMessage.append(formatData(String.valueOf(getReleaseId()), 6, "0"));
/*  739 */     pMessage.append(formatData(getError(), 200, " "));
/*      */ 
/*      */     
/*  742 */     setMessage(pMessage.toString());
/*      */ 
/*      */     
/*  745 */     PushCommunication.log(pMessage.toString());
/*      */ 
/*      */     
/*  748 */     return pMessage.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String formatData(String p1, int length, String filler) {
/*  760 */     String r1 = "";
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  765 */     if (p1 == null || p1.trim().equalsIgnoreCase("")) {
/*      */       
/*  767 */       for (int i = 0; i < length; i++) {
/*  768 */         r1 = String.valueOf(r1) + filler;
/*      */       }
/*  770 */       return r1;
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  776 */     if (p1.length() > length) {
/*  777 */       return p1.substring(0, length);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  786 */     for (int i = 0; i < length - p1.length(); i++) {
/*  787 */       r1 = String.valueOf(r1) + filler;
/*      */     }
/*      */     
/*  790 */     if (filler.equalsIgnoreCase("0")) {
/*  791 */       return String.valueOf(r1) + p1;
/*      */     }
/*  793 */     return String.valueOf(p1) + r1;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*  798 */   public void setUserId(String userId) { this.userId = userId; }
/*      */ 
/*      */   
/*  801 */   public boolean getIsAPNG() { return this.IsAPNG; }
/*      */ 
/*      */   
/*  804 */   public void setIsAPNG(boolean IsAPNG) { this.IsAPNG = IsAPNG; }
/*      */ 
/*      */   
/*  807 */   public boolean getIsDigital() { return this.IsDigital; }
/*      */ 
/*      */   
/*  810 */   public void setIsDigital(boolean IsDigital) { this.IsDigital = IsDigital; }
/*      */ 
/*      */   
/*  813 */   public String getPushType() { return this.pushType; }
/*      */ 
/*      */   
/*  816 */   public void setPushType(String pushType) { this.pushType = pushType; }
/*      */ 
/*      */   
/*  819 */   public String getError() { return this.error; }
/*      */ 
/*      */   
/*  822 */   public void setError(String error) { this.error = error; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Hashtable getPushElements() {
/*  829 */     if (pushElems != null) {
/*  830 */       return pushElems;
/*      */     }
/*      */     
/*  833 */     xmlUtil = new XMLUtil("pushbean.config");
/*      */     
/*  835 */     if (xmlUtil.getDocument() != null) {
/*      */ 
/*      */ 
/*      */       
/*  839 */       ArrayList elems = new ArrayList();
/*  840 */       elems = xmlUtil.getElementsValueByParentList(PFMPushElements, "element");
/*  841 */       pushElems = new Hashtable();
/*  842 */       for (int x = 0; x < elems.size(); x++)
/*  843 */         pushElems.put((String)elems.get(x), ""); 
/*      */     } else {
/*  845 */       PushCommunication.sendJavaMailOpt("", "", "** Unable to retrieve Push Elements", "Initialize", "");
/*      */     } 
/*  847 */     return pushElems;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Hashtable getPushExcludeFamilies() {
/*  854 */     if (PFMPushExclFamilies != null) {
/*  855 */       return PFMPushExclFamilies;
/*      */     }
/*      */     
/*  858 */     xmlUtil = new XMLUtil("pushbean.config");
/*      */     
/*  860 */     if (xmlUtil.getDocument() != null) {
/*      */ 
/*      */ 
/*      */       
/*  864 */       ArrayList elems = new ArrayList();
/*  865 */       elems = xmlUtil.getElementsValueByParentList(PFMPushElements, "exclude-family");
/*  866 */       PFMPushExclFamilies = new Hashtable();
/*  867 */       for (int x = 0; x < elems.size(); x++)
/*  868 */         PFMPushExclFamilies.put(elems.get(x).toString().trim().toUpperCase(), ""); 
/*      */     } else {
/*  870 */       PushCommunication.sendJavaMailOpt("", "", "** Unable to retrieve PFM Push Exclude Families **", "Initialize", "");
/*      */     } 
/*  872 */     return PFMPushExclFamilies;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*  877 */   public String getArtistFirstName() { return this.artistFirstName; }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  882 */   public void setArtistFirstName(String artistFirstName) { this.artistFirstName = artistFirstName; }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  887 */   public String getArtistLastName() { return this.artistLastName; }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  892 */   public void setArtistLastName(String artistLastName) { this.artistLastName = artistLastName; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getRepertoireClassSubDesc(String repClass) {
/*  899 */     String repClassSubDesc = "";
/*  900 */     LookupObject lookupObject = MilestoneHelper.getLookupObject(repClass, Cache.getRepertoireClasses());
/*  901 */     if (lookupObject != null)
/*  902 */       repClassSubDesc = lookupObject.getSubValue(); 
/*  903 */     return repClassSubDesc;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean setPFMPushBack(String buffer) {
/*  908 */     boolean result = true;
/*  909 */     StringBuffer resultErr = new StringBuffer("<br>Product#: " + getProductNo());
/*      */ 
/*      */     
/*  912 */     setMessage(buffer);
/*  913 */     setPushType("PFM Push Back");
/*      */ 
/*      */ 
/*      */     
/*  917 */     if (buffer.length() < 542) {
/*      */ 
/*      */       
/*  920 */       setError("SERVICE ERROR: ** PFM RECEIVE ** :  Received string length not equal to 542");
/*      */       
/*  922 */       return false;
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  931 */     this.digitalPhysicalInd = getElementStrValue(buffer, 2);
/*  932 */     if (this.digitalPhysicalInd.equals("D")) {
/*  933 */       setIsDigital(true);
/*      */     } else {
/*  935 */       setIsDigital(false);
/*      */     } 
/*      */     
/*  938 */     setUserId(getElementStrValue(buffer, 1));
/*      */     
/*  940 */     this.promoCommercialInd = getElementStrValue(buffer, 3);
/*  941 */     this.opCompany = getElementStrValue(buffer, 4);
/*  942 */     this.superLabel = getElementStrValue(buffer, 5);
/*  943 */     this.configCode = getElementStrValue(buffer, 6);
/*  944 */     this.titleId = getElementStrValue(buffer, 7);
/*  945 */     this.productNo = getElementStrValue(buffer, 8);
/*  946 */     this.title = getElementStrValue(buffer, 9);
/*  947 */     this.artist = getElementStrValue(buffer, 10);
/*      */     
/*      */     try {
/*  950 */       setReleaseDate(convertDate(getElementStrValue(buffer, 11)));
/*  951 */     } catch (Exception e) {
/*  952 */       resultErr.append("<br>Physical Release Date: " + e.toString());
/*  953 */       result = false;
/*      */     } 
/*      */     
/*      */     try {
/*  957 */       setDigitalReleaseDate(convertDate(getElementStrValue(buffer, 12)));
/*  958 */     } catch (Exception e) {
/*  959 */       resultErr.append("<br>Digital Release Date: " + e.toString());
/*  960 */       result = false;
/*      */     } 
/*  962 */     this.labelCode = getElementStrValue(buffer, 13);
/*  963 */     this.projectId = getElementStrValue(buffer, 14);
/*  964 */     this.upc = getElementStrValue(buffer, 15);
/*  965 */     PushCommunication.log(">>> UPC: " + this.upc);
/*  966 */     this.soundScanGrpCode = getElementStrValue(buffer, 16);
/*  967 */     PushCommunication.log(">>> SSG: " + this.soundScanGrpCode);
/*  968 */     this.companyCode = getElementStrValue(buffer, 17);
/*  969 */     this.configMod = getElementStrValue(buffer, 18);
/*  970 */     this.poMergeCode = getElementStrValue(buffer, 19);
/*      */     try {
/*  972 */       this.unitsPerSet = Integer.parseInt(getElementStrValue(buffer, 20).trim());
/*  973 */     } catch (Exception eu) {
/*  974 */       resultErr.append("<br>Units per set: " + eu.toString());
/*  975 */       result = false;
/*      */     } 
/*      */     try {
/*  978 */       this.setsPerCarton = Integer.parseInt(getElementStrValue(buffer, 21).trim());
/*  979 */     } catch (Exception es) {
/*      */       
/*  981 */       resultErr.append("<br>Sets per carton: " + es.toString());
/*  982 */       result = false;
/*      */     } 
/*  984 */     this.supplierNo = getElementStrValue(buffer, 22);
/*  985 */     this.importInd = getElementStrValue(buffer, 23);
/*  986 */     this.musicLine = getElementStrValue(buffer, 24);
/*  987 */     this.repertoireOwner = getElementStrValue(buffer, 25);
/*  988 */     this.repertoireClass = getElementStrValue(buffer, 26);
/*  989 */     this.scrapCode = getElementStrValue(buffer, 27);
/*  990 */     this.exportInd = getElementStrValue(buffer, 28);
/*  991 */     this.spineTitle = getElementStrValue(buffer, 29);
/*  992 */     this.spineArtist = getElementStrValue(buffer, 30);
/*  993 */     this.loosePickExemptInd = getElementStrValue(buffer, 31);
/*  994 */     this.guaranteeInd = getElementStrValue(buffer, 32);
/*  995 */     this.musicType = getElementStrValue(buffer, 33);
/*  996 */     this.impRateCode = getElementStrValue(buffer, 34);
/*  997 */     this.pricePoint = getElementStrValue(buffer, 35);
/*  998 */     this.naarmExtractInd = getElementStrValue(buffer, 36);
/*  999 */     this.sndtrkCode = getElementStrValue(buffer, 37);
/* 1000 */     this.parentalAdvisoryInd = getElementStrValue(buffer, 38);
/* 1001 */     this.boxSetInd = getElementStrValue(buffer, 39);
/* 1002 */     this.valueAddedInd = getElementStrValue(buffer, 40);
/* 1003 */     this.encryptionInd = getElementStrValue(buffer, 41);
/* 1004 */     this.priceCode = getElementStrValue(buffer, 42).toUpperCase();
/* 1005 */     this.status = getElementStrValue(buffer, 43);
/*      */     try {
/* 1007 */       setReleaseId(Integer.parseInt(getElementStrValue(buffer, 44).trim()));
/* 1008 */     } catch (Exception eu) {
/* 1009 */       resultErr.append("<br>Release Id: " + eu.toString());
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1015 */     setUserName(getElementStrValue(buffer, 45));
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1020 */     if (!result)
/*      */     {
/*      */       
/* 1023 */       setError("SERVICE ERROR: ** PFM RECEIVE **  Error found in Push PFM Push Back buffer <br>" + 
/* 1024 */           resultErr);
/*      */     }
/*      */     
/* 1027 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getElementStrValue(String buffer, int posInBuffer) {
/* 1035 */     int beg = elementPositions[posInBuffer];
/* 1036 */     int end = elementPositions[posInBuffer + 1];
/*      */ 
/*      */     
/* 1039 */     if (buffer.trim().equals("") || buffer.length() < end - 1) {
/* 1040 */       return "";
/*      */     }
/*      */     
/* 1043 */     return buffer.substring(beg, end);
/*      */   }
/*      */ 
/*      */   
/*      */   public Date convertDate(String dateStr) {
/* 1048 */     Date retDate = null;
/*      */ 
/*      */     
/* 1051 */     if (dateStr.trim().equals("")) {
/* 1052 */       return retDate;
/*      */     }
/* 1054 */     SimpleDateFormat adf = new SimpleDateFormat("MMddyy");
/*      */     try {
/* 1056 */       retDate = adf.parse(dateStr);
/* 1057 */     } catch (Exception e) {
/* 1058 */       PushCommunication.log("convertDate() exception: " + e.toString());
/*      */     } 
/* 1060 */     return retDate;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1067 */   private String removeSpace(String inStr) { return inStr.replaceAll(", ", ","); }
/*      */ 
/*      */ 
/*      */   
/* 1071 */   public int getReleaseId() { return this.releaseId; }
/*      */ 
/*      */ 
/*      */   
/* 1075 */   public void setReleaseId(int releaseId) { this.releaseId = releaseId; }
/*      */ 
/*      */ 
/*      */   
/* 1079 */   public int getMatchReleaseId() { return this.matchReleaseId; }
/*      */ 
/*      */ 
/*      */   
/* 1083 */   public void setMatchReleaseId(int matchReleaseId) { this.matchReleaseId = matchReleaseId; }
/*      */ 
/*      */ 
/*      */   
/* 1087 */   public String getAppendToEmail() { return this.appendToEmail; }
/*      */ 
/*      */ 
/*      */   
/* 1091 */   public void setAppendToEmail(String appendToEmail) { this.appendToEmail = appendToEmail; }
/*      */ 
/*      */ 
/*      */   
/* 1095 */   public boolean isException() { return this.exception; }
/*      */ 
/*      */ 
/*      */   
/* 1099 */   public void setException(boolean exception) { this.exception = exception; }
/*      */ 
/*      */ 
/*      */   
/* 1103 */   public String getUserName() { return this.userName; }
/*      */ 
/*      */ 
/*      */   
/* 1107 */   public void setUserName(String userName) { this.userName = userName; }
/*      */ }


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\push\PushPFM.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */