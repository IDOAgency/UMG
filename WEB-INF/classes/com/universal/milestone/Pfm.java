/*      */ package WEB-INF.classes.com.universal.milestone;
/*      */ 
/*      */ import com.universal.milestone.Genre;
/*      */ import com.universal.milestone.Pfm;
/*      */ import com.universal.milestone.PrefixCode;
/*      */ import com.universal.milestone.Selection;
/*      */ import java.util.Calendar;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class Pfm
/*      */   implements Cloneable
/*      */ {
/*      */   protected int mnReleaseId;
/*      */   protected String releaseType;
/*      */   protected String narmFlag;
/*      */   protected String mstrPreparedBy;
/*      */   protected String mstrEmail;
/*      */   protected String mstrPhone;
/*      */   protected String mstrFaxNumber;
/*      */   protected String mstrOperatingCompany;
/*      */   protected String mlProductNumber;
/*      */   protected String mstrComments;
/*      */   protected String mstrConfigCode;
/*      */   protected String mstrModifier;
/*      */   protected String mstrTitle;
/*      */   protected String mstrArtist;
/*      */   protected String mstrTitleId;
/*      */   protected String mstrSuperLabel;
/*      */   protected String mstrLabelCode;
/*      */   protected String mstrCompanyCode;
/*      */   protected String mstrPoMergeCode;
/*      */   protected int mnUnitsPerSet;
/*      */   protected int mnSetsPerCarton;
/*      */   protected String mstrSupplier;
/*      */   protected String mstrImportIndicator;
/*      */   protected String mstrUpc;
/*      */   protected String mstrMusicLine;
/*      */   protected String mstrRepertoireOwner;
/*      */   protected String mstrRepertoireClass;
/*      */   protected String mstrReturnCode;
/*      */   protected String mstrExportFlag;
/*      */   protected String mstrCountries;
/*      */   protected String mstrSpineTitle;
/*      */   protected String mstrSpineArtist;
/*      */   protected String mstrPriceCode;
/*      */   protected String mstrPriceCodeDPC;
/*      */   protected String mstrGuaranteeCode;
/*      */   protected String mstrLoosePickExemptCode;
/*      */   protected String mstrCompilationCode;
/*      */   protected boolean mbParentalAdv;
/*      */   protected boolean mbValueAdded;
/*      */   protected boolean mbBoxSet;
/*      */   protected String mstrImpRateCode;
/*      */   protected Genre mstrMusicType;
/*      */   protected String mstrPricePoint;
/*      */   protected String mode;
/*      */   protected String printOption;
/*      */   protected int lastUpdatingUser;
/*      */   protected Calendar lastUpdatedDate;
/*      */   protected long lastUpdatedCk;
/*      */   protected String mstrApprovedByName;
/*      */   protected String mstrEnteredByName;
/*      */   protected Calendar enteredDate;
/*      */   protected String mstrVerifiedByName;
/*   91 */   private Selection selection = null;
/*      */ 
/*      */   
/*   94 */   protected String changeNumber = "";
/*      */ 
/*      */   
/*   97 */   protected Calendar streetDate = null;
/*   98 */   protected String projectID = "";
/*      */   
/*      */   protected boolean parentalGuidance;
/*      */   
/*  102 */   protected String soundscan_grp = "";
/*      */ 
/*      */   
/*  105 */   protected String encryption_flag = "";
/*      */ 
/*      */   
/*      */   protected String mstrStatus;
/*      */ 
/*      */   
/*  111 */   protected PrefixCode prefixID = null;
/*  112 */   protected String selectionNo = "";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   Pfm(Selection selection) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  131 */   public String getReleaseType() { return this.releaseType; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  139 */   public void setReleaseType(String releaseType) { this.releaseType = releaseType; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  147 */   public void setNarmFlag(String narmFlag) { this.narmFlag = narmFlag; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  155 */   public String getNarmFlag() { return this.narmFlag; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  163 */   public String getPreparedBy() { return this.mstrPreparedBy; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  171 */   public void setPreparedBy(String preparedBy) { this.mstrPreparedBy = preparedBy; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  178 */   public String getEmail() { return this.mstrEmail; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  186 */   public void setEmail(String email) { this.mstrEmail = email; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  194 */   public String getPhone() { return this.mstrPhone; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  202 */   public void setPhone(String phone) { this.mstrPhone = phone; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  210 */   public String getFaxNumber() { return this.mstrFaxNumber; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  218 */   public void setFaxNumber(String faxNumber) { this.mstrFaxNumber = faxNumber; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  226 */   public String getOperatingCompany() { return this.mstrOperatingCompany; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  234 */   public void setOperatingCompany(String operatingCompany) { this.mstrOperatingCompany = operatingCompany; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  242 */   public String getProductNumber() { return this.mlProductNumber; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  250 */   public void setProductNumber(String productNumber) { this.mlProductNumber = productNumber; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  259 */   public int getReleaseId() { return this.mnReleaseId; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  267 */   public void setReleaseId(int releaseId) { this.mnReleaseId = releaseId; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  275 */   public String getComments() { return this.mstrComments; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  283 */   public void setComments(String comments) { this.mstrComments = comments; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  291 */   public String getConfigCode() { return this.mstrConfigCode; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  299 */   public void setConfigCode(String configCode) { this.mstrConfigCode = configCode; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  307 */   public String getModifier() { return this.mstrModifier; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  315 */   public void setModifier(String modifier) { this.mstrModifier = modifier; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  323 */   public String getTitle() { return this.mstrTitle; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  331 */   public void setTitle(String title) { this.mstrTitle = title; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  339 */   public String getArtist() { return this.mstrArtist; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  347 */   public void setArtist(String artist) { this.mstrArtist = artist; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  355 */   public String getTitleId() { return this.mstrTitleId; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  363 */   public void setTitleId(String titleId) { this.mstrTitleId = titleId; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  371 */   public String getSuperLabel() { return this.mstrSuperLabel; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  379 */   public void setSuperLabel(String pSuperLabel) { this.mstrSuperLabel = pSuperLabel; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  387 */   public String getLabelCode() { return this.mstrLabelCode; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  396 */   public void setLabelCode(String pLabelCode) { this.mstrLabelCode = pLabelCode; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  404 */   public String getCompanyCode() { return this.mstrCompanyCode; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  412 */   public void setCompanyCode(String pCompanyCode) { this.mstrCompanyCode = pCompanyCode; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  420 */   public String getPoMergeCode() { return this.mstrPoMergeCode; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  428 */   public void setPoMergeCode(String pPoMergeCode) { this.mstrPoMergeCode = pPoMergeCode; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  436 */   public int getUnitsPerSet() { return this.mnUnitsPerSet; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  444 */   public void setUnitsPerSet(int pUnitsPerSet) { this.mnUnitsPerSet = pUnitsPerSet; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  452 */   public int getSetsPerCarton() { return this.mnSetsPerCarton; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  460 */   public void setSetsPerCarton(int pSetsPerCarton) { this.mnSetsPerCarton = pSetsPerCarton; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  468 */   public String getSupplier() { return this.mstrSupplier; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  476 */   public void setSupplier(String pSupplier) { this.mstrSupplier = pSupplier; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  484 */   public String getImportIndicator() { return this.mstrImportIndicator; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  492 */   public void setImportIndicator(String pImportIndicator) { this.mstrImportIndicator = pImportIndicator; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  500 */   public String getUpc() { return this.mstrUpc; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  508 */   public void setUpc(String pUpc) { this.mstrUpc = pUpc; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  516 */   public String getMusicLine() { return this.mstrMusicLine; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  524 */   public void setMusicLine(String pMusicLine) { this.mstrMusicLine = pMusicLine; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  532 */   public String getRepertoireOwner() { return this.mstrRepertoireOwner; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  540 */   public void setRepertoireOwner(String pRepertoireOwner) { this.mstrRepertoireOwner = pRepertoireOwner; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  548 */   public String getRepertoireClass() { return this.mstrRepertoireClass; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  556 */   public void setRepertoireClass(String pRepertoireClass) { this.mstrRepertoireClass = pRepertoireClass; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  564 */   public String getReturnCode() { return this.mstrReturnCode; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  572 */   public void setReturnCode(String pReturnCode) { this.mstrReturnCode = pReturnCode; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  580 */   public String getExportFlag() { return this.mstrExportFlag; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  588 */   public void setExportFlag(String pExportFlag) { this.mstrExportFlag = pExportFlag; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  596 */   public String getCountries() { return this.mstrCountries; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  604 */   public void setMode(String mode) { this.mode = mode; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  612 */   public String getMode() { return this.mode; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  621 */   public void setPrintOption(String printOption) { this.printOption = printOption; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  629 */   public String getPrintOption() { return this.printOption; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  637 */   public void setCountries(String pCountries) { this.mstrCountries = pCountries; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  645 */   public String getSpineTitle() { return this.mstrSpineTitle; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  653 */   public void setSpineTitle(String pSpineTitle) { this.mstrSpineTitle = pSpineTitle; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  661 */   public String getSpineArtist() { return this.mstrSpineArtist; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  669 */   public void setSpineArtist(String pSpineArtist) { this.mstrSpineArtist = pSpineArtist; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  677 */   public String getPriceCode() { return this.mstrPriceCode; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  685 */   public void setPriceCode(String pPriceCode) { this.mstrPriceCode = pPriceCode; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  693 */   public String getPriceCodeDPC() { return this.mstrPriceCodeDPC; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  701 */   public void setPriceCodeDPC(String pPriceCodeDPC) { this.mstrPriceCodeDPC = pPriceCodeDPC; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  709 */   public String getGuaranteeCode() { return this.mstrGuaranteeCode; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  717 */   public String getLoosePickExemptCode() { return this.mstrLoosePickExemptCode; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  726 */   public String getCompilationCode() { return this.mstrCompilationCode; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  734 */   public boolean getBoxSet() { return this.mbBoxSet; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  743 */   public boolean getValueAdded() { return this.mbValueAdded; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  751 */   public void setGuaranteeCode(String pGuaranteeCode) { this.mstrGuaranteeCode = pGuaranteeCode; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  759 */   public void setLoosePickExemptCode(String pLoosePickExemptCode) { this.mstrLoosePickExemptCode = pLoosePickExemptCode; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  767 */   public void setCompilationCode(String pCompilationCode) { this.mstrCompilationCode = pCompilationCode; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  775 */   public void setBoxSet(boolean pBoxSet) { this.mbBoxSet = pBoxSet; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  784 */   public void setValueAdded(boolean pValueAdded) { this.mbValueAdded = pValueAdded; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  792 */   public String getImpRateCode() { return this.mstrImpRateCode; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  800 */   public void setImpRateCode(String pImpRateCode) { this.mstrImpRateCode = pImpRateCode; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  809 */   public Genre getMusicType() { return this.mstrMusicType; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  818 */   public void setMusicType(Genre pMusicType) { this.mstrMusicType = pMusicType; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  826 */   public String getPricePoint() { return this.mstrPricePoint; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  834 */   public void setPricePoint(String pPricePoint) { this.mstrPricePoint = pPricePoint; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  844 */   public Calendar getLastUpdatedDate() { return this.lastUpdatedDate; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  854 */   public void setLastUpdatedDate(Calendar lastUpdatedDate) { this.lastUpdatedDate = lastUpdatedDate; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  864 */   public int getLastUpdatingUser() { return this.lastUpdatingUser; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  874 */   public void setLastUpdatingUser(int lastUpdatingUser) { this.lastUpdatingUser = lastUpdatingUser; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  884 */   public long getLastUpdatedCk() { return this.lastUpdatedCk; }
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
/*  895 */   public void setLastUpdatedCk(long lastUpdatedCk) { this.lastUpdatedCk = lastUpdatedCk; }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  900 */   public Selection getSelection() { return this.selection; }
/*      */ 
/*      */ 
/*      */   
/*  904 */   public void setSelection(Selection value) { this.selection = value; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  912 */   public String getApprovedByName() { return this.mstrApprovedByName; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  920 */   public void setApprovedByName(String pApprovedByName) { this.mstrApprovedByName = pApprovedByName; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  928 */   public String getEnteredByName() { return this.mstrEnteredByName; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  936 */   public void setEnteredByName(String pEnteredByName) { this.mstrEnteredByName = pEnteredByName; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  944 */   public Calendar getEnteredDate() { return this.enteredDate; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  952 */   public void setEnteredDate(Calendar enteredDate) { enteredDate = enteredDate; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  960 */   public String getVerifiedByName() { return this.mstrVerifiedByName; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  968 */   public void setVerifiedByName(String pVerifiedByName) { this.mstrVerifiedByName = pVerifiedByName; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Pfm() {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  982 */   public Object clone() throws CloneNotSupportedException { return super.clone(); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  987 */   public String getChangeNumber() { return this.changeNumber; }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  992 */   public void setChangeNumber(String value) { this.changeNumber = value; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1002 */   public Calendar getStreetDate() { return this.streetDate; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1012 */   public void setStreetDate(Calendar date) { this.streetDate = date; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1021 */   public String getProjectID() { return this.projectID; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1031 */   public void setProjectID(String idNumber) { this.projectID = idNumber; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1040 */   public boolean getParentalGuidance() { return this.parentalGuidance; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1049 */   public void setParentalGuidance(boolean pg) { this.parentalGuidance = pg; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1059 */   public String getSoundScanGrp() { return this.soundscan_grp; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1068 */   public void setSoundScanGrp(String soundscan_grp) { this.soundscan_grp = soundscan_grp; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1078 */   public String getEncryptionFlag() { return this.encryption_flag; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1087 */   public void setEncryptionFlag(String encryption_flag) { this.encryption_flag = encryption_flag; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1097 */   public String getStatus() { return this.mstrStatus; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1106 */   public void setStatus(String status) { this.mstrStatus = status; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1114 */   public PrefixCode getPrefixID() { return this.prefixID; }
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
/* 1125 */   public void setPrefixID(PrefixCode prefixCode) { this.prefixID = prefixCode; }
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
/* 1136 */   public String getSelectionNo() { return this.selectionNo; }
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
/* 1147 */   public void setSelectionNo(String no) { this.selectionNo = no; }
/*      */ }


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\Pfm.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */