/*     */ package WEB-INF.classes.com.universal.milestone;
/*     */ 
/*     */ import com.techempower.ComponentLog;
/*     */ import com.techempower.EnhancedProperties;
/*     */ import com.techempower.gemini.GeminiApplication;
/*     */ import com.universal.milestone.Cache;
/*     */ import com.universal.milestone.Company;
/*     */ import com.universal.milestone.CorporateObjectSearchObj;
/*     */ import com.universal.milestone.Division;
/*     */ import com.universal.milestone.Environment;
/*     */ import com.universal.milestone.Family;
/*     */ import com.universal.milestone.JdbcConnector;
/*     */ import com.universal.milestone.Label;
/*     */ import com.universal.milestone.LabelManager;
/*     */ import com.universal.milestone.MilestoneConstants;
/*     */ import com.universal.milestone.MilestoneHelper;
/*     */ import com.universal.milestone.MilestoneHelper_2;
/*     */ import com.universal.milestone.Notepad;
/*     */ import com.universal.milestone.projectSearchSvcClient;
/*     */ import java.rmi.RemoteException;
/*     */ import java.util.Hashtable;
/*     */ import java.util.Vector;
/*     */ import net.umusic.milestone.alps.DcLabelData;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class LabelManager
/*     */   implements MilestoneConstants
/*     */ {
/*     */   public static final String COMPONENT_CODE = "mLab";
/*  58 */   protected static LabelManager labelManager = null;
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
/*     */   
/*  71 */   public static void configure(EnhancedProperties props, GeminiApplication application) { log = application.getLog("mLab"); }
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
/*     */   public static LabelManager getInstance() {
/*  89 */     if (labelManager == null)
/*     */     {
/*  91 */       labelManager = new LabelManager();
/*     */     }
/*  93 */     return labelManager;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Label getLabel(int id) {
/* 101 */     Label label = null;
/* 102 */     label = (Label)MilestoneHelper.getStructureObject(id);
/*     */     
/* 104 */     if (label != null)
/*     */     {
/* 106 */       label.setParentDivision((Division)MilestoneHelper.getStructureObject(label.getParentID()));
/*     */     }
/*     */     
/* 109 */     String query = "SELECT last_updated_by, last_updated_on, last_updated_ck  FROM vi_Structure  WHERE type = 4 AND structure_id = " + 
/*     */ 
/*     */       
/* 112 */       id + 
/* 113 */       " ORDER BY name ";
/*     */     
/* 115 */     JdbcConnector connector = MilestoneHelper.getConnector(query);
/* 116 */     connector.runQuery();
/*     */     
/* 118 */     if (connector.more()) {
/*     */ 
/*     */       
/* 121 */       String lastDateString = connector.getFieldByName("last_updated_on");
/* 122 */       if (lastDateString != null) {
/* 123 */         label.setLastUpdateDate(MilestoneHelper.getDatabaseDate(lastDateString));
/*     */       }
/*     */       
/* 126 */       label.setLastUpdatingUser(connector.getIntegerField("last_updated_by"));
/*     */ 
/*     */       
/* 129 */       long lastUpdatedLong = Long.parseLong(connector.getField("last_updated_ck"), 16);
/* 130 */       label.setLastUpdatedCk(lastUpdatedLong);
/*     */     } 
/*     */     
/* 133 */     connector.close();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 138 */     getArchimedes(label, Boolean.valueOf(false));
/*     */     
/* 140 */     return label;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Label saveLabel(Label label, int userID) {
/* 148 */     long timestamp = label.getLastUpdatedCk();
/* 149 */     String activeFlag = "0";
/* 150 */     boolean boolVal = label.getActive();
/* 151 */     if (!boolVal) {
/* 152 */       activeFlag = "1";
/*     */     }
/*     */     
/* 155 */     String query = "sp_sav_Structure " + 
/* 156 */       label.getStructureID() + "," + 
/* 157 */       label.getParentDivision().getStructureID() + "," + 
/* 158 */       activeFlag + "," + 
/* 159 */       '\004' + "," + 
/* 160 */       "'" + MilestoneHelper.escapeSingleQuotes(label.getName()) + "'," + 
/* 161 */       "'" + MilestoneHelper.escapeSingleQuotes(label.getStructureAbbreviation()) + "'," + 
/*     */       
/* 163 */       label.getDistribution() + "," + 
/* 164 */       "0" + "," + 
/* 165 */       userID + "," + 
/* 166 */       timestamp;
/*     */     
/* 168 */     JdbcConnector connector = MilestoneHelper.getConnector(query);
/* 169 */     connector.runQuery();
/* 170 */     connector.close();
/*     */ 
/*     */     
/* 173 */     label.flushAudits(userID);
/*     */ 
/*     */ 
/*     */     
/* 177 */     if (label.getArchimedesId() == 0) {
/* 178 */       updateDistributionCompany(label);
/*     */     }
/*     */     
/* 181 */     String timestampQuery = "SELECT last_updated_ck, last_updated_on FROM vi_structure WHERE structure_id = " + 
/*     */       
/* 183 */       label.getStructureID() + 
/* 184 */       ";";
/*     */     
/* 186 */     JdbcConnector connectorTimestamp = MilestoneHelper.getConnector(timestampQuery);
/* 187 */     connectorTimestamp.runQuery();
/* 188 */     if (connectorTimestamp.more()) {
/*     */       
/* 190 */       label.setLastUpdatedCk(Long.parseLong(connectorTimestamp.getField("last_updated_ck"), 16));
/* 191 */       label.setLastUpdateDate(MilestoneHelper.getDatabaseDate(connectorTimestamp.getField("last_updated_on")));
/*     */     } 
/* 193 */     connectorTimestamp.close();
/*     */     
/* 195 */     return label;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Label saveNewLabel(Label label, int userID) {
/* 203 */     int parentDivision = -1;
/* 204 */     if (label.getParentDivision() != null) {
/* 205 */       parentDivision = label.getParentDivision().getStructureID();
/*     */     }
/*     */     
/* 208 */     String activeFlag = "0";
/* 209 */     boolean boolVal = label.getActive();
/* 210 */     if (!boolVal) {
/* 211 */       activeFlag = "1";
/*     */     }
/*     */     
/* 214 */     String query = "sp_sav_Structure " + 
/* 215 */       label.getStructureID() + "," + 
/* 216 */       parentDivision + "," + 
/* 217 */       activeFlag + "," + 
/* 218 */       '\004' + "," + 
/* 219 */       "'" + MilestoneHelper.escapeSingleQuotes(label.getName()) + "'," + 
/* 220 */       "'" + MilestoneHelper.escapeSingleQuotes(label.getStructureAbbreviation()) + "'," + 
/*     */       
/* 222 */       label.getDistribution() + "," + 
/* 223 */       "-1" + "," + 
/* 224 */       userID + "," + 
/* 225 */       "-1";
/*     */ 
/*     */     
/* 228 */     label.flushAudits(userID);
/*     */     
/* 230 */     log.debug("Save new label query:\n" + query);
/* 231 */     JdbcConnector connector = MilestoneHelper.getConnector(query);
/* 232 */     connector.runQuery();
/* 233 */     label.setStructureID(connector.getIntegerField("ReturnId"));
/*     */ 
/*     */     
/* 236 */     String timestampQuery = "SELECT last_updated_ck, last_updated_on FROM vi_structure WHERE structure_id = " + 
/*     */       
/* 238 */       label.getStructureID() + 
/* 239 */       ";";
/*     */     
/* 241 */     JdbcConnector connectorTimestamp = MilestoneHelper.getConnector(timestampQuery);
/* 242 */     connectorTimestamp.runQuery();
/* 243 */     if (connectorTimestamp.more()) {
/*     */       
/* 245 */       label.setLastUpdatedCk(Long.parseLong(connectorTimestamp.getField("last_updated_ck"), 16));
/* 246 */       label.setLastUpdateDate(MilestoneHelper.getDatabaseDate(connectorTimestamp.getField("last_updated_on")));
/*     */     } 
/* 248 */     connectorTimestamp.close();
/*     */     
/* 250 */     connector.close();
/*     */ 
/*     */ 
/*     */     
/* 254 */     if (label.getName().matches("(?i).*unknown.*")) {
/* 255 */       updateDistributionCompany(label);
/*     */     }
/*     */ 
/*     */     
/* 259 */     return label;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void deleteLabel(Label label, int userID) {
/* 268 */     String query = "sp_del_Structure " + 
/* 269 */       label.getStructureID();
/*     */     
/* 271 */     JdbcConnector connector = MilestoneHelper.getConnector(query);
/* 272 */     connector.runQuery();
/* 273 */     connector.close();
/*     */     
/* 275 */     Cache.getInstance().flushCorporateStructure();
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
/*     */   public static Vector getEnvironmentsFromFamilies(Vector families) {
/* 289 */     Vector preCache = new Vector();
/*     */ 
/*     */     
/* 292 */     for (int i = 0; i < families.size(); i++) {
/*     */       
/* 294 */       Family family = (Family)families.get(i);
/* 295 */       Vector environments = family.getEnvironments();
/*     */       
/* 297 */       for (int j = 0; j < environments.size(); j++) {
/* 298 */         preCache.add(environments.get(j));
/*     */       }
/*     */     } 
/* 301 */     return preCache;
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
/*     */   public static Vector getDivisionsFromCompanies(Vector companies) {
/* 315 */     Vector preCache = new Vector();
/*     */ 
/*     */     
/* 318 */     for (int i = 0; i < companies.size(); i++) {
/*     */       
/* 320 */       Company company = (Company)companies.get(i);
/* 321 */       Vector divisions = company.getDivisions();
/*     */       
/* 323 */       for (int j = 0; j < divisions.size(); j++) {
/* 324 */         preCache.add(divisions.get(j));
/*     */       }
/*     */     } 
/* 327 */     return preCache;
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
/*     */   public static Vector getCompaniesFromEnvironments(Vector environments) {
/* 341 */     Vector preCache = new Vector();
/*     */ 
/*     */     
/* 344 */     for (int i = 0; i < environments.size(); i++) {
/*     */       
/* 346 */       Environment environment = (Environment)environments.get(i);
/* 347 */       Vector companies = environment.getCompanies();
/*     */       
/* 349 */       for (int j = 0; j < companies.size(); j++) {
/* 350 */         preCache.add(companies.get(j));
/*     */       }
/*     */     } 
/* 353 */     return preCache;
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
/*     */ 
/*     */ 
/*     */   
/*     */   public void setNotepadQuery(Notepad notepad) {
/* 371 */     if (notepad != null)
/*     */     {
/*     */       
/* 374 */       if (notepad.getCorporateObjectSearchObj() != null) {
/*     */         
/* 376 */         CorporateObjectSearchObj corpObject = notepad.getCorporateObjectSearchObj();
/*     */ 
/*     */         
/* 379 */         Vector contents = new Vector();
/*     */         
/* 381 */         String descriptionSearch = corpObject.getFamilySearch();
/* 382 */         String environmentDescriptionSearch = corpObject.getEnvironmentSearch();
/* 383 */         String companyDescriptionSearch = corpObject.getCompanySearch();
/* 384 */         String divisionDescriptionSearch = corpObject.getDivisionSearch();
/* 385 */         String labelDescriptionSearch = corpObject.getLabelSearch();
/* 386 */         String entityDescriptionSearch = corpObject.getEntitySearch();
/*     */         
/* 388 */         Vector corporateObjects = Cache.getFamilies();
/* 389 */         contents = MilestoneHelper.filterCorporateByDescription(corporateObjects, descriptionSearch);
/*     */         
/* 391 */         contents = getEnvironmentsFromFamilies(contents);
/* 392 */         contents = MilestoneHelper.filterCorporateByDescription(contents, environmentDescriptionSearch);
/* 393 */         contents = getCompaniesFromEnvironments(contents);
/* 394 */         contents = MilestoneHelper.filterCorporateByDescription(contents, companyDescriptionSearch);
/* 395 */         contents = getDivisionsFromCompanies(contents);
/* 396 */         contents = MilestoneHelper.filterCorporateByDescription(contents, divisionDescriptionSearch);
/*     */ 
/*     */         
/* 399 */         Vector preCache = new Vector();
/*     */         
/* 401 */         for (int i = 0; i < contents.size(); i++) {
/*     */           
/* 403 */           Division division = (Division)contents.get(i);
/* 404 */           Vector labels = division.getLabels();
/*     */           
/* 406 */           for (int j = 0; j < labels.size(); j++) {
/* 407 */             preCache.add(labels.get(j));
/*     */           }
/*     */         } 
/* 410 */         preCache = MilestoneHelper.sortCorporateVectorByName(MilestoneHelper.filterCorporateByDescription(preCache, labelDescriptionSearch));
/*     */ 
/*     */         
/* 413 */         preCache = MilestoneHelper.sortCorporateVectorByName(MilestoneHelper_2.filterCorporateByEntityName(preCache, entityDescriptionSearch));
/*     */ 
/*     */         
/* 416 */         notepad.setAllContents(preCache);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector getLabelNotepadList(int UserId, Notepad notepad) {
/* 424 */     sortedVector = new Vector();
/*     */     
/* 426 */     if (notepad != null && 
/* 427 */       notepad.getCorporateObjectSearchObj() != null) {
/*     */ 
/*     */       
/* 430 */       setNotepadQuery(notepad);
/* 431 */       return MilestoneHelper.sortCorporateVectorByName(notepad.getAllContents());
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 436 */     return MilestoneHelper.sortCorporateVectorByName(Cache.getLabels());
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
/*     */   public boolean isTimestampValid(Label label) {
/* 448 */     if (label != null) {
/*     */       
/* 450 */       String timestampQuery = "SELECT last_updated_ck, last_updated_on FROM vi_structure WHERE structure_id = " + 
/*     */         
/* 452 */         label.getStructureID() + 
/* 453 */         ";";
/*     */       
/* 455 */       JdbcConnector connectorTimestamp = MilestoneHelper.getConnector(timestampQuery);
/* 456 */       connectorTimestamp.runQuery();
/* 457 */       if (connectorTimestamp.more())
/*     */       {
/* 459 */         if (label.getLastUpdatedCk() != Long.parseLong(connectorTimestamp.getField("last_updated_ck", ""), 16)) {
/*     */           
/* 461 */           connectorTimestamp.close();
/* 462 */           return false;
/*     */         } 
/*     */       }
/* 465 */       connectorTimestamp.close();
/* 466 */       return true;
/*     */     } 
/* 468 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 478 */   public boolean isDuplicate(String name, int type, int id, int parentId) { return false; }
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
/*     */   public static void getArchimedes(Label label, Boolean IsGetFromCache) {
/* 513 */     if (label.getArchimedesId() == 0) {
/* 514 */       StringBuffer query = new StringBuffer(" SELECT ");
/* 515 */       query.append(" ISNULL(DistCoId,'1') as DistCoId, ");
/* 516 */       query.append(" ISNULL(DistCoName,'UMVD') as DistCoName ");
/* 517 */       query.append(" FROM ArchimedesLabels ");
/* 518 */       query.append(" WHERE MS_LabelId = " + label.getStructureID());
/* 519 */       JdbcConnector connector = MilestoneHelper.getConnector(query.toString());
/* 520 */       connector.runQuery();
/* 521 */       if (connector.more()) {
/*     */         
/* 523 */         label.setDistCoName(connector.getField("DistCoName"));
/* 524 */         label.setDistCoId(connector.getIntegerField("DistCoId"));
/*     */       } 
/* 526 */       connector.close();
/*     */     } else {
/*     */ 
/*     */       
/*     */       try {
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
/* 575 */         Hashtable<Integer, DcLabelData> cachedLD = null;
/*     */ 
/*     */         
/* 578 */         if (IsGetFromCache.booleanValue())
/*     */         {
/* 580 */           if (projectSearchSvcClient.cachedLabelData == null) {
/*     */             
/* 582 */             log.debug("Get Archimedes Label Data start.");
/*     */             try {
/* 584 */               cachedLD = projectSearchSvcClient.LabelDataGet();
/* 585 */               System.out.println("Labels from WCF: " + cachedLD.size());
/* 586 */             } catch (RemoteException re) {
/*     */               
/* 588 */               log.debug(re.getMessage());
/* 589 */               System.out.println(re.getMessage());
/* 590 */             } catch (Exception e) {
/* 591 */               log.debug(e.getMessage());
/* 592 */               System.out.println(e.getMessage());
/*     */             } 
/* 594 */             log.debug("Get Archimedes Label Data end.");
/*     */           } else {
/*     */             
/* 597 */             cachedLD = projectSearchSvcClient.cachedLabelData;
/*     */           } 
/*     */         }
/*     */ 
/*     */         
/* 602 */         DcLabelData dld = null;
/*     */ 
/*     */         
/* 605 */         if (cachedLD != null && cachedLD.containsKey(Integer.valueOf(label.getArchimedesId()))) {
/* 606 */           dld = (DcLabelData)cachedLD.get(Integer.valueOf(label.getArchimedesId()));
/*     */         } else {
/* 608 */           dld = projectSearchSvcClient.LabelDataGetById(label.getArchimedesId());
/*     */         } 
/* 610 */         if (dld != null && dld.getArchimedesID() != null)
/*     */         {
/* 612 */           log.debug("Loading Label Id " + dld.getArchimedesID());
/* 613 */           label.setArchimedesId(dld.getArchimedesID().intValue());
/* 614 */           label.setEntityName(dld.getEntityName());
/* 615 */           label.setLegacyOpCo(dld.getLegacyOperatingCo());
/* 616 */           label.setLegacyOpUnit(dld.getLegacyOperatingUnit());
/* 617 */           label.setLegacySuperLabel(dld.getLegacySuperlabel());
/* 618 */           label.setLegacySubLabel(dld.getLegacySublabel());
/* 619 */           label.setLevelOfActivity(dld.getLevelOfActivity());
/* 620 */           label.setProductionGroupCode(dld.getProductionGroupCode());
/* 621 */           label.setEntityType(dld.getEntityType());
/*     */           
/* 623 */           label.setAPNGInd(dld.getAPNGInd().booleanValue());
/*     */           
/* 625 */           label.setDistCoName(dld.getDistCoName());
/* 626 */           label.setDistCoId(dld.getDistCoID().intValue());
/*     */         }
/*     */       
/* 629 */       } catch (Exception e) {
/*     */         
/* 631 */         System.out.println("****exception raised in projectSearchSvcClient().LabelDataGet****");
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void updateDistributionCompany(Label label) {
/* 643 */     String query = " sp_sav_distCompanyForLabel " + label.getStructureID() + 
/* 644 */       "," + label.getDistCoId() + ",'" + label.getDistCoName() + "'";
/* 645 */     JdbcConnector connector = MilestoneHelper.getConnector(query);
/* 646 */     connector.runQuery();
/* 647 */     connector.close();
/*     */   }
/*     */ }


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\LabelManager.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */