/*     */ package WEB-INF.classes.com.universal.milestone;
/*     */ 
/*     */ import com.techempower.ComponentLog;
/*     */ import com.techempower.EnhancedProperties;
/*     */ import com.techempower.gemini.GeminiApplication;
/*     */ import com.universal.milestone.Cache;
/*     */ import com.universal.milestone.Company;
/*     */ import com.universal.milestone.CorporateObjectSearchObj;
/*     */ import com.universal.milestone.Division;
/*     */ import com.universal.milestone.DivisionManager;
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
/*     */ public class DivisionManager
/*     */   implements MilestoneConstants
/*     */ {
/*     */   public static final String COMPONENT_CODE = "mDiv";
/*  43 */   protected static DivisionManager divisionManager = null;
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
/*  55 */   public static void configure(EnhancedProperties props, GeminiApplication application) { log = application.getLog("mDiv"); }
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
/*     */   public static DivisionManager getInstance() {
/*  73 */     if (divisionManager == null)
/*     */     {
/*  75 */       divisionManager = new DivisionManager();
/*     */     }
/*  77 */     return divisionManager;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Division getDivision(int id) {
/*  86 */     Division division = null;
/*     */     
/*  88 */     division = (Division)MilestoneHelper.getStructureObject(id);
/*     */ 
/*     */ 
/*     */     
/*  92 */     String query = "SELECT last_updated_by, last_updated_on, last_updated_ck FROM vi_Structure WHERE type = 3 AND structure_id = " + 
/*     */ 
/*     */       
/*  95 */       id + 
/*  96 */       " ORDER BY name;";
/*     */     
/*  98 */     JdbcConnector connector = MilestoneHelper.getConnector(query);
/*  99 */     connector.runQuery();
/*     */     
/* 101 */     if (connector.more()) {
/*     */ 
/*     */       
/* 104 */       String lastDateString = connector.getFieldByName("last_updated_on");
/* 105 */       if (lastDateString != null) {
/* 106 */         division.setLastUpdateDate(MilestoneHelper.getDatabaseDate(lastDateString));
/*     */       }
/*     */       
/* 109 */       division.setLastUpdatingUser(connector.getIntegerField("last_updated_by"));
/*     */ 
/*     */       
/* 112 */       long lastUpdatedLong = Long.parseLong(connector.getField("last_updated_ck"), 16);
/* 113 */       division.setLastUpdatedCk(lastUpdatedLong);
/*     */     } 
/* 115 */     connector.close();
/*     */     
/* 117 */     return division;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Division saveDivision(Division division, int userID) {
/* 125 */     long timestamp = division.getLastUpdatedCk();
/* 126 */     int structId = division.getStructureID();
/* 127 */     String activeFlag = "0";
/*     */     
/* 129 */     boolean boolVal = division.getActive();
/* 130 */     if (!boolVal) {
/* 131 */       activeFlag = "1";
/*     */     }
/*     */     
/* 134 */     String query = "sp_sav_Structure " + 
/* 135 */       division.getStructureID() + "," + 
/* 136 */       division.getParentCompany().getStructureID() + "," + 
/* 137 */       activeFlag + "," + 
/* 138 */       '\003' + "," + 
/* 139 */       "'" + MilestoneHelper.escapeSingleQuotes(division.getName()) + "'," + 
/* 140 */       "'" + MilestoneHelper.escapeSingleQuotes(division.getStructureAbbreviation()) + "'," + 
/* 141 */       "0" + "," + 
/* 142 */       "0" + "," + 
/* 143 */       userID + "," + 
/* 144 */       timestamp + ";";
/*     */     
/* 146 */     log.debug("Division update query:\n" + query);
/* 147 */     JdbcConnector connector = MilestoneHelper.getConnector(query);
/* 148 */     connector.runQuery();
/* 149 */     connector.close();
/*     */ 
/*     */     
/* 152 */     division.flushAudits(userID);
/*     */     
/* 154 */     String timestampQuery = "SELECT last_updated_ck, last_updated_on FROM vi_structure WHERE structure_id = " + 
/*     */       
/* 156 */       division.getStructureID() + 
/* 157 */       ";";
/*     */     
/* 159 */     JdbcConnector connectorTimestamp = MilestoneHelper.getConnector(timestampQuery);
/* 160 */     connectorTimestamp.runQuery();
/* 161 */     if (connectorTimestamp.more()) {
/*     */       
/* 163 */       division.setLastUpdatedCk(Long.parseLong(connectorTimestamp.getField("last_updated_ck"), 16));
/* 164 */       division.setLastUpdateDate(MilestoneHelper.getDatabaseDate(connectorTimestamp.getField("last_updated_on")));
/*     */     } 
/* 166 */     connectorTimestamp.close();
/*     */     
/* 168 */     return division;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Division saveNewDivision(Division division, int userID) {
/* 176 */     int parentCompany = -1;
/* 177 */     if (division.getParentCompany() != null) {
/* 178 */       parentCompany = division.getParentCompany().getStructureID();
/*     */     }
/*     */     
/* 181 */     division.flushAudits(userID);
/*     */ 
/*     */     
/* 184 */     String activeFlag = "0";
/* 185 */     boolean boolVal = division.getActive();
/* 186 */     if (!boolVal) {
/* 187 */       activeFlag = "1";
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 193 */     String query = "sp_sav_Structure " + 
/* 194 */       division.getStructureID() + "," + 
/* 195 */       parentCompany + "," + 
/* 196 */       activeFlag + "," + 
/* 197 */       '\003' + "," + 
/* 198 */       "'" + MilestoneHelper.escapeSingleQuotes(division.getName()) + "'," + 
/* 199 */       "'" + MilestoneHelper.escapeSingleQuotes(division.getStructureAbbreviation()) + "'," + 
/* 200 */       "-1" + "," + 
/* 201 */       "-1" + "," + 
/* 202 */       userID + "," + 
/* 203 */       "-1" + ";";
/*     */     
/* 205 */     log.debug("Division save query:\n" + query);
/* 206 */     JdbcConnector connector = MilestoneHelper.getConnector(query);
/* 207 */     connector.runQuery();
/* 208 */     division.setStructureID(connector.getIntegerField("ReturnId"));
/*     */ 
/*     */     
/* 211 */     String timestampQuery = "SELECT last_updated_ck, last_updated_on FROM vi_structure WHERE structure_id = " + 
/*     */       
/* 213 */       division.getStructureID() + 
/* 214 */       ";";
/*     */     
/* 216 */     JdbcConnector connectorTimestamp = MilestoneHelper.getConnector(timestampQuery);
/* 217 */     connectorTimestamp.runQuery();
/* 218 */     if (connectorTimestamp.more()) {
/*     */       
/* 220 */       division.setLastUpdatedCk(Long.parseLong(connectorTimestamp.getField("last_updated_ck"), 16));
/* 221 */       division.setLastUpdateDate(MilestoneHelper.getDatabaseDate(connectorTimestamp.getField("last_updated_on")));
/*     */     } 
/* 223 */     connectorTimestamp.close();
/*     */ 
/*     */     
/* 226 */     connector.close();
/*     */     
/* 228 */     return division;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void deleteDivision(Division division, int userID) {
/* 236 */     String query = "sp_del_Structure " + 
/* 237 */       division.getStructureID();
/*     */     
/* 239 */     log.debug("Division delete query:\n" + query);
/* 240 */     JdbcConnector connector = MilestoneHelper.getConnector(query);
/* 241 */     connector.runQuery();
/* 242 */     connector.close();
/*     */     
/* 244 */     Cache.getInstance().flushCorporateStructure();
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
/* 261 */     if (notepad != null)
/*     */     {
/*     */       
/* 264 */       if (notepad.getCorporateObjectSearchObj() != null) {
/*     */         
/* 266 */         CorporateObjectSearchObj corpObject = notepad.getCorporateObjectSearchObj();
/*     */         
/* 268 */         Vector contents = new Vector();
/*     */ 
/*     */         
/* 271 */         String descriptionSearch = corpObject.getFamilySearch();
/* 272 */         String environmentDescriptionSearch = corpObject.getEnvironmentSearch();
/* 273 */         String companyDescriptionSearch = corpObject.getCompanySearch();
/* 274 */         String divisionDescriptionSearch = corpObject.getDivisionSearch();
/*     */         
/* 276 */         Vector corporateObjects = Cache.getFamilies();
/* 277 */         contents = MilestoneHelper.filterCorporateByDescription(corporateObjects, descriptionSearch);
/* 278 */         contents = MilestoneHelper.filterCorporateByDescription(Cache.getFamilies(), descriptionSearch);
/* 279 */         contents = getEnvironmentsFromFamilies(contents);
/* 280 */         contents = MilestoneHelper.filterCorporateByDescription(contents, environmentDescriptionSearch);
/* 281 */         contents = getCompaniesFromEnvironments(contents);
/* 282 */         contents = MilestoneHelper.filterCorporateByDescription(contents, companyDescriptionSearch);
/* 283 */         contents = getDivisionsFromCompanies(contents);
/* 284 */         contents = MilestoneHelper.filterCorporateByDescription(contents, divisionDescriptionSearch);
/*     */ 
/*     */ 
/*     */         
/* 288 */         notepad.setAllContents(MilestoneHelper.sortCorporateVectorByName(contents));
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public Vector getDivisionNotepadList(int UserId, Notepad notepad) {
/* 295 */     if (notepad != null && 
/* 296 */       notepad.getCorporateObjectSearchObj() != null) {
/*     */ 
/*     */       
/* 299 */       setNotepadQuery(notepad);
/* 300 */       return MilestoneHelper.sortCorporateVectorByName(notepad.getAllContents());
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 305 */     return MilestoneHelper.sortCorporateVectorByName(Cache.getDivisions());
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
/* 319 */     Vector preCache = new Vector();
/*     */ 
/*     */     
/* 322 */     for (int i = 0; i < companies.size(); i++) {
/*     */       
/* 324 */       Company company = (Company)companies.get(i);
/* 325 */       Vector divisions = company.getDivisions();
/*     */       
/* 327 */       for (int j = 0; j < divisions.size(); j++) {
/* 328 */         preCache.add(divisions.get(j));
/*     */       }
/*     */     } 
/* 331 */     return preCache;
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
/* 345 */     Vector preCache = new Vector();
/*     */ 
/*     */     
/* 348 */     for (int i = 0; i < families.size(); i++) {
/*     */       
/* 350 */       Family family = (Family)families.get(i);
/* 351 */       Vector environments = family.getEnvironments();
/*     */       
/* 353 */       for (int j = 0; j < environments.size(); j++) {
/* 354 */         preCache.add(environments.get(j));
/*     */       }
/*     */     } 
/* 357 */     return preCache;
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
/* 370 */     Vector preCache = new Vector();
/*     */ 
/*     */     
/* 373 */     for (int i = 0; i < environments.size(); i++) {
/*     */       
/* 375 */       Environment environment = (Environment)environments.get(i);
/* 376 */       Vector companies = environment.getCompanies();
/*     */       
/* 378 */       for (int j = 0; j < companies.size(); j++) {
/* 379 */         preCache.add(companies.get(j));
/*     */       }
/*     */     } 
/* 382 */     return preCache;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isTimestampValid(Division division) {
/* 393 */     if (division != null) {
/*     */       
/* 395 */       String timestampQuery = "SELECT last_updated_ck, last_updated_on FROM vi_structure WHERE structure_id = " + 
/*     */         
/* 397 */         division.getStructureID() + 
/* 398 */         ";";
/*     */       
/* 400 */       JdbcConnector connectorTimestamp = MilestoneHelper.getConnector(timestampQuery);
/* 401 */       connectorTimestamp.runQuery();
/* 402 */       if (connectorTimestamp.more())
/*     */       {
/* 404 */         if (division.getLastUpdatedCk() != Long.parseLong(connectorTimestamp.getField("last_updated_ck", ""), 16)) {
/*     */           
/* 406 */           connectorTimestamp.close();
/* 407 */           return false;
/*     */         } 
/*     */       }
/* 410 */       connectorTimestamp.close();
/* 411 */       return true;
/*     */     } 
/* 413 */     return false;
/*     */   }
/*     */ }


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\DivisionManager.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */