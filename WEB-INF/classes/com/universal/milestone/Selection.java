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
/*      */   
/*   86 */   protected String productionCodeGroup = "";
/*   87 */   protected String titleID = "";
/*   88 */   protected String taskname = "";
/*   89 */   protected String department = "";
/*   90 */   protected String sellCode = "";
/*   91 */   protected String sellCodeDPC = "";
/*   92 */   protected String selectionNo = "";
/*   93 */   protected String projectID = "";
/*   94 */   protected String streetDateString = "";
/*   95 */   protected String internationalDateString = "";
/*   96 */   protected String impactDateString = "";
/*   97 */   protected String lastUpdateDateString = "";
/*   98 */   protected String lastMfgUpdateDateString = "";
/*   99 */   protected String holdReason = "";
/*  100 */   protected String upc = "";
/*  101 */   protected String artistFirstName = "";
/*  102 */   protected String artistLastName = "";
/*  103 */   protected String artist = "";
/*  104 */   protected String flArtist = "";
/*  105 */   protected String title = "";
/*  106 */   protected String aSide = "";
/*  107 */   protected String bSide = "";
/*  108 */   protected String selectionPackaging = "";
/*      */   
/*  110 */   protected String auditUPC = "";
/*  111 */   protected String auditSelectionNo = "";
/*  112 */   protected SelectionStatus auditSelectionStatus = null;
/*      */   
/*  114 */   protected String selectionTerritory = "";
/*  115 */   protected String otherContact = "";
/*  116 */   protected String manufacturingComments = "";
/*  117 */   protected String selectionComments = "";
/*  118 */   protected String retailCode = "";
/*      */   
/*  120 */   protected Calendar streetDate = null;
/*  121 */   protected Calendar auditStreetDate = null;
/*  122 */   protected Calendar auditDate = null;
/*  123 */   protected Calendar completionDate = null;
/*  124 */   protected Calendar dueDate = null;
/*  125 */   protected Calendar internationalDate = null;
/*  126 */   protected Calendar impactDate = null;
/*  127 */   protected Calendar lastUpdateDate = null;
/*  128 */   protected Calendar lastMfgUpdateDate = null;
/*  129 */   protected Calendar lastStreetUpdateDate = null;
/*  130 */   protected Calendar originDate = null;
/*  131 */   protected Calendar archieDate = null;
/*  132 */   protected Calendar lastLegacyUpdateDate = null;
/*      */   
/*  134 */   protected Calendar lastSchedUpdateDate = null;
/*  135 */   protected User lastSchedUpdatingUser = null;
/*      */   
/*  137 */   protected long lastUpdatedCheck = -1L;
/*  138 */   protected long lastMfgUpdatedCheck = -1L;
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
/*  157 */   protected int familyId = -1;
/*  158 */   protected int environmentId = -1;
/*  159 */   protected int companyId = -1;
/*  160 */   protected int divisionId = -1;
/*  161 */   protected int labelId = -1;
/*  162 */   protected int labelContactId = -1;
/*  163 */   protected int scheduleId = -1;
/*      */   
/*  165 */   protected int calendarGroup = -1;
/*      */ 
/*      */   
/*      */   protected ImpactDate impactDateObject;
/*      */   
/*      */   protected boolean fullSelection = false;
/*      */   
/*  172 */   protected int sortBy = -1;
/*      */ 
/*      */   
/*  175 */   protected int templateId = -1;
/*      */ 
/*      */   
/*      */   protected boolean parentalGuidance;
/*      */ 
/*      */   
/*  181 */   protected Vector impactDates = null;
/*      */ 
/*      */   
/*  184 */   protected Vector manufacturingPlants = null;
/*      */ 
/*      */   
/*  187 */   protected Vector multSelections = null;
/*      */ 
/*      */   
/*  190 */   protected Vector multOtherContacts = null;
/*      */ 
/*      */ 
/*      */   
/*  194 */   protected Calendar digital_rls_date = null;
/*  195 */   protected String digital_rls_date_string = "";
/*  196 */   protected String oper_company = "";
/*  197 */   protected String super_label = "";
/*  198 */   protected String sub_label = "";
/*  199 */   protected String config_code = "";
/*  200 */   protected String soundscan_grp = "";
/*      */ 
/*      */   
/*      */   protected boolean international_flag;
/*      */   
/*      */   protected boolean isDigital = false;
/*      */   
/*  207 */   protected String imprint = "";
/*      */   protected boolean new_bundle_flag = false;
/*  209 */   protected String grid_number = "";
/*  210 */   protected String special_instructions = "";
/*      */ 
/*      */   
/*      */   protected boolean priority_flag = false;
/*      */   
/*  215 */   protected int archimedesId = -1;
/*  216 */   protected int releaseFamilyId = -1;
/*      */ 
/*      */   
/*  219 */   protected Calendar autoCloseDate = null;
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
/*  231 */   public int getIdentity() { return getSelectionID(); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  240 */   public String getTableName() { return "Release_Header"; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int compareTo(Object sel) throws ClassCastException {
/*  250 */     switch (this.sortBy) {
/*      */       
/*      */       case 1:
/*  253 */         return compareByStreetDate(sel);
/*      */       
/*      */       case 2:
/*  256 */         return compareByProductCategory(sel);
/*      */       
/*      */       case 3:
/*  259 */         return compareByArtist(sel);
/*      */       
/*      */       case 4:
/*  262 */         return compareByTitle(sel);
/*      */       
/*      */       case 5:
/*  265 */         return compareByUpc(sel);
/*      */       
/*      */       case 7:
/*  268 */         return compareByDivision(sel);
/*      */       
/*      */       case 9:
/*  271 */         return compareByLabel(sel);
/*      */       
/*      */       case 14:
/*  274 */         return compareByPrefixSelectionId(sel);
/*      */       
/*      */       case 12:
/*  277 */         return compareBySelectionNo(sel);
/*      */       
/*      */       case 6:
/*  280 */         return compareByFamily(sel);
/*      */       
/*      */       case 23:
/*  283 */         return compareByEnvironment(sel);
/*      */       
/*      */       case 8:
/*  286 */         return compareByCompany(sel);
/*      */       
/*      */       case 13:
/*  289 */         return compareBySubConfig(sel);
/*      */       
/*      */       case 10:
/*  292 */         return compareByStatus(sel);
/*      */       
/*      */       case 16:
/*  295 */         return compareBySpecialStatus(sel);
/*      */       
/*      */       case 17:
/*  298 */         return compareByStreetDateBlankAtEnd(sel);
/*      */       
/*      */       case 21:
/*  301 */         return compareByPackagingSpecs(sel);
/*      */       
/*      */       case 22:
/*  304 */         return compareByFlArtist(sel);
/*      */       
/*      */       case 19:
/*  307 */         return compareByImpactDate(sel);
/*      */       
/*      */       case 24:
/*  310 */         return compareByImprint(sel);
/*      */       
/*      */       case 25:
/*  313 */         return compareByPriority(sel);
/*      */       
/*      */       case 26:
/*  316 */         return compareByDigitalStreetDate(sel);
/*      */       
/*      */       case 27:
/*  319 */         return compareByAuditDate(sel);
/*      */       
/*      */       case 20:
/*  322 */         return compareByReverseStreetDate(sel);
/*      */     } 
/*      */     
/*  325 */     return compareByStreetDate(sel);
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
/*  337 */     Calendar thisDate = this.auditDate;
/*  338 */     Calendar thatDate = ((Selection)sel).auditDate;
/*      */     
/*  340 */     if (thisDate == null && thatDate == null) {
/*  341 */       return 0;
/*      */     }
/*  343 */     if (thisDate == null) {
/*  344 */       return 1;
/*      */     }
/*  346 */     if (thatDate == null) {
/*  347 */       return -1;
/*      */     }
/*  349 */     if (thisDate.before(thatDate)) {
/*  350 */       return -1;
/*      */     }
/*  352 */     if (thisDate.after(thatDate)) {
/*  353 */       return 1;
/*      */     }
/*      */     
/*  356 */     return 0;
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
/*  367 */     Calendar thisStreetDate = this.digital_rls_date;
/*  368 */     Calendar thatStreetDate = ((Selection)sel).digital_rls_date;
/*      */     
/*  370 */     if (thisStreetDate == null && thatStreetDate == null) {
/*  371 */       return 0;
/*      */     }
/*  373 */     if (thisStreetDate == null) {
/*  374 */       return 1;
/*      */     }
/*  376 */     if (thatStreetDate == null) {
/*  377 */       return -1;
/*      */     }
/*  379 */     if (thisStreetDate.before(thatStreetDate)) {
/*  380 */       return -1;
/*      */     }
/*  382 */     if (thisStreetDate.after(thatStreetDate)) {
/*  383 */       return 1;
/*      */     }
/*      */     
/*  386 */     return 0;
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
/*  398 */     boolean thisPriority = getPriority();
/*      */     
/*  400 */     boolean thatPriority = ((Selection)sel).getPriority();
/*      */ 
/*      */     
/*  403 */     if (thisPriority && !thatPriority)
/*  404 */       return -1; 
/*  405 */     if (!thisPriority && thatPriority) {
/*  406 */       return 1;
/*      */     }
/*  408 */     return 0;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected int compareByImprint(Object sel) throws ClassCastException {
/*  418 */     String thisImprint = (getImprint() != null) ? getImprint().trim() : "";
/*  419 */     String thatImprint = (((Selection)sel).getImprint() != null) ? ((Selection)sel).getImprint().trim() : "";
/*      */     
/*  421 */     if (thisImprint.equals("") && !thatImprint.equals("")) {
/*  422 */       return 1;
/*      */     }
/*  424 */     if (!thisImprint.equals("") && thatImprint.equals("")) {
/*  425 */       return -1;
/*      */     }
/*      */     
/*  428 */     return thisImprint.compareToIgnoreCase(thatImprint);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected int compareBySubConfig(Object sel) throws ClassCastException {
/*  438 */     String thisSubConfig = (getSelectionConfig() != null) ? getSelectionConfig().getSelectionConfigurationName() : "";
/*      */ 
/*      */     
/*  441 */     String thatSubConfig = (((Selection)sel).getSelectionConfig() != null) ? ((Selection)sel).getSelectionConfig().getSelectionConfigurationName() : "";
/*      */     
/*  443 */     return thisSubConfig.compareTo(thatSubConfig);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected int compareByStreetDate(Object sel) throws ClassCastException {
/*  452 */     Calendar thisStreetDate = this.streetDate;
/*  453 */     Calendar thatStreetDate = ((Selection)sel).streetDate;
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
/*  520 */     if (thisStreetDate == null && thatStreetDate == null) {
/*  521 */       return 0;
/*      */     }
/*  523 */     if (thisStreetDate == null) {
/*  524 */       return 1;
/*      */     }
/*  526 */     if (thatStreetDate == null) {
/*  527 */       return -1;
/*      */     }
/*  529 */     if (thisStreetDate.before(thatStreetDate)) {
/*  530 */       return -1;
/*      */     }
/*  532 */     if (thisStreetDate.after(thatStreetDate)) {
/*  533 */       return 1;
/*      */     }
/*      */     
/*  536 */     return 0;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected int compareByReverseStreetDate(Object sel) throws ClassCastException {
/*  545 */     Calendar thisStreetDate = this.streetDate;
/*  546 */     Calendar thatStreetDate = ((Selection)sel).streetDate;
/*      */     
/*  548 */     if (thisStreetDate == null && thatStreetDate == null) {
/*  549 */       return 0;
/*      */     }
/*  551 */     if (thisStreetDate == null) {
/*  552 */       return -1;
/*      */     }
/*  554 */     if (thatStreetDate == null) {
/*  555 */       return 1;
/*      */     }
/*  557 */     if (thisStreetDate.before(thatStreetDate)) {
/*  558 */       return 1;
/*      */     }
/*  560 */     if (thisStreetDate.after(thatStreetDate)) {
/*  561 */       return -1;
/*      */     }
/*      */     
/*  564 */     return 0;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected int compareByStreetDateBlankAtEnd(Object sel) throws ClassCastException {
/*  574 */     Calendar thisStreetDate = this.streetDate;
/*  575 */     Calendar thatStreetDate = ((Selection)sel).streetDate;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  580 */     if (thisStreetDate == null) {
/*  581 */       return 1;
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
/*  593 */     return 0;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected int compareByProductCategory(Object sel) throws ClassCastException {
/*  603 */     LookupObject prodCategory = SelectionManager.getLookupObject(getProductCategory().getAbbreviation(), 
/*  604 */         Cache.getProductCategories());
/*  605 */     String thisProdCategoryName = (prodCategory != null && prodCategory.getName() != null) ? 
/*  606 */       prodCategory.getName() : "";
/*      */ 
/*      */     
/*  609 */     prodCategory = SelectionManager.getLookupObject(((Selection)sel).getProductCategory().getAbbreviation(), 
/*  610 */         Cache.getProductCategories());
/*  611 */     String thatProdCategoryName = (prodCategory != null && prodCategory.getName() != null) ? 
/*  612 */       prodCategory.getName() : "";
/*      */     
/*  614 */     if (thisProdCategoryName.equals("") && !thatProdCategoryName.equals("")) {
/*  615 */       return 1;
/*      */     }
/*  617 */     if (!thisProdCategoryName.equals("") && thatProdCategoryName.equals("")) {
/*  618 */       return -1;
/*      */     }
/*  620 */     return thisProdCategoryName.compareTo(thatProdCategoryName);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected int compareByArtist(Object sel) throws ClassCastException {
/*  629 */     String thisArtist = (getArtist() != null) ? getArtist().trim() : "";
/*  630 */     String thatArtist = (((Selection)sel).getArtist() != null) ? ((Selection)sel).getArtist().trim() : "";
/*      */     
/*  632 */     if (thisArtist.equals("") && !thatArtist.equals("")) {
/*  633 */       return 1;
/*      */     }
/*  635 */     if (!thisArtist.equals("") && thatArtist.equals("")) {
/*  636 */       return -1;
/*      */     }
/*      */     
/*  639 */     return thisArtist.compareToIgnoreCase(thatArtist);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected int compareByFlArtist(Object sel) throws ClassCastException {
/*  648 */     String thisFlArtist = (getFlArtist() != null) ? getFlArtist().trim() : "";
/*  649 */     String thatFlArtist = (((Selection)sel).getFlArtist() != null) ? ((Selection)sel).getFlArtist().trim() : "";
/*      */     
/*  651 */     if (thisFlArtist.equals("") && !thatFlArtist.equals("")) {
/*  652 */       return 1;
/*      */     }
/*  654 */     if (!thisFlArtist.equals("") && thatFlArtist.equals("")) {
/*  655 */       return -1;
/*      */     }
/*      */     
/*  658 */     return thisFlArtist.compareToIgnoreCase(thatFlArtist);
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
/*  669 */     String thisTitle = (getTitle() != null) ? getTitle().trim() : "";
/*  670 */     String thatTitle = (((Selection)sel).getTitle() != null) ? ((Selection)sel).getTitle().trim() : "";
/*      */     
/*  672 */     if (thisTitle.equals("") && !thatTitle.equals("")) {
/*  673 */       return 1;
/*      */     }
/*  675 */     if (!thisTitle.equals("") && thatTitle.equals("")) {
/*  676 */       return -1;
/*      */     }
/*      */     
/*  679 */     return thisTitle.compareToIgnoreCase(thatTitle);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected int compareByPackagingSpecs(Object sel) throws ClassCastException {
/*  689 */     String thisPackagingSpec = (getSelectionPackaging() != null) ? getSelectionPackaging().trim() : "";
/*  690 */     String thatPackagingSpec = (((Selection)sel).getSelectionPackaging() != null) ? ((Selection)sel).getSelectionPackaging().trim() : "";
/*      */     
/*  692 */     if (thisPackagingSpec.equals("") && !thatPackagingSpec.equals("")) {
/*  693 */       return 1;
/*      */     }
/*  695 */     if (!thisPackagingSpec.equals("") && thatPackagingSpec.equals("")) {
/*  696 */       return -1;
/*      */     }
/*  698 */     return thisPackagingSpec.compareTo(thatPackagingSpec);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected int compareByUpc(Object sel) throws ClassCastException {
/*  707 */     String thisUpc = (getUpc() != null) ? getUpc() : "";
/*  708 */     String thatUpc = (((Selection)sel).getUpc() != null) ? ((Selection)sel).getUpc() : "";
/*      */     
/*  710 */     if (thisUpc.equals("") && !thatUpc.equals("")) {
/*  711 */       return 1;
/*      */     }
/*  713 */     if (!thisUpc.equals("") && thatUpc.equals("")) {
/*  714 */       return -1;
/*      */     }
/*  716 */     return thisUpc.compareTo(thatUpc);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected int compareByDivision(Object sel) throws ClassCastException {
/*  725 */     String thisDivision = (getDivision() != null && getDivision().getName() != null) ? 
/*  726 */       getDivision().getName().trim() : "";
/*      */     
/*  728 */     String thatDivision = (((Selection)sel).getDivision() != null && ((Selection)sel).getDivision().getName() != null) ? (
/*  729 */       (Selection)sel).getDivision().getName().trim() : "";
/*      */     
/*  731 */     if (thisDivision.equals("") && !thatDivision.equals("")) {
/*  732 */       return 1;
/*      */     }
/*  734 */     if (!thisDivision.equals("") && thatDivision.equals("")) {
/*  735 */       return -1;
/*      */     }
/*  737 */     return thisDivision.compareTo(thatDivision);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected int compareByLabel(Object sel) throws ClassCastException {
/*  746 */     String thisLabel = (getLabel() != null && getLabel().getName() != null) ? 
/*  747 */       getLabel().getName().trim() : "";
/*      */     
/*  749 */     String thatLabel = (((Selection)sel).getLabel() != null && ((Selection)sel).getLabel().getName() != null) ? (
/*  750 */       (Selection)sel).getLabel().getName().trim() : "";
/*      */     
/*  752 */     if (thisLabel.equals("") && !thatLabel.equals("")) {
/*  753 */       return 1;
/*      */     }
/*  755 */     if (!thisLabel.equals("") && thatLabel.equals("")) {
/*  756 */       return -1;
/*      */     }
/*  758 */     return thisLabel.compareTo(thatLabel);
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
/*  769 */     String thisSelectionNo = SelectionManager.getLookupObjectValue(getPrefixID());
/*  770 */     if (thisSelectionNo == null)
/*  771 */       thisSelectionNo = ""; 
/*  772 */     thisSelectionNo = String.valueOf(thisSelectionNo) + getSelectionNo();
/*      */ 
/*      */     
/*  775 */     String thatSelectionNo = SelectionManager.getLookupObjectValue(((Selection)sel).getPrefixID());
/*  776 */     if (thatSelectionNo == null)
/*  777 */       thatSelectionNo = ""; 
/*  778 */     thatSelectionNo = String.valueOf(thatSelectionNo) + ((Selection)sel).getSelectionNo();
/*      */     
/*  780 */     if (thisSelectionNo.equals("") && !thatSelectionNo.equals("")) {
/*  781 */       return 1;
/*      */     }
/*  783 */     if (!thisSelectionNo.equals("") && thatSelectionNo.equals("")) {
/*  784 */       return -1;
/*      */     }
/*  786 */     return thisSelectionNo.compareTo(thatSelectionNo);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected int compareBySelectionNo(Object sel) throws ClassCastException {
/*  796 */     String thisSelectionNo = (getSelectionNo() != null) ? getSelectionNo() : "";
/*      */ 
/*      */     
/*  799 */     String thatSelectionNo = (((Selection)sel).getSelectionNo() != null) ? ((Selection)sel).getSelectionNo() : "";
/*      */     
/*  801 */     if (thisSelectionNo.equals("") && !thatSelectionNo.equals("")) {
/*  802 */       return 1;
/*      */     }
/*  804 */     if (!thisSelectionNo.equals("") && thatSelectionNo.equals("")) {
/*  805 */       return -1;
/*      */     }
/*  807 */     return thisSelectionNo.compareTo(thatSelectionNo);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected int compareByFamily(Object sel) throws ClassCastException {
/*  817 */     String thisFamily = (getFamily() != null && getFamily().getName() != null) ? 
/*  818 */       getFamily().getName().trim() : "";
/*      */ 
/*      */     
/*  821 */     String thatFamily = (((Selection)sel).getFamily() != null && ((Selection)sel).getFamily().getName() != null) ? (
/*  822 */       (Selection)sel).getFamily().getName().trim() : "";
/*      */     
/*  824 */     if (thisFamily.equals("") && !thatFamily.equals("")) {
/*  825 */       return 1;
/*      */     }
/*  827 */     if (!thisFamily.equals("") && thatFamily.equals("")) {
/*  828 */       return -1;
/*      */     }
/*  830 */     return thisFamily.compareTo(thatFamily);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected int compareByStatus(Object sel) throws ClassCastException {
/*  840 */     String thisStatus = (getSelectionStatus() != null && getSelectionStatus().getName() != null) ? 
/*  841 */       getSelectionStatus().getName().trim() : "";
/*      */ 
/*      */     
/*  844 */     String thatStatus = (((Selection)sel).getSelectionStatus() != null && ((Selection)sel).getSelectionStatus().getName() != null) ? (
/*  845 */       (Selection)sel).getSelectionStatus().getName().trim() : "";
/*      */     
/*  847 */     if (thisStatus.equals("") && !thatStatus.equals("")) {
/*  848 */       return 1;
/*      */     }
/*  850 */     if (!thisStatus.equals("") && thatStatus.equals("")) {
/*  851 */       return -1;
/*      */     }
/*  853 */     return thisStatus.compareTo(thatStatus);
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
/*  864 */     String thisStatus = (getSelectionStatus() != null && getSelectionStatus().getName() != null) ? 
/*  865 */       getSelectionStatus().getName().trim() : "";
/*      */     
/*  867 */     if (!thisStatus.equalsIgnoreCase("TBS") && !thisStatus.equalsIgnoreCase("In The Works"))
/*      */     {
/*  869 */       thisStatus = "A";
/*      */     }
/*      */ 
/*      */     
/*  873 */     String thatStatus = (((Selection)sel).getSelectionStatus() != null && ((Selection)sel).getSelectionStatus().getName() != null) ? (
/*  874 */       (Selection)sel).getSelectionStatus().getName().trim() : "";
/*      */     
/*  876 */     if (!thatStatus.equalsIgnoreCase("TBS") && !thatStatus.equalsIgnoreCase("In The Works"))
/*      */     {
/*  878 */       thatStatus = "A";
/*      */     }
/*      */     
/*  881 */     if (thisStatus.equals("") && !thatStatus.equals("")) {
/*  882 */       return 1;
/*      */     }
/*  884 */     if (!thisStatus.equals("") && thatStatus.equals("")) {
/*  885 */       return -1;
/*      */     }
/*  887 */     return thisStatus.compareTo(thatStatus);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected int compareByCompany(Object sel) throws ClassCastException {
/*  897 */     String thisCompany = (getCompany() != null && getCompany().getName() != null) ? 
/*  898 */       getCompany().getName().trim() : "";
/*      */ 
/*      */     
/*  901 */     String thatCompany = (((Selection)sel).getCompany() != null && ((Selection)sel).getCompany().getName() != null) ? (
/*  902 */       (Selection)sel).getCompany().getName().trim() : "";
/*      */     
/*  904 */     if (thisCompany.equals("") && !thatCompany.equals("")) {
/*  905 */       return 1;
/*      */     }
/*  907 */     if (!thisCompany.equals("") && thatCompany.equals("")) {
/*  908 */       return -1;
/*      */     }
/*  910 */     return thisCompany.compareTo(thatCompany);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected int compareByImpactDate(Object sel) throws ClassCastException {
/*  919 */     Calendar thisImpactDate = this.impactDate;
/*  920 */     Calendar thatImpactDate = ((Selection)sel).impactDate;
/*      */     
/*  922 */     if (thisImpactDate == null && thatImpactDate == null) {
/*  923 */       return 0;
/*      */     }
/*  925 */     if (thisImpactDate == null) {
/*  926 */       return 1;
/*      */     }
/*  928 */     if (thatImpactDate == null) {
/*  929 */       return -1;
/*      */     }
/*  931 */     if (thisImpactDate.before(thatImpactDate)) {
/*  932 */       return -1;
/*      */     }
/*  934 */     if (thisImpactDate.after(thatImpactDate)) {
/*  935 */       return 1;
/*      */     }
/*      */     
/*  938 */     return 0;
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
/* 1003 */   public int getSelectionID() { return this.selectionID; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1013 */   public void setSelectionID(int id) { this.selectionID = id; }
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
/* 1024 */   public String getSelectionNo() { return this.selectionNo; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setSelectionNo(String no) {
/* 1034 */     auditCheck("selection_no", this.selectionNo, no);
/* 1035 */     this.selectionNo = no;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1045 */   public Calendar getStreetDate() { return this.streetDate; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1055 */   public void setStreetDate(Calendar date) { this.streetDate = date; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1065 */   public Calendar getAuditStreetDate() { return this.auditStreetDate; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1075 */   public void setAuditStreetDate(Calendar date) { this.auditStreetDate = date; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1085 */   public Calendar getAuditDate() { return this.auditDate; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1095 */   public void setAuditDate(Calendar date) { this.auditDate = date; }
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
/* 1106 */   public String getAuditSelectionNo() { return this.auditSelectionNo; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1116 */   public void setAuditSelectionNo(String no) { this.auditSelectionNo = no; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1125 */   public PrefixCode getAuditPrefixID() { return this.auditPrefixID; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1135 */   public void setAuditPrefixID(PrefixCode prefixCode) { this.auditPrefixID = prefixCode; }
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
/* 1146 */   public String getAuditUPC() { return this.auditUPC; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1156 */   public void setAuditUPC(String no) { this.auditUPC = no; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1165 */   public SelectionStatus getAuditSelectionStatus() { return this.auditSelectionStatus; }
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
/* 1178 */   public void setAuditSelectionStatus(SelectionStatus status) { this.auditSelectionStatus = status; }
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
/* 1191 */   public Calendar getInternationalDate() { return this.internationalDate; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1201 */   public void setInternationalDate(Calendar date) { this.internationalDate = date; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1209 */   public Calendar getImpactDate() { return this.impactDate; }
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
/* 1220 */   public void setImpactDate(Calendar date) { this.impactDate = date; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1228 */   public ImpactDate getImpactDateObject() { return this.impactDateObject; }
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
/* 1239 */   public void setImpactDateObject(ImpactDate date) { this.impactDateObject = date; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1247 */   public SelectionStatus getSelectionStatus() { return this.selectionStatus; }
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
/* 1259 */   public void setSelectionStatus(SelectionStatus status) { this.selectionStatus = status; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1269 */   public boolean getHoldSelection() { return this.holdSelection; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1279 */   public void setHoldSelection(boolean isOnHold) { this.holdSelection = isOnHold; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1289 */   public boolean getNoDigitalRelease() { return this.noDigitalRelease; }
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
/* 1300 */   public void setNoDigitalRelease(boolean noDigRel) { this.noDigitalRelease = noDigRel; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1309 */   public String getHoldReason() { return this.holdReason; }
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
/* 1320 */   public void setHoldReason(String reason) { this.holdReason = reason; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1330 */   public boolean getPressAndDistribution() { return this.pressAndDistribution; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1340 */   public void setPressAndDistribution(boolean isPressAndDistribution) { this.pressAndDistribution = isPressAndDistribution; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1349 */   public String getProjectID() { return this.projectID; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1359 */   public void setProjectID(String idNumber) { this.projectID = idNumber; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1367 */   public boolean getSpecialPackaging() { return this.specialPackaging; }
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
/* 1378 */   public void setSpecialPackaging(boolean hasSpecialPackaging) { this.specialPackaging = hasSpecialPackaging; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1387 */   public PrefixCode getPrefixID() { return this.prefixID; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1397 */   public void setPrefixID(PrefixCode prefixCode) { this.prefixID = prefixCode; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1405 */   public String getTitleID() { return this.titleID; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1415 */   public void setTitleID(String titleIdNumber) { this.titleID = titleIdNumber; }
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
/* 1427 */   public String getProductionGroupCode() { return this.productionCodeGroup; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1437 */   public void setProductionGroupCode(String productionCodeGroup) { this.productionCodeGroup = productionCodeGroup; }
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
/* 1449 */   public String getUpc() { return this.upc; }
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
/* 1460 */     auditCheck("upc", this.upc, upcCode);
/* 1461 */     this.upc = upcCode;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1469 */   public String getArtistFirstName() { return this.artistFirstName; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1479 */   public void setArtistFirstName(String firstName) { this.artistFirstName = firstName; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1487 */   public String getArtistLastName() { return this.artistLastName; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1497 */   public void setArtistLastName(String lastName) { this.artistLastName = lastName; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1505 */   public void setArtist(String artistName) { this.artist = artistName; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1513 */   public String getArtist() { return this.artist; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1521 */   public void setFlArtist(String artistName) { this.flArtist = artistName; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1529 */   public String getFlArtist() { return this.flArtist; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1537 */   public String getTitle() { return this.title; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1547 */   public void setTitle(String theTitle) { this.title = theTitle; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1555 */   public String getASide() { return this.aSide; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1565 */   public void setASide(String aSideText) { this.aSide = aSideText; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1573 */   public String getBSide() { return this.bSide; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1583 */   public void setBSide(String bSideText) { this.bSide = bSideText; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1591 */   public ProductCategory getProductCategory() { return this.productCategory; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1601 */   public void setProductCategory(ProductCategory productCategory) { this.productCategory = productCategory; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1609 */   public ReleaseType getReleaseType() { return this.releaseType; }
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
/* 1620 */   public void setReleaseType(ReleaseType releaseType) { this.releaseType = releaseType; }
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
/* 1631 */   public SelectionConfiguration getSelectionConfig() { return this.selectionConfig; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1640 */   public void setSelectionConfig(SelectionConfiguration selectionConfig) { this.selectionConfig = selectionConfig; }
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
/* 1651 */   public SelectionSubConfiguration getSelectionSubConfig() { return this.selectionSubConfig; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1660 */   public void setSelectionSubConfig(SelectionSubConfiguration selectionSubConfig) { this.selectionSubConfig = selectionSubConfig; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1668 */   public Family getFamily() { return this.family; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1676 */   public void setFamily(Family family) { this.family = family; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1684 */   public Company getCompany() { return this.company; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1692 */   public void setCompany(Company company) { this.company = company; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1700 */   public Division getDivision() { return this.division; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1710 */   public void setDivision(Division division) { this.division = division; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1718 */   public Label getLabel() { return this.label; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1728 */   public void setLabel(Label label) { this.label = label; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1736 */   public String getSellCode() { return this.sellCode; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1744 */   public void setSellCode(String sellCode) { this.sellCode = sellCode; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1752 */   public String getSellCodeDPC() { return this.sellCodeDPC; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1760 */   public void setSellCodeDPC(String sellCodeDPC) { this.sellCodeDPC = sellCodeDPC; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1769 */   public String getRetailCode() { return this.retailCode; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1779 */   public void setRetailCode(String retailCode) { this.retailCode = retailCode; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1787 */   public PriceCode getPriceCode() { return this.priceCode; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1797 */   public void setPriceCode(PriceCode priceCode) { this.priceCode = priceCode; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1805 */   public PriceCode getPriceCodeDPC() { return this.priceCodeDPC; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1815 */   public void setPriceCodeDPC(PriceCode priceCodeDPC) { this.priceCodeDPC = priceCodeDPC; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1825 */   public float getPrice() { return this.price; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1835 */   public void setPrice(float price) { this.price = price; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1844 */   public int getNumberOfUnits() { return this.numberOfUnits; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1853 */   public void setNumberOfUnits(int numberOfUnits) { this.numberOfUnits = numberOfUnits; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1861 */   public Genre getGenre() { return this.genre; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1869 */   public void setGenre(Genre genre) { this.genre = genre; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1877 */   public String getSelectionPackaging() { return this.selectionPackaging; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1886 */   public void setSelectionPackaging(String selectionPackaging) { this.selectionPackaging = selectionPackaging; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1894 */   public User getLabelContact() { return this.labelContact; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1904 */   public void setLabelContact(User labelContact) { this.labelContact = labelContact; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1913 */   public String getOtherContact() { return this.otherContact; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1921 */   public void setOtherContact(String otherContact) { this.otherContact = otherContact; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1929 */   public Schedule getSchedule() { return this.schedule; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1937 */   public void setBom(Bom bom) { this.bom = bom; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1945 */   public Bom getBom() { return this.bom; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1953 */   public void setSchedule(Schedule schedule) { this.schedule = schedule; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1961 */   public User getLastUpdatingUser() { return this.lastUpdatingUser; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1971 */   public void setLastUpdatingUser(User lastUpdatingUser) { this.lastUpdatingUser = lastUpdatingUser; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1979 */   public User getLastMfgUpdatingUser() { return this.lastMfgUpdatingUser; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1989 */   public void setLastMfgUpdatingUser(User lastMfgUpdatingUser) { this.lastMfgUpdatingUser = lastMfgUpdatingUser; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1998 */   public Calendar getLastUpdateDate() { return this.lastUpdateDate; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2008 */   public void setLastUpdateDate(Calendar lastUpdateDate) { this.lastUpdateDate = lastUpdateDate; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2016 */   public Calendar getArchieDate() { return this.archieDate; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2026 */   public void setArchieDate(Calendar archieDate) { this.archieDate = archieDate; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2034 */   public Calendar getLastLegacyUpdateDate() { return this.lastLegacyUpdateDate; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2044 */   public void setLastLegacyUpdateDate(Calendar lastLegacyUpdateDate) { this.lastLegacyUpdateDate = lastLegacyUpdateDate; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2053 */   public Calendar getAutoCloseDate() { return this.autoCloseDate; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2063 */   public void setAutoCloseDate(Calendar autoCloseDate) { this.autoCloseDate = autoCloseDate; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2072 */   public Calendar getLastMfgUpdateDate() { return this.lastMfgUpdateDate; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2082 */   public void setLastMfgUpdateDate(Calendar lastMfgUpdateDate) { this.lastMfgUpdateDate = lastMfgUpdateDate; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2092 */   public Calendar getLastStreetUpdateDate() { return this.lastStreetUpdateDate; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2102 */   public void setLastStreetUpdateDate(Calendar lastStreetUpdateDate) { this.lastStreetUpdateDate = lastStreetUpdateDate; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2111 */   public String getSelectionTerritory() { return this.selectionTerritory; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2120 */   public void setSelectionTerritory(String selectionTerritory) { this.selectionTerritory = selectionTerritory; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2130 */   public User getUmlContact() { return this.umlContact; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2140 */   public void setUmlContact(User umlContact) { this.umlContact = umlContact; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2148 */   public LookupObject getPlant() { return this.plant; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2158 */   public void setPlant(LookupObject plant) { this.plant = plant; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2166 */   public LookupObject getDistribution() { return this.distribution; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2176 */   public void setDistribution(LookupObject distribution) { this.distribution = distribution; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2184 */   public int getPoQuantity() { return this.poQuantity; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2194 */   public void setPoQuantity(int poQuantity) { this.poQuantity = poQuantity; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2203 */   public int getCompletedQuantity() { return this.completedQuantity; }
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
/* 2214 */   public void setCompletedQuantity(int completedQuantity) { this.completedQuantity = completedQuantity; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2223 */   public String getSelectionComments() { return this.selectionComments; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2233 */   public void setComments(String selectionComments) { this.selectionComments = selectionComments; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2242 */   public String getManufacturingComments() { return this.manufacturingComments; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2252 */   public void setManufacturingComments(String manufacturingComments) { this.manufacturingComments = manufacturingComments; }
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
/* 2264 */   public int getFamilyId() { return this.familyId; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2273 */   public void setFamilyId(int familyId) { this.familyId = familyId; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2282 */   public int getCompanyId() { return this.companyId; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2291 */   public void setCompanyId(int companyId) { this.companyId = companyId; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2300 */   public int getDivisionId() { return this.divisionId; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2309 */   public void setDivisionId(int divisionId) { this.divisionId = divisionId; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2318 */   public int getLabelId() { return this.labelId; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2327 */   public void setLabelId(int labelId) { this.labelId = labelId; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2336 */   public int getLabelContactId() { return this.labelContactId; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2345 */   public void setLabelContactId(int labelContactId) { this.labelContactId = labelContactId; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2354 */   public int getScheduleId() { return this.scheduleId; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2363 */   public void setScheduleId(int scheduleId) { this.scheduleId = scheduleId; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2372 */   public void setTaskName(String name) { this.taskname = name; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2382 */   public String getTaskName() { return this.taskname; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2392 */   public void setDepartment(String p_department) { this.department = p_department; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2402 */   public String getDepartment() { return this.department; }
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
/* 2413 */   public Calendar getCompletionDate() { return this.completionDate; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2423 */   public void setCompletionDate(Calendar date) { this.completionDate = date; }
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
/* 2434 */   public void setDueDate(Calendar date) { this.dueDate = date; }
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
/* 2445 */   public Calendar getDueDate() { return this.dueDate; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2454 */   public String getStreetDateString() { return this.streetDateString; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2463 */   public void setStreetDateString(String streetDateString) { this.streetDateString = streetDateString; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2472 */   public String getInternationalDateString() { return this.internationalDateString; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2481 */   public void setInternationalDateString(String internationalDateString) { this.internationalDateString = internationalDateString; }
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
/* 2497 */   public boolean isFullSelection() { return this.fullSelection; }
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
/* 2512 */   public void setFullSelection(boolean fullSelection) { this.fullSelection = fullSelection; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2522 */   public String getImpactDateString() { return this.impactDateString; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2531 */   public void setImpactDateString(String impactDateString) { this.impactDateString = impactDateString; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2541 */   public String getLastUpdateDateString() { return this.lastUpdateDateString; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2550 */   public void setLastUpdateDateString(String lastUpdateDateString) { this.lastUpdateDateString = lastUpdateDateString; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2559 */   public String getLastMfgUpdateDateString() { return this.lastMfgUpdateDateString; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2568 */   public void setLastMfgUpdateDateString(String lastMfgUpdateDateString) { this.lastMfgUpdateDateString = lastMfgUpdateDateString; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2577 */   public long getLastUpdatedCheck() { return this.lastUpdatedCheck; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2586 */   public void setLastUpdatedCheck(long lastUpdatedCheck) { this.lastUpdatedCheck = lastUpdatedCheck; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2596 */   public Calendar getOriginDate() { return this.originDate; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2605 */   public void setOriginDate(Calendar originDate) { this.originDate = originDate; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2615 */   public long getLastMfgUpdatedCheck() { return this.lastMfgUpdatedCheck; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2624 */   public void setLastMfgUpdatedCheck(long lastMfgUpdatedCheck) { this.lastMfgUpdatedCheck = lastMfgUpdatedCheck; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2633 */   public int getTemplateId() { return this.templateId; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2642 */   public void setTemplateId(int id) { this.templateId = id; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2652 */   public boolean getParentalGuidance() { return this.parentalGuidance; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2661 */   public void setParentalGuidance(boolean pg) { this.parentalGuidance = pg; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2670 */   public Vector getImpactDates() { return this.impactDates; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2679 */   public void setManufacturingPlants(Vector manufacturingPlants) { this.manufacturingPlants = manufacturingPlants; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2688 */   public Vector getManufacturingPlants() { return this.manufacturingPlants; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2697 */   public void setImpactDates(Vector impactDates) { this.impactDates = impactDates; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2707 */   public Calendar getLastSchedUpdateDate() { return this.lastSchedUpdateDate; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2717 */   public void setLastSchedUpdateDate(Calendar lastSchedUpdateDate) { this.lastSchedUpdateDate = lastSchedUpdateDate; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2724 */   public User getLastSchedUpdatingUser() { return this.lastSchedUpdatingUser; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2734 */   public void setLastSchedUpdatingUser(User lastSchedUpdatingUser) { this.lastSchedUpdatingUser = lastSchedUpdatingUser; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String toString() {
/* 2743 */     return " \n--Selection--\ngetSelectionID " + 
/*      */       
/* 2745 */       getSelectionID() + "\n" + 
/* 2746 */       "getProjectID " + getProjectID() + "\n" + 
/* 2747 */       "getTitleID " + getTitleID() + "\n" + 
/* 2748 */       "getProductionGroupCode" + getProductionGroupCode() + "\n" + 
/* 2749 */       "getArtistFirstName " + getArtistFirstName() + "\n" + 
/* 2750 */       "getArtistLastName " + getArtistLastName() + "\n" + 
/* 2751 */       "getSelectionID " + getSelectionID() + "\n" + 
/*      */ 
/*      */       
/* 2754 */       "getReleaseType " + getReleaseType() + "\n" + 
/* 2755 */       "getSelectionConfig " + getSelectionConfig() + "\n" + 
/* 2756 */       "getSelectionSubConfig " + getSelectionSubConfig() + "\n" + 
/* 2757 */       "getUpc " + getUpc() + "\n" + 
/* 2758 */       "getGenre " + getGenre() + "\n" + 
/* 2759 */       "getFamilyId " + getFamilyId() + "\n" + 
/* 2760 */       "getEnvironmentId " + getEnvironmentId() + "\n" + 
/* 2761 */       "getCompanyId " + getCompanyId() + "\n" + 
/* 2762 */       "getDivisionId " + getDivisionId() + "\n" + 
/* 2763 */       "getLabelId " + getLabelId() + "\n" + 
/* 2764 */       "getStreetDate " + getStreetDate() + "\n" + 
/* 2765 */       "getInternationalDate " + getInternationalDate() + "\n" + 
/*      */ 
/*      */       
/* 2768 */       "getLabelContactId " + getLabelContactId() + "\n" + 
/* 2769 */       "getSelectionStatus " + getSelectionStatus() + "\n" + 
/* 2770 */       "getHoldSelection " + getHoldSelection() + "\n" + 
/* 2771 */       "getHoldReason " + getHoldReason() + "\n" + 
/* 2772 */       "getSelectionComments " + getSelectionComments() + "\n" + 
/*      */       
/* 2774 */       "getSpecialPackaging " + getSpecialPackaging() + "\n" + 
/* 2775 */       "getPrice " + getPrice() + "\n" + 
/*      */       
/* 2777 */       "getNumberOfUnits " + getNumberOfUnits() + "\n" + 
/* 2778 */       "gePressAndDistribution " + getPressAndDistribution() + "\n" + 
/* 2779 */       "getPrefixId " + getPrefixID() + "\n" + 
/*      */       
/* 2781 */       "getSelectionPackaging " + getSelectionPackaging() + "\n" + 
/*      */       
/* 2783 */       "getImpactDate " + getImpactDate() + "\n" + 
/*      */ 
/*      */ 
/*      */       
/* 2787 */       "getLastUpdateDate " + getLastUpdateDate() + "\n" + 
/* 2788 */       "getLastUpdateCheck " + getLastUpdatedCheck() + "\n" + 
/*      */       
/* 2790 */       "getDigitalRlsDate " + getDigitalRlsDate() + "\n" + 
/* 2791 */       "getOperCompany " + getOperCompany() + "\n" + 
/* 2792 */       "getSuperLabel " + getSuperLabel() + "\n" + 
/* 2793 */       "getConfigCode " + getConfigCode() + "\n" + 
/* 2794 */       "getSoundScanGrp " + getSoundScanGrp() + "\n" + 
/* 2795 */       "internationalFlag " + getInternationalFlag() + "\n" + 
/*      */       
/* 2797 */       "getASide " + getASide() + "\n" + 
/* 2798 */       "getBSide " + getBSide() + "\n" + 
/* 2799 */       "--End Selection--" + "\n";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2808 */   public Object clone() throws CloneNotSupportedException { return super.clone(); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2817 */   public String getNotepadContentObjectId() { return Integer.toString(this.selectionID); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2822 */   public void setSortBy(int s) { this.sortBy = s; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2832 */   public Vector getMultSelections() { return this.multSelections; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2840 */   public void setMultSelections(Vector multSelections) { this.multSelections = multSelections; }
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
/* 2851 */   public Vector getMultOtherContacts() { return this.multOtherContacts; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2859 */   public void setMultOtherContacts(Vector multOtherContacts) { this.multOtherContacts = multOtherContacts; }
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
/* 2870 */     String thisEnvironment = (getEnvironment() != null && getEnvironment().getName() != null) ? 
/* 2871 */       getEnvironment().getName().trim() : "";
/*      */ 
/*      */     
/* 2874 */     String thatEnvironment = (((Selection)sel).getEnvironment() != null && ((Selection)sel).getEnvironment().getName() != null) ? (
/* 2875 */       (Selection)sel).getEnvironment().getName().trim() : "";
/*      */     
/* 2877 */     if (thisEnvironment.equals("") && !thatEnvironment.equals("")) {
/* 2878 */       return 1;
/*      */     }
/* 2880 */     if (!thisEnvironment.equals("") && thatEnvironment.equals("")) {
/* 2881 */       return -1;
/*      */     }
/* 2883 */     return thisEnvironment.compareTo(thatEnvironment);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2891 */   public Environment getEnvironment() { return this.environment; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2899 */   public void setEnvironment(Environment environment) { this.environment = environment; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2908 */   public int getEnvironmentId() { return this.environmentId; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2917 */   public void setEnvironmentId(int environmentId) { this.environmentId = environmentId; }
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
/* 2929 */   public Calendar getDigitalRlsDate() { return this.digital_rls_date; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2938 */   public void setDigitalRlsDate(Calendar digital_rls_date) { this.digital_rls_date = digital_rls_date; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2947 */   public String getDigitalRlsDateString() { return this.digital_rls_date_string; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2956 */   public void setDigitalRlsDateString(String digital_rls_date_string) { this.digital_rls_date_string = digital_rls_date_string; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2966 */   public String getOperCompany() { return this.oper_company; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2975 */   public void setOperCompany(String oper_company) { this.oper_company = oper_company; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2985 */   public String getSuperLabel() { return this.super_label; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2994 */   public void setSuperLabel(String super_label) { this.super_label = super_label; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 3004 */   public String getSubLabel() { return this.sub_label; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 3013 */   public void setSubLabel(String sub_label) { this.sub_label = sub_label; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 3023 */   public String getConfigCode() { return this.config_code; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 3032 */   public void setConfigCode(String config_code) { this.config_code = config_code; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 3042 */   public String getSoundScanGrp() { return this.soundscan_grp; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 3051 */   public void setSoundScanGrp(String soundscan_grp) { this.soundscan_grp = soundscan_grp; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 3061 */   public boolean getInternationalFlag() { return this.international_flag; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 3070 */   public void setInternationalFlag(boolean international_flag) { this.international_flag = international_flag; }
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
/* 3082 */   public String getImprint() { return this.imprint; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 3091 */   public void setImprint(String imprint) { this.imprint = imprint; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 3100 */   public boolean getIsDigital() { return this.isDigital; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 3109 */   public void setIsDigital(boolean isDigital) { this.isDigital = isDigital; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 3118 */   public boolean getNewBundleFlag() { return this.new_bundle_flag; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 3127 */   public void setNewBundleFlag(boolean new_bundle_flag) { this.new_bundle_flag = new_bundle_flag; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 3136 */   public String getGridNumber() { return this.grid_number; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 3145 */   public void setGridNumber(String grid_number) { this.grid_number = grid_number; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 3154 */   public String getSpecialInstructions() { return this.special_instructions; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 3163 */   public void setSpecialInstructions(String special_instructions) { this.special_instructions = special_instructions; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 3171 */   public int getArchimedesId() { return this.archimedesId; }
/*      */ 
/*      */ 
/*      */   
/* 3175 */   public void setArchimedesId(int archimedesId) { this.archimedesId = archimedesId; }
/*      */ 
/*      */ 
/*      */   
/* 3179 */   public int getReleaseFamilyId() { return this.releaseFamilyId; }
/*      */ 
/*      */   
/*      */   public void setReleaseFamilyId(int releaseFamilyId) {
/* 3183 */     this.releaseFamilyId = releaseFamilyId;
/* 3184 */     setCalendarGroup();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 3193 */   public boolean getPriority() { return this.priority_flag; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 3202 */   public void setPriority(boolean priority_flag) { this.priority_flag = priority_flag; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 3211 */   public int getCalendarGroup() { return this.calendarGroup; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void setCalendarGroup() {
/* 3221 */     String releaseFamilyName = MilestoneHelper.getStructureName(getReleaseFamilyId());
/* 3222 */     if (releaseFamilyName.equals("Canada")) {
/*      */       
/* 3224 */       this.calendarGroup = 2;
/*      */     }
/*      */     else {
/*      */       
/* 3228 */       this.calendarGroup = 1;
/*      */     } 
/*      */   }
/*      */ }


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\Selection.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */