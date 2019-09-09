/*     */ package WEB-INF.classes.com.universal.milestone;
/*     */ 
/*     */ import com.techempower.ComponentLog;
/*     */ import com.techempower.EnhancedProperties;
/*     */ import com.techempower.gemini.Context;
/*     */ import com.techempower.gemini.GeminiApplication;
/*     */ import com.universal.milestone.Environment;
/*     */ import com.universal.milestone.JdbcConnector;
/*     */ import com.universal.milestone.MilestoneConstants;
/*     */ import com.universal.milestone.MilestoneHelper;
/*     */ import com.universal.milestone.Notepad;
/*     */ import com.universal.milestone.ProductionGroupCode;
/*     */ import com.universal.milestone.Report;
/*     */ import com.universal.milestone.ReportConfigManager;
/*     */ import com.universal.milestone.Template;
/*     */ import com.universal.milestone.User;
/*     */ import com.universal.milestone.UserManager;
/*     */ import java.util.Collections;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ReportConfigManager
/*     */   implements MilestoneConstants
/*     */ {
/*  46 */   protected static ReportConfigManager reportConfigManager = null;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final String COMPONENT_CODE = "rcfm";
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final String DEFAULT_QUERY = "SELECT DISTINCT report_id, report_name, file_name, description, report_status, report_owner FROM vi_Report_Config";
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final String DEFAULT_ORDER = " ORDER BY report_name";
/*     */ 
/*     */ 
/*     */   
/*     */   protected static ComponentLog log;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  70 */   public static void configure(EnhancedProperties props, GeminiApplication application) { log = application.getLog("rcfm"); }
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
/*     */   public static ReportConfigManager getInstance() {
/*  88 */     if (reportConfigManager == null)
/*     */     {
/*  90 */       reportConfigManager = new ReportConfigManager();
/*     */     }
/*  92 */     return reportConfigManager;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Report getReportConfig(int reportId, boolean getTimestamp) {
/* 100 */     String reportConfigQuery = "SELECT report_id, type, description,        report_path, file_name,subreport_name, report_format, report_owner,        flag_begin_str_date, flag_end_str_date, flag_region,        flag_family, flag_environment, flag_company, flag_label,flag_contact,        flag_complete_task, flag_task_owner,        flag_key_task, flag_artist,        flag_release_type, flag_status, flag_roll_up, flag_future_2, flag_future_1 ,       report_status, report_name, last_updated_by, last_updated_on,        last_updated_ck, flag_begin_due_date, flag_end_due_date, flag_begin_effective_date,        flag_end_effective_date, flag_config, flag_scheduled_releases,        flag_adds_moves_both, flag_subconfig,        flag_product_type, flag_physical_product_activity,        flag_dist_co, flag_production_group_code  FROM vi_Report_Config   WHERE ( report_id = " + 
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
/* 115 */       reportId + ") ORDER BY description ";
/*     */     
/* 117 */     Report reportConfig = null;
/*     */     
/* 119 */     JdbcConnector connector = MilestoneHelper.getConnector(reportConfigQuery);
/* 120 */     connector.runQuery();
/*     */     
/* 122 */     if (connector.more()) {
/*     */       
/* 124 */       reportConfig = new Report(connector.getIntegerField("report_id"));
/*     */ 
/*     */       
/* 127 */       reportConfig.setDescription(connector.getField("description"));
/*     */ 
/*     */       
/* 130 */       reportConfig.setType(connector.getIntegerField("type"));
/*     */ 
/*     */       
/* 133 */       reportConfig.setFormat(connector.getIntegerField("report_format"));
/*     */ 
/*     */       
/* 136 */       reportConfig.setReportName(connector.getField("report_name"));
/*     */ 
/*     */       
/* 139 */       reportConfig.setSubName(connector.getField("subreport_name"));
/*     */ 
/*     */       
/* 142 */       reportConfig.setReportStatus(connector.getField("report_status"));
/*     */ 
/*     */       
/* 145 */       reportConfig.setPath(connector.getField("report_path"));
/*     */ 
/*     */       
/* 148 */       reportConfig.setFileName(connector.getField("file_name"));
/*     */ 
/*     */       
/* 151 */       reportConfig.setSubName(connector.getField("subreport_name"));
/*     */ 
/*     */       
/* 154 */       reportConfig.setReportOwner(connector.getIntegerField("report_owner"));
/*     */ 
/*     */       
/* 157 */       reportConfig.setBeginDateFlag(connector.getBoolean("flag_begin_str_date"));
/*     */ 
/*     */       
/* 160 */       reportConfig.setEndDateFlag(connector.getBoolean("flag_end_str_date"));
/*     */ 
/*     */       
/* 163 */       reportConfig.setRegionFlag(connector.getBoolean("flag_region"));
/*     */ 
/*     */       
/* 166 */       reportConfig.setFamilyFlag(connector.getBoolean("flag_family"));
/*     */ 
/*     */       
/* 169 */       reportConfig.setEnvironmentFlag(connector.getBoolean("flag_environment"));
/*     */ 
/*     */       
/* 172 */       reportConfig.setCompanyFlag(connector.getBoolean("flag_company"));
/*     */ 
/*     */       
/* 175 */       reportConfig.setLabelFlag(connector.getBoolean("flag_label"));
/*     */ 
/*     */       
/* 178 */       reportConfig.setContactFlag(connector.getBoolean("flag_contact"));
/*     */ 
/*     */       
/* 181 */       reportConfig.setCompleteKeyTaskFlag(connector.getBoolean("flag_complete_task"));
/*     */ 
/*     */       
/* 184 */       reportConfig.setUmlKeyTaskFlag(connector.getBoolean("flag_task_owner"));
/*     */ 
/*     */       
/* 187 */       reportConfig.setReleaseTypeFlag(connector.getBoolean("flag_release_type"));
/*     */ 
/*     */       
/* 190 */       reportConfig.setStatusFlag(connector.getBoolean("flag_status"));
/*     */ 
/*     */       
/* 193 */       reportConfig.setArtistFlag(connector.getBoolean("flag_artist"));
/*     */ 
/*     */       
/* 196 */       reportConfig.setFuture1Flag(connector.getBoolean("flag_future_1"));
/*     */ 
/*     */       
/* 199 */       reportConfig.setFuture2Flag(connector.getBoolean("flag_future_2"));
/*     */ 
/*     */       
/* 202 */       reportConfig.setKeyTaskFlag(connector.getBoolean("flag_key_task"));
/*     */ 
/*     */       
/* 205 */       reportConfig.setEndDueDateFlag(connector.getBoolean("flag_end_due_date"));
/*     */ 
/*     */       
/* 208 */       reportConfig.setBeginDueDateFlag(connector.getBoolean("flag_begin_due_date"));
/*     */ 
/*     */       
/* 211 */       reportConfig.setEndDueDateFlag(connector.getBoolean("flag_end_due_date"));
/*     */ 
/*     */       
/* 214 */       reportConfig.setParentsOnlyFlag(connector.getBoolean("flag_roll_up"));
/*     */ 
/*     */       
/* 217 */       reportConfig.setSubName(connector.getField("subreport_name"));
/*     */ 
/*     */       
/* 220 */       reportConfig.setBeginEffectiveDateFlag(connector.getBoolean("flag_begin_effective_date"));
/*     */ 
/*     */       
/* 223 */       reportConfig.setEndEffectiveDateFlag(connector.getBoolean("flag_end_effective_date"));
/*     */ 
/*     */       
/* 226 */       String lastDateString = connector.getField("last_updated_on");
/* 227 */       if (!lastDateString.equalsIgnoreCase("[none]")) {
/* 228 */         reportConfig.setLastUpdatedDate(MilestoneHelper.getDatabaseDate(lastDateString));
/*     */       }
/*     */       
/* 231 */       reportConfig.setLastUpdatingUser(connector.getIntegerField("last_updated_by"));
/*     */ 
/*     */ 
/*     */       
/* 235 */       if (getTimestamp) {
/*     */         
/* 237 */         long lastUpdatedLong = Long.parseLong(connector.getField("last_updated_ck"), 16);
/* 238 */         reportConfig.setLastUpdatedCk(lastUpdatedLong);
/*     */       } 
/*     */       
/* 241 */       reportConfig.setConfiguration(connector.getBoolean("flag_config"));
/*     */       
/* 243 */       reportConfig.setScheduledReleasesFlag(connector.getBoolean("flag_scheduled_releases"));
/*     */ 
/*     */       
/* 246 */       reportConfig.setAddsMovesBoth(connector.getBoolean("flag_adds_moves_both"));
/*     */ 
/*     */       
/* 249 */       reportConfig.setPhysicalProductActivity(connector.getBoolean("flag_physical_product_activity"));
/*     */ 
/*     */       
/* 252 */       reportConfig.setSubconfigFlag(connector.getBoolean("flag_subconfig"));
/*     */ 
/*     */       
/* 255 */       reportConfig.setProductType(connector.getIntegerField("flag_product_type"));
/*     */ 
/*     */       
/* 258 */       reportConfig.setDistCo(connector.getBoolean("flag_dist_co"));
/*     */ 
/*     */       
/* 261 */       reportConfig.setProductGroupCodeFlag(connector.getBoolean("flag_production_group_code"));
/*     */       
/* 263 */       connector.close();
/*     */       
/* 265 */       return reportConfig;
/*     */     } 
/*     */ 
/*     */     
/*     */     try {
/* 270 */       connector.close();
/* 271 */     } catch (Exception exception) {}
/*     */ 
/*     */     
/* 274 */     return null;
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
/*     */   private Report getNotepadReportConfig(JdbcConnector connector) {
/* 286 */     Report reportConfig = null;
/*     */     
/* 288 */     if (connector != null) {
/*     */       
/* 290 */       reportConfig = new Report(connector.getIntegerField("report_id"));
/*     */ 
/*     */       
/* 293 */       reportConfig.setReportName(connector.getFieldByName("report_name"));
/*     */ 
/*     */       
/* 296 */       reportConfig.setDescription(connector.getFieldByName("description"));
/*     */       
/* 298 */       reportConfig.setFileName(connector.getFieldByName("file_name"));
/*     */     } 
/*     */     
/* 301 */     return reportConfig;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Report saveReportConfig(Report reportConfig, int userID) {
/* 310 */     long timestamp = reportConfig.getLastUpdatedCk();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 316 */     int beginDate = 0;
/* 317 */     if (reportConfig.getBeginDateFlag())
/*     */     {
/* 319 */       beginDate = 1;
/*     */     }
/* 321 */     int endDate = 0;
/* 322 */     if (reportConfig.getEndDateFlag())
/*     */     {
/* 324 */       endDate = 1;
/*     */     }
/* 326 */     int region = 0;
/* 327 */     if (reportConfig.getRegionFlag())
/*     */     {
/* 329 */       region = 1;
/*     */     }
/* 331 */     int family = 0;
/* 332 */     if (reportConfig.getFamilyFlag())
/*     */     {
/* 334 */       family = 1;
/*     */     }
/* 336 */     int environment = 0;
/* 337 */     if (reportConfig.getEnvironmentFlag())
/*     */     {
/* 339 */       environment = 1;
/*     */     }
/* 341 */     int company = 0;
/* 342 */     if (reportConfig.getCompanyFlag())
/*     */     {
/* 344 */       company = 1;
/*     */     }
/* 346 */     int label = 0;
/* 347 */     if (reportConfig.getLabelFlag())
/*     */     {
/* 349 */       label = 1;
/*     */     }
/* 351 */     int contact = 0;
/*     */     
/* 353 */     if (reportConfig.getContactFlag())
/*     */     {
/* 355 */       contact = 1;
/*     */     }
/* 357 */     int complete = 0;
/*     */     
/* 359 */     if (reportConfig.getCompleteKeyTaskFlag())
/*     */     {
/* 361 */       complete = 1;
/*     */     }
/* 363 */     int taskOwner = 0;
/* 364 */     if (reportConfig.getUmlKeyTaskFlag())
/*     */     {
/* 366 */       taskOwner = 1;
/*     */     }
/* 368 */     int umlKeyTask = 0;
/* 369 */     if (reportConfig.getKeyTaskFlag())
/*     */     {
/* 371 */       umlKeyTask = 1;
/*     */     }
/* 373 */     int artist = 0;
/* 374 */     if (reportConfig.getArtistFlag())
/*     */     {
/* 376 */       artist = 1;
/*     */     }
/* 378 */     int releaseType = 0;
/* 379 */     if (reportConfig.getReleaseTypeFlag())
/*     */     {
/* 381 */       releaseType = 1;
/*     */     }
/* 383 */     int status = 0;
/* 384 */     if (reportConfig.getStatusFlag())
/*     */     {
/* 386 */       status = 1;
/*     */     }
/* 388 */     int parentsOnly = 0;
/* 389 */     if (reportConfig.getParentsOnlyFlag())
/*     */     {
/* 391 */       parentsOnly = 1;
/*     */     }
/* 393 */     int beginDueDate = 0;
/* 394 */     if (reportConfig.getBeginDueDateFlag())
/*     */     {
/* 396 */       beginDueDate = 1;
/*     */     }
/* 398 */     int endDueDate = 0;
/* 399 */     if (reportConfig.getEndDueDateFlag())
/*     */     {
/* 401 */       endDueDate = 1;
/*     */     }
/* 403 */     int future2 = 0;
/* 404 */     if (reportConfig.getFuture2Flag())
/*     */     {
/* 406 */       future2 = 1;
/*     */     }
/* 408 */     int future1 = 0;
/*     */     
/* 410 */     if (reportConfig.getFuture1Flag())
/*     */     {
/* 412 */       future1 = 1;
/*     */     }
/* 414 */     int beginEffectiveDate = 0;
/* 415 */     if (reportConfig.getBeginEffectiveDateFlag())
/*     */     {
/* 417 */       beginEffectiveDate = 1;
/*     */     }
/* 419 */     int endEffectiveDate = 0;
/* 420 */     if (reportConfig.getEndEffectiveDateFlag())
/*     */     {
/* 422 */       endEffectiveDate = 1;
/*     */     }
/*     */ 
/*     */     
/* 426 */     int config = 0;
/* 427 */     if (reportConfig.getConfiguration()) {
/* 428 */       config = 1;
/*     */     }
/*     */ 
/*     */     
/* 432 */     int scheduledReleases = 0;
/* 433 */     if (reportConfig.getScheduledReleasesFlag())
/*     */     {
/* 435 */       scheduledReleases = 1;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 440 */     int addsMovesBoth = 0;
/* 441 */     if (reportConfig.getAddsMovesBoth())
/*     */     {
/* 443 */       addsMovesBoth = 1;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 448 */     int physicalProductActivity = 0;
/* 449 */     if (reportConfig.getPhysicalProductActivity()) {
/* 450 */       physicalProductActivity = 1;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 455 */     int distCo = reportConfig.getDistCo() ? 1 : 0;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 460 */     int subconfig = 0;
/* 461 */     if (reportConfig.getSubconfigFlag())
/*     */     {
/* 463 */       subconfig = 1;
/*     */     }
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
/* 475 */     String query = "sp_sav_Report_Config " + 
/* 476 */       reportConfig.getReportID() + "," + 
/* 477 */       reportConfig.getType() + ",'" + 
/* 478 */       MilestoneHelper.escapeSingleQuotes(reportConfig.getReportName()) + "','" + 
/* 479 */       reportConfig.getDescription() + "','" + 
/* 480 */       MilestoneHelper.escapeSingleQuotes(reportConfig.getPath()) + "','" + 
/* 481 */       MilestoneHelper.escapeSingleQuotes(reportConfig.getFileName()) + "','" + 
/* 482 */       MilestoneHelper.escapeSingleQuotes(reportConfig.getSubName()) + "'," + 
/* 483 */       reportConfig.getFormat() + "," + 
/* 484 */       reportConfig.getReportOwner() + "," + 
/*     */       
/* 486 */       beginDate + "," + 
/* 487 */       endDate + "," + 
/* 488 */       region + "," + 
/* 489 */       family + "," + 
/* 490 */       environment + "," + 
/* 491 */       company + "," + 
/* 492 */       label + "," + 
/* 493 */       contact + "," + 
/* 494 */       complete + "," + 
/* 495 */       taskOwner + "," + 
/* 496 */       umlKeyTask + "," + 
/* 497 */       artist + "," + 
/* 498 */       releaseType + "," + 
/* 499 */       status + "," + 
/* 500 */       parentsOnly + "," + 
/* 501 */       beginDueDate + "," + 
/* 502 */       endDueDate + "," + 
/* 503 */       beginEffectiveDate + "," + 
/* 504 */       endEffectiveDate + "," + 
/* 505 */       future2 + "," + 
/* 506 */       future1 + ",'" + 
/* 507 */       reportConfig.getReportStatus() + "'," + 
/* 508 */       reportConfig.getLastUpdatingUser() + "," + 
/* 509 */       timestamp + "," + 
/* 510 */       config + "," + 
/* 511 */       scheduledReleases + "," + 
/* 512 */       addsMovesBoth + "," + 
/* 513 */       subconfig + "," + 
/* 514 */       reportConfig.getProductType() + "," + 
/* 515 */       physicalProductActivity + "," + 
/* 516 */       distCo;
/*     */ 
/*     */     
/* 519 */     JdbcConnector connector = MilestoneHelper.getConnector(query);
/* 520 */     connector.runQuery();
/*     */ 
/*     */     
/* 523 */     if (connector.getIntegerField("ReturnId") > 0) {
/* 524 */       reportConfig.setReportID(connector.getIntegerField("ReturnId"));
/*     */     }
/* 526 */     connector.close();
/*     */     
/* 528 */     String timestampQuery = "Select last_updated_ck, last_updated_on from vi_Report_Config where report_id = " + reportConfig.getReportID();
/*     */     
/* 530 */     JdbcConnector connectorTimestamp = MilestoneHelper.getConnector(timestampQuery);
/* 531 */     connectorTimestamp.runQuery();
/* 532 */     if (connectorTimestamp.more()) {
/*     */       
/* 534 */       reportConfig.setLastUpdatedCk(Long.parseLong(connectorTimestamp.getField("last_updated_ck"), 16));
/* 535 */       reportConfig.setLastUpdatedDate(MilestoneHelper.getDatabaseDate(connectorTimestamp.getField("last_updated_on")));
/*     */     } 
/*     */     
/* 538 */     connectorTimestamp.close();
/* 539 */     return reportConfig;
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
/*     */   
/*     */   public void deleteReportConfig(Report reportConfig, int userID) {
/* 552 */     String query = "sp_del_Report_Config " + 
/* 553 */       reportConfig.getReportID();
/*     */     
/* 555 */     JdbcConnector connector1 = MilestoneHelper.getConnector(query);
/* 556 */     connector1.runQuery();
/* 557 */     connector1.close();
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
/*     */   public void setNotepadQuery(Context context, int UserId, Notepad notepad) {
/* 569 */     if (notepad != null) {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 574 */       String reportNameSearch = context.getParameter("ReportNameSearch");
/* 575 */       String fileNameSearch = context.getParameter("FileNameSearch");
/*     */ 
/*     */       
/* 578 */       String reportConfigQuery = "SELECT DISTINCT report_id, report_name, file_name, description FROM vi_Report_Config";
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 583 */       if (MilestoneHelper.isStringNotEmpty(reportNameSearch)) {
/* 584 */         reportConfigQuery = String.valueOf(reportConfigQuery) + MilestoneHelper.addQueryParams(reportConfigQuery, " report_name " + MilestoneHelper.setWildCardsEscapeSingleQuotes(reportNameSearch));
/*     */       }
/* 586 */       if (MilestoneHelper.isStringNotEmpty(fileNameSearch)) {
/* 587 */         reportConfigQuery = String.valueOf(reportConfigQuery) + MilestoneHelper.addQueryParams(reportConfigQuery, " file_name " + MilestoneHelper.setWildCardsEscapeSingleQuotes(fileNameSearch));
/*     */       }
/* 589 */       String order = " ORDER BY report_name";
/*     */       
/* 591 */       notepad.setSearchQuery(reportConfigQuery);
/* 592 */       notepad.setOrderBy(order);
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
/*     */ 
/*     */   
/*     */   public Vector getReportConfigNotepadList(Notepad notepad) {
/* 606 */     String reportConfigQuery = "";
/*     */     
/* 608 */     if (notepad != null && !notepad.getSearchQuery().equals("")) {
/*     */       
/* 610 */       reportConfigQuery = notepad.getSearchQuery();
/* 611 */       reportConfigQuery = String.valueOf(reportConfigQuery) + notepad.getOrderBy();
/*     */     
/*     */     }
/*     */     else {
/*     */       
/* 616 */       reportConfigQuery = "SELECT DISTINCT report_id, report_name, file_name, description, report_status, report_owner FROM vi_Report_Config ORDER BY report_name";
/*     */     } 
/*     */     
/* 619 */     Report reportConfig = null;
/* 620 */     Vector precache = new Vector();
/* 621 */     JdbcConnector connector = MilestoneHelper.getConnector(reportConfigQuery);
/* 622 */     connector.runQuery();
/*     */     
/* 624 */     while (connector.more()) {
/*     */       
/* 626 */       reportConfig = getNotepadReportConfig(connector);
/*     */       
/* 628 */       precache.addElement(reportConfig);
/* 629 */       reportConfig = null;
/* 630 */       connector.next();
/*     */     } 
/*     */     
/* 633 */     connector.close();
/*     */     
/* 635 */     return precache;
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
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector getReportNotepadList(Notepad notepad, Context context) {
/* 650 */     String reportQuery = "";
/*     */ 
/*     */     
/* 653 */     Vector environments = MilestoneHelper.getUserEnvironments(context);
/* 654 */     String familyWhere = "";
/*     */     
/* 656 */     familyWhere = String.valueOf(familyWhere) + " AND ( report_owner IS NULL OR  report_owner = 0 OR report_owner = -1 ";
/*     */     
/* 658 */     for (int i = 0; i < environments.size(); i++) {
/*     */       
/* 660 */       Environment environment = (Environment)environments.get(i);
/* 661 */       familyWhere = String.valueOf(familyWhere) + " OR report_owner = " + environment.getParent().getStructureID();
/*     */     } 
/*     */     
/* 664 */     familyWhere = String.valueOf(familyWhere) + " )";
/*     */ 
/*     */ 
/*     */     
/* 668 */     if (notepad != null && !notepad.getSearchQuery().equals("")) {
/*     */       
/* 670 */       reportQuery = String.valueOf(notepad.getSearchQuery()) + familyWhere;
/* 671 */       reportQuery = String.valueOf(reportQuery) + notepad.getOrderBy();
/*     */     
/*     */     }
/*     */     else {
/*     */       
/* 676 */       reportQuery = "SELECT DISTINCT report_id, report_name, file_name, description, report_status, report_owner FROM vi_Report_Config where report_status = 'ACTIVE'" + familyWhere + " ORDER By Description";
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 681 */     Report reportConfig = null;
/* 682 */     Vector precache = new Vector();
/*     */     
/* 684 */     JdbcConnector connector = MilestoneHelper.getConnector(reportQuery);
/* 685 */     connector.runQuery();
/*     */     
/* 687 */     while (connector.more()) {
/*     */ 
/*     */       
/* 690 */       reportConfig = getNotepadReportConfig(connector);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 696 */       int owner = connector.getInt("report_owner", 0);
/*     */       
/* 698 */       if (owner == 12) {
/* 699 */         reportConfig.order = owner;
/*     */       }
/*     */ 
/*     */       
/* 703 */       precache.addElement(reportConfig);
/* 704 */       reportConfig = null;
/* 705 */       connector.next();
/*     */     } 
/*     */     
/* 708 */     connector.close();
/*     */ 
/*     */     
/* 711 */     Collections.sort(precache);
/*     */ 
/*     */     
/* 714 */     return precache;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isTimestampValid(Report reportConfig) {
/* 723 */     if (reportConfig != null) {
/*     */       
/* 725 */       String timestampQuery = "Select last_updated_ck, last_updated_on from vi_Report_Config where report_id = " + reportConfig.getReportID();
/*     */       
/* 727 */       JdbcConnector connectorTimestamp = MilestoneHelper.getConnector(timestampQuery);
/* 728 */       connectorTimestamp.runQuery();
/* 729 */       if (connectorTimestamp.more())
/*     */       {
/* 731 */         if (reportConfig.getLastUpdatedCk() != Long.parseLong(connectorTimestamp.getField("last_updated_ck", ""), 16)) {
/*     */           
/* 733 */           connectorTimestamp.close();
/* 734 */           return false;
/*     */         } 
/*     */       }
/* 737 */       connectorTimestamp.close();
/* 738 */       return true;
/*     */     } 
/* 740 */     return false;
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
/*     */   public static Vector getLabelContacts() {
/* 752 */     labelUsers = new Vector();
/*     */ 
/*     */     
/* 755 */     String query = "SELECT DISTINCT vi_All_User.full_Name as name, vi_All_User.User_Id FROM vi_All_User WITH (NOLOCK), vi_User_Company WITH (NOLOCK) WHERE (vi_All_User.User_ID = vi_User_Company.User_ID) AND (menu_access LIKE '2%') AND (vi_All_User.employed_by IS NOT NULL) AND (vi_All_User.employed_by <> 12) AND (vi_All_User.employed_by <> 0) AND (vi_All_user.employed_by <> -1) AND (vi_All_User.employed_by <> 417) AND exists (select top 1 'x' from dbo.release_header rh WITH (NOLOCK) where rh.contact_id = vi_All_User.[user_id]) ORDER BY Name;";
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
/* 768 */     JdbcConnector connector = MilestoneHelper.getConnector(query);
/* 769 */     connector.runQuery();
/*     */     
/* 771 */     while (connector.more()) {
/*     */       
/* 773 */       User labelUser = UserManager.getInstance().getUser(connector.getIntegerField("user_id"));
/* 774 */       labelUsers.add(labelUser);
/* 775 */       connector.next();
/*     */     } 
/*     */     
/* 778 */     connector.close();
/*     */     
/* 780 */     return labelUsers;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Vector getATLASProductionGroupCodes() {
/* 791 */     productionGroupCodes = new Vector();
/*     */     
/* 793 */     String query = "SELECT id, name FROM [atlas].[archimedes].[dbo].[tblProductionGroupCode] ORDER BY name";
/*     */ 
/*     */ 
/*     */     
/* 797 */     JdbcConnector connector = MilestoneHelper.getConnector(query);
/* 798 */     connector.runQuery();
/*     */     
/* 800 */     while (connector.more()) {
/*     */       
/* 802 */       ProductionGroupCode productionGroupCode = new ProductionGroupCode();
/*     */       
/* 804 */       productionGroupCode.setId(connector.getIntegerField("id"));
/* 805 */       productionGroupCode.setName(connector.getField("name"));
/*     */       
/* 807 */       productionGroupCodes.addElement(productionGroupCode);
/* 808 */       productionGroupCode = null;
/*     */       
/* 810 */       connector.next();
/*     */     } 
/*     */     
/* 813 */     connector.close();
/*     */     
/* 815 */     return productionGroupCodes;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Vector getUmlContacts() {
/* 826 */     labelUsers = new Vector();
/*     */     
/* 828 */     String query = "SELECT DISTINCT vi_User.full_Name as name, vi_User.User_Id FROM vi_User, vi_User_Company WHERE (vi_User.User_ID = vi_User_Company.User_ID) AND (vi_User.employed_by IS NOT NULL) AND (vi_User.employed_by = 417) AND (vi_User.employed_by <> 0) AND (vi_user.employed_by <> -1) ORDER BY Name;";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 838 */     JdbcConnector connector = MilestoneHelper.getConnector(query);
/* 839 */     connector.runQuery();
/*     */     
/* 841 */     while (connector.more()) {
/*     */       
/* 843 */       User labelUser = UserManager.getInstance().getUser(connector.getIntegerField("user_id"));
/* 844 */       labelUsers.add(labelUser);
/* 845 */       connector.next();
/*     */     } 
/*     */     
/* 848 */     connector.close();
/*     */     
/* 850 */     return labelUsers;
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
/*     */   public boolean isDuplicate(Report reportConfig) {
/* 862 */     boolean isDuplicate = false;
/*     */     
/* 864 */     if (reportConfig != null) {
/*     */       
/* 866 */       String query = "SELECT * FROM vi_report_config  WHERE report_name = '" + 
/* 867 */         MilestoneHelper.escapeSingleQuotes(reportConfig.getReportName()) + "'";
/*     */       
/* 869 */       JdbcConnector connector = MilestoneHelper.getConnector(query);
/* 870 */       connector.runQuery();
/*     */       
/* 872 */       if (connector.more()) {
/* 873 */         isDuplicate = true;
/*     */       }
/* 875 */       connector.close();
/*     */     } 
/*     */ 
/*     */     
/* 879 */     return isDuplicate;
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
/*     */   
/*     */   public static Vector getTemplateNames(Context context) {
/* 892 */     Vector templates = new Vector();
/*     */     
/* 894 */     String query = "SELECT * FROM vi_Template_Header a LEFT JOIN vi_structure b \ton a.[owner] = b.[structure_id] ";
/*     */ 
/*     */     
/* 897 */     query = String.valueOf(query) + MilestoneHelper.getOwnerCompanyWhereClause(context);
/* 898 */     query = String.valueOf(query) + " ORDER BY a.owner, a.name ";
/*     */ 
/*     */ 
/*     */     
/* 902 */     JdbcConnector connector = MilestoneHelper.getConnector(query);
/* 903 */     connector.runQuery();
/*     */     
/* 905 */     while (connector.more()) {
/*     */ 
/*     */       
/* 908 */       Template template = new Template();
/*     */ 
/*     */       
/* 911 */       template.setTemplateID(connector.getIntegerField("template_id"));
/*     */ 
/*     */       
/* 914 */       template.setTemplateName(connector.getField("name", ""));
/*     */       
/* 916 */       templates.add(template);
/*     */       
/* 918 */       template = null;
/*     */       
/* 920 */       connector.next();
/*     */     } 
/*     */     
/* 923 */     connector.close();
/*     */ 
/*     */ 
/*     */     
/* 927 */     return templates;
/*     */   }
/*     */ }


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\ReportConfigManager.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */