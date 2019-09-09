/*     */ package WEB-INF.classes.com.universal.milestone;
/*     */ 
/*     */ import com.techempower.ComponentLog;
/*     */ import com.techempower.EnhancedProperties;
/*     */ import com.techempower.gemini.GeminiApplication;
/*     */ import com.universal.milestone.Cache;
/*     */ import com.universal.milestone.CorporateObjectSearchObj;
/*     */ import com.universal.milestone.Family;
/*     */ import com.universal.milestone.FamilyManager;
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
/*     */ public class FamilyManager
/*     */   implements MilestoneConstants
/*     */ {
/*     */   public static final String COMPONENT_CODE = "mFam";
/*  43 */   protected static FamilyManager familyManager = null;
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
/*  55 */   public static void configure(EnhancedProperties props, GeminiApplication application) { log = application.getLog("mFam"); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static FamilyManager getInstance() {
/*  73 */     if (familyManager == null)
/*     */     {
/*  75 */       familyManager = new FamilyManager();
/*     */     }
/*  77 */     return familyManager;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Family getFamily(int id) {
/*  86 */     Family family = null;
/*     */     
/*  88 */     family = (Family)MilestoneHelper.getStructureObject(id);
/*     */     
/*  90 */     String query = "SELECT last_updated_by, last_updated_on, last_updated_ck FROM vi_Structure  WHERE type = 1 AND structure_id = " + 
/*     */ 
/*     */       
/*  93 */       id + 
/*  94 */       " ORDER BY name;";
/*     */     
/*  96 */     JdbcConnector connector = MilestoneHelper.getConnector(query);
/*  97 */     connector.runQuery();
/*     */     
/*  99 */     if (connector.more()) {
/*     */ 
/*     */       
/* 102 */       String lastDateString = connector.getFieldByName("last_updated_on");
/* 103 */       if (lastDateString != null) {
/* 104 */         family.setLastUpdateDate(MilestoneHelper.getDatabaseDate(lastDateString));
/*     */       }
/*     */       
/* 107 */       family.setLastUpdatingUser(connector.getIntegerField("last_updated_by"));
/*     */ 
/*     */       
/* 110 */       long lastUpdatedLong = Long.parseLong(connector.getField("last_updated_ck"), 16);
/* 111 */       family.setLastUpdatedCk(lastUpdatedLong);
/*     */     } 
/*     */     
/* 114 */     connector.close();
/*     */     
/* 116 */     return family;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Family saveFamily(Family family, int userID) {
/* 124 */     long timestamp = family.getLastUpdatedCk();
/* 125 */     String activeFlag = "0";
/* 126 */     boolean boolVal = family.getActive();
/* 127 */     if (!boolVal) {
/* 128 */       activeFlag = "1";
/*     */     }
/*     */     
/* 131 */     String query = "sp_sav_Structure " + 
/* 132 */       family.getStructureID() + "," + 
/* 133 */       "0" + "," + 
/* 134 */       activeFlag + "," + 
/* 135 */       '\001' + "," + 
/* 136 */       "'" + MilestoneHelper.escapeSingleQuotes(family.getName()) + "'," + 
/* 137 */       "'" + MilestoneHelper.escapeSingleQuotes(family.getStructureAbbreviation()) + "'," + 
/* 138 */       "0" + "," + 
/* 139 */       "0" + "," + 
/* 140 */       userID + "," + 
/* 141 */       timestamp;
/*     */     
/* 143 */     log.debug("Update family query:\n" + query);
/* 144 */     JdbcConnector connector = MilestoneHelper.getConnector(query);
/* 145 */     connector.runQuery();
/*     */ 
/*     */     
/* 148 */     family.flushAudits(userID);
/*     */     
/* 150 */     if (family.getStructureID() < 1) {
/* 151 */       family.setStructureID(connector.getIntegerField("ReturnId"));
/*     */     }
/* 153 */     connector.close();
/*     */     
/* 155 */     String timestampQuery = "SELECT last_updated_ck, last_updated_on FROM vi_structure WHERE structure_id = " + 
/*     */       
/* 157 */       family.getStructureID() + 
/* 158 */       ";";
/*     */     
/* 160 */     JdbcConnector connectorTimestamp = MilestoneHelper.getConnector(timestampQuery);
/* 161 */     connectorTimestamp.runQuery();
/* 162 */     if (connectorTimestamp.more()) {
/*     */       
/* 164 */       family.setLastUpdatedCk(Long.parseLong(connectorTimestamp.getField("last_updated_ck"), 16));
/* 165 */       family.setLastUpdateDate(MilestoneHelper.getDatabaseDate(connectorTimestamp.getField("last_updated_on")));
/*     */     } 
/* 167 */     connectorTimestamp.close();
/* 168 */     return family;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void deleteFamily(Family family, int userID) {
/* 176 */     String query = "sp_del_Structure " + 
/* 177 */       family.getStructureID();
/*     */     
/* 179 */     log.debug("Delete query:\n" + query);
/* 180 */     JdbcConnector connector = MilestoneHelper.getConnector(query);
/* 181 */     connector.runQuery();
/* 182 */     connector.close();
/*     */     
/* 184 */     Cache.getInstance().flushCorporateStructure();
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
/* 201 */     if (notepad != null)
/*     */     {
/* 203 */       if (notepad.getCorporateObjectSearchObj() != null) {
/*     */         
/* 205 */         CorporateObjectSearchObj corpObject = notepad.getCorporateObjectSearchObj();
/* 206 */         Vector contents = new Vector();
/*     */         
/* 208 */         String descriptionSearch = corpObject.getFamilySearch();
/* 209 */         Vector corporateObjects = Cache.getFamilies();
/* 210 */         contents = MilestoneHelper.filterCorporateByDescription(corporateObjects, descriptionSearch);
/*     */         
/* 212 */         contents = MilestoneHelper.sortCorporateVectorByName(contents);
/* 213 */         notepad.setAllContents(contents);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector getFamilyNotepadList(int UserId, Notepad notepad) {
/* 221 */     if (notepad != null && 
/* 222 */       notepad.getCorporateObjectSearchObj() != null) {
/*     */ 
/*     */       
/* 225 */       setNotepadQuery(notepad);
/* 226 */       return MilestoneHelper.sortCorporateVectorByName(notepad.getAllContents());
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 231 */     return MilestoneHelper.sortCorporateVectorByName(Cache.getFamilies());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isTimestampValid(Family family) {
/* 241 */     if (family != null) {
/*     */       
/* 243 */       String timestampQuery = "SELECT last_updated_ck, last_updated_on FROM vi_structure WHERE structure_id = " + 
/*     */         
/* 245 */         family.getStructureID() + 
/* 246 */         ";";
/*     */       
/* 248 */       JdbcConnector connectorTimestamp = MilestoneHelper.getConnector(timestampQuery);
/* 249 */       connectorTimestamp.runQuery();
/* 250 */       if (connectorTimestamp.more())
/*     */       {
/* 252 */         if (family.getLastUpdatedCk() != Long.parseLong(connectorTimestamp.getField("last_updated_ck", ""), 16)) {
/*     */           
/* 254 */           connectorTimestamp.close();
/* 255 */           return false;
/*     */         } 
/*     */       }
/* 258 */       connectorTimestamp.close();
/* 259 */       return true;
/*     */     } 
/* 261 */     return false;
/*     */   }
/*     */ }


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\FamilyManager.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */