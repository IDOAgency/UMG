package WEB-INF.classes.com.universal.milestone;

import com.universal.milestone.Bom;
import com.universal.milestone.Cache;
import com.universal.milestone.Company;
import com.universal.milestone.Division;
import com.universal.milestone.Environment;
import com.universal.milestone.Family;
import com.universal.milestone.Genre;
import com.universal.milestone.ImpactDate;
import com.universal.milestone.Label;
import com.universal.milestone.LookupObject;
import com.universal.milestone.MilestoneDataEntity;
import com.universal.milestone.MilestoneHelper;
import com.universal.milestone.NotepadContentObject;
import com.universal.milestone.PrefixCode;
import com.universal.milestone.PriceCode;
import com.universal.milestone.ProductCategory;
import com.universal.milestone.ReleaseType;
import com.universal.milestone.Schedule;
import com.universal.milestone.Selection;
import com.universal.milestone.SelectionConfiguration;
import com.universal.milestone.SelectionManager;
import com.universal.milestone.SelectionStatus;
import com.universal.milestone.SelectionSubConfiguration;
import com.universal.milestone.User;
import java.util.Calendar;
import java.util.Vector;

public class Selection extends MilestoneDataEntity implements Cloneable, NotepadContentObject, Comparable {
  protected int selectionID = -1;
  
  protected int poQuantity = -1;
  
  protected int completedQuantity = -1;
  
  protected int numberOfUnits = -1;
  
  protected boolean holdSelection;
  
  protected boolean noDigitalRelease;
  
  protected boolean pressAndDistribution;
  
  protected boolean specialPackaging;
  
  protected User lastUpdatingUser = null;
  
  protected User lastMfgUpdatingUser = null;
  
  protected SelectionConfiguration selectionConfig;
  
  protected SelectionSubConfiguration selectionSubConfig;
  
  protected Genre genre = null;
  
  protected PrefixCode prefixID = null;
  
  protected PrefixCode auditPrefixID = null;
  
  protected ProductCategory productCategory = null;
  
  protected ReleaseType releaseType = null;
  
  protected SelectionStatus selectionStatus = null;
  
  protected LookupObject plant = null;
  
  protected LookupObject distribution = null;
  
  protected String titleID = "";
  
  protected String taskname = "";
  
  protected String department = "";
  
  protected String sellCode = "";
  
  protected String sellCodeDPC = "";
  
  protected String selectionNo = "";
  
  protected String projectID = "";
  
  protected String streetDateString = "";
  
  protected String internationalDateString = "";
  
  protected String impactDateString = "";
  
  protected String lastUpdateDateString = "";
  
  protected String lastMfgUpdateDateString = "";
  
  protected String holdReason = "";
  
  protected String upc = "";
  
  protected String artistFirstName = "";
  
  protected String artistLastName = "";
  
  protected String artist = "";
  
  protected String flArtist = "";
  
  protected String title = "";
  
  protected String aSide = "";
  
  protected String bSide = "";
  
  protected String selectionPackaging = "";
  
  protected String auditUPC = "";
  
  protected String auditSelectionNo = "";
  
  protected SelectionStatus auditSelectionStatus = null;
  
  protected String selectionTerritory = "";
  
  protected String otherContact = "";
  
  protected String manufacturingComments = "";
  
  protected String selectionComments = "";
  
  protected String retailCode = "";
  
  protected Calendar streetDate = null;
  
  protected Calendar auditStreetDate = null;
  
  protected Calendar auditDate = null;
  
  protected Calendar completionDate = null;
  
  protected Calendar dueDate = null;
  
  protected Calendar internationalDate = null;
  
  protected Calendar impactDate = null;
  
  protected Calendar lastUpdateDate = null;
  
  protected Calendar lastMfgUpdateDate = null;
  
  protected Calendar lastStreetUpdateDate = null;
  
  protected Calendar originDate = null;
  
  protected Calendar archieDate = null;
  
  protected Calendar lastLegacyUpdateDate = null;
  
  protected Calendar lastSchedUpdateDate = null;
  
  protected User lastSchedUpdatingUser = null;
  
  protected long lastUpdatedCheck = -1L;
  
  protected long lastMfgUpdatedCheck = -1L;
  
  protected float price;
  
  protected Family family;
  
  protected Environment environment;
  
  protected Company company;
  
  protected Division division;
  
  protected Label label;
  
  protected PriceCode priceCode;
  
  protected PriceCode priceCodeDPC;
  
  protected User labelContact;
  
  protected User umlContact;
  
  protected Schedule schedule;
  
  protected Bom bom;
  
  protected int familyId = -1;
  
  protected int environmentId = -1;
  
  protected int companyId = -1;
  
  protected int divisionId = -1;
  
  protected int labelId = -1;
  
  protected int labelContactId = -1;
  
  protected int scheduleId = -1;
  
  protected int calendarGroup = -1;
  
  protected ImpactDate impactDateObject;
  
  protected boolean fullSelection = false;
  
  protected int sortBy = -1;
  
  protected int templateId = -1;
  
  protected boolean parentalGuidance;
  
  protected Vector impactDates = null;
  
  protected Vector manufacturingPlants = null;
  
  protected Vector multSelections = null;
  
  protected Vector multOtherContacts = null;
  
  protected Calendar digital_rls_date = null;
  
  protected String digital_rls_date_string = "";
  
  protected String oper_company = "";
  
  protected String super_label = "";
  
  protected String sub_label = "";
  
  protected String config_code = "";
  
  protected String soundscan_grp = "";
  
  protected boolean international_flag;
  
  protected boolean isDigital = false;
  
  protected String imprint = "";
  
  protected boolean new_bundle_flag = false;
  
  protected String grid_number = "";
  
  protected String special_instructions = "";
  
  protected boolean priority_flag = false;
  
  protected int archimedesId = -1;
  
  protected int releaseFamilyId = -1;
  
  protected Calendar autoCloseDate = null;
  
  public int getIdentity() { return getSelectionID(); }
  
  public String getTableName() { return "Release_Header"; }
  
  public int compareTo(Object sel) throws ClassCastException {
    switch (this.sortBy) {
      case 1:
        return compareByStreetDate(sel);
      case 2:
        return compareByProductCategory(sel);
      case 3:
        return compareByArtist(sel);
      case 4:
        return compareByTitle(sel);
      case 5:
        return compareByUpc(sel);
      case 7:
        return compareByDivision(sel);
      case 9:
        return compareByLabel(sel);
      case 14:
        return compareByPrefixSelectionId(sel);
      case 12:
        return compareBySelectionNo(sel);
      case 6:
        return compareByFamily(sel);
      case 23:
        return compareByEnvironment(sel);
      case 8:
        return compareByCompany(sel);
      case 13:
        return compareBySubConfig(sel);
      case 10:
        return compareByStatus(sel);
      case 16:
        return compareBySpecialStatus(sel);
      case 17:
        return compareByStreetDateBlankAtEnd(sel);
      case 21:
        return compareByPackagingSpecs(sel);
      case 22:
        return compareByFlArtist(sel);
      case 19:
        return compareByImpactDate(sel);
      case 24:
        return compareByImprint(sel);
      case 25:
        return compareByPriority(sel);
      case 26:
        return compareByDigitalStreetDate(sel);
      case 27:
        return compareByAuditDate(sel);
      case 20:
        return compareByReverseStreetDate(sel);
    } 
    return compareByStreetDate(sel);
  }
  
  protected int compareByAuditDate(Object sel) throws ClassCastException {
    Calendar thisDate = this.auditDate;
    Calendar thatDate = ((Selection)sel).auditDate;
    if (thisDate == null && thatDate == null)
      return 0; 
    if (thisDate == null)
      return 1; 
    if (thatDate == null)
      return -1; 
    if (thisDate.before(thatDate))
      return -1; 
    if (thisDate.after(thatDate))
      return 1; 
    return 0;
  }
  
  protected int compareByDigitalStreetDate(Object sel) throws ClassCastException {
    Calendar thisStreetDate = this.digital_rls_date;
    Calendar thatStreetDate = ((Selection)sel).digital_rls_date;
    if (thisStreetDate == null && thatStreetDate == null)
      return 0; 
    if (thisStreetDate == null)
      return 1; 
    if (thatStreetDate == null)
      return -1; 
    if (thisStreetDate.before(thatStreetDate))
      return -1; 
    if (thisStreetDate.after(thatStreetDate))
      return 1; 
    return 0;
  }
  
  protected int compareByPriority(Object sel) throws ClassCastException {
    boolean thisPriority = getPriority();
    boolean thatPriority = ((Selection)sel).getPriority();
    if (thisPriority && !thatPriority)
      return -1; 
    if (!thisPriority && thatPriority)
      return 1; 
    return 0;
  }
  
  protected int compareByImprint(Object sel) throws ClassCastException {
    String thisImprint = (getImprint() != null) ? getImprint().trim() : "";
    String thatImprint = (((Selection)sel).getImprint() != null) ? ((Selection)sel).getImprint().trim() : "";
    if (thisImprint.equals("") && !thatImprint.equals(""))
      return 1; 
    if (!thisImprint.equals("") && thatImprint.equals(""))
      return -1; 
    return thisImprint.compareToIgnoreCase(thatImprint);
  }
  
  protected int compareBySubConfig(Object sel) throws ClassCastException {
    String thisSubConfig = (getSelectionConfig() != null) ? getSelectionConfig().getSelectionConfigurationName() : "";
    String thatSubConfig = (((Selection)sel).getSelectionConfig() != null) ? ((Selection)sel).getSelectionConfig().getSelectionConfigurationName() : "";
    return thisSubConfig.compareTo(thatSubConfig);
  }
  
  protected int compareByStreetDate(Object sel) throws ClassCastException {
    Calendar thisStreetDate = this.streetDate;
    Calendar thatStreetDate = ((Selection)sel).streetDate;
    if (thisStreetDate == null && thatStreetDate == null)
      return 0; 
    if (thisStreetDate == null)
      return 1; 
    if (thatStreetDate == null)
      return -1; 
    if (thisStreetDate.before(thatStreetDate))
      return -1; 
    if (thisStreetDate.after(thatStreetDate))
      return 1; 
    return 0;
  }
  
  protected int compareByReverseStreetDate(Object sel) throws ClassCastException {
    Calendar thisStreetDate = this.streetDate;
    Calendar thatStreetDate = ((Selection)sel).streetDate;
    if (thisStreetDate == null && thatStreetDate == null)
      return 0; 
    if (thisStreetDate == null)
      return -1; 
    if (thatStreetDate == null)
      return 1; 
    if (thisStreetDate.before(thatStreetDate))
      return 1; 
    if (thisStreetDate.after(thatStreetDate))
      return -1; 
    return 0;
  }
  
  protected int compareByStreetDateBlankAtEnd(Object sel) throws ClassCastException {
    Calendar thisStreetDate = this.streetDate;
    Calendar thatStreetDate = ((Selection)sel).streetDate;
    if (thisStreetDate == null)
      return 1; 
    return 0;
  }
  
  protected int compareByProductCategory(Object sel) throws ClassCastException {
    LookupObject prodCategory = SelectionManager.getLookupObject(getProductCategory().getAbbreviation(), 
        Cache.getProductCategories());
    String thisProdCategoryName = (prodCategory != null && prodCategory.getName() != null) ? 
      prodCategory.getName() : "";
    prodCategory = SelectionManager.getLookupObject(((Selection)sel).getProductCategory().getAbbreviation(), 
        Cache.getProductCategories());
    String thatProdCategoryName = (prodCategory != null && prodCategory.getName() != null) ? 
      prodCategory.getName() : "";
    if (thisProdCategoryName.equals("") && !thatProdCategoryName.equals(""))
      return 1; 
    if (!thisProdCategoryName.equals("") && thatProdCategoryName.equals(""))
      return -1; 
    return thisProdCategoryName.compareTo(thatProdCategoryName);
  }
  
  protected int compareByArtist(Object sel) throws ClassCastException {
    String thisArtist = (getArtist() != null) ? getArtist().trim() : "";
    String thatArtist = (((Selection)sel).getArtist() != null) ? ((Selection)sel).getArtist().trim() : "";
    if (thisArtist.equals("") && !thatArtist.equals(""))
      return 1; 
    if (!thisArtist.equals("") && thatArtist.equals(""))
      return -1; 
    return thisArtist.compareToIgnoreCase(thatArtist);
  }
  
  protected int compareByFlArtist(Object sel) throws ClassCastException {
    String thisFlArtist = (getFlArtist() != null) ? getFlArtist().trim() : "";
    String thatFlArtist = (((Selection)sel).getFlArtist() != null) ? ((Selection)sel).getFlArtist().trim() : "";
    if (thisFlArtist.equals("") && !thatFlArtist.equals(""))
      return 1; 
    if (!thisFlArtist.equals("") && thatFlArtist.equals(""))
      return -1; 
    return thisFlArtist.compareToIgnoreCase(thatFlArtist);
  }
  
  protected int compareByTitle(Object sel) throws ClassCastException {
    String thisTitle = (getTitle() != null) ? getTitle().trim() : "";
    String thatTitle = (((Selection)sel).getTitle() != null) ? ((Selection)sel).getTitle().trim() : "";
    if (thisTitle.equals("") && !thatTitle.equals(""))
      return 1; 
    if (!thisTitle.equals("") && thatTitle.equals(""))
      return -1; 
    return thisTitle.compareToIgnoreCase(thatTitle);
  }
  
  protected int compareByPackagingSpecs(Object sel) throws ClassCastException {
    String thisPackagingSpec = (getSelectionPackaging() != null) ? getSelectionPackaging().trim() : "";
    String thatPackagingSpec = (((Selection)sel).getSelectionPackaging() != null) ? ((Selection)sel).getSelectionPackaging().trim() : "";
    if (thisPackagingSpec.equals("") && !thatPackagingSpec.equals(""))
      return 1; 
    if (!thisPackagingSpec.equals("") && thatPackagingSpec.equals(""))
      return -1; 
    return thisPackagingSpec.compareTo(thatPackagingSpec);
  }
  
  protected int compareByUpc(Object sel) throws ClassCastException {
    String thisUpc = (getUpc() != null) ? getUpc() : "";
    String thatUpc = (((Selection)sel).getUpc() != null) ? ((Selection)sel).getUpc() : "";
    if (thisUpc.equals("") && !thatUpc.equals(""))
      return 1; 
    if (!thisUpc.equals("") && thatUpc.equals(""))
      return -1; 
    return thisUpc.compareTo(thatUpc);
  }
  
  protected int compareByDivision(Object sel) throws ClassCastException {
    String thisDivision = (getDivision() != null && getDivision().getName() != null) ? 
      getDivision().getName().trim() : "";
    String thatDivision = (((Selection)sel).getDivision() != null && ((Selection)sel).getDivision().getName() != null) ? (
      (Selection)sel).getDivision().getName().trim() : "";
    if (thisDivision.equals("") && !thatDivision.equals(""))
      return 1; 
    if (!thisDivision.equals("") && thatDivision.equals(""))
      return -1; 
    return thisDivision.compareTo(thatDivision);
  }
  
  protected int compareByLabel(Object sel) throws ClassCastException {
    String thisLabel = (getLabel() != null && getLabel().getName() != null) ? 
      getLabel().getName().trim() : "";
    String thatLabel = (((Selection)sel).getLabel() != null && ((Selection)sel).getLabel().getName() != null) ? (
      (Selection)sel).getLabel().getName().trim() : "";
    if (thisLabel.equals("") && !thatLabel.equals(""))
      return 1; 
    if (!thisLabel.equals("") && thatLabel.equals(""))
      return -1; 
    return thisLabel.compareTo(thatLabel);
  }
  
  protected int compareByPrefixSelectionId(Object sel) throws ClassCastException {
    String thisSelectionNo = SelectionManager.getLookupObjectValue(getPrefixID());
    if (thisSelectionNo == null)
      thisSelectionNo = ""; 
    thisSelectionNo = String.valueOf(thisSelectionNo) + getSelectionNo();
    String thatSelectionNo = SelectionManager.getLookupObjectValue(((Selection)sel).getPrefixID());
    if (thatSelectionNo == null)
      thatSelectionNo = ""; 
    thatSelectionNo = String.valueOf(thatSelectionNo) + ((Selection)sel).getSelectionNo();
    if (thisSelectionNo.equals("") && !thatSelectionNo.equals(""))
      return 1; 
    if (!thisSelectionNo.equals("") && thatSelectionNo.equals(""))
      return -1; 
    return thisSelectionNo.compareTo(thatSelectionNo);
  }
  
  protected int compareBySelectionNo(Object sel) throws ClassCastException {
    String thisSelectionNo = (getSelectionNo() != null) ? getSelectionNo() : "";
    String thatSelectionNo = (((Selection)sel).getSelectionNo() != null) ? ((Selection)sel).getSelectionNo() : "";
    if (thisSelectionNo.equals("") && !thatSelectionNo.equals(""))
      return 1; 
    if (!thisSelectionNo.equals("") && thatSelectionNo.equals(""))
      return -1; 
    return thisSelectionNo.compareTo(thatSelectionNo);
  }
  
  protected int compareByFamily(Object sel) throws ClassCastException {
    String thisFamily = (getFamily() != null && getFamily().getName() != null) ? 
      getFamily().getName().trim() : "";
    String thatFamily = (((Selection)sel).getFamily() != null && ((Selection)sel).getFamily().getName() != null) ? (
      (Selection)sel).getFamily().getName().trim() : "";
    if (thisFamily.equals("") && !thatFamily.equals(""))
      return 1; 
    if (!thisFamily.equals("") && thatFamily.equals(""))
      return -1; 
    return thisFamily.compareTo(thatFamily);
  }
  
  protected int compareByStatus(Object sel) throws ClassCastException {
    String thisStatus = (getSelectionStatus() != null && getSelectionStatus().getName() != null) ? 
      getSelectionStatus().getName().trim() : "";
    String thatStatus = (((Selection)sel).getSelectionStatus() != null && ((Selection)sel).getSelectionStatus().getName() != null) ? (
      (Selection)sel).getSelectionStatus().getName().trim() : "";
    if (thisStatus.equals("") && !thatStatus.equals(""))
      return 1; 
    if (!thisStatus.equals("") && thatStatus.equals(""))
      return -1; 
    return thisStatus.compareTo(thatStatus);
  }
  
  protected int compareBySpecialStatus(Object sel) throws ClassCastException {
    String thisStatus = (getSelectionStatus() != null && getSelectionStatus().getName() != null) ? 
      getSelectionStatus().getName().trim() : "";
    if (!thisStatus.equalsIgnoreCase("TBS") && !thisStatus.equalsIgnoreCase("In The Works"))
      thisStatus = "A"; 
    String thatStatus = (((Selection)sel).getSelectionStatus() != null && ((Selection)sel).getSelectionStatus().getName() != null) ? (
      (Selection)sel).getSelectionStatus().getName().trim() : "";
    if (!thatStatus.equalsIgnoreCase("TBS") && !thatStatus.equalsIgnoreCase("In The Works"))
      thatStatus = "A"; 
    if (thisStatus.equals("") && !thatStatus.equals(""))
      return 1; 
    if (!thisStatus.equals("") && thatStatus.equals(""))
      return -1; 
    return thisStatus.compareTo(thatStatus);
  }
  
  protected int compareByCompany(Object sel) throws ClassCastException {
    String thisCompany = (getCompany() != null && getCompany().getName() != null) ? 
      getCompany().getName().trim() : "";
    String thatCompany = (((Selection)sel).getCompany() != null && ((Selection)sel).getCompany().getName() != null) ? (
      (Selection)sel).getCompany().getName().trim() : "";
    if (thisCompany.equals("") && !thatCompany.equals(""))
      return 1; 
    if (!thisCompany.equals("") && thatCompany.equals(""))
      return -1; 
    return thisCompany.compareTo(thatCompany);
  }
  
  protected int compareByImpactDate(Object sel) throws ClassCastException {
    Calendar thisImpactDate = this.impactDate;
    Calendar thatImpactDate = ((Selection)sel).impactDate;
    if (thisImpactDate == null && thatImpactDate == null)
      return 0; 
    if (thisImpactDate == null)
      return 1; 
    if (thatImpactDate == null)
      return -1; 
    if (thisImpactDate.before(thatImpactDate))
      return -1; 
    if (thisImpactDate.after(thatImpactDate))
      return 1; 
    return 0;
  }
  
  public int getSelectionID() { return this.selectionID; }
  
  public void setSelectionID(int id) { this.selectionID = id; }
  
  public String getSelectionNo() { return this.selectionNo; }
  
  public void setSelectionNo(String no) {
    auditCheck("selection_no", this.selectionNo, no);
    this.selectionNo = no;
  }
  
  public Calendar getStreetDate() { return this.streetDate; }
  
  public void setStreetDate(Calendar date) { this.streetDate = date; }
  
  public Calendar getAuditStreetDate() { return this.auditStreetDate; }
  
  public void setAuditStreetDate(Calendar date) { this.auditStreetDate = date; }
  
  public Calendar getAuditDate() { return this.auditDate; }
  
  public void setAuditDate(Calendar date) { this.auditDate = date; }
  
  public String getAuditSelectionNo() { return this.auditSelectionNo; }
  
  public void setAuditSelectionNo(String no) { this.auditSelectionNo = no; }
  
  public PrefixCode getAuditPrefixID() { return this.auditPrefixID; }
  
  public void setAuditPrefixID(PrefixCode prefixCode) { this.auditPrefixID = prefixCode; }
  
  public String getAuditUPC() { return this.auditUPC; }
  
  public void setAuditUPC(String no) { this.auditUPC = no; }
  
  public SelectionStatus getAuditSelectionStatus() { return this.auditSelectionStatus; }
  
  public void setAuditSelectionStatus(SelectionStatus status) { this.auditSelectionStatus = status; }
  
  public Calendar getInternationalDate() { return this.internationalDate; }
  
  public void setInternationalDate(Calendar date) { this.internationalDate = date; }
  
  public Calendar getImpactDate() { return this.impactDate; }
  
  public void setImpactDate(Calendar date) { this.impactDate = date; }
  
  public ImpactDate getImpactDateObject() { return this.impactDateObject; }
  
  public void setImpactDateObject(ImpactDate date) { this.impactDateObject = date; }
  
  public SelectionStatus getSelectionStatus() { return this.selectionStatus; }
  
  public void setSelectionStatus(SelectionStatus status) { this.selectionStatus = status; }
  
  public boolean getHoldSelection() { return this.holdSelection; }
  
  public void setHoldSelection(boolean isOnHold) { this.holdSelection = isOnHold; }
  
  public boolean getNoDigitalRelease() { return this.noDigitalRelease; }
  
  public void setNoDigitalRelease(boolean noDigRel) { this.noDigitalRelease = noDigRel; }
  
  public String getHoldReason() { return this.holdReason; }
  
  public void setHoldReason(String reason) { this.holdReason = reason; }
  
  public boolean getPressAndDistribution() { return this.pressAndDistribution; }
  
  public void setPressAndDistribution(boolean isPressAndDistribution) { this.pressAndDistribution = isPressAndDistribution; }
  
  public String getProjectID() { return this.projectID; }
  
  public void setProjectID(String idNumber) { this.projectID = idNumber; }
  
  public boolean getSpecialPackaging() { return this.specialPackaging; }
  
  public void setSpecialPackaging(boolean hasSpecialPackaging) { this.specialPackaging = hasSpecialPackaging; }
  
  public PrefixCode getPrefixID() { return this.prefixID; }
  
  public void setPrefixID(PrefixCode prefixCode) { this.prefixID = prefixCode; }
  
  public String getTitleID() { return this.titleID; }
  
  public void setTitleID(String titleIdNumber) { this.titleID = titleIdNumber; }
  
  public String getUpc() { return this.upc; }
  
  public void setUpc(String upcCode) {
    auditCheck("upc", this.upc, upcCode);
    this.upc = upcCode;
  }
  
  public String getArtistFirstName() { return this.artistFirstName; }
  
  public void setArtistFirstName(String firstName) { this.artistFirstName = firstName; }
  
  public String getArtistLastName() { return this.artistLastName; }
  
  public void setArtistLastName(String lastName) { this.artistLastName = lastName; }
  
  public void setArtist(String artistName) { this.artist = artistName; }
  
  public String getArtist() { return this.artist; }
  
  public void setFlArtist(String artistName) { this.flArtist = artistName; }
  
  public String getFlArtist() { return this.flArtist; }
  
  public String getTitle() { return this.title; }
  
  public void setTitle(String theTitle) { this.title = theTitle; }
  
  public String getASide() { return this.aSide; }
  
  public void setASide(String aSideText) { this.aSide = aSideText; }
  
  public String getBSide() { return this.bSide; }
  
  public void setBSide(String bSideText) { this.bSide = bSideText; }
  
  public ProductCategory getProductCategory() { return this.productCategory; }
  
  public void setProductCategory(ProductCategory productCategory) { this.productCategory = productCategory; }
  
  public ReleaseType getReleaseType() { return this.releaseType; }
  
  public void setReleaseType(ReleaseType releaseType) { this.releaseType = releaseType; }
  
  public SelectionConfiguration getSelectionConfig() { return this.selectionConfig; }
  
  public void setSelectionConfig(SelectionConfiguration selectionConfig) { this.selectionConfig = selectionConfig; }
  
  public SelectionSubConfiguration getSelectionSubConfig() { return this.selectionSubConfig; }
  
  public void setSelectionSubConfig(SelectionSubConfiguration selectionSubConfig) { this.selectionSubConfig = selectionSubConfig; }
  
  public Family getFamily() { return this.family; }
  
  public void setFamily(Family family) { this.family = family; }
  
  public Company getCompany() { return this.company; }
  
  public void setCompany(Company company) { this.company = company; }
  
  public Division getDivision() { return this.division; }
  
  public void setDivision(Division division) { this.division = division; }
  
  public Label getLabel() { return this.label; }
  
  public void setLabel(Label label) { this.label = label; }
  
  public String getSellCode() { return this.sellCode; }
  
  public void setSellCode(String sellCode) { this.sellCode = sellCode; }
  
  public String getSellCodeDPC() { return this.sellCodeDPC; }
  
  public void setSellCodeDPC(String sellCodeDPC) { this.sellCodeDPC = sellCodeDPC; }
  
  public String getRetailCode() { return this.retailCode; }
  
  public void setRetailCode(String retailCode) { this.retailCode = retailCode; }
  
  public PriceCode getPriceCode() { return this.priceCode; }
  
  public void setPriceCode(PriceCode priceCode) { this.priceCode = priceCode; }
  
  public PriceCode getPriceCodeDPC() { return this.priceCodeDPC; }
  
  public void setPriceCodeDPC(PriceCode priceCodeDPC) { this.priceCodeDPC = priceCodeDPC; }
  
  public float getPrice() { return this.price; }
  
  public void setPrice(float price) { this.price = price; }
  
  public int getNumberOfUnits() { return this.numberOfUnits; }
  
  public void setNumberOfUnits(int numberOfUnits) { this.numberOfUnits = numberOfUnits; }
  
  public Genre getGenre() { return this.genre; }
  
  public void setGenre(Genre genre) { this.genre = genre; }
  
  public String getSelectionPackaging() { return this.selectionPackaging; }
  
  public void setSelectionPackaging(String selectionPackaging) { this.selectionPackaging = selectionPackaging; }
  
  public User getLabelContact() { return this.labelContact; }
  
  public void setLabelContact(User labelContact) { this.labelContact = labelContact; }
  
  public String getOtherContact() { return this.otherContact; }
  
  public void setOtherContact(String otherContact) { this.otherContact = otherContact; }
  
  public Schedule getSchedule() { return this.schedule; }
  
  public void setBom(Bom bom) { this.bom = bom; }
  
  public Bom getBom() { return this.bom; }
  
  public void setSchedule(Schedule schedule) { this.schedule = schedule; }
  
  public User getLastUpdatingUser() { return this.lastUpdatingUser; }
  
  public void setLastUpdatingUser(User lastUpdatingUser) { this.lastUpdatingUser = lastUpdatingUser; }
  
  public User getLastMfgUpdatingUser() { return this.lastMfgUpdatingUser; }
  
  public void setLastMfgUpdatingUser(User lastMfgUpdatingUser) { this.lastMfgUpdatingUser = lastMfgUpdatingUser; }
  
  public Calendar getLastUpdateDate() { return this.lastUpdateDate; }
  
  public void setLastUpdateDate(Calendar lastUpdateDate) { this.lastUpdateDate = lastUpdateDate; }
  
  public Calendar getArchieDate() { return this.archieDate; }
  
  public void setArchieDate(Calendar archieDate) { this.archieDate = archieDate; }
  
  public Calendar getLastLegacyUpdateDate() { return this.lastLegacyUpdateDate; }
  
  public void setLastLegacyUpdateDate(Calendar lastLegacyUpdateDate) { this.lastLegacyUpdateDate = lastLegacyUpdateDate; }
  
  public Calendar getAutoCloseDate() { return this.autoCloseDate; }
  
  public void setAutoCloseDate(Calendar autoCloseDate) { this.autoCloseDate = autoCloseDate; }
  
  public Calendar getLastMfgUpdateDate() { return this.lastMfgUpdateDate; }
  
  public void setLastMfgUpdateDate(Calendar lastMfgUpdateDate) { this.lastMfgUpdateDate = lastMfgUpdateDate; }
  
  public Calendar getLastStreetUpdateDate() { return this.lastStreetUpdateDate; }
  
  public void setLastStreetUpdateDate(Calendar lastStreetUpdateDate) { this.lastStreetUpdateDate = lastStreetUpdateDate; }
  
  public String getSelectionTerritory() { return this.selectionTerritory; }
  
  public void setSelectionTerritory(String selectionTerritory) { this.selectionTerritory = selectionTerritory; }
  
  public User getUmlContact() { return this.umlContact; }
  
  public void setUmlContact(User umlContact) { this.umlContact = umlContact; }
  
  public LookupObject getPlant() { return this.plant; }
  
  public void setPlant(LookupObject plant) { this.plant = plant; }
  
  public LookupObject getDistribution() { return this.distribution; }
  
  public void setDistribution(LookupObject distribution) { this.distribution = distribution; }
  
  public int getPoQuantity() { return this.poQuantity; }
  
  public void setPoQuantity(int poQuantity) { this.poQuantity = poQuantity; }
  
  public int getCompletedQuantity() { return this.completedQuantity; }
  
  public void setCompletedQuantity(int completedQuantity) { this.completedQuantity = completedQuantity; }
  
  public String getSelectionComments() { return this.selectionComments; }
  
  public void setComments(String selectionComments) { this.selectionComments = selectionComments; }
  
  public String getManufacturingComments() { return this.manufacturingComments; }
  
  public void setManufacturingComments(String manufacturingComments) { this.manufacturingComments = manufacturingComments; }
  
  public int getFamilyId() { return this.familyId; }
  
  public void setFamilyId(int familyId) { this.familyId = familyId; }
  
  public int getCompanyId() { return this.companyId; }
  
  public void setCompanyId(int companyId) { this.companyId = companyId; }
  
  public int getDivisionId() { return this.divisionId; }
  
  public void setDivisionId(int divisionId) { this.divisionId = divisionId; }
  
  public int getLabelId() { return this.labelId; }
  
  public void setLabelId(int labelId) { this.labelId = labelId; }
  
  public int getLabelContactId() { return this.labelContactId; }
  
  public void setLabelContactId(int labelContactId) { this.labelContactId = labelContactId; }
  
  public int getScheduleId() { return this.scheduleId; }
  
  public void setScheduleId(int scheduleId) { this.scheduleId = scheduleId; }
  
  public void setTaskName(String name) { this.taskname = name; }
  
  public String getTaskName() { return this.taskname; }
  
  public void setDepartment(String p_department) { this.department = p_department; }
  
  public String getDepartment() { return this.department; }
  
  public Calendar getCompletionDate() { return this.completionDate; }
  
  public void setCompletionDate(Calendar date) { this.completionDate = date; }
  
  public void setDueDate(Calendar date) { this.dueDate = date; }
  
  public Calendar getDueDate() { return this.dueDate; }
  
  public String getStreetDateString() { return this.streetDateString; }
  
  public void setStreetDateString(String streetDateString) { this.streetDateString = streetDateString; }
  
  public String getInternationalDateString() { return this.internationalDateString; }
  
  public void setInternationalDateString(String internationalDateString) { this.internationalDateString = internationalDateString; }
  
  public boolean isFullSelection() { return this.fullSelection; }
  
  public void setFullSelection(boolean fullSelection) { this.fullSelection = fullSelection; }
  
  public String getImpactDateString() { return this.impactDateString; }
  
  public void setImpactDateString(String impactDateString) { this.impactDateString = impactDateString; }
  
  public String getLastUpdateDateString() { return this.lastUpdateDateString; }
  
  public void setLastUpdateDateString(String lastUpdateDateString) { this.lastUpdateDateString = lastUpdateDateString; }
  
  public String getLastMfgUpdateDateString() { return this.lastMfgUpdateDateString; }
  
  public void setLastMfgUpdateDateString(String lastMfgUpdateDateString) { this.lastMfgUpdateDateString = lastMfgUpdateDateString; }
  
  public long getLastUpdatedCheck() { return this.lastUpdatedCheck; }
  
  public void setLastUpdatedCheck(long lastUpdatedCheck) { this.lastUpdatedCheck = lastUpdatedCheck; }
  
  public Calendar getOriginDate() { return this.originDate; }
  
  public void setOriginDate(Calendar originDate) { this.originDate = originDate; }
  
  public long getLastMfgUpdatedCheck() { return this.lastMfgUpdatedCheck; }
  
  public void setLastMfgUpdatedCheck(long lastMfgUpdatedCheck) { this.lastMfgUpdatedCheck = lastMfgUpdatedCheck; }
  
  public int getTemplateId() { return this.templateId; }
  
  public void setTemplateId(int id) { this.templateId = id; }
  
  public boolean getParentalGuidance() { return this.parentalGuidance; }
  
  public void setParentalGuidance(boolean pg) { this.parentalGuidance = pg; }
  
  public Vector getImpactDates() { return this.impactDates; }
  
  public void setManufacturingPlants(Vector manufacturingPlants) { this.manufacturingPlants = manufacturingPlants; }
  
  public Vector getManufacturingPlants() { return this.manufacturingPlants; }
  
  public void setImpactDates(Vector impactDates) { this.impactDates = impactDates; }
  
  public Calendar getLastSchedUpdateDate() { return this.lastSchedUpdateDate; }
  
  public void setLastSchedUpdateDate(Calendar lastSchedUpdateDate) { this.lastSchedUpdateDate = lastSchedUpdateDate; }
  
  public User getLastSchedUpdatingUser() { return this.lastSchedUpdatingUser; }
  
  public void setLastSchedUpdatingUser(User lastSchedUpdatingUser) { this.lastSchedUpdatingUser = lastSchedUpdatingUser; }
  
  public String toString() {
    return " \n--Selection--\ngetSelectionID " + 
      
      getSelectionID() + "\n" + 
      "getProjectID " + getProjectID() + "\n" + 
      "getTitleID " + getTitleID() + "\n" + 
      "getArtistFirstName " + getArtistFirstName() + "\n" + 
      "getArtistLastName " + getArtistLastName() + "\n" + 
      "getSelectionID " + getSelectionID() + "\n" + 
      
      "getReleaseType " + getReleaseType() + "\n" + 
      "getSelectionConfig " + getSelectionConfig() + "\n" + 
      "getSelectionSubConfig " + getSelectionSubConfig() + "\n" + 
      "getUpc " + getUpc() + "\n" + 
      "getGenre " + getGenre() + "\n" + 
      "getFamilyId " + getFamilyId() + "\n" + 
      "getEnvironmentId " + getEnvironmentId() + "\n" + 
      "getCompanyId " + getCompanyId() + "\n" + 
      "getDivisionId " + getDivisionId() + "\n" + 
      "getLabelId " + getLabelId() + "\n" + 
      "getStreetDate " + getStreetDate() + "\n" + 
      "getInternationalDate " + getInternationalDate() + "\n" + 
      
      "getLabelContactId " + getLabelContactId() + "\n" + 
      "getSelectionStatus " + getSelectionStatus() + "\n" + 
      "getHoldSelection " + getHoldSelection() + "\n" + 
      "getHoldReason " + getHoldReason() + "\n" + 
      "getSelectionComments " + getSelectionComments() + "\n" + 
      
      "getSpecialPackaging " + getSpecialPackaging() + "\n" + 
      "getPrice " + getPrice() + "\n" + 
      
      "getNumberOfUnits " + getNumberOfUnits() + "\n" + 
      "gePressAndDistribution " + getPressAndDistribution() + "\n" + 
      "getPrefixId " + getPrefixID() + "\n" + 
      
      "getSelectionPackaging " + getSelectionPackaging() + "\n" + 
      
      "getImpactDate " + getImpactDate() + "\n" + 
      
      "getLastUpdateDate " + getLastUpdateDate() + "\n" + 
      "getLastUpdateCheck " + getLastUpdatedCheck() + "\n" + 
      
      "getDigitalRlsDate " + getDigitalRlsDate() + "\n" + 
      "getOperCompany " + getOperCompany() + "\n" + 
      "getSuperLabel " + getSuperLabel() + "\n" + 
      "getConfigCode " + getConfigCode() + "\n" + 
      "getSoundScanGrp " + getSoundScanGrp() + "\n" + 
      "internationalFlag " + getInternationalFlag() + "\n" + 
      
      "getASide " + getASide() + "\n" + 
      "getBSide " + getBSide() + "\n" + 
      "--End Selection--" + "\n";
  }
  
  public Object clone() throws CloneNotSupportedException { return super.clone(); }
  
  public String getNotepadContentObjectId() { return Integer.toString(this.selectionID); }
  
  public void setSortBy(int s) { this.sortBy = s; }
  
  public Vector getMultSelections() { return this.multSelections; }
  
  public void setMultSelections(Vector multSelections) { this.multSelections = multSelections; }
  
  public Vector getMultOtherContacts() { return this.multOtherContacts; }
  
  public void setMultOtherContacts(Vector multOtherContacts) { this.multOtherContacts = multOtherContacts; }
  
  protected int compareByEnvironment(Object sel) throws ClassCastException {
    String thisEnvironment = (getEnvironment() != null && getEnvironment().getName() != null) ? 
      getEnvironment().getName().trim() : "";
    String thatEnvironment = (((Selection)sel).getEnvironment() != null && ((Selection)sel).getEnvironment().getName() != null) ? (
      (Selection)sel).getEnvironment().getName().trim() : "";
    if (thisEnvironment.equals("") && !thatEnvironment.equals(""))
      return 1; 
    if (!thisEnvironment.equals("") && thatEnvironment.equals(""))
      return -1; 
    return thisEnvironment.compareTo(thatEnvironment);
  }
  
  public Environment getEnvironment() { return this.environment; }
  
  public void setEnvironment(Environment environment) { this.environment = environment; }
  
  public int getEnvironmentId() { return this.environmentId; }
  
  public void setEnvironmentId(int environmentId) { this.environmentId = environmentId; }
  
  public Calendar getDigitalRlsDate() { return this.digital_rls_date; }
  
  public void setDigitalRlsDate(Calendar digital_rls_date) { this.digital_rls_date = digital_rls_date; }
  
  public String getDigitalRlsDateString() { return this.digital_rls_date_string; }
  
  public void setDigitalRlsDateString(String digital_rls_date_string) { this.digital_rls_date_string = digital_rls_date_string; }
  
  public String getOperCompany() { return this.oper_company; }
  
  public void setOperCompany(String oper_company) { this.oper_company = oper_company; }
  
  public String getSuperLabel() { return this.super_label; }
  
  public void setSuperLabel(String super_label) { this.super_label = super_label; }
  
  public String getSubLabel() { return this.sub_label; }
  
  public void setSubLabel(String sub_label) { this.sub_label = sub_label; }
  
  public String getConfigCode() { return this.config_code; }
  
  public void setConfigCode(String config_code) { this.config_code = config_code; }
  
  public String getSoundScanGrp() { return this.soundscan_grp; }
  
  public void setSoundScanGrp(String soundscan_grp) { this.soundscan_grp = soundscan_grp; }
  
  public boolean getInternationalFlag() { return this.international_flag; }
  
  public void setInternationalFlag(boolean international_flag) { this.international_flag = international_flag; }
  
  public String getImprint() { return this.imprint; }
  
  public void setImprint(String imprint) { this.imprint = imprint; }
  
  public boolean getIsDigital() { return this.isDigital; }
  
  public void setIsDigital(boolean isDigital) { this.isDigital = isDigital; }
  
  public boolean getNewBundleFlag() { return this.new_bundle_flag; }
  
  public void setNewBundleFlag(boolean new_bundle_flag) { this.new_bundle_flag = new_bundle_flag; }
  
  public String getGridNumber() { return this.grid_number; }
  
  public void setGridNumber(String grid_number) { this.grid_number = grid_number; }
  
  public String getSpecialInstructions() { return this.special_instructions; }
  
  public void setSpecialInstructions(String special_instructions) { this.special_instructions = special_instructions; }
  
  public int getArchimedesId() { return this.archimedesId; }
  
  public void setArchimedesId(int archimedesId) { this.archimedesId = archimedesId; }
  
  public int getReleaseFamilyId() { return this.releaseFamilyId; }
  
  public void setReleaseFamilyId(int releaseFamilyId) {
    this.releaseFamilyId = releaseFamilyId;
    setCalendarGroup();
  }
  
  public boolean getPriority() { return this.priority_flag; }
  
  public void setPriority(boolean priority_flag) { this.priority_flag = priority_flag; }
  
  public int getCalendarGroup() { return this.calendarGroup; }
  
  protected void setCalendarGroup() {
    String releaseFamilyName = MilestoneHelper.getStructureName(getReleaseFamilyId());
    if (releaseFamilyName.equals("Canada")) {
      this.calendarGroup = 2;
    } else {
      this.calendarGroup = 1;
    } 
  }
}


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\Selection.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */