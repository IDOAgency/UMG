package WEB-INF.classes.com.universal.milestone;

import com.universal.milestone.Genre;
import com.universal.milestone.Pfm;
import com.universal.milestone.PrefixCode;
import com.universal.milestone.Selection;
import java.util.Calendar;

public class Pfm implements Cloneable {
  protected int mnReleaseId;
  
  protected String releaseType;
  
  protected String narmFlag;
  
  protected String mstrPreparedBy;
  
  protected String mstrEmail;
  
  protected String mstrPhone;
  
  protected String mstrFaxNumber;
  
  protected String mstrOperatingCompany;
  
  protected String mlProductNumber;
  
  protected String mstrComments;
  
  protected String mstrConfigCode;
  
  protected String mstrModifier;
  
  protected String mstrTitle;
  
  protected String mstrArtist;
  
  protected String mstrTitleId;
  
  protected String mstrSuperLabel;
  
  protected String mstrLabelCode;
  
  protected String mstrCompanyCode;
  
  protected String mstrPoMergeCode;
  
  protected int mnUnitsPerSet;
  
  protected int mnSetsPerCarton;
  
  protected String mstrSupplier;
  
  protected String mstrImportIndicator;
  
  protected String mstrUpc;
  
  protected String mstrMusicLine;
  
  protected String mstrRepertoireOwner;
  
  protected String mstrRepertoireClass;
  
  protected String mstrReturnCode;
  
  protected String mstrExportFlag;
  
  protected String mstrCountries;
  
  protected String mstrSpineTitle;
  
  protected String mstrSpineArtist;
  
  protected String mstrPriceCode;
  
  protected String mstrPriceCodeDPC;
  
  protected String mstrGuaranteeCode;
  
  protected String mstrLoosePickExemptCode;
  
  protected String mstrCompilationCode;
  
  protected boolean mbParentalAdv;
  
  protected boolean mbValueAdded;
  
  protected boolean mbBoxSet;
  
  protected String mstrImpRateCode;
  
  protected Genre mstrMusicType;
  
  protected String mstrPricePoint;
  
  protected String mode;
  
  protected String printOption;
  
  protected int lastUpdatingUser;
  
  protected Calendar lastUpdatedDate;
  
  protected long lastUpdatedCk;
  
  protected String mstrApprovedByName;
  
  protected String mstrEnteredByName;
  
  protected Calendar enteredDate;
  
  protected String mstrVerifiedByName;
  
  private Selection selection = null;
  
  protected String changeNumber = "";
  
  protected Calendar streetDate = null;
  
  protected String projectID = "";
  
  protected boolean parentalGuidance;
  
  protected String soundscan_grp = "";
  
  protected String encryption_flag = "";
  
  protected String mstrStatus;
  
  protected PrefixCode prefixID = null;
  
  protected String selectionNo = "";
  
  Pfm(Selection selection) {}
  
  public String getReleaseType() { return this.releaseType; }
  
  public void setReleaseType(String releaseType) { this.releaseType = releaseType; }
  
  public void setNarmFlag(String narmFlag) { this.narmFlag = narmFlag; }
  
  public String getNarmFlag() { return this.narmFlag; }
  
  public String getPreparedBy() { return this.mstrPreparedBy; }
  
  public void setPreparedBy(String preparedBy) { this.mstrPreparedBy = preparedBy; }
  
  public String getEmail() { return this.mstrEmail; }
  
  public void setEmail(String email) { this.mstrEmail = email; }
  
  public String getPhone() { return this.mstrPhone; }
  
  public void setPhone(String phone) { this.mstrPhone = phone; }
  
  public String getFaxNumber() { return this.mstrFaxNumber; }
  
  public void setFaxNumber(String faxNumber) { this.mstrFaxNumber = faxNumber; }
  
  public String getOperatingCompany() { return this.mstrOperatingCompany; }
  
  public void setOperatingCompany(String operatingCompany) { this.mstrOperatingCompany = operatingCompany; }
  
  public String getProductNumber() { return this.mlProductNumber; }
  
  public void setProductNumber(String productNumber) { this.mlProductNumber = productNumber; }
  
  public int getReleaseId() { return this.mnReleaseId; }
  
  public void setReleaseId(int releaseId) { this.mnReleaseId = releaseId; }
  
  public String getComments() { return this.mstrComments; }
  
  public void setComments(String comments) { this.mstrComments = comments; }
  
  public String getConfigCode() { return this.mstrConfigCode; }
  
  public void setConfigCode(String configCode) { this.mstrConfigCode = configCode; }
  
  public String getModifier() { return this.mstrModifier; }
  
  public void setModifier(String modifier) { this.mstrModifier = modifier; }
  
  public String getTitle() { return this.mstrTitle; }
  
  public void setTitle(String title) { this.mstrTitle = title; }
  
  public String getArtist() { return this.mstrArtist; }
  
  public void setArtist(String artist) { this.mstrArtist = artist; }
  
  public String getTitleId() { return this.mstrTitleId; }
  
  public void setTitleId(String titleId) { this.mstrTitleId = titleId; }
  
  public String getSuperLabel() { return this.mstrSuperLabel; }
  
  public void setSuperLabel(String pSuperLabel) { this.mstrSuperLabel = pSuperLabel; }
  
  public String getLabelCode() { return this.mstrLabelCode; }
  
  public void setLabelCode(String pLabelCode) { this.mstrLabelCode = pLabelCode; }
  
  public String getCompanyCode() { return this.mstrCompanyCode; }
  
  public void setCompanyCode(String pCompanyCode) { this.mstrCompanyCode = pCompanyCode; }
  
  public String getPoMergeCode() { return this.mstrPoMergeCode; }
  
  public void setPoMergeCode(String pPoMergeCode) { this.mstrPoMergeCode = pPoMergeCode; }
  
  public int getUnitsPerSet() { return this.mnUnitsPerSet; }
  
  public void setUnitsPerSet(int pUnitsPerSet) { this.mnUnitsPerSet = pUnitsPerSet; }
  
  public int getSetsPerCarton() { return this.mnSetsPerCarton; }
  
  public void setSetsPerCarton(int pSetsPerCarton) { this.mnSetsPerCarton = pSetsPerCarton; }
  
  public String getSupplier() { return this.mstrSupplier; }
  
  public void setSupplier(String pSupplier) { this.mstrSupplier = pSupplier; }
  
  public String getImportIndicator() { return this.mstrImportIndicator; }
  
  public void setImportIndicator(String pImportIndicator) { this.mstrImportIndicator = pImportIndicator; }
  
  public String getUpc() { return this.mstrUpc; }
  
  public void setUpc(String pUpc) { this.mstrUpc = pUpc; }
  
  public String getMusicLine() { return this.mstrMusicLine; }
  
  public void setMusicLine(String pMusicLine) { this.mstrMusicLine = pMusicLine; }
  
  public String getRepertoireOwner() { return this.mstrRepertoireOwner; }
  
  public void setRepertoireOwner(String pRepertoireOwner) { this.mstrRepertoireOwner = pRepertoireOwner; }
  
  public String getRepertoireClass() { return this.mstrRepertoireClass; }
  
  public void setRepertoireClass(String pRepertoireClass) { this.mstrRepertoireClass = pRepertoireClass; }
  
  public String getReturnCode() { return this.mstrReturnCode; }
  
  public void setReturnCode(String pReturnCode) { this.mstrReturnCode = pReturnCode; }
  
  public String getExportFlag() { return this.mstrExportFlag; }
  
  public void setExportFlag(String pExportFlag) { this.mstrExportFlag = pExportFlag; }
  
  public String getCountries() { return this.mstrCountries; }
  
  public void setMode(String mode) { this.mode = mode; }
  
  public String getMode() { return this.mode; }
  
  public void setPrintOption(String printOption) { this.printOption = printOption; }
  
  public String getPrintOption() { return this.printOption; }
  
  public void setCountries(String pCountries) { this.mstrCountries = pCountries; }
  
  public String getSpineTitle() { return this.mstrSpineTitle; }
  
  public void setSpineTitle(String pSpineTitle) { this.mstrSpineTitle = pSpineTitle; }
  
  public String getSpineArtist() { return this.mstrSpineArtist; }
  
  public void setSpineArtist(String pSpineArtist) { this.mstrSpineArtist = pSpineArtist; }
  
  public String getPriceCode() { return this.mstrPriceCode; }
  
  public void setPriceCode(String pPriceCode) { this.mstrPriceCode = pPriceCode; }
  
  public String getPriceCodeDPC() { return this.mstrPriceCodeDPC; }
  
  public void setPriceCodeDPC(String pPriceCodeDPC) { this.mstrPriceCodeDPC = pPriceCodeDPC; }
  
  public String getGuaranteeCode() { return this.mstrGuaranteeCode; }
  
  public String getLoosePickExemptCode() { return this.mstrLoosePickExemptCode; }
  
  public String getCompilationCode() { return this.mstrCompilationCode; }
  
  public boolean getBoxSet() { return this.mbBoxSet; }
  
  public boolean getValueAdded() { return this.mbValueAdded; }
  
  public void setGuaranteeCode(String pGuaranteeCode) { this.mstrGuaranteeCode = pGuaranteeCode; }
  
  public void setLoosePickExemptCode(String pLoosePickExemptCode) { this.mstrLoosePickExemptCode = pLoosePickExemptCode; }
  
  public void setCompilationCode(String pCompilationCode) { this.mstrCompilationCode = pCompilationCode; }
  
  public void setBoxSet(boolean pBoxSet) { this.mbBoxSet = pBoxSet; }
  
  public void setValueAdded(boolean pValueAdded) { this.mbValueAdded = pValueAdded; }
  
  public String getImpRateCode() { return this.mstrImpRateCode; }
  
  public void setImpRateCode(String pImpRateCode) { this.mstrImpRateCode = pImpRateCode; }
  
  public Genre getMusicType() { return this.mstrMusicType; }
  
  public void setMusicType(Genre pMusicType) { this.mstrMusicType = pMusicType; }
  
  public String getPricePoint() { return this.mstrPricePoint; }
  
  public void setPricePoint(String pPricePoint) { this.mstrPricePoint = pPricePoint; }
  
  public Calendar getLastUpdatedDate() { return this.lastUpdatedDate; }
  
  public void setLastUpdatedDate(Calendar lastUpdatedDate) { this.lastUpdatedDate = lastUpdatedDate; }
  
  public int getLastUpdatingUser() { return this.lastUpdatingUser; }
  
  public void setLastUpdatingUser(int lastUpdatingUser) { this.lastUpdatingUser = lastUpdatingUser; }
  
  public long getLastUpdatedCk() { return this.lastUpdatedCk; }
  
  public void setLastUpdatedCk(long lastUpdatedCk) { this.lastUpdatedCk = lastUpdatedCk; }
  
  public Selection getSelection() { return this.selection; }
  
  public void setSelection(Selection value) { this.selection = value; }
  
  public String getApprovedByName() { return this.mstrApprovedByName; }
  
  public void setApprovedByName(String pApprovedByName) { this.mstrApprovedByName = pApprovedByName; }
  
  public String getEnteredByName() { return this.mstrEnteredByName; }
  
  public void setEnteredByName(String pEnteredByName) { this.mstrEnteredByName = pEnteredByName; }
  
  public Calendar getEnteredDate() { return this.enteredDate; }
  
  public void setEnteredDate(Calendar enteredDate) { enteredDate = enteredDate; }
  
  public String getVerifiedByName() { return this.mstrVerifiedByName; }
  
  public void setVerifiedByName(String pVerifiedByName) { this.mstrVerifiedByName = pVerifiedByName; }
  
  public Pfm() {}
  
  public Object clone() throws CloneNotSupportedException { return super.clone(); }
  
  public String getChangeNumber() { return this.changeNumber; }
  
  public void setChangeNumber(String value) { this.changeNumber = value; }
  
  public Calendar getStreetDate() { return this.streetDate; }
  
  public void setStreetDate(Calendar date) { this.streetDate = date; }
  
  public String getProjectID() { return this.projectID; }
  
  public void setProjectID(String idNumber) { this.projectID = idNumber; }
  
  public boolean getParentalGuidance() { return this.parentalGuidance; }
  
  public void setParentalGuidance(boolean pg) { this.parentalGuidance = pg; }
  
  public String getSoundScanGrp() { return this.soundscan_grp; }
  
  public void setSoundScanGrp(String soundscan_grp) { this.soundscan_grp = soundscan_grp; }
  
  public String getEncryptionFlag() { return this.encryption_flag; }
  
  public void setEncryptionFlag(String encryption_flag) { this.encryption_flag = encryption_flag; }
  
  public String getStatus() { return this.mstrStatus; }
  
  public void setStatus(String status) { this.mstrStatus = status; }
  
  public PrefixCode getPrefixID() { return this.prefixID; }
  
  public void setPrefixID(PrefixCode prefixCode) { this.prefixID = prefixCode; }
  
  public String getSelectionNo() { return this.selectionNo; }
  
  public void setSelectionNo(String no) { this.selectionNo = no; }
}


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\Pfm.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */