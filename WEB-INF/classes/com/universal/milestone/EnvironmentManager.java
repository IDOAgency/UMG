/*     */ package WEB-INF.classes.com.universal.milestone;
/*     */ 
/*     */ import com.techempower.ComponentLog;
/*     */ import com.techempower.EnhancedProperties;
/*     */ import com.techempower.gemini.GeminiApplication;
/*     */ import com.universal.milestone.Cache;
/*     */ import com.universal.milestone.CorporateObjectSearchObj;
/*     */ import com.universal.milestone.Environment;
/*     */ import com.universal.milestone.EnvironmentManager;
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
/*     */ 
/*     */ 
/*     */ public class EnvironmentManager
/*     */   implements MilestoneConstants
/*     */ {
/*     */   public static final String COMPONENT_CODE = "mEnv";
/*  44 */   protected static EnvironmentManager environmentManager = null;
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
/*  56 */   public static void configure(EnhancedProperties props, GeminiApplication application) { log = application.getLog("mEnv"); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static EnvironmentManager getInstance() {
/*  74 */     if (environmentManager == null)
/*     */     {
/*  76 */       environmentManager = new EnvironmentManager();
/*     */     }
/*  78 */     return environmentManager;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Environment getEnvironment(int id) {
/*  86 */     Environment environment = null;
/*     */     
/*  88 */     environment = (Environment)MilestoneHelper.getStructureObject(id);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  95 */     String query = "SELECT last_updated_by, last_updated_on, last_updated_ck FROM vi_Structure WHERE type = 5 AND structure_id = " + 
/*     */ 
/*     */       
/*  98 */       id + 
/*  99 */       " ORDER BY name;";
/*     */     
/* 101 */     JdbcConnector connector = MilestoneHelper.getConnector(query);
/* 102 */     connector.runQuery();
/*     */ 
/*     */     
/* 105 */     if (connector.more()) {
/*     */ 
/*     */       
/* 108 */       String lastDateString = connector.getFieldByName("last_updated_on");
/* 109 */       if (lastDateString != null) {
/* 110 */         environment.setLastUpdateDate(MilestoneHelper.getDatabaseDate(lastDateString));
/*     */       }
/*     */       
/* 113 */       environment.setLastUpdatingUser(connector.getIntegerField("last_updated_by"));
/*     */ 
/*     */       
/* 116 */       long lastUpdatedLong = Long.parseLong(connector.getField("last_updated_ck"), 16);
/* 117 */       environment.setLastUpdatedCk(lastUpdatedLong);
/*     */     } 
/* 119 */     connector.close();
/*     */     
/* 121 */     return environment;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Environment saveEnvironment(Environment environment, int userID) {
/* 129 */     long timestamp = environment.getLastUpdatedCk();
/*     */     
/* 131 */     String activeFlag = "0";
/* 132 */     boolean boolVal = environment.getActive();
/* 133 */     if (!boolVal) {
/* 134 */       activeFlag = "1";
/*     */     }
/* 136 */     String query = "sp_sav_Structure " + 
/* 137 */       environment.getStructureID() + "," + 
/* 138 */       environment.getParentFamily().getStructureID() + "," + 
/* 139 */       activeFlag + "," + 
/* 140 */       '\005' + "," + 
/* 141 */       "'" + MilestoneHelper.escapeSingleQuotes(environment.getName()) + "'," + 
/* 142 */       "'" + MilestoneHelper.escapeSingleQuotes(environment.getStructureAbbreviation()) + "'," + 
/* 143 */       environment.getDistribution() + "," + 
/* 144 */       environment.getCalendarGroup() + "," + 
/* 145 */       userID + "," + 
/* 146 */       timestamp + 
/* 147 */       ";";
/*     */     
/* 149 */     JdbcConnector connector = MilestoneHelper.getConnector(query);
/*     */     
/* 151 */     connector.runQuery();
/* 152 */     connector.close();
/*     */ 
/*     */ 
/*     */     
/* 156 */     environment.flushAudits(userID);
/*     */     
/* 158 */     String timestampQuery = "SELECT last_updated_ck, last_updated_on FROM vi_structure WHERE structure_id = " + 
/*     */       
/* 160 */       environment.getStructureID() + 
/* 161 */       ";";
/*     */     
/* 163 */     JdbcConnector connectorTimestamp = MilestoneHelper.getConnector(timestampQuery);
/* 164 */     connectorTimestamp.runQuery();
/* 165 */     if (connectorTimestamp.more()) {
/*     */       
/* 167 */       environment.setLastUpdatedCk(Long.parseLong(connectorTimestamp.getField("last_updated_ck"), 16));
/* 168 */       environment.setLastUpdateDate(MilestoneHelper.getDatabaseDate(connectorTimestamp.getField("last_updated_on")));
/*     */     } 
/* 170 */     connectorTimestamp.close();
/*     */     
/* 172 */     return environment;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Environment saveNewEnvironment(Environment environment, int userID) {
/* 180 */     long timestamp = environment.getLastUpdatedCk();
/*     */ 
/*     */ 
/*     */     
/* 184 */     environment.flushAudits(userID);
/*     */     
/* 186 */     String query = "sp_sav_Structure " + 
/* 187 */       environment.getStructureID() + "," + 
/* 188 */       environment.getParentFamily().getStructureID() + "," + 
/* 189 */       "0" + "," + 
/* 190 */       '\005' + "," + 
/* 191 */       "'" + MilestoneHelper.escapeSingleQuotes(environment.getName()) + "'," + 
/* 192 */       "'" + MilestoneHelper.escapeSingleQuotes(environment.getStructureAbbreviation()) + "'," + 
/* 193 */       "0" + "," + 
/* 194 */       "0" + "," + 
/* 195 */       userID + "," + 
/* 196 */       timestamp + 
/* 197 */       ";";
/*     */     
/* 199 */     JdbcConnector connector = MilestoneHelper.getConnector(query);
/* 200 */     connector.runQuery();
/*     */ 
/*     */     
/* 203 */     if (environment.isNew())
/*     */     {
/* 205 */       environment.setStructureID(connector.getIntegerField("ReturnId"));
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 210 */     String timestampQuery = "SELECT last_updated_ck, last_updated_on FROM vi_structure WHERE structure_id = " + 
/*     */       
/* 212 */       environment.getStructureID() + 
/* 213 */       ";";
/*     */     
/* 215 */     JdbcConnector connectorTimestamp = MilestoneHelper.getConnector(timestampQuery);
/* 216 */     connectorTimestamp.runQuery();
/* 217 */     if (connectorTimestamp.more()) {
/*     */       
/* 219 */       environment.setLastUpdatedCk(Long.parseLong(connectorTimestamp.getField("last_updated_ck"), 16));
/* 220 */       environment.setLastUpdateDate(MilestoneHelper.getDatabaseDate(connectorTimestamp.getField("last_updated_on")));
/*     */     } 
/* 222 */     connectorTimestamp.close();
/*     */     
/* 224 */     connector.close();
/*     */     
/* 226 */     return environment;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void deleteEnvironment(Environment environment, int userID) {
/* 234 */     String query = "sp_del_Structure " + 
/* 235 */       environment.getStructureID();
/*     */     
/* 237 */     JdbcConnector connector = MilestoneHelper.getConnector(query);
/* 238 */     connector.runQuery();
/* 239 */     connector.close();
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
/* 256 */     if (notepad != null)
/*     */     {
/*     */       
/* 259 */       if (notepad.getCorporateObjectSearchObj() != null) {
/*     */         
/* 261 */         CorporateObjectSearchObj corpObject = notepad.getCorporateObjectSearchObj();
/*     */         
/* 263 */         Vector contents = new Vector();
/*     */         
/* 265 */         String descriptionSearch = corpObject.getFamilySearch();
/* 266 */         String environmentDescriptionSearch = corpObject.getEnvironmentSearch();
/*     */         
/* 268 */         Vector corporateObjects = Cache.getFamilies();
/* 269 */         contents = MilestoneHelper.filterCorporateByDescription(corporateObjects, descriptionSearch);
/* 270 */         contents = MilestoneHelper.filterCorporateByDescription(Cache.getFamilies(), descriptionSearch);
/* 271 */         contents = getEnvironmentsFromFamilies(contents);
/* 272 */         contents = MilestoneHelper.filterCorporateByDescription(contents, environmentDescriptionSearch);
/*     */         
/* 274 */         notepad.setAllContents(MilestoneHelper.sortCorporateVectorByName(contents));
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector getEnvironmentNotepadList(int UserId, Notepad notepad) {
/* 282 */     if (notepad != null && 
/* 283 */       notepad.getCorporateObjectSearchObj() != null) {
/*     */ 
/*     */       
/* 286 */       setNotepadQuery(notepad);
/* 287 */       return MilestoneHelper.sortCorporateVectorByName(notepad.getAllContents());
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 292 */     return MilestoneHelper.sortCorporateVectorByName(Cache.getEnvironments());
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
/* 306 */     Vector preCache = new Vector();
/*     */ 
/*     */     
/* 309 */     for (int i = 0; i < families.size(); i++) {
/*     */       
/* 311 */       Family family = (Family)families.get(i);
/* 312 */       Vector environments = family.getEnvironments();
/*     */       
/* 314 */       for (int j = 0; j < environments.size(); j++) {
/* 315 */         preCache.add(environments.get(j));
/*     */       }
/*     */     } 
/* 318 */     return preCache;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isTimestampValid(Environment environment) {
/* 327 */     if (environment != null) {
/*     */       
/* 329 */       String timestampQuery = "SELECT last_updated_ck, last_updated_on FROM vi_structure WHERE structure_id = " + 
/*     */         
/* 331 */         environment.getStructureID() + 
/* 332 */         ";";
/*     */       
/* 334 */       JdbcConnector connectorTimestamp = MilestoneHelper.getConnector(timestampQuery);
/* 335 */       connectorTimestamp.runQuery();
/* 336 */       if (connectorTimestamp.more())
/*     */       {
/* 338 */         if (environment.getLastUpdatedCk() != Long.parseLong(connectorTimestamp.getField("last_updated_ck", ""), 16)) {
/*     */           
/* 340 */           connectorTimestamp.close();
/* 341 */           return false;
/*     */         } 
/*     */       }
/* 344 */       connectorTimestamp.close();
/* 345 */       return true;
/*     */     } 
/* 347 */     return false;
/*     */   }
/*     */ }


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\EnvironmentManager.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */