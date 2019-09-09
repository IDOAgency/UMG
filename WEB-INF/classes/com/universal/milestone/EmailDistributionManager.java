/*     */ package WEB-INF.classes.com.universal.milestone;
/*     */ 
/*     */ import com.techempower.ComponentLog;
/*     */ import com.techempower.EnhancedProperties;
/*     */ import com.techempower.gemini.Context;
/*     */ import com.techempower.gemini.GeminiApplication;
/*     */ import com.universal.milestone.EmailDistribution;
/*     */ import com.universal.milestone.EmailDistributionDetail;
/*     */ import com.universal.milestone.EmailDistributionManager;
/*     */ import com.universal.milestone.EmailDistributionReleasingFamily;
/*     */ import com.universal.milestone.Environment;
/*     */ import com.universal.milestone.JdbcConnector;
/*     */ import com.universal.milestone.Label;
/*     */ import com.universal.milestone.MilestoneConstants;
/*     */ import com.universal.milestone.MilestoneHelper;
/*     */ import com.universal.milestone.Notepad;
/*     */ import com.universal.milestone.SessionUserEmail;
/*     */ import com.universal.milestone.SessionUserEmailObj;
/*     */ import com.universal.milestone.User;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Hashtable;
/*     */ import java.util.StringTokenizer;
/*     */ import java.util.Vector;
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
/*     */ public class EmailDistributionManager
/*     */   implements MilestoneConstants
/*     */ {
/*     */   public static final String COMPONENT_CODE = "mEmailDistr";
/*     */   public static final String DEFAULT_HEADER_QUERY = "SELECT * FROM vi_email_distribution_header ";
/*     */   public static final String DEFAULT_HEADER_ORDER = " ORDER BY last_name, first_name";
/*     */   public static final int WestAndEast = 2;
/*     */   public static final int None = 3;
/*     */   public static final int UME_CATALOG = 1052;
/*  51 */   protected static EmailDistributionManager emailDistributionManager = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static ComponentLog log;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  63 */   public static void configure(EnhancedProperties props, GeminiApplication application) { log = application.getLog("mEmailDistr"); }
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
/*     */   public static EmailDistributionManager getInstance() {
/*  79 */     if (emailDistributionManager == null)
/*     */     {
/*  81 */       emailDistributionManager = new EmailDistributionManager();
/*     */     }
/*  83 */     return emailDistributionManager;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EmailDistribution getEmailDistribution(int distributionId, boolean getTimestamp) {
/*  93 */     String userQuery = 
/*  94 */       "SELECT * FROM vi_Email_Distribution_Header WHERE distribution_id = " + distributionId + ";";
/*     */     
/*  96 */     EmailDistribution emailDist = null;
/*     */     
/*  98 */     JdbcConnector connector = MilestoneHelper.getConnector(userQuery);
/*  99 */     connector.runQuery();
/*     */     
/* 101 */     if (connector.more()) {
/*     */       
/* 103 */       emailDist = new EmailDistribution();
/*     */       
/* 105 */       emailDist.setDistributionId(connector.getInt("distribution_id"));
/* 106 */       emailDist.setFirstName(connector.getField("first_name", ""));
/* 107 */       emailDist.setLastName(connector.getField("last_name", ""));
/* 108 */       emailDist.setEmail(connector.getField("email", ""));
/* 109 */       emailDist.setPfm(connector.getBoolean("pfm"));
/* 110 */       emailDist.setBom(connector.getBoolean("bom"));
/* 111 */       emailDist.setPromo(connector.getBoolean("promo"));
/* 112 */       emailDist.setCommercial(connector.getBoolean("commercial"));
/* 113 */       emailDist.setInactive(connector.getBoolean("inactive"));
/* 114 */       emailDist.setLabelDistribution(connector.getInt("label_distribution"));
/*     */       
/* 116 */       emailDist.setProductType(connector.getInt("product_type"));
/*     */       
/* 118 */       long lastUpdatedLong = Long.parseLong(connector.getField("last_updated_ck"), 16);
/* 119 */       int lastUpdatedBy = connector.getIntegerField("last_updated_by");
/* 120 */       String lastDateString = connector.getField("last_updated_on");
/*     */ 
/*     */       
/* 123 */       connector.close();
/*     */ 
/*     */       
/* 126 */       if (lastDateString != null) {
/* 127 */         emailDist.setLastUpdatedOn(MilestoneHelper.getDatabaseDate(lastDateString));
/*     */       }
/*     */       
/* 130 */       emailDist.setLastUpdatedBy(lastUpdatedBy);
/*     */ 
/*     */       
/* 133 */       if (getTimestamp) {
/* 134 */         emailDist.setLastUpdatedCk(lastUpdatedLong);
/*     */       }
/*     */       
/* 137 */       emailDist.setEmailDistributionDetail(getEmailDistributionDetail(distributionId));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 143 */       emailDist.setAssignedEnvironments(getAssignedEnvironments(emailDist.getDistributionId()));
/* 144 */       emailDist.setReleasingFamily(EmailDistributionReleasingFamily.get(emailDist.getDistributionId()));
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 152 */     return emailDist;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EmailDistribution save(EmailDistribution emailDist, User sessionUser, Vector checkedDetail, Context context) {
/* 162 */     long timestamp = emailDist.getLastUpdatedCk();
/*     */     
/* 164 */     String query = "sp_sav_Email_Distribution_Header " + 
/* 165 */       emailDist.getDistributionId() + 
/* 166 */       ",'" + MilestoneHelper.escapeSingleQuotes(emailDist.getFirstName()) + "'" + 
/* 167 */       ",'" + MilestoneHelper.escapeSingleQuotes(emailDist.getLastName()) + "'" + 
/* 168 */       ",'" + MilestoneHelper.escapeSingleQuotes(emailDist.getEmail()) + "'" + 
/* 169 */       "," + (emailDist.getPfm() ? 1 : 0) + 
/* 170 */       "," + (emailDist.getBom() ? 1 : 0) + 
/* 171 */       "," + (emailDist.getPromo() ? 1 : 0) + 
/* 172 */       "," + (emailDist.getCommercial() ? 1 : 0) + 
/* 173 */       "," + sessionUser.getUserId() + 
/* 174 */       "," + timestamp + 
/* 175 */       "," + (emailDist.getInactive() ? 1 : 0) + 
/* 176 */       "," + emailDist.getLabelDistribution() + 
/* 177 */       "," + emailDist.getProductType();
/*     */ 
/*     */ 
/*     */     
/* 181 */     JdbcConnector connector = MilestoneHelper.getConnector(query);
/* 182 */     connector.runQuery();
/* 183 */     int savedDistributionId = connector.getIntegerField("ReturnId");
/* 184 */     connector.close();
/*     */ 
/*     */     
/* 187 */     if (savedDistributionId > 0) {
/* 188 */       emailDist.setDistributionId(savedDistributionId);
/*     */     }
/*     */     
/* 191 */     String timestampQuery = "SELECT last_updated_ck, last_updated_on FROM vi_Email_Distribution_Header WHERE distribution_id = " + 
/*     */       
/* 193 */       emailDist.getDistributionId() + 
/* 194 */       ";";
/*     */     
/* 196 */     JdbcConnector connectorTimestamp = MilestoneHelper.getConnector(timestampQuery);
/* 197 */     connectorTimestamp.runQuery();
/* 198 */     if (connectorTimestamp.more()) {
/*     */       
/* 200 */       emailDist.setLastUpdatedCk(Long.parseLong(connectorTimestamp.getField("last_updated_ck"), 16));
/* 201 */       String lastString = connectorTimestamp.getField("last_updated_on");
/* 202 */       if (lastString != null)
/* 203 */         emailDist.setLastUpdatedOn(MilestoneHelper.getDatabaseDate(connectorTimestamp.getField("last_updated_on"))); 
/*     */     } 
/* 205 */     connectorTimestamp.close();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 210 */     int distributionId = emailDist.getDistributionId();
/*     */ 
/*     */     
/* 213 */     Vector emailDistDetails = getEmailDistributionDetail(distributionId);
/*     */ 
/*     */     
/* 216 */     boolean isNew = true;
/* 217 */     boolean isChecked = false;
/* 218 */     boolean isUnChecked = false;
/*     */ 
/*     */     
/* 221 */     for (int b = 0; b < emailDistDetails.size(); b++) {
/*     */       
/* 223 */       EmailDistributionDetail emailDistDetail = (EmailDistributionDetail)emailDistDetails.elementAt(b);
/* 224 */       if (emailDistDetail != null) {
/*     */         
/* 226 */         isChecked = false;
/* 227 */         for (int j = 0; j < checkedDetail.size(); j++) {
/*     */           
/* 229 */           Environment checkEnvironment = (Environment)checkedDetail.get(j);
/* 230 */           if (checkEnvironment != null && checkEnvironment.getStructureID() == emailDistDetail.getStructureId()) {
/*     */             
/* 232 */             isChecked = true;
/*     */             
/*     */             break;
/*     */           } 
/*     */         } 
/* 237 */         if (!isChecked)
/*     */         {
/* 239 */           deleteEmailDistributionDetail(distributionId, emailDistDetail.getStructureId());
/*     */         }
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 245 */     for (int l = 0; l < checkedDetail.size(); l++) {
/*     */       
/* 247 */       Environment checkEnvironment = (Environment)checkedDetail.get(l);
/* 248 */       if (checkEnvironment != null) {
/*     */         
/* 250 */         isNew = true;
/* 251 */         for (int c = 0; c < emailDistDetails.size(); c++) {
/*     */           
/* 253 */           EmailDistributionDetail emailDistDetail = (EmailDistributionDetail)emailDistDetails.elementAt(c);
/* 254 */           if (emailDistDetail != null && emailDistDetail.getStructureId() == checkEnvironment.getStructureID()) {
/*     */             
/* 256 */             isNew = false;
/*     */             break;
/*     */           } 
/*     */         } 
/* 260 */         if (isNew)
/*     */         {
/*     */           
/* 263 */           addEmailDistributionDetail(distributionId, checkEnvironment.getStructureID(), sessionUser.getUserId());
/*     */         }
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 269 */     context.removeSessionValue("copiedEmailDistObj");
/* 270 */     return emailDist;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Vector getEmailDistributionDetail(int distributionId) {
/* 279 */     String strQuery = "SELECT b.* FROM vi_email_distribution_detail b WHERE distribution_id = " + 
/*     */       
/* 281 */       distributionId + 
/* 282 */       " ORDER BY b.distribution_id;";
/*     */     
/* 284 */     Vector precache = new Vector();
/*     */     
/* 286 */     JdbcConnector connector = MilestoneHelper.createConnector(strQuery);
/*     */ 
/*     */ 
/*     */     
/* 290 */     connector.setForwardOnly(false);
/* 291 */     connector.runQuery();
/*     */ 
/*     */ 
/*     */     
/* 295 */     while (connector.more()) {
/*     */       
/* 297 */       EmailDistributionDetail environment = new EmailDistributionDetail();
/* 298 */       environment.setDistributionId(connector.getIntegerField("distribution_id"));
/* 299 */       environment.setStructureId(connector.getIntegerField("structure_id"));
/* 300 */       precache.add(environment);
/* 301 */       environment = null;
/* 302 */       connector.next();
/*     */     } 
/*     */     
/* 305 */     connector.close();
/*     */ 
/*     */ 
/*     */     
/* 309 */     return precache;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void deleteEmailDistribution(int distributionId) {
/* 321 */     String query = "sp_del_Email_Distribution_Detail " + 
/* 322 */       distributionId + "," + 
/* 323 */       -1;
/*     */     
/* 325 */     JdbcConnector connector = MilestoneHelper.getConnector(query);
/* 326 */     connector.runQuery();
/* 327 */     connector.close();
/*     */ 
/*     */     
/* 330 */     query = "sp_del_Email_Distribution_Header " + 
/* 331 */       distributionId;
/*     */     
/* 333 */     connector = MilestoneHelper.getConnector(query);
/* 334 */     connector.runQuery();
/* 335 */     connector.close();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void saveCopyEmailDistributionReleasingFamily(int distributionId, int copyDistributionId, int userId) {
/* 346 */     String query = "sp_saveCopy_Email_Distribution_ReleasingFamily " + distributionId + "," + 
/* 347 */       copyDistributionId + "," + userId;
/*     */     
/* 349 */     JdbcConnector connector = MilestoneHelper.getConnector(query);
/* 350 */     connector.runQuery();
/* 351 */     connector.close();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Vector getDistributionNotepadList(Notepad notepad) {
/* 362 */     String query = "";
/* 363 */     if (notepad != null && !notepad.getSearchQuery().equals("")) {
/*     */       
/* 365 */       query = notepad.getSearchQuery();
/* 366 */       query = String.valueOf(query) + notepad.getOrderBy();
/*     */     
/*     */     }
/*     */     else {
/*     */       
/* 371 */       query = "SELECT * FROM vi_email_distribution_header  ORDER BY last_name, first_name";
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 376 */     JdbcConnector connector = MilestoneHelper.getConnector(query);
/* 377 */     connector.runQuery();
/*     */     
/* 379 */     Vector precache = new Vector();
/*     */     
/* 381 */     while (connector.more()) {
/*     */       
/* 383 */       EmailDistribution emailDist = new EmailDistribution();
/* 384 */       emailDist.setDistributionId(connector.getInt("distribution_id"));
/* 385 */       emailDist.setFirstName(connector.getField("first_name", ""));
/* 386 */       emailDist.setLastName(connector.getField("last_name", ""));
/* 387 */       emailDist.setEmail(connector.getField("email", ""));
/* 388 */       emailDist.setPfm(connector.getBoolean("pfm"));
/* 389 */       emailDist.setBom(connector.getBoolean("bom"));
/* 390 */       emailDist.setPromo(connector.getBoolean("promo"));
/* 391 */       emailDist.setCommercial(connector.getBoolean("commercial"));
/* 392 */       emailDist.setInactive(connector.getBoolean("inactive"));
/* 393 */       emailDist.setLabelDistribution(connector.getInt("label_distribution"));
/*     */       
/* 395 */       emailDist.setProductType(connector.getInt("product_type"));
/*     */ 
/*     */       
/* 398 */       emailDist.setEmailDistributionDetail(getEmailDistributionDetail(emailDist.getDistributionId()));
/*     */       
/* 400 */       precache.addElement(emailDist);
/* 401 */       emailDist = null;
/* 402 */       connector.next();
/*     */     } 
/*     */     
/* 405 */     connector.close();
/*     */ 
/*     */     
/* 408 */     return precache;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 413 */   public static String getDefaultQuery() { return "SELECT * FROM vi_email_distribution_header "; }
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
/*     */   public static void deleteEmailDistributionDetail(int distributionId, int structureId) {
/* 427 */     String query = "sp_del_Email_Distribution_Detail " + 
/* 428 */       distributionId + "," + 
/* 429 */       structureId;
/*     */ 
/*     */     
/* 432 */     JdbcConnector connector = MilestoneHelper.getConnector(query);
/* 433 */     connector.runQuery();
/*     */     
/* 435 */     connector.close();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void addEmailDistributionDetail(int distributionId, int structureId, int updator) {
/* 446 */     String query = "sp_sav_Email_Distribution_Detail " + 
/* 447 */       distributionId + "," + 
/* 448 */       structureId + "," + 
/* 449 */       updator;
/*     */ 
/*     */ 
/*     */     
/* 453 */     JdbcConnector connector = MilestoneHelper.getConnector(query);
/* 454 */     connector.runQuery();
/*     */     
/* 456 */     connector.close();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setEmailDistributionNotepadQuery(Context context, Notepad notepad) {
/* 468 */     if (notepad != null) {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 473 */       String firstNameSearch = context.getParameter("firstNameSrch");
/* 474 */       String lastNameSearch = context.getParameter("lastNameSrch");
/* 475 */       String formTypeSearch = context.getParameter("formTypeSrch");
/* 476 */       String releaseTypeSearch = context.getParameter("releaseTypeSrch");
/* 477 */       String productTypeSearch = context.getParameter("productTypeSrch");
/* 478 */       String environmentSearch = context.getParameter("environmentSrch");
/* 479 */       if (environmentSearch == null) {
/* 480 */         environmentSearch = "0";
/*     */       }
/*     */ 
/*     */       
/* 484 */       String params = "";
/*     */ 
/*     */       
/* 487 */       String query = "SELECT * FROM vi_email_distribution_header ";
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 492 */       if (!environmentSearch.equals("0")) {
/* 493 */         query = String.valueOf(query) + " JOIN vi_email_distribution_detail   ON vi_email_distribution_header.distribution_id =   vi_email_distribution_detail.distribution_id ";
/*     */ 
/*     */         
/* 496 */         query = String.valueOf(query) + MilestoneHelper.addQueryParams(query, " vi_email_distribution_detail.structure_id = " + environmentSearch);
/*     */       } 
/*     */       
/* 499 */       if (MilestoneHelper.isStringNotEmpty(firstNameSearch)) {
/* 500 */         query = String.valueOf(query) + 
/* 501 */           MilestoneHelper.addQueryParams(query, " vi_email_distribution_header.first_Name " + MilestoneHelper.setWildCardsEscapeSingleQuotes(firstNameSearch));
/*     */       }
/* 503 */       if (MilestoneHelper.isStringNotEmpty(lastNameSearch)) {
/* 504 */         query = String.valueOf(query) + 
/* 505 */           MilestoneHelper.addQueryParams(query, " vi_email_distribution_header.last_Name " + MilestoneHelper.setWildCardsEscapeSingleQuotes(lastNameSearch));
/*     */       }
/* 507 */       if (MilestoneHelper.isStringNotEmpty(formTypeSearch) && !formTypeSearch.equals("-1")) {
/* 508 */         if (formTypeSearch.equals("pfm")) {
/* 509 */           query = String.valueOf(query) + MilestoneHelper.addQueryParams(query, " vi_email_distribution_header.pfm = 1 ");
/* 510 */           query = String.valueOf(query) + MilestoneHelper.addQueryParams(query, " vi_email_distribution_header.bom = 0 ");
/* 511 */         } else if (formTypeSearch.equals("bom")) {
/* 512 */           query = String.valueOf(query) + MilestoneHelper.addQueryParams(query, " vi_email_distribution_header.bom = 1 ");
/* 513 */           query = String.valueOf(query) + MilestoneHelper.addQueryParams(query, " vi_email_distribution_header.pfm = 0 ");
/*     */         } 
/*     */       }
/*     */       
/* 517 */       if (MilestoneHelper.isStringNotEmpty(releaseTypeSearch) && !releaseTypeSearch.equals("-1")) {
/* 518 */         if (releaseTypeSearch.equals("promo")) {
/* 519 */           query = String.valueOf(query) + MilestoneHelper.addQueryParams(query, " vi_email_distribution_header.promo = 1 ");
/* 520 */           query = String.valueOf(query) + MilestoneHelper.addQueryParams(query, " vi_email_distribution_header.commercial = 0 ");
/*     */         }
/* 522 */         else if (releaseTypeSearch.equals("commercial")) {
/* 523 */           query = String.valueOf(query) + MilestoneHelper.addQueryParams(query, " vi_email_distribution_header.commercial = 1 ");
/* 524 */           query = String.valueOf(query) + MilestoneHelper.addQueryParams(query, " vi_email_distribution_header.promo = 0 ");
/*     */         } 
/*     */       }
/*     */       
/* 528 */       if (MilestoneHelper.isStringNotEmpty(productTypeSearch) && !productTypeSearch.equals("-1")) {
/* 529 */         query = String.valueOf(query) + MilestoneHelper.addQueryParams(query, " vi_email_distribution_header.product_type  = " + productTypeSearch);
/*     */       }
/* 531 */       String order = " ORDER BY last_name, first_name";
/*     */       
/* 533 */       notepad.setSearchQuery(query);
/* 534 */       notepad.setOrderBy(order);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isTimestampValid(EmailDistribution emailDistribution) {
/* 546 */     if (emailDistribution != null) {
/*     */       
/* 548 */       String timestampQuery = "SELECT last_updated_ck  FROM vi_email_distribution_header WHERE distribution_id = " + 
/*     */         
/* 550 */         emailDistribution.getDistributionId() + 
/* 551 */         ";";
/* 552 */       JdbcConnector connectorTimestamp = MilestoneHelper.getConnector(timestampQuery);
/* 553 */       connectorTimestamp.runQuery();
/* 554 */       if (connectorTimestamp.more())
/*     */       {
/* 556 */         if (emailDistribution.getLastUpdatedCk() != Long.parseLong(connectorTimestamp.getField("last_updated_ck", ""), 16)) {
/*     */           
/* 558 */           connectorTimestamp.close();
/* 559 */           return false;
/*     */         } 
/*     */       }
/* 562 */       connectorTimestamp.close();
/* 563 */       return true;
/*     */     } 
/* 565 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isDuplicate(EmailDistribution emailDistribution) {
/* 575 */     boolean isDuplicate = false;
/*     */     
/* 577 */     if (emailDistribution != null) {
/*     */       
/* 579 */       String query = "SELECT * FROM vi_email_distribution_header WHERE  first_name = '" + 
/* 580 */         MilestoneHelper.escapeSingleQuotes(emailDistribution.getFirstName()) + "' " + 
/* 581 */         " and last_name = '" + MilestoneHelper.escapeSingleQuotes(emailDistribution.getLastName()) + "' " + 
/* 582 */         " AND distribution_id <> " + emailDistribution.getDistributionId();
/*     */ 
/*     */       
/* 585 */       JdbcConnector connector = MilestoneHelper.getConnector(query);
/* 586 */       connector.runQuery();
/*     */       
/* 588 */       if (connector.more()) {
/* 589 */         isDuplicate = true;
/*     */       }
/* 591 */       connector.close();
/*     */     } 
/*     */ 
/*     */     
/* 595 */     return isDuplicate;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getEmailTo(Environment environment, String formType, String releaseType, Label label, boolean IsDigital, int releaseFamilyId, int familyId) {
/* 604 */     StringBuffer emailTo = new StringBuffer();
/*     */     
/* 606 */     emailTo.append("");
/*     */ 
/*     */     
/* 609 */     String strQuery = "SELECT d.distribution_id, d.structure_id, h.email, h.inactive, h.pfm, h.bom,h.promo, h.commercial, h.label_distribution, h.product_type  FROM vi_email_distribution_detail d  inner join vi_email_distribution_header h on d.distribution_id = h.distribution_id  WHERE d.structure_id = " + 
/*     */ 
/*     */ 
/*     */       
/* 613 */       environment.getStructureID() + 
/* 614 */       " ORDER BY d.structure_id;";
/*     */     
/* 616 */     JdbcConnector connector = MilestoneHelper.createConnector(strQuery);
/*     */ 
/*     */ 
/*     */     
/* 620 */     connector.setForwardOnly(false);
/* 621 */     connector.runQuery();
/*     */ 
/*     */ 
/*     */     
/* 625 */     while (connector.more()) {
/*     */       
/* 627 */       int distributionId = connector.getInt("distribution_id", -1);
/* 628 */       boolean inactive = connector.getBoolean("inactive");
/* 629 */       boolean pfm = connector.getBoolean("pfm");
/* 630 */       boolean bom = connector.getBoolean("bom");
/* 631 */       boolean promo = connector.getBoolean("promo");
/* 632 */       boolean commercial = connector.getBoolean("commercial");
/* 633 */       int label_distribution = connector.getInt("label_distribution", 2);
/* 634 */       int product_type = connector.getInt("product_type", 2);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 640 */       if (!inactive)
/*     */       {
/* 642 */         if (connector.getInt("structure_id") == environment.getStructureID() && ((
/* 643 */           pfm && formType.equalsIgnoreCase("PFM")) || (
/* 644 */           bom && formType.equalsIgnoreCase("BOM"))))
/*     */         {
/* 646 */           if ((promo && releaseType.equalsIgnoreCase("PR")) || (
/* 647 */             commercial && releaseType.equalsIgnoreCase("CO"))) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 653 */             Hashtable releasingFamilyHash = EmailDistributionReleasingFamily.get(distributionId);
/*     */             
/* 655 */             if (releasingFamilyHash == null || (
/* 656 */               releasingFamilyHash != null && releasingFamilyHash.get(Integer.toString(familyId)) == null)) {
/*     */ 
/*     */               
/* 659 */               releasingFamilyHash = new Hashtable();
/* 660 */               Vector releasingFamilies = new Vector();
/* 661 */               releasingFamilies.add(new EmailDistributionReleasingFamily(familyId, familyId, true, "", ""));
/* 662 */               releasingFamilyHash.put(Integer.toString(familyId), releasingFamilies);
/*     */             } 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 668 */             boolean found = false;
/* 669 */             Vector releasingFamilies = (Vector)releasingFamilyHash.get(Integer.toString(familyId));
/* 670 */             if (releasingFamilies != null) {
/* 671 */               for (int i = 0; i < releasingFamilies.size(); i++) {
/* 672 */                 EmailDistributionReleasingFamily releasingFamily = (EmailDistributionReleasingFamily)releasingFamilies.get(i);
/* 673 */                 if (releasingFamily != null && releasingFamily.getReleasingFamilyId() == releaseFamilyId) {
/* 674 */                   found = true;
/*     */                   
/*     */                   break;
/*     */                 } 
/*     */               } 
/*     */             }
/*     */             
/* 681 */             if (found)
/*     */             {
/*     */ 
/*     */ 
/*     */               
/* 686 */               if (releaseFamilyId != 1052 || 
/* 687 */                 label_distribution == 2) {
/*     */                 
/* 689 */                 if (checkDigital(IsDigital, product_type)) {
/* 690 */                   emailTo.append(String.valueOf(connector.getField("email", "")) + ";");
/*     */                 
/*     */                 }
/*     */               
/*     */               }
/* 695 */               else if (label_distribution != 3 && 
/* 696 */                 label.getDistribution() == label_distribution) {
/*     */                 
/* 698 */                 if (checkDigital(IsDigital, product_type)) {
/* 699 */                   emailTo.append(String.valueOf(connector.getField("email", "")) + ";");
/*     */                 }
/*     */               } 
/*     */             }
/*     */           } 
/*     */         }
/*     */       }
/*     */ 
/*     */       
/* 708 */       connector.next();
/*     */     } 
/*     */     
/* 711 */     connector.close();
/*     */ 
/*     */     
/* 714 */     return emailTo.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean checkDigital(boolean IsDigital, int product_type) {
/* 723 */     if (product_type == 2) {
/* 724 */       return true;
/*     */     }
/* 726 */     if (IsDigital && product_type == 0) {
/* 727 */       return true;
/*     */     }
/* 729 */     if (!IsDigital && product_type == 1) {
/* 730 */       return true;
/*     */     }
/*     */     
/* 733 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean sendEmailDistribution(String subject, String body, String recipients, String copyRecipients, String attachment) {
/* 742 */     boolean result = false;
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
/*     */     try {
/* 756 */       StringTokenizer valueTokenizer = new StringTokenizer(recipients, ";");
/* 757 */       ArrayList destList = new ArrayList();
/*     */       
/* 759 */       while (valueTokenizer.hasMoreTokens()) {
/* 760 */         destList.add(new SessionUserEmailObj(valueTokenizer.nextToken().trim(), true));
/*     */       }
/*     */       
/* 763 */       StringTokenizer ccValueTokenizer = new StringTokenizer(copyRecipients, ";");
/* 764 */       ArrayList ccList = new ArrayList();
/*     */       
/* 766 */       while (ccValueTokenizer.hasMoreTokens()) {
/* 767 */         ccList.add(new SessionUserEmailObj(ccValueTokenizer.nextToken().trim(), true));
/*     */       }
/* 769 */       SessionUserEmail sue = new SessionUserEmail();
/* 770 */       body = "<font face='arial'>" + body + "</font>";
/* 771 */       result = sue.sendEmail(destList, body, subject, attachment, ccList);
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
/* 787 */       if (!result)
/*     */       {
/*     */         
/* 790 */         String emailAuditSQL = "INSERT INTO [dbo].[Email_Distribution_audit] ";
/* 791 */         emailAuditSQL = String.valueOf(emailAuditSQL) + " ([subject], [body], [recipients], [copyRecipients], ";
/* 792 */         emailAuditSQL = String.valueOf(emailAuditSQL) + " [fileName], [serverName], [errorCode]) ";
/* 793 */         emailAuditSQL = String.valueOf(emailAuditSQL) + " VALUES('" + MilestoneHelper.escapeSingleQuotes(subject) + "',";
/* 794 */         emailAuditSQL = String.valueOf(emailAuditSQL) + "'" + MilestoneHelper.escapeSingleQuotes(body) + "',";
/* 795 */         emailAuditSQL = String.valueOf(emailAuditSQL) + "'" + MilestoneHelper.escapeSingleQuotes(recipients) + "',";
/* 796 */         emailAuditSQL = String.valueOf(emailAuditSQL) + "'" + MilestoneHelper.escapeSingleQuotes(copyRecipients) + "',";
/* 797 */         emailAuditSQL = String.valueOf(emailAuditSQL) + "'" + MilestoneHelper.escapeSingleQuotes(attachment) + "',";
/* 798 */         emailAuditSQL = String.valueOf(emailAuditSQL) + "'" + JdbcConnector.PROVIDER_URL + "','1')";
/*     */         
/* 800 */         JdbcConnector connector = MilestoneHelper.getConnector(emailAuditSQL);
/* 801 */         connector.runQuery();
/* 802 */         connector.close();
/*     */         
/* 804 */         SessionUserEmail sueFailed = new SessionUserEmail();
/* 805 */         ArrayList destListMilestone = new ArrayList();
/* 806 */         destListMilestone.add(new SessionUserEmailObj("mark.cole@umusic.com", true));
/* 807 */         destListMilestone.add(new SessionUserEmailObj("marla.hall@umusic.com", true));
/*     */         
/* 809 */         String bodyFailed = "<b>The following message failed to send:</b><P><b>Subject:</b>" + 
/* 810 */           subject + "<BR>" + 
/* 811 */           "<b>Body:</b>" + body + "<BR>" + 
/* 812 */           "<b>To:</b>" + recipients + "<BR>" + 
/* 813 */           "<b>CC:</b>" + copyRecipients + "<BR>" + 
/* 814 */           "<b>Attachment:</b>" + attachment;
/*     */ 
/*     */         
/* 817 */         ArrayList cc = new ArrayList();
/* 818 */         sueFailed.sendEmail(destListMilestone, bodyFailed, "Email failed to send", "", cc);
/*     */       }
/*     */     
/*     */     }
/* 822 */     catch (Exception e) {
/*     */       
/* 824 */       System.out.println("send email exception...");
/*     */     } 
/* 826 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Vector getAssignedEnvironments(int distributionId) {
/* 836 */     Vector assignedEnvironments = null;
/* 837 */     String userQuery = "SELECT * FROM vi_Email_Distribution_Detail WHERE distribution_id = " + distributionId + ";";
/*     */ 
/*     */     
/* 840 */     JdbcConnector connector = MilestoneHelper.getConnector(userQuery);
/* 841 */     connector.runQuery();
/*     */     
/* 843 */     while (connector.more()) {
/*     */       
/* 845 */       if (assignedEnvironments == null) {
/* 846 */         assignedEnvironments = new Vector();
/*     */       }
/*     */       
/* 849 */       int environmentId = connector.getInt("structure_id", -1);
/* 850 */       if (environmentId != -1) {
/*     */ 
/*     */         
/* 853 */         Environment environment = MilestoneHelper.getEnvironmentById(environmentId);
/* 854 */         if (environment != null)
/*     */         {
/*     */           
/* 857 */           if (!assignedEnvironments.contains(environment))
/* 858 */             assignedEnvironments.add(environment); 
/*     */         }
/*     */       } 
/* 861 */       connector.next();
/*     */     } 
/*     */ 
/*     */     
/* 865 */     connector.close();
/*     */     
/* 867 */     return assignedEnvironments;
/*     */   }
/*     */ }


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\EmailDistributionManager.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */