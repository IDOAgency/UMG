/*      */ package WEB-INF.classes.com.universal.milestone;
/*      */ 
/*      */ import com.universal.milestone.Bom;
/*      */ import com.universal.milestone.Cache;
/*      */ import com.universal.milestone.Company;
/*      */ import com.universal.milestone.Division;
/*      */ import com.universal.milestone.Environment;
/*      */ import com.universal.milestone.Family;
/*      */ import com.universal.milestone.Genre;
/*      */ import com.universal.milestone.ImpactDate;
/*      */ import com.universal.milestone.Label;
/*      */ import com.universal.milestone.LookupObject;
/*      */ import com.universal.milestone.MilestoneDataEntity;
/*      */ import com.universal.milestone.MilestoneHelper;
/*      */ import com.universal.milestone.NotepadContentObject;
/*      */ import com.universal.milestone.PrefixCode;
/*      */ import com.universal.milestone.PriceCode;
/*      */ import com.universal.milestone.ProductCategory;
/*      */ import com.universal.milestone.ReleaseType;
/*      */ import com.universal.milestone.Schedule;
/*      */ import com.universal.milestone.Selection;
/*      */ import com.universal.milestone.SelectionConfiguration;
/*      */ import com.universal.milestone.SelectionManager;
/*      */ import com.universal.milestone.SelectionStatus;
/*      */ import com.universal.milestone.SelectionSubConfiguration;
/*      */ import com.universal.milestone.User;
/*      */ import java.util.Calendar;
/*      */ import java.util.Vector;
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
/*      */ public class Selection
/*      */   extends MilestoneDataEntity
/*      */   implements Cloneable, NotepadContentObject, Comparable
/*      */ {
/*   58 */   protected int selectionID = -1;
/*      */   
/*   60 */   protected int poQuantity = -1;
/*   61 */   protected int completedQuantity = -1;
/*   62 */   protected int numberOfUnits = -1;
/*      */   
/*      */   protected boolean holdSelection;
/*      */   
/*      */   protected boolean noDigitalRelease;
/*      */   protected boolean pressAndDistribution;
/*      */   protected boolean specialPackaging;
/*   69 */   protected User lastUpdatingUser = null;
/*   70 */   protected User lastMfgUpdatingUser = null;
/*      */   
/*      */   protected SelectionConfiguration selectionConfig;
/*      */   
/*      */   protected SelectionSubConfiguration selectionSubConfig;
/*   75 */   protected Genre genre = null;
/*   76 */   protected PrefixCode prefixID = null;
/*   77 */   protected PrefixCode auditPrefixID = null;
/*   78 */   protected ProductCategory productCategory = null;
/*   79 */   protected ReleaseType releaseType = null;
/*   80 */   protected SelectionStatus selectionStatus = null;
/*      */   
/*   82 */   protected LookupObject plant = null;
/*   83 */   protected LookupObject distribution = null;
/*      */   
/*   85 */   protected String titleID = "";
/*   86 */   protected String taskname = "";
/*   87 */   protected String department = "";
/*   88 */   protected String sellCode = "";
/*   89 */   protected String sellCodeDPC = "";
/*   90 */   protected String selectionNo = "";
/*   91 */   protected String projectID = "";
/*   92 */   protected String streetDateString = "";
/*   93 */   protected String internationalDateString = "";
/*   94 */   protected String impactDateString = "";
/*   95 */   protected String lastUpdateDateString = "";
/*   96 */   protected String lastMfgUpdateDateString = "";
/*   97 */   protected String holdReason = "";
/*   98 */   protected String upc = "";
/*   99 */   protected String artistFirstName = "";
/*  100 */   protected String artistLastName = "";
/*  101 */   protected String artist = "";
/*  102 */   protected String flArtist = "";
/*  103 */   protected String title = "";
/*  104 */   protected String aSide = "";
/*  105 */   protected String bSide = "";
/*  106 */   protected String selectionPackaging = "";
/*      */   
/*  108 */   protected String auditUPC = "";
/*  109 */   protected String auditSelectionNo = "";
/*  110 */   protected SelectionStatus auditSelectionStatus = null;
/*      */   
/*  112 */   protected String selectionTerritory = "";
/*  113 */   protected String otherContact = "";
/*  114 */   protected String manufacturingComments = "";
/*  115 */   protected String selectionComments = "";
/*  116 */   protected String retailCode = "";
/*      */   
/*  118 */   protected Calendar streetDate = null;
/*  119 */   protected Calendar auditStreetDate = null;
/*  120 */   protected Calendar auditDate = null;
/*  121 */   protected Calendar completionDate = null;
/*  122 */   protected Calendar dueDate = null;
/*  123 */   protected Calendar internationalDate = null;
/*  124 */   protected Calendar impactDate = null;
/*  125 */   protected Calendar lastUpdateDate = null;
/*  126 */   protected Calendar lastMfgUpdateDate = null;
/*  127 */   protected Calendar lastStreetUpdateDate = null;
/*  128 */   protected Calendar originDate = null;
/*  129 */   protected Calendar archieDate = null;
/*  130 */   protected Calendar lastLegacyUpdateDate = null;
/*      */   
/*  132 */   protected Calendar lastSchedUpdateDate = null;
/*  133 */   protected User lastSchedUpdatingUser = null;
/*      */   
/*  135 */   protected long lastUpdatedCheck = -1L;
/*  136 */   protected long lastMfgUpdatedCheck = -1L;
/*      */   
/*      */   protected float price;
/*      */   
/*      */   protected Family family;
/*      */   
/*      */   protected Environment environment;
/*      */   
/*      */   protected Company company;
/*      */   
/*      */   protected Division division;
/*      */   
/*      */   protected Label label;
/*      */   protected PriceCode priceCode;
/*      */   protected PriceCode priceCodeDPC;
/*      */   protected User labelContact;
/*      */   protected User umlContact;
/*      */   protected Schedule schedule;
/*      */   protected Bom bom;
/*  155 */   protected int familyId = -1;
/*  156 */   protected int environmentId = -1;
/*  157 */   protected int companyId = -1;
/*  158 */   protected int divisionId = -1;
/*  159 */   protected int labelId = -1;
/*  160 */   protected int labelContactId = -1;
/*  161 */   protected int scheduleId = -1;
/*      */   
/*  163 */   protected int calendarGroup = -1;
/*      */ 
/*      */   
/*      */   protected ImpactDate impactDateObject;
/*      */   
/*      */   protected boolean fullSelection = false;
/*      */   
/*  170 */   protected int sortBy = -1;
/*      */ 
/*      */   
/*  173 */   protected int templateId = -1;
/*      */ 
/*      */   
/*      */   protected boolean parentalGuidance;
/*      */ 
/*      */   
/*  179 */   protected Vector impactDates = null;
/*      */ 
/*      */   
/*  182 */   protected Vector manufacturingPlants = null;
/*      */ 
/*      */   
/*  185 */   protected Vector multSelections = null;
/*      */ 
/*      */   
/*  188 */   protected Vector multOtherContacts = null;
/*      */ 
/*      */ 
/*      */   
/*  192 */   protected Calendar digital_rls_date = null;
/*  193 */   protected String digital_rls_date_string = "";
/*  194 */   protected String oper_company = "";
/*  195 */   protected String super_label = "";
/*  196 */   protected String sub_label = "";
/*  197 */   protected String config_code = "";
/*  198 */   protected String soundscan_grp = "";
/*      */ 
/*      */   
/*      */   protected boolean international_flag;
/*      */   
/*      */   protected boolean isDigital = false;
/*      */   
/*  205 */   protected String imprint = "";
/*      */   protected boolean new_bundle_flag = false;
/*  207 */   protected String grid_number = "";
/*  208 */   protected String special_instructions = "";
/*      */ 
/*      */   
/*      */   protected boolean priority_flag = false;
/*      */   
/*  213 */   protected int archimedesId = -1;
/*  214 */   protected int releaseFamilyId = -1;
/*      */ 
/*      */   
/*  217 */   protected Calendar autoCloseDate = null;
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
/*  229 */   public int getIdentity() { return getSelectionID(); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  238 */   public String getTableName() { return "Release_Header"; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int compareTo(Object sel) throws ClassCastException {
/*  248 */     switch (this.sortBy) {
/*      */       
/*      */       case 1:
/*  251 */         return compareByStreetDate(sel);
/*      */       
/*      */       case 2:
/*  254 */         return compareByProductCategory(sel);
/*      */       
/*      */       case 3:
/*  257 */         return compareByArtist(sel);
/*      */       
/*      */       case 4:
/*  260 */         return compareByTitle(sel);
/*      */       
/*      */       case 5:
/*  263 */         return compareByUpc(sel);
/*      */       
/*      */       case 7:
/*  266 */         return compareByDivision(sel);
/*      */       
/*      */       case 9:
/*  269 */         return compareByLabel(sel);
/*      */       
/*      */       case 14:
/*  272 */         return compareByPrefixSelectionId(sel);
/*      */       
/*      */       case 12:
/*  275 */         return compareBySelectionNo(sel);
/*      */       
/*      */       case 6:
/*  278 */         return compareByFamily(sel);
/*      */       
/*      */       case 23:
/*  281 */         return compareByEnvironment(sel);
/*      */       
/*      */       case 8:
/*  284 */         return compareByCompany(sel);
/*      */       
/*      */       case 13:
/*  287 */         return compareBySubConfig(sel);
/*      */       
/*      */       case 10:
/*  290 */         return compareByStatus(sel);
/*      */       
/*      */       case 16:
/*  293 */         return compareBySpecialStatus(sel);
/*      */       
/*      */       case 17:
/*  296 */         return compareByStreetDateBlankAtEnd(sel);
/*      */       
/*      */       case 21:
/*  299 */         return compareByPackagingSpecs(sel);
/*      */       
/*      */       case 22:
/*  302 */         return compareByFlArtist(sel);
/*      */       
/*      */       case 19:
/*  305 */         return compareByImpactDate(sel);
/*      */       
/*      */       case 24:
/*  308 */         return compareByImprint(sel);
/*      */       
/*      */       case 25:
/*  311 */         return compareByPriority(sel);
/*      */       
/*      */       case 26:
/*  314 */         return compareByDigitalStreetDate(sel);
/*      */       
/*      */       case 27:
/*  317 */         return compareByAuditDate(sel);
/*      */       
/*      */       case 20:
/*  320 */         return compareByReverseStreetDate(sel);
/*      */     } 
/*      */     
/*  323 */     return compareByStreetDate(sel);
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
/*      */   protected int compareByAuditDate(Object sel) throws ClassCastException {
/*  335 */     Calendar thisDate = this.auditDate;
/*  336 */     Calendar thatDate = ((Selection)sel).auditDate;
/*      */     
/*  338 */     if (thisDate == null && thatDate == null) {
/*  339 */       return 0;
/*      */     }
/*  341 */     if (thisDate == null) {
/*  342 */       return 1;
/*      */     }
/*  344 */     if (thatDate == null) {
/*  345 */       return -1;
/*      */     }
/*  347 */     if (thisDate.before(thatDate)) {
/*  348 */       return -1;
/*      */     }
/*  350 */     if (thisDate.after(thatDate)) {
/*  351 */       return 1;
/*      */     }
/*      */     
/*  354 */     return 0;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected int compareByDigitalStreetDate(Object sel) throws ClassCastException {
/*  365 */     Calendar thisStreetDate = this.digital_rls_date;
/*  366 */     Calendar thatStreetDate = ((Selection)sel).digital_rls_date;
/*      */     
/*  368 */     if (thisStreetDate == null && thatStreetDate == null) {
/*  369 */       return 0;
/*      */     }
/*  371 */     if (thisStreetDate == null) {
/*  372 */       return 1;
/*      */     }
/*  374 */     if (thatStreetDate == null) {
/*  375 */       return -1;
/*      */     }
/*  377 */     if (thisStreetDate.before(thatStreetDate)) {
/*  378 */       return -1;
/*      */     }
/*  380 */     if (thisStreetDate.after(thatStreetDate)) {
/*  381 */       return 1;
/*      */     }
/*      */     
/*  384 */     return 0;
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
/*      */   protected int compareByPriority(Object sel) throws ClassCastException {
/*  396 */     boolean thisPriority = getPriority();
/*      */     
/*  398 */     boolean thatPriority = ((Selection)sel).getPriority();
/*      */ 
/*      */     
/*  401 */     if (thisPriority && !thatPriority)
/*  402 */       return -1; 
/*  403 */     if (!thisPriority && thatPriority) {
/*  404 */       return 1;
/*      */     }
/*  406 */     return 0;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected int compareByImprint(Object sel) throws ClassCastException {
/*  416 */     String thisImprint = (getImprint() != null) ? getImprint().trim() : "";
/*  417 */     String thatImprint = (((Selection)sel).getImprint() != null) ? ((Selection)sel).getImprint().trim() : "";
/*      */     
/*  419 */     if (thisImprint.equals("") && !thatImprint.equals("")) {
/*  420 */       return 1;
/*      */     }
/*  422 */     if (!thisImprint.equals("") && thatImprint.equals("")) {
/*  423 */       return -1;
/*      */     }
/*      */     
/*  426 */     return thisImprint.compareToIgnoreCase(thatImprint);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected int compareBySubConfig(Object sel) throws ClassCastException {
/*  436 */     String thisSubConfig = (getSelectionConfig() != null) ? getSelectionConfig().getSelectionConfigurationName() : "";
/*      */ 
/*      */     
/*  439 */     String thatSubConfig = (((Selection)sel).getSelectionConfig() != null) ? ((Selection)sel).getSelectionConfig().getSelectionConfigurationName() : "";
/*      */     
/*  441 */     return thisSubConfig.compareTo(thatSubConfig);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected int compareByStreetDate(Object sel) throws ClassCastException {
/*  450 */     Calendar thisStreetDate = this.streetDate;
/*  451 */     Calendar thatStreetDate = ((Selection)sel).streetDate;
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
/*  518 */     if (thisStreetDate == null && thatStreetDate == null) {
/*  519 */       return 0;
/*      */     }
/*  521 */     if (thisStreetDate == null) {
/*  522 */       return 1;
/*      */     }
/*  524 */     if (thatStreetDate == null) {
/*  525 */       return -1;
/*      */     }
/*  527 */     if (thisStreetDate.before(thatStreetDate)) {
/*  528 */       return -1;
/*      */     }
/*  530 */     if (thisStreetDate.after(thatStreetDate)) {
/*  531 */       return 1;
/*      */     }
/*      */     
/*  534 */     return 0;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected int compareByReverseStreetDate(Object sel) throws ClassCastException {
/*  543 */     Calendar thisStreetDate = this.streetDate;
/*  544 */     Calendar thatStreetDate = ((Selection)sel).streetDate;
/*      */     
/*  546 */     if (thisStreetDate == null && thatStreetDate == null) {
/*  547 */       return 0;
/*      */     }
/*  549 */     if (thisStreetDate == null) {
/*  550 */       return -1;
/*      */     }
/*  552 */     if (thatStreetDate == null) {
/*  553 */       return 1;
/*      */     }
/*  555 */     if (thisStreetDate.before(thatStreetDate)) {
/*  556 */       return 1;
/*      */     }
/*  558 */     if (thisStreetDate.after(thatStreetDate)) {
/*  559 */       return -1;
/*      */     }
/*      */     
/*  562 */     return 0;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected int compareByStreetDateBlankAtEnd(Object sel) throws ClassCastException {
/*  572 */     Calendar thisStreetDate = this.streetDate;
/*  573 */     Calendar thatStreetDate = ((Selection)sel).streetDate;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  578 */     if (thisStreetDate == null) {
/*  579 */       return 1;
/*      */     }
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
/*  591 */     return 0;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected int compareByProductCategory(Object sel) throws ClassCastException {
/*  601 */     LookupObject prodCategory = SelectionManager.getLookupObject(getProductCategory().getAbbreviation(), 
/*  602 */         Cache.getProductCategories());
/*  603 */     String thisProdCategoryName = (prodCategory != null && prodCategory.getName() != null) ? 
/*  604 */       prodCategory.getName() : "";
/*      */ 
/*      */     
/*  607 */     prodCategory = SelectionManager.getLookupObject(((Selection)sel).getProductCategory().getAbbreviation(), 
/*  608 */         Cache.getProductCategories());
/*  609 */     String thatProdCategoryName = (prodCategory != null && prodCategory.getName() != null) ? 
/*  610 */       prodCategory.getName() : "";
/*      */     
/*  612 */     if (thisProdCategoryName.equals("") && !thatProdCategoryName.equals("")) {
/*  613 */       return 1;
/*      */     }
/*  615 */     if (!thisProdCategoryName.equals("") && thatProdCategoryName.equals("")) {
/*  616 */       return -1;
/*      */     }
/*  618 */     return thisProdCategoryName.compareTo(thatProdCategoryName);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected int compareByArtist(Object sel) throws ClassCastException {
/*  627 */     String thisArtist = (getArtist() != null) ? getArtist().trim() : "";
/*  628 */     String thatArtist = (((Selection)sel).getArtist() != null) ? ((Selection)sel).getArtist().trim() : "";
/*      */     
/*  630 */     if (thisArtist.equals("") && !thatArtist.equals("")) {
/*  631 */       return 1;
/*      */     }
/*  633 */     if (!thisArtist.equals("") && thatArtist.equals("")) {
/*  634 */       return -1;
/*      */     }
/*      */     
/*  637 */     return thisArtist.compareToIgnoreCase(thatArtist);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected int compareByFlArtist(Object sel) throws ClassCastException {
/*  646 */     String thisFlArtist = (getFlArtist() != null) ? getFlArtist().trim() : "";
/*  647 */     String thatFlArtist = (((Selection)sel).getFlArtist() != null) ? ((Selection)sel).getFlArtist().trim() : "";
/*      */     
/*  649 */     if (thisFlArtist.equals("") && !thatFlArtist.equals("")) {
/*  650 */       return 1;
/*      */     }
/*  652 */     if (!thisFlArtist.equals("") && thatFlArtist.equals("")) {
/*  653 */       return -1;
/*      */     }
/*      */     
/*  656 */     return thisFlArtist.compareToIgnoreCase(thatFlArtist);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected int compareByTitle(Object sel) throws ClassCastException {
/*  667 */     String thisTitle = (getTitle() != null) ? getTitle().trim() : "";
/*  668 */     String thatTitle = (((Selection)sel).getTitle() != null) ? ((Selection)sel).getTitle().trim() : "";
/*      */     
/*  670 */     if (thisTitle.equals("") && !thatTitle.equals("")) {
/*  671 */       return 1;
/*      */     }
/*  673 */     if (!thisTitle.equals("") && thatTitle.equals("")) {
/*  674 */       return -1;
/*      */     }
/*      */     
/*  677 */     return thisTitle.compareToIgnoreCase(thatTitle);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected int compareByPackagingSpecs(Object sel) throws ClassCastException {
/*  687 */     String thisPackagingSpec = (getSelectionPackaging() != null) ? getSelectionPackaging().trim() : "";
/*  688 */     String thatPackagingSpec = (((Selection)sel).getSelectionPackaging() != null) ? ((Selection)sel).getSelectionPackaging().trim() : "";
/*      */     
/*  690 */     if (thisPackagingSpec.equals("") && !thatPackagingSpec.equals("")) {
/*  691 */       return 1;
/*      */     }
/*  693 */     if (!thisPackagingSpec.equals("") && thatPackagingSpec.equals("")) {
/*  694 */       return -1;
/*      */     }
/*  696 */     return thisPackagingSpec.compareTo(thatPackagingSpec);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected int compareByUpc(Object sel) throws ClassCastException {
/*  705 */     String thisUpc = (getUpc() != null) ? getUpc() : "";
/*  706 */     String thatUpc = (((Selection)sel).getUpc() != null) ? ((Selection)sel).getUpc() : "";
/*      */     
/*  708 */     if (thisUpc.equals("") && !thatUpc.equals("")) {
/*  709 */       return 1;
/*      */     }
/*  711 */     if (!thisUpc.equals("") && thatUpc.equals("")) {
/*  712 */       return -1;
/*      */     }
/*  714 */     return thisUpc.compareTo(thatUpc);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected int compareByDivision(Object sel) throws ClassCastException {
/*  723 */     String thisDivision = (getDivision() != null && getDivision().getName() != null) ? 
/*  724 */       getDivision().getName().trim() : "";
/*      */     
/*  726 */     String thatDivision = (((Selection)sel).getDivision() != null && ((Selection)sel).getDivision().getName() != null) ? (
/*  727 */       (Selection)sel).getDivision().getName().trim() : "";
/*      */     
/*  729 */     if (thisDivision.equals("") && !thatDivision.equals("")) {
/*  730 */       return 1;
/*      */     }
/*  732 */     if (!thisDivision.equals("") && thatDivision.equals("")) {
/*  733 */       return -1;
/*      */     }
/*  735 */     return thisDivision.compareTo(thatDivision);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected int compareByLabel(Object sel) throws ClassCastException {
/*  744 */     String thisLabel = (getLabel() != null && getLabel().getName() != null) ? 
/*  745 */       getLabel().getName().trim() : "";
/*      */     
/*  747 */     String thatLabel = (((Selection)sel).getLabel() != null && ((Selection)sel).getLabel().getName() != null) ? (
/*  748 */       (Selection)sel).getLabel().getName().trim() : "";
/*      */     
/*  750 */     if (thisLabel.equals("") && !thatLabel.equals("")) {
/*  751 */       return 1;
/*      */     }
/*  753 */     if (!thisLabel.equals("") && thatLabel.equals("")) {
/*  754 */       return -1;
/*      */     }
/*  756 */     return thisLabel.compareTo(thatLabel);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected int compareByPrefixSelectionId(Object sel) throws ClassCastException {
/*  767 */     String thisSelectionNo = SelectionManager.getLookupObjectValue(getPrefixID());
/*  768 */     if (thisSelectionNo == null)
/*  769 */       thisSelectionNo = ""; 
/*  770 */     thisSelectionNo = String.valueOf(thisSelectionNo) + getSelectionNo();
/*      */ 
/*      */     
/*  773 */     String thatSelectionNo = SelectionManager.getLookupObjectValue(((Selection)sel).getPrefixID());
/*  774 */     if (thatSelectionNo == null)
/*  775 */       thatSelectionNo = ""; 
/*  776 */     thatSelectionNo = String.valueOf(thatSelectionNo) + ((Selection)sel).getSelectionNo();
/*      */     
/*  778 */     if (thisSelectionNo.equals("") && !thatSelectionNo.equals("")) {
/*  779 */       return 1;
/*      */     }
/*  781 */     if (!thisSelectionNo.equals("") && thatSelectionNo.equals("")) {
/*  782 */       return -1;
/*      */     }
/*  784 */     return thisSelectionNo.compareTo(thatSelectionNo);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected int compareBySelectionNo(Object sel) throws ClassCastException {
/*  794 */     String thisSelectionNo = (getSelectionNo() != null) ? getSelectionNo() : "";
/*      */ 
/*      */     
/*  797 */     String thatSelectionNo = (((Selection)sel).getSelectionNo() != null) ? ((Selection)sel).getSelectionNo() : "";
/*      */     
/*  799 */     if (thisSelectionNo.equals("") && !thatSelectionNo.equals("")) {
/*  800 */       return 1;
/*      */     }
/*  802 */     if (!thisSelectionNo.equals("") && thatSelectionNo.equals("")) {
/*  803 */       return -1;
/*      */     }
/*  805 */     return thisSelectionNo.compareTo(thatSelectionNo);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected int compareByFamily(Object sel) throws ClassCastException {
/*  815 */     String thisFamily = (getFamily() != null && getFamily().getName() != null) ? 
/*  816 */       getFamily().getName().trim() : "";
/*      */ 
/*      */     
/*  819 */     String thatFamily = (((Selection)sel).getFamily() != null && ((Selection)sel).getFamily().getName() != null) ? (
/*  820 */       (Selection)sel).getFamily().getName().trim() : "";
/*      */     
/*  822 */     if (thisFamily.equals("") && !thatFamily.equals("")) {
/*  823 */       return 1;
/*      */     }
/*  825 */     if (!thisFamily.equals("") && thatFamily.equals("")) {
/*  826 */       return -1;
/*      */     }
/*  828 */     return thisFamily.compareTo(thatFamily);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected int compareByStatus(Object sel) throws ClassCastException {
/*  838 */     String thisStatus = (getSelectionStatus() != null && getSelectionStatus().getName() != null) ? 
/*  839 */       getSelectionStatus().getName().trim() : "";
/*      */ 
/*      */     
/*  842 */     String thatStatus = (((Selection)sel).getSelectionStatus() != null && ((Selection)sel).getSelectionStatus().getName() != null) ? (
/*  843 */       (Selection)sel).getSelectionStatus().getName().trim() : "";
/*      */     
/*  845 */     if (thisStatus.equals("") && !thatStatus.equals("")) {
/*  846 */       return 1;
/*      */     }
/*  848 */     if (!thisStatus.equals("") && thatStatus.equals("")) {
/*  849 */       return -1;
/*      */     }
/*  851 */     return thisStatus.compareTo(thatStatus);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected int compareBySpecialStatus(Object sel) throws ClassCastException {
/*  862 */     String thisStatus = (getSelectionStatus() != null && getSelectionStatus().getName() != null) ? 
/*  863 */       getSelectionStatus().getName().trim() : "";
/*      */     
/*  865 */     if (!thisStatus.equalsIgnoreCase("TBS") && !thisStatus.equalsIgnoreCase("In The Works"))
/*      */     {
/*  867 */       thisStatus = "A";
/*      */     }
/*      */ 
/*      */     
/*  871 */     String thatStatus = (((Selection)sel).getSelectionStatus() != null && ((Selection)sel).getSelectionStatus().getName() != null) ? (
/*  872 */       (Selection)sel).getSelectionStatus().getName().trim() : "";
/*      */     
/*  874 */     if (!thatStatus.equalsIgnoreCase("TBS") && !thatStatus.equalsIgnoreCase("In The Works"))
/*      */     {
/*  876 */       thatStatus = "A";
/*      */     }
/*      */     
/*  879 */     if (thisStatus.equals("") && !thatStatus.equals("")) {
/*  880 */       return 1;
/*      */     }
/*  882 */     if (!thisStatus.equals("") && thatStatus.equals("")) {
/*  883 */       return -1;
/*      */     }
/*  885 */     return thisStatus.compareTo(thatStatus);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected int compareByCompany(Object sel) throws ClassCastException {
/*  895 */     String thisCompany = (getCompany() != null && getCompany().getName() != null) ? 
/*  896 */       getCompany().getName().trim() : "";
/*      */ 
/*      */     
/*  899 */     String thatCompany = (((Selection)sel).getCompany() != null && ((Selection)sel).getCompany().getName() != null) ? (
/*  900 */       (Selection)sel).getCompany().getName().trim() : "";
/*      */     
/*  902 */     if (thisCompany.equals("") && !thatCompany.equals("")) {
/*  903 */       return 1;
/*      */     }
/*  905 */     if (!thisCompany.equals("") && thatCompany.equals("")) {
/*  906 */       return -1;
/*      */     }
/*  908 */     return thisCompany.compareTo(thatCompany);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected int compareByImpactDate(Object sel) throws ClassCastException {
/*  917 */     Calendar thisImpactDate = this.impactDate;
/*  918 */     Calendar thatImpactDate = ((Selection)sel).impactDate;
/*      */     
/*  920 */     if (thisImpactDate == null && thatImpactDate == null) {
/*  921 */       return 0;
/*      */     }
/*  923 */     if (thisImpactDate == null) {
/*  924 */       return 1;
/*      */     }
/*  926 */     if (thatImpactDate == null) {
/*  927 */       return -1;
/*      */     }
/*  929 */     if (thisImpactDate.before(thatImpactDate)) {
/*  930 */       return -1;
/*      */     }
/*  932 */     if (thisImpactDate.after(thatImpactDate)) {
/*  933 */       return 1;
/*      */     }
/*      */     
/*  936 */     return 0;
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
/* 1001 */   public int getSelectionID() { return this.selectionID; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1011 */   public void setSelectionID(int id) { this.selectionID = id; }
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
/* 1022 */   public String getSelectionNo() { return this.selectionNo; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setSelectionNo(String no) {
/* 1032 */     auditCheck("selection_no", this.selectionNo, no);
/* 1033 */     this.selectionNo = no;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1043 */   public Calendar getStreetDate() { return this.streetDate; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1053 */   public void setStreetDate(Calendar date) { this.streetDate = date; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1063 */   public Calendar getAuditStreetDate() { return this.auditStreetDate; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1073 */   public void setAuditStreetDate(Calendar date) { this.auditStreetDate = date; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1083 */   public Calendar getAuditDate() { return this.auditDate; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1093 */   public void setAuditDate(Calendar date) { this.auditDate = date; }
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
/* 1104 */   public String getAuditSelectionNo() { return this.auditSelectionNo; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1114 */   public void setAuditSelectionNo(String no) { this.auditSelectionNo = no; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1123 */   public PrefixCode getAuditPrefixID() { return this.auditPrefixID; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1133 */   public void setAuditPrefixID(PrefixCode prefixCode) { this.auditPrefixID = prefixCode; }
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
/* 1144 */   public String getAuditUPC() { return this.auditUPC; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1154 */   public void setAuditUPC(String no) { this.auditUPC = no; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1163 */   public SelectionStatus getAuditSelectionStatus() { return this.auditSelectionStatus; }
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
/* 1176 */   public void setAuditSelectionStatus(SelectionStatus status) { this.auditSelectionStatus = status; }
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
/* 1189 */   public Calendar getInternationalDate() { return this.internationalDate; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1199 */   public void setInternationalDate(Calendar date) { this.internationalDate = date; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1207 */   public Calendar getImpactDate() { return this.impactDate; }
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
/* 1218 */   public void setImpactDate(Calendar date) { this.impactDate = date; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1226 */   public ImpactDate getImpactDateObject() { return this.impactDateObject; }
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
/* 1237 */   public void setImpactDateObject(ImpactDate date) { this.impactDateObject = date; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1245 */   public SelectionStatus getSelectionStatus() { return this.selectionStatus; }
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
/* 1257 */   public void setSelectionStatus(SelectionStatus status) { this.selectionStatus = status; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1267 */   public boolean getHoldSelection() { return this.holdSelection; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1277 */   public void setHoldSelection(boolean isOnHold) { this.holdSelection = isOnHold; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1287 */   public boolean getNoDigitalRelease() { return this.noDigitalRelease; }
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
/* 1298 */   public void setNoDigitalRelease(boolean noDigRel) { this.noDigitalRelease = noDigRel; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1307 */   public String getHoldReason() { return this.holdReason; }
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
/* 1318 */   public void setHoldReason(String reason) { this.holdReason = reason; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1328 */   public boolean getPressAndDistribution() { return this.pressAndDistribution; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1338 */   public void setPressAndDistribution(boolean isPressAndDistribution) { this.pressAndDistribution = isPressAndDistribution; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1347 */   public String getProjectID() { return this.projectID; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1357 */   public void setProjectID(String idNumber) { this.projectID = idNumber; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1365 */   public boolean getSpecialPackaging() { return this.specialPackaging; }
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
/* 1376 */   public void setSpecialPackaging(boolean hasSpecialPackaging) { this.specialPackaging = hasSpecialPackaging; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1385 */   public PrefixCode getPrefixID() { return this.prefixID; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1395 */   public void setPrefixID(PrefixCode prefixCode) { this.prefixID = prefixCode; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1403 */   public String getTitleID() { return this.titleID; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1413 */   public void setTitleID(String titleIdNumber) { this.titleID = titleIdNumber; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1422 */   public String getUpc() { return this.upc; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setUpc(String upcCode) {
/* 1433 */     auditCheck("upc", this.upc, upcCode);
/* 1434 */     this.upc = upcCode;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1442 */   public String getArtistFirstName() { return this.artistFirstName; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1452 */   public void setArtistFirstName(String firstName) { this.artistFirstName = firstName; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1460 */   public String getArtistLastName() { return this.artistLastName; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1470 */   public void setArtistLastName(String lastName) { this.artistLastName = lastName; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1478 */   public void setArtist(String artistName) { this.artist = artistName; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1486 */   public String getArtist() { return this.artist; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1494 */   public void setFlArtist(String artistName) { this.flArtist = artistName; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1502 */   public String getFlArtist() { return this.flArtist; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1510 */   public String getTitle() { return this.title; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1520 */   public void setTitle(String theTitle) { this.title = theTitle; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1528 */   public String getASide() { return this.aSide; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1538 */   public void setASide(String aSideText) { this.aSide = aSideText; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1546 */   public String getBSide() { return this.bSide; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1556 */   public void setBSide(String bSideText) { this.bSide = bSideText; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1564 */   public ProductCategory getProductCategory() { return this.productCategory; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1574 */   public void setProductCategory(ProductCategory productCategory) { this.productCategory = productCategory; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1582 */   public ReleaseType getReleaseType() { return this.releaseType; }
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
/* 1593 */   public void setReleaseType(ReleaseType releaseType) { this.releaseType = releaseType; }
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
/* 1604 */   public SelectionConfiguration getSelectionConfig() { return this.selectionConfig; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1613 */   public void setSelectionConfig(SelectionConfiguration selectionConfig) { this.selectionConfig = selectionConfig; }
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
/* 1624 */   public SelectionSubConfiguration getSelectionSubConfig() { return this.selectionSubConfig; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1633 */   public void setSelectionSubConfig(SelectionSubConfiguration selectionSubConfig) { this.selectionSubConfig = selectionSubConfig; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1641 */   public Family getFamily() { return this.family; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1649 */   public void setFamily(Family family) { this.family = family; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1657 */   public Company getCompany() { return this.company; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1665 */   public void setCompany(Company company) { this.company = company; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1673 */   public Division getDivision() { return this.division; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1683 */   public void setDivision(Division division) { this.division = division; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1691 */   public Label getLabel() { return this.label; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1701 */   public void setLabel(Label label) { this.label = label; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1709 */   public String getSellCode() { return this.sellCode; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1717 */   public void setSellCode(String sellCode) { this.sellCode = sellCode; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1725 */   public String getSellCodeDPC() { return this.sellCodeDPC; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1733 */   public void setSellCodeDPC(String sellCodeDPC) { this.sellCodeDPC = sellCodeDPC; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1742 */   public String getRetailCode() { return this.retailCode; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1752 */   public void setRetailCode(String retailCode) { this.retailCode = retailCode; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1760 */   public PriceCode getPriceCode() { return this.priceCode; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1770 */   public void setPriceCode(PriceCode priceCode) { this.priceCode = priceCode; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1778 */   public PriceCode getPriceCodeDPC() { return this.priceCodeDPC; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1788 */   public void setPriceCodeDPC(PriceCode priceCodeDPC) { this.priceCodeDPC = priceCodeDPC; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1798 */   public float getPrice() { return this.price; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1808 */   public void setPrice(float price) { this.price = price; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1817 */   public int getNumberOfUnits() { return this.numberOfUnits; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1826 */   public void setNumberOfUnits(int numberOfUnits) { this.numberOfUnits = numberOfUnits; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1834 */   public Genre getGenre() { return this.genre; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1842 */   public void setGenre(Genre genre) { this.genre = genre; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1850 */   public String getSelectionPackaging() { return this.selectionPackaging; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1859 */   public void setSelectionPackaging(String selectionPackaging) { this.selectionPackaging = selectionPackaging; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1867 */   public User getLabelContact() { return this.labelContact; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1877 */   public void setLabelContact(User labelContact) { this.labelContact = labelContact; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1886 */   public String getOtherContact() { return this.otherContact; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1894 */   public void setOtherContact(String otherContact) { this.otherContact = otherContact; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1902 */   public Schedule getSchedule() { return this.schedule; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1910 */   public void setBom(Bom bom) { this.bom = bom; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1918 */   public Bom getBom() { return this.bom; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1926 */   public void setSchedule(Schedule schedule) { this.schedule = schedule; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1934 */   public User getLastUpdatingUser() { return this.lastUpdatingUser; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1944 */   public void setLastUpdatingUser(User lastUpdatingUser) { this.lastUpdatingUser = lastUpdatingUser; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1952 */   public User getLastMfgUpdatingUser() { return this.lastMfgUpdatingUser; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1962 */   public void setLastMfgUpdatingUser(User lastMfgUpdatingUser) { this.lastMfgUpdatingUser = lastMfgUpdatingUser; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1971 */   public Calendar getLastUpdateDate() { return this.lastUpdateDate; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1981 */   public void setLastUpdateDate(Calendar lastUpdateDate) { this.lastUpdateDate = lastUpdateDate; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1989 */   public Calendar getArchieDate() { return this.archieDate; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1999 */   public void setArchieDate(Calendar archieDate) { this.archieDate = archieDate; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2007 */   public Calendar getLastLegacyUpdateDate() { return this.lastLegacyUpdateDate; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2017 */   public void setLastLegacyUpdateDate(Calendar lastLegacyUpdateDate) { this.lastLegacyUpdateDate = lastLegacyUpdateDate; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2026 */   public Calendar getAutoCloseDate() { return this.autoCloseDate; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2036 */   public void setAutoCloseDate(Calendar autoCloseDate) { this.autoCloseDate = autoCloseDate; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2045 */   public Calendar getLastMfgUpdateDate() { return this.lastMfgUpdateDate; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2055 */   public void setLastMfgUpdateDate(Calendar lastMfgUpdateDate) { this.lastMfgUpdateDate = lastMfgUpdateDate; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2065 */   public Calendar getLastStreetUpdateDate() { return this.lastStreetUpdateDate; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2075 */   public void setLastStreetUpdateDate(Calendar lastStreetUpdateDate) { this.lastStreetUpdateDate = lastStreetUpdateDate; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2084 */   public String getSelectionTerritory() { return this.selectionTerritory; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2093 */   public void setSelectionTerritory(String selectionTerritory) { this.selectionTerritory = selectionTerritory; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2103 */   public User getUmlContact() { return this.umlContact; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2113 */   public void setUmlContact(User umlContact) { this.umlContact = umlContact; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2121 */   public LookupObject getPlant() { return this.plant; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2131 */   public void setPlant(LookupObject plant) { this.plant = plant; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2139 */   public LookupObject getDistribution() { return this.distribution; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2149 */   public void setDistribution(LookupObject distribution) { this.distribution = distribution; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2157 */   public int getPoQuantity() { return this.poQuantity; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2167 */   public void setPoQuantity(int poQuantity) { this.poQuantity = poQuantity; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2176 */   public int getCompletedQuantity() { return this.completedQuantity; }
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
/* 2187 */   public void setCompletedQuantity(int completedQuantity) { this.completedQuantity = completedQuantity; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2196 */   public String getSelectionComments() { return this.selectionComments; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2206 */   public void setComments(String selectionComments) { this.selectionComments = selectionComments; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2215 */   public String getManufacturingComments() { return this.manufacturingComments; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2225 */   public void setManufacturingComments(String manufacturingComments) { this.manufacturingComments = manufacturingComments; }
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
/* 2237 */   public int getFamilyId() { return this.familyId; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2246 */   public void setFamilyId(int familyId) { this.familyId = familyId; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2255 */   public int getCompanyId() { return this.companyId; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2264 */   public void setCompanyId(int companyId) { this.companyId = companyId; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2273 */   public int getDivisionId() { return this.divisionId; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2282 */   public void setDivisionId(int divisionId) { this.divisionId = divisionId; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2291 */   public int getLabelId() { return this.labelId; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2300 */   public void setLabelId(int labelId) { this.labelId = labelId; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2309 */   public int getLabelContactId() { return this.labelContactId; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2318 */   public void setLabelContactId(int labelContactId) { this.labelContactId = labelContactId; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2327 */   public int getScheduleId() { return this.scheduleId; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2336 */   public void setScheduleId(int scheduleId) { this.scheduleId = scheduleId; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2345 */   public void setTaskName(String name) { this.taskname = name; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2355 */   public String getTaskName() { return this.taskname; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2365 */   public void setDepartment(String p_department) { this.department = p_department; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2375 */   public String getDepartment() { return this.department; }
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
/* 2386 */   public Calendar getCompletionDate() { return this.completionDate; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2396 */   public void setCompletionDate(Calendar date) { this.completionDate = date; }
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
/* 2407 */   public void setDueDate(Calendar date) { this.dueDate = date; }
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
/* 2418 */   public Calendar getDueDate() { return this.dueDate; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2427 */   public String getStreetDateString() { return this.streetDateString; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2436 */   public void setStreetDateString(String streetDateString) { this.streetDateString = streetDateString; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2445 */   public String getInternationalDateString() { return this.internationalDateString; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2454 */   public void setInternationalDateString(String internationalDateString) { this.internationalDateString = internationalDateString; }
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
/* 2470 */   public boolean isFullSelection() { return this.fullSelection; }
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
/* 2485 */   public void setFullSelection(boolean fullSelection) { this.fullSelection = fullSelection; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2495 */   public String getImpactDateString() { return this.impactDateString; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2504 */   public void setImpactDateString(String impactDateString) { this.impactDateString = impactDateString; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2514 */   public String getLastUpdateDateString() { return this.lastUpdateDateString; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2523 */   public void setLastUpdateDateString(String lastUpdateDateString) { this.lastUpdateDateString = lastUpdateDateString; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2532 */   public String getLastMfgUpdateDateString() { return this.lastMfgUpdateDateString; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2541 */   public void setLastMfgUpdateDateString(String lastMfgUpdateDateString) { this.lastMfgUpdateDateString = lastMfgUpdateDateString; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2550 */   public long getLastUpdatedCheck() { return this.lastUpdatedCheck; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2559 */   public void setLastUpdatedCheck(long lastUpdatedCheck) { this.lastUpdatedCheck = lastUpdatedCheck; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2569 */   public Calendar getOriginDate() { return this.originDate; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2578 */   public void setOriginDate(Calendar originDate) { this.originDate = originDate; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2588 */   public long getLastMfgUpdatedCheck() { return this.lastMfgUpdatedCheck; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2597 */   public void setLastMfgUpdatedCheck(long lastMfgUpdatedCheck) { this.lastMfgUpdatedCheck = lastMfgUpdatedCheck; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2606 */   public int getTemplateId() { return this.templateId; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2615 */   public void setTemplateId(int id) { this.templateId = id; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2625 */   public boolean getParentalGuidance() { return this.parentalGuidance; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2634 */   public void setParentalGuidance(boolean pg) { this.parentalGuidance = pg; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2643 */   public Vector getImpactDates() { return this.impactDates; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2652 */   public void setManufacturingPlants(Vector manufacturingPlants) { this.manufacturingPlants = manufacturingPlants; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2661 */   public Vector getManufacturingPlants() { return this.manufacturingPlants; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2670 */   public void setImpactDates(Vector impactDates) { this.impactDates = impactDates; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2680 */   public Calendar getLastSchedUpdateDate() { return this.lastSchedUpdateDate; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2690 */   public void setLastSchedUpdateDate(Calendar lastSchedUpdateDate) { this.lastSchedUpdateDate = lastSchedUpdateDate; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2697 */   public User getLastSchedUpdatingUser() { return this.lastSchedUpdatingUser; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2707 */   public void setLastSchedUpdatingUser(User lastSchedUpdatingUser) { this.lastSchedUpdatingUser = lastSchedUpdatingUser; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String toString() {
/* 2716 */     return " \n--Selection--\ngetSelectionID " + 
/*      */       
/* 2718 */       getSelectionID() + "\n" + 
/* 2719 */       "getProjectID " + getProjectID() + "\n" + 
/* 2720 */       "getTitleID " + getTitleID() + "\n" + 
/* 2721 */       "getArtistFirstName " + getArtistFirstName() + "\n" + 
/* 2722 */       "getArtistLastName " + getArtistLastName() + "\n" + 
/* 2723 */       "getSelectionID " + getSelectionID() + "\n" + 
/*      */ 
/*      */       
/* 2726 */       "getReleaseType " + getReleaseType() + "\n" + 
/* 2727 */       "getSelectionConfig " + getSelectionConfig() + "\n" + 
/* 2728 */       "getSelectionSubConfig " + getSelectionSubConfig() + "\n" + 
/* 2729 */       "getUpc " + getUpc() + "\n" + 
/* 2730 */       "getGenre " + getGenre() + "\n" + 
/* 2731 */       "getFamilyId " + getFamilyId() + "\n" + 
/* 2732 */       "getEnvironmentId " + getEnvironmentId() + "\n" + 
/* 2733 */       "getCompanyId " + getCompanyId() + "\n" + 
/* 2734 */       "getDivisionId " + getDivisionId() + "\n" + 
/* 2735 */       "getLabelId " + getLabelId() + "\n" + 
/* 2736 */       "getStreetDate " + getStreetDate() + "\n" + 
/* 2737 */       "getInternationalDate " + getInternationalDate() + "\n" + 
/*      */ 
/*      */       
/* 2740 */       "getLabelContactId " + getLabelContactId() + "\n" + 
/* 2741 */       "getSelectionStatus " + getSelectionStatus() + "\n" + 
/* 2742 */       "getHoldSelection " + getHoldSelection() + "\n" + 
/* 2743 */       "getHoldReason " + getHoldReason() + "\n" + 
/* 2744 */       "getSelectionComments " + getSelectionComments() + "\n" + 
/*      */       
/* 2746 */       "getSpecialPackaging " + getSpecialPackaging() + "\n" + 
/* 2747 */       "getPrice " + getPrice() + "\n" + 
/*      */       
/* 2749 */       "getNumberOfUnits " + getNumberOfUnits() + "\n" + 
/* 2750 */       "gePressAndDistribution " + getPressAndDistribution() + "\n" + 
/* 2751 */       "getPrefixId " + getPrefixID() + "\n" + 
/*      */       
/* 2753 */       "getSelectionPackaging " + getSelectionPackaging() + "\n" + 
/*      */       
/* 2755 */       "getImpactDate " + getImpactDate() + "\n" + 
/*      */ 
/*      */ 
/*      */       
/* 2759 */       "getLastUpdateDate " + getLastUpdateDate() + "\n" + 
/* 2760 */       "getLastUpdateCheck " + getLastUpdatedCheck() + "\n" + 
/*      */       
/* 2762 */       "getDigitalRlsDate " + getDigitalRlsDate() + "\n" + 
/* 2763 */       "getOperCompany " + getOperCompany() + "\n" + 
/* 2764 */       "getSuperLabel " + getSuperLabel() + "\n" + 
/* 2765 */       "getConfigCode " + getConfigCode() + "\n" + 
/* 2766 */       "getSoundScanGrp " + getSoundScanGrp() + "\n" + 
/* 2767 */       "internationalFlag " + getInternationalFlag() + "\n" + 
/*      */       
/* 2769 */       "getASide " + getASide() + "\n" + 
/* 2770 */       "getBSide " + getBSide() + "\n" + 
/* 2771 */       "--End Selection--" + "\n";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2780 */   public Object clone() throws CloneNotSupportedException { return super.clone(); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2789 */   public String getNotepadContentObjectId() { return Integer.toString(this.selectionID); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2794 */   public void setSortBy(int s) { this.sortBy = s; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2804 */   public Vector getMultSelections() { return this.multSelections; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2812 */   public void setMultSelections(Vector multSelections) { this.multSelections = multSelections; }
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
/* 2823 */   public Vector getMultOtherContacts() { return this.multOtherContacts; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2831 */   public void setMultOtherContacts(Vector multOtherContacts) { this.multOtherContacts = multOtherContacts; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected int compareByEnvironment(Object sel) throws ClassCastException {
/* 2842 */     String thisEnvironment = (getEnvironment() != null && getEnvironment().getName() != null) ? 
/* 2843 */       getEnvironment().getName().trim() : "";
/*      */ 
/*      */     
/* 2846 */     String thatEnvironment = (((Selection)sel).getEnvironment() != null && ((Selection)sel).getEnvironment().getName() != null) ? (
/* 2847 */       (Selection)sel).getEnvironment().getName().trim() : "";
/*      */     
/* 2849 */     if (thisEnvironment.equals("") && !thatEnvironment.equals("")) {
/* 2850 */       return 1;
/*      */     }
/* 2852 */     if (!thisEnvironment.equals("") && thatEnvironment.equals("")) {
/* 2853 */       return -1;
/*      */     }
/* 2855 */     return thisEnvironment.compareTo(thatEnvironment);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2863 */   public Environment getEnvironment() { return this.environment; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2871 */   public void setEnvironment(Environment environment) { this.environment = environment; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2880 */   public int getEnvironmentId() { return this.environmentId; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2889 */   public void setEnvironmentId(int environmentId) { this.environmentId = environmentId; }
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
/* 2901 */   public Calendar getDigitalRlsDate() { return this.digital_rls_date; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2910 */   public void setDigitalRlsDate(Calendar digital_rls_date) { this.digital_rls_date = digital_rls_date; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2919 */   public String getDigitalRlsDateString() { return this.digital_rls_date_string; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2928 */   public void setDigitalRlsDateString(String digital_rls_date_string) { this.digital_rls_date_string = digital_rls_date_string; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2938 */   public String getOperCompany() { return this.oper_company; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2947 */   public void setOperCompany(String oper_company) { this.oper_company = oper_company; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2957 */   public String getSuperLabel() { return this.super_label; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2966 */   public void setSuperLabel(String super_label) { this.super_label = super_label; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2976 */   public String getSubLabel() { return this.sub_label; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2985 */   public void setSubLabel(String sub_label) { this.sub_label = sub_label; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2995 */   public String getConfigCode() { return this.config_code; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 3004 */   public void setConfigCode(String config_code) { this.config_code = config_code; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 3014 */   public String getSoundScanGrp() { return this.soundscan_grp; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 3023 */   public void setSoundScanGrp(String soundscan_grp) { this.soundscan_grp = soundscan_grp; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 3033 */   public boolean getInternationalFlag() { return this.international_flag; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 3042 */   public void setInternationalFlag(boolean international_flag) { this.international_flag = international_flag; }
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
/* 3054 */   public String getImprint() { return this.imprint; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 3063 */   public void setImprint(String imprint) { this.imprint = imprint; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 3072 */   public boolean getIsDigital() { return this.isDigital; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 3081 */   public void setIsDigital(boolean isDigital) { this.isDigital = isDigital; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 3090 */   public boolean getNewBundleFlag() { return this.new_bundle_flag; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 3099 */   public void setNewBundleFlag(boolean new_bundle_flag) { this.new_bundle_flag = new_bundle_flag; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 3108 */   public String getGridNumber() { return this.grid_number; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 3117 */   public void setGridNumber(String grid_number) { this.grid_number = grid_number; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 3126 */   public String getSpecialInstructions() { return this.special_instructions; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 3135 */   public void setSpecialInstructions(String special_instructions) { this.special_instructions = special_instructions; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 3143 */   public int getArchimedesId() { return this.archimedesId; }
/*      */ 
/*      */ 
/*      */   
/* 3147 */   public void setArchimedesId(int archimedesId) { this.archimedesId = archimedesId; }
/*      */ 
/*      */ 
/*      */   
/* 3151 */   public int getReleaseFamilyId() { return this.releaseFamilyId; }
/*      */ 
/*      */   
/*      */   public void setReleaseFamilyId(int releaseFamilyId) {
/* 3155 */     this.releaseFamilyId = releaseFamilyId;
/* 3156 */     setCalendarGroup();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 3165 */   public boolean getPriority() { return this.priority_flag; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 3174 */   public void setPriority(boolean priority_flag) { this.priority_flag = priority_flag; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 3183 */   public int getCalendarGroup() { return this.calendarGroup; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void setCalendarGroup() {
/* 3193 */     String releaseFamilyName = MilestoneHelper.getStructureName(getReleaseFamilyId());
/* 3194 */     if (releaseFamilyName.equals("Canada")) {
/*      */       
/* 3196 */       this.calendarGroup = 2;
/*      */     }
/*      */     else {
/*      */       
/* 3200 */       this.calendarGroup = 1;
/*      */     } 
/*      */   }
/*      */ }


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\Selection.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */