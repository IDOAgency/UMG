/*     */ package WEB-INF.classes.com.universal.milestone;
/*     */ 
/*     */ import com.techempower.ComponentLog;
/*     */ import com.techempower.EnhancedProperties;
/*     */ import com.techempower.gemini.GeminiApplication;
/*     */ import com.universal.milestone.Cache;
/*     */ import com.universal.milestone.Company;
/*     */ import com.universal.milestone.CompanyManager;
/*     */ import com.universal.milestone.CorporateObjectSearchObj;
/*     */ import com.universal.milestone.Environment;
/*     */ import com.universal.milestone.Family;
/*     */ import com.universal.milestone.JdbcConnector;
/*     */ import com.universal.milestone.MilestoneConstants;
/*     */ import com.universal.milestone.MilestoneHelper;
/*     */ import com.universal.milestone.Notepad;
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
/*     */ public class CompanyManager
/*     */   implements MilestoneConstants
/*     */ {
/*     */   public static final String COMPONENT_CODE = "mCmp";
/*  43 */   protected static CompanyManager companyManager = null;
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
/*  55 */   public static void configure(EnhancedProperties props, GeminiApplication application) { log = application.getLog("mCmp"); }
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
/*     */   public static CompanyManager getInstance() {
/*  73 */     if (companyManager == null)
/*     */     {
/*  75 */       companyManager = new CompanyManager();
/*     */     }
/*  77 */     return companyManager;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Company getCompany(int id) {
/*  85 */     Company company = null;
/*     */     
/*  87 */     company = (Company)MilestoneHelper.getStructureObject(id);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  94 */     String query = "SELECT last_updated_by, last_updated_on, last_updated_ck FROM vi_Structure WHERE type = 2 AND structure_id = " + 
/*     */ 
/*     */       
/*  97 */       id + 
/*  98 */       " ORDER BY name;";
/*     */     
/* 100 */     JdbcConnector connector = MilestoneHelper.getConnector(query);
/* 101 */     connector.runQuery();
/*     */ 
/*     */     
/* 104 */     if (connector.more()) {
/*     */ 
/*     */       
/* 107 */       String lastDateString = connector.getFieldByName("last_updated_on");
/* 108 */       if (lastDateString != null) {
/* 109 */         company.setLastUpdateDate(MilestoneHelper.getDatabaseDate(lastDateString));
/*     */       }
/*     */       
/* 112 */       company.setLastUpdatingUser(connector.getIntegerField("last_updated_by"));
/*     */ 
/*     */       
/* 115 */       long lastUpdatedLong = Long.parseLong(connector.getField("last_updated_ck"), 16);
/* 116 */       company.setLastUpdatedCk(lastUpdatedLong);
/*     */     } 
/* 118 */     connector.close();
/*     */     
/* 120 */     return company;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Company saveCompany(Company company, int userID) {
/* 128 */     long timestamp = company.getLastUpdatedCk();
/*     */     
/* 130 */     String activeFlag = "0";
/* 131 */     boolean boolVal = company.getActive();
/* 132 */     if (!boolVal) {
/* 133 */       activeFlag = "1";
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 138 */     String query = "sp_sav_Structure " + 
/* 139 */       company.getStructureID() + "," + 
/* 140 */       company.getParentEnvironment().getStructureID() + "," + 
/* 141 */       activeFlag + "," + 
/* 142 */       '\002' + "," + 
/* 143 */       "'" + MilestoneHelper.escapeSingleQuotes(company.getName()) + "'," + 
/* 144 */       "'" + MilestoneHelper.escapeSingleQuotes(company.getStructureAbbreviation()) + "'," + 
/* 145 */       company.getDistribution() + "," + 
/* 146 */       company.getCalendarGroup() + "," + 
/* 147 */       userID + "," + 
/* 148 */       timestamp + 
/* 149 */       ";";
/*     */     
/* 151 */     JdbcConnector connector = MilestoneHelper.getConnector(query);
/* 152 */     log.debug("Executing query: " + query);
/* 153 */     connector.runQuery();
/* 154 */     connector.close();
/*     */ 
/*     */     
/* 157 */     log.debug("Flushing audits.");
/* 158 */     company.flushAudits(userID);
/*     */     
/* 160 */     String timestampQuery = "SELECT last_updated_ck, last_updated_on FROM vi_structure WHERE structure_id = " + 
/*     */       
/* 162 */       company.getStructureID() + 
/* 163 */       ";";
/*     */     
/* 165 */     JdbcConnector connectorTimestamp = MilestoneHelper.getConnector(timestampQuery);
/* 166 */     connectorTimestamp.runQuery();
/* 167 */     if (connectorTimestamp.more()) {
/*     */       
/* 169 */       company.setLastUpdatedCk(Long.parseLong(connectorTimestamp.getField("last_updated_ck"), 16));
/* 170 */       company.setLastUpdateDate(MilestoneHelper.getDatabaseDate(connectorTimestamp.getField("last_updated_on")));
/*     */     } 
/* 172 */     connectorTimestamp.close();
/*     */     
/* 174 */     return company;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Company saveNewCompany(Company company, int userID) {
/* 182 */     long timestamp = company.getLastUpdatedCk();
/*     */ 
/*     */ 
/*     */     
/* 186 */     company.flushAudits(userID);
/*     */ 
/*     */ 
/*     */     
/* 190 */     String query = "sp_sav_Structure " + 
/* 191 */       company.getStructureID() + "," + 
/* 192 */       company.getParentEnvironment().getStructureID() + "," + 
/* 193 */       "0" + "," + 
/* 194 */       '\002' + "," + 
/* 195 */       "'" + MilestoneHelper.escapeSingleQuotes(company.getName()) + "'," + 
/* 196 */       "'" + MilestoneHelper.escapeSingleQuotes(company.getStructureAbbreviation()) + "'," + 
/* 197 */       "0" + "," + 
/* 198 */       "0" + "," + 
/* 199 */       userID + "," + 
/* 200 */       timestamp + 
/* 201 */       ";";
/*     */     
/* 203 */     JdbcConnector connector = MilestoneHelper.getConnector(query);
/* 204 */     connector.runQuery();
/*     */ 
/*     */     
/* 207 */     if (company.isNew()) {
/*     */       
/* 209 */       company.setStructureID(connector.getIntegerField("ReturnId"));
/* 210 */       log.debug("New company's ID set to " + company.getStructureID());
/*     */     } 
/*     */ 
/*     */     
/* 214 */     String timestampQuery = "SELECT last_updated_ck, last_updated_on FROM vi_structure WHERE structure_id = " + 
/*     */       
/* 216 */       company.getStructureID() + 
/* 217 */       ";";
/*     */     
/* 219 */     JdbcConnector connectorTimestamp = MilestoneHelper.getConnector(timestampQuery);
/* 220 */     connectorTimestamp.runQuery();
/* 221 */     if (connectorTimestamp.more()) {
/*     */       
/* 223 */       company.setLastUpdatedCk(Long.parseLong(connectorTimestamp.getField("last_updated_ck"), 16));
/* 224 */       company.setLastUpdateDate(MilestoneHelper.getDatabaseDate(connectorTimestamp.getField("last_updated_on")));
/*     */     } 
/* 226 */     connectorTimestamp.close();
/*     */     
/* 228 */     connector.close();
/*     */ 
/*     */ 
/*     */     
/* 232 */     String companyUpdateQuery = "sp_structure_insert_maintenance 2," + company.getStructureID() + ";";
/* 233 */     JdbcConnector companyUpdate = MilestoneHelper.getConnector(companyUpdateQuery);
/* 234 */     companyUpdate.runQuery();
/* 235 */     companyUpdate.close();
/*     */     
/* 237 */     return company;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void deleteCompany(Company company, int userID) {
/* 245 */     String query = "sp_del_Structure " + 
/* 246 */       company.getStructureID();
/*     */     
/* 248 */     JdbcConnector connector = MilestoneHelper.getConnector(query);
/* 249 */     connector.runQuery();
/* 250 */     connector.close();
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
/*     */   public void setNotepadQuery(Notepad notepad) {
/* 267 */     if (notepad != null)
/*     */     {
/*     */       
/* 270 */       if (notepad.getCorporateObjectSearchObj() != null) {
/*     */         
/* 272 */         CorporateObjectSearchObj corpObject = notepad.getCorporateObjectSearchObj();
/*     */         
/* 274 */         Vector contents = new Vector();
/*     */ 
/*     */ 
/*     */         
/* 278 */         String descriptionSearch = corpObject.getFamilySearch();
/* 279 */         String environmentDescriptionSearch = corpObject.getEnvironmentSearch();
/* 280 */         String companyDescriptionSearch = corpObject.getCompanySearch();
/*     */         
/* 282 */         Vector corporateObjects = Cache.getFamilies();
/* 283 */         contents = MilestoneHelper.filterCorporateByDescription(corporateObjects, descriptionSearch);
/* 284 */         contents = MilestoneHelper.filterCorporateByDescription(Cache.getFamilies(), descriptionSearch);
/* 285 */         contents = getEnvironmentsFromFamilies(contents);
/* 286 */         contents = MilestoneHelper.filterCorporateByDescription(contents, environmentDescriptionSearch);
/* 287 */         contents = getCompaniesFromEnvironments(contents);
/* 288 */         contents = MilestoneHelper.filterCorporateByDescription(contents, companyDescriptionSearch);
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
/* 303 */         notepad.setAllContents(MilestoneHelper.sortCorporateVectorByName(contents));
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector getCompanyNotepadList(int UserId, Notepad notepad) {
/* 311 */     if (notepad != null && 
/* 312 */       notepad.getCorporateObjectSearchObj() != null) {
/*     */ 
/*     */       
/* 315 */       setNotepadQuery(notepad);
/* 316 */       return MilestoneHelper.sortCorporateVectorByName(notepad.getAllContents());
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 321 */     return MilestoneHelper.sortCorporateVectorByName(Cache.getCompanies());
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
/*     */   public static Vector getCompaniesFromFamilies(Vector families) {
/* 335 */     Vector preCache = new Vector();
/*     */ 
/*     */     
/* 338 */     for (int i = 0; i < families.size(); i++) {
/*     */       
/* 340 */       Family family = (Family)families.get(i);
/* 341 */       Vector companies = family.getCompanies();
/*     */       
/* 343 */       for (int j = 0; j < companies.size(); j++) {
/* 344 */         preCache.add(companies.get(j));
/*     */       }
/*     */     } 
/* 347 */     return preCache;
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
/*     */   public static Vector getCompaniesFromEnvironments(Vector environments) {
/* 360 */     Vector preCache = new Vector();
/*     */ 
/*     */     
/* 363 */     for (int i = 0; i < environments.size(); i++) {
/*     */       
/* 365 */       Environment environment = (Environment)environments.get(i);
/* 366 */       Vector companies = environment.getCompanies();
/*     */       
/* 368 */       for (int j = 0; j < companies.size(); j++) {
/* 369 */         preCache.add(companies.get(j));
/*     */       }
/*     */     } 
/* 372 */     return preCache;
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
/* 386 */     Vector preCache = new Vector();
/*     */ 
/*     */     
/* 389 */     for (int i = 0; i < families.size(); i++) {
/*     */       
/* 391 */       Family family = (Family)families.get(i);
/* 392 */       Vector environments = family.getEnvironments();
/*     */       
/* 394 */       for (int j = 0; j < environments.size(); j++) {
/* 395 */         preCache.add(environments.get(j));
/*     */       }
/*     */     } 
/* 398 */     return preCache;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isTimestampValid(Company company) {
/* 409 */     if (company != null) {
/*     */       
/* 411 */       String timestampQuery = "SELECT last_updated_ck, last_updated_on FROM vi_structure WHERE structure_id = " + 
/*     */         
/* 413 */         company.getStructureID() + 
/* 414 */         ";";
/*     */       
/* 416 */       JdbcConnector connectorTimestamp = MilestoneHelper.getConnector(timestampQuery);
/* 417 */       connectorTimestamp.runQuery();
/* 418 */       if (connectorTimestamp.more())
/*     */       {
/* 420 */         if (company.getLastUpdatedCk() != Long.parseLong(connectorTimestamp.getField("last_updated_ck", ""), 16)) {
/*     */           
/* 422 */           connectorTimestamp.close();
/* 423 */           return false;
/*     */         } 
/*     */       }
/* 426 */       connectorTimestamp.close();
/* 427 */       return true;
/*     */     } 
/* 429 */     return false;
/*     */   }
/*     */ }


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\CompanyManager.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */