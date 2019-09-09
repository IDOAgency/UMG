/*     */ package WEB-INF.classes.com.universal.milestone;
/*     */ 
/*     */ import com.techempower.ComponentLog;
/*     */ import com.techempower.EnhancedProperties;
/*     */ import com.techempower.gemini.Context;
/*     */ import com.techempower.gemini.GeminiApplication;
/*     */ import com.universal.milestone.Cache;
/*     */ import com.universal.milestone.Company;
/*     */ import com.universal.milestone.CompanyAcl;
/*     */ import com.universal.milestone.Day;
/*     */ import com.universal.milestone.Family;
/*     */ import com.universal.milestone.JdbcConnector;
/*     */ import com.universal.milestone.MilestoneConstants;
/*     */ import com.universal.milestone.MilestoneHelper;
/*     */ import com.universal.milestone.Notepad;
/*     */ import com.universal.milestone.NotepadSortOrder;
/*     */ import com.universal.milestone.NotepadSortOrderTemplate;
/*     */ import com.universal.milestone.ProductCategory;
/*     */ import com.universal.milestone.ReleaseType;
/*     */ import com.universal.milestone.Task;
/*     */ import com.universal.milestone.TaskManager;
/*     */ import com.universal.milestone.Template;
/*     */ import com.universal.milestone.TemplateManager;
/*     */ import com.universal.milestone.User;
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
/*     */ public class TemplateManager
/*     */   implements MilestoneConstants
/*     */ {
/*     */   public static final String COMPONENT_CODE = "mTmp";
/*     */   public static final String DEFAULT_ORDER = " ORDER BY a.[name]";
/*     */   public static final String DEFAULT_TASK_ORDER = " ORDER BY name";
/*  44 */   protected static TemplateManager templateManager = null;
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
/*  56 */   public static void configure(EnhancedProperties props, GeminiApplication application) { log = application.getLog("mTmp"); }
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
/*     */   public static TemplateManager getInstance() {
/*  78 */     if (templateManager == null)
/*     */     {
/*  80 */       templateManager = new TemplateManager();
/*     */     }
/*  82 */     return templateManager;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Template getTemplate(int id, boolean getTimestamp) {
/*  90 */     String templateQuery = "SELECT * FROM vi_Template_Header WHERE template_Id = " + 
/*     */       
/*  92 */       id + 
/*  93 */       ";";
/*     */     
/*  95 */     Template template = null;
/*     */     
/*  97 */     JdbcConnector connector = MilestoneHelper.getConnector(templateQuery);
/*  98 */     connector.runQuery();
/*     */     
/* 100 */     if (connector.more()) {
/*     */       
/* 102 */       template = new Template();
/*     */ 
/*     */       
/* 105 */       template.setTemplateID(connector.getIntegerField("template_id"));
/*     */ 
/*     */       
/* 108 */       template.setTemplateName(connector.getField("name", ""));
/*     */ 
/*     */       
/* 111 */       String comments = connector.getField("comments", "");
/* 112 */       if (comments.length() > 0) {
/* 113 */         template.setComments(comments);
/*     */       }
/*     */       
/* 116 */       if (connector.getField("product_line", "").length() > 0) {
/* 117 */         template.setProductCategory((ProductCategory)MilestoneHelper.getLookupObject(connector.getField("product_line", ""), Cache.getProductCategories()));
/*     */       }
/*     */ 
/*     */       
/* 121 */       if (connector.getIntegerField("owner") > 0) {
/* 122 */         template.setOwner((Family)MilestoneHelper.getStructureObject(connector.getIntegerField("owner")));
/*     */       }
/*     */ 
/*     */       
/* 126 */       if (connector.getField("release_type", "").length() > 0) {
/* 127 */         template.setReleaseType((ReleaseType)MilestoneHelper.getLookupObject(connector.getField("release_type", ""), Cache.getReleaseTypes()));
/*     */       }
/*     */       
/* 130 */       if (connector.getField("configuration", "").length() > 0) {
/* 131 */         template.setSelectionConfig(MilestoneHelper.getSelectionConfigObject(connector.getField("configuration", ""), Cache.getSelectionConfigs()));
/*     */       }
/*     */ 
/*     */       
/* 135 */       if (connector.getField("sub_configuration", "").length() > 0) {
/* 136 */         template.setSelectionSubConfig(MilestoneHelper.getSelectionSubConfigObject(connector.getField("sub_configuration", ""), template.getSelectionConfig()));
/*     */       }
/*     */       
/* 139 */       template.setProdType(connector.getIntegerField("productType"));
/*     */ 
/*     */       
/* 142 */       String tasksQuery = "SELECT * FROM vi_Task WHERE task_id IN (SELECT task_id FROM vi_Template_Detail  WHERE template_id = " + 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 147 */         template.getTemplateID() + ")" + 
/* 148 */         " ORDER BY weeks_to_release DESC, day_of_week ASC, name ASC;";
/*     */       
/* 150 */       Task task = null;
/* 151 */       Vector precache = new Vector();
/*     */       
/* 153 */       JdbcConnector taskConnector = MilestoneHelper.getConnector(tasksQuery);
/* 154 */       taskConnector.runQuery();
/*     */       
/* 156 */       while (taskConnector.more()) {
/*     */         
/* 158 */         if (taskConnector.getIntegerField("task_id") > 0)
/* 159 */           precache.addElement(TaskManager.getInstance().getTask(taskConnector.getIntegerField("task_id"), true)); 
/* 160 */         taskConnector.next();
/*     */       } 
/* 162 */       template.setTasks(precache);
/*     */ 
/*     */       
/* 165 */       String lastDateString = connector.getFieldByName("last_updated_on");
/* 166 */       if (lastDateString != null) {
/* 167 */         template.setLastUpdateDate(MilestoneHelper.getDatabaseDate(lastDateString));
/*     */       }
/*     */       
/* 170 */       template.setLastUpdatingUser(connector.getIntegerField("last_updated_by"));
/*     */ 
/*     */       
/* 173 */       if (getTimestamp) {
/*     */         
/* 175 */         long lastUpdatedLong = Long.parseLong(connector.getField("last_updated_ck"), 16);
/* 176 */         template.setLastUpdatedCk(lastUpdatedLong);
/*     */       } 
/*     */       
/* 179 */       taskConnector.close();
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 184 */     connector.close();
/*     */     
/* 186 */     return template;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Template getNotepadTemplate(JdbcConnector connector) {
/* 195 */     Template template = null;
/*     */     
/* 197 */     if (connector != null) {
/*     */       
/* 199 */       template = new Template();
/*     */ 
/*     */       
/* 202 */       template.setTemplateID(connector.getIntegerField("template_id"));
/*     */ 
/*     */       
/* 205 */       template.setTemplateName(connector.getFieldByName("name"));
/*     */ 
/*     */       
/* 208 */       if (connector.getIntegerField("owner") > 0) {
/* 209 */         template.setOwner((Family)MilestoneHelper.getStructureObject(connector.getIntegerField("owner")));
/*     */       }
/*     */       
/* 212 */       if (connector.getField("configuration", "").length() > 0) {
/* 213 */         template.setSelectionConfig(MilestoneHelper.getSelectionConfigObject(connector.getField("configuration", ""), Cache.getSelectionConfigs()));
/*     */       }
/*     */ 
/*     */       
/* 217 */       if (connector.getField("product_line", "").length() > 0) {
/* 218 */         template.setProductCategory((ProductCategory)MilestoneHelper.getLookupObject(connector.getField("product_line", ""), Cache.getProductCategories()));
/*     */       }
/*     */       
/* 221 */       if (connector.getIntegerField("owner") > 0) {
/* 222 */         template.setOwner((Family)MilestoneHelper.getStructureObject(connector.getIntegerField("owner")));
/*     */       }
/*     */       
/* 225 */       if (connector.getField("release_type", "").length() > 0) {
/* 226 */         template.setReleaseType((ReleaseType)MilestoneHelper.getLookupObject(connector.getField("release_type", ""), Cache.getReleaseTypes()));
/*     */       }
/*     */ 
/*     */       
/* 230 */       if (connector.getField("sub_configuration", "").length() > 0) {
/* 231 */         template.setSelectionSubConfig(MilestoneHelper.getSelectionSubConfigObject(connector.getField("sub_configuration", ""), template.getSelectionConfig()));
/*     */       }
/*     */       
/* 234 */       if (connector.getIntegerField("productType") > 0) {
/* 235 */         template.setProdType(connector.getIntegerField("productType"));
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 243 */     return template;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Template saveTemplate(Template template, int userID) {
/* 251 */     long timestamp = template.getLastUpdatedCk();
/*     */     
/* 253 */     int owner = -1;
/* 254 */     if (template.getOwner() != null) {
/* 255 */       owner = template.getOwner().getStructureID();
/*     */     }
/* 257 */     String product = "";
/* 258 */     if (template.getProductCategory() != null) {
/* 259 */       product = template.getProductCategory().getAbbreviation();
/*     */     }
/* 261 */     String release = "";
/* 262 */     if (template.getReleaseType() != null) {
/* 263 */       release = template.getReleaseType().getAbbreviation();
/*     */     }
/* 265 */     String config = "";
/* 266 */     if (template.getSelectionConfig() != null) {
/* 267 */       config = template.getSelectionConfig().getSelectionConfigurationAbbreviation();
/*     */     }
/* 269 */     String subconfig = "";
/* 270 */     if (template.getSelectionSubConfig() != null) {
/* 271 */       subconfig = template.getSelectionSubConfig().getSelectionSubConfigurationAbbreviation();
/*     */     }
/* 273 */     String tempName = "";
/* 274 */     if (template.getTempateName() != null) {
/* 275 */       tempName = template.getTempateName();
/*     */     }
/* 277 */     String comments = "";
/*     */     
/* 279 */     if (template.getComments() != null) {
/* 280 */       comments = template.getComments();
/*     */     }
/* 282 */     String query = "sp_sav_Template_Header " + 
/* 283 */       template.getTemplateID() + "," + 
/* 284 */       "'" + MilestoneHelper.escapeSingleQuotes(tempName) + "'," + 
/* 285 */       owner + "," + 
/* 286 */       "'" + MilestoneHelper.escapeSingleQuotes(product) + "'," + 
/* 287 */       "'" + MilestoneHelper.escapeSingleQuotes(release) + "'," + 
/* 288 */       "'" + MilestoneHelper.escapeSingleQuotes(config) + "'," + 
/* 289 */       "'" + MilestoneHelper.escapeSingleQuotes(subconfig) + "'," + 
/* 290 */       "'" + MilestoneHelper.escapeSingleQuotes(comments) + "'," + 
/* 291 */       userID + "," + 
/* 292 */       timestamp + "," + 
/* 293 */       template.getProdType();
/*     */     
/* 295 */     JdbcConnector connector = MilestoneHelper.getConnector(query);
/* 296 */     connector.runQuery();
/* 297 */     int newTemplateID = -1;
/*     */ 
/*     */ 
/*     */     
/* 301 */     if (template.getTemplateID() < 0) {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 306 */       newTemplateID = connector.getInt("ReturnID");
/*     */     }
/*     */     else {
/*     */       
/* 310 */       newTemplateID = template.getTemplateID();
/*     */     } 
/*     */ 
/*     */     
/* 314 */     connector.close();
/*     */     
/* 316 */     return getInstance().getTemplate(newTemplateID, true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Template saveTemplateCopiedTemplate(Template template, int userID) {
/* 326 */     long timestamp = template.getLastUpdatedCk();
/*     */     
/* 328 */     int owner = -1;
/* 329 */     if (template.getOwner() != null) {
/* 330 */       owner = template.getOwner().getStructureID();
/*     */     }
/* 332 */     String product = "";
/* 333 */     if (template.getProductCategory() != null) {
/* 334 */       product = template.getProductCategory().getAbbreviation();
/*     */     }
/* 336 */     String release = "";
/* 337 */     if (template.getReleaseType() != null) {
/* 338 */       release = template.getReleaseType().getAbbreviation();
/*     */     }
/* 340 */     String config = "";
/* 341 */     if (template.getSelectionConfig() != null) {
/* 342 */       config = template.getSelectionConfig().getSelectionConfigurationAbbreviation();
/*     */     }
/* 344 */     String subconfig = "";
/* 345 */     if (template.getSelectionSubConfig() != null) {
/* 346 */       subconfig = template.getSelectionSubConfig().getSelectionSubConfigurationAbbreviation();
/*     */     }
/* 348 */     String tempName = "";
/* 349 */     if (template.getTempateName() != null) {
/* 350 */       tempName = template.getTempateName();
/*     */     }
/* 352 */     String comments = "";
/* 353 */     if (template.getComments() != null) {
/* 354 */       comments = template.getComments();
/*     */     }
/* 356 */     String query = "sp_sav_Template_Header " + 
/* 357 */       template.getTemplateID() + "," + 
/* 358 */       "'" + MilestoneHelper.escapeSingleQuotes(tempName) + "'," + 
/* 359 */       owner + "," + 
/* 360 */       "'" + MilestoneHelper.escapeSingleQuotes(product) + "'," + 
/* 361 */       "'" + MilestoneHelper.escapeSingleQuotes(release) + "'," + 
/* 362 */       "'" + MilestoneHelper.escapeSingleQuotes(config) + "'," + 
/* 363 */       "'" + MilestoneHelper.escapeSingleQuotes(subconfig) + "'," + 
/* 364 */       "'" + MilestoneHelper.escapeSingleQuotes(comments) + "'," + 
/* 365 */       userID + "," + 
/* 366 */       timestamp + "," + 
/* 367 */       template.getProdType();
/*     */     
/* 369 */     JdbcConnector connector = MilestoneHelper.getConnector(query);
/* 370 */     connector.setQuery(query);
/* 371 */     connector.runQuery();
/* 372 */     int newTemplateID = -1;
/*     */ 
/*     */ 
/*     */     
/* 376 */     if (template.getTemplateID() < 0)
/*     */     {
/*     */ 
/*     */ 
/*     */       
/* 381 */       newTemplateID = connector.getInt("ReturnID");
/*     */     }
/* 383 */     connector.close();
/*     */     
/* 385 */     if (newTemplateID > 0)
/*     */     {
/* 387 */       if (template.getTasks() != null) {
/*     */         
/* 389 */         Vector tasks = template.getTasks();
/* 390 */         for (int i = 0; i < tasks.size(); i++) {
/*     */           
/* 392 */           Task task = (Task)tasks.elementAt(i);
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 397 */           int sort = 0;
/*     */           
/* 399 */           String queryDetails = "sp_ins_Template_Detail " + 
/* 400 */             newTemplateID + "," + 
/* 401 */             task.getTaskID() + "," + 
/* 402 */             sort + "," + 
/* 403 */             userID;
/*     */           
/* 405 */           JdbcConnector connectorDetail = MilestoneHelper.getConnector(queryDetails);
/* 406 */           connectorDetail.setQuery(queryDetails);
/* 407 */           connectorDetail.runQuery();
/* 408 */           connectorDetail.close();
/*     */         } 
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/* 414 */     return getInstance().getTemplate(newTemplateID, true);
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
/*     */   public void setNotepadQuery(Context context, int UserId, Notepad notepad) {
/* 427 */     log.debug("TEMPLATE MGR SETTING NOTEPAD QUERY FOR SEARCH");
/* 428 */     if (notepad != null) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 437 */       String templateNameSrch = context.getParameter("templateNameSrch");
/* 438 */       String configurationSrch = context.getParameter("configurationSrch");
/* 439 */       String ownerSrch = context.getParameter("ownerSrch");
/* 440 */       String params = "";
/*     */ 
/*     */       
/* 443 */       String productSearch = context.getParameter("ProdTypeSearch");
/*     */ 
/*     */       
/* 446 */       String query = getDefaultSearchQuery(context);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 454 */       NotepadSortOrderTemplate notepadSortOrder = NotepadSortOrder.getNotepadSortOrderFromSession(context).getNotepadSortOrderTemplate();
/* 455 */       notepadSortOrder.setTemplateOrderBy("");
/* 456 */       notepadSortOrder.setTemplateOrderCol("Template");
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 461 */       if (MilestoneHelper.isStringNotEmpty(templateNameSrch)) {
/* 462 */         query = String.valueOf(query) + 
/* 463 */           MilestoneHelper.addQueryParams(query, " a.[name] " + MilestoneHelper.setWildCardsEscapeSingleQuotes(templateNameSrch));
/*     */       }
/* 465 */       if (MilestoneHelper.isStringNotEmpty(configurationSrch) && !configurationSrch.equals("0")) {
/* 466 */         query = String.valueOf(query) + 
/* 467 */           MilestoneHelper.addQueryParams(query, " a.[configuration] = '" + MilestoneHelper.escapeSingleQuotes(configurationSrch) + "'");
/*     */       }
/* 469 */       if (MilestoneHelper.isStringNotEmpty(ownerSrch) && !ownerSrch.equals("0")) {
/* 470 */         query = String.valueOf(query) + 
/* 471 */           MilestoneHelper.addQueryParams(query, " a.[owner] = '" + MilestoneHelper.escapeSingleQuotes(ownerSrch) + "'");
/*     */       }
/* 473 */       if (MilestoneHelper.isStringNotEmpty(productSearch) && !productSearch.equals("2")) {
/* 474 */         query = String.valueOf(query) + 
/* 475 */           MilestoneHelper.addQueryParams(query, " a.[productType] = " + MilestoneHelper.escapeSingleQuotes(productSearch));
/*     */       }
/* 477 */       String order = " ORDER BY a.[name]";
/*     */       
/* 479 */       log.debug("Notepad TTTTTTTT template query:\n" + query);
/*     */       
/* 481 */       notepad.setSearchQuery(query);
/* 482 */       notepad.setOrderBy(order);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public Vector getTemplateNotepadList(Context context, Notepad notepad) {
/* 488 */     String query = "";
/* 489 */     Template template = null;
/* 490 */     Vector precache = new Vector();
/*     */     
/* 492 */     if (notepad != null && !notepad.getSearchQuery().equals("")) {
/*     */       
/* 494 */       query = notepad.getSearchQuery();
/* 495 */       query = String.valueOf(query) + notepad.getOrderBy();
/* 496 */       log.debug("notepad TTTTTTTT getting preset query :\n" + query);
/*     */     }
/*     */     else {
/*     */       
/* 500 */       log.debug("notepad TTTTTTTT doing default query");
/* 501 */       query = getDefaultSearchQuery(context);
/* 502 */       query = String.valueOf(query) + " ORDER BY a.[name]";
/*     */     } 
/*     */     
/* 505 */     log.debug("notepad TTTTTTTT template query being used :\n" + query);
/* 506 */     JdbcConnector connector = MilestoneHelper.getConnector(query);
/* 507 */     connector.runQuery();
/*     */     
/* 509 */     while (connector.more()) {
/*     */       
/* 511 */       template = getNotepadTemplate(connector);
/* 512 */       precache.addElement(template);
/* 513 */       template = null;
/* 514 */       connector.next();
/*     */     } 
/* 516 */     connector.close();
/*     */     
/* 518 */     return precache;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void deleteTemplate(Template template, int userID) {
/* 526 */     String query = "sp_del_Templates " + 
/* 527 */       template.getTemplateID();
/*     */     
/* 529 */     JdbcConnector connector = MilestoneHelper.getConnector(query);
/* 530 */     connector.runQuery();
/* 531 */     connector.close();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void deleteTemplateDetail(Template template, Task task, int userID) {
/* 540 */     String query = "sp_del_Template_Detail " + 
/* 541 */       template.getTemplateID() + "," + 
/* 542 */       task.getTaskID();
/*     */     
/* 544 */     JdbcConnector connector = MilestoneHelper.getConnector(query);
/* 545 */     connector.runQuery();
/* 546 */     connector.close();
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
/*     */   public void addTask(Template template, Task task, int userID) {
/* 559 */     int sortId = 1;
/*     */ 
/*     */     
/* 562 */     String query = "sp_ins_Template_Detail " + 
/* 563 */       template.getTemplateID() + "," + 
/* 564 */       task.getTaskID() + "," + 
/* 565 */       sortId + "," + 
/* 566 */       userID;
/*     */     
/* 568 */     JdbcConnector connector = MilestoneHelper.getConnector(query);
/* 569 */     connector.runQuery();
/* 570 */     connector.close();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getDefaultSearchQuery(Context context) {
/* 580 */     query = "SELECT * FROM vi_Template_Header ";
/*     */ 
/*     */     
/* 583 */     query = "SELECT * FROM vi_Template_Header a LEFT JOIN vi_structure b \ton a.[owner] = b.[structure_id] ";
/*     */ 
/*     */     
/* 586 */     return String.valueOf(query) + MilestoneHelper.getOwnerCompanyWhereClause(context);
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
/*     */   public String getDefaultTaskSearchQuery(int templateId) {
/* 598 */     query = "";
/*     */     
/* 600 */     return "SELECT * FROM vi_Task WHERE task_id  NOT IN (SELECT task_id FROM vi_Template_Detail  WHERE template_id = " + 
/*     */       
/* 602 */       templateId + ")";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector getTemplateTaskNotepadList(int templateId, int userId, Notepad notepad, Context context) {
/* 612 */     String query = "";
/*     */     
/* 614 */     if (notepad != null && !notepad.getSearchQuery().equals("")) {
/*     */       
/* 616 */       query = notepad.getSearchQuery();
/* 617 */       query = String.valueOf(query) + notepad.getOrderBy();
/*     */     }
/*     */     else {
/*     */       
/* 621 */       query = String.valueOf(getDefaultTaskSearchQuery(templateId)) + " ORDER BY name";
/*     */     } 
/* 623 */     log.debug("++++++++++++++++++++ template query " + query);
/* 624 */     Task task = null;
/* 625 */     Vector precache = new Vector();
/* 626 */     Vector companies = MilestoneHelper.getUserCompanies(context);
/*     */     
/* 628 */     JdbcConnector connector = MilestoneHelper.getConnector(query);
/* 629 */     connector.runQuery();
/*     */     
/* 631 */     while (connector.more()) {
/*     */ 
/*     */       
/* 634 */       task = getTemplateTaskNotepadObject(connector);
/*     */       
/* 636 */       if (task != null) {
/*     */         
/* 638 */         Company company = null;
/* 639 */         if (companies != null)
/*     */         {
/* 641 */           for (int j = 0; j < companies.size(); j++) {
/*     */             
/* 643 */             company = (Company)companies.get(j);
/* 644 */             if (company != null && company.getParentEnvironment().getParentFamily() != null && task.getOwner() != null)
/*     */             {
/* 646 */               if (company.getParentEnvironment().getParentFamily().getStructureID() == task.getOwner().getStructureID()) {
/*     */                 
/* 648 */                 if (task.getActiveFlag())
/*     */                 {
/* 650 */                   precache.addElement(task);
/*     */                 }
/*     */                 break;
/*     */               } 
/*     */             }
/*     */           } 
/*     */         }
/*     */       } 
/* 658 */       task = null;
/* 659 */       connector.next();
/*     */     } 
/* 661 */     connector.close();
/*     */     
/* 663 */     return precache;
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
/*     */   public Task getTemplateTaskNotepadObject(JdbcConnector connector) {
/* 676 */     Task task = null;
/*     */     
/* 678 */     if (connector != null) {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 683 */       task = new Task();
/* 684 */       task.setTaskID(connector.getIntegerField("task_id"));
/*     */ 
/*     */       
/* 687 */       task.setTaskName(connector.getField("name", ""));
/*     */ 
/*     */       
/* 690 */       task.setWeeksToRelease(connector.getIntegerField("weeks_to_release"));
/*     */ 
/*     */       
/* 693 */       task.setDayOfTheWeek(new Day(connector.getIntegerField("day_of_week")));
/*     */ 
/*     */       
/* 696 */       task.setDepartment(connector.getField("department", ""));
/*     */ 
/*     */       
/* 699 */       task.setOwner((Family)MilestoneHelper.getStructureObject(connector.getIntegerField("owner")));
/*     */ 
/*     */       
/* 702 */       task.setTaskAbbreviation(connector.getIntegerField("abbrev_id"));
/*     */ 
/*     */       
/* 705 */       task.setIsKeyTask(connector.getBoolean("key_task_indicator"));
/*     */ 
/*     */       
/* 708 */       task.setActiveFlag(connector.getBoolean("active_flag"));
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 713 */     return task;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getTaskEditAccess(User user, int familyID) {
/* 721 */     Vector companyAcls = user.getAcl().getCompanyAcl();
/* 722 */     int access = 0;
/* 723 */     for (int i = 0; i < companyAcls.size(); i++) {
/*     */ 
/*     */       
/* 726 */       CompanyAcl companyAcl = (CompanyAcl)companyAcls.get(i);
/*     */       
/* 728 */       Company company = null;
/*     */       
/* 730 */       if (companyAcl != null) {
/*     */         
/* 732 */         company = (Company)MilestoneHelper.getStructureObject(companyAcl
/* 733 */             .getCompanyId());
/*     */         
/* 735 */         int familyIdCk = -1;
/* 736 */         if (company != null) {
/* 737 */           familyIdCk = company.getParentEnvironment().getParentFamily()
/* 738 */             .getStructureID();
/*     */         }
/* 740 */         if (familyIdCk == familyID)
/*     */         {
/* 742 */           if (companyAcl.getAccessTemplate() > access) {
/* 743 */             access = companyAcl.getAccessTemplate();
/*     */           }
/*     */         }
/*     */       } 
/*     */     } 
/* 748 */     return access;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTaskNotepadQuery(Context context, Notepad notepad) {
/* 759 */     if (notepad != null) {
/*     */       
/* 761 */       int templateId = ((Template)context.getSessionValue("Template")).getTemplateID();
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 766 */       String taskNameSearch = context.getParameter("TaskNameSearch");
/* 767 */       String keyTaskSearch = context.getParameter("KeyTaskSearch");
/* 768 */       String taskOwnerSearch = context.getParameter("TaskOwnerSearch");
/* 769 */       String taskDepartmentSearch = context.getParameter("TaskDepartmentSearch");
/*     */ 
/*     */       
/* 772 */       int keyTask = -1;
/*     */       
/* 774 */       if (keyTaskSearch != null && keyTaskSearch.equalsIgnoreCase("yes")) {
/*     */         
/* 776 */         keyTask = 1;
/*     */       }
/* 778 */       else if (keyTaskSearch != null && keyTaskSearch.equalsIgnoreCase("no")) {
/*     */         
/* 780 */         keyTask = 0;
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 787 */       String query = "SELECT * FROM vi_Task WHERE task_id  not in (select task_id from vi_Template_Detail  where template_id = " + 
/*     */         
/* 789 */         templateId + ")";
/*     */ 
/*     */ 
/*     */       
/* 793 */       if (keyTask > -1) {
/* 794 */         query = String.valueOf(query) + " AND key_task_indicator = " + keyTask;
/*     */       }
/* 796 */       if (MilestoneHelper.isStringNotEmpty(taskNameSearch)) {
/* 797 */         query = String.valueOf(query) + " AND name " + MilestoneHelper.setWildCardsEscapeSingleQuotes(taskNameSearch);
/*     */       }
/* 799 */       if (MilestoneHelper.isStringNotEmpty(taskOwnerSearch) && !taskOwnerSearch.equals("0")) {
/* 800 */         query = String.valueOf(query) + " AND owner = '" + taskOwnerSearch + "'";
/*     */       }
/* 802 */       if (MilestoneHelper.isStringNotEmpty(taskDepartmentSearch) && !taskDepartmentSearch.equals("0") && !taskDepartmentSearch.equals("-1")) {
/* 803 */         query = String.valueOf(query) + " AND department like '%" + taskDepartmentSearch + "%'";
/*     */       }
/*     */ 
/*     */       
/* 807 */       String order = " ORDER BY name";
/*     */       
/* 809 */       notepad.setSearchQuery(query);
/* 810 */       notepad.setOrderBy(order);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isTimestampValid(Template template) {
/* 820 */     if (template != null) {
/*     */       
/* 822 */       String timestampQuery = "SELECT last_updated_ck  FROM vi_Template_Header WHERE template_Id = " + 
/*     */         
/* 824 */         template.getTemplateID() + 
/* 825 */         ";";
/*     */       
/* 827 */       JdbcConnector connectorTimestamp = MilestoneHelper.getConnector(timestampQuery);
/* 828 */       connectorTimestamp.runQuery();
/* 829 */       if (connectorTimestamp.more())
/*     */       {
/* 831 */         if (template.getLastUpdatedCk() != Long.parseLong(connectorTimestamp.getField("last_updated_ck", ""), 16)) {
/*     */           
/* 833 */           connectorTimestamp.close();
/* 834 */           return false;
/*     */         } 
/*     */       }
/* 837 */       connectorTimestamp.close();
/* 838 */       return true;
/*     */     } 
/* 840 */     return false;
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
/*     */   public boolean isDuplicate(Template template) {
/* 852 */     boolean isDuplicate = false;
/*     */     
/* 854 */     if (template != null) {
/*     */       
/* 856 */       String query = "SELECT * FROM vi_template_header WHERE  name = '" + 
/* 857 */         MilestoneHelper.escapeSingleQuotes(template.getTempateName()) + "'" + 
/* 858 */         " AND template_id <> " + template.getTemplateID();
/*     */       
/* 860 */       log.debug("TTTTTTTTTTTTTTTT duplicate query = " + query);
/*     */       
/* 862 */       JdbcConnector connector = MilestoneHelper.getConnector(query);
/* 863 */       connector.runQuery();
/*     */       
/* 865 */       if (connector.more()) {
/* 866 */         isDuplicate = true;
/*     */       }
/* 868 */       connector.close();
/*     */     } 
/*     */ 
/*     */     
/* 872 */     return isDuplicate;
/*     */   }
/*     */ }


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\TemplateManager.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */