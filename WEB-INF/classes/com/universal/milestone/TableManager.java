/*     */ package WEB-INF.classes.com.universal.milestone;
/*     */ 
/*     */ import com.techempower.ComponentLog;
/*     */ import com.techempower.EnhancedProperties;
/*     */ import com.techempower.gemini.Context;
/*     */ import com.techempower.gemini.GeminiApplication;
/*     */ import com.universal.milestone.JdbcConnector;
/*     */ import com.universal.milestone.LookupObject;
/*     */ import com.universal.milestone.MilestoneConstants;
/*     */ import com.universal.milestone.MilestoneHelper;
/*     */ import com.universal.milestone.Notepad;
/*     */ import com.universal.milestone.PrefixCode;
/*     */ import com.universal.milestone.Table;
/*     */ import com.universal.milestone.TableManager;
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
/*     */ public class TableManager
/*     */   implements MilestoneConstants
/*     */ {
/*     */   public static final String COMPONENT_CODE = "mTab";
/*  43 */   protected static TableManager TableManager = null;
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
/*  55 */   public static void configure(EnhancedProperties props, GeminiApplication application) { log = application.getLog("mTab"); }
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
/*     */   public static TableManager getInstance() {
/*  73 */     if (TableManager == null)
/*     */     {
/*  75 */       TableManager = new TableManager();
/*     */     }
/*  77 */     return TableManager;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Table getTable(int id, boolean getTimestamp) {
/*  85 */     String TableQuery = "SELECT [field_id], [field_name], [description], [max_length], [add_indicator], [subdetail_indicator],[last_updated_on], [last_updated_by], [last_updated_ck] FROM vi_Lookup_Header where field_id = " + 
/*     */       
/*  87 */       id + 
/*  88 */       " ORDER BY [field_name];";
/*     */ 
/*     */     
/*  91 */     JdbcConnector connector = MilestoneHelper.getConnector(TableQuery);
/*  92 */     connector.runQuery();
/*     */     
/*  94 */     String detail = "";
/*  95 */     String subdetail = "";
/*     */     
/*  97 */     Table table = null;
/*  98 */     if (connector.more()) {
/*     */       
/* 100 */       detail = "SELECT [field_id], [value], [description], [last_updated_on], [last_updated_by], [last_updated_ck], [inactive] FROM vi_Lookup_Detail  WHERE [field_id] = " + 
/*     */ 
/*     */         
/* 103 */         connector.getIntegerField("field_id") + 
/* 104 */         " order by [value];";
/*     */       
/* 106 */       table = new Table();
/*     */       
/* 108 */       table.setId(connector.getIntegerField("field_id"));
/* 109 */       table.setAbbreviation(connector.getField("field_name"));
/* 110 */       table.setName(connector.getField("description"));
/*     */       
/* 112 */       JdbcConnector detailConnector = MilestoneHelper.getConnector(detail);
/* 113 */       detailConnector.runQuery();
/*     */       
/* 115 */       if (detailConnector.more()) {
/*     */         
/* 117 */         subdetail = "SELECT [field_id], [det_value], [sub_value], [description], [last_updated_on], [last_updated_by], [last_updated_ck], [inactive] FROM vi_Lookup_SubDetail  WHERE field_id = " + 
/*     */ 
/*     */           
/* 120 */           connector.getIntegerField("field_id") + 
/* 121 */           " AND det_value = '" + MilestoneHelper.escapeSingleQuotes(detailConnector.getField("value")) + "'" + 
/* 122 */           ";";
/*     */         
/* 124 */         JdbcConnector subdetailConnector = MilestoneHelper.getConnector(subdetail);
/* 125 */         subdetailConnector.runQuery();
/*     */         
/* 127 */         LookupObject detailObj = new LookupObject(detailConnector.getField("value"), detailConnector.getField("description"));
/* 128 */         detailObj.setId(detailConnector.getIntegerField("field_id"));
/*     */         
/* 130 */         PrefixCode subdetailObj = new PrefixCode();
/* 131 */         if (subdetailConnector.more()) {
/*     */           
/* 133 */           subdetailObj.setAbbreviation(subdetailConnector.getField("sub_value"));
/* 134 */           subdetailObj.setName(subdetailConnector.getField("description"));
/* 135 */           subdetailObj.setId(subdetailConnector.getIntegerField("field_id"));
/* 136 */           subdetailObj.setDetValue(subdetailConnector.getField("det_value"));
/*     */ 
/*     */ 
/*     */           
/* 140 */           String lastDateStringSubDetail = subdetailConnector.getFieldByName("last_updated_on");
/* 141 */           if (lastDateStringSubDetail != null) {
/* 142 */             subdetailObj.setLastUpdateDate(MilestoneHelper.getDatabaseDate(lastDateStringSubDetail));
/*     */           }
/*     */           
/* 145 */           subdetailObj.setLastUpdatingUser(subdetailConnector.getIntegerField("last_updated_by"));
/*     */ 
/*     */           
/* 148 */           long lastUpdatedLongSubDetail = Long.parseLong(subdetailConnector.getField("last_updated_ck"), 16);
/* 149 */           subdetailObj.setLastUpdatedCk(lastUpdatedLongSubDetail);
/*     */ 
/*     */           
/* 152 */           subdetailObj.setInactive(subdetailConnector.getBoolean("inactive"));
/*     */         } 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 158 */         String lastDateStringDetail = detailConnector.getFieldByName("last_updated_on");
/* 159 */         if (lastDateStringDetail != null) {
/* 160 */           detailObj.setLastUpdateDate(MilestoneHelper.getDatabaseDate(lastDateStringDetail));
/*     */         }
/*     */         
/* 163 */         detailObj.setLastUpdatingUser(detailConnector.getIntegerField("last_updated_by"));
/*     */ 
/*     */         
/* 166 */         long lastUpdatedLongDetail = Long.parseLong(detailConnector.getField("last_updated_ck"), 16);
/* 167 */         detailObj.setLastUpdatedCk(lastUpdatedLongDetail);
/*     */ 
/*     */ 
/*     */         
/* 171 */         detailObj.setInactive(detailConnector.getBoolean("inactive"));
/*     */         
/* 173 */         table.setDetail(detailObj);
/* 174 */         table.setSubDetail(subdetailObj);
/*     */         
/* 176 */         detailConnector.close();
/* 177 */         subdetailConnector.close();
/*     */       } 
/*     */ 
/*     */       
/* 181 */       String lastDateString = connector.getFieldByName("last_updated_on");
/* 182 */       if (lastDateString != null) {
/* 183 */         table.setLastUpdateDate(MilestoneHelper.getDatabaseDate(lastDateString));
/*     */       }
/*     */       
/* 186 */       table.setLastUpdatingUser(connector.getIntegerField("last_updated_by"));
/*     */ 
/*     */       
/* 189 */       if (getTimestamp) {
/*     */         
/* 191 */         long lastUpdatedLong = Long.parseLong(connector.getField("last_updated_ck"), 16);
/* 192 */         table.setLastUpdatedCk(lastUpdatedLong);
/*     */       } 
/*     */     } 
/*     */     
/* 196 */     connector.close();
/* 197 */     return table;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Table getNotepadTable(JdbcConnector connector) {
/* 206 */     Table table = null;
/*     */     
/* 208 */     if (connector != null) {
/*     */       
/* 210 */       table = new Table();
/*     */       
/* 212 */       table.setId(connector.getIntegerField("field_id"));
/* 213 */       table.setAbbreviation(connector.getFieldByName("field_name"));
/* 214 */       table.setName(connector.getFieldByName("description"));
/*     */     } 
/* 216 */     return table;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Table getNotepadTableDetail(JdbcConnector connector) {
/* 225 */     Table table = null;
/*     */     
/* 227 */     if (connector != null) {
/*     */       
/* 229 */       table = new Table();
/* 230 */       table.setId(connector.getIntegerField("field_id"));
/* 231 */       table.setName(connector.getField("description"));
/* 232 */       LookupObject detailObj = new LookupObject(connector.getFieldByName("value"), connector.getFieldByName("description"));
/* 233 */       detailObj.setId(connector.getIntegerField("field_id"));
/* 234 */       table.setDetail(detailObj);
/*     */     } 
/*     */     
/* 237 */     return table;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Table getTableDetail(int id, String value, boolean getTimestamp) {
/* 246 */     String detail = "SELECT [field_id], [value], [description], [last_updated_on], [last_updated_by], [last_updated_ck], [inactive], [productType], isnull([isDigitalEquivalent],0) as isDigitalEquivalent FROM vi_Lookup_Detail  WHERE [field_id] = " + 
/*     */ 
/*     */       
/* 249 */       id + 
/* 250 */       " AND value= '" + MilestoneHelper.escapeSingleQuotes(value) + "'";
/*     */     
/* 252 */     JdbcConnector detailConnector = MilestoneHelper.getConnector(detail);
/* 253 */     detailConnector.runQuery();
/*     */     
/* 255 */     String subdetail = "";
/*     */ 
/*     */ 
/*     */     
/* 259 */     Table table = null;
/* 260 */     if (detailConnector.more()) {
/*     */       
/* 262 */       subdetail = "SELECT [field_id], [det_value], [sub_value], [description], [last_updated_on], [last_updated_by], [last_updated_ck], [inactive], [productType] FROM vi_Lookup_SubDetail  WHERE field_id = " + 
/*     */ 
/*     */         
/* 265 */         id + 
/* 266 */         " AND det_value = '" + MilestoneHelper.escapeSingleQuotes(value) + "'";
/*     */ 
/*     */       
/* 269 */       JdbcConnector subdetailConnector = MilestoneHelper.getConnector(subdetail);
/* 270 */       subdetailConnector.runQuery();
/*     */       
/* 272 */       table = new Table();
/*     */       
/* 274 */       table.setId(detailConnector.getIntegerField("field_id"));
/*     */ 
/*     */ 
/*     */       
/* 278 */       table.setName(detailConnector.getField("description"));
/*     */       
/* 280 */       LookupObject detailObj = new LookupObject(detailConnector.getField("value"), 
/* 281 */           detailConnector.getField("description"));
/* 282 */       detailObj.setId(detailConnector.getIntegerField("field_id"));
/*     */       
/* 284 */       PrefixCode subdetailObj = new PrefixCode();
/*     */ 
/*     */       
/* 287 */       if (subdetailConnector.more()) {
/*     */ 
/*     */         
/* 290 */         subdetailObj.setAbbreviation(subdetailConnector.getField("sub_value"));
/* 291 */         subdetailObj.setId(subdetailConnector.getIntegerField("field_id"));
/* 292 */         subdetailObj.setName(subdetailConnector.getField("description"));
/* 293 */         subdetailObj.setDetValue(subdetailConnector.getField("det_value"));
/*     */ 
/*     */ 
/*     */         
/* 297 */         String lastDateStringSubDetail = subdetailConnector.getFieldByName("last_updated_on");
/* 298 */         if (lastDateStringSubDetail != null) {
/* 299 */           subdetailObj.setLastUpdateDate(MilestoneHelper.getDatabaseDate(lastDateStringSubDetail));
/*     */         }
/*     */         
/* 302 */         subdetailObj.setLastUpdatingUser(subdetailConnector.getIntegerField("last_updated_by"));
/*     */ 
/*     */         
/* 305 */         long lastUpdatedLongSubDetail = Long.parseLong(subdetailConnector.getField("last_updated_ck"), 16);
/* 306 */         subdetailObj.setLastUpdatedCk(lastUpdatedLongSubDetail);
/*     */ 
/*     */ 
/*     */         
/* 310 */         subdetailObj.setInactive(subdetailConnector.getBoolean("inactive"));
/*     */ 
/*     */         
/* 313 */         subdetailObj.setProdType(subdetailConnector.getIntegerField("productType"));
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 319 */       String lastDateStringDetail = detailConnector.getField("last_updated_on", "");
/* 320 */       if (lastDateStringDetail.length() > 0) {
/* 321 */         detailObj.setLastUpdateDate(MilestoneHelper.getDatabaseDate(lastDateStringDetail));
/*     */       }
/*     */       
/* 324 */       detailObj.setLastUpdatingUser(detailConnector.getIntegerField("last_updated_by"));
/*     */ 
/*     */       
/* 327 */       if (getTimestamp) {
/*     */         
/* 329 */         long lastUpdatedLongDetail = Long.parseLong(detailConnector.getField("last_updated_ck"), 16);
/* 330 */         detailObj.setLastUpdatedCk(lastUpdatedLongDetail);
/*     */       } 
/*     */ 
/*     */       
/* 334 */       detailObj.setInactive(detailConnector.getBoolean("inactive"));
/*     */ 
/*     */       
/* 337 */       detailObj.setProdType(detailConnector.getIntegerField("productType"));
/*     */ 
/*     */       
/* 340 */       detailObj.setIsDigitalEquivalent(detailConnector.getBoolean("isDigitalEquivalent"));
/*     */       
/* 342 */       table.setDetail(detailObj);
/* 343 */       table.setSubDetail(subdetailObj);
/*     */ 
/*     */       
/* 346 */       String lastDateString = detailConnector.getField("last_updated_on", "");
/* 347 */       if (lastDateString.length() > 0) {
/* 348 */         table.setLastUpdateDate(MilestoneHelper.getDatabaseDate(lastDateString));
/*     */       }
/*     */       
/* 351 */       table.setLastUpdatingUser(detailConnector.getIntegerField("last_updated_by"));
/*     */ 
/*     */       
/* 354 */       long lastUpdatedLong = Long.parseLong(detailConnector.getField("last_updated_ck"), 16);
/* 355 */       table.setLastUpdatedCk(lastUpdatedLong);
/*     */       
/* 357 */       subdetailConnector.close();
/*     */     } 
/*     */     
/* 360 */     detailConnector.close();
/* 361 */     return table;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getAssocConfig(int id, String value) {
/* 371 */     String assocConfig = "";
/* 372 */     String subdetail = "SELECT [det_value] FROM vi_Lookup_SubDetail  WHERE field_id = 3 AND sub_value = '" + 
/*     */ 
/*     */       
/* 375 */       MilestoneHelper.escapeSingleQuotes(value) + "'";
/*     */ 
/*     */ 
/*     */     
/* 379 */     JdbcConnector subdetailConnector = MilestoneHelper.getConnector(subdetail);
/* 380 */     subdetailConnector.runQuery();
/*     */ 
/*     */     
/* 383 */     if (subdetailConnector.more()) {
/* 384 */       assocConfig = subdetailConnector.getField("det_value");
/*     */     }
/*     */     
/* 387 */     subdetailConnector.close();
/* 388 */     return assocConfig;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getProdType(String value) {
/* 397 */     int productType = -1;
/* 398 */     String subdetail = "select productType from vi_Lookup_Detail where field_id = 3 and value in (  SELECT det_value FROM vi_Lookup_SubDetail WHERE [field_id] = 3 AND sub_value = '" + 
/*     */ 
/*     */       
/* 401 */       MilestoneHelper.escapeSingleQuotes(value) + "')";
/*     */ 
/*     */ 
/*     */     
/* 405 */     JdbcConnector subdetailConnector = MilestoneHelper.getConnector(subdetail);
/* 406 */     subdetailConnector.runQuery();
/*     */ 
/*     */     
/* 409 */     if (subdetailConnector.more()) {
/* 410 */       productType = subdetailConnector.getIntegerField("productType");
/*     */     }
/*     */     
/* 413 */     subdetailConnector.close();
/* 414 */     return productType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isDuplicate(int id, String value) {
/* 424 */     String detail = "SELECT [field_id], [value] FROM vi_Lookup_Detail  WHERE [field_id] = " + 
/*     */       
/* 426 */       id + 
/* 427 */       " AND value= '" + MilestoneHelper.escapeSingleQuotes(value) + "'";
/*     */ 
/*     */ 
/*     */     
/* 431 */     JdbcConnector detailConnector = MilestoneHelper.getConnector(detail);
/* 432 */     detailConnector.runQuery();
/*     */     
/* 434 */     boolean isDuplicate = false;
/*     */     
/* 436 */     if (detailConnector.more()) {
/* 437 */       isDuplicate = true;
/*     */     }
/* 439 */     detailConnector.close();
/*     */     
/* 441 */     return isDuplicate;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void deleteTable(Table table, int userID) {
/* 450 */     String query = "sp_del_Lookup_SubDetail " + 
/* 451 */       table.getDetail().getId() + "," + 
/* 452 */       "'" + MilestoneHelper.escapeSingleQuotes(table.getDetail().getAbbreviation()) + "'," + 
/* 453 */       "'" + MilestoneHelper.escapeSingleQuotes(table.getSubDetail().getAbbreviation()) + "'";
/*     */     
/* 455 */     log.debug("Table subdetail id = " + table.getDetail().getId());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 461 */     log.debug("Delete table subdetail query:\n" + query);
/* 462 */     JdbcConnector connector = MilestoneHelper.getConnector(query);
/* 463 */     connector.setQuery(query);
/* 464 */     connector.runQuery();
/* 465 */     connector.close();
/*     */     
/* 467 */     query = "sp_del_Lookup_Detail " + 
/* 468 */       table.getId() + "," + 
/* 469 */       " '" + MilestoneHelper.escapeSingleQuotes(table.getDetail().getAbbreviation()) + "'";
/*     */     
/* 471 */     log.debug("Delete table detail query:\n" + query);
/* 472 */     connector.setQuery(query);
/* 473 */     connector.runQuery();
/* 474 */     connector.close();
/*     */ 
/*     */ 
/*     */     
/* 478 */     if (table.getId() == 4) {
/* 479 */       updateAssocConfig(table, 3, "", true);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Table saveTableDetail(Table table, int userID, boolean isThereSubdetail, String assocConfig) {
/* 490 */     LookupObject detail = table.getDetail();
/* 491 */     PrefixCode subdetail = table.getSubDetail();
/* 492 */     long timestamp = detail.getLastUpdatedCk();
/*     */     
/* 494 */     String query = "sp_upd_Lookup_Detail " + 
/* 495 */       table.getId() + "," + 
/* 496 */       "'" + MilestoneHelper.escapeSingleQuotes(detail.getAbbreviation()) + "'," + 
/* 497 */       "'" + MilestoneHelper.escapeSingleQuotes(detail.getName()) + "'," + 
/* 498 */       userID + "," + 
/* 499 */       timestamp + "," + (
/* 500 */       detail.getInactive() ? 1 : 0) + "," + 
/* 501 */       detail.getProdType() + "," + (
/* 502 */       detail.getIsDigitalEquivalent() ? 1 : 0);
/*     */     
/* 504 */     log.debug("Table save query:\n" + query);
/* 505 */     JdbcConnector connector = MilestoneHelper.getConnector(query);
/* 506 */     connector.setQuery(query);
/* 507 */     connector.runQuery();
/* 508 */     connector.close();
/*     */     
/* 510 */     if (isThereSubdetail) {
/*     */       
/* 512 */       long subtimestamp = subdetail.getLastUpdatedCk();
/*     */       
/* 514 */       String subquery = "sp_upd_Lookup_SubDetail " + 
/* 515 */         table.getId() + "," + 
/* 516 */         "'" + MilestoneHelper.escapeSingleQuotes(subdetail.getDetValue()) + "'," + 
/* 517 */         "'" + MilestoneHelper.escapeSingleQuotes(subdetail.getAbbreviation()) + "'," + 
/* 518 */         "'" + MilestoneHelper.escapeSingleQuotes(subdetail.getName()) + "'," + 
/* 519 */         userID + "," + 
/* 520 */         subtimestamp + "," + (
/* 521 */         detail.getInactive() ? 1 : 0) + "," + 
/* 522 */         detail.getProdType();
/*     */       
/* 524 */       log.debug("Table save sub-query:\n" + subquery);
/* 525 */       JdbcConnector subconnector = MilestoneHelper.getConnector(subquery);
/* 526 */       subconnector.setQuery(subquery);
/* 527 */       subconnector.runQuery();
/* 528 */       subconnector.close();
/*     */     
/*     */     }
/*     */     else {
/*     */       
/* 533 */       long subtimestamp = subdetail.getLastUpdatedCk();
/* 534 */       String subquery = "sp_ins_Lookup_SubDetail " + 
/* 535 */         table.getId() + "," + 
/* 536 */         "'" + MilestoneHelper.escapeSingleQuotes(subdetail.getDetValue()) + "'," + 
/* 537 */         "'" + MilestoneHelper.escapeSingleQuotes(subdetail.getAbbreviation()) + "'," + 
/* 538 */         "'" + MilestoneHelper.escapeSingleQuotes(subdetail.getName()) + "'," + 
/* 539 */         userID + "," + (
/* 540 */         detail.getInactive() ? 1 : 0) + "," + 
/* 541 */         detail.getProdType();
/*     */       
/* 543 */       log.debug("Table insert sub-query:\n" + subquery);
/* 544 */       JdbcConnector subconnector = MilestoneHelper.getConnector(subquery);
/* 545 */       subconnector.setQuery(subquery);
/* 546 */       subconnector.runQuery();
/* 547 */       subconnector.close();
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 552 */     if (table.getId() == 4) {
/* 553 */       updateAssocConfig(table, 3, assocConfig, false);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 559 */     return getInstance().getTableDetail(table.getId(), detail.getAbbreviation(), true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Table insertTableDetail(Table table, int userID, String assocConfig) {
/* 569 */     LookupObject detail = table.getDetail();
/* 570 */     PrefixCode subdetail = table.getSubDetail();
/* 571 */     long timestamp = detail.getLastUpdatedCk();
/*     */     
/* 573 */     if (table.getId() == 33) {
/*     */       
/* 575 */       String queryTaskId = "SELECT MAX(CONVERT(int, value)) AS newId FROM Lookup_Detail WHERE field_id = 33";
/*     */       
/* 577 */       JdbcConnector connectorTaskId = MilestoneHelper.getConnector(queryTaskId);
/* 578 */       connectorTaskId.runQuery();
/*     */       
/* 580 */       int newTaskID = connectorTaskId.getInt("newId", -1) + 1;
/*     */       
/* 582 */       connectorTaskId.close();
/*     */       
/* 584 */       detail.setAbbreviation(String.valueOf(newTaskID));
/* 585 */       subdetail.setAbbreviation(String.valueOf(newTaskID));
/*     */     } 
/*     */     
/* 588 */     String query = "sp_ins_Lookup_Detail " + 
/* 589 */       table.getId() + "," + 
/* 590 */       "'" + MilestoneHelper.escapeSingleQuotes(detail.getAbbreviation()) + "'," + 
/* 591 */       "'" + MilestoneHelper.escapeSingleQuotes(detail.getName()) + "'," + 
/* 592 */       userID + "," + (
/* 593 */       detail.getInactive() ? 1 : 0) + "," + 
/* 594 */       detail.getProdType() + "," + (
/* 595 */       detail.getIsDigitalEquivalent() ? 1 : 0);
/*     */     
/* 597 */     JdbcConnector connector = MilestoneHelper.getConnector(query);
/* 598 */     connector.setQuery(query);
/* 599 */     connector.runQuery();
/* 600 */     connector.close();
/*     */ 
/*     */     
/* 603 */     if (table.getId() != 3) {
/*     */       
/* 605 */       long subtimestamp = subdetail.getLastUpdatedCk();
/* 606 */       String subquery = "sp_ins_Lookup_SubDetail " + 
/* 607 */         table.getId() + "," + 
/* 608 */         "'" + MilestoneHelper.escapeSingleQuotes(subdetail.getDetValue()) + 
/* 609 */         "'," + 
/* 610 */         "'" + MilestoneHelper.escapeSingleQuotes(subdetail.getAbbreviation()) + 
/* 611 */         "'," + 
/* 612 */         "'" + MilestoneHelper.escapeSingleQuotes(subdetail.getName()) + 
/* 613 */         "'," + 
/* 614 */         userID + "," + (
/* 615 */         detail.getInactive() ? 1 : 0) + "," + 
/* 616 */         detail.getProdType();
/*     */       
/* 618 */       JdbcConnector subconnector = MilestoneHelper.getConnector(subquery);
/* 619 */       subconnector.setQuery(subquery);
/* 620 */       subconnector.runQuery();
/* 621 */       subconnector.close();
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 626 */     if (table.getId() == 4) {
/* 627 */       updateAssocConfig(table, 3, assocConfig, false);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 632 */     return getInstance().getTableDetail(table.getId(), detail.getAbbreviation(), true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void deleteTableDetail(Table table, int userID) {
/* 642 */     String query = "sp_del_Lookup_SubDetail " + 
/* 643 */       table.getSubDetail().getId() + "," + 
/* 644 */       "'" + MilestoneHelper.escapeSingleQuotes(table.getSubDetail().getDetValue()) + "'," + 
/* 645 */       "'" + MilestoneHelper.escapeSingleQuotes(table.getSubDetail().getAbbreviation()) + "'";
/*     */     
/* 647 */     log.debug("Table subdetail id = " + table.getSubDetail().getId());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 653 */     log.debug("Delete table subdetail query:\n" + query);
/* 654 */     JdbcConnector connector = MilestoneHelper.getConnector(query);
/* 655 */     connector.setQuery(query);
/* 656 */     connector.runQuery();
/* 657 */     connector.close();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 665 */     query = "sp_del_Lookup_Detail " + 
/* 666 */       table.getId() + "," + 
/* 667 */       " '" + MilestoneHelper.escapeSingleQuotes(table.getDetail().getAbbreviation()) + "'";
/*     */     
/* 669 */     log.debug("Delete table detail query:\n" + query);
/* 670 */     connector.setQuery(query);
/* 671 */     connector.runQuery();
/* 672 */     connector.close();
/*     */ 
/*     */ 
/*     */     
/* 676 */     if (table.getId() == 4) {
/* 677 */       updateAssocConfig(table, 3, "", true);
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
/*     */   public void updateAssocConfig(Table table, int configId, String configStr, boolean deleteOnly) {
/* 690 */     String query = "DELETE FROM Lookup_SubDetail  WHERE field_Id = " + 
/* 691 */       configId + " AND sub_value = " + 
/* 692 */       "'" + MilestoneHelper.escapeSingleQuotes(table.getDetail().getAbbreviation()) + "'";
/*     */     
/* 694 */     log.debug("Table subdetail id = " + configId);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 701 */     log.debug("Delete table subdetail query:\n" + query);
/* 702 */     JdbcConnector connector = MilestoneHelper.getConnector(query);
/* 703 */     connector.setQuery(query);
/* 704 */     connector.runQuery();
/* 705 */     connector.close();
/*     */ 
/*     */     
/* 708 */     if (!deleteOnly) {
/* 709 */       query = "sp_ins_Lookup_SubDetail " + 
/* 710 */         configId + "," + 
/* 711 */         "'" + configStr + "'," + 
/* 712 */         "'" + MilestoneHelper.escapeSingleQuotes(table.getDetail().getAbbreviation()) + "'" + "," + 
/* 713 */         "'" + MilestoneHelper.escapeSingleQuotes(table.getDetail().getName()) + "'" + "," + (
/* 714 */         table.getDetail().getInactive() ? 1 : 0);
/* 715 */       log.debug("Inserted table subdetail query:\n" + query);
/* 716 */       connector = MilestoneHelper.getConnector(query);
/* 717 */       connector.setQuery(query);
/* 718 */       connector.runQuery();
/* 719 */       connector.close();
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
/*     */   public void setNotepadQuery(Context context, int UserId, Notepad notepad) {
/* 733 */     if (notepad != null) {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 738 */       String descriptionSearch = context.getParameter("DescriptionSearch");
/* 739 */       String params = "";
/*     */ 
/*     */       
/* 742 */       String tableQuery = "SELECT [field_id], [field_name], [description], [max_length], [add_indicator], [subdetail_indicator]  FROM vi_Lookup_Header";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 749 */       if (MilestoneHelper.isStringNotEmpty(descriptionSearch)) {
/* 750 */         tableQuery = String.valueOf(tableQuery) + 
/* 751 */           MilestoneHelper.addQueryParams(tableQuery, " description " + MilestoneHelper.setWildCardsEscapeSingleQuotes(descriptionSearch));
/*     */       }
/* 753 */       String order = " ORDER BY [field_name]";
/*     */       
/* 755 */       log.debug("Notepad THTHTHTH tableQuery:\n" + tableQuery);
/*     */       
/* 757 */       notepad.setSearchQuery(tableQuery);
/* 758 */       notepad.setOrderBy(order);
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
/*     */   public void setDetailNotepadQuery(Context context, int fieldId, Notepad notepad) {
/* 770 */     if (notepad != null) {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 775 */       String descriptionSearch = context.getParameter("DescriptionSearch");
/*     */       
/* 777 */       String isDigitalEquivalentSearch = context.getParameter("isDigitalEquivalentSearch");
/*     */ 
/*     */       
/* 780 */       String detailQuery = "SELECT [field_id], [value], [description], [last_updated_on], [last_updated_by], [last_updated_ck], [inactive] FROM vi_Lookup_Detail  WHERE [field_id] = " + 
/*     */ 
/*     */         
/* 783 */         fieldId;
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 788 */       if (MilestoneHelper.isStringNotEmpty(descriptionSearch)) {
/* 789 */         detailQuery = String.valueOf(detailQuery) + " AND description " + MilestoneHelper.setWildCardsEscapeSingleQuotes(descriptionSearch);
/*     */       }
/* 791 */       log.log("isDigitalEquivalentSearch value =  " + isDigitalEquivalentSearch);
/* 792 */       if (isDigitalEquivalentSearch != null && isDigitalEquivalentSearch.length() > 0) {
/* 793 */         detailQuery = String.valueOf(detailQuery) + " AND isDigitalEquivalent = 1";
/*     */       }
/*     */       
/* 796 */       String order = " ORDER BY [value]";
/*     */       
/* 798 */       notepad.setSearchQuery(detailQuery);
/* 799 */       notepad.setOrderBy(order);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public Vector getTableNotepadList(int UserId, Notepad notepad) {
/* 805 */     String TableQuery = "";
/*     */     
/* 807 */     if (notepad != null && !notepad.getSearchQuery().equals("")) {
/*     */       
/* 809 */       TableQuery = notepad.getSearchQuery();
/* 810 */       TableQuery = String.valueOf(TableQuery) + notepad.getOrderBy();
/*     */     
/*     */     }
/*     */     else {
/*     */       
/* 815 */       TableQuery = "SELECT [field_id], [field_name], [description], [max_length], [add_indicator], [subdetail_indicator]  FROM vi_Lookup_Header ORDER BY [field_name];";
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 821 */     Table table = null;
/* 822 */     Vector precache = new Vector();
/*     */     
/* 824 */     JdbcConnector connector = MilestoneHelper.getConnector(TableQuery);
/* 825 */     connector.runQuery();
/*     */     
/* 827 */     while (connector.more()) {
/*     */ 
/*     */       
/* 830 */       table = getNotepadTable(connector);
/*     */       
/* 832 */       precache.addElement(table);
/* 833 */       table = null;
/* 834 */       connector.next();
/*     */     } 
/* 836 */     connector.close();
/*     */     
/* 838 */     return precache;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector getTableDetailNotepadList(int fieldId, Notepad notepad) {
/*     */     String detail;
/* 850 */     if (notepad != null && !notepad.getSearchQuery().equals("")) {
/*     */       
/* 852 */       log.debug("notepad DDDDDDDDDDDDDD using notepad query");
/* 853 */       detail = notepad.getSearchQuery();
/* 854 */       detail = String.valueOf(detail) + notepad.getOrderBy();
/*     */     
/*     */     }
/*     */     else {
/*     */       
/* 859 */       log.debug("notepad DDDDDDDDDDDDDD using default query");
/* 860 */       detail = "SELECT [field_id], [value], [description], [last_updated_on], [last_updated_by], [last_updated_ck], [inactive] FROM vi_Lookup_Detail  WHERE [field_id] = " + 
/*     */ 
/*     */         
/* 863 */         fieldId + 
/* 864 */         " order by [value];";
/*     */     } 
/*     */ 
/*     */     
/*     */     try {
/* 869 */       log.debug("Query from notepad was: " + notepad.getSearchQuery());
/*     */     }
/* 871 */     catch (Exception exception) {}
/*     */     
/* 873 */     log.debug("Get table detail query:\n" + detail);
/*     */     
/* 875 */     JdbcConnector detailConnector = MilestoneHelper.getConnector(detail);
/* 876 */     detailConnector.runQuery();
/*     */     
/* 878 */     Table table = null;
/* 879 */     Vector precache = new Vector();
/*     */     
/* 881 */     while (detailConnector.more()) {
/*     */ 
/*     */       
/* 884 */       table = getNotepadTableDetail(detailConnector);
/* 885 */       precache.addElement(table);
/* 886 */       table = null;
/* 887 */       detailConnector.next();
/*     */     } 
/* 889 */     detailConnector.close();
/*     */     
/* 891 */     return precache;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isTimestampValid(Table table) {
/* 900 */     if (table != null) {
/*     */       
/* 902 */       LookupObject detailObj = table.getDetail();
/*     */       
/* 904 */       String timestampQuery = "SELECT [last_updated_ck]  FROM vi_Lookup_Detail  WHERE [field_id] = " + 
/*     */         
/* 906 */         table.getId() + 
/* 907 */         " AND value= '" + MilestoneHelper.escapeSingleQuotes(detailObj.getAbbreviation()) + "'";
/*     */ 
/*     */ 
/*     */       
/* 911 */       JdbcConnector connectorTimestamp = MilestoneHelper.getConnector(timestampQuery);
/* 912 */       connectorTimestamp.runQuery();
/* 913 */       if (connectorTimestamp.more())
/*     */       {
/* 915 */         if (detailObj.getLastUpdatedCk() != Long.parseLong(connectorTimestamp.getField("last_updated_ck", ""), 16)) {
/*     */           
/* 917 */           connectorTimestamp.close();
/* 918 */           return false;
/*     */         } 
/*     */       }
/* 921 */       connectorTimestamp.close();
/* 922 */       return true;
/*     */     } 
/* 924 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isSubDetailTimestampValid(Table table) {
/* 933 */     if (table != null) {
/*     */       
/* 935 */       LookupObject detailObj = table.getDetail();
/* 936 */       PrefixCode subdetail = table.getSubDetail();
/*     */       
/* 938 */       String timestampQuery = "SELECT [last_updated_ck]  FROM vi_Lookup_SubDetail  WHERE [field_id] = " + 
/*     */         
/* 940 */         table.getId() + 
/* 941 */         " AND det_value = '" + MilestoneHelper.escapeSingleQuotes(detailObj.getAbbreviation()) + "'";
/*     */ 
/*     */ 
/*     */       
/* 945 */       JdbcConnector connectorTimestamp = MilestoneHelper.getConnector(timestampQuery);
/* 946 */       connectorTimestamp.runQuery();
/* 947 */       if (connectorTimestamp.more())
/*     */       {
/* 949 */         if (subdetail.getLastUpdatedCk() != Long.parseLong(connectorTimestamp.getField("last_updated_ck", ""), 16))
/* 950 */           return false; 
/*     */       }
/* 952 */       connectorTimestamp.close();
/* 953 */       return true;
/*     */     } 
/* 955 */     return false;
/*     */   }
/*     */ }


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\TableManager.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */