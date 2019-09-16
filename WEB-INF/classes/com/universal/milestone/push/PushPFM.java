package WEB-INF.classes.com.universal.milestone.push;

import com.universal.milestone.Cache;
import com.universal.milestone.LookupObject;
import com.universal.milestone.MilestoneHelper;
import com.universal.milestone.MilestoneHelper_2;
import com.universal.milestone.Pfm;
import com.universal.milestone.Selection;
import com.universal.milestone.SelectionManager;
import com.universal.milestone.push.PushCommunication;
import com.universal.milestone.push.PushPFM;
import com.universal.milestone.xml.XMLUtil;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;

public class PushPFM {
  public int releaseId;
  
  public int matchReleaseId;
  
  public static final String programID = "FM511N";
  
  public String pushType;
  
  public String digitalPhysicalInd;
  
  public String promoCommercialInd;
  
  public String userId;
  
  public String userName;
  
  public String opCompany;
  
  public String productNo;
  
  public String configCode;
  
  public String configMod;
  
  public String title;
  
  public String artist;
  
  public String artistFirstName;
  
  public String artistLastName;
  
  public String titleId;
  
  public String releaseDate;
  
  public String digitalReleaseDate;
  
  public String superLabel;
  
  public String labelCode;
  
  public String companyCode;
  
  public String projectId;
  
  public String poMergeCode;
  
  public int unitsPerSet;
  
  public int setsPerCarton;
  
  public String supplierNo;
  
  public String importInd;
  
  public String upc;
  
  public String soundScanGrpCode;
  
  public String musicLine;
  
  public String repertoireOwner;
  
  public String repertoireClass;
  
  public String scrapCode;
  
  public String exportInd;
  
  public String spineTitle;
  
  public String spineArtist;
  
  public String loosePickExemptInd;
  
  public String guaranteeInd;
  
  public String musicType;
  
  public String impRateCode;
  
  public String pricePoint;
  
  public String naarmExtractInd;
  
  public String sndtrkCode;
  
  public String parentalAdvisoryInd;
  
  public String boxSetInd;
  
  public String valueAddedInd;
  
  public String encryptionInd;
  
  public String priceCode;
  
  public String status;
  
  public String error;
  
  public String message;
  
  public String appendToEmail;
  
  public boolean exception;
  
  boolean IsDigital;
  
  String IsNone;
  
  boolean IsAPNG;
  
  public static final String IsBlank = "-1";
  
  public static final String PROMO = "P";
  
  public static final String COMMERCIAL = "C";
  
  public static final String DIGITAL = "D";
  
  public static final String PHYSICAL = "P";
  
  public String prefix;
  
  public static final String TBS_DATE = "123139";
  
  public static Hashtable pushElems = null;
  
  public static final String[] PFMPushElements = { "PFMPushElems" };
  
  public static Hashtable PFMPushExclFamilies = null;
  
  public static String promoZeroUPCStr = "00000000000000";
  
  public static String TBS = "TBS";
  
  public static int[] elementPositions = { 
      0, 8, 16, 17, 18, 20, 23, 25, 35, 45, 
      95, 145, 151, 
      157, 160, 172, 186, 200, 204, 206, 
      208, 211, 214, 234, 235, 239, 241, 242, 
      243, 244, 
      281, 318, 319, 320, 322, 324, 326, 327, 328, 329, 
      330, 331, 332, 
      335, 336, 342, 542 };
  
  public static String emailSubject = "PFM Push Error Message ";
  
  public static String emailBodyPrefix = "<br><b>PFM Push Processing Error</b><br><br>";
  
  public String getPrefix() { return this.prefix; }
  
  public void setPrefix(String prefix) { this.prefix = prefix; }
  
  public PushPFM(boolean isDigital, String releaseType, String userID, boolean isAPNG) {
    this.releaseId = -1;
    this.matchReleaseId = -1;
    this.pushType = "PFM Push";
    this.digitalPhysicalInd = "";
    this.promoCommercialInd = "";
    this.userId = "";
    this.userName = "";
    this.opCompany = "";
    this.productNo = "";
    this.configCode = "";
    this.configMod = "";
    this.title = "";
    this.artist = "";
    this.artistFirstName = "";
    this.artistLastName = "";
    this.titleId = "";
    this.releaseDate = "";
    this.digitalReleaseDate = "";
    this.superLabel = "";
    this.labelCode = "";
    this.companyCode = "";
    this.projectId = "";
    this.poMergeCode = "";
    this.unitsPerSet = 0;
    this.setsPerCarton = 0;
    this.supplierNo = "";
    this.importInd = "";
    this.upc = "";
    this.soundScanGrpCode = "";
    this.musicLine = "";
    this.repertoireOwner = "";
    this.repertoireClass = "";
    this.scrapCode = "";
    this.exportInd = "";
    this.spineTitle = "";
    this.spineArtist = "";
    this.loosePickExemptInd = "";
    this.guaranteeInd = "";
    this.musicType = "";
    this.impRateCode = "";
    this.pricePoint = "";
    this.naarmExtractInd = "";
    this.sndtrkCode = "";
    this.parentalAdvisoryInd = "";
    this.boxSetInd = "";
    this.valueAddedInd = "";
    this.encryptionInd = "";
    this.priceCode = "";
    this.status = "";
    this.error = "";
    this.message = "";
    this.appendToEmail = "";
    this.exception = false;
    this.IsDigital = false;
    this.IsNone = "[none]";
    this.IsAPNG = false;
    this.prefix = "";
    setIsDigital(isDigital);
    setDigitalPhysicalInd();
    setPromoCommercialInd(releaseType);
    setUserId(userID);
    setIsAPNG(isAPNG);
  }
  
  public PushPFM() {
    this.releaseId = -1;
    this.matchReleaseId = -1;
    this.pushType = "PFM Push";
    this.digitalPhysicalInd = "";
    this.promoCommercialInd = "";
    this.userId = "";
    this.userName = "";
    this.opCompany = "";
    this.productNo = "";
    this.configCode = "";
    this.configMod = "";
    this.title = "";
    this.artist = "";
    this.artistFirstName = "";
    this.artistLastName = "";
    this.titleId = "";
    this.releaseDate = "";
    this.digitalReleaseDate = "";
    this.superLabel = "";
    this.labelCode = "";
    this.companyCode = "";
    this.projectId = "";
    this.poMergeCode = "";
    this.unitsPerSet = 0;
    this.setsPerCarton = 0;
    this.supplierNo = "";
    this.importInd = "";
    this.upc = "";
    this.soundScanGrpCode = "";
    this.musicLine = "";
    this.repertoireOwner = "";
    this.repertoireClass = "";
    this.scrapCode = "";
    this.exportInd = "";
    this.spineTitle = "";
    this.spineArtist = "";
    this.loosePickExemptInd = "";
    this.guaranteeInd = "";
    this.musicType = "";
    this.impRateCode = "";
    this.pricePoint = "";
    this.naarmExtractInd = "";
    this.sndtrkCode = "";
    this.parentalAdvisoryInd = "";
    this.boxSetInd = "";
    this.valueAddedInd = "";
    this.encryptionInd = "";
    this.priceCode = "";
    this.status = "";
    this.error = "";
    this.message = "";
    this.appendToEmail = "";
    this.exception = false;
    this.IsDigital = false;
    this.IsNone = "[none]";
    this.IsAPNG = false;
    this.prefix = "";
  }
  
  public String getProgramId() { return "FM511N"; }
  
  public String getArtist() { return this.artist; }
  
  public void setArtist(String artist) { this.artist = removeSpace(artist); }
  
  public String getBoxSetInd() { return this.boxSetInd; }
  
  public void setBoxSetInd(boolean boxSetInd) {
    if (this.IsDigital) {
      this.boxSetInd = "";
    } else {
      this.boxSetInd = boxSetInd ? "Y" : "";
    } 
  }
  
  public String getCompanyCode() { return this.companyCode; }
  
  public void setCompanyCode(String companyCode) { this.companyCode = companyCode.equals("-1") ? "" : companyCode; }
  
  public String getConfigMod() { return this.configMod; }
  
  public String getConfigCode() { return this.configCode; }
  
  public void setConfigCode(String configCode) { this.configCode = configCode.equals("-1") ? "" : configCode; }
  
  public void setConfigMod(String configMod) {
    if (this.IsDigital) {
      this.configMod = "";
    } else {
      this.configMod = "";
    } 
  }
  
  public void setDigitalPhysicalInd() {
    if (this.IsDigital) {
      this.digitalPhysicalInd = "D";
    } else {
      this.digitalPhysicalInd = "P";
    } 
  }
  
  public void setDigitalReleaseDate(Date digitalReleaseDate) {
    if (digitalReleaseDate == null) {
      this.digitalReleaseDate = "123139";
    } else {
      SimpleDateFormat adf = new SimpleDateFormat("MMddyy");
      this.digitalReleaseDate = adf.format(digitalReleaseDate);
      if (this.digitalReleaseDate.trim().equals(""))
        this.digitalReleaseDate = "123139"; 
    } 
  }
  
  public void setExportInd(String exportInd) {
    if (this.IsDigital) {
      this.exportInd = "N";
    } else {
      this.exportInd = exportInd.equals("-1") ? "" : exportInd;
    } 
  }
  
  public void setEncryptionInd(String encryptionInd) {
    if (this.IsDigital) {
      this.encryptionInd = "";
    } else {
      this.encryptionInd = encryptionInd;
    } 
  }
  
  public String getDigitalReleaseDate() { return this.digitalReleaseDate; }
  
  public String getDigitalPhysicalInd() { return this.digitalPhysicalInd; }
  
  public String getEncryptionInd() { return this.encryptionInd; }
  
  public String getGuaranteeInd() { return this.guaranteeInd; }
  
  public void setGuaranteeInd(String guaranteeInd) {
    if (this.IsDigital) {
      this.guaranteeInd = "N";
    } else {
      this.guaranteeInd = guaranteeInd;
    } 
  }
  
  public String getImportInd() { return this.importInd; }
  
  public void setImportInd(String importInd) {
    if (this.IsDigital) {
      this.importInd = "D";
    } else {
      this.importInd = importInd;
    } 
  }
  
  public String getImpRateCode() { return this.impRateCode; }
  
  public String getLabelCode() { return this.labelCode; }
  
  public String getLoosePickExemptInd() { return this.loosePickExemptInd; }
  
  public String getMessage() { return this.message; }
  
  public String getMusicLine() { return this.musicLine; }
  
  public String getMusicType() { return this.musicType; }
  
  public String getNaarmExtractInd() { return this.naarmExtractInd; }
  
  public String getOpCompany() { return this.opCompany; }
  
  public String getParentalAdvisoryInd() { return this.parentalAdvisoryInd; }
  
  public String getPromoCommercialInd() { return this.promoCommercialInd; }
  
  public String getPoMergeCode() { return this.poMergeCode; }
  
  public String getPriceCode() { return this.priceCode; }
  
  public String getPricePoint() { return this.pricePoint; }
  
  public String getProductNo() { return this.productNo; }
  
  public String getProjectId() { return this.projectId; }
  
  public String getReleaseDate() { return this.releaseDate; }
  
  public String getRepertoireClass() { return this.repertoireClass; }
  
  public String getRepertoireOwner() { return this.repertoireOwner; }
  
  public String getTitle() { return this.title; }
  
  public String getScrapCode() { return this.scrapCode; }
  
  public int getSetsPerCarton() { return this.setsPerCarton; }
  
  public String getSndtrkCode() { return this.sndtrkCode; }
  
  public String getSoundScanGrpCode() { return this.soundScanGrpCode; }
  
  public String getSpineArtist() { return this.spineArtist; }
  
  public String getSpineTitle() { return this.spineTitle; }
  
  public String getStatus() { return this.status; }
  
  public String getSuperLabel() { return this.superLabel; }
  
  public String getSupplierNo() { return this.supplierNo; }
  
  public String getTitleId() { return this.titleId; }
  
  public int getUnitsPerSet() { return this.unitsPerSet; }
  
  public String getUpc() { return this.upc; }
  
  public String getValueAddedInd() { return this.valueAddedInd; }
  
  public void setValueAddedInd(boolean valueAddedInd) {
    if (this.IsDigital) {
      this.valueAddedInd = "";
    } else {
      this.valueAddedInd = valueAddedInd ? "Y" : "";
    } 
  }
  
  public void setUpc(String upc) {
    if (getPromoCommercialInd().equals("P") && upc.trim().equals("")) {
      this.upc = promoZeroUPCStr;
    } else {
      this.upc = MilestoneHelper_2.reformat_UPC_SSG_SGC_forSave(upc.trim(), "UPC", this.IsDigital, true);
    } 
  }
  
  public String getUserId() { return this.userId; }
  
  public void setUnitsPerSet(int unitsPerSet) {
    if (this.IsDigital) {
      this.unitsPerSet = 1;
    } else {
      this.unitsPerSet = unitsPerSet;
    } 
  }
  
  public void setTitleId(String value) {
    String wrkStr = value.trim().replaceAll(" ", "");
    this.titleId = wrkStr.replaceAll("-", "");
  }
  
  public void setSupplierNo(String supplierNo) {
    if (this.IsDigital) {
      this.supplierNo = "";
    } else {
      this.supplierNo = supplierNo.trim().equals("-1") ? "" : supplierNo.trim();
    } 
  }
  
  public void setSuperLabel(String superLabel) { this.superLabel = superLabel; }
  
  public void setStatus() {
    if (this.IsDigital) {
      this.status = "N";
    } else {
      this.status = "F";
    } 
  }
  
  public void setSpineTitle(String spineTitle) { this.spineTitle = spineTitle; }
  
  public void setSpineArtist(String spineArtist) { this.spineArtist = spineArtist; }
  
  public void setSoundScanGrpCode(String soundScanGrpCode) { this.soundScanGrpCode = MilestoneHelper_2.reformat_UPC_SSG_SGC_forSave(soundScanGrpCode.trim(), "SSG", this.IsDigital, true); }
  
  public void setSndtrkCode(String sndtrkCode) {
    this.sndtrkCode = sndtrkCode.equals("-1") ? "" : sndtrkCode.trim();
    if (this.sndtrkCode.length() == 2)
      this.sndtrkCode = this.sndtrkCode.substring(1); 
  }
  
  public void setSetsPerCarton(int setsPerCarton) {
    if (this.IsDigital) {
      this.setsPerCarton = 1;
    } else {
      this.setsPerCarton = setsPerCarton;
    } 
  }
  
  public void setScrapCode(String scrapCode) {
    if (this.IsDigital) {
      this.scrapCode = "T";
    } else {
      this.scrapCode = scrapCode.equals("-1") ? "" : scrapCode;
    } 
  }
  
  public void setTitle(String title) { this.title = title; }
  
  public void setRepertoireOwner(String repertoireOwner) { this.repertoireOwner = repertoireOwner.equals("-1") ? "" : repertoireOwner; }
  
  public void setRepertoireClass(String repertoireClass) { this.repertoireClass = repertoireClass.equals("-1") ? "" : repertoireClass; }
  
  public void setReleaseDate(Date releaseDate) {
    if (releaseDate == null) {
      this.releaseDate = "123139";
    } else {
      SimpleDateFormat adf = new SimpleDateFormat("MMddyy");
      this.releaseDate = adf.format(releaseDate);
      if (this.releaseDate.trim().equals(""))
        this.releaseDate = "123139"; 
    } 
  }
  
  public void setProjectId(String projectId) { this.projectId = projectId.replaceAll("-", ""); }
  
  public void setProductNo(String productNo) {
    if (this.IsDigital) {
      this.productNo = convertUPCtoProduct(getUpc());
    } else {
      String wrkStr = productNo.trim().replaceAll(" ", "");
      this.productNo = wrkStr.replaceAll("-", "");
    } 
  }
  
  public String convertUPCtoProduct(String upc) {
    String product = upc.trim().replaceAll("-", "");
    int len = product.length();
    if (len == 14) {
      product = upc.substring(3, 13);
    } else if (len == 13) {
      product = upc.substring(2, 12);
    } else if (len == 12) {
      product = upc.substring(1, 11);
    } 
    return product;
  }
  
  public void setPricePoint(String pricePoint) {
    if (this.IsDigital) {
      this.pricePoint = "PS";
    } else {
      this.pricePoint = pricePoint.equals("-1") ? "" : pricePoint;
    } 
  }
  
  public void setPriceCode(String priceCode) {
    if (this.IsDigital || getPromoCommercialInd().equals("P")) {
      this.priceCode = "AAA";
    } else {
      this.priceCode = priceCode;
    } 
  }
  
  public void setPoMergeCode(String poMergeCode) {
    if (this.IsDigital) {
      this.poMergeCode = "99";
    } else {
      this.poMergeCode = poMergeCode.equals("-1") ? "" : poMergeCode;
    } 
  }
  
  public void setPromoCommercialInd(String releaseType) {
    if (releaseType.equals("CO")) {
      this.promoCommercialInd = "C";
    } else {
      this.promoCommercialInd = "P";
    } 
  }
  
  public void setParentalAdvisoryInd(boolean parentalAdvisoryInd) { this.parentalAdvisoryInd = parentalAdvisoryInd ? "Y" : ""; }
  
  public void setOpCompany(String opCompany) { this.opCompany = opCompany; }
  
  public void setNaarmExtractInd(String naarmExtractInd) {
    if (this.IsDigital) {
      this.naarmExtractInd = "N";
    } else {
      this.naarmExtractInd = naarmExtractInd;
    } 
  }
  
  public void setMusicType(String musicType) { this.musicType = musicType; }
  
  public void setMusicLine(String musicLine) { this.musicLine = musicLine.equals("-1") ? "" : musicLine; }
  
  public void setMessage(String message) { this.message = message; }
  
  public void setLoosePickExemptInd(String loosePickExemptInd) {
    if (this.IsDigital) {
      this.loosePickExemptInd = "N";
    } else {
      this.loosePickExemptInd = loosePickExemptInd;
    } 
  }
  
  public void setLabelCode(String labelCode) { this.labelCode = labelCode; }
  
  public void setImpRateCode(String impRateCode) {
    if (this.IsDigital) {
      this.impRateCode = "SI";
    } else {
      this.impRateCode = impRateCode.equals("-1") ? "" : impRateCode;
    } 
  }
  
  public String getExportInd() { return this.exportInd; }
  
  public boolean setPFM(Pfm pfm, Selection selection) {
    setReleaseId(pfm.getReleaseId());
    setUpc(pfm.getUpc());
    setPrefix(SelectionManager.getLookupObjectValue(selection.getPrefixID()));
    setOpCompany(pfm.getOperatingCompany());
    setProductNo(pfm.getProductNumber());
    setConfigCode(pfm.getConfigCode());
    setConfigMod(pfm.getModifier());
    setTitle(pfm.getTitle());
    setArtist(pfm.getArtist());
    setArtistFirstName(selection.getArtistFirstName());
    setArtistLastName(selection.getArtistLastName());
    if (this.IsDigital) {
      setTitleId("");
    } else {
      setTitleId(pfm.getTitleId());
    } 
    String prodStatus = "";
    if (selection.getSelectionStatus() != null && selection.getSelectionStatus().getAbbreviation() != null)
      prodStatus = selection.getSelectionStatus().getAbbreviation().toUpperCase(); 
    if (this.IsDigital) {
      if (pfm.getStreetDate() == null || prodStatus.equals(TBS)) {
        setDigitalReleaseDate(null);
      } else {
        setDigitalReleaseDate(pfm.getStreetDate().getTime());
      } 
    } else if (pfm.getStreetDate() == null || prodStatus.equals(TBS)) {
      setReleaseDate(null);
    } else {
      setReleaseDate(pfm.getStreetDate().getTime());
    } 
    setSuperLabel(pfm.getSuperLabel());
    setLabelCode(pfm.getLabelCode());
    setCompanyCode(pfm.getCompanyCode());
    setProjectId(pfm.getProjectID());
    setPoMergeCode(pfm.getPoMergeCode());
    setUnitsPerSet(pfm.getUnitsPerSet());
    setSetsPerCarton(pfm.getSetsPerCarton());
    setSupplierNo(pfm.getSupplier());
    setImportInd(pfm.getImportIndicator());
    setSoundScanGrpCode(pfm.getSoundScanGrp());
    setMusicLine(pfm.getMusicLine());
    setRepertoireOwner(pfm.getRepertoireOwner());
    setRepertoireClass(pfm.getRepertoireClass());
    setScrapCode(pfm.getReturnCode());
    setExportInd(pfm.getExportFlag());
    setSpineTitle(pfm.getSpineTitle());
    setSpineArtist(pfm.getSpineArtist());
    setLoosePickExemptInd(pfm.getLoosePickExemptCode());
    setGuaranteeInd(pfm.getGuaranteeCode());
    String musicType = (pfm.getMusicType() != null && !pfm.getMusicType().equals("")) ? pfm.getMusicType().getAbbreviation() : "";
    setMusicType(musicType);
    setImpRateCode(pfm.getImpRateCode());
    setPricePoint(pfm.getPricePoint());
    setNaarmExtractInd(pfm.getNarmFlag());
    setSndtrkCode(pfm.getCompilationCode());
    setParentalAdvisoryInd(pfm.getParentalGuidance());
    setBoxSetInd(pfm.getBoxSet());
    setValueAddedInd(pfm.getValueAdded());
    setEncryptionInd(pfm.getEncryptionFlag());
    setPriceCode(pfm.getPriceCode());
    setStatus();
    return true;
  }
  
  public String toString() {
    StringBuffer pMessage = new StringBuffer();
    pMessage.append(formatData(getProgramId(), 8, " "));
    pMessage.append(formatData(getUserId(), 8, " "));
    pMessage.append(formatData(getDigitalPhysicalInd(), 1, " "));
    pMessage.append(formatData(getPromoCommercialInd(), 1, " "));
    pMessage.append(formatData(getOpCompany(), 2, " "));
    pMessage.append(formatData(getSuperLabel(), 3, " "));
    pMessage.append(formatData(getConfigCode(), 2, " "));
    pMessage.append(formatData(getTitleId(), 10, " "));
    pMessage.append(formatData(getProductNo(), 10, " "));
    pMessage.append(formatData(getTitle(), 50, " "));
    pMessage.append(formatData(getArtist(), 50, " "));
    pMessage.append(formatData(getReleaseDate(), 6, " "));
    pMessage.append(formatData(getDigitalReleaseDate(), 6, " "));
    pMessage.append(formatData(getLabelCode(), 3, " "));
    pMessage.append(formatData(formatData(getProjectId(), 9, "0"), 12, " "));
    pMessage.append(formatData(getUpc(), 14, " "));
    pMessage.append(formatData(getSoundScanGrpCode(), 14, " "));
    pMessage.append(formatData(getCompanyCode(), 4, " "));
    pMessage.append(formatData(getConfigMod(), 2, " "));
    pMessage.append(formatData(getPoMergeCode(), 2, " "));
    pMessage.append(formatData((new Integer(getUnitsPerSet())).toString(), 3, "0"));
    pMessage.append(formatData((new Integer(getSetsPerCarton())).toString(), 3, "0"));
    pMessage.append(formatData(getSupplierNo(), 4, "0"));
    pMessage.append(formatData(new String(), 16, "0"));
    pMessage.append(formatData(getImportInd(), 1, " "));
    pMessage.append(formatData(getMusicLine(), 4, " "));
    pMessage.append(formatData(getRepertoireOwner(), 2, " "));
    pMessage.append(formatData(getRepertoireClassSubDesc(getRepertoireClass()), 1, " "));
    pMessage.append(formatData(getScrapCode(), 1, " "));
    pMessage.append(formatData(getExportInd(), 1, " "));
    pMessage.append(formatData(getSpineTitle(), 37, " "));
    pMessage.append(formatData(getSpineArtist(), 37, " "));
    pMessage.append(formatData(getLoosePickExemptInd(), 1, " "));
    pMessage.append(formatData(getGuaranteeInd(), 1, " "));
    pMessage.append(formatData(getMusicType(), 2, " "));
    pMessage.append(formatData(getImpRateCode(), 2, " "));
    pMessage.append(formatData(getPricePoint(), 2, " "));
    pMessage.append(formatData(getNaarmExtractInd(), 1, " "));
    pMessage.append(formatData(getSndtrkCode(), 1, " "));
    pMessage.append(formatData(getParentalAdvisoryInd(), 1, " "));
    pMessage.append(formatData(getBoxSetInd(), 1, " "));
    pMessage.append(formatData(getValueAddedInd(), 1, " "));
    pMessage.append(formatData(getEncryptionInd(), 1, " "));
    pMessage.append(formatData(getPriceCode(), 3, " "));
    pMessage.append(formatData(getStatus(), 1, " "));
    pMessage.append(formatData(String.valueOf(getReleaseId()), 6, "0"));
    pMessage.append(formatData(getError(), 200, " "));
    setMessage(pMessage.toString());
    PushCommunication.log(pMessage.toString());
    return pMessage.toString();
  }
  
  public String formatData(String p1, int length, String filler) {
    String r1 = "";
    if (p1 == null || p1.trim().equalsIgnoreCase("")) {
      for (int i = 0; i < length; i++)
        r1 = String.valueOf(r1) + filler; 
      return r1;
    } 
    if (p1.length() > length)
      return p1.substring(0, length); 
    for (int i = 0; i < length - p1.length(); i++)
      r1 = String.valueOf(r1) + filler; 
    if (filler.equalsIgnoreCase("0"))
      return String.valueOf(r1) + p1; 
    return String.valueOf(p1) + r1;
  }
  
  public void setUserId(String userId) { this.userId = userId; }
  
  public boolean getIsAPNG() { return this.IsAPNG; }
  
  public void setIsAPNG(boolean IsAPNG) { this.IsAPNG = IsAPNG; }
  
  public boolean getIsDigital() { return this.IsDigital; }
  
  public void setIsDigital(boolean IsDigital) { this.IsDigital = IsDigital; }
  
  public String getPushType() { return this.pushType; }
  
  public void setPushType(String pushType) { this.pushType = pushType; }
  
  public String getError() { return this.error; }
  
  public void setError(String error) { this.error = error; }
  
  public static Hashtable getPushElements() {
    if (pushElems != null)
      return pushElems; 
    xmlUtil = new XMLUtil("pushbean.config");
    if (xmlUtil.getDocument() != null) {
      ArrayList elems = new ArrayList();
      elems = xmlUtil.getElementsValueByParentList(PFMPushElements, "element");
      pushElems = new Hashtable();
      for (int x = 0; x < elems.size(); x++)
        pushElems.put((String)elems.get(x), ""); 
    } else {
      PushCommunication.sendJavaMailOpt("", "", "** Unable to retrieve Push Elements", "Initialize", "");
    } 
    return pushElems;
  }
  
  public static Hashtable getPushExcludeFamilies() {
    if (PFMPushExclFamilies != null)
      return PFMPushExclFamilies; 
    xmlUtil = new XMLUtil("pushbean.config");
    if (xmlUtil.getDocument() != null) {
      ArrayList elems = new ArrayList();
      elems = xmlUtil.getElementsValueByParentList(PFMPushElements, "exclude-family");
      PFMPushExclFamilies = new Hashtable();
      for (int x = 0; x < elems.size(); x++)
        PFMPushExclFamilies.put(elems.get(x).toString().trim().toUpperCase(), ""); 
    } else {
      PushCommunication.sendJavaMailOpt("", "", "** Unable to retrieve PFM Push Exclude Families **", "Initialize", "");
    } 
    return PFMPushExclFamilies;
  }
  
  public String getArtistFirstName() { return this.artistFirstName; }
  
  public void setArtistFirstName(String artistFirstName) { this.artistFirstName = artistFirstName; }
  
  public String getArtistLastName() { return this.artistLastName; }
  
  public void setArtistLastName(String artistLastName) { this.artistLastName = artistLastName; }
  
  public String getRepertoireClassSubDesc(String repClass) {
    String repClassSubDesc = "";
    LookupObject lookupObject = MilestoneHelper.getLookupObject(repClass, Cache.getRepertoireClasses());
    if (lookupObject != null)
      repClassSubDesc = lookupObject.getSubValue(); 
    return repClassSubDesc;
  }
  
  public boolean setPFMPushBack(String buffer) {
    boolean result = true;
    StringBuffer resultErr = new StringBuffer("<br>Product#: " + getProductNo());
    setMessage(buffer);
    setPushType("PFM Push Back");
    if (buffer.length() < 542) {
      setError("SERVICE ERROR: ** PFM RECEIVE ** :  Received string length not equal to 542");
      return false;
    } 
    this.digitalPhysicalInd = getElementStrValue(buffer, 2);
    if (this.digitalPhysicalInd.equals("D")) {
      setIsDigital(true);
    } else {
      setIsDigital(false);
    } 
    setUserId(getElementStrValue(buffer, 1));
    this.promoCommercialInd = getElementStrValue(buffer, 3);
    this.opCompany = getElementStrValue(buffer, 4);
    this.superLabel = getElementStrValue(buffer, 5);
    this.configCode = getElementStrValue(buffer, 6);
    this.titleId = getElementStrValue(buffer, 7);
    this.productNo = getElementStrValue(buffer, 8);
    this.title = getElementStrValue(buffer, 9);
    this.artist = getElementStrValue(buffer, 10);
    try {
      setReleaseDate(convertDate(getElementStrValue(buffer, 11)));
    } catch (Exception e) {
      resultErr.append("<br>Physical Release Date: " + e.toString());
      result = false;
    } 
    try {
      setDigitalReleaseDate(convertDate(getElementStrValue(buffer, 12)));
    } catch (Exception e) {
      resultErr.append("<br>Digital Release Date: " + e.toString());
      result = false;
    } 
    this.labelCode = getElementStrValue(buffer, 13);
    this.projectId = getElementStrValue(buffer, 14);
    this.upc = getElementStrValue(buffer, 15);
    PushCommunication.log(">>> UPC: " + this.upc);
    this.soundScanGrpCode = getElementStrValue(buffer, 16);
    PushCommunication.log(">>> SSG: " + this.soundScanGrpCode);
    this.companyCode = getElementStrValue(buffer, 17);
    this.configMod = getElementStrValue(buffer, 18);
    this.poMergeCode = getElementStrValue(buffer, 19);
    try {
      this.unitsPerSet = Integer.parseInt(getElementStrValue(buffer, 20).trim());
    } catch (Exception eu) {
      resultErr.append("<br>Units per set: " + eu.toString());
      result = false;
    } 
    try {
      this.setsPerCarton = Integer.parseInt(getElementStrValue(buffer, 21).trim());
    } catch (Exception es) {
      resultErr.append("<br>Sets per carton: " + es.toString());
      result = false;
    } 
    this.supplierNo = getElementStrValue(buffer, 22);
    this.importInd = getElementStrValue(buffer, 23);
    this.musicLine = getElementStrValue(buffer, 24);
    this.repertoireOwner = getElementStrValue(buffer, 25);
    this.repertoireClass = getElementStrValue(buffer, 26);
    this.scrapCode = getElementStrValue(buffer, 27);
    this.exportInd = getElementStrValue(buffer, 28);
    this.spineTitle = getElementStrValue(buffer, 29);
    this.spineArtist = getElementStrValue(buffer, 30);
    this.loosePickExemptInd = getElementStrValue(buffer, 31);
    this.guaranteeInd = getElementStrValue(buffer, 32);
    this.musicType = getElementStrValue(buffer, 33);
    this.impRateCode = getElementStrValue(buffer, 34);
    this.pricePoint = getElementStrValue(buffer, 35);
    this.naarmExtractInd = getElementStrValue(buffer, 36);
    this.sndtrkCode = getElementStrValue(buffer, 37);
    this.parentalAdvisoryInd = getElementStrValue(buffer, 38);
    this.boxSetInd = getElementStrValue(buffer, 39);
    this.valueAddedInd = getElementStrValue(buffer, 40);
    this.encryptionInd = getElementStrValue(buffer, 41);
    this.priceCode = getElementStrValue(buffer, 42).toUpperCase();
    this.status = getElementStrValue(buffer, 43);
    try {
      setReleaseId(Integer.parseInt(getElementStrValue(buffer, 44).trim()));
    } catch (Exception eu) {
      resultErr.append("<br>Release Id: " + eu.toString());
    } 
    setUserName(getElementStrValue(buffer, 45));
    if (!result)
      setError("SERVICE ERROR: ** PFM RECEIVE **  Error found in Push PFM Push Back buffer <br>" + 
          resultErr); 
    return result;
  }
  
  public String getElementStrValue(String buffer, int posInBuffer) {
    int beg = elementPositions[posInBuffer];
    int end = elementPositions[posInBuffer + 1];
    if (buffer.trim().equals("") || buffer.length() < end - 1)
      return ""; 
    return buffer.substring(beg, end);
  }
  
  public Date convertDate(String dateStr) {
    Date retDate = null;
    if (dateStr.trim().equals(""))
      return retDate; 
    SimpleDateFormat adf = new SimpleDateFormat("MMddyy");
    try {
      retDate = adf.parse(dateStr);
    } catch (Exception e) {
      PushCommunication.log("convertDate() exception: " + e.toString());
    } 
    return retDate;
  }
  
  private String removeSpace(String inStr) { return inStr.replaceAll(", ", ","); }
  
  public int getReleaseId() { return this.releaseId; }
  
  public void setReleaseId(int releaseId) { this.releaseId = releaseId; }
  
  public int getMatchReleaseId() { return this.matchReleaseId; }
  
  public void setMatchReleaseId(int matchReleaseId) { this.matchReleaseId = matchReleaseId; }
  
  public String getAppendToEmail() { return this.appendToEmail; }
  
  public void setAppendToEmail(String appendToEmail) { this.appendToEmail = appendToEmail; }
  
  public boolean isException() { return this.exception; }
  
  public void setException(boolean exception) { this.exception = exception; }
  
  public String getUserName() { return this.userName; }
  
  public void setUserName(String userName) { this.userName = userName; }
}


/* Location:              D:\Documents\NetBeansProjects\milestone2 Local.war!\WEB-INF\classes\co\\universal\milestone\push\PushPFM.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */